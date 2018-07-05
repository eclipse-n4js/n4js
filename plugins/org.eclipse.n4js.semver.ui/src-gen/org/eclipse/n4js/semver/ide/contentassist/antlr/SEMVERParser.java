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

import com.google.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.n4js.semver.ide.contentassist.antlr.internal.InternalSEMVERParser;
import org.eclipse.n4js.semver.services.SEMVERGrammarAccess;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.AbstractContentAssistParser;

public class SEMVERParser extends AbstractContentAssistParser {

	@Inject
	private SEMVERGrammarAccess grammarAccess;

	private Map<AbstractElement, String> nameMappings;

	@Override
	protected InternalSEMVERParser createParser() {
		InternalSEMVERParser result = new InternalSEMVERParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}

	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getVersionRangeAccess().getAlternatives(), "rule__VersionRange__Alternatives");
					put(grammarAccess.getVersionPartAccess().getAlternatives(), "rule__VersionPart__Alternatives");
					put(grammarAccess.getQualifierAccess().getAlternatives(), "rule__Qualifier__Alternatives");
					put(grammarAccess.getWILDCARDAccess().getAlternatives(), "rule__WILDCARD__Alternatives");
					put(grammarAccess.getALPHA_NUMERIC_CHARAccess().getAlternatives_1(), "rule__ALPHA_NUMERIC_CHAR__Alternatives_1");
					put(grammarAccess.getVersionComparatorAccess().getAlternatives(), "rule__VersionComparator__Alternatives");
					put(grammarAccess.getVersionRangeSetAccess().getGroup(), "rule__VersionRangeSet__Group__0");
					put(grammarAccess.getVersionRangeSetAccess().getGroup_2(), "rule__VersionRangeSet__Group_2__0");
					put(grammarAccess.getVersionRangeSetAccess().getGroup_2_1(), "rule__VersionRangeSet__Group_2_1__0");
					put(grammarAccess.getHyphenVersionRangeAccess().getGroup(), "rule__HyphenVersionRange__Group__0");
					put(grammarAccess.getVersionRangeContraintAccess().getGroup(), "rule__VersionRangeContraint__Group__0");
					put(grammarAccess.getVersionRangeContraintAccess().getGroup_2(), "rule__VersionRangeContraint__Group_2__0");
					put(grammarAccess.getSimpleVersionAccess().getGroup(), "rule__SimpleVersion__Group__0");
					put(grammarAccess.getSimpleVersionAccess().getGroup_1(), "rule__SimpleVersion__Group_1__0");
					put(grammarAccess.getVersionNumberAccess().getGroup(), "rule__VersionNumber__Group__0");
					put(grammarAccess.getVersionNumberAccess().getGroup_1(), "rule__VersionNumber__Group_1__0");
					put(grammarAccess.getVersionNumberAccess().getGroup_1_2(), "rule__VersionNumber__Group_1_2__0");
					put(grammarAccess.getVersionNumberAccess().getGroup_1_2_2(), "rule__VersionNumber__Group_1_2_2__0");
					put(grammarAccess.getQualifierAccess().getGroup_0(), "rule__Qualifier__Group_0__0");
					put(grammarAccess.getQualifierAccess().getGroup_1(), "rule__Qualifier__Group_1__0");
					put(grammarAccess.getQualifierAccess().getGroup_2(), "rule__Qualifier__Group_2__0");
					put(grammarAccess.getQualifierTagAccess().getGroup(), "rule__QualifierTag__Group__0");
					put(grammarAccess.getQualifierTagAccess().getGroup_1(), "rule__QualifierTag__Group_1__0");
					put(grammarAccess.getALPHA_NUMERIC_CHARAccess().getGroup(), "rule__ALPHA_NUMERIC_CHAR__Group__0");
					put(grammarAccess.getVersionRangeSetAccess().getRangesAssignment_2_0(), "rule__VersionRangeSet__RangesAssignment_2_0");
					put(grammarAccess.getVersionRangeSetAccess().getRangesAssignment_2_1_3(), "rule__VersionRangeSet__RangesAssignment_2_1_3");
					put(grammarAccess.getHyphenVersionRangeAccess().getFromAssignment_1(), "rule__HyphenVersionRange__FromAssignment_1");
					put(grammarAccess.getHyphenVersionRangeAccess().getToAssignment_5(), "rule__HyphenVersionRange__ToAssignment_5");
					put(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_1(), "rule__VersionRangeContraint__VersionConstraintsAssignment_1");
					put(grammarAccess.getVersionRangeContraintAccess().getVersionConstraintsAssignment_2_1(), "rule__VersionRangeContraint__VersionConstraintsAssignment_2_1");
					put(grammarAccess.getSimpleVersionAccess().getComparatorsAssignment_1_0(), "rule__SimpleVersion__ComparatorsAssignment_1_0");
					put(grammarAccess.getSimpleVersionAccess().getNumberAssignment_2(), "rule__SimpleVersion__NumberAssignment_2");
					put(grammarAccess.getVersionNumberAccess().getMajorAssignment_0(), "rule__VersionNumber__MajorAssignment_0");
					put(grammarAccess.getVersionNumberAccess().getMinorAssignment_1_1(), "rule__VersionNumber__MinorAssignment_1_1");
					put(grammarAccess.getVersionNumberAccess().getPatchAssignment_1_2_1(), "rule__VersionNumber__PatchAssignment_1_2_1");
					put(grammarAccess.getVersionNumberAccess().getExtendedAssignment_1_2_2_1(), "rule__VersionNumber__ExtendedAssignment_1_2_2_1");
					put(grammarAccess.getVersionNumberAccess().getQualifierAssignment_2(), "rule__VersionNumber__QualifierAssignment_2");
					put(grammarAccess.getVersionPartAccess().getWildcardAssignment_0(), "rule__VersionPart__WildcardAssignment_0");
					put(grammarAccess.getVersionPartAccess().getNumberRawAssignment_1(), "rule__VersionPart__NumberRawAssignment_1");
					put(grammarAccess.getQualifierAccess().getPreReleaseAssignment_0_1(), "rule__Qualifier__PreReleaseAssignment_0_1");
					put(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_1_1(), "rule__Qualifier__BuildMetadataAssignment_1_1");
					put(grammarAccess.getQualifierAccess().getPreReleaseAssignment_2_1(), "rule__Qualifier__PreReleaseAssignment_2_1");
					put(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_2_3(), "rule__Qualifier__BuildMetadataAssignment_2_3");
					put(grammarAccess.getQualifierTagAccess().getPartsAssignment_0(), "rule__QualifierTag__PartsAssignment_0");
					put(grammarAccess.getQualifierTagAccess().getPartsAssignment_1_1(), "rule__QualifierTag__PartsAssignment_1_1");
				}
			};
		}
		return nameMappings.get(element);
	}
			
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_EOL" };
	}

	public SEMVERGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(SEMVERGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
