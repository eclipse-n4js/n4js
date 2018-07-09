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

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.n4js.ui.external.ExternalLibrariesActionsHelper;

import com.google.inject.Inject;

/**
 * Helper for installing npm dependencies.
 */
public class ExternalLibrariesInstallHelper {

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
		final SubMonitor subMonitor2 = monitor.split(1);

		if (removeNpmCache)
			// remove npm cache
			externals.maintenanceCleanNpmCache(multistatus, subMonitor2);

		// reset type definitions
		externals.maintenanceResetTypeDefinitions(multistatus);

		// remove npms
		externals.maintenanceDeleteNpms(multistatus);

		Set<String> projectIdsOfShippedCode = StreamSupport
				.stream(dependenciesHelper.getAvailableProjectsDescriptions(true).spliterator(), false)
				.map(pd -> pd.getProjectId())
				.collect(Collectors.toCollection(LinkedHashSet::new));

		// install npms from target platform
		Map<String, String> dependenciesToInstall = dependenciesHelper.calculateDependenciesToInstall();
		addDependenciesForRemainingShippedCode(dependenciesToInstall, projectIdsOfShippedCode);
		final SubMonitor subMonitor3 = monitor.split(45);

		externals.installNoUpdate(dependenciesToInstall, multistatus, subMonitor3);

		// rebuild externals & schedule full rebuild
		final SubMonitor subMonitor4 = monitor.split(35);
		externals.maintenanceUpateState(multistatus, subMonitor4);
	}

	/**
	 * If map 'dependenciesToInstall' contains at least one project that is among the shipped code projects, this method
	 * will add new entries to map 'dependenciesToInstall' for all remaining shipped code projects, using the same
	 * version constraint as for those already in the map.
	 * <p>
	 * Rationale is that when shadowing a shipped code project with a newly installed NPM in the library manager, then
	 * *all* shipped code projects need to be shadowed, because shipped code is now published in clusters in which each
	 * project depends on the others with a fixed version.
	 */
	private void addDependenciesForRemainingShippedCode(Map<String, String> dependenciesToInstall,
			Set<String> projectIdsOfShippedCode) {
		Set<String> projectIdsOfShippedCodeToInstall = new HashSet<>(dependenciesToInstall.keySet());
		projectIdsOfShippedCodeToInstall.retainAll(projectIdsOfShippedCode);
		if (!projectIdsOfShippedCodeToInstall.isEmpty()
				&& projectIdsOfShippedCodeToInstall.size() < projectIdsOfShippedCode.size()) {
			Set<String> versionConstraintsOfShippedCodeToInstall = projectIdsOfShippedCodeToInstall
					.stream()
					.map(id -> dependenciesToInstall.get(id))
					.collect(Collectors.toSet());
			if (versionConstraintsOfShippedCodeToInstall.size() > 1) {
				// FIXME GH-957 / GH-809 warn about conflicting version constraints for shipped code!
			}
			String versionConstraint = versionConstraintsOfShippedCodeToInstall.stream().findFirst()
					.orElse(null);
			if (versionConstraint != null) {
				for (String id : projectIdsOfShippedCode) {
					if (!projectIdsOfShippedCodeToInstall.contains(id)) {
						dependenciesToInstall.put(id, versionConstraint);
					}
				}
			}
		}
	}
}
