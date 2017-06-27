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
package org.eclipse.n4js.validation.helper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;

/**
 * Interface used for Algorithms assessing import statements.
 */
public interface ImportStateRegister {

	/**
	 * Mapping of name -> ImportSpecifier.
	 */
	public static class Name2Specifier {
		/** name as used in script (can be alias) */
		public final String name;
		/** import specifiers which imports the name. */
		public final ImportSpecifier importSpec;
		/** usage-flag in script (statements & expressions) */
		public boolean used = false;
		/**
		 * In case of ambiguous imports the scoping gives a List imported things from other modules. The instances carry
		 * the ref to those modules.
		 */
		public ArrayList<IdentifiableElement> ambiguityList = null;

		/**
		 * @param usedName
		 *            aliased Name for the imported entity
		 * @param importer
		 *            import-specifier, referencing the import-declaration.
		 */
		public Name2Specifier(String usedName, ImportSpecifier importer) {
			this.name = usedName;
			this.importSpec = importer;
		}

		/** set the used flag to true */
		public void markUsed() {
			used = true;
		}

	}

	/**
	 * Null-register, doing nothing.
	 */
	public static final ImportStateRegister NULLREGISTER = new ImportStateRegister() {

		@Override
		public void regEmptyWildcardImport(NamespaceImportSpecifier declaration) {
			// None
		}

		@Override
		public void regScatteredImport(ImportDeclaration declaration) {
			// None
		}

		@Override
		public void regUnusedImport(ImportSpecifier specifier) {
			// None
		}

		@Override
		public void regUnresolvedImport(NamedImportSpecifier specifier, ParameterizedTypeRef ref) {
			// None
		}

		@Override
		public void regDuplicate(NamedImportSpecifier specifier, NamedImportSpecifier specifier2) {
			// None
		}

		@Override
		public void regDuplicateAlias(NamedImportSpecifier specifier, NamedImportSpecifier specifier2Aliased) {
			// None
		}

		@Override
		public void regConflictingAliasType(NamedImportSpecifier specifierAliased, NamedImportSpecifier specifier2) {
			// None
		}

		@Override
		public void regConflictingAliases(NamedImportSpecifier specifierAliased,
				NamedImportSpecifier specifier2Aliased) {
			// None
		}

		@Override
		public void regConflict(NamedImportSpecifier specifier, ImportSpecifier specifier2) {
			// None
		}

		@Override
		public void cleanup() {
			// None
		}

		@Override
		public void regAllTypenameToSpecifierTuples(List<Name2Specifier> pairs) {
			// None
		}

	};

	/**
	 * Mark this Import as obsolete since the other side doesn't export anything
	 *
	 * @param declaration
	 *            ImportDeclaration of nothing.
	 */
	public abstract void regEmptyWildcardImport(NamespaceImportSpecifier declaration);

	/**
	 * Marking the import to be in an unsual place ( not in header section )
	 *
	 * @param declaration
	 *            import in unusual place.
	 */
	public abstract void regScatteredImport(ImportDeclaration declaration);

	/**
	 * Mark unused Import
	 *
	 * @param specifier
	 *            unused import
	 */
	public abstract void regUnusedImport(ImportSpecifier specifier);

	/**
	 * Register an unresolved import at it's referenced location.
	 *
	 * @param specifier
	 *            unresolved import
	 * @param ref
	 *            usage of the imported (unresolved) element
	 */
	public abstract void regUnresolvedImport(NamedImportSpecifier specifier, ParameterizedTypeRef ref);

	/**
	 * Simple Duplicate import
	 *
	 * @param specifier
	 *            without alias
	 * @param specifier2
	 *            the other
	 */
	public abstract void regDuplicate(NamedImportSpecifier specifier, NamedImportSpecifier specifier2);

	/**
	 * Duplicate alias, Imported from same Modul
	 *
	 * @param specifier
	 *            without alias
	 * @param specifier2Aliased
	 *            aliased duplicat import
	 */
	public abstract void regDuplicateAlias(NamedImportSpecifier specifier, NamedImportSpecifier specifier2Aliased);

	/**
	 * Conflicting Import
	 *
	 * @param specifierAliased
	 *            with alias name
	 * @param specifier2
	 *            without alias name
	 */
	public abstract void regConflictingAliasType(NamedImportSpecifier specifierAliased,
			NamedImportSpecifier specifier2);

	/**
	 * Conflicting alias imports (same thing with two different alias names)
	 *
	 * @param specifierAliased
	 *            the one
	 * @param specifier2Aliased
	 *            the other
	 */
	public abstract void regConflictingAliases(NamedImportSpecifier specifierAliased,
			NamedImportSpecifier specifier2Aliased);

	/**
	 * Conflicting Imports
	 *
	 * @param specifier
	 *            import1
	 * @param specifier2
	 *            import2
	 */
	public abstract void regConflict(NamedImportSpecifier specifier, ImportSpecifier specifier2);

	/**
	 * Called before destruction. Should free all references.
	 *
	 */
	public abstract void cleanup();

	/**
	 * Mapping of all current typenames (alias or real) and the relevant Importspecifier.
	 *
	 * @param pairs
	 *            alias(or typename) -> ImportSpecifier
	 */
	public abstract void regAllTypenameToSpecifierTuples(List<Name2Specifier> pairs);

}
