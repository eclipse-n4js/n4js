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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.n4mf.ProjectDependencyScope;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectReferenceImpl#getProjectId <em>Project Id</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectReferenceImpl#getDeclaredVendorId <em>Declared Vendor Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProjectReferenceImpl extends MinimalEObjectImpl.Container implements ProjectReference {
	/**
	 * The default value of the '{@link #getProjectId() <em>Project Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectId()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProjectId() <em>Project Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectId()
	 * @generated
	 * @ordered
	 */
	protected String projectId = PROJECT_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getDeclaredVendorId() <em>Declared Vendor Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredVendorId()
	 * @generated
	 * @ordered
	 */
	protected static final String DECLARED_VENDOR_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDeclaredVendorId() <em>Declared Vendor Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredVendorId()
	 * @generated
	 * @ordered
	 */
	protected String declaredVendorId = DECLARED_VENDOR_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProjectReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4mfPackage.Literals.PROJECT_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProjectId() {
		return projectId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjectId(String newProjectId) {
		String oldProjectId = projectId;
		projectId = newProjectId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_REFERENCE__PROJECT_ID, oldProjectId, projectId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDeclaredVendorId() {
		return declaredVendorId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeclaredVendorId(String newDeclaredVendorId) {
		String oldDeclaredVendorId = declaredVendorId;
		declaredVendorId = newDeclaredVendorId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_REFERENCE__DECLARED_VENDOR_ID, oldDeclaredVendorId, declaredVendorId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVendorId() {
		String _xifexpression = null;
		String _declaredVendorId = this.getDeclaredVendorId();
		boolean _tripleNotEquals = (_declaredVendorId != null);
		if (_tripleNotEquals) {
			_xifexpression = this.getDeclaredVendorId();
		}
		else {
			EObject _eContainer = this.eContainer();
			String _vendorId = null;
			if (((ProjectDescription) _eContainer)!=null) {
				_vendorId=((ProjectDescription) _eContainer).getVendorId();
			}
			_xifexpression = _vendorId;
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectDependencyScope getScope() {
		return ProjectDependencyScope.COMPILE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case N4mfPackage.PROJECT_REFERENCE__PROJECT_ID:
				return getProjectId();
			case N4mfPackage.PROJECT_REFERENCE__DECLARED_VENDOR_ID:
				return getDeclaredVendorId();
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
			case N4mfPackage.PROJECT_REFERENCE__PROJECT_ID:
				setProjectId((String)newValue);
				return;
			case N4mfPackage.PROJECT_REFERENCE__DECLARED_VENDOR_ID:
				setDeclaredVendorId((String)newValue);
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
			case N4mfPackage.PROJECT_REFERENCE__PROJECT_ID:
				setProjectId(PROJECT_ID_EDEFAULT);
				return;
			case N4mfPackage.PROJECT_REFERENCE__DECLARED_VENDOR_ID:
				setDeclaredVendorId(DECLARED_VENDOR_ID_EDEFAULT);
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
			case N4mfPackage.PROJECT_REFERENCE__PROJECT_ID:
				return PROJECT_ID_EDEFAULT == null ? projectId != null : !PROJECT_ID_EDEFAULT.equals(projectId);
			case N4mfPackage.PROJECT_REFERENCE__DECLARED_VENDOR_ID:
				return DECLARED_VENDOR_ID_EDEFAULT == null ? declaredVendorId != null : !DECLARED_VENDOR_ID_EDEFAULT.equals(declaredVendorId);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case N4mfPackage.PROJECT_REFERENCE___GET_VENDOR_ID:
				return getVendorId();
			case N4mfPackage.PROJECT_REFERENCE___GET_SCOPE:
				return getScope();
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(" (projectId: ");
		result.append(projectId);
		result.append(", declaredVendorId: ");
		result.append(declaredVendorId);
		result.append(')');
		return result.toString();
	}

} //ProjectReferenceImpl
