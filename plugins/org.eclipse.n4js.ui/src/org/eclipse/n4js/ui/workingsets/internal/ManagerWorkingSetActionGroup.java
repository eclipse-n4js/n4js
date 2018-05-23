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

import org.eclipse.jdt.ui.IContextMenuConstants;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.actions.ActionGroup;

/**
 * Action group for managing existing working sets.
 */
public class ManagerWorkingSetActionGroup extends ActionGroup {

	private static final String GROUP_MANAGE = "group.manage"; //$NON-NLS-1$

	private final HideWorkingSetAction hideAction;

	/**
	 * Creates a new {@code ManagerWorkingSetActionGroup}. The group requires that the selection provided by the part's
	 * selection provider is of type {@code IStructuredSelection}.
	 *
	 * @param part
	 *            the view part that owns this action group
	 */
	public ManagerWorkingSetActionGroup(IViewPart part) {
		hideAction = new HideWorkingSetAction();
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		menu.appendToGroup(IContextMenuConstants.GROUP_REORGANIZE, new Separator(GROUP_MANAGE));
		menu.appendToGroup(GROUP_MANAGE, hideAction);
		super.fillContextMenu(menu);
	}

	@Override
	public void updateActionBars() {
		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		hideAction.selectionChanged(selection);
		super.updateActionBars();
	}

	@Override
	public void fillActionBars(IActionBars actionBars) {
		updateActionBars();
		super.fillActionBars(actionBars);
	}

	@Override
	public void dispose() {
		hideAction.dispose();
		super.dispose();
	}

}
