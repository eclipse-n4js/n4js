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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.n4js.semver.SEMVER.Qualifier;
import org.eclipse.n4js.semver.SEMVER.SEMVERPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Qualifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.impl.QualifierImpl#getPreRelease <em>Pre Release</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.impl.QualifierImpl#getBuildMetadata <em>Build Metadata</em>}</li>
 * </ul>
 *
 * @generated
 */
public class QualifierImpl extends MinimalEObjectImpl.Container implements Qualifier {
	/**
	 * The default value of the '{@link #getPreRelease() <em>Pre Release</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreRelease()
	 * @generated
	 * @ordered
	 */
	protected static final String PRE_RELEASE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPreRelease() <em>Pre Release</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreRelease()
	 * @generated
	 * @ordered
	 */
	protected String preRelease = PRE_RELEASE_EDEFAULT;

	/**
	 * The default value of the '{@link #getBuildMetadata() <em>Build Metadata</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuildMetadata()
	 * @generated
	 * @ordered
	 */
	protected static final String BUILD_METADATA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBuildMetadata() <em>Build Metadata</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuildMetadata()
	 * @generated
	 * @ordered
	 */
	protected String buildMetadata = BUILD_METADATA_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected QualifierImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SEMVERPackage.Literals.QUALIFIER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPreRelease() {
		return preRelease;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreRelease(String newPreRelease) {
		String oldPreRelease = preRelease;
		preRelease = newPreRelease;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SEMVERPackage.QUALIFIER__PRE_RELEASE, oldPreRelease, preRelease));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBuildMetadata() {
		return buildMetadata;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBuildMetadata(String newBuildMetadata) {
		String oldBuildMetadata = buildMetadata;
		buildMetadata = newBuildMetadata;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SEMVERPackage.QUALIFIER__BUILD_METADATA, oldBuildMetadata, buildMetadata));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SEMVERPackage.QUALIFIER__PRE_RELEASE:
				return getPreRelease();
			case SEMVERPackage.QUALIFIER__BUILD_METADATA:
				return getBuildMetadata();
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
			case SEMVERPackage.QUALIFIER__PRE_RELEASE:
				setPreRelease((String)newValue);
				return;
			case SEMVERPackage.QUALIFIER__BUILD_METADATA:
				setBuildMetadata((String)newValue);
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
			case SEMVERPackage.QUALIFIER__PRE_RELEASE:
				setPreRelease(PRE_RELEASE_EDEFAULT);
				return;
			case SEMVERPackage.QUALIFIER__BUILD_METADATA:
				setBuildMetadata(BUILD_METADATA_EDEFAULT);
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
			case SEMVERPackage.QUALIFIER__PRE_RELEASE:
				return PRE_RELEASE_EDEFAULT == null ? preRelease != null : !PRE_RELEASE_EDEFAULT.equals(preRelease);
			case SEMVERPackage.QUALIFIER__BUILD_METADATA:
				return BUILD_METADATA_EDEFAULT == null ? buildMetadata != null : !BUILD_METADATA_EDEFAULT.equals(buildMetadata);
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
		result.append(" (preRelease: ");
		result.append(preRelease);
		result.append(", buildMetadata: ");
		result.append(buildMetadata);
		result.append(')');
		return result.toString();
	}

} //QualifierImpl
