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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.types.ElementExportDefinition;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element Export Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.ElementExportDefinitionImpl#getDeclaredExportedName <em>Declared Exported Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.ElementExportDefinitionImpl#getExportedElement <em>Exported Element</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ElementExportDefinitionImpl extends ProxyResolvingEObjectImpl implements ElementExportDefinition {
	/**
	 * The default value of the '{@link #getDeclaredExportedName() <em>Declared Exported Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredExportedName()
	 * @generated
	 * @ordered
	 */
	protected static final String DECLARED_EXPORTED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDeclaredExportedName() <em>Declared Exported Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredExportedName()
	 * @generated
	 * @ordered
	 */
	protected String declaredExportedName = DECLARED_EXPORTED_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExportedElement() <em>Exported Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportedElement()
	 * @generated
	 * @ordered
	 */
	protected TExportableElement exportedElement;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ElementExportDefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.ELEMENT_EXPORT_DEFINITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDeclaredExportedName() {
		return declaredExportedName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredExportedName(String newDeclaredExportedName) {
		String oldDeclaredExportedName = declaredExportedName;
		declaredExportedName = newDeclaredExportedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ELEMENT_EXPORT_DEFINITION__DECLARED_EXPORTED_NAME, oldDeclaredExportedName, declaredExportedName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TExportableElement getExportedElement() {
		if (exportedElement != null && exportedElement.eIsProxy()) {
			InternalEObject oldExportedElement = (InternalEObject)exportedElement;
			exportedElement = (TExportableElement)eResolveProxy(oldExportedElement);
			if (exportedElement != oldExportedElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.ELEMENT_EXPORT_DEFINITION__EXPORTED_ELEMENT, oldExportedElement, exportedElement));
			}
		}
		return exportedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TExportableElement basicGetExportedElement() {
		return exportedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExportedElement(TExportableElement newExportedElement) {
		TExportableElement oldExportedElement = exportedElement;
		exportedElement = newExportedElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ELEMENT_EXPORT_DEFINITION__EXPORTED_ELEMENT, oldExportedElement, exportedElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getExportedName() {
		String _elvis = null;
		String _declaredExportedName = this.getDeclaredExportedName();
		if (_declaredExportedName != null) {
			_elvis = _declaredExportedName;
		} else {
			TExportableElement _exportedElement = this.getExportedElement();
			String _name = null;
			if (_exportedElement!=null) {
				_name=_exportedElement.getName();
			}
			_elvis = _name;
		}
		return _elvis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.ELEMENT_EXPORT_DEFINITION__DECLARED_EXPORTED_NAME:
				return getDeclaredExportedName();
			case TypesPackage.ELEMENT_EXPORT_DEFINITION__EXPORTED_ELEMENT:
				if (resolve) return getExportedElement();
				return basicGetExportedElement();
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
			case TypesPackage.ELEMENT_EXPORT_DEFINITION__DECLARED_EXPORTED_NAME:
				setDeclaredExportedName((String)newValue);
				return;
			case TypesPackage.ELEMENT_EXPORT_DEFINITION__EXPORTED_ELEMENT:
				setExportedElement((TExportableElement)newValue);
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
			case TypesPackage.ELEMENT_EXPORT_DEFINITION__DECLARED_EXPORTED_NAME:
				setDeclaredExportedName(DECLARED_EXPORTED_NAME_EDEFAULT);
				return;
			case TypesPackage.ELEMENT_EXPORT_DEFINITION__EXPORTED_ELEMENT:
				setExportedElement((TExportableElement)null);
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
			case TypesPackage.ELEMENT_EXPORT_DEFINITION__DECLARED_EXPORTED_NAME:
				return DECLARED_EXPORTED_NAME_EDEFAULT == null ? declaredExportedName != null : !DECLARED_EXPORTED_NAME_EDEFAULT.equals(declaredExportedName);
			case TypesPackage.ELEMENT_EXPORT_DEFINITION__EXPORTED_ELEMENT:
				return exportedElement != null;
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
			case TypesPackage.ELEMENT_EXPORT_DEFINITION___GET_EXPORTED_NAME:
				return getExportedName();
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
		result.append(" (declaredExportedName: ");
		result.append(declaredExportedName);
		result.append(')');
		return result.toString();
	}

} //ElementExportDefinitionImpl
