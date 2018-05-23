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

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;

import org.eclipse.n4js.ui.wizard.workspace.SuffixText;

/**
 * This class holds the form/ui for {@link CustomElementSelectionDialog}.
 */
public class CustomElementSelectionForm extends Composite {

	private final TreeViewer treeViewer;
	private final Label elementLabel;
	private final SuffixText elementInput;

	/**
	 * Create the composite.
	 */
	public CustomElementSelectionForm(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));

		treeViewer = new TreeViewer(this, SWT.BORDER);
		Tree tree = getTreeViewer().getTree();

		// Set a minimum height to prevent weird dialog dimensions
		tree.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).span(2, 1).minSize(0, 200).create());

		elementLabel = new Label(this, SWT.NONE);
		elementLabel.setLayoutData(GridDataFactory.swtDefaults().create());
		elementLabel.setText("New Label");

		elementInput = new SuffixText(this, SWT.BORDER);// new Text(this, SWT.BORDER);
		elementInput.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
	}

	/**
	 * Set a new element label shown next to the element name input
	 *
	 * @param label
	 *            The new label
	 */
	public void setElementLabel(String label) {
		this.elementLabel.setText(label);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * Return the tree viewer of this dialog
	 *
	 * @return The TreeViewer
	 */
	public TreeViewer getTreeViewer() {
		return treeViewer;
	}

	/**
	 * Return the element name input
	 *
	 * @return The Input
	 */
	public SuffixText getElementInput() {
		return elementInput;
	}
}
