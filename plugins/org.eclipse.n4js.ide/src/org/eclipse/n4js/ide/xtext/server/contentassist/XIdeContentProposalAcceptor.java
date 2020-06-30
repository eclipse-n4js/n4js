package org.eclipse.n4js.ide.xtext.server.contentassist;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

/**
 * @author Sven Efftinge - Initial contribution and API
 *
 * @since 2.11
 */
public class XIdeContentProposalAcceptor
		implements IIdeContentProposalAcceptor {

	private static final int DEFAULT_PROPOSALS_LIMIT = 1000;

	private final CancelIndicator cancelIndicator;
	private final Map<String, Pair<Integer, ContentAssistEntry>> entries = new HashMap<>();
	private final Comparator<Pair<Integer, ContentAssistEntry>> comparator = Comparator
			.comparingInt(Pair<Integer, ContentAssistEntry>::getKey)
			.thenComparing(p -> p.getValue().toString(), String.CASE_INSENSITIVE_ORDER);

	XIdeContentProposalAcceptor(CancelIndicator cancelIndicator) {
		this.cancelIndicator = cancelIndicator;
	}

	@Override
	public void accept(ContentAssistEntry entry, int priority) {
		if (entry != null) {
			if (entry.getProposal() == null) {
				throw new IllegalArgumentException("proposal must not be null.");
			}
			String entryString = entry.toString();
			if (entries.containsKey(entryString)) {
				Pair<Integer, ContentAssistEntry> existingProposal = entries.get(entryString);
				priority = existingProposal.getKey();
			}
			entries.put(entryString, Pair.of(priority, entry));
		}
	}

	@Override
	public boolean canAcceptMoreProposals() {
		if (cancelIndicator.isCanceled()) {
			return true;
		}
		return entries.size() < DEFAULT_PROPOSALS_LIMIT;
	}

	/**
	 * Return all the entries.
	 */
	public Iterable<ContentAssistEntry> getEntries() {
		ImmutableList<Pair<Integer, ContentAssistEntry>> sortedList = FluentIterable.from(entries.values())
				.toSortedList(comparator);
		return FluentIterable.from(sortedList).transform(Pair::getValue);
	}
}
