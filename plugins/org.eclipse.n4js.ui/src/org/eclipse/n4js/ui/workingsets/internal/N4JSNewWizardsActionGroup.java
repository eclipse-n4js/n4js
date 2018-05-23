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

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.ui.IContextMenuConstants;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.actions.NewWizardMenu;

import org.eclipse.n4js.ui.workingsets.WorkingSet;

/**
 * N4JS working set aware 'New' wizard action group contribution for the navigator.
 */
public class N4JSNewWizardsActionGroup extends ActionGroup {

	private final IWorkbenchSite fSite;
	private NewWizardMenu fNewWizardMenu;

	/**
	 * Creates a new <code>NewWizardsActionGroup</code>. The group requires that the selection provided by the part's
	 * selection provider is of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code>.
	 *
	 * @param site
	 *            the view part that owns this action group
	 */
	public N4JSNewWizardsActionGroup(final IWorkbenchSite site) {
		fSite = site;
	}

	@Override
	public void fillContextMenu(final IMenuManager menu) {
		super.fillContextMenu(menu);

		final ISelection selection = getContext().getSelection();
		if (selection instanceof IStructuredSelection) {
			if (canEnable((IStructuredSelection) selection)) {
				final MenuManager newMenu = new MenuManager("Ne&w");
				menu.appendToGroup(IContextMenuConstants.GROUP_NEW, newMenu);
				newMenu.add(getNewWizardMenu());
			}
		}

	}

	private NewWizardMenu getNewWizardMenu() {
		if (fNewWizardMenu == null) {
			fNewWizardMenu = new NewWizardMenu(fSite.getWorkbenchWindow());
		}
		return fNewWizardMenu;
	}

	private boolean canEnable(final IStructuredSelection sel) {
		if (sel.size() == 0) {
			return true;
		}

		final List<?> list = sel.toList();
		for (final Iterator<?> iterator = list.iterator(); iterator.hasNext(); /**/) {
			if (!isNewTarget(iterator.next())) {
				return false;
			}
		}

		return true;
	}

	private boolean isNewTarget(final Object element) {
		if (element == null) {
			return true;
		}
		if (element instanceof IResource) {
			return true;
		}
		if (element instanceof WorkingSet) {
			return true;
		}
		return false;
	}

	@Override
	public void dispose() {
		if (fNewWizardMenu != null) {
			fNewWizardMenu.dispose();
			fNewWizardMenu = null;
		}
		super.dispose();
	}

}
