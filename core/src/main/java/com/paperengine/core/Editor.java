package com.paperengine.core;


public class Editor {
	public static boolean playing = true;
	public static boolean paused;
	public static boolean viewingEditor;
	
	public static boolean updateEditor() {
		return viewingEditor;
	}
	
	public static boolean updateGame() {
		return playing && !paused;
	}

//	public static String status() {
//		return String.format("playing=%s, paused=%s, viewingEditor=%s", playing, paused, viewingEditor);
//	}
}
