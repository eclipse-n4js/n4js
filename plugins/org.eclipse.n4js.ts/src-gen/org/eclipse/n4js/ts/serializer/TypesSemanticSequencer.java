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
package org.eclipse.n4js.ts.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.n4js.ts.services.TypesGrammarAccess;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefNominal;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.VersionedParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.VersionedParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.AnyType;
import org.eclipse.n4js.ts.types.NullType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.ts.types.TAnnotationStringArgument;
import org.eclipse.n4js.ts.types.TAnnotationTypeRefArgument;
import org.eclipse.n4js.ts.types.TAnonymousFormalParameter;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TObjectPrototype;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.TStructGetter;
import org.eclipse.n4js.ts.types.TStructMethod;
import org.eclipse.n4js.ts.types.TStructSetter;
import org.eclipse.n4js.ts.types.TypeDefs;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.ts.types.UndefinedType;
import org.eclipse.n4js.ts.types.VirtualBaseType;
import org.eclipse.n4js.ts.types.VoidType;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class TypesSemanticSequencer extends TypeExpressionsSemanticSequencer {

	@Inject
	private TypesGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == TypeRefsPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION:
				if (rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_0_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()) {
					sequence_ArrowFunctionTypeExpression_ColonSepReturnTypeRef_FunctionTypeExpressionOLD_TAnonymousFormalParameterList_TypeRef_TypeRefWithModifiers(context, (FunctionTypeExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getArrowFunctionTypeExpressionRule()) {
					sequence_ArrowFunctionTypeExpression_TAnonymousFormalParameterList(context, (FunctionTypeExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithoutModifiersRule()
						|| rule == grammarAccess.getFunctionTypeExpressionOLDRule()) {
					sequence_ColonSepReturnTypeRef_FunctionTypeExpressionOLD_TAnonymousFormalParameterList(context, (FunctionTypeExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithModifiersRule()) {
					sequence_ColonSepReturnTypeRef_FunctionTypeExpressionOLD_TAnonymousFormalParameterList_TypeRefWithModifiers(context, (FunctionTypeExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_ColonSepReturnTypeRef_FunctionTypeExpressionOLD_TAnonymousFormalParameterList_TypeRef(context, (FunctionTypeExpression) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.INTERSECTION_TYPE_EXPRESSION:
				if (rule == grammarAccess.getTypeRefWithoutModifiersRule()
						|| rule == grammarAccess.getTypeRefFunctionTypeExpressionRule()
						|| rule == grammarAccess.getIntersectionTypeExpressionOLDRule()) {
					sequence_IntersectionTypeExpressionOLD(context, (IntersectionTypeExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithModifiersRule()) {
					sequence_IntersectionTypeExpressionOLD_TypeRefWithModifiers(context, (IntersectionTypeExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_IntersectionTypeExpressionOLD_TypeRef(context, (IntersectionTypeExpression) semanticObject); 
					return; 
				}
				else if (action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_0_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()) {
					sequence_IntersectionTypeExpressionOLD_TypeRef_TypeRefWithModifiers(context, (IntersectionTypeExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getIntersectionTypeExpressionRule()) {
					sequence_IntersectionTypeExpression_IntersectionTypeExpressionOLD_TypeRef_TypeRefWithModifiers(context, (IntersectionTypeExpression) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF:
				if (rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_0_0()) {
					sequence_ArrayTypeExpression_IterableTypeExpression_TypeArguments_TypeRef_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference(context, (ParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getIterableTypeExpressionRule()) {
					sequence_IterableTypeExpression(context, (ParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getPrimaryTypeExpressionRule()) {
					sequence_IterableTypeExpression_TypeArguments_TypeRef_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference(context, (ParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefFunctionTypeExpressionRule()) {
					sequence_IterableTypeExpression_TypeArguments_TypeReference(context, (ParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithModifiersRule()) {
					sequence_TypeArguments_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference(context, (ParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithoutModifiersRule()) {
					sequence_TypeArguments_TypeRefWithoutModifiers_TypeReference(context, (ParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_TypeArguments_TypeRef_TypeRefWithoutModifiers_TypeReference(context, (ParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeArgInTypeTypeRefRule()
						|| rule == grammarAccess.getParameterizedTypeRefRule()
						|| rule == grammarAccess.getParameterizedTypeRefNominalRule()) {
					sequence_TypeArguments_TypeReference(context, (ParameterizedTypeRef) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL:
				if (rule == grammarAccess.getTypeRefWithModifiersRule()) {
					sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference(context, (ParameterizedTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithoutModifiersRule()) {
					sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRefWithoutModifiers_TypeReference(context, (ParameterizedTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_0_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()) {
					sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRef_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference(context, (ParameterizedTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRef_TypeRefWithoutModifiers_TypeReference(context, (ParameterizedTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefFunctionTypeExpressionRule()
						|| rule == grammarAccess.getParameterizedTypeRefRule()
						|| rule == grammarAccess.getParameterizedTypeRefStructuralRule()) {
					sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeReference(context, (ParameterizedTypeRefStructural) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.THIS_TYPE_REF_NOMINAL:
				if (rule == grammarAccess.getTypeArgInTypeTypeRefRule()
						|| rule == grammarAccess.getThisTypeRefRule()
						|| rule == grammarAccess.getThisTypeRefNominalRule()) {
					sequence_ThisTypeRefNominal(context, (ThisTypeRefNominal) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithModifiersRule()) {
					sequence_ThisTypeRefNominal_TypeRefWithModifiers_TypeRefWithoutModifiers(context, (ThisTypeRefNominal) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithoutModifiersRule()) {
					sequence_ThisTypeRefNominal_TypeRefWithoutModifiers(context, (ThisTypeRefNominal) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_0_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()) {
					sequence_ThisTypeRefNominal_TypeRef_TypeRefWithModifiers_TypeRefWithoutModifiers(context, (ThisTypeRefNominal) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_ThisTypeRefNominal_TypeRef_TypeRefWithoutModifiers(context, (ThisTypeRefNominal) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL:
				if (rule == grammarAccess.getThisTypeRefRule()
						|| rule == grammarAccess.getThisTypeRefStructuralRule()) {
					sequence_TStructMemberList_ThisTypeRefStructural(context, (ThisTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithModifiersRule()) {
					sequence_TStructMemberList_ThisTypeRefStructural_TypeRefWithModifiers_TypeRefWithoutModifiers(context, (ThisTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithoutModifiersRule()) {
					sequence_TStructMemberList_ThisTypeRefStructural_TypeRefWithoutModifiers(context, (ThisTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_0_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()) {
					sequence_TStructMemberList_ThisTypeRefStructural_TypeRef_TypeRefWithModifiers_TypeRefWithoutModifiers(context, (ThisTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_TStructMemberList_ThisTypeRefStructural_TypeRef_TypeRefWithoutModifiers(context, (ThisTypeRefStructural) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.TYPE_TYPE_REF:
				if (rule == grammarAccess.getTypeRefWithModifiersRule()) {
					sequence_TypeRefWithModifiers_TypeTypeRef(context, (TypeTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_0_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()) {
					sequence_TypeRef_TypeRefWithModifiers_TypeTypeRef(context, (TypeTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_TypeRef_TypeTypeRef(context, (TypeTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithoutModifiersRule()
						|| rule == grammarAccess.getTypeRefFunctionTypeExpressionRule()
						|| rule == grammarAccess.getTypeTypeRefRule()) {
					sequence_TypeTypeRef(context, (TypeTypeRef) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.UNION_TYPE_EXPRESSION:
				if (rule == grammarAccess.getTypeRefWithModifiersRule()) {
					sequence_TypeRefWithModifiers_UnionTypeExpressionOLD(context, (UnionTypeExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_0_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()) {
					sequence_TypeRef_TypeRefWithModifiers_UnionTypeExpressionOLD(context, (UnionTypeExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_TypeRef_UnionTypeExpressionOLD(context, (UnionTypeExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithoutModifiersRule()
						|| rule == grammarAccess.getTypeRefFunctionTypeExpressionRule()
						|| rule == grammarAccess.getUnionTypeExpressionOLDRule()) {
					sequence_UnionTypeExpressionOLD(context, (UnionTypeExpression) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.VERSIONED_PARAMETERIZED_TYPE_REF:
				if (rule == grammarAccess.getTypeRefWithModifiersRule()) {
					sequence_ParameterizedTypeRefNominal_TypeArguments_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference_VersionRequest(context, (VersionedParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithoutModifiersRule()) {
					sequence_ParameterizedTypeRefNominal_TypeArguments_TypeRefWithoutModifiers_TypeReference_VersionRequest(context, (VersionedParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_0_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()) {
					sequence_ParameterizedTypeRefNominal_TypeArguments_TypeRef_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference_VersionRequest(context, (VersionedParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_ParameterizedTypeRefNominal_TypeArguments_TypeRef_TypeRefWithoutModifiers_TypeReference_VersionRequest(context, (VersionedParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefFunctionTypeExpressionRule()
						|| rule == grammarAccess.getTypeArgInTypeTypeRefRule()
						|| rule == grammarAccess.getParameterizedTypeRefRule()
						|| rule == grammarAccess.getParameterizedTypeRefNominalRule()) {
					sequence_ParameterizedTypeRefNominal_TypeArguments_TypeReference_VersionRequest(context, (VersionedParameterizedTypeRef) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL:
				if (rule == grammarAccess.getTypeRefWithModifiersRule()) {
					sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference_VersionRequest(context, (VersionedParameterizedTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithoutModifiersRule()) {
					sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRefWithoutModifiers_TypeReference_VersionRequest(context, (VersionedParameterizedTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_0_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()) {
					sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRef_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference_VersionRequest(context, (VersionedParameterizedTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRef_TypeRefWithoutModifiers_TypeReference_VersionRequest(context, (VersionedParameterizedTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefFunctionTypeExpressionRule()
						|| rule == grammarAccess.getParameterizedTypeRefRule()
						|| rule == grammarAccess.getParameterizedTypeRefStructuralRule()) {
					sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeReference_VersionRequest(context, (VersionedParameterizedTypeRefStructural) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.WILDCARD:
				if (rule == grammarAccess.getEmptyIterableTypeExpressionTailRule()) {
					sequence_EmptyIterableTypeExpressionTail(context, (Wildcard) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getWildcardNewNotationRule()) {
					sequence_WildcardNewNotation(context, (Wildcard) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeArgInTypeTypeRefRule()
						|| rule == grammarAccess.getWildcardRule()) {
					sequence_Wildcard(context, (Wildcard) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeArgumentRule()) {
					sequence_Wildcard_WildcardNewNotation(context, (Wildcard) semanticObject); 
					return; 
				}
				else break;
			}
		else if (epackage == TypesPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case TypesPackage.ANY_TYPE:
				sequence_AnyType(context, (AnyType) semanticObject); 
				return; 
			case TypesPackage.NULL_TYPE:
				sequence_NullType(context, (NullType) semanticObject); 
				return; 
			case TypesPackage.PRIMITIVE_TYPE:
				sequence_PrimitiveType(context, (PrimitiveType) semanticObject); 
				return; 
			case TypesPackage.TANNOTATION:
				sequence_TAnnotation(context, (TAnnotation) semanticObject); 
				return; 
			case TypesPackage.TANNOTATION_STRING_ARGUMENT:
				sequence_TAnnotationStringArgument(context, (TAnnotationStringArgument) semanticObject); 
				return; 
			case TypesPackage.TANNOTATION_TYPE_REF_ARGUMENT:
				sequence_TAnnotationTypeRefArgument(context, (TAnnotationTypeRefArgument) semanticObject); 
				return; 
			case TypesPackage.TANONYMOUS_FORMAL_PARAMETER:
				sequence_ColonSepTypeRef_DefaultFormalParameter_TAnonymousFormalParameter(context, (TAnonymousFormalParameter) semanticObject); 
				return; 
			case TypesPackage.TCLASS:
				sequence_TClass_TClassOrInterfaceHeader(context, (TClass) semanticObject); 
				return; 
			case TypesPackage.TENUM:
				sequence_TEnum(context, (TEnum) semanticObject); 
				return; 
			case TypesPackage.TENUM_LITERAL:
				sequence_TEnumLiteral(context, (TEnumLiteral) semanticObject); 
				return; 
			case TypesPackage.TFIELD:
				sequence_ColonSepTypeRef_TField(context, (TField) semanticObject); 
				return; 
			case TypesPackage.TFORMAL_PARAMETER:
				sequence_ColonSepTypeRef_DefaultFormalParameter_TFormalParameter(context, (TFormalParameter) semanticObject); 
				return; 
			case TypesPackage.TFUNCTION:
				sequence_ColonSepReturnTypeRef_TFormalParameters_TFunction_TypeVariables(context, (TFunction) semanticObject); 
				return; 
			case TypesPackage.TGETTER:
				sequence_ColonSepDeclaredTypeRef_TGetter(context, (TGetter) semanticObject); 
				return; 
			case TypesPackage.TINTERFACE:
				sequence_TClassOrInterfaceHeader_TInterface(context, (TInterface) semanticObject); 
				return; 
			case TypesPackage.TMETHOD:
				if (rule == grammarAccess.getCallableCtorRule()) {
					sequence_CallableCtor_ColonSepReturnTypeRef_TFormalParameters(context, (TMethod) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTMemberRule()
						|| rule == grammarAccess.getTMethodRule()) {
					sequence_ColonSepReturnTypeRef_TFormalParameters_TMethod_TypeVariables(context, (TMethod) semanticObject); 
					return; 
				}
				else break;
			case TypesPackage.TOBJECT_PROTOTYPE:
				sequence_TObjectPrototype_TypeVariables(context, (TObjectPrototype) semanticObject); 
				return; 
			case TypesPackage.TSETTER:
				sequence_TSetter(context, (TSetter) semanticObject); 
				return; 
			case TypesPackage.TSTRUCT_FIELD:
				sequence_ColonSepTypeRef_TStructField(context, (TStructField) semanticObject); 
				return; 
			case TypesPackage.TSTRUCT_GETTER:
				sequence_ColonSepDeclaredTypeRef_TStructGetter(context, (TStructGetter) semanticObject); 
				return; 
			case TypesPackage.TSTRUCT_METHOD:
				sequence_ColonSepReturnTypeRef_TAnonymousFormalParameterList_TStructMethod_TypeVariables(context, (TStructMethod) semanticObject); 
				return; 
			case TypesPackage.TSTRUCT_SETTER:
				sequence_TStructSetter(context, (TStructSetter) semanticObject); 
				return; 
			case TypesPackage.TYPE_DEFS:
				sequence_TypeDefs(context, (TypeDefs) semanticObject); 
				return; 
			case TypesPackage.TYPE_VARIABLE:
				if (rule == grammarAccess.getTypeRule()
						|| rule == grammarAccess.getTypeVariableRule()) {
					sequence_TypeVariable(context, (TypeVariable) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeExpressionsTypeVariableRule()) {
					sequence_TypeVariable(context, (TypeVariable) semanticObject); 
					return; 
				}
				else break;
			case TypesPackage.UNDEFINED_TYPE:
				sequence_UndefinedType(context, (UndefinedType) semanticObject); 
				return; 
			case TypesPackage.VIRTUAL_BASE_TYPE:
				sequence_VirtualBaseType(context, (VirtualBaseType) semanticObject); 
				return; 
			case TypesPackage.VOID_TYPE:
				sequence_VoidType(context, (VoidType) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Contexts:
	 *     Type returns AnyType
	 *     AnyType returns AnyType
	 *
	 * Constraint:
	 *     name='any'
	 */
	protected void sequence_AnyType(ISerializationContext context, AnyType semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, TypesPackage.Literals.IDENTIFIABLE_ELEMENT__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TypesPackage.Literals.IDENTIFIABLE_ELEMENT__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getAnyTypeAccess().getNameAnyKeyword_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     IntersectionTypeExpression returns ParameterizedTypeRef
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns ParameterizedTypeRef
	 *     ArrayTypeExpression returns ParameterizedTypeRef
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_0_0 returns ParameterizedTypeRef
	 *
	 * Constraint:
	 *     (
	 *         (typeArgs+=ArrayTypeExpression_ParameterizedTypeRef_1_0_0 arrayTypeExpression?='[') | 
	 *         (iterableTypeExpression?='[' (typeArgs+=EmptyIterableTypeExpressionTail | (typeArgs+=TypeArgument typeArgs+=TypeArgument*))) | 
	 *         (
	 *             declaredType=[Type|TypeReferenceName] 
	 *             (typeArgs+=TypeArgument typeArgs+=TypeArgument*)? 
	 *             dynamic?='+'? 
	 *             (followedByQuestionMark?='?' | followedByQuestionMark?='?')?
	 *         )
	 *     )
	 */
	protected void sequence_ArrayTypeExpression_IterableTypeExpression_TypeArguments_TypeRef_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference(ISerializationContext context, ParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     IntersectionTypeExpression returns FunctionTypeExpression
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns FunctionTypeExpression
	 *     ArrayTypeExpression returns FunctionTypeExpression
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_0_0 returns FunctionTypeExpression
	 *     PrimaryTypeExpression returns FunctionTypeExpression
	 *
	 * Constraint:
	 *     (
	 *         (
	 *             declaredThisType=TypeRefFunctionTypeExpression? 
	 *             (ownedTypeVars+=TypeVariable ownedTypeVars+=TypeVariable*)? 
	 *             (
	 *                 (
	 *                     (fpars+=TAnonymousFormalParameter fpars+=TAnonymousFormalParameter*)? 
	 *                     returnTypeRef=TypeRef? 
	 *                     (followedByQuestionMark?='?' | followedByQuestionMark?='?')?
	 *                 ) | 
	 *                 ((fpars+=TAnonymousFormalParameter fpars+=TAnonymousFormalParameter*)? returnTypeRef=PrimaryTypeExpression)
	 *             )
	 *         ) | 
	 *         returnTypeRef=PrimaryTypeExpression
	 *     )?
	 */
	protected void sequence_ArrowFunctionTypeExpression_ColonSepReturnTypeRef_FunctionTypeExpressionOLD_TAnonymousFormalParameterList_TypeRef_TypeRefWithModifiers(ISerializationContext context, FunctionTypeExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     CallableCtor returns TMethod
	 *
	 * Constraint:
	 *     ((fpars+=TFormalParameter fpars+=TFormalParameter*)? returnTypeRef=TypeRef?)
	 */
	protected void sequence_CallableCtor_ColonSepReturnTypeRef_TFormalParameters(ISerializationContext context, TMethod semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TMember returns TGetter
	 *     TGetter returns TGetter
	 *
	 * Constraint:
	 *     (
	 *         declaredMemberAccessModifier=MemberAccessModifier 
	 *         (declaredAbstract?='abstract' | declaredStatic?='static')? 
	 *         (name=TypesIdentifier | name=TypesComputedPropertyName) 
	 *         optional?='?'? 
	 *         declaredTypeRef=TypeRef
	 *     )
	 */
	protected void sequence_ColonSepDeclaredTypeRef_TGetter(ISerializationContext context, TGetter semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns FunctionTypeExpression
	 *     TypeArgument returns FunctionTypeExpression
	 *
	 * Constraint:
	 *     (
	 *         declaredThisType=TypeRefFunctionTypeExpression? 
	 *         (ownedTypeVars+=TypeVariable ownedTypeVars+=TypeVariable*)? 
	 *         (fpars+=TAnonymousFormalParameter fpars+=TAnonymousFormalParameter*)? 
	 *         returnTypeRef=TypeRef? 
	 *         followedByQuestionMark?='?'?
	 *     )
	 */
	protected void sequence_ColonSepReturnTypeRef_FunctionTypeExpressionOLD_TAnonymousFormalParameterList_TypeRef(ISerializationContext context, FunctionTypeExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Type returns TFunction
	 *     TFunction returns TFunction
	 *
	 * Constraint:
	 *     (
	 *         declaredTypeAccessModifier=TypeAccessModifier 
	 *         declaredProvidedByRuntime?='providedByRuntime'? 
	 *         (typeVars+=TypeVariable typeVars+=TypeVariable*)? 
	 *         name=BindingTypesIdentifier 
	 *         (fpars+=TFormalParameter fpars+=TFormalParameter*)? 
	 *         returnTypeRef=TypeRef
	 *     )
	 */
	protected void sequence_ColonSepReturnTypeRef_TFormalParameters_TFunction_TypeVariables(ISerializationContext context, TFunction semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TMember returns TMethod
	 *     TMethod returns TMethod
	 *
	 * Constraint:
	 *     (
	 *         declaredMemberAccessModifier=MemberAccessModifier 
	 *         (declaredAbstract?='abstract' | declaredStatic?='static')? 
	 *         (typeVars+=TypeVariable typeVars+=TypeVariable*)? 
	 *         (name=TypesIdentifier | name=TypesComputedPropertyName) 
	 *         (fpars+=TFormalParameter fpars+=TFormalParameter*)? 
	 *         returnTypeRef=TypeRef
	 *     )
	 */
	protected void sequence_ColonSepReturnTypeRef_TFormalParameters_TMethod_TypeVariables(ISerializationContext context, TMethod semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TMember returns TField
	 *     TField returns TField
	 *
	 * Constraint:
	 *     (
	 *         declaredMemberAccessModifier=MemberAccessModifier 
	 *         (declaredStatic?='static' | const?='const' | declaredFinal?='final')? 
	 *         (name=TypesIdentifier | name=TypesComputedPropertyName) 
	 *         optional?='?'? 
	 *         typeRef=TypeRef
	 *     )
	 */
	protected void sequence_ColonSepTypeRef_TField(ISerializationContext context, TField semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns IntersectionTypeExpression
	 *     TypeArgument returns IntersectionTypeExpression
	 *
	 * Constraint:
	 *     (typeRefs+=TypeRefWithoutModifiers typeRefs+=TypeRefWithoutModifiers* followedByQuestionMark?='?'?)
	 */
	protected void sequence_IntersectionTypeExpressionOLD_TypeRef(ISerializationContext context, IntersectionTypeExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns IntersectionTypeExpression
	 *     ArrayTypeExpression returns IntersectionTypeExpression
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_0_0 returns IntersectionTypeExpression
	 *     PrimaryTypeExpression returns IntersectionTypeExpression
	 *
	 * Constraint:
	 *     (typeRefs+=TypeRefWithoutModifiers typeRefs+=TypeRefWithoutModifiers* (followedByQuestionMark?='?' | followedByQuestionMark?='?')?)
	 */
	protected void sequence_IntersectionTypeExpressionOLD_TypeRef_TypeRefWithModifiers(ISerializationContext context, IntersectionTypeExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     IntersectionTypeExpression returns IntersectionTypeExpression
	 *
	 * Constraint:
	 *     (
	 *         (typeRefs+=IntersectionTypeExpression_IntersectionTypeExpression_1_0 typeRefs+=ArrayTypeExpression+) | 
	 *         (typeRefs+=TypeRefWithoutModifiers typeRefs+=TypeRefWithoutModifiers* (followedByQuestionMark?='?' | followedByQuestionMark?='?')?)
	 *     )
	 */
	protected void sequence_IntersectionTypeExpression_IntersectionTypeExpressionOLD_TypeRef_TypeRefWithModifiers(ISerializationContext context, IntersectionTypeExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     PrimaryTypeExpression returns ParameterizedTypeRef
	 *
	 * Constraint:
	 *     (
	 *         (iterableTypeExpression?='[' (typeArgs+=EmptyIterableTypeExpressionTail | (typeArgs+=TypeArgument typeArgs+=TypeArgument*))) | 
	 *         (
	 *             declaredType=[Type|TypeReferenceName] 
	 *             (typeArgs+=TypeArgument typeArgs+=TypeArgument*)? 
	 *             dynamic?='+'? 
	 *             (followedByQuestionMark?='?' | followedByQuestionMark?='?')?
	 *         )
	 *     )
	 */
	protected void sequence_IterableTypeExpression_TypeArguments_TypeRef_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference(ISerializationContext context, ParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Type returns NullType
	 *     NullType returns NullType
	 *
	 * Constraint:
	 *     name='null'
	 */
	protected void sequence_NullType(ISerializationContext context, NullType semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, TypesPackage.Literals.IDENTIFIABLE_ELEMENT__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TypesPackage.Literals.IDENTIFIABLE_ELEMENT__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getNullTypeAccess().getNameNullKeyword_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     IntersectionTypeExpression returns VersionedParameterizedTypeRef
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns VersionedParameterizedTypeRef
	 *     ArrayTypeExpression returns VersionedParameterizedTypeRef
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_0_0 returns VersionedParameterizedTypeRef
	 *     PrimaryTypeExpression returns VersionedParameterizedTypeRef
	 *
	 * Constraint:
	 *     (
	 *         declaredType=[Type|TypeReferenceName] 
	 *         requestedVersion=VERSION 
	 *         (typeArgs+=TypeArgument typeArgs+=TypeArgument*)? 
	 *         dynamic?='+'? 
	 *         (followedByQuestionMark?='?' | followedByQuestionMark?='?')?
	 *     )
	 */
	protected void sequence_ParameterizedTypeRefNominal_TypeArguments_TypeRef_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference_VersionRequest(ISerializationContext context, VersionedParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns VersionedParameterizedTypeRef
	 *     TypeArgument returns VersionedParameterizedTypeRef
	 *
	 * Constraint:
	 *     (
	 *         declaredType=[Type|TypeReferenceName] 
	 *         requestedVersion=VERSION 
	 *         (typeArgs+=TypeArgument typeArgs+=TypeArgument*)? 
	 *         dynamic?='+'? 
	 *         followedByQuestionMark?='?'?
	 *     )
	 */
	protected void sequence_ParameterizedTypeRefNominal_TypeArguments_TypeRef_TypeRefWithoutModifiers_TypeReference_VersionRequest(ISerializationContext context, VersionedParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     IntersectionTypeExpression returns ParameterizedTypeRefStructural
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns ParameterizedTypeRefStructural
	 *     ArrayTypeExpression returns ParameterizedTypeRefStructural
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_0_0 returns ParameterizedTypeRefStructural
	 *     PrimaryTypeExpression returns ParameterizedTypeRefStructural
	 *
	 * Constraint:
	 *     (
	 *         definedTypingStrategy=TypingStrategyUseSiteOperator 
	 *         declaredType=[Type|TypeReferenceName] 
	 *         (typeArgs+=TypeArgument typeArgs+=TypeArgument*)? 
	 *         astStructuralMembers+=TStructMember* 
	 *         dynamic?='+'? 
	 *         (followedByQuestionMark?='?' | followedByQuestionMark?='?')?
	 *     )
	 */
	protected void sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRef_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference(ISerializationContext context, ParameterizedTypeRefStructural semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     IntersectionTypeExpression returns VersionedParameterizedTypeRefStructural
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns VersionedParameterizedTypeRefStructural
	 *     ArrayTypeExpression returns VersionedParameterizedTypeRefStructural
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_0_0 returns VersionedParameterizedTypeRefStructural
	 *     PrimaryTypeExpression returns VersionedParameterizedTypeRefStructural
	 *
	 * Constraint:
	 *     (
	 *         definedTypingStrategy=TypingStrategyUseSiteOperator 
	 *         declaredType=[Type|TypeReferenceName] 
	 *         requestedVersion=VERSION 
	 *         (typeArgs+=TypeArgument typeArgs+=TypeArgument*)? 
	 *         astStructuralMembers+=TStructMember* 
	 *         dynamic?='+'? 
	 *         (followedByQuestionMark?='?' | followedByQuestionMark?='?')?
	 *     )
	 */
	protected void sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRef_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference_VersionRequest(ISerializationContext context, VersionedParameterizedTypeRefStructural semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns ParameterizedTypeRefStructural
	 *     TypeArgument returns ParameterizedTypeRefStructural
	 *
	 * Constraint:
	 *     (
	 *         definedTypingStrategy=TypingStrategyUseSiteOperator 
	 *         declaredType=[Type|TypeReferenceName] 
	 *         (typeArgs+=TypeArgument typeArgs+=TypeArgument*)? 
	 *         astStructuralMembers+=TStructMember* 
	 *         dynamic?='+'? 
	 *         followedByQuestionMark?='?'?
	 *     )
	 */
	protected void sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRef_TypeRefWithoutModifiers_TypeReference(ISerializationContext context, ParameterizedTypeRefStructural semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns VersionedParameterizedTypeRefStructural
	 *     TypeArgument returns VersionedParameterizedTypeRefStructural
	 *
	 * Constraint:
	 *     (
	 *         definedTypingStrategy=TypingStrategyUseSiteOperator 
	 *         declaredType=[Type|TypeReferenceName] 
	 *         requestedVersion=VERSION 
	 *         (typeArgs+=TypeArgument typeArgs+=TypeArgument*)? 
	 *         astStructuralMembers+=TStructMember* 
	 *         dynamic?='+'? 
	 *         followedByQuestionMark?='?'?
	 *     )
	 */
	protected void sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRef_TypeRefWithoutModifiers_TypeReference_VersionRequest(ISerializationContext context, VersionedParameterizedTypeRefStructural semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Type returns PrimitiveType
	 *     PrimitiveType returns PrimitiveType
	 *
	 * Constraint:
	 *     (
	 *         name=VoidOrBindingIdentifier 
	 *         typeVars+=TypeVariable? 
	 *         declaredElementType=ParameterizedTypeRefNominal? 
	 *         autoboxedType=[TClassifier|TypeReferenceName]? 
	 *         assignmentCompatible=[PrimitiveType|TypeReferenceName]?
	 *     )
	 */
	protected void sequence_PrimitiveType(ISerializationContext context, PrimitiveType semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TAnnotationArgument returns TAnnotationStringArgument
	 *     TAnnotationStringArgument returns TAnnotationStringArgument
	 *
	 * Constraint:
	 *     value=STRING
	 */
	protected void sequence_TAnnotationStringArgument(ISerializationContext context, TAnnotationStringArgument semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, TypesPackage.Literals.TANNOTATION_STRING_ARGUMENT__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TypesPackage.Literals.TANNOTATION_STRING_ARGUMENT__VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getTAnnotationStringArgumentAccess().getValueSTRINGTerminalRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     TAnnotationArgument returns TAnnotationTypeRefArgument
	 *     TAnnotationTypeRefArgument returns TAnnotationTypeRefArgument
	 *
	 * Constraint:
	 *     typeRef=TypeRef
	 */
	protected void sequence_TAnnotationTypeRefArgument(ISerializationContext context, TAnnotationTypeRefArgument semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, TypesPackage.Literals.TTYPED_ELEMENT__TYPE_REF) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TypesPackage.Literals.TTYPED_ELEMENT__TYPE_REF));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getTAnnotationTypeRefArgumentAccess().getTypeRefTypeRefParserRuleCall_0(), semanticObject.getTypeRef());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     TAnnotation returns TAnnotation
	 *
	 * Constraint:
	 *     (name=IDENTIFIER (args+=TAnnotationArgument args+=TAnnotationArgument*)?)
	 */
	protected void sequence_TAnnotation(ISerializationContext context, TAnnotation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Type returns TInterface
	 *     TInterface returns TInterface
	 *
	 * Constraint:
	 *     (
	 *         declaredTypeAccessModifier=TypeAccessModifier 
	 *         declaredProvidedByRuntime?='providedByRuntime'? 
	 *         typingStrategy=TypingStrategyDefSiteOperator? 
	 *         name=BindingTypesIdentifier 
	 *         (typeVars+=TypeVariable typeVars+=TypeVariable*)? 
	 *         (superInterfaceRefs+=ParameterizedTypeRefNominal superInterfaceRefs+=ParameterizedTypeRefNominal*)? 
	 *         annotations+=TAnnotation* 
	 *         ownedMembers+=TMember*
	 *     )
	 */
	protected void sequence_TClassOrInterfaceHeader_TInterface(ISerializationContext context, TInterface semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Type returns TClass
	 *     TClass returns TClass
	 *
	 * Constraint:
	 *     (
	 *         declaredTypeAccessModifier=TypeAccessModifier 
	 *         declaredProvidedByRuntime?='providedByRuntime'? 
	 *         declaredAbstract?='abstract'? 
	 *         declaredFinal?='final'? 
	 *         typingStrategy=TypingStrategyDefSiteOperator? 
	 *         name=BindingTypesIdentifier 
	 *         (typeVars+=TypeVariable typeVars+=TypeVariable*)? 
	 *         superClassRef=ParameterizedTypeRefNominal? 
	 *         (implementedInterfaceRefs+=ParameterizedTypeRefNominal implementedInterfaceRefs+=ParameterizedTypeRefNominal*)? 
	 *         annotations+=TAnnotation* 
	 *         ownedMembers+=TMember* 
	 *         (callableCtor=CallableCtor ownedMembers+=TMember*)?
	 *     )
	 */
	protected void sequence_TClass_TClassOrInterfaceHeader(ISerializationContext context, TClass semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TEnumLiteral returns TEnumLiteral
	 *
	 * Constraint:
	 *     name=IDENTIFIER
	 */
	protected void sequence_TEnumLiteral(ISerializationContext context, TEnumLiteral semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, TypesPackage.Literals.IDENTIFIABLE_ELEMENT__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TypesPackage.Literals.IDENTIFIABLE_ELEMENT__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getTEnumLiteralAccess().getNameIDENTIFIERTerminalRuleCall_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Type returns TEnum
	 *     TEnum returns TEnum
	 *
	 * Constraint:
	 *     (
	 *         declaredTypeAccessModifier=TypeAccessModifier 
	 *         declaredProvidedByRuntime?='providedByRuntime'? 
	 *         name=BindingTypesIdentifier 
	 *         literals+=TEnumLiteral 
	 *         literals+=TEnumLiteral*
	 *     )
	 */
	protected void sequence_TEnum(ISerializationContext context, TEnum semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Type returns TObjectPrototype
	 *     TObjectPrototype returns TObjectPrototype
	 *
	 * Constraint:
	 *     (
	 *         declaredTypeAccessModifier=TypeAccessModifier 
	 *         declaredProvidedByRuntime?='providedByRuntime'? 
	 *         declaredFinal?='final'? 
	 *         name=BindingTypesIdentifier 
	 *         (typeVars+=TypeVariable typeVars+=TypeVariable*)? 
	 *         superType=ParameterizedTypeRefNominal? 
	 *         declaredElementType=ParameterizedTypeRefNominal? 
	 *         annotations+=TAnnotation* 
	 *         ownedMembers+=TMember* 
	 *         (callableCtor=CallableCtor ownedMembers+=TMember*)?
	 *     )
	 */
	protected void sequence_TObjectPrototype_TypeVariables(ISerializationContext context, TObjectPrototype semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TMember returns TSetter
	 *     TSetter returns TSetter
	 *
	 * Constraint:
	 *     (
	 *         declaredMemberAccessModifier=MemberAccessModifier 
	 *         (declaredAbstract?='abstract' | declaredStatic?='static')? 
	 *         (name=TypesIdentifier | name=TypesComputedPropertyName) 
	 *         optional?='?'? 
	 *         fpar=TFormalParameter
	 *     )
	 */
	protected void sequence_TSetter(ISerializationContext context, TSetter semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     IntersectionTypeExpression returns ThisTypeRefStructural
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns ThisTypeRefStructural
	 *     ArrayTypeExpression returns ThisTypeRefStructural
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_0_0 returns ThisTypeRefStructural
	 *     PrimaryTypeExpression returns ThisTypeRefStructural
	 *
	 * Constraint:
	 *     (
	 *         definedTypingStrategy=TypingStrategyUseSiteOperator 
	 *         astStructuralMembers+=TStructMember* 
	 *         dynamic?='+'? 
	 *         (followedByQuestionMark?='?' | followedByQuestionMark?='?')?
	 *     )
	 */
	protected void sequence_TStructMemberList_ThisTypeRefStructural_TypeRef_TypeRefWithModifiers_TypeRefWithoutModifiers(ISerializationContext context, ThisTypeRefStructural semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns ThisTypeRefStructural
	 *     TypeArgument returns ThisTypeRefStructural
	 *
	 * Constraint:
	 *     (definedTypingStrategy=TypingStrategyUseSiteOperator astStructuralMembers+=TStructMember* dynamic?='+'? followedByQuestionMark?='?'?)
	 */
	protected void sequence_TStructMemberList_ThisTypeRefStructural_TypeRef_TypeRefWithoutModifiers(ISerializationContext context, ThisTypeRefStructural semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     IntersectionTypeExpression returns ThisTypeRefNominal
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns ThisTypeRefNominal
	 *     ArrayTypeExpression returns ThisTypeRefNominal
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_0_0 returns ThisTypeRefNominal
	 *     PrimaryTypeExpression returns ThisTypeRefNominal
	 *
	 * Constraint:
	 *     (dynamic?='+'? (followedByQuestionMark?='?' | followedByQuestionMark?='?')?)
	 */
	protected void sequence_ThisTypeRefNominal_TypeRef_TypeRefWithModifiers_TypeRefWithoutModifiers(ISerializationContext context, ThisTypeRefNominal semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns ThisTypeRefNominal
	 *     TypeArgument returns ThisTypeRefNominal
	 *
	 * Constraint:
	 *     (dynamic?='+'? followedByQuestionMark?='?'?)
	 */
	protected void sequence_ThisTypeRefNominal_TypeRef_TypeRefWithoutModifiers(ISerializationContext context, ThisTypeRefNominal semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns ParameterizedTypeRef
	 *     TypeArgument returns ParameterizedTypeRef
	 *
	 * Constraint:
	 *     (declaredType=[Type|TypeReferenceName] (typeArgs+=TypeArgument typeArgs+=TypeArgument*)? dynamic?='+'? followedByQuestionMark?='?'?)
	 */
	protected void sequence_TypeArguments_TypeRef_TypeRefWithoutModifiers_TypeReference(ISerializationContext context, ParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeDefs returns TypeDefs
	 *
	 * Constraint:
	 *     types+=Type+
	 */
	protected void sequence_TypeDefs(ISerializationContext context, TypeDefs semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     IntersectionTypeExpression returns TypeTypeRef
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns TypeTypeRef
	 *     ArrayTypeExpression returns TypeTypeRef
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_0_0 returns TypeTypeRef
	 *     PrimaryTypeExpression returns TypeTypeRef
	 *
	 * Constraint:
	 *     (constructorRef?='constructor'? typeArg=TypeArgInTypeTypeRef (followedByQuestionMark?='?' | followedByQuestionMark?='?')?)
	 */
	protected void sequence_TypeRef_TypeRefWithModifiers_TypeTypeRef(ISerializationContext context, TypeTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     IntersectionTypeExpression returns UnionTypeExpression
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns UnionTypeExpression
	 *     ArrayTypeExpression returns UnionTypeExpression
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_0_0 returns UnionTypeExpression
	 *     PrimaryTypeExpression returns UnionTypeExpression
	 *
	 * Constraint:
	 *     (typeRefs+=TypeRefWithoutModifiers typeRefs+=TypeRefWithoutModifiers* (followedByQuestionMark?='?' | followedByQuestionMark?='?')?)
	 */
	protected void sequence_TypeRef_TypeRefWithModifiers_UnionTypeExpressionOLD(ISerializationContext context, UnionTypeExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns TypeTypeRef
	 *     TypeArgument returns TypeTypeRef
	 *
	 * Constraint:
	 *     (constructorRef?='constructor'? typeArg=TypeArgInTypeTypeRef followedByQuestionMark?='?'?)
	 */
	protected void sequence_TypeRef_TypeTypeRef(ISerializationContext context, TypeTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns UnionTypeExpression
	 *     TypeArgument returns UnionTypeExpression
	 *
	 * Constraint:
	 *     (typeRefs+=TypeRefWithoutModifiers typeRefs+=TypeRefWithoutModifiers* followedByQuestionMark?='?'?)
	 */
	protected void sequence_TypeRef_UnionTypeExpressionOLD(ISerializationContext context, UnionTypeExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Type returns TypeVariable
	 *     TypeVariable returns TypeVariable
	 *
	 * Constraint:
	 *     (name=IDENTIFIER declaredUpperBound=TypeRef?)
	 */
	protected void sequence_TypeVariable(ISerializationContext context, TypeVariable semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Type returns UndefinedType
	 *     UndefinedType returns UndefinedType
	 *
	 * Constraint:
	 *     name='undefined'
	 */
	protected void sequence_UndefinedType(ISerializationContext context, UndefinedType semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, TypesPackage.Literals.IDENTIFIABLE_ELEMENT__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TypesPackage.Literals.IDENTIFIABLE_ELEMENT__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getUndefinedTypeAccess().getNameUndefinedKeyword_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     Type returns VirtualBaseType
	 *     VirtualBaseType returns VirtualBaseType
	 *
	 * Constraint:
	 *     (name=BindingTypesIdentifier declaredElementType=ParameterizedTypeRefNominal? ownedMembers+=TMember*)
	 */
	protected void sequence_VirtualBaseType(ISerializationContext context, VirtualBaseType semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Type returns VoidType
	 *     VoidType returns VoidType
	 *
	 * Constraint:
	 *     name='void'
	 */
	protected void sequence_VoidType(ISerializationContext context, VoidType semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, TypesPackage.Literals.IDENTIFIABLE_ELEMENT__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TypesPackage.Literals.IDENTIFIABLE_ELEMENT__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getVoidTypeAccess().getNameVoidKeyword_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
}
