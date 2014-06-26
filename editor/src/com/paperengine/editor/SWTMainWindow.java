package com.paperengine.editor;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import com.paperengine.core.Editor;
import com.paperengine.core.GameObject;
import com.paperengine.core.Handler;
import com.paperengine.core.Handler.ListProvider;
import com.paperengine.core.Scene;
import com.paperengine.editor.ObjectTree.ObjectSelectedListener;
import com.paperengine.editor.editor.ObjectEditor;


public class SWTMainWindow implements ObjectSelectedListener {

	private Display display;
	private GameCanvas gameCanvas;
	private Button buttonToggleView;
	private Button buttonTogglePause;
	private Button buttonTogglePlay;
	private ObjectTree objectTree;
	private ObjectEditor objectEditor;
	private Frame awtFrame;
	
	public Scene scene() {
		return gameCanvas.scene();
	}
	
	public static void main(String[] args) {
		
		SWTMainWindow window = new SWTMainWindow();
		window.open();
	}

	private void togglePlay() {
		if (!Editor.playing) {
			gameCanvas.pushScene();
			objectTree.setScene(gameCanvas.scene());
		}
		Editor.playing = !Editor.playing;
		updatePlayText();
		if (Editor.paused) {
			togglePause();
		}
		if (Editor.viewingEditor == Editor.playing) {
			toggleView();
		}
		if (!Editor.playing) {
			gameCanvas.popScene();
			objectTree.setScene(gameCanvas.scene());
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
		if (!Editor.paused && !Editor.playing) {
			togglePlay();
		}
		Editor.paused = !Editor.paused;
		updatePauseText();
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
			buttonToggleView.setText("View Editor");
		} else {
			buttonToggleView.setText("View Game");
		}
	}

	@Override
	public void onObjectSelected(GameObject object) {
		objectEditor.loadObject(object);
	}
	
	public void open() {
		Editor.playing = false;
		Editor.viewingEditor = true;
		Handler.listProvider = new ListProvider() {
			@Override
			public <T> List<T> createList() {
				return Collections.synchronizedList(new LinkedList<T>());
			}
		};
		
		display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(1024, 600);
		shell.setText("SWT GL Mutiple Composites");
		shell.setLayout(new GridLayout(1, false));
		
		Composite compositeTopPanel = new Composite(shell, SWT.NONE);
		GridLayout gl_compositeTopPanel = new GridLayout(3, false);
		gl_compositeTopPanel.marginHeight = 0;
		gl_compositeTopPanel.marginWidth = 0;
		gl_compositeTopPanel.verticalSpacing = 0;
		gl_compositeTopPanel.horizontalSpacing = 0;
		compositeTopPanel.setLayout(gl_compositeTopPanel);
		compositeTopPanel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		compositeTopPanel.setSize(new Point(0, 50));
		
		Composite composite_2 = new Composite(compositeTopPanel, SWT.NONE);
		composite_2.setLayout(new FillLayout(SWT.HORIZONTAL));
		composite_2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		Composite composite_1 = new Composite(compositeTopPanel, SWT.NONE);
		GridData gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_1.minimumWidth = 300;
		composite_1.setLayoutData(gd_composite_1);
		RowLayout rl_composite_1 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_1.marginBottom = 0;
		rl_composite_1.marginTop = 0;
		rl_composite_1.pack = false;
		composite_1.setLayout(rl_composite_1);
		
		buttonTogglePlay = new Button(composite_1, SWT.NONE);
		buttonTogglePlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(org.eclipse.swt.events.MouseEvent arg0) {
				togglePlay();
			}
		});
		buttonTogglePlay.setText("Play");
		
		buttonTogglePause = new Button(composite_1, SWT.NONE);
		buttonTogglePause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(org.eclipse.swt.events.MouseEvent arg0) {
				togglePause();
			}
		});
		buttonTogglePause.setText("Pause");
		
		buttonToggleView = new Button(composite_1, SWT.NONE);
		buttonToggleView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(org.eclipse.swt.events.MouseEvent arg0) {
				toggleView();
			}
		});
		buttonToggleView.setText("View Game");
		
		Composite composite_3 = new Composite(compositeTopPanel, SWT.NONE);
		composite_3.setLayout(new FillLayout(SWT.HORIZONTAL));
		composite_3.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		SashForm sashFormVertical = new SashForm(shell, SWT.NONE);
		sashFormVertical.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		sashFormVertical.setOrientation(SWT.VERTICAL);
		
		SashForm sashFormHorizontal = new SashForm(sashFormVertical, SWT.NONE);
		sashFormHorizontal.setTouchEnabled(true);
		
		ScrolledComposite scrolledCompositeObjectTree = new ScrolledComposite(sashFormHorizontal, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledCompositeObjectTree.setExpandHorizontal(true);
		scrolledCompositeObjectTree.setExpandVertical(true);
		
		objectTree = new ObjectTree(scrolledCompositeObjectTree, SWT.BORDER);
		scrolledCompositeObjectTree.setContent(objectTree.tree());
		scrolledCompositeObjectTree.setMinSize(objectTree.tree().computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		final Composite composite = new Composite(sashFormHorizontal, SWT.EMBEDDED);
		composite.setDragDetect(false);
		
		awtFrame = SWT_AWT.new_Frame(composite);
		
		Panel panel = new Panel();
		awtFrame.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		gameCanvas = new GameCanvas();
		panel.add(gameCanvas);
		gameCanvas.init();
		
		ScrolledComposite scrolledCompositeObjectEditor = new ScrolledComposite(sashFormHorizontal, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledCompositeObjectEditor.setExpandHorizontal(true);
		scrolledCompositeObjectEditor.setExpandVertical(true);
		sashFormHorizontal.setWeights(new int[] {174, 635, 193});
		
		objectEditor = new ObjectEditor(scrolledCompositeObjectEditor, SWT.NONE);
		RowLayout rl_objectEditor = new RowLayout(SWT.VERTICAL);
		rl_objectEditor.wrap = false;
		rl_objectEditor.fill = true;
		objectEditor.setLayout(rl_objectEditor);
		
		scrolledCompositeObjectEditor.setContent(objectEditor);
		scrolledCompositeObjectEditor.setMinSize(objectEditor.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Composite compositeDebug = new Composite(sashFormVertical, SWT.NONE);
		compositeDebug.setLayout(new RowLayout(SWT.HORIZONTAL));
		sashFormVertical.setWeights(new int[] {10, 1});
		
		Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		
		MenuItem fileItem = new MenuItem(menuBar, SWT.CASCADE);
		fileItem.setText("File");
		
		Menu fileMenu = new Menu(fileItem);
		fileItem.setMenu(fileMenu);
		
		for (int i = 0; i < 5; i++) {
			final MenuItem importItem = new MenuItem(fileMenu, SWT.NONE);
			importItem.setText("Item " + i);
		}

		shell.open();		
		start(); // render loop thread
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				update();
				display.sleep();
			}
		}
		display.dispose();
		gameCanvas.dispose();
	}

	public void start() {
		objectTree.setScene(gameCanvas.scene());
		objectTree.setListener(this);
	}
	
	public final static int FPS = 10;
	private long lastUpdate = System.currentTimeMillis();
	
	public void update() {
		long elapsed = System.currentTimeMillis() - lastUpdate;
		if (elapsed > 1000 / FPS) {
			lastUpdate += elapsed;
			objectTree.update(gameCanvas.scene());
			objectEditor.update(gameCanvas.scene());
		}
	}
	
	public static MouseEvent toAwtMouseEvent(org.eclipse.swt.events.MouseEvent event, Component compoment) {
        int button = MouseEvent.NOBUTTON;
        switch (event.button) {
        case 1: button = MouseEvent.BUTTON1; break;
        case 2: button = MouseEvent.BUTTON2; break;
        case 3: button = MouseEvent.BUTTON3; break;
        }
        int modifiers = 0;
        if ((event.stateMask & SWT.CTRL) != 0) {
            modifiers |= InputEvent.CTRL_DOWN_MASK;
        }
        if ((event.stateMask & SWT.SHIFT) != 0) {
            modifiers |= InputEvent.SHIFT_DOWN_MASK;
        }
        if ((event.stateMask & SWT.ALT) != 0) {
            modifiers |= InputEvent.ALT_DOWN_MASK;
        }
        MouseEvent awtMouseEvent = new MouseEvent(compoment, event.hashCode(),
                event.time, modifiers, event.x, event.y, 1, false, button);
        return awtMouseEvent;
    }

}