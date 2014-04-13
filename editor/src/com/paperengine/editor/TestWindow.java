package com.paperengine.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.lwjgl.LWJGLUtil;

import com.paperengine.core.PaperGame;
import javax.swing.JTree;

public class TestWindow {

	private JFrame frame;
	private GameCanvas gameWindow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new TestWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initGame() {
		
		if(LWJGLUtil.getPlatform() == LWJGLUtil.PLATFORM_WINDOWS) {
			frame.addWindowFocusListener(new WindowAdapter() {
				@Override
				public void windowGainedFocus(WindowEvent e) {
					gameWindow.requestFocusInWindow();
				}
			});
		}
		
		gameWindow.setGame(new PaperGame());
		gameWindow.init();
	}
	
	/**
	 * Create the application.
	 */
	public TestWindow() {
		initialize();
		frame.setVisible(true);
		initGame();
	}

	private void splitPaneMoved() {
		frame.setPreferredSize(frame.getSize()); // store the current size to restore it after packing.
		frame.setSize(frame.getWidth() + 1, frame.getHeight()); // resize it!!
		frame.pack();
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
	}

	protected GameCanvas gameWindow() {
		return gameWindow;
	}
}
