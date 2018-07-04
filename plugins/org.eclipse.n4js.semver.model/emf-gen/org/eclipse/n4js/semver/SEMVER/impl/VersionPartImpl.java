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

import org.eclipse.n4js.semver.SEMVER.SEMVERPackage;
import org.eclipse.n4js.semver.SEMVER.VersionPart;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Version Part</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.impl.VersionPartImpl#isWildcard <em>Wildcard</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.impl.VersionPartImpl#getNumber <em>Number</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VersionPartImpl extends MinimalEObjectImpl.Container implements VersionPart {
	/**
	 * The default value of the '{@link #isWildcard() <em>Wildcard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWildcard()
	 * @generated
	 * @ordered
	 */
	protected static final boolean WILDCARD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isWildcard() <em>Wildcard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWildcard()
	 * @generated
	 * @ordered
	 */
	protected boolean wildcard = WILDCARD_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumber() <em>Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumber()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumber() <em>Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumber()
	 * @generated
	 * @ordered
	 */
	protected int number = NUMBER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VersionPartImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SEMVERPackage.Literals.VERSION_PART;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isWildcard() {
		return wildcard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWildcard(boolean newWildcard) {
		boolean oldWildcard = wildcard;
		wildcard = newWildcard;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SEMVERPackage.VERSION_PART__WILDCARD, oldWildcard, wildcard));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumber(int newNumber) {
		int oldNumber = number;
		number = newNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SEMVERPackage.VERSION_PART__NUMBER, oldNumber, number));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SEMVERPackage.VERSION_PART__WILDCARD:
				return isWildcard();
			case SEMVERPackage.VERSION_PART__NUMBER:
				return getNumber();
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
			case SEMVERPackage.VERSION_PART__WILDCARD:
				setWildcard((Boolean)newValue);
				return;
			case SEMVERPackage.VERSION_PART__NUMBER:
				setNumber((Integer)newValue);
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
			case SEMVERPackage.VERSION_PART__WILDCARD:
				setWildcard(WILDCARD_EDEFAULT);
				return;
			case SEMVERPackage.VERSION_PART__NUMBER:
				setNumber(NUMBER_EDEFAULT);
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
			case SEMVERPackage.VERSION_PART__WILDCARD:
				return wildcard != WILDCARD_EDEFAULT;
			case SEMVERPackage.VERSION_PART__NUMBER:
				return number != NUMBER_EDEFAULT;
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
		result.append(" (wildcard: ");
		result.append(wildcard);
		result.append(", number: ");
		result.append(number);
		result.append(')');
		return result.toString();
	}

} //VersionPartImpl
