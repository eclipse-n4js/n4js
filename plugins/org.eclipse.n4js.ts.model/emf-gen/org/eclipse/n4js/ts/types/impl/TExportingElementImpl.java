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

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.ts.types.ExportDefinition;
import org.eclipse.n4js.ts.types.TExportingElement;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TExporting Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TExportingElementImpl#getExportDefinitions <em>Export Definitions</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class TExportingElementImpl extends ProxyResolvingEObjectImpl implements TExportingElement {
	/**
	 * The cached value of the '{@link #getExportDefinitions() <em>Export Definitions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportDefinitions()
	 * @generated
	 * @ordered
	 */
	protected EList<ExportDefinition> exportDefinitions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TExportingElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TEXPORTING_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ExportDefinition> getExportDefinitions() {
		if (exportDefinitions == null) {
			exportDefinitions = new EObjectContainmentEList<ExportDefinition>(ExportDefinition.class, this, TypesPackage.TEXPORTING_ELEMENT__EXPORT_DEFINITIONS);
		}
		return exportDefinitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.TEXPORTING_ELEMENT__EXPORT_DEFINITIONS:
				return ((InternalEList<?>)getExportDefinitions()).basicRemove(otherEnd, msgs);
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
			case TypesPackage.TEXPORTING_ELEMENT__EXPORT_DEFINITIONS:
				return getExportDefinitions();
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
			case TypesPackage.TEXPORTING_ELEMENT__EXPORT_DEFINITIONS:
				getExportDefinitions().clear();
				getExportDefinitions().addAll((Collection<? extends ExportDefinition>)newValue);
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
			case TypesPackage.TEXPORTING_ELEMENT__EXPORT_DEFINITIONS:
				getExportDefinitions().clear();
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
			case TypesPackage.TEXPORTING_ELEMENT__EXPORT_DEFINITIONS:
				return exportDefinitions != null && !exportDefinitions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TExportingElementImpl
