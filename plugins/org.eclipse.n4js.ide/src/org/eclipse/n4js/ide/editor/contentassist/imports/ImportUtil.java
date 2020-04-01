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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.eclipse.lsp4j.Range;
import org.eclipse.xtext.diagnostics.AbstractDiagnostic;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider;
import org.eclipse.xtext.ide.editor.contentassist.antlr.ContentAssistContextFactory;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.TextRegion;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.base.Objects;
import com.google.inject.Inject;

/**
 * Utility class for adding imports to a document and other import-related modifications.
 * <p>
 * Currently, this simply delegates to the corresponding functionality in Content Assist to allow reuse of this
 * functionality from other parts of the UI implementation (e.g. from a quick fix). This class should be removed when
 * reusable parts (esp. handling of imports) have been factored out of content assist code.
 */
public class ImportUtil {

	@Inject
	private IdeContentProposalProvider contentProposalProvider;

	@Inject
	private ContentAssistContextFactory cacFactory;

	@Inject
	private ExecutorService executorService;

	public List<ContentAssistEntry> findImportCandidatesForUnresolvedReferences(XtextResource resource,
			Document document, CancelIndicator cancelIndicator) {
		// ensure lazy cross-references are resolved
		// (no need to invoke the resource validator, here, because we are only interested in linking diagnostics)
		if (resource instanceof LazyLinkingResource) {
			((LazyLinkingResource) resource).resolveLazyCrossReferences(cancelIndicator);
		}
		// find import candidates for each unresolved reference
		String documentContent = document.getContents();
		Set<String> done = new HashSet<>();
		List<ContentAssistEntry> result = new ArrayList<>();
		for (org.eclipse.emf.ecore.resource.Resource.Diagnostic diagnosticEMF : resource.getErrors()) {
			if (diagnosticEMF instanceof AbstractDiagnostic) {
				AbstractDiagnostic diagnostic = (AbstractDiagnostic) diagnosticEMF;
				if (Diagnostic.LINKING_DIAGNOSTIC.equals(diagnostic.getCode())) {
					String unresolvedReferenceAsText = documentContent.substring(diagnostic.getOffset(),
							diagnostic.getOffset() + diagnostic.getLength());
					if (done.add(unresolvedReferenceAsText)) {
						Set<ContentAssistEntry> candidates = findImportCandidates(resource, document,
								diagnostic.getOffset(), diagnostic.getLength(), cancelIndicator);
						// iff unambiguous, add candidate to the result
						if (candidates.size() == 1) {
							result.add(IterableExtensions.head(candidates));
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Collect all possible import candidates for an unresolved reference at the given range.
	 * <p>
	 * Delegates to content assist functionality by simulating a content assist request with the cursor located at the
	 * end of the given range.
	 */
	public Set<ContentAssistEntry> findImportCandidates(XtextResource resource, Document document, Range range,
			CancelIndicator cancelIndicator) {
		int offset = document.getOffSet(range.getStart());
		int length = document.getOffSet(range.getEnd()) - offset;
		return findImportCandidates(resource, document, offset, length, cancelIndicator);
	}

	private Set<ContentAssistEntry> findImportCandidates(XtextResource resource, Document document, int offset,
			int length, CancelIndicator cancelIndicator) {
		String documentContent = document.getContents();
		String importIdentifier = documentContent.substring(offset, offset + length);
		TextRegion textRegion = new TextRegion(offset, length);

		cacFactory.setPool(executorService);
		ContentAssistContext[] cacs = cacFactory.create(documentContent, textRegion, offset + length, resource);

		Collection<ContentAssistContext> contexts = Arrays.asList(cacs);
		ContentProposalAcceptorCollector acceptor = new ContentProposalAcceptorCollector(cancelIndicator);
		contentProposalProvider.createProposals(contexts, acceptor);

		Set<ContentAssistEntry> caEntries = new LinkedHashSet<>();
		for (ContentAssistEntry caEntry : acceptor.caEntries) {
			if (Objects.equal(importIdentifier, caEntry.getProposal())) {
				caEntries.add(caEntry);
			}
		}
		return caEntries;
	}

	private static class ContentProposalAcceptorCollector implements IIdeContentProposalAcceptor {
		final List<ContentAssistEntry> caEntries = new ArrayList<>();
		private final CancelIndicator cancelIndicator;

		ContentProposalAcceptorCollector(CancelIndicator cancelIndicator) {
			this.cancelIndicator = cancelIndicator;

		}

		@Override
		public void accept(ContentAssistEntry entry, int priority) {
			if (entry != null) {
				caEntries.add(entry);
			}
		}

		@Override
		public boolean canAcceptMoreProposals() {
			return !cancelIndicator.isCanceled();
		}

	}

}
