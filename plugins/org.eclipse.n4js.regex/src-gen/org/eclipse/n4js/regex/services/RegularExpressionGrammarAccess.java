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
package org.eclipse.n4js.regex.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import org.eclipse.n4js.common.unicode.services.UnicodeGrammarAccess;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.service.AbstractElementFinder.AbstractGrammarElementFinder;
import org.eclipse.xtext.service.GrammarProvider;

@Singleton
public class RegularExpressionGrammarAccess extends AbstractGrammarElementFinder {
	
	public class RegularExpressionLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.RegularExpressionLiteral");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cSolidusKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cBodyAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cBodyRegularExpressionBodyParserRuleCall_1_0 = (RuleCall)cBodyAssignment_1.eContents().get(0);
		private final Keyword cSolidusKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cFlagsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cFlagsRegularExpressionFlagsParserRuleCall_3_0 = (RuleCall)cFlagsAssignment_3.eContents().get(0);
		
		//RegularExpressionLiteral:
		//	'/' body=RegularExpressionBody '/' flags=RegularExpressionFlags;
		@Override public ParserRule getRule() { return rule; }
		
		//'/' body=RegularExpressionBody '/' flags=RegularExpressionFlags
		public Group getGroup() { return cGroup; }
		
		//'/'
		public Keyword getSolidusKeyword_0() { return cSolidusKeyword_0; }
		
		//body=RegularExpressionBody
		public Assignment getBodyAssignment_1() { return cBodyAssignment_1; }
		
		//RegularExpressionBody
		public RuleCall getBodyRegularExpressionBodyParserRuleCall_1_0() { return cBodyRegularExpressionBodyParserRuleCall_1_0; }
		
		//'/'
		public Keyword getSolidusKeyword_2() { return cSolidusKeyword_2; }
		
		//flags=RegularExpressionFlags
		public Assignment getFlagsAssignment_3() { return cFlagsAssignment_3; }
		
		//RegularExpressionFlags
		public RuleCall getFlagsRegularExpressionFlagsParserRuleCall_3_0() { return cFlagsRegularExpressionFlagsParserRuleCall_3_0; }
	}
	public class RegularExpressionBodyElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.RegularExpressionBody");
		private final Assignment cPatternAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cPatternDisjunctionParserRuleCall_0 = (RuleCall)cPatternAssignment.eContents().get(0);
		
		//RegularExpressionBody:
		//	pattern=Disjunction;
		@Override public ParserRule getRule() { return rule; }
		
		//pattern=Disjunction
		public Assignment getPatternAssignment() { return cPatternAssignment; }
		
		//Disjunction
		public RuleCall getPatternDisjunctionParserRuleCall_0() { return cPatternDisjunctionParserRuleCall_0; }
	}
	public class DisjunctionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.Disjunction");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final RuleCall cAlternativeParserRuleCall_0_0 = (RuleCall)cGroup_0.eContents().get(0);
		private final Group cGroup_0_1 = (Group)cGroup_0.eContents().get(1);
		private final Action cDisjunctionElementsAction_0_1_0 = (Action)cGroup_0_1.eContents().get(0);
		private final Group cGroup_0_1_1 = (Group)cGroup_0_1.eContents().get(1);
		private final Keyword cVerticalLineKeyword_0_1_1_0 = (Keyword)cGroup_0_1_1.eContents().get(0);
		private final Assignment cElementsAssignment_0_1_1_1 = (Assignment)cGroup_0_1_1.eContents().get(1);
		private final RuleCall cElementsAlternativeParserRuleCall_0_1_1_1_0 = (RuleCall)cElementsAssignment_0_1_1_1.eContents().get(0);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Action cDisjunctionAction_1_0 = (Action)cGroup_1.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cGroup_1.eContents().get(1);
		private final Keyword cVerticalLineKeyword_1_1_0 = (Keyword)cGroup_1_1.eContents().get(0);
		private final Assignment cElementsAssignment_1_1_1 = (Assignment)cGroup_1_1.eContents().get(1);
		private final RuleCall cElementsAlternativeParserRuleCall_1_1_1_0 = (RuleCall)cElementsAssignment_1_1_1.eContents().get(0);
		
		//Disjunction Pattern:
		//	Alternative ({Disjunction.elements+=current} ('|' elements+=Alternative?)+)?
		//	| {Disjunction} ('|' elements+=Alternative?)*;
		@Override public ParserRule getRule() { return rule; }
		
		//Alternative ({Disjunction.elements+=current} ('|' elements+=Alternative?)+)? | {Disjunction} ('|'
		//elements+=Alternative?)*
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//Alternative ({Disjunction.elements+=current} ('|' elements+=Alternative?)+)?
		public Group getGroup_0() { return cGroup_0; }
		
		//Alternative
		public RuleCall getAlternativeParserRuleCall_0_0() { return cAlternativeParserRuleCall_0_0; }
		
		//({Disjunction.elements+=current} ('|' elements+=Alternative?)+)?
		public Group getGroup_0_1() { return cGroup_0_1; }
		
		//{Disjunction.elements+=current}
		public Action getDisjunctionElementsAction_0_1_0() { return cDisjunctionElementsAction_0_1_0; }
		
		//('|' elements+=Alternative?)+
		public Group getGroup_0_1_1() { return cGroup_0_1_1; }
		
		//'|'
		public Keyword getVerticalLineKeyword_0_1_1_0() { return cVerticalLineKeyword_0_1_1_0; }
		
		//elements+=Alternative?
		public Assignment getElementsAssignment_0_1_1_1() { return cElementsAssignment_0_1_1_1; }
		
		//Alternative
		public RuleCall getElementsAlternativeParserRuleCall_0_1_1_1_0() { return cElementsAlternativeParserRuleCall_0_1_1_1_0; }
		
		//{Disjunction} ('|' elements+=Alternative?)*
		public Group getGroup_1() { return cGroup_1; }
		
		//{Disjunction}
		public Action getDisjunctionAction_1_0() { return cDisjunctionAction_1_0; }
		
		//('|' elements+=Alternative?)*
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//'|'
		public Keyword getVerticalLineKeyword_1_1_0() { return cVerticalLineKeyword_1_1_0; }
		
		//elements+=Alternative?
		public Assignment getElementsAssignment_1_1_1() { return cElementsAssignment_1_1_1; }
		
		//Alternative
		public RuleCall getElementsAlternativeParserRuleCall_1_1_1_0() { return cElementsAlternativeParserRuleCall_1_1_1_0; }
	}
	public class AlternativeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.Alternative");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cTermParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Action cSequenceElementsAction_1_0 = (Action)cGroup_1.eContents().get(0);
		private final Assignment cElementsAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cElementsTermParserRuleCall_1_1_0 = (RuleCall)cElementsAssignment_1_1.eContents().get(0);
		
		//Alternative Pattern:
		//	Term ({Sequence.elements+=current} elements+=Term+)?;
		@Override public ParserRule getRule() { return rule; }
		
		//Term ({Sequence.elements+=current} elements+=Term+)?
		public Group getGroup() { return cGroup; }
		
		//Term
		public RuleCall getTermParserRuleCall_0() { return cTermParserRuleCall_0; }
		
		//({Sequence.elements+=current} elements+=Term+)?
		public Group getGroup_1() { return cGroup_1; }
		
		//{Sequence.elements+=current}
		public Action getSequenceElementsAction_1_0() { return cSequenceElementsAction_1_0; }
		
		//elements+=Term+
		public Assignment getElementsAssignment_1_1() { return cElementsAssignment_1_1; }
		
		//Term
		public RuleCall getElementsTermParserRuleCall_1_1_0() { return cElementsTermParserRuleCall_1_1_0; }
	}
	public class TermElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.Term");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cAssertionParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final RuleCall cAtomParserRuleCall_1_0 = (RuleCall)cGroup_1.eContents().get(0);
		private final Assignment cQuantifierAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cQuantifierQuantifierParserRuleCall_1_1_0 = (RuleCall)cQuantifierAssignment_1_1.eContents().get(0);
		
		//Term Pattern:
		//	Assertion | Atom => quantifier=Quantifier?;
		@Override public ParserRule getRule() { return rule; }
		
		//Assertion | Atom => quantifier=Quantifier?
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//Assertion
		public RuleCall getAssertionParserRuleCall_0() { return cAssertionParserRuleCall_0; }
		
		//Atom => quantifier=Quantifier?
		public Group getGroup_1() { return cGroup_1; }
		
		//Atom
		public RuleCall getAtomParserRuleCall_1_0() { return cAtomParserRuleCall_1_0; }
		
		//=> quantifier=Quantifier?
		public Assignment getQuantifierAssignment_1_1() { return cQuantifierAssignment_1_1; }
		
		//Quantifier
		public RuleCall getQuantifierQuantifierParserRuleCall_1_1_0() { return cQuantifierQuantifierParserRuleCall_1_1_0; }
	}
	public class AssertionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.Assertion");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cLineStartParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cLineEndParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cWordBoundaryParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cAbstractLookAheadParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		
		//Assertion:
		//	LineStart | LineEnd | WordBoundary | AbstractLookAhead;
		@Override public ParserRule getRule() { return rule; }
		
		//LineStart | LineEnd | WordBoundary | AbstractLookAhead
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//LineStart
		public RuleCall getLineStartParserRuleCall_0() { return cLineStartParserRuleCall_0; }
		
		//LineEnd
		public RuleCall getLineEndParserRuleCall_1() { return cLineEndParserRuleCall_1; }
		
		//WordBoundary
		public RuleCall getWordBoundaryParserRuleCall_2() { return cWordBoundaryParserRuleCall_2; }
		
		//AbstractLookAhead
		public RuleCall getAbstractLookAheadParserRuleCall_3() { return cAbstractLookAheadParserRuleCall_3; }
	}
	public class LineStartElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.LineStart");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cLineStartAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cCircumflexAccentKeyword_1 = (Keyword)cGroup.eContents().get(1);
		
		//LineStart:
		//	{LineStart} '^';
		@Override public ParserRule getRule() { return rule; }
		
		//{LineStart} '^'
		public Group getGroup() { return cGroup; }
		
		//{LineStart}
		public Action getLineStartAction_0() { return cLineStartAction_0; }
		
		//'^'
		public Keyword getCircumflexAccentKeyword_1() { return cCircumflexAccentKeyword_1; }
	}
	public class LineEndElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.LineEnd");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cLineEndAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cDollarSignKeyword_1 = (Keyword)cGroup.eContents().get(1);
		
		//LineEnd:
		//	{LineEnd} '$';
		@Override public ParserRule getRule() { return rule; }
		
		//{LineEnd} '$'
		public Group getGroup() { return cGroup; }
		
		//{LineEnd}
		public Action getLineEndAction_0() { return cLineEndAction_0; }
		
		//'$'
		public Keyword getDollarSignKeyword_1() { return cDollarSignKeyword_1; }
	}
	public class WordBoundaryElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.WordBoundary");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cWordBoundaryAction_0 = (Action)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final RuleCall cWORD_BOUNDARYTerminalRuleCall_1_0 = (RuleCall)cAlternatives_1.eContents().get(0);
		private final Assignment cNotAssignment_1_1 = (Assignment)cAlternatives_1.eContents().get(1);
		private final RuleCall cNotNOT_WORD_BOUNDARYTerminalRuleCall_1_1_0 = (RuleCall)cNotAssignment_1_1.eContents().get(0);
		
		//WordBoundary:
		//	{WordBoundary} (WORD_BOUNDARY | not?=NOT_WORD_BOUNDARY);
		@Override public ParserRule getRule() { return rule; }
		
		//{WordBoundary} (WORD_BOUNDARY | not?=NOT_WORD_BOUNDARY)
		public Group getGroup() { return cGroup; }
		
		//{WordBoundary}
		public Action getWordBoundaryAction_0() { return cWordBoundaryAction_0; }
		
		//(WORD_BOUNDARY | not?=NOT_WORD_BOUNDARY)
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//WORD_BOUNDARY
		public RuleCall getWORD_BOUNDARYTerminalRuleCall_1_0() { return cWORD_BOUNDARYTerminalRuleCall_1_0; }
		
		//not?=NOT_WORD_BOUNDARY
		public Assignment getNotAssignment_1_1() { return cNotAssignment_1_1; }
		
		//NOT_WORD_BOUNDARY
		public RuleCall getNotNOT_WORD_BOUNDARYTerminalRuleCall_1_1_0() { return cNotNOT_WORD_BOUNDARYTerminalRuleCall_1_1_0; }
	}
	public class AbstractLookAheadElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.AbstractLookAhead");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Alternatives cAlternatives_0 = (Alternatives)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cAlternatives_0.eContents().get(0);
		private final Action cLookAheadAction_0_0_0 = (Action)cGroup_0_0.eContents().get(0);
		private final Alternatives cAlternatives_0_0_1 = (Alternatives)cGroup_0_0.eContents().get(1);
		private final Keyword cLeftParenthesisQuestionMarkEqualsSignKeyword_0_0_1_0 = (Keyword)cAlternatives_0_0_1.eContents().get(0);
		private final Assignment cNotAssignment_0_0_1_1 = (Assignment)cAlternatives_0_0_1.eContents().get(1);
		private final Keyword cNotLeftParenthesisQuestionMarkExclamationMarkKeyword_0_0_1_1_0 = (Keyword)cNotAssignment_0_0_1_1.eContents().get(0);
		private final Group cGroup_0_1 = (Group)cAlternatives_0.eContents().get(1);
		private final Action cLookBehindAction_0_1_0 = (Action)cGroup_0_1.eContents().get(0);
		private final Alternatives cAlternatives_0_1_1 = (Alternatives)cGroup_0_1.eContents().get(1);
		private final Keyword cLeftParenthesisQuestionMarkLessThanSignEqualsSignKeyword_0_1_1_0 = (Keyword)cAlternatives_0_1_1.eContents().get(0);
		private final Assignment cNotAssignment_0_1_1_1 = (Assignment)cAlternatives_0_1_1.eContents().get(1);
		private final Keyword cNotLeftParenthesisQuestionMarkLessThanSignExclamationMarkKeyword_0_1_1_1_0 = (Keyword)cNotAssignment_0_1_1_1.eContents().get(0);
		private final Assignment cPatternAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cPatternDisjunctionParserRuleCall_1_0 = (RuleCall)cPatternAssignment_1.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		//AbstractLookAhead:
		//	({LookAhead} ('(?=' | not?='(?!') | {LookBehind} ('(?<=' | not?='(?<!')) pattern=Disjunction ')';
		@Override public ParserRule getRule() { return rule; }
		
		//({LookAhead} ('(?=' | not?='(?!') | {LookBehind} ('(?<=' | not?='(?<!')) pattern=Disjunction ')'
		public Group getGroup() { return cGroup; }
		
		//({LookAhead} ('(?=' | not?='(?!') | {LookBehind} ('(?<=' | not?='(?<!'))
		public Alternatives getAlternatives_0() { return cAlternatives_0; }
		
		//{LookAhead} ('(?=' | not?='(?!')
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//{LookAhead}
		public Action getLookAheadAction_0_0_0() { return cLookAheadAction_0_0_0; }
		
		//('(?=' | not?='(?!')
		public Alternatives getAlternatives_0_0_1() { return cAlternatives_0_0_1; }
		
		//'(?='
		public Keyword getLeftParenthesisQuestionMarkEqualsSignKeyword_0_0_1_0() { return cLeftParenthesisQuestionMarkEqualsSignKeyword_0_0_1_0; }
		
		//not?='(?!'
		public Assignment getNotAssignment_0_0_1_1() { return cNotAssignment_0_0_1_1; }
		
		//'(?!'
		public Keyword getNotLeftParenthesisQuestionMarkExclamationMarkKeyword_0_0_1_1_0() { return cNotLeftParenthesisQuestionMarkExclamationMarkKeyword_0_0_1_1_0; }
		
		//{LookBehind} ('(?<=' | not?='(?<!')
		public Group getGroup_0_1() { return cGroup_0_1; }
		
		//{LookBehind}
		public Action getLookBehindAction_0_1_0() { return cLookBehindAction_0_1_0; }
		
		//('(?<=' | not?='(?<!')
		public Alternatives getAlternatives_0_1_1() { return cAlternatives_0_1_1; }
		
		//'(?<='
		public Keyword getLeftParenthesisQuestionMarkLessThanSignEqualsSignKeyword_0_1_1_0() { return cLeftParenthesisQuestionMarkLessThanSignEqualsSignKeyword_0_1_1_0; }
		
		//not?='(?<!'
		public Assignment getNotAssignment_0_1_1_1() { return cNotAssignment_0_1_1_1; }
		
		//'(?<!'
		public Keyword getNotLeftParenthesisQuestionMarkLessThanSignExclamationMarkKeyword_0_1_1_1_0() { return cNotLeftParenthesisQuestionMarkLessThanSignExclamationMarkKeyword_0_1_1_1_0; }
		
		//pattern=Disjunction
		public Assignment getPatternAssignment_1() { return cPatternAssignment_1; }
		
		//Disjunction
		public RuleCall getPatternDisjunctionParserRuleCall_1_0() { return cPatternDisjunctionParserRuleCall_1_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_2() { return cRightParenthesisKeyword_2; }
	}
	public class AtomElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.Atom");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cPatternCharacterParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cWildcardParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cAtomEscapeParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cCharacterClassParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cGroupParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		
		//Atom Pattern:
		//	PatternCharacter | Wildcard | AtomEscape | CharacterClass | Group;
		@Override public ParserRule getRule() { return rule; }
		
		//PatternCharacter | Wildcard | AtomEscape | CharacterClass | Group
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//PatternCharacter
		public RuleCall getPatternCharacterParserRuleCall_0() { return cPatternCharacterParserRuleCall_0; }
		
		//Wildcard
		public RuleCall getWildcardParserRuleCall_1() { return cWildcardParserRuleCall_1; }
		
		//AtomEscape
		public RuleCall getAtomEscapeParserRuleCall_2() { return cAtomEscapeParserRuleCall_2; }
		
		//CharacterClass
		public RuleCall getCharacterClassParserRuleCall_3() { return cCharacterClassParserRuleCall_3; }
		
		//Group
		public RuleCall getGroupParserRuleCall_4() { return cGroupParserRuleCall_4; }
	}
	public class PatternCharacterElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.PatternCharacter");
		private final Assignment cValueAssignment = (Assignment)rule.eContents().get(1);
		private final Alternatives cValueAlternatives_0 = (Alternatives)cValueAssignment.eContents().get(0);
		private final RuleCall cValuePATTERN_CHARACTER_NO_DASHTerminalRuleCall_0_0 = (RuleCall)cValueAlternatives_0.eContents().get(0);
		private final RuleCall cValueUNICODE_LETTERTerminalRuleCall_0_1 = (RuleCall)cValueAlternatives_0.eContents().get(1);
		private final RuleCall cValueUNICODE_DIGITTerminalRuleCall_0_2 = (RuleCall)cValueAlternatives_0.eContents().get(2);
		private final Keyword cValueHyphenMinusKeyword_0_3 = (Keyword)cValueAlternatives_0.eContents().get(3);
		private final Keyword cValue_Keyword_0_4 = (Keyword)cValueAlternatives_0.eContents().get(4);
		private final Keyword cValueCommaKeyword_0_5 = (Keyword)cValueAlternatives_0.eContents().get(5);
		private final Keyword cValueEqualsSignKeyword_0_6 = (Keyword)cValueAlternatives_0.eContents().get(6);
		private final Keyword cValueColonKeyword_0_7 = (Keyword)cValueAlternatives_0.eContents().get(7);
		private final Keyword cValueExclamationMarkKeyword_0_8 = (Keyword)cValueAlternatives_0.eContents().get(8);
		private final Keyword cValueLeftCurlyBracketKeyword_0_9 = (Keyword)cValueAlternatives_0.eContents().get(9);
		private final Keyword cValueRightCurlyBracketKeyword_0_10 = (Keyword)cValueAlternatives_0.eContents().get(10);
		private final Keyword cValueRightSquareBracketKeyword_0_11 = (Keyword)cValueAlternatives_0.eContents().get(11);
		private final Keyword cValueLessThanSignKeyword_0_12 = (Keyword)cValueAlternatives_0.eContents().get(12);
		private final Keyword cValueGreaterThanSignKeyword_0_13 = (Keyword)cValueAlternatives_0.eContents().get(13);
		
		//PatternCharacter:
		//	value=(PATTERN_CHARACTER_NO_DASH | UNICODE_LETTER | UNICODE_DIGIT |
		//	'-' | '_' |
		//	',' | '=' | ':' | '!' | '{' | '}' | ']' | '<' | '>');
		@Override public ParserRule getRule() { return rule; }
		
		//value=(PATTERN_CHARACTER_NO_DASH | UNICODE_LETTER | UNICODE_DIGIT | '-' | '_' | ',' | '=' | ':' | '!' | '{' | '}' | ']'
		//| '<' | '>')
		public Assignment getValueAssignment() { return cValueAssignment; }
		
		//(PATTERN_CHARACTER_NO_DASH | UNICODE_LETTER | UNICODE_DIGIT | '-' | '_' | ',' | '=' | ':' | '!' | '{' | '}' | ']' | '<'
		//| '>')
		public Alternatives getValueAlternatives_0() { return cValueAlternatives_0; }
		
		//PATTERN_CHARACTER_NO_DASH
		public RuleCall getValuePATTERN_CHARACTER_NO_DASHTerminalRuleCall_0_0() { return cValuePATTERN_CHARACTER_NO_DASHTerminalRuleCall_0_0; }
		
		//UNICODE_LETTER
		public RuleCall getValueUNICODE_LETTERTerminalRuleCall_0_1() { return cValueUNICODE_LETTERTerminalRuleCall_0_1; }
		
		//UNICODE_DIGIT
		public RuleCall getValueUNICODE_DIGITTerminalRuleCall_0_2() { return cValueUNICODE_DIGITTerminalRuleCall_0_2; }
		
		//'-'
		public Keyword getValueHyphenMinusKeyword_0_3() { return cValueHyphenMinusKeyword_0_3; }
		
		//'_'
		public Keyword getValue_Keyword_0_4() { return cValue_Keyword_0_4; }
		
		//','
		public Keyword getValueCommaKeyword_0_5() { return cValueCommaKeyword_0_5; }
		
		//'='
		public Keyword getValueEqualsSignKeyword_0_6() { return cValueEqualsSignKeyword_0_6; }
		
		//':'
		public Keyword getValueColonKeyword_0_7() { return cValueColonKeyword_0_7; }
		
		//'!'
		public Keyword getValueExclamationMarkKeyword_0_8() { return cValueExclamationMarkKeyword_0_8; }
		
		//'{'
		public Keyword getValueLeftCurlyBracketKeyword_0_9() { return cValueLeftCurlyBracketKeyword_0_9; }
		
		//'}'
		public Keyword getValueRightCurlyBracketKeyword_0_10() { return cValueRightCurlyBracketKeyword_0_10; }
		
		//']'
		public Keyword getValueRightSquareBracketKeyword_0_11() { return cValueRightSquareBracketKeyword_0_11; }
		
		//'<'
		public Keyword getValueLessThanSignKeyword_0_12() { return cValueLessThanSignKeyword_0_12; }
		
		//'>'
		public Keyword getValueGreaterThanSignKeyword_0_13() { return cValueGreaterThanSignKeyword_0_13; }
	}
	public class WildcardElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.Wildcard");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cWildcardAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cFullStopKeyword_1 = (Keyword)cGroup.eContents().get(1);
		
		//Wildcard:
		//	{Wildcard} '.';
		@Override public ParserRule getRule() { return rule; }
		
		//{Wildcard} '.'
		public Group getGroup() { return cGroup; }
		
		//{Wildcard}
		public Action getWildcardAction_0() { return cWildcardAction_0; }
		
		//'.'
		public Keyword getFullStopKeyword_1() { return cFullStopKeyword_1; }
	}
	public class AtomEscapeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.AtomEscape");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cDecimalEscapeSequenceParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cCharacterEscapeSequenceParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cControlLetterEscapeSequenceParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cHexEscapeSequenceParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cUnicodeEscapeSequenceParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		private final RuleCall cIdentityEscapeSequenceParserRuleCall_5 = (RuleCall)cAlternatives.eContents().get(5);
		private final RuleCall cCharacterClassEscapeSequenceParserRuleCall_6 = (RuleCall)cAlternatives.eContents().get(6);
		
		//AtomEscape:
		//	DecimalEscapeSequence | CharacterEscapeSequence | ControlLetterEscapeSequence | HexEscapeSequence |
		//	UnicodeEscapeSequence | IdentityEscapeSequence | CharacterClassEscapeSequence;
		@Override public ParserRule getRule() { return rule; }
		
		//DecimalEscapeSequence | CharacterEscapeSequence | ControlLetterEscapeSequence | HexEscapeSequence |
		//UnicodeEscapeSequence | IdentityEscapeSequence | CharacterClassEscapeSequence
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//DecimalEscapeSequence
		public RuleCall getDecimalEscapeSequenceParserRuleCall_0() { return cDecimalEscapeSequenceParserRuleCall_0; }
		
		//CharacterEscapeSequence
		public RuleCall getCharacterEscapeSequenceParserRuleCall_1() { return cCharacterEscapeSequenceParserRuleCall_1; }
		
		//ControlLetterEscapeSequence
		public RuleCall getControlLetterEscapeSequenceParserRuleCall_2() { return cControlLetterEscapeSequenceParserRuleCall_2; }
		
		//HexEscapeSequence
		public RuleCall getHexEscapeSequenceParserRuleCall_3() { return cHexEscapeSequenceParserRuleCall_3; }
		
		//UnicodeEscapeSequence
		public RuleCall getUnicodeEscapeSequenceParserRuleCall_4() { return cUnicodeEscapeSequenceParserRuleCall_4; }
		
		//IdentityEscapeSequence
		public RuleCall getIdentityEscapeSequenceParserRuleCall_5() { return cIdentityEscapeSequenceParserRuleCall_5; }
		
		//CharacterClassEscapeSequence
		public RuleCall getCharacterClassEscapeSequenceParserRuleCall_6() { return cCharacterClassEscapeSequenceParserRuleCall_6; }
	}
	public class CharacterClassEscapeSequenceElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.CharacterClassEscapeSequence");
		private final Assignment cSequenceAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cSequenceCHARACTER_CLASS_ESCAPETerminalRuleCall_0 = (RuleCall)cSequenceAssignment.eContents().get(0);
		
		//CharacterClassEscapeSequence:
		//	sequence=CHARACTER_CLASS_ESCAPE;
		@Override public ParserRule getRule() { return rule; }
		
		//sequence=CHARACTER_CLASS_ESCAPE
		public Assignment getSequenceAssignment() { return cSequenceAssignment; }
		
		//CHARACTER_CLASS_ESCAPE
		public RuleCall getSequenceCHARACTER_CLASS_ESCAPETerminalRuleCall_0() { return cSequenceCHARACTER_CLASS_ESCAPETerminalRuleCall_0; }
	}
	public class CharacterEscapeSequenceElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.CharacterEscapeSequence");
		private final Assignment cSequenceAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cSequenceCONTROL_ESCAPETerminalRuleCall_0 = (RuleCall)cSequenceAssignment.eContents().get(0);
		
		//CharacterEscapeSequence:
		//	sequence=CONTROL_ESCAPE;
		@Override public ParserRule getRule() { return rule; }
		
		//sequence=CONTROL_ESCAPE
		public Assignment getSequenceAssignment() { return cSequenceAssignment; }
		
		//CONTROL_ESCAPE
		public RuleCall getSequenceCONTROL_ESCAPETerminalRuleCall_0() { return cSequenceCONTROL_ESCAPETerminalRuleCall_0; }
	}
	public class ControlLetterEscapeSequenceElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.ControlLetterEscapeSequence");
		private final Assignment cSequenceAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cSequenceCONTROL_LETTER_ESCAPETerminalRuleCall_0 = (RuleCall)cSequenceAssignment.eContents().get(0);
		
		//ControlLetterEscapeSequence:
		//	sequence=CONTROL_LETTER_ESCAPE;
		@Override public ParserRule getRule() { return rule; }
		
		//sequence=CONTROL_LETTER_ESCAPE
		public Assignment getSequenceAssignment() { return cSequenceAssignment; }
		
		//CONTROL_LETTER_ESCAPE
		public RuleCall getSequenceCONTROL_LETTER_ESCAPETerminalRuleCall_0() { return cSequenceCONTROL_LETTER_ESCAPETerminalRuleCall_0; }
	}
	public class HexEscapeSequenceElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.HexEscapeSequence");
		private final Assignment cSequenceAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cSequenceHEX_ESCAPETerminalRuleCall_0 = (RuleCall)cSequenceAssignment.eContents().get(0);
		
		//HexEscapeSequence:
		//	sequence=HEX_ESCAPE;
		@Override public ParserRule getRule() { return rule; }
		
		//sequence=HEX_ESCAPE
		public Assignment getSequenceAssignment() { return cSequenceAssignment; }
		
		//HEX_ESCAPE
		public RuleCall getSequenceHEX_ESCAPETerminalRuleCall_0() { return cSequenceHEX_ESCAPETerminalRuleCall_0; }
	}
	public class UnicodeEscapeSequenceElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.UnicodeEscapeSequence");
		private final Assignment cSequenceAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cSequenceUNICODE_ESCAPETerminalRuleCall_0 = (RuleCall)cSequenceAssignment.eContents().get(0);
		
		//UnicodeEscapeSequence:
		//	sequence=UNICODE_ESCAPE;
		@Override public ParserRule getRule() { return rule; }
		
		//sequence=UNICODE_ESCAPE
		public Assignment getSequenceAssignment() { return cSequenceAssignment; }
		
		//UNICODE_ESCAPE
		public RuleCall getSequenceUNICODE_ESCAPETerminalRuleCall_0() { return cSequenceUNICODE_ESCAPETerminalRuleCall_0; }
	}
	public class IdentityEscapeSequenceElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.IdentityEscapeSequence");
		private final Assignment cSequenceAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cSequenceIDENTITY_ESCAPETerminalRuleCall_0 = (RuleCall)cSequenceAssignment.eContents().get(0);
		
		//IdentityEscapeSequence:
		//	sequence=IDENTITY_ESCAPE;
		@Override public ParserRule getRule() { return rule; }
		
		//sequence=IDENTITY_ESCAPE
		public Assignment getSequenceAssignment() { return cSequenceAssignment; }
		
		//IDENTITY_ESCAPE
		public RuleCall getSequenceIDENTITY_ESCAPETerminalRuleCall_0() { return cSequenceIDENTITY_ESCAPETerminalRuleCall_0; }
	}
	public class DecimalEscapeSequenceElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.DecimalEscapeSequence");
		private final Assignment cSequenceAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cSequenceDECIMAL_ESCAPETerminalRuleCall_0 = (RuleCall)cSequenceAssignment.eContents().get(0);
		
		//DecimalEscapeSequence:
		//	sequence=DECIMAL_ESCAPE;
		@Override public ParserRule getRule() { return rule; }
		
		//sequence=DECIMAL_ESCAPE
		public Assignment getSequenceAssignment() { return cSequenceAssignment; }
		
		//DECIMAL_ESCAPE
		public RuleCall getSequenceDECIMAL_ESCAPETerminalRuleCall_0() { return cSequenceDECIMAL_ESCAPETerminalRuleCall_0; }
	}
	public class CharacterClassElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.CharacterClass");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cCharacterClassAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cLeftSquareBracketKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Assignment cNegatedAssignment_2_0 = (Assignment)cGroup_2.eContents().get(0);
		private final Keyword cNegatedCircumflexAccentKeyword_2_0_0 = (Keyword)cNegatedAssignment_2_0.eContents().get(0);
		private final Assignment cElementsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cElementsCharacterClassElementParserRuleCall_3_0 = (RuleCall)cElementsAssignment_3.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//CharacterClass:
		//	{CharacterClass} '[' => (negated?='^')?
		//	elements+=CharacterClassElement*
		//	']';
		@Override public ParserRule getRule() { return rule; }
		
		//{CharacterClass} '[' => (negated?='^')? elements+=CharacterClassElement* ']'
		public Group getGroup() { return cGroup; }
		
		//{CharacterClass}
		public Action getCharacterClassAction_0() { return cCharacterClassAction_0; }
		
		//'['
		public Keyword getLeftSquareBracketKeyword_1() { return cLeftSquareBracketKeyword_1; }
		
		//=> (negated?='^')?
		public Group getGroup_2() { return cGroup_2; }
		
		//negated?='^'
		public Assignment getNegatedAssignment_2_0() { return cNegatedAssignment_2_0; }
		
		//'^'
		public Keyword getNegatedCircumflexAccentKeyword_2_0_0() { return cNegatedCircumflexAccentKeyword_2_0_0; }
		
		//elements+=CharacterClassElement*
		public Assignment getElementsAssignment_3() { return cElementsAssignment_3; }
		
		//CharacterClassElement
		public RuleCall getElementsCharacterClassElementParserRuleCall_3_0() { return cElementsCharacterClassElementParserRuleCall_3_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_4() { return cRightSquareBracketKeyword_4; }
	}
	public class CharacterClassElementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.CharacterClassElement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cCharacterClassAtomParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
		private final Action cCharacterClassRangeLeftAction_1_0_0 = (Action)cGroup_1_0.eContents().get(0);
		private final Keyword cHyphenMinusKeyword_1_0_1 = (Keyword)cGroup_1_0.eContents().get(1);
		private final Assignment cRightAssignment_1_0_2 = (Assignment)cGroup_1_0.eContents().get(2);
		private final RuleCall cRightCharacterClassAtomParserRuleCall_1_0_2_0 = (RuleCall)cRightAssignment_1_0_2.eContents().get(0);
		
		//CharacterClassElement:
		//	CharacterClassAtom => ({CharacterClassRange.left=current} '-' right=CharacterClassAtom)?;
		@Override public ParserRule getRule() { return rule; }
		
		//CharacterClassAtom => ({CharacterClassRange.left=current} '-' right=CharacterClassAtom)?
		public Group getGroup() { return cGroup; }
		
		//CharacterClassAtom
		public RuleCall getCharacterClassAtomParserRuleCall_0() { return cCharacterClassAtomParserRuleCall_0; }
		
		//=> ({CharacterClassRange.left=current} '-' right=CharacterClassAtom)?
		public Group getGroup_1() { return cGroup_1; }
		
		//({CharacterClassRange.left=current} '-' right=CharacterClassAtom)
		public Group getGroup_1_0() { return cGroup_1_0; }
		
		//{CharacterClassRange.left=current}
		public Action getCharacterClassRangeLeftAction_1_0_0() { return cCharacterClassRangeLeftAction_1_0_0; }
		
		//'-'
		public Keyword getHyphenMinusKeyword_1_0_1() { return cHyphenMinusKeyword_1_0_1; }
		
		//right=CharacterClassAtom
		public Assignment getRightAssignment_1_0_2() { return cRightAssignment_1_0_2; }
		
		//CharacterClassAtom
		public RuleCall getRightCharacterClassAtomParserRuleCall_1_0_2_0() { return cRightCharacterClassAtomParserRuleCall_1_0_2_0; }
	}
	public class CharacterClassAtomElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.CharacterClassAtom");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cEscapedCharacterClassAtomParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final Assignment cCharactersAssignment_1 = (Assignment)cAlternatives.eContents().get(1);
		private final Alternatives cCharactersAlternatives_1_0 = (Alternatives)cCharactersAssignment_1.eContents().get(0);
		private final Keyword cCharactersCommaKeyword_1_0_0 = (Keyword)cCharactersAlternatives_1_0.eContents().get(0);
		private final Keyword cCharactersEqualsSignKeyword_1_0_1 = (Keyword)cCharactersAlternatives_1_0.eContents().get(1);
		private final Keyword cCharactersColonKeyword_1_0_2 = (Keyword)cCharactersAlternatives_1_0.eContents().get(2);
		private final Keyword cCharactersExclamationMarkKeyword_1_0_3 = (Keyword)cCharactersAlternatives_1_0.eContents().get(3);
		private final Keyword cCharacters_Keyword_1_0_4 = (Keyword)cCharactersAlternatives_1_0.eContents().get(4);
		private final Keyword cCharactersHyphenMinusKeyword_1_0_5 = (Keyword)cCharactersAlternatives_1_0.eContents().get(5);
		private final Keyword cCharactersCircumflexAccentKeyword_1_0_6 = (Keyword)cCharactersAlternatives_1_0.eContents().get(6);
		private final Keyword cCharactersDollarSignKeyword_1_0_7 = (Keyword)cCharactersAlternatives_1_0.eContents().get(7);
		private final Keyword cCharactersFullStopKeyword_1_0_8 = (Keyword)cCharactersAlternatives_1_0.eContents().get(8);
		private final Keyword cCharactersAsteriskKeyword_1_0_9 = (Keyword)cCharactersAlternatives_1_0.eContents().get(9);
		private final Keyword cCharactersPlusSignKeyword_1_0_10 = (Keyword)cCharactersAlternatives_1_0.eContents().get(10);
		private final Keyword cCharactersQuestionMarkKeyword_1_0_11 = (Keyword)cCharactersAlternatives_1_0.eContents().get(11);
		private final Keyword cCharactersLeftParenthesisKeyword_1_0_12 = (Keyword)cCharactersAlternatives_1_0.eContents().get(12);
		private final Keyword cCharactersRightParenthesisKeyword_1_0_13 = (Keyword)cCharactersAlternatives_1_0.eContents().get(13);
		private final Keyword cCharactersLeftSquareBracketKeyword_1_0_14 = (Keyword)cCharactersAlternatives_1_0.eContents().get(14);
		private final Keyword cCharactersLeftCurlyBracketKeyword_1_0_15 = (Keyword)cCharactersAlternatives_1_0.eContents().get(15);
		private final Keyword cCharactersRightCurlyBracketKeyword_1_0_16 = (Keyword)cCharactersAlternatives_1_0.eContents().get(16);
		private final Keyword cCharactersVerticalLineKeyword_1_0_17 = (Keyword)cCharactersAlternatives_1_0.eContents().get(17);
		private final Keyword cCharactersSolidusKeyword_1_0_18 = (Keyword)cCharactersAlternatives_1_0.eContents().get(18);
		private final Keyword cCharactersLessThanSignKeyword_1_0_19 = (Keyword)cCharactersAlternatives_1_0.eContents().get(19);
		private final Keyword cCharactersGreaterThanSignKeyword_1_0_20 = (Keyword)cCharactersAlternatives_1_0.eContents().get(20);
		private final Keyword cCharactersLeftParenthesisQuestionMarkKeyword_1_0_21 = (Keyword)cCharactersAlternatives_1_0.eContents().get(21);
		private final Keyword cCharactersLeftParenthesisQuestionMarkLessThanSignKeyword_1_0_22 = (Keyword)cCharactersAlternatives_1_0.eContents().get(22);
		private final Keyword cCharactersLeftParenthesisQuestionMarkEqualsSignKeyword_1_0_23 = (Keyword)cCharactersAlternatives_1_0.eContents().get(23);
		private final Keyword cCharactersLeftParenthesisQuestionMarkExclamationMarkKeyword_1_0_24 = (Keyword)cCharactersAlternatives_1_0.eContents().get(24);
		private final Keyword cCharactersLeftParenthesisQuestionMarkLessThanSignExclamationMarkKeyword_1_0_25 = (Keyword)cCharactersAlternatives_1_0.eContents().get(25);
		private final Keyword cCharactersLeftParenthesisQuestionMarkLessThanSignEqualsSignKeyword_1_0_26 = (Keyword)cCharactersAlternatives_1_0.eContents().get(26);
		private final RuleCall cCharactersPATTERN_CHARACTER_NO_DASHTerminalRuleCall_1_0_27 = (RuleCall)cCharactersAlternatives_1_0.eContents().get(27);
		private final RuleCall cCharactersUNICODE_LETTERTerminalRuleCall_1_0_28 = (RuleCall)cCharactersAlternatives_1_0.eContents().get(28);
		private final RuleCall cCharactersUNICODE_DIGITTerminalRuleCall_1_0_29 = (RuleCall)cCharactersAlternatives_1_0.eContents().get(29);
		
		//CharacterClassAtom:
		//	EscapedCharacterClassAtom | characters=(',' | '=' | ':' | '!' | '_' |
		//	'-' | '^' | '$' | '.' | '*' | '+' | '?' | '(' | ')' | '[' | '{' | '}' | '|' | '/' | '<' | '>' | '(?' | '(?<' | '(?=' |
		//	'(?!' | '(?<!' | '(?<=' | PATTERN_CHARACTER_NO_DASH | UNICODE_LETTER | UNICODE_DIGIT);
		@Override public ParserRule getRule() { return rule; }
		
		//EscapedCharacterClassAtom | characters=(',' | '=' | ':' | '!' | '_' | '-' | '^' | '$' | '.' | '*' | '+' | '?' | '(' |
		//')' | '[' | '{' | '}' | '|' | '/' | '<' | '>' | '(?' | '(?<' | '(?=' | '(?!' | '(?<!' | '(?<=' |
		//PATTERN_CHARACTER_NO_DASH | UNICODE_LETTER | UNICODE_DIGIT)
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//EscapedCharacterClassAtom
		public RuleCall getEscapedCharacterClassAtomParserRuleCall_0() { return cEscapedCharacterClassAtomParserRuleCall_0; }
		
		//characters=(',' | '=' | ':' | '!' | '_' | '-' | '^' | '$' | '.' | '*' | '+' | '?' | '(' | ')' | '[' | '{' | '}' | '|' |
		//'/' | '<' | '>' | '(?' | '(?<' | '(?=' | '(?!' | '(?<!' | '(?<=' | PATTERN_CHARACTER_NO_DASH | UNICODE_LETTER |
		//UNICODE_DIGIT)
		public Assignment getCharactersAssignment_1() { return cCharactersAssignment_1; }
		
		//(',' | '=' | ':' | '!' | '_' | '-' | '^' | '$' | '.' | '*' | '+' | '?' | '(' | ')' | '[' | '{' | '}' | '|' | '/' | '<' |
		//'>' | '(?' | '(?<' | '(?=' | '(?!' | '(?<!' | '(?<=' | PATTERN_CHARACTER_NO_DASH | UNICODE_LETTER | UNICODE_DIGIT)
		public Alternatives getCharactersAlternatives_1_0() { return cCharactersAlternatives_1_0; }
		
		//','
		public Keyword getCharactersCommaKeyword_1_0_0() { return cCharactersCommaKeyword_1_0_0; }
		
		//'='
		public Keyword getCharactersEqualsSignKeyword_1_0_1() { return cCharactersEqualsSignKeyword_1_0_1; }
		
		//':'
		public Keyword getCharactersColonKeyword_1_0_2() { return cCharactersColonKeyword_1_0_2; }
		
		//'!'
		public Keyword getCharactersExclamationMarkKeyword_1_0_3() { return cCharactersExclamationMarkKeyword_1_0_3; }
		
		//'_'
		public Keyword getCharacters_Keyword_1_0_4() { return cCharacters_Keyword_1_0_4; }
		
		//'-'
		public Keyword getCharactersHyphenMinusKeyword_1_0_5() { return cCharactersHyphenMinusKeyword_1_0_5; }
		
		//'^'
		public Keyword getCharactersCircumflexAccentKeyword_1_0_6() { return cCharactersCircumflexAccentKeyword_1_0_6; }
		
		//'$'
		public Keyword getCharactersDollarSignKeyword_1_0_7() { return cCharactersDollarSignKeyword_1_0_7; }
		
		//'.'
		public Keyword getCharactersFullStopKeyword_1_0_8() { return cCharactersFullStopKeyword_1_0_8; }
		
		//'*'
		public Keyword getCharactersAsteriskKeyword_1_0_9() { return cCharactersAsteriskKeyword_1_0_9; }
		
		//'+'
		public Keyword getCharactersPlusSignKeyword_1_0_10() { return cCharactersPlusSignKeyword_1_0_10; }
		
		//'?'
		public Keyword getCharactersQuestionMarkKeyword_1_0_11() { return cCharactersQuestionMarkKeyword_1_0_11; }
		
		//'('
		public Keyword getCharactersLeftParenthesisKeyword_1_0_12() { return cCharactersLeftParenthesisKeyword_1_0_12; }
		
		//')'
		public Keyword getCharactersRightParenthesisKeyword_1_0_13() { return cCharactersRightParenthesisKeyword_1_0_13; }
		
		//'['
		public Keyword getCharactersLeftSquareBracketKeyword_1_0_14() { return cCharactersLeftSquareBracketKeyword_1_0_14; }
		
		//'{'
		public Keyword getCharactersLeftCurlyBracketKeyword_1_0_15() { return cCharactersLeftCurlyBracketKeyword_1_0_15; }
		
		//'}'
		public Keyword getCharactersRightCurlyBracketKeyword_1_0_16() { return cCharactersRightCurlyBracketKeyword_1_0_16; }
		
		//'|'
		public Keyword getCharactersVerticalLineKeyword_1_0_17() { return cCharactersVerticalLineKeyword_1_0_17; }
		
		//'/'
		public Keyword getCharactersSolidusKeyword_1_0_18() { return cCharactersSolidusKeyword_1_0_18; }
		
		//'<'
		public Keyword getCharactersLessThanSignKeyword_1_0_19() { return cCharactersLessThanSignKeyword_1_0_19; }
		
		//'>'
		public Keyword getCharactersGreaterThanSignKeyword_1_0_20() { return cCharactersGreaterThanSignKeyword_1_0_20; }
		
		//'(?'
		public Keyword getCharactersLeftParenthesisQuestionMarkKeyword_1_0_21() { return cCharactersLeftParenthesisQuestionMarkKeyword_1_0_21; }
		
		//'(?<'
		public Keyword getCharactersLeftParenthesisQuestionMarkLessThanSignKeyword_1_0_22() { return cCharactersLeftParenthesisQuestionMarkLessThanSignKeyword_1_0_22; }
		
		//'(?='
		public Keyword getCharactersLeftParenthesisQuestionMarkEqualsSignKeyword_1_0_23() { return cCharactersLeftParenthesisQuestionMarkEqualsSignKeyword_1_0_23; }
		
		//'(?!'
		public Keyword getCharactersLeftParenthesisQuestionMarkExclamationMarkKeyword_1_0_24() { return cCharactersLeftParenthesisQuestionMarkExclamationMarkKeyword_1_0_24; }
		
		//'(?<!'
		public Keyword getCharactersLeftParenthesisQuestionMarkLessThanSignExclamationMarkKeyword_1_0_25() { return cCharactersLeftParenthesisQuestionMarkLessThanSignExclamationMarkKeyword_1_0_25; }
		
		//'(?<='
		public Keyword getCharactersLeftParenthesisQuestionMarkLessThanSignEqualsSignKeyword_1_0_26() { return cCharactersLeftParenthesisQuestionMarkLessThanSignEqualsSignKeyword_1_0_26; }
		
		//PATTERN_CHARACTER_NO_DASH
		public RuleCall getCharactersPATTERN_CHARACTER_NO_DASHTerminalRuleCall_1_0_27() { return cCharactersPATTERN_CHARACTER_NO_DASHTerminalRuleCall_1_0_27; }
		
		//UNICODE_LETTER
		public RuleCall getCharactersUNICODE_LETTERTerminalRuleCall_1_0_28() { return cCharactersUNICODE_LETTERTerminalRuleCall_1_0_28; }
		
		//UNICODE_DIGIT
		public RuleCall getCharactersUNICODE_DIGITTerminalRuleCall_1_0_29() { return cCharactersUNICODE_DIGITTerminalRuleCall_1_0_29; }
	}
	public class EscapedCharacterClassAtomElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.EscapedCharacterClassAtom");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cDecimalEscapeSequenceParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cBackspaceParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cCharacterEscapeSequenceParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cControlLetterEscapeSequenceParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cHexEscapeSequenceParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		private final RuleCall cUnicodeEscapeSequenceParserRuleCall_5 = (RuleCall)cAlternatives.eContents().get(5);
		private final RuleCall cIdentityEscapeSequenceParserRuleCall_6 = (RuleCall)cAlternatives.eContents().get(6);
		private final RuleCall cCharacterClassEscapeSequenceParserRuleCall_7 = (RuleCall)cAlternatives.eContents().get(7);
		
		//EscapedCharacterClassAtom:
		//	DecimalEscapeSequence
		//	| Backspace
		//	| CharacterEscapeSequence
		//	| ControlLetterEscapeSequence
		//	| HexEscapeSequence
		//	| UnicodeEscapeSequence
		//	| IdentityEscapeSequence
		//	| CharacterClassEscapeSequence;
		@Override public ParserRule getRule() { return rule; }
		
		//DecimalEscapeSequence | Backspace | CharacterEscapeSequence | ControlLetterEscapeSequence | HexEscapeSequence |
		//UnicodeEscapeSequence | IdentityEscapeSequence | CharacterClassEscapeSequence
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//DecimalEscapeSequence
		public RuleCall getDecimalEscapeSequenceParserRuleCall_0() { return cDecimalEscapeSequenceParserRuleCall_0; }
		
		//Backspace
		public RuleCall getBackspaceParserRuleCall_1() { return cBackspaceParserRuleCall_1; }
		
		//CharacterEscapeSequence
		public RuleCall getCharacterEscapeSequenceParserRuleCall_2() { return cCharacterEscapeSequenceParserRuleCall_2; }
		
		//ControlLetterEscapeSequence
		public RuleCall getControlLetterEscapeSequenceParserRuleCall_3() { return cControlLetterEscapeSequenceParserRuleCall_3; }
		
		//HexEscapeSequence
		public RuleCall getHexEscapeSequenceParserRuleCall_4() { return cHexEscapeSequenceParserRuleCall_4; }
		
		//UnicodeEscapeSequence
		public RuleCall getUnicodeEscapeSequenceParserRuleCall_5() { return cUnicodeEscapeSequenceParserRuleCall_5; }
		
		//IdentityEscapeSequence
		public RuleCall getIdentityEscapeSequenceParserRuleCall_6() { return cIdentityEscapeSequenceParserRuleCall_6; }
		
		//CharacterClassEscapeSequence
		public RuleCall getCharacterClassEscapeSequenceParserRuleCall_7() { return cCharacterClassEscapeSequenceParserRuleCall_7; }
	}
	public class BackspaceElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.Backspace");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cBackspaceAction_0 = (Action)cGroup.eContents().get(0);
		private final RuleCall cWORD_BOUNDARYTerminalRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		//Backspace:
		//	{Backspace} WORD_BOUNDARY;
		@Override public ParserRule getRule() { return rule; }
		
		//{Backspace} WORD_BOUNDARY
		public Group getGroup() { return cGroup; }
		
		//{Backspace}
		public Action getBackspaceAction_0() { return cBackspaceAction_0; }
		
		//WORD_BOUNDARY
		public RuleCall getWORD_BOUNDARYTerminalRuleCall_1() { return cWORD_BOUNDARYTerminalRuleCall_1; }
	}
	public class GroupElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.Group");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cGroupAction_0 = (Action)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Keyword cLeftParenthesisKeyword_1_0 = (Keyword)cAlternatives_1.eContents().get(0);
		private final Assignment cNonCapturingAssignment_1_1 = (Assignment)cAlternatives_1.eContents().get(1);
		private final Keyword cNonCapturingLeftParenthesisQuestionMarkColonKeyword_1_1_0 = (Keyword)cNonCapturingAssignment_1_1.eContents().get(0);
		private final Group cGroup_1_2 = (Group)cAlternatives_1.eContents().get(2);
		private final Assignment cNamedAssignment_1_2_0 = (Assignment)cGroup_1_2.eContents().get(0);
		private final Keyword cNamedLeftParenthesisQuestionMarkLessThanSignKeyword_1_2_0_0 = (Keyword)cNamedAssignment_1_2_0.eContents().get(0);
		private final Assignment cNameAssignment_1_2_1 = (Assignment)cGroup_1_2.eContents().get(1);
		private final RuleCall cNameRegExpIdentifierNameParserRuleCall_1_2_1_0 = (RuleCall)cNameAssignment_1_2_1.eContents().get(0);
		private final Keyword cGreaterThanSignKeyword_1_2_2 = (Keyword)cGroup_1_2.eContents().get(2);
		private final Assignment cPatternAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cPatternDisjunctionParserRuleCall_2_0 = (RuleCall)cPatternAssignment_2.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//Group:
		//	{Group} ('('
		//	| nonCapturing?='(?:'
		//	| named?='(?<' name=RegExpIdentifierName '>') pattern=Disjunction ')';
		@Override public ParserRule getRule() { return rule; }
		
		//{Group} ('(' | nonCapturing?='(?:' | named?='(?<' name=RegExpIdentifierName '>') pattern=Disjunction ')'
		public Group getGroup() { return cGroup; }
		
		//{Group}
		public Action getGroupAction_0() { return cGroupAction_0; }
		
		//('(' | nonCapturing?='(?:' | named?='(?<' name=RegExpIdentifierName '>')
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_1_0() { return cLeftParenthesisKeyword_1_0; }
		
		//nonCapturing?='(?:'
		public Assignment getNonCapturingAssignment_1_1() { return cNonCapturingAssignment_1_1; }
		
		//'(?:'
		public Keyword getNonCapturingLeftParenthesisQuestionMarkColonKeyword_1_1_0() { return cNonCapturingLeftParenthesisQuestionMarkColonKeyword_1_1_0; }
		
		//named?='(?<' name=RegExpIdentifierName '>'
		public Group getGroup_1_2() { return cGroup_1_2; }
		
		//named?='(?<'
		public Assignment getNamedAssignment_1_2_0() { return cNamedAssignment_1_2_0; }
		
		//'(?<'
		public Keyword getNamedLeftParenthesisQuestionMarkLessThanSignKeyword_1_2_0_0() { return cNamedLeftParenthesisQuestionMarkLessThanSignKeyword_1_2_0_0; }
		
		//name=RegExpIdentifierName
		public Assignment getNameAssignment_1_2_1() { return cNameAssignment_1_2_1; }
		
		//RegExpIdentifierName
		public RuleCall getNameRegExpIdentifierNameParserRuleCall_1_2_1_0() { return cNameRegExpIdentifierNameParserRuleCall_1_2_1_0; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_1_2_2() { return cGreaterThanSignKeyword_1_2_2; }
		
		//pattern=Disjunction
		public Assignment getPatternAssignment_2() { return cPatternAssignment_2; }
		
		//Disjunction
		public RuleCall getPatternDisjunctionParserRuleCall_2_0() { return cPatternDisjunctionParserRuleCall_2_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_3() { return cRightParenthesisKeyword_3; }
	}
	public class RegExpIdentifierNameElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.RegExpIdentifierName");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cRegExpIdentifierStartParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final RuleCall cRegExpIdentifierPartParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		//RegExpIdentifierName:
		//	RegExpIdentifierStart RegExpIdentifierPart*;
		@Override public ParserRule getRule() { return rule; }
		
		//RegExpIdentifierStart RegExpIdentifierPart*
		public Group getGroup() { return cGroup; }
		
		//RegExpIdentifierStart
		public RuleCall getRegExpIdentifierStartParserRuleCall_0() { return cRegExpIdentifierStartParserRuleCall_0; }
		
		//RegExpIdentifierPart*
		public RuleCall getRegExpIdentifierPartParserRuleCall_1() { return cRegExpIdentifierPartParserRuleCall_1; }
	}
	public class RegExpIdentifierStartElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.RegExpIdentifierStart");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cUNICODE_LETTERTerminalRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final Keyword cDollarSignKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword c_Keyword_2 = (Keyword)cAlternatives.eContents().get(2);
		private final RuleCall cUNICODE_ESCAPETerminalRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		
		//RegExpIdentifierStart:
		//	UNICODE_LETTER | '$' | '_' | UNICODE_ESCAPE;
		@Override public ParserRule getRule() { return rule; }
		
		//UNICODE_LETTER | '$' | '_' | UNICODE_ESCAPE
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//UNICODE_LETTER
		public RuleCall getUNICODE_LETTERTerminalRuleCall_0() { return cUNICODE_LETTERTerminalRuleCall_0; }
		
		//'$'
		public Keyword getDollarSignKeyword_1() { return cDollarSignKeyword_1; }
		
		//'_'
		public Keyword get_Keyword_2() { return c_Keyword_2; }
		
		//UNICODE_ESCAPE
		public RuleCall getUNICODE_ESCAPETerminalRuleCall_3() { return cUNICODE_ESCAPETerminalRuleCall_3; }
	}
	public class RegExpIdentifierPartElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.RegExpIdentifierPart");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cUNICODE_LETTERTerminalRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cUNICODE_DIGITTerminalRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final Keyword cDollarSignKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		private final Keyword c_Keyword_3 = (Keyword)cAlternatives.eContents().get(3);
		private final RuleCall cUNICODE_ESCAPETerminalRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		
		//RegExpIdentifierPart:
		//	UNICODE_LETTER | UNICODE_DIGIT | '$' | '_' | UNICODE_ESCAPE;
		@Override public ParserRule getRule() { return rule; }
		
		//UNICODE_LETTER | UNICODE_DIGIT | '$' | '_' | UNICODE_ESCAPE
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//UNICODE_LETTER
		public RuleCall getUNICODE_LETTERTerminalRuleCall_0() { return cUNICODE_LETTERTerminalRuleCall_0; }
		
		//UNICODE_DIGIT
		public RuleCall getUNICODE_DIGITTerminalRuleCall_1() { return cUNICODE_DIGITTerminalRuleCall_1; }
		
		//'$'
		public Keyword getDollarSignKeyword_2() { return cDollarSignKeyword_2; }
		
		//'_'
		public Keyword get_Keyword_3() { return c_Keyword_3; }
		
		//UNICODE_ESCAPE
		public RuleCall getUNICODE_ESCAPETerminalRuleCall_4() { return cUNICODE_ESCAPETerminalRuleCall_4; }
	}
	public class QuantifierElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.Quantifier");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cSimpleQuantifierParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cExactQuantifierParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//Quantifier:
		//	SimpleQuantifier | ExactQuantifier;
		@Override public ParserRule getRule() { return rule; }
		
		//SimpleQuantifier | ExactQuantifier
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//SimpleQuantifier
		public RuleCall getSimpleQuantifierParserRuleCall_0() { return cSimpleQuantifierParserRuleCall_0; }
		
		//ExactQuantifier
		public RuleCall getExactQuantifierParserRuleCall_1() { return cExactQuantifierParserRuleCall_1; }
	}
	public class SimpleQuantifierElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.SimpleQuantifier");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cQuantifierAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final Alternatives cQuantifierAlternatives_0_0 = (Alternatives)cQuantifierAssignment_0.eContents().get(0);
		private final Keyword cQuantifierPlusSignKeyword_0_0_0 = (Keyword)cQuantifierAlternatives_0_0.eContents().get(0);
		private final Keyword cQuantifierAsteriskKeyword_0_0_1 = (Keyword)cQuantifierAlternatives_0_0.eContents().get(1);
		private final Keyword cQuantifierQuestionMarkKeyword_0_0_2 = (Keyword)cQuantifierAlternatives_0_0.eContents().get(2);
		private final Assignment cNonGreedyAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cNonGreedyQuestionMarkKeyword_1_0 = (Keyword)cNonGreedyAssignment_1.eContents().get(0);
		
		//SimpleQuantifier:
		//	quantifier=('+' | '*' | '?') nonGreedy?='?'?;
		@Override public ParserRule getRule() { return rule; }
		
		//quantifier=('+' | '*' | '?') nonGreedy?='?'?
		public Group getGroup() { return cGroup; }
		
		//quantifier=('+' | '*' | '?')
		public Assignment getQuantifierAssignment_0() { return cQuantifierAssignment_0; }
		
		//('+' | '*' | '?')
		public Alternatives getQuantifierAlternatives_0_0() { return cQuantifierAlternatives_0_0; }
		
		//'+'
		public Keyword getQuantifierPlusSignKeyword_0_0_0() { return cQuantifierPlusSignKeyword_0_0_0; }
		
		//'*'
		public Keyword getQuantifierAsteriskKeyword_0_0_1() { return cQuantifierAsteriskKeyword_0_0_1; }
		
		//'?'
		public Keyword getQuantifierQuestionMarkKeyword_0_0_2() { return cQuantifierQuestionMarkKeyword_0_0_2; }
		
		//nonGreedy?='?'?
		public Assignment getNonGreedyAssignment_1() { return cNonGreedyAssignment_1; }
		
		//'?'
		public Keyword getNonGreedyQuestionMarkKeyword_1_0() { return cNonGreedyQuestionMarkKeyword_1_0; }
	}
	public class ExactQuantifierElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.ExactQuantifier");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cExactQuantifierAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cMinAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cMinINTParserRuleCall_2_0 = (RuleCall)cMinAssignment_2.eContents().get(0);
		private final Alternatives cAlternatives_3 = (Alternatives)cGroup.eContents().get(3);
		private final Group cGroup_3_0 = (Group)cAlternatives_3.eContents().get(0);
		private final Keyword cCommaKeyword_3_0_0 = (Keyword)cGroup_3_0.eContents().get(0);
		private final Assignment cMaxAssignment_3_0_1 = (Assignment)cGroup_3_0.eContents().get(1);
		private final RuleCall cMaxINTParserRuleCall_3_0_1_0 = (RuleCall)cMaxAssignment_3_0_1.eContents().get(0);
		private final Assignment cUnboundedMaxAssignment_3_1 = (Assignment)cAlternatives_3.eContents().get(1);
		private final Keyword cUnboundedMaxCommaKeyword_3_1_0 = (Keyword)cUnboundedMaxAssignment_3_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		private final Assignment cNonGreedyAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final Keyword cNonGreedyQuestionMarkKeyword_5_0 = (Keyword)cNonGreedyAssignment_5.eContents().get(0);
		
		//ExactQuantifier:
		//	{ExactQuantifier} '{' min=INT (',' max=INT | unboundedMax?=',')? '}' nonGreedy?='?'?;
		@Override public ParserRule getRule() { return rule; }
		
		//{ExactQuantifier} '{' min=INT (',' max=INT | unboundedMax?=',')? '}' nonGreedy?='?'?
		public Group getGroup() { return cGroup; }
		
		//{ExactQuantifier}
		public Action getExactQuantifierAction_0() { return cExactQuantifierAction_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_1() { return cLeftCurlyBracketKeyword_1; }
		
		//min=INT
		public Assignment getMinAssignment_2() { return cMinAssignment_2; }
		
		//INT
		public RuleCall getMinINTParserRuleCall_2_0() { return cMinINTParserRuleCall_2_0; }
		
		//(',' max=INT | unboundedMax?=',')?
		public Alternatives getAlternatives_3() { return cAlternatives_3; }
		
		//',' max=INT
		public Group getGroup_3_0() { return cGroup_3_0; }
		
		//','
		public Keyword getCommaKeyword_3_0_0() { return cCommaKeyword_3_0_0; }
		
		//max=INT
		public Assignment getMaxAssignment_3_0_1() { return cMaxAssignment_3_0_1; }
		
		//INT
		public RuleCall getMaxINTParserRuleCall_3_0_1_0() { return cMaxINTParserRuleCall_3_0_1_0; }
		
		//unboundedMax?=','
		public Assignment getUnboundedMaxAssignment_3_1() { return cUnboundedMaxAssignment_3_1; }
		
		//','
		public Keyword getUnboundedMaxCommaKeyword_3_1_0() { return cUnboundedMaxCommaKeyword_3_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_4() { return cRightCurlyBracketKeyword_4; }
		
		//nonGreedy?='?'?
		public Assignment getNonGreedyAssignment_5() { return cNonGreedyAssignment_5; }
		
		//'?'
		public Keyword getNonGreedyQuestionMarkKeyword_5_0() { return cNonGreedyQuestionMarkKeyword_5_0; }
	}
	public class RegularExpressionFlagsElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.RegularExpressionFlags");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cRegularExpressionFlagsAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cFlagsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Alternatives cFlagsAlternatives_1_0 = (Alternatives)cFlagsAssignment_1.eContents().get(0);
		private final RuleCall cFlagsUNICODE_LETTERTerminalRuleCall_1_0_0 = (RuleCall)cFlagsAlternatives_1_0.eContents().get(0);
		private final RuleCall cFlagsUNICODE_ESCAPETerminalRuleCall_1_0_1 = (RuleCall)cFlagsAlternatives_1_0.eContents().get(1);
		
		//RegularExpressionFlags:
		//	{RegularExpressionFlags} flags+=(UNICODE_LETTER | UNICODE_ESCAPE)*;
		@Override public ParserRule getRule() { return rule; }
		
		//{RegularExpressionFlags} flags+=(UNICODE_LETTER | UNICODE_ESCAPE)*
		public Group getGroup() { return cGroup; }
		
		//{RegularExpressionFlags}
		public Action getRegularExpressionFlagsAction_0() { return cRegularExpressionFlagsAction_0; }
		
		//flags+=(UNICODE_LETTER | UNICODE_ESCAPE)*
		public Assignment getFlagsAssignment_1() { return cFlagsAssignment_1; }
		
		//(UNICODE_LETTER | UNICODE_ESCAPE)
		public Alternatives getFlagsAlternatives_1_0() { return cFlagsAlternatives_1_0; }
		
		//UNICODE_LETTER
		public RuleCall getFlagsUNICODE_LETTERTerminalRuleCall_1_0_0() { return cFlagsUNICODE_LETTERTerminalRuleCall_1_0_0; }
		
		//UNICODE_ESCAPE
		public RuleCall getFlagsUNICODE_ESCAPETerminalRuleCall_1_0_1() { return cFlagsUNICODE_ESCAPETerminalRuleCall_1_0_1; }
	}
	public class INTElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.INT");
		private final RuleCall cUNICODE_DIGITTerminalRuleCall = (RuleCall)rule.eContents().get(1);
		
		//INT ecore::EInt:
		//	UNICODE_DIGIT+;
		@Override public ParserRule getRule() { return rule; }
		
		//// TODO validate only valid ints
		//UNICODE_DIGIT+
		public RuleCall getUNICODE_DIGITTerminalRuleCall() { return cUNICODE_DIGITTerminalRuleCall; }
	}
	
	
	private final RegularExpressionLiteralElements pRegularExpressionLiteral;
	private final RegularExpressionBodyElements pRegularExpressionBody;
	private final DisjunctionElements pDisjunction;
	private final AlternativeElements pAlternative;
	private final TermElements pTerm;
	private final AssertionElements pAssertion;
	private final LineStartElements pLineStart;
	private final LineEndElements pLineEnd;
	private final WordBoundaryElements pWordBoundary;
	private final AbstractLookAheadElements pAbstractLookAhead;
	private final AtomElements pAtom;
	private final PatternCharacterElements pPatternCharacter;
	private final WildcardElements pWildcard;
	private final AtomEscapeElements pAtomEscape;
	private final CharacterClassEscapeSequenceElements pCharacterClassEscapeSequence;
	private final CharacterEscapeSequenceElements pCharacterEscapeSequence;
	private final ControlLetterEscapeSequenceElements pControlLetterEscapeSequence;
	private final HexEscapeSequenceElements pHexEscapeSequence;
	private final UnicodeEscapeSequenceElements pUnicodeEscapeSequence;
	private final IdentityEscapeSequenceElements pIdentityEscapeSequence;
	private final DecimalEscapeSequenceElements pDecimalEscapeSequence;
	private final CharacterClassElements pCharacterClass;
	private final CharacterClassElementElements pCharacterClassElement;
	private final CharacterClassAtomElements pCharacterClassAtom;
	private final EscapedCharacterClassAtomElements pEscapedCharacterClassAtom;
	private final BackspaceElements pBackspace;
	private final GroupElements pGroup;
	private final RegExpIdentifierNameElements pRegExpIdentifierName;
	private final RegExpIdentifierStartElements pRegExpIdentifierStart;
	private final RegExpIdentifierPartElements pRegExpIdentifierPart;
	private final QuantifierElements pQuantifier;
	private final SimpleQuantifierElements pSimpleQuantifier;
	private final ExactQuantifierElements pExactQuantifier;
	private final RegularExpressionFlagsElements pRegularExpressionFlags;
	private final INTElements pINT;
	private final TerminalRule tWORD_BOUNDARY;
	private final TerminalRule tNOT_WORD_BOUNDARY;
	private final TerminalRule tCHARACTER_CLASS_ESCAPE;
	private final TerminalRule tCONTROL_ESCAPE;
	private final TerminalRule tCONTROL_LETTER_ESCAPE;
	private final TerminalRule tHEX_ESCAPE;
	private final TerminalRule tUNICODE_ESCAPE;
	private final TerminalRule tDECIMAL_ESCAPE;
	private final TerminalRule tIDENTITY_ESCAPE;
	private final TerminalRule tUNICODE_DIGIT;
	private final TerminalRule tUNICODE_LETTER;
	private final TerminalRule tPATTERN_CHARACTER_NO_DASH;
	
	private final Grammar grammar;
	
	private final UnicodeGrammarAccess gaUnicode;

	@Inject
	public RegularExpressionGrammarAccess(GrammarProvider grammarProvider,
			UnicodeGrammarAccess gaUnicode) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaUnicode = gaUnicode;
		this.pRegularExpressionLiteral = new RegularExpressionLiteralElements();
		this.pRegularExpressionBody = new RegularExpressionBodyElements();
		this.pDisjunction = new DisjunctionElements();
		this.pAlternative = new AlternativeElements();
		this.pTerm = new TermElements();
		this.pAssertion = new AssertionElements();
		this.pLineStart = new LineStartElements();
		this.pLineEnd = new LineEndElements();
		this.pWordBoundary = new WordBoundaryElements();
		this.pAbstractLookAhead = new AbstractLookAheadElements();
		this.pAtom = new AtomElements();
		this.pPatternCharacter = new PatternCharacterElements();
		this.pWildcard = new WildcardElements();
		this.pAtomEscape = new AtomEscapeElements();
		this.pCharacterClassEscapeSequence = new CharacterClassEscapeSequenceElements();
		this.pCharacterEscapeSequence = new CharacterEscapeSequenceElements();
		this.pControlLetterEscapeSequence = new ControlLetterEscapeSequenceElements();
		this.pHexEscapeSequence = new HexEscapeSequenceElements();
		this.pUnicodeEscapeSequence = new UnicodeEscapeSequenceElements();
		this.pIdentityEscapeSequence = new IdentityEscapeSequenceElements();
		this.pDecimalEscapeSequence = new DecimalEscapeSequenceElements();
		this.pCharacterClass = new CharacterClassElements();
		this.pCharacterClassElement = new CharacterClassElementElements();
		this.pCharacterClassAtom = new CharacterClassAtomElements();
		this.pEscapedCharacterClassAtom = new EscapedCharacterClassAtomElements();
		this.pBackspace = new BackspaceElements();
		this.pGroup = new GroupElements();
		this.pRegExpIdentifierName = new RegExpIdentifierNameElements();
		this.pRegExpIdentifierStart = new RegExpIdentifierStartElements();
		this.pRegExpIdentifierPart = new RegExpIdentifierPartElements();
		this.pQuantifier = new QuantifierElements();
		this.pSimpleQuantifier = new SimpleQuantifierElements();
		this.pExactQuantifier = new ExactQuantifierElements();
		this.pRegularExpressionFlags = new RegularExpressionFlagsElements();
		this.pINT = new INTElements();
		this.tWORD_BOUNDARY = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.WORD_BOUNDARY");
		this.tNOT_WORD_BOUNDARY = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.NOT_WORD_BOUNDARY");
		this.tCHARACTER_CLASS_ESCAPE = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.CHARACTER_CLASS_ESCAPE");
		this.tCONTROL_ESCAPE = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.CONTROL_ESCAPE");
		this.tCONTROL_LETTER_ESCAPE = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.CONTROL_LETTER_ESCAPE");
		this.tHEX_ESCAPE = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.HEX_ESCAPE");
		this.tUNICODE_ESCAPE = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.UNICODE_ESCAPE");
		this.tDECIMAL_ESCAPE = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.DECIMAL_ESCAPE");
		this.tIDENTITY_ESCAPE = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.IDENTITY_ESCAPE");
		this.tUNICODE_DIGIT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.UNICODE_DIGIT");
		this.tUNICODE_LETTER = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.UNICODE_LETTER");
		this.tPATTERN_CHARACTER_NO_DASH = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.regex.RegularExpression.PATTERN_CHARACTER_NO_DASH");
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("org.eclipse.n4js.regex.RegularExpression".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
	
	@Override
	public Grammar getGrammar() {
		return grammar;
	}
	
	
	public UnicodeGrammarAccess getUnicodeGrammarAccess() {
		return gaUnicode;
	}

	
	//RegularExpressionLiteral:
	//	'/' body=RegularExpressionBody '/' flags=RegularExpressionFlags;
	public RegularExpressionLiteralElements getRegularExpressionLiteralAccess() {
		return pRegularExpressionLiteral;
	}
	
	public ParserRule getRegularExpressionLiteralRule() {
		return getRegularExpressionLiteralAccess().getRule();
	}
	
	//RegularExpressionBody:
	//	pattern=Disjunction;
	public RegularExpressionBodyElements getRegularExpressionBodyAccess() {
		return pRegularExpressionBody;
	}
	
	public ParserRule getRegularExpressionBodyRule() {
		return getRegularExpressionBodyAccess().getRule();
	}
	
	//Disjunction Pattern:
	//	Alternative ({Disjunction.elements+=current} ('|' elements+=Alternative?)+)?
	//	| {Disjunction} ('|' elements+=Alternative?)*;
	public DisjunctionElements getDisjunctionAccess() {
		return pDisjunction;
	}
	
	public ParserRule getDisjunctionRule() {
		return getDisjunctionAccess().getRule();
	}
	
	//Alternative Pattern:
	//	Term ({Sequence.elements+=current} elements+=Term+)?;
	public AlternativeElements getAlternativeAccess() {
		return pAlternative;
	}
	
	public ParserRule getAlternativeRule() {
		return getAlternativeAccess().getRule();
	}
	
	//Term Pattern:
	//	Assertion | Atom => quantifier=Quantifier?;
	public TermElements getTermAccess() {
		return pTerm;
	}
	
	public ParserRule getTermRule() {
		return getTermAccess().getRule();
	}
	
	//Assertion:
	//	LineStart | LineEnd | WordBoundary | AbstractLookAhead;
	public AssertionElements getAssertionAccess() {
		return pAssertion;
	}
	
	public ParserRule getAssertionRule() {
		return getAssertionAccess().getRule();
	}
	
	//LineStart:
	//	{LineStart} '^';
	public LineStartElements getLineStartAccess() {
		return pLineStart;
	}
	
	public ParserRule getLineStartRule() {
		return getLineStartAccess().getRule();
	}
	
	//LineEnd:
	//	{LineEnd} '$';
	public LineEndElements getLineEndAccess() {
		return pLineEnd;
	}
	
	public ParserRule getLineEndRule() {
		return getLineEndAccess().getRule();
	}
	
	//WordBoundary:
	//	{WordBoundary} (WORD_BOUNDARY | not?=NOT_WORD_BOUNDARY);
	public WordBoundaryElements getWordBoundaryAccess() {
		return pWordBoundary;
	}
	
	public ParserRule getWordBoundaryRule() {
		return getWordBoundaryAccess().getRule();
	}
	
	//AbstractLookAhead:
	//	({LookAhead} ('(?=' | not?='(?!') | {LookBehind} ('(?<=' | not?='(?<!')) pattern=Disjunction ')';
	public AbstractLookAheadElements getAbstractLookAheadAccess() {
		return pAbstractLookAhead;
	}
	
	public ParserRule getAbstractLookAheadRule() {
		return getAbstractLookAheadAccess().getRule();
	}
	
	//Atom Pattern:
	//	PatternCharacter | Wildcard | AtomEscape | CharacterClass | Group;
	public AtomElements getAtomAccess() {
		return pAtom;
	}
	
	public ParserRule getAtomRule() {
		return getAtomAccess().getRule();
	}
	
	//PatternCharacter:
	//	value=(PATTERN_CHARACTER_NO_DASH | UNICODE_LETTER | UNICODE_DIGIT |
	//	'-' | '_' |
	//	',' | '=' | ':' | '!' | '{' | '}' | ']' | '<' | '>');
	public PatternCharacterElements getPatternCharacterAccess() {
		return pPatternCharacter;
	}
	
	public ParserRule getPatternCharacterRule() {
		return getPatternCharacterAccess().getRule();
	}
	
	//Wildcard:
	//	{Wildcard} '.';
	public WildcardElements getWildcardAccess() {
		return pWildcard;
	}
	
	public ParserRule getWildcardRule() {
		return getWildcardAccess().getRule();
	}
	
	//AtomEscape:
	//	DecimalEscapeSequence | CharacterEscapeSequence | ControlLetterEscapeSequence | HexEscapeSequence |
	//	UnicodeEscapeSequence | IdentityEscapeSequence | CharacterClassEscapeSequence;
	public AtomEscapeElements getAtomEscapeAccess() {
		return pAtomEscape;
	}
	
	public ParserRule getAtomEscapeRule() {
		return getAtomEscapeAccess().getRule();
	}
	
	//CharacterClassEscapeSequence:
	//	sequence=CHARACTER_CLASS_ESCAPE;
	public CharacterClassEscapeSequenceElements getCharacterClassEscapeSequenceAccess() {
		return pCharacterClassEscapeSequence;
	}
	
	public ParserRule getCharacterClassEscapeSequenceRule() {
		return getCharacterClassEscapeSequenceAccess().getRule();
	}
	
	//CharacterEscapeSequence:
	//	sequence=CONTROL_ESCAPE;
	public CharacterEscapeSequenceElements getCharacterEscapeSequenceAccess() {
		return pCharacterEscapeSequence;
	}
	
	public ParserRule getCharacterEscapeSequenceRule() {
		return getCharacterEscapeSequenceAccess().getRule();
	}
	
	//ControlLetterEscapeSequence:
	//	sequence=CONTROL_LETTER_ESCAPE;
	public ControlLetterEscapeSequenceElements getControlLetterEscapeSequenceAccess() {
		return pControlLetterEscapeSequence;
	}
	
	public ParserRule getControlLetterEscapeSequenceRule() {
		return getControlLetterEscapeSequenceAccess().getRule();
	}
	
	//HexEscapeSequence:
	//	sequence=HEX_ESCAPE;
	public HexEscapeSequenceElements getHexEscapeSequenceAccess() {
		return pHexEscapeSequence;
	}
	
	public ParserRule getHexEscapeSequenceRule() {
		return getHexEscapeSequenceAccess().getRule();
	}
	
	//UnicodeEscapeSequence:
	//	sequence=UNICODE_ESCAPE;
	public UnicodeEscapeSequenceElements getUnicodeEscapeSequenceAccess() {
		return pUnicodeEscapeSequence;
	}
	
	public ParserRule getUnicodeEscapeSequenceRule() {
		return getUnicodeEscapeSequenceAccess().getRule();
	}
	
	//IdentityEscapeSequence:
	//	sequence=IDENTITY_ESCAPE;
	public IdentityEscapeSequenceElements getIdentityEscapeSequenceAccess() {
		return pIdentityEscapeSequence;
	}
	
	public ParserRule getIdentityEscapeSequenceRule() {
		return getIdentityEscapeSequenceAccess().getRule();
	}
	
	//DecimalEscapeSequence:
	//	sequence=DECIMAL_ESCAPE;
	public DecimalEscapeSequenceElements getDecimalEscapeSequenceAccess() {
		return pDecimalEscapeSequence;
	}
	
	public ParserRule getDecimalEscapeSequenceRule() {
		return getDecimalEscapeSequenceAccess().getRule();
	}
	
	//CharacterClass:
	//	{CharacterClass} '[' => (negated?='^')?
	//	elements+=CharacterClassElement*
	//	']';
	public CharacterClassElements getCharacterClassAccess() {
		return pCharacterClass;
	}
	
	public ParserRule getCharacterClassRule() {
		return getCharacterClassAccess().getRule();
	}
	
	//CharacterClassElement:
	//	CharacterClassAtom => ({CharacterClassRange.left=current} '-' right=CharacterClassAtom)?;
	public CharacterClassElementElements getCharacterClassElementAccess() {
		return pCharacterClassElement;
	}
	
	public ParserRule getCharacterClassElementRule() {
		return getCharacterClassElementAccess().getRule();
	}
	
	//CharacterClassAtom:
	//	EscapedCharacterClassAtom | characters=(',' | '=' | ':' | '!' | '_' |
	//	'-' | '^' | '$' | '.' | '*' | '+' | '?' | '(' | ')' | '[' | '{' | '}' | '|' | '/' | '<' | '>' | '(?' | '(?<' | '(?=' |
	//	'(?!' | '(?<!' | '(?<=' | PATTERN_CHARACTER_NO_DASH | UNICODE_LETTER | UNICODE_DIGIT);
	public CharacterClassAtomElements getCharacterClassAtomAccess() {
		return pCharacterClassAtom;
	}
	
	public ParserRule getCharacterClassAtomRule() {
		return getCharacterClassAtomAccess().getRule();
	}
	
	//EscapedCharacterClassAtom:
	//	DecimalEscapeSequence
	//	| Backspace
	//	| CharacterEscapeSequence
	//	| ControlLetterEscapeSequence
	//	| HexEscapeSequence
	//	| UnicodeEscapeSequence
	//	| IdentityEscapeSequence
	//	| CharacterClassEscapeSequence;
	public EscapedCharacterClassAtomElements getEscapedCharacterClassAtomAccess() {
		return pEscapedCharacterClassAtom;
	}
	
	public ParserRule getEscapedCharacterClassAtomRule() {
		return getEscapedCharacterClassAtomAccess().getRule();
	}
	
	//Backspace:
	//	{Backspace} WORD_BOUNDARY;
	public BackspaceElements getBackspaceAccess() {
		return pBackspace;
	}
	
	public ParserRule getBackspaceRule() {
		return getBackspaceAccess().getRule();
	}
	
	//Group:
	//	{Group} ('('
	//	| nonCapturing?='(?:'
	//	| named?='(?<' name=RegExpIdentifierName '>') pattern=Disjunction ')';
	public GroupElements getGroupAccess() {
		return pGroup;
	}
	
	public ParserRule getGroupRule() {
		return getGroupAccess().getRule();
	}
	
	//RegExpIdentifierName:
	//	RegExpIdentifierStart RegExpIdentifierPart*;
	public RegExpIdentifierNameElements getRegExpIdentifierNameAccess() {
		return pRegExpIdentifierName;
	}
	
	public ParserRule getRegExpIdentifierNameRule() {
		return getRegExpIdentifierNameAccess().getRule();
	}
	
	//RegExpIdentifierStart:
	//	UNICODE_LETTER | '$' | '_' | UNICODE_ESCAPE;
	public RegExpIdentifierStartElements getRegExpIdentifierStartAccess() {
		return pRegExpIdentifierStart;
	}
	
	public ParserRule getRegExpIdentifierStartRule() {
		return getRegExpIdentifierStartAccess().getRule();
	}
	
	//RegExpIdentifierPart:
	//	UNICODE_LETTER | UNICODE_DIGIT | '$' | '_' | UNICODE_ESCAPE;
	public RegExpIdentifierPartElements getRegExpIdentifierPartAccess() {
		return pRegExpIdentifierPart;
	}
	
	public ParserRule getRegExpIdentifierPartRule() {
		return getRegExpIdentifierPartAccess().getRule();
	}
	
	//Quantifier:
	//	SimpleQuantifier | ExactQuantifier;
	public QuantifierElements getQuantifierAccess() {
		return pQuantifier;
	}
	
	public ParserRule getQuantifierRule() {
		return getQuantifierAccess().getRule();
	}
	
	//SimpleQuantifier:
	//	quantifier=('+' | '*' | '?') nonGreedy?='?'?;
	public SimpleQuantifierElements getSimpleQuantifierAccess() {
		return pSimpleQuantifier;
	}
	
	public ParserRule getSimpleQuantifierRule() {
		return getSimpleQuantifierAccess().getRule();
	}
	
	//ExactQuantifier:
	//	{ExactQuantifier} '{' min=INT (',' max=INT | unboundedMax?=',')? '}' nonGreedy?='?'?;
	public ExactQuantifierElements getExactQuantifierAccess() {
		return pExactQuantifier;
	}
	
	public ParserRule getExactQuantifierRule() {
		return getExactQuantifierAccess().getRule();
	}
	
	//RegularExpressionFlags:
	//	{RegularExpressionFlags} flags+=(UNICODE_LETTER | UNICODE_ESCAPE)*;
	public RegularExpressionFlagsElements getRegularExpressionFlagsAccess() {
		return pRegularExpressionFlags;
	}
	
	public ParserRule getRegularExpressionFlagsRule() {
		return getRegularExpressionFlagsAccess().getRule();
	}
	
	//INT ecore::EInt:
	//	UNICODE_DIGIT+;
	public INTElements getINTAccess() {
		return pINT;
	}
	
	public ParserRule getINTRule() {
		return getINTAccess().getRule();
	}
	
	//terminal WORD_BOUNDARY:
	//	'\\' 'b';
	public TerminalRule getWORD_BOUNDARYRule() {
		return tWORD_BOUNDARY;
	}
	
	//terminal NOT_WORD_BOUNDARY:
	//	'\\' 'B';
	public TerminalRule getNOT_WORD_BOUNDARYRule() {
		return tNOT_WORD_BOUNDARY;
	}
	
	//terminal CHARACTER_CLASS_ESCAPE:
	//	'\\' ('d' | 'D' | 's' | 'S' | 'w' | 'W');
	public TerminalRule getCHARACTER_CLASS_ESCAPERule() {
		return tCHARACTER_CLASS_ESCAPE;
	}
	
	//terminal CONTROL_ESCAPE:
	//	'\\' ('f' | 'n' | 'r' | 't' | 'v');
	public TerminalRule getCONTROL_ESCAPERule() {
		return tCONTROL_ESCAPE;
	}
	
	//terminal CONTROL_LETTER_ESCAPE:
	//	'\\' 'c' ('a'..'z' | 'A'..'Z')?;
	public TerminalRule getCONTROL_LETTER_ESCAPERule() {
		return tCONTROL_LETTER_ESCAPE;
	}
	
	//terminal HEX_ESCAPE:
	//	'\\' 'x' (HEX_DIGIT HEX_DIGIT?)?;
	public TerminalRule getHEX_ESCAPERule() {
		return tHEX_ESCAPE;
	}
	
	//terminal UNICODE_ESCAPE:
	//	'\\' ('u' (HEX_DIGIT (HEX_DIGIT (HEX_DIGIT HEX_DIGIT?)?)?
	//	| '{' HEX_DIGIT* '}'?)?)?;
	public TerminalRule getUNICODE_ESCAPERule() {
		return tUNICODE_ESCAPE;
	}
	
	//terminal DECIMAL_ESCAPE:
	//	'\\' DECIMAL_INTEGER_LITERAL_FRAGMENT;
	public TerminalRule getDECIMAL_ESCAPERule() {
		return tDECIMAL_ESCAPE;
	}
	
	//terminal IDENTITY_ESCAPE:
	//	'\\' .;
	public TerminalRule getIDENTITY_ESCAPERule() {
		return tIDENTITY_ESCAPE;
	}
	
	//terminal UNICODE_DIGIT:
	//	UNICODE_DIGIT_FRAGMENT;
	public TerminalRule getUNICODE_DIGITRule() {
		return tUNICODE_DIGIT;
	}
	
	//terminal UNICODE_LETTER:
	//	UNICODE_LETTER_FRAGMENT;
	public TerminalRule getUNICODE_LETTERRule() {
		return tUNICODE_LETTER;
	}
	
	//terminal PATTERN_CHARACTER_NO_DASH:
	//	!('^' | '$' | '\\' | '.' | '*' | '+' | '?' | '(' | ')' | '[' | ']' | '{' | '}' | '|' | '-' | '<' | '>');
	public TerminalRule getPATTERN_CHARACTER_NO_DASHRule() {
		return tPATTERN_CHARACTER_NO_DASH;
	}
	
	//terminal fragment HEX_DIGIT:
	//	DECIMAL_DIGIT_FRAGMENT | 'a'..'f' | 'A'..'F';
	public TerminalRule getHEX_DIGITRule() {
		return gaUnicode.getHEX_DIGITRule();
	}
	
	//terminal fragment DECIMAL_INTEGER_LITERAL_FRAGMENT:
	//	'0'
	//	| '1'..'9' DECIMAL_DIGIT_FRAGMENT*;
	public TerminalRule getDECIMAL_INTEGER_LITERAL_FRAGMENTRule() {
		return gaUnicode.getDECIMAL_INTEGER_LITERAL_FRAGMENTRule();
	}
	
	//terminal fragment DECIMAL_DIGIT_FRAGMENT:
	//	'0'..'9';
	public TerminalRule getDECIMAL_DIGIT_FRAGMENTRule() {
		return gaUnicode.getDECIMAL_DIGIT_FRAGMENTRule();
	}
	
	//terminal fragment ZWJ:
	//	'\\u200D';
	public TerminalRule getZWJRule() {
		return gaUnicode.getZWJRule();
	}
	
	//terminal fragment ZWNJ:
	//	'\\u200C';
	public TerminalRule getZWNJRule() {
		return gaUnicode.getZWNJRule();
	}
	
	//terminal fragment BOM:
	//	'\\uFEFF';
	public TerminalRule getBOMRule() {
		return gaUnicode.getBOMRule();
	}
	
	//terminal fragment WHITESPACE_FRAGMENT:
	//	'\\u0009' | '\\u000B' | '\\u000C' | '\\u0020' | '\\u00A0' | BOM | UNICODE_SPACE_SEPARATOR_FRAGMENT;
	public TerminalRule getWHITESPACE_FRAGMENTRule() {
		return gaUnicode.getWHITESPACE_FRAGMENTRule();
	}
	
	//terminal fragment LINE_TERMINATOR_FRAGMENT:
	//	'\\u000A' | '\\u000D' | '\\u2028' | '\\u2029';
	public TerminalRule getLINE_TERMINATOR_FRAGMENTRule() {
		return gaUnicode.getLINE_TERMINATOR_FRAGMENTRule();
	}
	
	//terminal fragment LINE_TERMINATOR_SEQUENCE_FRAGMENT:
	//	'\\u000A' | '\\u000D' '\\u000A'? | '\\u2028' | '\\u2029';
	public TerminalRule getLINE_TERMINATOR_SEQUENCE_FRAGMENTRule() {
		return gaUnicode.getLINE_TERMINATOR_SEQUENCE_FRAGMENTRule();
	}
	
	//terminal fragment SL_COMMENT_FRAGMENT:
	//	'//' !LINE_TERMINATOR_FRAGMENT*;
	public TerminalRule getSL_COMMENT_FRAGMENTRule() {
		return gaUnicode.getSL_COMMENT_FRAGMENTRule();
	}
	
	//terminal fragment ML_COMMENT_FRAGMENT:
	//	'/*'->'*/';
	public TerminalRule getML_COMMENT_FRAGMENTRule() {
		return gaUnicode.getML_COMMENT_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_COMBINING_MARK_FRAGMENT: // any character in the Unicode categories
	//// ―Non-spacing mark (Mn)
	//// ―Combining spacing mark (Mc)
	//	'\\u0300'..'\\u036F' | '\\u0483'..'\\u0487' | '\\u0591'..'\\u05BD' | '\\u05BF' | '\\u05C1'..'\\u05C2' | '\\u05C4'..'\\u05C5' |
	//	'\\u05C7' | '\\u0610'..'\\u061A' | '\\u064B'..'\\u065F' | '\\u0670' | '\\u06D6'..'\\u06DC' | '\\u06DF'..'\\u06E4' |
	//	'\\u06E7'..'\\u06E8' | '\\u06EA'..'\\u06ED' | '\\u0711' | '\\u0730'..'\\u074A' | '\\u07A6'..'\\u07B0' | '\\u07EB'..'\\u07F3' |
	//	'\\u0816'..'\\u0819' | '\\u081B'..'\\u0823' | '\\u0825'..'\\u0827' | '\\u0829'..'\\u082D' | '\\u0859'..'\\u085B' |
	//	'\\u08E3'..'\\u0903' | '\\u093A'..'\\u093C' | '\\u093E'..'\\u094F' | '\\u0951'..'\\u0957' | '\\u0962'..'\\u0963' |
	//	'\\u0981'..'\\u0983' | '\\u09BC' | '\\u09BE'..'\\u09C4' | '\\u09C7'..'\\u09C8' | '\\u09CB'..'\\u09CD' | '\\u09D7' |
	//	'\\u09E2'..'\\u09E3' | '\\u0A01'..'\\u0A03' | '\\u0A3C' | '\\u0A3E'..'\\u0A42' | '\\u0A47'..'\\u0A48' | '\\u0A4B'..'\\u0A4D' |
	//	'\\u0A51' | '\\u0A70'..'\\u0A71' | '\\u0A75' | '\\u0A81'..'\\u0A83' | '\\u0ABC' | '\\u0ABE'..'\\u0AC5' | '\\u0AC7'..'\\u0AC9' |
	//	'\\u0ACB'..'\\u0ACD' | '\\u0AE2'..'\\u0AE3' | '\\u0B01'..'\\u0B03' | '\\u0B3C' | '\\u0B3E'..'\\u0B44' | '\\u0B47'..'\\u0B48' |
	//	'\\u0B4B'..'\\u0B4D' | '\\u0B56'..'\\u0B57' | '\\u0B62'..'\\u0B63' | '\\u0B82' | '\\u0BBE'..'\\u0BC2' | '\\u0BC6'..'\\u0BC8' |
	//	'\\u0BCA'..'\\u0BCD' | '\\u0BD7' | '\\u0C00'..'\\u0C03' | '\\u0C3E'..'\\u0C44' | '\\u0C46'..'\\u0C48' | '\\u0C4A'..'\\u0C4D' |
	//	'\\u0C55'..'\\u0C56' | '\\u0C62'..'\\u0C63' | '\\u0C81'..'\\u0C83' | '\\u0CBC' | '\\u0CBE'..'\\u0CC4' | '\\u0CC6'..'\\u0CC8' |
	//	'\\u0CCA'..'\\u0CCD' | '\\u0CD5'..'\\u0CD6' | '\\u0CE2'..'\\u0CE3' | '\\u0D01'..'\\u0D03' | '\\u0D3E'..'\\u0D44' |
	//	'\\u0D46'..'\\u0D48' | '\\u0D4A'..'\\u0D4D' | '\\u0D57' | '\\u0D62'..'\\u0D63' | '\\u0D82'..'\\u0D83' | '\\u0DCA' |
	//	'\\u0DCF'..'\\u0DD4' | '\\u0DD6' | '\\u0DD8'..'\\u0DDF' | '\\u0DF2'..'\\u0DF3' | '\\u0E31' | '\\u0E34'..'\\u0E3A' |
	//	'\\u0E47'..'\\u0E4E' | '\\u0EB1' | '\\u0EB4'..'\\u0EB9' | '\\u0EBB'..'\\u0EBC' | '\\u0EC8'..'\\u0ECD' | '\\u0F18'..'\\u0F19' |
	//	'\\u0F35' | '\\u0F37' | '\\u0F39' | '\\u0F3E'..'\\u0F3F' | '\\u0F71'..'\\u0F84' | '\\u0F86'..'\\u0F87' | '\\u0F8D'..'\\u0F97' |
	//	'\\u0F99'..'\\u0FBC' | '\\u0FC6' | '\\u102B'..'\\u103E' | '\\u1056'..'\\u1059' | '\\u105E'..'\\u1060' | '\\u1062'..'\\u1064' |
	//	'\\u1067'..'\\u106D' | '\\u1071'..'\\u1074' | '\\u1082'..'\\u108D' | '\\u108F' | '\\u109A'..'\\u109D' | '\\u135D'..'\\u135F' |
	//	'\\u1712'..'\\u1714' | '\\u1732'..'\\u1734' | '\\u1752'..'\\u1753' | '\\u1772'..'\\u1773' | '\\u17B4'..'\\u17D3' | '\\u17DD' |
	//	'\\u180B'..'\\u180D' | '\\u18A9' | '\\u1920'..'\\u192B' | '\\u1930'..'\\u193B' | '\\u1A17'..'\\u1A1B' | '\\u1A55'..'\\u1A5E' |
	//	'\\u1A60'..'\\u1A7C' | '\\u1A7F' | '\\u1AB0'..'\\u1ABD' | '\\u1B00'..'\\u1B04' | '\\u1B34'..'\\u1B44' | '\\u1B6B'..'\\u1B73' |
	//	'\\u1B80'..'\\u1B82' | '\\u1BA1'..'\\u1BAD' | '\\u1BE6'..'\\u1BF3' | '\\u1C24'..'\\u1C37' | '\\u1CD0'..'\\u1CD2' |
	//	'\\u1CD4'..'\\u1CE8' | '\\u1CED' | '\\u1CF2'..'\\u1CF4' | '\\u1CF8'..'\\u1CF9' | '\\u1DC0'..'\\u1DF5' | '\\u1DFC'..'\\u1DFF' |
	//	'\\u20D0'..'\\u20DC' | '\\u20E1' | '\\u20E5'..'\\u20F0' | '\\u2CEF'..'\\u2CF1' | '\\u2D7F' | '\\u2DE0'..'\\u2DFF' |
	//	'\\u302A'..'\\u302F' | '\\u3099'..'\\u309A' | '\\uA66F' | '\\uA674'..'\\uA67D' | '\\uA69E'..'\\uA69F' | '\\uA6F0'..'\\uA6F1' |
	//	'\\uA802' | '\\uA806' | '\\uA80B' | '\\uA823'..'\\uA827' | '\\uA880'..'\\uA881' | '\\uA8B4'..'\\uA8C4' | '\\uA8E0'..'\\uA8F1' |
	//	'\\uA926'..'\\uA92D' | '\\uA947'..'\\uA953' | '\\uA980'..'\\uA983' | '\\uA9B3'..'\\uA9C0' | '\\uA9E5' | '\\uAA29'..'\\uAA36' |
	//	'\\uAA43' | '\\uAA4C'..'\\uAA4D' | '\\uAA7B'..'\\uAA7D' | '\\uAAB0' | '\\uAAB2'..'\\uAAB4' | '\\uAAB7'..'\\uAAB8' |
	//	'\\uAABE'..'\\uAABF' | '\\uAAC1' | '\\uAAEB'..'\\uAAEF' | '\\uAAF5'..'\\uAAF6' | '\\uABE3'..'\\uABEA' | '\\uABEC'..'\\uABED' |
	//	'\\uFB1E' | '\\uFE00'..'\\uFE0F' | '\\uFE20'..'\\uFE2F';
	public TerminalRule getUNICODE_COMBINING_MARK_FRAGMENTRule() {
		return gaUnicode.getUNICODE_COMBINING_MARK_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_DIGIT_FRAGMENT: // any character in the Unicode categories
	//// ―Decimal number (Nd)
	//	'\\u0030'..'\\u0039' | '\\u0660'..'\\u0669' | '\\u06F0'..'\\u06F9' | '\\u07C0'..'\\u07C9' | '\\u0966'..'\\u096F' |
	//	'\\u09E6'..'\\u09EF' | '\\u0A66'..'\\u0A6F' | '\\u0AE6'..'\\u0AEF' | '\\u0B66'..'\\u0B6F' | '\\u0BE6'..'\\u0BEF' |
	//	'\\u0C66'..'\\u0C6F' | '\\u0CE6'..'\\u0CEF' | '\\u0D66'..'\\u0D6F' | '\\u0DE6'..'\\u0DEF' | '\\u0E50'..'\\u0E59' |
	//	'\\u0ED0'..'\\u0ED9' | '\\u0F20'..'\\u0F29' | '\\u1040'..'\\u1049' | '\\u1090'..'\\u1099' | '\\u17E0'..'\\u17E9' |
	//	'\\u1810'..'\\u1819' | '\\u1946'..'\\u194F' | '\\u19D0'..'\\u19D9' | '\\u1A80'..'\\u1A89' | '\\u1A90'..'\\u1A99' |
	//	'\\u1B50'..'\\u1B59' | '\\u1BB0'..'\\u1BB9' | '\\u1C40'..'\\u1C49' | '\\u1C50'..'\\u1C59' | '\\uA620'..'\\uA629' |
	//	'\\uA8D0'..'\\uA8D9' | '\\uA900'..'\\uA909' | '\\uA9D0'..'\\uA9D9' | '\\uA9F0'..'\\uA9F9' | '\\uAA50'..'\\uAA59' |
	//	'\\uABF0'..'\\uABF9' | '\\uFF10'..'\\uFF19';
	public TerminalRule getUNICODE_DIGIT_FRAGMENTRule() {
		return gaUnicode.getUNICODE_DIGIT_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT: // any character in the Unicode categories
	//// ―Connector punctuation (Pc)
	//	'\\u005F' | '\\u203F'..'\\u2040' | '\\u2054' | '\\uFE33'..'\\uFE34' | '\\uFE4D'..'\\uFE4F' | '\\uFF3F';
	public TerminalRule getUNICODE_CONNECTOR_PUNCTUATION_FRAGMENTRule() {
		return gaUnicode.getUNICODE_CONNECTOR_PUNCTUATION_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_LETTER_FRAGMENT: // any character in the Unicode categories
	//// ―Uppercase letter (Lu)
	//// ―Lowercase letter (Ll)
	//// ―Titlecase letter (Lt)
	//// ―Modifier letter (Lm)
	//// ―Other letter (Lo)
	//// ―Letter number (Nl)
	//	'\\u0041'..'\\u005A' | '\\u0061'..'\\u007A' | '\\u00AA' | '\\u00B5' | '\\u00BA' | '\\u00C0'..'\\u00D6' | '\\u00D8'..'\\u00F6' |
	//	'\\u00F8'..'\\u02C1' | '\\u02C6'..'\\u02D1' | '\\u02E0'..'\\u02E4' | '\\u02EC' | '\\u02EE' | '\\u0370'..'\\u0374' |
	//	'\\u0376'..'\\u0377' | '\\u037A'..'\\u037D' | '\\u037F' | '\\u0386' | '\\u0388'..'\\u038A' | '\\u038C' | '\\u038E'..'\\u03A1' |
	//	'\\u03A3'..'\\u03F5' | '\\u03F7'..'\\u0481' | '\\u048A'..'\\u052F' | '\\u0531'..'\\u0556' | '\\u0559' | '\\u0561'..'\\u0587' |
	//	'\\u05D0'..'\\u05EA' | '\\u05F0'..'\\u05F2' | '\\u0620'..'\\u064A' | '\\u066E'..'\\u066F' | '\\u0671'..'\\u06D3' | '\\u06D5' |
	//	'\\u06E5'..'\\u06E6' | '\\u06EE'..'\\u06EF' | '\\u06FA'..'\\u06FC' | '\\u06FF' | '\\u0710' | '\\u0712'..'\\u072F' |
	//	'\\u074D'..'\\u07A5' | '\\u07B1' | '\\u07CA'..'\\u07EA' | '\\u07F4'..'\\u07F5' | '\\u07FA' | '\\u0800'..'\\u0815' | '\\u081A' |
	//	'\\u0824' | '\\u0828' | '\\u0840'..'\\u0858' | '\\u08A0'..'\\u08B4' | '\\u0904'..'\\u0939' | '\\u093D' | '\\u0950' |
	//	'\\u0958'..'\\u0961' | '\\u0971'..'\\u0980' | '\\u0985'..'\\u098C' | '\\u098F'..'\\u0990' | '\\u0993'..'\\u09A8' |
	//	'\\u09AA'..'\\u09B0' | '\\u09B2' | '\\u09B6'..'\\u09B9' | '\\u09BD' | '\\u09CE' | '\\u09DC'..'\\u09DD' | '\\u09DF'..'\\u09E1' |
	//	'\\u09F0'..'\\u09F1' | '\\u0A05'..'\\u0A0A' | '\\u0A0F'..'\\u0A10' | '\\u0A13'..'\\u0A28' | '\\u0A2A'..'\\u0A30' |
	//	'\\u0A32'..'\\u0A33' | '\\u0A35'..'\\u0A36' | '\\u0A38'..'\\u0A39' | '\\u0A59'..'\\u0A5C' | '\\u0A5E' | '\\u0A72'..'\\u0A74' |
	//	'\\u0A85'..'\\u0A8D' | '\\u0A8F'..'\\u0A91' | '\\u0A93'..'\\u0AA8' | '\\u0AAA'..'\\u0AB0' | '\\u0AB2'..'\\u0AB3' |
	//	'\\u0AB5'..'\\u0AB9' | '\\u0ABD' | '\\u0AD0' | '\\u0AE0'..'\\u0AE1' | '\\u0AF9' | '\\u0B05'..'\\u0B0C' | '\\u0B0F'..'\\u0B10' |
	//	'\\u0B13'..'\\u0B28' | '\\u0B2A'..'\\u0B30' | '\\u0B32'..'\\u0B33' | '\\u0B35'..'\\u0B39' | '\\u0B3D' | '\\u0B5C'..'\\u0B5D' |
	//	'\\u0B5F'..'\\u0B61' | '\\u0B71' | '\\u0B83' | '\\u0B85'..'\\u0B8A' | '\\u0B8E'..'\\u0B90' | '\\u0B92'..'\\u0B95' |
	//	'\\u0B99'..'\\u0B9A' | '\\u0B9C' | '\\u0B9E'..'\\u0B9F' | '\\u0BA3'..'\\u0BA4' | '\\u0BA8'..'\\u0BAA' | '\\u0BAE'..'\\u0BB9' |
	//	'\\u0BD0' | '\\u0C05'..'\\u0C0C' | '\\u0C0E'..'\\u0C10' | '\\u0C12'..'\\u0C28' | '\\u0C2A'..'\\u0C39' | '\\u0C3D' |
	//	'\\u0C58'..'\\u0C5A' | '\\u0C60'..'\\u0C61' | '\\u0C85'..'\\u0C8C' | '\\u0C8E'..'\\u0C90' | '\\u0C92'..'\\u0CA8' |
	//	'\\u0CAA'..'\\u0CB3' | '\\u0CB5'..'\\u0CB9' | '\\u0CBD' | '\\u0CDE' | '\\u0CE0'..'\\u0CE1' | '\\u0CF1'..'\\u0CF2' |
	//	'\\u0D05'..'\\u0D0C' | '\\u0D0E'..'\\u0D10' | '\\u0D12'..'\\u0D3A' | '\\u0D3D' | '\\u0D4E' | '\\u0D5F'..'\\u0D61' |
	//	'\\u0D7A'..'\\u0D7F' | '\\u0D85'..'\\u0D96' | '\\u0D9A'..'\\u0DB1' | '\\u0DB3'..'\\u0DBB' | '\\u0DBD' | '\\u0DC0'..'\\u0DC6' |
	//	'\\u0E01'..'\\u0E30' | '\\u0E32'..'\\u0E33' | '\\u0E40'..'\\u0E46' | '\\u0E81'..'\\u0E82' | '\\u0E84' | '\\u0E87'..'\\u0E88' |
	//	'\\u0E8A' | '\\u0E8D' | '\\u0E94'..'\\u0E97' | '\\u0E99'..'\\u0E9F' | '\\u0EA1'..'\\u0EA3' | '\\u0EA5' | '\\u0EA7' |
	//	'\\u0EAA'..'\\u0EAB' | '\\u0EAD'..'\\u0EB0' | '\\u0EB2'..'\\u0EB3' | '\\u0EBD' | '\\u0EC0'..'\\u0EC4' | '\\u0EC6' |
	//	'\\u0EDC'..'\\u0EDF' | '\\u0F00' | '\\u0F40'..'\\u0F47' | '\\u0F49'..'\\u0F6C' | '\\u0F88'..'\\u0F8C' | '\\u1000'..'\\u102A' |
	//	'\\u103F' | '\\u1050'..'\\u1055' | '\\u105A'..'\\u105D' | '\\u1061' | '\\u1065'..'\\u1066' | '\\u106E'..'\\u1070' |
	//	'\\u1075'..'\\u1081' | '\\u108E' | '\\u10A0'..'\\u10C5' | '\\u10C7' | '\\u10CD' | '\\u10D0'..'\\u10FA' | '\\u10FC'..'\\u1248' |
	//	'\\u124A'..'\\u124D' | '\\u1250'..'\\u1256' | '\\u1258' | '\\u125A'..'\\u125D' | '\\u1260'..'\\u1288' | '\\u128A'..'\\u128D' |
	//	'\\u1290'..'\\u12B0' | '\\u12B2'..'\\u12B5' | '\\u12B8'..'\\u12BE' | '\\u12C0' | '\\u12C2'..'\\u12C5' | '\\u12C8'..'\\u12D6' |
	//	'\\u12D8'..'\\u1310' | '\\u1312'..'\\u1315' | '\\u1318'..'\\u135A' | '\\u1380'..'\\u138F' | '\\u13A0'..'\\u13F5' |
	//	'\\u13F8'..'\\u13FD' | '\\u1401'..'\\u166C' | '\\u166F'..'\\u167F' | '\\u1681'..'\\u169A' | '\\u16A0'..'\\u16EA' |
	//	'\\u16EE'..'\\u16F8' | '\\u1700'..'\\u170C' | '\\u170E'..'\\u1711' | '\\u1720'..'\\u1731' | '\\u1740'..'\\u1751' |
	//	'\\u1760'..'\\u176C' | '\\u176E'..'\\u1770' | '\\u1780'..'\\u17B3' | '\\u17D7' | '\\u17DC' | '\\u1820'..'\\u1877' |
	//	'\\u1880'..'\\u18A8' | '\\u18AA' | '\\u18B0'..'\\u18F5' | '\\u1900'..'\\u191E' | '\\u1950'..'\\u196D' | '\\u1970'..'\\u1974' |
	//	'\\u1980'..'\\u19AB' | '\\u19B0'..'\\u19C9' | '\\u1A00'..'\\u1A16' | '\\u1A20'..'\\u1A54' | '\\u1AA7' | '\\u1B05'..'\\u1B33' |
	//	'\\u1B45'..'\\u1B4B' | '\\u1B83'..'\\u1BA0' | '\\u1BAE'..'\\u1BAF' | '\\u1BBA'..'\\u1BE5' | '\\u1C00'..'\\u1C23' |
	//	'\\u1C4D'..'\\u1C4F' | '\\u1C5A'..'\\u1C7D' | '\\u1CE9'..'\\u1CEC' | '\\u1CEE'..'\\u1CF1' | '\\u1CF5'..'\\u1CF6' |
	//	'\\u1D00'..'\\u1DBF' | '\\u1E00'..'\\u1F15' | '\\u1F18'..'\\u1F1D' | '\\u1F20'..'\\u1F45' | '\\u1F48'..'\\u1F4D' |
	//	'\\u1F50'..'\\u1F57' | '\\u1F59' | '\\u1F5B' | '\\u1F5D' | '\\u1F5F'..'\\u1F7D' | '\\u1F80'..'\\u1FB4' | '\\u1FB6'..'\\u1FBC' |
	//	'\\u1FBE' | '\\u1FC2'..'\\u1FC4' | '\\u1FC6'..'\\u1FCC' | '\\u1FD0'..'\\u1FD3' | '\\u1FD6'..'\\u1FDB' | '\\u1FE0'..'\\u1FEC' |
	//	'\\u1FF2'..'\\u1FF4' | '\\u1FF6'..'\\u1FFC' | '\\u2071' | '\\u207F' | '\\u2090'..'\\u209C' | '\\u2102' | '\\u2107' |
	//	'\\u210A'..'\\u2113' | '\\u2115' | '\\u2119'..'\\u211D' | '\\u2124' | '\\u2126' | '\\u2128' | '\\u212A'..'\\u212D' |
	//	'\\u212F'..'\\u2139' | '\\u213C'..'\\u213F' | '\\u2145'..'\\u2149' | '\\u214E' | '\\u2160'..'\\u2188' | '\\u2C00'..'\\u2C2E' |
	//	'\\u2C30'..'\\u2C5E' | '\\u2C60'..'\\u2CE4' | '\\u2CEB'..'\\u2CEE' | '\\u2CF2'..'\\u2CF3' | '\\u2D00'..'\\u2D25' | '\\u2D27' |
	//	'\\u2D2D' | '\\u2D30'..'\\u2D67' | '\\u2D6F' | '\\u2D80'..'\\u2D96' | '\\u2DA0'..'\\u2DA6' | '\\u2DA8'..'\\u2DAE' |
	//	'\\u2DB0'..'\\u2DB6' | '\\u2DB8'..'\\u2DBE' | '\\u2DC0'..'\\u2DC6' | '\\u2DC8'..'\\u2DCE' | '\\u2DD0'..'\\u2DD6' |
	//	'\\u2DD8'..'\\u2DDE' | '\\u2E2F' | '\\u3005'..'\\u3007' | '\\u3021'..'\\u3029' | '\\u3031'..'\\u3035' | '\\u3038'..'\\u303C' |
	//	'\\u3041'..'\\u3096' | '\\u309D'..'\\u309F' | '\\u30A1'..'\\u30FA' | '\\u30FC'..'\\u30FF' | '\\u3105'..'\\u312D' |
	//	'\\u3131'..'\\u318E' | '\\u31A0'..'\\u31BA' | '\\u31F0'..'\\u31FF' | '\\u3400'..'\\u4DB5' | '\\u4E00'..'\\u9FD5' |
	//	'\\uA000'..'\\uA48C' | '\\uA4D0'..'\\uA4FD' | '\\uA500'..'\\uA60C' | '\\uA610'..'\\uA61F' | '\\uA62A'..'\\uA62B' |
	//	'\\uA640'..'\\uA66E' | '\\uA67F'..'\\uA69D' | '\\uA6A0'..'\\uA6EF' | '\\uA717'..'\\uA71F' | '\\uA722'..'\\uA788' |
	//	'\\uA78B'..'\\uA7AD' | '\\uA7B0'..'\\uA7B7' | '\\uA7F7'..'\\uA801' | '\\uA803'..'\\uA805' | '\\uA807'..'\\uA80A' |
	//	'\\uA80C'..'\\uA822' | '\\uA840'..'\\uA873' | '\\uA882'..'\\uA8B3' | '\\uA8F2'..'\\uA8F7' | '\\uA8FB' | '\\uA8FD' |
	//	'\\uA90A'..'\\uA925' | '\\uA930'..'\\uA946' | '\\uA960'..'\\uA97C' | '\\uA984'..'\\uA9B2' | '\\uA9CF' | '\\uA9E0'..'\\uA9E4' |
	//	'\\uA9E6'..'\\uA9EF' | '\\uA9FA'..'\\uA9FE' | '\\uAA00'..'\\uAA28' | '\\uAA40'..'\\uAA42' | '\\uAA44'..'\\uAA4B' |
	//	'\\uAA60'..'\\uAA76' | '\\uAA7A' | '\\uAA7E'..'\\uAAAF' | '\\uAAB1' | '\\uAAB5'..'\\uAAB6' | '\\uAAB9'..'\\uAABD' | '\\uAAC0' |
	//	'\\uAAC2' | '\\uAADB'..'\\uAADD' | '\\uAAE0'..'\\uAAEA' | '\\uAAF2'..'\\uAAF4' | '\\uAB01'..'\\uAB06' | '\\uAB09'..'\\uAB0E' |
	//	'\\uAB11'..'\\uAB16' | '\\uAB20'..'\\uAB26' | '\\uAB28'..'\\uAB2E' | '\\uAB30'..'\\uAB5A' | '\\uAB5C'..'\\uAB65' |
	//	'\\uAB70'..'\\uABE2' | '\\uAC00'..'\\uD7A3' | '\\uD7B0'..'\\uD7C6' | '\\uD7CB'..'\\uD7FB' | '\\uF900'..'\\uFA6D' |
	//	'\\uFA70'..'\\uFAD9' | '\\uFB00'..'\\uFB06' | '\\uFB13'..'\\uFB17' | '\\uFB1D' | '\\uFB1F'..'\\uFB28' | '\\uFB2A'..'\\uFB36' |
	//	'\\uFB38'..'\\uFB3C' | '\\uFB3E' | '\\uFB40'..'\\uFB41' | '\\uFB43'..'\\uFB44' | '\\uFB46'..'\\uFBB1' | '\\uFBD3'..'\\uFD3D' |
	//	'\\uFD50'..'\\uFD8F' | '\\uFD92'..'\\uFDC7' | '\\uFDF0'..'\\uFDFB' | '\\uFE70'..'\\uFE74' | '\\uFE76'..'\\uFEFC' |
	//	'\\uFF21'..'\\uFF3A' | '\\uFF41'..'\\uFF5A' | '\\uFF66'..'\\uFFBE' | '\\uFFC2'..'\\uFFC7' | '\\uFFCA'..'\\uFFCF' |
	//	'\\uFFD2'..'\\uFFD7' | '\\uFFDA'..'\\uFFDC';
	public TerminalRule getUNICODE_LETTER_FRAGMENTRule() {
		return gaUnicode.getUNICODE_LETTER_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_SPACE_SEPARATOR_FRAGMENT: // any character in the Unicode categories
	//// ―space separator (Zs)
	//	'\\u0020' | '\\u00A0' | '\\u1680' | '\\u2000'..'\\u200A' | '\\u202F' | '\\u205F' | '\\u3000';
	public TerminalRule getUNICODE_SPACE_SEPARATOR_FRAGMENTRule() {
		return gaUnicode.getUNICODE_SPACE_SEPARATOR_FRAGMENTRule();
	}
	
	//terminal fragment ANY_OTHER:
	//	.;
	public TerminalRule getANY_OTHERRule() {
		return gaUnicode.getANY_OTHERRule();
	}
}
