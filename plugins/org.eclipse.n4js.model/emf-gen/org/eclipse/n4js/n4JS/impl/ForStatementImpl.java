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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.IterationStatement;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.VariableEnvironmentElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>For Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ForStatementImpl#getStatement <em>Statement</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ForStatementImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ForStatementImpl#getInitExpr <em>Init Expr</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ForStatementImpl#getUpdateExpr <em>Update Expr</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ForStatementImpl#isForIn <em>For In</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ForStatementImpl#isForOf <em>For Of</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ForStatementImpl extends VariableDeclarationContainerImpl implements ForStatement {
	/**
	 * The cached value of the '{@link #getStatement() <em>Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatement()
	 * @generated
	 * @ordered
	 */
	protected Statement statement;

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
	 * The cached value of the '{@link #getInitExpr() <em>Init Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitExpr()
	 * @generated
	 * @ordered
	 */
	protected Expression initExpr;

	/**
	 * The cached value of the '{@link #getUpdateExpr() <em>Update Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdateExpr()
	 * @generated
	 * @ordered
	 */
	protected Expression updateExpr;

	/**
	 * The default value of the '{@link #isForIn() <em>For In</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isForIn()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FOR_IN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isForIn() <em>For In</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isForIn()
	 * @generated
	 * @ordered
	 */
	protected boolean forIn = FOR_IN_EDEFAULT;

	/**
	 * The default value of the '{@link #isForOf() <em>For Of</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isForOf()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FOR_OF_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isForOf() <em>For Of</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isForOf()
	 * @generated
	 * @ordered
	 */
	protected boolean forOf = FOR_OF_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ForStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.FOR_STATEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Statement getStatement() {
		return statement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStatement(Statement newStatement, NotificationChain msgs) {
		Statement oldStatement = statement;
		statement = newStatement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.FOR_STATEMENT__STATEMENT, oldStatement, newStatement);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatement(Statement newStatement) {
		if (newStatement != statement) {
			NotificationChain msgs = null;
			if (statement != null)
				msgs = ((InternalEObject)statement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FOR_STATEMENT__STATEMENT, null, msgs);
			if (newStatement != null)
				msgs = ((InternalEObject)newStatement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FOR_STATEMENT__STATEMENT, null, msgs);
			msgs = basicSetStatement(newStatement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FOR_STATEMENT__STATEMENT, newStatement, newStatement));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.FOR_STATEMENT__EXPRESSION, oldExpression, newExpression);
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
				msgs = ((InternalEObject)expression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FOR_STATEMENT__EXPRESSION, null, msgs);
			if (newExpression != null)
				msgs = ((InternalEObject)newExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FOR_STATEMENT__EXPRESSION, null, msgs);
			msgs = basicSetExpression(newExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FOR_STATEMENT__EXPRESSION, newExpression, newExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getInitExpr() {
		return initExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInitExpr(Expression newInitExpr, NotificationChain msgs) {
		Expression oldInitExpr = initExpr;
		initExpr = newInitExpr;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.FOR_STATEMENT__INIT_EXPR, oldInitExpr, newInitExpr);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitExpr(Expression newInitExpr) {
		if (newInitExpr != initExpr) {
			NotificationChain msgs = null;
			if (initExpr != null)
				msgs = ((InternalEObject)initExpr).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FOR_STATEMENT__INIT_EXPR, null, msgs);
			if (newInitExpr != null)
				msgs = ((InternalEObject)newInitExpr).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FOR_STATEMENT__INIT_EXPR, null, msgs);
			msgs = basicSetInitExpr(newInitExpr, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FOR_STATEMENT__INIT_EXPR, newInitExpr, newInitExpr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getUpdateExpr() {
		return updateExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUpdateExpr(Expression newUpdateExpr, NotificationChain msgs) {
		Expression oldUpdateExpr = updateExpr;
		updateExpr = newUpdateExpr;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.FOR_STATEMENT__UPDATE_EXPR, oldUpdateExpr, newUpdateExpr);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUpdateExpr(Expression newUpdateExpr) {
		if (newUpdateExpr != updateExpr) {
			NotificationChain msgs = null;
			if (updateExpr != null)
				msgs = ((InternalEObject)updateExpr).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FOR_STATEMENT__UPDATE_EXPR, null, msgs);
			if (newUpdateExpr != null)
				msgs = ((InternalEObject)newUpdateExpr).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FOR_STATEMENT__UPDATE_EXPR, null, msgs);
			msgs = basicSetUpdateExpr(newUpdateExpr, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FOR_STATEMENT__UPDATE_EXPR, newUpdateExpr, newUpdateExpr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isForIn() {
		return forIn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setForIn(boolean newForIn) {
		boolean oldForIn = forIn;
		forIn = newForIn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FOR_STATEMENT__FOR_IN, oldForIn, forIn));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isForOf() {
		return forOf;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setForOf(boolean newForOf) {
		boolean oldForOf = forOf;
		forOf = newForOf;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FOR_STATEMENT__FOR_OF, oldForOf, forOf));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isForPlain() {
		return ((!this.isForIn()) && (!this.isForOf()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean appliesOnlyToBlockScopedElements() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.FOR_STATEMENT__STATEMENT:
				return basicSetStatement(null, msgs);
			case N4JSPackage.FOR_STATEMENT__EXPRESSION:
				return basicSetExpression(null, msgs);
			case N4JSPackage.FOR_STATEMENT__INIT_EXPR:
				return basicSetInitExpr(null, msgs);
			case N4JSPackage.FOR_STATEMENT__UPDATE_EXPR:
				return basicSetUpdateExpr(null, msgs);
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
			case N4JSPackage.FOR_STATEMENT__STATEMENT:
				return getStatement();
			case N4JSPackage.FOR_STATEMENT__EXPRESSION:
				return getExpression();
			case N4JSPackage.FOR_STATEMENT__INIT_EXPR:
				return getInitExpr();
			case N4JSPackage.FOR_STATEMENT__UPDATE_EXPR:
				return getUpdateExpr();
			case N4JSPackage.FOR_STATEMENT__FOR_IN:
				return isForIn();
			case N4JSPackage.FOR_STATEMENT__FOR_OF:
				return isForOf();
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
			case N4JSPackage.FOR_STATEMENT__STATEMENT:
				setStatement((Statement)newValue);
				return;
			case N4JSPackage.FOR_STATEMENT__EXPRESSION:
				setExpression((Expression)newValue);
				return;
			case N4JSPackage.FOR_STATEMENT__INIT_EXPR:
				setInitExpr((Expression)newValue);
				return;
			case N4JSPackage.FOR_STATEMENT__UPDATE_EXPR:
				setUpdateExpr((Expression)newValue);
				return;
			case N4JSPackage.FOR_STATEMENT__FOR_IN:
				setForIn((Boolean)newValue);
				return;
			case N4JSPackage.FOR_STATEMENT__FOR_OF:
				setForOf((Boolean)newValue);
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
			case N4JSPackage.FOR_STATEMENT__STATEMENT:
				setStatement((Statement)null);
				return;
			case N4JSPackage.FOR_STATEMENT__EXPRESSION:
				setExpression((Expression)null);
				return;
			case N4JSPackage.FOR_STATEMENT__INIT_EXPR:
				setInitExpr((Expression)null);
				return;
			case N4JSPackage.FOR_STATEMENT__UPDATE_EXPR:
				setUpdateExpr((Expression)null);
				return;
			case N4JSPackage.FOR_STATEMENT__FOR_IN:
				setForIn(FOR_IN_EDEFAULT);
				return;
			case N4JSPackage.FOR_STATEMENT__FOR_OF:
				setForOf(FOR_OF_EDEFAULT);
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
			case N4JSPackage.FOR_STATEMENT__STATEMENT:
				return statement != null;
			case N4JSPackage.FOR_STATEMENT__EXPRESSION:
				return expression != null;
			case N4JSPackage.FOR_STATEMENT__INIT_EXPR:
				return initExpr != null;
			case N4JSPackage.FOR_STATEMENT__UPDATE_EXPR:
				return updateExpr != null;
			case N4JSPackage.FOR_STATEMENT__FOR_IN:
				return forIn != FOR_IN_EDEFAULT;
			case N4JSPackage.FOR_STATEMENT__FOR_OF:
				return forOf != FOR_OF_EDEFAULT;
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
		if (baseClass == ScriptElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == ControlFlowElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == Statement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == IterationStatement.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.FOR_STATEMENT__STATEMENT: return N4JSPackage.ITERATION_STATEMENT__STATEMENT;
				case N4JSPackage.FOR_STATEMENT__EXPRESSION: return N4JSPackage.ITERATION_STATEMENT__EXPRESSION;
				default: return -1;
			}
		}
		if (baseClass == VariableEnvironmentElement.class) {
			switch (derivedFeatureID) {
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
		if (baseClass == ScriptElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == ControlFlowElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == Statement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == IterationStatement.class) {
			switch (baseFeatureID) {
				case N4JSPackage.ITERATION_STATEMENT__STATEMENT: return N4JSPackage.FOR_STATEMENT__STATEMENT;
				case N4JSPackage.ITERATION_STATEMENT__EXPRESSION: return N4JSPackage.FOR_STATEMENT__EXPRESSION;
				default: return -1;
			}
		}
		if (baseClass == VariableEnvironmentElement.class) {
			switch (baseFeatureID) {
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
		if (baseClass == ScriptElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == ControlFlowElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == Statement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == IterationStatement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == VariableEnvironmentElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.VARIABLE_ENVIRONMENT_ELEMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS: return N4JSPackage.FOR_STATEMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;
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
			case N4JSPackage.FOR_STATEMENT___IS_FOR_PLAIN:
				return isForPlain();
			case N4JSPackage.FOR_STATEMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS:
				return appliesOnlyToBlockScopedElements();
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
		result.append(" (forIn: ");
		result.append(forIn);
		result.append(", forOf: ");
		result.append(forOf);
		result.append(')');
		return result.toString();
	}

} //ForStatementImpl
