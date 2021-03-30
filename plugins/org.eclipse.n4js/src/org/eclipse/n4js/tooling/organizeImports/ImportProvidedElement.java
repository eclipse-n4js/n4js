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
package org.eclipse.n4js.tooling.organizeImports;

import java.util.ArrayList;

import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TModule;

/**
 * Describing Elements provided by import statement and marking some state about visibility and usage.
 */
public class ImportProvidedElement {

	/** Prefix to be used when creating IPE for namespace itself */
	public final static String NAMESPACE_PREFIX = "NAMESPACE_";

	/** Name as used in script (can be alias) */
	private final String localname;

	/** Name as provided by TModule */
	private final String exportedName;

	/** Import specifier which imports the name. */
	private final ImportSpecifier importSpec;

	/** Imported module, providing this named element. */
	private final TModule importedModule;

	/** Usage-flag in script (statements & expressions) */
	private boolean used;

	/**
	 * In case of ambiguous imports the scoping gives a List imported things from other modules (variables, functions,
	 * classes,...). The instances carry the ref to those modules.
	 */
	private final ArrayList<IdentifiableElement> ambiguityList = new ArrayList<>();

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
		this.importedModule = ((ImportDeclaration) importer.eContainer()).getModule();
	}

	/**
	 * Marks this element as used.
	 */
	public void markUsed() {
		used = true;
	}

	/**
	 * Returns {@code true} if this element has been marked as used.
	 *
	 * @See {@link #markUsed()}
	 */
	public boolean isUsed() {
		return used;
	}

	/**
	 * Returns the local name of this element.
	 */
	public String getLocalName() {
		return localname;
	}

	/**
	 * Returns the exported name of this element.
	 */
	public String getExportedName() {
		return exportedName;
	}

	/**
	 * Returns the {@link ImportSpecifier} of this element.
	 */
	public ImportSpecifier getImportSpecifier() {
		return importSpec;
	}

	/**
	 * Returns the module from which this element was imported.
	 */
	public TModule getImportedModule() {
		return importedModule;
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
		return getLocalName() + (getLocalName() != getExportedName() ? "[" + getExportedName() + "]" : "") +
				(getImportSpecifier() instanceof NamedImportSpecifier ? "-" : "*") + "<"
				+ getImportedModule().getQualifiedName()
				+ "" + (used ? "+" : "-") + (ambiguityList != null ? "A" : "");
	}
}
