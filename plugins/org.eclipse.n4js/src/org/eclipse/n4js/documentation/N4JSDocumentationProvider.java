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
package org.eclipse.n4js.documentation;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.documentation.impl.MultiLineCommentDocumentationProvider;
import org.eclipse.xtext.nodemodel.BidiTreeIterator;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.LeafNode;
import org.eclipse.xtext.nodemodel.impl.LeafNodeWithSyntaxError;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration;
import org.eclipse.n4js.n4JS.ExportedVariableStatement;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.parser.InternalSemicolonInjectingParser;
import org.eclipse.n4js.ts.typeRefs.TypeRef;

/**
 */
public class N4JSDocumentationProvider extends MultiLineCommentDocumentationProvider {

	private static final Logger logger = Logger.getLogger(N4JSDocumentationProvider.class);

	/**
	 * Returns documentation nodes for N4JS AST and type elements (in which case the method returns the nodes of the
	 * corresponding AST element). If the AST element has no documentation nodes itself, but is an exportable element,
	 * then the documentation of the export statement is returned if present.
	 */
	@Override
	public List<INode> getDocumentationNodes(EObject object) {
		final EObject astNode = N4JSASTUtils.getCorrespondingASTNode(object);
		if (astNode != null) {
			List<INode> nodes = super.getDocumentationNodes(astNode);
			if (nodes.isEmpty() && astNode instanceof VariableDeclaration) {
				TypeRef typeRef = ((VariableDeclaration) astNode).getDeclaredTypeRef();
				if (typeRef != null) {
					nodes = getDocumentationNodes(typeRef);
				}
			}
			if (nodes.isEmpty()) {
				if (astNode instanceof ExportedVariableDeclaration
						&& astNode.eContainer() instanceof ExportedVariableStatement) {
					EList<VariableDeclaration> decls = ((ExportedVariableStatement) astNode.eContainer()).getVarDecl();
					if (decls.size() == 1) {
						return getDocumentationNodes(astNode.eContainer());
					}
				}
				if (astNode instanceof ExportableElement && astNode.eContainer() instanceof ExportDeclaration) {
					nodes = super.getDocumentationNodes(astNode.eContainer());
				}
			}
			if (nodes.isEmpty()) {
				// failure case, was ASI grabbing the doc?
				// backward search for first non-hidden element, over-stepping if it is a LeafNodeWithSyntaxError from
				// ASI.
				ICompositeNode ptNodeOfASTNode = NodeModelUtils.getNode(astNode);
				LeafNode lNode = searchLeafNodeDocumentation(ptNodeOfASTNode);
				if (lNode != null) {
					return Collections.<INode> singletonList(lNode);
				}
			}
			return nodes;
		}
		return super.getDocumentationNodes(object);
	}

	/**
	 * This is a fix for shadowed jsdoc-style documentation. Example:
	 *
	 * <pre>
	 * "use strict" /&#42;&#42;
	 * jsdoc-style comment
	 * &#42;/
	 * class A {
	 * }
	 * </pre>
	 *
	 * Parsing the snippet above will trigger automatic semicolon insertion (ASI) in the first line. Currently ASI
	 * replaces the HiddenLeaf MultiLine comment starting in the same line as the missing ';' by a
	 * {@link LeafNodeWithSyntaxError} which is not hidden. It also transfers the "ownership" from the class-node to the
	 * directive-statement in the parse-tree. The text of the LeafNodeWithSyntaxError is still the original
	 * comment-text.
	 *
	 * Giving the concrete node for "class A", this method will return the LeafNodeWithSyntaxError representing an ASI
	 * and having the textual content of the jsdoc.
	 *
	 * <p>
	 * Looking for lost documentation due to ASI. Goes backward in the AST unless an ASI-modified
	 * {@link LeafNodeWithSyntaxError} is encountered.
	 *
	 * @return A hidden leaf node with jsdoc-documentation or a LeafNodeWithSyntaxError containing the documentation.
	 *         {@code null} if no jsdoc-style documentation could be found.
	 */
	private LeafNode searchLeafNodeDocumentation(ICompositeNode ptNodeOfASTNode) {

		BidiTreeIterator<INode> rootIterator = ptNodeOfASTNode.getRootNode().getAsTreeIterable().iterator();

		// First linearly search the current node, then go back until a documentation or a non-hidden node is
		// encountered.
		// First search:
		int counter = 0;
		boolean found = false; // flag for finding right position in iterator.
		while (rootIterator.hasNext()) {
			INode next = rootIterator.next();
			counter++;
			dump(counter, next);
			if (next == ptNodeOfASTNode) {
				found = true;
				break;
			}
		}
		if (!found) {
			throw new IllegalStateException(
					"Node model broken. Unable to find the current node in the node model by traversing from root.");
		}

		// Second search:
		// go back until we find a LeafNodeWithSyntaxErrors or a different non-hidden leaf.
		ICompositeNode directParent = ptNodeOfASTNode;// .getParent();
		boolean beforeDirectParentNode = false;
		// since going back on a composite node will traverse it's children, we skip investigations until we hit the
		// direct parent. This is marked in beforDirectParentNode
		while (rootIterator.hasPrevious()) {
			INode prev = rootIterator.previous();
			counter--;
			dump(counter, prev);
			if (!beforeDirectParentNode) {
				if (!hasAsParent(prev, directParent)) {
					beforeDirectParentNode = true;
				} else {
					continue;
				}
			}
			if (prev instanceof LeafNodeWithSyntaxError) {
				if (((LeafNodeWithSyntaxError) prev).getSyntaxErrorMessage()
						.getIssueCode() != InternalSemicolonInjectingParser.SEMICOLON_INSERTED) {
					// Some other error, not ASI, stop looking.
					return null;
				}
				// Two cases can happen:
				// 1) it is a ML-documentation
				// 2) it is a line-break
				// single-line documentations don't match the
				String text = prev.getText();
				if (isDocumentationStyle(text)) {
					// found a documentation
					return (LeafNodeWithSyntaxError) prev;
				} else {
					// not a documentation, then continue
				}

			} else if (prev instanceof LeafNode) {
				LeafNode ln = (LeafNode) prev;
				if (!ln.isHidden()) {
					// found a non-hidden node, stop looking!
					return null;
				} else {
					// hidden leaf-node, check for comment.
					String text = prev.getText();
					if (isDocumentationStyle(text)) {
						// found a documentation
						return (LeafNode) prev;
					}
				}
			}
			// other wise keep looking.
		}
		return null;
	}

	/**
	 * Test if directParent is in parent-chain.
	 *
	 * @param child
	 *            child to test.
	 * @param directParent
	 *            parent to look for
	 * @return true if any parent of child (including child) equals to directParent
	 */
	private boolean hasAsParent(INode child, ICompositeNode directParent) {
		INode parent = child;
		while (parent != null) {
			if (parent == directParent)
				return true;

			parent = parent.getParent();
		}
		return false;
	}

	/** logging utility, quickly-returns if logging is off. */
	private void dump(int counter, INode next) {
		if (!logger.isTraceEnabled()) {
			return;
		}

		String leafText = leafText(next);
		logger.trace(String.format("%4d: %90s - %s", counter, next.toString(), leafText));
	}

	/** Extract text of LeafNode. Returns empty String for other node types. */
	private String leafText(INode next) {
		String leafText = "";
		if (next instanceof LeafNode) {
			leafText = ((LeafNode) next).getText();
		}
		return leafText;
	}

	/**
	 * Checks a string {@code text} for documentation capabilities.
	 *
	 * @param text
	 *            potential documentation
	 * @return true if text adheres to the configured documentation rules.
	 */
	public boolean isDocumentationStyle(String text) {
		if (text == null) {
			return false;
		}
		boolean a = commentStartTagRegex.matcher(text).matches();
		boolean b = this.endTagRegex.matcher(text).find();
		return a && b;

	}
}
