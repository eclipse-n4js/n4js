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
							({true}?=>(otherlv_19=ExtendedRuntimeEnvironment
							{
								newLeafNode(otherlv_19, grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentKeyword_6_0());
							}
							otherlv_20=Colon
							{
								newLeafNode(otherlv_20, grammarAccess.getProjectDescriptionAccess().getColonKeyword_6_1());
							}
							(
								(
									{
										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getExtendedRuntimeEnvironmentProjectReferenceParserRuleCall_6_2_0());
									}
									lv_extendedRuntimeEnvironment_21_0=ruleProjectReference
									{
										if ($current==null) {
											$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
										}
										set(
											$current,
											"extendedRuntimeEnvironment",
											lv_extendedRuntimeEnvironment_21_0,
											"org.eclipse.n4js.n4mf.N4MF.ProjectReference");
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
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 7);
				}
							({true}?=>(otherlv_22=ProvidedRuntimeLibraries
							{
								newLeafNode(otherlv_22, grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesKeyword_7_0());
							}
							otherlv_23=LeftCurlyBracket
							{
								newLeafNode(otherlv_23, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_7_1());
							}
							(
								(
									(
										{
											newCompositeNode(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_7_2_0_0());
										}
										lv_providedRuntimeLibraries_24_0=ruleProvidedRuntimeLibraryDependency
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
											}
											add(
												$current,
												"providedRuntimeLibraries",
												lv_providedRuntimeLibraries_24_0,
												"org.eclipse.n4js.n4mf.N4MF.ProvidedRuntimeLibraryDependency");
											afterParserOrEnumRuleCall();
										}
									)
								)
								(
									otherlv_25=Comma
									{
										newLeafNode(otherlv_25, grammarAccess.getProjectDescriptionAccess().getCommaKeyword_7_2_1_0());
									}
									(
										(
											{
												newCompositeNode(grammarAccess.getProjectDescriptionAccess().getProvidedRuntimeLibrariesProvidedRuntimeLibraryDependencyParserRuleCall_7_2_1_1_0());
											}
											lv_providedRuntimeLibraries_26_0=ruleProvidedRuntimeLibraryDependency
											{
												if ($current==null) {
													$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
												}
												add(
													$current,
													"providedRuntimeLibraries",
													lv_providedRuntimeLibraries_26_0,
													"org.eclipse.n4js.n4mf.N4MF.ProvidedRuntimeLibraryDependency");
												afterParserOrEnumRuleCall();
											}
										)
									)
								)*
							)?
							otherlv_27=RightCurlyBracket
							{
								newLeafNode(otherlv_27, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_7_3());
							}
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
							({true}?=>(otherlv_28=RequiredRuntimeLibraries
							{
								newLeafNode(otherlv_28, grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesKeyword_8_0());
							}
							otherlv_29=LeftCurlyBracket
							{
								newLeafNode(otherlv_29, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_8_1());
							}
							(
								(
									(
										{
											newCompositeNode(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_8_2_0_0());
										}
										lv_requiredRuntimeLibraries_30_0=ruleRequiredRuntimeLibraryDependency
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
											}
											add(
												$current,
												"requiredRuntimeLibraries",
												lv_requiredRuntimeLibraries_30_0,
												"org.eclipse.n4js.n4mf.N4MF.RequiredRuntimeLibraryDependency");
											afterParserOrEnumRuleCall();
										}
									)
								)
								(
									otherlv_31=Comma
									{
										newLeafNode(otherlv_31, grammarAccess.getProjectDescriptionAccess().getCommaKeyword_8_2_1_0());
									}
									(
										(
											{
												newCompositeNode(grammarAccess.getProjectDescriptionAccess().getRequiredRuntimeLibrariesRequiredRuntimeLibraryDependencyParserRuleCall_8_2_1_1_0());
											}
											lv_requiredRuntimeLibraries_32_0=ruleRequiredRuntimeLibraryDependency
											{
												if ($current==null) {
													$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
												}
												add(
													$current,
													"requiredRuntimeLibraries",
													lv_requiredRuntimeLibraries_32_0,
													"org.eclipse.n4js.n4mf.N4MF.RequiredRuntimeLibraryDependency");
												afterParserOrEnumRuleCall();
											}
										)
									)
								)*
							)?
							otherlv_33=RightCurlyBracket
							{
								newLeafNode(otherlv_33, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_8_3());
							}
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
							({true}?=>(otherlv_34=ProjectDependencies
							{
								newLeafNode(otherlv_34, grammarAccess.getProjectDescriptionAccess().getProjectDependenciesKeyword_9_0());
							}
							otherlv_35=LeftCurlyBracket
							{
								newLeafNode(otherlv_35, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_9_1());
							}
							(
								(
									(
										{
											newCompositeNode(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesProjectDependencyParserRuleCall_9_2_0_0());
										}
										lv_projectDependencies_36_0=ruleProjectDependency
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
											}
											add(
												$current,
												"projectDependencies",
												lv_projectDependencies_36_0,
												"org.eclipse.n4js.n4mf.N4MF.ProjectDependency");
											afterParserOrEnumRuleCall();
										}
									)
								)
								(
									otherlv_37=Comma
									{
										newLeafNode(otherlv_37, grammarAccess.getProjectDescriptionAccess().getCommaKeyword_9_2_1_0());
									}
									(
										(
											{
												newCompositeNode(grammarAccess.getProjectDescriptionAccess().getProjectDependenciesProjectDependencyParserRuleCall_9_2_1_1_0());
											}
											lv_projectDependencies_38_0=ruleProjectDependency
											{
												if ($current==null) {
													$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
												}
												add(
													$current,
													"projectDependencies",
													lv_projectDependencies_38_0,
													"org.eclipse.n4js.n4mf.N4MF.ProjectDependency");
												afterParserOrEnumRuleCall();
											}
										)
									)
								)*
							)?
							otherlv_39=RightCurlyBracket
							{
								newLeafNode(otherlv_39, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_9_3());
							}
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
							({true}?=>(otherlv_40=ImplementationId
							{
								newLeafNode(otherlv_40, grammarAccess.getProjectDescriptionAccess().getImplementationIdKeyword_10_0());
							}
							otherlv_41=Colon
							{
								newLeafNode(otherlv_41, grammarAccess.getProjectDescriptionAccess().getColonKeyword_10_1());
							}
							(
								(
									{
										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getImplementationIdN4mfIdentifierParserRuleCall_10_2_0());
									}
									lv_implementationId_42_0=ruleN4mfIdentifier
									{
										if ($current==null) {
											$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
										}
										set(
											$current,
											"implementationId",
											lv_implementationId_42_0,
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
							({true}?=>(otherlv_43=ImplementedProjects
							{
								newLeafNode(otherlv_43, grammarAccess.getProjectDescriptionAccess().getImplementedProjectsKeyword_11_0());
							}
							otherlv_44=LeftCurlyBracket
							{
								newLeafNode(otherlv_44, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_11_1());
							}
							(
								(
									(
										{
											newCompositeNode(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsProjectReferenceParserRuleCall_11_2_0_0());
										}
										lv_implementedProjects_45_0=ruleProjectReference
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
											}
											add(
												$current,
												"implementedProjects",
												lv_implementedProjects_45_0,
												"org.eclipse.n4js.n4mf.N4MF.ProjectReference");
											afterParserOrEnumRuleCall();
										}
									)
								)
								(
									otherlv_46=Comma
									{
										newLeafNode(otherlv_46, grammarAccess.getProjectDescriptionAccess().getCommaKeyword_11_2_1_0());
									}
									(
										(
											{
												newCompositeNode(grammarAccess.getProjectDescriptionAccess().getImplementedProjectsProjectReferenceParserRuleCall_11_2_1_1_0());
											}
											lv_implementedProjects_47_0=ruleProjectReference
											{
												if ($current==null) {
													$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
												}
												add(
													$current,
													"implementedProjects",
													lv_implementedProjects_47_0,
													"org.eclipse.n4js.n4mf.N4MF.ProjectReference");
												afterParserOrEnumRuleCall();
											}
										)
									)
								)*
							)?
							otherlv_48=RightCurlyBracket
							{
								newLeafNode(otherlv_48, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_11_3());
							}
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
							({true}?=>(otherlv_49=InitModules
							{
								newLeafNode(otherlv_49, grammarAccess.getProjectDescriptionAccess().getInitModulesKeyword_12_0());
							}
							otherlv_50=LeftCurlyBracket
							{
								newLeafNode(otherlv_50, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_12_1());
							}
							(
								(
									(
										{
											newCompositeNode(grammarAccess.getProjectDescriptionAccess().getInitModulesBootstrapModuleParserRuleCall_12_2_0_0());
										}
										lv_initModules_51_0=ruleBootstrapModule
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
											}
											add(
												$current,
												"initModules",
												lv_initModules_51_0,
												"org.eclipse.n4js.n4mf.N4MF.BootstrapModule");
											afterParserOrEnumRuleCall();
										}
									)
								)
								(
									otherlv_52=Comma
									{
										newLeafNode(otherlv_52, grammarAccess.getProjectDescriptionAccess().getCommaKeyword_12_2_1_0());
									}
									(
										(
											{
												newCompositeNode(grammarAccess.getProjectDescriptionAccess().getInitModulesBootstrapModuleParserRuleCall_12_2_1_1_0());
											}
											lv_initModules_53_0=ruleBootstrapModule
											{
												if ($current==null) {
													$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
												}
												add(
													$current,
													"initModules",
													lv_initModules_53_0,
													"org.eclipse.n4js.n4mf.N4MF.BootstrapModule");
												afterParserOrEnumRuleCall();
											}
										)
									)
								)*
							)?
							otherlv_54=RightCurlyBracket
							{
								newLeafNode(otherlv_54, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_12_3());
							}
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
							({true}?=>(otherlv_55=ExecModule
							{
								newLeafNode(otherlv_55, grammarAccess.getProjectDescriptionAccess().getExecModuleKeyword_13_0());
							}
							otherlv_56=Colon
							{
								newLeafNode(otherlv_56, grammarAccess.getProjectDescriptionAccess().getColonKeyword_13_1());
							}
							(
								(
									{
										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getExecModuleBootstrapModuleParserRuleCall_13_2_0());
									}
									lv_execModule_57_0=ruleBootstrapModule
									{
										if ($current==null) {
											$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
										}
										set(
											$current,
											"execModule",
											lv_execModule_57_0,
											"org.eclipse.n4js.n4mf.N4MF.BootstrapModule");
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
			{getUnorderedGroupHelper().canSelect(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14)}?=>(
				{
					getUnorderedGroupHelper().select(grammarAccess.getProjectDescriptionAccess().getUnorderedGroup(), 14);
				}
							({true}?=>(otherlv_58=Output
							{
								newLeafNode(otherlv_58, grammarAccess.getProjectDescriptionAccess().getOutputKeyword_14_0());
							}
							otherlv_59=Colon
							{
								newLeafNode(otherlv_59, grammarAccess.getProjectDescriptionAccess().getColonKeyword_14_1());
							}
							(
								(
									lv_outputPathRaw_60_0=RULE_STRING
									{
										newLeafNode(lv_outputPathRaw_60_0, grammarAccess.getProjectDescriptionAccess().getOutputPathRawSTRINGTerminalRuleCall_14_2_0());
									}
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getProjectDescriptionRule());
										}
										setWithLastConsumed(
											$current,
											"outputPathRaw",
											lv_outputPathRaw_60_0,
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
							({true}?=>(otherlv_61=Libraries
							{
								newLeafNode(otherlv_61, grammarAccess.getProjectDescriptionAccess().getLibrariesKeyword_15_0());
							}
							otherlv_62=LeftCurlyBracket
							{
								newLeafNode(otherlv_62, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_15_1());
							}
							(
								(
									lv_libraryPathsRaw_63_0=RULE_STRING
									{
										newLeafNode(lv_libraryPathsRaw_63_0, grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawSTRINGTerminalRuleCall_15_2_0());
									}
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getProjectDescriptionRule());
										}
										addWithLastConsumed(
											$current,
											"libraryPathsRaw",
											lv_libraryPathsRaw_63_0,
											"org.eclipse.xtext.common.Terminals.STRING");
									}
								)
							)
							(
								otherlv_64=Comma
								{
									newLeafNode(otherlv_64, grammarAccess.getProjectDescriptionAccess().getCommaKeyword_15_3_0());
								}
								(
									(
										lv_libraryPathsRaw_65_0=RULE_STRING
										{
											newLeafNode(lv_libraryPathsRaw_65_0, grammarAccess.getProjectDescriptionAccess().getLibraryPathsRawSTRINGTerminalRuleCall_15_3_1_0());
										}
										{
											if ($current==null) {
												$current = createModelElement(grammarAccess.getProjectDescriptionRule());
											}
											addWithLastConsumed(
												$current,
												"libraryPathsRaw",
												lv_libraryPathsRaw_65_0,
												"org.eclipse.xtext.common.Terminals.STRING");
										}
									)
								)
							)*
							otherlv_66=RightCurlyBracket
							{
								newLeafNode(otherlv_66, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_15_4());
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
							({true}?=>(otherlv_67=Resources
							{
								newLeafNode(otherlv_67, grammarAccess.getProjectDescriptionAccess().getResourcesKeyword_16_0());
							}
							otherlv_68=LeftCurlyBracket
							{
								newLeafNode(otherlv_68, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_16_1());
							}
							(
								(
									lv_resourcePathsRaw_69_0=RULE_STRING
									{
										newLeafNode(lv_resourcePathsRaw_69_0, grammarAccess.getProjectDescriptionAccess().getResourcePathsRawSTRINGTerminalRuleCall_16_2_0());
									}
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getProjectDescriptionRule());
										}
										addWithLastConsumed(
											$current,
											"resourcePathsRaw",
											lv_resourcePathsRaw_69_0,
											"org.eclipse.xtext.common.Terminals.STRING");
									}
								)
							)
							(
								otherlv_70=Comma
								{
									newLeafNode(otherlv_70, grammarAccess.getProjectDescriptionAccess().getCommaKeyword_16_3_0());
								}
								(
									(
										lv_resourcePathsRaw_71_0=RULE_STRING
										{
											newLeafNode(lv_resourcePathsRaw_71_0, grammarAccess.getProjectDescriptionAccess().getResourcePathsRawSTRINGTerminalRuleCall_16_3_1_0());
										}
										{
											if ($current==null) {
												$current = createModelElement(grammarAccess.getProjectDescriptionRule());
											}
											addWithLastConsumed(
												$current,
												"resourcePathsRaw",
												lv_resourcePathsRaw_71_0,
												"org.eclipse.xtext.common.Terminals.STRING");
										}
									)
								)
							)*
							otherlv_72=RightCurlyBracket
							{
								newLeafNode(otherlv_72, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_16_4());
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
							({true}?=>(otherlv_73=Sources
							{
								newLeafNode(otherlv_73, grammarAccess.getProjectDescriptionAccess().getSourcesKeyword_17_0());
							}
							otherlv_74=LeftCurlyBracket
							{
								newLeafNode(otherlv_74, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_17_1());
							}
							(
								(
									{
										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getSourceFragmentSourceFragmentParserRuleCall_17_2_0());
									}
									lv_sourceFragment_75_0=ruleSourceFragment
									{
										if ($current==null) {
											$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
										}
										add(
											$current,
											"sourceFragment",
											lv_sourceFragment_75_0,
											"org.eclipse.n4js.n4mf.N4MF.SourceFragment");
										afterParserOrEnumRuleCall();
									}
								)
							)+
							otherlv_76=RightCurlyBracket
							{
								newLeafNode(otherlv_76, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_17_3());
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
							({true}?=>(otherlv_77=ModuleFilters
							{
								newLeafNode(otherlv_77, grammarAccess.getProjectDescriptionAccess().getModuleFiltersKeyword_18_0());
							}
							otherlv_78=LeftCurlyBracket
							{
								newLeafNode(otherlv_78, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_18_1());
							}
							(
								(
									{
										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getModuleFiltersModuleFilterParserRuleCall_18_2_0());
									}
									lv_moduleFilters_79_0=ruleModuleFilter
									{
										if ($current==null) {
											$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
										}
										add(
											$current,
											"moduleFilters",
											lv_moduleFilters_79_0,
											"org.eclipse.n4js.n4mf.N4MF.ModuleFilter");
										afterParserOrEnumRuleCall();
									}
								)
							)+
							otherlv_80=RightCurlyBracket
							{
								newLeafNode(otherlv_80, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_18_3());
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
							({true}?=>(otherlv_81=TestedProjects
							{
								newLeafNode(otherlv_81, grammarAccess.getProjectDescriptionAccess().getTestedProjectsKeyword_19_0());
							}
							otherlv_82=LeftCurlyBracket
							{
								newLeafNode(otherlv_82, grammarAccess.getProjectDescriptionAccess().getLeftCurlyBracketKeyword_19_1());
							}
							(
								(
									(
										{
											newCompositeNode(grammarAccess.getProjectDescriptionAccess().getTestedProjectsTestedProjectParserRuleCall_19_2_0_0());
										}
										lv_testedProjects_83_0=ruleTestedProject
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
											}
											add(
												$current,
												"testedProjects",
												lv_testedProjects_83_0,
												"org.eclipse.n4js.n4mf.N4MF.TestedProject");
											afterParserOrEnumRuleCall();
										}
									)
								)
								(
									otherlv_84=Comma
									{
										newLeafNode(otherlv_84, grammarAccess.getProjectDescriptionAccess().getCommaKeyword_19_2_1_0());
									}
									(
										(
											{
												newCompositeNode(grammarAccess.getProjectDescriptionAccess().getTestedProjectsTestedProjectParserRuleCall_19_2_1_1_0());
											}
											lv_testedProjects_85_0=ruleTestedProject
											{
												if ($current==null) {
													$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
												}
												add(
													$current,
													"testedProjects",
													lv_testedProjects_85_0,
													"org.eclipse.n4js.n4mf.N4MF.TestedProject");
												afterParserOrEnumRuleCall();
											}
										)
									)
								)*
							)?
							otherlv_86=RightCurlyBracket
							{
								newLeafNode(otherlv_86, grammarAccess.getProjectDescriptionAccess().getRightCurlyBracketKeyword_19_3());
							}
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
							({true}?=>(otherlv_87=ModuleLoader
							{
								newLeafNode(otherlv_87, grammarAccess.getProjectDescriptionAccess().getModuleLoaderKeyword_20_0());
							}
							otherlv_88=Colon
							{
								newLeafNode(otherlv_88, grammarAccess.getProjectDescriptionAccess().getColonKeyword_20_1());
							}
							(
								(
									{
										newCompositeNode(grammarAccess.getProjectDescriptionAccess().getModuleLoaderModuleLoaderEnumRuleCall_20_2_0());
									}
									lv_moduleLoader_89_0=ruleModuleLoader
									{
										if ($current==null) {
											$current = createModelElementForParent(grammarAccess.getProjectDescriptionRule());
										}
										set(
											$current,
											"moduleLoader",
											lv_moduleLoader_89_0,
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
				lv_pathsRaw_2_0=RULE_STRING
				{
					newLeafNode(lv_pathsRaw_2_0, grammarAccess.getSourceFragmentAccess().getPathsRawSTRINGTerminalRuleCall_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getSourceFragmentRule());
					}
					addWithLastConsumed(
						$current,
						"pathsRaw",
						lv_pathsRaw_2_0,
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
					lv_pathsRaw_4_0=RULE_STRING
					{
						newLeafNode(lv_pathsRaw_4_0, grammarAccess.getSourceFragmentAccess().getPathsRawSTRINGTerminalRuleCall_3_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getSourceFragmentRule());
						}
						addWithLastConsumed(
							$current,
							"pathsRaw",
							lv_pathsRaw_4_0,
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
	{
		if ($current==null) {
			$current = createModelElement(grammarAccess.getProvidedRuntimeLibraryDependencyRule());
		}
		newCompositeNode(grammarAccess.getProvidedRuntimeLibraryDependencyAccess().getProjectIdWithOptionalVendorParserRuleCall());
	}
	this_ProjectIdWithOptionalVendor_0=ruleProjectIdWithOptionalVendor[$current]
	{
		$current = $this_ProjectIdWithOptionalVendor_0.current;
		afterParserOrEnumRuleCall();
	}
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
	{
		if ($current==null) {
			$current = createModelElement(grammarAccess.getRequiredRuntimeLibraryDependencyRule());
		}
		newCompositeNode(grammarAccess.getRequiredRuntimeLibraryDependencyAccess().getProjectIdWithOptionalVendorParserRuleCall());
	}
	this_ProjectIdWithOptionalVendor_0=ruleProjectIdWithOptionalVendor[$current]
	{
		$current = $this_ProjectIdWithOptionalVendor_0.current;
		afterParserOrEnumRuleCall();
	}
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
	{
		if ($current==null) {
			$current = createModelElement(grammarAccess.getTestedProjectRule());
		}
		newCompositeNode(grammarAccess.getTestedProjectAccess().getProjectIdWithOptionalVendorParserRuleCall());
	}
	this_ProjectIdWithOptionalVendor_0=ruleProjectIdWithOptionalVendor[$current]
	{
		$current = $this_ProjectIdWithOptionalVendor_0.current;
		afterParserOrEnumRuleCall();
	}
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
	{
		if ($current==null) {
			$current = createModelElement(grammarAccess.getProjectReferenceRule());
		}
		newCompositeNode(grammarAccess.getProjectReferenceAccess().getProjectIdWithOptionalVendorParserRuleCall());
	}
	this_ProjectIdWithOptionalVendor_0=ruleProjectIdWithOptionalVendor[$current]
	{
		$current = $this_ProjectIdWithOptionalVendor_0.current;
		afterParserOrEnumRuleCall();
	}
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
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getProjectDependencyRule());
			}
			newCompositeNode(grammarAccess.getProjectDependencyAccess().getProjectIdWithOptionalVendorParserRuleCall_0());
		}
		this_ProjectIdWithOptionalVendor_0=ruleProjectIdWithOptionalVendor[$current]
		{
			$current = $this_ProjectIdWithOptionalVendor_0.current;
			afterParserOrEnumRuleCall();
		}
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


// Rule ProjectIdWithOptionalVendor
ruleProjectIdWithOptionalVendor[EObject in_current]  returns [EObject current=in_current]
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
						newCompositeNode(grammarAccess.getProjectIdWithOptionalVendorAccess().getDeclaredVendorIdN4mfIdentifierParserRuleCall_0_0_0());
					}
					lv_declaredVendorId_0_0=ruleN4mfIdentifier
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getProjectIdWithOptionalVendorRule());
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
				newLeafNode(otherlv_1, grammarAccess.getProjectIdWithOptionalVendorAccess().getColonKeyword_0_1());
			}
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getProjectIdWithOptionalVendorAccess().getProjectIdN4mfIdentifierParserRuleCall_1_0());
				}
				lv_projectId_2_0=ruleN4mfIdentifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getProjectIdWithOptionalVendorRule());
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
		    |
		(
			enumLiteral_7=Validation
			{
				$current = grammarAccess.getProjectTypeAccess().getVALIDATIONEnumLiteralDeclaration_7().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_7, grammarAccess.getProjectTypeAccess().getVALIDATIONEnumLiteralDeclaration_7());
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
