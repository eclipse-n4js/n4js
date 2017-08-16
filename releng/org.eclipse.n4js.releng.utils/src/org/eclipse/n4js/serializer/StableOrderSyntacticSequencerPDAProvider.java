/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.serializer;

import java.util.Iterator;

import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.GrammarAliasFactory;
import org.eclipse.xtext.serializer.analysis.GrammarElementDeclarationOrder;
import org.eclipse.xtext.serializer.analysis.SyntacticSequencerPDAProvider;
import org.eclipse.xtext.util.formallang.Nfa;
import org.eclipse.xtext.util.formallang.NfaToProduction;

// TODO remove with Xtext 2.13
/**
 * Patched {@link SyntacticSequencerPDAProvider} that yields a stable order of ambiguous syntaxes since the output is
 * sorted by occurrence in the grammar.
 *
 * This will become obsolete with Xtext 2.13.
 */
@SuppressWarnings({ "restriction" })
public class StableOrderSyntacticSequencerPDAProvider extends SyntacticSequencerPDAProvider {

	/**
	 * Patched transition.
	 *
	 * @see #getAmbiguousSyntax()
	 */
	protected static class StableOrderSynTransition extends SynTransition {

		/**
		 * Constructor. Delegates to the super constructore. Please move on, there is nothing to see here.
		 */
		public StableOrderSynTransition(ISynAbsorberState source, ISynAbsorberState target) {
			super(source, target);
		}

		/**
		 * Specialized to yield an {@link AbstractElementAlias} that has sorted alternatives where the occurrence in the
		 * grammar defines the order of the output.
		 */
		@Override
		public AbstractElementAlias getAmbiguousSyntax() {
			if (ambiguousSyntax != null)
				return ambiguousSyntax;
			Nfa<ISynState> nfa = getAmbiguousNfa();
			NfaToProduction prod = new NfaToProduction();

			// TODO remove with Xtext 2.13 if the patch makes it into the framework
			// FIX is here: We want to use a stable order
			Grammar grammar = getGrammar(nfa);
			GrammarElementDeclarationOrder order = GrammarElementDeclarationOrder.get(grammar);
			ambiguousSyntax = prod.nfaToGrammar(nfa, new GetGrammarElement(), order, new GrammarAliasFactory());
			// end fix
			return ambiguousSyntax;
		}

		/**
		 * Extracts the grammar from this transition or the NFA if this transition does not point to an
		 * {@link AbstractElement}.
		 */
		private Grammar getGrammar(Nfa<ISynState> nfa) {
			AbstractElement grammarElement = getGrammarElement();
			if (grammarElement == null) {
				grammarElement = nfa.getStart().getGrammarElement();
				if (grammarElement == null) {
					grammarElement = nfa.getStop().getGrammarElement();
					if (grammarElement == null) {
						Iterator<ISynState> iter = nfa.getStart().getFollowers().iterator();
						while (grammarElement == null && iter.hasNext()) {
							grammarElement = iter.next().getGrammarElement();
						}
					}
				}
			}
			Grammar grammar = GrammarUtil.getGrammar(grammarElement);
			return grammar;
		}
	}

	/**
	 * Creates a transtion that yields a stable output for the ambigous syntax.
	 */
	@Override
	protected SynTransition createTransition(SynAbsorberState source, SynAbsorberState target) {
		return new StableOrderSynTransition(source, target);
	}

}
