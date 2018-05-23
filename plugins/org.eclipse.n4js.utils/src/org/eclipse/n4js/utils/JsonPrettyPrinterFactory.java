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
package org.eclipse.n4js.utils;

import java.io.IOException;
import java.io.Writer;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.IOContext;

/**
 * Factory for prettier JSON output than the default one.
 *
 * @see JsonPrettyPrinter
 */
public class JsonPrettyPrinterFactory extends JsonFactory {

	@Override
	protected JsonGenerator _createGenerator(final Writer out, final IOContext ctxt) throws IOException {
		return super._createGenerator(out, ctxt).setPrettyPrinter(new JsonPrettyPrinter());
	}

}
