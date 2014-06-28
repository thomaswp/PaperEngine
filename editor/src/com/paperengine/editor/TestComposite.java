package com.paperengine.editor;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;

public class TestComposite extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TestComposite(Composite parent, int style) {
		super(parent, style);
		RowLayout rowLayout = new RowLayout(SWT.HORIZONTAL);
		rowLayout.center = true;
		setLayout(rowLayout);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.setBounds(0, 0, 75, 25);
		btnNewButton.setText("New Button");
		
		Button btnNewButton_3 = new Button(this, SWT.NONE);
		btnNewButton_3.setText("New Button");
		
		Button btnNewButton_1 = new Button(this, SWT.NONE);
		btnNewButton_1.setBounds(0, 0, 75, 25);
		btnNewButton_1.setText("New Button");
		
		Button btnNewButton_2 = new Button(this, SWT.NONE);
		btnNewButton_2.setBounds(0, 0, 75, 25);
		btnNewButton_2.setText("New Button");
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setText("New Label");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
