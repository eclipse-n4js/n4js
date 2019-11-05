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
	superClass=AbstractInternalContentAssistParser;
	backtrack=true;
}

@lexer::header {
package org.eclipse.n4js.semver.ide.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;
}

@parser::header {
package org.eclipse.n4js.semver.ide.contentassist.antlr.internal;

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
import org.eclipse.n4js.semver.services.SemverGrammarAccess;

}
@parser::members {
	private SemverGrammarAccess grammarAccess;

	public void setGrammarAccess(SemverGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}

	@Override
	protected Grammar getGrammar() {
		return grammarAccess.getGrammar();
	}

	@Override
	protected String getValueForTokenName(String tokenName) {
		return tokenName;
	}
}

// Entry rule entryRuleNPMVersionRequirement
entryRuleNPMVersionRequirement
:
{ before(grammarAccess.getNPMVersionRequirementRule()); }
	 ruleNPMVersionRequirement
{ after(grammarAccess.getNPMVersionRequirementRule()); } 
	 EOF 
;

// Rule NPMVersionRequirement
ruleNPMVersionRequirement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getNPMVersionRequirementAccess().getAlternatives()); }
		(rule__NPMVersionRequirement__Alternatives)
		{ after(grammarAccess.getNPMVersionRequirementAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleLocalPathVersionRequirement
entryRuleLocalPathVersionRequirement
:
{ before(grammarAccess.getLocalPathVersionRequirementRule()); }
	 ruleLocalPathVersionRequirement
{ after(grammarAccess.getLocalPathVersionRequirementRule()); } 
	 EOF 
;

// Rule LocalPathVersionRequirement
ruleLocalPathVersionRequirement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getLocalPathVersionRequirementAccess().getGroup()); }
		(rule__LocalPathVersionRequirement__Group__0)
		{ after(grammarAccess.getLocalPathVersionRequirementAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleURLVersionRequirement
entryRuleURLVersionRequirement
:
{ before(grammarAccess.getURLVersionRequirementRule()); }
	 ruleURLVersionRequirement
{ after(grammarAccess.getURLVersionRequirementRule()); } 
	 EOF 
;

// Rule URLVersionRequirement
ruleURLVersionRequirement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getURLVersionRequirementAccess().getGroup()); }
		(rule__URLVersionRequirement__Group__0)
		{ after(grammarAccess.getURLVersionRequirementAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleURLVersionSpecifier
entryRuleURLVersionSpecifier
:
{ before(grammarAccess.getURLVersionSpecifierRule()); }
	 ruleURLVersionSpecifier
{ after(grammarAccess.getURLVersionSpecifierRule()); } 
	 EOF 
;

// Rule URLVersionSpecifier
ruleURLVersionSpecifier 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getURLVersionSpecifierAccess().getAlternatives()); }
		(rule__URLVersionSpecifier__Alternatives)
		{ after(grammarAccess.getURLVersionSpecifierAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleURLSemver
entryRuleURLSemver
:
{ before(grammarAccess.getURLSemverRule()); }
	 ruleURLSemver
{ after(grammarAccess.getURLSemverRule()); } 
	 EOF 
;

// Rule URLSemver
ruleURLSemver 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getURLSemverAccess().getGroup()); }
		(rule__URLSemver__Group__0)
		{ after(grammarAccess.getURLSemverAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTagVersionRequirement
entryRuleTagVersionRequirement
:
{ before(grammarAccess.getTagVersionRequirementRule()); }
	 ruleTagVersionRequirement
{ after(grammarAccess.getTagVersionRequirementRule()); } 
	 EOF 
;

// Rule TagVersionRequirement
ruleTagVersionRequirement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTagVersionRequirementAccess().getTagNameAssignment()); }
		(rule__TagVersionRequirement__TagNameAssignment)
		{ after(grammarAccess.getTagVersionRequirementAccess().getTagNameAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleGitHubVersionRequirement
entryRuleGitHubVersionRequirement
:
{ before(grammarAccess.getGitHubVersionRequirementRule()); }
	 ruleGitHubVersionRequirement
{ after(grammarAccess.getGitHubVersionRequirementRule()); } 
	 EOF 
;

// Rule GitHubVersionRequirement
ruleGitHubVersionRequirement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getGitHubVersionRequirementAccess().getGroup()); }
		(rule__GitHubVersionRequirement__Group__0)
		{ after(grammarAccess.getGitHubVersionRequirementAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleVersionRangeSetRequirement
entryRuleVersionRangeSetRequirement
:
{ before(grammarAccess.getVersionRangeSetRequirementRule()); }
	 ruleVersionRangeSetRequirement
{ after(grammarAccess.getVersionRangeSetRequirementRule()); } 
	 EOF 
;

// Rule VersionRangeSetRequirement
ruleVersionRangeSetRequirement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getVersionRangeSetRequirementAccess().getGroup()); }
		(rule__VersionRangeSetRequirement__Group__0)
		{ after(grammarAccess.getVersionRangeSetRequirementAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleVersionRange
entryRuleVersionRange
:
{ before(grammarAccess.getVersionRangeRule()); }
	 ruleVersionRange
{ after(grammarAccess.getVersionRangeRule()); } 
	 EOF 
;

// Rule VersionRange
ruleVersionRange 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getVersionRangeAccess().getAlternatives()); }
		(rule__VersionRange__Alternatives)
		{ after(grammarAccess.getVersionRangeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleHyphenVersionRange
entryRuleHyphenVersionRange
:
{ before(grammarAccess.getHyphenVersionRangeRule()); }
	 ruleHyphenVersionRange
{ after(grammarAccess.getHyphenVersionRangeRule()); } 
	 EOF 
;

// Rule HyphenVersionRange
ruleHyphenVersionRange 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getHyphenVersionRangeAccess().getGroup()); }
		(rule__HyphenVersionRange__Group__0)
		{ after(grammarAccess.getHyphenVersionRangeAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleVersionRangeContraint
entryRuleVersionRangeContraint
:
{ before(grammarAccess.getVersionRangeContraintRule()); }
	 ruleVersionRangeContraint
{ after(grammarAccess.getVersionRangeContraintRule()); } 
	 EOF 
;

// Rule VersionRangeContraint
ruleVersionRangeContraint 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getVersionRangeContraintAccess().getGroup()); }
		(rule__VersionRangeContraint__Group__0)
		{ after(grammarAccess.getVersionRangeContraintAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSimpleVersion
entryRuleSimpleVersion
:
{ before(grammarAccess.getSimpleVersionRule()); }
	 ruleSimpleVersion
{ after(grammarAccess.getSimpleVersionRule()); } 
	 EOF 
;

// Rule SimpleVersion
ruleSimpleVersion 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSimpleVersionAccess().getGroup()); }
		(rule__SimpleVersion__Group__0)
		{ after(grammarAccess.getSimpleVersionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleVersionNumber
entryRuleVersionNumber
:
{ before(grammarAccess.getVersionNumberRule()); }
	 ruleVersionNumber
{ after(grammarAccess.getVersionNumberRule()); } 
	 EOF 
;

// Rule VersionNumber
ruleVersionNumber 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getVersionNumberAccess().getGroup()); }
		(rule__VersionNumber__Group__0)
		{ after(grammarAccess.getVersionNumberAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleVersionPart
entryRuleVersionPart
:
{ before(grammarAccess.getVersionPartRule()); }
	 ruleVersionPart
{ after(grammarAccess.getVersionPartRule()); } 
	 EOF 
;

// Rule VersionPart
ruleVersionPart 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getVersionPartAccess().getAlternatives()); }
		(rule__VersionPart__Alternatives)
		{ after(grammarAccess.getVersionPartAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleQualifier
entryRuleQualifier
:
{ before(grammarAccess.getQualifierRule()); }
	 ruleQualifier
{ after(grammarAccess.getQualifierRule()); } 
	 EOF 
;

// Rule Qualifier
ruleQualifier 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getQualifierAccess().getAlternatives()); }
		(rule__Qualifier__Alternatives)
		{ after(grammarAccess.getQualifierAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleQualifierTag
entryRuleQualifierTag
:
{ before(grammarAccess.getQualifierTagRule()); }
	 ruleQualifierTag
{ after(grammarAccess.getQualifierTagRule()); } 
	 EOF 
;

// Rule QualifierTag
ruleQualifierTag 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getQualifierTagAccess().getGroup()); }
		(rule__QualifierTag__Group__0)
		{ after(grammarAccess.getQualifierTagAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleFILE_TAG
entryRuleFILE_TAG
:
{ before(grammarAccess.getFILE_TAGRule()); }
	 ruleFILE_TAG
{ after(grammarAccess.getFILE_TAGRule()); } 
	 EOF 
;

// Rule FILE_TAG
ruleFILE_TAG 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getFILE_TAGAccess().getGroup()); }
		(rule__FILE_TAG__Group__0)
		{ after(grammarAccess.getFILE_TAGAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSEMVER_TAG
entryRuleSEMVER_TAG
:
{ before(grammarAccess.getSEMVER_TAGRule()); }
	 ruleSEMVER_TAG
{ after(grammarAccess.getSEMVER_TAGRule()); } 
	 EOF 
;

// Rule SEMVER_TAG
ruleSEMVER_TAG 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSEMVER_TAGAccess().getGroup()); }
		(rule__SEMVER_TAG__Group__0)
		{ after(grammarAccess.getSEMVER_TAGAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRulePATH
entryRulePATH
:
{ before(grammarAccess.getPATHRule()); }
	 rulePATH
{ after(grammarAccess.getPATHRule()); } 
	 EOF 
;

// Rule PATH
rulePATH 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		(
			{ before(grammarAccess.getPATHAccess().getAlternatives()); }
			(rule__PATH__Alternatives)
			{ after(grammarAccess.getPATHAccess().getAlternatives()); }
		)
		(
			{ before(grammarAccess.getPATHAccess().getAlternatives()); }
			(rule__PATH__Alternatives)*
			{ after(grammarAccess.getPATHAccess().getAlternatives()); }
		)
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleURL_PROTOCOL
entryRuleURL_PROTOCOL
:
{ before(grammarAccess.getURL_PROTOCOLRule()); }
	 ruleURL_PROTOCOL
{ after(grammarAccess.getURL_PROTOCOLRule()); } 
	 EOF 
;

// Rule URL_PROTOCOL
ruleURL_PROTOCOL 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getURL_PROTOCOLAccess().getGroup()); }
		(rule__URL_PROTOCOL__Group__0)
		{ after(grammarAccess.getURL_PROTOCOLAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleURL
entryRuleURL
:
{ before(grammarAccess.getURLRule()); }
	 ruleURL
{ after(grammarAccess.getURLRule()); } 
	 EOF 
;

// Rule URL
ruleURL 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getURLAccess().getGroup()); }
		(rule__URL__Group__0)
		{ after(grammarAccess.getURLAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleURL_NO_VX
entryRuleURL_NO_VX
:
{ before(grammarAccess.getURL_NO_VXRule()); }
	 ruleURL_NO_VX
{ after(grammarAccess.getURL_NO_VXRule()); } 
	 EOF 
;

// Rule URL_NO_VX
ruleURL_NO_VX 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getURL_NO_VXAccess().getGroup()); }
		(rule__URL_NO_VX__Group__0)
		{ after(grammarAccess.getURL_NO_VXAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTAG
entryRuleTAG
:
{ before(grammarAccess.getTAGRule()); }
	 ruleTAG
{ after(grammarAccess.getTAGRule()); } 
	 EOF 
;

// Rule TAG
ruleTAG 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTAGAccess().getGroup()); }
		(rule__TAG__Group__0)
		{ after(grammarAccess.getTAGAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleALPHA_NUMERIC_CHARS
entryRuleALPHA_NUMERIC_CHARS
:
{ before(grammarAccess.getALPHA_NUMERIC_CHARSRule()); }
	 ruleALPHA_NUMERIC_CHARS
{ after(grammarAccess.getALPHA_NUMERIC_CHARSRule()); } 
	 EOF 
;

// Rule ALPHA_NUMERIC_CHARS
ruleALPHA_NUMERIC_CHARS 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		(
			{ before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getAlternatives()); }
			(rule__ALPHA_NUMERIC_CHARS__Alternatives)
			{ after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getAlternatives()); }
		)
		(
			{ before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getAlternatives()); }
			(rule__ALPHA_NUMERIC_CHARS__Alternatives)*
			{ after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getAlternatives()); }
		)
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS
entryRuleALPHA_NUMERIC_CHARS_START_WITH_DIGITS
:
{ before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSRule()); }
	 ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS
{ after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSRule()); } 
	 EOF 
;

// Rule ALPHA_NUMERIC_CHARS_START_WITH_DIGITS
ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getGroup()); }
		(rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0)
		{ after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleWILDCARD
entryRuleWILDCARD
:
{ before(grammarAccess.getWILDCARDRule()); }
	 ruleWILDCARD
{ after(grammarAccess.getWILDCARDRule()); } 
	 EOF 
;

// Rule WILDCARD
ruleWILDCARD 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getWILDCARDAccess().getAlternatives()); }
		(rule__WILDCARD__Alternatives)
		{ after(grammarAccess.getWILDCARDAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}


// Rule LETTER
ruleLETTER 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getLETTERAccess().getAlternatives()); }
		(rule__LETTER__Alternatives)
		{ after(grammarAccess.getLETTERAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}


// Rule LETTER_NO_VX
ruleLETTER_NO_VX 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getLETTER_NO_VXAccess().getAlternatives()); }
		(rule__LETTER_NO_VX__Alternatives)
		{ after(grammarAccess.getLETTER_NO_VXAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Rule VersionComparator
ruleVersionComparator
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionComparatorAccess().getAlternatives()); }
		(rule__VersionComparator__Alternatives)
		{ after(grammarAccess.getVersionComparatorAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__NPMVersionRequirement__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getNPMVersionRequirementAccess().getGroup_0()); }
		(rule__NPMVersionRequirement__Group_0__0)
		{ after(grammarAccess.getNPMVersionRequirementAccess().getGroup_0()); }
	)
	|
	(
		{ before(grammarAccess.getNPMVersionRequirementAccess().getGroup_1()); }
		(rule__NPMVersionRequirement__Group_1__0)
		{ after(grammarAccess.getNPMVersionRequirementAccess().getGroup_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__NPMVersionRequirement__Alternatives_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getNPMVersionRequirementAccess().getLocalPathVersionRequirementParserRuleCall_1_0_0()); }
		(ruleLocalPathVersionRequirement)
		{ after(grammarAccess.getNPMVersionRequirementAccess().getLocalPathVersionRequirementParserRuleCall_1_0_0()); }
	)
	|
	(
		{ before(grammarAccess.getNPMVersionRequirementAccess().getAlternatives_1_0_1()); }
		(rule__NPMVersionRequirement__Alternatives_1_0_1)
		{ after(grammarAccess.getNPMVersionRequirementAccess().getAlternatives_1_0_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__NPMVersionRequirement__Alternatives_1_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getNPMVersionRequirementAccess().getURLVersionRequirementParserRuleCall_1_0_1_0()); }
		(ruleURLVersionRequirement)
		{ after(grammarAccess.getNPMVersionRequirementAccess().getURLVersionRequirementParserRuleCall_1_0_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getNPMVersionRequirementAccess().getGitHubVersionRequirementParserRuleCall_1_0_1_1()); }
		ruleGitHubVersionRequirement
		{ after(grammarAccess.getNPMVersionRequirementAccess().getGitHubVersionRequirementParserRuleCall_1_0_1_1()); }
	)
	|
	(
		{ before(grammarAccess.getNPMVersionRequirementAccess().getTagVersionRequirementParserRuleCall_1_0_1_2()); }
		ruleTagVersionRequirement
		{ after(grammarAccess.getNPMVersionRequirementAccess().getTagVersionRequirementParserRuleCall_1_0_1_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionSpecifier__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getURLVersionSpecifierAccess().getGroup_0()); }
		(rule__URLVersionSpecifier__Group_0__0)
		{ after(grammarAccess.getURLVersionSpecifierAccess().getGroup_0()); }
	)
	|
	(
		{ before(grammarAccess.getURLVersionSpecifierAccess().getGroup_1()); }
		(rule__URLVersionSpecifier__Group_1__0)
		{ after(grammarAccess.getURLVersionSpecifierAccess().getGroup_1()); }
	)
	|
	(
		{ before(grammarAccess.getURLVersionSpecifierAccess().getGroup_2()); }
		(rule__URLVersionSpecifier__Group_2__0)
		{ after(grammarAccess.getURLVersionSpecifierAccess().getGroup_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRange__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionRangeAccess().getVersionRangeContraintParserRuleCall_0()); }
		ruleVersionRangeContraint
		{ after(grammarAccess.getVersionRangeAccess().getVersionRangeContraintParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getVersionRangeAccess().getHyphenVersionRangeParserRuleCall_1()); }
		ruleHyphenVersionRange
		{ after(grammarAccess.getVersionRangeAccess().getHyphenVersionRangeParserRuleCall_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionPart__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionPartAccess().getWildcardAssignment_0()); }
		(rule__VersionPart__WildcardAssignment_0)
		{ after(grammarAccess.getVersionPartAccess().getWildcardAssignment_0()); }
	)
	|
	(
		{ before(grammarAccess.getVersionPartAccess().getNumberRawAssignment_1()); }
		(rule__VersionPart__NumberRawAssignment_1)
		{ after(grammarAccess.getVersionPartAccess().getNumberRawAssignment_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Qualifier__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getQualifierAccess().getGroup_0()); }
		(rule__Qualifier__Group_0__0)
		{ after(grammarAccess.getQualifierAccess().getGroup_0()); }
	)
	|
	(
		{ before(grammarAccess.getQualifierAccess().getGroup_1()); }
		(rule__Qualifier__Group_1__0)
		{ after(grammarAccess.getQualifierAccess().getGroup_1()); }
	)
	|
	(
		{ before(grammarAccess.getQualifierAccess().getGroup_2()); }
		(rule__Qualifier__Group_2__0)
		{ after(grammarAccess.getQualifierAccess().getGroup_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__PATH__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPATHAccess().getSolidusKeyword_0()); }
		'/'
		{ after(grammarAccess.getPATHAccess().getSolidusKeyword_0()); }
	)
	|
	(
		{ before(grammarAccess.getPATHAccess().getFullStopKeyword_1()); }
		'.'
		{ after(grammarAccess.getPATHAccess().getFullStopKeyword_1()); }
	)
	|
	(
		{ before(grammarAccess.getPATHAccess().getCommercialAtKeyword_2()); }
		'@'
		{ after(grammarAccess.getPATHAccess().getCommercialAtKeyword_2()); }
	)
	|
	(
		{ before(grammarAccess.getPATHAccess().getHyphenMinusKeyword_3()); }
		'-'
		{ after(grammarAccess.getPATHAccess().getHyphenMinusKeyword_3()); }
	)
	|
	(
		{ before(grammarAccess.getPATHAccess().get_Keyword_4()); }
		'_'
		{ after(grammarAccess.getPATHAccess().get_Keyword_4()); }
	)
	|
	(
		{ before(grammarAccess.getPATHAccess().getDIGITSTerminalRuleCall_5()); }
		RULE_DIGITS
		{ after(grammarAccess.getPATHAccess().getDIGITSTerminalRuleCall_5()); }
	)
	|
	(
		{ before(grammarAccess.getPATHAccess().getLETTERParserRuleCall_6()); }
		ruleLETTER
		{ after(grammarAccess.getPATHAccess().getLETTERParserRuleCall_6()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__URL_PROTOCOL__Alternatives_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getURL_PROTOCOLAccess().getLETTERParserRuleCall_1_0()); }
		ruleLETTER
		{ after(grammarAccess.getURL_PROTOCOLAccess().getLETTERParserRuleCall_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getURL_PROTOCOLAccess().getPlusSignKeyword_1_1()); }
		'+'
		{ after(grammarAccess.getURL_PROTOCOLAccess().getPlusSignKeyword_1_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__URL__Alternatives_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getURLAccess().getHyphenMinusKeyword_0_0()); }
		'-'
		{ after(grammarAccess.getURLAccess().getHyphenMinusKeyword_0_0()); }
	)
	|
	(
		{ before(grammarAccess.getURLAccess().get_Keyword_0_1()); }
		'_'
		{ after(grammarAccess.getURLAccess().get_Keyword_0_1()); }
	)
	|
	(
		{ before(grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_0_2()); }
		RULE_DIGITS
		{ after(grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_0_2()); }
	)
	|
	(
		{ before(grammarAccess.getURLAccess().getLETTERParserRuleCall_0_3()); }
		ruleLETTER
		{ after(grammarAccess.getURLAccess().getLETTERParserRuleCall_0_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__URL__Alternatives_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getURLAccess().getSolidusKeyword_1_0()); }
		'/'
		{ after(grammarAccess.getURLAccess().getSolidusKeyword_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getURLAccess().getFullStopKeyword_1_1()); }
		'.'
		{ after(grammarAccess.getURLAccess().getFullStopKeyword_1_1()); }
	)
	|
	(
		{ before(grammarAccess.getURLAccess().getColonKeyword_1_2()); }
		':'
		{ after(grammarAccess.getURLAccess().getColonKeyword_1_2()); }
	)
	|
	(
		{ before(grammarAccess.getURLAccess().getCommercialAtKeyword_1_3()); }
		'@'
		{ after(grammarAccess.getURLAccess().getCommercialAtKeyword_1_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__URL__Alternatives_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getURLAccess().getSolidusKeyword_2_0()); }
		'/'
		{ after(grammarAccess.getURLAccess().getSolidusKeyword_2_0()); }
	)
	|
	(
		{ before(grammarAccess.getURLAccess().getFullStopKeyword_2_1()); }
		'.'
		{ after(grammarAccess.getURLAccess().getFullStopKeyword_2_1()); }
	)
	|
	(
		{ before(grammarAccess.getURLAccess().getColonKeyword_2_2()); }
		':'
		{ after(grammarAccess.getURLAccess().getColonKeyword_2_2()); }
	)
	|
	(
		{ before(grammarAccess.getURLAccess().getCommercialAtKeyword_2_3()); }
		'@'
		{ after(grammarAccess.getURLAccess().getCommercialAtKeyword_2_3()); }
	)
	|
	(
		{ before(grammarAccess.getURLAccess().getHyphenMinusKeyword_2_4()); }
		'-'
		{ after(grammarAccess.getURLAccess().getHyphenMinusKeyword_2_4()); }
	)
	|
	(
		{ before(grammarAccess.getURLAccess().get_Keyword_2_5()); }
		'_'
		{ after(grammarAccess.getURLAccess().get_Keyword_2_5()); }
	)
	|
	(
		{ before(grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_2_6()); }
		RULE_DIGITS
		{ after(grammarAccess.getURLAccess().getDIGITSTerminalRuleCall_2_6()); }
	)
	|
	(
		{ before(grammarAccess.getURLAccess().getLETTERParserRuleCall_2_7()); }
		ruleLETTER
		{ after(grammarAccess.getURLAccess().getLETTERParserRuleCall_2_7()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__URL_NO_VX__Alternatives_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_0_0()); }
		'-'
		{ after(grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_0_0()); }
	)
	|
	(
		{ before(grammarAccess.getURL_NO_VXAccess().get_Keyword_0_1()); }
		'_'
		{ after(grammarAccess.getURL_NO_VXAccess().get_Keyword_0_1()); }
	)
	|
	(
		{ before(grammarAccess.getURL_NO_VXAccess().getLETTER_NO_VXParserRuleCall_0_2()); }
		ruleLETTER_NO_VX
		{ after(grammarAccess.getURL_NO_VXAccess().getLETTER_NO_VXParserRuleCall_0_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__URL_NO_VX__Alternatives_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_1_0()); }
		'-'
		{ after(grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getURL_NO_VXAccess().get_Keyword_1_1()); }
		'_'
		{ after(grammarAccess.getURL_NO_VXAccess().get_Keyword_1_1()); }
	)
	|
	(
		{ before(grammarAccess.getURL_NO_VXAccess().getDIGITSTerminalRuleCall_1_2()); }
		RULE_DIGITS
		{ after(grammarAccess.getURL_NO_VXAccess().getDIGITSTerminalRuleCall_1_2()); }
	)
	|
	(
		{ before(grammarAccess.getURL_NO_VXAccess().getLETTERParserRuleCall_1_3()); }
		ruleLETTER
		{ after(grammarAccess.getURL_NO_VXAccess().getLETTERParserRuleCall_1_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__URL_NO_VX__Alternatives_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getURL_NO_VXAccess().getSolidusKeyword_2_0()); }
		'/'
		{ after(grammarAccess.getURL_NO_VXAccess().getSolidusKeyword_2_0()); }
	)
	|
	(
		{ before(grammarAccess.getURL_NO_VXAccess().getFullStopKeyword_2_1()); }
		'.'
		{ after(grammarAccess.getURL_NO_VXAccess().getFullStopKeyword_2_1()); }
	)
	|
	(
		{ before(grammarAccess.getURL_NO_VXAccess().getColonKeyword_2_2()); }
		':'
		{ after(grammarAccess.getURL_NO_VXAccess().getColonKeyword_2_2()); }
	)
	|
	(
		{ before(grammarAccess.getURL_NO_VXAccess().getCommercialAtKeyword_2_3()); }
		'@'
		{ after(grammarAccess.getURL_NO_VXAccess().getCommercialAtKeyword_2_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__URL_NO_VX__Alternatives_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getURL_NO_VXAccess().getSolidusKeyword_3_0()); }
		'/'
		{ after(grammarAccess.getURL_NO_VXAccess().getSolidusKeyword_3_0()); }
	)
	|
	(
		{ before(grammarAccess.getURL_NO_VXAccess().getFullStopKeyword_3_1()); }
		'.'
		{ after(grammarAccess.getURL_NO_VXAccess().getFullStopKeyword_3_1()); }
	)
	|
	(
		{ before(grammarAccess.getURL_NO_VXAccess().getColonKeyword_3_2()); }
		':'
		{ after(grammarAccess.getURL_NO_VXAccess().getColonKeyword_3_2()); }
	)
	|
	(
		{ before(grammarAccess.getURL_NO_VXAccess().getCommercialAtKeyword_3_3()); }
		'@'
		{ after(grammarAccess.getURL_NO_VXAccess().getCommercialAtKeyword_3_3()); }
	)
	|
	(
		{ before(grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_3_4()); }
		'-'
		{ after(grammarAccess.getURL_NO_VXAccess().getHyphenMinusKeyword_3_4()); }
	)
	|
	(
		{ before(grammarAccess.getURL_NO_VXAccess().get_Keyword_3_5()); }
		'_'
		{ after(grammarAccess.getURL_NO_VXAccess().get_Keyword_3_5()); }
	)
	|
	(
		{ before(grammarAccess.getURL_NO_VXAccess().getDIGITSTerminalRuleCall_3_6()); }
		RULE_DIGITS
		{ after(grammarAccess.getURL_NO_VXAccess().getDIGITSTerminalRuleCall_3_6()); }
	)
	|
	(
		{ before(grammarAccess.getURL_NO_VXAccess().getLETTERParserRuleCall_3_7()); }
		ruleLETTER
		{ after(grammarAccess.getURL_NO_VXAccess().getLETTERParserRuleCall_3_7()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAG__Alternatives_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTAGAccess().getHyphenMinusKeyword_1_0()); }
		'-'
		{ after(grammarAccess.getTAGAccess().getHyphenMinusKeyword_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getTAGAccess().getDIGITSTerminalRuleCall_1_1()); }
		RULE_DIGITS
		{ after(grammarAccess.getTAGAccess().getDIGITSTerminalRuleCall_1_1()); }
	)
	|
	(
		{ before(grammarAccess.getTAGAccess().getLETTERParserRuleCall_1_2()); }
		ruleLETTER
		{ after(grammarAccess.getTAGAccess().getLETTERParserRuleCall_1_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ALPHA_NUMERIC_CHARS__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getHyphenMinusKeyword_0()); }
		'-'
		{ after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getHyphenMinusKeyword_0()); }
	)
	|
	(
		{ before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getDIGITSTerminalRuleCall_1()); }
		RULE_DIGITS
		{ after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getDIGITSTerminalRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getLETTERParserRuleCall_2()); }
		ruleLETTER
		{ after(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getLETTERParserRuleCall_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getHyphenMinusKeyword_1_0()); }
		'-'
		{ after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getHyphenMinusKeyword_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getDIGITSTerminalRuleCall_1_1()); }
		RULE_DIGITS
		{ after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getDIGITSTerminalRuleCall_1_1()); }
	)
	|
	(
		{ before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getLETTERParserRuleCall_1_2()); }
		ruleLETTER
		{ after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getLETTERParserRuleCall_1_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__WILDCARD__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getWILDCARDAccess().getLETTER_XTerminalRuleCall_0()); }
		RULE_LETTER_X
		{ after(grammarAccess.getWILDCARDAccess().getLETTER_XTerminalRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getWILDCARDAccess().getASTERIXTerminalRuleCall_1()); }
		RULE_ASTERIX
		{ after(grammarAccess.getWILDCARDAccess().getASTERIXTerminalRuleCall_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__LETTER__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getLETTERAccess().getLETTER_VTerminalRuleCall_0()); }
		RULE_LETTER_V
		{ after(grammarAccess.getLETTERAccess().getLETTER_VTerminalRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getLETTERAccess().getLETTER_XTerminalRuleCall_1()); }
		RULE_LETTER_X
		{ after(grammarAccess.getLETTERAccess().getLETTER_XTerminalRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getLETTERAccess().getLETTER_NO_VXParserRuleCall_2()); }
		ruleLETTER_NO_VX
		{ after(grammarAccess.getLETTERAccess().getLETTER_NO_VXParserRuleCall_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__LETTER_NO_VX__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_STerminalRuleCall_0()); }
		RULE_LETTER_S
		{ after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_STerminalRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_MTerminalRuleCall_1()); }
		RULE_LETTER_M
		{ after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_MTerminalRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_RTerminalRuleCall_2()); }
		RULE_LETTER_R
		{ after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_RTerminalRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_FTerminalRuleCall_3()); }
		RULE_LETTER_F
		{ after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_FTerminalRuleCall_3()); }
	)
	|
	(
		{ before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_ITerminalRuleCall_4()); }
		RULE_LETTER_I
		{ after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_ITerminalRuleCall_4()); }
	)
	|
	(
		{ before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_LTerminalRuleCall_5()); }
		RULE_LETTER_L
		{ after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_LTerminalRuleCall_5()); }
	)
	|
	(
		{ before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_ETerminalRuleCall_6()); }
		RULE_LETTER_E
		{ after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_ETerminalRuleCall_6()); }
	)
	|
	(
		{ before(grammarAccess.getLETTER_NO_VXAccess().getLETTER_OTHERTerminalRuleCall_7()); }
		RULE_LETTER_OTHER
		{ after(grammarAccess.getLETTER_NO_VXAccess().getLETTER_OTHERTerminalRuleCall_7()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionComparator__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0()); }
		('=')
		{ after(grammarAccess.getVersionComparatorAccess().getEqualsEnumLiteralDeclaration_0()); }
	)
	|
	(
		{ before(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1()); }
		('<')
		{ after(grammarAccess.getVersionComparatorAccess().getSmallerEnumLiteralDeclaration_1()); }
	)
	|
	(
		{ before(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_2()); }
		('~')
		{ after(grammarAccess.getVersionComparatorAccess().getTildeEnumLiteralDeclaration_2()); }
	)
	|
	(
		{ before(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_3()); }
		('^')
		{ after(grammarAccess.getVersionComparatorAccess().getCaretEnumLiteralDeclaration_3()); }
	)
	|
	(
		{ before(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_4()); }
		('<=')
		{ after(grammarAccess.getVersionComparatorAccess().getSmallerEqualsEnumLiteralDeclaration_4()); }
	)
	|
	(
		{ before(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_5()); }
		('>')
		{ after(grammarAccess.getVersionComparatorAccess().getGreaterEnumLiteralDeclaration_5()); }
	)
	|
	(
		{ before(grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_6()); }
		('>=')
		{ after(grammarAccess.getVersionComparatorAccess().getGreaterEqualsEnumLiteralDeclaration_6()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__NPMVersionRequirement__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__NPMVersionRequirement__Group_0__0__Impl
	rule__NPMVersionRequirement__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__NPMVersionRequirement__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_0_0()); }
	(RULE_WS)*
	{ after(grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__NPMVersionRequirement__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__NPMVersionRequirement__Group_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__NPMVersionRequirement__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getNPMVersionRequirementAccess().getVersionRangeSetRequirementParserRuleCall_0_1()); }
	ruleVersionRangeSetRequirement
	{ after(grammarAccess.getNPMVersionRequirementAccess().getVersionRangeSetRequirementParserRuleCall_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__NPMVersionRequirement__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__NPMVersionRequirement__Group_1__0__Impl
	rule__NPMVersionRequirement__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__NPMVersionRequirement__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getNPMVersionRequirementAccess().getAlternatives_1_0()); }
	(rule__NPMVersionRequirement__Alternatives_1_0)
	{ after(grammarAccess.getNPMVersionRequirementAccess().getAlternatives_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__NPMVersionRequirement__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__NPMVersionRequirement__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__NPMVersionRequirement__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_1_1()); }
	(RULE_WS)*
	{ after(grammarAccess.getNPMVersionRequirementAccess().getWSTerminalRuleCall_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__LocalPathVersionRequirement__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LocalPathVersionRequirement__Group__0__Impl
	rule__LocalPathVersionRequirement__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalPathVersionRequirement__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLocalPathVersionRequirementAccess().getFILE_TAGParserRuleCall_0()); }
	ruleFILE_TAG
	{ after(grammarAccess.getLocalPathVersionRequirementAccess().getFILE_TAGParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalPathVersionRequirement__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__LocalPathVersionRequirement__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__LocalPathVersionRequirement__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getLocalPathVersionRequirementAccess().getLocalPathAssignment_1()); }
	(rule__LocalPathVersionRequirement__LocalPathAssignment_1)
	{ after(grammarAccess.getLocalPathVersionRequirementAccess().getLocalPathAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__URLVersionRequirement__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URLVersionRequirement__Group__0__Impl
	rule__URLVersionRequirement__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionRequirement__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURLVersionRequirementAccess().getProtocolAssignment_0()); }
	(rule__URLVersionRequirement__ProtocolAssignment_0)
	{ after(grammarAccess.getURLVersionRequirementAccess().getProtocolAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionRequirement__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URLVersionRequirement__Group__1__Impl
	rule__URLVersionRequirement__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionRequirement__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURLVersionRequirementAccess().getGroup_1()); }
	(rule__URLVersionRequirement__Group_1__0)
	{ after(grammarAccess.getURLVersionRequirementAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionRequirement__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URLVersionRequirement__Group__2__Impl
	rule__URLVersionRequirement__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionRequirement__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURLVersionRequirementAccess().getUrlAssignment_2()); }
	(rule__URLVersionRequirement__UrlAssignment_2)
	{ after(grammarAccess.getURLVersionRequirementAccess().getUrlAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionRequirement__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URLVersionRequirement__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionRequirement__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURLVersionRequirementAccess().getGroup_3()); }
	(rule__URLVersionRequirement__Group_3__0)?
	{ after(grammarAccess.getURLVersionRequirementAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__URLVersionRequirement__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URLVersionRequirement__Group_1__0__Impl
	rule__URLVersionRequirement__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionRequirement__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURLVersionRequirementAccess().getColonKeyword_1_0()); }
	':'
	{ after(grammarAccess.getURLVersionRequirementAccess().getColonKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionRequirement__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URLVersionRequirement__Group_1__1__Impl
	rule__URLVersionRequirement__Group_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionRequirement__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURLVersionRequirementAccess().getSolidusKeyword_1_1()); }
	'/'
	{ after(grammarAccess.getURLVersionRequirementAccess().getSolidusKeyword_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionRequirement__Group_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URLVersionRequirement__Group_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionRequirement__Group_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURLVersionRequirementAccess().getSolidusKeyword_1_2()); }
	'/'
	{ after(grammarAccess.getURLVersionRequirementAccess().getSolidusKeyword_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__URLVersionRequirement__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URLVersionRequirement__Group_3__0__Impl
	rule__URLVersionRequirement__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionRequirement__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURLVersionRequirementAccess().getNumberSignKeyword_3_0()); }
	'#'
	{ after(grammarAccess.getURLVersionRequirementAccess().getNumberSignKeyword_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionRequirement__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URLVersionRequirement__Group_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionRequirement__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURLVersionRequirementAccess().getVersionSpecifierAssignment_3_1()); }
	(rule__URLVersionRequirement__VersionSpecifierAssignment_3_1)
	{ after(grammarAccess.getURLVersionRequirementAccess().getVersionSpecifierAssignment_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__URLVersionSpecifier__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URLVersionSpecifier__Group_0__0__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionSpecifier__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURLVersionSpecifierAccess().getURLSemverParserRuleCall_0_0()); }
	ruleURLSemver
	{ after(grammarAccess.getURLVersionSpecifierAccess().getURLSemverParserRuleCall_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__URLVersionSpecifier__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URLVersionSpecifier__Group_1__0__Impl
	rule__URLVersionSpecifier__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionSpecifier__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_1_0()); }
	()
	{ after(grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionSpecifier__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URLVersionSpecifier__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionSpecifier__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURLVersionSpecifierAccess().getCommitISHAssignment_1_1()); }
	(rule__URLVersionSpecifier__CommitISHAssignment_1_1)
	{ after(grammarAccess.getURLVersionSpecifierAccess().getCommitISHAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__URLVersionSpecifier__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URLVersionSpecifier__Group_2__0__Impl
	rule__URLVersionSpecifier__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionSpecifier__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_2_0()); }
	()
	{ after(grammarAccess.getURLVersionSpecifierAccess().getURLCommitISHAction_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionSpecifier__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URLVersionSpecifier__Group_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionSpecifier__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURLVersionSpecifierAccess().getCommitISHAssignment_2_1()); }
	(rule__URLVersionSpecifier__CommitISHAssignment_2_1)
	{ after(grammarAccess.getURLVersionSpecifierAccess().getCommitISHAssignment_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__URLSemver__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URLSemver__Group__0__Impl
	rule__URLSemver__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__URLSemver__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURLSemverAccess().getURLSemverAction_0()); }
	()
	{ after(grammarAccess.getURLSemverAccess().getURLSemverAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__URLSemver__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URLSemver__Group__1__Impl
	rule__URLSemver__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__URLSemver__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURLSemverAccess().getWithSemverTagAssignment_1()); }
	(rule__URLSemver__WithSemverTagAssignment_1)?
	{ after(grammarAccess.getURLSemverAccess().getWithSemverTagAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__URLSemver__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URLSemver__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__URLSemver__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURLSemverAccess().getSimpleVersionAssignment_2()); }
	(rule__URLSemver__SimpleVersionAssignment_2)
	{ after(grammarAccess.getURLSemverAccess().getSimpleVersionAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__GitHubVersionRequirement__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__GitHubVersionRequirement__Group__0__Impl
	rule__GitHubVersionRequirement__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__GitHubVersionRequirement__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlAssignment_0()); }
	(rule__GitHubVersionRequirement__GithubUrlAssignment_0)
	{ after(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__GitHubVersionRequirement__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__GitHubVersionRequirement__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__GitHubVersionRequirement__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getGitHubVersionRequirementAccess().getGroup_1()); }
	(rule__GitHubVersionRequirement__Group_1__0)?
	{ after(grammarAccess.getGitHubVersionRequirementAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__GitHubVersionRequirement__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__GitHubVersionRequirement__Group_1__0__Impl
	rule__GitHubVersionRequirement__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__GitHubVersionRequirement__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getGitHubVersionRequirementAccess().getNumberSignKeyword_1_0()); }
	'#'
	{ after(grammarAccess.getGitHubVersionRequirementAccess().getNumberSignKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__GitHubVersionRequirement__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__GitHubVersionRequirement__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__GitHubVersionRequirement__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getGitHubVersionRequirementAccess().getCommitISHAssignment_1_1()); }
	(rule__GitHubVersionRequirement__CommitISHAssignment_1_1)
	{ after(grammarAccess.getGitHubVersionRequirementAccess().getCommitISHAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__VersionRangeSetRequirement__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionRangeSetRequirement__Group__0__Impl
	rule__VersionRangeSetRequirement__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeSetRequirement__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionRangeSetRequirementAccess().getVersionRangeSetRequirementAction_0()); }
	()
	{ after(grammarAccess.getVersionRangeSetRequirementAccess().getVersionRangeSetRequirementAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeSetRequirement__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionRangeSetRequirement__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeSetRequirement__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1()); }
	(rule__VersionRangeSetRequirement__Group_1__0)?
	{ after(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__VersionRangeSetRequirement__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionRangeSetRequirement__Group_1__0__Impl
	rule__VersionRangeSetRequirement__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeSetRequirement__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_0()); }
	(rule__VersionRangeSetRequirement__RangesAssignment_1_0)
	{ after(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeSetRequirement__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionRangeSetRequirement__Group_1__1__Impl
	rule__VersionRangeSetRequirement__Group_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeSetRequirement__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1_1()); }
	(rule__VersionRangeSetRequirement__Group_1_1__0)*
	{ after(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeSetRequirement__Group_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionRangeSetRequirement__Group_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeSetRequirement__Group_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_2()); }
	(RULE_WS)*
	{ after(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__VersionRangeSetRequirement__Group_1_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionRangeSetRequirement__Group_1_1__0__Impl
	rule__VersionRangeSetRequirement__Group_1_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeSetRequirement__Group_1_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_0()); }
	(RULE_WS)*
	{ after(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeSetRequirement__Group_1_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionRangeSetRequirement__Group_1_1__1__Impl
	rule__VersionRangeSetRequirement__Group_1_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeSetRequirement__Group_1_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionRangeSetRequirementAccess().getVerticalLineVerticalLineKeyword_1_1_1()); }
	'||'
	{ after(grammarAccess.getVersionRangeSetRequirementAccess().getVerticalLineVerticalLineKeyword_1_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeSetRequirement__Group_1_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionRangeSetRequirement__Group_1_1__2__Impl
	rule__VersionRangeSetRequirement__Group_1_1__3
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeSetRequirement__Group_1_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_2()); }
	(RULE_WS)*
	{ after(grammarAccess.getVersionRangeSetRequirementAccess().getWSTerminalRuleCall_1_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeSetRequirement__Group_1_1__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionRangeSetRequirement__Group_1_1__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeSetRequirement__Group_1_1__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_1_3()); }
	(rule__VersionRangeSetRequirement__RangesAssignment_1_1_3)
	{ after(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_1_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__HyphenVersionRange__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__HyphenVersionRange__Group__0__Impl
	rule__HyphenVersionRange__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__HyphenVersionRange__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0()); }
	()
	{ after(grammarAccess.getHyphenVersionRangeAccess().getHyphenVersionRangeAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__HyphenVersionRange__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__HyphenVersionRange__Group__1__Impl
	rule__HyphenVersionRange__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__HyphenVersionRange__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getHyphenVersionRangeAccess().getFromAssignment_1()); }
	(rule__HyphenVersionRange__FromAssignment_1)
	{ after(grammarAccess.getHyphenVersionRangeAccess().getFromAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__HyphenVersionRange__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__HyphenVersionRange__Group__2__Impl
	rule__HyphenVersionRange__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__HyphenVersionRange__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	(
		{ before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); }
		(RULE_WS)
		{ after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); }
	)
	(
		{ before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); }
		(RULE_WS)*
		{ after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_2()); }
	)
)
;
finally {
	restoreStackSize(stackSize);
}

rule__HyphenVersionRange__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__HyphenVersionRange__Group__3__Impl
	rule__HyphenVersionRange__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__HyphenVersionRange__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_3()); }
	'-'
	{ after(grammarAccess.getHyphenVersionRangeAccess().getHyphenMinusKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__HyphenVersionRange__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__HyphenVersionRange__Group__4__Impl
	rule__HyphenVersionRange__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__HyphenVersionRange__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	(
		{ before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); }
		(RULE_WS)
		{ after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); }
	)
	(
		{ before(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); }
		(RULE_WS)*
		{ after(grammarAccess.getHyphenVersionRangeAccess().getWSTerminalRuleCall_4()); }
	)
)
;
finally {
	restoreStackSize(stackSize);
}

rule__HyphenVersionRange__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__HyphenVersionRange__Group__5__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__HyphenVersionRange__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getHyphenVersionRangeAccess().getToAssignment_5()); }
	(rule__HyphenVersionRange__ToAssignment_5)
	{ after(grammarAccess.getHyphenVersionRangeAccess().getToAssignment_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__VersionRangeContraint__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionRangeContraint__Group__0__Impl
	rule__VersionRangeContraint__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeContraint__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionRangeContraintAccess().getVersionRangeConstraintAction_0()); }
	()
	{ after(grammarAccess.getVersionRangeContraintAccess().getVersionRangeConstraintAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeContraint__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionRangeContraint__Group__1__Impl
	rule__VersionRangeContraint__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeContraint__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_1()); }
	(rule__VersionRangeContraint__VersionConstraintsAssignment_1)
	{ after(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeContraint__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionRangeContraint__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeContraint__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionRangeContraintAccess().getGroup_2()); }
	(rule__VersionRangeContraint__Group_2__0)*
	{ after(grammarAccess.getVersionRangeContraintAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__VersionRangeContraint__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionRangeContraint__Group_2__0__Impl
	rule__VersionRangeContraint__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeContraint__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	(
		{ before(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); }
		(RULE_WS)
		{ after(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); }
	)
	(
		{ before(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); }
		(RULE_WS)*
		{ after(grammarAccess.getVersionRangeContraintAccess().getWSTerminalRuleCall_2_0()); }
	)
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeContraint__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionRangeContraint__Group_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeContraint__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_2_1()); }
	(rule__VersionRangeContraint__VersionConstraintsAssignment_2_1)
	{ after(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__SimpleVersion__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SimpleVersion__Group__0__Impl
	rule__SimpleVersion__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleVersion__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSimpleVersionAccess().getGroup_0()); }
	(rule__SimpleVersion__Group_0__0)*
	{ after(grammarAccess.getSimpleVersionAccess().getGroup_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleVersion__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SimpleVersion__Group__1__Impl
	rule__SimpleVersion__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleVersion__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSimpleVersionAccess().getWithLetterVAssignment_1()); }
	(rule__SimpleVersion__WithLetterVAssignment_1)?
	{ after(grammarAccess.getSimpleVersionAccess().getWithLetterVAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleVersion__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SimpleVersion__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleVersion__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSimpleVersionAccess().getNumberAssignment_2()); }
	(rule__SimpleVersion__NumberAssignment_2)
	{ after(grammarAccess.getSimpleVersionAccess().getNumberAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__SimpleVersion__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SimpleVersion__Group_0__0__Impl
	rule__SimpleVersion__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleVersion__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSimpleVersionAccess().getComparatorsAssignment_0_0()); }
	(rule__SimpleVersion__ComparatorsAssignment_0_0)
	{ after(grammarAccess.getSimpleVersionAccess().getComparatorsAssignment_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleVersion__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SimpleVersion__Group_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleVersion__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSimpleVersionAccess().getWSTerminalRuleCall_0_1()); }
	(RULE_WS)*
	{ after(grammarAccess.getSimpleVersionAccess().getWSTerminalRuleCall_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__VersionNumber__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionNumber__Group__0__Impl
	rule__VersionNumber__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionNumberAccess().getMajorAssignment_0()); }
	(rule__VersionNumber__MajorAssignment_0)
	{ after(grammarAccess.getVersionNumberAccess().getMajorAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionNumber__Group__1__Impl
	rule__VersionNumber__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionNumberAccess().getGroup_1()); }
	(rule__VersionNumber__Group_1__0)?
	{ after(grammarAccess.getVersionNumberAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionNumber__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionNumberAccess().getQualifierAssignment_2()); }
	(rule__VersionNumber__QualifierAssignment_2)?
	{ after(grammarAccess.getVersionNumberAccess().getQualifierAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__VersionNumber__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionNumber__Group_1__0__Impl
	rule__VersionNumber__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0()); }
	'.'
	{ after(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionNumber__Group_1__1__Impl
	rule__VersionNumber__Group_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionNumberAccess().getMinorAssignment_1_1()); }
	(rule__VersionNumber__MinorAssignment_1_1)
	{ after(grammarAccess.getVersionNumberAccess().getMinorAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__Group_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionNumber__Group_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__Group_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionNumberAccess().getGroup_1_2()); }
	(rule__VersionNumber__Group_1_2__0)?
	{ after(grammarAccess.getVersionNumberAccess().getGroup_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__VersionNumber__Group_1_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionNumber__Group_1_2__0__Impl
	rule__VersionNumber__Group_1_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__Group_1_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0()); }
	'.'
	{ after(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__Group_1_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionNumber__Group_1_2__1__Impl
	rule__VersionNumber__Group_1_2__2
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__Group_1_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionNumberAccess().getPatchAssignment_1_2_1()); }
	(rule__VersionNumber__PatchAssignment_1_2_1)
	{ after(grammarAccess.getVersionNumberAccess().getPatchAssignment_1_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__Group_1_2__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionNumber__Group_1_2__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__Group_1_2__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionNumberAccess().getGroup_1_2_2()); }
	(rule__VersionNumber__Group_1_2_2__0)*
	{ after(grammarAccess.getVersionNumberAccess().getGroup_1_2_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__VersionNumber__Group_1_2_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionNumber__Group_1_2_2__0__Impl
	rule__VersionNumber__Group_1_2_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__Group_1_2_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_2_0()); }
	'.'
	{ after(grammarAccess.getVersionNumberAccess().getFullStopKeyword_1_2_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__Group_1_2_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__VersionNumber__Group_1_2_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__Group_1_2_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getVersionNumberAccess().getExtendedAssignment_1_2_2_1()); }
	(rule__VersionNumber__ExtendedAssignment_1_2_2_1)
	{ after(grammarAccess.getVersionNumberAccess().getExtendedAssignment_1_2_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Qualifier__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Qualifier__Group_0__0__Impl
	rule__Qualifier__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Qualifier__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_0_0()); }
	'-'
	{ after(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Qualifier__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Qualifier__Group_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Qualifier__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_0_1()); }
	(rule__Qualifier__PreReleaseAssignment_0_1)
	{ after(grammarAccess.getQualifierAccess().getPreReleaseAssignment_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Qualifier__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Qualifier__Group_1__0__Impl
	rule__Qualifier__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Qualifier__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0()); }
	'+'
	{ after(grammarAccess.getQualifierAccess().getPlusSignKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Qualifier__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Qualifier__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Qualifier__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_1_1()); }
	(rule__Qualifier__BuildMetadataAssignment_1_1)
	{ after(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Qualifier__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Qualifier__Group_2__0__Impl
	rule__Qualifier__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Qualifier__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_2_0()); }
	'-'
	{ after(grammarAccess.getQualifierAccess().getHyphenMinusKeyword_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Qualifier__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Qualifier__Group_2__1__Impl
	rule__Qualifier__Group_2__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Qualifier__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getQualifierAccess().getPreReleaseAssignment_2_1()); }
	(rule__Qualifier__PreReleaseAssignment_2_1)
	{ after(grammarAccess.getQualifierAccess().getPreReleaseAssignment_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Qualifier__Group_2__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Qualifier__Group_2__2__Impl
	rule__Qualifier__Group_2__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Qualifier__Group_2__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getQualifierAccess().getPlusSignKeyword_2_2()); }
	'+'
	{ after(grammarAccess.getQualifierAccess().getPlusSignKeyword_2_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Qualifier__Group_2__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Qualifier__Group_2__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Qualifier__Group_2__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_2_3()); }
	(rule__Qualifier__BuildMetadataAssignment_2_3)
	{ after(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_2_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__QualifierTag__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__QualifierTag__Group__0__Impl
	rule__QualifierTag__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__QualifierTag__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getQualifierTagAccess().getPartsAssignment_0()); }
	(rule__QualifierTag__PartsAssignment_0)
	{ after(grammarAccess.getQualifierTagAccess().getPartsAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__QualifierTag__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__QualifierTag__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__QualifierTag__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getQualifierTagAccess().getGroup_1()); }
	(rule__QualifierTag__Group_1__0)*
	{ after(grammarAccess.getQualifierTagAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__QualifierTag__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__QualifierTag__Group_1__0__Impl
	rule__QualifierTag__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__QualifierTag__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getQualifierTagAccess().getFullStopKeyword_1_0()); }
	'.'
	{ after(grammarAccess.getQualifierTagAccess().getFullStopKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__QualifierTag__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__QualifierTag__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__QualifierTag__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getQualifierTagAccess().getPartsAssignment_1_1()); }
	(rule__QualifierTag__PartsAssignment_1_1)
	{ after(grammarAccess.getQualifierTagAccess().getPartsAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__FILE_TAG__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FILE_TAG__Group__0__Impl
	rule__FILE_TAG__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__FILE_TAG__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFILE_TAGAccess().getLETTER_FTerminalRuleCall_0()); }
	RULE_LETTER_F
	{ after(grammarAccess.getFILE_TAGAccess().getLETTER_FTerminalRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FILE_TAG__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FILE_TAG__Group__1__Impl
	rule__FILE_TAG__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__FILE_TAG__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFILE_TAGAccess().getLETTER_ITerminalRuleCall_1()); }
	RULE_LETTER_I
	{ after(grammarAccess.getFILE_TAGAccess().getLETTER_ITerminalRuleCall_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FILE_TAG__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FILE_TAG__Group__2__Impl
	rule__FILE_TAG__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__FILE_TAG__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFILE_TAGAccess().getLETTER_LTerminalRuleCall_2()); }
	RULE_LETTER_L
	{ after(grammarAccess.getFILE_TAGAccess().getLETTER_LTerminalRuleCall_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FILE_TAG__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FILE_TAG__Group__3__Impl
	rule__FILE_TAG__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__FILE_TAG__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFILE_TAGAccess().getLETTER_ETerminalRuleCall_3()); }
	RULE_LETTER_E
	{ after(grammarAccess.getFILE_TAGAccess().getLETTER_ETerminalRuleCall_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__FILE_TAG__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__FILE_TAG__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__FILE_TAG__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getFILE_TAGAccess().getColonKeyword_4()); }
	':'
	{ after(grammarAccess.getFILE_TAGAccess().getColonKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__SEMVER_TAG__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SEMVER_TAG__Group__0__Impl
	rule__SEMVER_TAG__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__SEMVER_TAG__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSEMVER_TAGAccess().getLETTER_STerminalRuleCall_0()); }
	RULE_LETTER_S
	{ after(grammarAccess.getSEMVER_TAGAccess().getLETTER_STerminalRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SEMVER_TAG__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SEMVER_TAG__Group__1__Impl
	rule__SEMVER_TAG__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__SEMVER_TAG__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSEMVER_TAGAccess().getLETTER_ETerminalRuleCall_1()); }
	RULE_LETTER_E
	{ after(grammarAccess.getSEMVER_TAGAccess().getLETTER_ETerminalRuleCall_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SEMVER_TAG__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SEMVER_TAG__Group__2__Impl
	rule__SEMVER_TAG__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__SEMVER_TAG__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSEMVER_TAGAccess().getLETTER_MTerminalRuleCall_2()); }
	RULE_LETTER_M
	{ after(grammarAccess.getSEMVER_TAGAccess().getLETTER_MTerminalRuleCall_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SEMVER_TAG__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SEMVER_TAG__Group__3__Impl
	rule__SEMVER_TAG__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__SEMVER_TAG__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSEMVER_TAGAccess().getLETTER_VTerminalRuleCall_3()); }
	RULE_LETTER_V
	{ after(grammarAccess.getSEMVER_TAGAccess().getLETTER_VTerminalRuleCall_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SEMVER_TAG__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SEMVER_TAG__Group__4__Impl
	rule__SEMVER_TAG__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__SEMVER_TAG__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSEMVER_TAGAccess().getLETTER_ETerminalRuleCall_4()); }
	RULE_LETTER_E
	{ after(grammarAccess.getSEMVER_TAGAccess().getLETTER_ETerminalRuleCall_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SEMVER_TAG__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SEMVER_TAG__Group__5__Impl
	rule__SEMVER_TAG__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__SEMVER_TAG__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSEMVER_TAGAccess().getLETTER_RTerminalRuleCall_5()); }
	RULE_LETTER_R
	{ after(grammarAccess.getSEMVER_TAGAccess().getLETTER_RTerminalRuleCall_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SEMVER_TAG__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SEMVER_TAG__Group__6__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__SEMVER_TAG__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSEMVER_TAGAccess().getColonKeyword_6()); }
	':'
	{ after(grammarAccess.getSEMVER_TAGAccess().getColonKeyword_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__URL_PROTOCOL__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URL_PROTOCOL__Group__0__Impl
	rule__URL_PROTOCOL__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__URL_PROTOCOL__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURL_PROTOCOLAccess().getLETTER_NO_VXParserRuleCall_0()); }
	ruleLETTER_NO_VX
	{ after(grammarAccess.getURL_PROTOCOLAccess().getLETTER_NO_VXParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__URL_PROTOCOL__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URL_PROTOCOL__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__URL_PROTOCOL__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	(
		{ before(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); }
		(rule__URL_PROTOCOL__Alternatives_1)
		{ after(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); }
	)
	(
		{ before(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); }
		(rule__URL_PROTOCOL__Alternatives_1)*
		{ after(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1()); }
	)
)
;
finally {
	restoreStackSize(stackSize);
}


rule__URL__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URL__Group__0__Impl
	rule__URL__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__URL__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURLAccess().getAlternatives_0()); }
	(rule__URL__Alternatives_0)*
	{ after(grammarAccess.getURLAccess().getAlternatives_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__URL__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URL__Group__1__Impl
	rule__URL__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__URL__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURLAccess().getAlternatives_1()); }
	(rule__URL__Alternatives_1)
	{ after(grammarAccess.getURLAccess().getAlternatives_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__URL__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URL__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__URL__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURLAccess().getAlternatives_2()); }
	(rule__URL__Alternatives_2)*
	{ after(grammarAccess.getURLAccess().getAlternatives_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__URL_NO_VX__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URL_NO_VX__Group__0__Impl
	rule__URL_NO_VX__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__URL_NO_VX__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURL_NO_VXAccess().getAlternatives_0()); }
	(rule__URL_NO_VX__Alternatives_0)
	{ after(grammarAccess.getURL_NO_VXAccess().getAlternatives_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__URL_NO_VX__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URL_NO_VX__Group__1__Impl
	rule__URL_NO_VX__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__URL_NO_VX__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURL_NO_VXAccess().getAlternatives_1()); }
	(rule__URL_NO_VX__Alternatives_1)*
	{ after(grammarAccess.getURL_NO_VXAccess().getAlternatives_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__URL_NO_VX__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URL_NO_VX__Group__2__Impl
	rule__URL_NO_VX__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__URL_NO_VX__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURL_NO_VXAccess().getAlternatives_2()); }
	(rule__URL_NO_VX__Alternatives_2)
	{ after(grammarAccess.getURL_NO_VXAccess().getAlternatives_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__URL_NO_VX__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__URL_NO_VX__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__URL_NO_VX__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getURL_NO_VXAccess().getAlternatives_3()); }
	(rule__URL_NO_VX__Alternatives_3)*
	{ after(grammarAccess.getURL_NO_VXAccess().getAlternatives_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TAG__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAG__Group__0__Impl
	rule__TAG__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TAG__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTAGAccess().getLETTER_NO_VXParserRuleCall_0()); }
	ruleLETTER_NO_VX
	{ after(grammarAccess.getTAGAccess().getLETTER_NO_VXParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TAG__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TAG__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TAG__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	(
		{ before(grammarAccess.getTAGAccess().getAlternatives_1()); }
		(rule__TAG__Alternatives_1)
		{ after(grammarAccess.getTAGAccess().getAlternatives_1()); }
	)
	(
		{ before(grammarAccess.getTAGAccess().getAlternatives_1()); }
		(rule__TAG__Alternatives_1)*
		{ after(grammarAccess.getTAGAccess().getAlternatives_1()); }
	)
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl
	rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getDIGITSTerminalRuleCall_0()); }
	RULE_DIGITS
	{ after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getDIGITSTerminalRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	(
		{ before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getAlternatives_1()); }
		(rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1)
		{ after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getAlternatives_1()); }
	)
	(
		{ before(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getAlternatives_1()); }
		(rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1)*
		{ after(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getAlternatives_1()); }
	)
)
;
finally {
	restoreStackSize(stackSize);
}


rule__LocalPathVersionRequirement__LocalPathAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getLocalPathVersionRequirementAccess().getLocalPathPATHParserRuleCall_1_0()); }
		rulePATH
		{ after(grammarAccess.getLocalPathVersionRequirementAccess().getLocalPathPATHParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionRequirement__ProtocolAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getURLVersionRequirementAccess().getProtocolURL_PROTOCOLParserRuleCall_0_0()); }
		ruleURL_PROTOCOL
		{ after(grammarAccess.getURLVersionRequirementAccess().getProtocolURL_PROTOCOLParserRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionRequirement__UrlAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getURLVersionRequirementAccess().getUrlURLParserRuleCall_2_0()); }
		ruleURL
		{ after(grammarAccess.getURLVersionRequirementAccess().getUrlURLParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionRequirement__VersionSpecifierAssignment_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getURLVersionRequirementAccess().getVersionSpecifierURLVersionSpecifierParserRuleCall_3_1_0()); }
		ruleURLVersionSpecifier
		{ after(grammarAccess.getURLVersionRequirementAccess().getVersionSpecifierURLVersionSpecifierParserRuleCall_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionSpecifier__CommitISHAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getURLVersionSpecifierAccess().getCommitISHALPHA_NUMERIC_CHARS_START_WITH_DIGITSParserRuleCall_1_1_0()); }
		ruleALPHA_NUMERIC_CHARS_START_WITH_DIGITS
		{ after(grammarAccess.getURLVersionSpecifierAccess().getCommitISHALPHA_NUMERIC_CHARS_START_WITH_DIGITSParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__URLVersionSpecifier__CommitISHAssignment_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getURLVersionSpecifierAccess().getCommitISHALPHA_NUMERIC_CHARSParserRuleCall_2_1_0()); }
		ruleALPHA_NUMERIC_CHARS
		{ after(grammarAccess.getURLVersionSpecifierAccess().getCommitISHALPHA_NUMERIC_CHARSParserRuleCall_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__URLSemver__WithSemverTagAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getURLSemverAccess().getWithSemverTagSEMVER_TAGParserRuleCall_1_0()); }
		ruleSEMVER_TAG
		{ after(grammarAccess.getURLSemverAccess().getWithSemverTagSEMVER_TAGParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__URLSemver__SimpleVersionAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getURLSemverAccess().getSimpleVersionSimpleVersionParserRuleCall_2_0()); }
		ruleSimpleVersion
		{ after(grammarAccess.getURLSemverAccess().getSimpleVersionSimpleVersionParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TagVersionRequirement__TagNameAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTagVersionRequirementAccess().getTagNameTAGParserRuleCall_0()); }
		ruleTAG
		{ after(grammarAccess.getTagVersionRequirementAccess().getTagNameTAGParserRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__GitHubVersionRequirement__GithubUrlAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlURL_NO_VXParserRuleCall_0_0()); }
		ruleURL_NO_VX
		{ after(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlURL_NO_VXParserRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__GitHubVersionRequirement__CommitISHAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getGitHubVersionRequirementAccess().getCommitISHALPHA_NUMERIC_CHARSParserRuleCall_1_1_0()); }
		ruleALPHA_NUMERIC_CHARS
		{ after(grammarAccess.getGitHubVersionRequirementAccess().getCommitISHALPHA_NUMERIC_CHARSParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeSetRequirement__RangesAssignment_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionRangeSetRequirementAccess().getRangesVersionRangeParserRuleCall_1_0_0()); }
		ruleVersionRange
		{ after(grammarAccess.getVersionRangeSetRequirementAccess().getRangesVersionRangeParserRuleCall_1_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeSetRequirement__RangesAssignment_1_1_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionRangeSetRequirementAccess().getRangesVersionRangeParserRuleCall_1_1_3_0()); }
		ruleVersionRange
		{ after(grammarAccess.getVersionRangeSetRequirementAccess().getRangesVersionRangeParserRuleCall_1_1_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__HyphenVersionRange__FromAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getHyphenVersionRangeAccess().getFromVersionNumberParserRuleCall_1_0()); }
		ruleVersionNumber
		{ after(grammarAccess.getHyphenVersionRangeAccess().getFromVersionNumberParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__HyphenVersionRange__ToAssignment_5
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getHyphenVersionRangeAccess().getToVersionNumberParserRuleCall_5_0()); }
		ruleVersionNumber
		{ after(grammarAccess.getHyphenVersionRangeAccess().getToVersionNumberParserRuleCall_5_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeContraint__VersionConstraintsAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsSimpleVersionParserRuleCall_1_0()); }
		ruleSimpleVersion
		{ after(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsSimpleVersionParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionRangeContraint__VersionConstraintsAssignment_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsSimpleVersionParserRuleCall_2_1_0()); }
		ruleSimpleVersion
		{ after(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsSimpleVersionParserRuleCall_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleVersion__ComparatorsAssignment_0_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSimpleVersionAccess().getComparatorsVersionComparatorEnumRuleCall_0_0_0()); }
		ruleVersionComparator
		{ after(grammarAccess.getSimpleVersionAccess().getComparatorsVersionComparatorEnumRuleCall_0_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleVersion__WithLetterVAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSimpleVersionAccess().getWithLetterVLETTER_VTerminalRuleCall_1_0()); }
		RULE_LETTER_V
		{ after(grammarAccess.getSimpleVersionAccess().getWithLetterVLETTER_VTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SimpleVersion__NumberAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSimpleVersionAccess().getNumberVersionNumberParserRuleCall_2_0()); }
		ruleVersionNumber
		{ after(grammarAccess.getSimpleVersionAccess().getNumberVersionNumberParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__MajorAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionNumberAccess().getMajorVersionPartParserRuleCall_0_0()); }
		ruleVersionPart
		{ after(grammarAccess.getVersionNumberAccess().getMajorVersionPartParserRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__MinorAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionNumberAccess().getMinorVersionPartParserRuleCall_1_1_0()); }
		ruleVersionPart
		{ after(grammarAccess.getVersionNumberAccess().getMinorVersionPartParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__PatchAssignment_1_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionNumberAccess().getPatchVersionPartParserRuleCall_1_2_1_0()); }
		ruleVersionPart
		{ after(grammarAccess.getVersionNumberAccess().getPatchVersionPartParserRuleCall_1_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__ExtendedAssignment_1_2_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionNumberAccess().getExtendedVersionPartParserRuleCall_1_2_2_1_0()); }
		ruleVersionPart
		{ after(grammarAccess.getVersionNumberAccess().getExtendedVersionPartParserRuleCall_1_2_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionNumber__QualifierAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionNumberAccess().getQualifierQualifierParserRuleCall_2_0()); }
		ruleQualifier
		{ after(grammarAccess.getVersionNumberAccess().getQualifierQualifierParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionPart__WildcardAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionPartAccess().getWildcardWILDCARDParserRuleCall_0_0()); }
		ruleWILDCARD
		{ after(grammarAccess.getVersionPartAccess().getWildcardWILDCARDParserRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__VersionPart__NumberRawAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getVersionPartAccess().getNumberRawDIGITSTerminalRuleCall_1_0()); }
		RULE_DIGITS
		{ after(grammarAccess.getVersionPartAccess().getNumberRawDIGITSTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Qualifier__PreReleaseAssignment_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getQualifierAccess().getPreReleaseQualifierTagParserRuleCall_0_1_0()); }
		ruleQualifierTag
		{ after(grammarAccess.getQualifierAccess().getPreReleaseQualifierTagParserRuleCall_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Qualifier__BuildMetadataAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getQualifierAccess().getBuildMetadataQualifierTagParserRuleCall_1_1_0()); }
		ruleQualifierTag
		{ after(grammarAccess.getQualifierAccess().getBuildMetadataQualifierTagParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Qualifier__PreReleaseAssignment_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getQualifierAccess().getPreReleaseQualifierTagParserRuleCall_2_1_0()); }
		ruleQualifierTag
		{ after(grammarAccess.getQualifierAccess().getPreReleaseQualifierTagParserRuleCall_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Qualifier__BuildMetadataAssignment_2_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getQualifierAccess().getBuildMetadataQualifierTagParserRuleCall_2_3_0()); }
		ruleQualifierTag
		{ after(grammarAccess.getQualifierAccess().getBuildMetadataQualifierTagParserRuleCall_2_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__QualifierTag__PartsAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getQualifierTagAccess().getPartsALPHA_NUMERIC_CHARSParserRuleCall_0_0()); }
		ruleALPHA_NUMERIC_CHARS
		{ after(grammarAccess.getQualifierTagAccess().getPartsALPHA_NUMERIC_CHARSParserRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__QualifierTag__PartsAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getQualifierTagAccess().getPartsALPHA_NUMERIC_CHARSParserRuleCall_1_1_0()); }
		ruleALPHA_NUMERIC_CHARS
		{ after(grammarAccess.getQualifierTagAccess().getPartsALPHA_NUMERIC_CHARSParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

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
