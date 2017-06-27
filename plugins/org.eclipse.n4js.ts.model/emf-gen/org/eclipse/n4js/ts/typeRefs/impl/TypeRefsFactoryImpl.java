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
package org.eclipse.n4js.ts.typeRefs.impl;

import java.lang.Iterable;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.n4js.ts.typeRefs.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TypeRefsFactoryImpl extends EFactoryImpl implements TypeRefsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TypeRefsFactory init() {
		try {
			TypeRefsFactory theTypeRefsFactory = (TypeRefsFactory)EPackage.Registry.INSTANCE.getEFactory(TypeRefsPackage.eNS_URI);
			if (theTypeRefsFactory != null) {
				return theTypeRefsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TypeRefsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeRefsFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TypeRefsPackage.UNION_TYPE_EXPRESSION: return createUnionTypeExpression();
			case TypeRefsPackage.INTERSECTION_TYPE_EXPRESSION: return createIntersectionTypeExpression();
			case TypeRefsPackage.THIS_TYPE_REF: return createThisTypeRef();
			case TypeRefsPackage.THIS_TYPE_REF_NOMINAL: return createThisTypeRefNominal();
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL: return createThisTypeRefStructural();
			case TypeRefsPackage.BOUND_THIS_TYPE_REF: return createBoundThisTypeRef();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF: return createParameterizedTypeRef();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL: return createParameterizedTypeRefStructural();
			case TypeRefsPackage.EXISTENTIAL_TYPE_REF: return createExistentialTypeRef();
			case TypeRefsPackage.UNKNOWN_TYPE_REF: return createUnknownTypeRef();
			case TypeRefsPackage.TYPE_TYPE_REF: return createTypeTypeRef();
			case TypeRefsPackage.WILDCARD: return createWildcard();
			case TypeRefsPackage.FUNCTION_TYPE_REF: return createFunctionTypeRef();
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION: return createFunctionTypeExpression();
			case TypeRefsPackage.DEFERRED_TYPE_REF: return createDeferredTypeRef();
			case TypeRefsPackage.TYPE_VARIABLE_MAPPING: return createTypeVariableMapping();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF_ITERABLE:
				return createParameterizedTypeRefIterableFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF_ITERABLE:
				return convertParameterizedTypeRefIterableToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnionTypeExpression createUnionTypeExpression() {
		UnionTypeExpressionImpl unionTypeExpression = new UnionTypeExpressionImpl();
		return unionTypeExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntersectionTypeExpression createIntersectionTypeExpression() {
		IntersectionTypeExpressionImpl intersectionTypeExpression = new IntersectionTypeExpressionImpl();
		return intersectionTypeExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ThisTypeRef createThisTypeRef() {
		ThisTypeRefImpl thisTypeRef = new ThisTypeRefImpl();
		return thisTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ThisTypeRefNominal createThisTypeRefNominal() {
		ThisTypeRefNominalImpl thisTypeRefNominal = new ThisTypeRefNominalImpl();
		return thisTypeRefNominal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ThisTypeRefStructural createThisTypeRefStructural() {
		ThisTypeRefStructuralImpl thisTypeRefStructural = new ThisTypeRefStructuralImpl();
		return thisTypeRefStructural;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BoundThisTypeRef createBoundThisTypeRef() {
		BoundThisTypeRefImpl boundThisTypeRef = new BoundThisTypeRefImpl();
		return boundThisTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterizedTypeRef createParameterizedTypeRef() {
		ParameterizedTypeRefImpl parameterizedTypeRef = new ParameterizedTypeRefImpl();
		return parameterizedTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterizedTypeRefStructural createParameterizedTypeRefStructural() {
		ParameterizedTypeRefStructuralImpl parameterizedTypeRefStructural = new ParameterizedTypeRefStructuralImpl();
		return parameterizedTypeRefStructural;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExistentialTypeRef createExistentialTypeRef() {
		ExistentialTypeRefImpl existentialTypeRef = new ExistentialTypeRefImpl();
		return existentialTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnknownTypeRef createUnknownTypeRef() {
		UnknownTypeRefImpl unknownTypeRef = new UnknownTypeRefImpl();
		return unknownTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeTypeRef createTypeTypeRef() {
		TypeTypeRefImpl typeTypeRef = new TypeTypeRefImpl();
		return typeTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Wildcard createWildcard() {
		WildcardImpl wildcard = new WildcardImpl();
		return wildcard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionTypeRef createFunctionTypeRef() {
		FunctionTypeRefImpl functionTypeRef = new FunctionTypeRefImpl();
		return functionTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionTypeExpression createFunctionTypeExpression() {
		FunctionTypeExpressionImpl functionTypeExpression = new FunctionTypeExpressionImpl();
		return functionTypeExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeferredTypeRef createDeferredTypeRef() {
		DeferredTypeRefImpl deferredTypeRef = new DeferredTypeRefImpl();
		return deferredTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeVariableMapping createTypeVariableMapping() {
		TypeVariableMappingImpl typeVariableMapping = new TypeVariableMappingImpl();
		return typeVariableMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Iterable<ParameterizedTypeRef> createParameterizedTypeRefIterableFromString(EDataType eDataType, String initialValue) {
		return (Iterable<ParameterizedTypeRef>)super.createFromString(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertParameterizedTypeRefIterableToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeRefsPackage getTypeRefsPackage() {
		return (TypeRefsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TypeRefsPackage getPackage() {
		return TypeRefsPackage.eINSTANCE;
	}

} //TypeRefsFactoryImpl
