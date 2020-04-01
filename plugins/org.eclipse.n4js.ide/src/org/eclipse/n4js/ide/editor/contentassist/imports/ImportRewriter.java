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
package org.eclipse.n4js.ide.editor.contentassist.imports;

import static org.eclipse.n4js.parser.InternalSemicolonInjectingParser.SEMICOLON_INSERTED;
import static org.eclipse.n4js.utils.UtilN4.isIgnoredSyntaxErrorNode;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.ide.server.imports.ImportOrganizer.ImportRef;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.utils.Lazy;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.LeafNodeWithSyntaxError;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.util.TextRegion;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * Obtain an import rewriter for a resource and add used types optionally along with aliases. It uses the serializer to
 * rewrite the import declarations.
 *
 * Instances of the {@link ImportChanges} are obtained from the {@link #create(String, Resource)}.
 *
 * TODO: review if that is stable enough, e.g. with broken import declarations.
 */
public class ImportRewriter {

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	@Inject
	private IValueConverterService valueConverters;

	@Inject
	private N4JSGrammarAccess grammarAccess;

	@Inject
	private ImportsRegionHelper importsRegionHelper;

	@Inject
	private ImportSpacerUserPreferenceHelper spacerPreference;

	/**
	 * Creates a new import rewriter.
	 */
	public ImportChanges create(String lineDelimiter, Resource resource) {
		ImportChanges result = new ImportChanges(lineDelimiter, resource);
		return result;
	}

	/** Constructor */
	public class ImportChanges {
		private final String lineDelimiter;
		private final Script script;
		private final Set<NameAndAlias> requestedImports;
		private final List<ImportDeclaration> existingImports;
		private final Lazy<String> lazySpacer;

		private ImportChanges(String lineDelimiter, Resource resource) {
			this.lineDelimiter = lineDelimiter;
			this.script = (Script) resource.getContents().get(0);
			this.existingImports = Lists.newArrayList();
			for (ScriptElement element : script.getScriptElements()) {
				if (element instanceof ImportDeclaration)
					existingImports.add((ImportDeclaration) element);
			}
			this.requestedImports = Sets.newLinkedHashSet();
			this.lazySpacer = new Lazy<>(() -> spacerPreference.getSpacingPreference(resource));

		}

		/**
		 * @param nameAndAlias
		 *            instance with name and optional alias
		 */
		public void addImport(NameAndAlias nameAndAlias) {
			requestedImports.add(nameAndAlias);
		}

		/**
		 * @param regions
		 *            the accumulator for the changes
		 */
		public void addReplaceRegions(Collection<ReplaceRegion> regions, Collection<ImportRef> importRefs) {
			int insertionOffset = findInsertionOffset();
			for (NameAndAlias requested : requestedImports) {
				addNewImportDeclaration(requested.getName(), requested.getAlias(), requested.getProjectName(),
						insertionOffset, regions, importRefs);
			}
		}

		private void addNewImportDeclaration(QualifiedName qualifiedName,
				String optionalAlias, String projectName, int insertionOffset,
				Collection<ReplaceRegion> replaceRegions, Collection<ImportRef> importRefs) {

			QualifiedName moduleName = qualifiedName.skipLast(1);

			String spacer = lazySpacer.get();
			String syntacticModuleName = syntacticModuleName(moduleName);

			String importSpec = (insertionOffset != 0 ? lineDelimiter : "") + "import ";

			if (!N4JSLanguageUtils.isDefaultExport(qualifiedName)) { // not an 'default' export
				importSpec = importSpec + "{" + spacer + qualifiedName.getLastSegment();
				if (optionalAlias != null) {
					importSpec = importSpec + " as ";
					importSpec = importSpec + optionalAlias;
				}
				importSpec = importSpec + spacer + "}";
			} else { // import default exported element
				if (optionalAlias == null) {
					importSpec = importSpec + N4JSLanguageUtils.lastSegmentOrDefaultHost(qualifiedName);
				} else {
					importSpec = importSpec + optionalAlias;
				}
			}

			String insertedCode = importSpec + " from " + syntacticModuleName + ";"
					+ (insertionOffset != 0 ? "" : lineDelimiter);
			ITextRegion region = new TextRegion(insertionOffset, 0);
			replaceRegions.add(new XReplaceRegion(region, insertedCode));

			ImportRef importRef = new ImportRef(qualifiedName.getLastSegment(), optionalAlias, projectName, moduleName);
			importRefs.add(importRef);
		}

		/** compute the syntactic string representation of the moduleName */
		private String syntacticModuleName(QualifiedName moduleName) {
			String syntacticModuleName = valueConverters.toString(
					qualifiedNameConverter.toString(moduleName),
					grammarAccess.getModuleSpecifierRule().getName());
			return syntacticModuleName;
		}

		private int findInsertionOffset() {
			int result = 0;
			List<ScriptElement> scriptElements = script.getScriptElements();
			for (int i = 0, size = scriptElements.size(); i < size; i++) {
				ScriptElement element = scriptElements.get(i);
				if (element instanceof ImportDeclaration) {
					// Instead of getting the total offset for the first non-import-declaration, we try to get the
					// total end offset for the most recent import declaration which is followed by any other script
					// element
					// this is required for the linebreak handling for automatic semicolon insertion.
					ICompositeNode importNode = NodeModelUtils.findActualNodeFor(element);
					if (null != importNode) {
						result = importNode.getTotalOffset() + getLengthWithoutAutomaticSemicolon(importNode);
					}
				} else {
					// We assume that all import declarations are to be found in one place, thus
					// at this point we must have seen all of them.
					break;
				}
			}
			// If previously, an existing import declaration could be found, use it as offset.
			if (result != 0) {
				return result;
			}
			// Otherwise, we assume there is no import declarations yet. Use {@link ImportsRegionHelper}
			// to obtain an offset for the insertion of a new import declaration.
			return importsRegionHelper.getImportOffset(script);
		}

		/**
		 * Returns with the length of the node including all hidden leaf nodes but the {@link LeafNodeWithSyntaxError}
		 * one, that was created for the automatic semicolon insertion.
		 */
		private int getLengthWithoutAutomaticSemicolon(final INode node) {
			if (node instanceof ILeafNode) {
				return node.getLength();
			}

			int length = 0;
			for (INode leafNode : ((ICompositeNode) node).getLeafNodes()) {
				if (!isIgnoredSyntaxErrorNode(leafNode, SEMICOLON_INSERTED)) {
					length += leafNode.getLength();
				}
			}

			return length;
		}

	}
}
