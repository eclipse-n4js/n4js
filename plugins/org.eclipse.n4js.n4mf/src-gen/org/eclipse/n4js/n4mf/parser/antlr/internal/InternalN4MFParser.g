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
parser grammar InternalN4MFParser;

options {
	tokenVocab=InternalN4MFLexer;
	superClass=AbstractInternalAntlrParser;
}

@header {
package org.eclipse.n4js.n4mf.parser.antlr.internal;

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
import org.eclipse.n4js.n4mf.services.N4MFGrammarAccess;

}

@members {

 	private N4MFGrammarAccess grammarAccess;

    public InternalN4MFParser(TokenStream input, N4MFGrammarAccess grammarAccess) {
        this(input);
        this.grammarAccess = grammarAccess;
        registerRules(grammarAccess.getGrammar());
    }

    @Override
    protected String getFirstRuleName() {
    	return "ProjectDescription";
   	}

   	@Override
   	protected N4MFGrammarAccess getGrammarAccess() {
   		return grammarAccess;
   	}

}

@rulecatch {
    catch (RecognitionException re) {
        recover(input,re);
        appendSkippedTokens();
    }
}

// Entry rule entryRuleProjectDescription
entryRuleProjectDescription returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getProjectDescriptionRule()); }
	iv_ruleProjectDescription=ruleProjectDescription
	{ $current=$iv_ruleProjectDescription.current; }
	EOF;

// Rule ProjectDescription
ruleProjectDescription returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{ 
			  getUnorderedGroupHelper().enter(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
			}
			(
				(
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 0);
				}
							({true}?=>(otherlv_1=ProjectId
							{
								newLeafNode(otherlv_1, grammarAccess.getProjectDescriptionAccess().getProjectIdKeyword_0_0());
							}
							otherlv_2=Colon
							{
								newLeafNode(otherlv_2, grammarAccess.getProjectDescriptionAccess().getColonKeyword_0_1());
							}
							(
								(
									{
										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getProjectIdN4mfIdentifierParserRuleCall_0_2_0());
									}
									lv_projectId_3_0=ruleN4mfIdentifier
									{
										if ($current==null) {
											$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
										}
										set(
											$current,
											"projectId",
											lv_projectId_3_0,
											"org.eclipse.n4js.n4mf.N4MF.N4mfIdentifier");
										afterParserOrEnumRuleCall();
									}
								)
							)
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)|
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 1);
				}
							({true}?=>(otherlv_4=ProjectType
							{
								newLeafNode(otherlv_4, grammarAccess.getProjectDescriptionAccess().getProjectTypeKeyword_1_0());
							}
							otherlv_5=Colon
							{
								newLeafNode(otherlv_5, grammarAccess.getProjectDescriptionAccess().getColonKeyword_1_1());
							}
							(
								(
									{
										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getProjectTypeProjectTypeEnumRuleCall_1_2_0());
									}
									lv_projectType_6_0=ruleProjectType
									{
										if ($current==null) {
											$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
										}
										set(
											$current,
											"projectType",
											lv_projectType_6_0,
											"org.eclipse.n4js.n4mf.N4MF.ProjectType");
										afterParserOrEnumRuleCall();
									}
								)
							)
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)|
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 2);
				}
							({true}?=>(otherlv_7=ProjectVersion
							{
								newLeafNode(otherlv_7, grammarAccess.getProjectDescriptionAccess().getProjectVersionKeyword_2_0());
							}
							otherlv_8=Colon
							{
								newLeafNode(otherlv_8, grammarAccess.getProjectDescriptionAccess().getColonKeyword_2_1());
							}
							(
								(
									{
										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getProjectVersionDeclaredVersionParserRuleCall_2_2_0());
									}
									lv_projectVersion_9_0=ruleDeclaredVersion
									{
										if ($current==null) {
											$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
										}
										set(
											$current,
											"projectVersion",
											lv_projectVersion_9_0,
											"org.eclipse.n4js.n4mf.N4MF.DeclaredVersion");
										afterParserOrEnumRuleCall();
									}
								)
							)
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)|
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 3);
				}
							({true}?=>(otherlv_10=VendorId
							{
								newLeafNode(otherlv_10, grammarAccess.getProjectDescriptionAccess().getVendorIdKeyword_3_0());
							}
							otherlv_11=Colon
							{
								newLeafNode(otherlv_11, grammarAccess.getProjectDescriptionAccess().getColonKeyword_3_1());
							}
							(
								(
									{
										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getDeclaredVendorIdN4mfIdentifierParserRuleCall_3_2_0());
									}
									lv_declaredVendorId_12_0=ruleN4mfIdentifier
									{
										if ($current==null) {
											$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
										}
										set(
											$current,
											"declaredVendorId",
											lv_declaredVendorId_12_0,
											"org.eclipse.n4js.n4mf.N4MF.N4mfIdentifier");
										afterParserOrEnumRuleCall();
									}
								)
							)
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)|
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 4);
				}
							({true}?=>(otherlv_13=VendorName
							{
								newLeafNode(otherlv_13, grammarAccess.getProjectDescriptionAccess().getVendorNameKeyword_4_0());
							}
							otherlv_14=Colon
							{
								newLeafNode(otherlv_14, grammarAccess.getProjectDescriptionAccess().getColonKeyword_4_1());
							}
							(
								(
									lv_vendorName_15_0=RULE_STRING
									{
										newLeafNode(lv_vendorName_15_0, grammarAccess.getProjectDescriptionAccess().getVendorNameSTRINGTerminalRuleCall_4_2_0());
									}
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getProjectDescriptionRule());
										}
										setWithLastConsumed(
											$current,
											"vendorName",
											lv_vendorName_15_0,
											"org.eclipse.xtext.common.Terminals.STRING");
									}
								)
							)
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)|
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 5);
				}
							({true}?=>(otherlv_16=MainModule
							{
								newLeafNode(otherlv_16, grammarAccess.getProjectDescriptionAccess().getMainModuleKeyword_5_0());
							}
							otherlv_17=Colon
							{
								newLeafNode(otherlv_17, grammarAccess.getProjectDescriptionAccess().getColonKeyword_5_1());
							}
							(
								(
									lv_mainModule_18_0=RULE_STRING
									{
										newLeafNode(lv_mainModule_18_0, grammarAccess.getProjectDescriptionAccess().getMainModuleSTRINGTerminalRuleCall_5_2_0());
									}
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getProjectDescriptionRule());
										}
										setWithLastConsumed(
											$current,
											"mainModule",
											lv_mainModule_18_0,
											"org.eclipse.xtext.common.Terminals.STRING");
									}
								)
							)
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)|
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 6);
				}
							({true}?=>((
								{
									newCompositeNode(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentExtendedRuntimeEnvironmentParserRuleCall_6_0());
								}
								lv_extendedRuntimeEnvironment_19_0=ruleExtendedRuntimeEnvironment
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
									}
									set(
										$current,
										"extendedRuntimeEnvironment",
										lv_extendedRuntimeEnvironment_19_0,
										"org.eclipse.n4js.n4mf.N4MF.ExtendedRuntimeEnvironment");
									afterParserOrEnumRuleCall();
								}
							)
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)|
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7);
				}
							({true}?=>((
								{
									newCompositeNode(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibrariesParserRuleCall_7_0());
								}
								lv_providedRuntimeLibraries_20_0=ruleProvidedRuntimeLibraries
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
									}
									set(
										$current,
										"providedRuntimeLibraries",
										lv_providedRuntimeLibraries_20_0,
										"org.eclipse.n4js.n4mf.N4MF.ProvidedRuntimeLibraries");
									afterParserOrEnumRuleCall();
								}
							)
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)|
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 8);
				}
							({true}?=>((
								{
									newCompositeNode(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibrariesParserRuleCall_8_0());
								}
								lv_requiredRuntimeLibraries_21_0=ruleRequiredRuntimeLibraries
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
									}
									set(
										$current,
										"requiredRuntimeLibraries",
										lv_requiredRuntimeLibraries_21_0,
										"org.eclipse.n4js.n4mf.N4MF.RequiredRuntimeLibraries");
									afterParserOrEnumRuleCall();
								}
							)
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)|
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 9);
				}
							({true}?=>((
								{
									newCompositeNode(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesProjectDependenciesParserRuleCall_9_0());
								}
								lv_projectDependencies_22_0=ruleProjectDependencies
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
									}
									set(
										$current,
										"projectDependencies",
										lv_projectDependencies_22_0,
										"org.eclipse.n4js.n4mf.N4MF.ProjectDependencies");
									afterParserOrEnumRuleCall();
								}
							)
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)|
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 10);
				}
							({true}?=>(otherlv_23=ImplementationId
							{
								newLeafNode(otherlv_23, grammarAccess.getProjectDescriptionAccess().getImplementationIdKeyword_10_0());
							}
							otherlv_24=Colon
							{
								newLeafNode(otherlv_24, grammarAccess.getProjectDescriptionAccess().getColonKeyword_10_1());
							}
							(
								(
									{
										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getImplementationIdN4mfIdentifierParserRuleCall_10_2_0());
									}
									lv_implementationId_25_0=ruleN4mfIdentifier
									{
										if ($current==null) {
											$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
										}
										set(
											$current,
											"implementationId",
											lv_implementationId_25_0,
											"org.eclipse.n4js.n4mf.N4MF.N4mfIdentifier");
										afterParserOrEnumRuleCall();
									}
								)
							)
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)|
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 11);
				}
							({true}?=>((
								{
									newCompositeNode(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsImplementedProjectsParserRuleCall_11_0());
								}
								lv_implementedProjects_26_0=ruleImplementedProjects
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
									}
									set(
										$current,
										"implementedProjects",
										lv_implementedProjects_26_0,
										"org.eclipse.n4js.n4mf.N4MF.ImplementedProjects");
									afterParserOrEnumRuleCall();
								}
							)
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)|
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 12);
				}
							({true}?=>((
								{
									newCompositeNode(grammarAccess.getProjectDescriptionAccess().getInitModulesInitModulesParserRuleCall_12_0());
								}
								lv_initModules_27_0=ruleInitModules
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
									}
									set(
										$current,
										"initModules",
										lv_initModules_27_0,
										"org.eclipse.n4js.n4mf.N4MF.InitModules");
									afterParserOrEnumRuleCall();
								}
							)
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)|
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 13);
				}
							({true}?=>((
								{
									newCompositeNode(grammarAccess.getProjectDescriptionAccess().getExecModuleExecModuleParserRuleCall_13_0());
								}
								lv_execModule_28_0=ruleExecModule
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
									}
									set(
										$current,
										"execModule",
										lv_execModule_28_0,
										"org.eclipse.n4js.n4mf.N4MF.ExecModule");
									afterParserOrEnumRuleCall();
								}
							)
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)|
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14);
				}
							({true}?=>(otherlv_29=Output
							{
								newLeafNode(otherlv_29, grammarAccess.getProjectDescriptionAccess().getOutputKeyword_14_0());
							}
							otherlv_30=Colon
							{
								newLeafNode(otherlv_30, grammarAccess.getProjectDescriptionAccess().getColonKeyword_14_1());
							}
							(
								(
									lv_outputPath_31_0=RULE_STRING
									{
										newLeafNode(lv_outputPath_31_0, grammarAccess.getProjectDescriptionAccess().getOutputPathSTRINGTerminalRuleCall_14_2_0());
									}
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getProjectDescriptionRule());
										}
										setWithLastConsumed(
											$current,
											"outputPath",
											lv_outputPath_31_0,
											"org.eclipse.xtext.common.Terminals.STRING");
									}
								)
							)
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)|
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 15);
				}
							({true}?=>(otherlv_32=Libraries
							{
								newLeafNode(otherlv_32, grammarAccess.getProjectDescriptionAccess().getLibrariesKeyword_15_0());
							}
							otherlv_33=LeftCurlyBracket
							{
								newLeafNode(otherlv_33, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_15_1());
							}
							(
								(
									lv_libraryPaths_34_0=RULE_STRING
									{
										newLeafNode(lv_libraryPaths_34_0, grammarAccess.getProjectDescriptionAccess().getLibraryPathsSTRINGTerminalRuleCall_15_2_0());
									}
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getProjectDescriptionRule());
										}
										addWithLastConsumed(
											$current,
											"libraryPaths",
											lv_libraryPaths_34_0,
											"org.eclipse.xtext.common.Terminals.STRING");
									}
								)
							)
							(
								otherlv_35=Comma
								{
									newLeafNode(otherlv_35, grammarAccess.getProjectDescriptionAccess().getCommaKeyword_15_3_0());
								}
								(
									(
										lv_libraryPaths_36_0=RULE_STRING
										{
											newLeafNode(lv_libraryPaths_36_0, grammarAccess.getProjectDescriptionAccess().getLibraryPathsSTRINGTerminalRuleCall_15_3_1_0());
										}
										{
											if ($current==null) {
												$current = createModelElement(grammarAccess.getProjectDescriptionRule());
											}
											addWithLastConsumed(
												$current,
												"libraryPaths",
												lv_libraryPaths_36_0,
												"org.eclipse.xtext.common.Terminals.STRING");
										}
									)
								)
							)*
							otherlv_37=RightCurlyBracket
							{
								newLeafNode(otherlv_37, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_15_4());
							}
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)|
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 16);
				}
							({true}?=>(otherlv_38=Resources
							{
								newLeafNode(otherlv_38, grammarAccess.getProjectDescriptionAccess().getResourcesKeyword_16_0());
							}
							otherlv_39=LeftCurlyBracket
							{
								newLeafNode(otherlv_39, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_16_1());
							}
							(
								(
									lv_resourcePaths_40_0=RULE_STRING
									{
										newLeafNode(lv_resourcePaths_40_0, grammarAccess.getProjectDescriptionAccess().getResourcePathsSTRINGTerminalRuleCall_16_2_0());
									}
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getProjectDescriptionRule());
										}
										addWithLastConsumed(
											$current,
											"resourcePaths",
											lv_resourcePaths_40_0,
											"org.eclipse.xtext.common.Terminals.STRING");
									}
								)
							)
							(
								otherlv_41=Comma
								{
									newLeafNode(otherlv_41, grammarAccess.getProjectDescriptionAccess().getCommaKeyword_16_3_0());
								}
								(
									(
										lv_resourcePaths_42_0=RULE_STRING
										{
											newLeafNode(lv_resourcePaths_42_0, grammarAccess.getProjectDescriptionAccess().getResourcePathsSTRINGTerminalRuleCall_16_3_1_0());
										}
										{
											if ($current==null) {
												$current = createModelElement(grammarAccess.getProjectDescriptionRule());
											}
											addWithLastConsumed(
												$current,
												"resourcePaths",
												lv_resourcePaths_42_0,
												"org.eclipse.xtext.common.Terminals.STRING");
										}
									)
								)
							)*
							otherlv_43=RightCurlyBracket
							{
								newLeafNode(otherlv_43, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_16_4());
							}
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)|
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 17);
				}
							({true}?=>(otherlv_44=Sources
							{
								newLeafNode(otherlv_44, grammarAccess.getProjectDescriptionAccess().getSourcesKeyword_17_0());
							}
							otherlv_45=LeftCurlyBracket
							{
								newLeafNode(otherlv_45, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_17_1());
							}
							(
								(
									{
										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getSourceFragmentSourceFragmentParserRuleCall_17_2_0());
									}
									lv_sourceFragment_46_0=ruleSourceFragment
									{
										if ($current==null) {
											$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
										}
										add(
											$current,
											"sourceFragment",
											lv_sourceFragment_46_0,
											"org.eclipse.n4js.n4mf.N4MF.SourceFragment");
										afterParserOrEnumRuleCall();
									}
								)
							)+
							otherlv_47=RightCurlyBracket
							{
								newLeafNode(otherlv_47, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_17_3());
							}
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)|
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 18);
				}
							({true}?=>(otherlv_48=ModuleFilters
							{
								newLeafNode(otherlv_48, grammarAccess.getProjectDescriptionAccess().getModuleFiltersKeyword_18_0());
							}
							otherlv_49=LeftCurlyBracket
							{
								newLeafNode(otherlv_49, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_18_1());
							}
							(
								(
									{
										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getModuleFiltersModuleFilterParserRuleCall_18_2_0());
									}
									lv_moduleFilters_50_0=ruleModuleFilter
									{
										if ($current==null) {
											$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
										}
										add(
											$current,
											"moduleFilters",
											lv_moduleFilters_50_0,
											"org.eclipse.n4js.n4mf.N4MF.ModuleFilter");
										afterParserOrEnumRuleCall();
									}
								)
							)+
							otherlv_51=RightCurlyBracket
							{
								newLeafNode(otherlv_51, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_18_3());
							}
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)|
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 19);
				}
							({true}?=>((
								{
									newCompositeNode(grammarAccess.getProjectDescriptionAccess().getTestedProjectsTestedProjectsParserRuleCall_19_0());
								}
								lv_testedProjects_52_0=ruleTestedProjects
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
									}
									set(
										$current,
										"testedProjects",
										lv_testedProjects_52_0,
										"org.eclipse.n4js.n4mf.N4MF.TestedProjects");
									afterParserOrEnumRuleCall();
								}
							)
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)|
		(
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 20);
				}
							({true}?=>(otherlv_53=ModuleLoader
							{
								newLeafNode(otherlv_53, grammarAccess.getProjectDescriptionAccess().getModuleLoaderKeyword_20_0());
							}
							otherlv_54=Colon
							{
								newLeafNode(otherlv_54, grammarAccess.getProjectDescriptionAccess().getColonKeyword_20_1());
							}
							(
								(
									{
										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getModuleLoaderModuleLoaderEnumRuleCall_20_2_0());
									}
									lv_moduleLoader_55_0=ruleModuleLoader
									{
										if ($current==null) {
											$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
										}
										set(
											$current,
											"moduleLoader",
											lv_moduleLoader_55_0,
											"org.eclipse.n4js.n4mf.N4MF.ModuleLoader");
										afterParserOrEnumRuleCall();
									}
								)
							)
							))
				{ 
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
				}
			)
		)
				)+
				{getUnorderedGroupHelper().canLeave(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup())}?
			)
		)
			{ 
			  getUnorderedGroupHelper().leave(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup());
			}
	)
;

// Entry rule entryRuleExecModule
entryRuleExecModule returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getExecModuleRule()); }
	iv_ruleExecModule=ruleExecModule
	{ $current=$iv_ruleExecModule.current; }
	EOF;

// Rule ExecModule
ruleExecModule returns [EObject current=null]
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
					grammarAccess.getExecModuleAccess().getExecModuleAction_0(),
					$current);
			}
		)
		otherlv_1=ExecModule
		{
			newLeafNode(otherlv_1, grammarAccess.getExecModuleAccess().getExecModuleKeyword_1());
		}
		otherlv_2=Colon
		{
			newLeafNode(otherlv_2, grammarAccess.getExecModuleAccess().getColonKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getExecModuleAccess().getExecModuleBootstrapModuleParserRuleCall_3_0());
				}
				lv_execModule_3_0=ruleBootstrapModule
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getExecModuleRule());
					}
					set(
						$current,
						"execModule",
						lv_execModule_3_0,
						"org.eclipse.n4js.n4mf.N4MF.BootstrapModule");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleTestedProjects
entryRuleTestedProjects returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTestedProjectsRule()); }
	iv_ruleTestedProjects=ruleTestedProjects
	{ $current=$iv_ruleTestedProjects.current; }
	EOF;

// Rule TestedProjects
ruleTestedProjects returns [EObject current=null]
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
					grammarAccess.getTestedProjectsAccess().getTestedProjectsAction_0(),
					$current);
			}
		)
		otherlv_1=TestedProjects
		{
			newLeafNode(otherlv_1, grammarAccess.getTestedProjectsAccess().getTestedProjectsKeyword_1());
		}
		otherlv_2=LeftCurlyBracket
		{
			newLeafNode(otherlv_2, grammarAccess.getTestedProjectsAccess().getLeftCurlyBracketKeyword_2());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getTestedProjectsAccess().getTestedProjectsTestedProjectParserRuleCall_3_0_0());
					}
					lv_testedProjects_3_0=ruleTestedProject
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTestedProjectsRule());
						}
						add(
							$current,
							"testedProjects",
							lv_testedProjects_3_0,
							"org.eclipse.n4js.n4mf.N4MF.TestedProject");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_4=Comma
				{
					newLeafNode(otherlv_4, grammarAccess.getTestedProjectsAccess().getCommaKeyword_3_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getTestedProjectsAccess().getTestedProjectsTestedProjectParserRuleCall_3_1_1_0());
						}
						lv_testedProjects_5_0=ruleTestedProject
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTestedProjectsRule());
							}
							add(
								$current,
								"testedProjects",
								lv_testedProjects_5_0,
								"org.eclipse.n4js.n4mf.N4MF.TestedProject");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_6=RightCurlyBracket
		{
			newLeafNode(otherlv_6, grammarAccess.getTestedProjectsAccess().getRightCurlyBracketKeyword_4());
		}
	)
;

// Entry rule entryRuleInitModules
entryRuleInitModules returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getInitModulesRule()); }
	iv_ruleInitModules=ruleInitModules
	{ $current=$iv_ruleInitModules.current; }
	EOF;

// Rule InitModules
ruleInitModules returns [EObject current=null]
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
					grammarAccess.getInitModulesAccess().getInitModulesAction_0(),
					$current);
			}
		)
		otherlv_1=InitModules
		{
			newLeafNode(otherlv_1, grammarAccess.getInitModulesAccess().getInitModulesKeyword_1());
		}
		otherlv_2=LeftCurlyBracket
		{
			newLeafNode(otherlv_2, grammarAccess.getInitModulesAccess().getLeftCurlyBracketKeyword_2());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getInitModulesAccess().getInitModulesBootstrapModuleParserRuleCall_3_0_0());
					}
					lv_initModules_3_0=ruleBootstrapModule
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getInitModulesRule());
						}
						add(
							$current,
							"initModules",
							lv_initModules_3_0,
							"org.eclipse.n4js.n4mf.N4MF.BootstrapModule");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_4=Comma
				{
					newLeafNode(otherlv_4, grammarAccess.getInitModulesAccess().getCommaKeyword_3_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getInitModulesAccess().getInitModulesBootstrapModuleParserRuleCall_3_1_1_0());
						}
						lv_initModules_5_0=ruleBootstrapModule
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getInitModulesRule());
							}
							add(
								$current,
								"initModules",
								lv_initModules_5_0,
								"org.eclipse.n4js.n4mf.N4MF.BootstrapModule");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_6=RightCurlyBracket
		{
			newLeafNode(otherlv_6, grammarAccess.getInitModulesAccess().getRightCurlyBracketKeyword_4());
		}
	)
;

// Entry rule entryRuleImplementedProjects
entryRuleImplementedProjects returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getImplementedProjectsRule()); }
	iv_ruleImplementedProjects=ruleImplementedProjects
	{ $current=$iv_ruleImplementedProjects.current; }
	EOF;

// Rule ImplementedProjects
ruleImplementedProjects returns [EObject current=null]
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
					grammarAccess.getImplementedProjectsAccess().getImplementedProjectsAction_0(),
					$current);
			}
		)
		otherlv_1=ImplementedProjects
		{
			newLeafNode(otherlv_1, grammarAccess.getImplementedProjectsAccess().getImplementedProjectsKeyword_1());
		}
		otherlv_2=LeftCurlyBracket
		{
			newLeafNode(otherlv_2, grammarAccess.getImplementedProjectsAccess().getLeftCurlyBracketKeyword_2());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsProjectReferenceParserRuleCall_3_0_0());
					}
					lv_implementedProjects_3_0=ruleProjectReference
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getImplementedProjectsRule());
						}
						add(
							$current,
							"implementedProjects",
							lv_implementedProjects_3_0,
							"org.eclipse.n4js.n4mf.N4MF.ProjectReference");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_4=Comma
				{
					newLeafNode(otherlv_4, grammarAccess.getImplementedProjectsAccess().getCommaKeyword_3_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getImplementedProjectsAccess().getImplementedProjectsProjectReferenceParserRuleCall_3_1_1_0());
						}
						lv_implementedProjects_5_0=ruleProjectReference
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getImplementedProjectsRule());
							}
							add(
								$current,
								"implementedProjects",
								lv_implementedProjects_5_0,
								"org.eclipse.n4js.n4mf.N4MF.ProjectReference");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_6=RightCurlyBracket
		{
			newLeafNode(otherlv_6, grammarAccess.getImplementedProjectsAccess().getRightCurlyBracketKeyword_4());
		}
	)
;

// Entry rule entryRuleProjectDependencies
entryRuleProjectDependencies returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getProjectDependenciesRule()); }
	iv_ruleProjectDependencies=ruleProjectDependencies
	{ $current=$iv_ruleProjectDependencies.current; }
	EOF;

// Rule ProjectDependencies
ruleProjectDependencies returns [EObject current=null]
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
					grammarAccess.getProjectDependenciesAccess().getProjectDependenciesAction_0(),
					$current);
			}
		)
		otherlv_1=ProjectDependencies
		{
			newLeafNode(otherlv_1, grammarAccess.getProjectDependenciesAccess().getProjectDependenciesKeyword_1());
		}
		otherlv_2=LeftCurlyBracket
		{
			newLeafNode(otherlv_2, grammarAccess.getProjectDependenciesAccess().getLeftCurlyBracketKeyword_2());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesProjectDependencyParserRuleCall_3_0_0());
					}
					lv_projectDependencies_3_0=ruleProjectDependency
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getProjectDependenciesRule());
						}
						add(
							$current,
							"projectDependencies",
							lv_projectDependencies_3_0,
							"org.eclipse.n4js.n4mf.N4MF.ProjectDependency");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_4=Comma
				{
					newLeafNode(otherlv_4, grammarAccess.getProjectDependenciesAccess().getCommaKeyword_3_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getProjectDependenciesAccess().getProjectDependenciesProjectDependencyParserRuleCall_3_1_1_0());
						}
						lv_projectDependencies_5_0=ruleProjectDependency
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getProjectDependenciesRule());
							}
							add(
								$current,
								"projectDependencies",
								lv_projectDependencies_5_0,
								"org.eclipse.n4js.n4mf.N4MF.ProjectDependency");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_6=RightCurlyBracket
		{
			newLeafNode(otherlv_6, grammarAccess.getProjectDependenciesAccess().getRightCurlyBracketKeyword_4());
		}
	)
;

// Entry rule entryRuleProvidedRuntimeLibraries
entryRuleProvidedRuntimeLibraries returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getProvidedRuntimeLibrariesRule()); }
	iv_ruleProvidedRuntimeLibraries=ruleProvidedRuntimeLibraries
	{ $current=$iv_ruleProvidedRuntimeLibraries.current; }
	EOF;

// Rule ProvidedRuntimeLibraries
ruleProvidedRuntimeLibraries returns [EObject current=null]
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
					grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesAction_0(),
					$current);
			}
		)
		otherlv_1=ProvidedRuntimeLibraries
		{
			newLeafNode(otherlv_1, grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesKeyword_1());
		}
		otherlv_2=LeftCurlyBracket
		{
			newLeafNode(otherlv_2, grammarAccess.getProvidedRuntimeLibrariesAccess().getLeftCurlyBracketKeyword_2());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_3_0_0());
					}
					lv_providedRuntimeLibraries_3_0=ruleProvidedRuntimeLibraryDependency
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getProvidedRuntimeLibrariesRule());
						}
						add(
							$current,
							"providedRuntimeLibraries",
							lv_providedRuntimeLibraries_3_0,
							"org.eclipse.n4js.n4mf.N4MF.ProvidedRuntimeLibraryDependency");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_4=Comma
				{
					newLeafNode(otherlv_4, grammarAccess.getProvidedRuntimeLibrariesAccess().getCommaKeyword_3_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getProvidedRuntimeLibrariesAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_3_1_1_0());
						}
						lv_providedRuntimeLibraries_5_0=ruleProvidedRuntimeLibraryDependency
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getProvidedRuntimeLibrariesRule());
							}
							add(
								$current,
								"providedRuntimeLibraries",
								lv_providedRuntimeLibraries_5_0,
								"org.eclipse.n4js.n4mf.N4MF.ProvidedRuntimeLibraryDependency");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_6=RightCurlyBracket
		{
			newLeafNode(otherlv_6, grammarAccess.getProvidedRuntimeLibrariesAccess().getRightCurlyBracketKeyword_4());
		}
	)
;

// Entry rule entryRuleRequiredRuntimeLibraries
entryRuleRequiredRuntimeLibraries returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getRequiredRuntimeLibrariesRule()); }
	iv_ruleRequiredRuntimeLibraries=ruleRequiredRuntimeLibraries
	{ $current=$iv_ruleRequiredRuntimeLibraries.current; }
	EOF;

// Rule RequiredRuntimeLibraries
ruleRequiredRuntimeLibraries returns [EObject current=null]
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
					grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesAction_0(),
					$current);
			}
		)
		otherlv_1=RequiredRuntimeLibraries
		{
			newLeafNode(otherlv_1, grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesKeyword_1());
		}
		otherlv_2=LeftCurlyBracket
		{
			newLeafNode(otherlv_2, grammarAccess.getRequiredRuntimeLibrariesAccess().getLeftCurlyBracketKeyword_2());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_3_0_0());
					}
					lv_requiredRuntimeLibraries_3_0=ruleRequiredRuntimeLibraryDependency
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getRequiredRuntimeLibrariesRule());
						}
						add(
							$current,
							"requiredRuntimeLibraries",
							lv_requiredRuntimeLibraries_3_0,
							"org.eclipse.n4js.n4mf.N4MF.RequiredRuntimeLibraryDependency");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_4=Comma
				{
					newLeafNode(otherlv_4, grammarAccess.getRequiredRuntimeLibrariesAccess().getCommaKeyword_3_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getRequiredRuntimeLibrariesAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_3_1_1_0());
						}
						lv_requiredRuntimeLibraries_5_0=ruleRequiredRuntimeLibraryDependency
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getRequiredRuntimeLibrariesRule());
							}
							add(
								$current,
								"requiredRuntimeLibraries",
								lv_requiredRuntimeLibraries_5_0,
								"org.eclipse.n4js.n4mf.N4MF.RequiredRuntimeLibraryDependency");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_6=RightCurlyBracket
		{
			newLeafNode(otherlv_6, grammarAccess.getRequiredRuntimeLibrariesAccess().getRightCurlyBracketKeyword_4());
		}
	)
;

// Entry rule entryRuleExtendedRuntimeEnvironment
entryRuleExtendedRuntimeEnvironment returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getExtendedRuntimeEnvironmentRule()); }
	iv_ruleExtendedRuntimeEnvironment=ruleExtendedRuntimeEnvironment
	{ $current=$iv_ruleExtendedRuntimeEnvironment.current; }
	EOF;

// Rule ExtendedRuntimeEnvironment
ruleExtendedRuntimeEnvironment returns [EObject current=null]
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
					grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentAction_0(),
					$current);
			}
		)
		otherlv_1=ExtendedRuntimeEnvironment
		{
			newLeafNode(otherlv_1, grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentKeyword_1());
		}
		otherlv_2=Colon
		{
			newLeafNode(otherlv_2, grammarAccess.getExtendedRuntimeEnvironmentAccess().getColonKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getExtendedRuntimeEnvironmentAccess().getExtendedRuntimeEnvironmentProjectReferenceParserRuleCall_3_0());
				}
				lv_extendedRuntimeEnvironment_3_0=ruleProjectReference
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getExtendedRuntimeEnvironmentRule());
					}
					set(
						$current,
						"extendedRuntimeEnvironment",
						lv_extendedRuntimeEnvironment_3_0,
						"org.eclipse.n4js.n4mf.N4MF.ProjectReference");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleDeclaredVersion
entryRuleDeclaredVersion returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getDeclaredVersionRule()); }
	iv_ruleDeclaredVersion=ruleDeclaredVersion
	{ $current=$iv_ruleDeclaredVersion.current; }
	EOF;

// Rule DeclaredVersion
ruleDeclaredVersion returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_major_0_0=RULE_INT
				{
					newLeafNode(lv_major_0_0, grammarAccess.getDeclaredVersionAccess().getMajorINTTerminalRuleCall_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getDeclaredVersionRule());
					}
					setWithLastConsumed(
						$current,
						"major",
						lv_major_0_0,
						"org.eclipse.xtext.common.Terminals.INT");
				}
			)
		)
		(
			otherlv_1=FullStop
			{
				newLeafNode(otherlv_1, grammarAccess.getDeclaredVersionAccess().getFullStopKeyword_1_0());
			}
			(
				(
					lv_minor_2_0=RULE_INT
					{
						newLeafNode(lv_minor_2_0, grammarAccess.getDeclaredVersionAccess().getMinorINTTerminalRuleCall_1_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getDeclaredVersionRule());
						}
						setWithLastConsumed(
							$current,
							"minor",
							lv_minor_2_0,
							"org.eclipse.xtext.common.Terminals.INT");
					}
				)
			)
			(
				otherlv_3=FullStop
				{
					newLeafNode(otherlv_3, grammarAccess.getDeclaredVersionAccess().getFullStopKeyword_1_2_0());
				}
				(
					(
						lv_micro_4_0=RULE_INT
						{
							newLeafNode(lv_micro_4_0, grammarAccess.getDeclaredVersionAccess().getMicroINTTerminalRuleCall_1_2_1_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getDeclaredVersionRule());
							}
							setWithLastConsumed(
								$current,
								"micro",
								lv_micro_4_0,
								"org.eclipse.xtext.common.Terminals.INT");
						}
					)
				)
			)?
		)?
		(
			otherlv_5=HyphenMinus
			{
				newLeafNode(otherlv_5, grammarAccess.getDeclaredVersionAccess().getHyphenMinusKeyword_2_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getDeclaredVersionAccess().getQualifierN4mfIdentifierParserRuleCall_2_1_0());
					}
					lv_qualifier_6_0=ruleN4mfIdentifier
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getDeclaredVersionRule());
						}
						set(
							$current,
							"qualifier",
							lv_qualifier_6_0,
							"org.eclipse.n4js.n4mf.N4MF.N4mfIdentifier");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;

// Entry rule entryRuleSourceFragment
entryRuleSourceFragment returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSourceFragmentRule()); }
	iv_ruleSourceFragment=ruleSourceFragment
	{ $current=$iv_ruleSourceFragment.current; }
	EOF;

// Rule SourceFragment
ruleSourceFragment returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getSourceFragmentAccess().getSourceFragmentTypeSourceFragmentTypeEnumRuleCall_0_0());
				}
				lv_sourceFragmentType_0_0=ruleSourceFragmentType
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSourceFragmentRule());
					}
					set(
						$current,
						"sourceFragmentType",
						lv_sourceFragmentType_0_0,
						"org.eclipse.n4js.n4mf.N4MF.SourceFragmentType");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_1=LeftCurlyBracket
		{
			newLeafNode(otherlv_1, grammarAccess.getSourceFragmentAccess().getLeftCurlyBracketKeyword_1());
		}
		(
			(
				lv_paths_2_0=RULE_STRING
				{
					newLeafNode(lv_paths_2_0, grammarAccess.getSourceFragmentAccess().getPathsSTRINGTerminalRuleCall_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSourceFragmentRule());
					}
					addWithLastConsumed(
						$current,
						"paths",
						lv_paths_2_0,
						"org.eclipse.xtext.common.Terminals.STRING");
				}
			)
		)
		(
			otherlv_3=Comma
			{
				newLeafNode(otherlv_3, grammarAccess.getSourceFragmentAccess().getCommaKeyword_3_0());
			}
			(
				(
					lv_paths_4_0=RULE_STRING
					{
						newLeafNode(lv_paths_4_0, grammarAccess.getSourceFragmentAccess().getPathsSTRINGTerminalRuleCall_3_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getSourceFragmentRule());
						}
						addWithLastConsumed(
							$current,
							"paths",
							lv_paths_4_0,
							"org.eclipse.xtext.common.Terminals.STRING");
					}
				)
			)
		)*
		otherlv_5=RightCurlyBracket
		{
			newLeafNode(otherlv_5, grammarAccess.getSourceFragmentAccess().getRightCurlyBracketKeyword_4());
		}
	)
;

// Entry rule entryRuleModuleFilter
entryRuleModuleFilter returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getModuleFilterRule()); }
	iv_ruleModuleFilter=ruleModuleFilter
	{ $current=$iv_ruleModuleFilter.current; }
	EOF;

// Rule ModuleFilter
ruleModuleFilter returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getModuleFilterAccess().getModuleFilterTypeModuleFilterTypeEnumRuleCall_0_0());
				}
				lv_moduleFilterType_0_0=ruleModuleFilterType
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getModuleFilterRule());
					}
					set(
						$current,
						"moduleFilterType",
						lv_moduleFilterType_0_0,
						"org.eclipse.n4js.n4mf.N4MF.ModuleFilterType");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_1=LeftCurlyBracket
		{
			newLeafNode(otherlv_1, grammarAccess.getModuleFilterAccess().getLeftCurlyBracketKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getModuleFilterAccess().getModuleSpecifiersModuleFilterSpecifierParserRuleCall_2_0());
				}
				lv_moduleSpecifiers_2_0=ruleModuleFilterSpecifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getModuleFilterRule());
					}
					add(
						$current,
						"moduleSpecifiers",
						lv_moduleSpecifiers_2_0,
						"org.eclipse.n4js.n4mf.N4MF.ModuleFilterSpecifier");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_3=Comma
			{
				newLeafNode(otherlv_3, grammarAccess.getModuleFilterAccess().getCommaKeyword_3_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getModuleFilterAccess().getModuleSpecifiersModuleFilterSpecifierParserRuleCall_3_1_0());
					}
					lv_moduleSpecifiers_4_0=ruleModuleFilterSpecifier
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getModuleFilterRule());
						}
						add(
							$current,
							"moduleSpecifiers",
							lv_moduleSpecifiers_4_0,
							"org.eclipse.n4js.n4mf.N4MF.ModuleFilterSpecifier");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		otherlv_5=RightCurlyBracket
		{
			newLeafNode(otherlv_5, grammarAccess.getModuleFilterAccess().getRightCurlyBracketKeyword_4());
		}
	)
;

// Entry rule entryRuleBootstrapModule
entryRuleBootstrapModule returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getBootstrapModuleRule()); }
	iv_ruleBootstrapModule=ruleBootstrapModule
	{ $current=$iv_ruleBootstrapModule.current; }
	EOF;

// Rule BootstrapModule
ruleBootstrapModule returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_moduleSpecifierWithWildcard_0_0=RULE_STRING
				{
					newLeafNode(lv_moduleSpecifierWithWildcard_0_0, grammarAccess.getBootstrapModuleAccess().getModuleSpecifierWithWildcardSTRINGTerminalRuleCall_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getBootstrapModuleRule());
					}
					setWithLastConsumed(
						$current,
						"moduleSpecifierWithWildcard",
						lv_moduleSpecifierWithWildcard_0_0,
						"org.eclipse.xtext.common.Terminals.STRING");
				}
			)
		)
		(
			otherlv_1=In
			{
				newLeafNode(otherlv_1, grammarAccess.getBootstrapModuleAccess().getInKeyword_1_0());
			}
			(
				(
					lv_sourcePath_2_0=RULE_STRING
					{
						newLeafNode(lv_sourcePath_2_0, grammarAccess.getBootstrapModuleAccess().getSourcePathSTRINGTerminalRuleCall_1_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getBootstrapModuleRule());
						}
						setWithLastConsumed(
							$current,
							"sourcePath",
							lv_sourcePath_2_0,
							"org.eclipse.xtext.common.Terminals.STRING");
					}
				)
			)
		)?
	)
;

// Entry rule entryRuleModuleFilterSpecifier
entryRuleModuleFilterSpecifier returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getModuleFilterSpecifierRule()); }
	iv_ruleModuleFilterSpecifier=ruleModuleFilterSpecifier
	{ $current=$iv_ruleModuleFilterSpecifier.current; }
	EOF;

// Rule ModuleFilterSpecifier
ruleModuleFilterSpecifier returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_moduleSpecifierWithWildcard_0_0=RULE_STRING
				{
					newLeafNode(lv_moduleSpecifierWithWildcard_0_0, grammarAccess.getModuleFilterSpecifierAccess().getModuleSpecifierWithWildcardSTRINGTerminalRuleCall_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getModuleFilterSpecifierRule());
					}
					setWithLastConsumed(
						$current,
						"moduleSpecifierWithWildcard",
						lv_moduleSpecifierWithWildcard_0_0,
						"org.eclipse.xtext.common.Terminals.STRING");
				}
			)
		)
		(
			otherlv_1=In
			{
				newLeafNode(otherlv_1, grammarAccess.getModuleFilterSpecifierAccess().getInKeyword_1_0());
			}
			(
				(
					lv_sourcePath_2_0=RULE_STRING
					{
						newLeafNode(lv_sourcePath_2_0, grammarAccess.getModuleFilterSpecifierAccess().getSourcePathSTRINGTerminalRuleCall_1_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getModuleFilterSpecifierRule());
						}
						setWithLastConsumed(
							$current,
							"sourcePath",
							lv_sourcePath_2_0,
							"org.eclipse.xtext.common.Terminals.STRING");
					}
				)
			)
		)?
	)
;

// Entry rule entryRuleProvidedRuntimeLibraryDependency
entryRuleProvidedRuntimeLibraryDependency returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getProvidedRuntimeLibraryDependencyRule()); }
	iv_ruleProvidedRuntimeLibraryDependency=ruleProvidedRuntimeLibraryDependency
	{ $current=$iv_ruleProvidedRuntimeLibraryDependency.current; }
	EOF;

// Rule ProvidedRuntimeLibraryDependency
ruleProvidedRuntimeLibraryDependency returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getProvidedRuntimeLibraryDependencyAccess().getProjectSimpleProjectDescriptionParserRuleCall_0());
			}
			lv_project_0_0=ruleSimpleProjectDescription
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getProvidedRuntimeLibraryDependencyRule());
				}
				set(
					$current,
					"project",
					lv_project_0_0,
					"org.eclipse.n4js.n4mf.N4MF.SimpleProjectDescription");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleRequiredRuntimeLibraryDependency
entryRuleRequiredRuntimeLibraryDependency returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getRequiredRuntimeLibraryDependencyRule()); }
	iv_ruleRequiredRuntimeLibraryDependency=ruleRequiredRuntimeLibraryDependency
	{ $current=$iv_ruleRequiredRuntimeLibraryDependency.current; }
	EOF;

// Rule RequiredRuntimeLibraryDependency
ruleRequiredRuntimeLibraryDependency returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getRequiredRuntimeLibraryDependencyAccess().getProjectSimpleProjectDescriptionParserRuleCall_0());
			}
			lv_project_0_0=ruleSimpleProjectDescription
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getRequiredRuntimeLibraryDependencyRule());
				}
				set(
					$current,
					"project",
					lv_project_0_0,
					"org.eclipse.n4js.n4mf.N4MF.SimpleProjectDescription");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleTestedProject
entryRuleTestedProject returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTestedProjectRule()); }
	iv_ruleTestedProject=ruleTestedProject
	{ $current=$iv_ruleTestedProject.current; }
	EOF;

// Rule TestedProject
ruleTestedProject returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getTestedProjectAccess().getProjectSimpleProjectDescriptionParserRuleCall_0());
			}
			lv_project_0_0=ruleSimpleProjectDescription
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getTestedProjectRule());
				}
				set(
					$current,
					"project",
					lv_project_0_0,
					"org.eclipse.n4js.n4mf.N4MF.SimpleProjectDescription");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleProjectReference
entryRuleProjectReference returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getProjectReferenceRule()); }
	iv_ruleProjectReference=ruleProjectReference
	{ $current=$iv_ruleProjectReference.current; }
	EOF;

// Rule ProjectReference
ruleProjectReference returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getProjectReferenceAccess().getProjectSimpleProjectDescriptionParserRuleCall_0());
			}
			lv_project_0_0=ruleSimpleProjectDescription
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getProjectReferenceRule());
				}
				set(
					$current,
					"project",
					lv_project_0_0,
					"org.eclipse.n4js.n4mf.N4MF.SimpleProjectDescription");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleProjectDependency
entryRuleProjectDependency returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getProjectDependencyRule()); }
	iv_ruleProjectDependency=ruleProjectDependency
	{ $current=$iv_ruleProjectDependency.current; }
	EOF;

// Rule ProjectDependency
ruleProjectDependency returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getProjectDependencyAccess().getProjectSimpleProjectDescriptionParserRuleCall_0_0());
				}
				lv_project_0_0=ruleSimpleProjectDescription
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getProjectDependencyRule());
					}
					set(
						$current,
						"project",
						lv_project_0_0,
						"org.eclipse.n4js.n4mf.N4MF.SimpleProjectDescription");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getProjectDependencyAccess().getVersionConstraintVersionConstraintParserRuleCall_1_0());
				}
				lv_versionConstraint_1_0=ruleVersionConstraint
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getProjectDependencyRule());
					}
					set(
						$current,
						"versionConstraint",
						lv_versionConstraint_1_0,
						"org.eclipse.n4js.n4mf.N4MF.VersionConstraint");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getProjectDependencyAccess().getDeclaredScopeProjectDependencyScopeEnumRuleCall_2_0());
				}
				lv_declaredScope_2_0=ruleProjectDependencyScope
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getProjectDependencyRule());
					}
					set(
						$current,
						"declaredScope",
						lv_declaredScope_2_0,
						"org.eclipse.n4js.n4mf.N4MF.ProjectDependencyScope");
					afterParserOrEnumRuleCall();
				}
			)
		)?
	)
;

// Entry rule entryRuleSimpleProjectDescription
entryRuleSimpleProjectDescription returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSimpleProjectDescriptionRule()); }
	iv_ruleSimpleProjectDescription=ruleSimpleProjectDescription
	{ $current=$iv_ruleSimpleProjectDescription.current; }
	EOF;

// Rule SimpleProjectDescription
ruleSimpleProjectDescription returns [EObject current=null]
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
						newCompositeNode(grammarAccess.getSimpleProjectDescriptionAccess().getDeclaredVendorIdN4mfIdentifierParserRuleCall_0_0_0());
					}
					lv_declaredVendorId_0_0=ruleN4mfIdentifier
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSimpleProjectDescriptionRule());
						}
						set(
							$current,
							"declaredVendorId",
							lv_declaredVendorId_0_0,
							"org.eclipse.n4js.n4mf.N4MF.N4mfIdentifier");
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_1=Colon
			{
				newLeafNode(otherlv_1, grammarAccess.getSimpleProjectDescriptionAccess().getColonKeyword_0_1());
			}
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getSimpleProjectDescriptionAccess().getProjectIdN4mfIdentifierParserRuleCall_1_0());
				}
				lv_projectId_2_0=ruleN4mfIdentifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSimpleProjectDescriptionRule());
					}
					set(
						$current,
						"projectId",
						lv_projectId_2_0,
						"org.eclipse.n4js.n4mf.N4MF.N4mfIdentifier");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleVersionConstraint
entryRuleVersionConstraint returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getVersionConstraintRule()); }
	iv_ruleVersionConstraint=ruleVersionConstraint
	{ $current=$iv_ruleVersionConstraint.current; }
	EOF;

// Rule VersionConstraint
ruleVersionConstraint returns [EObject current=null]
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
					(
						lv_exclLowerBound_0_0=LeftParenthesis
						{
							newLeafNode(lv_exclLowerBound_0_0, grammarAccess.getVersionConstraintAccess().getExclLowerBoundLeftParenthesisKeyword_0_0_0_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getVersionConstraintRule());
							}
							setWithLastConsumed($current, "exclLowerBound", true, "(");
						}
					)
				)
				    |
				otherlv_1=LeftSquareBracket
				{
					newLeafNode(otherlv_1, grammarAccess.getVersionConstraintAccess().getLeftSquareBracketKeyword_0_0_1());
				}
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getVersionConstraintAccess().getLowerVersionDeclaredVersionParserRuleCall_0_1_0());
					}
					lv_lowerVersion_2_0=ruleDeclaredVersion
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getVersionConstraintRule());
						}
						set(
							$current,
							"lowerVersion",
							lv_lowerVersion_2_0,
							"org.eclipse.n4js.n4mf.N4MF.DeclaredVersion");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				(
					otherlv_3=Comma
					{
						newLeafNode(otherlv_3, grammarAccess.getVersionConstraintAccess().getCommaKeyword_0_2_0_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getVersionConstraintAccess().getUpperVersionDeclaredVersionParserRuleCall_0_2_0_1_0());
							}
							lv_upperVersion_4_0=ruleDeclaredVersion
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getVersionConstraintRule());
								}
								set(
									$current,
									"upperVersion",
									lv_upperVersion_4_0,
									"org.eclipse.n4js.n4mf.N4MF.DeclaredVersion");
								afterParserOrEnumRuleCall();
							}
						)
					)
					(
						(
							(
								lv_exclUpperBound_5_0=RightParenthesis
								{
									newLeafNode(lv_exclUpperBound_5_0, grammarAccess.getVersionConstraintAccess().getExclUpperBoundRightParenthesisKeyword_0_2_0_2_0_0());
								}
								{
									if ($current==null) {
										$current = createModelElement(grammarAccess.getVersionConstraintRule());
									}
									setWithLastConsumed($current, "exclUpperBound", true, ")");
								}
							)
						)
						    |
						otherlv_6=RightSquareBracket
						{
							newLeafNode(otherlv_6, grammarAccess.getVersionConstraintAccess().getRightSquareBracketKeyword_0_2_0_2_1());
						}
					)
				)?
				    |
				otherlv_7=RightParenthesis
				{
					newLeafNode(otherlv_7, grammarAccess.getVersionConstraintAccess().getRightParenthesisKeyword_0_2_1());
				}
			)
		)
		    |
		(
			(
				{
					newCompositeNode(grammarAccess.getVersionConstraintAccess().getLowerVersionDeclaredVersionParserRuleCall_1_0());
				}
				lv_lowerVersion_8_0=ruleDeclaredVersion
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVersionConstraintRule());
					}
					set(
						$current,
						"lowerVersion",
						lv_lowerVersion_8_0,
						"org.eclipse.n4js.n4mf.N4MF.DeclaredVersion");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleN4mfIdentifier
entryRuleN4mfIdentifier returns [String current=null]:
	{ newCompositeNode(grammarAccess.getN4mfIdentifierRule()); }
	iv_ruleN4mfIdentifier=ruleN4mfIdentifier
	{ $current=$iv_ruleN4mfIdentifier.current.getText(); }
	EOF;

// Rule N4mfIdentifier
ruleN4mfIdentifier returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_ID_0=RULE_ID
		{
			$current.merge(this_ID_0);
		}
		{
			newLeafNode(this_ID_0, grammarAccess.getN4mfIdentifierAccess().getIDTerminalRuleCall_0());
		}
		    |
		kw=ProjectId
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getProjectIdKeyword_1());
		}
		    |
		kw=ProjectType
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getProjectTypeKeyword_2());
		}
		    |
		kw=ProjectVersion
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getProjectVersionKeyword_3());
		}
		    |
		kw=VendorId
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getVendorIdKeyword_4());
		}
		    |
		kw=VendorName
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getVendorNameKeyword_5());
		}
		    |
		kw=Output
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getOutputKeyword_6());
		}
		    |
		kw=Libraries
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getLibrariesKeyword_7());
		}
		    |
		kw=Resources
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getResourcesKeyword_8());
		}
		    |
		kw=Sources
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getSourcesKeyword_9());
		}
		    |
		kw=ModuleFilters
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getModuleFiltersKeyword_10());
		}
		    |
		(
			kw=ProjectDependencies
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getProjectDependenciesKeyword_11_0());
			}
			kw=KW_System
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getSystemKeyword_11_1());
			}
		)
		    |
		kw=API
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getAPIKeyword_12());
		}
		    |
		kw=User
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getUserKeyword_13());
		}
		    |
		kw=Application
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getApplicationKeyword_14());
		}
		    |
		(
			kw=Processor
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getProcessorKeyword_15_0());
			}
			kw=Source
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getSourceKeyword_15_1());
			}
		)
		    |
		kw=Content
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getContentKeyword_16());
		}
		    |
		kw=Test
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4mfIdentifierAccess().getTestKeyword_17());
		}
	)
;

// Rule ProjectType
ruleProjectType returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0=Application
			{
				$current = grammarAccess.getProjectTypeAccess().getAPPLICATIONEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getProjectTypeAccess().getAPPLICATIONEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=Processor
			{
				$current = grammarAccess.getProjectTypeAccess().getPROCESSOREnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getProjectTypeAccess().getPROCESSOREnumLiteralDeclaration_1());
			}
		)
		    |
		(
			enumLiteral_2=Library
			{
				$current = grammarAccess.getProjectTypeAccess().getLIBRARYEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_2, grammarAccess.getProjectTypeAccess().getLIBRARYEnumLiteralDeclaration_2());
			}
		)
		    |
		(
			enumLiteral_3=API
			{
				$current = grammarAccess.getProjectTypeAccess().getAPIEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_3, grammarAccess.getProjectTypeAccess().getAPIEnumLiteralDeclaration_3());
			}
		)
		    |
		(
			enumLiteral_4=RuntimeEnvironment
			{
				$current = grammarAccess.getProjectTypeAccess().getRUNTIME_ENVIRONMENTEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_4, grammarAccess.getProjectTypeAccess().getRUNTIME_ENVIRONMENTEnumLiteralDeclaration_4());
			}
		)
		    |
		(
			enumLiteral_5=RuntimeLibrary
			{
				$current = grammarAccess.getProjectTypeAccess().getRUNTIME_LIBRARYEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_5, grammarAccess.getProjectTypeAccess().getRUNTIME_LIBRARYEnumLiteralDeclaration_5());
			}
		)
		    |
		(
			enumLiteral_6=Test
			{
				$current = grammarAccess.getProjectTypeAccess().getTESTEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_6, grammarAccess.getProjectTypeAccess().getTESTEnumLiteralDeclaration_6());
			}
		)
	)
;

// Rule SourceFragmentType
ruleSourceFragmentType returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0=Source
			{
				$current = grammarAccess.getSourceFragmentTypeAccess().getSOURCEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getSourceFragmentTypeAccess().getSOURCEEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=External
			{
				$current = grammarAccess.getSourceFragmentTypeAccess().getEXTERNALEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getSourceFragmentTypeAccess().getEXTERNALEnumLiteralDeclaration_1());
			}
		)
		    |
		(
			enumLiteral_2=Test
			{
				$current = grammarAccess.getSourceFragmentTypeAccess().getTESTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_2, grammarAccess.getSourceFragmentTypeAccess().getTESTEnumLiteralDeclaration_2());
			}
		)
	)
;

// Rule ModuleFilterType
ruleModuleFilterType returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0=NoValidate
			{
				$current = grammarAccess.getModuleFilterTypeAccess().getNO_VALIDATEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getModuleFilterTypeAccess().getNO_VALIDATEEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=NoModuleWrap
			{
				$current = grammarAccess.getModuleFilterTypeAccess().getNO_MODULE_WRAPPINGEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getModuleFilterTypeAccess().getNO_MODULE_WRAPPINGEnumLiteralDeclaration_1());
			}
		)
	)
;

// Rule ProjectDependencyScope
ruleProjectDependencyScope returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0=Compile
			{
				$current = grammarAccess.getProjectDependencyScopeAccess().getCOMPILEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getProjectDependencyScopeAccess().getCOMPILEEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=Test
			{
				$current = grammarAccess.getProjectDependencyScopeAccess().getTESTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getProjectDependencyScopeAccess().getTESTEnumLiteralDeclaration_1());
			}
		)
	)
;

// Rule ModuleLoader
ruleModuleLoader returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0=N4js
			{
				$current = grammarAccess.getModuleLoaderAccess().getN4JSEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getModuleLoaderAccess().getN4JSEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=Commonjs
			{
				$current = grammarAccess.getModuleLoaderAccess().getCOMMONJSEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getModuleLoaderAccess().getCOMMONJSEnumLiteralDeclaration_1());
			}
		)
		    |
		(
			enumLiteral_2=Node_builtin
			{
				$current = grammarAccess.getModuleLoaderAccess().getNODE_BUILTINEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_2, grammarAccess.getModuleLoaderAccess().getNODE_BUILTINEnumLiteralDeclaration_2());
			}
		)
	)
;
