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
package org.eclipse.n4js.transpiler;

import java.io.IOException;
import java.io.Writer;

import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.n4js.transpiler.AbstractTranspiler.SourceMapInfo;
import org.eclipse.n4js.transpiler.print.PrettyPrinter;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 */
public class PrettyPrintingStep {

	@Inject
	private PrettyPrinter prettyPrinter;

	/**
	 * Writes the final intermediate model in the given transpiler state to target code and emits source maps
	 * (optional).
	 */
	public void print(TranspilerState state, Writer outCode, Optional<String> optPreamble,
			Optional<SourceMapInfo> optSourceMapInfo) {
		try {
			prettyPrinter.print(state, outCode, optPreamble, optSourceMapInfo);
		} catch (IOException e) {
			throw new WrappedException("exception while pretty-printing the intermediate model", e);
		}
	}
}
