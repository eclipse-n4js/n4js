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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.n4js.external.NpmLogger;
import org.eclipse.n4js.semver.SemverMatcher;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.semver.model.SemverSerializer;
import org.eclipse.n4js.ui.external.ExternalLibrariesActionsHelper;

import com.google.common.base.Joiner;
import com.google.inject.Inject;

/**
 * Helper for installing npm dependencies.
 */
public class ExternalLibrariesInstallHelper {

	@Inject
	private ProjectDependenciesHelper dependenciesHelper;

	@Inject
	private ExternalLibrariesActionsHelper externals;

	@Inject
	private NpmLogger logger;

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

		Map<String, VersionNumber> projectNamesOfShippedCode = StreamSupport
				.stream(dependenciesHelper.getAvailableProjectsDescriptions(true).spliterator(), false)
				.collect(Collectors.toMap(pd -> pd.getProjectName(), pd -> pd.getProjectVersion()));

		// install npms from target platform
		Map<String, NPMVersionRequirement> dependenciesToInstall = dependenciesHelper.calculateDependenciesToInstall();
		removeDependenciesToShippedCodeIfVersionMatches(dependenciesToInstall, projectNamesOfShippedCode);
		addDependenciesForRemainingShippedCode(dependenciesToInstall, projectNamesOfShippedCode.keySet());
		logShippedCodeInstallationStatus(dependenciesToInstall, projectNamesOfShippedCode.keySet());

		final SubMonitor subMonitor3 = monitor.split(45);
		externals.installNoUpdate(dependenciesToInstall, multistatus, subMonitor3);

		// rebuild externals & schedule full rebuild
		final SubMonitor subMonitor4 = monitor.split(35);
		externals.maintenanceUpateState(multistatus, subMonitor4);
	}

	/**
	 * Removes from map 'dependenciesToInstall' all entries for projects that are in the shipped code, if and only if
	 * the version provided by the shipped code matches the version requirement.
	 */
	private void removeDependenciesToShippedCodeIfVersionMatches(
			Map<String, NPMVersionRequirement> dependenciesToInstall,
			Map<String, VersionNumber> projectNamesOfShippedCode) {
		for (Entry<String, NPMVersionRequirement> depToInstall : dependenciesToInstall.entrySet()) {
			String projectName = depToInstall.getKey();
			VersionNumber availableVersionInShippedCode = projectNamesOfShippedCode.get(projectName);
			if (availableVersionInShippedCode != null) {
				NPMVersionRequirement versionRequirement = depToInstall.getValue();
				if (versionRequirement != null
						&& SemverMatcher.matchesStrict(availableVersionInShippedCode, versionRequirement)) {
					// so remove from list of dependencies to be installed:
					dependenciesToInstall.remove(projectName);
				}
			}
		}
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
	private void addDependenciesForRemainingShippedCode(Map<String, NPMVersionRequirement> dependenciesToInstall,
			Set<String> projectNamesOfShippedCode) {
		Set<String> projectNamesOfShippedCodeToInstall = new HashSet<>(dependenciesToInstall.keySet());
		projectNamesOfShippedCodeToInstall.retainAll(projectNamesOfShippedCode);
		if (!projectNamesOfShippedCodeToInstall.isEmpty()
				&& projectNamesOfShippedCodeToInstall.size() < projectNamesOfShippedCode.size()) {
			Map<String, NPMVersionRequirement> versionRequirementsOfShippedCodeToInstall = projectNamesOfShippedCodeToInstall
					.stream()
					.map(id -> dependenciesToInstall.get(id))
					.collect(Collectors.toMap(SemverSerializer::serialize, Function.identity(), (vr1, vr2) -> vr1));
			NPMVersionRequirement versionConstraint = versionRequirementsOfShippedCodeToInstall.values().stream()
					.findFirst().orElse(null);
			if (versionConstraint != null) {
				if (versionRequirementsOfShippedCodeToInstall.size() > 1) {
					logger.logInfo("WARNING: differing version requirements for shipped code: "
							+ Joiner.on(", ").join(versionRequirementsOfShippedCodeToInstall.values())
							+ "; using: " + versionConstraint);
				}
				for (String id : projectNamesOfShippedCode) {
					if (!projectNamesOfShippedCodeToInstall.contains(id)) {
						dependenciesToInstall.put(id, versionConstraint);
					}
				}
			}
		}
	}

	/**
	 * Pure logging, won't change the arguments.
	 */
	private void logShippedCodeInstallationStatus(Map<String, NPMVersionRequirement> dependenciesToInstall,
			Set<String> projectNamesOfShippedCode) {
		// compute status of shipped code installation
		Map<String, NPMVersionRequirement> shippedCodeToInstall = new HashMap<>(dependenciesToInstall);
		Set<String> nonShadowedShippedCode = new HashSet<>(projectNamesOfShippedCode);
		shippedCodeToInstall.keySet().retainAll(projectNamesOfShippedCode);
		nonShadowedShippedCode.removeAll(shippedCodeToInstall.keySet());
		// report status to user
		if (shippedCodeToInstall.isEmpty()) {
			logger.logInfo("Not going to shadow any shipped code with installed packages.");
		} else {
			String separator = "\n\t";
			logger.logInfo("The following installed packages will shadow shipped code:" + separator
					+ shippedCodeToInstall.entrySet().stream()
							.map(e -> e.getKey() + "@" + e.getValue())
							.collect(Collectors.joining(separator)));
			if (nonShadowedShippedCode.isEmpty()) {
				logger.logInfo("The entire shipped code will be shadowed.");
			} else {
				logger.logInfo("WARNING: shipped code will be shadowed only partially; non-shadowed shipped projects:"
						+ separator + Joiner.on(separator).join(nonShadowedShippedCode));
			}
		}
	}
}
