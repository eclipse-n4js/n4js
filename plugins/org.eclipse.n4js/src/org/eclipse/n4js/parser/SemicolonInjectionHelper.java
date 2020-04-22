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
package org.eclipse.n4js.parser;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.eclipse.n4js.parser.antlr.internal.InternalN4JSParser;

/**
 * Small utility to handle ASI.
 */
public class SemicolonInjectionHelper {

	/**
	 * Callback interface that is implemented by a parser that wants to use the semicolon injection logic of this
	 * helper.
	 */
	public interface Callback {

		/**
		 * Allow to obtain the current internal parser state.
		 */
		RecognizerSharedState getState();

		/**
		 * Allow to obtain the current internal input tokens.
		 */
		TokenStream getInput();

		/**
		 * Obtain the follow set that is relevant for the ASI.
		 */
		BitSet getSemicolonFollowSet();

		/**
		 * Returns the token type for comma.
		 */
		default int getCommaBit() {
			throw new UnsupportedOperationException();
		}

		/**
		 * Returns the token recovery sets.
		 */
		default RecoverySets getRecoverySets() {
			throw new UnsupportedOperationException();
		}

		/**
		 * Simple helper for ASI recovery sets.
		 */
		public static class RecoverySets {
			private final long semicolonRecoverySetLeft;
			private final long semicolonRecoverySetRight;
			private final long semicolonRecoverySetWithComma;

			private RecoverySets(long[] array, int commaBit) {
				semicolonRecoverySetLeft = array[1];
				semicolonRecoverySetRight = array[2];
				semicolonRecoverySetWithComma = array[1] | (1L << commaBit);
			}

			boolean matches(BitSet bitSet) {
				long[] array = bitSet.toPackedArray();
				int len = array.length;
				if (len < 3 || array[0] != 0L) {
					return false;
				}
				long leftBits = array[1];
				long rightBits = array[2];
				if (rightBits == semicolonRecoverySetRight
						&& (leftBits == semicolonRecoverySetLeft || leftBits == semicolonRecoverySetWithComma)) {
					for (int i = 3; i < len; i++) {
						if (array[i] != 0L) {
							return false;
						}
					}
					return true;
				}
				return false;
			}
		}

		/**
		 * Computes the ASI recovery sets.
		 */
		default RecoverySets computeRecoverySets() {
			BitSet followSet = getSemicolonFollowSet();
			long[] array = followSet.toPackedArray();
			if (array.length != 3 || array[0] != 0L) {
				throw new RuntimeException("Internal token types changed. Need to rework ASI.");
			}
			int commaBit = getCommaBit() - Long.SIZE;
			if (commaBit < 0 || commaBit > Long.SIZE) {
				throw new RuntimeException("Internal token types changed. Need to rework ASI.");
			}
			RecoverySets result = new RecoverySets(array, commaBit);

			return result;
		}

		/**
		 * If the custom recovery logic fails, fall back to the default logic.
		 */
		void recoverBase(IntStream inputStream, RecognitionException re);

		/**
		 * Add an ASI message (optional).
		 */
		void addASIMessage();

		/**
		 * Discard a previously added error.
		 */
		void discardError();

		// /**
		// * Try to parse a TypeRefNoTrailingLineBreak.
		// */
		// void tryRuleTypeRefNoTrailingLineBreak() throws RecognitionException;

		/**
		 * Returns true if ASI is allowed for given exception. This is false in particular if ASI has already been
		 * applied at given position without success. Trying it again would then lead to an infinite loop.
		 * <p>
		 * Note that this methods updates the internal ASI recovery state. When this method is called twice with the
		 * same exception, the second time is must return false.
		 *
		 * TODO Review this scenario, test case is found in
		 * org.eclipse.n4js.tests.parser.ES_07_09_ASI_InfiniteLoopProblemTest.testInfiniteLoopBug()
		 */
		boolean allowASI(RecognitionException re);

	}

	/**
	 * Recover from an error found on the input stream. This is for {@link NoViableAltException} and
	 * {@link MismatchedTokenException}. If you enable single token insertion and deletion, this will usually not handle
	 * mismatched symbol exceptions but there could be a mismatched token that the
	 * {@link Parser#match(IntStream, int, BitSet) match} routine could not recover from.
	 */
	public static void recover(IntStream inputStream, RecognitionException re, Callback callback) {
		RecognizerSharedState state = callback.getState();
		if (re instanceof MismatchedTokenException) {
			// We expected a specific token
			// if that is not a semicolon, ASI is pointless, perform normal recovery
			int expecting = ((MismatchedTokenException) re).expecting;
			if (expecting != InternalN4JSParser.Semicolon) {

				callback.discardError(); // delete ASI message, a previously added ASI may fix too much! cf.
				// IDEBUG-215
				callback.recoverBase(inputStream, re);
				return;
			}
		}

		// System.out.println("Line: " + re.line + ":" + re.index);

		int unexpectedTokenType = re.token.getType();
		if (!followedBySemicolon(state, callback.getRecoverySets(), re.index)
				|| isOffendingToken(unexpectedTokenType)) {
			callback.recoverBase(inputStream, re);
		} else {
			int la = inputStream.LA(1);
			TokenStream casted = (TokenStream) inputStream;
			if (!isOffendingToken(la)) {
				// Start on the position before the current token and scan backwards off channel tokens until the
				// previous on channel token.
				for (int ix = re.token.getTokenIndex() - 1; ix > 0; ix--) {
					Token lt = casted.get(ix);
					if (lt.getChannel() == Token.DEFAULT_CHANNEL) {
						// On channel token found: stop scanning.
						callback.recoverBase(inputStream, re);
						return;
					} else if (lt.getType() == InternalN4JSParser.RULE_EOL) {
						// We found our EOL: everything's good, no need to do additional recovering
						// rule start.
						if (!callback.allowASI(re)) {
							callback.recoverBase(inputStream, re);
							return;
						}
						if (!findCommaBeforeEOL(casted, ix)) {
							callback.addASIMessage();
							return;
						}
					} else if (lt.getType() == InternalN4JSParser.RULE_ML_COMMENT) {
						String tokenText = lt.getText();
						if (!findCommaBeforeEOL(casted, ix)
								&& (tokenText.indexOf('\n', 2) >= 2 || tokenText.indexOf('\r', 2) >= 2)) {
							callback.addASIMessage();
							return;
						}
					}
				}
				callback.recoverBase(inputStream, re);
			}
		}
	}

	// /**
	// * Simulate a semantic predicate that is executed before any token LA was used. This way we can make sure that a
	// * variable declaration without a type declaration does not mess the node model.
	// */
	// public static boolean isTypeRefNoTrailingLineBreak(Callback callback) {
	// RecognizerSharedState state = callback.getState();
	// TokenStream input = callback.getInput();
	// state.backtracking++;
	// int start = input.mark();
	// try {
	// callback.tryRuleTypeRefNoTrailingLineBreak();
	// } catch (RecognitionException re) {
	// throw new RuntimeException("Cannot happen since we are backtracking right now.", re);
	// }
	// boolean success = !state.failed;
	// if (state.backtracking <= 1) {
	// // do not revert if we are still backtracking since subsequent predicate logic may depend on incremented
	// // token indizes
	// input.rewind(start);
	// }
	// state.backtracking--;
	// state.failed = false;
	// return success;
	// }

	/**
	 * Returns {@code true} if the set of expected follow-states includes an implicit or explicit semicolon.
	 */
	private static boolean followedBySemicolon(RecognizerSharedState state, Callback.RecoverySets recoverySets,
			int currentIndex) {
		int top = state._fsp;
		if (currentIndex != state.lastErrorIndex) {
			long[] array = state.following[top].toPackedArray();
			if (array.length == 1 && array[0] == (1L << Token.EOR_TOKEN_TYPE)) {
				return false;
			}
		}
		for (int i = top; i >= 0; i--) {
			BitSet localFollowSet = state.following[i];
			if (recoverySets.matches(localFollowSet)) {
				return true;
			}

		}
		return false;
	}

	/**
	 * <p>
	 * Promotes EOL which may lead to an automatically inserted semicolon. This is probably the most important method
	 * for automatic semicolon insertion, as it is only possible to insert a semicolon in case of line breaks (even if
	 * they are hidden in a multi-line comment!).
	 * </p>
	 */
	public static void promoteEOL(Callback callback) {
		RecognizerSharedState state = callback.getState();
		TokenStream input = callback.getInput();
		// Don't promote EOL if there was a syntax error at EOF
		if (state.lastErrorIndex == input.size()) {
			return;
		}
		// Get current token and its type (the possibly offending token).
		Token prev = input.LT(-1);
		Token next = input.LT(1);
		int la = next.getType();
		if (la == InternalN4JSParser.Semicolon) {
			return;
		}

		// Promoting an EOL means switching it from off channel to on channel.
		// A ML_COMMENT gets promoted when it contains an EOL.
		for (int idx = prev == null ? 0 : prev.getTokenIndex() + 1, max = la == Token.EOF ? input.size()
				: next.getTokenIndex(); idx < max; idx++) {
			Token lt = input.get(idx);
			if (lt.getChannel() == Token.DEFAULT_CHANNEL) {
				// On channel token found: stop scanning (previously promoted)
				break;
			} else if (isSemicolonEquivalent(lt)) {
				// We found our EOL: promote the token to on channel, position the input on it and reset the rule
				// start.
				lt.setChannel(Token.DEFAULT_CHANNEL);
				input.seek(idx);
				break;
			}
		}
	}

	/**
	 * Returns true if there was an unexpected EOL.
	 */
	public static boolean hasDisallowedEOL(Callback callback) {
		TokenStream input = callback.getInput();
		Token lt = input.LT(1);

		// Start on the position before the current token and scan backwards off channel tokens until the previous on
		// channel token.
		for (int ix = lt.getTokenIndex() - 1; ix > 0; ix--) {
			lt = input.get(ix);
			if (lt.getChannel() == Token.DEFAULT_CHANNEL) {
				// On channel token found: stop scanning.
				break;
			} else if (isSemicolonEquivalent(lt)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns {@code true} if the given look-ahead token type is considered to be an explicit or implicitly injected
	 * semicolon.
	 */
	public static boolean isOffendingToken(int la) {
		return la == InternalN4JSParser.Semicolon
				|| la == InternalN4JSParser.EOF
				|| la == InternalN4JSParser.RightCurlyBracket
				|| la == InternalN4JSParser.RULE_EOL
				|| la == InternalN4JSParser.RULE_ML_COMMENT;
	}

	/**
	 * A "," cannot be followed by an automatically inserted semicolon. This is in particular true in case of variable
	 * statements, in which the last declaration is ended with a comma (which might easily happen in case of copying the
	 * initializer from a list or object literal (cf. IDEBUG-214).
	 */
	private static boolean findCommaBeforeEOL(TokenStream casted, int startIndex) {
		for (int ix = startIndex - 1; ix > 0; ix--) {
			Token lt = casted.get(ix);
			if (lt.getType() == InternalN4JSParser.Comma) {
				// System.out.println("Found Comma, EOL is not valid");
				return true;
			}
			if (lt.getChannel() == Token.DEFAULT_CHANNEL) { // any other real char ends this search
				break;
			}
		}
		return false;
	}

	/**
	 * Returns true if the given token is considered to be semicolon equivalent.
	 */
	static boolean isSemicolonEquivalent(Token lt) {
		if (lt.getType() == InternalN4JSParser.RULE_EOL) {
			return true;
		}
		if (lt.getType() == InternalN4JSParser.RULE_ML_COMMENT) {
			String tokenText = lt.getText();
			for (int i = 2; i < tokenText.length() - 2; i++) {
				char c = tokenText.charAt(i);
				if (c == '\n' || c == '\r') {
					return true;
				}
			}
		}
		return false;
	}

}
