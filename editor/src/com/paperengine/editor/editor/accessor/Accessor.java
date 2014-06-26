package com.paperengine.editor.editor.accessor;

import java.lang.reflect.Type;


public interface Accessor {
	Object get();
	void set(Object value, Runnable callback);
	String name();
	Type type();
	Accessor copyForObject(Object object);
	boolean sameAs(Object value);
}
