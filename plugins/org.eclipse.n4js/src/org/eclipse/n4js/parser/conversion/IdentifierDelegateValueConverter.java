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
package org.eclipse.n4js.parser.conversion;

import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.conversion.impl.KeywordAlternativeConverter;

import com.google.common.collect.ImmutableSet.Builder;

/**
 */
public class IdentifierDelegateValueConverter extends KeywordAlternativeConverter {

	@Override
	protected void processElement(AbstractElement element, AbstractRule rule, Builder<String> result) {
		if (element instanceof RuleCall) {
			AbstractRule calledRule = ((RuleCall) element).getRule();
			if (calledRule instanceof TerminalRule) {
				super.processElement(element, rule, result);
			} else {
				processElement(calledRule.getAlternatives(), rule, result);
			}
		} else {
			super.processElement(element, rule, result);
		}
	}

}
