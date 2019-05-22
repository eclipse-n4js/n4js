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
import org.eclipse.n4js.ts.services.TypeExpressionsGrammarAccess;
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
import org.eclipse.n4js.ts.types.TAnonymousFormalParameter;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.TStructGetter;
import org.eclipse.n4js.ts.types.TStructMethod;
import org.eclipse.n4js.ts.types.TStructSetter;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;

@SuppressWarnings("all")
public class TypeExpressionsSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private TypeExpressionsGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == TypeRefsPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION:
				if (rule == grammarAccess.getTypeRefRule()
						|| action == grammarAccess.getTypeRefAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_0_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_ArrowFunctionTypeExpression_ColonSepReturnTypeRef_FunctionTypeExpressionOLD_TAnonymousFormalParameterList_TypeRefWithModifiers(context, (FunctionTypeExpression) semanticObject); 
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
						|| action == grammarAccess.getTypeRefAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_0_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_IntersectionTypeExpression_IntersectionTypeExpressionOLD_TypeRefWithModifiers(context, (IntersectionTypeExpression) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF:
				if (rule == grammarAccess.getTypeRefRule()
						|| action == grammarAccess.getTypeRefAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_0_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_ArrayTypeExpression_IterableTypeExpression_TypeArguments_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference(context, (ParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getIterableTypeExpressionRule()) {
					sequence_IterableTypeExpression(context, (ParameterizedTypeRef) semanticObject); 
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
				else if (rule == grammarAccess.getTypeArgInTypeTypeRefRule()
						|| rule == grammarAccess.getParameterizedTypeRefRule()
						|| rule == grammarAccess.getParameterizedTypeRefNominalRule()) {
					sequence_TypeArguments_TypeReference(context, (ParameterizedTypeRef) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL:
				if (rule == grammarAccess.getTypeRefRule()
						|| action == grammarAccess.getTypeRefAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_0_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeRefWithModifiersRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference(context, (ParameterizedTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithoutModifiersRule()) {
					sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRefWithoutModifiers_TypeReference(context, (ParameterizedTypeRefStructural) semanticObject); 
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
				else if (rule == grammarAccess.getTypeRefRule()
						|| action == grammarAccess.getTypeRefAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_0_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeRefWithModifiersRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_ThisTypeRefNominal_TypeRefWithModifiers_TypeRefWithoutModifiers(context, (ThisTypeRefNominal) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithoutModifiersRule()) {
					sequence_ThisTypeRefNominal_TypeRefWithoutModifiers(context, (ThisTypeRefNominal) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL:
				if (rule == grammarAccess.getThisTypeRefRule()
						|| rule == grammarAccess.getThisTypeRefStructuralRule()) {
					sequence_TStructMemberList_ThisTypeRefStructural(context, (ThisTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| action == grammarAccess.getTypeRefAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_0_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeRefWithModifiersRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_TStructMemberList_ThisTypeRefStructural_TypeRefWithModifiers_TypeRefWithoutModifiers(context, (ThisTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithoutModifiersRule()) {
					sequence_TStructMemberList_ThisTypeRefStructural_TypeRefWithoutModifiers(context, (ThisTypeRefStructural) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.TYPE_TYPE_REF:
				if (rule == grammarAccess.getTypeRefRule()
						|| action == grammarAccess.getTypeRefAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_0_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeRefWithModifiersRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_TypeRefWithModifiers_TypeTypeRef(context, (TypeTypeRef) semanticObject); 
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
				else if (rule == grammarAccess.getTypeRefRule()
						|| action == grammarAccess.getTypeRefAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_0_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_TypeRef_TypeRefWithModifiers_UnionTypeExpressionOLD(context, (UnionTypeExpression) semanticObject); 
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
				if (rule == grammarAccess.getTypeRefRule()
						|| action == grammarAccess.getTypeRefAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_0_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeRefWithModifiersRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_ParameterizedTypeRefNominal_TypeArguments_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference_VersionRequest(context, (VersionedParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithoutModifiersRule()) {
					sequence_ParameterizedTypeRefNominal_TypeArguments_TypeRefWithoutModifiers_TypeReference_VersionRequest(context, (VersionedParameterizedTypeRef) semanticObject); 
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
				if (rule == grammarAccess.getTypeRefRule()
						|| action == grammarAccess.getTypeRefAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefTypeArgsAction_1_0_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeRefWithModifiersRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference_VersionRequest(context, (VersionedParameterizedTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithoutModifiersRule()) {
					sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRefWithoutModifiers_TypeReference_VersionRequest(context, (VersionedParameterizedTypeRefStructural) semanticObject); 
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
			case TypesPackage.TANONYMOUS_FORMAL_PARAMETER:
				sequence_ColonSepTypeRef_DefaultFormalParameter_TAnonymousFormalParameter(context, (TAnonymousFormalParameter) semanticObject); 
				return; 
			case TypesPackage.TFORMAL_PARAMETER:
				sequence_ColonSepTypeRef_DefaultFormalParameter_TFormalParameter(context, (TFormalParameter) semanticObject); 
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
			case TypesPackage.TYPE_VARIABLE:
				sequence_TypeVariable(context, (TypeVariable) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Contexts:
	 *     TypeRef returns ParameterizedTypeRef
	 *     TypeRef.UnionTypeExpression_1_0 returns ParameterizedTypeRef
	 *     IntersectionTypeExpression returns ParameterizedTypeRef
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns ParameterizedTypeRef
	 *     ArrayTypeExpression returns ParameterizedTypeRef
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_0_0 returns ParameterizedTypeRef
	 *     PrimaryTypeExpression returns ParameterizedTypeRef
	 *     TypeArgument returns ParameterizedTypeRef
	 *
	 * Constraint:
	 *     (
	 *         (typeArgs+=ArrayTypeExpression_ParameterizedTypeRef_1_0_0 arrayTypeExpression?='[') | 
	 *         (iterableTypeExpression?='[' (typeArgs+=EmptyIterableTypeExpressionTail | (typeArgs+=TypeArgument typeArgs+=TypeArgument*))) | 
	 *         (declaredType=[Type|TypeReferenceName] (typeArgs+=TypeArgument typeArgs+=TypeArgument*)? dynamic?='+'? followedByQuestionMark?='?'?)
	 *     )
	 */
	protected void sequence_ArrayTypeExpression_IterableTypeExpression_TypeArguments_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference(ISerializationContext context, ParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns FunctionTypeExpression
	 *     TypeRef.UnionTypeExpression_1_0 returns FunctionTypeExpression
	 *     IntersectionTypeExpression returns FunctionTypeExpression
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns FunctionTypeExpression
	 *     ArrayTypeExpression returns FunctionTypeExpression
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_0_0 returns FunctionTypeExpression
	 *     PrimaryTypeExpression returns FunctionTypeExpression
	 *     TypeArgument returns FunctionTypeExpression
	 *
	 * Constraint:
	 *     (
	 *         (
	 *             declaredThisType=TypeRefFunctionTypeExpression? 
	 *             (ownedTypeVars+=TypeVariable ownedTypeVars+=TypeVariable*)? 
	 *             (
	 *                 (
	 *                     fpars+=TAnonymousFormalParameter 
	 *                     fpars+=TAnonymousFormalParameter* 
	 *                     (returnTypeRef=PrimaryTypeExpression | (returnTypeRef=TypeRef? followedByQuestionMark?='?'?))
	 *                 ) | 
	 *                 (returnTypeRef=TypeRef? followedByQuestionMark?='?'?)
	 *             )
	 *         ) | 
	 *         returnTypeRef=PrimaryTypeExpression
	 *     )?
	 */
	protected void sequence_ArrowFunctionTypeExpression_ColonSepReturnTypeRef_FunctionTypeExpressionOLD_TAnonymousFormalParameterList_TypeRefWithModifiers(ISerializationContext context, FunctionTypeExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ArrowFunctionTypeExpression returns FunctionTypeExpression
	 *
	 * Constraint:
	 *     ((fpars+=TAnonymousFormalParameter fpars+=TAnonymousFormalParameter*)? returnTypeRef=PrimaryTypeExpression)
	 */
	protected void sequence_ArrowFunctionTypeExpression_TAnonymousFormalParameterList(ISerializationContext context, FunctionTypeExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TStructMember returns TStructGetter
	 *     TStructGetter returns TStructGetter
	 *
	 * Constraint:
	 *     (name=IdentifierName optional?='?'? declaredTypeRef=TypeRef?)
	 */
	protected void sequence_ColonSepDeclaredTypeRef_TStructGetter(ISerializationContext context, TStructGetter semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefWithoutModifiers returns FunctionTypeExpression
	 *     FunctionTypeExpressionOLD returns FunctionTypeExpression
	 *
	 * Constraint:
	 *     (
	 *         declaredThisType=TypeRefFunctionTypeExpression? 
	 *         (ownedTypeVars+=TypeVariable ownedTypeVars+=TypeVariable*)? 
	 *         (fpars+=TAnonymousFormalParameter fpars+=TAnonymousFormalParameter*)? 
	 *         returnTypeRef=TypeRef?
	 *     )
	 */
	protected void sequence_ColonSepReturnTypeRef_FunctionTypeExpressionOLD_TAnonymousFormalParameterList(ISerializationContext context, FunctionTypeExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefWithModifiers returns FunctionTypeExpression
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
	protected void sequence_ColonSepReturnTypeRef_FunctionTypeExpressionOLD_TAnonymousFormalParameterList_TypeRefWithModifiers(ISerializationContext context, FunctionTypeExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TStructMember returns TStructMethod
	 *     TStructMethod returns TStructMethod
	 *
	 * Constraint:
	 *     (
	 *         (typeVars+=TypeVariable typeVars+=TypeVariable*)? 
	 *         name=IdentifierName 
	 *         (fpars+=TAnonymousFormalParameter fpars+=TAnonymousFormalParameter*)? 
	 *         returnTypeRef=TypeRef?
	 *     )
	 */
	protected void sequence_ColonSepReturnTypeRef_TAnonymousFormalParameterList_TStructMethod_TypeVariables(ISerializationContext context, TStructMethod semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TAnonymousFormalParameter returns TAnonymousFormalParameter
	 *
	 * Constraint:
	 *     (
	 *         variadic?='...'? 
	 *         ((name=BindingIdentifier typeRef=TypeRef) | typeRef=TypeRef) 
	 *         (hasInitializerAssignment?='=' astInitializer=TypeReferenceName?)?
	 *     )
	 */
	protected void sequence_ColonSepTypeRef_DefaultFormalParameter_TAnonymousFormalParameter(ISerializationContext context, TAnonymousFormalParameter semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TFormalParameter returns TFormalParameter
	 *
	 * Constraint:
	 *     (variadic?='...'? name=BindingIdentifier typeRef=TypeRef (hasInitializerAssignment?='=' astInitializer=TypeReferenceName?)?)
	 */
	protected void sequence_ColonSepTypeRef_DefaultFormalParameter_TFormalParameter(ISerializationContext context, TFormalParameter semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TStructMember returns TStructField
	 *     TStructField returns TStructField
	 *
	 * Constraint:
	 *     (name=IdentifierName optional?='?'? typeRef=TypeRef?)
	 */
	protected void sequence_ColonSepTypeRef_TStructField(ISerializationContext context, TStructField semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     EmptyIterableTypeExpressionTail returns Wildcard
	 *
	 * Constraint:
	 *     {Wildcard}
	 */
	protected void sequence_EmptyIterableTypeExpressionTail(ISerializationContext context, Wildcard semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefWithoutModifiers returns IntersectionTypeExpression
	 *     TypeRefFunctionTypeExpression returns IntersectionTypeExpression
	 *     IntersectionTypeExpressionOLD returns IntersectionTypeExpression
	 *
	 * Constraint:
	 *     (typeRefs+=TypeRef typeRefs+=TypeRef*)
	 */
	protected void sequence_IntersectionTypeExpressionOLD(ISerializationContext context, IntersectionTypeExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefWithModifiers returns IntersectionTypeExpression
	 *
	 * Constraint:
	 *     (typeRefs+=TypeRef typeRefs+=TypeRef* followedByQuestionMark?='?'?)
	 */
	protected void sequence_IntersectionTypeExpressionOLD_TypeRefWithModifiers(ISerializationContext context, IntersectionTypeExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns IntersectionTypeExpression
	 *     TypeRef.UnionTypeExpression_1_0 returns IntersectionTypeExpression
	 *     IntersectionTypeExpression returns IntersectionTypeExpression
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns IntersectionTypeExpression
	 *     ArrayTypeExpression returns IntersectionTypeExpression
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_0_0 returns IntersectionTypeExpression
	 *     PrimaryTypeExpression returns IntersectionTypeExpression
	 *     TypeArgument returns IntersectionTypeExpression
	 *
	 * Constraint:
	 *     (
	 *         (typeRefs+=IntersectionTypeExpression_IntersectionTypeExpression_1_0 typeRefs+=ArrayTypeExpression+) | 
	 *         (typeRefs+=TypeRef typeRefs+=TypeRef* followedByQuestionMark?='?'?)
	 *     )
	 */
	protected void sequence_IntersectionTypeExpression_IntersectionTypeExpressionOLD_TypeRefWithModifiers(ISerializationContext context, IntersectionTypeExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     IterableTypeExpression returns ParameterizedTypeRef
	 *
	 * Constraint:
	 *     (iterableTypeExpression?='[' (typeArgs+=EmptyIterableTypeExpressionTail | (typeArgs+=TypeArgument typeArgs+=TypeArgument*)))
	 */
	protected void sequence_IterableTypeExpression(ISerializationContext context, ParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefFunctionTypeExpression returns ParameterizedTypeRef
	 *
	 * Constraint:
	 *     (
	 *         (iterableTypeExpression?='[' (typeArgs+=EmptyIterableTypeExpressionTail | (typeArgs+=TypeArgument typeArgs+=TypeArgument*))) | 
	 *         (declaredType=[Type|TypeReferenceName] (typeArgs+=TypeArgument typeArgs+=TypeArgument*)?)
	 *     )
	 */
	protected void sequence_IterableTypeExpression_TypeArguments_TypeReference(ISerializationContext context, ParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns VersionedParameterizedTypeRef
	 *     TypeRef.UnionTypeExpression_1_0 returns VersionedParameterizedTypeRef
	 *     IntersectionTypeExpression returns VersionedParameterizedTypeRef
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns VersionedParameterizedTypeRef
	 *     ArrayTypeExpression returns VersionedParameterizedTypeRef
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_0_0 returns VersionedParameterizedTypeRef
	 *     PrimaryTypeExpression returns VersionedParameterizedTypeRef
	 *     TypeRefWithModifiers returns VersionedParameterizedTypeRef
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
	protected void sequence_ParameterizedTypeRefNominal_TypeArguments_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference_VersionRequest(ISerializationContext context, VersionedParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefWithoutModifiers returns VersionedParameterizedTypeRef
	 *
	 * Constraint:
	 *     (declaredType=[Type|TypeReferenceName] requestedVersion=VERSION (typeArgs+=TypeArgument typeArgs+=TypeArgument*)? dynamic?='+'?)
	 */
	protected void sequence_ParameterizedTypeRefNominal_TypeArguments_TypeRefWithoutModifiers_TypeReference_VersionRequest(ISerializationContext context, VersionedParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefFunctionTypeExpression returns VersionedParameterizedTypeRef
	 *     TypeArgInTypeTypeRef returns VersionedParameterizedTypeRef
	 *     ParameterizedTypeRef returns VersionedParameterizedTypeRef
	 *     ParameterizedTypeRefNominal returns VersionedParameterizedTypeRef
	 *
	 * Constraint:
	 *     (declaredType=[Type|TypeReferenceName] requestedVersion=VERSION (typeArgs+=TypeArgument typeArgs+=TypeArgument*)?)
	 */
	protected void sequence_ParameterizedTypeRefNominal_TypeArguments_TypeReference_VersionRequest(ISerializationContext context, VersionedParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns ParameterizedTypeRefStructural
	 *     TypeRef.UnionTypeExpression_1_0 returns ParameterizedTypeRefStructural
	 *     IntersectionTypeExpression returns ParameterizedTypeRefStructural
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns ParameterizedTypeRefStructural
	 *     ArrayTypeExpression returns ParameterizedTypeRefStructural
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_0_0 returns ParameterizedTypeRefStructural
	 *     PrimaryTypeExpression returns ParameterizedTypeRefStructural
	 *     TypeRefWithModifiers returns ParameterizedTypeRefStructural
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
	protected void sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference(ISerializationContext context, ParameterizedTypeRefStructural semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns VersionedParameterizedTypeRefStructural
	 *     TypeRef.UnionTypeExpression_1_0 returns VersionedParameterizedTypeRefStructural
	 *     IntersectionTypeExpression returns VersionedParameterizedTypeRefStructural
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns VersionedParameterizedTypeRefStructural
	 *     ArrayTypeExpression returns VersionedParameterizedTypeRefStructural
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_0_0 returns VersionedParameterizedTypeRefStructural
	 *     PrimaryTypeExpression returns VersionedParameterizedTypeRefStructural
	 *     TypeRefWithModifiers returns VersionedParameterizedTypeRefStructural
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
	protected void sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference_VersionRequest(ISerializationContext context, VersionedParameterizedTypeRefStructural semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefWithoutModifiers returns ParameterizedTypeRefStructural
	 *
	 * Constraint:
	 *     (
	 *         definedTypingStrategy=TypingStrategyUseSiteOperator 
	 *         declaredType=[Type|TypeReferenceName] 
	 *         (typeArgs+=TypeArgument typeArgs+=TypeArgument*)? 
	 *         astStructuralMembers+=TStructMember* 
	 *         dynamic?='+'?
	 *     )
	 */
	protected void sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRefWithoutModifiers_TypeReference(ISerializationContext context, ParameterizedTypeRefStructural semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefWithoutModifiers returns VersionedParameterizedTypeRefStructural
	 *
	 * Constraint:
	 *     (
	 *         definedTypingStrategy=TypingStrategyUseSiteOperator 
	 *         declaredType=[Type|TypeReferenceName] 
	 *         requestedVersion=VERSION 
	 *         (typeArgs+=TypeArgument typeArgs+=TypeArgument*)? 
	 *         astStructuralMembers+=TStructMember* 
	 *         dynamic?='+'?
	 *     )
	 */
	protected void sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRefWithoutModifiers_TypeReference_VersionRequest(ISerializationContext context, VersionedParameterizedTypeRefStructural semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefFunctionTypeExpression returns ParameterizedTypeRefStructural
	 *     ParameterizedTypeRef returns ParameterizedTypeRefStructural
	 *     ParameterizedTypeRefStructural returns ParameterizedTypeRefStructural
	 *
	 * Constraint:
	 *     (
	 *         definedTypingStrategy=TypingStrategyUseSiteOperator 
	 *         declaredType=[Type|TypeReferenceName] 
	 *         (typeArgs+=TypeArgument typeArgs+=TypeArgument*)? 
	 *         astStructuralMembers+=TStructMember*
	 *     )
	 */
	protected void sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeReference(ISerializationContext context, ParameterizedTypeRefStructural semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefFunctionTypeExpression returns VersionedParameterizedTypeRefStructural
	 *     ParameterizedTypeRef returns VersionedParameterizedTypeRefStructural
	 *     ParameterizedTypeRefStructural returns VersionedParameterizedTypeRefStructural
	 *
	 * Constraint:
	 *     (
	 *         definedTypingStrategy=TypingStrategyUseSiteOperator 
	 *         declaredType=[Type|TypeReferenceName] 
	 *         requestedVersion=VERSION 
	 *         (typeArgs+=TypeArgument typeArgs+=TypeArgument*)? 
	 *         astStructuralMembers+=TStructMember*
	 *     )
	 */
	protected void sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeReference_VersionRequest(ISerializationContext context, VersionedParameterizedTypeRefStructural semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ThisTypeRef returns ThisTypeRefStructural
	 *     ThisTypeRefStructural returns ThisTypeRefStructural
	 *
	 * Constraint:
	 *     (definedTypingStrategy=TypingStrategyUseSiteOperator astStructuralMembers+=TStructMember*)
	 */
	protected void sequence_TStructMemberList_ThisTypeRefStructural(ISerializationContext context, ThisTypeRefStructural semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns ThisTypeRefStructural
	 *     TypeRef.UnionTypeExpression_1_0 returns ThisTypeRefStructural
	 *     IntersectionTypeExpression returns ThisTypeRefStructural
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns ThisTypeRefStructural
	 *     ArrayTypeExpression returns ThisTypeRefStructural
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_0_0 returns ThisTypeRefStructural
	 *     PrimaryTypeExpression returns ThisTypeRefStructural
	 *     TypeRefWithModifiers returns ThisTypeRefStructural
	 *     TypeArgument returns ThisTypeRefStructural
	 *
	 * Constraint:
	 *     (definedTypingStrategy=TypingStrategyUseSiteOperator astStructuralMembers+=TStructMember* dynamic?='+'? followedByQuestionMark?='?'?)
	 */
	protected void sequence_TStructMemberList_ThisTypeRefStructural_TypeRefWithModifiers_TypeRefWithoutModifiers(ISerializationContext context, ThisTypeRefStructural semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefWithoutModifiers returns ThisTypeRefStructural
	 *
	 * Constraint:
	 *     (definedTypingStrategy=TypingStrategyUseSiteOperator astStructuralMembers+=TStructMember* dynamic?='+'?)
	 */
	protected void sequence_TStructMemberList_ThisTypeRefStructural_TypeRefWithoutModifiers(ISerializationContext context, ThisTypeRefStructural semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TStructMember returns TStructSetter
	 *     TStructSetter returns TStructSetter
	 *
	 * Constraint:
	 *     (name=IdentifierName optional?='?'? fpar=TAnonymousFormalParameter)
	 */
	protected void sequence_TStructSetter(ISerializationContext context, TStructSetter semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeArgInTypeTypeRef returns ThisTypeRefNominal
	 *     ThisTypeRef returns ThisTypeRefNominal
	 *     ThisTypeRefNominal returns ThisTypeRefNominal
	 *
	 * Constraint:
	 *     {ThisTypeRefNominal}
	 */
	protected void sequence_ThisTypeRefNominal(ISerializationContext context, ThisTypeRefNominal semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns ThisTypeRefNominal
	 *     TypeRef.UnionTypeExpression_1_0 returns ThisTypeRefNominal
	 *     IntersectionTypeExpression returns ThisTypeRefNominal
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns ThisTypeRefNominal
	 *     ArrayTypeExpression returns ThisTypeRefNominal
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_0_0 returns ThisTypeRefNominal
	 *     PrimaryTypeExpression returns ThisTypeRefNominal
	 *     TypeRefWithModifiers returns ThisTypeRefNominal
	 *     TypeArgument returns ThisTypeRefNominal
	 *
	 * Constraint:
	 *     (dynamic?='+'? followedByQuestionMark?='?'?)
	 */
	protected void sequence_ThisTypeRefNominal_TypeRefWithModifiers_TypeRefWithoutModifiers(ISerializationContext context, ThisTypeRefNominal semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefWithoutModifiers returns ThisTypeRefNominal
	 *
	 * Constraint:
	 *     dynamic?='+'?
	 */
	protected void sequence_ThisTypeRefNominal_TypeRefWithoutModifiers(ISerializationContext context, ThisTypeRefNominal semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefWithModifiers returns ParameterizedTypeRef
	 *
	 * Constraint:
	 *     (declaredType=[Type|TypeReferenceName] (typeArgs+=TypeArgument typeArgs+=TypeArgument*)? dynamic?='+'? followedByQuestionMark?='?'?)
	 */
	protected void sequence_TypeArguments_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference(ISerializationContext context, ParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefWithoutModifiers returns ParameterizedTypeRef
	 *
	 * Constraint:
	 *     (declaredType=[Type|TypeReferenceName] (typeArgs+=TypeArgument typeArgs+=TypeArgument*)? dynamic?='+'?)
	 */
	protected void sequence_TypeArguments_TypeRefWithoutModifiers_TypeReference(ISerializationContext context, ParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeArgInTypeTypeRef returns ParameterizedTypeRef
	 *     ParameterizedTypeRef returns ParameterizedTypeRef
	 *     ParameterizedTypeRefNominal returns ParameterizedTypeRef
	 *
	 * Constraint:
	 *     (declaredType=[Type|TypeReferenceName] (typeArgs+=TypeArgument typeArgs+=TypeArgument*)?)
	 */
	protected void sequence_TypeArguments_TypeReference(ISerializationContext context, ParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns TypeTypeRef
	 *     TypeRef.UnionTypeExpression_1_0 returns TypeTypeRef
	 *     IntersectionTypeExpression returns TypeTypeRef
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns TypeTypeRef
	 *     ArrayTypeExpression returns TypeTypeRef
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_0_0 returns TypeTypeRef
	 *     PrimaryTypeExpression returns TypeTypeRef
	 *     TypeRefWithModifiers returns TypeTypeRef
	 *     TypeArgument returns TypeTypeRef
	 *
	 * Constraint:
	 *     (constructorRef?='constructor'? typeArg=TypeArgInTypeTypeRef followedByQuestionMark?='?'?)
	 */
	protected void sequence_TypeRefWithModifiers_TypeTypeRef(ISerializationContext context, TypeTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefWithModifiers returns UnionTypeExpression
	 *
	 * Constraint:
	 *     (typeRefs+=TypeRef typeRefs+=TypeRef* followedByQuestionMark?='?'?)
	 */
	protected void sequence_TypeRefWithModifiers_UnionTypeExpressionOLD(ISerializationContext context, UnionTypeExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns UnionTypeExpression
	 *     TypeRef.UnionTypeExpression_1_0 returns UnionTypeExpression
	 *     IntersectionTypeExpression returns UnionTypeExpression
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns UnionTypeExpression
	 *     ArrayTypeExpression returns UnionTypeExpression
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_0_0 returns UnionTypeExpression
	 *     PrimaryTypeExpression returns UnionTypeExpression
	 *     TypeArgument returns UnionTypeExpression
	 *
	 * Constraint:
	 *     (
	 *         (typeRefs+=TypeRef_UnionTypeExpression_1_0 typeRefs+=IntersectionTypeExpression+) | 
	 *         (typeRefs+=TypeRef typeRefs+=TypeRef* followedByQuestionMark?='?'?)
	 *     )
	 */
	protected void sequence_TypeRef_TypeRefWithModifiers_UnionTypeExpressionOLD(ISerializationContext context, UnionTypeExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefWithoutModifiers returns TypeTypeRef
	 *     TypeRefFunctionTypeExpression returns TypeTypeRef
	 *     TypeTypeRef returns TypeTypeRef
	 *
	 * Constraint:
	 *     (constructorRef?='constructor'? typeArg=TypeArgInTypeTypeRef)
	 */
	protected void sequence_TypeTypeRef(ISerializationContext context, TypeTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeVariable returns TypeVariable
	 *
	 * Constraint:
	 *     ((declaredCovariant?='out' | declaredContravariant?='in')? name=IDENTIFIER declaredUpperBound=TypeRef?)
	 */
	protected void sequence_TypeVariable(ISerializationContext context, TypeVariable semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefWithoutModifiers returns UnionTypeExpression
	 *     TypeRefFunctionTypeExpression returns UnionTypeExpression
	 *     UnionTypeExpressionOLD returns UnionTypeExpression
	 *
	 * Constraint:
	 *     (typeRefs+=TypeRef typeRefs+=TypeRef*)
	 */
	protected void sequence_UnionTypeExpressionOLD(ISerializationContext context, UnionTypeExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     WildcardNewNotation returns Wildcard
	 *
	 * Constraint:
	 *     ((usingInOutNotation?='out' declaredUpperBound=TypeRef) | (usingInOutNotation?='in' declaredLowerBound=TypeRef))
	 */
	protected void sequence_WildcardNewNotation(ISerializationContext context, Wildcard semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeArgInTypeTypeRef returns Wildcard
	 *     Wildcard returns Wildcard
	 *
	 * Constraint:
	 *     (declaredUpperBound=TypeRef | declaredLowerBound=TypeRef)?
	 */
	protected void sequence_Wildcard(ISerializationContext context, Wildcard semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeArgument returns Wildcard
	 *
	 * Constraint:
	 *     (
	 *         declaredUpperBound=TypeRef | 
	 *         declaredLowerBound=TypeRef | 
	 *         (usingInOutNotation?='out' declaredUpperBound=TypeRef) | 
	 *         (usingInOutNotation?='in' declaredLowerBound=TypeRef)
	 *     )?
	 */
	protected void sequence_Wildcard_WildcardNewNotation(ISerializationContext context, Wildcard semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
