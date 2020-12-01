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
package org.eclipse.n4js.ts.conversions;

import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;

/**
 * This value converter converts computed-names, for example {@code [System.iterator]} and {@code ["@type"]}, on the way
 * from the parser to the AST (see also Design document, Section 9.3)
 *
 * <p>
 * In ECMAScript 6, members can not only be defined by an ordinary, string-based identifier, but also by a so-called
 * symbol. N4JS provides support for this, but only to cover the single, special use case of \type{Iterator}s and their
 * application in <code>for..of</code> loops.
 * <p>
 * To keep special handling of such members to a minimum we internally create a string-based identifier here as well: If
 * member m is identified by built-in symbol s with name name_s we use "#" + name_s as m's name internally.
 * <p>
 * For example, ECMAScript 6 provides the built-in symbol "iterator" that can be referenced by
 * <code>Symbol.iterator</code>. If this symbol is used as a member identifier, then we will internally use "#iterator"
 * as this member's name. Note that this approach only works for built-in symbols, not local or shared symbols (but the
 * latter two forms of symbols are not yet supported by N4JS anyway).
 * <p>
 * For more details see grammar rules
 * <ul>
 * <li><code>N4JSPropertyComputedName</code></li>
 * <li><code>JsPropertyComputedName</code></li>
 * <li><code>TypesComputedPropertyName</code></li>
 * </ul>
 * and the sections related to symbols, iterators, and the <code>for..of</code> loop in the N4JS Specification and
 * Section 9.3 in the Design document.
 */
public class ComputedPropertyNameValueConverter extends IdentifierDelegateValueConverter {

	/**
	 * Prefix used in names of members that are identified by a built-in symbol.
	 * <p>
	 * Take this example:
	 *
	 * <pre>
	 * class C&lt;T> {
	 *     Iterator&lt;T> [Symbol.iterator]() {
	 *         // ...
	 *     }
	 * }
	 * </pre>
	 *
	 * Here, class C has a single method identified by built-in symbol 'iterator'. Internally, this will be represented
	 * by a method with name {@link #SYMBOL_IDENTIFIER_PREFIX} + "iterator".
	 */
	public static final String SYMBOL_IDENTIFIER_PREFIX = "#";

	/**
	 * Internally special-casing [Symbol.iterator] as a member named hash-iterator.
	 */
	public static final String SYMBOL_ITERATOR_MANGLED = SYMBOL_IDENTIFIER_PREFIX + "iterator";

	/**
	 * Internally special-casing [Symbol.asyncIterator] as a member named hash-iterator.
	 */
	public static final String SYMBOL_ASYNC_ITERATOR_MANGLED = SYMBOL_IDENTIFIER_PREFIX + "asyncIterator";

	/**
	 * This method handles computed-names, for example {@code [System.iterator]} and {@code ["@type"]}.
	 */
	@Override
	public String toValue(String string, INode node) throws ValueConverterException {
		if (string != null && string.length() > 0 && string.charAt(0) == '[') {
			// rules arriving here:
			// (1) N4JSPropertyComputedName
			// (2) JsPropertyComputedName
			// (3) TypesComputedPropertyName
			// argument 'string' matches '[' (SymbolLiteralComputedName | StringLiteralComputedName) ']'
			String payload = string.substring(1, string.length() - 1).trim();
			// payload matches what's enclosed by square brackets (but devoid of whitespace and comments)
			if (payload.charAt(0) == '"' || payload.charAt(0) == '\'') {
				String literalWoQuotes = payload.substring(1, payload.length() - 1);
				return literalWoQuotes;
			} else {
				// argument 'string' matches "[<ident-1>.<ident-2>]"
				// convert it to "#<ident-2>"
				return SYMBOL_IDENTIFIER_PREFIX + string.substring(string.indexOf('.') + 1, string.length() - 1);
			}
		}
		return super.toValue(string, node);
	}

	/**
	 * The method being overridden expects a rule of a certain form (body does not contain an alternative of keywords
	 * and a single rule) but the rules arriving here don't have this format.
	 * <p>
	 * Therefore pass to that method the first one of the two nested rules Some-Identifier that are expected in any of
	 * the rules arriving here.
	 *
	 * For more details see grammar rules
	 * <ul>
	 * <li><code>N4JSPropertyComputedName</code></li>
	 * <li><code>JsPropertyComputedName</code></li>
	 * <li><code>TypesComputedPropertyName</code></li>
	 * </ul>
	 */
	@Override
	public void setRule(AbstractRule rule) {
		// TODO make this safer / throw exceptions more informative exceptions
		// rule: '[' (SymbolLiteralComputedName | StringLiteralComputedName) ']'
		Group g = (Group) rule.getAlternatives();
		Alternatives elem = (Alternatives) g.getElements().get(1);
		// SymbolLiteralComputedName: N4JSIdentifier '.' N4JSIdentifier
		AbstractRule symLitCompName = ((RuleCall) elem.getElements().get(0)).getRule();
		Group g2 = (Group) symLitCompName.getAlternatives();
		RuleCall identCall = (RuleCall) g2.getElements().get(0);
		AbstractRule ident = identCall.getRule();
		super.setRule(ident);
	}
}
