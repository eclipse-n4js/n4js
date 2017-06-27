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
package org.eclipse.n4js.xpect.ui.results;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.runner.Description;
import org.xpect.runner.IXpectURIProvider;
import org.xpect.runner.XpectRunner;

import org.eclipse.n4js.xpect.ui.N4IDEXpectUIPlugin;
import org.eclipse.n4js.xpect.ui.runner.N4IDEXpectTestFilesCollector.N4IDEXpectTestURIProvider;

/**
 * Utility for creating {@link XpectFileContentAccess}
 */
public class XpectFileContentsUtil {

	/**
	 * Provides access to contents of the specific xpect test file. May provide additional convenience methods for that
	 * file contents. Does not provide access to the file, nor any way to modify the file.
	 */
	public static class XpectFileContentAccess {
		private final String contents;
		private final boolean containsFixme;

		private XpectFileContentAccess(String contents, boolean containsFixme) {
			this.contents = contents;
			this.containsFixme = containsFixme;
		}

		/**
		 * @return contents of the file as string
		 */
		public String getContetns() {
			return this.contents;
		}

		/**
		 * Convenience method checks if contents has expectation marked with fixme marker
		 *
		 * @return true if one or more fixme found
		 */
		public boolean containsFixme() {
			return this.containsFixme;
		}
	}

	/**
	 * Create instance of {@link XpectFileContentAccess} for given {@link Description}. If xpect file cannot be accessed
	 * or resolved from provided description returns empty {@link Optional}
	 *
	 * @return {@link Optional}<{@link XpectFileContentAccess}>
	 */
	public static Optional<XpectFileContentAccess> getXpectFileContentAccess(Description description) {

		String contents = "";
		boolean containsFixme = false;

		IXpectURIProvider xpectUriProvider = XpectRunner.INSTANCE.getUriProvider();
		if (xpectUriProvider instanceof N4IDEXpectTestURIProvider) {
			N4IDEXpectTestURIProvider n4XpectUriProvider = (N4IDEXpectTestURIProvider) xpectUriProvider;
			String rawFileLocation = n4XpectUriProvider.findRawLocation(description);

			try {
				contents = new String(Files.readAllBytes(Paths.get(rawFileLocation)));
			} catch (IOException e) {
				N4IDEXpectUIPlugin.logError("cannot read contents of test file", e);
				e.printStackTrace();
				return Optional.empty();
			}
		}
		containsFixme = contents.split("XPECT\\sFIXME").length > 1;
		XpectFileContentAccess xpectFileContetnsAccess = new XpectFileContentAccess(contents, containsFixme);

		return Optional.of(xpectFileContetnsAccess);
	}
}
