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
package org.eclipse.n4js.jsdoc.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.utils.nodemodel.HiddenLeafAccess;
import org.eclipse.n4js.utils.nodemodel.HiddenLeafAccess.CommentInfo;
import org.eclipse.n4js.utils.nodemodel.HiddenLeafAccess.HiddenLeafs;
import org.eclipse.n4js.utils.nodemodel.HiddenLeafAccess.LeafInfo;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import com.google.inject.Inject;

/**
 * Tries to retrieve comment form given EObject. Returns {@link CommentCandidate} or null.
 *
 * Initiate class with @Inject.
 */
public class DocCommentLookup {
	@Inject
	private HiddenLeafAccess hla;

	@Inject
	private N4JSGrammarAccess grammarAccess;

	/**
	 * Naive comment lookup, that doesn't consider parent. Returns comment that is closest to the lookup element. If
	 * flag is provided includes doublestar comments into search. Doublestar comments are prioritized over single star
	 * comments.
	 *
	 * Throws {@link InstantiationException} when it stumbles upon MultiLine comment that doesn't start with proper
	 * markers (</*> and <*\/>)
	 *
	 * @param eObject
	 *            element on which lookup is performed
	 * @param considerDoubleStar
	 *            swithc to include in luukup comments with double star
	 * @return CommentCandidate for given eObject.
	 * @throws InstantiationException
	 *             if comment on element is malformatted.
	 */
	public CommentCandidate findJSDocComment(EObject eObject, boolean considerDoubleStar)
			throws InstantiationException {
		if (eObject == null) {
			return null;
		}

		List<INode> comments = findMultiLineComments(eObject);
		// fast result
		if (comments == null || comments.isEmpty()) {
			return null;
		}

		if (considerDoubleStar) {
			return pickCommentConsiderDoubleStar(comments);
		}
		return pickCommentNoDoubleStar(comments);
	}

	/**
	 * Finds multi line comments on eObject. Comment is consider multiline by its type (e.g. start-end markers). Actual
	 * conents can be either single line, or multiline content.
	 *
	 * @param eObject
	 *            on which we look for multiline comment
	 * @return list of nodes with comment, can be empty if no comments
	 */
	protected List<INode> findMultiLineComments(EObject eObject) {
		// get node
		INode elementNode = NodeModelUtils.findActualNodeFor(eObject);
		HiddenLeafs hLeafs = hla.getHiddenLeafsBefore(elementNode);
		// check for comments
		if (!hLeafs.containsComment()) {
			return null;
		}
		// get all comments
		List<LeafInfo> leafs = hLeafs.getLeafs();
		List<INode> comments = new ArrayList<>();

		final TerminalRule SL = grammarAccess.getSL_COMMENTRule();
		// get only MultiLine comments
		for (LeafInfo li : leafs) {
			if (li instanceof CommentInfo) {
				INode commentNode = li.getNode();
				EObject ge = commentNode.getGrammarElement();
				// are we sure we get here only ML/SL ?
				if (ge != SL) { // ignore SL
					// finds ML and SML
					comments.add(commentNode);
				}
			}
		}
		return comments;
	}

	/**
	 * Select comment closest to lookup element. Assume comments to be ordered list. Ignores comments starting with
	 * doublestar.
	 *
	 * @param comments
	 *            list of commnets on luukup elements
	 * @throws InstantiationException
	 *             if comment is malformed
	 */
	protected CommentCandidate pickCommentNoDoubleStar(List<INode> comments) throws InstantiationException {
		ListIterator<INode> iter = comments.listIterator(comments.size());
		String candidateTextString = null;
		while (iter.hasPrevious()) {
			candidateTextString = iter.previous().getText();
			if (!candidateTextString.startsWith("/**")) {
				return new CommentCandidate(candidateTextString);
			}
		}
		return null;
	}

	/**
	 * Select comment closest to lookup element. Assume comments to be ordered list. Includes comments with doublestar
	 * in search results. DoubleStar comments are prioritized over singlestar comemnts.
	 *
	 * @param comments
	 *            list of commnets on luukup elements
	 * @throws InstantiationException
	 *             if comment is malformed
	 */
	protected CommentCandidate pickCommentConsiderDoubleStar(List<INode> comments) throws InstantiationException {
		// one commemnt - wins
		if (comments.size() == 1) {
			return new CommentCandidate(comments.get(0).getText());
		}

		// multiple comments
		// arrList has reversed order
		ListIterator<INode> iter = comments.listIterator(comments.size());
		// so far best candidate
		String bestCandidateString = iter.previous().getText();
		if (bestCandidateString.startsWith("/**")) {
			return new CommentCandidate(bestCandidateString); // automatic win
		}
		// if we get here we have first single star comment, so
		// check if there is supreme candidate
		String candidateTextString = "";
		while (iter.hasPrevious()) {
			candidateTextString = iter.previous().getText();
			if (candidateTextString.startsWith("/**")) {
				bestCandidateString = candidateTextString;
				break; // first supreme wins
			}
		}
		return new CommentCandidate(bestCandidateString);
	}

}
