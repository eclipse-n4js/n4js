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
package org.eclipse.n4js.tests.externalPackages;

import java.io.File;
import java.nio.file.Path;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;

import com.google.common.base.StandardSystemProperty;

/**
 */
public class ExternalWorkspaceTestUtils {
	private static final String USER_HOME = StandardSystemProperty.USER_HOME.value();

	/**
	 * Adds provided paths to the {@link ExternalLibraryPreferenceStore} data. Also tries to remove 'default' location
	 * from preferences.
	 *
	 * @param externalLibraryPreferenceStore
	 *            preference store to modify
	 * @param location
	 *            to be added to the preferences
	 */
	synchronized public static final void setExternalLibrariesPreferenceStoreLocations(
			ExternalLibraryPreferenceStore externalLibraryPreferenceStore, Path location) {

		externalLibraryPreferenceStore.remove((new File(USER_HOME)).toPath().toUri());// remove default
		externalLibraryPreferenceStore.add(location.toUri());
		externalLibraryPreferenceStore.save(new NullProgressMonitor());
		ProjectTestsUtils.waitForAutoBuild();
	}

	/**
	 * Removes given locations from the provided library store. Tries to restore 'default' location.
	 *
	 * @param externalLibraryPreferenceStore
	 *            to be edited
	 * @param dir
	 *            locations to be removed from store
	 */
	synchronized public static void removeExternalLibrariesPreferenceStoreLocations(
			ExternalLibraryPreferenceStore externalLibraryPreferenceStore, Path dir) {

		// externalLibraryPreferenceStore.add((new File(USER_HOME)).toPath().toUri());// add default
		externalLibraryPreferenceStore.remove(dir.toUri());
		externalLibraryPreferenceStore.save(new NullProgressMonitor());
		ProjectTestsUtils.waitForAutoBuild();
	}

}
