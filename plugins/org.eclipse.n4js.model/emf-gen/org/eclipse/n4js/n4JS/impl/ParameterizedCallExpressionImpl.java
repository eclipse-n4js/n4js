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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.ParameterizedAccess;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parameterized Call Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ParameterizedCallExpressionImpl#getTypeArgs <em>Type Args</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ParameterizedCallExpressionImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ParameterizedCallExpressionImpl#getArguments <em>Arguments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParameterizedCallExpressionImpl extends ExpressionImpl implements ParameterizedCallExpression {
	/**
	 * The cached value of the '{@link #getTypeArgs() <em>Type Args</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeArgs()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeRef> typeArgs;

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
	 * The cached value of the '{@link #getArguments() <em>Arguments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArguments()
	 * @generated
	 * @ordered
	 */
	protected EList<Argument> arguments;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParameterizedCallExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.PARAMETERIZED_CALL_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TypeRef> getTypeArgs() {
		if (typeArgs == null) {
			typeArgs = new EObjectContainmentEList<TypeRef>(TypeRef.class, this, N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__TYPE_ARGS);
		}
		return typeArgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__TARGET, oldTarget, newTarget);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(Expression newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject)target).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__TARGET, null, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__TARGET, null, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__TARGET, newTarget, newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Argument> getArguments() {
		if (arguments == null) {
			arguments = new EObjectContainmentEList<Argument>(Argument.class, this, N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__ARGUMENTS);
		}
		return arguments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getReceiver() {
		Expression _xifexpression = null;
		Expression _target = this.getTarget();
		if ((_target instanceof ParameterizedPropertyAccessExpression)) {
			Expression _target_1 = this.getTarget();
			_xifexpression = ((ParameterizedPropertyAccessExpression) _target_1).getTarget();
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
	public boolean isParameterized() {
		boolean _isEmpty = this.getTypeArgs().isEmpty();
		return (!_isEmpty);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__TYPE_ARGS:
				return ((InternalEList<?>)getTypeArgs()).basicRemove(otherEnd, msgs);
			case N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__TARGET:
				return basicSetTarget(null, msgs);
			case N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__ARGUMENTS:
				return ((InternalEList<?>)getArguments()).basicRemove(otherEnd, msgs);
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
			case N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__TYPE_ARGS:
				return getTypeArgs();
			case N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__TARGET:
				return getTarget();
			case N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__ARGUMENTS:
				return getArguments();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__TYPE_ARGS:
				getTypeArgs().clear();
				getTypeArgs().addAll((Collection<? extends TypeRef>)newValue);
				return;
			case N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__TARGET:
				setTarget((Expression)newValue);
				return;
			case N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__ARGUMENTS:
				getArguments().clear();
				getArguments().addAll((Collection<? extends Argument>)newValue);
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
			case N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__TYPE_ARGS:
				getTypeArgs().clear();
				return;
			case N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__TARGET:
				setTarget((Expression)null);
				return;
			case N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__ARGUMENTS:
				getArguments().clear();
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
			case N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__TYPE_ARGS:
				return typeArgs != null && !typeArgs.isEmpty();
			case N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__TARGET:
				return target != null;
			case N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__ARGUMENTS:
				return arguments != null && !arguments.isEmpty();
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
		if (baseClass == ParameterizedAccess.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__TYPE_ARGS: return N4JSPackage.PARAMETERIZED_ACCESS__TYPE_ARGS;
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
		if (baseClass == ParameterizedAccess.class) {
			switch (baseFeatureID) {
				case N4JSPackage.PARAMETERIZED_ACCESS__TYPE_ARGS: return N4JSPackage.PARAMETERIZED_CALL_EXPRESSION__TYPE_ARGS;
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
		if (baseClass == ParameterizedAccess.class) {
			switch (baseOperationID) {
				case N4JSPackage.PARAMETERIZED_ACCESS___IS_PARAMETERIZED: return N4JSPackage.PARAMETERIZED_CALL_EXPRESSION___IS_PARAMETERIZED;
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
			case N4JSPackage.PARAMETERIZED_CALL_EXPRESSION___GET_RECEIVER:
				return getReceiver();
			case N4JSPackage.PARAMETERIZED_CALL_EXPRESSION___IS_PARAMETERIZED:
				return isParameterized();
		}
		return super.eInvoke(operationID, arguments);
	}

} //ParameterizedCallExpressionImpl
