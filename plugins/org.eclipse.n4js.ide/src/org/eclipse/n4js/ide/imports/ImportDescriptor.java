/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.imports;

import java.util.Objects;

import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.workspace.utils.N4JSProjectName;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.naming.QualifiedName;

/**
 * Describes a single N4JS import in a normalized form, without the need of having AST nodes available. Can be
 * <ul>
 * <li>sorted,
 * <li>added to a set to get rid of duplicate imports,
 * <li>converted back to code (see {@link #toCode(String, IValueConverterService, N4JSGrammarAccess) #toCode()}).
 * </ul>
 */
public class ImportDescriptor implements Comparable<ImportDescriptor> {

	private final boolean isNamed;
	private final boolean isNamespace;
	private final boolean isDefault;
	private final boolean isDynamic;
	private final String elementName;
	private final String alias;
	private final String moduleSpecifier;
	private final QualifiedName moduleFQN;

	private final int originalIndex;

	private ImportDescriptor(boolean isNamed, boolean isNamespace, boolean isDefault, boolean isDynamic,
			String elementName, String alias, String moduleSpecifier, QualifiedName moduleFQN, int originalIndex) {
		if (isDefault && !isNamed) {
			throw new IllegalArgumentException("default imports must be named imports");
		}
		if (isDynamic && !isNamespace) {
			throw new IllegalArgumentException("dynamic imports must be namespace imports");
		}
		if (elementName != null && !isNamed) {
			throw new IllegalArgumentException("elementName may only be non-null for named imports");
		}
		Objects.requireNonNull(moduleSpecifier);
		Objects.requireNonNull(moduleFQN);
		this.isNamed = isNamed;
		this.isNamespace = isNamespace;
		this.isDefault = isDefault;
		this.isDynamic = isDynamic;
		this.elementName = isDefault ? N4JSLanguageConstants.EXPORT_DEFAULT_NAME : elementName;
		this.alias = alias;
		this.moduleSpecifier = moduleSpecifier;
		this.moduleFQN = moduleFQN;
		this.originalIndex = originalIndex;
	}

	/** Create a bare import. */
	public static ImportDescriptor createBareImport(String moduleSpecifier, N4JSProjectName targetProjectName,
			QualifiedName targetModule, int originalIndex) {

		boolean isNamed = false;
		boolean isNamespace = false;
		boolean isDefault = false;
		boolean isDynamic = false;
		String elementName = null;
		String alias = null;
		QualifiedName moduleFQN = getFQN(targetProjectName, targetModule);

		return new ImportDescriptor(isNamed, isNamespace, isDefault, isDynamic, elementName, alias, moduleSpecifier,
				moduleFQN, originalIndex);
	}

	/** Create a named import. */
	public static ImportDescriptor createNamedImport(String elementName, String alias, String moduleSpecifier,
			N4JSProjectName targetProjectName, QualifiedName targetModule, int originalIndex) {

		boolean isNamed = true;
		boolean isNamespace = false;
		boolean isDefault = false;
		boolean isDynamic = false;
		QualifiedName moduleFQN = getFQN(targetProjectName, targetModule);

		return new ImportDescriptor(isNamed, isNamespace, isDefault, isDynamic, elementName, alias, moduleSpecifier,
				moduleFQN, originalIndex);
	}

	/** Create a default import. */
	public static ImportDescriptor createDefaultImport(String localName, String moduleSpecifier,
			N4JSProjectName targetProjectName, QualifiedName targetModule, int originalIndex) {

		boolean isNamed = true;
		boolean isNamespace = false;
		boolean isDefault = true;
		boolean isDynamic = false;
		String elementName = null;
		String alias = localName;
		QualifiedName moduleFQN = getFQN(targetProjectName, targetModule);

		return new ImportDescriptor(isNamed, isNamespace, isDefault, isDynamic, elementName, alias, moduleSpecifier,
				moduleFQN, originalIndex);
	}

	/** Create a namespace import. */
	public static ImportDescriptor createNamespaceImport(String localNamespaceName, boolean isDynamic,
			String moduleSpecifier, N4JSProjectName targetProjectName, QualifiedName targetModule, int originalIndex) {

		boolean isNamed = false;
		boolean isNamespace = true;
		boolean isDefault = false;
		String elementName = null;
		String alias = localNamespaceName;
		QualifiedName moduleFQN = getFQN(targetProjectName, targetModule);

		return new ImportDescriptor(isNamed, isNamespace, isDefault, isDynamic, elementName, alias, moduleSpecifier,
				moduleFQN, originalIndex);
	}

	private static QualifiedName getFQN(N4JSProjectName projectName, QualifiedName moduleName) {
		return projectName.toQualifiedName().append(moduleName);
	}

	/** Tells if this import is a bare import. */
	public boolean isBare() {
		return !isNamed && !isNamespace;
	}

	@Override
	public int hashCode() {
		// don't include 'moduleSpecifier' and 'originalIndex', because ...
		// 1) the module specifier is redundant to 'moduleFQN' (only required to capture the desired module
		// specifier form),
		// 2) two imports that differ only in original index should be deemed duplicates.
		return Objects.hash(isNamed, isNamespace, isDefault, isDynamic, elementName, alias, moduleFQN);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ImportDescriptor)) {
			return false;
		}
		return compareTo((ImportDescriptor) other) == 0;
	}

	@Override
	public int compareTo(ImportDescriptor other) {
		// 1) sort by "bareness" (i.e. move bare imports to the front)
		int cmpBareness = Boolean.compare(this.isBare(), other.isBare()) * -1;
		if (cmpBareness != 0) {
			return cmpBareness;
		}
		// 2) sort bare imports by their original index (i.e. keep their order relative to themselves)
		if (this.isBare()) {
			// both are bare imports:
			if (Objects.equals(this.moduleFQN, other.moduleFQN)) {
				return 0;
			}
			return Integer.compare(this.originalIndex, other.originalIndex);
		}
		// NOW: both are non-bare imports ...
		// 3) sort by module's fully qualified name
		int cmpModule = this.moduleFQN.compareTo(other.moduleFQN);
		if (cmpModule != 0) {
			return cmpModule;
		}
		// 4) move namespace imports to the front
		int cmpNamespace = Boolean.compare(this.isNamespace, other.isNamespace) * -1;
		if (cmpNamespace != 0) {
			return cmpNamespace;
		}
		if (this.isNamespace && other.isNamespace) {
			// Two namespace imports for the same target module is an error case. Still, we want to handle this case
			// gracefully.
			// -> sort by name of namespace and isDynamic
			int cmpAlias = this.alias.compareTo(other.alias);
			if (cmpAlias != 0) {
				return cmpAlias;
			}
			return Boolean.compare(this.isDynamic, other.isDynamic) * -1;
		}
		// NOW: both are named imports ...
		// 5) move default imports to the top
		int cmpDefault = Boolean.compare(this.isDefault, other.isDefault) * -1;
		if (cmpDefault != 0) {
			return cmpDefault;
		}
		// 6) sort by name of imported element
		int cmpName = this.elementName.compareTo(other.elementName);
		if (cmpName != 0) {
			return cmpName;
		}
		// 7) sort by alias
		int cmpAlias = this.alias != null && other.alias != null
				? this.alias.compareTo(other.alias)
				: Boolean.compare(this.alias != null, other.alias != null);
		return cmpAlias;
	}

	/** Emit N4JS source code for this import. */
	public String toCode(String spacing, IValueConverterService valueConverter, N4JSGrammarAccess grammarAccess) {
		StringBuilder sb = new StringBuilder(128);
		sb.append("import ");
		if (isNamed) {
			if (isDefault) {
				sb.append(alias);
			} else {
				sb.append('{');
				sb.append(spacing);
				sb.append(elementName);
				if (alias != null) {
					sb.append(" as ");
					sb.append(alias);
				}
				sb.append(spacing);
				sb.append('}');
			}
			sb.append(' ');
		} else if (isNamespace) {
			sb.append("* as ");
			sb.append(alias);
			if (isDynamic) {
				sb.append('+');
			}
			sb.append(' ');
		}
		if (!isBare()) {
			sb.append("from ");
		}
		if (valueConverter != null && grammarAccess != null) {
			String syntacticModuleSpecifier = valueConverter.toString(moduleSpecifier,
					grammarAccess.getModuleSpecifierRule().getName());
			sb.append(syntacticModuleSpecifier);
		} else {
			sb.append('"');
			sb.append(moduleSpecifier);
			sb.append('"');
		}
		sb.append(';');
		return sb.toString();
	}
}
