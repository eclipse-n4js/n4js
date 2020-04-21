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
package org.eclipse.n4js.ide.editor.contentassist

import org.eclipse.xtext.ParserRule
import org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElementCalculator

/**
 * We subclass {@link FollowElementCalculator} here in order to filter out any follow elements that would be added
 * as a result of a rule call to any rule whose name begins with "Bogus". This is necessary because any proposal
 * that is derived from such a follow element is bogus itself and should not be shown to the user. Unfortunately, it
 * is impossible to trace back whether or not a proposal was based on a bogus grammar element, so we have to do it
 * here instead of later on in the proposal providers.
 */
class N4JSFollowElementCalculator extends FollowElementCalculator {

	override caseParserRule(ParserRule parserRule) {
		if (!parserRule.getName().startsWith("Bogus"))
			return super.caseParserRule(parserRule);
		return Boolean.FALSE;
	}
}
