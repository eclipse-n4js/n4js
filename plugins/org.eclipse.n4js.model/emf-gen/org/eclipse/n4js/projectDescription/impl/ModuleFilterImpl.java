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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.projectDescription.ModuleFilter;
import org.eclipse.n4js.projectDescription.ModuleFilterSpecifier;
import org.eclipse.n4js.projectDescription.ModuleFilterType;
import org.eclipse.n4js.projectDescription.ProjectDescriptionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Module Filter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ModuleFilterImpl#getModuleFilterType <em>Module Filter Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ModuleFilterImpl#getModuleSpecifiers <em>Module Specifiers</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ModuleFilterImpl extends MinimalEObjectImpl.Container implements ModuleFilter {
	/**
	 * The default value of the '{@link #getModuleFilterType() <em>Module Filter Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleFilterType()
	 * @generated
	 * @ordered
	 */
	protected static final ModuleFilterType MODULE_FILTER_TYPE_EDEFAULT = ModuleFilterType.NO_VALIDATE;

	/**
	 * The cached value of the '{@link #getModuleFilterType() <em>Module Filter Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleFilterType()
	 * @generated
	 * @ordered
	 */
	protected ModuleFilterType moduleFilterType = MODULE_FILTER_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getModuleSpecifiers() <em>Module Specifiers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleSpecifiers()
	 * @generated
	 * @ordered
	 */
	protected EList<ModuleFilterSpecifier> moduleSpecifiers;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModuleFilterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProjectDescriptionPackage.Literals.MODULE_FILTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ModuleFilterType getModuleFilterType() {
		return moduleFilterType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModuleFilterType(ModuleFilterType newModuleFilterType) {
		ModuleFilterType oldModuleFilterType = moduleFilterType;
		moduleFilterType = newModuleFilterType == null ? MODULE_FILTER_TYPE_EDEFAULT : newModuleFilterType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.MODULE_FILTER__MODULE_FILTER_TYPE, oldModuleFilterType, moduleFilterType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ModuleFilterSpecifier> getModuleSpecifiers() {
		if (moduleSpecifiers == null) {
			moduleSpecifiers = new EObjectContainmentEList<ModuleFilterSpecifier>(ModuleFilterSpecifier.class, this, ProjectDescriptionPackage.MODULE_FILTER__MODULE_SPECIFIERS);
		}
		return moduleSpecifiers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProjectDescriptionPackage.MODULE_FILTER__MODULE_SPECIFIERS:
				return ((InternalEList<?>)getModuleSpecifiers()).basicRemove(otherEnd, msgs);
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
			case ProjectDescriptionPackage.MODULE_FILTER__MODULE_FILTER_TYPE:
				return getModuleFilterType();
			case ProjectDescriptionPackage.MODULE_FILTER__MODULE_SPECIFIERS:
				return getModuleSpecifiers();
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
			case ProjectDescriptionPackage.MODULE_FILTER__MODULE_FILTER_TYPE:
				setModuleFilterType((ModuleFilterType)newValue);
				return;
			case ProjectDescriptionPackage.MODULE_FILTER__MODULE_SPECIFIERS:
				getModuleSpecifiers().clear();
				getModuleSpecifiers().addAll((Collection<? extends ModuleFilterSpecifier>)newValue);
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
			case ProjectDescriptionPackage.MODULE_FILTER__MODULE_FILTER_TYPE:
				setModuleFilterType(MODULE_FILTER_TYPE_EDEFAULT);
				return;
			case ProjectDescriptionPackage.MODULE_FILTER__MODULE_SPECIFIERS:
				getModuleSpecifiers().clear();
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
			case ProjectDescriptionPackage.MODULE_FILTER__MODULE_FILTER_TYPE:
				return moduleFilterType != MODULE_FILTER_TYPE_EDEFAULT;
			case ProjectDescriptionPackage.MODULE_FILTER__MODULE_SPECIFIERS:
				return moduleSpecifiers != null && !moduleSpecifiers.isEmpty();
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
		result.append(" (moduleFilterType: ");
		result.append(moduleFilterType);
		result.append(')');
		return result.toString();
	}

} //ModuleFilterImpl
