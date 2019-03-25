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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

import org.eclipse.n4js.projectDescription.ProjectDescriptionPackage;
import org.eclipse.n4js.projectDescription.SourceContainerDescription;
import org.eclipse.n4js.projectDescription.SourceContainerType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Source Container Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.SourceContainerDescriptionImpl#getSourceContainerType <em>Source Container Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.SourceContainerDescriptionImpl#getPaths <em>Paths</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SourceContainerDescriptionImpl extends MinimalEObjectImpl.Container implements SourceContainerDescription {
	/**
	 * The default value of the '{@link #getSourceContainerType() <em>Source Container Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceContainerType()
	 * @generated
	 * @ordered
	 */
	protected static final SourceContainerType SOURCE_CONTAINER_TYPE_EDEFAULT = SourceContainerType.SOURCE;

	/**
	 * The cached value of the '{@link #getSourceContainerType() <em>Source Container Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceContainerType()
	 * @generated
	 * @ordered
	 */
	protected SourceContainerType sourceContainerType = SOURCE_CONTAINER_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPaths() <em>Paths</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPaths()
	 * @generated
	 * @ordered
	 */
	protected EList<String> paths;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SourceContainerDescriptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProjectDescriptionPackage.Literals.SOURCE_CONTAINER_DESCRIPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SourceContainerType getSourceContainerType() {
		return sourceContainerType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceContainerType(SourceContainerType newSourceContainerType) {
		SourceContainerType oldSourceContainerType = sourceContainerType;
		sourceContainerType = newSourceContainerType == null ? SOURCE_CONTAINER_TYPE_EDEFAULT : newSourceContainerType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.SOURCE_CONTAINER_DESCRIPTION__SOURCE_CONTAINER_TYPE, oldSourceContainerType, sourceContainerType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getPaths() {
		if (paths == null) {
			paths = new EDataTypeEList<String>(String.class, this, ProjectDescriptionPackage.SOURCE_CONTAINER_DESCRIPTION__PATHS);
		}
		return paths;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProjectDescriptionPackage.SOURCE_CONTAINER_DESCRIPTION__SOURCE_CONTAINER_TYPE:
				return getSourceContainerType();
			case ProjectDescriptionPackage.SOURCE_CONTAINER_DESCRIPTION__PATHS:
				return getPaths();
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
			case ProjectDescriptionPackage.SOURCE_CONTAINER_DESCRIPTION__SOURCE_CONTAINER_TYPE:
				setSourceContainerType((SourceContainerType)newValue);
				return;
			case ProjectDescriptionPackage.SOURCE_CONTAINER_DESCRIPTION__PATHS:
				getPaths().clear();
				getPaths().addAll((Collection<? extends String>)newValue);
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
			case ProjectDescriptionPackage.SOURCE_CONTAINER_DESCRIPTION__SOURCE_CONTAINER_TYPE:
				setSourceContainerType(SOURCE_CONTAINER_TYPE_EDEFAULT);
				return;
			case ProjectDescriptionPackage.SOURCE_CONTAINER_DESCRIPTION__PATHS:
				getPaths().clear();
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
			case ProjectDescriptionPackage.SOURCE_CONTAINER_DESCRIPTION__SOURCE_CONTAINER_TYPE:
				return sourceContainerType != SOURCE_CONTAINER_TYPE_EDEFAULT;
			case ProjectDescriptionPackage.SOURCE_CONTAINER_DESCRIPTION__PATHS:
				return paths != null && !paths.isEmpty();
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
		result.append(" (sourceContainerType: ");
		result.append(sourceContainerType);
		result.append(", paths: ");
		result.append(paths);
		result.append(')');
		return result.toString();
	}

} //SourceContainerDescriptionImpl
