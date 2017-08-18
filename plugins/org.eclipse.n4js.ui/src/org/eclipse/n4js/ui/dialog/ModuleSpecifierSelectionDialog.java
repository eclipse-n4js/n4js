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

import static org.eclipse.n4js.ui.wizard.workspace.WorkspaceWizardValidatorUtils.isValidFolderName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.ui.wizard.workspace.WorkspaceWizardValidatorUtils;
import org.eclipse.n4js.utils.OSInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * Browse dialog to select and create module folders inside of a given source folder location.
 *
 * Offers create functionality what means that the user is able to create new module folders. Created module containers
 * are directly written to the file system.
 *
 * Returns values of type {@link String} that are a representation of the path of the selected module folder. The
 * specified file may not exist, but it is ensured that all folders in the path exist.
 */
public class ModuleSpecifierSelectionDialog extends CustomElementSelectionDialog {

	private static Logger LOGGER = Logger.getLogger(ModuleSpecifierSelectionDialog.class);

	private final static String MODULE_ELEMENT_NAME = "Module:";
	private final static String CREATE_FOLDER_LABEL = "Create Folder";

	/** Create a new folder dialog */
	private static final String CREATE_A_NEW_FOLDER_MESSAGE = "Enter the name of the new folder";
	private static final String CREATE_A_NEW_FOLDER_TITLE = "Create a new folder";

	/** Failed to create folder dialog */
	private static final String FAILED_TO_CREATE_FOLDER_TITLE = "Failed to create the folders";
	private static final String FAILED_TO_CREATE_FOLDER_MESSAGE = "The folder %s couldn't be created: %s";

	/** Non existing module location dialog */
	private static final String NON_EXISTING_MODULE_LOCATION_TITLE = "Non-existing module location";
	private static final String NON_EXISTING_MODULE_LOCATION_MESSAGE = "The module specifier you entered does not exist yet, do you want to create it in the file system?";

	/** Specifier overlaps dialog */
	private static final String SPECIFIER_OVERLAPS_WITH_FILE_TITLE = "Invalid module specifier";
	private static final String SPECIFIER_OVERLAPS_WITH_FILE_MESSAGE = "Your module specifier %s overlaps with the path of the file %s.";

	private final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
	private final IContainer sourceFolder;
	private final IContainer treeRoot;

	private final ModuleSpecifierValidator inputValidator = new ModuleSpecifierValidator();

	private String defaultFileExtension = N4JSGlobals.N4JS_FILE_EXTENSION;

	/** The module name extracted from the initial selection */
	private String initialModuleName = "";

	/**
	 * Create the dialog.
	 *
	 * <p>
	 * Note: The model should have a valid source folder path as this is the root folder for this browse dialog. If the
	 * source folder path doesn't exist yet this dialog will be empty.
	 * </p>
	 *
	 * @param parent
	 *            Parent Shell  
	 * @param sourceFolder
	 *            The source folder to browse in for modules and module folders
	 *
	 *
	 */
	public ModuleSpecifierSelectionDialog(Shell parent, IPath sourceFolder) {
		super(parent, MODULE_ELEMENT_NAME, CREATE_FOLDER_LABEL);
		this.setTitle("Select a module");

		this.setInputValidator(inputValidator);

		IPath parentPath = sourceFolder.removeLastSegments(1);
		IContainer sourceFolderParent = containerForPath(parentPath);
		IFolder workspaceSourceFolder = workspaceRoot
				.getFolder(sourceFolder);

		// Use parent of source folder as root to show source folder itself in the tree
		this.treeRoot = sourceFolderParent;

		this.sourceFolder = workspaceSourceFolder;
		this.addFilter(new ModuleFolderFilter(this.sourceFolder.getFullPath()));

		this.setAutoExpandLevel(2);
		// Show the status line above the buttons
		this.setStatusLineAboveButtons(true);
	}

	@Override
	public void setInitialSelection(Object element) {
		super.setInitialSelection(element);

	}

	@Override
	public int open() {
		if (getInitialElementSelections().size() > 0) {
			Object initialSelection = getInitialElementSelections().get(0);

			// Preprocess initial string selection and replace it with its file system equivalent
			if (initialSelection instanceof String) {
				setInitialSelection(processInitialSelection((String) initialSelection));
			}
		}
		return super.open();
	}

	/**
	 * Sets the default file extension which is used for created files.
	 *
	 * This means, that the user still is able to select files with a different file extension. If he however specifies
	 * a not yet existing file, it will have the default file extension.
	 *
	 * @param defaultFileExtension
	 *            The extension to use by default
	 */
	public void setDefaultFileExtension(String defaultFileExtension) {
		this.defaultFileExtension = defaultFileExtension;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Control dialog = super.createDialogArea(parent);

		elementNameInput.setSuffix("." + this.defaultFileExtension);
		this.setInput(this.treeRoot);

		// Update the file extension whenever the dialog value selection changes (tree + element name)
		this.treeViewer.addSelectionChangedListener(selectionChange -> {
			updateFileExtension();

			// Also validate the element input, as the tree selection may change the element
			validateElementInput();

			// When selecting a file, use its name as element name
			Object selection = treeViewer.getStructuredSelection().getFirstElement();
			if (selection instanceof IFile) {
				String extensionFreeFileName = ((IFile) selection).getFullPath().removeFileExtension().lastSegment();
				elementNameInput.setText(extensionFreeFileName);
			}
		});
		this.elementNameInput.addPropertyChangeListener(change -> updateFileExtension());

		// Refire initial selection change
		this.treeViewer.setSelection(this.treeViewer.getSelection());

		if (!initialModuleName.isEmpty()) {
			elementNameInput.setText(initialModuleName);
		}

		validateElementInput();

		elementNameInput.setFocus();

		return dialog;
	}

	@Override
	protected void computeResult() {

		Object selection = treeViewer.getStructuredSelection().getFirstElement();

		if (selection == null) {
			return;
		}

		String moduleName = elementNameInput.getText();
		String moduleFileExtension = elementNameInput.getSuffix();

		// For selected files where the element name input equals the selected file
		if (selection instanceof IFile
				&& ((IFile) selection).getFullPath().removeFileExtension().lastSegment().equals(moduleName)) {
			// Use the selected file's path as result
			IPath fileSpec = sourceFolderRelativePath((IResource) selection);
			this.setResult(Arrays.asList(fileSpec.toString()));
			return;
		} else if (selection instanceof IResource) {
			// For files with different element name input value, use their container as basepath
			if (selection instanceof IFile) {
				selection = ((IFile) selection).getParent();
			}

			IFile moduleFile = ((IContainer) selection).getFile(new Path(moduleName + moduleFileExtension));
			this.setResult(
					Arrays.asList(moduleFile.getFullPath().makeRelativeTo(sourceFolder.getFullPath()).toString()));

			return;
		} else {
			updateError("Invalid selection type.");
		}
	}

	@Override
	protected void createPressed() {
		InputDialog dialog = new InputDialog(getShell(), CREATE_A_NEW_FOLDER_TITLE, CREATE_A_NEW_FOLDER_MESSAGE, "",
				new ModuleFolderValidator());
		dialog.open();

		Object selection = treeViewer.getStructuredSelection().getFirstElement();

		// Infer parent folder from selection
		IContainer parent;
		if (selection instanceof IFile) {
			parent = ((IFile) selection).getParent();
		} else if (selection instanceof IContainer) {
			parent = (IContainer) selection;
		} else { // Use the source folder as default
			parent = this.sourceFolder;
		}

		String dialogValue = dialog.getValue();

		if (OSInfo.isWindows()) {
			dialogValue = convertToUnixPath(dialogValue);
		}

		IPath folderPath = new Path(dialogValue);
		IContainer createdFolder = null;

		if (Window.OK == dialog.getReturnCode()) {
			ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(getShell());
			progressMonitorDialog.open();
			IProgressMonitor progressMonitor = progressMonitorDialog.getProgressMonitor();

			createdFolder = createFolderPath(folderPath, parent, null);

			progressMonitor.done();
			progressMonitorDialog.close();

			if (null != createdFolder) {
				treeViewer.setSelection(new StructuredSelection(createdFolder));
			}
		}

	}

	/** Return the container with the given path */
	private IContainer containerForPath(IPath path) {
		if (path.segmentCount() == 1) {
			return workspaceRoot.getProject(path.segment(0));
		} else {
			return workspaceRoot.getFolder(path);
		}
	}

	/**
	 * Processes the initial string selection.
	 *
	 * For existing resources it just sets the initial selection to the specified file system resource.
	 *
	 * In the case that the initial module specifier points to a non-existing location, a dialog is displayed which
	 * allows the user to automatically create not yet existing folders.
	 *
	 * @param initialModuleSpecifier
	 *            The module specifier
	 * @return A {@link IWorkbenchAdapter} adaptable object or null on failure
	 */
	private Object processInitialSelection(String initialModuleSpecifier) {

		IPath sourceFolderPath = sourceFolder.getFullPath();
		IPath initialModulePath = new Path(initialModuleSpecifier);

		// Use the root element source folder for an empty initial selection
		if (initialModuleSpecifier.isEmpty()) {
			return this.sourceFolder;
		}

		// Use the root element source folder for invalid module specifiers
		if (!WorkspaceWizardValidatorUtils.isValidFolderPath(initialModulePath)) {
			return this.sourceFolder;
		}

		// The project relative path of a module specifier
		IPath fullPath = sourceFolderPath.append(new Path(initialModuleSpecifier));

		// If the module specifier refers to an existing n4js resource
		if (!fullPath.hasTrailingSeparator()) {
			IFile n4jsModuleFile = workspaceRoot.getFile(fullPath.addFileExtension(N4JSGlobals.N4JS_FILE_EXTENSION));
			IFile n4jsdModuleFile = workspaceRoot.getFile(fullPath.addFileExtension(N4JSGlobals.N4JSD_FILE_EXTENSION));

			// Just use it as initial selection
			if (n4jsModuleFile.exists()) {
				return n4jsModuleFile;
			}
			if (n4jsdModuleFile.exists()) {
				return n4jsdModuleFile;
			}

		}

		//// Otherwise use the existing part of the path as initial selection:

		// If the module specifier specifies the module name, extract it and remove its segment.
		if (isModuleFileSpecifier(initialModulePath)) {
			initialModuleName = initialModulePath.lastSegment();
			initialModulePath = initialModulePath.removeLastSegments(1);
		}

		IResource selection = this.sourceFolder;

		// Accumulate path segments to search for the longest existing path
		IPath accumulatedPath = sourceFolderPath;

		// Collect the paths of all non-existing segments
		// These are relative to the last existing segment of the path
		List<IPath> nonExistingSegmentPaths = new ArrayList<>();

		for (Iterator<String> segmentIterator = Arrays.asList(initialModulePath.segments()).iterator(); segmentIterator
				.hasNext(); /**/) {

			accumulatedPath = accumulatedPath.append(segmentIterator.next());

			// Results in null if non-existing
			IResource nextSegmentResource = workspaceRoot.findMember(accumulatedPath);

			// If the current segment is an existing file and not the last specifier segment
			// show a file overlap error message
			if (null != nextSegmentResource && !(nextSegmentResource instanceof IContainer)
					&& segmentIterator.hasNext()) {

				MessageDialog.open(MessageDialog.ERROR, getShell(), SPECIFIER_OVERLAPS_WITH_FILE_TITLE,
						String.format(SPECIFIER_OVERLAPS_WITH_FILE_MESSAGE, initialModuleSpecifier, accumulatedPath),
						SWT.NONE);

				return selection;
			}

			// If the segment exist go ahead with the next one
			if (null != nextSegmentResource && nextSegmentResource.exists()) {
				selection = nextSegmentResource;
			} else { // If not add it to the list of non existing segments.
				nonExistingSegmentPaths.add(accumulatedPath.makeRelativeTo(selection.getFullPath()));
			}
		}

		// If any non-existing folders need to be created
		if (nonExistingSegmentPaths.size() > 0) {
			// Ask the user if he wants to create the missing folders
			boolean create = MessageDialog.open(MessageDialog.QUESTION, getShell(), NON_EXISTING_MODULE_LOCATION_TITLE,
					NON_EXISTING_MODULE_LOCATION_MESSAGE, SWT.NONE);

			// Create the missing folders
			if (create) {
				ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(getShell());
				progressMonitorDialog.open();
				IProgressMonitor progressMonitor = progressMonitorDialog.getProgressMonitor();

				IPath deepestPath = nonExistingSegmentPaths.get(nonExistingSegmentPaths.size() - 1);
				selection = createFolderPath(deepestPath, (IContainer) selection, progressMonitor);

				progressMonitor.done();
				progressMonitorDialog.close();
			}

		}
		return selection;
	}

	/**
	 * Creates the folder in the given container.
	 *
	 * @param name
	 *            The name of the new folder
	 * @param parent
	 *            The parent container
	 * @param monitor
	 *            The progress monitor. May be {@code null}.
	 * @throws CoreException
	 *             for {@link IFolder#create(boolean, boolean, IProgressMonitor)} exceptions
	 * @return The created folder
	 */
	private IFolder createFolder(String name, IContainer parent, IProgressMonitor monitor) throws CoreException {
		IFolder folder = parent.getFolder(new Path(name));
		folder.create(true, true, monitor);
		return folder;
	}

	/**
	 * Creates all non-existing segments of the given path.
	 *
	 * @param path
	 *            The path to create
	 * @param parent
	 *            The container in which the path should be created in
	 * @param monitor
	 *            A progress monitor. May be {@code null}
	 *
	 * @return The folder specified by the path
	 */
	private IContainer createFolderPath(IPath path, IContainer parent, IProgressMonitor monitor) {
		IContainer activeContainer = parent;

		if (null != monitor) {
			monitor.beginTask("Creating folders", path.segmentCount());
		}

		for (String segment : path.segments()) {
			IFolder folderToCreate = activeContainer.getFolder(new Path(segment));
			try {
				if (!folderToCreate.exists()) {
					createFolder(segment, activeContainer, monitor);
				}
				if (null != monitor) {
					monitor.worked(1);
				}
				activeContainer = folderToCreate;
			} catch (CoreException e) {
				LOGGER.error("Failed to create module folders.", e);
				MessageDialog.open(MessageDialog.ERROR, getShell(),
						FAILED_TO_CREATE_FOLDER_TITLE, String.format(FAILED_TO_CREATE_FOLDER_MESSAGE,
								folderToCreate.getFullPath().toString(), e.getMessage()),
						SWT.NONE);
				break;
			}
		}
		return activeContainer;
	}

	/**
	 * Returns true if the path is a module file specifier.
	 *
	 * <p>
	 * Note: A module file specifier doesn't only specify the container of the new class file but also the file in which
	 * the class is contained in.
	 * </p>
	 *
	 * @param path
	 *            The path to examine
	 * @return True if the path is a module file specifier
	 */
	private static boolean isModuleFileSpecifier(IPath path) {
		if (path.segmentCount() < 1) {
			return false;
		}
		String stringPath = path.toString();
		return stringPath.charAt(stringPath.length() - 1) != '/';
	}

	/**
	 * Updates the file extension according to the current selection. (tree + element name input)
	 */
	private void updateFileExtension() {
		Object selection = treeViewer.getStructuredSelection().getFirstElement();
		String elementFileName = elementNameInput.getText();

		if (null == selection) {
			setFileExtension(defaultFileExtension);
		}

		// If an existing file is selected and element name input equals its name
		if (selection instanceof IFile && elementFileName.equals(((IFile) selection).getName())) {
			// Use the file's extension
			setFileExtension(((IFile) selection).getFileExtension());
		} else if (selection instanceof IResource) {
			// Otherwise compute the path of the selected element
			IPath basepath;
			if (selection instanceof IFile) {
				basepath = ((IFile) selection).getParent().getFullPath();
			} else {
				basepath = ((IResource) selection).getFullPath();
			}
			IPath pathOfSelection = basepath.append(elementFileName);

			IFile n4jsFile = workspaceRoot.getFile(pathOfSelection.addFileExtension(N4JSGlobals.N4JS_FILE_EXTENSION));
			IFile n4jsdFile = workspaceRoot.getFile(pathOfSelection.addFileExtension(N4JSGlobals.N4JSD_FILE_EXTENSION));

			// If a n4js or n4jsd file with the specified location exists, use the proper file extension. Otherwise use
			// the default file extension.
			if (n4jsdFile.exists()) {
				setFileExtension(N4JSGlobals.N4JSD_FILE_EXTENSION);
			} else if (n4jsFile.exists()) {
				setFileExtension(N4JSGlobals.N4JS_FILE_EXTENSION);
			} else {
				setFileExtension(defaultFileExtension);
			}
		}
	}

	private void setFileExtension(String fileExtension) {
		elementNameInput.setSuffix("." + fileExtension);
	}

	/**
	 * Returns the source folder relative path of a given resource
	 *
	 * @param resource
	 *            The file system resource
	 * @return The systems path relative to the source folder
	 */
	private IPath sourceFolderRelativePath(IResource resource) {
		IPath path = resource.getFullPath().makeRelativeTo(this.sourceFolder.getFullPath());

		if (path.toString().equals(IPath.SEPARATOR)) {
			return new Path("");
		}
		return path;
	}

	/**
	 * Converts backslash separated or mixed separator paths to a pure slash separated Unix paths.
	 *
	 * @param path
	 *            The path to convert
	 * @return A slash separated path
	 */
	private static String convertToUnixPath(String path) {
		return path.replaceAll("[\\\\]", "/");
	}

	/**
	 * Filter to only show module containers (=folders) with a specified base path.
	 *
	 * <p>
	 * Note: The prefix can be used to limit the scope of the selection. E.g. the prefix "/project/src" would limit the
	 * selection only to elements like "/project/src/A","/project/src/B","/project/src/A/C" etc.
	 *
	 * Note that also the prefix folder itself will be shown. In this example this means that there is a root folder
	 * called "src"
	 * </p>
	 */
	private static class ModuleFolderFilter extends ViewerFilter {

		private final IPath prefix;

		/**
		 * Create a filter for given prefix path condition
		 *
		 * @param prefix
		 *            Path prefix
		 */
		public ModuleFolderFilter(IPath prefix) {
			this.prefix = prefix;
		}

		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			if (element instanceof IContainer) {
				IPath p = ((IContainer) element).getFullPath();
				return this.prefix.isPrefixOf(p);
			}
			if (element instanceof IFile) {
				IPath p = ((IFile) element).getFullPath();
				return isN4JSResource(p) && this.prefix.isPrefixOf(p);
			}
			return false;
		}

		private boolean isN4JSResource(IPath path) {
			String fileExtension = path.getFileExtension();
			return null != fileExtension &&
					(fileExtension.equals(N4JSGlobals.N4JS_FILE_EXTENSION)
							|| fileExtension.equals(N4JSGlobals.N4JSD_FILE_EXTENSION));
		}

	}

	/**
	 * An input validator to validate module folder names
	 */
	private static final class ModuleFolderValidator implements IInputValidator {
		@Override
		public String isValid(String text) {
			String textToValidate = text;

			// Convert mixed and windows separated paths to unix paths for validation
			if (OSInfo.isWindows()) {
				textToValidate = convertToUnixPath(text);
			}

			if (textToValidate.isEmpty()) {
				return "The module folder must not be empty";
			}
			if (!WorkspaceWizardValidatorUtils.isValidFolderPath(new Path(textToValidate))) {
				return "The module name is not a valid file system name";
			}
			return null;
		}
	}

	/**
	 * An input validator to validate the selected module specifier.
	 *
	 * The validator validates the module name as well as the selected container.
	 */
	private final class ModuleSpecifierValidator implements IInputValidator {
		@Override
		public String isValid(String newText) {
			IPath path = new Path(newText);
			String fileExtension = path.getFileExtension();
			String moduleName = path.removeFileExtension().lastSegment();

			if (path.removeFileExtension().segmentCount() < 1 || moduleName.isEmpty()) {
				return "The module name must not be empty.";
			}

			if (!isValidFolderName(path.removeFileExtension().toString())) {
				return "The module name is not a valid file system name.";
			}

			if (fileExtension == null) {
				return "The module name needs to have a valid N4JS file extension.";
			}
			if (!(fileExtension.equals(N4JSGlobals.N4JS_FILE_EXTENSION) ||
					fileExtension.equals(N4JSGlobals.N4JSD_FILE_EXTENSION))) {
				return "Invalid file extension.";
			}
			if (!isModuleFileSpecifier(path)) {
				return "Invalid module file specifier.";
			}
			if (path.segmentCount() > 1) {
				return IPath.SEPARATOR + " is not allowed in a module file specifier.";
			}
			if (treeViewer.getStructuredSelection().getFirstElement() == null) {
				return "Please select a module container";
			}

			return null;
		}
	}

}
