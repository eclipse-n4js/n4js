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

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.n4mf.ProjectDependencies;
import org.eclipse.n4js.n4mf.ProjectDependency;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project Dependencies</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDependenciesImpl#getProjectDependencies <em>Project Dependencies</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProjectDependenciesImpl extends MinimalEObjectImpl.Container implements ProjectDependencies {
	/**
	 * The cached value of the '{@link #getProjectDependencies() <em>Project Dependencies</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<ProjectDependency> projectDependencies;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProjectDependenciesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4mfPackage.Literals.PROJECT_DEPENDENCIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProjectDependency> getProjectDependencies() {
		if (projectDependencies == null) {
			projectDependencies = new EObjectContainmentEList<ProjectDependency>(ProjectDependency.class, this, N4mfPackage.PROJECT_DEPENDENCIES__PROJECT_DEPENDENCIES);
		}
		return projectDependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4mfPackage.PROJECT_DEPENDENCIES__PROJECT_DEPENDENCIES:
				return ((InternalEList<?>)getProjectDependencies()).basicRemove(otherEnd, msgs);
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
			case N4mfPackage.PROJECT_DEPENDENCIES__PROJECT_DEPENDENCIES:
				return getProjectDependencies();
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
			case N4mfPackage.PROJECT_DEPENDENCIES__PROJECT_DEPENDENCIES:
				getProjectDependencies().clear();
				getProjectDependencies().addAll((Collection<? extends ProjectDependency>)newValue);
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
			case N4mfPackage.PROJECT_DEPENDENCIES__PROJECT_DEPENDENCIES:
				getProjectDependencies().clear();
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
			case N4mfPackage.PROJECT_DEPENDENCIES__PROJECT_DEPENDENCIES:
				return projectDependencies != null && !projectDependencies.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProjectDependenciesImpl
