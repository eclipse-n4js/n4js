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
package org.eclipse.n4js.product;

import static com.google.common.collect.FluentIterable.from;
import static java.util.Arrays.asList;
import static org.eclipse.ui.IWorkbenchActionConstants.M_PROJECT;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.Workbench_buildSet;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.internal.ide.WorkbenchActionBuilder;

import com.google.common.base.Predicate;

/**
 * Class for configuring the action and menu bar for the N4JS IDE application.
 */
@SuppressWarnings("restriction")
public class N4JSApplicationActionBarAdvisor extends ActionBarAdvisor {

	private static final Predicate<MenuManager> PROJECT_MENU_PREDICATE = i -> M_PROJECT.equals(i.getId());
	private static final Predicate<MenuManager> WORKING_SET_MENU_PREDICATE = i -> Workbench_buildSet
			.equals(i.getMenuText());

	private final WorkbenchActionBuilder delegate;
	private final IActionBarConfigurer configurer;

	/**
	 * Constructor for creating a new action bar advisor for the application.
	 *
	 * @param configurer
	 *            the action bar configurer argument.
	 */
	public N4JSApplicationActionBarAdvisor(final IActionBarConfigurer configurer) {
		super(configurer);
		delegate = new WorkbenchActionBuilder(configurer);
		this.configurer = configurer;
	}

	@Override
	public IStatus saveState(final IMemento memento) {
		return delegate.saveState(memento);
	}

	@Override
	public void fillActionBars(final int flags) {
		delegate.fillActionBars(flags);

		// To remove 'Build Working Set' contribution item from the main menu.
		final IMenuManager menuManager = configurer.getMenuManager();
		final MenuManager projectManager = getMenuManager(PROJECT_MENU_PREDICATE, menuManager);
		if (null != projectManager) {
			final MenuManager workingSetManager = getMenuManager(WORKING_SET_MENU_PREDICATE, projectManager);
			projectManager.remove(workingSetManager);
			projectManager.update(true);
		}
	}

	@Override
	public IStatus restoreState(final IMemento memento) {
		return delegate.restoreState(memento);
	}

	@Override
	public void dispose() {
		delegate.dispose();
	}

	@Override
	public boolean isApplicationMenu(final String menuId) {
		return delegate.isApplicationMenu(menuId);
	}

	private MenuManager getMenuManager(Predicate<MenuManager> predicate, IContributionManager manager) {
		return from(asList(manager.getItems())).filter(MenuManager.class).firstMatch(predicate).orNull();
	}

}
