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
package org.eclipse.n4js.ui.workingsets.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.n4js.ui.workingsets.ManualAssociationAwareWorkingSetManager;
import org.eclipse.n4js.ui.workingsets.ManualAssociationAwareWorkingSetManager.ManualAssociationWorkingSet;
import org.eclipse.n4js.ui.workingsets.WorkingSet;
import org.eclipse.n4js.ui.workingsets.WorkingSetDiffBuilder;
import org.eclipse.n4js.ui.workingsets.WorkingSetLabelProvider;
import org.eclipse.n4js.ui.workingsets.WorkingSetManager;
import org.eclipse.n4js.ui.workingsets.WorkingSetManagerBroker;
import org.eclipse.n4js.utils.collections.Arrays2;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.actions.SelectionListenerAction;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerSite;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * Action for assigning a selection of projects to one or more working sets.
 *
 * Note that this action has to be initialized via {@link #init(ICommonActionExtensionSite)}
 */
public class AssignWorkingSetsAction extends SelectionListenerAction implements IWorkbenchAction {

	private static final String DIALOG_TITLE = "Working Set Assignment";
	private static final String DIALOG_SUBTITLE = "Select working sets (visible-only) for %d selected element(s)";

	@Inject
	private WorkingSetManagerBroker broker;

	// the viewer site this action is executed in
	private ICommonViewerSite site;

	/**
	 * Creates a new action instance.
	 */
	public AssignWorkingSetsAction() {
		super("Assign Working Sets...");
	}

	/**
	 * Initializes the action.
	 *
	 * @param extensionSite
	 *            The extension site this action is used in
	 */
	public void init(ICommonActionExtensionSite extensionSite) {
		this.site = extensionSite.getViewSite();
	}

	@Override
	public void run() {
		// fail if the action hasn't been initialized
		if (site == null) {
			return;
		}

		final Object[] selectionElements = getStructuredSelection().toArray();

		// get Iterable of selected project names
		Iterable<String> selectionProjectNames = Arrays.asList(selectionElements)
				.stream().filter(item -> item instanceof IProject)
				.map(item -> ((IProject) item).getName())
				.collect(Collectors.toList());

		// double-check that the active Working Sets Manager is {@link ManualAssociationAwareWorkingSetManager}
		if (!(broker.getActiveManager() instanceof ManualAssociationAwareWorkingSetManager)) {
			return;
		}

		// open the dialog
		SelectionDialog dialog = createDialog(Arrays.asList(((ManualAssociationAwareWorkingSetManager) broker
				.getActiveManager()).getWorkingSets()), selectionElements.length);

		dialog.open();

		// Abort if user didn't press OK
		if (dialog.getReturnCode() != Window.OK) {
			return;
		}

		// perform specified working set updates
		performWorkingSetUpdate(dialog.getResult(), selectionProjectNames);
	}

	/**
	 * Performs the working sets update specified by the given dialog result.
	 *
	 * @param dialogResult
	 *            The result of the list selection dialog.
	 * @param selectionProjectNames
	 *            A list of names of the selected projects
	 * @returns {@code true} if the update was successfully performed. {@code false} otherwise.
	 */
	private boolean performWorkingSetUpdate(Object[] dialogResult, Iterable<String> selectionProjectNames) {
		if (dialogResult == null) {
			return false;
		}

		WorkingSetManager workingSetManager = broker.getActiveManager();
		if (!(workingSetManager instanceof ManualAssociationAwareWorkingSetManager)) {
			return false;
		}

		// get copies of the workspace working sets.
		List<WorkingSet> allWorkingSets = new ArrayList<>(Arrays.asList(workingSetManager.getAllWorkingSets()));
		List<WorkingSet> workingSets = new ArrayList<>(Arrays.asList(workingSetManager.getWorkingSets()));

		// build WorkingSet edit difference
		WorkingSetDiffBuilder builder = new WorkingSetDiffBuilder(workingSetManager);
		for (Object resultItem : dialogResult) {
			// abort if the dialog returned a non-working-set item
			if (!(resultItem instanceof ManualAssociationWorkingSet)) {
				return false;
			}
			ManualAssociationWorkingSet oldState = (ManualAssociationWorkingSet) resultItem;
			ManualAssociationWorkingSet newState = getWorkingSetWithAddedProjects(oldState, selectionProjectNames);

			// register working set edit
			builder.edit(oldState, newState);
			// replace working set with its new state
			replaceWorkingSet(workingSets, newState);
			replaceWorkingSet(allWorkingSets, newState);
		}
		// apply WorkingSet update and refresh UI
		workingSetManager.updateState(builder.build(workingSets, allWorkingSets));
		broker.refreshNavigator();

		return true;
	}

	/**
	 * Creates the dialog for working set assignment.
	 *
	 * @param workingSets
	 *            All the working set the user should be able to select from
	 * @param numberOfSelectedProjects
	 *            The number of selected projects
	 */
	private ListSelectionDialog createDialog(Collection<WorkingSet> workingSets, int numberOfSelectedProjects) {
		// Filter 'Other Projects' working set
		List<WorkingSet> selectableWorkingSets = workingSets.stream()
				.filter(set -> !set.getId().equals(WorkingSet.OTHERS_WORKING_SET_ID)).collect(Collectors.toList());

		String message = String.format(DIALOG_SUBTITLE, numberOfSelectedProjects);

		ListSelectionDialog dialog = new NonEmptyListSelectionDialog(site.getShell(),
				selectableWorkingSets, ArrayContentProvider.getInstance(), WorkingSetLabelProvider.INSTANCE, message);

		dialog.setTitle(DIALOG_TITLE);
		return dialog;
	}

	/**
	 * Replaces a possibly out-dated version of the given working set in the given list of working sets.
	 *
	 * Note that this depends on the equality-property of two different WorkingSet instances with the same id.
	 *
	 * @param list
	 *            The list of working sets
	 * @param workingSet
	 *            The new state of the working set to replace
	 */
	private boolean replaceWorkingSet(List<WorkingSet> list, WorkingSet workingSet) {
		int replaceIndex = list.indexOf(workingSet);
		if (replaceIndex != -1) {
			list.set(replaceIndex, workingSet);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns a new instance of the given working set with the added project names.
	 *
	 * @param workingSet
	 *            The old state of the working set
	 * @param addedProjectNames
	 *            The project names to add
	 */
	private ManualAssociationWorkingSet getWorkingSetWithAddedProjects(ManualAssociationWorkingSet workingSet,
			Iterable<String> addedProjectNames) {
		Iterable<String> mergedProjects = Iterables.concat(workingSet.getAssociatedProjectNames(), addedProjectNames);
		return new ManualAssociationWorkingSet(mergedProjects, workingSet.getName(), workingSet.getWorkingSetManager());
	}

	@Override
	public boolean updateSelection(IStructuredSelection selection) {
		if (selection == null || selection.isEmpty()) {
			return false;
		}

		final Object[] selectedElements = selection.toArray();
		final IProject[] selectedProjects = Arrays2.filter(selectedElements, IProject.class);

		// only enable this action for project-only selections
		if (selectedElements.length != selectedProjects.length) {
			return false;
		}

		// also check whether the active manager is {@link ManualAssociationAwareWorkingSetManager}
		if (!(broker.getActiveManager() instanceof ManualAssociationAwareWorkingSetManager)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void dispose() {
		// Currently nothing to dispose
	}

	/**
	 * A {@link ListSelectionDialog} that enforces that the user checks at least one element.
	 */
	private static class NonEmptyListSelectionDialog extends ListSelectionDialog {

		/**
		 * Instantiates a new {@link NonEmptyListSelectionDialog}.
		 *
		 * See super class for parameter documentation.
		 */
		public NonEmptyListSelectionDialog(Shell parentShell, Object input, IStructuredContentProvider contentProvider,
				ILabelProvider labelProvider, String message) {
			super(parentShell, input, contentProvider, labelProvider, message);
		}

		@Override
		protected Control createContents(Composite parent) {
			Control control = super.createContents(parent);

			// validate on every selection change
			getViewer().addCheckStateListener(event -> validateSelection());
			// do initial validation
			validateSelection();

			return control;
		}

		/**
		 * Validates the current checked-elements-selection.
		 *
		 * Enables the OK button iff the checked-elements-selection is not empty.
		 */
		private void validateSelection() {
			getOkButton().setEnabled(this.getViewer().getCheckedElements().length > 0);
		}

	}

}
