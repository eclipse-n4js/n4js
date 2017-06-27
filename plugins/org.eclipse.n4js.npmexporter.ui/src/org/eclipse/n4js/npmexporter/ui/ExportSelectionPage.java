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
package org.eclipse.n4js.npmexporter.ui;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.equinox.bidi.StructuredTextTypeHandlerFactory;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.util.BidiUtils;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.dialogs.WizardExportResourcesPage;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;
import org.eclipse.ui.model.WorkbenchLabelProvider;

@SuppressWarnings({ "javadoc", "restriction" })
public class ExportSelectionPage extends WizardExportResourcesPage {

	private final static String STORE_EXPORT_DESTINATION_FOLDERS_ID = "org.eclipse.n4js.npmexporter.ui.ExportSelectionPage.destinationNameField";
	private final static String STORE_EXPORT_COMPRESS_CONTENTS_ID = "org.eclipse.n4js.npmexporter.ui.ExportSelectionPage.compressContentsValue";

	private Combo destinationNameField;
	private Button destinationBrowseButton;
	private Text errorText;

	private final Map<IProject, Boolean> projects;

	private CheckboxTableViewer choiceList;
	private Button compressContentsCheckbox;
	private boolean errorFlag = false;

	/**
	 * @param name
	 * @param iProjects
	 * @param labelProvider
	 */
	protected ExportSelectionPage(String name, Map<IProject, Boolean> iProjects) {
		super(name, null);
		this.setTitle(name);
		this.projects = iProjects;
		setMessage("Select npm export root folder.");
	}

	/**
	 * (non-Javadoc) Method declared on IDialogPage.
	 */
	@Override
	public void createControl(Composite parent) {

		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
				| GridData.HORIZONTAL_ALIGN_FILL));
		composite.setFont(parent.getFont());

		// createResourcesGroup(composite); // unwanted.
		createChoiceListGroup(composite);
		// createButtonsGroup(composite); // unwanted.

		createDestinationGroup(composite);

		createOptionsGroup(composite);

		restoreResourceSpecificationWidgetValues();
		restoreWidgetValues();

		setupBasedOnInitialSelections();

		createErrorGroup(composite);
		setControl(composite);

		updateWidgetEnablements();
		updatePageCompletion();

	}

	/**
	 * The List of projects
	 *
	 * @param composite
	 */
	private void createChoiceListGroup(Composite parent) {
		Composite choiceListGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		choiceListGroup.setLayout(layout);
		choiceListGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		choiceListGroup.setFont(parent.getFont());
		choiceList = CheckboxTableViewer.newCheckList(choiceListGroup, SWT.BORDER);
		choiceList.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		choiceList.setLabelProvider(WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider());

		// sort the projects before displaying, ignoring case like in the workspace projects view.
		final List<IProject> sortedProjects = new ArrayList<>(projects.keySet());
		sortedProjects.sort((a, b) -> a.getName()
				.compareToIgnoreCase(b.getName()));
		choiceList.setContentProvider(new IStructuredContentProvider() {

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				// n.t.d.
			}

			@Override
			public void dispose() {
				// n.t.d.
			}

			@Override
			public Object[] getElements(Object inputElement) {
				return sortedProjects.toArray();
			}
		});
		choiceList.setCheckStateProvider(new ICheckStateProvider() {

			@Override
			public boolean isGrayed(Object element) {
				return false;
			}

			@Override
			public boolean isChecked(Object element) {
				Boolean checkedState = projects.get(element);
				return checkedState != null && checkedState.booleanValue();
			}
		});
		choiceList.setInput(new StructuredSelection(projects));
		choiceList.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				handleCheckStateChange(event);
			}
		});

	}

	/**
	 * @param parent
	 * @param composite
	 */
	private void createErrorGroup(Composite parent) {
		Composite errorGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		errorGroup.setLayout(layout);
		errorGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		errorGroup.setFont(parent.getFont());
		errorText = new Text(errorGroup, SWT.READ_ONLY | SWT.WRAP | SWT.MULTI | SWT.BORDER);
		errorText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		errorText.setText("asldfjlasjflkj");
	}

	/**
	 * Create the export destination specification widgets
	 *
	 * @param parent
	 *            org.eclipse.swt.widgets.Composite
	 */
	@SuppressWarnings("unused")
	@Override
	protected void createDestinationGroup(Composite parent) {

		Font font = parent.getFont();
		// destination specification group
		Composite destinationSelectionGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		destinationSelectionGroup.setLayout(layout);
		destinationSelectionGroup.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL));
		destinationSelectionGroup.setFont(font);

		Label destinationLabel = new Label(destinationSelectionGroup, SWT.NONE);
		destinationLabel.setText("npm Target Folder");
		destinationLabel.setFont(font);

		// destination name entry field
		destinationNameField = new Combo(destinationSelectionGroup, SWT.SINGLE
				| SWT.BORDER);
		destinationNameField.addListener(SWT.Modify, this);
		destinationNameField.addListener(SWT.Selection, this);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		destinationNameField.setLayoutData(data);
		destinationNameField.setFont(font);
		BidiUtils.applyBidiProcessing(destinationNameField, StructuredTextTypeHandlerFactory.FILE);

		// destination browse button
		destinationBrowseButton = new Button(destinationSelectionGroup,
				SWT.PUSH);
		destinationBrowseButton.setText(DataTransferMessages.DataTransfer_browse);
		destinationBrowseButton.addListener(SWT.Selection, this);
		destinationBrowseButton.setFont(font);
		setButtonLayoutData(destinationBrowseButton);

		new Label(parent, SWT.NONE); // vertical spacer
	}

	@Override
	protected void createOptionsGroupButtons(Group optionsGroup) {
		Font font = optionsGroup.getFont();
		optionsGroup.setLayout(new GridLayout(2, true));

		Composite left = new Composite(optionsGroup, SWT.NONE);
		left.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
		left.setLayout(new GridLayout(1, true));

		// compress... checkbox
		compressContentsCheckbox = new Button(left, SWT.CHECK | SWT.LEFT);
		compressContentsCheckbox.setText(DataTransferMessages.ZipExport_compressContents);
		compressContentsCheckbox.setFont(font);

	}

	@Override
	protected void setupBasedOnInitialSelections() {
		// don't: super.setupBasedOnInitialSelections();
	}

	@Override
	public void handleEvent(Event event) {
		Widget source = event.widget;

		if (source == destinationBrowseButton) {
			handleDestinationBrowseButtonPressed();
		}

		updatePageCompletion();

	}

	/** recompute page completion upon check-changes. */
	protected void handleCheckStateChange(CheckStateChangedEvent event) {
		// Update the selection map accordingly
		Object changedElement = event.getElement();
		if (changedElement instanceof IProject) {
			projects.put((IProject) changedElement, event.getChecked());
		}

		updatePageCompletion();
	}

	/**
	 * Open an appropriate destination browser so that the user can specify a source to import from
	 */
	protected void handleDestinationBrowseButtonPressed() {
		DirectoryDialog dialog = new DirectoryDialog(getContainer().getShell(),
				SWT.SAVE | SWT.SHEET);
		dialog.setMessage("Select npm destination folder");
		dialog.setText("Select npm destination folder");
		dialog.setFilterPath(getDestinationValue());
		String selectedDirectoryName = dialog.open();

		if (selectedDirectoryName != null) {
			setError(null, null);
			setDestinationValue(selectedDirectoryName);
		}
	}

	/**
	 * Answer the contents of self's destination specification widget
	 *
	 * @return java.lang.String
	 */
	protected String getDestinationValue() {
		return destinationNameField.getText().trim();
	}

	/**
	 * Set the contents of the receivers destination specification widget to the passed value
	 *
	 */
	protected void setDestinationValue(String value) {
		destinationNameField.setText(value);
	}

	/** Get the user-selection of Projects. */
	protected List<IProject> getChosenProjects() {
		return projects.entrySet().stream()
				// Filter selected projects
				.filter(entry -> entry.getValue().booleanValue())
				// Map to project (key)
				.map(entry -> entry.getKey())
				.collect(Collectors.toList());
	}

	@Override
	protected boolean validateSourceGroup() {
		Object[] checked = choiceList.getCheckedElements();
		boolean someSelectionPresent = checked != null && checked.length > 0;
		// go to npmExportWizard and check the projects for errors.
		try {
			// clearError Flag
			clearErrorFlag();
			((NpmExportWizard) getWizard()).updateProjectsToExportSelection(checked);

		} catch (Exception e) {
			e.printStackTrace();
			Throwable t = (e instanceof InvocationTargetException) ? ((InvocationTargetException) e)
					.getTargetException() : e;
			setError("Error validating projects.", t);
			return false;

		}

		// if any error, then dont give ok here.
		return someSelectionPresent && !errorFlag;
	}

	@Override
	protected boolean validateDestinationGroup() {
		if (getDestinationValue() == null || getDestinationValue().isEmpty()) {
			return false;
		}
		if (!new File(getDestinationValue()).exists()) {
			return false;
		}
		return true;
	}

	private void setError(String shortMsg, Throwable t) {
		if (shortMsg == null) {
			errorText.getParent().setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
			errorText.setText("");
			setErrorMessage(null);
			clearErrorFlag();
		} else {
			setErrorMessage(shortMsg);
			errorText.getParent().setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
			errorText.setText(t.getMessage());
			setErrorFlag();
		}
	}

	/**
	 * @return state of compress contents check box
	 */
	public boolean getShouldPackAsTarball() {
		return compressContentsCheckbox.getSelection();
	}

	@Override
	protected void internalSaveWidgetValues() {

		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			// update directory names history
			String[] directoryNames = settings
					.getArray(STORE_EXPORT_DESTINATION_FOLDERS_ID);
			if (directoryNames == null) {
				directoryNames = new String[0];
			}

			directoryNames = addToHistory(directoryNames, getDestinationValue());
			settings.put(STORE_EXPORT_DESTINATION_FOLDERS_ID, directoryNames);

			// store checkbox - compress
			settings.put(STORE_EXPORT_COMPRESS_CONTENTS_ID, compressContentsCheckbox.getSelection());
		}

	}

	@Override
	protected void restoreWidgetValues() {
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			String[] directoryNames = settings
					.getArray(STORE_EXPORT_DESTINATION_FOLDERS_ID);
			if (directoryNames == null || directoryNames.length == 0) {
				// ie.- no settings stored
			} else {

				// destination
				setDestinationValue(directoryNames[0]);
				for (int i = 0; i < directoryNames.length; i++) {
					addDestinationItem(directoryNames[i]);
				}
			}
			compressContentsCheckbox.setSelection(settings.getBoolean(STORE_EXPORT_COMPRESS_CONTENTS_ID));
		}
	}

	/**
	 * @param string
	 */
	private void addDestinationItem(String string) {
		destinationNameField.add(string);
	}

	/**
	 * Called, if overall export succeeded.
	 */
	public void finish() {
		// store values in success case.
		internalSaveWidgetValues();
	}

	/**
	 * @param asErrorText
	 */
	public void setErrorText(String asErrorText) {
		if (errorText != null) {
			errorText.setText(asErrorText);
			if (asErrorText.trim().length() > 0)
				setErrorFlag();
		}
	}

	/**
	 *
	 */
	private void clearErrorFlag() {
		errorFlag = false;
		setErrorText("");
	}

	private void setErrorFlag() {
		errorFlag = true;
	}

}
