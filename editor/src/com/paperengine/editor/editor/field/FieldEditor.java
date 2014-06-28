package com.paperengine.editor.editor.field;

import java.lang.reflect.Type;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import pythagoras.f.Point;

import com.paperengine.core.Editor;
import com.paperengine.core.Handler;
import com.paperengine.core.Scene;
import com.paperengine.core.Handler.Postable;
import com.paperengine.editor.editor.accessor.Accessor;

public abstract class FieldEditor<T> extends Composite implements Postable {
	
	protected Handler handler;
	protected Accessor accessor;
	private boolean wasPlaying;
	private boolean setting, getting;

	protected abstract T getUI();
	protected abstract void setEnabledLocal(boolean enabled);
	
	public FieldEditor(Composite parent, Accessor accessor) {
		super(parent, SWT.NONE);
		handler = new Handler();
		this.accessor = accessor;
	}
	
	public static FieldEditor<?> create(Composite parent, Accessor accessor) {
		Type type = accessor.type();
		if (type == float.class || type == Float.class) {
			return new FloatFieldEditor(parent, accessor); 
		} else if (type == int.class || type == Integer.class) {
			if (accessor.name().toLowerCase().contains("color")) {
				return new ColorFieldEditor(parent, accessor);
			} else {
				return new IntegerFieldEditor(parent, accessor);
			}
		} else if (type == boolean.class || type == Boolean.class) {
			return new BooleanFieldEditor(parent, accessor); 
		} else if (type == Point.class) {
			return new PointFieldEditor(parent, accessor); 
		} else if (type instanceof Class<?> && ((Class<?>) type).isEnum()) {
			return new EnumFieldEditor(parent, accessor);
		} else {
			System.out.println("No editor for: " + type.toString());
		}
		return null;
	}

	public void update(Scene scene) {
		handler.update();
		setEnabledLocal(Editor.canEdit());
		if (wasPlaying || Editor.playing) {
			updateField();
		}
		wasPlaying = Editor.playing;
	}
	
	protected void updateFieldLocal() {
	}
	
	public final void updateField() {
		if (setting || accessor.sameAs(getUI())) return;
		getting = true;
		updateFieldLocal();
		getting = false;
	}
	
	@SuppressWarnings("unchecked")
	protected T getValue() {
		return (T) accessor.get();
	}
	
	protected void setValue(T value) {
		if (getting || accessor.sameAs(value)) return;
//		System.out.println("Set: " + value);
		setting = true;
		accessor.set(value, new Runnable() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						updateField();
						setting = false;
//						System.out.println("Update: " + accessor.get());
					}
				});
			}
		});
	}
	
	@Override
	public Handler handler() {
		return handler;
	}
}
