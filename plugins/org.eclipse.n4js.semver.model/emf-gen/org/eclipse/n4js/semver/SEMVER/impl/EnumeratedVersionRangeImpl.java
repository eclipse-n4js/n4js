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
package org.eclipse.n4js.semver.SEMVER.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.semver.SEMVER.EnumeratedVersionRange;
import org.eclipse.n4js.semver.SEMVER.SEMVERPackage;
import org.eclipse.n4js.semver.SEMVER.SimpleVersion;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enumerated Version Range</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.impl.EnumeratedVersionRangeImpl#getSimpleVersions <em>Simple Versions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EnumeratedVersionRangeImpl extends VersionRangeImpl implements EnumeratedVersionRange {
	/**
	 * The cached value of the '{@link #getSimpleVersions() <em>Simple Versions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimpleVersions()
	 * @generated
	 * @ordered
	 */
	protected EList<SimpleVersion> simpleVersions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnumeratedVersionRangeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SEMVERPackage.Literals.ENUMERATED_VERSION_RANGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SimpleVersion> getSimpleVersions() {
		if (simpleVersions == null) {
			simpleVersions = new EObjectContainmentEList<SimpleVersion>(SimpleVersion.class, this, SEMVERPackage.ENUMERATED_VERSION_RANGE__SIMPLE_VERSIONS);
		}
		return simpleVersions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SEMVERPackage.ENUMERATED_VERSION_RANGE__SIMPLE_VERSIONS:
				return ((InternalEList<?>)getSimpleVersions()).basicRemove(otherEnd, msgs);
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
			case SEMVERPackage.ENUMERATED_VERSION_RANGE__SIMPLE_VERSIONS:
				return getSimpleVersions();
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
			case SEMVERPackage.ENUMERATED_VERSION_RANGE__SIMPLE_VERSIONS:
				getSimpleVersions().clear();
				getSimpleVersions().addAll((Collection<? extends SimpleVersion>)newValue);
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
			case SEMVERPackage.ENUMERATED_VERSION_RANGE__SIMPLE_VERSIONS:
				getSimpleVersions().clear();
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
			case SEMVERPackage.ENUMERATED_VERSION_RANGE__SIMPLE_VERSIONS:
				return simpleVersions != null && !simpleVersions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //EnumeratedVersionRangeImpl
