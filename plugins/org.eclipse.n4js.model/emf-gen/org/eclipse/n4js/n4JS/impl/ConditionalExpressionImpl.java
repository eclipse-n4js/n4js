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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.N4JSPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Conditional Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ConditionalExpressionImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ConditionalExpressionImpl#getTrueExpression <em>True Expression</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ConditionalExpressionImpl#getFalseExpression <em>False Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConditionalExpressionImpl extends ExpressionImpl implements ConditionalExpression {
	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected Expression expression;

	/**
	 * The cached value of the '{@link #getTrueExpression() <em>True Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrueExpression()
	 * @generated
	 * @ordered
	 */
	protected Expression trueExpression;

	/**
	 * The cached value of the '{@link #getFalseExpression() <em>False Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFalseExpression()
	 * @generated
	 * @ordered
	 */
	protected Expression falseExpression;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConditionalExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.CONDITIONAL_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getExpression() {
		return expression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExpression(Expression newExpression, NotificationChain msgs) {
		Expression oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.CONDITIONAL_EXPRESSION__EXPRESSION, oldExpression, newExpression);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpression(Expression newExpression) {
		if (newExpression != expression) {
			NotificationChain msgs = null;
			if (expression != null)
				msgs = ((InternalEObject)expression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.CONDITIONAL_EXPRESSION__EXPRESSION, null, msgs);
			if (newExpression != null)
				msgs = ((InternalEObject)newExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.CONDITIONAL_EXPRESSION__EXPRESSION, null, msgs);
			msgs = basicSetExpression(newExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.CONDITIONAL_EXPRESSION__EXPRESSION, newExpression, newExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getTrueExpression() {
		return trueExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrueExpression(Expression newTrueExpression, NotificationChain msgs) {
		Expression oldTrueExpression = trueExpression;
		trueExpression = newTrueExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.CONDITIONAL_EXPRESSION__TRUE_EXPRESSION, oldTrueExpression, newTrueExpression);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrueExpression(Expression newTrueExpression) {
		if (newTrueExpression != trueExpression) {
			NotificationChain msgs = null;
			if (trueExpression != null)
				msgs = ((InternalEObject)trueExpression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.CONDITIONAL_EXPRESSION__TRUE_EXPRESSION, null, msgs);
			if (newTrueExpression != null)
				msgs = ((InternalEObject)newTrueExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.CONDITIONAL_EXPRESSION__TRUE_EXPRESSION, null, msgs);
			msgs = basicSetTrueExpression(newTrueExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.CONDITIONAL_EXPRESSION__TRUE_EXPRESSION, newTrueExpression, newTrueExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getFalseExpression() {
		return falseExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFalseExpression(Expression newFalseExpression, NotificationChain msgs) {
		Expression oldFalseExpression = falseExpression;
		falseExpression = newFalseExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.CONDITIONAL_EXPRESSION__FALSE_EXPRESSION, oldFalseExpression, newFalseExpression);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFalseExpression(Expression newFalseExpression) {
		if (newFalseExpression != falseExpression) {
			NotificationChain msgs = null;
			if (falseExpression != null)
				msgs = ((InternalEObject)falseExpression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.CONDITIONAL_EXPRESSION__FALSE_EXPRESSION, null, msgs);
			if (newFalseExpression != null)
				msgs = ((InternalEObject)newFalseExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.CONDITIONAL_EXPRESSION__FALSE_EXPRESSION, null, msgs);
			msgs = basicSetFalseExpression(newFalseExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.CONDITIONAL_EXPRESSION__FALSE_EXPRESSION, newFalseExpression, newFalseExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.CONDITIONAL_EXPRESSION__EXPRESSION:
				return basicSetExpression(null, msgs);
			case N4JSPackage.CONDITIONAL_EXPRESSION__TRUE_EXPRESSION:
				return basicSetTrueExpression(null, msgs);
			case N4JSPackage.CONDITIONAL_EXPRESSION__FALSE_EXPRESSION:
				return basicSetFalseExpression(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case N4JSPackage.CONDITIONAL_EXPRESSION__EXPRESSION:
				return getExpression();
			case N4JSPackage.CONDITIONAL_EXPRESSION__TRUE_EXPRESSION:
				return getTrueExpression();
			case N4JSPackage.CONDITIONAL_EXPRESSION__FALSE_EXPRESSION:
				return getFalseExpression();
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
			case N4JSPackage.CONDITIONAL_EXPRESSION__EXPRESSION:
				setExpression((Expression)newValue);
				return;
			case N4JSPackage.CONDITIONAL_EXPRESSION__TRUE_EXPRESSION:
				setTrueExpression((Expression)newValue);
				return;
			case N4JSPackage.CONDITIONAL_EXPRESSION__FALSE_EXPRESSION:
				setFalseExpression((Expression)newValue);
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
			case N4JSPackage.CONDITIONAL_EXPRESSION__EXPRESSION:
				setExpression((Expression)null);
				return;
			case N4JSPackage.CONDITIONAL_EXPRESSION__TRUE_EXPRESSION:
				setTrueExpression((Expression)null);
				return;
			case N4JSPackage.CONDITIONAL_EXPRESSION__FALSE_EXPRESSION:
				setFalseExpression((Expression)null);
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
			case N4JSPackage.CONDITIONAL_EXPRESSION__EXPRESSION:
				return expression != null;
			case N4JSPackage.CONDITIONAL_EXPRESSION__TRUE_EXPRESSION:
				return trueExpression != null;
			case N4JSPackage.CONDITIONAL_EXPRESSION__FALSE_EXPRESSION:
				return falseExpression != null;
		}
		return super.eIsSet(featureID);
	}

} //ConditionalExpressionImpl
