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

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.eclipse.n4js.ui.workingsets.WorkingSet.OTHERS_WORKING_SET_ID;
import static org.eclipse.core.runtime.Status.CANCEL_STATUS;
import static org.eclipse.core.runtime.Status.OK_STATUS;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

import org.eclipse.n4js.ui.workingsets.ManualAssociationAwareWorkingSetManager;
import org.eclipse.n4js.ui.workingsets.ManualAssociationAwareWorkingSetManager.ManualAssociationWorkingSet;
import org.eclipse.n4js.ui.workingsets.WorkingSet;
import org.eclipse.n4js.ui.workingsets.WorkingSetDiffBuilder;
import org.eclipse.n4js.ui.workingsets.WorkingSetManager;
import org.eclipse.n4js.ui.workingsets.WorkingSetManagerBroker;
import org.eclipse.n4js.utils.Diff;

/**
 * Allow dragging projects between N4JS working sets in the {@code Project Explorer}.
 */
public class N4JSProjectInWorkingSetDropAdapterAssistant extends CommonDropAdapterAssistant {

	@Inject
	private WorkingSetManagerBroker workingSetManagerBroker;

	@Override
	public IStatus validateDrop(Object target, int operation, TransferData transferType) {

		// We don't currently support COPY or LINK
		if (operation != DND.DROP_MOVE) {
			return CANCEL_STATUS;
		}

		WorkingSet targetWorkingSet = null;
		if (target instanceof WorkingSet) {
			targetWorkingSet = (WorkingSet) target;
		}

		if (targetWorkingSet == null) {
			return CANCEL_STATUS;
		}

		if (!LocalSelectionTransfer.getTransfer().isSupportedType(transferType)) {
			return CANCEL_STATUS;
		}

		// Verify that we have at least one project not already in the target
		ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();
		if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
			for (Object item : ((IStructuredSelection) selection).toArray()) {
				if (item instanceof IAdaptable) {
					IProject project = ((IAdaptable) item).getAdapter(IProject.class);
					if (project != null && !workingSetContains(targetWorkingSet, project)) {
						WorkingSetManager manager = ((WorkingSet) target).getWorkingSetManager();
						if (ManualAssociationAwareWorkingSetManager.class.getName().equals(manager.getId())) {
							return OK_STATUS;
						}
					}
				}
			}

			// Or contains exactly one working set for rearrange purposes.
			final Object[] elements = ((IStructuredSelection) selection).toArray();
			if (elements.length == 1 && elements[0] instanceof WorkingSet) {
				getCommonDropAdapter().setExpandEnabled(false);
				getCommonDropAdapter().setFeedbackEnabled(true);

				return OK_STATUS;
			}

		}

		return CANCEL_STATUS;
	}

	@Override
	public IStatus handleDrop(CommonDropAdapter dropAdapter, DropTargetEvent dropTargetEvent,
			Object target) {

		WorkingSet oldTarget = (WorkingSet) target;
		WorkingSetManager manager = oldTarget.getWorkingSetManager();

		List<WorkingSet> allItems = newArrayList(manager.getAllWorkingSets());
		List<WorkingSet> visibleItems = newArrayList(manager.getWorkingSets());
		WorkingSetDiffBuilder diffBuilder = new WorkingSetDiffBuilder(manager);

		ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();
		if (selection instanceof ITreeSelection) {
			ManualAssociationWorkingSet oldSource = null;
			for (TreePath path : ((ITreeSelection) selection).getPaths()) {
				IProject project = ((IAdaptable) path.getLastSegment()).getAdapter(IProject.class);
				if (project != null) {

					if (!(target instanceof ManualAssociationWorkingSet)) {
						return CANCEL_STATUS;
					}

					if (!ManualAssociationAwareWorkingSetManager.class.getName().equals(manager.getId())) {
						return CANCEL_STATUS;
					}

					if (!workingSetContains(oldTarget, project)
							&& !OTHERS_WORKING_SET_ID.equals(oldTarget.getId())) {

						Collection<String> projectNames = newHashSet(
								((ManualAssociationWorkingSet) oldTarget).getAssociatedProjectNames());
						projectNames.add(project.getName());
						ManualAssociationWorkingSet newTarget = new ManualAssociationWorkingSet(projectNames,
								oldTarget.getId(), manager);

						int allIndex = indexOfById(oldTarget, allItems);
						allItems.remove(allIndex);
						allItems.add(allIndex, newTarget);

						int visibleIndex = indexOfById(oldTarget, visibleItems);
						if (visibleIndex >= 0) {
							visibleItems.remove(visibleIndex);
							visibleItems.add(visibleIndex, newTarget);
						}

						diffBuilder.edit(oldTarget, newTarget);
						oldTarget = newTarget;
					}

					// Check if our top-level element is a working set so that we can perform a move
					if (path.getFirstSegment() instanceof ManualAssociationWorkingSet) {
						if (oldSource == null) {
							oldSource = ((ManualAssociationWorkingSet) path.getFirstSegment());
						}

						if (oldSource != null && !OTHERS_WORKING_SET_ID.equals(oldSource.getId())) {

							Collection<String> projectNames = newHashSet(oldSource.getAssociatedProjectNames());
							projectNames.remove(project.getName());
							ManualAssociationWorkingSet newSource = new ManualAssociationWorkingSet(projectNames,
									oldSource.getId(), manager);

							int allIndex = indexOfById(oldSource, allItems);
							allItems.remove(allIndex);
							allItems.add(allIndex, newSource);

							int visibleIndex = indexOfById(oldSource, visibleItems);
							if (visibleIndex >= 0) {
								visibleItems.remove(visibleIndex);
								visibleItems.add(visibleIndex, newSource);
							}

							diffBuilder.edit(oldSource, newSource);
							oldSource = newSource;
						}
					}
				} else if (path.getLastSegment() instanceof WorkingSet) {

					WorkingSet movedWorkingSet = (WorkingSet) path.getLastSegment();
					int sourceVisibleIndex = indexOfById(movedWorkingSet, visibleItems);
					int sourceAllIndex = indexOfById(movedWorkingSet, allItems);

					if (sourceVisibleIndex == -1 || sourceAllIndex == -1) {
						return CANCEL_STATUS;
					}

					final Object currentTarget = getCommonDropAdapter().getCurrentTarget();
					if (currentTarget instanceof WorkingSet) {
						int targetVisibleIndex = indexOfById((WorkingSet) currentTarget, visibleItems);
						int targetAllIndex = indexOfById((WorkingSet) currentTarget, allItems);

						if (targetVisibleIndex == -1 || targetAllIndex == -1) {
							return CANCEL_STATUS;
						}

						if (getCommonDropAdapter().getCurrentLocation() == ViewerDropAdapter.LOCATION_AFTER) {
							targetVisibleIndex++;
							targetAllIndex++;
						}

						WorkingSet visibleRemoved = visibleItems.remove(sourceVisibleIndex);
						visibleItems.add(
								sourceVisibleIndex >= targetVisibleIndex ? targetVisibleIndex : targetVisibleIndex - 1,
								visibleRemoved);

						WorkingSet allRemoved = allItems.remove(sourceAllIndex);
						allItems.add(
								sourceAllIndex >= targetAllIndex ? targetAllIndex : targetAllIndex - 1,
								allRemoved);

					} else {
						return CANCEL_STATUS;
					}

				}
			}

		} else if (selection instanceof IStructuredSelection) {
			for (Object item : ((IStructuredSelection) selection).toArray()) {
				IProject project = ((IAdaptable) item).getAdapter(IProject.class);
				if (project != null && !workingSetContains(oldTarget, project)
						&& !OTHERS_WORKING_SET_ID.equals(oldTarget.getId())) {

					Collection<String> projectNames = newHashSet(
							((ManualAssociationWorkingSet) oldTarget).getAssociatedProjectNames());
					projectNames.add(project.getName());
					ManualAssociationWorkingSet newTarget = new ManualAssociationWorkingSet(projectNames,
							oldTarget.getId(), manager);

					allItems.remove(oldTarget);
					allItems.add(newTarget);
					if (visibleItems.remove(oldTarget)) {
						visibleItems.add(newTarget);
					}

					diffBuilder.edit(oldTarget, newTarget);
					oldTarget = newTarget;

				}
			}
		}

		WorkingSet[] newItems = Iterables.toArray(visibleItems, WorkingSet.class);
		WorkingSet[] newAllItems = Iterables.toArray(allItems, WorkingSet.class);
		Diff<WorkingSet> diff = diffBuilder.build(newItems, newAllItems);
		if (!diff.isEmpty()) {
			manager.updateState(diff);
			manager.saveState(new NullProgressMonitor());
			workingSetManagerBroker.refreshNavigator();
		}

		return OK_STATUS;
	}

	/**
	 * Made public for testing purposes.
	 *
	 * <p>
	 * {@inheritDoc}
	 */
	@Override
	@VisibleForTesting
	public CommonDropAdapter getCommonDropAdapter() {
		return super.getCommonDropAdapter();
	}

	private int indexOfById(WorkingSet element, List<WorkingSet> items) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getId().equals(element.getId())) {
				return i;
			}
		}
		return -1;
	}

	private boolean workingSetContains(WorkingSet workingSet, IProject project) {
		for (IAdaptable element : workingSet.getElements()) {
			if (project.equals(element.getAdapter(IProject.class))) {
				return true;
			}
		}
		return false;
	}

}
