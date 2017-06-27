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
package org.eclipse.n4js.tests.ecmatestsuite;

import java.io.IOException;
import java.util.Set;
import java.util.zip.ZipEntry;

import org.eclipse.n4js.JSLibSingleTestConfig;
import org.eclipse.n4js.JSLibSingleTestConfigProvider;

/**
 */
public class ECMAScriptSingleTestConfigProvider extends JSLibSingleTestConfigProvider {

	final Set<String> validators;

	/**
	 * Creates a provider with validator and blacklist awareness.
	 */
	public ECMAScriptSingleTestConfigProvider(String validatorListFileName, String... blacklistFileNames)
			throws IOException {
		super(blacklistFileNames);
		validators = readModifierFiles(validatorListFileName);
	}

	@Override
	public JSLibSingleTestConfig createConfig(ZipEntry entry, String resourceName) {
		final String entryName = entry.getName();
		final String modifier = (blacklist.contains(entryName))
				? JSLibSingleTestConfig.BLACKLIST :
				validators.contains(entryName) ? JSLibSingleTestConfig.VALIDATOR : null;
		return new JSLibSingleTestConfig(entry, resourceName, modifier);
	}
}
