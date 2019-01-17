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
import static org.eclipse.n4js.ui.preferences.external.ButtonFactoryUtil.createEnabledPushButton;
import static org.eclipse.swt.SWT.END;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.Selection;
import static org.eclipse.swt.SWT.TOP;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Collection;
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
import org.eclipse.n4js.external.NpmCLI;
import org.eclipse.n4js.external.ShadowingInfoHelper;
import org.eclipse.n4js.external.TargetPlatformInstallLocationProvider;
import org.eclipse.n4js.internal.N4JSProject;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceModel;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.n4js.ui.external.ExternalLibrariesActionsHelper;
import org.eclipse.n4js.ui.utils.InputComposedValidator;
import org.eclipse.n4js.ui.utils.InputFunctionalValidator;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.ui.viewer.TreeViewerBuilder;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.collections.Arrays2;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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

		final Button install = createEnabledPushButton(subComposite, "Install npm...",
				"Runs 'npm install' with the given package and version. Uses 'yarn add' in a yarn workspace.",
				new InstallNpmDependencyButtonListener(this::installAndUpdate,
						() -> getPackageNameToInstallValidator(), () -> getPackageVersionValidator(),
						semverHelper, statusHelper));

		final Button uninstall = createEnabledPushButton(subComposite, "Uninstall npm...",
				"Runs 'npm uninstall' with the given package and version. Uses 'yarn remove' in a yarn workspace.",
				new UninstallNpmDependencyButtonListener(this::uninstallAndUpdate,
						() -> getPackageNameToUninstallValidator(),
						statusHelper, this::getSelectedNpm));

		createPlaceHolderLabel(subComposite);

		createPlaceHolderLabel(subComposite);

		createEnabledPushButton(subComposite, "Re-Build node_modules",
				"Cleans the type information from the IDE and then re-build the type information of all node_modules.",
				new RereigsterAllNpmsButtonListener(this::reregisterNpms, statusHelper));

		createEnabledPushButton(subComposite, "Clean node_modules",
				"Runs 'npm clean' on all node_modules folders. Uses 'yarn clean' in a yarn workspace.",
				new CleanAllNpmsButtonListener(this::cleanNpms, statusHelper));

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(final /* @Nullable */ SelectionChangedEvent event) {
				install.setEnabled(false);
				uninstall.setEnabled(false);
				final Tree tree = viewer.getTree();
				final TreeItem[] selection = tree.getSelection();
				if (!Arrays2.isEmpty(selection) && 1 == selection.length) {
					Object data = selection[0].getData();

					if (data instanceof URI) {
						URI uri = (URI) data;
						if (ExternalLibraryPreferenceModel.isNodeModulesLocation(uri)) {
							install.setEnabled(true);
						}
					}

					if (data instanceof N4JSProject) {
						uninstall.setEnabled(true);
					}
				}
			}
		});

		control.requestLayout();

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

	/** Actions to be taken if deleting npms is requested. */
	private MultiStatus cleanNpms(final MultiStatus multistatus) {
		try {
			externalLibrariesActionsHelper.maintenanceDeleteNpms(multistatus);
		} catch (Exception e) {
			String msg = "Error when cleaning external libraries.";
			multistatus.merge(statusHelper.createError(msg, e));
		} finally {
			updateInput(viewer, store.getLocations());
		}
		return multistatus;
	}

	/** Actions to be taken if re-registering npms is requested. */
	private MultiStatus reregisterNpms(final IProgressMonitor monitor, final MultiStatus multistatus) {
		try {
			IStatus status = libManager.registerAllExternalProjects(monitor);
			multistatus.merge(status);
		} catch (Exception e) {
			String msg = "Error when re-registering external libraries.";
			multistatus.merge(statusHelper.createError(msg, e));
		} finally {
			updateInput(viewer, store.getLocations());
		}
		return multistatus;
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
