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
package org.eclipse.n4js.ui.preferences.external;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.primitives.Ints.asList;
import static java.util.Collections.singletonList;
import static org.eclipse.jface.layout.GridDataFactory.fillDefaults;
import static org.eclipse.n4js.external.libraries.ExternalLibrariesActivator.EXTERNAL_LIBRARIES_SUPPLIER;
import static org.eclipse.n4js.ui.preferences.external.ButtonFactoryUtil.createDisabledPushButton;
import static org.eclipse.n4js.ui.preferences.external.ButtonFactoryUtil.createEnabledPushButton;
import static org.eclipse.n4js.ui.utils.DelegatingSelectionAdapter.createSelectionListener;
import static org.eclipse.swt.SWT.END;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.OPEN;
import static org.eclipse.swt.SWT.Selection;
import static org.eclipse.swt.SWT.TOP;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.n4js.external.ExternalIndexSynchronizer;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.external.NpmCLI;
import org.eclipse.n4js.external.ShadowingInfoHelper;
import org.eclipse.n4js.external.TargetPlatformInstallLocationProvider;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.semver.SemverUtils;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.n4js.semver.Semver.VersionComparator;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.ui.external.ExternalLibrariesActionsHelper;
import org.eclipse.n4js.ui.utils.InputComposedValidator;
import org.eclipse.n4js.ui.utils.InputFunctionalValidator;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.ui.viewer.TreeViewerBuilder;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.collections.Arrays2;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Preference page for managing external libraries.
 */
public class ExternalLibraryPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	/**
	 * The unique preference page ID.
	 */
	public static final String ID = ExternalLibraryPreferencePage.class.getName();

	static final Map<URI, String> BUILT_IN_LIBS = EXTERNAL_LIBRARIES_SUPPLIER.get();

	@Inject
	private ExternalLibraryPreferenceStore store;

	@Inject
	private Provider<ExternalLibraryTreeContentProvider> contentProvider;

	@Inject
	private LibraryManager libManager;

	@Inject
	private NpmCLI npmCli;

	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;

	@Inject
	private TargetPlatformInstallLocationProvider locationProvider;

	@Inject
	private StatusHelper statusHelper;

	@Inject
	private SemverHelper semverHelper;

	@Inject
	private ExternalLibrariesActionsHelper externalLibrariesActionsHelper;

	@Inject
	private ExternalIndexSynchronizer indexSynchronizer;

	@Inject
	private ShadowingInfoHelper shadowingInfoHelper;

	private TreeViewer viewer;

	@Override
	public void init(final IWorkbench workbench) {
		// Nothing.
	}

	@Override
	protected Control createContents(final Composite parent) {
		this.setSize(new Point(600, 600));

		final BuiltInLibrariesLabelProvider labelProvider = new BuiltInLibrariesLabelProvider(indexSynchronizer,
				shadowingInfoHelper, externalLibraryWorkspace);
		final Composite control = new Composite(parent, NONE);
		control.setLayout(GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).create());
		control.setLayoutData(fillDefaults().align(FILL, FILL).create());

		viewer = new TreeViewerBuilder(singletonList(""), contentProvider.get())
				.setVirtual(true)
				.setHeaderVisible(false)
				.setUseHashlookup(true)
				.setHasBorder(true)
				.setColumnWeights(asList(1))
				.setLabelProvider(new DelegatingStyledCellLabelProvider(labelProvider))
				.build(control);

		setViewerInput();

		final Composite subComposite = new Composite(control, NONE);
		subComposite.setLayout(GridLayoutFactory.fillDefaults().create());
		subComposite.setLayoutData(fillDefaults().align(END, TOP).create());

		createEnabledPushButton(subComposite, "Add...",
				createSelectionListener(this::handleAddButtonSelectionListener));

		final Button remove = createDisabledPushButton(subComposite, "Remove",
				createSelectionListener(this::handleRemoveButtonSelection));

		createPlaceHolderLabel(subComposite);

		final Button moveUp = createDisabledPushButton(subComposite, "Up",
				createSelectionListener(this::handleMoveUpButtonSelection));

		final Button moveDown = createDisabledPushButton(subComposite, "Down",
				createSelectionListener(this::handleMoveDownButtonSelection));

		createPlaceHolderLabel(subComposite);

		createPlaceHolderLabel(subComposite);

		createEnabledPushButton(subComposite, "Install npm...",
				new InstallNpmDependencyButtonListener(this::installAndUpdate,
						() -> getPackageNameToInstallValidator(), () -> getPackageVersionValidator(),
						semverHelper, statusHelper));

		createEnabledPushButton(subComposite, "Uninstall npm...",
				new UninstallNpmDependencyButtonListener(this::uninstallAndUpdate,
						() -> getPackageNameToUninstallValidator(),
						statusHelper,
						this::getSelectedNpm));

		createEnabledPushButton(subComposite, "Run maintenance actions...",
				new MaintenanceActionsButtonListener(this::runMaintananceActions, statusHelper));

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(final /* @Nullable */ SelectionChangedEvent event) {
				final Tree tree = viewer.getTree();
				final TreeItem[] selection = tree.getSelection();
				if (!Arrays2.isEmpty(selection) && 1 == selection.length && selection[0].getData() instanceof URI) {
					final URI uri = (URI) selection[0].getData();
					if (BUILT_IN_LIBS.containsKey(uri)) {
						remove.setEnabled(false);
						moveUp.setEnabled(false);
						moveDown.setEnabled(false);
					} else {
						final int selectionIndex = tree.indexOf(selection[0]);
						final int itemCount = tree.getItemCount();
						remove.setEnabled(true);
						if (selectionIndex > 0) {
							moveUp.setEnabled(!BUILT_IN_LIBS.containsKey(tree.getItem(selectionIndex - 1).getData()));
						} else {
							moveUp.setEnabled(0 != selectionIndex);
						}
						moveDown.setEnabled(selectionIndex != itemCount - 1);
					}
				} else {
					remove.setEnabled(false);
					moveUp.setEnabled(false);
					moveDown.setEnabled(false);
				}
			}
		});

		return control;
	}

	@Override
	public void createControl(Composite parent) {
		noDefaultAndApplyButton();
		super.createControl(parent);
	}

	@Override
	public boolean performCancel() {
		store.invalidate();
		return true;
	}

	@Override
	public boolean performOk() {
		final MultiStatus multistatus = statusHelper
				.createMultiStatus("Status of importing target platform.");
		try {
			new ProgressMonitorDialog(getShell()).run(true, false, monitor -> {
				final IStatus status = store.save(monitor);
				if (!status.isOK()) {
					setMessage(status.getMessage(), ERROR);
					multistatus.merge(status);
				} else {
					updateInput(viewer, store.getLocations());
				}
			});
		} catch (final InvocationTargetException | InterruptedException exc) {
			multistatus.merge(statusHelper.createError("Error while building external libraries.", exc));
		}

		if (multistatus.isOK())
			return super.performOk();
		else
			return false;
	}

	/**
	 * Asynchronously sets the the viewer input with the locations available from the
	 * {@link ExternalLibraryPreferenceStore store}.
	 */
	private void setViewerInput() {
		if (null != viewer && null != viewer.getControl() && !viewer.getControl().isDisposed()) {
			UIUtils.getDisplay().asyncExec(() -> updateInput(viewer, store.getLocations()));
		}
	}

	private Control createPlaceHolderLabel(final Composite parent) {
		return new Label(parent, NONE);
	}

	/**
	 * Validator that checks if given name is valid package name and can be used to install new package (i.e. there is
	 * no installed package with the same name).
	 *
	 * @return validator checking if provided name can be used to install new package
	 */
	private IInputValidator getPackageNameToInstallValidator() {
		return InputComposedValidator.compose(
				getBasicPackageValidator(), InputFunctionalValidator.from(
						(final String name) -> !isNpmWithNameInstalled(name) ? null
								/* error message */
								: "The npm package '" + name + "' is already available."));
	}

	/**
	 * Validator that checks if given name is valid package name and can be used to uninstall new package (i.e. there is
	 * installed package with the same name).
	 *
	 * @return validator checking if provided name can be used to install new package
	 */
	private IInputValidator getPackageNameToUninstallValidator() {
		return InputComposedValidator.compose(
				getBasicPackageValidator(), InputFunctionalValidator.from(
						(final String name) -> isNpmWithNameInstalled(name) ? null
								/* error case */
								: "The npm package '" + name + "' is not installed."));
	}

	// TODO refactor with libManager internal logic of validating package name
	private IInputValidator getBasicPackageValidator() {
		return InputFunctionalValidator.from(
				(final String name) -> {
					if (npmCli.invalidPackageName(name))
						return "The npm package name should be specified.";
					for (int i = 0; i < name.length(); i++) {
						if (Character.isWhitespace(name.charAt(i)))
							return "The npm package name must not contain any whitespaces.";

						if (Character.isUpperCase(name.charAt(i)))
							return "The npm package name must not contain any upper case letter.";
					}
					return null;
				});
	}

	private IInputValidator getPackageVersionValidator() {
		return InputFunctionalValidator.from(
				(final String version) -> parsingVersionValidator(version));
	}

	/**
	 * version validator based on N4MF parser (and its support for version syntax).
	 *
	 * @return error message or null if there are no errors
	 */
	private String parsingVersionValidator(final String data) {
		String result = null;

		IParseResult parseResult = semverHelper.getParseResult(data);
		if (parseResult == null) {
			result = "Could not create version from string :" + data + ":\n";
		} else if (parseResult.hasSyntaxErrors()) {
			INode firstErrorNode = parseResult.getSyntaxErrors().iterator().next();
			result = "Parsing error: " + firstErrorNode.getSyntaxErrorMessage().getMessage();
		}

		// otherwise, parsedVersion is valid and result remains 'null'
		// to indicate validity (see {@link IInputValidator#isValid})

		return result;
	}

	private boolean isNpmWithNameInstalled(final String packageName) {
		final File root = new File(locationProvider.getNodeModulesURI());
		return from(externalLibraryWorkspace.getProjectsIn(root.toURI()))
				.transform(p -> p.getName())
				.anyMatch(name -> name.equals(packageName));
	}

	private Map<String, NPMVersionRequirement> getInstalledNpms() {
		URI root = locationProvider.getNodeModulesURI();
		Collection<N4JSExternalProject> projects = externalLibraryWorkspace.getProjectsIn(root);
		Map<String, NPMVersionRequirement> versionedNpms = new HashMap<>();

		for (N4JSExternalProject prj : projects) {
			org.eclipse.emf.common.util.URI location = prj.getIProject().getLocation();
			ProjectDescription pd = externalLibraryWorkspace.getProjectDescription(location);
			String name = pd.getProjectName();
			VersionNumber version = pd.getProjectVersion();
			NPMVersionRequirement vr = SemverUtils.createVersionRangeSet(VersionComparator.EQUALS, version);
			versionedNpms.put(name, vr);
		}

		return versionedNpms;
	}

	/**
	 * Handler for executing maintenance action based on the provided {@link MaintenanceActionsChoice user choice}.
	 */
	private MultiStatus runMaintananceActions(final MaintenanceActionsChoice userChoice, IProgressMonitor monitor) {
		final MultiStatus multistatus = statusHelper
				.createMultiStatus("Executing maintenance actions.");

		// persist state for reinstall
		Map<String, NPMVersionRequirement> oldPackages = new HashMap<>();
		if (userChoice.decisionReinstall)
			oldPackages.putAll(getInstalledNpms());

		// keep the order Cache->TypeDefs->NPMs->Reinstall->Update
		// actions have side effects that can interact with each other
		maintenanceCleanNpmCache(userChoice, multistatus, monitor);
		maintenanceDeleteNpms(userChoice, multistatus);
		reinstallNpms(userChoice, multistatus, monitor, oldPackages);
		upateState(userChoice, multistatus, monitor);

		return multistatus;
	}

	/**
	 * Actions to be taken if npm cache clean is requested.
	 *
	 * @param userChoice
	 *            options object used to decide if / how actions should be performed
	 * @param multistatus
	 *            the status used accumulate issues
	 * @param monitor
	 *            the monitor used to interact with npm manager
	 */
	private void maintenanceCleanNpmCache(final MaintenanceActionsChoice userChoice,
			final MultiStatus multistatus, IProgressMonitor monitor) {
		if (userChoice.decisionCleanCache) {
			externalLibrariesActionsHelper.maintenanceCleanNpmCache(multistatus, monitor);
		}
	}

	/**
	 * Updates all affected state after maintenance actions have been performed. In particular updates state of the
	 * workspace, persisted preferences state, and displayed preferences.
	 *
	 * @param userChoice
	 *            options object used to decide if / how actions should be performed
	 * @param multistatus
	 *            the status used accumulate issues
	 * @param monitor
	 *            the monitor used to interact with npm manager
	 */
	private void upateState(final MaintenanceActionsChoice userChoice,
			final MultiStatus multistatus, IProgressMonitor monitor) {

		if (userChoice.decisionReload || userChoice.decisionReinstall || userChoice.decisionPurgeNpm) {

			try {
				libManager.reloadAllExternalProjects(monitor);

			} catch (Exception e) {
				String msg = "Error when reloading external libraries.";
				multistatus.merge(statusHelper.createError(msg, e));
			}
			updateInput(viewer, store.getLocations());
		}
	}

	/**
	 * Actions to be taken if reinstalling npms is requested.
	 *
	 * @param userChoice
	 *            options object used to decide if / how actions should be performed
	 * @param multistatus
	 *            the status used accumulate issues
	 * @param monitor
	 *            the monitor used to interact with npm manager
	 * @param packageNames
	 *            names of the packages and their versions to reinstall
	 *
	 */
	private void reinstallNpms(final MaintenanceActionsChoice userChoice,
			final MultiStatus multistatus, IProgressMonitor monitor, Map<String, NPMVersionRequirement> packageNames) {
		if (userChoice.decisionReinstall) {

			// unless all npms were purged, uninstall known ones
			if (!userChoice.decisionPurgeNpm) {
				IStatus uninstallStatus = uninstallAndUpdate(packageNames.keySet(), monitor);
				if (!uninstallStatus.isOK())
					multistatus.merge(uninstallStatus);
			}

			IStatus installStatus = installAndUpdate(packageNames, monitor);
			if (!installStatus.isOK())
				multistatus.merge(installStatus);

		}
	}

	/**
	 * Actions to be taken if deleting npms is requested.
	 *
	 * @param userChoice
	 *            options object used to decide if / how actions should be performed
	 * @param multistatus
	 *            the status used accumulate issues
	 */
	private void maintenanceDeleteNpms(final MaintenanceActionsChoice userChoice, final MultiStatus multistatus) {
		if (userChoice.decisionPurgeNpm) {
			externalLibrariesActionsHelper.maintenanceDeleteNpms(multistatus);
		}
	}

	/**
	 * Selection handler for adding a new external library location.
	 */
	private void handleAddButtonSelectionListener(@SuppressWarnings("unused") final SelectionEvent e) {
		final String directoryPath = new DirectoryDialog(viewer.getControl().getShell(), OPEN).open();
		if (null != directoryPath) {
			final File file = new File(directoryPath);
			if (file.isDirectory()) {
				store.add(file.toURI());
				updateInput(viewer, store.getLocations());
			}
		}
	}

	/**
	 * Selection handler for adding a new external library location.
	 */
	private void handleRemoveButtonSelection(@SuppressWarnings("unused") final SelectionEvent e) {
		final ISelection selection = viewer.getSelection();
		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
			final Object element = ((IStructuredSelection) selection).getFirstElement();
			if (element instanceof URI) {
				store.remove((URI) element);
				updateInput(viewer, store.getLocations());
			}
		}
	}

	/**
	 * Selection handler for moving up an external library location in the list.
	 */
	private void handleMoveUpButtonSelection(@SuppressWarnings("unused") final SelectionEvent e) {
		final ISelection selection = viewer.getSelection();
		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
			final Object element = ((IStructuredSelection) selection).getFirstElement();
			if (element instanceof URI) {
				store.moveUp((URI) element);
				updateInput(viewer, store.getLocations());
			}
		}
	}

	/**
	 * Selection handler for moving down an external library location in the list.
	 */
	private void handleMoveDownButtonSelection(@SuppressWarnings("unused") final SelectionEvent e) {
		final ISelection selection = viewer.getSelection();
		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
			final Object element = ((IStructuredSelection) selection).getFirstElement();
			if (element instanceof URI) {
				store.moveDown((URI) element);
				updateInput(viewer, store.getLocations());
			}
		}
	}

	/**
	 * Installs npm packages with provide names and versions, if successful updates preference page view. Note that in
	 * case package has no version it is expected that empty string is provided.
	 *
	 * @return status of the operation.
	 */
	private IStatus installAndUpdate(final Map<String, NPMVersionRequirement> versionedPackages,
			final IProgressMonitor monitor) {
		IStatus status = libManager.installNPMs(versionedPackages, false, monitor);
		if (status.isOK())
			updateInput(viewer, store.getLocations());

		return status;
	}

	/**
	 * Uninstalls npm packages with provide names, if successful updates preference page view.
	 *
	 * @return status of the operation.
	 */
	private IStatus uninstallAndUpdate(final Collection<String> packageNames, final IProgressMonitor monitor) {
		IStatus status = libManager.uninstallNPM(packageNames, monitor);
		if (status.isOK())
			updateInput(viewer, store.getLocations());

		return status;
	}

	private String getSelectedNpm() {
		final ISelection selection = viewer.getSelection();
		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
			final Object element = ((IStructuredSelection) selection).getFirstElement();
			if (element instanceof IN4JSProject) {
				IN4JSProject project = (IN4JSProject) element;
				return project.getProjectName();
			}
		}
		return null;
	}

	private static void updateInput(final TreeViewer viewer, final Object input) {
		UIUtils.getDisplay().asyncExec(() -> {
			final Object[] expandedElements = viewer.getExpandedElements();
			final TreePath[] expandedTreePaths = viewer.getExpandedTreePaths();
			viewer.setInput(input);
			viewer.getControl().notifyListeners(Selection, null);
			if (!Arrays2.isEmpty(expandedElements)) {
				viewer.setExpandedElements(expandedElements);
			}
			if (!Arrays2.isEmpty(expandedTreePaths)) {
				viewer.setExpandedTreePaths(expandedTreePaths);
			}
		});
	}

}
