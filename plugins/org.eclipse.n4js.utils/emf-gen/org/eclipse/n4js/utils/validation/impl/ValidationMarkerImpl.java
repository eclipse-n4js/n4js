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
package org.eclipse.n4js.utils.validation.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.n4js.utils.validation.ValidationMarker;
import org.eclipse.n4js.utils.validation.ValidationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Marker</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.utils.validation.impl.ValidationMarkerImpl#getDelegateResource <em>Delegate Resource</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ValidationMarkerImpl extends MinimalEObjectImpl.Container implements ValidationMarker {
	/**
	 * The default value of the '{@link #getDelegateResource() <em>Delegate Resource</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDelegateResource()
	 * @generated
	 * @ordered
	 */
	protected static final Resource DELEGATE_RESOURCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDelegateResource() <em>Delegate Resource</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDelegateResource()
	 * @generated
	 * @ordered
	 */
	protected Resource delegateResource = DELEGATE_RESOURCE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ValidationMarkerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ValidationPackage.Literals.VALIDATION_MARKER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Resource getDelegateResource() {
		return delegateResource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDelegateResource(Resource newDelegateResource) {
		Resource oldDelegateResource = delegateResource;
		delegateResource = newDelegateResource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.VALIDATION_MARKER__DELEGATE_RESOURCE, oldDelegateResource, delegateResource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Resource eResource() {
		return this.getDelegateResource();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ValidationPackage.VALIDATION_MARKER__DELEGATE_RESOURCE:
				return getDelegateResource();
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
			case ValidationPackage.VALIDATION_MARKER__DELEGATE_RESOURCE:
				setDelegateResource((Resource)newValue);
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
			case ValidationPackage.VALIDATION_MARKER__DELEGATE_RESOURCE:
				setDelegateResource(DELEGATE_RESOURCE_EDEFAULT);
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
			case ValidationPackage.VALIDATION_MARKER__DELEGATE_RESOURCE:
				return DELEGATE_RESOURCE_EDEFAULT == null ? delegateResource != null : !DELEGATE_RESOURCE_EDEFAULT.equals(delegateResource);
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
			case ValidationPackage.VALIDATION_MARKER___ERESOURCE:
				return eResource();
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

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (delegateResource: ");
		result.append(delegateResource);
		result.append(')');
		return result.toString();
	}

} //ValidationMarkerImpl
