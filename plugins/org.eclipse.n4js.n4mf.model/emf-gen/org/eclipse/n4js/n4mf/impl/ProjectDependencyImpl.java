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

import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.n4mf.ProjectDependency;

import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project Dependency</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDependencyImpl#getVersionRequirement <em>Version Requirement</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDependencyImpl#getVersionRequirementString <em>Version Requirement String</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProjectDependencyImpl extends ProjectReferenceImpl implements ProjectDependency {
	/**
	 * The cached value of the '{@link #getVersionRequirement() <em>Version Requirement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersionRequirement()
	 * @generated
	 * @ordered
	 */
	protected NPMVersionRequirement versionRequirement;

	/**
	 * The default value of the '{@link #getVersionRequirementString() <em>Version Requirement String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersionRequirementString()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_REQUIREMENT_STRING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersionRequirementString() <em>Version Requirement String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersionRequirementString()
	 * @generated
	 * @ordered
	 */
	protected String versionRequirementString = VERSION_REQUIREMENT_STRING_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProjectDependencyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4mfPackage.Literals.PROJECT_DEPENDENCY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NPMVersionRequirement getVersionRequirement() {
		return versionRequirement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVersionRequirement(NPMVersionRequirement newVersionRequirement, NotificationChain msgs) {
		NPMVersionRequirement oldVersionRequirement = versionRequirement;
		versionRequirement = newVersionRequirement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DEPENDENCY__VERSION_REQUIREMENT, oldVersionRequirement, newVersionRequirement);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersionRequirement(NPMVersionRequirement newVersionRequirement) {
		if (newVersionRequirement != versionRequirement) {
			NotificationChain msgs = null;
			if (versionRequirement != null)
				msgs = ((InternalEObject)versionRequirement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DEPENDENCY__VERSION_REQUIREMENT, null, msgs);
			if (newVersionRequirement != null)
				msgs = ((InternalEObject)newVersionRequirement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DEPENDENCY__VERSION_REQUIREMENT, null, msgs);
			msgs = basicSetVersionRequirement(newVersionRequirement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DEPENDENCY__VERSION_REQUIREMENT, newVersionRequirement, newVersionRequirement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersionRequirementString() {
		return versionRequirementString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersionRequirementString(String newVersionRequirementString) {
		String oldVersionRequirementString = versionRequirementString;
		versionRequirementString = newVersionRequirementString;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DEPENDENCY__VERSION_REQUIREMENT_STRING, oldVersionRequirementString, versionRequirementString));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4mfPackage.PROJECT_DEPENDENCY__VERSION_REQUIREMENT:
				return basicSetVersionRequirement(null, msgs);
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
			case N4mfPackage.PROJECT_DEPENDENCY__VERSION_REQUIREMENT:
				return getVersionRequirement();
			case N4mfPackage.PROJECT_DEPENDENCY__VERSION_REQUIREMENT_STRING:
				return getVersionRequirementString();
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
			case N4mfPackage.PROJECT_DEPENDENCY__VERSION_REQUIREMENT:
				setVersionRequirement((NPMVersionRequirement)newValue);
				return;
			case N4mfPackage.PROJECT_DEPENDENCY__VERSION_REQUIREMENT_STRING:
				setVersionRequirementString((String)newValue);
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
			case N4mfPackage.PROJECT_DEPENDENCY__VERSION_REQUIREMENT:
				setVersionRequirement((NPMVersionRequirement)null);
				return;
			case N4mfPackage.PROJECT_DEPENDENCY__VERSION_REQUIREMENT_STRING:
				setVersionRequirementString(VERSION_REQUIREMENT_STRING_EDEFAULT);
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
			case N4mfPackage.PROJECT_DEPENDENCY__VERSION_REQUIREMENT:
				return versionRequirement != null;
			case N4mfPackage.PROJECT_DEPENDENCY__VERSION_REQUIREMENT_STRING:
				return VERSION_REQUIREMENT_STRING_EDEFAULT == null ? versionRequirementString != null : !VERSION_REQUIREMENT_STRING_EDEFAULT.equals(versionRequirementString);
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (versionRequirementString: ");
		result.append(versionRequirementString);
		result.append(')');
		return result.toString();
	}

} //ProjectDependencyImpl
