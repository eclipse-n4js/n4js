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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.eclipse.lsp4j.Range;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider;
import org.eclipse.xtext.ide.editor.contentassist.antlr.ContentAssistContextFactory;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.TextRegion;

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
	IdeContentProposalProvider contentProposalProvider;

	@Inject
	ContentAssistContextFactory cacFactory;

	@Inject
	ExecutorService executorService;

	/**
	 * Collect all possible import candidates for an unresolved reference at the given range.
	 * <p>
	 * Delegates to content assist functionality by simulating a content assist request with the cursor located at the
	 * end of the given range.
	 */
	public Set<ContentAssistEntry> findImportCandidates(XtextResource resource, Document document, Range range,
			CancelIndicator cancelIndicator) {
		String documentContent = document.getContents();
		String importIdentifier = document.getSubstring(range);
		int offsetStart = document.getOffSet(range.getStart());
		int offsetEnd = document.getOffSet(range.getEnd());
		TextRegion textRegion = new TextRegion(offsetStart, offsetEnd - offsetStart);

		cacFactory.setPool(executorService);
		ContentAssistContext[] cacs = cacFactory.create(documentContent, textRegion, offsetEnd, resource);

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
