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

import com.google.common.base.Objects;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.semver.Semver.SemverPackage;
import org.eclipse.n4js.semver.Semver.VersionPart;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Version Part</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.VersionPartImpl#isWildcard <em>Wildcard</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.VersionPartImpl#getNumberRaw <em>Number Raw</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VersionPartImpl extends SemverToStringableImpl implements VersionPart {
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
	 * The default value of the '{@link #getNumberRaw() <em>Number Raw</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberRaw()
	 * @generated
	 * @ordered
	 */
	protected static final Integer NUMBER_RAW_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNumberRaw() <em>Number Raw</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberRaw()
	 * @generated
	 * @ordered
	 */
	protected Integer numberRaw = NUMBER_RAW_EDEFAULT;

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
		return SemverPackage.Literals.VERSION_PART;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isWildcard() {
		return wildcard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWildcard(boolean newWildcard) {
		boolean oldWildcard = wildcard;
		wildcard = newWildcard;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SemverPackage.VERSION_PART__WILDCARD, oldWildcard, wildcard));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Integer getNumberRaw() {
		return numberRaw;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNumberRaw(Integer newNumberRaw) {
		Integer oldNumberRaw = numberRaw;
		numberRaw = newNumberRaw;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SemverPackage.VERSION_PART__NUMBER_RAW, oldNumberRaw, numberRaw));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Integer getNumber() {
		boolean _isWildcard = this.isWildcard();
		if (_isWildcard) {
			return null;
		}
		return this.getNumberRaw();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean equals(final Object obj) {
		if ((!(obj instanceof VersionPart))) {
			return false;
		}
		final VersionPart vp = ((VersionPart) obj);
		boolean equals = ((this.isWildcard() == vp.isWildcard()) && Objects.equal(this.getNumberRaw(), vp.getNumberRaw()));
		return equals;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SemverPackage.VERSION_PART__WILDCARD:
				return isWildcard();
			case SemverPackage.VERSION_PART__NUMBER_RAW:
				return getNumberRaw();
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
			case SemverPackage.VERSION_PART__WILDCARD:
				setWildcard((Boolean)newValue);
				return;
			case SemverPackage.VERSION_PART__NUMBER_RAW:
				setNumberRaw((Integer)newValue);
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
			case SemverPackage.VERSION_PART__WILDCARD:
				setWildcard(WILDCARD_EDEFAULT);
				return;
			case SemverPackage.VERSION_PART__NUMBER_RAW:
				setNumberRaw(NUMBER_RAW_EDEFAULT);
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
			case SemverPackage.VERSION_PART__WILDCARD:
				return wildcard != WILDCARD_EDEFAULT;
			case SemverPackage.VERSION_PART__NUMBER_RAW:
				return NUMBER_RAW_EDEFAULT == null ? numberRaw != null : !NUMBER_RAW_EDEFAULT.equals(numberRaw);
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
			case SemverPackage.VERSION_PART___GET_NUMBER:
				return getNumber();
			case SemverPackage.VERSION_PART___EQUALS__OBJECT:
				return equals(arguments.get(0));
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

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (wildcard: ");
		result.append(wildcard);
		result.append(", numberRaw: ");
		result.append(numberRaw);
		result.append(')');
		return result.toString();
	}

} //VersionPartImpl
