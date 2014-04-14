package com.paperengine.editor;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import playn.java.JavaPlatform;

import com.paperengine.core.PaperGame;
import com.paperengine.editor.game.JavaEditorPlatform;
import com.paperengine.editor.game.TestScene;

public class GameCanvas extends Canvas {
	private static final long serialVersionUID = 1L;
	
	private PaperGame game;
	private JavaEditorPlatform platform;
	private Thread gameThread;
	
	public void setGame(PaperGame game) {
		this.game = game;
	}
	
	public PaperGame game() {
		return game;
	}
	
	public GameCanvas() {
		setFocusTraversalKeysEnabled(true);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				Display.wasResized();
				platform.setSize(getWidth(), getHeight());
			}
		});
		
		setMinimumSize(new Dimension(0, 0));
	}
	
	@Override
	public Dimension getPreferredSize() {
		if (getParent() == null) return getSize();
		return getParent().getSize();
//		return new Dimension(800, 600);
	}
	
	public void init() {
		
		JavaPlatform.Config config = new JavaPlatform.Config();
		config.width = 800; //getWidth();
		config.height = 600; //getHeight();
		// use config to customize the Java platform, if needed
		platform = JavaEditorPlatform.register(config);

		try {
			Display.setParent(this);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		game = new PaperGame();
		game.setInitCallback(new Runnable() {
			@Override
			public void run() {
				game.setScene(new TestScene());
			}
		});
		
		
		gameThread = new Thread(new Runnable() {
			@Override
			public void run() {
				platform.run(game);
			}
		});
		gameThread.start();
	}
	
}
