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
package org.eclipse.n4js.regex.ui.contentassist.antlr;

import com.google.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.antlr.runtime.RecognitionException;
import org.eclipse.n4js.regex.services.RegularExpressionGrammarAccess;
import org.eclipse.n4js.regex.ui.contentassist.antlr.internal.InternalRegularExpressionParser;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.AbstractContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;

public class RegularExpressionParser extends AbstractContentAssistParser {

	@Inject
	private RegularExpressionGrammarAccess grammarAccess;

	private Map<AbstractElement, String> nameMappings;

	@Override
	protected InternalRegularExpressionParser createParser() {
		InternalRegularExpressionParser result = new InternalRegularExpressionParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}

	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getDisjunctionAccess().getAlternatives(), "rule__Disjunction__Alternatives");
					put(grammarAccess.getTermAccess().getAlternatives(), "rule__Term__Alternatives");
					put(grammarAccess.getAssertionAccess().getAlternatives(), "rule__Assertion__Alternatives");
					put(grammarAccess.getWordBoundaryAccess().getAlternatives_1(), "rule__WordBoundary__Alternatives_1");
					put(grammarAccess.getLookAheadAccess().getAlternatives_3(), "rule__LookAhead__Alternatives_3");
					put(grammarAccess.getAtomAccess().getAlternatives(), "rule__Atom__Alternatives");
					put(grammarAccess.getPatternCharacterAccess().getValueAlternatives_0(), "rule__PatternCharacter__ValueAlternatives_0");
					put(grammarAccess.getAtomEscapeAccess().getAlternatives(), "rule__AtomEscape__Alternatives");
					put(grammarAccess.getCharacterClassAtomAccess().getAlternatives(), "rule__CharacterClassAtom__Alternatives");
					put(grammarAccess.getCharacterClassAtomAccess().getCharacterAlternatives_1_0(), "rule__CharacterClassAtom__CharacterAlternatives_1_0");
					put(grammarAccess.getEscapedCharacterClassAtomAccess().getAlternatives(), "rule__EscapedCharacterClassAtom__Alternatives");
					put(grammarAccess.getQuantifierAccess().getAlternatives(), "rule__Quantifier__Alternatives");
					put(grammarAccess.getSimpleQuantifierAccess().getQuantifierAlternatives_0_0(), "rule__SimpleQuantifier__QuantifierAlternatives_0_0");
					put(grammarAccess.getExactQuantifierAccess().getAlternatives_3(), "rule__ExactQuantifier__Alternatives_3");
					put(grammarAccess.getRegularExpressionFlagsAccess().getFlagsAlternatives_1_0(), "rule__RegularExpressionFlags__FlagsAlternatives_1_0");
					put(grammarAccess.getRegularExpressionLiteralAccess().getGroup(), "rule__RegularExpressionLiteral__Group__0");
					put(grammarAccess.getDisjunctionAccess().getGroup_0(), "rule__Disjunction__Group_0__0");
					put(grammarAccess.getDisjunctionAccess().getGroup_0_1(), "rule__Disjunction__Group_0_1__0");
					put(grammarAccess.getDisjunctionAccess().getGroup_0_1_1(), "rule__Disjunction__Group_0_1_1__0");
					put(grammarAccess.getDisjunctionAccess().getGroup_1(), "rule__Disjunction__Group_1__0");
					put(grammarAccess.getDisjunctionAccess().getGroup_1_1(), "rule__Disjunction__Group_1_1__0");
					put(grammarAccess.getAlternativeAccess().getGroup(), "rule__Alternative__Group__0");
					put(grammarAccess.getAlternativeAccess().getGroup_1(), "rule__Alternative__Group_1__0");
					put(grammarAccess.getTermAccess().getGroup_1(), "rule__Term__Group_1__0");
					put(grammarAccess.getLineStartAccess().getGroup(), "rule__LineStart__Group__0");
					put(grammarAccess.getLineEndAccess().getGroup(), "rule__LineEnd__Group__0");
					put(grammarAccess.getWordBoundaryAccess().getGroup(), "rule__WordBoundary__Group__0");
					put(grammarAccess.getLookAheadAccess().getGroup(), "rule__LookAhead__Group__0");
					put(grammarAccess.getWildcardAccess().getGroup(), "rule__Wildcard__Group__0");
					put(grammarAccess.getCharacterClassAccess().getGroup(), "rule__CharacterClass__Group__0");
					put(grammarAccess.getCharacterClassAccess().getGroup_2(), "rule__CharacterClass__Group_2__0");
					put(grammarAccess.getCharacterClassElementAccess().getGroup(), "rule__CharacterClassElement__Group__0");
					put(grammarAccess.getCharacterClassElementAccess().getGroup_1(), "rule__CharacterClassElement__Group_1__0");
					put(grammarAccess.getCharacterClassElementAccess().getGroup_1_0(), "rule__CharacterClassElement__Group_1_0__0");
					put(grammarAccess.getBackspaceAccess().getGroup(), "rule__Backspace__Group__0");
					put(grammarAccess.getGroupAccess().getGroup(), "rule__Group__Group__0");
					put(grammarAccess.getGroupAccess().getGroup_2(), "rule__Group__Group_2__0");
					put(grammarAccess.getSimpleQuantifierAccess().getGroup(), "rule__SimpleQuantifier__Group__0");
					put(grammarAccess.getExactQuantifierAccess().getGroup(), "rule__ExactQuantifier__Group__0");
					put(grammarAccess.getExactQuantifierAccess().getGroup_3_0(), "rule__ExactQuantifier__Group_3_0__0");
					put(grammarAccess.getRegularExpressionFlagsAccess().getGroup(), "rule__RegularExpressionFlags__Group__0");
					put(grammarAccess.getRegularExpressionLiteralAccess().getBodyAssignment_1(), "rule__RegularExpressionLiteral__BodyAssignment_1");
					put(grammarAccess.getRegularExpressionLiteralAccess().getFlagsAssignment_3(), "rule__RegularExpressionLiteral__FlagsAssignment_3");
					put(grammarAccess.getRegularExpressionBodyAccess().getPatternAssignment(), "rule__RegularExpressionBody__PatternAssignment");
					put(grammarAccess.getDisjunctionAccess().getElementsAssignment_0_1_1_1(), "rule__Disjunction__ElementsAssignment_0_1_1_1");
					put(grammarAccess.getDisjunctionAccess().getElementsAssignment_1_1_1(), "rule__Disjunction__ElementsAssignment_1_1_1");
					put(grammarAccess.getAlternativeAccess().getElementsAssignment_1_1(), "rule__Alternative__ElementsAssignment_1_1");
					put(grammarAccess.getTermAccess().getQuantifierAssignment_1_1(), "rule__Term__QuantifierAssignment_1_1");
					put(grammarAccess.getWordBoundaryAccess().getNotAssignment_1_1(), "rule__WordBoundary__NotAssignment_1_1");
					put(grammarAccess.getLookAheadAccess().getNotAssignment_3_1(), "rule__LookAhead__NotAssignment_3_1");
					put(grammarAccess.getLookAheadAccess().getPatternAssignment_4(), "rule__LookAhead__PatternAssignment_4");
					put(grammarAccess.getPatternCharacterAccess().getValueAssignment(), "rule__PatternCharacter__ValueAssignment");
					put(grammarAccess.getCharacterClassEscapeSequenceAccess().getSequenceAssignment(), "rule__CharacterClassEscapeSequence__SequenceAssignment");
					put(grammarAccess.getCharacterEscapeSequenceAccess().getSequenceAssignment(), "rule__CharacterEscapeSequence__SequenceAssignment");
					put(grammarAccess.getControlLetterEscapeSequenceAccess().getSequenceAssignment(), "rule__ControlLetterEscapeSequence__SequenceAssignment");
					put(grammarAccess.getHexEscapeSequenceAccess().getSequenceAssignment(), "rule__HexEscapeSequence__SequenceAssignment");
					put(grammarAccess.getUnicodeEscapeSequenceAccess().getSequenceAssignment(), "rule__UnicodeEscapeSequence__SequenceAssignment");
					put(grammarAccess.getIdentityEscapeSequenceAccess().getSequenceAssignment(), "rule__IdentityEscapeSequence__SequenceAssignment");
					put(grammarAccess.getDecimalEscapeSequenceAccess().getSequenceAssignment(), "rule__DecimalEscapeSequence__SequenceAssignment");
					put(grammarAccess.getCharacterClassAccess().getNegatedAssignment_2_0(), "rule__CharacterClass__NegatedAssignment_2_0");
					put(grammarAccess.getCharacterClassAccess().getElementsAssignment_3(), "rule__CharacterClass__ElementsAssignment_3");
					put(grammarAccess.getCharacterClassElementAccess().getRightAssignment_1_0_2(), "rule__CharacterClassElement__RightAssignment_1_0_2");
					put(grammarAccess.getCharacterClassAtomAccess().getCharacterAssignment_1(), "rule__CharacterClassAtom__CharacterAssignment_1");
					put(grammarAccess.getGroupAccess().getNonCapturingAssignment_2_0(), "rule__Group__NonCapturingAssignment_2_0");
					put(grammarAccess.getGroupAccess().getPatternAssignment_3(), "rule__Group__PatternAssignment_3");
					put(grammarAccess.getSimpleQuantifierAccess().getQuantifierAssignment_0(), "rule__SimpleQuantifier__QuantifierAssignment_0");
					put(grammarAccess.getSimpleQuantifierAccess().getNonGreedyAssignment_1(), "rule__SimpleQuantifier__NonGreedyAssignment_1");
					put(grammarAccess.getExactQuantifierAccess().getMinAssignment_2(), "rule__ExactQuantifier__MinAssignment_2");
					put(grammarAccess.getExactQuantifierAccess().getMaxAssignment_3_0_1(), "rule__ExactQuantifier__MaxAssignment_3_0_1");
					put(grammarAccess.getExactQuantifierAccess().getUnboundedMaxAssignment_3_1(), "rule__ExactQuantifier__UnboundedMaxAssignment_3_1");
					put(grammarAccess.getExactQuantifierAccess().getNonGreedyAssignment_5(), "rule__ExactQuantifier__NonGreedyAssignment_5");
					put(grammarAccess.getRegularExpressionFlagsAccess().getFlagsAssignment_1(), "rule__RegularExpressionFlags__FlagsAssignment_1");
				}
			};
		}
		return nameMappings.get(element);
	}

	@Override
	protected Collection<FollowElement> getFollowElements(AbstractInternalContentAssistParser parser) {
		try {
			InternalRegularExpressionParser typedParser = (InternalRegularExpressionParser) parser;
			typedParser.entryRuleRegularExpressionLiteral();
			return typedParser.getFollowElements();
		} catch(RecognitionException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] {  };
	}

	public RegularExpressionGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(RegularExpressionGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
