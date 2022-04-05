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

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.n4js.utils.UtilN4;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TExportable Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TExportableElementImpl#isDirectlyExported <em>Directly Exported</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TExportableElementImpl#isDirectlyExportedAsDefault <em>Directly Exported As Default</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TExportableElementImpl extends IdentifiableElementImpl implements TExportableElement {
	/**
	 * The default value of the '{@link #isDirectlyExported() <em>Directly Exported</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDirectlyExported()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DIRECTLY_EXPORTED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDirectlyExported() <em>Directly Exported</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDirectlyExported()
	 * @generated
	 * @ordered
	 */
	protected boolean directlyExported = DIRECTLY_EXPORTED_EDEFAULT;

	/**
	 * The default value of the '{@link #isDirectlyExportedAsDefault() <em>Directly Exported As Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDirectlyExportedAsDefault()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DIRECTLY_EXPORTED_AS_DEFAULT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDirectlyExportedAsDefault() <em>Directly Exported As Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDirectlyExportedAsDefault()
	 * @generated
	 * @ordered
	 */
	protected boolean directlyExportedAsDefault = DIRECTLY_EXPORTED_AS_DEFAULT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TExportableElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TEXPORTABLE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDirectlyExported() {
		return directlyExported;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDirectlyExported(boolean newDirectlyExported) {
		boolean oldDirectlyExported = directlyExported;
		directlyExported = newDirectlyExported;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TEXPORTABLE_ELEMENT__DIRECTLY_EXPORTED, oldDirectlyExported, directlyExported));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDirectlyExportedAsDefault() {
		return directlyExportedAsDefault;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDirectlyExportedAsDefault(boolean newDirectlyExportedAsDefault) {
		boolean oldDirectlyExportedAsDefault = directlyExportedAsDefault;
		directlyExportedAsDefault = newDirectlyExportedAsDefault;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TEXPORTABLE_ELEMENT__DIRECTLY_EXPORTED_AS_DEFAULT, oldDirectlyExportedAsDefault, directlyExportedAsDefault));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDirectlyExportedName() {
		boolean _isDirectlyExported = this.isDirectlyExported();
		if (_isDirectlyExported) {
			boolean _isDirectlyExportedAsDefault = this.isDirectlyExportedAsDefault();
			if (_isDirectlyExportedAsDefault) {
				return UtilN4.EXPORT_DEFAULT_NAME;
			}
			return this.getName();
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.TEXPORTABLE_ELEMENT__DIRECTLY_EXPORTED:
				return isDirectlyExported();
			case TypesPackage.TEXPORTABLE_ELEMENT__DIRECTLY_EXPORTED_AS_DEFAULT:
				return isDirectlyExportedAsDefault();
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
			case TypesPackage.TEXPORTABLE_ELEMENT__DIRECTLY_EXPORTED:
				setDirectlyExported((Boolean)newValue);
				return;
			case TypesPackage.TEXPORTABLE_ELEMENT__DIRECTLY_EXPORTED_AS_DEFAULT:
				setDirectlyExportedAsDefault((Boolean)newValue);
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
			case TypesPackage.TEXPORTABLE_ELEMENT__DIRECTLY_EXPORTED:
				setDirectlyExported(DIRECTLY_EXPORTED_EDEFAULT);
				return;
			case TypesPackage.TEXPORTABLE_ELEMENT__DIRECTLY_EXPORTED_AS_DEFAULT:
				setDirectlyExportedAsDefault(DIRECTLY_EXPORTED_AS_DEFAULT_EDEFAULT);
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
			case TypesPackage.TEXPORTABLE_ELEMENT__DIRECTLY_EXPORTED:
				return directlyExported != DIRECTLY_EXPORTED_EDEFAULT;
			case TypesPackage.TEXPORTABLE_ELEMENT__DIRECTLY_EXPORTED_AS_DEFAULT:
				return directlyExportedAsDefault != DIRECTLY_EXPORTED_AS_DEFAULT_EDEFAULT;
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
			case TypesPackage.TEXPORTABLE_ELEMENT___GET_DIRECTLY_EXPORTED_NAME:
				return getDirectlyExportedName();
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
		result.append(" (directlyExported: ");
		result.append(directlyExported);
		result.append(", directlyExportedAsDefault: ");
		result.append(directlyExportedAsDefault);
		result.append(')');
		return result.toString();
	}

} //TExportableElementImpl
