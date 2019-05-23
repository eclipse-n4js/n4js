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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.n4js.n4JS.DoubleLiteral;
import org.eclipse.n4js.n4JS.Literal;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NumericLiteral;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Double Literal</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class DoubleLiteralImpl extends NumericLiteralImpl implements DoubleLiteral {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DoubleLiteralImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.DOUBLE_LITERAL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double toDouble() {
		return this.getValue().doubleValue();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getValueAsString() {
		BigDecimal _value = this.getValue();
		boolean _tripleEquals = (_value == null);
		if (_tripleEquals) {
			return null;
		}
		return this.getValue().toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == Literal.class) {
			switch (baseOperationID) {
				case N4JSPackage.LITERAL___GET_VALUE_AS_STRING: return N4JSPackage.DOUBLE_LITERAL___GET_VALUE_AS_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == NumericLiteral.class) {
			switch (baseOperationID) {
				case N4JSPackage.NUMERIC_LITERAL___GET_VALUE_AS_STRING: return N4JSPackage.DOUBLE_LITERAL___GET_VALUE_AS_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
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
			case N4JSPackage.DOUBLE_LITERAL___TO_DOUBLE:
				return toDouble();
			case N4JSPackage.DOUBLE_LITERAL___GET_VALUE_AS_STRING:
				return getValueAsString();
		}
		return super.eInvoke(operationID, arguments);
	}

} //DoubleLiteralImpl
