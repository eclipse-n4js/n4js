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

import java.lang.reflect.InvocationTargetException;

import java.math.BigDecimal;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.xtext.EcoreUtil2;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TEnum Literal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TEnumLiteralImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TEnumLiteralImpl#getValueString <em>Value String</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TEnumLiteralImpl#getValueNumber <em>Value Number</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TEnumLiteralImpl extends SyntaxRelatedTElementImpl implements TEnumLiteral {
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
	 * The default value of the '{@link #getValueString() <em>Value String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueString()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_STRING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValueString() <em>Value String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueString()
	 * @generated
	 * @ordered
	 */
	protected String valueString = VALUE_STRING_EDEFAULT;

	/**
	 * The default value of the '{@link #getValueNumber() <em>Value Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueNumber()
	 * @generated
	 * @ordered
	 */
	protected static final BigDecimal VALUE_NUMBER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValueNumber() <em>Value Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueNumber()
	 * @generated
	 * @ordered
	 */
	protected BigDecimal valueNumber = VALUE_NUMBER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TEnumLiteralImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TENUM_LITERAL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TENUM_LITERAL__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getValueString() {
		return valueString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValueString(String newValueString) {
		String oldValueString = valueString;
		valueString = newValueString;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TENUM_LITERAL__VALUE_STRING, oldValueString, valueString));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BigDecimal getValueNumber() {
		return valueNumber;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValueNumber(BigDecimal newValueNumber) {
		BigDecimal oldValueNumber = valueNumber;
		valueNumber = newValueNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TENUM_LITERAL__VALUE_NUMBER, oldValueNumber, valueNumber));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TModule getContainingModule() {
		return EcoreUtil2.<TModule>getContainerOfType(this, TModule.class);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.TENUM_LITERAL__NAME:
				return getName();
			case TypesPackage.TENUM_LITERAL__VALUE_STRING:
				return getValueString();
			case TypesPackage.TENUM_LITERAL__VALUE_NUMBER:
				return getValueNumber();
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
			case TypesPackage.TENUM_LITERAL__NAME:
				setName((String)newValue);
				return;
			case TypesPackage.TENUM_LITERAL__VALUE_STRING:
				setValueString((String)newValue);
				return;
			case TypesPackage.TENUM_LITERAL__VALUE_NUMBER:
				setValueNumber((BigDecimal)newValue);
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
			case TypesPackage.TENUM_LITERAL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TypesPackage.TENUM_LITERAL__VALUE_STRING:
				setValueString(VALUE_STRING_EDEFAULT);
				return;
			case TypesPackage.TENUM_LITERAL__VALUE_NUMBER:
				setValueNumber(VALUE_NUMBER_EDEFAULT);
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
			case TypesPackage.TENUM_LITERAL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TypesPackage.TENUM_LITERAL__VALUE_STRING:
				return VALUE_STRING_EDEFAULT == null ? valueString != null : !VALUE_STRING_EDEFAULT.equals(valueString);
			case TypesPackage.TENUM_LITERAL__VALUE_NUMBER:
				return VALUE_NUMBER_EDEFAULT == null ? valueNumber != null : !VALUE_NUMBER_EDEFAULT.equals(valueNumber);
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
		if (baseClass == TypableElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == IdentifiableElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TENUM_LITERAL__NAME: return TypesPackage.IDENTIFIABLE_ELEMENT__NAME;
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
		if (baseClass == TypableElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == IdentifiableElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.IDENTIFIABLE_ELEMENT__NAME: return TypesPackage.TENUM_LITERAL__NAME;
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
		if (baseClass == TypableElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == IdentifiableElement.class) {
			switch (baseOperationID) {
				case TypesPackage.IDENTIFIABLE_ELEMENT___GET_CONTAINING_MODULE: return TypesPackage.TENUM_LITERAL___GET_CONTAINING_MODULE;
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
			case TypesPackage.TENUM_LITERAL___GET_CONTAINING_MODULE:
				return getContainingModule();
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
		result.append(" (name: ");
		result.append(name);
		result.append(", valueString: ");
		result.append(valueString);
		result.append(", valueNumber: ");
		result.append(valueNumber);
		result.append(')');
		return result.toString();
	}

} //TEnumLiteralImpl
