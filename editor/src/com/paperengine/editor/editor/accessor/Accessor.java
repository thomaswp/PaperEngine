package com.paperengine.editor.editor.accessor;

import java.lang.reflect.Type;


public interface Accessor {
	Object get();
	void set(Object value);
	String name();
	Type type();
	Accessor copyForObject(Object object);
}
