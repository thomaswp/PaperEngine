package com.paperengine.editor.editor.field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import pythagoras.f.Point;

import com.paperengine.editor.editor.accessor.Accessor;

public class PointFieldEditor extends FieldEditor<Point> {

	private Label labelBeginParen, labelComma, labelEndParen;
	private Text textX, textY;
	
	public PointFieldEditor(Composite parent, Accessor accessor) {
		super(parent, accessor);
		setLayout(new RowLayout(SWT.HORIZONTAL));
		
		labelBeginParen = new Label(this, SWT.NONE);
		labelBeginParen.setText("(");
		textX = new Text(this, SWT.BORDER);
		labelComma = new Label(this, SWT.NONE);
		labelComma.setText(", ");
		textY = new Text(this, SWT.BORDER);
		labelEndParen = new Label(this, SWT.NONE);
		labelEndParen.setText(")");
		
		textX.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				set();
			}
		});
		
		textY.addModifyListener(new ModifyListener() {			
			@Override
			public void modifyText(ModifyEvent arg0) {
				set();
			}
		});
		
		updateFieldLocal();
	}

	@Override
	public void updateFieldLocal() {
		super.updateFieldLocal();
		Point value = getValue();
		textX.setText(format(value.x));
		textY.setText(format(value.y));
	}
	
	private String format(float x) {
		return String.format("%.04f", x);
	}
	
	private void set() {
		setValue(getUI());
	}

	@Override
	protected Point getUI() {
		Point point = getValue();
		try {
			point.x = Float.parseFloat(textX.getText());
		} catch (Exception e) { }
		try {
			point.y = Float.parseFloat(textY.getText());
		} catch (Exception e) { }
		return point;
	}

	@Override
	protected void setEnabledLocal(boolean enabled) {
		textX.setEnabled(enabled);
		textY.setEnabled(enabled);
	}
}
