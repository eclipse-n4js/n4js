/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.factories;

import org.eclipse.n4js.flowgraphs.model.Symbol;
import org.eclipse.n4js.n4JS.VariableDeclaration;

/**
 * Creates {@link Symbol}s depending on the given AST element
 */
public class SymbolFactory {

	/**  */
	public static Symbol create(VariableDeclaration vd) {
		return new Symbol();
	}

}
