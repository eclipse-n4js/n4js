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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.ts.typeRefs.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
 * @generated
 */
public class TypeRefsAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TypeRefsPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeRefsAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = TypeRefsPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeRefsSwitch<Adapter> modelSwitch =
		new TypeRefsSwitch<Adapter>() {
			@Override
			public Adapter caseVersionable(Versionable object) {
				return createVersionableAdapter();
			}
			@Override
			public Adapter caseTypeArgument(TypeArgument object) {
				return createTypeArgumentAdapter();
			}
			@Override
			public Adapter caseTypeRef(TypeRef object) {
				return createTypeRefAdapter();
			}
			@Override
			public Adapter caseStaticBaseTypeRef(StaticBaseTypeRef object) {
				return createStaticBaseTypeRefAdapter();
			}
			@Override
			public Adapter caseBaseTypeRef(BaseTypeRef object) {
				return createBaseTypeRefAdapter();
			}
			@Override
			public Adapter caseComposedTypeRef(ComposedTypeRef object) {
				return createComposedTypeRefAdapter();
			}
			@Override
			public Adapter caseUnionTypeExpression(UnionTypeExpression object) {
				return createUnionTypeExpressionAdapter();
			}
			@Override
			public Adapter caseIntersectionTypeExpression(IntersectionTypeExpression object) {
				return createIntersectionTypeExpressionAdapter();
			}
			@Override
			public Adapter caseThisTypeRef(ThisTypeRef object) {
				return createThisTypeRefAdapter();
			}
			@Override
			public Adapter caseThisTypeRefNominal(ThisTypeRefNominal object) {
				return createThisTypeRefNominalAdapter();
			}
			@Override
			public Adapter caseThisTypeRefStructural(ThisTypeRefStructural object) {
				return createThisTypeRefStructuralAdapter();
			}
			@Override
			public Adapter caseBoundThisTypeRef(BoundThisTypeRef object) {
				return createBoundThisTypeRefAdapter();
			}
			@Override
			public Adapter caseParameterizedTypeRef(ParameterizedTypeRef object) {
				return createParameterizedTypeRefAdapter();
			}
			@Override
			public Adapter caseStructuralTypeRef(StructuralTypeRef object) {
				return createStructuralTypeRefAdapter();
			}
			@Override
			public Adapter caseParameterizedTypeRefStructural(ParameterizedTypeRefStructural object) {
				return createParameterizedTypeRefStructuralAdapter();
			}
			@Override
			public Adapter caseExistentialTypeRef(ExistentialTypeRef object) {
				return createExistentialTypeRefAdapter();
			}
			@Override
			public Adapter caseUnknownTypeRef(UnknownTypeRef object) {
				return createUnknownTypeRefAdapter();
			}
			@Override
			public Adapter caseTypeTypeRef(TypeTypeRef object) {
				return createTypeTypeRefAdapter();
			}
			@Override
			public Adapter caseWildcard(Wildcard object) {
				return createWildcardAdapter();
			}
			@Override
			public Adapter caseFunctionTypeExprOrRef(FunctionTypeExprOrRef object) {
				return createFunctionTypeExprOrRefAdapter();
			}
			@Override
			public Adapter caseFunctionTypeRef(FunctionTypeRef object) {
				return createFunctionTypeRefAdapter();
			}
			@Override
			public Adapter caseFunctionTypeExpression(FunctionTypeExpression object) {
				return createFunctionTypeExpressionAdapter();
			}
			@Override
			public Adapter caseDeferredTypeRef(DeferredTypeRef object) {
				return createDeferredTypeRefAdapter();
			}
			@Override
			public Adapter caseTypeVariableMapping(TypeVariableMapping object) {
				return createTypeVariableMappingAdapter();
			}
			@Override
			public Adapter caseVersionedReference(VersionedReference object) {
				return createVersionedReferenceAdapter();
			}
			@Override
			public Adapter caseVersionedParameterizedTypeRef(VersionedParameterizedTypeRef object) {
				return createVersionedParameterizedTypeRefAdapter();
			}
			@Override
			public Adapter caseVersionedFunctionTypeRef(VersionedFunctionTypeRef object) {
				return createVersionedFunctionTypeRefAdapter();
			}
			@Override
			public Adapter caseVersionedParameterizedTypeRefStructural(VersionedParameterizedTypeRefStructural object) {
				return createVersionedParameterizedTypeRefStructuralAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.Versionable <em>Versionable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.Versionable
	 * @generated
	 */
	public Adapter createVersionableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.TypeArgument <em>Type Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeArgument
	 * @generated
	 */
	public Adapter createTypeArgumentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.TypeRef <em>Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef
	 * @generated
	 */
	public Adapter createTypeRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.StaticBaseTypeRef <em>Static Base Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.StaticBaseTypeRef
	 * @generated
	 */
	public Adapter createStaticBaseTypeRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.BaseTypeRef <em>Base Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.BaseTypeRef
	 * @generated
	 */
	public Adapter createBaseTypeRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.ComposedTypeRef <em>Composed Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
	 * @generated
	 */
	public Adapter createComposedTypeRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.UnionTypeExpression <em>Union Type Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
	 * @generated
	 */
	public Adapter createUnionTypeExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression <em>Intersection Type Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression
	 * @generated
	 */
	public Adapter createIntersectionTypeExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.ThisTypeRef <em>This Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.ThisTypeRef
	 * @generated
	 */
	public Adapter createThisTypeRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.ThisTypeRefNominal <em>This Type Ref Nominal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.ThisTypeRefNominal
	 * @generated
	 */
	public Adapter createThisTypeRefNominalAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural <em>This Type Ref Structural</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural
	 * @generated
	 */
	public Adapter createThisTypeRefStructuralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef <em>Bound This Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef
	 * @generated
	 */
	public Adapter createBoundThisTypeRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef <em>Parameterized Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
	 * @generated
	 */
	public Adapter createParameterizedTypeRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.StructuralTypeRef <em>Structural Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.StructuralTypeRef
	 * @generated
	 */
	public Adapter createStructuralTypeRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural <em>Parameterized Type Ref Structural</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural
	 * @generated
	 */
	public Adapter createParameterizedTypeRefStructuralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef <em>Existential Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef
	 * @generated
	 */
	public Adapter createExistentialTypeRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.UnknownTypeRef <em>Unknown Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.UnknownTypeRef
	 * @generated
	 */
	public Adapter createUnknownTypeRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.TypeTypeRef <em>Type Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeTypeRef
	 * @generated
	 */
	public Adapter createTypeTypeRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.Wildcard <em>Wildcard</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.Wildcard
	 * @generated
	 */
	public Adapter createWildcardAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef <em>Function Type Expr Or Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
	 * @generated
	 */
	public Adapter createFunctionTypeExprOrRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeRef <em>Function Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeRef
	 * @generated
	 */
	public Adapter createFunctionTypeRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression <em>Function Type Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
	 * @generated
	 */
	public Adapter createFunctionTypeExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.DeferredTypeRef <em>Deferred Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.DeferredTypeRef
	 * @generated
	 */
	public Adapter createDeferredTypeRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.TypeVariableMapping <em>Type Variable Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeVariableMapping
	 * @generated
	 */
	public Adapter createTypeVariableMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.VersionedReference <em>Versioned Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.VersionedReference
	 * @generated
	 */
	public Adapter createVersionedReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.VersionedParameterizedTypeRef <em>Versioned Parameterized Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.VersionedParameterizedTypeRef
	 * @generated
	 */
	public Adapter createVersionedParameterizedTypeRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.VersionedFunctionTypeRef <em>Versioned Function Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.VersionedFunctionTypeRef
	 * @generated
	 */
	public Adapter createVersionedFunctionTypeRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.ts.typeRefs.VersionedParameterizedTypeRefStructural <em>Versioned Parameterized Type Ref Structural</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.ts.typeRefs.VersionedParameterizedTypeRefStructural
	 * @generated
	 */
	public Adapter createVersionedParameterizedTypeRefStructuralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //TypeRefsAdapterFactory
