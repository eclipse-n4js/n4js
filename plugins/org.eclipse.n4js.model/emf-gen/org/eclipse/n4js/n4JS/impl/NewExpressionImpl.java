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
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.ParameterizedAccess;
import org.eclipse.n4js.n4JS.TypeReferenceInAST;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>New Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.NewExpressionImpl#getTypeArgs <em>Type Args</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.NewExpressionImpl#getCallee <em>Callee</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.NewExpressionImpl#getArguments <em>Arguments</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.NewExpressionImpl#isWithArgs <em>With Args</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NewExpressionImpl extends ExpressionImpl implements NewExpression {
	/**
	 * The cached value of the '{@link #getTypeArgs() <em>Type Args</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeArgs()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeReferenceInAST> typeArgs;

	/**
	 * The cached value of the '{@link #getCallee() <em>Callee</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCallee()
	 * @generated
	 * @ordered
	 */
	protected Expression callee;

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
	 * The default value of the '{@link #isWithArgs() <em>With Args</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWithArgs()
	 * @generated
	 * @ordered
	 */
	protected static final boolean WITH_ARGS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isWithArgs() <em>With Args</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWithArgs()
	 * @generated
	 * @ordered
	 */
	protected boolean withArgs = WITH_ARGS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NewExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.NEW_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypeReferenceInAST> getTypeArgs() {
		if (typeArgs == null) {
			typeArgs = new EObjectContainmentEList<TypeReferenceInAST>(TypeReferenceInAST.class, this, N4JSPackage.NEW_EXPRESSION__TYPE_ARGS);
		}
		return typeArgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Expression getCallee() {
		return callee;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCallee(Expression newCallee, NotificationChain msgs) {
		Expression oldCallee = callee;
		callee = newCallee;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.NEW_EXPRESSION__CALLEE, oldCallee, newCallee);
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
	public void setCallee(Expression newCallee) {
		if (newCallee != callee) {
			NotificationChain msgs = null;
			if (callee != null)
				msgs = ((InternalEObject)callee).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.NEW_EXPRESSION__CALLEE, null, msgs);
			if (newCallee != null)
				msgs = ((InternalEObject)newCallee).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.NEW_EXPRESSION__CALLEE, null, msgs);
			msgs = basicSetCallee(newCallee, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.NEW_EXPRESSION__CALLEE, newCallee, newCallee));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Argument> getArguments() {
		if (arguments == null) {
			arguments = new EObjectContainmentEList<Argument>(Argument.class, this, N4JSPackage.NEW_EXPRESSION__ARGUMENTS);
		}
		return arguments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isWithArgs() {
		return withArgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWithArgs(boolean newWithArgs) {
		boolean oldWithArgs = withArgs;
		withArgs = newWithArgs;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.NEW_EXPRESSION__WITH_ARGS, oldWithArgs, withArgs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
			case N4JSPackage.NEW_EXPRESSION__TYPE_ARGS:
				return ((InternalEList<?>)getTypeArgs()).basicRemove(otherEnd, msgs);
			case N4JSPackage.NEW_EXPRESSION__CALLEE:
				return basicSetCallee(null, msgs);
			case N4JSPackage.NEW_EXPRESSION__ARGUMENTS:
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
			case N4JSPackage.NEW_EXPRESSION__TYPE_ARGS:
				return getTypeArgs();
			case N4JSPackage.NEW_EXPRESSION__CALLEE:
				return getCallee();
			case N4JSPackage.NEW_EXPRESSION__ARGUMENTS:
				return getArguments();
			case N4JSPackage.NEW_EXPRESSION__WITH_ARGS:
				return isWithArgs();
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
			case N4JSPackage.NEW_EXPRESSION__TYPE_ARGS:
				getTypeArgs().clear();
				getTypeArgs().addAll((Collection<? extends TypeReferenceInAST>)newValue);
				return;
			case N4JSPackage.NEW_EXPRESSION__CALLEE:
				setCallee((Expression)newValue);
				return;
			case N4JSPackage.NEW_EXPRESSION__ARGUMENTS:
				getArguments().clear();
				getArguments().addAll((Collection<? extends Argument>)newValue);
				return;
			case N4JSPackage.NEW_EXPRESSION__WITH_ARGS:
				setWithArgs((Boolean)newValue);
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
			case N4JSPackage.NEW_EXPRESSION__TYPE_ARGS:
				getTypeArgs().clear();
				return;
			case N4JSPackage.NEW_EXPRESSION__CALLEE:
				setCallee((Expression)null);
				return;
			case N4JSPackage.NEW_EXPRESSION__ARGUMENTS:
				getArguments().clear();
				return;
			case N4JSPackage.NEW_EXPRESSION__WITH_ARGS:
				setWithArgs(WITH_ARGS_EDEFAULT);
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
			case N4JSPackage.NEW_EXPRESSION__TYPE_ARGS:
				return typeArgs != null && !typeArgs.isEmpty();
			case N4JSPackage.NEW_EXPRESSION__CALLEE:
				return callee != null;
			case N4JSPackage.NEW_EXPRESSION__ARGUMENTS:
				return arguments != null && !arguments.isEmpty();
			case N4JSPackage.NEW_EXPRESSION__WITH_ARGS:
				return withArgs != WITH_ARGS_EDEFAULT;
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
				case N4JSPackage.NEW_EXPRESSION__TYPE_ARGS: return N4JSPackage.PARAMETERIZED_ACCESS__TYPE_ARGS;
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
				case N4JSPackage.PARAMETERIZED_ACCESS__TYPE_ARGS: return N4JSPackage.NEW_EXPRESSION__TYPE_ARGS;
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
				case N4JSPackage.PARAMETERIZED_ACCESS___IS_PARAMETERIZED: return N4JSPackage.NEW_EXPRESSION___IS_PARAMETERIZED;
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
			case N4JSPackage.NEW_EXPRESSION___IS_PARAMETERIZED:
				return isParameterized();
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
		result.append(" (withArgs: ");
		result.append(withArgs);
		result.append(')');
		return result.toString();
	}

} //NewExpressionImpl
