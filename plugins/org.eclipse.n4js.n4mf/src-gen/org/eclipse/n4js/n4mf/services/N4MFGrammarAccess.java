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
package org.eclipse.n4js.n4mf.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
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
import org.eclipse.xtext.UnorderedGroup;
import org.eclipse.xtext.common.services.TerminalsGrammarAccess;
import org.eclipse.xtext.service.AbstractElementFinder.AbstractEnumRuleElementFinder;
import org.eclipse.xtext.service.AbstractElementFinder.AbstractGrammarElementFinder;
import org.eclipse.xtext.service.GrammarProvider;

@Singleton
public class N4MFGrammarAccess extends AbstractGrammarElementFinder {
	
	public class ProjectDescriptionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.ProjectDescription");
		private final UnorderedGroup cUnorderedGroup = (UnorderedGroup)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cUnorderedGroup.eContents().get(0);
		private final Keyword cProjectIdKeyword_0_0 = (Keyword)cGroup_0.eContents().get(0);
		private final Keyword cColonKeyword_0_1 = (Keyword)cGroup_0.eContents().get(1);
		private final Assignment cProjectIdAssignment_0_2 = (Assignment)cGroup_0.eContents().get(2);
		private final RuleCall cProjectIdN4mfIdentifierParserRuleCall_0_2_0 = (RuleCall)cProjectIdAssignment_0_2.eContents().get(0);
		private final Group cGroup_1 = (Group)cUnorderedGroup.eContents().get(1);
		private final Keyword cProjectTypeKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Keyword cColonKeyword_1_1 = (Keyword)cGroup_1.eContents().get(1);
		private final Assignment cProjectTypeAssignment_1_2 = (Assignment)cGroup_1.eContents().get(2);
		private final RuleCall cProjectTypeProjectTypeEnumRuleCall_1_2_0 = (RuleCall)cProjectTypeAssignment_1_2.eContents().get(0);
		private final Group cGroup_2 = (Group)cUnorderedGroup.eContents().get(2);
		private final Keyword cProjectVersionKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Keyword cColonKeyword_2_1 = (Keyword)cGroup_2.eContents().get(1);
		private final Assignment cProjectVersionAssignment_2_2 = (Assignment)cGroup_2.eContents().get(2);
		private final RuleCall cProjectVersionDeclaredVersionParserRuleCall_2_2_0 = (RuleCall)cProjectVersionAssignment_2_2.eContents().get(0);
		private final Group cGroup_3 = (Group)cUnorderedGroup.eContents().get(3);
		private final Keyword cVendorIdKeyword_3_0 = (Keyword)cGroup_3.eContents().get(0);
		private final Keyword cColonKeyword_3_1 = (Keyword)cGroup_3.eContents().get(1);
		private final Assignment cDeclaredVendorIdAssignment_3_2 = (Assignment)cGroup_3.eContents().get(2);
		private final RuleCall cDeclaredVendorIdN4mfIdentifierParserRuleCall_3_2_0 = (RuleCall)cDeclaredVendorIdAssignment_3_2.eContents().get(0);
		private final Group cGroup_4 = (Group)cUnorderedGroup.eContents().get(4);
		private final Keyword cVendorNameKeyword_4_0 = (Keyword)cGroup_4.eContents().get(0);
		private final Keyword cColonKeyword_4_1 = (Keyword)cGroup_4.eContents().get(1);
		private final Assignment cVendorNameAssignment_4_2 = (Assignment)cGroup_4.eContents().get(2);
		private final RuleCall cVendorNameSTRINGTerminalRuleCall_4_2_0 = (RuleCall)cVendorNameAssignment_4_2.eContents().get(0);
		private final Group cGroup_5 = (Group)cUnorderedGroup.eContents().get(5);
		private final Keyword cMainModuleKeyword_5_0 = (Keyword)cGroup_5.eContents().get(0);
		private final Keyword cColonKeyword_5_1 = (Keyword)cGroup_5.eContents().get(1);
		private final Assignment cMainModuleAssignment_5_2 = (Assignment)cGroup_5.eContents().get(2);
		private final RuleCall cMainModuleSTRINGTerminalRuleCall_5_2_0 = (RuleCall)cMainModuleAssignment_5_2.eContents().get(0);
		private final Assignment cExtendedRuntimeEnvironmentAssignment_6 = (Assignment)cUnorderedGroup.eContents().get(6);
		private final RuleCall cExtendedRuntimeEnvironmentExtendedRuntimeEnvironmentParserRuleCall_6_0 = (RuleCall)cExtendedRuntimeEnvironmentAssignment_6.eContents().get(0);
		private final Assignment cProvidedRuntimeLibrariesAssignment_7 = (Assignment)cUnorderedGroup.eContents().get(7);
		private final RuleCall cProvidedRuntimeLibrariesProvidedRuntimeLibrariesParserRuleCall_7_0 = (RuleCall)cProvidedRuntimeLibrariesAssignment_7.eContents().get(0);
		private final Assignment cRequiredRuntimeLibrariesAssignment_8 = (Assignment)cUnorderedGroup.eContents().get(8);
		private final RuleCall cRequiredRuntimeLibrariesRequiredRuntimeLibrariesParserRuleCall_8_0 = (RuleCall)cRequiredRuntimeLibrariesAssignment_8.eContents().get(0);
		private final Assignment cProjectDependenciesAssignment_9 = (Assignment)cUnorderedGroup.eContents().get(9);
		private final RuleCall cProjectDependenciesProjectDependenciesParserRuleCall_9_0 = (RuleCall)cProjectDependenciesAssignment_9.eContents().get(0);
		private final Group cGroup_10 = (Group)cUnorderedGroup.eContents().get(10);
		private final Keyword cImplementationIdKeyword_10_0 = (Keyword)cGroup_10.eContents().get(0);
		private final Keyword cColonKeyword_10_1 = (Keyword)cGroup_10.eContents().get(1);
		private final Assignment cImplementationIdAssignment_10_2 = (Assignment)cGroup_10.eContents().get(2);
		private final RuleCall cImplementationIdN4mfIdentifierParserRuleCall_10_2_0 = (RuleCall)cImplementationIdAssignment_10_2.eContents().get(0);
		private final Assignment cImplementedProjectsAssignment_11 = (Assignment)cUnorderedGroup.eContents().get(11);
		private final RuleCall cImplementedProjectsImplementedProjectsParserRuleCall_11_0 = (RuleCall)cImplementedProjectsAssignment_11.eContents().get(0);
		private final Assignment cInitModulesAssignment_12 = (Assignment)cUnorderedGroup.eContents().get(12);
		private final RuleCall cInitModulesInitModulesParserRuleCall_12_0 = (RuleCall)cInitModulesAssignment_12.eContents().get(0);
		private final Assignment cExecModuleAssignment_13 = (Assignment)cUnorderedGroup.eContents().get(13);
		private final RuleCall cExecModuleExecModuleParserRuleCall_13_0 = (RuleCall)cExecModuleAssignment_13.eContents().get(0);
		private final Group cGroup_14 = (Group)cUnorderedGroup.eContents().get(14);
		private final Keyword cOutputKeyword_14_0 = (Keyword)cGroup_14.eContents().get(0);
		private final Keyword cColonKeyword_14_1 = (Keyword)cGroup_14.eContents().get(1);
		private final Assignment cOutputPathAssignment_14_2 = (Assignment)cGroup_14.eContents().get(2);
		private final RuleCall cOutputPathSTRINGTerminalRuleCall_14_2_0 = (RuleCall)cOutputPathAssignment_14_2.eContents().get(0);
		private final Group cGroup_15 = (Group)cUnorderedGroup.eContents().get(15);
		private final Keyword cLibrariesKeyword_15_0 = (Keyword)cGroup_15.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_15_1 = (Keyword)cGroup_15.eContents().get(1);
		private final Assignment cLibraryPathsAssignment_15_2 = (Assignment)cGroup_15.eContents().get(2);
		private final RuleCall cLibraryPathsSTRINGTerminalRuleCall_15_2_0 = (RuleCall)cLibraryPathsAssignment_15_2.eContents().get(0);
		private final Group cGroup_15_3 = (Group)cGroup_15.eContents().get(3);
		private final Keyword cCommaKeyword_15_3_0 = (Keyword)cGroup_15_3.eContents().get(0);
		private final Assignment cLibraryPathsAssignment_15_3_1 = (Assignment)cGroup_15_3.eContents().get(1);
		private final RuleCall cLibraryPathsSTRINGTerminalRuleCall_15_3_1_0 = (RuleCall)cLibraryPathsAssignment_15_3_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_15_4 = (Keyword)cGroup_15.eContents().get(4);
		private final Group cGroup_16 = (Group)cUnorderedGroup.eContents().get(16);
		private final Keyword cResourcesKeyword_16_0 = (Keyword)cGroup_16.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_16_1 = (Keyword)cGroup_16.eContents().get(1);
		private final Assignment cResourcePathsAssignment_16_2 = (Assignment)cGroup_16.eContents().get(2);
		private final RuleCall cResourcePathsSTRINGTerminalRuleCall_16_2_0 = (RuleCall)cResourcePathsAssignment_16_2.eContents().get(0);
		private final Group cGroup_16_3 = (Group)cGroup_16.eContents().get(3);
		private final Keyword cCommaKeyword_16_3_0 = (Keyword)cGroup_16_3.eContents().get(0);
		private final Assignment cResourcePathsAssignment_16_3_1 = (Assignment)cGroup_16_3.eContents().get(1);
		private final RuleCall cResourcePathsSTRINGTerminalRuleCall_16_3_1_0 = (RuleCall)cResourcePathsAssignment_16_3_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_16_4 = (Keyword)cGroup_16.eContents().get(4);
		private final Group cGroup_17 = (Group)cUnorderedGroup.eContents().get(17);
		private final Keyword cSourcesKeyword_17_0 = (Keyword)cGroup_17.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_17_1 = (Keyword)cGroup_17.eContents().get(1);
		private final Assignment cSourceFragmentAssignment_17_2 = (Assignment)cGroup_17.eContents().get(2);
		private final RuleCall cSourceFragmentSourceFragmentParserRuleCall_17_2_0 = (RuleCall)cSourceFragmentAssignment_17_2.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_17_3 = (Keyword)cGroup_17.eContents().get(3);
		private final Group cGroup_18 = (Group)cUnorderedGroup.eContents().get(18);
		private final Keyword cModuleFiltersKeyword_18_0 = (Keyword)cGroup_18.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_18_1 = (Keyword)cGroup_18.eContents().get(1);
		private final Assignment cModuleFiltersAssignment_18_2 = (Assignment)cGroup_18.eContents().get(2);
		private final RuleCall cModuleFiltersModuleFilterParserRuleCall_18_2_0 = (RuleCall)cModuleFiltersAssignment_18_2.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_18_3 = (Keyword)cGroup_18.eContents().get(3);
		private final Assignment cTestedProjectsAssignment_19 = (Assignment)cUnorderedGroup.eContents().get(19);
		private final RuleCall cTestedProjectsTestedProjectsParserRuleCall_19_0 = (RuleCall)cTestedProjectsAssignment_19.eContents().get(0);
		private final Group cGroup_20 = (Group)cUnorderedGroup.eContents().get(20);
		private final Keyword cModuleLoaderKeyword_20_0 = (Keyword)cGroup_20.eContents().get(0);
		private final Keyword cColonKeyword_20_1 = (Keyword)cGroup_20.eContents().get(1);
		private final Assignment cModuleLoaderAssignment_20_2 = (Assignment)cGroup_20.eContents().get(2);
		private final RuleCall cModuleLoaderModuleLoaderEnumRuleCall_20_2_0 = (RuleCall)cModuleLoaderAssignment_20_2.eContents().get(0);
		
		//ProjectDescription:
		//	'ProjectId' ':' projectId=N4mfIdentifier & 'ProjectType' ':' projectType=ProjectType & 'ProjectVersion' ':'
		//	projectVersion=DeclaredVersion & 'VendorId' ':' declaredVendorId=N4mfIdentifier & ('VendorName' ':'
		//	vendorName=STRING)?
		//	& ('MainModule' ':' mainModule=STRING)?
		//	// only available for runtime environments
		//	& extendedRuntimeEnvironment=ExtendedRuntimeEnvironment?
		//	// only in case of runtime libraries or runtime environment:
		//	& providedRuntimeLibraries=ProvidedRuntimeLibraries?
		//	// not available in runtime environments:
		//	& requiredRuntimeLibraries=RequiredRuntimeLibraries?
		//	// only available in N4JS components (Apps, Libs, Processor)
		//	& projectDependencies=ProjectDependencies?
		//	// only available in N4JS components (Apps, Libs, Processor)
		//	& ('ImplementationId' ':' implementationId=N4mfIdentifier)?
		//	// only available in N4JS components (Apps, Libs, Processor)
		//	& implementedProjects=ImplementedProjects?
		//	//only RuntimeLibrary and RuntimeEnvironemnt
		//	& initModules=InitModules?
		//	& execModule=ExecModule?
		//	& ('Output' ':' outputPath=STRING)?
		//	& ('Libraries' '{' libraryPaths+=STRING (',' libraryPaths+=STRING)* '}')?
		//	& ('Resources' '{' resourcePaths+=STRING (',' resourcePaths+=STRING)* '}')?
		//	& ('Sources' '{' sourceFragment+=SourceFragment+ '}')?
		//	& ('ModuleFilters' '{' moduleFilters+=ModuleFilter+ '}')?
		//	& testedProjects=TestedProjects?
		//	& ('ModuleLoader' ':' moduleLoader=ModuleLoader)?;
		@Override public ParserRule getRule() { return rule; }
		
		//'ProjectId' ':' projectId=N4mfIdentifier & 'ProjectType' ':' projectType=ProjectType & 'ProjectVersion' ':'
		//projectVersion=DeclaredVersion & 'VendorId' ':' declaredVendorId=N4mfIdentifier & ('VendorName' ':' vendorName=STRING)?
		//& ('MainModule' ':' mainModule=STRING)? // only available for runtime environments
		//& extendedRuntimeEnvironment=ExtendedRuntimeEnvironment? // only in case of runtime libraries or runtime environment:
		//& providedRuntimeLibraries=ProvidedRuntimeLibraries? // not available in runtime environments:
		//& requiredRuntimeLibraries=RequiredRuntimeLibraries? // only available in N4JS components (Apps, Libs, Processor)
		//& projectDependencies=ProjectDependencies? // only available in N4JS components (Apps, Libs, Processor)
		//& ('ImplementationId' ':' implementationId=N4mfIdentifier)? // only available in N4JS components (Apps, Libs, Processor)
		//& implementedProjects=ImplementedProjects? //only RuntimeLibrary and RuntimeEnvironemnt
		//& initModules=InitModules? & execModule=ExecModule? & ('Output' ':' outputPath=STRING)? & ('Libraries' '{'
		//libraryPaths+=STRING (',' libraryPaths+=STRING)* '}')? & ('Resources' '{' resourcePaths+=STRING (','
		//resourcePaths+=STRING)* '}')? & ('Sources' '{' sourceFragment+=SourceFragment+ '}')? & ('ModuleFilters' '{'
		//moduleFilters+=ModuleFilter+ '}')? & testedProjects=TestedProjects? & ('ModuleLoader' ':' moduleLoader=ModuleLoader)?
		public UnorderedGroup getUnorderedGroup() { return cUnorderedGroup; }
		
		//'ProjectId' ':' projectId=N4mfIdentifier
		public Group getGroup_0() { return cGroup_0; }
		
		//'ProjectId'
		public Keyword getProjectIdKeyword_0_0() { return cProjectIdKeyword_0_0; }
		
		//':'
		public Keyword getColonKeyword_0_1() { return cColonKeyword_0_1; }
		
		//projectId=N4mfIdentifier
		public Assignment getProjectIdAssignment_0_2() { return cProjectIdAssignment_0_2; }
		
		//N4mfIdentifier
		public RuleCall getProjectIdN4mfIdentifierParserRuleCall_0_2_0() { return cProjectIdN4mfIdentifierParserRuleCall_0_2_0; }
		
		//'ProjectType' ':' projectType=ProjectType
		public Group getGroup_1() { return cGroup_1; }
		
		//'ProjectType'
		public Keyword getProjectTypeKeyword_1_0() { return cProjectTypeKeyword_1_0; }
		
		//':'
		public Keyword getColonKeyword_1_1() { return cColonKeyword_1_1; }
		
		//projectType=ProjectType
		public Assignment getProjectTypeAssignment_1_2() { return cProjectTypeAssignment_1_2; }
		
		//ProjectType
		public RuleCall getProjectTypeProjectTypeEnumRuleCall_1_2_0() { return cProjectTypeProjectTypeEnumRuleCall_1_2_0; }
		
		//'ProjectVersion' ':' projectVersion=DeclaredVersion
		public Group getGroup_2() { return cGroup_2; }
		
		//'ProjectVersion'
		public Keyword getProjectVersionKeyword_2_0() { return cProjectVersionKeyword_2_0; }
		
		//':'
		public Keyword getColonKeyword_2_1() { return cColonKeyword_2_1; }
		
		//projectVersion=DeclaredVersion
		public Assignment getProjectVersionAssignment_2_2() { return cProjectVersionAssignment_2_2; }
		
		//DeclaredVersion
		public RuleCall getProjectVersionDeclaredVersionParserRuleCall_2_2_0() { return cProjectVersionDeclaredVersionParserRuleCall_2_2_0; }
		
		//'VendorId' ':' declaredVendorId=N4mfIdentifier
		public Group getGroup_3() { return cGroup_3; }
		
		//'VendorId'
		public Keyword getVendorIdKeyword_3_0() { return cVendorIdKeyword_3_0; }
		
		//':'
		public Keyword getColonKeyword_3_1() { return cColonKeyword_3_1; }
		
		//declaredVendorId=N4mfIdentifier
		public Assignment getDeclaredVendorIdAssignment_3_2() { return cDeclaredVendorIdAssignment_3_2; }
		
		//N4mfIdentifier
		public RuleCall getDeclaredVendorIdN4mfIdentifierParserRuleCall_3_2_0() { return cDeclaredVendorIdN4mfIdentifierParserRuleCall_3_2_0; }
		
		//('VendorName' ':' vendorName=STRING)?
		public Group getGroup_4() { return cGroup_4; }
		
		//'VendorName'
		public Keyword getVendorNameKeyword_4_0() { return cVendorNameKeyword_4_0; }
		
		//':'
		public Keyword getColonKeyword_4_1() { return cColonKeyword_4_1; }
		
		//vendorName=STRING
		public Assignment getVendorNameAssignment_4_2() { return cVendorNameAssignment_4_2; }
		
		//STRING
		public RuleCall getVendorNameSTRINGTerminalRuleCall_4_2_0() { return cVendorNameSTRINGTerminalRuleCall_4_2_0; }
		
		//('MainModule' ':' mainModule=STRING)?
		public Group getGroup_5() { return cGroup_5; }
		
		//'MainModule'
		public Keyword getMainModuleKeyword_5_0() { return cMainModuleKeyword_5_0; }
		
		//':'
		public Keyword getColonKeyword_5_1() { return cColonKeyword_5_1; }
		
		//mainModule=STRING
		public Assignment getMainModuleAssignment_5_2() { return cMainModuleAssignment_5_2; }
		
		//STRING
		public RuleCall getMainModuleSTRINGTerminalRuleCall_5_2_0() { return cMainModuleSTRINGTerminalRuleCall_5_2_0; }
		
		//extendedRuntimeEnvironment=ExtendedRuntimeEnvironment?
		public Assignment getExtendedRuntimeEnvironmentAssignment_6() { return cExtendedRuntimeEnvironmentAssignment_6; }
		
		//ExtendedRuntimeEnvironment
		public RuleCall getExtendedRuntimeEnvironmentExtendedRuntimeEnvironmentParserRuleCall_6_0() { return cExtendedRuntimeEnvironmentExtendedRuntimeEnvironmentParserRuleCall_6_0; }
		
		//providedRuntimeLibraries=ProvidedRuntimeLibraries?
		public Assignment getProvidedRuntimeLibrariesAssignment_7() { return cProvidedRuntimeLibrariesAssignment_7; }
		
		//ProvidedRuntimeLibraries
		public RuleCall getProvidedRuntimeLibrariesProvidedRuntimeLibrariesParserRuleCall_7_0() { return cProvidedRuntimeLibrariesProvidedRuntimeLibrariesParserRuleCall_7_0; }
		
		//requiredRuntimeLibraries=RequiredRuntimeLibraries?
		public Assignment getRequiredRuntimeLibrariesAssignment_8() { return cRequiredRuntimeLibrariesAssignment_8; }
		
		//RequiredRuntimeLibraries
		public RuleCall getRequiredRuntimeLibrariesRequiredRuntimeLibrariesParserRuleCall_8_0() { return cRequiredRuntimeLibrariesRequiredRuntimeLibrariesParserRuleCall_8_0; }
		
		//projectDependencies=ProjectDependencies?
		public Assignment getProjectDependenciesAssignment_9() { return cProjectDependenciesAssignment_9; }
		
		//ProjectDependencies
		public RuleCall getProjectDependenciesProjectDependenciesParserRuleCall_9_0() { return cProjectDependenciesProjectDependenciesParserRuleCall_9_0; }
		
		//('ImplementationId' ':' implementationId=N4mfIdentifier)?
		public Group getGroup_10() { return cGroup_10; }
		
		//'ImplementationId'
		public Keyword getImplementationIdKeyword_10_0() { return cImplementationIdKeyword_10_0; }
		
		//':'
		public Keyword getColonKeyword_10_1() { return cColonKeyword_10_1; }
		
		//implementationId=N4mfIdentifier
		public Assignment getImplementationIdAssignment_10_2() { return cImplementationIdAssignment_10_2; }
		
		//N4mfIdentifier
		public RuleCall getImplementationIdN4mfIdentifierParserRuleCall_10_2_0() { return cImplementationIdN4mfIdentifierParserRuleCall_10_2_0; }
		
		//implementedProjects=ImplementedProjects?
		public Assignment getImplementedProjectsAssignment_11() { return cImplementedProjectsAssignment_11; }
		
		//ImplementedProjects
		public RuleCall getImplementedProjectsImplementedProjectsParserRuleCall_11_0() { return cImplementedProjectsImplementedProjectsParserRuleCall_11_0; }
		
		//initModules=InitModules?
		public Assignment getInitModulesAssignment_12() { return cInitModulesAssignment_12; }
		
		//InitModules
		public RuleCall getInitModulesInitModulesParserRuleCall_12_0() { return cInitModulesInitModulesParserRuleCall_12_0; }
		
		//execModule=ExecModule?
		public Assignment getExecModuleAssignment_13() { return cExecModuleAssignment_13; }
		
		//ExecModule
		public RuleCall getExecModuleExecModuleParserRuleCall_13_0() { return cExecModuleExecModuleParserRuleCall_13_0; }
		
		//('Output' ':' outputPath=STRING)?
		public Group getGroup_14() { return cGroup_14; }
		
		//'Output'
		public Keyword getOutputKeyword_14_0() { return cOutputKeyword_14_0; }
		
		//':'
		public Keyword getColonKeyword_14_1() { return cColonKeyword_14_1; }
		
		//outputPath=STRING
		public Assignment getOutputPathAssignment_14_2() { return cOutputPathAssignment_14_2; }
		
		//STRING
		public RuleCall getOutputPathSTRINGTerminalRuleCall_14_2_0() { return cOutputPathSTRINGTerminalRuleCall_14_2_0; }
		
		//('Libraries' '{' libraryPaths+=STRING (',' libraryPaths+=STRING)* '}')?
		public Group getGroup_15() { return cGroup_15; }
		
		//'Libraries'
		public Keyword getLibrariesKeyword_15_0() { return cLibrariesKeyword_15_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_15_1() { return cLeftCurlyBracketKeyword_15_1; }
		
		//libraryPaths+=STRING
		public Assignment getLibraryPathsAssignment_15_2() { return cLibraryPathsAssignment_15_2; }
		
		//STRING
		public RuleCall getLibraryPathsSTRINGTerminalRuleCall_15_2_0() { return cLibraryPathsSTRINGTerminalRuleCall_15_2_0; }
		
		//(',' libraryPaths+=STRING)*
		public Group getGroup_15_3() { return cGroup_15_3; }
		
		//','
		public Keyword getCommaKeyword_15_3_0() { return cCommaKeyword_15_3_0; }
		
		//libraryPaths+=STRING
		public Assignment getLibraryPathsAssignment_15_3_1() { return cLibraryPathsAssignment_15_3_1; }
		
		//STRING
		public RuleCall getLibraryPathsSTRINGTerminalRuleCall_15_3_1_0() { return cLibraryPathsSTRINGTerminalRuleCall_15_3_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_15_4() { return cRightCurlyBracketKeyword_15_4; }
		
		//('Resources' '{' resourcePaths+=STRING (',' resourcePaths+=STRING)* '}')?
		public Group getGroup_16() { return cGroup_16; }
		
		//'Resources'
		public Keyword getResourcesKeyword_16_0() { return cResourcesKeyword_16_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_16_1() { return cLeftCurlyBracketKeyword_16_1; }
		
		//resourcePaths+=STRING
		public Assignment getResourcePathsAssignment_16_2() { return cResourcePathsAssignment_16_2; }
		
		//STRING
		public RuleCall getResourcePathsSTRINGTerminalRuleCall_16_2_0() { return cResourcePathsSTRINGTerminalRuleCall_16_2_0; }
		
		//(',' resourcePaths+=STRING)*
		public Group getGroup_16_3() { return cGroup_16_3; }
		
		//','
		public Keyword getCommaKeyword_16_3_0() { return cCommaKeyword_16_3_0; }
		
		//resourcePaths+=STRING
		public Assignment getResourcePathsAssignment_16_3_1() { return cResourcePathsAssignment_16_3_1; }
		
		//STRING
		public RuleCall getResourcePathsSTRINGTerminalRuleCall_16_3_1_0() { return cResourcePathsSTRINGTerminalRuleCall_16_3_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_16_4() { return cRightCurlyBracketKeyword_16_4; }
		
		//('Sources' '{' sourceFragment+=SourceFragment+ '}')?
		public Group getGroup_17() { return cGroup_17; }
		
		//'Sources'
		public Keyword getSourcesKeyword_17_0() { return cSourcesKeyword_17_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_17_1() { return cLeftCurlyBracketKeyword_17_1; }
		
		//sourceFragment+=SourceFragment+
		public Assignment getSourceFragmentAssignment_17_2() { return cSourceFragmentAssignment_17_2; }
		
		//SourceFragment
		public RuleCall getSourceFragmentSourceFragmentParserRuleCall_17_2_0() { return cSourceFragmentSourceFragmentParserRuleCall_17_2_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_17_3() { return cRightCurlyBracketKeyword_17_3; }
		
		//('ModuleFilters' '{' moduleFilters+=ModuleFilter+ '}')?
		public Group getGroup_18() { return cGroup_18; }
		
		//'ModuleFilters'
		public Keyword getModuleFiltersKeyword_18_0() { return cModuleFiltersKeyword_18_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_18_1() { return cLeftCurlyBracketKeyword_18_1; }
		
		//moduleFilters+=ModuleFilter+
		public Assignment getModuleFiltersAssignment_18_2() { return cModuleFiltersAssignment_18_2; }
		
		//ModuleFilter
		public RuleCall getModuleFiltersModuleFilterParserRuleCall_18_2_0() { return cModuleFiltersModuleFilterParserRuleCall_18_2_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_18_3() { return cRightCurlyBracketKeyword_18_3; }
		
		//testedProjects=TestedProjects?
		public Assignment getTestedProjectsAssignment_19() { return cTestedProjectsAssignment_19; }
		
		//TestedProjects
		public RuleCall getTestedProjectsTestedProjectsParserRuleCall_19_0() { return cTestedProjectsTestedProjectsParserRuleCall_19_0; }
		
		//('ModuleLoader' ':' moduleLoader=ModuleLoader)?
		public Group getGroup_20() { return cGroup_20; }
		
		//'ModuleLoader'
		public Keyword getModuleLoaderKeyword_20_0() { return cModuleLoaderKeyword_20_0; }
		
		//':'
		public Keyword getColonKeyword_20_1() { return cColonKeyword_20_1; }
		
		//moduleLoader=ModuleLoader
		public Assignment getModuleLoaderAssignment_20_2() { return cModuleLoaderAssignment_20_2; }
		
		//ModuleLoader
		public RuleCall getModuleLoaderModuleLoaderEnumRuleCall_20_2_0() { return cModuleLoaderModuleLoaderEnumRuleCall_20_2_0; }
	}
	public class ExecModuleElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.ExecModule");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cExecModuleAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cExecModuleKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cColonKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cExecModuleAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cExecModuleBootstrapModuleParserRuleCall_3_0 = (RuleCall)cExecModuleAssignment_3.eContents().get(0);
		
		//ExecModule:
		//	{ExecModule}
		//	'ExecModule' ':' execModule=BootstrapModule;
		@Override public ParserRule getRule() { return rule; }
		
		//{ExecModule} 'ExecModule' ':' execModule=BootstrapModule
		public Group getGroup() { return cGroup; }
		
		//{ExecModule}
		public Action getExecModuleAction_0() { return cExecModuleAction_0; }
		
		//'ExecModule'
		public Keyword getExecModuleKeyword_1() { return cExecModuleKeyword_1; }
		
		//':'
		public Keyword getColonKeyword_2() { return cColonKeyword_2; }
		
		//execModule=BootstrapModule
		public Assignment getExecModuleAssignment_3() { return cExecModuleAssignment_3; }
		
		//BootstrapModule
		public RuleCall getExecModuleBootstrapModuleParserRuleCall_3_0() { return cExecModuleBootstrapModuleParserRuleCall_3_0; }
	}
	public class TestedProjectsElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.TestedProjects");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cTestedProjectsAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cTestedProjectsKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cLeftCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Assignment cTestedProjectsAssignment_3_0 = (Assignment)cGroup_3.eContents().get(0);
		private final RuleCall cTestedProjectsTestedProjectParserRuleCall_3_0_0 = (RuleCall)cTestedProjectsAssignment_3_0.eContents().get(0);
		private final Group cGroup_3_1 = (Group)cGroup_3.eContents().get(1);
		private final Keyword cCommaKeyword_3_1_0 = (Keyword)cGroup_3_1.eContents().get(0);
		private final Assignment cTestedProjectsAssignment_3_1_1 = (Assignment)cGroup_3_1.eContents().get(1);
		private final RuleCall cTestedProjectsTestedProjectParserRuleCall_3_1_1_0 = (RuleCall)cTestedProjectsAssignment_3_1_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//TestedProjects:
		//	{TestedProjects}
		//	'TestedProjects' '{' (testedProjects+=TestedProject (',' testedProjects+=TestedProject)*)?
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//{TestedProjects} 'TestedProjects' '{' (testedProjects+=TestedProject (',' testedProjects+=TestedProject)*)? '}'
		public Group getGroup() { return cGroup; }
		
		//{TestedProjects}
		public Action getTestedProjectsAction_0() { return cTestedProjectsAction_0; }
		
		//'TestedProjects'
		public Keyword getTestedProjectsKeyword_1() { return cTestedProjectsKeyword_1; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_2() { return cLeftCurlyBracketKeyword_2; }
		
		//(testedProjects+=TestedProject (',' testedProjects+=TestedProject)*)?
		public Group getGroup_3() { return cGroup_3; }
		
		//testedProjects+=TestedProject
		public Assignment getTestedProjectsAssignment_3_0() { return cTestedProjectsAssignment_3_0; }
		
		//TestedProject
		public RuleCall getTestedProjectsTestedProjectParserRuleCall_3_0_0() { return cTestedProjectsTestedProjectParserRuleCall_3_0_0; }
		
		//(',' testedProjects+=TestedProject)*
		public Group getGroup_3_1() { return cGroup_3_1; }
		
		//','
		public Keyword getCommaKeyword_3_1_0() { return cCommaKeyword_3_1_0; }
		
		//testedProjects+=TestedProject
		public Assignment getTestedProjectsAssignment_3_1_1() { return cTestedProjectsAssignment_3_1_1; }
		
		//TestedProject
		public RuleCall getTestedProjectsTestedProjectParserRuleCall_3_1_1_0() { return cTestedProjectsTestedProjectParserRuleCall_3_1_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_4() { return cRightCurlyBracketKeyword_4; }
	}
	public class InitModulesElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.InitModules");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cInitModulesAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cInitModulesKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cLeftCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Assignment cInitModulesAssignment_3_0 = (Assignment)cGroup_3.eContents().get(0);
		private final RuleCall cInitModulesBootstrapModuleParserRuleCall_3_0_0 = (RuleCall)cInitModulesAssignment_3_0.eContents().get(0);
		private final Group cGroup_3_1 = (Group)cGroup_3.eContents().get(1);
		private final Keyword cCommaKeyword_3_1_0 = (Keyword)cGroup_3_1.eContents().get(0);
		private final Assignment cInitModulesAssignment_3_1_1 = (Assignment)cGroup_3_1.eContents().get(1);
		private final RuleCall cInitModulesBootstrapModuleParserRuleCall_3_1_1_0 = (RuleCall)cInitModulesAssignment_3_1_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//InitModules:
		//	{InitModules}
		//	'InitModules' '{' (initModules+=BootstrapModule (',' initModules+=BootstrapModule)*)?
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//{InitModules} 'InitModules' '{' (initModules+=BootstrapModule (',' initModules+=BootstrapModule)*)? '}'
		public Group getGroup() { return cGroup; }
		
		//{InitModules}
		public Action getInitModulesAction_0() { return cInitModulesAction_0; }
		
		//'InitModules'
		public Keyword getInitModulesKeyword_1() { return cInitModulesKeyword_1; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_2() { return cLeftCurlyBracketKeyword_2; }
		
		//(initModules+=BootstrapModule (',' initModules+=BootstrapModule)*)?
		public Group getGroup_3() { return cGroup_3; }
		
		//initModules+=BootstrapModule
		public Assignment getInitModulesAssignment_3_0() { return cInitModulesAssignment_3_0; }
		
		//BootstrapModule
		public RuleCall getInitModulesBootstrapModuleParserRuleCall_3_0_0() { return cInitModulesBootstrapModuleParserRuleCall_3_0_0; }
		
		//(',' initModules+=BootstrapModule)*
		public Group getGroup_3_1() { return cGroup_3_1; }
		
		//','
		public Keyword getCommaKeyword_3_1_0() { return cCommaKeyword_3_1_0; }
		
		//initModules+=BootstrapModule
		public Assignment getInitModulesAssignment_3_1_1() { return cInitModulesAssignment_3_1_1; }
		
		//BootstrapModule
		public RuleCall getInitModulesBootstrapModuleParserRuleCall_3_1_1_0() { return cInitModulesBootstrapModuleParserRuleCall_3_1_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_4() { return cRightCurlyBracketKeyword_4; }
	}
	public class ImplementedProjectsElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.ImplementedProjects");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cImplementedProjectsAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cImplementedProjectsKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cLeftCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Assignment cImplementedProjectsAssignment_3_0 = (Assignment)cGroup_3.eContents().get(0);
		private final RuleCall cImplementedProjectsProjectReferenceParserRuleCall_3_0_0 = (RuleCall)cImplementedProjectsAssignment_3_0.eContents().get(0);
		private final Group cGroup_3_1 = (Group)cGroup_3.eContents().get(1);
		private final Keyword cCommaKeyword_3_1_0 = (Keyword)cGroup_3_1.eContents().get(0);
		private final Assignment cImplementedProjectsAssignment_3_1_1 = (Assignment)cGroup_3_1.eContents().get(1);
		private final RuleCall cImplementedProjectsProjectReferenceParserRuleCall_3_1_1_0 = (RuleCall)cImplementedProjectsAssignment_3_1_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//ImplementedProjects:
		//	{ImplementedProjects}
		//	'ImplementedProjects' '{' (implementedProjects+=ProjectReference (',' implementedProjects+=ProjectReference)*)?
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//{ImplementedProjects} 'ImplementedProjects' '{' (implementedProjects+=ProjectReference (','
		//implementedProjects+=ProjectReference)*)? '}'
		public Group getGroup() { return cGroup; }
		
		//{ImplementedProjects}
		public Action getImplementedProjectsAction_0() { return cImplementedProjectsAction_0; }
		
		//'ImplementedProjects'
		public Keyword getImplementedProjectsKeyword_1() { return cImplementedProjectsKeyword_1; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_2() { return cLeftCurlyBracketKeyword_2; }
		
		//(implementedProjects+=ProjectReference (',' implementedProjects+=ProjectReference)*)?
		public Group getGroup_3() { return cGroup_3; }
		
		//implementedProjects+=ProjectReference
		public Assignment getImplementedProjectsAssignment_3_0() { return cImplementedProjectsAssignment_3_0; }
		
		//ProjectReference
		public RuleCall getImplementedProjectsProjectReferenceParserRuleCall_3_0_0() { return cImplementedProjectsProjectReferenceParserRuleCall_3_0_0; }
		
		//(',' implementedProjects+=ProjectReference)*
		public Group getGroup_3_1() { return cGroup_3_1; }
		
		//','
		public Keyword getCommaKeyword_3_1_0() { return cCommaKeyword_3_1_0; }
		
		//implementedProjects+=ProjectReference
		public Assignment getImplementedProjectsAssignment_3_1_1() { return cImplementedProjectsAssignment_3_1_1; }
		
		//ProjectReference
		public RuleCall getImplementedProjectsProjectReferenceParserRuleCall_3_1_1_0() { return cImplementedProjectsProjectReferenceParserRuleCall_3_1_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_4() { return cRightCurlyBracketKeyword_4; }
	}
	public class ProjectDependenciesElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.ProjectDependencies");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cProjectDependenciesAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cProjectDependenciesKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cLeftCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Assignment cProjectDependenciesAssignment_3_0 = (Assignment)cGroup_3.eContents().get(0);
		private final RuleCall cProjectDependenciesProjectDependencyParserRuleCall_3_0_0 = (RuleCall)cProjectDependenciesAssignment_3_0.eContents().get(0);
		private final Group cGroup_3_1 = (Group)cGroup_3.eContents().get(1);
		private final Keyword cCommaKeyword_3_1_0 = (Keyword)cGroup_3_1.eContents().get(0);
		private final Assignment cProjectDependenciesAssignment_3_1_1 = (Assignment)cGroup_3_1.eContents().get(1);
		private final RuleCall cProjectDependenciesProjectDependencyParserRuleCall_3_1_1_0 = (RuleCall)cProjectDependenciesAssignment_3_1_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//ProjectDependencies:
		//	{ProjectDependencies}
		//	'ProjectDependencies' '{' (projectDependencies+=ProjectDependency (',' projectDependencies+=ProjectDependency)*)?
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//{ProjectDependencies} 'ProjectDependencies' '{' (projectDependencies+=ProjectDependency (','
		//projectDependencies+=ProjectDependency)*)? '}'
		public Group getGroup() { return cGroup; }
		
		//{ProjectDependencies}
		public Action getProjectDependenciesAction_0() { return cProjectDependenciesAction_0; }
		
		//'ProjectDependencies'
		public Keyword getProjectDependenciesKeyword_1() { return cProjectDependenciesKeyword_1; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_2() { return cLeftCurlyBracketKeyword_2; }
		
		//(projectDependencies+=ProjectDependency (',' projectDependencies+=ProjectDependency)*)?
		public Group getGroup_3() { return cGroup_3; }
		
		//projectDependencies+=ProjectDependency
		public Assignment getProjectDependenciesAssignment_3_0() { return cProjectDependenciesAssignment_3_0; }
		
		//ProjectDependency
		public RuleCall getProjectDependenciesProjectDependencyParserRuleCall_3_0_0() { return cProjectDependenciesProjectDependencyParserRuleCall_3_0_0; }
		
		//(',' projectDependencies+=ProjectDependency)*
		public Group getGroup_3_1() { return cGroup_3_1; }
		
		//','
		public Keyword getCommaKeyword_3_1_0() { return cCommaKeyword_3_1_0; }
		
		//projectDependencies+=ProjectDependency
		public Assignment getProjectDependenciesAssignment_3_1_1() { return cProjectDependenciesAssignment_3_1_1; }
		
		//ProjectDependency
		public RuleCall getProjectDependenciesProjectDependencyParserRuleCall_3_1_1_0() { return cProjectDependenciesProjectDependencyParserRuleCall_3_1_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_4() { return cRightCurlyBracketKeyword_4; }
	}
	public class ProvidedRuntimeLibrariesElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.ProvidedRuntimeLibraries");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cProvidedRuntimeLibrariesAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cProvidedRuntimeLibrariesKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cLeftCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Assignment cProvidedRuntimeLibrariesAssignment_3_0 = (Assignment)cGroup_3.eContents().get(0);
		private final RuleCall cProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_3_0_0 = (RuleCall)cProvidedRuntimeLibrariesAssignment_3_0.eContents().get(0);
		private final Group cGroup_3_1 = (Group)cGroup_3.eContents().get(1);
		private final Keyword cCommaKeyword_3_1_0 = (Keyword)cGroup_3_1.eContents().get(0);
		private final Assignment cProvidedRuntimeLibrariesAssignment_3_1_1 = (Assignment)cGroup_3_1.eContents().get(1);
		private final RuleCall cProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_3_1_1_0 = (RuleCall)cProvidedRuntimeLibrariesAssignment_3_1_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//ProvidedRuntimeLibraries:
		//	{ProvidedRuntimeLibraries}
		//	'ProvidedRuntimeLibraries' '{' (providedRuntimeLibraries+=ProvidedRuntimeLibraryDependency (','
		//	providedRuntimeLibraries+=ProvidedRuntimeLibraryDependency)*)?
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//{ProvidedRuntimeLibraries} 'ProvidedRuntimeLibraries' '{' (providedRuntimeLibraries+=ProvidedRuntimeLibraryDependency
		//(',' providedRuntimeLibraries+=ProvidedRuntimeLibraryDependency)*)? '}'
		public Group getGroup() { return cGroup; }
		
		//{ProvidedRuntimeLibraries}
		public Action getProvidedRuntimeLibrariesAction_0() { return cProvidedRuntimeLibrariesAction_0; }
		
		//'ProvidedRuntimeLibraries'
		public Keyword getProvidedRuntimeLibrariesKeyword_1() { return cProvidedRuntimeLibrariesKeyword_1; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_2() { return cLeftCurlyBracketKeyword_2; }
		
		//(providedRuntimeLibraries+=ProvidedRuntimeLibraryDependency (','
		//providedRuntimeLibraries+=ProvidedRuntimeLibraryDependency)*)?
		public Group getGroup_3() { return cGroup_3; }
		
		//providedRuntimeLibraries+=ProvidedRuntimeLibraryDependency
		public Assignment getProvidedRuntimeLibrariesAssignment_3_0() { return cProvidedRuntimeLibrariesAssignment_3_0; }
		
		//ProvidedRuntimeLibraryDependency
		public RuleCall getProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_3_0_0() { return cProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_3_0_0; }
		
		//(',' providedRuntimeLibraries+=ProvidedRuntimeLibraryDependency)*
		public Group getGroup_3_1() { return cGroup_3_1; }
		
		//','
		public Keyword getCommaKeyword_3_1_0() { return cCommaKeyword_3_1_0; }
		
		//providedRuntimeLibraries+=ProvidedRuntimeLibraryDependency
		public Assignment getProvidedRuntimeLibrariesAssignment_3_1_1() { return cProvidedRuntimeLibrariesAssignment_3_1_1; }
		
		//ProvidedRuntimeLibraryDependency
		public RuleCall getProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_3_1_1_0() { return cProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_3_1_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_4() { return cRightCurlyBracketKeyword_4; }
	}
	public class RequiredRuntimeLibrariesElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.RequiredRuntimeLibraries");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cRequiredRuntimeLibrariesAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cRequiredRuntimeLibrariesKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cLeftCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Assignment cRequiredRuntimeLibrariesAssignment_3_0 = (Assignment)cGroup_3.eContents().get(0);
		private final RuleCall cRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_3_0_0 = (RuleCall)cRequiredRuntimeLibrariesAssignment_3_0.eContents().get(0);
		private final Group cGroup_3_1 = (Group)cGroup_3.eContents().get(1);
		private final Keyword cCommaKeyword_3_1_0 = (Keyword)cGroup_3_1.eContents().get(0);
		private final Assignment cRequiredRuntimeLibrariesAssignment_3_1_1 = (Assignment)cGroup_3_1.eContents().get(1);
		private final RuleCall cRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_3_1_1_0 = (RuleCall)cRequiredRuntimeLibrariesAssignment_3_1_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//RequiredRuntimeLibraries:
		//	{RequiredRuntimeLibraries}
		//	'RequiredRuntimeLibraries' '{' (requiredRuntimeLibraries+=RequiredRuntimeLibraryDependency (','
		//	requiredRuntimeLibraries+=RequiredRuntimeLibraryDependency)*)?
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//{RequiredRuntimeLibraries} 'RequiredRuntimeLibraries' '{' (requiredRuntimeLibraries+=RequiredRuntimeLibraryDependency
		//(',' requiredRuntimeLibraries+=RequiredRuntimeLibraryDependency)*)? '}'
		public Group getGroup() { return cGroup; }
		
		//{RequiredRuntimeLibraries}
		public Action getRequiredRuntimeLibrariesAction_0() { return cRequiredRuntimeLibrariesAction_0; }
		
		//'RequiredRuntimeLibraries'
		public Keyword getRequiredRuntimeLibrariesKeyword_1() { return cRequiredRuntimeLibrariesKeyword_1; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_2() { return cLeftCurlyBracketKeyword_2; }
		
		//(requiredRuntimeLibraries+=RequiredRuntimeLibraryDependency (','
		//requiredRuntimeLibraries+=RequiredRuntimeLibraryDependency)*)?
		public Group getGroup_3() { return cGroup_3; }
		
		//requiredRuntimeLibraries+=RequiredRuntimeLibraryDependency
		public Assignment getRequiredRuntimeLibrariesAssignment_3_0() { return cRequiredRuntimeLibrariesAssignment_3_0; }
		
		//RequiredRuntimeLibraryDependency
		public RuleCall getRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_3_0_0() { return cRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_3_0_0; }
		
		//(',' requiredRuntimeLibraries+=RequiredRuntimeLibraryDependency)*
		public Group getGroup_3_1() { return cGroup_3_1; }
		
		//','
		public Keyword getCommaKeyword_3_1_0() { return cCommaKeyword_3_1_0; }
		
		//requiredRuntimeLibraries+=RequiredRuntimeLibraryDependency
		public Assignment getRequiredRuntimeLibrariesAssignment_3_1_1() { return cRequiredRuntimeLibrariesAssignment_3_1_1; }
		
		//RequiredRuntimeLibraryDependency
		public RuleCall getRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_3_1_1_0() { return cRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_3_1_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_4() { return cRightCurlyBracketKeyword_4; }
	}
	public class ExtendedRuntimeEnvironmentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.ExtendedRuntimeEnvironment");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cExtendedRuntimeEnvironmentAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cExtendedRuntimeEnvironmentKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cColonKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cExtendedRuntimeEnvironmentAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cExtendedRuntimeEnvironmentProjectReferenceParserRuleCall_3_0 = (RuleCall)cExtendedRuntimeEnvironmentAssignment_3.eContents().get(0);
		
		//ExtendedRuntimeEnvironment:
		//	{ExtendedRuntimeEnvironment}
		//	'ExtendedRuntimeEnvironment' ':' extendedRuntimeEnvironment=ProjectReference;
		@Override public ParserRule getRule() { return rule; }
		
		//{ExtendedRuntimeEnvironment} 'ExtendedRuntimeEnvironment' ':' extendedRuntimeEnvironment=ProjectReference
		public Group getGroup() { return cGroup; }
		
		//{ExtendedRuntimeEnvironment}
		public Action getExtendedRuntimeEnvironmentAction_0() { return cExtendedRuntimeEnvironmentAction_0; }
		
		//'ExtendedRuntimeEnvironment'
		public Keyword getExtendedRuntimeEnvironmentKeyword_1() { return cExtendedRuntimeEnvironmentKeyword_1; }
		
		//':'
		public Keyword getColonKeyword_2() { return cColonKeyword_2; }
		
		//extendedRuntimeEnvironment=ProjectReference
		public Assignment getExtendedRuntimeEnvironmentAssignment_3() { return cExtendedRuntimeEnvironmentAssignment_3; }
		
		//ProjectReference
		public RuleCall getExtendedRuntimeEnvironmentProjectReferenceParserRuleCall_3_0() { return cExtendedRuntimeEnvironmentProjectReferenceParserRuleCall_3_0; }
	}
	public class DeclaredVersionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.DeclaredVersion");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cMajorAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cMajorINTTerminalRuleCall_0_0 = (RuleCall)cMajorAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cFullStopKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cMinorAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cMinorINTTerminalRuleCall_1_1_0 = (RuleCall)cMinorAssignment_1_1.eContents().get(0);
		private final Group cGroup_1_2 = (Group)cGroup_1.eContents().get(2);
		private final Keyword cFullStopKeyword_1_2_0 = (Keyword)cGroup_1_2.eContents().get(0);
		private final Assignment cMicroAssignment_1_2_1 = (Assignment)cGroup_1_2.eContents().get(1);
		private final RuleCall cMicroINTTerminalRuleCall_1_2_1_0 = (RuleCall)cMicroAssignment_1_2_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cHyphenMinusKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Assignment cQualifierAssignment_2_1 = (Assignment)cGroup_2.eContents().get(1);
		private final RuleCall cQualifierN4mfIdentifierParserRuleCall_2_1_0 = (RuleCall)cQualifierAssignment_2_1.eContents().get(0);
		
		////TODO build meta data not supported https://semver.org/#spec-item-10
		//DeclaredVersion:
		//	major=INT ('.' minor=INT ('.' micro=INT)?)? ('-' qualifier=N4mfIdentifier)?;
		@Override public ParserRule getRule() { return rule; }
		
		//major=INT ('.' minor=INT ('.' micro=INT)?)? ('-' qualifier=N4mfIdentifier)?
		public Group getGroup() { return cGroup; }
		
		//major=INT
		public Assignment getMajorAssignment_0() { return cMajorAssignment_0; }
		
		//INT
		public RuleCall getMajorINTTerminalRuleCall_0_0() { return cMajorINTTerminalRuleCall_0_0; }
		
		//('.' minor=INT ('.' micro=INT)?)?
		public Group getGroup_1() { return cGroup_1; }
		
		//'.'
		public Keyword getFullStopKeyword_1_0() { return cFullStopKeyword_1_0; }
		
		//minor=INT
		public Assignment getMinorAssignment_1_1() { return cMinorAssignment_1_1; }
		
		//INT
		public RuleCall getMinorINTTerminalRuleCall_1_1_0() { return cMinorINTTerminalRuleCall_1_1_0; }
		
		//('.' micro=INT)?
		public Group getGroup_1_2() { return cGroup_1_2; }
		
		//'.'
		public Keyword getFullStopKeyword_1_2_0() { return cFullStopKeyword_1_2_0; }
		
		//micro=INT
		public Assignment getMicroAssignment_1_2_1() { return cMicroAssignment_1_2_1; }
		
		//INT
		public RuleCall getMicroINTTerminalRuleCall_1_2_1_0() { return cMicroINTTerminalRuleCall_1_2_1_0; }
		
		//('-' qualifier=N4mfIdentifier)?
		public Group getGroup_2() { return cGroup_2; }
		
		//'-'
		public Keyword getHyphenMinusKeyword_2_0() { return cHyphenMinusKeyword_2_0; }
		
		//qualifier=N4mfIdentifier
		public Assignment getQualifierAssignment_2_1() { return cQualifierAssignment_2_1; }
		
		//N4mfIdentifier
		public RuleCall getQualifierN4mfIdentifierParserRuleCall_2_1_0() { return cQualifierN4mfIdentifierParserRuleCall_2_1_0; }
	}
	public class SourceFragmentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.SourceFragment");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cSourceFragmentTypeAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cSourceFragmentTypeSourceFragmentTypeEnumRuleCall_0_0 = (RuleCall)cSourceFragmentTypeAssignment_0.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cPathsAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cPathsSTRINGTerminalRuleCall_2_0 = (RuleCall)cPathsAssignment_2.eContents().get(0);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Keyword cCommaKeyword_3_0 = (Keyword)cGroup_3.eContents().get(0);
		private final Assignment cPathsAssignment_3_1 = (Assignment)cGroup_3.eContents().get(1);
		private final RuleCall cPathsSTRINGTerminalRuleCall_3_1_0 = (RuleCall)cPathsAssignment_3_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//SourceFragment:
		//	sourceFragmentType=SourceFragmentType '{' paths+=STRING (',' paths+=STRING)* '}';
		@Override public ParserRule getRule() { return rule; }
		
		//sourceFragmentType=SourceFragmentType '{' paths+=STRING (',' paths+=STRING)* '}'
		public Group getGroup() { return cGroup; }
		
		//sourceFragmentType=SourceFragmentType
		public Assignment getSourceFragmentTypeAssignment_0() { return cSourceFragmentTypeAssignment_0; }
		
		//SourceFragmentType
		public RuleCall getSourceFragmentTypeSourceFragmentTypeEnumRuleCall_0_0() { return cSourceFragmentTypeSourceFragmentTypeEnumRuleCall_0_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_1() { return cLeftCurlyBracketKeyword_1; }
		
		//paths+=STRING
		public Assignment getPathsAssignment_2() { return cPathsAssignment_2; }
		
		//STRING
		public RuleCall getPathsSTRINGTerminalRuleCall_2_0() { return cPathsSTRINGTerminalRuleCall_2_0; }
		
		//(',' paths+=STRING)*
		public Group getGroup_3() { return cGroup_3; }
		
		//','
		public Keyword getCommaKeyword_3_0() { return cCommaKeyword_3_0; }
		
		//paths+=STRING
		public Assignment getPathsAssignment_3_1() { return cPathsAssignment_3_1; }
		
		//STRING
		public RuleCall getPathsSTRINGTerminalRuleCall_3_1_0() { return cPathsSTRINGTerminalRuleCall_3_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_4() { return cRightCurlyBracketKeyword_4; }
	}
	public class ModuleFilterElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.ModuleFilter");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cModuleFilterTypeAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cModuleFilterTypeModuleFilterTypeEnumRuleCall_0_0 = (RuleCall)cModuleFilterTypeAssignment_0.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cModuleSpecifiersAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cModuleSpecifiersModuleFilterSpecifierParserRuleCall_2_0 = (RuleCall)cModuleSpecifiersAssignment_2.eContents().get(0);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Keyword cCommaKeyword_3_0 = (Keyword)cGroup_3.eContents().get(0);
		private final Assignment cModuleSpecifiersAssignment_3_1 = (Assignment)cGroup_3.eContents().get(1);
		private final RuleCall cModuleSpecifiersModuleFilterSpecifierParserRuleCall_3_1_0 = (RuleCall)cModuleSpecifiersAssignment_3_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//ModuleFilter:
		//	moduleFilterType=ModuleFilterType '{'
		//	moduleSpecifiers+=ModuleFilterSpecifier (',' moduleSpecifiers+=ModuleFilterSpecifier)* '}';
		@Override public ParserRule getRule() { return rule; }
		
		//moduleFilterType=ModuleFilterType '{' moduleSpecifiers+=ModuleFilterSpecifier (','
		//moduleSpecifiers+=ModuleFilterSpecifier)* '}'
		public Group getGroup() { return cGroup; }
		
		//moduleFilterType=ModuleFilterType
		public Assignment getModuleFilterTypeAssignment_0() { return cModuleFilterTypeAssignment_0; }
		
		//ModuleFilterType
		public RuleCall getModuleFilterTypeModuleFilterTypeEnumRuleCall_0_0() { return cModuleFilterTypeModuleFilterTypeEnumRuleCall_0_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_1() { return cLeftCurlyBracketKeyword_1; }
		
		//moduleSpecifiers+=ModuleFilterSpecifier
		public Assignment getModuleSpecifiersAssignment_2() { return cModuleSpecifiersAssignment_2; }
		
		//ModuleFilterSpecifier
		public RuleCall getModuleSpecifiersModuleFilterSpecifierParserRuleCall_2_0() { return cModuleSpecifiersModuleFilterSpecifierParserRuleCall_2_0; }
		
		//(',' moduleSpecifiers+=ModuleFilterSpecifier)*
		public Group getGroup_3() { return cGroup_3; }
		
		//','
		public Keyword getCommaKeyword_3_0() { return cCommaKeyword_3_0; }
		
		//moduleSpecifiers+=ModuleFilterSpecifier
		public Assignment getModuleSpecifiersAssignment_3_1() { return cModuleSpecifiersAssignment_3_1; }
		
		//ModuleFilterSpecifier
		public RuleCall getModuleSpecifiersModuleFilterSpecifierParserRuleCall_3_1_0() { return cModuleSpecifiersModuleFilterSpecifierParserRuleCall_3_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_4() { return cRightCurlyBracketKeyword_4; }
	}
	public class BootstrapModuleElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.BootstrapModule");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cModuleSpecifierWithWildcardAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cModuleSpecifierWithWildcardSTRINGTerminalRuleCall_0_0 = (RuleCall)cModuleSpecifierWithWildcardAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cInKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cSourcePathAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cSourcePathSTRINGTerminalRuleCall_1_1_0 = (RuleCall)cSourcePathAssignment_1_1.eContents().get(0);
		
		//BootstrapModule:
		//	moduleSpecifierWithWildcard=STRING ('in' sourcePath=STRING)?;
		@Override public ParserRule getRule() { return rule; }
		
		//moduleSpecifierWithWildcard=STRING ('in' sourcePath=STRING)?
		public Group getGroup() { return cGroup; }
		
		//moduleSpecifierWithWildcard=STRING
		public Assignment getModuleSpecifierWithWildcardAssignment_0() { return cModuleSpecifierWithWildcardAssignment_0; }
		
		//STRING
		public RuleCall getModuleSpecifierWithWildcardSTRINGTerminalRuleCall_0_0() { return cModuleSpecifierWithWildcardSTRINGTerminalRuleCall_0_0; }
		
		//('in' sourcePath=STRING)?
		public Group getGroup_1() { return cGroup_1; }
		
		//'in'
		public Keyword getInKeyword_1_0() { return cInKeyword_1_0; }
		
		//sourcePath=STRING
		public Assignment getSourcePathAssignment_1_1() { return cSourcePathAssignment_1_1; }
		
		//STRING
		public RuleCall getSourcePathSTRINGTerminalRuleCall_1_1_0() { return cSourcePathSTRINGTerminalRuleCall_1_1_0; }
	}
	public class ModuleFilterSpecifierElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.ModuleFilterSpecifier");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cModuleSpecifierWithWildcardAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cModuleSpecifierWithWildcardSTRINGTerminalRuleCall_0_0 = (RuleCall)cModuleSpecifierWithWildcardAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cInKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cSourcePathAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cSourcePathSTRINGTerminalRuleCall_1_1_0 = (RuleCall)cSourcePathAssignment_1_1.eContents().get(0);
		
		//ModuleFilterSpecifier:
		//	moduleSpecifierWithWildcard=STRING ('in' sourcePath=STRING)?;
		@Override public ParserRule getRule() { return rule; }
		
		//moduleSpecifierWithWildcard=STRING ('in' sourcePath=STRING)?
		public Group getGroup() { return cGroup; }
		
		//moduleSpecifierWithWildcard=STRING
		public Assignment getModuleSpecifierWithWildcardAssignment_0() { return cModuleSpecifierWithWildcardAssignment_0; }
		
		//STRING
		public RuleCall getModuleSpecifierWithWildcardSTRINGTerminalRuleCall_0_0() { return cModuleSpecifierWithWildcardSTRINGTerminalRuleCall_0_0; }
		
		//('in' sourcePath=STRING)?
		public Group getGroup_1() { return cGroup_1; }
		
		//'in'
		public Keyword getInKeyword_1_0() { return cInKeyword_1_0; }
		
		//sourcePath=STRING
		public Assignment getSourcePathAssignment_1_1() { return cSourcePathAssignment_1_1; }
		
		//STRING
		public RuleCall getSourcePathSTRINGTerminalRuleCall_1_1_0() { return cSourcePathSTRINGTerminalRuleCall_1_1_0; }
	}
	public class ProvidedRuntimeLibraryDependencyElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.ProvidedRuntimeLibraryDependency");
		private final Assignment cProjectAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cProjectSimpleProjectDescriptionParserRuleCall_0 = (RuleCall)cProjectAssignment.eContents().get(0);
		
		//ProvidedRuntimeLibraryDependency:
		//	project=SimpleProjectDescription;
		@Override public ParserRule getRule() { return rule; }
		
		//project=SimpleProjectDescription
		public Assignment getProjectAssignment() { return cProjectAssignment; }
		
		//SimpleProjectDescription
		public RuleCall getProjectSimpleProjectDescriptionParserRuleCall_0() { return cProjectSimpleProjectDescriptionParserRuleCall_0; }
	}
	public class RequiredRuntimeLibraryDependencyElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.RequiredRuntimeLibraryDependency");
		private final Assignment cProjectAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cProjectSimpleProjectDescriptionParserRuleCall_0 = (RuleCall)cProjectAssignment.eContents().get(0);
		
		//RequiredRuntimeLibraryDependency:
		//	project=SimpleProjectDescription;
		@Override public ParserRule getRule() { return rule; }
		
		//project=SimpleProjectDescription
		public Assignment getProjectAssignment() { return cProjectAssignment; }
		
		//SimpleProjectDescription
		public RuleCall getProjectSimpleProjectDescriptionParserRuleCall_0() { return cProjectSimpleProjectDescriptionParserRuleCall_0; }
	}
	public class TestedProjectElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.TestedProject");
		private final Assignment cProjectAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cProjectSimpleProjectDescriptionParserRuleCall_0 = (RuleCall)cProjectAssignment.eContents().get(0);
		
		//TestedProject:
		//	project=SimpleProjectDescription;
		@Override public ParserRule getRule() { return rule; }
		
		//project=SimpleProjectDescription
		public Assignment getProjectAssignment() { return cProjectAssignment; }
		
		//SimpleProjectDescription
		public RuleCall getProjectSimpleProjectDescriptionParserRuleCall_0() { return cProjectSimpleProjectDescriptionParserRuleCall_0; }
	}
	public class ProjectReferenceElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.ProjectReference");
		private final Assignment cProjectAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cProjectSimpleProjectDescriptionParserRuleCall_0 = (RuleCall)cProjectAssignment.eContents().get(0);
		
		///*
		// * scope is optional, default scope is compile
		// */ ProjectReference:
		//	project=SimpleProjectDescription;
		@Override public ParserRule getRule() { return rule; }
		
		//project=SimpleProjectDescription
		public Assignment getProjectAssignment() { return cProjectAssignment; }
		
		//SimpleProjectDescription
		public RuleCall getProjectSimpleProjectDescriptionParserRuleCall_0() { return cProjectSimpleProjectDescriptionParserRuleCall_0; }
	}
	public class ProjectDependencyElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.ProjectDependency");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cProjectAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cProjectSimpleProjectDescriptionParserRuleCall_0_0 = (RuleCall)cProjectAssignment_0.eContents().get(0);
		private final Assignment cVersionConstraintAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cVersionConstraintVersionConstraintParserRuleCall_1_0 = (RuleCall)cVersionConstraintAssignment_1.eContents().get(0);
		private final Assignment cDeclaredScopeAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cDeclaredScopeProjectDependencyScopeEnumRuleCall_2_0 = (RuleCall)cDeclaredScopeAssignment_2.eContents().get(0);
		
		///*
		// * scope is optional, default scope is compile
		// */ ProjectDependency:
		//	project=SimpleProjectDescription
		//	versionConstraint=VersionConstraint?
		//	declaredScope=ProjectDependencyScope?;
		@Override public ParserRule getRule() { return rule; }
		
		//project=SimpleProjectDescription versionConstraint=VersionConstraint? declaredScope=ProjectDependencyScope?
		public Group getGroup() { return cGroup; }
		
		//project=SimpleProjectDescription
		public Assignment getProjectAssignment_0() { return cProjectAssignment_0; }
		
		//SimpleProjectDescription
		public RuleCall getProjectSimpleProjectDescriptionParserRuleCall_0_0() { return cProjectSimpleProjectDescriptionParserRuleCall_0_0; }
		
		//versionConstraint=VersionConstraint?
		public Assignment getVersionConstraintAssignment_1() { return cVersionConstraintAssignment_1; }
		
		//VersionConstraint
		public RuleCall getVersionConstraintVersionConstraintParserRuleCall_1_0() { return cVersionConstraintVersionConstraintParserRuleCall_1_0; }
		
		//declaredScope=ProjectDependencyScope?
		public Assignment getDeclaredScopeAssignment_2() { return cDeclaredScopeAssignment_2; }
		
		//ProjectDependencyScope
		public RuleCall getDeclaredScopeProjectDependencyScopeEnumRuleCall_2_0() { return cDeclaredScopeProjectDependencyScopeEnumRuleCall_2_0; }
	}
	public class SimpleProjectDescriptionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.SimpleProjectDescription");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Assignment cDeclaredVendorIdAssignment_0_0 = (Assignment)cGroup_0.eContents().get(0);
		private final RuleCall cDeclaredVendorIdN4mfIdentifierParserRuleCall_0_0_0 = (RuleCall)cDeclaredVendorIdAssignment_0_0.eContents().get(0);
		private final Keyword cColonKeyword_0_1 = (Keyword)cGroup_0.eContents().get(1);
		private final Assignment cProjectIdAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cProjectIdN4mfIdentifierParserRuleCall_1_0 = (RuleCall)cProjectIdAssignment_1.eContents().get(0);
		
		///*
		// * vendorN4mfIdentifier is optional, if it is not specified, vendor id of current project is used.
		// */ SimpleProjectDescription:
		//	(declaredVendorId=N4mfIdentifier ':')? projectId=N4mfIdentifier;
		@Override public ParserRule getRule() { return rule; }
		
		//(declaredVendorId=N4mfIdentifier ':')? projectId=N4mfIdentifier
		public Group getGroup() { return cGroup; }
		
		//(declaredVendorId=N4mfIdentifier ':')?
		public Group getGroup_0() { return cGroup_0; }
		
		//declaredVendorId=N4mfIdentifier
		public Assignment getDeclaredVendorIdAssignment_0_0() { return cDeclaredVendorIdAssignment_0_0; }
		
		//N4mfIdentifier
		public RuleCall getDeclaredVendorIdN4mfIdentifierParserRuleCall_0_0_0() { return cDeclaredVendorIdN4mfIdentifierParserRuleCall_0_0_0; }
		
		//':'
		public Keyword getColonKeyword_0_1() { return cColonKeyword_0_1; }
		
		//projectId=N4mfIdentifier
		public Assignment getProjectIdAssignment_1() { return cProjectIdAssignment_1; }
		
		//N4mfIdentifier
		public RuleCall getProjectIdN4mfIdentifierParserRuleCall_1_0() { return cProjectIdN4mfIdentifierParserRuleCall_1_0; }
	}
	public class VersionConstraintElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.VersionConstraint");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final Alternatives cAlternatives_0_0 = (Alternatives)cGroup_0.eContents().get(0);
		private final Assignment cExclLowerBoundAssignment_0_0_0 = (Assignment)cAlternatives_0_0.eContents().get(0);
		private final Keyword cExclLowerBoundLeftParenthesisKeyword_0_0_0_0 = (Keyword)cExclLowerBoundAssignment_0_0_0.eContents().get(0);
		private final Keyword cLeftSquareBracketKeyword_0_0_1 = (Keyword)cAlternatives_0_0.eContents().get(1);
		private final Assignment cLowerVersionAssignment_0_1 = (Assignment)cGroup_0.eContents().get(1);
		private final RuleCall cLowerVersionDeclaredVersionParserRuleCall_0_1_0 = (RuleCall)cLowerVersionAssignment_0_1.eContents().get(0);
		private final Alternatives cAlternatives_0_2 = (Alternatives)cGroup_0.eContents().get(2);
		private final Group cGroup_0_2_0 = (Group)cAlternatives_0_2.eContents().get(0);
		private final Keyword cCommaKeyword_0_2_0_0 = (Keyword)cGroup_0_2_0.eContents().get(0);
		private final Assignment cUpperVersionAssignment_0_2_0_1 = (Assignment)cGroup_0_2_0.eContents().get(1);
		private final RuleCall cUpperVersionDeclaredVersionParserRuleCall_0_2_0_1_0 = (RuleCall)cUpperVersionAssignment_0_2_0_1.eContents().get(0);
		private final Alternatives cAlternatives_0_2_0_2 = (Alternatives)cGroup_0_2_0.eContents().get(2);
		private final Assignment cExclUpperBoundAssignment_0_2_0_2_0 = (Assignment)cAlternatives_0_2_0_2.eContents().get(0);
		private final Keyword cExclUpperBoundRightParenthesisKeyword_0_2_0_2_0_0 = (Keyword)cExclUpperBoundAssignment_0_2_0_2_0.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_0_2_0_2_1 = (Keyword)cAlternatives_0_2_0_2.eContents().get(1);
		private final Keyword cRightParenthesisKeyword_0_2_1 = (Keyword)cAlternatives_0_2.eContents().get(1);
		private final Assignment cLowerVersionAssignment_1 = (Assignment)cAlternatives.eContents().get(1);
		private final RuleCall cLowerVersionDeclaredVersionParserRuleCall_1_0 = (RuleCall)cLowerVersionAssignment_1.eContents().get(0);
		
		///*
		// * If no version range is specified, lower version is inclusive.
		// */ VersionConstraint:
		//	(exclLowerBound?='(' | '[') lowerVersion=DeclaredVersion ((',' upperVersion=DeclaredVersion (exclUpperBound?=')' |
		//	']'))? | ')') | lowerVersion=DeclaredVersion;
		@Override public ParserRule getRule() { return rule; }
		
		//(exclLowerBound?='(' | '[') lowerVersion=DeclaredVersion ((',' upperVersion=DeclaredVersion (exclUpperBound?=')' |
		//']'))? | ')') | lowerVersion=DeclaredVersion
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//(exclLowerBound?='(' | '[') lowerVersion=DeclaredVersion ((',' upperVersion=DeclaredVersion (exclUpperBound?=')' |
		//']'))? | ')')
		public Group getGroup_0() { return cGroup_0; }
		
		//exclLowerBound?='(' | '['
		public Alternatives getAlternatives_0_0() { return cAlternatives_0_0; }
		
		//exclLowerBound?='('
		public Assignment getExclLowerBoundAssignment_0_0_0() { return cExclLowerBoundAssignment_0_0_0; }
		
		//'('
		public Keyword getExclLowerBoundLeftParenthesisKeyword_0_0_0_0() { return cExclLowerBoundLeftParenthesisKeyword_0_0_0_0; }
		
		//'['
		public Keyword getLeftSquareBracketKeyword_0_0_1() { return cLeftSquareBracketKeyword_0_0_1; }
		
		//lowerVersion=DeclaredVersion
		public Assignment getLowerVersionAssignment_0_1() { return cLowerVersionAssignment_0_1; }
		
		//DeclaredVersion
		public RuleCall getLowerVersionDeclaredVersionParserRuleCall_0_1_0() { return cLowerVersionDeclaredVersionParserRuleCall_0_1_0; }
		
		//(',' upperVersion=DeclaredVersion (exclUpperBound?=')' | ']'))? | ')'
		public Alternatives getAlternatives_0_2() { return cAlternatives_0_2; }
		
		//(',' upperVersion=DeclaredVersion (exclUpperBound?=')' | ']'))?
		public Group getGroup_0_2_0() { return cGroup_0_2_0; }
		
		//','
		public Keyword getCommaKeyword_0_2_0_0() { return cCommaKeyword_0_2_0_0; }
		
		//upperVersion=DeclaredVersion
		public Assignment getUpperVersionAssignment_0_2_0_1() { return cUpperVersionAssignment_0_2_0_1; }
		
		//DeclaredVersion
		public RuleCall getUpperVersionDeclaredVersionParserRuleCall_0_2_0_1_0() { return cUpperVersionDeclaredVersionParserRuleCall_0_2_0_1_0; }
		
		//exclUpperBound?=')' | ']'
		public Alternatives getAlternatives_0_2_0_2() { return cAlternatives_0_2_0_2; }
		
		//exclUpperBound?=')'
		public Assignment getExclUpperBoundAssignment_0_2_0_2_0() { return cExclUpperBoundAssignment_0_2_0_2_0; }
		
		//')'
		public Keyword getExclUpperBoundRightParenthesisKeyword_0_2_0_2_0_0() { return cExclUpperBoundRightParenthesisKeyword_0_2_0_2_0_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_0_2_0_2_1() { return cRightSquareBracketKeyword_0_2_0_2_1; }
		
		//')'
		public Keyword getRightParenthesisKeyword_0_2_1() { return cRightParenthesisKeyword_0_2_1; }
		
		//lowerVersion=DeclaredVersion
		public Assignment getLowerVersionAssignment_1() { return cLowerVersionAssignment_1; }
		
		//DeclaredVersion
		public RuleCall getLowerVersionDeclaredVersionParserRuleCall_1_0() { return cLowerVersionDeclaredVersionParserRuleCall_1_0; }
	}
	public class N4mfIdentifierElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.N4mfIdentifier");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cIDTerminalRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final Keyword cProjectIdKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cProjectTypeKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		private final Keyword cProjectVersionKeyword_3 = (Keyword)cAlternatives.eContents().get(3);
		private final Keyword cVendorIdKeyword_4 = (Keyword)cAlternatives.eContents().get(4);
		private final Keyword cVendorNameKeyword_5 = (Keyword)cAlternatives.eContents().get(5);
		private final Keyword cOutputKeyword_6 = (Keyword)cAlternatives.eContents().get(6);
		private final Keyword cLibrariesKeyword_7 = (Keyword)cAlternatives.eContents().get(7);
		private final Keyword cResourcesKeyword_8 = (Keyword)cAlternatives.eContents().get(8);
		private final Keyword cSourcesKeyword_9 = (Keyword)cAlternatives.eContents().get(9);
		private final Keyword cModuleFiltersKeyword_10 = (Keyword)cAlternatives.eContents().get(10);
		private final Group cGroup_11 = (Group)cAlternatives.eContents().get(11);
		private final Keyword cProjectDependenciesKeyword_11_0 = (Keyword)cGroup_11.eContents().get(0);
		private final Keyword cSystemKeyword_11_1 = (Keyword)cGroup_11.eContents().get(1);
		private final Keyword cAPIKeyword_12 = (Keyword)cAlternatives.eContents().get(12);
		private final Keyword cUserKeyword_13 = (Keyword)cAlternatives.eContents().get(13);
		private final Keyword cApplicationKeyword_14 = (Keyword)cAlternatives.eContents().get(14);
		private final Group cGroup_15 = (Group)cAlternatives.eContents().get(15);
		private final Keyword cProcessorKeyword_15_0 = (Keyword)cGroup_15.eContents().get(0);
		private final Keyword cSourceKeyword_15_1 = (Keyword)cGroup_15.eContents().get(1);
		private final Keyword cContentKeyword_16 = (Keyword)cAlternatives.eContents().get(16);
		private final Keyword cTestKeyword_17 = (Keyword)cAlternatives.eContents().get(17);
		
		//N4mfIdentifier:
		//	ID |
		//	'ProjectId' |
		//	'ProjectType' |
		//	'ProjectVersion' |
		//	'VendorId' |
		//	'VendorName' |
		//	'Output' |
		//	'Libraries' |
		//	'Resources' |
		//	'Sources' |
		//	'ModuleFilters' |
		//	'ProjectDependencies'
		//	'system' |
		//	'API' |
		//	'user' |
		//	'application' |
		//	'processor'
		//	'source' |
		//	'content' |
		//	'test';
		@Override public ParserRule getRule() { return rule; }
		
		//ID | 'ProjectId' | 'ProjectType' | 'ProjectVersion' | 'VendorId' | 'VendorName' | 'Output' | 'Libraries' | 'Resources' |
		//'Sources' | 'ModuleFilters' | 'ProjectDependencies' 'system' | 'API' | 'user' | 'application' | 'processor' 'source' |
		//'content' | 'test'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//ID
		public RuleCall getIDTerminalRuleCall_0() { return cIDTerminalRuleCall_0; }
		
		//'ProjectId'
		public Keyword getProjectIdKeyword_1() { return cProjectIdKeyword_1; }
		
		//'ProjectType'
		public Keyword getProjectTypeKeyword_2() { return cProjectTypeKeyword_2; }
		
		//'ProjectVersion'
		public Keyword getProjectVersionKeyword_3() { return cProjectVersionKeyword_3; }
		
		//'VendorId'
		public Keyword getVendorIdKeyword_4() { return cVendorIdKeyword_4; }
		
		//'VendorName'
		public Keyword getVendorNameKeyword_5() { return cVendorNameKeyword_5; }
		
		//'Output'
		public Keyword getOutputKeyword_6() { return cOutputKeyword_6; }
		
		//'Libraries'
		public Keyword getLibrariesKeyword_7() { return cLibrariesKeyword_7; }
		
		//'Resources'
		public Keyword getResourcesKeyword_8() { return cResourcesKeyword_8; }
		
		//'Sources'
		public Keyword getSourcesKeyword_9() { return cSourcesKeyword_9; }
		
		//'ModuleFilters'
		public Keyword getModuleFiltersKeyword_10() { return cModuleFiltersKeyword_10; }
		
		//'ProjectDependencies' 'system'
		public Group getGroup_11() { return cGroup_11; }
		
		//'ProjectDependencies'
		public Keyword getProjectDependenciesKeyword_11_0() { return cProjectDependenciesKeyword_11_0; }
		
		//'system'
		public Keyword getSystemKeyword_11_1() { return cSystemKeyword_11_1; }
		
		//'API'
		public Keyword getAPIKeyword_12() { return cAPIKeyword_12; }
		
		//'user'
		public Keyword getUserKeyword_13() { return cUserKeyword_13; }
		
		//'application'
		public Keyword getApplicationKeyword_14() { return cApplicationKeyword_14; }
		
		//'processor' 'source'
		public Group getGroup_15() { return cGroup_15; }
		
		//'processor'
		public Keyword getProcessorKeyword_15_0() { return cProcessorKeyword_15_0; }
		
		//'source'
		public Keyword getSourceKeyword_15_1() { return cSourceKeyword_15_1; }
		
		//'content'
		public Keyword getContentKeyword_16() { return cContentKeyword_16; }
		
		//'test'
		public Keyword getTestKeyword_17() { return cTestKeyword_17; }
	}
	
	public class ProjectTypeElements extends AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.ProjectType");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cAPPLICATIONEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cAPPLICATIONApplicationKeyword_0_0 = (Keyword)cAPPLICATIONEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cPROCESSOREnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cPROCESSORProcessorKeyword_1_0 = (Keyword)cPROCESSOREnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cLIBRARYEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cLIBRARYLibraryKeyword_2_0 = (Keyword)cLIBRARYEnumLiteralDeclaration_2.eContents().get(0);
		private final EnumLiteralDeclaration cAPIEnumLiteralDeclaration_3 = (EnumLiteralDeclaration)cAlternatives.eContents().get(3);
		private final Keyword cAPIAPIKeyword_3_0 = (Keyword)cAPIEnumLiteralDeclaration_3.eContents().get(0);
		private final EnumLiteralDeclaration cRUNTIME_ENVIRONMENTEnumLiteralDeclaration_4 = (EnumLiteralDeclaration)cAlternatives.eContents().get(4);
		private final Keyword cRUNTIME_ENVIRONMENTRuntimeEnvironmentKeyword_4_0 = (Keyword)cRUNTIME_ENVIRONMENTEnumLiteralDeclaration_4.eContents().get(0);
		private final EnumLiteralDeclaration cRUNTIME_LIBRARYEnumLiteralDeclaration_5 = (EnumLiteralDeclaration)cAlternatives.eContents().get(5);
		private final Keyword cRUNTIME_LIBRARYRuntimeLibraryKeyword_5_0 = (Keyword)cRUNTIME_LIBRARYEnumLiteralDeclaration_5.eContents().get(0);
		private final EnumLiteralDeclaration cTESTEnumLiteralDeclaration_6 = (EnumLiteralDeclaration)cAlternatives.eContents().get(6);
		private final Keyword cTESTTestKeyword_6_0 = (Keyword)cTESTEnumLiteralDeclaration_6.eContents().get(0);
		
		//enum ProjectType:
		//	APPLICATION='application' |
		//	PROCESSOR='processor' |
		//	LIBRARY='library' |
		//	API |
		//	RUNTIME_ENVIRONMENT="runtimeEnvironment" |
		//	RUNTIME_LIBRARY="runtimeLibrary" |
		//	TEST="test";
		public EnumRule getRule() { return rule; }
		
		//APPLICATION='application' | PROCESSOR='processor' | LIBRARY='library' | API | RUNTIME_ENVIRONMENT="runtimeEnvironment" |
		//RUNTIME_LIBRARY="runtimeLibrary" | TEST="test"
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//APPLICATION='application'
		public EnumLiteralDeclaration getAPPLICATIONEnumLiteralDeclaration_0() { return cAPPLICATIONEnumLiteralDeclaration_0; }
		
		//'application'
		public Keyword getAPPLICATIONApplicationKeyword_0_0() { return cAPPLICATIONApplicationKeyword_0_0; }
		
		//PROCESSOR='processor'
		public EnumLiteralDeclaration getPROCESSOREnumLiteralDeclaration_1() { return cPROCESSOREnumLiteralDeclaration_1; }
		
		//'processor'
		public Keyword getPROCESSORProcessorKeyword_1_0() { return cPROCESSORProcessorKeyword_1_0; }
		
		//LIBRARY='library'
		public EnumLiteralDeclaration getLIBRARYEnumLiteralDeclaration_2() { return cLIBRARYEnumLiteralDeclaration_2; }
		
		//'library'
		public Keyword getLIBRARYLibraryKeyword_2_0() { return cLIBRARYLibraryKeyword_2_0; }
		
		//API
		public EnumLiteralDeclaration getAPIEnumLiteralDeclaration_3() { return cAPIEnumLiteralDeclaration_3; }
		
		//'API'
		public Keyword getAPIAPIKeyword_3_0() { return cAPIAPIKeyword_3_0; }
		
		//RUNTIME_ENVIRONMENT="runtimeEnvironment"
		public EnumLiteralDeclaration getRUNTIME_ENVIRONMENTEnumLiteralDeclaration_4() { return cRUNTIME_ENVIRONMENTEnumLiteralDeclaration_4; }
		
		//"runtimeEnvironment"
		public Keyword getRUNTIME_ENVIRONMENTRuntimeEnvironmentKeyword_4_0() { return cRUNTIME_ENVIRONMENTRuntimeEnvironmentKeyword_4_0; }
		
		//RUNTIME_LIBRARY="runtimeLibrary"
		public EnumLiteralDeclaration getRUNTIME_LIBRARYEnumLiteralDeclaration_5() { return cRUNTIME_LIBRARYEnumLiteralDeclaration_5; }
		
		//"runtimeLibrary"
		public Keyword getRUNTIME_LIBRARYRuntimeLibraryKeyword_5_0() { return cRUNTIME_LIBRARYRuntimeLibraryKeyword_5_0; }
		
		//TEST="test"
		public EnumLiteralDeclaration getTESTEnumLiteralDeclaration_6() { return cTESTEnumLiteralDeclaration_6; }
		
		//"test"
		public Keyword getTESTTestKeyword_6_0() { return cTESTTestKeyword_6_0; }
	}
	public class SourceFragmentTypeElements extends AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.SourceFragmentType");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cSOURCEEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cSOURCESourceKeyword_0_0 = (Keyword)cSOURCEEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cEXTERNALEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cEXTERNALExternalKeyword_1_0 = (Keyword)cEXTERNALEnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cTESTEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cTESTTestKeyword_2_0 = (Keyword)cTESTEnumLiteralDeclaration_2.eContents().get(0);
		
		//enum SourceFragmentType:
		//	SOURCE='source' | EXTERNAL='external' | TEST='test';
		public EnumRule getRule() { return rule; }
		
		//SOURCE='source' | EXTERNAL='external' | TEST='test'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//SOURCE='source'
		public EnumLiteralDeclaration getSOURCEEnumLiteralDeclaration_0() { return cSOURCEEnumLiteralDeclaration_0; }
		
		//'source'
		public Keyword getSOURCESourceKeyword_0_0() { return cSOURCESourceKeyword_0_0; }
		
		//EXTERNAL='external'
		public EnumLiteralDeclaration getEXTERNALEnumLiteralDeclaration_1() { return cEXTERNALEnumLiteralDeclaration_1; }
		
		//'external'
		public Keyword getEXTERNALExternalKeyword_1_0() { return cEXTERNALExternalKeyword_1_0; }
		
		//TEST='test'
		public EnumLiteralDeclaration getTESTEnumLiteralDeclaration_2() { return cTESTEnumLiteralDeclaration_2; }
		
		//'test'
		public Keyword getTESTTestKeyword_2_0() { return cTESTTestKeyword_2_0; }
	}
	public class ModuleFilterTypeElements extends AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.ModuleFilterType");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cNO_VALIDATEEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cNO_VALIDATENoValidateKeyword_0_0 = (Keyword)cNO_VALIDATEEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cNO_MODULE_WRAPPINGEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cNO_MODULE_WRAPPINGNoModuleWrapKeyword_1_0 = (Keyword)cNO_MODULE_WRAPPINGEnumLiteralDeclaration_1.eContents().get(0);
		
		//enum ModuleFilterType:
		//	NO_VALIDATE='noValidate' | NO_MODULE_WRAPPING='noModuleWrap';
		public EnumRule getRule() { return rule; }
		
		//NO_VALIDATE='noValidate' | NO_MODULE_WRAPPING='noModuleWrap'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//NO_VALIDATE='noValidate'
		public EnumLiteralDeclaration getNO_VALIDATEEnumLiteralDeclaration_0() { return cNO_VALIDATEEnumLiteralDeclaration_0; }
		
		//'noValidate'
		public Keyword getNO_VALIDATENoValidateKeyword_0_0() { return cNO_VALIDATENoValidateKeyword_0_0; }
		
		//NO_MODULE_WRAPPING='noModuleWrap'
		public EnumLiteralDeclaration getNO_MODULE_WRAPPINGEnumLiteralDeclaration_1() { return cNO_MODULE_WRAPPINGEnumLiteralDeclaration_1; }
		
		//'noModuleWrap'
		public Keyword getNO_MODULE_WRAPPINGNoModuleWrapKeyword_1_0() { return cNO_MODULE_WRAPPINGNoModuleWrapKeyword_1_0; }
	}
	public class ProjectDependencyScopeElements extends AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.ProjectDependencyScope");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cCOMPILEEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cCOMPILECompileKeyword_0_0 = (Keyword)cCOMPILEEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cTESTEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cTESTTestKeyword_1_0 = (Keyword)cTESTEnumLiteralDeclaration_1.eContents().get(0);
		
		//enum ProjectDependencyScope:
		//	COMPILE='compile' | TEST='test';
		public EnumRule getRule() { return rule; }
		
		//COMPILE='compile' | TEST='test'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//COMPILE='compile'
		public EnumLiteralDeclaration getCOMPILEEnumLiteralDeclaration_0() { return cCOMPILEEnumLiteralDeclaration_0; }
		
		//'compile'
		public Keyword getCOMPILECompileKeyword_0_0() { return cCOMPILECompileKeyword_0_0; }
		
		//TEST='test'
		public EnumLiteralDeclaration getTESTEnumLiteralDeclaration_1() { return cTESTEnumLiteralDeclaration_1; }
		
		//'test'
		public Keyword getTESTTestKeyword_1_0() { return cTESTTestKeyword_1_0; }
	}
	public class ModuleLoaderElements extends AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.ModuleLoader");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cN4JSEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cN4JSN4jsKeyword_0_0 = (Keyword)cN4JSEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cCOMMONJSEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cCOMMONJSCommonjsKeyword_1_0 = (Keyword)cCOMMONJSEnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cNODE_BUILTINEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cNODE_BUILTINNode_builtinKeyword_2_0 = (Keyword)cNODE_BUILTINEnumLiteralDeclaration_2.eContents().get(0);
		
		//enum ModuleLoader:
		//	N4JS='n4js'
		//	| COMMONJS='commonjs'
		//	| NODE_BUILTIN='node_builtin';
		public EnumRule getRule() { return rule; }
		
		//N4JS='n4js' | COMMONJS='commonjs' | NODE_BUILTIN='node_builtin'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//N4JS='n4js'
		public EnumLiteralDeclaration getN4JSEnumLiteralDeclaration_0() { return cN4JSEnumLiteralDeclaration_0; }
		
		//'n4js'
		public Keyword getN4JSN4jsKeyword_0_0() { return cN4JSN4jsKeyword_0_0; }
		
		//COMMONJS='commonjs'
		public EnumLiteralDeclaration getCOMMONJSEnumLiteralDeclaration_1() { return cCOMMONJSEnumLiteralDeclaration_1; }
		
		//'commonjs'
		public Keyword getCOMMONJSCommonjsKeyword_1_0() { return cCOMMONJSCommonjsKeyword_1_0; }
		
		//NODE_BUILTIN='node_builtin'
		public EnumLiteralDeclaration getNODE_BUILTINEnumLiteralDeclaration_2() { return cNODE_BUILTINEnumLiteralDeclaration_2; }
		
		//'node_builtin'
		public Keyword getNODE_BUILTINNode_builtinKeyword_2_0() { return cNODE_BUILTINNode_builtinKeyword_2_0; }
	}
	
	private final ProjectDescriptionElements pProjectDescription;
	private final ProjectTypeElements eProjectType;
	private final ExecModuleElements pExecModule;
	private final TestedProjectsElements pTestedProjects;
	private final InitModulesElements pInitModules;
	private final ImplementedProjectsElements pImplementedProjects;
	private final ProjectDependenciesElements pProjectDependencies;
	private final ProvidedRuntimeLibrariesElements pProvidedRuntimeLibraries;
	private final RequiredRuntimeLibrariesElements pRequiredRuntimeLibraries;
	private final ExtendedRuntimeEnvironmentElements pExtendedRuntimeEnvironment;
	private final DeclaredVersionElements pDeclaredVersion;
	private final SourceFragmentElements pSourceFragment;
	private final SourceFragmentTypeElements eSourceFragmentType;
	private final ModuleFilterElements pModuleFilter;
	private final BootstrapModuleElements pBootstrapModule;
	private final ModuleFilterSpecifierElements pModuleFilterSpecifier;
	private final ModuleFilterTypeElements eModuleFilterType;
	private final ProvidedRuntimeLibraryDependencyElements pProvidedRuntimeLibraryDependency;
	private final RequiredRuntimeLibraryDependencyElements pRequiredRuntimeLibraryDependency;
	private final TestedProjectElements pTestedProject;
	private final ProjectReferenceElements pProjectReference;
	private final ProjectDependencyElements pProjectDependency;
	private final SimpleProjectDescriptionElements pSimpleProjectDescription;
	private final VersionConstraintElements pVersionConstraint;
	private final ProjectDependencyScopeElements eProjectDependencyScope;
	private final ModuleLoaderElements eModuleLoader;
	private final N4mfIdentifierElements pN4mfIdentifier;
	private final TerminalRule tID;
	
	private final Grammar grammar;
	
	private final TerminalsGrammarAccess gaTerminals;

	@Inject
	public N4MFGrammarAccess(GrammarProvider grammarProvider,
			TerminalsGrammarAccess gaTerminals) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaTerminals = gaTerminals;
		this.pProjectDescription = new ProjectDescriptionElements();
		this.eProjectType = new ProjectTypeElements();
		this.pExecModule = new ExecModuleElements();
		this.pTestedProjects = new TestedProjectsElements();
		this.pInitModules = new InitModulesElements();
		this.pImplementedProjects = new ImplementedProjectsElements();
		this.pProjectDependencies = new ProjectDependenciesElements();
		this.pProvidedRuntimeLibraries = new ProvidedRuntimeLibrariesElements();
		this.pRequiredRuntimeLibraries = new RequiredRuntimeLibrariesElements();
		this.pExtendedRuntimeEnvironment = new ExtendedRuntimeEnvironmentElements();
		this.pDeclaredVersion = new DeclaredVersionElements();
		this.pSourceFragment = new SourceFragmentElements();
		this.eSourceFragmentType = new SourceFragmentTypeElements();
		this.pModuleFilter = new ModuleFilterElements();
		this.pBootstrapModule = new BootstrapModuleElements();
		this.pModuleFilterSpecifier = new ModuleFilterSpecifierElements();
		this.eModuleFilterType = new ModuleFilterTypeElements();
		this.pProvidedRuntimeLibraryDependency = new ProvidedRuntimeLibraryDependencyElements();
		this.pRequiredRuntimeLibraryDependency = new RequiredRuntimeLibraryDependencyElements();
		this.pTestedProject = new TestedProjectElements();
		this.pProjectReference = new ProjectReferenceElements();
		this.pProjectDependency = new ProjectDependencyElements();
		this.pSimpleProjectDescription = new SimpleProjectDescriptionElements();
		this.pVersionConstraint = new VersionConstraintElements();
		this.eProjectDependencyScope = new ProjectDependencyScopeElements();
		this.eModuleLoader = new ModuleLoaderElements();
		this.pN4mfIdentifier = new N4mfIdentifierElements();
		this.tID = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.ID");
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("org.eclipse.n4js.n4mf.N4MF".equals(grammar.getName())) {
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
	
	
	public TerminalsGrammarAccess getTerminalsGrammarAccess() {
		return gaTerminals;
	}

	
	//ProjectDescription:
	//	'ProjectId' ':' projectId=N4mfIdentifier & 'ProjectType' ':' projectType=ProjectType & 'ProjectVersion' ':'
	//	projectVersion=DeclaredVersion & 'VendorId' ':' declaredVendorId=N4mfIdentifier & ('VendorName' ':'
	//	vendorName=STRING)?
	//	& ('MainModule' ':' mainModule=STRING)?
	//	// only available for runtime environments
	//	& extendedRuntimeEnvironment=ExtendedRuntimeEnvironment?
	//	// only in case of runtime libraries or runtime environment:
	//	& providedRuntimeLibraries=ProvidedRuntimeLibraries?
	//	// not available in runtime environments:
	//	& requiredRuntimeLibraries=RequiredRuntimeLibraries?
	//	// only available in N4JS components (Apps, Libs, Processor)
	//	& projectDependencies=ProjectDependencies?
	//	// only available in N4JS components (Apps, Libs, Processor)
	//	& ('ImplementationId' ':' implementationId=N4mfIdentifier)?
	//	// only available in N4JS components (Apps, Libs, Processor)
	//	& implementedProjects=ImplementedProjects?
	//	//only RuntimeLibrary and RuntimeEnvironemnt
	//	& initModules=InitModules?
	//	& execModule=ExecModule?
	//	& ('Output' ':' outputPath=STRING)?
	//	& ('Libraries' '{' libraryPaths+=STRING (',' libraryPaths+=STRING)* '}')?
	//	& ('Resources' '{' resourcePaths+=STRING (',' resourcePaths+=STRING)* '}')?
	//	& ('Sources' '{' sourceFragment+=SourceFragment+ '}')?
	//	& ('ModuleFilters' '{' moduleFilters+=ModuleFilter+ '}')?
	//	& testedProjects=TestedProjects?
	//	& ('ModuleLoader' ':' moduleLoader=ModuleLoader)?;
	public ProjectDescriptionElements getProjectDescriptionAccess() {
		return pProjectDescription;
	}
	
	public ParserRule getProjectDescriptionRule() {
		return getProjectDescriptionAccess().getRule();
	}
	
	//enum ProjectType:
	//	APPLICATION='application' |
	//	PROCESSOR='processor' |
	//	LIBRARY='library' |
	//	API |
	//	RUNTIME_ENVIRONMENT="runtimeEnvironment" |
	//	RUNTIME_LIBRARY="runtimeLibrary" |
	//	TEST="test";
	public ProjectTypeElements getProjectTypeAccess() {
		return eProjectType;
	}
	
	public EnumRule getProjectTypeRule() {
		return getProjectTypeAccess().getRule();
	}
	
	//ExecModule:
	//	{ExecModule}
	//	'ExecModule' ':' execModule=BootstrapModule;
	public ExecModuleElements getExecModuleAccess() {
		return pExecModule;
	}
	
	public ParserRule getExecModuleRule() {
		return getExecModuleAccess().getRule();
	}
	
	//TestedProjects:
	//	{TestedProjects}
	//	'TestedProjects' '{' (testedProjects+=TestedProject (',' testedProjects+=TestedProject)*)?
	//	'}';
	public TestedProjectsElements getTestedProjectsAccess() {
		return pTestedProjects;
	}
	
	public ParserRule getTestedProjectsRule() {
		return getTestedProjectsAccess().getRule();
	}
	
	//InitModules:
	//	{InitModules}
	//	'InitModules' '{' (initModules+=BootstrapModule (',' initModules+=BootstrapModule)*)?
	//	'}';
	public InitModulesElements getInitModulesAccess() {
		return pInitModules;
	}
	
	public ParserRule getInitModulesRule() {
		return getInitModulesAccess().getRule();
	}
	
	//ImplementedProjects:
	//	{ImplementedProjects}
	//	'ImplementedProjects' '{' (implementedProjects+=ProjectReference (',' implementedProjects+=ProjectReference)*)?
	//	'}';
	public ImplementedProjectsElements getImplementedProjectsAccess() {
		return pImplementedProjects;
	}
	
	public ParserRule getImplementedProjectsRule() {
		return getImplementedProjectsAccess().getRule();
	}
	
	//ProjectDependencies:
	//	{ProjectDependencies}
	//	'ProjectDependencies' '{' (projectDependencies+=ProjectDependency (',' projectDependencies+=ProjectDependency)*)?
	//	'}';
	public ProjectDependenciesElements getProjectDependenciesAccess() {
		return pProjectDependencies;
	}
	
	public ParserRule getProjectDependenciesRule() {
		return getProjectDependenciesAccess().getRule();
	}
	
	//ProvidedRuntimeLibraries:
	//	{ProvidedRuntimeLibraries}
	//	'ProvidedRuntimeLibraries' '{' (providedRuntimeLibraries+=ProvidedRuntimeLibraryDependency (','
	//	providedRuntimeLibraries+=ProvidedRuntimeLibraryDependency)*)?
	//	'}';
	public ProvidedRuntimeLibrariesElements getProvidedRuntimeLibrariesAccess() {
		return pProvidedRuntimeLibraries;
	}
	
	public ParserRule getProvidedRuntimeLibrariesRule() {
		return getProvidedRuntimeLibrariesAccess().getRule();
	}
	
	//RequiredRuntimeLibraries:
	//	{RequiredRuntimeLibraries}
	//	'RequiredRuntimeLibraries' '{' (requiredRuntimeLibraries+=RequiredRuntimeLibraryDependency (','
	//	requiredRuntimeLibraries+=RequiredRuntimeLibraryDependency)*)?
	//	'}';
	public RequiredRuntimeLibrariesElements getRequiredRuntimeLibrariesAccess() {
		return pRequiredRuntimeLibraries;
	}
	
	public ParserRule getRequiredRuntimeLibrariesRule() {
		return getRequiredRuntimeLibrariesAccess().getRule();
	}
	
	//ExtendedRuntimeEnvironment:
	//	{ExtendedRuntimeEnvironment}
	//	'ExtendedRuntimeEnvironment' ':' extendedRuntimeEnvironment=ProjectReference;
	public ExtendedRuntimeEnvironmentElements getExtendedRuntimeEnvironmentAccess() {
		return pExtendedRuntimeEnvironment;
	}
	
	public ParserRule getExtendedRuntimeEnvironmentRule() {
		return getExtendedRuntimeEnvironmentAccess().getRule();
	}
	
	////TODO build meta data not supported https://semver.org/#spec-item-10
	//DeclaredVersion:
	//	major=INT ('.' minor=INT ('.' micro=INT)?)? ('-' qualifier=N4mfIdentifier)?;
	public DeclaredVersionElements getDeclaredVersionAccess() {
		return pDeclaredVersion;
	}
	
	public ParserRule getDeclaredVersionRule() {
		return getDeclaredVersionAccess().getRule();
	}
	
	//SourceFragment:
	//	sourceFragmentType=SourceFragmentType '{' paths+=STRING (',' paths+=STRING)* '}';
	public SourceFragmentElements getSourceFragmentAccess() {
		return pSourceFragment;
	}
	
	public ParserRule getSourceFragmentRule() {
		return getSourceFragmentAccess().getRule();
	}
	
	//enum SourceFragmentType:
	//	SOURCE='source' | EXTERNAL='external' | TEST='test';
	public SourceFragmentTypeElements getSourceFragmentTypeAccess() {
		return eSourceFragmentType;
	}
	
	public EnumRule getSourceFragmentTypeRule() {
		return getSourceFragmentTypeAccess().getRule();
	}
	
	//ModuleFilter:
	//	moduleFilterType=ModuleFilterType '{'
	//	moduleSpecifiers+=ModuleFilterSpecifier (',' moduleSpecifiers+=ModuleFilterSpecifier)* '}';
	public ModuleFilterElements getModuleFilterAccess() {
		return pModuleFilter;
	}
	
	public ParserRule getModuleFilterRule() {
		return getModuleFilterAccess().getRule();
	}
	
	//BootstrapModule:
	//	moduleSpecifierWithWildcard=STRING ('in' sourcePath=STRING)?;
	public BootstrapModuleElements getBootstrapModuleAccess() {
		return pBootstrapModule;
	}
	
	public ParserRule getBootstrapModuleRule() {
		return getBootstrapModuleAccess().getRule();
	}
	
	//ModuleFilterSpecifier:
	//	moduleSpecifierWithWildcard=STRING ('in' sourcePath=STRING)?;
	public ModuleFilterSpecifierElements getModuleFilterSpecifierAccess() {
		return pModuleFilterSpecifier;
	}
	
	public ParserRule getModuleFilterSpecifierRule() {
		return getModuleFilterSpecifierAccess().getRule();
	}
	
	//enum ModuleFilterType:
	//	NO_VALIDATE='noValidate' | NO_MODULE_WRAPPING='noModuleWrap';
	public ModuleFilterTypeElements getModuleFilterTypeAccess() {
		return eModuleFilterType;
	}
	
	public EnumRule getModuleFilterTypeRule() {
		return getModuleFilterTypeAccess().getRule();
	}
	
	//ProvidedRuntimeLibraryDependency:
	//	project=SimpleProjectDescription;
	public ProvidedRuntimeLibraryDependencyElements getProvidedRuntimeLibraryDependencyAccess() {
		return pProvidedRuntimeLibraryDependency;
	}
	
	public ParserRule getProvidedRuntimeLibraryDependencyRule() {
		return getProvidedRuntimeLibraryDependencyAccess().getRule();
	}
	
	//RequiredRuntimeLibraryDependency:
	//	project=SimpleProjectDescription;
	public RequiredRuntimeLibraryDependencyElements getRequiredRuntimeLibraryDependencyAccess() {
		return pRequiredRuntimeLibraryDependency;
	}
	
	public ParserRule getRequiredRuntimeLibraryDependencyRule() {
		return getRequiredRuntimeLibraryDependencyAccess().getRule();
	}
	
	//TestedProject:
	//	project=SimpleProjectDescription;
	public TestedProjectElements getTestedProjectAccess() {
		return pTestedProject;
	}
	
	public ParserRule getTestedProjectRule() {
		return getTestedProjectAccess().getRule();
	}
	
	///*
	// * scope is optional, default scope is compile
	// */ ProjectReference:
	//	project=SimpleProjectDescription;
	public ProjectReferenceElements getProjectReferenceAccess() {
		return pProjectReference;
	}
	
	public ParserRule getProjectReferenceRule() {
		return getProjectReferenceAccess().getRule();
	}
	
	///*
	// * scope is optional, default scope is compile
	// */ ProjectDependency:
	//	project=SimpleProjectDescription
	//	versionConstraint=VersionConstraint?
	//	declaredScope=ProjectDependencyScope?;
	public ProjectDependencyElements getProjectDependencyAccess() {
		return pProjectDependency;
	}
	
	public ParserRule getProjectDependencyRule() {
		return getProjectDependencyAccess().getRule();
	}
	
	///*
	// * vendorN4mfIdentifier is optional, if it is not specified, vendor id of current project is used.
	// */ SimpleProjectDescription:
	//	(declaredVendorId=N4mfIdentifier ':')? projectId=N4mfIdentifier;
	public SimpleProjectDescriptionElements getSimpleProjectDescriptionAccess() {
		return pSimpleProjectDescription;
	}
	
	public ParserRule getSimpleProjectDescriptionRule() {
		return getSimpleProjectDescriptionAccess().getRule();
	}
	
	///*
	// * If no version range is specified, lower version is inclusive.
	// */ VersionConstraint:
	//	(exclLowerBound?='(' | '[') lowerVersion=DeclaredVersion ((',' upperVersion=DeclaredVersion (exclUpperBound?=')' |
	//	']'))? | ')') | lowerVersion=DeclaredVersion;
	public VersionConstraintElements getVersionConstraintAccess() {
		return pVersionConstraint;
	}
	
	public ParserRule getVersionConstraintRule() {
		return getVersionConstraintAccess().getRule();
	}
	
	//enum ProjectDependencyScope:
	//	COMPILE='compile' | TEST='test';
	public ProjectDependencyScopeElements getProjectDependencyScopeAccess() {
		return eProjectDependencyScope;
	}
	
	public EnumRule getProjectDependencyScopeRule() {
		return getProjectDependencyScopeAccess().getRule();
	}
	
	//enum ModuleLoader:
	//	N4JS='n4js'
	//	| COMMONJS='commonjs'
	//	| NODE_BUILTIN='node_builtin';
	public ModuleLoaderElements getModuleLoaderAccess() {
		return eModuleLoader;
	}
	
	public EnumRule getModuleLoaderRule() {
		return getModuleLoaderAccess().getRule();
	}
	
	//N4mfIdentifier:
	//	ID |
	//	'ProjectId' |
	//	'ProjectType' |
	//	'ProjectVersion' |
	//	'VendorId' |
	//	'VendorName' |
	//	'Output' |
	//	'Libraries' |
	//	'Resources' |
	//	'Sources' |
	//	'ModuleFilters' |
	//	'ProjectDependencies'
	//	'system' |
	//	'API' |
	//	'user' |
	//	'application' |
	//	'processor'
	//	'source' |
	//	'content' |
	//	'test';
	public N4mfIdentifierElements getN4mfIdentifierAccess() {
		return pN4mfIdentifier;
	}
	
	public ParserRule getN4mfIdentifierRule() {
		return getN4mfIdentifierAccess().getRule();
	}
	
	//@ Override terminal ID:
	//	'^'? ('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '-' | '.' | '0'..'9')*;
	public TerminalRule getIDRule() {
		return tID;
	}
	
	//terminal INT returns ecore::EInt:
	//	'0'..'9'+;
	public TerminalRule getINTRule() {
		return gaTerminals.getINTRule();
	}
	
	//terminal STRING:
	//	'"' ('\\' . | !('\\' | '"'))* '"' |
	//	"'" ('\\' . | !('\\' | "'"))* "'";
	public TerminalRule getSTRINGRule() {
		return gaTerminals.getSTRINGRule();
	}
	
	//terminal ML_COMMENT:
	//	'/*'->'*/';
	public TerminalRule getML_COMMENTRule() {
		return gaTerminals.getML_COMMENTRule();
	}
	
	//terminal SL_COMMENT:
	//	'//' !('\n' | '\r')* ('\r'? '\n')?;
	public TerminalRule getSL_COMMENTRule() {
		return gaTerminals.getSL_COMMENTRule();
	}
	
	//terminal WS:
	//	' ' | '\t' | '\r' | '\n'+;
	public TerminalRule getWSRule() {
		return gaTerminals.getWSRule();
	}
	
	//terminal ANY_OTHER:
	//	.;
	public TerminalRule getANY_OTHERRule() {
		return gaTerminals.getANY_OTHERRule();
	}
}
