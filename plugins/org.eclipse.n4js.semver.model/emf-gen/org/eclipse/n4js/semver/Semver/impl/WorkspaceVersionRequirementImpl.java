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
import org.eclipse.n4js.semver.Semver.WorkspaceVersionRequirement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Workspace Version Requirement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.WorkspaceVersionRequirementImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.WorkspaceVersionRequirementImpl#getOtherVersion <em>Other Version</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WorkspaceVersionRequirementImpl extends NPMVersionRequirementImpl implements WorkspaceVersionRequirement {
	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected SimpleVersion version;

	/**
	 * The default value of the '{@link #getOtherVersion() <em>Other Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOtherVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String OTHER_VERSION_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getOtherVersion() <em>Other Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOtherVersion()
	 * @generated
	 * @ordered
	 */
	protected String otherVersion = OTHER_VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WorkspaceVersionRequirementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SemverPackage.Literals.WORKSPACE_VERSION_REQUIREMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SimpleVersion getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVersion(SimpleVersion newVersion, NotificationChain msgs) {
		SimpleVersion oldVersion = version;
		version = newVersion;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SemverPackage.WORKSPACE_VERSION_REQUIREMENT__VERSION, oldVersion, newVersion);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVersion(SimpleVersion newVersion) {
		if (newVersion != version) {
			NotificationChain msgs = null;
			if (version != null)
				msgs = ((InternalEObject)version).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SemverPackage.WORKSPACE_VERSION_REQUIREMENT__VERSION, null, msgs);
			if (newVersion != null)
				msgs = ((InternalEObject)newVersion).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SemverPackage.WORKSPACE_VERSION_REQUIREMENT__VERSION, null, msgs);
			msgs = basicSetVersion(newVersion, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SemverPackage.WORKSPACE_VERSION_REQUIREMENT__VERSION, newVersion, newVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getOtherVersion() {
		return otherVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOtherVersion(String newOtherVersion) {
		String oldOtherVersion = otherVersion;
		otherVersion = newOtherVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SemverPackage.WORKSPACE_VERSION_REQUIREMENT__OTHER_VERSION, oldOtherVersion, otherVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SemverPackage.WORKSPACE_VERSION_REQUIREMENT__VERSION:
				return basicSetVersion(null, msgs);
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
			case SemverPackage.WORKSPACE_VERSION_REQUIREMENT__VERSION:
				return getVersion();
			case SemverPackage.WORKSPACE_VERSION_REQUIREMENT__OTHER_VERSION:
				return getOtherVersion();
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
			case SemverPackage.WORKSPACE_VERSION_REQUIREMENT__VERSION:
				setVersion((SimpleVersion)newValue);
				return;
			case SemverPackage.WORKSPACE_VERSION_REQUIREMENT__OTHER_VERSION:
				setOtherVersion((String)newValue);
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
			case SemverPackage.WORKSPACE_VERSION_REQUIREMENT__VERSION:
				setVersion((SimpleVersion)null);
				return;
			case SemverPackage.WORKSPACE_VERSION_REQUIREMENT__OTHER_VERSION:
				setOtherVersion(OTHER_VERSION_EDEFAULT);
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
			case SemverPackage.WORKSPACE_VERSION_REQUIREMENT__VERSION:
				return version != null;
			case SemverPackage.WORKSPACE_VERSION_REQUIREMENT__OTHER_VERSION:
				return OTHER_VERSION_EDEFAULT == null ? otherVersion != null : !OTHER_VERSION_EDEFAULT.equals(otherVersion);
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
		result.append(" (otherVersion: ");
		result.append(otherVersion);
		result.append(')');
		return result.toString();
	}

} //WorkspaceVersionRequirementImpl
