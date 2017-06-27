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

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.google.inject.Inject;

import org.eclipse.n4js.ui.wizard.workspace.WizardPreviewProvider.WizardPreview;
import org.eclipse.n4js.ui.wizard.workspace.WorkspaceWizardModelValidator.ValidationResult;

/**
 * A previewable wizard page also displays a code preview in addition to the normal wizard components.
 *
 * Subclasses of this wizard page can implement {@link #updateContentPreview(WizardPreview)} to provide their own code
 * preview.
 */
public abstract class PreviewableWizardPage<M extends WorkspaceWizardModel> extends WorkspaceWizardPage<M> {

	private static final Point PREVIEW_MINIMUM_SHELL_SIZE = new Point(900, 600);

	//// Layout constants
	private static final GridLayout ONE_PANE_LAYOUT = GridLayoutFactory.fillDefaults().numColumns(1).create();
	private static final GridLayout TWO_PANES_LAYOUT = GridLayoutFactory.fillDefaults().equalWidth(true).numColumns(2)
			.create();
	private static final GridDataFactory PREVIEW_GRID_DATA_FACTORY = GridDataFactory.fillDefaults().span(1, 2)
			.grab(true, true);
	private static final GridDataFactory EXCLUDE_PREVIEW_GRID_DATA_FACTORY = GridDataFactory.fillDefaults()
			.exclude(true);

	//// Dialog settings
	private static final String DIALOG_SETTING_HIDE_PREVIEW_KEY = "org.eclipse.n4js.ui.wizard.workspaces.PreviewableWizardPage.ShowPreview";

	//// Preview toggle labels
	private static final String SHOW_PREVIEW_TEXT = "Show preview >>";
	private static final String HIDE_PREVIEW_TEXT = "<< Hide preview";
	private static final String PREVIEW_BUTTON_TOOLTIP = "Toggles the preview";

	@Inject
	private WizardPreviewProvider previewProvider;

	// The main composite dividing the interface in two panes
	private Composite paneComposite;

	private WizardPreview wizardContentPreview;
	private Button previewToggleButton;

	private boolean previewVisible = true;

	@Override
	public void createControl(Composite parent) {
		paneComposite = new Composite(parent, SWT.NONE);
		paneComposite.setLayout(GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(true).create());

		super.createControl(paneComposite);
		workspaceWizardControl.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());

		createPreview(paneComposite);
		createBottomControls(paneComposite);

		setControl(paneComposite);

		boolean hidePreviewSetting = getDialogSettings().getBoolean(DIALOG_SETTING_HIDE_PREVIEW_KEY);

		if (hidePreviewSetting) {
			hideContentPreview();
		} else {
			// Otherwise just make sure the shell size is set
			getShell().setMinimumSize(PREVIEW_MINIMUM_SHELL_SIZE);
		}
	}

	/**
	 * Shows the content preview.
	 */
	protected void showContentPreview() {
		if (previewVisible) {
			return;
		}
		// Update content
		if (getValidator().getValidationResult().valid) {
			updateContentPreview(wizardContentPreview);
		}

		// Switch to two panes
		paneComposite.setLayout(TWO_PANES_LAYOUT);

		// Reinsert the content preview in the layout
		wizardContentPreview.setLayoutData(PREVIEW_GRID_DATA_FACTORY.create());

		// Set internal state
		previewVisible = true;

		// Update button label
		previewToggleButton.setText(HIDE_PREVIEW_TEXT);

		// Set window size limits
		getShell().setMinimumSize(PREVIEW_MINIMUM_SHELL_SIZE);

		paneComposite.layout();

		getDialogSettings().put(DIALOG_SETTING_HIDE_PREVIEW_KEY, false);
	}

	/**
	 * Hides the content preview.
	 */
	protected void hideContentPreview() {
		if (!previewVisible) {
			return;
		}
		int contentPreviewWidth = wizardContentPreview.getClientArea().width;

		// Exclude content preview from layout
		wizardContentPreview.setLayoutData(EXCLUDE_PREVIEW_GRID_DATA_FACTORY.create());
		wizardContentPreview.setBounds(0, 0, 0, 0); // Manually set bounds to avoid weird clipping

		// Switch to one pane
		paneComposite.setLayout(ONE_PANE_LAYOUT);

		// Set internal state
		previewVisible = false;

		// Update button label
		previewToggleButton.setText(SHOW_PREVIEW_TEXT);

		// Reset window size limits
		getShell().setMinimumSize(0, 0);
		getShell().setSize(getShell().getSize().x - contentPreviewWidth, getShell().getSize().y);

		paneComposite.layout();

		getDialogSettings().put(DIALOG_SETTING_HIDE_PREVIEW_KEY, true);
	}

	/**
	 * This method is invoked whenever the model or validation status changes.
	 *
	 * Override this method to implement a custom preview content.
	 */
	protected abstract void updateContentPreview(WizardPreview contentPreview);

	/**
	 * Creates the right pane content, which is the preview area.
	 */
	private void createPreview(Composite parent) {
		wizardContentPreview = previewProvider.create(parent, SWT.NONE);
		wizardContentPreview.setLayoutData(PREVIEW_GRID_DATA_FACTORY.create());

		// Connect a delayed property change listener to the model
		getModel().addPropertyChangeListener(propertyChange -> {
			ValidationResult result = getValidator().getValidationResult();

			// Don't update if the preview is hidden
			if (!previewVisible) {
				return;
			}

			if (result.valid) {
				wizardContentPreview.setEnabled(true);
				updateContentPreview(wizardContentPreview);
			} else {
				wizardContentPreview.setEnabled(false);
				wizardContentPreview.setInfo("");
			}

		});
	}

	/**
	 * Creates the bottom controls.
	 */
	private void createBottomControls(Composite parent) {
		Composite bottomControls = new Composite(parent, SWT.NONE);

		bottomControls
				.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).align(SWT.RIGHT, SWT.CENTER).create());
		bottomControls.setLayout(GridLayoutFactory.fillDefaults().numColumns(1).extendedMargins(0, 5, 0, 0).create());

		previewToggleButton = new Button(bottomControls, SWT.PUSH);
		previewToggleButton.setText(HIDE_PREVIEW_TEXT);
		previewToggleButton.setSelection(true);
		previewToggleButton.setLayoutData(GridDataFactory.fillDefaults().align(SWT.RIGHT, SWT.BOTTOM).create());
		previewToggleButton.setToolTipText(PREVIEW_BUTTON_TOOLTIP);

		previewToggleButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!previewVisible) {
					showContentPreview();
				} else {
					hideContentPreview();
				}
			}
		});
	}

}
