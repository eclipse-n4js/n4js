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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.ExportedVariableBinding;
import org.eclipse.n4js.n4JS.N4JSPackage;

import org.eclipse.n4js.ts.types.TVariable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Exported Variable Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ExportedVariableBindingImpl#getDefinedVariable <em>Defined Variable</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExportedVariableBindingImpl extends VariableBindingImpl implements ExportedVariableBinding {
	/**
	 * The cached value of the '{@link #getDefinedVariable() <em>Defined Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinedVariable()
	 * @generated
	 * @ordered
	 */
	protected TVariable definedVariable;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExportedVariableBindingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.EXPORTED_VARIABLE_BINDING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TVariable getDefinedVariable() {
		if (definedVariable != null && definedVariable.eIsProxy()) {
			InternalEObject oldDefinedVariable = (InternalEObject)definedVariable;
			definedVariable = (TVariable)eResolveProxy(oldDefinedVariable);
			if (definedVariable != oldDefinedVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.EXPORTED_VARIABLE_BINDING__DEFINED_VARIABLE, oldDefinedVariable, definedVariable));
			}
		}
		return definedVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TVariable basicGetDefinedVariable() {
		return definedVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefinedVariable(TVariable newDefinedVariable) {
		TVariable oldDefinedVariable = definedVariable;
		definedVariable = newDefinedVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.EXPORTED_VARIABLE_BINDING__DEFINED_VARIABLE, oldDefinedVariable, definedVariable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case N4JSPackage.EXPORTED_VARIABLE_BINDING__DEFINED_VARIABLE:
				if (resolve) return getDefinedVariable();
				return basicGetDefinedVariable();
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
			case N4JSPackage.EXPORTED_VARIABLE_BINDING__DEFINED_VARIABLE:
				setDefinedVariable((TVariable)newValue);
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
			case N4JSPackage.EXPORTED_VARIABLE_BINDING__DEFINED_VARIABLE:
				setDefinedVariable((TVariable)null);
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
			case N4JSPackage.EXPORTED_VARIABLE_BINDING__DEFINED_VARIABLE:
				return definedVariable != null;
		}
		return super.eIsSet(featureID);
	}

} //ExportedVariableBindingImpl
