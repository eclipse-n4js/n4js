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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.n4js.n4mf.ExtendedRuntimeEnvironment;
import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.n4mf.ProjectReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Extended Runtime Environment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ExtendedRuntimeEnvironmentImpl#getExtendedRuntimeEnvironment <em>Extended Runtime Environment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExtendedRuntimeEnvironmentImpl extends MinimalEObjectImpl.Container implements ExtendedRuntimeEnvironment {
	/**
	 * The cached value of the '{@link #getExtendedRuntimeEnvironment() <em>Extended Runtime Environment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtendedRuntimeEnvironment()
	 * @generated
	 * @ordered
	 */
	protected ProjectReference extendedRuntimeEnvironment;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtendedRuntimeEnvironmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4mfPackage.Literals.EXTENDED_RUNTIME_ENVIRONMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectReference getExtendedRuntimeEnvironment() {
		return extendedRuntimeEnvironment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExtendedRuntimeEnvironment(ProjectReference newExtendedRuntimeEnvironment, NotificationChain msgs) {
		ProjectReference oldExtendedRuntimeEnvironment = extendedRuntimeEnvironment;
		extendedRuntimeEnvironment = newExtendedRuntimeEnvironment;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4mfPackage.EXTENDED_RUNTIME_ENVIRONMENT__EXTENDED_RUNTIME_ENVIRONMENT, oldExtendedRuntimeEnvironment, newExtendedRuntimeEnvironment);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtendedRuntimeEnvironment(ProjectReference newExtendedRuntimeEnvironment) {
		if (newExtendedRuntimeEnvironment != extendedRuntimeEnvironment) {
			NotificationChain msgs = null;
			if (extendedRuntimeEnvironment != null)
				msgs = ((InternalEObject)extendedRuntimeEnvironment).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.EXTENDED_RUNTIME_ENVIRONMENT__EXTENDED_RUNTIME_ENVIRONMENT, null, msgs);
			if (newExtendedRuntimeEnvironment != null)
				msgs = ((InternalEObject)newExtendedRuntimeEnvironment).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.EXTENDED_RUNTIME_ENVIRONMENT__EXTENDED_RUNTIME_ENVIRONMENT, null, msgs);
			msgs = basicSetExtendedRuntimeEnvironment(newExtendedRuntimeEnvironment, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.EXTENDED_RUNTIME_ENVIRONMENT__EXTENDED_RUNTIME_ENVIRONMENT, newExtendedRuntimeEnvironment, newExtendedRuntimeEnvironment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4mfPackage.EXTENDED_RUNTIME_ENVIRONMENT__EXTENDED_RUNTIME_ENVIRONMENT:
				return basicSetExtendedRuntimeEnvironment(null, msgs);
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
			case N4mfPackage.EXTENDED_RUNTIME_ENVIRONMENT__EXTENDED_RUNTIME_ENVIRONMENT:
				return getExtendedRuntimeEnvironment();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case N4mfPackage.EXTENDED_RUNTIME_ENVIRONMENT__EXTENDED_RUNTIME_ENVIRONMENT:
				setExtendedRuntimeEnvironment((ProjectReference)newValue);
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
			case N4mfPackage.EXTENDED_RUNTIME_ENVIRONMENT__EXTENDED_RUNTIME_ENVIRONMENT:
				setExtendedRuntimeEnvironment((ProjectReference)null);
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
			case N4mfPackage.EXTENDED_RUNTIME_ENVIRONMENT__EXTENDED_RUNTIME_ENVIRONMENT:
				return extendedRuntimeEnvironment != null;
		}
		return super.eIsSet(featureID);
	}

} //ExtendedRuntimeEnvironmentImpl
