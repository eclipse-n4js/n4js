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
grammar InternalSemver;

options {
	superClass=AbstractInternalAntlrParser;
}

@lexer::header {
package org.eclipse.n4js.semver.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

@parser::header {
package org.eclipse.n4js.semver.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.n4js.semver.services.SemverGrammarAccess;

}

@parser::members {

 	private SemverGrammarAccess grammarAccess;

    public InternalSemverParser(TokenStream input, SemverGrammarAccess grammarAccess) {
        this(input);
        this.grammarAccess = grammarAccess;
        registerRules(grammarAccess.getGrammar());
    }

    @Override
    protected String getFirstRuleName() {
    	return "NPMVersionRequirement";
   	}

   	@Override
   	protected SemverGrammarAccess getGrammarAccess() {
   		return grammarAccess;
   	}

}

@rulecatch {
    catch (RecognitionException re) {
        recover(input,re);
        appendSkippedTokens();
    }
}

// Entry rule entryRuleNPMVersionRequirement
entryRuleNPMVersionRequirement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getNPMVersionRequirementRule()); }
	iv_ruleNPMVersionRequirement=ruleNPMVersionRequirement
	{ $current=$iv_ruleNPMVersionRequirement.current; }
	EOF;

// Rule NPMVersionRequirement
ruleNPMVersionRequirement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				this_WS_0=RULE_WS
				{
					newLeafNode(this_WS_0, grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_0_0());
				}
			)?
			{
				newCompositeNode(grammarAccess.getNPMVersionRequirementAccess().getVersionRangeSetRequirementParserRuleCall_0_1());
			}
			this_VersionRangeSetRequirement_1=ruleVersionRangeSetRequirement
			{
				$current = $this_VersionRangeSetRequirement_1.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			(
				(
					(ruleLocalPathVersionRequirement)=>
					{
						newCompositeNode(grammarAccess.getNPMVersionRequirementAccess().getLocalPathVersionRequirementParserRuleCall_1_0_0());
					}
					this_LocalPathVersionRequirement_2=ruleLocalPathVersionRequirement
					{
						$current = $this_LocalPathVersionRequirement_2.current;
						afterParserOrEnumRuleCall();
					}
				)
				    |
				(
					(
						(ruleURLVersionRequirement)=>
						{
							newCompositeNode(grammarAccess.getNPMVersionRequirementAccess().getURLVersionRequirementParserRuleCall_1_0_1_0());
						}
						this_URLVersionRequirement_3=ruleURLVersionRequirement
						{
							$current = $this_URLVersionRequirement_3.current;
							afterParserOrEnumRuleCall();
						}
					)
					    |
					{
						newCompositeNode(grammarAccess.getNPMVersionRequirementAccess().getGitHubVersionRequirementParserRuleCall_1_0_1_1());
					}
					this_GitHubVersionRequirement_4=ruleGitHubVersionRequirement
					{
						$current = $this_GitHubVersionRequirement_4.current;
						afterParserOrEnumRuleCall();
					}
					    |
					{
						newCompositeNode(grammarAccess.getNPMVersionRequirementAccess().getTagVersionRequirementParserRuleCall_1_0_1_2());
					}
					this_TagVersionRequirement_5=ruleTagVersionRequirement
					{
						$current = $this_TagVersionRequirement_5.current;
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				this_WS_6=RULE_WS
				{
					newLeafNode(this_WS_6, grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_1_1());
				}
			)?
		)
	)
;

// Entry rule entryRuleLocalPathVersionRequirement
entryRuleLocalPathVersionRequirement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getLocalPathVersionRequirementRule()); }
	iv_ruleLocalPathVersionRequirement=ruleLocalPathVersionRequirement
	{ $current=$iv_ruleLocalPathVersionRequirement.current; }
	EOF;

// Rule LocalPathVersionRequirement
ruleLocalPathVersionRequirement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getLocalPathVersionRequirementAccess().getFILE_TAGParserRuleCall_0());
		}
		ruleFILE_TAG
		{
			afterParserOrEnumRuleCall();
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getLocalPathVersionRequirementAccess().getLocalPathPATHParserRuleCall_1_0());
				}
				lv_localPath_1_0=rulePATH
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getLocalPathVersionRequirementRule());
					}
					set(
						$current,
						"localPath",
						lv_localPath_1_0,
						"org.eclipse.n4js.semver.Semver.PATH");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleURLVersionRequirement
entryRuleURLVersionRequirement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getURLVersionRequirementRule()); }
	iv_ruleURLVersionRequirement=ruleURLVersionRequirement
	{ $current=$iv_ruleURLVersionRequirement.current; }
	EOF;

// Rule URLVersionRequirement
ruleURLVersionRequirement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					newCompositeNode(grammarAccess.getURLVersionRequirementAccess().getProtocolURL_PROTOCOLParserRuleCall_0_0());
				}
				lv_protocol_0_0=ruleURL_PROTOCOL
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getURLVersionRequirementRule());
					}
					set(
						$current,
						"protocol",
						lv_protocol_0_0,
						"org.eclipse.n4js.semver.Semver.URL_PROTOCOL");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_1=':'
			{
				newLeafNode(otherlv_1, grammarAccess.getURLVersionRequirementAccess().getColonKeyword_1_0());
			}
			otherlv_2='/'
			{
				newLeafNode(otherlv_2, grammarAccess.getURLVersionRequirementAccess().getSolidusKeyword_1_1());
			}
			otherlv_3='/'
			{
				newLeafNode(otherlv_3, grammarAccess.getURLVersionRequirementAccess().getSolidusKeyword_1_2());
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getURLVersionRequirementAccess().getUrlURLParserRuleCall_2_0());
				}
				lv_url_4_0=ruleURL
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getURLVersionRequirementRule());
					}
					set(
						$current,
						"url",
						lv_url_4_0,
						"org.eclipse.n4js.semver.Semver.URL");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_5='#'
			{
				newLeafNode(otherlv_5, grammarAccess.getURLVersionRequirementAccess().getNumberSignKeyword_3_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getURLVersionRequirementAccess().getVersionSpecifierURLVersionSpecifierParserRuleCall_3_1_0());
					}
					lv_versionSpecifier_6_0=ruleURLVersionSpecifier
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getURLVersionRequirementRule());
						}
						set(
							$current,
							"versionSpecifier",
							lv_versionSpecifier_6_0,
							"org.eclipse.n4js.semver.Semver.URLVersionSpecifier");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;

// Entry rule entryRuleURLVersionSpecifier
entryRuleURLVersionSpecifier returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getURLVersionSpecifierRule()); }
	iv_ruleURLVersionSpecifier=ruleURLVersionSpecifier
	{ $current=$iv_ruleURLVersionSpecifier.current; }
	EOF;

// Rule URLVersionSpecifier
ruleURLVersionSpecifier returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(ruleURLSemver
			)=>
			{
				newCompositeNode(grammarAccess.getURLVersionSpecifierAccess().getURLSemverParserRuleCall_0_0());
			}
			this_URLSemver_0=ruleURLSemver
			{
				$current = $this_URLSemver_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			(
				{
					$current = forceCreateModelElement(
						grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_1_0(),
						$current);
				}
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getURLVersionSpecifierAccess().getCommitISHALPHA_NUMERIC_CHARS_START_WITH_DIGITSParserRuleCall_1_1_0());
					}
					lv_commitISH_2_0=ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getURLVersionSpecifierRule());
						}
						set(
							$current,
							"commitISH",
							lv_commitISH_2_0,
							"org.eclipse.n4js.semver.Semver.ALPHA_NUMERIC_CHARS_START_WITH_DIGITS");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		    |
		(
			(
				{
					$current = forceCreateModelElement(
						grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_2_0(),
						$current);
				}
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getURLVersionSpecifierAccess().getCommitISHALPHA_NUMERIC_CHARSParserRuleCall_2_1_0());
					}
					lv_commitISH_4_0=ruleALPHA_NUMERIC_CHARS
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getURLVersionSpecifierRule());
						}
						set(
							$current,
							"commitISH",
							lv_commitISH_4_0,
							"org.eclipse.n4js.semver.Semver.ALPHA_NUMERIC_CHARS");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;

// Entry rule entryRuleURLSemver
entryRuleURLSemver returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getURLSemverRule()); }
	iv_ruleURLSemver=ruleURLSemver
	{ $current=$iv_ruleURLSemver.current; }
	EOF;

// Rule URLSemver
ruleURLSemver returns [EObject current=null]
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
					grammarAccess.getURLSemverAccess().getURLSemverAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getURLSemverAccess().getWithSemverTagSEMVER_TAGParserRuleCall_1_0());
				}
				lv_withSemverTag_1_0=ruleSEMVER_TAG
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getURLSemverRule());
					}
					set(
						$current,
						"withSemverTag",
						true,
						"org.eclipse.n4js.semver.Semver.SEMVER_TAG");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getURLSemverAccess().getSimpleVersionSimpleVersionParserRuleCall_2_0());
				}
				lv_simpleVersion_2_0=ruleSimpleVersion
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getURLSemverRule());
					}
					set(
						$current,
						"simpleVersion",
						lv_simpleVersion_2_0,
						"org.eclipse.n4js.semver.Semver.SimpleVersion");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleTagVersionRequirement
entryRuleTagVersionRequirement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTagVersionRequirementRule()); }
	iv_ruleTagVersionRequirement=ruleTagVersionRequirement
	{ $current=$iv_ruleTagVersionRequirement.current; }
	EOF;

// Rule TagVersionRequirement
ruleTagVersionRequirement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getTagVersionRequirementAccess().getTagNameTAGParserRuleCall_0());
			}
			lv_tagName_0_0=ruleTAG
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getTagVersionRequirementRule());
				}
				set(
					$current,
					"tagName",
					lv_tagName_0_0,
					"org.eclipse.n4js.semver.Semver.TAG");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleGitHubVersionRequirement
entryRuleGitHubVersionRequirement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getGitHubVersionRequirementRule()); }
	iv_ruleGitHubVersionRequirement=ruleGitHubVersionRequirement
	{ $current=$iv_ruleGitHubVersionRequirement.current; }
	EOF;

// Rule GitHubVersionRequirement
ruleGitHubVersionRequirement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					newCompositeNode(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlURL_NO_VXParserRuleCall_0_0());
				}
				lv_githubUrl_0_0=ruleURL_NO_VX
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getGitHubVersionRequirementRule());
					}
					set(
						$current,
						"githubUrl",
						lv_githubUrl_0_0,
						"org.eclipse.n4js.semver.Semver.URL_NO_VX");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_1='#'
			{
				newLeafNode(otherlv_1, grammarAccess.getGitHubVersionRequirementAccess().getNumberSignKeyword_1_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getGitHubVersionRequirementAccess().getCommitISHALPHA_NUMERIC_CHARSParserRuleCall_1_1_0());
					}
					lv_commitISH_2_0=ruleALPHA_NUMERIC_CHARS
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getGitHubVersionRequirementRule());
						}
						set(
							$current,
							"commitISH",
							lv_commitISH_2_0,
							"org.eclipse.n4js.semver.Semver.ALPHA_NUMERIC_CHARS");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;

// Entry rule entryRuleVersionRangeSetRequirement
entryRuleVersionRangeSetRequirement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getVersionRangeSetRequirementRule()); }
	iv_ruleVersionRangeSetRequirement=ruleVersionRangeSetRequirement
	{ $current=$iv_ruleVersionRangeSetRequirement.current; }
	EOF;

// Rule VersionRangeSetRequirement
ruleVersionRangeSetRequirement returns [EObject current=null]
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
					grammarAccess.getVersionRangeSetRequirementAccess().getVersionRangeSetRequirementAction_0(),
					$current);
			}
		)
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getVersionRangeSetRequirementAccess().getRangesVersionRangeParserRuleCall_1_0_0());
					}
					lv_ranges_1_0=ruleVersionRange
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getVersionRangeSetRequirementRule());
						}
						add(
							$current,
							"ranges",
							lv_ranges_1_0,
							"org.eclipse.n4js.semver.Semver.VersionRange");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				(
					this_WS_2=RULE_WS
					{
						newLeafNode(this_WS_2, grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_0());
					}
				)?
				otherlv_3='||'
				{
					newLeafNode(otherlv_3, grammarAccess.getVersionRangeSetRequirementAccess().getVerticalLineVerticalLineKeyword_1_1_1());
				}
				(
					this_WS_4=RULE_WS
					{
						newLeafNode(this_WS_4, grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_2());
					}
				)?
				(
					(
						{
							newCompositeNode(grammarAccess.getVersionRangeSetRequirementAccess().getRangesVersionRangeParserRuleCall_1_1_3_0());
						}
						lv_ranges_5_0=ruleVersionRange
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getVersionRangeSetRequirementRule());
							}
							add(
								$current,
								"ranges",
								lv_ranges_5_0,
								"org.eclipse.n4js.semver.Semver.VersionRange");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
			(
				this_WS_6=RULE_WS
				{
					newLeafNode(this_WS_6, grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_2());
				}
			)?
		)?
	)
;

// Entry rule entryRuleVersionRange
entryRuleVersionRange returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getVersionRangeRule()); }
	iv_ruleVersionRange=ruleVersionRange
	{ $current=$iv_ruleVersionRange.current; }
	EOF;

// Rule VersionRange
ruleVersionRange returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getVersionRangeAccess().getVersionRangeContraintParserRuleCall_0());
		}
		this_VersionRangeContraint_0=ruleVersionRangeContraint
		{
			$current = $this_VersionRangeContraint_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getVersionRangeAccess().getHyphenVersionRangeParserRuleCall_1());
		}
		this_HyphenVersionRange_1=ruleHyphenVersionRange
		{
			$current = $this_HyphenVersionRange_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleHyphenVersionRange
entryRuleHyphenVersionRange returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getHyphenVersionRangeRule()); }
	iv_ruleHyphenVersionRange=ruleHyphenVersionRange
	{ $current=$iv_ruleHyphenVersionRange.current; }
	EOF;

// Rule HyphenVersionRange
ruleHyphenVersionRange returns [EObject current=null]
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
					grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getHyphenVersionRangeAccess().getFromVersionNumberParserRuleCall_1_0());
				}
				lv_from_1_0=ruleVersionNumber
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getHyphenVersionRangeRule());
					}
					set(
						$current,
						"from",
						lv_from_1_0,
						"org.eclipse.n4js.semver.Semver.VersionNumber");
					afterParserOrEnumRuleCall();
				}
			)
		)
		this_WS_2=RULE_WS
		{
			newLeafNode(this_WS_2, grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2());
		}
		otherlv_3='-'
		{
			newLeafNode(otherlv_3, grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_3());
		}
		this_WS_4=RULE_WS
		{
			newLeafNode(this_WS_4, grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getHyphenVersionRangeAccess().getToVersionNumberParserRuleCall_5_0());
				}
				lv_to_5_0=ruleVersionNumber
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getHyphenVersionRangeRule());
					}
					set(
						$current,
						"to",
						lv_to_5_0,
						"org.eclipse.n4js.semver.Semver.VersionNumber");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleVersionRangeContraint
entryRuleVersionRangeContraint returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getVersionRangeContraintRule()); }
	iv_ruleVersionRangeContraint=ruleVersionRangeContraint
	{ $current=$iv_ruleVersionRangeContraint.current; }
	EOF;

// Rule VersionRangeContraint
ruleVersionRangeContraint returns [EObject current=null]
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
					grammarAccess.getVersionRangeContraintAccess().getVersionRangeConstraintAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsSimpleVersionParserRuleCall_1_0());
				}
				lv_versionConstraints_1_0=ruleSimpleVersion
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVersionRangeContraintRule());
					}
					add(
						$current,
						"versionConstraints",
						lv_versionConstraints_1_0,
						"org.eclipse.n4js.semver.Semver.SimpleVersion");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			this_WS_2=RULE_WS
			{
				newLeafNode(this_WS_2, grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsSimpleVersionParserRuleCall_2_1_0());
					}
					lv_versionConstraints_3_0=ruleSimpleVersion
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getVersionRangeContraintRule());
						}
						add(
							$current,
							"versionConstraints",
							lv_versionConstraints_3_0,
							"org.eclipse.n4js.semver.Semver.SimpleVersion");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleSimpleVersion
entryRuleSimpleVersion returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSimpleVersionRule()); }
	iv_ruleSimpleVersion=ruleSimpleVersion
	{ $current=$iv_ruleSimpleVersion.current; }
	EOF;

// Rule SimpleVersion
ruleSimpleVersion returns [EObject current=null]
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
						newCompositeNode(grammarAccess.getSimpleVersionAccess().getComparatorsVersionComparatorEnumRuleCall_0_0_0());
					}
					lv_comparators_0_0=ruleVersionComparator
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSimpleVersionRule());
						}
						add(
							$current,
							"comparators",
							lv_comparators_0_0,
							"org.eclipse.n4js.semver.Semver.VersionComparator");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				this_WS_1=RULE_WS
				{
					newLeafNode(this_WS_1, grammarAccess.getSimpleVersionAccess().getWSTerminalRuleCall_0_1());
				}
			)?
		)*
		(
			(
				lv_withLetterV_2_0=RULE_LETTER_V
				{
					newLeafNode(lv_withLetterV_2_0, grammarAccess.getSimpleVersionAccess().getWithLetterVLETTER_VTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSimpleVersionRule());
					}
					setWithLastConsumed(
						$current,
						"withLetterV",
						true,
						"org.eclipse.n4js.semver.Semver.LETTER_V");
				}
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getSimpleVersionAccess().getNumberVersionNumberParserRuleCall_2_0());
				}
				lv_number_3_0=ruleVersionNumber
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSimpleVersionRule());
					}
					set(
						$current,
						"number",
						lv_number_3_0,
						"org.eclipse.n4js.semver.Semver.VersionNumber");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleVersionNumber
entryRuleVersionNumber returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getVersionNumberRule()); }
	iv_ruleVersionNumber=ruleVersionNumber
	{ $current=$iv_ruleVersionNumber.current; }
	EOF;

// Rule VersionNumber
ruleVersionNumber returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					newCompositeNode(grammarAccess.getVersionNumberAccess().getMajorVersionPartParserRuleCall_0_0());
				}
				lv_major_0_0=ruleVersionPart
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVersionNumberRule());
					}
					set(
						$current,
						"major",
						lv_major_0_0,
						"org.eclipse.n4js.semver.Semver.VersionPart");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_1='.'
			{
				newLeafNode(otherlv_1, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getVersionNumberAccess().getMinorVersionPartParserRuleCall_1_1_0());
					}
					lv_minor_2_0=ruleVersionPart
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getVersionNumberRule());
						}
						set(
							$current,
							"minor",
							lv_minor_2_0,
							"org.eclipse.n4js.semver.Semver.VersionPart");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_3='.'
				{
					newLeafNode(otherlv_3, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getVersionNumberAccess().getPatchVersionPartParserRuleCall_1_2_1_0());
						}
						lv_patch_4_0=ruleVersionPart
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getVersionNumberRule());
							}
							set(
								$current,
								"patch",
								lv_patch_4_0,
								"org.eclipse.n4js.semver.Semver.VersionPart");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					otherlv_5='.'
					{
						newLeafNode(otherlv_5, grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_2_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getVersionNumberAccess().getExtendedVersionPartParserRuleCall_1_2_2_1_0());
							}
							lv_extended_6_0=ruleVersionPart
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getVersionNumberRule());
								}
								add(
									$current,
									"extended",
									lv_extended_6_0,
									"org.eclipse.n4js.semver.Semver.VersionPart");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)*
			)?
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getVersionNumberAccess().getQualifierQualifierParserRuleCall_2_0());
				}
				lv_qualifier_7_0=ruleQualifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVersionNumberRule());
					}
					set(
						$current,
						"qualifier",
						lv_qualifier_7_0,
						"org.eclipse.n4js.semver.Semver.Qualifier");
					afterParserOrEnumRuleCall();
				}
			)
		)?
	)
;

// Entry rule entryRuleVersionPart
entryRuleVersionPart returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getVersionPartRule()); }
	iv_ruleVersionPart=ruleVersionPart
	{ $current=$iv_ruleVersionPart.current; }
	EOF;

// Rule VersionPart
ruleVersionPart returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					newCompositeNode(grammarAccess.getVersionPartAccess().getWildcardWILDCARDParserRuleCall_0_0());
				}
				lv_wildcard_0_0=ruleWILDCARD
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVersionPartRule());
					}
					set(
						$current,
						"wildcard",
						true,
						"org.eclipse.n4js.semver.Semver.WILDCARD");
					afterParserOrEnumRuleCall();
				}
			)
		)
		    |
		(
			(
				lv_numberRaw_1_0=RULE_DIGITS
				{
					newLeafNode(lv_numberRaw_1_0, grammarAccess.getVersionPartAccess().getNumberRawDIGITSTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getVersionPartRule());
					}
					setWithLastConsumed(
						$current,
						"numberRaw",
						lv_numberRaw_1_0,
						"org.eclipse.n4js.semver.Semver.DIGITS");
				}
			)
		)
	)
;

// Entry rule entryRuleQualifier
entryRuleQualifier returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getQualifierRule()); }
	iv_ruleQualifier=ruleQualifier
	{ $current=$iv_ruleQualifier.current; }
	EOF;

// Rule Qualifier
ruleQualifier returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			otherlv_0='-'
			{
				newLeafNode(otherlv_0, grammarAccess.getQualifierAccess().getHyphenMinusKeyword_0_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getQualifierAccess().getPreReleaseQualifierTagParserRuleCall_0_1_0());
					}
					lv_preRelease_1_0=ruleQualifierTag
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getQualifierRule());
						}
						set(
							$current,
							"preRelease",
							lv_preRelease_1_0,
							"org.eclipse.n4js.semver.Semver.QualifierTag");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_2='+'
				{
					newLeafNode(otherlv_2, grammarAccess.getQualifierAccess().getPlusSignKeyword_0_2_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getQualifierAccess().getBuildMetadataQualifierTagParserRuleCall_0_2_1_0());
						}
						lv_buildMetadata_3_0=ruleQualifierTag
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getQualifierRule());
							}
							set(
								$current,
								"buildMetadata",
								lv_buildMetadata_3_0,
								"org.eclipse.n4js.semver.Semver.QualifierTag");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)?
		)
		    |
		(
			otherlv_4='+'
			{
				newLeafNode(otherlv_4, grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getQualifierAccess().getBuildMetadataQualifierTagParserRuleCall_1_1_0());
					}
					lv_buildMetadata_5_0=ruleQualifierTag
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getQualifierRule());
						}
						set(
							$current,
							"buildMetadata",
							lv_buildMetadata_5_0,
							"org.eclipse.n4js.semver.Semver.QualifierTag");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;

// Entry rule entryRuleQualifierTag
entryRuleQualifierTag returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getQualifierTagRule()); }
	iv_ruleQualifierTag=ruleQualifierTag
	{ $current=$iv_ruleQualifierTag.current; }
	EOF;

// Rule QualifierTag
ruleQualifierTag returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					newCompositeNode(grammarAccess.getQualifierTagAccess().getPartsALPHA_NUMERIC_CHARSParserRuleCall_0_0());
				}
				lv_parts_0_0=ruleALPHA_NUMERIC_CHARS
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getQualifierTagRule());
					}
					add(
						$current,
						"parts",
						lv_parts_0_0,
						"org.eclipse.n4js.semver.Semver.ALPHA_NUMERIC_CHARS");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_1='.'
			{
				newLeafNode(otherlv_1, grammarAccess.getQualifierTagAccess().getFullStopKeyword_1_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getQualifierTagAccess().getPartsALPHA_NUMERIC_CHARSParserRuleCall_1_1_0());
					}
					lv_parts_2_0=ruleALPHA_NUMERIC_CHARS
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getQualifierTagRule());
						}
						add(
							$current,
							"parts",
							lv_parts_2_0,
							"org.eclipse.n4js.semver.Semver.ALPHA_NUMERIC_CHARS");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleFILE_TAG
entryRuleFILE_TAG returns [String current=null]:
	{ newCompositeNode(grammarAccess.getFILE_TAGRule()); }
	iv_ruleFILE_TAG=ruleFILE_TAG
	{ $current=$iv_ruleFILE_TAG.current.getText(); }
	EOF;

// Rule FILE_TAG
ruleFILE_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_LETTER_F_0=RULE_LETTER_F
		{
			$current.merge(this_LETTER_F_0);
		}
		{
			newLeafNode(this_LETTER_F_0, grammarAccess.getFILE_TAGAccess().getLETTER_FTerminalRuleCall_0());
		}
		this_LETTER_I_1=RULE_LETTER_I
		{
			$current.merge(this_LETTER_I_1);
		}
		{
			newLeafNode(this_LETTER_I_1, grammarAccess.getFILE_TAGAccess().getLETTER_ITerminalRuleCall_1());
		}
		this_LETTER_L_2=RULE_LETTER_L
		{
			$current.merge(this_LETTER_L_2);
		}
		{
			newLeafNode(this_LETTER_L_2, grammarAccess.getFILE_TAGAccess().getLETTER_LTerminalRuleCall_2());
		}
		this_LETTER_E_3=RULE_LETTER_E
		{
			$current.merge(this_LETTER_E_3);
		}
		{
			newLeafNode(this_LETTER_E_3, grammarAccess.getFILE_TAGAccess().getLETTER_ETerminalRuleCall_3());
		}
		kw=':'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getFILE_TAGAccess().getColonKeyword_4());
		}
	)
;

// Entry rule entryRuleSEMVER_TAG
entryRuleSEMVER_TAG returns [String current=null]:
	{ newCompositeNode(grammarAccess.getSEMVER_TAGRule()); }
	iv_ruleSEMVER_TAG=ruleSEMVER_TAG
	{ $current=$iv_ruleSEMVER_TAG.current.getText(); }
	EOF;

// Rule SEMVER_TAG
ruleSEMVER_TAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_LETTER_S_0=RULE_LETTER_S
		{
			$current.merge(this_LETTER_S_0);
		}
		{
			newLeafNode(this_LETTER_S_0, grammarAccess.getSEMVER_TAGAccess().getLETTER_STerminalRuleCall_0());
		}
		this_LETTER_E_1=RULE_LETTER_E
		{
			$current.merge(this_LETTER_E_1);
		}
		{
			newLeafNode(this_LETTER_E_1, grammarAccess.getSEMVER_TAGAccess().getLETTER_ETerminalRuleCall_1());
		}
		this_LETTER_M_2=RULE_LETTER_M
		{
			$current.merge(this_LETTER_M_2);
		}
		{
			newLeafNode(this_LETTER_M_2, grammarAccess.getSEMVER_TAGAccess().getLETTER_MTerminalRuleCall_2());
		}
		this_LETTER_V_3=RULE_LETTER_V
		{
			$current.merge(this_LETTER_V_3);
		}
		{
			newLeafNode(this_LETTER_V_3, grammarAccess.getSEMVER_TAGAccess().getLETTER_VTerminalRuleCall_3());
		}
		this_LETTER_E_4=RULE_LETTER_E
		{
			$current.merge(this_LETTER_E_4);
		}
		{
			newLeafNode(this_LETTER_E_4, grammarAccess.getSEMVER_TAGAccess().getLETTER_ETerminalRuleCall_4());
		}
		this_LETTER_R_5=RULE_LETTER_R
		{
			$current.merge(this_LETTER_R_5);
		}
		{
			newLeafNode(this_LETTER_R_5, grammarAccess.getSEMVER_TAGAccess().getLETTER_RTerminalRuleCall_5());
		}
		kw=':'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getSEMVER_TAGAccess().getColonKeyword_6());
		}
	)
;

// Entry rule entryRulePATH
entryRulePATH returns [String current=null]:
	{ newCompositeNode(grammarAccess.getPATHRule()); }
	iv_rulePATH=rulePATH
	{ $current=$iv_rulePATH.current.getText(); }
	EOF;

// Rule PATH
rulePATH returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw='/'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getPATHAccess().getSolidusKeyword_0());
		}
		    |
		kw='.'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getPATHAccess().getFullStopKeyword_1());
		}
		    |
		kw='@'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getPATHAccess().getCommercialAtKeyword_2());
		}
		    |
		kw='-'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getPATHAccess().getHyphenMinusKeyword_3());
		}
		    |
		kw='_'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getPATHAccess().get_Keyword_4());
		}
		    |
		this_DIGITS_5=RULE_DIGITS
		{
			$current.merge(this_DIGITS_5);
		}
		{
			newLeafNode(this_DIGITS_5, grammarAccess.getPATHAccess().getDIGITSTerminalRuleCall_5());
		}
		    |
		{
			newCompositeNode(grammarAccess.getPATHAccess().getLETTERParserRuleCall_6());
		}
		this_LETTER_6=ruleLETTER
		{
			$current.merge(this_LETTER_6);
		}
		{
			afterParserOrEnumRuleCall();
		}
	)+
;

// Entry rule entryRuleURL_PROTOCOL
entryRuleURL_PROTOCOL returns [String current=null]:
	{ newCompositeNode(grammarAccess.getURL_PROTOCOLRule()); }
	iv_ruleURL_PROTOCOL=ruleURL_PROTOCOL
	{ $current=$iv_ruleURL_PROTOCOL.current.getText(); }
	EOF;

// Rule URL_PROTOCOL
ruleURL_PROTOCOL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getURL_PROTOCOLAccess().getLETTER_NO_VXParserRuleCall_0());
		}
		this_LETTER_NO_VX_0=ruleLETTER_NO_VX
		{
			$current.merge(this_LETTER_NO_VX_0);
		}
		{
			afterParserOrEnumRuleCall();
		}
		(
			{
				newCompositeNode(grammarAccess.getURL_PROTOCOLAccess().getLETTERParserRuleCall_1_0());
			}
			this_LETTER_1=ruleLETTER
			{
				$current.merge(this_LETTER_1);
			}
			{
				afterParserOrEnumRuleCall();
			}
			    |
			kw='+'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURL_PROTOCOLAccess().getPlusSignKeyword_1_1());
			}
		)+
	)
;

// Entry rule entryRuleURL
entryRuleURL returns [String current=null]:
	{ newCompositeNode(grammarAccess.getURLRule()); }
	iv_ruleURL=ruleURL
	{ $current=$iv_ruleURL.current.getText(); }
	EOF;

// Rule URL
ruleURL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			kw='-'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURLAccess().getHyphenMinusKeyword_0_0());
			}
			    |
			kw='_'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURLAccess().get_Keyword_0_1());
			}
			    |
			this_DIGITS_2=RULE_DIGITS
			{
				$current.merge(this_DIGITS_2);
			}
			{
				newLeafNode(this_DIGITS_2, grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_0_2());
			}
			    |
			{
				newCompositeNode(grammarAccess.getURLAccess().getLETTERParserRuleCall_0_3());
			}
			this_LETTER_3=ruleLETTER
			{
				$current.merge(this_LETTER_3);
			}
			{
				afterParserOrEnumRuleCall();
			}
		)*
		(
			kw='/'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURLAccess().getSolidusKeyword_1_0());
			}
			    |
			kw='.'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURLAccess().getFullStopKeyword_1_1());
			}
			    |
			kw=':'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURLAccess().getColonKeyword_1_2());
			}
			    |
			kw='@'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURLAccess().getCommercialAtKeyword_1_3());
			}
		)
		(
			kw='/'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURLAccess().getSolidusKeyword_2_0());
			}
			    |
			kw='.'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURLAccess().getFullStopKeyword_2_1());
			}
			    |
			kw=':'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURLAccess().getColonKeyword_2_2());
			}
			    |
			kw='@'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURLAccess().getCommercialAtKeyword_2_3());
			}
			    |
			kw='-'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURLAccess().getHyphenMinusKeyword_2_4());
			}
			    |
			kw='_'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURLAccess().get_Keyword_2_5());
			}
			    |
			this_DIGITS_14=RULE_DIGITS
			{
				$current.merge(this_DIGITS_14);
			}
			{
				newLeafNode(this_DIGITS_14, grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_2_6());
			}
			    |
			{
				newCompositeNode(grammarAccess.getURLAccess().getLETTERParserRuleCall_2_7());
			}
			this_LETTER_15=ruleLETTER
			{
				$current.merge(this_LETTER_15);
			}
			{
				afterParserOrEnumRuleCall();
			}
		)*
	)
;

// Entry rule entryRuleURL_NO_VX
entryRuleURL_NO_VX returns [String current=null]:
	{ newCompositeNode(grammarAccess.getURL_NO_VXRule()); }
	iv_ruleURL_NO_VX=ruleURL_NO_VX
	{ $current=$iv_ruleURL_NO_VX.current.getText(); }
	EOF;

// Rule URL_NO_VX
ruleURL_NO_VX returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			kw='-'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_0_0());
			}
			    |
			kw='_'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().get_Keyword_0_1());
			}
			    |
			{
				newCompositeNode(grammarAccess.getURL_NO_VXAccess().getLETTER_NO_VXParserRuleCall_0_2());
			}
			this_LETTER_NO_VX_2=ruleLETTER_NO_VX
			{
				$current.merge(this_LETTER_NO_VX_2);
			}
			{
				afterParserOrEnumRuleCall();
			}
		)
		(
			kw='-'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_1_0());
			}
			    |
			kw='_'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().get_Keyword_1_1());
			}
			    |
			this_DIGITS_5=RULE_DIGITS
			{
				$current.merge(this_DIGITS_5);
			}
			{
				newLeafNode(this_DIGITS_5, grammarAccess.getURL_NO_VXAccess().getDIGITSTerminalRuleCall_1_2());
			}
			    |
			{
				newCompositeNode(grammarAccess.getURL_NO_VXAccess().getLETTERParserRuleCall_1_3());
			}
			this_LETTER_6=ruleLETTER
			{
				$current.merge(this_LETTER_6);
			}
			{
				afterParserOrEnumRuleCall();
			}
		)*
		(
			kw='/'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getSolidusKeyword_2_0());
			}
			    |
			kw='.'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getFullStopKeyword_2_1());
			}
			    |
			kw=':'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getColonKeyword_2_2());
			}
			    |
			kw='@'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getCommercialAtKeyword_2_3());
			}
		)
		(
			kw='/'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getSolidusKeyword_3_0());
			}
			    |
			kw='.'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getFullStopKeyword_3_1());
			}
			    |
			kw=':'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getColonKeyword_3_2());
			}
			    |
			kw='@'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getCommercialAtKeyword_3_3());
			}
			    |
			kw='-'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_3_4());
			}
			    |
			kw='_'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getURL_NO_VXAccess().get_Keyword_3_5());
			}
			    |
			this_DIGITS_17=RULE_DIGITS
			{
				$current.merge(this_DIGITS_17);
			}
			{
				newLeafNode(this_DIGITS_17, grammarAccess.getURL_NO_VXAccess().getDIGITSTerminalRuleCall_3_6());
			}
			    |
			{
				newCompositeNode(grammarAccess.getURL_NO_VXAccess().getLETTERParserRuleCall_3_7());
			}
			this_LETTER_18=ruleLETTER
			{
				$current.merge(this_LETTER_18);
			}
			{
				afterParserOrEnumRuleCall();
			}
		)*
	)
;

// Entry rule entryRuleTAG
entryRuleTAG returns [String current=null]:
	{ newCompositeNode(grammarAccess.getTAGRule()); }
	iv_ruleTAG=ruleTAG
	{ $current=$iv_ruleTAG.current.getText(); }
	EOF;

// Rule TAG
ruleTAG returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getTAGAccess().getLETTER_NO_VXParserRuleCall_0());
		}
		this_LETTER_NO_VX_0=ruleLETTER_NO_VX
		{
			$current.merge(this_LETTER_NO_VX_0);
		}
		{
			afterParserOrEnumRuleCall();
		}
		(
			kw='-'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getTAGAccess().getHyphenMinusKeyword_1_0());
			}
			    |
			this_DIGITS_2=RULE_DIGITS
			{
				$current.merge(this_DIGITS_2);
			}
			{
				newLeafNode(this_DIGITS_2, grammarAccess.getTAGAccess().getDIGITSTerminalRuleCall_1_1());
			}
			    |
			{
				newCompositeNode(grammarAccess.getTAGAccess().getLETTERParserRuleCall_1_2());
			}
			this_LETTER_3=ruleLETTER
			{
				$current.merge(this_LETTER_3);
			}
			{
				afterParserOrEnumRuleCall();
			}
		)+
	)
;

// Entry rule entryRuleALPHA_NUMERIC_CHARS
entryRuleALPHA_NUMERIC_CHARS returns [String current=null]:
	{ newCompositeNode(grammarAccess.getALPHA_NUMERIC_CHARSRule()); }
	iv_ruleALPHA_NUMERIC_CHARS=ruleALPHA_NUMERIC_CHARS
	{ $current=$iv_ruleALPHA_NUMERIC_CHARS.current.getText(); }
	EOF;

// Rule ALPHA_NUMERIC_CHARS
ruleALPHA_NUMERIC_CHARS returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw='-'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getALPHA_NUMERIC_CHARSAccess().getHyphenMinusKeyword_0());
		}
		    |
		this_DIGITS_1=RULE_DIGITS
		{
			$current.merge(this_DIGITS_1);
		}
		{
			newLeafNode(this_DIGITS_1, grammarAccess.getALPHA_NUMERIC_CHARSAccess().getDIGITSTerminalRuleCall_1());
		}
		    |
		{
			newCompositeNode(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getLETTERParserRuleCall_2());
		}
		this_LETTER_2=ruleLETTER
		{
			$current.merge(this_LETTER_2);
		}
		{
			afterParserOrEnumRuleCall();
		}
	)+
;

// Entry rule entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS
entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS returns [String current=null]:
	{ newCompositeNode(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSRule()); }
	iv_ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS=ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS
	{ $current=$iv_ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS.current.getText(); }
	EOF;

// Rule ALPHA_NUMERIC_CHARS_START_WITH_DIGITS
ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_DIGITS_0=RULE_DIGITS
		{
			$current.merge(this_DIGITS_0);
		}
		{
			newLeafNode(this_DIGITS_0, grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getDIGITSTerminalRuleCall_0());
		}
		(
			kw='-'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getHyphenMinusKeyword_1_0());
			}
			    |
			this_DIGITS_2=RULE_DIGITS
			{
				$current.merge(this_DIGITS_2);
			}
			{
				newLeafNode(this_DIGITS_2, grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getDIGITSTerminalRuleCall_1_1());
			}
			    |
			{
				newCompositeNode(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getLETTERParserRuleCall_1_2());
			}
			this_LETTER_3=ruleLETTER
			{
				$current.merge(this_LETTER_3);
			}
			{
				afterParserOrEnumRuleCall();
			}
		)+
	)
;

// Entry rule entryRuleWILDCARD
entryRuleWILDCARD returns [String current=null]:
	{ newCompositeNode(grammarAccess.getWILDCARDRule()); }
	iv_ruleWILDCARD=ruleWILDCARD
	{ $current=$iv_ruleWILDCARD.current.getText(); }
	EOF;

// Rule WILDCARD
ruleWILDCARD returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_LETTER_X_0=RULE_LETTER_X
		{
			$current.merge(this_LETTER_X_0);
		}
		{
			newLeafNode(this_LETTER_X_0, grammarAccess.getWILDCARDAccess().getLETTER_XTerminalRuleCall_0());
		}
		    |
		this_ASTERIX_1=RULE_ASTERIX
		{
			$current.merge(this_ASTERIX_1);
		}
		{
			newLeafNode(this_ASTERIX_1, grammarAccess.getWILDCARDAccess().getASTERIXTerminalRuleCall_1());
		}
	)
;


// Rule LETTER
ruleLETTER returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_LETTER_V_0=RULE_LETTER_V
		{
			$current.merge(this_LETTER_V_0);
		}
		{
			newLeafNode(this_LETTER_V_0, grammarAccess.getLETTERAccess().getLETTER_VTerminalRuleCall_0());
		}
		    |
		this_LETTER_X_1=RULE_LETTER_X
		{
			$current.merge(this_LETTER_X_1);
		}
		{
			newLeafNode(this_LETTER_X_1, grammarAccess.getLETTERAccess().getLETTER_XTerminalRuleCall_1());
		}
		    |
		{
			newCompositeNode(grammarAccess.getLETTERAccess().getLETTER_NO_VXParserRuleCall_2());
		}
		this_LETTER_NO_VX_2=ruleLETTER_NO_VX
		{
			$current.merge(this_LETTER_NO_VX_2);
		}
		{
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule LETTER_NO_VX
ruleLETTER_NO_VX returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_LETTER_S_0=RULE_LETTER_S
		{
			$current.merge(this_LETTER_S_0);
		}
		{
			newLeafNode(this_LETTER_S_0, grammarAccess.getLETTER_NO_VXAccess().getLETTER_STerminalRuleCall_0());
		}
		    |
		this_LETTER_M_1=RULE_LETTER_M
		{
			$current.merge(this_LETTER_M_1);
		}
		{
			newLeafNode(this_LETTER_M_1, grammarAccess.getLETTER_NO_VXAccess().getLETTER_MTerminalRuleCall_1());
		}
		    |
		this_LETTER_R_2=RULE_LETTER_R
		{
			$current.merge(this_LETTER_R_2);
		}
		{
			newLeafNode(this_LETTER_R_2, grammarAccess.getLETTER_NO_VXAccess().getLETTER_RTerminalRuleCall_2());
		}
		    |
		this_LETTER_F_3=RULE_LETTER_F
		{
			$current.merge(this_LETTER_F_3);
		}
		{
			newLeafNode(this_LETTER_F_3, grammarAccess.getLETTER_NO_VXAccess().getLETTER_FTerminalRuleCall_3());
		}
		    |
		this_LETTER_I_4=RULE_LETTER_I
		{
			$current.merge(this_LETTER_I_4);
		}
		{
			newLeafNode(this_LETTER_I_4, grammarAccess.getLETTER_NO_VXAccess().getLETTER_ITerminalRuleCall_4());
		}
		    |
		this_LETTER_L_5=RULE_LETTER_L
		{
			$current.merge(this_LETTER_L_5);
		}
		{
			newLeafNode(this_LETTER_L_5, grammarAccess.getLETTER_NO_VXAccess().getLETTER_LTerminalRuleCall_5());
		}
		    |
		this_LETTER_E_6=RULE_LETTER_E
		{
			$current.merge(this_LETTER_E_6);
		}
		{
			newLeafNode(this_LETTER_E_6, grammarAccess.getLETTER_NO_VXAccess().getLETTER_ETerminalRuleCall_6());
		}
		    |
		this_LETTER_OTHER_7=RULE_LETTER_OTHER
		{
			$current.merge(this_LETTER_OTHER_7);
		}
		{
			newLeafNode(this_LETTER_OTHER_7, grammarAccess.getLETTER_NO_VXAccess().getLETTER_OTHERTerminalRuleCall_7());
		}
	)
;

// Rule VersionComparator
ruleVersionComparator returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0='='
			{
				$current = grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1='<'
			{
				$current = grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1());
			}
		)
		    |
		(
			enumLiteral_2='~'
			{
				$current = grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_2, grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_2());
			}
		)
		    |
		(
			enumLiteral_3='^'
			{
				$current = grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_3, grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_3());
			}
		)
		    |
		(
			enumLiteral_4='<='
			{
				$current = grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_4, grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_4());
			}
		)
		    |
		(
			enumLiteral_5='>'
			{
				$current = grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_5, grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_5());
			}
		)
		    |
		(
			enumLiteral_6='>='
			{
				$current = grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_6, grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_6());
			}
		)
	)
;

RULE_LETTER_S : ('s'|'S');

RULE_LETTER_M : ('m'|'M');

RULE_LETTER_R : ('r'|'R');

RULE_LETTER_F : ('f'|'F');

RULE_LETTER_I : ('i'|'I');

RULE_LETTER_L : ('l'|'L');

RULE_LETTER_E : ('e'|'E');

RULE_LETTER_V : ('v'|'V');

RULE_LETTER_X : ('x'|'X');

RULE_LETTER_OTHER : ('a'|'A'|'b'|'B'|'c'|'C'|'d'|'D'|'g'|'G'|'h'|'H'|'j'|'J'|'k'|'K'|'n'|'N'|'o'|'O'|'p'|'P'|'q'|'Q'|'t'|'T'|'u'|'U'|'w'|'W'|'y'|'Y'|'z'|'Z');

RULE_ASTERIX : '*';

RULE_DIGITS : ('0'..'9')+;

RULE_WS : RULE_WHITESPACE_FRAGMENT+;

RULE_EOL : RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT;

fragment RULE_HEX_DIGIT : (RULE_DECIMAL_DIGIT_FRAGMENT|'a'..'f'|'A'..'F');

fragment RULE_DECIMAL_INTEGER_LITERAL_FRAGMENT : ('0'|'1'..'9' RULE_DECIMAL_DIGIT_FRAGMENT*);

fragment RULE_DECIMAL_DIGIT_FRAGMENT : '0'..'9';

fragment RULE_ZWJ : '\u200D';

fragment RULE_ZWNJ : '\u200C';

fragment RULE_BOM : '\uFEFF';

fragment RULE_WHITESPACE_FRAGMENT : ('\t'|'\u000B'|'\f'|' '|'\u00A0'|RULE_BOM|RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT);

fragment RULE_LINE_TERMINATOR_FRAGMENT : ('\n'|'\r'|'\u2028'|'\u2029');

fragment RULE_LINE_TERMINATOR_SEQUENCE_FRAGMENT : ('\n'|'\r' '\n'?|'\u2028'|'\u2029');

fragment RULE_SL_COMMENT_FRAGMENT : '//' ~(RULE_LINE_TERMINATOR_FRAGMENT)*;

fragment RULE_ML_COMMENT_FRAGMENT : '/*' ( options {greedy=false;} : . )*'*/';

fragment RULE_UNICODE_COMBINING_MARK_FRAGMENT : ('\u0300'..'\u036F'|'\u0483'..'\u0487'|'\u0591'..'\u05BD'|'\u05BF'|'\u05C1'..'\u05C2'|'\u05C4'..'\u05C5'|'\u05C7'|'\u0610'..'\u061A'|'\u064B'..'\u065F'|'\u0670'|'\u06D6'..'\u06DC'|'\u06DF'..'\u06E4'|'\u06E7'..'\u06E8'|'\u06EA'..'\u06ED'|'\u0711'|'\u0730'..'\u074A'|'\u07A6'..'\u07B0'|'\u07EB'..'\u07F3'|'\u0816'..'\u0819'|'\u081B'..'\u0823'|'\u0825'..'\u0827'|'\u0829'..'\u082D'|'\u0859'..'\u085B'|'\u08E3'..'\u0903'|'\u093A'..'\u093C'|'\u093E'..'\u094F'|'\u0951'..'\u0957'|'\u0962'..'\u0963'|'\u0981'..'\u0983'|'\u09BC'|'\u09BE'..'\u09C4'|'\u09C7'..'\u09C8'|'\u09CB'..'\u09CD'|'\u09D7'|'\u09E2'..'\u09E3'|'\u0A01'..'\u0A03'|'\u0A3C'|'\u0A3E'..'\u0A42'|'\u0A47'..'\u0A48'|'\u0A4B'..'\u0A4D'|'\u0A51'|'\u0A70'..'\u0A71'|'\u0A75'|'\u0A81'..'\u0A83'|'\u0ABC'|'\u0ABE'..'\u0AC5'|'\u0AC7'..'\u0AC9'|'\u0ACB'..'\u0ACD'|'\u0AE2'..'\u0AE3'|'\u0B01'..'\u0B03'|'\u0B3C'|'\u0B3E'..'\u0B44'|'\u0B47'..'\u0B48'|'\u0B4B'..'\u0B4D'|'\u0B56'..'\u0B57'|'\u0B62'..'\u0B63'|'\u0B82'|'\u0BBE'..'\u0BC2'|'\u0BC6'..'\u0BC8'|'\u0BCA'..'\u0BCD'|'\u0BD7'|'\u0C00'..'\u0C03'|'\u0C3E'..'\u0C44'|'\u0C46'..'\u0C48'|'\u0C4A'..'\u0C4D'|'\u0C55'..'\u0C56'|'\u0C62'..'\u0C63'|'\u0C81'..'\u0C83'|'\u0CBC'|'\u0CBE'..'\u0CC4'|'\u0CC6'..'\u0CC8'|'\u0CCA'..'\u0CCD'|'\u0CD5'..'\u0CD6'|'\u0CE2'..'\u0CE3'|'\u0D01'..'\u0D03'|'\u0D3E'..'\u0D44'|'\u0D46'..'\u0D48'|'\u0D4A'..'\u0D4D'|'\u0D57'|'\u0D62'..'\u0D63'|'\u0D82'..'\u0D83'|'\u0DCA'|'\u0DCF'..'\u0DD4'|'\u0DD6'|'\u0DD8'..'\u0DDF'|'\u0DF2'..'\u0DF3'|'\u0E31'|'\u0E34'..'\u0E3A'|'\u0E47'..'\u0E4E'|'\u0EB1'|'\u0EB4'..'\u0EB9'|'\u0EBB'..'\u0EBC'|'\u0EC8'..'\u0ECD'|'\u0F18'..'\u0F19'|'\u0F35'|'\u0F37'|'\u0F39'|'\u0F3E'..'\u0F3F'|'\u0F71'..'\u0F84'|'\u0F86'..'\u0F87'|'\u0F8D'..'\u0F97'|'\u0F99'..'\u0FBC'|'\u0FC6'|'\u102B'..'\u103E'|'\u1056'..'\u1059'|'\u105E'..'\u1060'|'\u1062'..'\u1064'|'\u1067'..'\u106D'|'\u1071'..'\u1074'|'\u1082'..'\u108D'|'\u108F'|'\u109A'..'\u109D'|'\u135D'..'\u135F'|'\u1712'..'\u1714'|'\u1732'..'\u1734'|'\u1752'..'\u1753'|'\u1772'..'\u1773'|'\u17B4'..'\u17D3'|'\u17DD'|'\u180B'..'\u180D'|'\u18A9'|'\u1920'..'\u192B'|'\u1930'..'\u193B'|'\u1A17'..'\u1A1B'|'\u1A55'..'\u1A5E'|'\u1A60'..'\u1A7C'|'\u1A7F'|'\u1AB0'..'\u1ABD'|'\u1B00'..'\u1B04'|'\u1B34'..'\u1B44'|'\u1B6B'..'\u1B73'|'\u1B80'..'\u1B82'|'\u1BA1'..'\u1BAD'|'\u1BE6'..'\u1BF3'|'\u1C24'..'\u1C37'|'\u1CD0'..'\u1CD2'|'\u1CD4'..'\u1CE8'|'\u1CED'|'\u1CF2'..'\u1CF4'|'\u1CF8'..'\u1CF9'|'\u1DC0'..'\u1DF5'|'\u1DFC'..'\u1DFF'|'\u20D0'..'\u20DC'|'\u20E1'|'\u20E5'..'\u20F0'|'\u2CEF'..'\u2CF1'|'\u2D7F'|'\u2DE0'..'\u2DFF'|'\u302A'..'\u302F'|'\u3099'..'\u309A'|'\uA66F'|'\uA674'..'\uA67D'|'\uA69E'..'\uA69F'|'\uA6F0'..'\uA6F1'|'\uA802'|'\uA806'|'\uA80B'|'\uA823'..'\uA827'|'\uA880'..'\uA881'|'\uA8B4'..'\uA8C4'|'\uA8E0'..'\uA8F1'|'\uA926'..'\uA92D'|'\uA947'..'\uA953'|'\uA980'..'\uA983'|'\uA9B3'..'\uA9C0'|'\uA9E5'|'\uAA29'..'\uAA36'|'\uAA43'|'\uAA4C'..'\uAA4D'|'\uAA7B'..'\uAA7D'|'\uAAB0'|'\uAAB2'..'\uAAB4'|'\uAAB7'..'\uAAB8'|'\uAABE'..'\uAABF'|'\uAAC1'|'\uAAEB'..'\uAAEF'|'\uAAF5'..'\uAAF6'|'\uABE3'..'\uABEA'|'\uABEC'..'\uABED'|'\uFB1E'|'\uFE00'..'\uFE0F'|'\uFE20'..'\uFE2F');

fragment RULE_UNICODE_DIGIT_FRAGMENT : ('0'..'9'|'\u0660'..'\u0669'|'\u06F0'..'\u06F9'|'\u07C0'..'\u07C9'|'\u0966'..'\u096F'|'\u09E6'..'\u09EF'|'\u0A66'..'\u0A6F'|'\u0AE6'..'\u0AEF'|'\u0B66'..'\u0B6F'|'\u0BE6'..'\u0BEF'|'\u0C66'..'\u0C6F'|'\u0CE6'..'\u0CEF'|'\u0D66'..'\u0D6F'|'\u0DE6'..'\u0DEF'|'\u0E50'..'\u0E59'|'\u0ED0'..'\u0ED9'|'\u0F20'..'\u0F29'|'\u1040'..'\u1049'|'\u1090'..'\u1099'|'\u17E0'..'\u17E9'|'\u1810'..'\u1819'|'\u1946'..'\u194F'|'\u19D0'..'\u19D9'|'\u1A80'..'\u1A89'|'\u1A90'..'\u1A99'|'\u1B50'..'\u1B59'|'\u1BB0'..'\u1BB9'|'\u1C40'..'\u1C49'|'\u1C50'..'\u1C59'|'\uA620'..'\uA629'|'\uA8D0'..'\uA8D9'|'\uA900'..'\uA909'|'\uA9D0'..'\uA9D9'|'\uA9F0'..'\uA9F9'|'\uAA50'..'\uAA59'|'\uABF0'..'\uABF9'|'\uFF10'..'\uFF19');

fragment RULE_UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT : ('_'|'\u203F'..'\u2040'|'\u2054'|'\uFE33'..'\uFE34'|'\uFE4D'..'\uFE4F'|'\uFF3F');

fragment RULE_UNICODE_LETTER_FRAGMENT : ('A'..'Z'|'a'..'z'|'\u00AA'|'\u00B5'|'\u00BA'|'\u00C0'..'\u00D6'|'\u00D8'..'\u00F6'|'\u00F8'..'\u02C1'|'\u02C6'..'\u02D1'|'\u02E0'..'\u02E4'|'\u02EC'|'\u02EE'|'\u0370'..'\u0374'|'\u0376'..'\u0377'|'\u037A'..'\u037D'|'\u037F'|'\u0386'|'\u0388'..'\u038A'|'\u038C'|'\u038E'..'\u03A1'|'\u03A3'..'\u03F5'|'\u03F7'..'\u0481'|'\u048A'..'\u052F'|'\u0531'..'\u0556'|'\u0559'|'\u0561'..'\u0587'|'\u05D0'..'\u05EA'|'\u05F0'..'\u05F2'|'\u0620'..'\u064A'|'\u066E'..'\u066F'|'\u0671'..'\u06D3'|'\u06D5'|'\u06E5'..'\u06E6'|'\u06EE'..'\u06EF'|'\u06FA'..'\u06FC'|'\u06FF'|'\u0710'|'\u0712'..'\u072F'|'\u074D'..'\u07A5'|'\u07B1'|'\u07CA'..'\u07EA'|'\u07F4'..'\u07F5'|'\u07FA'|'\u0800'..'\u0815'|'\u081A'|'\u0824'|'\u0828'|'\u0840'..'\u0858'|'\u08A0'..'\u08B4'|'\u0904'..'\u0939'|'\u093D'|'\u0950'|'\u0958'..'\u0961'|'\u0971'..'\u0980'|'\u0985'..'\u098C'|'\u098F'..'\u0990'|'\u0993'..'\u09A8'|'\u09AA'..'\u09B0'|'\u09B2'|'\u09B6'..'\u09B9'|'\u09BD'|'\u09CE'|'\u09DC'..'\u09DD'|'\u09DF'..'\u09E1'|'\u09F0'..'\u09F1'|'\u0A05'..'\u0A0A'|'\u0A0F'..'\u0A10'|'\u0A13'..'\u0A28'|'\u0A2A'..'\u0A30'|'\u0A32'..'\u0A33'|'\u0A35'..'\u0A36'|'\u0A38'..'\u0A39'|'\u0A59'..'\u0A5C'|'\u0A5E'|'\u0A72'..'\u0A74'|'\u0A85'..'\u0A8D'|'\u0A8F'..'\u0A91'|'\u0A93'..'\u0AA8'|'\u0AAA'..'\u0AB0'|'\u0AB2'..'\u0AB3'|'\u0AB5'..'\u0AB9'|'\u0ABD'|'\u0AD0'|'\u0AE0'..'\u0AE1'|'\u0AF9'|'\u0B05'..'\u0B0C'|'\u0B0F'..'\u0B10'|'\u0B13'..'\u0B28'|'\u0B2A'..'\u0B30'|'\u0B32'..'\u0B33'|'\u0B35'..'\u0B39'|'\u0B3D'|'\u0B5C'..'\u0B5D'|'\u0B5F'..'\u0B61'|'\u0B71'|'\u0B83'|'\u0B85'..'\u0B8A'|'\u0B8E'..'\u0B90'|'\u0B92'..'\u0B95'|'\u0B99'..'\u0B9A'|'\u0B9C'|'\u0B9E'..'\u0B9F'|'\u0BA3'..'\u0BA4'|'\u0BA8'..'\u0BAA'|'\u0BAE'..'\u0BB9'|'\u0BD0'|'\u0C05'..'\u0C0C'|'\u0C0E'..'\u0C10'|'\u0C12'..'\u0C28'|'\u0C2A'..'\u0C39'|'\u0C3D'|'\u0C58'..'\u0C5A'|'\u0C60'..'\u0C61'|'\u0C85'..'\u0C8C'|'\u0C8E'..'\u0C90'|'\u0C92'..'\u0CA8'|'\u0CAA'..'\u0CB3'|'\u0CB5'..'\u0CB9'|'\u0CBD'|'\u0CDE'|'\u0CE0'..'\u0CE1'|'\u0CF1'..'\u0CF2'|'\u0D05'..'\u0D0C'|'\u0D0E'..'\u0D10'|'\u0D12'..'\u0D3A'|'\u0D3D'|'\u0D4E'|'\u0D5F'..'\u0D61'|'\u0D7A'..'\u0D7F'|'\u0D85'..'\u0D96'|'\u0D9A'..'\u0DB1'|'\u0DB3'..'\u0DBB'|'\u0DBD'|'\u0DC0'..'\u0DC6'|'\u0E01'..'\u0E30'|'\u0E32'..'\u0E33'|'\u0E40'..'\u0E46'|'\u0E81'..'\u0E82'|'\u0E84'|'\u0E87'..'\u0E88'|'\u0E8A'|'\u0E8D'|'\u0E94'..'\u0E97'|'\u0E99'..'\u0E9F'|'\u0EA1'..'\u0EA3'|'\u0EA5'|'\u0EA7'|'\u0EAA'..'\u0EAB'|'\u0EAD'..'\u0EB0'|'\u0EB2'..'\u0EB3'|'\u0EBD'|'\u0EC0'..'\u0EC4'|'\u0EC6'|'\u0EDC'..'\u0EDF'|'\u0F00'|'\u0F40'..'\u0F47'|'\u0F49'..'\u0F6C'|'\u0F88'..'\u0F8C'|'\u1000'..'\u102A'|'\u103F'|'\u1050'..'\u1055'|'\u105A'..'\u105D'|'\u1061'|'\u1065'..'\u1066'|'\u106E'..'\u1070'|'\u1075'..'\u1081'|'\u108E'|'\u10A0'..'\u10C5'|'\u10C7'|'\u10CD'|'\u10D0'..'\u10FA'|'\u10FC'..'\u1248'|'\u124A'..'\u124D'|'\u1250'..'\u1256'|'\u1258'|'\u125A'..'\u125D'|'\u1260'..'\u1288'|'\u128A'..'\u128D'|'\u1290'..'\u12B0'|'\u12B2'..'\u12B5'|'\u12B8'..'\u12BE'|'\u12C0'|'\u12C2'..'\u12C5'|'\u12C8'..'\u12D6'|'\u12D8'..'\u1310'|'\u1312'..'\u1315'|'\u1318'..'\u135A'|'\u1380'..'\u138F'|'\u13A0'..'\u13F5'|'\u13F8'..'\u13FD'|'\u1401'..'\u166C'|'\u166F'..'\u167F'|'\u1681'..'\u169A'|'\u16A0'..'\u16EA'|'\u16EE'..'\u16F8'|'\u1700'..'\u170C'|'\u170E'..'\u1711'|'\u1720'..'\u1731'|'\u1740'..'\u1751'|'\u1760'..'\u176C'|'\u176E'..'\u1770'|'\u1780'..'\u17B3'|'\u17D7'|'\u17DC'|'\u1820'..'\u1877'|'\u1880'..'\u18A8'|'\u18AA'|'\u18B0'..'\u18F5'|'\u1900'..'\u191E'|'\u1950'..'\u196D'|'\u1970'..'\u1974'|'\u1980'..'\u19AB'|'\u19B0'..'\u19C9'|'\u1A00'..'\u1A16'|'\u1A20'..'\u1A54'|'\u1AA7'|'\u1B05'..'\u1B33'|'\u1B45'..'\u1B4B'|'\u1B83'..'\u1BA0'|'\u1BAE'..'\u1BAF'|'\u1BBA'..'\u1BE5'|'\u1C00'..'\u1C23'|'\u1C4D'..'\u1C4F'|'\u1C5A'..'\u1C7D'|'\u1CE9'..'\u1CEC'|'\u1CEE'..'\u1CF1'|'\u1CF5'..'\u1CF6'|'\u1D00'..'\u1DBF'|'\u1E00'..'\u1F15'|'\u1F18'..'\u1F1D'|'\u1F20'..'\u1F45'|'\u1F48'..'\u1F4D'|'\u1F50'..'\u1F57'|'\u1F59'|'\u1F5B'|'\u1F5D'|'\u1F5F'..'\u1F7D'|'\u1F80'..'\u1FB4'|'\u1FB6'..'\u1FBC'|'\u1FBE'|'\u1FC2'..'\u1FC4'|'\u1FC6'..'\u1FCC'|'\u1FD0'..'\u1FD3'|'\u1FD6'..'\u1FDB'|'\u1FE0'..'\u1FEC'|'\u1FF2'..'\u1FF4'|'\u1FF6'..'\u1FFC'|'\u2071'|'\u207F'|'\u2090'..'\u209C'|'\u2102'|'\u2107'|'\u210A'..'\u2113'|'\u2115'|'\u2119'..'\u211D'|'\u2124'|'\u2126'|'\u2128'|'\u212A'..'\u212D'|'\u212F'..'\u2139'|'\u213C'..'\u213F'|'\u2145'..'\u2149'|'\u214E'|'\u2160'..'\u2188'|'\u2C00'..'\u2C2E'|'\u2C30'..'\u2C5E'|'\u2C60'..'\u2CE4'|'\u2CEB'..'\u2CEE'|'\u2CF2'..'\u2CF3'|'\u2D00'..'\u2D25'|'\u2D27'|'\u2D2D'|'\u2D30'..'\u2D67'|'\u2D6F'|'\u2D80'..'\u2D96'|'\u2DA0'..'\u2DA6'|'\u2DA8'..'\u2DAE'|'\u2DB0'..'\u2DB6'|'\u2DB8'..'\u2DBE'|'\u2DC0'..'\u2DC6'|'\u2DC8'..'\u2DCE'|'\u2DD0'..'\u2DD6'|'\u2DD8'..'\u2DDE'|'\u2E2F'|'\u3005'..'\u3007'|'\u3021'..'\u3029'|'\u3031'..'\u3035'|'\u3038'..'\u303C'|'\u3041'..'\u3096'|'\u309D'..'\u309F'|'\u30A1'..'\u30FA'|'\u30FC'..'\u30FF'|'\u3105'..'\u312D'|'\u3131'..'\u318E'|'\u31A0'..'\u31BA'|'\u31F0'..'\u31FF'|'\u3400'..'\u4DB5'|'\u4E00'..'\u9FD5'|'\uA000'..'\uA48C'|'\uA4D0'..'\uA4FD'|'\uA500'..'\uA60C'|'\uA610'..'\uA61F'|'\uA62A'..'\uA62B'|'\uA640'..'\uA66E'|'\uA67F'..'\uA69D'|'\uA6A0'..'\uA6EF'|'\uA717'..'\uA71F'|'\uA722'..'\uA788'|'\uA78B'..'\uA7AD'|'\uA7B0'..'\uA7B7'|'\uA7F7'..'\uA801'|'\uA803'..'\uA805'|'\uA807'..'\uA80A'|'\uA80C'..'\uA822'|'\uA840'..'\uA873'|'\uA882'..'\uA8B3'|'\uA8F2'..'\uA8F7'|'\uA8FB'|'\uA8FD'|'\uA90A'..'\uA925'|'\uA930'..'\uA946'|'\uA960'..'\uA97C'|'\uA984'..'\uA9B2'|'\uA9CF'|'\uA9E0'..'\uA9E4'|'\uA9E6'..'\uA9EF'|'\uA9FA'..'\uA9FE'|'\uAA00'..'\uAA28'|'\uAA40'..'\uAA42'|'\uAA44'..'\uAA4B'|'\uAA60'..'\uAA76'|'\uAA7A'|'\uAA7E'..'\uAAAF'|'\uAAB1'|'\uAAB5'..'\uAAB6'|'\uAAB9'..'\uAABD'|'\uAAC0'|'\uAAC2'|'\uAADB'..'\uAADD'|'\uAAE0'..'\uAAEA'|'\uAAF2'..'\uAAF4'|'\uAB01'..'\uAB06'|'\uAB09'..'\uAB0E'|'\uAB11'..'\uAB16'|'\uAB20'..'\uAB26'|'\uAB28'..'\uAB2E'|'\uAB30'..'\uAB5A'|'\uAB5C'..'\uAB65'|'\uAB70'..'\uABE2'|'\uAC00'..'\uD7A3'|'\uD7B0'..'\uD7C6'|'\uD7CB'..'\uD7FB'|'\uF900'..'\uFA6D'|'\uFA70'..'\uFAD9'|'\uFB00'..'\uFB06'|'\uFB13'..'\uFB17'|'\uFB1D'|'\uFB1F'..'\uFB28'|'\uFB2A'..'\uFB36'|'\uFB38'..'\uFB3C'|'\uFB3E'|'\uFB40'..'\uFB41'|'\uFB43'..'\uFB44'|'\uFB46'..'\uFBB1'|'\uFBD3'..'\uFD3D'|'\uFD50'..'\uFD8F'|'\uFD92'..'\uFDC7'|'\uFDF0'..'\uFDFB'|'\uFE70'..'\uFE74'|'\uFE76'..'\uFEFC'|'\uFF21'..'\uFF3A'|'\uFF41'..'\uFF5A'|'\uFF66'..'\uFFBE'|'\uFFC2'..'\uFFC7'|'\uFFCA'..'\uFFCF'|'\uFFD2'..'\uFFD7'|'\uFFDA'..'\uFFDC');

fragment RULE_UNICODE_SPACE_SEPARATOR_FRAGMENT : (' '|'\u00A0'|'\u1680'|'\u2000'..'\u200A'|'\u202F'|'\u205F'|'\u3000');

fragment RULE_ANY_OTHER : .;
