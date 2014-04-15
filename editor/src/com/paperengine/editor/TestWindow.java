package com.paperengine.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;

import org.lwjgl.LWJGLUtil;

import com.paperengine.core.Editor;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.peer.ButtonPeer;

public class TestWindow {

	private JFrame frame;
	private GameCanvas gameWindow;
	private JButton buttonTogglePlay;
	private JButton buttonTogglePause;
	private JButton buttonView;

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
	}
	
	/**
	 * Create the application.
	 */
	public TestWindow() {
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		JPanel leftPanel = new JPanel();
		splitPane.setLeftComponent(leftPanel);
		leftPanel.setLayout(new BorderLayout());
		
		JTree tree = new JTree();
		leftPanel.add(tree);
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(new FlowLayout());
		
		gameWindow = new GameCanvas();
		panel.add(gameWindow);
		gameWindow.setBackground(Color.LIGHT_GRAY);
		
		JLabel lblThisIsA = new JLabel("This is a label");
		frame.getContentPane().add(lblThisIsA, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		
		buttonTogglePlay = new JButton("Play");
		buttonTogglePlay.setPreferredSize(new Dimension(100, 25));
		panel_1.add(buttonTogglePlay);
		
		buttonTogglePause = new JButton("Pause");
		buttonTogglePause.setPreferredSize(new Dimension(100, 25));
		buttonTogglePause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				togglePause();
			}
		});
		panel_1.add(buttonTogglePause);
		
		buttonView = new JButton("View Game");
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
