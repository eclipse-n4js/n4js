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
package org.eclipse.n4js.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.n4js.services.TypeExpressionsGrammarAccess;
import org.eclipse.n4js.ts.typeRefs.BooleanLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ConditionalTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.IndexAccessTypeRef;
import org.eclipse.n4js.ts.typeRefs.InferTypeRef;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
<<<<<<< HEAD
import org.eclipse.n4js.ts.typeRefs.NamespaceLikeRef;
=======
import org.eclipse.n4js.ts.typeRefs.MappedTypeRef;
>>>>>>> 765b95ac8 (early support for mapped types)
import org.eclipse.n4js.ts.typeRefs.NumericLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.OperatorTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.QueryTypeRef;
import org.eclipse.n4js.ts.typeRefs.StringLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefNominal;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.TAnonymousFormalParameter;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.TStructGetter;
import org.eclipse.n4js.ts.types.TStructIndexSignature;
import org.eclipse.n4js.ts.types.TStructMethod;
import org.eclipse.n4js.ts.types.TStructSetter;
import org.eclipse.n4js.ts.types.TypePredicate;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

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
			case TypeRefsPackage.BOOLEAN_LITERAL_TYPE_REF:
				sequence_BooleanLiteralTypeRef(context, (BooleanLiteralTypeRef) semanticObject); 
				return; 
			case TypeRefsPackage.CONDITIONAL_TYPE_REF:
				sequence_ConditionalTypeRef(context, (ConditionalTypeRef) semanticObject); 
				return; 
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION:
				sequence_ArrowFunctionTypeExpression_TAnonymousFormalParameterListWithDeclaredThisType(context, (FunctionTypeExpression) semanticObject); 
				return; 
			case TypeRefsPackage.INDEX_ACCESS_TYPE_REF:
				sequence_ArrayTypeExpression(context, (IndexAccessTypeRef) semanticObject); 
				return; 
			case TypeRefsPackage.INFER_TYPE_REF:
				sequence_InferTypeRef(context, (InferTypeRef) semanticObject); 
				return; 
			case TypeRefsPackage.INTERSECTION_TYPE_EXPRESSION:
				if (rule == grammarAccess.getTypeRefWithModifiersRule()
						|| rule == grammarAccess.getIntersectionTypeExpressionOLDRule()) {
					sequence_IntersectionTypeExpressionOLD(context, (IntersectionTypeExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| rule == grammarAccess.getConditionalTypeRefRule()
						|| action == grammarAccess.getConditionalTypeRefAccess().getConditionalTypeRefTypeRefAction_1_0_0_0()
						|| rule == grammarAccess.getUnionTypeExpressionRule()
						|| action == grammarAccess.getUnionTypeExpressionAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getOperatorTypeRefRule()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefDeclaredTypeArgsAction_2_1_0_0_0()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getIndexAccessTypeRefTargetTypeRefAction_2_1_0_1_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_IntersectionTypeExpression_IntersectionTypeExpressionOLD(context, (IntersectionTypeExpression) semanticObject); 
					return; 
				}
				else break;
<<<<<<< HEAD
			case TypeRefsPackage.NAMESPACE_LIKE_REF:
				sequence_NamespaceLikeRef(context, (NamespaceLikeRef) semanticObject); 
=======
			case TypeRefsPackage.MAPPED_TYPE_REF:
				sequence_MappedTypeRef(context, (MappedTypeRef) semanticObject); 
>>>>>>> 765b95ac8 (early support for mapped types)
				return; 
			case TypeRefsPackage.NUMERIC_LITERAL_TYPE_REF:
				sequence_NumericLiteralTypeRef(context, (NumericLiteralTypeRef) semanticObject); 
				return; 
			case TypeRefsPackage.OPERATOR_TYPE_REF:
				sequence_OperatorTypeRef(context, (OperatorTypeRef) semanticObject); 
				return; 
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF:
				if (rule == grammarAccess.getTypeRefRule()
						|| rule == grammarAccess.getConditionalTypeRefRule()
						|| action == grammarAccess.getConditionalTypeRefAccess().getConditionalTypeRefTypeRefAction_1_0_0_0()
						|| rule == grammarAccess.getUnionTypeExpressionRule()
						|| action == grammarAccess.getUnionTypeExpressionAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getOperatorTypeRefRule()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefDeclaredTypeArgsAction_2_1_0_0_0()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getIndexAccessTypeRefTargetTypeRefAction_2_1_0_1_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_ArrayNTypeExpression_ArrayTypeExpression_TypeArguments_TypeRefWithModifiers_TypeReference(context, (ParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getArrayNTypeExpressionRule()) {
					sequence_ArrayNTypeExpression(context, (ParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefDeclaredTypeArgsAction_0_4_0_0()) {
					sequence_ArrayTypeExpression_ParameterizedTypeRef_0_4_0_0(context, (ParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefDeclaredTypeArgsAction_1_6_0_0()) {
					sequence_ArrayTypeExpression_ParameterizedTypeRef_1_6_0_0(context, (ParameterizedTypeRef) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefWithModifiersRule()) {
					sequence_TypeArguments_TypeRefWithModifiers_TypeReference(context, (ParameterizedTypeRef) semanticObject); 
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
						|| rule == grammarAccess.getConditionalTypeRefRule()
						|| action == grammarAccess.getConditionalTypeRefAccess().getConditionalTypeRefTypeRefAction_1_0_0_0()
						|| rule == grammarAccess.getUnionTypeExpressionRule()
						|| action == grammarAccess.getUnionTypeExpressionAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getOperatorTypeRefRule()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefDeclaredTypeArgsAction_2_1_0_0_0()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getIndexAccessTypeRefTargetTypeRefAction_2_1_0_1_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeRefWithModifiersRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRefWithModifiers_TypeReference(context, (ParameterizedTypeRefStructural) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getParameterizedTypeRefRule()
						|| rule == grammarAccess.getParameterizedTypeRefStructuralRule()) {
					sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeReference(context, (ParameterizedTypeRefStructural) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.QUERY_TYPE_REF:
				sequence_QueryTypeRef(context, (QueryTypeRef) semanticObject); 
				return; 
			case TypeRefsPackage.STRING_LITERAL_TYPE_REF:
				sequence_StringLiteralTypeRef(context, (StringLiteralTypeRef) semanticObject); 
				return; 
			case TypeRefsPackage.THIS_TYPE_REF_NOMINAL:
				if (rule == grammarAccess.getTypeArgInTypeTypeRefRule()
						|| rule == grammarAccess.getThisTypeRefRule()
						|| rule == grammarAccess.getThisTypeRefNominalRule()) {
					sequence_ThisTypeRefNominal(context, (ThisTypeRefNominal) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| rule == grammarAccess.getConditionalTypeRefRule()
						|| action == grammarAccess.getConditionalTypeRefAccess().getConditionalTypeRefTypeRefAction_1_0_0_0()
						|| rule == grammarAccess.getUnionTypeExpressionRule()
						|| action == grammarAccess.getUnionTypeExpressionAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getOperatorTypeRefRule()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefDeclaredTypeArgsAction_2_1_0_0_0()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getIndexAccessTypeRefTargetTypeRefAction_2_1_0_1_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeRefWithModifiersRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_ThisTypeRefNominal_TypeRefWithModifiers(context, (ThisTypeRefNominal) semanticObject); 
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
						|| rule == grammarAccess.getConditionalTypeRefRule()
						|| action == grammarAccess.getConditionalTypeRefAccess().getConditionalTypeRefTypeRefAction_1_0_0_0()
						|| rule == grammarAccess.getUnionTypeExpressionRule()
						|| action == grammarAccess.getUnionTypeExpressionAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getOperatorTypeRefRule()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefDeclaredTypeArgsAction_2_1_0_0_0()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getIndexAccessTypeRefTargetTypeRefAction_2_1_0_1_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeRefWithModifiersRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_TStructMemberList_ThisTypeRefStructural_TypeRefWithModifiers(context, (ThisTypeRefStructural) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.TYPE_TYPE_REF:
				sequence_TypeTypeRef(context, (TypeTypeRef) semanticObject); 
				return; 
			case TypeRefsPackage.UNION_TYPE_EXPRESSION:
				if (rule == grammarAccess.getTypeRefWithModifiersRule()
						|| rule == grammarAccess.getUnionTypeExpressionOLDRule()) {
					sequence_UnionTypeExpressionOLD(context, (UnionTypeExpression) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeRefRule()
						|| rule == grammarAccess.getConditionalTypeRefRule()
						|| action == grammarAccess.getConditionalTypeRefAccess().getConditionalTypeRefTypeRefAction_1_0_0_0()
						|| rule == grammarAccess.getUnionTypeExpressionRule()
						|| action == grammarAccess.getUnionTypeExpressionAccess().getUnionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getIntersectionTypeExpressionRule()
						|| action == grammarAccess.getIntersectionTypeExpressionAccess().getIntersectionTypeExpressionTypeRefsAction_1_0()
						|| rule == grammarAccess.getOperatorTypeRefRule()
						|| rule == grammarAccess.getArrayTypeExpressionRule()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getParameterizedTypeRefDeclaredTypeArgsAction_2_1_0_0_0()
						|| action == grammarAccess.getArrayTypeExpressionAccess().getIndexAccessTypeRefTargetTypeRefAction_2_1_0_1_0()
						|| rule == grammarAccess.getPrimaryTypeExpressionRule()
						|| rule == grammarAccess.getTypeArgumentRule()) {
					sequence_UnionTypeExpression_UnionTypeExpressionOLD(context, (UnionTypeExpression) semanticObject); 
					return; 
				}
				else break;
			case TypeRefsPackage.WILDCARD:
				if (rule == grammarAccess.getEmptyIterableTypeExpressionTailRule()
						|| rule == grammarAccess.getWildcardOldNotationWithoutBoundRule()) {
					sequence_EmptyIterableTypeExpressionTail_WildcardOldNotationWithoutBound(context, (Wildcard) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getWildcardNewNotationRule()) {
					sequence_WildcardNewNotation(context, (Wildcard) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeArgumentRule()
						|| rule == grammarAccess.getWildcardRule()) {
					sequence_WildcardNewNotation_WildcardOldNotation(context, (Wildcard) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypeArgInTypeTypeRefRule()
						|| rule == grammarAccess.getWildcardOldNotationRule()) {
					sequence_WildcardOldNotation(context, (Wildcard) semanticObject); 
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
				sequence_ColonSepTypeRef_TStructGetter(context, (TStructGetter) semanticObject); 
				return; 
			case TypesPackage.TSTRUCT_INDEX_SIGNATURE:
				sequence_TStructIndexSignature(context, (TStructIndexSignature) semanticObject); 
				return; 
			case TypesPackage.TSTRUCT_METHOD:
				sequence_ColonSepReturnTypeRef_TAnonymousFormalParameterList_TStructMethod(context, (TStructMethod) semanticObject); 
				return; 
			case TypesPackage.TSTRUCT_SETTER:
				sequence_TStructSetter(context, (TStructSetter) semanticObject); 
				return; 
			case TypesPackage.TYPE_PREDICATE:
				if (rule == grammarAccess.getTypePredicateWithPrimaryRule()) {
					sequence_TypePredicateWithPrimary(context, (TypePredicate) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getTypePredicateRule()) {
					sequence_TypePredicate(context, (TypePredicate) semanticObject); 
					return; 
				}
				else break;
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
	 *     ConditionalTypeRef returns ParameterizedTypeRef
	 *     ConditionalTypeRef.ConditionalTypeRef_1_0_0_0 returns ParameterizedTypeRef
	 *     UnionTypeExpression returns ParameterizedTypeRef
	 *     UnionTypeExpression.UnionTypeExpression_1_0 returns ParameterizedTypeRef
	 *     IntersectionTypeExpression returns ParameterizedTypeRef
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns ParameterizedTypeRef
	 *     OperatorTypeRef returns ParameterizedTypeRef
	 *     ArrayTypeExpression returns ParameterizedTypeRef
	 *     ArrayTypeExpression.ParameterizedTypeRef_2_1_0_0_0 returns ParameterizedTypeRef
	 *     ArrayTypeExpression.IndexAccessTypeRef_2_1_0_1_0 returns ParameterizedTypeRef
	 *     PrimaryTypeExpression returns ParameterizedTypeRef
	 *     TypeArgument returns ParameterizedTypeRef
	 *
	 * Constraint:
	 *     (
	 *         (declaredTypeArgs+=WildcardOldNotationWithoutBound arrayTypeExpression?='[') | 
	 *         (declaredTypeArgs+=ArrayTypeExpression_ParameterizedTypeRef_0_4_0_0 arrayTypeExpression?='[') | 
	 *         (declaredTypeArgs+=Wildcard arrayTypeExpression?='[') | 
	 *         (declaredTypeArgs+=ArrayTypeExpression_ParameterizedTypeRef_1_6_0_0 arrayTypeExpression?='[') | 
	 *         (declaredTypeArgs+=ArrayTypeExpression_ParameterizedTypeRef_2_1_0_0_0 arrayTypeExpression?='[') | 
	 *         (
	 *             arrayNTypeExpression?='[' 
	 *             (declaredTypeArgs+=EmptyIterableTypeExpressionTail | (declaredTypeArgs+=TypeArgument declaredTypeArgs+=TypeArgument*))
	 *         ) | 
<<<<<<< HEAD
	 *         (
	 *             astNamespaceLikeRefs+=NamespaceLikeRef* 
	 *             declaredType=[Type|TypeReferenceName] 
	 *             (declaredTypeArgs+=TypeArgument declaredTypeArgs+=TypeArgument*)? 
	 *             dynamic?='+'? 
	 *             followedByQuestionMark?='?'?
	 *         )
=======
	 *         (declaredType=[Type|TypeReferenceName] (declaredTypeArgs+=TypeArgument declaredTypeArgs+=TypeArgument*)? dynamic?='+'?)
>>>>>>> a919ae797 (early support for DTS type references, except mapped types)
	 *     )
	 */
	protected void sequence_ArrayNTypeExpression_ArrayTypeExpression_TypeArguments_TypeRefWithModifiers_TypeReference(ISerializationContext context, ParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ArrayNTypeExpression returns ParameterizedTypeRef
	 *
	 * Constraint:
	 *     (
	 *         arrayNTypeExpression?='[' 
	 *         (declaredTypeArgs+=EmptyIterableTypeExpressionTail | (declaredTypeArgs+=TypeArgument declaredTypeArgs+=TypeArgument*))
	 *     )
	 */
	protected void sequence_ArrayNTypeExpression(ISerializationContext context, ParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
<<<<<<< HEAD
<<<<<<< HEAD
	 *     TypeRefFunctionTypeExpression returns ParameterizedTypeRef
	 *
	 * Constraint:
	 *     (
	 *         (
	 *             arrayNTypeExpression?='[' 
	 *             (declaredTypeArgs+=EmptyIterableTypeExpressionTail | (declaredTypeArgs+=TypeArgument declaredTypeArgs+=TypeArgument*))
	 *         ) | 
	 *         (
	 *             astNamespaceLikeRefs+=NamespaceLikeRef* 
	 *             declaredType=[Type|TypeReferenceName] 
	 *             (declaredTypeArgs+=TypeArgument declaredTypeArgs+=TypeArgument*)?
	 *         )
	 *     )
	 */
	protected void sequence_ArrayNTypeExpression_TypeArguments_TypeReference(ISerializationContext context, ParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
=======
	 *     TypeRef returns IndexAccessTypeRef
	 *     ConditionalTypeRef returns IndexAccessTypeRef
	 *     ConditionalTypeRef.ConditionalTypeRef_1_0_0_0 returns IndexAccessTypeRef
	 *     UnionTypeExpression returns IndexAccessTypeRef
	 *     UnionTypeExpression.UnionTypeExpression_1_0 returns IndexAccessTypeRef
	 *     IntersectionTypeExpression returns IndexAccessTypeRef
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns IndexAccessTypeRef
	 *     OperatorTypeRef returns IndexAccessTypeRef
	 *     ArrayTypeExpression returns IndexAccessTypeRef
	 *     ArrayTypeExpression.ParameterizedTypeRef_2_1_0_0_0 returns IndexAccessTypeRef
	 *     ArrayTypeExpression.IndexAccessTypeRef_2_1_0_1_0 returns IndexAccessTypeRef
	 *     PrimaryTypeExpression returns IndexAccessTypeRef
	 *     TypeArgument returns IndexAccessTypeRef
	 *
	 * Constraint:
	 *     (targetTypeRef=ArrayTypeExpression_IndexAccessTypeRef_2_1_0_1_0 indexTypeRef=TypeRef)
	 */
	protected void sequence_ArrayTypeExpression(ISerializationContext context, IndexAccessTypeRef semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, TypeRefsPackage.Literals.INDEX_ACCESS_TYPE_REF__TARGET_TYPE_REF) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TypeRefsPackage.Literals.INDEX_ACCESS_TYPE_REF__TARGET_TYPE_REF));
			if (transientValues.isValueTransient(semanticObject, TypeRefsPackage.Literals.INDEX_ACCESS_TYPE_REF__INDEX_TYPE_REF) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TypeRefsPackage.Literals.INDEX_ACCESS_TYPE_REF__INDEX_TYPE_REF));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getArrayTypeExpressionAccess().getIndexAccessTypeRefTargetTypeRefAction_2_1_0_1_0(), semanticObject.getTargetTypeRef());
		feeder.accept(grammarAccess.getArrayTypeExpressionAccess().getIndexTypeRefTypeRefParserRuleCall_2_1_0_1_2_0(), semanticObject.getIndexTypeRef());
		feeder.finish();
>>>>>>> a919ae797 (early support for DTS type references, except mapped types)
	}
	
	
	/**
	 * Contexts:
<<<<<<< HEAD
=======
>>>>>>> 8b8567bc8 (early support for a few first constructs)
=======
>>>>>>> a919ae797 (early support for DTS type references, except mapped types)
	 *     ArrayTypeExpression.ParameterizedTypeRef_0_4_0_0 returns ParameterizedTypeRef
	 *
	 * Constraint:
	 *     (
	 *         (declaredTypeArgs+=WildcardOldNotationWithoutBound arrayTypeExpression?='[') | 
	 *         (declaredTypeArgs+=ArrayTypeExpression_ParameterizedTypeRef_0_4_0_0 arrayTypeExpression?='[')
	 *     )
	 */
	protected void sequence_ArrayTypeExpression_ParameterizedTypeRef_0_4_0_0(ISerializationContext context, ParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ArrayTypeExpression.ParameterizedTypeRef_1_6_0_0 returns ParameterizedTypeRef
	 *
	 * Constraint:
	 *     (
	 *         (declaredTypeArgs+=Wildcard arrayTypeExpression?='[') | 
	 *         (declaredTypeArgs+=ArrayTypeExpression_ParameterizedTypeRef_1_6_0_0 arrayTypeExpression?='[')
	 *     )
	 */
	protected void sequence_ArrayTypeExpression_ParameterizedTypeRef_1_6_0_0(ISerializationContext context, ParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns FunctionTypeExpression
	 *     ConditionalTypeRef returns FunctionTypeExpression
	 *     ConditionalTypeRef.ConditionalTypeRef_1_0_0_0 returns FunctionTypeExpression
	 *     UnionTypeExpression returns FunctionTypeExpression
	 *     UnionTypeExpression.UnionTypeExpression_1_0 returns FunctionTypeExpression
	 *     IntersectionTypeExpression returns FunctionTypeExpression
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns FunctionTypeExpression
	 *     OperatorTypeRef returns FunctionTypeExpression
	 *     ArrayTypeExpression returns FunctionTypeExpression
	 *     ArrayTypeExpression.ParameterizedTypeRef_2_1_0_0_0 returns FunctionTypeExpression
	 *     ArrayTypeExpression.IndexAccessTypeRef_2_1_0_1_0 returns FunctionTypeExpression
	 *     PrimaryTypeExpression returns FunctionTypeExpression
	 *     ArrowFunctionTypeExpression returns FunctionTypeExpression
	 *     TypeArgument returns FunctionTypeExpression
	 *
	 * Constraint:
	 *     (
	 *         (dtsAbstract?='abstract'? dtsConstructor?='new')? 
	 *         (ownedTypeVars+=TypeVariable ownedTypeVars+=TypeVariable*)? 
	 *         ((declaredThisType=TypeRef | fpars+=TAnonymousFormalParameter) fpars+=TAnonymousFormalParameter*)? 
	 *         (returnTypePredicate=TypePredicateWithPrimary | returnTypeRef=PrimaryTypeExpression)
	 *     )
	 */
	protected void sequence_ArrowFunctionTypeExpression_TAnonymousFormalParameterListWithDeclaredThisType(ISerializationContext context, FunctionTypeExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns BooleanLiteralTypeRef
	 *     ConditionalTypeRef returns BooleanLiteralTypeRef
	 *     ConditionalTypeRef.ConditionalTypeRef_1_0_0_0 returns BooleanLiteralTypeRef
	 *     UnionTypeExpression returns BooleanLiteralTypeRef
	 *     UnionTypeExpression.UnionTypeExpression_1_0 returns BooleanLiteralTypeRef
	 *     IntersectionTypeExpression returns BooleanLiteralTypeRef
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns BooleanLiteralTypeRef
	 *     OperatorTypeRef returns BooleanLiteralTypeRef
	 *     ArrayTypeExpression returns BooleanLiteralTypeRef
	 *     ArrayTypeExpression.ParameterizedTypeRef_2_1_0_0_0 returns BooleanLiteralTypeRef
	 *     ArrayTypeExpression.IndexAccessTypeRef_2_1_0_1_0 returns BooleanLiteralTypeRef
	 *     PrimaryTypeExpression returns BooleanLiteralTypeRef
	 *     LiteralTypeRef returns BooleanLiteralTypeRef
	 *     BooleanLiteralTypeRef returns BooleanLiteralTypeRef
	 *     TypeArgument returns BooleanLiteralTypeRef
	 *
	 * Constraint:
	 *     (astValue='true' | astValue='false')
	 */
	protected void sequence_BooleanLiteralTypeRef(ISerializationContext context, BooleanLiteralTypeRef semanticObject) {
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
	 *         name=IdentifierName? 
	 *         (fpars+=TAnonymousFormalParameter fpars+=TAnonymousFormalParameter*)? 
	 *         (returnTypePredicate=TypePredicate | returnTypeRef=TypeRef)?
	 *     )
	 */
	protected void sequence_ColonSepReturnTypeRef_TAnonymousFormalParameterList_TStructMethod(ISerializationContext context, TStructMethod semanticObject) {
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
	 *     TStructMember returns TStructGetter
	 *     TStructGetter returns TStructGetter
	 *
	 * Constraint:
	 *     (name=IdentifierName optional?='?'? typeRef=TypeRef?)
	 */
	protected void sequence_ColonSepTypeRef_TStructGetter(ISerializationContext context, TStructGetter semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns ConditionalTypeRef
	 *     ConditionalTypeRef returns ConditionalTypeRef
	 *     ConditionalTypeRef.ConditionalTypeRef_1_0_0_0 returns ConditionalTypeRef
	 *     UnionTypeExpression returns ConditionalTypeRef
	 *     UnionTypeExpression.UnionTypeExpression_1_0 returns ConditionalTypeRef
	 *     IntersectionTypeExpression returns ConditionalTypeRef
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns ConditionalTypeRef
	 *     OperatorTypeRef returns ConditionalTypeRef
	 *     ArrayTypeExpression returns ConditionalTypeRef
	 *     ArrayTypeExpression.ParameterizedTypeRef_2_1_0_0_0 returns ConditionalTypeRef
	 *     ArrayTypeExpression.IndexAccessTypeRef_2_1_0_1_0 returns ConditionalTypeRef
	 *     PrimaryTypeExpression returns ConditionalTypeRef
	 *     TypeArgument returns ConditionalTypeRef
	 *
	 * Constraint:
	 *     (
	 *         typeRef=ConditionalTypeRef_ConditionalTypeRef_1_0_0_0 
	 *         upperBound=UnionTypeExpression 
	 *         trueTypeRef=ConditionalTypeRef 
	 *         falseTypeRef=ConditionalTypeRef
	 *     )
	 */
	protected void sequence_ConditionalTypeRef(ISerializationContext context, ConditionalTypeRef semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, TypeRefsPackage.Literals.CONDITIONAL_TYPE_REF__TYPE_REF) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TypeRefsPackage.Literals.CONDITIONAL_TYPE_REF__TYPE_REF));
			if (transientValues.isValueTransient(semanticObject, TypeRefsPackage.Literals.CONDITIONAL_TYPE_REF__UPPER_BOUND) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TypeRefsPackage.Literals.CONDITIONAL_TYPE_REF__UPPER_BOUND));
			if (transientValues.isValueTransient(semanticObject, TypeRefsPackage.Literals.CONDITIONAL_TYPE_REF__TRUE_TYPE_REF) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TypeRefsPackage.Literals.CONDITIONAL_TYPE_REF__TRUE_TYPE_REF));
			if (transientValues.isValueTransient(semanticObject, TypeRefsPackage.Literals.CONDITIONAL_TYPE_REF__FALSE_TYPE_REF) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TypeRefsPackage.Literals.CONDITIONAL_TYPE_REF__FALSE_TYPE_REF));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getConditionalTypeRefAccess().getConditionalTypeRefTypeRefAction_1_0_0_0(), semanticObject.getTypeRef());
		feeder.accept(grammarAccess.getConditionalTypeRefAccess().getUpperBoundUnionTypeExpressionParserRuleCall_1_1_0(), semanticObject.getUpperBound());
		feeder.accept(grammarAccess.getConditionalTypeRefAccess().getTrueTypeRefConditionalTypeRefParserRuleCall_1_3_0(), semanticObject.getTrueTypeRef());
		feeder.accept(grammarAccess.getConditionalTypeRefAccess().getFalseTypeRefConditionalTypeRefParserRuleCall_1_5_0(), semanticObject.getFalseTypeRef());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     EmptyIterableTypeExpressionTail returns Wildcard
	 *     WildcardOldNotationWithoutBound returns Wildcard
	 *
	 * Constraint:
	 *     {Wildcard}
	 */
	protected void sequence_EmptyIterableTypeExpressionTail_WildcardOldNotationWithoutBound(ISerializationContext context, Wildcard semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns InferTypeRef
	 *     ConditionalTypeRef returns InferTypeRef
	 *     ConditionalTypeRef.ConditionalTypeRef_1_0_0_0 returns InferTypeRef
	 *     UnionTypeExpression returns InferTypeRef
	 *     UnionTypeExpression.UnionTypeExpression_1_0 returns InferTypeRef
	 *     IntersectionTypeExpression returns InferTypeRef
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns InferTypeRef
	 *     OperatorTypeRef returns InferTypeRef
	 *     ArrayTypeExpression returns InferTypeRef
	 *     ArrayTypeExpression.ParameterizedTypeRef_2_1_0_0_0 returns InferTypeRef
	 *     ArrayTypeExpression.IndexAccessTypeRef_2_1_0_1_0 returns InferTypeRef
	 *     PrimaryTypeExpression returns InferTypeRef
	 *     TypeArgument returns InferTypeRef
	 *     InferTypeRef returns InferTypeRef
	 *
	 * Constraint:
	 *     typeVarName=IDENTIFIER
	 */
	protected void sequence_InferTypeRef(ISerializationContext context, InferTypeRef semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, TypeRefsPackage.Literals.INFER_TYPE_REF__TYPE_VAR_NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TypeRefsPackage.Literals.INFER_TYPE_REF__TYPE_VAR_NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getInferTypeRefAccess().getTypeVarNameIDENTIFIERTerminalRuleCall_1_0(), semanticObject.getTypeVarName());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefWithModifiers returns IntersectionTypeExpression
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
	 *     TypeRef returns IntersectionTypeExpression
	 *     ConditionalTypeRef returns IntersectionTypeExpression
	 *     ConditionalTypeRef.ConditionalTypeRef_1_0_0_0 returns IntersectionTypeExpression
	 *     UnionTypeExpression returns IntersectionTypeExpression
	 *     UnionTypeExpression.UnionTypeExpression_1_0 returns IntersectionTypeExpression
	 *     IntersectionTypeExpression returns IntersectionTypeExpression
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns IntersectionTypeExpression
	 *     OperatorTypeRef returns IntersectionTypeExpression
	 *     ArrayTypeExpression returns IntersectionTypeExpression
	 *     ArrayTypeExpression.ParameterizedTypeRef_2_1_0_0_0 returns IntersectionTypeExpression
	 *     ArrayTypeExpression.IndexAccessTypeRef_2_1_0_1_0 returns IntersectionTypeExpression
	 *     PrimaryTypeExpression returns IntersectionTypeExpression
	 *     TypeArgument returns IntersectionTypeExpression
	 *
	 * Constraint:
	 *     ((typeRefs+=IntersectionTypeExpression_IntersectionTypeExpression_1_0 typeRefs+=OperatorTypeRef+) | (typeRefs+=TypeRef typeRefs+=TypeRef*))
	 */
	protected void sequence_IntersectionTypeExpression_IntersectionTypeExpressionOLD(ISerializationContext context, IntersectionTypeExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
<<<<<<< HEAD
	 *     NamespaceLikeRef returns NamespaceLikeRef
	 *
	 * Constraint:
	 *     declaredType=[Type|TypeReferenceName]
	 */
	protected void sequence_NamespaceLikeRef(ISerializationContext context, NamespaceLikeRef semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, TypeRefsPackage.Literals.NAMESPACE_LIKE_REF__DECLARED_TYPE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TypeRefsPackage.Literals.NAMESPACE_LIKE_REF__DECLARED_TYPE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getNamespaceLikeRefAccess().getDeclaredTypeTypeTypeReferenceNameParserRuleCall_0_1(), semanticObject.eGet(TypeRefsPackage.Literals.NAMESPACE_LIKE_REF__DECLARED_TYPE, false));
		feeder.finish();
=======
	 *     TypeRef returns MappedTypeRef
	 *     ConditionalTypeRef returns MappedTypeRef
	 *     ConditionalTypeRef.ConditionalTypeRef_1_0_0_0 returns MappedTypeRef
	 *     UnionTypeExpression returns MappedTypeRef
	 *     UnionTypeExpression.UnionTypeExpression_1_0 returns MappedTypeRef
	 *     IntersectionTypeExpression returns MappedTypeRef
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns MappedTypeRef
	 *     OperatorTypeRef returns MappedTypeRef
	 *     ArrayTypeExpression returns MappedTypeRef
	 *     ArrayTypeExpression.ParameterizedTypeRef_2_1_0_0_0 returns MappedTypeRef
	 *     ArrayTypeExpression.IndexAccessTypeRef_2_1_0_1_0 returns MappedTypeRef
	 *     PrimaryTypeExpression returns MappedTypeRef
	 *     TypeRefWithModifiers returns MappedTypeRef
	 *     MappedTypeRef returns MappedTypeRef
	 *     TypeArgument returns MappedTypeRef
	 *
	 * Constraint:
	 *     (
	 *         (includeReadonly?='readonly' | excludeReadonly?='readonly')? 
	 *         propName=IdentifierName 
	 *         propNameTypeRef=TypeRef 
	 *         (includeOptional?='?' | excludeOptional?='?')? 
	 *         templateTypeRef=TypeRef?
	 *     )
	 */
	protected void sequence_MappedTypeRef(ISerializationContext context, MappedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
>>>>>>> 765b95ac8 (early support for mapped types)
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns NumericLiteralTypeRef
	 *     ConditionalTypeRef returns NumericLiteralTypeRef
	 *     ConditionalTypeRef.ConditionalTypeRef_1_0_0_0 returns NumericLiteralTypeRef
	 *     UnionTypeExpression returns NumericLiteralTypeRef
	 *     UnionTypeExpression.UnionTypeExpression_1_0 returns NumericLiteralTypeRef
	 *     IntersectionTypeExpression returns NumericLiteralTypeRef
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns NumericLiteralTypeRef
	 *     OperatorTypeRef returns NumericLiteralTypeRef
	 *     ArrayTypeExpression returns NumericLiteralTypeRef
	 *     ArrayTypeExpression.ParameterizedTypeRef_2_1_0_0_0 returns NumericLiteralTypeRef
	 *     ArrayTypeExpression.IndexAccessTypeRef_2_1_0_1_0 returns NumericLiteralTypeRef
	 *     PrimaryTypeExpression returns NumericLiteralTypeRef
	 *     LiteralTypeRef returns NumericLiteralTypeRef
	 *     NumericLiteralTypeRef returns NumericLiteralTypeRef
	 *     TypeArgument returns NumericLiteralTypeRef
	 *
	 * Constraint:
	 *     (
	 *         astNegated?='-'? 
	 *         (
	 *             astValue=INT | 
	 *             astValue=DOUBLE | 
	 *             astValue=OCTAL_INT | 
	 *             astValue=LEGACY_OCTAL_INT | 
	 *             astValue=HEX_INT | 
	 *             astValue=BINARY_INT | 
	 *             astValue=SCIENTIFIC_INT
	 *         )
	 *     )
	 */
	protected void sequence_NumericLiteralTypeRef(ISerializationContext context, NumericLiteralTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns OperatorTypeRef
	 *     ConditionalTypeRef returns OperatorTypeRef
	 *     ConditionalTypeRef.ConditionalTypeRef_1_0_0_0 returns OperatorTypeRef
	 *     UnionTypeExpression returns OperatorTypeRef
	 *     UnionTypeExpression.UnionTypeExpression_1_0 returns OperatorTypeRef
	 *     IntersectionTypeExpression returns OperatorTypeRef
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns OperatorTypeRef
	 *     OperatorTypeRef returns OperatorTypeRef
	 *     ArrayTypeExpression returns OperatorTypeRef
	 *     ArrayTypeExpression.ParameterizedTypeRef_2_1_0_0_0 returns OperatorTypeRef
	 *     ArrayTypeExpression.IndexAccessTypeRef_2_1_0_1_0 returns OperatorTypeRef
	 *     PrimaryTypeExpression returns OperatorTypeRef
	 *     TypeArgument returns OperatorTypeRef
	 *
	 * Constraint:
<<<<<<< HEAD
	 *     (
<<<<<<< HEAD
	 *         definedTypingStrategy=TypingStrategyUseSiteOperator 
	 *         astNamespaceLikeRefs+=NamespaceLikeRef* 
	 *         declaredType=[Type|TypeReferenceName] 
	 *         (declaredTypeArgs+=TypeArgument declaredTypeArgs+=TypeArgument*)? 
	 *         astStructuralMembers+=TStructMember* 
=======
	 *         (
	 *             astStructuralMembers+=TStructMember+ | 
	 *             (
	 *                 definedTypingStrategy=TypingStrategyUseSiteOperator 
	 *                 declaredType=[Type|TypeReferenceName] 
	 *                 (declaredTypeArgs+=TypeArgument declaredTypeArgs+=TypeArgument*)? 
	 *                 astStructuralMembers+=TStructMember*
	 *             )
	 *         )? 
>>>>>>> 8b8567bc8 (early support for a few first constructs)
	 *         dynamic?='+'? 
	 *         followedByQuestionMark?='?'?
	 *     )
=======
	 *     (op=TypeOperator typeRef=ArrayTypeExpression)
>>>>>>> a919ae797 (early support for DTS type references, except mapped types)
	 */
	protected void sequence_OperatorTypeRef(ISerializationContext context, OperatorTypeRef semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, TypeRefsPackage.Literals.OPERATOR_TYPE_REF__OP) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TypeRefsPackage.Literals.OPERATOR_TYPE_REF__OP));
			if (transientValues.isValueTransient(semanticObject, TypeRefsPackage.Literals.OPERATOR_TYPE_REF__TYPE_REF) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TypeRefsPackage.Literals.OPERATOR_TYPE_REF__TYPE_REF));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getOperatorTypeRefAccess().getOpTypeOperatorEnumRuleCall_0_1_0(), semanticObject.getOp());
		feeder.accept(grammarAccess.getOperatorTypeRefAccess().getTypeRefArrayTypeExpressionParserRuleCall_0_2_0(), semanticObject.getTypeRef());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns ParameterizedTypeRefStructural
	 *     ConditionalTypeRef returns ParameterizedTypeRefStructural
	 *     ConditionalTypeRef.ConditionalTypeRef_1_0_0_0 returns ParameterizedTypeRefStructural
	 *     UnionTypeExpression returns ParameterizedTypeRefStructural
	 *     UnionTypeExpression.UnionTypeExpression_1_0 returns ParameterizedTypeRefStructural
	 *     IntersectionTypeExpression returns ParameterizedTypeRefStructural
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns ParameterizedTypeRefStructural
	 *     OperatorTypeRef returns ParameterizedTypeRefStructural
	 *     ArrayTypeExpression returns ParameterizedTypeRefStructural
	 *     ArrayTypeExpression.ParameterizedTypeRef_2_1_0_0_0 returns ParameterizedTypeRefStructural
	 *     ArrayTypeExpression.IndexAccessTypeRef_2_1_0_1_0 returns ParameterizedTypeRefStructural
	 *     PrimaryTypeExpression returns ParameterizedTypeRefStructural
	 *     TypeRefWithModifiers returns ParameterizedTypeRefStructural
	 *     TypeArgument returns ParameterizedTypeRefStructural
	 *
	 * Constraint:
	 *     (
<<<<<<< HEAD
	 *         definedTypingStrategy=TypingStrategyUseSiteOperator 
	 *         astNamespaceLikeRefs+=NamespaceLikeRef* 
	 *         declaredType=[Type|TypeReferenceName] 
	 *         (declaredTypeArgs+=TypeArgument declaredTypeArgs+=TypeArgument*)? 
	 *         astStructuralMembers+=TStructMember* 
=======
	 *         (
	 *             astStructuralMembers+=TStructMember+ | 
	 *             (
	 *                 definedTypingStrategy=TypingStrategyUseSiteOperator 
	 *                 declaredType=[Type|TypeReferenceName] 
	 *                 (declaredTypeArgs+=TypeArgument declaredTypeArgs+=TypeArgument*)? 
	 *                 astStructuralMembers+=TStructMember*
	 *             )
	 *         )? 
>>>>>>> 8b8567bc8 (early support for a few first constructs)
	 *         dynamic?='+'?
	 *     )
	 */
	protected void sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeRefWithModifiers_TypeReference(ISerializationContext context, ParameterizedTypeRefStructural semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     ParameterizedTypeRef returns ParameterizedTypeRefStructural
	 *     ParameterizedTypeRefStructural returns ParameterizedTypeRefStructural
	 *
	 * Constraint:
	 *     (
<<<<<<< HEAD
	 *         definedTypingStrategy=TypingStrategyUseSiteOperator 
	 *         astNamespaceLikeRefs+=NamespaceLikeRef* 
	 *         declaredType=[Type|TypeReferenceName] 
	 *         (declaredTypeArgs+=TypeArgument declaredTypeArgs+=TypeArgument*)? 
	 *         astStructuralMembers+=TStructMember*
	 *     )
=======
	 *         astStructuralMembers+=TStructMember+ | 
	 *         (
	 *             definedTypingStrategy=TypingStrategyUseSiteOperator 
	 *             declaredType=[Type|TypeReferenceName] 
	 *             (declaredTypeArgs+=TypeArgument declaredTypeArgs+=TypeArgument*)? 
	 *             astStructuralMembers+=TStructMember*
	 *         )
	 *     )?
>>>>>>> 8b8567bc8 (early support for a few first constructs)
	 */
	protected void sequence_ParameterizedTypeRefStructural_TStructMemberList_TypeArguments_TypeReference(ISerializationContext context, ParameterizedTypeRefStructural semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns QueryTypeRef
	 *     ConditionalTypeRef returns QueryTypeRef
	 *     ConditionalTypeRef.ConditionalTypeRef_1_0_0_0 returns QueryTypeRef
	 *     UnionTypeExpression returns QueryTypeRef
	 *     UnionTypeExpression.UnionTypeExpression_1_0 returns QueryTypeRef
	 *     IntersectionTypeExpression returns QueryTypeRef
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns QueryTypeRef
	 *     OperatorTypeRef returns QueryTypeRef
	 *     ArrayTypeExpression returns QueryTypeRef
	 *     ArrayTypeExpression.ParameterizedTypeRef_2_1_0_0_0 returns QueryTypeRef
	 *     ArrayTypeExpression.IndexAccessTypeRef_2_1_0_1_0 returns QueryTypeRef
	 *     PrimaryTypeExpression returns QueryTypeRef
	 *     TypeArgument returns QueryTypeRef
	 *     QueryTypeRef returns QueryTypeRef
	 *
	 * Constraint:
	 *     element=[IdentifiableElement|IdentifierName]
	 */
	protected void sequence_QueryTypeRef(ISerializationContext context, QueryTypeRef semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, TypeRefsPackage.Literals.QUERY_TYPE_REF__ELEMENT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TypeRefsPackage.Literals.QUERY_TYPE_REF__ELEMENT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getQueryTypeRefAccess().getElementIdentifiableElementIdentifierNameParserRuleCall_1_0_1(), semanticObject.eGet(TypeRefsPackage.Literals.QUERY_TYPE_REF__ELEMENT, false));
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns StringLiteralTypeRef
	 *     ConditionalTypeRef returns StringLiteralTypeRef
	 *     ConditionalTypeRef.ConditionalTypeRef_1_0_0_0 returns StringLiteralTypeRef
	 *     UnionTypeExpression returns StringLiteralTypeRef
	 *     UnionTypeExpression.UnionTypeExpression_1_0 returns StringLiteralTypeRef
	 *     IntersectionTypeExpression returns StringLiteralTypeRef
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns StringLiteralTypeRef
	 *     OperatorTypeRef returns StringLiteralTypeRef
	 *     ArrayTypeExpression returns StringLiteralTypeRef
	 *     ArrayTypeExpression.ParameterizedTypeRef_2_1_0_0_0 returns StringLiteralTypeRef
	 *     ArrayTypeExpression.IndexAccessTypeRef_2_1_0_1_0 returns StringLiteralTypeRef
	 *     PrimaryTypeExpression returns StringLiteralTypeRef
	 *     LiteralTypeRef returns StringLiteralTypeRef
	 *     StringLiteralTypeRef returns StringLiteralTypeRef
	 *     TypeArgument returns StringLiteralTypeRef
	 *
	 * Constraint:
	 *     astValue=STRING
	 */
	protected void sequence_StringLiteralTypeRef(ISerializationContext context, StringLiteralTypeRef semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, TypeRefsPackage.Literals.LITERAL_TYPE_REF__AST_VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, TypeRefsPackage.Literals.LITERAL_TYPE_REF__AST_VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getStringLiteralTypeRefAccess().getAstValueSTRINGTerminalRuleCall_0(), semanticObject.getAstValue());
		feeder.finish();
	}
	
	
	/**
	 * Contexts:
	 *     TStructMember returns TStructIndexSignature
	 *     TStructIndexSignature returns TStructIndexSignature
	 *
	 * Constraint:
	 *     (readonly?='readonly'? keyName=IdentifierName keyTypeRef=TypeRef valueTypeRef=TypeRef)
	 */
	protected void sequence_TStructIndexSignature(ISerializationContext context, TStructIndexSignature semanticObject) {
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
	 *     ConditionalTypeRef returns ThisTypeRefStructural
	 *     ConditionalTypeRef.ConditionalTypeRef_1_0_0_0 returns ThisTypeRefStructural
	 *     UnionTypeExpression returns ThisTypeRefStructural
	 *     UnionTypeExpression.UnionTypeExpression_1_0 returns ThisTypeRefStructural
	 *     IntersectionTypeExpression returns ThisTypeRefStructural
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns ThisTypeRefStructural
	 *     OperatorTypeRef returns ThisTypeRefStructural
	 *     ArrayTypeExpression returns ThisTypeRefStructural
	 *     ArrayTypeExpression.ParameterizedTypeRef_2_1_0_0_0 returns ThisTypeRefStructural
	 *     ArrayTypeExpression.IndexAccessTypeRef_2_1_0_1_0 returns ThisTypeRefStructural
	 *     PrimaryTypeExpression returns ThisTypeRefStructural
	 *     TypeRefWithModifiers returns ThisTypeRefStructural
	 *     TypeArgument returns ThisTypeRefStructural
	 *
	 * Constraint:
	 *     (definedTypingStrategy=TypingStrategyUseSiteOperator astStructuralMembers+=TStructMember* dynamic?='+'?)
	 */
	protected void sequence_TStructMemberList_ThisTypeRefStructural_TypeRefWithModifiers(ISerializationContext context, ThisTypeRefStructural semanticObject) {
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
	 *     ConditionalTypeRef returns ThisTypeRefNominal
	 *     ConditionalTypeRef.ConditionalTypeRef_1_0_0_0 returns ThisTypeRefNominal
	 *     UnionTypeExpression returns ThisTypeRefNominal
	 *     UnionTypeExpression.UnionTypeExpression_1_0 returns ThisTypeRefNominal
	 *     IntersectionTypeExpression returns ThisTypeRefNominal
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns ThisTypeRefNominal
	 *     OperatorTypeRef returns ThisTypeRefNominal
	 *     ArrayTypeExpression returns ThisTypeRefNominal
	 *     ArrayTypeExpression.ParameterizedTypeRef_2_1_0_0_0 returns ThisTypeRefNominal
	 *     ArrayTypeExpression.IndexAccessTypeRef_2_1_0_1_0 returns ThisTypeRefNominal
	 *     PrimaryTypeExpression returns ThisTypeRefNominal
	 *     TypeRefWithModifiers returns ThisTypeRefNominal
	 *     TypeArgument returns ThisTypeRefNominal
	 *
	 * Constraint:
	 *     dynamic?='+'?
	 */
	protected void sequence_ThisTypeRefNominal_TypeRefWithModifiers(ISerializationContext context, ThisTypeRefNominal semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefWithModifiers returns ParameterizedTypeRef
	 *
	 * Constraint:
<<<<<<< HEAD
	 *     (
	 *         astNamespaceLikeRefs+=NamespaceLikeRef* 
	 *         declaredType=[Type|TypeReferenceName] 
	 *         (declaredTypeArgs+=TypeArgument declaredTypeArgs+=TypeArgument*)? 
	 *         dynamic?='+'? 
	 *         followedByQuestionMark?='?'?
	 *     )
	 */
	protected void sequence_TypeArguments_TypeRefWithModifiers_TypeRefWithoutModifiers_TypeReference(ISerializationContext context, ParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefWithoutModifiers returns ParameterizedTypeRef
	 *
	 * Constraint:
	 *     (
	 *         astNamespaceLikeRefs+=NamespaceLikeRef* 
	 *         declaredType=[Type|TypeReferenceName] 
	 *         (declaredTypeArgs+=TypeArgument declaredTypeArgs+=TypeArgument*)? 
	 *         dynamic?='+'?
	 *     )
=======
	 *     (declaredType=[Type|TypeReferenceName] (declaredTypeArgs+=TypeArgument declaredTypeArgs+=TypeArgument*)? dynamic?='+'?)
>>>>>>> a919ae797 (early support for DTS type references, except mapped types)
	 */
	protected void sequence_TypeArguments_TypeRefWithModifiers_TypeReference(ISerializationContext context, ParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeArgInTypeTypeRef returns ParameterizedTypeRef
	 *     ParameterizedTypeRef returns ParameterizedTypeRef
	 *     ParameterizedTypeRefNominal returns ParameterizedTypeRef
	 *
	 * Constraint:
	 *     (
	 *         astNamespaceLikeRefs+=NamespaceLikeRef* 
	 *         declaredType=[Type|TypeReferenceName] 
	 *         (declaredTypeArgs+=TypeArgument declaredTypeArgs+=TypeArgument*)?
	 *     )
	 */
	protected void sequence_TypeArguments_TypeReference(ISerializationContext context, ParameterizedTypeRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypePredicateWithPrimary returns TypePredicate
	 *
	 * Constraint:
	 *     ((referringToThis?='this' | fpar=[IdentifiableElement|BindingIdentifier]) typeRef=PrimaryTypeExpression)
	 */
	protected void sequence_TypePredicateWithPrimary(ISerializationContext context, TypePredicate semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypePredicate returns TypePredicate
	 *
	 * Constraint:
	 *     ((referringToThis?='this' | fpar=[IdentifiableElement|BindingIdentifier]) typeRef=TypeRef)
	 */
	protected void sequence_TypePredicate(ISerializationContext context, TypePredicate semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRef returns TypeTypeRef
	 *     ConditionalTypeRef returns TypeTypeRef
	 *     ConditionalTypeRef.ConditionalTypeRef_1_0_0_0 returns TypeTypeRef
	 *     UnionTypeExpression returns TypeTypeRef
	 *     UnionTypeExpression.UnionTypeExpression_1_0 returns TypeTypeRef
	 *     IntersectionTypeExpression returns TypeTypeRef
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns TypeTypeRef
	 *     OperatorTypeRef returns TypeTypeRef
	 *     ArrayTypeExpression returns TypeTypeRef
	 *     ArrayTypeExpression.ParameterizedTypeRef_2_1_0_0_0 returns TypeTypeRef
	 *     ArrayTypeExpression.IndexAccessTypeRef_2_1_0_1_0 returns TypeTypeRef
	 *     PrimaryTypeExpression returns TypeTypeRef
	 *     TypeRefWithModifiers returns TypeTypeRef
	 *     TypeTypeRef returns TypeTypeRef
	 *     TypeArgument returns TypeTypeRef
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
	 *     ((declaredCovariant?='out' | declaredContravariant?='in')? name=IDENTIFIER declaredUpperBound=TypeRef? defaultArgument=TypeRef?)
	 */
	protected void sequence_TypeVariable(ISerializationContext context, TypeVariable semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeRefWithModifiers returns UnionTypeExpression
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
	 *     TypeRef returns UnionTypeExpression
	 *     ConditionalTypeRef returns UnionTypeExpression
	 *     ConditionalTypeRef.ConditionalTypeRef_1_0_0_0 returns UnionTypeExpression
	 *     UnionTypeExpression returns UnionTypeExpression
	 *     UnionTypeExpression.UnionTypeExpression_1_0 returns UnionTypeExpression
	 *     IntersectionTypeExpression returns UnionTypeExpression
	 *     IntersectionTypeExpression.IntersectionTypeExpression_1_0 returns UnionTypeExpression
	 *     OperatorTypeRef returns UnionTypeExpression
	 *     ArrayTypeExpression returns UnionTypeExpression
	 *     ArrayTypeExpression.ParameterizedTypeRef_2_1_0_0_0 returns UnionTypeExpression
	 *     ArrayTypeExpression.IndexAccessTypeRef_2_1_0_1_0 returns UnionTypeExpression
	 *     PrimaryTypeExpression returns UnionTypeExpression
	 *     TypeArgument returns UnionTypeExpression
	 *
	 * Constraint:
	 *     ((typeRefs+=UnionTypeExpression_UnionTypeExpression_1_0 typeRefs+=IntersectionTypeExpression+) | (typeRefs+=TypeRef typeRefs+=TypeRef*))
	 */
	protected void sequence_UnionTypeExpression_UnionTypeExpressionOLD(ISerializationContext context, UnionTypeExpression semanticObject) {
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
	 *     TypeArgument returns Wildcard
	 *     Wildcard returns Wildcard
	 *
	 * Constraint:
	 *     (
	 *         declaredUpperBound=TypeRef | 
	 *         declaredLowerBound=TypeRef | 
	 *         (usingInOutNotation?='out' declaredUpperBound=TypeRef) | 
	 *         (usingInOutNotation?='in' declaredLowerBound=TypeRef)
	 *     )?
	 */
	protected void sequence_WildcardNewNotation_WildcardOldNotation(ISerializationContext context, Wildcard semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     TypeArgInTypeTypeRef returns Wildcard
	 *     WildcardOldNotation returns Wildcard
	 *
	 * Constraint:
	 *     (declaredUpperBound=TypeRef | declaredLowerBound=TypeRef)?
	 */
	protected void sequence_WildcardOldNotation(ISerializationContext context, Wildcard semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
