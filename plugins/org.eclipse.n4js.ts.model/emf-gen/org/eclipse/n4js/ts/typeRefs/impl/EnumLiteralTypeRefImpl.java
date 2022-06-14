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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.typeRefs.EnumLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;

import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TEnumLiteral;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enum Literal Type Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.EnumLiteralTypeRefImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EnumLiteralTypeRefImpl extends LiteralTypeRefImpl implements EnumLiteralTypeRef {
	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected TEnumLiteral value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnumLiteralTypeRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.ENUM_LITERAL_TYPE_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TEnumLiteral getValue() {
		if (value != null && value.eIsProxy()) {
			InternalEObject oldValue = (InternalEObject)value;
			value = (TEnumLiteral)eResolveProxy(oldValue);
			if (value != oldValue) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypeRefsPackage.ENUM_LITERAL_TYPE_REF__VALUE, oldValue, value));
			}
		}
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TEnumLiteral basicGetValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValue(TEnumLiteral newValue) {
		TEnumLiteral oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.ENUM_LITERAL_TYPE_REF__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TEnum getEnumType() {
		TEnumLiteral _value = this.getValue();
		EObject _eContainer = null;
		if (_value!=null) {
			_eContainer=_value.eContainer();
		}
		return ((TEnum) _eContainer);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String internalGetTypeRefAsString(final boolean resolveProxies) {
		if (((this.getValue() == null) && (this.getAstValue() != null))) {
			return this.getAstValue().toString();
		}
		String _elvis = null;
		TEnum _enumType = this.getEnumType();
		String _name = null;
		if (_enumType!=null) {
			_name=_enumType.getName();
		}
		if (_name != null) {
			_elvis = _name;
		} else {
			_elvis = "\u00ABnull\u00BB";
		}
		String _plus = (_elvis + ".");
		String _elvis_1 = null;
		TEnumLiteral _value = this.getValue();
		String _name_1 = null;
		if (_value!=null) {
			_name_1=_value.getName();
		}
		if (_name_1 != null) {
			_elvis_1 = _name_1;
		} else {
			_elvis_1 = "\u00ABnull\u00BB";
		}
		return (_plus + _elvis_1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypeRefsPackage.ENUM_LITERAL_TYPE_REF__VALUE:
				if (resolve) return getValue();
				return basicGetValue();
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
			case TypeRefsPackage.ENUM_LITERAL_TYPE_REF__VALUE:
				setValue((TEnumLiteral)newValue);
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
			case TypeRefsPackage.ENUM_LITERAL_TYPE_REF__VALUE:
				setValue((TEnumLiteral)null);
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
			case TypeRefsPackage.ENUM_LITERAL_TYPE_REF__VALUE:
				return value != null;
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
			case TypeRefsPackage.ENUM_LITERAL_TYPE_REF___GET_ENUM_TYPE:
				return getEnumType();
			case TypeRefsPackage.ENUM_LITERAL_TYPE_REF___INTERNAL_GET_TYPE_REF_AS_STRING__BOOLEAN:
				return internalGetTypeRefAsString((Boolean)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //EnumLiteralTypeRefImpl
