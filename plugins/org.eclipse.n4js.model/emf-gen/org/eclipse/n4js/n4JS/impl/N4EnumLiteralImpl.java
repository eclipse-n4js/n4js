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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.N4EnumLiteral;
import org.eclipse.n4js.n4JS.N4JSPackage;

import org.eclipse.n4js.ts.types.TEnumLiteral;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>N4 Enum Literal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4EnumLiteralImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4EnumLiteralImpl#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4EnumLiteralImpl#getDefinedLiteral <em>Defined Literal</em>}</li>
 * </ul>
 *
 * @generated
 */
public class N4EnumLiteralImpl extends ProxyResolvingEObjectImpl implements N4EnumLiteral {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDefinedLiteral() <em>Defined Literal</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinedLiteral()
	 * @generated
	 * @ordered
	 */
	protected TEnumLiteral definedLiteral;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected N4EnumLiteralImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.N4_ENUM_LITERAL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_ENUM_LITERAL__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_ENUM_LITERAL__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TEnumLiteral getDefinedLiteral() {
		if (definedLiteral != null && definedLiteral.eIsProxy()) {
			InternalEObject oldDefinedLiteral = (InternalEObject)definedLiteral;
			definedLiteral = (TEnumLiteral)eResolveProxy(oldDefinedLiteral);
			if (definedLiteral != oldDefinedLiteral) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.N4_ENUM_LITERAL__DEFINED_LITERAL, oldDefinedLiteral, definedLiteral));
			}
		}
		return definedLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TEnumLiteral basicGetDefinedLiteral() {
		return definedLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefinedLiteral(TEnumLiteral newDefinedLiteral) {
		TEnumLiteral oldDefinedLiteral = definedLiteral;
		definedLiteral = newDefinedLiteral;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_ENUM_LITERAL__DEFINED_LITERAL, oldDefinedLiteral, definedLiteral));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case N4JSPackage.N4_ENUM_LITERAL__NAME:
				return getName();
			case N4JSPackage.N4_ENUM_LITERAL__VALUE:
				return getValue();
			case N4JSPackage.N4_ENUM_LITERAL__DEFINED_LITERAL:
				if (resolve) return getDefinedLiteral();
				return basicGetDefinedLiteral();
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
			case N4JSPackage.N4_ENUM_LITERAL__NAME:
				setName((String)newValue);
				return;
			case N4JSPackage.N4_ENUM_LITERAL__VALUE:
				setValue((String)newValue);
				return;
			case N4JSPackage.N4_ENUM_LITERAL__DEFINED_LITERAL:
				setDefinedLiteral((TEnumLiteral)newValue);
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
			case N4JSPackage.N4_ENUM_LITERAL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case N4JSPackage.N4_ENUM_LITERAL__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case N4JSPackage.N4_ENUM_LITERAL__DEFINED_LITERAL:
				setDefinedLiteral((TEnumLiteral)null);
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
			case N4JSPackage.N4_ENUM_LITERAL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case N4JSPackage.N4_ENUM_LITERAL__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
			case N4JSPackage.N4_ENUM_LITERAL__DEFINED_LITERAL:
				return definedLiteral != null;
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

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", value: ");
		result.append(value);
		result.append(')');
		return result.toString();
	}

} //N4EnumLiteralImpl
