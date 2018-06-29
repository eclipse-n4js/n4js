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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

import org.eclipse.n4js.semver.SEMVER.Qualifier;
import org.eclipse.n4js.semver.SEMVER.SEMVERPackage;
import org.eclipse.n4js.semver.SEMVER.VersionNumber;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Version Number</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.impl.VersionNumberImpl#getQualifier <em>Qualifier</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.impl.VersionNumberImpl#getMajor <em>Major</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.impl.VersionNumberImpl#getMinor <em>Minor</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.impl.VersionNumberImpl#getPatch <em>Patch</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.impl.VersionNumberImpl#getExtended <em>Extended</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VersionNumberImpl extends MinimalEObjectImpl.Container implements VersionNumber {
	/**
	 * The cached value of the '{@link #getQualifier() <em>Qualifier</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifier()
	 * @generated
	 * @ordered
	 */
	protected Qualifier qualifier;

	/**
	 * The default value of the '{@link #getMajor() <em>Major</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMajor()
	 * @generated
	 * @ordered
	 */
	protected static final String MAJOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMajor() <em>Major</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMajor()
	 * @generated
	 * @ordered
	 */
	protected String major = MAJOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getMinor() <em>Minor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinor()
	 * @generated
	 * @ordered
	 */
	protected static final String MINOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMinor() <em>Minor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinor()
	 * @generated
	 * @ordered
	 */
	protected String minor = MINOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getPatch() <em>Patch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPatch()
	 * @generated
	 * @ordered
	 */
	protected static final String PATCH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPatch() <em>Patch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPatch()
	 * @generated
	 * @ordered
	 */
	protected String patch = PATCH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExtended() <em>Extended</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtended()
	 * @generated
	 * @ordered
	 */
	protected EList<String> extended;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VersionNumberImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SEMVERPackage.Literals.VERSION_NUMBER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Qualifier getQualifier() {
		return qualifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetQualifier(Qualifier newQualifier, NotificationChain msgs) {
		Qualifier oldQualifier = qualifier;
		qualifier = newQualifier;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SEMVERPackage.VERSION_NUMBER__QUALIFIER, oldQualifier, newQualifier);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQualifier(Qualifier newQualifier) {
		if (newQualifier != qualifier) {
			NotificationChain msgs = null;
			if (qualifier != null)
				msgs = ((InternalEObject)qualifier).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SEMVERPackage.VERSION_NUMBER__QUALIFIER, null, msgs);
			if (newQualifier != null)
				msgs = ((InternalEObject)newQualifier).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SEMVERPackage.VERSION_NUMBER__QUALIFIER, null, msgs);
			msgs = basicSetQualifier(newQualifier, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SEMVERPackage.VERSION_NUMBER__QUALIFIER, newQualifier, newQualifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMajor() {
		return major;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMajor(String newMajor) {
		String oldMajor = major;
		major = newMajor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SEMVERPackage.VERSION_NUMBER__MAJOR, oldMajor, major));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMinor() {
		return minor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinor(String newMinor) {
		String oldMinor = minor;
		minor = newMinor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SEMVERPackage.VERSION_NUMBER__MINOR, oldMinor, minor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPatch() {
		return patch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPatch(String newPatch) {
		String oldPatch = patch;
		patch = newPatch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SEMVERPackage.VERSION_NUMBER__PATCH, oldPatch, patch));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getExtended() {
		if (extended == null) {
			extended = new EDataTypeEList<String>(String.class, this, SEMVERPackage.VERSION_NUMBER__EXTENDED);
		}
		return extended;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SEMVERPackage.VERSION_NUMBER__QUALIFIER:
				return basicSetQualifier(null, msgs);
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
			case SEMVERPackage.VERSION_NUMBER__QUALIFIER:
				return getQualifier();
			case SEMVERPackage.VERSION_NUMBER__MAJOR:
				return getMajor();
			case SEMVERPackage.VERSION_NUMBER__MINOR:
				return getMinor();
			case SEMVERPackage.VERSION_NUMBER__PATCH:
				return getPatch();
			case SEMVERPackage.VERSION_NUMBER__EXTENDED:
				return getExtended();
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
			case SEMVERPackage.VERSION_NUMBER__QUALIFIER:
				setQualifier((Qualifier)newValue);
				return;
			case SEMVERPackage.VERSION_NUMBER__MAJOR:
				setMajor((String)newValue);
				return;
			case SEMVERPackage.VERSION_NUMBER__MINOR:
				setMinor((String)newValue);
				return;
			case SEMVERPackage.VERSION_NUMBER__PATCH:
				setPatch((String)newValue);
				return;
			case SEMVERPackage.VERSION_NUMBER__EXTENDED:
				getExtended().clear();
				getExtended().addAll((Collection<? extends String>)newValue);
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
			case SEMVERPackage.VERSION_NUMBER__QUALIFIER:
				setQualifier((Qualifier)null);
				return;
			case SEMVERPackage.VERSION_NUMBER__MAJOR:
				setMajor(MAJOR_EDEFAULT);
				return;
			case SEMVERPackage.VERSION_NUMBER__MINOR:
				setMinor(MINOR_EDEFAULT);
				return;
			case SEMVERPackage.VERSION_NUMBER__PATCH:
				setPatch(PATCH_EDEFAULT);
				return;
			case SEMVERPackage.VERSION_NUMBER__EXTENDED:
				getExtended().clear();
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
			case SEMVERPackage.VERSION_NUMBER__QUALIFIER:
				return qualifier != null;
			case SEMVERPackage.VERSION_NUMBER__MAJOR:
				return MAJOR_EDEFAULT == null ? major != null : !MAJOR_EDEFAULT.equals(major);
			case SEMVERPackage.VERSION_NUMBER__MINOR:
				return MINOR_EDEFAULT == null ? minor != null : !MINOR_EDEFAULT.equals(minor);
			case SEMVERPackage.VERSION_NUMBER__PATCH:
				return PATCH_EDEFAULT == null ? patch != null : !PATCH_EDEFAULT.equals(patch);
			case SEMVERPackage.VERSION_NUMBER__EXTENDED:
				return extended != null && !extended.isEmpty();
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
		result.append(", patch: ");
		result.append(patch);
		result.append(", extended: ");
		result.append(extended);
		result.append(')');
		return result.toString();
	}

} //VersionNumberImpl
