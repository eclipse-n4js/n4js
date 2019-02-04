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

import org.eclipse.n4js.n4JS.BindingElement;
import org.eclipse.n4js.n4JS.BindingPattern;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.VariableDeclaration;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Binding Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.BindingElementImpl#isRest <em>Rest</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.BindingElementImpl#getVarDecl <em>Var Decl</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.BindingElementImpl#getNestedPattern <em>Nested Pattern</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.BindingElementImpl#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BindingElementImpl extends ProxyResolvingEObjectImpl implements BindingElement {
	/**
	 * The default value of the '{@link #isRest() <em>Rest</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRest()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REST_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRest() <em>Rest</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRest()
	 * @generated
	 * @ordered
	 */
	protected boolean rest = REST_EDEFAULT;

	/**
	 * The cached value of the '{@link #getVarDecl() <em>Var Decl</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVarDecl()
	 * @generated
	 * @ordered
	 */
	protected VariableDeclaration varDecl;

	/**
	 * The cached value of the '{@link #getNestedPattern() <em>Nested Pattern</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNestedPattern()
	 * @generated
	 * @ordered
	 */
	protected BindingPattern nestedPattern;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BindingElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.BINDING_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isRest() {
		return rest;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRest(boolean newRest) {
		boolean oldRest = rest;
		rest = newRest;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.BINDING_ELEMENT__REST, oldRest, rest));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VariableDeclaration getVarDecl() {
		return varDecl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVarDecl(VariableDeclaration newVarDecl, NotificationChain msgs) {
		VariableDeclaration oldVarDecl = varDecl;
		varDecl = newVarDecl;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.BINDING_ELEMENT__VAR_DECL, oldVarDecl, newVarDecl);
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
	public void setVarDecl(VariableDeclaration newVarDecl) {
		if (newVarDecl != varDecl) {
			NotificationChain msgs = null;
			if (varDecl != null)
				msgs = ((InternalEObject)varDecl).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.BINDING_ELEMENT__VAR_DECL, null, msgs);
			if (newVarDecl != null)
				msgs = ((InternalEObject)newVarDecl).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.BINDING_ELEMENT__VAR_DECL, null, msgs);
			msgs = basicSetVarDecl(newVarDecl, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.BINDING_ELEMENT__VAR_DECL, newVarDecl, newVarDecl));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BindingPattern getNestedPattern() {
		return nestedPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNestedPattern(BindingPattern newNestedPattern, NotificationChain msgs) {
		BindingPattern oldNestedPattern = nestedPattern;
		nestedPattern = newNestedPattern;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.BINDING_ELEMENT__NESTED_PATTERN, oldNestedPattern, newNestedPattern);
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
	public void setNestedPattern(BindingPattern newNestedPattern) {
		if (newNestedPattern != nestedPattern) {
			NotificationChain msgs = null;
			if (nestedPattern != null)
				msgs = ((InternalEObject)nestedPattern).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.BINDING_ELEMENT__NESTED_PATTERN, null, msgs);
			if (newNestedPattern != null)
				msgs = ((InternalEObject)newNestedPattern).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.BINDING_ELEMENT__NESTED_PATTERN, null, msgs);
			msgs = basicSetNestedPattern(newNestedPattern, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.BINDING_ELEMENT__NESTED_PATTERN, newNestedPattern, newNestedPattern));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.BINDING_ELEMENT__EXPRESSION, oldExpression, newExpression);
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
				msgs = ((InternalEObject)expression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.BINDING_ELEMENT__EXPRESSION, null, msgs);
			if (newExpression != null)
				msgs = ((InternalEObject)newExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.BINDING_ELEMENT__EXPRESSION, null, msgs);
			msgs = basicSetExpression(newExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.BINDING_ELEMENT__EXPRESSION, newExpression, newExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isElision() {
		return ((this.getVarDecl() == null) && (this.getNestedPattern() == null));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.BINDING_ELEMENT__VAR_DECL:
				return basicSetVarDecl(null, msgs);
			case N4JSPackage.BINDING_ELEMENT__NESTED_PATTERN:
				return basicSetNestedPattern(null, msgs);
			case N4JSPackage.BINDING_ELEMENT__EXPRESSION:
				return basicSetExpression(null, msgs);
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
			case N4JSPackage.BINDING_ELEMENT__REST:
				return isRest();
			case N4JSPackage.BINDING_ELEMENT__VAR_DECL:
				return getVarDecl();
			case N4JSPackage.BINDING_ELEMENT__NESTED_PATTERN:
				return getNestedPattern();
			case N4JSPackage.BINDING_ELEMENT__EXPRESSION:
				return getExpression();
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
			case N4JSPackage.BINDING_ELEMENT__REST:
				setRest((Boolean)newValue);
				return;
			case N4JSPackage.BINDING_ELEMENT__VAR_DECL:
				setVarDecl((VariableDeclaration)newValue);
				return;
			case N4JSPackage.BINDING_ELEMENT__NESTED_PATTERN:
				setNestedPattern((BindingPattern)newValue);
				return;
			case N4JSPackage.BINDING_ELEMENT__EXPRESSION:
				setExpression((Expression)newValue);
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
			case N4JSPackage.BINDING_ELEMENT__REST:
				setRest(REST_EDEFAULT);
				return;
			case N4JSPackage.BINDING_ELEMENT__VAR_DECL:
				setVarDecl((VariableDeclaration)null);
				return;
			case N4JSPackage.BINDING_ELEMENT__NESTED_PATTERN:
				setNestedPattern((BindingPattern)null);
				return;
			case N4JSPackage.BINDING_ELEMENT__EXPRESSION:
				setExpression((Expression)null);
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
			case N4JSPackage.BINDING_ELEMENT__REST:
				return rest != REST_EDEFAULT;
			case N4JSPackage.BINDING_ELEMENT__VAR_DECL:
				return varDecl != null;
			case N4JSPackage.BINDING_ELEMENT__NESTED_PATTERN:
				return nestedPattern != null;
			case N4JSPackage.BINDING_ELEMENT__EXPRESSION:
				return expression != null;
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
			case N4JSPackage.BINDING_ELEMENT___IS_ELISION:
				return isElision();
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
		result.append(" (rest: ");
		result.append(rest);
		result.append(')');
		return result.toString();
	}

} //BindingElementImpl
