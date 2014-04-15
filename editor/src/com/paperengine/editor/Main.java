package com.paperengine.editor;

import java.awt.EventQueue;


public class Main {
	public static void main(String[] args) {
//		new MainWindow();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
