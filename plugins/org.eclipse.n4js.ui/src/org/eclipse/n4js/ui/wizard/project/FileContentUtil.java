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
package org.eclipse.n4js.ui.wizard.project;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Utility for converting String like objects to the
 */
public class FileContentUtil {

	/**
	 * Converts {@link String} to the {@link InputStream} with provided {@link Charset}.
	 *
	 */
	public static InputStream from(String content, Charset charset) {
		return new ByteArrayInputStream(content.getBytes(charset));
	}

	/**
	 * Converts {@link CharSequence} to the {@link InputStream} with provided {@link Charset}.
	 *
	 */
	public static InputStream from(CharSequence content, Charset charset) {
		return new ByteArrayInputStream(content.toString().getBytes(charset));
	}

}
