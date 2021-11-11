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
package org.eclipse.n4js.ts.types.impl;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TNamespace;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

import org.eclipse.xtext.EcoreUtil2;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Namespace</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.AbstractNamespaceImpl#getTopLevelTypes <em>Top Level Types</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.AbstractNamespaceImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.AbstractNamespaceImpl#getNamespaces <em>Namespaces</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AbstractNamespaceImpl extends ProxyResolvingEObjectImpl implements AbstractNamespace {
	/**
	 * The cached value of the '{@link #getTopLevelTypes() <em>Top Level Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTopLevelTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<Type> topLevelTypes;

	/**
	 * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<TVariable> variables;

	/**
	 * The cached value of the '{@link #getNamespaces() <em>Namespaces</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespaces()
	 * @generated
	 * @ordered
	 */
	protected EList<TNamespace> namespaces;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractNamespaceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.ABSTRACT_NAMESPACE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Type> getTopLevelTypes() {
		if (topLevelTypes == null) {
			topLevelTypes = new EObjectContainmentEList<Type>(Type.class, this, TypesPackage.ABSTRACT_NAMESPACE__TOP_LEVEL_TYPES);
		}
		return topLevelTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TVariable> getVariables() {
		if (variables == null) {
			variables = new EObjectContainmentEList<TVariable>(TVariable.class, this, TypesPackage.ABSTRACT_NAMESPACE__VARIABLES);
		}
		return variables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TNamespace> getNamespaces() {
		if (namespaces == null) {
			namespaces = new EObjectContainmentEList<TNamespace>(TNamespace.class, this, TypesPackage.ABSTRACT_NAMESPACE__NAMESPACES);
		}
		return namespaces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TModule getContainingModule() {
		return EcoreUtil2.<TModule>getContainerOfType(this, TModule.class);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.ABSTRACT_NAMESPACE__TOP_LEVEL_TYPES:
				return ((InternalEList<?>)getTopLevelTypes()).basicRemove(otherEnd, msgs);
			case TypesPackage.ABSTRACT_NAMESPACE__VARIABLES:
				return ((InternalEList<?>)getVariables()).basicRemove(otherEnd, msgs);
			case TypesPackage.ABSTRACT_NAMESPACE__NAMESPACES:
				return ((InternalEList<?>)getNamespaces()).basicRemove(otherEnd, msgs);
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
			case TypesPackage.ABSTRACT_NAMESPACE__TOP_LEVEL_TYPES:
				return getTopLevelTypes();
			case TypesPackage.ABSTRACT_NAMESPACE__VARIABLES:
				return getVariables();
			case TypesPackage.ABSTRACT_NAMESPACE__NAMESPACES:
				return getNamespaces();
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
			case TypesPackage.ABSTRACT_NAMESPACE__TOP_LEVEL_TYPES:
				getTopLevelTypes().clear();
				getTopLevelTypes().addAll((Collection<? extends Type>)newValue);
				return;
			case TypesPackage.ABSTRACT_NAMESPACE__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends TVariable>)newValue);
				return;
			case TypesPackage.ABSTRACT_NAMESPACE__NAMESPACES:
				getNamespaces().clear();
				getNamespaces().addAll((Collection<? extends TNamespace>)newValue);
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
			case TypesPackage.ABSTRACT_NAMESPACE__TOP_LEVEL_TYPES:
				getTopLevelTypes().clear();
				return;
			case TypesPackage.ABSTRACT_NAMESPACE__VARIABLES:
				getVariables().clear();
				return;
			case TypesPackage.ABSTRACT_NAMESPACE__NAMESPACES:
				getNamespaces().clear();
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
			case TypesPackage.ABSTRACT_NAMESPACE__TOP_LEVEL_TYPES:
				return topLevelTypes != null && !topLevelTypes.isEmpty();
			case TypesPackage.ABSTRACT_NAMESPACE__VARIABLES:
				return variables != null && !variables.isEmpty();
			case TypesPackage.ABSTRACT_NAMESPACE__NAMESPACES:
				return namespaces != null && !namespaces.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case TypesPackage.ABSTRACT_NAMESPACE___GET_CONTAINING_MODULE:
				return getContainingModule();
		}
		return super.eInvoke(operationID, arguments);
	}

} //AbstractNamespaceImpl
