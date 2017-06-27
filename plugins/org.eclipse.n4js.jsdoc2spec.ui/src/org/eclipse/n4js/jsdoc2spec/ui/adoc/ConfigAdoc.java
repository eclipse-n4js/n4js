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
package org.eclipse.n4js.jsdoc2spec.ui.adoc;

import java.io.File;

/**
 * Data holder for the configuration done by the user on the {@link SpecConfigAdocPage}.
 */
class ConfigAdoc {
	final boolean genAdoc;
	final String docRootDir;

	private File docRootDirFile;

	ConfigAdoc(String docRootDir, boolean genAdoc) {
		this.docRootDir = docRootDir;
		this.genAdoc = genAdoc;
	}

	File getDocRootDir() {
		if (docRootDirFile == null)
			docRootDirFile = new File(docRootDir);
		return docRootDirFile;
	}
}
