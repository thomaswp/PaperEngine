package com.paperengine.core;

public class PaperEngineException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PaperEngineException() { }
	
	public PaperEngineException(String message) {
		super(message);
	}
}
