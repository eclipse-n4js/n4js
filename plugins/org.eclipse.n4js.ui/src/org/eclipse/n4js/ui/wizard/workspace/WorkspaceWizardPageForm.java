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
package org.eclipse.n4js.ui.wizard.workspace;

import static org.eclipse.n4js.ui.wizard.components.WizardComponentUtils.fillLabelDefaults;
import static org.eclipse.n4js.ui.wizard.components.WizardComponentUtils.fillTextDefaults;
import static org.eclipse.n4js.ui.wizard.components.WizardComponentUtils.insertHorizontalSeparator;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * The controls for a {@link WorkspaceWizardPage}.
 *
 */
public class WorkspaceWizardPageForm extends Composite {

	private final Text projectText;
	private final Text sourceFolderText;
	private final Button projectBrowseButton;
	private final Button sourceFolderBrowseButton;
	private final Button moduleSpecifierBrowseButton;
	private final SuffixText moduleSpecifierSuffixText;

	/**
	 * Create the composite.
	 *
	 */
	public WorkspaceWizardPageForm(Composite parent, int style) {
		super(parent, style);

		this.setLayout(new GridLayout(3, false));

		Label projectLabel = new Label(this, SWT.NONE);
		projectLabel.setText("Project:");
		projectLabel.setLayoutData(GridDataFactory.swtDefaults().create());

		projectText = new Text(this, SWT.BORDER);
		projectText.setLayoutData(fillTextDefaults());

		projectBrowseButton = new Button(this, SWT.NONE);
		projectBrowseButton.setToolTipText("Opens a dialog to choose the project");
		projectBrowseButton.setText("Browse...");

		Label sourceFolderLabel = new Label(this, SWT.NONE);
		sourceFolderLabel.setText("Source folder:");
		sourceFolderLabel.setLayoutData(fillLabelDefaults());

		sourceFolderText = new Text(this, SWT.BORDER);
		sourceFolderText.setLayoutData(fillTextDefaults());

		sourceFolderBrowseButton = new Button(this, SWT.NONE);
		sourceFolderBrowseButton.setToolTipText("Opens a dialog to choose the source folder");
		sourceFolderBrowseButton.setText("Browse...");

		Label moduleSpecifierLabel = new Label(this, SWT.NONE);
		moduleSpecifierLabel.setLayoutData(fillLabelDefaults());
		moduleSpecifierLabel.setText("Module specifier:");

		moduleSpecifierSuffixText = new SuffixText(this, SWT.BORDER);
		getModuleSpecifierText().setLayoutData(fillTextDefaults());

		moduleSpecifierBrowseButton = new Button(this, SWT.NONE);
		moduleSpecifierBrowseButton.setToolTipText("Opens a dialog to choose the module specifier");
		moduleSpecifierBrowseButton.setText("Browse...");

		insertHorizontalSeparator(this);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/** The project text input */
	public Text getProjectText() {
		return projectText;
	}

	/** The project browse button */
	public Button getProjectBrowseButton() {
		return projectBrowseButton;
	}

	/** The source folder text input */
	public Text getSourceFolderText() {
		return sourceFolderText;
	}

	/** The source folder browse button */
	public Button getSourceFolderBrowseButton() {
		return sourceFolderBrowseButton;
	}

	/** The module specifier text input */
	public SuffixText getModuleSpecifierText() {
		return moduleSpecifierSuffixText;
	}

	/** The module specifier browse button */
	public Button getModuleSpecifierBrowseButton() {
		return moduleSpecifierBrowseButton;
	}
}
