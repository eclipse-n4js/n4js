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
parser grammar InternalTypesParser;

options {
	tokenVocab=InternalTypesLexer;
	superClass=AbstractInternalAntlrParser;
}

@header {
package org.eclipse.n4js.ts.parser.antlr.internal;

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
import org.eclipse.n4js.ts.services.TypesGrammarAccess;

}

@members {

 	private TypesGrammarAccess grammarAccess;

    public InternalTypesParser(TokenStream input, TypesGrammarAccess grammarAccess) {
        this(input);
        this.grammarAccess = grammarAccess;
        registerRules(grammarAccess.getGrammar());
    }

    @Override
    protected String getFirstRuleName() {
    	return "TypeDefs";
   	}

   	@Override
   	protected TypesGrammarAccess getGrammarAccess() {
   		return grammarAccess;
   	}

}

@rulecatch {
    catch (RecognitionException re) {
        recover(input,re);
        appendSkippedTokens();
    }
}

// Entry rule entryRuleTypeDefs
entryRuleTypeDefs returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTypeDefsRule()); }
	iv_ruleTypeDefs=ruleTypeDefs
	{ $current=$iv_ruleTypeDefs.current; }
	EOF;

// Rule TypeDefs
ruleTypeDefs returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getTypeDefsAccess().getTypesTypeParserRuleCall_0());
			}
			lv_types_0_0=ruleType
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getTypeDefsRule());
				}
				add(
					$current,
					"types",
					lv_types_0_0,
					"org.eclipse.n4js.ts.Types.Type");
				afterParserOrEnumRuleCall();
			}
		)
	)*
;

// Entry rule entryRuleTAnnotation
entryRuleTAnnotation returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTAnnotationRule()); }
	iv_ruleTAnnotation=ruleTAnnotation
	{ $current=$iv_ruleTAnnotation.current; }
	EOF;

// Rule TAnnotation
ruleTAnnotation returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				CommercialAt
				(
					(
						RULE_IDENTIFIER
					)
				)
			)
			)=>
			(
				otherlv_0=CommercialAt
				{
					newLeafNode(otherlv_0, grammarAccess.getTAnnotationAccess().getCommercialAtKeyword_0_0_0());
				}
				(
					(
						lv_name_1_0=RULE_IDENTIFIER
						{
							newLeafNode(lv_name_1_0, grammarAccess.getTAnnotationAccess().getNameIDENTIFIERTerminalRuleCall_0_0_1_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getTAnnotationRule());
							}
							setWithLastConsumed(
								$current,
								"name",
								lv_name_1_0,
								"org.eclipse.n4js.ts.TypeExpressions.IDENTIFIER");
						}
					)
				)
			)
		)
		(
			(
				(LeftParenthesis)=>
				otherlv_2=LeftParenthesis
				{
					newLeafNode(otherlv_2, grammarAccess.getTAnnotationAccess().getLeftParenthesisKeyword_1_0());
				}
			)
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getTAnnotationAccess().getArgsTAnnotationArgumentParserRuleCall_1_1_0_0());
						}
						lv_args_3_0=ruleTAnnotationArgument
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTAnnotationRule());
							}
							add(
								$current,
								"args",
								lv_args_3_0,
								"org.eclipse.n4js.ts.Types.TAnnotationArgument");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					otherlv_4=Comma
					{
						newLeafNode(otherlv_4, grammarAccess.getTAnnotationAccess().getCommaKeyword_1_1_1_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getTAnnotationAccess().getArgsTAnnotationArgumentParserRuleCall_1_1_1_1_0());
							}
							lv_args_5_0=ruleTAnnotationArgument
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getTAnnotationRule());
								}
								add(
									$current,
									"args",
									lv_args_5_0,
									"org.eclipse.n4js.ts.Types.TAnnotationArgument");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)*
			)?
			otherlv_6=RightParenthesis
			{
				newLeafNode(otherlv_6, grammarAccess.getTAnnotationAccess().getRightParenthesisKeyword_1_2());
			}
		)?
	)
;

// Entry rule entryRuleTAnnotationArgument
entryRuleTAnnotationArgument returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTAnnotationArgumentRule()); }
	iv_ruleTAnnotationArgument=ruleTAnnotationArgument
	{ $current=$iv_ruleTAnnotationArgument.current; }
	EOF;

// Rule TAnnotationArgument
ruleTAnnotationArgument returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getTAnnotationArgumentAccess().getTAnnotationStringArgumentParserRuleCall_0());
		}
		this_TAnnotationStringArgument_0=ruleTAnnotationStringArgument
		{
			$current = $this_TAnnotationStringArgument_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTAnnotationArgumentAccess().getTAnnotationTypeRefArgumentParserRuleCall_1());
		}
		this_TAnnotationTypeRefArgument_1=ruleTAnnotationTypeRefArgument
		{
			$current = $this_TAnnotationTypeRefArgument_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleTAnnotationStringArgument
entryRuleTAnnotationStringArgument returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTAnnotationStringArgumentRule()); }
	iv_ruleTAnnotationStringArgument=ruleTAnnotationStringArgument
	{ $current=$iv_ruleTAnnotationStringArgument.current; }
	EOF;

// Rule TAnnotationStringArgument
ruleTAnnotationStringArgument returns [EObject current=null]
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
				newLeafNode(lv_value_0_0, grammarAccess.getTAnnotationStringArgumentAccess().getValueSTRINGTerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getTAnnotationStringArgumentRule());
				}
				setWithLastConsumed(
					$current,
					"value",
					lv_value_0_0,
					"org.eclipse.n4js.ts.Types.STRING");
			}
		)
	)
;

// Entry rule entryRuleTAnnotationTypeRefArgument
entryRuleTAnnotationTypeRefArgument returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTAnnotationTypeRefArgumentRule()); }
	iv_ruleTAnnotationTypeRefArgument=ruleTAnnotationTypeRefArgument
	{ $current=$iv_ruleTAnnotationTypeRefArgument.current; }
	EOF;

// Rule TAnnotationTypeRefArgument
ruleTAnnotationTypeRefArgument returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getTAnnotationTypeRefArgumentAccess().getTypeRefTypeRefParserRuleCall_0());
			}
			lv_typeRef_0_0=ruleTypeRef
			{
				if ($current==null) {
					$current = createModelElementForParent(grammarAccess.getTAnnotationTypeRefArgumentRule());
				}
				set(
					$current,
					"typeRef",
					lv_typeRef_0_0,
					"org.eclipse.n4js.ts.Types.TypeRef");
				afterParserOrEnumRuleCall();
			}
		)
	)
;

// Entry rule entryRuleType
entryRuleType returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTypeRule()); }
	iv_ruleType=ruleType
	{ $current=$iv_ruleType.current; }
	EOF;

// Rule Type
ruleType returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getTypeAccess().getTObjectPrototypeParserRuleCall_0());
		}
		this_TObjectPrototype_0=ruleTObjectPrototype
		{
			$current = $this_TObjectPrototype_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeAccess().getTClassParserRuleCall_1());
		}
		this_TClass_1=ruleTClass
		{
			$current = $this_TClass_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeAccess().getTInterfaceParserRuleCall_2());
		}
		this_TInterface_2=ruleTInterface
		{
			$current = $this_TInterface_2.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeAccess().getTEnumParserRuleCall_3());
		}
		this_TEnum_3=ruleTEnum
		{
			$current = $this_TEnum_3.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeAccess().getAnyTypeParserRuleCall_4());
		}
		this_AnyType_4=ruleAnyType
		{
			$current = $this_AnyType_4.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeAccess().getVoidTypeParserRuleCall_5());
		}
		this_VoidType_5=ruleVoidType
		{
			$current = $this_VoidType_5.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeAccess().getUndefinedTypeParserRuleCall_6());
		}
		this_UndefinedType_6=ruleUndefinedType
		{
			$current = $this_UndefinedType_6.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeAccess().getNullTypeParserRuleCall_7());
		}
		this_NullType_7=ruleNullType
		{
			$current = $this_NullType_7.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeAccess().getPrimitiveTypeParserRuleCall_8());
		}
		this_PrimitiveType_8=rulePrimitiveType
		{
			$current = $this_PrimitiveType_8.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeAccess().getTFunctionParserRuleCall_9());
		}
		this_TFunction_9=ruleTFunction
		{
			$current = $this_TFunction_9.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeAccess().getTypeVariableParserRuleCall_10());
		}
		this_TypeVariable_10=ruleTypeVariable
		{
			$current = $this_TypeVariable_10.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypeAccess().getVirtualBaseTypeParserRuleCall_11());
		}
		this_VirtualBaseType_11=ruleVirtualBaseType
		{
			$current = $this_VirtualBaseType_11.current;
			afterParserOrEnumRuleCall();
		}
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
			newCompositeNode(grammarAccess.getTypeRefAccess().getTypeRefWithoutModifiersParserRuleCall_0());
		}
		this_TypeRefWithoutModifiers_0=ruleTypeRefWithoutModifiers
		{
			$current = $this_TypeRefWithoutModifiers_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				lv_followedByQuestionMark_1_0=QuestionMark
				{
					newLeafNode(lv_followedByQuestionMark_1_0, grammarAccess.getTypeRefAccess().getFollowedByQuestionMarkQuestionMarkKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTypeRefRule());
					}
					setWithLastConsumed($current, "followedByQuestionMark", true, "?");
				}
			)
		)?
	)
;

// Entry rule entryRulePrimitiveType
entryRulePrimitiveType returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getPrimitiveTypeRule()); }
	iv_rulePrimitiveType=rulePrimitiveType
	{ $current=$iv_rulePrimitiveType.current; }
	EOF;

// Rule PrimitiveType
rulePrimitiveType returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=Primitive
		{
			newLeafNode(otherlv_0, grammarAccess.getPrimitiveTypeAccess().getPrimitiveKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getPrimitiveTypeAccess().getNameVoidOrBindingIdentifierParserRuleCall_1_0());
				}
				lv_name_1_0=ruleVoidOrBindingIdentifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getPrimitiveTypeRule());
					}
					set(
						$current,
						"name",
						lv_name_1_0,
						"org.eclipse.n4js.ts.Types.VoidOrBindingIdentifier");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_2=LessThanSign
			{
				newLeafNode(otherlv_2, grammarAccess.getPrimitiveTypeAccess().getLessThanSignKeyword_2_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getPrimitiveTypeAccess().getTypeVarsTypeVariableParserRuleCall_2_1_0());
					}
					lv_typeVars_3_0=ruleTypeVariable
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getPrimitiveTypeRule());
						}
						add(
							$current,
							"typeVars",
							lv_typeVars_3_0,
							"org.eclipse.n4js.ts.Types.TypeVariable");
						afterParserOrEnumRuleCall();
					}
				)
			)
			otherlv_4=GreaterThanSign
			{
				newLeafNode(otherlv_4, grammarAccess.getPrimitiveTypeAccess().getGreaterThanSignKeyword_2_2());
			}
		)?
		(
			otherlv_5=Indexed
			{
				newLeafNode(otherlv_5, grammarAccess.getPrimitiveTypeAccess().getIndexedKeyword_3_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getPrimitiveTypeAccess().getDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_3_1_0());
					}
					lv_declaredElementType_6_0=ruleParameterizedTypeRefNominal
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getPrimitiveTypeRule());
						}
						set(
							$current,
							"declaredElementType",
							lv_declaredElementType_6_0,
							"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		otherlv_7=LeftCurlyBracket
		{
			newLeafNode(otherlv_7, grammarAccess.getPrimitiveTypeAccess().getLeftCurlyBracketKeyword_4());
		}
		(
			otherlv_8=AutoboxedType
			{
				newLeafNode(otherlv_8, grammarAccess.getPrimitiveTypeAccess().getAutoboxedTypeKeyword_5_0());
			}
			(
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getPrimitiveTypeRule());
						}
					}
					{
						newCompositeNode(grammarAccess.getPrimitiveTypeAccess().getAutoboxedTypeTClassifierCrossReference_5_1_0());
					}
					ruleTypeReferenceName
					{
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		(
			otherlv_10=AssignmnentCompatible
			{
				newLeafNode(otherlv_10, grammarAccess.getPrimitiveTypeAccess().getAssignmnentCompatibleKeyword_6_0());
			}
			(
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getPrimitiveTypeRule());
						}
					}
					{
						newCompositeNode(grammarAccess.getPrimitiveTypeAccess().getAssignmentCompatiblePrimitiveTypeCrossReference_6_1_0());
					}
					ruleTypeReferenceName
					{
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		otherlv_12=RightCurlyBracket
		{
			newLeafNode(otherlv_12, grammarAccess.getPrimitiveTypeAccess().getRightCurlyBracketKeyword_7());
		}
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
		kw=Any
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypeReferenceNameAccess().getAnyKeyword_1());
		}
		    |
		kw=Undefined
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypeReferenceNameAccess().getUndefinedKeyword_2());
		}
		    |
		kw=Null
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypeReferenceNameAccess().getNullKeyword_3());
		}
		    |
		kw=Indexed
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypeReferenceNameAccess().getIndexedKeyword_4());
		}
		    |
		(
			this_IDENTIFIER_5=RULE_IDENTIFIER
			{
				$current.merge(this_IDENTIFIER_5);
			}
			{
				newLeafNode(this_IDENTIFIER_5, grammarAccess.getTypeReferenceNameAccess().getIDENTIFIERTerminalRuleCall_5_0());
			}
			(
				kw=Solidus
				{
					$current.merge(kw);
					newLeafNode(kw, grammarAccess.getTypeReferenceNameAccess().getSolidusKeyword_5_1_0());
				}
				this_IDENTIFIER_7=RULE_IDENTIFIER
				{
					$current.merge(this_IDENTIFIER_7);
				}
				{
					newLeafNode(this_IDENTIFIER_7, grammarAccess.getTypeReferenceNameAccess().getIDENTIFIERTerminalRuleCall_5_1_1());
				}
			)*
		)
	)
;

// Entry rule entryRuleAnyType
entryRuleAnyType returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getAnyTypeRule()); }
	iv_ruleAnyType=ruleAnyType
	{ $current=$iv_ruleAnyType.current; }
	EOF;

// Rule AnyType
ruleAnyType returns [EObject current=null]
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
					grammarAccess.getAnyTypeAccess().getAnyTypeAction_0(),
					$current);
			}
		)
		(
			(
				lv_name_1_0=Any
				{
					newLeafNode(lv_name_1_0, grammarAccess.getAnyTypeAccess().getNameAnyKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getAnyTypeRule());
					}
					setWithLastConsumed($current, "name", lv_name_1_0, "any");
				}
			)
		)
		otherlv_2=LeftCurlyBracket
		{
			newLeafNode(otherlv_2, grammarAccess.getAnyTypeAccess().getLeftCurlyBracketKeyword_2());
		}
		otherlv_3=RightCurlyBracket
		{
			newLeafNode(otherlv_3, grammarAccess.getAnyTypeAccess().getRightCurlyBracketKeyword_3());
		}
	)
;

// Entry rule entryRuleVoidType
entryRuleVoidType returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getVoidTypeRule()); }
	iv_ruleVoidType=ruleVoidType
	{ $current=$iv_ruleVoidType.current; }
	EOF;

// Rule VoidType
ruleVoidType returns [EObject current=null]
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
					grammarAccess.getVoidTypeAccess().getVoidTypeAction_0(),
					$current);
			}
		)
		(
			(
				lv_name_1_0=Void
				{
					newLeafNode(lv_name_1_0, grammarAccess.getVoidTypeAccess().getNameVoidKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getVoidTypeRule());
					}
					setWithLastConsumed($current, "name", lv_name_1_0, "void");
				}
			)
		)
		otherlv_2=LeftCurlyBracket
		{
			newLeafNode(otherlv_2, grammarAccess.getVoidTypeAccess().getLeftCurlyBracketKeyword_2());
		}
		otherlv_3=RightCurlyBracket
		{
			newLeafNode(otherlv_3, grammarAccess.getVoidTypeAccess().getRightCurlyBracketKeyword_3());
		}
	)
;

// Entry rule entryRuleUndefinedType
entryRuleUndefinedType returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getUndefinedTypeRule()); }
	iv_ruleUndefinedType=ruleUndefinedType
	{ $current=$iv_ruleUndefinedType.current; }
	EOF;

// Rule UndefinedType
ruleUndefinedType returns [EObject current=null]
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
					grammarAccess.getUndefinedTypeAccess().getUndefinedTypeAction_0(),
					$current);
			}
		)
		(
			(
				lv_name_1_0=Undefined
				{
					newLeafNode(lv_name_1_0, grammarAccess.getUndefinedTypeAccess().getNameUndefinedKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getUndefinedTypeRule());
					}
					setWithLastConsumed($current, "name", lv_name_1_0, "undefined");
				}
			)
		)
		otherlv_2=LeftCurlyBracket
		{
			newLeafNode(otherlv_2, grammarAccess.getUndefinedTypeAccess().getLeftCurlyBracketKeyword_2());
		}
		otherlv_3=RightCurlyBracket
		{
			newLeafNode(otherlv_3, grammarAccess.getUndefinedTypeAccess().getRightCurlyBracketKeyword_3());
		}
	)
;

// Entry rule entryRuleNullType
entryRuleNullType returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getNullTypeRule()); }
	iv_ruleNullType=ruleNullType
	{ $current=$iv_ruleNullType.current; }
	EOF;

// Rule NullType
ruleNullType returns [EObject current=null]
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
					grammarAccess.getNullTypeAccess().getNullTypeAction_0(),
					$current);
			}
		)
		(
			(
				lv_name_1_0=Null
				{
					newLeafNode(lv_name_1_0, grammarAccess.getNullTypeAccess().getNameNullKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getNullTypeRule());
					}
					setWithLastConsumed($current, "name", lv_name_1_0, "null");
				}
			)
		)
		otherlv_2=LeftCurlyBracket
		{
			newLeafNode(otherlv_2, grammarAccess.getNullTypeAccess().getLeftCurlyBracketKeyword_2());
		}
		otherlv_3=RightCurlyBracket
		{
			newLeafNode(otherlv_3, grammarAccess.getNullTypeAccess().getRightCurlyBracketKeyword_3());
		}
	)
;

// Entry rule entryRuleTypesIdentifier
entryRuleTypesIdentifier returns [String current=null]:
	{ newCompositeNode(grammarAccess.getTypesIdentifierRule()); }
	iv_ruleTypesIdentifier=ruleTypesIdentifier
	{ $current=$iv_ruleTypesIdentifier.current.getText(); }
	EOF;

// Rule TypesIdentifier
ruleTypesIdentifier returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getTypesIdentifierAccess().getTypesSpecificKeywordsParserRuleCall_0());
		}
		this_TypesSpecificKeywords_0=ruleTypesSpecificKeywords
		{
			$current.merge(this_TypesSpecificKeywords_0);
		}
		{
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getTypesIdentifierAccess().getIdentifierNameParserRuleCall_1());
		}
		this_IdentifierName_1=ruleIdentifierName
		{
			$current.merge(this_IdentifierName_1);
		}
		{
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleBindingTypesIdentifier
entryRuleBindingTypesIdentifier returns [String current=null]:
	{ newCompositeNode(grammarAccess.getBindingTypesIdentifierRule()); }
	iv_ruleBindingTypesIdentifier=ruleBindingTypesIdentifier
	{ $current=$iv_ruleBindingTypesIdentifier.current.getText(); }
	EOF;

// Rule BindingTypesIdentifier
ruleBindingTypesIdentifier returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getBindingTypesIdentifierAccess().getTypesSpecificKeywordsParserRuleCall_0());
		}
		this_TypesSpecificKeywords_0=ruleTypesSpecificKeywords
		{
			$current.merge(this_TypesSpecificKeywords_0);
		}
		{
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getBindingTypesIdentifierAccess().getBindingIdentifierParserRuleCall_1());
		}
		this_BindingIdentifier_1=ruleBindingIdentifier
		{
			$current.merge(this_BindingIdentifier_1);
		}
		{
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleVoidOrBindingIdentifier
entryRuleVoidOrBindingIdentifier returns [String current=null]:
	{ newCompositeNode(grammarAccess.getVoidOrBindingIdentifierRule()); }
	iv_ruleVoidOrBindingIdentifier=ruleVoidOrBindingIdentifier
	{ $current=$iv_ruleVoidOrBindingIdentifier.current.getText(); }
	EOF;

// Rule VoidOrBindingIdentifier
ruleVoidOrBindingIdentifier returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
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
			newLeafNode(kw, grammarAccess.getVoidOrBindingIdentifierAccess().getVoidKeyword_0());
		}
		    |
		{
			newCompositeNode(grammarAccess.getVoidOrBindingIdentifierAccess().getBindingTypesIdentifierParserRuleCall_1());
		}
		this_BindingTypesIdentifier_1=ruleBindingTypesIdentifier
		{
			$current.merge(this_BindingTypesIdentifier_1);
		}
		{
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleTypesSpecificKeywords
entryRuleTypesSpecificKeywords returns [String current=null]:
	{ newCompositeNode(grammarAccess.getTypesSpecificKeywordsRule()); }
	iv_ruleTypesSpecificKeywords=ruleTypesSpecificKeywords
	{ $current=$iv_ruleTypesSpecificKeywords.current.getText(); }
	EOF;

// Rule TypesSpecificKeywords
ruleTypesSpecificKeywords returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw=Any
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypesSpecificKeywordsAccess().getAnyKeyword_0());
		}
		    |
		kw=Undefined
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypesSpecificKeywordsAccess().getUndefinedKeyword_1());
		}
		    |
		kw=Object
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypesSpecificKeywordsAccess().getObjectKeyword_2());
		}
		    |
		kw=VirtualBase
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypesSpecificKeywordsAccess().getVirtualBaseKeyword_3());
		}
		    |
		kw=Primitive
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypesSpecificKeywordsAccess().getPrimitiveKeyword_4());
		}
		    |
		kw=AutoboxedType
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypesSpecificKeywordsAccess().getAutoboxedTypeKeyword_5());
		}
		    |
		kw=AssignmnentCompatible
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypesSpecificKeywordsAccess().getAssignmnentCompatibleKeyword_6());
		}
	)
;

// Entry rule entryRuleTypesComputedPropertyName
entryRuleTypesComputedPropertyName returns [String current=null]:
	{ newCompositeNode(grammarAccess.getTypesComputedPropertyNameRule()); }
	iv_ruleTypesComputedPropertyName=ruleTypesComputedPropertyName
	{ $current=$iv_ruleTypesComputedPropertyName.current.getText(); }
	EOF;

// Rule TypesComputedPropertyName
ruleTypesComputedPropertyName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw=LeftSquareBracket
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypesComputedPropertyNameAccess().getLeftSquareBracketKeyword_0());
		}
		(
			{
				newCompositeNode(grammarAccess.getTypesComputedPropertyNameAccess().getTypesSymbolLiteralComputedNameParserRuleCall_1_0());
			}
			this_TypesSymbolLiteralComputedName_1=ruleTypesSymbolLiteralComputedName
			{
				$current.merge(this_TypesSymbolLiteralComputedName_1);
			}
			{
				afterParserOrEnumRuleCall();
			}
			    |
			{
				newCompositeNode(grammarAccess.getTypesComputedPropertyNameAccess().getTypesStringLiteralComputedNameParserRuleCall_1_1());
			}
			this_TypesStringLiteralComputedName_2=ruleTypesStringLiteralComputedName
			{
				$current.merge(this_TypesStringLiteralComputedName_2);
			}
			{
				afterParserOrEnumRuleCall();
			}
		)
		kw=RightSquareBracket
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypesComputedPropertyNameAccess().getRightSquareBracketKeyword_2());
		}
	)
;

// Entry rule entryRuleTypesSymbolLiteralComputedName
entryRuleTypesSymbolLiteralComputedName returns [String current=null]:
	{ newCompositeNode(grammarAccess.getTypesSymbolLiteralComputedNameRule()); }
	iv_ruleTypesSymbolLiteralComputedName=ruleTypesSymbolLiteralComputedName
	{ $current=$iv_ruleTypesSymbolLiteralComputedName.current.getText(); }
	EOF;

// Rule TypesSymbolLiteralComputedName
ruleTypesSymbolLiteralComputedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getTypesSymbolLiteralComputedNameAccess().getTypesIdentifierParserRuleCall_0());
		}
		this_TypesIdentifier_0=ruleTypesIdentifier
		{
			$current.merge(this_TypesIdentifier_0);
		}
		{
			afterParserOrEnumRuleCall();
		}
		kw=FullStop
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getTypesSymbolLiteralComputedNameAccess().getFullStopKeyword_1());
		}
		{
			newCompositeNode(grammarAccess.getTypesSymbolLiteralComputedNameAccess().getTypesIdentifierParserRuleCall_2());
		}
		this_TypesIdentifier_2=ruleTypesIdentifier
		{
			$current.merge(this_TypesIdentifier_2);
		}
		{
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleTypesStringLiteralComputedName
entryRuleTypesStringLiteralComputedName returns [String current=null]:
	{ newCompositeNode(grammarAccess.getTypesStringLiteralComputedNameRule()); }
	iv_ruleTypesStringLiteralComputedName=ruleTypesStringLiteralComputedName
	{ $current=$iv_ruleTypesStringLiteralComputedName.current.getText(); }
	EOF;

// Rule TypesStringLiteralComputedName
ruleTypesStringLiteralComputedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
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
		newLeafNode(this_STRING_0, grammarAccess.getTypesStringLiteralComputedNameAccess().getSTRINGTerminalRuleCall());
	}
;

// Entry rule entryRuleTObjectPrototype
entryRuleTObjectPrototype returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTObjectPrototypeRule()); }
	iv_ruleTObjectPrototype=ruleTObjectPrototype
	{ $current=$iv_ruleTObjectPrototype.current; }
	EOF;

// Rule TObjectPrototype
ruleTObjectPrototype returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getTObjectPrototypeAccess().getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0());
				}
				lv_declaredTypeAccessModifier_0_0=ruleTypeAccessModifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTObjectPrototypeRule());
					}
					set(
						$current,
						"declaredTypeAccessModifier",
						lv_declaredTypeAccessModifier_0_0,
						"org.eclipse.n4js.ts.Types.TypeAccessModifier");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				lv_declaredProvidedByRuntime_1_0=ProvidedByRuntime
				{
					newLeafNode(lv_declaredProvidedByRuntime_1_0, grammarAccess.getTObjectPrototypeAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTObjectPrototypeRule());
					}
					setWithLastConsumed($current, "declaredProvidedByRuntime", true, "providedByRuntime");
				}
			)
		)?
		(
			(
				lv_declaredFinal_2_0=Final
				{
					newLeafNode(lv_declaredFinal_2_0, grammarAccess.getTObjectPrototypeAccess().getDeclaredFinalFinalKeyword_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTObjectPrototypeRule());
					}
					setWithLastConsumed($current, "declaredFinal", true, "final");
				}
			)
		)?
		otherlv_3=Object
		{
			newLeafNode(otherlv_3, grammarAccess.getTObjectPrototypeAccess().getObjectKeyword_3());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getTObjectPrototypeAccess().getNameBindingTypesIdentifierParserRuleCall_4_0());
				}
				lv_name_4_0=ruleBindingTypesIdentifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTObjectPrototypeRule());
					}
					set(
						$current,
						"name",
						lv_name_4_0,
						"org.eclipse.n4js.ts.Types.BindingTypesIdentifier");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getTObjectPrototypeRule());
				}
				newCompositeNode(grammarAccess.getTObjectPrototypeAccess().getTypeVariablesParserRuleCall_5());
			}
			this_TypeVariables_5=ruleTypeVariables[$current]
			{
				$current = $this_TypeVariables_5.current;
				afterParserOrEnumRuleCall();
			}
		)?
		(
			otherlv_6=Extends
			{
				newLeafNode(otherlv_6, grammarAccess.getTObjectPrototypeAccess().getExtendsKeyword_6_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getTObjectPrototypeAccess().getSuperTypeParameterizedTypeRefNominalParserRuleCall_6_1_0());
					}
					lv_superType_7_0=ruleParameterizedTypeRefNominal
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTObjectPrototypeRule());
						}
						set(
							$current,
							"superType",
							lv_superType_7_0,
							"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		(
			otherlv_8=Indexed
			{
				newLeafNode(otherlv_8, grammarAccess.getTObjectPrototypeAccess().getIndexedKeyword_7_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getTObjectPrototypeAccess().getDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_7_1_0());
					}
					lv_declaredElementType_9_0=ruleParameterizedTypeRefNominal
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTObjectPrototypeRule());
						}
						set(
							$current,
							"declaredElementType",
							lv_declaredElementType_9_0,
							"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		(
			((
				CommercialAt
				(
					(
						RULE_IDENTIFIER
					)
				)
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getTObjectPrototypeAccess().getAnnotationsTAnnotationParserRuleCall_8_0());
				}
				lv_annotations_10_0=ruleTAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTObjectPrototypeRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_10_0,
						"org.eclipse.n4js.ts.Types.TAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_11=LeftCurlyBracket
		{
			newLeafNode(otherlv_11, grammarAccess.getTObjectPrototypeAccess().getLeftCurlyBracketKeyword_9());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getTObjectPrototypeAccess().getOwnedMembersTMemberParserRuleCall_10_0());
				}
				lv_ownedMembers_12_0=ruleTMember
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTObjectPrototypeRule());
					}
					add(
						$current,
						"ownedMembers",
						lv_ownedMembers_12_0,
						"org.eclipse.n4js.ts.Types.TMember");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getTObjectPrototypeAccess().getCallableCtorCallableCtorParserRuleCall_11_0_0());
					}
					lv_callableCtor_13_0=ruleCallableCtor
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTObjectPrototypeRule());
						}
						set(
							$current,
							"callableCtor",
							lv_callableCtor_13_0,
							"org.eclipse.n4js.ts.Types.CallableCtor");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getTObjectPrototypeAccess().getOwnedMembersTMemberParserRuleCall_11_1_0());
					}
					lv_ownedMembers_14_0=ruleTMember
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTObjectPrototypeRule());
						}
						add(
							$current,
							"ownedMembers",
							lv_ownedMembers_14_0,
							"org.eclipse.n4js.ts.Types.TMember");
						afterParserOrEnumRuleCall();
					}
				)
			)*
		)?
		otherlv_15=RightCurlyBracket
		{
			newLeafNode(otherlv_15, grammarAccess.getTObjectPrototypeAccess().getRightCurlyBracketKeyword_12());
		}
	)
;

// Entry rule entryRuleVirtualBaseType
entryRuleVirtualBaseType returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getVirtualBaseTypeRule()); }
	iv_ruleVirtualBaseType=ruleVirtualBaseType
	{ $current=$iv_ruleVirtualBaseType.current; }
	EOF;

// Rule VirtualBaseType
ruleVirtualBaseType returns [EObject current=null]
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
					grammarAccess.getVirtualBaseTypeAccess().getVirtualBaseTypeAction_0(),
					$current);
			}
		)
		otherlv_1=VirtualBase
		{
			newLeafNode(otherlv_1, grammarAccess.getVirtualBaseTypeAccess().getVirtualBaseKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getVirtualBaseTypeAccess().getNameBindingTypesIdentifierParserRuleCall_2_0());
				}
				lv_name_2_0=ruleBindingTypesIdentifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVirtualBaseTypeRule());
					}
					set(
						$current,
						"name",
						lv_name_2_0,
						"org.eclipse.n4js.ts.Types.BindingTypesIdentifier");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_3=Indexed
			{
				newLeafNode(otherlv_3, grammarAccess.getVirtualBaseTypeAccess().getIndexedKeyword_3_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getVirtualBaseTypeAccess().getDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_3_1_0());
					}
					lv_declaredElementType_4_0=ruleParameterizedTypeRefNominal
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getVirtualBaseTypeRule());
						}
						set(
							$current,
							"declaredElementType",
							lv_declaredElementType_4_0,
							"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		otherlv_5=LeftCurlyBracket
		{
			newLeafNode(otherlv_5, grammarAccess.getVirtualBaseTypeAccess().getLeftCurlyBracketKeyword_4());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getVirtualBaseTypeAccess().getOwnedMembersTMemberParserRuleCall_5_0());
				}
				lv_ownedMembers_6_0=ruleTMember
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getVirtualBaseTypeRule());
					}
					add(
						$current,
						"ownedMembers",
						lv_ownedMembers_6_0,
						"org.eclipse.n4js.ts.Types.TMember");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_7=RightCurlyBracket
		{
			newLeafNode(otherlv_7, grammarAccess.getVirtualBaseTypeAccess().getRightCurlyBracketKeyword_6());
		}
	)
;

// Entry rule entryRuleTClass
entryRuleTClass returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTClassRule()); }
	iv_ruleTClass=ruleTClass
	{ $current=$iv_ruleTClass.current; }
	EOF;

// Rule TClass
ruleTClass returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getTClassAccess().getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0());
				}
				lv_declaredTypeAccessModifier_0_0=ruleTypeAccessModifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTClassRule());
					}
					set(
						$current,
						"declaredTypeAccessModifier",
						lv_declaredTypeAccessModifier_0_0,
						"org.eclipse.n4js.ts.Types.TypeAccessModifier");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				lv_declaredProvidedByRuntime_1_0=ProvidedByRuntime
				{
					newLeafNode(lv_declaredProvidedByRuntime_1_0, grammarAccess.getTClassAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTClassRule());
					}
					setWithLastConsumed($current, "declaredProvidedByRuntime", true, "providedByRuntime");
				}
			)
		)?
		(
			(
				lv_declaredAbstract_2_0=Abstract
				{
					newLeafNode(lv_declaredAbstract_2_0, grammarAccess.getTClassAccess().getDeclaredAbstractAbstractKeyword_2_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTClassRule());
					}
					setWithLastConsumed($current, "declaredAbstract", true, "abstract");
				}
			)
		)?
		(
			(
				lv_declaredFinal_3_0=Final
				{
					newLeafNode(lv_declaredFinal_3_0, grammarAccess.getTClassAccess().getDeclaredFinalFinalKeyword_3_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTClassRule());
					}
					setWithLastConsumed($current, "declaredFinal", true, "final");
				}
			)
		)?
		otherlv_4=Class
		{
			newLeafNode(otherlv_4, grammarAccess.getTClassAccess().getClassKeyword_4());
		}
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getTClassRule());
			}
			newCompositeNode(grammarAccess.getTClassAccess().getTClassOrInterfaceHeaderParserRuleCall_5());
		}
		this_TClassOrInterfaceHeader_5=ruleTClassOrInterfaceHeader[$current]
		{
			$current = $this_TClassOrInterfaceHeader_5.current;
			afterParserOrEnumRuleCall();
		}
		(
			otherlv_6=Extends
			{
				newLeafNode(otherlv_6, grammarAccess.getTClassAccess().getExtendsKeyword_6_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getTClassAccess().getSuperClassRefParameterizedTypeRefNominalParserRuleCall_6_1_0());
					}
					lv_superClassRef_7_0=ruleParameterizedTypeRefNominal
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTClassRule());
						}
						set(
							$current,
							"superClassRef",
							lv_superClassRef_7_0,
							"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		(
			otherlv_8=Implements
			{
				newLeafNode(otherlv_8, grammarAccess.getTClassAccess().getImplementsKeyword_7_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getTClassAccess().getImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_7_1_0());
					}
					lv_implementedInterfaceRefs_9_0=ruleParameterizedTypeRefNominal
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTClassRule());
						}
						add(
							$current,
							"implementedInterfaceRefs",
							lv_implementedInterfaceRefs_9_0,
							"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_10=Comma
				{
					newLeafNode(otherlv_10, grammarAccess.getTClassAccess().getCommaKeyword_7_2_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getTClassAccess().getImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_7_2_1_0());
						}
						lv_implementedInterfaceRefs_11_0=ruleParameterizedTypeRefNominal
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTClassRule());
							}
							add(
								$current,
								"implementedInterfaceRefs",
								lv_implementedInterfaceRefs_11_0,
								"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		(
			((
				CommercialAt
				(
					(
						RULE_IDENTIFIER
					)
				)
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getTClassAccess().getAnnotationsTAnnotationParserRuleCall_8_0());
				}
				lv_annotations_12_0=ruleTAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTClassRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_12_0,
						"org.eclipse.n4js.ts.Types.TAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_13=LeftCurlyBracket
		{
			newLeafNode(otherlv_13, grammarAccess.getTClassAccess().getLeftCurlyBracketKeyword_9());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getTClassAccess().getOwnedMembersTMemberParserRuleCall_10_0());
				}
				lv_ownedMembers_14_0=ruleTMember
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTClassRule());
					}
					add(
						$current,
						"ownedMembers",
						lv_ownedMembers_14_0,
						"org.eclipse.n4js.ts.Types.TMember");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getTClassAccess().getCallableCtorCallableCtorParserRuleCall_11_0_0());
					}
					lv_callableCtor_15_0=ruleCallableCtor
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTClassRule());
						}
						set(
							$current,
							"callableCtor",
							lv_callableCtor_15_0,
							"org.eclipse.n4js.ts.Types.CallableCtor");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getTClassAccess().getOwnedMembersTMemberParserRuleCall_11_1_0());
					}
					lv_ownedMembers_16_0=ruleTMember
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTClassRule());
						}
						add(
							$current,
							"ownedMembers",
							lv_ownedMembers_16_0,
							"org.eclipse.n4js.ts.Types.TMember");
						afterParserOrEnumRuleCall();
					}
				)
			)*
		)?
		otherlv_17=RightCurlyBracket
		{
			newLeafNode(otherlv_17, grammarAccess.getTClassAccess().getRightCurlyBracketKeyword_12());
		}
	)
;

// Entry rule entryRuleTInterface
entryRuleTInterface returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTInterfaceRule()); }
	iv_ruleTInterface=ruleTInterface
	{ $current=$iv_ruleTInterface.current; }
	EOF;

// Rule TInterface
ruleTInterface returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getTInterfaceAccess().getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0());
				}
				lv_declaredTypeAccessModifier_0_0=ruleTypeAccessModifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTInterfaceRule());
					}
					set(
						$current,
						"declaredTypeAccessModifier",
						lv_declaredTypeAccessModifier_0_0,
						"org.eclipse.n4js.ts.Types.TypeAccessModifier");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				lv_declaredProvidedByRuntime_1_0=ProvidedByRuntime
				{
					newLeafNode(lv_declaredProvidedByRuntime_1_0, grammarAccess.getTInterfaceAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTInterfaceRule());
					}
					setWithLastConsumed($current, "declaredProvidedByRuntime", true, "providedByRuntime");
				}
			)
		)?
		otherlv_2=Interface
		{
			newLeafNode(otherlv_2, grammarAccess.getTInterfaceAccess().getInterfaceKeyword_2());
		}
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getTInterfaceRule());
			}
			newCompositeNode(grammarAccess.getTInterfaceAccess().getTClassOrInterfaceHeaderParserRuleCall_3());
		}
		this_TClassOrInterfaceHeader_3=ruleTClassOrInterfaceHeader[$current]
		{
			$current = $this_TClassOrInterfaceHeader_3.current;
			afterParserOrEnumRuleCall();
		}
		(
			otherlv_4=Extends
			{
				newLeafNode(otherlv_4, grammarAccess.getTInterfaceAccess().getExtendsKeyword_4_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getTInterfaceAccess().getSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_4_1_0());
					}
					lv_superInterfaceRefs_5_0=ruleParameterizedTypeRefNominal
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTInterfaceRule());
						}
						add(
							$current,
							"superInterfaceRefs",
							lv_superInterfaceRefs_5_0,
							"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_6=Comma
				{
					newLeafNode(otherlv_6, grammarAccess.getTInterfaceAccess().getCommaKeyword_4_2_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getTInterfaceAccess().getSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_4_2_1_0());
						}
						lv_superInterfaceRefs_7_0=ruleParameterizedTypeRefNominal
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTInterfaceRule());
							}
							add(
								$current,
								"superInterfaceRefs",
								lv_superInterfaceRefs_7_0,
								"org.eclipse.n4js.ts.TypeExpressions.ParameterizedTypeRefNominal");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		(
			((
				CommercialAt
				(
					(
						RULE_IDENTIFIER
					)
				)
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getTInterfaceAccess().getAnnotationsTAnnotationParserRuleCall_5_0());
				}
				lv_annotations_8_0=ruleTAnnotation
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTInterfaceRule());
					}
					add(
						$current,
						"annotations",
						lv_annotations_8_0,
						"org.eclipse.n4js.ts.Types.TAnnotation");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_9=LeftCurlyBracket
		{
			newLeafNode(otherlv_9, grammarAccess.getTInterfaceAccess().getLeftCurlyBracketKeyword_6());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getTInterfaceAccess().getOwnedMembersTMemberParserRuleCall_7_0());
				}
				lv_ownedMembers_10_0=ruleTMember
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTInterfaceRule());
					}
					add(
						$current,
						"ownedMembers",
						lv_ownedMembers_10_0,
						"org.eclipse.n4js.ts.Types.TMember");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_11=RightCurlyBracket
		{
			newLeafNode(otherlv_11, grammarAccess.getTInterfaceAccess().getRightCurlyBracketKeyword_8());
		}
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
				lv_name_0_0=RULE_IDENTIFIER
				{
					newLeafNode(lv_name_0_0, grammarAccess.getTypeVariableAccess().getNameIDENTIFIERTerminalRuleCall_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTypeVariableRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_0_0,
						"org.eclipse.n4js.ts.TypeExpressions.IDENTIFIER");
				}
			)
		)
		(
			otherlv_1=Extends
			{
				newLeafNode(otherlv_1, grammarAccess.getTypeVariableAccess().getExtendsKeyword_1_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getTypeVariableAccess().getDeclaredUpperBoundTypeRefParserRuleCall_1_1_0());
					}
					lv_declaredUpperBound_2_0=ruleTypeRef
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTypeVariableRule());
						}
						set(
							$current,
							"declaredUpperBound",
							lv_declaredUpperBound_2_0,
							"org.eclipse.n4js.ts.Types.TypeRef");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;


// Rule TClassOrInterfaceHeader
ruleTClassOrInterfaceHeader[EObject in_current]  returns [EObject current=in_current]
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
					newCompositeNode(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0());
				}
				lv_typingStrategy_0_0=ruleTypingStrategyDefSiteOperator
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTClassOrInterfaceHeaderRule());
					}
					set(
						$current,
						"typingStrategy",
						lv_typingStrategy_0_0,
						"org.eclipse.n4js.ts.TypeExpressions.TypingStrategyDefSiteOperator");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getTClassOrInterfaceHeaderAccess().getNameBindingTypesIdentifierParserRuleCall_1_0());
				}
				lv_name_1_0=ruleBindingTypesIdentifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTClassOrInterfaceHeaderRule());
					}
					set(
						$current,
						"name",
						lv_name_1_0,
						"org.eclipse.n4js.ts.Types.BindingTypesIdentifier");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_2=LessThanSign
			{
				newLeafNode(otherlv_2, grammarAccess.getTClassOrInterfaceHeaderAccess().getLessThanSignKeyword_2_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypeVarsTypeVariableParserRuleCall_2_1_0());
					}
					lv_typeVars_3_0=superTypeVariable
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTClassOrInterfaceHeaderRule());
						}
						add(
							$current,
							"typeVars",
							lv_typeVars_3_0,
							"org.eclipse.n4js.ts.TypeExpressions.TypeVariable");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_4=Comma
				{
					newLeafNode(otherlv_4, grammarAccess.getTClassOrInterfaceHeaderAccess().getCommaKeyword_2_2_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypeVarsTypeVariableParserRuleCall_2_2_1_0());
						}
						lv_typeVars_5_0=superTypeVariable
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTClassOrInterfaceHeaderRule());
							}
							add(
								$current,
								"typeVars",
								lv_typeVars_5_0,
								"org.eclipse.n4js.ts.TypeExpressions.TypeVariable");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
			otherlv_6=GreaterThanSign
			{
				newLeafNode(otherlv_6, grammarAccess.getTClassOrInterfaceHeaderAccess().getGreaterThanSignKeyword_2_3());
			}
		)?
	)
;

// Entry rule entryRuleCallableCtor
entryRuleCallableCtor returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getCallableCtorRule()); }
	iv_ruleCallableCtor=ruleCallableCtor
	{ $current=$iv_ruleCallableCtor.current; }
	EOF;

// Rule CallableCtor
ruleCallableCtor returns [EObject current=null]
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
					grammarAccess.getCallableCtorAccess().getTMethodAction_0(),
					$current);
			}
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getCallableCtorRule());
			}
			newCompositeNode(grammarAccess.getCallableCtorAccess().getTFormalParametersParserRuleCall_1());
		}
		this_TFormalParameters_1=ruleTFormalParameters[$current]
		{
			$current = $this_TFormalParameters_1.current;
			afterParserOrEnumRuleCall();
		}
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getCallableCtorRule());
				}
				newCompositeNode(grammarAccess.getCallableCtorAccess().getColonSepReturnTypeRefParserRuleCall_2());
			}
			this_ColonSepReturnTypeRef_2=ruleColonSepReturnTypeRef[$current]
			{
				$current = $this_ColonSepReturnTypeRef_2.current;
				afterParserOrEnumRuleCall();
			}
		)?
		(
			otherlv_3=Semicolon
			{
				newLeafNode(otherlv_3, grammarAccess.getCallableCtorAccess().getSemicolonKeyword_3());
			}
		)?
	)
;


// Rule TFormalParameters
ruleTFormalParameters[EObject in_current]  returns [EObject current=in_current]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0=LeftParenthesis
		{
			newLeafNode(otherlv_0, grammarAccess.getTFormalParametersAccess().getLeftParenthesisKeyword_0());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getTFormalParametersAccess().getFparsTFormalParameterParserRuleCall_1_0_0());
					}
					lv_fpars_1_0=ruleTFormalParameter
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTFormalParametersRule());
						}
						add(
							$current,
							"fpars",
							lv_fpars_1_0,
							"org.eclipse.n4js.ts.TypeExpressions.TFormalParameter");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_2=Comma
				{
					newLeafNode(otherlv_2, grammarAccess.getTFormalParametersAccess().getCommaKeyword_1_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getTFormalParametersAccess().getFparsTFormalParameterParserRuleCall_1_1_1_0());
						}
						lv_fpars_3_0=ruleTFormalParameter
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTFormalParametersRule());
							}
							add(
								$current,
								"fpars",
								lv_fpars_3_0,
								"org.eclipse.n4js.ts.TypeExpressions.TFormalParameter");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_4=RightParenthesis
		{
			newLeafNode(otherlv_4, grammarAccess.getTFormalParametersAccess().getRightParenthesisKeyword_2());
		}
	)
;

// Entry rule entryRuleTMember
entryRuleTMember returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTMemberRule()); }
	iv_ruleTMember=ruleTMember
	{ $current=$iv_ruleTMember.current; }
	EOF;

// Rule TMember
ruleTMember returns [EObject current=null]
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
						ruleMemberAccessModifier
					)
				)
				(
					(
						(
							Abstract
						)
					)
					    |
					(
						(
							Static
						)
					)
				)?
				Get
				(
					(
						(
							ruleTypesIdentifier
						)
					)
					    |
					(
						(
							ruleTypesComputedPropertyName
						)
					)
				)
			)
			)=>
			{
				newCompositeNode(grammarAccess.getTMemberAccess().getTGetterParserRuleCall_0());
			}
			this_TGetter_0=ruleTGetter
			{
				$current = $this_TGetter_0.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
					(
						ruleMemberAccessModifier
					)
				)
				(
					(
						(
							Abstract
						)
					)
					    |
					(
						(
							Static
						)
					)
				)?
				Set
				(
					(
						(
							ruleTypesIdentifier
						)
					)
					    |
					(
						(
							ruleTypesComputedPropertyName
						)
					)
				)
			)
			)=>
			{
				newCompositeNode(grammarAccess.getTMemberAccess().getTSetterParserRuleCall_1());
			}
			this_TSetter_1=ruleTSetter
			{
				$current = $this_TSetter_1.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		(
			((
				(
					(
						ruleMemberAccessModifier
					)
				)
				(
					(
						(
							Abstract
						)
					)
					    |
					(
						(
							Static
						)
					)
				)?
				(
					(
						Async
					)
				)?
				(
					ruleTypeVariables[null]
				)?
				(
					(
						(
							ruleTypesIdentifier
						)
					)
					    |
					(
						(
							ruleTypesComputedPropertyName
						)
					)
				)
				LeftParenthesis
			)
			)=>
			{
				newCompositeNode(grammarAccess.getTMemberAccess().getTMethodParserRuleCall_2());
			}
			this_TMethod_2=ruleTMethod
			{
				$current = $this_TMethod_2.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getTMemberAccess().getTFieldParserRuleCall_3());
		}
		this_TField_3=ruleTField
		{
			$current = $this_TField_3.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleTMethod
entryRuleTMethod returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTMethodRule()); }
	iv_ruleTMethod=ruleTMethod
	{ $current=$iv_ruleTMethod.current; }
	EOF;

// Rule TMethod
ruleTMethod returns [EObject current=null]
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
						ruleMemberAccessModifier
					)
				)
				(
					(
						(
							Abstract
						)
					)
					    |
					(
						(
							Static
						)
					)
				)?
				(
					(
						Async
					)
				)?
				(
					ruleTypeVariables[null]
				)?
				(
					(
						(
							ruleTypesIdentifier
						)
					)
					    |
					(
						(
							ruleTypesComputedPropertyName
						)
					)
				)
				LeftParenthesis
			)
			)=>
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getTMethodAccess().getDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0());
						}
						lv_declaredMemberAccessModifier_0_0=ruleMemberAccessModifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTMethodRule());
							}
							set(
								$current,
								"declaredMemberAccessModifier",
								lv_declaredMemberAccessModifier_0_0,
								"org.eclipse.n4js.ts.Types.MemberAccessModifier");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(
						(
							lv_declaredAbstract_1_0=Abstract
							{
								newLeafNode(lv_declaredAbstract_1_0, grammarAccess.getTMethodAccess().getDeclaredAbstractAbstractKeyword_0_0_1_0_0());
							}
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getTMethodRule());
								}
								setWithLastConsumed($current, "declaredAbstract", true, "abstract");
							}
						)
					)
					    |
					(
						(
							lv_declaredStatic_2_0=Static
							{
								newLeafNode(lv_declaredStatic_2_0, grammarAccess.getTMethodAccess().getDeclaredStaticStaticKeyword_0_0_1_1_0());
							}
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getTMethodRule());
								}
								setWithLastConsumed($current, "declaredStatic", true, "static");
							}
						)
					)
				)?
				(
					(
						lv_declaredAsync_3_0=Async
						{
							newLeafNode(lv_declaredAsync_3_0, grammarAccess.getTMethodAccess().getDeclaredAsyncAsyncKeyword_0_0_2_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getTMethodRule());
							}
							setWithLastConsumed($current, "declaredAsync", true, "async");
						}
					)
				)?
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getTMethodRule());
						}
						newCompositeNode(grammarAccess.getTMethodAccess().getTypeVariablesParserRuleCall_0_0_3());
					}
					this_TypeVariables_4=ruleTypeVariables[$current]
					{
						$current = $this_TypeVariables_4.current;
						afterParserOrEnumRuleCall();
					}
				)?
				(
					(
						(
							{
								newCompositeNode(grammarAccess.getTMethodAccess().getNameTypesIdentifierParserRuleCall_0_0_4_0_0());
							}
							lv_name_5_0=ruleTypesIdentifier
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getTMethodRule());
								}
								set(
									$current,
									"name",
									lv_name_5_0,
									"org.eclipse.n4js.ts.Types.TypesIdentifier");
								afterParserOrEnumRuleCall();
							}
						)
					)
					    |
					(
						(
							{
								newCompositeNode(grammarAccess.getTMethodAccess().getNameTypesComputedPropertyNameParserRuleCall_0_0_4_1_0());
							}
							lv_name_6_0=ruleTypesComputedPropertyName
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getTMethodRule());
								}
								set(
									$current,
									"name",
									lv_name_6_0,
									"org.eclipse.n4js.ts.Types.TypesComputedPropertyName");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
				(
					(LeftParenthesis)=>
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getTMethodRule());
						}
						newCompositeNode(grammarAccess.getTMethodAccess().getTFormalParametersParserRuleCall_0_0_5());
					}
					this_TFormalParameters_7=ruleTFormalParameters[$current]
					{
						$current = $this_TFormalParameters_7.current;
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getTMethodRule());
			}
			newCompositeNode(grammarAccess.getTMethodAccess().getColonSepReturnTypeRefParserRuleCall_1());
		}
		this_ColonSepReturnTypeRef_8=ruleColonSepReturnTypeRef[$current]
		{
			$current = $this_ColonSepReturnTypeRef_8.current;
			afterParserOrEnumRuleCall();
		}
		(
			otherlv_9=Semicolon
			{
				newLeafNode(otherlv_9, grammarAccess.getTMethodAccess().getSemicolonKeyword_2());
			}
		)?
	)
;

// Entry rule entryRuleTField
entryRuleTField returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTFieldRule()); }
	iv_ruleTField=ruleTField
	{ $current=$iv_ruleTField.current; }
	EOF;

// Rule TField
ruleTField returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getTFieldAccess().getDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0());
				}
				lv_declaredMemberAccessModifier_0_0=ruleMemberAccessModifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTFieldRule());
					}
					set(
						$current,
						"declaredMemberAccessModifier",
						lv_declaredMemberAccessModifier_0_0,
						"org.eclipse.n4js.ts.Types.MemberAccessModifier");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				(
					lv_declaredStatic_1_0=Static
					{
						newLeafNode(lv_declaredStatic_1_0, grammarAccess.getTFieldAccess().getDeclaredStaticStaticKeyword_1_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getTFieldRule());
						}
						setWithLastConsumed($current, "declaredStatic", true, "static");
					}
				)
			)
			    |
			(
				(
					lv_const_2_0=Const
					{
						newLeafNode(lv_const_2_0, grammarAccess.getTFieldAccess().getConstConstKeyword_1_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getTFieldRule());
						}
						setWithLastConsumed($current, "const", true, "const");
					}
				)
			)
			    |
			(
				(
					lv_declaredFinal_3_0=Final
					{
						newLeafNode(lv_declaredFinal_3_0, grammarAccess.getTFieldAccess().getDeclaredFinalFinalKeyword_1_2_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getTFieldRule());
						}
						setWithLastConsumed($current, "declaredFinal", true, "final");
					}
				)
			)
		)?
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getTFieldAccess().getNameTypesIdentifierParserRuleCall_2_0_0());
					}
					lv_name_4_0=ruleTypesIdentifier
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTFieldRule());
						}
						set(
							$current,
							"name",
							lv_name_4_0,
							"org.eclipse.n4js.ts.Types.TypesIdentifier");
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getTFieldAccess().getNameTypesComputedPropertyNameParserRuleCall_2_1_0());
					}
					lv_name_5_0=ruleTypesComputedPropertyName
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTFieldRule());
						}
						set(
							$current,
							"name",
							lv_name_5_0,
							"org.eclipse.n4js.ts.Types.TypesComputedPropertyName");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		(
			(
				lv_optional_6_0=QuestionMark
				{
					newLeafNode(lv_optional_6_0, grammarAccess.getTFieldAccess().getOptionalQuestionMarkKeyword_3_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTFieldRule());
					}
					setWithLastConsumed($current, "optional", true, "?");
				}
			)
		)?
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getTFieldRule());
			}
			newCompositeNode(grammarAccess.getTFieldAccess().getColonSepTypeRefParserRuleCall_4());
		}
		this_ColonSepTypeRef_7=ruleColonSepTypeRef[$current]
		{
			$current = $this_ColonSepTypeRef_7.current;
			afterParserOrEnumRuleCall();
		}
		(
			otherlv_8=Semicolon
			{
				newLeafNode(otherlv_8, grammarAccess.getTFieldAccess().getSemicolonKeyword_5());
			}
		)?
	)
;

// Entry rule entryRuleTGetter
entryRuleTGetter returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTGetterRule()); }
	iv_ruleTGetter=ruleTGetter
	{ $current=$iv_ruleTGetter.current; }
	EOF;

// Rule TGetter
ruleTGetter returns [EObject current=null]
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
						ruleMemberAccessModifier
					)
				)
				(
					(
						(
							Abstract
						)
					)
					    |
					(
						(
							Static
						)
					)
				)?
				Get
				(
					(
						(
							ruleTypesIdentifier
						)
					)
					    |
					(
						(
							ruleTypesComputedPropertyName
						)
					)
				)
			)
			)=>
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getTGetterAccess().getDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0());
						}
						lv_declaredMemberAccessModifier_0_0=ruleMemberAccessModifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTGetterRule());
							}
							set(
								$current,
								"declaredMemberAccessModifier",
								lv_declaredMemberAccessModifier_0_0,
								"org.eclipse.n4js.ts.Types.MemberAccessModifier");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(
						(
							lv_declaredAbstract_1_0=Abstract
							{
								newLeafNode(lv_declaredAbstract_1_0, grammarAccess.getTGetterAccess().getDeclaredAbstractAbstractKeyword_0_0_1_0_0());
							}
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getTGetterRule());
								}
								setWithLastConsumed($current, "declaredAbstract", true, "abstract");
							}
						)
					)
					    |
					(
						(
							lv_declaredStatic_2_0=Static
							{
								newLeafNode(lv_declaredStatic_2_0, grammarAccess.getTGetterAccess().getDeclaredStaticStaticKeyword_0_0_1_1_0());
							}
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getTGetterRule());
								}
								setWithLastConsumed($current, "declaredStatic", true, "static");
							}
						)
					)
				)?
				otherlv_3=Get
				{
					newLeafNode(otherlv_3, grammarAccess.getTGetterAccess().getGetKeyword_0_0_2());
				}
				(
					(
						(
							{
								newCompositeNode(grammarAccess.getTGetterAccess().getNameTypesIdentifierParserRuleCall_0_0_3_0_0());
							}
							lv_name_4_0=ruleTypesIdentifier
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getTGetterRule());
								}
								set(
									$current,
									"name",
									lv_name_4_0,
									"org.eclipse.n4js.ts.Types.TypesIdentifier");
								afterParserOrEnumRuleCall();
							}
						)
					)
					    |
					(
						(
							{
								newCompositeNode(grammarAccess.getTGetterAccess().getNameTypesComputedPropertyNameParserRuleCall_0_0_3_1_0());
							}
							lv_name_5_0=ruleTypesComputedPropertyName
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getTGetterRule());
								}
								set(
									$current,
									"name",
									lv_name_5_0,
									"org.eclipse.n4js.ts.Types.TypesComputedPropertyName");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
		)
		(
			(
				lv_optional_6_0=QuestionMark
				{
					newLeafNode(lv_optional_6_0, grammarAccess.getTGetterAccess().getOptionalQuestionMarkKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTGetterRule());
					}
					setWithLastConsumed($current, "optional", true, "?");
				}
			)
		)?
		otherlv_7=LeftParenthesis
		{
			newLeafNode(otherlv_7, grammarAccess.getTGetterAccess().getLeftParenthesisKeyword_2());
		}
		otherlv_8=RightParenthesis
		{
			newLeafNode(otherlv_8, grammarAccess.getTGetterAccess().getRightParenthesisKeyword_3());
		}
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getTGetterRule());
			}
			newCompositeNode(grammarAccess.getTGetterAccess().getColonSepTypeRefParserRuleCall_4());
		}
		this_ColonSepTypeRef_9=ruleColonSepTypeRef[$current]
		{
			$current = $this_ColonSepTypeRef_9.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleTSetter
entryRuleTSetter returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTSetterRule()); }
	iv_ruleTSetter=ruleTSetter
	{ $current=$iv_ruleTSetter.current; }
	EOF;

// Rule TSetter
ruleTSetter returns [EObject current=null]
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
						ruleMemberAccessModifier
					)
				)
				(
					(
						(
							Abstract
						)
					)
					    |
					(
						(
							Static
						)
					)
				)?
				Set
				(
					(
						(
							ruleTypesIdentifier
						)
					)
					    |
					(
						(
							ruleTypesComputedPropertyName
						)
					)
				)
			)
			)=>
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getTSetterAccess().getDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0());
						}
						lv_declaredMemberAccessModifier_0_0=ruleMemberAccessModifier
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getTSetterRule());
							}
							set(
								$current,
								"declaredMemberAccessModifier",
								lv_declaredMemberAccessModifier_0_0,
								"org.eclipse.n4js.ts.Types.MemberAccessModifier");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(
						(
							lv_declaredAbstract_1_0=Abstract
							{
								newLeafNode(lv_declaredAbstract_1_0, grammarAccess.getTSetterAccess().getDeclaredAbstractAbstractKeyword_0_0_1_0_0());
							}
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getTSetterRule());
								}
								setWithLastConsumed($current, "declaredAbstract", true, "abstract");
							}
						)
					)
					    |
					(
						(
							lv_declaredStatic_2_0=Static
							{
								newLeafNode(lv_declaredStatic_2_0, grammarAccess.getTSetterAccess().getDeclaredStaticStaticKeyword_0_0_1_1_0());
							}
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getTSetterRule());
								}
								setWithLastConsumed($current, "declaredStatic", true, "static");
							}
						)
					)
				)?
				otherlv_3=Set
				{
					newLeafNode(otherlv_3, grammarAccess.getTSetterAccess().getSetKeyword_0_0_2());
				}
				(
					(
						(
							{
								newCompositeNode(grammarAccess.getTSetterAccess().getNameTypesIdentifierParserRuleCall_0_0_3_0_0());
							}
							lv_name_4_0=ruleTypesIdentifier
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getTSetterRule());
								}
								set(
									$current,
									"name",
									lv_name_4_0,
									"org.eclipse.n4js.ts.Types.TypesIdentifier");
								afterParserOrEnumRuleCall();
							}
						)
					)
					    |
					(
						(
							{
								newCompositeNode(grammarAccess.getTSetterAccess().getNameTypesComputedPropertyNameParserRuleCall_0_0_3_1_0());
							}
							lv_name_5_0=ruleTypesComputedPropertyName
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getTSetterRule());
								}
								set(
									$current,
									"name",
									lv_name_5_0,
									"org.eclipse.n4js.ts.Types.TypesComputedPropertyName");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
		)
		(
			(
				lv_optional_6_0=QuestionMark
				{
					newLeafNode(lv_optional_6_0, grammarAccess.getTSetterAccess().getOptionalQuestionMarkKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTSetterRule());
					}
					setWithLastConsumed($current, "optional", true, "?");
				}
			)
		)?
		otherlv_7=LeftParenthesis
		{
			newLeafNode(otherlv_7, grammarAccess.getTSetterAccess().getLeftParenthesisKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getTSetterAccess().getFparTFormalParameterParserRuleCall_3_0());
				}
				lv_fpar_8_0=ruleTFormalParameter
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTSetterRule());
					}
					set(
						$current,
						"fpar",
						lv_fpar_8_0,
						"org.eclipse.n4js.ts.TypeExpressions.TFormalParameter");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_9=RightParenthesis
		{
			newLeafNode(otherlv_9, grammarAccess.getTSetterAccess().getRightParenthesisKeyword_4());
		}
	)
;

// Entry rule entryRuleTFunction
entryRuleTFunction returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTFunctionRule()); }
	iv_ruleTFunction=ruleTFunction
	{ $current=$iv_ruleTFunction.current; }
	EOF;

// Rule TFunction
ruleTFunction returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getTFunctionAccess().getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0());
				}
				lv_declaredTypeAccessModifier_0_0=ruleTypeAccessModifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTFunctionRule());
					}
					set(
						$current,
						"declaredTypeAccessModifier",
						lv_declaredTypeAccessModifier_0_0,
						"org.eclipse.n4js.ts.Types.TypeAccessModifier");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				lv_declaredProvidedByRuntime_1_0=ProvidedByRuntime
				{
					newLeafNode(lv_declaredProvidedByRuntime_1_0, grammarAccess.getTFunctionAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTFunctionRule());
					}
					setWithLastConsumed($current, "declaredProvidedByRuntime", true, "providedByRuntime");
				}
			)
		)?
		otherlv_2=Function
		{
			newLeafNode(otherlv_2, grammarAccess.getTFunctionAccess().getFunctionKeyword_2());
		}
		(
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getTFunctionRule());
				}
				newCompositeNode(grammarAccess.getTFunctionAccess().getTypeVariablesParserRuleCall_3());
			}
			this_TypeVariables_3=ruleTypeVariables[$current]
			{
				$current = $this_TypeVariables_3.current;
				afterParserOrEnumRuleCall();
			}
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getTFunctionAccess().getNameBindingTypesIdentifierParserRuleCall_4_0());
				}
				lv_name_4_0=ruleBindingTypesIdentifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTFunctionRule());
					}
					set(
						$current,
						"name",
						lv_name_4_0,
						"org.eclipse.n4js.ts.Types.BindingTypesIdentifier");
					afterParserOrEnumRuleCall();
				}
			)
		)
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getTFunctionRule());
			}
			newCompositeNode(grammarAccess.getTFunctionAccess().getTFormalParametersParserRuleCall_5());
		}
		this_TFormalParameters_5=ruleTFormalParameters[$current]
		{
			$current = $this_TFormalParameters_5.current;
			afterParserOrEnumRuleCall();
		}
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getTFunctionRule());
			}
			newCompositeNode(grammarAccess.getTFunctionAccess().getColonSepReturnTypeRefParserRuleCall_6());
		}
		this_ColonSepReturnTypeRef_6=ruleColonSepReturnTypeRef[$current]
		{
			$current = $this_ColonSepReturnTypeRef_6.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleTEnum
entryRuleTEnum returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTEnumRule()); }
	iv_ruleTEnum=ruleTEnum
	{ $current=$iv_ruleTEnum.current; }
	EOF;

// Rule TEnum
ruleTEnum returns [EObject current=null]
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
					newCompositeNode(grammarAccess.getTEnumAccess().getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0());
				}
				lv_declaredTypeAccessModifier_0_0=ruleTypeAccessModifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTEnumRule());
					}
					set(
						$current,
						"declaredTypeAccessModifier",
						lv_declaredTypeAccessModifier_0_0,
						"org.eclipse.n4js.ts.Types.TypeAccessModifier");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				lv_declaredProvidedByRuntime_1_0=ProvidedByRuntime
				{
					newLeafNode(lv_declaredProvidedByRuntime_1_0, grammarAccess.getTEnumAccess().getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTEnumRule());
					}
					setWithLastConsumed($current, "declaredProvidedByRuntime", true, "providedByRuntime");
				}
			)
		)?
		otherlv_2=Enum
		{
			newLeafNode(otherlv_2, grammarAccess.getTEnumAccess().getEnumKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getTEnumAccess().getNameBindingTypesIdentifierParserRuleCall_3_0());
				}
				lv_name_3_0=ruleBindingTypesIdentifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTEnumRule());
					}
					set(
						$current,
						"name",
						lv_name_3_0,
						"org.eclipse.n4js.ts.Types.BindingTypesIdentifier");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_4=LeftCurlyBracket
		{
			newLeafNode(otherlv_4, grammarAccess.getTEnumAccess().getLeftCurlyBracketKeyword_4());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getTEnumAccess().getLiteralsTEnumLiteralParserRuleCall_5_0());
				}
				lv_literals_5_0=ruleTEnumLiteral
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTEnumRule());
					}
					add(
						$current,
						"literals",
						lv_literals_5_0,
						"org.eclipse.n4js.ts.Types.TEnumLiteral");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			otherlv_6=Comma
			{
				newLeafNode(otherlv_6, grammarAccess.getTEnumAccess().getCommaKeyword_6_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getTEnumAccess().getLiteralsTEnumLiteralParserRuleCall_6_1_0());
					}
					lv_literals_7_0=ruleTEnumLiteral
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTEnumRule());
						}
						add(
							$current,
							"literals",
							lv_literals_7_0,
							"org.eclipse.n4js.ts.Types.TEnumLiteral");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
		otherlv_8=RightCurlyBracket
		{
			newLeafNode(otherlv_8, grammarAccess.getTEnumAccess().getRightCurlyBracketKeyword_7());
		}
	)
;

// Entry rule entryRuleTEnumLiteral
entryRuleTEnumLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTEnumLiteralRule()); }
	iv_ruleTEnumLiteral=ruleTEnumLiteral
	{ $current=$iv_ruleTEnumLiteral.current; }
	EOF;

// Rule TEnumLiteral
ruleTEnumLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			lv_name_0_0=RULE_IDENTIFIER
			{
				newLeafNode(lv_name_0_0, grammarAccess.getTEnumLiteralAccess().getNameIDENTIFIERTerminalRuleCall_0());
			}
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getTEnumLiteralRule());
				}
				setWithLastConsumed(
					$current,
					"name",
					lv_name_0_0,
					"org.eclipse.n4js.ts.TypeExpressions.IDENTIFIER");
			}
		)
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
							"org.eclipse.n4js.ts.Types.TypeVariable");
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
								"org.eclipse.n4js.ts.Types.TypeVariable");
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
							"org.eclipse.n4js.ts.Types.TypeRef");
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

// Entry rule entryRuleTFormalParameter
entryRuleTFormalParameter returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTFormalParameterRule()); }
	iv_ruleTFormalParameter=ruleTFormalParameter
	{ $current=$iv_ruleTFormalParameter.current; }
	EOF;

// Rule TFormalParameter
ruleTFormalParameter returns [EObject current=null]
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
					newLeafNode(lv_variadic_0_0, grammarAccess.getTFormalParameterAccess().getVariadicFullStopFullStopFullStopKeyword_0_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTFormalParameterRule());
					}
					setWithLastConsumed($current, "variadic", true, "...");
				}
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getTFormalParameterAccess().getNameBindingIdentifierParserRuleCall_1_0());
				}
				lv_name_1_0=ruleBindingIdentifier
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getTFormalParameterRule());
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
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getTFormalParameterRule());
			}
			newCompositeNode(grammarAccess.getTFormalParameterAccess().getColonSepTypeRefParserRuleCall_2());
		}
		this_ColonSepTypeRef_2=ruleColonSepTypeRef[$current]
		{
			$current = $this_ColonSepTypeRef_2.current;
			afterParserOrEnumRuleCall();
		}
		{
			if ($current==null) {
				$current = createModelElement(grammarAccess.getTFormalParameterRule());
			}
			newCompositeNode(grammarAccess.getTFormalParameterAccess().getDefaultFormalParameterParserRuleCall_3());
		}
		this_DefaultFormalParameter_3=ruleDefaultFormalParameter[$current]
		{
			$current = $this_DefaultFormalParameter_3.current;
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
						"org.eclipse.n4js.ts.Types.TypeReferenceName");
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
						"org.eclipse.n4js.ts.Types.TypeRef");
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
							"org.eclipse.n4js.ts.Types.TypeRef");
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
						"org.eclipse.n4js.ts.Types.TypeRef");
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
							"org.eclipse.n4js.ts.Types.TypeRef");
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
			{
				if ($current==null) {
					$current = createModelElement(grammarAccess.getTypeReferenceRule());
				}
			}
			{
				newCompositeNode(grammarAccess.getTypeReferenceAccess().getDeclaredTypeTypeCrossReference_0());
			}
			ruleTypeReferenceName
			{
				afterParserOrEnumRuleCall();
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
						"org.eclipse.n4js.ts.Types.TypeVariable");
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
							"org.eclipse.n4js.ts.Types.TypeVariable");
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
						"org.eclipse.n4js.ts.Types.TypeRef");
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
						"org.eclipse.n4js.ts.Types.TypeRef");
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
				newCompositeNode(grammarAccess.getTStructGetterAccess().getColonSepTypeRefParserRuleCall_4());
			}
			this_ColonSepTypeRef_6=ruleColonSepTypeRef[$current]
			{
				$current = $this_ColonSepTypeRef_6.current;
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
								"org.eclipse.n4js.ts.Types.TypeRef");
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
								"org.eclipse.n4js.ts.Types.TypeRef");
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
							"org.eclipse.n4js.ts.Types.TypeRef");
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
							"org.eclipse.n4js.ts.Types.TypeRef");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
	)
;

// Entry rule entrySuperTypeVariable
entrySuperTypeVariable returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getTypeExpressionsTypeVariableRule()); }
	iv_superTypeVariable=superTypeVariable
	{ $current=$iv_superTypeVariable.current; }
	EOF;

// Rule TypeVariable
superTypeVariable returns [EObject current=null]
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
						newLeafNode(lv_declaredCovariant_0_0, grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredCovariantOutKeyword_0_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getTypeExpressionsTypeVariableRule());
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
						newLeafNode(lv_declaredContravariant_1_0, grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredContravariantInKeyword_0_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getTypeExpressionsTypeVariableRule());
						}
						setWithLastConsumed($current, "declaredContravariant", true, "in");
					}
				)
			)
		)?
		(
			(
				lv_name_2_0=RULE_IDENTIFIER
				{
					newLeafNode(lv_name_2_0, grammarAccess.getTypeExpressionsTypeVariableAccess().getNameIDENTIFIERTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getTypeExpressionsTypeVariableRule());
					}
					setWithLastConsumed(
						$current,
						"name",
						lv_name_2_0,
						"org.eclipse.n4js.ts.TypeExpressions.IDENTIFIER");
				}
			)
		)
		(
			otherlv_3=Extends
			{
				newLeafNode(otherlv_3, grammarAccess.getTypeExpressionsTypeVariableAccess().getExtendsKeyword_2_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredUpperBoundTypeRefParserRuleCall_2_1_0());
					}
					lv_declaredUpperBound_4_0=ruleTypeRef
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getTypeExpressionsTypeVariableRule());
						}
						set(
							$current,
							"declaredUpperBound",
							lv_declaredUpperBound_4_0,
							"org.eclipse.n4js.ts.Types.TypeRef");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
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

// Rule TypeAccessModifier
ruleTypeAccessModifier returns [Enumerator current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			enumLiteral_0=Project
			{
				$current = grammarAccess.getTypeAccessModifierAccess().getProjectEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getTypeAccessModifierAccess().getProjectEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=PublicInternal
			{
				$current = grammarAccess.getTypeAccessModifierAccess().getPublicInternalEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getTypeAccessModifierAccess().getPublicInternalEnumLiteralDeclaration_1());
			}
		)
		    |
		(
			enumLiteral_2=Public
			{
				$current = grammarAccess.getTypeAccessModifierAccess().getPublicEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_2, grammarAccess.getTypeAccessModifierAccess().getPublicEnumLiteralDeclaration_2());
			}
		)
	)
;

// Rule MemberAccessModifier
ruleMemberAccessModifier returns [Enumerator current=null]
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
				$current = grammarAccess.getMemberAccessModifierAccess().getPrivateEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_0, grammarAccess.getMemberAccessModifierAccess().getPrivateEnumLiteralDeclaration_0());
			}
		)
		    |
		(
			enumLiteral_1=Project
			{
				$current = grammarAccess.getMemberAccessModifierAccess().getProjectEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_1, grammarAccess.getMemberAccessModifierAccess().getProjectEnumLiteralDeclaration_1());
			}
		)
		    |
		(
			enumLiteral_2=ProtectedInternal
			{
				$current = grammarAccess.getMemberAccessModifierAccess().getProtectedInternalEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_2, grammarAccess.getMemberAccessModifierAccess().getProtectedInternalEnumLiteralDeclaration_2());
			}
		)
		    |
		(
			enumLiteral_3=Protected
			{
				$current = grammarAccess.getMemberAccessModifierAccess().getProtectedEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_3, grammarAccess.getMemberAccessModifierAccess().getProtectedEnumLiteralDeclaration_3());
			}
		)
		    |
		(
			enumLiteral_4=PublicInternal
			{
				$current = grammarAccess.getMemberAccessModifierAccess().getPublicInternalEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_4, grammarAccess.getMemberAccessModifierAccess().getPublicInternalEnumLiteralDeclaration_4());
			}
		)
		    |
		(
			enumLiteral_5=Public
			{
				$current = grammarAccess.getMemberAccessModifierAccess().getPublicEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
				newLeafNode(enumLiteral_5, grammarAccess.getMemberAccessModifierAccess().getPublicEnumLiteralDeclaration_5());
			}
		)
	)
;
