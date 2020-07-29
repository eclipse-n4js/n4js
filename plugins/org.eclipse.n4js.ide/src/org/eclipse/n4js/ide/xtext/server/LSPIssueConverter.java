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
package org.eclipse.n4js.ide.xtext.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.lsp4j.Position;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;

/**
 * Converts from plain {@link Issue}s to {@link LSPIssue}s.
 */
@SuppressWarnings("javadoc")
public class LSPIssueConverter {

	@Inject
	private OperationCanceledManager operationCanceledManager;

	public List<LSPIssue> convertToLSPIssues(Resource resource, Collection<? extends Issue> issues,
			CancelIndicator cancelIndicator) {

		List<LSPIssue> result = new ArrayList<>(issues.size());
		XDocument document = null; // create only if necessary
		for (Issue issue : issues) {
			operationCanceledManager.checkCanceled(cancelIndicator);
			LSPIssue lspIssue;
			if (issue instanceof LSPIssue) {
				lspIssue = (LSPIssue) issue;
			} else {
				if (!(resource instanceof XtextResource)) {
					continue;
				}
				document = createDocument((XtextResource) resource);
				lspIssue = convertToLSPIssue(issue, document);
			}
			result.add(lspIssue);
		}
		return result;
	}

	/*
	 * Review feedback:
	 *
	 * Newer versions of Xtext do have Issue.getEndLine and things.
	 */
	// TODO IDE-3402 consider avoiding the need for an XDocument here (use NodeModelUtils#getLineAndColumn())
	public LSPIssue convertToLSPIssue(Issue issue, XDocument document) {
		if (issue instanceof LSPIssue) {
			return (LSPIssue) issue;
		}
		if (document == null) {
			throw new IllegalArgumentException("a document is required if the given issue is not already an LSPIssue");
		}
		LSPIssue result = new LSPIssue(issue);
		Position end = document.getPosition(issue.getOffset() + issue.getLength());
		if (end == null) {
			Position start = new Position(toZeroBasedInt(issue.getLineNumber()), toZeroBasedInt(issue.getColumn()));
			end = new Position(start.getLine(), start.getCharacter() + 1);
		}
		result.setLineNumberEnd(end.getLine() + 1);
		result.setColumnEnd(end.getCharacter() + 1);
		return result;
	}

	// TODO GH-1537: Remove this function when org.eclipse.xtext.validation.Issue.IssueImpl#lineNumber and #column are
	// initialized with '0'
	private int toZeroBasedInt(Integer oneBasedInteger) {
		return oneBasedInteger == null ? 0 : (oneBasedInteger - 1);
	}

	protected XDocument createDocument(XtextResource resource) {
		String text = resource.getParseResult().getRootNode().getText();
		return new XDocument(Integer.valueOf(1), text);
	}
}
