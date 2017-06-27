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
package org.eclipse.n4js.ui.dialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * A base class for workspace element selection dialog.
 *
 * This class offers an optional create button in the dialog as well as an API to overlay virtual resources in the
 * workspace tree. This can be useful to display files and folders which shouldn't yet be created.
 */
public class WorkspaceElementSelectionDialog extends ElementTreeSelectionDialog {

	/**
	 * The ID of the optional Create button
	 */
	public static final int CREATE_ID = IDialogConstants.CLIENT_ID + 1;
	private static final String CREATE_LABEL = "Create";

	private final boolean hasCreateButton;

	/**
	 * @param parent
	 *            Parent shell
	 * @param createButton
	 *            Show create button and dialog
	 */
	public WorkspaceElementSelectionDialog(Shell parent, boolean createButton) {
		super(parent, new WorkbenchLabelProvider(),
				new WorkbenchContentProvider());

		this.setHelpAvailable(false);
		this.hasCreateButton = createButton;

	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == CREATE_ID) {
			this.createPressed();
		} else {
			super.buttonPressed(buttonId);
		}
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		if (this.hasCreateButton) {
			createButton(parent, CREATE_ID,
					CREATE_LABEL, false);
		}
		super.createButtonsForButtonBar(parent);
	}

	/**
	 * This method is called when the user clicks the create button.
	 *
	 * This method should be overwritten by subclasses.
	 *
	 */
	public void createPressed() {
		// Do nothing in this implementation
	}

}
