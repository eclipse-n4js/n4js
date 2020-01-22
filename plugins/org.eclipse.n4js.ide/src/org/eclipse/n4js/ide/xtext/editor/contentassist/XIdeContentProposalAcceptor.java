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
package org.eclipse.n4js.ide.xtext.editor.contentassist;

import java.util.Comparator;

import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalAcceptor;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Overwritten since {@link IdeContentProposalAcceptor} will remove proposals with the same label
 */
public class XIdeContentProposalAcceptor extends IdeContentProposalAcceptor {

	private final Comparator<Pair<Integer, ContentAssistEntry>> compareVersions = Comparator.comparing(Pair::getKey);
	private final Comparator<Pair<Integer, ContentAssistEntry>> compareLabels = Comparator
			.comparing(p -> Strings.emptyIfNull(p.getValue().getLabel()));
	private final Comparator<Pair<Integer, ContentAssistEntry>> compareDescrs = Comparator
			.comparing(p -> Strings.emptyIfNull(p.getValue().getDescription()));
	private final Comparator<Pair<Integer, ContentAssistEntry>> comparePropos = Comparator
			.comparing(p -> Strings.emptyIfNull(p.getValue().getProposal()));
	private final Comparator<Pair<Integer, ContentAssistEntry>> comparator = compareVersions
			.thenComparing(compareLabels).thenComparing(compareDescrs).thenComparing(comparePropos);

	@Override
	public int compare(Pair<Integer, ContentAssistEntry> p1, Pair<Integer, ContentAssistEntry> p2) {
		return comparator.compare(p1, p2);
	}

}
