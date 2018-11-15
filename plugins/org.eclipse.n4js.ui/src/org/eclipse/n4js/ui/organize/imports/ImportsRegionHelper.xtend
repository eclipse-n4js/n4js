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
import java.util.List
import org.eclipse.n4js.documentation.N4JSDocumentationProvider
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.parser.InternalSemicolonInjectingParser
import org.eclipse.n4js.ts.services.TypeExpressionsGrammarAccess
import org.eclipse.n4js.utils.UtilN4
import org.eclipse.xtext.TerminalRule
import org.eclipse.xtext.nodemodel.ILeafNode
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.nodemodel.impl.HiddenLeafNode
import org.eclipse.xtext.nodemodel.impl.LeafNode
import org.eclipse.xtext.resource.XtextResource

import static org.eclipse.n4js.ui.organize.imports.XtextResourceUtils.*

import static extension org.eclipse.n4js.n4JS.N4JSASTUtils.*
import static extension org.eclipse.xtext.nodemodel.util.NodeModelUtils.*

/**
 * Helper used to calculate imports region in the resource.
 */
class ImportsRegionHelper {
	@Inject
	private N4JSDocumentationProvider documentationProvider;

	@Inject
	private TypeExpressionsGrammarAccess typeExpressionGrammmarAccess;

	/** 
	 * Calculates import offset by analyzing the provided resource.
	 * @See {@link #getImportRegion(Script)}
	 */
	public def int getImportOffset(XtextResource resource) {
		getImportRegion(resource).offset;
	}
	
	/** 
	 * Calculates import offset by analyzing the provided script.
	 * @See {@link #getImportRegion(Script)}
	 */
	public def int getImportOffset(Script script) {
		getImportRegion(script).offset;
	}
	
	/** 
	 * Calculates import region by analyzing the provided resource.
	 * @See {@link #getImportRegion(Script)}
	 */
	package def InsertionPoint getImportRegion(XtextResource xtextResource) {
		return getImportRegion(getScript(xtextResource))
	}

	/**
	 * Calculate destination region in Document for imports. If the offset is not 0,
	 * then it has to be advanced by current line feed length
	 *
	 * Note reduced visibility - it is internal method used only by helpers related to organizing imports.
	 *
	 * Using first position after script-annotation ("@@") and after any directive in the prolog section, that is
	 * just before the first statement or, in cases where the first statement is js-style documented, before the jsdoc-style
	 * documentation.
	 *
	 * Examples:
	 * <pre>
	 *  // (A)
	 *  // (B)
	 *  &#64;&#64;StaticPolyfillAware
	 *  // (C)
	 *  "use strict";
	 *  // (D)
	 *  /&#42; non-jsdoc comment (E) &#42;/
	 *  /&#42;&#42; jsdoc comment (F) &#42;/
	 *  /&#42; non-jsdoc comment (G) &#42;/
	 *  // (H)
	 *  export public class A { // (I)
	 *     method(): B {
	 *        return new B(); // requires import
	 *    }
	 *  }
	 * </pre>
	 * Will put the insertion-point in front of line (F), since this is the active jsdoc for class A.
	 * {@link InsertionPoint#isBeforeJsdocDocumentation} will be set to true. Lowest possible insertion
	 * is the begin of line (D), stored in {@link InsertionPoint#notBeforeTotalOffset}. If the directive <code>"use strict";</code>
	 * between lines (C) and (D) is omitted, then the lowest insertion point would be in front of line (C). In any case the insertion of
	 * the import must be in front of the <code>export</code> keyword line (I).
	 *
	 * <p>Region has length 0.
	 *
	 * @param xtextResource
	 *            n4js resource
	 * @return region for import statements, length 0
	 */
	package def InsertionPoint getImportRegion(Script script) {
		// In N4js imports can appear anywhere in the Script as top-level elements. So even as a last
		// statement and more importantly scattered around.
		val InsertionPoint insertionPoint = new InsertionPoint;

		// First Position
		var int begin = -1;

		if (script !== null) {
			// if there is a script, we can insert in first position.
			begin = 0;
			val List<INode> scriptAnnos = findNodesForFeature(script, N4JSPackage.Literals.SCRIPT__ANNOTATIONS);
			if (!scriptAnnos.isEmpty()) {
				val INode lastAnno = scriptAnnos.get(scriptAnnos.size() - 1);
				begin = lastAnno.getTotalEndOffset();
				insertionPoint.notBeforeTotalOffset = begin;
			// advance over line feed, since newlines are in the hidden token
			// channel of the following element
			// ChangeManager has information of current line feed length.
			}

			// Searching for directives (string-statements at the beginning, e.g. "use strict")
			// and the first real statement, ignoring imports:
			val elements = script.scriptElements;
			var lastSeenDirective = -1;
			var idxNondirectiveStatemnt = -1;
			for (var i = 0; i < elements.size && idxNondirectiveStatemnt === -1; i++) {
				val curr = elements.get(i)
				if (curr.isStringLiteralExpression) {
					lastSeenDirective = i;
				} else if (curr instanceof ImportDeclaration) {
					// ignore import declarations, will be removed anyway.
				} else {
					idxNondirectiveStatemnt = i;
				}
			}

			// Conditionally calculate begin-position
			if (idxNondirectiveStatemnt !== -1) {
				// Standard case, a normal statement encountered.
				// get documentation of that statement:
				val realScriptElement = elements.get(idxNondirectiveStatemnt);
				val realScriptElementNode = findActualNodeFor(realScriptElement);

				// find doc, looks for nearest.
				val docuNodes = documentationProvider.getDocumentationNodes(realScriptElement);

				if (!docuNodes.isEmpty) {
					// documentation found
					val docuNode = docuNodes.get(0);
					var INode previousNode = docuNode;
					var INode lastEOL = null;
					var continue = true;
					while (continue && previousNode.hasPreviousSibling &&
						previousNode.previousSibling instanceof HiddenLeafNode) {
						val grammar = previousNode.previousSibling.grammarElement
						if (grammar == typeExpressionGrammmarAccess.WSRule) {
							previousNode = previousNode.previousSibling;
						} else if (grammar == typeExpressionGrammmarAccess.EOLRule) {
							previousNode = previousNode.previousSibling;
							lastEOL = previousNode;
						} else {
							continue = false;
						}
					}

					begin = if (lastEOL !== null) lastEOL.endOffset else docuNode.totalOffset;
					insertionPoint.isBeforeJsdocDocumentation = true;

				} else {
					// no documentation, go before the statement:
					var listLeafNodes = realScriptElementNode.leafNodes.toList;

					// looking for a position right after the last comment and before (WS+EOL)* NOT_HIDDEN_LEAF
					{
						val iterLeaves = listLeafNodes.iterator;
						var ILeafNode curr;
						var ILeafNode firstEOL;
						var ILeafNode afterFirstEOL;
						var boolean sawComment = false;
						var ILeafNode lastComment;
						// got to first non-hidden:
						while (iterLeaves.hasNext && ((curr = iterLeaves.next).isHidden)) {
							if (curr.grammarElement instanceof TerminalRule) {
								if (curr.grammarElement == typeExpressionGrammmarAccess.ML_COMMENTRule) {
									// reset EOLs
									firstEOL = null;
									afterFirstEOL = null;
									sawComment = true;
									lastComment = curr;
								} else if (curr.grammarElement == typeExpressionGrammmarAccess.SL_COMMENTRule) {
									// reset EOLs
									firstEOL = null;
									afterFirstEOL = null;
									sawComment = true;
									lastComment = curr;
								} else if (curr.grammarElement == typeExpressionGrammmarAccess.EOLRule) {
									if (firstEOL === null) {
										// store
										firstEOL = curr;
									} else if (afterFirstEOL === null) {
										// store insertion point.
										afterFirstEOL = curr;
									}
								} else if (curr.grammarElement == typeExpressionGrammmarAccess.WSRule) {
									// keep going
									if (firstEOL !== null && afterFirstEOL === null) afterFirstEOL = curr;
								} else {
									firstEOL = null;
									afterFirstEOL = null;
								}
							}
						}
						if (curr === null || curr.isHidden) {
							// Something is wrong here.
							throw new RuntimeException("Expected at least one non-hidden element.");
						}

						insertionPoint.notAfterTotalOffset = curr.totalOffset;

						var begin2 = if (afterFirstEOL !== null && sawComment) {
								afterFirstEOL.totalOffset;
							} else {
								if (hasNoCommentUpTo(curr)) {
									0; // all the imports before will be removed, so put at the beginning.
								} else {
									// make the comments above the import.
									if (sawComment) {
										lastComment.endOffset;
									} else {
										// do not use 'curr.totalOffset;' since it would insert the import directly before the statement.
										// we'd rather want to keep is above the whitespace, so use the first in the list:
										listLeafNodes.head.totalOffset;
									}
								}
							};
						begin = Math.max(begin, begin2);
					}

					if (lastSeenDirective > -1) {
						// have directive, so insert not before last directive.
						val lastDirectiveNode = elements.get(lastSeenDirective).findActualNodeFor;
						val lastDirectiveEndOffset = lastDirectiveNode.totalEndOffset;
						// update not before,
						insertionPoint.notBeforeTotalOffset = Math.max(lastDirectiveEndOffset,
							insertionPoint.notBeforeTotalOffset);
						// update begin
						begin = Math.max(begin, lastDirectiveEndOffset);
					}

				}

			} else {
				// no normal statement encountered.
				if (lastSeenDirective > -1) {
					// have directive, so insert after last directive.
					val lastDirectiveNode = elements.get(lastSeenDirective).findActualNodeFor;

					// update begin:
					begin = lastDirectiveNode.totalEndOffset
					insertionPoint.notBeforeTotalOffset = Math.max(begin, insertionPoint.notBeforeTotalOffset);

				} else {
					// no directive
					// --> just leave begin as is, after the "@@"
				}
			}

			// create new insertion point.
			insertionPoint.offset = begin;
		}

		return insertionPoint;
	}


		/** Goes from the beginning of the RootNode up to the passed in node. Looks only at hidden leafs and at ASI-LeafNodes.
	 * @return {@code false} if any comment is encountered on the way.
	 */
	private def boolean hasNoCommentUpTo(ILeafNode node) {
		if (node === null) return true;
		val iter = node.rootNode.asTreeIterable.iterator
		while (iter.hasNext) {
			val curr = iter.next;
			// exit case:
			if (curr == node) return true;

			if (curr instanceof LeafNode) {
				if (curr.isHidden ||
					UtilN4.isIgnoredSyntaxErrorNode(curr, InternalSemicolonInjectingParser.SEMICOLON_INSERTED)) {
					// hidden OR ASI
					if (! curr.text.trim.isEmpty) {
						// token-text contains not only whitespace --> there must be a comment.
						return false;
					}
				}
			}
		}
		// should never be reached.
		throw new IllegalStateException("Iteration over-stepped the passed in node.");
	}
}
