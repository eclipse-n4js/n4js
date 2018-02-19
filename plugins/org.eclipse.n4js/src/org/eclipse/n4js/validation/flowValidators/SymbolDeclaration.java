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
package org.eclipse.n4js.validation.flowValidators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.Symbol;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;

/**
 *
 */
public class SymbolDeclaration {

	/**
	 * @param symbol
	 *            the {@link Symbol} of whose declaration AST element is asked for
	 * @param ts
	 *            the {@link N4JSTypeSystem} to search the declaration if necessary
	 * @return the declaration AST element such as {@link VariableDeclaration}
	 */
	public static EObject get(Symbol symbol, N4JSTypeSystem ts) {
		EObject decl = symbol.getDeclaration();
		if (decl != null) {
			return decl;
		}

		return null;
	}

}
