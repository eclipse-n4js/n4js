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
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService2;
import org.eclipse.xtext.resource.XtextResource;
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
@SuppressWarnings("restriction")
public class ImportUtil {

	@Inject
	IdeContentProposalProvider contentProposalProvider;

	@Inject
	ContentAssistContextFactory cacFactory;

	@Inject
	ExecutorService executorService;

	/**
	 * Collect all possible import candidates for the given offset. If 'allowCompletion' is <code>true</code>, this is
	 * very similar to pressing CTRL+Space at the given offset.
	 */
	public Set<ContentAssistEntry> findImportCandidates(ICodeActionService2.Options options) {
		Document doc = options.getDocument();
		String documentContent = doc.getContents();
		Range range = options.getCodeActionParams().getRange();
		String importIdentifier = doc.getSubstring(range);
		int offsetStart = doc.getOffSet(range.getStart());
		int offsetEnd = doc.getOffSet(range.getEnd());
		TextRegion textRegion = new TextRegion(offsetStart, offsetEnd - offsetStart);
		XtextResource resource = options.getResource();

		cacFactory.setPool(executorService);
		ContentAssistContext[] cacs = cacFactory.create(documentContent, textRegion, offsetEnd, resource);

		Collection<ContentAssistContext> contexts = Arrays.asList(cacs);
		ContentProposalAcceptorCollector acceptor = new ContentProposalAcceptorCollector();
		contentProposalProvider.createProposals(contexts, acceptor);

		Set<ContentAssistEntry> caEntries = new LinkedHashSet<>();
		for (ContentAssistEntry caEntry : acceptor.caEntries) {
			if (Objects.equal(importIdentifier, caEntry.getProposal())) {
				caEntries.add(caEntry);
			}
		}
		return caEntries;
	}

	class ContentProposalAcceptorCollector implements IIdeContentProposalAcceptor {
		final List<ContentAssistEntry> caEntries = new ArrayList<>();

		@Override
		public void accept(ContentAssistEntry entry, int priority) {
			if (entry != null) {
				caEntries.add(entry);
			}
		}

		@Override
		public boolean canAcceptMoreProposals() {
			return true;
		}

	}

}
