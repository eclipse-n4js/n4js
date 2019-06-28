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
package org.eclipse.n4js.projectDescription.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.n4js.projectDescription.ModuleFilterSpecifier;
import org.eclipse.n4js.projectDescription.ProjectDescriptionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Module Filter Specifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ModuleFilterSpecifierImpl#getModuleSpecifierWithWildcard <em>Module Specifier With Wildcard</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ModuleFilterSpecifierImpl#getSourcePath <em>Source Path</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ModuleFilterSpecifierImpl extends MinimalEObjectImpl.Container implements ModuleFilterSpecifier {
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
	protected ModuleFilterSpecifierImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProjectDescriptionPackage.Literals.MODULE_FILTER_SPECIFIER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getModuleSpecifierWithWildcard() {
		return moduleSpecifierWithWildcard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModuleSpecifierWithWildcard(String newModuleSpecifierWithWildcard) {
		String oldModuleSpecifierWithWildcard = moduleSpecifierWithWildcard;
		moduleSpecifierWithWildcard = newModuleSpecifierWithWildcard;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.MODULE_FILTER_SPECIFIER__MODULE_SPECIFIER_WITH_WILDCARD, oldModuleSpecifierWithWildcard, moduleSpecifierWithWildcard));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSourcePath() {
		return sourcePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSourcePath(String newSourcePath) {
		String oldSourcePath = sourcePath;
		sourcePath = newSourcePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.MODULE_FILTER_SPECIFIER__SOURCE_PATH, oldSourcePath, sourcePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProjectDescriptionPackage.MODULE_FILTER_SPECIFIER__MODULE_SPECIFIER_WITH_WILDCARD:
				return getModuleSpecifierWithWildcard();
			case ProjectDescriptionPackage.MODULE_FILTER_SPECIFIER__SOURCE_PATH:
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
			case ProjectDescriptionPackage.MODULE_FILTER_SPECIFIER__MODULE_SPECIFIER_WITH_WILDCARD:
				setModuleSpecifierWithWildcard((String)newValue);
				return;
			case ProjectDescriptionPackage.MODULE_FILTER_SPECIFIER__SOURCE_PATH:
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
			case ProjectDescriptionPackage.MODULE_FILTER_SPECIFIER__MODULE_SPECIFIER_WITH_WILDCARD:
				setModuleSpecifierWithWildcard(MODULE_SPECIFIER_WITH_WILDCARD_EDEFAULT);
				return;
			case ProjectDescriptionPackage.MODULE_FILTER_SPECIFIER__SOURCE_PATH:
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
			case ProjectDescriptionPackage.MODULE_FILTER_SPECIFIER__MODULE_SPECIFIER_WITH_WILDCARD:
				return MODULE_SPECIFIER_WITH_WILDCARD_EDEFAULT == null ? moduleSpecifierWithWildcard != null : !MODULE_SPECIFIER_WITH_WILDCARD_EDEFAULT.equals(moduleSpecifierWithWildcard);
			case ProjectDescriptionPackage.MODULE_FILTER_SPECIFIER__SOURCE_PATH:
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

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (moduleSpecifierWithWildcard: ");
		result.append(moduleSpecifierWithWildcard);
		result.append(", sourcePath: ");
		result.append(sourcePath);
		result.append(')');
		return result.toString();
	}

} //ModuleFilterSpecifierImpl
