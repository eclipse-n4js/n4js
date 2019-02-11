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
package org.eclipse.n4js.external.libraries;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Predicates.notNull;
import static com.google.common.base.Suppliers.memoize;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Maps.newLinkedHashMap;
import static java.lang.Boolean.parseBoolean;
import static org.eclipse.core.runtime.Platform.inDebugMode;
import static org.eclipse.core.runtime.Platform.inDevelopmentMode;
import static org.eclipse.xtext.util.Tuples.pair;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.internal.runtime.InternalPlatform;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.xtext.util.Pair;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * Activator for the bundle that holds all the external/built-in libraries.
 * <p>
 * Keep folder computations in sync with TargetPlatformInstallLocationProvider!
 */
@SuppressWarnings("restriction")
public class ExternalLibrariesActivator implements BundleActivator {

	private static final Logger LOGGER = Logger.getLogger(ExternalLibrariesActivator.class);

	/**
	 * Name of the top-level folder in the N4JS Git repository containing the main N4JS plugins.
	 */
	private static final String PLUGINS_FOLDER_NAME = "plugins"; // can't use N4JSGlobals.PLUGINS_FOLDER_NAME here

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

	/** Unique name of the N4JS language category. */
	public static final String LANG_CATEGORY = "lang";

	/** Unique name of the N4JS runtime category. */
	public static final String RUNTIME_CATEGORY = "runtime";

	/** Unique name of the Mangelhaft category. */
	public static final String MANGELHAFT_CATEGORY = "mangelhaft";

	/** Unique name of the {@code npm} category. */
	public static final String NPM_CATEGORY = "node_modules";

	/** List of all categories. Latter entries shadow former entries. */
	public static final List<String> CATEGORY_SHADOWING_ORDER = ImmutableList.<String> builder()
			.add(LANG_CATEGORY)
			.add(RUNTIME_CATEGORY)
			.add(MANGELHAFT_CATEGORY)
			.add(NPM_CATEGORY)
			.build();

	private static final Function<URL, URL> URL_TO_FILE_URL_FUNC = url -> {
		try {
			return FileLocator.toFileURL(url);
		} catch (final IOException e) {
			final String message = "Error while converting URL to file URL. " + url;
			LOGGER.error(message, e);
			throw new RuntimeException(message, e);
		}
	};

	private static final Function<URL, URI> URL_TO_URI_FUNC = url -> {
		if ("file".equals(url.getProtocol())) {
			return new File(url.getFile()).toURI();
		} else {
			final String message = "Unexpected protocol while trying to convert URL to URI." + url;
			LOGGER.error(message);
			return null;
		}
	};

	private static final Function<File, File> FILE_TO_CANONICAL_FILE = file -> {
		try {
			return file.getCanonicalFile();
		} catch (final IOException e) {
			final String message = "Error while getting the canonical file. " + file;
			LOGGER.error(message, e);
			throw new RuntimeException(message, e);
		}
	};

	/**
	 * Name of a folder located in this bundle's root folder, containing the runtime code to be shipped with the library
	 * manager (i.e. default runtime environments, <code>n4js.lang</code> with N4JS dependency injection support,
	 * mangelhaft). This folder will contain sub folders representing the categories showing up in the library manager
	 * UI.
	 */
	public static final String SHIPPED_CODE_FOLDER_NAME = "shipped-code";

	/**
	 * Returns the {@link #SHIPPED_CODE_FOLDER_NAME shipped code folder} or throws an {@link IllegalStateException} if
	 * the current working directory does not lie in an N4JS repository clone.
	 */
	public static Path getShippedCodeFolderPath() {
		final Path n4jsRepoRootPath = UtilN4.findN4jsRepoRootPath();
		return n4jsRepoRootPath.resolve(PLUGINS_FOLDER_NAME)
				.resolve(ExternalLibrariesActivator.PLUGIN_ID)
				.resolve(ExternalLibrariesActivator.SHIPPED_CODE_FOLDER_NAME);
	}

	/**
	 * An iterable of folder names that hold built-in N4JS libraries.
	 */
	public static final Collection<String> SHIPPED_ROOTS_FOLDER_NAMES = ImmutableList.<String> builder()
			.add(LANG_CATEGORY)
			.add(RUNTIME_CATEGORY)
			.add(MANGELHAFT_CATEGORY)
			.build();

	/**
	 * Unique symbolic name of the bundle that where this activator belongs to.
	 */
	public static final String PLUGIN_ID = "org.eclipse.n4js.external.libraries";

	/**
	 * Provides a mapping between the unique names of the external N4JS libraries and the human readable names.
	 */
	public static final Map<String, String> EXTERNAL_LIBRARY_NAMES = ImmutableMap.<String, String> builder()
			.put(LANG_CATEGORY, "N4JS Language")
			.put(RUNTIME_CATEGORY, "N4JS Runtime")
			.put(MANGELHAFT_CATEGORY, "Mangelhaft")
			.build();

	/**
	 * Supplies a one to one mapping between the available external built-in N4JS library root locations and the unique
	 * keywords for the libraries.
	 */
	public static final Supplier<BiMap<URI, String>> EXTERNAL_LIBRARIES_SUPPLIER = memoize(
			() -> getExternalLibraries());

	/** Shared private bundle context. */
	private static BundleContext context;

	/**
	 * Returns with the bundle context instance.
	 *
	 * @return the shared bundle context instance.
	 */
	public static BundleContext getContext() {
		return context;
	}

	@Override
	public void start(final BundleContext bundleContext) throws Exception {
		context = bundleContext;
	}

	@Override
	public void stop(final BundleContext bundleContext) throws Exception {
		context = null;
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

	/**
	 * Logs the given status to the platform log. Has no effect if the platform is not running or the bundle cannot be
	 * found.
	 *
	 * @param status
	 *            the status to log.
	 */
	public static void log(final IStatus status) {
		if (null != status && Platform.isRunning() && null != context) {
			final Bundle bundle = context.getBundle();
			if (null != bundle) {
				Platform.getLog(bundle).log(status);
			}
		}
	}

	/**
	 * Returns with the nested folder. Creates it if the folder does not exist yet.
	 *
	 * @param path
	 *            the name of the nested folder.
	 * @return the nested folder.
	 */
	public static synchronized File getOrCreateNestedFolder(String path) {
		checkState(Platform.isRunning(), "Expected running platform.");
		final Bundle bundle = context.getBundle();
		checkNotNull(bundle, "Bundle was null. Does the platform running?");
		final File targetPlatform = InternalPlatform.getDefault().getStateLocation(bundle).append(path).toFile();
		if (!targetPlatform.exists()) {
			checkState(targetPlatform.mkdirs(), "Error while creating " + targetPlatform + " folder.");
		}
		checkState(targetPlatform.isDirectory(), "Expecting director but was a file: " + targetPlatform + ".");
		return targetPlatform;
	}

	private static BiMap<URI, String> getExternalLibraries() {

		final Bundle bundle = context.getBundle();
		checkNotNull(bundle, "Bundle was null. Is the platform running?");

		final Iterable<Pair<URI, String>> uriNamePairs = from(SHIPPED_ROOTS_FOLDER_NAMES)
				.transform(name -> bundle.getResource(name))
				.filter(notNull())
				.transform(URL_TO_FILE_URL_FUNC)
				.transform(URL_TO_URI_FUNC)
				.filter(notNull())
				.transform(uri -> new File(uri))
				.transform(FILE_TO_CANONICAL_FILE)
				.transform(file -> file.toURI())
				.transform(uri -> pair(uri, new File(uri).getName()));

		final Map<URI, String> uriMappings = newLinkedHashMap();

		for (final Pair<URI, String> pair : uriNamePairs) {
			uriMappings.put(pair.getFirst(), pair.getSecond());
		}

		return ImmutableBiMap.copyOf(uriMappings);

	}

}
