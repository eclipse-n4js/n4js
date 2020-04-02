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
package org.eclipse.n4js.ide.server.imports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.eclipse.lsp4j.TextEdit;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.ide.editor.contentassist.imports.ImportRegionHelper;
import org.eclipse.n4js.ide.server.codeActions.util.ChangeProvider;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.organize.imports.ImportSpecifiersUtil;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.nodemodel.NodeModelUtilsN4;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Functionality for organizing imports. See {@link #organizeImports(Document, Script, Collection, CancelIndicator)
 * #organizeImports()}.
 */
@Singleton
public class ImportOrganizer {

	@Inject
	private ImportRegionHelper importRegionHelper;

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	/**
	 * Returns {@link TextEdit}s for organizing the imports of the given script, i.e.
	 * <ul>
	 * <li>removing unused imports,
	 * <li>sorting imports,
	 * <li>normalizing imports (e.g. only a single import specifier per import declaration).
	 * </ul>
	 */
	public List<TextEdit> organizeImports(Document document, Script script,
			Collection<ImportRef> importsToBeAdded, CancelIndicator cancelIndicator) {
		// we rely on flag 'ImportSpecifier#flaggedUsedInCode', so ensure that post-processing was done:
		((N4JSResource) script.eResource()).performPostProcessing(cancelIndicator);

		List<ImportDeclaration> importDecls = script.getScriptElements().stream()
				.filter(ImportDeclaration.class::isInstance)
				.map(ImportDeclaration.class::cast)
				.collect(Collectors.toList());

		SortedSet<ImportRef> importRefs = createImportRefs(importDecls);
		importRefs.addAll(importsToBeAdded);

		List<TextEdit> textEdits = new ArrayList<>();
		int offsetOfFirstImport = Integer.MAX_VALUE;

		// create edits for removal of all old imports
		for (ImportDeclaration importDecl : importDecls) {
			ICompositeNode node = NodeModelUtils.findActualNodeFor(importDecl);
			int offset = node.getOffset();
			int length = NodeModelUtilsN4.getNodeLengthWithASISupport(node);
			TextEdit edit = ChangeProvider.removeText(document, offset, length, true);
			textEdits.add(edit);

			offsetOfFirstImport = Math.min(offsetOfFirstImport, node.getOffset());
		}
		textEdits = ChangeProvider.closeGapsIfEmpty(document, textEdits);

		// if we do not have any existing imports, find correct location for adding new imports
		if (offsetOfFirstImport == Integer.MAX_VALUE) {
			offsetOfFirstImport = importRegionHelper.findInsertionOffset(script);
		}

		// create edit for inserting the new imports
		String[] importStrings = importRefs.stream()
				.map(ImportRef::toCode)
				.toArray(String[]::new);
		if (importStrings.length > 0) {
			TextEdit edit = ChangeProvider.insertLinesAbove(document, offsetOfFirstImport, false, importStrings);
			textEdits.add(edit);
		}

		return textEdits;
	}

	private SortedSet<ImportRef> createImportRefs(List<ImportDeclaration> importDecls) {
		SortedSet<ImportRef> result = new TreeSet<>();
		int idx = 0;
		for (ImportDeclaration importDecl : importDecls) {
			if (importDecl.isBare()) {
				if (ImportSpecifiersUtil.isBrokenImport(importDecl)) {
					continue;
				}
				ImportRef importRef = createFromAST(importDecl, null, idx++);
				result.add(importRef);
			} else {
				for (ImportSpecifier importSpec : importDecl.getImportSpecifiers()) {
					if (!importSpec.isFlaggedUsedInCode()) {
						continue;
					}
					if (ImportSpecifiersUtil.isBrokenImport(importSpec)) {
						continue;
					}
					ImportRef importRef = createFromAST(importDecl, importSpec, idx++);
					result.add(importRef);
				}
			}
		}
		return result;
	}

	private ImportRef createFromAST(ImportDeclaration importDecl, ImportSpecifier importSpec, int originalIndex) {
		if (importSpec != null && !importSpec.isFlaggedUsedInCode()) {
			throw new IllegalArgumentException("must not create an ImportRef for unused imports");
		}
		if ((importSpec == null && ImportSpecifiersUtil.isBrokenImport(importDecl))
				|| (importSpec != null && ImportSpecifiersUtil.isBrokenImport(importSpec))) {
			throw new IllegalArgumentException("must not create an ImportRef for broken imports");
		}

		TModule module = importDecl.getModule();
		String targetProjectName = module.getProjectName();
		QualifiedName targetModule = qualifiedNameConverter.toQualifiedName(module.getQualifiedName());

		String moduleSpecifier = importDecl.getModuleSpecifierAsText();
		if (importDecl.isBare()) {
			return ImportRef.createBareImport(moduleSpecifier, targetProjectName, targetModule, originalIndex);
		} else {
			if (importSpec instanceof NamedImportSpecifier) {
				NamedImportSpecifier importSpecCasted = (NamedImportSpecifier) importSpec;
				if (importSpecCasted.isDefaultImport()) {
					String localName = importSpecCasted.getAlias();
					return ImportRef.createDefaultImport(localName, moduleSpecifier, targetProjectName, targetModule,
							originalIndex);
				} else {
					String elementName = importSpecCasted.getImportedElementAsText();
					String alias = importSpecCasted.getAlias();
					return ImportRef.createNamedImport(elementName, alias, moduleSpecifier, targetProjectName,
							targetModule,
							originalIndex);
				}
			} else if (importSpec instanceof NamespaceImportSpecifier) {
				String localNamespaceName = ((NamespaceImportSpecifier) importSpec).getAlias();
				return ImportRef.createNamespaceImport(localNamespaceName, moduleSpecifier, targetProjectName,
						targetModule,
						originalIndex);
			} else if (importSpec != null) {
				throw new IllegalArgumentException(
						"unknown subclass of ImportSpecifier: " + importSpec.getClass().getSimpleName());
			} else {
				throw new IllegalArgumentException("importSpec may be null only if importDecl is a bare import");
			}
		}
	}

	public static class ImportRef implements Comparable<ImportRef> {

		final boolean isNamed;
		final boolean isNamespace;
		final boolean isDefault;
		final String elementName;
		final String alias;
		final String moduleSpecifier;
		final QualifiedName moduleFQN;

		final int originalIndex;

		private ImportRef(boolean isNamed, boolean isNamespace, boolean isDefault, String elementName, String alias,
				String moduleSpecifier, QualifiedName moduleFQN, int originalIndex) {
			if (isDefault && !isNamed) {
				throw new IllegalArgumentException("default imports must be named imports");
			}
			if (elementName != null && !isNamed) {
				throw new IllegalArgumentException("elementName may only be non-null for named imports");
			}
			Objects.requireNonNull(moduleSpecifier);
			Objects.requireNonNull(moduleFQN);
			this.isNamed = isNamed;
			this.isNamespace = isNamespace;
			this.isDefault = isDefault;
			this.elementName = isDefault ? N4JSLanguageConstants.EXPORT_DEFAULT_NAME : elementName;
			this.alias = alias;
			this.moduleSpecifier = moduleSpecifier;
			this.moduleFQN = moduleFQN;
			this.originalIndex = originalIndex;
		}

		public static ImportRef createBareImport(String moduleSpecifier, String targetProjectName,
				QualifiedName targetModule, int originalIndex) {

			boolean isNamed = false;
			boolean isNamespace = false;
			boolean isDefault = false;
			String elementName = null;
			String alias = null;
			QualifiedName moduleFQN = getFQN(targetProjectName, targetModule);

			return new ImportRef(isNamed, isNamespace, isDefault, elementName, alias, moduleSpecifier, moduleFQN,
					originalIndex);
		}

		public static ImportRef createNamedImport(String elementName, String alias, String moduleSpecifier,
				String targetProjectName, QualifiedName targetModule, int originalIndex) {

			boolean isNamed = true;
			boolean isNamespace = false;
			boolean isDefault = false;
			QualifiedName moduleFQN = getFQN(targetProjectName, targetModule);

			return new ImportRef(isNamed, isNamespace, isDefault, elementName, alias, moduleSpecifier, moduleFQN,
					originalIndex);
		}

		public static ImportRef createDefaultImport(String localName, String moduleSpecifier, String targetProjectName,
				QualifiedName targetModule, int originalIndex) {

			boolean isNamed = true;
			boolean isNamespace = false;
			boolean isDefault = true;
			String elementName = null;
			String alias = localName;
			QualifiedName moduleFQN = getFQN(targetProjectName, targetModule);

			return new ImportRef(isNamed, isNamespace, isDefault, elementName, alias, moduleSpecifier, moduleFQN,
					originalIndex);
		}

		public static ImportRef createNamespaceImport(String localNamespaceName, String moduleSpecifier,
				String targetProjectName, QualifiedName targetModule, int originalIndex) {

			boolean isNamed = false;
			boolean isNamespace = true;
			boolean isDefault = false;
			String elementName = null;
			String alias = localNamespaceName;
			QualifiedName moduleFQN = getFQN(targetProjectName, targetModule);

			return new ImportRef(isNamed, isNamespace, isDefault, elementName, alias, moduleSpecifier, moduleFQN,
					originalIndex);
		}

		private static QualifiedName getFQN(String projectName, QualifiedName moduleName) {
			return QualifiedName.create(projectName).append(moduleName);
		}

		public boolean isBare() {
			return !isNamed && !isNamespace;
		}

		@Override
		public int hashCode() {
			// don't include 'moduleSpecifier' and 'originalIndex', because ...
			// 1) the module specifier is redundant to 'moduleFQN' (only required to capture the desired module
			// specifier form),
			// 2) two imports that differ only in original index should be deemed duplicates.
			return Objects.hash(isNamed, isNamespace, isDefault, elementName, alias, moduleFQN);
		}

		@Override
		public boolean equals(Object other) {
			if (!(other instanceof ImportRef)) {
				return false;
			}
			return compareTo((ImportRef) other) == 0;
		}

		@Override
		public int compareTo(ImportRef other) {
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
				// -> sort by name of namespace
				return this.alias.compareTo(other.alias);
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

		// FIXME remove this method!
		public String toCode() {
			return toCode("", null, null);
		}

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
}
