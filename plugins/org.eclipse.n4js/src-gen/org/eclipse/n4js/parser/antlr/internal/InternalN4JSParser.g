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
parser grammar InternalN4JSParser;

options {
	tokenVocab=InternalN4JSLexer;
	superClass=AbstractInternalAntlrParser;
}

@header {
package org.eclipse.n4js.parser.antlr.internal;

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
import org.eclipse.n4js.services.N4JSGrammarAccess;

}

@members {

// injected by AutomaticSemicolonInjector
protected void setInRegularExpression() {}
protected void setInTemplateSegment() {}
protected boolean forcedRewind(int marker) { return true; } // overridden in subtype
protected void promoteEOL() {} // overridden in subtype
protected void addASIMessage() {} // overridden in subtype
protected boolean hasDisallowedEOL() { return false; } // overridden in subtype
// end of injection

 	private N4JSGrammarAccess grammarAccess;

    public InternalN4JSParser(TokenStream input, N4JSGrammarAccess grammarAccess) {
        this(input);
        this.grammarAccess = grammarAccess;
        registerRules(grammarAccess.getGrammar());
    }

    @Override
    protected String getFirstRuleName() {
    	return "Script";
   	}

   	@Override
   	protected N4JSGrammarAccess getGrammarAccess() {
   		return grammarAccess;
   	}

}

@rulecatch {
    catch (RecognitionException re) {
        recover(input,re);
        appendSkippedTokens();
    }
}

// Entry rule entryRuleScript
entryRuleScript returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getScriptRule()); }
	iv_ruleScript=ruleScript
	{ $current=$iv_ruleScript.current; }
	EOF;

// Rule Script
ruleScript returns [EObject current=null]
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
					grammarAccess.getScriptAccess().getScriptAction_0(),
					$current);
			}
		)
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getScriptAccess().getAnnotationsScriptAnnotationParserRuleCall_1_0_0());
					}
					lv_annotations_1_0=ruleScriptAnnotation
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getScriptRule());
						}
						add(
							$current,
							"annotations",
							lv_annotations_1_0,
							"org.eclipse.n4js.N4JS.ScriptAnnotation");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getScriptAccess().getScriptElementsScriptElementParserRuleCall_1_1_0());
					}
					lv_scriptElements_2_0=ruleScriptElement
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getScriptRule());
						}
						add(
							$current,
							"scriptElements",
							lv_scriptElements_2_0,
							"org.eclipse.n4js.N4JS.ScriptElement");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleScriptElement
entryRuleScriptElement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getScriptElementRule()); }
	iv_ruleScriptElement=ruleScriptElement
	{ $current=$iv_ruleScriptElement.current; }
	EOF;

// Rule ScriptElement
ruleScriptElement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				CommercialAt
				(
					This
					    |
					Target
					    |
					RULE_IDENTIFIER
				)
			)
			)=>
			{
				newCompositeNode(grammarAccess.getScriptElementAccess().getAnnotatedScriptElementParserRuleCall_0());
			}
			this_AnnotatedScriptElement_0=ruleAnnotatedScriptElement
			{
				$current = $this_AnnotatedScriptElement_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
					(
						ruleN4Modifier
					)
				)*
				Class
				(
					(
						ruleTypingStrategyDefSiteOperator
					)
				)?
				(
					(
						ruleBindingIdentifier
					)
				)?
				(
					ruleVersionDeclaration[null]
				)?
			)
			)=>
			{
				newCompositeNode(grammarAccess.getScriptElementAccess().getN4ClassDeclarationParserRuleCall_1());
			}
			this_N4ClassDeclaration_1=ruleN4ClassDeclaration
			{
				$current = $this_N4ClassDeclaration_1.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
					(
						ruleN4Modifier
					)
				)*
				Interface
				(
					(
						ruleTypingStrategyDefSiteOperator
					)
				)?
				(
					(
						ruleBindingIdentifier
					)
				)?
				(
					ruleVersionDeclaration[null]
				)?
			)
			)=>
			{
				newCompositeNode(grammarAccess.getScriptElementAccess().getN4InterfaceDeclarationParserRuleCall_2());
			}
			this_N4InterfaceDeclaration_2=ruleN4InterfaceDeclaration
			{
				$current = $this_N4InterfaceDeclaration_2.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				(
					(
						ruleN4Modifier
					)
				)*
				Enum
				(
					(
						ruleBindingIdentifier
					)
				)?
				(
					ruleVersionDeclaration[null]
				)?
			)
			)=>
			{
				newCompositeNode(grammarAccess.getScriptElementAccess().getN4EnumDeclarationParserRuleCall_3());
			}
			this_N4EnumDeclaration_3=ruleN4EnumDeclaration
			{
				$current = $this_N4EnumDeclaration_3.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getScriptElementAccess().getImportDeclarationParserRuleCall_4());
		}
		this_ImportDeclaration_4=ruleImportDeclaration
		{
			$current = $this_ImportDeclaration_4.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getScriptElementAccess().getExportDeclarationParserRuleCall_5());
		}
		this_ExportDeclaration_5=ruleExportDeclaration
		{
			$current = $this_ExportDeclaration_5.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getScriptElementAccess().getRootStatementParserRuleCall_6());
		}
		this_RootStatement_6=ruleRootStatement
		{
			$current = $this_RootStatement_6.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleAnnotatedScriptElement
entryRuleAnnotatedScriptElement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAnnotatedScriptElementRule()); }
	iv_ruleAnnotatedScriptElement=ruleAnnotatedScriptElement
	{ $current=$iv_ruleAnnotatedScriptElement.current; }
	EOF;

// Rule AnnotatedScriptElement
ruleAnnotatedScriptElement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				CommercialAt
				(
					This
					    |
					Target
					    |
					RULE_IDENTIFIER
				)
			)
			)=>
			{
				newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getAnnotationListParserRuleCall_0());
			}
			this_AnnotationList_0=ruleAnnotationList
			{
				$current = $this_AnnotationList_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		(
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getAnnotatedScriptElementAccess().getExportDeclarationAnnotationListAction_1_0_0(),
							$current);
					}
				)
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAnnotatedScriptElementRule());
					}
					newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getExportDeclarationImplParserRuleCall_1_0_1());
				}
				this_ExportDeclarationImpl_2=ruleExportDeclarationImpl[$current]
				{
					$current = $this_ExportDeclarationImpl_2.current;
					afterParserOrEnumRuleCall();
				}
			)
			    |
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getAnnotatedScriptElementAccess().getImportDeclarationAnnotationListAction_1_1_0(),
							$current);
					}
				)
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAnnotatedScriptElementRule());
					}
					newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getImportDeclarationImplParserRuleCall_1_1_1());
				}
				this_ImportDeclarationImpl_4=ruleImportDeclarationImpl[$current]
				{
					$current = $this_ImportDeclarationImpl_4.current;
					afterParserOrEnumRuleCall();
				}
			)
			    |
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getAnnotatedScriptElementAccess().getFunctionDeclarationAnnotationListAction_1_2_0(),
							$current);
					}
				)
				(
					((
						(
							(
								ruleN4Modifier
							)
						)*
						ruleAsyncNoTrailingLineBreak[null]
						Function
					)
					)=>
					(
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getDeclaredModifiersN4ModifierEnumRuleCall_1_2_1_0_0_0());
								}
								lv_declaredModifiers_6_0=ruleN4Modifier
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedScriptElementRule());
									}
									add(
										$current,
										"declaredModifiers",
										lv_declaredModifiers_6_0,
										"org.eclipse.n4js.N4JS.N4Modifier");
									afterParserOrEnumRuleCall();
								}
							)
						)*
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getAnnotatedScriptElementRule());
							}
							newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getAsyncNoTrailingLineBreakParserRuleCall_1_2_1_0_1());
						}
						this_AsyncNoTrailingLineBreak_7=ruleAsyncNoTrailingLineBreak[$current]
						{
							$current = $this_AsyncNoTrailingLineBreak_7.current;
							afterParserOrEnumRuleCall();
						}
						(
							(Function)=>
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getAnnotatedScriptElementRule());
								}
								newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getFunctionImplParserRuleCall_1_2_1_0_2());
							}
							this_FunctionImpl_8=ruleFunctionImpl[$current]
							{
								$current = $this_FunctionImpl_8.current;
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			    |
			(
				(
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAnnotatedScriptElementAccess().getN4ClassDeclarationAnnotationListAction_1_3_0_0_0(),
									$current);
							}
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getDeclaredModifiersN4ModifierEnumRuleCall_1_3_0_0_1_0());
								}
								lv_declaredModifiers_10_0=ruleN4Modifier
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedScriptElementRule());
									}
									add(
										$current,
										"declaredModifiers",
										lv_declaredModifiers_10_0,
										"org.eclipse.n4js.N4JS.N4Modifier");
									afterParserOrEnumRuleCall();
								}
							)
						)*
						otherlv_11=Class
						{
							newLeafNode(otherlv_11, grammarAccess.getAnnotatedScriptElementAccess().getClassKeyword_1_3_0_0_2());
						}
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_3_0_0_3_0());
								}
								lv_typingStrategy_12_0=ruleTypingStrategyDefSiteOperator
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedScriptElementRule());
									}
									set(
										$current,
										"typingStrategy",
										lv_typingStrategy_12_0,
										"org.eclipse.n4js.ts.TypeExpressions.TypingStrategyDefSiteOperator");
									afterParserOrEnumRuleCall();
								}
							)
						)?
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getNameBindingIdentifierParserRuleCall_1_3_0_0_4_0());
								}
								lv_name_13_0=ruleBindingIdentifier
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedScriptElementRule());
									}
									set(
										$current,
										"name",
										lv_name_13_0,
										"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
									afterParserOrEnumRuleCall();
								}
							)
						)
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getAnnotatedScriptElementRule());
								}
								newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getVersionDeclarationParserRuleCall_1_3_0_0_5());
							}
							this_VersionDeclaration_14=ruleVersionDeclaration[$current]
							{
								$current = $this_VersionDeclaration_14.current;
								afterParserOrEnumRuleCall();
							}
						)?
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getAnnotatedScriptElementRule());
								}
								newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getTypeVariablesParserRuleCall_1_3_0_0_6());
							}
							this_TypeVariables_15=ruleTypeVariables[$current]
							{
								$current = $this_TypeVariables_15.current;
								afterParserOrEnumRuleCall();
							}
						)?
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getAnnotatedScriptElementRule());
								}
								newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getClassExtendsImplementsParserRuleCall_1_3_0_0_7());
							}
							this_ClassExtendsImplements_16=ruleClassExtendsImplements[$current]
							{
								$current = $this_ClassExtendsImplements_16.current;
								afterParserOrEnumRuleCall();
							}
						)?
					)
					    |
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAnnotatedScriptElementAccess().getN4InterfaceDeclarationAnnotationListAction_1_3_0_1_0(),
									$current);
							}
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getDeclaredModifiersN4ModifierEnumRuleCall_1_3_0_1_1_0());
								}
								lv_declaredModifiers_18_0=ruleN4Modifier
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedScriptElementRule());
									}
									add(
										$current,
										"declaredModifiers",
										lv_declaredModifiers_18_0,
										"org.eclipse.n4js.N4JS.N4Modifier");
									afterParserOrEnumRuleCall();
								}
							)
						)*
						otherlv_19=Interface
						{
							newLeafNode(otherlv_19, grammarAccess.getAnnotatedScriptElementAccess().getInterfaceKeyword_1_3_0_1_2());
						}
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_3_0_1_3_0());
								}
								lv_typingStrategy_20_0=ruleTypingStrategyDefSiteOperator
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedScriptElementRule());
									}
									set(
										$current,
										"typingStrategy",
										lv_typingStrategy_20_0,
										"org.eclipse.n4js.ts.TypeExpressions.TypingStrategyDefSiteOperator");
									afterParserOrEnumRuleCall();
								}
							)
						)?
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getNameBindingIdentifierParserRuleCall_1_3_0_1_4_0());
								}
								lv_name_21_0=ruleBindingIdentifier
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedScriptElementRule());
									}
									set(
										$current,
										"name",
										lv_name_21_0,
										"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
									afterParserOrEnumRuleCall();
								}
							)
						)
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getAnnotatedScriptElementRule());
								}
								newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getVersionDeclarationParserRuleCall_1_3_0_1_5());
							}
							this_VersionDeclaration_22=ruleVersionDeclaration[$current]
							{
								$current = $this_VersionDeclaration_22.current;
								afterParserOrEnumRuleCall();
							}
						)?
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getAnnotatedScriptElementRule());
								}
								newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getTypeVariablesParserRuleCall_1_3_0_1_6());
							}
							this_TypeVariables_23=ruleTypeVariables[$current]
							{
								$current = $this_TypeVariables_23.current;
								afterParserOrEnumRuleCall();
							}
						)?
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getAnnotatedScriptElementRule());
								}
								newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getInterfaceExtendsListParserRuleCall_1_3_0_1_7());
							}
							this_InterfaceExtendsList_24=ruleInterfaceExtendsList[$current]
							{
								$current = $this_InterfaceExtendsList_24.current;
								afterParserOrEnumRuleCall();
							}
						)?
					)
				)
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAnnotatedScriptElementRule());
					}
					newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getMembersParserRuleCall_1_3_1());
				}
				this_Members_25=ruleMembers[$current]
				{
					$current = $this_Members_25.current;
					afterParserOrEnumRuleCall();
				}
			)
			    |
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getAnnotatedScriptElementAccess().getN4EnumDeclarationAnnotationListAction_1_4_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getDeclaredModifiersN4ModifierEnumRuleCall_1_4_1_0());
						}
						lv_declaredModifiers_27_0=ruleN4Modifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedScriptElementRule());
							}
							add(
								$current,
								"declaredModifiers",
								lv_declaredModifiers_27_0,
								"org.eclipse.n4js.N4JS.N4Modifier");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				otherlv_28=Enum
				{
					newLeafNode(otherlv_28, grammarAccess.getAnnotatedScriptElementAccess().getEnumKeyword_1_4_2());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getNameBindingIdentifierParserRuleCall_1_4_3_0());
						}
						lv_name_29_0=ruleBindingIdentifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedScriptElementRule());
							}
							set(
								$current,
								"name",
								lv_name_29_0,
								"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getAnnotatedScriptElementRule());
						}
						newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getVersionDeclarationParserRuleCall_1_4_4());
					}
					this_VersionDeclaration_30=ruleVersionDeclaration[$current]
					{
						$current = $this_VersionDeclaration_30.current;
						afterParserOrEnumRuleCall();
					}
				)?
				otherlv_31=LeftCurlyBracket
				{
					newLeafNode(otherlv_31, grammarAccess.getAnnotatedScriptElementAccess().getLeftCurlyBracketKeyword_1_4_5());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getLiteralsN4EnumLiteralParserRuleCall_1_4_6_0());
						}
						lv_literals_32_0=ruleN4EnumLiteral
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedScriptElementRule());
							}
							add(
								$current,
								"literals",
								lv_literals_32_0,
								"org.eclipse.n4js.N4JS.N4EnumLiteral");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					otherlv_33=Comma
					{
						newLeafNode(otherlv_33, grammarAccess.getAnnotatedScriptElementAccess().getCommaKeyword_1_4_7_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getAnnotatedScriptElementAccess().getLiteralsN4EnumLiteralParserRuleCall_1_4_7_1_0());
							}
							lv_literals_34_0=ruleN4EnumLiteral
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getAnnotatedScriptElementRule());
								}
								add(
									$current,
									"literals",
									lv_literals_34_0,
									"org.eclipse.n4js.N4JS.N4EnumLiteral");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)*
				otherlv_35=RightCurlyBracket
				{
					newLeafNode(otherlv_35, grammarAccess.getAnnotatedScriptElementAccess().getRightCurlyBracketKeyword_1_4_8());
				}
			)
		)
	)
;

// Entry rule entryRuleExportDeclaration
entryRuleExportDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getExportDeclarationRule()); }
	iv_ruleExportDeclaration=ruleExportDeclaration
	{ $current=$iv_ruleExportDeclaration.current; }
	EOF;

// Rule ExportDeclaration
ruleExportDeclaration returns [EObject current=null]
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
					grammarAccess.getExportDeclarationAccess().getExportDeclarationAction_0(),
					$current);
			}
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getExportDeclarationRule());
			}
			newCompositeNode(grammarAccess.getExportDeclarationAccess().getExportDeclarationImplParserRuleCall_1());
		}
		this_ExportDeclarationImpl_1=ruleExportDeclarationImpl[$current]
		{
			$current = $this_ExportDeclarationImpl_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule ExportDeclarationImpl
ruleExportDeclarationImpl[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Export
		{
			newLeafNode(otherlv_0, grammarAccess.getExportDeclarationImplAccess().getExportKeyword_0());
		}
		(
			(
				(
					(
						lv_wildcardExport_1_0=Asterisk
						{
							newLeafNode(lv_wildcardExport_1_0, grammarAccess.getExportDeclarationImplAccess().getWildcardExportAsteriskKeyword_1_0_0_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getExportDeclarationImplRule());
							}
							setWithLastConsumed($current, "wildcardExport", true, "*");
						}
					)
				)
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getExportDeclarationImplRule());
					}
					newCompositeNode(grammarAccess.getExportDeclarationImplAccess().getExportFromClauseParserRuleCall_1_0_1());
				}
				this_ExportFromClause_2=ruleExportFromClause[$current]
				{
					$current = $this_ExportFromClause_2.current;
					afterParserOrEnumRuleCall();
				}
				{
					newCompositeNode(grammarAccess.getExportDeclarationImplAccess().getSemiParserRuleCall_1_0_2());
				}
				ruleSemi
				{
					afterParserOrEnumRuleCall();
				}
			)
			    |
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getExportDeclarationImplRule());
					}
					newCompositeNode(grammarAccess.getExportDeclarationImplAccess().getExportClauseParserRuleCall_1_1_0());
				}
				this_ExportClause_4=ruleExportClause[$current]
				{
					$current = $this_ExportClause_4.current;
					afterParserOrEnumRuleCall();
				}
				(
					(From)=>
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getExportDeclarationImplRule());
						}
						newCompositeNode(grammarAccess.getExportDeclarationImplAccess().getExportFromClauseParserRuleCall_1_1_1());
					}
					this_ExportFromClause_5=ruleExportFromClause[$current]
					{
						$current = $this_ExportFromClause_5.current;
						afterParserOrEnumRuleCall();
					}
				)?
				{
					newCompositeNode(grammarAccess.getExportDeclarationImplAccess().getSemiParserRuleCall_1_1_2());
				}
				ruleSemi
				{
					afterParserOrEnumRuleCall();
				}
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getExportDeclarationImplAccess().getExportedElementExportableElementParserRuleCall_1_2_0());
					}
					lv_exportedElement_7_0=ruleExportableElement
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getExportDeclarationImplRule());
						}
						set(
							$current,
							"exportedElement",
							lv_exportedElement_7_0,
							"org.eclipse.n4js.N4JS.ExportableElement");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					(
						lv_defaultExport_8_0=Default
						{
							newLeafNode(lv_defaultExport_8_0, grammarAccess.getExportDeclarationImplAccess().getDefaultExportDefaultKeyword_1_3_0_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getExportDeclarationImplRule());
							}
							setWithLastConsumed($current, "defaultExport", true, "default");
						}
					)
				)
				(
					(
						(CommercialAt | Private | Project | Protected | Public | External | Abstract | Static | Const | Class | Interface | Enum | Async | Function | Var | Let)=>
						(
							{
								newCompositeNode(grammarAccess.getExportDeclarationImplAccess().getExportedElementExportableElementParserRuleCall_1_3_1_0_0());
							}
							lv_exportedElement_9_0=ruleExportableElement
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getExportDeclarationImplRule());
								}
								set(
									$current,
									"exportedElement",
									lv_exportedElement_9_0,
									"org.eclipse.n4js.N4JS.ExportableElement");
								afterParserOrEnumRuleCall();
							}
						)
					)
					    |
					(
						(
							(
								{
									newCompositeNode(grammarAccess.getExportDeclarationImplAccess().getDefaultExportedExpressionAssignmentExpressionParserRuleCall_1_3_1_1_0_0());
								}
								lv_defaultExportedExpression_10_0=norm1_AssignmentExpression
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getExportDeclarationImplRule());
									}
									set(
										$current,
										"defaultExportedExpression",
										lv_defaultExportedExpression_10_0,
										"org.eclipse.n4js.N4JS.AssignmentExpression");
									afterParserOrEnumRuleCall();
								}
							)
						)
						{
							newCompositeNode(grammarAccess.getExportDeclarationImplAccess().getSemiParserRuleCall_1_3_1_1_1());
						}
						ruleSemi
						{
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)
	)
;


// Rule ExportFromClause
ruleExportFromClause[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=From
		{
			newLeafNode(otherlv_0, grammarAccess.getExportFromClauseAccess().getFromKeyword_0());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getExportFromClauseRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getExportFromClauseAccess().getReexportedFromTModuleCrossReference_1_0());
				}
				ruleModuleSpecifier
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule ExportClause
ruleExportClause[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LeftCurlyBracket
		{
			newLeafNode(otherlv_0, grammarAccess.getExportClauseAccess().getLeftCurlyBracketKeyword_0());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getExportClauseAccess().getNamedExportsExportSpecifierParserRuleCall_1_0_0());
					}
					lv_namedExports_1_0=ruleExportSpecifier
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getExportClauseRule());
						}
						add(
							$current,
							"namedExports",
							lv_namedExports_1_0,
							"org.eclipse.n4js.N4JS.ExportSpecifier");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_2=Comma
				{
					newLeafNode(otherlv_2, grammarAccess.getExportClauseAccess().getCommaKeyword_1_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getExportClauseAccess().getNamedExportsExportSpecifierParserRuleCall_1_1_1_0());
						}
						lv_namedExports_3_0=ruleExportSpecifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getExportClauseRule());
							}
							add(
								$current,
								"namedExports",
								lv_namedExports_3_0,
								"org.eclipse.n4js.N4JS.ExportSpecifier");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
			(
				otherlv_4=Comma
				{
					newLeafNode(otherlv_4, grammarAccess.getExportClauseAccess().getCommaKeyword_1_2());
				}
			)?
		)?
		otherlv_5=RightCurlyBracket
		{
			newLeafNode(otherlv_5, grammarAccess.getExportClauseAccess().getRightCurlyBracketKeyword_2());
		}
	)
;

// Entry rule entryRuleExportSpecifier
entryRuleExportSpecifier returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getExportSpecifierRule()); }
	iv_ruleExportSpecifier=ruleExportSpecifier
	{ $current=$iv_ruleExportSpecifier.current; }
	EOF;

// Rule ExportSpecifier
ruleExportSpecifier returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getExportSpecifierAccess().getElementIdentifierRefParserRuleCall_0_0());
				}
				lv_element_0_0=ruleIdentifierRef
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getExportSpecifierRule());
					}
					set(
						$current,
						"element",
						lv_element_0_0,
						"org.eclipse.n4js.N4JS.IdentifierRef");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_1=As
			{
				newLeafNode(otherlv_1, grammarAccess.getExportSpecifierAccess().getAsKeyword_1_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getExportSpecifierAccess().getAliasIdentifierNameParserRuleCall_1_1_0());
					}
					lv_alias_2_0=ruleIdentifierName
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getExportSpecifierRule());
						}
						set(
							$current,
							"alias",
							lv_alias_2_0,
							"org.eclipse.n4js.ts.TypeExpressions.IdentifierName");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;

// Entry rule entryRuleExportableElement
entryRuleExportableElement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getExportableElementRule()); }
	iv_ruleExportableElement=ruleExportableElement
	{ $current=$iv_ruleExportableElement.current; }
	EOF;

// Rule ExportableElement
ruleExportableElement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				CommercialAt
				(
					This
					    |
					Target
					    |
					RULE_IDENTIFIER
				)
			)
			)=>
			{
				newCompositeNode(grammarAccess.getExportableElementAccess().getAnnotatedExportableElementParserRuleCall_0());
			}
			this_AnnotatedExportableElement_0=ruleAnnotatedExportableElement
			{
				$current = $this_AnnotatedExportableElement_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
					(
						ruleN4Modifier
					)
				)*
				Class
				(
					(
						ruleTypingStrategyDefSiteOperator
					)
				)?
				(
					(
						ruleBindingIdentifier
					)
				)?
				(
					ruleVersionDeclaration[null]
				)?
			)
			)=>
			{
				newCompositeNode(grammarAccess.getExportableElementAccess().getN4ClassDeclarationParserRuleCall_1());
			}
			this_N4ClassDeclaration_1=ruleN4ClassDeclaration
			{
				$current = $this_N4ClassDeclaration_1.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
					(
						ruleN4Modifier
					)
				)*
				Interface
				(
					(
						ruleTypingStrategyDefSiteOperator
					)
				)?
				(
					(
						ruleBindingIdentifier
					)
				)?
				(
					ruleVersionDeclaration[null]
				)?
			)
			)=>
			{
				newCompositeNode(grammarAccess.getExportableElementAccess().getN4InterfaceDeclarationParserRuleCall_2());
			}
			this_N4InterfaceDeclaration_2=ruleN4InterfaceDeclaration
			{
				$current = $this_N4InterfaceDeclaration_2.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				(
					(
						ruleN4Modifier
					)
				)*
				Enum
				(
					(
						ruleBindingIdentifier
					)
				)?
				(
					ruleVersionDeclaration[null]
				)?
			)
			)=>
			{
				newCompositeNode(grammarAccess.getExportableElementAccess().getN4EnumDeclarationParserRuleCall_3());
			}
			this_N4EnumDeclaration_3=ruleN4EnumDeclaration
			{
				$current = $this_N4EnumDeclaration_3.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				(
					(
						ruleN4Modifier
					)
				)*
				ruleAsyncNoTrailingLineBreak[null]
				Function
			)
			)=>
			{
				newCompositeNode(grammarAccess.getExportableElementAccess().getFunctionDeclarationParserRuleCall_4());
			}
			this_FunctionDeclaration_4=ruleFunctionDeclaration
			{
				$current = $this_FunctionDeclaration_4.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getExportableElementAccess().getExportedVariableStatementParserRuleCall_5());
		}
		this_ExportedVariableStatement_5=ruleExportedVariableStatement
		{
			$current = $this_ExportedVariableStatement_5.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleAnnotatedExportableElement
entryRuleAnnotatedExportableElement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAnnotatedExportableElementRule()); }
	iv_ruleAnnotatedExportableElement=ruleAnnotatedExportableElement
	{ $current=$iv_ruleAnnotatedExportableElement.current; }
	EOF;

// Rule AnnotatedExportableElement
ruleAnnotatedExportableElement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				CommercialAt
				(
					This
					    |
					Target
					    |
					RULE_IDENTIFIER
				)
			)
			)=>
			{
				newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getAnnotationListParserRuleCall_0());
			}
			this_AnnotationList_0=ruleAnnotationList
			{
				$current = $this_AnnotationList_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		(
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getAnnotatedExportableElementAccess().getFunctionDeclarationAnnotationListAction_1_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getDeclaredModifiersN4ModifierEnumRuleCall_1_0_1_0());
						}
						lv_declaredModifiers_2_0=ruleN4Modifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedExportableElementRule());
							}
							add(
								$current,
								"declaredModifiers",
								lv_declaredModifiers_2_0,
								"org.eclipse.n4js.N4JS.N4Modifier");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAnnotatedExportableElementRule());
					}
					newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getAsyncNoTrailingLineBreakParserRuleCall_1_0_2());
				}
				this_AsyncNoTrailingLineBreak_3=ruleAsyncNoTrailingLineBreak[$current]
				{
					$current = $this_AsyncNoTrailingLineBreak_3.current;
					afterParserOrEnumRuleCall();
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAnnotatedExportableElementRule());
					}
					newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getFunctionImplParserRuleCall_1_0_3());
				}
				this_FunctionImpl_4=ruleFunctionImpl[$current]
				{
					$current = $this_FunctionImpl_4.current;
					afterParserOrEnumRuleCall();
				}
			)
			    |
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getAnnotatedExportableElementAccess().getExportedVariableStatementAnnotationListAction_1_1_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getDeclaredModifiersN4ModifierEnumRuleCall_1_1_1_0());
						}
						lv_declaredModifiers_6_0=ruleN4Modifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedExportableElementRule());
							}
							add(
								$current,
								"declaredModifiers",
								lv_declaredModifiers_6_0,
								"org.eclipse.n4js.N4JS.N4Modifier");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getVarStmtKeywordVariableStatementKeywordEnumRuleCall_1_1_2_0());
						}
						lv_varStmtKeyword_7_0=ruleVariableStatementKeyword
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedExportableElementRule());
							}
							set(
								$current,
								"varStmtKeyword",
								lv_varStmtKeyword_7_0,
								"org.eclipse.n4js.N4JS.VariableStatementKeyword");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getVarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_1_1_3_0());
						}
						lv_varDeclsOrBindings_8_0=ruleExportedVariableDeclarationOrBinding
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedExportableElementRule());
							}
							add(
								$current,
								"varDeclsOrBindings",
								lv_varDeclsOrBindings_8_0,
								"org.eclipse.n4js.N4JS.ExportedVariableDeclarationOrBinding");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					otherlv_9=Comma
					{
						newLeafNode(otherlv_9, grammarAccess.getAnnotatedExportableElementAccess().getCommaKeyword_1_1_4_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getVarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_1_1_4_1_0());
							}
							lv_varDeclsOrBindings_10_0=ruleExportedVariableDeclarationOrBinding
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getAnnotatedExportableElementRule());
								}
								add(
									$current,
									"varDeclsOrBindings",
									lv_varDeclsOrBindings_10_0,
									"org.eclipse.n4js.N4JS.ExportedVariableDeclarationOrBinding");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)*
				{
					newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getSemiParserRuleCall_1_1_5());
				}
				ruleSemi
				{
					afterParserOrEnumRuleCall();
				}
			)
			    |
			(
				(
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAnnotatedExportableElementAccess().getN4ClassDeclarationAnnotationListAction_1_2_0_0_0(),
									$current);
							}
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getDeclaredModifiersN4ModifierEnumRuleCall_1_2_0_0_1_0());
								}
								lv_declaredModifiers_13_0=ruleN4Modifier
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedExportableElementRule());
									}
									add(
										$current,
										"declaredModifiers",
										lv_declaredModifiers_13_0,
										"org.eclipse.n4js.N4JS.N4Modifier");
									afterParserOrEnumRuleCall();
								}
							)
						)*
						otherlv_14=Class
						{
							newLeafNode(otherlv_14, grammarAccess.getAnnotatedExportableElementAccess().getClassKeyword_1_2_0_0_2());
						}
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_2_0_0_3_0());
								}
								lv_typingStrategy_15_0=ruleTypingStrategyDefSiteOperator
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedExportableElementRule());
									}
									set(
										$current,
										"typingStrategy",
										lv_typingStrategy_15_0,
										"org.eclipse.n4js.ts.TypeExpressions.TypingStrategyDefSiteOperator");
									afterParserOrEnumRuleCall();
								}
							)
						)?
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getNameBindingIdentifierParserRuleCall_1_2_0_0_4_0());
								}
								lv_name_16_0=ruleBindingIdentifier
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedExportableElementRule());
									}
									set(
										$current,
										"name",
										lv_name_16_0,
										"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
									afterParserOrEnumRuleCall();
								}
							)
						)
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getAnnotatedExportableElementRule());
								}
								newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getTypeVariablesParserRuleCall_1_2_0_0_5());
							}
							this_TypeVariables_17=ruleTypeVariables[$current]
							{
								$current = $this_TypeVariables_17.current;
								afterParserOrEnumRuleCall();
							}
						)?
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getAnnotatedExportableElementRule());
								}
								newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getClassExtendsImplementsParserRuleCall_1_2_0_0_6());
							}
							this_ClassExtendsImplements_18=ruleClassExtendsImplements[$current]
							{
								$current = $this_ClassExtendsImplements_18.current;
								afterParserOrEnumRuleCall();
							}
						)?
					)
					    |
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAnnotatedExportableElementAccess().getN4InterfaceDeclarationAnnotationListAction_1_2_0_1_0(),
									$current);
							}
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getDeclaredModifiersN4ModifierEnumRuleCall_1_2_0_1_1_0());
								}
								lv_declaredModifiers_20_0=ruleN4Modifier
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedExportableElementRule());
									}
									add(
										$current,
										"declaredModifiers",
										lv_declaredModifiers_20_0,
										"org.eclipse.n4js.N4JS.N4Modifier");
									afterParserOrEnumRuleCall();
								}
							)
						)*
						otherlv_21=Interface
						{
							newLeafNode(otherlv_21, grammarAccess.getAnnotatedExportableElementAccess().getInterfaceKeyword_1_2_0_1_2());
						}
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_1_2_0_1_3_0());
								}
								lv_typingStrategy_22_0=ruleTypingStrategyDefSiteOperator
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedExportableElementRule());
									}
									set(
										$current,
										"typingStrategy",
										lv_typingStrategy_22_0,
										"org.eclipse.n4js.ts.TypeExpressions.TypingStrategyDefSiteOperator");
									afterParserOrEnumRuleCall();
								}
							)
						)?
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getNameBindingIdentifierParserRuleCall_1_2_0_1_4_0());
								}
								lv_name_23_0=ruleBindingIdentifier
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedExportableElementRule());
									}
									set(
										$current,
										"name",
										lv_name_23_0,
										"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
									afterParserOrEnumRuleCall();
								}
							)
						)
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getAnnotatedExportableElementRule());
								}
								newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getTypeVariablesParserRuleCall_1_2_0_1_5());
							}
							this_TypeVariables_24=ruleTypeVariables[$current]
							{
								$current = $this_TypeVariables_24.current;
								afterParserOrEnumRuleCall();
							}
						)?
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getAnnotatedExportableElementRule());
								}
								newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getInterfaceExtendsListParserRuleCall_1_2_0_1_6());
							}
							this_InterfaceExtendsList_25=ruleInterfaceExtendsList[$current]
							{
								$current = $this_InterfaceExtendsList_25.current;
								afterParserOrEnumRuleCall();
							}
						)?
					)
				)
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAnnotatedExportableElementRule());
					}
					newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getMembersParserRuleCall_1_2_1());
				}
				this_Members_26=ruleMembers[$current]
				{
					$current = $this_Members_26.current;
					afterParserOrEnumRuleCall();
				}
			)
			    |
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getAnnotatedExportableElementAccess().getN4EnumDeclarationAnnotationListAction_1_3_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getDeclaredModifiersN4ModifierEnumRuleCall_1_3_1_0());
						}
						lv_declaredModifiers_28_0=ruleN4Modifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedExportableElementRule());
							}
							add(
								$current,
								"declaredModifiers",
								lv_declaredModifiers_28_0,
								"org.eclipse.n4js.N4JS.N4Modifier");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				otherlv_29=Enum
				{
					newLeafNode(otherlv_29, grammarAccess.getAnnotatedExportableElementAccess().getEnumKeyword_1_3_2());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getNameBindingIdentifierParserRuleCall_1_3_3_0());
						}
						lv_name_30_0=ruleBindingIdentifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedExportableElementRule());
							}
							set(
								$current,
								"name",
								lv_name_30_0,
								"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
							afterParserOrEnumRuleCall();
						}
					)
				)
				otherlv_31=LeftCurlyBracket
				{
					newLeafNode(otherlv_31, grammarAccess.getAnnotatedExportableElementAccess().getLeftCurlyBracketKeyword_1_3_4());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getLiteralsN4EnumLiteralParserRuleCall_1_3_5_0());
						}
						lv_literals_32_0=ruleN4EnumLiteral
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedExportableElementRule());
							}
							add(
								$current,
								"literals",
								lv_literals_32_0,
								"org.eclipse.n4js.N4JS.N4EnumLiteral");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					otherlv_33=Comma
					{
						newLeafNode(otherlv_33, grammarAccess.getAnnotatedExportableElementAccess().getCommaKeyword_1_3_6_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getAnnotatedExportableElementAccess().getLiteralsN4EnumLiteralParserRuleCall_1_3_6_1_0());
							}
							lv_literals_34_0=ruleN4EnumLiteral
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getAnnotatedExportableElementRule());
								}
								add(
									$current,
									"literals",
									lv_literals_34_0,
									"org.eclipse.n4js.N4JS.N4EnumLiteral");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)*
				otherlv_35=RightCurlyBracket
				{
					newLeafNode(otherlv_35, grammarAccess.getAnnotatedExportableElementAccess().getRightCurlyBracketKeyword_1_3_7());
				}
			)
		)
	)
;

// Entry rule entryRuleImportDeclaration
entryRuleImportDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getImportDeclarationRule()); }
	iv_ruleImportDeclaration=ruleImportDeclaration
	{ $current=$iv_ruleImportDeclaration.current; }
	EOF;

// Rule ImportDeclaration
ruleImportDeclaration returns [EObject current=null]
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
					grammarAccess.getImportDeclarationAccess().getImportDeclarationAction_0(),
					$current);
			}
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getImportDeclarationRule());
			}
			newCompositeNode(grammarAccess.getImportDeclarationAccess().getImportDeclarationImplParserRuleCall_1());
		}
		this_ImportDeclarationImpl_1=ruleImportDeclarationImpl[$current]
		{
			$current = $this_ImportDeclarationImpl_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule ImportDeclarationImpl
ruleImportDeclarationImpl[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Import
		{
			newLeafNode(otherlv_0, grammarAccess.getImportDeclarationImplAccess().getImportKeyword_0());
		}
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getImportDeclarationImplRule());
				}
				newCompositeNode(grammarAccess.getImportDeclarationImplAccess().getImportClauseParserRuleCall_1_0());
			}
			this_ImportClause_1=ruleImportClause[$current]
			{
				$current = $this_ImportClause_1.current;
				afterParserOrEnumRuleCall();
			}
			(
				(
					lv_importFrom_2_0=From
					{
						newLeafNode(lv_importFrom_2_0, grammarAccess.getImportDeclarationImplAccess().getImportFromFromKeyword_1_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getImportDeclarationImplRule());
						}
						setWithLastConsumed($current, "importFrom", true, "from");
					}
				)
			)
		)?
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getImportDeclarationImplRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getImportDeclarationImplAccess().getModuleTModuleCrossReference_2_0());
				}
				ruleModuleSpecifier
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
		{
			newCompositeNode(grammarAccess.getImportDeclarationImplAccess().getSemiParserRuleCall_3());
		}
		ruleSemi
		{
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule ImportClause
ruleImportClause[EObject in_current]  returns [EObject current=in_current]
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
						newCompositeNode(grammarAccess.getImportClauseAccess().getImportSpecifiersDefaultImportSpecifierParserRuleCall_0_0_0());
					}
					lv_importSpecifiers_0_0=ruleDefaultImportSpecifier
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getImportClauseRule());
						}
						add(
							$current,
							"importSpecifiers",
							lv_importSpecifiers_0_0,
							"org.eclipse.n4js.N4JS.DefaultImportSpecifier");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_1=Comma
				{
					newLeafNode(otherlv_1, grammarAccess.getImportClauseAccess().getCommaKeyword_0_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getImportClauseRule());
					}
					newCompositeNode(grammarAccess.getImportClauseAccess().getImportSpecifiersExceptDefaultParserRuleCall_0_1_1());
				}
				this_ImportSpecifiersExceptDefault_2=ruleImportSpecifiersExceptDefault[$current]
				{
					$current = $this_ImportSpecifiersExceptDefault_2.current;
					afterParserOrEnumRuleCall();
				}
			)?
		)
		    |
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getImportClauseRule());
			}
			newCompositeNode(grammarAccess.getImportClauseAccess().getImportSpecifiersExceptDefaultParserRuleCall_1());
		}
		this_ImportSpecifiersExceptDefault_3=ruleImportSpecifiersExceptDefault[$current]
		{
			$current = $this_ImportSpecifiersExceptDefault_3.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule ImportSpecifiersExceptDefault
ruleImportSpecifiersExceptDefault[EObject in_current]  returns [EObject current=in_current]
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
					newCompositeNode(grammarAccess.getImportSpecifiersExceptDefaultAccess().getImportSpecifiersNamespaceImportSpecifierParserRuleCall_0_0());
				}
				lv_importSpecifiers_0_0=ruleNamespaceImportSpecifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getImportSpecifiersExceptDefaultRule());
					}
					add(
						$current,
						"importSpecifiers",
						lv_importSpecifiers_0_0,
						"org.eclipse.n4js.N4JS.NamespaceImportSpecifier");
					afterParserOrEnumRuleCall();
				}
			)
		)
		    |
		(
			otherlv_1=LeftCurlyBracket
			{
				newLeafNode(otherlv_1, grammarAccess.getImportSpecifiersExceptDefaultAccess().getLeftCurlyBracketKeyword_1_0());
			}
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getImportSpecifiersExceptDefaultAccess().getImportSpecifiersNamedImportSpecifierParserRuleCall_1_1_0_0());
						}
						lv_importSpecifiers_2_0=ruleNamedImportSpecifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getImportSpecifiersExceptDefaultRule());
							}
							add(
								$current,
								"importSpecifiers",
								lv_importSpecifiers_2_0,
								"org.eclipse.n4js.N4JS.NamedImportSpecifier");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					otherlv_3=Comma
					{
						newLeafNode(otherlv_3, grammarAccess.getImportSpecifiersExceptDefaultAccess().getCommaKeyword_1_1_1_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getImportSpecifiersExceptDefaultAccess().getImportSpecifiersNamedImportSpecifierParserRuleCall_1_1_1_1_0());
							}
							lv_importSpecifiers_4_0=ruleNamedImportSpecifier
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getImportSpecifiersExceptDefaultRule());
								}
								add(
									$current,
									"importSpecifiers",
									lv_importSpecifiers_4_0,
									"org.eclipse.n4js.N4JS.NamedImportSpecifier");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)*
				(
					otherlv_5=Comma
					{
						newLeafNode(otherlv_5, grammarAccess.getImportSpecifiersExceptDefaultAccess().getCommaKeyword_1_1_2());
					}
				)?
			)?
			otherlv_6=RightCurlyBracket
			{
				newLeafNode(otherlv_6, grammarAccess.getImportSpecifiersExceptDefaultAccess().getRightCurlyBracketKeyword_1_2());
			}
		)
	)
;

// Entry rule entryRuleNamedImportSpecifier
entryRuleNamedImportSpecifier returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getNamedImportSpecifierRule()); }
	iv_ruleNamedImportSpecifier=ruleNamedImportSpecifier
	{ $current=$iv_ruleNamedImportSpecifier.current; }
	EOF;

// Rule NamedImportSpecifier
ruleNamedImportSpecifier returns [EObject current=null]
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
					if ($current==null) {
						$current = createModelElement(grammarAccess.getNamedImportSpecifierRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getNamedImportSpecifierAccess().getImportedElementTExportableElementCrossReference_0_0());
				}
				ruleBindingIdentifier
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
		    |
		(
			(
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getNamedImportSpecifierRule());
						}
					}
					{
						newCompositeNode(grammarAccess.getNamedImportSpecifierAccess().getImportedElementTExportableElementCrossReference_1_0_0());
					}
					ruleIdentifierName
					{
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_2=As
			{
				newLeafNode(otherlv_2, grammarAccess.getNamedImportSpecifierAccess().getAsKeyword_1_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getNamedImportSpecifierAccess().getAliasBindingIdentifierParserRuleCall_1_2_0());
					}
					lv_alias_3_0=ruleBindingIdentifier
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getNamedImportSpecifierRule());
						}
						set(
							$current,
							"alias",
							lv_alias_3_0,
							"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;

// Entry rule entryRuleDefaultImportSpecifier
entryRuleDefaultImportSpecifier returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getDefaultImportSpecifierRule()); }
	iv_ruleDefaultImportSpecifier=ruleDefaultImportSpecifier
	{ $current=$iv_ruleDefaultImportSpecifier.current; }
	EOF;

// Rule DefaultImportSpecifier
ruleDefaultImportSpecifier returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getDefaultImportSpecifierRule());
				}
			}
			{
				newCompositeNode(grammarAccess.getDefaultImportSpecifierAccess().getImportedElementTExportableElementCrossReference_0());
			}
			ruleBindingIdentifier
			{
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleNamespaceImportSpecifier
entryRuleNamespaceImportSpecifier returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getNamespaceImportSpecifierRule()); }
	iv_ruleNamespaceImportSpecifier=ruleNamespaceImportSpecifier
	{ $current=$iv_ruleNamespaceImportSpecifier.current; }
	EOF;

// Rule NamespaceImportSpecifier
ruleNamespaceImportSpecifier returns [EObject current=null]
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
					grammarAccess.getNamespaceImportSpecifierAccess().getNamespaceImportSpecifierAction_0(),
					$current);
			}
		)
		otherlv_1=Asterisk
		{
			newLeafNode(otherlv_1, grammarAccess.getNamespaceImportSpecifierAccess().getAsteriskKeyword_1());
		}
		otherlv_2=As
		{
			newLeafNode(otherlv_2, grammarAccess.getNamespaceImportSpecifierAccess().getAsKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getNamespaceImportSpecifierAccess().getAliasBindingIdentifierParserRuleCall_3_0());
				}
				lv_alias_3_0=ruleBindingIdentifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getNamespaceImportSpecifierRule());
					}
					set(
						$current,
						"alias",
						lv_alias_3_0,
						"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				lv_declaredDynamic_4_0=PlusSign
				{
					newLeafNode(lv_declaredDynamic_4_0, grammarAccess.getNamespaceImportSpecifierAccess().getDeclaredDynamicPlusSignKeyword_4_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getNamespaceImportSpecifierRule());
					}
					setWithLastConsumed($current, "declaredDynamic", true, "+");
				}
			)
		)?
	)
;

// Entry rule entryRuleModuleSpecifier
entryRuleModuleSpecifier returns [String current=null]:
	{ newCompositeNode(grammarAccess.getModuleSpecifierRule()); }
	iv_ruleModuleSpecifier=ruleModuleSpecifier
	{ $current=$iv_ruleModuleSpecifier.current.getText(); }
	EOF;

// Rule ModuleSpecifier
ruleModuleSpecifier returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	this_STRING_0=RULE_STRING
	{
		$current.merge(this_STRING_0);
	}
	{
		newLeafNode(this_STRING_0, grammarAccess.getModuleSpecifierAccess().getSTRINGTerminalRuleCall());
	}
;

// Entry rule entryRuleFunctionDeclaration
entryRuleFunctionDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getFunctionDeclarationRule()); }
	iv_ruleFunctionDeclaration=ruleFunctionDeclaration
	{ $current=$iv_ruleFunctionDeclaration.current; }
	EOF;

// Rule FunctionDeclaration
ruleFunctionDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				(
					(
						ruleN4Modifier
					)
				)*
				ruleAsyncNoTrailingLineBreak[null]
				Function
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getFunctionDeclarationAccess().getFunctionDeclarationAction_0_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getFunctionDeclarationAccess().getDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0());
						}
						lv_declaredModifiers_1_0=ruleN4Modifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getFunctionDeclarationRule());
							}
							add(
								$current,
								"declaredModifiers",
								lv_declaredModifiers_1_0,
								"org.eclipse.n4js.N4JS.N4Modifier");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getFunctionDeclarationRule());
					}
					newCompositeNode(grammarAccess.getFunctionDeclarationAccess().getAsyncNoTrailingLineBreakParserRuleCall_0_0_2());
				}
				this_AsyncNoTrailingLineBreak_2=ruleAsyncNoTrailingLineBreak[$current]
				{
					$current = $this_AsyncNoTrailingLineBreak_2.current;
					afterParserOrEnumRuleCall();
				}
				(
					(Function)=>
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getFunctionDeclarationRule());
						}
						newCompositeNode(grammarAccess.getFunctionDeclarationAccess().getFunctionImplParserRuleCall_0_0_3());
					}
					this_FunctionImpl_3=ruleFunctionImpl[$current]
					{
						$current = $this_FunctionImpl_3.current;
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		(
			(ruleSemi)=>
			{
				newCompositeNode(grammarAccess.getFunctionDeclarationAccess().getSemiParserRuleCall_1());
			}
			ruleSemi
			{
				afterParserOrEnumRuleCall();
			}
		)?
	)
;


// Rule FunctionDeclaration
norm1_FunctionDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				(
					(
						ruleN4Modifier
					)
				)*
				ruleAsyncNoTrailingLineBreak[null]
				Function
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getFunctionDeclarationAccess().getFunctionDeclarationAction_0_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getFunctionDeclarationAccess().getDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0());
						}
						lv_declaredModifiers_1_0=ruleN4Modifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getFunctionDeclarationRule());
							}
							add(
								$current,
								"declaredModifiers",
								lv_declaredModifiers_1_0,
								"org.eclipse.n4js.N4JS.N4Modifier");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getFunctionDeclarationRule());
					}
					newCompositeNode(grammarAccess.getFunctionDeclarationAccess().getAsyncNoTrailingLineBreakParserRuleCall_0_0_2());
				}
				this_AsyncNoTrailingLineBreak_2=ruleAsyncNoTrailingLineBreak[$current]
				{
					$current = $this_AsyncNoTrailingLineBreak_2.current;
					afterParserOrEnumRuleCall();
				}
				(
					(Function)=>
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getFunctionDeclarationRule());
						}
						newCompositeNode(grammarAccess.getFunctionDeclarationAccess().getFunctionImplParserRuleCall_0_0_3());
					}
					this_FunctionImpl_3=norm3_FunctionImpl[$current]
					{
						$current = $this_FunctionImpl_3.current;
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		(
			(ruleSemi)=>
			{
				newCompositeNode(grammarAccess.getFunctionDeclarationAccess().getSemiParserRuleCall_1());
			}
			ruleSemi
			{
				afterParserOrEnumRuleCall();
			}
		)?
	)
;


// Rule AsyncNoTrailingLineBreak
ruleAsyncNoTrailingLineBreak[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_declaredAsync_0_0=Async
				{
					newLeafNode(lv_declaredAsync_0_0, grammarAccess.getAsyncNoTrailingLineBreakAccess().getDeclaredAsyncAsyncKeyword_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAsyncNoTrailingLineBreakRule());
					}
					setWithLastConsumed($current, "declaredAsync", true, "async");
				}
			)
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getAsyncNoTrailingLineBreakRule());
			}
			newCompositeNode(grammarAccess.getAsyncNoTrailingLineBreakAccess().getNoLineTerminatorParserRuleCall_1());
		}
		this_NoLineTerminator_1=ruleNoLineTerminator[$current]
		{
			$current = $this_NoLineTerminator_1.current;
			afterParserOrEnumRuleCall();
		}
	)?
;


// Rule FunctionImpl
ruleFunctionImpl[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Function
		{
			newLeafNode(otherlv_0, grammarAccess.getFunctionImplAccess().getFunctionKeyword_0());
		}
		(
			(
				(
					(
						lv_generator_1_0=Asterisk
						{
							newLeafNode(lv_generator_1_0, grammarAccess.getFunctionImplAccess().getGeneratorAsteriskKeyword_1_0_0_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getFunctionImplRule());
							}
							setWithLastConsumed($current, "generator", true, "*");
						}
					)
				)
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getFunctionImplRule());
					}
					newCompositeNode(grammarAccess.getFunctionImplAccess().getFunctionHeaderParserRuleCall_1_0_1());
				}
				this_FunctionHeader_2=norm2_FunctionHeader[$current]
				{
					$current = $this_FunctionHeader_2.current;
					afterParserOrEnumRuleCall();
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getFunctionImplRule());
					}
					newCompositeNode(grammarAccess.getFunctionImplAccess().getFunctionBodyParserRuleCall_1_0_2());
				}
				this_FunctionBody_3=norm1_FunctionBody[$current]
				{
					$current = $this_FunctionBody_3.current;
					afterParserOrEnumRuleCall();
				}
			)
			    |
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getFunctionImplRule());
					}
					newCompositeNode(grammarAccess.getFunctionImplAccess().getFunctionHeaderParserRuleCall_1_1_0());
				}
				this_FunctionHeader_4=ruleFunctionHeader[$current]
				{
					$current = $this_FunctionHeader_4.current;
					afterParserOrEnumRuleCall();
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getFunctionImplRule());
					}
					newCompositeNode(grammarAccess.getFunctionImplAccess().getFunctionBodyParserRuleCall_1_1_1());
				}
				this_FunctionBody_5=ruleFunctionBody[$current]
				{
					$current = $this_FunctionBody_5.current;
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule FunctionImpl
norm3_FunctionImpl[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Function
		{
			newLeafNode(otherlv_0, grammarAccess.getFunctionImplAccess().getFunctionKeyword_0());
		}
		(
			(
				(
					(
						lv_generator_1_0=Asterisk
						{
							newLeafNode(lv_generator_1_0, grammarAccess.getFunctionImplAccess().getGeneratorAsteriskKeyword_1_0_0_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getFunctionImplRule());
							}
							setWithLastConsumed($current, "generator", true, "*");
						}
					)
				)
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getFunctionImplRule());
					}
					newCompositeNode(grammarAccess.getFunctionImplAccess().getFunctionHeaderParserRuleCall_1_0_1());
				}
				this_FunctionHeader_2=norm3_FunctionHeader[$current]
				{
					$current = $this_FunctionHeader_2.current;
					afterParserOrEnumRuleCall();
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getFunctionImplRule());
					}
					newCompositeNode(grammarAccess.getFunctionImplAccess().getFunctionBodyParserRuleCall_1_0_2());
				}
				this_FunctionBody_3=norm1_FunctionBody[$current]
				{
					$current = $this_FunctionBody_3.current;
					afterParserOrEnumRuleCall();
				}
			)
			    |
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getFunctionImplRule());
					}
					newCompositeNode(grammarAccess.getFunctionImplAccess().getFunctionHeaderParserRuleCall_1_1_0());
				}
				this_FunctionHeader_4=norm1_FunctionHeader[$current]
				{
					$current = $this_FunctionHeader_4.current;
					afterParserOrEnumRuleCall();
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getFunctionImplRule());
					}
					newCompositeNode(grammarAccess.getFunctionImplAccess().getFunctionBodyParserRuleCall_1_1_1());
				}
				this_FunctionBody_5=ruleFunctionBody[$current]
				{
					$current = $this_FunctionBody_5.current;
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule FunctionImpl
norm6_FunctionImpl[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Function
		{
			newLeafNode(otherlv_0, grammarAccess.getFunctionImplAccess().getFunctionKeyword_0());
		}
		(
			(
				(
					(
						lv_generator_1_0=Asterisk
						{
							newLeafNode(lv_generator_1_0, grammarAccess.getFunctionImplAccess().getGeneratorAsteriskKeyword_1_0_0_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getFunctionImplRule());
							}
							setWithLastConsumed($current, "generator", true, "*");
						}
					)
				)
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getFunctionImplRule());
					}
					newCompositeNode(grammarAccess.getFunctionImplAccess().getFunctionHeaderParserRuleCall_1_0_1());
				}
				this_FunctionHeader_2=norm3_FunctionHeader[$current]
				{
					$current = $this_FunctionHeader_2.current;
					afterParserOrEnumRuleCall();
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getFunctionImplRule());
					}
					newCompositeNode(grammarAccess.getFunctionImplAccess().getFunctionBodyParserRuleCall_1_0_2());
				}
				this_FunctionBody_3=norm3_FunctionBody[$current]
				{
					$current = $this_FunctionBody_3.current;
					afterParserOrEnumRuleCall();
				}
			)
			    |
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getFunctionImplRule());
					}
					newCompositeNode(grammarAccess.getFunctionImplAccess().getFunctionHeaderParserRuleCall_1_1_0());
				}
				this_FunctionHeader_4=ruleFunctionHeader[$current]
				{
					$current = $this_FunctionHeader_4.current;
					afterParserOrEnumRuleCall();
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getFunctionImplRule());
					}
					newCompositeNode(grammarAccess.getFunctionImplAccess().getFunctionBodyParserRuleCall_1_1_1());
				}
				this_FunctionBody_5=norm2_FunctionBody[$current]
				{
					$current = $this_FunctionBody_5.current;
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule FunctionHeader
ruleFunctionHeader[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getFunctionHeaderRule());
				}
				newCompositeNode(grammarAccess.getFunctionHeaderAccess().getTypeVariablesParserRuleCall_0());
			}
			this_TypeVariables_0=ruleTypeVariables[$current]
			{
				$current = $this_TypeVariables_0.current;
				afterParserOrEnumRuleCall();
			}
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getFunctionHeaderAccess().getNameBindingIdentifierParserRuleCall_1_0());
				}
				lv_name_1_0=ruleBindingIdentifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getFunctionHeaderRule());
					}
					set(
						$current,
						"name",
						lv_name_1_0,
						"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getFunctionHeaderRule());
				}
				newCompositeNode(grammarAccess.getFunctionHeaderAccess().getVersionDeclarationParserRuleCall_2());
			}
			this_VersionDeclaration_2=ruleVersionDeclaration[$current]
			{
				$current = $this_VersionDeclaration_2.current;
				afterParserOrEnumRuleCall();
			}
		)?
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getFunctionHeaderRule());
			}
			newCompositeNode(grammarAccess.getFunctionHeaderAccess().getStrictFormalParametersParserRuleCall_3());
		}
		this_StrictFormalParameters_3=ruleStrictFormalParameters[$current]
		{
			$current = $this_StrictFormalParameters_3.current;
			afterParserOrEnumRuleCall();
		}
		(
			(Colon)=>
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getFunctionHeaderRule());
				}
				newCompositeNode(grammarAccess.getFunctionHeaderAccess().getColonSepReturnTypeRefParserRuleCall_4());
			}
			this_ColonSepReturnTypeRef_4=ruleColonSepReturnTypeRef[$current]
			{
				$current = $this_ColonSepReturnTypeRef_4.current;
				afterParserOrEnumRuleCall();
			}
		)?
	)
;


// Rule FunctionHeader
norm1_FunctionHeader[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getFunctionHeaderRule());
				}
				newCompositeNode(grammarAccess.getFunctionHeaderAccess().getTypeVariablesParserRuleCall_0());
			}
			this_TypeVariables_0=ruleTypeVariables[$current]
			{
				$current = $this_TypeVariables_0.current;
				afterParserOrEnumRuleCall();
			}
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getFunctionHeaderAccess().getNameBindingIdentifierParserRuleCall_1_0());
				}
				lv_name_1_0=norm1_BindingIdentifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getFunctionHeaderRule());
					}
					set(
						$current,
						"name",
						lv_name_1_0,
						"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getFunctionHeaderRule());
				}
				newCompositeNode(grammarAccess.getFunctionHeaderAccess().getVersionDeclarationParserRuleCall_2());
			}
			this_VersionDeclaration_2=ruleVersionDeclaration[$current]
			{
				$current = $this_VersionDeclaration_2.current;
				afterParserOrEnumRuleCall();
			}
		)?
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getFunctionHeaderRule());
			}
			newCompositeNode(grammarAccess.getFunctionHeaderAccess().getStrictFormalParametersParserRuleCall_3());
		}
		this_StrictFormalParameters_3=ruleStrictFormalParameters[$current]
		{
			$current = $this_StrictFormalParameters_3.current;
			afterParserOrEnumRuleCall();
		}
		(
			(Colon)=>
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getFunctionHeaderRule());
				}
				newCompositeNode(grammarAccess.getFunctionHeaderAccess().getColonSepReturnTypeRefParserRuleCall_4());
			}
			this_ColonSepReturnTypeRef_4=ruleColonSepReturnTypeRef[$current]
			{
				$current = $this_ColonSepReturnTypeRef_4.current;
				afterParserOrEnumRuleCall();
			}
		)?
	)
;


// Rule FunctionHeader
norm2_FunctionHeader[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getFunctionHeaderRule());
				}
				newCompositeNode(grammarAccess.getFunctionHeaderAccess().getTypeVariablesParserRuleCall_0());
			}
			this_TypeVariables_0=ruleTypeVariables[$current]
			{
				$current = $this_TypeVariables_0.current;
				afterParserOrEnumRuleCall();
			}
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getFunctionHeaderAccess().getNameBindingIdentifierParserRuleCall_1_0());
				}
				lv_name_1_0=ruleBindingIdentifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getFunctionHeaderRule());
					}
					set(
						$current,
						"name",
						lv_name_1_0,
						"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getFunctionHeaderRule());
				}
				newCompositeNode(grammarAccess.getFunctionHeaderAccess().getVersionDeclarationParserRuleCall_2());
			}
			this_VersionDeclaration_2=ruleVersionDeclaration[$current]
			{
				$current = $this_VersionDeclaration_2.current;
				afterParserOrEnumRuleCall();
			}
		)?
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getFunctionHeaderRule());
			}
			newCompositeNode(grammarAccess.getFunctionHeaderAccess().getStrictFormalParametersParserRuleCall_3());
		}
		this_StrictFormalParameters_3=norm1_StrictFormalParameters[$current]
		{
			$current = $this_StrictFormalParameters_3.current;
			afterParserOrEnumRuleCall();
		}
		(
			(Colon)=>
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getFunctionHeaderRule());
				}
				newCompositeNode(grammarAccess.getFunctionHeaderAccess().getColonSepReturnTypeRefParserRuleCall_4());
			}
			this_ColonSepReturnTypeRef_4=ruleColonSepReturnTypeRef[$current]
			{
				$current = $this_ColonSepReturnTypeRef_4.current;
				afterParserOrEnumRuleCall();
			}
		)?
	)
;


// Rule FunctionHeader
norm3_FunctionHeader[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getFunctionHeaderRule());
				}
				newCompositeNode(grammarAccess.getFunctionHeaderAccess().getTypeVariablesParserRuleCall_0());
			}
			this_TypeVariables_0=ruleTypeVariables[$current]
			{
				$current = $this_TypeVariables_0.current;
				afterParserOrEnumRuleCall();
			}
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getFunctionHeaderAccess().getNameBindingIdentifierParserRuleCall_1_0());
				}
				lv_name_1_0=norm1_BindingIdentifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getFunctionHeaderRule());
					}
					set(
						$current,
						"name",
						lv_name_1_0,
						"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getFunctionHeaderRule());
				}
				newCompositeNode(grammarAccess.getFunctionHeaderAccess().getVersionDeclarationParserRuleCall_2());
			}
			this_VersionDeclaration_2=ruleVersionDeclaration[$current]
			{
				$current = $this_VersionDeclaration_2.current;
				afterParserOrEnumRuleCall();
			}
		)?
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getFunctionHeaderRule());
			}
			newCompositeNode(grammarAccess.getFunctionHeaderAccess().getStrictFormalParametersParserRuleCall_3());
		}
		this_StrictFormalParameters_3=norm1_StrictFormalParameters[$current]
		{
			$current = $this_StrictFormalParameters_3.current;
			afterParserOrEnumRuleCall();
		}
		(
			(Colon)=>
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getFunctionHeaderRule());
				}
				newCompositeNode(grammarAccess.getFunctionHeaderAccess().getColonSepReturnTypeRefParserRuleCall_4());
			}
			this_ColonSepReturnTypeRef_4=ruleColonSepReturnTypeRef[$current]
			{
				$current = $this_ColonSepReturnTypeRef_4.current;
				afterParserOrEnumRuleCall();
			}
		)?
	)
;


// Rule FunctionBody
ruleFunctionBody[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		((
			(
			)
			LeftCurlyBracket
		)
		)=>
		(
			{
				newCompositeNode(grammarAccess.getFunctionBodyAccess().getBodyBlockParserRuleCall_1_0_0());
			}
			lv_body_0_0=ruleBlock
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getFunctionBodyRule());
				}
				set(
					$current,
					"body",
					lv_body_0_0,
					"org.eclipse.n4js.N4JS.Block");
				afterParserOrEnumRuleCall();
			}
		)
	)?
;


// Rule FunctionBody
norm1_FunctionBody[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		((
			(
			)
			LeftCurlyBracket
		)
		)=>
		(
			{
				newCompositeNode(grammarAccess.getFunctionBodyAccess().getBodyBlockParserRuleCall_1_0_0());
			}
			lv_body_0_0=norm1_Block
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getFunctionBodyRule());
				}
				set(
					$current,
					"body",
					lv_body_0_0,
					"org.eclipse.n4js.N4JS.Block");
				afterParserOrEnumRuleCall();
			}
		)
	)?
;


// Rule FunctionBody
norm2_FunctionBody[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		((
			(
			)
			LeftCurlyBracket
		)
		)=>
		(
			{
				newCompositeNode(grammarAccess.getFunctionBodyAccess().getBodyBlockParserRuleCall_0_0_0());
			}
			lv_body_0_0=ruleBlock
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getFunctionBodyRule());
				}
				set(
					$current,
					"body",
					lv_body_0_0,
					"org.eclipse.n4js.N4JS.Block");
				afterParserOrEnumRuleCall();
			}
		)
	)
;


// Rule FunctionBody
norm3_FunctionBody[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		((
			(
			)
			LeftCurlyBracket
		)
		)=>
		(
			{
				newCompositeNode(grammarAccess.getFunctionBodyAccess().getBodyBlockParserRuleCall_0_0_0());
			}
			lv_body_0_0=norm1_Block
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getFunctionBodyRule());
				}
				set(
					$current,
					"body",
					lv_body_0_0,
					"org.eclipse.n4js.N4JS.Block");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleAnnotatedFunctionDeclaration
entryRuleAnnotatedFunctionDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAnnotatedFunctionDeclarationRule()); }
	iv_ruleAnnotatedFunctionDeclaration=ruleAnnotatedFunctionDeclaration
	{ $current=$iv_ruleAnnotatedFunctionDeclaration.current; }
	EOF;

// Rule AnnotatedFunctionDeclaration
ruleAnnotatedFunctionDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				CommercialAt
				(
					This
					    |
					Target
					    |
					RULE_IDENTIFIER
				)
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getAnnotatedFunctionDeclarationAccess().getAnnotationListAnnotationListParserRuleCall_0_0());
				}
				lv_annotationList_0_0=ruleAnnotationList
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getAnnotatedFunctionDeclarationRule());
					}
					set(
						$current,
						"annotationList",
						lv_annotationList_0_0,
						"org.eclipse.n4js.N4JS.AnnotationList");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getAnnotatedFunctionDeclarationAccess().getDeclaredModifiersN4ModifierEnumRuleCall_1_0());
				}
				lv_declaredModifiers_1_0=ruleN4Modifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getAnnotatedFunctionDeclarationRule());
					}
					add(
						$current,
						"declaredModifiers",
						lv_declaredModifiers_1_0,
						"org.eclipse.n4js.N4JS.N4Modifier");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getAnnotatedFunctionDeclarationRule());
			}
			newCompositeNode(grammarAccess.getAnnotatedFunctionDeclarationAccess().getAsyncNoTrailingLineBreakParserRuleCall_2());
		}
		this_AsyncNoTrailingLineBreak_2=ruleAsyncNoTrailingLineBreak[$current]
		{
			$current = $this_AsyncNoTrailingLineBreak_2.current;
			afterParserOrEnumRuleCall();
		}
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getAnnotatedFunctionDeclarationRule());
			}
			newCompositeNode(grammarAccess.getAnnotatedFunctionDeclarationAccess().getFunctionImplParserRuleCall_3());
		}
		this_FunctionImpl_3=ruleFunctionImpl[$current]
		{
			$current = $this_FunctionImpl_3.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule AnnotatedFunctionDeclaration
norm1_AnnotatedFunctionDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				CommercialAt
				(
					This
					    |
					Target
					    |
					RULE_IDENTIFIER
				)
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getAnnotatedFunctionDeclarationAccess().getAnnotationListAnnotationListParserRuleCall_0_0());
				}
				lv_annotationList_0_0=ruleAnnotationList
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getAnnotatedFunctionDeclarationRule());
					}
					set(
						$current,
						"annotationList",
						lv_annotationList_0_0,
						"org.eclipse.n4js.N4JS.AnnotationList");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getAnnotatedFunctionDeclarationAccess().getDeclaredModifiersN4ModifierEnumRuleCall_1_0());
				}
				lv_declaredModifiers_1_0=ruleN4Modifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getAnnotatedFunctionDeclarationRule());
					}
					add(
						$current,
						"declaredModifiers",
						lv_declaredModifiers_1_0,
						"org.eclipse.n4js.N4JS.N4Modifier");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getAnnotatedFunctionDeclarationRule());
			}
			newCompositeNode(grammarAccess.getAnnotatedFunctionDeclarationAccess().getAsyncNoTrailingLineBreakParserRuleCall_2());
		}
		this_AsyncNoTrailingLineBreak_2=ruleAsyncNoTrailingLineBreak[$current]
		{
			$current = $this_AsyncNoTrailingLineBreak_2.current;
			afterParserOrEnumRuleCall();
		}
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getAnnotatedFunctionDeclarationRule());
			}
			newCompositeNode(grammarAccess.getAnnotatedFunctionDeclarationAccess().getFunctionImplParserRuleCall_3());
		}
		this_FunctionImpl_3=norm3_FunctionImpl[$current]
		{
			$current = $this_FunctionImpl_3.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleFunctionExpression
entryRuleFunctionExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getFunctionExpressionRule()); }
	iv_ruleFunctionExpression=ruleFunctionExpression
	{ $current=$iv_ruleFunctionExpression.current; }
	EOF;

// Rule FunctionExpression
ruleFunctionExpression returns [EObject current=null]
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
					grammarAccess.getFunctionExpressionAccess().getFunctionExpressionAction_0(),
					$current);
			}
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getFunctionExpressionRule());
			}
			newCompositeNode(grammarAccess.getFunctionExpressionAccess().getFunctionImplParserRuleCall_1());
		}
		this_FunctionImpl_1=norm6_FunctionImpl[$current]
		{
			$current = $this_FunctionImpl_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleAsyncFunctionExpression
entryRuleAsyncFunctionExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAsyncFunctionExpressionRule()); }
	iv_ruleAsyncFunctionExpression=ruleAsyncFunctionExpression
	{ $current=$iv_ruleAsyncFunctionExpression.current; }
	EOF;

// Rule AsyncFunctionExpression
ruleAsyncFunctionExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
					(
						Async
					)
				)
				ruleNoLineTerminator[null]
				Function
			)
			)=>
			(
				(
					(
						lv_declaredAsync_0_0=Async
						{
							newLeafNode(lv_declaredAsync_0_0, grammarAccess.getAsyncFunctionExpressionAccess().getDeclaredAsyncAsyncKeyword_0_0_0_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getAsyncFunctionExpressionRule());
							}
							setWithLastConsumed($current, "declaredAsync", true, "async");
						}
					)
				)
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAsyncFunctionExpressionRule());
					}
					newCompositeNode(grammarAccess.getAsyncFunctionExpressionAccess().getNoLineTerminatorParserRuleCall_0_0_1());
				}
				this_NoLineTerminator_1=ruleNoLineTerminator[$current]
				{
					$current = $this_NoLineTerminator_1.current;
					afterParserOrEnumRuleCall();
				}
				otherlv_2=Function
				{
					newLeafNode(otherlv_2, grammarAccess.getAsyncFunctionExpressionAccess().getFunctionKeyword_0_0_2());
				}
			)
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getAsyncFunctionExpressionRule());
			}
			newCompositeNode(grammarAccess.getAsyncFunctionExpressionAccess().getFunctionHeaderParserRuleCall_1());
		}
		this_FunctionHeader_3=ruleFunctionHeader[$current]
		{
			$current = $this_FunctionHeader_3.current;
			afterParserOrEnumRuleCall();
		}
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getAsyncFunctionExpressionRule());
			}
			newCompositeNode(grammarAccess.getAsyncFunctionExpressionAccess().getFunctionBodyParserRuleCall_2());
		}
		this_FunctionBody_4=norm2_FunctionBody[$current]
		{
			$current = $this_FunctionBody_4.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleArrowExpression
entryRuleArrowExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getArrowExpressionRule()); }
	iv_ruleArrowExpression=ruleArrowExpression
	{ $current=$iv_ruleArrowExpression.current; }
	EOF;

// Rule ArrowExpression
ruleArrowExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
					(
						ruleStrictFormalParameters[null]
						(
							ruleColonSepReturnTypeRef[null]
						)?
					)
					    |
					(
						(
							((
								(
									(
										Async
									)
								)
								ruleNoLineTerminator[null]
								LeftParenthesis
							)
							)=>
							(
								(
									(
										Async
									)
								)
								ruleNoLineTerminator[null]
								(
									(LeftParenthesis)=>
									ruleStrictFormalParameters[null]
								)
							)
						)
						(
							ruleColonSepReturnTypeRef[null]
						)?
					)
					    |
					(
						(
							ruleBindingIdentifierAsFormalParameter
						)
					)
				)
				EqualsSignGreaterThanSign
			)
			)=>
			(
				(
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getArrowExpressionRule());
							}
							newCompositeNode(grammarAccess.getArrowExpressionAccess().getStrictFormalParametersParserRuleCall_0_0_0_0_0());
						}
						this_StrictFormalParameters_0=ruleStrictFormalParameters[$current]
						{
							$current = $this_StrictFormalParameters_0.current;
							afterParserOrEnumRuleCall();
						}
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getArrowExpressionRule());
								}
								newCompositeNode(grammarAccess.getArrowExpressionAccess().getColonSepReturnTypeRefParserRuleCall_0_0_0_0_1());
							}
							this_ColonSepReturnTypeRef_1=ruleColonSepReturnTypeRef[$current]
							{
								$current = $this_ColonSepReturnTypeRef_1.current;
								afterParserOrEnumRuleCall();
							}
						)?
					)
					    |
					(
						(
							((
								(
									(
										Async
									)
								)
								ruleNoLineTerminator[null]
								LeftParenthesis
							)
							)=>
							(
								(
									(
										lv_declaredAsync_2_0=Async
										{
											newLeafNode(lv_declaredAsync_2_0, grammarAccess.getArrowExpressionAccess().getDeclaredAsyncAsyncKeyword_0_0_0_1_0_0_0_0());
										}
										{
											if ($current==null) {
												$current = createModelElement(grammarAccess.getArrowExpressionRule());
											}
											setWithLastConsumed($current, "declaredAsync", true, "async");
										}
									)
								)
								{
									if ($current==null) {
										$current = createModelElement(grammarAccess.getArrowExpressionRule());
									}
									newCompositeNode(grammarAccess.getArrowExpressionAccess().getNoLineTerminatorParserRuleCall_0_0_0_1_0_0_1());
								}
								this_NoLineTerminator_3=ruleNoLineTerminator[$current]
								{
									$current = $this_NoLineTerminator_3.current;
									afterParserOrEnumRuleCall();
								}
								(
									(LeftParenthesis)=>
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getArrowExpressionRule());
										}
										newCompositeNode(grammarAccess.getArrowExpressionAccess().getStrictFormalParametersParserRuleCall_0_0_0_1_0_0_2());
									}
									this_StrictFormalParameters_4=ruleStrictFormalParameters[$current]
									{
										$current = $this_StrictFormalParameters_4.current;
										afterParserOrEnumRuleCall();
									}
								)
							)
						)
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getArrowExpressionRule());
								}
								newCompositeNode(grammarAccess.getArrowExpressionAccess().getColonSepReturnTypeRefParserRuleCall_0_0_0_1_1());
							}
							this_ColonSepReturnTypeRef_5=ruleColonSepReturnTypeRef[$current]
							{
								$current = $this_ColonSepReturnTypeRef_5.current;
								afterParserOrEnumRuleCall();
							}
						)?
					)
					    |
					(
						(
							{
								newCompositeNode(grammarAccess.getArrowExpressionAccess().getFparsBindingIdentifierAsFormalParameterParserRuleCall_0_0_0_2_0());
							}
							lv_fpars_6_0=ruleBindingIdentifierAsFormalParameter
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getArrowExpressionRule());
								}
								add(
									$current,
									"fpars",
									lv_fpars_6_0,
									"org.eclipse.n4js.N4JS.BindingIdentifierAsFormalParameter");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
				otherlv_7=EqualsSignGreaterThanSign
				{
					newLeafNode(otherlv_7, grammarAccess.getArrowExpressionAccess().getEqualsSignGreaterThanSignKeyword_0_0_1());
				}
			)
		)
		(
			(
				(
					(LeftCurlyBracket)=>
					(
						lv_hasBracesAroundBody_8_0=LeftCurlyBracket
						{
							newLeafNode(lv_hasBracesAroundBody_8_0, grammarAccess.getArrowExpressionAccess().getHasBracesAroundBodyLeftCurlyBracketKeyword_1_0_0_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getArrowExpressionRule());
							}
							setWithLastConsumed($current, "hasBracesAroundBody", true, "{");
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getArrowExpressionAccess().getBodyBlockMinusBracesParserRuleCall_1_0_1_0());
						}
						lv_body_9_0=ruleBlockMinusBraces
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getArrowExpressionRule());
							}
							set(
								$current,
								"body",
								lv_body_9_0,
								"org.eclipse.n4js.N4JS.BlockMinusBraces");
							afterParserOrEnumRuleCall();
						}
					)
				)
				otherlv_10=RightCurlyBracket
				{
					newLeafNode(otherlv_10, grammarAccess.getArrowExpressionAccess().getRightCurlyBracketKeyword_1_0_2());
				}
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getArrowExpressionAccess().getBodyExpressionDisguisedAsBlockParserRuleCall_1_1_0());
					}
					lv_body_11_0=ruleExpressionDisguisedAsBlock
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getArrowExpressionRule());
						}
						set(
							$current,
							"body",
							lv_body_11_0,
							"org.eclipse.n4js.N4JS.ExpressionDisguisedAsBlock");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;


// Rule ArrowExpression
norm1_ArrowExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
					(
						ruleStrictFormalParameters[null]
						(
							ruleColonSepReturnTypeRef[null]
						)?
					)
					    |
					(
						(
							((
								(
									(
										Async
									)
								)
								ruleNoLineTerminator[null]
								LeftParenthesis
							)
							)=>
							(
								(
									(
										Async
									)
								)
								ruleNoLineTerminator[null]
								(
									(LeftParenthesis)=>
									ruleStrictFormalParameters[null]
								)
							)
						)
						(
							ruleColonSepReturnTypeRef[null]
						)?
					)
					    |
					(
						(
							ruleBindingIdentifierAsFormalParameter
						)
					)
				)
				EqualsSignGreaterThanSign
			)
			)=>
			(
				(
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getArrowExpressionRule());
							}
							newCompositeNode(grammarAccess.getArrowExpressionAccess().getStrictFormalParametersParserRuleCall_0_0_0_0_0());
						}
						this_StrictFormalParameters_0=ruleStrictFormalParameters[$current]
						{
							$current = $this_StrictFormalParameters_0.current;
							afterParserOrEnumRuleCall();
						}
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getArrowExpressionRule());
								}
								newCompositeNode(grammarAccess.getArrowExpressionAccess().getColonSepReturnTypeRefParserRuleCall_0_0_0_0_1());
							}
							this_ColonSepReturnTypeRef_1=ruleColonSepReturnTypeRef[$current]
							{
								$current = $this_ColonSepReturnTypeRef_1.current;
								afterParserOrEnumRuleCall();
							}
						)?
					)
					    |
					(
						(
							((
								(
									(
										Async
									)
								)
								ruleNoLineTerminator[null]
								LeftParenthesis
							)
							)=>
							(
								(
									(
										lv_declaredAsync_2_0=Async
										{
											newLeafNode(lv_declaredAsync_2_0, grammarAccess.getArrowExpressionAccess().getDeclaredAsyncAsyncKeyword_0_0_0_1_0_0_0_0());
										}
										{
											if ($current==null) {
												$current = createModelElement(grammarAccess.getArrowExpressionRule());
											}
											setWithLastConsumed($current, "declaredAsync", true, "async");
										}
									)
								)
								{
									if ($current==null) {
										$current = createModelElement(grammarAccess.getArrowExpressionRule());
									}
									newCompositeNode(grammarAccess.getArrowExpressionAccess().getNoLineTerminatorParserRuleCall_0_0_0_1_0_0_1());
								}
								this_NoLineTerminator_3=ruleNoLineTerminator[$current]
								{
									$current = $this_NoLineTerminator_3.current;
									afterParserOrEnumRuleCall();
								}
								(
									(LeftParenthesis)=>
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getArrowExpressionRule());
										}
										newCompositeNode(grammarAccess.getArrowExpressionAccess().getStrictFormalParametersParserRuleCall_0_0_0_1_0_0_2());
									}
									this_StrictFormalParameters_4=ruleStrictFormalParameters[$current]
									{
										$current = $this_StrictFormalParameters_4.current;
										afterParserOrEnumRuleCall();
									}
								)
							)
						)
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getArrowExpressionRule());
								}
								newCompositeNode(grammarAccess.getArrowExpressionAccess().getColonSepReturnTypeRefParserRuleCall_0_0_0_1_1());
							}
							this_ColonSepReturnTypeRef_5=ruleColonSepReturnTypeRef[$current]
							{
								$current = $this_ColonSepReturnTypeRef_5.current;
								afterParserOrEnumRuleCall();
							}
						)?
					)
					    |
					(
						(
							{
								newCompositeNode(grammarAccess.getArrowExpressionAccess().getFparsBindingIdentifierAsFormalParameterParserRuleCall_0_0_0_2_0());
							}
							lv_fpars_6_0=ruleBindingIdentifierAsFormalParameter
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getArrowExpressionRule());
								}
								add(
									$current,
									"fpars",
									lv_fpars_6_0,
									"org.eclipse.n4js.N4JS.BindingIdentifierAsFormalParameter");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
				otherlv_7=EqualsSignGreaterThanSign
				{
					newLeafNode(otherlv_7, grammarAccess.getArrowExpressionAccess().getEqualsSignGreaterThanSignKeyword_0_0_1());
				}
			)
		)
		(
			(
				(
					(LeftCurlyBracket)=>
					(
						lv_hasBracesAroundBody_8_0=LeftCurlyBracket
						{
							newLeafNode(lv_hasBracesAroundBody_8_0, grammarAccess.getArrowExpressionAccess().getHasBracesAroundBodyLeftCurlyBracketKeyword_1_0_0_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getArrowExpressionRule());
							}
							setWithLastConsumed($current, "hasBracesAroundBody", true, "{");
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getArrowExpressionAccess().getBodyBlockMinusBracesParserRuleCall_1_0_1_0());
						}
						lv_body_9_0=ruleBlockMinusBraces
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getArrowExpressionRule());
							}
							set(
								$current,
								"body",
								lv_body_9_0,
								"org.eclipse.n4js.N4JS.BlockMinusBraces");
							afterParserOrEnumRuleCall();
						}
					)
				)
				otherlv_10=RightCurlyBracket
				{
					newLeafNode(otherlv_10, grammarAccess.getArrowExpressionAccess().getRightCurlyBracketKeyword_1_0_2());
				}
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getArrowExpressionAccess().getBodyExpressionDisguisedAsBlockParserRuleCall_1_1_0());
					}
					lv_body_11_0=norm1_ExpressionDisguisedAsBlock
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getArrowExpressionRule());
						}
						set(
							$current,
							"body",
							lv_body_11_0,
							"org.eclipse.n4js.N4JS.ExpressionDisguisedAsBlock");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;


// Rule ArrowExpression
norm2_ArrowExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
					(
						norm1_StrictFormalParameters[null]
						(
							ruleColonSepReturnTypeRef[null]
						)?
					)
					    |
					(
						(
							((
								(
									(
										Async
									)
								)
								ruleNoLineTerminator[null]
								LeftParenthesis
							)
							)=>
							(
								(
									(
										Async
									)
								)
								ruleNoLineTerminator[null]
								(
									(LeftParenthesis)=>
									norm1_StrictFormalParameters[null]
								)
							)
						)
						(
							ruleColonSepReturnTypeRef[null]
						)?
					)
					    |
					(
						(
							norm1_BindingIdentifierAsFormalParameter
						)
					)
				)
				EqualsSignGreaterThanSign
			)
			)=>
			(
				(
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getArrowExpressionRule());
							}
							newCompositeNode(grammarAccess.getArrowExpressionAccess().getStrictFormalParametersParserRuleCall_0_0_0_0_0());
						}
						this_StrictFormalParameters_0=norm1_StrictFormalParameters[$current]
						{
							$current = $this_StrictFormalParameters_0.current;
							afterParserOrEnumRuleCall();
						}
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getArrowExpressionRule());
								}
								newCompositeNode(grammarAccess.getArrowExpressionAccess().getColonSepReturnTypeRefParserRuleCall_0_0_0_0_1());
							}
							this_ColonSepReturnTypeRef_1=ruleColonSepReturnTypeRef[$current]
							{
								$current = $this_ColonSepReturnTypeRef_1.current;
								afterParserOrEnumRuleCall();
							}
						)?
					)
					    |
					(
						(
							((
								(
									(
										Async
									)
								)
								ruleNoLineTerminator[null]
								LeftParenthesis
							)
							)=>
							(
								(
									(
										lv_declaredAsync_2_0=Async
										{
											newLeafNode(lv_declaredAsync_2_0, grammarAccess.getArrowExpressionAccess().getDeclaredAsyncAsyncKeyword_0_0_0_1_0_0_0_0());
										}
										{
											if ($current==null) {
												$current = createModelElement(grammarAccess.getArrowExpressionRule());
											}
											setWithLastConsumed($current, "declaredAsync", true, "async");
										}
									)
								)
								{
									if ($current==null) {
										$current = createModelElement(grammarAccess.getArrowExpressionRule());
									}
									newCompositeNode(grammarAccess.getArrowExpressionAccess().getNoLineTerminatorParserRuleCall_0_0_0_1_0_0_1());
								}
								this_NoLineTerminator_3=ruleNoLineTerminator[$current]
								{
									$current = $this_NoLineTerminator_3.current;
									afterParserOrEnumRuleCall();
								}
								(
									(LeftParenthesis)=>
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getArrowExpressionRule());
										}
										newCompositeNode(grammarAccess.getArrowExpressionAccess().getStrictFormalParametersParserRuleCall_0_0_0_1_0_0_2());
									}
									this_StrictFormalParameters_4=norm1_StrictFormalParameters[$current]
									{
										$current = $this_StrictFormalParameters_4.current;
										afterParserOrEnumRuleCall();
									}
								)
							)
						)
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getArrowExpressionRule());
								}
								newCompositeNode(grammarAccess.getArrowExpressionAccess().getColonSepReturnTypeRefParserRuleCall_0_0_0_1_1());
							}
							this_ColonSepReturnTypeRef_5=ruleColonSepReturnTypeRef[$current]
							{
								$current = $this_ColonSepReturnTypeRef_5.current;
								afterParserOrEnumRuleCall();
							}
						)?
					)
					    |
					(
						(
							{
								newCompositeNode(grammarAccess.getArrowExpressionAccess().getFparsBindingIdentifierAsFormalParameterParserRuleCall_0_0_0_2_0());
							}
							lv_fpars_6_0=norm1_BindingIdentifierAsFormalParameter
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getArrowExpressionRule());
								}
								add(
									$current,
									"fpars",
									lv_fpars_6_0,
									"org.eclipse.n4js.N4JS.BindingIdentifierAsFormalParameter");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
				otherlv_7=EqualsSignGreaterThanSign
				{
					newLeafNode(otherlv_7, grammarAccess.getArrowExpressionAccess().getEqualsSignGreaterThanSignKeyword_0_0_1());
				}
			)
		)
		(
			(
				(
					(LeftCurlyBracket)=>
					(
						lv_hasBracesAroundBody_8_0=LeftCurlyBracket
						{
							newLeafNode(lv_hasBracesAroundBody_8_0, grammarAccess.getArrowExpressionAccess().getHasBracesAroundBodyLeftCurlyBracketKeyword_1_0_0_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getArrowExpressionRule());
							}
							setWithLastConsumed($current, "hasBracesAroundBody", true, "{");
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getArrowExpressionAccess().getBodyBlockMinusBracesParserRuleCall_1_0_1_0());
						}
						lv_body_9_0=norm1_BlockMinusBraces
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getArrowExpressionRule());
							}
							set(
								$current,
								"body",
								lv_body_9_0,
								"org.eclipse.n4js.N4JS.BlockMinusBraces");
							afterParserOrEnumRuleCall();
						}
					)
				)
				otherlv_10=RightCurlyBracket
				{
					newLeafNode(otherlv_10, grammarAccess.getArrowExpressionAccess().getRightCurlyBracketKeyword_1_0_2());
				}
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getArrowExpressionAccess().getBodyExpressionDisguisedAsBlockParserRuleCall_1_1_0());
					}
					lv_body_11_0=ruleExpressionDisguisedAsBlock
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getArrowExpressionRule());
						}
						set(
							$current,
							"body",
							lv_body_11_0,
							"org.eclipse.n4js.N4JS.ExpressionDisguisedAsBlock");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;


// Rule ArrowExpression
norm3_ArrowExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
					(
						norm1_StrictFormalParameters[null]
						(
							ruleColonSepReturnTypeRef[null]
						)?
					)
					    |
					(
						(
							((
								(
									(
										Async
									)
								)
								ruleNoLineTerminator[null]
								LeftParenthesis
							)
							)=>
							(
								(
									(
										Async
									)
								)
								ruleNoLineTerminator[null]
								(
									(LeftParenthesis)=>
									norm1_StrictFormalParameters[null]
								)
							)
						)
						(
							ruleColonSepReturnTypeRef[null]
						)?
					)
					    |
					(
						(
							norm1_BindingIdentifierAsFormalParameter
						)
					)
				)
				EqualsSignGreaterThanSign
			)
			)=>
			(
				(
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getArrowExpressionRule());
							}
							newCompositeNode(grammarAccess.getArrowExpressionAccess().getStrictFormalParametersParserRuleCall_0_0_0_0_0());
						}
						this_StrictFormalParameters_0=norm1_StrictFormalParameters[$current]
						{
							$current = $this_StrictFormalParameters_0.current;
							afterParserOrEnumRuleCall();
						}
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getArrowExpressionRule());
								}
								newCompositeNode(grammarAccess.getArrowExpressionAccess().getColonSepReturnTypeRefParserRuleCall_0_0_0_0_1());
							}
							this_ColonSepReturnTypeRef_1=ruleColonSepReturnTypeRef[$current]
							{
								$current = $this_ColonSepReturnTypeRef_1.current;
								afterParserOrEnumRuleCall();
							}
						)?
					)
					    |
					(
						(
							((
								(
									(
										Async
									)
								)
								ruleNoLineTerminator[null]
								LeftParenthesis
							)
							)=>
							(
								(
									(
										lv_declaredAsync_2_0=Async
										{
											newLeafNode(lv_declaredAsync_2_0, grammarAccess.getArrowExpressionAccess().getDeclaredAsyncAsyncKeyword_0_0_0_1_0_0_0_0());
										}
										{
											if ($current==null) {
												$current = createModelElement(grammarAccess.getArrowExpressionRule());
											}
											setWithLastConsumed($current, "declaredAsync", true, "async");
										}
									)
								)
								{
									if ($current==null) {
										$current = createModelElement(grammarAccess.getArrowExpressionRule());
									}
									newCompositeNode(grammarAccess.getArrowExpressionAccess().getNoLineTerminatorParserRuleCall_0_0_0_1_0_0_1());
								}
								this_NoLineTerminator_3=ruleNoLineTerminator[$current]
								{
									$current = $this_NoLineTerminator_3.current;
									afterParserOrEnumRuleCall();
								}
								(
									(LeftParenthesis)=>
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getArrowExpressionRule());
										}
										newCompositeNode(grammarAccess.getArrowExpressionAccess().getStrictFormalParametersParserRuleCall_0_0_0_1_0_0_2());
									}
									this_StrictFormalParameters_4=norm1_StrictFormalParameters[$current]
									{
										$current = $this_StrictFormalParameters_4.current;
										afterParserOrEnumRuleCall();
									}
								)
							)
						)
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getArrowExpressionRule());
								}
								newCompositeNode(grammarAccess.getArrowExpressionAccess().getColonSepReturnTypeRefParserRuleCall_0_0_0_1_1());
							}
							this_ColonSepReturnTypeRef_5=ruleColonSepReturnTypeRef[$current]
							{
								$current = $this_ColonSepReturnTypeRef_5.current;
								afterParserOrEnumRuleCall();
							}
						)?
					)
					    |
					(
						(
							{
								newCompositeNode(grammarAccess.getArrowExpressionAccess().getFparsBindingIdentifierAsFormalParameterParserRuleCall_0_0_0_2_0());
							}
							lv_fpars_6_0=norm1_BindingIdentifierAsFormalParameter
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getArrowExpressionRule());
								}
								add(
									$current,
									"fpars",
									lv_fpars_6_0,
									"org.eclipse.n4js.N4JS.BindingIdentifierAsFormalParameter");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
				otherlv_7=EqualsSignGreaterThanSign
				{
					newLeafNode(otherlv_7, grammarAccess.getArrowExpressionAccess().getEqualsSignGreaterThanSignKeyword_0_0_1());
				}
			)
		)
		(
			(
				(
					(LeftCurlyBracket)=>
					(
						lv_hasBracesAroundBody_8_0=LeftCurlyBracket
						{
							newLeafNode(lv_hasBracesAroundBody_8_0, grammarAccess.getArrowExpressionAccess().getHasBracesAroundBodyLeftCurlyBracketKeyword_1_0_0_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getArrowExpressionRule());
							}
							setWithLastConsumed($current, "hasBracesAroundBody", true, "{");
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getArrowExpressionAccess().getBodyBlockMinusBracesParserRuleCall_1_0_1_0());
						}
						lv_body_9_0=norm1_BlockMinusBraces
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getArrowExpressionRule());
							}
							set(
								$current,
								"body",
								lv_body_9_0,
								"org.eclipse.n4js.N4JS.BlockMinusBraces");
							afterParserOrEnumRuleCall();
						}
					)
				)
				otherlv_10=RightCurlyBracket
				{
					newLeafNode(otherlv_10, grammarAccess.getArrowExpressionAccess().getRightCurlyBracketKeyword_1_0_2());
				}
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getArrowExpressionAccess().getBodyExpressionDisguisedAsBlockParserRuleCall_1_1_0());
					}
					lv_body_11_0=norm1_ExpressionDisguisedAsBlock
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getArrowExpressionRule());
						}
						set(
							$current,
							"body",
							lv_body_11_0,
							"org.eclipse.n4js.N4JS.ExpressionDisguisedAsBlock");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;


// Rule StrictFormalParameters
ruleStrictFormalParameters[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LeftParenthesis
		{
			newLeafNode(otherlv_0, grammarAccess.getStrictFormalParametersAccess().getLeftParenthesisKeyword_0());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getStrictFormalParametersAccess().getFparsFormalParameterParserRuleCall_1_0_0());
					}
					lv_fpars_1_0=ruleFormalParameter
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getStrictFormalParametersRule());
						}
						add(
							$current,
							"fpars",
							lv_fpars_1_0,
							"org.eclipse.n4js.N4JS.FormalParameter");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_2=Comma
				{
					newLeafNode(otherlv_2, grammarAccess.getStrictFormalParametersAccess().getCommaKeyword_1_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getStrictFormalParametersAccess().getFparsFormalParameterParserRuleCall_1_1_1_0());
						}
						lv_fpars_3_0=ruleFormalParameter
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getStrictFormalParametersRule());
							}
							add(
								$current,
								"fpars",
								lv_fpars_3_0,
								"org.eclipse.n4js.N4JS.FormalParameter");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_4=RightParenthesis
		{
			newLeafNode(otherlv_4, grammarAccess.getStrictFormalParametersAccess().getRightParenthesisKeyword_2());
		}
	)
;


// Rule StrictFormalParameters
norm1_StrictFormalParameters[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LeftParenthesis
		{
			newLeafNode(otherlv_0, grammarAccess.getStrictFormalParametersAccess().getLeftParenthesisKeyword_0());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getStrictFormalParametersAccess().getFparsFormalParameterParserRuleCall_1_0_0());
					}
					lv_fpars_1_0=norm1_FormalParameter
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getStrictFormalParametersRule());
						}
						add(
							$current,
							"fpars",
							lv_fpars_1_0,
							"org.eclipse.n4js.N4JS.FormalParameter");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_2=Comma
				{
					newLeafNode(otherlv_2, grammarAccess.getStrictFormalParametersAccess().getCommaKeyword_1_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getStrictFormalParametersAccess().getFparsFormalParameterParserRuleCall_1_1_1_0());
						}
						lv_fpars_3_0=norm1_FormalParameter
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getStrictFormalParametersRule());
							}
							add(
								$current,
								"fpars",
								lv_fpars_3_0,
								"org.eclipse.n4js.N4JS.FormalParameter");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_4=RightParenthesis
		{
			newLeafNode(otherlv_4, grammarAccess.getStrictFormalParametersAccess().getRightParenthesisKeyword_2());
		}
	)
;

// Entry rule entryRuleBindingIdentifierAsFormalParameter
entryRuleBindingIdentifierAsFormalParameter returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getBindingIdentifierAsFormalParameterRule()); }
	iv_ruleBindingIdentifierAsFormalParameter=ruleBindingIdentifierAsFormalParameter
	{ $current=$iv_ruleBindingIdentifierAsFormalParameter.current; }
	EOF;

// Rule BindingIdentifierAsFormalParameter
ruleBindingIdentifierAsFormalParameter returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getBindingIdentifierAsFormalParameterAccess().getNameBindingIdentifierParserRuleCall_0());
			}
			lv_name_0_0=ruleBindingIdentifier
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getBindingIdentifierAsFormalParameterRule());
				}
				set(
					$current,
					"name",
					lv_name_0_0,
					"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
				afterParserOrEnumRuleCall();
			}
		)
	)
;


// Rule BindingIdentifierAsFormalParameter
norm1_BindingIdentifierAsFormalParameter returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getBindingIdentifierAsFormalParameterAccess().getNameBindingIdentifierParserRuleCall_0());
			}
			lv_name_0_0=norm1_BindingIdentifier
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getBindingIdentifierAsFormalParameterRule());
				}
				set(
					$current,
					"name",
					lv_name_0_0,
					"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleBlockMinusBraces
entryRuleBlockMinusBraces returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getBlockMinusBracesRule()); }
	iv_ruleBlockMinusBraces=ruleBlockMinusBraces
	{ $current=$iv_ruleBlockMinusBraces.current; }
	EOF;

// Rule BlockMinusBraces
ruleBlockMinusBraces returns [EObject current=null]
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
					grammarAccess.getBlockMinusBracesAccess().getBlockAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getBlockMinusBracesAccess().getStatementsStatementParserRuleCall_1_0());
				}
				lv_statements_1_0=ruleStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getBlockMinusBracesRule());
					}
					add(
						$current,
						"statements",
						lv_statements_1_0,
						"org.eclipse.n4js.N4JS.Statement");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;


// Rule BlockMinusBraces
norm1_BlockMinusBraces returns [EObject current=null]
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
					grammarAccess.getBlockMinusBracesAccess().getBlockAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getBlockMinusBracesAccess().getStatementsStatementParserRuleCall_1_0());
				}
				lv_statements_1_0=norm1_Statement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getBlockMinusBracesRule());
					}
					add(
						$current,
						"statements",
						lv_statements_1_0,
						"org.eclipse.n4js.N4JS.Statement");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;

// Entry rule entryRuleExpressionDisguisedAsBlock
entryRuleExpressionDisguisedAsBlock returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getExpressionDisguisedAsBlockRule()); }
	iv_ruleExpressionDisguisedAsBlock=ruleExpressionDisguisedAsBlock
	{ $current=$iv_ruleExpressionDisguisedAsBlock.current; }
	EOF;

// Rule ExpressionDisguisedAsBlock
ruleExpressionDisguisedAsBlock returns [EObject current=null]
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
					grammarAccess.getExpressionDisguisedAsBlockAccess().getBlockAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getExpressionDisguisedAsBlockAccess().getStatementsAssignmentExpressionStatementParserRuleCall_1_0());
				}
				lv_statements_1_0=ruleAssignmentExpressionStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getExpressionDisguisedAsBlockRule());
					}
					add(
						$current,
						"statements",
						lv_statements_1_0,
						"org.eclipse.n4js.N4JS.AssignmentExpressionStatement");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule ExpressionDisguisedAsBlock
norm1_ExpressionDisguisedAsBlock returns [EObject current=null]
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
					grammarAccess.getExpressionDisguisedAsBlockAccess().getBlockAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getExpressionDisguisedAsBlockAccess().getStatementsAssignmentExpressionStatementParserRuleCall_1_0());
				}
				lv_statements_1_0=norm1_AssignmentExpressionStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getExpressionDisguisedAsBlockRule());
					}
					add(
						$current,
						"statements",
						lv_statements_1_0,
						"org.eclipse.n4js.N4JS.AssignmentExpressionStatement");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleAssignmentExpressionStatement
entryRuleAssignmentExpressionStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAssignmentExpressionStatementRule()); }
	iv_ruleAssignmentExpressionStatement=ruleAssignmentExpressionStatement
	{ $current=$iv_ruleAssignmentExpressionStatement.current; }
	EOF;

// Rule AssignmentExpressionStatement
ruleAssignmentExpressionStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getAssignmentExpressionStatementAccess().getExpressionAssignmentExpressionParserRuleCall_0());
			}
			lv_expression_0_0=ruleAssignmentExpression
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getAssignmentExpressionStatementRule());
				}
				set(
					$current,
					"expression",
					lv_expression_0_0,
					"org.eclipse.n4js.N4JS.AssignmentExpression");
				afterParserOrEnumRuleCall();
			}
		)
	)
;


// Rule AssignmentExpressionStatement
norm1_AssignmentExpressionStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getAssignmentExpressionStatementAccess().getExpressionAssignmentExpressionParserRuleCall_0());
			}
			lv_expression_0_0=norm1_AssignmentExpression
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getAssignmentExpressionStatementRule());
				}
				set(
					$current,
					"expression",
					lv_expression_0_0,
					"org.eclipse.n4js.N4JS.AssignmentExpression");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleAnnotatedExpression
entryRuleAnnotatedExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAnnotatedExpressionRule()); }
	iv_ruleAnnotatedExpression=ruleAnnotatedExpression
	{ $current=$iv_ruleAnnotatedExpression.current; }
	EOF;

// Rule AnnotatedExpression
ruleAnnotatedExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getAnnotatedExpressionAccess().getExpressionAnnotationListParserRuleCall_0());
		}
		this_ExpressionAnnotationList_0=ruleExpressionAnnotationList
		{
			$current = $this_ExpressionAnnotationList_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getAnnotatedExpressionAccess().getN4ClassExpressionAnnotationListAction_1_0_0(),
							$current);
					}
				)
				otherlv_2=Class
				{
					newLeafNode(otherlv_2, grammarAccess.getAnnotatedExpressionAccess().getClassKeyword_1_0_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedExpressionAccess().getNameBindingIdentifierParserRuleCall_1_0_2_0());
						}
						lv_name_3_0=ruleBindingIdentifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedExpressionRule());
							}
							set(
								$current,
								"name",
								lv_name_3_0,
								"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
							afterParserOrEnumRuleCall();
						}
					)
				)?
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getAnnotatedExpressionRule());
						}
						newCompositeNode(grammarAccess.getAnnotatedExpressionAccess().getClassExtendsImplementsParserRuleCall_1_0_3());
					}
					this_ClassExtendsImplements_4=ruleClassExtendsImplements[$current]
					{
						$current = $this_ClassExtendsImplements_4.current;
						afterParserOrEnumRuleCall();
					}
				)?
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAnnotatedExpressionRule());
					}
					newCompositeNode(grammarAccess.getAnnotatedExpressionAccess().getMembersParserRuleCall_1_0_4());
				}
				this_Members_5=ruleMembers[$current]
				{
					$current = $this_Members_5.current;
					afterParserOrEnumRuleCall();
				}
			)
			    |
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getAnnotatedExpressionAccess().getFunctionExpressionAnnotationListAction_1_1_0(),
							$current);
					}
				)
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAnnotatedExpressionRule());
					}
					newCompositeNode(grammarAccess.getAnnotatedExpressionAccess().getAsyncNoTrailingLineBreakParserRuleCall_1_1_1());
				}
				this_AsyncNoTrailingLineBreak_7=ruleAsyncNoTrailingLineBreak[$current]
				{
					$current = $this_AsyncNoTrailingLineBreak_7.current;
					afterParserOrEnumRuleCall();
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAnnotatedExpressionRule());
					}
					newCompositeNode(grammarAccess.getAnnotatedExpressionAccess().getFunctionImplParserRuleCall_1_1_2());
				}
				this_FunctionImpl_8=norm6_FunctionImpl[$current]
				{
					$current = $this_FunctionImpl_8.current;
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule AnnotatedExpression
norm1_AnnotatedExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getAnnotatedExpressionAccess().getExpressionAnnotationListParserRuleCall_0());
		}
		this_ExpressionAnnotationList_0=ruleExpressionAnnotationList
		{
			$current = $this_ExpressionAnnotationList_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getAnnotatedExpressionAccess().getN4ClassExpressionAnnotationListAction_1_0_0(),
							$current);
					}
				)
				otherlv_2=Class
				{
					newLeafNode(otherlv_2, grammarAccess.getAnnotatedExpressionAccess().getClassKeyword_1_0_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedExpressionAccess().getNameBindingIdentifierParserRuleCall_1_0_2_0());
						}
						lv_name_3_0=norm1_BindingIdentifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedExpressionRule());
							}
							set(
								$current,
								"name",
								lv_name_3_0,
								"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
							afterParserOrEnumRuleCall();
						}
					)
				)?
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getAnnotatedExpressionRule());
						}
						newCompositeNode(grammarAccess.getAnnotatedExpressionAccess().getClassExtendsImplementsParserRuleCall_1_0_3());
					}
					this_ClassExtendsImplements_4=norm1_ClassExtendsImplements[$current]
					{
						$current = $this_ClassExtendsImplements_4.current;
						afterParserOrEnumRuleCall();
					}
				)?
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAnnotatedExpressionRule());
					}
					newCompositeNode(grammarAccess.getAnnotatedExpressionAccess().getMembersParserRuleCall_1_0_4());
				}
				this_Members_5=norm1_Members[$current]
				{
					$current = $this_Members_5.current;
					afterParserOrEnumRuleCall();
				}
			)
			    |
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getAnnotatedExpressionAccess().getFunctionExpressionAnnotationListAction_1_1_0(),
							$current);
					}
				)
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAnnotatedExpressionRule());
					}
					newCompositeNode(grammarAccess.getAnnotatedExpressionAccess().getAsyncNoTrailingLineBreakParserRuleCall_1_1_1());
				}
				this_AsyncNoTrailingLineBreak_7=ruleAsyncNoTrailingLineBreak[$current]
				{
					$current = $this_AsyncNoTrailingLineBreak_7.current;
					afterParserOrEnumRuleCall();
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAnnotatedExpressionRule());
					}
					newCompositeNode(grammarAccess.getAnnotatedExpressionAccess().getFunctionImplParserRuleCall_1_1_2());
				}
				this_FunctionImpl_8=norm6_FunctionImpl[$current]
				{
					$current = $this_FunctionImpl_8.current;
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleTypeVariable
entryRuleTypeVariable returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTypeVariableRule()); }
	iv_ruleTypeVariable=ruleTypeVariable
	{ $current=$iv_ruleTypeVariable.current; }
	EOF;

// Rule TypeVariable
ruleTypeVariable returns [EObject current=null]
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
					lv_declaredCovariant_0_0=Out
					{
						newLeafNode(lv_declaredCovariant_0_0, grammarAccess.getTypeVariableAccess().getDeclaredCovariantOutKeyword_0_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getTypeVariableRule());
						}
						setWithLastConsumed($current, "declaredCovariant", true, "out");
					}
				)
			)
			    |
			(
				(
					lv_declaredContravariant_1_0=In
					{
						newLeafNode(lv_declaredContravariant_1_0, grammarAccess.getTypeVariableAccess().getDeclaredContravariantInKeyword_0_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getTypeVariableRule());
						}
						setWithLastConsumed($current, "declaredContravariant", true, "in");
					}
				)
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getTypeVariableAccess().getNameIdentifierOrThisParserRuleCall_1_0());
				}
				lv_name_2_0=ruleIdentifierOrThis
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTypeVariableRule());
					}
					set(
						$current,
						"name",
						lv_name_2_0,
						"org.eclipse.n4js.N4JS.IdentifierOrThis");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_3=Extends
			{
				newLeafNode(otherlv_3, grammarAccess.getTypeVariableAccess().getExtendsKeyword_2_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getTypeVariableAccess().getDeclaredUpperBoundTypeRefParserRuleCall_2_1_0());
					}
					lv_declaredUpperBound_4_0=ruleTypeRef
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTypeVariableRule());
						}
						set(
							$current,
							"declaredUpperBound",
							lv_declaredUpperBound_4_0,
							"org.eclipse.n4js.ts.TypeExpressions.TypeRef");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;

// Entry rule entryRuleFormalParameter
entryRuleFormalParameter returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getFormalParameterRule()); }
	iv_ruleFormalParameter=ruleFormalParameter
	{ $current=$iv_ruleFormalParameter.current; }
	EOF;

// Rule FormalParameter
ruleFormalParameter returns [EObject current=null]
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
					grammarAccess.getFormalParameterAccess().getFormalParameterAction_0(),
					$current);
			}
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getFormalParameterRule());
			}
			newCompositeNode(grammarAccess.getFormalParameterAccess().getBindingElementFragmentParserRuleCall_1());
		}
		this_BindingElementFragment_1=ruleBindingElementFragment[$current]
		{
			$current = $this_BindingElementFragment_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule FormalParameter
norm1_FormalParameter returns [EObject current=null]
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
					grammarAccess.getFormalParameterAccess().getFormalParameterAction_0(),
					$current);
			}
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getFormalParameterRule());
			}
			newCompositeNode(grammarAccess.getFormalParameterAccess().getBindingElementFragmentParserRuleCall_1());
		}
		this_BindingElementFragment_1=norm1_BindingElementFragment[$current]
		{
			$current = $this_BindingElementFragment_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule BindingElementFragment
ruleBindingElementFragment[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				((
					ruleBindingPattern
				)
				)=>
				(
					{
						newCompositeNode(grammarAccess.getBindingElementFragmentAccess().getBindingPatternBindingPatternParserRuleCall_0_0_0());
					}
					lv_bindingPattern_0_0=ruleBindingPattern
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getBindingElementFragmentRule());
						}
						set(
							$current,
							"bindingPattern",
							lv_bindingPattern_0_0,
							"org.eclipse.n4js.N4JS.BindingPattern");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getBindingElementFragmentAccess().getAnnotationsAnnotationParserRuleCall_0_1_0_0());
						}
						lv_annotations_1_0=ruleAnnotation
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getBindingElementFragmentRule());
							}
							add(
								$current,
								"annotations",
								lv_annotations_1_0,
								"org.eclipse.n4js.N4JS.Annotation");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getBindingElementFragmentRule());
						}
						newCompositeNode(grammarAccess.getBindingElementFragmentAccess().getBogusTypeRefFragmentParserRuleCall_0_1_1());
					}
					this_BogusTypeRefFragment_2=ruleBogusTypeRefFragment[$current]
					{
						$current = $this_BogusTypeRefFragment_2.current;
						afterParserOrEnumRuleCall();
					}
				)?
				(
					(
						lv_variadic_3_0=FullStopFullStopFullStop
						{
							newLeafNode(lv_variadic_3_0, grammarAccess.getBindingElementFragmentAccess().getVariadicFullStopFullStopFullStopKeyword_0_1_2_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getBindingElementFragmentRule());
							}
							setWithLastConsumed($current, "variadic", true, "...");
						}
					)
				)?
				(
					(
						{
							newCompositeNode(grammarAccess.getBindingElementFragmentAccess().getNameBindingIdentifierParserRuleCall_0_1_3_0());
						}
						lv_name_4_0=ruleBindingIdentifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getBindingElementFragmentRule());
							}
							set(
								$current,
								"name",
								lv_name_4_0,
								"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getBindingElementFragmentRule());
						}
						newCompositeNode(grammarAccess.getBindingElementFragmentAccess().getColonSepDeclaredTypeRefParserRuleCall_0_1_4());
					}
					this_ColonSepDeclaredTypeRef_5=ruleColonSepDeclaredTypeRef[$current]
					{
						$current = $this_ColonSepDeclaredTypeRef_5.current;
						afterParserOrEnumRuleCall();
					}
				)?
			)
		)
		(
			(
				(
					lv_hasInitializerAssignment_6_0=EqualsSign
					{
						newLeafNode(lv_hasInitializerAssignment_6_0, grammarAccess.getBindingElementFragmentAccess().getHasInitializerAssignmentEqualsSignKeyword_1_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getBindingElementFragmentRule());
						}
						setWithLastConsumed($current, "hasInitializerAssignment", true, "=");
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getBindingElementFragmentAccess().getInitializerAssignmentExpressionParserRuleCall_1_1_0());
					}
					lv_initializer_7_0=norm1_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getBindingElementFragmentRule());
						}
						set(
							$current,
							"initializer",
							lv_initializer_7_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)?
		)?
	)
;


// Rule BindingElementFragment
norm1_BindingElementFragment[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				((
					norm1_BindingPattern
				)
				)=>
				(
					{
						newCompositeNode(grammarAccess.getBindingElementFragmentAccess().getBindingPatternBindingPatternParserRuleCall_0_0_0());
					}
					lv_bindingPattern_0_0=norm1_BindingPattern
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getBindingElementFragmentRule());
						}
						set(
							$current,
							"bindingPattern",
							lv_bindingPattern_0_0,
							"org.eclipse.n4js.N4JS.BindingPattern");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getBindingElementFragmentAccess().getAnnotationsAnnotationParserRuleCall_0_1_0_0());
						}
						lv_annotations_1_0=ruleAnnotation
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getBindingElementFragmentRule());
							}
							add(
								$current,
								"annotations",
								lv_annotations_1_0,
								"org.eclipse.n4js.N4JS.Annotation");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getBindingElementFragmentRule());
						}
						newCompositeNode(grammarAccess.getBindingElementFragmentAccess().getBogusTypeRefFragmentParserRuleCall_0_1_1());
					}
					this_BogusTypeRefFragment_2=ruleBogusTypeRefFragment[$current]
					{
						$current = $this_BogusTypeRefFragment_2.current;
						afterParserOrEnumRuleCall();
					}
				)?
				(
					(
						lv_variadic_3_0=FullStopFullStopFullStop
						{
							newLeafNode(lv_variadic_3_0, grammarAccess.getBindingElementFragmentAccess().getVariadicFullStopFullStopFullStopKeyword_0_1_2_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getBindingElementFragmentRule());
							}
							setWithLastConsumed($current, "variadic", true, "...");
						}
					)
				)?
				(
					(
						{
							newCompositeNode(grammarAccess.getBindingElementFragmentAccess().getNameBindingIdentifierParserRuleCall_0_1_3_0());
						}
						lv_name_4_0=norm1_BindingIdentifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getBindingElementFragmentRule());
							}
							set(
								$current,
								"name",
								lv_name_4_0,
								"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getBindingElementFragmentRule());
						}
						newCompositeNode(grammarAccess.getBindingElementFragmentAccess().getColonSepDeclaredTypeRefParserRuleCall_0_1_4());
					}
					this_ColonSepDeclaredTypeRef_5=ruleColonSepDeclaredTypeRef[$current]
					{
						$current = $this_ColonSepDeclaredTypeRef_5.current;
						afterParserOrEnumRuleCall();
					}
				)?
			)
		)
		(
			(
				(
					lv_hasInitializerAssignment_6_0=EqualsSign
					{
						newLeafNode(lv_hasInitializerAssignment_6_0, grammarAccess.getBindingElementFragmentAccess().getHasInitializerAssignmentEqualsSignKeyword_1_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getBindingElementFragmentRule());
						}
						setWithLastConsumed($current, "hasInitializerAssignment", true, "=");
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getBindingElementFragmentAccess().getInitializerAssignmentExpressionParserRuleCall_1_1_0());
					}
					lv_initializer_7_0=norm3_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getBindingElementFragmentRule());
						}
						set(
							$current,
							"initializer",
							lv_initializer_7_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)?
		)?
	)
;


// Rule BogusTypeRefFragment
ruleBogusTypeRefFragment[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getBogusTypeRefFragmentAccess().getBogusTypeRefTypeRefWithModifiersParserRuleCall_0());
			}
			lv_bogusTypeRef_0_0=ruleTypeRefWithModifiers
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getBogusTypeRefFragmentRule());
				}
				set(
					$current,
					"bogusTypeRef",
					lv_bogusTypeRef_0_0,
					"org.eclipse.n4js.ts.TypeExpressions.TypeRefWithModifiers");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleBlock
entryRuleBlock returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getBlockRule()); }
	iv_ruleBlock=ruleBlock
	{ $current=$iv_ruleBlock.current; }
	EOF;

// Rule Block
ruleBlock returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getBlockAccess().getBlockAction_0_0_0(),
							$current);
					}
				)
				otherlv_1=LeftCurlyBracket
				{
					newLeafNode(otherlv_1, grammarAccess.getBlockAccess().getLeftCurlyBracketKeyword_0_0_1());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getBlockAccess().getStatementsStatementParserRuleCall_1_0());
				}
				lv_statements_2_0=ruleStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getBlockRule());
					}
					add(
						$current,
						"statements",
						lv_statements_2_0,
						"org.eclipse.n4js.N4JS.Statement");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_3=RightCurlyBracket
		{
			newLeafNode(otherlv_3, grammarAccess.getBlockAccess().getRightCurlyBracketKeyword_2());
		}
	)
;


// Rule Block
norm1_Block returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getBlockAccess().getBlockAction_0_0_0(),
							$current);
					}
				)
				otherlv_1=LeftCurlyBracket
				{
					newLeafNode(otherlv_1, grammarAccess.getBlockAccess().getLeftCurlyBracketKeyword_0_0_1());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getBlockAccess().getStatementsStatementParserRuleCall_1_0());
				}
				lv_statements_2_0=norm1_Statement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getBlockRule());
					}
					add(
						$current,
						"statements",
						lv_statements_2_0,
						"org.eclipse.n4js.N4JS.Statement");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_3=RightCurlyBracket
		{
			newLeafNode(otherlv_3, grammarAccess.getBlockAccess().getRightCurlyBracketKeyword_2());
		}
	)
;

// Entry rule entryRuleRootStatement
entryRuleRootStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getRootStatementRule()); }
	iv_ruleRootStatement=ruleRootStatement
	{ $current=$iv_ruleRootStatement.current; }
	EOF;

// Rule RootStatement
ruleRootStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			{
				newCompositeNode(grammarAccess.getRootStatementAccess().getBlockParserRuleCall_0());
			}
			this_Block_0=ruleBlock
			{
				$current = $this_Block_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				(
					(
						ruleN4Modifier
					)
				)*
				ruleAsyncNoTrailingLineBreak[null]
				Function
			)
			)=>
			{
				newCompositeNode(grammarAccess.getRootStatementAccess().getFunctionDeclarationParserRuleCall_1());
			}
			this_FunctionDeclaration_1=ruleFunctionDeclaration
			{
				$current = $this_FunctionDeclaration_1.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				(
					(
						ruleVariableStatementKeyword
					)
				)
			)
			)=>
			{
				newCompositeNode(grammarAccess.getRootStatementAccess().getVariableStatementParserRuleCall_2());
			}
			this_VariableStatement_2=norm1_VariableStatement
			{
				$current = $this_VariableStatement_2.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getEmptyStatementParserRuleCall_3());
		}
		this_EmptyStatement_3=ruleEmptyStatement
		{
			$current = $this_EmptyStatement_3.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			((
				(
					(
						ruleBindingIdentifier
					)
				)
				Colon
			)
			)=>
			{
				newCompositeNode(grammarAccess.getRootStatementAccess().getLabelledStatementParserRuleCall_4());
			}
			this_LabelledStatement_4=ruleLabelledStatement
			{
				$current = $this_LabelledStatement_4.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getExpressionStatementParserRuleCall_5());
		}
		this_ExpressionStatement_5=ruleExpressionStatement
		{
			$current = $this_ExpressionStatement_5.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getIfStatementParserRuleCall_6());
		}
		this_IfStatement_6=ruleIfStatement
		{
			$current = $this_IfStatement_6.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getIterationStatementParserRuleCall_7());
		}
		this_IterationStatement_7=ruleIterationStatement
		{
			$current = $this_IterationStatement_7.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getContinueStatementParserRuleCall_8());
		}
		this_ContinueStatement_8=ruleContinueStatement
		{
			$current = $this_ContinueStatement_8.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getBreakStatementParserRuleCall_9());
		}
		this_BreakStatement_9=ruleBreakStatement
		{
			$current = $this_BreakStatement_9.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getReturnStatementParserRuleCall_10());
		}
		this_ReturnStatement_10=ruleReturnStatement
		{
			$current = $this_ReturnStatement_10.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getWithStatementParserRuleCall_11());
		}
		this_WithStatement_11=ruleWithStatement
		{
			$current = $this_WithStatement_11.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getSwitchStatementParserRuleCall_12());
		}
		this_SwitchStatement_12=ruleSwitchStatement
		{
			$current = $this_SwitchStatement_12.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getThrowStatementParserRuleCall_13());
		}
		this_ThrowStatement_13=ruleThrowStatement
		{
			$current = $this_ThrowStatement_13.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getTryStatementParserRuleCall_14());
		}
		this_TryStatement_14=ruleTryStatement
		{
			$current = $this_TryStatement_14.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getDebuggerStatementParserRuleCall_15());
		}
		this_DebuggerStatement_15=ruleDebuggerStatement
		{
			$current = $this_DebuggerStatement_15.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule RootStatement
norm1_RootStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			{
				newCompositeNode(grammarAccess.getRootStatementAccess().getBlockParserRuleCall_0());
			}
			this_Block_0=norm1_Block
			{
				$current = $this_Block_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				(
					(
						ruleN4Modifier
					)
				)*
				ruleAsyncNoTrailingLineBreak[null]
				Function
			)
			)=>
			{
				newCompositeNode(grammarAccess.getRootStatementAccess().getFunctionDeclarationParserRuleCall_1());
			}
			this_FunctionDeclaration_1=norm1_FunctionDeclaration
			{
				$current = $this_FunctionDeclaration_1.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				(
					(
						ruleVariableStatementKeyword
					)
				)
			)
			)=>
			{
				newCompositeNode(grammarAccess.getRootStatementAccess().getVariableStatementParserRuleCall_2());
			}
			this_VariableStatement_2=norm3_VariableStatement
			{
				$current = $this_VariableStatement_2.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getEmptyStatementParserRuleCall_3());
		}
		this_EmptyStatement_3=ruleEmptyStatement
		{
			$current = $this_EmptyStatement_3.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			((
				(
					(
						norm1_BindingIdentifier
					)
				)
				Colon
			)
			)=>
			{
				newCompositeNode(grammarAccess.getRootStatementAccess().getLabelledStatementParserRuleCall_4());
			}
			this_LabelledStatement_4=norm1_LabelledStatement
			{
				$current = $this_LabelledStatement_4.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getExpressionStatementParserRuleCall_5());
		}
		this_ExpressionStatement_5=norm1_ExpressionStatement
		{
			$current = $this_ExpressionStatement_5.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getIfStatementParserRuleCall_6());
		}
		this_IfStatement_6=norm1_IfStatement
		{
			$current = $this_IfStatement_6.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getIterationStatementParserRuleCall_7());
		}
		this_IterationStatement_7=norm1_IterationStatement
		{
			$current = $this_IterationStatement_7.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getContinueStatementParserRuleCall_8());
		}
		this_ContinueStatement_8=norm1_ContinueStatement
		{
			$current = $this_ContinueStatement_8.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getBreakStatementParserRuleCall_9());
		}
		this_BreakStatement_9=norm1_BreakStatement
		{
			$current = $this_BreakStatement_9.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getReturnStatementParserRuleCall_10());
		}
		this_ReturnStatement_10=norm1_ReturnStatement
		{
			$current = $this_ReturnStatement_10.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getWithStatementParserRuleCall_11());
		}
		this_WithStatement_11=norm1_WithStatement
		{
			$current = $this_WithStatement_11.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getSwitchStatementParserRuleCall_12());
		}
		this_SwitchStatement_12=norm1_SwitchStatement
		{
			$current = $this_SwitchStatement_12.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getThrowStatementParserRuleCall_13());
		}
		this_ThrowStatement_13=norm1_ThrowStatement
		{
			$current = $this_ThrowStatement_13.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getTryStatementParserRuleCall_14());
		}
		this_TryStatement_14=norm1_TryStatement
		{
			$current = $this_TryStatement_14.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getRootStatementAccess().getDebuggerStatementParserRuleCall_15());
		}
		this_DebuggerStatement_15=ruleDebuggerStatement
		{
			$current = $this_DebuggerStatement_15.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleStatement
entryRuleStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getStatementRule()); }
	iv_ruleStatement=ruleStatement
	{ $current=$iv_ruleStatement.current; }
	EOF;

// Rule Statement
ruleStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				CommercialAt
				(
					This
					    |
					Target
					    |
					RULE_IDENTIFIER
				)
			)
			)=>
			{
				newCompositeNode(grammarAccess.getStatementAccess().getAnnotatedFunctionDeclarationParserRuleCall_0());
			}
			this_AnnotatedFunctionDeclaration_0=ruleAnnotatedFunctionDeclaration
			{
				$current = $this_AnnotatedFunctionDeclaration_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getStatementAccess().getRootStatementParserRuleCall_1());
		}
		this_RootStatement_1=ruleRootStatement
		{
			$current = $this_RootStatement_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule Statement
norm1_Statement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				CommercialAt
				(
					This
					    |
					Target
					    |
					RULE_IDENTIFIER
				)
			)
			)=>
			{
				newCompositeNode(grammarAccess.getStatementAccess().getAnnotatedFunctionDeclarationParserRuleCall_0());
			}
			this_AnnotatedFunctionDeclaration_0=norm1_AnnotatedFunctionDeclaration
			{
				$current = $this_AnnotatedFunctionDeclaration_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getStatementAccess().getRootStatementParserRuleCall_1());
		}
		this_RootStatement_1=norm1_RootStatement
		{
			$current = $this_RootStatement_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule VariableStatement
norm1_VariableStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				(
					(
						ruleVariableStatementKeyword
					)
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getVariableStatementAccess().getVariableStatementAction_0_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getVariableStatementAccess().getVarStmtKeywordVariableStatementKeywordEnumRuleCall_0_0_1_0());
						}
						lv_varStmtKeyword_1_0=ruleVariableStatementKeyword
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getVariableStatementRule());
							}
							set(
								$current,
								"varStmtKeyword",
								lv_varStmtKeyword_1_0,
								"org.eclipse.n4js.N4JS.VariableStatementKeyword");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getVariableStatementAccess().getVarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_1_0());
				}
				lv_varDeclsOrBindings_2_0=norm1_VariableDeclarationOrBinding
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVariableStatementRule());
					}
					add(
						$current,
						"varDeclsOrBindings",
						lv_varDeclsOrBindings_2_0,
						"org.eclipse.n4js.N4JS.VariableDeclarationOrBinding");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_3=Comma
			{
				newLeafNode(otherlv_3, grammarAccess.getVariableStatementAccess().getCommaKeyword_2_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getVariableStatementAccess().getVarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_2_1_0());
					}
					lv_varDeclsOrBindings_4_0=norm1_VariableDeclarationOrBinding
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getVariableStatementRule());
						}
						add(
							$current,
							"varDeclsOrBindings",
							lv_varDeclsOrBindings_4_0,
							"org.eclipse.n4js.N4JS.VariableDeclarationOrBinding");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		{
			newCompositeNode(grammarAccess.getVariableStatementAccess().getSemiParserRuleCall_3());
		}
		ruleSemi
		{
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule VariableStatement
norm3_VariableStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				(
					(
						ruleVariableStatementKeyword
					)
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getVariableStatementAccess().getVariableStatementAction_0_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getVariableStatementAccess().getVarStmtKeywordVariableStatementKeywordEnumRuleCall_0_0_1_0());
						}
						lv_varStmtKeyword_1_0=ruleVariableStatementKeyword
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getVariableStatementRule());
							}
							set(
								$current,
								"varStmtKeyword",
								lv_varStmtKeyword_1_0,
								"org.eclipse.n4js.N4JS.VariableStatementKeyword");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getVariableStatementAccess().getVarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_1_0());
				}
				lv_varDeclsOrBindings_2_0=norm3_VariableDeclarationOrBinding
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVariableStatementRule());
					}
					add(
						$current,
						"varDeclsOrBindings",
						lv_varDeclsOrBindings_2_0,
						"org.eclipse.n4js.N4JS.VariableDeclarationOrBinding");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_3=Comma
			{
				newLeafNode(otherlv_3, grammarAccess.getVariableStatementAccess().getCommaKeyword_2_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getVariableStatementAccess().getVarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_2_1_0());
					}
					lv_varDeclsOrBindings_4_0=norm3_VariableDeclarationOrBinding
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getVariableStatementRule());
						}
						add(
							$current,
							"varDeclsOrBindings",
							lv_varDeclsOrBindings_4_0,
							"org.eclipse.n4js.N4JS.VariableDeclarationOrBinding");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		{
			newCompositeNode(grammarAccess.getVariableStatementAccess().getSemiParserRuleCall_3());
		}
		ruleSemi
		{
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleExportedVariableStatement
entryRuleExportedVariableStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getExportedVariableStatementRule()); }
	iv_ruleExportedVariableStatement=ruleExportedVariableStatement
	{ $current=$iv_ruleExportedVariableStatement.current; }
	EOF;

// Rule ExportedVariableStatement
ruleExportedVariableStatement returns [EObject current=null]
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
					grammarAccess.getExportedVariableStatementAccess().getExportedVariableStatementAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getExportedVariableStatementAccess().getDeclaredModifiersN4ModifierEnumRuleCall_1_0());
				}
				lv_declaredModifiers_1_0=ruleN4Modifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getExportedVariableStatementRule());
					}
					add(
						$current,
						"declaredModifiers",
						lv_declaredModifiers_1_0,
						"org.eclipse.n4js.N4JS.N4Modifier");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			(
				{
					newCompositeNode(grammarAccess.getExportedVariableStatementAccess().getVarStmtKeywordVariableStatementKeywordEnumRuleCall_2_0());
				}
				lv_varStmtKeyword_2_0=ruleVariableStatementKeyword
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getExportedVariableStatementRule());
					}
					set(
						$current,
						"varStmtKeyword",
						lv_varStmtKeyword_2_0,
						"org.eclipse.n4js.N4JS.VariableStatementKeyword");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getExportedVariableStatementAccess().getVarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_3_0());
				}
				lv_varDeclsOrBindings_3_0=ruleExportedVariableDeclarationOrBinding
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getExportedVariableStatementRule());
					}
					add(
						$current,
						"varDeclsOrBindings",
						lv_varDeclsOrBindings_3_0,
						"org.eclipse.n4js.N4JS.ExportedVariableDeclarationOrBinding");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_4=Comma
			{
				newLeafNode(otherlv_4, grammarAccess.getExportedVariableStatementAccess().getCommaKeyword_4_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getExportedVariableStatementAccess().getVarDeclsOrBindingsExportedVariableDeclarationOrBindingParserRuleCall_4_1_0());
					}
					lv_varDeclsOrBindings_5_0=ruleExportedVariableDeclarationOrBinding
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getExportedVariableStatementRule());
						}
						add(
							$current,
							"varDeclsOrBindings",
							lv_varDeclsOrBindings_5_0,
							"org.eclipse.n4js.N4JS.ExportedVariableDeclarationOrBinding");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		{
			newCompositeNode(grammarAccess.getExportedVariableStatementAccess().getSemiParserRuleCall_5());
		}
		ruleSemi
		{
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleVariableDeclarationOrBinding
entryRuleVariableDeclarationOrBinding returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getVariableDeclarationOrBindingRule()); }
	iv_ruleVariableDeclarationOrBinding=ruleVariableDeclarationOrBinding
	{ $current=$iv_ruleVariableDeclarationOrBinding.current; }
	EOF;

// Rule VariableDeclarationOrBinding
ruleVariableDeclarationOrBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				ruleBindingPattern
			)
			)=>
			{
				newCompositeNode(grammarAccess.getVariableDeclarationOrBindingAccess().getVariableBindingParserRuleCall_0());
			}
			this_VariableBinding_0=ruleVariableBinding
			{
				$current = $this_VariableBinding_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getVariableDeclarationOrBindingAccess().getVariableDeclarationParserRuleCall_1());
		}
		this_VariableDeclaration_1=norm4_VariableDeclaration
		{
			$current = $this_VariableDeclaration_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule VariableDeclarationOrBinding
norm1_VariableDeclarationOrBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				ruleBindingPattern
			)
			)=>
			{
				newCompositeNode(grammarAccess.getVariableDeclarationOrBindingAccess().getVariableBindingParserRuleCall_0());
			}
			this_VariableBinding_0=norm1_VariableBinding
			{
				$current = $this_VariableBinding_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getVariableDeclarationOrBindingAccess().getVariableDeclarationParserRuleCall_1());
		}
		this_VariableDeclaration_1=norm5_VariableDeclaration
		{
			$current = $this_VariableDeclaration_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule VariableDeclarationOrBinding
norm2_VariableDeclarationOrBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				norm1_BindingPattern
			)
			)=>
			{
				newCompositeNode(grammarAccess.getVariableDeclarationOrBindingAccess().getVariableBindingParserRuleCall_0());
			}
			this_VariableBinding_0=norm2_VariableBinding
			{
				$current = $this_VariableBinding_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getVariableDeclarationOrBindingAccess().getVariableDeclarationParserRuleCall_1());
		}
		this_VariableDeclaration_1=norm6_VariableDeclaration
		{
			$current = $this_VariableDeclaration_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule VariableDeclarationOrBinding
norm3_VariableDeclarationOrBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				norm1_BindingPattern
			)
			)=>
			{
				newCompositeNode(grammarAccess.getVariableDeclarationOrBindingAccess().getVariableBindingParserRuleCall_0());
			}
			this_VariableBinding_0=norm3_VariableBinding
			{
				$current = $this_VariableBinding_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getVariableDeclarationOrBindingAccess().getVariableDeclarationParserRuleCall_1());
		}
		this_VariableDeclaration_1=norm7_VariableDeclaration
		{
			$current = $this_VariableDeclaration_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule VariableDeclarationOrBinding
norm4_VariableDeclarationOrBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				ruleBindingPattern
			)
			)=>
			{
				newCompositeNode(grammarAccess.getVariableDeclarationOrBindingAccess().getVariableBindingParserRuleCall_0());
			}
			this_VariableBinding_0=norm4_VariableBinding
			{
				$current = $this_VariableBinding_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getVariableDeclarationOrBindingAccess().getVariableDeclarationParserRuleCall_1());
		}
		this_VariableDeclaration_1=norm4_VariableDeclaration
		{
			$current = $this_VariableDeclaration_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule VariableDeclarationOrBinding
norm6_VariableDeclarationOrBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				norm1_BindingPattern
			)
			)=>
			{
				newCompositeNode(grammarAccess.getVariableDeclarationOrBindingAccess().getVariableBindingParserRuleCall_0());
			}
			this_VariableBinding_0=norm6_VariableBinding
			{
				$current = $this_VariableBinding_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getVariableDeclarationOrBindingAccess().getVariableDeclarationParserRuleCall_1());
		}
		this_VariableDeclaration_1=norm6_VariableDeclaration
		{
			$current = $this_VariableDeclaration_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleVariableBinding
entryRuleVariableBinding returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getVariableBindingRule()); }
	iv_ruleVariableBinding=ruleVariableBinding
	{ $current=$iv_ruleVariableBinding.current; }
	EOF;

// Rule VariableBinding
ruleVariableBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				ruleBindingPattern
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getVariableBindingAccess().getPatternBindingPatternParserRuleCall_0_0());
				}
				lv_pattern_0_0=ruleBindingPattern
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVariableBindingRule());
					}
					set(
						$current,
						"pattern",
						lv_pattern_0_0,
						"org.eclipse.n4js.N4JS.BindingPattern");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_1=EqualsSign
			{
				newLeafNode(otherlv_1, grammarAccess.getVariableBindingAccess().getEqualsSignKeyword_1_1_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getVariableBindingAccess().getExpressionAssignmentExpressionParserRuleCall_1_1_1_0());
					}
					lv_expression_2_0=ruleAssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getVariableBindingRule());
						}
						set(
							$current,
							"expression",
							lv_expression_2_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;


// Rule VariableBinding
norm1_VariableBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				ruleBindingPattern
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getVariableBindingAccess().getPatternBindingPatternParserRuleCall_0_0());
				}
				lv_pattern_0_0=ruleBindingPattern
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVariableBindingRule());
					}
					set(
						$current,
						"pattern",
						lv_pattern_0_0,
						"org.eclipse.n4js.N4JS.BindingPattern");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_1=EqualsSign
			{
				newLeafNode(otherlv_1, grammarAccess.getVariableBindingAccess().getEqualsSignKeyword_1_1_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getVariableBindingAccess().getExpressionAssignmentExpressionParserRuleCall_1_1_1_0());
					}
					lv_expression_2_0=norm1_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getVariableBindingRule());
						}
						set(
							$current,
							"expression",
							lv_expression_2_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;


// Rule VariableBinding
norm2_VariableBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				norm1_BindingPattern
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getVariableBindingAccess().getPatternBindingPatternParserRuleCall_0_0());
				}
				lv_pattern_0_0=norm1_BindingPattern
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVariableBindingRule());
					}
					set(
						$current,
						"pattern",
						lv_pattern_0_0,
						"org.eclipse.n4js.N4JS.BindingPattern");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_1=EqualsSign
			{
				newLeafNode(otherlv_1, grammarAccess.getVariableBindingAccess().getEqualsSignKeyword_1_1_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getVariableBindingAccess().getExpressionAssignmentExpressionParserRuleCall_1_1_1_0());
					}
					lv_expression_2_0=norm2_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getVariableBindingRule());
						}
						set(
							$current,
							"expression",
							lv_expression_2_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;


// Rule VariableBinding
norm3_VariableBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				norm1_BindingPattern
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getVariableBindingAccess().getPatternBindingPatternParserRuleCall_0_0());
				}
				lv_pattern_0_0=norm1_BindingPattern
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVariableBindingRule());
					}
					set(
						$current,
						"pattern",
						lv_pattern_0_0,
						"org.eclipse.n4js.N4JS.BindingPattern");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_1=EqualsSign
			{
				newLeafNode(otherlv_1, grammarAccess.getVariableBindingAccess().getEqualsSignKeyword_1_1_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getVariableBindingAccess().getExpressionAssignmentExpressionParserRuleCall_1_1_1_0());
					}
					lv_expression_2_0=norm3_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getVariableBindingRule());
						}
						set(
							$current,
							"expression",
							lv_expression_2_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;


// Rule VariableBinding
norm4_VariableBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				ruleBindingPattern
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getVariableBindingAccess().getPatternBindingPatternParserRuleCall_0_0());
				}
				lv_pattern_0_0=ruleBindingPattern
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVariableBindingRule());
					}
					set(
						$current,
						"pattern",
						lv_pattern_0_0,
						"org.eclipse.n4js.N4JS.BindingPattern");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_1=EqualsSign
			{
				newLeafNode(otherlv_1, grammarAccess.getVariableBindingAccess().getEqualsSignKeyword_1_0_0_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getVariableBindingAccess().getExpressionAssignmentExpressionParserRuleCall_1_0_0_1_0());
					}
					lv_expression_2_0=ruleAssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getVariableBindingRule());
						}
						set(
							$current,
							"expression",
							lv_expression_2_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;


// Rule VariableBinding
norm5_VariableBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				ruleBindingPattern
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getVariableBindingAccess().getPatternBindingPatternParserRuleCall_0_0());
				}
				lv_pattern_0_0=ruleBindingPattern
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVariableBindingRule());
					}
					set(
						$current,
						"pattern",
						lv_pattern_0_0,
						"org.eclipse.n4js.N4JS.BindingPattern");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_1=EqualsSign
			{
				newLeafNode(otherlv_1, grammarAccess.getVariableBindingAccess().getEqualsSignKeyword_1_0_0_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getVariableBindingAccess().getExpressionAssignmentExpressionParserRuleCall_1_0_0_1_0());
					}
					lv_expression_2_0=norm1_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getVariableBindingRule());
						}
						set(
							$current,
							"expression",
							lv_expression_2_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;


// Rule VariableBinding
norm6_VariableBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				norm1_BindingPattern
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getVariableBindingAccess().getPatternBindingPatternParserRuleCall_0_0());
				}
				lv_pattern_0_0=norm1_BindingPattern
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVariableBindingRule());
					}
					set(
						$current,
						"pattern",
						lv_pattern_0_0,
						"org.eclipse.n4js.N4JS.BindingPattern");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_1=EqualsSign
			{
				newLeafNode(otherlv_1, grammarAccess.getVariableBindingAccess().getEqualsSignKeyword_1_0_0_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getVariableBindingAccess().getExpressionAssignmentExpressionParserRuleCall_1_0_0_1_0());
					}
					lv_expression_2_0=norm2_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getVariableBindingRule());
						}
						set(
							$current,
							"expression",
							lv_expression_2_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;


// Rule VariableBinding
norm7_VariableBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				norm1_BindingPattern
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getVariableBindingAccess().getPatternBindingPatternParserRuleCall_0_0());
				}
				lv_pattern_0_0=norm1_BindingPattern
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVariableBindingRule());
					}
					set(
						$current,
						"pattern",
						lv_pattern_0_0,
						"org.eclipse.n4js.N4JS.BindingPattern");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_1=EqualsSign
			{
				newLeafNode(otherlv_1, grammarAccess.getVariableBindingAccess().getEqualsSignKeyword_1_0_0_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getVariableBindingAccess().getExpressionAssignmentExpressionParserRuleCall_1_0_0_1_0());
					}
					lv_expression_2_0=norm3_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getVariableBindingRule());
						}
						set(
							$current,
							"expression",
							lv_expression_2_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;


// Rule VariableDeclaration
norm1_VariableDeclaration returns [EObject current=null]
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
					grammarAccess.getVariableDeclarationAccess().getVariableDeclarationAction_0(),
					$current);
			}
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getVariableDeclarationRule());
			}
			newCompositeNode(grammarAccess.getVariableDeclarationAccess().getVariableDeclarationImplParserRuleCall_1());
		}
		this_VariableDeclarationImpl_1=norm1_VariableDeclarationImpl[$current]
		{
			$current = $this_VariableDeclarationImpl_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule VariableDeclaration
norm3_VariableDeclaration returns [EObject current=null]
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
					grammarAccess.getVariableDeclarationAccess().getVariableDeclarationAction_0(),
					$current);
			}
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getVariableDeclarationRule());
			}
			newCompositeNode(grammarAccess.getVariableDeclarationAccess().getVariableDeclarationImplParserRuleCall_1());
		}
		this_VariableDeclarationImpl_1=norm3_VariableDeclarationImpl[$current]
		{
			$current = $this_VariableDeclarationImpl_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule VariableDeclaration
norm4_VariableDeclaration returns [EObject current=null]
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
					grammarAccess.getVariableDeclarationAccess().getVariableDeclarationAction_0(),
					$current);
			}
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getVariableDeclarationRule());
			}
			newCompositeNode(grammarAccess.getVariableDeclarationAccess().getVariableDeclarationImplParserRuleCall_1());
		}
		this_VariableDeclarationImpl_1=norm4_VariableDeclarationImpl[$current]
		{
			$current = $this_VariableDeclarationImpl_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule VariableDeclaration
norm5_VariableDeclaration returns [EObject current=null]
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
					grammarAccess.getVariableDeclarationAccess().getVariableDeclarationAction_0(),
					$current);
			}
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getVariableDeclarationRule());
			}
			newCompositeNode(grammarAccess.getVariableDeclarationAccess().getVariableDeclarationImplParserRuleCall_1());
		}
		this_VariableDeclarationImpl_1=norm5_VariableDeclarationImpl[$current]
		{
			$current = $this_VariableDeclarationImpl_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule VariableDeclaration
norm6_VariableDeclaration returns [EObject current=null]
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
					grammarAccess.getVariableDeclarationAccess().getVariableDeclarationAction_0(),
					$current);
			}
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getVariableDeclarationRule());
			}
			newCompositeNode(grammarAccess.getVariableDeclarationAccess().getVariableDeclarationImplParserRuleCall_1());
		}
		this_VariableDeclarationImpl_1=norm6_VariableDeclarationImpl[$current]
		{
			$current = $this_VariableDeclarationImpl_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule VariableDeclaration
norm7_VariableDeclaration returns [EObject current=null]
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
					grammarAccess.getVariableDeclarationAccess().getVariableDeclarationAction_0(),
					$current);
			}
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getVariableDeclarationRule());
			}
			newCompositeNode(grammarAccess.getVariableDeclarationAccess().getVariableDeclarationImplParserRuleCall_1());
		}
		this_VariableDeclarationImpl_1=norm7_VariableDeclarationImpl[$current]
		{
			$current = $this_VariableDeclarationImpl_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule VariableDeclarationImpl
ruleVariableDeclarationImpl[EObject in_current]  returns [EObject current=in_current]
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
					newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getAnnotationsAnnotationParserRuleCall_0_0());
				}
				lv_annotations_0_0=ruleAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_0_0,
						"org.eclipse.n4js.N4JS.Annotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			(
				((
					(
						ruleBindingIdentifier
					)
				)
				)=>
				(
					(
						{
							newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getNameBindingIdentifierParserRuleCall_1_1_0_0_0());
						}
						lv_name_1_0=ruleBindingIdentifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
							}
							set(
								$current,
								"name",
								lv_name_1_0,
								"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			(
				otherlv_2=EqualsSign
				{
					newLeafNode(otherlv_2, grammarAccess.getVariableDeclarationImplAccess().getEqualsSignKeyword_1_1_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getExpressionAssignmentExpressionParserRuleCall_1_1_1_1_0());
						}
						lv_expression_3_0=ruleAssignmentExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
							}
							set(
								$current,
								"expression",
								lv_expression_3_0,
								"org.eclipse.n4js.N4JS.AssignmentExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)?
		)
	)
;


// Rule VariableDeclarationImpl
norm1_VariableDeclarationImpl[EObject in_current]  returns [EObject current=in_current]
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
					newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getAnnotationsAnnotationParserRuleCall_0_0());
				}
				lv_annotations_0_0=ruleAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_0_0,
						"org.eclipse.n4js.N4JS.Annotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			(
				((
					(
						ruleBindingIdentifier
					)
				)
				)=>
				(
					(
						{
							newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getNameBindingIdentifierParserRuleCall_1_1_0_0_0());
						}
						lv_name_1_0=ruleBindingIdentifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
							}
							set(
								$current,
								"name",
								lv_name_1_0,
								"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			(
				otherlv_2=EqualsSign
				{
					newLeafNode(otherlv_2, grammarAccess.getVariableDeclarationImplAccess().getEqualsSignKeyword_1_1_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getExpressionAssignmentExpressionParserRuleCall_1_1_1_1_0());
						}
						lv_expression_3_0=norm1_AssignmentExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
							}
							set(
								$current,
								"expression",
								lv_expression_3_0,
								"org.eclipse.n4js.N4JS.AssignmentExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)?
		)
	)
;


// Rule VariableDeclarationImpl
norm2_VariableDeclarationImpl[EObject in_current]  returns [EObject current=in_current]
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
					newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getAnnotationsAnnotationParserRuleCall_0_0());
				}
				lv_annotations_0_0=ruleAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_0_0,
						"org.eclipse.n4js.N4JS.Annotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			(
				((
					(
						norm1_BindingIdentifier
					)
				)
				)=>
				(
					(
						{
							newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getNameBindingIdentifierParserRuleCall_1_1_0_0_0());
						}
						lv_name_1_0=norm1_BindingIdentifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
							}
							set(
								$current,
								"name",
								lv_name_1_0,
								"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			(
				otherlv_2=EqualsSign
				{
					newLeafNode(otherlv_2, grammarAccess.getVariableDeclarationImplAccess().getEqualsSignKeyword_1_1_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getExpressionAssignmentExpressionParserRuleCall_1_1_1_1_0());
						}
						lv_expression_3_0=norm2_AssignmentExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
							}
							set(
								$current,
								"expression",
								lv_expression_3_0,
								"org.eclipse.n4js.N4JS.AssignmentExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)?
		)
	)
;


// Rule VariableDeclarationImpl
norm3_VariableDeclarationImpl[EObject in_current]  returns [EObject current=in_current]
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
					newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getAnnotationsAnnotationParserRuleCall_0_0());
				}
				lv_annotations_0_0=ruleAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_0_0,
						"org.eclipse.n4js.N4JS.Annotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			(
				((
					(
						norm1_BindingIdentifier
					)
				)
				)=>
				(
					(
						{
							newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getNameBindingIdentifierParserRuleCall_1_1_0_0_0());
						}
						lv_name_1_0=norm1_BindingIdentifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
							}
							set(
								$current,
								"name",
								lv_name_1_0,
								"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			(
				otherlv_2=EqualsSign
				{
					newLeafNode(otherlv_2, grammarAccess.getVariableDeclarationImplAccess().getEqualsSignKeyword_1_1_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getExpressionAssignmentExpressionParserRuleCall_1_1_1_1_0());
						}
						lv_expression_3_0=norm3_AssignmentExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
							}
							set(
								$current,
								"expression",
								lv_expression_3_0,
								"org.eclipse.n4js.N4JS.AssignmentExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)?
		)
	)
;


// Rule VariableDeclarationImpl
norm4_VariableDeclarationImpl[EObject in_current]  returns [EObject current=in_current]
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
					newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getAnnotationsAnnotationParserRuleCall_0_0());
				}
				lv_annotations_0_0=ruleAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_0_0,
						"org.eclipse.n4js.N4JS.Annotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			(
				((
					(
						(
							ruleBindingIdentifier
						)
					)
					(
						ruleColonSepDeclaredTypeRef[null]
					)?
				)
				)=>
				(
					(
						(
							{
								newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getNameBindingIdentifierParserRuleCall_1_0_0_0_0_0());
							}
							lv_name_1_0=ruleBindingIdentifier
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
								}
								set(
									$current,
									"name",
									lv_name_1_0,
									"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
								afterParserOrEnumRuleCall();
							}
						)
					)
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getVariableDeclarationImplRule());
							}
							newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1());
						}
						this_ColonSepDeclaredTypeRef_2=ruleColonSepDeclaredTypeRef[$current]
						{
							$current = $this_ColonSepDeclaredTypeRef_2.current;
							afterParserOrEnumRuleCall();
						}
					)?
				)
			)
			(
				otherlv_3=EqualsSign
				{
					newLeafNode(otherlv_3, grammarAccess.getVariableDeclarationImplAccess().getEqualsSignKeyword_1_0_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getExpressionAssignmentExpressionParserRuleCall_1_0_1_1_0());
						}
						lv_expression_4_0=ruleAssignmentExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
							}
							set(
								$current,
								"expression",
								lv_expression_4_0,
								"org.eclipse.n4js.N4JS.AssignmentExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)?
		)
	)
;


// Rule VariableDeclarationImpl
norm5_VariableDeclarationImpl[EObject in_current]  returns [EObject current=in_current]
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
					newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getAnnotationsAnnotationParserRuleCall_0_0());
				}
				lv_annotations_0_0=ruleAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_0_0,
						"org.eclipse.n4js.N4JS.Annotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			(
				((
					(
						(
							ruleBindingIdentifier
						)
					)
					(
						ruleColonSepDeclaredTypeRef[null]
					)?
				)
				)=>
				(
					(
						(
							{
								newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getNameBindingIdentifierParserRuleCall_1_0_0_0_0_0());
							}
							lv_name_1_0=ruleBindingIdentifier
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
								}
								set(
									$current,
									"name",
									lv_name_1_0,
									"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
								afterParserOrEnumRuleCall();
							}
						)
					)
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getVariableDeclarationImplRule());
							}
							newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1());
						}
						this_ColonSepDeclaredTypeRef_2=ruleColonSepDeclaredTypeRef[$current]
						{
							$current = $this_ColonSepDeclaredTypeRef_2.current;
							afterParserOrEnumRuleCall();
						}
					)?
				)
			)
			(
				otherlv_3=EqualsSign
				{
					newLeafNode(otherlv_3, grammarAccess.getVariableDeclarationImplAccess().getEqualsSignKeyword_1_0_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getExpressionAssignmentExpressionParserRuleCall_1_0_1_1_0());
						}
						lv_expression_4_0=norm1_AssignmentExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
							}
							set(
								$current,
								"expression",
								lv_expression_4_0,
								"org.eclipse.n4js.N4JS.AssignmentExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)?
		)
	)
;


// Rule VariableDeclarationImpl
norm6_VariableDeclarationImpl[EObject in_current]  returns [EObject current=in_current]
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
					newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getAnnotationsAnnotationParserRuleCall_0_0());
				}
				lv_annotations_0_0=ruleAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_0_0,
						"org.eclipse.n4js.N4JS.Annotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			(
				((
					(
						(
							norm1_BindingIdentifier
						)
					)
					(
						ruleColonSepDeclaredTypeRef[null]
					)?
				)
				)=>
				(
					(
						(
							{
								newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getNameBindingIdentifierParserRuleCall_1_0_0_0_0_0());
							}
							lv_name_1_0=norm1_BindingIdentifier
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
								}
								set(
									$current,
									"name",
									lv_name_1_0,
									"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
								afterParserOrEnumRuleCall();
							}
						)
					)
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getVariableDeclarationImplRule());
							}
							newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1());
						}
						this_ColonSepDeclaredTypeRef_2=ruleColonSepDeclaredTypeRef[$current]
						{
							$current = $this_ColonSepDeclaredTypeRef_2.current;
							afterParserOrEnumRuleCall();
						}
					)?
				)
			)
			(
				otherlv_3=EqualsSign
				{
					newLeafNode(otherlv_3, grammarAccess.getVariableDeclarationImplAccess().getEqualsSignKeyword_1_0_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getExpressionAssignmentExpressionParserRuleCall_1_0_1_1_0());
						}
						lv_expression_4_0=norm2_AssignmentExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
							}
							set(
								$current,
								"expression",
								lv_expression_4_0,
								"org.eclipse.n4js.N4JS.AssignmentExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)?
		)
	)
;


// Rule VariableDeclarationImpl
norm7_VariableDeclarationImpl[EObject in_current]  returns [EObject current=in_current]
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
					newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getAnnotationsAnnotationParserRuleCall_0_0());
				}
				lv_annotations_0_0=ruleAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_0_0,
						"org.eclipse.n4js.N4JS.Annotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			(
				((
					(
						(
							norm1_BindingIdentifier
						)
					)
					(
						ruleColonSepDeclaredTypeRef[null]
					)?
				)
				)=>
				(
					(
						(
							{
								newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getNameBindingIdentifierParserRuleCall_1_0_0_0_0_0());
							}
							lv_name_1_0=norm1_BindingIdentifier
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
								}
								set(
									$current,
									"name",
									lv_name_1_0,
									"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
								afterParserOrEnumRuleCall();
							}
						)
					)
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getVariableDeclarationImplRule());
							}
							newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getColonSepDeclaredTypeRefParserRuleCall_1_0_0_0_1());
						}
						this_ColonSepDeclaredTypeRef_2=ruleColonSepDeclaredTypeRef[$current]
						{
							$current = $this_ColonSepDeclaredTypeRef_2.current;
							afterParserOrEnumRuleCall();
						}
					)?
				)
			)
			(
				otherlv_3=EqualsSign
				{
					newLeafNode(otherlv_3, grammarAccess.getVariableDeclarationImplAccess().getEqualsSignKeyword_1_0_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getVariableDeclarationImplAccess().getExpressionAssignmentExpressionParserRuleCall_1_0_1_1_0());
						}
						lv_expression_4_0=norm3_AssignmentExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getVariableDeclarationImplRule());
							}
							set(
								$current,
								"expression",
								lv_expression_4_0,
								"org.eclipse.n4js.N4JS.AssignmentExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)?
		)
	)
;

// Entry rule entryRuleExportedVariableDeclarationOrBinding
entryRuleExportedVariableDeclarationOrBinding returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getExportedVariableDeclarationOrBindingRule()); }
	iv_ruleExportedVariableDeclarationOrBinding=ruleExportedVariableDeclarationOrBinding
	{ $current=$iv_ruleExportedVariableDeclarationOrBinding.current; }
	EOF;

// Rule ExportedVariableDeclarationOrBinding
ruleExportedVariableDeclarationOrBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				ruleBindingPattern
			)
			)=>
			{
				newCompositeNode(grammarAccess.getExportedVariableDeclarationOrBindingAccess().getExportedVariableBindingParserRuleCall_0());
			}
			this_ExportedVariableBinding_0=ruleExportedVariableBinding
			{
				$current = $this_ExportedVariableBinding_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getExportedVariableDeclarationOrBindingAccess().getExportedVariableDeclarationParserRuleCall_1());
		}
		this_ExportedVariableDeclaration_1=ruleExportedVariableDeclaration
		{
			$current = $this_ExportedVariableDeclaration_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule ExportedVariableDeclarationOrBinding
norm1_ExportedVariableDeclarationOrBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				norm1_BindingPattern
			)
			)=>
			{
				newCompositeNode(grammarAccess.getExportedVariableDeclarationOrBindingAccess().getExportedVariableBindingParserRuleCall_0());
			}
			this_ExportedVariableBinding_0=norm1_ExportedVariableBinding
			{
				$current = $this_ExportedVariableBinding_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getExportedVariableDeclarationOrBindingAccess().getExportedVariableDeclarationParserRuleCall_1());
		}
		this_ExportedVariableDeclaration_1=norm1_ExportedVariableDeclaration
		{
			$current = $this_ExportedVariableDeclaration_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleExportedVariableBinding
entryRuleExportedVariableBinding returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getExportedVariableBindingRule()); }
	iv_ruleExportedVariableBinding=ruleExportedVariableBinding
	{ $current=$iv_ruleExportedVariableBinding.current; }
	EOF;

// Rule ExportedVariableBinding
ruleExportedVariableBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				ruleBindingPattern
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getExportedVariableBindingAccess().getPatternBindingPatternParserRuleCall_0_0());
				}
				lv_pattern_0_0=ruleBindingPattern
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getExportedVariableBindingRule());
					}
					set(
						$current,
						"pattern",
						lv_pattern_0_0,
						"org.eclipse.n4js.N4JS.BindingPattern");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_1=EqualsSign
		{
			newLeafNode(otherlv_1, grammarAccess.getExportedVariableBindingAccess().getEqualsSignKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getExportedVariableBindingAccess().getExpressionAssignmentExpressionParserRuleCall_2_0());
				}
				lv_expression_2_0=norm1_AssignmentExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getExportedVariableBindingRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.n4js.N4JS.AssignmentExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule ExportedVariableBinding
norm1_ExportedVariableBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				norm1_BindingPattern
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getExportedVariableBindingAccess().getPatternBindingPatternParserRuleCall_0_0());
				}
				lv_pattern_0_0=norm1_BindingPattern
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getExportedVariableBindingRule());
					}
					set(
						$current,
						"pattern",
						lv_pattern_0_0,
						"org.eclipse.n4js.N4JS.BindingPattern");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_1=EqualsSign
		{
			newLeafNode(otherlv_1, grammarAccess.getExportedVariableBindingAccess().getEqualsSignKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getExportedVariableBindingAccess().getExpressionAssignmentExpressionParserRuleCall_2_0());
				}
				lv_expression_2_0=norm3_AssignmentExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getExportedVariableBindingRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.n4js.N4JS.AssignmentExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleExportedVariableDeclaration
entryRuleExportedVariableDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getExportedVariableDeclarationRule()); }
	iv_ruleExportedVariableDeclaration=ruleExportedVariableDeclaration
	{ $current=$iv_ruleExportedVariableDeclaration.current; }
	EOF;

// Rule ExportedVariableDeclaration
ruleExportedVariableDeclaration returns [EObject current=null]
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
					grammarAccess.getExportedVariableDeclarationAccess().getExportedVariableDeclarationAction_0(),
					$current);
			}
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getExportedVariableDeclarationRule());
			}
			newCompositeNode(grammarAccess.getExportedVariableDeclarationAccess().getVariableDeclarationImplParserRuleCall_1());
		}
		this_VariableDeclarationImpl_1=norm5_VariableDeclarationImpl[$current]
		{
			$current = $this_VariableDeclarationImpl_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule ExportedVariableDeclaration
norm1_ExportedVariableDeclaration returns [EObject current=null]
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
					grammarAccess.getExportedVariableDeclarationAccess().getExportedVariableDeclarationAction_0(),
					$current);
			}
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getExportedVariableDeclarationRule());
			}
			newCompositeNode(grammarAccess.getExportedVariableDeclarationAccess().getVariableDeclarationImplParserRuleCall_1());
		}
		this_VariableDeclarationImpl_1=norm7_VariableDeclarationImpl[$current]
		{
			$current = $this_VariableDeclarationImpl_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleEmptyStatement
entryRuleEmptyStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getEmptyStatementRule()); }
	iv_ruleEmptyStatement=ruleEmptyStatement
	{ $current=$iv_ruleEmptyStatement.current; }
	EOF;

// Rule EmptyStatement
ruleEmptyStatement returns [EObject current=null]
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
					grammarAccess.getEmptyStatementAccess().getEmptyStatementAction_0(),
					$current);
			}
		)
		otherlv_1=Semicolon
		{
			newLeafNode(otherlv_1, grammarAccess.getEmptyStatementAccess().getSemicolonKeyword_1());
		}
	)
;

// Entry rule entryRuleExpressionStatement
entryRuleExpressionStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getExpressionStatementRule()); }
	iv_ruleExpressionStatement=ruleExpressionStatement
	{ $current=$iv_ruleExpressionStatement.current; }
	EOF;

// Rule ExpressionStatement
ruleExpressionStatement returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getExpressionStatementAccess().getExpressionExpressionParserRuleCall_0_0());
				}
				lv_expression_0_0=norm1_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getExpressionStatementRule());
					}
					set(
						$current,
						"expression",
						lv_expression_0_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		{
			newCompositeNode(grammarAccess.getExpressionStatementAccess().getSemiParserRuleCall_1());
		}
		ruleSemi
		{
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule ExpressionStatement
norm1_ExpressionStatement returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getExpressionStatementAccess().getExpressionExpressionParserRuleCall_0_0());
				}
				lv_expression_0_0=norm3_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getExpressionStatementRule());
					}
					set(
						$current,
						"expression",
						lv_expression_0_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		{
			newCompositeNode(grammarAccess.getExpressionStatementAccess().getSemiParserRuleCall_1());
		}
		ruleSemi
		{
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleIfStatement
entryRuleIfStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getIfStatementRule()); }
	iv_ruleIfStatement=ruleIfStatement
	{ $current=$iv_ruleIfStatement.current; }
	EOF;

// Rule IfStatement
ruleIfStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=If
		{
			newLeafNode(otherlv_0, grammarAccess.getIfStatementAccess().getIfKeyword_0());
		}
		otherlv_1=LeftParenthesis
		{
			newLeafNode(otherlv_1, grammarAccess.getIfStatementAccess().getLeftParenthesisKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getIfStatementAccess().getExpressionExpressionParserRuleCall_2_0());
				}
				lv_expression_2_0=norm1_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getIfStatementRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_3=RightParenthesis
		{
			newLeafNode(otherlv_3, grammarAccess.getIfStatementAccess().getRightParenthesisKeyword_3());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getIfStatementAccess().getIfStmtStatementParserRuleCall_4_0());
				}
				lv_ifStmt_4_0=ruleStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getIfStatementRule());
					}
					set(
						$current,
						"ifStmt",
						lv_ifStmt_4_0,
						"org.eclipse.n4js.N4JS.Statement");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				(Else)=>
				otherlv_5=Else
				{
					newLeafNode(otherlv_5, grammarAccess.getIfStatementAccess().getElseKeyword_5_0());
				}
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getIfStatementAccess().getElseStmtStatementParserRuleCall_5_1_0());
					}
					lv_elseStmt_6_0=ruleStatement
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getIfStatementRule());
						}
						set(
							$current,
							"elseStmt",
							lv_elseStmt_6_0,
							"org.eclipse.n4js.N4JS.Statement");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;


// Rule IfStatement
norm1_IfStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=If
		{
			newLeafNode(otherlv_0, grammarAccess.getIfStatementAccess().getIfKeyword_0());
		}
		otherlv_1=LeftParenthesis
		{
			newLeafNode(otherlv_1, grammarAccess.getIfStatementAccess().getLeftParenthesisKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getIfStatementAccess().getExpressionExpressionParserRuleCall_2_0());
				}
				lv_expression_2_0=norm3_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getIfStatementRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_3=RightParenthesis
		{
			newLeafNode(otherlv_3, grammarAccess.getIfStatementAccess().getRightParenthesisKeyword_3());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getIfStatementAccess().getIfStmtStatementParserRuleCall_4_0());
				}
				lv_ifStmt_4_0=norm1_Statement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getIfStatementRule());
					}
					set(
						$current,
						"ifStmt",
						lv_ifStmt_4_0,
						"org.eclipse.n4js.N4JS.Statement");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				(Else)=>
				otherlv_5=Else
				{
					newLeafNode(otherlv_5, grammarAccess.getIfStatementAccess().getElseKeyword_5_0());
				}
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getIfStatementAccess().getElseStmtStatementParserRuleCall_5_1_0());
					}
					lv_elseStmt_6_0=norm1_Statement
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getIfStatementRule());
						}
						set(
							$current,
							"elseStmt",
							lv_elseStmt_6_0,
							"org.eclipse.n4js.N4JS.Statement");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;

// Entry rule entryRuleIterationStatement
entryRuleIterationStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getIterationStatementRule()); }
	iv_ruleIterationStatement=ruleIterationStatement
	{ $current=$iv_ruleIterationStatement.current; }
	EOF;

// Rule IterationStatement
ruleIterationStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getIterationStatementAccess().getDoStatementParserRuleCall_0());
		}
		this_DoStatement_0=ruleDoStatement
		{
			$current = $this_DoStatement_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getIterationStatementAccess().getWhileStatementParserRuleCall_1());
		}
		this_WhileStatement_1=ruleWhileStatement
		{
			$current = $this_WhileStatement_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getIterationStatementAccess().getForStatementParserRuleCall_2());
		}
		this_ForStatement_2=ruleForStatement
		{
			$current = $this_ForStatement_2.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule IterationStatement
norm1_IterationStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getIterationStatementAccess().getDoStatementParserRuleCall_0());
		}
		this_DoStatement_0=norm1_DoStatement
		{
			$current = $this_DoStatement_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getIterationStatementAccess().getWhileStatementParserRuleCall_1());
		}
		this_WhileStatement_1=norm1_WhileStatement
		{
			$current = $this_WhileStatement_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getIterationStatementAccess().getForStatementParserRuleCall_2());
		}
		this_ForStatement_2=norm1_ForStatement
		{
			$current = $this_ForStatement_2.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleDoStatement
entryRuleDoStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getDoStatementRule()); }
	iv_ruleDoStatement=ruleDoStatement
	{ $current=$iv_ruleDoStatement.current; }
	EOF;

// Rule DoStatement
ruleDoStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Do
		{
			newLeafNode(otherlv_0, grammarAccess.getDoStatementAccess().getDoKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getDoStatementAccess().getStatementStatementParserRuleCall_1_0());
				}
				lv_statement_1_0=ruleStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getDoStatementRule());
					}
					set(
						$current,
						"statement",
						lv_statement_1_0,
						"org.eclipse.n4js.N4JS.Statement");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_2=While
		{
			newLeafNode(otherlv_2, grammarAccess.getDoStatementAccess().getWhileKeyword_2());
		}
		otherlv_3=LeftParenthesis
		{
			newLeafNode(otherlv_3, grammarAccess.getDoStatementAccess().getLeftParenthesisKeyword_3());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getDoStatementAccess().getExpressionExpressionParserRuleCall_4_0());
				}
				lv_expression_4_0=norm1_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getDoStatementRule());
					}
					set(
						$current,
						"expression",
						lv_expression_4_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_5=RightParenthesis
		{
			newLeafNode(otherlv_5, grammarAccess.getDoStatementAccess().getRightParenthesisKeyword_5());
		}
		(
			(ruleSemi)=>
			{
				newCompositeNode(grammarAccess.getDoStatementAccess().getSemiParserRuleCall_6());
			}
			ruleSemi
			{
				afterParserOrEnumRuleCall();
			}
		)?
	)
;


// Rule DoStatement
norm1_DoStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Do
		{
			newLeafNode(otherlv_0, grammarAccess.getDoStatementAccess().getDoKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getDoStatementAccess().getStatementStatementParserRuleCall_1_0());
				}
				lv_statement_1_0=norm1_Statement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getDoStatementRule());
					}
					set(
						$current,
						"statement",
						lv_statement_1_0,
						"org.eclipse.n4js.N4JS.Statement");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_2=While
		{
			newLeafNode(otherlv_2, grammarAccess.getDoStatementAccess().getWhileKeyword_2());
		}
		otherlv_3=LeftParenthesis
		{
			newLeafNode(otherlv_3, grammarAccess.getDoStatementAccess().getLeftParenthesisKeyword_3());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getDoStatementAccess().getExpressionExpressionParserRuleCall_4_0());
				}
				lv_expression_4_0=norm3_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getDoStatementRule());
					}
					set(
						$current,
						"expression",
						lv_expression_4_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_5=RightParenthesis
		{
			newLeafNode(otherlv_5, grammarAccess.getDoStatementAccess().getRightParenthesisKeyword_5());
		}
		(
			(ruleSemi)=>
			{
				newCompositeNode(grammarAccess.getDoStatementAccess().getSemiParserRuleCall_6());
			}
			ruleSemi
			{
				afterParserOrEnumRuleCall();
			}
		)?
	)
;

// Entry rule entryRuleWhileStatement
entryRuleWhileStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getWhileStatementRule()); }
	iv_ruleWhileStatement=ruleWhileStatement
	{ $current=$iv_ruleWhileStatement.current; }
	EOF;

// Rule WhileStatement
ruleWhileStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=While
		{
			newLeafNode(otherlv_0, grammarAccess.getWhileStatementAccess().getWhileKeyword_0());
		}
		otherlv_1=LeftParenthesis
		{
			newLeafNode(otherlv_1, grammarAccess.getWhileStatementAccess().getLeftParenthesisKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getWhileStatementAccess().getExpressionExpressionParserRuleCall_2_0());
				}
				lv_expression_2_0=norm1_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getWhileStatementRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_3=RightParenthesis
		{
			newLeafNode(otherlv_3, grammarAccess.getWhileStatementAccess().getRightParenthesisKeyword_3());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getWhileStatementAccess().getStatementStatementParserRuleCall_4_0());
				}
				lv_statement_4_0=ruleStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getWhileStatementRule());
					}
					set(
						$current,
						"statement",
						lv_statement_4_0,
						"org.eclipse.n4js.N4JS.Statement");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule WhileStatement
norm1_WhileStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=While
		{
			newLeafNode(otherlv_0, grammarAccess.getWhileStatementAccess().getWhileKeyword_0());
		}
		otherlv_1=LeftParenthesis
		{
			newLeafNode(otherlv_1, grammarAccess.getWhileStatementAccess().getLeftParenthesisKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getWhileStatementAccess().getExpressionExpressionParserRuleCall_2_0());
				}
				lv_expression_2_0=norm3_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getWhileStatementRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_3=RightParenthesis
		{
			newLeafNode(otherlv_3, grammarAccess.getWhileStatementAccess().getRightParenthesisKeyword_3());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getWhileStatementAccess().getStatementStatementParserRuleCall_4_0());
				}
				lv_statement_4_0=norm1_Statement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getWhileStatementRule());
					}
					set(
						$current,
						"statement",
						lv_statement_4_0,
						"org.eclipse.n4js.N4JS.Statement");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleForStatement
entryRuleForStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getForStatementRule()); }
	iv_ruleForStatement=ruleForStatement
	{ $current=$iv_ruleForStatement.current; }
	EOF;

// Rule ForStatement
ruleForStatement returns [EObject current=null]
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
					grammarAccess.getForStatementAccess().getForStatementAction_0(),
					$current);
			}
		)
		otherlv_1=For
		{
			newLeafNode(otherlv_1, grammarAccess.getForStatementAccess().getForKeyword_1());
		}
		otherlv_2=LeftParenthesis
		{
			newLeafNode(otherlv_2, grammarAccess.getForStatementAccess().getLeftParenthesisKeyword_2());
		}
		(
			(
				((
					(
						(
							ruleLetIdentifierRef
						)
					)
					(
						(
							In
						)
					)
					(
						(
							norm1_Expression
						)
					)
					RightParenthesis
				)
				)=>
				(
					(
						(
							{
								newCompositeNode(grammarAccess.getForStatementAccess().getInitExprLetIdentifierRefParserRuleCall_3_0_0_0_0());
							}
							lv_initExpr_3_0=ruleLetIdentifierRef
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getForStatementRule());
								}
								set(
									$current,
									"initExpr",
									lv_initExpr_3_0,
									"org.eclipse.n4js.N4JS.LetIdentifierRef");
								afterParserOrEnumRuleCall();
							}
						)
					)
					(
						(
							lv_forIn_4_0=In
							{
								newLeafNode(lv_forIn_4_0, grammarAccess.getForStatementAccess().getForInInKeyword_3_0_0_1_0());
							}
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getForStatementRule());
								}
								setWithLastConsumed($current, "forIn", true, "in");
							}
						)
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getForStatementAccess().getExpressionExpressionParserRuleCall_3_0_0_2_0());
							}
							lv_expression_5_0=norm1_Expression
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getForStatementRule());
								}
								set(
									$current,
									"expression",
									lv_expression_5_0,
									"org.eclipse.n4js.N4JS.Expression");
								afterParserOrEnumRuleCall();
							}
						)
					)
					otherlv_6=RightParenthesis
					{
						newLeafNode(otherlv_6, grammarAccess.getForStatementAccess().getRightParenthesisKeyword_3_0_0_3());
					}
				)
			)
			    |
			(
				(
					(
						(
							(Var | Const | Let)=>
							(
								{
									newCompositeNode(grammarAccess.getForStatementAccess().getVarStmtKeywordVariableStatementKeywordEnumRuleCall_3_1_0_0_0_0());
								}
								lv_varStmtKeyword_7_0=ruleVariableStatementKeyword
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getForStatementRule());
									}
									set(
										$current,
										"varStmtKeyword",
										lv_varStmtKeyword_7_0,
										"org.eclipse.n4js.N4JS.VariableStatementKeyword");
									afterParserOrEnumRuleCall();
								}
							)
						)
						(
							(
								((
									(
										(
											ruleBindingIdentifierAsVariableDeclaration
										)
									)
									(
										(
											(
												In
											)
										)
										    |
										(
											(
												Of
											)
										)
									)
									(
										Await
										    |
										CommercialAt
										    |
										LeftParenthesis
										    |
										Async
										    |
										Yield
										    |
										Get
										    |
										Set
										    |
										Let
										    |
										Project
										    |
										External
										    |
										Abstract
										    |
										Static
										    |
										As
										    |
										From
										    |
										Constructor
										    |
										Of
										    |
										Target
										    |
										Type
										    |
										Union
										    |
										Intersection
										    |
										This
										    |
										Promisify
										    |
										Implements
										    |
										Interface
										    |
										Private
										    |
										Protected
										    |
										Public
										    |
										Out
										    |
										New
										    |
										This_1
										    |
										Super
										    |
										LessThanSign
										    |
										True
										    |
										False
										    |
										Null
										    |
										Solidus
										    |
										SolidusEqualsSign
										    |
										LeftSquareBracket
										    |
										LeftCurlyBracket
										    |
										Function
										    |
										Class
										    |
										Delete
										    |
										Void
										    |
										Typeof
										    |
										PlusSignPlusSign
										    |
										HyphenMinusHyphenMinus
										    |
										PlusSign
										    |
										HyphenMinus
										    |
										Tilde
										    |
										ExclamationMark
										    |
										RULE_IDENTIFIER
										    |
										RULE_DOUBLE
										    |
										RULE_INT
										    |
										RULE_BINARY_INT
										    |
										RULE_OCTAL_INT
										    |
										RULE_LEGACY_OCTAL_INT
										    |
										RULE_HEX_INT
										    |
										RULE_SCIENTIFIC_INT
										    |
										RULE_STRING
										    |
										RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
										    |
										RULE_TEMPLATE_HEAD
									)?
								)
								)=>
								(
									(
										(
											{
												newCompositeNode(grammarAccess.getForStatementAccess().getVarDeclsOrBindingsBindingIdentifierAsVariableDeclarationParserRuleCall_3_1_0_0_1_0_0_0_0());
											}
											lv_varDeclsOrBindings_8_0=ruleBindingIdentifierAsVariableDeclaration
											{
												if ($current==null) {
													$current = createModelElementForParent(grammarAccess.getForStatementRule());
												}
												add(
													$current,
													"varDeclsOrBindings",
													lv_varDeclsOrBindings_8_0,
													"org.eclipse.n4js.N4JS.BindingIdentifierAsVariableDeclaration");
												afterParserOrEnumRuleCall();
											}
										)
									)
									(
										(
											(
												lv_forIn_9_0=In
												{
													newLeafNode(lv_forIn_9_0, grammarAccess.getForStatementAccess().getForInInKeyword_3_1_0_0_1_0_0_1_0_0());
												}
												{
													if ($current==null) {
														$current = createModelElement(grammarAccess.getForStatementRule());
													}
													setWithLastConsumed($current, "forIn", true, "in");
												}
											)
										)
										    |
										(
											(
												lv_forOf_10_0=Of
												{
													newLeafNode(lv_forOf_10_0, grammarAccess.getForStatementAccess().getForOfOfKeyword_3_1_0_0_1_0_0_1_1_0());
												}
												{
													if ($current==null) {
														$current = createModelElement(grammarAccess.getForStatementRule());
													}
													setWithLastConsumed($current, "forOf", true, "of");
												}
											)
										)
									)
									(
										(Await | CommercialAt | LeftParenthesis | Async | Yield | Get | Set | Let | Project | External | Abstract | Static | As | From | Constructor | Of | Target | Type | Union | Intersection | This | Promisify | Implements | Interface | Private | Protected | Public | Out | New | This_1 | Super | LessThanSign | True | False | Null | Solidus | SolidusEqualsSign | LeftSquareBracket | LeftCurlyBracket | Function | Class | Delete | Void | Typeof | PlusSignPlusSign | HyphenMinusHyphenMinus | PlusSign | HyphenMinus | Tilde | ExclamationMark | RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
										(
											{
												newCompositeNode(grammarAccess.getForStatementAccess().getExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_0_0_2_0());
											}
											lv_expression_11_0=norm1_AssignmentExpression
											{
												if ($current==null) {
													$current = createModelElementForParent(grammarAccess.getForStatementRule());
												}
												set(
													$current,
													"expression",
													lv_expression_11_0,
													"org.eclipse.n4js.N4JS.AssignmentExpression");
												afterParserOrEnumRuleCall();
											}
										)
									)?
								)
							)
							    |
							(
								(
									(
										{
											newCompositeNode(grammarAccess.getForStatementAccess().getVarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_0_0());
										}
										lv_varDeclsOrBindings_12_0=norm4_VariableDeclarationOrBinding
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getForStatementRule());
											}
											add(
												$current,
												"varDeclsOrBindings",
												lv_varDeclsOrBindings_12_0,
												"org.eclipse.n4js.N4JS.VariableDeclarationOrBinding");
											afterParserOrEnumRuleCall();
										}
									)
								)
								(
									(
										(
											otherlv_13=Comma
											{
												newLeafNode(otherlv_13, grammarAccess.getForStatementAccess().getCommaKeyword_3_1_0_0_1_1_1_0_0_0());
											}
											(
												(
													{
														newCompositeNode(grammarAccess.getForStatementAccess().getVarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_1_0_0_1_0());
													}
													lv_varDeclsOrBindings_14_0=ruleVariableDeclarationOrBinding
													{
														if ($current==null) {
															$current = createModelElementForParent(grammarAccess.getForStatementRule());
														}
														add(
															$current,
															"varDeclsOrBindings",
															lv_varDeclsOrBindings_14_0,
															"org.eclipse.n4js.N4JS.VariableDeclarationOrBinding");
														afterParserOrEnumRuleCall();
													}
												)
											)
										)*
										otherlv_15=Semicolon
										{
											newLeafNode(otherlv_15, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_0_1_1_1_0_1());
										}
										(
											(
												{
													newCompositeNode(grammarAccess.getForStatementAccess().getExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_0_2_0());
												}
												lv_expression_16_0=norm1_Expression
												{
													if ($current==null) {
														$current = createModelElementForParent(grammarAccess.getForStatementRule());
													}
													set(
														$current,
														"expression",
														lv_expression_16_0,
														"org.eclipse.n4js.N4JS.Expression");
													afterParserOrEnumRuleCall();
												}
											)
										)?
										otherlv_17=Semicolon
										{
											newLeafNode(otherlv_17, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_0_1_1_1_0_3());
										}
										(
											(
												{
													newCompositeNode(grammarAccess.getForStatementAccess().getUpdateExprExpressionParserRuleCall_3_1_0_0_1_1_1_0_4_0());
												}
												lv_updateExpr_18_0=norm1_Expression
												{
													if ($current==null) {
														$current = createModelElementForParent(grammarAccess.getForStatementRule());
													}
													set(
														$current,
														"updateExpr",
														lv_updateExpr_18_0,
														"org.eclipse.n4js.N4JS.Expression");
													afterParserOrEnumRuleCall();
												}
											)
										)?
									)
									    |
									(
										(
											(
												lv_forIn_19_0=In
												{
													newLeafNode(lv_forIn_19_0, grammarAccess.getForStatementAccess().getForInInKeyword_3_1_0_0_1_1_1_1_0_0());
												}
												{
													if ($current==null) {
														$current = createModelElement(grammarAccess.getForStatementRule());
													}
													setWithLastConsumed($current, "forIn", true, "in");
												}
											)
										)
										(
											(
												{
													newCompositeNode(grammarAccess.getForStatementAccess().getExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_1_1_0());
												}
												lv_expression_20_0=norm1_Expression
												{
													if ($current==null) {
														$current = createModelElementForParent(grammarAccess.getForStatementRule());
													}
													set(
														$current,
														"expression",
														lv_expression_20_0,
														"org.eclipse.n4js.N4JS.Expression");
													afterParserOrEnumRuleCall();
												}
											)
										)?
									)
									    |
									(
										(
											(
												lv_forOf_21_0=Of
												{
													newLeafNode(lv_forOf_21_0, grammarAccess.getForStatementAccess().getForOfOfKeyword_3_1_0_0_1_1_1_2_0_0());
												}
												{
													if ($current==null) {
														$current = createModelElement(grammarAccess.getForStatementRule());
													}
													setWithLastConsumed($current, "forOf", true, "of");
												}
											)
										)
										(
											(
												{
													newCompositeNode(grammarAccess.getForStatementAccess().getExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_1_1_2_1_0());
												}
												lv_expression_22_0=norm1_AssignmentExpression
												{
													if ($current==null) {
														$current = createModelElementForParent(grammarAccess.getForStatementRule());
													}
													set(
														$current,
														"expression",
														lv_expression_22_0,
														"org.eclipse.n4js.N4JS.AssignmentExpression");
													afterParserOrEnumRuleCall();
												}
											)
										)?
									)
								)
							)
						)
					)
					    |
					(
						(
							(
								{
									newCompositeNode(grammarAccess.getForStatementAccess().getInitExprExpressionParserRuleCall_3_1_0_1_0_0());
								}
								lv_initExpr_23_0=ruleExpression
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getForStatementRule());
									}
									set(
										$current,
										"initExpr",
										lv_initExpr_23_0,
										"org.eclipse.n4js.N4JS.Expression");
									afterParserOrEnumRuleCall();
								}
							)
						)
						(
							(
								otherlv_24=Semicolon
								{
									newLeafNode(otherlv_24, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_1_1_0_0());
								}
								(
									(
										{
											newCompositeNode(grammarAccess.getForStatementAccess().getExpressionExpressionParserRuleCall_3_1_0_1_1_0_1_0());
										}
										lv_expression_25_0=norm1_Expression
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getForStatementRule());
											}
											set(
												$current,
												"expression",
												lv_expression_25_0,
												"org.eclipse.n4js.N4JS.Expression");
											afterParserOrEnumRuleCall();
										}
									)
								)?
								otherlv_26=Semicolon
								{
									newLeafNode(otherlv_26, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_1_1_0_2());
								}
								(
									(
										{
											newCompositeNode(grammarAccess.getForStatementAccess().getUpdateExprExpressionParserRuleCall_3_1_0_1_1_0_3_0());
										}
										lv_updateExpr_27_0=norm1_Expression
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getForStatementRule());
											}
											set(
												$current,
												"updateExpr",
												lv_updateExpr_27_0,
												"org.eclipse.n4js.N4JS.Expression");
											afterParserOrEnumRuleCall();
										}
									)
								)?
							)
							    |
							(
								(
									(
										lv_forIn_28_0=In
										{
											newLeafNode(lv_forIn_28_0, grammarAccess.getForStatementAccess().getForInInKeyword_3_1_0_1_1_1_0_0());
										}
										{
											if ($current==null) {
												$current = createModelElement(grammarAccess.getForStatementRule());
											}
											setWithLastConsumed($current, "forIn", true, "in");
										}
									)
								)
								(
									(
										{
											newCompositeNode(grammarAccess.getForStatementAccess().getExpressionExpressionParserRuleCall_3_1_0_1_1_1_1_0());
										}
										lv_expression_29_0=norm1_Expression
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getForStatementRule());
											}
											set(
												$current,
												"expression",
												lv_expression_29_0,
												"org.eclipse.n4js.N4JS.Expression");
											afterParserOrEnumRuleCall();
										}
									)
								)?
							)
							    |
							(
								(
									(
										lv_forOf_30_0=Of
										{
											newLeafNode(lv_forOf_30_0, grammarAccess.getForStatementAccess().getForOfOfKeyword_3_1_0_1_1_2_0_0());
										}
										{
											if ($current==null) {
												$current = createModelElement(grammarAccess.getForStatementRule());
											}
											setWithLastConsumed($current, "forOf", true, "of");
										}
									)
								)
								(
									(
										{
											newCompositeNode(grammarAccess.getForStatementAccess().getExpressionAssignmentExpressionParserRuleCall_3_1_0_1_1_2_1_0());
										}
										lv_expression_31_0=norm1_AssignmentExpression
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getForStatementRule());
											}
											set(
												$current,
												"expression",
												lv_expression_31_0,
												"org.eclipse.n4js.N4JS.AssignmentExpression");
											afterParserOrEnumRuleCall();
										}
									)
								)?
							)
						)
					)
					    |
					(
						otherlv_32=Semicolon
						{
							newLeafNode(otherlv_32, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_2_0());
						}
						(
							(
								{
									newCompositeNode(grammarAccess.getForStatementAccess().getExpressionExpressionParserRuleCall_3_1_0_2_1_0());
								}
								lv_expression_33_0=norm1_Expression
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getForStatementRule());
									}
									set(
										$current,
										"expression",
										lv_expression_33_0,
										"org.eclipse.n4js.N4JS.Expression");
									afterParserOrEnumRuleCall();
								}
							)
						)?
						otherlv_34=Semicolon
						{
							newLeafNode(otherlv_34, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_2_2());
						}
						(
							(
								{
									newCompositeNode(grammarAccess.getForStatementAccess().getUpdateExprExpressionParserRuleCall_3_1_0_2_3_0());
								}
								lv_updateExpr_35_0=norm1_Expression
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getForStatementRule());
									}
									set(
										$current,
										"updateExpr",
										lv_updateExpr_35_0,
										"org.eclipse.n4js.N4JS.Expression");
									afterParserOrEnumRuleCall();
								}
							)
						)?
					)
				)
				otherlv_36=RightParenthesis
				{
					newLeafNode(otherlv_36, grammarAccess.getForStatementAccess().getRightParenthesisKeyword_3_1_1());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getForStatementAccess().getStatementStatementParserRuleCall_4_0());
				}
				lv_statement_37_0=ruleStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getForStatementRule());
					}
					set(
						$current,
						"statement",
						lv_statement_37_0,
						"org.eclipse.n4js.N4JS.Statement");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule ForStatement
norm1_ForStatement returns [EObject current=null]
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
					grammarAccess.getForStatementAccess().getForStatementAction_0(),
					$current);
			}
		)
		otherlv_1=For
		{
			newLeafNode(otherlv_1, grammarAccess.getForStatementAccess().getForKeyword_1());
		}
		otherlv_2=LeftParenthesis
		{
			newLeafNode(otherlv_2, grammarAccess.getForStatementAccess().getLeftParenthesisKeyword_2());
		}
		(
			(
				((
					(
						(
							ruleLetIdentifierRef
						)
					)
					(
						(
							In
						)
					)
					(
						(
							norm3_Expression
						)
					)
					RightParenthesis
				)
				)=>
				(
					(
						(
							{
								newCompositeNode(grammarAccess.getForStatementAccess().getInitExprLetIdentifierRefParserRuleCall_3_0_0_0_0());
							}
							lv_initExpr_3_0=ruleLetIdentifierRef
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getForStatementRule());
								}
								set(
									$current,
									"initExpr",
									lv_initExpr_3_0,
									"org.eclipse.n4js.N4JS.LetIdentifierRef");
								afterParserOrEnumRuleCall();
							}
						)
					)
					(
						(
							lv_forIn_4_0=In
							{
								newLeafNode(lv_forIn_4_0, grammarAccess.getForStatementAccess().getForInInKeyword_3_0_0_1_0());
							}
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getForStatementRule());
								}
								setWithLastConsumed($current, "forIn", true, "in");
							}
						)
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getForStatementAccess().getExpressionExpressionParserRuleCall_3_0_0_2_0());
							}
							lv_expression_5_0=norm3_Expression
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getForStatementRule());
								}
								set(
									$current,
									"expression",
									lv_expression_5_0,
									"org.eclipse.n4js.N4JS.Expression");
								afterParserOrEnumRuleCall();
							}
						)
					)
					otherlv_6=RightParenthesis
					{
						newLeafNode(otherlv_6, grammarAccess.getForStatementAccess().getRightParenthesisKeyword_3_0_0_3());
					}
				)
			)
			    |
			(
				(
					(
						(
							(Var | Const | Let)=>
							(
								{
									newCompositeNode(grammarAccess.getForStatementAccess().getVarStmtKeywordVariableStatementKeywordEnumRuleCall_3_1_0_0_0_0());
								}
								lv_varStmtKeyword_7_0=ruleVariableStatementKeyword
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getForStatementRule());
									}
									set(
										$current,
										"varStmtKeyword",
										lv_varStmtKeyword_7_0,
										"org.eclipse.n4js.N4JS.VariableStatementKeyword");
									afterParserOrEnumRuleCall();
								}
							)
						)
						(
							(
								((
									(
										(
											norm2_BindingIdentifierAsVariableDeclaration
										)
									)
									(
										(
											(
												In
											)
										)
										    |
										(
											(
												Of
											)
										)
									)
									(
										Await
										    |
										CommercialAt
										    |
										LeftParenthesis
										    |
										Async
										    |
										Get
										    |
										Set
										    |
										Let
										    |
										Project
										    |
										External
										    |
										Abstract
										    |
										Static
										    |
										As
										    |
										From
										    |
										Constructor
										    |
										Of
										    |
										Target
										    |
										Type
										    |
										Union
										    |
										Intersection
										    |
										This
										    |
										Promisify
										    |
										Implements
										    |
										Interface
										    |
										Private
										    |
										Protected
										    |
										Public
										    |
										Out
										    |
										Yield
										    |
										New
										    |
										This_1
										    |
										Super
										    |
										LessThanSign
										    |
										True
										    |
										False
										    |
										Null
										    |
										Solidus
										    |
										SolidusEqualsSign
										    |
										LeftSquareBracket
										    |
										LeftCurlyBracket
										    |
										Function
										    |
										Class
										    |
										Delete
										    |
										Void
										    |
										Typeof
										    |
										PlusSignPlusSign
										    |
										HyphenMinusHyphenMinus
										    |
										PlusSign
										    |
										HyphenMinus
										    |
										Tilde
										    |
										ExclamationMark
										    |
										RULE_IDENTIFIER
										    |
										RULE_DOUBLE
										    |
										RULE_INT
										    |
										RULE_BINARY_INT
										    |
										RULE_OCTAL_INT
										    |
										RULE_LEGACY_OCTAL_INT
										    |
										RULE_HEX_INT
										    |
										RULE_SCIENTIFIC_INT
										    |
										RULE_STRING
										    |
										RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
										    |
										RULE_TEMPLATE_HEAD
									)?
								)
								)=>
								(
									(
										(
											{
												newCompositeNode(grammarAccess.getForStatementAccess().getVarDeclsOrBindingsBindingIdentifierAsVariableDeclarationParserRuleCall_3_1_0_0_1_0_0_0_0());
											}
											lv_varDeclsOrBindings_8_0=norm2_BindingIdentifierAsVariableDeclaration
											{
												if ($current==null) {
													$current = createModelElementForParent(grammarAccess.getForStatementRule());
												}
												add(
													$current,
													"varDeclsOrBindings",
													lv_varDeclsOrBindings_8_0,
													"org.eclipse.n4js.N4JS.BindingIdentifierAsVariableDeclaration");
												afterParserOrEnumRuleCall();
											}
										)
									)
									(
										(
											(
												lv_forIn_9_0=In
												{
													newLeafNode(lv_forIn_9_0, grammarAccess.getForStatementAccess().getForInInKeyword_3_1_0_0_1_0_0_1_0_0());
												}
												{
													if ($current==null) {
														$current = createModelElement(grammarAccess.getForStatementRule());
													}
													setWithLastConsumed($current, "forIn", true, "in");
												}
											)
										)
										    |
										(
											(
												lv_forOf_10_0=Of
												{
													newLeafNode(lv_forOf_10_0, grammarAccess.getForStatementAccess().getForOfOfKeyword_3_1_0_0_1_0_0_1_1_0());
												}
												{
													if ($current==null) {
														$current = createModelElement(grammarAccess.getForStatementRule());
													}
													setWithLastConsumed($current, "forOf", true, "of");
												}
											)
										)
									)
									(
										(Await | CommercialAt | LeftParenthesis | Async | Get | Set | Let | Project | External | Abstract | Static | As | From | Constructor | Of | Target | Type | Union | Intersection | This | Promisify | Implements | Interface | Private | Protected | Public | Out | Yield | New | This_1 | Super | LessThanSign | True | False | Null | Solidus | SolidusEqualsSign | LeftSquareBracket | LeftCurlyBracket | Function | Class | Delete | Void | Typeof | PlusSignPlusSign | HyphenMinusHyphenMinus | PlusSign | HyphenMinus | Tilde | ExclamationMark | RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
										(
											{
												newCompositeNode(grammarAccess.getForStatementAccess().getExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_0_0_2_0());
											}
											lv_expression_11_0=norm3_AssignmentExpression
											{
												if ($current==null) {
													$current = createModelElementForParent(grammarAccess.getForStatementRule());
												}
												set(
													$current,
													"expression",
													lv_expression_11_0,
													"org.eclipse.n4js.N4JS.AssignmentExpression");
												afterParserOrEnumRuleCall();
											}
										)
									)?
								)
							)
							    |
							(
								(
									(
										{
											newCompositeNode(grammarAccess.getForStatementAccess().getVarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_0_0());
										}
										lv_varDeclsOrBindings_12_0=norm6_VariableDeclarationOrBinding
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getForStatementRule());
											}
											add(
												$current,
												"varDeclsOrBindings",
												lv_varDeclsOrBindings_12_0,
												"org.eclipse.n4js.N4JS.VariableDeclarationOrBinding");
											afterParserOrEnumRuleCall();
										}
									)
								)
								(
									(
										(
											otherlv_13=Comma
											{
												newLeafNode(otherlv_13, grammarAccess.getForStatementAccess().getCommaKeyword_3_1_0_0_1_1_1_0_0_0());
											}
											(
												(
													{
														newCompositeNode(grammarAccess.getForStatementAccess().getVarDeclsOrBindingsVariableDeclarationOrBindingParserRuleCall_3_1_0_0_1_1_1_0_0_1_0());
													}
													lv_varDeclsOrBindings_14_0=norm2_VariableDeclarationOrBinding
													{
														if ($current==null) {
															$current = createModelElementForParent(grammarAccess.getForStatementRule());
														}
														add(
															$current,
															"varDeclsOrBindings",
															lv_varDeclsOrBindings_14_0,
															"org.eclipse.n4js.N4JS.VariableDeclarationOrBinding");
														afterParserOrEnumRuleCall();
													}
												)
											)
										)*
										otherlv_15=Semicolon
										{
											newLeafNode(otherlv_15, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_0_1_1_1_0_1());
										}
										(
											(
												{
													newCompositeNode(grammarAccess.getForStatementAccess().getExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_0_2_0());
												}
												lv_expression_16_0=norm3_Expression
												{
													if ($current==null) {
														$current = createModelElementForParent(grammarAccess.getForStatementRule());
													}
													set(
														$current,
														"expression",
														lv_expression_16_0,
														"org.eclipse.n4js.N4JS.Expression");
													afterParserOrEnumRuleCall();
												}
											)
										)?
										otherlv_17=Semicolon
										{
											newLeafNode(otherlv_17, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_0_1_1_1_0_3());
										}
										(
											(
												{
													newCompositeNode(grammarAccess.getForStatementAccess().getUpdateExprExpressionParserRuleCall_3_1_0_0_1_1_1_0_4_0());
												}
												lv_updateExpr_18_0=norm3_Expression
												{
													if ($current==null) {
														$current = createModelElementForParent(grammarAccess.getForStatementRule());
													}
													set(
														$current,
														"updateExpr",
														lv_updateExpr_18_0,
														"org.eclipse.n4js.N4JS.Expression");
													afterParserOrEnumRuleCall();
												}
											)
										)?
									)
									    |
									(
										(
											(
												lv_forIn_19_0=In
												{
													newLeafNode(lv_forIn_19_0, grammarAccess.getForStatementAccess().getForInInKeyword_3_1_0_0_1_1_1_1_0_0());
												}
												{
													if ($current==null) {
														$current = createModelElement(grammarAccess.getForStatementRule());
													}
													setWithLastConsumed($current, "forIn", true, "in");
												}
											)
										)
										(
											(
												{
													newCompositeNode(grammarAccess.getForStatementAccess().getExpressionExpressionParserRuleCall_3_1_0_0_1_1_1_1_1_0());
												}
												lv_expression_20_0=norm3_Expression
												{
													if ($current==null) {
														$current = createModelElementForParent(grammarAccess.getForStatementRule());
													}
													set(
														$current,
														"expression",
														lv_expression_20_0,
														"org.eclipse.n4js.N4JS.Expression");
													afterParserOrEnumRuleCall();
												}
											)
										)?
									)
									    |
									(
										(
											(
												lv_forOf_21_0=Of
												{
													newLeafNode(lv_forOf_21_0, grammarAccess.getForStatementAccess().getForOfOfKeyword_3_1_0_0_1_1_1_2_0_0());
												}
												{
													if ($current==null) {
														$current = createModelElement(grammarAccess.getForStatementRule());
													}
													setWithLastConsumed($current, "forOf", true, "of");
												}
											)
										)
										(
											(
												{
													newCompositeNode(grammarAccess.getForStatementAccess().getExpressionAssignmentExpressionParserRuleCall_3_1_0_0_1_1_1_2_1_0());
												}
												lv_expression_22_0=norm3_AssignmentExpression
												{
													if ($current==null) {
														$current = createModelElementForParent(grammarAccess.getForStatementRule());
													}
													set(
														$current,
														"expression",
														lv_expression_22_0,
														"org.eclipse.n4js.N4JS.AssignmentExpression");
													afterParserOrEnumRuleCall();
												}
											)
										)?
									)
								)
							)
						)
					)
					    |
					(
						(
							(
								{
									newCompositeNode(grammarAccess.getForStatementAccess().getInitExprExpressionParserRuleCall_3_1_0_1_0_0());
								}
								lv_initExpr_23_0=norm2_Expression
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getForStatementRule());
									}
									set(
										$current,
										"initExpr",
										lv_initExpr_23_0,
										"org.eclipse.n4js.N4JS.Expression");
									afterParserOrEnumRuleCall();
								}
							)
						)
						(
							(
								otherlv_24=Semicolon
								{
									newLeafNode(otherlv_24, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_1_1_0_0());
								}
								(
									(
										{
											newCompositeNode(grammarAccess.getForStatementAccess().getExpressionExpressionParserRuleCall_3_1_0_1_1_0_1_0());
										}
										lv_expression_25_0=norm3_Expression
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getForStatementRule());
											}
											set(
												$current,
												"expression",
												lv_expression_25_0,
												"org.eclipse.n4js.N4JS.Expression");
											afterParserOrEnumRuleCall();
										}
									)
								)?
								otherlv_26=Semicolon
								{
									newLeafNode(otherlv_26, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_1_1_0_2());
								}
								(
									(
										{
											newCompositeNode(grammarAccess.getForStatementAccess().getUpdateExprExpressionParserRuleCall_3_1_0_1_1_0_3_0());
										}
										lv_updateExpr_27_0=norm3_Expression
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getForStatementRule());
											}
											set(
												$current,
												"updateExpr",
												lv_updateExpr_27_0,
												"org.eclipse.n4js.N4JS.Expression");
											afterParserOrEnumRuleCall();
										}
									)
								)?
							)
							    |
							(
								(
									(
										lv_forIn_28_0=In
										{
											newLeafNode(lv_forIn_28_0, grammarAccess.getForStatementAccess().getForInInKeyword_3_1_0_1_1_1_0_0());
										}
										{
											if ($current==null) {
												$current = createModelElement(grammarAccess.getForStatementRule());
											}
											setWithLastConsumed($current, "forIn", true, "in");
										}
									)
								)
								(
									(
										{
											newCompositeNode(grammarAccess.getForStatementAccess().getExpressionExpressionParserRuleCall_3_1_0_1_1_1_1_0());
										}
										lv_expression_29_0=norm3_Expression
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getForStatementRule());
											}
											set(
												$current,
												"expression",
												lv_expression_29_0,
												"org.eclipse.n4js.N4JS.Expression");
											afterParserOrEnumRuleCall();
										}
									)
								)?
							)
							    |
							(
								(
									(
										lv_forOf_30_0=Of
										{
											newLeafNode(lv_forOf_30_0, grammarAccess.getForStatementAccess().getForOfOfKeyword_3_1_0_1_1_2_0_0());
										}
										{
											if ($current==null) {
												$current = createModelElement(grammarAccess.getForStatementRule());
											}
											setWithLastConsumed($current, "forOf", true, "of");
										}
									)
								)
								(
									(
										{
											newCompositeNode(grammarAccess.getForStatementAccess().getExpressionAssignmentExpressionParserRuleCall_3_1_0_1_1_2_1_0());
										}
										lv_expression_31_0=norm3_AssignmentExpression
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getForStatementRule());
											}
											set(
												$current,
												"expression",
												lv_expression_31_0,
												"org.eclipse.n4js.N4JS.AssignmentExpression");
											afterParserOrEnumRuleCall();
										}
									)
								)?
							)
						)
					)
					    |
					(
						otherlv_32=Semicolon
						{
							newLeafNode(otherlv_32, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_2_0());
						}
						(
							(
								{
									newCompositeNode(grammarAccess.getForStatementAccess().getExpressionExpressionParserRuleCall_3_1_0_2_1_0());
								}
								lv_expression_33_0=norm3_Expression
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getForStatementRule());
									}
									set(
										$current,
										"expression",
										lv_expression_33_0,
										"org.eclipse.n4js.N4JS.Expression");
									afterParserOrEnumRuleCall();
								}
							)
						)?
						otherlv_34=Semicolon
						{
							newLeafNode(otherlv_34, grammarAccess.getForStatementAccess().getSemicolonKeyword_3_1_0_2_2());
						}
						(
							(
								{
									newCompositeNode(grammarAccess.getForStatementAccess().getUpdateExprExpressionParserRuleCall_3_1_0_2_3_0());
								}
								lv_updateExpr_35_0=norm3_Expression
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getForStatementRule());
									}
									set(
										$current,
										"updateExpr",
										lv_updateExpr_35_0,
										"org.eclipse.n4js.N4JS.Expression");
									afterParserOrEnumRuleCall();
								}
							)
						)?
					)
				)
				otherlv_36=RightParenthesis
				{
					newLeafNode(otherlv_36, grammarAccess.getForStatementAccess().getRightParenthesisKeyword_3_1_1());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getForStatementAccess().getStatementStatementParserRuleCall_4_0());
				}
				lv_statement_37_0=norm1_Statement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getForStatementRule());
					}
					set(
						$current,
						"statement",
						lv_statement_37_0,
						"org.eclipse.n4js.N4JS.Statement");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleLetIdentifierRef
entryRuleLetIdentifierRef returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getLetIdentifierRefRule()); }
	iv_ruleLetIdentifierRef=ruleLetIdentifierRef
	{ $current=$iv_ruleLetIdentifierRef.current; }
	EOF;

// Rule LetIdentifierRef
ruleLetIdentifierRef returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getLetIdentifierRefRule());
				}
			}
			{
				newCompositeNode(grammarAccess.getLetIdentifierRefAccess().getIdIdentifiableElementCrossReference_0());
			}
			ruleLetAsIdentifier
			{
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleLetAsIdentifier
entryRuleLetAsIdentifier returns [String current=null]:
	{ newCompositeNode(grammarAccess.getLetAsIdentifierRule()); }
	iv_ruleLetAsIdentifier=ruleLetAsIdentifier
	{ $current=$iv_ruleLetAsIdentifier.current.getText(); }
	EOF;

// Rule LetAsIdentifier
ruleLetAsIdentifier returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	kw=Let
	{
		$current.merge(kw);
		newLeafNode(kw, grammarAccess.getLetAsIdentifierAccess().getLetKeyword());
	}
;

// Entry rule entryRuleBindingIdentifierAsVariableDeclaration
entryRuleBindingIdentifierAsVariableDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getBindingIdentifierAsVariableDeclarationRule()); }
	iv_ruleBindingIdentifierAsVariableDeclaration=ruleBindingIdentifierAsVariableDeclaration
	{ $current=$iv_ruleBindingIdentifierAsVariableDeclaration.current; }
	EOF;

// Rule BindingIdentifierAsVariableDeclaration
ruleBindingIdentifierAsVariableDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getBindingIdentifierAsVariableDeclarationAccess().getNameBindingIdentifierParserRuleCall_0());
			}
			lv_name_0_0=ruleBindingIdentifier
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getBindingIdentifierAsVariableDeclarationRule());
				}
				set(
					$current,
					"name",
					lv_name_0_0,
					"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
				afterParserOrEnumRuleCall();
			}
		)
	)
;


// Rule BindingIdentifierAsVariableDeclaration
norm2_BindingIdentifierAsVariableDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getBindingIdentifierAsVariableDeclarationAccess().getNameBindingIdentifierParserRuleCall_0());
			}
			lv_name_0_0=norm1_BindingIdentifier
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getBindingIdentifierAsVariableDeclarationRule());
				}
				set(
					$current,
					"name",
					lv_name_0_0,
					"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleContinueStatement
entryRuleContinueStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getContinueStatementRule()); }
	iv_ruleContinueStatement=ruleContinueStatement
	{ $current=$iv_ruleContinueStatement.current; }
	EOF;

// Rule ContinueStatement
ruleContinueStatement returns [EObject current=null]
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
					grammarAccess.getContinueStatementAccess().getContinueStatementAction_0(),
					$current);
			}
		)
		otherlv_1=Continue { promoteEOL(); }
		{
			newLeafNode(otherlv_1, grammarAccess.getContinueStatementAccess().getContinueKeyword_1());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getContinueStatementRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getContinueStatementAccess().getLabelLabelledStatementCrossReference_2_0());
				}
				ruleBindingIdentifier
				{
					afterParserOrEnumRuleCall();
				}
			)
		)?
		{
			newCompositeNode(grammarAccess.getContinueStatementAccess().getSemiParserRuleCall_3());
		}
		ruleSemi
		{
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule ContinueStatement
norm1_ContinueStatement returns [EObject current=null]
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
					grammarAccess.getContinueStatementAccess().getContinueStatementAction_0(),
					$current);
			}
		)
		otherlv_1=Continue { promoteEOL(); }
		{
			newLeafNode(otherlv_1, grammarAccess.getContinueStatementAccess().getContinueKeyword_1());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getContinueStatementRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getContinueStatementAccess().getLabelLabelledStatementCrossReference_2_0());
				}
				norm1_BindingIdentifier
				{
					afterParserOrEnumRuleCall();
				}
			)
		)?
		{
			newCompositeNode(grammarAccess.getContinueStatementAccess().getSemiParserRuleCall_3());
		}
		ruleSemi
		{
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleBreakStatement
entryRuleBreakStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getBreakStatementRule()); }
	iv_ruleBreakStatement=ruleBreakStatement
	{ $current=$iv_ruleBreakStatement.current; }
	EOF;

// Rule BreakStatement
ruleBreakStatement returns [EObject current=null]
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
					grammarAccess.getBreakStatementAccess().getBreakStatementAction_0(),
					$current);
			}
		)
		otherlv_1=Break { promoteEOL(); }
		{
			newLeafNode(otherlv_1, grammarAccess.getBreakStatementAccess().getBreakKeyword_1());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getBreakStatementRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getBreakStatementAccess().getLabelLabelledStatementCrossReference_2_0());
				}
				ruleBindingIdentifier
				{
					afterParserOrEnumRuleCall();
				}
			)
		)?
		{
			newCompositeNode(grammarAccess.getBreakStatementAccess().getSemiParserRuleCall_3());
		}
		ruleSemi
		{
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule BreakStatement
norm1_BreakStatement returns [EObject current=null]
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
					grammarAccess.getBreakStatementAccess().getBreakStatementAction_0(),
					$current);
			}
		)
		otherlv_1=Break { promoteEOL(); }
		{
			newLeafNode(otherlv_1, grammarAccess.getBreakStatementAccess().getBreakKeyword_1());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getBreakStatementRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getBreakStatementAccess().getLabelLabelledStatementCrossReference_2_0());
				}
				norm1_BindingIdentifier
				{
					afterParserOrEnumRuleCall();
				}
			)
		)?
		{
			newCompositeNode(grammarAccess.getBreakStatementAccess().getSemiParserRuleCall_3());
		}
		ruleSemi
		{
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleReturnStatement
entryRuleReturnStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getReturnStatementRule()); }
	iv_ruleReturnStatement=ruleReturnStatement
	{ $current=$iv_ruleReturnStatement.current; }
	EOF;

// Rule ReturnStatement
ruleReturnStatement returns [EObject current=null]
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
					grammarAccess.getReturnStatementAccess().getReturnStatementAction_0(),
					$current);
			}
		)
		otherlv_1=Return { promoteEOL(); }
		{
			newLeafNode(otherlv_1, grammarAccess.getReturnStatementAccess().getReturnKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getReturnStatementAccess().getExpressionExpressionParserRuleCall_2_0());
				}
				lv_expression_2_0=norm1_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getReturnStatementRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		{
			newCompositeNode(grammarAccess.getReturnStatementAccess().getSemiParserRuleCall_3());
		}
		ruleSemi
		{
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule ReturnStatement
norm1_ReturnStatement returns [EObject current=null]
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
					grammarAccess.getReturnStatementAccess().getReturnStatementAction_0(),
					$current);
			}
		)
		otherlv_1=Return { promoteEOL(); }
		{
			newLeafNode(otherlv_1, grammarAccess.getReturnStatementAccess().getReturnKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getReturnStatementAccess().getExpressionExpressionParserRuleCall_2_0());
				}
				lv_expression_2_0=norm3_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getReturnStatementRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		{
			newCompositeNode(grammarAccess.getReturnStatementAccess().getSemiParserRuleCall_3());
		}
		ruleSemi
		{
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleWithStatement
entryRuleWithStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getWithStatementRule()); }
	iv_ruleWithStatement=ruleWithStatement
	{ $current=$iv_ruleWithStatement.current; }
	EOF;

// Rule WithStatement
ruleWithStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=With
		{
			newLeafNode(otherlv_0, grammarAccess.getWithStatementAccess().getWithKeyword_0());
		}
		otherlv_1=LeftParenthesis
		{
			newLeafNode(otherlv_1, grammarAccess.getWithStatementAccess().getLeftParenthesisKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getWithStatementAccess().getExpressionExpressionParserRuleCall_2_0());
				}
				lv_expression_2_0=norm1_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getWithStatementRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_3=RightParenthesis
		{
			newLeafNode(otherlv_3, grammarAccess.getWithStatementAccess().getRightParenthesisKeyword_3());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getWithStatementAccess().getStatementStatementParserRuleCall_4_0());
				}
				lv_statement_4_0=ruleStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getWithStatementRule());
					}
					set(
						$current,
						"statement",
						lv_statement_4_0,
						"org.eclipse.n4js.N4JS.Statement");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule WithStatement
norm1_WithStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=With
		{
			newLeafNode(otherlv_0, grammarAccess.getWithStatementAccess().getWithKeyword_0());
		}
		otherlv_1=LeftParenthesis
		{
			newLeafNode(otherlv_1, grammarAccess.getWithStatementAccess().getLeftParenthesisKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getWithStatementAccess().getExpressionExpressionParserRuleCall_2_0());
				}
				lv_expression_2_0=norm3_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getWithStatementRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_3=RightParenthesis
		{
			newLeafNode(otherlv_3, grammarAccess.getWithStatementAccess().getRightParenthesisKeyword_3());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getWithStatementAccess().getStatementStatementParserRuleCall_4_0());
				}
				lv_statement_4_0=norm1_Statement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getWithStatementRule());
					}
					set(
						$current,
						"statement",
						lv_statement_4_0,
						"org.eclipse.n4js.N4JS.Statement");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleSwitchStatement
entryRuleSwitchStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSwitchStatementRule()); }
	iv_ruleSwitchStatement=ruleSwitchStatement
	{ $current=$iv_ruleSwitchStatement.current; }
	EOF;

// Rule SwitchStatement
ruleSwitchStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Switch
		{
			newLeafNode(otherlv_0, grammarAccess.getSwitchStatementAccess().getSwitchKeyword_0());
		}
		otherlv_1=LeftParenthesis
		{
			newLeafNode(otherlv_1, grammarAccess.getSwitchStatementAccess().getLeftParenthesisKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSwitchStatementAccess().getExpressionExpressionParserRuleCall_2_0());
				}
				lv_expression_2_0=norm1_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSwitchStatementRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_3=RightParenthesis
		{
			newLeafNode(otherlv_3, grammarAccess.getSwitchStatementAccess().getRightParenthesisKeyword_3());
		}
		otherlv_4=LeftCurlyBracket
		{
			newLeafNode(otherlv_4, grammarAccess.getSwitchStatementAccess().getLeftCurlyBracketKeyword_4());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSwitchStatementAccess().getCasesCaseClauseParserRuleCall_5_0());
				}
				lv_cases_5_0=ruleCaseClause
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSwitchStatementRule());
					}
					add(
						$current,
						"cases",
						lv_cases_5_0,
						"org.eclipse.n4js.N4JS.CaseClause");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getSwitchStatementAccess().getCasesDefaultClauseParserRuleCall_6_0_0());
					}
					lv_cases_6_0=ruleDefaultClause
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSwitchStatementRule());
						}
						add(
							$current,
							"cases",
							lv_cases_6_0,
							"org.eclipse.n4js.N4JS.DefaultClause");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getSwitchStatementAccess().getCasesCaseClauseParserRuleCall_6_1_0());
					}
					lv_cases_7_0=ruleCaseClause
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSwitchStatementRule());
						}
						add(
							$current,
							"cases",
							lv_cases_7_0,
							"org.eclipse.n4js.N4JS.CaseClause");
						afterParserOrEnumRuleCall();
					}
				)
			)*
		)?
		otherlv_8=RightCurlyBracket
		{
			newLeafNode(otherlv_8, grammarAccess.getSwitchStatementAccess().getRightCurlyBracketKeyword_7());
		}
	)
;


// Rule SwitchStatement
norm1_SwitchStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Switch
		{
			newLeafNode(otherlv_0, grammarAccess.getSwitchStatementAccess().getSwitchKeyword_0());
		}
		otherlv_1=LeftParenthesis
		{
			newLeafNode(otherlv_1, grammarAccess.getSwitchStatementAccess().getLeftParenthesisKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSwitchStatementAccess().getExpressionExpressionParserRuleCall_2_0());
				}
				lv_expression_2_0=norm3_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSwitchStatementRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_3=RightParenthesis
		{
			newLeafNode(otherlv_3, grammarAccess.getSwitchStatementAccess().getRightParenthesisKeyword_3());
		}
		otherlv_4=LeftCurlyBracket
		{
			newLeafNode(otherlv_4, grammarAccess.getSwitchStatementAccess().getLeftCurlyBracketKeyword_4());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getSwitchStatementAccess().getCasesCaseClauseParserRuleCall_5_0());
				}
				lv_cases_5_0=norm1_CaseClause
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getSwitchStatementRule());
					}
					add(
						$current,
						"cases",
						lv_cases_5_0,
						"org.eclipse.n4js.N4JS.CaseClause");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getSwitchStatementAccess().getCasesDefaultClauseParserRuleCall_6_0_0());
					}
					lv_cases_6_0=norm1_DefaultClause
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSwitchStatementRule());
						}
						add(
							$current,
							"cases",
							lv_cases_6_0,
							"org.eclipse.n4js.N4JS.DefaultClause");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getSwitchStatementAccess().getCasesCaseClauseParserRuleCall_6_1_0());
					}
					lv_cases_7_0=norm1_CaseClause
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getSwitchStatementRule());
						}
						add(
							$current,
							"cases",
							lv_cases_7_0,
							"org.eclipse.n4js.N4JS.CaseClause");
						afterParserOrEnumRuleCall();
					}
				)
			)*
		)?
		otherlv_8=RightCurlyBracket
		{
			newLeafNode(otherlv_8, grammarAccess.getSwitchStatementAccess().getRightCurlyBracketKeyword_7());
		}
	)
;

// Entry rule entryRuleCaseClause
entryRuleCaseClause returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getCaseClauseRule()); }
	iv_ruleCaseClause=ruleCaseClause
	{ $current=$iv_ruleCaseClause.current; }
	EOF;

// Rule CaseClause
ruleCaseClause returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Case
		{
			newLeafNode(otherlv_0, grammarAccess.getCaseClauseAccess().getCaseKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getCaseClauseAccess().getExpressionExpressionParserRuleCall_1_0());
				}
				lv_expression_1_0=norm1_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getCaseClauseRule());
					}
					set(
						$current,
						"expression",
						lv_expression_1_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_2=Colon
		{
			newLeafNode(otherlv_2, grammarAccess.getCaseClauseAccess().getColonKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getCaseClauseAccess().getStatementsStatementParserRuleCall_3_0());
				}
				lv_statements_3_0=ruleStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getCaseClauseRule());
					}
					add(
						$current,
						"statements",
						lv_statements_3_0,
						"org.eclipse.n4js.N4JS.Statement");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;


// Rule CaseClause
norm1_CaseClause returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Case
		{
			newLeafNode(otherlv_0, grammarAccess.getCaseClauseAccess().getCaseKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getCaseClauseAccess().getExpressionExpressionParserRuleCall_1_0());
				}
				lv_expression_1_0=norm3_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getCaseClauseRule());
					}
					set(
						$current,
						"expression",
						lv_expression_1_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_2=Colon
		{
			newLeafNode(otherlv_2, grammarAccess.getCaseClauseAccess().getColonKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getCaseClauseAccess().getStatementsStatementParserRuleCall_3_0());
				}
				lv_statements_3_0=norm1_Statement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getCaseClauseRule());
					}
					add(
						$current,
						"statements",
						lv_statements_3_0,
						"org.eclipse.n4js.N4JS.Statement");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;

// Entry rule entryRuleDefaultClause
entryRuleDefaultClause returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getDefaultClauseRule()); }
	iv_ruleDefaultClause=ruleDefaultClause
	{ $current=$iv_ruleDefaultClause.current; }
	EOF;

// Rule DefaultClause
ruleDefaultClause returns [EObject current=null]
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
					grammarAccess.getDefaultClauseAccess().getDefaultClauseAction_0(),
					$current);
			}
		)
		otherlv_1=Default
		{
			newLeafNode(otherlv_1, grammarAccess.getDefaultClauseAccess().getDefaultKeyword_1());
		}
		otherlv_2=Colon
		{
			newLeafNode(otherlv_2, grammarAccess.getDefaultClauseAccess().getColonKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getDefaultClauseAccess().getStatementsStatementParserRuleCall_3_0());
				}
				lv_statements_3_0=ruleStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getDefaultClauseRule());
					}
					add(
						$current,
						"statements",
						lv_statements_3_0,
						"org.eclipse.n4js.N4JS.Statement");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;


// Rule DefaultClause
norm1_DefaultClause returns [EObject current=null]
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
					grammarAccess.getDefaultClauseAccess().getDefaultClauseAction_0(),
					$current);
			}
		)
		otherlv_1=Default
		{
			newLeafNode(otherlv_1, grammarAccess.getDefaultClauseAccess().getDefaultKeyword_1());
		}
		otherlv_2=Colon
		{
			newLeafNode(otherlv_2, grammarAccess.getDefaultClauseAccess().getColonKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getDefaultClauseAccess().getStatementsStatementParserRuleCall_3_0());
				}
				lv_statements_3_0=norm1_Statement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getDefaultClauseRule());
					}
					add(
						$current,
						"statements",
						lv_statements_3_0,
						"org.eclipse.n4js.N4JS.Statement");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;

// Entry rule entryRuleLabelledStatement
entryRuleLabelledStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getLabelledStatementRule()); }
	iv_ruleLabelledStatement=ruleLabelledStatement
	{ $current=$iv_ruleLabelledStatement.current; }
	EOF;

// Rule LabelledStatement
ruleLabelledStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
					(
						ruleBindingIdentifier
					)
				)
				Colon
			)
			)=>
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getLabelledStatementAccess().getNameBindingIdentifierParserRuleCall_0_0_0_0());
						}
						lv_name_0_0=ruleBindingIdentifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getLabelledStatementRule());
							}
							set(
								$current,
								"name",
								lv_name_0_0,
								"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
							afterParserOrEnumRuleCall();
						}
					)
				)
				otherlv_1=Colon
				{
					newLeafNode(otherlv_1, grammarAccess.getLabelledStatementAccess().getColonKeyword_0_0_1());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getLabelledStatementAccess().getStatementStatementParserRuleCall_1_0());
				}
				lv_statement_2_0=ruleStatement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getLabelledStatementRule());
					}
					set(
						$current,
						"statement",
						lv_statement_2_0,
						"org.eclipse.n4js.N4JS.Statement");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule LabelledStatement
norm1_LabelledStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
					(
						norm1_BindingIdentifier
					)
				)
				Colon
			)
			)=>
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getLabelledStatementAccess().getNameBindingIdentifierParserRuleCall_0_0_0_0());
						}
						lv_name_0_0=norm1_BindingIdentifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getLabelledStatementRule());
							}
							set(
								$current,
								"name",
								lv_name_0_0,
								"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
							afterParserOrEnumRuleCall();
						}
					)
				)
				otherlv_1=Colon
				{
					newLeafNode(otherlv_1, grammarAccess.getLabelledStatementAccess().getColonKeyword_0_0_1());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getLabelledStatementAccess().getStatementStatementParserRuleCall_1_0());
				}
				lv_statement_2_0=norm1_Statement
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getLabelledStatementRule());
					}
					set(
						$current,
						"statement",
						lv_statement_2_0,
						"org.eclipse.n4js.N4JS.Statement");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleThrowStatement
entryRuleThrowStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getThrowStatementRule()); }
	iv_ruleThrowStatement=ruleThrowStatement
	{ $current=$iv_ruleThrowStatement.current; }
	EOF;

// Rule ThrowStatement
ruleThrowStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Throw { promoteEOL(); }
		{
			newLeafNode(otherlv_0, grammarAccess.getThrowStatementAccess().getThrowKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getThrowStatementAccess().getExpressionExpressionParserRuleCall_1_0());
				}
				lv_expression_1_0=norm1_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getThrowStatementRule());
					}
					set(
						$current,
						"expression",
						lv_expression_1_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		{
			newCompositeNode(grammarAccess.getThrowStatementAccess().getSemiParserRuleCall_2());
		}
		ruleSemi
		{
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule ThrowStatement
norm1_ThrowStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Throw { promoteEOL(); }
		{
			newLeafNode(otherlv_0, grammarAccess.getThrowStatementAccess().getThrowKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getThrowStatementAccess().getExpressionExpressionParserRuleCall_1_0());
				}
				lv_expression_1_0=norm3_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getThrowStatementRule());
					}
					set(
						$current,
						"expression",
						lv_expression_1_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		{
			newCompositeNode(grammarAccess.getThrowStatementAccess().getSemiParserRuleCall_2());
		}
		ruleSemi
		{
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleTryStatement
entryRuleTryStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTryStatementRule()); }
	iv_ruleTryStatement=ruleTryStatement
	{ $current=$iv_ruleTryStatement.current; }
	EOF;

// Rule TryStatement
ruleTryStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Try
		{
			newLeafNode(otherlv_0, grammarAccess.getTryStatementAccess().getTryKeyword_0());
		}
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getTryStatementAccess().getBlockBlockParserRuleCall_1_0());
				}
				lv_block_1_0=ruleBlock
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTryStatementRule());
					}
					set(
						$current,
						"block",
						lv_block_1_0,
						"org.eclipse.n4js.N4JS.Block");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getTryStatementAccess().getCatchCatchBlockParserRuleCall_2_0_0_0());
						}
						lv_catch_2_0=ruleCatchBlock
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTryStatementRule());
							}
							set(
								$current,
								"catch",
								lv_catch_2_0,
								"org.eclipse.n4js.N4JS.CatchBlock");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getTryStatementAccess().getFinallyFinallyBlockParserRuleCall_2_0_1_0());
						}
						lv_finally_3_0=ruleFinallyBlock
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTryStatementRule());
							}
							set(
								$current,
								"finally",
								lv_finally_3_0,
								"org.eclipse.n4js.N4JS.FinallyBlock");
							afterParserOrEnumRuleCall();
						}
					)
				)?
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getTryStatementAccess().getFinallyFinallyBlockParserRuleCall_2_1_0());
					}
					lv_finally_4_0=ruleFinallyBlock
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTryStatementRule());
						}
						set(
							$current,
							"finally",
							lv_finally_4_0,
							"org.eclipse.n4js.N4JS.FinallyBlock");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;


// Rule TryStatement
norm1_TryStatement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Try
		{
			newLeafNode(otherlv_0, grammarAccess.getTryStatementAccess().getTryKeyword_0());
		}
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getTryStatementAccess().getBlockBlockParserRuleCall_1_0());
				}
				lv_block_1_0=norm1_Block
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTryStatementRule());
					}
					set(
						$current,
						"block",
						lv_block_1_0,
						"org.eclipse.n4js.N4JS.Block");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getTryStatementAccess().getCatchCatchBlockParserRuleCall_2_0_0_0());
						}
						lv_catch_2_0=norm1_CatchBlock
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTryStatementRule());
							}
							set(
								$current,
								"catch",
								lv_catch_2_0,
								"org.eclipse.n4js.N4JS.CatchBlock");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getTryStatementAccess().getFinallyFinallyBlockParserRuleCall_2_0_1_0());
						}
						lv_finally_3_0=norm1_FinallyBlock
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTryStatementRule());
							}
							set(
								$current,
								"finally",
								lv_finally_3_0,
								"org.eclipse.n4js.N4JS.FinallyBlock");
							afterParserOrEnumRuleCall();
						}
					)
				)?
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getTryStatementAccess().getFinallyFinallyBlockParserRuleCall_2_1_0());
					}
					lv_finally_4_0=norm1_FinallyBlock
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTryStatementRule());
						}
						set(
							$current,
							"finally",
							lv_finally_4_0,
							"org.eclipse.n4js.N4JS.FinallyBlock");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;

// Entry rule entryRuleCatchBlock
entryRuleCatchBlock returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getCatchBlockRule()); }
	iv_ruleCatchBlock=ruleCatchBlock
	{ $current=$iv_ruleCatchBlock.current; }
	EOF;

// Rule CatchBlock
ruleCatchBlock returns [EObject current=null]
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
					grammarAccess.getCatchBlockAccess().getCatchBlockAction_0(),
					$current);
			}
		)
		otherlv_1=Catch
		{
			newLeafNode(otherlv_1, grammarAccess.getCatchBlockAccess().getCatchKeyword_1());
		}
		otherlv_2=LeftParenthesis
		{
			newLeafNode(otherlv_2, grammarAccess.getCatchBlockAccess().getLeftParenthesisKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getCatchBlockAccess().getCatchVariableCatchVariableParserRuleCall_3_0());
				}
				lv_catchVariable_3_0=ruleCatchVariable
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getCatchBlockRule());
					}
					set(
						$current,
						"catchVariable",
						lv_catchVariable_3_0,
						"org.eclipse.n4js.N4JS.CatchVariable");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_4=RightParenthesis
		{
			newLeafNode(otherlv_4, grammarAccess.getCatchBlockAccess().getRightParenthesisKeyword_4());
		}
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getCatchBlockAccess().getBlockBlockParserRuleCall_5_0());
				}
				lv_block_5_0=ruleBlock
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getCatchBlockRule());
					}
					set(
						$current,
						"block",
						lv_block_5_0,
						"org.eclipse.n4js.N4JS.Block");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule CatchBlock
norm1_CatchBlock returns [EObject current=null]
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
					grammarAccess.getCatchBlockAccess().getCatchBlockAction_0(),
					$current);
			}
		)
		otherlv_1=Catch
		{
			newLeafNode(otherlv_1, grammarAccess.getCatchBlockAccess().getCatchKeyword_1());
		}
		otherlv_2=LeftParenthesis
		{
			newLeafNode(otherlv_2, grammarAccess.getCatchBlockAccess().getLeftParenthesisKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getCatchBlockAccess().getCatchVariableCatchVariableParserRuleCall_3_0());
				}
				lv_catchVariable_3_0=norm1_CatchVariable
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getCatchBlockRule());
					}
					set(
						$current,
						"catchVariable",
						lv_catchVariable_3_0,
						"org.eclipse.n4js.N4JS.CatchVariable");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_4=RightParenthesis
		{
			newLeafNode(otherlv_4, grammarAccess.getCatchBlockAccess().getRightParenthesisKeyword_4());
		}
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getCatchBlockAccess().getBlockBlockParserRuleCall_5_0());
				}
				lv_block_5_0=norm1_Block
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getCatchBlockRule());
					}
					set(
						$current,
						"block",
						lv_block_5_0,
						"org.eclipse.n4js.N4JS.Block");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleCatchVariable
entryRuleCatchVariable returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getCatchVariableRule()); }
	iv_ruleCatchVariable=ruleCatchVariable
	{ $current=$iv_ruleCatchVariable.current; }
	EOF;

// Rule CatchVariable
ruleCatchVariable returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				ruleBindingPattern
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getCatchVariableAccess().getBindingPatternBindingPatternParserRuleCall_0_0());
				}
				lv_bindingPattern_0_0=ruleBindingPattern
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getCatchVariableRule());
					}
					set(
						$current,
						"bindingPattern",
						lv_bindingPattern_0_0,
						"org.eclipse.n4js.N4JS.BindingPattern");
					afterParserOrEnumRuleCall();
				}
			)
		)
		    |
		(
			((
				(
					(
						ruleBindingIdentifier
					)
				)
				Colon
			)
			)=>
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getCatchVariableAccess().getNameBindingIdentifierParserRuleCall_1_0_0_0());
						}
						lv_name_1_0=ruleBindingIdentifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getCatchVariableRule());
							}
							set(
								$current,
								"name",
								lv_name_1_0,
								"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(Colon)=>
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCatchVariableRule());
						}
						newCompositeNode(grammarAccess.getCatchVariableAccess().getColonSepDeclaredTypeRefParserRuleCall_1_0_1());
					}
					this_ColonSepDeclaredTypeRef_2=ruleColonSepDeclaredTypeRef[$current]
					{
						$current = $this_ColonSepDeclaredTypeRef_2.current;
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		    |
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getCatchVariableRule());
					}
					newCompositeNode(grammarAccess.getCatchVariableAccess().getBogusTypeRefFragmentParserRuleCall_2_0());
				}
				this_BogusTypeRefFragment_3=ruleBogusTypeRefFragment[$current]
				{
					$current = $this_BogusTypeRefFragment_3.current;
					afterParserOrEnumRuleCall();
				}
			)?
			(
				(
					{
						newCompositeNode(grammarAccess.getCatchVariableAccess().getNameBindingIdentifierParserRuleCall_2_1_0());
					}
					lv_name_4_0=ruleBindingIdentifier
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getCatchVariableRule());
						}
						set(
							$current,
							"name",
							lv_name_4_0,
							"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;


// Rule CatchVariable
norm1_CatchVariable returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				norm1_BindingPattern
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getCatchVariableAccess().getBindingPatternBindingPatternParserRuleCall_0_0());
				}
				lv_bindingPattern_0_0=norm1_BindingPattern
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getCatchVariableRule());
					}
					set(
						$current,
						"bindingPattern",
						lv_bindingPattern_0_0,
						"org.eclipse.n4js.N4JS.BindingPattern");
					afterParserOrEnumRuleCall();
				}
			)
		)
		    |
		(
			((
				(
					(
						norm1_BindingIdentifier
					)
				)
				Colon
			)
			)=>
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getCatchVariableAccess().getNameBindingIdentifierParserRuleCall_1_0_0_0());
						}
						lv_name_1_0=norm1_BindingIdentifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getCatchVariableRule());
							}
							set(
								$current,
								"name",
								lv_name_1_0,
								"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(Colon)=>
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getCatchVariableRule());
						}
						newCompositeNode(grammarAccess.getCatchVariableAccess().getColonSepDeclaredTypeRefParserRuleCall_1_0_1());
					}
					this_ColonSepDeclaredTypeRef_2=ruleColonSepDeclaredTypeRef[$current]
					{
						$current = $this_ColonSepDeclaredTypeRef_2.current;
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		    |
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getCatchVariableRule());
					}
					newCompositeNode(grammarAccess.getCatchVariableAccess().getBogusTypeRefFragmentParserRuleCall_2_0());
				}
				this_BogusTypeRefFragment_3=ruleBogusTypeRefFragment[$current]
				{
					$current = $this_BogusTypeRefFragment_3.current;
					afterParserOrEnumRuleCall();
				}
			)?
			(
				(
					{
						newCompositeNode(grammarAccess.getCatchVariableAccess().getNameBindingIdentifierParserRuleCall_2_1_0());
					}
					lv_name_4_0=norm1_BindingIdentifier
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getCatchVariableRule());
						}
						set(
							$current,
							"name",
							lv_name_4_0,
							"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;

// Entry rule entryRuleFinallyBlock
entryRuleFinallyBlock returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getFinallyBlockRule()); }
	iv_ruleFinallyBlock=ruleFinallyBlock
	{ $current=$iv_ruleFinallyBlock.current; }
	EOF;

// Rule FinallyBlock
ruleFinallyBlock returns [EObject current=null]
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
					grammarAccess.getFinallyBlockAccess().getFinallyBlockAction_0(),
					$current);
			}
		)
		otherlv_1=Finally
		{
			newLeafNode(otherlv_1, grammarAccess.getFinallyBlockAccess().getFinallyKeyword_1());
		}
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getFinallyBlockAccess().getBlockBlockParserRuleCall_2_0());
				}
				lv_block_2_0=ruleBlock
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getFinallyBlockRule());
					}
					set(
						$current,
						"block",
						lv_block_2_0,
						"org.eclipse.n4js.N4JS.Block");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule FinallyBlock
norm1_FinallyBlock returns [EObject current=null]
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
					grammarAccess.getFinallyBlockAccess().getFinallyBlockAction_0(),
					$current);
			}
		)
		otherlv_1=Finally
		{
			newLeafNode(otherlv_1, grammarAccess.getFinallyBlockAccess().getFinallyKeyword_1());
		}
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getFinallyBlockAccess().getBlockBlockParserRuleCall_2_0());
				}
				lv_block_2_0=norm1_Block
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getFinallyBlockRule());
					}
					set(
						$current,
						"block",
						lv_block_2_0,
						"org.eclipse.n4js.N4JS.Block");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleDebuggerStatement
entryRuleDebuggerStatement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getDebuggerStatementRule()); }
	iv_ruleDebuggerStatement=ruleDebuggerStatement
	{ $current=$iv_ruleDebuggerStatement.current; }
	EOF;

// Rule DebuggerStatement
ruleDebuggerStatement returns [EObject current=null]
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
					grammarAccess.getDebuggerStatementAccess().getDebuggerStatementAction_0(),
					$current);
			}
		)
		otherlv_1=Debugger
		{
			newLeafNode(otherlv_1, grammarAccess.getDebuggerStatementAccess().getDebuggerKeyword_1());
		}
		{
			newCompositeNode(grammarAccess.getDebuggerStatementAccess().getSemiParserRuleCall_2());
		}
		ruleSemi
		{
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRulePrimaryExpression
entryRulePrimaryExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPrimaryExpressionRule()); }
	iv_rulePrimaryExpression=rulePrimaryExpression
	{ $current=$iv_rulePrimaryExpression.current; }
	EOF;

// Rule PrimaryExpression
rulePrimaryExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getThisLiteralParserRuleCall_0());
		}
		this_ThisLiteral_0=ruleThisLiteral
		{
			$current = $this_ThisLiteral_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getSuperLiteralParserRuleCall_1());
		}
		this_SuperLiteral_1=ruleSuperLiteral
		{
			$current = $this_SuperLiteral_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getIdentifierRefParserRuleCall_2());
		}
		this_IdentifierRef_2=ruleIdentifierRef
		{
			$current = $this_IdentifierRef_2.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getJSXFragmentParserRuleCall_3());
		}
		this_JSXFragment_3=ruleJSXFragment
		{
			$current = $this_JSXFragment_3.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getJSXElementParserRuleCall_4());
		}
		this_JSXElement_4=ruleJSXElement
		{
			$current = $this_JSXElement_4.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParameterizedCallExpressionParserRuleCall_5());
		}
		this_ParameterizedCallExpression_5=ruleParameterizedCallExpression
		{
			$current = $this_ParameterizedCallExpression_5.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getLiteralParserRuleCall_6());
		}
		this_Literal_6=ruleLiteral
		{
			$current = $this_Literal_6.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getArrayLiteralParserRuleCall_7());
		}
		this_ArrayLiteral_7=ruleArrayLiteral
		{
			$current = $this_ArrayLiteral_7.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getObjectLiteralParserRuleCall_8());
		}
		this_ObjectLiteral_8=ruleObjectLiteral
		{
			$current = $this_ObjectLiteral_8.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParenExpressionParserRuleCall_9());
		}
		this_ParenExpression_9=ruleParenExpression
		{
			$current = $this_ParenExpression_9.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getAnnotatedExpressionParserRuleCall_10());
		}
		this_AnnotatedExpression_10=ruleAnnotatedExpression
		{
			$current = $this_AnnotatedExpression_10.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getFunctionExpressionParserRuleCall_11());
		}
		this_FunctionExpression_11=ruleFunctionExpression
		{
			$current = $this_FunctionExpression_11.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			((
				(
					(
						Async
					)
				)
				ruleNoLineTerminator[null]
				Function
			)
			)=>
			{
				newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getAsyncFunctionExpressionParserRuleCall_12());
			}
			this_AsyncFunctionExpression_12=ruleAsyncFunctionExpression
			{
				$current = $this_AsyncFunctionExpression_12.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getN4ClassExpressionParserRuleCall_13());
		}
		this_N4ClassExpression_13=ruleN4ClassExpression
		{
			$current = $this_N4ClassExpression_13.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getTemplateLiteralParserRuleCall_14());
		}
		this_TemplateLiteral_14=ruleTemplateLiteral
		{
			$current = $this_TemplateLiteral_14.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule PrimaryExpression
norm1_PrimaryExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getThisLiteralParserRuleCall_0());
		}
		this_ThisLiteral_0=ruleThisLiteral
		{
			$current = $this_ThisLiteral_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getSuperLiteralParserRuleCall_1());
		}
		this_SuperLiteral_1=ruleSuperLiteral
		{
			$current = $this_SuperLiteral_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getIdentifierRefParserRuleCall_2());
		}
		this_IdentifierRef_2=norm1_IdentifierRef
		{
			$current = $this_IdentifierRef_2.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getJSXFragmentParserRuleCall_3());
		}
		this_JSXFragment_3=ruleJSXFragment
		{
			$current = $this_JSXFragment_3.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getJSXElementParserRuleCall_4());
		}
		this_JSXElement_4=ruleJSXElement
		{
			$current = $this_JSXElement_4.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParameterizedCallExpressionParserRuleCall_5());
		}
		this_ParameterizedCallExpression_5=norm1_ParameterizedCallExpression
		{
			$current = $this_ParameterizedCallExpression_5.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getLiteralParserRuleCall_6());
		}
		this_Literal_6=ruleLiteral
		{
			$current = $this_Literal_6.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getArrayLiteralParserRuleCall_7());
		}
		this_ArrayLiteral_7=norm1_ArrayLiteral
		{
			$current = $this_ArrayLiteral_7.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getObjectLiteralParserRuleCall_8());
		}
		this_ObjectLiteral_8=norm1_ObjectLiteral
		{
			$current = $this_ObjectLiteral_8.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParenExpressionParserRuleCall_9());
		}
		this_ParenExpression_9=norm1_ParenExpression
		{
			$current = $this_ParenExpression_9.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getAnnotatedExpressionParserRuleCall_10());
		}
		this_AnnotatedExpression_10=norm1_AnnotatedExpression
		{
			$current = $this_AnnotatedExpression_10.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getFunctionExpressionParserRuleCall_11());
		}
		this_FunctionExpression_11=ruleFunctionExpression
		{
			$current = $this_FunctionExpression_11.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			((
				(
					(
						Async
					)
				)
				ruleNoLineTerminator[null]
				Function
			)
			)=>
			{
				newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getAsyncFunctionExpressionParserRuleCall_12());
			}
			this_AsyncFunctionExpression_12=ruleAsyncFunctionExpression
			{
				$current = $this_AsyncFunctionExpression_12.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getN4ClassExpressionParserRuleCall_13());
		}
		this_N4ClassExpression_13=norm1_N4ClassExpression
		{
			$current = $this_N4ClassExpression_13.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getTemplateLiteralParserRuleCall_14());
		}
		this_TemplateLiteral_14=norm1_TemplateLiteral
		{
			$current = $this_TemplateLiteral_14.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleParenExpression
entryRuleParenExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getParenExpressionRule()); }
	iv_ruleParenExpression=ruleParenExpression
	{ $current=$iv_ruleParenExpression.current; }
	EOF;

// Rule ParenExpression
ruleParenExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LeftParenthesis
		{
			newLeafNode(otherlv_0, grammarAccess.getParenExpressionAccess().getLeftParenthesisKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getParenExpressionAccess().getExpressionExpressionParserRuleCall_1_0());
				}
				lv_expression_1_0=norm1_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getParenExpressionRule());
					}
					set(
						$current,
						"expression",
						lv_expression_1_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_2=RightParenthesis
		{
			newLeafNode(otherlv_2, grammarAccess.getParenExpressionAccess().getRightParenthesisKeyword_2());
		}
	)
;


// Rule ParenExpression
norm1_ParenExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LeftParenthesis
		{
			newLeafNode(otherlv_0, grammarAccess.getParenExpressionAccess().getLeftParenthesisKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getParenExpressionAccess().getExpressionExpressionParserRuleCall_1_0());
				}
				lv_expression_1_0=norm3_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getParenExpressionRule());
					}
					set(
						$current,
						"expression",
						lv_expression_1_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_2=RightParenthesis
		{
			newLeafNode(otherlv_2, grammarAccess.getParenExpressionAccess().getRightParenthesisKeyword_2());
		}
	)
;

// Entry rule entryRuleIdentifierRef
entryRuleIdentifierRef returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getIdentifierRefRule()); }
	iv_ruleIdentifierRef=ruleIdentifierRef
	{ $current=$iv_ruleIdentifierRef.current; }
	EOF;

// Rule IdentifierRef
ruleIdentifierRef returns [EObject current=null]
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
					if ($current==null) {
						$current = createModelElement(grammarAccess.getIdentifierRefRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getIdentifierRefAccess().getIdIdentifiableElementCrossReference_0_0());
				}
				ruleBindingIdentifier
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
		    |
		(
			(
				{
					$current = forceCreateModelElement(
						grammarAccess.getIdentifierRefAccess().getVersionedIdentifierRefAction_1_0(),
						$current);
				}
			)
			(
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getIdentifierRefRule());
						}
					}
					{
						newCompositeNode(grammarAccess.getIdentifierRefAccess().getIdIdentifiableElementCrossReference_1_1_0());
					}
					ruleBindingIdentifier
					{
						afterParserOrEnumRuleCall();
					}
				)
			)
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getIdentifierRefRule());
				}
				newCompositeNode(grammarAccess.getIdentifierRefAccess().getVersionRequestParserRuleCall_1_2());
			}
			this_VersionRequest_3=ruleVersionRequest[$current]
			{
				$current = $this_VersionRequest_3.current;
				afterParserOrEnumRuleCall();
			}
		)
	)
;


// Rule IdentifierRef
norm1_IdentifierRef returns [EObject current=null]
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
					if ($current==null) {
						$current = createModelElement(grammarAccess.getIdentifierRefRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getIdentifierRefAccess().getIdIdentifiableElementCrossReference_0_0());
				}
				norm1_BindingIdentifier
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
		    |
		(
			(
				{
					$current = forceCreateModelElement(
						grammarAccess.getIdentifierRefAccess().getVersionedIdentifierRefAction_1_0(),
						$current);
				}
			)
			(
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getIdentifierRefRule());
						}
					}
					{
						newCompositeNode(grammarAccess.getIdentifierRefAccess().getIdIdentifiableElementCrossReference_1_1_0());
					}
					norm1_BindingIdentifier
					{
						afterParserOrEnumRuleCall();
					}
				)
			)
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getIdentifierRefRule());
				}
				newCompositeNode(grammarAccess.getIdentifierRefAccess().getVersionRequestParserRuleCall_1_2());
			}
			this_VersionRequest_3=ruleVersionRequest[$current]
			{
				$current = $this_VersionRequest_3.current;
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleSuperLiteral
entryRuleSuperLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSuperLiteralRule()); }
	iv_ruleSuperLiteral=ruleSuperLiteral
	{ $current=$iv_ruleSuperLiteral.current; }
	EOF;

// Rule SuperLiteral
ruleSuperLiteral returns [EObject current=null]
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
					grammarAccess.getSuperLiteralAccess().getSuperLiteralAction_0(),
					$current);
			}
		)
		otherlv_1=Super
		{
			newLeafNode(otherlv_1, grammarAccess.getSuperLiteralAccess().getSuperKeyword_1());
		}
	)
;

// Entry rule entryRuleThisLiteral
entryRuleThisLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getThisLiteralRule()); }
	iv_ruleThisLiteral=ruleThisLiteral
	{ $current=$iv_ruleThisLiteral.current; }
	EOF;

// Rule ThisLiteral
ruleThisLiteral returns [EObject current=null]
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
					grammarAccess.getThisLiteralAccess().getThisLiteralAction_0(),
					$current);
			}
		)
		otherlv_1=This_1
		{
			newLeafNode(otherlv_1, grammarAccess.getThisLiteralAccess().getThisKeyword_1());
		}
	)
;

// Entry rule entryRuleArrayLiteral
entryRuleArrayLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getArrayLiteralRule()); }
	iv_ruleArrayLiteral=ruleArrayLiteral
	{ $current=$iv_ruleArrayLiteral.current; }
	EOF;

// Rule ArrayLiteral
ruleArrayLiteral returns [EObject current=null]
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
					grammarAccess.getArrayLiteralAccess().getArrayLiteralAction_0(),
					$current);
			}
		)
		otherlv_1=LeftSquareBracket
		{
			newLeafNode(otherlv_1, grammarAccess.getArrayLiteralAccess().getLeftSquareBracketKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getArrayLiteralAccess().getElementsArrayPaddingParserRuleCall_2_0());
				}
				lv_elements_2_0=ruleArrayPadding
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getArrayLiteralRule());
					}
					add(
						$current,
						"elements",
						lv_elements_2_0,
						"org.eclipse.n4js.N4JS.ArrayPadding");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getArrayLiteralAccess().getElementsArrayElementParserRuleCall_3_0_0());
					}
					lv_elements_3_0=ruleArrayElement
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getArrayLiteralRule());
						}
						add(
							$current,
							"elements",
							lv_elements_3_0,
							"org.eclipse.n4js.N4JS.ArrayElement");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_4=Comma
				{
					newLeafNode(otherlv_4, grammarAccess.getArrayLiteralAccess().getCommaKeyword_3_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getArrayLiteralAccess().getElementsArrayPaddingParserRuleCall_3_1_1_0());
						}
						lv_elements_5_0=ruleArrayPadding
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getArrayLiteralRule());
							}
							add(
								$current,
								"elements",
								lv_elements_5_0,
								"org.eclipse.n4js.N4JS.ArrayPadding");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				(
					(
						{
							newCompositeNode(grammarAccess.getArrayLiteralAccess().getElementsArrayElementParserRuleCall_3_1_2_0());
						}
						lv_elements_6_0=ruleArrayElement
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getArrayLiteralRule());
							}
							add(
								$current,
								"elements",
								lv_elements_6_0,
								"org.eclipse.n4js.N4JS.ArrayElement");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
			(
				(
					(
						lv_trailingComma_7_0=Comma
						{
							newLeafNode(lv_trailingComma_7_0, grammarAccess.getArrayLiteralAccess().getTrailingCommaCommaKeyword_3_2_0_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getArrayLiteralRule());
							}
							setWithLastConsumed($current, "trailingComma", true, ",");
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getArrayLiteralAccess().getElementsArrayPaddingParserRuleCall_3_2_1_0());
						}
						lv_elements_8_0=ruleArrayPadding
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getArrayLiteralRule());
							}
							add(
								$current,
								"elements",
								lv_elements_8_0,
								"org.eclipse.n4js.N4JS.ArrayPadding");
							afterParserOrEnumRuleCall();
						}
					)
				)*
			)?
		)?
		otherlv_9=RightSquareBracket
		{
			newLeafNode(otherlv_9, grammarAccess.getArrayLiteralAccess().getRightSquareBracketKeyword_4());
		}
	)
;


// Rule ArrayLiteral
norm1_ArrayLiteral returns [EObject current=null]
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
					grammarAccess.getArrayLiteralAccess().getArrayLiteralAction_0(),
					$current);
			}
		)
		otherlv_1=LeftSquareBracket
		{
			newLeafNode(otherlv_1, grammarAccess.getArrayLiteralAccess().getLeftSquareBracketKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getArrayLiteralAccess().getElementsArrayPaddingParserRuleCall_2_0());
				}
				lv_elements_2_0=ruleArrayPadding
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getArrayLiteralRule());
					}
					add(
						$current,
						"elements",
						lv_elements_2_0,
						"org.eclipse.n4js.N4JS.ArrayPadding");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getArrayLiteralAccess().getElementsArrayElementParserRuleCall_3_0_0());
					}
					lv_elements_3_0=norm1_ArrayElement
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getArrayLiteralRule());
						}
						add(
							$current,
							"elements",
							lv_elements_3_0,
							"org.eclipse.n4js.N4JS.ArrayElement");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_4=Comma
				{
					newLeafNode(otherlv_4, grammarAccess.getArrayLiteralAccess().getCommaKeyword_3_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getArrayLiteralAccess().getElementsArrayPaddingParserRuleCall_3_1_1_0());
						}
						lv_elements_5_0=ruleArrayPadding
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getArrayLiteralRule());
							}
							add(
								$current,
								"elements",
								lv_elements_5_0,
								"org.eclipse.n4js.N4JS.ArrayPadding");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				(
					(
						{
							newCompositeNode(grammarAccess.getArrayLiteralAccess().getElementsArrayElementParserRuleCall_3_1_2_0());
						}
						lv_elements_6_0=norm1_ArrayElement
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getArrayLiteralRule());
							}
							add(
								$current,
								"elements",
								lv_elements_6_0,
								"org.eclipse.n4js.N4JS.ArrayElement");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
			(
				(
					(
						lv_trailingComma_7_0=Comma
						{
							newLeafNode(lv_trailingComma_7_0, grammarAccess.getArrayLiteralAccess().getTrailingCommaCommaKeyword_3_2_0_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getArrayLiteralRule());
							}
							setWithLastConsumed($current, "trailingComma", true, ",");
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getArrayLiteralAccess().getElementsArrayPaddingParserRuleCall_3_2_1_0());
						}
						lv_elements_8_0=ruleArrayPadding
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getArrayLiteralRule());
							}
							add(
								$current,
								"elements",
								lv_elements_8_0,
								"org.eclipse.n4js.N4JS.ArrayPadding");
							afterParserOrEnumRuleCall();
						}
					)
				)*
			)?
		)?
		otherlv_9=RightSquareBracket
		{
			newLeafNode(otherlv_9, grammarAccess.getArrayLiteralAccess().getRightSquareBracketKeyword_4());
		}
	)
;

// Entry rule entryRuleArrayPadding
entryRuleArrayPadding returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getArrayPaddingRule()); }
	iv_ruleArrayPadding=ruleArrayPadding
	{ $current=$iv_ruleArrayPadding.current; }
	EOF;

// Rule ArrayPadding
ruleArrayPadding returns [EObject current=null]
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
					grammarAccess.getArrayPaddingAccess().getArrayPaddingAction_0(),
					$current);
			}
		)
		otherlv_1=Comma
		{
			newLeafNode(otherlv_1, grammarAccess.getArrayPaddingAccess().getCommaKeyword_1());
		}
	)
;

// Entry rule entryRuleArrayElement
entryRuleArrayElement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getArrayElementRule()); }
	iv_ruleArrayElement=ruleArrayElement
	{ $current=$iv_ruleArrayElement.current; }
	EOF;

// Rule ArrayElement
ruleArrayElement returns [EObject current=null]
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
					grammarAccess.getArrayElementAccess().getArrayElementAction_0(),
					$current);
			}
		)
		(
			(
				lv_spread_1_0=FullStopFullStopFullStop
				{
					newLeafNode(lv_spread_1_0, grammarAccess.getArrayElementAccess().getSpreadFullStopFullStopFullStopKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getArrayElementRule());
					}
					setWithLastConsumed($current, "spread", true, "...");
				}
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getArrayElementAccess().getExpressionAssignmentExpressionParserRuleCall_2_0());
				}
				lv_expression_2_0=norm1_AssignmentExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getArrayElementRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.n4js.N4JS.AssignmentExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule ArrayElement
norm1_ArrayElement returns [EObject current=null]
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
					grammarAccess.getArrayElementAccess().getArrayElementAction_0(),
					$current);
			}
		)
		(
			(
				lv_spread_1_0=FullStopFullStopFullStop
				{
					newLeafNode(lv_spread_1_0, grammarAccess.getArrayElementAccess().getSpreadFullStopFullStopFullStopKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getArrayElementRule());
					}
					setWithLastConsumed($current, "spread", true, "...");
				}
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getArrayElementAccess().getExpressionAssignmentExpressionParserRuleCall_2_0());
				}
				lv_expression_2_0=norm3_AssignmentExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getArrayElementRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.n4js.N4JS.AssignmentExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleObjectLiteral
entryRuleObjectLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getObjectLiteralRule()); }
	iv_ruleObjectLiteral=ruleObjectLiteral
	{ $current=$iv_ruleObjectLiteral.current; }
	EOF;

// Rule ObjectLiteral
ruleObjectLiteral returns [EObject current=null]
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
					grammarAccess.getObjectLiteralAccess().getObjectLiteralAction_0(),
					$current);
			}
		)
		otherlv_1=LeftCurlyBracket
		{
			newLeafNode(otherlv_1, grammarAccess.getObjectLiteralAccess().getLeftCurlyBracketKeyword_1());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getObjectLiteralAccess().getPropertyAssignmentsPropertyAssignmentParserRuleCall_2_0_0());
					}
					lv_propertyAssignments_2_0=rulePropertyAssignment
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getObjectLiteralRule());
						}
						add(
							$current,
							"propertyAssignments",
							lv_propertyAssignments_2_0,
							"org.eclipse.n4js.N4JS.PropertyAssignment");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_3=Comma
				{
					newLeafNode(otherlv_3, grammarAccess.getObjectLiteralAccess().getCommaKeyword_2_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getObjectLiteralAccess().getPropertyAssignmentsPropertyAssignmentParserRuleCall_2_1_1_0());
						}
						lv_propertyAssignments_4_0=rulePropertyAssignment
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getObjectLiteralRule());
							}
							add(
								$current,
								"propertyAssignments",
								lv_propertyAssignments_4_0,
								"org.eclipse.n4js.N4JS.PropertyAssignment");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
			(
				otherlv_5=Comma
				{
					newLeafNode(otherlv_5, grammarAccess.getObjectLiteralAccess().getCommaKeyword_2_2());
				}
			)?
		)?
		otherlv_6=RightCurlyBracket
		{
			newLeafNode(otherlv_6, grammarAccess.getObjectLiteralAccess().getRightCurlyBracketKeyword_3());
		}
	)
;


// Rule ObjectLiteral
norm1_ObjectLiteral returns [EObject current=null]
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
					grammarAccess.getObjectLiteralAccess().getObjectLiteralAction_0(),
					$current);
			}
		)
		otherlv_1=LeftCurlyBracket
		{
			newLeafNode(otherlv_1, grammarAccess.getObjectLiteralAccess().getLeftCurlyBracketKeyword_1());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getObjectLiteralAccess().getPropertyAssignmentsPropertyAssignmentParserRuleCall_2_0_0());
					}
					lv_propertyAssignments_2_0=norm1_PropertyAssignment
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getObjectLiteralRule());
						}
						add(
							$current,
							"propertyAssignments",
							lv_propertyAssignments_2_0,
							"org.eclipse.n4js.N4JS.PropertyAssignment");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_3=Comma
				{
					newLeafNode(otherlv_3, grammarAccess.getObjectLiteralAccess().getCommaKeyword_2_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getObjectLiteralAccess().getPropertyAssignmentsPropertyAssignmentParserRuleCall_2_1_1_0());
						}
						lv_propertyAssignments_4_0=norm1_PropertyAssignment
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getObjectLiteralRule());
							}
							add(
								$current,
								"propertyAssignments",
								lv_propertyAssignments_4_0,
								"org.eclipse.n4js.N4JS.PropertyAssignment");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
			(
				otherlv_5=Comma
				{
					newLeafNode(otherlv_5, grammarAccess.getObjectLiteralAccess().getCommaKeyword_2_2());
				}
			)?
		)?
		otherlv_6=RightCurlyBracket
		{
			newLeafNode(otherlv_6, grammarAccess.getObjectLiteralAccess().getRightCurlyBracketKeyword_3());
		}
	)
;

// Entry rule entryRulePropertyAssignment
entryRulePropertyAssignment returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPropertyAssignmentRule()); }
	iv_rulePropertyAssignment=rulePropertyAssignment
	{ $current=$iv_rulePropertyAssignment.current; }
	EOF;

// Rule PropertyAssignment
rulePropertyAssignment returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getPropertyAssignmentAccess().getAnnotatedPropertyAssignmentParserRuleCall_0());
		}
		this_AnnotatedPropertyAssignment_0=ruleAnnotatedPropertyAssignment
		{
			$current = $this_AnnotatedPropertyAssignment_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			((
				(
				)
				(
					(
						ruleTypeRefWithModifiers
					)
				)?
				(
					(
						ruleLiteralOrComputedPropertyName
					)
				)
				(
					(
						QuestionMark
					)
				)?
				Colon
			)
			)=>
			{
				newCompositeNode(grammarAccess.getPropertyAssignmentAccess().getPropertyNameValuePairParserRuleCall_1());
			}
			this_PropertyNameValuePair_1=rulePropertyNameValuePair
			{
				$current = $this_PropertyNameValuePair_1.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				ruleGetterHeader[null]
			)
			)=>
			{
				newCompositeNode(grammarAccess.getPropertyAssignmentAccess().getPropertyGetterDeclarationParserRuleCall_2());
			}
			this_PropertyGetterDeclaration_2=rulePropertyGetterDeclaration
			{
				$current = $this_PropertyGetterDeclaration_2.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				Set
				(
					Break
					    |
					Case
					    |
					Catch
					    |
					Class
					    |
					Const
					    |
					Continue
					    |
					Debugger
					    |
					Default
					    |
					Delete
					    |
					Do
					    |
					Else
					    |
					Export
					    |
					Extends
					    |
					Finally
					    |
					For
					    |
					Function
					    |
					If
					    |
					Import
					    |
					In
					    |
					Instanceof
					    |
					New
					    |
					Return
					    |
					Super
					    |
					Switch
					    |
					This_1
					    |
					Throw
					    |
					Try
					    |
					Typeof
					    |
					Var
					    |
					Void
					    |
					While
					    |
					With
					    |
					Yield
					    |
					Null
					    |
					True
					    |
					False
					    |
					Enum
					    |
					Get
					    |
					Set
					    |
					Let
					    |
					Project
					    |
					External
					    |
					Abstract
					    |
					Static
					    |
					As
					    |
					From
					    |
					Constructor
					    |
					Of
					    |
					Target
					    |
					Type
					    |
					Union
					    |
					Intersection
					    |
					This
					    |
					Promisify
					    |
					Await
					    |
					Async
					    |
					Implements
					    |
					Interface
					    |
					Private
					    |
					Protected
					    |
					Public
					    |
					Out
					    |
					LeftSquareBracket
					    |
					RULE_IDENTIFIER
					    |
					RULE_STRING
					    |
					RULE_DOUBLE
					    |
					RULE_INT
					    |
					RULE_OCTAL_INT
					    |
					RULE_HEX_INT
					    |
					RULE_SCIENTIFIC_INT
				)
			)
			)=>
			{
				newCompositeNode(grammarAccess.getPropertyAssignmentAccess().getPropertySetterDeclarationParserRuleCall_3());
			}
			this_PropertySetterDeclaration_3=rulePropertySetterDeclaration
			{
				$current = $this_PropertySetterDeclaration_3.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				(
					ruleTypeVariables[null]
				)?
				(
					(
						ruleTypeRefWithModifiers
					)
				)?
				(
					(
						(
							(
								Asterisk
							)
						)
						(
							(
								ruleLiteralOrComputedPropertyName
							)
						)
						LeftParenthesis
					)
					    |
					(
						(
							(
								ruleLiteralOrComputedPropertyName
							)
						)
						LeftParenthesis
					)
				)
			)
			)=>
			{
				newCompositeNode(grammarAccess.getPropertyAssignmentAccess().getPropertyMethodDeclarationParserRuleCall_4());
			}
			this_PropertyMethodDeclaration_4=rulePropertyMethodDeclaration
			{
				$current = $this_PropertyMethodDeclaration_4.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getPropertyAssignmentAccess().getPropertyNameValuePairSingleNameParserRuleCall_5());
		}
		this_PropertyNameValuePairSingleName_5=rulePropertyNameValuePairSingleName
		{
			$current = $this_PropertyNameValuePairSingleName_5.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule PropertyAssignment
norm1_PropertyAssignment returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getPropertyAssignmentAccess().getAnnotatedPropertyAssignmentParserRuleCall_0());
		}
		this_AnnotatedPropertyAssignment_0=norm1_AnnotatedPropertyAssignment
		{
			$current = $this_AnnotatedPropertyAssignment_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			((
				(
				)
				(
					(
						ruleTypeRefWithModifiers
					)
				)?
				(
					(
						norm1_LiteralOrComputedPropertyName
					)
				)
				(
					(
						QuestionMark
					)
				)?
				Colon
			)
			)=>
			{
				newCompositeNode(grammarAccess.getPropertyAssignmentAccess().getPropertyNameValuePairParserRuleCall_1());
			}
			this_PropertyNameValuePair_1=norm1_PropertyNameValuePair
			{
				$current = $this_PropertyNameValuePair_1.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				norm1_GetterHeader[null]
			)
			)=>
			{
				newCompositeNode(grammarAccess.getPropertyAssignmentAccess().getPropertyGetterDeclarationParserRuleCall_2());
			}
			this_PropertyGetterDeclaration_2=norm1_PropertyGetterDeclaration
			{
				$current = $this_PropertyGetterDeclaration_2.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				Set
				(
					Break
					    |
					Case
					    |
					Catch
					    |
					Class
					    |
					Const
					    |
					Continue
					    |
					Debugger
					    |
					Default
					    |
					Delete
					    |
					Do
					    |
					Else
					    |
					Export
					    |
					Extends
					    |
					Finally
					    |
					For
					    |
					Function
					    |
					If
					    |
					Import
					    |
					In
					    |
					Instanceof
					    |
					New
					    |
					Return
					    |
					Super
					    |
					Switch
					    |
					This_1
					    |
					Throw
					    |
					Try
					    |
					Typeof
					    |
					Var
					    |
					Void
					    |
					While
					    |
					With
					    |
					Yield
					    |
					Null
					    |
					True
					    |
					False
					    |
					Enum
					    |
					Get
					    |
					Set
					    |
					Let
					    |
					Project
					    |
					External
					    |
					Abstract
					    |
					Static
					    |
					As
					    |
					From
					    |
					Constructor
					    |
					Of
					    |
					Target
					    |
					Type
					    |
					Union
					    |
					Intersection
					    |
					This
					    |
					Promisify
					    |
					Await
					    |
					Async
					    |
					Implements
					    |
					Interface
					    |
					Private
					    |
					Protected
					    |
					Public
					    |
					Out
					    |
					LeftSquareBracket
					    |
					RULE_IDENTIFIER
					    |
					RULE_STRING
					    |
					RULE_DOUBLE
					    |
					RULE_INT
					    |
					RULE_OCTAL_INT
					    |
					RULE_HEX_INT
					    |
					RULE_SCIENTIFIC_INT
				)
			)
			)=>
			{
				newCompositeNode(grammarAccess.getPropertyAssignmentAccess().getPropertySetterDeclarationParserRuleCall_3());
			}
			this_PropertySetterDeclaration_3=norm1_PropertySetterDeclaration
			{
				$current = $this_PropertySetterDeclaration_3.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				(
					ruleTypeVariables[null]
				)?
				(
					(
						ruleTypeRefWithModifiers
					)
				)?
				(
					(
						(
							(
								Asterisk
							)
						)
						(
							(
								norm1_LiteralOrComputedPropertyName
							)
						)
						LeftParenthesis
					)
					    |
					(
						(
							(
								norm1_LiteralOrComputedPropertyName
							)
						)
						LeftParenthesis
					)
				)
			)
			)=>
			{
				newCompositeNode(grammarAccess.getPropertyAssignmentAccess().getPropertyMethodDeclarationParserRuleCall_4());
			}
			this_PropertyMethodDeclaration_4=norm1_PropertyMethodDeclaration
			{
				$current = $this_PropertyMethodDeclaration_4.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getPropertyAssignmentAccess().getPropertyNameValuePairSingleNameParserRuleCall_5());
		}
		this_PropertyNameValuePairSingleName_5=norm1_PropertyNameValuePairSingleName
		{
			$current = $this_PropertyNameValuePairSingleName_5.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleAnnotatedPropertyAssignment
entryRuleAnnotatedPropertyAssignment returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentRule()); }
	iv_ruleAnnotatedPropertyAssignment=ruleAnnotatedPropertyAssignment
	{ $current=$iv_ruleAnnotatedPropertyAssignment.current; }
	EOF;

// Rule AnnotatedPropertyAssignment
ruleAnnotatedPropertyAssignment returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getPropertyAssignmentAnnotationListParserRuleCall_0());
		}
		this_PropertyAssignmentAnnotationList_0=rulePropertyAssignmentAnnotationList
		{
			$current = $this_PropertyAssignmentAnnotationList_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					((
						(
						)
						(
							(
								ruleTypeRefWithModifiers
							)
						)?
						(
							(
								ruleLiteralOrComputedPropertyName
							)
						)
						Colon
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAnnotatedPropertyAssignmentAccess().getPropertyNameValuePairAnnotationListAction_1_0_0_0_0(),
									$current);
							}
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredTypeRefTypeRefWithModifiersParserRuleCall_1_0_0_0_1_0());
								}
								lv_declaredTypeRef_2_0=ruleTypeRefWithModifiers
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
									}
									set(
										$current,
										"declaredTypeRef",
										lv_declaredTypeRef_2_0,
										"org.eclipse.n4js.ts.TypeExpressions.TypeRefWithModifiers");
									afterParserOrEnumRuleCall();
								}
							)
						)?
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_0_0_0_2_0());
								}
								lv_declaredName_3_0=ruleLiteralOrComputedPropertyName
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
									}
									set(
										$current,
										"declaredName",
										lv_declaredName_3_0,
										"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
									afterParserOrEnumRuleCall();
								}
							)
						)
						otherlv_4=Colon
						{
							newLeafNode(otherlv_4, grammarAccess.getAnnotatedPropertyAssignmentAccess().getColonKeyword_1_0_0_0_3());
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getExpressionAssignmentExpressionParserRuleCall_1_0_1_0());
						}
						lv_expression_5_0=norm1_AssignmentExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
							}
							set(
								$current,
								"expression",
								lv_expression_5_0,
								"org.eclipse.n4js.N4JS.AssignmentExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			    |
			(
				(
					((
						(
						)
						ruleGetterHeader[null]
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAnnotatedPropertyAssignmentAccess().getPropertyGetterDeclarationAnnotationListAction_1_1_0_0_0(),
									$current);
							}
						)
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getAnnotatedPropertyAssignmentRule());
							}
							newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getGetterHeaderParserRuleCall_1_1_0_0_1());
						}
						this_GetterHeader_7=ruleGetterHeader[$current]
						{
							$current = $this_GetterHeader_7.current;
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					((
						(
						)
						LeftCurlyBracket
					)
					)=>
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getBodyBlockParserRuleCall_1_1_1_0());
						}
						lv_body_8_0=ruleBlock
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
							}
							set(
								$current,
								"body",
								lv_body_8_0,
								"org.eclipse.n4js.N4JS.Block");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			    |
			(
				(
					((
						(
						)
						Set
						(
							Break
							    |
							Case
							    |
							Catch
							    |
							Class
							    |
							Const
							    |
							Continue
							    |
							Debugger
							    |
							Default
							    |
							Delete
							    |
							Do
							    |
							Else
							    |
							Export
							    |
							Extends
							    |
							Finally
							    |
							For
							    |
							Function
							    |
							If
							    |
							Import
							    |
							In
							    |
							Instanceof
							    |
							New
							    |
							Return
							    |
							Super
							    |
							Switch
							    |
							This_1
							    |
							Throw
							    |
							Try
							    |
							Typeof
							    |
							Var
							    |
							Void
							    |
							While
							    |
							With
							    |
							Yield
							    |
							Null
							    |
							True
							    |
							False
							    |
							Enum
							    |
							Get
							    |
							Set
							    |
							Let
							    |
							Project
							    |
							External
							    |
							Abstract
							    |
							Static
							    |
							As
							    |
							From
							    |
							Constructor
							    |
							Of
							    |
							Target
							    |
							Type
							    |
							Union
							    |
							Intersection
							    |
							This
							    |
							Promisify
							    |
							Await
							    |
							Async
							    |
							Implements
							    |
							Interface
							    |
							Private
							    |
							Protected
							    |
							Public
							    |
							Out
							    |
							LeftSquareBracket
							    |
							RULE_IDENTIFIER
							    |
							RULE_STRING
							    |
							RULE_DOUBLE
							    |
							RULE_INT
							    |
							RULE_OCTAL_INT
							    |
							RULE_HEX_INT
							    |
							RULE_SCIENTIFIC_INT
						)
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAnnotatedPropertyAssignmentAccess().getPropertySetterDeclarationAnnotationListAction_1_2_0_0_0(),
									$current);
							}
						)
						otherlv_10=Set
						{
							newLeafNode(otherlv_10, grammarAccess.getAnnotatedPropertyAssignmentAccess().getSetKeyword_1_2_0_0_1());
						}
						(
							(Break | Case | Catch | Class | Const | Continue | Debugger | Default | Delete | Do | Else | Export | Extends | Finally | For | Function | If | Import | In | Instanceof | New | Return | Super | Switch | This_1 | Throw | Try | Typeof | Var | Void | While | With | Yield | Null | True | False | Enum | Get | Set | Let | Project | External | Abstract | Static | As | From | Constructor | Of | Target | Type | Union | Intersection | This | Promisify | Await | Async | Implements | Interface | Private | Protected | Public | Out | LeftSquareBracket | RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_2_0());
								}
								lv_declaredName_11_0=ruleLiteralOrComputedPropertyName
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
									}
									set(
										$current,
										"declaredName",
										lv_declaredName_11_0,
										"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
									afterParserOrEnumRuleCall();
								}
							)
						)
					)
				)
				(
					(
						lv_declaredOptional_12_0=QuestionMark
						{
							newLeafNode(lv_declaredOptional_12_0, grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredOptionalQuestionMarkKeyword_1_2_1_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getAnnotatedPropertyAssignmentRule());
							}
							setWithLastConsumed($current, "declaredOptional", true, "?");
						}
					)
				)?
				otherlv_13=LeftParenthesis
				{
					newLeafNode(otherlv_13, grammarAccess.getAnnotatedPropertyAssignmentAccess().getLeftParenthesisKeyword_1_2_2());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getFparFormalParameterParserRuleCall_1_2_3_0());
						}
						lv_fpar_14_0=ruleFormalParameter
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
							}
							set(
								$current,
								"fpar",
								lv_fpar_14_0,
								"org.eclipse.n4js.N4JS.FormalParameter");
							afterParserOrEnumRuleCall();
						}
					)
				)
				otherlv_15=RightParenthesis
				{
					newLeafNode(otherlv_15, grammarAccess.getAnnotatedPropertyAssignmentAccess().getRightParenthesisKeyword_1_2_4());
				}
				(
					((
						(
						)
						LeftCurlyBracket
					)
					)=>
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getBodyBlockParserRuleCall_1_2_5_0());
						}
						lv_body_16_0=ruleBlock
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
							}
							set(
								$current,
								"body",
								lv_body_16_0,
								"org.eclipse.n4js.N4JS.Block");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			    |
			(
				(
					((
						(
						)
						(
							ruleTypeVariables[null]
						)?
						(
							(
								ruleTypeRefWithModifiers
							)
						)?
						(
							(
								(
									(
										Asterisk
									)
								)
								(
									(
										ruleLiteralOrComputedPropertyName
									)
								)
								LeftParenthesis
							)
							    |
							(
								(
									(
										ruleLiteralOrComputedPropertyName
									)
								)
								LeftParenthesis
							)
						)
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAnnotatedPropertyAssignmentAccess().getPropertyMethodDeclarationAnnotationListAction_1_3_0_0_0(),
									$current);
							}
						)
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getAnnotatedPropertyAssignmentRule());
								}
								newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getTypeVariablesParserRuleCall_1_3_0_0_1());
							}
							this_TypeVariables_18=ruleTypeVariables[$current]
							{
								$current = $this_TypeVariables_18.current;
								afterParserOrEnumRuleCall();
							}
						)?
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getReturnTypeRefTypeRefWithModifiersParserRuleCall_1_3_0_0_2_0());
								}
								lv_returnTypeRef_19_0=ruleTypeRefWithModifiers
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
									}
									set(
										$current,
										"returnTypeRef",
										lv_returnTypeRef_19_0,
										"org.eclipse.n4js.ts.TypeExpressions.TypeRefWithModifiers");
									afterParserOrEnumRuleCall();
								}
							)
						)?
						(
							(
								(
									(
										lv_generator_20_0=Asterisk
										{
											newLeafNode(lv_generator_20_0, grammarAccess.getAnnotatedPropertyAssignmentAccess().getGeneratorAsteriskKeyword_1_3_0_0_3_0_0_0());
										}
										{
											if ($current==null) {
												$current = createModelElement(grammarAccess.getAnnotatedPropertyAssignmentRule());
											}
											setWithLastConsumed($current, "generator", true, "*");
										}
									)
								)
								(
									(
										{
											newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_0_1_0());
										}
										lv_declaredName_21_0=ruleLiteralOrComputedPropertyName
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
											}
											set(
												$current,
												"declaredName",
												lv_declaredName_21_0,
												"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
											afterParserOrEnumRuleCall();
										}
									)
								)
								(
									(LeftParenthesis)=>
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getAnnotatedPropertyAssignmentRule());
										}
										newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getMethodParamsAndBodyParserRuleCall_1_3_0_0_3_0_2());
									}
									this_MethodParamsAndBody_22=norm1_MethodParamsAndBody[$current]
									{
										$current = $this_MethodParamsAndBody_22.current;
										afterParserOrEnumRuleCall();
									}
								)
							)
							    |
							(
								(
									(
										{
											newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_1_0_0());
										}
										lv_declaredName_23_0=ruleLiteralOrComputedPropertyName
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
											}
											set(
												$current,
												"declaredName",
												lv_declaredName_23_0,
												"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
											afterParserOrEnumRuleCall();
										}
									)
								)
								(
									(LeftParenthesis)=>
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getAnnotatedPropertyAssignmentRule());
										}
										newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getMethodParamsAndBodyParserRuleCall_1_3_0_0_3_1_1());
									}
									this_MethodParamsAndBody_24=ruleMethodParamsAndBody[$current]
									{
										$current = $this_MethodParamsAndBody_24.current;
										afterParserOrEnumRuleCall();
									}
								)
							)
						)
					)
				)
				(
					otherlv_25=Semicolon
					{
						newLeafNode(otherlv_25, grammarAccess.getAnnotatedPropertyAssignmentAccess().getSemicolonKeyword_1_3_1());
					}
				)?
			)
			    |
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getAnnotatedPropertyAssignmentAccess().getPropertyNameValuePairSingleNameAnnotationListAction_1_4_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredTypeRefTypeRefParserRuleCall_1_4_1_0());
						}
						lv_declaredTypeRef_27_0=ruleTypeRef
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
							}
							set(
								$current,
								"declaredTypeRef",
								lv_declaredTypeRef_27_0,
								"org.eclipse.n4js.ts.TypeExpressions.TypeRef");
							afterParserOrEnumRuleCall();
						}
					)
				)?
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getIdentifierRefIdentifierRefParserRuleCall_1_4_2_0());
						}
						lv_identifierRef_28_0=ruleIdentifierRef
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
							}
							set(
								$current,
								"identifierRef",
								lv_identifierRef_28_0,
								"org.eclipse.n4js.N4JS.IdentifierRef");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					otherlv_29=EqualsSign
					{
						newLeafNode(otherlv_29, grammarAccess.getAnnotatedPropertyAssignmentAccess().getEqualsSignKeyword_1_4_3_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getExpressionAssignmentExpressionParserRuleCall_1_4_3_1_0());
							}
							lv_expression_30_0=norm1_AssignmentExpression
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
								}
								set(
									$current,
									"expression",
									lv_expression_30_0,
									"org.eclipse.n4js.N4JS.AssignmentExpression");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)?
			)
		)
	)
;


// Rule AnnotatedPropertyAssignment
norm1_AnnotatedPropertyAssignment returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getPropertyAssignmentAnnotationListParserRuleCall_0());
		}
		this_PropertyAssignmentAnnotationList_0=rulePropertyAssignmentAnnotationList
		{
			$current = $this_PropertyAssignmentAnnotationList_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					((
						(
						)
						(
							(
								ruleTypeRefWithModifiers
							)
						)?
						(
							(
								norm1_LiteralOrComputedPropertyName
							)
						)
						Colon
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAnnotatedPropertyAssignmentAccess().getPropertyNameValuePairAnnotationListAction_1_0_0_0_0(),
									$current);
							}
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredTypeRefTypeRefWithModifiersParserRuleCall_1_0_0_0_1_0());
								}
								lv_declaredTypeRef_2_0=ruleTypeRefWithModifiers
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
									}
									set(
										$current,
										"declaredTypeRef",
										lv_declaredTypeRef_2_0,
										"org.eclipse.n4js.ts.TypeExpressions.TypeRefWithModifiers");
									afterParserOrEnumRuleCall();
								}
							)
						)?
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_0_0_0_2_0());
								}
								lv_declaredName_3_0=norm1_LiteralOrComputedPropertyName
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
									}
									set(
										$current,
										"declaredName",
										lv_declaredName_3_0,
										"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
									afterParserOrEnumRuleCall();
								}
							)
						)
						otherlv_4=Colon
						{
							newLeafNode(otherlv_4, grammarAccess.getAnnotatedPropertyAssignmentAccess().getColonKeyword_1_0_0_0_3());
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getExpressionAssignmentExpressionParserRuleCall_1_0_1_0());
						}
						lv_expression_5_0=norm3_AssignmentExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
							}
							set(
								$current,
								"expression",
								lv_expression_5_0,
								"org.eclipse.n4js.N4JS.AssignmentExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			    |
			(
				(
					((
						(
						)
						norm1_GetterHeader[null]
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAnnotatedPropertyAssignmentAccess().getPropertyGetterDeclarationAnnotationListAction_1_1_0_0_0(),
									$current);
							}
						)
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getAnnotatedPropertyAssignmentRule());
							}
							newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getGetterHeaderParserRuleCall_1_1_0_0_1());
						}
						this_GetterHeader_7=norm1_GetterHeader[$current]
						{
							$current = $this_GetterHeader_7.current;
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					((
						(
						)
						LeftCurlyBracket
					)
					)=>
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getBodyBlockParserRuleCall_1_1_1_0());
						}
						lv_body_8_0=ruleBlock
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
							}
							set(
								$current,
								"body",
								lv_body_8_0,
								"org.eclipse.n4js.N4JS.Block");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			    |
			(
				(
					((
						(
						)
						Set
						(
							Break
							    |
							Case
							    |
							Catch
							    |
							Class
							    |
							Const
							    |
							Continue
							    |
							Debugger
							    |
							Default
							    |
							Delete
							    |
							Do
							    |
							Else
							    |
							Export
							    |
							Extends
							    |
							Finally
							    |
							For
							    |
							Function
							    |
							If
							    |
							Import
							    |
							In
							    |
							Instanceof
							    |
							New
							    |
							Return
							    |
							Super
							    |
							Switch
							    |
							This_1
							    |
							Throw
							    |
							Try
							    |
							Typeof
							    |
							Var
							    |
							Void
							    |
							While
							    |
							With
							    |
							Yield
							    |
							Null
							    |
							True
							    |
							False
							    |
							Enum
							    |
							Get
							    |
							Set
							    |
							Let
							    |
							Project
							    |
							External
							    |
							Abstract
							    |
							Static
							    |
							As
							    |
							From
							    |
							Constructor
							    |
							Of
							    |
							Target
							    |
							Type
							    |
							Union
							    |
							Intersection
							    |
							This
							    |
							Promisify
							    |
							Await
							    |
							Async
							    |
							Implements
							    |
							Interface
							    |
							Private
							    |
							Protected
							    |
							Public
							    |
							Out
							    |
							LeftSquareBracket
							    |
							RULE_IDENTIFIER
							    |
							RULE_STRING
							    |
							RULE_DOUBLE
							    |
							RULE_INT
							    |
							RULE_OCTAL_INT
							    |
							RULE_HEX_INT
							    |
							RULE_SCIENTIFIC_INT
						)
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAnnotatedPropertyAssignmentAccess().getPropertySetterDeclarationAnnotationListAction_1_2_0_0_0(),
									$current);
							}
						)
						otherlv_10=Set
						{
							newLeafNode(otherlv_10, grammarAccess.getAnnotatedPropertyAssignmentAccess().getSetKeyword_1_2_0_0_1());
						}
						(
							(Break | Case | Catch | Class | Const | Continue | Debugger | Default | Delete | Do | Else | Export | Extends | Finally | For | Function | If | Import | In | Instanceof | New | Return | Super | Switch | This_1 | Throw | Try | Typeof | Var | Void | While | With | Yield | Null | True | False | Enum | Get | Set | Let | Project | External | Abstract | Static | As | From | Constructor | Of | Target | Type | Union | Intersection | This | Promisify | Await | Async | Implements | Interface | Private | Protected | Public | Out | LeftSquareBracket | RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_2_0());
								}
								lv_declaredName_11_0=norm1_LiteralOrComputedPropertyName
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
									}
									set(
										$current,
										"declaredName",
										lv_declaredName_11_0,
										"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
									afterParserOrEnumRuleCall();
								}
							)
						)
					)
				)
				(
					(
						lv_declaredOptional_12_0=QuestionMark
						{
							newLeafNode(lv_declaredOptional_12_0, grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredOptionalQuestionMarkKeyword_1_2_1_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getAnnotatedPropertyAssignmentRule());
							}
							setWithLastConsumed($current, "declaredOptional", true, "?");
						}
					)
				)?
				otherlv_13=LeftParenthesis
				{
					newLeafNode(otherlv_13, grammarAccess.getAnnotatedPropertyAssignmentAccess().getLeftParenthesisKeyword_1_2_2());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getFparFormalParameterParserRuleCall_1_2_3_0());
						}
						lv_fpar_14_0=norm1_FormalParameter
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
							}
							set(
								$current,
								"fpar",
								lv_fpar_14_0,
								"org.eclipse.n4js.N4JS.FormalParameter");
							afterParserOrEnumRuleCall();
						}
					)
				)
				otherlv_15=RightParenthesis
				{
					newLeafNode(otherlv_15, grammarAccess.getAnnotatedPropertyAssignmentAccess().getRightParenthesisKeyword_1_2_4());
				}
				(
					((
						(
						)
						LeftCurlyBracket
					)
					)=>
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getBodyBlockParserRuleCall_1_2_5_0());
						}
						lv_body_16_0=ruleBlock
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
							}
							set(
								$current,
								"body",
								lv_body_16_0,
								"org.eclipse.n4js.N4JS.Block");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			    |
			(
				(
					((
						(
						)
						(
							ruleTypeVariables[null]
						)?
						(
							(
								ruleTypeRefWithModifiers
							)
						)?
						(
							(
								(
									(
										Asterisk
									)
								)
								(
									(
										norm1_LiteralOrComputedPropertyName
									)
								)
								LeftParenthesis
							)
							    |
							(
								(
									(
										norm1_LiteralOrComputedPropertyName
									)
								)
								LeftParenthesis
							)
						)
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAnnotatedPropertyAssignmentAccess().getPropertyMethodDeclarationAnnotationListAction_1_3_0_0_0(),
									$current);
							}
						)
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getAnnotatedPropertyAssignmentRule());
								}
								newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getTypeVariablesParserRuleCall_1_3_0_0_1());
							}
							this_TypeVariables_18=ruleTypeVariables[$current]
							{
								$current = $this_TypeVariables_18.current;
								afterParserOrEnumRuleCall();
							}
						)?
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getReturnTypeRefTypeRefWithModifiersParserRuleCall_1_3_0_0_2_0());
								}
								lv_returnTypeRef_19_0=ruleTypeRefWithModifiers
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
									}
									set(
										$current,
										"returnTypeRef",
										lv_returnTypeRef_19_0,
										"org.eclipse.n4js.ts.TypeExpressions.TypeRefWithModifiers");
									afterParserOrEnumRuleCall();
								}
							)
						)?
						(
							(
								(
									(
										lv_generator_20_0=Asterisk
										{
											newLeafNode(lv_generator_20_0, grammarAccess.getAnnotatedPropertyAssignmentAccess().getGeneratorAsteriskKeyword_1_3_0_0_3_0_0_0());
										}
										{
											if ($current==null) {
												$current = createModelElement(grammarAccess.getAnnotatedPropertyAssignmentRule());
											}
											setWithLastConsumed($current, "generator", true, "*");
										}
									)
								)
								(
									(
										{
											newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_0_1_0());
										}
										lv_declaredName_21_0=norm1_LiteralOrComputedPropertyName
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
											}
											set(
												$current,
												"declaredName",
												lv_declaredName_21_0,
												"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
											afterParserOrEnumRuleCall();
										}
									)
								)
								(
									(LeftParenthesis)=>
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getAnnotatedPropertyAssignmentRule());
										}
										newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getMethodParamsAndBodyParserRuleCall_1_3_0_0_3_0_2());
									}
									this_MethodParamsAndBody_22=norm1_MethodParamsAndBody[$current]
									{
										$current = $this_MethodParamsAndBody_22.current;
										afterParserOrEnumRuleCall();
									}
								)
							)
							    |
							(
								(
									(
										{
											newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_3_0_0_3_1_0_0());
										}
										lv_declaredName_23_0=norm1_LiteralOrComputedPropertyName
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
											}
											set(
												$current,
												"declaredName",
												lv_declaredName_23_0,
												"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
											afterParserOrEnumRuleCall();
										}
									)
								)
								(
									(LeftParenthesis)=>
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getAnnotatedPropertyAssignmentRule());
										}
										newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getMethodParamsAndBodyParserRuleCall_1_3_0_0_3_1_1());
									}
									this_MethodParamsAndBody_24=ruleMethodParamsAndBody[$current]
									{
										$current = $this_MethodParamsAndBody_24.current;
										afterParserOrEnumRuleCall();
									}
								)
							)
						)
					)
				)
				(
					otherlv_25=Semicolon
					{
						newLeafNode(otherlv_25, grammarAccess.getAnnotatedPropertyAssignmentAccess().getSemicolonKeyword_1_3_1());
					}
				)?
			)
			    |
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getAnnotatedPropertyAssignmentAccess().getPropertyNameValuePairSingleNameAnnotationListAction_1_4_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getDeclaredTypeRefTypeRefParserRuleCall_1_4_1_0());
						}
						lv_declaredTypeRef_27_0=ruleTypeRef
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
							}
							set(
								$current,
								"declaredTypeRef",
								lv_declaredTypeRef_27_0,
								"org.eclipse.n4js.ts.TypeExpressions.TypeRef");
							afterParserOrEnumRuleCall();
						}
					)
				)?
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getIdentifierRefIdentifierRefParserRuleCall_1_4_2_0());
						}
						lv_identifierRef_28_0=norm1_IdentifierRef
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
							}
							set(
								$current,
								"identifierRef",
								lv_identifierRef_28_0,
								"org.eclipse.n4js.N4JS.IdentifierRef");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					otherlv_29=EqualsSign
					{
						newLeafNode(otherlv_29, grammarAccess.getAnnotatedPropertyAssignmentAccess().getEqualsSignKeyword_1_4_3_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getAnnotatedPropertyAssignmentAccess().getExpressionAssignmentExpressionParserRuleCall_1_4_3_1_0());
							}
							lv_expression_30_0=norm3_AssignmentExpression
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getAnnotatedPropertyAssignmentRule());
								}
								set(
									$current,
									"expression",
									lv_expression_30_0,
									"org.eclipse.n4js.N4JS.AssignmentExpression");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)?
			)
		)
	)
;

// Entry rule entryRulePropertyMethodDeclaration
entryRulePropertyMethodDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPropertyMethodDeclarationRule()); }
	iv_rulePropertyMethodDeclaration=rulePropertyMethodDeclaration
	{ $current=$iv_rulePropertyMethodDeclaration.current; }
	EOF;

// Rule PropertyMethodDeclaration
rulePropertyMethodDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				(
					ruleTypeVariables[null]
				)?
				(
					(
						ruleTypeRefWithModifiers
					)
				)?
				(
					(
						(
							(
								Asterisk
							)
						)
						(
							(
								ruleLiteralOrComputedPropertyName
							)
						)
						LeftParenthesis
					)
					    |
					(
						(
							(
								ruleLiteralOrComputedPropertyName
							)
						)
						LeftParenthesis
					)
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getPropertyMethodDeclarationAccess().getPropertyMethodDeclarationAction_0_0_0(),
							$current);
					}
				)
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getPropertyMethodDeclarationRule());
						}
						newCompositeNode(grammarAccess.getPropertyMethodDeclarationAccess().getTypeVariablesParserRuleCall_0_0_1());
					}
					this_TypeVariables_1=ruleTypeVariables[$current]
					{
						$current = $this_TypeVariables_1.current;
						afterParserOrEnumRuleCall();
					}
				)?
				(
					(
						{
							newCompositeNode(grammarAccess.getPropertyMethodDeclarationAccess().getReturnTypeRefTypeRefWithModifiersParserRuleCall_0_0_2_0());
						}
						lv_returnTypeRef_2_0=ruleTypeRefWithModifiers
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getPropertyMethodDeclarationRule());
							}
							set(
								$current,
								"returnTypeRef",
								lv_returnTypeRef_2_0,
								"org.eclipse.n4js.ts.TypeExpressions.TypeRefWithModifiers");
							afterParserOrEnumRuleCall();
						}
					)
				)?
				(
					(
						(
							(
								lv_generator_3_0=Asterisk
								{
									newLeafNode(lv_generator_3_0, grammarAccess.getPropertyMethodDeclarationAccess().getGeneratorAsteriskKeyword_0_0_3_0_0_0());
								}
								{
									if ($current==null) {
										$current = createModelElement(grammarAccess.getPropertyMethodDeclarationRule());
									}
									setWithLastConsumed($current, "generator", true, "*");
								}
							)
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getPropertyMethodDeclarationAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0_1_0());
								}
								lv_declaredName_4_0=ruleLiteralOrComputedPropertyName
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getPropertyMethodDeclarationRule());
									}
									set(
										$current,
										"declaredName",
										lv_declaredName_4_0,
										"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
									afterParserOrEnumRuleCall();
								}
							)
						)
						(
							(LeftParenthesis)=>
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getPropertyMethodDeclarationRule());
								}
								newCompositeNode(grammarAccess.getPropertyMethodDeclarationAccess().getMethodParamsAndBodyParserRuleCall_0_0_3_0_2());
							}
							this_MethodParamsAndBody_5=norm1_MethodParamsAndBody[$current]
							{
								$current = $this_MethodParamsAndBody_5.current;
								afterParserOrEnumRuleCall();
							}
						)
					)
					    |
					(
						(
							(
								{
									newCompositeNode(grammarAccess.getPropertyMethodDeclarationAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_1_0_0());
								}
								lv_declaredName_6_0=ruleLiteralOrComputedPropertyName
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getPropertyMethodDeclarationRule());
									}
									set(
										$current,
										"declaredName",
										lv_declaredName_6_0,
										"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
									afterParserOrEnumRuleCall();
								}
							)
						)
						(
							(LeftParenthesis)=>
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getPropertyMethodDeclarationRule());
								}
								newCompositeNode(grammarAccess.getPropertyMethodDeclarationAccess().getMethodParamsAndBodyParserRuleCall_0_0_3_1_1());
							}
							this_MethodParamsAndBody_7=ruleMethodParamsAndBody[$current]
							{
								$current = $this_MethodParamsAndBody_7.current;
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
		)
		(
			otherlv_8=Semicolon
			{
				newLeafNode(otherlv_8, grammarAccess.getPropertyMethodDeclarationAccess().getSemicolonKeyword_1());
			}
		)?
	)
;


// Rule PropertyMethodDeclaration
norm1_PropertyMethodDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				(
					ruleTypeVariables[null]
				)?
				(
					(
						ruleTypeRefWithModifiers
					)
				)?
				(
					(
						(
							(
								Asterisk
							)
						)
						(
							(
								norm1_LiteralOrComputedPropertyName
							)
						)
						LeftParenthesis
					)
					    |
					(
						(
							(
								norm1_LiteralOrComputedPropertyName
							)
						)
						LeftParenthesis
					)
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getPropertyMethodDeclarationAccess().getPropertyMethodDeclarationAction_0_0_0(),
							$current);
					}
				)
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getPropertyMethodDeclarationRule());
						}
						newCompositeNode(grammarAccess.getPropertyMethodDeclarationAccess().getTypeVariablesParserRuleCall_0_0_1());
					}
					this_TypeVariables_1=ruleTypeVariables[$current]
					{
						$current = $this_TypeVariables_1.current;
						afterParserOrEnumRuleCall();
					}
				)?
				(
					(
						{
							newCompositeNode(grammarAccess.getPropertyMethodDeclarationAccess().getReturnTypeRefTypeRefWithModifiersParserRuleCall_0_0_2_0());
						}
						lv_returnTypeRef_2_0=ruleTypeRefWithModifiers
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getPropertyMethodDeclarationRule());
							}
							set(
								$current,
								"returnTypeRef",
								lv_returnTypeRef_2_0,
								"org.eclipse.n4js.ts.TypeExpressions.TypeRefWithModifiers");
							afterParserOrEnumRuleCall();
						}
					)
				)?
				(
					(
						(
							(
								lv_generator_3_0=Asterisk
								{
									newLeafNode(lv_generator_3_0, grammarAccess.getPropertyMethodDeclarationAccess().getGeneratorAsteriskKeyword_0_0_3_0_0_0());
								}
								{
									if ($current==null) {
										$current = createModelElement(grammarAccess.getPropertyMethodDeclarationRule());
									}
									setWithLastConsumed($current, "generator", true, "*");
								}
							)
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getPropertyMethodDeclarationAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0_1_0());
								}
								lv_declaredName_4_0=norm1_LiteralOrComputedPropertyName
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getPropertyMethodDeclarationRule());
									}
									set(
										$current,
										"declaredName",
										lv_declaredName_4_0,
										"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
									afterParserOrEnumRuleCall();
								}
							)
						)
						(
							(LeftParenthesis)=>
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getPropertyMethodDeclarationRule());
								}
								newCompositeNode(grammarAccess.getPropertyMethodDeclarationAccess().getMethodParamsAndBodyParserRuleCall_0_0_3_0_2());
							}
							this_MethodParamsAndBody_5=norm1_MethodParamsAndBody[$current]
							{
								$current = $this_MethodParamsAndBody_5.current;
								afterParserOrEnumRuleCall();
							}
						)
					)
					    |
					(
						(
							(
								{
									newCompositeNode(grammarAccess.getPropertyMethodDeclarationAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_1_0_0());
								}
								lv_declaredName_6_0=norm1_LiteralOrComputedPropertyName
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getPropertyMethodDeclarationRule());
									}
									set(
										$current,
										"declaredName",
										lv_declaredName_6_0,
										"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
									afterParserOrEnumRuleCall();
								}
							)
						)
						(
							(LeftParenthesis)=>
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getPropertyMethodDeclarationRule());
								}
								newCompositeNode(grammarAccess.getPropertyMethodDeclarationAccess().getMethodParamsAndBodyParserRuleCall_0_0_3_1_1());
							}
							this_MethodParamsAndBody_7=ruleMethodParamsAndBody[$current]
							{
								$current = $this_MethodParamsAndBody_7.current;
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
		)
		(
			otherlv_8=Semicolon
			{
				newLeafNode(otherlv_8, grammarAccess.getPropertyMethodDeclarationAccess().getSemicolonKeyword_1());
			}
		)?
	)
;

// Entry rule entryRulePropertyNameValuePair
entryRulePropertyNameValuePair returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPropertyNameValuePairRule()); }
	iv_rulePropertyNameValuePair=rulePropertyNameValuePair
	{ $current=$iv_rulePropertyNameValuePair.current; }
	EOF;

// Rule PropertyNameValuePair
rulePropertyNameValuePair returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				(
					(
						ruleTypeRefWithModifiers
					)
				)?
				(
					(
						ruleLiteralOrComputedPropertyName
					)
				)
				(
					(
						QuestionMark
					)
				)?
				Colon
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getPropertyNameValuePairAccess().getPropertyNameValuePairAction_0_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getPropertyNameValuePairAccess().getDeclaredTypeRefTypeRefWithModifiersParserRuleCall_0_0_1_0());
						}
						lv_declaredTypeRef_1_0=ruleTypeRefWithModifiers
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getPropertyNameValuePairRule());
							}
							set(
								$current,
								"declaredTypeRef",
								lv_declaredTypeRef_1_0,
								"org.eclipse.n4js.ts.TypeExpressions.TypeRefWithModifiers");
							afterParserOrEnumRuleCall();
						}
					)
				)?
				(
					(
						{
							newCompositeNode(grammarAccess.getPropertyNameValuePairAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0());
						}
						lv_declaredName_2_0=ruleLiteralOrComputedPropertyName
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getPropertyNameValuePairRule());
							}
							set(
								$current,
								"declaredName",
								lv_declaredName_2_0,
								"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(
						lv_declaredOptional_3_0=QuestionMark
						{
							newLeafNode(lv_declaredOptional_3_0, grammarAccess.getPropertyNameValuePairAccess().getDeclaredOptionalQuestionMarkKeyword_0_0_3_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getPropertyNameValuePairRule());
							}
							setWithLastConsumed($current, "declaredOptional", true, "?");
						}
					)
				)?
				otherlv_4=Colon
				{
					newLeafNode(otherlv_4, grammarAccess.getPropertyNameValuePairAccess().getColonKeyword_0_0_4());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getPropertyNameValuePairAccess().getExpressionAssignmentExpressionParserRuleCall_1_0());
				}
				lv_expression_5_0=norm1_AssignmentExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPropertyNameValuePairRule());
					}
					set(
						$current,
						"expression",
						lv_expression_5_0,
						"org.eclipse.n4js.N4JS.AssignmentExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule PropertyNameValuePair
norm1_PropertyNameValuePair returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				(
					(
						ruleTypeRefWithModifiers
					)
				)?
				(
					(
						norm1_LiteralOrComputedPropertyName
					)
				)
				(
					(
						QuestionMark
					)
				)?
				Colon
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getPropertyNameValuePairAccess().getPropertyNameValuePairAction_0_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getPropertyNameValuePairAccess().getDeclaredTypeRefTypeRefWithModifiersParserRuleCall_0_0_1_0());
						}
						lv_declaredTypeRef_1_0=ruleTypeRefWithModifiers
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getPropertyNameValuePairRule());
							}
							set(
								$current,
								"declaredTypeRef",
								lv_declaredTypeRef_1_0,
								"org.eclipse.n4js.ts.TypeExpressions.TypeRefWithModifiers");
							afterParserOrEnumRuleCall();
						}
					)
				)?
				(
					(
						{
							newCompositeNode(grammarAccess.getPropertyNameValuePairAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0());
						}
						lv_declaredName_2_0=norm1_LiteralOrComputedPropertyName
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getPropertyNameValuePairRule());
							}
							set(
								$current,
								"declaredName",
								lv_declaredName_2_0,
								"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(
						lv_declaredOptional_3_0=QuestionMark
						{
							newLeafNode(lv_declaredOptional_3_0, grammarAccess.getPropertyNameValuePairAccess().getDeclaredOptionalQuestionMarkKeyword_0_0_3_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getPropertyNameValuePairRule());
							}
							setWithLastConsumed($current, "declaredOptional", true, "?");
						}
					)
				)?
				otherlv_4=Colon
				{
					newLeafNode(otherlv_4, grammarAccess.getPropertyNameValuePairAccess().getColonKeyword_0_0_4());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getPropertyNameValuePairAccess().getExpressionAssignmentExpressionParserRuleCall_1_0());
				}
				lv_expression_5_0=norm3_AssignmentExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPropertyNameValuePairRule());
					}
					set(
						$current,
						"expression",
						lv_expression_5_0,
						"org.eclipse.n4js.N4JS.AssignmentExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRulePropertyNameValuePairSingleName
entryRulePropertyNameValuePairSingleName returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPropertyNameValuePairSingleNameRule()); }
	iv_rulePropertyNameValuePairSingleName=rulePropertyNameValuePairSingleName
	{ $current=$iv_rulePropertyNameValuePairSingleName.current; }
	EOF;

// Rule PropertyNameValuePairSingleName
rulePropertyNameValuePairSingleName returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getPropertyNameValuePairSingleNameAccess().getDeclaredTypeRefTypeRefParserRuleCall_0_0());
				}
				lv_declaredTypeRef_0_0=ruleTypeRef
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPropertyNameValuePairSingleNameRule());
					}
					set(
						$current,
						"declaredTypeRef",
						lv_declaredTypeRef_0_0,
						"org.eclipse.n4js.ts.TypeExpressions.TypeRef");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getPropertyNameValuePairSingleNameAccess().getIdentifierRefIdentifierRefParserRuleCall_1_0());
				}
				lv_identifierRef_1_0=ruleIdentifierRef
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPropertyNameValuePairSingleNameRule());
					}
					set(
						$current,
						"identifierRef",
						lv_identifierRef_1_0,
						"org.eclipse.n4js.N4JS.IdentifierRef");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_2=EqualsSign
			{
				newLeafNode(otherlv_2, grammarAccess.getPropertyNameValuePairSingleNameAccess().getEqualsSignKeyword_2_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getPropertyNameValuePairSingleNameAccess().getExpressionAssignmentExpressionParserRuleCall_2_1_0());
					}
					lv_expression_3_0=norm1_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getPropertyNameValuePairSingleNameRule());
						}
						set(
							$current,
							"expression",
							lv_expression_3_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;


// Rule PropertyNameValuePairSingleName
norm1_PropertyNameValuePairSingleName returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getPropertyNameValuePairSingleNameAccess().getDeclaredTypeRefTypeRefParserRuleCall_0_0());
				}
				lv_declaredTypeRef_0_0=ruleTypeRef
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPropertyNameValuePairSingleNameRule());
					}
					set(
						$current,
						"declaredTypeRef",
						lv_declaredTypeRef_0_0,
						"org.eclipse.n4js.ts.TypeExpressions.TypeRef");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getPropertyNameValuePairSingleNameAccess().getIdentifierRefIdentifierRefParserRuleCall_1_0());
				}
				lv_identifierRef_1_0=norm1_IdentifierRef
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPropertyNameValuePairSingleNameRule());
					}
					set(
						$current,
						"identifierRef",
						lv_identifierRef_1_0,
						"org.eclipse.n4js.N4JS.IdentifierRef");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_2=EqualsSign
			{
				newLeafNode(otherlv_2, grammarAccess.getPropertyNameValuePairSingleNameAccess().getEqualsSignKeyword_2_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getPropertyNameValuePairSingleNameAccess().getExpressionAssignmentExpressionParserRuleCall_2_1_0());
					}
					lv_expression_3_0=norm3_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getPropertyNameValuePairSingleNameRule());
						}
						set(
							$current,
							"expression",
							lv_expression_3_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;

// Entry rule entryRulePropertyGetterDeclaration
entryRulePropertyGetterDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPropertyGetterDeclarationRule()); }
	iv_rulePropertyGetterDeclaration=rulePropertyGetterDeclaration
	{ $current=$iv_rulePropertyGetterDeclaration.current; }
	EOF;

// Rule PropertyGetterDeclaration
rulePropertyGetterDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				ruleGetterHeader[null]
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getPropertyGetterDeclarationAccess().getPropertyGetterDeclarationAction_0_0_0(),
							$current);
					}
				)
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPropertyGetterDeclarationRule());
					}
					newCompositeNode(grammarAccess.getPropertyGetterDeclarationAccess().getGetterHeaderParserRuleCall_0_0_1());
				}
				this_GetterHeader_1=ruleGetterHeader[$current]
				{
					$current = $this_GetterHeader_1.current;
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getPropertyGetterDeclarationAccess().getBodyBlockParserRuleCall_1_0());
				}
				lv_body_2_0=ruleBlock
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPropertyGetterDeclarationRule());
					}
					set(
						$current,
						"body",
						lv_body_2_0,
						"org.eclipse.n4js.N4JS.Block");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule PropertyGetterDeclaration
norm1_PropertyGetterDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				norm1_GetterHeader[null]
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getPropertyGetterDeclarationAccess().getPropertyGetterDeclarationAction_0_0_0(),
							$current);
					}
				)
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPropertyGetterDeclarationRule());
					}
					newCompositeNode(grammarAccess.getPropertyGetterDeclarationAccess().getGetterHeaderParserRuleCall_0_0_1());
				}
				this_GetterHeader_1=norm1_GetterHeader[$current]
				{
					$current = $this_GetterHeader_1.current;
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getPropertyGetterDeclarationAccess().getBodyBlockParserRuleCall_1_0());
				}
				lv_body_2_0=ruleBlock
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPropertyGetterDeclarationRule());
					}
					set(
						$current,
						"body",
						lv_body_2_0,
						"org.eclipse.n4js.N4JS.Block");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRulePropertySetterDeclaration
entryRulePropertySetterDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPropertySetterDeclarationRule()); }
	iv_rulePropertySetterDeclaration=rulePropertySetterDeclaration
	{ $current=$iv_rulePropertySetterDeclaration.current; }
	EOF;

// Rule PropertySetterDeclaration
rulePropertySetterDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				Set
				(
					Break
					    |
					Case
					    |
					Catch
					    |
					Class
					    |
					Const
					    |
					Continue
					    |
					Debugger
					    |
					Default
					    |
					Delete
					    |
					Do
					    |
					Else
					    |
					Export
					    |
					Extends
					    |
					Finally
					    |
					For
					    |
					Function
					    |
					If
					    |
					Import
					    |
					In
					    |
					Instanceof
					    |
					New
					    |
					Return
					    |
					Super
					    |
					Switch
					    |
					This_1
					    |
					Throw
					    |
					Try
					    |
					Typeof
					    |
					Var
					    |
					Void
					    |
					While
					    |
					With
					    |
					Yield
					    |
					Null
					    |
					True
					    |
					False
					    |
					Enum
					    |
					Get
					    |
					Set
					    |
					Let
					    |
					Project
					    |
					External
					    |
					Abstract
					    |
					Static
					    |
					As
					    |
					From
					    |
					Constructor
					    |
					Of
					    |
					Target
					    |
					Type
					    |
					Union
					    |
					Intersection
					    |
					This
					    |
					Promisify
					    |
					Await
					    |
					Async
					    |
					Implements
					    |
					Interface
					    |
					Private
					    |
					Protected
					    |
					Public
					    |
					Out
					    |
					LeftSquareBracket
					    |
					RULE_IDENTIFIER
					    |
					RULE_STRING
					    |
					RULE_DOUBLE
					    |
					RULE_INT
					    |
					RULE_OCTAL_INT
					    |
					RULE_HEX_INT
					    |
					RULE_SCIENTIFIC_INT
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getPropertySetterDeclarationAccess().getPropertySetterDeclarationAction_0_0_0(),
							$current);
					}
				)
				otherlv_1=Set
				{
					newLeafNode(otherlv_1, grammarAccess.getPropertySetterDeclarationAccess().getSetKeyword_0_0_1());
				}
				(
					(Break | Case | Catch | Class | Const | Continue | Debugger | Default | Delete | Do | Else | Export | Extends | Finally | For | Function | If | Import | In | Instanceof | New | Return | Super | Switch | This_1 | Throw | Try | Typeof | Var | Void | While | With | Yield | Null | True | False | Enum | Get | Set | Let | Project | External | Abstract | Static | As | From | Constructor | Of | Target | Type | Union | Intersection | This | Promisify | Await | Async | Implements | Interface | Private | Protected | Public | Out | LeftSquareBracket | RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
					(
						{
							newCompositeNode(grammarAccess.getPropertySetterDeclarationAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0());
						}
						lv_declaredName_2_0=ruleLiteralOrComputedPropertyName
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getPropertySetterDeclarationRule());
							}
							set(
								$current,
								"declaredName",
								lv_declaredName_2_0,
								"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)
		(
			(
				lv_declaredOptional_3_0=QuestionMark
				{
					newLeafNode(lv_declaredOptional_3_0, grammarAccess.getPropertySetterDeclarationAccess().getDeclaredOptionalQuestionMarkKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPropertySetterDeclarationRule());
					}
					setWithLastConsumed($current, "declaredOptional", true, "?");
				}
			)
		)?
		otherlv_4=LeftParenthesis
		{
			newLeafNode(otherlv_4, grammarAccess.getPropertySetterDeclarationAccess().getLeftParenthesisKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getPropertySetterDeclarationAccess().getFparFormalParameterParserRuleCall_3_0());
				}
				lv_fpar_5_0=ruleFormalParameter
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPropertySetterDeclarationRule());
					}
					set(
						$current,
						"fpar",
						lv_fpar_5_0,
						"org.eclipse.n4js.N4JS.FormalParameter");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_6=RightParenthesis
		{
			newLeafNode(otherlv_6, grammarAccess.getPropertySetterDeclarationAccess().getRightParenthesisKeyword_4());
		}
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getPropertySetterDeclarationAccess().getBodyBlockParserRuleCall_5_0());
				}
				lv_body_7_0=ruleBlock
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPropertySetterDeclarationRule());
					}
					set(
						$current,
						"body",
						lv_body_7_0,
						"org.eclipse.n4js.N4JS.Block");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule PropertySetterDeclaration
norm1_PropertySetterDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				Set
				(
					Break
					    |
					Case
					    |
					Catch
					    |
					Class
					    |
					Const
					    |
					Continue
					    |
					Debugger
					    |
					Default
					    |
					Delete
					    |
					Do
					    |
					Else
					    |
					Export
					    |
					Extends
					    |
					Finally
					    |
					For
					    |
					Function
					    |
					If
					    |
					Import
					    |
					In
					    |
					Instanceof
					    |
					New
					    |
					Return
					    |
					Super
					    |
					Switch
					    |
					This_1
					    |
					Throw
					    |
					Try
					    |
					Typeof
					    |
					Var
					    |
					Void
					    |
					While
					    |
					With
					    |
					Yield
					    |
					Null
					    |
					True
					    |
					False
					    |
					Enum
					    |
					Get
					    |
					Set
					    |
					Let
					    |
					Project
					    |
					External
					    |
					Abstract
					    |
					Static
					    |
					As
					    |
					From
					    |
					Constructor
					    |
					Of
					    |
					Target
					    |
					Type
					    |
					Union
					    |
					Intersection
					    |
					This
					    |
					Promisify
					    |
					Await
					    |
					Async
					    |
					Implements
					    |
					Interface
					    |
					Private
					    |
					Protected
					    |
					Public
					    |
					Out
					    |
					LeftSquareBracket
					    |
					RULE_IDENTIFIER
					    |
					RULE_STRING
					    |
					RULE_DOUBLE
					    |
					RULE_INT
					    |
					RULE_OCTAL_INT
					    |
					RULE_HEX_INT
					    |
					RULE_SCIENTIFIC_INT
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getPropertySetterDeclarationAccess().getPropertySetterDeclarationAction_0_0_0(),
							$current);
					}
				)
				otherlv_1=Set
				{
					newLeafNode(otherlv_1, grammarAccess.getPropertySetterDeclarationAccess().getSetKeyword_0_0_1());
				}
				(
					(Break | Case | Catch | Class | Const | Continue | Debugger | Default | Delete | Do | Else | Export | Extends | Finally | For | Function | If | Import | In | Instanceof | New | Return | Super | Switch | This_1 | Throw | Try | Typeof | Var | Void | While | With | Yield | Null | True | False | Enum | Get | Set | Let | Project | External | Abstract | Static | As | From | Constructor | Of | Target | Type | Union | Intersection | This | Promisify | Await | Async | Implements | Interface | Private | Protected | Public | Out | LeftSquareBracket | RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
					(
						{
							newCompositeNode(grammarAccess.getPropertySetterDeclarationAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_2_0());
						}
						lv_declaredName_2_0=norm1_LiteralOrComputedPropertyName
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getPropertySetterDeclarationRule());
							}
							set(
								$current,
								"declaredName",
								lv_declaredName_2_0,
								"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)
		(
			(
				lv_declaredOptional_3_0=QuestionMark
				{
					newLeafNode(lv_declaredOptional_3_0, grammarAccess.getPropertySetterDeclarationAccess().getDeclaredOptionalQuestionMarkKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getPropertySetterDeclarationRule());
					}
					setWithLastConsumed($current, "declaredOptional", true, "?");
				}
			)
		)?
		otherlv_4=LeftParenthesis
		{
			newLeafNode(otherlv_4, grammarAccess.getPropertySetterDeclarationAccess().getLeftParenthesisKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getPropertySetterDeclarationAccess().getFparFormalParameterParserRuleCall_3_0());
				}
				lv_fpar_5_0=norm1_FormalParameter
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPropertySetterDeclarationRule());
					}
					set(
						$current,
						"fpar",
						lv_fpar_5_0,
						"org.eclipse.n4js.N4JS.FormalParameter");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_6=RightParenthesis
		{
			newLeafNode(otherlv_6, grammarAccess.getPropertySetterDeclarationAccess().getRightParenthesisKeyword_4());
		}
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getPropertySetterDeclarationAccess().getBodyBlockParserRuleCall_5_0());
				}
				lv_body_7_0=ruleBlock
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPropertySetterDeclarationRule());
					}
					set(
						$current,
						"body",
						lv_body_7_0,
						"org.eclipse.n4js.N4JS.Block");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleParameterizedCallExpression
entryRuleParameterizedCallExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getParameterizedCallExpressionRule()); }
	iv_ruleParameterizedCallExpression=ruleParameterizedCallExpression
	{ $current=$iv_ruleParameterizedCallExpression.current; }
	EOF;

// Rule ParameterizedCallExpression
ruleParameterizedCallExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getParameterizedCallExpressionRule());
			}
			newCompositeNode(grammarAccess.getParameterizedCallExpressionAccess().getConcreteTypeArgumentsParserRuleCall_0());
		}
		this_ConcreteTypeArguments_0=ruleConcreteTypeArguments[$current]
		{
			$current = $this_ConcreteTypeArguments_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getParameterizedCallExpressionAccess().getTargetIdentifierRefParserRuleCall_1_0());
				}
				lv_target_1_0=ruleIdentifierRef
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getParameterizedCallExpressionRule());
					}
					set(
						$current,
						"target",
						lv_target_1_0,
						"org.eclipse.n4js.N4JS.IdentifierRef");
					afterParserOrEnumRuleCall();
				}
			)
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getParameterizedCallExpressionRule());
			}
			newCompositeNode(grammarAccess.getParameterizedCallExpressionAccess().getArgumentsWithParenthesesParserRuleCall_2());
		}
		this_ArgumentsWithParentheses_2=ruleArgumentsWithParentheses[$current]
		{
			$current = $this_ArgumentsWithParentheses_2.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule ParameterizedCallExpression
norm1_ParameterizedCallExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getParameterizedCallExpressionRule());
			}
			newCompositeNode(grammarAccess.getParameterizedCallExpressionAccess().getConcreteTypeArgumentsParserRuleCall_0());
		}
		this_ConcreteTypeArguments_0=ruleConcreteTypeArguments[$current]
		{
			$current = $this_ConcreteTypeArguments_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getParameterizedCallExpressionAccess().getTargetIdentifierRefParserRuleCall_1_0());
				}
				lv_target_1_0=norm1_IdentifierRef
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getParameterizedCallExpressionRule());
					}
					set(
						$current,
						"target",
						lv_target_1_0,
						"org.eclipse.n4js.N4JS.IdentifierRef");
					afterParserOrEnumRuleCall();
				}
			)
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getParameterizedCallExpressionRule());
			}
			newCompositeNode(grammarAccess.getParameterizedCallExpressionAccess().getArgumentsWithParenthesesParserRuleCall_2());
		}
		this_ArgumentsWithParentheses_2=norm1_ArgumentsWithParentheses[$current]
		{
			$current = $this_ArgumentsWithParentheses_2.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule ConcreteTypeArguments
ruleConcreteTypeArguments[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LessThanSign
		{
			newLeafNode(otherlv_0, grammarAccess.getConcreteTypeArgumentsAccess().getLessThanSignKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getConcreteTypeArgumentsAccess().getTypeArgsTypeRefParserRuleCall_1_0());
				}
				lv_typeArgs_1_0=ruleTypeRef
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getConcreteTypeArgumentsRule());
					}
					add(
						$current,
						"typeArgs",
						lv_typeArgs_1_0,
						"org.eclipse.n4js.ts.TypeExpressions.TypeRef");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_2=Comma
			{
				newLeafNode(otherlv_2, grammarAccess.getConcreteTypeArgumentsAccess().getCommaKeyword_2_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getConcreteTypeArgumentsAccess().getTypeArgsTypeRefParserRuleCall_2_1_0());
					}
					lv_typeArgs_3_0=ruleTypeRef
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getConcreteTypeArgumentsRule());
						}
						add(
							$current,
							"typeArgs",
							lv_typeArgs_3_0,
							"org.eclipse.n4js.ts.TypeExpressions.TypeRef");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		otherlv_4=GreaterThanSign
		{
			newLeafNode(otherlv_4, grammarAccess.getConcreteTypeArgumentsAccess().getGreaterThanSignKeyword_3());
		}
	)
;

// Entry rule entryRuleLeftHandSideExpression
entryRuleLeftHandSideExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getLeftHandSideExpressionRule()); }
	iv_ruleLeftHandSideExpression=ruleLeftHandSideExpression
	{ $current=$iv_ruleLeftHandSideExpression.current; }
	EOF;

// Rule LeftHandSideExpression
ruleLeftHandSideExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getLeftHandSideExpressionAccess().getMemberExpressionParserRuleCall_0());
		}
		this_MemberExpression_0=ruleMemberExpression
		{
			$current = $this_MemberExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				{
					$current = forceCreateModelElementAndSet(
						grammarAccess.getLeftHandSideExpressionAccess().getParameterizedCallExpressionTargetAction_1_0(),
						$current);
				}
			)
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getLeftHandSideExpressionRule());
				}
				newCompositeNode(grammarAccess.getLeftHandSideExpressionAccess().getArgumentsWithParenthesesParserRuleCall_1_1());
			}
			this_ArgumentsWithParentheses_2=ruleArgumentsWithParentheses[$current]
			{
				$current = $this_ArgumentsWithParentheses_2.current;
				afterParserOrEnumRuleCall();
			}
			(
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getLeftHandSideExpressionAccess().getParameterizedCallExpressionTargetAction_1_2_0_0(),
								$current);
						}
					)
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getLeftHandSideExpressionRule());
						}
						newCompositeNode(grammarAccess.getLeftHandSideExpressionAccess().getArgumentsWithParenthesesParserRuleCall_1_2_0_1());
					}
					this_ArgumentsWithParentheses_4=ruleArgumentsWithParentheses[$current]
					{
						$current = $this_ArgumentsWithParentheses_4.current;
						afterParserOrEnumRuleCall();
					}
				)
				    |
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getLeftHandSideExpressionAccess().getIndexedAccessExpressionTargetAction_1_2_1_0(),
								$current);
						}
					)
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getLeftHandSideExpressionRule());
						}
						newCompositeNode(grammarAccess.getLeftHandSideExpressionAccess().getIndexedAccessExpressionTailParserRuleCall_1_2_1_1());
					}
					this_IndexedAccessExpressionTail_6=ruleIndexedAccessExpressionTail[$current]
					{
						$current = $this_IndexedAccessExpressionTail_6.current;
						afterParserOrEnumRuleCall();
					}
				)
				    |
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getLeftHandSideExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_1_2_2_0(),
								$current);
						}
					)
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getLeftHandSideExpressionRule());
						}
						newCompositeNode(grammarAccess.getLeftHandSideExpressionAccess().getParameterizedPropertyAccessExpressionTailParserRuleCall_1_2_2_1());
					}
					this_ParameterizedPropertyAccessExpressionTail_8=ruleParameterizedPropertyAccessExpressionTail[$current]
					{
						$current = $this_ParameterizedPropertyAccessExpressionTail_8.current;
						afterParserOrEnumRuleCall();
					}
				)
				    |
				(
					(RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getLeftHandSideExpressionAccess().getTaggedTemplateStringTargetAction_1_2_3_0_0(),
									$current);
							}
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getLeftHandSideExpressionAccess().getTemplateTemplateLiteralParserRuleCall_1_2_3_0_1_0());
								}
								lv_template_10_0=ruleTemplateLiteral
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getLeftHandSideExpressionRule());
									}
									set(
										$current,
										"template",
										lv_template_10_0,
										"org.eclipse.n4js.N4JS.TemplateLiteral");
									afterParserOrEnumRuleCall();
								}
							)
						)
					)
				)
			)*
		)?
	)
;


// Rule LeftHandSideExpression
norm1_LeftHandSideExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getLeftHandSideExpressionAccess().getMemberExpressionParserRuleCall_0());
		}
		this_MemberExpression_0=norm1_MemberExpression
		{
			$current = $this_MemberExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				{
					$current = forceCreateModelElementAndSet(
						grammarAccess.getLeftHandSideExpressionAccess().getParameterizedCallExpressionTargetAction_1_0(),
						$current);
				}
			)
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getLeftHandSideExpressionRule());
				}
				newCompositeNode(grammarAccess.getLeftHandSideExpressionAccess().getArgumentsWithParenthesesParserRuleCall_1_1());
			}
			this_ArgumentsWithParentheses_2=norm1_ArgumentsWithParentheses[$current]
			{
				$current = $this_ArgumentsWithParentheses_2.current;
				afterParserOrEnumRuleCall();
			}
			(
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getLeftHandSideExpressionAccess().getParameterizedCallExpressionTargetAction_1_2_0_0(),
								$current);
						}
					)
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getLeftHandSideExpressionRule());
						}
						newCompositeNode(grammarAccess.getLeftHandSideExpressionAccess().getArgumentsWithParenthesesParserRuleCall_1_2_0_1());
					}
					this_ArgumentsWithParentheses_4=norm1_ArgumentsWithParentheses[$current]
					{
						$current = $this_ArgumentsWithParentheses_4.current;
						afterParserOrEnumRuleCall();
					}
				)
				    |
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getLeftHandSideExpressionAccess().getIndexedAccessExpressionTargetAction_1_2_1_0(),
								$current);
						}
					)
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getLeftHandSideExpressionRule());
						}
						newCompositeNode(grammarAccess.getLeftHandSideExpressionAccess().getIndexedAccessExpressionTailParserRuleCall_1_2_1_1());
					}
					this_IndexedAccessExpressionTail_6=norm1_IndexedAccessExpressionTail[$current]
					{
						$current = $this_IndexedAccessExpressionTail_6.current;
						afterParserOrEnumRuleCall();
					}
				)
				    |
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getLeftHandSideExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_1_2_2_0(),
								$current);
						}
					)
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getLeftHandSideExpressionRule());
						}
						newCompositeNode(grammarAccess.getLeftHandSideExpressionAccess().getParameterizedPropertyAccessExpressionTailParserRuleCall_1_2_2_1());
					}
					this_ParameterizedPropertyAccessExpressionTail_8=norm1_ParameterizedPropertyAccessExpressionTail[$current]
					{
						$current = $this_ParameterizedPropertyAccessExpressionTail_8.current;
						afterParserOrEnumRuleCall();
					}
				)
				    |
				(
					(RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getLeftHandSideExpressionAccess().getTaggedTemplateStringTargetAction_1_2_3_0_0(),
									$current);
							}
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getLeftHandSideExpressionAccess().getTemplateTemplateLiteralParserRuleCall_1_2_3_0_1_0());
								}
								lv_template_10_0=norm1_TemplateLiteral
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getLeftHandSideExpressionRule());
									}
									set(
										$current,
										"template",
										lv_template_10_0,
										"org.eclipse.n4js.N4JS.TemplateLiteral");
									afterParserOrEnumRuleCall();
								}
							)
						)
					)
				)
			)*
		)?
	)
;


// Rule ArgumentsWithParentheses
ruleArgumentsWithParentheses[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LeftParenthesis
		{
			newLeafNode(otherlv_0, grammarAccess.getArgumentsWithParenthesesAccess().getLeftParenthesisKeyword_0());
		}
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getArgumentsWithParenthesesRule());
				}
				newCompositeNode(grammarAccess.getArgumentsWithParenthesesAccess().getArgumentsParserRuleCall_1());
			}
			this_Arguments_1=ruleArguments[$current]
			{
				$current = $this_Arguments_1.current;
				afterParserOrEnumRuleCall();
			}
		)?
		otherlv_2=RightParenthesis
		{
			newLeafNode(otherlv_2, grammarAccess.getArgumentsWithParenthesesAccess().getRightParenthesisKeyword_2());
		}
	)
;


// Rule ArgumentsWithParentheses
norm1_ArgumentsWithParentheses[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LeftParenthesis
		{
			newLeafNode(otherlv_0, grammarAccess.getArgumentsWithParenthesesAccess().getLeftParenthesisKeyword_0());
		}
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getArgumentsWithParenthesesRule());
				}
				newCompositeNode(grammarAccess.getArgumentsWithParenthesesAccess().getArgumentsParserRuleCall_1());
			}
			this_Arguments_1=norm1_Arguments[$current]
			{
				$current = $this_Arguments_1.current;
				afterParserOrEnumRuleCall();
			}
		)?
		otherlv_2=RightParenthesis
		{
			newLeafNode(otherlv_2, grammarAccess.getArgumentsWithParenthesesAccess().getRightParenthesisKeyword_2());
		}
	)
;


// Rule Arguments
ruleArguments[EObject in_current]  returns [EObject current=in_current]
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
					newCompositeNode(grammarAccess.getArgumentsAccess().getArgumentsArgumentParserRuleCall_0_0());
				}
				lv_arguments_0_0=ruleArgument
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getArgumentsRule());
					}
					add(
						$current,
						"arguments",
						lv_arguments_0_0,
						"org.eclipse.n4js.N4JS.Argument");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_1=Comma
			{
				newLeafNode(otherlv_1, grammarAccess.getArgumentsAccess().getCommaKeyword_1_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getArgumentsAccess().getArgumentsArgumentParserRuleCall_1_1_0());
					}
					lv_arguments_2_0=ruleArgument
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getArgumentsRule());
						}
						add(
							$current,
							"arguments",
							lv_arguments_2_0,
							"org.eclipse.n4js.N4JS.Argument");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule Arguments
norm1_Arguments[EObject in_current]  returns [EObject current=in_current]
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
					newCompositeNode(grammarAccess.getArgumentsAccess().getArgumentsArgumentParserRuleCall_0_0());
				}
				lv_arguments_0_0=norm1_Argument
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getArgumentsRule());
					}
					add(
						$current,
						"arguments",
						lv_arguments_0_0,
						"org.eclipse.n4js.N4JS.Argument");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_1=Comma
			{
				newLeafNode(otherlv_1, grammarAccess.getArgumentsAccess().getCommaKeyword_1_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getArgumentsAccess().getArgumentsArgumentParserRuleCall_1_1_0());
					}
					lv_arguments_2_0=norm1_Argument
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getArgumentsRule());
						}
						add(
							$current,
							"arguments",
							lv_arguments_2_0,
							"org.eclipse.n4js.N4JS.Argument");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleArgument
entryRuleArgument returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getArgumentRule()); }
	iv_ruleArgument=ruleArgument
	{ $current=$iv_ruleArgument.current; }
	EOF;

// Rule Argument
ruleArgument returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_spread_0_0=FullStopFullStopFullStop
				{
					newLeafNode(lv_spread_0_0, grammarAccess.getArgumentAccess().getSpreadFullStopFullStopFullStopKeyword_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getArgumentRule());
					}
					setWithLastConsumed($current, "spread", true, "...");
				}
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getArgumentAccess().getExpressionAssignmentExpressionParserRuleCall_1_0());
				}
				lv_expression_1_0=norm1_AssignmentExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getArgumentRule());
					}
					set(
						$current,
						"expression",
						lv_expression_1_0,
						"org.eclipse.n4js.N4JS.AssignmentExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule Argument
norm1_Argument returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_spread_0_0=FullStopFullStopFullStop
				{
					newLeafNode(lv_spread_0_0, grammarAccess.getArgumentAccess().getSpreadFullStopFullStopFullStopKeyword_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getArgumentRule());
					}
					setWithLastConsumed($current, "spread", true, "...");
				}
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getArgumentAccess().getExpressionAssignmentExpressionParserRuleCall_1_0());
				}
				lv_expression_1_0=norm3_AssignmentExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getArgumentRule());
					}
					set(
						$current,
						"expression",
						lv_expression_1_0,
						"org.eclipse.n4js.N4JS.AssignmentExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleMemberExpression
entryRuleMemberExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getMemberExpressionRule()); }
	iv_ruleMemberExpression=ruleMemberExpression
	{ $current=$iv_ruleMemberExpression.current; }
	EOF;

// Rule MemberExpression
ruleMemberExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				((
					(
					)
					New
					FullStop
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElement(
								grammarAccess.getMemberExpressionAccess().getNewTargetAction_0_0_0_0(),
								$current);
						}
					)
					otherlv_1=New
					{
						newLeafNode(otherlv_1, grammarAccess.getMemberExpressionAccess().getNewKeyword_0_0_0_1());
					}
					otherlv_2=FullStop
					{
						newLeafNode(otherlv_2, grammarAccess.getMemberExpressionAccess().getFullStopKeyword_0_0_0_2());
					}
				)
			)
			otherlv_3=Target
			{
				newLeafNode(otherlv_3, grammarAccess.getMemberExpressionAccess().getTargetKeyword_0_1());
			}
		)
		    |
		(
			(
				((
					(
					)
					New
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElement(
								grammarAccess.getMemberExpressionAccess().getNewExpressionAction_1_0_0_0(),
								$current);
						}
					)
					otherlv_5=New
					{
						newLeafNode(otherlv_5, grammarAccess.getMemberExpressionAccess().getNewKeyword_1_0_0_1());
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getMemberExpressionAccess().getCalleeMemberExpressionParserRuleCall_1_1_0());
					}
					lv_callee_6_0=ruleMemberExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getMemberExpressionRule());
						}
						set(
							$current,
							"callee",
							lv_callee_6_0,
							"org.eclipse.n4js.N4JS.MemberExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				(LessThanSign)=>
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getMemberExpressionRule());
					}
					newCompositeNode(grammarAccess.getMemberExpressionAccess().getConcreteTypeArgumentsParserRuleCall_1_2());
				}
				this_ConcreteTypeArguments_7=ruleConcreteTypeArguments[$current]
				{
					$current = $this_ConcreteTypeArguments_7.current;
					afterParserOrEnumRuleCall();
				}
			)?
			(
				(
					((
						LeftParenthesis
					)
					)=>
					(
						lv_withArgs_8_0=LeftParenthesis
						{
							newLeafNode(lv_withArgs_8_0, grammarAccess.getMemberExpressionAccess().getWithArgsLeftParenthesisKeyword_1_3_0_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getMemberExpressionRule());
							}
							setWithLastConsumed($current, "withArgs", true, "(");
						}
					)
				)
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getMemberExpressionRule());
						}
						newCompositeNode(grammarAccess.getMemberExpressionAccess().getArgumentsParserRuleCall_1_3_1());
					}
					this_Arguments_9=ruleArguments[$current]
					{
						$current = $this_Arguments_9.current;
						afterParserOrEnumRuleCall();
					}
				)?
				otherlv_10=RightParenthesis
				{
					newLeafNode(otherlv_10, grammarAccess.getMemberExpressionAccess().getRightParenthesisKeyword_1_3_2());
				}
				(
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTargetAction_1_3_3_0_0(),
									$current);
							}
						)
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getMemberExpressionRule());
							}
							newCompositeNode(grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTailParserRuleCall_1_3_3_0_1());
						}
						this_IndexedAccessExpressionTail_12=ruleIndexedAccessExpressionTail[$current]
						{
							$current = $this_IndexedAccessExpressionTail_12.current;
							afterParserOrEnumRuleCall();
						}
					)
					    |
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_1_3_3_1_0(),
									$current);
							}
						)
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getMemberExpressionRule());
							}
							newCompositeNode(grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTailParserRuleCall_1_3_3_1_1());
						}
						this_ParameterizedPropertyAccessExpressionTail_14=ruleParameterizedPropertyAccessExpressionTail[$current]
						{
							$current = $this_ParameterizedPropertyAccessExpressionTail_14.current;
							afterParserOrEnumRuleCall();
						}
					)
					    |
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getMemberExpressionAccess().getTaggedTemplateStringTargetAction_1_3_3_2_0(),
									$current);
							}
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getMemberExpressionAccess().getTemplateTemplateLiteralParserRuleCall_1_3_3_2_1_0());
								}
								lv_template_16_0=ruleTemplateLiteral
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getMemberExpressionRule());
									}
									set(
										$current,
										"template",
										lv_template_16_0,
										"org.eclipse.n4js.N4JS.TemplateLiteral");
									afterParserOrEnumRuleCall();
								}
							)
						)
					)
				)*
			)?
		)
		    |
		(
			{
				newCompositeNode(grammarAccess.getMemberExpressionAccess().getPrimaryExpressionParserRuleCall_2_0());
			}
			this_PrimaryExpression_17=rulePrimaryExpression
			{
				$current = $this_PrimaryExpression_17.current;
				afterParserOrEnumRuleCall();
			}
			(
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTargetAction_2_1_0_0(),
								$current);
						}
					)
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getMemberExpressionRule());
						}
						newCompositeNode(grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTailParserRuleCall_2_1_0_1());
					}
					this_IndexedAccessExpressionTail_19=ruleIndexedAccessExpressionTail[$current]
					{
						$current = $this_IndexedAccessExpressionTail_19.current;
						afterParserOrEnumRuleCall();
					}
				)
				    |
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_2_1_1_0(),
								$current);
						}
					)
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getMemberExpressionRule());
						}
						newCompositeNode(grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTailParserRuleCall_2_1_1_1());
					}
					this_ParameterizedPropertyAccessExpressionTail_21=ruleParameterizedPropertyAccessExpressionTail[$current]
					{
						$current = $this_ParameterizedPropertyAccessExpressionTail_21.current;
						afterParserOrEnumRuleCall();
					}
				)
				    |
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getMemberExpressionAccess().getTaggedTemplateStringTargetAction_2_1_2_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getMemberExpressionAccess().getTemplateTemplateLiteralParserRuleCall_2_1_2_1_0());
							}
							lv_template_23_0=ruleTemplateLiteral
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getMemberExpressionRule());
								}
								set(
									$current,
									"template",
									lv_template_23_0,
									"org.eclipse.n4js.N4JS.TemplateLiteral");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)*
		)
	)
;


// Rule MemberExpression
norm1_MemberExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				((
					(
					)
					New
					FullStop
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElement(
								grammarAccess.getMemberExpressionAccess().getNewTargetAction_0_0_0_0(),
								$current);
						}
					)
					otherlv_1=New
					{
						newLeafNode(otherlv_1, grammarAccess.getMemberExpressionAccess().getNewKeyword_0_0_0_1());
					}
					otherlv_2=FullStop
					{
						newLeafNode(otherlv_2, grammarAccess.getMemberExpressionAccess().getFullStopKeyword_0_0_0_2());
					}
				)
			)
			otherlv_3=Target
			{
				newLeafNode(otherlv_3, grammarAccess.getMemberExpressionAccess().getTargetKeyword_0_1());
			}
		)
		    |
		(
			(
				((
					(
					)
					New
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElement(
								grammarAccess.getMemberExpressionAccess().getNewExpressionAction_1_0_0_0(),
								$current);
						}
					)
					otherlv_5=New
					{
						newLeafNode(otherlv_5, grammarAccess.getMemberExpressionAccess().getNewKeyword_1_0_0_1());
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getMemberExpressionAccess().getCalleeMemberExpressionParserRuleCall_1_1_0());
					}
					lv_callee_6_0=norm1_MemberExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getMemberExpressionRule());
						}
						set(
							$current,
							"callee",
							lv_callee_6_0,
							"org.eclipse.n4js.N4JS.MemberExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				(LessThanSign)=>
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getMemberExpressionRule());
					}
					newCompositeNode(grammarAccess.getMemberExpressionAccess().getConcreteTypeArgumentsParserRuleCall_1_2());
				}
				this_ConcreteTypeArguments_7=ruleConcreteTypeArguments[$current]
				{
					$current = $this_ConcreteTypeArguments_7.current;
					afterParserOrEnumRuleCall();
				}
			)?
			(
				(
					((
						LeftParenthesis
					)
					)=>
					(
						lv_withArgs_8_0=LeftParenthesis
						{
							newLeafNode(lv_withArgs_8_0, grammarAccess.getMemberExpressionAccess().getWithArgsLeftParenthesisKeyword_1_3_0_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getMemberExpressionRule());
							}
							setWithLastConsumed($current, "withArgs", true, "(");
						}
					)
				)
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getMemberExpressionRule());
						}
						newCompositeNode(grammarAccess.getMemberExpressionAccess().getArgumentsParserRuleCall_1_3_1());
					}
					this_Arguments_9=norm1_Arguments[$current]
					{
						$current = $this_Arguments_9.current;
						afterParserOrEnumRuleCall();
					}
				)?
				otherlv_10=RightParenthesis
				{
					newLeafNode(otherlv_10, grammarAccess.getMemberExpressionAccess().getRightParenthesisKeyword_1_3_2());
				}
				(
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTargetAction_1_3_3_0_0(),
									$current);
							}
						)
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getMemberExpressionRule());
							}
							newCompositeNode(grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTailParserRuleCall_1_3_3_0_1());
						}
						this_IndexedAccessExpressionTail_12=norm1_IndexedAccessExpressionTail[$current]
						{
							$current = $this_IndexedAccessExpressionTail_12.current;
							afterParserOrEnumRuleCall();
						}
					)
					    |
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_1_3_3_1_0(),
									$current);
							}
						)
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getMemberExpressionRule());
							}
							newCompositeNode(grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTailParserRuleCall_1_3_3_1_1());
						}
						this_ParameterizedPropertyAccessExpressionTail_14=norm1_ParameterizedPropertyAccessExpressionTail[$current]
						{
							$current = $this_ParameterizedPropertyAccessExpressionTail_14.current;
							afterParserOrEnumRuleCall();
						}
					)
					    |
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getMemberExpressionAccess().getTaggedTemplateStringTargetAction_1_3_3_2_0(),
									$current);
							}
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getMemberExpressionAccess().getTemplateTemplateLiteralParserRuleCall_1_3_3_2_1_0());
								}
								lv_template_16_0=norm1_TemplateLiteral
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getMemberExpressionRule());
									}
									set(
										$current,
										"template",
										lv_template_16_0,
										"org.eclipse.n4js.N4JS.TemplateLiteral");
									afterParserOrEnumRuleCall();
								}
							)
						)
					)
				)*
			)?
		)
		    |
		(
			{
				newCompositeNode(grammarAccess.getMemberExpressionAccess().getPrimaryExpressionParserRuleCall_2_0());
			}
			this_PrimaryExpression_17=norm1_PrimaryExpression
			{
				$current = $this_PrimaryExpression_17.current;
				afterParserOrEnumRuleCall();
			}
			(
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTargetAction_2_1_0_0(),
								$current);
						}
					)
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getMemberExpressionRule());
						}
						newCompositeNode(grammarAccess.getMemberExpressionAccess().getIndexedAccessExpressionTailParserRuleCall_2_1_0_1());
					}
					this_IndexedAccessExpressionTail_19=norm1_IndexedAccessExpressionTail[$current]
					{
						$current = $this_IndexedAccessExpressionTail_19.current;
						afterParserOrEnumRuleCall();
					}
				)
				    |
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_2_1_1_0(),
								$current);
						}
					)
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getMemberExpressionRule());
						}
						newCompositeNode(grammarAccess.getMemberExpressionAccess().getParameterizedPropertyAccessExpressionTailParserRuleCall_2_1_1_1());
					}
					this_ParameterizedPropertyAccessExpressionTail_21=norm1_ParameterizedPropertyAccessExpressionTail[$current]
					{
						$current = $this_ParameterizedPropertyAccessExpressionTail_21.current;
						afterParserOrEnumRuleCall();
					}
				)
				    |
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getMemberExpressionAccess().getTaggedTemplateStringTargetAction_2_1_2_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getMemberExpressionAccess().getTemplateTemplateLiteralParserRuleCall_2_1_2_1_0());
							}
							lv_template_23_0=norm1_TemplateLiteral
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getMemberExpressionRule());
								}
								set(
									$current,
									"template",
									lv_template_23_0,
									"org.eclipse.n4js.N4JS.TemplateLiteral");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)*
		)
	)
;


// Rule IndexedAccessExpressionTail
ruleIndexedAccessExpressionTail[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LeftSquareBracket
		{
			newLeafNode(otherlv_0, grammarAccess.getIndexedAccessExpressionTailAccess().getLeftSquareBracketKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getIndexedAccessExpressionTailAccess().getIndexExpressionParserRuleCall_1_0());
				}
				lv_index_1_0=norm1_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getIndexedAccessExpressionTailRule());
					}
					set(
						$current,
						"index",
						lv_index_1_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_2=RightSquareBracket
		{
			newLeafNode(otherlv_2, grammarAccess.getIndexedAccessExpressionTailAccess().getRightSquareBracketKeyword_2());
		}
	)
;


// Rule IndexedAccessExpressionTail
norm1_IndexedAccessExpressionTail[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LeftSquareBracket
		{
			newLeafNode(otherlv_0, grammarAccess.getIndexedAccessExpressionTailAccess().getLeftSquareBracketKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getIndexedAccessExpressionTailAccess().getIndexExpressionParserRuleCall_1_0());
				}
				lv_index_1_0=norm3_Expression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getIndexedAccessExpressionTailRule());
					}
					set(
						$current,
						"index",
						lv_index_1_0,
						"org.eclipse.n4js.N4JS.Expression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_2=RightSquareBracket
		{
			newLeafNode(otherlv_2, grammarAccess.getIndexedAccessExpressionTailAccess().getRightSquareBracketKeyword_2());
		}
	)
;


// Rule ParameterizedPropertyAccessExpressionTail
ruleParameterizedPropertyAccessExpressionTail[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=FullStop
		{
			newLeafNode(otherlv_0, grammarAccess.getParameterizedPropertyAccessExpressionTailAccess().getFullStopKeyword_0());
		}
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getParameterizedPropertyAccessExpressionTailRule());
				}
				newCompositeNode(grammarAccess.getParameterizedPropertyAccessExpressionTailAccess().getConcreteTypeArgumentsParserRuleCall_1());
			}
			this_ConcreteTypeArguments_1=ruleConcreteTypeArguments[$current]
			{
				$current = $this_ConcreteTypeArguments_1.current;
				afterParserOrEnumRuleCall();
			}
		)?
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getParameterizedPropertyAccessExpressionTailRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getParameterizedPropertyAccessExpressionTailAccess().getPropertyIdentifiableElementCrossReference_2_0());
				}
				ruleIdentifierName
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule ParameterizedPropertyAccessExpressionTail
norm1_ParameterizedPropertyAccessExpressionTail[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=FullStop
		{
			newLeafNode(otherlv_0, grammarAccess.getParameterizedPropertyAccessExpressionTailAccess().getFullStopKeyword_0());
		}
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getParameterizedPropertyAccessExpressionTailRule());
				}
				newCompositeNode(grammarAccess.getParameterizedPropertyAccessExpressionTailAccess().getConcreteTypeArgumentsParserRuleCall_1());
			}
			this_ConcreteTypeArguments_1=ruleConcreteTypeArguments[$current]
			{
				$current = $this_ConcreteTypeArguments_1.current;
				afterParserOrEnumRuleCall();
			}
		)?
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getParameterizedPropertyAccessExpressionTailRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getParameterizedPropertyAccessExpressionTailAccess().getPropertyIdentifiableElementCrossReference_2_0());
				}
				ruleIdentifierName
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRulePostfixExpression
entryRulePostfixExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPostfixExpressionRule()); }
	iv_rulePostfixExpression=rulePostfixExpression
	{ $current=$iv_rulePostfixExpression.current; }
	EOF;

// Rule PostfixExpression
rulePostfixExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getPostfixExpressionAccess().getLeftHandSideExpressionParserRuleCall_0());
		}
		this_LeftHandSideExpression_0=ruleLeftHandSideExpression  { if (input.LA(1) == PlusSignPlusSign || input.LA(1) == HyphenMinusHyphenMinus) promoteEOL(); }
		{
			$current = $this_LeftHandSideExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			((
				(
				)
				(
					(
						rulePostfixOperator
					)
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getPostfixExpressionAccess().getPostfixExpressionExpressionAction_1_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getPostfixExpressionAccess().getOpPostfixOperatorEnumRuleCall_1_0_1_0());
						}
						lv_op_2_0=rulePostfixOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getPostfixExpressionRule());
							}
							set(
								$current,
								"op",
								lv_op_2_0,
								"org.eclipse.n4js.N4JS.PostfixOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)?
	)
;


// Rule PostfixExpression
norm1_PostfixExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getPostfixExpressionAccess().getLeftHandSideExpressionParserRuleCall_0());
		}
		this_LeftHandSideExpression_0=norm1_LeftHandSideExpression
		{
			$current = $this_LeftHandSideExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			((
				(
				)
				(
					(
						rulePostfixOperator
					)
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getPostfixExpressionAccess().getPostfixExpressionExpressionAction_1_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getPostfixExpressionAccess().getOpPostfixOperatorEnumRuleCall_1_0_1_0());
						}
						lv_op_2_0=rulePostfixOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getPostfixExpressionRule());
							}
							set(
								$current,
								"op",
								lv_op_2_0,
								"org.eclipse.n4js.N4JS.PostfixOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)?
	)
;

// Entry rule entryRuleCastExpression
entryRuleCastExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getCastExpressionRule()); }
	iv_ruleCastExpression=ruleCastExpression
	{ $current=$iv_ruleCastExpression.current; }
	EOF;

// Rule CastExpression
ruleCastExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getCastExpressionAccess().getPostfixExpressionParserRuleCall_0());
		}
		this_PostfixExpression_0=rulePostfixExpression
		{
			$current = $this_PostfixExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					As
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getCastExpressionAccess().getCastExpressionExpressionAction_1_0_0_0(),
								$current);
						}
					)
					otherlv_2=As
					{
						newLeafNode(otherlv_2, grammarAccess.getCastExpressionAccess().getAsKeyword_1_0_0_1());
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getCastExpressionAccess().getTargetTypeRefArrayTypeExpressionParserRuleCall_1_1_0());
					}
					lv_targetTypeRef_3_0=ruleArrayTypeExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getCastExpressionRule());
						}
						set(
							$current,
							"targetTypeRef",
							lv_targetTypeRef_3_0,
							"org.eclipse.n4js.ts.TypeExpressions.ArrayTypeExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;


// Rule CastExpression
norm1_CastExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getCastExpressionAccess().getPostfixExpressionParserRuleCall_0());
		}
		this_PostfixExpression_0=norm1_PostfixExpression
		{
			$current = $this_PostfixExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					As
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getCastExpressionAccess().getCastExpressionExpressionAction_1_0_0_0(),
								$current);
						}
					)
					otherlv_2=As
					{
						newLeafNode(otherlv_2, grammarAccess.getCastExpressionAccess().getAsKeyword_1_0_0_1());
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getCastExpressionAccess().getTargetTypeRefArrayTypeExpressionParserRuleCall_1_1_0());
					}
					lv_targetTypeRef_3_0=ruleArrayTypeExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getCastExpressionRule());
						}
						set(
							$current,
							"targetTypeRef",
							lv_targetTypeRef_3_0,
							"org.eclipse.n4js.ts.TypeExpressions.ArrayTypeExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;

// Entry rule entryRuleUnaryExpression
entryRuleUnaryExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getUnaryExpressionRule()); }
	iv_ruleUnaryExpression=ruleUnaryExpression
	{ $current=$iv_ruleUnaryExpression.current; }
	EOF;

// Rule UnaryExpression
ruleUnaryExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getUnaryExpressionAccess().getCastExpressionParserRuleCall_0());
		}
		this_CastExpression_0=ruleCastExpression
		{
			$current = $this_CastExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			(
				{
					$current = forceCreateModelElement(
						grammarAccess.getUnaryExpressionAccess().getUnaryExpressionAction_1_0(),
						$current);
				}
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getUnaryExpressionAccess().getOpUnaryOperatorEnumRuleCall_1_1_0());
					}
					lv_op_2_0=ruleUnaryOperator
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getUnaryExpressionRule());
						}
						set(
							$current,
							"op",
							lv_op_2_0,
							"org.eclipse.n4js.N4JS.UnaryOperator");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getUnaryExpressionAccess().getExpressionUnaryExpressionParserRuleCall_1_2_0());
					}
					lv_expression_3_0=ruleUnaryExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getUnaryExpressionRule());
						}
						set(
							$current,
							"expression",
							lv_expression_3_0,
							"org.eclipse.n4js.N4JS.UnaryExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;


// Rule UnaryExpression
norm1_UnaryExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getUnaryExpressionAccess().getCastExpressionParserRuleCall_0());
		}
		this_CastExpression_0=norm1_CastExpression
		{
			$current = $this_CastExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			(
				{
					$current = forceCreateModelElement(
						grammarAccess.getUnaryExpressionAccess().getUnaryExpressionAction_1_0(),
						$current);
				}
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getUnaryExpressionAccess().getOpUnaryOperatorEnumRuleCall_1_1_0());
					}
					lv_op_2_0=ruleUnaryOperator
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getUnaryExpressionRule());
						}
						set(
							$current,
							"op",
							lv_op_2_0,
							"org.eclipse.n4js.N4JS.UnaryOperator");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getUnaryExpressionAccess().getExpressionUnaryExpressionParserRuleCall_1_2_0());
					}
					lv_expression_3_0=norm1_UnaryExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getUnaryExpressionRule());
						}
						set(
							$current,
							"expression",
							lv_expression_3_0,
							"org.eclipse.n4js.N4JS.UnaryExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;

// Entry rule entryRuleMultiplicativeExpression
entryRuleMultiplicativeExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getMultiplicativeExpressionRule()); }
	iv_ruleMultiplicativeExpression=ruleMultiplicativeExpression
	{ $current=$iv_ruleMultiplicativeExpression.current; }
	EOF;

// Rule MultiplicativeExpression
ruleMultiplicativeExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getUnaryExpressionParserRuleCall_0());
		}
		this_UnaryExpression_0=ruleUnaryExpression
		{
			$current = $this_UnaryExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleMultiplicativeOperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getOpMultiplicativeOperatorEnumRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleMultiplicativeOperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getMultiplicativeExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.MultiplicativeOperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getRhsUnaryExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=ruleUnaryExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getMultiplicativeExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.UnaryExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule MultiplicativeExpression
norm1_MultiplicativeExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getUnaryExpressionParserRuleCall_0());
		}
		this_UnaryExpression_0=norm1_UnaryExpression
		{
			$current = $this_UnaryExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleMultiplicativeOperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getOpMultiplicativeOperatorEnumRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleMultiplicativeOperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getMultiplicativeExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.MultiplicativeOperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getRhsUnaryExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=norm1_UnaryExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getMultiplicativeExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.UnaryExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleAdditiveExpression
entryRuleAdditiveExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAdditiveExpressionRule()); }
	iv_ruleAdditiveExpression=ruleAdditiveExpression
	{ $current=$iv_ruleAdditiveExpression.current; }
	EOF;

// Rule AdditiveExpression
ruleAdditiveExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getAdditiveExpressionAccess().getMultiplicativeExpressionParserRuleCall_0());
		}
		this_MultiplicativeExpression_0=ruleMultiplicativeExpression
		{
			$current = $this_MultiplicativeExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleAdditiveOperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getAdditiveExpressionAccess().getOpAdditiveOperatorEnumRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleAdditiveOperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getAdditiveExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.AdditiveOperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getAdditiveExpressionAccess().getRhsMultiplicativeExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=ruleMultiplicativeExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getAdditiveExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.MultiplicativeExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule AdditiveExpression
norm1_AdditiveExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getAdditiveExpressionAccess().getMultiplicativeExpressionParserRuleCall_0());
		}
		this_MultiplicativeExpression_0=norm1_MultiplicativeExpression
		{
			$current = $this_MultiplicativeExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleAdditiveOperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getAdditiveExpressionAccess().getOpAdditiveOperatorEnumRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleAdditiveOperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getAdditiveExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.AdditiveOperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getAdditiveExpressionAccess().getRhsMultiplicativeExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=norm1_MultiplicativeExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getAdditiveExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.MultiplicativeExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleShiftExpression
entryRuleShiftExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getShiftExpressionRule()); }
	iv_ruleShiftExpression=ruleShiftExpression
	{ $current=$iv_ruleShiftExpression.current; }
	EOF;

// Rule ShiftExpression
ruleShiftExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getShiftExpressionAccess().getAdditiveExpressionParserRuleCall_0());
		}
		this_AdditiveExpression_0=ruleAdditiveExpression
		{
			$current = $this_AdditiveExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			((
				(
				)
				(
					(
						ruleShiftOperator
					)
				)
				(
					(
						ruleAdditiveExpression
					)
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getShiftExpressionAccess().getShiftExpressionLhsAction_1_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getShiftExpressionAccess().getOpShiftOperatorParserRuleCall_1_0_1_0());
						}
						lv_op_2_0=ruleShiftOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getShiftExpressionRule());
							}
							set(
								$current,
								"op",
								lv_op_2_0,
								"org.eclipse.n4js.N4JS.ShiftOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getShiftExpressionAccess().getRhsAdditiveExpressionParserRuleCall_1_0_2_0());
						}
						lv_rhs_3_0=ruleAdditiveExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getShiftExpressionRule());
							}
							set(
								$current,
								"rhs",
								lv_rhs_3_0,
								"org.eclipse.n4js.N4JS.AdditiveExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)*
	)
;


// Rule ShiftExpression
norm1_ShiftExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getShiftExpressionAccess().getAdditiveExpressionParserRuleCall_0());
		}
		this_AdditiveExpression_0=norm1_AdditiveExpression
		{
			$current = $this_AdditiveExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			((
				(
				)
				(
					(
						ruleShiftOperator
					)
				)
				(
					(
						norm1_AdditiveExpression
					)
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getShiftExpressionAccess().getShiftExpressionLhsAction_1_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getShiftExpressionAccess().getOpShiftOperatorParserRuleCall_1_0_1_0());
						}
						lv_op_2_0=ruleShiftOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getShiftExpressionRule());
							}
							set(
								$current,
								"op",
								lv_op_2_0,
								"org.eclipse.n4js.N4JS.ShiftOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getShiftExpressionAccess().getRhsAdditiveExpressionParserRuleCall_1_0_2_0());
						}
						lv_rhs_3_0=norm1_AdditiveExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getShiftExpressionRule());
							}
							set(
								$current,
								"rhs",
								lv_rhs_3_0,
								"org.eclipse.n4js.N4JS.AdditiveExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)*
	)
;

// Entry rule entryRuleShiftOperator
entryRuleShiftOperator returns [String current=null]:
	{ newCompositeNode(grammarAccess.getShiftOperatorRule()); }
	iv_ruleShiftOperator=ruleShiftOperator
	{ $current=$iv_ruleShiftOperator.current.getText(); }
	EOF;

// Rule ShiftOperator
ruleShiftOperator returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			kw=GreaterThanSign
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getShiftOperatorAccess().getGreaterThanSignKeyword_0_0());
			}
			kw=GreaterThanSign
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getShiftOperatorAccess().getGreaterThanSignKeyword_0_1());
			}
			(
				kw=GreaterThanSign
				{
					$current.merge(kw);
					newLeafNode(kw, grammarAccess.getShiftOperatorAccess().getGreaterThanSignKeyword_0_2());
				}
			)?
		)
		    |
		kw=LessThanSignLessThanSign
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getShiftOperatorAccess().getLessThanSignLessThanSignKeyword_1());
		}
	)
;

// Entry rule entryRuleRelationalExpression
entryRuleRelationalExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getRelationalExpressionRule()); }
	iv_ruleRelationalExpression=ruleRelationalExpression
	{ $current=$iv_ruleRelationalExpression.current; }
	EOF;

// Rule RelationalExpression
ruleRelationalExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getRelationalExpressionAccess().getShiftExpressionParserRuleCall_0());
		}
		this_ShiftExpression_0=ruleShiftExpression
		{
			$current = $this_ShiftExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			((
				(
				)
				(
					(
						ruleRelationalOperator
					)
				)
				(
					New
					    |
					This_1
					    |
					Super
					    |
					Yield
					    |
					Get
					    |
					Set
					    |
					Let
					    |
					Project
					    |
					External
					    |
					Abstract
					    |
					Static
					    |
					As
					    |
					From
					    |
					Constructor
					    |
					Of
					    |
					Target
					    |
					Type
					    |
					Union
					    |
					Intersection
					    |
					This
					    |
					Promisify
					    |
					Await
					    |
					Async
					    |
					Implements
					    |
					Interface
					    |
					Private
					    |
					Protected
					    |
					Public
					    |
					Out
					    |
					LessThanSign
					    |
					True
					    |
					False
					    |
					Null
					    |
					Solidus
					    |
					SolidusEqualsSign
					    |
					LeftSquareBracket
					    |
					LeftCurlyBracket
					    |
					LeftParenthesis
					    |
					CommercialAt
					    |
					Function
					    |
					Class
					    |
					Delete
					    |
					Void
					    |
					Typeof
					    |
					PlusSignPlusSign
					    |
					HyphenMinusHyphenMinus
					    |
					PlusSign
					    |
					HyphenMinus
					    |
					Tilde
					    |
					ExclamationMark
					    |
					RULE_IDENTIFIER
					    |
					RULE_DOUBLE
					    |
					RULE_INT
					    |
					RULE_BINARY_INT
					    |
					RULE_OCTAL_INT
					    |
					RULE_LEGACY_OCTAL_INT
					    |
					RULE_HEX_INT
					    |
					RULE_SCIENTIFIC_INT
					    |
					RULE_STRING
					    |
					RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
					    |
					RULE_TEMPLATE_HEAD
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLhsAction_1_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getRelationalExpressionAccess().getOpRelationalOperatorParserRuleCall_1_0_1_0());
						}
						lv_op_2_0=ruleRelationalOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getRelationalExpressionRule());
							}
							set(
								$current,
								"op",
								lv_op_2_0,
								"org.eclipse.n4js.N4JS.RelationalOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(New | This_1 | Super | Yield | Get | Set | Let | Project | External | Abstract | Static | As | From | Constructor | Of | Target | Type | Union | Intersection | This | Promisify | Await | Async | Implements | Interface | Private | Protected | Public | Out | LessThanSign | True | False | Null | Solidus | SolidusEqualsSign | LeftSquareBracket | LeftCurlyBracket | LeftParenthesis | CommercialAt | Function | Class | Delete | Void | Typeof | PlusSignPlusSign | HyphenMinusHyphenMinus | PlusSign | HyphenMinus | Tilde | ExclamationMark | RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
					(
						{
							newCompositeNode(grammarAccess.getRelationalExpressionAccess().getRhsShiftExpressionParserRuleCall_1_0_2_0());
						}
						lv_rhs_3_0=ruleShiftExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getRelationalExpressionRule());
							}
							set(
								$current,
								"rhs",
								lv_rhs_3_0,
								"org.eclipse.n4js.N4JS.ShiftExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)*
	)
;


// Rule RelationalExpression
norm1_RelationalExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getRelationalExpressionAccess().getShiftExpressionParserRuleCall_0());
		}
		this_ShiftExpression_0=ruleShiftExpression
		{
			$current = $this_ShiftExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			((
				(
				)
				(
					(
						norm1_RelationalOperator
					)
				)
				(
					New
					    |
					This_1
					    |
					Super
					    |
					Yield
					    |
					Get
					    |
					Set
					    |
					Let
					    |
					Project
					    |
					External
					    |
					Abstract
					    |
					Static
					    |
					As
					    |
					From
					    |
					Constructor
					    |
					Of
					    |
					Target
					    |
					Type
					    |
					Union
					    |
					Intersection
					    |
					This
					    |
					Promisify
					    |
					Await
					    |
					Async
					    |
					Implements
					    |
					Interface
					    |
					Private
					    |
					Protected
					    |
					Public
					    |
					Out
					    |
					LessThanSign
					    |
					True
					    |
					False
					    |
					Null
					    |
					Solidus
					    |
					SolidusEqualsSign
					    |
					LeftSquareBracket
					    |
					LeftCurlyBracket
					    |
					LeftParenthesis
					    |
					CommercialAt
					    |
					Function
					    |
					Class
					    |
					Delete
					    |
					Void
					    |
					Typeof
					    |
					PlusSignPlusSign
					    |
					HyphenMinusHyphenMinus
					    |
					PlusSign
					    |
					HyphenMinus
					    |
					Tilde
					    |
					ExclamationMark
					    |
					RULE_IDENTIFIER
					    |
					RULE_DOUBLE
					    |
					RULE_INT
					    |
					RULE_BINARY_INT
					    |
					RULE_OCTAL_INT
					    |
					RULE_LEGACY_OCTAL_INT
					    |
					RULE_HEX_INT
					    |
					RULE_SCIENTIFIC_INT
					    |
					RULE_STRING
					    |
					RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
					    |
					RULE_TEMPLATE_HEAD
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLhsAction_1_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getRelationalExpressionAccess().getOpRelationalOperatorParserRuleCall_1_0_1_0());
						}
						lv_op_2_0=norm1_RelationalOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getRelationalExpressionRule());
							}
							set(
								$current,
								"op",
								lv_op_2_0,
								"org.eclipse.n4js.N4JS.RelationalOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(New | This_1 | Super | Yield | Get | Set | Let | Project | External | Abstract | Static | As | From | Constructor | Of | Target | Type | Union | Intersection | This | Promisify | Await | Async | Implements | Interface | Private | Protected | Public | Out | LessThanSign | True | False | Null | Solidus | SolidusEqualsSign | LeftSquareBracket | LeftCurlyBracket | LeftParenthesis | CommercialAt | Function | Class | Delete | Void | Typeof | PlusSignPlusSign | HyphenMinusHyphenMinus | PlusSign | HyphenMinus | Tilde | ExclamationMark | RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
					(
						{
							newCompositeNode(grammarAccess.getRelationalExpressionAccess().getRhsShiftExpressionParserRuleCall_1_0_2_0());
						}
						lv_rhs_3_0=ruleShiftExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getRelationalExpressionRule());
							}
							set(
								$current,
								"rhs",
								lv_rhs_3_0,
								"org.eclipse.n4js.N4JS.ShiftExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)*
	)
;


// Rule RelationalExpression
norm2_RelationalExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getRelationalExpressionAccess().getShiftExpressionParserRuleCall_0());
		}
		this_ShiftExpression_0=norm1_ShiftExpression
		{
			$current = $this_ShiftExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			((
				(
				)
				(
					(
						ruleRelationalOperator
					)
				)
				(
					New
					    |
					This_1
					    |
					Super
					    |
					Get
					    |
					Set
					    |
					Let
					    |
					Project
					    |
					External
					    |
					Abstract
					    |
					Static
					    |
					As
					    |
					From
					    |
					Constructor
					    |
					Of
					    |
					Target
					    |
					Type
					    |
					Union
					    |
					Intersection
					    |
					This
					    |
					Promisify
					    |
					Await
					    |
					Async
					    |
					Implements
					    |
					Interface
					    |
					Private
					    |
					Protected
					    |
					Public
					    |
					Out
					    |
					LessThanSign
					    |
					True
					    |
					False
					    |
					Null
					    |
					Solidus
					    |
					SolidusEqualsSign
					    |
					LeftSquareBracket
					    |
					LeftCurlyBracket
					    |
					LeftParenthesis
					    |
					CommercialAt
					    |
					Function
					    |
					Class
					    |
					Delete
					    |
					Void
					    |
					Typeof
					    |
					PlusSignPlusSign
					    |
					HyphenMinusHyphenMinus
					    |
					PlusSign
					    |
					HyphenMinus
					    |
					Tilde
					    |
					ExclamationMark
					    |
					RULE_IDENTIFIER
					    |
					RULE_DOUBLE
					    |
					RULE_INT
					    |
					RULE_BINARY_INT
					    |
					RULE_OCTAL_INT
					    |
					RULE_LEGACY_OCTAL_INT
					    |
					RULE_HEX_INT
					    |
					RULE_SCIENTIFIC_INT
					    |
					RULE_STRING
					    |
					RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
					    |
					RULE_TEMPLATE_HEAD
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLhsAction_1_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getRelationalExpressionAccess().getOpRelationalOperatorParserRuleCall_1_0_1_0());
						}
						lv_op_2_0=ruleRelationalOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getRelationalExpressionRule());
							}
							set(
								$current,
								"op",
								lv_op_2_0,
								"org.eclipse.n4js.N4JS.RelationalOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(New | This_1 | Super | Get | Set | Let | Project | External | Abstract | Static | As | From | Constructor | Of | Target | Type | Union | Intersection | This | Promisify | Await | Async | Implements | Interface | Private | Protected | Public | Out | LessThanSign | True | False | Null | Solidus | SolidusEqualsSign | LeftSquareBracket | LeftCurlyBracket | LeftParenthesis | CommercialAt | Function | Class | Delete | Void | Typeof | PlusSignPlusSign | HyphenMinusHyphenMinus | PlusSign | HyphenMinus | Tilde | ExclamationMark | RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
					(
						{
							newCompositeNode(grammarAccess.getRelationalExpressionAccess().getRhsShiftExpressionParserRuleCall_1_0_2_0());
						}
						lv_rhs_3_0=norm1_ShiftExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getRelationalExpressionRule());
							}
							set(
								$current,
								"rhs",
								lv_rhs_3_0,
								"org.eclipse.n4js.N4JS.ShiftExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)*
	)
;


// Rule RelationalExpression
norm3_RelationalExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getRelationalExpressionAccess().getShiftExpressionParserRuleCall_0());
		}
		this_ShiftExpression_0=norm1_ShiftExpression
		{
			$current = $this_ShiftExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			((
				(
				)
				(
					(
						norm1_RelationalOperator
					)
				)
				(
					New
					    |
					This_1
					    |
					Super
					    |
					Get
					    |
					Set
					    |
					Let
					    |
					Project
					    |
					External
					    |
					Abstract
					    |
					Static
					    |
					As
					    |
					From
					    |
					Constructor
					    |
					Of
					    |
					Target
					    |
					Type
					    |
					Union
					    |
					Intersection
					    |
					This
					    |
					Promisify
					    |
					Await
					    |
					Async
					    |
					Implements
					    |
					Interface
					    |
					Private
					    |
					Protected
					    |
					Public
					    |
					Out
					    |
					LessThanSign
					    |
					True
					    |
					False
					    |
					Null
					    |
					Solidus
					    |
					SolidusEqualsSign
					    |
					LeftSquareBracket
					    |
					LeftCurlyBracket
					    |
					LeftParenthesis
					    |
					CommercialAt
					    |
					Function
					    |
					Class
					    |
					Delete
					    |
					Void
					    |
					Typeof
					    |
					PlusSignPlusSign
					    |
					HyphenMinusHyphenMinus
					    |
					PlusSign
					    |
					HyphenMinus
					    |
					Tilde
					    |
					ExclamationMark
					    |
					RULE_IDENTIFIER
					    |
					RULE_DOUBLE
					    |
					RULE_INT
					    |
					RULE_BINARY_INT
					    |
					RULE_OCTAL_INT
					    |
					RULE_LEGACY_OCTAL_INT
					    |
					RULE_HEX_INT
					    |
					RULE_SCIENTIFIC_INT
					    |
					RULE_STRING
					    |
					RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
					    |
					RULE_TEMPLATE_HEAD
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLhsAction_1_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getRelationalExpressionAccess().getOpRelationalOperatorParserRuleCall_1_0_1_0());
						}
						lv_op_2_0=norm1_RelationalOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getRelationalExpressionRule());
							}
							set(
								$current,
								"op",
								lv_op_2_0,
								"org.eclipse.n4js.N4JS.RelationalOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(New | This_1 | Super | Get | Set | Let | Project | External | Abstract | Static | As | From | Constructor | Of | Target | Type | Union | Intersection | This | Promisify | Await | Async | Implements | Interface | Private | Protected | Public | Out | LessThanSign | True | False | Null | Solidus | SolidusEqualsSign | LeftSquareBracket | LeftCurlyBracket | LeftParenthesis | CommercialAt | Function | Class | Delete | Void | Typeof | PlusSignPlusSign | HyphenMinusHyphenMinus | PlusSign | HyphenMinus | Tilde | ExclamationMark | RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
					(
						{
							newCompositeNode(grammarAccess.getRelationalExpressionAccess().getRhsShiftExpressionParserRuleCall_1_0_2_0());
						}
						lv_rhs_3_0=norm1_ShiftExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getRelationalExpressionRule());
							}
							set(
								$current,
								"rhs",
								lv_rhs_3_0,
								"org.eclipse.n4js.N4JS.ShiftExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)*
	)
;

// Entry rule entryRuleRelationalOperator
entryRuleRelationalOperator returns [String current=null]:
	{ newCompositeNode(grammarAccess.getRelationalOperatorRule()); }
	iv_ruleRelationalOperator=ruleRelationalOperator
	{ $current=$iv_ruleRelationalOperator.current.getText(); }
	EOF;

// Rule RelationalOperator
ruleRelationalOperator returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw=LessThanSign
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getRelationalOperatorAccess().getLessThanSignKeyword_0());
		}
		    |
		(
			kw=GreaterThanSign
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getRelationalOperatorAccess().getGreaterThanSignKeyword_1_0());
			}
			(
				kw=EqualsSign
				{
					$current.merge(kw);
					newLeafNode(kw, grammarAccess.getRelationalOperatorAccess().getEqualsSignKeyword_1_1());
				}
			)?
		)
		    |
		kw=LessThanSignEqualsSign
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getRelationalOperatorAccess().getLessThanSignEqualsSignKeyword_2());
		}
		    |
		kw=Instanceof
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getRelationalOperatorAccess().getInstanceofKeyword_3());
		}
	)
;


// Rule RelationalOperator
norm1_RelationalOperator returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw=LessThanSign
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getRelationalOperatorAccess().getLessThanSignKeyword_0());
		}
		    |
		(
			kw=GreaterThanSign
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getRelationalOperatorAccess().getGreaterThanSignKeyword_1_0());
			}
			(
				kw=EqualsSign
				{
					$current.merge(kw);
					newLeafNode(kw, grammarAccess.getRelationalOperatorAccess().getEqualsSignKeyword_1_1());
				}
			)?
		)
		    |
		kw=LessThanSignEqualsSign
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getRelationalOperatorAccess().getLessThanSignEqualsSignKeyword_2());
		}
		    |
		kw=Instanceof
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getRelationalOperatorAccess().getInstanceofKeyword_3());
		}
		    |
		kw=In
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getRelationalOperatorAccess().getInKeyword_4_0());
		}
	)
;

// Entry rule entryRuleEqualityExpression
entryRuleEqualityExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getEqualityExpressionRule()); }
	iv_ruleEqualityExpression=ruleEqualityExpression
	{ $current=$iv_ruleEqualityExpression.current; }
	EOF;

// Rule EqualityExpression
ruleEqualityExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getEqualityExpressionAccess().getRelationalExpressionParserRuleCall_0());
		}
		this_RelationalExpression_0=ruleRelationalExpression
		{
			$current = $this_RelationalExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleEqualityOperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getEqualityExpressionAccess().getOpEqualityOperatorEnumRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleEqualityOperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getEqualityExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.EqualityOperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getEqualityExpressionAccess().getRhsRelationalExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=ruleRelationalExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getEqualityExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.RelationalExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule EqualityExpression
norm1_EqualityExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getEqualityExpressionAccess().getRelationalExpressionParserRuleCall_0());
		}
		this_RelationalExpression_0=norm1_RelationalExpression
		{
			$current = $this_RelationalExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleEqualityOperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getEqualityExpressionAccess().getOpEqualityOperatorEnumRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleEqualityOperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getEqualityExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.EqualityOperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getEqualityExpressionAccess().getRhsRelationalExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=norm1_RelationalExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getEqualityExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.RelationalExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule EqualityExpression
norm2_EqualityExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getEqualityExpressionAccess().getRelationalExpressionParserRuleCall_0());
		}
		this_RelationalExpression_0=norm2_RelationalExpression
		{
			$current = $this_RelationalExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleEqualityOperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getEqualityExpressionAccess().getOpEqualityOperatorEnumRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleEqualityOperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getEqualityExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.EqualityOperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getEqualityExpressionAccess().getRhsRelationalExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=norm2_RelationalExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getEqualityExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.RelationalExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule EqualityExpression
norm3_EqualityExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getEqualityExpressionAccess().getRelationalExpressionParserRuleCall_0());
		}
		this_RelationalExpression_0=norm3_RelationalExpression
		{
			$current = $this_RelationalExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleEqualityOperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getEqualityExpressionAccess().getOpEqualityOperatorEnumRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleEqualityOperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getEqualityExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.EqualityOperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getEqualityExpressionAccess().getRhsRelationalExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=norm3_RelationalExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getEqualityExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.RelationalExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleBitwiseANDExpression
entryRuleBitwiseANDExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getBitwiseANDExpressionRule()); }
	iv_ruleBitwiseANDExpression=ruleBitwiseANDExpression
	{ $current=$iv_ruleBitwiseANDExpression.current; }
	EOF;

// Rule BitwiseANDExpression
ruleBitwiseANDExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getBitwiseANDExpressionAccess().getEqualityExpressionParserRuleCall_0());
		}
		this_EqualityExpression_0=ruleEqualityExpression
		{
			$current = $this_EqualityExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleBitwiseANDOperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getBitwiseANDExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getBitwiseANDExpressionAccess().getOpBitwiseANDOperatorParserRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleBitwiseANDOperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getBitwiseANDExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.BitwiseANDOperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getBitwiseANDExpressionAccess().getRhsEqualityExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=ruleEqualityExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getBitwiseANDExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.EqualityExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule BitwiseANDExpression
norm1_BitwiseANDExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getBitwiseANDExpressionAccess().getEqualityExpressionParserRuleCall_0());
		}
		this_EqualityExpression_0=norm1_EqualityExpression
		{
			$current = $this_EqualityExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleBitwiseANDOperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getBitwiseANDExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getBitwiseANDExpressionAccess().getOpBitwiseANDOperatorParserRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleBitwiseANDOperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getBitwiseANDExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.BitwiseANDOperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getBitwiseANDExpressionAccess().getRhsEqualityExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=norm1_EqualityExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getBitwiseANDExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.EqualityExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule BitwiseANDExpression
norm2_BitwiseANDExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getBitwiseANDExpressionAccess().getEqualityExpressionParserRuleCall_0());
		}
		this_EqualityExpression_0=norm2_EqualityExpression
		{
			$current = $this_EqualityExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleBitwiseANDOperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getBitwiseANDExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getBitwiseANDExpressionAccess().getOpBitwiseANDOperatorParserRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleBitwiseANDOperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getBitwiseANDExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.BitwiseANDOperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getBitwiseANDExpressionAccess().getRhsEqualityExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=norm2_EqualityExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getBitwiseANDExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.EqualityExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule BitwiseANDExpression
norm3_BitwiseANDExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getBitwiseANDExpressionAccess().getEqualityExpressionParserRuleCall_0());
		}
		this_EqualityExpression_0=norm3_EqualityExpression
		{
			$current = $this_EqualityExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleBitwiseANDOperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getBitwiseANDExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getBitwiseANDExpressionAccess().getOpBitwiseANDOperatorParserRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleBitwiseANDOperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getBitwiseANDExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.BitwiseANDOperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getBitwiseANDExpressionAccess().getRhsEqualityExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=norm3_EqualityExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getBitwiseANDExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.EqualityExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleBitwiseANDOperator
entryRuleBitwiseANDOperator returns [String current=null]:
	{ newCompositeNode(grammarAccess.getBitwiseANDOperatorRule()); }
	iv_ruleBitwiseANDOperator=ruleBitwiseANDOperator
	{ $current=$iv_ruleBitwiseANDOperator.current.getText(); }
	EOF;

// Rule BitwiseANDOperator
ruleBitwiseANDOperator returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	kw=Ampersand
	{
		$current.merge(kw);
		newLeafNode(kw, grammarAccess.getBitwiseANDOperatorAccess().getAmpersandKeyword());
	}
;

// Entry rule entryRuleBitwiseXORExpression
entryRuleBitwiseXORExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getBitwiseXORExpressionRule()); }
	iv_ruleBitwiseXORExpression=ruleBitwiseXORExpression
	{ $current=$iv_ruleBitwiseXORExpression.current; }
	EOF;

// Rule BitwiseXORExpression
ruleBitwiseXORExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getBitwiseXORExpressionAccess().getBitwiseANDExpressionParserRuleCall_0());
		}
		this_BitwiseANDExpression_0=ruleBitwiseANDExpression
		{
			$current = $this_BitwiseANDExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleBitwiseXOROperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getBitwiseXORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getBitwiseXORExpressionAccess().getOpBitwiseXOROperatorParserRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleBitwiseXOROperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getBitwiseXORExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.BitwiseXOROperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getBitwiseXORExpressionAccess().getRhsBitwiseANDExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=ruleBitwiseANDExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getBitwiseXORExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.BitwiseANDExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule BitwiseXORExpression
norm1_BitwiseXORExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getBitwiseXORExpressionAccess().getBitwiseANDExpressionParserRuleCall_0());
		}
		this_BitwiseANDExpression_0=norm1_BitwiseANDExpression
		{
			$current = $this_BitwiseANDExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleBitwiseXOROperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getBitwiseXORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getBitwiseXORExpressionAccess().getOpBitwiseXOROperatorParserRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleBitwiseXOROperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getBitwiseXORExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.BitwiseXOROperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getBitwiseXORExpressionAccess().getRhsBitwiseANDExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=norm1_BitwiseANDExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getBitwiseXORExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.BitwiseANDExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule BitwiseXORExpression
norm2_BitwiseXORExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getBitwiseXORExpressionAccess().getBitwiseANDExpressionParserRuleCall_0());
		}
		this_BitwiseANDExpression_0=norm2_BitwiseANDExpression
		{
			$current = $this_BitwiseANDExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleBitwiseXOROperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getBitwiseXORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getBitwiseXORExpressionAccess().getOpBitwiseXOROperatorParserRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleBitwiseXOROperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getBitwiseXORExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.BitwiseXOROperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getBitwiseXORExpressionAccess().getRhsBitwiseANDExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=norm2_BitwiseANDExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getBitwiseXORExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.BitwiseANDExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule BitwiseXORExpression
norm3_BitwiseXORExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getBitwiseXORExpressionAccess().getBitwiseANDExpressionParserRuleCall_0());
		}
		this_BitwiseANDExpression_0=norm3_BitwiseANDExpression
		{
			$current = $this_BitwiseANDExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleBitwiseXOROperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getBitwiseXORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getBitwiseXORExpressionAccess().getOpBitwiseXOROperatorParserRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleBitwiseXOROperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getBitwiseXORExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.BitwiseXOROperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getBitwiseXORExpressionAccess().getRhsBitwiseANDExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=norm3_BitwiseANDExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getBitwiseXORExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.BitwiseANDExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleBitwiseXOROperator
entryRuleBitwiseXOROperator returns [String current=null]:
	{ newCompositeNode(grammarAccess.getBitwiseXOROperatorRule()); }
	iv_ruleBitwiseXOROperator=ruleBitwiseXOROperator
	{ $current=$iv_ruleBitwiseXOROperator.current.getText(); }
	EOF;

// Rule BitwiseXOROperator
ruleBitwiseXOROperator returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	kw=CircumflexAccent
	{
		$current.merge(kw);
		newLeafNode(kw, grammarAccess.getBitwiseXOROperatorAccess().getCircumflexAccentKeyword());
	}
;

// Entry rule entryRuleBitwiseORExpression
entryRuleBitwiseORExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getBitwiseORExpressionRule()); }
	iv_ruleBitwiseORExpression=ruleBitwiseORExpression
	{ $current=$iv_ruleBitwiseORExpression.current; }
	EOF;

// Rule BitwiseORExpression
ruleBitwiseORExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getBitwiseORExpressionAccess().getBitwiseXORExpressionParserRuleCall_0());
		}
		this_BitwiseXORExpression_0=ruleBitwiseXORExpression
		{
			$current = $this_BitwiseXORExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleBitwiseOROperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getBitwiseORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getBitwiseORExpressionAccess().getOpBitwiseOROperatorParserRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleBitwiseOROperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getBitwiseORExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.BitwiseOROperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getBitwiseORExpressionAccess().getRhsBitwiseXORExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=ruleBitwiseXORExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getBitwiseORExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.BitwiseXORExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule BitwiseORExpression
norm1_BitwiseORExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getBitwiseORExpressionAccess().getBitwiseXORExpressionParserRuleCall_0());
		}
		this_BitwiseXORExpression_0=norm1_BitwiseXORExpression
		{
			$current = $this_BitwiseXORExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleBitwiseOROperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getBitwiseORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getBitwiseORExpressionAccess().getOpBitwiseOROperatorParserRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleBitwiseOROperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getBitwiseORExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.BitwiseOROperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getBitwiseORExpressionAccess().getRhsBitwiseXORExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=norm1_BitwiseXORExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getBitwiseORExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.BitwiseXORExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule BitwiseORExpression
norm2_BitwiseORExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getBitwiseORExpressionAccess().getBitwiseXORExpressionParserRuleCall_0());
		}
		this_BitwiseXORExpression_0=norm2_BitwiseXORExpression
		{
			$current = $this_BitwiseXORExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleBitwiseOROperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getBitwiseORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getBitwiseORExpressionAccess().getOpBitwiseOROperatorParserRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleBitwiseOROperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getBitwiseORExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.BitwiseOROperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getBitwiseORExpressionAccess().getRhsBitwiseXORExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=norm2_BitwiseXORExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getBitwiseORExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.BitwiseXORExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule BitwiseORExpression
norm3_BitwiseORExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getBitwiseORExpressionAccess().getBitwiseXORExpressionParserRuleCall_0());
		}
		this_BitwiseXORExpression_0=norm3_BitwiseXORExpression
		{
			$current = $this_BitwiseXORExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleBitwiseOROperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getBitwiseORExpressionAccess().getBinaryBitwiseExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getBitwiseORExpressionAccess().getOpBitwiseOROperatorParserRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleBitwiseOROperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getBitwiseORExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.BitwiseOROperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getBitwiseORExpressionAccess().getRhsBitwiseXORExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=norm3_BitwiseXORExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getBitwiseORExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.BitwiseXORExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleBitwiseOROperator
entryRuleBitwiseOROperator returns [String current=null]:
	{ newCompositeNode(grammarAccess.getBitwiseOROperatorRule()); }
	iv_ruleBitwiseOROperator=ruleBitwiseOROperator
	{ $current=$iv_ruleBitwiseOROperator.current.getText(); }
	EOF;

// Rule BitwiseOROperator
ruleBitwiseOROperator returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	kw=VerticalLine
	{
		$current.merge(kw);
		newLeafNode(kw, grammarAccess.getBitwiseOROperatorAccess().getVerticalLineKeyword());
	}
;

// Entry rule entryRuleLogicalANDExpression
entryRuleLogicalANDExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getLogicalANDExpressionRule()); }
	iv_ruleLogicalANDExpression=ruleLogicalANDExpression
	{ $current=$iv_ruleLogicalANDExpression.current; }
	EOF;

// Rule LogicalANDExpression
ruleLogicalANDExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getLogicalANDExpressionAccess().getBitwiseORExpressionParserRuleCall_0());
		}
		this_BitwiseORExpression_0=ruleBitwiseORExpression
		{
			$current = $this_BitwiseORExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleLogicalANDOperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getLogicalANDExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getLogicalANDExpressionAccess().getOpLogicalANDOperatorParserRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleLogicalANDOperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getLogicalANDExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.LogicalANDOperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getLogicalANDExpressionAccess().getRhsBitwiseORExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=ruleBitwiseORExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getLogicalANDExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.BitwiseORExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule LogicalANDExpression
norm1_LogicalANDExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getLogicalANDExpressionAccess().getBitwiseORExpressionParserRuleCall_0());
		}
		this_BitwiseORExpression_0=norm1_BitwiseORExpression
		{
			$current = $this_BitwiseORExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleLogicalANDOperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getLogicalANDExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getLogicalANDExpressionAccess().getOpLogicalANDOperatorParserRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleLogicalANDOperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getLogicalANDExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.LogicalANDOperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getLogicalANDExpressionAccess().getRhsBitwiseORExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=norm1_BitwiseORExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getLogicalANDExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.BitwiseORExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule LogicalANDExpression
norm2_LogicalANDExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getLogicalANDExpressionAccess().getBitwiseORExpressionParserRuleCall_0());
		}
		this_BitwiseORExpression_0=norm2_BitwiseORExpression
		{
			$current = $this_BitwiseORExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleLogicalANDOperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getLogicalANDExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getLogicalANDExpressionAccess().getOpLogicalANDOperatorParserRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleLogicalANDOperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getLogicalANDExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.LogicalANDOperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getLogicalANDExpressionAccess().getRhsBitwiseORExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=norm2_BitwiseORExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getLogicalANDExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.BitwiseORExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule LogicalANDExpression
norm3_LogicalANDExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getLogicalANDExpressionAccess().getBitwiseORExpressionParserRuleCall_0());
		}
		this_BitwiseORExpression_0=norm3_BitwiseORExpression
		{
			$current = $this_BitwiseORExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleLogicalANDOperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getLogicalANDExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getLogicalANDExpressionAccess().getOpLogicalANDOperatorParserRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleLogicalANDOperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getLogicalANDExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.LogicalANDOperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getLogicalANDExpressionAccess().getRhsBitwiseORExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=norm3_BitwiseORExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getLogicalANDExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.BitwiseORExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleLogicalANDOperator
entryRuleLogicalANDOperator returns [String current=null]:
	{ newCompositeNode(grammarAccess.getLogicalANDOperatorRule()); }
	iv_ruleLogicalANDOperator=ruleLogicalANDOperator
	{ $current=$iv_ruleLogicalANDOperator.current.getText(); }
	EOF;

// Rule LogicalANDOperator
ruleLogicalANDOperator returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	kw=AmpersandAmpersand
	{
		$current.merge(kw);
		newLeafNode(kw, grammarAccess.getLogicalANDOperatorAccess().getAmpersandAmpersandKeyword());
	}
;

// Entry rule entryRuleLogicalORExpression
entryRuleLogicalORExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getLogicalORExpressionRule()); }
	iv_ruleLogicalORExpression=ruleLogicalORExpression
	{ $current=$iv_ruleLogicalORExpression.current; }
	EOF;

// Rule LogicalORExpression
ruleLogicalORExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getLogicalORExpressionAccess().getLogicalANDExpressionParserRuleCall_0());
		}
		this_LogicalANDExpression_0=ruleLogicalANDExpression
		{
			$current = $this_LogicalANDExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleLogicalOROperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getLogicalORExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getLogicalORExpressionAccess().getOpLogicalOROperatorParserRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleLogicalOROperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getLogicalORExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.LogicalOROperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getLogicalORExpressionAccess().getRhsLogicalANDExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=ruleLogicalANDExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getLogicalORExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.LogicalANDExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule LogicalORExpression
norm1_LogicalORExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getLogicalORExpressionAccess().getLogicalANDExpressionParserRuleCall_0());
		}
		this_LogicalANDExpression_0=norm1_LogicalANDExpression
		{
			$current = $this_LogicalANDExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleLogicalOROperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getLogicalORExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getLogicalORExpressionAccess().getOpLogicalOROperatorParserRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleLogicalOROperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getLogicalORExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.LogicalOROperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getLogicalORExpressionAccess().getRhsLogicalANDExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=norm1_LogicalANDExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getLogicalORExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.LogicalANDExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule LogicalORExpression
norm2_LogicalORExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getLogicalORExpressionAccess().getLogicalANDExpressionParserRuleCall_0());
		}
		this_LogicalANDExpression_0=norm2_LogicalANDExpression
		{
			$current = $this_LogicalANDExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleLogicalOROperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getLogicalORExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getLogicalORExpressionAccess().getOpLogicalOROperatorParserRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleLogicalOROperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getLogicalORExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.LogicalOROperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getLogicalORExpressionAccess().getRhsLogicalANDExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=norm2_LogicalANDExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getLogicalORExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.LogicalANDExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;


// Rule LogicalORExpression
norm3_LogicalORExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getLogicalORExpressionAccess().getLogicalANDExpressionParserRuleCall_0());
		}
		this_LogicalANDExpression_0=norm3_LogicalANDExpression
		{
			$current = $this_LogicalANDExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleLogicalOROperator
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getLogicalORExpressionAccess().getBinaryLogicalExpressionLhsAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getLogicalORExpressionAccess().getOpLogicalOROperatorParserRuleCall_1_0_0_1_0());
							}
							lv_op_2_0=ruleLogicalOROperator
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getLogicalORExpressionRule());
								}
								set(
									$current,
									"op",
									lv_op_2_0,
									"org.eclipse.n4js.N4JS.LogicalOROperator");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getLogicalORExpressionAccess().getRhsLogicalANDExpressionParserRuleCall_1_1_0());
					}
					lv_rhs_3_0=norm3_LogicalANDExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getLogicalORExpressionRule());
						}
						set(
							$current,
							"rhs",
							lv_rhs_3_0,
							"org.eclipse.n4js.N4JS.LogicalANDExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleLogicalOROperator
entryRuleLogicalOROperator returns [String current=null]:
	{ newCompositeNode(grammarAccess.getLogicalOROperatorRule()); }
	iv_ruleLogicalOROperator=ruleLogicalOROperator
	{ $current=$iv_ruleLogicalOROperator.current.getText(); }
	EOF;

// Rule LogicalOROperator
ruleLogicalOROperator returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	kw=VerticalLineVerticalLine
	{
		$current.merge(kw);
		newLeafNode(kw, grammarAccess.getLogicalOROperatorAccess().getVerticalLineVerticalLineKeyword());
	}
;

// Entry rule entryRuleConditionalExpression
entryRuleConditionalExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getConditionalExpressionRule()); }
	iv_ruleConditionalExpression=ruleConditionalExpression
	{ $current=$iv_ruleConditionalExpression.current; }
	EOF;

// Rule ConditionalExpression
ruleConditionalExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getConditionalExpressionAccess().getLogicalORExpressionParserRuleCall_0());
		}
		this_LogicalORExpression_0=ruleLogicalORExpression
		{
			$current = $this_LogicalORExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					QuestionMark
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getConditionalExpressionAccess().getConditionalExpressionExpressionAction_1_0_0_0(),
								$current);
						}
					)
					otherlv_2=QuestionMark
					{
						newLeafNode(otherlv_2, grammarAccess.getConditionalExpressionAccess().getQuestionMarkKeyword_1_0_0_1());
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getConditionalExpressionAccess().getTrueExpressionAssignmentExpressionParserRuleCall_1_1_0());
					}
					lv_trueExpression_3_0=norm1_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getConditionalExpressionRule());
						}
						set(
							$current,
							"trueExpression",
							lv_trueExpression_3_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_4=Colon
			{
				newLeafNode(otherlv_4, grammarAccess.getConditionalExpressionAccess().getColonKeyword_1_2());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getConditionalExpressionAccess().getFalseExpressionAssignmentExpressionParserRuleCall_1_3_0());
					}
					lv_falseExpression_5_0=ruleAssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getConditionalExpressionRule());
						}
						set(
							$current,
							"falseExpression",
							lv_falseExpression_5_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;


// Rule ConditionalExpression
norm1_ConditionalExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getConditionalExpressionAccess().getLogicalORExpressionParserRuleCall_0());
		}
		this_LogicalORExpression_0=norm1_LogicalORExpression
		{
			$current = $this_LogicalORExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					QuestionMark
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getConditionalExpressionAccess().getConditionalExpressionExpressionAction_1_0_0_0(),
								$current);
						}
					)
					otherlv_2=QuestionMark
					{
						newLeafNode(otherlv_2, grammarAccess.getConditionalExpressionAccess().getQuestionMarkKeyword_1_0_0_1());
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getConditionalExpressionAccess().getTrueExpressionAssignmentExpressionParserRuleCall_1_1_0());
					}
					lv_trueExpression_3_0=norm1_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getConditionalExpressionRule());
						}
						set(
							$current,
							"trueExpression",
							lv_trueExpression_3_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_4=Colon
			{
				newLeafNode(otherlv_4, grammarAccess.getConditionalExpressionAccess().getColonKeyword_1_2());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getConditionalExpressionAccess().getFalseExpressionAssignmentExpressionParserRuleCall_1_3_0());
					}
					lv_falseExpression_5_0=norm1_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getConditionalExpressionRule());
						}
						set(
							$current,
							"falseExpression",
							lv_falseExpression_5_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;


// Rule ConditionalExpression
norm2_ConditionalExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getConditionalExpressionAccess().getLogicalORExpressionParserRuleCall_0());
		}
		this_LogicalORExpression_0=norm2_LogicalORExpression
		{
			$current = $this_LogicalORExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					QuestionMark
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getConditionalExpressionAccess().getConditionalExpressionExpressionAction_1_0_0_0(),
								$current);
						}
					)
					otherlv_2=QuestionMark
					{
						newLeafNode(otherlv_2, grammarAccess.getConditionalExpressionAccess().getQuestionMarkKeyword_1_0_0_1());
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getConditionalExpressionAccess().getTrueExpressionAssignmentExpressionParserRuleCall_1_1_0());
					}
					lv_trueExpression_3_0=norm3_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getConditionalExpressionRule());
						}
						set(
							$current,
							"trueExpression",
							lv_trueExpression_3_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_4=Colon
			{
				newLeafNode(otherlv_4, grammarAccess.getConditionalExpressionAccess().getColonKeyword_1_2());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getConditionalExpressionAccess().getFalseExpressionAssignmentExpressionParserRuleCall_1_3_0());
					}
					lv_falseExpression_5_0=norm2_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getConditionalExpressionRule());
						}
						set(
							$current,
							"falseExpression",
							lv_falseExpression_5_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;


// Rule ConditionalExpression
norm3_ConditionalExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getConditionalExpressionAccess().getLogicalORExpressionParserRuleCall_0());
		}
		this_LogicalORExpression_0=norm3_LogicalORExpression
		{
			$current = $this_LogicalORExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					QuestionMark
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getConditionalExpressionAccess().getConditionalExpressionExpressionAction_1_0_0_0(),
								$current);
						}
					)
					otherlv_2=QuestionMark
					{
						newLeafNode(otherlv_2, grammarAccess.getConditionalExpressionAccess().getQuestionMarkKeyword_1_0_0_1());
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getConditionalExpressionAccess().getTrueExpressionAssignmentExpressionParserRuleCall_1_1_0());
					}
					lv_trueExpression_3_0=norm3_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getConditionalExpressionRule());
						}
						set(
							$current,
							"trueExpression",
							lv_trueExpression_3_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_4=Colon
			{
				newLeafNode(otherlv_4, grammarAccess.getConditionalExpressionAccess().getColonKeyword_1_2());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getConditionalExpressionAccess().getFalseExpressionAssignmentExpressionParserRuleCall_1_3_0());
					}
					lv_falseExpression_5_0=norm3_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getConditionalExpressionRule());
						}
						set(
							$current,
							"falseExpression",
							lv_falseExpression_5_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;

// Entry rule entryRuleAssignmentExpression
entryRuleAssignmentExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAssignmentExpressionRule()); }
	iv_ruleAssignmentExpression=ruleAssignmentExpression
	{ $current=$iv_ruleAssignmentExpression.current; }
	EOF;

// Rule AssignmentExpression
ruleAssignmentExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				Await
			)
			)=>
			{
				newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getAwaitExpressionParserRuleCall_0());
			}
			this_AwaitExpression_0=ruleAwaitExpression
			{
				$current = $this_AwaitExpression_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				CommercialAt
				Promisify
			)
			)=>
			{
				newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getPromisifyExpressionParserRuleCall_1());
			}
			this_PromisifyExpression_1=rulePromisifyExpression
			{
				$current = $this_PromisifyExpression_1.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
					(
						ruleStrictFormalParameters[null]
						(
							ruleColonSepReturnTypeRef[null]
						)?
					)
					    |
					(
						(
							((
								(
									(
										Async
									)
								)
								ruleNoLineTerminator[null]
								LeftParenthesis
							)
							)=>
							(
								(
									(
										Async
									)
								)
								ruleNoLineTerminator[null]
								(
									(LeftParenthesis)=>
									ruleStrictFormalParameters[null]
								)
							)
						)
						(
							ruleColonSepReturnTypeRef[null]
						)?
					)
					    |
					(
						(
							ruleBindingIdentifierAsFormalParameter
						)
					)
				)
				EqualsSignGreaterThanSign
			)
			)=>
			{
				newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getArrowExpressionParserRuleCall_2());
			}
			this_ArrowExpression_2=ruleArrowExpression
			{
				$current = $this_ArrowExpression_2.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			{
				newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getConditionalExpressionParserRuleCall_4_0());
			}
			this_ConditionalExpression_3=ruleConditionalExpression
			{
				$current = $this_ConditionalExpression_3.current;
				afterParserOrEnumRuleCall();
			}
			(
				(
					((
						(
						)
						(
							(
								ruleAssignmentOperator
							)
						)
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLhsAction_4_1_0_0_0(),
									$current);
							}
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getOpAssignmentOperatorParserRuleCall_4_1_0_0_1_0());
								}
								lv_op_5_0=ruleAssignmentOperator
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAssignmentExpressionRule());
									}
									set(
										$current,
										"op",
										lv_op_5_0,
										"org.eclipse.n4js.N4JS.AssignmentOperator");
									afterParserOrEnumRuleCall();
								}
							)
						)
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getRhsAssignmentExpressionParserRuleCall_4_1_1_0());
						}
						lv_rhs_6_0=ruleAssignmentExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAssignmentExpressionRule());
							}
							set(
								$current,
								"rhs",
								lv_rhs_6_0,
								"org.eclipse.n4js.N4JS.AssignmentExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)?
		)
	)
;


// Rule AssignmentExpression
norm1_AssignmentExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				Await
			)
			)=>
			{
				newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getAwaitExpressionParserRuleCall_0());
			}
			this_AwaitExpression_0=norm1_AwaitExpression
			{
				$current = $this_AwaitExpression_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				CommercialAt
				Promisify
			)
			)=>
			{
				newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getPromisifyExpressionParserRuleCall_1());
			}
			this_PromisifyExpression_1=norm1_PromisifyExpression
			{
				$current = $this_PromisifyExpression_1.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
					(
						ruleStrictFormalParameters[null]
						(
							ruleColonSepReturnTypeRef[null]
						)?
					)
					    |
					(
						(
							((
								(
									(
										Async
									)
								)
								ruleNoLineTerminator[null]
								LeftParenthesis
							)
							)=>
							(
								(
									(
										Async
									)
								)
								ruleNoLineTerminator[null]
								(
									(LeftParenthesis)=>
									ruleStrictFormalParameters[null]
								)
							)
						)
						(
							ruleColonSepReturnTypeRef[null]
						)?
					)
					    |
					(
						(
							ruleBindingIdentifierAsFormalParameter
						)
					)
				)
				EqualsSignGreaterThanSign
			)
			)=>
			{
				newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getArrowExpressionParserRuleCall_2());
			}
			this_ArrowExpression_2=norm1_ArrowExpression
			{
				$current = $this_ArrowExpression_2.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			{
				newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getConditionalExpressionParserRuleCall_4_0());
			}
			this_ConditionalExpression_3=norm1_ConditionalExpression
			{
				$current = $this_ConditionalExpression_3.current;
				afterParserOrEnumRuleCall();
			}
			(
				(
					((
						(
						)
						(
							(
								ruleAssignmentOperator
							)
						)
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLhsAction_4_1_0_0_0(),
									$current);
							}
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getOpAssignmentOperatorParserRuleCall_4_1_0_0_1_0());
								}
								lv_op_5_0=ruleAssignmentOperator
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAssignmentExpressionRule());
									}
									set(
										$current,
										"op",
										lv_op_5_0,
										"org.eclipse.n4js.N4JS.AssignmentOperator");
									afterParserOrEnumRuleCall();
								}
							)
						)
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getRhsAssignmentExpressionParserRuleCall_4_1_1_0());
						}
						lv_rhs_6_0=norm1_AssignmentExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAssignmentExpressionRule());
							}
							set(
								$current,
								"rhs",
								lv_rhs_6_0,
								"org.eclipse.n4js.N4JS.AssignmentExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)?
		)
	)
;


// Rule AssignmentExpression
norm2_AssignmentExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				Await
			)
			)=>
			{
				newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getAwaitExpressionParserRuleCall_0());
			}
			this_AwaitExpression_0=norm2_AwaitExpression
			{
				$current = $this_AwaitExpression_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				CommercialAt
				Promisify
			)
			)=>
			{
				newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getPromisifyExpressionParserRuleCall_1());
			}
			this_PromisifyExpression_1=norm2_PromisifyExpression
			{
				$current = $this_PromisifyExpression_1.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
					(
						norm1_StrictFormalParameters[null]
						(
							ruleColonSepReturnTypeRef[null]
						)?
					)
					    |
					(
						(
							((
								(
									(
										Async
									)
								)
								ruleNoLineTerminator[null]
								LeftParenthesis
							)
							)=>
							(
								(
									(
										Async
									)
								)
								ruleNoLineTerminator[null]
								(
									(LeftParenthesis)=>
									norm1_StrictFormalParameters[null]
								)
							)
						)
						(
							ruleColonSepReturnTypeRef[null]
						)?
					)
					    |
					(
						(
							norm1_BindingIdentifierAsFormalParameter
						)
					)
				)
				EqualsSignGreaterThanSign
			)
			)=>
			{
				newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getArrowExpressionParserRuleCall_2());
			}
			this_ArrowExpression_2=norm2_ArrowExpression
			{
				$current = $this_ArrowExpression_2.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getYieldExpressionParserRuleCall_3_0());
		}
		this_YieldExpression_3=ruleYieldExpression
		{
			$current = $this_YieldExpression_3.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			{
				newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getConditionalExpressionParserRuleCall_4_0());
			}
			this_ConditionalExpression_4=norm2_ConditionalExpression
			{
				$current = $this_ConditionalExpression_4.current;
				afterParserOrEnumRuleCall();
			}
			(
				(
					((
						(
						)
						(
							(
								ruleAssignmentOperator
							)
						)
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLhsAction_4_1_0_0_0(),
									$current);
							}
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getOpAssignmentOperatorParserRuleCall_4_1_0_0_1_0());
								}
								lv_op_6_0=ruleAssignmentOperator
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAssignmentExpressionRule());
									}
									set(
										$current,
										"op",
										lv_op_6_0,
										"org.eclipse.n4js.N4JS.AssignmentOperator");
									afterParserOrEnumRuleCall();
								}
							)
						)
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getRhsAssignmentExpressionParserRuleCall_4_1_1_0());
						}
						lv_rhs_7_0=norm2_AssignmentExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAssignmentExpressionRule());
							}
							set(
								$current,
								"rhs",
								lv_rhs_7_0,
								"org.eclipse.n4js.N4JS.AssignmentExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)?
		)
	)
;


// Rule AssignmentExpression
norm3_AssignmentExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				Await
			)
			)=>
			{
				newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getAwaitExpressionParserRuleCall_0());
			}
			this_AwaitExpression_0=norm3_AwaitExpression
			{
				$current = $this_AwaitExpression_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				CommercialAt
				Promisify
			)
			)=>
			{
				newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getPromisifyExpressionParserRuleCall_1());
			}
			this_PromisifyExpression_1=norm3_PromisifyExpression
			{
				$current = $this_PromisifyExpression_1.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
					(
						norm1_StrictFormalParameters[null]
						(
							ruleColonSepReturnTypeRef[null]
						)?
					)
					    |
					(
						(
							((
								(
									(
										Async
									)
								)
								ruleNoLineTerminator[null]
								LeftParenthesis
							)
							)=>
							(
								(
									(
										Async
									)
								)
								ruleNoLineTerminator[null]
								(
									(LeftParenthesis)=>
									norm1_StrictFormalParameters[null]
								)
							)
						)
						(
							ruleColonSepReturnTypeRef[null]
						)?
					)
					    |
					(
						(
							norm1_BindingIdentifierAsFormalParameter
						)
					)
				)
				EqualsSignGreaterThanSign
			)
			)=>
			{
				newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getArrowExpressionParserRuleCall_2());
			}
			this_ArrowExpression_2=norm3_ArrowExpression
			{
				$current = $this_ArrowExpression_2.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getYieldExpressionParserRuleCall_3_0());
		}
		this_YieldExpression_3=norm1_YieldExpression
		{
			$current = $this_YieldExpression_3.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			{
				newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getConditionalExpressionParserRuleCall_4_0());
			}
			this_ConditionalExpression_4=norm3_ConditionalExpression
			{
				$current = $this_ConditionalExpression_4.current;
				afterParserOrEnumRuleCall();
			}
			(
				(
					((
						(
						)
						(
							(
								ruleAssignmentOperator
							)
						)
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLhsAction_4_1_0_0_0(),
									$current);
							}
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getOpAssignmentOperatorParserRuleCall_4_1_0_0_1_0());
								}
								lv_op_6_0=ruleAssignmentOperator
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAssignmentExpressionRule());
									}
									set(
										$current,
										"op",
										lv_op_6_0,
										"org.eclipse.n4js.N4JS.AssignmentOperator");
									afterParserOrEnumRuleCall();
								}
							)
						)
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getAssignmentExpressionAccess().getRhsAssignmentExpressionParserRuleCall_4_1_1_0());
						}
						lv_rhs_7_0=norm3_AssignmentExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAssignmentExpressionRule());
							}
							set(
								$current,
								"rhs",
								lv_rhs_7_0,
								"org.eclipse.n4js.N4JS.AssignmentExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)?
		)
	)
;

// Entry rule entryRuleYieldExpression
entryRuleYieldExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getYieldExpressionRule()); }
	iv_ruleYieldExpression=ruleYieldExpression
	{ $current=$iv_ruleYieldExpression.current; }
	EOF;

// Rule YieldExpression
ruleYieldExpression returns [EObject current=null]
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
					grammarAccess.getYieldExpressionAccess().getYieldExpressionAction_0(),
					$current);
			}
		)
		otherlv_1=Yield { promoteEOL(); }
		{
			newLeafNode(otherlv_1, grammarAccess.getYieldExpressionAccess().getYieldKeyword_1());
		}
		(
			((
				Asterisk
			)
			)=>
			(
				lv_many_2_0=Asterisk
				{
					newLeafNode(lv_many_2_0, grammarAccess.getYieldExpressionAccess().getManyAsteriskKeyword_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getYieldExpressionRule());
					}
					setWithLastConsumed($current, "many", true, "*");
				}
			)
		)?
		(
			(Await | CommercialAt | LeftParenthesis | Async | Get | Set | Let | Project | External | Abstract | Static | As | From | Constructor | Of | Target | Type | Union | Intersection | This | Promisify | Implements | Interface | Private | Protected | Public | Out | Yield | New | This_1 | Super | LessThanSign | True | False | Null | Solidus | SolidusEqualsSign | LeftSquareBracket | LeftCurlyBracket | Function | Class | Delete | Void | Typeof | PlusSignPlusSign | HyphenMinusHyphenMinus | PlusSign | HyphenMinus | Tilde | ExclamationMark | RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
			(
				{
					newCompositeNode(grammarAccess.getYieldExpressionAccess().getExpressionAssignmentExpressionParserRuleCall_3_0());
				}
				lv_expression_3_0=norm2_AssignmentExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getYieldExpressionRule());
					}
					set(
						$current,
						"expression",
						lv_expression_3_0,
						"org.eclipse.n4js.N4JS.AssignmentExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)?
	)
;


// Rule YieldExpression
norm1_YieldExpression returns [EObject current=null]
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
					grammarAccess.getYieldExpressionAccess().getYieldExpressionAction_0(),
					$current);
			}
		)
		otherlv_1=Yield { promoteEOL(); }
		{
			newLeafNode(otherlv_1, grammarAccess.getYieldExpressionAccess().getYieldKeyword_1());
		}
		(
			((
				Asterisk
			)
			)=>
			(
				lv_many_2_0=Asterisk
				{
					newLeafNode(lv_many_2_0, grammarAccess.getYieldExpressionAccess().getManyAsteriskKeyword_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getYieldExpressionRule());
					}
					setWithLastConsumed($current, "many", true, "*");
				}
			)
		)?
		(
			(Await | CommercialAt | LeftParenthesis | Async | Get | Set | Let | Project | External | Abstract | Static | As | From | Constructor | Of | Target | Type | Union | Intersection | This | Promisify | Implements | Interface | Private | Protected | Public | Out | Yield | New | This_1 | Super | LessThanSign | True | False | Null | Solidus | SolidusEqualsSign | LeftSquareBracket | LeftCurlyBracket | Function | Class | Delete | Void | Typeof | PlusSignPlusSign | HyphenMinusHyphenMinus | PlusSign | HyphenMinus | Tilde | ExclamationMark | RULE_IDENTIFIER | RULE_DOUBLE | RULE_INT | RULE_BINARY_INT | RULE_OCTAL_INT | RULE_LEGACY_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT | RULE_STRING | RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL | RULE_TEMPLATE_HEAD)=>
			(
				{
					newCompositeNode(grammarAccess.getYieldExpressionAccess().getExpressionAssignmentExpressionParserRuleCall_3_0());
				}
				lv_expression_3_0=norm3_AssignmentExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getYieldExpressionRule());
					}
					set(
						$current,
						"expression",
						lv_expression_3_0,
						"org.eclipse.n4js.N4JS.AssignmentExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)?
	)
;

// Entry rule entryRuleAssignmentOperator
entryRuleAssignmentOperator returns [String current=null]:
	{ newCompositeNode(grammarAccess.getAssignmentOperatorRule()); }
	iv_ruleAssignmentOperator=ruleAssignmentOperator
	{ $current=$iv_ruleAssignmentOperator.current.getText(); }
	EOF;

// Rule AssignmentOperator
ruleAssignmentOperator returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw=EqualsSign
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getAssignmentOperatorAccess().getEqualsSignKeyword_0());
		}
		    |
		kw=AsteriskEqualsSign
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getAssignmentOperatorAccess().getAsteriskEqualsSignKeyword_1());
		}
		    |
		kw=SolidusEqualsSign
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getAssignmentOperatorAccess().getSolidusEqualsSignKeyword_2());
		}
		    |
		kw=PercentSignEqualsSign
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getAssignmentOperatorAccess().getPercentSignEqualsSignKeyword_3());
		}
		    |
		kw=PlusSignEqualsSign
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getAssignmentOperatorAccess().getPlusSignEqualsSignKeyword_4());
		}
		    |
		kw=HyphenMinusEqualsSign
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getAssignmentOperatorAccess().getHyphenMinusEqualsSignKeyword_5());
		}
		    |
		kw=LessThanSignLessThanSignEqualsSign
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getAssignmentOperatorAccess().getLessThanSignLessThanSignEqualsSignKeyword_6());
		}
		    |
		(
			kw=GreaterThanSign
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getAssignmentOperatorAccess().getGreaterThanSignKeyword_7_0());
			}
			kw=GreaterThanSign
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getAssignmentOperatorAccess().getGreaterThanSignKeyword_7_1());
			}
			(
				kw=GreaterThanSign
				{
					$current.merge(kw);
					newLeafNode(kw, grammarAccess.getAssignmentOperatorAccess().getGreaterThanSignKeyword_7_2());
				}
			)?
			kw=EqualsSign
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getAssignmentOperatorAccess().getEqualsSignKeyword_7_3());
			}
		)
		    |
		kw=AmpersandEqualsSign
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getAssignmentOperatorAccess().getAmpersandEqualsSignKeyword_8());
		}
		    |
		kw=CircumflexAccentEqualsSign
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getAssignmentOperatorAccess().getCircumflexAccentEqualsSignKeyword_9());
		}
		    |
		kw=VerticalLineEqualsSign
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getAssignmentOperatorAccess().getVerticalLineEqualsSignKeyword_10());
		}
	)
;

// Entry rule entryRuleAwaitExpression
entryRuleAwaitExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAwaitExpressionRule()); }
	iv_ruleAwaitExpression=ruleAwaitExpression
	{ $current=$iv_ruleAwaitExpression.current; }
	EOF;

// Rule AwaitExpression
ruleAwaitExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				Await
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getAwaitExpressionAccess().getAwaitExpressionAction_0_0_0(),
							$current);
					}
				)
				otherlv_1=Await
				{
					newLeafNode(otherlv_1, grammarAccess.getAwaitExpressionAccess().getAwaitKeyword_0_0_1());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getAwaitExpressionAccess().getExpressionAssignmentExpressionParserRuleCall_1_0());
				}
				lv_expression_2_0=ruleAssignmentExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getAwaitExpressionRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.n4js.N4JS.AssignmentExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule AwaitExpression
norm1_AwaitExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				Await
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getAwaitExpressionAccess().getAwaitExpressionAction_0_0_0(),
							$current);
					}
				)
				otherlv_1=Await
				{
					newLeafNode(otherlv_1, grammarAccess.getAwaitExpressionAccess().getAwaitKeyword_0_0_1());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getAwaitExpressionAccess().getExpressionAssignmentExpressionParserRuleCall_1_0());
				}
				lv_expression_2_0=norm1_AssignmentExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getAwaitExpressionRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.n4js.N4JS.AssignmentExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule AwaitExpression
norm2_AwaitExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				Await
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getAwaitExpressionAccess().getAwaitExpressionAction_0_0_0(),
							$current);
					}
				)
				otherlv_1=Await
				{
					newLeafNode(otherlv_1, grammarAccess.getAwaitExpressionAccess().getAwaitKeyword_0_0_1());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getAwaitExpressionAccess().getExpressionAssignmentExpressionParserRuleCall_1_0());
				}
				lv_expression_2_0=norm2_AssignmentExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getAwaitExpressionRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.n4js.N4JS.AssignmentExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule AwaitExpression
norm3_AwaitExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				Await
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getAwaitExpressionAccess().getAwaitExpressionAction_0_0_0(),
							$current);
					}
				)
				otherlv_1=Await
				{
					newLeafNode(otherlv_1, grammarAccess.getAwaitExpressionAccess().getAwaitKeyword_0_0_1());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getAwaitExpressionAccess().getExpressionAssignmentExpressionParserRuleCall_1_0());
				}
				lv_expression_2_0=norm3_AssignmentExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getAwaitExpressionRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.n4js.N4JS.AssignmentExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRulePromisifyExpression
entryRulePromisifyExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPromisifyExpressionRule()); }
	iv_rulePromisifyExpression=rulePromisifyExpression
	{ $current=$iv_rulePromisifyExpression.current; }
	EOF;

// Rule PromisifyExpression
rulePromisifyExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				CommercialAt
				Promisify
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getPromisifyExpressionAccess().getPromisifyExpressionAction_0_0_0(),
							$current);
					}
				)
				otherlv_1=CommercialAt
				{
					newLeafNode(otherlv_1, grammarAccess.getPromisifyExpressionAccess().getCommercialAtKeyword_0_0_1());
				}
				otherlv_2=Promisify
				{
					newLeafNode(otherlv_2, grammarAccess.getPromisifyExpressionAccess().getPromisifyKeyword_0_0_2());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getPromisifyExpressionAccess().getExpressionAssignmentExpressionParserRuleCall_1_0());
				}
				lv_expression_3_0=ruleAssignmentExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPromisifyExpressionRule());
					}
					set(
						$current,
						"expression",
						lv_expression_3_0,
						"org.eclipse.n4js.N4JS.AssignmentExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule PromisifyExpression
norm1_PromisifyExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				CommercialAt
				Promisify
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getPromisifyExpressionAccess().getPromisifyExpressionAction_0_0_0(),
							$current);
					}
				)
				otherlv_1=CommercialAt
				{
					newLeafNode(otherlv_1, grammarAccess.getPromisifyExpressionAccess().getCommercialAtKeyword_0_0_1());
				}
				otherlv_2=Promisify
				{
					newLeafNode(otherlv_2, grammarAccess.getPromisifyExpressionAccess().getPromisifyKeyword_0_0_2());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getPromisifyExpressionAccess().getExpressionAssignmentExpressionParserRuleCall_1_0());
				}
				lv_expression_3_0=norm1_AssignmentExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPromisifyExpressionRule());
					}
					set(
						$current,
						"expression",
						lv_expression_3_0,
						"org.eclipse.n4js.N4JS.AssignmentExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule PromisifyExpression
norm2_PromisifyExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				CommercialAt
				Promisify
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getPromisifyExpressionAccess().getPromisifyExpressionAction_0_0_0(),
							$current);
					}
				)
				otherlv_1=CommercialAt
				{
					newLeafNode(otherlv_1, grammarAccess.getPromisifyExpressionAccess().getCommercialAtKeyword_0_0_1());
				}
				otherlv_2=Promisify
				{
					newLeafNode(otherlv_2, grammarAccess.getPromisifyExpressionAccess().getPromisifyKeyword_0_0_2());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getPromisifyExpressionAccess().getExpressionAssignmentExpressionParserRuleCall_1_0());
				}
				lv_expression_3_0=norm2_AssignmentExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPromisifyExpressionRule());
					}
					set(
						$current,
						"expression",
						lv_expression_3_0,
						"org.eclipse.n4js.N4JS.AssignmentExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule PromisifyExpression
norm3_PromisifyExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				CommercialAt
				Promisify
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getPromisifyExpressionAccess().getPromisifyExpressionAction_0_0_0(),
							$current);
					}
				)
				otherlv_1=CommercialAt
				{
					newLeafNode(otherlv_1, grammarAccess.getPromisifyExpressionAccess().getCommercialAtKeyword_0_0_1());
				}
				otherlv_2=Promisify
				{
					newLeafNode(otherlv_2, grammarAccess.getPromisifyExpressionAccess().getPromisifyKeyword_0_0_2());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getPromisifyExpressionAccess().getExpressionAssignmentExpressionParserRuleCall_1_0());
				}
				lv_expression_3_0=norm3_AssignmentExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPromisifyExpressionRule());
					}
					set(
						$current,
						"expression",
						lv_expression_3_0,
						"org.eclipse.n4js.N4JS.AssignmentExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleExpression
entryRuleExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getExpressionRule()); }
	iv_ruleExpression=ruleExpression
	{ $current=$iv_ruleExpression.current; }
	EOF;

// Rule Expression
ruleExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getExpressionAccess().getAssignmentExpressionParserRuleCall_0());
		}
		this_AssignmentExpression_0=ruleAssignmentExpression
		{
			$current = $this_AssignmentExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				{
					$current = forceCreateModelElementAndAdd(
						grammarAccess.getExpressionAccess().getCommaExpressionExprsAction_1_0(),
						$current);
				}
			)
			otherlv_2=Comma
			{
				newLeafNode(otherlv_2, grammarAccess.getExpressionAccess().getCommaKeyword_1_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getExpressionAccess().getExprsAssignmentExpressionParserRuleCall_1_2_0());
					}
					lv_exprs_3_0=ruleAssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getExpressionRule());
						}
						add(
							$current,
							"exprs",
							lv_exprs_3_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_4=Comma
				{
					newLeafNode(otherlv_4, grammarAccess.getExpressionAccess().getCommaKeyword_1_3_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getExpressionAccess().getExprsAssignmentExpressionParserRuleCall_1_3_1_0());
						}
						lv_exprs_5_0=ruleAssignmentExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getExpressionRule());
							}
							add(
								$current,
								"exprs",
								lv_exprs_5_0,
								"org.eclipse.n4js.N4JS.AssignmentExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
	)
;


// Rule Expression
norm1_Expression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getExpressionAccess().getAssignmentExpressionParserRuleCall_0());
		}
		this_AssignmentExpression_0=norm1_AssignmentExpression
		{
			$current = $this_AssignmentExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				{
					$current = forceCreateModelElementAndAdd(
						grammarAccess.getExpressionAccess().getCommaExpressionExprsAction_1_0(),
						$current);
				}
			)
			otherlv_2=Comma
			{
				newLeafNode(otherlv_2, grammarAccess.getExpressionAccess().getCommaKeyword_1_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getExpressionAccess().getExprsAssignmentExpressionParserRuleCall_1_2_0());
					}
					lv_exprs_3_0=norm1_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getExpressionRule());
						}
						add(
							$current,
							"exprs",
							lv_exprs_3_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_4=Comma
				{
					newLeafNode(otherlv_4, grammarAccess.getExpressionAccess().getCommaKeyword_1_3_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getExpressionAccess().getExprsAssignmentExpressionParserRuleCall_1_3_1_0());
						}
						lv_exprs_5_0=norm1_AssignmentExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getExpressionRule());
							}
							add(
								$current,
								"exprs",
								lv_exprs_5_0,
								"org.eclipse.n4js.N4JS.AssignmentExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
	)
;


// Rule Expression
norm2_Expression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getExpressionAccess().getAssignmentExpressionParserRuleCall_0());
		}
		this_AssignmentExpression_0=norm2_AssignmentExpression
		{
			$current = $this_AssignmentExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				{
					$current = forceCreateModelElementAndAdd(
						grammarAccess.getExpressionAccess().getCommaExpressionExprsAction_1_0(),
						$current);
				}
			)
			otherlv_2=Comma
			{
				newLeafNode(otherlv_2, grammarAccess.getExpressionAccess().getCommaKeyword_1_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getExpressionAccess().getExprsAssignmentExpressionParserRuleCall_1_2_0());
					}
					lv_exprs_3_0=norm2_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getExpressionRule());
						}
						add(
							$current,
							"exprs",
							lv_exprs_3_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_4=Comma
				{
					newLeafNode(otherlv_4, grammarAccess.getExpressionAccess().getCommaKeyword_1_3_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getExpressionAccess().getExprsAssignmentExpressionParserRuleCall_1_3_1_0());
						}
						lv_exprs_5_0=norm2_AssignmentExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getExpressionRule());
							}
							add(
								$current,
								"exprs",
								lv_exprs_5_0,
								"org.eclipse.n4js.N4JS.AssignmentExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
	)
;


// Rule Expression
norm3_Expression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getExpressionAccess().getAssignmentExpressionParserRuleCall_0());
		}
		this_AssignmentExpression_0=norm3_AssignmentExpression
		{
			$current = $this_AssignmentExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				{
					$current = forceCreateModelElementAndAdd(
						grammarAccess.getExpressionAccess().getCommaExpressionExprsAction_1_0(),
						$current);
				}
			)
			otherlv_2=Comma
			{
				newLeafNode(otherlv_2, grammarAccess.getExpressionAccess().getCommaKeyword_1_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getExpressionAccess().getExprsAssignmentExpressionParserRuleCall_1_2_0());
					}
					lv_exprs_3_0=norm3_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getExpressionRule());
						}
						add(
							$current,
							"exprs",
							lv_exprs_3_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_4=Comma
				{
					newLeafNode(otherlv_4, grammarAccess.getExpressionAccess().getCommaKeyword_1_3_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getExpressionAccess().getExprsAssignmentExpressionParserRuleCall_1_3_1_0());
						}
						lv_exprs_5_0=norm3_AssignmentExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getExpressionRule());
							}
							add(
								$current,
								"exprs",
								lv_exprs_5_0,
								"org.eclipse.n4js.N4JS.AssignmentExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
	)
;

// Entry rule entryRuleTemplateLiteral
entryRuleTemplateLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTemplateLiteralRule()); }
	iv_ruleTemplateLiteral=ruleTemplateLiteral
	{ $current=$iv_ruleTemplateLiteral.current; }
	EOF;

// Rule TemplateLiteral
ruleTemplateLiteral returns [EObject current=null]
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
					grammarAccess.getTemplateLiteralAccess().getTemplateLiteralAction_0(),
					$current);
			}
		)
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getTemplateLiteralAccess().getSegmentsNoSubstitutionTemplateParserRuleCall_1_0_0());
					}
					lv_segments_1_0=ruleNoSubstitutionTemplate
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTemplateLiteralRule());
						}
						add(
							$current,
							"segments",
							lv_segments_1_0,
							"org.eclipse.n4js.N4JS.NoSubstitutionTemplate");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getTemplateLiteralAccess().getSegmentsTemplateHeadParserRuleCall_1_1_0_0());
						}
						lv_segments_2_0=ruleTemplateHead
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTemplateLiteralRule());
							}
							add(
								$current,
								"segments",
								lv_segments_2_0,
								"org.eclipse.n4js.N4JS.TemplateHead");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getTemplateLiteralAccess().getSegmentsExpressionParserRuleCall_1_1_1_0());
						}
						lv_segments_3_0=norm1_Expression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTemplateLiteralRule());
							}
							add(
								$current,
								"segments",
								lv_segments_3_0,
								"org.eclipse.n4js.N4JS.Expression");
							afterParserOrEnumRuleCall();
						}
					)
				)?
				{
					newCompositeNode(grammarAccess.getTemplateLiteralAccess().getTemplateExpressionEndParserRuleCall_1_1_2());
				}
				ruleTemplateExpressionEnd
				{
					afterParserOrEnumRuleCall();
				}
				(
					(
						(
							{
								newCompositeNode(grammarAccess.getTemplateLiteralAccess().getSegmentsTemplateMiddleParserRuleCall_1_1_3_0_0());
							}
							lv_segments_5_0=ruleTemplateMiddle
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getTemplateLiteralRule());
								}
								add(
									$current,
									"segments",
									lv_segments_5_0,
									"org.eclipse.n4js.N4JS.TemplateMiddle");
								afterParserOrEnumRuleCall();
							}
						)
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getTemplateLiteralAccess().getSegmentsExpressionParserRuleCall_1_1_3_1_0());
							}
							lv_segments_6_0=norm1_Expression
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getTemplateLiteralRule());
								}
								add(
									$current,
									"segments",
									lv_segments_6_0,
									"org.eclipse.n4js.N4JS.Expression");
								afterParserOrEnumRuleCall();
							}
						)
					)?
					{
						newCompositeNode(grammarAccess.getTemplateLiteralAccess().getTemplateExpressionEndParserRuleCall_1_1_3_2());
					}
					ruleTemplateExpressionEnd
					{
						afterParserOrEnumRuleCall();
					}
				)*
				(
					(
						{
							newCompositeNode(grammarAccess.getTemplateLiteralAccess().getSegmentsTemplateTailParserRuleCall_1_1_4_0());
						}
						lv_segments_8_0=ruleTemplateTail
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTemplateLiteralRule());
							}
							add(
								$current,
								"segments",
								lv_segments_8_0,
								"org.eclipse.n4js.N4JS.TemplateTail");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)
	)
;


// Rule TemplateLiteral
norm1_TemplateLiteral returns [EObject current=null]
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
					grammarAccess.getTemplateLiteralAccess().getTemplateLiteralAction_0(),
					$current);
			}
		)
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getTemplateLiteralAccess().getSegmentsNoSubstitutionTemplateParserRuleCall_1_0_0());
					}
					lv_segments_1_0=ruleNoSubstitutionTemplate
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTemplateLiteralRule());
						}
						add(
							$current,
							"segments",
							lv_segments_1_0,
							"org.eclipse.n4js.N4JS.NoSubstitutionTemplate");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getTemplateLiteralAccess().getSegmentsTemplateHeadParserRuleCall_1_1_0_0());
						}
						lv_segments_2_0=ruleTemplateHead
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTemplateLiteralRule());
							}
							add(
								$current,
								"segments",
								lv_segments_2_0,
								"org.eclipse.n4js.N4JS.TemplateHead");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getTemplateLiteralAccess().getSegmentsExpressionParserRuleCall_1_1_1_0());
						}
						lv_segments_3_0=norm3_Expression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTemplateLiteralRule());
							}
							add(
								$current,
								"segments",
								lv_segments_3_0,
								"org.eclipse.n4js.N4JS.Expression");
							afterParserOrEnumRuleCall();
						}
					)
				)?
				{
					newCompositeNode(grammarAccess.getTemplateLiteralAccess().getTemplateExpressionEndParserRuleCall_1_1_2());
				}
				ruleTemplateExpressionEnd
				{
					afterParserOrEnumRuleCall();
				}
				(
					(
						(
							{
								newCompositeNode(grammarAccess.getTemplateLiteralAccess().getSegmentsTemplateMiddleParserRuleCall_1_1_3_0_0());
							}
							lv_segments_5_0=ruleTemplateMiddle
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getTemplateLiteralRule());
								}
								add(
									$current,
									"segments",
									lv_segments_5_0,
									"org.eclipse.n4js.N4JS.TemplateMiddle");
								afterParserOrEnumRuleCall();
							}
						)
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getTemplateLiteralAccess().getSegmentsExpressionParserRuleCall_1_1_3_1_0());
							}
							lv_segments_6_0=norm3_Expression
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getTemplateLiteralRule());
								}
								add(
									$current,
									"segments",
									lv_segments_6_0,
									"org.eclipse.n4js.N4JS.Expression");
								afterParserOrEnumRuleCall();
							}
						)
					)?
					{
						newCompositeNode(grammarAccess.getTemplateLiteralAccess().getTemplateExpressionEndParserRuleCall_1_1_3_2());
					}
					ruleTemplateExpressionEnd
					{
						afterParserOrEnumRuleCall();
					}
				)*
				(
					(
						{
							newCompositeNode(grammarAccess.getTemplateLiteralAccess().getSegmentsTemplateTailParserRuleCall_1_1_4_0());
						}
						lv_segments_8_0=ruleTemplateTail
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTemplateLiteralRule());
							}
							add(
								$current,
								"segments",
								lv_segments_8_0,
								"org.eclipse.n4js.N4JS.TemplateTail");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)
	)
;

// Entry rule entryRuleTemplateExpressionEnd
entryRuleTemplateExpressionEnd returns [String current=null]:
	{ newCompositeNode(grammarAccess.getTemplateExpressionEndRule()); }
	iv_ruleTemplateExpressionEnd=ruleTemplateExpressionEnd
	{ $current=$iv_ruleTemplateExpressionEnd.current.getText(); }
	EOF;

// Rule TemplateExpressionEnd
ruleTemplateExpressionEnd returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
	setInTemplateSegment();}
@after {
	leaveRule();
}:
	kw=RightCurlyBracket
	{
		$current.merge(kw);
		newLeafNode(kw, grammarAccess.getTemplateExpressionEndAccess().getRightCurlyBracketKeyword());
	}
;

// Entry rule entryRuleNoSubstitutionTemplate
entryRuleNoSubstitutionTemplate returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getNoSubstitutionTemplateRule()); }
	iv_ruleNoSubstitutionTemplate=ruleNoSubstitutionTemplate
	{ $current=$iv_ruleNoSubstitutionTemplate.current; }
	EOF;

// Rule NoSubstitutionTemplate
ruleNoSubstitutionTemplate returns [EObject current=null]
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
					grammarAccess.getNoSubstitutionTemplateAccess().getTemplateSegmentAction_0(),
					$current);
			}
		)
		(
			(
				lv_value_1_0=RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL
				{
					newLeafNode(lv_value_1_0, grammarAccess.getNoSubstitutionTemplateAccess().getValueNO_SUBSTITUTION_TEMPLATE_LITERALTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getNoSubstitutionTemplateRule());
					}
					setWithLastConsumed(
						$current,
						"value",
						lv_value_1_0,
						"org.eclipse.n4js.N4JS.NO_SUBSTITUTION_TEMPLATE_LITERAL");
				}
			)
		)
	)
;

// Entry rule entryRuleTemplateHead
entryRuleTemplateHead returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTemplateHeadRule()); }
	iv_ruleTemplateHead=ruleTemplateHead
	{ $current=$iv_ruleTemplateHead.current; }
	EOF;

// Rule TemplateHead
ruleTemplateHead returns [EObject current=null]
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
					grammarAccess.getTemplateHeadAccess().getTemplateSegmentAction_0(),
					$current);
			}
		)
		(
			(
				lv_value_1_0=RULE_TEMPLATE_HEAD
				{
					newLeafNode(lv_value_1_0, grammarAccess.getTemplateHeadAccess().getValueTEMPLATE_HEADTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTemplateHeadRule());
					}
					setWithLastConsumed(
						$current,
						"value",
						lv_value_1_0,
						"org.eclipse.n4js.N4JS.TEMPLATE_HEAD");
				}
			)
		)
	)
;

// Entry rule entryRuleTemplateTail
entryRuleTemplateTail returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTemplateTailRule()); }
	iv_ruleTemplateTail=ruleTemplateTail
	{ $current=$iv_ruleTemplateTail.current; }
	EOF;

// Rule TemplateTail
ruleTemplateTail returns [EObject current=null]
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
					grammarAccess.getTemplateTailAccess().getTemplateSegmentAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getTemplateTailAccess().getValueTemplateTailLiteralParserRuleCall_1_0());
				}
				lv_value_1_0=ruleTemplateTailLiteral
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTemplateTailRule());
					}
					set(
						$current,
						"value",
						lv_value_1_0,
						"org.eclipse.n4js.N4JS.TemplateTailLiteral");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleTemplateMiddle
entryRuleTemplateMiddle returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTemplateMiddleRule()); }
	iv_ruleTemplateMiddle=ruleTemplateMiddle
	{ $current=$iv_ruleTemplateMiddle.current; }
	EOF;

// Rule TemplateMiddle
ruleTemplateMiddle returns [EObject current=null]
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
					grammarAccess.getTemplateMiddleAccess().getTemplateSegmentAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getTemplateMiddleAccess().getValueTemplateMiddleLiteralParserRuleCall_1_0());
				}
				lv_value_1_0=ruleTemplateMiddleLiteral
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTemplateMiddleRule());
					}
					set(
						$current,
						"value",
						lv_value_1_0,
						"org.eclipse.n4js.N4JS.TemplateMiddleLiteral");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleLiteral
entryRuleLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getLiteralRule()); }
	iv_ruleLiteral=ruleLiteral
	{ $current=$iv_ruleLiteral.current; }
	EOF;

// Rule Literal
ruleLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getLiteralAccess().getNumericLiteralParserRuleCall_0());
		}
		this_NumericLiteral_0=ruleNumericLiteral
		{
			$current = $this_NumericLiteral_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getLiteralAccess().getBooleanLiteralParserRuleCall_1());
		}
		this_BooleanLiteral_1=ruleBooleanLiteral
		{
			$current = $this_BooleanLiteral_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getLiteralAccess().getStringLiteralParserRuleCall_2());
		}
		this_StringLiteral_2=ruleStringLiteral
		{
			$current = $this_StringLiteral_2.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getLiteralAccess().getNullLiteralParserRuleCall_3());
		}
		this_NullLiteral_3=ruleNullLiteral
		{
			$current = $this_NullLiteral_3.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getLiteralAccess().getRegularExpressionLiteralParserRuleCall_4());
		}
		this_RegularExpressionLiteral_4=ruleRegularExpressionLiteral
		{
			$current = $this_RegularExpressionLiteral_4.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleNullLiteral
entryRuleNullLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getNullLiteralRule()); }
	iv_ruleNullLiteral=ruleNullLiteral
	{ $current=$iv_ruleNullLiteral.current; }
	EOF;

// Rule NullLiteral
ruleNullLiteral returns [EObject current=null]
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
					grammarAccess.getNullLiteralAccess().getNullLiteralAction_0(),
					$current);
			}
		)
		otherlv_1=Null
		{
			newLeafNode(otherlv_1, grammarAccess.getNullLiteralAccess().getNullKeyword_1());
		}
	)
;

// Entry rule entryRuleBooleanLiteral
entryRuleBooleanLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getBooleanLiteralRule()); }
	iv_ruleBooleanLiteral=ruleBooleanLiteral
	{ $current=$iv_ruleBooleanLiteral.current; }
	EOF;

// Rule BooleanLiteral
ruleBooleanLiteral returns [EObject current=null]
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
					grammarAccess.getBooleanLiteralAccess().getBooleanLiteralAction_0(),
					$current);
			}
		)
		(
			(
				(
					lv_true_1_0=True
					{
						newLeafNode(lv_true_1_0, grammarAccess.getBooleanLiteralAccess().getTrueTrueKeyword_1_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getBooleanLiteralRule());
						}
						setWithLastConsumed($current, "true", true, "true");
					}
				)
			)
			    |
			otherlv_2=False
			{
				newLeafNode(otherlv_2, grammarAccess.getBooleanLiteralAccess().getFalseKeyword_1_1());
			}
		)
	)
;

// Entry rule entryRuleStringLiteral
entryRuleStringLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getStringLiteralRule()); }
	iv_ruleStringLiteral=ruleStringLiteral
	{ $current=$iv_ruleStringLiteral.current; }
	EOF;

// Rule StringLiteral
ruleStringLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_value_0_0=RULE_STRING
			{
				newLeafNode(lv_value_0_0, grammarAccess.getStringLiteralAccess().getValueSTRINGTerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getStringLiteralRule());
				}
				setWithLastConsumed(
					$current,
					"value",
					lv_value_0_0,
					"org.eclipse.n4js.N4JS.STRING");
			}
		)
	)
;

// Entry rule entryRuleNumericLiteral
entryRuleNumericLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getNumericLiteralRule()); }
	iv_ruleNumericLiteral=ruleNumericLiteral
	{ $current=$iv_ruleNumericLiteral.current; }
	EOF;

// Rule NumericLiteral
ruleNumericLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getNumericLiteralAccess().getDoubleLiteralParserRuleCall_0());
		}
		this_DoubleLiteral_0=ruleDoubleLiteral
		{
			$current = $this_DoubleLiteral_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getNumericLiteralAccess().getIntLiteralParserRuleCall_1());
		}
		this_IntLiteral_1=ruleIntLiteral
		{
			$current = $this_IntLiteral_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getNumericLiteralAccess().getBinaryIntLiteralParserRuleCall_2());
		}
		this_BinaryIntLiteral_2=ruleBinaryIntLiteral
		{
			$current = $this_BinaryIntLiteral_2.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getNumericLiteralAccess().getOctalIntLiteralParserRuleCall_3());
		}
		this_OctalIntLiteral_3=ruleOctalIntLiteral
		{
			$current = $this_OctalIntLiteral_3.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getNumericLiteralAccess().getLegacyOctalIntLiteralParserRuleCall_4());
		}
		this_LegacyOctalIntLiteral_4=ruleLegacyOctalIntLiteral
		{
			$current = $this_LegacyOctalIntLiteral_4.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getNumericLiteralAccess().getHexIntLiteralParserRuleCall_5());
		}
		this_HexIntLiteral_5=ruleHexIntLiteral
		{
			$current = $this_HexIntLiteral_5.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getNumericLiteralAccess().getScientificIntLiteralParserRuleCall_6());
		}
		this_ScientificIntLiteral_6=ruleScientificIntLiteral
		{
			$current = $this_ScientificIntLiteral_6.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleDoubleLiteral
entryRuleDoubleLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getDoubleLiteralRule()); }
	iv_ruleDoubleLiteral=ruleDoubleLiteral
	{ $current=$iv_ruleDoubleLiteral.current; }
	EOF;

// Rule DoubleLiteral
ruleDoubleLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_value_0_0=RULE_DOUBLE
			{
				newLeafNode(lv_value_0_0, grammarAccess.getDoubleLiteralAccess().getValueDOUBLETerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getDoubleLiteralRule());
				}
				setWithLastConsumed(
					$current,
					"value",
					lv_value_0_0,
					"org.eclipse.n4js.N4JS.DOUBLE");
			}
		)
	)
;

// Entry rule entryRuleIntLiteral
entryRuleIntLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getIntLiteralRule()); }
	iv_ruleIntLiteral=ruleIntLiteral
	{ $current=$iv_ruleIntLiteral.current; }
	EOF;

// Rule IntLiteral
ruleIntLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_value_0_0=RULE_INT
			{
				newLeafNode(lv_value_0_0, grammarAccess.getIntLiteralAccess().getValueINTTerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getIntLiteralRule());
				}
				setWithLastConsumed(
					$current,
					"value",
					lv_value_0_0,
					"org.eclipse.n4js.ts.TypeExpressions.INT");
			}
		)
	)
;

// Entry rule entryRuleOctalIntLiteral
entryRuleOctalIntLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getOctalIntLiteralRule()); }
	iv_ruleOctalIntLiteral=ruleOctalIntLiteral
	{ $current=$iv_ruleOctalIntLiteral.current; }
	EOF;

// Rule OctalIntLiteral
ruleOctalIntLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_value_0_0=RULE_OCTAL_INT
			{
				newLeafNode(lv_value_0_0, grammarAccess.getOctalIntLiteralAccess().getValueOCTAL_INTTerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getOctalIntLiteralRule());
				}
				setWithLastConsumed(
					$current,
					"value",
					lv_value_0_0,
					"org.eclipse.n4js.N4JS.OCTAL_INT");
			}
		)
	)
;

// Entry rule entryRuleLegacyOctalIntLiteral
entryRuleLegacyOctalIntLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getLegacyOctalIntLiteralRule()); }
	iv_ruleLegacyOctalIntLiteral=ruleLegacyOctalIntLiteral
	{ $current=$iv_ruleLegacyOctalIntLiteral.current; }
	EOF;

// Rule LegacyOctalIntLiteral
ruleLegacyOctalIntLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_value_0_0=RULE_LEGACY_OCTAL_INT
			{
				newLeafNode(lv_value_0_0, grammarAccess.getLegacyOctalIntLiteralAccess().getValueLEGACY_OCTAL_INTTerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getLegacyOctalIntLiteralRule());
				}
				setWithLastConsumed(
					$current,
					"value",
					lv_value_0_0,
					"org.eclipse.n4js.N4JS.LEGACY_OCTAL_INT");
			}
		)
	)
;

// Entry rule entryRuleHexIntLiteral
entryRuleHexIntLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getHexIntLiteralRule()); }
	iv_ruleHexIntLiteral=ruleHexIntLiteral
	{ $current=$iv_ruleHexIntLiteral.current; }
	EOF;

// Rule HexIntLiteral
ruleHexIntLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_value_0_0=RULE_HEX_INT
			{
				newLeafNode(lv_value_0_0, grammarAccess.getHexIntLiteralAccess().getValueHEX_INTTerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getHexIntLiteralRule());
				}
				setWithLastConsumed(
					$current,
					"value",
					lv_value_0_0,
					"org.eclipse.n4js.N4JS.HEX_INT");
			}
		)
	)
;

// Entry rule entryRuleBinaryIntLiteral
entryRuleBinaryIntLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getBinaryIntLiteralRule()); }
	iv_ruleBinaryIntLiteral=ruleBinaryIntLiteral
	{ $current=$iv_ruleBinaryIntLiteral.current; }
	EOF;

// Rule BinaryIntLiteral
ruleBinaryIntLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_value_0_0=RULE_BINARY_INT
			{
				newLeafNode(lv_value_0_0, grammarAccess.getBinaryIntLiteralAccess().getValueBINARY_INTTerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getBinaryIntLiteralRule());
				}
				setWithLastConsumed(
					$current,
					"value",
					lv_value_0_0,
					"org.eclipse.n4js.N4JS.BINARY_INT");
			}
		)
	)
;

// Entry rule entryRuleScientificIntLiteral
entryRuleScientificIntLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getScientificIntLiteralRule()); }
	iv_ruleScientificIntLiteral=ruleScientificIntLiteral
	{ $current=$iv_ruleScientificIntLiteral.current; }
	EOF;

// Rule ScientificIntLiteral
ruleScientificIntLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_value_0_0=RULE_SCIENTIFIC_INT
			{
				newLeafNode(lv_value_0_0, grammarAccess.getScientificIntLiteralAccess().getValueSCIENTIFIC_INTTerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getScientificIntLiteralRule());
				}
				setWithLastConsumed(
					$current,
					"value",
					lv_value_0_0,
					"org.eclipse.n4js.N4JS.SCIENTIFIC_INT");
			}
		)
	)
;

// Entry rule entryRuleRegularExpressionLiteral
entryRuleRegularExpressionLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getRegularExpressionLiteralRule()); }
	iv_ruleRegularExpressionLiteral=ruleRegularExpressionLiteral
	{ $current=$iv_ruleRegularExpressionLiteral.current; }
	EOF;

// Rule RegularExpressionLiteral
ruleRegularExpressionLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getRegularExpressionLiteralAccess().getValueREGEX_LITERALParserRuleCall_0());
			}
			lv_value_0_0=ruleREGEX_LITERAL
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getRegularExpressionLiteralRule());
				}
				set(
					$current,
					"value",
					lv_value_0_0,
					"org.eclipse.n4js.N4JS.REGEX_LITERAL");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleNumericLiteralAsString
entryRuleNumericLiteralAsString returns [String current=null]:
	{ newCompositeNode(grammarAccess.getNumericLiteralAsStringRule()); }
	iv_ruleNumericLiteralAsString=ruleNumericLiteralAsString
	{ $current=$iv_ruleNumericLiteralAsString.current.getText(); }
	EOF;

// Rule NumericLiteralAsString
ruleNumericLiteralAsString returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_DOUBLE_0=RULE_DOUBLE
		{
			$current.merge(this_DOUBLE_0);
		}
		{
			newLeafNode(this_DOUBLE_0, grammarAccess.getNumericLiteralAsStringAccess().getDOUBLETerminalRuleCall_0());
		}
		    |
		this_INT_1=RULE_INT
		{
			$current.merge(this_INT_1);
		}
		{
			newLeafNode(this_INT_1, grammarAccess.getNumericLiteralAsStringAccess().getINTTerminalRuleCall_1());
		}
		    |
		this_OCTAL_INT_2=RULE_OCTAL_INT
		{
			$current.merge(this_OCTAL_INT_2);
		}
		{
			newLeafNode(this_OCTAL_INT_2, grammarAccess.getNumericLiteralAsStringAccess().getOCTAL_INTTerminalRuleCall_2());
		}
		    |
		this_HEX_INT_3=RULE_HEX_INT
		{
			$current.merge(this_HEX_INT_3);
		}
		{
			newLeafNode(this_HEX_INT_3, grammarAccess.getNumericLiteralAsStringAccess().getHEX_INTTerminalRuleCall_3());
		}
		    |
		this_SCIENTIFIC_INT_4=RULE_SCIENTIFIC_INT
		{
			$current.merge(this_SCIENTIFIC_INT_4);
		}
		{
			newLeafNode(this_SCIENTIFIC_INT_4, grammarAccess.getNumericLiteralAsStringAccess().getSCIENTIFIC_INTTerminalRuleCall_4());
		}
	)
;

// Entry rule entryRuleIdentifierOrThis
entryRuleIdentifierOrThis returns [String current=null]:
	{ newCompositeNode(grammarAccess.getIdentifierOrThisRule()); }
	iv_ruleIdentifierOrThis=ruleIdentifierOrThis
	{ $current=$iv_ruleIdentifierOrThis.current.getText(); }
	EOF;

// Rule IdentifierOrThis
ruleIdentifierOrThis returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_IDENTIFIER_0=RULE_IDENTIFIER
		{
			$current.merge(this_IDENTIFIER_0);
		}
		{
			newLeafNode(this_IDENTIFIER_0, grammarAccess.getIdentifierOrThisAccess().getIDENTIFIERTerminalRuleCall_0());
		}
		    |
		kw=This
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getIdentifierOrThisAccess().getThisKeyword_1());
		}
		    |
		kw=Promisify
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getIdentifierOrThisAccess().getPromisifyKeyword_2());
		}
		    |
		kw=Target
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getIdentifierOrThisAccess().getTargetKeyword_3());
		}
	)
;

// Entry rule entryRuleAnnotationName
entryRuleAnnotationName returns [String current=null]:
	{ newCompositeNode(grammarAccess.getAnnotationNameRule()); }
	iv_ruleAnnotationName=ruleAnnotationName
	{ $current=$iv_ruleAnnotationName.current.getText(); }
	EOF;

// Rule AnnotationName
ruleAnnotationName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_IDENTIFIER_0=RULE_IDENTIFIER
		{
			$current.merge(this_IDENTIFIER_0);
		}
		{
			newLeafNode(this_IDENTIFIER_0, grammarAccess.getAnnotationNameAccess().getIDENTIFIERTerminalRuleCall_0());
		}
		    |
		kw=This
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getAnnotationNameAccess().getThisKeyword_1());
		}
		    |
		kw=Target
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getAnnotationNameAccess().getTargetKeyword_2());
		}
	)
;

// Entry rule entryRuleREGEX_LITERAL
entryRuleREGEX_LITERAL returns [String current=null]:
	{ newCompositeNode(grammarAccess.getREGEX_LITERALRule()); }
	iv_ruleREGEX_LITERAL=ruleREGEX_LITERAL
	{ $current=$iv_ruleREGEX_LITERAL.current.getText(); }
	EOF;

// Rule REGEX_LITERAL
ruleREGEX_LITERAL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
	setInRegularExpression();
}
@after {
	leaveRule();
}:
	(
		(
			kw=Solidus
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getREGEX_LITERALAccess().getSolidusKeyword_0_0());
			}
			    |
			kw=SolidusEqualsSign
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getREGEX_LITERALAccess().getSolidusEqualsSignKeyword_0_1());
			}
		)
		(
			this_REGEX_TAIL_2=RULE_REGEX_TAIL
			{
				$current.merge(this_REGEX_TAIL_2);
			}
			{
				newLeafNode(this_REGEX_TAIL_2, grammarAccess.getREGEX_LITERALAccess().getREGEX_TAILTerminalRuleCall_1());
			}
		)?
	)
;

// Entry rule entryRuleTemplateTailLiteral
entryRuleTemplateTailLiteral returns [String current=null]:
	{ newCompositeNode(grammarAccess.getTemplateTailLiteralRule()); }
	iv_ruleTemplateTailLiteral=ruleTemplateTailLiteral
	{ $current=$iv_ruleTemplateTailLiteral.current.getText(); }
	EOF;

// Rule TemplateTailLiteral
ruleTemplateTailLiteral returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_TEMPLATE_END_0=RULE_TEMPLATE_END
		{
			$current.merge(this_TEMPLATE_END_0);
		}
		{
			newLeafNode(this_TEMPLATE_END_0, grammarAccess.getTemplateTailLiteralAccess().getTEMPLATE_ENDTerminalRuleCall());
		}
	)?
;

// Entry rule entryRuleTemplateMiddleLiteral
entryRuleTemplateMiddleLiteral returns [String current=null]:
	{ newCompositeNode(grammarAccess.getTemplateMiddleLiteralRule()); }
	iv_ruleTemplateMiddleLiteral=ruleTemplateMiddleLiteral
	{ $current=$iv_ruleTemplateMiddleLiteral.current.getText(); }
	EOF;

// Rule TemplateMiddleLiteral
ruleTemplateMiddleLiteral returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	this_TEMPLATE_MIDDLE_0=RULE_TEMPLATE_MIDDLE
	{
		$current.merge(this_TEMPLATE_MIDDLE_0);
	}
	{
		newLeafNode(this_TEMPLATE_MIDDLE_0, grammarAccess.getTemplateMiddleLiteralAccess().getTEMPLATE_MIDDLETerminalRuleCall());
	}
;

// Entry rule entryRuleSemi
entryRuleSemi returns [String current=null]:
	{ newCompositeNode(grammarAccess.getSemiRule()); }
	iv_ruleSemi=ruleSemi
	{ $current=$iv_ruleSemi.current.getText(); }
	EOF;

// REPLACEMENT ruleSemi.g.replacement START
// Rule Semi
ruleSemi returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] 
    @init { enterRule();
		// injected by AutomaticSemicolonInjector
		int marker = input.mark();
		// Promote EOL if appropriate	
		promoteEOL();    }
    @after { leaveRule(); }:

	kw=Semicolon
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getSemiAccess().getSemicolonKeyword()); 
    }
	|
	kw=(
		  EOF
		| RULE_EOL
		| RULE_ML_COMMENT
   )
    {
        addASIMessage();
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getSemiAccess().getSemicolonKeyword()); 
    }
    | RightCurlyBracket { forcedRewind(marker) }?
    ;
// REPLACEMENT ruleSemi.g.replacement END


// Rule NoLineTerminator
ruleNoLineTerminator[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_NO_LINE_TERMINATOR_0=RULE_NO_LINE_TERMINATOR
		{
			newLeafNode(this_NO_LINE_TERMINATOR_0, grammarAccess.getNoLineTerminatorAccess().getNO_LINE_TERMINATORTerminalRuleCall());
		}
	)?
;

// Entry rule entryRuleAnnotation
entryRuleAnnotation returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAnnotationRule()); }
	iv_ruleAnnotation=ruleAnnotation
	{ $current=$iv_ruleAnnotation.current; }
	EOF;

// Rule Annotation
ruleAnnotation returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=CommercialAt
		{
			newLeafNode(otherlv_0, grammarAccess.getAnnotationAccess().getCommercialAtKeyword_0());
		}
		{
			newCompositeNode(grammarAccess.getAnnotationAccess().getAnnotationNoAtSignParserRuleCall_1());
		}
		this_AnnotationNoAtSign_1=ruleAnnotationNoAtSign
		{
			$current = $this_AnnotationNoAtSign_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleScriptAnnotation
entryRuleScriptAnnotation returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getScriptAnnotationRule()); }
	iv_ruleScriptAnnotation=ruleScriptAnnotation
	{ $current=$iv_ruleScriptAnnotation.current; }
	EOF;

// Rule ScriptAnnotation
ruleScriptAnnotation returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=CommercialAtCommercialAt
		{
			newLeafNode(otherlv_0, grammarAccess.getScriptAnnotationAccess().getCommercialAtCommercialAtKeyword_0());
		}
		{
			newCompositeNode(grammarAccess.getScriptAnnotationAccess().getAnnotationNoAtSignParserRuleCall_1());
		}
		this_AnnotationNoAtSign_1=ruleAnnotationNoAtSign
		{
			$current = $this_AnnotationNoAtSign_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleAnnotationNoAtSign
entryRuleAnnotationNoAtSign returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAnnotationNoAtSignRule()); }
	iv_ruleAnnotationNoAtSign=ruleAnnotationNoAtSign
	{ $current=$iv_ruleAnnotationNoAtSign.current; }
	EOF;

// Rule AnnotationNoAtSign
ruleAnnotationNoAtSign returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getAnnotationNoAtSignAccess().getNameAnnotationNameParserRuleCall_0_0());
				}
				lv_name_0_0=ruleAnnotationName
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getAnnotationNoAtSignRule());
					}
					set(
						$current,
						"name",
						lv_name_0_0,
						"org.eclipse.n4js.N4JS.AnnotationName");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				(LeftParenthesis)=>
				otherlv_1=LeftParenthesis
				{
					newLeafNode(otherlv_1, grammarAccess.getAnnotationNoAtSignAccess().getLeftParenthesisKeyword_1_0());
				}
			)
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotationNoAtSignAccess().getArgsAnnotationArgumentParserRuleCall_1_1_0_0());
						}
						lv_args_2_0=ruleAnnotationArgument
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotationNoAtSignRule());
							}
							add(
								$current,
								"args",
								lv_args_2_0,
								"org.eclipse.n4js.N4JS.AnnotationArgument");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					otherlv_3=Comma
					{
						newLeafNode(otherlv_3, grammarAccess.getAnnotationNoAtSignAccess().getCommaKeyword_1_1_1_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getAnnotationNoAtSignAccess().getArgsAnnotationArgumentParserRuleCall_1_1_1_1_0());
							}
							lv_args_4_0=ruleAnnotationArgument
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getAnnotationNoAtSignRule());
								}
								add(
									$current,
									"args",
									lv_args_4_0,
									"org.eclipse.n4js.N4JS.AnnotationArgument");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)*
			)?
			otherlv_5=RightParenthesis
			{
				newLeafNode(otherlv_5, grammarAccess.getAnnotationNoAtSignAccess().getRightParenthesisKeyword_1_2());
			}
		)?
	)
;

// Entry rule entryRuleAnnotationArgument
entryRuleAnnotationArgument returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAnnotationArgumentRule()); }
	iv_ruleAnnotationArgument=ruleAnnotationArgument
	{ $current=$iv_ruleAnnotationArgument.current; }
	EOF;

// Rule AnnotationArgument
ruleAnnotationArgument returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getAnnotationArgumentAccess().getLiteralAnnotationArgumentParserRuleCall_0());
		}
		this_LiteralAnnotationArgument_0=ruleLiteralAnnotationArgument
		{
			$current = $this_LiteralAnnotationArgument_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getAnnotationArgumentAccess().getTypeRefAnnotationArgumentParserRuleCall_1());
		}
		this_TypeRefAnnotationArgument_1=ruleTypeRefAnnotationArgument
		{
			$current = $this_TypeRefAnnotationArgument_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleLiteralAnnotationArgument
entryRuleLiteralAnnotationArgument returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getLiteralAnnotationArgumentRule()); }
	iv_ruleLiteralAnnotationArgument=ruleLiteralAnnotationArgument
	{ $current=$iv_ruleLiteralAnnotationArgument.current; }
	EOF;

// Rule LiteralAnnotationArgument
ruleLiteralAnnotationArgument returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getLiteralAnnotationArgumentAccess().getLiteralLiteralParserRuleCall_0());
			}
			lv_literal_0_0=ruleLiteral
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getLiteralAnnotationArgumentRule());
				}
				set(
					$current,
					"literal",
					lv_literal_0_0,
					"org.eclipse.n4js.N4JS.Literal");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleTypeRefAnnotationArgument
entryRuleTypeRefAnnotationArgument returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTypeRefAnnotationArgumentRule()); }
	iv_ruleTypeRefAnnotationArgument=ruleTypeRefAnnotationArgument
	{ $current=$iv_ruleTypeRefAnnotationArgument.current; }
	EOF;

// Rule TypeRefAnnotationArgument
ruleTypeRefAnnotationArgument returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getTypeRefAnnotationArgumentAccess().getTypeRefTypeRefParserRuleCall_0());
			}
			lv_typeRef_0_0=ruleTypeRef
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getTypeRefAnnotationArgumentRule());
				}
				set(
					$current,
					"typeRef",
					lv_typeRef_0_0,
					"org.eclipse.n4js.ts.TypeExpressions.TypeRef");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleAnnotationList
entryRuleAnnotationList returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAnnotationListRule()); }
	iv_ruleAnnotationList=ruleAnnotationList
	{ $current=$iv_ruleAnnotationList.current; }
	EOF;

// Rule AnnotationList
ruleAnnotationList returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				CommercialAt
				(
					This
					    |
					Target
					    |
					RULE_IDENTIFIER
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getAnnotationListAccess().getAnnotationListAction_0_0_0(),
							$current);
					}
				)
				otherlv_1=CommercialAt
				{
					newLeafNode(otherlv_1, grammarAccess.getAnnotationListAccess().getCommercialAtKeyword_0_0_1());
				}
				(
					(This | Target | RULE_IDENTIFIER)=>
					(
						{
							newCompositeNode(grammarAccess.getAnnotationListAccess().getAnnotationsAnnotationNoAtSignParserRuleCall_0_0_2_0());
						}
						lv_annotations_2_0=ruleAnnotationNoAtSign
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotationListRule());
							}
							add(
								$current,
								"annotations",
								lv_annotations_2_0,
								"org.eclipse.n4js.N4JS.AnnotationNoAtSign");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getAnnotationListAccess().getAnnotationsAnnotationParserRuleCall_1_0());
				}
				lv_annotations_3_0=ruleAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getAnnotationListRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_3_0,
						"org.eclipse.n4js.N4JS.Annotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
	)
;

// Entry rule entryRuleExpressionAnnotationList
entryRuleExpressionAnnotationList returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getExpressionAnnotationListRule()); }
	iv_ruleExpressionAnnotationList=ruleExpressionAnnotationList
	{ $current=$iv_ruleExpressionAnnotationList.current; }
	EOF;

// Rule ExpressionAnnotationList
ruleExpressionAnnotationList returns [EObject current=null]
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
					grammarAccess.getExpressionAnnotationListAccess().getExpressionAnnotationListAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getExpressionAnnotationListAccess().getAnnotationsAnnotationParserRuleCall_1_0());
				}
				lv_annotations_1_0=ruleAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getExpressionAnnotationListRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_1_0,
						"org.eclipse.n4js.N4JS.Annotation");
					afterParserOrEnumRuleCall();
				}
			)
		)+
	)
;

// Entry rule entryRulePropertyAssignmentAnnotationList
entryRulePropertyAssignmentAnnotationList returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPropertyAssignmentAnnotationListRule()); }
	iv_rulePropertyAssignmentAnnotationList=rulePropertyAssignmentAnnotationList
	{ $current=$iv_rulePropertyAssignmentAnnotationList.current; }
	EOF;

// Rule PropertyAssignmentAnnotationList
rulePropertyAssignmentAnnotationList returns [EObject current=null]
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
					grammarAccess.getPropertyAssignmentAnnotationListAccess().getPropertyAssignmentAnnotationListAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getPropertyAssignmentAnnotationListAccess().getAnnotationsAnnotationParserRuleCall_1_0());
				}
				lv_annotations_1_0=ruleAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPropertyAssignmentAnnotationListRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_1_0,
						"org.eclipse.n4js.N4JS.Annotation");
					afterParserOrEnumRuleCall();
				}
			)
		)+
	)
;

// Entry rule entryRuleN4MemberAnnotationList
entryRuleN4MemberAnnotationList returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getN4MemberAnnotationListRule()); }
	iv_ruleN4MemberAnnotationList=ruleN4MemberAnnotationList
	{ $current=$iv_ruleN4MemberAnnotationList.current; }
	EOF;

// Rule N4MemberAnnotationList
ruleN4MemberAnnotationList returns [EObject current=null]
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
					grammarAccess.getN4MemberAnnotationListAccess().getN4MemberAnnotationListAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getN4MemberAnnotationListAccess().getAnnotationsAnnotationParserRuleCall_1_0());
				}
				lv_annotations_1_0=ruleAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getN4MemberAnnotationListRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_1_0,
						"org.eclipse.n4js.N4JS.Annotation");
					afterParserOrEnumRuleCall();
				}
			)
		)+
	)
;


// Rule TypeReference
ruleTypeReference[EObject in_current]  returns [EObject current=in_current]
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
						if ($current==null) {
							$current = createModelElement(grammarAccess.getTypeReferenceRule());
						}
					}
					{
						newCompositeNode(grammarAccess.getTypeReferenceAccess().getAstNamespaceModuleNamespaceVirtualTypeCrossReference_0_0_0());
					}
					ruleTypeReferenceName
					{
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_1=FullStop
			{
				newLeafNode(otherlv_1, grammarAccess.getTypeReferenceAccess().getFullStopKeyword_0_1());
			}
		)?
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTypeReferenceRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getTypeReferenceAccess().getDeclaredTypeTypeCrossReference_1_0());
				}
				ruleTypeReferenceName
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleTypeReferenceName
entryRuleTypeReferenceName returns [String current=null]:
	{ newCompositeNode(grammarAccess.getTypeReferenceNameRule()); }
	iv_ruleTypeReferenceName=ruleTypeReferenceName
	{ $current=$iv_ruleTypeReferenceName.current.getText(); }
	EOF;

// Rule TypeReferenceName
ruleTypeReferenceName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw=Void
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypeReferenceNameAccess().getVoidKeyword_0());
		}
		    |
		kw=This
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypeReferenceNameAccess().getThisKeyword_1());
		}
		    |
		kw=Await
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypeReferenceNameAccess().getAwaitKeyword_2());
		}
		    |
		kw=Promisify
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypeReferenceNameAccess().getPromisifyKeyword_3());
		}
		    |
		kw=Target
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypeReferenceNameAccess().getTargetKeyword_4());
		}
		    |
		kw=Default
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypeReferenceNameAccess().getDefaultKeyword_5());
		}
		    |
		this_IDENTIFIER_6=RULE_IDENTIFIER
		{
			$current.merge(this_IDENTIFIER_6);
		}
		{
			newLeafNode(this_IDENTIFIER_6, grammarAccess.getTypeReferenceNameAccess().getIDENTIFIERTerminalRuleCall_6());
		}
	)
;

// Entry rule entryRuleN4ClassDeclaration
entryRuleN4ClassDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getN4ClassDeclarationRule()); }
	iv_ruleN4ClassDeclaration=ruleN4ClassDeclaration
	{ $current=$iv_ruleN4ClassDeclaration.current; }
	EOF;

// Rule N4ClassDeclaration
ruleN4ClassDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
					(
						ruleN4Modifier
					)
				)*
				Class
				(
					(
						ruleTypingStrategyDefSiteOperator
					)
				)?
				(
					(
						ruleBindingIdentifier
					)
				)?
				(
					ruleVersionDeclaration[null]
				)?
			)
			)=>
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getN4ClassDeclarationAccess().getDeclaredModifiersN4ModifierEnumRuleCall_0_0_0_0());
						}
						lv_declaredModifiers_0_0=ruleN4Modifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getN4ClassDeclarationRule());
							}
							add(
								$current,
								"declaredModifiers",
								lv_declaredModifiers_0_0,
								"org.eclipse.n4js.N4JS.N4Modifier");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				otherlv_1=Class
				{
					newLeafNode(otherlv_1, grammarAccess.getN4ClassDeclarationAccess().getClassKeyword_0_0_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getN4ClassDeclarationAccess().getTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0_2_0());
						}
						lv_typingStrategy_2_0=ruleTypingStrategyDefSiteOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getN4ClassDeclarationRule());
							}
							set(
								$current,
								"typingStrategy",
								lv_typingStrategy_2_0,
								"org.eclipse.n4js.ts.TypeExpressions.TypingStrategyDefSiteOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)?
				(
					(
						{
							newCompositeNode(grammarAccess.getN4ClassDeclarationAccess().getNameBindingIdentifierParserRuleCall_0_0_3_0());
						}
						lv_name_3_0=ruleBindingIdentifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getN4ClassDeclarationRule());
							}
							set(
								$current,
								"name",
								lv_name_3_0,
								"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
							afterParserOrEnumRuleCall();
						}
					)
				)?
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getN4ClassDeclarationRule());
						}
						newCompositeNode(grammarAccess.getN4ClassDeclarationAccess().getVersionDeclarationParserRuleCall_0_0_4());
					}
					this_VersionDeclaration_4=ruleVersionDeclaration[$current]
					{
						$current = $this_VersionDeclaration_4.current;
						afterParserOrEnumRuleCall();
					}
				)?
			)
		)
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getN4ClassDeclarationRule());
				}
				newCompositeNode(grammarAccess.getN4ClassDeclarationAccess().getTypeVariablesParserRuleCall_1());
			}
			this_TypeVariables_5=ruleTypeVariables[$current]
			{
				$current = $this_TypeVariables_5.current;
				afterParserOrEnumRuleCall();
			}
		)?
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getN4ClassDeclarationRule());
				}
				newCompositeNode(grammarAccess.getN4ClassDeclarationAccess().getClassExtendsImplementsParserRuleCall_2());
			}
			this_ClassExtendsImplements_6=ruleClassExtendsImplements[$current]
			{
				$current = $this_ClassExtendsImplements_6.current;
				afterParserOrEnumRuleCall();
			}
		)?
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getN4ClassDeclarationRule());
			}
			newCompositeNode(grammarAccess.getN4ClassDeclarationAccess().getMembersParserRuleCall_3());
		}
		this_Members_7=ruleMembers[$current]
		{
			$current = $this_Members_7.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule Members
ruleMembers[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LeftCurlyBracket
		{
			newLeafNode(otherlv_0, grammarAccess.getMembersAccess().getLeftCurlyBracketKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getMembersAccess().getOwnedMembersRawN4MemberDeclarationParserRuleCall_1_0());
				}
				lv_ownedMembersRaw_1_0=ruleN4MemberDeclaration
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getMembersRule());
					}
					add(
						$current,
						"ownedMembersRaw",
						lv_ownedMembersRaw_1_0,
						"org.eclipse.n4js.N4JS.N4MemberDeclaration");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_2=RightCurlyBracket
		{
			newLeafNode(otherlv_2, grammarAccess.getMembersAccess().getRightCurlyBracketKeyword_2());
		}
	)
;


// Rule Members
norm1_Members[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LeftCurlyBracket
		{
			newLeafNode(otherlv_0, grammarAccess.getMembersAccess().getLeftCurlyBracketKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getMembersAccess().getOwnedMembersRawN4MemberDeclarationParserRuleCall_1_0());
				}
				lv_ownedMembersRaw_1_0=norm1_N4MemberDeclaration
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getMembersRule());
					}
					add(
						$current,
						"ownedMembersRaw",
						lv_ownedMembersRaw_1_0,
						"org.eclipse.n4js.N4JS.N4MemberDeclaration");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_2=RightCurlyBracket
		{
			newLeafNode(otherlv_2, grammarAccess.getMembersAccess().getRightCurlyBracketKeyword_2());
		}
	)
;


// Rule ClassExtendsImplements
ruleClassExtendsImplements[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getClassExtendsImplementsRule());
				}
				newCompositeNode(grammarAccess.getClassExtendsImplementsAccess().getClassExtendsClauseParserRuleCall_0_0());
			}
			this_ClassExtendsClause_0=ruleClassExtendsClause[$current]
			{
				$current = $this_ClassExtendsClause_0.current;
				afterParserOrEnumRuleCall();
			}
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getClassExtendsImplementsRule());
					}
					newCompositeNode(grammarAccess.getClassExtendsImplementsAccess().getClassImplementsListParserRuleCall_0_1());
				}
				this_ClassImplementsList_1=ruleClassImplementsList[$current]
				{
					$current = $this_ClassImplementsList_1.current;
					afterParserOrEnumRuleCall();
				}
			)?
		)
		    |
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getClassExtendsImplementsRule());
				}
				newCompositeNode(grammarAccess.getClassExtendsImplementsAccess().getClassImplementsListParserRuleCall_1_0());
			}
			this_ClassImplementsList_2=ruleClassImplementsList[$current]
			{
				$current = $this_ClassImplementsList_2.current;
				afterParserOrEnumRuleCall();
			}
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getClassExtendsImplementsRule());
					}
					newCompositeNode(grammarAccess.getClassExtendsImplementsAccess().getClassExtendsClauseParserRuleCall_1_1());
				}
				this_ClassExtendsClause_3=ruleClassExtendsClause[$current]
				{
					$current = $this_ClassExtendsClause_3.current;
					afterParserOrEnumRuleCall();
				}
			)?
		)
	)
;


// Rule ClassExtendsImplements
norm1_ClassExtendsImplements[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getClassExtendsImplementsRule());
				}
				newCompositeNode(grammarAccess.getClassExtendsImplementsAccess().getClassExtendsClauseParserRuleCall_0_0());
			}
			this_ClassExtendsClause_0=norm1_ClassExtendsClause[$current]
			{
				$current = $this_ClassExtendsClause_0.current;
				afterParserOrEnumRuleCall();
			}
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getClassExtendsImplementsRule());
					}
					newCompositeNode(grammarAccess.getClassExtendsImplementsAccess().getClassImplementsListParserRuleCall_0_1());
				}
				this_ClassImplementsList_1=ruleClassImplementsList[$current]
				{
					$current = $this_ClassImplementsList_1.current;
					afterParserOrEnumRuleCall();
				}
			)?
		)
		    |
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getClassExtendsImplementsRule());
				}
				newCompositeNode(grammarAccess.getClassExtendsImplementsAccess().getClassImplementsListParserRuleCall_1_0());
			}
			this_ClassImplementsList_2=ruleClassImplementsList[$current]
			{
				$current = $this_ClassImplementsList_2.current;
				afterParserOrEnumRuleCall();
			}
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getClassExtendsImplementsRule());
					}
					newCompositeNode(grammarAccess.getClassExtendsImplementsAccess().getClassExtendsClauseParserRuleCall_1_1());
				}
				this_ClassExtendsClause_3=norm1_ClassExtendsClause[$current]
				{
					$current = $this_ClassExtendsClause_3.current;
					afterParserOrEnumRuleCall();
				}
			)?
		)
	)
;


// Rule ClassExtendsClause
ruleClassExtendsClause[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Extends
		{
			newLeafNode(otherlv_0, grammarAccess.getClassExtendsClauseAccess().getExtendsKeyword_0());
		}
		(
			(
				((
					ruleParameterizedTypeRefNominal
				)
				)=>
				(
					{
						newCompositeNode(grammarAccess.getClassExtendsClauseAccess().getSuperClassRefParameterizedTypeRefNominalParserRuleCall_1_0_0());
					}
					lv_superClassRef_1_0=ruleParameterizedTypeRefNominal
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getClassExtendsClauseRule());
						}
						set(
							$current,
							"superClassRef",
							lv_superClassRef_1_0,
							"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getClassExtendsClauseAccess().getSuperClassExpressionLeftHandSideExpressionParserRuleCall_1_1_0());
					}
					lv_superClassExpression_2_0=ruleLeftHandSideExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getClassExtendsClauseRule());
						}
						set(
							$current,
							"superClassExpression",
							lv_superClassExpression_2_0,
							"org.eclipse.n4js.N4JS.LeftHandSideExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;


// Rule ClassExtendsClause
norm1_ClassExtendsClause[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Extends
		{
			newLeafNode(otherlv_0, grammarAccess.getClassExtendsClauseAccess().getExtendsKeyword_0());
		}
		(
			(
				((
					ruleParameterizedTypeRefNominal
				)
				)=>
				(
					{
						newCompositeNode(grammarAccess.getClassExtendsClauseAccess().getSuperClassRefParameterizedTypeRefNominalParserRuleCall_1_0_0());
					}
					lv_superClassRef_1_0=ruleParameterizedTypeRefNominal
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getClassExtendsClauseRule());
						}
						set(
							$current,
							"superClassRef",
							lv_superClassRef_1_0,
							"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getClassExtendsClauseAccess().getSuperClassExpressionLeftHandSideExpressionParserRuleCall_1_1_0());
					}
					lv_superClassExpression_2_0=norm1_LeftHandSideExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getClassExtendsClauseRule());
						}
						set(
							$current,
							"superClassExpression",
							lv_superClassExpression_2_0,
							"org.eclipse.n4js.N4JS.LeftHandSideExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;


// Rule ClassImplementsList
ruleClassImplementsList[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Implements
		{
			newLeafNode(otherlv_0, grammarAccess.getClassImplementsListAccess().getImplementsKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getClassImplementsListAccess().getImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_1_0());
				}
				lv_implementedInterfaceRefs_1_0=ruleParameterizedTypeRefNominal
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getClassImplementsListRule());
					}
					add(
						$current,
						"implementedInterfaceRefs",
						lv_implementedInterfaceRefs_1_0,
						"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_2=Comma
			{
				newLeafNode(otherlv_2, grammarAccess.getClassImplementsListAccess().getCommaKeyword_2_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getClassImplementsListAccess().getImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_2_1_0());
					}
					lv_implementedInterfaceRefs_3_0=ruleParameterizedTypeRefNominal
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getClassImplementsListRule());
						}
						add(
							$current,
							"implementedInterfaceRefs",
							lv_implementedInterfaceRefs_3_0,
							"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleN4ClassExpression
entryRuleN4ClassExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getN4ClassExpressionRule()); }
	iv_ruleN4ClassExpression=ruleN4ClassExpression
	{ $current=$iv_ruleN4ClassExpression.current; }
	EOF;

// Rule N4ClassExpression
ruleN4ClassExpression returns [EObject current=null]
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
					grammarAccess.getN4ClassExpressionAccess().getN4ClassExpressionAction_0(),
					$current);
			}
		)
		otherlv_1=Class
		{
			newLeafNode(otherlv_1, grammarAccess.getN4ClassExpressionAccess().getClassKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getN4ClassExpressionAccess().getNameBindingIdentifierParserRuleCall_2_0());
				}
				lv_name_2_0=ruleBindingIdentifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getN4ClassExpressionRule());
					}
					set(
						$current,
						"name",
						lv_name_2_0,
						"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getN4ClassExpressionRule());
				}
				newCompositeNode(grammarAccess.getN4ClassExpressionAccess().getClassExtendsImplementsParserRuleCall_3());
			}
			this_ClassExtendsImplements_3=ruleClassExtendsImplements[$current]
			{
				$current = $this_ClassExtendsImplements_3.current;
				afterParserOrEnumRuleCall();
			}
		)?
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getN4ClassExpressionRule());
			}
			newCompositeNode(grammarAccess.getN4ClassExpressionAccess().getMembersParserRuleCall_4());
		}
		this_Members_4=ruleMembers[$current]
		{
			$current = $this_Members_4.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule N4ClassExpression
norm1_N4ClassExpression returns [EObject current=null]
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
					grammarAccess.getN4ClassExpressionAccess().getN4ClassExpressionAction_0(),
					$current);
			}
		)
		otherlv_1=Class
		{
			newLeafNode(otherlv_1, grammarAccess.getN4ClassExpressionAccess().getClassKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getN4ClassExpressionAccess().getNameBindingIdentifierParserRuleCall_2_0());
				}
				lv_name_2_0=norm1_BindingIdentifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getN4ClassExpressionRule());
					}
					set(
						$current,
						"name",
						lv_name_2_0,
						"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getN4ClassExpressionRule());
				}
				newCompositeNode(grammarAccess.getN4ClassExpressionAccess().getClassExtendsImplementsParserRuleCall_3());
			}
			this_ClassExtendsImplements_3=norm1_ClassExtendsImplements[$current]
			{
				$current = $this_ClassExtendsImplements_3.current;
				afterParserOrEnumRuleCall();
			}
		)?
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getN4ClassExpressionRule());
			}
			newCompositeNode(grammarAccess.getN4ClassExpressionAccess().getMembersParserRuleCall_4());
		}
		this_Members_4=norm1_Members[$current]
		{
			$current = $this_Members_4.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleN4InterfaceDeclaration
entryRuleN4InterfaceDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getN4InterfaceDeclarationRule()); }
	iv_ruleN4InterfaceDeclaration=ruleN4InterfaceDeclaration
	{ $current=$iv_ruleN4InterfaceDeclaration.current; }
	EOF;

// Rule N4InterfaceDeclaration
ruleN4InterfaceDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
					(
						ruleN4Modifier
					)
				)*
				Interface
				(
					(
						ruleTypingStrategyDefSiteOperator
					)
				)?
				(
					(
						ruleBindingIdentifier
					)
				)?
				(
					ruleVersionDeclaration[null]
				)?
			)
			)=>
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getN4InterfaceDeclarationAccess().getDeclaredModifiersN4ModifierEnumRuleCall_0_0_0_0());
						}
						lv_declaredModifiers_0_0=ruleN4Modifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getN4InterfaceDeclarationRule());
							}
							add(
								$current,
								"declaredModifiers",
								lv_declaredModifiers_0_0,
								"org.eclipse.n4js.N4JS.N4Modifier");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				otherlv_1=Interface
				{
					newLeafNode(otherlv_1, grammarAccess.getN4InterfaceDeclarationAccess().getInterfaceKeyword_0_0_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getN4InterfaceDeclarationAccess().getTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0_2_0());
						}
						lv_typingStrategy_2_0=ruleTypingStrategyDefSiteOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getN4InterfaceDeclarationRule());
							}
							set(
								$current,
								"typingStrategy",
								lv_typingStrategy_2_0,
								"org.eclipse.n4js.ts.TypeExpressions.TypingStrategyDefSiteOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)?
				(
					(
						{
							newCompositeNode(grammarAccess.getN4InterfaceDeclarationAccess().getNameBindingIdentifierParserRuleCall_0_0_3_0());
						}
						lv_name_3_0=ruleBindingIdentifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getN4InterfaceDeclarationRule());
							}
							set(
								$current,
								"name",
								lv_name_3_0,
								"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
							afterParserOrEnumRuleCall();
						}
					)
				)?
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getN4InterfaceDeclarationRule());
						}
						newCompositeNode(grammarAccess.getN4InterfaceDeclarationAccess().getVersionDeclarationParserRuleCall_0_0_4());
					}
					this_VersionDeclaration_4=ruleVersionDeclaration[$current]
					{
						$current = $this_VersionDeclaration_4.current;
						afterParserOrEnumRuleCall();
					}
				)?
			)
		)
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getN4InterfaceDeclarationRule());
				}
				newCompositeNode(grammarAccess.getN4InterfaceDeclarationAccess().getTypeVariablesParserRuleCall_1());
			}
			this_TypeVariables_5=ruleTypeVariables[$current]
			{
				$current = $this_TypeVariables_5.current;
				afterParserOrEnumRuleCall();
			}
		)?
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getN4InterfaceDeclarationRule());
				}
				newCompositeNode(grammarAccess.getN4InterfaceDeclarationAccess().getInterfaceExtendsListParserRuleCall_2());
			}
			this_InterfaceExtendsList_6=ruleInterfaceExtendsList[$current]
			{
				$current = $this_InterfaceExtendsList_6.current;
				afterParserOrEnumRuleCall();
			}
		)?
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getN4InterfaceDeclarationRule());
			}
			newCompositeNode(grammarAccess.getN4InterfaceDeclarationAccess().getMembersParserRuleCall_3());
		}
		this_Members_7=ruleMembers[$current]
		{
			$current = $this_Members_7.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule InterfaceExtendsList
ruleInterfaceExtendsList[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			otherlv_0=Extends
			{
				newLeafNode(otherlv_0, grammarAccess.getInterfaceExtendsListAccess().getExtendsKeyword_0_0());
			}
			    |
			otherlv_1=Implements
			{
				newLeafNode(otherlv_1, grammarAccess.getInterfaceExtendsListAccess().getImplementsKeyword_0_1());
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getInterfaceExtendsListAccess().getSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_1_0());
				}
				lv_superInterfaceRefs_2_0=ruleParameterizedTypeRefNominal
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getInterfaceExtendsListRule());
					}
					add(
						$current,
						"superInterfaceRefs",
						lv_superInterfaceRefs_2_0,
						"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_3=Comma
			{
				newLeafNode(otherlv_3, grammarAccess.getInterfaceExtendsListAccess().getCommaKeyword_2_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getInterfaceExtendsListAccess().getSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_2_1_0());
					}
					lv_superInterfaceRefs_4_0=ruleParameterizedTypeRefNominal
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getInterfaceExtendsListRule());
						}
						add(
							$current,
							"superInterfaceRefs",
							lv_superInterfaceRefs_4_0,
							"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleN4EnumDeclaration
entryRuleN4EnumDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getN4EnumDeclarationRule()); }
	iv_ruleN4EnumDeclaration=ruleN4EnumDeclaration
	{ $current=$iv_ruleN4EnumDeclaration.current; }
	EOF;

// Rule N4EnumDeclaration
ruleN4EnumDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				(
					(
						ruleN4Modifier
					)
				)*
				Enum
				(
					(
						ruleBindingIdentifier
					)
				)?
				(
					ruleVersionDeclaration[null]
				)?
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getN4EnumDeclarationAccess().getN4EnumDeclarationAction_0_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getN4EnumDeclarationAccess().getDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0());
						}
						lv_declaredModifiers_1_0=ruleN4Modifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getN4EnumDeclarationRule());
							}
							add(
								$current,
								"declaredModifiers",
								lv_declaredModifiers_1_0,
								"org.eclipse.n4js.N4JS.N4Modifier");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				otherlv_2=Enum
				{
					newLeafNode(otherlv_2, grammarAccess.getN4EnumDeclarationAccess().getEnumKeyword_0_0_2());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getN4EnumDeclarationAccess().getNameBindingIdentifierParserRuleCall_0_0_3_0());
						}
						lv_name_3_0=ruleBindingIdentifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getN4EnumDeclarationRule());
							}
							set(
								$current,
								"name",
								lv_name_3_0,
								"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
							afterParserOrEnumRuleCall();
						}
					)
				)?
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getN4EnumDeclarationRule());
						}
						newCompositeNode(grammarAccess.getN4EnumDeclarationAccess().getVersionDeclarationParserRuleCall_0_0_4());
					}
					this_VersionDeclaration_4=ruleVersionDeclaration[$current]
					{
						$current = $this_VersionDeclaration_4.current;
						afterParserOrEnumRuleCall();
					}
				)?
			)
		)
		otherlv_5=LeftCurlyBracket
		{
			newLeafNode(otherlv_5, grammarAccess.getN4EnumDeclarationAccess().getLeftCurlyBracketKeyword_1());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getN4EnumDeclarationAccess().getLiteralsN4EnumLiteralParserRuleCall_2_0_0());
					}
					lv_literals_6_0=ruleN4EnumLiteral
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getN4EnumDeclarationRule());
						}
						add(
							$current,
							"literals",
							lv_literals_6_0,
							"org.eclipse.n4js.N4JS.N4EnumLiteral");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_7=Comma
				{
					newLeafNode(otherlv_7, grammarAccess.getN4EnumDeclarationAccess().getCommaKeyword_2_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getN4EnumDeclarationAccess().getLiteralsN4EnumLiteralParserRuleCall_2_1_1_0());
						}
						lv_literals_8_0=ruleN4EnumLiteral
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getN4EnumDeclarationRule());
							}
							add(
								$current,
								"literals",
								lv_literals_8_0,
								"org.eclipse.n4js.N4JS.N4EnumLiteral");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_9=RightCurlyBracket
		{
			newLeafNode(otherlv_9, grammarAccess.getN4EnumDeclarationAccess().getRightCurlyBracketKeyword_3());
		}
	)
;

// Entry rule entryRuleN4EnumLiteral
entryRuleN4EnumLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getN4EnumLiteralRule()); }
	iv_ruleN4EnumLiteral=ruleN4EnumLiteral
	{ $current=$iv_ruleN4EnumLiteral.current; }
	EOF;

// Rule N4EnumLiteral
ruleN4EnumLiteral returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getN4EnumLiteralAccess().getNameIdentifierNameParserRuleCall_0_0());
				}
				lv_name_0_0=ruleIdentifierName
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getN4EnumLiteralRule());
					}
					set(
						$current,
						"name",
						lv_name_0_0,
						"org.eclipse.n4js.ts.TypeExpressions.IdentifierName");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_1=Colon
			{
				newLeafNode(otherlv_1, grammarAccess.getN4EnumLiteralAccess().getColonKeyword_1_0());
			}
			(
				(
					lv_value_2_0=RULE_STRING
					{
						newLeafNode(lv_value_2_0, grammarAccess.getN4EnumLiteralAccess().getValueSTRINGTerminalRuleCall_1_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getN4EnumLiteralRule());
						}
						setWithLastConsumed(
							$current,
							"value",
							lv_value_2_0,
							"org.eclipse.n4js.N4JS.STRING");
					}
				)
			)
		)?
	)
;

// Entry rule entryRuleN4MemberDeclaration
entryRuleN4MemberDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getN4MemberDeclarationRule()); }
	iv_ruleN4MemberDeclaration=ruleN4MemberDeclaration
	{ $current=$iv_ruleN4MemberDeclaration.current; }
	EOF;

// Rule N4MemberDeclaration
ruleN4MemberDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getN4MemberDeclarationAccess().getAnnotatedN4MemberDeclarationParserRuleCall_0());
		}
		this_AnnotatedN4MemberDeclaration_0=ruleAnnotatedN4MemberDeclaration
		{
			$current = $this_AnnotatedN4MemberDeclaration_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			((
				(
				)
				(
					(
						ruleN4Modifier
					)
				)*
				ruleGetterHeader[null]
			)
			)=>
			{
				newCompositeNode(grammarAccess.getN4MemberDeclarationAccess().getN4GetterDeclarationParserRuleCall_1());
			}
			this_N4GetterDeclaration_1=ruleN4GetterDeclaration
			{
				$current = $this_N4GetterDeclaration_1.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				(
					(
						ruleN4Modifier
					)
				)*
				Set
				(
					Break
					    |
					Case
					    |
					Catch
					    |
					Class
					    |
					Const
					    |
					Continue
					    |
					Debugger
					    |
					Default
					    |
					Delete
					    |
					Do
					    |
					Else
					    |
					Export
					    |
					Extends
					    |
					Finally
					    |
					For
					    |
					Function
					    |
					If
					    |
					Import
					    |
					In
					    |
					Instanceof
					    |
					New
					    |
					Return
					    |
					Super
					    |
					Switch
					    |
					This_1
					    |
					Throw
					    |
					Try
					    |
					Typeof
					    |
					Var
					    |
					Void
					    |
					While
					    |
					With
					    |
					Yield
					    |
					Null
					    |
					True
					    |
					False
					    |
					Enum
					    |
					Get
					    |
					Set
					    |
					Let
					    |
					Project
					    |
					External
					    |
					Abstract
					    |
					Static
					    |
					As
					    |
					From
					    |
					Constructor
					    |
					Of
					    |
					Target
					    |
					Type
					    |
					Union
					    |
					Intersection
					    |
					This
					    |
					Promisify
					    |
					Await
					    |
					Async
					    |
					Implements
					    |
					Interface
					    |
					Private
					    |
					Protected
					    |
					Public
					    |
					Out
					    |
					LeftSquareBracket
					    |
					RULE_IDENTIFIER
					    |
					RULE_STRING
					    |
					RULE_DOUBLE
					    |
					RULE_INT
					    |
					RULE_OCTAL_INT
					    |
					RULE_HEX_INT
					    |
					RULE_SCIENTIFIC_INT
				)
			)
			)=>
			{
				newCompositeNode(grammarAccess.getN4MemberDeclarationAccess().getN4SetterDeclarationParserRuleCall_2());
			}
			this_N4SetterDeclaration_2=ruleN4SetterDeclaration
			{
				$current = $this_N4SetterDeclaration_2.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				(
					(
						ruleN4Modifier
					)
				)*
				(
					ruleTypeVariables[null]
				)?
				(
					ruleBogusTypeRefFragment[null]
				)?
				(
					(
						(
							(
								Asterisk
							)
						)
						(
							(
								ruleLiteralOrComputedPropertyName
							)
						)
						LeftParenthesis
					)
					    |
					(
						ruleAsyncNoTrailingLineBreak[null]
						(
							(
								ruleLiteralOrComputedPropertyName
							)
						)
						LeftParenthesis
					)
				)
			)
			)=>
			{
				newCompositeNode(grammarAccess.getN4MemberDeclarationAccess().getN4MethodDeclarationParserRuleCall_3());
			}
			this_N4MethodDeclaration_3=ruleN4MethodDeclaration
			{
				$current = $this_N4MethodDeclaration_3.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getN4MemberDeclarationAccess().getN4FieldDeclarationParserRuleCall_4());
		}
		this_N4FieldDeclaration_4=ruleN4FieldDeclaration
		{
			$current = $this_N4FieldDeclaration_4.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getN4MemberDeclarationAccess().getN4CallableConstructorDeclarationParserRuleCall_5());
		}
		this_N4CallableConstructorDeclaration_5=ruleN4CallableConstructorDeclaration
		{
			$current = $this_N4CallableConstructorDeclaration_5.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule N4MemberDeclaration
norm1_N4MemberDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getN4MemberDeclarationAccess().getAnnotatedN4MemberDeclarationParserRuleCall_0());
		}
		this_AnnotatedN4MemberDeclaration_0=norm1_AnnotatedN4MemberDeclaration
		{
			$current = $this_AnnotatedN4MemberDeclaration_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			((
				(
				)
				(
					(
						ruleN4Modifier
					)
				)*
				norm1_GetterHeader[null]
			)
			)=>
			{
				newCompositeNode(grammarAccess.getN4MemberDeclarationAccess().getN4GetterDeclarationParserRuleCall_1());
			}
			this_N4GetterDeclaration_1=norm1_N4GetterDeclaration
			{
				$current = $this_N4GetterDeclaration_1.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				(
					(
						ruleN4Modifier
					)
				)*
				Set
				(
					Break
					    |
					Case
					    |
					Catch
					    |
					Class
					    |
					Const
					    |
					Continue
					    |
					Debugger
					    |
					Default
					    |
					Delete
					    |
					Do
					    |
					Else
					    |
					Export
					    |
					Extends
					    |
					Finally
					    |
					For
					    |
					Function
					    |
					If
					    |
					Import
					    |
					In
					    |
					Instanceof
					    |
					New
					    |
					Return
					    |
					Super
					    |
					Switch
					    |
					This_1
					    |
					Throw
					    |
					Try
					    |
					Typeof
					    |
					Var
					    |
					Void
					    |
					While
					    |
					With
					    |
					Yield
					    |
					Null
					    |
					True
					    |
					False
					    |
					Enum
					    |
					Get
					    |
					Set
					    |
					Let
					    |
					Project
					    |
					External
					    |
					Abstract
					    |
					Static
					    |
					As
					    |
					From
					    |
					Constructor
					    |
					Of
					    |
					Target
					    |
					Type
					    |
					Union
					    |
					Intersection
					    |
					This
					    |
					Promisify
					    |
					Await
					    |
					Async
					    |
					Implements
					    |
					Interface
					    |
					Private
					    |
					Protected
					    |
					Public
					    |
					Out
					    |
					LeftSquareBracket
					    |
					RULE_IDENTIFIER
					    |
					RULE_STRING
					    |
					RULE_DOUBLE
					    |
					RULE_INT
					    |
					RULE_OCTAL_INT
					    |
					RULE_HEX_INT
					    |
					RULE_SCIENTIFIC_INT
				)
			)
			)=>
			{
				newCompositeNode(grammarAccess.getN4MemberDeclarationAccess().getN4SetterDeclarationParserRuleCall_2());
			}
			this_N4SetterDeclaration_2=norm1_N4SetterDeclaration
			{
				$current = $this_N4SetterDeclaration_2.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				(
					(
						ruleN4Modifier
					)
				)*
				(
					ruleTypeVariables[null]
				)?
				(
					ruleBogusTypeRefFragment[null]
				)?
				(
					(
						(
							(
								Asterisk
							)
						)
						(
							(
								norm1_LiteralOrComputedPropertyName
							)
						)
						LeftParenthesis
					)
					    |
					(
						ruleAsyncNoTrailingLineBreak[null]
						(
							(
								norm1_LiteralOrComputedPropertyName
							)
						)
						LeftParenthesis
					)
				)
			)
			)=>
			{
				newCompositeNode(grammarAccess.getN4MemberDeclarationAccess().getN4MethodDeclarationParserRuleCall_3());
			}
			this_N4MethodDeclaration_3=norm1_N4MethodDeclaration
			{
				$current = $this_N4MethodDeclaration_3.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getN4MemberDeclarationAccess().getN4FieldDeclarationParserRuleCall_4());
		}
		this_N4FieldDeclaration_4=norm1_N4FieldDeclaration
		{
			$current = $this_N4FieldDeclaration_4.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getN4MemberDeclarationAccess().getN4CallableConstructorDeclarationParserRuleCall_5());
		}
		this_N4CallableConstructorDeclaration_5=norm1_N4CallableConstructorDeclaration
		{
			$current = $this_N4CallableConstructorDeclaration_5.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleAnnotatedN4MemberDeclaration
entryRuleAnnotatedN4MemberDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationRule()); }
	iv_ruleAnnotatedN4MemberDeclaration=ruleAnnotatedN4MemberDeclaration
	{ $current=$iv_ruleAnnotatedN4MemberDeclaration.current; }
	EOF;

// Rule AnnotatedN4MemberDeclaration
ruleAnnotatedN4MemberDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getN4MemberAnnotationListParserRuleCall_0());
		}
		this_N4MemberAnnotationList_0=ruleN4MemberAnnotationList
		{
			$current = $this_N4MemberAnnotationList_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					((
						(
						)
						(
							(
								ruleN4Modifier
							)
						)*
						ruleGetterHeader[null]
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAnnotatedN4MemberDeclarationAccess().getN4GetterDeclarationAnnotationListAction_1_0_0_0_0(),
									$current);
							}
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredModifiersN4ModifierEnumRuleCall_1_0_0_0_1_0());
								}
								lv_declaredModifiers_2_0=ruleN4Modifier
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedN4MemberDeclarationRule());
									}
									add(
										$current,
										"declaredModifiers",
										lv_declaredModifiers_2_0,
										"org.eclipse.n4js.N4JS.N4Modifier");
									afterParserOrEnumRuleCall();
								}
							)
						)*
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getAnnotatedN4MemberDeclarationRule());
							}
							newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getGetterHeaderParserRuleCall_1_0_0_0_2());
						}
						this_GetterHeader_3=ruleGetterHeader[$current]
						{
							$current = $this_GetterHeader_3.current;
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					((
						(
						)
						LeftCurlyBracket
					)
					)=>
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getBodyBlockParserRuleCall_1_0_1_0());
						}
						lv_body_4_0=ruleBlock
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedN4MemberDeclarationRule());
							}
							set(
								$current,
								"body",
								lv_body_4_0,
								"org.eclipse.n4js.N4JS.Block");
							afterParserOrEnumRuleCall();
						}
					)
				)?
				(
					otherlv_5=Semicolon
					{
						newLeafNode(otherlv_5, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSemicolonKeyword_1_0_2());
					}
				)?
			)
			    |
			(
				(
					((
						(
						)
						(
							(
								ruleN4Modifier
							)
						)*
						Set
						(
							Break
							    |
							Case
							    |
							Catch
							    |
							Class
							    |
							Const
							    |
							Continue
							    |
							Debugger
							    |
							Default
							    |
							Delete
							    |
							Do
							    |
							Else
							    |
							Export
							    |
							Extends
							    |
							Finally
							    |
							For
							    |
							Function
							    |
							If
							    |
							Import
							    |
							In
							    |
							Instanceof
							    |
							New
							    |
							Return
							    |
							Super
							    |
							Switch
							    |
							This_1
							    |
							Throw
							    |
							Try
							    |
							Typeof
							    |
							Var
							    |
							Void
							    |
							While
							    |
							With
							    |
							Yield
							    |
							Null
							    |
							True
							    |
							False
							    |
							Enum
							    |
							Get
							    |
							Set
							    |
							Let
							    |
							Project
							    |
							External
							    |
							Abstract
							    |
							Static
							    |
							As
							    |
							From
							    |
							Constructor
							    |
							Of
							    |
							Target
							    |
							Type
							    |
							Union
							    |
							Intersection
							    |
							This
							    |
							Promisify
							    |
							Await
							    |
							Async
							    |
							Implements
							    |
							Interface
							    |
							Private
							    |
							Protected
							    |
							Public
							    |
							Out
							    |
							LeftSquareBracket
							    |
							RULE_IDENTIFIER
							    |
							RULE_STRING
							    |
							RULE_DOUBLE
							    |
							RULE_INT
							    |
							RULE_OCTAL_INT
							    |
							RULE_HEX_INT
							    |
							RULE_SCIENTIFIC_INT
						)
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAnnotatedN4MemberDeclarationAccess().getN4SetterDeclarationAnnotationListAction_1_1_0_0_0(),
									$current);
							}
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredModifiersN4ModifierEnumRuleCall_1_1_0_0_1_0());
								}
								lv_declaredModifiers_7_0=ruleN4Modifier
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedN4MemberDeclarationRule());
									}
									add(
										$current,
										"declaredModifiers",
										lv_declaredModifiers_7_0,
										"org.eclipse.n4js.N4JS.N4Modifier");
									afterParserOrEnumRuleCall();
								}
							)
						)*
						otherlv_8=Set
						{
							newLeafNode(otherlv_8, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSetKeyword_1_1_0_0_2());
						}
						(
							(Break | Case | Catch | Class | Const | Continue | Debugger | Default | Delete | Do | Else | Export | Extends | Finally | For | Function | If | Import | In | Instanceof | New | Return | Super | Switch | This_1 | Throw | Try | Typeof | Var | Void | While | With | Yield | Null | True | False | Enum | Get | Set | Let | Project | External | Abstract | Static | As | From | Constructor | Of | Target | Type | Union | Intersection | This | Promisify | Await | Async | Implements | Interface | Private | Protected | Public | Out | LeftSquareBracket | RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_1_0_0_3_0());
								}
								lv_declaredName_9_0=ruleLiteralOrComputedPropertyName
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedN4MemberDeclarationRule());
									}
									set(
										$current,
										"declaredName",
										lv_declaredName_9_0,
										"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
									afterParserOrEnumRuleCall();
								}
							)
						)
					)
				)
				(
					(
						lv_declaredOptional_10_0=QuestionMark
						{
							newLeafNode(lv_declaredOptional_10_0, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredOptionalQuestionMarkKeyword_1_1_1_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getAnnotatedN4MemberDeclarationRule());
							}
							setWithLastConsumed($current, "declaredOptional", true, "?");
						}
					)
				)?
				otherlv_11=LeftParenthesis
				{
					newLeafNode(otherlv_11, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getLeftParenthesisKeyword_1_1_2());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getFparFormalParameterParserRuleCall_1_1_3_0());
						}
						lv_fpar_12_0=ruleFormalParameter
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedN4MemberDeclarationRule());
							}
							set(
								$current,
								"fpar",
								lv_fpar_12_0,
								"org.eclipse.n4js.N4JS.FormalParameter");
							afterParserOrEnumRuleCall();
						}
					)
				)
				otherlv_13=RightParenthesis
				{
					newLeafNode(otherlv_13, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getRightParenthesisKeyword_1_1_4());
				}
				(
					((
						(
						)
						LeftCurlyBracket
					)
					)=>
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getBodyBlockParserRuleCall_1_1_5_0());
						}
						lv_body_14_0=ruleBlock
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedN4MemberDeclarationRule());
							}
							set(
								$current,
								"body",
								lv_body_14_0,
								"org.eclipse.n4js.N4JS.Block");
							afterParserOrEnumRuleCall();
						}
					)
				)?
				(
					otherlv_15=Semicolon
					{
						newLeafNode(otherlv_15, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSemicolonKeyword_1_1_6());
					}
				)?
			)
			    |
			(
				(
					((
						(
						)
						(
							(
								ruleN4Modifier
							)
						)*
						(
							ruleTypeVariables[null]
						)?
						(
							ruleBogusTypeRefFragment[null]
						)?
						(
							(
								(
									(
										Asterisk
									)
								)
								(
									(
										ruleLiteralOrComputedPropertyName
									)
								)
								LeftParenthesis
							)
							    |
							(
								ruleAsyncNoTrailingLineBreak[null]
								(
									(
										ruleLiteralOrComputedPropertyName
									)
								)
								LeftParenthesis
							)
						)
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAnnotatedN4MemberDeclarationAccess().getN4MethodDeclarationAnnotationListAction_1_2_0_0_0(),
									$current);
							}
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredModifiersN4ModifierEnumRuleCall_1_2_0_0_1_0());
								}
								lv_declaredModifiers_17_0=ruleN4Modifier
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedN4MemberDeclarationRule());
									}
									add(
										$current,
										"declaredModifiers",
										lv_declaredModifiers_17_0,
										"org.eclipse.n4js.N4JS.N4Modifier");
									afterParserOrEnumRuleCall();
								}
							)
						)*
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getAnnotatedN4MemberDeclarationRule());
								}
								newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getTypeVariablesParserRuleCall_1_2_0_0_2());
							}
							this_TypeVariables_18=ruleTypeVariables[$current]
							{
								$current = $this_TypeVariables_18.current;
								afterParserOrEnumRuleCall();
							}
						)?
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getAnnotatedN4MemberDeclarationRule());
								}
								newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getBogusTypeRefFragmentParserRuleCall_1_2_0_0_3());
							}
							this_BogusTypeRefFragment_19=ruleBogusTypeRefFragment[$current]
							{
								$current = $this_BogusTypeRefFragment_19.current;
								afterParserOrEnumRuleCall();
							}
						)?
						(
							(
								(
									(
										lv_generator_20_0=Asterisk
										{
											newLeafNode(lv_generator_20_0, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getGeneratorAsteriskKeyword_1_2_0_0_4_0_0_0());
										}
										{
											if ($current==null) {
												$current = createModelElement(grammarAccess.getAnnotatedN4MemberDeclarationRule());
											}
											setWithLastConsumed($current, "generator", true, "*");
										}
									)
								)
								(
									(
										{
											newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_0_1_0());
										}
										lv_declaredName_21_0=ruleLiteralOrComputedPropertyName
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getAnnotatedN4MemberDeclarationRule());
											}
											set(
												$current,
												"declaredName",
												lv_declaredName_21_0,
												"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
											afterParserOrEnumRuleCall();
										}
									)
								)
								(
									(LeftParenthesis)=>
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getAnnotatedN4MemberDeclarationRule());
										}
										newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getMethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_0_2());
									}
									this_MethodParamsReturnAndBody_22=norm1_MethodParamsReturnAndBody[$current]
									{
										$current = $this_MethodParamsReturnAndBody_22.current;
										afterParserOrEnumRuleCall();
									}
								)
							)
							    |
							(
								{
									if ($current==null) {
										$current = createModelElement(grammarAccess.getAnnotatedN4MemberDeclarationRule());
									}
									newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getAsyncNoTrailingLineBreakParserRuleCall_1_2_0_0_4_1_0());
								}
								this_AsyncNoTrailingLineBreak_23=ruleAsyncNoTrailingLineBreak[$current]
								{
									$current = $this_AsyncNoTrailingLineBreak_23.current;
									afterParserOrEnumRuleCall();
								}
								(
									(
										{
											newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_1_1_0());
										}
										lv_declaredName_24_0=ruleLiteralOrComputedPropertyName
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getAnnotatedN4MemberDeclarationRule());
											}
											set(
												$current,
												"declaredName",
												lv_declaredName_24_0,
												"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
											afterParserOrEnumRuleCall();
										}
									)
								)
								(
									(LeftParenthesis)=>
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getAnnotatedN4MemberDeclarationRule());
										}
										newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getMethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_1_2());
									}
									this_MethodParamsReturnAndBody_25=ruleMethodParamsReturnAndBody[$current]
									{
										$current = $this_MethodParamsReturnAndBody_25.current;
										afterParserOrEnumRuleCall();
									}
								)
							)
						)
					)
				)
				(
					otherlv_26=Semicolon
					{
						newLeafNode(otherlv_26, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSemicolonKeyword_1_2_1());
					}
				)?
			)
			    |
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getAnnotatedN4MemberDeclarationAccess().getN4FieldDeclarationAnnotationListAction_1_3_0(),
							$current);
					}
				)
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAnnotatedN4MemberDeclarationRule());
					}
					newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getFieldDeclarationImplParserRuleCall_1_3_1());
				}
				this_FieldDeclarationImpl_28=ruleFieldDeclarationImpl[$current]
				{
					$current = $this_FieldDeclarationImpl_28.current;
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule AnnotatedN4MemberDeclaration
norm1_AnnotatedN4MemberDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getN4MemberAnnotationListParserRuleCall_0());
		}
		this_N4MemberAnnotationList_0=ruleN4MemberAnnotationList
		{
			$current = $this_N4MemberAnnotationList_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					((
						(
						)
						(
							(
								ruleN4Modifier
							)
						)*
						norm1_GetterHeader[null]
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAnnotatedN4MemberDeclarationAccess().getN4GetterDeclarationAnnotationListAction_1_0_0_0_0(),
									$current);
							}
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredModifiersN4ModifierEnumRuleCall_1_0_0_0_1_0());
								}
								lv_declaredModifiers_2_0=ruleN4Modifier
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedN4MemberDeclarationRule());
									}
									add(
										$current,
										"declaredModifiers",
										lv_declaredModifiers_2_0,
										"org.eclipse.n4js.N4JS.N4Modifier");
									afterParserOrEnumRuleCall();
								}
							)
						)*
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getAnnotatedN4MemberDeclarationRule());
							}
							newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getGetterHeaderParserRuleCall_1_0_0_0_2());
						}
						this_GetterHeader_3=norm1_GetterHeader[$current]
						{
							$current = $this_GetterHeader_3.current;
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					((
						(
						)
						LeftCurlyBracket
					)
					)=>
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getBodyBlockParserRuleCall_1_0_1_0());
						}
						lv_body_4_0=norm1_Block
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedN4MemberDeclarationRule());
							}
							set(
								$current,
								"body",
								lv_body_4_0,
								"org.eclipse.n4js.N4JS.Block");
							afterParserOrEnumRuleCall();
						}
					)
				)?
				(
					otherlv_5=Semicolon
					{
						newLeafNode(otherlv_5, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSemicolonKeyword_1_0_2());
					}
				)?
			)
			    |
			(
				(
					((
						(
						)
						(
							(
								ruleN4Modifier
							)
						)*
						Set
						(
							Break
							    |
							Case
							    |
							Catch
							    |
							Class
							    |
							Const
							    |
							Continue
							    |
							Debugger
							    |
							Default
							    |
							Delete
							    |
							Do
							    |
							Else
							    |
							Export
							    |
							Extends
							    |
							Finally
							    |
							For
							    |
							Function
							    |
							If
							    |
							Import
							    |
							In
							    |
							Instanceof
							    |
							New
							    |
							Return
							    |
							Super
							    |
							Switch
							    |
							This_1
							    |
							Throw
							    |
							Try
							    |
							Typeof
							    |
							Var
							    |
							Void
							    |
							While
							    |
							With
							    |
							Yield
							    |
							Null
							    |
							True
							    |
							False
							    |
							Enum
							    |
							Get
							    |
							Set
							    |
							Let
							    |
							Project
							    |
							External
							    |
							Abstract
							    |
							Static
							    |
							As
							    |
							From
							    |
							Constructor
							    |
							Of
							    |
							Target
							    |
							Type
							    |
							Union
							    |
							Intersection
							    |
							This
							    |
							Promisify
							    |
							Await
							    |
							Async
							    |
							Implements
							    |
							Interface
							    |
							Private
							    |
							Protected
							    |
							Public
							    |
							Out
							    |
							LeftSquareBracket
							    |
							RULE_IDENTIFIER
							    |
							RULE_STRING
							    |
							RULE_DOUBLE
							    |
							RULE_INT
							    |
							RULE_OCTAL_INT
							    |
							RULE_HEX_INT
							    |
							RULE_SCIENTIFIC_INT
						)
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAnnotatedN4MemberDeclarationAccess().getN4SetterDeclarationAnnotationListAction_1_1_0_0_0(),
									$current);
							}
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredModifiersN4ModifierEnumRuleCall_1_1_0_0_1_0());
								}
								lv_declaredModifiers_7_0=ruleN4Modifier
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedN4MemberDeclarationRule());
									}
									add(
										$current,
										"declaredModifiers",
										lv_declaredModifiers_7_0,
										"org.eclipse.n4js.N4JS.N4Modifier");
									afterParserOrEnumRuleCall();
								}
							)
						)*
						otherlv_8=Set
						{
							newLeafNode(otherlv_8, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSetKeyword_1_1_0_0_2());
						}
						(
							(Break | Case | Catch | Class | Const | Continue | Debugger | Default | Delete | Do | Else | Export | Extends | Finally | For | Function | If | Import | In | Instanceof | New | Return | Super | Switch | This_1 | Throw | Try | Typeof | Var | Void | While | With | Yield | Null | True | False | Enum | Get | Set | Let | Project | External | Abstract | Static | As | From | Constructor | Of | Target | Type | Union | Intersection | This | Promisify | Await | Async | Implements | Interface | Private | Protected | Public | Out | LeftSquareBracket | RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_1_0_0_3_0());
								}
								lv_declaredName_9_0=norm1_LiteralOrComputedPropertyName
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedN4MemberDeclarationRule());
									}
									set(
										$current,
										"declaredName",
										lv_declaredName_9_0,
										"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
									afterParserOrEnumRuleCall();
								}
							)
						)
					)
				)
				(
					(
						lv_declaredOptional_10_0=QuestionMark
						{
							newLeafNode(lv_declaredOptional_10_0, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredOptionalQuestionMarkKeyword_1_1_1_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getAnnotatedN4MemberDeclarationRule());
							}
							setWithLastConsumed($current, "declaredOptional", true, "?");
						}
					)
				)?
				otherlv_11=LeftParenthesis
				{
					newLeafNode(otherlv_11, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getLeftParenthesisKeyword_1_1_2());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getFparFormalParameterParserRuleCall_1_1_3_0());
						}
						lv_fpar_12_0=norm1_FormalParameter
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedN4MemberDeclarationRule());
							}
							set(
								$current,
								"fpar",
								lv_fpar_12_0,
								"org.eclipse.n4js.N4JS.FormalParameter");
							afterParserOrEnumRuleCall();
						}
					)
				)
				otherlv_13=RightParenthesis
				{
					newLeafNode(otherlv_13, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getRightParenthesisKeyword_1_1_4());
				}
				(
					((
						(
						)
						LeftCurlyBracket
					)
					)=>
					(
						{
							newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getBodyBlockParserRuleCall_1_1_5_0());
						}
						lv_body_14_0=norm1_Block
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getAnnotatedN4MemberDeclarationRule());
							}
							set(
								$current,
								"body",
								lv_body_14_0,
								"org.eclipse.n4js.N4JS.Block");
							afterParserOrEnumRuleCall();
						}
					)
				)?
				(
					otherlv_15=Semicolon
					{
						newLeafNode(otherlv_15, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSemicolonKeyword_1_1_6());
					}
				)?
			)
			    |
			(
				(
					((
						(
						)
						(
							(
								ruleN4Modifier
							)
						)*
						(
							ruleTypeVariables[null]
						)?
						(
							ruleBogusTypeRefFragment[null]
						)?
						(
							(
								(
									(
										Asterisk
									)
								)
								(
									(
										norm1_LiteralOrComputedPropertyName
									)
								)
								LeftParenthesis
							)
							    |
							(
								ruleAsyncNoTrailingLineBreak[null]
								(
									(
										norm1_LiteralOrComputedPropertyName
									)
								)
								LeftParenthesis
							)
						)
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getAnnotatedN4MemberDeclarationAccess().getN4MethodDeclarationAnnotationListAction_1_2_0_0_0(),
									$current);
							}
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredModifiersN4ModifierEnumRuleCall_1_2_0_0_1_0());
								}
								lv_declaredModifiers_17_0=ruleN4Modifier
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getAnnotatedN4MemberDeclarationRule());
									}
									add(
										$current,
										"declaredModifiers",
										lv_declaredModifiers_17_0,
										"org.eclipse.n4js.N4JS.N4Modifier");
									afterParserOrEnumRuleCall();
								}
							)
						)*
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getAnnotatedN4MemberDeclarationRule());
								}
								newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getTypeVariablesParserRuleCall_1_2_0_0_2());
							}
							this_TypeVariables_18=ruleTypeVariables[$current]
							{
								$current = $this_TypeVariables_18.current;
								afterParserOrEnumRuleCall();
							}
						)?
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getAnnotatedN4MemberDeclarationRule());
								}
								newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getBogusTypeRefFragmentParserRuleCall_1_2_0_0_3());
							}
							this_BogusTypeRefFragment_19=ruleBogusTypeRefFragment[$current]
							{
								$current = $this_BogusTypeRefFragment_19.current;
								afterParserOrEnumRuleCall();
							}
						)?
						(
							(
								(
									(
										lv_generator_20_0=Asterisk
										{
											newLeafNode(lv_generator_20_0, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getGeneratorAsteriskKeyword_1_2_0_0_4_0_0_0());
										}
										{
											if ($current==null) {
												$current = createModelElement(grammarAccess.getAnnotatedN4MemberDeclarationRule());
											}
											setWithLastConsumed($current, "generator", true, "*");
										}
									)
								)
								(
									(
										{
											newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_0_1_0());
										}
										lv_declaredName_21_0=norm1_LiteralOrComputedPropertyName
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getAnnotatedN4MemberDeclarationRule());
											}
											set(
												$current,
												"declaredName",
												lv_declaredName_21_0,
												"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
											afterParserOrEnumRuleCall();
										}
									)
								)
								(
									(LeftParenthesis)=>
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getAnnotatedN4MemberDeclarationRule());
										}
										newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getMethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_0_2());
									}
									this_MethodParamsReturnAndBody_22=norm1_MethodParamsReturnAndBody[$current]
									{
										$current = $this_MethodParamsReturnAndBody_22.current;
										afterParserOrEnumRuleCall();
									}
								)
							)
							    |
							(
								{
									if ($current==null) {
										$current = createModelElement(grammarAccess.getAnnotatedN4MemberDeclarationRule());
									}
									newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getAsyncNoTrailingLineBreakParserRuleCall_1_2_0_0_4_1_0());
								}
								this_AsyncNoTrailingLineBreak_23=ruleAsyncNoTrailingLineBreak[$current]
								{
									$current = $this_AsyncNoTrailingLineBreak_23.current;
									afterParserOrEnumRuleCall();
								}
								(
									(
										{
											newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_1_2_0_0_4_1_1_0());
										}
										lv_declaredName_24_0=norm1_LiteralOrComputedPropertyName
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getAnnotatedN4MemberDeclarationRule());
											}
											set(
												$current,
												"declaredName",
												lv_declaredName_24_0,
												"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
											afterParserOrEnumRuleCall();
										}
									)
								)
								(
									(LeftParenthesis)=>
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getAnnotatedN4MemberDeclarationRule());
										}
										newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getMethodParamsReturnAndBodyParserRuleCall_1_2_0_0_4_1_2());
									}
									this_MethodParamsReturnAndBody_25=ruleMethodParamsReturnAndBody[$current]
									{
										$current = $this_MethodParamsReturnAndBody_25.current;
										afterParserOrEnumRuleCall();
									}
								)
							)
						)
					)
				)
				(
					otherlv_26=Semicolon
					{
						newLeafNode(otherlv_26, grammarAccess.getAnnotatedN4MemberDeclarationAccess().getSemicolonKeyword_1_2_1());
					}
				)?
			)
			    |
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getAnnotatedN4MemberDeclarationAccess().getN4FieldDeclarationAnnotationListAction_1_3_0(),
							$current);
					}
				)
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAnnotatedN4MemberDeclarationRule());
					}
					newCompositeNode(grammarAccess.getAnnotatedN4MemberDeclarationAccess().getFieldDeclarationImplParserRuleCall_1_3_1());
				}
				this_FieldDeclarationImpl_28=norm1_FieldDeclarationImpl[$current]
				{
					$current = $this_FieldDeclarationImpl_28.current;
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule FieldDeclarationImpl
ruleFieldDeclarationImpl[EObject in_current]  returns [EObject current=in_current]
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
					newCompositeNode(grammarAccess.getFieldDeclarationImplAccess().getDeclaredModifiersN4ModifierEnumRuleCall_0_0());
				}
				lv_declaredModifiers_0_0=ruleN4Modifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getFieldDeclarationImplRule());
					}
					add(
						$current,
						"declaredModifiers",
						lv_declaredModifiers_0_0,
						"org.eclipse.n4js.N4JS.N4Modifier");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getFieldDeclarationImplRule());
				}
				newCompositeNode(grammarAccess.getFieldDeclarationImplAccess().getBogusTypeRefFragmentParserRuleCall_1());
			}
			this_BogusTypeRefFragment_1=ruleBogusTypeRefFragment[$current]
			{
				$current = $this_BogusTypeRefFragment_1.current;
				afterParserOrEnumRuleCall();
			}
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getFieldDeclarationImplAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0());
				}
				lv_declaredName_2_0=ruleLiteralOrComputedPropertyName
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getFieldDeclarationImplRule());
					}
					set(
						$current,
						"declaredName",
						lv_declaredName_2_0,
						"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				lv_declaredOptional_3_0=QuestionMark
				{
					newLeafNode(lv_declaredOptional_3_0, grammarAccess.getFieldDeclarationImplAccess().getDeclaredOptionalQuestionMarkKeyword_3_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getFieldDeclarationImplRule());
					}
					setWithLastConsumed($current, "declaredOptional", true, "?");
				}
			)
		)?
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getFieldDeclarationImplRule());
				}
				newCompositeNode(grammarAccess.getFieldDeclarationImplAccess().getColonSepDeclaredTypeRefParserRuleCall_4());
			}
			this_ColonSepDeclaredTypeRef_4=ruleColonSepDeclaredTypeRef[$current]
			{
				$current = $this_ColonSepDeclaredTypeRef_4.current;
				afterParserOrEnumRuleCall();
			}
		)?
		(
			otherlv_5=EqualsSign
			{
				newLeafNode(otherlv_5, grammarAccess.getFieldDeclarationImplAccess().getEqualsSignKeyword_5_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getFieldDeclarationImplAccess().getExpressionExpressionParserRuleCall_5_1_0());
					}
					lv_expression_6_0=norm1_Expression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getFieldDeclarationImplRule());
						}
						set(
							$current,
							"expression",
							lv_expression_6_0,
							"org.eclipse.n4js.N4JS.Expression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		{
			newCompositeNode(grammarAccess.getFieldDeclarationImplAccess().getSemiParserRuleCall_6());
		}
		ruleSemi
		{
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule FieldDeclarationImpl
norm1_FieldDeclarationImpl[EObject in_current]  returns [EObject current=in_current]
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
					newCompositeNode(grammarAccess.getFieldDeclarationImplAccess().getDeclaredModifiersN4ModifierEnumRuleCall_0_0());
				}
				lv_declaredModifiers_0_0=ruleN4Modifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getFieldDeclarationImplRule());
					}
					add(
						$current,
						"declaredModifiers",
						lv_declaredModifiers_0_0,
						"org.eclipse.n4js.N4JS.N4Modifier");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getFieldDeclarationImplRule());
				}
				newCompositeNode(grammarAccess.getFieldDeclarationImplAccess().getBogusTypeRefFragmentParserRuleCall_1());
			}
			this_BogusTypeRefFragment_1=ruleBogusTypeRefFragment[$current]
			{
				$current = $this_BogusTypeRefFragment_1.current;
				afterParserOrEnumRuleCall();
			}
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getFieldDeclarationImplAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0());
				}
				lv_declaredName_2_0=norm1_LiteralOrComputedPropertyName
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getFieldDeclarationImplRule());
					}
					set(
						$current,
						"declaredName",
						lv_declaredName_2_0,
						"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				lv_declaredOptional_3_0=QuestionMark
				{
					newLeafNode(lv_declaredOptional_3_0, grammarAccess.getFieldDeclarationImplAccess().getDeclaredOptionalQuestionMarkKeyword_3_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getFieldDeclarationImplRule());
					}
					setWithLastConsumed($current, "declaredOptional", true, "?");
				}
			)
		)?
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getFieldDeclarationImplRule());
				}
				newCompositeNode(grammarAccess.getFieldDeclarationImplAccess().getColonSepDeclaredTypeRefParserRuleCall_4());
			}
			this_ColonSepDeclaredTypeRef_4=ruleColonSepDeclaredTypeRef[$current]
			{
				$current = $this_ColonSepDeclaredTypeRef_4.current;
				afterParserOrEnumRuleCall();
			}
		)?
		(
			otherlv_5=EqualsSign
			{
				newLeafNode(otherlv_5, grammarAccess.getFieldDeclarationImplAccess().getEqualsSignKeyword_5_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getFieldDeclarationImplAccess().getExpressionExpressionParserRuleCall_5_1_0());
					}
					lv_expression_6_0=norm3_Expression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getFieldDeclarationImplRule());
						}
						set(
							$current,
							"expression",
							lv_expression_6_0,
							"org.eclipse.n4js.N4JS.Expression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		{
			newCompositeNode(grammarAccess.getFieldDeclarationImplAccess().getSemiParserRuleCall_6());
		}
		ruleSemi
		{
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleN4FieldDeclaration
entryRuleN4FieldDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getN4FieldDeclarationRule()); }
	iv_ruleN4FieldDeclaration=ruleN4FieldDeclaration
	{ $current=$iv_ruleN4FieldDeclaration.current; }
	EOF;

// Rule N4FieldDeclaration
ruleN4FieldDeclaration returns [EObject current=null]
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
					grammarAccess.getN4FieldDeclarationAccess().getN4FieldDeclarationAction_0(),
					$current);
			}
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getN4FieldDeclarationRule());
			}
			newCompositeNode(grammarAccess.getN4FieldDeclarationAccess().getFieldDeclarationImplParserRuleCall_1());
		}
		this_FieldDeclarationImpl_1=ruleFieldDeclarationImpl[$current]
		{
			$current = $this_FieldDeclarationImpl_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule N4FieldDeclaration
norm1_N4FieldDeclaration returns [EObject current=null]
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
					grammarAccess.getN4FieldDeclarationAccess().getN4FieldDeclarationAction_0(),
					$current);
			}
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getN4FieldDeclarationRule());
			}
			newCompositeNode(grammarAccess.getN4FieldDeclarationAccess().getFieldDeclarationImplParserRuleCall_1());
		}
		this_FieldDeclarationImpl_1=norm1_FieldDeclarationImpl[$current]
		{
			$current = $this_FieldDeclarationImpl_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleN4MethodDeclaration
entryRuleN4MethodDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getN4MethodDeclarationRule()); }
	iv_ruleN4MethodDeclaration=ruleN4MethodDeclaration
	{ $current=$iv_ruleN4MethodDeclaration.current; }
	EOF;

// Rule N4MethodDeclaration
ruleN4MethodDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				(
					(
						ruleN4Modifier
					)
				)*
				(
					ruleTypeVariables[null]
				)?
				(
					ruleBogusTypeRefFragment[null]
				)?
				(
					(
						(
							(
								Asterisk
							)
						)
						(
							(
								ruleLiteralOrComputedPropertyName
							)
						)
						LeftParenthesis
					)
					    |
					(
						ruleAsyncNoTrailingLineBreak[null]
						(
							(
								ruleLiteralOrComputedPropertyName
							)
						)
						LeftParenthesis
					)
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getN4MethodDeclarationAccess().getN4MethodDeclarationAction_0_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getN4MethodDeclarationAccess().getDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0());
						}
						lv_declaredModifiers_1_0=ruleN4Modifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getN4MethodDeclarationRule());
							}
							add(
								$current,
								"declaredModifiers",
								lv_declaredModifiers_1_0,
								"org.eclipse.n4js.N4JS.N4Modifier");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getN4MethodDeclarationRule());
						}
						newCompositeNode(grammarAccess.getN4MethodDeclarationAccess().getTypeVariablesParserRuleCall_0_0_2());
					}
					this_TypeVariables_2=ruleTypeVariables[$current]
					{
						$current = $this_TypeVariables_2.current;
						afterParserOrEnumRuleCall();
					}
				)?
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getN4MethodDeclarationRule());
						}
						newCompositeNode(grammarAccess.getN4MethodDeclarationAccess().getBogusTypeRefFragmentParserRuleCall_0_0_3());
					}
					this_BogusTypeRefFragment_3=ruleBogusTypeRefFragment[$current]
					{
						$current = $this_BogusTypeRefFragment_3.current;
						afterParserOrEnumRuleCall();
					}
				)?
				(
					(
						(
							(
								lv_generator_4_0=Asterisk
								{
									newLeafNode(lv_generator_4_0, grammarAccess.getN4MethodDeclarationAccess().getGeneratorAsteriskKeyword_0_0_4_0_0_0());
								}
								{
									if ($current==null) {
										$current = createModelElement(grammarAccess.getN4MethodDeclarationRule());
									}
									setWithLastConsumed($current, "generator", true, "*");
								}
							)
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getN4MethodDeclarationAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_0_1_0());
								}
								lv_declaredName_5_0=ruleLiteralOrComputedPropertyName
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getN4MethodDeclarationRule());
									}
									set(
										$current,
										"declaredName",
										lv_declaredName_5_0,
										"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
									afterParserOrEnumRuleCall();
								}
							)
						)
						(
							(LeftParenthesis)=>
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getN4MethodDeclarationRule());
								}
								newCompositeNode(grammarAccess.getN4MethodDeclarationAccess().getMethodParamsReturnAndBodyParserRuleCall_0_0_4_0_2());
							}
							this_MethodParamsReturnAndBody_6=norm1_MethodParamsReturnAndBody[$current]
							{
								$current = $this_MethodParamsReturnAndBody_6.current;
								afterParserOrEnumRuleCall();
							}
						)
					)
					    |
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getN4MethodDeclarationRule());
							}
							newCompositeNode(grammarAccess.getN4MethodDeclarationAccess().getAsyncNoTrailingLineBreakParserRuleCall_0_0_4_1_0());
						}
						this_AsyncNoTrailingLineBreak_7=ruleAsyncNoTrailingLineBreak[$current]
						{
							$current = $this_AsyncNoTrailingLineBreak_7.current;
							afterParserOrEnumRuleCall();
						}
						(
							(
								{
									newCompositeNode(grammarAccess.getN4MethodDeclarationAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_1_1_0());
								}
								lv_declaredName_8_0=ruleLiteralOrComputedPropertyName
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getN4MethodDeclarationRule());
									}
									set(
										$current,
										"declaredName",
										lv_declaredName_8_0,
										"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
									afterParserOrEnumRuleCall();
								}
							)
						)
						(
							(LeftParenthesis)=>
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getN4MethodDeclarationRule());
								}
								newCompositeNode(grammarAccess.getN4MethodDeclarationAccess().getMethodParamsReturnAndBodyParserRuleCall_0_0_4_1_2());
							}
							this_MethodParamsReturnAndBody_9=ruleMethodParamsReturnAndBody[$current]
							{
								$current = $this_MethodParamsReturnAndBody_9.current;
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
		)
		(
			otherlv_10=Semicolon
			{
				newLeafNode(otherlv_10, grammarAccess.getN4MethodDeclarationAccess().getSemicolonKeyword_1());
			}
		)?
	)
;


// Rule N4MethodDeclaration
norm1_N4MethodDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				(
					(
						ruleN4Modifier
					)
				)*
				(
					ruleTypeVariables[null]
				)?
				(
					ruleBogusTypeRefFragment[null]
				)?
				(
					(
						(
							(
								Asterisk
							)
						)
						(
							(
								norm1_LiteralOrComputedPropertyName
							)
						)
						LeftParenthesis
					)
					    |
					(
						ruleAsyncNoTrailingLineBreak[null]
						(
							(
								norm1_LiteralOrComputedPropertyName
							)
						)
						LeftParenthesis
					)
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getN4MethodDeclarationAccess().getN4MethodDeclarationAction_0_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getN4MethodDeclarationAccess().getDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0());
						}
						lv_declaredModifiers_1_0=ruleN4Modifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getN4MethodDeclarationRule());
							}
							add(
								$current,
								"declaredModifiers",
								lv_declaredModifiers_1_0,
								"org.eclipse.n4js.N4JS.N4Modifier");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getN4MethodDeclarationRule());
						}
						newCompositeNode(grammarAccess.getN4MethodDeclarationAccess().getTypeVariablesParserRuleCall_0_0_2());
					}
					this_TypeVariables_2=ruleTypeVariables[$current]
					{
						$current = $this_TypeVariables_2.current;
						afterParserOrEnumRuleCall();
					}
				)?
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getN4MethodDeclarationRule());
						}
						newCompositeNode(grammarAccess.getN4MethodDeclarationAccess().getBogusTypeRefFragmentParserRuleCall_0_0_3());
					}
					this_BogusTypeRefFragment_3=ruleBogusTypeRefFragment[$current]
					{
						$current = $this_BogusTypeRefFragment_3.current;
						afterParserOrEnumRuleCall();
					}
				)?
				(
					(
						(
							(
								lv_generator_4_0=Asterisk
								{
									newLeafNode(lv_generator_4_0, grammarAccess.getN4MethodDeclarationAccess().getGeneratorAsteriskKeyword_0_0_4_0_0_0());
								}
								{
									if ($current==null) {
										$current = createModelElement(grammarAccess.getN4MethodDeclarationRule());
									}
									setWithLastConsumed($current, "generator", true, "*");
								}
							)
						)
						(
							(
								{
									newCompositeNode(grammarAccess.getN4MethodDeclarationAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_0_1_0());
								}
								lv_declaredName_5_0=norm1_LiteralOrComputedPropertyName
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getN4MethodDeclarationRule());
									}
									set(
										$current,
										"declaredName",
										lv_declaredName_5_0,
										"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
									afterParserOrEnumRuleCall();
								}
							)
						)
						(
							(LeftParenthesis)=>
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getN4MethodDeclarationRule());
								}
								newCompositeNode(grammarAccess.getN4MethodDeclarationAccess().getMethodParamsReturnAndBodyParserRuleCall_0_0_4_0_2());
							}
							this_MethodParamsReturnAndBody_6=norm1_MethodParamsReturnAndBody[$current]
							{
								$current = $this_MethodParamsReturnAndBody_6.current;
								afterParserOrEnumRuleCall();
							}
						)
					)
					    |
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getN4MethodDeclarationRule());
							}
							newCompositeNode(grammarAccess.getN4MethodDeclarationAccess().getAsyncNoTrailingLineBreakParserRuleCall_0_0_4_1_0());
						}
						this_AsyncNoTrailingLineBreak_7=ruleAsyncNoTrailingLineBreak[$current]
						{
							$current = $this_AsyncNoTrailingLineBreak_7.current;
							afterParserOrEnumRuleCall();
						}
						(
							(
								{
									newCompositeNode(grammarAccess.getN4MethodDeclarationAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_4_1_1_0());
								}
								lv_declaredName_8_0=norm1_LiteralOrComputedPropertyName
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getN4MethodDeclarationRule());
									}
									set(
										$current,
										"declaredName",
										lv_declaredName_8_0,
										"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
									afterParserOrEnumRuleCall();
								}
							)
						)
						(
							(LeftParenthesis)=>
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getN4MethodDeclarationRule());
								}
								newCompositeNode(grammarAccess.getN4MethodDeclarationAccess().getMethodParamsReturnAndBodyParserRuleCall_0_0_4_1_2());
							}
							this_MethodParamsReturnAndBody_9=ruleMethodParamsReturnAndBody[$current]
							{
								$current = $this_MethodParamsReturnAndBody_9.current;
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
		)
		(
			otherlv_10=Semicolon
			{
				newLeafNode(otherlv_10, grammarAccess.getN4MethodDeclarationAccess().getSemicolonKeyword_1());
			}
		)?
	)
;

// Entry rule entryRuleN4CallableConstructorDeclaration
entryRuleN4CallableConstructorDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getN4CallableConstructorDeclarationRule()); }
	iv_ruleN4CallableConstructorDeclaration=ruleN4CallableConstructorDeclaration
	{ $current=$iv_ruleN4CallableConstructorDeclaration.current; }
	EOF;

// Rule N4CallableConstructorDeclaration
ruleN4CallableConstructorDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getN4CallableConstructorDeclarationRule());
			}
			newCompositeNode(grammarAccess.getN4CallableConstructorDeclarationAccess().getMethodParamsReturnAndBodyParserRuleCall_0());
		}
		this_MethodParamsReturnAndBody_0=ruleMethodParamsReturnAndBody[$current]
		{
			$current = $this_MethodParamsReturnAndBody_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			otherlv_1=Semicolon
			{
				newLeafNode(otherlv_1, grammarAccess.getN4CallableConstructorDeclarationAccess().getSemicolonKeyword_1());
			}
		)?
	)
;


// Rule N4CallableConstructorDeclaration
norm1_N4CallableConstructorDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getN4CallableConstructorDeclarationRule());
			}
			newCompositeNode(grammarAccess.getN4CallableConstructorDeclarationAccess().getMethodParamsReturnAndBodyParserRuleCall_0());
		}
		this_MethodParamsReturnAndBody_0=ruleMethodParamsReturnAndBody[$current]
		{
			$current = $this_MethodParamsReturnAndBody_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			otherlv_1=Semicolon
			{
				newLeafNode(otherlv_1, grammarAccess.getN4CallableConstructorDeclarationAccess().getSemicolonKeyword_1());
			}
		)?
	)
;


// Rule MethodParamsAndBody
ruleMethodParamsAndBody[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getMethodParamsAndBodyRule());
			}
			newCompositeNode(grammarAccess.getMethodParamsAndBodyAccess().getStrictFormalParametersParserRuleCall_0());
		}
		this_StrictFormalParameters_0=ruleStrictFormalParameters[$current]
		{
			$current = $this_StrictFormalParameters_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getMethodParamsAndBodyAccess().getBodyBlockParserRuleCall_1_0());
				}
				lv_body_1_0=ruleBlock
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getMethodParamsAndBodyRule());
					}
					set(
						$current,
						"body",
						lv_body_1_0,
						"org.eclipse.n4js.N4JS.Block");
					afterParserOrEnumRuleCall();
				}
			)
		)?
	)
;


// Rule MethodParamsAndBody
norm1_MethodParamsAndBody[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getMethodParamsAndBodyRule());
			}
			newCompositeNode(grammarAccess.getMethodParamsAndBodyAccess().getStrictFormalParametersParserRuleCall_0());
		}
		this_StrictFormalParameters_0=norm1_StrictFormalParameters[$current]
		{
			$current = $this_StrictFormalParameters_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getMethodParamsAndBodyAccess().getBodyBlockParserRuleCall_1_0());
				}
				lv_body_1_0=norm1_Block
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getMethodParamsAndBodyRule());
					}
					set(
						$current,
						"body",
						lv_body_1_0,
						"org.eclipse.n4js.N4JS.Block");
					afterParserOrEnumRuleCall();
				}
			)
		)?
	)
;


// Rule MethodParamsReturnAndBody
ruleMethodParamsReturnAndBody[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getMethodParamsReturnAndBodyRule());
			}
			newCompositeNode(grammarAccess.getMethodParamsReturnAndBodyAccess().getStrictFormalParametersParserRuleCall_0());
		}
		this_StrictFormalParameters_0=ruleStrictFormalParameters[$current]
		{
			$current = $this_StrictFormalParameters_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getMethodParamsReturnAndBodyRule());
				}
				newCompositeNode(grammarAccess.getMethodParamsReturnAndBodyAccess().getColonSepReturnTypeRefParserRuleCall_1());
			}
			this_ColonSepReturnTypeRef_1=ruleColonSepReturnTypeRef[$current]
			{
				$current = $this_ColonSepReturnTypeRef_1.current;
				afterParserOrEnumRuleCall();
			}
		)?
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getMethodParamsReturnAndBodyAccess().getBodyBlockParserRuleCall_2_0());
				}
				lv_body_2_0=ruleBlock
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getMethodParamsReturnAndBodyRule());
					}
					set(
						$current,
						"body",
						lv_body_2_0,
						"org.eclipse.n4js.N4JS.Block");
					afterParserOrEnumRuleCall();
				}
			)
		)?
	)
;


// Rule MethodParamsReturnAndBody
norm1_MethodParamsReturnAndBody[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getMethodParamsReturnAndBodyRule());
			}
			newCompositeNode(grammarAccess.getMethodParamsReturnAndBodyAccess().getStrictFormalParametersParserRuleCall_0());
		}
		this_StrictFormalParameters_0=norm1_StrictFormalParameters[$current]
		{
			$current = $this_StrictFormalParameters_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getMethodParamsReturnAndBodyRule());
				}
				newCompositeNode(grammarAccess.getMethodParamsReturnAndBodyAccess().getColonSepReturnTypeRefParserRuleCall_1());
			}
			this_ColonSepReturnTypeRef_1=ruleColonSepReturnTypeRef[$current]
			{
				$current = $this_ColonSepReturnTypeRef_1.current;
				afterParserOrEnumRuleCall();
			}
		)?
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getMethodParamsReturnAndBodyAccess().getBodyBlockParserRuleCall_2_0());
				}
				lv_body_2_0=norm1_Block
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getMethodParamsReturnAndBodyRule());
					}
					set(
						$current,
						"body",
						lv_body_2_0,
						"org.eclipse.n4js.N4JS.Block");
					afterParserOrEnumRuleCall();
				}
			)
		)?
	)
;

// Entry rule entryRuleN4GetterDeclaration
entryRuleN4GetterDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getN4GetterDeclarationRule()); }
	iv_ruleN4GetterDeclaration=ruleN4GetterDeclaration
	{ $current=$iv_ruleN4GetterDeclaration.current; }
	EOF;

// Rule N4GetterDeclaration
ruleN4GetterDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				(
					(
						ruleN4Modifier
					)
				)*
				ruleGetterHeader[null]
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getN4GetterDeclarationAccess().getN4GetterDeclarationAction_0_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getN4GetterDeclarationAccess().getDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0());
						}
						lv_declaredModifiers_1_0=ruleN4Modifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getN4GetterDeclarationRule());
							}
							add(
								$current,
								"declaredModifiers",
								lv_declaredModifiers_1_0,
								"org.eclipse.n4js.N4JS.N4Modifier");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getN4GetterDeclarationRule());
					}
					newCompositeNode(grammarAccess.getN4GetterDeclarationAccess().getGetterHeaderParserRuleCall_0_0_2());
				}
				this_GetterHeader_2=ruleGetterHeader[$current]
				{
					$current = $this_GetterHeader_2.current;
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getN4GetterDeclarationAccess().getBodyBlockParserRuleCall_1_0());
				}
				lv_body_3_0=ruleBlock
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getN4GetterDeclarationRule());
					}
					set(
						$current,
						"body",
						lv_body_3_0,
						"org.eclipse.n4js.N4JS.Block");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		(
			otherlv_4=Semicolon
			{
				newLeafNode(otherlv_4, grammarAccess.getN4GetterDeclarationAccess().getSemicolonKeyword_2());
			}
		)?
	)
;


// Rule N4GetterDeclaration
norm1_N4GetterDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				(
					(
						ruleN4Modifier
					)
				)*
				norm1_GetterHeader[null]
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getN4GetterDeclarationAccess().getN4GetterDeclarationAction_0_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getN4GetterDeclarationAccess().getDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0());
						}
						lv_declaredModifiers_1_0=ruleN4Modifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getN4GetterDeclarationRule());
							}
							add(
								$current,
								"declaredModifiers",
								lv_declaredModifiers_1_0,
								"org.eclipse.n4js.N4JS.N4Modifier");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getN4GetterDeclarationRule());
					}
					newCompositeNode(grammarAccess.getN4GetterDeclarationAccess().getGetterHeaderParserRuleCall_0_0_2());
				}
				this_GetterHeader_2=norm1_GetterHeader[$current]
				{
					$current = $this_GetterHeader_2.current;
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getN4GetterDeclarationAccess().getBodyBlockParserRuleCall_1_0());
				}
				lv_body_3_0=norm1_Block
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getN4GetterDeclarationRule());
					}
					set(
						$current,
						"body",
						lv_body_3_0,
						"org.eclipse.n4js.N4JS.Block");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		(
			otherlv_4=Semicolon
			{
				newLeafNode(otherlv_4, grammarAccess.getN4GetterDeclarationAccess().getSemicolonKeyword_2());
			}
		)?
	)
;


// Rule GetterHeader
ruleGetterHeader[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getGetterHeaderRule());
				}
				newCompositeNode(grammarAccess.getGetterHeaderAccess().getBogusTypeRefFragmentParserRuleCall_0());
			}
			this_BogusTypeRefFragment_0=ruleBogusTypeRefFragment[$current]
			{
				$current = $this_BogusTypeRefFragment_0.current;
				afterParserOrEnumRuleCall();
			}
		)?
		otherlv_1=Get
		{
			newLeafNode(otherlv_1, grammarAccess.getGetterHeaderAccess().getGetKeyword_1());
		}
		(
			(Break | Case | Catch | Class | Const | Continue | Debugger | Default | Delete | Do | Else | Export | Extends | Finally | For | Function | If | Import | In | Instanceof | New | Return | Super | Switch | This_1 | Throw | Try | Typeof | Var | Void | While | With | Yield | Null | True | False | Enum | Get | Set | Let | Project | External | Abstract | Static | As | From | Constructor | Of | Target | Type | Union | Intersection | This | Promisify | Await | Async | Implements | Interface | Private | Protected | Public | Out | LeftSquareBracket | RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
			(
				{
					newCompositeNode(grammarAccess.getGetterHeaderAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0());
				}
				lv_declaredName_2_0=ruleLiteralOrComputedPropertyName
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getGetterHeaderRule());
					}
					set(
						$current,
						"declaredName",
						lv_declaredName_2_0,
						"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				lv_declaredOptional_3_0=QuestionMark
				{
					newLeafNode(lv_declaredOptional_3_0, grammarAccess.getGetterHeaderAccess().getDeclaredOptionalQuestionMarkKeyword_3_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getGetterHeaderRule());
					}
					setWithLastConsumed($current, "declaredOptional", true, "?");
				}
			)
		)?
		otherlv_4=LeftParenthesis
		{
			newLeafNode(otherlv_4, grammarAccess.getGetterHeaderAccess().getLeftParenthesisKeyword_4());
		}
		otherlv_5=RightParenthesis
		{
			newLeafNode(otherlv_5, grammarAccess.getGetterHeaderAccess().getRightParenthesisKeyword_5());
		}
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getGetterHeaderRule());
				}
				newCompositeNode(grammarAccess.getGetterHeaderAccess().getColonSepDeclaredTypeRefParserRuleCall_6());
			}
			this_ColonSepDeclaredTypeRef_6=ruleColonSepDeclaredTypeRef[$current]
			{
				$current = $this_ColonSepDeclaredTypeRef_6.current;
				afterParserOrEnumRuleCall();
			}
		)?
	)
;


// Rule GetterHeader
norm1_GetterHeader[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getGetterHeaderRule());
				}
				newCompositeNode(grammarAccess.getGetterHeaderAccess().getBogusTypeRefFragmentParserRuleCall_0());
			}
			this_BogusTypeRefFragment_0=ruleBogusTypeRefFragment[$current]
			{
				$current = $this_BogusTypeRefFragment_0.current;
				afterParserOrEnumRuleCall();
			}
		)?
		otherlv_1=Get
		{
			newLeafNode(otherlv_1, grammarAccess.getGetterHeaderAccess().getGetKeyword_1());
		}
		(
			(Break | Case | Catch | Class | Const | Continue | Debugger | Default | Delete | Do | Else | Export | Extends | Finally | For | Function | If | Import | In | Instanceof | New | Return | Super | Switch | This_1 | Throw | Try | Typeof | Var | Void | While | With | Yield | Null | True | False | Enum | Get | Set | Let | Project | External | Abstract | Static | As | From | Constructor | Of | Target | Type | Union | Intersection | This | Promisify | Await | Async | Implements | Interface | Private | Protected | Public | Out | LeftSquareBracket | RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
			(
				{
					newCompositeNode(grammarAccess.getGetterHeaderAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_2_0());
				}
				lv_declaredName_2_0=norm1_LiteralOrComputedPropertyName
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getGetterHeaderRule());
					}
					set(
						$current,
						"declaredName",
						lv_declaredName_2_0,
						"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				lv_declaredOptional_3_0=QuestionMark
				{
					newLeafNode(lv_declaredOptional_3_0, grammarAccess.getGetterHeaderAccess().getDeclaredOptionalQuestionMarkKeyword_3_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getGetterHeaderRule());
					}
					setWithLastConsumed($current, "declaredOptional", true, "?");
				}
			)
		)?
		otherlv_4=LeftParenthesis
		{
			newLeafNode(otherlv_4, grammarAccess.getGetterHeaderAccess().getLeftParenthesisKeyword_4());
		}
		otherlv_5=RightParenthesis
		{
			newLeafNode(otherlv_5, grammarAccess.getGetterHeaderAccess().getRightParenthesisKeyword_5());
		}
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getGetterHeaderRule());
				}
				newCompositeNode(grammarAccess.getGetterHeaderAccess().getColonSepDeclaredTypeRefParserRuleCall_6());
			}
			this_ColonSepDeclaredTypeRef_6=ruleColonSepDeclaredTypeRef[$current]
			{
				$current = $this_ColonSepDeclaredTypeRef_6.current;
				afterParserOrEnumRuleCall();
			}
		)?
	)
;

// Entry rule entryRuleN4SetterDeclaration
entryRuleN4SetterDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getN4SetterDeclarationRule()); }
	iv_ruleN4SetterDeclaration=ruleN4SetterDeclaration
	{ $current=$iv_ruleN4SetterDeclaration.current; }
	EOF;

// Rule N4SetterDeclaration
ruleN4SetterDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				(
					(
						ruleN4Modifier
					)
				)*
				Set
				(
					Break
					    |
					Case
					    |
					Catch
					    |
					Class
					    |
					Const
					    |
					Continue
					    |
					Debugger
					    |
					Default
					    |
					Delete
					    |
					Do
					    |
					Else
					    |
					Export
					    |
					Extends
					    |
					Finally
					    |
					For
					    |
					Function
					    |
					If
					    |
					Import
					    |
					In
					    |
					Instanceof
					    |
					New
					    |
					Return
					    |
					Super
					    |
					Switch
					    |
					This_1
					    |
					Throw
					    |
					Try
					    |
					Typeof
					    |
					Var
					    |
					Void
					    |
					While
					    |
					With
					    |
					Yield
					    |
					Null
					    |
					True
					    |
					False
					    |
					Enum
					    |
					Get
					    |
					Set
					    |
					Let
					    |
					Project
					    |
					External
					    |
					Abstract
					    |
					Static
					    |
					As
					    |
					From
					    |
					Constructor
					    |
					Of
					    |
					Target
					    |
					Type
					    |
					Union
					    |
					Intersection
					    |
					This
					    |
					Promisify
					    |
					Await
					    |
					Async
					    |
					Implements
					    |
					Interface
					    |
					Private
					    |
					Protected
					    |
					Public
					    |
					Out
					    |
					LeftSquareBracket
					    |
					RULE_IDENTIFIER
					    |
					RULE_STRING
					    |
					RULE_DOUBLE
					    |
					RULE_INT
					    |
					RULE_OCTAL_INT
					    |
					RULE_HEX_INT
					    |
					RULE_SCIENTIFIC_INT
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getN4SetterDeclarationAccess().getN4SetterDeclarationAction_0_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getN4SetterDeclarationAccess().getDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0());
						}
						lv_declaredModifiers_1_0=ruleN4Modifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getN4SetterDeclarationRule());
							}
							add(
								$current,
								"declaredModifiers",
								lv_declaredModifiers_1_0,
								"org.eclipse.n4js.N4JS.N4Modifier");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				otherlv_2=Set
				{
					newLeafNode(otherlv_2, grammarAccess.getN4SetterDeclarationAccess().getSetKeyword_0_0_2());
				}
				(
					(Break | Case | Catch | Class | Const | Continue | Debugger | Default | Delete | Do | Else | Export | Extends | Finally | For | Function | If | Import | In | Instanceof | New | Return | Super | Switch | This_1 | Throw | Try | Typeof | Var | Void | While | With | Yield | Null | True | False | Enum | Get | Set | Let | Project | External | Abstract | Static | As | From | Constructor | Of | Target | Type | Union | Intersection | This | Promisify | Await | Async | Implements | Interface | Private | Protected | Public | Out | LeftSquareBracket | RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
					(
						{
							newCompositeNode(grammarAccess.getN4SetterDeclarationAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0());
						}
						lv_declaredName_3_0=ruleLiteralOrComputedPropertyName
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getN4SetterDeclarationRule());
							}
							set(
								$current,
								"declaredName",
								lv_declaredName_3_0,
								"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)
		(
			(
				lv_declaredOptional_4_0=QuestionMark
				{
					newLeafNode(lv_declaredOptional_4_0, grammarAccess.getN4SetterDeclarationAccess().getDeclaredOptionalQuestionMarkKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getN4SetterDeclarationRule());
					}
					setWithLastConsumed($current, "declaredOptional", true, "?");
				}
			)
		)?
		otherlv_5=LeftParenthesis
		{
			newLeafNode(otherlv_5, grammarAccess.getN4SetterDeclarationAccess().getLeftParenthesisKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getN4SetterDeclarationAccess().getFparFormalParameterParserRuleCall_3_0());
				}
				lv_fpar_6_0=ruleFormalParameter
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getN4SetterDeclarationRule());
					}
					set(
						$current,
						"fpar",
						lv_fpar_6_0,
						"org.eclipse.n4js.N4JS.FormalParameter");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_7=RightParenthesis
		{
			newLeafNode(otherlv_7, grammarAccess.getN4SetterDeclarationAccess().getRightParenthesisKeyword_4());
		}
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getN4SetterDeclarationAccess().getBodyBlockParserRuleCall_5_0());
				}
				lv_body_8_0=ruleBlock
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getN4SetterDeclarationRule());
					}
					set(
						$current,
						"body",
						lv_body_8_0,
						"org.eclipse.n4js.N4JS.Block");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		(
			otherlv_9=Semicolon
			{
				newLeafNode(otherlv_9, grammarAccess.getN4SetterDeclarationAccess().getSemicolonKeyword_6());
			}
		)?
	)
;


// Rule N4SetterDeclaration
norm1_N4SetterDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				(
					(
						ruleN4Modifier
					)
				)*
				Set
				(
					Break
					    |
					Case
					    |
					Catch
					    |
					Class
					    |
					Const
					    |
					Continue
					    |
					Debugger
					    |
					Default
					    |
					Delete
					    |
					Do
					    |
					Else
					    |
					Export
					    |
					Extends
					    |
					Finally
					    |
					For
					    |
					Function
					    |
					If
					    |
					Import
					    |
					In
					    |
					Instanceof
					    |
					New
					    |
					Return
					    |
					Super
					    |
					Switch
					    |
					This_1
					    |
					Throw
					    |
					Try
					    |
					Typeof
					    |
					Var
					    |
					Void
					    |
					While
					    |
					With
					    |
					Yield
					    |
					Null
					    |
					True
					    |
					False
					    |
					Enum
					    |
					Get
					    |
					Set
					    |
					Let
					    |
					Project
					    |
					External
					    |
					Abstract
					    |
					Static
					    |
					As
					    |
					From
					    |
					Constructor
					    |
					Of
					    |
					Target
					    |
					Type
					    |
					Union
					    |
					Intersection
					    |
					This
					    |
					Promisify
					    |
					Await
					    |
					Async
					    |
					Implements
					    |
					Interface
					    |
					Private
					    |
					Protected
					    |
					Public
					    |
					Out
					    |
					LeftSquareBracket
					    |
					RULE_IDENTIFIER
					    |
					RULE_STRING
					    |
					RULE_DOUBLE
					    |
					RULE_INT
					    |
					RULE_OCTAL_INT
					    |
					RULE_HEX_INT
					    |
					RULE_SCIENTIFIC_INT
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getN4SetterDeclarationAccess().getN4SetterDeclarationAction_0_0_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getN4SetterDeclarationAccess().getDeclaredModifiersN4ModifierEnumRuleCall_0_0_1_0());
						}
						lv_declaredModifiers_1_0=ruleN4Modifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getN4SetterDeclarationRule());
							}
							add(
								$current,
								"declaredModifiers",
								lv_declaredModifiers_1_0,
								"org.eclipse.n4js.N4JS.N4Modifier");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				otherlv_2=Set
				{
					newLeafNode(otherlv_2, grammarAccess.getN4SetterDeclarationAccess().getSetKeyword_0_0_2());
				}
				(
					(Break | Case | Catch | Class | Const | Continue | Debugger | Default | Delete | Do | Else | Export | Extends | Finally | For | Function | If | Import | In | Instanceof | New | Return | Super | Switch | This_1 | Throw | Try | Typeof | Var | Void | While | With | Yield | Null | True | False | Enum | Get | Set | Let | Project | External | Abstract | Static | As | From | Constructor | Of | Target | Type | Union | Intersection | This | Promisify | Await | Async | Implements | Interface | Private | Protected | Public | Out | LeftSquareBracket | RULE_IDENTIFIER | RULE_STRING | RULE_DOUBLE | RULE_INT | RULE_OCTAL_INT | RULE_HEX_INT | RULE_SCIENTIFIC_INT)=>
					(
						{
							newCompositeNode(grammarAccess.getN4SetterDeclarationAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_3_0());
						}
						lv_declaredName_3_0=norm1_LiteralOrComputedPropertyName
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getN4SetterDeclarationRule());
							}
							set(
								$current,
								"declaredName",
								lv_declaredName_3_0,
								"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)
		(
			(
				lv_declaredOptional_4_0=QuestionMark
				{
					newLeafNode(lv_declaredOptional_4_0, grammarAccess.getN4SetterDeclarationAccess().getDeclaredOptionalQuestionMarkKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getN4SetterDeclarationRule());
					}
					setWithLastConsumed($current, "declaredOptional", true, "?");
				}
			)
		)?
		otherlv_5=LeftParenthesis
		{
			newLeafNode(otherlv_5, grammarAccess.getN4SetterDeclarationAccess().getLeftParenthesisKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getN4SetterDeclarationAccess().getFparFormalParameterParserRuleCall_3_0());
				}
				lv_fpar_6_0=norm1_FormalParameter
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getN4SetterDeclarationRule());
					}
					set(
						$current,
						"fpar",
						lv_fpar_6_0,
						"org.eclipse.n4js.N4JS.FormalParameter");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_7=RightParenthesis
		{
			newLeafNode(otherlv_7, grammarAccess.getN4SetterDeclarationAccess().getRightParenthesisKeyword_4());
		}
		(
			((
				(
				)
				LeftCurlyBracket
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getN4SetterDeclarationAccess().getBodyBlockParserRuleCall_5_0());
				}
				lv_body_8_0=norm1_Block
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getN4SetterDeclarationRule());
					}
					set(
						$current,
						"body",
						lv_body_8_0,
						"org.eclipse.n4js.N4JS.Block");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		(
			otherlv_9=Semicolon
			{
				newLeafNode(otherlv_9, grammarAccess.getN4SetterDeclarationAccess().getSemicolonKeyword_6());
			}
		)?
	)
;

// Entry rule entryRuleBindingPattern
entryRuleBindingPattern returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getBindingPatternRule()); }
	iv_ruleBindingPattern=ruleBindingPattern
	{ $current=$iv_ruleBindingPattern.current; }
	EOF;

// Rule BindingPattern
ruleBindingPattern returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getBindingPatternAccess().getObjectBindingPatternParserRuleCall_0());
		}
		this_ObjectBindingPattern_0=ruleObjectBindingPattern
		{
			$current = $this_ObjectBindingPattern_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getBindingPatternAccess().getArrayBindingPatternParserRuleCall_1());
		}
		this_ArrayBindingPattern_1=ruleArrayBindingPattern
		{
			$current = $this_ArrayBindingPattern_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule BindingPattern
norm1_BindingPattern returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getBindingPatternAccess().getObjectBindingPatternParserRuleCall_0());
		}
		this_ObjectBindingPattern_0=norm1_ObjectBindingPattern
		{
			$current = $this_ObjectBindingPattern_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getBindingPatternAccess().getArrayBindingPatternParserRuleCall_1());
		}
		this_ArrayBindingPattern_1=norm1_ArrayBindingPattern
		{
			$current = $this_ArrayBindingPattern_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleObjectBindingPattern
entryRuleObjectBindingPattern returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getObjectBindingPatternRule()); }
	iv_ruleObjectBindingPattern=ruleObjectBindingPattern
	{ $current=$iv_ruleObjectBindingPattern.current; }
	EOF;

// Rule ObjectBindingPattern
ruleObjectBindingPattern returns [EObject current=null]
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
					grammarAccess.getObjectBindingPatternAccess().getObjectBindingPatternAction_0(),
					$current);
			}
		)
		otherlv_1=LeftCurlyBracket
		{
			newLeafNode(otherlv_1, grammarAccess.getObjectBindingPatternAccess().getLeftCurlyBracketKeyword_1());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getObjectBindingPatternAccess().getPropertiesBindingPropertyParserRuleCall_2_0_0());
					}
					lv_properties_2_0=ruleBindingProperty
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getObjectBindingPatternRule());
						}
						add(
							$current,
							"properties",
							lv_properties_2_0,
							"org.eclipse.n4js.N4JS.BindingProperty");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_3=Comma
				{
					newLeafNode(otherlv_3, grammarAccess.getObjectBindingPatternAccess().getCommaKeyword_2_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getObjectBindingPatternAccess().getPropertiesBindingPropertyParserRuleCall_2_1_1_0());
						}
						lv_properties_4_0=ruleBindingProperty
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getObjectBindingPatternRule());
							}
							add(
								$current,
								"properties",
								lv_properties_4_0,
								"org.eclipse.n4js.N4JS.BindingProperty");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_5=RightCurlyBracket
		{
			newLeafNode(otherlv_5, grammarAccess.getObjectBindingPatternAccess().getRightCurlyBracketKeyword_3());
		}
	)
;


// Rule ObjectBindingPattern
norm1_ObjectBindingPattern returns [EObject current=null]
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
					grammarAccess.getObjectBindingPatternAccess().getObjectBindingPatternAction_0(),
					$current);
			}
		)
		otherlv_1=LeftCurlyBracket
		{
			newLeafNode(otherlv_1, grammarAccess.getObjectBindingPatternAccess().getLeftCurlyBracketKeyword_1());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getObjectBindingPatternAccess().getPropertiesBindingPropertyParserRuleCall_2_0_0());
					}
					lv_properties_2_0=norm1_BindingProperty
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getObjectBindingPatternRule());
						}
						add(
							$current,
							"properties",
							lv_properties_2_0,
							"org.eclipse.n4js.N4JS.BindingProperty");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_3=Comma
				{
					newLeafNode(otherlv_3, grammarAccess.getObjectBindingPatternAccess().getCommaKeyword_2_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getObjectBindingPatternAccess().getPropertiesBindingPropertyParserRuleCall_2_1_1_0());
						}
						lv_properties_4_0=norm1_BindingProperty
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getObjectBindingPatternRule());
							}
							add(
								$current,
								"properties",
								lv_properties_4_0,
								"org.eclipse.n4js.N4JS.BindingProperty");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_5=RightCurlyBracket
		{
			newLeafNode(otherlv_5, grammarAccess.getObjectBindingPatternAccess().getRightCurlyBracketKeyword_3());
		}
	)
;

// Entry rule entryRuleArrayBindingPattern
entryRuleArrayBindingPattern returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getArrayBindingPatternRule()); }
	iv_ruleArrayBindingPattern=ruleArrayBindingPattern
	{ $current=$iv_ruleArrayBindingPattern.current; }
	EOF;

// Rule ArrayBindingPattern
ruleArrayBindingPattern returns [EObject current=null]
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
					grammarAccess.getArrayBindingPatternAccess().getArrayBindingPatternAction_0(),
					$current);
			}
		)
		otherlv_1=LeftSquareBracket
		{
			newLeafNode(otherlv_1, grammarAccess.getArrayBindingPatternAccess().getLeftSquareBracketKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getArrayBindingPatternAccess().getElementsElisionParserRuleCall_2_0());
				}
				lv_elements_2_0=ruleElision
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getArrayBindingPatternRule());
					}
					add(
						$current,
						"elements",
						lv_elements_2_0,
						"org.eclipse.n4js.N4JS.Elision");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getArrayBindingPatternAccess().getElementsBindingRestElementParserRuleCall_3_0_0());
					}
					lv_elements_3_0=ruleBindingRestElement
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getArrayBindingPatternRule());
						}
						add(
							$current,
							"elements",
							lv_elements_3_0,
							"org.eclipse.n4js.N4JS.BindingRestElement");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_4=Comma
				{
					newLeafNode(otherlv_4, grammarAccess.getArrayBindingPatternAccess().getCommaKeyword_3_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getArrayBindingPatternAccess().getElementsElisionParserRuleCall_3_1_1_0());
						}
						lv_elements_5_0=ruleElision
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getArrayBindingPatternRule());
							}
							add(
								$current,
								"elements",
								lv_elements_5_0,
								"org.eclipse.n4js.N4JS.Elision");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				(
					(
						{
							newCompositeNode(grammarAccess.getArrayBindingPatternAccess().getElementsBindingRestElementParserRuleCall_3_1_2_0());
						}
						lv_elements_6_0=ruleBindingRestElement
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getArrayBindingPatternRule());
							}
							add(
								$current,
								"elements",
								lv_elements_6_0,
								"org.eclipse.n4js.N4JS.BindingRestElement");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
			(
				otherlv_7=Comma
				{
					newLeafNode(otherlv_7, grammarAccess.getArrayBindingPatternAccess().getCommaKeyword_3_2_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getArrayBindingPatternAccess().getElementsElisionParserRuleCall_3_2_1_0());
						}
						lv_elements_8_0=ruleElision
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getArrayBindingPatternRule());
							}
							add(
								$current,
								"elements",
								lv_elements_8_0,
								"org.eclipse.n4js.N4JS.Elision");
							afterParserOrEnumRuleCall();
						}
					)
				)*
			)?
		)?
		otherlv_9=RightSquareBracket
		{
			newLeafNode(otherlv_9, grammarAccess.getArrayBindingPatternAccess().getRightSquareBracketKeyword_4());
		}
	)
;


// Rule ArrayBindingPattern
norm1_ArrayBindingPattern returns [EObject current=null]
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
					grammarAccess.getArrayBindingPatternAccess().getArrayBindingPatternAction_0(),
					$current);
			}
		)
		otherlv_1=LeftSquareBracket
		{
			newLeafNode(otherlv_1, grammarAccess.getArrayBindingPatternAccess().getLeftSquareBracketKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getArrayBindingPatternAccess().getElementsElisionParserRuleCall_2_0());
				}
				lv_elements_2_0=ruleElision
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getArrayBindingPatternRule());
					}
					add(
						$current,
						"elements",
						lv_elements_2_0,
						"org.eclipse.n4js.N4JS.Elision");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getArrayBindingPatternAccess().getElementsBindingRestElementParserRuleCall_3_0_0());
					}
					lv_elements_3_0=norm1_BindingRestElement
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getArrayBindingPatternRule());
						}
						add(
							$current,
							"elements",
							lv_elements_3_0,
							"org.eclipse.n4js.N4JS.BindingRestElement");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_4=Comma
				{
					newLeafNode(otherlv_4, grammarAccess.getArrayBindingPatternAccess().getCommaKeyword_3_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getArrayBindingPatternAccess().getElementsElisionParserRuleCall_3_1_1_0());
						}
						lv_elements_5_0=ruleElision
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getArrayBindingPatternRule());
							}
							add(
								$current,
								"elements",
								lv_elements_5_0,
								"org.eclipse.n4js.N4JS.Elision");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				(
					(
						{
							newCompositeNode(grammarAccess.getArrayBindingPatternAccess().getElementsBindingRestElementParserRuleCall_3_1_2_0());
						}
						lv_elements_6_0=norm1_BindingRestElement
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getArrayBindingPatternRule());
							}
							add(
								$current,
								"elements",
								lv_elements_6_0,
								"org.eclipse.n4js.N4JS.BindingRestElement");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
			(
				otherlv_7=Comma
				{
					newLeafNode(otherlv_7, grammarAccess.getArrayBindingPatternAccess().getCommaKeyword_3_2_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getArrayBindingPatternAccess().getElementsElisionParserRuleCall_3_2_1_0());
						}
						lv_elements_8_0=ruleElision
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getArrayBindingPatternRule());
							}
							add(
								$current,
								"elements",
								lv_elements_8_0,
								"org.eclipse.n4js.N4JS.Elision");
							afterParserOrEnumRuleCall();
						}
					)
				)*
			)?
		)?
		otherlv_9=RightSquareBracket
		{
			newLeafNode(otherlv_9, grammarAccess.getArrayBindingPatternAccess().getRightSquareBracketKeyword_4());
		}
	)
;

// Entry rule entryRuleBindingProperty
entryRuleBindingProperty returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getBindingPropertyRule()); }
	iv_ruleBindingProperty=ruleBindingProperty
	{ $current=$iv_ruleBindingProperty.current; }
	EOF;

// Rule BindingProperty
ruleBindingProperty returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				((
					(
						(
							ruleLiteralOrComputedPropertyName
						)
					)
					Colon
				)
				)=>
				(
					(
						(
							{
								newCompositeNode(grammarAccess.getBindingPropertyAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_0_0_0());
							}
							lv_declaredName_0_0=ruleLiteralOrComputedPropertyName
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getBindingPropertyRule());
								}
								set(
									$current,
									"declaredName",
									lv_declaredName_0_0,
									"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
								afterParserOrEnumRuleCall();
							}
						)
					)
					otherlv_1=Colon
					{
						newLeafNode(otherlv_1, grammarAccess.getBindingPropertyAccess().getColonKeyword_0_0_0_1());
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getBindingPropertyAccess().getValueBindingElementParserRuleCall_0_1_0());
					}
					lv_value_2_0=ruleBindingElement
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getBindingPropertyRule());
						}
						set(
							$current,
							"value",
							lv_value_2_0,
							"org.eclipse.n4js.N4JS.BindingElement");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		    |
		(
			(
				{
					newCompositeNode(grammarAccess.getBindingPropertyAccess().getValueSingleNameBindingParserRuleCall_1_0());
				}
				lv_value_3_0=ruleSingleNameBinding
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getBindingPropertyRule());
					}
					set(
						$current,
						"value",
						lv_value_3_0,
						"org.eclipse.n4js.N4JS.SingleNameBinding");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule BindingProperty
norm1_BindingProperty returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				((
					(
						(
							norm1_LiteralOrComputedPropertyName
						)
					)
					Colon
				)
				)=>
				(
					(
						(
							{
								newCompositeNode(grammarAccess.getBindingPropertyAccess().getDeclaredNameLiteralOrComputedPropertyNameParserRuleCall_0_0_0_0_0());
							}
							lv_declaredName_0_0=norm1_LiteralOrComputedPropertyName
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getBindingPropertyRule());
								}
								set(
									$current,
									"declaredName",
									lv_declaredName_0_0,
									"org.eclipse.n4js.N4JS.LiteralOrComputedPropertyName");
								afterParserOrEnumRuleCall();
							}
						)
					)
					otherlv_1=Colon
					{
						newLeafNode(otherlv_1, grammarAccess.getBindingPropertyAccess().getColonKeyword_0_0_0_1());
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getBindingPropertyAccess().getValueBindingElementParserRuleCall_0_1_0());
					}
					lv_value_2_0=norm1_BindingElement
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getBindingPropertyRule());
						}
						set(
							$current,
							"value",
							lv_value_2_0,
							"org.eclipse.n4js.N4JS.BindingElement");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		    |
		(
			(
				{
					newCompositeNode(grammarAccess.getBindingPropertyAccess().getValueSingleNameBindingParserRuleCall_1_0());
				}
				lv_value_3_0=norm1_SingleNameBinding
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getBindingPropertyRule());
					}
					set(
						$current,
						"value",
						lv_value_3_0,
						"org.eclipse.n4js.N4JS.SingleNameBinding");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleSingleNameBinding
entryRuleSingleNameBinding returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getSingleNameBindingRule()); }
	iv_ruleSingleNameBinding=ruleSingleNameBinding
	{ $current=$iv_ruleSingleNameBinding.current; }
	EOF;

// Rule SingleNameBinding
ruleSingleNameBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getSingleNameBindingAccess().getVarDeclVariableDeclarationParserRuleCall_0());
			}
			lv_varDecl_0_0=norm1_VariableDeclaration
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getSingleNameBindingRule());
				}
				set(
					$current,
					"varDecl",
					lv_varDecl_0_0,
					"org.eclipse.n4js.N4JS.VariableDeclaration");
				afterParserOrEnumRuleCall();
			}
		)
	)
;


// Rule SingleNameBinding
norm1_SingleNameBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getSingleNameBindingAccess().getVarDeclVariableDeclarationParserRuleCall_0());
			}
			lv_varDecl_0_0=norm3_VariableDeclaration
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getSingleNameBindingRule());
				}
				set(
					$current,
					"varDecl",
					lv_varDecl_0_0,
					"org.eclipse.n4js.N4JS.VariableDeclaration");
				afterParserOrEnumRuleCall();
			}
		)
	)
;


// Rule SingleNameBinding
norm2_SingleNameBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getSingleNameBindingAccess().getVarDeclVariableDeclarationParserRuleCall_0());
			}
			lv_varDecl_0_0=norm5_VariableDeclaration
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getSingleNameBindingRule());
				}
				set(
					$current,
					"varDecl",
					lv_varDecl_0_0,
					"org.eclipse.n4js.N4JS.VariableDeclaration");
				afterParserOrEnumRuleCall();
			}
		)
	)
;


// Rule SingleNameBinding
norm3_SingleNameBinding returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getSingleNameBindingAccess().getVarDeclVariableDeclarationParserRuleCall_0());
			}
			lv_varDecl_0_0=norm7_VariableDeclaration
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getSingleNameBindingRule());
				}
				set(
					$current,
					"varDecl",
					lv_varDecl_0_0,
					"org.eclipse.n4js.N4JS.VariableDeclaration");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleBindingElement
entryRuleBindingElement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getBindingElementRule()); }
	iv_ruleBindingElement=ruleBindingElement
	{ $current=$iv_ruleBindingElement.current; }
	EOF;

// Rule BindingElement
ruleBindingElement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	{
		if ($current==null) {
			$current = createModelElement(grammarAccess.getBindingElementRule());
		}
		newCompositeNode(grammarAccess.getBindingElementAccess().getBindingElementImplParserRuleCall());
	}
	this_BindingElementImpl_0=ruleBindingElementImpl[$current]
	{
		$current = $this_BindingElementImpl_0.current;
		afterParserOrEnumRuleCall();
	}
;


// Rule BindingElement
norm1_BindingElement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	{
		if ($current==null) {
			$current = createModelElement(grammarAccess.getBindingElementRule());
		}
		newCompositeNode(grammarAccess.getBindingElementAccess().getBindingElementImplParserRuleCall());
	}
	this_BindingElementImpl_0=norm1_BindingElementImpl[$current]
	{
		$current = $this_BindingElementImpl_0.current;
		afterParserOrEnumRuleCall();
	}
;

// Entry rule entryRuleBindingRestElement
entryRuleBindingRestElement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getBindingRestElementRule()); }
	iv_ruleBindingRestElement=ruleBindingRestElement
	{ $current=$iv_ruleBindingRestElement.current; }
	EOF;

// Rule BindingRestElement
ruleBindingRestElement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_rest_0_0=FullStopFullStopFullStop
				{
					newLeafNode(lv_rest_0_0, grammarAccess.getBindingRestElementAccess().getRestFullStopFullStopFullStopKeyword_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getBindingRestElementRule());
					}
					setWithLastConsumed($current, "rest", true, "...");
				}
			)
		)?
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getBindingRestElementRule());
			}
			newCompositeNode(grammarAccess.getBindingRestElementAccess().getBindingElementImplParserRuleCall_1());
		}
		this_BindingElementImpl_1=ruleBindingElementImpl[$current]
		{
			$current = $this_BindingElementImpl_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule BindingRestElement
norm1_BindingRestElement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_rest_0_0=FullStopFullStopFullStop
				{
					newLeafNode(lv_rest_0_0, grammarAccess.getBindingRestElementAccess().getRestFullStopFullStopFullStopKeyword_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getBindingRestElementRule());
					}
					setWithLastConsumed($current, "rest", true, "...");
				}
			)
		)?
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getBindingRestElementRule());
			}
			newCompositeNode(grammarAccess.getBindingRestElementAccess().getBindingElementImplParserRuleCall_1());
		}
		this_BindingElementImpl_1=norm1_BindingElementImpl[$current]
		{
			$current = $this_BindingElementImpl_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule BindingElementImpl
ruleBindingElementImpl[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				((
					(
						ruleBindingPattern
					)
				)
				)=>
				(
					(
						{
							newCompositeNode(grammarAccess.getBindingElementImplAccess().getNestedPatternBindingPatternParserRuleCall_0_0_0_0());
						}
						lv_nestedPattern_0_0=ruleBindingPattern
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getBindingElementImplRule());
							}
							set(
								$current,
								"nestedPattern",
								lv_nestedPattern_0_0,
								"org.eclipse.n4js.N4JS.BindingPattern");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			(
				otherlv_1=EqualsSign
				{
					newLeafNode(otherlv_1, grammarAccess.getBindingElementImplAccess().getEqualsSignKeyword_0_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getBindingElementImplAccess().getExpressionAssignmentExpressionParserRuleCall_0_1_1_0());
						}
						lv_expression_2_0=norm1_AssignmentExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getBindingElementImplRule());
							}
							set(
								$current,
								"expression",
								lv_expression_2_0,
								"org.eclipse.n4js.N4JS.AssignmentExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)?
		)
		    |
		(
			(
				{
					newCompositeNode(grammarAccess.getBindingElementImplAccess().getVarDeclVariableDeclarationParserRuleCall_1_0());
				}
				lv_varDecl_3_0=norm5_VariableDeclaration
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getBindingElementImplRule());
					}
					set(
						$current,
						"varDecl",
						lv_varDecl_3_0,
						"org.eclipse.n4js.N4JS.VariableDeclaration");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule BindingElementImpl
norm1_BindingElementImpl[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				((
					(
						norm1_BindingPattern
					)
				)
				)=>
				(
					(
						{
							newCompositeNode(grammarAccess.getBindingElementImplAccess().getNestedPatternBindingPatternParserRuleCall_0_0_0_0());
						}
						lv_nestedPattern_0_0=norm1_BindingPattern
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getBindingElementImplRule());
							}
							set(
								$current,
								"nestedPattern",
								lv_nestedPattern_0_0,
								"org.eclipse.n4js.N4JS.BindingPattern");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			(
				otherlv_1=EqualsSign
				{
					newLeafNode(otherlv_1, grammarAccess.getBindingElementImplAccess().getEqualsSignKeyword_0_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getBindingElementImplAccess().getExpressionAssignmentExpressionParserRuleCall_0_1_1_0());
						}
						lv_expression_2_0=norm3_AssignmentExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getBindingElementImplRule());
							}
							set(
								$current,
								"expression",
								lv_expression_2_0,
								"org.eclipse.n4js.N4JS.AssignmentExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)?
		)
		    |
		(
			(
				{
					newCompositeNode(grammarAccess.getBindingElementImplAccess().getVarDeclVariableDeclarationParserRuleCall_1_0());
				}
				lv_varDecl_3_0=norm7_VariableDeclaration
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getBindingElementImplRule());
					}
					set(
						$current,
						"varDecl",
						lv_varDecl_3_0,
						"org.eclipse.n4js.N4JS.VariableDeclaration");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleElision
entryRuleElision returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getElisionRule()); }
	iv_ruleElision=ruleElision
	{ $current=$iv_ruleElision.current; }
	EOF;

// Rule Elision
ruleElision returns [EObject current=null]
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
					grammarAccess.getElisionAccess().getBindingElementAction_0(),
					$current);
			}
		)
		otherlv_1=Comma
		{
			newLeafNode(otherlv_1, grammarAccess.getElisionAccess().getCommaKeyword_1());
		}
	)
;

// Entry rule entryRuleLiteralOrComputedPropertyName
entryRuleLiteralOrComputedPropertyName returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getLiteralOrComputedPropertyNameRule()); }
	iv_ruleLiteralOrComputedPropertyName=ruleLiteralOrComputedPropertyName
	{ $current=$iv_ruleLiteralOrComputedPropertyName.current; }
	EOF;

// Rule LiteralOrComputedPropertyName
ruleLiteralOrComputedPropertyName returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getLiteralOrComputedPropertyNameAccess().getLiteralNameIdentifierNameParserRuleCall_0_0());
				}
				lv_literalName_0_0=ruleIdentifierName
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getLiteralOrComputedPropertyNameRule());
					}
					set(
						$current,
						"literalName",
						lv_literalName_0_0,
						"org.eclipse.n4js.ts.TypeExpressions.IdentifierName");
					afterParserOrEnumRuleCall();
				}
			)
		)
		    |
		(
			(
				lv_literalName_1_0=RULE_STRING
				{
					newLeafNode(lv_literalName_1_0, grammarAccess.getLiteralOrComputedPropertyNameAccess().getLiteralNameSTRINGTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getLiteralOrComputedPropertyNameRule());
					}
					setWithLastConsumed(
						$current,
						"literalName",
						lv_literalName_1_0,
						"org.eclipse.n4js.N4JS.STRING");
				}
			)
		)
		    |
		(
			(
				{
					newCompositeNode(grammarAccess.getLiteralOrComputedPropertyNameAccess().getLiteralNameNumericLiteralAsStringParserRuleCall_2_0());
				}
				lv_literalName_2_0=ruleNumericLiteralAsString
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getLiteralOrComputedPropertyNameRule());
					}
					set(
						$current,
						"literalName",
						lv_literalName_2_0,
						"org.eclipse.n4js.N4JS.NumericLiteralAsString");
					afterParserOrEnumRuleCall();
				}
			)
		)
		    |
		(
			otherlv_3=LeftSquareBracket
			{
				newLeafNode(otherlv_3, grammarAccess.getLiteralOrComputedPropertyNameAccess().getLeftSquareBracketKeyword_3_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getLiteralOrComputedPropertyNameAccess().getExpressionAssignmentExpressionParserRuleCall_3_1_0());
					}
					lv_expression_4_0=norm1_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getLiteralOrComputedPropertyNameRule());
						}
						set(
							$current,
							"expression",
							lv_expression_4_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_5=RightSquareBracket
			{
				newLeafNode(otherlv_5, grammarAccess.getLiteralOrComputedPropertyNameAccess().getRightSquareBracketKeyword_3_2());
			}
		)
	)
;


// Rule LiteralOrComputedPropertyName
norm1_LiteralOrComputedPropertyName returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getLiteralOrComputedPropertyNameAccess().getLiteralNameIdentifierNameParserRuleCall_0_0());
				}
				lv_literalName_0_0=ruleIdentifierName
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getLiteralOrComputedPropertyNameRule());
					}
					set(
						$current,
						"literalName",
						lv_literalName_0_0,
						"org.eclipse.n4js.ts.TypeExpressions.IdentifierName");
					afterParserOrEnumRuleCall();
				}
			)
		)
		    |
		(
			(
				lv_literalName_1_0=RULE_STRING
				{
					newLeafNode(lv_literalName_1_0, grammarAccess.getLiteralOrComputedPropertyNameAccess().getLiteralNameSTRINGTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getLiteralOrComputedPropertyNameRule());
					}
					setWithLastConsumed(
						$current,
						"literalName",
						lv_literalName_1_0,
						"org.eclipse.n4js.N4JS.STRING");
				}
			)
		)
		    |
		(
			(
				{
					newCompositeNode(grammarAccess.getLiteralOrComputedPropertyNameAccess().getLiteralNameNumericLiteralAsStringParserRuleCall_2_0());
				}
				lv_literalName_2_0=ruleNumericLiteralAsString
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getLiteralOrComputedPropertyNameRule());
					}
					set(
						$current,
						"literalName",
						lv_literalName_2_0,
						"org.eclipse.n4js.N4JS.NumericLiteralAsString");
					afterParserOrEnumRuleCall();
				}
			)
		)
		    |
		(
			otherlv_3=LeftSquareBracket
			{
				newLeafNode(otherlv_3, grammarAccess.getLiteralOrComputedPropertyNameAccess().getLeftSquareBracketKeyword_3_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getLiteralOrComputedPropertyNameAccess().getExpressionAssignmentExpressionParserRuleCall_3_1_0());
					}
					lv_expression_4_0=norm3_AssignmentExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getLiteralOrComputedPropertyNameRule());
						}
						set(
							$current,
							"expression",
							lv_expression_4_0,
							"org.eclipse.n4js.N4JS.AssignmentExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_5=RightSquareBracket
			{
				newLeafNode(otherlv_5, grammarAccess.getLiteralOrComputedPropertyNameAccess().getRightSquareBracketKeyword_3_2());
			}
		)
	)
;

// Entry rule entryRuleJSXElement
entryRuleJSXElement returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getJSXElementRule()); }
	iv_ruleJSXElement=ruleJSXElement
	{ $current=$iv_ruleJSXElement.current; }
	EOF;

// Rule JSXElement
ruleJSXElement returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LessThanSign
		{
			newLeafNode(otherlv_0, grammarAccess.getJSXElementAccess().getLessThanSignKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getJSXElementAccess().getJsxElementNameJSXElementNameParserRuleCall_1_0());
				}
				lv_jsxElementName_1_0=ruleJSXElementName
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getJSXElementRule());
					}
					set(
						$current,
						"jsxElementName",
						lv_jsxElementName_1_0,
						"org.eclipse.n4js.N4JS.JSXElementName");
					afterParserOrEnumRuleCall();
				}
			)
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getJSXElementRule());
			}
			newCompositeNode(grammarAccess.getJSXElementAccess().getJSXAttributesParserRuleCall_2());
		}
		this_JSXAttributes_2=ruleJSXAttributes[$current]
		{
			$current = $this_JSXAttributes_2.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				otherlv_3=GreaterThanSign
				{
					newLeafNode(otherlv_3, grammarAccess.getJSXElementAccess().getGreaterThanSignKeyword_3_0_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getJSXElementAccess().getJsxChildrenJSXChildParserRuleCall_3_0_1_0());
						}
						lv_jsxChildren_4_0=ruleJSXChild
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getJSXElementRule());
							}
							add(
								$current,
								"jsxChildren",
								lv_jsxChildren_4_0,
								"org.eclipse.n4js.N4JS.JSXChild");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getJSXElementRule());
					}
					newCompositeNode(grammarAccess.getJSXElementAccess().getJSXClosingElementParserRuleCall_3_0_2());
				}
				this_JSXClosingElement_5=ruleJSXClosingElement[$current]
				{
					$current = $this_JSXClosingElement_5.current;
					afterParserOrEnumRuleCall();
				}
			)
			    |
			(
				otherlv_6=Solidus
				{
					newLeafNode(otherlv_6, grammarAccess.getJSXElementAccess().getSolidusKeyword_3_1_0());
				}
				otherlv_7=GreaterThanSign
				{
					newLeafNode(otherlv_7, grammarAccess.getJSXElementAccess().getGreaterThanSignKeyword_3_1_1());
				}
			)
		)
	)
;

// Entry rule entryRuleJSXFragment
entryRuleJSXFragment returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getJSXFragmentRule()); }
	iv_ruleJSXFragment=ruleJSXFragment
	{ $current=$iv_ruleJSXFragment.current; }
	EOF;

// Rule JSXFragment
ruleJSXFragment returns [EObject current=null]
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
					$current = forceCreateModelElement(
						grammarAccess.getJSXFragmentAccess().getJSXFragmentAction_0_0(),
						$current);
				}
			)
			(
				otherlv_1=LessThanSign
				{
					newLeafNode(otherlv_1, grammarAccess.getJSXFragmentAccess().getLessThanSignKeyword_0_1_0());
				}
				otherlv_2=GreaterThanSign
				{
					newLeafNode(otherlv_2, grammarAccess.getJSXFragmentAccess().getGreaterThanSignKeyword_0_1_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getJSXFragmentAccess().getJsxChildrenJSXChildParserRuleCall_0_1_2_0());
						}
						lv_jsxChildren_3_0=ruleJSXChild
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getJSXFragmentRule());
							}
							add(
								$current,
								"jsxChildren",
								lv_jsxChildren_3_0,
								"org.eclipse.n4js.N4JS.JSXChild");
							afterParserOrEnumRuleCall();
						}
					)
				)*
				otherlv_4=LessThanSign
				{
					newLeafNode(otherlv_4, grammarAccess.getJSXFragmentAccess().getLessThanSignKeyword_0_1_3());
				}
				otherlv_5=Solidus
				{
					newLeafNode(otherlv_5, grammarAccess.getJSXFragmentAccess().getSolidusKeyword_0_1_4());
				}
				otherlv_6=GreaterThanSign
				{
					newLeafNode(otherlv_6, grammarAccess.getJSXFragmentAccess().getGreaterThanSignKeyword_0_1_5());
				}
			)
		)
		    |
		(
			(
				{
					$current = forceCreateModelElement(
						grammarAccess.getJSXFragmentAccess().getJSXFragmentAction_1_0(),
						$current);
				}
			)
			(
				otherlv_8=LessThanSign
				{
					newLeafNode(otherlv_8, grammarAccess.getJSXFragmentAccess().getLessThanSignKeyword_1_1_0());
				}
				otherlv_9=Solidus
				{
					newLeafNode(otherlv_9, grammarAccess.getJSXFragmentAccess().getSolidusKeyword_1_1_1());
				}
				otherlv_10=GreaterThanSign
				{
					newLeafNode(otherlv_10, grammarAccess.getJSXFragmentAccess().getGreaterThanSignKeyword_1_1_2());
				}
			)
		)
	)
;


// Rule JSXClosingElement
ruleJSXClosingElement[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LessThanSign
		{
			newLeafNode(otherlv_0, grammarAccess.getJSXClosingElementAccess().getLessThanSignKeyword_0());
		}
		otherlv_1=Solidus
		{
			newLeafNode(otherlv_1, grammarAccess.getJSXClosingElementAccess().getSolidusKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getJSXClosingElementAccess().getJsxClosingNameJSXElementNameParserRuleCall_2_0());
				}
				lv_jsxClosingName_2_0=ruleJSXElementName
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getJSXClosingElementRule());
					}
					set(
						$current,
						"jsxClosingName",
						lv_jsxClosingName_2_0,
						"org.eclipse.n4js.N4JS.JSXElementName");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_3=GreaterThanSign
		{
			newLeafNode(otherlv_3, grammarAccess.getJSXClosingElementAccess().getGreaterThanSignKeyword_3());
		}
	)
;

// Entry rule entryRuleJSXChild
entryRuleJSXChild returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getJSXChildRule()); }
	iv_ruleJSXChild=ruleJSXChild
	{ $current=$iv_ruleJSXChild.current; }
	EOF;

// Rule JSXChild
ruleJSXChild returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getJSXChildAccess().getJSXElementParserRuleCall_0());
		}
		this_JSXElement_0=ruleJSXElement
		{
			$current = $this_JSXElement_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getJSXChildAccess().getJSXExpressionParserRuleCall_1());
		}
		this_JSXExpression_1=ruleJSXExpression
		{
			$current = $this_JSXExpression_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleJSXExpression
entryRuleJSXExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getJSXExpressionRule()); }
	iv_ruleJSXExpression=ruleJSXExpression
	{ $current=$iv_ruleJSXExpression.current; }
	EOF;

// Rule JSXExpression
ruleJSXExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LeftCurlyBracket
		{
			newLeafNode(otherlv_0, grammarAccess.getJSXExpressionAccess().getLeftCurlyBracketKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getJSXExpressionAccess().getExpressionAssignmentExpressionParserRuleCall_1_0());
				}
				lv_expression_1_0=ruleAssignmentExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getJSXExpressionRule());
					}
					set(
						$current,
						"expression",
						lv_expression_1_0,
						"org.eclipse.n4js.N4JS.AssignmentExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_2=RightCurlyBracket
		{
			newLeafNode(otherlv_2, grammarAccess.getJSXExpressionAccess().getRightCurlyBracketKeyword_2());
		}
	)
;

// Entry rule entryRuleJSXElementName
entryRuleJSXElementName returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getJSXElementNameRule()); }
	iv_ruleJSXElementName=ruleJSXElementName
	{ $current=$iv_ruleJSXElementName.current; }
	EOF;

// Rule JSXElementName
ruleJSXElementName returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getJSXElementNameAccess().getExpressionJSXElementNameExpressionParserRuleCall_0());
			}
			lv_expression_0_0=ruleJSXElementNameExpression
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getJSXElementNameRule());
				}
				set(
					$current,
					"expression",
					lv_expression_0_0,
					"org.eclipse.n4js.N4JS.JSXElementNameExpression");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleJSXElementNameExpression
entryRuleJSXElementNameExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getJSXElementNameExpressionRule()); }
	iv_ruleJSXElementNameExpression=ruleJSXElementNameExpression
	{ $current=$iv_ruleJSXElementNameExpression.current; }
	EOF;

// Rule JSXElementNameExpression
ruleJSXElementNameExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getJSXElementNameExpressionAccess().getIdentifierRefParserRuleCall_0());
		}
		this_IdentifierRef_0=ruleIdentifierRef
		{
			$current = $this_IdentifierRef_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				{
					$current = forceCreateModelElementAndSet(
						grammarAccess.getJSXElementNameExpressionAccess().getParameterizedPropertyAccessExpressionTargetAction_1_0(),
						$current);
				}
			)
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getJSXElementNameExpressionRule());
				}
				newCompositeNode(grammarAccess.getJSXElementNameExpressionAccess().getParameterizedPropertyAccessExpressionTailParserRuleCall_1_1());
			}
			this_ParameterizedPropertyAccessExpressionTail_2=ruleParameterizedPropertyAccessExpressionTail[$current]
			{
				$current = $this_ParameterizedPropertyAccessExpressionTail_2.current;
				afterParserOrEnumRuleCall();
			}
		)*
	)
;


// Rule JSXAttributes
ruleJSXAttributes[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getJSXAttributesAccess().getJsxAttributesJSXAttributeParserRuleCall_0());
			}
			lv_jsxAttributes_0_0=ruleJSXAttribute
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getJSXAttributesRule());
				}
				add(
					$current,
					"jsxAttributes",
					lv_jsxAttributes_0_0,
					"org.eclipse.n4js.N4JS.JSXAttribute");
				afterParserOrEnumRuleCall();
			}
		)
	)*
;

// Entry rule entryRuleJSXAttribute
entryRuleJSXAttribute returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getJSXAttributeRule()); }
	iv_ruleJSXAttribute=ruleJSXAttribute
	{ $current=$iv_ruleJSXAttribute.current; }
	EOF;

// Rule JSXAttribute
ruleJSXAttribute returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getJSXAttributeAccess().getJSXSpreadAttributeParserRuleCall_0());
		}
		this_JSXSpreadAttribute_0=ruleJSXSpreadAttribute
		{
			$current = $this_JSXSpreadAttribute_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getJSXAttributeAccess().getJSXPropertyAttributeParserRuleCall_1());
		}
		this_JSXPropertyAttribute_1=ruleJSXPropertyAttribute
		{
			$current = $this_JSXPropertyAttribute_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleJSXSpreadAttribute
entryRuleJSXSpreadAttribute returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getJSXSpreadAttributeRule()); }
	iv_ruleJSXSpreadAttribute=ruleJSXSpreadAttribute
	{ $current=$iv_ruleJSXSpreadAttribute.current; }
	EOF;

// Rule JSXSpreadAttribute
ruleJSXSpreadAttribute returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LeftCurlyBracket
		{
			newLeafNode(otherlv_0, grammarAccess.getJSXSpreadAttributeAccess().getLeftCurlyBracketKeyword_0());
		}
		otherlv_1=FullStopFullStopFullStop
		{
			newLeafNode(otherlv_1, grammarAccess.getJSXSpreadAttributeAccess().getFullStopFullStopFullStopKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getJSXSpreadAttributeAccess().getExpressionAssignmentExpressionParserRuleCall_2_0());
				}
				lv_expression_2_0=ruleAssignmentExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getJSXSpreadAttributeRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.n4js.N4JS.AssignmentExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_3=RightCurlyBracket
		{
			newLeafNode(otherlv_3, grammarAccess.getJSXSpreadAttributeAccess().getRightCurlyBracketKeyword_3());
		}
	)
;

// Entry rule entryRuleJSXPropertyAttribute
entryRuleJSXPropertyAttribute returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getJSXPropertyAttributeRule()); }
	iv_ruleJSXPropertyAttribute=ruleJSXPropertyAttribute
	{ $current=$iv_ruleJSXPropertyAttribute.current; }
	EOF;

// Rule JSXPropertyAttribute
ruleJSXPropertyAttribute returns [EObject current=null]
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
					if ($current==null) {
						$current = createModelElement(grammarAccess.getJSXPropertyAttributeRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getJSXPropertyAttributeAccess().getPropertyIdentifiableElementCrossReference_0_0());
				}
				ruleIdentifierName
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_1=EqualsSign
			{
				newLeafNode(otherlv_1, grammarAccess.getJSXPropertyAttributeAccess().getEqualsSignKeyword_1_0());
			}
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getJSXPropertyAttributeAccess().getJsxAttributeValueStringLiteralParserRuleCall_1_1_0_0());
						}
						lv_jsxAttributeValue_2_0=ruleStringLiteral
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getJSXPropertyAttributeRule());
							}
							set(
								$current,
								"jsxAttributeValue",
								lv_jsxAttributeValue_2_0,
								"org.eclipse.n4js.N4JS.StringLiteral");
							afterParserOrEnumRuleCall();
						}
					)
				)
				    |
				(
					otherlv_3=LeftCurlyBracket
					{
						newLeafNode(otherlv_3, grammarAccess.getJSXPropertyAttributeAccess().getLeftCurlyBracketKeyword_1_1_1_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getJSXPropertyAttributeAccess().getJsxAttributeValueAssignmentExpressionParserRuleCall_1_1_1_1_0());
							}
							lv_jsxAttributeValue_4_0=ruleAssignmentExpression
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getJSXPropertyAttributeRule());
								}
								set(
									$current,
									"jsxAttributeValue",
									lv_jsxAttributeValue_4_0,
									"org.eclipse.n4js.N4JS.AssignmentExpression");
								afterParserOrEnumRuleCall();
							}
						)
					)
					otherlv_5=RightCurlyBracket
					{
						newLeafNode(otherlv_5, grammarAccess.getJSXPropertyAttributeAccess().getRightCurlyBracketKeyword_1_1_1_2());
					}
				)
			)
		)?
	)
;


// Rule VersionDeclaration
ruleVersionDeclaration[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_declaredVersion_0_0=RULE_VERSION
			{
				newLeafNode(lv_declaredVersion_0_0, grammarAccess.getVersionDeclarationAccess().getDeclaredVersionVERSIONTerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getVersionDeclarationRule());
				}
				setWithLastConsumed(
					$current,
					"declaredVersion",
					lv_declaredVersion_0_0,
					"org.eclipse.n4js.ts.TypeExpressions.VERSION");
			}
		)
	)
;

// Entry rule entryRuleTypeRef
entryRuleTypeRef returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTypeRefRule()); }
	iv_ruleTypeRef=ruleTypeRef
	{ $current=$iv_ruleTypeRef.current; }
	EOF;

// Rule TypeRef
ruleTypeRef returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getTypeRefAccess().getIntersectionTypeExpressionParserRuleCall_0());
		}
		this_IntersectionTypeExpression_0=ruleIntersectionTypeExpression
		{
			$current = $this_IntersectionTypeExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				{
					$current = forceCreateModelElementAndAdd(
						grammarAccess.getTypeRefAccess().getUnionTypeExpressionTypeRefsAction_1_0(),
						$current);
				}
			)
			(
				otherlv_2=VerticalLine
				{
					newLeafNode(otherlv_2, grammarAccess.getTypeRefAccess().getVerticalLineKeyword_1_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getTypeRefAccess().getTypeRefsIntersectionTypeExpressionParserRuleCall_1_1_1_0());
						}
						lv_typeRefs_3_0=ruleIntersectionTypeExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTypeRefRule());
							}
							add(
								$current,
								"typeRefs",
								lv_typeRefs_3_0,
								"org.eclipse.n4js.ts.TypeExpressions.IntersectionTypeExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)+
		)?
	)
;

// Entry rule entryRuleIntersectionTypeExpression
entryRuleIntersectionTypeExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getIntersectionTypeExpressionRule()); }
	iv_ruleIntersectionTypeExpression=ruleIntersectionTypeExpression
	{ $current=$iv_ruleIntersectionTypeExpression.current; }
	EOF;

// Rule IntersectionTypeExpression
ruleIntersectionTypeExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getIntersectionTypeExpressionAccess().getArrayTypeExpressionParserRuleCall_0());
		}
		this_ArrayTypeExpression_0=ruleArrayTypeExpression
		{
			$current = $this_ArrayTypeExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				{
					$current = forceCreateModelElementAndAdd(
						grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0(),
						$current);
				}
			)
			(
				otherlv_2=Ampersand
				{
					newLeafNode(otherlv_2, grammarAccess.getIntersectionTypeExpressionAccess().getAmpersandKeyword_1_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getIntersectionTypeExpressionAccess().getTypeRefsArrayTypeExpressionParserRuleCall_1_1_1_0());
						}
						lv_typeRefs_3_0=ruleArrayTypeExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getIntersectionTypeExpressionRule());
							}
							add(
								$current,
								"typeRefs",
								lv_typeRefs_3_0,
								"org.eclipse.n4js.ts.TypeExpressions.ArrayTypeExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)+
		)?
	)
;

// Entry rule entryRuleArrayTypeExpression
entryRuleArrayTypeExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getArrayTypeExpressionRule()); }
	iv_ruleArrayTypeExpression=ruleArrayTypeExpression
	{ $current=$iv_ruleArrayTypeExpression.current; }
	EOF;

// Rule ArrayTypeExpression
ruleArrayTypeExpression returns [EObject current=null]
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
					$current = forceCreateModelElement(
						grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefAction_0_0(),
						$current);
				}
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getArrayTypeExpressionAccess().getTypeArgsWildcardOldNotationWithoutBoundParserRuleCall_0_1_0());
					}
					lv_typeArgs_1_0=ruleWildcardOldNotationWithoutBound
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getArrayTypeExpressionRule());
						}
						add(
							$current,
							"typeArgs",
							lv_typeArgs_1_0,
							"org.eclipse.n4js.ts.TypeExpressions.WildcardOldNotationWithoutBound");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				(
					lv_arrayTypeExpression_2_0=LeftSquareBracket
					{
						newLeafNode(lv_arrayTypeExpression_2_0, grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_0_2_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getArrayTypeExpressionRule());
						}
						setWithLastConsumed($current, "arrayTypeExpression", true, "[");
					}
				)
			)
			otherlv_3=RightSquareBracket
			{
				newLeafNode(otherlv_3, grammarAccess.getArrayTypeExpressionAccess().getRightSquareBracketKeyword_0_3());
			}
			(
				((
					(
					)
					(
						(
							LeftSquareBracket
						)
					)
					RightSquareBracket
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndAdd(
								grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_0_4_0_0(),
								$current);
						}
					)
					(
						(
							lv_arrayTypeExpression_5_0=LeftSquareBracket
							{
								newLeafNode(lv_arrayTypeExpression_5_0, grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_0_4_0_1_0());
							}
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getArrayTypeExpressionRule());
								}
								setWithLastConsumed($current, "arrayTypeExpression", true, "[");
							}
						)
					)
					otherlv_6=RightSquareBracket
					{
						newLeafNode(otherlv_6, grammarAccess.getArrayTypeExpressionAccess().getRightSquareBracketKeyword_0_4_0_2());
					}
				)
			)*
		)
		    |
		(
			(
				{
					$current = forceCreateModelElement(
						grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefAction_1_0(),
						$current);
				}
			)
			otherlv_8=LeftParenthesis
			{
				newLeafNode(otherlv_8, grammarAccess.getArrayTypeExpressionAccess().getLeftParenthesisKeyword_1_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getArrayTypeExpressionAccess().getTypeArgsWildcardParserRuleCall_1_2_0());
					}
					lv_typeArgs_9_0=ruleWildcard
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getArrayTypeExpressionRule());
						}
						add(
							$current,
							"typeArgs",
							lv_typeArgs_9_0,
							"org.eclipse.n4js.ts.TypeExpressions.Wildcard");
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_10=RightParenthesis
			{
				newLeafNode(otherlv_10, grammarAccess.getArrayTypeExpressionAccess().getRightParenthesisKeyword_1_3());
			}
			(
				(
					lv_arrayTypeExpression_11_0=LeftSquareBracket
					{
						newLeafNode(lv_arrayTypeExpression_11_0, grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_1_4_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getArrayTypeExpressionRule());
						}
						setWithLastConsumed($current, "arrayTypeExpression", true, "[");
					}
				)
			)
			otherlv_12=RightSquareBracket
			{
				newLeafNode(otherlv_12, grammarAccess.getArrayTypeExpressionAccess().getRightSquareBracketKeyword_1_5());
			}
			(
				((
					(
					)
					(
						(
							LeftSquareBracket
						)
					)
					RightSquareBracket
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndAdd(
								grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_6_0_0(),
								$current);
						}
					)
					(
						(
							lv_arrayTypeExpression_14_0=LeftSquareBracket
							{
								newLeafNode(lv_arrayTypeExpression_14_0, grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_1_6_0_1_0());
							}
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getArrayTypeExpressionRule());
								}
								setWithLastConsumed($current, "arrayTypeExpression", true, "[");
							}
						)
					)
					otherlv_15=RightSquareBracket
					{
						newLeafNode(otherlv_15, grammarAccess.getArrayTypeExpressionAccess().getRightSquareBracketKeyword_1_6_0_2());
					}
				)
			)*
		)
		    |
		(
			{
				newCompositeNode(grammarAccess.getArrayTypeExpressionAccess().getPrimaryTypeExpressionParserRuleCall_2_0());
			}
			this_PrimaryTypeExpression_16=rulePrimaryTypeExpression
			{
				$current = $this_PrimaryTypeExpression_16.current;
				afterParserOrEnumRuleCall();
			}
			(
				((
					(
					)
					(
						(
							LeftSquareBracket
						)
					)
					RightSquareBracket
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndAdd(
								grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_2_1_0_0(),
								$current);
						}
					)
					(
						(
							lv_arrayTypeExpression_18_0=LeftSquareBracket
							{
								newLeafNode(lv_arrayTypeExpression_18_0, grammarAccess.getArrayTypeExpressionAccess().getArrayTypeExpressionLeftSquareBracketKeyword_2_1_0_1_0());
							}
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getArrayTypeExpressionRule());
								}
								setWithLastConsumed($current, "arrayTypeExpression", true, "[");
							}
						)
					)
					otherlv_19=RightSquareBracket
					{
						newLeafNode(otherlv_19, grammarAccess.getArrayTypeExpressionAccess().getRightSquareBracketKeyword_2_1_0_2());
					}
				)
			)*
		)
	)
;

// Entry rule entryRulePrimaryTypeExpression
entryRulePrimaryTypeExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPrimaryTypeExpressionRule()); }
	iv_rulePrimaryTypeExpression=rulePrimaryTypeExpression
	{ $current=$iv_rulePrimaryTypeExpression.current; }
	EOF;

// Rule PrimaryTypeExpression
rulePrimaryTypeExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				LeftParenthesis
				ruleTAnonymousFormalParameterList[null]
				RightParenthesis
				EqualsSignGreaterThanSign
			)
			)=>
			{
				newCompositeNode(grammarAccess.getPrimaryTypeExpressionAccess().getArrowFunctionTypeExpressionParserRuleCall_0());
			}
			this_ArrowFunctionTypeExpression_0=ruleArrowFunctionTypeExpression
			{
				$current = $this_ArrowFunctionTypeExpression_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryTypeExpressionAccess().getIterableTypeExpressionParserRuleCall_1());
		}
		this_IterableTypeExpression_1=ruleIterableTypeExpression
		{
			$current = $this_IterableTypeExpression_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getPrimaryTypeExpressionAccess().getTypeRefWithModifiersParserRuleCall_2());
		}
		this_TypeRefWithModifiers_2=ruleTypeRefWithModifiers
		{
			$current = $this_TypeRefWithModifiers_2.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			otherlv_3=LeftParenthesis
			{
				newLeafNode(otherlv_3, grammarAccess.getPrimaryTypeExpressionAccess().getLeftParenthesisKeyword_3_0());
			}
			{
				newCompositeNode(grammarAccess.getPrimaryTypeExpressionAccess().getTypeRefParserRuleCall_3_1());
			}
			this_TypeRef_4=ruleTypeRef
			{
				$current = $this_TypeRef_4.current;
				afterParserOrEnumRuleCall();
			}
			otherlv_5=RightParenthesis
			{
				newLeafNode(otherlv_5, grammarAccess.getPrimaryTypeExpressionAccess().getRightParenthesisKeyword_3_2());
			}
		)
	)
;

// Entry rule entryRuleTypeRefWithModifiers
entryRuleTypeRefWithModifiers returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTypeRefWithModifiersRule()); }
	iv_ruleTypeRefWithModifiers=ruleTypeRefWithModifiers
	{ $current=$iv_ruleTypeRefWithModifiers.current; }
	EOF;

// Rule TypeRefWithModifiers
ruleTypeRefWithModifiers returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getTypeRefWithModifiersAccess().getTypeRefWithoutModifiersParserRuleCall_0());
		}
		this_TypeRefWithoutModifiers_0=ruleTypeRefWithoutModifiers
		{
			$current = $this_TypeRefWithoutModifiers_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			((
				QuestionMark
			)
			)=>
			(
				lv_followedByQuestionMark_1_0=QuestionMark
				{
					newLeafNode(lv_followedByQuestionMark_1_0, grammarAccess.getTypeRefWithModifiersAccess().getFollowedByQuestionMarkQuestionMarkKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTypeRefWithModifiersRule());
					}
					setWithLastConsumed($current, "followedByQuestionMark", true, "?");
				}
			)
		)?
	)
;

// Entry rule entryRuleTypeRefWithoutModifiers
entryRuleTypeRefWithoutModifiers returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTypeRefWithoutModifiersRule()); }
	iv_ruleTypeRefWithoutModifiers=ruleTypeRefWithoutModifiers
	{ $current=$iv_ruleTypeRefWithoutModifiers.current; }
	EOF;

// Rule TypeRefWithoutModifiers
ruleTypeRefWithoutModifiers returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getTypeRefWithoutModifiersAccess().getParameterizedTypeRefParserRuleCall_0_0_0());
				}
				this_ParameterizedTypeRef_0=ruleParameterizedTypeRef
				{
					$current = $this_ParameterizedTypeRef_0.current;
					afterParserOrEnumRuleCall();
				}
				    |
				{
					newCompositeNode(grammarAccess.getTypeRefWithoutModifiersAccess().getThisTypeRefParserRuleCall_0_0_1());
				}
				this_ThisTypeRef_1=ruleThisTypeRef
				{
					$current = $this_ThisTypeRef_1.current;
					afterParserOrEnumRuleCall();
				}
			)
			(
				((
					PlusSign
				)
				)=>
				(
					lv_dynamic_2_0=PlusSign
					{
						newLeafNode(lv_dynamic_2_0, grammarAccess.getTypeRefWithoutModifiersAccess().getDynamicPlusSignKeyword_0_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getTypeRefWithoutModifiersRule());
						}
						setWithLastConsumed($current, "dynamic", true, "+");
					}
				)
			)?
		)
		    |
		{
			newCompositeNode(grammarAccess.getTypeRefWithoutModifiersAccess().getTypeTypeRefParserRuleCall_1());
		}
		this_TypeTypeRef_3=ruleTypeTypeRef
		{
			$current = $this_TypeTypeRef_3.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeRefWithoutModifiersAccess().getFunctionTypeExpressionOLDParserRuleCall_2());
		}
		this_FunctionTypeExpressionOLD_4=ruleFunctionTypeExpressionOLD
		{
			$current = $this_FunctionTypeExpressionOLD_4.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeRefWithoutModifiersAccess().getUnionTypeExpressionOLDParserRuleCall_3());
		}
		this_UnionTypeExpressionOLD_5=ruleUnionTypeExpressionOLD
		{
			$current = $this_UnionTypeExpressionOLD_5.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeRefWithoutModifiersAccess().getIntersectionTypeExpressionOLDParserRuleCall_4());
		}
		this_IntersectionTypeExpressionOLD_6=ruleIntersectionTypeExpressionOLD
		{
			$current = $this_IntersectionTypeExpressionOLD_6.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleTypeRefFunctionTypeExpression
entryRuleTypeRefFunctionTypeExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTypeRefFunctionTypeExpressionRule()); }
	iv_ruleTypeRefFunctionTypeExpression=ruleTypeRefFunctionTypeExpression
	{ $current=$iv_ruleTypeRefFunctionTypeExpression.current; }
	EOF;

// Rule TypeRefFunctionTypeExpression
ruleTypeRefFunctionTypeExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getParameterizedTypeRefParserRuleCall_0());
		}
		this_ParameterizedTypeRef_0=ruleParameterizedTypeRef
		{
			$current = $this_ParameterizedTypeRef_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getIterableTypeExpressionParserRuleCall_1());
		}
		this_IterableTypeExpression_1=ruleIterableTypeExpression
		{
			$current = $this_IterableTypeExpression_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getTypeTypeRefParserRuleCall_2());
		}
		this_TypeTypeRef_2=ruleTypeTypeRef
		{
			$current = $this_TypeTypeRef_2.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getUnionTypeExpressionOLDParserRuleCall_3());
		}
		this_UnionTypeExpressionOLD_3=ruleUnionTypeExpressionOLD
		{
			$current = $this_UnionTypeExpressionOLD_3.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getIntersectionTypeExpressionOLDParserRuleCall_4());
		}
		this_IntersectionTypeExpressionOLD_4=ruleIntersectionTypeExpressionOLD
		{
			$current = $this_IntersectionTypeExpressionOLD_4.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleTypeArgInTypeTypeRef
entryRuleTypeArgInTypeTypeRef returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTypeArgInTypeTypeRefRule()); }
	iv_ruleTypeArgInTypeTypeRef=ruleTypeArgInTypeTypeRef
	{ $current=$iv_ruleTypeArgInTypeTypeRef.current; }
	EOF;

// Rule TypeArgInTypeTypeRef
ruleTypeArgInTypeTypeRef returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getTypeArgInTypeTypeRefAccess().getParameterizedTypeRefNominalParserRuleCall_0());
		}
		this_ParameterizedTypeRefNominal_0=ruleParameterizedTypeRefNominal
		{
			$current = $this_ParameterizedTypeRefNominal_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeArgInTypeTypeRefAccess().getThisTypeRefNominalParserRuleCall_1());
		}
		this_ThisTypeRefNominal_1=ruleThisTypeRefNominal
		{
			$current = $this_ThisTypeRefNominal_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			((
				(
				)
				QuestionMark
			)
			)=>
			{
				newCompositeNode(grammarAccess.getTypeArgInTypeTypeRefAccess().getWildcardOldNotationParserRuleCall_2());
			}
			this_WildcardOldNotation_2=ruleWildcardOldNotation
			{
				$current = $this_WildcardOldNotation_2.current;
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleThisTypeRef
entryRuleThisTypeRef returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getThisTypeRefRule()); }
	iv_ruleThisTypeRef=ruleThisTypeRef
	{ $current=$iv_ruleThisTypeRef.current; }
	EOF;

// Rule ThisTypeRef
ruleThisTypeRef returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getThisTypeRefAccess().getThisTypeRefNominalParserRuleCall_0());
		}
		this_ThisTypeRefNominal_0=ruleThisTypeRefNominal
		{
			$current = $this_ThisTypeRefNominal_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getThisTypeRefAccess().getThisTypeRefStructuralParserRuleCall_1());
		}
		this_ThisTypeRefStructural_1=ruleThisTypeRefStructural
		{
			$current = $this_ThisTypeRefStructural_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleThisTypeRefNominal
entryRuleThisTypeRefNominal returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getThisTypeRefNominalRule()); }
	iv_ruleThisTypeRefNominal=ruleThisTypeRefNominal
	{ $current=$iv_ruleThisTypeRefNominal.current; }
	EOF;

// Rule ThisTypeRefNominal
ruleThisTypeRefNominal returns [EObject current=null]
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
					grammarAccess.getThisTypeRefNominalAccess().getThisTypeRefNominalAction_0(),
					$current);
			}
		)
		otherlv_1=This_1
		{
			newLeafNode(otherlv_1, grammarAccess.getThisTypeRefNominalAccess().getThisKeyword_1());
		}
	)
;

// Entry rule entryRuleThisTypeRefStructural
entryRuleThisTypeRefStructural returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getThisTypeRefStructuralRule()); }
	iv_ruleThisTypeRefStructural=ruleThisTypeRefStructural
	{ $current=$iv_ruleThisTypeRefStructural.current; }
	EOF;

// Rule ThisTypeRefStructural
ruleThisTypeRefStructural returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getThisTypeRefStructuralAccess().getDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0());
				}
				lv_definedTypingStrategy_0_0=ruleTypingStrategyUseSiteOperator
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getThisTypeRefStructuralRule());
					}
					set(
						$current,
						"definedTypingStrategy",
						lv_definedTypingStrategy_0_0,
						"org.eclipse.n4js.ts.TypeExpressions.TypingStrategyUseSiteOperator");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_1=This_1
		{
			newLeafNode(otherlv_1, grammarAccess.getThisTypeRefStructuralAccess().getThisKeyword_1());
		}
		(
			otherlv_2=With
			{
				newLeafNode(otherlv_2, grammarAccess.getThisTypeRefStructuralAccess().getWithKeyword_2_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getThisTypeRefStructuralRule());
				}
				newCompositeNode(grammarAccess.getThisTypeRefStructuralAccess().getTStructMemberListParserRuleCall_2_1());
			}
			this_TStructMemberList_3=ruleTStructMemberList[$current]
			{
				$current = $this_TStructMemberList_3.current;
				afterParserOrEnumRuleCall();
			}
		)?
	)
;

// Entry rule entryRuleFunctionTypeExpressionOLD
entryRuleFunctionTypeExpressionOLD returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getFunctionTypeExpressionOLDRule()); }
	iv_ruleFunctionTypeExpressionOLD=ruleFunctionTypeExpressionOLD
	{ $current=$iv_ruleFunctionTypeExpressionOLD.current; }
	EOF;

// Rule FunctionTypeExpressionOLD
ruleFunctionTypeExpressionOLD returns [EObject current=null]
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
					grammarAccess.getFunctionTypeExpressionOLDAccess().getFunctionTypeExpressionAction_0(),
					$current);
			}
		)
		otherlv_1=LeftCurlyBracket
		{
			newLeafNode(otherlv_1, grammarAccess.getFunctionTypeExpressionOLDAccess().getLeftCurlyBracketKeyword_1());
		}
		(
			otherlv_2=CommercialAt
			{
				newLeafNode(otherlv_2, grammarAccess.getFunctionTypeExpressionOLDAccess().getCommercialAtKeyword_2_0());
			}
			otherlv_3=This
			{
				newLeafNode(otherlv_3, grammarAccess.getFunctionTypeExpressionOLDAccess().getThisKeyword_2_1());
			}
			otherlv_4=LeftParenthesis
			{
				newLeafNode(otherlv_4, grammarAccess.getFunctionTypeExpressionOLDAccess().getLeftParenthesisKeyword_2_2());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getFunctionTypeExpressionOLDAccess().getDeclaredThisTypeTypeRefFunctionTypeExpressionParserRuleCall_2_3_0());
					}
					lv_declaredThisType_5_0=ruleTypeRefFunctionTypeExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getFunctionTypeExpressionOLDRule());
						}
						set(
							$current,
							"declaredThisType",
							lv_declaredThisType_5_0,
							"org.eclipse.n4js.ts.TypeExpressions.TypeRefFunctionTypeExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_6=RightParenthesis
			{
				newLeafNode(otherlv_6, grammarAccess.getFunctionTypeExpressionOLDAccess().getRightParenthesisKeyword_2_4());
			}
		)?
		otherlv_7=Function
		{
			newLeafNode(otherlv_7, grammarAccess.getFunctionTypeExpressionOLDAccess().getFunctionKeyword_3());
		}
		(
			otherlv_8=LessThanSign
			{
				newLeafNode(otherlv_8, grammarAccess.getFunctionTypeExpressionOLDAccess().getLessThanSignKeyword_4_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getFunctionTypeExpressionOLDAccess().getOwnedTypeVarsTypeVariableParserRuleCall_4_1_0());
					}
					lv_ownedTypeVars_9_0=ruleTypeVariable
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getFunctionTypeExpressionOLDRule());
						}
						add(
							$current,
							"ownedTypeVars",
							lv_ownedTypeVars_9_0,
							"org.eclipse.n4js.N4JS.TypeVariable");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_10=Comma
				{
					newLeafNode(otherlv_10, grammarAccess.getFunctionTypeExpressionOLDAccess().getCommaKeyword_4_2_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getFunctionTypeExpressionOLDAccess().getOwnedTypeVarsTypeVariableParserRuleCall_4_2_1_0());
						}
						lv_ownedTypeVars_11_0=ruleTypeVariable
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getFunctionTypeExpressionOLDRule());
							}
							add(
								$current,
								"ownedTypeVars",
								lv_ownedTypeVars_11_0,
								"org.eclipse.n4js.N4JS.TypeVariable");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
			otherlv_12=GreaterThanSign
			{
				newLeafNode(otherlv_12, grammarAccess.getFunctionTypeExpressionOLDAccess().getGreaterThanSignKeyword_4_3());
			}
		)?
		otherlv_13=LeftParenthesis
		{
			newLeafNode(otherlv_13, grammarAccess.getFunctionTypeExpressionOLDAccess().getLeftParenthesisKeyword_5());
		}
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getFunctionTypeExpressionOLDRule());
			}
			newCompositeNode(grammarAccess.getFunctionTypeExpressionOLDAccess().getTAnonymousFormalParameterListParserRuleCall_6());
		}
		this_TAnonymousFormalParameterList_14=ruleTAnonymousFormalParameterList[$current]
		{
			$current = $this_TAnonymousFormalParameterList_14.current;
			afterParserOrEnumRuleCall();
		}
		otherlv_15=RightParenthesis
		{
			newLeafNode(otherlv_15, grammarAccess.getFunctionTypeExpressionOLDAccess().getRightParenthesisKeyword_7());
		}
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getFunctionTypeExpressionOLDRule());
				}
				newCompositeNode(grammarAccess.getFunctionTypeExpressionOLDAccess().getColonSepReturnTypeRefParserRuleCall_8());
			}
			this_ColonSepReturnTypeRef_16=ruleColonSepReturnTypeRef[$current]
			{
				$current = $this_ColonSepReturnTypeRef_16.current;
				afterParserOrEnumRuleCall();
			}
		)?
		otherlv_17=RightCurlyBracket
		{
			newLeafNode(otherlv_17, grammarAccess.getFunctionTypeExpressionOLDAccess().getRightCurlyBracketKeyword_9());
		}
	)
;

// Entry rule entryRuleArrowFunctionTypeExpression
entryRuleArrowFunctionTypeExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getArrowFunctionTypeExpressionRule()); }
	iv_ruleArrowFunctionTypeExpression=ruleArrowFunctionTypeExpression
	{ $current=$iv_ruleArrowFunctionTypeExpression.current; }
	EOF;

// Rule ArrowFunctionTypeExpression
ruleArrowFunctionTypeExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				LeftParenthesis
				ruleTAnonymousFormalParameterList[null]
				RightParenthesis
				EqualsSignGreaterThanSign
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getArrowFunctionTypeExpressionAccess().getFunctionTypeExpressionAction_0_0_0(),
							$current);
					}
				)
				otherlv_1=LeftParenthesis
				{
					newLeafNode(otherlv_1, grammarAccess.getArrowFunctionTypeExpressionAccess().getLeftParenthesisKeyword_0_0_1());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getArrowFunctionTypeExpressionRule());
					}
					newCompositeNode(grammarAccess.getArrowFunctionTypeExpressionAccess().getTAnonymousFormalParameterListParserRuleCall_0_0_2());
				}
				this_TAnonymousFormalParameterList_2=ruleTAnonymousFormalParameterList[$current]
				{
					$current = $this_TAnonymousFormalParameterList_2.current;
					afterParserOrEnumRuleCall();
				}
				otherlv_3=RightParenthesis
				{
					newLeafNode(otherlv_3, grammarAccess.getArrowFunctionTypeExpressionAccess().getRightParenthesisKeyword_0_0_3());
				}
				otherlv_4=EqualsSignGreaterThanSign
				{
					newLeafNode(otherlv_4, grammarAccess.getArrowFunctionTypeExpressionAccess().getEqualsSignGreaterThanSignKeyword_0_0_4());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getArrowFunctionTypeExpressionAccess().getReturnTypeRefPrimaryTypeExpressionParserRuleCall_1_0());
				}
				lv_returnTypeRef_5_0=rulePrimaryTypeExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getArrowFunctionTypeExpressionRule());
					}
					set(
						$current,
						"returnTypeRef",
						lv_returnTypeRef_5_0,
						"org.eclipse.n4js.ts.TypeExpressions.PrimaryTypeExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule TAnonymousFormalParameterList
ruleTAnonymousFormalParameterList[EObject in_current]  returns [EObject current=in_current]
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
					newCompositeNode(grammarAccess.getTAnonymousFormalParameterListAccess().getFparsTAnonymousFormalParameterParserRuleCall_0_0());
				}
				lv_fpars_0_0=ruleTAnonymousFormalParameter
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTAnonymousFormalParameterListRule());
					}
					add(
						$current,
						"fpars",
						lv_fpars_0_0,
						"org.eclipse.n4js.ts.TypeExpressions.TAnonymousFormalParameter");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_1=Comma
			{
				newLeafNode(otherlv_1, grammarAccess.getTAnonymousFormalParameterListAccess().getCommaKeyword_1_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getTAnonymousFormalParameterListAccess().getFparsTAnonymousFormalParameterParserRuleCall_1_1_0());
					}
					lv_fpars_2_0=ruleTAnonymousFormalParameter
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTAnonymousFormalParameterListRule());
						}
						add(
							$current,
							"fpars",
							lv_fpars_2_0,
							"org.eclipse.n4js.ts.TypeExpressions.TAnonymousFormalParameter");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)?
;

// Entry rule entryRuleTAnonymousFormalParameter
entryRuleTAnonymousFormalParameter returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTAnonymousFormalParameterRule()); }
	iv_ruleTAnonymousFormalParameter=ruleTAnonymousFormalParameter
	{ $current=$iv_ruleTAnonymousFormalParameter.current; }
	EOF;

// Rule TAnonymousFormalParameter
ruleTAnonymousFormalParameter returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_variadic_0_0=FullStopFullStopFullStop
				{
					newLeafNode(lv_variadic_0_0, grammarAccess.getTAnonymousFormalParameterAccess().getVariadicFullStopFullStopFullStopKeyword_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTAnonymousFormalParameterRule());
					}
					setWithLastConsumed($current, "variadic", true, "...");
				}
			)
		)?
		(
			(
				((
					(
						(
							ruleBindingIdentifier
						)
					)
					Colon
				)
				)=>
				(
					(
						(
							{
								newCompositeNode(grammarAccess.getTAnonymousFormalParameterAccess().getNameBindingIdentifierParserRuleCall_1_0_0_0_0());
							}
							lv_name_1_0=ruleBindingIdentifier
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getTAnonymousFormalParameterRule());
								}
								set(
									$current,
									"name",
									lv_name_1_0,
									"org.eclipse.n4js.ts.TypeExpressions.BindingIdentifier");
								afterParserOrEnumRuleCall();
							}
						)
					)
					(
						(Colon)=>
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getTAnonymousFormalParameterRule());
							}
							newCompositeNode(grammarAccess.getTAnonymousFormalParameterAccess().getColonSepTypeRefParserRuleCall_1_0_0_1());
						}
						this_ColonSepTypeRef_2=ruleColonSepTypeRef[$current]
						{
							$current = $this_ColonSepTypeRef_2.current;
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getTAnonymousFormalParameterAccess().getTypeRefTypeRefParserRuleCall_1_1_0());
					}
					lv_typeRef_3_0=ruleTypeRef
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTAnonymousFormalParameterRule());
						}
						set(
							$current,
							"typeRef",
							lv_typeRef_3_0,
							"org.eclipse.n4js.ts.TypeExpressions.TypeRef");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getTAnonymousFormalParameterRule());
			}
			newCompositeNode(grammarAccess.getTAnonymousFormalParameterAccess().getDefaultFormalParameterParserRuleCall_2());
		}
		this_DefaultFormalParameter_4=ruleDefaultFormalParameter[$current]
		{
			$current = $this_DefaultFormalParameter_4.current;
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule DefaultFormalParameter
ruleDefaultFormalParameter[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_hasInitializerAssignment_0_0=EqualsSign
				{
					newLeafNode(lv_hasInitializerAssignment_0_0, grammarAccess.getDefaultFormalParameterAccess().getHasInitializerAssignmentEqualsSignKeyword_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getDefaultFormalParameterRule());
					}
					setWithLastConsumed($current, "hasInitializerAssignment", true, "=");
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getDefaultFormalParameterAccess().getAstInitializerTypeReferenceNameParserRuleCall_1_0());
				}
				lv_astInitializer_1_0=ruleTypeReferenceName
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getDefaultFormalParameterRule());
					}
					set(
						$current,
						"astInitializer",
						lv_astInitializer_1_0,
						"org.eclipse.n4js.N4JS.TypeReferenceName");
					afterParserOrEnumRuleCall();
				}
			)
		)?
	)?
;

// Entry rule entryRuleUnionTypeExpressionOLD
entryRuleUnionTypeExpressionOLD returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getUnionTypeExpressionOLDRule()); }
	iv_ruleUnionTypeExpressionOLD=ruleUnionTypeExpressionOLD
	{ $current=$iv_ruleUnionTypeExpressionOLD.current; }
	EOF;

// Rule UnionTypeExpressionOLD
ruleUnionTypeExpressionOLD returns [EObject current=null]
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
					grammarAccess.getUnionTypeExpressionOLDAccess().getUnionTypeExpressionAction_0(),
					$current);
			}
		)
		otherlv_1=Union
		{
			newLeafNode(otherlv_1, grammarAccess.getUnionTypeExpressionOLDAccess().getUnionKeyword_1());
		}
		otherlv_2=LeftCurlyBracket
		{
			newLeafNode(otherlv_2, grammarAccess.getUnionTypeExpressionOLDAccess().getLeftCurlyBracketKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getUnionTypeExpressionOLDAccess().getTypeRefsTypeRefParserRuleCall_3_0());
				}
				lv_typeRefs_3_0=ruleTypeRef
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getUnionTypeExpressionOLDRule());
					}
					add(
						$current,
						"typeRefs",
						lv_typeRefs_3_0,
						"org.eclipse.n4js.ts.TypeExpressions.TypeRef");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_4=Comma
			{
				newLeafNode(otherlv_4, grammarAccess.getUnionTypeExpressionOLDAccess().getCommaKeyword_4_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getUnionTypeExpressionOLDAccess().getTypeRefsTypeRefParserRuleCall_4_1_0());
					}
					lv_typeRefs_5_0=ruleTypeRef
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getUnionTypeExpressionOLDRule());
						}
						add(
							$current,
							"typeRefs",
							lv_typeRefs_5_0,
							"org.eclipse.n4js.ts.TypeExpressions.TypeRef");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		otherlv_6=RightCurlyBracket
		{
			newLeafNode(otherlv_6, grammarAccess.getUnionTypeExpressionOLDAccess().getRightCurlyBracketKeyword_5());
		}
	)
;

// Entry rule entryRuleIntersectionTypeExpressionOLD
entryRuleIntersectionTypeExpressionOLD returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getIntersectionTypeExpressionOLDRule()); }
	iv_ruleIntersectionTypeExpressionOLD=ruleIntersectionTypeExpressionOLD
	{ $current=$iv_ruleIntersectionTypeExpressionOLD.current; }
	EOF;

// Rule IntersectionTypeExpressionOLD
ruleIntersectionTypeExpressionOLD returns [EObject current=null]
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
					grammarAccess.getIntersectionTypeExpressionOLDAccess().getIntersectionTypeExpressionAction_0(),
					$current);
			}
		)
		otherlv_1=Intersection
		{
			newLeafNode(otherlv_1, grammarAccess.getIntersectionTypeExpressionOLDAccess().getIntersectionKeyword_1());
		}
		otherlv_2=LeftCurlyBracket
		{
			newLeafNode(otherlv_2, grammarAccess.getIntersectionTypeExpressionOLDAccess().getLeftCurlyBracketKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getIntersectionTypeExpressionOLDAccess().getTypeRefsTypeRefParserRuleCall_3_0());
				}
				lv_typeRefs_3_0=ruleTypeRef
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getIntersectionTypeExpressionOLDRule());
					}
					add(
						$current,
						"typeRefs",
						lv_typeRefs_3_0,
						"org.eclipse.n4js.ts.TypeExpressions.TypeRef");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_4=Comma
			{
				newLeafNode(otherlv_4, grammarAccess.getIntersectionTypeExpressionOLDAccess().getCommaKeyword_4_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getIntersectionTypeExpressionOLDAccess().getTypeRefsTypeRefParserRuleCall_4_1_0());
					}
					lv_typeRefs_5_0=ruleTypeRef
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getIntersectionTypeExpressionOLDRule());
						}
						add(
							$current,
							"typeRefs",
							lv_typeRefs_5_0,
							"org.eclipse.n4js.ts.TypeExpressions.TypeRef");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		otherlv_6=RightCurlyBracket
		{
			newLeafNode(otherlv_6, grammarAccess.getIntersectionTypeExpressionOLDAccess().getRightCurlyBracketKeyword_5());
		}
	)
;

// Entry rule entryRuleParameterizedTypeRef
entryRuleParameterizedTypeRef returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getParameterizedTypeRefRule()); }
	iv_ruleParameterizedTypeRef=ruleParameterizedTypeRef
	{ $current=$iv_ruleParameterizedTypeRef.current; }
	EOF;

// Rule ParameterizedTypeRef
ruleParameterizedTypeRef returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getParameterizedTypeRefAccess().getParameterizedTypeRefNominalParserRuleCall_0());
		}
		this_ParameterizedTypeRefNominal_0=ruleParameterizedTypeRefNominal
		{
			$current = $this_ParameterizedTypeRefNominal_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getParameterizedTypeRefAccess().getParameterizedTypeRefStructuralParserRuleCall_1());
		}
		this_ParameterizedTypeRefStructural_1=ruleParameterizedTypeRefStructural
		{
			$current = $this_ParameterizedTypeRefStructural_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleParameterizedTypeRefNominal
entryRuleParameterizedTypeRefNominal returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getParameterizedTypeRefNominalRule()); }
	iv_ruleParameterizedTypeRefNominal=ruleParameterizedTypeRefNominal
	{ $current=$iv_ruleParameterizedTypeRefNominal.current; }
	EOF;

// Rule ParameterizedTypeRefNominal
ruleParameterizedTypeRefNominal returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getParameterizedTypeRefNominalRule());
				}
				newCompositeNode(grammarAccess.getParameterizedTypeRefNominalAccess().getTypeReferenceParserRuleCall_0_0());
			}
			this_TypeReference_0=ruleTypeReference[$current]
			{
				$current = $this_TypeReference_0.current;
				afterParserOrEnumRuleCall();
			}
			    |
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getParameterizedTypeRefNominalAccess().getVersionedParameterizedTypeRefAction_0_1_0(),
							$current);
					}
				)
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getParameterizedTypeRefNominalRule());
					}
					newCompositeNode(grammarAccess.getParameterizedTypeRefNominalAccess().getTypeReferenceParserRuleCall_0_1_1());
				}
				this_TypeReference_2=ruleTypeReference[$current]
				{
					$current = $this_TypeReference_2.current;
					afterParserOrEnumRuleCall();
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getParameterizedTypeRefNominalRule());
					}
					newCompositeNode(grammarAccess.getParameterizedTypeRefNominalAccess().getVersionRequestParserRuleCall_0_1_2());
				}
				this_VersionRequest_3=ruleVersionRequest[$current]
				{
					$current = $this_VersionRequest_3.current;
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(LessThanSign)=>
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getParameterizedTypeRefNominalRule());
				}
				newCompositeNode(grammarAccess.getParameterizedTypeRefNominalAccess().getTypeArgumentsParserRuleCall_1());
			}
			this_TypeArguments_4=ruleTypeArguments[$current]
			{
				$current = $this_TypeArguments_4.current;
				afterParserOrEnumRuleCall();
			}
		)?
	)
;

// Entry rule entryRuleParameterizedTypeRefStructural
entryRuleParameterizedTypeRefStructural returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getParameterizedTypeRefStructuralRule()); }
	iv_ruleParameterizedTypeRefStructural=ruleParameterizedTypeRefStructural
	{ $current=$iv_ruleParameterizedTypeRefStructural.current; }
	EOF;

// Rule ParameterizedTypeRefStructural
ruleParameterizedTypeRefStructural returns [EObject current=null]
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
						{
							newCompositeNode(grammarAccess.getParameterizedTypeRefStructuralAccess().getDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_0_0_0());
						}
						lv_definedTypingStrategy_0_0=ruleTypingStrategyUseSiteOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getParameterizedTypeRefStructuralRule());
							}
							set(
								$current,
								"definedTypingStrategy",
								lv_definedTypingStrategy_0_0,
								"org.eclipse.n4js.ts.TypeExpressions.TypingStrategyUseSiteOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getParameterizedTypeRefStructuralRule());
					}
					newCompositeNode(grammarAccess.getParameterizedTypeRefStructuralAccess().getTypeReferenceParserRuleCall_0_0_1());
				}
				this_TypeReference_1=ruleTypeReference[$current]
				{
					$current = $this_TypeReference_1.current;
					afterParserOrEnumRuleCall();
				}
			)
			    |
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getParameterizedTypeRefStructuralAccess().getVersionedParameterizedTypeRefStructuralAction_0_1_0(),
							$current);
					}
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getParameterizedTypeRefStructuralAccess().getDefinedTypingStrategyTypingStrategyUseSiteOperatorParserRuleCall_0_1_1_0());
						}
						lv_definedTypingStrategy_3_0=ruleTypingStrategyUseSiteOperator
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getParameterizedTypeRefStructuralRule());
							}
							set(
								$current,
								"definedTypingStrategy",
								lv_definedTypingStrategy_3_0,
								"org.eclipse.n4js.ts.TypeExpressions.TypingStrategyUseSiteOperator");
							afterParserOrEnumRuleCall();
						}
					)
				)
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getParameterizedTypeRefStructuralRule());
					}
					newCompositeNode(grammarAccess.getParameterizedTypeRefStructuralAccess().getTypeReferenceParserRuleCall_0_1_2());
				}
				this_TypeReference_4=ruleTypeReference[$current]
				{
					$current = $this_TypeReference_4.current;
					afterParserOrEnumRuleCall();
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getParameterizedTypeRefStructuralRule());
					}
					newCompositeNode(grammarAccess.getParameterizedTypeRefStructuralAccess().getVersionRequestParserRuleCall_0_1_3());
				}
				this_VersionRequest_5=ruleVersionRequest[$current]
				{
					$current = $this_VersionRequest_5.current;
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(LessThanSign)=>
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getParameterizedTypeRefStructuralRule());
				}
				newCompositeNode(grammarAccess.getParameterizedTypeRefStructuralAccess().getTypeArgumentsParserRuleCall_1());
			}
			this_TypeArguments_6=ruleTypeArguments[$current]
			{
				$current = $this_TypeArguments_6.current;
				afterParserOrEnumRuleCall();
			}
		)?
		(
			otherlv_7=With
			{
				newLeafNode(otherlv_7, grammarAccess.getParameterizedTypeRefStructuralAccess().getWithKeyword_2_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getParameterizedTypeRefStructuralRule());
				}
				newCompositeNode(grammarAccess.getParameterizedTypeRefStructuralAccess().getTStructMemberListParserRuleCall_2_1());
			}
			this_TStructMemberList_8=ruleTStructMemberList[$current]
			{
				$current = $this_TStructMemberList_8.current;
				afterParserOrEnumRuleCall();
			}
		)?
	)
;

// Entry rule entryRuleIterableTypeExpression
entryRuleIterableTypeExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getIterableTypeExpressionRule()); }
	iv_ruleIterableTypeExpression=ruleIterableTypeExpression
	{ $current=$iv_ruleIterableTypeExpression.current; }
	EOF;

// Rule IterableTypeExpression
ruleIterableTypeExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				lv_iterableTypeExpression_0_0=LeftSquareBracket
				{
					newLeafNode(lv_iterableTypeExpression_0_0, grammarAccess.getIterableTypeExpressionAccess().getIterableTypeExpressionLeftSquareBracketKeyword_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getIterableTypeExpressionRule());
					}
					setWithLastConsumed($current, "iterableTypeExpression", true, "[");
				}
			)
		)
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getIterableTypeExpressionAccess().getTypeArgsEmptyIterableTypeExpressionTailParserRuleCall_1_0_0());
					}
					lv_typeArgs_1_0=ruleEmptyIterableTypeExpressionTail
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getIterableTypeExpressionRule());
						}
						add(
							$current,
							"typeArgs",
							lv_typeArgs_1_0,
							"org.eclipse.n4js.ts.TypeExpressions.EmptyIterableTypeExpressionTail");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getIterableTypeExpressionAccess().getTypeArgsTypeArgumentParserRuleCall_1_1_0_0());
						}
						lv_typeArgs_2_0=ruleTypeArgument
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getIterableTypeExpressionRule());
							}
							add(
								$current,
								"typeArgs",
								lv_typeArgs_2_0,
								"org.eclipse.n4js.ts.TypeExpressions.TypeArgument");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					otherlv_3=Comma
					{
						newLeafNode(otherlv_3, grammarAccess.getIterableTypeExpressionAccess().getCommaKeyword_1_1_1_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getIterableTypeExpressionAccess().getTypeArgsTypeArgumentParserRuleCall_1_1_1_1_0());
							}
							lv_typeArgs_4_0=ruleTypeArgument
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getIterableTypeExpressionRule());
								}
								add(
									$current,
									"typeArgs",
									lv_typeArgs_4_0,
									"org.eclipse.n4js.ts.TypeExpressions.TypeArgument");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)*
				otherlv_5=RightSquareBracket
				{
					newLeafNode(otherlv_5, grammarAccess.getIterableTypeExpressionAccess().getRightSquareBracketKeyword_1_1_2());
				}
			)
		)
	)
;

// Entry rule entryRuleEmptyIterableTypeExpressionTail
entryRuleEmptyIterableTypeExpressionTail returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getEmptyIterableTypeExpressionTailRule()); }
	iv_ruleEmptyIterableTypeExpressionTail=ruleEmptyIterableTypeExpressionTail
	{ $current=$iv_ruleEmptyIterableTypeExpressionTail.current; }
	EOF;

// Rule EmptyIterableTypeExpressionTail
ruleEmptyIterableTypeExpressionTail returns [EObject current=null]
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
					grammarAccess.getEmptyIterableTypeExpressionTailAccess().getWildcardAction_0(),
					$current);
			}
		)
		otherlv_1=RightSquareBracket
		{
			newLeafNode(otherlv_1, grammarAccess.getEmptyIterableTypeExpressionTailAccess().getRightSquareBracketKeyword_1());
		}
	)
;


// Rule VersionRequest
ruleVersionRequest[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_requestedVersion_0_0=RULE_VERSION
			{
				newLeafNode(lv_requestedVersion_0_0, grammarAccess.getVersionRequestAccess().getRequestedVersionVERSIONTerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getVersionRequestRule());
				}
				setWithLastConsumed(
					$current,
					"requestedVersion",
					lv_requestedVersion_0_0,
					"org.eclipse.n4js.ts.TypeExpressions.VERSION");
			}
		)
	)
;


// Rule TypeArguments
ruleTypeArguments[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LessThanSign
		{
			newLeafNode(otherlv_0, grammarAccess.getTypeArgumentsAccess().getLessThanSignKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getTypeArgumentsAccess().getTypeArgsTypeArgumentParserRuleCall_1_0());
				}
				lv_typeArgs_1_0=ruleTypeArgument
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTypeArgumentsRule());
					}
					add(
						$current,
						"typeArgs",
						lv_typeArgs_1_0,
						"org.eclipse.n4js.ts.TypeExpressions.TypeArgument");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_2=Comma
			{
				newLeafNode(otherlv_2, grammarAccess.getTypeArgumentsAccess().getCommaKeyword_2_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getTypeArgumentsAccess().getTypeArgsTypeArgumentParserRuleCall_2_1_0());
					}
					lv_typeArgs_3_0=ruleTypeArgument
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTypeArgumentsRule());
						}
						add(
							$current,
							"typeArgs",
							lv_typeArgs_3_0,
							"org.eclipse.n4js.ts.TypeExpressions.TypeArgument");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		otherlv_4=GreaterThanSign
		{
			newLeafNode(otherlv_4, grammarAccess.getTypeArgumentsAccess().getGreaterThanSignKeyword_3());
		}
	)
;


// Rule TStructMemberList
ruleTStructMemberList[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LeftCurlyBracket
		{
			newLeafNode(otherlv_0, grammarAccess.getTStructMemberListAccess().getLeftCurlyBracketKeyword_0());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getTStructMemberListAccess().getAstStructuralMembersTStructMemberParserRuleCall_1_0_0());
					}
					lv_astStructuralMembers_1_0=ruleTStructMember
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTStructMemberListRule());
						}
						add(
							$current,
							"astStructuralMembers",
							lv_astStructuralMembers_1_0,
							"org.eclipse.n4js.ts.TypeExpressions.TStructMember");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_2=Semicolon
				{
					newLeafNode(otherlv_2, grammarAccess.getTStructMemberListAccess().getSemicolonKeyword_1_1_0());
				}
				    |
				otherlv_3=Comma
				{
					newLeafNode(otherlv_3, grammarAccess.getTStructMemberListAccess().getCommaKeyword_1_1_1());
				}
			)?
		)*
		otherlv_4=RightCurlyBracket
		{
			newLeafNode(otherlv_4, grammarAccess.getTStructMemberListAccess().getRightCurlyBracketKeyword_2());
		}
	)
;

// Entry rule entryRuleTStructMember
entryRuleTStructMember returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTStructMemberRule()); }
	iv_ruleTStructMember=ruleTStructMember
	{ $current=$iv_ruleTStructMember.current; }
	EOF;

// Rule TStructMember
ruleTStructMember returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				Get
				(
					(
						ruleIdentifierName
					)
				)
			)
			)=>
			{
				newCompositeNode(grammarAccess.getTStructMemberAccess().getTStructGetterParserRuleCall_0());
			}
			this_TStructGetter_0=ruleTStructGetter
			{
				$current = $this_TStructGetter_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				Set
				(
					(
						ruleIdentifierName
					)
				)
			)
			)=>
			{
				newCompositeNode(grammarAccess.getTStructMemberAccess().getTStructSetterParserRuleCall_1());
			}
			this_TStructSetter_1=ruleTStructSetter
			{
				$current = $this_TStructSetter_1.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
				)
				(
					ruleTypeVariables[null]
				)?
				(
					(
						ruleIdentifierName
					)
				)
				LeftParenthesis
			)
			)=>
			{
				newCompositeNode(grammarAccess.getTStructMemberAccess().getTStructMethodParserRuleCall_2());
			}
			this_TStructMethod_2=ruleTStructMethod
			{
				$current = $this_TStructMethod_2.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getTStructMemberAccess().getTStructFieldParserRuleCall_3());
		}
		this_TStructField_3=ruleTStructField
		{
			$current = $this_TStructField_3.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleTStructMethod
entryRuleTStructMethod returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTStructMethodRule()); }
	iv_ruleTStructMethod=ruleTStructMethod
	{ $current=$iv_ruleTStructMethod.current; }
	EOF;

// Rule TStructMethod
ruleTStructMethod returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				(
					ruleTypeVariables[null]
				)?
				(
					(
						ruleIdentifierName
					)
				)
				LeftParenthesis
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getTStructMethodAccess().getTStructMethodAction_0_0_0(),
							$current);
					}
				)
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getTStructMethodRule());
						}
						newCompositeNode(grammarAccess.getTStructMethodAccess().getTypeVariablesParserRuleCall_0_0_1());
					}
					this_TypeVariables_1=ruleTypeVariables[$current]
					{
						$current = $this_TypeVariables_1.current;
						afterParserOrEnumRuleCall();
					}
				)?
				(
					(
						{
							newCompositeNode(grammarAccess.getTStructMethodAccess().getNameIdentifierNameParserRuleCall_0_0_2_0());
						}
						lv_name_2_0=ruleIdentifierName
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTStructMethodRule());
							}
							set(
								$current,
								"name",
								lv_name_2_0,
								"org.eclipse.n4js.ts.TypeExpressions.IdentifierName");
							afterParserOrEnumRuleCall();
						}
					)
				)
				otherlv_3=LeftParenthesis
				{
					newLeafNode(otherlv_3, grammarAccess.getTStructMethodAccess().getLeftParenthesisKeyword_0_0_3());
				}
			)
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getTStructMethodRule());
			}
			newCompositeNode(grammarAccess.getTStructMethodAccess().getTAnonymousFormalParameterListParserRuleCall_1());
		}
		this_TAnonymousFormalParameterList_4=ruleTAnonymousFormalParameterList[$current]
		{
			$current = $this_TAnonymousFormalParameterList_4.current;
			afterParserOrEnumRuleCall();
		}
		otherlv_5=RightParenthesis
		{
			newLeafNode(otherlv_5, grammarAccess.getTStructMethodAccess().getRightParenthesisKeyword_2());
		}
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getTStructMethodRule());
				}
				newCompositeNode(grammarAccess.getTStructMethodAccess().getColonSepReturnTypeRefParserRuleCall_3());
			}
			this_ColonSepReturnTypeRef_6=ruleColonSepReturnTypeRef[$current]
			{
				$current = $this_ColonSepReturnTypeRef_6.current;
				afterParserOrEnumRuleCall();
			}
		)?
	)
;


// Rule TypeVariables
ruleTypeVariables[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LessThanSign
		{
			newLeafNode(otherlv_0, grammarAccess.getTypeVariablesAccess().getLessThanSignKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getTypeVariablesAccess().getTypeVarsTypeVariableParserRuleCall_1_0());
				}
				lv_typeVars_1_0=ruleTypeVariable
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTypeVariablesRule());
					}
					add(
						$current,
						"typeVars",
						lv_typeVars_1_0,
						"org.eclipse.n4js.N4JS.TypeVariable");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_2=Comma
			{
				newLeafNode(otherlv_2, grammarAccess.getTypeVariablesAccess().getCommaKeyword_2_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getTypeVariablesAccess().getTypeVarsTypeVariableParserRuleCall_2_1_0());
					}
					lv_typeVars_3_0=ruleTypeVariable
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTypeVariablesRule());
						}
						add(
							$current,
							"typeVars",
							lv_typeVars_3_0,
							"org.eclipse.n4js.N4JS.TypeVariable");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		otherlv_4=GreaterThanSign
		{
			newLeafNode(otherlv_4, grammarAccess.getTypeVariablesAccess().getGreaterThanSignKeyword_3());
		}
	)
;


// Rule ColonSepDeclaredTypeRef
ruleColonSepDeclaredTypeRef[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Colon
		{
			newLeafNode(otherlv_0, grammarAccess.getColonSepDeclaredTypeRefAccess().getColonKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getColonSepDeclaredTypeRefAccess().getDeclaredTypeRefTypeRefParserRuleCall_1_0());
				}
				lv_declaredTypeRef_1_0=ruleTypeRef
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getColonSepDeclaredTypeRefRule());
					}
					set(
						$current,
						"declaredTypeRef",
						lv_declaredTypeRef_1_0,
						"org.eclipse.n4js.ts.TypeExpressions.TypeRef");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule ColonSepTypeRef
ruleColonSepTypeRef[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Colon
		{
			newLeafNode(otherlv_0, grammarAccess.getColonSepTypeRefAccess().getColonKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getColonSepTypeRefAccess().getTypeRefTypeRefParserRuleCall_1_0());
				}
				lv_typeRef_1_0=ruleTypeRef
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getColonSepTypeRefRule());
					}
					set(
						$current,
						"typeRef",
						lv_typeRef_1_0,
						"org.eclipse.n4js.ts.TypeExpressions.TypeRef");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;


// Rule ColonSepReturnTypeRef
ruleColonSepReturnTypeRef[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Colon
		{
			newLeafNode(otherlv_0, grammarAccess.getColonSepReturnTypeRefAccess().getColonKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getColonSepReturnTypeRefAccess().getReturnTypeRefTypeRefParserRuleCall_1_0());
				}
				lv_returnTypeRef_1_0=ruleTypeRef
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getColonSepReturnTypeRefRule());
					}
					set(
						$current,
						"returnTypeRef",
						lv_returnTypeRef_1_0,
						"org.eclipse.n4js.ts.TypeExpressions.TypeRef");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleTStructField
entryRuleTStructField returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTStructFieldRule()); }
	iv_ruleTStructField=ruleTStructField
	{ $current=$iv_ruleTStructField.current; }
	EOF;

// Rule TStructField
ruleTStructField returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getTStructFieldAccess().getNameIdentifierNameParserRuleCall_0_0());
				}
				lv_name_0_0=ruleIdentifierName
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTStructFieldRule());
					}
					set(
						$current,
						"name",
						lv_name_0_0,
						"org.eclipse.n4js.ts.TypeExpressions.IdentifierName");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				lv_optional_1_0=QuestionMark
				{
					newLeafNode(lv_optional_1_0, grammarAccess.getTStructFieldAccess().getOptionalQuestionMarkKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTStructFieldRule());
					}
					setWithLastConsumed($current, "optional", true, "?");
				}
			)
		)?
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getTStructFieldRule());
				}
				newCompositeNode(grammarAccess.getTStructFieldAccess().getColonSepTypeRefParserRuleCall_2());
			}
			this_ColonSepTypeRef_2=ruleColonSepTypeRef[$current]
			{
				$current = $this_ColonSepTypeRef_2.current;
				afterParserOrEnumRuleCall();
			}
		)?
	)
;

// Entry rule entryRuleTStructGetter
entryRuleTStructGetter returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTStructGetterRule()); }
	iv_ruleTStructGetter=ruleTStructGetter
	{ $current=$iv_ruleTStructGetter.current; }
	EOF;

// Rule TStructGetter
ruleTStructGetter returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				Get
				(
					(
						ruleIdentifierName
					)
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getTStructGetterAccess().getTStructGetterAction_0_0_0(),
							$current);
					}
				)
				otherlv_1=Get
				{
					newLeafNode(otherlv_1, grammarAccess.getTStructGetterAccess().getGetKeyword_0_0_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getTStructGetterAccess().getNameIdentifierNameParserRuleCall_0_0_2_0());
						}
						lv_name_2_0=ruleIdentifierName
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTStructGetterRule());
							}
							set(
								$current,
								"name",
								lv_name_2_0,
								"org.eclipse.n4js.ts.TypeExpressions.IdentifierName");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)
		(
			(
				lv_optional_3_0=QuestionMark
				{
					newLeafNode(lv_optional_3_0, grammarAccess.getTStructGetterAccess().getOptionalQuestionMarkKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTStructGetterRule());
					}
					setWithLastConsumed($current, "optional", true, "?");
				}
			)
		)?
		otherlv_4=LeftParenthesis
		{
			newLeafNode(otherlv_4, grammarAccess.getTStructGetterAccess().getLeftParenthesisKeyword_2());
		}
		otherlv_5=RightParenthesis
		{
			newLeafNode(otherlv_5, grammarAccess.getTStructGetterAccess().getRightParenthesisKeyword_3());
		}
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getTStructGetterRule());
				}
				newCompositeNode(grammarAccess.getTStructGetterAccess().getColonSepDeclaredTypeRefParserRuleCall_4());
			}
			this_ColonSepDeclaredTypeRef_6=ruleColonSepDeclaredTypeRef[$current]
			{
				$current = $this_ColonSepDeclaredTypeRef_6.current;
				afterParserOrEnumRuleCall();
			}
		)?
	)
;

// Entry rule entryRuleTStructSetter
entryRuleTStructSetter returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTStructSetterRule()); }
	iv_ruleTStructSetter=ruleTStructSetter
	{ $current=$iv_ruleTStructSetter.current; }
	EOF;

// Rule TStructSetter
ruleTStructSetter returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				Set
				(
					(
						ruleIdentifierName
					)
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getTStructSetterAccess().getTStructSetterAction_0_0_0(),
							$current);
					}
				)
				otherlv_1=Set
				{
					newLeafNode(otherlv_1, grammarAccess.getTStructSetterAccess().getSetKeyword_0_0_1());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getTStructSetterAccess().getNameIdentifierNameParserRuleCall_0_0_2_0());
						}
						lv_name_2_0=ruleIdentifierName
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTStructSetterRule());
							}
							set(
								$current,
								"name",
								lv_name_2_0,
								"org.eclipse.n4js.ts.TypeExpressions.IdentifierName");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)
		(
			(
				lv_optional_3_0=QuestionMark
				{
					newLeafNode(lv_optional_3_0, grammarAccess.getTStructSetterAccess().getOptionalQuestionMarkKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTStructSetterRule());
					}
					setWithLastConsumed($current, "optional", true, "?");
				}
			)
		)?
		otherlv_4=LeftParenthesis
		{
			newLeafNode(otherlv_4, grammarAccess.getTStructSetterAccess().getLeftParenthesisKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getTStructSetterAccess().getFparTAnonymousFormalParameterParserRuleCall_3_0());
				}
				lv_fpar_5_0=ruleTAnonymousFormalParameter
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTStructSetterRule());
					}
					set(
						$current,
						"fpar",
						lv_fpar_5_0,
						"org.eclipse.n4js.ts.TypeExpressions.TAnonymousFormalParameter");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_6=RightParenthesis
		{
			newLeafNode(otherlv_6, grammarAccess.getTStructSetterAccess().getRightParenthesisKeyword_4());
		}
	)
;

// Entry rule entryRuleTypingStrategyUseSiteOperator
entryRuleTypingStrategyUseSiteOperator returns [String current=null]:
	{ newCompositeNode(grammarAccess.getTypingStrategyUseSiteOperatorRule()); }
	iv_ruleTypingStrategyUseSiteOperator=ruleTypingStrategyUseSiteOperator
	{ $current=$iv_ruleTypingStrategyUseSiteOperator.current.getText(); }
	EOF;

// Rule TypingStrategyUseSiteOperator
ruleTypingStrategyUseSiteOperator returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw=Tilde
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypingStrategyUseSiteOperatorAccess().getTildeKeyword_0());
		}
		(
			kw=Tilde
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getTypingStrategyUseSiteOperatorAccess().getTildeKeyword_1_0());
			}
			    |
			this_STRUCTMODSUFFIX_2=RULE_STRUCTMODSUFFIX
			{
				$current.merge(this_STRUCTMODSUFFIX_2);
			}
			{
				newLeafNode(this_STRUCTMODSUFFIX_2, grammarAccess.getTypingStrategyUseSiteOperatorAccess().getSTRUCTMODSUFFIXTerminalRuleCall_1_1());
			}
		)?
	)
;

// Entry rule entryRuleTypingStrategyDefSiteOperator
entryRuleTypingStrategyDefSiteOperator returns [String current=null]:
	{ newCompositeNode(grammarAccess.getTypingStrategyDefSiteOperatorRule()); }
	iv_ruleTypingStrategyDefSiteOperator=ruleTypingStrategyDefSiteOperator
	{ $current=$iv_ruleTypingStrategyDefSiteOperator.current.getText(); }
	EOF;

// Rule TypingStrategyDefSiteOperator
ruleTypingStrategyDefSiteOperator returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	kw=Tilde
	{
		$current.merge(kw);
		newLeafNode(kw, grammarAccess.getTypingStrategyDefSiteOperatorAccess().getTildeKeyword());
	}
;

// Entry rule entryRuleTypeTypeRef
entryRuleTypeTypeRef returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTypeTypeRefRule()); }
	iv_ruleTypeTypeRef=ruleTypeTypeRef
	{ $current=$iv_ruleTypeTypeRef.current; }
	EOF;

// Rule TypeTypeRef
ruleTypeTypeRef returns [EObject current=null]
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
					grammarAccess.getTypeTypeRefAccess().getTypeTypeRefAction_0(),
					$current);
			}
		)
		(
			otherlv_1=Type
			{
				newLeafNode(otherlv_1, grammarAccess.getTypeTypeRefAccess().getTypeKeyword_1_0());
			}
			    |
			(
				(
					lv_constructorRef_2_0=Constructor
					{
						newLeafNode(lv_constructorRef_2_0, grammarAccess.getTypeTypeRefAccess().getConstructorRefConstructorKeyword_1_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getTypeTypeRefRule());
						}
						setWithLastConsumed($current, "constructorRef", true, "constructor");
					}
				)
			)
		)
		otherlv_3=LeftCurlyBracket
		{
			newLeafNode(otherlv_3, grammarAccess.getTypeTypeRefAccess().getLeftCurlyBracketKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getTypeTypeRefAccess().getTypeArgTypeArgInTypeTypeRefParserRuleCall_3_0());
				}
				lv_typeArg_4_0=ruleTypeArgInTypeTypeRef
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTypeTypeRefRule());
					}
					set(
						$current,
						"typeArg",
						lv_typeArg_4_0,
						"org.eclipse.n4js.ts.TypeExpressions.TypeArgInTypeTypeRef");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_5=RightCurlyBracket
		{
			newLeafNode(otherlv_5, grammarAccess.getTypeTypeRefAccess().getRightCurlyBracketKeyword_4());
		}
	)
;

// Entry rule entryRuleTypeArgument
entryRuleTypeArgument returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTypeArgumentRule()); }
	iv_ruleTypeArgument=ruleTypeArgument
	{ $current=$iv_ruleTypeArgument.current; }
	EOF;

// Rule TypeArgument
ruleTypeArgument returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getTypeArgumentAccess().getWildcardParserRuleCall_0());
		}
		this_Wildcard_0=ruleWildcard
		{
			$current = $this_Wildcard_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeArgumentAccess().getTypeRefParserRuleCall_1());
		}
		this_TypeRef_1=ruleTypeRef
		{
			$current = $this_TypeRef_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleWildcard
entryRuleWildcard returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getWildcardRule()); }
	iv_ruleWildcard=ruleWildcard
	{ $current=$iv_ruleWildcard.current; }
	EOF;

// Rule Wildcard
ruleWildcard returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				QuestionMark
			)
			)=>
			{
				newCompositeNode(grammarAccess.getWildcardAccess().getWildcardOldNotationParserRuleCall_0());
			}
			this_WildcardOldNotation_0=ruleWildcardOldNotation
			{
				$current = $this_WildcardOldNotation_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getWildcardAccess().getWildcardNewNotationParserRuleCall_1());
		}
		this_WildcardNewNotation_1=ruleWildcardNewNotation
		{
			$current = $this_WildcardNewNotation_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleWildcardOldNotation
entryRuleWildcardOldNotation returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getWildcardOldNotationRule()); }
	iv_ruleWildcardOldNotation=ruleWildcardOldNotation
	{ $current=$iv_ruleWildcardOldNotation.current; }
	EOF;

// Rule WildcardOldNotation
ruleWildcardOldNotation returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				QuestionMark
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getWildcardOldNotationAccess().getWildcardAction_0_0_0(),
							$current);
					}
				)
				otherlv_1=QuestionMark
				{
					newLeafNode(otherlv_1, grammarAccess.getWildcardOldNotationAccess().getQuestionMarkKeyword_0_0_1());
				}
			)
		)
		(
			(
				otherlv_2=Extends
				{
					newLeafNode(otherlv_2, grammarAccess.getWildcardOldNotationAccess().getExtendsKeyword_1_0_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getWildcardOldNotationAccess().getDeclaredUpperBoundTypeRefParserRuleCall_1_0_1_0());
						}
						lv_declaredUpperBound_3_0=ruleTypeRef
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getWildcardOldNotationRule());
							}
							set(
								$current,
								"declaredUpperBound",
								lv_declaredUpperBound_3_0,
								"org.eclipse.n4js.ts.TypeExpressions.TypeRef");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			    |
			(
				otherlv_4=Super
				{
					newLeafNode(otherlv_4, grammarAccess.getWildcardOldNotationAccess().getSuperKeyword_1_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getWildcardOldNotationAccess().getDeclaredLowerBoundTypeRefParserRuleCall_1_1_1_0());
						}
						lv_declaredLowerBound_5_0=ruleTypeRef
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getWildcardOldNotationRule());
							}
							set(
								$current,
								"declaredLowerBound",
								lv_declaredLowerBound_5_0,
								"org.eclipse.n4js.ts.TypeExpressions.TypeRef");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)?
	)
;

// Entry rule entryRuleWildcardOldNotationWithoutBound
entryRuleWildcardOldNotationWithoutBound returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getWildcardOldNotationWithoutBoundRule()); }
	iv_ruleWildcardOldNotationWithoutBound=ruleWildcardOldNotationWithoutBound
	{ $current=$iv_ruleWildcardOldNotationWithoutBound.current; }
	EOF;

// Rule WildcardOldNotationWithoutBound
ruleWildcardOldNotationWithoutBound returns [EObject current=null]
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
					grammarAccess.getWildcardOldNotationWithoutBoundAccess().getWildcardAction_0(),
					$current);
			}
		)
		otherlv_1=QuestionMark
		{
			newLeafNode(otherlv_1, grammarAccess.getWildcardOldNotationWithoutBoundAccess().getQuestionMarkKeyword_1());
		}
	)
;

// Entry rule entryRuleWildcardNewNotation
entryRuleWildcardNewNotation returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getWildcardNewNotationRule()); }
	iv_ruleWildcardNewNotation=ruleWildcardNewNotation
	{ $current=$iv_ruleWildcardNewNotation.current; }
	EOF;

// Rule WildcardNewNotation
ruleWildcardNewNotation returns [EObject current=null]
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
					lv_usingInOutNotation_0_0=Out
					{
						newLeafNode(lv_usingInOutNotation_0_0, grammarAccess.getWildcardNewNotationAccess().getUsingInOutNotationOutKeyword_0_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getWildcardNewNotationRule());
						}
						setWithLastConsumed($current, "usingInOutNotation", true, "out");
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getWildcardNewNotationAccess().getDeclaredUpperBoundTypeRefParserRuleCall_0_1_0());
					}
					lv_declaredUpperBound_1_0=ruleTypeRef
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getWildcardNewNotationRule());
						}
						set(
							$current,
							"declaredUpperBound",
							lv_declaredUpperBound_1_0,
							"org.eclipse.n4js.ts.TypeExpressions.TypeRef");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		    |
		(
			(
				(
					lv_usingInOutNotation_2_0=In
					{
						newLeafNode(lv_usingInOutNotation_2_0, grammarAccess.getWildcardNewNotationAccess().getUsingInOutNotationInKeyword_1_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getWildcardNewNotationRule());
						}
						setWithLastConsumed($current, "usingInOutNotation", true, "in");
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getWildcardNewNotationAccess().getDeclaredLowerBoundTypeRefParserRuleCall_1_1_0());
					}
					lv_declaredLowerBound_3_0=ruleTypeRef
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getWildcardNewNotationRule());
						}
						set(
							$current,
							"declaredLowerBound",
							lv_declaredLowerBound_3_0,
							"org.eclipse.n4js.ts.TypeExpressions.TypeRef");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;

// Entry rule entryRuleBindingIdentifier
entryRuleBindingIdentifier returns [String current=null]:
	{ newCompositeNode(grammarAccess.getBindingIdentifierRule()); }
	iv_ruleBindingIdentifier=ruleBindingIdentifier
	{ $current=$iv_ruleBindingIdentifier.current.getText(); }
	EOF;

// Rule BindingIdentifier
ruleBindingIdentifier returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_IDENTIFIER_0=RULE_IDENTIFIER
		{
			$current.merge(this_IDENTIFIER_0);
		}
		{
			newLeafNode(this_IDENTIFIER_0, grammarAccess.getBindingIdentifierAccess().getIDENTIFIERTerminalRuleCall_0());
		}
		    |
		kw=Yield
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getBindingIdentifierAccess().getYieldKeyword_1_0());
		}
		    |
		{
			newCompositeNode(grammarAccess.getBindingIdentifierAccess().getN4KeywordParserRuleCall_2());
		}
		this_N4Keyword_2=ruleN4Keyword
		{
			$current.merge(this_N4Keyword_2);
		}
		{
			afterParserOrEnumRuleCall();
		}
	)
;


// Rule BindingIdentifier
norm1_BindingIdentifier returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_IDENTIFIER_0=RULE_IDENTIFIER
		{
			$current.merge(this_IDENTIFIER_0);
		}
		{
			newLeafNode(this_IDENTIFIER_0, grammarAccess.getBindingIdentifierAccess().getIDENTIFIERTerminalRuleCall_0());
		}
		    |
		{
			newCompositeNode(grammarAccess.getBindingIdentifierAccess().getN4KeywordParserRuleCall_2());
		}
		this_N4Keyword_1=ruleN4Keyword
		{
			$current.merge(this_N4Keyword_1);
		}
		{
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleIdentifierName
entryRuleIdentifierName returns [String current=null]:
	{ newCompositeNode(grammarAccess.getIdentifierNameRule()); }
	iv_ruleIdentifierName=ruleIdentifierName
	{ $current=$iv_ruleIdentifierName.current.getText(); }
	EOF;

// Rule IdentifierName
ruleIdentifierName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		this_IDENTIFIER_0=RULE_IDENTIFIER
		{
			$current.merge(this_IDENTIFIER_0);
		}
		{
			newLeafNode(this_IDENTIFIER_0, grammarAccess.getIdentifierNameAccess().getIDENTIFIERTerminalRuleCall_0());
		}
		    |
		{
			newCompositeNode(grammarAccess.getIdentifierNameAccess().getReservedWordParserRuleCall_1());
		}
		this_ReservedWord_1=ruleReservedWord
		{
			$current.merge(this_ReservedWord_1);
		}
		{
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getIdentifierNameAccess().getN4KeywordParserRuleCall_2());
		}
		this_N4Keyword_2=ruleN4Keyword
		{
			$current.merge(this_N4Keyword_2);
		}
		{
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleReservedWord
entryRuleReservedWord returns [String current=null]:
	{ newCompositeNode(grammarAccess.getReservedWordRule()); }
	iv_ruleReservedWord=ruleReservedWord
	{ $current=$iv_ruleReservedWord.current.getText(); }
	EOF;

// Rule ReservedWord
ruleReservedWord returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw=Break
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getBreakKeyword_0());
		}
		    |
		kw=Case
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getCaseKeyword_1());
		}
		    |
		kw=Catch
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getCatchKeyword_2());
		}
		    |
		kw=Class
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getClassKeyword_3());
		}
		    |
		kw=Const
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getConstKeyword_4());
		}
		    |
		kw=Continue
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getContinueKeyword_5());
		}
		    |
		kw=Debugger
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getDebuggerKeyword_6());
		}
		    |
		kw=Default
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getDefaultKeyword_7());
		}
		    |
		kw=Delete
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getDeleteKeyword_8());
		}
		    |
		kw=Do
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getDoKeyword_9());
		}
		    |
		kw=Else
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getElseKeyword_10());
		}
		    |
		kw=Export
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getExportKeyword_11());
		}
		    |
		kw=Extends
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getExtendsKeyword_12());
		}
		    |
		kw=Finally
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getFinallyKeyword_13());
		}
		    |
		kw=For
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getForKeyword_14());
		}
		    |
		kw=Function
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getFunctionKeyword_15());
		}
		    |
		kw=If
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getIfKeyword_16());
		}
		    |
		kw=Import
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getImportKeyword_17());
		}
		    |
		kw=In
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getInKeyword_18());
		}
		    |
		kw=Instanceof
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getInstanceofKeyword_19());
		}
		    |
		kw=New
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getNewKeyword_20());
		}
		    |
		kw=Return
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getReturnKeyword_21());
		}
		    |
		kw=Super
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getSuperKeyword_22());
		}
		    |
		kw=Switch
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getSwitchKeyword_23());
		}
		    |
		kw=This_1
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getThisKeyword_24());
		}
		    |
		kw=Throw
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getThrowKeyword_25());
		}
		    |
		kw=Try
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getTryKeyword_26());
		}
		    |
		kw=Typeof
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getTypeofKeyword_27());
		}
		    |
		kw=Var
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getVarKeyword_28());
		}
		    |
		kw=Void
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getVoidKeyword_29());
		}
		    |
		kw=While
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getWhileKeyword_30());
		}
		    |
		kw=With
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getWithKeyword_31());
		}
		    |
		kw=Yield
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getYieldKeyword_32());
		}
		    |
		kw=Null
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getNullKeyword_33());
		}
		    |
		kw=True
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getTrueKeyword_34());
		}
		    |
		kw=False
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getFalseKeyword_35());
		}
		    |
		kw=Enum
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getReservedWordAccess().getEnumKeyword_36());
		}
	)
;

// Entry rule entryRuleN4Keyword
entryRuleN4Keyword returns [String current=null]:
	{ newCompositeNode(grammarAccess.getN4KeywordRule()); }
	iv_ruleN4Keyword=ruleN4Keyword
	{ $current=$iv_ruleN4Keyword.current.getText(); }
	EOF;

// Rule N4Keyword
ruleN4Keyword returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw=Get
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getGetKeyword_0());
		}
		    |
		kw=Set
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getSetKeyword_1());
		}
		    |
		kw=Let
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getLetKeyword_2());
		}
		    |
		kw=Project
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getProjectKeyword_3());
		}
		    |
		kw=External
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getExternalKeyword_4());
		}
		    |
		kw=Abstract
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getAbstractKeyword_5());
		}
		    |
		kw=Static
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getStaticKeyword_6());
		}
		    |
		kw=As
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getAsKeyword_7());
		}
		    |
		kw=From
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getFromKeyword_8());
		}
		    |
		kw=Constructor
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getConstructorKeyword_9());
		}
		    |
		kw=Of
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getOfKeyword_10());
		}
		    |
		kw=Target
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getTargetKeyword_11());
		}
		    |
		kw=Type
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getTypeKeyword_12());
		}
		    |
		kw=Union
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getUnionKeyword_13());
		}
		    |
		kw=Intersection
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getIntersectionKeyword_14());
		}
		    |
		kw=This
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getThisKeyword_15());
		}
		    |
		kw=Promisify
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getPromisifyKeyword_16());
		}
		    |
		kw=Await
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getAwaitKeyword_17());
		}
		    |
		kw=Async
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getAsyncKeyword_18());
		}
		    |
		kw=Implements
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getImplementsKeyword_19());
		}
		    |
		kw=Interface
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getInterfaceKeyword_20());
		}
		    |
		kw=Private
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getPrivateKeyword_21());
		}
		    |
		kw=Protected
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getProtectedKeyword_22());
		}
		    |
		kw=Public
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getPublicKeyword_23());
		}
		    |
		kw=Out
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getN4KeywordAccess().getOutKeyword_24());
		}
	)
;

// Rule VariableStatementKeyword
ruleVariableStatementKeyword returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0=Var
			{
				$current = grammarAccess.getVariableStatementKeywordAccess().getVarEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getVariableStatementKeywordAccess().getVarEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=Const
			{
				$current = grammarAccess.getVariableStatementKeywordAccess().getConstEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getVariableStatementKeywordAccess().getConstEnumLiteralDeclaration_1());
			}
		)
		    |
		(
			enumLiteral_2=Let
			{
				$current = grammarAccess.getVariableStatementKeywordAccess().getLetEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_2, grammarAccess.getVariableStatementKeywordAccess().getLetEnumLiteralDeclaration_2());
			}
		)
	)
;

// Rule PostfixOperator
rulePostfixOperator returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0=PlusSignPlusSign
			{
				$current = grammarAccess.getPostfixOperatorAccess().getIncEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getPostfixOperatorAccess().getIncEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=HyphenMinusHyphenMinus
			{
				$current = grammarAccess.getPostfixOperatorAccess().getDecEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getPostfixOperatorAccess().getDecEnumLiteralDeclaration_1());
			}
		)
	)
;

// Rule UnaryOperator
ruleUnaryOperator returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0=Delete
			{
				$current = grammarAccess.getUnaryOperatorAccess().getDeleteEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getUnaryOperatorAccess().getDeleteEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=Void
			{
				$current = grammarAccess.getUnaryOperatorAccess().getVoidEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getUnaryOperatorAccess().getVoidEnumLiteralDeclaration_1());
			}
		)
		    |
		(
			enumLiteral_2=Typeof
			{
				$current = grammarAccess.getUnaryOperatorAccess().getTypeofEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_2, grammarAccess.getUnaryOperatorAccess().getTypeofEnumLiteralDeclaration_2());
			}
		)
		    |
		(
			enumLiteral_3=PlusSignPlusSign
			{
				$current = grammarAccess.getUnaryOperatorAccess().getIncEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_3, grammarAccess.getUnaryOperatorAccess().getIncEnumLiteralDeclaration_3());
			}
		)
		    |
		(
			enumLiteral_4=HyphenMinusHyphenMinus
			{
				$current = grammarAccess.getUnaryOperatorAccess().getDecEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_4, grammarAccess.getUnaryOperatorAccess().getDecEnumLiteralDeclaration_4());
			}
		)
		    |
		(
			enumLiteral_5=PlusSign
			{
				$current = grammarAccess.getUnaryOperatorAccess().getPosEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_5, grammarAccess.getUnaryOperatorAccess().getPosEnumLiteralDeclaration_5());
			}
		)
		    |
		(
			enumLiteral_6=HyphenMinus
			{
				$current = grammarAccess.getUnaryOperatorAccess().getNegEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_6, grammarAccess.getUnaryOperatorAccess().getNegEnumLiteralDeclaration_6());
			}
		)
		    |
		(
			enumLiteral_7=Tilde
			{
				$current = grammarAccess.getUnaryOperatorAccess().getInvEnumLiteralDeclaration_7().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_7, grammarAccess.getUnaryOperatorAccess().getInvEnumLiteralDeclaration_7());
			}
		)
		    |
		(
			enumLiteral_8=ExclamationMark
			{
				$current = grammarAccess.getUnaryOperatorAccess().getNotEnumLiteralDeclaration_8().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_8, grammarAccess.getUnaryOperatorAccess().getNotEnumLiteralDeclaration_8());
			}
		)
	)
;

// Rule MultiplicativeOperator
ruleMultiplicativeOperator returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0=Asterisk
			{
				$current = grammarAccess.getMultiplicativeOperatorAccess().getTimesEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getMultiplicativeOperatorAccess().getTimesEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=Solidus
			{
				$current = grammarAccess.getMultiplicativeOperatorAccess().getDivEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getMultiplicativeOperatorAccess().getDivEnumLiteralDeclaration_1());
			}
		)
		    |
		(
			enumLiteral_2=PercentSign
			{
				$current = grammarAccess.getMultiplicativeOperatorAccess().getModEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_2, grammarAccess.getMultiplicativeOperatorAccess().getModEnumLiteralDeclaration_2());
			}
		)
	)
;

// Rule AdditiveOperator
ruleAdditiveOperator returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0=PlusSign
			{
				$current = grammarAccess.getAdditiveOperatorAccess().getAddEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getAdditiveOperatorAccess().getAddEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=HyphenMinus
			{
				$current = grammarAccess.getAdditiveOperatorAccess().getSubEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getAdditiveOperatorAccess().getSubEnumLiteralDeclaration_1());
			}
		)
	)
;

// Rule EqualityOperator
ruleEqualityOperator returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0=EqualsSignEqualsSignEqualsSign
			{
				$current = grammarAccess.getEqualityOperatorAccess().getSameEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getEqualityOperatorAccess().getSameEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=ExclamationMarkEqualsSignEqualsSign
			{
				$current = grammarAccess.getEqualityOperatorAccess().getNsameEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getEqualityOperatorAccess().getNsameEnumLiteralDeclaration_1());
			}
		)
		    |
		(
			enumLiteral_2=EqualsSignEqualsSign
			{
				$current = grammarAccess.getEqualityOperatorAccess().getEqEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_2, grammarAccess.getEqualityOperatorAccess().getEqEnumLiteralDeclaration_2());
			}
		)
		    |
		(
			enumLiteral_3=ExclamationMarkEqualsSign
			{
				$current = grammarAccess.getEqualityOperatorAccess().getNeqEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_3, grammarAccess.getEqualityOperatorAccess().getNeqEnumLiteralDeclaration_3());
			}
		)
	)
;

// Rule N4Modifier
ruleN4Modifier returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0=Private
			{
				$current = grammarAccess.getN4ModifierAccess().getPrivateEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getN4ModifierAccess().getPrivateEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=Project
			{
				$current = grammarAccess.getN4ModifierAccess().getProjectEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getN4ModifierAccess().getProjectEnumLiteralDeclaration_1());
			}
		)
		    |
		(
			enumLiteral_2=Protected
			{
				$current = grammarAccess.getN4ModifierAccess().getProtectedEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_2, grammarAccess.getN4ModifierAccess().getProtectedEnumLiteralDeclaration_2());
			}
		)
		    |
		(
			enumLiteral_3=Public
			{
				$current = grammarAccess.getN4ModifierAccess().getPublicEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_3, grammarAccess.getN4ModifierAccess().getPublicEnumLiteralDeclaration_3());
			}
		)
		    |
		(
			enumLiteral_4=External
			{
				$current = grammarAccess.getN4ModifierAccess().getExternalEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_4, grammarAccess.getN4ModifierAccess().getExternalEnumLiteralDeclaration_4());
			}
		)
		    |
		(
			enumLiteral_5=Abstract
			{
				$current = grammarAccess.getN4ModifierAccess().getAbstractEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_5, grammarAccess.getN4ModifierAccess().getAbstractEnumLiteralDeclaration_5());
			}
		)
		    |
		(
			enumLiteral_6=Static
			{
				$current = grammarAccess.getN4ModifierAccess().getStaticEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_6, grammarAccess.getN4ModifierAccess().getStaticEnumLiteralDeclaration_6());
			}
		)
		    |
		(
			enumLiteral_7=Const
			{
				$current = grammarAccess.getN4ModifierAccess().getConstEnumLiteralDeclaration_7().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_7, grammarAccess.getN4ModifierAccess().getConstEnumLiteralDeclaration_7());
			}
		)
	)
;
