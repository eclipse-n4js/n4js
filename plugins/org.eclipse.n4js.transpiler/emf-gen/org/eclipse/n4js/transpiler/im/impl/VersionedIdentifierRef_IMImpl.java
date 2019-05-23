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
package org.eclipse.n4js.transpiler.im.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.VersionedIdentifierRef;

import org.eclipse.n4js.transpiler.im.ImPackage;
import org.eclipse.n4js.transpiler.im.VersionedIdentifierRef_IM;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.Versionable;
import org.eclipse.n4js.ts.typeRefs.VersionedReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Versioned Identifier Ref IM</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.VersionedIdentifierRef_IMImpl#getRequestedVersion <em>Requested Version</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VersionedIdentifierRef_IMImpl extends IdentifierRef_IMImpl implements VersionedIdentifierRef_IM {
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
	protected VersionedIdentifierRef_IMImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImPackage.Literals.VERSIONED_IDENTIFIER_REF_IM;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.VERSIONED_IDENTIFIER_REF_IM__REQUESTED_VERSION, oldRequestedVersion, requestedVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getVersion() {
		return this.getRequestedVersionOrZero();
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
			case ImPackage.VERSIONED_IDENTIFIER_REF_IM__REQUESTED_VERSION:
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
			case ImPackage.VERSIONED_IDENTIFIER_REF_IM__REQUESTED_VERSION:
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
			case ImPackage.VERSIONED_IDENTIFIER_REF_IM__REQUESTED_VERSION:
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
			case ImPackage.VERSIONED_IDENTIFIER_REF_IM__REQUESTED_VERSION:
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
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == VersionedReference.class) {
			switch (derivedFeatureID) {
				case ImPackage.VERSIONED_IDENTIFIER_REF_IM__REQUESTED_VERSION: return TypeRefsPackage.VERSIONED_REFERENCE__REQUESTED_VERSION;
				default: return -1;
			}
		}
		if (baseClass == VersionedIdentifierRef.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == VersionedReference.class) {
			switch (baseFeatureID) {
				case TypeRefsPackage.VERSIONED_REFERENCE__REQUESTED_VERSION: return ImPackage.VERSIONED_IDENTIFIER_REF_IM__REQUESTED_VERSION;
				default: return -1;
			}
		}
		if (baseClass == VersionedIdentifierRef.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == Versionable.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.VERSIONABLE___GET_VERSION: return ImPackage.VERSIONED_IDENTIFIER_REF_IM___GET_VERSION;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == IdentifierRef.class) {
			switch (baseOperationID) {
				case N4JSPackage.IDENTIFIER_REF___GET_VERSION: return ImPackage.VERSIONED_IDENTIFIER_REF_IM___GET_VERSION;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == VersionedReference.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.VERSIONED_REFERENCE___HAS_REQUESTED_VERSION: return ImPackage.VERSIONED_IDENTIFIER_REF_IM___HAS_REQUESTED_VERSION;
				case TypeRefsPackage.VERSIONED_REFERENCE___GET_REQUESTED_VERSION_OR_ZERO: return ImPackage.VERSIONED_IDENTIFIER_REF_IM___GET_REQUESTED_VERSION_OR_ZERO;
				default: return -1;
			}
		}
		if (baseClass == VersionedIdentifierRef.class) {
			switch (baseOperationID) {
				case N4JSPackage.VERSIONED_IDENTIFIER_REF___GET_VERSION: return ImPackage.VERSIONED_IDENTIFIER_REF_IM___GET_VERSION;
				default: return -1;
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case ImPackage.VERSIONED_IDENTIFIER_REF_IM___GET_VERSION:
				return getVersion();
			case ImPackage.VERSIONED_IDENTIFIER_REF_IM___HAS_REQUESTED_VERSION:
				return hasRequestedVersion();
			case ImPackage.VERSIONED_IDENTIFIER_REF_IM___GET_REQUESTED_VERSION_OR_ZERO:
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

} //VersionedIdentifierRef_IMImpl
