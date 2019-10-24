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

import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionWithTarget;
import org.eclipse.n4js.n4JS.N4JSPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Expression With Target</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ExpressionWithTargetImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ExpressionWithTargetImpl#isOptionalChaining <em>Optional Chaining</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ExpressionWithTargetImpl extends ExpressionImpl implements ExpressionWithTarget {
	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected Expression target;

	/**
	 * The default value of the '{@link #isOptionalChaining() <em>Optional Chaining</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOptionalChaining()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OPTIONAL_CHAINING_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isOptionalChaining() <em>Optional Chaining</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOptionalChaining()
	 * @generated
	 * @ordered
	 */
	protected boolean optionalChaining = OPTIONAL_CHAINING_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExpressionWithTargetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.EXPRESSION_WITH_TARGET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Expression getTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(Expression newTarget, NotificationChain msgs) {
		Expression oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPRESSION_WITH_TARGET__TARGET, oldTarget, newTarget);
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
	public void setTarget(Expression newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject)target).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.EXPRESSION_WITH_TARGET__TARGET, null, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.EXPRESSION_WITH_TARGET__TARGET, null, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPRESSION_WITH_TARGET__TARGET, newTarget, newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isOptionalChaining() {
		return optionalChaining;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOptionalChaining(boolean newOptionalChaining) {
		boolean oldOptionalChaining = optionalChaining;
		optionalChaining = newOptionalChaining;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPRESSION_WITH_TARGET__OPTIONAL_CHAINING, oldOptionalChaining, optionalChaining));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isOrHasTargetWithOptionalChaining() {
		boolean _isOptionalChaining = this.isOptionalChaining();
		if (_isOptionalChaining) {
			return true;
		}
		final Expression _target = this.getTarget();
		if ((_target instanceof ExpressionWithTarget)) {
			return ((ExpressionWithTarget)_target).isOrHasTargetWithOptionalChaining();
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.EXPRESSION_WITH_TARGET__TARGET:
				return basicSetTarget(null, msgs);
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
			case N4JSPackage.EXPRESSION_WITH_TARGET__TARGET:
				return getTarget();
			case N4JSPackage.EXPRESSION_WITH_TARGET__OPTIONAL_CHAINING:
				return isOptionalChaining();
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
			case N4JSPackage.EXPRESSION_WITH_TARGET__TARGET:
				setTarget((Expression)newValue);
				return;
			case N4JSPackage.EXPRESSION_WITH_TARGET__OPTIONAL_CHAINING:
				setOptionalChaining((Boolean)newValue);
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
			case N4JSPackage.EXPRESSION_WITH_TARGET__TARGET:
				setTarget((Expression)null);
				return;
			case N4JSPackage.EXPRESSION_WITH_TARGET__OPTIONAL_CHAINING:
				setOptionalChaining(OPTIONAL_CHAINING_EDEFAULT);
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
			case N4JSPackage.EXPRESSION_WITH_TARGET__TARGET:
				return target != null;
			case N4JSPackage.EXPRESSION_WITH_TARGET__OPTIONAL_CHAINING:
				return optionalChaining != OPTIONAL_CHAINING_EDEFAULT;
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
			case N4JSPackage.EXPRESSION_WITH_TARGET___IS_OR_HAS_TARGET_WITH_OPTIONAL_CHAINING:
				return isOrHasTargetWithOptionalChaining();
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
		result.append(" (optionalChaining: ");
		result.append(optionalChaining);
		result.append(')');
		return result.toString();
	}

} //ExpressionWithTargetImpl
