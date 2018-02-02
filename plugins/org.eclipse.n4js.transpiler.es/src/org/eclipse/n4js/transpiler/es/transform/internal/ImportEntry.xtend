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

import org.eclipse.xtend.lib.annotations.Data
import java.util.List
import org.eclipse.n4js.n4JS.ImportDeclaration

/**
 * Immutable ValueObject for import-rewriting.
 */
@Data
class ImportEntry {
	// this string will be used in the list of dependencies (i.e. 1st argument to the System.register() call)
	String actualModuleSpecifier
	// name of module-parameter passed into setter
	String fparName
	// Mappings of things to import. the actualName can be null, which means NamespaceImport
	List<ImportAssignment> variableSTE_actualName
	// for Tracing: IM-element which will be replaced:
	ImportDeclaration toBeReplacedImportDeclaration
}
