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
package org.eclipse.n4js.ui.navigator.internal;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.ui.IContextMenuConstants;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.n4js.ui.workingsets.WorkingSet;
import org.eclipse.n4js.ui.workingsets.internal.AssignWorkingSetsAction;
import org.eclipse.n4js.ui.workingsets.internal.N4JSProjectActionGroup;
import org.eclipse.n4js.ui.workingsets.internal.N4JSWorkingSetActionProvider;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

import com.google.inject.Inject;

/**
 * Common Navigator Framework (CNF) action provider for N4JS elements in the navigator. (resources, working sets,
 * projects)
 */
public class N4JSNavigatorActionProvider extends CommonActionProvider {

	private N4JSProjectActionGroup projectGroup;
	private N4JSWorkingSetActionProvider workingSetActionProvider;

	@Inject
	private AssignWorkingSetsAction assignWorkingSetsAction;

	private boolean selectionContainsWorkingSet = false;

	@Override
	public void init(final ICommonActionExtensionSite site) {
		ICommonViewerWorkbenchSite workbenchSite = null;
		if (site.getViewSite() instanceof ICommonViewerWorkbenchSite)
			workbenchSite = (ICommonViewerWorkbenchSite) site.getViewSite();

		if (workbenchSite != null) {
			if (workbenchSite.getPart() != null && workbenchSite.getPart() instanceof IViewPart) {
				final IViewPart viewPart = (IViewPart) workbenchSite.getPart();

				projectGroup = new N4JSProjectActionGroup(viewPart);

				workingSetActionProvider = new N4JSWorkingSetActionProvider();
				workingSetActionProvider.init(site);

				assignWorkingSetsAction.init(site);
			}
		}

	}

	@Override
	public void fillContextMenu(final IMenuManager menu) {
		// {@link N4JSProjectActionGroup} does enablement-logic
		// on its own, thus always invoke it here
		projectGroup.fillContextMenu(menu);

		// Only delegate to {@link N4JSWorkingSetActionProvider},
		// if the current selection contains working sets.
		if (selectionContainsWorkingSet) {
			workingSetActionProvider.fillContextMenu(menu);
		}

		if (assignWorkingSetsAction.isEnabled()) {
			menu.appendToGroup(IContextMenuConstants.GROUP_BUILD, assignWorkingSetsAction);
		}
	}

	@Override
	public void fillActionBars(final IActionBars actionBars) {
		projectGroup.fillActionBars(actionBars);

		if (selectionContainsWorkingSet) {
			workingSetActionProvider.fillActionBars(actionBars);
		}
	}

	@Override
	public void setContext(final ActionContext context) {
		super.setContext(context);

		projectGroup.setContext(context);

		// context is null if disposal of the provider is triggered
		if (null != context) {
			StructuredSelection selection = (StructuredSelection) context.getSelection();
			List<Object> selectedElements = Arrays.asList(selection.toArray());

			selectionContainsWorkingSet = selectedElements.stream()
					.anyMatch(element -> element instanceof WorkingSet);

			// try to minimize number of context updates for working set action provider
			if (selectionContainsWorkingSet) {
				workingSetActionProvider.setContext(context);
			}

			assignWorkingSetsAction.selectionChanged(selection);
		}
	}

	@Override
	public void dispose() {
		projectGroup.dispose();
		workingSetActionProvider.dispose();

		super.dispose();
	}
}
