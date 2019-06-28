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

import org.eclipse.n4js.n4JS.CatchBlock;
import org.eclipse.n4js.n4JS.CatchVariable;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.VariableEnvironmentElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Catch Block</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.CatchBlockImpl#getCatchVariable <em>Catch Variable</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CatchBlockImpl extends AbstractCatchBlockImpl implements CatchBlock {
	/**
	 * The cached value of the '{@link #getCatchVariable() <em>Catch Variable</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCatchVariable()
	 * @generated
	 * @ordered
	 */
	protected CatchVariable catchVariable;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CatchBlockImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.CATCH_BLOCK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CatchVariable getCatchVariable() {
		return catchVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCatchVariable(CatchVariable newCatchVariable, NotificationChain msgs) {
		CatchVariable oldCatchVariable = catchVariable;
		catchVariable = newCatchVariable;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.CATCH_BLOCK__CATCH_VARIABLE, oldCatchVariable, newCatchVariable);
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
	public void setCatchVariable(CatchVariable newCatchVariable) {
		if (newCatchVariable != catchVariable) {
			NotificationChain msgs = null;
			if (catchVariable != null)
				msgs = ((InternalEObject)catchVariable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.CATCH_BLOCK__CATCH_VARIABLE, null, msgs);
			if (newCatchVariable != null)
				msgs = ((InternalEObject)newCatchVariable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.CATCH_BLOCK__CATCH_VARIABLE, null, msgs);
			msgs = basicSetCatchVariable(newCatchVariable, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.CATCH_BLOCK__CATCH_VARIABLE, newCatchVariable, newCatchVariable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean appliesOnlyToBlockScopedElements() {
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
			case N4JSPackage.CATCH_BLOCK__CATCH_VARIABLE:
				return basicSetCatchVariable(null, msgs);
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
			case N4JSPackage.CATCH_BLOCK__CATCH_VARIABLE:
				return getCatchVariable();
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
			case N4JSPackage.CATCH_BLOCK__CATCH_VARIABLE:
				setCatchVariable((CatchVariable)newValue);
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
			case N4JSPackage.CATCH_BLOCK__CATCH_VARIABLE:
				setCatchVariable((CatchVariable)null);
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
			case N4JSPackage.CATCH_BLOCK__CATCH_VARIABLE:
				return catchVariable != null;
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
		if (baseClass == VariableEnvironmentElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.VARIABLE_ENVIRONMENT_ELEMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS: return N4JSPackage.CATCH_BLOCK___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;
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
			case N4JSPackage.CATCH_BLOCK___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS:
				return appliesOnlyToBlockScopedElements();
		}
		return super.eInvoke(operationID, arguments);
	}

} //CatchBlockImpl
