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
import org.eclipse.n4js.n4mf.ProvidedRuntimeLibraries;
import org.eclipse.n4js.n4mf.ProvidedRuntimeLibraryDependency;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Provided Runtime Libraries</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProvidedRuntimeLibrariesImpl#getProvidedRuntimeLibraries <em>Provided Runtime Libraries</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProvidedRuntimeLibrariesImpl extends MinimalEObjectImpl.Container implements ProvidedRuntimeLibraries {
	/**
	 * The cached value of the '{@link #getProvidedRuntimeLibraries() <em>Provided Runtime Libraries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProvidedRuntimeLibraries()
	 * @generated
	 * @ordered
	 */
	protected EList<ProvidedRuntimeLibraryDependency> providedRuntimeLibraries;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProvidedRuntimeLibrariesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4mfPackage.Literals.PROVIDED_RUNTIME_LIBRARIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProvidedRuntimeLibraryDependency> getProvidedRuntimeLibraries() {
		if (providedRuntimeLibraries == null) {
			providedRuntimeLibraries = new EObjectContainmentEList<ProvidedRuntimeLibraryDependency>(ProvidedRuntimeLibraryDependency.class, this, N4mfPackage.PROVIDED_RUNTIME_LIBRARIES__PROVIDED_RUNTIME_LIBRARIES);
		}
		return providedRuntimeLibraries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4mfPackage.PROVIDED_RUNTIME_LIBRARIES__PROVIDED_RUNTIME_LIBRARIES:
				return ((InternalEList<?>)getProvidedRuntimeLibraries()).basicRemove(otherEnd, msgs);
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
			case N4mfPackage.PROVIDED_RUNTIME_LIBRARIES__PROVIDED_RUNTIME_LIBRARIES:
				return getProvidedRuntimeLibraries();
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
			case N4mfPackage.PROVIDED_RUNTIME_LIBRARIES__PROVIDED_RUNTIME_LIBRARIES:
				getProvidedRuntimeLibraries().clear();
				getProvidedRuntimeLibraries().addAll((Collection<? extends ProvidedRuntimeLibraryDependency>)newValue);
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
			case N4mfPackage.PROVIDED_RUNTIME_LIBRARIES__PROVIDED_RUNTIME_LIBRARIES:
				getProvidedRuntimeLibraries().clear();
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
			case N4mfPackage.PROVIDED_RUNTIME_LIBRARIES__PROVIDED_RUNTIME_LIBRARIES:
				return providedRuntimeLibraries != null && !providedRuntimeLibraries.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProvidedRuntimeLibrariesImpl
