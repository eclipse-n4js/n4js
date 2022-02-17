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

import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4ModuleDeclaration;

import org.eclipse.n4js.ts.types.TNestedModule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>N4 Module Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4ModuleDeclarationImpl#getDefinedModule <em>Defined Module</em>}</li>
 * </ul>
 *
 * @generated
 */
public class N4ModuleDeclarationImpl extends N4AbstractNamespaceDeclarationImpl implements N4ModuleDeclaration {
	/**
	 * The cached value of the '{@link #getDefinedModule() <em>Defined Module</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinedModule()
	 * @generated
	 * @ordered
	 */
	protected TNestedModule definedModule;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected N4ModuleDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.N4_MODULE_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TNestedModule getDefinedModule() {
		if (definedModule != null && definedModule.eIsProxy()) {
			InternalEObject oldDefinedModule = (InternalEObject)definedModule;
			definedModule = (TNestedModule)eResolveProxy(oldDefinedModule);
			if (definedModule != oldDefinedModule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.N4_MODULE_DECLARATION__DEFINED_MODULE, oldDefinedModule, definedModule));
			}
		}
		return definedModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TNestedModule basicGetDefinedModule() {
		return definedModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefinedModule(TNestedModule newDefinedModule) {
		TNestedModule oldDefinedModule = definedModule;
		definedModule = newDefinedModule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_MODULE_DECLARATION__DEFINED_MODULE, oldDefinedModule, definedModule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case N4JSPackage.N4_MODULE_DECLARATION__DEFINED_MODULE:
				if (resolve) return getDefinedModule();
				return basicGetDefinedModule();
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
			case N4JSPackage.N4_MODULE_DECLARATION__DEFINED_MODULE:
				setDefinedModule((TNestedModule)newValue);
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
			case N4JSPackage.N4_MODULE_DECLARATION__DEFINED_MODULE:
				setDefinedModule((TNestedModule)null);
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
			case N4JSPackage.N4_MODULE_DECLARATION__DEFINED_MODULE:
				return definedModule != null;
		}
		return super.eIsSet(featureID);
	}

} //N4ModuleDeclarationImpl
