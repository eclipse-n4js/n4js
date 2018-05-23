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
package org.eclipse.n4js.ui.utils

import com.google.inject.Inject
import java.util.List
import org.eclipse.jface.text.BadLocationException
import org.eclipse.jface.text.contentassist.ICompletionProposal
import org.eclipse.jface.text.source.ISourceViewer
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext
import org.eclipse.xtext.ui.editor.contentassist.FQNPrefixMatcher
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor
import org.eclipse.xtext.ui.editor.contentassist.IContentProposalProvider
import org.eclipse.xtext.ui.editor.contentassist.PrefixMatcher
import org.eclipse.xtext.ui.editor.model.IXtextDocument
import org.eclipse.xtext.validation.Issue

/**
 * Utility class for adding imports to a document and other import-related modifications.
 * <p>
 * Currently, this simply delegates to the corresponding functionality in Content Assist to allow reuse
 * of this functionality from other parts of the UI implementation (e.g. from a quick fix). This class
 * should be removed when reusable parts (esp. handling of imports) have been factored out of content
 * assist code.
 */
public class ImportUtil {

	@Inject
	private ContentAssistContext.Factory contentAssistContextFactory;
	@Inject
	private IContentProposalProvider contentProposalProvider;

	@Inject
	extension IssueUtilN4


	/**
	 * Collect all possible import candidates for the given issue. It is assumed that the issue spans a name
	 * and an import is to be added such that the name can be resolved (as is the case with 'Couldn't resolve
	 * reference to ...' issues created by the Xtext linker).
	 * <p>
	 * Parameter 'allowCompletion' is explained {@link #findImportCandidates(ISourceViewer,IXtextDocument,int,boolean) here}.
	 *
	 * @see #findImportCandidates(ISourceViewer,IXtextDocument,int,boolean)
	 */
	def List<ICompletionProposal> findImportCandidates(Issue issue, boolean allowCompletion) {
		findImportCandidates(issue.viewer, issue.document, issue.offset + issue.length, allowCompletion)
	}

	/**
	 * Collect all possible import candidates for the given offset. If 'allowCompletion' is <code>true</code>,
	 * this is very similar to pressing CTRL+Space at the given offset.
	 *
	 * @param allowCompletion if <code>true</code>, collects imports for all names <em>starting</em> with the
	 *                        character sequence before the given offset; if <code>false</code>, collects imports
	 *                        only for exact matches.
	 */
	def List<ICompletionProposal> findImportCandidates(ISourceViewer viewer, IXtextDocument doc, int offset, boolean allowCompletion) {
		doc.readOnly[resource|
			val collector = new CollectingAcceptor

			val contentAssistContexts = createContentAssistContexts(viewer, offset, resource, allowCompletion);
			for(contentAssistContext : contentAssistContexts)
				contentProposalProvider.createProposals(contentAssistContext, collector);

			collector.get().toList
		]
	}

	private def ContentAssistContext[] createContentAssistContexts(ISourceViewer viewer, int offset, XtextResource resource, boolean allowCompletion) {
		try {
			val result = contentAssistContextFactory.create(viewer, offset, resource);
			if(allowCompletion) {
				// we are re-using functionality from content assist where completion of names is the standard case,
				// -> so nothing to modify in this case
				return result;
			}
			else {
				// disallow completion in each created ContentAssistContext
				return result.map[disallowCompletion];
			}
		}
		catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * Disallow completion in the given ContentAssistContext by wrapping the contained matcher with an ExactMatcher.
	 * Disallowing completion means that in all matching proposals the name must have the same length as the prefix.
	 */
	private def ContentAssistContext disallowCompletion(ContentAssistContext context) {
		context.copy().setMatcher(new ExactMatcher(context.matcher)).toContext()
	}


	/**
	 * An acceptor that simply collects the accepted proposals.
	 */
	private static class CollectingAcceptor implements ICompletionProposalAcceptor {

		val proposals = <ICompletionProposal>newLinkedHashSet()

		def get() {
			proposals
		}

		override accept(ICompletionProposal proposal) {
			proposals.add(proposal)
		}

		override canAcceptMoreProposals() {
			true
		}
	}

	/**
	 * A matcher that makes sure we have an exact match, i.e. name and prefix have same length.
	 */
	private static class ExactMatcher extends PrefixMatcher {

		val PrefixMatcher delegate;

		new(PrefixMatcher delegate) {
			this.delegate = delegate;
		}

		override isCandidateMatchingPrefix(String name, String prefix) {
			if(!delegate.isCandidateMatchingPrefix(name,prefix))
				return false;
			// ensure that name (or the last segment, in case of a FQN) has same length as 'prefix'
			var lastSegment = name;
			if(delegate instanceof FQNPrefixMatcher) {
				val lsf = delegate.lastSegmentFinder;
				val delimiter = delegate.delimiter;
				if(lastSegment.indexOf(delimiter)>=0) {
					lastSegment = lsf.getLastSegment(name,delimiter);
					if(lastSegment===null)
						return false;
				}
			}
			return lastSegment.length === prefix.length;
		}
	}
}
