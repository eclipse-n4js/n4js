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

import org.eclipse.jdt.internal.ui.navigator.OpenAndExpand;
import org.eclipse.jdt.ui.actions.OpenAction;
import org.eclipse.jdt.ui.actions.OpenEditorActionGroup;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionConstants;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

/**
 * Action contribution for opening external resource files as well.
 */
@SuppressWarnings("restriction")
public class N4JSOpenActions extends CommonActionProvider {

	private IAction openAndExpandAction;
	private OpenEditorActionGroup openGroup;
	private boolean inViewPart = false;
	private ISelection selection;

	@Override
	public void fillActionBars(final IActionBars actionBars) {
		if (inViewPart && canEnableOpenAction()) {
			openGroup.fillActionBars(actionBars);
			if (openAndExpandAction == null && openGroup.getOpenAction().isEnabled()) {
				actionBars.setGlobalActionHandler(ICommonActionConstants.OPEN, openGroup.getOpenAction());
			} else if (openAndExpandAction != null && openAndExpandAction.isEnabled()) {
				actionBars.setGlobalActionHandler(ICommonActionConstants.OPEN, openAndExpandAction);
			}
		}
	}

	@Override
	public void fillContextMenu(final IMenuManager menu) {
		if (inViewPart && canEnableOpenAction()) {
			if (openGroup.getOpenAction().isEnabled()) {
				openGroup.fillContextMenu(menu);
			}
		}
	}

	@Override
	public void init(final ICommonActionExtensionSite site) {

		ICommonViewerWorkbenchSite workbenchSite = null;
		if (site.getViewSite() instanceof ICommonViewerWorkbenchSite) {
			workbenchSite = (ICommonViewerWorkbenchSite) site.getViewSite();
		}

		if ((workbenchSite != null)) {
			if (null != workbenchSite.getPart() && workbenchSite.getPart() instanceof IViewPart) {
				final IViewPart viewPart = (IViewPart) workbenchSite.getPart();
				openGroup = new OpenEditorActionGroup(viewPart);
				if (site.getStructuredViewer() instanceof TreeViewer) {
					openAndExpandAction = new OpenAndExpand(workbenchSite.getSite(),
							(OpenAction) openGroup.getOpenAction(),
							(TreeViewer) site.getStructuredViewer());
				}
				inViewPart = true;
			}
		}
	}

	@Override
	public void setContext(final ActionContext context) {
		super.setContext(context);
		selection = null == context ? null : context.getSelection();
		if (inViewPart) {
			openGroup.setContext(context);
		}
	}

	@Override
	public void dispose() {
		if (null != openGroup) {
			openGroup.dispose();
		}
		super.dispose();
	}

	/**
	 * Returns with {@code false} if the selection contains at least one single {@link ResourceNode} element, that
	 * either does not exist or backed by a directory. Otherwise returns with {@code true}.
	 */
	private boolean canEnableOpenAction() {

		if (null == selection) {
			return false;
		}

		if (!(selection instanceof IStructuredSelection)) {
			return false;
		}

		final Object[] elements = ((IStructuredSelection) selection).toArray();
		for (final Object element : elements) {
			if (element instanceof ResourceNode) {
				final ResourceNode node = (ResourceNode) element;
				final SafeURI<?> resource = node.getLocation();
				if (!resource.isFile()) {
					return false;
				}
			}
		}

		return true;
	}

}
