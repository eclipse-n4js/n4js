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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.Statement;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Arrow Function</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ArrowFunctionImpl#isHasBracesAroundBody <em>Has Braces Around Body</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ArrowFunctionImpl extends FunctionExpressionImpl implements ArrowFunction {
	/**
	 * The default value of the '{@link #isHasBracesAroundBody() <em>Has Braces Around Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasBracesAroundBody()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HAS_BRACES_AROUND_BODY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHasBracesAroundBody() <em>Has Braces Around Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasBracesAroundBody()
	 * @generated
	 * @ordered
	 */
	protected boolean hasBracesAroundBody = HAS_BRACES_AROUND_BODY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArrowFunctionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.ARROW_FUNCTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHasBracesAroundBody() {
		return hasBracesAroundBody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasBracesAroundBody(boolean newHasBracesAroundBody) {
		boolean oldHasBracesAroundBody = hasBracesAroundBody;
		hasBracesAroundBody = newHasBracesAroundBody;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.ARROW_FUNCTION__HAS_BRACES_AROUND_BODY, oldHasBracesAroundBody, hasBracesAroundBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isArrowFunction() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSingleExprImplicitReturn() {
		return ((((this.isArrowFunction() && 
			(!this.isHasBracesAroundBody())) && (this.getBody() != null)) && 
			(!this.getBody().getStatements().isEmpty())) && (IterableExtensions.<Statement>head(this.getBody().getStatements()) instanceof ExpressionStatement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getSingleExpression() {
		Statement _head = IterableExtensions.<Statement>head(this.getBody().getStatements());
		return ((ExpressionStatement) _head).getExpression();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression implicitReturnExpr() {
		Expression _xifexpression = null;
		boolean _isSingleExprImplicitReturn = this.isSingleExprImplicitReturn();
		if (_isSingleExprImplicitReturn) {
			Statement _get = this.getBody().getStatements().get(0);
			_xifexpression = ((ExpressionStatement) _get).getExpression();
		}
		else {
			_xifexpression = null;
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
			case N4JSPackage.ARROW_FUNCTION__HAS_BRACES_AROUND_BODY:
				return isHasBracesAroundBody();
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
			case N4JSPackage.ARROW_FUNCTION__HAS_BRACES_AROUND_BODY:
				setHasBracesAroundBody((Boolean)newValue);
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
			case N4JSPackage.ARROW_FUNCTION__HAS_BRACES_AROUND_BODY:
				setHasBracesAroundBody(HAS_BRACES_AROUND_BODY_EDEFAULT);
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
			case N4JSPackage.ARROW_FUNCTION__HAS_BRACES_AROUND_BODY:
				return hasBracesAroundBody != HAS_BRACES_AROUND_BODY_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == FunctionExpression.class) {
			switch (baseOperationID) {
				case N4JSPackage.FUNCTION_EXPRESSION___IS_ARROW_FUNCTION: return N4JSPackage.ARROW_FUNCTION___IS_ARROW_FUNCTION;
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
			case N4JSPackage.ARROW_FUNCTION___IS_ARROW_FUNCTION:
				return isArrowFunction();
			case N4JSPackage.ARROW_FUNCTION___IS_SINGLE_EXPR_IMPLICIT_RETURN:
				return isSingleExprImplicitReturn();
			case N4JSPackage.ARROW_FUNCTION___GET_SINGLE_EXPRESSION:
				return getSingleExpression();
			case N4JSPackage.ARROW_FUNCTION___IMPLICIT_RETURN_EXPR:
				return implicitReturnExpr();
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
		result.append(" (hasBracesAroundBody: ");
		result.append(hasBracesAroundBody);
		result.append(')');
		return result.toString();
	}

} //ArrowFunctionImpl
