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
package org.eclipse.n4js.semver.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import org.eclipse.n4js.common.unicode.services.UnicodeGrammarAccess;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.EnumLiteralDeclaration;
import org.eclipse.xtext.EnumRule;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.service.AbstractElementFinder.AbstractEnumRuleElementFinder;
import org.eclipse.xtext.service.AbstractElementFinder.AbstractGrammarElementFinder;
import org.eclipse.xtext.service.GrammarProvider;

@Singleton
public class SemanticVersioningGrammarAccess extends AbstractGrammarElementFinder {
	
	public class VersionRangeSetElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.SemanticVersioning.VersionRangeSet");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cRangesAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cRangesVersionRangeParserRuleCall_0_0 = (RuleCall)cRangesAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cVerticalLineVerticalLineKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cRangesAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cRangesVersionRangeParserRuleCall_1_1_0 = (RuleCall)cRangesAssignment_1_1.eContents().get(0);
		
		//// This grammar is an adapted version of the BNF found at:
		////  https://docs.npmjs.com/misc/semver
		////
		////	range-set  ::= range ( logical-or range ) *
		////	logical-or ::= ( ' ' ) * '||' ( ' ' ) *
		////	range      ::= hyphen | simple ( ' ' simple ) * | ''
		////	hyphen     ::= partial ' - ' partial
		////	simple     ::= primitive | partial | tilde | caret
		////	primitive  ::= ( '<' | '>' | '>=' | '<=' | '=' | ) partial
		////	partial    ::= xr ( '.' xr ( '.' xr qualifier ? )? )?
		////	xr         ::= 'x' | 'X' | '*' | nr
		////	nr         ::= '0' | ['1'-'9'] ( ['0'-'9'] ) *
		////	tilde      ::= '~' partial
		////	caret      ::= '^' partial
		////	qualifier  ::= ( '-' pre )? ( '+' build )?
		////	pre        ::= parts
		////	build      ::= parts
		////	parts      ::= part ( '.' part ) *
		////	part       ::= nr | [-0-9A-Za-z]+
		//VersionRangeSet:
		//	ranges+=VersionRange? ('||' ranges+=VersionRange)*;
		@Override public ParserRule getRule() { return rule; }
		
		//ranges+=VersionRange? ('||' ranges+=VersionRange)*
		public Group getGroup() { return cGroup; }
		
		//ranges+=VersionRange?
		public Assignment getRangesAssignment_0() { return cRangesAssignment_0; }
		
		//VersionRange
		public RuleCall getRangesVersionRangeParserRuleCall_0_0() { return cRangesVersionRangeParserRuleCall_0_0; }
		
		//('||' ranges+=VersionRange)*
		public Group getGroup_1() { return cGroup_1; }
		
		//'||'
		public Keyword getVerticalLineVerticalLineKeyword_1_0() { return cVerticalLineVerticalLineKeyword_1_0; }
		
		//ranges+=VersionRange
		public Assignment getRangesAssignment_1_1() { return cRangesAssignment_1_1; }
		
		//VersionRange
		public RuleCall getRangesVersionRangeParserRuleCall_1_1_0() { return cRangesVersionRangeParserRuleCall_1_1_0; }
	}
	public class VersionRangeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.SemanticVersioning.VersionRange");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cHyphenVersionRangeParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cEnumeratedVersionRangeParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//VersionRange:
		//	HyphenVersionRange | EnumeratedVersionRange;
		@Override public ParserRule getRule() { return rule; }
		
		//HyphenVersionRange | EnumeratedVersionRange
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//HyphenVersionRange
		public RuleCall getHyphenVersionRangeParserRuleCall_0() { return cHyphenVersionRangeParserRuleCall_0; }
		
		//EnumeratedVersionRange
		public RuleCall getEnumeratedVersionRangeParserRuleCall_1() { return cEnumeratedVersionRangeParserRuleCall_1; }
	}
	public class HyphenVersionRangeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.SemanticVersioning.HyphenVersionRange");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cHyphenVersionRangeAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cFromAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cFromVersionNumberParserRuleCall_1_0 = (RuleCall)cFromAssignment_1.eContents().get(0);
		private final Keyword cHyphenMinusKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cToAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cToVersionNumberParserRuleCall_3_0 = (RuleCall)cToAssignment_3.eContents().get(0);
		
		//HyphenVersionRange VersionRange:
		//	{HyphenVersionRange} from=VersionNumber '-' to=VersionNumber;
		@Override public ParserRule getRule() { return rule; }
		
		//{HyphenVersionRange} from=VersionNumber '-' to=VersionNumber
		public Group getGroup() { return cGroup; }
		
		//{HyphenVersionRange}
		public Action getHyphenVersionRangeAction_0() { return cHyphenVersionRangeAction_0; }
		
		//from=VersionNumber
		public Assignment getFromAssignment_1() { return cFromAssignment_1; }
		
		//VersionNumber
		public RuleCall getFromVersionNumberParserRuleCall_1_0() { return cFromVersionNumberParserRuleCall_1_0; }
		
		//'-'
		public Keyword getHyphenMinusKeyword_2() { return cHyphenMinusKeyword_2; }
		
		//to=VersionNumber
		public Assignment getToAssignment_3() { return cToAssignment_3; }
		
		//VersionNumber
		public RuleCall getToVersionNumberParserRuleCall_3_0() { return cToVersionNumberParserRuleCall_3_0; }
	}
	public class EnumeratedVersionRangeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.SemanticVersioning.EnumeratedVersionRange");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cEnumeratedVersionRangeAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cSimpleVersionsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cSimpleVersionsSimpleVersionParserRuleCall_1_0 = (RuleCall)cSimpleVersionsAssignment_1.eContents().get(0);
		
		//EnumeratedVersionRange VersionRange:
		//	{EnumeratedVersionRange} simpleVersions+=SimpleVersion+;
		@Override public ParserRule getRule() { return rule; }
		
		//{EnumeratedVersionRange} simpleVersions+=SimpleVersion+
		public Group getGroup() { return cGroup; }
		
		//{EnumeratedVersionRange}
		public Action getEnumeratedVersionRangeAction_0() { return cEnumeratedVersionRangeAction_0; }
		
		//simpleVersions+=SimpleVersion+
		public Assignment getSimpleVersionsAssignment_1() { return cSimpleVersionsAssignment_1; }
		
		//SimpleVersion
		public RuleCall getSimpleVersionsSimpleVersionParserRuleCall_1_0() { return cSimpleVersionsSimpleVersionParserRuleCall_1_0; }
	}
	public class SimpleVersionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.SemanticVersioning.SimpleVersion");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cComparatorAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cComparatorVersionComparatorEnumRuleCall_0_0 = (RuleCall)cComparatorAssignment_0.eContents().get(0);
		private final Assignment cHasTildeAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cHasTildeTildeKeyword_1_0 = (Keyword)cHasTildeAssignment_1.eContents().get(0);
		private final Assignment cHasCaretAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final Keyword cHasCaretCircumflexAccentKeyword_2_0 = (Keyword)cHasCaretAssignment_2.eContents().get(0);
		private final Assignment cNumberAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cNumberVersionNumberParserRuleCall_3_0 = (RuleCall)cNumberAssignment_3.eContents().get(0);
		
		//SimpleVersion:
		//	comparator=VersionComparator? hasTilde?='~' hasCaret?='^' number=VersionNumber;
		@Override public ParserRule getRule() { return rule; }
		
		//comparator=VersionComparator? hasTilde?='~' hasCaret?='^' number=VersionNumber
		public Group getGroup() { return cGroup; }
		
		//comparator=VersionComparator?
		public Assignment getComparatorAssignment_0() { return cComparatorAssignment_0; }
		
		//VersionComparator
		public RuleCall getComparatorVersionComparatorEnumRuleCall_0_0() { return cComparatorVersionComparatorEnumRuleCall_0_0; }
		
		//hasTilde?='~'
		public Assignment getHasTildeAssignment_1() { return cHasTildeAssignment_1; }
		
		//'~'
		public Keyword getHasTildeTildeKeyword_1_0() { return cHasTildeTildeKeyword_1_0; }
		
		//hasCaret?='^'
		public Assignment getHasCaretAssignment_2() { return cHasCaretAssignment_2; }
		
		//'^'
		public Keyword getHasCaretCircumflexAccentKeyword_2_0() { return cHasCaretCircumflexAccentKeyword_2_0; }
		
		//number=VersionNumber
		public Assignment getNumberAssignment_3() { return cNumberAssignment_3; }
		
		//VersionNumber
		public RuleCall getNumberVersionNumberParserRuleCall_3_0() { return cNumberVersionNumberParserRuleCall_3_0; }
	}
	public class VersionNumberElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.SemanticVersioning.VersionNumber");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cMajorAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cMajorXrParserRuleCall_0_0 = (RuleCall)cMajorAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cFullStopKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cMinorAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cMinorXrParserRuleCall_1_1_0 = (RuleCall)cMinorAssignment_1_1.eContents().get(0);
		private final Group cGroup_1_2 = (Group)cGroup_1.eContents().get(2);
		private final Keyword cFullStopKeyword_1_2_0 = (Keyword)cGroup_1_2.eContents().get(0);
		private final Assignment cPathAssignment_1_2_1 = (Assignment)cGroup_1_2.eContents().get(1);
		private final RuleCall cPathXrParserRuleCall_1_2_1_0 = (RuleCall)cPathAssignment_1_2_1.eContents().get(0);
		private final Assignment cQualifierAssignment_1_2_2 = (Assignment)cGroup_1_2.eContents().get(2);
		private final RuleCall cQualifierQualifierParserRuleCall_1_2_2_0 = (RuleCall)cQualifierAssignment_1_2_2.eContents().get(0);
		
		//VersionNumber:
		//	major=Xr ('.' minor=Xr ('.' path=Xr qualifier=Qualifier?)?)?;
		@Override public ParserRule getRule() { return rule; }
		
		//major=Xr ('.' minor=Xr ('.' path=Xr qualifier=Qualifier?)?)?
		public Group getGroup() { return cGroup; }
		
		//major=Xr
		public Assignment getMajorAssignment_0() { return cMajorAssignment_0; }
		
		//Xr
		public RuleCall getMajorXrParserRuleCall_0_0() { return cMajorXrParserRuleCall_0_0; }
		
		//('.' minor=Xr ('.' path=Xr qualifier=Qualifier?)?)?
		public Group getGroup_1() { return cGroup_1; }
		
		//'.'
		public Keyword getFullStopKeyword_1_0() { return cFullStopKeyword_1_0; }
		
		//minor=Xr
		public Assignment getMinorAssignment_1_1() { return cMinorAssignment_1_1; }
		
		//Xr
		public RuleCall getMinorXrParserRuleCall_1_1_0() { return cMinorXrParserRuleCall_1_1_0; }
		
		//('.' path=Xr qualifier=Qualifier?)?
		public Group getGroup_1_2() { return cGroup_1_2; }
		
		//'.'
		public Keyword getFullStopKeyword_1_2_0() { return cFullStopKeyword_1_2_0; }
		
		//path=Xr
		public Assignment getPathAssignment_1_2_1() { return cPathAssignment_1_2_1; }
		
		//Xr
		public RuleCall getPathXrParserRuleCall_1_2_1_0() { return cPathXrParserRuleCall_1_2_1_0; }
		
		//qualifier=Qualifier?
		public Assignment getQualifierAssignment_1_2_2() { return cQualifierAssignment_1_2_2; }
		
		//Qualifier
		public RuleCall getQualifierQualifierParserRuleCall_1_2_2_0() { return cQualifierQualifierParserRuleCall_1_2_2_0; }
	}
	public class XrElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.SemanticVersioning.Xr");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cXKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cXKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cAsteriskKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		
		//Xr:
		//	'x' | 'X' | '*' // | NUMBER
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//'x' | 'X' | '*'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'x'
		public Keyword getXKeyword_0() { return cXKeyword_0; }
		
		//'X'
		public Keyword getXKeyword_1() { return cXKeyword_1; }
		
		//'*'
		public Keyword getAsteriskKeyword_2() { return cAsteriskKeyword_2; }
	}
	public class QualifierElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.SemanticVersioning.Qualifier");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final Keyword cHyphenMinusKeyword_0_0 = (Keyword)cGroup_0.eContents().get(0);
		private final Assignment cPreReleaseAssignment_0_1 = (Assignment)cGroup_0.eContents().get(1);
		private final RuleCall cPreReleasePartsParserRuleCall_0_1_0 = (RuleCall)cPreReleaseAssignment_0_1.eContents().get(0);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Keyword cPlusSignKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cBuildMetadataAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cBuildMetadataPartsParserRuleCall_1_1_0 = (RuleCall)cBuildMetadataAssignment_1_1.eContents().get(0);
		
		//Qualifier:
		//	'-' preRelease=Parts | '+' buildMetadata=Parts;
		@Override public ParserRule getRule() { return rule; }
		
		//'-' preRelease=Parts | '+' buildMetadata=Parts
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'-' preRelease=Parts
		public Group getGroup_0() { return cGroup_0; }
		
		//'-'
		public Keyword getHyphenMinusKeyword_0_0() { return cHyphenMinusKeyword_0_0; }
		
		//preRelease=Parts
		public Assignment getPreReleaseAssignment_0_1() { return cPreReleaseAssignment_0_1; }
		
		//Parts
		public RuleCall getPreReleasePartsParserRuleCall_0_1_0() { return cPreReleasePartsParserRuleCall_0_1_0; }
		
		//'+' buildMetadata=Parts
		public Group getGroup_1() { return cGroup_1; }
		
		//'+'
		public Keyword getPlusSignKeyword_1_0() { return cPlusSignKeyword_1_0; }
		
		//buildMetadata=Parts
		public Assignment getBuildMetadataAssignment_1_1() { return cBuildMetadataAssignment_1_1; }
		
		//Parts
		public RuleCall getBuildMetadataPartsParserRuleCall_1_1_0() { return cBuildMetadataPartsParserRuleCall_1_1_0; }
	}
	public class PartsElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.SemanticVersioning.Parts");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cPARTTerminalRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cFullStopKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final RuleCall cPARTTerminalRuleCall_1_1 = (RuleCall)cGroup_1.eContents().get(1);
		
		//Parts:
		//	PART ('.' PART)*;
		@Override public ParserRule getRule() { return rule; }
		
		//PART ('.' PART)*
		public Group getGroup() { return cGroup; }
		
		//PART
		public RuleCall getPARTTerminalRuleCall_0() { return cPARTTerminalRuleCall_0; }
		
		//('.' PART)*
		public Group getGroup_1() { return cGroup_1; }
		
		//'.'
		public Keyword getFullStopKeyword_1_0() { return cFullStopKeyword_1_0; }
		
		//PART
		public RuleCall getPARTTerminalRuleCall_1_1() { return cPARTTerminalRuleCall_1_1; }
	}
	
	public class VersionComparatorElements extends AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.SemanticVersioning.VersionComparator");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cEqualsEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cEqualsEqualsSignKeyword_0_0 = (Keyword)cEqualsEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cSmallerEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cSmallerLessThanSignKeyword_1_0 = (Keyword)cSmallerEnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cSmallerEqualsEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cSmallerEqualsLessThanSignEqualsSignKeyword_2_0 = (Keyword)cSmallerEqualsEnumLiteralDeclaration_2.eContents().get(0);
		private final EnumLiteralDeclaration cGreaterEnumLiteralDeclaration_3 = (EnumLiteralDeclaration)cAlternatives.eContents().get(3);
		private final Keyword cGreaterGreaterThanSignKeyword_3_0 = (Keyword)cGreaterEnumLiteralDeclaration_3.eContents().get(0);
		private final EnumLiteralDeclaration cGreaterEqualsEnumLiteralDeclaration_4 = (EnumLiteralDeclaration)cAlternatives.eContents().get(4);
		private final Keyword cGreaterEqualsGreaterThanSignEqualsSignKeyword_4_0 = (Keyword)cGreaterEqualsEnumLiteralDeclaration_4.eContents().get(0);
		
		//enum VersionComparator:
		//	Equals='='
		//	| Smaller='<'
		//	| SmallerEquals='<='
		//	| Greater='>'
		//	| GreaterEquals='>=';
		public EnumRule getRule() { return rule; }
		
		//Equals='=' | Smaller='<' | SmallerEquals='<=' | Greater='>' | GreaterEquals='>='
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//Equals='='
		public EnumLiteralDeclaration getEqualsEnumLiteralDeclaration_0() { return cEqualsEnumLiteralDeclaration_0; }
		
		//'='
		public Keyword getEqualsEqualsSignKeyword_0_0() { return cEqualsEqualsSignKeyword_0_0; }
		
		//Smaller='<'
		public EnumLiteralDeclaration getSmallerEnumLiteralDeclaration_1() { return cSmallerEnumLiteralDeclaration_1; }
		
		//'<'
		public Keyword getSmallerLessThanSignKeyword_1_0() { return cSmallerLessThanSignKeyword_1_0; }
		
		//SmallerEquals='<='
		public EnumLiteralDeclaration getSmallerEqualsEnumLiteralDeclaration_2() { return cSmallerEqualsEnumLiteralDeclaration_2; }
		
		//'<='
		public Keyword getSmallerEqualsLessThanSignEqualsSignKeyword_2_0() { return cSmallerEqualsLessThanSignEqualsSignKeyword_2_0; }
		
		//Greater='>'
		public EnumLiteralDeclaration getGreaterEnumLiteralDeclaration_3() { return cGreaterEnumLiteralDeclaration_3; }
		
		//'>'
		public Keyword getGreaterGreaterThanSignKeyword_3_0() { return cGreaterGreaterThanSignKeyword_3_0; }
		
		//GreaterEquals='>='
		public EnumLiteralDeclaration getGreaterEqualsEnumLiteralDeclaration_4() { return cGreaterEqualsEnumLiteralDeclaration_4; }
		
		//'>='
		public Keyword getGreaterEqualsGreaterThanSignEqualsSignKeyword_4_0() { return cGreaterEqualsGreaterThanSignEqualsSignKeyword_4_0; }
	}
	
	private final VersionRangeSetElements pVersionRangeSet;
	private final VersionRangeElements pVersionRange;
	private final HyphenVersionRangeElements pHyphenVersionRange;
	private final EnumeratedVersionRangeElements pEnumeratedVersionRange;
	private final SimpleVersionElements pSimpleVersion;
	private final VersionNumberElements pVersionNumber;
	private final XrElements pXr;
	private final QualifierElements pQualifier;
	private final PartsElements pParts;
	private final TerminalRule tPART;
	private final TerminalRule tWS;
	private final TerminalRule tEOL;
	private final VersionComparatorElements eVersionComparator;
	
	private final Grammar grammar;
	
	private final UnicodeGrammarAccess gaUnicode;

	@Inject
	public SemanticVersioningGrammarAccess(GrammarProvider grammarProvider,
			UnicodeGrammarAccess gaUnicode) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaUnicode = gaUnicode;
		this.pVersionRangeSet = new VersionRangeSetElements();
		this.pVersionRange = new VersionRangeElements();
		this.pHyphenVersionRange = new HyphenVersionRangeElements();
		this.pEnumeratedVersionRange = new EnumeratedVersionRangeElements();
		this.pSimpleVersion = new SimpleVersionElements();
		this.pVersionNumber = new VersionNumberElements();
		this.pXr = new XrElements();
		this.pQualifier = new QualifierElements();
		this.pParts = new PartsElements();
		this.tPART = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.SemanticVersioning.PART");
		this.tWS = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.SemanticVersioning.WS");
		this.tEOL = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.SemanticVersioning.EOL");
		this.eVersionComparator = new VersionComparatorElements();
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("org.eclipse.n4js.semver.SemanticVersioning".equals(grammar.getName())) {
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

	
	//// This grammar is an adapted version of the BNF found at:
	////  https://docs.npmjs.com/misc/semver
	////
	////	range-set  ::= range ( logical-or range ) *
	////	logical-or ::= ( ' ' ) * '||' ( ' ' ) *
	////	range      ::= hyphen | simple ( ' ' simple ) * | ''
	////	hyphen     ::= partial ' - ' partial
	////	simple     ::= primitive | partial | tilde | caret
	////	primitive  ::= ( '<' | '>' | '>=' | '<=' | '=' | ) partial
	////	partial    ::= xr ( '.' xr ( '.' xr qualifier ? )? )?
	////	xr         ::= 'x' | 'X' | '*' | nr
	////	nr         ::= '0' | ['1'-'9'] ( ['0'-'9'] ) *
	////	tilde      ::= '~' partial
	////	caret      ::= '^' partial
	////	qualifier  ::= ( '-' pre )? ( '+' build )?
	////	pre        ::= parts
	////	build      ::= parts
	////	parts      ::= part ( '.' part ) *
	////	part       ::= nr | [-0-9A-Za-z]+
	//VersionRangeSet:
	//	ranges+=VersionRange? ('||' ranges+=VersionRange)*;
	public VersionRangeSetElements getVersionRangeSetAccess() {
		return pVersionRangeSet;
	}
	
	public ParserRule getVersionRangeSetRule() {
		return getVersionRangeSetAccess().getRule();
	}
	
	//VersionRange:
	//	HyphenVersionRange | EnumeratedVersionRange;
	public VersionRangeElements getVersionRangeAccess() {
		return pVersionRange;
	}
	
	public ParserRule getVersionRangeRule() {
		return getVersionRangeAccess().getRule();
	}
	
	//HyphenVersionRange VersionRange:
	//	{HyphenVersionRange} from=VersionNumber '-' to=VersionNumber;
	public HyphenVersionRangeElements getHyphenVersionRangeAccess() {
		return pHyphenVersionRange;
	}
	
	public ParserRule getHyphenVersionRangeRule() {
		return getHyphenVersionRangeAccess().getRule();
	}
	
	//EnumeratedVersionRange VersionRange:
	//	{EnumeratedVersionRange} simpleVersions+=SimpleVersion+;
	public EnumeratedVersionRangeElements getEnumeratedVersionRangeAccess() {
		return pEnumeratedVersionRange;
	}
	
	public ParserRule getEnumeratedVersionRangeRule() {
		return getEnumeratedVersionRangeAccess().getRule();
	}
	
	//SimpleVersion:
	//	comparator=VersionComparator? hasTilde?='~' hasCaret?='^' number=VersionNumber;
	public SimpleVersionElements getSimpleVersionAccess() {
		return pSimpleVersion;
	}
	
	public ParserRule getSimpleVersionRule() {
		return getSimpleVersionAccess().getRule();
	}
	
	//VersionNumber:
	//	major=Xr ('.' minor=Xr ('.' path=Xr qualifier=Qualifier?)?)?;
	public VersionNumberElements getVersionNumberAccess() {
		return pVersionNumber;
	}
	
	public ParserRule getVersionNumberRule() {
		return getVersionNumberAccess().getRule();
	}
	
	//Xr:
	//	'x' | 'X' | '*' // | NUMBER
	//;
	public XrElements getXrAccess() {
		return pXr;
	}
	
	public ParserRule getXrRule() {
		return getXrAccess().getRule();
	}
	
	//Qualifier:
	//	'-' preRelease=Parts | '+' buildMetadata=Parts;
	public QualifierElements getQualifierAccess() {
		return pQualifier;
	}
	
	public ParserRule getQualifierRule() {
		return getQualifierAccess().getRule();
	}
	
	//Parts:
	//	PART ('.' PART)*;
	public PartsElements getPartsAccess() {
		return pParts;
	}
	
	public ParserRule getPartsRule() {
		return getPartsAccess().getRule();
	}
	
	//terminal PART:
	//	'0' | ('1'..'9' | 'A'..'Z' | 'a'..'z') ('-' | '0'..'9' | 'A'..'Z' | 'a'..'z')*;
	public TerminalRule getPARTRule() {
		return tPART;
	}
	
	//terminal WS:
	//	WHITESPACE_FRAGMENT+;
	public TerminalRule getWSRule() {
		return tWS;
	}
	
	//terminal EOL:
	//	LINE_TERMINATOR_SEQUENCE_FRAGMENT;
	public TerminalRule getEOLRule() {
		return tEOL;
	}
	
	//enum VersionComparator:
	//	Equals='='
	//	| Smaller='<'
	//	| SmallerEquals='<='
	//	| Greater='>'
	//	| GreaterEquals='>=';
	public VersionComparatorElements getVersionComparatorAccess() {
		return eVersionComparator;
	}
	
	public EnumRule getVersionComparatorRule() {
		return getVersionComparatorAccess().getRule();
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
