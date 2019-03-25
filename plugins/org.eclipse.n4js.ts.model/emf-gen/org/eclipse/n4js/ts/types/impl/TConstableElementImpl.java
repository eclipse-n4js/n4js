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
package org.eclipse.n4js.ts.types.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.types.TConstableElement;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TConstable Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TConstableElementImpl#isConst <em>Const</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TConstableElementImpl#getCompileTimeValue <em>Compile Time Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class TConstableElementImpl extends ProxyResolvingEObjectImpl implements TConstableElement {
	/**
	 * The default value of the '{@link #isConst() <em>Const</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConst()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONST_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isConst() <em>Const</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConst()
	 * @generated
	 * @ordered
	 */
	protected boolean const_ = CONST_EDEFAULT;

	/**
	 * The default value of the '{@link #getCompileTimeValue() <em>Compile Time Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompileTimeValue()
	 * @generated
	 * @ordered
	 */
	protected static final String COMPILE_TIME_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCompileTimeValue() <em>Compile Time Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompileTimeValue()
	 * @generated
	 * @ordered
	 */
	protected String compileTimeValue = COMPILE_TIME_VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TConstableElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TCONSTABLE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isConst() {
		return const_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConst(boolean newConst) {
		boolean oldConst = const_;
		const_ = newConst;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TCONSTABLE_ELEMENT__CONST, oldConst, const_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCompileTimeValue() {
		return compileTimeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCompileTimeValue(String newCompileTimeValue) {
		String oldCompileTimeValue = compileTimeValue;
		compileTimeValue = newCompileTimeValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TCONSTABLE_ELEMENT__COMPILE_TIME_VALUE, oldCompileTimeValue, compileTimeValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.TCONSTABLE_ELEMENT__CONST:
				return isConst();
			case TypesPackage.TCONSTABLE_ELEMENT__COMPILE_TIME_VALUE:
				return getCompileTimeValue();
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
			case TypesPackage.TCONSTABLE_ELEMENT__CONST:
				setConst((Boolean)newValue);
				return;
			case TypesPackage.TCONSTABLE_ELEMENT__COMPILE_TIME_VALUE:
				setCompileTimeValue((String)newValue);
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
			case TypesPackage.TCONSTABLE_ELEMENT__CONST:
				setConst(CONST_EDEFAULT);
				return;
			case TypesPackage.TCONSTABLE_ELEMENT__COMPILE_TIME_VALUE:
				setCompileTimeValue(COMPILE_TIME_VALUE_EDEFAULT);
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
			case TypesPackage.TCONSTABLE_ELEMENT__CONST:
				return const_ != CONST_EDEFAULT;
			case TypesPackage.TCONSTABLE_ELEMENT__COMPILE_TIME_VALUE:
				return COMPILE_TIME_VALUE_EDEFAULT == null ? compileTimeValue != null : !COMPILE_TIME_VALUE_EDEFAULT.equals(compileTimeValue);
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
		result.append(" (const: ");
		result.append(const_);
		result.append(", compileTimeValue: ");
		result.append(compileTimeValue);
		result.append(')');
		return result.toString();
	}

} //TConstableElementImpl
