package com.paperengine.editor;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.Display;

import playn.java.JavaPlatform;

import com.paperengine.core.PaperGame;
import com.paperengine.editor.game.JavaEditorPlatform;

@SuppressWarnings("serial")
public class MainWindow extends Frame implements WindowListener {

	final Canvas canvas;
	final JavaEditorPlatform platform;
	final Thread gameThread;
	
	final Dimension GAME_SIZE = new Dimension(800, 600);

	public MainWindow() {
		System.setProperty("org.lwjgl.input.Mouse.allowNegativeMouseCoords", "true");
		
		setSize(1000, 700);
		setVisible(true);
		addWindowListener(this);

		setLayout(new FlowLayout());
		
		canvas = new Canvas();
		canvas.setFocusTraversalKeysEnabled(false);
		canvas.setMaximumSize(GAME_SIZE);
		canvas.setSize(GAME_SIZE);

		add(new Label("Hello"));
		add(canvas);
		
		// on Windows we need to transfer focus to the Canvas
		// otherwise keyboard input does not work when using alt-tab
		if(LWJGLUtil.getPlatform() == LWJGLUtil.PLATFORM_WINDOWS) {
			addWindowFocusListener(new WindowAdapter() {
				@Override
				public void windowGainedFocus(WindowEvent e) {
					canvas.requestFocusInWindow();
				}
			});
		}
		
		JavaPlatform.Config config = new JavaPlatform.Config();
		config.width = canvas.getWidth();
		config.height = canvas.getHeight();
		// use config to customize the Java platform, if needed
		platform = JavaEditorPlatform.register(config);

		try {
			Display.setParent(canvas);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		canvas.paint(getGraphics());
		
		gameThread = new Thread(new Runnable() {
			@Override
			public void run() {
				platform.run(new PaperGame());
			}
		});
		gameThread.start();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}


}
