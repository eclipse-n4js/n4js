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
package org.eclipse.n4js.ui.proposals.imports;

import static org.eclipse.n4js.parser.InternalSemicolonInjectingParser.SEMICOLON_INSERTED;
import static org.eclipse.n4js.utils.UtilN4.isIgnoredSyntaxErrorNode;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension4;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.resource.AccessibleSerializer;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ui.organize.imports.ImportsRegionHelper;
import org.eclipse.n4js.ui.utils.ImportSpacerUserPreferenceHelper;
import org.eclipse.n4js.utils.Lazy;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.LeafNodeWithSyntaxError;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.MembersInjector;

/**
 * Obtain an import rewriter for a resource and add used types optionally along with aliases. It uses the serializer to
 * rewrite the import declarations.
 *
 * Instances of the {@link ImportRewriter} are obtained from the {@link ImportRewriter.Factory}.
 *
 * TODO: review if that is stable enough, e.g. with broken import declarations.
 */
public class ImportRewriter {

	/**
	 * The factory provides a readily configured {@link ImportRewriter}. It is parameterized with a document and a
	 * resource. It is assumed that the resource belongs to the given document and was properly guarded by a
	 * {@link IUnitOfWork}.
	 */
	public static class Factory {

		@Inject
		private MembersInjector<ImportRewriter> injector;

		/**
		 * Creates a new import rewriter.
		 */
		public ImportRewriter create(IDocument document, Resource resource) {
			ImportRewriter result = new ImportRewriter(document, resource);
			injector.injectMembers(result);
			return result;
		}
	}

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	@Inject
	private IValueConverterService valueConverters;

	@Inject
	private N4JSGrammarAccess grammarAccess;

	@Inject
	private AccessibleSerializer serializer;

	@Inject
	private ImportSpacerUserPreferenceHelper spacerPreference;

	@Inject
	private ImportsRegionHelper importsRegionHelper;

	private final String lineDelimiter;
	private final Script script;
	private final Set<NameAndAlias> requestedImports;
	private final List<ImportDeclaration> existingImports;
	private final Lazy<String> lazySpacer;

	private ImportRewriter(IDocument document, Resource resource) {
		if (document instanceof IDocumentExtension4) {
			lineDelimiter = ((IDocumentExtension4) document).getDefaultLineDelimiter();
		} else {
			lineDelimiter = Strings.newLine();
		}
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
	 * @param qualifiedName
	 *            the name of the thing to import
	 */
	public void addImport(QualifiedName qualifiedName) {
		requestedImports.add(new NameAndAlias(qualifiedName, null));
	}

	/**
	 * @param qualifiedName
	 *            the fqn of the thing to import
	 * @param alias
	 *            the alias to use, may be null.
	 */
	public void addImport(QualifiedName qualifiedName, String alias) {
		requestedImports.add(new NameAndAlias(qualifiedName, alias));
	}

	/**
	 * @param qualifiedName
	 *            the name of the thing to import
	 */
	public void addSingleImport(QualifiedName qualifiedName, MultiTextEdit result) {
		toTextEdit(qualifiedName, null, findInsertionOffset(), result);
	}

	/**
	 * @param qualifiedName
	 *            the FQN of the thing to import
	 * @param alias
	 *            the alias to use, may be null.
	 * @return the location of the alias in the final field
	 */
	public AliasLocation addSingleImport(QualifiedName qualifiedName, String alias, MultiTextEdit result) {
		return toTextEdit(qualifiedName, alias, findInsertionOffset(), result);
	}

	/**
	 * @param result
	 *            the accumulator for the changes
	 */
	public void toTextEdits(MultiTextEdit result) {
		int insertionOffset = findInsertionOffset();
		for (NameAndAlias requested : requestedImports) {
			toTextEdit(requested.getName(), requested.getAlias(), insertionOffset, result);
		}
	}

	/**
	 * Add the necessary text edits to the accumulating result. Optionally return the offset of the alias.
	 */
	private AliasLocation toTextEdit(QualifiedName qualifiedName, String optionalAlias, int insertionOffset,
			MultiTextEdit result) {
		QualifiedName moduleName = qualifiedName.skipLast(1);
		// the following code for enhancing existing ImportDeclarations makes use of the Xtext serializer, which is not
		// yet compatible with fragments in Xtext grammars (as of Xtext 2.9.1) -> deactivated for now
// @formatter:off
//		String moduleNameAsString = unquoted(syntacticModuleName(moduleName));
//		for (ImportDeclaration existing : existingImports) {
//			String importedModuleName = existing.getModule().getQualifiedName();
//			if (moduleNameAsString.equals(importedModuleName)) {
//				return enhanceExistingImportDeclaration(existing, qualifiedName, optionalAlias, result);
//			}
//		}
// @formatter:on
		return addNewImportDeclaration(moduleName, qualifiedName, optionalAlias, insertionOffset, result);
	}

	/**
	 */
	@SuppressWarnings("unused")
	private String unquoted(String syntacticModuleName) {
		if (syntacticModuleName == null || syntacticModuleName.length() < 2)
			return syntacticModuleName;
		if (syntacticModuleName.charAt(0) == '"' && syntacticModuleName.charAt(syntacticModuleName.length() - 1) == '"')
			return syntacticModuleName.substring(1, syntacticModuleName.length() - 1);

		return syntacticModuleName;
	}

	private AliasLocation addNewImportDeclaration(QualifiedName moduleName, QualifiedName qualifiedName,
			String optionalAlias,
			int insertionOffset, MultiTextEdit result) {

		final String spacer = lazySpacer.get();
		String syntacticModuleName = syntacticModuleName(moduleName);

		AliasLocation aliasLocation = null;
		String importSpec = (insertionOffset != 0 ? lineDelimiter : "") + "import ";

		if (!N4JSLanguageUtils.isDefaultExport(qualifiedName)) { // not an 'default' export
			importSpec = importSpec + "{" + spacer + qualifiedName.getLastSegment();
			if (optionalAlias != null) {
				importSpec = importSpec + " as ";
				aliasLocation = new AliasLocation(insertionOffset, importSpec.length(), optionalAlias);
				importSpec = importSpec + optionalAlias;
			}
			importSpec = importSpec + spacer + "}";
		} else { // import default exported element
			if (optionalAlias == null) {
				importSpec = importSpec + N4JSLanguageUtils.lastSegmentOrDefaultHost(qualifiedName);
			} else {
				aliasLocation = new AliasLocation(insertionOffset, importSpec.length(), optionalAlias);
				importSpec = importSpec + optionalAlias;
			}
		}

		result.addChild(new InsertEdit(insertionOffset, importSpec + " from "
				+ syntacticModuleName + ";"
				+ (insertionOffset != 0 ? "" : lineDelimiter)));

		return aliasLocation;
	}

	/** compute the syntactic string representation of the moduleName */
	private String syntacticModuleName(QualifiedName moduleName) {
		String syntacticModuleName = valueConverters.toString(
				qualifiedNameConverter.toString(moduleName),
				grammarAccess.getModuleSpecifierRule().getName());
		return syntacticModuleName;
	}

	@SuppressWarnings({ "unused", "deprecation" })
	private AliasLocation enhanceExistingImportDeclaration(ImportDeclaration importDeclaration,
			QualifiedName qualifiedName,
			String optionalAlias, MultiTextEdit result) {

		addImportSpecifier(importDeclaration, qualifiedName, optionalAlias);
		ICompositeNode replaceMe = NodeModelUtils.getNode(importDeclaration);
		int offset = replaceMe.getOffset();
		AliasLocationAwareBuffer observableBuffer = new AliasLocationAwareBuffer(
				optionalAlias,
				offset,
				grammarAccess);

		try {
			serializer.serialize(
					importDeclaration,
					observableBuffer,
					SaveOptions.newBuilder().noValidation().getOptions());
		} catch (IOException e) {
			throw new RuntimeException("Should never happen since we write into memory", e);
		}
		result.addChild(new ReplaceEdit(offset, replaceMe.getLength(), observableBuffer.toString()));
		return observableBuffer.getAliasLocation();
	}

	private void addImportSpecifier(ImportDeclaration importDeclaration, QualifiedName qualifiedName,
			String optionalAlias) {
		boolean isDefaultExport = N4JSLanguageUtils.isDefaultExport(qualifiedName);

		NamedImportSpecifier specifier = null;
		specifier = isDefaultExport ? N4JSFactory.eINSTANCE.createDefaultImportSpecifier()
				: N4JSFactory.eINSTANCE.createNamedImportSpecifier();

		// not only types, but also variables ...
		Iterable<TExportableElement> topLevelTypes = Iterables.concat(
				importDeclaration.getModule().getTopLevelTypes(),
				importDeclaration.getModule().getVariables());
		for (TExportableElement t : topLevelTypes) {
			if (t.getExportedName().equals(qualifiedName.getLastSegment())) {
				specifier.setImportedElement(t);
				specifier.setAlias(optionalAlias);
				if (optionalAlias == null && isDefaultExport) {
					specifier.setAlias(qualifiedName.getSegment(qualifiedName.getSegmentCount() - 2)); // set to
																										// module-name
				}
				break;
			}
		}

		if (isDefaultExport)
			importDeclaration.getImportSpecifiers().add(0, specifier); // to Front
		else
			importDeclaration.getImportSpecifiers().add(specifier);
	}

	private int findInsertionOffset() {
		int result = 0;
		List<ScriptElement> scriptElements = script.getScriptElements();
		for (int i = 0, size = scriptElements.size(); i < size; i++) {
			ScriptElement element = scriptElements.get(i);
			if (element instanceof ImportDeclaration) {
				// Instead of getting the total offset for the first non-import-declaration, we try to get the
				// total end offset for the most recent import declaration which is followed by any other script element
				// this is required for the linebreak handling for automatic semicolon insertion.
				final ICompositeNode importNode = NodeModelUtils.findActualNodeFor(element);
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
	 * Returns with the length of the node including all hidden leaf nodes but the {@link LeafNodeWithSyntaxError} one,
	 * that was created for the automatic semicolon insertion.
	 */
	private int getLengthWithoutAutomaticSemicolon(final INode node) {
		if (node instanceof ILeafNode) {
			return node.getLength();
		}

		int length = 0;
		for (final INode leafNode : ((ICompositeNode) node).getLeafNodes()) {
			if (!isIgnoredSyntaxErrorNode(leafNode, SEMICOLON_INSERTED)) {
				length += leafNode.getLength();
			}
		}

		return length;
	}

}
