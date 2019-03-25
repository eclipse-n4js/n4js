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

import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.Statement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>If Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.IfStatementImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.IfStatementImpl#getIfStmt <em>If Stmt</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.IfStatementImpl#getElseStmt <em>Else Stmt</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IfStatementImpl extends StatementImpl implements IfStatement {
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
	 * The cached value of the '{@link #getIfStmt() <em>If Stmt</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIfStmt()
	 * @generated
	 * @ordered
	 */
	protected Statement ifStmt;

	/**
	 * The cached value of the '{@link #getElseStmt() <em>Else Stmt</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElseStmt()
	 * @generated
	 * @ordered
	 */
	protected Statement elseStmt;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IfStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.IF_STATEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.IF_STATEMENT__EXPRESSION, oldExpression, newExpression);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExpression(Expression newExpression) {
		if (newExpression != expression) {
			NotificationChain msgs = null;
			if (expression != null)
				msgs = ((InternalEObject)expression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.IF_STATEMENT__EXPRESSION, null, msgs);
			if (newExpression != null)
				msgs = ((InternalEObject)newExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.IF_STATEMENT__EXPRESSION, null, msgs);
			msgs = basicSetExpression(newExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.IF_STATEMENT__EXPRESSION, newExpression, newExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Statement getIfStmt() {
		return ifStmt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIfStmt(Statement newIfStmt, NotificationChain msgs) {
		Statement oldIfStmt = ifStmt;
		ifStmt = newIfStmt;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.IF_STATEMENT__IF_STMT, oldIfStmt, newIfStmt);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIfStmt(Statement newIfStmt) {
		if (newIfStmt != ifStmt) {
			NotificationChain msgs = null;
			if (ifStmt != null)
				msgs = ((InternalEObject)ifStmt).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.IF_STATEMENT__IF_STMT, null, msgs);
			if (newIfStmt != null)
				msgs = ((InternalEObject)newIfStmt).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.IF_STATEMENT__IF_STMT, null, msgs);
			msgs = basicSetIfStmt(newIfStmt, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.IF_STATEMENT__IF_STMT, newIfStmt, newIfStmt));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Statement getElseStmt() {
		return elseStmt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetElseStmt(Statement newElseStmt, NotificationChain msgs) {
		Statement oldElseStmt = elseStmt;
		elseStmt = newElseStmt;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.IF_STATEMENT__ELSE_STMT, oldElseStmt, newElseStmt);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setElseStmt(Statement newElseStmt) {
		if (newElseStmt != elseStmt) {
			NotificationChain msgs = null;
			if (elseStmt != null)
				msgs = ((InternalEObject)elseStmt).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.IF_STATEMENT__ELSE_STMT, null, msgs);
			if (newElseStmt != null)
				msgs = ((InternalEObject)newElseStmt).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.IF_STATEMENT__ELSE_STMT, null, msgs);
			msgs = basicSetElseStmt(newElseStmt, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.IF_STATEMENT__ELSE_STMT, newElseStmt, newElseStmt));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.IF_STATEMENT__EXPRESSION:
				return basicSetExpression(null, msgs);
			case N4JSPackage.IF_STATEMENT__IF_STMT:
				return basicSetIfStmt(null, msgs);
			case N4JSPackage.IF_STATEMENT__ELSE_STMT:
				return basicSetElseStmt(null, msgs);
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
			case N4JSPackage.IF_STATEMENT__EXPRESSION:
				return getExpression();
			case N4JSPackage.IF_STATEMENT__IF_STMT:
				return getIfStmt();
			case N4JSPackage.IF_STATEMENT__ELSE_STMT:
				return getElseStmt();
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
			case N4JSPackage.IF_STATEMENT__EXPRESSION:
				setExpression((Expression)newValue);
				return;
			case N4JSPackage.IF_STATEMENT__IF_STMT:
				setIfStmt((Statement)newValue);
				return;
			case N4JSPackage.IF_STATEMENT__ELSE_STMT:
				setElseStmt((Statement)newValue);
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
			case N4JSPackage.IF_STATEMENT__EXPRESSION:
				setExpression((Expression)null);
				return;
			case N4JSPackage.IF_STATEMENT__IF_STMT:
				setIfStmt((Statement)null);
				return;
			case N4JSPackage.IF_STATEMENT__ELSE_STMT:
				setElseStmt((Statement)null);
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
			case N4JSPackage.IF_STATEMENT__EXPRESSION:
				return expression != null;
			case N4JSPackage.IF_STATEMENT__IF_STMT:
				return ifStmt != null;
			case N4JSPackage.IF_STATEMENT__ELSE_STMT:
				return elseStmt != null;
		}
		return super.eIsSet(featureID);
	}

} //IfStatementImpl
