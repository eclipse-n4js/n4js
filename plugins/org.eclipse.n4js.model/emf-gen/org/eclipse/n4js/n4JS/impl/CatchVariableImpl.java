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

import org.eclipse.n4js.n4JS.BindingPattern;
import org.eclipse.n4js.n4JS.CatchVariable;
import org.eclipse.n4js.n4JS.N4JSPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Catch Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.CatchVariableImpl#getBindingPattern <em>Binding Pattern</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CatchVariableImpl extends VariableImpl implements CatchVariable {
	/**
	 * The cached value of the '{@link #getBindingPattern() <em>Binding Pattern</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBindingPattern()
	 * @generated
	 * @ordered
	 */
	protected BindingPattern bindingPattern;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CatchVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.CATCH_VARIABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BindingPattern getBindingPattern() {
		return bindingPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBindingPattern(BindingPattern newBindingPattern, NotificationChain msgs) {
		BindingPattern oldBindingPattern = bindingPattern;
		bindingPattern = newBindingPattern;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.CATCH_VARIABLE__BINDING_PATTERN, oldBindingPattern, newBindingPattern);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBindingPattern(BindingPattern newBindingPattern) {
		if (newBindingPattern != bindingPattern) {
			NotificationChain msgs = null;
			if (bindingPattern != null)
				msgs = ((InternalEObject)bindingPattern).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.CATCH_VARIABLE__BINDING_PATTERN, null, msgs);
			if (newBindingPattern != null)
				msgs = ((InternalEObject)newBindingPattern).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.CATCH_VARIABLE__BINDING_PATTERN, null, msgs);
			msgs = basicSetBindingPattern(newBindingPattern, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.CATCH_VARIABLE__BINDING_PATTERN, newBindingPattern, newBindingPattern));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.CATCH_VARIABLE__BINDING_PATTERN:
				return basicSetBindingPattern(null, msgs);
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
			case N4JSPackage.CATCH_VARIABLE__BINDING_PATTERN:
				return getBindingPattern();
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
			case N4JSPackage.CATCH_VARIABLE__BINDING_PATTERN:
				setBindingPattern((BindingPattern)newValue);
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
			case N4JSPackage.CATCH_VARIABLE__BINDING_PATTERN:
				setBindingPattern((BindingPattern)null);
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
			case N4JSPackage.CATCH_VARIABLE__BINDING_PATTERN:
				return bindingPattern != null;
		}
		return super.eIsSet(featureID);
	}

} //CatchVariableImpl
