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
package org.eclipse.n4js.jsdoc.dom.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.jsdoc.dom.DomPackage;
import org.eclipse.n4js.jsdoc.dom.FullTypeReference;
import org.eclipse.n4js.jsdoc.dom.JSDocNode;
import org.eclipse.n4js.jsdoc.dom.SimpleTypeReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Full Type Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.impl.FullTypeReferenceImpl#getModuleName <em>Module Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FullTypeReferenceImpl extends SimpleTypeReferenceImpl implements FullTypeReference {
	/**
	 * The default value of the '{@link #getModuleName() <em>Module Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleName()
	 * @generated
	 * @ordered
	 */
	protected static final String MODULE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModuleName() <em>Module Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleName()
	 * @generated
	 * @ordered
	 */
	protected String moduleName = MODULE_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FullTypeReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomPackage.Literals.FULL_TYPE_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModuleName(String newModuleName) {
		String oldModuleName = moduleName;
		moduleName = newModuleName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomPackage.FULL_TYPE_REFERENCE__MODULE_NAME, oldModuleName, moduleName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean moduleNameSet() {
		return ((this.getModuleName() != null) && (!this.getModuleName().isEmpty()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder();
		boolean _moduleNameSet = this.moduleNameSet();
		if (_moduleNameSet) {
			strb.append(this.getModuleName());
		}
		boolean _typeNameSet = this.typeNameSet();
		if (_typeNameSet) {
			int _length = strb.length();
			boolean _greaterThan = (_length > 0);
			if (_greaterThan) {
				strb.append(".");
			}
			strb.append(this.getTypeName());
		}
		return strb.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String fullTypeName() {
		StringBuilder strb = new StringBuilder();
		boolean _moduleNameSet = this.moduleNameSet();
		if (_moduleNameSet) {
			strb.append(this.getModuleName());
		}
		boolean _typeNameSet = this.typeNameSet();
		if (_typeNameSet) {
			strb.append(".");
			strb.append(this.getTypeName());
		}
		return strb.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DomPackage.FULL_TYPE_REFERENCE__MODULE_NAME:
				return getModuleName();
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
			case DomPackage.FULL_TYPE_REFERENCE__MODULE_NAME:
				setModuleName((String)newValue);
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
			case DomPackage.FULL_TYPE_REFERENCE__MODULE_NAME:
				setModuleName(MODULE_NAME_EDEFAULT);
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
			case DomPackage.FULL_TYPE_REFERENCE__MODULE_NAME:
				return MODULE_NAME_EDEFAULT == null ? moduleName != null : !MODULE_NAME_EDEFAULT.equals(moduleName);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == JSDocNode.class) {
			switch (baseOperationID) {
				case DomPackage.JS_DOC_NODE___TO_STRING: return DomPackage.FULL_TYPE_REFERENCE___TO_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == SimpleTypeReference.class) {
			switch (baseOperationID) {
				case DomPackage.SIMPLE_TYPE_REFERENCE___TO_STRING: return DomPackage.FULL_TYPE_REFERENCE___TO_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case DomPackage.FULL_TYPE_REFERENCE___MODULE_NAME_SET:
				return moduleNameSet();
			case DomPackage.FULL_TYPE_REFERENCE___TO_STRING:
				return toString();
			case DomPackage.FULL_TYPE_REFERENCE___FULL_TYPE_NAME:
				return fullTypeName();
		}
		return super.eInvoke(operationID, arguments);
	}

} //FullTypeReferenceImpl
