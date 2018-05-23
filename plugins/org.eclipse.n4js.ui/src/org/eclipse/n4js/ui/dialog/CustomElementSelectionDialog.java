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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.SelectionStatusDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.wizard.workspace.SuffixText;

/**
 * This is a basic dialog which allows custom workspace element selection.
 *
 * It provides a tree view as well as a text input to either select an element from the the given input or choose a new
 * element by typing a new element name in the provided text field.
 *
 * It also provides an additional 'Create' dialog button which can be used for further element creation UI.
 */
public abstract class CustomElementSelectionDialog extends SelectionStatusDialog {

	/**
	 * The ID of the Create button
	 */
	public static final int CREATE_ID = IDialogConstants.CLIENT_ID + 1;
	/**
	 * The default create button label.
	 */
	private static final String DEFAULT_CREATE_BUTTON_LABEL = "Create";

	private final ArrayList<ViewerFilter> filters = new ArrayList<>();
	private final String elementLabel;
	private final String createButtonLabel;

	private IInputValidator inputValidator;

	private int autoExpandLevel = 0;

	/**
	 * Tree viewer of this dialog
	 */
	protected TreeViewer treeViewer;

	/**
	 * The create button
	 */
	protected Button createButton;
	/**
	 * The text label for the element name input
	 */
	protected SuffixText elementNameInput;

	/**
	 * Create a new custom element selection dialog with given element label.
	 *
	 * 'Create' is used as default create button label
	 *
	 * @param parentShell
	 *            Parent shell
	 *
	 * @param elementLabel
	 *            The label of the element name input
	 */
	protected CustomElementSelectionDialog(Shell parentShell, String elementLabel) {
		this(parentShell, elementLabel, DEFAULT_CREATE_BUTTON_LABEL);
	}

	/**
	 * Create a new custom element selection dialog with given element and create button label.
	 *
	 * @param parentShell
	 *            Parent shell
	 * @param elementLabel
	 *            The label of the element name input
	 * @param createButtonLabel
	 *            The label of the create button
	 */
	protected CustomElementSelectionDialog(Shell parentShell, String elementLabel, String createButtonLabel) {
		super(parentShell);
		this.elementLabel = elementLabel;
		this.createButtonLabel = createButtonLabel;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		CustomElementSelectionForm form = new CustomElementSelectionForm(parent, SWT.FILL);
		form.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());

		TreeViewer v = form.getTreeViewer();
		for (ViewerFilter filter : filters) {
			v.addFilter(filter);
		}
		this.treeViewer = v;

		WorkbenchContentProvider contentProvider = new WorkbenchContentProvider();
		ILabelProvider labelProvider = new WorkbenchLabelProvider();

		this.treeViewer.setContentProvider(contentProvider);
		this.treeViewer.setLabelProvider(labelProvider);

		this.treeViewer.setComparator(new ViewerComparator());

		this.treeViewer.setAutoExpandLevel(this.getAutoExpandLevel());

		form.setElementLabel(this.elementLabel);

		this.elementNameInput = form.getElementInput();

		form.getElementInput().addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals(SuffixText.TEXT_PROPERTY) ||
						evt.getPropertyName().equals(SuffixText.SUFFIX_PROPERTY)) {
					validateElementInput();
					elementInputChanged();
				}

			}
		});
		return form;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		// Add an additional dialog button for the create functionality
		this.createButton = createButton(parent, CREATE_ID, this.createButtonLabel, false);
		super.createButtonsForButtonBar(parent);
	}

	/**
	 * Add a filter for the dialog tree viewer
	 *
	 * @param filter
	 *            The filter to add
	 */
	public void addFilter(ViewerFilter filter) {
		this.filters.add(filter);
	}

	/**
	 * Set a single element initial selection
	 *
	 * @param element
	 *            The initially selected element
	 */
	public void setInitialSelection(Object element) {
		this.setInitialSelections(new Object[] { element });
	}

	/**
	 * Set the input object for the tree viewer.
	 *
	 * <p>
	 * Note that this method has only an effect on opened dialogs
	 * </p>
	 *
	 * @param treeInput
	 *            The input object
	 */
	public void setInput(Object treeInput) {
		if (this.treeViewer != null) {
			this.treeViewer.setInput(treeInput);

			if (!this.getInitialElementSelections().isEmpty()) {
				this.treeViewer.setSelection(new StructuredSelection(this.getInitialElementSelections().get(0)));
			}
		}
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == CREATE_ID) {
			this.createPressed();
			return;
		}
		super.buttonPressed(buttonId);
	}

	/**
	 * Update the error state of the dialog.
	 *
	 * @param errorMessage
	 *            The error message or null for no error
	 */
	protected void updateError(String errorMessage) {
		if (errorMessage != null) {
			updateStatus(new Status(IStatus.ERROR, N4JSActivator.ORG_ECLIPSE_N4JS_N4JS, errorMessage));
		} else {
			// Explicitly set empty message ok status here
			updateStatus(new Status(IStatus.OK, N4JSActivator.ORG_ECLIPSE_N4JS_N4JS, ""));
		}
	}

	/**
	 * This method is invoked when the button 'Create' is pressed
	 */
	protected void createPressed() {
		// To be implemented by subclasses
	}

	/**
	 * This method is invoked when the element name input text has changed
	 */
	protected void elementInputChanged() {
		// To be implemented by subclasses
	}

	/**
	 * @return The auto expand level of the tree view
	 */
	public int getAutoExpandLevel() {
		return autoExpandLevel;
	}

	/**
	 * Set the amount of tree levels automatically expanded on dialog presentation.
	 *
	 * @param autoExpandLevel
	 *            Auto expand level
	 */
	public void setAutoExpandLevel(int autoExpandLevel) {
		this.autoExpandLevel = autoExpandLevel;
	}

	/**
	 * Set the input validator for the dialogs element name input field.
	 *
	 * @param inputValidator
	 *            The input validator
	 */
	public void setInputValidator(IInputValidator inputValidator) {
		this.inputValidator = inputValidator;
	}

	/**
	 * Validate element input and update the status of the dialog
	 */
	protected void validateElementInput() {
		if (inputValidator != null) {
			String validationError = inputValidator
					.isValid(elementNameInput.getText() + elementNameInput.getSuffix());
			// update error to be nothing(null) or the validationError
			updateError(validationError);
		}
	}

	/**
	 * Implement this method to compute the dialog results.
	 */
	@Override
	protected abstract void computeResult();

}
