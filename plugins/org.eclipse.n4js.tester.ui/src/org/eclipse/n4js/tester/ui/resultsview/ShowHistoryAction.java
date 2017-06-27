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
package org.eclipse.n4js.tester.ui.resultsview;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolItem;

import org.eclipse.n4js.tester.domain.TestTree;
import org.eclipse.n4js.tester.ui.TesterUiActivator;

/**
 * Action with a drop-down menu showing a list of recent sessions.
 */
public class ShowHistoryAction extends Action {

	private final TestResultsView view;

	/**
	 * Create an instance.
	 */
	public ShowHistoryAction(TestResultsView view) {
		super("Show History", IAction.AS_DROP_DOWN_MENU);
		this.view = view;
		setToolTipText("Show list of recent test sessions.");
		setImageDescriptor(TesterUiActivator.getImageDescriptor(TesterUiActivator.ICON_HISTORY));
		setMenuCreator(new HistoryMenuCreator());
	}

	@Override
	public void runWithEvent(Event event) {
		if (event.widget instanceof ToolItem) {
			final ToolItem toolItem = (ToolItem) event.widget;
			final Control control = toolItem.getParent();
			final Menu menu = getMenuCreator().getMenu(control);

			final Rectangle bounds = toolItem.getBounds();
			final Point topLeft = new Point(bounds.x, bounds.y + bounds.height);
			menu.setLocation(control.toDisplay(topLeft));
			menu.setVisible(true);
		}
	}

	private final class HistoryMenuCreator implements IMenuCreator {

		private Menu menu;

		@Override
		public Menu getMenu(Control parent) {
			if (menu != null)
				menu.dispose();

			menu = new Menu(parent.getShell(), SWT.POP_UP);
			for (TestTree currTree : view.getTestTrees()) {
				addActionToMenu(menu, new ShowSessionAction(currTree));
			}
			addSeparator(menu);
			addActionToMenu(menu, view.getActionClearTerminated());

			return menu;
		}

		protected void addActionToMenu(Menu parent, Action action) {
			final ActionContributionItem item = new ActionContributionItem(action);
			item.fill(parent, -1);
		}

		protected MenuItem addSeparator(Menu parent) {
			return new MenuItem(parent, SWT.SEPARATOR);
		}

		@Override
		public Menu getMenu(Menu parent) {
			return null;
		}

		@Override
		public void dispose() {
			if (menu != null)
				menu.dispose();
		}
	}

	private final class ShowSessionAction extends Action {

		private final TestTree tree;

		public ShowSessionAction(TestTree root) {
			this.tree = root;
			setText(root.getName());
			// setImageDescriptor(image);
			setEnabled(root != view.getShownTestTree());
		}

		@Override
		public void run() {
			view.setShownTestTree(tree.getSessionId());
		}
	}
}
