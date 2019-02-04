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

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.semver.Semver.SemverPackage;
import org.eclipse.n4js.semver.Semver.VersionRange;
import org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Version Range Set Requirement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.VersionRangeSetRequirementImpl#getRanges <em>Ranges</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VersionRangeSetRequirementImpl extends NPMVersionRequirementImpl implements VersionRangeSetRequirement {
	/**
	 * The cached value of the '{@link #getRanges() <em>Ranges</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRanges()
	 * @generated
	 * @ordered
	 */
	protected EList<VersionRange> ranges;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VersionRangeSetRequirementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SemverPackage.Literals.VERSION_RANGE_SET_REQUIREMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VersionRange> getRanges() {
		if (ranges == null) {
			ranges = new EObjectContainmentEList<VersionRange>(VersionRange.class, this, SemverPackage.VERSION_RANGE_SET_REQUIREMENT__RANGES);
		}
		return ranges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SemverPackage.VERSION_RANGE_SET_REQUIREMENT__RANGES:
				return ((InternalEList<?>)getRanges()).basicRemove(otherEnd, msgs);
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
			case SemverPackage.VERSION_RANGE_SET_REQUIREMENT__RANGES:
				return getRanges();
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
			case SemverPackage.VERSION_RANGE_SET_REQUIREMENT__RANGES:
				getRanges().clear();
				getRanges().addAll((Collection<? extends VersionRange>)newValue);
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
			case SemverPackage.VERSION_RANGE_SET_REQUIREMENT__RANGES:
				getRanges().clear();
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
			case SemverPackage.VERSION_RANGE_SET_REQUIREMENT__RANGES:
				return ranges != null && !ranges.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //VersionRangeSetRequirementImpl
