/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.contentassist;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * An {@link ICompletionProposalAcceptor} that will keep track of context/reference pairs it has seen already. See
 * {@link #hasSeenScopeFor(EObject, EReference)} for details.
 */
public class PickyCompletionProposalAcceptor extends ICompletionProposalAcceptor.Delegate {

	private final Set<Pair<EObject, EReference>> seenScopes = new HashSet<>();

	/** See {@link PickyCompletionProposalAcceptor}. */
	public PickyCompletionProposalAcceptor(ICompletionProposalAcceptor delegate) {
		super(delegate);
	}

	/**
	 * Tells if this acceptor has already seen proposals derived from the scope for the given context and reference. In
	 * addition, the scope for the given context and reference is marked as having been seen (if not marked as such
	 * already).
	 */
	public boolean hasSeenScopeFor(EObject context, EReference reference) {
		// Maybe this logic could be improved further by not just returning true in case of identical context/reference
		// but also for "similar" contexts/references of which we know they will lead to an equal scope.
		return !seenScopes.add(new Pair<>(context, reference));
	}

	/**
	 * Convenience method. Like {@link #hasSeenScopeFor(EObject, EReference)}, but performs casting of the acceptor.
	 */
	public static boolean hasSeenScopeFor(ICompletionProposalAcceptor acceptor, EObject context, EReference reference) {
		while (acceptor instanceof ICompletionProposalAcceptor.Delegate
				&& !(acceptor instanceof PickyCompletionProposalAcceptor)) {
			acceptor = ((ICompletionProposalAcceptor.Delegate) acceptor).getDelegate();
		}
		if (acceptor instanceof PickyCompletionProposalAcceptor) {
			return ((PickyCompletionProposalAcceptor) acceptor).hasSeenScopeFor(context, reference);
		}
		return false;
	}
}
