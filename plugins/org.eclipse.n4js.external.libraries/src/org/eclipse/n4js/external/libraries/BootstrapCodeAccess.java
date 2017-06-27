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

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Throwables.getStackTraceAsString;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.StringJoiner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.n4js.utils.io.FileDeleter;

import com.google.common.base.StandardSystemProperty;

/**
 * Provides access to the bootstrap code for setups that don't work with proper workspace, e.g. unit tests for the
 * compiler.
 *
 * Normally access to the bootstrap code is resolved through the workspace (via N4JSCore and other interfaces), but in
 * some cases workspace is not possible to setup, or avoided on purpose. Most common examples are output unit tests or,
 * in general, setups not based on eclipse platform.
 *
 */
public class BootstrapCodeAccess {

	/** Folder name, from which bootstrap code is loaded. */
	protected static final String RUNTIME_ROOT = "runtime";

	/**
	 * Returns with the path for the default Node.js environment location as a string.
	 *
	 * @return the path pointing to the default Node.js environment.
	 */
	public static final String getDefaultNodeEnvPath() {
		try {
			URL resourceUrl = getResource(RUNTIME_ROOT);

			final URLConnection connection = resourceUrl.openConnection();
			if (connection instanceof JarURLConnection) {
				return recursivelyCopyContent((JarURLConnection) connection);
			}

			return new File(resourceUrl.toURI()).getCanonicalFile().getAbsolutePath().replace("\\", "\\\\");
		} catch (final Exception e) {
			throw new RuntimeException("Error while getting NODE_PATH.", e);
		}
	}

	private static String recursivelyCopyContent(JarURLConnection connection) throws IOException {
		final File tempFolder = getTempFolder().toFile();
		final File rootFolder = new File(tempFolder, RUNTIME_ROOT);
		if (rootFolder.exists()) {
			FileDeleter.delete(rootFolder.toPath());
		}
		checkState(rootFolder.mkdir(), "Error while creating folder for Node.js environment. " + rootFolder);
		checkState(rootFolder.isDirectory(), "Expected directory but got a file: " + rootFolder);
		rootFolder.deleteOnExit();

		try (final JarFile jarFile = connection.getJarFile()) {
			for (final Enumeration<JarEntry> em = jarFile.entries(); em.hasMoreElements(); /**/) {
				final JarEntry entry = em.nextElement();
				// Do not process anything which is not under the runtime environment folder
				if (!entry.getName().startsWith(RUNTIME_ROOT)) {
					continue;
				}
				final String fileName = entry.getName();// .substring(connection.getEntryName().length());
				final File newResource = new File(tempFolder, fileName);
				if (entry.isDirectory()) {
					if (!newResource.exists()) {
						checkState(newResource.mkdir(), "Error while creating new folder at: " + newResource);
					}
				} else {
					checkState(newResource.createNewFile(), "Error while creating new file at: " + newResource);
					try (final InputStream is = jarFile.getInputStream(entry)) {
						com.google.common.io.Files.copy(() -> is, newResource);
					}
				}
				newResource.deleteOnExit();
			}
		}
		return rootFolder.getCanonicalFile().getAbsolutePath().replace("\\", "\\\\");
	}

	private static Path getTempFolder() {
		final String tempFolder = StandardSystemProperty.JAVA_IO_TMPDIR.value();
		final File file = new File(tempFolder);
		if (!file.exists() || !file.canWrite()) {
			throw new RuntimeException("Cannot access temporary directory under: " + tempFolder);
		}
		return file.toPath();
	}

	/**
	 * Returns with the URL of the resource given its unique name. The URL may reference an absent file.
	 *
	 * @param resourceName
	 *            the name of the resource.
	 * @return the URL referencing to the resource given with its resource name.
	 */
	private static URL getResource(final String resourceName) {
		if (resourceName == null) {
			throw new RuntimeException("Resource name cannot be null.");
		}

		if (resourceName.isEmpty()) {
			throw new RuntimeException("Resource name cannot be empty.");
		}

		final String locator = "/" + resourceName;
		final URL resourceUrl = BootstrapCodeAccess.class.getResource(locator);
		if (resourceUrl == null) {
			throw new RuntimeException("Obtaining resource with locator <" + locator + "> returned null.");
		}

		if (!Platform.isRunning()) {
			return resourceUrl;
		}

		try {
			final URL transformedResourceUrl = FileLocator.toFileURL(resourceUrl);
			if (transformedResourceUrl == null) {
				throw new RuntimeException("Transforming resource with url <" + resourceUrl + "> returned null.");
			}
			return transformedResourceUrl;
		} catch (final Exception originalException) {
			StringJoiner errLog = new StringJoiner("\n");
			errLog.add("Tried to convert resource URL:: " + resourceUrl);
			errLog.add("Original error >>> ");
			errLog.add(getStackTraceAsString(originalException));
			errLog.add("<<< Original error");

			if (Platform.isRunning()) {
				final URL foundResource = FileLocator.find(resourceUrl);
				errLog.add("Finding resource by URL :: " + foundResource);
				if (foundResource != null) {
					try {
						errLog.add("Converting resource URL :: " + FileLocator.toFileURL(resourceUrl));
					} catch (IOException e1) {
						errLog.add("Resource URL conversion throws :: ");
						errLog.add(getStackTraceAsString(e1));
					}
				}
			} else {
				errLog.add("Platform is not running anymore (probably crashed). Cannot obtain more information.");
			}

			System.err.println(errLog.toString());

			// don't wrapped original exception, we have logged enough
			throw new RuntimeException("Error while converting resource.");
		}
	}
}
