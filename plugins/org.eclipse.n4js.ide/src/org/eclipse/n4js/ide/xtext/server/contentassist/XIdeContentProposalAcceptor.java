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

	private final Comparator<Pair<Integer, ContentAssistEntry>> comparator = Comparator
			.comparingInt(Pair<Integer, ContentAssistEntry>::getKey).reversed().thenComparing(p -> {
				ContentAssistEntry entry = p.getValue();
				String string = entry.getLabel();
				if (string == null) {
					string = entry.getProposal();
				}
				return string;
			}, String.CASE_INSENSITIVE_ORDER);

	private final Map<String, Pair<Integer, ContentAssistEntry>> entries = new HashMap<>();

	private final CancelIndicator cancelIndicator;

	XIdeContentProposalAcceptor(CancelIndicator cancelIndicator) {
		this.cancelIndicator = cancelIndicator;
	}

	@Override
	public void accept(ContentAssistEntry entry, int priority) {
		if (entry != null) {
			if (entry.getProposal() == null) {
				throw new IllegalArgumentException("proposal must not be null.");
			}
			Pair<Integer, ContentAssistEntry> prev = entries.put(entry.getProposal(), Pair.of(priority, entry));
			if (prev != null && prev.getKey() > priority) {
				entries.put(entry.getProposal(), prev);
			}
		}
	}

	@Override
	public boolean canAcceptMoreProposals() {
		boolean result = !cancelIndicator.isCanceled();
		if (!result) {
			System.err.println("Cancelled");
		}
		// return true;
		return result && entries.size() < DEFAULT_PROPOSALS_LIMIT;
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
