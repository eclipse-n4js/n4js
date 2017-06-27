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
import org.eclipse.n4js.n4mf.TestedProject;
import org.eclipse.n4js.n4mf.TestedProjects;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tested Projects</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.TestedProjectsImpl#getTestedProjects <em>Tested Projects</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TestedProjectsImpl extends MinimalEObjectImpl.Container implements TestedProjects {
	/**
	 * The cached value of the '{@link #getTestedProjects() <em>Tested Projects</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTestedProjects()
	 * @generated
	 * @ordered
	 */
	protected EList<TestedProject> testedProjects;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TestedProjectsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4mfPackage.Literals.TESTED_PROJECTS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TestedProject> getTestedProjects() {
		if (testedProjects == null) {
			testedProjects = new EObjectContainmentEList<TestedProject>(TestedProject.class, this, N4mfPackage.TESTED_PROJECTS__TESTED_PROJECTS);
		}
		return testedProjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4mfPackage.TESTED_PROJECTS__TESTED_PROJECTS:
				return ((InternalEList<?>)getTestedProjects()).basicRemove(otherEnd, msgs);
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
			case N4mfPackage.TESTED_PROJECTS__TESTED_PROJECTS:
				return getTestedProjects();
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
			case N4mfPackage.TESTED_PROJECTS__TESTED_PROJECTS:
				getTestedProjects().clear();
				getTestedProjects().addAll((Collection<? extends TestedProject>)newValue);
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
			case N4mfPackage.TESTED_PROJECTS__TESTED_PROJECTS:
				getTestedProjects().clear();
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
			case N4mfPackage.TESTED_PROJECTS__TESTED_PROJECTS:
				return testedProjects != null && !testedProjects.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TestedProjectsImpl
