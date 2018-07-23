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
package org.eclipse.n4js.semver.Semver.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.semver.Semver.SemverPackage;
import org.eclipse.n4js.semver.Semver.SimpleVersion;
import org.eclipse.n4js.semver.Semver.URLSemver;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>URL Semver</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.URLSemverImpl#getSimpleVersion <em>Simple Version</em>}</li>
 * </ul>
 *
 * @generated
 */
public class URLSemverImpl extends URLVersionSpecifierImpl implements URLSemver {
	/**
	 * The cached value of the '{@link #getSimpleVersion() <em>Simple Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimpleVersion()
	 * @generated
	 * @ordered
	 */
	protected SimpleVersion simpleVersion;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected URLSemverImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SemverPackage.Literals.URL_SEMVER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleVersion getSimpleVersion() {
		return simpleVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSimpleVersion(SimpleVersion newSimpleVersion, NotificationChain msgs) {
		SimpleVersion oldSimpleVersion = simpleVersion;
		simpleVersion = newSimpleVersion;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SemverPackage.URL_SEMVER__SIMPLE_VERSION, oldSimpleVersion, newSimpleVersion);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSimpleVersion(SimpleVersion newSimpleVersion) {
		if (newSimpleVersion != simpleVersion) {
			NotificationChain msgs = null;
			if (simpleVersion != null)
				msgs = ((InternalEObject)simpleVersion).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SemverPackage.URL_SEMVER__SIMPLE_VERSION, null, msgs);
			if (newSimpleVersion != null)
				msgs = ((InternalEObject)newSimpleVersion).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SemverPackage.URL_SEMVER__SIMPLE_VERSION, null, msgs);
			msgs = basicSetSimpleVersion(newSimpleVersion, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SemverPackage.URL_SEMVER__SIMPLE_VERSION, newSimpleVersion, newSimpleVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SemverPackage.URL_SEMVER__SIMPLE_VERSION:
				return basicSetSimpleVersion(null, msgs);
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
			case SemverPackage.URL_SEMVER__SIMPLE_VERSION:
				return getSimpleVersion();
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
			case SemverPackage.URL_SEMVER__SIMPLE_VERSION:
				setSimpleVersion((SimpleVersion)newValue);
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
			case SemverPackage.URL_SEMVER__SIMPLE_VERSION:
				setSimpleVersion((SimpleVersion)null);
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
			case SemverPackage.URL_SEMVER__SIMPLE_VERSION:
				return simpleVersion != null;
		}
		return super.eIsSet(featureID);
	}

} //URLSemverImpl
