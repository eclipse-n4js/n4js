/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;

/**
 * A handler to select all visible elements in the project navigator.
 */
public class SelectAllProjectExplorerHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (null == activeWorkbenchWindow) {
			return null;
		}
		IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
		if (null == activePage) {
			return null;
		}
		IWorkbenchPart activePart = activePage.getActivePart();
		if (!(activePart instanceof CommonNavigator)) {
			return null;
		}

		CommonNavigator navigator = (CommonNavigator) activePart;
		CommonViewer commonViewer = navigator.getCommonViewer();

		Tree navigatorTree = commonViewer.getTree();
		List<TreeItem> visibleItems = new ArrayList<>();
		collectChildren(navigatorTree.getItems(), visibleItems);

		List<Object> visibleData = visibleItems.stream().map(i -> i.getData()).collect(Collectors.toList());

		commonViewer.setSelection(new StructuredSelection(visibleData), false);

		return null;
	}

	private void collectChildren(TreeItem[] items, List<TreeItem> visibleItems) {
		Arrays.asList(items).stream()
				.filter(i -> i.getData() != null)
				.forEach(item -> {
					visibleItems.add(item);
					if (item.getExpanded()) {
						collectChildren(item.getItems(), visibleItems);
					}
				});
	}
}
