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
package org.eclipse.n4js;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;

/**
 * Provides utility methods to work with zipped resource.
 */
public class TestCodeProvider {

	static Logger logger = Logger.getLogger("debugLibraryParsing");

	// /**
	// * Searches for entries in provided zipped resource. Returns a collection of pairs. The first element in each pair
	// * is a {@link ZipEntry} that points to the test data file while the second element in each pair is a blacklist of
	// * tests that are expected to fail. The blacklist is always empty. See also
	// * {@link #getDataFromZippedRoot(String, JSLibSingleTestConfigProvider)} where explicit blacklists can be passed.
	// *
	// * @param rootName
	// * the name of the archive file (classpath relative)
	// * @return the test data.
	// * @throws IOException
	// * if something wents wrong while reading the archive
	// */
	// public static Collection<JSLibSingleTestConfig> getDataFromZippedRoot(String rootName) throws URISyntaxException,
	// ZipException,
	// IOException {
	// return getDataFromZippedRoot(rootName, new JSLibSingleTestConfigProvider());
	// }

	/**
	 * Searches for entries in provided zipped resource. Returns a collection of pairs. The first element in each pair
	 * is a {@link ZipEntry} that points to the test data file while the second element in each pair is a blacklist of
	 * tests that are expected to fail.
	 *
	 * @param rootName
	 *            the fq-name of zip archive
	 * @param configProvider
	 *            factory for configurations
	 * @return the test data.
	 * @throws URISyntaxException
	 *             cannot happen
	 * @throws IOException
	 *             if something wents wrong while reading the archive or blacklist files
	 */
	public static Collection<JSLibSingleTestConfig> getDataFromZippedRoot(String rootName,
			JSLibSingleTestConfigProvider configProvider)
			throws IOException, URISyntaxException {

		URL rootURL = Thread.currentThread().getContextClassLoader().getResource(rootName);
		ZipFile root = new ZipFile(new File(rootURL.toURI()));
		Collection<JSLibSingleTestConfig> entries = new ArrayList<>();
		try {
			filterDataFilesFromZippedFolder(root, rootName, entries, configProvider);
		} finally {
			root.close();
		}

		return entries;
	}

	/**
	 * Searches for entries in provided zipped resource. Returns a collection of pairs. The first element in each pair
	 * is a {@link ZipEntry} that points to the test data file while the second element in each pair is a blacklist of
	 * tests that are expected to fail.
	 *
	 * @param rootNames
	 *            the names of the zip archives to be read.
	 * @param configProvider
	 *            provider creating {@link JSLibSingleTestConfig}s
	 * @return the test data.
	 * @throws URISyntaxException
	 *             cannot happen
	 * @throws IOException
	 *             if something wents wrong while reading the archive or blacklist files
	 */
	public static Collection<JSLibSingleTestConfig> getDataFromZippedRoots(Collection<String> rootNames,
			JSLibSingleTestConfigProvider configProvider)
			throws IOException, URISyntaxException {
		Collection<JSLibSingleTestConfig> mergeEntries = new ArrayList<>();
		for (String rootName : rootNames) {
			mergeEntries.addAll(getDataFromZippedRoot(rootName, configProvider));
		}
		return mergeEntries;
	}

	
	public static String getContentsFromFileEntry(final ZipEntry entry, String rootName) throws IOException,
			URISyntaxException {
		URL rootURL = Thread.currentThread().getContextClassLoader().getResource(rootName);
		try (final ZipFile root = new ZipFile(new File(rootURL.toURI()));) {
			ByteSource byteSource = new ByteSource() {
				@Override
				public InputStream openStream() throws IOException {
					return root.getInputStream(entry);
				}
			};

			CharSource charSrc = byteSource.asCharSource(Charsets.UTF_8);
			return charSrc.read();
		}
	}

	/**
	 * Saves to provided collection of {@link ZipEntry} that represents file in provided {@link ZipFile}
	 */
	private static void filterDataFilesFromZippedFolder(final ZipFile file, final String resourceName,
			Collection<JSLibSingleTestConfig> files, JSLibSingleTestConfigProvider configProvider) {
		Enumeration<? extends ZipEntry> entries = file.entries();
		Iterator<? extends ZipEntry> fileEntries = Iterators.filter(Iterators.forEnumeration(entries),
				new Predicate<ZipEntry>() {
					@Override
					public boolean apply(ZipEntry input) {
						return !input.isDirectory();
					}
				});
		List<ZipEntry> entriesAsList = Lists.newArrayList(fileEntries);
		files.addAll(Lists.transform(entriesAsList, new Function<ZipEntry, JSLibSingleTestConfig>() {
			@Override
			public JSLibSingleTestConfig apply(ZipEntry input) {
				return configProvider.createConfig(input, resourceName);
			}
		}));
	}

}
