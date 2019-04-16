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
import org.eclipse.n4js.semver.Semver.SimpleVersion;
import org.eclipse.n4js.semver.Semver.VersionRangeConstraint;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Version Range Constraint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.VersionRangeConstraintImpl#getVersionConstraints <em>Version Constraints</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VersionRangeConstraintImpl extends VersionRangeImpl implements VersionRangeConstraint {
	/**
	 * The cached value of the '{@link #getVersionConstraints() <em>Version Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersionConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<SimpleVersion> versionConstraints;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VersionRangeConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SemverPackage.Literals.VERSION_RANGE_CONSTRAINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SimpleVersion> getVersionConstraints() {
		if (versionConstraints == null) {
			versionConstraints = new EObjectContainmentEList<SimpleVersion>(SimpleVersion.class, this, SemverPackage.VERSION_RANGE_CONSTRAINT__VERSION_CONSTRAINTS);
		}
		return versionConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SemverPackage.VERSION_RANGE_CONSTRAINT__VERSION_CONSTRAINTS:
				return ((InternalEList<?>)getVersionConstraints()).basicRemove(otherEnd, msgs);
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
			case SemverPackage.VERSION_RANGE_CONSTRAINT__VERSION_CONSTRAINTS:
				return getVersionConstraints();
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
			case SemverPackage.VERSION_RANGE_CONSTRAINT__VERSION_CONSTRAINTS:
				getVersionConstraints().clear();
				getVersionConstraints().addAll((Collection<? extends SimpleVersion>)newValue);
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
			case SemverPackage.VERSION_RANGE_CONSTRAINT__VERSION_CONSTRAINTS:
				getVersionConstraints().clear();
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
			case SemverPackage.VERSION_RANGE_CONSTRAINT__VERSION_CONSTRAINTS:
				return versionConstraints != null && !versionConstraints.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //VersionRangeConstraintImpl
