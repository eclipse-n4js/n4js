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
import org.eclipse.n4js.projectModel.dependencies.ProjectDependenciesHelper;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.ui.external.ExternalLibrariesActionsHelper;

import com.google.inject.Inject;

/**
 * Helper for installing npm dependencies.
 */
public class ExternalLibrariesInstallHelper {

	static private final DataCollector dcInstallHelper = DataCollectors.INSTANCE
			.getOrCreateDataCollector("External Libraries Install Helper");
	private static final DataCollector dcCollectMissingDependencies = DataCollectors.INSTANCE
			.getOrCreateDataCollector("Collect missing dependencies", "External Libraries Install Helper");
	private static final DataCollector dcInstallMissingDependencies = DataCollectors.INSTANCE
			.getOrCreateDataCollector("Install missing dependencies", "External Libraries Install Helper");

	@Inject
	private ProjectDependenciesHelper dependenciesHelper;

	@Inject
	private ExternalLibrariesActionsHelper externals;

	/** Streamlined process of calculating and installing the dependencies without cleaning npm cache. */
	public void calculateAndInstallDependencies(SubMonitor monitor, MultiStatus multistatus) {
		calculateAndInstallDependencies(monitor, multistatus, false);
	}

	/** Streamlined process of calculating and installing the dependencies, npm cache cleaning forced by passed flag */
	public void calculateAndInstallDependencies(SubMonitor monitor, MultiStatus multistatus, boolean removeNpmCache) {
		final Measurement overallMeasurement = dcInstallHelper.getMeasurement("Install Missing Dependencies");
		final SubMonitor subMonitor2 = monitor.split(1);

		if (removeNpmCache)
			// remove npm cache
			externals.maintenanceCleanNpmCache(multistatus, subMonitor2);

		// remove npms
		externals.maintenanceDeleteNpms(multistatus);

		Measurement measurment = dcCollectMissingDependencies.getMeasurement("Collect Missing Dependencies");

		// install npms from target platform
		Map<String, NPMVersionRequirement> dependenciesToInstall = dependenciesHelper.computeDependenciesOfWorkspace();
		dependenciesHelper.fixDependenciesToInstall(dependenciesToInstall);

		measurment.end();
		measurment = dcInstallMissingDependencies.getMeasurement("Install missing dependencies");

		final SubMonitor subMonitor3 = monitor.split(45);
		// install dependencies and force external library workspace reload
		externals.installNoUpdate(dependenciesToInstall, true, multistatus, subMonitor3);

		measurment.end();
		overallMeasurement.end();
	}

}
