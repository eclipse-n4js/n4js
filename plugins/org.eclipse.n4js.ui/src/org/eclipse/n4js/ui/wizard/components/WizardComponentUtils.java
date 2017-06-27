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
package org.eclipse.n4js.ui.wizard.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * Utility methods for wizard components
 */
public class WizardComponentUtils {

	/** Default GridData for text widgets */
	public static GridData fillTextDefaults() {
		GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = false;
		data.horizontalAlignment = SWT.FILL;
		data.verticalAlignment = SWT.CENTER;
		return data;
	}

	/** Default GridData for all labels */
	public static GridData fillLabelDefaults() {
		GridData data = new GridData();
		data.grabExcessHorizontalSpace = false;
		data.grabExcessVerticalSpace = false;
		data.horizontalAlignment = SWT.LEFT;
		data.verticalAlignment = SWT.CENTER;
		return data;
	}

	/** Horizontal line separator */
	public static Control insertHorizontalSeparator(Composite parent) {
		Label label = new Label(parent, SWT.SEPARATOR | SWT.WRAP | SWT.HORIZONTAL | SWT.SHADOW_NONE);
		GridData separatorLayoutData = new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1);
		separatorLayoutData.heightHint = 25;
		label.setLayoutData(separatorLayoutData);
		return label;
	}

	/**
	 * Inserts an empty cell at the current position of a grid layout
	 *
	 * @param parent
	 *            The parent grid layout composite
	 * @return The empty control
	 */
	public static Control emptyGridCell(Composite parent) {
		return new Label(parent, SWT.NONE);
	}

}
