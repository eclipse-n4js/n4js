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
package org.eclipse.n4js.n4mf.ui.contentassist.antlr;

import com.google.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.n4js.n4mf.services.N4MFGrammarAccess;
import org.eclipse.n4js.n4mf.ui.contentassist.antlr.internal.InternalN4MFParser;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.AbstractContentAssistParser;

public class N4MFParser extends AbstractContentAssistParser {

	@Inject
	private N4MFGrammarAccess grammarAccess;

	private Map<AbstractElement, String> nameMappings;

	@Override
	protected InternalN4MFParser createParser() {
		InternalN4MFParser result = new InternalN4MFParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}

	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getVersionConstraintAccess().getAlternatives(), "rule__VersionConstraint__Alternatives");
					put(grammarAccess.getVersionConstraintAccess().getAlternatives_0_0(), "rule__VersionConstraint__Alternatives_0_0");
					put(grammarAccess.getVersionConstraintAccess().getAlternatives_0_2(), "rule__VersionConstraint__Alternatives_0_2");
					put(grammarAccess.getVersionConstraintAccess().getAlternatives_0_2_0_2(), "rule__VersionConstraint__Alternatives_0_2_0_2");
					put(grammarAccess.getN4mfIdentifierAccess().getAlternatives(), "rule__N4mfIdentifier__Alternatives");
					put(grammarAccess.getProjectTypeAccess().getAlternatives(), "rule__ProjectType__Alternatives");
					put(grammarAccess.getSourceContainerTypeAccess().getAlternatives(), "rule__SourceContainerType__Alternatives");
					put(grammarAccess.getModuleFilterTypeAccess().getAlternatives(), "rule__ModuleFilterType__Alternatives");
					put(grammarAccess.getProjectDependencyScopeAccess().getAlternatives(), "rule__ProjectDependencyScope__Alternatives");
					put(grammarAccess.getModuleLoaderAccess().getAlternatives(), "rule__ModuleLoader__Alternatives");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_0(), "rule__ProjectDescription__Group_0__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_1(), "rule__ProjectDescription__Group_1__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_2(), "rule__ProjectDescription__Group_2__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_3(), "rule__ProjectDescription__Group_3__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_4(), "rule__ProjectDescription__Group_4__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_5(), "rule__ProjectDescription__Group_5__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_6(), "rule__ProjectDescription__Group_6__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_7(), "rule__ProjectDescription__Group_7__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_7_2(), "rule__ProjectDescription__Group_7_2__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_7_2_1(), "rule__ProjectDescription__Group_7_2_1__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_8(), "rule__ProjectDescription__Group_8__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_8_2(), "rule__ProjectDescription__Group_8_2__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_8_2_1(), "rule__ProjectDescription__Group_8_2_1__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_9(), "rule__ProjectDescription__Group_9__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_9_2(), "rule__ProjectDescription__Group_9_2__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_9_2_1(), "rule__ProjectDescription__Group_9_2_1__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_10(), "rule__ProjectDescription__Group_10__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_11(), "rule__ProjectDescription__Group_11__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_11_2(), "rule__ProjectDescription__Group_11_2__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_11_2_1(), "rule__ProjectDescription__Group_11_2_1__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_12(), "rule__ProjectDescription__Group_12__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_12_2(), "rule__ProjectDescription__Group_12_2__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_12_2_1(), "rule__ProjectDescription__Group_12_2_1__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_13(), "rule__ProjectDescription__Group_13__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_14(), "rule__ProjectDescription__Group_14__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_15(), "rule__ProjectDescription__Group_15__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_15_3(), "rule__ProjectDescription__Group_15_3__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_16(), "rule__ProjectDescription__Group_16__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_16_3(), "rule__ProjectDescription__Group_16_3__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_17(), "rule__ProjectDescription__Group_17__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_18(), "rule__ProjectDescription__Group_18__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_19(), "rule__ProjectDescription__Group_19__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_19_2(), "rule__ProjectDescription__Group_19_2__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_19_2_1(), "rule__ProjectDescription__Group_19_2_1__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_20(), "rule__ProjectDescription__Group_20__0");
					put(grammarAccess.getDeclaredVersionAccess().getGroup(), "rule__DeclaredVersion__Group__0");
					put(grammarAccess.getDeclaredVersionAccess().getGroup_1(), "rule__DeclaredVersion__Group_1__0");
					put(grammarAccess.getDeclaredVersionAccess().getGroup_1_2(), "rule__DeclaredVersion__Group_1_2__0");
					put(grammarAccess.getDeclaredVersionAccess().getGroup_2(), "rule__DeclaredVersion__Group_2__0");
					put(grammarAccess.getSourceContainerDescriptionAccess().getGroup(), "rule__SourceContainerDescription__Group__0");
					put(grammarAccess.getSourceContainerDescriptionAccess().getGroup_3(), "rule__SourceContainerDescription__Group_3__0");
					put(grammarAccess.getModuleFilterAccess().getGroup(), "rule__ModuleFilter__Group__0");
					put(grammarAccess.getModuleFilterAccess().getGroup_3(), "rule__ModuleFilter__Group_3__0");
					put(grammarAccess.getBootstrapModuleAccess().getGroup(), "rule__BootstrapModule__Group__0");
					put(grammarAccess.getBootstrapModuleAccess().getGroup_1(), "rule__BootstrapModule__Group_1__0");
					put(grammarAccess.getModuleFilterSpecifierAccess().getGroup(), "rule__ModuleFilterSpecifier__Group__0");
					put(grammarAccess.getModuleFilterSpecifierAccess().getGroup_1(), "rule__ModuleFilterSpecifier__Group_1__0");
					put(grammarAccess.getProjectDependencyAccess().getGroup(), "rule__ProjectDependency__Group__0");
					put(grammarAccess.getProjectIdWithOptionalVendorAccess().getGroup(), "rule__ProjectIdWithOptionalVendor__Group__0");
					put(grammarAccess.getProjectIdWithOptionalVendorAccess().getGroup_0(), "rule__ProjectIdWithOptionalVendor__Group_0__0");
					put(grammarAccess.getVersionConstraintAccess().getGroup_0(), "rule__VersionConstraint__Group_0__0");
					put(grammarAccess.getVersionConstraintAccess().getGroup_0_2_0(), "rule__VersionConstraint__Group_0_2_0__0");
					put(grammarAccess.getN4mfIdentifierAccess().getGroup_11(), "rule__N4mfIdentifier__Group_11__0");
					put(grammarAccess.getN4mfIdentifierAccess().getGroup_15(), "rule__N4mfIdentifier__Group_15__0");
					put(grammarAccess.getProjectDescriptionAccess().getProjectIdAssignment_0_2(), "rule__ProjectDescription__ProjectIdAssignment_0_2");
					put(grammarAccess.getProjectDescriptionAccess().getProjectTypeAssignment_1_2(), "rule__ProjectDescription__ProjectTypeAssignment_1_2");
					put(grammarAccess.getProjectDescriptionAccess().getProjectVersionAssignment_2_2(), "rule__ProjectDescription__ProjectVersionAssignment_2_2");
					put(grammarAccess.getProjectDescriptionAccess().getVendorIdAssignment_3_2(), "rule__ProjectDescription__VendorIdAssignment_3_2");
					put(grammarAccess.getProjectDescriptionAccess().getVendorNameAssignment_4_2(), "rule__ProjectDescription__VendorNameAssignment_4_2");
					put(grammarAccess.getProjectDescriptionAccess().getMainModuleAssignment_5_2(), "rule__ProjectDescription__MainModuleAssignment_5_2");
					put(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentAssignment_6_2(), "rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6_2");
					put(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesAssignment_7_2_0(), "rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_0");
					put(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesAssignment_7_2_1_1(), "rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7_2_1_1");
					put(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesAssignment_8_2_0(), "rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_0");
					put(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesAssignment_8_2_1_1(), "rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8_2_1_1");
					put(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesAssignment_9_2_0(), "rule__ProjectDescription__ProjectDependenciesAssignment_9_2_0");
					put(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesAssignment_9_2_1_1(), "rule__ProjectDescription__ProjectDependenciesAssignment_9_2_1_1");
					put(grammarAccess.getProjectDescriptionAccess().getImplementationIdAssignment_10_2(), "rule__ProjectDescription__ImplementationIdAssignment_10_2");
					put(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsAssignment_11_2_0(), "rule__ProjectDescription__ImplementedProjectsAssignment_11_2_0");
					put(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsAssignment_11_2_1_1(), "rule__ProjectDescription__ImplementedProjectsAssignment_11_2_1_1");
					put(grammarAccess.getProjectDescriptionAccess().getInitModulesAssignment_12_2_0(), "rule__ProjectDescription__InitModulesAssignment_12_2_0");
					put(grammarAccess.getProjectDescriptionAccess().getInitModulesAssignment_12_2_1_1(), "rule__ProjectDescription__InitModulesAssignment_12_2_1_1");
					put(grammarAccess.getProjectDescriptionAccess().getExecModuleAssignment_13_2(), "rule__ProjectDescription__ExecModuleAssignment_13_2");
					put(grammarAccess.getProjectDescriptionAccess().getOutputPathRawAssignment_14_2(), "rule__ProjectDescription__OutputPathRawAssignment_14_2");
					put(grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawAssignment_15_2(), "rule__ProjectDescription__LibraryPathsRawAssignment_15_2");
					put(grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawAssignment_15_3_1(), "rule__ProjectDescription__LibraryPathsRawAssignment_15_3_1");
					put(grammarAccess.getProjectDescriptionAccess().getResourcePathsRawAssignment_16_2(), "rule__ProjectDescription__ResourcePathsRawAssignment_16_2");
					put(grammarAccess.getProjectDescriptionAccess().getResourcePathsRawAssignment_16_3_1(), "rule__ProjectDescription__ResourcePathsRawAssignment_16_3_1");
					put(grammarAccess.getProjectDescriptionAccess().getSourceContainersAssignment_17_2(), "rule__ProjectDescription__SourceContainersAssignment_17_2");
					put(grammarAccess.getProjectDescriptionAccess().getModuleFiltersAssignment_18_2(), "rule__ProjectDescription__ModuleFiltersAssignment_18_2");
					put(grammarAccess.getProjectDescriptionAccess().getTestedProjectsAssignment_19_2_0(), "rule__ProjectDescription__TestedProjectsAssignment_19_2_0");
					put(grammarAccess.getProjectDescriptionAccess().getTestedProjectsAssignment_19_2_1_1(), "rule__ProjectDescription__TestedProjectsAssignment_19_2_1_1");
					put(grammarAccess.getProjectDescriptionAccess().getModuleLoaderAssignment_20_2(), "rule__ProjectDescription__ModuleLoaderAssignment_20_2");
					put(grammarAccess.getDeclaredVersionAccess().getMajorAssignment_0(), "rule__DeclaredVersion__MajorAssignment_0");
					put(grammarAccess.getDeclaredVersionAccess().getMinorAssignment_1_1(), "rule__DeclaredVersion__MinorAssignment_1_1");
					put(grammarAccess.getDeclaredVersionAccess().getMicroAssignment_1_2_1(), "rule__DeclaredVersion__MicroAssignment_1_2_1");
					put(grammarAccess.getDeclaredVersionAccess().getQualifierAssignment_2_1(), "rule__DeclaredVersion__QualifierAssignment_2_1");
					put(grammarAccess.getSourceContainerDescriptionAccess().getSourceContainerTypeAssignment_0(), "rule__SourceContainerDescription__SourceContainerTypeAssignment_0");
					put(grammarAccess.getSourceContainerDescriptionAccess().getPathsRawAssignment_2(), "rule__SourceContainerDescription__PathsRawAssignment_2");
					put(grammarAccess.getSourceContainerDescriptionAccess().getPathsRawAssignment_3_1(), "rule__SourceContainerDescription__PathsRawAssignment_3_1");
					put(grammarAccess.getModuleFilterAccess().getModuleFilterTypeAssignment_0(), "rule__ModuleFilter__ModuleFilterTypeAssignment_0");
					put(grammarAccess.getModuleFilterAccess().getModuleSpecifiersAssignment_2(), "rule__ModuleFilter__ModuleSpecifiersAssignment_2");
					put(grammarAccess.getModuleFilterAccess().getModuleSpecifiersAssignment_3_1(), "rule__ModuleFilter__ModuleSpecifiersAssignment_3_1");
					put(grammarAccess.getBootstrapModuleAccess().getModuleSpecifierWithWildcardAssignment_0(), "rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0");
					put(grammarAccess.getBootstrapModuleAccess().getSourcePathAssignment_1_1(), "rule__BootstrapModule__SourcePathAssignment_1_1");
					put(grammarAccess.getModuleFilterSpecifierAccess().getModuleSpecifierWithWildcardAssignment_0(), "rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0");
					put(grammarAccess.getModuleFilterSpecifierAccess().getSourcePathAssignment_1_1(), "rule__ModuleFilterSpecifier__SourcePathAssignment_1_1");
					put(grammarAccess.getProjectDependencyAccess().getVersionConstraintAssignment_1(), "rule__ProjectDependency__VersionConstraintAssignment_1");
					put(grammarAccess.getProjectDependencyAccess().getDeclaredScopeAssignment_2(), "rule__ProjectDependency__DeclaredScopeAssignment_2");
					put(grammarAccess.getProjectIdWithOptionalVendorAccess().getDeclaredVendorIdAssignment_0_0(), "rule__ProjectIdWithOptionalVendor__DeclaredVendorIdAssignment_0_0");
					put(grammarAccess.getProjectIdWithOptionalVendorAccess().getProjectIdAssignment_1(), "rule__ProjectIdWithOptionalVendor__ProjectIdAssignment_1");
					put(grammarAccess.getVersionConstraintAccess().getExclLowerBoundAssignment_0_0_0(), "rule__VersionConstraint__ExclLowerBoundAssignment_0_0_0");
					put(grammarAccess.getVersionConstraintAccess().getLowerVersionAssignment_0_1(), "rule__VersionConstraint__LowerVersionAssignment_0_1");
					put(grammarAccess.getVersionConstraintAccess().getUpperVersionAssignment_0_2_0_1(), "rule__VersionConstraint__UpperVersionAssignment_0_2_0_1");
					put(grammarAccess.getVersionConstraintAccess().getExclUpperBoundAssignment_0_2_0_2_0(), "rule__VersionConstraint__ExclUpperBoundAssignment_0_2_0_2_0");
					put(grammarAccess.getVersionConstraintAccess().getLowerVersionAssignment_1(), "rule__VersionConstraint__LowerVersionAssignment_1");
					put(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), "rule__ProjectDescription__UnorderedGroup");
				}
			};
		}
		return nameMappings.get(element);
	}
			
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}

	public N4MFGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(N4MFGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
