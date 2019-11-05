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
parser grammar InternalRegularExpressionParser;

options {
	tokenVocab=InternalRegularExpressionLexer;
	superClass=AbstractInternalContentAssistParser;
	backtrack=true;
}

@header {
package org.eclipse.n4js.regex.ide.contentassist.antlr.internal;
import java.util.Map;
import java.util.HashMap;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import org.eclipse.n4js.regex.services.RegularExpressionGrammarAccess;

}
@members {
	private RegularExpressionGrammarAccess grammarAccess;
	private final Map<String, String> tokenNameToValue = new HashMap<String, String>();
	
	{
		tokenNameToValue.put("ExclamationMark", "'!'");
		tokenNameToValue.put("DollarSign", "'\\u0024'");
		tokenNameToValue.put("LeftParenthesis", "'('");
		tokenNameToValue.put("RightParenthesis", "')'");
		tokenNameToValue.put("Asterisk", "'*'");
		tokenNameToValue.put("PlusSign", "'+'");
		tokenNameToValue.put("Comma", "','");
		tokenNameToValue.put("HyphenMinus", "'-'");
		tokenNameToValue.put("FullStop", "'.'");
		tokenNameToValue.put("Solidus", "'/'");
		tokenNameToValue.put("Colon", "':'");
		tokenNameToValue.put("EqualsSign", "'='");
		tokenNameToValue.put("QuestionMark", "'?'");
		tokenNameToValue.put("LeftSquareBracket", "'['");
		tokenNameToValue.put("RightSquareBracket", "']'");
		tokenNameToValue.put("CircumflexAccent", "'^'");
		tokenNameToValue.put("LeftCurlyBracket", "'{'");
		tokenNameToValue.put("VerticalLine", "'|'");
		tokenNameToValue.put("RightCurlyBracket", "'}'");
	}

	public void setGrammarAccess(RegularExpressionGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}

	@Override
	protected Grammar getGrammar() {
		return grammarAccess.getGrammar();
	}

	@Override
	protected String getValueForTokenName(String tokenName) {
		String result = tokenNameToValue.get(tokenName);
		if (result == null)
			result = tokenName;
		return result;
	}
}

// Entry rule entryRuleRegularExpressionLiteral
entryRuleRegularExpressionLiteral
:
{ before(grammarAccess.getRegularExpressionLiteralRule()); }
	 ruleRegularExpressionLiteral
{ after(grammarAccess.getRegularExpressionLiteralRule()); } 
	 EOF 
;

// Rule RegularExpressionLiteral
ruleRegularExpressionLiteral 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getRegularExpressionLiteralAccess().getGroup()); }
		(rule__RegularExpressionLiteral__Group__0)
		{ after(grammarAccess.getRegularExpressionLiteralAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleRegularExpressionBody
entryRuleRegularExpressionBody
:
{ before(grammarAccess.getRegularExpressionBodyRule()); }
	 ruleRegularExpressionBody
{ after(grammarAccess.getRegularExpressionBodyRule()); } 
	 EOF 
;

// Rule RegularExpressionBody
ruleRegularExpressionBody 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getRegularExpressionBodyAccess().getPatternAssignment()); }
		(rule__RegularExpressionBody__PatternAssignment)
		{ after(grammarAccess.getRegularExpressionBodyAccess().getPatternAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleDisjunction
entryRuleDisjunction
:
{ before(grammarAccess.getDisjunctionRule()); }
	 ruleDisjunction
{ after(grammarAccess.getDisjunctionRule()); } 
	 EOF 
;

// Rule Disjunction
ruleDisjunction 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getDisjunctionAccess().getAlternatives()); }
		(rule__Disjunction__Alternatives)
		{ after(grammarAccess.getDisjunctionAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleAlternative
entryRuleAlternative
:
{ before(grammarAccess.getAlternativeRule()); }
	 ruleAlternative
{ after(grammarAccess.getAlternativeRule()); } 
	 EOF 
;

// Rule Alternative
ruleAlternative 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getAlternativeAccess().getGroup()); }
		(rule__Alternative__Group__0)
		{ after(grammarAccess.getAlternativeAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTerm
entryRuleTerm
:
{ before(grammarAccess.getTermRule()); }
	 ruleTerm
{ after(grammarAccess.getTermRule()); } 
	 EOF 
;

// Rule Term
ruleTerm 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTermAccess().getAlternatives()); }
		(rule__Term__Alternatives)
		{ after(grammarAccess.getTermAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleAssertion
entryRuleAssertion
:
{ before(grammarAccess.getAssertionRule()); }
	 ruleAssertion
{ after(grammarAccess.getAssertionRule()); } 
	 EOF 
;

// Rule Assertion
ruleAssertion 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getAssertionAccess().getAlternatives()); }
		(rule__Assertion__Alternatives)
		{ after(grammarAccess.getAssertionAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleLineStart
entryRuleLineStart
:
{ before(grammarAccess.getLineStartRule()); }
	 ruleLineStart
{ after(grammarAccess.getLineStartRule()); } 
	 EOF 
;

// Rule LineStart
ruleLineStart 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getLineStartAccess().getGroup()); }
		(rule__LineStart__Group__0)
		{ after(grammarAccess.getLineStartAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleLineEnd
entryRuleLineEnd
:
{ before(grammarAccess.getLineEndRule()); }
	 ruleLineEnd
{ after(grammarAccess.getLineEndRule()); } 
	 EOF 
;

// Rule LineEnd
ruleLineEnd 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getLineEndAccess().getGroup()); }
		(rule__LineEnd__Group__0)
		{ after(grammarAccess.getLineEndAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleWordBoundary
entryRuleWordBoundary
:
{ before(grammarAccess.getWordBoundaryRule()); }
	 ruleWordBoundary
{ after(grammarAccess.getWordBoundaryRule()); } 
	 EOF 
;

// Rule WordBoundary
ruleWordBoundary 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getWordBoundaryAccess().getGroup()); }
		(rule__WordBoundary__Group__0)
		{ after(grammarAccess.getWordBoundaryAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleLookAhead
entryRuleLookAhead
:
{ before(grammarAccess.getLookAheadRule()); }
	 ruleLookAhead
{ after(grammarAccess.getLookAheadRule()); } 
	 EOF 
;

// Rule LookAhead
ruleLookAhead 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getLookAheadAccess().getGroup()); }
		(rule__LookAhead__Group__0)
		{ after(grammarAccess.getLookAheadAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleAtom
entryRuleAtom
:
{ before(grammarAccess.getAtomRule()); }
	 ruleAtom
{ after(grammarAccess.getAtomRule()); } 
	 EOF 
;

// Rule Atom
ruleAtom 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getAtomAccess().getAlternatives()); }
		(rule__Atom__Alternatives)
		{ after(grammarAccess.getAtomAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRulePatternCharacter
entryRulePatternCharacter
:
{ before(grammarAccess.getPatternCharacterRule()); }
	 rulePatternCharacter
{ after(grammarAccess.getPatternCharacterRule()); } 
	 EOF 
;

// Rule PatternCharacter
rulePatternCharacter 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getPatternCharacterAccess().getValueAssignment()); }
		(rule__PatternCharacter__ValueAssignment)
		{ after(grammarAccess.getPatternCharacterAccess().getValueAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleWildcard
entryRuleWildcard
:
{ before(grammarAccess.getWildcardRule()); }
	 ruleWildcard
{ after(grammarAccess.getWildcardRule()); } 
	 EOF 
;

// Rule Wildcard
ruleWildcard 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getWildcardAccess().getGroup()); }
		(rule__Wildcard__Group__0)
		{ after(grammarAccess.getWildcardAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleAtomEscape
entryRuleAtomEscape
:
{ before(grammarAccess.getAtomEscapeRule()); }
	 ruleAtomEscape
{ after(grammarAccess.getAtomEscapeRule()); } 
	 EOF 
;

// Rule AtomEscape
ruleAtomEscape 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getAtomEscapeAccess().getAlternatives()); }
		(rule__AtomEscape__Alternatives)
		{ after(grammarAccess.getAtomEscapeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleCharacterClassEscapeSequence
entryRuleCharacterClassEscapeSequence
:
{ before(grammarAccess.getCharacterClassEscapeSequenceRule()); }
	 ruleCharacterClassEscapeSequence
{ after(grammarAccess.getCharacterClassEscapeSequenceRule()); } 
	 EOF 
;

// Rule CharacterClassEscapeSequence
ruleCharacterClassEscapeSequence 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getCharacterClassEscapeSequenceAccess().getSequenceAssignment()); }
		(rule__CharacterClassEscapeSequence__SequenceAssignment)
		{ after(grammarAccess.getCharacterClassEscapeSequenceAccess().getSequenceAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleCharacterEscapeSequence
entryRuleCharacterEscapeSequence
:
{ before(grammarAccess.getCharacterEscapeSequenceRule()); }
	 ruleCharacterEscapeSequence
{ after(grammarAccess.getCharacterEscapeSequenceRule()); } 
	 EOF 
;

// Rule CharacterEscapeSequence
ruleCharacterEscapeSequence 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getCharacterEscapeSequenceAccess().getSequenceAssignment()); }
		(rule__CharacterEscapeSequence__SequenceAssignment)
		{ after(grammarAccess.getCharacterEscapeSequenceAccess().getSequenceAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleControlLetterEscapeSequence
entryRuleControlLetterEscapeSequence
:
{ before(grammarAccess.getControlLetterEscapeSequenceRule()); }
	 ruleControlLetterEscapeSequence
{ after(grammarAccess.getControlLetterEscapeSequenceRule()); } 
	 EOF 
;

// Rule ControlLetterEscapeSequence
ruleControlLetterEscapeSequence 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getControlLetterEscapeSequenceAccess().getSequenceAssignment()); }
		(rule__ControlLetterEscapeSequence__SequenceAssignment)
		{ after(grammarAccess.getControlLetterEscapeSequenceAccess().getSequenceAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleHexEscapeSequence
entryRuleHexEscapeSequence
:
{ before(grammarAccess.getHexEscapeSequenceRule()); }
	 ruleHexEscapeSequence
{ after(grammarAccess.getHexEscapeSequenceRule()); } 
	 EOF 
;

// Rule HexEscapeSequence
ruleHexEscapeSequence 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getHexEscapeSequenceAccess().getSequenceAssignment()); }
		(rule__HexEscapeSequence__SequenceAssignment)
		{ after(grammarAccess.getHexEscapeSequenceAccess().getSequenceAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleUnicodeEscapeSequence
entryRuleUnicodeEscapeSequence
:
{ before(grammarAccess.getUnicodeEscapeSequenceRule()); }
	 ruleUnicodeEscapeSequence
{ after(grammarAccess.getUnicodeEscapeSequenceRule()); } 
	 EOF 
;

// Rule UnicodeEscapeSequence
ruleUnicodeEscapeSequence 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getUnicodeEscapeSequenceAccess().getSequenceAssignment()); }
		(rule__UnicodeEscapeSequence__SequenceAssignment)
		{ after(grammarAccess.getUnicodeEscapeSequenceAccess().getSequenceAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleIdentityEscapeSequence
entryRuleIdentityEscapeSequence
:
{ before(grammarAccess.getIdentityEscapeSequenceRule()); }
	 ruleIdentityEscapeSequence
{ after(grammarAccess.getIdentityEscapeSequenceRule()); } 
	 EOF 
;

// Rule IdentityEscapeSequence
ruleIdentityEscapeSequence 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getIdentityEscapeSequenceAccess().getSequenceAssignment()); }
		(rule__IdentityEscapeSequence__SequenceAssignment)
		{ after(grammarAccess.getIdentityEscapeSequenceAccess().getSequenceAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleDecimalEscapeSequence
entryRuleDecimalEscapeSequence
:
{ before(grammarAccess.getDecimalEscapeSequenceRule()); }
	 ruleDecimalEscapeSequence
{ after(grammarAccess.getDecimalEscapeSequenceRule()); } 
	 EOF 
;

// Rule DecimalEscapeSequence
ruleDecimalEscapeSequence 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getDecimalEscapeSequenceAccess().getSequenceAssignment()); }
		(rule__DecimalEscapeSequence__SequenceAssignment)
		{ after(grammarAccess.getDecimalEscapeSequenceAccess().getSequenceAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleCharacterClass
entryRuleCharacterClass
:
{ before(grammarAccess.getCharacterClassRule()); }
	 ruleCharacterClass
{ after(grammarAccess.getCharacterClassRule()); } 
	 EOF 
;

// Rule CharacterClass
ruleCharacterClass 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getCharacterClassAccess().getGroup()); }
		(rule__CharacterClass__Group__0)
		{ after(grammarAccess.getCharacterClassAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleCharacterClassElement
entryRuleCharacterClassElement
:
{ before(grammarAccess.getCharacterClassElementRule()); }
	 ruleCharacterClassElement
{ after(grammarAccess.getCharacterClassElementRule()); } 
	 EOF 
;

// Rule CharacterClassElement
ruleCharacterClassElement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getCharacterClassElementAccess().getGroup()); }
		(rule__CharacterClassElement__Group__0)
		{ after(grammarAccess.getCharacterClassElementAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleCharacterClassAtom
entryRuleCharacterClassAtom
:
{ before(grammarAccess.getCharacterClassAtomRule()); }
	 ruleCharacterClassAtom
{ after(grammarAccess.getCharacterClassAtomRule()); } 
	 EOF 
;

// Rule CharacterClassAtom
ruleCharacterClassAtom 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getAlternatives()); }
		(rule__CharacterClassAtom__Alternatives)
		{ after(grammarAccess.getCharacterClassAtomAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleEscapedCharacterClassAtom
entryRuleEscapedCharacterClassAtom
:
{ before(grammarAccess.getEscapedCharacterClassAtomRule()); }
	 ruleEscapedCharacterClassAtom
{ after(grammarAccess.getEscapedCharacterClassAtomRule()); } 
	 EOF 
;

// Rule EscapedCharacterClassAtom
ruleEscapedCharacterClassAtom 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getEscapedCharacterClassAtomAccess().getAlternatives()); }
		(rule__EscapedCharacterClassAtom__Alternatives)
		{ after(grammarAccess.getEscapedCharacterClassAtomAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleBackspace
entryRuleBackspace
:
{ before(grammarAccess.getBackspaceRule()); }
	 ruleBackspace
{ after(grammarAccess.getBackspaceRule()); } 
	 EOF 
;

// Rule Backspace
ruleBackspace 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getBackspaceAccess().getGroup()); }
		(rule__Backspace__Group__0)
		{ after(grammarAccess.getBackspaceAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleGroup
entryRuleGroup
:
{ before(grammarAccess.getGroupRule()); }
	 ruleGroup
{ after(grammarAccess.getGroupRule()); } 
	 EOF 
;

// Rule Group
ruleGroup 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getGroupAccess().getGroup()); }
		(rule__Group__Group__0)
		{ after(grammarAccess.getGroupAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleQuantifier
entryRuleQuantifier
:
{ before(grammarAccess.getQuantifierRule()); }
	 ruleQuantifier
{ after(grammarAccess.getQuantifierRule()); } 
	 EOF 
;

// Rule Quantifier
ruleQuantifier 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getQuantifierAccess().getAlternatives()); }
		(rule__Quantifier__Alternatives)
		{ after(grammarAccess.getQuantifierAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSimpleQuantifier
entryRuleSimpleQuantifier
:
{ before(grammarAccess.getSimpleQuantifierRule()); }
	 ruleSimpleQuantifier
{ after(grammarAccess.getSimpleQuantifierRule()); } 
	 EOF 
;

// Rule SimpleQuantifier
ruleSimpleQuantifier 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSimpleQuantifierAccess().getGroup()); }
		(rule__SimpleQuantifier__Group__0)
		{ after(grammarAccess.getSimpleQuantifierAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleExactQuantifier
entryRuleExactQuantifier
:
{ before(grammarAccess.getExactQuantifierRule()); }
	 ruleExactQuantifier
{ after(grammarAccess.getExactQuantifierRule()); } 
	 EOF 
;

// Rule ExactQuantifier
ruleExactQuantifier 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getExactQuantifierAccess().getGroup()); }
		(rule__ExactQuantifier__Group__0)
		{ after(grammarAccess.getExactQuantifierAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleRegularExpressionFlags
entryRuleRegularExpressionFlags
:
{ before(grammarAccess.getRegularExpressionFlagsRule()); }
	 ruleRegularExpressionFlags
{ after(grammarAccess.getRegularExpressionFlagsRule()); } 
	 EOF 
;

// Rule RegularExpressionFlags
ruleRegularExpressionFlags 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getRegularExpressionFlagsAccess().getGroup()); }
		(rule__RegularExpressionFlags__Group__0)
		{ after(grammarAccess.getRegularExpressionFlagsAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleINT
entryRuleINT
:
{ before(grammarAccess.getINTRule()); }
	 ruleINT
{ after(grammarAccess.getINTRule()); } 
	 EOF 
;

// Rule INT
ruleINT 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		(
			{ before(grammarAccess.getINTAccess().getUNICODE_DIGITTerminalRuleCall()); }
			(RULE_UNICODE_DIGIT)
			{ after(grammarAccess.getINTAccess().getUNICODE_DIGITTerminalRuleCall()); }
		)
		(
			{ before(grammarAccess.getINTAccess().getUNICODE_DIGITTerminalRuleCall()); }
			(RULE_UNICODE_DIGIT)*
			{ after(grammarAccess.getINTAccess().getUNICODE_DIGITTerminalRuleCall()); }
		)
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Disjunction__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getDisjunctionAccess().getGroup_0()); }
		(rule__Disjunction__Group_0__0)
		{ after(grammarAccess.getDisjunctionAccess().getGroup_0()); }
	)
	|
	(
		{ before(grammarAccess.getDisjunctionAccess().getGroup_1()); }
		(rule__Disjunction__Group_1__0)
		{ after(grammarAccess.getDisjunctionAccess().getGroup_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Term__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTermAccess().getAssertionParserRuleCall_0()); }
		ruleAssertion
		{ after(grammarAccess.getTermAccess().getAssertionParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getTermAccess().getGroup_1()); }
		(rule__Term__Group_1__0)
		{ after(grammarAccess.getTermAccess().getGroup_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Assertion__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAssertionAccess().getLineStartParserRuleCall_0()); }
		ruleLineStart
		{ after(grammarAccess.getAssertionAccess().getLineStartParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getAssertionAccess().getLineEndParserRuleCall_1()); }
		ruleLineEnd
		{ after(grammarAccess.getAssertionAccess().getLineEndParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getAssertionAccess().getWordBoundaryParserRuleCall_2()); }
		ruleWordBoundary
		{ after(grammarAccess.getAssertionAccess().getWordBoundaryParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getAssertionAccess().getLookAheadParserRuleCall_3()); }
		ruleLookAhead
		{ after(grammarAccess.getAssertionAccess().getLookAheadParserRuleCall_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__WordBoundary__Alternatives_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getWordBoundaryAccess().getWORD_BOUNDARYTerminalRuleCall_1_0()); }
		RULE_WORD_BOUNDARY
		{ after(grammarAccess.getWordBoundaryAccess().getWORD_BOUNDARYTerminalRuleCall_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getWordBoundaryAccess().getNotAssignment_1_1()); }
		(rule__WordBoundary__NotAssignment_1_1)
		{ after(grammarAccess.getWordBoundaryAccess().getNotAssignment_1_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__LookAhead__Alternatives_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getLookAheadAccess().getEqualsSignKeyword_3_0()); }
		EqualsSign
		{ after(grammarAccess.getLookAheadAccess().getEqualsSignKeyword_3_0()); }
	)
	|
	(
		{ before(grammarAccess.getLookAheadAccess().getNotAssignment_3_1()); }
		(rule__LookAhead__NotAssignment_3_1)
		{ after(grammarAccess.getLookAheadAccess().getNotAssignment_3_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Atom__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAtomAccess().getPatternCharacterParserRuleCall_0()); }
		rulePatternCharacter
		{ after(grammarAccess.getAtomAccess().getPatternCharacterParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getAtomAccess().getWildcardParserRuleCall_1()); }
		ruleWildcard
		{ after(grammarAccess.getAtomAccess().getWildcardParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getAtomAccess().getAtomEscapeParserRuleCall_2()); }
		ruleAtomEscape
		{ after(grammarAccess.getAtomAccess().getAtomEscapeParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getAtomAccess().getCharacterClassParserRuleCall_3()); }
		ruleCharacterClass
		{ after(grammarAccess.getAtomAccess().getCharacterClassParserRuleCall_3()); }
	)
	|
	(
		{ before(grammarAccess.getAtomAccess().getGroupParserRuleCall_4()); }
		ruleGroup
		{ after(grammarAccess.getAtomAccess().getGroupParserRuleCall_4()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__PatternCharacter__ValueAlternatives_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPatternCharacterAccess().getValuePATTERN_CHARACTER_NO_DASHTerminalRuleCall_0_0()); }
		RULE_PATTERN_CHARACTER_NO_DASH
		{ after(grammarAccess.getPatternCharacterAccess().getValuePATTERN_CHARACTER_NO_DASHTerminalRuleCall_0_0()); }
	)
	|
	(
		{ before(grammarAccess.getPatternCharacterAccess().getValueUNICODE_LETTERTerminalRuleCall_0_1()); }
		RULE_UNICODE_LETTER
		{ after(grammarAccess.getPatternCharacterAccess().getValueUNICODE_LETTERTerminalRuleCall_0_1()); }
	)
	|
	(
		{ before(grammarAccess.getPatternCharacterAccess().getValueUNICODE_DIGITTerminalRuleCall_0_2()); }
		RULE_UNICODE_DIGIT
		{ after(grammarAccess.getPatternCharacterAccess().getValueUNICODE_DIGITTerminalRuleCall_0_2()); }
	)
	|
	(
		{ before(grammarAccess.getPatternCharacterAccess().getValueHyphenMinusKeyword_0_3()); }
		HyphenMinus
		{ after(grammarAccess.getPatternCharacterAccess().getValueHyphenMinusKeyword_0_3()); }
	)
	|
	(
		{ before(grammarAccess.getPatternCharacterAccess().getValueCommaKeyword_0_4()); }
		Comma
		{ after(grammarAccess.getPatternCharacterAccess().getValueCommaKeyword_0_4()); }
	)
	|
	(
		{ before(grammarAccess.getPatternCharacterAccess().getValueEqualsSignKeyword_0_5()); }
		EqualsSign
		{ after(grammarAccess.getPatternCharacterAccess().getValueEqualsSignKeyword_0_5()); }
	)
	|
	(
		{ before(grammarAccess.getPatternCharacterAccess().getValueColonKeyword_0_6()); }
		Colon
		{ after(grammarAccess.getPatternCharacterAccess().getValueColonKeyword_0_6()); }
	)
	|
	(
		{ before(grammarAccess.getPatternCharacterAccess().getValueExclamationMarkKeyword_0_7()); }
		ExclamationMark
		{ after(grammarAccess.getPatternCharacterAccess().getValueExclamationMarkKeyword_0_7()); }
	)
	|
	(
		{ before(grammarAccess.getPatternCharacterAccess().getValueLeftCurlyBracketKeyword_0_8()); }
		LeftCurlyBracket
		{ after(grammarAccess.getPatternCharacterAccess().getValueLeftCurlyBracketKeyword_0_8()); }
	)
	|
	(
		{ before(grammarAccess.getPatternCharacterAccess().getValueRightCurlyBracketKeyword_0_9()); }
		RightCurlyBracket
		{ after(grammarAccess.getPatternCharacterAccess().getValueRightCurlyBracketKeyword_0_9()); }
	)
	|
	(
		{ before(grammarAccess.getPatternCharacterAccess().getValueRightSquareBracketKeyword_0_10()); }
		RightSquareBracket
		{ after(grammarAccess.getPatternCharacterAccess().getValueRightSquareBracketKeyword_0_10()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__AtomEscape__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAtomEscapeAccess().getDecimalEscapeSequenceParserRuleCall_0()); }
		ruleDecimalEscapeSequence
		{ after(grammarAccess.getAtomEscapeAccess().getDecimalEscapeSequenceParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getAtomEscapeAccess().getCharacterEscapeSequenceParserRuleCall_1()); }
		ruleCharacterEscapeSequence
		{ after(grammarAccess.getAtomEscapeAccess().getCharacterEscapeSequenceParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getAtomEscapeAccess().getControlLetterEscapeSequenceParserRuleCall_2()); }
		ruleControlLetterEscapeSequence
		{ after(grammarAccess.getAtomEscapeAccess().getControlLetterEscapeSequenceParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getAtomEscapeAccess().getHexEscapeSequenceParserRuleCall_3()); }
		ruleHexEscapeSequence
		{ after(grammarAccess.getAtomEscapeAccess().getHexEscapeSequenceParserRuleCall_3()); }
	)
	|
	(
		{ before(grammarAccess.getAtomEscapeAccess().getUnicodeEscapeSequenceParserRuleCall_4()); }
		ruleUnicodeEscapeSequence
		{ after(grammarAccess.getAtomEscapeAccess().getUnicodeEscapeSequenceParserRuleCall_4()); }
	)
	|
	(
		{ before(grammarAccess.getAtomEscapeAccess().getIdentityEscapeSequenceParserRuleCall_5()); }
		ruleIdentityEscapeSequence
		{ after(grammarAccess.getAtomEscapeAccess().getIdentityEscapeSequenceParserRuleCall_5()); }
	)
	|
	(
		{ before(grammarAccess.getAtomEscapeAccess().getCharacterClassEscapeSequenceParserRuleCall_6()); }
		ruleCharacterClassEscapeSequence
		{ after(grammarAccess.getAtomEscapeAccess().getCharacterClassEscapeSequenceParserRuleCall_6()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClassAtom__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getEscapedCharacterClassAtomParserRuleCall_0()); }
		ruleEscapedCharacterClassAtom
		{ after(grammarAccess.getCharacterClassAtomAccess().getEscapedCharacterClassAtomParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterAssignment_1()); }
		(rule__CharacterClassAtom__CharacterAssignment_1)
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterAssignment_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClassAtom__CharacterAlternatives_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterCommaKeyword_1_0_0()); }
		Comma
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterCommaKeyword_1_0_0()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterEqualsSignKeyword_1_0_1()); }
		EqualsSign
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterEqualsSignKeyword_1_0_1()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterColonKeyword_1_0_2()); }
		Colon
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterColonKeyword_1_0_2()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterExclamationMarkKeyword_1_0_3()); }
		ExclamationMark
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterExclamationMarkKeyword_1_0_3()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterHyphenMinusKeyword_1_0_4()); }
		HyphenMinus
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterHyphenMinusKeyword_1_0_4()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterCircumflexAccentKeyword_1_0_5()); }
		CircumflexAccent
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterCircumflexAccentKeyword_1_0_5()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterDollarSignKeyword_1_0_6()); }
		DollarSign
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterDollarSignKeyword_1_0_6()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterFullStopKeyword_1_0_7()); }
		FullStop
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterFullStopKeyword_1_0_7()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterAsteriskKeyword_1_0_8()); }
		Asterisk
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterAsteriskKeyword_1_0_8()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterPlusSignKeyword_1_0_9()); }
		PlusSign
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterPlusSignKeyword_1_0_9()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterQuestionMarkKeyword_1_0_10()); }
		QuestionMark
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterQuestionMarkKeyword_1_0_10()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterLeftParenthesisKeyword_1_0_11()); }
		LeftParenthesis
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterLeftParenthesisKeyword_1_0_11()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterRightParenthesisKeyword_1_0_12()); }
		RightParenthesis
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterRightParenthesisKeyword_1_0_12()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterLeftSquareBracketKeyword_1_0_13()); }
		LeftSquareBracket
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterLeftSquareBracketKeyword_1_0_13()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterLeftCurlyBracketKeyword_1_0_14()); }
		LeftCurlyBracket
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterLeftCurlyBracketKeyword_1_0_14()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterRightCurlyBracketKeyword_1_0_15()); }
		RightCurlyBracket
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterRightCurlyBracketKeyword_1_0_15()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterVerticalLineKeyword_1_0_16()); }
		VerticalLine
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterVerticalLineKeyword_1_0_16()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterSolidusKeyword_1_0_17()); }
		Solidus
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterSolidusKeyword_1_0_17()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterPATTERN_CHARACTER_NO_DASHTerminalRuleCall_1_0_18()); }
		RULE_PATTERN_CHARACTER_NO_DASH
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterPATTERN_CHARACTER_NO_DASHTerminalRuleCall_1_0_18()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterUNICODE_LETTERTerminalRuleCall_1_0_19()); }
		RULE_UNICODE_LETTER
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterUNICODE_LETTERTerminalRuleCall_1_0_19()); }
	)
	|
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterUNICODE_DIGITTerminalRuleCall_1_0_20()); }
		RULE_UNICODE_DIGIT
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterUNICODE_DIGITTerminalRuleCall_1_0_20()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__EscapedCharacterClassAtom__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEscapedCharacterClassAtomAccess().getDecimalEscapeSequenceParserRuleCall_0()); }
		ruleDecimalEscapeSequence
		{ after(grammarAccess.getEscapedCharacterClassAtomAccess().getDecimalEscapeSequenceParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getEscapedCharacterClassAtomAccess().getBackspaceParserRuleCall_1()); }
		ruleBackspace
		{ after(grammarAccess.getEscapedCharacterClassAtomAccess().getBackspaceParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getEscapedCharacterClassAtomAccess().getCharacterEscapeSequenceParserRuleCall_2()); }
		ruleCharacterEscapeSequence
		{ after(grammarAccess.getEscapedCharacterClassAtomAccess().getCharacterEscapeSequenceParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getEscapedCharacterClassAtomAccess().getControlLetterEscapeSequenceParserRuleCall_3()); }
		ruleControlLetterEscapeSequence
		{ after(grammarAccess.getEscapedCharacterClassAtomAccess().getControlLetterEscapeSequenceParserRuleCall_3()); }
	)
	|
	(
		{ before(grammarAccess.getEscapedCharacterClassAtomAccess().getHexEscapeSequenceParserRuleCall_4()); }
		ruleHexEscapeSequence
		{ after(grammarAccess.getEscapedCharacterClassAtomAccess().getHexEscapeSequenceParserRuleCall_4()); }
	)
	|
	(
		{ before(grammarAccess.getEscapedCharacterClassAtomAccess().getUnicodeEscapeSequenceParserRuleCall_5()); }
		ruleUnicodeEscapeSequence
		{ after(grammarAccess.getEscapedCharacterClassAtomAccess().getUnicodeEscapeSequenceParserRuleCall_5()); }
	)
	|
	(
		{ before(grammarAccess.getEscapedCharacterClassAtomAccess().getIdentityEscapeSequenceParserRuleCall_6()); }
		ruleIdentityEscapeSequence
		{ after(grammarAccess.getEscapedCharacterClassAtomAccess().getIdentityEscapeSequenceParserRuleCall_6()); }
	)
	|
	(
		{ before(grammarAccess.getEscapedCharacterClassAtomAccess().getCharacterClassEscapeSequenceParserRuleCall_7()); }
		ruleCharacterClassEscapeSequence
		{ after(grammarAccess.getEscapedCharacterClassAtomAccess().getCharacterClassEscapeSequenceParserRuleCall_7()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Quantifier__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getQuantifierAccess().getSimpleQuantifierParserRuleCall_0()); }
		ruleSimpleQuantifier
		{ after(grammarAccess.getQuantifierAccess().getSimpleQuantifierParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getQuantifierAccess().getExactQuantifierParserRuleCall_1()); }
		ruleExactQuantifier
		{ after(grammarAccess.getQuantifierAccess().getExactQuantifierParserRuleCall_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleQuantifier__QuantifierAlternatives_0_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSimpleQuantifierAccess().getQuantifierPlusSignKeyword_0_0_0()); }
		PlusSign
		{ after(grammarAccess.getSimpleQuantifierAccess().getQuantifierPlusSignKeyword_0_0_0()); }
	)
	|
	(
		{ before(grammarAccess.getSimpleQuantifierAccess().getQuantifierAsteriskKeyword_0_0_1()); }
		Asterisk
		{ after(grammarAccess.getSimpleQuantifierAccess().getQuantifierAsteriskKeyword_0_0_1()); }
	)
	|
	(
		{ before(grammarAccess.getSimpleQuantifierAccess().getQuantifierQuestionMarkKeyword_0_0_2()); }
		QuestionMark
		{ after(grammarAccess.getSimpleQuantifierAccess().getQuantifierQuestionMarkKeyword_0_0_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExactQuantifier__Alternatives_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getExactQuantifierAccess().getGroup_3_0()); }
		(rule__ExactQuantifier__Group_3_0__0)
		{ after(grammarAccess.getExactQuantifierAccess().getGroup_3_0()); }
	)
	|
	(
		{ before(grammarAccess.getExactQuantifierAccess().getUnboundedMaxAssignment_3_1()); }
		(rule__ExactQuantifier__UnboundedMaxAssignment_3_1)
		{ after(grammarAccess.getExactQuantifierAccess().getUnboundedMaxAssignment_3_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RegularExpressionFlags__FlagsAlternatives_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRegularExpressionFlagsAccess().getFlagsUNICODE_LETTERTerminalRuleCall_1_0_0()); }
		RULE_UNICODE_LETTER
		{ after(grammarAccess.getRegularExpressionFlagsAccess().getFlagsUNICODE_LETTERTerminalRuleCall_1_0_0()); }
	)
	|
	(
		{ before(grammarAccess.getRegularExpressionFlagsAccess().getFlagsUNICODE_ESCAPETerminalRuleCall_1_0_1()); }
		RULE_UNICODE_ESCAPE
		{ after(grammarAccess.getRegularExpressionFlagsAccess().getFlagsUNICODE_ESCAPETerminalRuleCall_1_0_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RegularExpressionLiteral__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RegularExpressionLiteral__Group__0__Impl
	rule__RegularExpressionLiteral__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__RegularExpressionLiteral__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRegularExpressionLiteralAccess().getSolidusKeyword_0()); }
	Solidus
	{ after(grammarAccess.getRegularExpressionLiteralAccess().getSolidusKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RegularExpressionLiteral__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RegularExpressionLiteral__Group__1__Impl
	rule__RegularExpressionLiteral__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__RegularExpressionLiteral__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRegularExpressionLiteralAccess().getBodyAssignment_1()); }
	(rule__RegularExpressionLiteral__BodyAssignment_1)
	{ after(grammarAccess.getRegularExpressionLiteralAccess().getBodyAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RegularExpressionLiteral__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RegularExpressionLiteral__Group__2__Impl
	rule__RegularExpressionLiteral__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__RegularExpressionLiteral__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRegularExpressionLiteralAccess().getSolidusKeyword_2()); }
	Solidus
	{ after(grammarAccess.getRegularExpressionLiteralAccess().getSolidusKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RegularExpressionLiteral__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RegularExpressionLiteral__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__RegularExpressionLiteral__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRegularExpressionLiteralAccess().getFlagsAssignment_3()); }
	(rule__RegularExpressionLiteral__FlagsAssignment_3)
	{ after(grammarAccess.getRegularExpressionLiteralAccess().getFlagsAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Disjunction__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Disjunction__Group_0__0__Impl
	rule__Disjunction__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Disjunction__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDisjunctionAccess().getAlternativeParserRuleCall_0_0()); }
	ruleAlternative
	{ after(grammarAccess.getDisjunctionAccess().getAlternativeParserRuleCall_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Disjunction__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Disjunction__Group_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Disjunction__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDisjunctionAccess().getGroup_0_1()); }
	(rule__Disjunction__Group_0_1__0)?
	{ after(grammarAccess.getDisjunctionAccess().getGroup_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Disjunction__Group_0_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Disjunction__Group_0_1__0__Impl
	rule__Disjunction__Group_0_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Disjunction__Group_0_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDisjunctionAccess().getDisjunctionElementsAction_0_1_0()); }
	()
	{ after(grammarAccess.getDisjunctionAccess().getDisjunctionElementsAction_0_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Disjunction__Group_0_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Disjunction__Group_0_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Disjunction__Group_0_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	(
		{ before(grammarAccess.getDisjunctionAccess().getGroup_0_1_1()); }
		(rule__Disjunction__Group_0_1_1__0)
		{ after(grammarAccess.getDisjunctionAccess().getGroup_0_1_1()); }
	)
	(
		{ before(grammarAccess.getDisjunctionAccess().getGroup_0_1_1()); }
		(rule__Disjunction__Group_0_1_1__0)*
		{ after(grammarAccess.getDisjunctionAccess().getGroup_0_1_1()); }
	)
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Disjunction__Group_0_1_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Disjunction__Group_0_1_1__0__Impl
	rule__Disjunction__Group_0_1_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Disjunction__Group_0_1_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDisjunctionAccess().getVerticalLineKeyword_0_1_1_0()); }
	VerticalLine
	{ after(grammarAccess.getDisjunctionAccess().getVerticalLineKeyword_0_1_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Disjunction__Group_0_1_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Disjunction__Group_0_1_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Disjunction__Group_0_1_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDisjunctionAccess().getElementsAssignment_0_1_1_1()); }
	(rule__Disjunction__ElementsAssignment_0_1_1_1)?
	{ after(grammarAccess.getDisjunctionAccess().getElementsAssignment_0_1_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Disjunction__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Disjunction__Group_1__0__Impl
	rule__Disjunction__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Disjunction__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDisjunctionAccess().getDisjunctionAction_1_0()); }
	()
	{ after(grammarAccess.getDisjunctionAccess().getDisjunctionAction_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Disjunction__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Disjunction__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Disjunction__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDisjunctionAccess().getGroup_1_1()); }
	(rule__Disjunction__Group_1_1__0)*
	{ after(grammarAccess.getDisjunctionAccess().getGroup_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Disjunction__Group_1_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Disjunction__Group_1_1__0__Impl
	rule__Disjunction__Group_1_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Disjunction__Group_1_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDisjunctionAccess().getVerticalLineKeyword_1_1_0()); }
	VerticalLine
	{ after(grammarAccess.getDisjunctionAccess().getVerticalLineKeyword_1_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Disjunction__Group_1_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Disjunction__Group_1_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Disjunction__Group_1_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDisjunctionAccess().getElementsAssignment_1_1_1()); }
	(rule__Disjunction__ElementsAssignment_1_1_1)?
	{ after(grammarAccess.getDisjunctionAccess().getElementsAssignment_1_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Alternative__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Alternative__Group__0__Impl
	rule__Alternative__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Alternative__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAlternativeAccess().getTermParserRuleCall_0()); }
	ruleTerm
	{ after(grammarAccess.getAlternativeAccess().getTermParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Alternative__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Alternative__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Alternative__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAlternativeAccess().getGroup_1()); }
	(rule__Alternative__Group_1__0)?
	{ after(grammarAccess.getAlternativeAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Alternative__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Alternative__Group_1__0__Impl
	rule__Alternative__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Alternative__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAlternativeAccess().getSequenceElementsAction_1_0()); }
	()
	{ after(grammarAccess.getAlternativeAccess().getSequenceElementsAction_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Alternative__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Alternative__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Alternative__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	(
		{ before(grammarAccess.getAlternativeAccess().getElementsAssignment_1_1()); }
		(rule__Alternative__ElementsAssignment_1_1)
		{ after(grammarAccess.getAlternativeAccess().getElementsAssignment_1_1()); }
	)
	(
		{ before(grammarAccess.getAlternativeAccess().getElementsAssignment_1_1()); }
		(rule__Alternative__ElementsAssignment_1_1)*
		{ after(grammarAccess.getAlternativeAccess().getElementsAssignment_1_1()); }
	)
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Term__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Term__Group_1__0__Impl
	rule__Term__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Term__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTermAccess().getAtomParserRuleCall_1_0()); }
	ruleAtom
	{ after(grammarAccess.getTermAccess().getAtomParserRuleCall_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Term__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Term__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Term__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTermAccess().getQuantifierAssignment_1_1()); }
	(rule__Term__QuantifierAssignment_1_1)?
	{ after(grammarAccess.getTermAccess().getQuantifierAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__LineStart__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LineStart__Group__0__Impl
	rule__LineStart__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__LineStart__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLineStartAccess().getLineStartAction_0()); }
	()
	{ after(grammarAccess.getLineStartAccess().getLineStartAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__LineStart__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LineStart__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__LineStart__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLineStartAccess().getCircumflexAccentKeyword_1()); }
	CircumflexAccent
	{ after(grammarAccess.getLineStartAccess().getCircumflexAccentKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__LineEnd__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LineEnd__Group__0__Impl
	rule__LineEnd__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__LineEnd__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLineEndAccess().getLineEndAction_0()); }
	()
	{ after(grammarAccess.getLineEndAccess().getLineEndAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__LineEnd__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LineEnd__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__LineEnd__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLineEndAccess().getDollarSignKeyword_1()); }
	DollarSign
	{ after(grammarAccess.getLineEndAccess().getDollarSignKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__WordBoundary__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WordBoundary__Group__0__Impl
	rule__WordBoundary__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__WordBoundary__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWordBoundaryAccess().getWordBoundaryAction_0()); }
	()
	{ after(grammarAccess.getWordBoundaryAccess().getWordBoundaryAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__WordBoundary__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__WordBoundary__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__WordBoundary__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWordBoundaryAccess().getAlternatives_1()); }
	(rule__WordBoundary__Alternatives_1)
	{ after(grammarAccess.getWordBoundaryAccess().getAlternatives_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__LookAhead__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LookAhead__Group__0__Impl
	rule__LookAhead__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__LookAhead__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLookAheadAccess().getLookAheadAction_0()); }
	()
	{ after(grammarAccess.getLookAheadAccess().getLookAheadAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__LookAhead__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LookAhead__Group__1__Impl
	rule__LookAhead__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__LookAhead__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLookAheadAccess().getLeftParenthesisKeyword_1()); }
	LeftParenthesis
	{ after(grammarAccess.getLookAheadAccess().getLeftParenthesisKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__LookAhead__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LookAhead__Group__2__Impl
	rule__LookAhead__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__LookAhead__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLookAheadAccess().getQuestionMarkKeyword_2()); }
	QuestionMark
	{ after(grammarAccess.getLookAheadAccess().getQuestionMarkKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__LookAhead__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LookAhead__Group__3__Impl
	rule__LookAhead__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__LookAhead__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLookAheadAccess().getAlternatives_3()); }
	(rule__LookAhead__Alternatives_3)
	{ after(grammarAccess.getLookAheadAccess().getAlternatives_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__LookAhead__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LookAhead__Group__4__Impl
	rule__LookAhead__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__LookAhead__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLookAheadAccess().getPatternAssignment_4()); }
	(rule__LookAhead__PatternAssignment_4)
	{ after(grammarAccess.getLookAheadAccess().getPatternAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__LookAhead__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LookAhead__Group__5__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__LookAhead__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLookAheadAccess().getRightParenthesisKeyword_5()); }
	RightParenthesis
	{ after(grammarAccess.getLookAheadAccess().getRightParenthesisKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Wildcard__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Wildcard__Group__0__Impl
	rule__Wildcard__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Wildcard__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWildcardAccess().getWildcardAction_0()); }
	()
	{ after(grammarAccess.getWildcardAccess().getWildcardAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Wildcard__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Wildcard__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Wildcard__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getWildcardAccess().getFullStopKeyword_1()); }
	FullStop
	{ after(grammarAccess.getWildcardAccess().getFullStopKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__CharacterClass__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CharacterClass__Group__0__Impl
	rule__CharacterClass__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClass__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCharacterClassAccess().getCharacterClassAction_0()); }
	()
	{ after(grammarAccess.getCharacterClassAccess().getCharacterClassAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClass__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CharacterClass__Group__1__Impl
	rule__CharacterClass__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClass__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCharacterClassAccess().getLeftSquareBracketKeyword_1()); }
	LeftSquareBracket
	{ after(grammarAccess.getCharacterClassAccess().getLeftSquareBracketKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClass__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CharacterClass__Group__2__Impl
	rule__CharacterClass__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClass__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCharacterClassAccess().getGroup_2()); }
	(rule__CharacterClass__Group_2__0)?
	{ after(grammarAccess.getCharacterClassAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClass__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CharacterClass__Group__3__Impl
	rule__CharacterClass__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClass__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCharacterClassAccess().getElementsAssignment_3()); }
	(rule__CharacterClass__ElementsAssignment_3)*
	{ after(grammarAccess.getCharacterClassAccess().getElementsAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClass__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CharacterClass__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClass__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCharacterClassAccess().getRightSquareBracketKeyword_4()); }
	RightSquareBracket
	{ after(grammarAccess.getCharacterClassAccess().getRightSquareBracketKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__CharacterClass__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CharacterClass__Group_2__0__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClass__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCharacterClassAccess().getNegatedAssignment_2_0()); }
	(rule__CharacterClass__NegatedAssignment_2_0)
	{ after(grammarAccess.getCharacterClassAccess().getNegatedAssignment_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__CharacterClassElement__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CharacterClassElement__Group__0__Impl
	rule__CharacterClassElement__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClassElement__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCharacterClassElementAccess().getCharacterClassAtomParserRuleCall_0()); }
	ruleCharacterClassAtom
	{ after(grammarAccess.getCharacterClassElementAccess().getCharacterClassAtomParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClassElement__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CharacterClassElement__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClassElement__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCharacterClassElementAccess().getGroup_1()); }
	(rule__CharacterClassElement__Group_1__0)?
	{ after(grammarAccess.getCharacterClassElementAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__CharacterClassElement__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CharacterClassElement__Group_1__0__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClassElement__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCharacterClassElementAccess().getGroup_1_0()); }
	(rule__CharacterClassElement__Group_1_0__0)
	{ after(grammarAccess.getCharacterClassElementAccess().getGroup_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__CharacterClassElement__Group_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CharacterClassElement__Group_1_0__0__Impl
	rule__CharacterClassElement__Group_1_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClassElement__Group_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCharacterClassElementAccess().getCharacterClassRangeLeftAction_1_0_0()); }
	()
	{ after(grammarAccess.getCharacterClassElementAccess().getCharacterClassRangeLeftAction_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClassElement__Group_1_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CharacterClassElement__Group_1_0__1__Impl
	rule__CharacterClassElement__Group_1_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClassElement__Group_1_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCharacterClassElementAccess().getHyphenMinusKeyword_1_0_1()); }
	HyphenMinus
	{ after(grammarAccess.getCharacterClassElementAccess().getHyphenMinusKeyword_1_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClassElement__Group_1_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CharacterClassElement__Group_1_0__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClassElement__Group_1_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCharacterClassElementAccess().getRightAssignment_1_0_2()); }
	(rule__CharacterClassElement__RightAssignment_1_0_2)
	{ after(grammarAccess.getCharacterClassElementAccess().getRightAssignment_1_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Backspace__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Backspace__Group__0__Impl
	rule__Backspace__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Backspace__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getBackspaceAccess().getBackspaceAction_0()); }
	()
	{ after(grammarAccess.getBackspaceAccess().getBackspaceAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Backspace__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Backspace__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Backspace__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getBackspaceAccess().getWORD_BOUNDARYTerminalRuleCall_1()); }
	RULE_WORD_BOUNDARY
	{ after(grammarAccess.getBackspaceAccess().getWORD_BOUNDARYTerminalRuleCall_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Group__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Group__Group__0__Impl
	rule__Group__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Group__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getGroupAccess().getGroupAction_0()); }
	()
	{ after(grammarAccess.getGroupAccess().getGroupAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Group__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Group__Group__1__Impl
	rule__Group__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Group__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getGroupAccess().getLeftParenthesisKeyword_1()); }
	LeftParenthesis
	{ after(grammarAccess.getGroupAccess().getLeftParenthesisKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Group__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Group__Group__2__Impl
	rule__Group__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Group__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getGroupAccess().getGroup_2()); }
	(rule__Group__Group_2__0)?
	{ after(grammarAccess.getGroupAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Group__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Group__Group__3__Impl
	rule__Group__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Group__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getGroupAccess().getPatternAssignment_3()); }
	(rule__Group__PatternAssignment_3)
	{ after(grammarAccess.getGroupAccess().getPatternAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Group__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Group__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Group__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getGroupAccess().getRightParenthesisKeyword_4()); }
	RightParenthesis
	{ after(grammarAccess.getGroupAccess().getRightParenthesisKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Group__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Group__Group_2__0__Impl
	rule__Group__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Group__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getGroupAccess().getNonCapturingAssignment_2_0()); }
	(rule__Group__NonCapturingAssignment_2_0)
	{ after(grammarAccess.getGroupAccess().getNonCapturingAssignment_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Group__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Group__Group_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Group__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getGroupAccess().getColonKeyword_2_1()); }
	Colon
	{ after(grammarAccess.getGroupAccess().getColonKeyword_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__SimpleQuantifier__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SimpleQuantifier__Group__0__Impl
	rule__SimpleQuantifier__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleQuantifier__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSimpleQuantifierAccess().getQuantifierAssignment_0()); }
	(rule__SimpleQuantifier__QuantifierAssignment_0)
	{ after(grammarAccess.getSimpleQuantifierAccess().getQuantifierAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleQuantifier__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SimpleQuantifier__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleQuantifier__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSimpleQuantifierAccess().getNonGreedyAssignment_1()); }
	(rule__SimpleQuantifier__NonGreedyAssignment_1)?
	{ after(grammarAccess.getSimpleQuantifierAccess().getNonGreedyAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ExactQuantifier__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExactQuantifier__Group__0__Impl
	rule__ExactQuantifier__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ExactQuantifier__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExactQuantifierAccess().getExactQuantifierAction_0()); }
	()
	{ after(grammarAccess.getExactQuantifierAccess().getExactQuantifierAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExactQuantifier__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExactQuantifier__Group__1__Impl
	rule__ExactQuantifier__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ExactQuantifier__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExactQuantifierAccess().getLeftCurlyBracketKeyword_1()); }
	LeftCurlyBracket
	{ after(grammarAccess.getExactQuantifierAccess().getLeftCurlyBracketKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExactQuantifier__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExactQuantifier__Group__2__Impl
	rule__ExactQuantifier__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ExactQuantifier__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExactQuantifierAccess().getMinAssignment_2()); }
	(rule__ExactQuantifier__MinAssignment_2)
	{ after(grammarAccess.getExactQuantifierAccess().getMinAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExactQuantifier__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExactQuantifier__Group__3__Impl
	rule__ExactQuantifier__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__ExactQuantifier__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExactQuantifierAccess().getAlternatives_3()); }
	(rule__ExactQuantifier__Alternatives_3)?
	{ after(grammarAccess.getExactQuantifierAccess().getAlternatives_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExactQuantifier__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExactQuantifier__Group__4__Impl
	rule__ExactQuantifier__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__ExactQuantifier__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExactQuantifierAccess().getRightCurlyBracketKeyword_4()); }
	RightCurlyBracket
	{ after(grammarAccess.getExactQuantifierAccess().getRightCurlyBracketKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExactQuantifier__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExactQuantifier__Group__5__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ExactQuantifier__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExactQuantifierAccess().getNonGreedyAssignment_5()); }
	(rule__ExactQuantifier__NonGreedyAssignment_5)?
	{ after(grammarAccess.getExactQuantifierAccess().getNonGreedyAssignment_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ExactQuantifier__Group_3_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExactQuantifier__Group_3_0__0__Impl
	rule__ExactQuantifier__Group_3_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ExactQuantifier__Group_3_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExactQuantifierAccess().getCommaKeyword_3_0_0()); }
	Comma
	{ after(grammarAccess.getExactQuantifierAccess().getCommaKeyword_3_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExactQuantifier__Group_3_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ExactQuantifier__Group_3_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ExactQuantifier__Group_3_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getExactQuantifierAccess().getMaxAssignment_3_0_1()); }
	(rule__ExactQuantifier__MaxAssignment_3_0_1)
	{ after(grammarAccess.getExactQuantifierAccess().getMaxAssignment_3_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__RegularExpressionFlags__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RegularExpressionFlags__Group__0__Impl
	rule__RegularExpressionFlags__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__RegularExpressionFlags__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRegularExpressionFlagsAccess().getRegularExpressionFlagsAction_0()); }
	()
	{ after(grammarAccess.getRegularExpressionFlagsAccess().getRegularExpressionFlagsAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RegularExpressionFlags__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RegularExpressionFlags__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__RegularExpressionFlags__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRegularExpressionFlagsAccess().getFlagsAssignment_1()); }
	(rule__RegularExpressionFlags__FlagsAssignment_1)*
	{ after(grammarAccess.getRegularExpressionFlagsAccess().getFlagsAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__RegularExpressionLiteral__BodyAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRegularExpressionLiteralAccess().getBodyRegularExpressionBodyParserRuleCall_1_0()); }
		ruleRegularExpressionBody
		{ after(grammarAccess.getRegularExpressionLiteralAccess().getBodyRegularExpressionBodyParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RegularExpressionLiteral__FlagsAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRegularExpressionLiteralAccess().getFlagsRegularExpressionFlagsParserRuleCall_3_0()); }
		ruleRegularExpressionFlags
		{ after(grammarAccess.getRegularExpressionLiteralAccess().getFlagsRegularExpressionFlagsParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RegularExpressionBody__PatternAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRegularExpressionBodyAccess().getPatternDisjunctionParserRuleCall_0()); }
		ruleDisjunction
		{ after(grammarAccess.getRegularExpressionBodyAccess().getPatternDisjunctionParserRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Disjunction__ElementsAssignment_0_1_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getDisjunctionAccess().getElementsAlternativeParserRuleCall_0_1_1_1_0()); }
		ruleAlternative
		{ after(grammarAccess.getDisjunctionAccess().getElementsAlternativeParserRuleCall_0_1_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Disjunction__ElementsAssignment_1_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getDisjunctionAccess().getElementsAlternativeParserRuleCall_1_1_1_0()); }
		ruleAlternative
		{ after(grammarAccess.getDisjunctionAccess().getElementsAlternativeParserRuleCall_1_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Alternative__ElementsAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAlternativeAccess().getElementsTermParserRuleCall_1_1_0()); }
		ruleTerm
		{ after(grammarAccess.getAlternativeAccess().getElementsTermParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Term__QuantifierAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTermAccess().getQuantifierQuantifierParserRuleCall_1_1_0()); }
		ruleQuantifier
		{ after(grammarAccess.getTermAccess().getQuantifierQuantifierParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__WordBoundary__NotAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getWordBoundaryAccess().getNotNOT_WORD_BOUNDARYTerminalRuleCall_1_1_0()); }
		RULE_NOT_WORD_BOUNDARY
		{ after(grammarAccess.getWordBoundaryAccess().getNotNOT_WORD_BOUNDARYTerminalRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__LookAhead__NotAssignment_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getLookAheadAccess().getNotExclamationMarkKeyword_3_1_0()); }
		(
			{ before(grammarAccess.getLookAheadAccess().getNotExclamationMarkKeyword_3_1_0()); }
			ExclamationMark
			{ after(grammarAccess.getLookAheadAccess().getNotExclamationMarkKeyword_3_1_0()); }
		)
		{ after(grammarAccess.getLookAheadAccess().getNotExclamationMarkKeyword_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__LookAhead__PatternAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getLookAheadAccess().getPatternDisjunctionParserRuleCall_4_0()); }
		ruleDisjunction
		{ after(grammarAccess.getLookAheadAccess().getPatternDisjunctionParserRuleCall_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__PatternCharacter__ValueAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPatternCharacterAccess().getValueAlternatives_0()); }
		(rule__PatternCharacter__ValueAlternatives_0)
		{ after(grammarAccess.getPatternCharacterAccess().getValueAlternatives_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClassEscapeSequence__SequenceAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCharacterClassEscapeSequenceAccess().getSequenceCHARACTER_CLASS_ESCAPETerminalRuleCall_0()); }
		RULE_CHARACTER_CLASS_ESCAPE
		{ after(grammarAccess.getCharacterClassEscapeSequenceAccess().getSequenceCHARACTER_CLASS_ESCAPETerminalRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterEscapeSequence__SequenceAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCharacterEscapeSequenceAccess().getSequenceCONTROL_ESCAPETerminalRuleCall_0()); }
		RULE_CONTROL_ESCAPE
		{ after(grammarAccess.getCharacterEscapeSequenceAccess().getSequenceCONTROL_ESCAPETerminalRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ControlLetterEscapeSequence__SequenceAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getControlLetterEscapeSequenceAccess().getSequenceCONTROL_LETTER_ESCAPETerminalRuleCall_0()); }
		RULE_CONTROL_LETTER_ESCAPE
		{ after(grammarAccess.getControlLetterEscapeSequenceAccess().getSequenceCONTROL_LETTER_ESCAPETerminalRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__HexEscapeSequence__SequenceAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getHexEscapeSequenceAccess().getSequenceHEX_ESCAPETerminalRuleCall_0()); }
		RULE_HEX_ESCAPE
		{ after(grammarAccess.getHexEscapeSequenceAccess().getSequenceHEX_ESCAPETerminalRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__UnicodeEscapeSequence__SequenceAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getUnicodeEscapeSequenceAccess().getSequenceUNICODE_ESCAPETerminalRuleCall_0()); }
		RULE_UNICODE_ESCAPE
		{ after(grammarAccess.getUnicodeEscapeSequenceAccess().getSequenceUNICODE_ESCAPETerminalRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__IdentityEscapeSequence__SequenceAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getIdentityEscapeSequenceAccess().getSequenceIDENTITY_ESCAPETerminalRuleCall_0()); }
		RULE_IDENTITY_ESCAPE
		{ after(grammarAccess.getIdentityEscapeSequenceAccess().getSequenceIDENTITY_ESCAPETerminalRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__DecimalEscapeSequence__SequenceAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getDecimalEscapeSequenceAccess().getSequenceDECIMAL_ESCAPETerminalRuleCall_0()); }
		RULE_DECIMAL_ESCAPE
		{ after(grammarAccess.getDecimalEscapeSequenceAccess().getSequenceDECIMAL_ESCAPETerminalRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClass__NegatedAssignment_2_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCharacterClassAccess().getNegatedCircumflexAccentKeyword_2_0_0()); }
		(
			{ before(grammarAccess.getCharacterClassAccess().getNegatedCircumflexAccentKeyword_2_0_0()); }
			CircumflexAccent
			{ after(grammarAccess.getCharacterClassAccess().getNegatedCircumflexAccentKeyword_2_0_0()); }
		)
		{ after(grammarAccess.getCharacterClassAccess().getNegatedCircumflexAccentKeyword_2_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClass__ElementsAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCharacterClassAccess().getElementsCharacterClassElementParserRuleCall_3_0()); }
		ruleCharacterClassElement
		{ after(grammarAccess.getCharacterClassAccess().getElementsCharacterClassElementParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClassElement__RightAssignment_1_0_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCharacterClassElementAccess().getRightCharacterClassAtomParserRuleCall_1_0_2_0()); }
		ruleCharacterClassAtom
		{ after(grammarAccess.getCharacterClassElementAccess().getRightCharacterClassAtomParserRuleCall_1_0_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CharacterClassAtom__CharacterAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCharacterClassAtomAccess().getCharacterAlternatives_1_0()); }
		(rule__CharacterClassAtom__CharacterAlternatives_1_0)
		{ after(grammarAccess.getCharacterClassAtomAccess().getCharacterAlternatives_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Group__NonCapturingAssignment_2_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getGroupAccess().getNonCapturingQuestionMarkKeyword_2_0_0()); }
		(
			{ before(grammarAccess.getGroupAccess().getNonCapturingQuestionMarkKeyword_2_0_0()); }
			QuestionMark
			{ after(grammarAccess.getGroupAccess().getNonCapturingQuestionMarkKeyword_2_0_0()); }
		)
		{ after(grammarAccess.getGroupAccess().getNonCapturingQuestionMarkKeyword_2_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Group__PatternAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getGroupAccess().getPatternDisjunctionParserRuleCall_3_0()); }
		ruleDisjunction
		{ after(grammarAccess.getGroupAccess().getPatternDisjunctionParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleQuantifier__QuantifierAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSimpleQuantifierAccess().getQuantifierAlternatives_0_0()); }
		(rule__SimpleQuantifier__QuantifierAlternatives_0_0)
		{ after(grammarAccess.getSimpleQuantifierAccess().getQuantifierAlternatives_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleQuantifier__NonGreedyAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSimpleQuantifierAccess().getNonGreedyQuestionMarkKeyword_1_0()); }
		(
			{ before(grammarAccess.getSimpleQuantifierAccess().getNonGreedyQuestionMarkKeyword_1_0()); }
			QuestionMark
			{ after(grammarAccess.getSimpleQuantifierAccess().getNonGreedyQuestionMarkKeyword_1_0()); }
		)
		{ after(grammarAccess.getSimpleQuantifierAccess().getNonGreedyQuestionMarkKeyword_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExactQuantifier__MinAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getExactQuantifierAccess().getMinINTParserRuleCall_2_0()); }
		ruleINT
		{ after(grammarAccess.getExactQuantifierAccess().getMinINTParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExactQuantifier__MaxAssignment_3_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getExactQuantifierAccess().getMaxINTParserRuleCall_3_0_1_0()); }
		ruleINT
		{ after(grammarAccess.getExactQuantifierAccess().getMaxINTParserRuleCall_3_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExactQuantifier__UnboundedMaxAssignment_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getExactQuantifierAccess().getUnboundedMaxCommaKeyword_3_1_0()); }
		(
			{ before(grammarAccess.getExactQuantifierAccess().getUnboundedMaxCommaKeyword_3_1_0()); }
			Comma
			{ after(grammarAccess.getExactQuantifierAccess().getUnboundedMaxCommaKeyword_3_1_0()); }
		)
		{ after(grammarAccess.getExactQuantifierAccess().getUnboundedMaxCommaKeyword_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ExactQuantifier__NonGreedyAssignment_5
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getExactQuantifierAccess().getNonGreedyQuestionMarkKeyword_5_0()); }
		(
			{ before(grammarAccess.getExactQuantifierAccess().getNonGreedyQuestionMarkKeyword_5_0()); }
			QuestionMark
			{ after(grammarAccess.getExactQuantifierAccess().getNonGreedyQuestionMarkKeyword_5_0()); }
		)
		{ after(grammarAccess.getExactQuantifierAccess().getNonGreedyQuestionMarkKeyword_5_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RegularExpressionFlags__FlagsAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRegularExpressionFlagsAccess().getFlagsAlternatives_1_0()); }
		(rule__RegularExpressionFlags__FlagsAlternatives_1_0)
		{ after(grammarAccess.getRegularExpressionFlagsAccess().getFlagsAlternatives_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}
