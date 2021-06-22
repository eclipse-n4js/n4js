/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.dts;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Utility to copy the d.ts version of n4js built-in types (and others from n4js libs) into the output folder of every
 * project.
 */
public class N4jsBuiltinsDtsLoader {

	final static String FILE_NAME = "n4jsbuiltins.d.ts";

	/**
	 * Copies the d.ts version of n4js built-in types (and others from n4js libs) into the output folder of every
	 * project.
	 */
	public static void ensure(File outputFolder) throws IOException {
		ClassLoader classLoader = N4jsBuiltinsDtsLoader.class.getClassLoader();
		if (classLoader == null) {
			return;
		}
		try (InputStream in = classLoader.getResourceAsStream(FILE_NAME);) {
			if (in == null) {
				return;
			}
			Path target = new File(outputFolder, FILE_NAME).toPath();
			Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
		}
	}

}
