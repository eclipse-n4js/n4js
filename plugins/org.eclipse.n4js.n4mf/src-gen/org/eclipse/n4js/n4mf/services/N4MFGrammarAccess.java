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
		private final Assignment cVendorIdAssignment_3_2 = (Assignment)cGroup_3.eContents().get(2);
		private final RuleCall cVendorIdN4mfIdentifierParserRuleCall_3_2_0 = (RuleCall)cVendorIdAssignment_3_2.eContents().get(0);
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
		private final Group cGroup_6 = (Group)cUnorderedGroup.eContents().get(6);
		private final Keyword cExtendedRuntimeEnvironmentKeyword_6_0 = (Keyword)cGroup_6.eContents().get(0);
		private final Keyword cColonKeyword_6_1 = (Keyword)cGroup_6.eContents().get(1);
		private final Assignment cExtendedRuntimeEnvironmentAssignment_6_2 = (Assignment)cGroup_6.eContents().get(2);
		private final RuleCall cExtendedRuntimeEnvironmentProjectReferenceParserRuleCall_6_2_0 = (RuleCall)cExtendedRuntimeEnvironmentAssignment_6_2.eContents().get(0);
		private final Group cGroup_7 = (Group)cUnorderedGroup.eContents().get(7);
		private final Keyword cProvidedRuntimeLibrariesKeyword_7_0 = (Keyword)cGroup_7.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_7_1 = (Keyword)cGroup_7.eContents().get(1);
		private final Group cGroup_7_2 = (Group)cGroup_7.eContents().get(2);
		private final Assignment cProvidedRuntimeLibrariesAssignment_7_2_0 = (Assignment)cGroup_7_2.eContents().get(0);
		private final RuleCall cProvidedRuntimeLibrariesProjectReferenceParserRuleCall_7_2_0_0 = (RuleCall)cProvidedRuntimeLibrariesAssignment_7_2_0.eContents().get(0);
		private final Group cGroup_7_2_1 = (Group)cGroup_7_2.eContents().get(1);
		private final Keyword cCommaKeyword_7_2_1_0 = (Keyword)cGroup_7_2_1.eContents().get(0);
		private final Assignment cProvidedRuntimeLibrariesAssignment_7_2_1_1 = (Assignment)cGroup_7_2_1.eContents().get(1);
		private final RuleCall cProvidedRuntimeLibrariesProjectReferenceParserRuleCall_7_2_1_1_0 = (RuleCall)cProvidedRuntimeLibrariesAssignment_7_2_1_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_7_3 = (Keyword)cGroup_7.eContents().get(3);
		private final Group cGroup_8 = (Group)cUnorderedGroup.eContents().get(8);
		private final Keyword cRequiredRuntimeLibrariesKeyword_8_0 = (Keyword)cGroup_8.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_8_1 = (Keyword)cGroup_8.eContents().get(1);
		private final Group cGroup_8_2 = (Group)cGroup_8.eContents().get(2);
		private final Assignment cRequiredRuntimeLibrariesAssignment_8_2_0 = (Assignment)cGroup_8_2.eContents().get(0);
		private final RuleCall cRequiredRuntimeLibrariesProjectReferenceParserRuleCall_8_2_0_0 = (RuleCall)cRequiredRuntimeLibrariesAssignment_8_2_0.eContents().get(0);
		private final Group cGroup_8_2_1 = (Group)cGroup_8_2.eContents().get(1);
		private final Keyword cCommaKeyword_8_2_1_0 = (Keyword)cGroup_8_2_1.eContents().get(0);
		private final Assignment cRequiredRuntimeLibrariesAssignment_8_2_1_1 = (Assignment)cGroup_8_2_1.eContents().get(1);
		private final RuleCall cRequiredRuntimeLibrariesProjectReferenceParserRuleCall_8_2_1_1_0 = (RuleCall)cRequiredRuntimeLibrariesAssignment_8_2_1_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_8_3 = (Keyword)cGroup_8.eContents().get(3);
		private final Group cGroup_9 = (Group)cUnorderedGroup.eContents().get(9);
		private final Keyword cProjectDependenciesKeyword_9_0 = (Keyword)cGroup_9.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_9_1 = (Keyword)cGroup_9.eContents().get(1);
		private final Group cGroup_9_2 = (Group)cGroup_9.eContents().get(2);
		private final Assignment cProjectDependenciesAssignment_9_2_0 = (Assignment)cGroup_9_2.eContents().get(0);
		private final RuleCall cProjectDependenciesProjectDependencyParserRuleCall_9_2_0_0 = (RuleCall)cProjectDependenciesAssignment_9_2_0.eContents().get(0);
		private final Group cGroup_9_2_1 = (Group)cGroup_9_2.eContents().get(1);
		private final Keyword cCommaKeyword_9_2_1_0 = (Keyword)cGroup_9_2_1.eContents().get(0);
		private final Assignment cProjectDependenciesAssignment_9_2_1_1 = (Assignment)cGroup_9_2_1.eContents().get(1);
		private final RuleCall cProjectDependenciesProjectDependencyParserRuleCall_9_2_1_1_0 = (RuleCall)cProjectDependenciesAssignment_9_2_1_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_9_3 = (Keyword)cGroup_9.eContents().get(3);
		private final Group cGroup_10 = (Group)cUnorderedGroup.eContents().get(10);
		private final Keyword cImplementationIdKeyword_10_0 = (Keyword)cGroup_10.eContents().get(0);
		private final Keyword cColonKeyword_10_1 = (Keyword)cGroup_10.eContents().get(1);
		private final Assignment cImplementationIdAssignment_10_2 = (Assignment)cGroup_10.eContents().get(2);
		private final RuleCall cImplementationIdN4mfIdentifierParserRuleCall_10_2_0 = (RuleCall)cImplementationIdAssignment_10_2.eContents().get(0);
		private final Group cGroup_11 = (Group)cUnorderedGroup.eContents().get(11);
		private final Keyword cImplementedProjectsKeyword_11_0 = (Keyword)cGroup_11.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_11_1 = (Keyword)cGroup_11.eContents().get(1);
		private final Group cGroup_11_2 = (Group)cGroup_11.eContents().get(2);
		private final Assignment cImplementedProjectsAssignment_11_2_0 = (Assignment)cGroup_11_2.eContents().get(0);
		private final RuleCall cImplementedProjectsProjectReferenceParserRuleCall_11_2_0_0 = (RuleCall)cImplementedProjectsAssignment_11_2_0.eContents().get(0);
		private final Group cGroup_11_2_1 = (Group)cGroup_11_2.eContents().get(1);
		private final Keyword cCommaKeyword_11_2_1_0 = (Keyword)cGroup_11_2_1.eContents().get(0);
		private final Assignment cImplementedProjectsAssignment_11_2_1_1 = (Assignment)cGroup_11_2_1.eContents().get(1);
		private final RuleCall cImplementedProjectsProjectReferenceParserRuleCall_11_2_1_1_0 = (RuleCall)cImplementedProjectsAssignment_11_2_1_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_11_3 = (Keyword)cGroup_11.eContents().get(3);
		private final Group cGroup_12 = (Group)cUnorderedGroup.eContents().get(12);
		private final Keyword cInitModulesKeyword_12_0 = (Keyword)cGroup_12.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_12_1 = (Keyword)cGroup_12.eContents().get(1);
		private final Group cGroup_12_2 = (Group)cGroup_12.eContents().get(2);
		private final Assignment cInitModulesAssignment_12_2_0 = (Assignment)cGroup_12_2.eContents().get(0);
		private final RuleCall cInitModulesBootstrapModuleParserRuleCall_12_2_0_0 = (RuleCall)cInitModulesAssignment_12_2_0.eContents().get(0);
		private final Group cGroup_12_2_1 = (Group)cGroup_12_2.eContents().get(1);
		private final Keyword cCommaKeyword_12_2_1_0 = (Keyword)cGroup_12_2_1.eContents().get(0);
		private final Assignment cInitModulesAssignment_12_2_1_1 = (Assignment)cGroup_12_2_1.eContents().get(1);
		private final RuleCall cInitModulesBootstrapModuleParserRuleCall_12_2_1_1_0 = (RuleCall)cInitModulesAssignment_12_2_1_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_12_3 = (Keyword)cGroup_12.eContents().get(3);
		private final Group cGroup_13 = (Group)cUnorderedGroup.eContents().get(13);
		private final Keyword cExecModuleKeyword_13_0 = (Keyword)cGroup_13.eContents().get(0);
		private final Keyword cColonKeyword_13_1 = (Keyword)cGroup_13.eContents().get(1);
		private final Assignment cExecModuleAssignment_13_2 = (Assignment)cGroup_13.eContents().get(2);
		private final RuleCall cExecModuleBootstrapModuleParserRuleCall_13_2_0 = (RuleCall)cExecModuleAssignment_13_2.eContents().get(0);
		private final Group cGroup_14 = (Group)cUnorderedGroup.eContents().get(14);
		private final Keyword cOutputKeyword_14_0 = (Keyword)cGroup_14.eContents().get(0);
		private final Keyword cColonKeyword_14_1 = (Keyword)cGroup_14.eContents().get(1);
		private final Assignment cOutputPathRawAssignment_14_2 = (Assignment)cGroup_14.eContents().get(2);
		private final RuleCall cOutputPathRawSTRINGTerminalRuleCall_14_2_0 = (RuleCall)cOutputPathRawAssignment_14_2.eContents().get(0);
		private final Group cGroup_15 = (Group)cUnorderedGroup.eContents().get(15);
		private final Keyword cLibrariesKeyword_15_0 = (Keyword)cGroup_15.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_15_1 = (Keyword)cGroup_15.eContents().get(1);
		private final Assignment cLibraryPathsRawAssignment_15_2 = (Assignment)cGroup_15.eContents().get(2);
		private final RuleCall cLibraryPathsRawSTRINGTerminalRuleCall_15_2_0 = (RuleCall)cLibraryPathsRawAssignment_15_2.eContents().get(0);
		private final Group cGroup_15_3 = (Group)cGroup_15.eContents().get(3);
		private final Keyword cCommaKeyword_15_3_0 = (Keyword)cGroup_15_3.eContents().get(0);
		private final Assignment cLibraryPathsRawAssignment_15_3_1 = (Assignment)cGroup_15_3.eContents().get(1);
		private final RuleCall cLibraryPathsRawSTRINGTerminalRuleCall_15_3_1_0 = (RuleCall)cLibraryPathsRawAssignment_15_3_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_15_4 = (Keyword)cGroup_15.eContents().get(4);
		private final Group cGroup_16 = (Group)cUnorderedGroup.eContents().get(16);
		private final Keyword cResourcesKeyword_16_0 = (Keyword)cGroup_16.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_16_1 = (Keyword)cGroup_16.eContents().get(1);
		private final Assignment cResourcePathsRawAssignment_16_2 = (Assignment)cGroup_16.eContents().get(2);
		private final RuleCall cResourcePathsRawSTRINGTerminalRuleCall_16_2_0 = (RuleCall)cResourcePathsRawAssignment_16_2.eContents().get(0);
		private final Group cGroup_16_3 = (Group)cGroup_16.eContents().get(3);
		private final Keyword cCommaKeyword_16_3_0 = (Keyword)cGroup_16_3.eContents().get(0);
		private final Assignment cResourcePathsRawAssignment_16_3_1 = (Assignment)cGroup_16_3.eContents().get(1);
		private final RuleCall cResourcePathsRawSTRINGTerminalRuleCall_16_3_1_0 = (RuleCall)cResourcePathsRawAssignment_16_3_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_16_4 = (Keyword)cGroup_16.eContents().get(4);
		private final Group cGroup_17 = (Group)cUnorderedGroup.eContents().get(17);
		private final Keyword cSourcesKeyword_17_0 = (Keyword)cGroup_17.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_17_1 = (Keyword)cGroup_17.eContents().get(1);
		private final Assignment cSourceContainersAssignment_17_2 = (Assignment)cGroup_17.eContents().get(2);
		private final RuleCall cSourceContainersSourceContainerDescriptionParserRuleCall_17_2_0 = (RuleCall)cSourceContainersAssignment_17_2.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_17_3 = (Keyword)cGroup_17.eContents().get(3);
		private final Group cGroup_18 = (Group)cUnorderedGroup.eContents().get(18);
		private final Keyword cModuleFiltersKeyword_18_0 = (Keyword)cGroup_18.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_18_1 = (Keyword)cGroup_18.eContents().get(1);
		private final Assignment cModuleFiltersAssignment_18_2 = (Assignment)cGroup_18.eContents().get(2);
		private final RuleCall cModuleFiltersModuleFilterParserRuleCall_18_2_0 = (RuleCall)cModuleFiltersAssignment_18_2.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_18_3 = (Keyword)cGroup_18.eContents().get(3);
		private final Group cGroup_19 = (Group)cUnorderedGroup.eContents().get(19);
		private final Keyword cTestedProjectsKeyword_19_0 = (Keyword)cGroup_19.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_19_1 = (Keyword)cGroup_19.eContents().get(1);
		private final Group cGroup_19_2 = (Group)cGroup_19.eContents().get(2);
		private final Assignment cTestedProjectsAssignment_19_2_0 = (Assignment)cGroup_19_2.eContents().get(0);
		private final RuleCall cTestedProjectsProjectDependencyParserRuleCall_19_2_0_0 = (RuleCall)cTestedProjectsAssignment_19_2_0.eContents().get(0);
		private final Group cGroup_19_2_1 = (Group)cGroup_19_2.eContents().get(1);
		private final Keyword cCommaKeyword_19_2_1_0 = (Keyword)cGroup_19_2_1.eContents().get(0);
		private final Assignment cTestedProjectsAssignment_19_2_1_1 = (Assignment)cGroup_19_2_1.eContents().get(1);
		private final RuleCall cTestedProjectsProjectDependencyParserRuleCall_19_2_1_1_0 = (RuleCall)cTestedProjectsAssignment_19_2_1_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_19_3 = (Keyword)cGroup_19.eContents().get(3);
		private final Group cGroup_20 = (Group)cUnorderedGroup.eContents().get(20);
		private final Keyword cModuleLoaderKeyword_20_0 = (Keyword)cGroup_20.eContents().get(0);
		private final Keyword cColonKeyword_20_1 = (Keyword)cGroup_20.eContents().get(1);
		private final Assignment cModuleLoaderAssignment_20_2 = (Assignment)cGroup_20.eContents().get(2);
		private final RuleCall cModuleLoaderModuleLoaderEnumRuleCall_20_2_0 = (RuleCall)cModuleLoaderAssignment_20_2.eContents().get(0);
		
		//ProjectDescription:
		//	'ProjectId' ':' projectId=N4mfIdentifier & 'ProjectType' ':' projectType=ProjectType & 'ProjectVersion' ':'
		//	projectVersion=DeclaredVersion & 'VendorId' ':' vendorId=N4mfIdentifier & ('VendorName' ':' vendorName=STRING)?
		//	& ('MainModule' ':' mainModule=STRING)?
		//	// only available for runtime environments
		//	& ('ExtendedRuntimeEnvironment' ':' extendedRuntimeEnvironment=ProjectReference)?
		//	// only in case of runtime libraries or runtime environment:
		//	& ('ProvidedRuntimeLibraries' '{' (providedRuntimeLibraries+=ProjectReference (','
		//	providedRuntimeLibraries+=ProjectReference)*)? '}')?
		//	// not available in runtime environments:
		//	& ('RequiredRuntimeLibraries' '{' (requiredRuntimeLibraries+=ProjectReference (','
		//	requiredRuntimeLibraries+=ProjectReference)*)? '}')?
		//	// only available in N4JS components (Apps, Libs, Processor)
		//	& ('ProjectDependencies' '{' (projectDependencies+=ProjectDependency (',' projectDependencies+=ProjectDependency)*)?
		//	'}')?
		//	// only available in N4JS components (Apps, Libs, Processor)
		//	& ('ImplementationId' ':' implementationId=N4mfIdentifier)?
		//	// only available in N4JS components (Apps, Libs, Processor)
		//	& ('ImplementedProjects' '{' (implementedProjects+=ProjectReference (',' implementedProjects+=ProjectReference)*)?
		//	'}')?
		//	//only RuntimeLibrary and RuntimeEnvironemnt
		//	& ('InitModules' '{' (initModules+=BootstrapModule (',' initModules+=BootstrapModule)*)? '}')?
		//	& ('ExecModule' ':' execModule=BootstrapModule)?
		//	& ('Output' ':' outputPathRaw=STRING)?
		//	& ('Libraries' '{' libraryPathsRaw+=STRING (',' libraryPathsRaw+=STRING)* '}')?
		//	& ('Resources' '{' resourcePathsRaw+=STRING (',' resourcePathsRaw+=STRING)* '}')?
		//	& ('Sources' '{' sourceContainers+=SourceContainerDescription+ '}')?
		//	& ('ModuleFilters' '{' moduleFilters+=ModuleFilter+ '}')?
		//	& ('TestedProjects' '{' (testedProjects+=ProjectDependency (',' testedProjects+=ProjectDependency)*)? '}')?
		//	& ('ModuleLoader' ':' moduleLoader=ModuleLoader)?;
		@Override public ParserRule getRule() { return rule; }
		
		//'ProjectId' ':' projectId=N4mfIdentifier & 'ProjectType' ':' projectType=ProjectType & 'ProjectVersion' ':'
		//projectVersion=DeclaredVersion & 'VendorId' ':' vendorId=N4mfIdentifier & ('VendorName' ':' vendorName=STRING)? &
		//('MainModule' ':' mainModule=STRING)? // only available for runtime environments
		//& ('ExtendedRuntimeEnvironment' ':' extendedRuntimeEnvironment=ProjectReference)? // only in case of runtime libraries or runtime environment:
		//& ('ProvidedRuntimeLibraries' '{' (providedRuntimeLibraries+=ProjectReference (','
		//providedRuntimeLibraries+=ProjectReference)*)? '}')? // not available in runtime environments:
		//& ('RequiredRuntimeLibraries' '{' (requiredRuntimeLibraries+=ProjectReference (','
		//requiredRuntimeLibraries+=ProjectReference)*)? '}')? // only available in N4JS components (Apps, Libs, Processor)
		//& ('ProjectDependencies' '{' (projectDependencies+=ProjectDependency (',' projectDependencies+=ProjectDependency)*)?
		//'}')? // only available in N4JS components (Apps, Libs, Processor)
		//& ('ImplementationId' ':' implementationId=N4mfIdentifier)? // only available in N4JS components (Apps, Libs, Processor)
		//& ('ImplementedProjects' '{' (implementedProjects+=ProjectReference (',' implementedProjects+=ProjectReference)*)? '}')? //only RuntimeLibrary and RuntimeEnvironemnt
		//& ('InitModules' '{' (initModules+=BootstrapModule (',' initModules+=BootstrapModule)*)? '}')? & ('ExecModule' ':'
		//execModule=BootstrapModule)? & ('Output' ':' outputPathRaw=STRING)? & ('Libraries' '{' libraryPathsRaw+=STRING (','
		//libraryPathsRaw+=STRING)* '}')? & ('Resources' '{' resourcePathsRaw+=STRING (',' resourcePathsRaw+=STRING)* '}')? &
		//('Sources' '{' sourceContainers+=SourceContainerDescription+ '}')? & ('ModuleFilters' '{' moduleFilters+=ModuleFilter+
		//'}')? & ('TestedProjects' '{' (testedProjects+=ProjectDependency (',' testedProjects+=ProjectDependency)*)? '}')? &
		//('ModuleLoader' ':' moduleLoader=ModuleLoader)?
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
		
		//'VendorId' ':' vendorId=N4mfIdentifier
		public Group getGroup_3() { return cGroup_3; }
		
		//'VendorId'
		public Keyword getVendorIdKeyword_3_0() { return cVendorIdKeyword_3_0; }
		
		//':'
		public Keyword getColonKeyword_3_1() { return cColonKeyword_3_1; }
		
		//vendorId=N4mfIdentifier
		public Assignment getVendorIdAssignment_3_2() { return cVendorIdAssignment_3_2; }
		
		//N4mfIdentifier
		public RuleCall getVendorIdN4mfIdentifierParserRuleCall_3_2_0() { return cVendorIdN4mfIdentifierParserRuleCall_3_2_0; }
		
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
		
		//('ExtendedRuntimeEnvironment' ':' extendedRuntimeEnvironment=ProjectReference)?
		public Group getGroup_6() { return cGroup_6; }
		
		//'ExtendedRuntimeEnvironment'
		public Keyword getExtendedRuntimeEnvironmentKeyword_6_0() { return cExtendedRuntimeEnvironmentKeyword_6_0; }
		
		//':'
		public Keyword getColonKeyword_6_1() { return cColonKeyword_6_1; }
		
		//extendedRuntimeEnvironment=ProjectReference
		public Assignment getExtendedRuntimeEnvironmentAssignment_6_2() { return cExtendedRuntimeEnvironmentAssignment_6_2; }
		
		//ProjectReference
		public RuleCall getExtendedRuntimeEnvironmentProjectReferenceParserRuleCall_6_2_0() { return cExtendedRuntimeEnvironmentProjectReferenceParserRuleCall_6_2_0; }
		
		//('ProvidedRuntimeLibraries' '{' (providedRuntimeLibraries+=ProjectReference (','
		//providedRuntimeLibraries+=ProjectReference)*)? '}')?
		public Group getGroup_7() { return cGroup_7; }
		
		//'ProvidedRuntimeLibraries'
		public Keyword getProvidedRuntimeLibrariesKeyword_7_0() { return cProvidedRuntimeLibrariesKeyword_7_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_7_1() { return cLeftCurlyBracketKeyword_7_1; }
		
		//(providedRuntimeLibraries+=ProjectReference (',' providedRuntimeLibraries+=ProjectReference)*)?
		public Group getGroup_7_2() { return cGroup_7_2; }
		
		//providedRuntimeLibraries+=ProjectReference
		public Assignment getProvidedRuntimeLibrariesAssignment_7_2_0() { return cProvidedRuntimeLibrariesAssignment_7_2_0; }
		
		//ProjectReference
		public RuleCall getProvidedRuntimeLibrariesProjectReferenceParserRuleCall_7_2_0_0() { return cProvidedRuntimeLibrariesProjectReferenceParserRuleCall_7_2_0_0; }
		
		//(',' providedRuntimeLibraries+=ProjectReference)*
		public Group getGroup_7_2_1() { return cGroup_7_2_1; }
		
		//','
		public Keyword getCommaKeyword_7_2_1_0() { return cCommaKeyword_7_2_1_0; }
		
		//providedRuntimeLibraries+=ProjectReference
		public Assignment getProvidedRuntimeLibrariesAssignment_7_2_1_1() { return cProvidedRuntimeLibrariesAssignment_7_2_1_1; }
		
		//ProjectReference
		public RuleCall getProvidedRuntimeLibrariesProjectReferenceParserRuleCall_7_2_1_1_0() { return cProvidedRuntimeLibrariesProjectReferenceParserRuleCall_7_2_1_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_7_3() { return cRightCurlyBracketKeyword_7_3; }
		
		//('RequiredRuntimeLibraries' '{' (requiredRuntimeLibraries+=ProjectReference (','
		//requiredRuntimeLibraries+=ProjectReference)*)? '}')?
		public Group getGroup_8() { return cGroup_8; }
		
		//'RequiredRuntimeLibraries'
		public Keyword getRequiredRuntimeLibrariesKeyword_8_0() { return cRequiredRuntimeLibrariesKeyword_8_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_8_1() { return cLeftCurlyBracketKeyword_8_1; }
		
		//(requiredRuntimeLibraries+=ProjectReference (',' requiredRuntimeLibraries+=ProjectReference)*)?
		public Group getGroup_8_2() { return cGroup_8_2; }
		
		//requiredRuntimeLibraries+=ProjectReference
		public Assignment getRequiredRuntimeLibrariesAssignment_8_2_0() { return cRequiredRuntimeLibrariesAssignment_8_2_0; }
		
		//ProjectReference
		public RuleCall getRequiredRuntimeLibrariesProjectReferenceParserRuleCall_8_2_0_0() { return cRequiredRuntimeLibrariesProjectReferenceParserRuleCall_8_2_0_0; }
		
		//(',' requiredRuntimeLibraries+=ProjectReference)*
		public Group getGroup_8_2_1() { return cGroup_8_2_1; }
		
		//','
		public Keyword getCommaKeyword_8_2_1_0() { return cCommaKeyword_8_2_1_0; }
		
		//requiredRuntimeLibraries+=ProjectReference
		public Assignment getRequiredRuntimeLibrariesAssignment_8_2_1_1() { return cRequiredRuntimeLibrariesAssignment_8_2_1_1; }
		
		//ProjectReference
		public RuleCall getRequiredRuntimeLibrariesProjectReferenceParserRuleCall_8_2_1_1_0() { return cRequiredRuntimeLibrariesProjectReferenceParserRuleCall_8_2_1_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_8_3() { return cRightCurlyBracketKeyword_8_3; }
		
		//('ProjectDependencies' '{' (projectDependencies+=ProjectDependency (',' projectDependencies+=ProjectDependency)*)? '}')?
		public Group getGroup_9() { return cGroup_9; }
		
		//'ProjectDependencies'
		public Keyword getProjectDependenciesKeyword_9_0() { return cProjectDependenciesKeyword_9_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_9_1() { return cLeftCurlyBracketKeyword_9_1; }
		
		//(projectDependencies+=ProjectDependency (',' projectDependencies+=ProjectDependency)*)?
		public Group getGroup_9_2() { return cGroup_9_2; }
		
		//projectDependencies+=ProjectDependency
		public Assignment getProjectDependenciesAssignment_9_2_0() { return cProjectDependenciesAssignment_9_2_0; }
		
		//ProjectDependency
		public RuleCall getProjectDependenciesProjectDependencyParserRuleCall_9_2_0_0() { return cProjectDependenciesProjectDependencyParserRuleCall_9_2_0_0; }
		
		//(',' projectDependencies+=ProjectDependency)*
		public Group getGroup_9_2_1() { return cGroup_9_2_1; }
		
		//','
		public Keyword getCommaKeyword_9_2_1_0() { return cCommaKeyword_9_2_1_0; }
		
		//projectDependencies+=ProjectDependency
		public Assignment getProjectDependenciesAssignment_9_2_1_1() { return cProjectDependenciesAssignment_9_2_1_1; }
		
		//ProjectDependency
		public RuleCall getProjectDependenciesProjectDependencyParserRuleCall_9_2_1_1_0() { return cProjectDependenciesProjectDependencyParserRuleCall_9_2_1_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_9_3() { return cRightCurlyBracketKeyword_9_3; }
		
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
		
		//('ImplementedProjects' '{' (implementedProjects+=ProjectReference (',' implementedProjects+=ProjectReference)*)? '}')?
		public Group getGroup_11() { return cGroup_11; }
		
		//'ImplementedProjects'
		public Keyword getImplementedProjectsKeyword_11_0() { return cImplementedProjectsKeyword_11_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_11_1() { return cLeftCurlyBracketKeyword_11_1; }
		
		//(implementedProjects+=ProjectReference (',' implementedProjects+=ProjectReference)*)?
		public Group getGroup_11_2() { return cGroup_11_2; }
		
		//implementedProjects+=ProjectReference
		public Assignment getImplementedProjectsAssignment_11_2_0() { return cImplementedProjectsAssignment_11_2_0; }
		
		//ProjectReference
		public RuleCall getImplementedProjectsProjectReferenceParserRuleCall_11_2_0_0() { return cImplementedProjectsProjectReferenceParserRuleCall_11_2_0_0; }
		
		//(',' implementedProjects+=ProjectReference)*
		public Group getGroup_11_2_1() { return cGroup_11_2_1; }
		
		//','
		public Keyword getCommaKeyword_11_2_1_0() { return cCommaKeyword_11_2_1_0; }
		
		//implementedProjects+=ProjectReference
		public Assignment getImplementedProjectsAssignment_11_2_1_1() { return cImplementedProjectsAssignment_11_2_1_1; }
		
		//ProjectReference
		public RuleCall getImplementedProjectsProjectReferenceParserRuleCall_11_2_1_1_0() { return cImplementedProjectsProjectReferenceParserRuleCall_11_2_1_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_11_3() { return cRightCurlyBracketKeyword_11_3; }
		
		//('InitModules' '{' (initModules+=BootstrapModule (',' initModules+=BootstrapModule)*)? '}')?
		public Group getGroup_12() { return cGroup_12; }
		
		//'InitModules'
		public Keyword getInitModulesKeyword_12_0() { return cInitModulesKeyword_12_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_12_1() { return cLeftCurlyBracketKeyword_12_1; }
		
		//(initModules+=BootstrapModule (',' initModules+=BootstrapModule)*)?
		public Group getGroup_12_2() { return cGroup_12_2; }
		
		//initModules+=BootstrapModule
		public Assignment getInitModulesAssignment_12_2_0() { return cInitModulesAssignment_12_2_0; }
		
		//BootstrapModule
		public RuleCall getInitModulesBootstrapModuleParserRuleCall_12_2_0_0() { return cInitModulesBootstrapModuleParserRuleCall_12_2_0_0; }
		
		//(',' initModules+=BootstrapModule)*
		public Group getGroup_12_2_1() { return cGroup_12_2_1; }
		
		//','
		public Keyword getCommaKeyword_12_2_1_0() { return cCommaKeyword_12_2_1_0; }
		
		//initModules+=BootstrapModule
		public Assignment getInitModulesAssignment_12_2_1_1() { return cInitModulesAssignment_12_2_1_1; }
		
		//BootstrapModule
		public RuleCall getInitModulesBootstrapModuleParserRuleCall_12_2_1_1_0() { return cInitModulesBootstrapModuleParserRuleCall_12_2_1_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_12_3() { return cRightCurlyBracketKeyword_12_3; }
		
		//('ExecModule' ':' execModule=BootstrapModule)?
		public Group getGroup_13() { return cGroup_13; }
		
		//'ExecModule'
		public Keyword getExecModuleKeyword_13_0() { return cExecModuleKeyword_13_0; }
		
		//':'
		public Keyword getColonKeyword_13_1() { return cColonKeyword_13_1; }
		
		//execModule=BootstrapModule
		public Assignment getExecModuleAssignment_13_2() { return cExecModuleAssignment_13_2; }
		
		//BootstrapModule
		public RuleCall getExecModuleBootstrapModuleParserRuleCall_13_2_0() { return cExecModuleBootstrapModuleParserRuleCall_13_2_0; }
		
		//('Output' ':' outputPathRaw=STRING)?
		public Group getGroup_14() { return cGroup_14; }
		
		//'Output'
		public Keyword getOutputKeyword_14_0() { return cOutputKeyword_14_0; }
		
		//':'
		public Keyword getColonKeyword_14_1() { return cColonKeyword_14_1; }
		
		//outputPathRaw=STRING
		public Assignment getOutputPathRawAssignment_14_2() { return cOutputPathRawAssignment_14_2; }
		
		//STRING
		public RuleCall getOutputPathRawSTRINGTerminalRuleCall_14_2_0() { return cOutputPathRawSTRINGTerminalRuleCall_14_2_0; }
		
		//('Libraries' '{' libraryPathsRaw+=STRING (',' libraryPathsRaw+=STRING)* '}')?
		public Group getGroup_15() { return cGroup_15; }
		
		//'Libraries'
		public Keyword getLibrariesKeyword_15_0() { return cLibrariesKeyword_15_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_15_1() { return cLeftCurlyBracketKeyword_15_1; }
		
		//libraryPathsRaw+=STRING
		public Assignment getLibraryPathsRawAssignment_15_2() { return cLibraryPathsRawAssignment_15_2; }
		
		//STRING
		public RuleCall getLibraryPathsRawSTRINGTerminalRuleCall_15_2_0() { return cLibraryPathsRawSTRINGTerminalRuleCall_15_2_0; }
		
		//(',' libraryPathsRaw+=STRING)*
		public Group getGroup_15_3() { return cGroup_15_3; }
		
		//','
		public Keyword getCommaKeyword_15_3_0() { return cCommaKeyword_15_3_0; }
		
		//libraryPathsRaw+=STRING
		public Assignment getLibraryPathsRawAssignment_15_3_1() { return cLibraryPathsRawAssignment_15_3_1; }
		
		//STRING
		public RuleCall getLibraryPathsRawSTRINGTerminalRuleCall_15_3_1_0() { return cLibraryPathsRawSTRINGTerminalRuleCall_15_3_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_15_4() { return cRightCurlyBracketKeyword_15_4; }
		
		//('Resources' '{' resourcePathsRaw+=STRING (',' resourcePathsRaw+=STRING)* '}')?
		public Group getGroup_16() { return cGroup_16; }
		
		//'Resources'
		public Keyword getResourcesKeyword_16_0() { return cResourcesKeyword_16_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_16_1() { return cLeftCurlyBracketKeyword_16_1; }
		
		//resourcePathsRaw+=STRING
		public Assignment getResourcePathsRawAssignment_16_2() { return cResourcePathsRawAssignment_16_2; }
		
		//STRING
		public RuleCall getResourcePathsRawSTRINGTerminalRuleCall_16_2_0() { return cResourcePathsRawSTRINGTerminalRuleCall_16_2_0; }
		
		//(',' resourcePathsRaw+=STRING)*
		public Group getGroup_16_3() { return cGroup_16_3; }
		
		//','
		public Keyword getCommaKeyword_16_3_0() { return cCommaKeyword_16_3_0; }
		
		//resourcePathsRaw+=STRING
		public Assignment getResourcePathsRawAssignment_16_3_1() { return cResourcePathsRawAssignment_16_3_1; }
		
		//STRING
		public RuleCall getResourcePathsRawSTRINGTerminalRuleCall_16_3_1_0() { return cResourcePathsRawSTRINGTerminalRuleCall_16_3_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_16_4() { return cRightCurlyBracketKeyword_16_4; }
		
		//('Sources' '{' sourceContainers+=SourceContainerDescription+ '}')?
		public Group getGroup_17() { return cGroup_17; }
		
		//'Sources'
		public Keyword getSourcesKeyword_17_0() { return cSourcesKeyword_17_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_17_1() { return cLeftCurlyBracketKeyword_17_1; }
		
		//sourceContainers+=SourceContainerDescription+
		public Assignment getSourceContainersAssignment_17_2() { return cSourceContainersAssignment_17_2; }
		
		//SourceContainerDescription
		public RuleCall getSourceContainersSourceContainerDescriptionParserRuleCall_17_2_0() { return cSourceContainersSourceContainerDescriptionParserRuleCall_17_2_0; }
		
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
		
		//('TestedProjects' '{' (testedProjects+=ProjectDependency (',' testedProjects+=ProjectDependency)*)? '}')?
		public Group getGroup_19() { return cGroup_19; }
		
		//'TestedProjects'
		public Keyword getTestedProjectsKeyword_19_0() { return cTestedProjectsKeyword_19_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_19_1() { return cLeftCurlyBracketKeyword_19_1; }
		
		//(testedProjects+=ProjectDependency (',' testedProjects+=ProjectDependency)*)?
		public Group getGroup_19_2() { return cGroup_19_2; }
		
		//testedProjects+=ProjectDependency
		public Assignment getTestedProjectsAssignment_19_2_0() { return cTestedProjectsAssignment_19_2_0; }
		
		//ProjectDependency
		public RuleCall getTestedProjectsProjectDependencyParserRuleCall_19_2_0_0() { return cTestedProjectsProjectDependencyParserRuleCall_19_2_0_0; }
		
		//(',' testedProjects+=ProjectDependency)*
		public Group getGroup_19_2_1() { return cGroup_19_2_1; }
		
		//','
		public Keyword getCommaKeyword_19_2_1_0() { return cCommaKeyword_19_2_1_0; }
		
		//testedProjects+=ProjectDependency
		public Assignment getTestedProjectsAssignment_19_2_1_1() { return cTestedProjectsAssignment_19_2_1_1; }
		
		//ProjectDependency
		public RuleCall getTestedProjectsProjectDependencyParserRuleCall_19_2_1_1_0() { return cTestedProjectsProjectDependencyParserRuleCall_19_2_1_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_19_3() { return cRightCurlyBracketKeyword_19_3; }
		
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
	public class SourceContainerDescriptionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.SourceContainerDescription");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cSourceContainerTypeAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cSourceContainerTypeSourceContainerTypeEnumRuleCall_0_0 = (RuleCall)cSourceContainerTypeAssignment_0.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cPathsRawAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cPathsRawSTRINGTerminalRuleCall_2_0 = (RuleCall)cPathsRawAssignment_2.eContents().get(0);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Keyword cCommaKeyword_3_0 = (Keyword)cGroup_3.eContents().get(0);
		private final Assignment cPathsRawAssignment_3_1 = (Assignment)cGroup_3.eContents().get(1);
		private final RuleCall cPathsRawSTRINGTerminalRuleCall_3_1_0 = (RuleCall)cPathsRawAssignment_3_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//SourceContainerDescription:
		//	sourceContainerType=SourceContainerType '{' pathsRaw+=STRING (',' pathsRaw+=STRING)* '}';
		@Override public ParserRule getRule() { return rule; }
		
		//sourceContainerType=SourceContainerType '{' pathsRaw+=STRING (',' pathsRaw+=STRING)* '}'
		public Group getGroup() { return cGroup; }
		
		//sourceContainerType=SourceContainerType
		public Assignment getSourceContainerTypeAssignment_0() { return cSourceContainerTypeAssignment_0; }
		
		//SourceContainerType
		public RuleCall getSourceContainerTypeSourceContainerTypeEnumRuleCall_0_0() { return cSourceContainerTypeSourceContainerTypeEnumRuleCall_0_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_1() { return cLeftCurlyBracketKeyword_1; }
		
		//pathsRaw+=STRING
		public Assignment getPathsRawAssignment_2() { return cPathsRawAssignment_2; }
		
		//STRING
		public RuleCall getPathsRawSTRINGTerminalRuleCall_2_0() { return cPathsRawSTRINGTerminalRuleCall_2_0; }
		
		//(',' pathsRaw+=STRING)*
		public Group getGroup_3() { return cGroup_3; }
		
		//','
		public Keyword getCommaKeyword_3_0() { return cCommaKeyword_3_0; }
		
		//pathsRaw+=STRING
		public Assignment getPathsRawAssignment_3_1() { return cPathsRawAssignment_3_1; }
		
		//STRING
		public RuleCall getPathsRawSTRINGTerminalRuleCall_3_1_0() { return cPathsRawSTRINGTerminalRuleCall_3_1_0; }
		
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
	public class ProjectReferenceElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.ProjectReference");
		private final RuleCall cProjectIdWithOptionalVendorParserRuleCall = (RuleCall)rule.eContents().get(1);
		
		///*
		// * scope is optional, default scope is compile
		// */ ProjectReference:
		//	ProjectIdWithOptionalVendor;
		@Override public ParserRule getRule() { return rule; }
		
		//ProjectIdWithOptionalVendor
		public RuleCall getProjectIdWithOptionalVendorParserRuleCall() { return cProjectIdWithOptionalVendorParserRuleCall; }
	}
	public class ProjectDependencyElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.ProjectDependency");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cProjectIdWithOptionalVendorParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Assignment cVersionConstraintAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cVersionConstraintVersionConstraintParserRuleCall_1_0 = (RuleCall)cVersionConstraintAssignment_1.eContents().get(0);
		private final Assignment cDeclaredScopeAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cDeclaredScopeProjectDependencyScopeEnumRuleCall_2_0 = (RuleCall)cDeclaredScopeAssignment_2.eContents().get(0);
		
		///*
		// * scope is optional, default scope is compile
		// */ ProjectDependency:
		//	ProjectIdWithOptionalVendor
		//	versionConstraint=VersionConstraint?
		//	declaredScope=ProjectDependencyScope?;
		@Override public ParserRule getRule() { return rule; }
		
		//ProjectIdWithOptionalVendor versionConstraint=VersionConstraint? declaredScope=ProjectDependencyScope?
		public Group getGroup() { return cGroup; }
		
		//ProjectIdWithOptionalVendor
		public RuleCall getProjectIdWithOptionalVendorParserRuleCall_0() { return cProjectIdWithOptionalVendorParserRuleCall_0; }
		
		//versionConstraint=VersionConstraint?
		public Assignment getVersionConstraintAssignment_1() { return cVersionConstraintAssignment_1; }
		
		//VersionConstraint
		public RuleCall getVersionConstraintVersionConstraintParserRuleCall_1_0() { return cVersionConstraintVersionConstraintParserRuleCall_1_0; }
		
		//declaredScope=ProjectDependencyScope?
		public Assignment getDeclaredScopeAssignment_2() { return cDeclaredScopeAssignment_2; }
		
		//ProjectDependencyScope
		public RuleCall getDeclaredScopeProjectDependencyScopeEnumRuleCall_2_0() { return cDeclaredScopeProjectDependencyScopeEnumRuleCall_2_0; }
	}
	public class ProjectIdWithOptionalVendorElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.ProjectIdWithOptionalVendor");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Assignment cDeclaredVendorIdAssignment_0_0 = (Assignment)cGroup_0.eContents().get(0);
		private final RuleCall cDeclaredVendorIdN4mfIdentifierParserRuleCall_0_0_0 = (RuleCall)cDeclaredVendorIdAssignment_0_0.eContents().get(0);
		private final Keyword cColonKeyword_0_1 = (Keyword)cGroup_0.eContents().get(1);
		private final Assignment cProjectIdAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cProjectIdN4mfIdentifierParserRuleCall_1_0 = (RuleCall)cProjectIdAssignment_1.eContents().get(0);
		
		///*
		// * vendorN4mfIdentifier is optional, if it is not specified, vendor id of current project is used.
		// */ fragment ProjectIdWithOptionalVendor *:
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
		private final EnumLiteralDeclaration cVALIDATIONEnumLiteralDeclaration_7 = (EnumLiteralDeclaration)cAlternatives.eContents().get(7);
		private final Keyword cVALIDATIONValidationKeyword_7_0 = (Keyword)cVALIDATIONEnumLiteralDeclaration_7.eContents().get(0);
		
		//enum ProjectType:
		//	APPLICATION='application' |
		//	PROCESSOR='processor' |
		//	LIBRARY='library' |
		//	API |
		//	RUNTIME_ENVIRONMENT="runtimeEnvironment" |
		//	RUNTIME_LIBRARY="runtimeLibrary" |
		//	TEST="test" |
		//	VALIDATION="validation";
		public EnumRule getRule() { return rule; }
		
		//APPLICATION='application' | PROCESSOR='processor' | LIBRARY='library' | API | RUNTIME_ENVIRONMENT="runtimeEnvironment" |
		//RUNTIME_LIBRARY="runtimeLibrary" | TEST="test" | VALIDATION="validation"
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
		
		//VALIDATION="validation"
		public EnumLiteralDeclaration getVALIDATIONEnumLiteralDeclaration_7() { return cVALIDATIONEnumLiteralDeclaration_7; }
		
		//"validation"
		public Keyword getVALIDATIONValidationKeyword_7_0() { return cVALIDATIONValidationKeyword_7_0; }
	}
	public class SourceContainerTypeElements extends AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.n4mf.N4MF.SourceContainerType");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cSOURCEEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cSOURCESourceKeyword_0_0 = (Keyword)cSOURCEEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cEXTERNALEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cEXTERNALExternalKeyword_1_0 = (Keyword)cEXTERNALEnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cTESTEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cTESTTestKeyword_2_0 = (Keyword)cTESTEnumLiteralDeclaration_2.eContents().get(0);
		
		//enum SourceContainerType:
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
	private final DeclaredVersionElements pDeclaredVersion;
	private final SourceContainerDescriptionElements pSourceContainerDescription;
	private final SourceContainerTypeElements eSourceContainerType;
	private final ModuleFilterElements pModuleFilter;
	private final BootstrapModuleElements pBootstrapModule;
	private final ModuleFilterSpecifierElements pModuleFilterSpecifier;
	private final ModuleFilterTypeElements eModuleFilterType;
	private final ProjectReferenceElements pProjectReference;
	private final ProjectDependencyElements pProjectDependency;
	private final ProjectIdWithOptionalVendorElements pProjectIdWithOptionalVendor;
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
		this.pDeclaredVersion = new DeclaredVersionElements();
		this.pSourceContainerDescription = new SourceContainerDescriptionElements();
		this.eSourceContainerType = new SourceContainerTypeElements();
		this.pModuleFilter = new ModuleFilterElements();
		this.pBootstrapModule = new BootstrapModuleElements();
		this.pModuleFilterSpecifier = new ModuleFilterSpecifierElements();
		this.eModuleFilterType = new ModuleFilterTypeElements();
		this.pProjectReference = new ProjectReferenceElements();
		this.pProjectDependency = new ProjectDependencyElements();
		this.pProjectIdWithOptionalVendor = new ProjectIdWithOptionalVendorElements();
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
	//	projectVersion=DeclaredVersion & 'VendorId' ':' vendorId=N4mfIdentifier & ('VendorName' ':' vendorName=STRING)?
	//	& ('MainModule' ':' mainModule=STRING)?
	//	// only available for runtime environments
	//	& ('ExtendedRuntimeEnvironment' ':' extendedRuntimeEnvironment=ProjectReference)?
	//	// only in case of runtime libraries or runtime environment:
	//	& ('ProvidedRuntimeLibraries' '{' (providedRuntimeLibraries+=ProjectReference (','
	//	providedRuntimeLibraries+=ProjectReference)*)? '}')?
	//	// not available in runtime environments:
	//	& ('RequiredRuntimeLibraries' '{' (requiredRuntimeLibraries+=ProjectReference (','
	//	requiredRuntimeLibraries+=ProjectReference)*)? '}')?
	//	// only available in N4JS components (Apps, Libs, Processor)
	//	& ('ProjectDependencies' '{' (projectDependencies+=ProjectDependency (',' projectDependencies+=ProjectDependency)*)?
	//	'}')?
	//	// only available in N4JS components (Apps, Libs, Processor)
	//	& ('ImplementationId' ':' implementationId=N4mfIdentifier)?
	//	// only available in N4JS components (Apps, Libs, Processor)
	//	& ('ImplementedProjects' '{' (implementedProjects+=ProjectReference (',' implementedProjects+=ProjectReference)*)?
	//	'}')?
	//	//only RuntimeLibrary and RuntimeEnvironemnt
	//	& ('InitModules' '{' (initModules+=BootstrapModule (',' initModules+=BootstrapModule)*)? '}')?
	//	& ('ExecModule' ':' execModule=BootstrapModule)?
	//	& ('Output' ':' outputPathRaw=STRING)?
	//	& ('Libraries' '{' libraryPathsRaw+=STRING (',' libraryPathsRaw+=STRING)* '}')?
	//	& ('Resources' '{' resourcePathsRaw+=STRING (',' resourcePathsRaw+=STRING)* '}')?
	//	& ('Sources' '{' sourceContainers+=SourceContainerDescription+ '}')?
	//	& ('ModuleFilters' '{' moduleFilters+=ModuleFilter+ '}')?
	//	& ('TestedProjects' '{' (testedProjects+=ProjectDependency (',' testedProjects+=ProjectDependency)*)? '}')?
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
	//	TEST="test" |
	//	VALIDATION="validation";
	public ProjectTypeElements getProjectTypeAccess() {
		return eProjectType;
	}
	
	public EnumRule getProjectTypeRule() {
		return getProjectTypeAccess().getRule();
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
	
	//SourceContainerDescription:
	//	sourceContainerType=SourceContainerType '{' pathsRaw+=STRING (',' pathsRaw+=STRING)* '}';
	public SourceContainerDescriptionElements getSourceContainerDescriptionAccess() {
		return pSourceContainerDescription;
	}
	
	public ParserRule getSourceContainerDescriptionRule() {
		return getSourceContainerDescriptionAccess().getRule();
	}
	
	//enum SourceContainerType:
	//	SOURCE='source' | EXTERNAL='external' | TEST='test';
	public SourceContainerTypeElements getSourceContainerTypeAccess() {
		return eSourceContainerType;
	}
	
	public EnumRule getSourceContainerTypeRule() {
		return getSourceContainerTypeAccess().getRule();
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
	
	///*
	// * scope is optional, default scope is compile
	// */ ProjectReference:
	//	ProjectIdWithOptionalVendor;
	public ProjectReferenceElements getProjectReferenceAccess() {
		return pProjectReference;
	}
	
	public ParserRule getProjectReferenceRule() {
		return getProjectReferenceAccess().getRule();
	}
	
	///*
	// * scope is optional, default scope is compile
	// */ ProjectDependency:
	//	ProjectIdWithOptionalVendor
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
	// */ fragment ProjectIdWithOptionalVendor *:
	//	(declaredVendorId=N4mfIdentifier ':')? projectId=N4mfIdentifier;
	public ProjectIdWithOptionalVendorElements getProjectIdWithOptionalVendorAccess() {
		return pProjectIdWithOptionalVendor;
	}
	
	public ParserRule getProjectIdWithOptionalVendorRule() {
		return getProjectIdWithOptionalVendorAccess().getRule();
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
