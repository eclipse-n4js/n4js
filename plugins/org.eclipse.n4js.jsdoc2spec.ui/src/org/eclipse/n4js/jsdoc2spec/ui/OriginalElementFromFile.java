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
package org.eclipse.n4js.jsdoc2spec.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.eclipse.compare.IEncodedStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Image;

/**
 * Comparison element for original (doc) files.
 */
class OriginalElementFromFile implements ITypedElement, IEncodedStreamContentAccessor {
	protected File file;

	public OriginalElementFromFile(File path) {
		this.file = path;
	}

	@Override
	public String getName() {
		return "<no name>"; //$NON-NLS-1$
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public String getType() {
		return TEXT_TYPE;
	}

	@Override
	public InputStream getContents() {
		InputStream str = null;
		try {
			str = new FileInputStream(file);
		} catch (Exception e) {
			// ignore exception
		}

		return str;
	}

	@Override
	public String getCharset() throws CoreException {
		return "UTF-8"; //$NON-NLS-1$
	}
}
