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
package org.eclipse.n4js.ui.wizard.dependencies;

import java.util.Map;

import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.n4js.ui.external.ExternalLibrariesActionsHelper;

import com.google.inject.Inject;

/**
 * Helper for installing npm dependencies.
 */
public class ExternalLibrariesInstallHelper {

	@Inject
	private ProjectDependenciesHelper dependneciesHelper;

	@Inject
	private ExternalLibrariesActionsHelper externals;

	/** Streamlined process of calculating and installing the dependencies without cleaning npm cache. */
	public void calculateAndInstallDependencies(SubMonitor monitor, MultiStatus multistatus) {
		calculateAndInstallDependencies(monitor, multistatus, false);
	}

	/** Streamlined process of calculating and installing the dependencies, npm cache cleaning forced by passed flag */
	public void calculateAndInstallDependencies(SubMonitor monitor, MultiStatus multistatus, boolean removeNpmCache) {
		final SubMonitor subMonitor2 = monitor.split(1);

		if (removeNpmCache)
			// remove npm cache
			externals.maintenanceCleanNpmCache(multistatus, subMonitor2);

		// reset type definitions
		externals.maintenanceResetTypeDefinitions(multistatus);

		// remove npms
		externals.maintenanceDeleteNpms(multistatus);

		// install npms from target platform
		Map<String, String> versionedPackages = dependneciesHelper.calculateDependenciesToInstall();
		final SubMonitor subMonitor3 = monitor.split(45);

		externals.installNoUpdate(versionedPackages, multistatus, subMonitor3);

		// rebuild externals & schedule full rebuild
		final SubMonitor subMonitor4 = monitor.split(35);
		externals.maintenanceUpateState(multistatus, subMonitor4);
	}

}
