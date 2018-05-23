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

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;

import com.google.common.base.Charsets;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;

/**
 * Base class for factories creating {@link JSLibSingleTestConfig} with some additional functionality to be aware of
 * blacklists (that is, tests which are supposed to fail). This default implementation supports a blacklist, if the
 * entry is found in the black list, it is marked accordingly. Subclasses may adjust that behavior.
 */
public class JSLibSingleTestConfigProvider {

	/**
	 * Line comment char for black lists.
	 */
	public static final String BLACKLIST_LINECOMMENT = "#";

	/**
	 * The black list, merger of all blacklist provided in constructor.
	 */
	protected Set<String> blacklist;

	/**
	 * Creates a provider with given black list filenames. These black lists are all merged together.
	 */
	public JSLibSingleTestConfigProvider(String... blacklistFileNames) throws IOException {
		blacklist = readModifierFiles(blacklistFileNames);
	}

	/**
	 * Creates a configuration from an entry in the given resource.
	 */
	public JSLibSingleTestConfig createConfig(ZipEntry entry, String resourceName) {
		final String entryName = entry.getName();
		final String modifier = (blacklist.contains(entryName)) ? JSLibSingleTestConfig.BLACKLIST : null;
		return new JSLibSingleTestConfig(entry, resourceName, modifier);
	}

	/**
	 * Merges several blacklists into one, may be used in custom {@link JSLibSingleTestConfigProvider} implementation.
	 */
	protected static Set<String> readModifierFiles(String... blacklistFileNames) throws IOException {
		Set<String> blacklist = new HashSet<>();
		if (blacklistFileNames != null) {
			for (String blacklistFileName : blacklistFileNames) {
				Iterable<String> entries = Iterables.filter(getFileLines(blacklistFileName), new Predicate<String>() {
					@Override
					public boolean apply(String s) {
						return !s.startsWith(BLACKLIST_LINECOMMENT) && !s.trim().isEmpty();
					}
				});
				for (String entry : entries) {
					if (!blacklist.add(entry)) {
						System.err.println("Duplicate blacklist entry: " + entry);
					}
				}
			}
		}
		return blacklist;
	}

	/**
	 * @param resourceName
	 *            the classpath-relative location of the to-be-read resource
	 */
	private static List<String> getFileLines(final String resourceName) throws IOException {
		ByteSource byteSource = new ByteSource() {
			@Override
			public InputStream openStream() throws IOException {
				return Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
			}
		};

		CharSource charSrc = byteSource.asCharSource(Charsets.UTF_8);
		return charSrc.readLines();
	}

}
