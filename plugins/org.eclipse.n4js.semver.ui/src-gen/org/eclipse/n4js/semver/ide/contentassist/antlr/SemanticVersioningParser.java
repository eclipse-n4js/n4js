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
import org.eclipse.n4js.semver.ide.contentassist.antlr.internal.InternalSemanticVersioningParser;
import org.eclipse.n4js.semver.services.SemanticVersioningGrammarAccess;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.AbstractContentAssistParser;

public class SemanticVersioningParser extends AbstractContentAssistParser {

	@Inject
	private SemanticVersioningGrammarAccess grammarAccess;

	private Map<AbstractElement, String> nameMappings;

	@Override
	protected InternalSemanticVersioningParser createParser() {
		InternalSemanticVersioningParser result = new InternalSemanticVersioningParser(null);
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
					put(grammarAccess.getXrAccess().getAlternatives(), "rule__Xr__Alternatives");
					put(grammarAccess.getQualifierAccess().getAlternatives(), "rule__Qualifier__Alternatives");
					put(grammarAccess.getVersionComparatorAccess().getAlternatives(), "rule__VersionComparator__Alternatives");
					put(grammarAccess.getVersionRangeSetAccess().getGroup(), "rule__VersionRangeSet__Group__0");
					put(grammarAccess.getVersionRangeSetAccess().getGroup_1(), "rule__VersionRangeSet__Group_1__0");
					put(grammarAccess.getHyphenVersionRangeAccess().getGroup(), "rule__HyphenVersionRange__Group__0");
					put(grammarAccess.getEnumeratedVersionRangeAccess().getGroup(), "rule__EnumeratedVersionRange__Group__0");
					put(grammarAccess.getSimpleVersionAccess().getGroup(), "rule__SimpleVersion__Group__0");
					put(grammarAccess.getVersionNumberAccess().getGroup(), "rule__VersionNumber__Group__0");
					put(grammarAccess.getVersionNumberAccess().getGroup_1(), "rule__VersionNumber__Group_1__0");
					put(grammarAccess.getVersionNumberAccess().getGroup_1_2(), "rule__VersionNumber__Group_1_2__0");
					put(grammarAccess.getQualifierAccess().getGroup_0(), "rule__Qualifier__Group_0__0");
					put(grammarAccess.getQualifierAccess().getGroup_1(), "rule__Qualifier__Group_1__0");
					put(grammarAccess.getPartsAccess().getGroup(), "rule__Parts__Group__0");
					put(grammarAccess.getPartsAccess().getGroup_1(), "rule__Parts__Group_1__0");
					put(grammarAccess.getVersionRangeSetAccess().getRangesAssignment_0(), "rule__VersionRangeSet__RangesAssignment_0");
					put(grammarAccess.getVersionRangeSetAccess().getRangesAssignment_1_1(), "rule__VersionRangeSet__RangesAssignment_1_1");
					put(grammarAccess.getHyphenVersionRangeAccess().getFromAssignment_1(), "rule__HyphenVersionRange__FromAssignment_1");
					put(grammarAccess.getHyphenVersionRangeAccess().getToAssignment_3(), "rule__HyphenVersionRange__ToAssignment_3");
					put(grammarAccess.getEnumeratedVersionRangeAccess().getSimpleVersionsAssignment_1(), "rule__EnumeratedVersionRange__SimpleVersionsAssignment_1");
					put(grammarAccess.getSimpleVersionAccess().getComparatorAssignment_0(), "rule__SimpleVersion__ComparatorAssignment_0");
					put(grammarAccess.getSimpleVersionAccess().getHasTildeAssignment_1(), "rule__SimpleVersion__HasTildeAssignment_1");
					put(grammarAccess.getSimpleVersionAccess().getHasCaretAssignment_2(), "rule__SimpleVersion__HasCaretAssignment_2");
					put(grammarAccess.getSimpleVersionAccess().getNumberAssignment_3(), "rule__SimpleVersion__NumberAssignment_3");
					put(grammarAccess.getVersionNumberAccess().getMajorAssignment_0(), "rule__VersionNumber__MajorAssignment_0");
					put(grammarAccess.getVersionNumberAccess().getMinorAssignment_1_1(), "rule__VersionNumber__MinorAssignment_1_1");
					put(grammarAccess.getVersionNumberAccess().getPathAssignment_1_2_1(), "rule__VersionNumber__PathAssignment_1_2_1");
					put(grammarAccess.getVersionNumberAccess().getQualifierAssignment_1_2_2(), "rule__VersionNumber__QualifierAssignment_1_2_2");
					put(grammarAccess.getQualifierAccess().getPreReleaseAssignment_0_1(), "rule__Qualifier__PreReleaseAssignment_0_1");
					put(grammarAccess.getQualifierAccess().getBuildMetadataAssignment_1_1(), "rule__Qualifier__BuildMetadataAssignment_1_1");
				}
			};
		}
		return nameMappings.get(element);
	}
			
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_EOL" };
	}

	public SemanticVersioningGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(SemanticVersioningGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
