package com.paperengine.editor.editor.field;

import java.lang.reflect.Type;


public interface Accessor {
	Object get();
	void set(Object value);
	String name();
	Type type();
}
