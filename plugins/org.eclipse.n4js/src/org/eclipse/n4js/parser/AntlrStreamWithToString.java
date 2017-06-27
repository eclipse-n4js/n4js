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
package org.eclipse.n4js.parser;

import java.io.IOException;
import java.io.Reader;

import org.antlr.runtime.ANTLRReaderStream;

/**
 * <p>An AntlrReaderStream that has a human readable {@link #toString()}
 * implementation, e.g. the contents that was read from the given Reader.</p>
 */
public class AntlrStreamWithToString extends ANTLRReaderStream {

	/**
	 * Creates a new Antlr stream with the contents read from the given reader.
	 */
	public AntlrStreamWithToString(Reader r) throws IOException {
		super(r);
	}

	@Override
	public String toString() {
		return String.valueOf(data, 0, n);
	}
}
