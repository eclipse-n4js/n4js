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
	superClass=AbstractInternalAntlrParser;
}

@header {
package org.eclipse.n4js.regex.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.n4js.regex.services.RegularExpressionGrammarAccess;

}

@members {

 	private RegularExpressionGrammarAccess grammarAccess;

    public InternalRegularExpressionParser(TokenStream input, RegularExpressionGrammarAccess grammarAccess) {
        this(input);
        this.grammarAccess = grammarAccess;
        registerRules(grammarAccess.getGrammar());
    }

    @Override
    protected String getFirstRuleName() {
    	return "RegularExpressionLiteral";
   	}

   	@Override
   	protected RegularExpressionGrammarAccess getGrammarAccess() {
   		return grammarAccess;
   	}

}

@rulecatch {
    catch (RecognitionException re) {
        recover(input,re);
        appendSkippedTokens();
    }
}

// Entry rule entryRuleRegularExpressionLiteral
entryRuleRegularExpressionLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getRegularExpressionLiteralRule()); }
	iv_ruleRegularExpressionLiteral=ruleRegularExpressionLiteral
	{ $current=$iv_ruleRegularExpressionLiteral.current; }
	EOF;

// Rule RegularExpressionLiteral
ruleRegularExpressionLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Solidus
		{
			newLeafNode(otherlv_0, grammarAccess.getRegularExpressionLiteralAccess().getSolidusKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getRegularExpressionLiteralAccess().getBodyRegularExpressionBodyParserRuleCall_1_0());
				}
				lv_body_1_0=ruleRegularExpressionBody
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getRegularExpressionLiteralRule());
					}
					set(
						$current,
						"body",
						lv_body_1_0,
						"org.eclipse.n4js.regex.RegularExpression.RegularExpressionBody");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_2=Solidus
		{
			newLeafNode(otherlv_2, grammarAccess.getRegularExpressionLiteralAccess().getSolidusKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getRegularExpressionLiteralAccess().getFlagsRegularExpressionFlagsParserRuleCall_3_0());
				}
				lv_flags_3_0=ruleRegularExpressionFlags
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getRegularExpressionLiteralRule());
					}
					set(
						$current,
						"flags",
						lv_flags_3_0,
						"org.eclipse.n4js.regex.RegularExpression.RegularExpressionFlags");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleRegularExpressionBody
entryRuleRegularExpressionBody returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getRegularExpressionBodyRule()); }
	iv_ruleRegularExpressionBody=ruleRegularExpressionBody
	{ $current=$iv_ruleRegularExpressionBody.current; }
	EOF;

// Rule RegularExpressionBody
ruleRegularExpressionBody returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getRegularExpressionBodyAccess().getPatternDisjunctionParserRuleCall_0());
			}
			lv_pattern_0_0=ruleDisjunction
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getRegularExpressionBodyRule());
				}
				set(
					$current,
					"pattern",
					lv_pattern_0_0,
					"org.eclipse.n4js.regex.RegularExpression.Disjunction");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleDisjunction
entryRuleDisjunction returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getDisjunctionRule()); }
	iv_ruleDisjunction=ruleDisjunction
	{ $current=$iv_ruleDisjunction.current; }
	EOF;

// Rule Disjunction
ruleDisjunction returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getDisjunctionAccess().getAlternativeParserRuleCall_0_0());
			}
			this_Alternative_0=ruleAlternative
			{
				$current = $this_Alternative_0.current;
				afterParserOrEnumRuleCall();
			}
			(
				(
					{
						$current = forceCreateModelElementAndAdd(
							grammarAccess.getDisjunctionAccess().getDisjunctionElementsAction_0_1_0(),
							$current);
					}
				)
				(
					otherlv_2=VerticalLine
					{
						newLeafNode(otherlv_2, grammarAccess.getDisjunctionAccess().getVerticalLineKeyword_0_1_1_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getDisjunctionAccess().getElementsAlternativeParserRuleCall_0_1_1_1_0());
							}
							lv_elements_3_0=ruleAlternative
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getDisjunctionRule());
								}
								add(
									$current,
									"elements",
									lv_elements_3_0,
									"org.eclipse.n4js.regex.RegularExpression.Alternative");
								afterParserOrEnumRuleCall();
							}
						)
					)?
				)+
			)?
		)
		    |
		(
			(
				{
					$current = forceCreateModelElement(
						grammarAccess.getDisjunctionAccess().getDisjunctionAction_1_0(),
						$current);
				}
			)
			(
				otherlv_5=VerticalLine
				{
					newLeafNode(otherlv_5, grammarAccess.getDisjunctionAccess().getVerticalLineKeyword_1_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getDisjunctionAccess().getElementsAlternativeParserRuleCall_1_1_1_0());
						}
						lv_elements_6_0=ruleAlternative
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getDisjunctionRule());
							}
							add(
								$current,
								"elements",
								lv_elements_6_0,
								"org.eclipse.n4js.regex.RegularExpression.Alternative");
							afterParserOrEnumRuleCall();
						}
					)
				)?
			)*
		)
	)
;

// Entry rule entryRuleAlternative
entryRuleAlternative returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAlternativeRule()); }
	iv_ruleAlternative=ruleAlternative
	{ $current=$iv_ruleAlternative.current; }
	EOF;

// Rule Alternative
ruleAlternative returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getAlternativeAccess().getTermParserRuleCall_0());
		}
		this_Term_0=ruleTerm
		{
			$current = $this_Term_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				{
					$current = forceCreateModelElementAndAdd(
						grammarAccess.getAlternativeAccess().getSequenceElementsAction_1_0(),
						$current);
				}
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getAlternativeAccess().getElementsTermParserRuleCall_1_1_0());
					}
					lv_elements_2_0=ruleTerm
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getAlternativeRule());
						}
						add(
							$current,
							"elements",
							lv_elements_2_0,
							"org.eclipse.n4js.regex.RegularExpression.Term");
						afterParserOrEnumRuleCall();
					}
				)
			)+
		)?
	)
;

// Entry rule entryRuleTerm
entryRuleTerm returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTermRule()); }
	iv_ruleTerm=ruleTerm
	{ $current=$iv_ruleTerm.current; }
	EOF;

// Rule Term
ruleTerm returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getTermAccess().getAssertionParserRuleCall_0());
		}
		this_Assertion_0=ruleAssertion
		{
			$current = $this_Assertion_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			{
				newCompositeNode(grammarAccess.getTermAccess().getAtomParserRuleCall_1_0());
			}
			this_Atom_1=ruleAtom
			{
				$current = $this_Atom_1.current;
				afterParserOrEnumRuleCall();
			}
			(
				((
					ruleQuantifier
				)
				)=>
				(
					{
						newCompositeNode(grammarAccess.getTermAccess().getQuantifierQuantifierParserRuleCall_1_1_0());
					}
					lv_quantifier_2_0=ruleQuantifier
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTermRule());
						}
						set(
							$current,
							"quantifier",
							lv_quantifier_2_0,
							"org.eclipse.n4js.regex.RegularExpression.Quantifier");
						afterParserOrEnumRuleCall();
					}
				)
			)?
		)
	)
;

// Entry rule entryRuleAssertion
entryRuleAssertion returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAssertionRule()); }
	iv_ruleAssertion=ruleAssertion
	{ $current=$iv_ruleAssertion.current; }
	EOF;

// Rule Assertion
ruleAssertion returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getAssertionAccess().getLineStartParserRuleCall_0());
		}
		this_LineStart_0=ruleLineStart
		{
			$current = $this_LineStart_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAssertionAccess().getLineEndParserRuleCall_1());
		}
		this_LineEnd_1=ruleLineEnd
		{
			$current = $this_LineEnd_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAssertionAccess().getWordBoundaryParserRuleCall_2());
		}
		this_WordBoundary_2=ruleWordBoundary
		{
			$current = $this_WordBoundary_2.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAssertionAccess().getAbstractLookAheadParserRuleCall_3());
		}
		this_AbstractLookAhead_3=ruleAbstractLookAhead
		{
			$current = $this_AbstractLookAhead_3.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleLineStart
entryRuleLineStart returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getLineStartRule()); }
	iv_ruleLineStart=ruleLineStart
	{ $current=$iv_ruleLineStart.current; }
	EOF;

// Rule LineStart
ruleLineStart returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getLineStartAccess().getLineStartAction_0(),
					$current);
			}
		)
		otherlv_1=CircumflexAccent
		{
			newLeafNode(otherlv_1, grammarAccess.getLineStartAccess().getCircumflexAccentKeyword_1());
		}
	)
;

// Entry rule entryRuleLineEnd
entryRuleLineEnd returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getLineEndRule()); }
	iv_ruleLineEnd=ruleLineEnd
	{ $current=$iv_ruleLineEnd.current; }
	EOF;

// Rule LineEnd
ruleLineEnd returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getLineEndAccess().getLineEndAction_0(),
					$current);
			}
		)
		otherlv_1=DollarSign
		{
			newLeafNode(otherlv_1, grammarAccess.getLineEndAccess().getDollarSignKeyword_1());
		}
	)
;

// Entry rule entryRuleWordBoundary
entryRuleWordBoundary returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getWordBoundaryRule()); }
	iv_ruleWordBoundary=ruleWordBoundary
	{ $current=$iv_ruleWordBoundary.current; }
	EOF;

// Rule WordBoundary
ruleWordBoundary returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getWordBoundaryAccess().getWordBoundaryAction_0(),
					$current);
			}
		)
		(
			this_WORD_BOUNDARY_1=RULE_WORD_BOUNDARY
			{
				newLeafNode(this_WORD_BOUNDARY_1, grammarAccess.getWordBoundaryAccess().getWORD_BOUNDARYTerminalRuleCall_1_0());
			}
			    |
			(
				(
					lv_not_2_0=RULE_NOT_WORD_BOUNDARY
					{
						newLeafNode(lv_not_2_0, grammarAccess.getWordBoundaryAccess().getNotNOT_WORD_BOUNDARYTerminalRuleCall_1_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getWordBoundaryRule());
						}
						setWithLastConsumed(
							$current,
							"not",
							true,
							"org.eclipse.n4js.regex.RegularExpression.NOT_WORD_BOUNDARY");
					}
				)
			)
		)
	)
;

// Entry rule entryRuleAbstractLookAhead
entryRuleAbstractLookAhead returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAbstractLookAheadRule()); }
	iv_ruleAbstractLookAhead=ruleAbstractLookAhead
	{ $current=$iv_ruleAbstractLookAhead.current; }
	EOF;

// Rule AbstractLookAhead
ruleAbstractLookAhead returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getAbstractLookAheadAccess().getLookAheadAction_0_0_0(),
							$current);
					}
				)
				(
					otherlv_1=LeftParenthesisQuestionMarkEqualsSign
					{
						newLeafNode(otherlv_1, grammarAccess.getAbstractLookAheadAccess().getLeftParenthesisQuestionMarkEqualsSignKeyword_0_0_1_0());
					}
					    |
					(
						(
							lv_not_2_0=LeftParenthesisQuestionMarkExclamationMark
							{
								newLeafNode(lv_not_2_0, grammarAccess.getAbstractLookAheadAccess().getNotLeftParenthesisQuestionMarkExclamationMarkKeyword_0_0_1_1_0());
							}
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getAbstractLookAheadRule());
								}
								setWithLastConsumed($current, "not", true, "(?!");
							}
						)
					)
				)
			)
			    |
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getAbstractLookAheadAccess().getLookBehindAction_0_1_0(),
							$current);
					}
				)
				(
					otherlv_4=LeftParenthesisQuestionMarkLessThanSignEqualsSign
					{
						newLeafNode(otherlv_4, grammarAccess.getAbstractLookAheadAccess().getLeftParenthesisQuestionMarkLessThanSignEqualsSignKeyword_0_1_1_0());
					}
					    |
					(
						(
							lv_not_5_0=LeftParenthesisQuestionMarkLessThanSignExclamationMark
							{
								newLeafNode(lv_not_5_0, grammarAccess.getAbstractLookAheadAccess().getNotLeftParenthesisQuestionMarkLessThanSignExclamationMarkKeyword_0_1_1_1_0());
							}
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getAbstractLookAheadRule());
								}
								setWithLastConsumed($current, "not", true, "(?<!");
							}
						)
					)
				)
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getAbstractLookAheadAccess().getPatternDisjunctionParserRuleCall_1_0());
				}
				lv_pattern_6_0=ruleDisjunction
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getAbstractLookAheadRule());
					}
					set(
						$current,
						"pattern",
						lv_pattern_6_0,
						"org.eclipse.n4js.regex.RegularExpression.Disjunction");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_7=RightParenthesis
		{
			newLeafNode(otherlv_7, grammarAccess.getAbstractLookAheadAccess().getRightParenthesisKeyword_2());
		}
	)
;

// Entry rule entryRuleAtom
entryRuleAtom returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAtomRule()); }
	iv_ruleAtom=ruleAtom
	{ $current=$iv_ruleAtom.current; }
	EOF;

// Rule Atom
ruleAtom returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getAtomAccess().getPatternCharacterParserRuleCall_0());
		}
		this_PatternCharacter_0=rulePatternCharacter
		{
			$current = $this_PatternCharacter_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAtomAccess().getWildcardParserRuleCall_1());
		}
		this_Wildcard_1=ruleWildcard
		{
			$current = $this_Wildcard_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAtomAccess().getAtomEscapeParserRuleCall_2());
		}
		this_AtomEscape_2=ruleAtomEscape
		{
			$current = $this_AtomEscape_2.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAtomAccess().getCharacterClassParserRuleCall_3());
		}
		this_CharacterClass_3=ruleCharacterClass
		{
			$current = $this_CharacterClass_3.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAtomAccess().getGroupParserRuleCall_4());
		}
		this_Group_4=ruleGroup
		{
			$current = $this_Group_4.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRulePatternCharacter
entryRulePatternCharacter returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPatternCharacterRule()); }
	iv_rulePatternCharacter=rulePatternCharacter
	{ $current=$iv_rulePatternCharacter.current; }
	EOF;

// Rule PatternCharacter
rulePatternCharacter returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_value_0_1=RULE_PATTERN_CHARACTER_NO_DASH
				{
					newLeafNode(lv_value_0_1, grammarAccess.getPatternCharacterAccess().getValuePATTERN_CHARACTER_NO_DASHTerminalRuleCall_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPatternCharacterRule());
					}
					setWithLastConsumed(
						$current,
						"value",
						lv_value_0_1,
						"org.eclipse.n4js.regex.RegularExpression.PATTERN_CHARACTER_NO_DASH");
				}
				    |
				lv_value_0_2=RULE_UNICODE_LETTER
				{
					newLeafNode(lv_value_0_2, grammarAccess.getPatternCharacterAccess().getValueUNICODE_LETTERTerminalRuleCall_0_1());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPatternCharacterRule());
					}
					setWithLastConsumed(
						$current,
						"value",
						lv_value_0_2,
						"org.eclipse.n4js.regex.RegularExpression.UNICODE_LETTER");
				}
				    |
				lv_value_0_3=RULE_UNICODE_DIGIT
				{
					newLeafNode(lv_value_0_3, grammarAccess.getPatternCharacterAccess().getValueUNICODE_DIGITTerminalRuleCall_0_2());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPatternCharacterRule());
					}
					setWithLastConsumed(
						$current,
						"value",
						lv_value_0_3,
						"org.eclipse.n4js.regex.RegularExpression.UNICODE_DIGIT");
				}
				    |
				lv_value_0_4=HyphenMinus
				{
					newLeafNode(lv_value_0_4, grammarAccess.getPatternCharacterAccess().getValueHyphenMinusKeyword_0_3());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPatternCharacterRule());
					}
					setWithLastConsumed($current, "value", lv_value_0_4, null);
				}
				    |
				lv_value_0_5=Comma
				{
					newLeafNode(lv_value_0_5, grammarAccess.getPatternCharacterAccess().getValueCommaKeyword_0_4());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPatternCharacterRule());
					}
					setWithLastConsumed($current, "value", lv_value_0_5, null);
				}
				    |
				lv_value_0_6=EqualsSign
				{
					newLeafNode(lv_value_0_6, grammarAccess.getPatternCharacterAccess().getValueEqualsSignKeyword_0_5());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPatternCharacterRule());
					}
					setWithLastConsumed($current, "value", lv_value_0_6, null);
				}
				    |
				lv_value_0_7=Colon
				{
					newLeafNode(lv_value_0_7, grammarAccess.getPatternCharacterAccess().getValueColonKeyword_0_6());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPatternCharacterRule());
					}
					setWithLastConsumed($current, "value", lv_value_0_7, null);
				}
				    |
				lv_value_0_8=ExclamationMark
				{
					newLeafNode(lv_value_0_8, grammarAccess.getPatternCharacterAccess().getValueExclamationMarkKeyword_0_7());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPatternCharacterRule());
					}
					setWithLastConsumed($current, "value", lv_value_0_8, null);
				}
				    |
				lv_value_0_9=LeftCurlyBracket
				{
					newLeafNode(lv_value_0_9, grammarAccess.getPatternCharacterAccess().getValueLeftCurlyBracketKeyword_0_8());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPatternCharacterRule());
					}
					setWithLastConsumed($current, "value", lv_value_0_9, null);
				}
				    |
				lv_value_0_10=RightCurlyBracket
				{
					newLeafNode(lv_value_0_10, grammarAccess.getPatternCharacterAccess().getValueRightCurlyBracketKeyword_0_9());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPatternCharacterRule());
					}
					setWithLastConsumed($current, "value", lv_value_0_10, null);
				}
				    |
				lv_value_0_11=RightSquareBracket
				{
					newLeafNode(lv_value_0_11, grammarAccess.getPatternCharacterAccess().getValueRightSquareBracketKeyword_0_10());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPatternCharacterRule());
					}
					setWithLastConsumed($current, "value", lv_value_0_11, null);
				}
				    |
				lv_value_0_12=LessThanSign
				{
					newLeafNode(lv_value_0_12, grammarAccess.getPatternCharacterAccess().getValueLessThanSignKeyword_0_11());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPatternCharacterRule());
					}
					setWithLastConsumed($current, "value", lv_value_0_12, null);
				}
				    |
				lv_value_0_13=GreaterThanSign
				{
					newLeafNode(lv_value_0_13, grammarAccess.getPatternCharacterAccess().getValueGreaterThanSignKeyword_0_12());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPatternCharacterRule());
					}
					setWithLastConsumed($current, "value", lv_value_0_13, null);
				}
			)
		)
	)
;

// Entry rule entryRuleWildcard
entryRuleWildcard returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getWildcardRule()); }
	iv_ruleWildcard=ruleWildcard
	{ $current=$iv_ruleWildcard.current; }
	EOF;

// Rule Wildcard
ruleWildcard returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getWildcardAccess().getWildcardAction_0(),
					$current);
			}
		)
		otherlv_1=FullStop
		{
			newLeafNode(otherlv_1, grammarAccess.getWildcardAccess().getFullStopKeyword_1());
		}
	)
;

// Entry rule entryRuleAtomEscape
entryRuleAtomEscape returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAtomEscapeRule()); }
	iv_ruleAtomEscape=ruleAtomEscape
	{ $current=$iv_ruleAtomEscape.current; }
	EOF;

// Rule AtomEscape
ruleAtomEscape returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getAtomEscapeAccess().getDecimalEscapeSequenceParserRuleCall_0());
		}
		this_DecimalEscapeSequence_0=ruleDecimalEscapeSequence
		{
			$current = $this_DecimalEscapeSequence_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAtomEscapeAccess().getCharacterEscapeSequenceParserRuleCall_1());
		}
		this_CharacterEscapeSequence_1=ruleCharacterEscapeSequence
		{
			$current = $this_CharacterEscapeSequence_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAtomEscapeAccess().getControlLetterEscapeSequenceParserRuleCall_2());
		}
		this_ControlLetterEscapeSequence_2=ruleControlLetterEscapeSequence
		{
			$current = $this_ControlLetterEscapeSequence_2.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAtomEscapeAccess().getHexEscapeSequenceParserRuleCall_3());
		}
		this_HexEscapeSequence_3=ruleHexEscapeSequence
		{
			$current = $this_HexEscapeSequence_3.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAtomEscapeAccess().getUnicodeEscapeSequenceParserRuleCall_4());
		}
		this_UnicodeEscapeSequence_4=ruleUnicodeEscapeSequence
		{
			$current = $this_UnicodeEscapeSequence_4.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAtomEscapeAccess().getIdentityEscapeSequenceParserRuleCall_5());
		}
		this_IdentityEscapeSequence_5=ruleIdentityEscapeSequence
		{
			$current = $this_IdentityEscapeSequence_5.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAtomEscapeAccess().getCharacterClassEscapeSequenceParserRuleCall_6());
		}
		this_CharacterClassEscapeSequence_6=ruleCharacterClassEscapeSequence
		{
			$current = $this_CharacterClassEscapeSequence_6.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleCharacterClassEscapeSequence
entryRuleCharacterClassEscapeSequence returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getCharacterClassEscapeSequenceRule()); }
	iv_ruleCharacterClassEscapeSequence=ruleCharacterClassEscapeSequence
	{ $current=$iv_ruleCharacterClassEscapeSequence.current; }
	EOF;

// Rule CharacterClassEscapeSequence
ruleCharacterClassEscapeSequence returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_sequence_0_0=RULE_CHARACTER_CLASS_ESCAPE
			{
				newLeafNode(lv_sequence_0_0, grammarAccess.getCharacterClassEscapeSequenceAccess().getSequenceCHARACTER_CLASS_ESCAPETerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getCharacterClassEscapeSequenceRule());
				}
				setWithLastConsumed(
					$current,
					"sequence",
					lv_sequence_0_0,
					"org.eclipse.n4js.regex.RegularExpression.CHARACTER_CLASS_ESCAPE");
			}
		)
	)
;

// Entry rule entryRuleCharacterEscapeSequence
entryRuleCharacterEscapeSequence returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getCharacterEscapeSequenceRule()); }
	iv_ruleCharacterEscapeSequence=ruleCharacterEscapeSequence
	{ $current=$iv_ruleCharacterEscapeSequence.current; }
	EOF;

// Rule CharacterEscapeSequence
ruleCharacterEscapeSequence returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_sequence_0_0=RULE_CONTROL_ESCAPE
			{
				newLeafNode(lv_sequence_0_0, grammarAccess.getCharacterEscapeSequenceAccess().getSequenceCONTROL_ESCAPETerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getCharacterEscapeSequenceRule());
				}
				setWithLastConsumed(
					$current,
					"sequence",
					lv_sequence_0_0,
					"org.eclipse.n4js.regex.RegularExpression.CONTROL_ESCAPE");
			}
		)
	)
;

// Entry rule entryRuleControlLetterEscapeSequence
entryRuleControlLetterEscapeSequence returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getControlLetterEscapeSequenceRule()); }
	iv_ruleControlLetterEscapeSequence=ruleControlLetterEscapeSequence
	{ $current=$iv_ruleControlLetterEscapeSequence.current; }
	EOF;

// Rule ControlLetterEscapeSequence
ruleControlLetterEscapeSequence returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_sequence_0_0=RULE_CONTROL_LETTER_ESCAPE
			{
				newLeafNode(lv_sequence_0_0, grammarAccess.getControlLetterEscapeSequenceAccess().getSequenceCONTROL_LETTER_ESCAPETerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getControlLetterEscapeSequenceRule());
				}
				setWithLastConsumed(
					$current,
					"sequence",
					lv_sequence_0_0,
					"org.eclipse.n4js.regex.RegularExpression.CONTROL_LETTER_ESCAPE");
			}
		)
	)
;

// Entry rule entryRuleHexEscapeSequence
entryRuleHexEscapeSequence returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getHexEscapeSequenceRule()); }
	iv_ruleHexEscapeSequence=ruleHexEscapeSequence
	{ $current=$iv_ruleHexEscapeSequence.current; }
	EOF;

// Rule HexEscapeSequence
ruleHexEscapeSequence returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_sequence_0_0=RULE_HEX_ESCAPE
			{
				newLeafNode(lv_sequence_0_0, grammarAccess.getHexEscapeSequenceAccess().getSequenceHEX_ESCAPETerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getHexEscapeSequenceRule());
				}
				setWithLastConsumed(
					$current,
					"sequence",
					lv_sequence_0_0,
					"org.eclipse.n4js.regex.RegularExpression.HEX_ESCAPE");
			}
		)
	)
;

// Entry rule entryRuleUnicodeEscapeSequence
entryRuleUnicodeEscapeSequence returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getUnicodeEscapeSequenceRule()); }
	iv_ruleUnicodeEscapeSequence=ruleUnicodeEscapeSequence
	{ $current=$iv_ruleUnicodeEscapeSequence.current; }
	EOF;

// Rule UnicodeEscapeSequence
ruleUnicodeEscapeSequence returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_sequence_0_0=RULE_UNICODE_ESCAPE
			{
				newLeafNode(lv_sequence_0_0, grammarAccess.getUnicodeEscapeSequenceAccess().getSequenceUNICODE_ESCAPETerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getUnicodeEscapeSequenceRule());
				}
				setWithLastConsumed(
					$current,
					"sequence",
					lv_sequence_0_0,
					"org.eclipse.n4js.regex.RegularExpression.UNICODE_ESCAPE");
			}
		)
	)
;

// Entry rule entryRuleIdentityEscapeSequence
entryRuleIdentityEscapeSequence returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getIdentityEscapeSequenceRule()); }
	iv_ruleIdentityEscapeSequence=ruleIdentityEscapeSequence
	{ $current=$iv_ruleIdentityEscapeSequence.current; }
	EOF;

// Rule IdentityEscapeSequence
ruleIdentityEscapeSequence returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_sequence_0_0=RULE_IDENTITY_ESCAPE
			{
				newLeafNode(lv_sequence_0_0, grammarAccess.getIdentityEscapeSequenceAccess().getSequenceIDENTITY_ESCAPETerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getIdentityEscapeSequenceRule());
				}
				setWithLastConsumed(
					$current,
					"sequence",
					lv_sequence_0_0,
					"org.eclipse.n4js.regex.RegularExpression.IDENTITY_ESCAPE");
			}
		)
	)
;

// Entry rule entryRuleDecimalEscapeSequence
entryRuleDecimalEscapeSequence returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getDecimalEscapeSequenceRule()); }
	iv_ruleDecimalEscapeSequence=ruleDecimalEscapeSequence
	{ $current=$iv_ruleDecimalEscapeSequence.current; }
	EOF;

// Rule DecimalEscapeSequence
ruleDecimalEscapeSequence returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_sequence_0_0=RULE_DECIMAL_ESCAPE
			{
				newLeafNode(lv_sequence_0_0, grammarAccess.getDecimalEscapeSequenceAccess().getSequenceDECIMAL_ESCAPETerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getDecimalEscapeSequenceRule());
				}
				setWithLastConsumed(
					$current,
					"sequence",
					lv_sequence_0_0,
					"org.eclipse.n4js.regex.RegularExpression.DECIMAL_ESCAPE");
			}
		)
	)
;

// Entry rule entryRuleCharacterClass
entryRuleCharacterClass returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getCharacterClassRule()); }
	iv_ruleCharacterClass=ruleCharacterClass
	{ $current=$iv_ruleCharacterClass.current; }
	EOF;

// Rule CharacterClass
ruleCharacterClass returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getCharacterClassAccess().getCharacterClassAction_0(),
					$current);
			}
		)
		otherlv_1=LeftSquareBracket
		{
			newLeafNode(otherlv_1, grammarAccess.getCharacterClassAccess().getLeftSquareBracketKeyword_1());
		}
		(
			((
				(
					CircumflexAccent
				)
			)
			)=>
			(
				(
					lv_negated_2_0=CircumflexAccent
					{
						newLeafNode(lv_negated_2_0, grammarAccess.getCharacterClassAccess().getNegatedCircumflexAccentKeyword_2_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassRule());
						}
						setWithLastConsumed($current, "negated", true, "^");
					}
				)
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getCharacterClassAccess().getElementsCharacterClassElementParserRuleCall_3_0());
				}
				lv_elements_3_0=ruleCharacterClassElement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getCharacterClassRule());
					}
					add(
						$current,
						"elements",
						lv_elements_3_0,
						"org.eclipse.n4js.regex.RegularExpression.CharacterClassElement");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_4=RightSquareBracket
		{
			newLeafNode(otherlv_4, grammarAccess.getCharacterClassAccess().getRightSquareBracketKeyword_4());
		}
	)
;

// Entry rule entryRuleCharacterClassElement
entryRuleCharacterClassElement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getCharacterClassElementRule()); }
	iv_ruleCharacterClassElement=ruleCharacterClassElement
	{ $current=$iv_ruleCharacterClassElement.current; }
	EOF;

// Rule CharacterClassElement
ruleCharacterClassElement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getCharacterClassElementAccess().getCharacterClassAtomParserRuleCall_0());
		}
		this_CharacterClassAtom_0=ruleCharacterClassAtom
		{
			$current = $this_CharacterClassAtom_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			((
				(
				)
				HyphenMinus
				(
					(
						ruleCharacterClassAtom
					)
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getCharacterClassElementAccess().getCharacterClassRangeLeftAction_1_0_0(),
							$current);
					}
				)
				otherlv_2=HyphenMinus
				{
					newLeafNode(otherlv_2, grammarAccess.getCharacterClassElementAccess().getHyphenMinusKeyword_1_0_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getCharacterClassElementAccess().getRightCharacterClassAtomParserRuleCall_1_0_2_0());
						}
						lv_right_3_0=ruleCharacterClassAtom
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getCharacterClassElementRule());
							}
							set(
								$current,
								"right",
								lv_right_3_0,
								"org.eclipse.n4js.regex.RegularExpression.CharacterClassAtom");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)?
	)
;

// Entry rule entryRuleCharacterClassAtom
entryRuleCharacterClassAtom returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getCharacterClassAtomRule()); }
	iv_ruleCharacterClassAtom=ruleCharacterClassAtom
	{ $current=$iv_ruleCharacterClassAtom.current; }
	EOF;

// Rule CharacterClassAtom
ruleCharacterClassAtom returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getCharacterClassAtomAccess().getEscapedCharacterClassAtomParserRuleCall_0());
		}
		this_EscapedCharacterClassAtom_0=ruleEscapedCharacterClassAtom
		{
			$current = $this_EscapedCharacterClassAtom_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			(
				(
					lv_characters_1_1=Comma
					{
						newLeafNode(lv_characters_1_1, grammarAccess.getCharacterClassAtomAccess().getCharactersCommaKeyword_1_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_1, null);
					}
					    |
					lv_characters_1_2=EqualsSign
					{
						newLeafNode(lv_characters_1_2, grammarAccess.getCharacterClassAtomAccess().getCharactersEqualsSignKeyword_1_0_1());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_2, null);
					}
					    |
					lv_characters_1_3=Colon
					{
						newLeafNode(lv_characters_1_3, grammarAccess.getCharacterClassAtomAccess().getCharactersColonKeyword_1_0_2());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_3, null);
					}
					    |
					lv_characters_1_4=ExclamationMark
					{
						newLeafNode(lv_characters_1_4, grammarAccess.getCharacterClassAtomAccess().getCharactersExclamationMarkKeyword_1_0_3());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_4, null);
					}
					    |
					lv_characters_1_5=HyphenMinus
					{
						newLeafNode(lv_characters_1_5, grammarAccess.getCharacterClassAtomAccess().getCharactersHyphenMinusKeyword_1_0_4());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_5, null);
					}
					    |
					lv_characters_1_6=CircumflexAccent
					{
						newLeafNode(lv_characters_1_6, grammarAccess.getCharacterClassAtomAccess().getCharactersCircumflexAccentKeyword_1_0_5());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_6, null);
					}
					    |
					lv_characters_1_7=DollarSign
					{
						newLeafNode(lv_characters_1_7, grammarAccess.getCharacterClassAtomAccess().getCharactersDollarSignKeyword_1_0_6());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_7, null);
					}
					    |
					lv_characters_1_8=FullStop
					{
						newLeafNode(lv_characters_1_8, grammarAccess.getCharacterClassAtomAccess().getCharactersFullStopKeyword_1_0_7());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_8, null);
					}
					    |
					lv_characters_1_9=Asterisk
					{
						newLeafNode(lv_characters_1_9, grammarAccess.getCharacterClassAtomAccess().getCharactersAsteriskKeyword_1_0_8());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_9, null);
					}
					    |
					lv_characters_1_10=PlusSign
					{
						newLeafNode(lv_characters_1_10, grammarAccess.getCharacterClassAtomAccess().getCharactersPlusSignKeyword_1_0_9());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_10, null);
					}
					    |
					lv_characters_1_11=QuestionMark
					{
						newLeafNode(lv_characters_1_11, grammarAccess.getCharacterClassAtomAccess().getCharactersQuestionMarkKeyword_1_0_10());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_11, null);
					}
					    |
					lv_characters_1_12=LeftParenthesis
					{
						newLeafNode(lv_characters_1_12, grammarAccess.getCharacterClassAtomAccess().getCharactersLeftParenthesisKeyword_1_0_11());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_12, null);
					}
					    |
					lv_characters_1_13=RightParenthesis
					{
						newLeafNode(lv_characters_1_13, grammarAccess.getCharacterClassAtomAccess().getCharactersRightParenthesisKeyword_1_0_12());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_13, null);
					}
					    |
					lv_characters_1_14=LeftSquareBracket
					{
						newLeafNode(lv_characters_1_14, grammarAccess.getCharacterClassAtomAccess().getCharactersLeftSquareBracketKeyword_1_0_13());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_14, null);
					}
					    |
					lv_characters_1_15=LeftCurlyBracket
					{
						newLeafNode(lv_characters_1_15, grammarAccess.getCharacterClassAtomAccess().getCharactersLeftCurlyBracketKeyword_1_0_14());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_15, null);
					}
					    |
					lv_characters_1_16=RightCurlyBracket
					{
						newLeafNode(lv_characters_1_16, grammarAccess.getCharacterClassAtomAccess().getCharactersRightCurlyBracketKeyword_1_0_15());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_16, null);
					}
					    |
					lv_characters_1_17=VerticalLine
					{
						newLeafNode(lv_characters_1_17, grammarAccess.getCharacterClassAtomAccess().getCharactersVerticalLineKeyword_1_0_16());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_17, null);
					}
					    |
					lv_characters_1_18=Solidus
					{
						newLeafNode(lv_characters_1_18, grammarAccess.getCharacterClassAtomAccess().getCharactersSolidusKeyword_1_0_17());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_18, null);
					}
					    |
					lv_characters_1_19=LessThanSign
					{
						newLeafNode(lv_characters_1_19, grammarAccess.getCharacterClassAtomAccess().getCharactersLessThanSignKeyword_1_0_18());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_19, null);
					}
					    |
					lv_characters_1_20=GreaterThanSign
					{
						newLeafNode(lv_characters_1_20, grammarAccess.getCharacterClassAtomAccess().getCharactersGreaterThanSignKeyword_1_0_19());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_20, null);
					}
					    |
					lv_characters_1_21=LeftParenthesisQuestionMark
					{
						newLeafNode(lv_characters_1_21, grammarAccess.getCharacterClassAtomAccess().getCharactersLeftParenthesisQuestionMarkKeyword_1_0_20());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_21, null);
					}
					    |
					lv_characters_1_22=LeftParenthesisQuestionMarkLessThanSign
					{
						newLeafNode(lv_characters_1_22, grammarAccess.getCharacterClassAtomAccess().getCharactersLeftParenthesisQuestionMarkLessThanSignKeyword_1_0_21());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_22, null);
					}
					    |
					lv_characters_1_23=LeftParenthesisQuestionMarkEqualsSign
					{
						newLeafNode(lv_characters_1_23, grammarAccess.getCharacterClassAtomAccess().getCharactersLeftParenthesisQuestionMarkEqualsSignKeyword_1_0_22());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_23, null);
					}
					    |
					lv_characters_1_24=LeftParenthesisQuestionMarkExclamationMark
					{
						newLeafNode(lv_characters_1_24, grammarAccess.getCharacterClassAtomAccess().getCharactersLeftParenthesisQuestionMarkExclamationMarkKeyword_1_0_23());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_24, null);
					}
					    |
					lv_characters_1_25=LeftParenthesisQuestionMarkLessThanSignExclamationMark
					{
						newLeafNode(lv_characters_1_25, grammarAccess.getCharacterClassAtomAccess().getCharactersLeftParenthesisQuestionMarkLessThanSignExclamationMarkKeyword_1_0_24());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_25, null);
					}
					    |
					lv_characters_1_26=LeftParenthesisQuestionMarkLessThanSignEqualsSign
					{
						newLeafNode(lv_characters_1_26, grammarAccess.getCharacterClassAtomAccess().getCharactersLeftParenthesisQuestionMarkLessThanSignEqualsSignKeyword_1_0_25());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed($current, "characters", lv_characters_1_26, null);
					}
					    |
					lv_characters_1_27=RULE_PATTERN_CHARACTER_NO_DASH
					{
						newLeafNode(lv_characters_1_27, grammarAccess.getCharacterClassAtomAccess().getCharactersPATTERN_CHARACTER_NO_DASHTerminalRuleCall_1_0_26());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed(
							$current,
							"characters",
							lv_characters_1_27,
							"org.eclipse.n4js.regex.RegularExpression.PATTERN_CHARACTER_NO_DASH");
					}
					    |
					lv_characters_1_28=RULE_UNICODE_LETTER
					{
						newLeafNode(lv_characters_1_28, grammarAccess.getCharacterClassAtomAccess().getCharactersUNICODE_LETTERTerminalRuleCall_1_0_27());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed(
							$current,
							"characters",
							lv_characters_1_28,
							"org.eclipse.n4js.regex.RegularExpression.UNICODE_LETTER");
					}
					    |
					lv_characters_1_29=RULE_UNICODE_DIGIT
					{
						newLeafNode(lv_characters_1_29, grammarAccess.getCharacterClassAtomAccess().getCharactersUNICODE_DIGITTerminalRuleCall_1_0_28());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCharacterClassAtomRule());
						}
						setWithLastConsumed(
							$current,
							"characters",
							lv_characters_1_29,
							"org.eclipse.n4js.regex.RegularExpression.UNICODE_DIGIT");
					}
				)
			)
		)
	)
;

// Entry rule entryRuleEscapedCharacterClassAtom
entryRuleEscapedCharacterClassAtom returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getEscapedCharacterClassAtomRule()); }
	iv_ruleEscapedCharacterClassAtom=ruleEscapedCharacterClassAtom
	{ $current=$iv_ruleEscapedCharacterClassAtom.current; }
	EOF;

// Rule EscapedCharacterClassAtom
ruleEscapedCharacterClassAtom returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getEscapedCharacterClassAtomAccess().getDecimalEscapeSequenceParserRuleCall_0());
		}
		this_DecimalEscapeSequence_0=ruleDecimalEscapeSequence
		{
			$current = $this_DecimalEscapeSequence_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getEscapedCharacterClassAtomAccess().getBackspaceParserRuleCall_1());
		}
		this_Backspace_1=ruleBackspace
		{
			$current = $this_Backspace_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getEscapedCharacterClassAtomAccess().getCharacterEscapeSequenceParserRuleCall_2());
		}
		this_CharacterEscapeSequence_2=ruleCharacterEscapeSequence
		{
			$current = $this_CharacterEscapeSequence_2.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getEscapedCharacterClassAtomAccess().getControlLetterEscapeSequenceParserRuleCall_3());
		}
		this_ControlLetterEscapeSequence_3=ruleControlLetterEscapeSequence
		{
			$current = $this_ControlLetterEscapeSequence_3.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getEscapedCharacterClassAtomAccess().getHexEscapeSequenceParserRuleCall_4());
		}
		this_HexEscapeSequence_4=ruleHexEscapeSequence
		{
			$current = $this_HexEscapeSequence_4.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getEscapedCharacterClassAtomAccess().getUnicodeEscapeSequenceParserRuleCall_5());
		}
		this_UnicodeEscapeSequence_5=ruleUnicodeEscapeSequence
		{
			$current = $this_UnicodeEscapeSequence_5.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getEscapedCharacterClassAtomAccess().getIdentityEscapeSequenceParserRuleCall_6());
		}
		this_IdentityEscapeSequence_6=ruleIdentityEscapeSequence
		{
			$current = $this_IdentityEscapeSequence_6.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getEscapedCharacterClassAtomAccess().getCharacterClassEscapeSequenceParserRuleCall_7());
		}
		this_CharacterClassEscapeSequence_7=ruleCharacterClassEscapeSequence
		{
			$current = $this_CharacterClassEscapeSequence_7.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleBackspace
entryRuleBackspace returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getBackspaceRule()); }
	iv_ruleBackspace=ruleBackspace
	{ $current=$iv_ruleBackspace.current; }
	EOF;

// Rule Backspace
ruleBackspace returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getBackspaceAccess().getBackspaceAction_0(),
					$current);
			}
		)
		this_WORD_BOUNDARY_1=RULE_WORD_BOUNDARY
		{
			newLeafNode(this_WORD_BOUNDARY_1, grammarAccess.getBackspaceAccess().getWORD_BOUNDARYTerminalRuleCall_1());
		}
	)
;

// Entry rule entryRuleGroup
entryRuleGroup returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getGroupRule()); }
	iv_ruleGroup=ruleGroup
	{ $current=$iv_ruleGroup.current; }
	EOF;

// Rule Group
ruleGroup returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getGroupAccess().getGroupAction_0(),
					$current);
			}
		)
		(
			otherlv_1=LeftParenthesis
			{
				newLeafNode(otherlv_1, grammarAccess.getGroupAccess().getLeftParenthesisKeyword_1_0());
			}
			    |
			(
				(
					lv_nonCapturing_2_0=LeftParenthesisQuestionMarkColon
					{
						newLeafNode(lv_nonCapturing_2_0, grammarAccess.getGroupAccess().getNonCapturingLeftParenthesisQuestionMarkColonKeyword_1_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getGroupRule());
						}
						setWithLastConsumed($current, "nonCapturing", true, "(?:");
					}
				)
			)
			    |
			(
				(
					(
						lv_named_3_0=LeftParenthesisQuestionMarkLessThanSign
						{
							newLeafNode(lv_named_3_0, grammarAccess.getGroupAccess().getNamedLeftParenthesisQuestionMarkLessThanSignKeyword_1_2_0_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getGroupRule());
							}
							setWithLastConsumed($current, "named", true, "(?<");
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getGroupAccess().getNameRegExpIdentifierNameParserRuleCall_1_2_1_0());
						}
						lv_name_4_0=ruleRegExpIdentifierName
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getGroupRule());
							}
							set(
								$current,
								"name",
								lv_name_4_0,
								"org.eclipse.n4js.regex.RegularExpression.RegExpIdentifierName");
							afterParserOrEnumRuleCall();
						}
					)
				)
				otherlv_5=GreaterThanSign
				{
					newLeafNode(otherlv_5, grammarAccess.getGroupAccess().getGreaterThanSignKeyword_1_2_2());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getGroupAccess().getPatternDisjunctionParserRuleCall_2_0());
				}
				lv_pattern_6_0=ruleDisjunction
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getGroupRule());
					}
					set(
						$current,
						"pattern",
						lv_pattern_6_0,
						"org.eclipse.n4js.regex.RegularExpression.Disjunction");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_7=RightParenthesis
		{
			newLeafNode(otherlv_7, grammarAccess.getGroupAccess().getRightParenthesisKeyword_3());
		}
	)
;

// Entry rule entryRuleRegExpIdentifierName
entryRuleRegExpIdentifierName returns [String current=null]:
	{ newCompositeNode(grammarAccess.getRegExpIdentifierNameRule()); }
	iv_ruleRegExpIdentifierName=ruleRegExpIdentifierName
	{ $current=$iv_ruleRegExpIdentifierName.current.getText(); }
	EOF;

// Rule RegExpIdentifierName
ruleRegExpIdentifierName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getRegExpIdentifierNameAccess().getRegExpIdentifierStartParserRuleCall_0());
		}
		this_RegExpIdentifierStart_0=ruleRegExpIdentifierStart
		{
			$current.merge(this_RegExpIdentifierStart_0);
		}
		{
			afterParserOrEnumRuleCall();
		}
		(
			{
				newCompositeNode(grammarAccess.getRegExpIdentifierNameAccess().getRegExpIdentifierPartParserRuleCall_1());
			}
			this_RegExpIdentifierPart_1=ruleRegExpIdentifierPart
			{
				$current.merge(this_RegExpIdentifierPart_1);
			}
			{
				afterParserOrEnumRuleCall();
			}
		)*
	)
;

// Entry rule entryRuleRegExpIdentifierStart
entryRuleRegExpIdentifierStart returns [String current=null]:
	{ newCompositeNode(grammarAccess.getRegExpIdentifierStartRule()); }
	iv_ruleRegExpIdentifierStart=ruleRegExpIdentifierStart
	{ $current=$iv_ruleRegExpIdentifierStart.current.getText(); }
	EOF;

// Rule RegExpIdentifierStart
ruleRegExpIdentifierStart returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_UNICODE_LETTER_0=RULE_UNICODE_LETTER
		{
			$current.merge(this_UNICODE_LETTER_0);
		}
		{
			newLeafNode(this_UNICODE_LETTER_0, grammarAccess.getRegExpIdentifierStartAccess().getUNICODE_LETTERTerminalRuleCall_0());
		}
		    |
		kw=DollarSign
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getRegExpIdentifierStartAccess().getDollarSignKeyword_1());
		}
		    |
		kw=KW__
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getRegExpIdentifierStartAccess().get_Keyword_2());
		}
		    |
		this_UNICODE_ESCAPE_3=RULE_UNICODE_ESCAPE
		{
			$current.merge(this_UNICODE_ESCAPE_3);
		}
		{
			newLeafNode(this_UNICODE_ESCAPE_3, grammarAccess.getRegExpIdentifierStartAccess().getUNICODE_ESCAPETerminalRuleCall_3());
		}
	)
;

// Entry rule entryRuleRegExpIdentifierPart
entryRuleRegExpIdentifierPart returns [String current=null]:
	{ newCompositeNode(grammarAccess.getRegExpIdentifierPartRule()); }
	iv_ruleRegExpIdentifierPart=ruleRegExpIdentifierPart
	{ $current=$iv_ruleRegExpIdentifierPart.current.getText(); }
	EOF;

// Rule RegExpIdentifierPart
ruleRegExpIdentifierPart returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_UNICODE_LETTER_0=RULE_UNICODE_LETTER
		{
			$current.merge(this_UNICODE_LETTER_0);
		}
		{
			newLeafNode(this_UNICODE_LETTER_0, grammarAccess.getRegExpIdentifierPartAccess().getUNICODE_LETTERTerminalRuleCall_0());
		}
		    |
		this_UNICODE_DIGIT_1=RULE_UNICODE_DIGIT
		{
			$current.merge(this_UNICODE_DIGIT_1);
		}
		{
			newLeafNode(this_UNICODE_DIGIT_1, grammarAccess.getRegExpIdentifierPartAccess().getUNICODE_DIGITTerminalRuleCall_1());
		}
		    |
		kw=DollarSign
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getRegExpIdentifierPartAccess().getDollarSignKeyword_2());
		}
		    |
		kw=KW__
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getRegExpIdentifierPartAccess().get_Keyword_3());
		}
		    |
		this_UNICODE_ESCAPE_4=RULE_UNICODE_ESCAPE
		{
			$current.merge(this_UNICODE_ESCAPE_4);
		}
		{
			newLeafNode(this_UNICODE_ESCAPE_4, grammarAccess.getRegExpIdentifierPartAccess().getUNICODE_ESCAPETerminalRuleCall_4());
		}
	)
;

// Entry rule entryRuleQuantifier
entryRuleQuantifier returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getQuantifierRule()); }
	iv_ruleQuantifier=ruleQuantifier
	{ $current=$iv_ruleQuantifier.current; }
	EOF;

// Rule Quantifier
ruleQuantifier returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getQuantifierAccess().getSimpleQuantifierParserRuleCall_0());
		}
		this_SimpleQuantifier_0=ruleSimpleQuantifier
		{
			$current = $this_SimpleQuantifier_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getQuantifierAccess().getExactQuantifierParserRuleCall_1());
		}
		this_ExactQuantifier_1=ruleExactQuantifier
		{
			$current = $this_ExactQuantifier_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleSimpleQuantifier
entryRuleSimpleQuantifier returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSimpleQuantifierRule()); }
	iv_ruleSimpleQuantifier=ruleSimpleQuantifier
	{ $current=$iv_ruleSimpleQuantifier.current; }
	EOF;

// Rule SimpleQuantifier
ruleSimpleQuantifier returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				(
					lv_quantifier_0_1=PlusSign
					{
						newLeafNode(lv_quantifier_0_1, grammarAccess.getSimpleQuantifierAccess().getQuantifierPlusSignKeyword_0_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getSimpleQuantifierRule());
						}
						setWithLastConsumed($current, "quantifier", lv_quantifier_0_1, null);
					}
					    |
					lv_quantifier_0_2=Asterisk
					{
						newLeafNode(lv_quantifier_0_2, grammarAccess.getSimpleQuantifierAccess().getQuantifierAsteriskKeyword_0_0_1());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getSimpleQuantifierRule());
						}
						setWithLastConsumed($current, "quantifier", lv_quantifier_0_2, null);
					}
					    |
					lv_quantifier_0_3=QuestionMark
					{
						newLeafNode(lv_quantifier_0_3, grammarAccess.getSimpleQuantifierAccess().getQuantifierQuestionMarkKeyword_0_0_2());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getSimpleQuantifierRule());
						}
						setWithLastConsumed($current, "quantifier", lv_quantifier_0_3, null);
					}
				)
			)
		)
		(
			(
				lv_nonGreedy_1_0=QuestionMark
				{
					newLeafNode(lv_nonGreedy_1_0, grammarAccess.getSimpleQuantifierAccess().getNonGreedyQuestionMarkKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSimpleQuantifierRule());
					}
					setWithLastConsumed($current, "nonGreedy", true, "?");
				}
			)
		)?
	)
;

// Entry rule entryRuleExactQuantifier
entryRuleExactQuantifier returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getExactQuantifierRule()); }
	iv_ruleExactQuantifier=ruleExactQuantifier
	{ $current=$iv_ruleExactQuantifier.current; }
	EOF;

// Rule ExactQuantifier
ruleExactQuantifier returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getExactQuantifierAccess().getExactQuantifierAction_0(),
					$current);
			}
		)
		otherlv_1=LeftCurlyBracket
		{
			newLeafNode(otherlv_1, grammarAccess.getExactQuantifierAccess().getLeftCurlyBracketKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getExactQuantifierAccess().getMinINTParserRuleCall_2_0());
				}
				lv_min_2_0=ruleINT
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getExactQuantifierRule());
					}
					set(
						$current,
						"min",
						lv_min_2_0,
						"org.eclipse.n4js.regex.RegularExpression.INT");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				otherlv_3=Comma
				{
					newLeafNode(otherlv_3, grammarAccess.getExactQuantifierAccess().getCommaKeyword_3_0_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getExactQuantifierAccess().getMaxINTParserRuleCall_3_0_1_0());
						}
						lv_max_4_0=ruleINT
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getExactQuantifierRule());
							}
							set(
								$current,
								"max",
								lv_max_4_0,
								"org.eclipse.n4js.regex.RegularExpression.INT");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			    |
			(
				(
					lv_unboundedMax_5_0=Comma
					{
						newLeafNode(lv_unboundedMax_5_0, grammarAccess.getExactQuantifierAccess().getUnboundedMaxCommaKeyword_3_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getExactQuantifierRule());
						}
						setWithLastConsumed($current, "unboundedMax", true, ",");
					}
				)
			)
		)?
		otherlv_6=RightCurlyBracket
		{
			newLeafNode(otherlv_6, grammarAccess.getExactQuantifierAccess().getRightCurlyBracketKeyword_4());
		}
		(
			(
				lv_nonGreedy_7_0=QuestionMark
				{
					newLeafNode(lv_nonGreedy_7_0, grammarAccess.getExactQuantifierAccess().getNonGreedyQuestionMarkKeyword_5_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getExactQuantifierRule());
					}
					setWithLastConsumed($current, "nonGreedy", true, "?");
				}
			)
		)?
	)
;

// Entry rule entryRuleRegularExpressionFlags
entryRuleRegularExpressionFlags returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getRegularExpressionFlagsRule()); }
	iv_ruleRegularExpressionFlags=ruleRegularExpressionFlags
	{ $current=$iv_ruleRegularExpressionFlags.current; }
	EOF;

// Rule RegularExpressionFlags
ruleRegularExpressionFlags returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getRegularExpressionFlagsAccess().getRegularExpressionFlagsAction_0(),
					$current);
			}
		)
		(
			(
				(
					lv_flags_1_1=RULE_UNICODE_LETTER
					{
						newLeafNode(lv_flags_1_1, grammarAccess.getRegularExpressionFlagsAccess().getFlagsUNICODE_LETTERTerminalRuleCall_1_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getRegularExpressionFlagsRule());
						}
						addWithLastConsumed(
							$current,
							"flags",
							lv_flags_1_1,
							"org.eclipse.n4js.regex.RegularExpression.UNICODE_LETTER");
					}
					    |
					lv_flags_1_2=RULE_UNICODE_ESCAPE
					{
						newLeafNode(lv_flags_1_2, grammarAccess.getRegularExpressionFlagsAccess().getFlagsUNICODE_ESCAPETerminalRuleCall_1_0_1());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getRegularExpressionFlagsRule());
						}
						addWithLastConsumed(
							$current,
							"flags",
							lv_flags_1_2,
							"org.eclipse.n4js.regex.RegularExpression.UNICODE_ESCAPE");
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleINT
entryRuleINT returns [String current=null]:
	{ newCompositeNode(grammarAccess.getINTRule()); }
	iv_ruleINT=ruleINT
	{ $current=$iv_ruleINT.current.getText(); }
	EOF;

// Rule INT
ruleINT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_UNICODE_DIGIT_0=RULE_UNICODE_DIGIT
		{
			$current.merge(this_UNICODE_DIGIT_0);
		}
		{
			newLeafNode(this_UNICODE_DIGIT_0, grammarAccess.getINTAccess().getUNICODE_DIGITTerminalRuleCall());
		}
	)+
;
