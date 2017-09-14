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
package org.eclipse.n4js.ui.organize.imports

import com.google.inject.Inject
import java.util.ArrayList
import org.eclipse.emf.common.notify.Adapter
import org.eclipse.n4js.n4JS.DefaultImportSpecifier
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.ui.utils.ImportSpacerUserPreferenceHelper
import org.eclipse.n4js.utils.UtilN4
import org.eclipse.xtext.nodemodel.ICompositeNode
import org.eclipse.xtext.nodemodel.ILeafNode
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage
import org.eclipse.xtext.nodemodel.impl.LeafNodeWithSyntaxError
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.resource.XtextResource

import static org.eclipse.n4js.parser.InternalSemicolonInjectingParser.SEMICOLON_INSERTED
import static org.eclipse.xtext.nodemodel.util.NodeModelUtils.*
import java.util.List
import org.eclipse.n4js.n4JS.ImportSpecifier

/**
 * Helper for creating representation of the {@link ImportDeclaration} as string with N4JS code.
 */
class ImportDeclarationTextHelper {

	@Inject
	private ImportSpacerUserPreferenceHelper spacerPreference;

	/** 
	 * Extracts the token text for existing import-declaration or creates new textual representation for
	 * a new generated import declaration.
	 */
	def String extractPureText(ImportDeclaration declaration, XtextResource resource, Adapter nodelessMarker) {
		// formatting decision: curly braces with whitespace
		val spacer = spacerPreference.getSpacingPreference(resource)

		if (declaration.eAdapters.contains(nodelessMarker)) {
			// wrap importSpecifiers in new ArrayList, to support sorting (GHOLD-48)
			val impSpec = new ArrayList(declaration.importSpecifiers)
			val module = declaration.module.moduleSpecifier

			if (impSpec.size === 1) {
				// create own string. from single Named Adapter:
				val onlyImpSpec = impSpec.get(0)
				if (onlyImpSpec instanceof NamespaceImportSpecifier)
					return onlyImpSpec.namespacePureText(module)

				val namedSpec = onlyImpSpec as NamedImportSpecifier
				if (namedSpec instanceof DefaultImportSpecifier)
					return namedSpec.defaultPureText(module)
				else
					return namedSpec.namedPureText(module, spacer)
			} else {
				return impSpec.multiplePureText(module, spacer)
			}
		} else
			return rewriteTokenText(findActualNodeFor(declaration), spacer, SEMICOLON_INSERTED);
	}

	private def namespacePureText(NamespaceImportSpecifier is, String module) {
		return '''import * as «is.alias» from "«module»";'''
	}

	private def defaultPureText(DefaultImportSpecifier is, String module) {
		return '''import «is.importedElement.name» from "«module»";'''
	}

	private def namedPureText(NamedImportSpecifier is, String module, String spacer) {
		return '''import {«spacer»«is.importedElement.name»«IF (is.alias !== null)» as «is.alias»«ENDIF»«spacer»} from "«module»";'''
	}

	private def multiplePureText(List<ImportSpecifier> impSpec, String module, String spacer) {
		// more then one, sort them:
		ImportsSorter.sortByName(impSpec)
		val defImp = impSpec.filter(DefaultImportSpecifier).head; // only one is possible
		val nameImp = impSpec.filter(NamespaceImportSpecifier).head; // only one is possible
		val rest = impSpec.filter [ specifier |
			specifier instanceof DefaultImportSpecifier === false &&
				specifier instanceof NamespaceImportSpecifier === false
		]
		val normalImports = !rest.isEmpty
		val defaultImport = if (defImp === null) "" else '''«defImp.importedElement.name»'''
		val spacerDefName = '''«IF defImp !== null && nameImp !== null», «ENDIF»'''
		val namespaceImport = if (nameImp === null) "" else '''* as «nameImp.alias»'''

		return '''import «defaultImport»«spacerDefName»«namespaceImport»«IF normalImports
							»{«spacer»«
								FOR a : impSpec SEPARATOR ', '»«
									(a as NamedImportSpecifier).importedElement.name»«
										IF ((a as NamedImportSpecifier).alias !== null)» as « (a as NamedImportSpecifier).alias »«
										ENDIF»«
								ENDFOR»«spacer
							»}«ENDIF» from "«module»";'''
	}

	/**
	 * Rewrites the node-content without comments.
	 * 
	 * Inspired by {@link NodeModelUtils#getTokenText(INode)} but can treat any {@link LeafNodeWithSyntaxError syntax error}
	 * nodes as a hidden one and reformats White-space after opening and before closing curly brace according to spacer-policy
	 * 
	 * @param node the node to reformat.
	 * @param spacer append after "{" and prepend before "}" can be empty string, then no white-space is inserted after
	 * @param ignoredSyntaxErrorIssues - nodes of type LeafNodeWithSyntaxError having one of this issues are treated as ignored/hidden leafs
	 * @returns modified textual form.
	 */
	private static def String rewriteTokenText(ICompositeNode node, String spacer, String... ignoredSyntaxErrorIssues) {
		val StringBuilder builder = new StringBuilder(Math.max(node.getTotalLength(), 1));

		var boolean hiddenSeen = false;
		var boolean openingCurlySeen = false;
		var boolean fixedASI = false;

		for (ILeafNode leaf : node.getLeafNodes()) {
			if (UtilN4.isIgnoredSyntaxErrorNode(leaf, SEMICOLON_INSERTED)) {
				fixedASI = true
			}
			if (!isHiddenOrIgnoredSyntaxError(leaf, ignoredSyntaxErrorIssues)) {
				val text = leaf.getText();
				if (builder.length() > 0) { // do not insert space before any content.
					if (openingCurlySeen) {
						// last real text was "{"
						builder.append(spacer);
					} else if ("}" == text) {
						// this text is "}"
						builder.append(spacer);
					} else if ("," == text) {
						// drop WS in front of commas.
					} else {
						// standard-WS handling.
						if (hiddenSeen) {
							builder.append(' ');
						}
					}
				}
				// record state of opening curly brace:
				openingCurlySeen = "{" == text;
				builder.append(text);
				hiddenSeen = false;
			} else {
				hiddenSeen = true;
			}
		}

		if (fixedASI) builder.append(";");

		return builder.toString();
	}

	/**
	 * Returns with {@code true} if the leaf node argument is either hidden, or represents a
	 * {@link LeafNodeWithSyntaxError syntax error} where the {@link SyntaxErrorMessage#getIssueCode() issue code} of
	 * the syntax error matches with any of the given ignored syntax error issue codes. Otherwise returns with
	 * {@code false}.
	 */
	private static def boolean isHiddenOrIgnoredSyntaxError(ILeafNode leaf, String... ignoredSyntaxErrorIssues) {
		return leaf.isHidden() || UtilN4.isIgnoredSyntaxErrorNode(leaf, ignoredSyntaxErrorIssues);
	}
}
