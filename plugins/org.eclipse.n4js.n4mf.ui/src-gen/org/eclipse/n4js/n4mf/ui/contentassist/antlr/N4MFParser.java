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
					put(grammarAccess.getSourceFragmentTypeAccess().getAlternatives(), "rule__SourceFragmentType__Alternatives");
					put(grammarAccess.getModuleFilterTypeAccess().getAlternatives(), "rule__ModuleFilterType__Alternatives");
					put(grammarAccess.getProjectDependencyScopeAccess().getAlternatives(), "rule__ProjectDependencyScope__Alternatives");
					put(grammarAccess.getModuleLoaderAccess().getAlternatives(), "rule__ModuleLoader__Alternatives");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_0(), "rule__ProjectDescription__Group_0__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_1(), "rule__ProjectDescription__Group_1__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_2(), "rule__ProjectDescription__Group_2__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_3(), "rule__ProjectDescription__Group_3__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_4(), "rule__ProjectDescription__Group_4__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_5(), "rule__ProjectDescription__Group_5__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_10(), "rule__ProjectDescription__Group_10__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_14(), "rule__ProjectDescription__Group_14__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_15(), "rule__ProjectDescription__Group_15__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_15_3(), "rule__ProjectDescription__Group_15_3__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_16(), "rule__ProjectDescription__Group_16__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_16_3(), "rule__ProjectDescription__Group_16_3__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_17(), "rule__ProjectDescription__Group_17__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_18(), "rule__ProjectDescription__Group_18__0");
					put(grammarAccess.getProjectDescriptionAccess().getGroup_20(), "rule__ProjectDescription__Group_20__0");
					put(grammarAccess.getExecModuleAccess().getGroup(), "rule__ExecModule__Group__0");
					put(grammarAccess.getTestedProjectsAccess().getGroup(), "rule__TestedProjects__Group__0");
					put(grammarAccess.getTestedProjectsAccess().getGroup_3(), "rule__TestedProjects__Group_3__0");
					put(grammarAccess.getTestedProjectsAccess().getGroup_3_1(), "rule__TestedProjects__Group_3_1__0");
					put(grammarAccess.getInitModulesAccess().getGroup(), "rule__InitModules__Group__0");
					put(grammarAccess.getInitModulesAccess().getGroup_3(), "rule__InitModules__Group_3__0");
					put(grammarAccess.getInitModulesAccess().getGroup_3_1(), "rule__InitModules__Group_3_1__0");
					put(grammarAccess.getImplementedProjectsAccess().getGroup(), "rule__ImplementedProjects__Group__0");
					put(grammarAccess.getImplementedProjectsAccess().getGroup_3(), "rule__ImplementedProjects__Group_3__0");
					put(grammarAccess.getImplementedProjectsAccess().getGroup_3_1(), "rule__ImplementedProjects__Group_3_1__0");
					put(grammarAccess.getProjectDependenciesAccess().getGroup(), "rule__ProjectDependencies__Group__0");
					put(grammarAccess.getProjectDependenciesAccess().getGroup_3(), "rule__ProjectDependencies__Group_3__0");
					put(grammarAccess.getProjectDependenciesAccess().getGroup_3_1(), "rule__ProjectDependencies__Group_3_1__0");
					put(grammarAccess.getProvidedRuntimeLibrariesAccess().getGroup(), "rule__ProvidedRuntimeLibraries__Group__0");
					put(grammarAccess.getProvidedRuntimeLibrariesAccess().getGroup_3(), "rule__ProvidedRuntimeLibraries__Group_3__0");
					put(grammarAccess.getProvidedRuntimeLibrariesAccess().getGroup_3_1(), "rule__ProvidedRuntimeLibraries__Group_3_1__0");
					put(grammarAccess.getRequiredRuntimeLibrariesAccess().getGroup(), "rule__RequiredRuntimeLibraries__Group__0");
					put(grammarAccess.getRequiredRuntimeLibrariesAccess().getGroup_3(), "rule__RequiredRuntimeLibraries__Group_3__0");
					put(grammarAccess.getRequiredRuntimeLibrariesAccess().getGroup_3_1(), "rule__RequiredRuntimeLibraries__Group_3_1__0");
					put(grammarAccess.getExtendedRuntimeEnvironmentAccess().getGroup(), "rule__ExtendedRuntimeEnvironment__Group__0");
					put(grammarAccess.getDeclaredVersionAccess().getGroup(), "rule__DeclaredVersion__Group__0");
					put(grammarAccess.getDeclaredVersionAccess().getGroup_1(), "rule__DeclaredVersion__Group_1__0");
					put(grammarAccess.getDeclaredVersionAccess().getGroup_1_2(), "rule__DeclaredVersion__Group_1_2__0");
					put(grammarAccess.getDeclaredVersionAccess().getGroup_2(), "rule__DeclaredVersion__Group_2__0");
					put(grammarAccess.getSourceFragmentAccess().getGroup(), "rule__SourceFragment__Group__0");
					put(grammarAccess.getSourceFragmentAccess().getGroup_3(), "rule__SourceFragment__Group_3__0");
					put(grammarAccess.getModuleFilterAccess().getGroup(), "rule__ModuleFilter__Group__0");
					put(grammarAccess.getModuleFilterAccess().getGroup_3(), "rule__ModuleFilter__Group_3__0");
					put(grammarAccess.getBootstrapModuleAccess().getGroup(), "rule__BootstrapModule__Group__0");
					put(grammarAccess.getBootstrapModuleAccess().getGroup_1(), "rule__BootstrapModule__Group_1__0");
					put(grammarAccess.getModuleFilterSpecifierAccess().getGroup(), "rule__ModuleFilterSpecifier__Group__0");
					put(grammarAccess.getModuleFilterSpecifierAccess().getGroup_1(), "rule__ModuleFilterSpecifier__Group_1__0");
					put(grammarAccess.getProjectDependencyAccess().getGroup(), "rule__ProjectDependency__Group__0");
					put(grammarAccess.getSimpleProjectDescriptionAccess().getGroup(), "rule__SimpleProjectDescription__Group__0");
					put(grammarAccess.getSimpleProjectDescriptionAccess().getGroup_0(), "rule__SimpleProjectDescription__Group_0__0");
					put(grammarAccess.getVersionConstraintAccess().getGroup_0(), "rule__VersionConstraint__Group_0__0");
					put(grammarAccess.getVersionConstraintAccess().getGroup_0_2_0(), "rule__VersionConstraint__Group_0_2_0__0");
					put(grammarAccess.getN4mfIdentifierAccess().getGroup_11(), "rule__N4mfIdentifier__Group_11__0");
					put(grammarAccess.getN4mfIdentifierAccess().getGroup_15(), "rule__N4mfIdentifier__Group_15__0");
					put(grammarAccess.getProjectDescriptionAccess().getProjectIdAssignment_0_2(), "rule__ProjectDescription__ProjectIdAssignment_0_2");
					put(grammarAccess.getProjectDescriptionAccess().getProjectTypeAssignment_1_2(), "rule__ProjectDescription__ProjectTypeAssignment_1_2");
					put(grammarAccess.getProjectDescriptionAccess().getProjectVersionAssignment_2_2(), "rule__ProjectDescription__ProjectVersionAssignment_2_2");
					put(grammarAccess.getProjectDescriptionAccess().getDeclaredVendorIdAssignment_3_2(), "rule__ProjectDescription__DeclaredVendorIdAssignment_3_2");
					put(grammarAccess.getProjectDescriptionAccess().getVendorNameAssignment_4_2(), "rule__ProjectDescription__VendorNameAssignment_4_2");
					put(grammarAccess.getProjectDescriptionAccess().getMainModuleAssignment_5_2(), "rule__ProjectDescription__MainModuleAssignment_5_2");
					put(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentAssignment_6(), "rule__ProjectDescription__ExtendedRuntimeEnvironmentAssignment_6");
					put(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesAssignment_7(), "rule__ProjectDescription__ProvidedRuntimeLibrariesAssignment_7");
					put(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesAssignment_8(), "rule__ProjectDescription__RequiredRuntimeLibrariesAssignment_8");
					put(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesAssignment_9(), "rule__ProjectDescription__ProjectDependenciesAssignment_9");
					put(grammarAccess.getProjectDescriptionAccess().getImplementationIdAssignment_10_2(), "rule__ProjectDescription__ImplementationIdAssignment_10_2");
					put(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsAssignment_11(), "rule__ProjectDescription__ImplementedProjectsAssignment_11");
					put(grammarAccess.getProjectDescriptionAccess().getInitModulesAssignment_12(), "rule__ProjectDescription__InitModulesAssignment_12");
					put(grammarAccess.getProjectDescriptionAccess().getExecModuleAssignment_13(), "rule__ProjectDescription__ExecModuleAssignment_13");
					put(grammarAccess.getProjectDescriptionAccess().getOutputPathAssignment_14_2(), "rule__ProjectDescription__OutputPathAssignment_14_2");
					put(grammarAccess.getProjectDescriptionAccess().getLibraryPathsAssignment_15_2(), "rule__ProjectDescription__LibraryPathsAssignment_15_2");
					put(grammarAccess.getProjectDescriptionAccess().getLibraryPathsAssignment_15_3_1(), "rule__ProjectDescription__LibraryPathsAssignment_15_3_1");
					put(grammarAccess.getProjectDescriptionAccess().getResourcePathsAssignment_16_2(), "rule__ProjectDescription__ResourcePathsAssignment_16_2");
					put(grammarAccess.getProjectDescriptionAccess().getResourcePathsAssignment_16_3_1(), "rule__ProjectDescription__ResourcePathsAssignment_16_3_1");
					put(grammarAccess.getProjectDescriptionAccess().getSourceFragmentAssignment_17_2(), "rule__ProjectDescription__SourceFragmentAssignment_17_2");
					put(grammarAccess.getProjectDescriptionAccess().getModuleFiltersAssignment_18_2(), "rule__ProjectDescription__ModuleFiltersAssignment_18_2");
					put(grammarAccess.getProjectDescriptionAccess().getTestedProjectsAssignment_19(), "rule__ProjectDescription__TestedProjectsAssignment_19");
					put(grammarAccess.getProjectDescriptionAccess().getModuleLoaderAssignment_20_2(), "rule__ProjectDescription__ModuleLoaderAssignment_20_2");
					put(grammarAccess.getExecModuleAccess().getExecModuleAssignment_3(), "rule__ExecModule__ExecModuleAssignment_3");
					put(grammarAccess.getTestedProjectsAccess().getTestedProjectsAssignment_3_0(), "rule__TestedProjects__TestedProjectsAssignment_3_0");
					put(grammarAccess.getTestedProjectsAccess().getTestedProjectsAssignment_3_1_1(), "rule__TestedProjects__TestedProjectsAssignment_3_1_1");
					put(grammarAccess.getInitModulesAccess().getInitModulesAssignment_3_0(), "rule__InitModules__InitModulesAssignment_3_0");
					put(grammarAccess.getInitModulesAccess().getInitModulesAssignment_3_1_1(), "rule__InitModules__InitModulesAssignment_3_1_1");
					put(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsAssignment_3_0(), "rule__ImplementedProjects__ImplementedProjectsAssignment_3_0");
					put(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsAssignment_3_1_1(), "rule__ImplementedProjects__ImplementedProjectsAssignment_3_1_1");
					put(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesAssignment_3_0(), "rule__ProjectDependencies__ProjectDependenciesAssignment_3_0");
					put(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesAssignment_3_1_1(), "rule__ProjectDependencies__ProjectDependenciesAssignment_3_1_1");
					put(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesAssignment_3_0(), "rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_0");
					put(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesAssignment_3_1_1(), "rule__ProvidedRuntimeLibraries__ProvidedRuntimeLibrariesAssignment_3_1_1");
					put(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesAssignment_3_0(), "rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_0");
					put(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesAssignment_3_1_1(), "rule__RequiredRuntimeLibraries__RequiredRuntimeLibrariesAssignment_3_1_1");
					put(grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentAssignment_3(), "rule__ExtendedRuntimeEnvironment__ExtendedRuntimeEnvironmentAssignment_3");
					put(grammarAccess.getDeclaredVersionAccess().getMajorAssignment_0(), "rule__DeclaredVersion__MajorAssignment_0");
					put(grammarAccess.getDeclaredVersionAccess().getMinorAssignment_1_1(), "rule__DeclaredVersion__MinorAssignment_1_1");
					put(grammarAccess.getDeclaredVersionAccess().getMicroAssignment_1_2_1(), "rule__DeclaredVersion__MicroAssignment_1_2_1");
					put(grammarAccess.getDeclaredVersionAccess().getQualifierAssignment_2_1(), "rule__DeclaredVersion__QualifierAssignment_2_1");
					put(grammarAccess.getSourceFragmentAccess().getSourceFragmentTypeAssignment_0(), "rule__SourceFragment__SourceFragmentTypeAssignment_0");
					put(grammarAccess.getSourceFragmentAccess().getPathsAssignment_2(), "rule__SourceFragment__PathsAssignment_2");
					put(grammarAccess.getSourceFragmentAccess().getPathsAssignment_3_1(), "rule__SourceFragment__PathsAssignment_3_1");
					put(grammarAccess.getModuleFilterAccess().getModuleFilterTypeAssignment_0(), "rule__ModuleFilter__ModuleFilterTypeAssignment_0");
					put(grammarAccess.getModuleFilterAccess().getModuleSpecifiersAssignment_2(), "rule__ModuleFilter__ModuleSpecifiersAssignment_2");
					put(grammarAccess.getModuleFilterAccess().getModuleSpecifiersAssignment_3_1(), "rule__ModuleFilter__ModuleSpecifiersAssignment_3_1");
					put(grammarAccess.getBootstrapModuleAccess().getModuleSpecifierWithWildcardAssignment_0(), "rule__BootstrapModule__ModuleSpecifierWithWildcardAssignment_0");
					put(grammarAccess.getBootstrapModuleAccess().getSourcePathAssignment_1_1(), "rule__BootstrapModule__SourcePathAssignment_1_1");
					put(grammarAccess.getModuleFilterSpecifierAccess().getModuleSpecifierWithWildcardAssignment_0(), "rule__ModuleFilterSpecifier__ModuleSpecifierWithWildcardAssignment_0");
					put(grammarAccess.getModuleFilterSpecifierAccess().getSourcePathAssignment_1_1(), "rule__ModuleFilterSpecifier__SourcePathAssignment_1_1");
					put(grammarAccess.getProvidedRuntimeLibraryDependencyAccess().getProjectAssignment(), "rule__ProvidedRuntimeLibraryDependency__ProjectAssignment");
					put(grammarAccess.getRequiredRuntimeLibraryDependencyAccess().getProjectAssignment(), "rule__RequiredRuntimeLibraryDependency__ProjectAssignment");
					put(grammarAccess.getTestedProjectAccess().getProjectAssignment(), "rule__TestedProject__ProjectAssignment");
					put(grammarAccess.getProjectReferenceAccess().getProjectAssignment(), "rule__ProjectReference__ProjectAssignment");
					put(grammarAccess.getProjectDependencyAccess().getProjectAssignment_0(), "rule__ProjectDependency__ProjectAssignment_0");
					put(grammarAccess.getProjectDependencyAccess().getVersionConstraintAssignment_1(), "rule__ProjectDependency__VersionConstraintAssignment_1");
					put(grammarAccess.getProjectDependencyAccess().getDeclaredScopeAssignment_2(), "rule__ProjectDependency__DeclaredScopeAssignment_2");
					put(grammarAccess.getSimpleProjectDescriptionAccess().getDeclaredVendorIdAssignment_0_0(), "rule__SimpleProjectDescription__DeclaredVendorIdAssignment_0_0");
					put(grammarAccess.getSimpleProjectDescriptionAccess().getProjectIdAssignment_1(), "rule__SimpleProjectDescription__ProjectIdAssignment_1");
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
