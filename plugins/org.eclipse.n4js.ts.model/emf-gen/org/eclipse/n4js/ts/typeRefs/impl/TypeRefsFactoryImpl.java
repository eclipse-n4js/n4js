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

import java.util.UUID;

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
			case TypeRefsPackage.THIS_TYPE_REF_NOMINAL: return createThisTypeRefNominal();
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL: return createThisTypeRefStructural();
			case TypeRefsPackage.BOUND_THIS_TYPE_REF: return createBoundThisTypeRef();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF: return createParameterizedTypeRef();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL: return createParameterizedTypeRefStructural();
			case TypeRefsPackage.EXISTENTIAL_TYPE_REF: return createExistentialTypeRef();
			case TypeRefsPackage.UNKNOWN_TYPE_REF: return createUnknownTypeRef();
			case TypeRefsPackage.TYPE_TYPE_REF: return createTypeTypeRef();
			case TypeRefsPackage.NAMESPACE_LIKE_REF: return createNamespaceLikeRef();
			case TypeRefsPackage.WILDCARD: return createWildcard();
			case TypeRefsPackage.FUNCTION_TYPE_REF: return createFunctionTypeRef();
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION: return createFunctionTypeExpression();
			case TypeRefsPackage.DEFERRED_TYPE_REF: return createDeferredTypeRef();
			case TypeRefsPackage.TYPE_VARIABLE_MAPPING: return createTypeVariableMapping();
			case TypeRefsPackage.BOOLEAN_LITERAL_TYPE_REF: return createBooleanLiteralTypeRef();
			case TypeRefsPackage.NUMERIC_LITERAL_TYPE_REF: return createNumericLiteralTypeRef();
			case TypeRefsPackage.STRING_LITERAL_TYPE_REF: return createStringLiteralTypeRef();
			case TypeRefsPackage.ENUM_LITERAL_TYPE_REF: return createEnumLiteralTypeRef();
			case TypeRefsPackage.QUERY_TYPE_REF: return createQueryTypeRef();
			case TypeRefsPackage.OPERATOR_TYPE_REF: return createOperatorTypeRef();
			case TypeRefsPackage.INDEX_ACCESS_TYPE_REF: return createIndexAccessTypeRef();
			case TypeRefsPackage.CONDITIONAL_TYPE_REF: return createConditionalTypeRef();
			case TypeRefsPackage.INFER_TYPE_REF: return createInferTypeRef();
			case TypeRefsPackage.MAPPED_TYPE_REF: return createMappedTypeRef();
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
			case TypeRefsPackage.OPTIONAL_FIELD_STRATEGY:
				return createOptionalFieldStrategyFromString(eDataType, initialValue);
			case TypeRefsPackage.TYPE_OPERATOR:
				return createTypeOperatorFromString(eDataType, initialValue);
			case TypeRefsPackage.UUID:
				return createUUIDFromString(eDataType, initialValue);
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
			case TypeRefsPackage.OPTIONAL_FIELD_STRATEGY:
				return convertOptionalFieldStrategyToString(eDataType, instanceValue);
			case TypeRefsPackage.TYPE_OPERATOR:
				return convertTypeOperatorToString(eDataType, instanceValue);
			case TypeRefsPackage.UUID:
				return convertUUIDToString(eDataType, instanceValue);
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
	public UnionTypeExpression createUnionTypeExpression() {
		UnionTypeExpressionImpl unionTypeExpression = new UnionTypeExpressionImpl();
		return unionTypeExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IntersectionTypeExpression createIntersectionTypeExpression() {
		IntersectionTypeExpressionImpl intersectionTypeExpression = new IntersectionTypeExpressionImpl();
		return intersectionTypeExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ThisTypeRefNominal createThisTypeRefNominal() {
		ThisTypeRefNominalImpl thisTypeRefNominal = new ThisTypeRefNominalImpl();
		return thisTypeRefNominal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ThisTypeRefStructural createThisTypeRefStructural() {
		ThisTypeRefStructuralImpl thisTypeRefStructural = new ThisTypeRefStructuralImpl();
		return thisTypeRefStructural;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BoundThisTypeRef createBoundThisTypeRef() {
		BoundThisTypeRefImpl boundThisTypeRef = new BoundThisTypeRefImpl();
		return boundThisTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ParameterizedTypeRef createParameterizedTypeRef() {
		ParameterizedTypeRefImpl parameterizedTypeRef = new ParameterizedTypeRefImpl();
		return parameterizedTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ParameterizedTypeRefStructural createParameterizedTypeRefStructural() {
		ParameterizedTypeRefStructuralImpl parameterizedTypeRefStructural = new ParameterizedTypeRefStructuralImpl();
		return parameterizedTypeRefStructural;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExistentialTypeRef createExistentialTypeRef() {
		ExistentialTypeRefImpl existentialTypeRef = new ExistentialTypeRefImpl();
		return existentialTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UnknownTypeRef createUnknownTypeRef() {
		UnknownTypeRefImpl unknownTypeRef = new UnknownTypeRefImpl();
		return unknownTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeTypeRef createTypeTypeRef() {
		TypeTypeRefImpl typeTypeRef = new TypeTypeRefImpl();
		return typeTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NamespaceLikeRef createNamespaceLikeRef() {
		NamespaceLikeRefImpl namespaceLikeRef = new NamespaceLikeRefImpl();
		return namespaceLikeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Wildcard createWildcard() {
		WildcardImpl wildcard = new WildcardImpl();
		return wildcard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FunctionTypeRef createFunctionTypeRef() {
		FunctionTypeRefImpl functionTypeRef = new FunctionTypeRefImpl();
		return functionTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FunctionTypeExpression createFunctionTypeExpression() {
		FunctionTypeExpressionImpl functionTypeExpression = new FunctionTypeExpressionImpl();
		return functionTypeExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DeferredTypeRef createDeferredTypeRef() {
		DeferredTypeRefImpl deferredTypeRef = new DeferredTypeRefImpl();
		return deferredTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeVariableMapping createTypeVariableMapping() {
		TypeVariableMappingImpl typeVariableMapping = new TypeVariableMappingImpl();
		return typeVariableMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BooleanLiteralTypeRef createBooleanLiteralTypeRef() {
		BooleanLiteralTypeRefImpl booleanLiteralTypeRef = new BooleanLiteralTypeRefImpl();
		return booleanLiteralTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NumericLiteralTypeRef createNumericLiteralTypeRef() {
		NumericLiteralTypeRefImpl numericLiteralTypeRef = new NumericLiteralTypeRefImpl();
		return numericLiteralTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StringLiteralTypeRef createStringLiteralTypeRef() {
		StringLiteralTypeRefImpl stringLiteralTypeRef = new StringLiteralTypeRefImpl();
		return stringLiteralTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EnumLiteralTypeRef createEnumLiteralTypeRef() {
		EnumLiteralTypeRefImpl enumLiteralTypeRef = new EnumLiteralTypeRefImpl();
		return enumLiteralTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public QueryTypeRef createQueryTypeRef() {
		QueryTypeRefImpl queryTypeRef = new QueryTypeRefImpl();
		return queryTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OperatorTypeRef createOperatorTypeRef() {
		OperatorTypeRefImpl operatorTypeRef = new OperatorTypeRefImpl();
		return operatorTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IndexAccessTypeRef createIndexAccessTypeRef() {
		IndexAccessTypeRefImpl indexAccessTypeRef = new IndexAccessTypeRefImpl();
		return indexAccessTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ConditionalTypeRef createConditionalTypeRef() {
		ConditionalTypeRefImpl conditionalTypeRef = new ConditionalTypeRefImpl();
		return conditionalTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InferTypeRef createInferTypeRef() {
		InferTypeRefImpl inferTypeRef = new InferTypeRefImpl();
		return inferTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MappedTypeRef createMappedTypeRef() {
		MappedTypeRefImpl mappedTypeRef = new MappedTypeRefImpl();
		return mappedTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OptionalFieldStrategy createOptionalFieldStrategyFromString(EDataType eDataType, String initialValue) {
		OptionalFieldStrategy result = OptionalFieldStrategy.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOptionalFieldStrategyToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeOperator createTypeOperatorFromString(EDataType eDataType, String initialValue) {
		TypeOperator result = TypeOperator.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTypeOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UUID createUUIDFromString(EDataType eDataType, String initialValue) {
		return (UUID)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertUUIDToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
