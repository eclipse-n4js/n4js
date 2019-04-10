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
package org.eclipse.n4js.n4JS.impl;

import java.lang.reflect.InvocationTargetException;

import java.math.BigDecimal;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.VersionedElement;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Versioned Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.VersionedElementImpl#getDeclaredVersion <em>Declared Version</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class VersionedElementImpl extends ProxyResolvingEObjectImpl implements VersionedElement {
	/**
	 * The default value of the '{@link #getDeclaredVersion() <em>Declared Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredVersion()
	 * @generated
	 * @ordered
	 */
	protected static final BigDecimal DECLARED_VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDeclaredVersion() <em>Declared Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredVersion()
	 * @generated
	 * @ordered
	 */
	protected BigDecimal declaredVersion = DECLARED_VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VersionedElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.VERSIONED_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BigDecimal getDeclaredVersion() {
		return declaredVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredVersion(BigDecimal newDeclaredVersion) {
		BigDecimal oldDeclaredVersion = declaredVersion;
		declaredVersion = newDeclaredVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.VERSIONED_ELEMENT__DECLARED_VERSION, oldDeclaredVersion, declaredVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean hasDeclaredVersion() {
		BigDecimal _declaredVersion = this.getDeclaredVersion();
		return (_declaredVersion != null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getDeclaredVersionOrZero() {
		int _xifexpression = (int) 0;
		boolean _hasDeclaredVersion = this.hasDeclaredVersion();
		if (_hasDeclaredVersion) {
			_xifexpression = this.getDeclaredVersion().intValue();
		}
		else {
			_xifexpression = 0;
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case N4JSPackage.VERSIONED_ELEMENT__DECLARED_VERSION:
				return getDeclaredVersion();
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
			case N4JSPackage.VERSIONED_ELEMENT__DECLARED_VERSION:
				setDeclaredVersion((BigDecimal)newValue);
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
			case N4JSPackage.VERSIONED_ELEMENT__DECLARED_VERSION:
				setDeclaredVersion(DECLARED_VERSION_EDEFAULT);
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
			case N4JSPackage.VERSIONED_ELEMENT__DECLARED_VERSION:
				return DECLARED_VERSION_EDEFAULT == null ? declaredVersion != null : !DECLARED_VERSION_EDEFAULT.equals(declaredVersion);
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
			case N4JSPackage.VERSIONED_ELEMENT___HAS_DECLARED_VERSION:
				return hasDeclaredVersion();
			case N4JSPackage.VERSIONED_ELEMENT___GET_DECLARED_VERSION_OR_ZERO:
				return getDeclaredVersionOrZero();
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
		result.append(" (declaredVersion: ");
		result.append(declaredVersion);
		result.append(')');
		return result.toString();
	}

} //VersionedElementImpl
