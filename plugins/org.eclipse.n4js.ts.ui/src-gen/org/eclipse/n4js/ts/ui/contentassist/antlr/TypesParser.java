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
package org.eclipse.n4js.ts.ui.contentassist.antlr;

import com.google.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.n4js.ts.services.TypesGrammarAccess;
import org.eclipse.n4js.ts.ui.contentassist.antlr.internal.InternalTypesParser;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.AbstractContentAssistParser;

public class TypesParser extends AbstractContentAssistParser {

	@Inject
	private TypesGrammarAccess grammarAccess;

	private Map<AbstractElement, String> nameMappings;

	@Override
	protected InternalTypesParser createParser() {
		InternalTypesParser result = new InternalTypesParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}

	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getTAnnotationArgumentAccess().getAlternatives(), "rule__TAnnotationArgument__Alternatives");
					put(grammarAccess.getTypeAccess().getAlternatives(), "rule__Type__Alternatives");
					put(grammarAccess.getTypeReferenceNameAccess().getAlternatives(), "rule__TypeReferenceName__Alternatives");
					put(grammarAccess.getTypesIdentifierAccess().getAlternatives(), "rule__TypesIdentifier__Alternatives");
					put(grammarAccess.getBindingTypesIdentifierAccess().getAlternatives(), "rule__BindingTypesIdentifier__Alternatives");
					put(grammarAccess.getVoidOrBindingIdentifierAccess().getAlternatives(), "rule__VoidOrBindingIdentifier__Alternatives");
					put(grammarAccess.getTypesSpecificKeywordsAccess().getAlternatives(), "rule__TypesSpecificKeywords__Alternatives");
					put(grammarAccess.getTypesComputedPropertyNameAccess().getAlternatives_1(), "rule__TypesComputedPropertyName__Alternatives_1");
					put(grammarAccess.getTMemberAccess().getAlternatives(), "rule__TMember__Alternatives");
					put(grammarAccess.getTMethodAccess().getAlternatives_0_0_1(), "rule__TMethod__Alternatives_0_0_1");
					put(grammarAccess.getTMethodAccess().getAlternatives_0_0_3(), "rule__TMethod__Alternatives_0_0_3");
					put(grammarAccess.getTFieldAccess().getAlternatives_1(), "rule__TField__Alternatives_1");
					put(grammarAccess.getTFieldAccess().getAlternatives_2(), "rule__TField__Alternatives_2");
					put(grammarAccess.getTGetterAccess().getAlternatives_0_0_1(), "rule__TGetter__Alternatives_0_0_1");
					put(grammarAccess.getTGetterAccess().getAlternatives_0_0_3(), "rule__TGetter__Alternatives_0_0_3");
					put(grammarAccess.getTSetterAccess().getAlternatives_0_0_1(), "rule__TSetter__Alternatives_0_0_1");
					put(grammarAccess.getTSetterAccess().getAlternatives_0_0_3(), "rule__TSetter__Alternatives_0_0_3");
					put(grammarAccess.getPrimaryTypeExpressionAccess().getAlternatives(), "rule__PrimaryTypeExpression__Alternatives");
					put(grammarAccess.getTypeRefWithoutModifiersAccess().getAlternatives(), "rule__TypeRefWithoutModifiers__Alternatives");
					put(grammarAccess.getTypeRefWithoutModifiersAccess().getAlternatives_0_0(), "rule__TypeRefWithoutModifiers__Alternatives_0_0");
					put(grammarAccess.getTypeRefFunctionTypeExpressionAccess().getAlternatives(), "rule__TypeRefFunctionTypeExpression__Alternatives");
					put(grammarAccess.getTypeArgInTypeTypeRefAccess().getAlternatives(), "rule__TypeArgInTypeTypeRef__Alternatives");
					put(grammarAccess.getThisTypeRefAccess().getAlternatives(), "rule__ThisTypeRef__Alternatives");
					put(grammarAccess.getTAnonymousFormalParameterAccess().getAlternatives_1(), "rule__TAnonymousFormalParameter__Alternatives_1");
					put(grammarAccess.getParameterizedTypeRefAccess().getAlternatives(), "rule__ParameterizedTypeRef__Alternatives");
					put(grammarAccess.getTStructMemberListAccess().getAlternatives_1_1(), "rule__TStructMemberList__Alternatives_1_1");
					put(grammarAccess.getTStructMemberAccess().getAlternatives(), "rule__TStructMember__Alternatives");
					put(grammarAccess.getTypingStrategyUseSiteOperatorAccess().getAlternatives_1(), "rule__TypingStrategyUseSiteOperator__Alternatives_1");
					put(grammarAccess.getTypeTypeRefAccess().getAlternatives_1(), "rule__TypeTypeRef__Alternatives_1");
					put(grammarAccess.getTypeArgumentAccess().getAlternatives(), "rule__TypeArgument__Alternatives");
					put(grammarAccess.getWildcardAccess().getAlternatives_1(), "rule__Wildcard__Alternatives_1");
					put(grammarAccess.getWildcardNewNotationAccess().getAlternatives(), "rule__WildcardNewNotation__Alternatives");
					put(grammarAccess.getTypeExpressionsTypeVariableAccess().getAlternatives_0(), "superTypeVariable__Alternatives_0");
					put(grammarAccess.getBindingIdentifierAccess().getAlternatives(), "rule__BindingIdentifier__Alternatives");
					put(grammarAccess.getIdentifierNameAccess().getAlternatives(), "rule__IdentifierName__Alternatives");
					put(grammarAccess.getReservedWordAccess().getAlternatives(), "rule__ReservedWord__Alternatives");
					put(grammarAccess.getN4KeywordAccess().getAlternatives(), "rule__N4Keyword__Alternatives");
					put(grammarAccess.getTypeAccessModifierAccess().getAlternatives(), "rule__TypeAccessModifier__Alternatives");
					put(grammarAccess.getMemberAccessModifierAccess().getAlternatives(), "rule__MemberAccessModifier__Alternatives");
					put(grammarAccess.getTAnnotationAccess().getGroup(), "rule__TAnnotation__Group__0");
					put(grammarAccess.getTAnnotationAccess().getGroup_0(), "rule__TAnnotation__Group_0__0");
					put(grammarAccess.getTAnnotationAccess().getGroup_0_0(), "rule__TAnnotation__Group_0_0__0");
					put(grammarAccess.getTAnnotationAccess().getGroup_1(), "rule__TAnnotation__Group_1__0");
					put(grammarAccess.getTAnnotationAccess().getGroup_1_1(), "rule__TAnnotation__Group_1_1__0");
					put(grammarAccess.getTAnnotationAccess().getGroup_1_1_1(), "rule__TAnnotation__Group_1_1_1__0");
					put(grammarAccess.getTypeRefAccess().getGroup(), "rule__TypeRef__Group__0");
					put(grammarAccess.getPrimitiveTypeAccess().getGroup(), "rule__PrimitiveType__Group__0");
					put(grammarAccess.getPrimitiveTypeAccess().getGroup_2(), "rule__PrimitiveType__Group_2__0");
					put(grammarAccess.getPrimitiveTypeAccess().getGroup_3(), "rule__PrimitiveType__Group_3__0");
					put(grammarAccess.getPrimitiveTypeAccess().getGroup_5(), "rule__PrimitiveType__Group_5__0");
					put(grammarAccess.getPrimitiveTypeAccess().getGroup_6(), "rule__PrimitiveType__Group_6__0");
					put(grammarAccess.getTypeReferenceNameAccess().getGroup_5(), "rule__TypeReferenceName__Group_5__0");
					put(grammarAccess.getTypeReferenceNameAccess().getGroup_5_1(), "rule__TypeReferenceName__Group_5_1__0");
					put(grammarAccess.getAnyTypeAccess().getGroup(), "rule__AnyType__Group__0");
					put(grammarAccess.getVoidTypeAccess().getGroup(), "rule__VoidType__Group__0");
					put(grammarAccess.getUndefinedTypeAccess().getGroup(), "rule__UndefinedType__Group__0");
					put(grammarAccess.getNullTypeAccess().getGroup(), "rule__NullType__Group__0");
					put(grammarAccess.getTypesComputedPropertyNameAccess().getGroup(), "rule__TypesComputedPropertyName__Group__0");
					put(grammarAccess.getTypesSymbolLiteralComputedNameAccess().getGroup(), "rule__TypesSymbolLiteralComputedName__Group__0");
					put(grammarAccess.getTObjectPrototypeAccess().getGroup(), "rule__TObjectPrototype__Group__0");
					put(grammarAccess.getTObjectPrototypeAccess().getGroup_6(), "rule__TObjectPrototype__Group_6__0");
					put(grammarAccess.getTObjectPrototypeAccess().getGroup_7(), "rule__TObjectPrototype__Group_7__0");
					put(grammarAccess.getTObjectPrototypeAccess().getGroup_11(), "rule__TObjectPrototype__Group_11__0");
					put(grammarAccess.getVirtualBaseTypeAccess().getGroup(), "rule__VirtualBaseType__Group__0");
					put(grammarAccess.getVirtualBaseTypeAccess().getGroup_3(), "rule__VirtualBaseType__Group_3__0");
					put(grammarAccess.getTClassAccess().getGroup(), "rule__TClass__Group__0");
					put(grammarAccess.getTClassAccess().getGroup_6(), "rule__TClass__Group_6__0");
					put(grammarAccess.getTClassAccess().getGroup_7(), "rule__TClass__Group_7__0");
					put(grammarAccess.getTClassAccess().getGroup_7_2(), "rule__TClass__Group_7_2__0");
					put(grammarAccess.getTClassAccess().getGroup_11(), "rule__TClass__Group_11__0");
					put(grammarAccess.getTInterfaceAccess().getGroup(), "rule__TInterface__Group__0");
					put(grammarAccess.getTInterfaceAccess().getGroup_4(), "rule__TInterface__Group_4__0");
					put(grammarAccess.getTInterfaceAccess().getGroup_4_2(), "rule__TInterface__Group_4_2__0");
					put(grammarAccess.getTypeVariableAccess().getGroup(), "rule__TypeVariable__Group__0");
					put(grammarAccess.getTypeVariableAccess().getGroup_1(), "rule__TypeVariable__Group_1__0");
					put(grammarAccess.getTClassOrInterfaceHeaderAccess().getGroup(), "rule__TClassOrInterfaceHeader__Group__0");
					put(grammarAccess.getTClassOrInterfaceHeaderAccess().getGroup_2(), "rule__TClassOrInterfaceHeader__Group_2__0");
					put(grammarAccess.getTClassOrInterfaceHeaderAccess().getGroup_2_2(), "rule__TClassOrInterfaceHeader__Group_2_2__0");
					put(grammarAccess.getCallableCtorAccess().getGroup(), "rule__CallableCtor__Group__0");
					put(grammarAccess.getTFormalParametersAccess().getGroup(), "rule__TFormalParameters__Group__0");
					put(grammarAccess.getTFormalParametersAccess().getGroup_1(), "rule__TFormalParameters__Group_1__0");
					put(grammarAccess.getTFormalParametersAccess().getGroup_1_1(), "rule__TFormalParameters__Group_1_1__0");
					put(grammarAccess.getTMethodAccess().getGroup(), "rule__TMethod__Group__0");
					put(grammarAccess.getTMethodAccess().getGroup_0(), "rule__TMethod__Group_0__0");
					put(grammarAccess.getTMethodAccess().getGroup_0_0(), "rule__TMethod__Group_0_0__0");
					put(grammarAccess.getTFieldAccess().getGroup(), "rule__TField__Group__0");
					put(grammarAccess.getTGetterAccess().getGroup(), "rule__TGetter__Group__0");
					put(grammarAccess.getTGetterAccess().getGroup_0(), "rule__TGetter__Group_0__0");
					put(grammarAccess.getTGetterAccess().getGroup_0_0(), "rule__TGetter__Group_0_0__0");
					put(grammarAccess.getTSetterAccess().getGroup(), "rule__TSetter__Group__0");
					put(grammarAccess.getTSetterAccess().getGroup_0(), "rule__TSetter__Group_0__0");
					put(grammarAccess.getTSetterAccess().getGroup_0_0(), "rule__TSetter__Group_0_0__0");
					put(grammarAccess.getTFunctionAccess().getGroup(), "rule__TFunction__Group__0");
					put(grammarAccess.getTEnumAccess().getGroup(), "rule__TEnum__Group__0");
					put(grammarAccess.getTEnumAccess().getGroup_6(), "rule__TEnum__Group_6__0");
					put(grammarAccess.getIntersectionTypeExpressionAccess().getGroup(), "rule__IntersectionTypeExpression__Group__0");
					put(grammarAccess.getIntersectionTypeExpressionAccess().getGroup_1(), "rule__IntersectionTypeExpression__Group_1__0");
					put(grammarAccess.getIntersectionTypeExpressionAccess().getGroup_1_1(), "rule__IntersectionTypeExpression__Group_1_1__0");
					put(grammarAccess.getPrimaryTypeExpressionAccess().getGroup_3(), "rule__PrimaryTypeExpression__Group_3__0");
					put(grammarAccess.getTypeRefWithModifiersAccess().getGroup(), "rule__TypeRefWithModifiers__Group__0");
					put(grammarAccess.getTypeRefWithoutModifiersAccess().getGroup_0(), "rule__TypeRefWithoutModifiers__Group_0__0");
					put(grammarAccess.getThisTypeRefNominalAccess().getGroup(), "rule__ThisTypeRefNominal__Group__0");
					put(grammarAccess.getThisTypeRefStructuralAccess().getGroup(), "rule__ThisTypeRefStructural__Group__0");
					put(grammarAccess.getThisTypeRefStructuralAccess().getGroup_2(), "rule__ThisTypeRefStructural__Group_2__0");
					put(grammarAccess.getFunctionTypeExpressionOLDAccess().getGroup(), "rule__FunctionTypeExpressionOLD__Group__0");
					put(grammarAccess.getFunctionTypeExpressionOLDAccess().getGroup_2(), "rule__FunctionTypeExpressionOLD__Group_2__0");
					put(grammarAccess.getFunctionTypeExpressionOLDAccess().getGroup_4(), "rule__FunctionTypeExpressionOLD__Group_4__0");
					put(grammarAccess.getFunctionTypeExpressionOLDAccess().getGroup_4_2(), "rule__FunctionTypeExpressionOLD__Group_4_2__0");
					put(grammarAccess.getArrowFunctionTypeExpressionAccess().getGroup(), "rule__ArrowFunctionTypeExpression__Group__0");
					put(grammarAccess.getArrowFunctionTypeExpressionAccess().getGroup_0(), "rule__ArrowFunctionTypeExpression__Group_0__0");
					put(grammarAccess.getArrowFunctionTypeExpressionAccess().getGroup_0_0(), "rule__ArrowFunctionTypeExpression__Group_0_0__0");
					put(grammarAccess.getTAnonymousFormalParameterListAccess().getGroup(), "rule__TAnonymousFormalParameterList__Group__0");
					put(grammarAccess.getTAnonymousFormalParameterListAccess().getGroup_1(), "rule__TAnonymousFormalParameterList__Group_1__0");
					put(grammarAccess.getTAnonymousFormalParameterAccess().getGroup(), "rule__TAnonymousFormalParameter__Group__0");
					put(grammarAccess.getTAnonymousFormalParameterAccess().getGroup_1_0(), "rule__TAnonymousFormalParameter__Group_1_0__0");
					put(grammarAccess.getTAnonymousFormalParameterAccess().getGroup_1_0_0(), "rule__TAnonymousFormalParameter__Group_1_0_0__0");
					put(grammarAccess.getTFormalParameterAccess().getGroup(), "rule__TFormalParameter__Group__0");
					put(grammarAccess.getDefaultFormalParameterAccess().getGroup(), "rule__DefaultFormalParameter__Group__0");
					put(grammarAccess.getUnionTypeExpressionOLDAccess().getGroup(), "rule__UnionTypeExpressionOLD__Group__0");
					put(grammarAccess.getUnionTypeExpressionOLDAccess().getGroup_4(), "rule__UnionTypeExpressionOLD__Group_4__0");
					put(grammarAccess.getIntersectionTypeExpressionOLDAccess().getGroup(), "rule__IntersectionTypeExpressionOLD__Group__0");
					put(grammarAccess.getIntersectionTypeExpressionOLDAccess().getGroup_4(), "rule__IntersectionTypeExpressionOLD__Group_4__0");
					put(grammarAccess.getArrayTypeRefAccess().getGroup(), "rule__ArrayTypeRef__Group__0");
					put(grammarAccess.getParameterizedTypeRefStructuralAccess().getGroup(), "rule__ParameterizedTypeRefStructural__Group__0");
					put(grammarAccess.getParameterizedTypeRefStructuralAccess().getGroup_2(), "rule__ParameterizedTypeRefStructural__Group_2__0");
					put(grammarAccess.getTypeAndTypeArgumentsAccess().getGroup(), "rule__TypeAndTypeArguments__Group__0");
					put(grammarAccess.getTypeArgumentsAccess().getGroup(), "rule__TypeArguments__Group__0");
					put(grammarAccess.getTypeArgumentsAccess().getGroup_2(), "rule__TypeArguments__Group_2__0");
					put(grammarAccess.getTStructMemberListAccess().getGroup(), "rule__TStructMemberList__Group__0");
					put(grammarAccess.getTStructMemberListAccess().getGroup_1(), "rule__TStructMemberList__Group_1__0");
					put(grammarAccess.getTStructMethodAccess().getGroup(), "rule__TStructMethod__Group__0");
					put(grammarAccess.getTStructMethodAccess().getGroup_0(), "rule__TStructMethod__Group_0__0");
					put(grammarAccess.getTStructMethodAccess().getGroup_0_0(), "rule__TStructMethod__Group_0_0__0");
					put(grammarAccess.getTypeVariablesAccess().getGroup(), "rule__TypeVariables__Group__0");
					put(grammarAccess.getTypeVariablesAccess().getGroup_2(), "rule__TypeVariables__Group_2__0");
					put(grammarAccess.getColonSepDeclaredTypeRefAccess().getGroup(), "rule__ColonSepDeclaredTypeRef__Group__0");
					put(grammarAccess.getColonSepTypeRefAccess().getGroup(), "rule__ColonSepTypeRef__Group__0");
					put(grammarAccess.getColonSepReturnTypeRefAccess().getGroup(), "rule__ColonSepReturnTypeRef__Group__0");
					put(grammarAccess.getTStructFieldAccess().getGroup(), "rule__TStructField__Group__0");
					put(grammarAccess.getTStructGetterAccess().getGroup(), "rule__TStructGetter__Group__0");
					put(grammarAccess.getTStructGetterAccess().getGroup_0(), "rule__TStructGetter__Group_0__0");
					put(grammarAccess.getTStructGetterAccess().getGroup_0_0(), "rule__TStructGetter__Group_0_0__0");
					put(grammarAccess.getTStructSetterAccess().getGroup(), "rule__TStructSetter__Group__0");
					put(grammarAccess.getTStructSetterAccess().getGroup_0(), "rule__TStructSetter__Group_0__0");
					put(grammarAccess.getTStructSetterAccess().getGroup_0_0(), "rule__TStructSetter__Group_0_0__0");
					put(grammarAccess.getTypingStrategyUseSiteOperatorAccess().getGroup(), "rule__TypingStrategyUseSiteOperator__Group__0");
					put(grammarAccess.getTypeTypeRefAccess().getGroup(), "rule__TypeTypeRef__Group__0");
					put(grammarAccess.getWildcardAccess().getGroup(), "rule__Wildcard__Group__0");
					put(grammarAccess.getWildcardAccess().getGroup_0(), "rule__Wildcard__Group_0__0");
					put(grammarAccess.getWildcardAccess().getGroup_0_0(), "rule__Wildcard__Group_0_0__0");
					put(grammarAccess.getWildcardAccess().getGroup_1_0(), "rule__Wildcard__Group_1_0__0");
					put(grammarAccess.getWildcardAccess().getGroup_1_1(), "rule__Wildcard__Group_1_1__0");
					put(grammarAccess.getWildcardNewNotationAccess().getGroup_0(), "rule__WildcardNewNotation__Group_0__0");
					put(grammarAccess.getWildcardNewNotationAccess().getGroup_1(), "rule__WildcardNewNotation__Group_1__0");
					put(grammarAccess.getTypeExpressionsTypeVariableAccess().getGroup(), "superTypeVariable__Group__0");
					put(grammarAccess.getTypeExpressionsTypeVariableAccess().getGroup_2(), "superTypeVariable__Group_2__0");
					put(grammarAccess.getBindingIdentifierAccess().getGroup_1(), "rule__BindingIdentifier__Group_1__0");
					put(grammarAccess.getTypeDefsAccess().getTypesAssignment(), "rule__TypeDefs__TypesAssignment");
					put(grammarAccess.getTAnnotationAccess().getNameAssignment_0_0_1(), "rule__TAnnotation__NameAssignment_0_0_1");
					put(grammarAccess.getTAnnotationAccess().getArgsAssignment_1_1_0(), "rule__TAnnotation__ArgsAssignment_1_1_0");
					put(grammarAccess.getTAnnotationAccess().getArgsAssignment_1_1_1_1(), "rule__TAnnotation__ArgsAssignment_1_1_1_1");
					put(grammarAccess.getTAnnotationStringArgumentAccess().getValueAssignment(), "rule__TAnnotationStringArgument__ValueAssignment");
					put(grammarAccess.getTAnnotationTypeRefArgumentAccess().getTypeRefAssignment(), "rule__TAnnotationTypeRefArgument__TypeRefAssignment");
					put(grammarAccess.getTypeRefAccess().getFollowedByQuestionMarkAssignment_1(), "rule__TypeRef__FollowedByQuestionMarkAssignment_1");
					put(grammarAccess.getPrimitiveTypeAccess().getNameAssignment_1(), "rule__PrimitiveType__NameAssignment_1");
					put(grammarAccess.getPrimitiveTypeAccess().getTypeVarsAssignment_2_1(), "rule__PrimitiveType__TypeVarsAssignment_2_1");
					put(grammarAccess.getPrimitiveTypeAccess().getDeclaredElementTypeAssignment_3_1(), "rule__PrimitiveType__DeclaredElementTypeAssignment_3_1");
					put(grammarAccess.getPrimitiveTypeAccess().getAutoboxedTypeAssignment_5_1(), "rule__PrimitiveType__AutoboxedTypeAssignment_5_1");
					put(grammarAccess.getPrimitiveTypeAccess().getAssignmentCompatibleAssignment_6_1(), "rule__PrimitiveType__AssignmentCompatibleAssignment_6_1");
					put(grammarAccess.getAnyTypeAccess().getNameAssignment_1(), "rule__AnyType__NameAssignment_1");
					put(grammarAccess.getVoidTypeAccess().getNameAssignment_1(), "rule__VoidType__NameAssignment_1");
					put(grammarAccess.getUndefinedTypeAccess().getNameAssignment_1(), "rule__UndefinedType__NameAssignment_1");
					put(grammarAccess.getNullTypeAccess().getNameAssignment_1(), "rule__NullType__NameAssignment_1");
					put(grammarAccess.getTObjectPrototypeAccess().getDeclaredTypeAccessModifierAssignment_0(), "rule__TObjectPrototype__DeclaredTypeAccessModifierAssignment_0");
					put(grammarAccess.getTObjectPrototypeAccess().getDeclaredProvidedByRuntimeAssignment_1(), "rule__TObjectPrototype__DeclaredProvidedByRuntimeAssignment_1");
					put(grammarAccess.getTObjectPrototypeAccess().getDeclaredFinalAssignment_2(), "rule__TObjectPrototype__DeclaredFinalAssignment_2");
					put(grammarAccess.getTObjectPrototypeAccess().getNameAssignment_4(), "rule__TObjectPrototype__NameAssignment_4");
					put(grammarAccess.getTObjectPrototypeAccess().getSuperTypeAssignment_6_1(), "rule__TObjectPrototype__SuperTypeAssignment_6_1");
					put(grammarAccess.getTObjectPrototypeAccess().getDeclaredElementTypeAssignment_7_1(), "rule__TObjectPrototype__DeclaredElementTypeAssignment_7_1");
					put(grammarAccess.getTObjectPrototypeAccess().getAnnotationsAssignment_8(), "rule__TObjectPrototype__AnnotationsAssignment_8");
					put(grammarAccess.getTObjectPrototypeAccess().getOwnedMembersAssignment_10(), "rule__TObjectPrototype__OwnedMembersAssignment_10");
					put(grammarAccess.getTObjectPrototypeAccess().getCallableCtorAssignment_11_0(), "rule__TObjectPrototype__CallableCtorAssignment_11_0");
					put(grammarAccess.getTObjectPrototypeAccess().getOwnedMembersAssignment_11_1(), "rule__TObjectPrototype__OwnedMembersAssignment_11_1");
					put(grammarAccess.getVirtualBaseTypeAccess().getNameAssignment_2(), "rule__VirtualBaseType__NameAssignment_2");
					put(grammarAccess.getVirtualBaseTypeAccess().getDeclaredElementTypeAssignment_3_1(), "rule__VirtualBaseType__DeclaredElementTypeAssignment_3_1");
					put(grammarAccess.getVirtualBaseTypeAccess().getOwnedMembersAssignment_5(), "rule__VirtualBaseType__OwnedMembersAssignment_5");
					put(grammarAccess.getTClassAccess().getDeclaredTypeAccessModifierAssignment_0(), "rule__TClass__DeclaredTypeAccessModifierAssignment_0");
					put(grammarAccess.getTClassAccess().getDeclaredProvidedByRuntimeAssignment_1(), "rule__TClass__DeclaredProvidedByRuntimeAssignment_1");
					put(grammarAccess.getTClassAccess().getDeclaredAbstractAssignment_2(), "rule__TClass__DeclaredAbstractAssignment_2");
					put(grammarAccess.getTClassAccess().getDeclaredFinalAssignment_3(), "rule__TClass__DeclaredFinalAssignment_3");
					put(grammarAccess.getTClassAccess().getSuperClassRefAssignment_6_1(), "rule__TClass__SuperClassRefAssignment_6_1");
					put(grammarAccess.getTClassAccess().getImplementedInterfaceRefsAssignment_7_1(), "rule__TClass__ImplementedInterfaceRefsAssignment_7_1");
					put(grammarAccess.getTClassAccess().getImplementedInterfaceRefsAssignment_7_2_1(), "rule__TClass__ImplementedInterfaceRefsAssignment_7_2_1");
					put(grammarAccess.getTClassAccess().getAnnotationsAssignment_8(), "rule__TClass__AnnotationsAssignment_8");
					put(grammarAccess.getTClassAccess().getOwnedMembersAssignment_10(), "rule__TClass__OwnedMembersAssignment_10");
					put(grammarAccess.getTClassAccess().getCallableCtorAssignment_11_0(), "rule__TClass__CallableCtorAssignment_11_0");
					put(grammarAccess.getTClassAccess().getOwnedMembersAssignment_11_1(), "rule__TClass__OwnedMembersAssignment_11_1");
					put(grammarAccess.getTInterfaceAccess().getDeclaredTypeAccessModifierAssignment_0(), "rule__TInterface__DeclaredTypeAccessModifierAssignment_0");
					put(grammarAccess.getTInterfaceAccess().getDeclaredProvidedByRuntimeAssignment_1(), "rule__TInterface__DeclaredProvidedByRuntimeAssignment_1");
					put(grammarAccess.getTInterfaceAccess().getSuperInterfaceRefsAssignment_4_1(), "rule__TInterface__SuperInterfaceRefsAssignment_4_1");
					put(grammarAccess.getTInterfaceAccess().getSuperInterfaceRefsAssignment_4_2_1(), "rule__TInterface__SuperInterfaceRefsAssignment_4_2_1");
					put(grammarAccess.getTInterfaceAccess().getAnnotationsAssignment_5(), "rule__TInterface__AnnotationsAssignment_5");
					put(grammarAccess.getTInterfaceAccess().getOwnedMembersAssignment_7(), "rule__TInterface__OwnedMembersAssignment_7");
					put(grammarAccess.getTypeVariableAccess().getNameAssignment_0(), "rule__TypeVariable__NameAssignment_0");
					put(grammarAccess.getTypeVariableAccess().getDeclaredUpperBoundAssignment_1_1(), "rule__TypeVariable__DeclaredUpperBoundAssignment_1_1");
					put(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypingStrategyAssignment_0(), "rule__TClassOrInterfaceHeader__TypingStrategyAssignment_0");
					put(grammarAccess.getTClassOrInterfaceHeaderAccess().getNameAssignment_1(), "rule__TClassOrInterfaceHeader__NameAssignment_1");
					put(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypeVarsAssignment_2_1(), "rule__TClassOrInterfaceHeader__TypeVarsAssignment_2_1");
					put(grammarAccess.getTClassOrInterfaceHeaderAccess().getTypeVarsAssignment_2_2_1(), "rule__TClassOrInterfaceHeader__TypeVarsAssignment_2_2_1");
					put(grammarAccess.getTFormalParametersAccess().getFparsAssignment_1_0(), "rule__TFormalParameters__FparsAssignment_1_0");
					put(grammarAccess.getTFormalParametersAccess().getFparsAssignment_1_1_1(), "rule__TFormalParameters__FparsAssignment_1_1_1");
					put(grammarAccess.getTMethodAccess().getDeclaredMemberAccessModifierAssignment_0_0_0(), "rule__TMethod__DeclaredMemberAccessModifierAssignment_0_0_0");
					put(grammarAccess.getTMethodAccess().getDeclaredAbstractAssignment_0_0_1_0(), "rule__TMethod__DeclaredAbstractAssignment_0_0_1_0");
					put(grammarAccess.getTMethodAccess().getDeclaredStaticAssignment_0_0_1_1(), "rule__TMethod__DeclaredStaticAssignment_0_0_1_1");
					put(grammarAccess.getTMethodAccess().getNameAssignment_0_0_3_0(), "rule__TMethod__NameAssignment_0_0_3_0");
					put(grammarAccess.getTMethodAccess().getNameAssignment_0_0_3_1(), "rule__TMethod__NameAssignment_0_0_3_1");
					put(grammarAccess.getTFieldAccess().getDeclaredMemberAccessModifierAssignment_0(), "rule__TField__DeclaredMemberAccessModifierAssignment_0");
					put(grammarAccess.getTFieldAccess().getDeclaredStaticAssignment_1_0(), "rule__TField__DeclaredStaticAssignment_1_0");
					put(grammarAccess.getTFieldAccess().getConstAssignment_1_1(), "rule__TField__ConstAssignment_1_1");
					put(grammarAccess.getTFieldAccess().getDeclaredFinalAssignment_1_2(), "rule__TField__DeclaredFinalAssignment_1_2");
					put(grammarAccess.getTFieldAccess().getNameAssignment_2_0(), "rule__TField__NameAssignment_2_0");
					put(grammarAccess.getTFieldAccess().getNameAssignment_2_1(), "rule__TField__NameAssignment_2_1");
					put(grammarAccess.getTFieldAccess().getOptionalAssignment_3(), "rule__TField__OptionalAssignment_3");
					put(grammarAccess.getTGetterAccess().getDeclaredMemberAccessModifierAssignment_0_0_0(), "rule__TGetter__DeclaredMemberAccessModifierAssignment_0_0_0");
					put(grammarAccess.getTGetterAccess().getDeclaredAbstractAssignment_0_0_1_0(), "rule__TGetter__DeclaredAbstractAssignment_0_0_1_0");
					put(grammarAccess.getTGetterAccess().getDeclaredStaticAssignment_0_0_1_1(), "rule__TGetter__DeclaredStaticAssignment_0_0_1_1");
					put(grammarAccess.getTGetterAccess().getNameAssignment_0_0_3_0(), "rule__TGetter__NameAssignment_0_0_3_0");
					put(grammarAccess.getTGetterAccess().getNameAssignment_0_0_3_1(), "rule__TGetter__NameAssignment_0_0_3_1");
					put(grammarAccess.getTGetterAccess().getOptionalAssignment_1(), "rule__TGetter__OptionalAssignment_1");
					put(grammarAccess.getTSetterAccess().getDeclaredMemberAccessModifierAssignment_0_0_0(), "rule__TSetter__DeclaredMemberAccessModifierAssignment_0_0_0");
					put(grammarAccess.getTSetterAccess().getDeclaredAbstractAssignment_0_0_1_0(), "rule__TSetter__DeclaredAbstractAssignment_0_0_1_0");
					put(grammarAccess.getTSetterAccess().getDeclaredStaticAssignment_0_0_1_1(), "rule__TSetter__DeclaredStaticAssignment_0_0_1_1");
					put(grammarAccess.getTSetterAccess().getNameAssignment_0_0_3_0(), "rule__TSetter__NameAssignment_0_0_3_0");
					put(grammarAccess.getTSetterAccess().getNameAssignment_0_0_3_1(), "rule__TSetter__NameAssignment_0_0_3_1");
					put(grammarAccess.getTSetterAccess().getOptionalAssignment_1(), "rule__TSetter__OptionalAssignment_1");
					put(grammarAccess.getTSetterAccess().getFparAssignment_3(), "rule__TSetter__FparAssignment_3");
					put(grammarAccess.getTFunctionAccess().getDeclaredTypeAccessModifierAssignment_0(), "rule__TFunction__DeclaredTypeAccessModifierAssignment_0");
					put(grammarAccess.getTFunctionAccess().getDeclaredProvidedByRuntimeAssignment_1(), "rule__TFunction__DeclaredProvidedByRuntimeAssignment_1");
					put(grammarAccess.getTFunctionAccess().getNameAssignment_4(), "rule__TFunction__NameAssignment_4");
					put(grammarAccess.getTEnumAccess().getDeclaredTypeAccessModifierAssignment_0(), "rule__TEnum__DeclaredTypeAccessModifierAssignment_0");
					put(grammarAccess.getTEnumAccess().getDeclaredProvidedByRuntimeAssignment_1(), "rule__TEnum__DeclaredProvidedByRuntimeAssignment_1");
					put(grammarAccess.getTEnumAccess().getNameAssignment_3(), "rule__TEnum__NameAssignment_3");
					put(grammarAccess.getTEnumAccess().getLiteralsAssignment_5(), "rule__TEnum__LiteralsAssignment_5");
					put(grammarAccess.getTEnumAccess().getLiteralsAssignment_6_1(), "rule__TEnum__LiteralsAssignment_6_1");
					put(grammarAccess.getTEnumLiteralAccess().getNameAssignment(), "rule__TEnumLiteral__NameAssignment");
					put(grammarAccess.getIntersectionTypeExpressionAccess().getTypeRefsAssignment_1_1_1(), "rule__IntersectionTypeExpression__TypeRefsAssignment_1_1_1");
					put(grammarAccess.getTypeRefWithModifiersAccess().getFollowedByQuestionMarkAssignment_1(), "rule__TypeRefWithModifiers__FollowedByQuestionMarkAssignment_1");
					put(grammarAccess.getTypeRefWithoutModifiersAccess().getDynamicAssignment_0_1(), "rule__TypeRefWithoutModifiers__DynamicAssignment_0_1");
					put(grammarAccess.getThisTypeRefStructuralAccess().getDefinedTypingStrategyAssignment_0(), "rule__ThisTypeRefStructural__DefinedTypingStrategyAssignment_0");
					put(grammarAccess.getFunctionTypeExpressionOLDAccess().getDeclaredThisTypeAssignment_2_3(), "rule__FunctionTypeExpressionOLD__DeclaredThisTypeAssignment_2_3");
					put(grammarAccess.getFunctionTypeExpressionOLDAccess().getOwnedTypeVarsAssignment_4_1(), "rule__FunctionTypeExpressionOLD__OwnedTypeVarsAssignment_4_1");
					put(grammarAccess.getFunctionTypeExpressionOLDAccess().getOwnedTypeVarsAssignment_4_2_1(), "rule__FunctionTypeExpressionOLD__OwnedTypeVarsAssignment_4_2_1");
					put(grammarAccess.getArrowFunctionTypeExpressionAccess().getReturnTypeRefAssignment_1(), "rule__ArrowFunctionTypeExpression__ReturnTypeRefAssignment_1");
					put(grammarAccess.getTAnonymousFormalParameterListAccess().getFparsAssignment_0(), "rule__TAnonymousFormalParameterList__FparsAssignment_0");
					put(grammarAccess.getTAnonymousFormalParameterListAccess().getFparsAssignment_1_1(), "rule__TAnonymousFormalParameterList__FparsAssignment_1_1");
					put(grammarAccess.getTAnonymousFormalParameterAccess().getVariadicAssignment_0(), "rule__TAnonymousFormalParameter__VariadicAssignment_0");
					put(grammarAccess.getTAnonymousFormalParameterAccess().getNameAssignment_1_0_0_0(), "rule__TAnonymousFormalParameter__NameAssignment_1_0_0_0");
					put(grammarAccess.getTAnonymousFormalParameterAccess().getTypeRefAssignment_1_1(), "rule__TAnonymousFormalParameter__TypeRefAssignment_1_1");
					put(grammarAccess.getTFormalParameterAccess().getVariadicAssignment_0(), "rule__TFormalParameter__VariadicAssignment_0");
					put(grammarAccess.getTFormalParameterAccess().getNameAssignment_1(), "rule__TFormalParameter__NameAssignment_1");
					put(grammarAccess.getDefaultFormalParameterAccess().getHasInitializerAssignmentAssignment_0(), "rule__DefaultFormalParameter__HasInitializerAssignmentAssignment_0");
					put(grammarAccess.getDefaultFormalParameterAccess().getAstInitializerAssignment_1(), "rule__DefaultFormalParameter__AstInitializerAssignment_1");
					put(grammarAccess.getUnionTypeExpressionOLDAccess().getTypeRefsAssignment_3(), "rule__UnionTypeExpressionOLD__TypeRefsAssignment_3");
					put(grammarAccess.getUnionTypeExpressionOLDAccess().getTypeRefsAssignment_4_1(), "rule__UnionTypeExpressionOLD__TypeRefsAssignment_4_1");
					put(grammarAccess.getIntersectionTypeExpressionOLDAccess().getTypeRefsAssignment_3(), "rule__IntersectionTypeExpressionOLD__TypeRefsAssignment_3");
					put(grammarAccess.getIntersectionTypeExpressionOLDAccess().getTypeRefsAssignment_4_1(), "rule__IntersectionTypeExpressionOLD__TypeRefsAssignment_4_1");
					put(grammarAccess.getArrayTypeRefAccess().getArrayTypeLiteralAssignment_0(), "rule__ArrayTypeRef__ArrayTypeLiteralAssignment_0");
					put(grammarAccess.getArrayTypeRefAccess().getTypeArgsAssignment_1(), "rule__ArrayTypeRef__TypeArgsAssignment_1");
					put(grammarAccess.getParameterizedTypeRefStructuralAccess().getDefinedTypingStrategyAssignment_0(), "rule__ParameterizedTypeRefStructural__DefinedTypingStrategyAssignment_0");
					put(grammarAccess.getTypeAndTypeArgumentsAccess().getDeclaredTypeAssignment_0(), "rule__TypeAndTypeArguments__DeclaredTypeAssignment_0");
					put(grammarAccess.getTypeArgumentsAccess().getTypeArgsAssignment_1(), "rule__TypeArguments__TypeArgsAssignment_1");
					put(grammarAccess.getTypeArgumentsAccess().getTypeArgsAssignment_2_1(), "rule__TypeArguments__TypeArgsAssignment_2_1");
					put(grammarAccess.getTStructMemberListAccess().getAstStructuralMembersAssignment_1_0(), "rule__TStructMemberList__AstStructuralMembersAssignment_1_0");
					put(grammarAccess.getTStructMethodAccess().getNameAssignment_0_0_2(), "rule__TStructMethod__NameAssignment_0_0_2");
					put(grammarAccess.getTypeVariablesAccess().getTypeVarsAssignment_1(), "rule__TypeVariables__TypeVarsAssignment_1");
					put(grammarAccess.getTypeVariablesAccess().getTypeVarsAssignment_2_1(), "rule__TypeVariables__TypeVarsAssignment_2_1");
					put(grammarAccess.getColonSepDeclaredTypeRefAccess().getDeclaredTypeRefAssignment_1(), "rule__ColonSepDeclaredTypeRef__DeclaredTypeRefAssignment_1");
					put(grammarAccess.getColonSepTypeRefAccess().getTypeRefAssignment_1(), "rule__ColonSepTypeRef__TypeRefAssignment_1");
					put(grammarAccess.getColonSepReturnTypeRefAccess().getReturnTypeRefAssignment_1(), "rule__ColonSepReturnTypeRef__ReturnTypeRefAssignment_1");
					put(grammarAccess.getTStructFieldAccess().getNameAssignment_0(), "rule__TStructField__NameAssignment_0");
					put(grammarAccess.getTStructFieldAccess().getOptionalAssignment_1(), "rule__TStructField__OptionalAssignment_1");
					put(grammarAccess.getTStructGetterAccess().getNameAssignment_0_0_2(), "rule__TStructGetter__NameAssignment_0_0_2");
					put(grammarAccess.getTStructGetterAccess().getOptionalAssignment_1(), "rule__TStructGetter__OptionalAssignment_1");
					put(grammarAccess.getTStructSetterAccess().getNameAssignment_0_0_2(), "rule__TStructSetter__NameAssignment_0_0_2");
					put(grammarAccess.getTStructSetterAccess().getOptionalAssignment_1(), "rule__TStructSetter__OptionalAssignment_1");
					put(grammarAccess.getTStructSetterAccess().getFparAssignment_3(), "rule__TStructSetter__FparAssignment_3");
					put(grammarAccess.getTypeTypeRefAccess().getConstructorRefAssignment_1_1(), "rule__TypeTypeRef__ConstructorRefAssignment_1_1");
					put(grammarAccess.getTypeTypeRefAccess().getTypeArgAssignment_3(), "rule__TypeTypeRef__TypeArgAssignment_3");
					put(grammarAccess.getWildcardAccess().getDeclaredUpperBoundAssignment_1_0_1(), "rule__Wildcard__DeclaredUpperBoundAssignment_1_0_1");
					put(grammarAccess.getWildcardAccess().getDeclaredLowerBoundAssignment_1_1_1(), "rule__Wildcard__DeclaredLowerBoundAssignment_1_1_1");
					put(grammarAccess.getWildcardNewNotationAccess().getUsingInOutNotationAssignment_0_0(), "rule__WildcardNewNotation__UsingInOutNotationAssignment_0_0");
					put(grammarAccess.getWildcardNewNotationAccess().getDeclaredUpperBoundAssignment_0_1(), "rule__WildcardNewNotation__DeclaredUpperBoundAssignment_0_1");
					put(grammarAccess.getWildcardNewNotationAccess().getUsingInOutNotationAssignment_1_0(), "rule__WildcardNewNotation__UsingInOutNotationAssignment_1_0");
					put(grammarAccess.getWildcardNewNotationAccess().getDeclaredLowerBoundAssignment_1_1(), "rule__WildcardNewNotation__DeclaredLowerBoundAssignment_1_1");
					put(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredCovariantAssignment_0_0(), "superTypeVariable__DeclaredCovariantAssignment_0_0");
					put(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredContravariantAssignment_0_1(), "superTypeVariable__DeclaredContravariantAssignment_0_1");
					put(grammarAccess.getTypeExpressionsTypeVariableAccess().getNameAssignment_1(), "superTypeVariable__NameAssignment_1");
					put(grammarAccess.getTypeExpressionsTypeVariableAccess().getDeclaredUpperBoundAssignment_2_1(), "superTypeVariable__DeclaredUpperBoundAssignment_2_1");
				}
			};
		}
		return nameMappings.get(element);
	}
			
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_EOL" };
	}

	public TypesGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(TypesGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
