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
package org.eclipse.n4js.ui.compare;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.xtext.ui.editor.IURIEditorOpener;

import com.google.inject.Inject;

import org.eclipse.n4js.compare.ProjectComparisonEntry;

/**
 */
public class ApiCompareView extends ViewPart {

	@Inject
	private ProjectCompareTreeHelper projectCompareTreeHelper;

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.eclipse.n4js.ui.apicompareview";

	private ProjectCompareTree viewer;
	private Action actionUpdate;
	private Action actionOpenInEditor;

	@Override
	public void createPartControl(Composite parent) {
		viewer = new ProjectCompareTree(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL,
				projectCompareTreeHelper);
		makeActions();
		hookContextMenu();
		hookDoubleClickListener();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				ApiCompareView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(actionUpdate);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(actionUpdate);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(actionUpdate);
	}

	private void makeActions() {
		actionUpdate = new Action() {
			@Override
			public void run() {
				updateComparison();
			}
		};
		actionUpdate.setText("Update");
		actionUpdate.setToolTipText(
				"Recompute comparison for all API project and their implementation projects in the workspace.");
		// action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
		// getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		actionOpenInEditor = new Action() {
			@Override
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection) selection).getFirstElement();
				if (obj instanceof ProjectComparisonEntry)
					showInEditor((ProjectComparisonEntry) obj, true, true);
			}
		};
		actionOpenInEditor.setText("Open in Editor");
		actionOpenInEditor.setToolTipText(
				"Open the currently selected API element and its implementations in N4JS editors.");
	}

	private void updateComparison() {
		viewer.setComparison();
	}

	private void showInEditor(ProjectComparisonEntry entry, boolean showApi, boolean showImpl) {
		if (showApi) {
			final EObject elemApi = entry.getElementAPI();
			if (elemApi != null)
				showInEditor(elemApi);
		}
		if (showImpl) {
			final EObject[] elemImpls = entry.getElementImpl();
			if (elemImpls != null) {
				for (EObject elemImpl : elemImpls) {
					if (elemImpl != null)
						showInEditor(elemImpl);
				}
			}
		}
	}

	private void showInEditor(EObject eobj) {
		final Resource res = eobj.eResource();
		final URI uriBase = res.getURI();
		final String frag = res.getURIFragment(eobj);
		final URI uri = uriBase.appendFragment(frag);
		uriOpener.open(uri, true);
	}

	@Inject
	private IURIEditorOpener uriOpener;

	private void hookDoubleClickListener() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				actionOpenInEditor.run();
			}
		});
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
