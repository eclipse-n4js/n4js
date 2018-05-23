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
package org.eclipse.n4js.jsdoc2spec.adoc;

import java.io.File;

import org.eclipse.n4js.jsdoc2spec.CopyrightHeader;

/**
 * A {@link SpecIndexFile} contains the content of the index.idx file.
 */
public class SpecIndexFile extends SpecListingFile {

	/**
	 * Constructor for a file
	 */
	public SpecIndexFile(File file, String newContent) {
		super(file, newContent);
	}

	/**
	 * Constructor for a file
	 */
	public SpecIndexFile(File file, String newContent, String packageDisplayName) {
		super(file, newContent, packageDisplayName);
	}

	@Override
	public String getCopyrightHeader() {
		return CopyrightHeader.getIdx();
	}

}
