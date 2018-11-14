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
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.URLSemverImpl#isWithSemverTag <em>With Semver Tag</em>}</li>
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
	 * The default value of the '{@link #isWithSemverTag() <em>With Semver Tag</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWithSemverTag()
	 * @generated
	 * @ordered
	 */
	protected static final boolean WITH_SEMVER_TAG_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isWithSemverTag() <em>With Semver Tag</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWithSemverTag()
	 * @generated
	 * @ordered
	 */
	protected boolean withSemverTag = WITH_SEMVER_TAG_EDEFAULT;

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
	public boolean isWithSemverTag() {
		return withSemverTag;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWithSemverTag(boolean newWithSemverTag) {
		boolean oldWithSemverTag = withSemverTag;
		withSemverTag = newWithSemverTag;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SemverPackage.URL_SEMVER__WITH_SEMVER_TAG, oldWithSemverTag, withSemverTag));
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
			case SemverPackage.URL_SEMVER__WITH_SEMVER_TAG:
				return isWithSemverTag();
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
			case SemverPackage.URL_SEMVER__WITH_SEMVER_TAG:
				setWithSemverTag((Boolean)newValue);
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
			case SemverPackage.URL_SEMVER__WITH_SEMVER_TAG:
				setWithSemverTag(WITH_SEMVER_TAG_EDEFAULT);
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
			case SemverPackage.URL_SEMVER__WITH_SEMVER_TAG:
				return withSemverTag != WITH_SEMVER_TAG_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (withSemverTag: ");
		result.append(withSemverTag);
		result.append(')');
		return result.toString();
	}

} //URLSemverImpl
