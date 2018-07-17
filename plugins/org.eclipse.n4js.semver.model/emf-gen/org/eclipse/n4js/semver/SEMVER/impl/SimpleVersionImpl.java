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

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

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
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.impl.SimpleVersionImpl#getComparators <em>Comparators</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SimpleVersionImpl extends AbstractSEMVERSerializerImpl implements SimpleVersion {
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
	 * The cached value of the '{@link #getComparators() <em>Comparators</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComparators()
	 * @generated
	 * @ordered
	 */
	protected EList<VersionComparator> comparators;

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
	public EList<VersionComparator> getComparators() {
		if (comparators == null) {
			comparators = new EDataTypeEList<VersionComparator>(VersionComparator.class, this, SEMVERPackage.SIMPLE_VERSION__COMPARATORS);
		}
		return comparators;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSpecific() {
		return ((this.getComparators().isEmpty() || this.getComparators().contains(VersionComparator.VERSION)) || this.getComparators().contains(VersionComparator.EQUALS));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCaret() {
		return this.getComparators().contains(VersionComparator.CARET);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTilde() {
		return this.getComparators().contains(VersionComparator.TILDE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGreater() {
		return this.getComparators().contains(VersionComparator.GREATER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGreaterEquals() {
		return this.getComparators().contains(VersionComparator.GREATER_EQUALS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSmaller() {
		return this.getComparators().contains(VersionComparator.SMALLER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSmallerEquals() {
		return this.getComparators().contains(VersionComparator.SMALLER_EQUALS);
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
			case SEMVERPackage.SIMPLE_VERSION__COMPARATORS:
				return getComparators();
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
			case SEMVERPackage.SIMPLE_VERSION__NUMBER:
				setNumber((VersionNumber)newValue);
				return;
			case SEMVERPackage.SIMPLE_VERSION__COMPARATORS:
				getComparators().clear();
				getComparators().addAll((Collection<? extends VersionComparator>)newValue);
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
			case SEMVERPackage.SIMPLE_VERSION__COMPARATORS:
				getComparators().clear();
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
			case SEMVERPackage.SIMPLE_VERSION__COMPARATORS:
				return comparators != null && !comparators.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case SEMVERPackage.SIMPLE_VERSION___IS_SPECIFIC:
				return isSpecific();
			case SEMVERPackage.SIMPLE_VERSION___IS_CARET:
				return isCaret();
			case SEMVERPackage.SIMPLE_VERSION___IS_TILDE:
				return isTilde();
			case SEMVERPackage.SIMPLE_VERSION___IS_GREATER:
				return isGreater();
			case SEMVERPackage.SIMPLE_VERSION___IS_GREATER_EQUALS:
				return isGreaterEquals();
			case SEMVERPackage.SIMPLE_VERSION___IS_SMALLER:
				return isSmaller();
			case SEMVERPackage.SIMPLE_VERSION___IS_SMALLER_EQUALS:
				return isSmallerEquals();
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(" (comparators: ");
		result.append(comparators);
		result.append(')');
		return result.toString();
	}

} //SimpleVersionImpl
