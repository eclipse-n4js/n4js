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
			case TypeRefsPackage.VERSIONABLE: {
				Versionable versionable = (Versionable)theEObject;
				T result = caseVersionable(versionable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
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
				if (result == null) result = caseVersionable(typeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.STATIC_BASE_TYPE_REF: {
				StaticBaseTypeRef staticBaseTypeRef = (StaticBaseTypeRef)theEObject;
				T result = caseStaticBaseTypeRef(staticBaseTypeRef);
				if (result == null) result = caseTypeRef(staticBaseTypeRef);
				if (result == null) result = caseTypeArgument(staticBaseTypeRef);
				if (result == null) result = caseVersionable(staticBaseTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.BASE_TYPE_REF: {
				BaseTypeRef baseTypeRef = (BaseTypeRef)theEObject;
				T result = caseBaseTypeRef(baseTypeRef);
				if (result == null) result = caseStaticBaseTypeRef(baseTypeRef);
				if (result == null) result = caseTypeRef(baseTypeRef);
				if (result == null) result = caseTypeArgument(baseTypeRef);
				if (result == null) result = caseVersionable(baseTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.COMPOSED_TYPE_REF: {
				ComposedTypeRef composedTypeRef = (ComposedTypeRef)theEObject;
				T result = caseComposedTypeRef(composedTypeRef);
				if (result == null) result = caseStaticBaseTypeRef(composedTypeRef);
				if (result == null) result = caseTypeRef(composedTypeRef);
				if (result == null) result = caseTypeArgument(composedTypeRef);
				if (result == null) result = caseVersionable(composedTypeRef);
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
				if (result == null) result = caseVersionable(unionTypeExpression);
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
				if (result == null) result = caseVersionable(intersectionTypeExpression);
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
				if (result == null) result = caseVersionable(thisTypeRef);
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
				if (result == null) result = caseVersionable(thisTypeRefNominal);
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
				if (result == null) result = caseVersionable(thisTypeRefStructural);
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
				if (result == null) result = caseVersionable(boundThisTypeRef);
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
				if (result == null) result = caseVersionable(parameterizedTypeRef);
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
				if (result == null) result = caseVersionable(parameterizedTypeRefStructural);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.EXISTENTIAL_TYPE_REF: {
				ExistentialTypeRef existentialTypeRef = (ExistentialTypeRef)theEObject;
				T result = caseExistentialTypeRef(existentialTypeRef);
				if (result == null) result = caseTypeRef(existentialTypeRef);
				if (result == null) result = caseTypeArgument(existentialTypeRef);
				if (result == null) result = caseVersionable(existentialTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.UNKNOWN_TYPE_REF: {
				UnknownTypeRef unknownTypeRef = (UnknownTypeRef)theEObject;
				T result = caseUnknownTypeRef(unknownTypeRef);
				if (result == null) result = caseTypeRef(unknownTypeRef);
				if (result == null) result = caseTypeArgument(unknownTypeRef);
				if (result == null) result = caseVersionable(unknownTypeRef);
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
				if (result == null) result = caseVersionable(typeTypeRef);
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
				if (result == null) result = caseVersionable(functionTypeExprOrRef);
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
				if (result == null) result = caseVersionable(functionTypeRef);
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
				if (result == null) result = caseVersionable(functionTypeExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.DEFERRED_TYPE_REF: {
				DeferredTypeRef deferredTypeRef = (DeferredTypeRef)theEObject;
				T result = caseDeferredTypeRef(deferredTypeRef);
				if (result == null) result = caseTypeRef(deferredTypeRef);
				if (result == null) result = caseTypeArgument(deferredTypeRef);
				if (result == null) result = caseVersionable(deferredTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.TYPE_VARIABLE_MAPPING: {
				TypeVariableMapping typeVariableMapping = (TypeVariableMapping)theEObject;
				T result = caseTypeVariableMapping(typeVariableMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.VERSIONED_REFERENCE: {
				VersionedReference versionedReference = (VersionedReference)theEObject;
				T result = caseVersionedReference(versionedReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.VERSIONED_PARAMETERIZED_TYPE_REF: {
				VersionedParameterizedTypeRef versionedParameterizedTypeRef = (VersionedParameterizedTypeRef)theEObject;
				T result = caseVersionedParameterizedTypeRef(versionedParameterizedTypeRef);
				if (result == null) result = caseParameterizedTypeRef(versionedParameterizedTypeRef);
				if (result == null) result = caseVersionedReference(versionedParameterizedTypeRef);
				if (result == null) result = caseBaseTypeRef(versionedParameterizedTypeRef);
				if (result == null) result = caseStaticBaseTypeRef(versionedParameterizedTypeRef);
				if (result == null) result = caseTypeRef(versionedParameterizedTypeRef);
				if (result == null) result = caseTypeArgument(versionedParameterizedTypeRef);
				if (result == null) result = caseVersionable(versionedParameterizedTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.VERSIONED_FUNCTION_TYPE_REF: {
				VersionedFunctionTypeRef versionedFunctionTypeRef = (VersionedFunctionTypeRef)theEObject;
				T result = caseVersionedFunctionTypeRef(versionedFunctionTypeRef);
				if (result == null) result = caseVersionedParameterizedTypeRef(versionedFunctionTypeRef);
				if (result == null) result = caseFunctionTypeRef(versionedFunctionTypeRef);
				if (result == null) result = caseParameterizedTypeRef(versionedFunctionTypeRef);
				if (result == null) result = caseVersionedReference(versionedFunctionTypeRef);
				if (result == null) result = caseFunctionTypeExprOrRef(versionedFunctionTypeRef);
				if (result == null) result = caseBaseTypeRef(versionedFunctionTypeRef);
				if (result == null) result = caseStaticBaseTypeRef(versionedFunctionTypeRef);
				if (result == null) result = caseTypeRef(versionedFunctionTypeRef);
				if (result == null) result = caseTypeArgument(versionedFunctionTypeRef);
				if (result == null) result = caseVersionable(versionedFunctionTypeRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypeRefsPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL: {
				VersionedParameterizedTypeRefStructural versionedParameterizedTypeRefStructural = (VersionedParameterizedTypeRefStructural)theEObject;
				T result = caseVersionedParameterizedTypeRefStructural(versionedParameterizedTypeRefStructural);
				if (result == null) result = caseVersionedParameterizedTypeRef(versionedParameterizedTypeRefStructural);
				if (result == null) result = caseParameterizedTypeRefStructural(versionedParameterizedTypeRefStructural);
				if (result == null) result = caseParameterizedTypeRef(versionedParameterizedTypeRefStructural);
				if (result == null) result = caseVersionedReference(versionedParameterizedTypeRefStructural);
				if (result == null) result = caseStructuralTypeRef(versionedParameterizedTypeRefStructural);
				if (result == null) result = caseBaseTypeRef(versionedParameterizedTypeRefStructural);
				if (result == null) result = caseStaticBaseTypeRef(versionedParameterizedTypeRefStructural);
				if (result == null) result = caseTypeRef(versionedParameterizedTypeRefStructural);
				if (result == null) result = caseTypeArgument(versionedParameterizedTypeRefStructural);
				if (result == null) result = caseVersionable(versionedParameterizedTypeRefStructural);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Versionable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Versionable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersionable(Versionable object) {
		return null;
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
	 * Returns the result of interpreting the object as an instance of '<em>Versioned Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Versioned Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersionedReference(VersionedReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Versioned Parameterized Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Versioned Parameterized Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersionedParameterizedTypeRef(VersionedParameterizedTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Versioned Function Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Versioned Function Type Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersionedFunctionTypeRef(VersionedFunctionTypeRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Versioned Parameterized Type Ref Structural</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Versioned Parameterized Type Ref Structural</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersionedParameterizedTypeRefStructural(VersionedParameterizedTypeRefStructural object) {
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
