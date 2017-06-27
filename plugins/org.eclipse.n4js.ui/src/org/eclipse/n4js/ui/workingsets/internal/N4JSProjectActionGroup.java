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
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.ui.IContextMenuConstants;
import org.eclipse.jdt.ui.actions.OpenProjectAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.n4js.ui.workingsets.WorkingSet;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.actions.CloseResourceAction;
import org.eclipse.ui.actions.CloseUnrelatedProjectsAction;
import org.eclipse.ui.ide.IDEActionFactory;

/**
 * Action group for closing and opening projects of a N4JS working set.
 */
public class N4JSProjectActionGroup extends ActionGroup {

	private static final int CLOSED_PROJECTS_SELECTED = 1;
	private static final int NON_PROJECT_SELECTED = 1 << 1;

	private final OpenProjectAction openAction;
	private final CloseResourceAction closeAction;
	private final CloseResourceAction closeUnrelatedAction;
	private final ISelectionChangedListener selectionChangedListener;

	private ISelectionProvider selectionProvider;
	private boolean enableOpenInContextMenu = true;

	// Enables or disables all the context menu contributions from this action group
	private boolean enableContribution = true;

	/**
	 * Creates a new {@code N4JSProjectActionGroup}. The group requires that the selection provided by the site's
	 * selection provider is of type {@link IStructuredSelection}.
	 *
	 * @param part
	 *            the view part that owns this action group
	 */
	public N4JSProjectActionGroup(final IViewPart part) {
		this(part.getSite(), part.getSite().getSelectionProvider());
	}

	/**
	 * Creates a new {@code N4JSProjectActionGroup}. The group requires that the selection provided by the given
	 * selection provider is of type {@link IStructuredSelection}.
	 *
	 * @param site
	 *            the site that will own the action group.
	 * @param selectionProvider
	 *            the selection provider used instead of the page selection provider.
	 */
	public N4JSProjectActionGroup(final IWorkbenchSite site, final ISelectionProvider selectionProvider) {
		this.selectionProvider = selectionProvider;
		final ISelection selection = selectionProvider.getSelection();

		closeAction = new CloseResourceAction(site);
		closeAction.setActionDefinitionId(IWorkbenchCommandConstants.PROJECT_CLOSE_PROJECT);

		closeUnrelatedAction = new CloseUnrelatedProjectsAction(site);
		closeUnrelatedAction.setActionDefinitionId(IWorkbenchCommandConstants.PROJECT_CLOSE_UNRELATED_PROJECTS);

		openAction = new OpenProjectAction(site);
		openAction.setActionDefinitionId(IWorkbenchCommandConstants.PROJECT_OPEN_PROJECT);
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection s = (IStructuredSelection) selection;
			openAction.selectionChanged(s);
			closeAction.selectionChanged(s);
			closeUnrelatedAction.selectionChanged(s);
		}

		selectionChangedListener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
				final ISelection s = event.getSelection();
				if (s instanceof IStructuredSelection) {
					performSelectionChanged((IStructuredSelection) s);
				}
			}
		};
		selectionProvider.addSelectionChangedListener(selectionChangedListener);

		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.addResourceChangeListener(openAction);
		workspace.addResourceChangeListener(closeAction);
		workspace.addResourceChangeListener(closeUnrelatedAction);
	}

	private void performSelectionChanged(final IStructuredSelection structuredSelection) {
		final Object[] array = structuredSelection.toArray();
		final ArrayList<IProject> openProjects = new ArrayList<>();
		final int selectionStatus = evaluateSelection(array, openProjects);
		final StructuredSelection sel = new StructuredSelection(openProjects);

		// If only projects are selected, disable this action group, as all of
		// the project-related contributions will be provided by default action providers
		enableContribution = (selectionStatus & NON_PROJECT_SELECTED) != 0;

		openAction.setEnabled(hasClosedProjectsInWorkspace());
		enableOpenInContextMenu = (selectionStatus & CLOSED_PROJECTS_SELECTED) != 0
				|| (selectionStatus == 0 && array.length == 0 && hasClosedProjectsInWorkspace());
		closeAction.selectionChanged(sel);
		closeUnrelatedAction.selectionChanged(sel);
	}

	private int evaluateSelection(final Object[] array, final List<IProject> allOpenProjects) {
		int status = 0;
		for (int i = 0; i < array.length; i++) {
			final Object curr = array[i];
			if (curr instanceof IProject) {
				final IProject project = (IProject) curr;
				if (project.isOpen()) {
					allOpenProjects.add(project);
				} else {
					status |= CLOSED_PROJECTS_SELECTED;
				}
			} else {
				status |= NON_PROJECT_SELECTED;
				if (curr instanceof WorkingSet) {
					final int res = evaluateSelection(((WorkingSet) curr).getElements(), allOpenProjects);
					status |= res;
				}
			}
		}
		return status;
	}

	private boolean hasClosedProjectsInWorkspace() {
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (int i = 0; i < projects.length; i++) {
			if (!projects[i].isOpen())
				return true;
		}
		return false;
	}

	@Override
	public void fillActionBars(final IActionBars actionBars) {
		super.fillActionBars(actionBars);
		actionBars.setGlobalActionHandler(IDEActionFactory.CLOSE_PROJECT.getId(), closeAction);
		actionBars.setGlobalActionHandler(IDEActionFactory.CLOSE_UNRELATED_PROJECTS.getId(), closeUnrelatedAction);
		actionBars.setGlobalActionHandler(IDEActionFactory.OPEN_PROJECT.getId(), openAction);
	}

	@Override
	public void fillContextMenu(final IMenuManager menu) {
		super.fillContextMenu(menu);

		// If disabled, do not contribute
		if (!enableContribution) {
			return;
		}

		if (openAction.isEnabled() && enableOpenInContextMenu)
			menu.appendToGroup(IContextMenuConstants.GROUP_BUILD, openAction);
		if (closeAction.isEnabled())
			menu.appendToGroup(IContextMenuConstants.GROUP_BUILD, closeAction);
		if (closeUnrelatedAction.isEnabled()
				&& areOnlyProjectsSelected(closeUnrelatedAction.getStructuredSelection()))
			menu.appendToGroup(IContextMenuConstants.GROUP_BUILD, closeUnrelatedAction);
	}

	/**
	 * Returns the open project action contained in this project action group.
	 *
	 * @return returns the open project action
	 */
	public OpenProjectAction getOpenProjectAction() {
		return openAction;
	}

	private boolean areOnlyProjectsSelected(final IStructuredSelection selection) {
		if (selection.isEmpty())
			return false;

		final Iterator<?> iter = selection.iterator();
		while (iter.hasNext()) {
			final Object obj = iter.next();
			if (obj instanceof IAdaptable) {
				if (((IAdaptable) obj).getAdapter(IProject.class) == null) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void dispose() {
		selectionProvider.removeSelectionChangedListener(selectionChangedListener);
		selectionProvider = null;

		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.removeResourceChangeListener(openAction);
		workspace.removeResourceChangeListener(closeAction);
		workspace.removeResourceChangeListener(closeUnrelatedAction);
		super.dispose();
	}

}
