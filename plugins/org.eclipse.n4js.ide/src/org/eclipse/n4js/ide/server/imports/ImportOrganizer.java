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
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.eclipse.lsp4j.TextEdit;
import org.eclipse.n4js.ide.server.codeActions.util.ChangeProvider;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.naming.N4JSQualifiedNameConverter;
import org.eclipse.n4js.organize.imports.ImportSpecifiersUtil;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.nodemodel.NodeModelUtilsN4;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.CancelIndicator;

/**
 * Functionality for organizing imports. See {@link #organizeImports(Document, Script, CancelIndicator)
 * #organizeImports()}.
 */
public class ImportOrganizer {

	/**
	 * Returns {@link TextEdit}s for organizing the imports of the given script, i.e.
	 * <ul>
	 * <li>removing unused imports,
	 * <li>sorting imports,
	 * <li>normalizing imports (e.g. only a single import specifier per import declaration).
	 * </ul>
	 */
	public static List<TextEdit> organizeImports(Document document, Script script, CancelIndicator cancelIndicator) {
		// we rely on flag 'ImportSpecifier#flaggedUsedInCode', so ensure that post-processing was done:
		((N4JSResource) script.eResource()).performPostProcessing(cancelIndicator);

		List<ImportDeclaration> importDecls = script.getScriptElements().stream()
				.filter(ImportDeclaration.class::isInstance)
				.map(ImportDeclaration.class::cast)
				.collect(Collectors.toList());

		SortedSet<ImportRef> importRefs = createImportRefs(importDecls);

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

	private static SortedSet<ImportRef> createImportRefs(List<ImportDeclaration> importDecls) {
		SortedSet<ImportRef> result = new TreeSet<>();
		int idx = 0;
		for (ImportDeclaration importDecl : importDecls) {
			if (importDecl.isBare()) {
				if (ImportSpecifiersUtil.isBrokenImport(importDecl)) {
					continue;
				}
				result.add(new ImportRef(importDecl, null, idx++));
			} else {
				for (ImportSpecifier importSpec : importDecl.getImportSpecifiers()) {
					if (!importSpec.isFlaggedUsedInCode()) {
						continue;
					}
					if (ImportSpecifiersUtil.isBrokenImport(importSpec)) {
						continue;
					}
					result.add(new ImportRef(importDecl, importSpec, idx++));
				}
			}
		}
		return result;
	}

	private static class ImportRef implements Comparable<ImportRef> {

		final ImportDeclaration importDecl;
		final ImportSpecifier importSpec;
		final int originalIndex;

		ImportRef(ImportDeclaration importDecl, ImportSpecifier importSpec, int originalIndex) {
			this.importDecl = importDecl;
			this.importSpec = importSpec;
			this.originalIndex = originalIndex;

			if (importDecl.isBare() != (importSpec == null)) {
				throw new IllegalArgumentException(
						"importSpec must be null if and only if importDecl is a bare import");
			}
			if (importSpec != null && !importSpec.isFlaggedUsedInCode()) {
				throw new IllegalArgumentException("must not create an ImportRef for unused imports");
			}
			if ((importSpec == null && ImportSpecifiersUtil.isBrokenImport(importDecl))
					|| (importSpec != null && ImportSpecifiersUtil.isBrokenImport(importSpec))) {
				throw new IllegalArgumentException("must not create an ImportRef for broken imports");
			}
		}

		public boolean isBare() {
			return importDecl.isBare();
		}

		@Override
		public int hashCode() {
			if (importSpec == null) {
				return importDecl.getModule().hashCode();
			} else if (importSpec instanceof NamedImportSpecifier) {
				return Objects.hash(
						Boolean.FALSE,
						importDecl.getModule(),
						((NamedImportSpecifier) importSpec).isDefaultImport(),
						((NamedImportSpecifier) importSpec).getImportedElement(),
						((NamedImportSpecifier) importSpec).getAlias());
			} else if (importSpec instanceof NamespaceImportSpecifier) {
				return Objects.hash(
						Boolean.TRUE,
						importDecl.getModule(),
						((NamespaceImportSpecifier) importSpec).getAlias());
			} else {
				throw new IllegalStateException(
						"unknown subclass of ImportSpecifier: " + importSpec.getClass().getSimpleName());
			}
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
				if (this.importDecl.getModule() == other.importDecl.getModule()) {
					return 0;
				}
				return Integer.compare(this.originalIndex, other.originalIndex);
			}
			// NOW: both are non-bare imports ...
			// 3) sort by module's fully qualified name
			int cmpModule = getFQN(this.importDecl.getModule()).compareTo(
					getFQN(other.importDecl.getModule()));
			if (cmpModule != 0) {
				return cmpModule;
			}
			// 4) move namespace imports to the front
			boolean isThisNamespace = this.importSpec instanceof NamespaceImportSpecifier;
			boolean isOtherNamespace = other.importSpec instanceof NamespaceImportSpecifier;
			int cmpNamespace = Boolean.compare(isThisNamespace, isOtherNamespace) * -1;
			if (cmpNamespace != 0) {
				return cmpNamespace;
			}
			if (isThisNamespace && isOtherNamespace) {
				// Two namespace imports for the same target module is an error case. Still, we want to handle this case
				// gracefully.
				// -> sort by name of namespace
				return ((NamespaceImportSpecifier) this.importSpec).getAlias().compareTo(
						((NamespaceImportSpecifier) other.importSpec).getAlias());
			}
			// NOW: both are named imports ...
			// 5) move default imports to the top
			boolean isThisDefault = ((NamedImportSpecifier) this.importSpec).isDefaultImport();
			boolean isOtherDefault = ((NamedImportSpecifier) other.importSpec).isDefaultImport();
			int cmpDefault = Boolean.compare(isThisDefault, isOtherDefault) * -1;
			if (cmpDefault != 0) {
				return cmpDefault;
			}
			// 6) sort by name of imported element
			int cmpName = ((NamedImportSpecifier) this.importSpec).getImportedElementAsText().compareTo(
					((NamedImportSpecifier) other.importSpec).getImportedElementAsText());
			if (cmpName != 0) {
				return cmpName;
			}
			// 7) sort by alias
			String thisAlias = ((NamedImportSpecifier) this.importSpec).getAlias();
			String otherAlias = ((NamedImportSpecifier) other.importSpec).getAlias();
			int cmpAlias = thisAlias != null && otherAlias != null
					? thisAlias.compareTo(otherAlias)
					: Boolean.compare(thisAlias != null, otherAlias != null);
			return cmpAlias;
		}

		private String getFQN(TModule module) {
			return module.getProjectName() + N4JSQualifiedNameConverter.DELIMITER + module.getQualifiedName();
		}

		public String toCode() {
			StringBuilder sb = new StringBuilder(128);
			sb.append("import ");
			if (importSpec instanceof NamedImportSpecifier) {
				NamedImportSpecifier importSpecCasted = (NamedImportSpecifier) importSpec;
				if (importSpecCasted.isDefaultImport()) {
					sb.append(importSpecCasted.getAlias());
				} else {
					sb.append('{');
					sb.append(importSpecCasted.getImportedElementAsText());
					String alias = importSpecCasted.getAlias();
					if (alias != null) {
						sb.append(" as ");
						sb.append(alias);
					}
					sb.append('}');
				}
				sb.append(' ');
			} else if (importSpec instanceof NamespaceImportSpecifier) {
				sb.append("* as ");
				sb.append(((NamespaceImportSpecifier) importSpec).getAlias());
				sb.append(' ');
			}
			if (!isBare()) {
				sb.append("from ");
			}
			sb.append('"');
			sb.append(importDecl.getModuleSpecifierAsText());
			sb.append('"');
			sb.append(';');
			return sb.toString();
		}
	}
}
