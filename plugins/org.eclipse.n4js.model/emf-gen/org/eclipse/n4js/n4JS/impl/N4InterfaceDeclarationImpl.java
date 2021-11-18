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
package org.eclipse.n4js.n4JS.impl;

import com.google.common.base.Objects;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4TypeDeclaration;
import org.eclipse.n4js.n4JS.NamespaceElement;
import org.eclipse.n4js.n4JS.TypeReferenceNode;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;

import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.Type;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>N4 Interface Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4InterfaceDeclarationImpl#getSuperInterfaceRefs <em>Super Interface Refs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class N4InterfaceDeclarationImpl extends N4ClassifierDeclarationImpl implements N4InterfaceDeclaration {
	/**
	 * The cached value of the '{@link #getSuperInterfaceRefs() <em>Super Interface Refs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperInterfaceRefs()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeReferenceNode<ParameterizedTypeRef>> superInterfaceRefs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected N4InterfaceDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.N4_INTERFACE_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypeReferenceNode<ParameterizedTypeRef>> getSuperInterfaceRefs() {
		if (superInterfaceRefs == null) {
			superInterfaceRefs = new EObjectContainmentEList<TypeReferenceNode<ParameterizedTypeRef>>(TypeReferenceNode.class, this, N4JSPackage.N4_INTERFACE_DECLARATION__SUPER_INTERFACE_REFS);
		}
		return superInterfaceRefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TInterface getDefinedTypeAsInterface() {
		Type _definedType = this.getDefinedType();
		return ((TInterface) _definedType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterable<TypeReferenceNode<ParameterizedTypeRef>> getSuperClassifierRefs() {
		return this.getSuperInterfaceRefs();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterable<TypeReferenceNode<ParameterizedTypeRef>> getImplementedOrExtendedInterfaceRefs() {
		return this.getSuperInterfaceRefs();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isHollow() {
		return (this.isExternal() && (!IterableExtensions.<Annotation>exists(this.getAnnotations(), new Function1<Annotation, Boolean>() {
			public Boolean apply(final Annotation it) {
				String _name = it.getName();
				return Boolean.valueOf(Objects.equal(_name, "N4JS"));
			}
		})));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.N4_INTERFACE_DECLARATION__SUPER_INTERFACE_REFS:
				return ((InternalEList<?>)getSuperInterfaceRefs()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case N4JSPackage.N4_INTERFACE_DECLARATION__SUPER_INTERFACE_REFS:
				return getSuperInterfaceRefs();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case N4JSPackage.N4_INTERFACE_DECLARATION__SUPER_INTERFACE_REFS:
				getSuperInterfaceRefs().clear();
				getSuperInterfaceRefs().addAll((Collection<? extends TypeReferenceNode<ParameterizedTypeRef>>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case N4JSPackage.N4_INTERFACE_DECLARATION__SUPER_INTERFACE_REFS:
				getSuperInterfaceRefs().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case N4JSPackage.N4_INTERFACE_DECLARATION__SUPER_INTERFACE_REFS:
				return superInterfaceRefs != null && !superInterfaceRefs.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == NamespaceElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.NAMESPACE_ELEMENT___IS_HOLLOW: return N4JSPackage.N4_INTERFACE_DECLARATION___IS_HOLLOW;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == N4TypeDeclaration.class) {
			switch (baseOperationID) {
				case N4JSPackage.N4_TYPE_DECLARATION___IS_HOLLOW: return N4JSPackage.N4_INTERFACE_DECLARATION___IS_HOLLOW;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == N4ClassifierDefinition.class) {
			switch (baseOperationID) {
				case N4JSPackage.N4_CLASSIFIER_DEFINITION___GET_SUPER_CLASSIFIER_REFS: return N4JSPackage.N4_INTERFACE_DECLARATION___GET_SUPER_CLASSIFIER_REFS;
				case N4JSPackage.N4_CLASSIFIER_DEFINITION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS: return N4JSPackage.N4_INTERFACE_DECLARATION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == N4ClassifierDeclaration.class) {
			switch (baseOperationID) {
				case N4JSPackage.N4_CLASSIFIER_DECLARATION___GET_SUPER_CLASSIFIER_REFS: return N4JSPackage.N4_INTERFACE_DECLARATION___GET_SUPER_CLASSIFIER_REFS;
				case N4JSPackage.N4_CLASSIFIER_DECLARATION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS: return N4JSPackage.N4_INTERFACE_DECLARATION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case N4JSPackage.N4_INTERFACE_DECLARATION___GET_DEFINED_TYPE_AS_INTERFACE:
				return getDefinedTypeAsInterface();
			case N4JSPackage.N4_INTERFACE_DECLARATION___GET_SUPER_CLASSIFIER_REFS:
				return getSuperClassifierRefs();
			case N4JSPackage.N4_INTERFACE_DECLARATION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS:
				return getImplementedOrExtendedInterfaceRefs();
			case N4JSPackage.N4_INTERFACE_DECLARATION___IS_HOLLOW:
				return isHollow();
		}
		return super.eInvoke(operationID, arguments);
	}

} //N4InterfaceDeclarationImpl
