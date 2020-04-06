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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.eclipse.lsp4j.TextEdit;
import org.eclipse.n4js.formatting2.FormattingUserPreferenceHelper;
import org.eclipse.n4js.ide.server.codeActions.util.ChangeProvider;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.organize.imports.ImportSpecifiersUtil;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.utils.nodemodel.NodeModelUtilsN4;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.ide.server.Document;
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
	private ImportHelper importHelper;

	@Inject
	private ImportRegionHelper importRegionHelper;

	@Inject
	private IValueConverterService valueConverterService;

	@Inject
	private N4JSGrammarAccess grammarAccess;

	@Inject
	private FormattingUserPreferenceHelper formattingUserPreferenceHelper;

	/**
	 * Returns {@link TextEdit}s for organizing the imports of the given script, i.e.
	 * <ul>
	 * <li>removing unused imports,
	 * <li>sorting imports,
	 * <li>normalizing imports (e.g. only a single import specifier per import declaration).
	 * </ul>
	 */
	public List<TextEdit> organizeImports(Document document, Script script,
			Collection<ImportDescriptor> importsToBeAdded, CancelIndicator cancelIndicator) {
		// we rely on flag 'ImportSpecifier#flaggedUsedInCode', so ensure that post-processing was done:
		N4JSResource resource = (N4JSResource) script.eResource();
		resource.performPostProcessing(cancelIndicator);

		List<ImportDeclaration> importDecls = script.getScriptElements().stream()
				.filter(ImportDeclaration.class::isInstance)
				.map(ImportDeclaration.class::cast)
				.collect(Collectors.toList());

		SortedSet<ImportDescriptor> importDescs = createImportDescriptors(importDecls);
		importDescs.addAll(importsToBeAdded);

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
		String spacing = formattingUserPreferenceHelper.getSpacingPreference(resource);
		String[] importStrings = importDescs.stream()
				.map(importDesc -> importDesc.toCode(spacing, valueConverterService, grammarAccess))
				.toArray(String[]::new);
		if (importStrings.length > 0) {
			TextEdit edit = ChangeProvider.insertLinesAbove(document, offsetOfFirstImport, false, importStrings);
			textEdits.add(edit);
		}

		return textEdits;
	}

	private SortedSet<ImportDescriptor> createImportDescriptors(List<ImportDeclaration> importDecls) {
		SortedSet<ImportDescriptor> result = new TreeSet<>();
		int idx = 0;
		for (ImportDeclaration importDecl : importDecls) {
			if (importDecl.isBare()) {
				if (ImportSpecifiersUtil.isBrokenImport(importDecl)) {
					continue;
				}
				ImportDescriptor importDesc = importHelper.createImportDescriptorFromAST(importDecl, null, idx++);
				result.add(importDesc);
			} else {
				for (ImportSpecifier importSpec : importDecl.getImportSpecifiers()) {
					if (!importSpec.isFlaggedUsedInCode()) {
						continue;
					}
					if (ImportSpecifiersUtil.isBrokenImport(importSpec)) {
						continue;
					}
					ImportDescriptor importDesc = importHelper.createImportDescriptorFromAST(importDecl, importSpec,
							idx++);
					result.add(importDesc);
				}
			}
		}
		return result;
	}
}
