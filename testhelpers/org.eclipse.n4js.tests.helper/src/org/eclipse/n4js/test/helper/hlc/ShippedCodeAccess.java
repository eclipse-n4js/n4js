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
package org.eclipse.n4js.test.helper.hlc;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Throwables.getStackTraceAsString;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringJoiner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.n4js.utils.io.FileDeleter;

import com.google.common.base.StandardSystemProperty;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;

/**
 * Provides access to the shipped code for setups that don't work with proper workspace, e.g. unit tests for the
 * compiler.
 *
 * Normally access to the bootstrap code is resolved through the workspace (via N4JSCore and other interfaces), but in
 * some cases workspace is not possible to setup, or avoided on purpose. Most common examples are output unit tests or,
 * setups not based on eclipse platform.
 */
public class ShippedCodeAccess {

	/**
	 * Returns with the iterable paths for the shipped code locations as a string.
	 */
	public static final Iterable<String> getAllShippedPaths() {
		List<String> paths = new ArrayList<>();
		// ExternalLibrariesActivator.SHIPPED_ROOTS_FOLDER_NAMES
		// .forEach(root -> paths.add(getShippedRuntimeCodePath(root)));
		return paths;
	}

	/**
	 * Returns path for the shipped code from the provided location. If location is plain file returns its absolute path
	 * as string. If location is inside jar file, unpacks desired resource to the temporal location and returns path to
	 * that location.
	 *
	 * @param rootName
	 *            name of shipped root to be located
	 * @return the path pointing to the shipped code
	 */
	private static String getShippedRuntimeCodePath(String rootName) {
		try {
			URL resourceUrl = getResource(rootName);

			final URLConnection connection = resourceUrl.openConnection();
			if (connection instanceof JarURLConnection) {
				return recursivelyCopyContent((JarURLConnection) connection, rootName);
			}

			return new File(resourceUrl.toURI()).getCanonicalFile().getAbsolutePath().replace("\\", "\\\\");
		} catch (final Exception e) {
			throw new RuntimeException("Error while getting shipped code path.", e);
		}
	}

	/**
	 * Unpacks desired folder structure from the JAR file into temporal location and returns that location absolute path
	 * as string.
	 *
	 * @param connection
	 *            connection to the JAR file
	 * @param rootName
	 *            name of the folder to be unpacked
	 * @return absolute path to the temporarily unpacked folder
	 * @throws IOException
	 *             for IO operations
	 */
	private static String recursivelyCopyContent(JarURLConnection connection, String rootName) throws IOException {
		final File tempFolder = getTempFolder();
		tempFolder.deleteOnExit();
		final File rootFolder = new File(tempFolder, rootName);
		if (rootFolder.exists()) {
			FileDeleter.delete(rootFolder.toPath());
		}
		checkState(rootFolder.mkdir(), "Error while creating folder for Node.js environment. " + rootFolder);
		checkState(rootFolder.isDirectory(), "Expected directory but got a file: " + rootFolder);
		rootFolder.deleteOnExit();

		try (final JarFile jarFile = connection.getJarFile()) {
			for (final Enumeration<JarEntry> em = jarFile.entries(); em.hasMoreElements(); /**/) {
				final JarEntry entry = em.nextElement();
				// Do not process anything which is not under desired root
				if (!entry.getName().startsWith(rootName)) {
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
					ByteSink byteSink = Files.asByteSink(newResource);
					try (
							final InputStream is = jarFile.getInputStream(entry);
							OutputStream outputStream = byteSink.openStream();) {
						ByteStreams.copy(is, outputStream);
					}
				}
				newResource.deleteOnExit();
			}
		}
		return rootFolder.getCanonicalFile().getAbsolutePath().replace("\\", "\\\\");
	}

	private static File getTempFolder() {
		final String tempFolder = StandardSystemProperty.JAVA_IO_TMPDIR.value();
		final File file = new File(tempFolder);
		if (!file.exists() || !file.canWrite()) {
			throw new RuntimeException("Cannot access temporary directory under: " + tempFolder);
		}
		return file;
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
		final URL resourceUrl = ShippedCodeAccess.class.getResource(locator);
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
