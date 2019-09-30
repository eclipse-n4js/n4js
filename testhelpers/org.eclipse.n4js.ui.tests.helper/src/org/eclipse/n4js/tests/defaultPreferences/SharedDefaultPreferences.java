/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.defaultPreferences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * Checks all default preferences defined in org.eclipse.n4js.product/plugin_customization.ini
 */
public class SharedDefaultPreferences {

	/** Checks all default preferences that are shared between all n4js products */
	static public void testDefaultPreferences() {

		// org.eclipse.core.resources
		IPreferenceStore prefsCoreResources = new ScopedPreferenceStore(InstanceScope.INSTANCE,
				"org.eclipse.core.resources");
		boolean prefsCoreResources_refreshEnabled = prefsCoreResources.getBoolean("refresh.enabled");
		String prefsCoreResources_encoding = prefsCoreResources.getString("encoding");
		assertTrue(prefsCoreResources_refreshEnabled);
		assertEquals(prefsCoreResources_encoding, "UTF-8");

		// org.eclipse.ui.editors
		IPreferenceStore prefsUiEditors = new ScopedPreferenceStore(InstanceScope.INSTANCE,
				"org.eclipse.ui.editors");
		boolean prefsUiEditors_spacesForTabs = prefsUiEditors.getBoolean("spacesForTabs");
		assertTrue(prefsUiEditors_spacesForTabs);

		// org.eclipse.ui.ide
		IPreferenceStore prefsUiIde = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.eclipse.ui.ide");
		boolean prefsUiIde_REFRESH_WORKSPACE_ON_STARTUP = prefsUiIde.getBoolean("REFRESH_WORKSPACE_ON_STARTUP");
		boolean prefsUiIde_tipsAndTricks = prefsUiIde.getBoolean("tipsAndTricks");
		boolean prefsUiIde_EXIT_PROMPT_ON_CLOSE_LAST_WINDOW = prefsUiIde.getBoolean("EXIT_PROMPT_ON_CLOSE_LAST_WINDOW");
		boolean prefsUiIde_WELCOME_DIALOG = prefsUiIde.getBoolean("WELCOME_DIALOG");
		assertTrue(prefsUiIde_REFRESH_WORKSPACE_ON_STARTUP);
		assertFalse(prefsUiIde_tipsAndTricks);
		assertFalse(prefsUiIde_EXIT_PROMPT_ON_CLOSE_LAST_WINDOW);
		assertFalse(prefsUiIde_WELCOME_DIALOG);

		// org.eclipse.ui
		IPreferenceStore prefsUi = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.eclipse.ui");
		boolean prefsUi_showIntro = prefsUi.getBoolean("showIntro");
		boolean prefsUi_SHOW_PROGRESS_ON_STARTUP = prefsUi.getBoolean("SHOW_PROGRESS_ON_STARTUP");
		String prefsUi_defaultPerspectiveId = prefsUi.getString("defaultPerspectiveId");
		boolean prefsUi_SHOW_TEXT_ON_PERSPECTIVE_BAR = prefsUi.getBoolean("SHOW_TEXT_ON_PERSPECTIVE_BAR");
		String prefsUi_PERSPECTIVE_BAR_EXTRAS = prefsUi.getString("PERSPECTIVE_BAR_EXTRAS");
		assertFalse(prefsUi_showIntro);
		assertTrue(prefsUi_SHOW_PROGRESS_ON_STARTUP);
		assertEquals(prefsUi_defaultPerspectiveId, "org.eclipse.n4js.product.N4JSPerspective");
		assertFalse(prefsUi_SHOW_TEXT_ON_PERSPECTIVE_BAR);
		assertEquals(prefsUi_PERSPECTIVE_BAR_EXTRAS,
				"org.eclipse.n4js.product.N4JSPerspective,org.eclipse.egit.ui.GitRepositoryExploring");

	}

}
