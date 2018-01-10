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
package org.eclipse.n4js.transpiler.es.transform.internal

import org.eclipse.n4js.n4JS.ImportSpecifier
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.xtend.lib.annotations.Data

/**
 * Immutable ValueObject for transforming imports.
 */
@Data
class ImportAssignment {
	/* imported thing (symbol for exported thing from other file) */
	SymbolTableEntry ste;
	/* will be null iff we have a namespace import */
	String actualName;
	ImportSpecifier tobeReplacedIM;
	boolean isNameSpace;
}
