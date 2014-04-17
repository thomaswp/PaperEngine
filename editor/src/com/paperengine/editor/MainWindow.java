package com.paperengine.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.lwjgl.LWJGLUtil;

import com.paperengine.core.Editor;

public class MainWindow {

	private JFrame frame;
	private GameCanvas gameWindow;
	private JXButton buttonTogglePlay;
	private JXButton buttonTogglePause;
	private JXButton buttonView;
	private ObjectTree objectTree;
	
	private void initGame() {
		
		if(LWJGLUtil.getPlatform() == LWJGLUtil.PLATFORM_WINDOWS) {
			frame.addWindowFocusListener(new WindowAdapter() {
				@Override
				public void windowGainedFocus(WindowEvent e) {
					gameWindow.requestFocusInWindow();
				}
			});
		}
		
		gameWindow.init();
		objectTree.setScene(gameWindow.scene());
	}
	
	/**
	 * Create the application.
	 */
	public MainWindow() {
		Editor.playing = false;
		Editor.viewingEditor = true;
		
		initialize();
		frame.setVisible(true);
		initGame();
	}

	private void splitPaneMoved() {
		frame.setPreferredSize(frame.getSize()); // store the current size to restore it after packing.
		frame.setSize(frame.getWidth() + 1, frame.getHeight()); // resize it!!
		frame.pack();
	}
	
	private void togglePlay() {
		Editor.playing = !Editor.playing;
		updatePlayText();
		if (Editor.paused) {
			togglePause();
		}
		if (Editor.viewingEditor == Editor.playing) {
			toggleView();
		}
		if (!Editor.playing) {
			gameWindow.resetGame();
			objectTree.setScene(gameWindow.scene());
		}
	}
	
	private void updatePlayText() {
		if (!Editor.playing) {
			buttonTogglePlay.setText("Play");
		} else {
			buttonTogglePlay.setText("Stop");
		}
	}
	
	private void togglePause() {
		Editor.paused = !Editor.paused;
		updatePauseText();
		if (Editor.paused && !Editor.playing) {
			Editor.playing = true;
			updatePlayText();
		}
	}

	private void updatePauseText() {
		if (!Editor.paused) {
			buttonTogglePause.setText("Pause");
		} else {
			buttonTogglePause.setText("Unpause");
		}
	}

	private void toggleView() {
		Editor.viewingEditor = !Editor.viewingEditor;
		updateViewText();
	}

	private void updateViewText() {
		if (!Editor.viewingEditor) {
			buttonView.setText("View Editor");
		} else {
			buttonView.setText("View Game");
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(new Dimension(1000, 800));
		frame.setDefaultCloseOperation(JXFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				splitPaneMoved();
			}
		});
		frame.getContentPane().add(splitPane);
		
		JXPanel leftPanel = new JXPanel();
		splitPane.setLeftComponent(leftPanel);
		leftPanel.setLayout(new BorderLayout());
		
		objectTree = new ObjectTree();
		leftPanel.add(objectTree);
		
		JXPanel panel = new JXPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(new FlowLayout());
		
		gameWindow = new GameCanvas();
		panel.add(gameWindow);
		gameWindow.setBackground(Color.LIGHT_GRAY);
		
		JXLabel lblThisIsA = new JXLabel("This is a label");
		frame.getContentPane().add(lblThisIsA, BorderLayout.NORTH);
		
		JXPanel panel_1 = new JXPanel();
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		
		buttonTogglePlay = new JXButton("Play");
		buttonTogglePlay.setPreferredSize(new Dimension(100, 25));
		panel_1.add(buttonTogglePlay);
		
		buttonTogglePause = new JXButton("Pause");
		buttonTogglePause.setPreferredSize(new Dimension(100, 25));
		buttonTogglePause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				togglePause();
			}
		});
		panel_1.add(buttonTogglePause);
		
		buttonView = new JXButton("View Game");
		buttonView.setPreferredSize(new Dimension(100, 25));
		buttonView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				toggleView();
			}
		});
		panel_1.add(buttonView);
		buttonTogglePlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				togglePlay();
			}
		});
	}

	protected GameCanvas gameWindow() {
		return gameWindow;
	}
}
