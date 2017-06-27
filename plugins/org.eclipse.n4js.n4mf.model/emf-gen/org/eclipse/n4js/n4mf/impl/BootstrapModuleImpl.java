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

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.n4js.n4mf.BootstrapModule;
import org.eclipse.n4js.n4mf.N4mfPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bootstrap Module</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.BootstrapModuleImpl#getModuleSpecifierWithWildcard <em>Module Specifier With Wildcard</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.BootstrapModuleImpl#getSourcePath <em>Source Path</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BootstrapModuleImpl extends MinimalEObjectImpl.Container implements BootstrapModule {
	/**
	 * The default value of the '{@link #getModuleSpecifierWithWildcard() <em>Module Specifier With Wildcard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleSpecifierWithWildcard()
	 * @generated
	 * @ordered
	 */
	protected static final String MODULE_SPECIFIER_WITH_WILDCARD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModuleSpecifierWithWildcard() <em>Module Specifier With Wildcard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleSpecifierWithWildcard()
	 * @generated
	 * @ordered
	 */
	protected String moduleSpecifierWithWildcard = MODULE_SPECIFIER_WITH_WILDCARD_EDEFAULT;

	/**
	 * The default value of the '{@link #getSourcePath() <em>Source Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourcePath()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSourcePath() <em>Source Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourcePath()
	 * @generated
	 * @ordered
	 */
	protected String sourcePath = SOURCE_PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BootstrapModuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4mfPackage.Literals.BOOTSTRAP_MODULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getModuleSpecifierWithWildcard() {
		return moduleSpecifierWithWildcard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModuleSpecifierWithWildcard(String newModuleSpecifierWithWildcard) {
		String oldModuleSpecifierWithWildcard = moduleSpecifierWithWildcard;
		moduleSpecifierWithWildcard = newModuleSpecifierWithWildcard;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.BOOTSTRAP_MODULE__MODULE_SPECIFIER_WITH_WILDCARD, oldModuleSpecifierWithWildcard, moduleSpecifierWithWildcard));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSourcePath() {
		return sourcePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourcePath(String newSourcePath) {
		String oldSourcePath = sourcePath;
		sourcePath = newSourcePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.BOOTSTRAP_MODULE__SOURCE_PATH, oldSourcePath, sourcePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case N4mfPackage.BOOTSTRAP_MODULE__MODULE_SPECIFIER_WITH_WILDCARD:
				return getModuleSpecifierWithWildcard();
			case N4mfPackage.BOOTSTRAP_MODULE__SOURCE_PATH:
				return getSourcePath();
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
			case N4mfPackage.BOOTSTRAP_MODULE__MODULE_SPECIFIER_WITH_WILDCARD:
				setModuleSpecifierWithWildcard((String)newValue);
				return;
			case N4mfPackage.BOOTSTRAP_MODULE__SOURCE_PATH:
				setSourcePath((String)newValue);
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
			case N4mfPackage.BOOTSTRAP_MODULE__MODULE_SPECIFIER_WITH_WILDCARD:
				setModuleSpecifierWithWildcard(MODULE_SPECIFIER_WITH_WILDCARD_EDEFAULT);
				return;
			case N4mfPackage.BOOTSTRAP_MODULE__SOURCE_PATH:
				setSourcePath(SOURCE_PATH_EDEFAULT);
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
			case N4mfPackage.BOOTSTRAP_MODULE__MODULE_SPECIFIER_WITH_WILDCARD:
				return MODULE_SPECIFIER_WITH_WILDCARD_EDEFAULT == null ? moduleSpecifierWithWildcard != null : !MODULE_SPECIFIER_WITH_WILDCARD_EDEFAULT.equals(moduleSpecifierWithWildcard);
			case N4mfPackage.BOOTSTRAP_MODULE__SOURCE_PATH:
				return SOURCE_PATH_EDEFAULT == null ? sourcePath != null : !SOURCE_PATH_EDEFAULT.equals(sourcePath);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (moduleSpecifierWithWildcard: ");
		result.append(moduleSpecifierWithWildcard);
		result.append(", sourcePath: ");
		result.append(sourcePath);
		result.append(')');
		return result.toString();
	}

} //BootstrapModuleImpl
