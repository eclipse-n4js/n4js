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
package org.eclipse.n4js.ts.typeRefs;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
 * @generated
 */
public interface TypeRefsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TypeRefsFactory eINSTANCE = org.eclipse.n4js.ts.typeRefs.impl.TypeRefsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Union Type Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Union Type Expression</em>'.
	 * @generated
	 */
	UnionTypeExpression createUnionTypeExpression();

	/**
	 * Returns a new object of class '<em>Intersection Type Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Intersection Type Expression</em>'.
	 * @generated
	 */
	IntersectionTypeExpression createIntersectionTypeExpression();

	/**
	 * Returns a new object of class '<em>This Type Ref Nominal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>This Type Ref Nominal</em>'.
	 * @generated
	 */
	ThisTypeRefNominal createThisTypeRefNominal();

	/**
	 * Returns a new object of class '<em>This Type Ref Structural</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>This Type Ref Structural</em>'.
	 * @generated
	 */
	ThisTypeRefStructural createThisTypeRefStructural();

	/**
	 * Returns a new object of class '<em>Bound This Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bound This Type Ref</em>'.
	 * @generated
	 */
	BoundThisTypeRef createBoundThisTypeRef();

	/**
	 * Returns a new object of class '<em>Parameterized Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameterized Type Ref</em>'.
	 * @generated
	 */
	ParameterizedTypeRef createParameterizedTypeRef();

	/**
	 * Returns a new object of class '<em>Parameterized Type Ref Structural</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameterized Type Ref Structural</em>'.
	 * @generated
	 */
	ParameterizedTypeRefStructural createParameterizedTypeRefStructural();

	/**
	 * Returns a new object of class '<em>Existential Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Existential Type Ref</em>'.
	 * @generated
	 */
	ExistentialTypeRef createExistentialTypeRef();

	/**
	 * Returns a new object of class '<em>Unknown Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Unknown Type Ref</em>'.
	 * @generated
	 */
	UnknownTypeRef createUnknownTypeRef();

	/**
	 * Returns a new object of class '<em>Type Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Type Ref</em>'.
	 * @generated
	 */
	TypeTypeRef createTypeTypeRef();

	/**
	 * Returns a new object of class '<em>Wildcard</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Wildcard</em>'.
	 * @generated
	 */
	Wildcard createWildcard();

	/**
	 * Returns a new object of class '<em>Function Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function Type Ref</em>'.
	 * @generated
	 */
	FunctionTypeRef createFunctionTypeRef();

	/**
	 * Returns a new object of class '<em>Function Type Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function Type Expression</em>'.
	 * @generated
	 */
	FunctionTypeExpression createFunctionTypeExpression();

	/**
	 * Returns a new object of class '<em>Deferred Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Deferred Type Ref</em>'.
	 * @generated
	 */
	DeferredTypeRef createDeferredTypeRef();

	/**
	 * Returns a new object of class '<em>Type Variable Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Variable Mapping</em>'.
	 * @generated
	 */
	TypeVariableMapping createTypeVariableMapping();

	/**
	 * Returns a new object of class '<em>Versioned Parameterized Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Versioned Parameterized Type Ref</em>'.
	 * @generated
	 */
	VersionedParameterizedTypeRef createVersionedParameterizedTypeRef();

	/**
	 * Returns a new object of class '<em>Versioned Function Type Ref</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Versioned Function Type Ref</em>'.
	 * @generated
	 */
	VersionedFunctionTypeRef createVersionedFunctionTypeRef();

	/**
	 * Returns a new object of class '<em>Versioned Parameterized Type Ref Structural</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Versioned Parameterized Type Ref Structural</em>'.
	 * @generated
	 */
	VersionedParameterizedTypeRefStructural createVersionedParameterizedTypeRefStructural();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TypeRefsPackage getTypeRefsPackage();

} //TypeRefsFactory
