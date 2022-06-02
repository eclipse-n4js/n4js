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

import org.eclipse.n4js.ts.typeRefs.NumericLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Numeric Literal Type Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.NumericLiteralTypeRefImpl#isAstNegated <em>Ast Negated</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.NumericLiteralTypeRefImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NumericLiteralTypeRefImpl extends LiteralTypeRefImpl implements NumericLiteralTypeRef {
	/**
	 * The default value of the '{@link #isAstNegated() <em>Ast Negated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAstNegated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean AST_NEGATED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAstNegated() <em>Ast Negated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAstNegated()
	 * @generated
	 * @ordered
	 */
	protected boolean astNegated = AST_NEGATED_EDEFAULT;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final BigDecimal VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected BigDecimal value = VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NumericLiteralTypeRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.NUMERIC_LITERAL_TYPE_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAstNegated() {
		return astNegated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAstNegated(boolean newAstNegated) {
		boolean oldAstNegated = astNegated;
		astNegated = newAstNegated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.NUMERIC_LITERAL_TYPE_REF__AST_NEGATED, oldAstNegated, astNegated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValue(BigDecimal newValue) {
		BigDecimal oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.NUMERIC_LITERAL_TYPE_REF__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String internalGetTypeRefAsString(final boolean resolveProxies) {
		String _elvis = null;
		String _elvis_1 = null;
		BigDecimal _value = this.getValue();
		BigDecimal _stripTrailingZeros = null;
		if (_value!=null) {
			_stripTrailingZeros=_value.stripTrailingZeros();
		}
		String _plainString = null;
		if (_stripTrailingZeros!=null) {
			_plainString=_stripTrailingZeros.toPlainString();
		}
		if (_plainString != null) {
			_elvis_1 = _plainString;
		} else {
			Object _astValue = this.getAstValue();
			String _string = null;
			if (_astValue!=null) {
				_string=_astValue.toString();
			}
			_elvis_1 = _string;
		}
		if (_elvis_1 != null) {
			_elvis = _elvis_1;
		} else {
			_elvis = "\u00ABnull\u00BB";
		}
		return _elvis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypeRefsPackage.NUMERIC_LITERAL_TYPE_REF__AST_NEGATED:
				return isAstNegated();
			case TypeRefsPackage.NUMERIC_LITERAL_TYPE_REF__VALUE:
				return getValue();
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
			case TypeRefsPackage.NUMERIC_LITERAL_TYPE_REF__AST_NEGATED:
				setAstNegated((Boolean)newValue);
				return;
			case TypeRefsPackage.NUMERIC_LITERAL_TYPE_REF__VALUE:
				setValue((BigDecimal)newValue);
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
			case TypeRefsPackage.NUMERIC_LITERAL_TYPE_REF__AST_NEGATED:
				setAstNegated(AST_NEGATED_EDEFAULT);
				return;
			case TypeRefsPackage.NUMERIC_LITERAL_TYPE_REF__VALUE:
				setValue(VALUE_EDEFAULT);
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
			case TypeRefsPackage.NUMERIC_LITERAL_TYPE_REF__AST_NEGATED:
				return astNegated != AST_NEGATED_EDEFAULT;
			case TypeRefsPackage.NUMERIC_LITERAL_TYPE_REF__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
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
			case TypeRefsPackage.NUMERIC_LITERAL_TYPE_REF___INTERNAL_GET_TYPE_REF_AS_STRING__BOOLEAN:
				return internalGetTypeRefAsString((Boolean)arguments.get(0));
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
		result.append(" (astNegated: ");
		result.append(astNegated);
		result.append(", value: ");
		result.append(value);
		result.append(')');
		return result.toString();
	}

} //NumericLiteralTypeRefImpl
