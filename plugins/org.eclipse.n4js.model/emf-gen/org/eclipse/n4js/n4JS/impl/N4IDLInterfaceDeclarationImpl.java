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

import org.eclipse.n4js.n4JS.N4IDLInterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.VersionedElement;

import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.Versionable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>N4IDL Interface Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4IDLInterfaceDeclarationImpl#getDeclaredVersion <em>Declared Version</em>}</li>
 * </ul>
 *
 * @generated
 */
public class N4IDLInterfaceDeclarationImpl extends N4InterfaceDeclarationImpl implements N4IDLInterfaceDeclaration {
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
	protected N4IDLInterfaceDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.N4IDL_INTERFACE_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigDecimal getDeclaredVersion() {
		return declaredVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeclaredVersion(BigDecimal newDeclaredVersion) {
		BigDecimal oldDeclaredVersion = declaredVersion;
		declaredVersion = newDeclaredVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4IDL_INTERFACE_DECLARATION__DECLARED_VERSION, oldDeclaredVersion, declaredVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getVersion() {
		return this.getDeclaredVersionOrZero();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean hasDeclaredVersion() {
		BigDecimal _declaredVersion = this.getDeclaredVersion();
		return (_declaredVersion != null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
			case N4JSPackage.N4IDL_INTERFACE_DECLARATION__DECLARED_VERSION:
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
			case N4JSPackage.N4IDL_INTERFACE_DECLARATION__DECLARED_VERSION:
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
			case N4JSPackage.N4IDL_INTERFACE_DECLARATION__DECLARED_VERSION:
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
			case N4JSPackage.N4IDL_INTERFACE_DECLARATION__DECLARED_VERSION:
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
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == Versionable.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == VersionedElement.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.N4IDL_INTERFACE_DECLARATION__DECLARED_VERSION: return N4JSPackage.VERSIONED_ELEMENT__DECLARED_VERSION;
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
		if (baseClass == Versionable.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == VersionedElement.class) {
			switch (baseFeatureID) {
				case N4JSPackage.VERSIONED_ELEMENT__DECLARED_VERSION: return N4JSPackage.N4IDL_INTERFACE_DECLARATION__DECLARED_VERSION;
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
				case TypeRefsPackage.VERSIONABLE___GET_VERSION: return N4JSPackage.N4IDL_INTERFACE_DECLARATION___GET_VERSION;
				default: return -1;
			}
		}
		if (baseClass == VersionedElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.VERSIONED_ELEMENT___HAS_DECLARED_VERSION: return N4JSPackage.N4IDL_INTERFACE_DECLARATION___HAS_DECLARED_VERSION;
				case N4JSPackage.VERSIONED_ELEMENT___GET_DECLARED_VERSION_OR_ZERO: return N4JSPackage.N4IDL_INTERFACE_DECLARATION___GET_DECLARED_VERSION_OR_ZERO;
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
			case N4JSPackage.N4IDL_INTERFACE_DECLARATION___GET_VERSION:
				return getVersion();
			case N4JSPackage.N4IDL_INTERFACE_DECLARATION___HAS_DECLARED_VERSION:
				return hasDeclaredVersion();
			case N4JSPackage.N4IDL_INTERFACE_DECLARATION___GET_DECLARED_VERSION_OR_ZERO:
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (declaredVersion: ");
		result.append(declaredVersion);
		result.append(')');
		return result.toString();
	}

} //N4IDLInterfaceDeclarationImpl
