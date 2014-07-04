package com.paperengine.core;


public class Editor {
	public static boolean playing = true;
	public static boolean paused;
	public static boolean viewingEditor;
	
	public static boolean drawEditor() {
//		return viewingEditor;
		return !playing || paused || viewingEditor;
	}
	
	public static boolean updateGame() {
		return playing && !paused;
	}
	
	public static boolean canEdit() {
		return !playing || paused;
	}

//	public static String status() {
//		return String.format("playing=%s, paused=%s, viewingEditor=%s", playing, paused, viewingEditor);
//	}
}
