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

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.n4js.n4mf.DeclaredVersion;
import org.eclipse.n4js.n4mf.N4mfPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Declared Version</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.DeclaredVersionImpl#getMajor <em>Major</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.DeclaredVersionImpl#getMinor <em>Minor</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.DeclaredVersionImpl#getMicro <em>Micro</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.DeclaredVersionImpl#getQualifier <em>Qualifier</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.DeclaredVersionImpl#getBuildMetaData <em>Build Meta Data</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DeclaredVersionImpl extends MinimalEObjectImpl.Container implements DeclaredVersion {
	/**
	 * The default value of the '{@link #getMajor() <em>Major</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMajor()
	 * @generated
	 * @ordered
	 */
	protected static final int MAJOR_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMajor() <em>Major</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMajor()
	 * @generated
	 * @ordered
	 */
	protected int major = MAJOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getMinor() <em>Minor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinor()
	 * @generated
	 * @ordered
	 */
	protected static final int MINOR_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMinor() <em>Minor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinor()
	 * @generated
	 * @ordered
	 */
	protected int minor = MINOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getMicro() <em>Micro</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMicro()
	 * @generated
	 * @ordered
	 */
	protected static final int MICRO_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMicro() <em>Micro</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMicro()
	 * @generated
	 * @ordered
	 */
	protected int micro = MICRO_EDEFAULT;

	/**
	 * The default value of the '{@link #getQualifier() <em>Qualifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifier()
	 * @generated
	 * @ordered
	 */
	protected static final String QUALIFIER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getQualifier() <em>Qualifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifier()
	 * @generated
	 * @ordered
	 */
	protected String qualifier = QUALIFIER_EDEFAULT;

	/**
	 * The default value of the '{@link #getBuildMetaData() <em>Build Meta Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuildMetaData()
	 * @generated
	 * @ordered
	 */
	protected static final String BUILD_META_DATA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBuildMetaData() <em>Build Meta Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuildMetaData()
	 * @generated
	 * @ordered
	 */
	protected String buildMetaData = BUILD_META_DATA_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeclaredVersionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4mfPackage.Literals.DECLARED_VERSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMajor() {
		return major;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMajor(int newMajor) {
		int oldMajor = major;
		major = newMajor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.DECLARED_VERSION__MAJOR, oldMajor, major));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMinor() {
		return minor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinor(int newMinor) {
		int oldMinor = minor;
		minor = newMinor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.DECLARED_VERSION__MINOR, oldMinor, minor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMicro() {
		return micro;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMicro(int newMicro) {
		int oldMicro = micro;
		micro = newMicro;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.DECLARED_VERSION__MICRO, oldMicro, micro));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getQualifier() {
		return qualifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQualifier(String newQualifier) {
		String oldQualifier = qualifier;
		qualifier = newQualifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.DECLARED_VERSION__QUALIFIER, oldQualifier, qualifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBuildMetaData() {
		return buildMetaData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBuildMetaData(String newBuildMetaData) {
		String oldBuildMetaData = buildMetaData;
		buildMetaData = newBuildMetaData;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.DECLARED_VERSION__BUILD_META_DATA, oldBuildMetaData, buildMetaData));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case N4mfPackage.DECLARED_VERSION__MAJOR:
				return getMajor();
			case N4mfPackage.DECLARED_VERSION__MINOR:
				return getMinor();
			case N4mfPackage.DECLARED_VERSION__MICRO:
				return getMicro();
			case N4mfPackage.DECLARED_VERSION__QUALIFIER:
				return getQualifier();
			case N4mfPackage.DECLARED_VERSION__BUILD_META_DATA:
				return getBuildMetaData();
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
			case N4mfPackage.DECLARED_VERSION__MAJOR:
				setMajor((Integer)newValue);
				return;
			case N4mfPackage.DECLARED_VERSION__MINOR:
				setMinor((Integer)newValue);
				return;
			case N4mfPackage.DECLARED_VERSION__MICRO:
				setMicro((Integer)newValue);
				return;
			case N4mfPackage.DECLARED_VERSION__QUALIFIER:
				setQualifier((String)newValue);
				return;
			case N4mfPackage.DECLARED_VERSION__BUILD_META_DATA:
				setBuildMetaData((String)newValue);
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
			case N4mfPackage.DECLARED_VERSION__MAJOR:
				setMajor(MAJOR_EDEFAULT);
				return;
			case N4mfPackage.DECLARED_VERSION__MINOR:
				setMinor(MINOR_EDEFAULT);
				return;
			case N4mfPackage.DECLARED_VERSION__MICRO:
				setMicro(MICRO_EDEFAULT);
				return;
			case N4mfPackage.DECLARED_VERSION__QUALIFIER:
				setQualifier(QUALIFIER_EDEFAULT);
				return;
			case N4mfPackage.DECLARED_VERSION__BUILD_META_DATA:
				setBuildMetaData(BUILD_META_DATA_EDEFAULT);
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
			case N4mfPackage.DECLARED_VERSION__MAJOR:
				return major != MAJOR_EDEFAULT;
			case N4mfPackage.DECLARED_VERSION__MINOR:
				return minor != MINOR_EDEFAULT;
			case N4mfPackage.DECLARED_VERSION__MICRO:
				return micro != MICRO_EDEFAULT;
			case N4mfPackage.DECLARED_VERSION__QUALIFIER:
				return QUALIFIER_EDEFAULT == null ? qualifier != null : !QUALIFIER_EDEFAULT.equals(qualifier);
			case N4mfPackage.DECLARED_VERSION__BUILD_META_DATA:
				return BUILD_META_DATA_EDEFAULT == null ? buildMetaData != null : !BUILD_META_DATA_EDEFAULT.equals(buildMetaData);
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
		result.append(" (major: ");
		result.append(major);
		result.append(", minor: ");
		result.append(minor);
		result.append(", micro: ");
		result.append(micro);
		result.append(", qualifier: ");
		result.append(qualifier);
		result.append(", buildMetaData: ");
		result.append(buildMetaData);
		result.append(')');
		return result.toString();
	}

} //DeclaredVersionImpl
