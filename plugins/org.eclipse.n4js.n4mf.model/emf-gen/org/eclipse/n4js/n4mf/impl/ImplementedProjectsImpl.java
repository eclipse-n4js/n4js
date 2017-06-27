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

import org.eclipse.n4js.n4mf.ImplementedProjects;
import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.n4mf.ProjectReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Implemented Projects</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ImplementedProjectsImpl#getImplementedProjects <em>Implemented Projects</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ImplementedProjectsImpl extends MinimalEObjectImpl.Container implements ImplementedProjects {
	/**
	 * The cached value of the '{@link #getImplementedProjects() <em>Implemented Projects</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementedProjects()
	 * @generated
	 * @ordered
	 */
	protected EList<ProjectReference> implementedProjects;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImplementedProjectsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4mfPackage.Literals.IMPLEMENTED_PROJECTS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProjectReference> getImplementedProjects() {
		if (implementedProjects == null) {
			implementedProjects = new EObjectContainmentEList<ProjectReference>(ProjectReference.class, this, N4mfPackage.IMPLEMENTED_PROJECTS__IMPLEMENTED_PROJECTS);
		}
		return implementedProjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4mfPackage.IMPLEMENTED_PROJECTS__IMPLEMENTED_PROJECTS:
				return ((InternalEList<?>)getImplementedProjects()).basicRemove(otherEnd, msgs);
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
			case N4mfPackage.IMPLEMENTED_PROJECTS__IMPLEMENTED_PROJECTS:
				return getImplementedProjects();
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
			case N4mfPackage.IMPLEMENTED_PROJECTS__IMPLEMENTED_PROJECTS:
				getImplementedProjects().clear();
				getImplementedProjects().addAll((Collection<? extends ProjectReference>)newValue);
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
			case N4mfPackage.IMPLEMENTED_PROJECTS__IMPLEMENTED_PROJECTS:
				getImplementedProjects().clear();
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
			case N4mfPackage.IMPLEMENTED_PROJECTS__IMPLEMENTED_PROJECTS:
				return implementedProjects != null && !implementedProjects.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ImplementedProjectsImpl
