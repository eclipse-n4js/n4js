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
package org.eclipse.n4js.ts.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import org.eclipse.n4js.common.unicode.services.UnicodeGrammarAccess;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.EnumLiteralDeclaration;
import org.eclipse.xtext.EnumRule;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.service.AbstractElementFinder.AbstractEnumRuleElementFinder;
import org.eclipse.xtext.service.AbstractElementFinder.AbstractGrammarElementFinder;
import org.eclipse.xtext.service.GrammarProvider;

@Singleton
public class TypesGrammarAccess extends AbstractGrammarElementFinder {
	
	public class TypeDefsElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TypeDefs");
		private final Assignment cTypesAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cTypesTypeParserRuleCall_0 = (RuleCall)cTypesAssignment.eContents().get(0);
		
		//// ****************************************************************************************************
		//// Root, only used in case types are explicitly defined, e.g., for built-in types.
		//// ****************************************************************************************************
		//TypeDefs:
		//	types+=Type*;
		@Override public ParserRule getRule() { return rule; }
		
		//types+=Type*
		public Assignment getTypesAssignment() { return cTypesAssignment; }
		
		//Type
		public RuleCall getTypesTypeParserRuleCall_0() { return cTypesTypeParserRuleCall_0; }
	}
	public class TAnnotationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TAnnotation");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Keyword cCommercialAtKeyword_0_0_0 = (Keyword)cGroup_0_0.eContents().get(0);
		private final Assignment cNameAssignment_0_0_1 = (Assignment)cGroup_0_0.eContents().get(1);
		private final RuleCall cNameIDENTIFIERTerminalRuleCall_0_0_1_0 = (RuleCall)cNameAssignment_0_0_1.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cLeftParenthesisKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cGroup_1.eContents().get(1);
		private final Assignment cArgsAssignment_1_1_0 = (Assignment)cGroup_1_1.eContents().get(0);
		private final RuleCall cArgsTAnnotationArgumentParserRuleCall_1_1_0_0 = (RuleCall)cArgsAssignment_1_1_0.eContents().get(0);
		private final Group cGroup_1_1_1 = (Group)cGroup_1_1.eContents().get(1);
		private final Keyword cCommaKeyword_1_1_1_0 = (Keyword)cGroup_1_1_1.eContents().get(0);
		private final Assignment cArgsAssignment_1_1_1_1 = (Assignment)cGroup_1_1_1.eContents().get(1);
		private final RuleCall cArgsTAnnotationArgumentParserRuleCall_1_1_1_1_0 = (RuleCall)cArgsAssignment_1_1_1_1.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_1_2 = (Keyword)cGroup_1.eContents().get(2);
		
		//// cf. N4JSSpec §9
		//TAnnotation:
		//	=> ('@' name=IDENTIFIER) (=> '(' (args+=TAnnotationArgument (',' args+=TAnnotationArgument)*)? ')')?;
		@Override public ParserRule getRule() { return rule; }
		
		//=> ('@' name=IDENTIFIER) (=> '(' (args+=TAnnotationArgument (',' args+=TAnnotationArgument)*)? ')')?
		public Group getGroup() { return cGroup; }
		
		//=> ('@' name=IDENTIFIER)
		public Group getGroup_0() { return cGroup_0; }
		
		//'@' name=IDENTIFIER
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//'@'
		public Keyword getCommercialAtKeyword_0_0_0() { return cCommercialAtKeyword_0_0_0; }
		
		//name=IDENTIFIER
		public Assignment getNameAssignment_0_0_1() { return cNameAssignment_0_0_1; }
		
		//IDENTIFIER
		public RuleCall getNameIDENTIFIERTerminalRuleCall_0_0_1_0() { return cNameIDENTIFIERTerminalRuleCall_0_0_1_0; }
		
		//(=> '(' (args+=TAnnotationArgument (',' args+=TAnnotationArgument)*)? ')')?
		public Group getGroup_1() { return cGroup_1; }
		
		//=> '('
		public Keyword getLeftParenthesisKeyword_1_0() { return cLeftParenthesisKeyword_1_0; }
		
		//(args+=TAnnotationArgument (',' args+=TAnnotationArgument)*)?
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//args+=TAnnotationArgument
		public Assignment getArgsAssignment_1_1_0() { return cArgsAssignment_1_1_0; }
		
		//TAnnotationArgument
		public RuleCall getArgsTAnnotationArgumentParserRuleCall_1_1_0_0() { return cArgsTAnnotationArgumentParserRuleCall_1_1_0_0; }
		
		//(',' args+=TAnnotationArgument)*
		public Group getGroup_1_1_1() { return cGroup_1_1_1; }
		
		//','
		public Keyword getCommaKeyword_1_1_1_0() { return cCommaKeyword_1_1_1_0; }
		
		//args+=TAnnotationArgument
		public Assignment getArgsAssignment_1_1_1_1() { return cArgsAssignment_1_1_1_1; }
		
		//TAnnotationArgument
		public RuleCall getArgsTAnnotationArgumentParserRuleCall_1_1_1_1_0() { return cArgsTAnnotationArgumentParserRuleCall_1_1_1_1_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_1_2() { return cRightParenthesisKeyword_1_2; }
	}
	public class TAnnotationArgumentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TAnnotationArgument");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cTAnnotationStringArgumentParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cTAnnotationTypeRefArgumentParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//TAnnotationArgument:
		//	TAnnotationStringArgument | TAnnotationTypeRefArgument;
		@Override public ParserRule getRule() { return rule; }
		
		//TAnnotationStringArgument | TAnnotationTypeRefArgument
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//TAnnotationStringArgument
		public RuleCall getTAnnotationStringArgumentParserRuleCall_0() { return cTAnnotationStringArgumentParserRuleCall_0; }
		
		//TAnnotationTypeRefArgument
		public RuleCall getTAnnotationTypeRefArgumentParserRuleCall_1() { return cTAnnotationTypeRefArgumentParserRuleCall_1; }
	}
	public class TAnnotationStringArgumentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TAnnotationStringArgument");
		private final Assignment cValueAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cValueSTRINGTerminalRuleCall_0 = (RuleCall)cValueAssignment.eContents().get(0);
		
		//TAnnotationStringArgument:
		//	value=STRING;
		@Override public ParserRule getRule() { return rule; }
		
		//value=STRING
		public Assignment getValueAssignment() { return cValueAssignment; }
		
		//STRING
		public RuleCall getValueSTRINGTerminalRuleCall_0() { return cValueSTRINGTerminalRuleCall_0; }
	}
	public class TAnnotationTypeRefArgumentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TAnnotationTypeRefArgument");
		private final Assignment cTypeRefAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cTypeRefTypeRefParserRuleCall_0 = (RuleCall)cTypeRefAssignment.eContents().get(0);
		
		//TAnnotationTypeRefArgument:
		//	typeRef=TypeRef;
		@Override public ParserRule getRule() { return rule; }
		
		//typeRef=TypeRef
		public Assignment getTypeRefAssignment() { return cTypeRefAssignment; }
		
		//TypeRef
		public RuleCall getTypeRefTypeRefParserRuleCall_0() { return cTypeRefTypeRefParserRuleCall_0; }
	}
	public class TypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.Type");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cTObjectPrototypeParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cTClassParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cTInterfaceParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cTEnumParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cAnyTypeParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		private final RuleCall cVoidTypeParserRuleCall_5 = (RuleCall)cAlternatives.eContents().get(5);
		private final RuleCall cUndefinedTypeParserRuleCall_6 = (RuleCall)cAlternatives.eContents().get(6);
		private final RuleCall cNullTypeParserRuleCall_7 = (RuleCall)cAlternatives.eContents().get(7);
		private final RuleCall cPrimitiveTypeParserRuleCall_8 = (RuleCall)cAlternatives.eContents().get(8);
		private final RuleCall cTFunctionParserRuleCall_9 = (RuleCall)cAlternatives.eContents().get(9);
		private final RuleCall cTypeVariableParserRuleCall_10 = (RuleCall)cAlternatives.eContents().get(10);
		private final RuleCall cVirtualBaseTypeParserRuleCall_11 = (RuleCall)cAlternatives.eContents().get(11);
		
		//// TODO jvp: more type information, cf. types model
		///*
		// * We cannot define a TypeVariableRef, as this cannot be distinguished from a ParameterizedTypeRef. That is,
		// * we cannot distinguish whether 'T' is a type variable T, or a type with name 'T'. Thus,
		// * type variables are handled similar to types (and are actually types).
		// */ Type:
		//	TObjectPrototype
		//	| TClass
		//	| TInterface
		//	| TEnum
		//	| AnyType
		//	| VoidType
		//	| UndefinedType
		//	| NullType
		//	| PrimitiveType
		//	| TFunction
		//	| TypeVariable
		//	| VirtualBaseType;
		@Override public ParserRule getRule() { return rule; }
		
		//TObjectPrototype | TClass | TInterface | TEnum | AnyType | VoidType | UndefinedType | NullType | PrimitiveType |
		//TFunction | TypeVariable | VirtualBaseType
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//TObjectPrototype
		public RuleCall getTObjectPrototypeParserRuleCall_0() { return cTObjectPrototypeParserRuleCall_0; }
		
		//TClass
		public RuleCall getTClassParserRuleCall_1() { return cTClassParserRuleCall_1; }
		
		//TInterface
		public RuleCall getTInterfaceParserRuleCall_2() { return cTInterfaceParserRuleCall_2; }
		
		//TEnum
		public RuleCall getTEnumParserRuleCall_3() { return cTEnumParserRuleCall_3; }
		
		//AnyType
		public RuleCall getAnyTypeParserRuleCall_4() { return cAnyTypeParserRuleCall_4; }
		
		//VoidType
		public RuleCall getVoidTypeParserRuleCall_5() { return cVoidTypeParserRuleCall_5; }
		
		//UndefinedType
		public RuleCall getUndefinedTypeParserRuleCall_6() { return cUndefinedTypeParserRuleCall_6; }
		
		//NullType
		public RuleCall getNullTypeParserRuleCall_7() { return cNullTypeParserRuleCall_7; }
		
		//PrimitiveType
		public RuleCall getPrimitiveTypeParserRuleCall_8() { return cPrimitiveTypeParserRuleCall_8; }
		
		//TFunction
		public RuleCall getTFunctionParserRuleCall_9() { return cTFunctionParserRuleCall_9; }
		
		//TypeVariable
		public RuleCall getTypeVariableParserRuleCall_10() { return cTypeVariableParserRuleCall_10; }
		
		//VirtualBaseType
		public RuleCall getVirtualBaseTypeParserRuleCall_11() { return cVirtualBaseTypeParserRuleCall_11; }
	}
	public class TypeRefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TypeRef");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cTypeRefWithoutModifiersParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Assignment cFollowedByQuestionMarkAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cFollowedByQuestionMarkQuestionMarkKeyword_1_0 = (Keyword)cFollowedByQuestionMarkAssignment_1.eContents().get(0);
		
		//@Override
		//TypeRef:
		//	TypeRefWithoutModifiers followedByQuestionMark?='?'?;
		@Override public ParserRule getRule() { return rule; }
		
		//TypeRefWithoutModifiers followedByQuestionMark?='?'?
		public Group getGroup() { return cGroup; }
		
		//TypeRefWithoutModifiers
		public RuleCall getTypeRefWithoutModifiersParserRuleCall_0() { return cTypeRefWithoutModifiersParserRuleCall_0; }
		
		//followedByQuestionMark?='?'?
		public Assignment getFollowedByQuestionMarkAssignment_1() { return cFollowedByQuestionMarkAssignment_1; }
		
		//'?'
		public Keyword getFollowedByQuestionMarkQuestionMarkKeyword_1_0() { return cFollowedByQuestionMarkQuestionMarkKeyword_1_0; }
	}
	public class PrimitiveTypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.PrimitiveType");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cPrimitiveKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameVoidOrBindingIdentifierParserRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cLessThanSignKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Assignment cTypeVarsAssignment_2_1 = (Assignment)cGroup_2.eContents().get(1);
		private final RuleCall cTypeVarsTypeVariableParserRuleCall_2_1_0 = (RuleCall)cTypeVarsAssignment_2_1.eContents().get(0);
		private final Keyword cGreaterThanSignKeyword_2_2 = (Keyword)cGroup_2.eContents().get(2);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Keyword cIndexedKeyword_3_0 = (Keyword)cGroup_3.eContents().get(0);
		private final Assignment cDeclaredElementTypeAssignment_3_1 = (Assignment)cGroup_3.eContents().get(1);
		private final RuleCall cDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_3_1_0 = (RuleCall)cDeclaredElementTypeAssignment_3_1.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		private final Group cGroup_5 = (Group)cGroup.eContents().get(5);
		private final Keyword cAutoboxedTypeKeyword_5_0 = (Keyword)cGroup_5.eContents().get(0);
		private final Assignment cAutoboxedTypeAssignment_5_1 = (Assignment)cGroup_5.eContents().get(1);
		private final CrossReference cAutoboxedTypeTClassifierCrossReference_5_1_0 = (CrossReference)cAutoboxedTypeAssignment_5_1.eContents().get(0);
		private final RuleCall cAutoboxedTypeTClassifierTypeReferenceNameParserRuleCall_5_1_0_1 = (RuleCall)cAutoboxedTypeTClassifierCrossReference_5_1_0.eContents().get(1);
		private final Group cGroup_6 = (Group)cGroup.eContents().get(6);
		private final Keyword cAssignmnentCompatibleKeyword_6_0 = (Keyword)cGroup_6.eContents().get(0);
		private final Assignment cAssignmentCompatibleAssignment_6_1 = (Assignment)cGroup_6.eContents().get(1);
		private final CrossReference cAssignmentCompatiblePrimitiveTypeCrossReference_6_1_0 = (CrossReference)cAssignmentCompatibleAssignment_6_1.eContents().get(0);
		private final RuleCall cAssignmentCompatiblePrimitiveTypeTypeReferenceNameParserRuleCall_6_1_0_1 = (RuleCall)cAssignmentCompatiblePrimitiveTypeCrossReference_6_1_0.eContents().get(1);
		private final Keyword cRightCurlyBracketKeyword_7 = (Keyword)cGroup.eContents().get(7);
		
		//PrimitiveType:
		//	'primitive' name=VoidOrBindingIdentifier ('<' typeVars+=TypeVariable '>')? ('indexed'
		//	declaredElementType=ParameterizedTypeRefNominal)?
		//	'{' ('autoboxedType' autoboxedType=[TClassifier|TypeReferenceName])? ('assignmnentCompatible'
		//	assignmentCompatible=[PrimitiveType|TypeReferenceName])?
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//'primitive' name=VoidOrBindingIdentifier ('<' typeVars+=TypeVariable '>')? ('indexed'
		//declaredElementType=ParameterizedTypeRefNominal)? '{' ('autoboxedType' autoboxedType=[TClassifier|TypeReferenceName])?
		//('assignmnentCompatible' assignmentCompatible=[PrimitiveType|TypeReferenceName])? '}'
		public Group getGroup() { return cGroup; }
		
		//'primitive'
		public Keyword getPrimitiveKeyword_0() { return cPrimitiveKeyword_0; }
		
		//name=VoidOrBindingIdentifier
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//VoidOrBindingIdentifier
		public RuleCall getNameVoidOrBindingIdentifierParserRuleCall_1_0() { return cNameVoidOrBindingIdentifierParserRuleCall_1_0; }
		
		//('<' typeVars+=TypeVariable '>')?
		public Group getGroup_2() { return cGroup_2; }
		
		//'<'
		public Keyword getLessThanSignKeyword_2_0() { return cLessThanSignKeyword_2_0; }
		
		//typeVars+=TypeVariable
		public Assignment getTypeVarsAssignment_2_1() { return cTypeVarsAssignment_2_1; }
		
		//TypeVariable
		public RuleCall getTypeVarsTypeVariableParserRuleCall_2_1_0() { return cTypeVarsTypeVariableParserRuleCall_2_1_0; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_2_2() { return cGreaterThanSignKeyword_2_2; }
		
		//('indexed' declaredElementType=ParameterizedTypeRefNominal)?
		public Group getGroup_3() { return cGroup_3; }
		
		//'indexed'
		public Keyword getIndexedKeyword_3_0() { return cIndexedKeyword_3_0; }
		
		//declaredElementType=ParameterizedTypeRefNominal
		public Assignment getDeclaredElementTypeAssignment_3_1() { return cDeclaredElementTypeAssignment_3_1; }
		
		//ParameterizedTypeRefNominal
		public RuleCall getDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_3_1_0() { return cDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_3_1_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_4() { return cLeftCurlyBracketKeyword_4; }
		
		//('autoboxedType' autoboxedType=[TClassifier|TypeReferenceName])?
		public Group getGroup_5() { return cGroup_5; }
		
		//'autoboxedType'
		public Keyword getAutoboxedTypeKeyword_5_0() { return cAutoboxedTypeKeyword_5_0; }
		
		//autoboxedType=[TClassifier|TypeReferenceName]
		public Assignment getAutoboxedTypeAssignment_5_1() { return cAutoboxedTypeAssignment_5_1; }
		
		//[TClassifier|TypeReferenceName]
		public CrossReference getAutoboxedTypeTClassifierCrossReference_5_1_0() { return cAutoboxedTypeTClassifierCrossReference_5_1_0; }
		
		//TypeReferenceName
		public RuleCall getAutoboxedTypeTClassifierTypeReferenceNameParserRuleCall_5_1_0_1() { return cAutoboxedTypeTClassifierTypeReferenceNameParserRuleCall_5_1_0_1; }
		
		//('assignmnentCompatible' assignmentCompatible=[PrimitiveType|TypeReferenceName])?
		public Group getGroup_6() { return cGroup_6; }
		
		//'assignmnentCompatible'
		public Keyword getAssignmnentCompatibleKeyword_6_0() { return cAssignmnentCompatibleKeyword_6_0; }
		
		//assignmentCompatible=[PrimitiveType|TypeReferenceName]
		public Assignment getAssignmentCompatibleAssignment_6_1() { return cAssignmentCompatibleAssignment_6_1; }
		
		//[PrimitiveType|TypeReferenceName]
		public CrossReference getAssignmentCompatiblePrimitiveTypeCrossReference_6_1_0() { return cAssignmentCompatiblePrimitiveTypeCrossReference_6_1_0; }
		
		//TypeReferenceName
		public RuleCall getAssignmentCompatiblePrimitiveTypeTypeReferenceNameParserRuleCall_6_1_0_1() { return cAssignmentCompatiblePrimitiveTypeTypeReferenceNameParserRuleCall_6_1_0_1; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_7() { return cRightCurlyBracketKeyword_7; }
	}
	public class TypeReferenceNameElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TypeReferenceName");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cVoidKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cAnyKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cUndefinedKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		private final Keyword cNullKeyword_3 = (Keyword)cAlternatives.eContents().get(3);
		private final Keyword cIndexedKeyword_4 = (Keyword)cAlternatives.eContents().get(4);
		private final Group cGroup_5 = (Group)cAlternatives.eContents().get(5);
		private final RuleCall cIDENTIFIERTerminalRuleCall_5_0 = (RuleCall)cGroup_5.eContents().get(0);
		private final Group cGroup_5_1 = (Group)cGroup_5.eContents().get(1);
		private final Keyword cSolidusKeyword_5_1_0 = (Keyword)cGroup_5_1.eContents().get(0);
		private final RuleCall cIDENTIFIERTerminalRuleCall_5_1_1 = (RuleCall)cGroup_5_1.eContents().get(1);
		
		//@Override
		//TypeReferenceName:
		//	'void' | 'any' | 'undefined' | 'null' | 'indexed' | IDENTIFIER ('/' IDENTIFIER)*;
		@Override public ParserRule getRule() { return rule; }
		
		//'void' | 'any' | 'undefined' | 'null' | 'indexed' | IDENTIFIER ('/' IDENTIFIER)*
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'void'
		public Keyword getVoidKeyword_0() { return cVoidKeyword_0; }
		
		//'any'
		public Keyword getAnyKeyword_1() { return cAnyKeyword_1; }
		
		//'undefined'
		public Keyword getUndefinedKeyword_2() { return cUndefinedKeyword_2; }
		
		//'null'
		public Keyword getNullKeyword_3() { return cNullKeyword_3; }
		
		//'indexed'
		public Keyword getIndexedKeyword_4() { return cIndexedKeyword_4; }
		
		//IDENTIFIER ('/' IDENTIFIER)*
		public Group getGroup_5() { return cGroup_5; }
		
		//IDENTIFIER
		public RuleCall getIDENTIFIERTerminalRuleCall_5_0() { return cIDENTIFIERTerminalRuleCall_5_0; }
		
		//('/' IDENTIFIER)*
		public Group getGroup_5_1() { return cGroup_5_1; }
		
		//'/'
		public Keyword getSolidusKeyword_5_1_0() { return cSolidusKeyword_5_1_0; }
		
		//IDENTIFIER
		public RuleCall getIDENTIFIERTerminalRuleCall_5_1_1() { return cIDENTIFIERTerminalRuleCall_5_1_1; }
	}
	public class AnyTypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.AnyType");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cAnyTypeAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cNameAnyKeyword_1_0 = (Keyword)cNameAssignment_1.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Keyword cRightCurlyBracketKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//// These will be defined only once to be able to load them as singletons and refer to them via x-refs
		//AnyType:
		//	{AnyType} name='any' '{' '}';
		@Override public ParserRule getRule() { return rule; }
		
		//{AnyType} name='any' '{' '}'
		public Group getGroup() { return cGroup; }
		
		//{AnyType}
		public Action getAnyTypeAction_0() { return cAnyTypeAction_0; }
		
		//name='any'
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//'any'
		public Keyword getNameAnyKeyword_1_0() { return cNameAnyKeyword_1_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_2() { return cLeftCurlyBracketKeyword_2; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_3() { return cRightCurlyBracketKeyword_3; }
	}
	public class VoidTypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.VoidType");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cVoidTypeAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cNameVoidKeyword_1_0 = (Keyword)cNameAssignment_1.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Keyword cRightCurlyBracketKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//VoidType:
		//	{VoidType} name='void' '{' '}';
		@Override public ParserRule getRule() { return rule; }
		
		//{VoidType} name='void' '{' '}'
		public Group getGroup() { return cGroup; }
		
		//{VoidType}
		public Action getVoidTypeAction_0() { return cVoidTypeAction_0; }
		
		//name='void'
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//'void'
		public Keyword getNameVoidKeyword_1_0() { return cNameVoidKeyword_1_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_2() { return cLeftCurlyBracketKeyword_2; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_3() { return cRightCurlyBracketKeyword_3; }
	}
	public class UndefinedTypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.UndefinedType");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cUndefinedTypeAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cNameUndefinedKeyword_1_0 = (Keyword)cNameAssignment_1.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Keyword cRightCurlyBracketKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//UndefinedType:
		//	{UndefinedType} name='undefined' '{' '}';
		@Override public ParserRule getRule() { return rule; }
		
		//{UndefinedType} name='undefined' '{' '}'
		public Group getGroup() { return cGroup; }
		
		//{UndefinedType}
		public Action getUndefinedTypeAction_0() { return cUndefinedTypeAction_0; }
		
		//name='undefined'
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//'undefined'
		public Keyword getNameUndefinedKeyword_1_0() { return cNameUndefinedKeyword_1_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_2() { return cLeftCurlyBracketKeyword_2; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_3() { return cRightCurlyBracketKeyword_3; }
	}
	public class NullTypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.NullType");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cNullTypeAction_0 = (Action)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cNameNullKeyword_1_0 = (Keyword)cNameAssignment_1.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Keyword cRightCurlyBracketKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//NullType:
		//	{NullType} name='null' '{' '}';
		@Override public ParserRule getRule() { return rule; }
		
		//{NullType} name='null' '{' '}'
		public Group getGroup() { return cGroup; }
		
		//{NullType}
		public Action getNullTypeAction_0() { return cNullTypeAction_0; }
		
		//name='null'
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//'null'
		public Keyword getNameNullKeyword_1_0() { return cNameNullKeyword_1_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_2() { return cLeftCurlyBracketKeyword_2; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_3() { return cRightCurlyBracketKeyword_3; }
	}
	public class TypesIdentifierElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TypesIdentifier");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cTypesSpecificKeywordsParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cIdentifierNameParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//TypesIdentifier:
		//	TypesSpecificKeywords | IdentifierName;
		@Override public ParserRule getRule() { return rule; }
		
		//TypesSpecificKeywords | IdentifierName
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//TypesSpecificKeywords
		public RuleCall getTypesSpecificKeywordsParserRuleCall_0() { return cTypesSpecificKeywordsParserRuleCall_0; }
		
		//IdentifierName
		public RuleCall getIdentifierNameParserRuleCall_1() { return cIdentifierNameParserRuleCall_1; }
	}
	public class BindingTypesIdentifierElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.BindingTypesIdentifier");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cTypesSpecificKeywordsParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cBindingIdentifierParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//BindingTypesIdentifier:
		//	TypesSpecificKeywords | BindingIdentifier<Yield=false>;
		@Override public ParserRule getRule() { return rule; }
		
		//TypesSpecificKeywords | BindingIdentifier<Yield=false>
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//TypesSpecificKeywords
		public RuleCall getTypesSpecificKeywordsParserRuleCall_0() { return cTypesSpecificKeywordsParserRuleCall_0; }
		
		//BindingIdentifier<Yield=false>
		public RuleCall getBindingIdentifierParserRuleCall_1() { return cBindingIdentifierParserRuleCall_1; }
	}
	public class VoidOrBindingIdentifierElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.VoidOrBindingIdentifier");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cVoidKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final RuleCall cBindingTypesIdentifierParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//// Also allows void as identifier although it is a reserved keyword as of [ECM15]
		//VoidOrBindingIdentifier:
		//	'void' | BindingTypesIdentifier;
		@Override public ParserRule getRule() { return rule; }
		
		//'void' | BindingTypesIdentifier
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//'void'
		public Keyword getVoidKeyword_0() { return cVoidKeyword_0; }
		
		//BindingTypesIdentifier
		public RuleCall getBindingTypesIdentifierParserRuleCall_1() { return cBindingTypesIdentifierParserRuleCall_1; }
	}
	public class TypesSpecificKeywordsElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TypesSpecificKeywords");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cAnyKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cUndefinedKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cObjectKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		private final Keyword cVirtualBaseKeyword_3 = (Keyword)cAlternatives.eContents().get(3);
		private final Keyword cPrimitiveKeyword_4 = (Keyword)cAlternatives.eContents().get(4);
		private final Keyword cAutoboxedTypeKeyword_5 = (Keyword)cAlternatives.eContents().get(5);
		private final Keyword cAssignmnentCompatibleKeyword_6 = (Keyword)cAlternatives.eContents().get(6);
		
		//// These keywords are specific to the types language when comparing with N4JS
		//TypesSpecificKeywords: // Types keywords
		//	'any' // no ECMAScript keywords, used in certain [ECM13] and N4JS contexts
		//	| 'undefined' // no ECMAScript nor N4JS keyword, used in types only
		//	| 'object' | 'virtualBase' | 'primitive' | 'autoboxedType' | 'assignmnentCompatible' // must not be used: 'notnull'|'nullable'
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//// Types keywords
		//'any' // no ECMAScript keywords, used in certain [ECM13] and N4JS contexts
		//| 'undefined' // no ECMAScript nor N4JS keyword, used in types only
		//| 'object' | 'virtualBase' | 'primitive' | 'autoboxedType' | 'assignmnentCompatible'
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//// Types keywords
		//'any'
		public Keyword getAnyKeyword_0() { return cAnyKeyword_0; }
		
		//'undefined'
		public Keyword getUndefinedKeyword_1() { return cUndefinedKeyword_1; }
		
		//'object'
		public Keyword getObjectKeyword_2() { return cObjectKeyword_2; }
		
		//'virtualBase'
		public Keyword getVirtualBaseKeyword_3() { return cVirtualBaseKeyword_3; }
		
		//'primitive'
		public Keyword getPrimitiveKeyword_4() { return cPrimitiveKeyword_4; }
		
		//'autoboxedType'
		public Keyword getAutoboxedTypeKeyword_5() { return cAutoboxedTypeKeyword_5; }
		
		//'assignmnentCompatible'
		public Keyword getAssignmnentCompatibleKeyword_6() { return cAssignmnentCompatibleKeyword_6; }
	}
	public class TypesComputedPropertyNameElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TypesComputedPropertyName");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cLeftSquareBracketKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final RuleCall cTypesSymbolLiteralComputedNameParserRuleCall_1_0 = (RuleCall)cAlternatives_1.eContents().get(0);
		private final RuleCall cTypesStringLiteralComputedNameParserRuleCall_1_1 = (RuleCall)cAlternatives_1.eContents().get(1);
		private final Keyword cRightSquareBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		//// see rule "ComputedPropertyName" in ECMAScript 6 specification (e.g. Section 12.2.5)
		//TypesComputedPropertyName:
		//	'[' (TypesSymbolLiteralComputedName | TypesStringLiteralComputedName) ']';
		@Override public ParserRule getRule() { return rule; }
		
		//'[' (TypesSymbolLiteralComputedName | TypesStringLiteralComputedName) ']'
		public Group getGroup() { return cGroup; }
		
		//'['
		public Keyword getLeftSquareBracketKeyword_0() { return cLeftSquareBracketKeyword_0; }
		
		//TypesSymbolLiteralComputedName | TypesStringLiteralComputedName
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//TypesSymbolLiteralComputedName
		public RuleCall getTypesSymbolLiteralComputedNameParserRuleCall_1_0() { return cTypesSymbolLiteralComputedNameParserRuleCall_1_0; }
		
		//TypesStringLiteralComputedName
		public RuleCall getTypesStringLiteralComputedNameParserRuleCall_1_1() { return cTypesStringLiteralComputedNameParserRuleCall_1_1; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_2() { return cRightSquareBracketKeyword_2; }
	}
	public class TypesSymbolLiteralComputedNameElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TypesSymbolLiteralComputedName");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cTypesIdentifierParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Keyword cFullStopKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final RuleCall cTypesIdentifierParserRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		
		//TypesSymbolLiteralComputedName:
		//	TypesIdentifier '.' TypesIdentifier;
		@Override public ParserRule getRule() { return rule; }
		
		//TypesIdentifier '.' TypesIdentifier
		public Group getGroup() { return cGroup; }
		
		//TypesIdentifier
		public RuleCall getTypesIdentifierParserRuleCall_0() { return cTypesIdentifierParserRuleCall_0; }
		
		//'.'
		public Keyword getFullStopKeyword_1() { return cFullStopKeyword_1; }
		
		//TypesIdentifier
		public RuleCall getTypesIdentifierParserRuleCall_2() { return cTypesIdentifierParserRuleCall_2; }
	}
	public class TypesStringLiteralComputedNameElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TypesStringLiteralComputedName");
		private final RuleCall cSTRINGTerminalRuleCall = (RuleCall)rule.eContents().get(1);
		
		//TypesStringLiteralComputedName:
		//	STRING;
		@Override public ParserRule getRule() { return rule; }
		
		//STRING
		public RuleCall getSTRINGTerminalRuleCall() { return cSTRINGTerminalRuleCall; }
	}
	public class TObjectPrototypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TObjectPrototype");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cDeclaredTypeAccessModifierAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0 = (RuleCall)cDeclaredTypeAccessModifierAssignment_0.eContents().get(0);
		private final Assignment cDeclaredProvidedByRuntimeAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0 = (Keyword)cDeclaredProvidedByRuntimeAssignment_1.eContents().get(0);
		private final Assignment cDeclaredFinalAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final Keyword cDeclaredFinalFinalKeyword_2_0 = (Keyword)cDeclaredFinalAssignment_2.eContents().get(0);
		private final Keyword cObjectKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Assignment cNameAssignment_4 = (Assignment)cGroup.eContents().get(4);
		private final RuleCall cNameBindingTypesIdentifierParserRuleCall_4_0 = (RuleCall)cNameAssignment_4.eContents().get(0);
		private final RuleCall cTypeVariablesParserRuleCall_5 = (RuleCall)cGroup.eContents().get(5);
		private final Group cGroup_6 = (Group)cGroup.eContents().get(6);
		private final Keyword cExtendsKeyword_6_0 = (Keyword)cGroup_6.eContents().get(0);
		private final Assignment cSuperTypeAssignment_6_1 = (Assignment)cGroup_6.eContents().get(1);
		private final RuleCall cSuperTypeParameterizedTypeRefNominalParserRuleCall_6_1_0 = (RuleCall)cSuperTypeAssignment_6_1.eContents().get(0);
		private final Group cGroup_7 = (Group)cGroup.eContents().get(7);
		private final Keyword cIndexedKeyword_7_0 = (Keyword)cGroup_7.eContents().get(0);
		private final Assignment cDeclaredElementTypeAssignment_7_1 = (Assignment)cGroup_7.eContents().get(1);
		private final RuleCall cDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_7_1_0 = (RuleCall)cDeclaredElementTypeAssignment_7_1.eContents().get(0);
		private final Assignment cAnnotationsAssignment_8 = (Assignment)cGroup.eContents().get(8);
		private final RuleCall cAnnotationsTAnnotationParserRuleCall_8_0 = (RuleCall)cAnnotationsAssignment_8.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_9 = (Keyword)cGroup.eContents().get(9);
		private final Assignment cOwnedMembersAssignment_10 = (Assignment)cGroup.eContents().get(10);
		private final RuleCall cOwnedMembersTMemberParserRuleCall_10_0 = (RuleCall)cOwnedMembersAssignment_10.eContents().get(0);
		private final Group cGroup_11 = (Group)cGroup.eContents().get(11);
		private final Assignment cCallableCtorAssignment_11_0 = (Assignment)cGroup_11.eContents().get(0);
		private final RuleCall cCallableCtorCallableCtorParserRuleCall_11_0_0 = (RuleCall)cCallableCtorAssignment_11_0.eContents().get(0);
		private final Assignment cOwnedMembersAssignment_11_1 = (Assignment)cGroup_11.eContents().get(1);
		private final RuleCall cOwnedMembersTMemberParserRuleCall_11_1_0 = (RuleCall)cOwnedMembersAssignment_11_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_12 = (Keyword)cGroup.eContents().get(12);
		
		//TObjectPrototype:
		//	declaredTypeAccessModifier=TypeAccessModifier
		//	declaredProvidedByRuntime?='providedByRuntime'?
		//	declaredFinal?='final'?
		//	'object' name=BindingTypesIdentifier
		//	TypeVariables? ('extends' superType=ParameterizedTypeRefNominal)? ('indexed'
		//	declaredElementType=ParameterizedTypeRefNominal)?
		//	annotations+=TAnnotation*
		//	'{'
		//	ownedMembers+=TMember* (callableCtor=CallableCtor
		//	ownedMembers+=TMember*)?
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//declaredTypeAccessModifier=TypeAccessModifier declaredProvidedByRuntime?='providedByRuntime'? declaredFinal?='final'?
		//'object' name=BindingTypesIdentifier TypeVariables? ('extends' superType=ParameterizedTypeRefNominal)? ('indexed'
		//declaredElementType=ParameterizedTypeRefNominal)? annotations+=TAnnotation* '{' ownedMembers+=TMember*
		//(callableCtor=CallableCtor ownedMembers+=TMember*)? '}'
		public Group getGroup() { return cGroup; }
		
		//declaredTypeAccessModifier=TypeAccessModifier
		public Assignment getDeclaredTypeAccessModifierAssignment_0() { return cDeclaredTypeAccessModifierAssignment_0; }
		
		//TypeAccessModifier
		public RuleCall getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0() { return cDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0; }
		
		//declaredProvidedByRuntime?='providedByRuntime'?
		public Assignment getDeclaredProvidedByRuntimeAssignment_1() { return cDeclaredProvidedByRuntimeAssignment_1; }
		
		//'providedByRuntime'
		public Keyword getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0() { return cDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0; }
		
		//declaredFinal?='final'?
		public Assignment getDeclaredFinalAssignment_2() { return cDeclaredFinalAssignment_2; }
		
		//'final'
		public Keyword getDeclaredFinalFinalKeyword_2_0() { return cDeclaredFinalFinalKeyword_2_0; }
		
		//'object'
		public Keyword getObjectKeyword_3() { return cObjectKeyword_3; }
		
		//name=BindingTypesIdentifier
		public Assignment getNameAssignment_4() { return cNameAssignment_4; }
		
		//BindingTypesIdentifier
		public RuleCall getNameBindingTypesIdentifierParserRuleCall_4_0() { return cNameBindingTypesIdentifierParserRuleCall_4_0; }
		
		//TypeVariables?
		public RuleCall getTypeVariablesParserRuleCall_5() { return cTypeVariablesParserRuleCall_5; }
		
		//('extends' superType=ParameterizedTypeRefNominal)?
		public Group getGroup_6() { return cGroup_6; }
		
		//'extends'
		public Keyword getExtendsKeyword_6_0() { return cExtendsKeyword_6_0; }
		
		//superType=ParameterizedTypeRefNominal
		public Assignment getSuperTypeAssignment_6_1() { return cSuperTypeAssignment_6_1; }
		
		//ParameterizedTypeRefNominal
		public RuleCall getSuperTypeParameterizedTypeRefNominalParserRuleCall_6_1_0() { return cSuperTypeParameterizedTypeRefNominalParserRuleCall_6_1_0; }
		
		//('indexed' declaredElementType=ParameterizedTypeRefNominal)?
		public Group getGroup_7() { return cGroup_7; }
		
		//'indexed'
		public Keyword getIndexedKeyword_7_0() { return cIndexedKeyword_7_0; }
		
		//declaredElementType=ParameterizedTypeRefNominal
		public Assignment getDeclaredElementTypeAssignment_7_1() { return cDeclaredElementTypeAssignment_7_1; }
		
		//ParameterizedTypeRefNominal
		public RuleCall getDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_7_1_0() { return cDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_7_1_0; }
		
		//annotations+=TAnnotation*
		public Assignment getAnnotationsAssignment_8() { return cAnnotationsAssignment_8; }
		
		//TAnnotation
		public RuleCall getAnnotationsTAnnotationParserRuleCall_8_0() { return cAnnotationsTAnnotationParserRuleCall_8_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_9() { return cLeftCurlyBracketKeyword_9; }
		
		//ownedMembers+=TMember*
		public Assignment getOwnedMembersAssignment_10() { return cOwnedMembersAssignment_10; }
		
		//TMember
		public RuleCall getOwnedMembersTMemberParserRuleCall_10_0() { return cOwnedMembersTMemberParserRuleCall_10_0; }
		
		//(callableCtor=CallableCtor ownedMembers+=TMember*)?
		public Group getGroup_11() { return cGroup_11; }
		
		//callableCtor=CallableCtor
		public Assignment getCallableCtorAssignment_11_0() { return cCallableCtorAssignment_11_0; }
		
		//CallableCtor
		public RuleCall getCallableCtorCallableCtorParserRuleCall_11_0_0() { return cCallableCtorCallableCtorParserRuleCall_11_0_0; }
		
		//ownedMembers+=TMember*
		public Assignment getOwnedMembersAssignment_11_1() { return cOwnedMembersAssignment_11_1; }
		
		//TMember
		public RuleCall getOwnedMembersTMemberParserRuleCall_11_1_0() { return cOwnedMembersTMemberParserRuleCall_11_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_12() { return cRightCurlyBracketKeyword_12; }
	}
	public class VirtualBaseTypeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.VirtualBaseType");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cVirtualBaseTypeAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cVirtualBaseKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cNameAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cNameBindingTypesIdentifierParserRuleCall_2_0 = (RuleCall)cNameAssignment_2.eContents().get(0);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Keyword cIndexedKeyword_3_0 = (Keyword)cGroup_3.eContents().get(0);
		private final Assignment cDeclaredElementTypeAssignment_3_1 = (Assignment)cGroup_3.eContents().get(1);
		private final RuleCall cDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_3_1_0 = (RuleCall)cDeclaredElementTypeAssignment_3_1.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		private final Assignment cOwnedMembersAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final RuleCall cOwnedMembersTMemberParserRuleCall_5_0 = (RuleCall)cOwnedMembersAssignment_5.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_6 = (Keyword)cGroup.eContents().get(6);
		
		///*
		// * Virtual base type, not visible to N4JS users.
		// * Used to define common super types, e.g. for all enumerations.
		// */ VirtualBaseType:
		//	{VirtualBaseType}
		//	'virtualBase' name=BindingTypesIdentifier ('indexed' declaredElementType=ParameterizedTypeRefNominal)?
		//	'{'
		//	ownedMembers+=TMember*
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//{VirtualBaseType} 'virtualBase' name=BindingTypesIdentifier ('indexed' declaredElementType=ParameterizedTypeRefNominal)?
		//'{' ownedMembers+=TMember* '}'
		public Group getGroup() { return cGroup; }
		
		//{VirtualBaseType}
		public Action getVirtualBaseTypeAction_0() { return cVirtualBaseTypeAction_0; }
		
		//'virtualBase'
		public Keyword getVirtualBaseKeyword_1() { return cVirtualBaseKeyword_1; }
		
		//name=BindingTypesIdentifier
		public Assignment getNameAssignment_2() { return cNameAssignment_2; }
		
		//BindingTypesIdentifier
		public RuleCall getNameBindingTypesIdentifierParserRuleCall_2_0() { return cNameBindingTypesIdentifierParserRuleCall_2_0; }
		
		//('indexed' declaredElementType=ParameterizedTypeRefNominal)?
		public Group getGroup_3() { return cGroup_3; }
		
		//'indexed'
		public Keyword getIndexedKeyword_3_0() { return cIndexedKeyword_3_0; }
		
		//declaredElementType=ParameterizedTypeRefNominal
		public Assignment getDeclaredElementTypeAssignment_3_1() { return cDeclaredElementTypeAssignment_3_1; }
		
		//ParameterizedTypeRefNominal
		public RuleCall getDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_3_1_0() { return cDeclaredElementTypeParameterizedTypeRefNominalParserRuleCall_3_1_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_4() { return cLeftCurlyBracketKeyword_4; }
		
		//ownedMembers+=TMember*
		public Assignment getOwnedMembersAssignment_5() { return cOwnedMembersAssignment_5; }
		
		//TMember
		public RuleCall getOwnedMembersTMemberParserRuleCall_5_0() { return cOwnedMembersTMemberParserRuleCall_5_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_6() { return cRightCurlyBracketKeyword_6; }
	}
	public class TClassElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TClass");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cDeclaredTypeAccessModifierAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0 = (RuleCall)cDeclaredTypeAccessModifierAssignment_0.eContents().get(0);
		private final Assignment cDeclaredProvidedByRuntimeAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0 = (Keyword)cDeclaredProvidedByRuntimeAssignment_1.eContents().get(0);
		private final Assignment cDeclaredAbstractAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final Keyword cDeclaredAbstractAbstractKeyword_2_0 = (Keyword)cDeclaredAbstractAssignment_2.eContents().get(0);
		private final Assignment cDeclaredFinalAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final Keyword cDeclaredFinalFinalKeyword_3_0 = (Keyword)cDeclaredFinalAssignment_3.eContents().get(0);
		private final Keyword cClassKeyword_4 = (Keyword)cGroup.eContents().get(4);
		private final RuleCall cTClassOrInterfaceHeaderParserRuleCall_5 = (RuleCall)cGroup.eContents().get(5);
		private final Group cGroup_6 = (Group)cGroup.eContents().get(6);
		private final Keyword cExtendsKeyword_6_0 = (Keyword)cGroup_6.eContents().get(0);
		private final Assignment cSuperClassRefAssignment_6_1 = (Assignment)cGroup_6.eContents().get(1);
		private final RuleCall cSuperClassRefParameterizedTypeRefNominalParserRuleCall_6_1_0 = (RuleCall)cSuperClassRefAssignment_6_1.eContents().get(0);
		private final Group cGroup_7 = (Group)cGroup.eContents().get(7);
		private final Keyword cImplementsKeyword_7_0 = (Keyword)cGroup_7.eContents().get(0);
		private final Assignment cImplementedInterfaceRefsAssignment_7_1 = (Assignment)cGroup_7.eContents().get(1);
		private final RuleCall cImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_7_1_0 = (RuleCall)cImplementedInterfaceRefsAssignment_7_1.eContents().get(0);
		private final Group cGroup_7_2 = (Group)cGroup_7.eContents().get(2);
		private final Keyword cCommaKeyword_7_2_0 = (Keyword)cGroup_7_2.eContents().get(0);
		private final Assignment cImplementedInterfaceRefsAssignment_7_2_1 = (Assignment)cGroup_7_2.eContents().get(1);
		private final RuleCall cImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_7_2_1_0 = (RuleCall)cImplementedInterfaceRefsAssignment_7_2_1.eContents().get(0);
		private final Assignment cAnnotationsAssignment_8 = (Assignment)cGroup.eContents().get(8);
		private final RuleCall cAnnotationsTAnnotationParserRuleCall_8_0 = (RuleCall)cAnnotationsAssignment_8.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_9 = (Keyword)cGroup.eContents().get(9);
		private final Assignment cOwnedMembersAssignment_10 = (Assignment)cGroup.eContents().get(10);
		private final RuleCall cOwnedMembersTMemberParserRuleCall_10_0 = (RuleCall)cOwnedMembersAssignment_10.eContents().get(0);
		private final Group cGroup_11 = (Group)cGroup.eContents().get(11);
		private final Assignment cCallableCtorAssignment_11_0 = (Assignment)cGroup_11.eContents().get(0);
		private final RuleCall cCallableCtorCallableCtorParserRuleCall_11_0_0 = (RuleCall)cCallableCtorAssignment_11_0.eContents().get(0);
		private final Assignment cOwnedMembersAssignment_11_1 = (Assignment)cGroup_11.eContents().get(1);
		private final RuleCall cOwnedMembersTMemberParserRuleCall_11_1_0 = (RuleCall)cOwnedMembersAssignment_11_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_12 = (Keyword)cGroup.eContents().get(12);
		
		//TClass:
		//	declaredTypeAccessModifier=TypeAccessModifier
		//	declaredProvidedByRuntime?='providedByRuntime'?
		//	declaredAbstract?='abstract'?
		//	declaredFinal?='final'?
		//	'class' TClassOrInterfaceHeader ('extends' superClassRef=ParameterizedTypeRefNominal)? ('implements'
		//	implementedInterfaceRefs+=ParameterizedTypeRefNominal (',' implementedInterfaceRefs+=ParameterizedTypeRefNominal)*)?
		//	annotations+=TAnnotation*
		//	'{'
		//	ownedMembers+=TMember* (callableCtor=CallableCtor
		//	ownedMembers+=TMember*)?
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//declaredTypeAccessModifier=TypeAccessModifier declaredProvidedByRuntime?='providedByRuntime'?
		//declaredAbstract?='abstract'? declaredFinal?='final'? 'class' TClassOrInterfaceHeader ('extends'
		//superClassRef=ParameterizedTypeRefNominal)? ('implements' implementedInterfaceRefs+=ParameterizedTypeRefNominal (','
		//implementedInterfaceRefs+=ParameterizedTypeRefNominal)*)? annotations+=TAnnotation* '{' ownedMembers+=TMember*
		//(callableCtor=CallableCtor ownedMembers+=TMember*)? '}'
		public Group getGroup() { return cGroup; }
		
		//declaredTypeAccessModifier=TypeAccessModifier
		public Assignment getDeclaredTypeAccessModifierAssignment_0() { return cDeclaredTypeAccessModifierAssignment_0; }
		
		//TypeAccessModifier
		public RuleCall getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0() { return cDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0; }
		
		//declaredProvidedByRuntime?='providedByRuntime'?
		public Assignment getDeclaredProvidedByRuntimeAssignment_1() { return cDeclaredProvidedByRuntimeAssignment_1; }
		
		//'providedByRuntime'
		public Keyword getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0() { return cDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0; }
		
		//declaredAbstract?='abstract'?
		public Assignment getDeclaredAbstractAssignment_2() { return cDeclaredAbstractAssignment_2; }
		
		//'abstract'
		public Keyword getDeclaredAbstractAbstractKeyword_2_0() { return cDeclaredAbstractAbstractKeyword_2_0; }
		
		//declaredFinal?='final'?
		public Assignment getDeclaredFinalAssignment_3() { return cDeclaredFinalAssignment_3; }
		
		//'final'
		public Keyword getDeclaredFinalFinalKeyword_3_0() { return cDeclaredFinalFinalKeyword_3_0; }
		
		//'class'
		public Keyword getClassKeyword_4() { return cClassKeyword_4; }
		
		//TClassOrInterfaceHeader
		public RuleCall getTClassOrInterfaceHeaderParserRuleCall_5() { return cTClassOrInterfaceHeaderParserRuleCall_5; }
		
		//('extends' superClassRef=ParameterizedTypeRefNominal)?
		public Group getGroup_6() { return cGroup_6; }
		
		//'extends'
		public Keyword getExtendsKeyword_6_0() { return cExtendsKeyword_6_0; }
		
		//superClassRef=ParameterizedTypeRefNominal
		public Assignment getSuperClassRefAssignment_6_1() { return cSuperClassRefAssignment_6_1; }
		
		//ParameterizedTypeRefNominal
		public RuleCall getSuperClassRefParameterizedTypeRefNominalParserRuleCall_6_1_0() { return cSuperClassRefParameterizedTypeRefNominalParserRuleCall_6_1_0; }
		
		//('implements' implementedInterfaceRefs+=ParameterizedTypeRefNominal (','
		//implementedInterfaceRefs+=ParameterizedTypeRefNominal)*)?
		public Group getGroup_7() { return cGroup_7; }
		
		//'implements'
		public Keyword getImplementsKeyword_7_0() { return cImplementsKeyword_7_0; }
		
		//implementedInterfaceRefs+=ParameterizedTypeRefNominal
		public Assignment getImplementedInterfaceRefsAssignment_7_1() { return cImplementedInterfaceRefsAssignment_7_1; }
		
		//ParameterizedTypeRefNominal
		public RuleCall getImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_7_1_0() { return cImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_7_1_0; }
		
		//(',' implementedInterfaceRefs+=ParameterizedTypeRefNominal)*
		public Group getGroup_7_2() { return cGroup_7_2; }
		
		//','
		public Keyword getCommaKeyword_7_2_0() { return cCommaKeyword_7_2_0; }
		
		//implementedInterfaceRefs+=ParameterizedTypeRefNominal
		public Assignment getImplementedInterfaceRefsAssignment_7_2_1() { return cImplementedInterfaceRefsAssignment_7_2_1; }
		
		//ParameterizedTypeRefNominal
		public RuleCall getImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_7_2_1_0() { return cImplementedInterfaceRefsParameterizedTypeRefNominalParserRuleCall_7_2_1_0; }
		
		//annotations+=TAnnotation*
		public Assignment getAnnotationsAssignment_8() { return cAnnotationsAssignment_8; }
		
		//TAnnotation
		public RuleCall getAnnotationsTAnnotationParserRuleCall_8_0() { return cAnnotationsTAnnotationParserRuleCall_8_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_9() { return cLeftCurlyBracketKeyword_9; }
		
		//ownedMembers+=TMember*
		public Assignment getOwnedMembersAssignment_10() { return cOwnedMembersAssignment_10; }
		
		//TMember
		public RuleCall getOwnedMembersTMemberParserRuleCall_10_0() { return cOwnedMembersTMemberParserRuleCall_10_0; }
		
		//(callableCtor=CallableCtor ownedMembers+=TMember*)?
		public Group getGroup_11() { return cGroup_11; }
		
		//callableCtor=CallableCtor
		public Assignment getCallableCtorAssignment_11_0() { return cCallableCtorAssignment_11_0; }
		
		//CallableCtor
		public RuleCall getCallableCtorCallableCtorParserRuleCall_11_0_0() { return cCallableCtorCallableCtorParserRuleCall_11_0_0; }
		
		//ownedMembers+=TMember*
		public Assignment getOwnedMembersAssignment_11_1() { return cOwnedMembersAssignment_11_1; }
		
		//TMember
		public RuleCall getOwnedMembersTMemberParserRuleCall_11_1_0() { return cOwnedMembersTMemberParserRuleCall_11_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_12() { return cRightCurlyBracketKeyword_12; }
	}
	public class TInterfaceElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TInterface");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cDeclaredTypeAccessModifierAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0 = (RuleCall)cDeclaredTypeAccessModifierAssignment_0.eContents().get(0);
		private final Assignment cDeclaredProvidedByRuntimeAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0 = (Keyword)cDeclaredProvidedByRuntimeAssignment_1.eContents().get(0);
		private final Keyword cInterfaceKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final RuleCall cTClassOrInterfaceHeaderParserRuleCall_3 = (RuleCall)cGroup.eContents().get(3);
		private final Group cGroup_4 = (Group)cGroup.eContents().get(4);
		private final Keyword cExtendsKeyword_4_0 = (Keyword)cGroup_4.eContents().get(0);
		private final Assignment cSuperInterfaceRefsAssignment_4_1 = (Assignment)cGroup_4.eContents().get(1);
		private final RuleCall cSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_4_1_0 = (RuleCall)cSuperInterfaceRefsAssignment_4_1.eContents().get(0);
		private final Group cGroup_4_2 = (Group)cGroup_4.eContents().get(2);
		private final Keyword cCommaKeyword_4_2_0 = (Keyword)cGroup_4_2.eContents().get(0);
		private final Assignment cSuperInterfaceRefsAssignment_4_2_1 = (Assignment)cGroup_4_2.eContents().get(1);
		private final RuleCall cSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_4_2_1_0 = (RuleCall)cSuperInterfaceRefsAssignment_4_2_1.eContents().get(0);
		private final Assignment cAnnotationsAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final RuleCall cAnnotationsTAnnotationParserRuleCall_5_0 = (RuleCall)cAnnotationsAssignment_5.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_6 = (Keyword)cGroup.eContents().get(6);
		private final Assignment cOwnedMembersAssignment_7 = (Assignment)cGroup.eContents().get(7);
		private final RuleCall cOwnedMembersTMemberParserRuleCall_7_0 = (RuleCall)cOwnedMembersAssignment_7.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_8 = (Keyword)cGroup.eContents().get(8);
		
		//TInterface:
		//	declaredTypeAccessModifier=TypeAccessModifier
		//	declaredProvidedByRuntime?='providedByRuntime'?
		//	'interface' TClassOrInterfaceHeader ('extends' superInterfaceRefs+=ParameterizedTypeRefNominal (','
		//	superInterfaceRefs+=ParameterizedTypeRefNominal)*)?
		//	annotations+=TAnnotation*
		//	'{' ownedMembers+=TMember* '}';
		@Override public ParserRule getRule() { return rule; }
		
		//declaredTypeAccessModifier=TypeAccessModifier declaredProvidedByRuntime?='providedByRuntime'? 'interface'
		//TClassOrInterfaceHeader ('extends' superInterfaceRefs+=ParameterizedTypeRefNominal (','
		//superInterfaceRefs+=ParameterizedTypeRefNominal)*)? annotations+=TAnnotation* '{' ownedMembers+=TMember* '}'
		public Group getGroup() { return cGroup; }
		
		//declaredTypeAccessModifier=TypeAccessModifier
		public Assignment getDeclaredTypeAccessModifierAssignment_0() { return cDeclaredTypeAccessModifierAssignment_0; }
		
		//TypeAccessModifier
		public RuleCall getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0() { return cDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0; }
		
		//declaredProvidedByRuntime?='providedByRuntime'?
		public Assignment getDeclaredProvidedByRuntimeAssignment_1() { return cDeclaredProvidedByRuntimeAssignment_1; }
		
		//'providedByRuntime'
		public Keyword getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0() { return cDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0; }
		
		//'interface'
		public Keyword getInterfaceKeyword_2() { return cInterfaceKeyword_2; }
		
		//TClassOrInterfaceHeader
		public RuleCall getTClassOrInterfaceHeaderParserRuleCall_3() { return cTClassOrInterfaceHeaderParserRuleCall_3; }
		
		//('extends' superInterfaceRefs+=ParameterizedTypeRefNominal (',' superInterfaceRefs+=ParameterizedTypeRefNominal)*)?
		public Group getGroup_4() { return cGroup_4; }
		
		//'extends'
		public Keyword getExtendsKeyword_4_0() { return cExtendsKeyword_4_0; }
		
		//superInterfaceRefs+=ParameterizedTypeRefNominal
		public Assignment getSuperInterfaceRefsAssignment_4_1() { return cSuperInterfaceRefsAssignment_4_1; }
		
		//ParameterizedTypeRefNominal
		public RuleCall getSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_4_1_0() { return cSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_4_1_0; }
		
		//(',' superInterfaceRefs+=ParameterizedTypeRefNominal)*
		public Group getGroup_4_2() { return cGroup_4_2; }
		
		//','
		public Keyword getCommaKeyword_4_2_0() { return cCommaKeyword_4_2_0; }
		
		//superInterfaceRefs+=ParameterizedTypeRefNominal
		public Assignment getSuperInterfaceRefsAssignment_4_2_1() { return cSuperInterfaceRefsAssignment_4_2_1; }
		
		//ParameterizedTypeRefNominal
		public RuleCall getSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_4_2_1_0() { return cSuperInterfaceRefsParameterizedTypeRefNominalParserRuleCall_4_2_1_0; }
		
		//annotations+=TAnnotation*
		public Assignment getAnnotationsAssignment_5() { return cAnnotationsAssignment_5; }
		
		//TAnnotation
		public RuleCall getAnnotationsTAnnotationParserRuleCall_5_0() { return cAnnotationsTAnnotationParserRuleCall_5_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_6() { return cLeftCurlyBracketKeyword_6; }
		
		//ownedMembers+=TMember*
		public Assignment getOwnedMembersAssignment_7() { return cOwnedMembersAssignment_7; }
		
		//TMember
		public RuleCall getOwnedMembersTMemberParserRuleCall_7_0() { return cOwnedMembersTMemberParserRuleCall_7_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_8() { return cRightCurlyBracketKeyword_8; }
	}
	public class TypeVariableElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TypeVariable");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cNameAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cNameIDENTIFIERTerminalRuleCall_0_0 = (RuleCall)cNameAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cExtendsKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cDeclaredUpperBoundAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cDeclaredUpperBoundTypeRefParserRuleCall_1_1_0 = (RuleCall)cDeclaredUpperBoundAssignment_1_1.eContents().get(0);
		
		//@Override
		//TypeVariable:
		//	name=IDENTIFIER ('extends' declaredUpperBound=TypeRef)?;
		@Override public ParserRule getRule() { return rule; }
		
		//name=IDENTIFIER ('extends' declaredUpperBound=TypeRef)?
		public Group getGroup() { return cGroup; }
		
		//name=IDENTIFIER
		public Assignment getNameAssignment_0() { return cNameAssignment_0; }
		
		//IDENTIFIER
		public RuleCall getNameIDENTIFIERTerminalRuleCall_0_0() { return cNameIDENTIFIERTerminalRuleCall_0_0; }
		
		//('extends' declaredUpperBound=TypeRef)?
		public Group getGroup_1() { return cGroup_1; }
		
		//'extends'
		public Keyword getExtendsKeyword_1_0() { return cExtendsKeyword_1_0; }
		
		//declaredUpperBound=TypeRef
		public Assignment getDeclaredUpperBoundAssignment_1_1() { return cDeclaredUpperBoundAssignment_1_1; }
		
		//TypeRef
		public RuleCall getDeclaredUpperBoundTypeRefParserRuleCall_1_1_0() { return cDeclaredUpperBoundTypeRefParserRuleCall_1_1_0; }
	}
	public class TClassOrInterfaceHeaderElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TClassOrInterfaceHeader");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Assignment cTypingStrategyAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0 = (RuleCall)cTypingStrategyAssignment_0.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameBindingTypesIdentifierParserRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cLessThanSignKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Assignment cTypeVarsAssignment_2_1 = (Assignment)cGroup_2.eContents().get(1);
		private final RuleCall cTypeVarsTypeVariableParserRuleCall_2_1_0 = (RuleCall)cTypeVarsAssignment_2_1.eContents().get(0);
		private final Group cGroup_2_2 = (Group)cGroup_2.eContents().get(2);
		private final Keyword cCommaKeyword_2_2_0 = (Keyword)cGroup_2_2.eContents().get(0);
		private final Assignment cTypeVarsAssignment_2_2_1 = (Assignment)cGroup_2_2.eContents().get(1);
		private final RuleCall cTypeVarsTypeVariableParserRuleCall_2_2_1_0 = (RuleCall)cTypeVarsAssignment_2_2_1.eContents().get(0);
		private final Keyword cGreaterThanSignKeyword_2_3 = (Keyword)cGroup_2.eContents().get(3);
		
		//fragment TClassOrInterfaceHeader *:
		//	typingStrategy=TypingStrategyDefSiteOperator? name=BindingTypesIdentifier ('<' typeVars+=super::TypeVariable (','
		//	typeVars+=super::TypeVariable)* '>')?;
		@Override public ParserRule getRule() { return rule; }
		
		//typingStrategy=TypingStrategyDefSiteOperator? name=BindingTypesIdentifier ('<' typeVars+=super::TypeVariable (','
		//typeVars+=super::TypeVariable)* '>')?
		public Group getGroup() { return cGroup; }
		
		//typingStrategy=TypingStrategyDefSiteOperator?
		public Assignment getTypingStrategyAssignment_0() { return cTypingStrategyAssignment_0; }
		
		//TypingStrategyDefSiteOperator
		public RuleCall getTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0() { return cTypingStrategyTypingStrategyDefSiteOperatorParserRuleCall_0_0; }
		
		//name=BindingTypesIdentifier
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//BindingTypesIdentifier
		public RuleCall getNameBindingTypesIdentifierParserRuleCall_1_0() { return cNameBindingTypesIdentifierParserRuleCall_1_0; }
		
		//('<' typeVars+=super::TypeVariable (',' typeVars+=super::TypeVariable)* '>')?
		public Group getGroup_2() { return cGroup_2; }
		
		//'<'
		public Keyword getLessThanSignKeyword_2_0() { return cLessThanSignKeyword_2_0; }
		
		//typeVars+=super::TypeVariable
		public Assignment getTypeVarsAssignment_2_1() { return cTypeVarsAssignment_2_1; }
		
		//super::TypeVariable
		public RuleCall getTypeVarsTypeVariableParserRuleCall_2_1_0() { return cTypeVarsTypeVariableParserRuleCall_2_1_0; }
		
		//(',' typeVars+=super::TypeVariable)*
		public Group getGroup_2_2() { return cGroup_2_2; }
		
		//','
		public Keyword getCommaKeyword_2_2_0() { return cCommaKeyword_2_2_0; }
		
		//typeVars+=super::TypeVariable
		public Assignment getTypeVarsAssignment_2_2_1() { return cTypeVarsAssignment_2_2_1; }
		
		//super::TypeVariable
		public RuleCall getTypeVarsTypeVariableParserRuleCall_2_2_1_0() { return cTypeVarsTypeVariableParserRuleCall_2_2_1_0; }
		
		//'>'
		public Keyword getGreaterThanSignKeyword_2_3() { return cGreaterThanSignKeyword_2_3; }
	}
	public class CallableCtorElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.CallableCtor");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cTMethodAction_0 = (Action)cGroup.eContents().get(0);
		private final RuleCall cTFormalParametersParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		private final RuleCall cColonSepReturnTypeRefParserRuleCall_2 = (RuleCall)cGroup.eContents().get(2);
		private final Keyword cSemicolonKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//CallableCtor TMethod:
		//	{TMethod} TFormalParameters
		//	ColonSepReturnTypeRef?
		//	';'?;
		@Override public ParserRule getRule() { return rule; }
		
		//{TMethod} TFormalParameters ColonSepReturnTypeRef? ';'?
		public Group getGroup() { return cGroup; }
		
		//{TMethod}
		public Action getTMethodAction_0() { return cTMethodAction_0; }
		
		//TFormalParameters
		public RuleCall getTFormalParametersParserRuleCall_1() { return cTFormalParametersParserRuleCall_1; }
		
		//ColonSepReturnTypeRef?
		public RuleCall getColonSepReturnTypeRefParserRuleCall_2() { return cColonSepReturnTypeRefParserRuleCall_2; }
		
		//';'?
		public Keyword getSemicolonKeyword_3() { return cSemicolonKeyword_3; }
	}
	public class TFormalParametersElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TFormalParameters");
		private final Group cGroup = (Group)rule.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Assignment cFparsAssignment_1_0 = (Assignment)cGroup_1.eContents().get(0);
		private final RuleCall cFparsTFormalParameterParserRuleCall_1_0_0 = (RuleCall)cFparsAssignment_1_0.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cGroup_1.eContents().get(1);
		private final Keyword cCommaKeyword_1_1_0 = (Keyword)cGroup_1_1.eContents().get(0);
		private final Assignment cFparsAssignment_1_1_1 = (Assignment)cGroup_1_1.eContents().get(1);
		private final RuleCall cFparsTFormalParameterParserRuleCall_1_1_1_0 = (RuleCall)cFparsAssignment_1_1_1.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		//fragment TFormalParameters *:
		//	'(' (fpars+=TFormalParameter (',' fpars+=TFormalParameter)*)? ')';
		@Override public ParserRule getRule() { return rule; }
		
		//'(' (fpars+=TFormalParameter (',' fpars+=TFormalParameter)*)? ')'
		public Group getGroup() { return cGroup; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_0() { return cLeftParenthesisKeyword_0; }
		
		//(fpars+=TFormalParameter (',' fpars+=TFormalParameter)*)?
		public Group getGroup_1() { return cGroup_1; }
		
		//fpars+=TFormalParameter
		public Assignment getFparsAssignment_1_0() { return cFparsAssignment_1_0; }
		
		//TFormalParameter
		public RuleCall getFparsTFormalParameterParserRuleCall_1_0_0() { return cFparsTFormalParameterParserRuleCall_1_0_0; }
		
		//(',' fpars+=TFormalParameter)*
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//','
		public Keyword getCommaKeyword_1_1_0() { return cCommaKeyword_1_1_0; }
		
		//fpars+=TFormalParameter
		public Assignment getFparsAssignment_1_1_1() { return cFparsAssignment_1_1_1; }
		
		//TFormalParameter
		public RuleCall getFparsTFormalParameterParserRuleCall_1_1_1_0() { return cFparsTFormalParameterParserRuleCall_1_1_1_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_2() { return cRightParenthesisKeyword_2; }
	}
	public class TMemberElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TMember");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cTGetterParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cTSetterParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cTMethodParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cTFieldParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		
		//TMember:
		//	TGetter | TSetter | TMethod | TField;
		@Override public ParserRule getRule() { return rule; }
		
		//TGetter | TSetter | TMethod | TField
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//TGetter
		public RuleCall getTGetterParserRuleCall_0() { return cTGetterParserRuleCall_0; }
		
		//TSetter
		public RuleCall getTSetterParserRuleCall_1() { return cTSetterParserRuleCall_1; }
		
		//TMethod
		public RuleCall getTMethodParserRuleCall_2() { return cTMethodParserRuleCall_2; }
		
		//TField
		public RuleCall getTFieldParserRuleCall_3() { return cTFieldParserRuleCall_3; }
	}
	public class TMethodElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TMethod");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Assignment cDeclaredMemberAccessModifierAssignment_0_0_0 = (Assignment)cGroup_0_0.eContents().get(0);
		private final RuleCall cDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0 = (RuleCall)cDeclaredMemberAccessModifierAssignment_0_0_0.eContents().get(0);
		private final Alternatives cAlternatives_0_0_1 = (Alternatives)cGroup_0_0.eContents().get(1);
		private final Assignment cDeclaredAbstractAssignment_0_0_1_0 = (Assignment)cAlternatives_0_0_1.eContents().get(0);
		private final Keyword cDeclaredAbstractAbstractKeyword_0_0_1_0_0 = (Keyword)cDeclaredAbstractAssignment_0_0_1_0.eContents().get(0);
		private final Assignment cDeclaredStaticAssignment_0_0_1_1 = (Assignment)cAlternatives_0_0_1.eContents().get(1);
		private final Keyword cDeclaredStaticStaticKeyword_0_0_1_1_0 = (Keyword)cDeclaredStaticAssignment_0_0_1_1.eContents().get(0);
		private final RuleCall cTypeVariablesParserRuleCall_0_0_2 = (RuleCall)cGroup_0_0.eContents().get(2);
		private final Alternatives cAlternatives_0_0_3 = (Alternatives)cGroup_0_0.eContents().get(3);
		private final Assignment cNameAssignment_0_0_3_0 = (Assignment)cAlternatives_0_0_3.eContents().get(0);
		private final RuleCall cNameTypesIdentifierParserRuleCall_0_0_3_0_0 = (RuleCall)cNameAssignment_0_0_3_0.eContents().get(0);
		private final Assignment cNameAssignment_0_0_3_1 = (Assignment)cAlternatives_0_0_3.eContents().get(1);
		private final RuleCall cNameTypesComputedPropertyNameParserRuleCall_0_0_3_1_0 = (RuleCall)cNameAssignment_0_0_3_1.eContents().get(0);
		private final RuleCall cTFormalParametersParserRuleCall_0_0_4 = (RuleCall)cGroup_0_0.eContents().get(4);
		private final RuleCall cColonSepReturnTypeRefParserRuleCall_1 = (RuleCall)cGroup.eContents().get(1);
		private final Keyword cSemicolonKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		//TMethod:
		//	=>
		//	(declaredMemberAccessModifier=MemberAccessModifier (declaredAbstract?='abstract' | declaredStatic?='static')?
		//	TypeVariables? (name=TypesIdentifier | name=TypesComputedPropertyName)
		//	-> TFormalParameters) ColonSepReturnTypeRef
		//	';'?;
		@Override public ParserRule getRule() { return rule; }
		
		//=> (declaredMemberAccessModifier=MemberAccessModifier (declaredAbstract?='abstract' | declaredStatic?='static')?
		//TypeVariables? (name=TypesIdentifier | name=TypesComputedPropertyName) -> TFormalParameters) ColonSepReturnTypeRef ';'?
		public Group getGroup() { return cGroup; }
		
		//=> (declaredMemberAccessModifier=MemberAccessModifier (declaredAbstract?='abstract' | declaredStatic?='static')?
		//TypeVariables? (name=TypesIdentifier | name=TypesComputedPropertyName) -> TFormalParameters)
		public Group getGroup_0() { return cGroup_0; }
		
		//declaredMemberAccessModifier=MemberAccessModifier (declaredAbstract?='abstract' | declaredStatic?='static')?
		//TypeVariables? (name=TypesIdentifier | name=TypesComputedPropertyName) -> TFormalParameters
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//declaredMemberAccessModifier=MemberAccessModifier
		public Assignment getDeclaredMemberAccessModifierAssignment_0_0_0() { return cDeclaredMemberAccessModifierAssignment_0_0_0; }
		
		//MemberAccessModifier
		public RuleCall getDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0() { return cDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0; }
		
		//(declaredAbstract?='abstract' | declaredStatic?='static')?
		public Alternatives getAlternatives_0_0_1() { return cAlternatives_0_0_1; }
		
		//declaredAbstract?='abstract'
		public Assignment getDeclaredAbstractAssignment_0_0_1_0() { return cDeclaredAbstractAssignment_0_0_1_0; }
		
		//'abstract'
		public Keyword getDeclaredAbstractAbstractKeyword_0_0_1_0_0() { return cDeclaredAbstractAbstractKeyword_0_0_1_0_0; }
		
		//declaredStatic?='static'
		public Assignment getDeclaredStaticAssignment_0_0_1_1() { return cDeclaredStaticAssignment_0_0_1_1; }
		
		//'static'
		public Keyword getDeclaredStaticStaticKeyword_0_0_1_1_0() { return cDeclaredStaticStaticKeyword_0_0_1_1_0; }
		
		//TypeVariables?
		public RuleCall getTypeVariablesParserRuleCall_0_0_2() { return cTypeVariablesParserRuleCall_0_0_2; }
		
		//name=TypesIdentifier | name=TypesComputedPropertyName
		public Alternatives getAlternatives_0_0_3() { return cAlternatives_0_0_3; }
		
		//name=TypesIdentifier
		public Assignment getNameAssignment_0_0_3_0() { return cNameAssignment_0_0_3_0; }
		
		//TypesIdentifier
		public RuleCall getNameTypesIdentifierParserRuleCall_0_0_3_0_0() { return cNameTypesIdentifierParserRuleCall_0_0_3_0_0; }
		
		//name=TypesComputedPropertyName
		public Assignment getNameAssignment_0_0_3_1() { return cNameAssignment_0_0_3_1; }
		
		//TypesComputedPropertyName
		public RuleCall getNameTypesComputedPropertyNameParserRuleCall_0_0_3_1_0() { return cNameTypesComputedPropertyNameParserRuleCall_0_0_3_1_0; }
		
		//-> TFormalParameters
		public RuleCall getTFormalParametersParserRuleCall_0_0_4() { return cTFormalParametersParserRuleCall_0_0_4; }
		
		//ColonSepReturnTypeRef
		public RuleCall getColonSepReturnTypeRefParserRuleCall_1() { return cColonSepReturnTypeRefParserRuleCall_1; }
		
		//';'?
		public Keyword getSemicolonKeyword_2() { return cSemicolonKeyword_2; }
	}
	public class TFieldElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TField");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cDeclaredMemberAccessModifierAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0 = (RuleCall)cDeclaredMemberAccessModifierAssignment_0.eContents().get(0);
		private final Alternatives cAlternatives_1 = (Alternatives)cGroup.eContents().get(1);
		private final Assignment cDeclaredStaticAssignment_1_0 = (Assignment)cAlternatives_1.eContents().get(0);
		private final Keyword cDeclaredStaticStaticKeyword_1_0_0 = (Keyword)cDeclaredStaticAssignment_1_0.eContents().get(0);
		private final Assignment cConstAssignment_1_1 = (Assignment)cAlternatives_1.eContents().get(1);
		private final Keyword cConstConstKeyword_1_1_0 = (Keyword)cConstAssignment_1_1.eContents().get(0);
		private final Assignment cDeclaredFinalAssignment_1_2 = (Assignment)cAlternatives_1.eContents().get(2);
		private final Keyword cDeclaredFinalFinalKeyword_1_2_0 = (Keyword)cDeclaredFinalAssignment_1_2.eContents().get(0);
		private final Alternatives cAlternatives_2 = (Alternatives)cGroup.eContents().get(2);
		private final Assignment cNameAssignment_2_0 = (Assignment)cAlternatives_2.eContents().get(0);
		private final RuleCall cNameTypesIdentifierParserRuleCall_2_0_0 = (RuleCall)cNameAssignment_2_0.eContents().get(0);
		private final Assignment cNameAssignment_2_1 = (Assignment)cAlternatives_2.eContents().get(1);
		private final RuleCall cNameTypesComputedPropertyNameParserRuleCall_2_1_0 = (RuleCall)cNameAssignment_2_1.eContents().get(0);
		private final Assignment cOptionalAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final Keyword cOptionalQuestionMarkKeyword_3_0 = (Keyword)cOptionalAssignment_3.eContents().get(0);
		private final RuleCall cColonSepTypeRefParserRuleCall_4 = (RuleCall)cGroup.eContents().get(4);
		private final Keyword cSemicolonKeyword_5 = (Keyword)cGroup.eContents().get(5);
		
		//TField:
		//	declaredMemberAccessModifier=MemberAccessModifier (declaredStatic?='static' | const?='const' |
		//	declaredFinal?='final')? (name=TypesIdentifier | name=TypesComputedPropertyName) optional?='?'?
		//	ColonSepTypeRef
		//	';'?;
		@Override public ParserRule getRule() { return rule; }
		
		//declaredMemberAccessModifier=MemberAccessModifier (declaredStatic?='static' | const?='const' | declaredFinal?='final')?
		//(name=TypesIdentifier | name=TypesComputedPropertyName) optional?='?'? ColonSepTypeRef ';'?
		public Group getGroup() { return cGroup; }
		
		//declaredMemberAccessModifier=MemberAccessModifier
		public Assignment getDeclaredMemberAccessModifierAssignment_0() { return cDeclaredMemberAccessModifierAssignment_0; }
		
		//MemberAccessModifier
		public RuleCall getDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0() { return cDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0; }
		
		//(declaredStatic?='static' | const?='const' | declaredFinal?='final')?
		public Alternatives getAlternatives_1() { return cAlternatives_1; }
		
		//declaredStatic?='static'
		public Assignment getDeclaredStaticAssignment_1_0() { return cDeclaredStaticAssignment_1_0; }
		
		//'static'
		public Keyword getDeclaredStaticStaticKeyword_1_0_0() { return cDeclaredStaticStaticKeyword_1_0_0; }
		
		//const?='const'
		public Assignment getConstAssignment_1_1() { return cConstAssignment_1_1; }
		
		//'const'
		public Keyword getConstConstKeyword_1_1_0() { return cConstConstKeyword_1_1_0; }
		
		//declaredFinal?='final'
		public Assignment getDeclaredFinalAssignment_1_2() { return cDeclaredFinalAssignment_1_2; }
		
		//'final'
		public Keyword getDeclaredFinalFinalKeyword_1_2_0() { return cDeclaredFinalFinalKeyword_1_2_0; }
		
		//name=TypesIdentifier | name=TypesComputedPropertyName
		public Alternatives getAlternatives_2() { return cAlternatives_2; }
		
		//name=TypesIdentifier
		public Assignment getNameAssignment_2_0() { return cNameAssignment_2_0; }
		
		//TypesIdentifier
		public RuleCall getNameTypesIdentifierParserRuleCall_2_0_0() { return cNameTypesIdentifierParserRuleCall_2_0_0; }
		
		//name=TypesComputedPropertyName
		public Assignment getNameAssignment_2_1() { return cNameAssignment_2_1; }
		
		//TypesComputedPropertyName
		public RuleCall getNameTypesComputedPropertyNameParserRuleCall_2_1_0() { return cNameTypesComputedPropertyNameParserRuleCall_2_1_0; }
		
		//optional?='?'?
		public Assignment getOptionalAssignment_3() { return cOptionalAssignment_3; }
		
		//'?'
		public Keyword getOptionalQuestionMarkKeyword_3_0() { return cOptionalQuestionMarkKeyword_3_0; }
		
		//ColonSepTypeRef
		public RuleCall getColonSepTypeRefParserRuleCall_4() { return cColonSepTypeRefParserRuleCall_4; }
		
		//';'?
		public Keyword getSemicolonKeyword_5() { return cSemicolonKeyword_5; }
	}
	public class TGetterElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TGetter");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Assignment cDeclaredMemberAccessModifierAssignment_0_0_0 = (Assignment)cGroup_0_0.eContents().get(0);
		private final RuleCall cDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0 = (RuleCall)cDeclaredMemberAccessModifierAssignment_0_0_0.eContents().get(0);
		private final Alternatives cAlternatives_0_0_1 = (Alternatives)cGroup_0_0.eContents().get(1);
		private final Assignment cDeclaredAbstractAssignment_0_0_1_0 = (Assignment)cAlternatives_0_0_1.eContents().get(0);
		private final Keyword cDeclaredAbstractAbstractKeyword_0_0_1_0_0 = (Keyword)cDeclaredAbstractAssignment_0_0_1_0.eContents().get(0);
		private final Assignment cDeclaredStaticAssignment_0_0_1_1 = (Assignment)cAlternatives_0_0_1.eContents().get(1);
		private final Keyword cDeclaredStaticStaticKeyword_0_0_1_1_0 = (Keyword)cDeclaredStaticAssignment_0_0_1_1.eContents().get(0);
		private final Keyword cGetKeyword_0_0_2 = (Keyword)cGroup_0_0.eContents().get(2);
		private final Alternatives cAlternatives_0_0_3 = (Alternatives)cGroup_0_0.eContents().get(3);
		private final Assignment cNameAssignment_0_0_3_0 = (Assignment)cAlternatives_0_0_3.eContents().get(0);
		private final RuleCall cNameTypesIdentifierParserRuleCall_0_0_3_0_0 = (RuleCall)cNameAssignment_0_0_3_0.eContents().get(0);
		private final Assignment cNameAssignment_0_0_3_1 = (Assignment)cAlternatives_0_0_3.eContents().get(1);
		private final RuleCall cNameTypesComputedPropertyNameParserRuleCall_0_0_3_1_0 = (RuleCall)cNameAssignment_0_0_3_1.eContents().get(0);
		private final Assignment cOptionalAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cOptionalQuestionMarkKeyword_1_0 = (Keyword)cOptionalAssignment_1.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Keyword cRightParenthesisKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final RuleCall cColonSepDeclaredTypeRefParserRuleCall_4 = (RuleCall)cGroup.eContents().get(4);
		
		//TGetter:
		//	=>
		//	(declaredMemberAccessModifier=MemberAccessModifier (declaredAbstract?='abstract' | declaredStatic?='static')?
		//	'get' (name=TypesIdentifier | name=TypesComputedPropertyName)) optional?='?'?
		//	'(' ')' ColonSepDeclaredTypeRef;
		@Override public ParserRule getRule() { return rule; }
		
		//=> (declaredMemberAccessModifier=MemberAccessModifier (declaredAbstract?='abstract' | declaredStatic?='static')? 'get'
		//(name=TypesIdentifier | name=TypesComputedPropertyName)) optional?='?'? '(' ')' ColonSepDeclaredTypeRef
		public Group getGroup() { return cGroup; }
		
		//=> (declaredMemberAccessModifier=MemberAccessModifier (declaredAbstract?='abstract' | declaredStatic?='static')? 'get'
		//(name=TypesIdentifier | name=TypesComputedPropertyName))
		public Group getGroup_0() { return cGroup_0; }
		
		//declaredMemberAccessModifier=MemberAccessModifier (declaredAbstract?='abstract' | declaredStatic?='static')? 'get'
		//(name=TypesIdentifier | name=TypesComputedPropertyName)
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//declaredMemberAccessModifier=MemberAccessModifier
		public Assignment getDeclaredMemberAccessModifierAssignment_0_0_0() { return cDeclaredMemberAccessModifierAssignment_0_0_0; }
		
		//MemberAccessModifier
		public RuleCall getDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0() { return cDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0; }
		
		//(declaredAbstract?='abstract' | declaredStatic?='static')?
		public Alternatives getAlternatives_0_0_1() { return cAlternatives_0_0_1; }
		
		//declaredAbstract?='abstract'
		public Assignment getDeclaredAbstractAssignment_0_0_1_0() { return cDeclaredAbstractAssignment_0_0_1_0; }
		
		//'abstract'
		public Keyword getDeclaredAbstractAbstractKeyword_0_0_1_0_0() { return cDeclaredAbstractAbstractKeyword_0_0_1_0_0; }
		
		//declaredStatic?='static'
		public Assignment getDeclaredStaticAssignment_0_0_1_1() { return cDeclaredStaticAssignment_0_0_1_1; }
		
		//'static'
		public Keyword getDeclaredStaticStaticKeyword_0_0_1_1_0() { return cDeclaredStaticStaticKeyword_0_0_1_1_0; }
		
		//'get'
		public Keyword getGetKeyword_0_0_2() { return cGetKeyword_0_0_2; }
		
		//name=TypesIdentifier | name=TypesComputedPropertyName
		public Alternatives getAlternatives_0_0_3() { return cAlternatives_0_0_3; }
		
		//name=TypesIdentifier
		public Assignment getNameAssignment_0_0_3_0() { return cNameAssignment_0_0_3_0; }
		
		//TypesIdentifier
		public RuleCall getNameTypesIdentifierParserRuleCall_0_0_3_0_0() { return cNameTypesIdentifierParserRuleCall_0_0_3_0_0; }
		
		//name=TypesComputedPropertyName
		public Assignment getNameAssignment_0_0_3_1() { return cNameAssignment_0_0_3_1; }
		
		//TypesComputedPropertyName
		public RuleCall getNameTypesComputedPropertyNameParserRuleCall_0_0_3_1_0() { return cNameTypesComputedPropertyNameParserRuleCall_0_0_3_1_0; }
		
		//optional?='?'?
		public Assignment getOptionalAssignment_1() { return cOptionalAssignment_1; }
		
		//'?'
		public Keyword getOptionalQuestionMarkKeyword_1_0() { return cOptionalQuestionMarkKeyword_1_0; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_2() { return cLeftParenthesisKeyword_2; }
		
		//')'
		public Keyword getRightParenthesisKeyword_3() { return cRightParenthesisKeyword_3; }
		
		//ColonSepDeclaredTypeRef
		public RuleCall getColonSepDeclaredTypeRefParserRuleCall_4() { return cColonSepDeclaredTypeRefParserRuleCall_4; }
	}
	public class TSetterElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TSetter");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cGroup.eContents().get(0);
		private final Group cGroup_0_0 = (Group)cGroup_0.eContents().get(0);
		private final Assignment cDeclaredMemberAccessModifierAssignment_0_0_0 = (Assignment)cGroup_0_0.eContents().get(0);
		private final RuleCall cDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0 = (RuleCall)cDeclaredMemberAccessModifierAssignment_0_0_0.eContents().get(0);
		private final Alternatives cAlternatives_0_0_1 = (Alternatives)cGroup_0_0.eContents().get(1);
		private final Assignment cDeclaredAbstractAssignment_0_0_1_0 = (Assignment)cAlternatives_0_0_1.eContents().get(0);
		private final Keyword cDeclaredAbstractAbstractKeyword_0_0_1_0_0 = (Keyword)cDeclaredAbstractAssignment_0_0_1_0.eContents().get(0);
		private final Assignment cDeclaredStaticAssignment_0_0_1_1 = (Assignment)cAlternatives_0_0_1.eContents().get(1);
		private final Keyword cDeclaredStaticStaticKeyword_0_0_1_1_0 = (Keyword)cDeclaredStaticAssignment_0_0_1_1.eContents().get(0);
		private final Keyword cSetKeyword_0_0_2 = (Keyword)cGroup_0_0.eContents().get(2);
		private final Alternatives cAlternatives_0_0_3 = (Alternatives)cGroup_0_0.eContents().get(3);
		private final Assignment cNameAssignment_0_0_3_0 = (Assignment)cAlternatives_0_0_3.eContents().get(0);
		private final RuleCall cNameTypesIdentifierParserRuleCall_0_0_3_0_0 = (RuleCall)cNameAssignment_0_0_3_0.eContents().get(0);
		private final Assignment cNameAssignment_0_0_3_1 = (Assignment)cAlternatives_0_0_3.eContents().get(1);
		private final RuleCall cNameTypesComputedPropertyNameParserRuleCall_0_0_3_1_0 = (RuleCall)cNameAssignment_0_0_3_1.eContents().get(0);
		private final Assignment cOptionalAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cOptionalQuestionMarkKeyword_1_0 = (Keyword)cOptionalAssignment_1.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cFparAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cFparTFormalParameterParserRuleCall_3_0 = (RuleCall)cFparAssignment_3.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//TSetter:
		//	=>
		//	(declaredMemberAccessModifier=MemberAccessModifier (declaredAbstract?='abstract' | declaredStatic?='static')?
		//	'set' (name=TypesIdentifier | name=TypesComputedPropertyName)) optional?='?'?
		//	'(' fpar=TFormalParameter ')';
		@Override public ParserRule getRule() { return rule; }
		
		//=> (declaredMemberAccessModifier=MemberAccessModifier (declaredAbstract?='abstract' | declaredStatic?='static')? 'set'
		//(name=TypesIdentifier | name=TypesComputedPropertyName)) optional?='?'? '(' fpar=TFormalParameter ')'
		public Group getGroup() { return cGroup; }
		
		//=> (declaredMemberAccessModifier=MemberAccessModifier (declaredAbstract?='abstract' | declaredStatic?='static')? 'set'
		//(name=TypesIdentifier | name=TypesComputedPropertyName))
		public Group getGroup_0() { return cGroup_0; }
		
		//declaredMemberAccessModifier=MemberAccessModifier (declaredAbstract?='abstract' | declaredStatic?='static')? 'set'
		//(name=TypesIdentifier | name=TypesComputedPropertyName)
		public Group getGroup_0_0() { return cGroup_0_0; }
		
		//declaredMemberAccessModifier=MemberAccessModifier
		public Assignment getDeclaredMemberAccessModifierAssignment_0_0_0() { return cDeclaredMemberAccessModifierAssignment_0_0_0; }
		
		//MemberAccessModifier
		public RuleCall getDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0() { return cDeclaredMemberAccessModifierMemberAccessModifierEnumRuleCall_0_0_0_0; }
		
		//(declaredAbstract?='abstract' | declaredStatic?='static')?
		public Alternatives getAlternatives_0_0_1() { return cAlternatives_0_0_1; }
		
		//declaredAbstract?='abstract'
		public Assignment getDeclaredAbstractAssignment_0_0_1_0() { return cDeclaredAbstractAssignment_0_0_1_0; }
		
		//'abstract'
		public Keyword getDeclaredAbstractAbstractKeyword_0_0_1_0_0() { return cDeclaredAbstractAbstractKeyword_0_0_1_0_0; }
		
		//declaredStatic?='static'
		public Assignment getDeclaredStaticAssignment_0_0_1_1() { return cDeclaredStaticAssignment_0_0_1_1; }
		
		//'static'
		public Keyword getDeclaredStaticStaticKeyword_0_0_1_1_0() { return cDeclaredStaticStaticKeyword_0_0_1_1_0; }
		
		//'set'
		public Keyword getSetKeyword_0_0_2() { return cSetKeyword_0_0_2; }
		
		//name=TypesIdentifier | name=TypesComputedPropertyName
		public Alternatives getAlternatives_0_0_3() { return cAlternatives_0_0_3; }
		
		//name=TypesIdentifier
		public Assignment getNameAssignment_0_0_3_0() { return cNameAssignment_0_0_3_0; }
		
		//TypesIdentifier
		public RuleCall getNameTypesIdentifierParserRuleCall_0_0_3_0_0() { return cNameTypesIdentifierParserRuleCall_0_0_3_0_0; }
		
		//name=TypesComputedPropertyName
		public Assignment getNameAssignment_0_0_3_1() { return cNameAssignment_0_0_3_1; }
		
		//TypesComputedPropertyName
		public RuleCall getNameTypesComputedPropertyNameParserRuleCall_0_0_3_1_0() { return cNameTypesComputedPropertyNameParserRuleCall_0_0_3_1_0; }
		
		//optional?='?'?
		public Assignment getOptionalAssignment_1() { return cOptionalAssignment_1; }
		
		//'?'
		public Keyword getOptionalQuestionMarkKeyword_1_0() { return cOptionalQuestionMarkKeyword_1_0; }
		
		//'('
		public Keyword getLeftParenthesisKeyword_2() { return cLeftParenthesisKeyword_2; }
		
		//fpar=TFormalParameter
		public Assignment getFparAssignment_3() { return cFparAssignment_3; }
		
		//TFormalParameter
		public RuleCall getFparTFormalParameterParserRuleCall_3_0() { return cFparTFormalParameterParserRuleCall_3_0; }
		
		//')'
		public Keyword getRightParenthesisKeyword_4() { return cRightParenthesisKeyword_4; }
	}
	public class TFunctionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TFunction");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cDeclaredTypeAccessModifierAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0 = (RuleCall)cDeclaredTypeAccessModifierAssignment_0.eContents().get(0);
		private final Assignment cDeclaredProvidedByRuntimeAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0 = (Keyword)cDeclaredProvidedByRuntimeAssignment_1.eContents().get(0);
		private final Keyword cFunctionKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final RuleCall cTypeVariablesParserRuleCall_3 = (RuleCall)cGroup.eContents().get(3);
		private final Assignment cNameAssignment_4 = (Assignment)cGroup.eContents().get(4);
		private final RuleCall cNameBindingTypesIdentifierParserRuleCall_4_0 = (RuleCall)cNameAssignment_4.eContents().get(0);
		private final RuleCall cTFormalParametersParserRuleCall_5 = (RuleCall)cGroup.eContents().get(5);
		private final RuleCall cColonSepReturnTypeRefParserRuleCall_6 = (RuleCall)cGroup.eContents().get(6);
		
		//TFunction:
		//	declaredTypeAccessModifier=TypeAccessModifier
		//	declaredProvidedByRuntime?='providedByRuntime'?
		//	'function'
		//	TypeVariables?
		//	name=BindingTypesIdentifier
		//	TFormalParameters
		//	ColonSepReturnTypeRef;
		@Override public ParserRule getRule() { return rule; }
		
		//declaredTypeAccessModifier=TypeAccessModifier declaredProvidedByRuntime?='providedByRuntime'? 'function' TypeVariables?
		//name=BindingTypesIdentifier TFormalParameters ColonSepReturnTypeRef
		public Group getGroup() { return cGroup; }
		
		//declaredTypeAccessModifier=TypeAccessModifier
		public Assignment getDeclaredTypeAccessModifierAssignment_0() { return cDeclaredTypeAccessModifierAssignment_0; }
		
		//TypeAccessModifier
		public RuleCall getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0() { return cDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0; }
		
		//declaredProvidedByRuntime?='providedByRuntime'?
		public Assignment getDeclaredProvidedByRuntimeAssignment_1() { return cDeclaredProvidedByRuntimeAssignment_1; }
		
		//'providedByRuntime'
		public Keyword getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0() { return cDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0; }
		
		//'function'
		public Keyword getFunctionKeyword_2() { return cFunctionKeyword_2; }
		
		//TypeVariables?
		public RuleCall getTypeVariablesParserRuleCall_3() { return cTypeVariablesParserRuleCall_3; }
		
		//name=BindingTypesIdentifier
		public Assignment getNameAssignment_4() { return cNameAssignment_4; }
		
		//BindingTypesIdentifier
		public RuleCall getNameBindingTypesIdentifierParserRuleCall_4_0() { return cNameBindingTypesIdentifierParserRuleCall_4_0; }
		
		//TFormalParameters
		public RuleCall getTFormalParametersParserRuleCall_5() { return cTFormalParametersParserRuleCall_5; }
		
		//ColonSepReturnTypeRef
		public RuleCall getColonSepReturnTypeRefParserRuleCall_6() { return cColonSepReturnTypeRefParserRuleCall_6; }
	}
	public class TEnumElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TEnum");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cDeclaredTypeAccessModifierAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0 = (RuleCall)cDeclaredTypeAccessModifierAssignment_0.eContents().get(0);
		private final Assignment cDeclaredProvidedByRuntimeAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0 = (Keyword)cDeclaredProvidedByRuntimeAssignment_1.eContents().get(0);
		private final Keyword cEnumKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cNameAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cNameBindingTypesIdentifierParserRuleCall_3_0 = (RuleCall)cNameAssignment_3.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		private final Assignment cLiteralsAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final RuleCall cLiteralsTEnumLiteralParserRuleCall_5_0 = (RuleCall)cLiteralsAssignment_5.eContents().get(0);
		private final Group cGroup_6 = (Group)cGroup.eContents().get(6);
		private final Keyword cCommaKeyword_6_0 = (Keyword)cGroup_6.eContents().get(0);
		private final Assignment cLiteralsAssignment_6_1 = (Assignment)cGroup_6.eContents().get(1);
		private final RuleCall cLiteralsTEnumLiteralParserRuleCall_6_1_0 = (RuleCall)cLiteralsAssignment_6_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_7 = (Keyword)cGroup.eContents().get(7);
		
		//TEnum:
		//	declaredTypeAccessModifier=TypeAccessModifier
		//	declaredProvidedByRuntime?='providedByRuntime'?
		//	'enum' name=BindingTypesIdentifier
		//	'{' literals+=TEnumLiteral (',' literals+=TEnumLiteral)* '}';
		@Override public ParserRule getRule() { return rule; }
		
		//declaredTypeAccessModifier=TypeAccessModifier declaredProvidedByRuntime?='providedByRuntime'? 'enum'
		//name=BindingTypesIdentifier '{' literals+=TEnumLiteral (',' literals+=TEnumLiteral)* '}'
		public Group getGroup() { return cGroup; }
		
		//declaredTypeAccessModifier=TypeAccessModifier
		public Assignment getDeclaredTypeAccessModifierAssignment_0() { return cDeclaredTypeAccessModifierAssignment_0; }
		
		//TypeAccessModifier
		public RuleCall getDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0() { return cDeclaredTypeAccessModifierTypeAccessModifierEnumRuleCall_0_0; }
		
		//declaredProvidedByRuntime?='providedByRuntime'?
		public Assignment getDeclaredProvidedByRuntimeAssignment_1() { return cDeclaredProvidedByRuntimeAssignment_1; }
		
		//'providedByRuntime'
		public Keyword getDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0() { return cDeclaredProvidedByRuntimeProvidedByRuntimeKeyword_1_0; }
		
		//'enum'
		public Keyword getEnumKeyword_2() { return cEnumKeyword_2; }
		
		//name=BindingTypesIdentifier
		public Assignment getNameAssignment_3() { return cNameAssignment_3; }
		
		//BindingTypesIdentifier
		public RuleCall getNameBindingTypesIdentifierParserRuleCall_3_0() { return cNameBindingTypesIdentifierParserRuleCall_3_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_4() { return cLeftCurlyBracketKeyword_4; }
		
		//literals+=TEnumLiteral
		public Assignment getLiteralsAssignment_5() { return cLiteralsAssignment_5; }
		
		//TEnumLiteral
		public RuleCall getLiteralsTEnumLiteralParserRuleCall_5_0() { return cLiteralsTEnumLiteralParserRuleCall_5_0; }
		
		//(',' literals+=TEnumLiteral)*
		public Group getGroup_6() { return cGroup_6; }
		
		//','
		public Keyword getCommaKeyword_6_0() { return cCommaKeyword_6_0; }
		
		//literals+=TEnumLiteral
		public Assignment getLiteralsAssignment_6_1() { return cLiteralsAssignment_6_1; }
		
		//TEnumLiteral
		public RuleCall getLiteralsTEnumLiteralParserRuleCall_6_1_0() { return cLiteralsTEnumLiteralParserRuleCall_6_1_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_7() { return cRightCurlyBracketKeyword_7; }
	}
	public class TEnumLiteralElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TEnumLiteral");
		private final Assignment cNameAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cNameIDENTIFIERTerminalRuleCall_0 = (RuleCall)cNameAssignment.eContents().get(0);
		
		//TEnumLiteral:
		//	name=IDENTIFIER;
		@Override public ParserRule getRule() { return rule; }
		
		//name=IDENTIFIER
		public Assignment getNameAssignment() { return cNameAssignment; }
		
		//IDENTIFIER
		public RuleCall getNameIDENTIFIERTerminalRuleCall_0() { return cNameIDENTIFIERTerminalRuleCall_0; }
	}
	
	public class TypeAccessModifierElements extends AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.TypeAccessModifier");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cProjectEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cProjectProjectKeyword_0_0 = (Keyword)cProjectEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cPublicInternalEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cPublicInternalPublicInternalKeyword_1_0 = (Keyword)cPublicInternalEnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cPublicEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cPublicPublicKeyword_2_0 = (Keyword)cPublicEnumLiteralDeclaration_2.eContents().get(0);
		
		//enum TypeAccessModifier:
		//	project | publicInternal | public;
		public EnumRule getRule() { return rule; }
		
		//project | publicInternal | public
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//project
		public EnumLiteralDeclaration getProjectEnumLiteralDeclaration_0() { return cProjectEnumLiteralDeclaration_0; }
		
		//"project"
		public Keyword getProjectProjectKeyword_0_0() { return cProjectProjectKeyword_0_0; }
		
		//publicInternal
		public EnumLiteralDeclaration getPublicInternalEnumLiteralDeclaration_1() { return cPublicInternalEnumLiteralDeclaration_1; }
		
		//"publicInternal"
		public Keyword getPublicInternalPublicInternalKeyword_1_0() { return cPublicInternalPublicInternalKeyword_1_0; }
		
		//public
		public EnumLiteralDeclaration getPublicEnumLiteralDeclaration_2() { return cPublicEnumLiteralDeclaration_2; }
		
		//"public"
		public Keyword getPublicPublicKeyword_2_0() { return cPublicPublicKeyword_2_0; }
	}
	public class MemberAccessModifierElements extends AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.MemberAccessModifier");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cPrivateEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cPrivatePrivateKeyword_0_0 = (Keyword)cPrivateEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cProjectEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cProjectProjectKeyword_1_0 = (Keyword)cProjectEnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cProtectedInternalEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cProtectedInternalProtectedInternalKeyword_2_0 = (Keyword)cProtectedInternalEnumLiteralDeclaration_2.eContents().get(0);
		private final EnumLiteralDeclaration cProtectedEnumLiteralDeclaration_3 = (EnumLiteralDeclaration)cAlternatives.eContents().get(3);
		private final Keyword cProtectedProtectedKeyword_3_0 = (Keyword)cProtectedEnumLiteralDeclaration_3.eContents().get(0);
		private final EnumLiteralDeclaration cPublicInternalEnumLiteralDeclaration_4 = (EnumLiteralDeclaration)cAlternatives.eContents().get(4);
		private final Keyword cPublicInternalPublicInternalKeyword_4_0 = (Keyword)cPublicInternalEnumLiteralDeclaration_4.eContents().get(0);
		private final EnumLiteralDeclaration cPublicEnumLiteralDeclaration_5 = (EnumLiteralDeclaration)cAlternatives.eContents().get(5);
		private final Keyword cPublicPublicKeyword_5_0 = (Keyword)cPublicEnumLiteralDeclaration_5.eContents().get(0);
		
		//enum MemberAccessModifier:
		//	private | project | protectedInternal | protected | publicInternal | public;
		public EnumRule getRule() { return rule; }
		
		//private | project | protectedInternal | protected | publicInternal | public
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//private
		public EnumLiteralDeclaration getPrivateEnumLiteralDeclaration_0() { return cPrivateEnumLiteralDeclaration_0; }
		
		//"private"
		public Keyword getPrivatePrivateKeyword_0_0() { return cPrivatePrivateKeyword_0_0; }
		
		//project
		public EnumLiteralDeclaration getProjectEnumLiteralDeclaration_1() { return cProjectEnumLiteralDeclaration_1; }
		
		//"project"
		public Keyword getProjectProjectKeyword_1_0() { return cProjectProjectKeyword_1_0; }
		
		//protectedInternal
		public EnumLiteralDeclaration getProtectedInternalEnumLiteralDeclaration_2() { return cProtectedInternalEnumLiteralDeclaration_2; }
		
		//"protectedInternal"
		public Keyword getProtectedInternalProtectedInternalKeyword_2_0() { return cProtectedInternalProtectedInternalKeyword_2_0; }
		
		//protected
		public EnumLiteralDeclaration getProtectedEnumLiteralDeclaration_3() { return cProtectedEnumLiteralDeclaration_3; }
		
		//"protected"
		public Keyword getProtectedProtectedKeyword_3_0() { return cProtectedProtectedKeyword_3_0; }
		
		//publicInternal
		public EnumLiteralDeclaration getPublicInternalEnumLiteralDeclaration_4() { return cPublicInternalEnumLiteralDeclaration_4; }
		
		//"publicInternal"
		public Keyword getPublicInternalPublicInternalKeyword_4_0() { return cPublicInternalPublicInternalKeyword_4_0; }
		
		//public
		public EnumLiteralDeclaration getPublicEnumLiteralDeclaration_5() { return cPublicEnumLiteralDeclaration_5; }
		
		//"public"
		public Keyword getPublicPublicKeyword_5_0() { return cPublicPublicKeyword_5_0; }
	}
	
	private final TypeDefsElements pTypeDefs;
	private final TerminalRule tSTRING;
	private final TerminalRule tSINGLE_STRING_CHAR;
	private final TAnnotationElements pTAnnotation;
	private final TAnnotationArgumentElements pTAnnotationArgument;
	private final TAnnotationStringArgumentElements pTAnnotationStringArgument;
	private final TAnnotationTypeRefArgumentElements pTAnnotationTypeRefArgument;
	private final TypeAccessModifierElements eTypeAccessModifier;
	private final MemberAccessModifierElements eMemberAccessModifier;
	private final TypeElements pType;
	private final TypeRefElements pTypeRef;
	private final PrimitiveTypeElements pPrimitiveType;
	private final TypeReferenceNameElements pTypeReferenceName;
	private final AnyTypeElements pAnyType;
	private final VoidTypeElements pVoidType;
	private final UndefinedTypeElements pUndefinedType;
	private final NullTypeElements pNullType;
	private final TypesIdentifierElements pTypesIdentifier;
	private final BindingTypesIdentifierElements pBindingTypesIdentifier;
	private final VoidOrBindingIdentifierElements pVoidOrBindingIdentifier;
	private final TypesSpecificKeywordsElements pTypesSpecificKeywords;
	private final TypesComputedPropertyNameElements pTypesComputedPropertyName;
	private final TypesSymbolLiteralComputedNameElements pTypesSymbolLiteralComputedName;
	private final TypesStringLiteralComputedNameElements pTypesStringLiteralComputedName;
	private final TObjectPrototypeElements pTObjectPrototype;
	private final VirtualBaseTypeElements pVirtualBaseType;
	private final TClassElements pTClass;
	private final TInterfaceElements pTInterface;
	private final TypeVariableElements pTypeVariable;
	private final TClassOrInterfaceHeaderElements pTClassOrInterfaceHeader;
	private final CallableCtorElements pCallableCtor;
	private final TFormalParametersElements pTFormalParameters;
	private final TMemberElements pTMember;
	private final TMethodElements pTMethod;
	private final TFieldElements pTField;
	private final TGetterElements pTGetter;
	private final TSetterElements pTSetter;
	private final TFunctionElements pTFunction;
	private final TEnumElements pTEnum;
	private final TEnumLiteralElements pTEnumLiteral;
	
	private final Grammar grammar;
	
	private final TypeExpressionsGrammarAccess gaTypeExpressions;
	
	private final UnicodeGrammarAccess gaUnicode;

	@Inject
	public TypesGrammarAccess(GrammarProvider grammarProvider,
			TypeExpressionsGrammarAccess gaTypeExpressions,
			UnicodeGrammarAccess gaUnicode) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaTypeExpressions = gaTypeExpressions;
		this.gaUnicode = gaUnicode;
		this.pTypeDefs = new TypeDefsElements();
		this.tSTRING = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.STRING");
		this.tSINGLE_STRING_CHAR = (TerminalRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.Types.SINGLE_STRING_CHAR");
		this.pTAnnotation = new TAnnotationElements();
		this.pTAnnotationArgument = new TAnnotationArgumentElements();
		this.pTAnnotationStringArgument = new TAnnotationStringArgumentElements();
		this.pTAnnotationTypeRefArgument = new TAnnotationTypeRefArgumentElements();
		this.eTypeAccessModifier = new TypeAccessModifierElements();
		this.eMemberAccessModifier = new MemberAccessModifierElements();
		this.pType = new TypeElements();
		this.pTypeRef = new TypeRefElements();
		this.pPrimitiveType = new PrimitiveTypeElements();
		this.pTypeReferenceName = new TypeReferenceNameElements();
		this.pAnyType = new AnyTypeElements();
		this.pVoidType = new VoidTypeElements();
		this.pUndefinedType = new UndefinedTypeElements();
		this.pNullType = new NullTypeElements();
		this.pTypesIdentifier = new TypesIdentifierElements();
		this.pBindingTypesIdentifier = new BindingTypesIdentifierElements();
		this.pVoidOrBindingIdentifier = new VoidOrBindingIdentifierElements();
		this.pTypesSpecificKeywords = new TypesSpecificKeywordsElements();
		this.pTypesComputedPropertyName = new TypesComputedPropertyNameElements();
		this.pTypesSymbolLiteralComputedName = new TypesSymbolLiteralComputedNameElements();
		this.pTypesStringLiteralComputedName = new TypesStringLiteralComputedNameElements();
		this.pTObjectPrototype = new TObjectPrototypeElements();
		this.pVirtualBaseType = new VirtualBaseTypeElements();
		this.pTClass = new TClassElements();
		this.pTInterface = new TInterfaceElements();
		this.pTypeVariable = new TypeVariableElements();
		this.pTClassOrInterfaceHeader = new TClassOrInterfaceHeaderElements();
		this.pCallableCtor = new CallableCtorElements();
		this.pTFormalParameters = new TFormalParametersElements();
		this.pTMember = new TMemberElements();
		this.pTMethod = new TMethodElements();
		this.pTField = new TFieldElements();
		this.pTGetter = new TGetterElements();
		this.pTSetter = new TSetterElements();
		this.pTFunction = new TFunctionElements();
		this.pTEnum = new TEnumElements();
		this.pTEnumLiteral = new TEnumLiteralElements();
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("org.eclipse.n4js.ts.Types".equals(grammar.getName())) {
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
	
	
	public TypeExpressionsGrammarAccess getTypeExpressionsGrammarAccess() {
		return gaTypeExpressions;
	}
	
	public UnicodeGrammarAccess getUnicodeGrammarAccess() {
		return gaUnicode;
	}

	
	//// ****************************************************************************************************
	//// Root, only used in case types are explicitly defined, e.g., for built-in types.
	//// ****************************************************************************************************
	//TypeDefs:
	//	types+=Type*;
	public TypeDefsElements getTypeDefsAccess() {
		return pTypeDefs;
	}
	
	public ParserRule getTypeDefsRule() {
		return getTypeDefsAccess().getRule();
	}
	
	//terminal STRING:
	//	"'" SINGLE_STRING_CHAR* "'";
	public TerminalRule getSTRINGRule() {
		return tSTRING;
	}
	
	//terminal fragment SINGLE_STRING_CHAR:
	//	!(LINE_TERMINATOR_FRAGMENT | "'" | '\\') | '\\' (LINE_TERMINATOR_SEQUENCE_FRAGMENT | !LINE_TERMINATOR_FRAGMENT);
	public TerminalRule getSINGLE_STRING_CHARRule() {
		return tSINGLE_STRING_CHAR;
	}
	
	//// cf. N4JSSpec §9
	//TAnnotation:
	//	=> ('@' name=IDENTIFIER) (=> '(' (args+=TAnnotationArgument (',' args+=TAnnotationArgument)*)? ')')?;
	public TAnnotationElements getTAnnotationAccess() {
		return pTAnnotation;
	}
	
	public ParserRule getTAnnotationRule() {
		return getTAnnotationAccess().getRule();
	}
	
	//TAnnotationArgument:
	//	TAnnotationStringArgument | TAnnotationTypeRefArgument;
	public TAnnotationArgumentElements getTAnnotationArgumentAccess() {
		return pTAnnotationArgument;
	}
	
	public ParserRule getTAnnotationArgumentRule() {
		return getTAnnotationArgumentAccess().getRule();
	}
	
	//TAnnotationStringArgument:
	//	value=STRING;
	public TAnnotationStringArgumentElements getTAnnotationStringArgumentAccess() {
		return pTAnnotationStringArgument;
	}
	
	public ParserRule getTAnnotationStringArgumentRule() {
		return getTAnnotationStringArgumentAccess().getRule();
	}
	
	//TAnnotationTypeRefArgument:
	//	typeRef=TypeRef;
	public TAnnotationTypeRefArgumentElements getTAnnotationTypeRefArgumentAccess() {
		return pTAnnotationTypeRefArgument;
	}
	
	public ParserRule getTAnnotationTypeRefArgumentRule() {
		return getTAnnotationTypeRefArgumentAccess().getRule();
	}
	
	//enum TypeAccessModifier:
	//	project | publicInternal | public;
	public TypeAccessModifierElements getTypeAccessModifierAccess() {
		return eTypeAccessModifier;
	}
	
	public EnumRule getTypeAccessModifierRule() {
		return getTypeAccessModifierAccess().getRule();
	}
	
	//enum MemberAccessModifier:
	//	private | project | protectedInternal | protected | publicInternal | public;
	public MemberAccessModifierElements getMemberAccessModifierAccess() {
		return eMemberAccessModifier;
	}
	
	public EnumRule getMemberAccessModifierRule() {
		return getMemberAccessModifierAccess().getRule();
	}
	
	//// TODO jvp: more type information, cf. types model
	///*
	// * We cannot define a TypeVariableRef, as this cannot be distinguished from a ParameterizedTypeRef. That is,
	// * we cannot distinguish whether 'T' is a type variable T, or a type with name 'T'. Thus,
	// * type variables are handled similar to types (and are actually types).
	// */ Type:
	//	TObjectPrototype
	//	| TClass
	//	| TInterface
	//	| TEnum
	//	| AnyType
	//	| VoidType
	//	| UndefinedType
	//	| NullType
	//	| PrimitiveType
	//	| TFunction
	//	| TypeVariable
	//	| VirtualBaseType;
	public TypeElements getTypeAccess() {
		return pType;
	}
	
	public ParserRule getTypeRule() {
		return getTypeAccess().getRule();
	}
	
	//@Override
	//TypeRef:
	//	TypeRefWithoutModifiers followedByQuestionMark?='?'?;
	public TypeRefElements getTypeRefAccess() {
		return pTypeRef;
	}
	
	public ParserRule getTypeRefRule() {
		return getTypeRefAccess().getRule();
	}
	
	//PrimitiveType:
	//	'primitive' name=VoidOrBindingIdentifier ('<' typeVars+=TypeVariable '>')? ('indexed'
	//	declaredElementType=ParameterizedTypeRefNominal)?
	//	'{' ('autoboxedType' autoboxedType=[TClassifier|TypeReferenceName])? ('assignmnentCompatible'
	//	assignmentCompatible=[PrimitiveType|TypeReferenceName])?
	//	'}';
	public PrimitiveTypeElements getPrimitiveTypeAccess() {
		return pPrimitiveType;
	}
	
	public ParserRule getPrimitiveTypeRule() {
		return getPrimitiveTypeAccess().getRule();
	}
	
	//@Override
	//TypeReferenceName:
	//	'void' | 'any' | 'undefined' | 'null' | 'indexed' | IDENTIFIER ('/' IDENTIFIER)*;
	public TypeReferenceNameElements getTypeReferenceNameAccess() {
		return pTypeReferenceName;
	}
	
	public ParserRule getTypeReferenceNameRule() {
		return getTypeReferenceNameAccess().getRule();
	}
	
	//// These will be defined only once to be able to load them as singletons and refer to them via x-refs
	//AnyType:
	//	{AnyType} name='any' '{' '}';
	public AnyTypeElements getAnyTypeAccess() {
		return pAnyType;
	}
	
	public ParserRule getAnyTypeRule() {
		return getAnyTypeAccess().getRule();
	}
	
	//VoidType:
	//	{VoidType} name='void' '{' '}';
	public VoidTypeElements getVoidTypeAccess() {
		return pVoidType;
	}
	
	public ParserRule getVoidTypeRule() {
		return getVoidTypeAccess().getRule();
	}
	
	//UndefinedType:
	//	{UndefinedType} name='undefined' '{' '}';
	public UndefinedTypeElements getUndefinedTypeAccess() {
		return pUndefinedType;
	}
	
	public ParserRule getUndefinedTypeRule() {
		return getUndefinedTypeAccess().getRule();
	}
	
	//NullType:
	//	{NullType} name='null' '{' '}';
	public NullTypeElements getNullTypeAccess() {
		return pNullType;
	}
	
	public ParserRule getNullTypeRule() {
		return getNullTypeAccess().getRule();
	}
	
	//TypesIdentifier:
	//	TypesSpecificKeywords | IdentifierName;
	public TypesIdentifierElements getTypesIdentifierAccess() {
		return pTypesIdentifier;
	}
	
	public ParserRule getTypesIdentifierRule() {
		return getTypesIdentifierAccess().getRule();
	}
	
	//BindingTypesIdentifier:
	//	TypesSpecificKeywords | BindingIdentifier<Yield=false>;
	public BindingTypesIdentifierElements getBindingTypesIdentifierAccess() {
		return pBindingTypesIdentifier;
	}
	
	public ParserRule getBindingTypesIdentifierRule() {
		return getBindingTypesIdentifierAccess().getRule();
	}
	
	//// Also allows void as identifier although it is a reserved keyword as of [ECM15]
	//VoidOrBindingIdentifier:
	//	'void' | BindingTypesIdentifier;
	public VoidOrBindingIdentifierElements getVoidOrBindingIdentifierAccess() {
		return pVoidOrBindingIdentifier;
	}
	
	public ParserRule getVoidOrBindingIdentifierRule() {
		return getVoidOrBindingIdentifierAccess().getRule();
	}
	
	//// These keywords are specific to the types language when comparing with N4JS
	//TypesSpecificKeywords: // Types keywords
	//	'any' // no ECMAScript keywords, used in certain [ECM13] and N4JS contexts
	//	| 'undefined' // no ECMAScript nor N4JS keyword, used in types only
	//	| 'object' | 'virtualBase' | 'primitive' | 'autoboxedType' | 'assignmnentCompatible' // must not be used: 'notnull'|'nullable'
	//;
	public TypesSpecificKeywordsElements getTypesSpecificKeywordsAccess() {
		return pTypesSpecificKeywords;
	}
	
	public ParserRule getTypesSpecificKeywordsRule() {
		return getTypesSpecificKeywordsAccess().getRule();
	}
	
	//// see rule "ComputedPropertyName" in ECMAScript 6 specification (e.g. Section 12.2.5)
	//TypesComputedPropertyName:
	//	'[' (TypesSymbolLiteralComputedName | TypesStringLiteralComputedName) ']';
	public TypesComputedPropertyNameElements getTypesComputedPropertyNameAccess() {
		return pTypesComputedPropertyName;
	}
	
	public ParserRule getTypesComputedPropertyNameRule() {
		return getTypesComputedPropertyNameAccess().getRule();
	}
	
	//TypesSymbolLiteralComputedName:
	//	TypesIdentifier '.' TypesIdentifier;
	public TypesSymbolLiteralComputedNameElements getTypesSymbolLiteralComputedNameAccess() {
		return pTypesSymbolLiteralComputedName;
	}
	
	public ParserRule getTypesSymbolLiteralComputedNameRule() {
		return getTypesSymbolLiteralComputedNameAccess().getRule();
	}
	
	//TypesStringLiteralComputedName:
	//	STRING;
	public TypesStringLiteralComputedNameElements getTypesStringLiteralComputedNameAccess() {
		return pTypesStringLiteralComputedName;
	}
	
	public ParserRule getTypesStringLiteralComputedNameRule() {
		return getTypesStringLiteralComputedNameAccess().getRule();
	}
	
	//TObjectPrototype:
	//	declaredTypeAccessModifier=TypeAccessModifier
	//	declaredProvidedByRuntime?='providedByRuntime'?
	//	declaredFinal?='final'?
	//	'object' name=BindingTypesIdentifier
	//	TypeVariables? ('extends' superType=ParameterizedTypeRefNominal)? ('indexed'
	//	declaredElementType=ParameterizedTypeRefNominal)?
	//	annotations+=TAnnotation*
	//	'{'
	//	ownedMembers+=TMember* (callableCtor=CallableCtor
	//	ownedMembers+=TMember*)?
	//	'}';
	public TObjectPrototypeElements getTObjectPrototypeAccess() {
		return pTObjectPrototype;
	}
	
	public ParserRule getTObjectPrototypeRule() {
		return getTObjectPrototypeAccess().getRule();
	}
	
	///*
	// * Virtual base type, not visible to N4JS users.
	// * Used to define common super types, e.g. for all enumerations.
	// */ VirtualBaseType:
	//	{VirtualBaseType}
	//	'virtualBase' name=BindingTypesIdentifier ('indexed' declaredElementType=ParameterizedTypeRefNominal)?
	//	'{'
	//	ownedMembers+=TMember*
	//	'}';
	public VirtualBaseTypeElements getVirtualBaseTypeAccess() {
		return pVirtualBaseType;
	}
	
	public ParserRule getVirtualBaseTypeRule() {
		return getVirtualBaseTypeAccess().getRule();
	}
	
	//TClass:
	//	declaredTypeAccessModifier=TypeAccessModifier
	//	declaredProvidedByRuntime?='providedByRuntime'?
	//	declaredAbstract?='abstract'?
	//	declaredFinal?='final'?
	//	'class' TClassOrInterfaceHeader ('extends' superClassRef=ParameterizedTypeRefNominal)? ('implements'
	//	implementedInterfaceRefs+=ParameterizedTypeRefNominal (',' implementedInterfaceRefs+=ParameterizedTypeRefNominal)*)?
	//	annotations+=TAnnotation*
	//	'{'
	//	ownedMembers+=TMember* (callableCtor=CallableCtor
	//	ownedMembers+=TMember*)?
	//	'}';
	public TClassElements getTClassAccess() {
		return pTClass;
	}
	
	public ParserRule getTClassRule() {
		return getTClassAccess().getRule();
	}
	
	//TInterface:
	//	declaredTypeAccessModifier=TypeAccessModifier
	//	declaredProvidedByRuntime?='providedByRuntime'?
	//	'interface' TClassOrInterfaceHeader ('extends' superInterfaceRefs+=ParameterizedTypeRefNominal (','
	//	superInterfaceRefs+=ParameterizedTypeRefNominal)*)?
	//	annotations+=TAnnotation*
	//	'{' ownedMembers+=TMember* '}';
	public TInterfaceElements getTInterfaceAccess() {
		return pTInterface;
	}
	
	public ParserRule getTInterfaceRule() {
		return getTInterfaceAccess().getRule();
	}
	
	//@Override
	//TypeVariable:
	//	name=IDENTIFIER ('extends' declaredUpperBound=TypeRef)?;
	public TypeVariableElements getTypeVariableAccess() {
		return pTypeVariable;
	}
	
	public ParserRule getTypeVariableRule() {
		return getTypeVariableAccess().getRule();
	}
	
	//fragment TClassOrInterfaceHeader *:
	//	typingStrategy=TypingStrategyDefSiteOperator? name=BindingTypesIdentifier ('<' typeVars+=super::TypeVariable (','
	//	typeVars+=super::TypeVariable)* '>')?;
	public TClassOrInterfaceHeaderElements getTClassOrInterfaceHeaderAccess() {
		return pTClassOrInterfaceHeader;
	}
	
	public ParserRule getTClassOrInterfaceHeaderRule() {
		return getTClassOrInterfaceHeaderAccess().getRule();
	}
	
	//CallableCtor TMethod:
	//	{TMethod} TFormalParameters
	//	ColonSepReturnTypeRef?
	//	';'?;
	public CallableCtorElements getCallableCtorAccess() {
		return pCallableCtor;
	}
	
	public ParserRule getCallableCtorRule() {
		return getCallableCtorAccess().getRule();
	}
	
	//fragment TFormalParameters *:
	//	'(' (fpars+=TFormalParameter (',' fpars+=TFormalParameter)*)? ')';
	public TFormalParametersElements getTFormalParametersAccess() {
		return pTFormalParameters;
	}
	
	public ParserRule getTFormalParametersRule() {
		return getTFormalParametersAccess().getRule();
	}
	
	//TMember:
	//	TGetter | TSetter | TMethod | TField;
	public TMemberElements getTMemberAccess() {
		return pTMember;
	}
	
	public ParserRule getTMemberRule() {
		return getTMemberAccess().getRule();
	}
	
	//TMethod:
	//	=>
	//	(declaredMemberAccessModifier=MemberAccessModifier (declaredAbstract?='abstract' | declaredStatic?='static')?
	//	TypeVariables? (name=TypesIdentifier | name=TypesComputedPropertyName)
	//	-> TFormalParameters) ColonSepReturnTypeRef
	//	';'?;
	public TMethodElements getTMethodAccess() {
		return pTMethod;
	}
	
	public ParserRule getTMethodRule() {
		return getTMethodAccess().getRule();
	}
	
	//TField:
	//	declaredMemberAccessModifier=MemberAccessModifier (declaredStatic?='static' | const?='const' |
	//	declaredFinal?='final')? (name=TypesIdentifier | name=TypesComputedPropertyName) optional?='?'?
	//	ColonSepTypeRef
	//	';'?;
	public TFieldElements getTFieldAccess() {
		return pTField;
	}
	
	public ParserRule getTFieldRule() {
		return getTFieldAccess().getRule();
	}
	
	//TGetter:
	//	=>
	//	(declaredMemberAccessModifier=MemberAccessModifier (declaredAbstract?='abstract' | declaredStatic?='static')?
	//	'get' (name=TypesIdentifier | name=TypesComputedPropertyName)) optional?='?'?
	//	'(' ')' ColonSepDeclaredTypeRef;
	public TGetterElements getTGetterAccess() {
		return pTGetter;
	}
	
	public ParserRule getTGetterRule() {
		return getTGetterAccess().getRule();
	}
	
	//TSetter:
	//	=>
	//	(declaredMemberAccessModifier=MemberAccessModifier (declaredAbstract?='abstract' | declaredStatic?='static')?
	//	'set' (name=TypesIdentifier | name=TypesComputedPropertyName)) optional?='?'?
	//	'(' fpar=TFormalParameter ')';
	public TSetterElements getTSetterAccess() {
		return pTSetter;
	}
	
	public ParserRule getTSetterRule() {
		return getTSetterAccess().getRule();
	}
	
	//TFunction:
	//	declaredTypeAccessModifier=TypeAccessModifier
	//	declaredProvidedByRuntime?='providedByRuntime'?
	//	'function'
	//	TypeVariables?
	//	name=BindingTypesIdentifier
	//	TFormalParameters
	//	ColonSepReturnTypeRef;
	public TFunctionElements getTFunctionAccess() {
		return pTFunction;
	}
	
	public ParserRule getTFunctionRule() {
		return getTFunctionAccess().getRule();
	}
	
	//TEnum:
	//	declaredTypeAccessModifier=TypeAccessModifier
	//	declaredProvidedByRuntime?='providedByRuntime'?
	//	'enum' name=BindingTypesIdentifier
	//	'{' literals+=TEnumLiteral (',' literals+=TEnumLiteral)* '}';
	public TEnumElements getTEnumAccess() {
		return pTEnum;
	}
	
	public ParserRule getTEnumRule() {
		return getTEnumAccess().getRule();
	}
	
	//TEnumLiteral:
	//	name=IDENTIFIER;
	public TEnumLiteralElements getTEnumLiteralAccess() {
		return pTEnumLiteral;
	}
	
	public ParserRule getTEnumLiteralRule() {
		return getTEnumLiteralAccess().getRule();
	}
	
	//IntersectionTypeExpression TypeRef:
	//	ArrayTypeExpression ({IntersectionTypeExpression.typeRefs+=current} ("&" typeRefs+=ArrayTypeExpression)+)?;
	public TypeExpressionsGrammarAccess.IntersectionTypeExpressionElements getIntersectionTypeExpressionAccess() {
		return gaTypeExpressions.getIntersectionTypeExpressionAccess();
	}
	
	public ParserRule getIntersectionTypeExpressionRule() {
		return getIntersectionTypeExpressionAccess().getRule();
	}
	
	//ArrayTypeExpression TypeRef:
	//	{ParameterizedTypeRef} typeArgs+=WildcardOldNotationWithoutBound arrayTypeExpression?='[' ']' =>
	//	({ParameterizedTypeRef.typeArgs+=current} arrayTypeExpression?='[' ']')* | {ParameterizedTypeRef} '('
	//	typeArgs+=Wildcard ')' arrayTypeExpression?='[' ']' => ({ParameterizedTypeRef.typeArgs+=current}
	//	arrayTypeExpression?='[' ']')* | PrimaryTypeExpression => ({ParameterizedTypeRef.typeArgs+=current}
	//	arrayTypeExpression?='[' ']')*;
	public TypeExpressionsGrammarAccess.ArrayTypeExpressionElements getArrayTypeExpressionAccess() {
		return gaTypeExpressions.getArrayTypeExpressionAccess();
	}
	
	public ParserRule getArrayTypeExpressionRule() {
		return getArrayTypeExpressionAccess().getRule();
	}
	
	//PrimaryTypeExpression TypeRef:
	//	ArrowFunctionTypeExpression
	//	| IterableTypeExpression
	//	| TypeRefWithModifiers
	//	| "(" super::TypeRef ")";
	public TypeExpressionsGrammarAccess.PrimaryTypeExpressionElements getPrimaryTypeExpressionAccess() {
		return gaTypeExpressions.getPrimaryTypeExpressionAccess();
	}
	
	public ParserRule getPrimaryTypeExpressionRule() {
		return getPrimaryTypeExpressionAccess().getRule();
	}
	
	//TypeRefWithModifiers StaticBaseTypeRef:
	//	TypeRefWithoutModifiers => followedByQuestionMark?='?'?;
	public TypeExpressionsGrammarAccess.TypeRefWithModifiersElements getTypeRefWithModifiersAccess() {
		return gaTypeExpressions.getTypeRefWithModifiersAccess();
	}
	
	public ParserRule getTypeRefWithModifiersRule() {
		return getTypeRefWithModifiersAccess().getRule();
	}
	
	//TypeRefWithoutModifiers StaticBaseTypeRef:
	//	(ParameterizedTypeRef | ThisTypeRef) => dynamic?='+'? | TypeTypeRef
	//	| FunctionTypeExpressionOLD
	//	| UnionTypeExpressionOLD
	//	| IntersectionTypeExpressionOLD;
	public TypeExpressionsGrammarAccess.TypeRefWithoutModifiersElements getTypeRefWithoutModifiersAccess() {
		return gaTypeExpressions.getTypeRefWithoutModifiersAccess();
	}
	
	public ParserRule getTypeRefWithoutModifiersRule() {
		return getTypeRefWithoutModifiersAccess().getRule();
	}
	
	//TypeRefFunctionTypeExpression StaticBaseTypeRef:
	//	ParameterizedTypeRef
	//	| IterableTypeExpression
	//	| TypeTypeRef
	//	| UnionTypeExpressionOLD
	//	| IntersectionTypeExpressionOLD;
	public TypeExpressionsGrammarAccess.TypeRefFunctionTypeExpressionElements getTypeRefFunctionTypeExpressionAccess() {
		return gaTypeExpressions.getTypeRefFunctionTypeExpressionAccess();
	}
	
	public ParserRule getTypeRefFunctionTypeExpressionRule() {
		return getTypeRefFunctionTypeExpressionAccess().getRule();
	}
	
	//TypeArgInTypeTypeRef TypeArgument:
	//	ParameterizedTypeRefNominal
	//	| ThisTypeRefNominal
	//	| WildcardOldNotation;
	public TypeExpressionsGrammarAccess.TypeArgInTypeTypeRefElements getTypeArgInTypeTypeRefAccess() {
		return gaTypeExpressions.getTypeArgInTypeTypeRefAccess();
	}
	
	public ParserRule getTypeArgInTypeTypeRefRule() {
		return getTypeArgInTypeTypeRefAccess().getRule();
	}
	
	//ThisTypeRef:
	//	ThisTypeRefNominal | ThisTypeRefStructural;
	public TypeExpressionsGrammarAccess.ThisTypeRefElements getThisTypeRefAccess() {
		return gaTypeExpressions.getThisTypeRefAccess();
	}
	
	public ParserRule getThisTypeRefRule() {
		return getThisTypeRefAccess().getRule();
	}
	
	//ThisTypeRefNominal:
	//	{ThisTypeRefNominal} 'this';
	public TypeExpressionsGrammarAccess.ThisTypeRefNominalElements getThisTypeRefNominalAccess() {
		return gaTypeExpressions.getThisTypeRefNominalAccess();
	}
	
	public ParserRule getThisTypeRefNominalRule() {
		return getThisTypeRefNominalAccess().getRule();
	}
	
	//ThisTypeRefStructural:
	//	definedTypingStrategy=TypingStrategyUseSiteOperator
	//	'this' ('with' TStructMemberList)?;
	public TypeExpressionsGrammarAccess.ThisTypeRefStructuralElements getThisTypeRefStructuralAccess() {
		return gaTypeExpressions.getThisTypeRefStructuralAccess();
	}
	
	public ParserRule getThisTypeRefStructuralRule() {
		return getThisTypeRefStructuralAccess().getRule();
	}
	
	//FunctionTypeExpressionOLD FunctionTypeExpression:
	//	{FunctionTypeExpression}
	//	'{' ('@' 'This' '(' declaredThisType=TypeRefFunctionTypeExpression ')')?
	//	'function' ('<' ownedTypeVars+=super::TypeVariable (',' ownedTypeVars+=super::TypeVariable)* '>')?
	//	'(' TAnonymousFormalParameterList ')' ColonSepReturnTypeRef?
	//	'}';
	public TypeExpressionsGrammarAccess.FunctionTypeExpressionOLDElements getFunctionTypeExpressionOLDAccess() {
		return gaTypeExpressions.getFunctionTypeExpressionOLDAccess();
	}
	
	public ParserRule getFunctionTypeExpressionOLDRule() {
		return getFunctionTypeExpressionOLDAccess().getRule();
	}
	
	//ArrowFunctionTypeExpression FunctionTypeExpression:
	//	=> ({FunctionTypeExpression} '(' TAnonymousFormalParameterList ')' '=>') returnTypeRef=PrimaryTypeExpression;
	public TypeExpressionsGrammarAccess.ArrowFunctionTypeExpressionElements getArrowFunctionTypeExpressionAccess() {
		return gaTypeExpressions.getArrowFunctionTypeExpressionAccess();
	}
	
	public ParserRule getArrowFunctionTypeExpressionRule() {
		return getArrowFunctionTypeExpressionAccess().getRule();
	}
	
	//// TODO extract FormalParameterContainer and use returns FormalParameterContainer instead of wildcard
	//fragment TAnonymousFormalParameterList *:
	//	(fpars+=TAnonymousFormalParameter (',' fpars+=TAnonymousFormalParameter)*)?;
	public TypeExpressionsGrammarAccess.TAnonymousFormalParameterListElements getTAnonymousFormalParameterListAccess() {
		return gaTypeExpressions.getTAnonymousFormalParameterListAccess();
	}
	
	public ParserRule getTAnonymousFormalParameterListRule() {
		return getTAnonymousFormalParameterListAccess().getRule();
	}
	
	///**
	// * Used in type expressions, name is optional.
	// */ TAnonymousFormalParameter:
	//	variadic?='...'? (=> (name=BindingIdentifier<Yield=false> -> ColonSepTypeRef) | typeRef=super::TypeRef)
	//	DefaultFormalParameter;
	public TypeExpressionsGrammarAccess.TAnonymousFormalParameterElements getTAnonymousFormalParameterAccess() {
		return gaTypeExpressions.getTAnonymousFormalParameterAccess();
	}
	
	public ParserRule getTAnonymousFormalParameterRule() {
		return getTAnonymousFormalParameterAccess().getRule();
	}
	
	///**
	// * Used in Types language only.
	// */ TFormalParameter:
	//	variadic?='...'? name=BindingIdentifier<Yield=false> ColonSepTypeRef
	//	DefaultFormalParameter;
	public TypeExpressionsGrammarAccess.TFormalParameterElements getTFormalParameterAccess() {
		return gaTypeExpressions.getTFormalParameterAccess();
	}
	
	public ParserRule getTFormalParameterRule() {
		return getTFormalParameterAccess().getRule();
	}
	
	///**
	// * Default initializers in FunctionTypeExpressions or TFunctions
	// * are necessary to specify optional formal parameters. Hence, their
	// * initializer expression is rather uninteresting and limited by validations
	// * to 'undefined'. The shorthand form, that is omitting the initializer, is supported.
	// */ fragment DefaultFormalParameter *:
	//	(hasInitializerAssignment?='=' astInitializer=super::TypeReferenceName?)?;
	public TypeExpressionsGrammarAccess.DefaultFormalParameterElements getDefaultFormalParameterAccess() {
		return gaTypeExpressions.getDefaultFormalParameterAccess();
	}
	
	public ParserRule getDefaultFormalParameterRule() {
		return getDefaultFormalParameterAccess().getRule();
	}
	
	//UnionTypeExpressionOLD UnionTypeExpression:
	//	{UnionTypeExpression}
	//	'union' '{' typeRefs+=super::TypeRef (',' typeRefs+=super::TypeRef)* '}';
	public TypeExpressionsGrammarAccess.UnionTypeExpressionOLDElements getUnionTypeExpressionOLDAccess() {
		return gaTypeExpressions.getUnionTypeExpressionOLDAccess();
	}
	
	public ParserRule getUnionTypeExpressionOLDRule() {
		return getUnionTypeExpressionOLDAccess().getRule();
	}
	
	//IntersectionTypeExpressionOLD IntersectionTypeExpression:
	//	{IntersectionTypeExpression}
	//	'intersection' '{' typeRefs+=super::TypeRef (',' typeRefs+=super::TypeRef)* '}';
	public TypeExpressionsGrammarAccess.IntersectionTypeExpressionOLDElements getIntersectionTypeExpressionOLDAccess() {
		return gaTypeExpressions.getIntersectionTypeExpressionOLDAccess();
	}
	
	public ParserRule getIntersectionTypeExpressionOLDRule() {
		return getIntersectionTypeExpressionOLDAccess().getRule();
	}
	
	//ParameterizedTypeRef:
	//	ParameterizedTypeRefNominal | ParameterizedTypeRefStructural;
	public TypeExpressionsGrammarAccess.ParameterizedTypeRefElements getParameterizedTypeRefAccess() {
		return gaTypeExpressions.getParameterizedTypeRefAccess();
	}
	
	public ParserRule getParameterizedTypeRefRule() {
		return getParameterizedTypeRefAccess().getRule();
	}
	
	//ParameterizedTypeRefNominal ParameterizedTypeRef:
	//	(TypeReference
	//	| {VersionedParameterizedTypeRef} TypeReference VersionRequest) -> TypeArguments?;
	public TypeExpressionsGrammarAccess.ParameterizedTypeRefNominalElements getParameterizedTypeRefNominalAccess() {
		return gaTypeExpressions.getParameterizedTypeRefNominalAccess();
	}
	
	public ParserRule getParameterizedTypeRefNominalRule() {
		return getParameterizedTypeRefNominalAccess().getRule();
	}
	
	//ParameterizedTypeRefStructural:
	//	(definedTypingStrategy=TypingStrategyUseSiteOperator TypeReference
	//	| {VersionedParameterizedTypeRefStructural} definedTypingStrategy=TypingStrategyUseSiteOperator TypeReference
	//	VersionRequest) -> TypeArguments? ('with' TStructMemberList)?;
	public TypeExpressionsGrammarAccess.ParameterizedTypeRefStructuralElements getParameterizedTypeRefStructuralAccess() {
		return gaTypeExpressions.getParameterizedTypeRefStructuralAccess();
	}
	
	public ParserRule getParameterizedTypeRefStructuralRule() {
		return getParameterizedTypeRefStructuralAccess().getRule();
	}
	
	//IterableTypeExpression ParameterizedTypeRef:
	//	iterableTypeExpression?='[' (typeArgs+=EmptyIterableTypeExpressionTail
	//	| typeArgs+=TypeArgument (',' typeArgs+=TypeArgument)* ']');
	public TypeExpressionsGrammarAccess.IterableTypeExpressionElements getIterableTypeExpressionAccess() {
		return gaTypeExpressions.getIterableTypeExpressionAccess();
	}
	
	public ParserRule getIterableTypeExpressionRule() {
		return getIterableTypeExpressionAccess().getRule();
	}
	
	//EmptyIterableTypeExpressionTail Wildcard:
	//	{Wildcard} ']';
	public TypeExpressionsGrammarAccess.EmptyIterableTypeExpressionTailElements getEmptyIterableTypeExpressionTailAccess() {
		return gaTypeExpressions.getEmptyIterableTypeExpressionTailAccess();
	}
	
	public ParserRule getEmptyIterableTypeExpressionTailRule() {
		return getEmptyIterableTypeExpressionTailAccess().getRule();
	}
	
	//fragment VersionRequest *:
	//	requestedVersion=VERSION;
	public TypeExpressionsGrammarAccess.VersionRequestElements getVersionRequestAccess() {
		return gaTypeExpressions.getVersionRequestAccess();
	}
	
	public ParserRule getVersionRequestRule() {
		return getVersionRequestAccess().getRule();
	}
	
	//fragment TypeReference *:
	//	declaredType=[Type|super::TypeReferenceName];
	public TypeExpressionsGrammarAccess.TypeReferenceElements getTypeReferenceAccess() {
		return gaTypeExpressions.getTypeReferenceAccess();
	}
	
	public ParserRule getTypeReferenceRule() {
		return getTypeReferenceAccess().getRule();
	}
	
	//fragment TypeArguments *:
	//	'<' typeArgs+=TypeArgument (',' typeArgs+=TypeArgument)* '>';
	public TypeExpressionsGrammarAccess.TypeArgumentsElements getTypeArgumentsAccess() {
		return gaTypeExpressions.getTypeArgumentsAccess();
	}
	
	public ParserRule getTypeArgumentsRule() {
		return getTypeArgumentsAccess().getRule();
	}
	
	//fragment TStructMemberList *:
	//	'{' (astStructuralMembers+=TStructMember (';' | ',')?)* '}';
	public TypeExpressionsGrammarAccess.TStructMemberListElements getTStructMemberListAccess() {
		return gaTypeExpressions.getTStructMemberListAccess();
	}
	
	public ParserRule getTStructMemberListRule() {
		return getTStructMemberListAccess().getRule();
	}
	
	///**
	// * All TMembers here are only used in ParameterizedTypeRefStructural references
	// * Most type references are optional. However, in the types language (n4ts), these
	// * references are NOT optional.
	// */ TStructMember:
	//	TStructGetter
	//	| TStructSetter
	//	| TStructMethod
	//	| TStructField;
	public TypeExpressionsGrammarAccess.TStructMemberElements getTStructMemberAccess() {
		return gaTypeExpressions.getTStructMemberAccess();
	}
	
	public ParserRule getTStructMemberRule() {
		return getTStructMemberAccess().getRule();
	}
	
	//TStructMethod:
	//	=>
	//	({TStructMethod} TypeVariables?
	//	name=IdentifierName '(') TAnonymousFormalParameterList ')' ColonSepReturnTypeRef?;
	public TypeExpressionsGrammarAccess.TStructMethodElements getTStructMethodAccess() {
		return gaTypeExpressions.getTStructMethodAccess();
	}
	
	public ParserRule getTStructMethodRule() {
		return getTStructMethodAccess().getRule();
	}
	
	//// TODO extract TypeVariableContainer to be used here
	//fragment TypeVariables *:
	//	'<' typeVars+=super::TypeVariable (',' typeVars+=super::TypeVariable)* '>';
	public TypeExpressionsGrammarAccess.TypeVariablesElements getTypeVariablesAccess() {
		return gaTypeExpressions.getTypeVariablesAccess();
	}
	
	public ParserRule getTypeVariablesRule() {
		return getTypeVariablesAccess().getRule();
	}
	
	//fragment ColonSepDeclaredTypeRef *:
	//	':' declaredTypeRef=super::TypeRef;
	public TypeExpressionsGrammarAccess.ColonSepDeclaredTypeRefElements getColonSepDeclaredTypeRefAccess() {
		return gaTypeExpressions.getColonSepDeclaredTypeRefAccess();
	}
	
	public ParserRule getColonSepDeclaredTypeRefRule() {
		return getColonSepDeclaredTypeRefAccess().getRule();
	}
	
	//fragment ColonSepTypeRef *:
	//	':' typeRef=super::TypeRef;
	public TypeExpressionsGrammarAccess.ColonSepTypeRefElements getColonSepTypeRefAccess() {
		return gaTypeExpressions.getColonSepTypeRefAccess();
	}
	
	public ParserRule getColonSepTypeRefRule() {
		return getColonSepTypeRefAccess().getRule();
	}
	
	//fragment ColonSepReturnTypeRef *:
	//	':' returnTypeRef=super::TypeRef;
	public TypeExpressionsGrammarAccess.ColonSepReturnTypeRefElements getColonSepReturnTypeRefAccess() {
		return gaTypeExpressions.getColonSepReturnTypeRefAccess();
	}
	
	public ParserRule getColonSepReturnTypeRefRule() {
		return getColonSepReturnTypeRefAccess().getRule();
	}
	
	//TStructField:
	//	name=IdentifierName optional?='?'? ColonSepTypeRef?;
	public TypeExpressionsGrammarAccess.TStructFieldElements getTStructFieldAccess() {
		return gaTypeExpressions.getTStructFieldAccess();
	}
	
	public ParserRule getTStructFieldRule() {
		return getTStructFieldAccess().getRule();
	}
	
	//TStructGetter:
	//	=> ({TStructGetter}
	//	'get'
	//	name=IdentifierName) optional?='?'?
	//	'(' ')' ColonSepDeclaredTypeRef?;
	public TypeExpressionsGrammarAccess.TStructGetterElements getTStructGetterAccess() {
		return gaTypeExpressions.getTStructGetterAccess();
	}
	
	public ParserRule getTStructGetterRule() {
		return getTStructGetterAccess().getRule();
	}
	
	//TStructSetter:
	//	=> ({TStructSetter}
	//	'set'
	//	name=IdentifierName) optional?='?'?
	//	'(' fpar=TAnonymousFormalParameter ')';
	public TypeExpressionsGrammarAccess.TStructSetterElements getTStructSetterAccess() {
		return gaTypeExpressions.getTStructSetterAccess();
	}
	
	public ParserRule getTStructSetterRule() {
		return getTStructSetterAccess().getRule();
	}
	
	//TypingStrategyUseSiteOperator TypingStrategy:
	//	'~' ('~' | STRUCTMODSUFFIX)?;
	public TypeExpressionsGrammarAccess.TypingStrategyUseSiteOperatorElements getTypingStrategyUseSiteOperatorAccess() {
		return gaTypeExpressions.getTypingStrategyUseSiteOperatorAccess();
	}
	
	public ParserRule getTypingStrategyUseSiteOperatorRule() {
		return getTypingStrategyUseSiteOperatorAccess().getRule();
	}
	
	//TypingStrategyDefSiteOperator TypingStrategy:
	//	'~';
	public TypeExpressionsGrammarAccess.TypingStrategyDefSiteOperatorElements getTypingStrategyDefSiteOperatorAccess() {
		return gaTypeExpressions.getTypingStrategyDefSiteOperatorAccess();
	}
	
	public ParserRule getTypingStrategyDefSiteOperatorRule() {
		return getTypingStrategyDefSiteOperatorAccess().getRule();
	}
	
	//terminal STRUCTMODSUFFIX:
	//	('r' | 'i' | 'w' | '\\u2205') '~';
	public TerminalRule getSTRUCTMODSUFFIXRule() {
		return gaTypeExpressions.getSTRUCTMODSUFFIXRule();
	}
	
	//TypeTypeRef:
	//	{TypeTypeRef} ('type' | constructorRef?='constructor')
	//	'{' typeArg=TypeArgInTypeTypeRef '}';
	public TypeExpressionsGrammarAccess.TypeTypeRefElements getTypeTypeRefAccess() {
		return gaTypeExpressions.getTypeTypeRefAccess();
	}
	
	public ParserRule getTypeTypeRefRule() {
		return getTypeTypeRefAccess().getRule();
	}
	
	//TypeArgument:
	//	Wildcard | super::TypeRef;
	public TypeExpressionsGrammarAccess.TypeArgumentElements getTypeArgumentAccess() {
		return gaTypeExpressions.getTypeArgumentAccess();
	}
	
	public ParserRule getTypeArgumentRule() {
		return getTypeArgumentAccess().getRule();
	}
	
	//Wildcard:
	//	WildcardOldNotation
	//	| WildcardNewNotation;
	public TypeExpressionsGrammarAccess.WildcardElements getWildcardAccess() {
		return gaTypeExpressions.getWildcardAccess();
	}
	
	public ParserRule getWildcardRule() {
		return getWildcardAccess().getRule();
	}
	
	//WildcardOldNotation Wildcard:
	//	=> ({Wildcard} '?') ('extends' declaredUpperBound=super::TypeRef | 'super'
	//	declaredLowerBound=super::TypeRef)?;
	public TypeExpressionsGrammarAccess.WildcardOldNotationElements getWildcardOldNotationAccess() {
		return gaTypeExpressions.getWildcardOldNotationAccess();
	}
	
	public ParserRule getWildcardOldNotationRule() {
		return getWildcardOldNotationAccess().getRule();
	}
	
	//WildcardOldNotationWithoutBound Wildcard:
	//	{Wildcard} '?';
	public TypeExpressionsGrammarAccess.WildcardOldNotationWithoutBoundElements getWildcardOldNotationWithoutBoundAccess() {
		return gaTypeExpressions.getWildcardOldNotationWithoutBoundAccess();
	}
	
	public ParserRule getWildcardOldNotationWithoutBoundRule() {
		return getWildcardOldNotationWithoutBoundAccess().getRule();
	}
	
	//WildcardNewNotation Wildcard:
	//	usingInOutNotation?='out' declaredUpperBound=super::TypeRef | usingInOutNotation?='in'
	//	declaredLowerBound=super::TypeRef;
	public TypeExpressionsGrammarAccess.WildcardNewNotationElements getWildcardNewNotationAccess() {
		return gaTypeExpressions.getWildcardNewNotationAccess();
	}
	
	public ParserRule getWildcardNewNotationRule() {
		return getWildcardNewNotationAccess().getRule();
	}
	
	//TypeVariable:
	//	(declaredCovariant?='out' | declaredContravariant?='in')?
	//	name=IDENTIFIER ('extends' declaredUpperBound=super::TypeRef)?;
	public TypeExpressionsGrammarAccess.TypeVariableElements getTypeExpressionsTypeVariableAccess() {
		return gaTypeExpressions.getTypeVariableAccess();
	}
	
	public ParserRule getTypeExpressionsTypeVariableRule() {
		return getTypeExpressionsTypeVariableAccess().getRule();
	}
	
	///*
	// * [ECM11] (7.6, pp. 17)
	// * Identifier :: IdentifierName but not ReservedWord
	// * ReservedWord :: Keyword | FutureReservedWord | NullLiteral | BooleanLiteral
	// */ BindingIdentifier <Yield>:
	//	IDENTIFIER
	//	// yield as identifier as of [ECM15] (11.6.2, pp. 165)
	//	| <!Yield> 'yield'
	//	| N4Keyword;
	public TypeExpressionsGrammarAccess.BindingIdentifierElements getBindingIdentifierAccess() {
		return gaTypeExpressions.getBindingIdentifierAccess();
	}
	
	public ParserRule getBindingIdentifierRule() {
		return getBindingIdentifierAccess().getRule();
	}
	
	//IdentifierName:
	//	IDENTIFIER | ReservedWord | N4Keyword;
	public TypeExpressionsGrammarAccess.IdentifierNameElements getIdentifierNameAccess() {
		return gaTypeExpressions.getIdentifierNameAccess();
	}
	
	public ParserRule getIdentifierNameRule() {
		return getIdentifierNameAccess().getRule();
	}
	
	//ReservedWord: // Keywords as of [ECM15] (11.6.2, pp. 165)
	//	'break' | 'case' | 'catch' | 'class' | 'const' | 'continue' | 'debugger' | 'default' | 'delete' | 'do' | 'else' |
	//	'export' | 'extends' | 'finally' | 'for' | 'function' | 'if' | 'import' | 'in' | 'instanceof' | 'new' | 'return' |
	//	'super' | 'switch' | 'this' | 'throw' | 'try' | 'typeof' | 'var' | 'void' | 'while' | 'with' | 'yield' // null literal
	//	| 'null' // boolean literal
	//	| 'true' | 'false' // Future Reserved Word as of [ECM15] (11.6.2.2, pp. 166)
	//	// | 'await' /* reserved word only if parse goal is module - compromise: allow as identifier and validate */
	//	| 'enum';
	public TypeExpressionsGrammarAccess.ReservedWordElements getReservedWordAccess() {
		return gaTypeExpressions.getReservedWordAccess();
	}
	
	public ParserRule getReservedWordRule() {
		return getReservedWordAccess().getRule();
	}
	
	//N4Keyword:
	//	'get' | 'set'
	//	| 'let'
	//	| 'project'
	//	| 'external' | 'abstract' | 'static'
	//	| 'as' | 'from' | 'constructor' | 'of' | 'target'
	//	| 'type' | 'union' | 'intersection'
	//	| 'This' | 'Promisify'
	//	// future reserved keyword in [ECM15] only in modules, we add additional validation
	//	| 'await'
	//	// async is not a reserved keyword, i.e. it can be used as a variable name
	//	| 'async'
	//	// future reserved keywords in [ECM15], restricted via static semantic in [ECM15]
	//	| 'implements' | 'interface'
	//	| 'private' | 'protected' | 'public' // package not used in N4JS
	//	// definition-site variance
	//	| 'out';
	public TypeExpressionsGrammarAccess.N4KeywordElements getN4KeywordAccess() {
		return gaTypeExpressions.getN4KeywordAccess();
	}
	
	public ParserRule getN4KeywordRule() {
		return getN4KeywordAccess().getRule();
	}
	
	//terminal IDENTIFIER:
	//	IDENTIFIER_START IDENTIFIER_PART*;
	public TerminalRule getIDENTIFIERRule() {
		return gaTypeExpressions.getIDENTIFIERRule();
	}
	
	//terminal INT returns ecore::EBigDecimal:
	//	DECIMAL_INTEGER_LITERAL_FRAGMENT;
	public TerminalRule getINTRule() {
		return gaTypeExpressions.getINTRule();
	}
	
	//terminal ML_COMMENT:
	//	ML_COMMENT_FRAGMENT;
	public TerminalRule getML_COMMENTRule() {
		return gaTypeExpressions.getML_COMMENTRule();
	}
	
	//terminal SL_COMMENT:
	//	'//' !LINE_TERMINATOR_FRAGMENT*;
	public TerminalRule getSL_COMMENTRule() {
		return gaTypeExpressions.getSL_COMMENTRule();
	}
	
	//terminal EOL:
	//	LINE_TERMINATOR_SEQUENCE_FRAGMENT;
	public TerminalRule getEOLRule() {
		return gaTypeExpressions.getEOLRule();
	}
	
	//terminal WS:
	//	WHITESPACE_FRAGMENT+;
	public TerminalRule getWSRule() {
		return gaTypeExpressions.getWSRule();
	}
	
	//terminal fragment UNICODE_ESCAPE_FRAGMENT:
	//	'\\' ('u' (HEX_DIGIT (HEX_DIGIT (HEX_DIGIT HEX_DIGIT?)?)?
	//	| '{' HEX_DIGIT* '}'?)?)?;
	public TerminalRule getUNICODE_ESCAPE_FRAGMENTRule() {
		return gaTypeExpressions.getUNICODE_ESCAPE_FRAGMENTRule();
	}
	
	//terminal fragment IDENTIFIER_START:
	//	UNICODE_LETTER_FRAGMENT
	//	| '$'
	//	| '_'
	//	| UNICODE_ESCAPE_FRAGMENT;
	public TerminalRule getIDENTIFIER_STARTRule() {
		return gaTypeExpressions.getIDENTIFIER_STARTRule();
	}
	
	//terminal fragment IDENTIFIER_PART:
	//	UNICODE_LETTER_FRAGMENT
	//	| UNICODE_ESCAPE_FRAGMENT
	//	| '$'
	//	| UNICODE_COMBINING_MARK_FRAGMENT
	//	| UNICODE_DIGIT_FRAGMENT
	//	| UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT
	//	| ZWNJ
	//	| ZWJ;
	public TerminalRule getIDENTIFIER_PARTRule() {
		return gaTypeExpressions.getIDENTIFIER_PARTRule();
	}
	
	//terminal DOT_DOT:
	//	'..';
	public TerminalRule getDOT_DOTRule() {
		return gaTypeExpressions.getDOT_DOTRule();
	}
	
	//terminal VERSION returns ecore::EBigDecimal:
	//	'#' WS* INT;
	public TerminalRule getVERSIONRule() {
		return gaTypeExpressions.getVERSIONRule();
	}
	
	//terminal fragment HEX_DIGIT:
	//	DECIMAL_DIGIT_FRAGMENT | 'a'..'f' | 'A'..'F';
	public TerminalRule getHEX_DIGITRule() {
		return gaUnicode.getHEX_DIGITRule();
	}
	
	//terminal fragment DECIMAL_INTEGER_LITERAL_FRAGMENT:
	//	'0'
	//	| '1'..'9' DECIMAL_DIGIT_FRAGMENT*;
	public TerminalRule getDECIMAL_INTEGER_LITERAL_FRAGMENTRule() {
		return gaUnicode.getDECIMAL_INTEGER_LITERAL_FRAGMENTRule();
	}
	
	//terminal fragment DECIMAL_DIGIT_FRAGMENT:
	//	'0'..'9';
	public TerminalRule getDECIMAL_DIGIT_FRAGMENTRule() {
		return gaUnicode.getDECIMAL_DIGIT_FRAGMENTRule();
	}
	
	//terminal fragment ZWJ:
	//	'\\u200D';
	public TerminalRule getZWJRule() {
		return gaUnicode.getZWJRule();
	}
	
	//terminal fragment ZWNJ:
	//	'\\u200C';
	public TerminalRule getZWNJRule() {
		return gaUnicode.getZWNJRule();
	}
	
	//terminal fragment BOM:
	//	'\\uFEFF';
	public TerminalRule getBOMRule() {
		return gaUnicode.getBOMRule();
	}
	
	//terminal fragment WHITESPACE_FRAGMENT:
	//	'\\u0009' | '\\u000B' | '\\u000C' | '\\u0020' | '\\u00A0' | BOM | UNICODE_SPACE_SEPARATOR_FRAGMENT;
	public TerminalRule getWHITESPACE_FRAGMENTRule() {
		return gaUnicode.getWHITESPACE_FRAGMENTRule();
	}
	
	//terminal fragment LINE_TERMINATOR_FRAGMENT:
	//	'\\u000A' | '\\u000D' | '\\u2028' | '\\u2029';
	public TerminalRule getLINE_TERMINATOR_FRAGMENTRule() {
		return gaUnicode.getLINE_TERMINATOR_FRAGMENTRule();
	}
	
	//terminal fragment LINE_TERMINATOR_SEQUENCE_FRAGMENT:
	//	'\\u000A' | '\\u000D' '\\u000A'? | '\\u2028' | '\\u2029';
	public TerminalRule getLINE_TERMINATOR_SEQUENCE_FRAGMENTRule() {
		return gaUnicode.getLINE_TERMINATOR_SEQUENCE_FRAGMENTRule();
	}
	
	//terminal fragment SL_COMMENT_FRAGMENT:
	//	'//' !LINE_TERMINATOR_FRAGMENT*;
	public TerminalRule getSL_COMMENT_FRAGMENTRule() {
		return gaUnicode.getSL_COMMENT_FRAGMENTRule();
	}
	
	//terminal fragment ML_COMMENT_FRAGMENT:
	//	'/*'->'*/';
	public TerminalRule getML_COMMENT_FRAGMENTRule() {
		return gaUnicode.getML_COMMENT_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_COMBINING_MARK_FRAGMENT: // any character in the Unicode categories
	//// ―Non-spacing mark (Mn)
	//// ―Combining spacing mark (Mc)
	//	'\\u0300'..'\\u036F' | '\\u0483'..'\\u0487' | '\\u0591'..'\\u05BD' | '\\u05BF' | '\\u05C1'..'\\u05C2' | '\\u05C4'..'\\u05C5' |
	//	'\\u05C7' | '\\u0610'..'\\u061A' | '\\u064B'..'\\u065F' | '\\u0670' | '\\u06D6'..'\\u06DC' | '\\u06DF'..'\\u06E4' |
	//	'\\u06E7'..'\\u06E8' | '\\u06EA'..'\\u06ED' | '\\u0711' | '\\u0730'..'\\u074A' | '\\u07A6'..'\\u07B0' | '\\u07EB'..'\\u07F3' |
	//	'\\u0816'..'\\u0819' | '\\u081B'..'\\u0823' | '\\u0825'..'\\u0827' | '\\u0829'..'\\u082D' | '\\u0859'..'\\u085B' |
	//	'\\u08E3'..'\\u0903' | '\\u093A'..'\\u093C' | '\\u093E'..'\\u094F' | '\\u0951'..'\\u0957' | '\\u0962'..'\\u0963' |
	//	'\\u0981'..'\\u0983' | '\\u09BC' | '\\u09BE'..'\\u09C4' | '\\u09C7'..'\\u09C8' | '\\u09CB'..'\\u09CD' | '\\u09D7' |
	//	'\\u09E2'..'\\u09E3' | '\\u0A01'..'\\u0A03' | '\\u0A3C' | '\\u0A3E'..'\\u0A42' | '\\u0A47'..'\\u0A48' | '\\u0A4B'..'\\u0A4D' |
	//	'\\u0A51' | '\\u0A70'..'\\u0A71' | '\\u0A75' | '\\u0A81'..'\\u0A83' | '\\u0ABC' | '\\u0ABE'..'\\u0AC5' | '\\u0AC7'..'\\u0AC9' |
	//	'\\u0ACB'..'\\u0ACD' | '\\u0AE2'..'\\u0AE3' | '\\u0B01'..'\\u0B03' | '\\u0B3C' | '\\u0B3E'..'\\u0B44' | '\\u0B47'..'\\u0B48' |
	//	'\\u0B4B'..'\\u0B4D' | '\\u0B56'..'\\u0B57' | '\\u0B62'..'\\u0B63' | '\\u0B82' | '\\u0BBE'..'\\u0BC2' | '\\u0BC6'..'\\u0BC8' |
	//	'\\u0BCA'..'\\u0BCD' | '\\u0BD7' | '\\u0C00'..'\\u0C03' | '\\u0C3E'..'\\u0C44' | '\\u0C46'..'\\u0C48' | '\\u0C4A'..'\\u0C4D' |
	//	'\\u0C55'..'\\u0C56' | '\\u0C62'..'\\u0C63' | '\\u0C81'..'\\u0C83' | '\\u0CBC' | '\\u0CBE'..'\\u0CC4' | '\\u0CC6'..'\\u0CC8' |
	//	'\\u0CCA'..'\\u0CCD' | '\\u0CD5'..'\\u0CD6' | '\\u0CE2'..'\\u0CE3' | '\\u0D01'..'\\u0D03' | '\\u0D3E'..'\\u0D44' |
	//	'\\u0D46'..'\\u0D48' | '\\u0D4A'..'\\u0D4D' | '\\u0D57' | '\\u0D62'..'\\u0D63' | '\\u0D82'..'\\u0D83' | '\\u0DCA' |
	//	'\\u0DCF'..'\\u0DD4' | '\\u0DD6' | '\\u0DD8'..'\\u0DDF' | '\\u0DF2'..'\\u0DF3' | '\\u0E31' | '\\u0E34'..'\\u0E3A' |
	//	'\\u0E47'..'\\u0E4E' | '\\u0EB1' | '\\u0EB4'..'\\u0EB9' | '\\u0EBB'..'\\u0EBC' | '\\u0EC8'..'\\u0ECD' | '\\u0F18'..'\\u0F19' |
	//	'\\u0F35' | '\\u0F37' | '\\u0F39' | '\\u0F3E'..'\\u0F3F' | '\\u0F71'..'\\u0F84' | '\\u0F86'..'\\u0F87' | '\\u0F8D'..'\\u0F97' |
	//	'\\u0F99'..'\\u0FBC' | '\\u0FC6' | '\\u102B'..'\\u103E' | '\\u1056'..'\\u1059' | '\\u105E'..'\\u1060' | '\\u1062'..'\\u1064' |
	//	'\\u1067'..'\\u106D' | '\\u1071'..'\\u1074' | '\\u1082'..'\\u108D' | '\\u108F' | '\\u109A'..'\\u109D' | '\\u135D'..'\\u135F' |
	//	'\\u1712'..'\\u1714' | '\\u1732'..'\\u1734' | '\\u1752'..'\\u1753' | '\\u1772'..'\\u1773' | '\\u17B4'..'\\u17D3' | '\\u17DD' |
	//	'\\u180B'..'\\u180D' | '\\u18A9' | '\\u1920'..'\\u192B' | '\\u1930'..'\\u193B' | '\\u1A17'..'\\u1A1B' | '\\u1A55'..'\\u1A5E' |
	//	'\\u1A60'..'\\u1A7C' | '\\u1A7F' | '\\u1AB0'..'\\u1ABD' | '\\u1B00'..'\\u1B04' | '\\u1B34'..'\\u1B44' | '\\u1B6B'..'\\u1B73' |
	//	'\\u1B80'..'\\u1B82' | '\\u1BA1'..'\\u1BAD' | '\\u1BE6'..'\\u1BF3' | '\\u1C24'..'\\u1C37' | '\\u1CD0'..'\\u1CD2' |
	//	'\\u1CD4'..'\\u1CE8' | '\\u1CED' | '\\u1CF2'..'\\u1CF4' | '\\u1CF8'..'\\u1CF9' | '\\u1DC0'..'\\u1DF5' | '\\u1DFC'..'\\u1DFF' |
	//	'\\u20D0'..'\\u20DC' | '\\u20E1' | '\\u20E5'..'\\u20F0' | '\\u2CEF'..'\\u2CF1' | '\\u2D7F' | '\\u2DE0'..'\\u2DFF' |
	//	'\\u302A'..'\\u302F' | '\\u3099'..'\\u309A' | '\\uA66F' | '\\uA674'..'\\uA67D' | '\\uA69E'..'\\uA69F' | '\\uA6F0'..'\\uA6F1' |
	//	'\\uA802' | '\\uA806' | '\\uA80B' | '\\uA823'..'\\uA827' | '\\uA880'..'\\uA881' | '\\uA8B4'..'\\uA8C4' | '\\uA8E0'..'\\uA8F1' |
	//	'\\uA926'..'\\uA92D' | '\\uA947'..'\\uA953' | '\\uA980'..'\\uA983' | '\\uA9B3'..'\\uA9C0' | '\\uA9E5' | '\\uAA29'..'\\uAA36' |
	//	'\\uAA43' | '\\uAA4C'..'\\uAA4D' | '\\uAA7B'..'\\uAA7D' | '\\uAAB0' | '\\uAAB2'..'\\uAAB4' | '\\uAAB7'..'\\uAAB8' |
	//	'\\uAABE'..'\\uAABF' | '\\uAAC1' | '\\uAAEB'..'\\uAAEF' | '\\uAAF5'..'\\uAAF6' | '\\uABE3'..'\\uABEA' | '\\uABEC'..'\\uABED' |
	//	'\\uFB1E' | '\\uFE00'..'\\uFE0F' | '\\uFE20'..'\\uFE2F';
	public TerminalRule getUNICODE_COMBINING_MARK_FRAGMENTRule() {
		return gaUnicode.getUNICODE_COMBINING_MARK_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_DIGIT_FRAGMENT: // any character in the Unicode categories
	//// ―Decimal number (Nd)
	//	'\\u0030'..'\\u0039' | '\\u0660'..'\\u0669' | '\\u06F0'..'\\u06F9' | '\\u07C0'..'\\u07C9' | '\\u0966'..'\\u096F' |
	//	'\\u09E6'..'\\u09EF' | '\\u0A66'..'\\u0A6F' | '\\u0AE6'..'\\u0AEF' | '\\u0B66'..'\\u0B6F' | '\\u0BE6'..'\\u0BEF' |
	//	'\\u0C66'..'\\u0C6F' | '\\u0CE6'..'\\u0CEF' | '\\u0D66'..'\\u0D6F' | '\\u0DE6'..'\\u0DEF' | '\\u0E50'..'\\u0E59' |
	//	'\\u0ED0'..'\\u0ED9' | '\\u0F20'..'\\u0F29' | '\\u1040'..'\\u1049' | '\\u1090'..'\\u1099' | '\\u17E0'..'\\u17E9' |
	//	'\\u1810'..'\\u1819' | '\\u1946'..'\\u194F' | '\\u19D0'..'\\u19D9' | '\\u1A80'..'\\u1A89' | '\\u1A90'..'\\u1A99' |
	//	'\\u1B50'..'\\u1B59' | '\\u1BB0'..'\\u1BB9' | '\\u1C40'..'\\u1C49' | '\\u1C50'..'\\u1C59' | '\\uA620'..'\\uA629' |
	//	'\\uA8D0'..'\\uA8D9' | '\\uA900'..'\\uA909' | '\\uA9D0'..'\\uA9D9' | '\\uA9F0'..'\\uA9F9' | '\\uAA50'..'\\uAA59' |
	//	'\\uABF0'..'\\uABF9' | '\\uFF10'..'\\uFF19';
	public TerminalRule getUNICODE_DIGIT_FRAGMENTRule() {
		return gaUnicode.getUNICODE_DIGIT_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_CONNECTOR_PUNCTUATION_FRAGMENT: // any character in the Unicode categories
	//// ―Connector punctuation (Pc)
	//	'\\u005F' | '\\u203F'..'\\u2040' | '\\u2054' | '\\uFE33'..'\\uFE34' | '\\uFE4D'..'\\uFE4F' | '\\uFF3F';
	public TerminalRule getUNICODE_CONNECTOR_PUNCTUATION_FRAGMENTRule() {
		return gaUnicode.getUNICODE_CONNECTOR_PUNCTUATION_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_LETTER_FRAGMENT: // any character in the Unicode categories
	//// ―Uppercase letter (Lu)
	//// ―Lowercase letter (Ll)
	//// ―Titlecase letter (Lt)
	//// ―Modifier letter (Lm)
	//// ―Other letter (Lo)
	//// ―Letter number (Nl)
	//	'\\u0041'..'\\u005A' | '\\u0061'..'\\u007A' | '\\u00AA' | '\\u00B5' | '\\u00BA' | '\\u00C0'..'\\u00D6' | '\\u00D8'..'\\u00F6' |
	//	'\\u00F8'..'\\u02C1' | '\\u02C6'..'\\u02D1' | '\\u02E0'..'\\u02E4' | '\\u02EC' | '\\u02EE' | '\\u0370'..'\\u0374' |
	//	'\\u0376'..'\\u0377' | '\\u037A'..'\\u037D' | '\\u037F' | '\\u0386' | '\\u0388'..'\\u038A' | '\\u038C' | '\\u038E'..'\\u03A1' |
	//	'\\u03A3'..'\\u03F5' | '\\u03F7'..'\\u0481' | '\\u048A'..'\\u052F' | '\\u0531'..'\\u0556' | '\\u0559' | '\\u0561'..'\\u0587' |
	//	'\\u05D0'..'\\u05EA' | '\\u05F0'..'\\u05F2' | '\\u0620'..'\\u064A' | '\\u066E'..'\\u066F' | '\\u0671'..'\\u06D3' | '\\u06D5' |
	//	'\\u06E5'..'\\u06E6' | '\\u06EE'..'\\u06EF' | '\\u06FA'..'\\u06FC' | '\\u06FF' | '\\u0710' | '\\u0712'..'\\u072F' |
	//	'\\u074D'..'\\u07A5' | '\\u07B1' | '\\u07CA'..'\\u07EA' | '\\u07F4'..'\\u07F5' | '\\u07FA' | '\\u0800'..'\\u0815' | '\\u081A' |
	//	'\\u0824' | '\\u0828' | '\\u0840'..'\\u0858' | '\\u08A0'..'\\u08B4' | '\\u0904'..'\\u0939' | '\\u093D' | '\\u0950' |
	//	'\\u0958'..'\\u0961' | '\\u0971'..'\\u0980' | '\\u0985'..'\\u098C' | '\\u098F'..'\\u0990' | '\\u0993'..'\\u09A8' |
	//	'\\u09AA'..'\\u09B0' | '\\u09B2' | '\\u09B6'..'\\u09B9' | '\\u09BD' | '\\u09CE' | '\\u09DC'..'\\u09DD' | '\\u09DF'..'\\u09E1' |
	//	'\\u09F0'..'\\u09F1' | '\\u0A05'..'\\u0A0A' | '\\u0A0F'..'\\u0A10' | '\\u0A13'..'\\u0A28' | '\\u0A2A'..'\\u0A30' |
	//	'\\u0A32'..'\\u0A33' | '\\u0A35'..'\\u0A36' | '\\u0A38'..'\\u0A39' | '\\u0A59'..'\\u0A5C' | '\\u0A5E' | '\\u0A72'..'\\u0A74' |
	//	'\\u0A85'..'\\u0A8D' | '\\u0A8F'..'\\u0A91' | '\\u0A93'..'\\u0AA8' | '\\u0AAA'..'\\u0AB0' | '\\u0AB2'..'\\u0AB3' |
	//	'\\u0AB5'..'\\u0AB9' | '\\u0ABD' | '\\u0AD0' | '\\u0AE0'..'\\u0AE1' | '\\u0AF9' | '\\u0B05'..'\\u0B0C' | '\\u0B0F'..'\\u0B10' |
	//	'\\u0B13'..'\\u0B28' | '\\u0B2A'..'\\u0B30' | '\\u0B32'..'\\u0B33' | '\\u0B35'..'\\u0B39' | '\\u0B3D' | '\\u0B5C'..'\\u0B5D' |
	//	'\\u0B5F'..'\\u0B61' | '\\u0B71' | '\\u0B83' | '\\u0B85'..'\\u0B8A' | '\\u0B8E'..'\\u0B90' | '\\u0B92'..'\\u0B95' |
	//	'\\u0B99'..'\\u0B9A' | '\\u0B9C' | '\\u0B9E'..'\\u0B9F' | '\\u0BA3'..'\\u0BA4' | '\\u0BA8'..'\\u0BAA' | '\\u0BAE'..'\\u0BB9' |
	//	'\\u0BD0' | '\\u0C05'..'\\u0C0C' | '\\u0C0E'..'\\u0C10' | '\\u0C12'..'\\u0C28' | '\\u0C2A'..'\\u0C39' | '\\u0C3D' |
	//	'\\u0C58'..'\\u0C5A' | '\\u0C60'..'\\u0C61' | '\\u0C85'..'\\u0C8C' | '\\u0C8E'..'\\u0C90' | '\\u0C92'..'\\u0CA8' |
	//	'\\u0CAA'..'\\u0CB3' | '\\u0CB5'..'\\u0CB9' | '\\u0CBD' | '\\u0CDE' | '\\u0CE0'..'\\u0CE1' | '\\u0CF1'..'\\u0CF2' |
	//	'\\u0D05'..'\\u0D0C' | '\\u0D0E'..'\\u0D10' | '\\u0D12'..'\\u0D3A' | '\\u0D3D' | '\\u0D4E' | '\\u0D5F'..'\\u0D61' |
	//	'\\u0D7A'..'\\u0D7F' | '\\u0D85'..'\\u0D96' | '\\u0D9A'..'\\u0DB1' | '\\u0DB3'..'\\u0DBB' | '\\u0DBD' | '\\u0DC0'..'\\u0DC6' |
	//	'\\u0E01'..'\\u0E30' | '\\u0E32'..'\\u0E33' | '\\u0E40'..'\\u0E46' | '\\u0E81'..'\\u0E82' | '\\u0E84' | '\\u0E87'..'\\u0E88' |
	//	'\\u0E8A' | '\\u0E8D' | '\\u0E94'..'\\u0E97' | '\\u0E99'..'\\u0E9F' | '\\u0EA1'..'\\u0EA3' | '\\u0EA5' | '\\u0EA7' |
	//	'\\u0EAA'..'\\u0EAB' | '\\u0EAD'..'\\u0EB0' | '\\u0EB2'..'\\u0EB3' | '\\u0EBD' | '\\u0EC0'..'\\u0EC4' | '\\u0EC6' |
	//	'\\u0EDC'..'\\u0EDF' | '\\u0F00' | '\\u0F40'..'\\u0F47' | '\\u0F49'..'\\u0F6C' | '\\u0F88'..'\\u0F8C' | '\\u1000'..'\\u102A' |
	//	'\\u103F' | '\\u1050'..'\\u1055' | '\\u105A'..'\\u105D' | '\\u1061' | '\\u1065'..'\\u1066' | '\\u106E'..'\\u1070' |
	//	'\\u1075'..'\\u1081' | '\\u108E' | '\\u10A0'..'\\u10C5' | '\\u10C7' | '\\u10CD' | '\\u10D0'..'\\u10FA' | '\\u10FC'..'\\u1248' |
	//	'\\u124A'..'\\u124D' | '\\u1250'..'\\u1256' | '\\u1258' | '\\u125A'..'\\u125D' | '\\u1260'..'\\u1288' | '\\u128A'..'\\u128D' |
	//	'\\u1290'..'\\u12B0' | '\\u12B2'..'\\u12B5' | '\\u12B8'..'\\u12BE' | '\\u12C0' | '\\u12C2'..'\\u12C5' | '\\u12C8'..'\\u12D6' |
	//	'\\u12D8'..'\\u1310' | '\\u1312'..'\\u1315' | '\\u1318'..'\\u135A' | '\\u1380'..'\\u138F' | '\\u13A0'..'\\u13F5' |
	//	'\\u13F8'..'\\u13FD' | '\\u1401'..'\\u166C' | '\\u166F'..'\\u167F' | '\\u1681'..'\\u169A' | '\\u16A0'..'\\u16EA' |
	//	'\\u16EE'..'\\u16F8' | '\\u1700'..'\\u170C' | '\\u170E'..'\\u1711' | '\\u1720'..'\\u1731' | '\\u1740'..'\\u1751' |
	//	'\\u1760'..'\\u176C' | '\\u176E'..'\\u1770' | '\\u1780'..'\\u17B3' | '\\u17D7' | '\\u17DC' | '\\u1820'..'\\u1877' |
	//	'\\u1880'..'\\u18A8' | '\\u18AA' | '\\u18B0'..'\\u18F5' | '\\u1900'..'\\u191E' | '\\u1950'..'\\u196D' | '\\u1970'..'\\u1974' |
	//	'\\u1980'..'\\u19AB' | '\\u19B0'..'\\u19C9' | '\\u1A00'..'\\u1A16' | '\\u1A20'..'\\u1A54' | '\\u1AA7' | '\\u1B05'..'\\u1B33' |
	//	'\\u1B45'..'\\u1B4B' | '\\u1B83'..'\\u1BA0' | '\\u1BAE'..'\\u1BAF' | '\\u1BBA'..'\\u1BE5' | '\\u1C00'..'\\u1C23' |
	//	'\\u1C4D'..'\\u1C4F' | '\\u1C5A'..'\\u1C7D' | '\\u1CE9'..'\\u1CEC' | '\\u1CEE'..'\\u1CF1' | '\\u1CF5'..'\\u1CF6' |
	//	'\\u1D00'..'\\u1DBF' | '\\u1E00'..'\\u1F15' | '\\u1F18'..'\\u1F1D' | '\\u1F20'..'\\u1F45' | '\\u1F48'..'\\u1F4D' |
	//	'\\u1F50'..'\\u1F57' | '\\u1F59' | '\\u1F5B' | '\\u1F5D' | '\\u1F5F'..'\\u1F7D' | '\\u1F80'..'\\u1FB4' | '\\u1FB6'..'\\u1FBC' |
	//	'\\u1FBE' | '\\u1FC2'..'\\u1FC4' | '\\u1FC6'..'\\u1FCC' | '\\u1FD0'..'\\u1FD3' | '\\u1FD6'..'\\u1FDB' | '\\u1FE0'..'\\u1FEC' |
	//	'\\u1FF2'..'\\u1FF4' | '\\u1FF6'..'\\u1FFC' | '\\u2071' | '\\u207F' | '\\u2090'..'\\u209C' | '\\u2102' | '\\u2107' |
	//	'\\u210A'..'\\u2113' | '\\u2115' | '\\u2119'..'\\u211D' | '\\u2124' | '\\u2126' | '\\u2128' | '\\u212A'..'\\u212D' |
	//	'\\u212F'..'\\u2139' | '\\u213C'..'\\u213F' | '\\u2145'..'\\u2149' | '\\u214E' | '\\u2160'..'\\u2188' | '\\u2C00'..'\\u2C2E' |
	//	'\\u2C30'..'\\u2C5E' | '\\u2C60'..'\\u2CE4' | '\\u2CEB'..'\\u2CEE' | '\\u2CF2'..'\\u2CF3' | '\\u2D00'..'\\u2D25' | '\\u2D27' |
	//	'\\u2D2D' | '\\u2D30'..'\\u2D67' | '\\u2D6F' | '\\u2D80'..'\\u2D96' | '\\u2DA0'..'\\u2DA6' | '\\u2DA8'..'\\u2DAE' |
	//	'\\u2DB0'..'\\u2DB6' | '\\u2DB8'..'\\u2DBE' | '\\u2DC0'..'\\u2DC6' | '\\u2DC8'..'\\u2DCE' | '\\u2DD0'..'\\u2DD6' |
	//	'\\u2DD8'..'\\u2DDE' | '\\u2E2F' | '\\u3005'..'\\u3007' | '\\u3021'..'\\u3029' | '\\u3031'..'\\u3035' | '\\u3038'..'\\u303C' |
	//	'\\u3041'..'\\u3096' | '\\u309D'..'\\u309F' | '\\u30A1'..'\\u30FA' | '\\u30FC'..'\\u30FF' | '\\u3105'..'\\u312D' |
	//	'\\u3131'..'\\u318E' | '\\u31A0'..'\\u31BA' | '\\u31F0'..'\\u31FF' | '\\u3400'..'\\u4DB5' | '\\u4E00'..'\\u9FD5' |
	//	'\\uA000'..'\\uA48C' | '\\uA4D0'..'\\uA4FD' | '\\uA500'..'\\uA60C' | '\\uA610'..'\\uA61F' | '\\uA62A'..'\\uA62B' |
	//	'\\uA640'..'\\uA66E' | '\\uA67F'..'\\uA69D' | '\\uA6A0'..'\\uA6EF' | '\\uA717'..'\\uA71F' | '\\uA722'..'\\uA788' |
	//	'\\uA78B'..'\\uA7AD' | '\\uA7B0'..'\\uA7B7' | '\\uA7F7'..'\\uA801' | '\\uA803'..'\\uA805' | '\\uA807'..'\\uA80A' |
	//	'\\uA80C'..'\\uA822' | '\\uA840'..'\\uA873' | '\\uA882'..'\\uA8B3' | '\\uA8F2'..'\\uA8F7' | '\\uA8FB' | '\\uA8FD' |
	//	'\\uA90A'..'\\uA925' | '\\uA930'..'\\uA946' | '\\uA960'..'\\uA97C' | '\\uA984'..'\\uA9B2' | '\\uA9CF' | '\\uA9E0'..'\\uA9E4' |
	//	'\\uA9E6'..'\\uA9EF' | '\\uA9FA'..'\\uA9FE' | '\\uAA00'..'\\uAA28' | '\\uAA40'..'\\uAA42' | '\\uAA44'..'\\uAA4B' |
	//	'\\uAA60'..'\\uAA76' | '\\uAA7A' | '\\uAA7E'..'\\uAAAF' | '\\uAAB1' | '\\uAAB5'..'\\uAAB6' | '\\uAAB9'..'\\uAABD' | '\\uAAC0' |
	//	'\\uAAC2' | '\\uAADB'..'\\uAADD' | '\\uAAE0'..'\\uAAEA' | '\\uAAF2'..'\\uAAF4' | '\\uAB01'..'\\uAB06' | '\\uAB09'..'\\uAB0E' |
	//	'\\uAB11'..'\\uAB16' | '\\uAB20'..'\\uAB26' | '\\uAB28'..'\\uAB2E' | '\\uAB30'..'\\uAB5A' | '\\uAB5C'..'\\uAB65' |
	//	'\\uAB70'..'\\uABE2' | '\\uAC00'..'\\uD7A3' | '\\uD7B0'..'\\uD7C6' | '\\uD7CB'..'\\uD7FB' | '\\uF900'..'\\uFA6D' |
	//	'\\uFA70'..'\\uFAD9' | '\\uFB00'..'\\uFB06' | '\\uFB13'..'\\uFB17' | '\\uFB1D' | '\\uFB1F'..'\\uFB28' | '\\uFB2A'..'\\uFB36' |
	//	'\\uFB38'..'\\uFB3C' | '\\uFB3E' | '\\uFB40'..'\\uFB41' | '\\uFB43'..'\\uFB44' | '\\uFB46'..'\\uFBB1' | '\\uFBD3'..'\\uFD3D' |
	//	'\\uFD50'..'\\uFD8F' | '\\uFD92'..'\\uFDC7' | '\\uFDF0'..'\\uFDFB' | '\\uFE70'..'\\uFE74' | '\\uFE76'..'\\uFEFC' |
	//	'\\uFF21'..'\\uFF3A' | '\\uFF41'..'\\uFF5A' | '\\uFF66'..'\\uFFBE' | '\\uFFC2'..'\\uFFC7' | '\\uFFCA'..'\\uFFCF' |
	//	'\\uFFD2'..'\\uFFD7' | '\\uFFDA'..'\\uFFDC';
	public TerminalRule getUNICODE_LETTER_FRAGMENTRule() {
		return gaUnicode.getUNICODE_LETTER_FRAGMENTRule();
	}
	
	//terminal fragment UNICODE_SPACE_SEPARATOR_FRAGMENT: // any character in the Unicode categories
	//// ―space separator (Zs)
	//	'\\u0020' | '\\u00A0' | '\\u1680' | '\\u2000'..'\\u200A' | '\\u202F' | '\\u205F' | '\\u3000';
	public TerminalRule getUNICODE_SPACE_SEPARATOR_FRAGMENTRule() {
		return gaUnicode.getUNICODE_SPACE_SEPARATOR_FRAGMENTRule();
	}
	
	//terminal fragment ANY_OTHER:
	//	.;
	public TerminalRule getANY_OTHERRule() {
		return gaUnicode.getANY_OTHERRule();
	}
}
