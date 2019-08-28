/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xpect.common;

import org.eclipse.xpect.runner.IXpectURIProvider;
import org.eclipse.xpect.runner.XpectRunner;
import org.eclipse.xpect.runner.XpectTestFiles;
import org.eclipse.xpect.runner.XpectTestFiles.Builder;
import org.eclipse.xpect.runner.XpectTestFiles.FileRoot;
import org.eclipse.xtext.util.Strings;
import org.junit.runners.model.InitializationError;

/**
 * This override fixes the issue that single .xt files can not be executed as JUnit tests when using the
 * {@link XpectRunner}.
 */
public class N4JSXpectRunner extends XpectRunner {

	/** Constructor */
	public N4JSXpectRunner(Class<?> testClass) throws InitializationError {
		super(testClass);
	}

	@Override
	protected IXpectURIProvider findUriProvider(Class<?> clazz) throws InitializationError {
		Builder builder = new XpectTestFiles.Builder().relativeTo(FileRoot.PROJECT);
		XpectTestFiles annotation = clazz.getAnnotation(XpectTestFiles.class);

		String baseDir = getBaseDir(annotation);
		if (!Strings.isEmpty(baseDir)) {
			builder.withBaseDir(baseDir);
		}

		String[] files = getFiles(annotation);
		if (files != null && files.length > 0) {
			for (String file : files) {
				String trimmed = file.trim();
				String removedBaseDir = trimmed.startsWith(baseDir) ? trimmed.substring(baseDir.length() + 1) : trimmed;
				if (!"".equals(removedBaseDir))
					builder.addFile(removedBaseDir);
			}
		}

		IXpectURIProvider provider = new SafeURIFileCollector(clazz, builder);
		return provider;
	}

	private String getBaseDir(XpectTestFiles annotation) {
		String baseDir = System.getProperty("xpectBaseDir");
		if (!Strings.isEmpty(baseDir)) {
			return baseDir;
		}

		baseDir = annotation == null ? "" : annotation.baseDir();
		if (!Strings.isEmpty(baseDir)) {
			return baseDir;
		}

		return null;
	}

	private String[] getFiles(XpectTestFiles annotation) {
		String filesString = System.getProperty("xpectFiles");
		if (!Strings.isEmpty(filesString)) {
			return filesString.split(";");
		}

		String[] files = annotation == null ? new String[0] : annotation.files();
		if (files != null && files.length > 0) {
			return files;
		}

		return null;
	}
}
