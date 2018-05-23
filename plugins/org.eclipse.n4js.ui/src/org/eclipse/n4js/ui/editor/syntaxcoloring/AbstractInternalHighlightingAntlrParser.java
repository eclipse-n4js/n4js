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
package org.eclipse.n4js.ui.editor.syntaxcoloring;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.DFA;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.MissingTokenException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.UnwantedTokenException;

/**
 * Base class for the generated parser that is used for lexical highlighting.
 */
public abstract class AbstractInternalHighlightingAntlrParser extends Parser {

	/**
	 * Delegates to super constructor.
	 */
	protected AbstractInternalHighlightingAntlrParser(TokenStream input) {
		super(input);
	}

	/**
	 * Delegates to super constructor.
	 */
	protected AbstractInternalHighlightingAntlrParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	/**
	 * Generated subtype is supposed to implement this method.
	 */
	public TokenStream getInput() {
		return input;
	}

	@Override
	public String getErrorMessage(RecognitionException e, String[] tokenNames) {
		throw new UnsupportedOperationException("getErrorMessage");
	}

	@Override
	public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
		throw new UnsupportedOperationException("displayRecognitionError");
	}

	@Override
	public void reportError(RecognitionException e) {
		if (state.errorRecovery) {
			return;
		}
		state.syntaxErrors++; // don't count spurious
		state.errorRecovery = true;
	}

	/**
	 * Same recovery logic as for the production parser.
	 */
	@Override
	protected Object recoverFromMismatchedToken(@SuppressWarnings("hiding") IntStream input, int ttype, BitSet follow)
			throws RecognitionException {
		RecognitionException e = null;
		// if next token is what we are looking for then "delete" this token
		if (mismatchIsUnwantedToken(input, ttype)) {
			e = new UnwantedTokenException(ttype, input);
			/*
			 * System.err.println("recoverFromMismatchedToken deleting "+ ((TokenStream)input).LT(1)+ " since "
			 * +((TokenStream)input).LT(2)+" is what we want");
			 */
			beginResync();
			input.consume(); // simply delete extra token
			endResync();
			reportError(e); // report after consuming so AW sees the token in the exception
			// we want to return the token we're actually matching
			Object matchedSymbol = getCurrentInputSymbol(input);
			input.consume(); // move past ttype token as if all were ok
			return matchedSymbol;
		}
		// can't recover with single token deletion, try insertion
		if (mismatchIsMissingToken(input, follow)) {
			Object inserted = getMissingSymbol(input, e, ttype, follow);
			e = new MissingTokenException(ttype, input, inserted);
			reportError(e); // report after inserting so AW sees the token in the exception
			return null;
			// throw e;
		}
		// even that didn't work; must throw the exception
		e = new MismatchedTokenException(ttype, input);
		throw e;
	}

	/**
	 * Nothing to emit.
	 */
	@Override
	public void emitErrorMessage(String msg) {
		// don't call super, since it would do a plain vanilla
		// System.err.println(msg);
	}

	/**
	 * Extracted to reduce generated Antlr parser size.
	 */
	protected static short[][] unpackEncodedStringArray(String[] arr) {
		int numStates = arr.length;
		short[][] result = new short[numStates][];
		for (int i = 0; i < numStates; i++) {
			result[i] = DFA.unpackEncodedString(arr[i]);
		}
		return result;
	}
}
