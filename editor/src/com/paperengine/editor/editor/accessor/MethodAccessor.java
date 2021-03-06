package com.paperengine.editor.editor.accessor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.paperengine.core.Handler.Postable;

public class MethodAccessor implements Accessor {

	private final Method getter, setter;
	private final Postable object;
	private final String name;

	public MethodAccessor(String name, Method getter, Method setter, Postable object) {
		this.name = name;
		this.getter = getter;
		this.setter = setter;
		this.object = object;
		if (getter.getReturnType() != setter.getParameterTypes()[0]) {
			throw new RuntimeException("Type Missmatch!");
		}
	}

	@Override
	public Object get() {
		try {
			return getter.invoke(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void set(final Object value, final Runnable callback) {
		object.handler().post(new Runnable() {
			@Override
			public void run() {
				try {
					setter.invoke(object, value);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (callback != null) callback.run();
				}
			}
		});
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public Type type() {
		return getter.getReturnType();
	}

	public static List<MethodAccessor> getForObject(Postable object) {
		List<MethodAccessor> accessors = new ArrayList<MethodAccessor>();
		ArrayList<String> getterNames = new ArrayList<String>();
		HashMap<String, Method> getters = new HashMap<String, Method>();
		HashMap<String, Method> setters = new HashMap<String, Method>();
		Method[] methods = object.getClass().getMethods();
		for (Method method : methods) {
			if (!Modifier.isPublic(method.getModifiers())) continue;
			String name = method.getName();
			String getterName = null;
			if (name.startsWith("get")) {
				getters.put(getterName = name.substring(3), method);
			} else if (name.startsWith("is")) {
				getters.put(getterName = name.substring(2), method);
			} else if (name.startsWith("set")) {
				setters.put(name.substring(3), method);
			} else {
				getters.put(getterName = Character.toUpperCase(name.charAt(0)) + name.substring(1), method);
			}
			if (getterName != null) getterNames.add(getterName);
		}

		for (String name : getterNames) {
			Method setter = setters.get(name);
			if (setter == null || setter.getReturnType() != void.class) continue;
			Method getter = getters.get(name);

			Type type = getter.getReturnType();
			Class<?>[] parameterTypes = setter.getParameterTypes();
			if (parameterTypes.length != 1 || parameterTypes[0] != type) continue;

			accessors.add(new MethodAccessor(name, getter, setter, object));
		}
		return accessors;
	}

	@Override
	public Accessor copyForObject(Object object) {
		return new MethodAccessor(name, getter, setter, (Postable) object);
	}
	
	@Override
	public boolean sameAs(Object value) {
		Object obj = get();
		if (value == null) return value == obj;
		return value.equals(obj);
	}
}
