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
package org.eclipse.n4js.semver.ide.contentassist.antlr;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Map;
import org.eclipse.n4js.semver.ide.contentassist.antlr.internal.InternalSemverParser;
import org.eclipse.n4js.semver.services.SemverGrammarAccess;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.AbstractContentAssistParser;

public class SemverParser extends AbstractContentAssistParser {

	@Singleton
	public static final class NameMappings {
		
		private final Map<AbstractElement, String> mappings;
		
		@Inject
		public NameMappings(SemverGrammarAccess grammarAccess) {
			ImmutableMap.Builder<AbstractElement, String> builder = ImmutableMap.builder();
			init(builder, grammarAccess);
			this.mappings = builder.build();
		}
		
		public String getRuleName(AbstractElement element) {
			return mappings.get(element);
		}
		
		private static void init(ImmutableMap.Builder<AbstractElement, String> builder, SemverGrammarAccess grammarAccess) {
			builder.put(grammarAccess.getNPMVersionRequirementAccess().getAlternatives(), "rule__NPMVersionRequirement__Alternatives");
			builder.put(grammarAccess.getNPMVersionRequirementAccess().getAlternatives_1_0(), "rule__NPMVersionRequirement__Alternatives_1_0");
			builder.put(grammarAccess.getNPMVersionRequirementAccess().getAlternatives_1_0_1(), "rule__NPMVersionRequirement__Alternatives_1_0_1");
			builder.put(grammarAccess.getURLVersionSpecifierAccess().getAlternatives(), "rule__URLVersionSpecifier__Alternatives");
			builder.put(grammarAccess.getVersionRangeAccess().getAlternatives(), "rule__VersionRange__Alternatives");
			builder.put(grammarAccess.getVersionPartAccess().getAlternatives(), "rule__VersionPart__Alternatives");
			builder.put(grammarAccess.getQualifierAccess().getAlternatives(), "rule__Qualifier__Alternatives");
			builder.put(grammarAccess.getPATHAccess().getAlternatives(), "rule__PATH__Alternatives");
			builder.put(grammarAccess.getURL_PROTOCOLAccess().getAlternatives_1(), "rule__URL_PROTOCOL__Alternatives_1");
			builder.put(grammarAccess.getURLAccess().getAlternatives_0(), "rule__URL__Alternatives_0");
			builder.put(grammarAccess.getURLAccess().getAlternatives_1(), "rule__URL__Alternatives_1");
			builder.put(grammarAccess.getURLAccess().getAlternatives_2(), "rule__URL__Alternatives_2");
			builder.put(grammarAccess.getURL_NO_VXAccess().getAlternatives_0(), "rule__URL_NO_VX__Alternatives_0");
			builder.put(grammarAccess.getURL_NO_VXAccess().getAlternatives_1(), "rule__URL_NO_VX__Alternatives_1");
			builder.put(grammarAccess.getURL_NO_VXAccess().getAlternatives_2(), "rule__URL_NO_VX__Alternatives_2");
			builder.put(grammarAccess.getURL_NO_VXAccess().getAlternatives_3(), "rule__URL_NO_VX__Alternatives_3");
			builder.put(grammarAccess.getTAGAccess().getAlternatives_1(), "rule__TAG__Alternatives_1");
			builder.put(grammarAccess.getALPHA_NUMERIC_CHARSAccess().getAlternatives(), "rule__ALPHA_NUMERIC_CHARS__Alternatives");
			builder.put(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getAlternatives_1(), "rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Alternatives_1");
			builder.put(grammarAccess.getWILDCARDAccess().getAlternatives(), "rule__WILDCARD__Alternatives");
			builder.put(grammarAccess.getLETTERAccess().getAlternatives(), "rule__LETTER__Alternatives");
			builder.put(grammarAccess.getLETTER_NO_VXAccess().getAlternatives(), "rule__LETTER_NO_VX__Alternatives");
			builder.put(grammarAccess.getVersionComparatorAccess().getAlternatives(), "rule__VersionComparator__Alternatives");
			builder.put(grammarAccess.getNPMVersionRequirementAccess().getGroup_0(), "rule__NPMVersionRequirement__Group_0__0");
			builder.put(grammarAccess.getNPMVersionRequirementAccess().getGroup_1(), "rule__NPMVersionRequirement__Group_1__0");
			builder.put(grammarAccess.getLocalPathVersionRequirementAccess().getGroup(), "rule__LocalPathVersionRequirement__Group__0");
			builder.put(grammarAccess.getURLVersionRequirementAccess().getGroup(), "rule__URLVersionRequirement__Group__0");
			builder.put(grammarAccess.getURLVersionRequirementAccess().getGroup_1(), "rule__URLVersionRequirement__Group_1__0");
			builder.put(grammarAccess.getURLVersionRequirementAccess().getGroup_3(), "rule__URLVersionRequirement__Group_3__0");
			builder.put(grammarAccess.getURLVersionSpecifierAccess().getGroup_0(), "rule__URLVersionSpecifier__Group_0__0");
			builder.put(grammarAccess.getURLVersionSpecifierAccess().getGroup_1(), "rule__URLVersionSpecifier__Group_1__0");
			builder.put(grammarAccess.getURLVersionSpecifierAccess().getGroup_2(), "rule__URLVersionSpecifier__Group_2__0");
			builder.put(grammarAccess.getURLSemverAccess().getGroup(), "rule__URLSemver__Group__0");
			builder.put(grammarAccess.getGitHubVersionRequirementAccess().getGroup(), "rule__GitHubVersionRequirement__Group__0");
			builder.put(grammarAccess.getGitHubVersionRequirementAccess().getGroup_1(), "rule__GitHubVersionRequirement__Group_1__0");
			builder.put(grammarAccess.getVersionRangeSetRequirementAccess().getGroup(), "rule__VersionRangeSetRequirement__Group__0");
			builder.put(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1(), "rule__VersionRangeSetRequirement__Group_1__0");
			builder.put(grammarAccess.getVersionRangeSetRequirementAccess().getGroup_1_1(), "rule__VersionRangeSetRequirement__Group_1_1__0");
			builder.put(grammarAccess.getHyphenVersionRangeAccess().getGroup(), "rule__HyphenVersionRange__Group__0");
			builder.put(grammarAccess.getVersionRangeContraintAccess().getGroup(), "rule__VersionRangeContraint__Group__0");
			builder.put(grammarAccess.getVersionRangeContraintAccess().getGroup_2(), "rule__VersionRangeContraint__Group_2__0");
			builder.put(grammarAccess.getSimpleVersionAccess().getGroup(), "rule__SimpleVersion__Group__0");
			builder.put(grammarAccess.getSimpleVersionAccess().getGroup_0(), "rule__SimpleVersion__Group_0__0");
			builder.put(grammarAccess.getVersionNumberAccess().getGroup(), "rule__VersionNumber__Group__0");
			builder.put(grammarAccess.getVersionNumberAccess().getGroup_1(), "rule__VersionNumber__Group_1__0");
			builder.put(grammarAccess.getVersionNumberAccess().getGroup_1_2(), "rule__VersionNumber__Group_1_2__0");
			builder.put(grammarAccess.getVersionNumberAccess().getGroup_1_2_2(), "rule__VersionNumber__Group_1_2_2__0");
			builder.put(grammarAccess.getQualifierAccess().getGroup_0(), "rule__Qualifier__Group_0__0");
			builder.put(grammarAccess.getQualifierAccess().getGroup_1(), "rule__Qualifier__Group_1__0");
			builder.put(grammarAccess.getQualifierAccess().getGroup_2(), "rule__Qualifier__Group_2__0");
			builder.put(grammarAccess.getQualifierTagAccess().getGroup(), "rule__QualifierTag__Group__0");
			builder.put(grammarAccess.getQualifierTagAccess().getGroup_1(), "rule__QualifierTag__Group_1__0");
			builder.put(grammarAccess.getFILE_TAGAccess().getGroup(), "rule__FILE_TAG__Group__0");
			builder.put(grammarAccess.getSEMVER_TAGAccess().getGroup(), "rule__SEMVER_TAG__Group__0");
			builder.put(grammarAccess.getURL_PROTOCOLAccess().getGroup(), "rule__URL_PROTOCOL__Group__0");
			builder.put(grammarAccess.getURLAccess().getGroup(), "rule__URL__Group__0");
			builder.put(grammarAccess.getURL_NO_VXAccess().getGroup(), "rule__URL_NO_VX__Group__0");
			builder.put(grammarAccess.getTAGAccess().getGroup(), "rule__TAG__Group__0");
			builder.put(grammarAccess.getALPHA_NUMERIC_CHARS_START_WITH_DIGITSAccess().getGroup(), "rule__ALPHA_NUMERIC_CHARS_START_WITH_DIGITS__Group__0");
			builder.put(grammarAccess.getLocalPathVersionRequirementAccess().getLocalPathAssignment_1(), "rule__LocalPathVersionRequirement__LocalPathAssignment_1");
			builder.put(grammarAccess.getURLVersionRequirementAccess().getProtocolAssignment_0(), "rule__URLVersionRequirement__ProtocolAssignment_0");
			builder.put(grammarAccess.getURLVersionRequirementAccess().getUrlAssignment_2(), "rule__URLVersionRequirement__UrlAssignment_2");
			builder.put(grammarAccess.getURLVersionRequirementAccess().getVersionSpecifierAssignment_3_1(), "rule__URLVersionRequirement__VersionSpecifierAssignment_3_1");
			builder.put(grammarAccess.getURLVersionSpecifierAccess().getCommitISHAssignment_1_1(), "rule__URLVersionSpecifier__CommitISHAssignment_1_1");
			builder.put(grammarAccess.getURLVersionSpecifierAccess().getCommitISHAssignment_2_1(), "rule__URLVersionSpecifier__CommitISHAssignment_2_1");
			builder.put(grammarAccess.getURLSemverAccess().getWithSemverTagAssignment_1(), "rule__URLSemver__WithSemverTagAssignment_1");
			builder.put(grammarAccess.getURLSemverAccess().getSimpleVersionAssignment_2(), "rule__URLSemver__SimpleVersionAssignment_2");
			builder.put(grammarAccess.getTagVersionRequirementAccess().getTagNameAssignment(), "rule__TagVersionRequirement__TagNameAssignment");
			builder.put(grammarAccess.getGitHubVersionRequirementAccess().getGithubUrlAssignment_0(), "rule__GitHubVersionRequirement__GithubUrlAssignment_0");
			builder.put(grammarAccess.getGitHubVersionRequirementAccess().getCommitISHAssignment_1_1(), "rule__GitHubVersionRequirement__CommitISHAssignment_1_1");
			builder.put(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_0(), "rule__VersionRangeSetRequirement__RangesAssignment_1_0");
			builder.put(grammarAccess.getVersionRangeSetRequirementAccess().getRangesAssignment_1_1_3(), "rule__VersionRangeSetRequirement__RangesAssignment_1_1_3");
			builder.put(grammarAccess.getHyphenVersionRangeAccess().getFromAssignment_1(), "rule__HyphenVersionRange__FromAssignment_1");
			builder.put(grammarAccess.getHyphenVersionRangeAccess().getToAssignment_5(), "rule__HyphenVersionRange__ToAssignment_5");
			builder.put(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_1(), "rule__VersionRangeContraint__VersionConstraintsAssignment_1");
			builder.put(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_2_1(), "rule__VersionRangeContraint__VersionConstraintsAssignment_2_1");
			builder.put(grammarAccess.getSimpleVersionAccess().getComparatorsAssignment_0_0(), "rule__SimpleVersion__ComparatorsAssignment_0_0");
			builder.put(grammarAccess.getSimpleVersionAccess().getWithLetterVAssignment_1(), "rule__SimpleVersion__WithLetterVAssignment_1");
			builder.put(grammarAccess.getSimpleVersionAccess().getNumberAssignment_2(), "rule__SimpleVersion__NumberAssignment_2");
			builder.put(grammarAccess.getVersionNumberAccess().getMajorAssignment_0(), "rule__VersionNumber__MajorAssignment_0");
			builder.put(grammarAccess.getVersionNumberAccess().getMinorAssignment_1_1(), "rule__VersionNumber__MinorAssignment_1_1");
			builder.put(grammarAccess.getVersionNumberAccess().getPatchAssignment_1_2_1(), "rule__VersionNumber__PatchAssignment_1_2_1");
			builder.put(grammarAccess.getVersionNumberAccess().getExtendedAssignment_1_2_2_1(), "rule__VersionNumber__ExtendedAssignment_1_2_2_1");
			builder.put(grammarAccess.getVersionNumberAccess().getQualifierAssignment_2(), "rule__VersionNumber__QualifierAssignment_2");
			builder.put(grammarAccess.getVersionPartAccess().getWildcardAssignment_0(), "rule__VersionPart__WildcardAssignment_0");
			builder.put(grammarAccess.getVersionPartAccess().getNumberRawAssignment_1(), "rule__VersionPart__NumberRawAssignment_1");
			builder.put(grammarAccess.getQualifierAccess().getPreReleaseAssignment_0_1(), "rule__Qualifier__PreReleaseAssignment_0_1");
			builder.put(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_1_1(), "rule__Qualifier__BuildMetadataAssignment_1_1");
			builder.put(grammarAccess.getQualifierAccess().getPreReleaseAssignment_2_1(), "rule__Qualifier__PreReleaseAssignment_2_1");
			builder.put(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_2_3(), "rule__Qualifier__BuildMetadataAssignment_2_3");
			builder.put(grammarAccess.getQualifierTagAccess().getPartsAssignment_0(), "rule__QualifierTag__PartsAssignment_0");
			builder.put(grammarAccess.getQualifierTagAccess().getPartsAssignment_1_1(), "rule__QualifierTag__PartsAssignment_1_1");
		}
	}
	
	@Inject
	private NameMappings nameMappings;

	@Inject
	private SemverGrammarAccess grammarAccess;

	@Override
	protected InternalSemverParser createParser() {
		InternalSemverParser result = new InternalSemverParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}

	@Override
	protected String getRuleName(AbstractElement element) {
		return nameMappings.getRuleName(element);
	}

	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_EOL" };
	}

	public SemverGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(SemverGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
	public NameMappings getNameMappings() {
		return nameMappings;
	}
	
	public void setNameMappings(NameMappings nameMappings) {
		this.nameMappings = nameMappings;
	}
}
