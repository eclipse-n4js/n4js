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
package org.eclipse.n4js.ts.types.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.types.AbstractModule;
import org.eclipse.n4js.ts.types.ModuleExportDefinition;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Module Export Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.ModuleExportDefinitionImpl#getExportedModule <em>Exported Module</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ModuleExportDefinitionImpl extends ProxyResolvingEObjectImpl implements ModuleExportDefinition {
	/**
	 * The cached value of the '{@link #getExportedModule() <em>Exported Module</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportedModule()
	 * @generated
	 * @ordered
	 */
	protected AbstractModule exportedModule;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModuleExportDefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.MODULE_EXPORT_DEFINITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AbstractModule getExportedModule() {
		if (exportedModule != null && exportedModule.eIsProxy()) {
			InternalEObject oldExportedModule = (InternalEObject)exportedModule;
			exportedModule = (AbstractModule)eResolveProxy(oldExportedModule);
			if (exportedModule != oldExportedModule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.MODULE_EXPORT_DEFINITION__EXPORTED_MODULE, oldExportedModule, exportedModule));
			}
		}
		return exportedModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractModule basicGetExportedModule() {
		return exportedModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExportedModule(AbstractModule newExportedModule) {
		AbstractModule oldExportedModule = exportedModule;
		exportedModule = newExportedModule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.MODULE_EXPORT_DEFINITION__EXPORTED_MODULE, oldExportedModule, exportedModule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.MODULE_EXPORT_DEFINITION__EXPORTED_MODULE:
				if (resolve) return getExportedModule();
				return basicGetExportedModule();
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
			case TypesPackage.MODULE_EXPORT_DEFINITION__EXPORTED_MODULE:
				setExportedModule((AbstractModule)newValue);
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
			case TypesPackage.MODULE_EXPORT_DEFINITION__EXPORTED_MODULE:
				setExportedModule((AbstractModule)null);
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
			case TypesPackage.MODULE_EXPORT_DEFINITION__EXPORTED_MODULE:
				return exportedModule != null;
		}
		return super.eIsSet(featureID);
	}

} //ModuleExportDefinitionImpl
