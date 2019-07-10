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

import static com.google.common.primitives.Ints.asList;
import static java.util.Collections.singletonList;
import static org.eclipse.jface.layout.GridDataFactory.fillDefaults;
import static org.eclipse.n4js.ui.preferences.external.ButtonFactoryUtil.createEnabledPushButton;
import static org.eclipse.swt.SWT.END;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.Selection;
import static org.eclipse.swt.SWT.TOP;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceModel;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.ui.external.ExternalLibrariesActionsHelper;
import org.eclipse.n4js.ui.navigator.internal.N4JSProjectExplorerHelper;
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

	@Inject
	private ExternalLibraryPreferenceStore store;

	@Inject
	private Provider<ExternalLibraryTreeContentProvider> contentProvider;

	@Inject
	private NpmNameAndVersionValidatorHelper validatorHelper;

	@Inject
	private LibraryManager libManager;

	@Inject
	private StatusHelper statusHelper;

	@Inject
	private SemverHelper semverHelper;

	@Inject
	private ExternalLibrariesActionsHelper externalLibrariesActionsHelper;

	@Inject
	private N4JSProjectExplorerHelper projectExplorerhelper;

	private TreeViewer viewer;

	@Override
	public void init(final IWorkbench workbench) {
		// Nothing.
	}

	@Override
	protected Control createContents(final Composite parent) {
		this.setSize(new Point(600, 600));

		final BuiltInLibrariesLabelProvider labelProvider = new BuiltInLibrariesLabelProvider(projectExplorerhelper);
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
				new InstallNpmDependencyButtonListener(this::updateLocations,
						libManager, validatorHelper, semverHelper, statusHelper, this::getSelectedNodeModulesURI));

		final Button uninstall = createEnabledPushButton(subComposite, "Uninstall npm...",
				"Runs 'npm uninstall' with the given package and version. Uses 'yarn remove' in a yarn workspace.",
				new UninstallNpmDependencyButtonListener(this::updateLocations,
						libManager, validatorHelper, statusHelper, this::getSelectedNpm));

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

				Object selectedItem = getSelectedItem();
				if (selectedItem instanceof SafeURI) {
					install.setEnabled(true);
				}
				if (selectedItem instanceof IN4JSProject) {
					uninstall.setEnabled(true);
				}
			}
		});

		control.requestLayout();

		return control;
	}

	/** @return either a URI of a node_modules folder or a external N4JSProject instance */
	private Object getSelectedItem() {
		final Tree tree = viewer.getTree();
		final TreeItem[] selection = tree.getSelection();
		if (!Arrays2.isEmpty(selection) && 1 == selection.length) {
			Object data = selection[0].getData();

			if (data instanceof SafeURI<?>) {
				SafeURI<?> uri = (SafeURI<?>) data;
				if (ExternalLibraryPreferenceModel.isNodeModulesLocation(uri)) {
					return data;
				}
			}

			if (data instanceof IN4JSProject) {
				return data;
			}
		}
		return null;
	}

	private IN4JSProject getSelectedNpm() {
		return (IN4JSProject) getSelectedItem();
	}

	private SafeURI<?> getSelectedNodeModulesURI() {
		return (SafeURI<?>) getSelectedItem();
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

	/** Actions to be taken if deleting npms is requested. */
	private MultiStatus cleanNpms(final IProgressMonitor monitor, final MultiStatus multistatus) {
		try {
			multistatus.merge(externalLibrariesActionsHelper.maintenanceDeleteNpms(monitor));
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

	private void updateLocations() {
		updateInput(viewer, store.getLocations());
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
