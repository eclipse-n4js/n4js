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
package org.eclipse.n4js.organize.imports;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TModule;

/**
 * Describing Elements provided by import statement and marking some state about visibility and usage.
 */
public class ImportProvidedElement {

	/** name as used in script (can be alias) */
	public String localname;

	/** name as provided by TModule */
	public String exportedName;
	/** Prefix to be used when creating IPE for namespace itself */
	public final static String NAMESPACE_PREFIX = "NAMESPACE_";

	/** import specifier which imports the name. */
	public ImportSpecifier importSpec;

	/** imported module, providing this named element. */
	public TModule tmodule;

	/** usage-flag in script (statements & expressions) */
	public boolean used;

	/**
	 * In case of ambiguous imports the scoping gives a List imported things from other modules (variables, functions,
	 * classes,...). The instances carry the ref to those modules.
	 */
	public ArrayList<IdentifiableElement> ambiguityList = new ArrayList<>();

	/**
	 * List of other Imports which provided ambiguous imports
	 */
	public List<ImportSpecifier> ambiguousImports = new ArrayList<>();

	/**
	 * @param localName
	 *            local name for the imported entity
	 * @param exportedName
	 *            name under which entity was exported
	 * @param importer
	 *            import-specifier, referencing the import-declaration.
	 *
	 */
	public ImportProvidedElement(String localName, String exportedName, ImportSpecifier importer) {
		this.localname = localName;
		this.exportedName = exportedName;
		this.importSpec = importer;
		this.tmodule = ((ImportDeclaration) importer.eContainer()).getModule();
	}

	/** set the used flag to true */
	public void markUsed() {
		used = true;
	}

	/**
	 * {@code localname[exportedName]<Module+øA} with
	 * <ul>
	 * <li>+ / - : used / unused
	 * <li>A : ambiguityList has entries.
	 * </ul>
	 */
	@Override
	public String toString() {
		return localname + (localname != exportedName ? "[" + exportedName + "]" : "") +
				(importSpec instanceof NamedImportSpecifier ? "-" : "*") + "<" + tmodule.getQualifiedName()
				+ "" + (used ? "+" : "-") + (ambiguityList != null ? "A" : "");
	}
}
