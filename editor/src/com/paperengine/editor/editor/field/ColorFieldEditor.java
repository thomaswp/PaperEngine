package com.paperengine.editor.editor.field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import playn.core.Color;

import com.paperengine.editor.editor.accessor.Accessor;

public class ColorFieldEditor extends FieldEditor<Integer> {

	private int color;
	private Label colorLabel;
	private Text textAlpha;
	
	public ColorFieldEditor(Composite parent, Accessor accessor) {
		super(parent, accessor);
		RowLayout layout = new RowLayout(SWT.HORIZONTAL);
		layout.center = true;
		setLayout(layout);
		
		colorLabel = new Label(this, SWT.BORDER | SWT.BORDER_SOLID);
		colorLabel.setText("     ");
		
		Label labelAlpha = new Label(this, SWT.None);
		labelAlpha.setText("  A: ");
		
		textAlpha = new Text(this, SWT.BORDER);
		
		updateFieldLocal();

		colorLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				super.mouseDown(e);
				ColorDialog dialog = new ColorDialog(ColorFieldEditor.this.getShell());
				dialog.setRGB(fromColor(color));
				RGB rgb = dialog.open();
				if (rgb != null) {
					setValue(Color.argb(Color.alpha(color), rgb.red, rgb.green, rgb.blue));
				}
			}
		});
		
		textAlpha.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				try {
					int alpha = Integer.parseInt(textAlpha.getText());
					alpha = Math.max(Math.min(alpha, 255), 0);
					int c = Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
					setValue(c);
				} catch (NumberFormatException ex)
				{ }
			}
		});
		textAlpha.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				textAlpha.setText("" + Color.alpha(color));
			}
			
			@Override
			public void focusGained(FocusEvent e) { }
		});
	}

	@Override
	protected Integer getUI() {
		return color;
	}

	@Override
	protected void setEnabledLocal(boolean enabled) {
		colorLabel.setEnabled(enabled);
		textAlpha.setEnabled(enabled);
	}
	
	private RGB fromColor(int color) {
		return new RGB(Color.red(color), Color.green(color), Color.blue(color));
	}
	
	@Override
	protected void updateFieldLocal() {
		super.updateFieldLocal();
		updateValues(getValue());
	}
	
	private void updateValues(int color) {
		this.color = color;
		colorLabel.setBackground(new org.eclipse.swt.graphics.Color(getShell().getDisplay(), fromColor(color)));
		String text = "" + Color.alpha(color);
		if (!textAlpha.isFocusControl() && !textAlpha.getText().equals(text)) textAlpha.setText(text);
	}

	@Override
	protected void setValue(Integer value) {
		updateValues(value);
		super.setValue(value);
	}
}
