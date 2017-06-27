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
package org.eclipse.n4js.xpect.ui.common

import com.google.common.collect.Multimap
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.nodemodel.BidiTreeIterator
import org.eclipse.xtext.nodemodel.ILeafNode
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.validation.Issue
import org.eclipse.xtext.ui.editor.quickfix.IssueResolution
import org.eclipse.jface.text.contentassist.ICompletionProposal
import junit.framework.AssertionFailedError
import org.eclipse.xtend.lib.annotations.Accessors

/**
 */
public class QuickFixTestHelper {

	/**
	 * Select all issues from {@link #offset2issue} concerning the {@link #offStartLine}
	 *
	 * @param offStartLine line number
	 * @param line2issue map of issues
	 * @return sorted list of Issues
	 */
	public static def List<Issue> extractAllIssuesInLine(int offStartLine, Multimap<Integer, Issue> offset2issue) {
		val ArrayList<Issue> result = newArrayList(offset2issue.values().filter[it.lineNumber == offStartLine].toList);

		// sort by a) offset or b) message
		// TODO arguable there are other things to consider:
		// - closeness to cursor (offset)
		// - severity: errors first
		// - selected region ( offset+length, does it match the selected region)
		// - does the cursor is in the issue-region
		result.sortInplace(
			[a, b|val first = b.offset - a.offset;
				if (first != 0)
					first
				else {
					a.message.compareToIgnoreCase(b.message)
				}])

		return result;
	}

	/**
	 * Find all EObjects beginning or in same line. Goes over all LeafNodes in same line and resolved the associated
	 * EObjects.
	 *
	 * @param offsetNode
	 *            Node at cursor position
	 * @return list of EObjects directly associated with this line. (but no wrapping Elements)
	 */
	def public static List<EObject> elementsInSameLine(ILeafNode offsetNode) {
		val ArrayList<EObject> result = newArrayList();
		val int line = offsetNode.getStartLine();

		var INode firstNode = null;
		val ArrayList<INode> nodesInLine = newArrayList();

		// Find beginning of Line: first Leaf-Node, that starts in this line
		val BidiTreeIterator<INode> iter = offsetNode.getRootNode().getAsTreeIterable().iterator();
		while (firstNode === null && iter.hasNext()) {
			val INode next = iter.next();
			if (next.getEndLine() < line) {
				iter.prune();
			} else if (next.getStartLine() >= line && next instanceof ILeafNode) {
				firstNode = next;
				nodesInLine.add(firstNode);
			}
		}

		// Find last Ileaf, that starts in line
		while (iter.hasNext()) {
			val INode next = iter.next();
			if (next.getStartLine() > line) {
				iter.prune();
			} else {
				if (next instanceof ILeafNode) {
					nodesInLine.add(next);
				}
			}
		}

		if (nodesInLine.isEmpty())
			return emptyList();

		for (INode ln : nodesInLine) {
			val EObject eo = NodeModelUtils.findActualSemanticObjectFor(ln);
			if (eo !== null && !result.contains(eo)) {
				result.add(eo);
			}
		}

		return result;
	}

	/**
 	 * Breaks lines into strings on comma-positions and remove outer quotes.
 	 * Beware: not quote-safe (Commas in matching quotes are <b>not</b> treated specifically.)
 	 * @param line to break up
 	 */
	public def static List<String> separateOnCommaAndQuote(CharSequence line) {
		return line.separateOnComma(true);
	}

	/**
	 * Breaks up a CharSequence into a List of Strings on comma-characters.
	 * Beware: not quote-safe (Commas in matching quotes are <b>not</b> treated specifically.)
	 * @param line to break up
	 * @param removeQuote - if true, remove outer quotes on  all strings in list
	 */
	public def static List<String> separateOnComma(CharSequence line, boolean removeQuote) {
		var result = line.toString.split(",").map[it.trim]
		if (removeQuote) {
			result = result.map [
				if (it.startsWith("\"") && it.endsWith("\"") || it.startsWith("'") && it.endsWith("'")) {
					it.substring(1, it.length - 1)
				} else
					it
			]
		}
		return result.toList;
	}

		/**
	 * Create a single line of comma-separated & single-quoted label strings.
	 * @param list of resolutions
	 */
	public static def String asString(List<IssueResolution> resolutions) {
		resolutions.map[label].reduce[p1, p2|"'" + p1 + "', '" + p2 + "'"]
	}

    /**
     * Create a single line of comma-separated & single-quoted display strings.
     * @param s list of completion proposals
     */
	public static def String asString2(List<ICompletionProposal> s) {
		s.map["'" + it.displayString + "'"].reduce[p1, p2|p1 + ", " + p2]
	}

   /**
	 * Return a specific IssueResolution out of resolutions.
	 * @param resolutions candidates
	 * @param labelPart matcher
	 * @return a resolution with a label containing labelPart as substring. Never null.
	 * @throws AssertionFailedError - if more then one or none resolution contains labelPart in its label
	 */
	public static def IssueResolution selectSingleOrFail(List<IssueResolution> resolutions, String labelPart) {
		val matched = resolutions.filter[it.label.contains(labelPart)].toList
		if (matched.size > 1) {
			throw new AssertionFailedError(
				"The selected issue resolution '" + labelPart + "' is matched by more then one (" + matched.size +
					") resolutions " + matched.asString)
		} else if (matched.size < 1) {
			throw new AssertionFailedError("No issue-resolution found with name containing '" + labelPart + "'");
		}
		matched.head
	}


	/**
	 * This simple algorithm can detect changes between the two multiline texts before & after assuming
	 * a single line has been changed, inserted or deleted.
	 *
	 * If more than one line is effected the result is probably useless.
	 *
	 * Query this with {@ChangeInfo#isMoreThanOne()}
	 *
	 * @param before first text
	 * @param after second text
	 *
	 * @return List of changes. If more than one line is effected the result is probably useless.
	 */
	public static def ChangeInfo extractSingleChangedLine(String before, String after) {

		// Java line-delimiter are single character: T?ODO get current active line-delimitor for the editor
		val delim = '\n'
		val bLines = before.split(delim)
		val aLines = after.split(delim)

		val ChangeInfo ci = new ChangeInfo()

		var bo = 0
		var ao = 0

		// assuming single line inserts and matching lines around them.
		for (var int bi = 0, var ai = 0; bi < bLines.length && ai < aLines.length; bi++, ai++) {
			val b = bLines.get(bi);
			val a = aLines.get(ai);
			if (! b.trim.equals(a.trim)) {

				// unmatch, check if a line inserted
				if (ai + 1 < aLines.length && b.trim.equals(aLines.get(ai + 1).trim)) {

					// match with next in a, newly inserted A
					ci.add(bi, bo, "", ao, aLines.get(ai))
					ai++
					ao += aLines.get(ai).length + delim.length
				} else // or this line is removed.
				if (bi + 1 < bLines.length && bLines.get(bi + 1).trim.equals(a.trim)) {
					ci.add(bi, bo, bLines.get(bi), ao, "")
					bi++
					bo += aLines.get(bi).length + delim.length
				} else {

					// a real difference
					ci.add(bi, bo, bLines.get(bi), ao, aLines.get(ai))
				}
			} else {
				// match.
			}
			ao += aLines.get(ai).length + delim.length
			bo += bLines.get(bi).length + delim.length
		}
		return ci
	}

	/**
	 * Container to track changed lines in multiline strings.
	 */
	static class ChangeInfo {
		@Accessors static class ChangedLine {
			val int lineNumber
			val int beforeOffset
			val String before
			val int afterOffset
			val String after

			override toString() {
			'''L:«lineNumber»[«before»|«after»]'''
		}
		}

		val List<ChangedLine> changes = newArrayList();

		public new() {
		}

		def add(int n, int beforeOffset, String b, int afterOffset, String a) {
			changes += new ChangedLine(n, beforeOffset, b, afterOffset, a)
		}

		def asString() {
			changes.map["L:" + lineNumber + " '" + after + "'\n"].reduce[p1, p2|p1 + p2]
		}

		public def boolean isMoreThanOne() {
			return changes.size > 1
		}

		public def boolean isEmpty() {
			return changes.empty
		}

		public def first() { return changes.get(0) }

		public def get(int i) { return changes.get(i) }

		public def size() { return changes.size }

	}

}
