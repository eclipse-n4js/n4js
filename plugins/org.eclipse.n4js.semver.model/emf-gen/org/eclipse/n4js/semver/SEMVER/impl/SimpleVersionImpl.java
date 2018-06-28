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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.n4js.semver.SEMVER.SEMVERPackage;
import org.eclipse.n4js.semver.SEMVER.SimpleVersion;
import org.eclipse.n4js.semver.SEMVER.VersionComparator;
import org.eclipse.n4js.semver.SEMVER.VersionNumber;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simple Version</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.impl.SimpleVersionImpl#getNumber <em>Number</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.impl.SimpleVersionImpl#getComparator <em>Comparator</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.impl.SimpleVersionImpl#isHasTilde <em>Has Tilde</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.impl.SimpleVersionImpl#isHasCaret <em>Has Caret</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SimpleVersionImpl extends MinimalEObjectImpl.Container implements SimpleVersion {
	/**
	 * The cached value of the '{@link #getNumber() <em>Number</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumber()
	 * @generated
	 * @ordered
	 */
	protected VersionNumber number;

	/**
	 * The default value of the '{@link #getComparator() <em>Comparator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComparator()
	 * @generated
	 * @ordered
	 */
	protected static final VersionComparator COMPARATOR_EDEFAULT = VersionComparator.EQUALS;

	/**
	 * The cached value of the '{@link #getComparator() <em>Comparator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComparator()
	 * @generated
	 * @ordered
	 */
	protected VersionComparator comparator = COMPARATOR_EDEFAULT;

	/**
	 * The default value of the '{@link #isHasTilde() <em>Has Tilde</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasTilde()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HAS_TILDE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHasTilde() <em>Has Tilde</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasTilde()
	 * @generated
	 * @ordered
	 */
	protected boolean hasTilde = HAS_TILDE_EDEFAULT;

	/**
	 * The default value of the '{@link #isHasCaret() <em>Has Caret</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasCaret()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HAS_CARET_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHasCaret() <em>Has Caret</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasCaret()
	 * @generated
	 * @ordered
	 */
	protected boolean hasCaret = HAS_CARET_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimpleVersionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SEMVERPackage.Literals.SIMPLE_VERSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VersionNumber getNumber() {
		return number;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNumber(VersionNumber newNumber, NotificationChain msgs) {
		VersionNumber oldNumber = number;
		number = newNumber;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SEMVERPackage.SIMPLE_VERSION__NUMBER, oldNumber, newNumber);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumber(VersionNumber newNumber) {
		if (newNumber != number) {
			NotificationChain msgs = null;
			if (number != null)
				msgs = ((InternalEObject)number).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SEMVERPackage.SIMPLE_VERSION__NUMBER, null, msgs);
			if (newNumber != null)
				msgs = ((InternalEObject)newNumber).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SEMVERPackage.SIMPLE_VERSION__NUMBER, null, msgs);
			msgs = basicSetNumber(newNumber, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SEMVERPackage.SIMPLE_VERSION__NUMBER, newNumber, newNumber));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VersionComparator getComparator() {
		return comparator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComparator(VersionComparator newComparator) {
		VersionComparator oldComparator = comparator;
		comparator = newComparator == null ? COMPARATOR_EDEFAULT : newComparator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SEMVERPackage.SIMPLE_VERSION__COMPARATOR, oldComparator, comparator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHasTilde() {
		return hasTilde;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasTilde(boolean newHasTilde) {
		boolean oldHasTilde = hasTilde;
		hasTilde = newHasTilde;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SEMVERPackage.SIMPLE_VERSION__HAS_TILDE, oldHasTilde, hasTilde));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHasCaret() {
		return hasCaret;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasCaret(boolean newHasCaret) {
		boolean oldHasCaret = hasCaret;
		hasCaret = newHasCaret;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SEMVERPackage.SIMPLE_VERSION__HAS_CARET, oldHasCaret, hasCaret));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SEMVERPackage.SIMPLE_VERSION__NUMBER:
				return basicSetNumber(null, msgs);
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
			case SEMVERPackage.SIMPLE_VERSION__NUMBER:
				return getNumber();
			case SEMVERPackage.SIMPLE_VERSION__COMPARATOR:
				return getComparator();
			case SEMVERPackage.SIMPLE_VERSION__HAS_TILDE:
				return isHasTilde();
			case SEMVERPackage.SIMPLE_VERSION__HAS_CARET:
				return isHasCaret();
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
			case SEMVERPackage.SIMPLE_VERSION__NUMBER:
				setNumber((VersionNumber)newValue);
				return;
			case SEMVERPackage.SIMPLE_VERSION__COMPARATOR:
				setComparator((VersionComparator)newValue);
				return;
			case SEMVERPackage.SIMPLE_VERSION__HAS_TILDE:
				setHasTilde((Boolean)newValue);
				return;
			case SEMVERPackage.SIMPLE_VERSION__HAS_CARET:
				setHasCaret((Boolean)newValue);
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
			case SEMVERPackage.SIMPLE_VERSION__NUMBER:
				setNumber((VersionNumber)null);
				return;
			case SEMVERPackage.SIMPLE_VERSION__COMPARATOR:
				setComparator(COMPARATOR_EDEFAULT);
				return;
			case SEMVERPackage.SIMPLE_VERSION__HAS_TILDE:
				setHasTilde(HAS_TILDE_EDEFAULT);
				return;
			case SEMVERPackage.SIMPLE_VERSION__HAS_CARET:
				setHasCaret(HAS_CARET_EDEFAULT);
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
			case SEMVERPackage.SIMPLE_VERSION__NUMBER:
				return number != null;
			case SEMVERPackage.SIMPLE_VERSION__COMPARATOR:
				return comparator != COMPARATOR_EDEFAULT;
			case SEMVERPackage.SIMPLE_VERSION__HAS_TILDE:
				return hasTilde != HAS_TILDE_EDEFAULT;
			case SEMVERPackage.SIMPLE_VERSION__HAS_CARET:
				return hasCaret != HAS_CARET_EDEFAULT;
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
		result.append(" (comparator: ");
		result.append(comparator);
		result.append(", hasTilde: ");
		result.append(hasTilde);
		result.append(", hasCaret: ");
		result.append(hasCaret);
		result.append(')');
		return result.toString();
	}

} //SimpleVersionImpl
