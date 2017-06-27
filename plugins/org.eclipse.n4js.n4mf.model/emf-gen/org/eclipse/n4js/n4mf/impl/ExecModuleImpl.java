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
package org.eclipse.n4js.n4mf.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.n4js.n4mf.BootstrapModule;
import org.eclipse.n4js.n4mf.ExecModule;
import org.eclipse.n4js.n4mf.N4mfPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Exec Module</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ExecModuleImpl#getExecModule <em>Exec Module</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExecModuleImpl extends MinimalEObjectImpl.Container implements ExecModule {
	/**
	 * The cached value of the '{@link #getExecModule() <em>Exec Module</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExecModule()
	 * @generated
	 * @ordered
	 */
	protected BootstrapModule execModule;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExecModuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4mfPackage.Literals.EXEC_MODULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BootstrapModule getExecModule() {
		return execModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExecModule(BootstrapModule newExecModule, NotificationChain msgs) {
		BootstrapModule oldExecModule = execModule;
		execModule = newExecModule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4mfPackage.EXEC_MODULE__EXEC_MODULE, oldExecModule, newExecModule);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExecModule(BootstrapModule newExecModule) {
		if (newExecModule != execModule) {
			NotificationChain msgs = null;
			if (execModule != null)
				msgs = ((InternalEObject)execModule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.EXEC_MODULE__EXEC_MODULE, null, msgs);
			if (newExecModule != null)
				msgs = ((InternalEObject)newExecModule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.EXEC_MODULE__EXEC_MODULE, null, msgs);
			msgs = basicSetExecModule(newExecModule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.EXEC_MODULE__EXEC_MODULE, newExecModule, newExecModule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4mfPackage.EXEC_MODULE__EXEC_MODULE:
				return basicSetExecModule(null, msgs);
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
			case N4mfPackage.EXEC_MODULE__EXEC_MODULE:
				return getExecModule();
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
			case N4mfPackage.EXEC_MODULE__EXEC_MODULE:
				setExecModule((BootstrapModule)newValue);
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
			case N4mfPackage.EXEC_MODULE__EXEC_MODULE:
				setExecModule((BootstrapModule)null);
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
			case N4mfPackage.EXEC_MODULE__EXEC_MODULE:
				return execModule != null;
		}
		return super.eIsSet(featureID);
	}

} //ExecModuleImpl
