package com.paperengine.core;

import java.util.LinkedList;
import java.util.List;

public class Handler {

	public static ListProvider listProvider = new ListProvider() {
		@Override
		public <T> List<T> createList() {
			return new LinkedList<T>();
		}
	};
	
	private List<Runnable> eventQueue = listProvider.createList();
	
	public void post(Runnable runnable) {
		eventQueue.add(runnable);
	}
	
	public void update() {
		while (eventQueue.size() > 0) {
			eventQueue.remove(0).run();
		}
	}
	
	public static interface Postable { 
		public Handler handler();
	}
	
	public static interface ListProvider {
		public <T> List<T> createList();
	}
}
