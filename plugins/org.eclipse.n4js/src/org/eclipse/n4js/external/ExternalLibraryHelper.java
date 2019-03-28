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
package org.eclipse.n4js.external;

import static java.lang.Boolean.parseBoolean;
import static org.eclipse.core.runtime.Platform.inDebugMode;
import static org.eclipse.core.runtime.Platform.inDevelopmentMode;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;

import com.google.common.collect.ImmutableList;

/**
 * Utilities and core rules for external libraries.
 */
public final class ExternalLibraryHelper {

	/**
	 * Property configured and available only in the N4JS IDE product. If the associated value of the property can be
	 * parsed to {@code true} value via the {@link Boolean#parseBoolean(String)}. This property is here to control
	 * presence of the built in libraries or the type definitions repository.
	 */
	public static final String INCLUDES_BUILT_INS_PRODUCT_PROPERTY = "includesBuiltInLibraries";

	/**
	 * System property that is checked only and if only the N4JS IDE product is running, it is configured to include the
	 * built-in libraries but the application is running either in {@link Platform#inDebugMode() debug mode} or in
	 * {@link Platform#inDevelopmentMode() development mode}, in other words not in production mode. If the N4JS IDE
	 * product is not in production mode, then if the {@link Boolean#parseBoolean(String) boolean value} of this
	 * property is {@code true}, then the built-in libraries (such as N4JS Runtime and Mangelhaft) will be included.
	 * Otherwise they will not be.
	 */
	public static final String INCLUDES_BUILT_INS_SYSTEM_PROPERTY = "org.eclipse.n4js.includesBuiltInLibraries";

	/** Unique name of the {@code npm} category. */
	public static final String NPM_CATEGORY = "node_modules";

	/** List of all categories. Latter entries shadow former entries. */
	public static final List<String> CATEGORY_SHADOWING_ORDER = ImmutableList.<String> builder()
			.add(NPM_CATEGORY)
			.build();

	/**
	 * Returns {@code true} iff the given {@link File} represents a project directory in the workspace that is available
	 * to the projects in the N4JS workspace in that workspace projects may declare dependencies on them.
	 *
	 * This excludes packages that have been installed to the external workspace as transitive dependency of a package
	 * that has been explicitly installed on user request.
	 */
	public boolean isExternalProjectDirectory(File projectDirectory) {
		if (!projectDirectory.isDirectory()) {
			return false;
		}

		// check whether package.json exists
		final File packageJsonFile = new File(projectDirectory, N4JSGlobals.PACKAGE_JSON);
		return packageJsonFile.isFile();
	}

	/**
	 * Returns {@code true} iff the given {@link File} represents a directory that is considered an npm scope directory.
	 */
	public boolean isScopeDirectory(File scopeDirectory) {
		final String name = scopeDirectory.getName();
		return name.startsWith(ProjectDescriptionUtils.NPM_SCOPE_PREFIX) &&
				ProjectDescriptionUtils.isValidScopeName(name);
	}

	/**
	 * Returns with {@code true} if all the followings are {@code true}
	 * <p>
	 * <ul>
	 * <li>The {@link Platform#isRunning() platform is running}.</li>
	 * <li>The platforms runs a {@link IProduct product}.</li>
	 * <li>The platforms runs the N4JS IDE product and it is configured to include built-in libraries.</li>
	 * <ul>
	 * <li>The N4JS IDE runs in production mode {@code OR}</li>
	 * <li>The N4JS IDE runs in either {@link Platform#inDebugMode() debug mode} or {@link Platform#inDevelopmentMode()
	 * development mode} and the {@link #INCLUDES_BUILT_INS_SYSTEM_PROPERTY} is configured to be {@code true}</li>
	 * </ul>
	 * </ul>
	 * Otherwise returns with {@code false} and neither built-in libraries nor local git repository for the N4JS
	 * definition files has to be set up .
	 *
	 * @return {@code true} if the infrastructure is required for the built-in and NPM support.
	 */
	public static boolean requiresInfrastructureForLibraryManager() {
		if (Platform.isRunning()) {
			final IProduct product = Platform.getProduct();
			if (null != product) {
				if (parseBoolean(product.getProperty(INCLUDES_BUILT_INS_PRODUCT_PROPERTY))) {
					// Runs in *non-production* mode and the system property is NOT set to include the built-ins.
					boolean includeBuiltins = parseBoolean(System.getProperty(INCLUDES_BUILT_INS_SYSTEM_PROPERTY));
					if ((inDebugMode() || inDevelopmentMode()) && !includeBuiltins) {
						return false;
					}
					return true;
				}
			}
		}
		return false;
	}

	/** Sorts given set of locations and returns sorted list */
	public static List<java.net.URI> sortByShadowing(Collection<java.net.URI> locations) {
		Map<String, java.net.URI> knownLocations = new HashMap<>();
		List<java.net.URI> unknownLocations = new LinkedList<>();

		for (java.net.URI location : locations) {
			String locStr = location.toString();
			locStr = locStr.endsWith("/") ? locStr.substring(0, locStr.length() - 1) : locStr;

			boolean locationFound = false;
			for (String knownLocation : CATEGORY_SHADOWING_ORDER) {
				if (locStr.endsWith(knownLocation) && !knownLocations.containsKey(knownLocation)) {
					knownLocations.put(knownLocation, location);
					locationFound = true;
				}
			}

			if (!locationFound) {
				unknownLocations.add(location);
			}
		}

		List<java.net.URI> sortedLocations = new LinkedList<>();
		for (String knownLocation : CATEGORY_SHADOWING_ORDER) {
			java.net.URI location = knownLocations.get(knownLocation);
			if (location != null) {
				sortedLocations.add(location);
			}
		}
		sortedLocations.addAll(unknownLocations);

		return sortedLocations;
	}
}
