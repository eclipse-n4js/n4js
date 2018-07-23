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
public class SemverGrammarAccess extends AbstractGrammarElementFinder {
	
	public class NPMVersionRequirementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.NPMVersionRequirement");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final RuleCall cWSTerminalRuleCall_0_0 = (RuleCall)cGroup_0.eContents().get(0);
		private final RuleCall cVersionRangeSetRequirementParserRuleCall_0_1 = (RuleCall)cGroup_0.eContents().get(1);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Alternatives cAlternatives_1_0 = (Alternatives)cGroup_1.eContents().get(0);
		private final RuleCall cURLVersionRequirementParserRuleCall_1_0_0 = (RuleCall)cAlternatives_1_0.eContents().get(0);
		private final RuleCall cLocalPathVersionRequirementParserRuleCall_1_0_1 = (RuleCall)cAlternatives_1_0.eContents().get(1);
		private final RuleCall cGitHubVersionRequirementParserRuleCall_1_0_2 = (RuleCall)cAlternatives_1_0.eContents().get(2);
		private final RuleCall cTagVersionRequirementParserRuleCall_1_0_3 = (RuleCall)cAlternatives_1_0.eContents().get(3);
		private final RuleCall cWSTerminalRuleCall_1_1 = (RuleCall)cGroup_1.eContents().get(1);
		
		//// This grammar of SemVer 2.0.0 is an adapted version of the BNF found at:
		////  https://docs.npmjs.com/misc/semver
		//NPMVersionRequirement:
		//	WS*
		//	VersionRangeSetRequirement
		//	| (URLVersionRequirement
		//	| LocalPathVersionRequirement
		//	| GitHubVersionRequirement
		//	| TagVersionRequirement) WS*;
		@Override public ParserRule getRule() { return rule; }
		
		//WS* VersionRangeSetRequirement | (URLVersionRequirement | LocalPathVersionRequirement | GitHubVersionRequirement |
		//TagVersionRequirement) WS*
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//WS* VersionRangeSetRequirement
		public Group getGroup_0() { return cGroup_0; }
		
		//WS*
		public RuleCall getWSTerminalRuleCall_0_0() { return cWSTerminalRuleCall_0_0; }
		
		//VersionRangeSetRequirement
		public RuleCall getVersionRangeSetRequirementParserRuleCall_0_1() { return cVersionRangeSetRequirementParserRuleCall_0_1; }
		
		//(URLVersionRequirement | LocalPathVersionRequirement | GitHubVersionRequirement | TagVersionRequirement) WS*
		public Group getGroup_1() { return cGroup_1; }
		
		//URLVersionRequirement | LocalPathVersionRequirement | GitHubVersionRequirement | TagVersionRequirement
		public Alternatives getAlternatives_1_0() { return cAlternatives_1_0; }
		
		//URLVersionRequirement
		public RuleCall getURLVersionRequirementParserRuleCall_1_0_0() { return cURLVersionRequirementParserRuleCall_1_0_0; }
		
		//LocalPathVersionRequirement
		public RuleCall getLocalPathVersionRequirementParserRuleCall_1_0_1() { return cLocalPathVersionRequirementParserRuleCall_1_0_1; }
		
		//GitHubVersionRequirement
		public RuleCall getGitHubVersionRequirementParserRuleCall_1_0_2() { return cGitHubVersionRequirementParserRuleCall_1_0_2; }
		
		//TagVersionRequirement
		public RuleCall getTagVersionRequirementParserRuleCall_1_0_3() { return cTagVersionRequirementParserRuleCall_1_0_3; }
		
		//WS*
		public RuleCall getWSTerminalRuleCall_1_1() { return cWSTerminalRuleCall_1_1; }
	}
	public class URLVersionRequirementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.URLVersionRequirement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cProtocolAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cProtocolURL_PROTOCOLParserRuleCall_0_0 = (RuleCall)cProtocolAssignment_0.eContents().get(0);
		private final Keyword cColonSolidusSolidusKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cUrlAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cUrlURLParserRuleCall_2_0 = (RuleCall)cUrlAssignment_2.eContents().get(0);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Keyword cNumberSignKeyword_3_0 = (Keyword)cGroup_3.eContents().get(0);
		private final Assignment cVersionSpecifierAssignment_3_1 = (Assignment)cGroup_3.eContents().get(1);
		private final RuleCall cVersionSpecifierURLVersionSpecifierParserRuleCall_3_1_0 = (RuleCall)cVersionSpecifierAssignment_3_1.eContents().get(0);
		
		//URLVersionRequirement:
		//	protocol=URL_PROTOCOL '://' url=URL ('#' versionSpecifier=URLVersionSpecifier)?;
		@Override public ParserRule getRule() { return rule; }
		
		//protocol=URL_PROTOCOL '://' url=URL ('#' versionSpecifier=URLVersionSpecifier)?
		public Group getGroup() { return cGroup; }
		
		//protocol=URL_PROTOCOL
		public Assignment getProtocolAssignment_0() { return cProtocolAssignment_0; }
		
		//URL_PROTOCOL
		public RuleCall getProtocolURL_PROTOCOLParserRuleCall_0_0() { return cProtocolURL_PROTOCOLParserRuleCall_0_0; }
		
		//'://'
		public Keyword getColonSolidusSolidusKeyword_1() { return cColonSolidusSolidusKeyword_1; }
		
		//url=URL
		public Assignment getUrlAssignment_2() { return cUrlAssignment_2; }
		
		//URL
		public RuleCall getUrlURLParserRuleCall_2_0() { return cUrlURLParserRuleCall_2_0; }
		
		//('#' versionSpecifier=URLVersionSpecifier)?
		public Group getGroup_3() { return cGroup_3; }
		
		//'#'
		public Keyword getNumberSignKeyword_3_0() { return cNumberSignKeyword_3_0; }
		
		//versionSpecifier=URLVersionSpecifier
		public Assignment getVersionSpecifierAssignment_3_1() { return cVersionSpecifierAssignment_3_1; }
		
		//URLVersionSpecifier
		public RuleCall getVersionSpecifierURLVersionSpecifierParserRuleCall_3_1_0() { return cVersionSpecifierURLVersionSpecifierParserRuleCall_3_1_0; }
	}
	public class URLVersionSpecifierElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.URLVersionSpecifier");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cURLSemverParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cURLCommitISHParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//URLVersionSpecifier:
		//	=> URLSemver
		//	| URLCommitISH;
		@Override public ParserRule getRule() { return rule; }
		
		//=> URLSemver | URLCommitISH
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//=> URLSemver
		public RuleCall getURLSemverParserRuleCall_0() { return cURLSemverParserRuleCall_0; }
		
		//URLCommitISH
		public RuleCall getURLCommitISHParserRuleCall_1() { return cURLCommitISHParserRuleCall_1; }
	}
	public class URLSemverElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.URLSemver");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cSemverKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cSimpleVersionAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cSimpleVersionSimpleVersionParserRuleCall_1_0 = (RuleCall)cSimpleVersionAssignment_1.eContents().get(0);
		
		//URLSemver:
		//	"semver:"? simpleVersion=SimpleVersion;
		@Override public ParserRule getRule() { return rule; }
		
		//"semver:"? simpleVersion=SimpleVersion
		public Group getGroup() { return cGroup; }
		
		//"semver:"?
		public Keyword getSemverKeyword_0() { return cSemverKeyword_0; }
		
		//simpleVersion=SimpleVersion
		public Assignment getSimpleVersionAssignment_1() { return cSimpleVersionAssignment_1; }
		
		//SimpleVersion
		public RuleCall getSimpleVersionSimpleVersionParserRuleCall_1_0() { return cSimpleVersionSimpleVersionParserRuleCall_1_0; }
	}
	public class URLCommitISHElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.URLCommitISH");
		private final Assignment cCommitISHAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cCommitISHALPHA_NUMERIC_CHARSParserRuleCall_0 = (RuleCall)cCommitISHAssignment.eContents().get(0);
		
		//URLCommitISH:
		//	commitISH=ALPHA_NUMERIC_CHARS;
		@Override public ParserRule getRule() { return rule; }
		
		//commitISH=ALPHA_NUMERIC_CHARS
		public Assignment getCommitISHAssignment() { return cCommitISHAssignment; }
		
		//ALPHA_NUMERIC_CHARS
		public RuleCall getCommitISHALPHA_NUMERIC_CHARSParserRuleCall_0() { return cCommitISHALPHA_NUMERIC_CHARSParserRuleCall_0; }
	}
	public class TagVersionRequirementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.TagVersionRequirement");
		private final Assignment cTagNameAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cTagNameTAGParserRuleCall_0 = (RuleCall)cTagNameAssignment.eContents().get(0);
		
		//TagVersionRequirement:
		//	tagName=TAG;
		@Override public ParserRule getRule() { return rule; }
		
		//tagName=TAG
		public Assignment getTagNameAssignment() { return cTagNameAssignment; }
		
		//TAG
		public RuleCall getTagNameTAGParserRuleCall_0() { return cTagNameTAGParserRuleCall_0; }
	}
	public class GitHubVersionRequirementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.GitHubVersionRequirement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cGithubUrlAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cGithubUrlURLParserRuleCall_0_0 = (RuleCall)cGithubUrlAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cNumberSignKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cCommitISHAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cCommitISHALPHA_NUMERIC_CHARSParserRuleCall_1_1_0 = (RuleCall)cCommitISHAssignment_1_1.eContents().get(0);
		
		//GitHubVersionRequirement:
		//	githubUrl=URL ('#' commitISH=ALPHA_NUMERIC_CHARS)?;
		@Override public ParserRule getRule() { return rule; }
		
		//githubUrl=URL ('#' commitISH=ALPHA_NUMERIC_CHARS)?
		public Group getGroup() { return cGroup; }
		
		//githubUrl=URL
		public Assignment getGithubUrlAssignment_0() { return cGithubUrlAssignment_0; }
		
		//URL
		public RuleCall getGithubUrlURLParserRuleCall_0_0() { return cGithubUrlURLParserRuleCall_0_0; }
		
		//('#' commitISH=ALPHA_NUMERIC_CHARS)?
		public Group getGroup_1() { return cGroup_1; }
		
		//'#'
		public Keyword getNumberSignKeyword_1_0() { return cNumberSignKeyword_1_0; }
		
		//commitISH=ALPHA_NUMERIC_CHARS
		public Assignment getCommitISHAssignment_1_1() { return cCommitISHAssignment_1_1; }
		
		//ALPHA_NUMERIC_CHARS
		public RuleCall getCommitISHALPHA_NUMERIC_CHARSParserRuleCall_1_1_0() { return cCommitISHALPHA_NUMERIC_CHARSParserRuleCall_1_1_0; }
	}
	public class LocalPathVersionRequirementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.LocalPathVersionRequirement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cFileKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cLocalPathAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cLocalPathPATHParserRuleCall_1_0 = (RuleCall)cLocalPathAssignment_1.eContents().get(0);
		
		//LocalPathVersionRequirement:
		//	'file:' localPath=PATH;
		@Override public ParserRule getRule() { return rule; }
		
		//'file:' localPath=PATH
		public Group getGroup() { return cGroup; }
		
		//'file:'
		public Keyword getFileKeyword_0() { return cFileKeyword_0; }
		
		//localPath=PATH
		public Assignment getLocalPathAssignment_1() { return cLocalPathAssignment_1; }
		
		//PATH
		public RuleCall getLocalPathPATHParserRuleCall_1_0() { return cLocalPathPATHParserRuleCall_1_0; }
	}
	public class VersionRangeSetRequirementElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cVersionRangeSetRequirementAction_0 = (Action)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Assignment cRangesAssignment_1_0 = (Assignment)cGroup_1.eContents().get(0);
		private final RuleCall cRangesVersionRangeParserRuleCall_1_0_0 = (RuleCall)cRangesAssignment_1_0.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cGroup_1.eContents().get(1);
		private final RuleCall cWSTerminalRuleCall_1_1_0 = (RuleCall)cGroup_1_1.eContents().get(0);
		private final Keyword cVerticalLineVerticalLineKeyword_1_1_1 = (Keyword)cGroup_1_1.eContents().get(1);
		private final RuleCall cWSTerminalRuleCall_1_1_2 = (RuleCall)cGroup_1_1.eContents().get(2);
		private final Assignment cRangesAssignment_1_1_3 = (Assignment)cGroup_1_1.eContents().get(3);
		private final RuleCall cRangesVersionRangeParserRuleCall_1_1_3_0 = (RuleCall)cRangesAssignment_1_1_3.eContents().get(0);
		private final RuleCall cWSTerminalRuleCall_1_2 = (RuleCall)cGroup_1.eContents().get(2);
		
		//VersionRangeSetRequirement:
		//	{VersionRangeSetRequirement} (ranges+=VersionRange (WS* '||' WS* ranges+=VersionRange)* WS*)?;
		@Override public ParserRule getRule() { return rule; }
		
		//{VersionRangeSetRequirement} (ranges+=VersionRange (WS* '||' WS* ranges+=VersionRange)* WS*)?
		public Group getGroup() { return cGroup; }
		
		//{VersionRangeSetRequirement}
		public Action getVersionRangeSetRequirementAction_0() { return cVersionRangeSetRequirementAction_0; }
		
		//(ranges+=VersionRange (WS* '||' WS* ranges+=VersionRange)* WS*)?
		public Group getGroup_1() { return cGroup_1; }
		
		//ranges+=VersionRange
		public Assignment getRangesAssignment_1_0() { return cRangesAssignment_1_0; }
		
		//VersionRange
		public RuleCall getRangesVersionRangeParserRuleCall_1_0_0() { return cRangesVersionRangeParserRuleCall_1_0_0; }
		
		//(WS* '||' WS* ranges+=VersionRange)*
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//WS*
		public RuleCall getWSTerminalRuleCall_1_1_0() { return cWSTerminalRuleCall_1_1_0; }
		
		//'||'
		public Keyword getVerticalLineVerticalLineKeyword_1_1_1() { return cVerticalLineVerticalLineKeyword_1_1_1; }
		
		//WS*
		public RuleCall getWSTerminalRuleCall_1_1_2() { return cWSTerminalRuleCall_1_1_2; }
		
		//ranges+=VersionRange
		public Assignment getRangesAssignment_1_1_3() { return cRangesAssignment_1_1_3; }
		
		//VersionRange
		public RuleCall getRangesVersionRangeParserRuleCall_1_1_3_0() { return cRangesVersionRangeParserRuleCall_1_1_3_0; }
		
		//WS*
		public RuleCall getWSTerminalRuleCall_1_2() { return cWSTerminalRuleCall_1_2; }
	}
	public class VersionRangeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.VersionRange");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cVersionRangeContraintParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cHyphenVersionRangeParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//VersionRange:
		//	VersionRangeContraint | HyphenVersionRange;
		@Override public ParserRule getRule() { return rule; }
		
		//VersionRangeContraint | HyphenVersionRange
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//VersionRangeContraint
		public RuleCall getVersionRangeContraintParserRuleCall_0() { return cVersionRangeContraintParserRuleCall_0; }
		
		//HyphenVersionRange
		public RuleCall getHyphenVersionRangeParserRuleCall_1() { return cHyphenVersionRangeParserRuleCall_1; }
	}
	public class HyphenVersionRangeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.HyphenVersionRange");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cHyphenVersionRangeAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cFromAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cFromVersionNumberParserRuleCall_1_0 = (RuleCall)cFromAssignment_1.eContents().get(0);
		private final RuleCall cWSTerminalRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		private final Keyword cHyphenMinusKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final RuleCall cWSTerminalRuleCall_4 = (RuleCall)cGroup.eContents().get(4);
		private final Assignment cToAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final RuleCall cToVersionNumberParserRuleCall_5_0 = (RuleCall)cToAssignment_5.eContents().get(0);
		
		//HyphenVersionRange VersionRange:
		//	{HyphenVersionRange} from=VersionNumber WS+ '-' WS+ to=VersionNumber;
		@Override public ParserRule getRule() { return rule; }
		
		//{HyphenVersionRange} from=VersionNumber WS+ '-' WS+ to=VersionNumber
		public Group getGroup() { return cGroup; }
		
		//{HyphenVersionRange}
		public Action getHyphenVersionRangeAction_0() { return cHyphenVersionRangeAction_0; }
		
		//from=VersionNumber
		public Assignment getFromAssignment_1() { return cFromAssignment_1; }
		
		//VersionNumber
		public RuleCall getFromVersionNumberParserRuleCall_1_0() { return cFromVersionNumberParserRuleCall_1_0; }
		
		//WS+
		public RuleCall getWSTerminalRuleCall_2() { return cWSTerminalRuleCall_2; }
		
		//'-'
		public Keyword getHyphenMinusKeyword_3() { return cHyphenMinusKeyword_3; }
		
		//WS+
		public RuleCall getWSTerminalRuleCall_4() { return cWSTerminalRuleCall_4; }
		
		//to=VersionNumber
		public Assignment getToAssignment_5() { return cToAssignment_5; }
		
		//VersionNumber
		public RuleCall getToVersionNumberParserRuleCall_5_0() { return cToVersionNumberParserRuleCall_5_0; }
	}
	public class VersionRangeContraintElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.VersionRangeContraint");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cVersionRangeConstraintAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cVersionConstraintsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cVersionConstraintsSimpleVersionParserRuleCall_1_0 = (RuleCall)cVersionConstraintsAssignment_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final RuleCall cWSTerminalRuleCall_2_0 = (RuleCall)cGroup_2.eContents().get(0);
		private final Assignment cVersionConstraintsAssignment_2_1 = (Assignment)cGroup_2.eContents().get(1);
		private final RuleCall cVersionConstraintsSimpleVersionParserRuleCall_2_1_0 = (RuleCall)cVersionConstraintsAssignment_2_1.eContents().get(0);
		
		//VersionRangeContraint VersionRange:
		//	{VersionRangeConstraint} versionConstraints+=SimpleVersion (WS+ versionConstraints+=SimpleVersion)*;
		@Override public ParserRule getRule() { return rule; }
		
		//{VersionRangeConstraint} versionConstraints+=SimpleVersion (WS+ versionConstraints+=SimpleVersion)*
		public Group getGroup() { return cGroup; }
		
		//{VersionRangeConstraint}
		public Action getVersionRangeConstraintAction_0() { return cVersionRangeConstraintAction_0; }
		
		//versionConstraints+=SimpleVersion
		public Assignment getVersionConstraintsAssignment_1() { return cVersionConstraintsAssignment_1; }
		
		//SimpleVersion
		public RuleCall getVersionConstraintsSimpleVersionParserRuleCall_1_0() { return cVersionConstraintsSimpleVersionParserRuleCall_1_0; }
		
		//(WS+ versionConstraints+=SimpleVersion)*
		public Group getGroup_2() { return cGroup_2; }
		
		//WS+
		public RuleCall getWSTerminalRuleCall_2_0() { return cWSTerminalRuleCall_2_0; }
		
		//versionConstraints+=SimpleVersion
		public Assignment getVersionConstraintsAssignment_2_1() { return cVersionConstraintsAssignment_2_1; }
		
		//SimpleVersion
		public RuleCall getVersionConstraintsSimpleVersionParserRuleCall_2_1_0() { return cVersionConstraintsSimpleVersionParserRuleCall_2_1_0; }
	}
	public class SimpleVersionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.SimpleVersion");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cSimpleVersionAction_0 = (Action)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Assignment cComparatorsAssignment_1_0 = (Assignment)cGroup_1.eContents().get(0);
		private final RuleCall cComparatorsVersionComparatorEnumRuleCall_1_0_0 = (RuleCall)cComparatorsAssignment_1_0.eContents().get(0);
		private final RuleCall cWSTerminalRuleCall_1_1 = (RuleCall)cGroup_1.eContents().get(1);
		private final Assignment cNumberAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cNumberVersionNumberParserRuleCall_2_0 = (RuleCall)cNumberAssignment_2.eContents().get(0);
		
		//SimpleVersion:
		//	{SimpleVersion} (comparators+=VersionComparator WS*)* number=VersionNumber;
		@Override public ParserRule getRule() { return rule; }
		
		//{SimpleVersion} (comparators+=VersionComparator WS*)* number=VersionNumber
		public Group getGroup() { return cGroup; }
		
		//{SimpleVersion}
		public Action getSimpleVersionAction_0() { return cSimpleVersionAction_0; }
		
		//(comparators+=VersionComparator WS*)*
		public Group getGroup_1() { return cGroup_1; }
		
		//comparators+=VersionComparator
		public Assignment getComparatorsAssignment_1_0() { return cComparatorsAssignment_1_0; }
		
		//VersionComparator
		public RuleCall getComparatorsVersionComparatorEnumRuleCall_1_0_0() { return cComparatorsVersionComparatorEnumRuleCall_1_0_0; }
		
		//WS*
		public RuleCall getWSTerminalRuleCall_1_1() { return cWSTerminalRuleCall_1_1; }
		
		//number=VersionNumber
		public Assignment getNumberAssignment_2() { return cNumberAssignment_2; }
		
		//VersionNumber
		public RuleCall getNumberVersionNumberParserRuleCall_2_0() { return cNumberVersionNumberParserRuleCall_2_0; }
	}
	public class VersionNumberElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.VersionNumber");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cMajorAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cMajorVersionPartParserRuleCall_0_0 = (RuleCall)cMajorAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cFullStopKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cMinorAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cMinorVersionPartParserRuleCall_1_1_0 = (RuleCall)cMinorAssignment_1_1.eContents().get(0);
		private final Group cGroup_1_2 = (Group)cGroup_1.eContents().get(2);
		private final Keyword cFullStopKeyword_1_2_0 = (Keyword)cGroup_1_2.eContents().get(0);
		private final Assignment cPatchAssignment_1_2_1 = (Assignment)cGroup_1_2.eContents().get(1);
		private final RuleCall cPatchVersionPartParserRuleCall_1_2_1_0 = (RuleCall)cPatchAssignment_1_2_1.eContents().get(0);
		private final Group cGroup_1_2_2 = (Group)cGroup_1_2.eContents().get(2);
		private final Keyword cFullStopKeyword_1_2_2_0 = (Keyword)cGroup_1_2_2.eContents().get(0);
		private final Assignment cExtendedAssignment_1_2_2_1 = (Assignment)cGroup_1_2_2.eContents().get(1);
		private final RuleCall cExtendedVersionPartParserRuleCall_1_2_2_1_0 = (RuleCall)cExtendedAssignment_1_2_2_1.eContents().get(0);
		private final Assignment cQualifierAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cQualifierQualifierParserRuleCall_2_0 = (RuleCall)cQualifierAssignment_2.eContents().get(0);
		
		//VersionNumber:
		//	major=VersionPart ("." minor=VersionPart ("." patch=VersionPart ("." extended+=VersionPart)*)?)?
		//	qualifier=Qualifier?;
		@Override public ParserRule getRule() { return rule; }
		
		//major=VersionPart ("." minor=VersionPart ("." patch=VersionPart ("." extended+=VersionPart)*)?)? qualifier=Qualifier?
		public Group getGroup() { return cGroup; }
		
		//major=VersionPart
		public Assignment getMajorAssignment_0() { return cMajorAssignment_0; }
		
		//VersionPart
		public RuleCall getMajorVersionPartParserRuleCall_0_0() { return cMajorVersionPartParserRuleCall_0_0; }
		
		//("." minor=VersionPart ("." patch=VersionPart ("." extended+=VersionPart)*)?)?
		public Group getGroup_1() { return cGroup_1; }
		
		//"."
		public Keyword getFullStopKeyword_1_0() { return cFullStopKeyword_1_0; }
		
		//minor=VersionPart
		public Assignment getMinorAssignment_1_1() { return cMinorAssignment_1_1; }
		
		//VersionPart
		public RuleCall getMinorVersionPartParserRuleCall_1_1_0() { return cMinorVersionPartParserRuleCall_1_1_0; }
		
		//("." patch=VersionPart ("." extended+=VersionPart)*)?
		public Group getGroup_1_2() { return cGroup_1_2; }
		
		//"."
		public Keyword getFullStopKeyword_1_2_0() { return cFullStopKeyword_1_2_0; }
		
		//patch=VersionPart
		public Assignment getPatchAssignment_1_2_1() { return cPatchAssignment_1_2_1; }
		
		//VersionPart
		public RuleCall getPatchVersionPartParserRuleCall_1_2_1_0() { return cPatchVersionPartParserRuleCall_1_2_1_0; }
		
		//("." extended+=VersionPart)*
		public Group getGroup_1_2_2() { return cGroup_1_2_2; }
		
		//"."
		public Keyword getFullStopKeyword_1_2_2_0() { return cFullStopKeyword_1_2_2_0; }
		
		//extended+=VersionPart
		public Assignment getExtendedAssignment_1_2_2_1() { return cExtendedAssignment_1_2_2_1; }
		
		//VersionPart
		public RuleCall getExtendedVersionPartParserRuleCall_1_2_2_1_0() { return cExtendedVersionPartParserRuleCall_1_2_2_1_0; }
		
		//qualifier=Qualifier?
		public Assignment getQualifierAssignment_2() { return cQualifierAssignment_2; }
		
		//Qualifier
		public RuleCall getQualifierQualifierParserRuleCall_2_0() { return cQualifierQualifierParserRuleCall_2_0; }
	}
	public class VersionPartElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.VersionPart");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Assignment cWildcardAssignment_0 = (Assignment)cAlternatives.eContents().get(0);
		private final RuleCall cWildcardWILDCARDParserRuleCall_0_0 = (RuleCall)cWildcardAssignment_0.eContents().get(0);
		private final Assignment cNumberRawAssignment_1 = (Assignment)cAlternatives.eContents().get(1);
		private final RuleCall cNumberRawDIGITSTerminalRuleCall_1_0 = (RuleCall)cNumberRawAssignment_1.eContents().get(0);
		
		//VersionPart:
		//	wildcard?=WILDCARD | numberRaw=DIGITS;
		@Override public ParserRule getRule() { return rule; }
		
		//wildcard?=WILDCARD | numberRaw=DIGITS
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//wildcard?=WILDCARD
		public Assignment getWildcardAssignment_0() { return cWildcardAssignment_0; }
		
		//WILDCARD
		public RuleCall getWildcardWILDCARDParserRuleCall_0_0() { return cWildcardWILDCARDParserRuleCall_0_0; }
		
		//numberRaw=DIGITS
		public Assignment getNumberRawAssignment_1() { return cNumberRawAssignment_1; }
		
		//DIGITS
		public RuleCall getNumberRawDIGITSTerminalRuleCall_1_0() { return cNumberRawDIGITSTerminalRuleCall_1_0; }
	}
	public class QualifierElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.Qualifier");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final Keyword cHyphenMinusKeyword_0_0 = (Keyword)cGroup_0.eContents().get(0);
		private final Assignment cPreReleaseAssignment_0_1 = (Assignment)cGroup_0.eContents().get(1);
		private final RuleCall cPreReleaseQualifierTagParserRuleCall_0_1_0 = (RuleCall)cPreReleaseAssignment_0_1.eContents().get(0);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Keyword cPlusSignKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cBuildMetadataAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cBuildMetadataQualifierTagParserRuleCall_1_1_0 = (RuleCall)cBuildMetadataAssignment_1_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cAlternatives.eContents().get(2);
		private final Keyword cHyphenMinusKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Assignment cPreReleaseAssignment_2_1 = (Assignment)cGroup_2.eContents().get(1);
		private final RuleCall cPreReleaseQualifierTagParserRuleCall_2_1_0 = (RuleCall)cPreReleaseAssignment_2_1.eContents().get(0);
		private final Keyword cPlusSignKeyword_2_2 = (Keyword)cGroup_2.eContents().get(2);
		private final Assignment cBuildMetadataAssignment_2_3 = (Assignment)cGroup_2.eContents().get(3);
		private final RuleCall cBuildMetadataQualifierTagParserRuleCall_2_3_0 = (RuleCall)cBuildMetadataAssignment_2_3.eContents().get(0);
		
		//Qualifier:
		//	'-' preRelease=QualifierTag | '+' buildMetadata=QualifierTag | '-' preRelease=QualifierTag '+'
		//	buildMetadata=QualifierTag;
		@Override public ParserRule getRule() { return rule; }
		
		//'-' preRelease=QualifierTag | '+' buildMetadata=QualifierTag | '-' preRelease=QualifierTag '+'
		//buildMetadata=QualifierTag
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'-' preRelease=QualifierTag
		public Group getGroup_0() { return cGroup_0; }
		
		//'-'
		public Keyword getHyphenMinusKeyword_0_0() { return cHyphenMinusKeyword_0_0; }
		
		//preRelease=QualifierTag
		public Assignment getPreReleaseAssignment_0_1() { return cPreReleaseAssignment_0_1; }
		
		//QualifierTag
		public RuleCall getPreReleaseQualifierTagParserRuleCall_0_1_0() { return cPreReleaseQualifierTagParserRuleCall_0_1_0; }
		
		//'+' buildMetadata=QualifierTag
		public Group getGroup_1() { return cGroup_1; }
		
		//'+'
		public Keyword getPlusSignKeyword_1_0() { return cPlusSignKeyword_1_0; }
		
		//buildMetadata=QualifierTag
		public Assignment getBuildMetadataAssignment_1_1() { return cBuildMetadataAssignment_1_1; }
		
		//QualifierTag
		public RuleCall getBuildMetadataQualifierTagParserRuleCall_1_1_0() { return cBuildMetadataQualifierTagParserRuleCall_1_1_0; }
		
		//'-' preRelease=QualifierTag '+' buildMetadata=QualifierTag
		public Group getGroup_2() { return cGroup_2; }
		
		//'-'
		public Keyword getHyphenMinusKeyword_2_0() { return cHyphenMinusKeyword_2_0; }
		
		//preRelease=QualifierTag
		public Assignment getPreReleaseAssignment_2_1() { return cPreReleaseAssignment_2_1; }
		
		//QualifierTag
		public RuleCall getPreReleaseQualifierTagParserRuleCall_2_1_0() { return cPreReleaseQualifierTagParserRuleCall_2_1_0; }
		
		//'+'
		public Keyword getPlusSignKeyword_2_2() { return cPlusSignKeyword_2_2; }
		
		//buildMetadata=QualifierTag
		public Assignment getBuildMetadataAssignment_2_3() { return cBuildMetadataAssignment_2_3; }
		
		//QualifierTag
		public RuleCall getBuildMetadataQualifierTagParserRuleCall_2_3_0() { return cBuildMetadataQualifierTagParserRuleCall_2_3_0; }
	}
	public class QualifierTagElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.QualifierTag");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cPartsAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cPartsALPHA_NUMERIC_CHARSParserRuleCall_0_0 = (RuleCall)cPartsAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cFullStopKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cPartsAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cPartsALPHA_NUMERIC_CHARSParserRuleCall_1_1_0 = (RuleCall)cPartsAssignment_1_1.eContents().get(0);
		
		//QualifierTag:
		//	parts+=ALPHA_NUMERIC_CHARS ("." parts+=ALPHA_NUMERIC_CHARS)*;
		@Override public ParserRule getRule() { return rule; }
		
		//parts+=ALPHA_NUMERIC_CHARS ("." parts+=ALPHA_NUMERIC_CHARS)*
		public Group getGroup() { return cGroup; }
		
		//parts+=ALPHA_NUMERIC_CHARS
		public Assignment getPartsAssignment_0() { return cPartsAssignment_0; }
		
		//ALPHA_NUMERIC_CHARS
		public RuleCall getPartsALPHA_NUMERIC_CHARSParserRuleCall_0_0() { return cPartsALPHA_NUMERIC_CHARSParserRuleCall_0_0; }
		
		//("." parts+=ALPHA_NUMERIC_CHARS)*
		public Group getGroup_1() { return cGroup_1; }
		
		//"."
		public Keyword getFullStopKeyword_1_0() { return cFullStopKeyword_1_0; }
		
		//parts+=ALPHA_NUMERIC_CHARS
		public Assignment getPartsAssignment_1_1() { return cPartsAssignment_1_1; }
		
		//ALPHA_NUMERIC_CHARS
		public RuleCall getPartsALPHA_NUMERIC_CHARSParserRuleCall_1_1_0() { return cPartsALPHA_NUMERIC_CHARSParserRuleCall_1_1_0; }
	}
	public class URL_PROTOCOLElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.URL_PROTOCOL");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cLETTERSParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final Keyword cPlusSignKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		
		//URL_PROTOCOL:
		//	(LETTERS | '+')+;
		@Override public ParserRule getRule() { return rule; }
		
		//(LETTERS | '+')+
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//LETTERS
		public RuleCall getLETTERSParserRuleCall_0() { return cLETTERSParserRuleCall_0; }
		
		//'+'
		public Keyword getPlusSignKeyword_1() { return cPlusSignKeyword_1; }
	}
	public class TAGElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.TAG");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cLETTER_NO_VParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final RuleCall cALPHA_NUMERIC_CHARSParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		
		//TAG:
		//	LETTER_NO_V ALPHA_NUMERIC_CHARS*;
		@Override public ParserRule getRule() { return rule; }
		
		//LETTER_NO_V ALPHA_NUMERIC_CHARS*
		public Group getGroup() { return cGroup; }
		
		//LETTER_NO_V
		public RuleCall getLETTER_NO_VParserRuleCall_0() { return cLETTER_NO_VParserRuleCall_0; }
		
		//ALPHA_NUMERIC_CHARS*
		public RuleCall getALPHA_NUMERIC_CHARSParserRuleCall_1() { return cALPHA_NUMERIC_CHARSParserRuleCall_1; }
	}
	public class PATHElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.PATH");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final RuleCall cLETTER_NO_VParserRuleCall_0_0 = (RuleCall)cGroup_0.eContents().get(0);
		private final RuleCall cALPHA_NUMERIC_CHARParserRuleCall_0_1 = (RuleCall)cGroup_0.eContents().get(1);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Alternatives cAlternatives_1_0 = (Alternatives)cGroup_1.eContents().get(0);
		private final Keyword cSolidusKeyword_1_0_0 = (Keyword)cAlternatives_1_0.eContents().get(0);
		private final Keyword cFullStopKeyword_1_0_1 = (Keyword)cAlternatives_1_0.eContents().get(1);
		private final RuleCall cALPHA_NUMERIC_CHARSParserRuleCall_1_1 = (RuleCall)cGroup_1.eContents().get(1);
		private final Alternatives cAlternatives_2 = (Alternatives)cGroup.eContents().get(2);
		private final Keyword cSolidusKeyword_2_0 = (Keyword)cAlternatives_2.eContents().get(0);
		private final Keyword cFullStopKeyword_2_1 = (Keyword)cAlternatives_2.eContents().get(1);
		
		//PATH:
		//	(LETTER_NO_V ALPHA_NUMERIC_CHAR)* (('/' | ".")+ ALPHA_NUMERIC_CHARS)+ ('/' | ".")*;
		@Override public ParserRule getRule() { return rule; }
		
		//(LETTER_NO_V ALPHA_NUMERIC_CHAR)* (('/' | ".")+ ALPHA_NUMERIC_CHARS)+ ('/' | ".")*
		public Group getGroup() { return cGroup; }
		
		//(LETTER_NO_V ALPHA_NUMERIC_CHAR)*
		public Group getGroup_0() { return cGroup_0; }
		
		//LETTER_NO_V
		public RuleCall getLETTER_NO_VParserRuleCall_0_0() { return cLETTER_NO_VParserRuleCall_0_0; }
		
		//ALPHA_NUMERIC_CHAR
		public RuleCall getALPHA_NUMERIC_CHARParserRuleCall_0_1() { return cALPHA_NUMERIC_CHARParserRuleCall_0_1; }
		
		//(('/' | ".")+ ALPHA_NUMERIC_CHARS)+
		public Group getGroup_1() { return cGroup_1; }
		
		//('/' | ".")+
		public Alternatives getAlternatives_1_0() { return cAlternatives_1_0; }
		
		//'/'
		public Keyword getSolidusKeyword_1_0_0() { return cSolidusKeyword_1_0_0; }
		
		//"."
		public Keyword getFullStopKeyword_1_0_1() { return cFullStopKeyword_1_0_1; }
		
		//ALPHA_NUMERIC_CHARS
		public RuleCall getALPHA_NUMERIC_CHARSParserRuleCall_1_1() { return cALPHA_NUMERIC_CHARSParserRuleCall_1_1; }
		
		//('/' | ".")*
		public Alternatives getAlternatives_2() { return cAlternatives_2; }
		
		//'/'
		public Keyword getSolidusKeyword_2_0() { return cSolidusKeyword_2_0; }
		
		//"."
		public Keyword getFullStopKeyword_2_1() { return cFullStopKeyword_2_1; }
	}
	public class URLElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.URL");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final RuleCall cLETTER_NO_VParserRuleCall_0_0 = (RuleCall)cGroup_0.eContents().get(0);
		private final RuleCall cALPHA_NUMERIC_CHARParserRuleCall_0_1 = (RuleCall)cGroup_0.eContents().get(1);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Alternatives cAlternatives_1_0 = (Alternatives)cGroup_1.eContents().get(0);
		private final Keyword cSolidusKeyword_1_0_0 = (Keyword)cAlternatives_1_0.eContents().get(0);
		private final Keyword cFullStopKeyword_1_0_1 = (Keyword)cAlternatives_1_0.eContents().get(1);
		private final Keyword cColonKeyword_1_0_2 = (Keyword)cAlternatives_1_0.eContents().get(2);
		private final Keyword cCommercialAtKeyword_1_0_3 = (Keyword)cAlternatives_1_0.eContents().get(3);
		private final RuleCall cALPHA_NUMERIC_CHARSParserRuleCall_1_1 = (RuleCall)cGroup_1.eContents().get(1);
		
		//URL:
		//	(LETTER_NO_V ALPHA_NUMERIC_CHAR)* (('/' | "." | ':' | '@')+ ALPHA_NUMERIC_CHARS)+;
		@Override public ParserRule getRule() { return rule; }
		
		//(LETTER_NO_V ALPHA_NUMERIC_CHAR)* (('/' | "." | ':' | '@')+ ALPHA_NUMERIC_CHARS)+
		public Group getGroup() { return cGroup; }
		
		//(LETTER_NO_V ALPHA_NUMERIC_CHAR)*
		public Group getGroup_0() { return cGroup_0; }
		
		//LETTER_NO_V
		public RuleCall getLETTER_NO_VParserRuleCall_0_0() { return cLETTER_NO_VParserRuleCall_0_0; }
		
		//ALPHA_NUMERIC_CHAR
		public RuleCall getALPHA_NUMERIC_CHARParserRuleCall_0_1() { return cALPHA_NUMERIC_CHARParserRuleCall_0_1; }
		
		//(('/' | "." | ':' | '@')+ ALPHA_NUMERIC_CHARS)+
		public Group getGroup_1() { return cGroup_1; }
		
		//('/' | "." | ':' | '@')+
		public Alternatives getAlternatives_1_0() { return cAlternatives_1_0; }
		
		//'/'
		public Keyword getSolidusKeyword_1_0_0() { return cSolidusKeyword_1_0_0; }
		
		//"."
		public Keyword getFullStopKeyword_1_0_1() { return cFullStopKeyword_1_0_1; }
		
		//':'
		public Keyword getColonKeyword_1_0_2() { return cColonKeyword_1_0_2; }
		
		//'@'
		public Keyword getCommercialAtKeyword_1_0_3() { return cCommercialAtKeyword_1_0_3; }
		
		//ALPHA_NUMERIC_CHARS
		public RuleCall getALPHA_NUMERIC_CHARSParserRuleCall_1_1() { return cALPHA_NUMERIC_CHARSParserRuleCall_1_1; }
	}
	public class ALPHA_NUMERIC_CHARSElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.ALPHA_NUMERIC_CHARS");
		private final RuleCall cALPHA_NUMERIC_CHARParserRuleCall = (RuleCall)rule.eContents().get(1);
		
		//ALPHA_NUMERIC_CHARS:
		//	ALPHA_NUMERIC_CHAR+;
		@Override public ParserRule getRule() { return rule; }
		
		//ALPHA_NUMERIC_CHAR+
		public RuleCall getALPHA_NUMERIC_CHARParserRuleCall() { return cALPHA_NUMERIC_CHARParserRuleCall; }
	}
	public class ALPHA_NUMERIC_CHARElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.ALPHA_NUMERIC_CHAR");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cHyphenMinusKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final RuleCall cDIGITSTerminalRuleCall_1_0 = (RuleCall)cAlternatives_1.eContents().get(0);
		private final RuleCall cLETTERSParserRuleCall_1_1 = (RuleCall)cAlternatives_1.eContents().get(1);
		
		//ALPHA_NUMERIC_CHAR:
		//	"-"? (DIGITS | LETTERS);
		@Override public ParserRule getRule() { return rule; }
		
		//"-"? (DIGITS | LETTERS)
		public Group getGroup() { return cGroup; }
		
		//"-"?
		public Keyword getHyphenMinusKeyword_0() { return cHyphenMinusKeyword_0; }
		
		//DIGITS | LETTERS
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//DIGITS
		public RuleCall getDIGITSTerminalRuleCall_1_0() { return cDIGITSTerminalRuleCall_1_0; }
		
		//LETTERS
		public RuleCall getLETTERSParserRuleCall_1_1() { return cLETTERSParserRuleCall_1_1; }
	}
	public class LETTERSElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.LETTERS");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cLETTER_VTerminalRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cLETTER_NO_VParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//LETTERS:
		//	(LETTER_V | LETTER_NO_V)+;
		@Override public ParserRule getRule() { return rule; }
		
		//(LETTER_V | LETTER_NO_V)+
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//LETTER_V
		public RuleCall getLETTER_VTerminalRuleCall_0() { return cLETTER_VTerminalRuleCall_0; }
		
		//LETTER_NO_V
		public RuleCall getLETTER_NO_VParserRuleCall_1() { return cLETTER_NO_VParserRuleCall_1; }
	}
	public class LETTER_NO_VElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.LETTER_NO_V");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cLETTER_XTerminalRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cLETTER_NO_VXTerminalRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//LETTER_NO_V:
		//	LETTER_X | LETTER_NO_VX;
		@Override public ParserRule getRule() { return rule; }
		
		//LETTER_X | LETTER_NO_VX
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//LETTER_X
		public RuleCall getLETTER_XTerminalRuleCall_0() { return cLETTER_XTerminalRuleCall_0; }
		
		//LETTER_NO_VX
		public RuleCall getLETTER_NO_VXTerminalRuleCall_1() { return cLETTER_NO_VXTerminalRuleCall_1; }
	}
	public class WILDCARDElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.WILDCARD");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cLETTER_XTerminalRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cASTERIXTerminalRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//WILDCARD:
		//	LETTER_X | ASTERIX;
		@Override public ParserRule getRule() { return rule; }
		
		//LETTER_X | ASTERIX
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//LETTER_X
		public RuleCall getLETTER_XTerminalRuleCall_0() { return cLETTER_XTerminalRuleCall_0; }
		
		//ASTERIX
		public RuleCall getASTERIXTerminalRuleCall_1() { return cASTERIXTerminalRuleCall_1; }
	}
	
	public class VersionComparatorElements extends AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.VersionComparator");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cVersionEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cVersionVKeyword_0_0 = (Keyword)cVersionEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cEqualsEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cEqualsEqualsSignKeyword_1_0 = (Keyword)cEqualsEnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cSmallerEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cSmallerLessThanSignKeyword_2_0 = (Keyword)cSmallerEnumLiteralDeclaration_2.eContents().get(0);
		private final EnumLiteralDeclaration cTildeEnumLiteralDeclaration_3 = (EnumLiteralDeclaration)cAlternatives.eContents().get(3);
		private final Keyword cTildeTildeKeyword_3_0 = (Keyword)cTildeEnumLiteralDeclaration_3.eContents().get(0);
		private final EnumLiteralDeclaration cCaretEnumLiteralDeclaration_4 = (EnumLiteralDeclaration)cAlternatives.eContents().get(4);
		private final Keyword cCaretCircumflexAccentKeyword_4_0 = (Keyword)cCaretEnumLiteralDeclaration_4.eContents().get(0);
		private final EnumLiteralDeclaration cSmallerEqualsEnumLiteralDeclaration_5 = (EnumLiteralDeclaration)cAlternatives.eContents().get(5);
		private final Keyword cSmallerEqualsLessThanSignEqualsSignKeyword_5_0 = (Keyword)cSmallerEqualsEnumLiteralDeclaration_5.eContents().get(0);
		private final EnumLiteralDeclaration cGreaterEnumLiteralDeclaration_6 = (EnumLiteralDeclaration)cAlternatives.eContents().get(6);
		private final Keyword cGreaterGreaterThanSignKeyword_6_0 = (Keyword)cGreaterEnumLiteralDeclaration_6.eContents().get(0);
		private final EnumLiteralDeclaration cGreaterEqualsEnumLiteralDeclaration_7 = (EnumLiteralDeclaration)cAlternatives.eContents().get(7);
		private final Keyword cGreaterEqualsGreaterThanSignEqualsSignKeyword_7_0 = (Keyword)cGreaterEqualsEnumLiteralDeclaration_7.eContents().get(0);
		
		//enum VersionComparator:
		//	Version='v'
		//	| Equals='='
		//	| Smaller='<'
		//	| Tilde='~'
		//	| Caret='^'
		//	| SmallerEquals='<='
		//	| Greater='>'
		//	| GreaterEquals='>=';
		public EnumRule getRule() { return rule; }
		
		//Version='v' | Equals='=' | Smaller='<' | Tilde='~' | Caret='^' | SmallerEquals='<=' | Greater='>' | GreaterEquals='>='
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//Version='v'
		public EnumLiteralDeclaration getVersionEnumLiteralDeclaration_0() { return cVersionEnumLiteralDeclaration_0; }
		
		//'v'
		public Keyword getVersionVKeyword_0_0() { return cVersionVKeyword_0_0; }
		
		//Equals='='
		public EnumLiteralDeclaration getEqualsEnumLiteralDeclaration_1() { return cEqualsEnumLiteralDeclaration_1; }
		
		//'='
		public Keyword getEqualsEqualsSignKeyword_1_0() { return cEqualsEqualsSignKeyword_1_0; }
		
		//Smaller='<'
		public EnumLiteralDeclaration getSmallerEnumLiteralDeclaration_2() { return cSmallerEnumLiteralDeclaration_2; }
		
		//'<'
		public Keyword getSmallerLessThanSignKeyword_2_0() { return cSmallerLessThanSignKeyword_2_0; }
		
		//Tilde='~'
		public EnumLiteralDeclaration getTildeEnumLiteralDeclaration_3() { return cTildeEnumLiteralDeclaration_3; }
		
		//'~'
		public Keyword getTildeTildeKeyword_3_0() { return cTildeTildeKeyword_3_0; }
		
		//Caret='^'
		public EnumLiteralDeclaration getCaretEnumLiteralDeclaration_4() { return cCaretEnumLiteralDeclaration_4; }
		
		//'^'
		public Keyword getCaretCircumflexAccentKeyword_4_0() { return cCaretCircumflexAccentKeyword_4_0; }
		
		//SmallerEquals='<='
		public EnumLiteralDeclaration getSmallerEqualsEnumLiteralDeclaration_5() { return cSmallerEqualsEnumLiteralDeclaration_5; }
		
		//'<='
		public Keyword getSmallerEqualsLessThanSignEqualsSignKeyword_5_0() { return cSmallerEqualsLessThanSignEqualsSignKeyword_5_0; }
		
		//Greater='>'
		public EnumLiteralDeclaration getGreaterEnumLiteralDeclaration_6() { return cGreaterEnumLiteralDeclaration_6; }
		
		//'>'
		public Keyword getGreaterGreaterThanSignKeyword_6_0() { return cGreaterGreaterThanSignKeyword_6_0; }
		
		//GreaterEquals='>='
		public EnumLiteralDeclaration getGreaterEqualsEnumLiteralDeclaration_7() { return cGreaterEqualsEnumLiteralDeclaration_7; }
		
		//'>='
		public Keyword getGreaterEqualsGreaterThanSignEqualsSignKeyword_7_0() { return cGreaterEqualsGreaterThanSignEqualsSignKeyword_7_0; }
	}
	
	private final NPMVersionRequirementElements pNPMVersionRequirement;
	private final URLVersionRequirementElements pURLVersionRequirement;
	private final URLVersionSpecifierElements pURLVersionSpecifier;
	private final URLSemverElements pURLSemver;
	private final URLCommitISHElements pURLCommitISH;
	private final TagVersionRequirementElements pTagVersionRequirement;
	private final GitHubVersionRequirementElements pGitHubVersionRequirement;
	private final LocalPathVersionRequirementElements pLocalPathVersionRequirement;
	private final VersionRangeSetRequirementElements pVersionRangeSetRequirement;
	private final VersionRangeElements pVersionRange;
	private final HyphenVersionRangeElements pHyphenVersionRange;
	private final VersionRangeContraintElements pVersionRangeContraint;
	private final SimpleVersionElements pSimpleVersion;
	private final VersionNumberElements pVersionNumber;
	private final VersionPartElements pVersionPart;
	private final QualifierElements pQualifier;
	private final QualifierTagElements pQualifierTag;
	private final URL_PROTOCOLElements pURL_PROTOCOL;
	private final TAGElements pTAG;
	private final PATHElements pPATH;
	private final URLElements pURL;
	private final ALPHA_NUMERIC_CHARSElements pALPHA_NUMERIC_CHARS;
	private final ALPHA_NUMERIC_CHARElements pALPHA_NUMERIC_CHAR;
	private final LETTERSElements pLETTERS;
	private final LETTER_NO_VElements pLETTER_NO_V;
	private final WILDCARDElements pWILDCARD;
	private final TerminalRule tLETTER_NO_VX;
	private final TerminalRule tLETTER_V;
	private final TerminalRule tLETTER_X;
	private final TerminalRule tASTERIX;
	private final TerminalRule tDIGITS;
	private final TerminalRule tDIGIT;
	private final TerminalRule tWS;
	private final TerminalRule tEOL;
	private final VersionComparatorElements eVersionComparator;
	
	private final Grammar grammar;
	
	private final UnicodeGrammarAccess gaUnicode;

	@Inject
	public SemverGrammarAccess(GrammarProvider grammarProvider,
			UnicodeGrammarAccess gaUnicode) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaUnicode = gaUnicode;
		this.pNPMVersionRequirement = new NPMVersionRequirementElements();
		this.pURLVersionRequirement = new URLVersionRequirementElements();
		this.pURLVersionSpecifier = new URLVersionSpecifierElements();
		this.pURLSemver = new URLSemverElements();
		this.pURLCommitISH = new URLCommitISHElements();
		this.pTagVersionRequirement = new TagVersionRequirementElements();
		this.pGitHubVersionRequirement = new GitHubVersionRequirementElements();
		this.pLocalPathVersionRequirement = new LocalPathVersionRequirementElements();
		this.pVersionRangeSetRequirement = new VersionRangeSetRequirementElements();
		this.pVersionRange = new VersionRangeElements();
		this.pHyphenVersionRange = new HyphenVersionRangeElements();
		this.pVersionRangeContraint = new VersionRangeContraintElements();
		this.pSimpleVersion = new SimpleVersionElements();
		this.pVersionNumber = new VersionNumberElements();
		this.pVersionPart = new VersionPartElements();
		this.pQualifier = new QualifierElements();
		this.pQualifierTag = new QualifierTagElements();
		this.pURL_PROTOCOL = new URL_PROTOCOLElements();
		this.pTAG = new TAGElements();
		this.pPATH = new PATHElements();
		this.pURL = new URLElements();
		this.pALPHA_NUMERIC_CHARS = new ALPHA_NUMERIC_CHARSElements();
		this.pALPHA_NUMERIC_CHAR = new ALPHA_NUMERIC_CHARElements();
		this.pLETTERS = new LETTERSElements();
		this.pLETTER_NO_V = new LETTER_NO_VElements();
		this.pWILDCARD = new WILDCARDElements();
		this.tLETTER_NO_VX = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.LETTER_NO_VX");
		this.tLETTER_V = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.LETTER_V");
		this.tLETTER_X = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.LETTER_X");
		this.tASTERIX = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.ASTERIX");
		this.tDIGITS = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.DIGITS");
		this.tDIGIT = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.DIGIT");
		this.tWS = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.WS");
		this.tEOL = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.semver.Semver.EOL");
		this.eVersionComparator = new VersionComparatorElements();
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("org.eclipse.n4js.semver.Semver".equals(grammar.getName())) {
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

	
	//// This grammar of SemVer 2.0.0 is an adapted version of the BNF found at:
	////  https://docs.npmjs.com/misc/semver
	//NPMVersionRequirement:
	//	WS*
	//	VersionRangeSetRequirement
	//	| (URLVersionRequirement
	//	| LocalPathVersionRequirement
	//	| GitHubVersionRequirement
	//	| TagVersionRequirement) WS*;
	public NPMVersionRequirementElements getNPMVersionRequirementAccess() {
		return pNPMVersionRequirement;
	}
	
	public ParserRule getNPMVersionRequirementRule() {
		return getNPMVersionRequirementAccess().getRule();
	}
	
	//URLVersionRequirement:
	//	protocol=URL_PROTOCOL '://' url=URL ('#' versionSpecifier=URLVersionSpecifier)?;
	public URLVersionRequirementElements getURLVersionRequirementAccess() {
		return pURLVersionRequirement;
	}
	
	public ParserRule getURLVersionRequirementRule() {
		return getURLVersionRequirementAccess().getRule();
	}
	
	//URLVersionSpecifier:
	//	=> URLSemver
	//	| URLCommitISH;
	public URLVersionSpecifierElements getURLVersionSpecifierAccess() {
		return pURLVersionSpecifier;
	}
	
	public ParserRule getURLVersionSpecifierRule() {
		return getURLVersionSpecifierAccess().getRule();
	}
	
	//URLSemver:
	//	"semver:"? simpleVersion=SimpleVersion;
	public URLSemverElements getURLSemverAccess() {
		return pURLSemver;
	}
	
	public ParserRule getURLSemverRule() {
		return getURLSemverAccess().getRule();
	}
	
	//URLCommitISH:
	//	commitISH=ALPHA_NUMERIC_CHARS;
	public URLCommitISHElements getURLCommitISHAccess() {
		return pURLCommitISH;
	}
	
	public ParserRule getURLCommitISHRule() {
		return getURLCommitISHAccess().getRule();
	}
	
	//TagVersionRequirement:
	//	tagName=TAG;
	public TagVersionRequirementElements getTagVersionRequirementAccess() {
		return pTagVersionRequirement;
	}
	
	public ParserRule getTagVersionRequirementRule() {
		return getTagVersionRequirementAccess().getRule();
	}
	
	//GitHubVersionRequirement:
	//	githubUrl=URL ('#' commitISH=ALPHA_NUMERIC_CHARS)?;
	public GitHubVersionRequirementElements getGitHubVersionRequirementAccess() {
		return pGitHubVersionRequirement;
	}
	
	public ParserRule getGitHubVersionRequirementRule() {
		return getGitHubVersionRequirementAccess().getRule();
	}
	
	//LocalPathVersionRequirement:
	//	'file:' localPath=PATH;
	public LocalPathVersionRequirementElements getLocalPathVersionRequirementAccess() {
		return pLocalPathVersionRequirement;
	}
	
	public ParserRule getLocalPathVersionRequirementRule() {
		return getLocalPathVersionRequirementAccess().getRule();
	}
	
	//VersionRangeSetRequirement:
	//	{VersionRangeSetRequirement} (ranges+=VersionRange (WS* '||' WS* ranges+=VersionRange)* WS*)?;
	public VersionRangeSetRequirementElements getVersionRangeSetRequirementAccess() {
		return pVersionRangeSetRequirement;
	}
	
	public ParserRule getVersionRangeSetRequirementRule() {
		return getVersionRangeSetRequirementAccess().getRule();
	}
	
	//VersionRange:
	//	VersionRangeContraint | HyphenVersionRange;
	public VersionRangeElements getVersionRangeAccess() {
		return pVersionRange;
	}
	
	public ParserRule getVersionRangeRule() {
		return getVersionRangeAccess().getRule();
	}
	
	//HyphenVersionRange VersionRange:
	//	{HyphenVersionRange} from=VersionNumber WS+ '-' WS+ to=VersionNumber;
	public HyphenVersionRangeElements getHyphenVersionRangeAccess() {
		return pHyphenVersionRange;
	}
	
	public ParserRule getHyphenVersionRangeRule() {
		return getHyphenVersionRangeAccess().getRule();
	}
	
	//VersionRangeContraint VersionRange:
	//	{VersionRangeConstraint} versionConstraints+=SimpleVersion (WS+ versionConstraints+=SimpleVersion)*;
	public VersionRangeContraintElements getVersionRangeContraintAccess() {
		return pVersionRangeContraint;
	}
	
	public ParserRule getVersionRangeContraintRule() {
		return getVersionRangeContraintAccess().getRule();
	}
	
	//SimpleVersion:
	//	{SimpleVersion} (comparators+=VersionComparator WS*)* number=VersionNumber;
	public SimpleVersionElements getSimpleVersionAccess() {
		return pSimpleVersion;
	}
	
	public ParserRule getSimpleVersionRule() {
		return getSimpleVersionAccess().getRule();
	}
	
	//VersionNumber:
	//	major=VersionPart ("." minor=VersionPart ("." patch=VersionPart ("." extended+=VersionPart)*)?)?
	//	qualifier=Qualifier?;
	public VersionNumberElements getVersionNumberAccess() {
		return pVersionNumber;
	}
	
	public ParserRule getVersionNumberRule() {
		return getVersionNumberAccess().getRule();
	}
	
	//VersionPart:
	//	wildcard?=WILDCARD | numberRaw=DIGITS;
	public VersionPartElements getVersionPartAccess() {
		return pVersionPart;
	}
	
	public ParserRule getVersionPartRule() {
		return getVersionPartAccess().getRule();
	}
	
	//Qualifier:
	//	'-' preRelease=QualifierTag | '+' buildMetadata=QualifierTag | '-' preRelease=QualifierTag '+'
	//	buildMetadata=QualifierTag;
	public QualifierElements getQualifierAccess() {
		return pQualifier;
	}
	
	public ParserRule getQualifierRule() {
		return getQualifierAccess().getRule();
	}
	
	//QualifierTag:
	//	parts+=ALPHA_NUMERIC_CHARS ("." parts+=ALPHA_NUMERIC_CHARS)*;
	public QualifierTagElements getQualifierTagAccess() {
		return pQualifierTag;
	}
	
	public ParserRule getQualifierTagRule() {
		return getQualifierTagAccess().getRule();
	}
	
	//URL_PROTOCOL:
	//	(LETTERS | '+')+;
	public URL_PROTOCOLElements getURL_PROTOCOLAccess() {
		return pURL_PROTOCOL;
	}
	
	public ParserRule getURL_PROTOCOLRule() {
		return getURL_PROTOCOLAccess().getRule();
	}
	
	//TAG:
	//	LETTER_NO_V ALPHA_NUMERIC_CHARS*;
	public TAGElements getTAGAccess() {
		return pTAG;
	}
	
	public ParserRule getTAGRule() {
		return getTAGAccess().getRule();
	}
	
	//PATH:
	//	(LETTER_NO_V ALPHA_NUMERIC_CHAR)* (('/' | ".")+ ALPHA_NUMERIC_CHARS)+ ('/' | ".")*;
	public PATHElements getPATHAccess() {
		return pPATH;
	}
	
	public ParserRule getPATHRule() {
		return getPATHAccess().getRule();
	}
	
	//URL:
	//	(LETTER_NO_V ALPHA_NUMERIC_CHAR)* (('/' | "." | ':' | '@')+ ALPHA_NUMERIC_CHARS)+;
	public URLElements getURLAccess() {
		return pURL;
	}
	
	public ParserRule getURLRule() {
		return getURLAccess().getRule();
	}
	
	//ALPHA_NUMERIC_CHARS:
	//	ALPHA_NUMERIC_CHAR+;
	public ALPHA_NUMERIC_CHARSElements getALPHA_NUMERIC_CHARSAccess() {
		return pALPHA_NUMERIC_CHARS;
	}
	
	public ParserRule getALPHA_NUMERIC_CHARSRule() {
		return getALPHA_NUMERIC_CHARSAccess().getRule();
	}
	
	//ALPHA_NUMERIC_CHAR:
	//	"-"? (DIGITS | LETTERS);
	public ALPHA_NUMERIC_CHARElements getALPHA_NUMERIC_CHARAccess() {
		return pALPHA_NUMERIC_CHAR;
	}
	
	public ParserRule getALPHA_NUMERIC_CHARRule() {
		return getALPHA_NUMERIC_CHARAccess().getRule();
	}
	
	//LETTERS:
	//	(LETTER_V | LETTER_NO_V)+;
	public LETTERSElements getLETTERSAccess() {
		return pLETTERS;
	}
	
	public ParserRule getLETTERSRule() {
		return getLETTERSAccess().getRule();
	}
	
	//LETTER_NO_V:
	//	LETTER_X | LETTER_NO_VX;
	public LETTER_NO_VElements getLETTER_NO_VAccess() {
		return pLETTER_NO_V;
	}
	
	public ParserRule getLETTER_NO_VRule() {
		return getLETTER_NO_VAccess().getRule();
	}
	
	//WILDCARD:
	//	LETTER_X | ASTERIX;
	public WILDCARDElements getWILDCARDAccess() {
		return pWILDCARD;
	}
	
	public ParserRule getWILDCARDRule() {
		return getWILDCARDAccess().getRule();
	}
	
	//terminal LETTER_NO_VX:
	//	"a".."u" | "w" | "y" | "z" | "A".."U" | "W" | "Y" | "Z";
	public TerminalRule getLETTER_NO_VXRule() {
		return tLETTER_NO_VX;
	}
	
	//terminal LETTER_V:
	//	"v" | "V";
	public TerminalRule getLETTER_VRule() {
		return tLETTER_V;
	}
	
	//terminal LETTER_X:
	//	'x' | 'X';
	public TerminalRule getLETTER_XRule() {
		return tLETTER_X;
	}
	
	//terminal ASTERIX:
	//	'*';
	public TerminalRule getASTERIXRule() {
		return tASTERIX;
	}
	
	//terminal DIGITS returns ecore::EInt:
	//	DIGIT+;
	public TerminalRule getDIGITSRule() {
		return tDIGITS;
	}
	
	//terminal fragment DIGIT:
	//	"0".."9";
	public TerminalRule getDIGITRule() {
		return tDIGIT;
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
	//	Version='v'
	//	| Equals='='
	//	| Smaller='<'
	//	| Tilde='~'
	//	| Caret='^'
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
