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
package org.eclipse.n4js.n4mf.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.n4mf.RequiredRuntimeLibraries;
import org.eclipse.n4js.n4mf.RequiredRuntimeLibraryDependency;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Required Runtime Libraries</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.RequiredRuntimeLibrariesImpl#getRequiredRuntimeLibraries <em>Required Runtime Libraries</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RequiredRuntimeLibrariesImpl extends MinimalEObjectImpl.Container implements RequiredRuntimeLibraries {
	/**
	 * The cached value of the '{@link #getRequiredRuntimeLibraries() <em>Required Runtime Libraries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequiredRuntimeLibraries()
	 * @generated
	 * @ordered
	 */
	protected EList<RequiredRuntimeLibraryDependency> requiredRuntimeLibraries;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RequiredRuntimeLibrariesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4mfPackage.Literals.REQUIRED_RUNTIME_LIBRARIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RequiredRuntimeLibraryDependency> getRequiredRuntimeLibraries() {
		if (requiredRuntimeLibraries == null) {
			requiredRuntimeLibraries = new EObjectContainmentEList<RequiredRuntimeLibraryDependency>(RequiredRuntimeLibraryDependency.class, this, N4mfPackage.REQUIRED_RUNTIME_LIBRARIES__REQUIRED_RUNTIME_LIBRARIES);
		}
		return requiredRuntimeLibraries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4mfPackage.REQUIRED_RUNTIME_LIBRARIES__REQUIRED_RUNTIME_LIBRARIES:
				return ((InternalEList<?>)getRequiredRuntimeLibraries()).basicRemove(otherEnd, msgs);
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
			case N4mfPackage.REQUIRED_RUNTIME_LIBRARIES__REQUIRED_RUNTIME_LIBRARIES:
				return getRequiredRuntimeLibraries();
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
			case N4mfPackage.REQUIRED_RUNTIME_LIBRARIES__REQUIRED_RUNTIME_LIBRARIES:
				getRequiredRuntimeLibraries().clear();
				getRequiredRuntimeLibraries().addAll((Collection<? extends RequiredRuntimeLibraryDependency>)newValue);
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
			case N4mfPackage.REQUIRED_RUNTIME_LIBRARIES__REQUIRED_RUNTIME_LIBRARIES:
				getRequiredRuntimeLibraries().clear();
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
			case N4mfPackage.REQUIRED_RUNTIME_LIBRARIES__REQUIRED_RUNTIME_LIBRARIES:
				return requiredRuntimeLibraries != null && !requiredRuntimeLibraries.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //RequiredRuntimeLibrariesImpl
