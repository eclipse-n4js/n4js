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
package org.eclipse.n4js.ui.export;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;

/**
 * Mostly adapted from {@link org.eclipse.ui.internal.wizards.datatransfer.WizardArchiveFileResourceExportPage1} and its
 * supertypes.
 */
@SuppressWarnings("restriction")
public abstract class AbstractExportToSingleFileWizardPage extends WizardPage implements Listener, IOverwriteQuery {

	private static final int SIZING_TEXT_FIELD_WIDTH = 250;

	private static final int COMBO_HISTORY_LENGTH = 5;

	private Combo destinationNameField;

	private Button destinationBrowseButton;

	private Button overwriteExistingFilesCheckbox;

	/** The initial selection, used to retrieve the main file and project */
	protected final IStructuredSelection initialResourceSelection;

	/** The list viewer with the projects, created in {@link #createProjectList(Composite)} */
	protected CheckboxTableViewer listViewer;

	@Inject
	private IWorkbench workbench;

	@Inject
	private IWorkspace workspace;

	@Inject
	private IN4JSEclipseCore n4jsCore;

	/**
	 * Create an instance of this class. It may be configured with a selection of {@link IResource resources}. This
	 * constraint is declared in the plugin.xml.
	 *
	 * @param selection
	 *            the selection which contains IResources
	 */
	public AbstractExportToSingleFileWizardPage(IStructuredSelection selection, String name, String title,
			String description) {
		super(name);
		this.initialResourceSelection = selection;
		setTitle(title);
		setDescription(description);
	}

	/**
	 * ID for storing the content of the destination when the page was closed, in order to recreate the content the next
	 * time the page is shown.
	 */
	protected abstract String getStoreDestinationNamesID();

	/**
	 * ID for storing the value of the overwrite flag when the page was closed, in order to recreate the content the
	 * next time the page is shown.
	 */
	protected abstract String getStoreOverwriteExistingFilesID();

	/**
	 * The output suffix of the file to be exported to, e.g., ".nfar" or ".js".
	 */
	protected abstract String getOutputSuffix();

	/**
	 * Creates the operation actually performing the export, to be implemented by subclasses.
	 */
	protected abstract AbstractExportOperation createExportOperation(File archiveFile,
			IFile mainFile,
			IN4JSEclipseProject eclipseProject);

	/**
	 * Adds an entry to a history, while taking care of duplicate history items and excessively long histories. The
	 * assumption is made that all histories should be of length
	 * <code>WizardDataTransferPage.COMBO_HISTORY_LENGTH</code>.
	 *
	 * @param history
	 *            the current history
	 * @param newEntry
	 *            the entry to add to the history
	 */
	private String[] addToHistory(String[] history, String newEntry) {
		List<String> l = new ArrayList<>(Arrays.asList(history));
		addToHistory(l, newEntry);
		String[] r = new String[l.size()];
		l.toArray(r);
		return r;
	}

	/**
	 * Adds an entry to a history, while taking care of duplicate history items and excessively long histories. The
	 * assumption is made that all histories should be of length <code>COMBO_HISTORY_LENGTH</code>.
	 *
	 * @param history
	 *            the current history
	 * @param newEntry
	 *            the entry to add to the history
	 */
	private void addToHistory(List<String> history, String newEntry) {
		history.remove(newEntry);
		history.add(0, newEntry);

		// since only one new item was added, we can be over the limit
		// by at most one item
		if (history.size() > COMBO_HISTORY_LENGTH) {
			history.remove(COMBO_HISTORY_LENGTH);
		}
	}

	/**
	 * Returns whether this page is complete. This determination is made based upon the current contents of this page's
	 * controls.
	 *
	 * @return <code>true</code> if this page is complete, and <code>false</code> if incomplete
	 * @see #validateSourceGroup()
	 * @see #validateDestinationGroup()
	 */
	private boolean determinePageCompletion() {
		boolean complete = validateSourceGroup() && validateDestinationGroup();

		// Avoid draw flicker by not clearing the error
		// message unless all is valid.
		if (complete) {
			setErrorMessage(null);
		}

		return complete;
	}

	/**
	 * The default implementation of this <code>IOverwriteQuery</code> method asks the user whether the existing
	 * resource at the given path should be overwritten.
	 *
	 * @param pathString
	 *            the path of the archive
	 * @return the user's reply: one of <code>"YES"</code>, <code>"NO"</code>, <code>"ALL"</code>, or
	 *         <code>"CANCEL"</code>
	 */
	@Override
	public String queryOverwrite(String pathString) {

		IPath path = Path.fromOSString(pathString);

		String messageString;
		// Break the message up if there is a file name and a directory
		// and there are at least 2 segments.
		if (path.getFileExtension() == null || path.segmentCount() < 2) {
			messageString = NLS.bind(IDEWorkbenchMessages.WizardDataTransfer_existsQuestion, pathString);
		} else {
			messageString = NLS.bind(IDEWorkbenchMessages.WizardDataTransfer_overwriteNameAndPathQuestion,
					path.lastSegment(),
					path.removeLastSegments(1).toOSString());
		}

		final MessageDialog dialog = new MessageDialog(getContainer()
				.getShell(), IDEWorkbenchMessages.Question,
				null, messageString, MessageDialog.QUESTION, new String[] {
			IDialogConstants.YES_LABEL,
			IDialogConstants.YES_TO_ALL_LABEL,
			IDialogConstants.NO_LABEL,
			IDialogConstants.NO_TO_ALL_LABEL,
			IDialogConstants.CANCEL_LABEL }, 0) {
			@Override
			protected int getShellStyle() {
				return super.getShellStyle() | SWT.SHEET;
			}
		};
		String[] response = new String[] { YES, ALL, NO, NO_ALL, CANCEL };
		// run in syncExec because callback is from an operation,
		// which is probably not running in the UI thread.
		getControl().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				dialog.open();
			}
		});
		return dialog.getReturnCode() < 0 ? CANCEL : response[dialog.getReturnCode()];
	}

	/**
	 * Displays a Yes/No question to the user with the specified message and returns the user's response.
	 *
	 * @param message
	 *            the question to ask
	 * @return <code>true</code> for Yes, and <code>false</code> for No
	 */
	private boolean queryYesNoQuestion(String message) {
		MessageDialog dialog = new MessageDialog(getContainer().getShell(),
				IDEWorkbenchMessages.Question,
				(Image) null, message, MessageDialog.NONE,
				new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL }, 0) {
			@Override
			protected int getShellStyle() {
				return super.getShellStyle() | SWT.SHEET;
			}
		};
		return dialog.open() == 0;
	}

	/**
	 * Restores control settings that were saved in the previous instance of this page.
	 */
	private void restoreWidgetValues() {
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			String[] directoryNames = settings.getArray(getStoreDestinationNamesID());
			if (directoryNames == null) {
				return; // ie.- no settings stored
			}

			// destination
			setDestinationValue(directoryNames[0]);
			for (int i = 0; i < directoryNames.length; i++) {
				addDestinationItem(directoryNames[i]);
			}

			// options
			overwriteExistingFilesCheckbox.setSelection(settings.getBoolean(getStoreOverwriteExistingFilesID()));
		}
	}

	/**
	 * Hook method for saving widget values for restoration by the next instance of this class.
	 */
	private void saveWidgetValues() {
		// update directory names history
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			String[] directoryNames = settings.getArray(getStoreDestinationNamesID());
			if (directoryNames == null) {
				directoryNames = new String[0];
			}

			directoryNames = addToHistory(directoryNames, getTargetDirectory());
			settings.put(getStoreDestinationNamesID(), directoryNames);

			// options
			settings.put(getStoreOverwriteExistingFilesID(), overwriteExistingFilesCheckbox.getSelection());
		}
	}

	/**
	 * Determine if the page is complete and update the page appropriately.
	 */
	private void updatePageCompletion() {
		boolean pageComplete = determinePageCompletion();
		setPageComplete(pageComplete);
		if (pageComplete) {
			setErrorMessage(null);
		}
	}

	/**
	 * Updates the enable state of this page's controls.
	 */
	private void updateWidgetEnablements() {
		boolean pageComplete = determinePageCompletion();
		setPageComplete(pageComplete);
		if (pageComplete) {
			setMessage(null);
		}
	}

	/**
	 * Answer a boolean indicating whether the receivers destination specification widgets currently all contain valid
	 * values.
	 */
	private boolean validateDestinationGroup() {
		String destinationValue = getTargetDirectory();
		if (destinationValue.length() == 0) {
			setMessage(destinationEmptyMessage());
			return false;
		}

		String conflictingContainer = getConflictingContainerNameFor(destinationValue);
		if (conflictingContainer == null) {
			// // no error message, but warning may exists
			// String threatenedContainer = getOverlappingProjectName(destinationValue);
			// if (threatenedContainer == null)
			setMessage(null);
			// else
			// setMessage(NLS.bind(NFARExportMessages.FileExport_damageWarning, threatenedContainer), WARNING);

		} else {
			setErrorMessage(NLS.bind(N4ExportMessages.FileExport_conflictingContainer, conflictingContainer));
			giveFocusToDestination();
			return false;
		}

		return true;
	}

	/**
	 * Returns whether this page's source specification controls currently all contain valid values.
	 *
	 * @return <code>true</code> to indicate validity of all controls in the source specification group
	 */
	protected boolean validateSourceGroup() {
		// there must be some resources selected for Export
		boolean isValid = listViewer.getCheckedElements().length == 1;
		if (!isValid) {
			setErrorMessage(N4ExportMessages.FileExport_noneSelected);
			isValid = false;
		} else {
			setErrorMessage(null);
		}
		return isValid;
	}

	/**
	 * Create the options specification widgets.
	 */
	protected void createOptionsGroup(Composite parent) {
		// options group
		Group optionsGroup = new Group(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		optionsGroup.setLayout(layout);
		optionsGroup.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL));
		optionsGroup.setText(IDEWorkbenchMessages.WizardExportPage_options);
		optionsGroup.setFont(parent.getFont());

		createOptionsGroupButtons(optionsGroup);

	}

	/**
	 * Display an error dialog with the specified message.
	 */
	private void displayErrorDialog(String message) {
		MessageDialog.open(MessageDialog.ERROR, getContainer().getShell(),
				getErrorDialogTitle(), message, SWT.SHEET);
	}

	/**
	 * Display an error dialog with the information from the supplied exception.
	 */
	private void displayErrorDialog(Throwable exception) {
		String message = exception.getMessage();
		// Some system exceptions have no message
		if (message == null) {
			message = NLS.bind(IDEWorkbenchMessages.WizardDataTransfer_exceptionMessage, exception);
		}
		displayErrorDialog(message);
	}

	/**
	 * Get the title for an error dialog.
	 */
	private String getErrorDialogTitle() {
		return IDEWorkbenchMessages.WizardExportPage_errorDialogTitle;
	}

	/**
	 * Add the passed value to self's destination widget's history
	 */
	private void addDestinationItem(String value) {
		destinationNameField.add(value);
	}

	/**
	 * Create the export destination specification widgets
	 */
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
		destinationLabel.setText(getTargetLabel());
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

		// destination browse button
		destinationBrowseButton = new Button(destinationSelectionGroup,
				SWT.PUSH);
		destinationBrowseButton.setText(N4ExportMessages.DataTransfer_browse);
		destinationBrowseButton.addListener(SWT.Selection, this);
		destinationBrowseButton.setFont(font);
		setButtonLayoutData(destinationBrowseButton);

		// new Label(parent, SWT.NONE); // vertical spacer
	}

	/**
	 * Creates the export options group controls.
	 */
	private void createOptionsGroupButtons(Group optionsGroup) {

		Font font = optionsGroup.getFont();
		createOverwriteExisting(optionsGroup, font);
	}

	/**
	 * Create the button for checking if we should ask if we are going to overwrite existing files.
	 */
	private void createOverwriteExisting(Group optionsGroup, Font font) {
		// overwrite... checkbox
		overwriteExistingFilesCheckbox = new Button(optionsGroup, SWT.CHECK | SWT.LEFT);
		overwriteExistingFilesCheckbox.setText(N4ExportMessages.ExportFile_overwriteExisting);
		overwriteExistingFilesCheckbox.setFont(font);
	}

	/**
	 * Attempts to ensure that the specified directory exists on the local file system. Answers a boolean indicating
	 * success.
	 */
	private boolean ensureDirectoryExists(File directory) {
		if (!directory.exists()) {
			if (!queryYesNoQuestion(N4ExportMessages.DataTransfer_createTargetDirectory)) {
				return false;
			}

			if (!directory.mkdirs()) {
				displayErrorDialog(N4ExportMessages.DataTransfer_directoryCreationError);
				giveFocusToDestination();
				return false;
			}
		}
		return true;
	}

	/**
	 * If the target for export does not exist then attempt to create it. Answer a boolean indicating whether the target
	 * exists (ie.- if it either pre-existed or this method was able to create it)
	 */
	private boolean ensureTargetDirectoryIsValid(File targetDirectory) {
		if (targetDirectory.exists() && !targetDirectory.isDirectory()) {
			displayErrorDialog(DataTransferMessages.FileExport_directoryExists);
			giveFocusToDestination();
			return false;
		}

		return ensureDirectoryExists(targetDirectory);
	}

	/**
	 * The Finish button was pressed. Try to do the required work now and answer a boolean indicating success. If false
	 * is returned then the wizard will not close.
	 */
	public boolean finish() {
		IProject selectedProject = (IProject) listViewer.getCheckedElements()[0];
		if (!ensureTargetIsValid()) {
			return false;
		}

		// Save dirty editors if possible but do not stop if not all are saved
		saveDirtyEditors();
		// about to invoke the operation so save our state
		saveWidgetValues();

		Optional<? extends IN4JSEclipseProject> eclipseProjectOpt = n4jsCore.create(selectedProject);

		if (eclipseProjectOpt.isPresent()) {
			String targetDirectory = getTargetDirectory();
			File archiveFile = new File(targetDirectory, getTargetFileName());

			// only certain Exporters demand the first selected element to be a 'file', which will be denoted as
			// main-file or the thing to execute (ObjectiveCwrapper),
			// others don't care. If the export is triggered on a project the first element is not an IFile !
			IFile mainFile = null;
			Object firstElement = initialResourceSelection.getFirstElement();
			if (firstElement instanceof IFile)
				mainFile = (IFile) firstElement;

			try {
				boolean result = executeExportOperation(createExportOperation(archiveFile, mainFile,
						eclipseProjectOpt.get()));
				return result;
			} finally {
				String overlappingProject = getOverlappingProjectName(targetDirectory);
				try {
					workspace.getRoot().getProject(overlappingProject).refreshLocal(IResource.DEPTH_INFINITE, null);
				} catch (CoreException e) {
					// ignore
				}
			}
		}
		return true;
	}

	/**
	 * Answer the string to display in the receiver as the destination type
	 */
	private String getTargetLabel() {
		return N4ExportMessages.FileExport_toDirectory;
	}

	/**
	 * Answer the contents of self's destination specification widget.
	 */
	private String getTargetDirectory() {
		String destinationText = destinationNameField.getText().trim();
		return destinationText;
	}

	/**
	 * Set the current input focus to self's destination entry field
	 */
	private void giveFocusToDestination() {
		destinationNameField.setFocus();
	}

	/**
	 * Open an appropriate destination browser so that the user can specify a source to import from
	 */
	private void handleDestinationBrowseButtonPressed() {
		DirectoryDialog dialog = new DirectoryDialog(getContainer().getShell(),
				SWT.SAVE | SWT.SHEET);
		dialog.setMessage(N4ExportMessages.FileExport_selectDestinationMessage);
		dialog.setText(N4ExportMessages.FileExport_selectDestinationTitle);
		dialog.setFilterPath(getTargetDirectory());
		String selectedDirectoryName = dialog.open();

		if (selectedDirectoryName != null) {
			setErrorMessage(null);
			setDestinationValue(selectedDirectoryName);
		}
	}

	/**
	 * Handle all events and enablements for widgets in this page
	 *
	 * @param e
	 *            Event
	 */
	@Override
	public void handleEvent(Event e) {
		Widget source = e.widget;

		if (source == destinationBrowseButton) {
			handleDestinationBrowseButtonPressed();
		}

		updatePageCompletion();
	}

	/**
	 * Set the contents of the receivers destination specification widget to the passed value
	 *
	 */
	private void setDestinationValue(String value) {
		destinationNameField.setText(value);
	}

	/**
	 * Get the message used to denote an empty destination.
	 */
	private String destinationEmptyMessage() {
		return N4ExportMessages.ArchiveExport_destinationEmpty;
	}

	/**
	 * Returns the name of a container with a location that encompasses targetDirectory. Returns null if there is no
	 * conflict.
	 *
	 * @param targetDirectory
	 *            the path of the directory to check.
	 * @return the conflicting container name or <code>null</code>
	 */
	private String getConflictingContainerNameFor(String targetDirectory) {

		IPath rootPath = ResourcesPlugin.getWorkspace().getRoot().getLocation();
		IPath testPath = new Path(targetDirectory);
		// cannot export into workspace root
		if (testPath.equals(rootPath))
			return rootPath.lastSegment();

		// Are they the same?
		if (testPath.matchingFirstSegments(rootPath) == rootPath.segmentCount()) {
			String firstSegment = testPath.removeFirstSegments(rootPath.segmentCount()).segment(0);
			if (!Character.isLetterOrDigit(firstSegment.charAt(0)))
				return firstSegment;
		}

		return null;

	}

	/**
	 * Returns the name of a {@link IProject} with a location that includes targetDirectory. Returns null if there is no
	 * such {@link IProject}.
	 *
	 * @param targetDirectory
	 *            the path of the directory to check.
	 * @return the overlapping project name or <code>null</code>
	 */
	private String getOverlappingProjectName(String targetDirectory) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IPath testPath = new Path(targetDirectory);
		IContainer[] containers = root.findContainersForLocationURI(testPath.makeAbsolute().toFile().toURI());
		if (containers.length > 0) {
			return containers[0].getProject().getName();
		}
		return null;
	}

	private void setupBasedOnInitialSelections() {
		listViewer.setSelection(initialResourceSelection);
		if (initialResourceSelection != null && !initialResourceSelection.isEmpty()) {
			Object first = initialResourceSelection.getFirstElement();
			if (first instanceof IResource) {
				listViewer.setChecked(((IResource) first).getProject(), true);
				Object[] checkedElements = listViewer.getCheckedElements();
				if (checkedElements.length == 1) {
					IProject selectedProject = (IProject) checkedElements[0];
					setDestinationValue(selectedProject.getLocation().append("lib").toOSString());
				}
			}
		}
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

		createExportGoups(composite);

		restoreWidgetValues(); // ie.- subclass hook
		if (initialResourceSelection != null) {
			setupBasedOnInitialSelections();
		}

		updateWidgetEnablements();
		setPageComplete(determinePageCompletion());
		setErrorMessage(null); // should not initially have error message

		setControl(composite);

		giveFocusToDestination();
	}

	/**
	 * Create export groups with projecdt list, destination field and option groups. May be overridden by sub classes if
	 * other groups are to be shown.
	 *
	 * @param composite
	 *            the parent of the groups
	 */
	protected void createExportGoups(Composite composite) {
		createProjectList(composite);
		createDestinationGroup(composite);
		createOptionsGroup(composite);
	}

	/**
	 * Returns a boolean indicating whether the directory portion of the passed pathname is valid and available for use.
	 */
	protected boolean ensureTargetDirectoryIsValid(String fullPathname) {
		return ensureTargetDirectoryIsValid(new File(fullPathname));
	}

	/**
	 * Returns a boolean indicating whether the passed File handle is is valid and available for use.
	 */
	protected boolean ensureTargetFileIsValid(File targetFile) {
		if (targetFile.exists() && targetFile.isDirectory()) {
			displayErrorDialog(N4ExportMessages.Export_mustBeFile);
			giveFocusToDestination();
			return false;
		}

		if (targetFile.exists()) {
			if (targetFile.canWrite()) {
				if (!this.overwriteExistingFilesCheckbox.getSelection()
						&& !queryYesNoQuestion(N4ExportMessages.Export_alreadyExists)) {
					return false;
				}
			} else {
				displayErrorDialog(N4ExportMessages.Export_alreadyExistsError);
				giveFocusToDestination();
				return false;
			}
		}
		return true;
	}

	/**
	 * Ensures that the target output file and its containing directory are both valid and able to be used. Answer a
	 * boolean indicating validity.
	 */
	private boolean ensureTargetIsValid() {
		String targetDir = getTargetDirectory();

		// check the selected path itself
		if (!ensureTargetDirectoryIsValid(targetDir)) {
			return false;
		}
		// check the synthesized target file name
		String fileName = getTargetFileName();
		if (!ensureTargetFileIsValid(new File(targetDir, fileName))) {
			return false;
		}

		return true;
	}

	/**
	 * Returns the target file name computed from the input of the page. May be overridden by subclasses if target file
	 * name is to be calculated differently.
	 */
	protected String getTargetFileName() {
		IProject selectedProject = (IProject) listViewer.getCheckedElements()[0];
		return selectedProject.getName() + getOutputSuffix();
	}

	/**
	 * Export the passed resource and recursively export all of its child resources (iff it's a container). Answer a
	 * boolean indicating success.
	 */
	private boolean executeExportOperation(AbstractExportOperation op) {
		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			displayErrorDialog(e.getTargetException());
			return false;
		}

		IStatus status = op.getStatus();
		if (!status.isOK()) {
			ErrorDialog.openError(getContainer().getShell(),
					N4ExportMessages.DataTransfer_exportProblems,
					null, // no special message
					status);
			return false;
		}

		return true;
	}

	/**
	 * Creates the checkbox tree and list for selecting resources.
	 *
	 * @param parent
	 *            the parent control
	 */
	protected void createProjectList(Composite parent) {

		// create the input element, which has the root resource
		// as its only child
		List<IProject> input = new ArrayList<>();
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		for (int i = 0; i < projects.length; i++) {
			if (projects[i].isOpen()) {
				input.add(projects[i]);
			}
		}

		listViewer = CheckboxTableViewer.newCheckList(parent, SWT.TOP
				| SWT.BORDER);
		GridData data = new GridData(GridData.FILL_BOTH);
		listViewer.getTable().setLayoutData(data);

		listViewer.setLabelProvider(WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider());
		listViewer.setContentProvider(getContentProvider());
		listViewer.setComparator(new ViewerComparator());

		// check for initial modification to avoid work if no changes are made
		listViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				if (event.getChecked()) {
					for (Object currentlyChecked : listViewer.getCheckedElements()) {
						if (currentlyChecked != event.getElement()) {
							listViewer.setChecked(currentlyChecked, false);
						}
					}
				}
				updateWidgetEnablements();
			}
		});
		listViewer.setInput(workspace);
	}

	/**
	 * Returns a content provider for the list dialog. It will return all projects in the workspace except the given
	 * project, plus any projects referenced by the given project which do no exist in the workspace.
	 *
	 * @return the content provider that shows the project content
	 */
	private IStructuredContentProvider getContentProvider() {
		return new WorkbenchContentProvider() {
			@Override
			public Object[] getChildren(Object o) {
				if (!(o instanceof IWorkspace)) {
					return new Object[0];
				}

				// Collect all the projects in the workspace except the given project
				IProject[] projects = ((IWorkspace) o).getRoot().getProjects();
				List<IProject> applicableProjects = Lists.newArrayList();
				Optional<? extends IN4JSEclipseProject> projectOpt = null;
				for (IProject candidate : projects) {
					projectOpt = n4jsCore.create(candidate);
					if (projectOpt.isPresent() && projectOpt.get().exists()) {
						applicableProjects.add(candidate);
					}
				}
				return applicableProjects.toArray(new IProject[applicableProjects.size()]);
			}
		};
	}

	/**
	 * Save any editors that the user wants to save before export.
	 *
	 * @return boolean if the save was successful.
	 */
	private boolean saveDirtyEditors() {
		return workbench.saveAllEditors(true);
	}

}
