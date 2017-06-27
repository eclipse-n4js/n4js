/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.jsdoc2spec.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * Shows a summary of specification export as a simple string.
 */
public class SpecExportCodeSummaryPage extends SpecPage {
	private Text text;

	/**
	 * Constructor
	 */
	public SpecExportCodeSummaryPage(String pageName) {
		super(pageName);
		setMessage("Confirm changes to write files.");
	}

	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new FillLayout());
		text = new Text(composite, SWT.MULTI | SWT.READ_ONLY | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);

		setControl(composite);
	}

	/**
	 * Sets the text of the summary page.
	 */
	public void setText(String text) {
		this.text.setText(text);
	}
}
