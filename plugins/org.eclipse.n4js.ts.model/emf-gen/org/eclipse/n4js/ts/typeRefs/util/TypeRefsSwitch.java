/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ts.typeRefs.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.n4js.ts.typeRefs.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
 * @generated
 */
public class TypeRefsSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TypeRefsPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeRefsSwitch() {
		if (modelPackage == null) {
			modelPackage = TypeRefsPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case TypeRefsPackage.TYPE_ARGUMENT: {
				TypeArgument typeArgument = (TypeArgument)theEObject;
				T result = caseTypeArgument(typeArgument);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.TYPE_REF: {
				TypeRef typeRef = (TypeRef)theEObject;
				T result = caseTypeRef(typeRef);
				if (result == null) result = caseTypeArgument(typeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.STATIC_BASE_TYPE_REF: {
				StaticBaseTypeRef staticBaseTypeRef = (StaticBaseTypeRef)theEObject;
				T result = caseStaticBaseTypeRef(staticBaseTypeRef);
				if (result == null) result = caseTypeRef(staticBaseTypeRef);
				if (result == null) result = caseTypeArgument(staticBaseTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.BASE_TYPE_REF: {
				BaseTypeRef baseTypeRef = (BaseTypeRef)theEObject;
				T result = caseBaseTypeRef(baseTypeRef);
				if (result == null) result = caseStaticBaseTypeRef(baseTypeRef);
				if (result == null) result = caseTypeRef(baseTypeRef);
				if (result == null) result = caseTypeArgument(baseTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.COMPOSED_TYPE_REF: {
				ComposedTypeRef composedTypeRef = (ComposedTypeRef)theEObject;
				T result = caseComposedTypeRef(composedTypeRef);
				if (result == null) result = caseStaticBaseTypeRef(composedTypeRef);
				if (result == null) result = caseTypeRef(composedTypeRef);
				if (result == null) result = caseTypeArgument(composedTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.UNION_TYPE_EXPRESSION: {
				UnionTypeExpression unionTypeExpression = (UnionTypeExpression)theEObject;
				T result = caseUnionTypeExpression(unionTypeExpression);
				if (result == null) result = caseComposedTypeRef(unionTypeExpression);
				if (result == null) result = caseStaticBaseTypeRef(unionTypeExpression);
				if (result == null) result = caseTypeRef(unionTypeExpression);
				if (result == null) result = caseTypeArgument(unionTypeExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.INTERSECTION_TYPE_EXPRESSION: {
				IntersectionTypeExpression intersectionTypeExpression = (IntersectionTypeExpression)theEObject;
				T result = caseIntersectionTypeExpression(intersectionTypeExpression);
				if (result == null) result = caseComposedTypeRef(intersectionTypeExpression);
				if (result == null) result = caseStaticBaseTypeRef(intersectionTypeExpression);
				if (result == null) result = caseTypeRef(intersectionTypeExpression);
				if (result == null) result = caseTypeArgument(intersectionTypeExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.THIS_TYPE_REF: {
				ThisTypeRef thisTypeRef = (ThisTypeRef)theEObject;
				T result = caseThisTypeRef(thisTypeRef);
				if (result == null) result = caseBaseTypeRef(thisTypeRef);
				if (result == null) result = caseStaticBaseTypeRef(thisTypeRef);
				if (result == null) result = caseTypeRef(thisTypeRef);
				if (result == null) result = caseTypeArgument(thisTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.THIS_TYPE_REF_NOMINAL: {
				ThisTypeRefNominal thisTypeRefNominal = (ThisTypeRefNominal)theEObject;
				T result = caseThisTypeRefNominal(thisTypeRefNominal);
				if (result == null) result = caseThisTypeRef(thisTypeRefNominal);
				if (result == null) result = caseBaseTypeRef(thisTypeRefNominal);
				if (result == null) result = caseStaticBaseTypeRef(thisTypeRefNominal);
				if (result == null) result = caseTypeRef(thisTypeRefNominal);
				if (result == null) result = caseTypeArgument(thisTypeRefNominal);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL: {
				ThisTypeRefStructural thisTypeRefStructural = (ThisTypeRefStructural)theEObject;
				T result = caseThisTypeRefStructural(thisTypeRefStructural);
				if (result == null) result = caseThisTypeRef(thisTypeRefStructural);
				if (result == null) result = caseStructuralTypeRef(thisTypeRefStructural);
				if (result == null) result = caseBaseTypeRef(thisTypeRefStructural);
				if (result == null) result = caseStaticBaseTypeRef(thisTypeRefStructural);
				if (result == null) result = caseTypeRef(thisTypeRefStructural);
				if (result == null) result = caseTypeArgument(thisTypeRefStructural);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.BOUND_THIS_TYPE_REF: {
				BoundThisTypeRef boundThisTypeRef = (BoundThisTypeRef)theEObject;
				T result = caseBoundThisTypeRef(boundThisTypeRef);
				if (result == null) result = caseThisTypeRef(boundThisTypeRef);
				if (result == null) result = caseStructuralTypeRef(boundThisTypeRef);
				if (result == null) result = caseBaseTypeRef(boundThisTypeRef);
				if (result == null) result = caseStaticBaseTypeRef(boundThisTypeRef);
				if (result == null) result = caseTypeRef(boundThisTypeRef);
				if (result == null) result = caseTypeArgument(boundThisTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF: {
				ParameterizedTypeRef parameterizedTypeRef = (ParameterizedTypeRef)theEObject;
				T result = caseParameterizedTypeRef(parameterizedTypeRef);
				if (result == null) result = caseBaseTypeRef(parameterizedTypeRef);
				if (result == null) result = caseStaticBaseTypeRef(parameterizedTypeRef);
				if (result == null) result = caseTypeRef(parameterizedTypeRef);
				if (result == null) result = caseTypeArgument(parameterizedTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.STRUCTURAL_TYPE_REF: {
				StructuralTypeRef structuralTypeRef = (StructuralTypeRef)theEObject;
				T result = caseStructuralTypeRef(structuralTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL: {
				ParameterizedTypeRefStructural parameterizedTypeRefStructural = (ParameterizedTypeRefStructural)theEObject;
				T result = caseParameterizedTypeRefStructural(parameterizedTypeRefStructural);
				if (result == null) result = caseParameterizedTypeRef(parameterizedTypeRefStructural);
				if (result == null) result = caseStructuralTypeRef(parameterizedTypeRefStructural);
				if (result == null) result = caseBaseTypeRef(parameterizedTypeRefStructural);
				if (result == null) result = caseStaticBaseTypeRef(parameterizedTypeRefStructural);
				if (result == null) result = caseTypeRef(parameterizedTypeRefStructural);
				if (result == null) result = caseTypeArgument(parameterizedTypeRefStructural);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.EXISTENTIAL_TYPE_REF: {
				ExistentialTypeRef existentialTypeRef = (ExistentialTypeRef)theEObject;
				T result = caseExistentialTypeRef(existentialTypeRef);
				if (result == null) result = caseTypeRef(existentialTypeRef);
				if (result == null) result = caseTypeArgument(existentialTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.UNKNOWN_TYPE_REF: {
				UnknownTypeRef unknownTypeRef = (UnknownTypeRef)theEObject;
				T result = caseUnknownTypeRef(unknownTypeRef);
				if (result == null) result = caseTypeRef(unknownTypeRef);
				if (result == null) result = caseTypeArgument(unknownTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.TYPE_TYPE_REF: {
				TypeTypeRef typeTypeRef = (TypeTypeRef)theEObject;
				T result = caseTypeTypeRef(typeTypeRef);
				if (result == null) result = caseBaseTypeRef(typeTypeRef);
				if (result == null) result = caseStaticBaseTypeRef(typeTypeRef);
				if (result == null) result = caseTypeRef(typeTypeRef);
				if (result == null) result = caseTypeArgument(typeTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.NAMESPACE_LIKE_REF: {
				NamespaceLikeRef namespaceLikeRef = (NamespaceLikeRef)theEObject;
				T result = caseNamespaceLikeRef(namespaceLikeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.WILDCARD: {
				Wildcard wildcard = (Wildcard)theEObject;
				T result = caseWildcard(wildcard);
				if (result == null) result = caseTypeArgument(wildcard);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF: {
				FunctionTypeExprOrRef functionTypeExprOrRef = (FunctionTypeExprOrRef)theEObject;
				T result = caseFunctionTypeExprOrRef(functionTypeExprOrRef);
				if (result == null) result = caseStaticBaseTypeRef(functionTypeExprOrRef);
				if (result == null) result = caseTypeRef(functionTypeExprOrRef);
				if (result == null) result = caseTypeArgument(functionTypeExprOrRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.FUNCTION_TYPE_REF: {
				FunctionTypeRef functionTypeRef = (FunctionTypeRef)theEObject;
				T result = caseFunctionTypeRef(functionTypeRef);
				if (result == null) result = caseParameterizedTypeRef(functionTypeRef);
				if (result == null) result = caseFunctionTypeExprOrRef(functionTypeRef);
				if (result == null) result = caseBaseTypeRef(functionTypeRef);
				if (result == null) result = caseStaticBaseTypeRef(functionTypeRef);
				if (result == null) result = caseTypeRef(functionTypeRef);
				if (result == null) result = caseTypeArgument(functionTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION: {
				FunctionTypeExpression functionTypeExpression = (FunctionTypeExpression)theEObject;
				T result = caseFunctionTypeExpression(functionTypeExpression);
				if (result == null) result = caseFunctionTypeExprOrRef(functionTypeExpression);
				if (result == null) result = caseStaticBaseTypeRef(functionTypeExpression);
				if (result == null) result = caseTypeRef(functionTypeExpression);
				if (result == null) result = caseTypeArgument(functionTypeExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.DEFERRED_TYPE_REF: {
				DeferredTypeRef deferredTypeRef = (DeferredTypeRef)theEObject;
				T result = caseDeferredTypeRef(deferredTypeRef);
				if (result == null) result = caseTypeRef(deferredTypeRef);
				if (result == null) result = caseTypeArgument(deferredTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.TYPE_VARIABLE_MAPPING: {
				TypeVariableMapping typeVariableMapping = (TypeVariableMapping)theEObject;
				T result = caseTypeVariableMapping(typeVariableMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.LITERAL_TYPE_REF: {
				LiteralTypeRef literalTypeRef = (LiteralTypeRef)theEObject;
				T result = caseLiteralTypeRef(literalTypeRef);
				if (result == null) result = caseTypeRef(literalTypeRef);
				if (result == null) result = caseTypeArgument(literalTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.BOOLEAN_LITERAL_TYPE_REF: {
				BooleanLiteralTypeRef booleanLiteralTypeRef = (BooleanLiteralTypeRef)theEObject;
				T result = caseBooleanLiteralTypeRef(booleanLiteralTypeRef);
				if (result == null) result = caseLiteralTypeRef(booleanLiteralTypeRef);
				if (result == null) result = caseTypeRef(booleanLiteralTypeRef);
				if (result == null) result = caseTypeArgument(booleanLiteralTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.NUMERIC_LITERAL_TYPE_REF: {
				NumericLiteralTypeRef numericLiteralTypeRef = (NumericLiteralTypeRef)theEObject;
				T result = caseNumericLiteralTypeRef(numericLiteralTypeRef);
				if (result == null) result = caseLiteralTypeRef(numericLiteralTypeRef);
				if (result == null) result = caseTypeRef(numericLiteralTypeRef);
				if (result == null) result = caseTypeArgument(numericLiteralTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.STRING_LITERAL_TYPE_REF: {
				StringLiteralTypeRef stringLiteralTypeRef = (StringLiteralTypeRef)theEObject;
				T result = caseStringLiteralTypeRef(stringLiteralTypeRef);
				if (result == null) result = caseLiteralTypeRef(stringLiteralTypeRef);
				if (result == null) result = caseTypeRef(stringLiteralTypeRef);
				if (result == null) result = caseTypeArgument(stringLiteralTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.ENUM_LITERAL_TYPE_REF: {
				EnumLiteralTypeRef enumLiteralTypeRef = (EnumLiteralTypeRef)theEObject;
				T result = caseEnumLiteralTypeRef(enumLiteralTypeRef);
				if (result == null) result = caseLiteralTypeRef(enumLiteralTypeRef);
				if (result == null) result = caseTypeRef(enumLiteralTypeRef);
				if (result == null) result = caseTypeArgument(enumLiteralTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.QUERY_TYPE_REF: {
				QueryTypeRef queryTypeRef = (QueryTypeRef)theEObject;
				T result = caseQueryTypeRef(queryTypeRef);
				if (result == null) result = caseTypeRef(queryTypeRef);
				if (result == null) result = caseTypeArgument(queryTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.OPERATOR_TYPE_REF: {
				OperatorTypeRef operatorTypeRef = (OperatorTypeRef)theEObject;
				T result = caseOperatorTypeRef(operatorTypeRef);
				if (result == null) result = caseTypeRef(operatorTypeRef);
				if (result == null) result = caseTypeArgument(operatorTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.INDEX_ACCESS_TYPE_REF: {
				IndexAccessTypeRef indexAccessTypeRef = (IndexAccessTypeRef)theEObject;
				T result = caseIndexAccessTypeRef(indexAccessTypeRef);
				if (result == null) result = caseTypeRef(indexAccessTypeRef);
				if (result == null) result = caseTypeArgument(indexAccessTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.CONDITIONAL_TYPE_REF: {
				ConditionalTypeRef conditionalTypeRef = (ConditionalTypeRef)theEObject;
				T result = caseConditionalTypeRef(conditionalTypeRef);
				if (result == null) result = caseTypeRef(conditionalTypeRef);
				if (result == null) result = caseTypeArgument(conditionalTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.INFER_TYPE_REF: {
				InferTypeRef inferTypeRef = (InferTypeRef)theEObject;
				T result = caseInferTypeRef(inferTypeRef);
				if (result == null) result = caseTypeRef(inferTypeRef);
				if (result == null) result = caseTypeArgument(inferTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.MAPPED_TYPE_REF: {
				MappedTypeRef mappedTypeRef = (MappedTypeRef)theEObject;
				T result = caseMappedTypeRef(mappedTypeRef);
				if (result == null) result = caseTypeRef(mappedTypeRef);
				if (result == null) result = caseTypeArgument(mappedTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Argument</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Argument</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypeArgument(TypeArgument object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypeRef(TypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Static Base Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Static Base Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStaticBaseTypeRef(StaticBaseTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Base Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Base Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBaseTypeRef(BaseTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composed Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composed Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComposedTypeRef(ComposedTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Union Type Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Union Type Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnionTypeExpression(UnionTypeExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Intersection Type Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Intersection Type Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntersectionTypeExpression(IntersectionTypeExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>This Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>This Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseThisTypeRef(ThisTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>This Type Ref Nominal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>This Type Ref Nominal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseThisTypeRefNominal(ThisTypeRefNominal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>This Type Ref Structural</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>This Type Ref Structural</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseThisTypeRefStructural(ThisTypeRefStructural object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bound This Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bound This Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBoundThisTypeRef(BoundThisTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameterized Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameterized Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParameterizedTypeRef(ParameterizedTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Structural Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Structural Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStructuralTypeRef(StructuralTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameterized Type Ref Structural</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameterized Type Ref Structural</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParameterizedTypeRefStructural(ParameterizedTypeRefStructural object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Existential Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Existential Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExistentialTypeRef(ExistentialTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unknown Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unknown Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnknownTypeRef(UnknownTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypeTypeRef(TypeTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Namespace Like Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Namespace Like Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamespaceLikeRef(NamespaceLikeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Wildcard</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Wildcard</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWildcard(Wildcard object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function Type Expr Or Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function Type Expr Or Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionTypeExprOrRef(FunctionTypeExprOrRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionTypeRef(FunctionTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function Type Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function Type Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionTypeExpression(FunctionTypeExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Deferred Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Deferred Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeferredTypeRef(DeferredTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Variable Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Variable Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypeVariableMapping(TypeVariableMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Literal Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Literal Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLiteralTypeRef(LiteralTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Literal Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Literal Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBooleanLiteralTypeRef(BooleanLiteralTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Numeric Literal Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Numeric Literal Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNumericLiteralTypeRef(NumericLiteralTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Literal Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Literal Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringLiteralTypeRef(StringLiteralTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enum Literal Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enum Literal Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnumLiteralTypeRef(EnumLiteralTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Query Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Query Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQueryTypeRef(QueryTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Operator Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Operator Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOperatorTypeRef(OperatorTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Index Access Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Index Access Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIndexAccessTypeRef(IndexAccessTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Conditional Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Conditional Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConditionalTypeRef(ConditionalTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Infer Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Infer Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInferTypeRef(InferTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mapped Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mapped Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMappedTypeRef(MappedTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //TypeRefsSwitch
