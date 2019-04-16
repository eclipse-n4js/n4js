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
package org.eclipse.n4js.ts.typeRefs.impl;

import java.lang.reflect.InvocationTargetException;

import java.math.BigDecimal;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.VersionedReference;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Versioned Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.VersionedReferenceImpl#getRequestedVersion <em>Requested Version</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class VersionedReferenceImpl extends ProxyResolvingEObjectImpl implements VersionedReference {
	/**
	 * The default value of the '{@link #getRequestedVersion() <em>Requested Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequestedVersion()
	 * @generated
	 * @ordered
	 */
	protected static final BigDecimal REQUESTED_VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRequestedVersion() <em>Requested Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequestedVersion()
	 * @generated
	 * @ordered
	 */
	protected BigDecimal requestedVersion = REQUESTED_VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VersionedReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.VERSIONED_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BigDecimal getRequestedVersion() {
		return requestedVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRequestedVersion(BigDecimal newRequestedVersion) {
		BigDecimal oldRequestedVersion = requestedVersion;
		requestedVersion = newRequestedVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.VERSIONED_REFERENCE__REQUESTED_VERSION, oldRequestedVersion, requestedVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean hasRequestedVersion() {
		BigDecimal _requestedVersion = this.getRequestedVersion();
		return (_requestedVersion != null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getRequestedVersionOrZero() {
		int _xifexpression = (int) 0;
		boolean _hasRequestedVersion = this.hasRequestedVersion();
		if (_hasRequestedVersion) {
			_xifexpression = this.getRequestedVersion().intValue();
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
			case TypeRefsPackage.VERSIONED_REFERENCE__REQUESTED_VERSION:
				return getRequestedVersion();
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
			case TypeRefsPackage.VERSIONED_REFERENCE__REQUESTED_VERSION:
				setRequestedVersion((BigDecimal)newValue);
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
			case TypeRefsPackage.VERSIONED_REFERENCE__REQUESTED_VERSION:
				setRequestedVersion(REQUESTED_VERSION_EDEFAULT);
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
			case TypeRefsPackage.VERSIONED_REFERENCE__REQUESTED_VERSION:
				return REQUESTED_VERSION_EDEFAULT == null ? requestedVersion != null : !REQUESTED_VERSION_EDEFAULT.equals(requestedVersion);
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
			case TypeRefsPackage.VERSIONED_REFERENCE___HAS_REQUESTED_VERSION:
				return hasRequestedVersion();
			case TypeRefsPackage.VERSIONED_REFERENCE___GET_REQUESTED_VERSION_OR_ZERO:
				return getRequestedVersionOrZero();
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
		result.append(" (requestedVersion: ");
		result.append(requestedVersion);
		result.append(')');
		return result.toString();
	}

} //VersionedReferenceImpl
