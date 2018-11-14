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
package org.eclipse.n4js.n4JS.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.N4JSPackage;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Import Specifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ImportSpecifierImpl#isFlaggedUsedInCode <em>Flagged Used In Code</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ImportSpecifierImpl extends ProxyResolvingEObjectImpl implements ImportSpecifier {
	/**
	 * The default value of the '{@link #isFlaggedUsedInCode() <em>Flagged Used In Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFlaggedUsedInCode()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FLAGGED_USED_IN_CODE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFlaggedUsedInCode() <em>Flagged Used In Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFlaggedUsedInCode()
	 * @generated
	 * @ordered
	 */
	protected boolean flaggedUsedInCode = FLAGGED_USED_IN_CODE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImportSpecifierImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.IMPORT_SPECIFIER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isFlaggedUsedInCode() {
		return flaggedUsedInCode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFlaggedUsedInCode(boolean newFlaggedUsedInCode) {
		boolean oldFlaggedUsedInCode = flaggedUsedInCode;
		flaggedUsedInCode = newFlaggedUsedInCode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.IMPORT_SPECIFIER__FLAGGED_USED_IN_CODE, oldFlaggedUsedInCode, flaggedUsedInCode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case N4JSPackage.IMPORT_SPECIFIER__FLAGGED_USED_IN_CODE:
				return isFlaggedUsedInCode();
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
			case N4JSPackage.IMPORT_SPECIFIER__FLAGGED_USED_IN_CODE:
				setFlaggedUsedInCode((Boolean)newValue);
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
			case N4JSPackage.IMPORT_SPECIFIER__FLAGGED_USED_IN_CODE:
				setFlaggedUsedInCode(FLAGGED_USED_IN_CODE_EDEFAULT);
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
			case N4JSPackage.IMPORT_SPECIFIER__FLAGGED_USED_IN_CODE:
				return flaggedUsedInCode != FLAGGED_USED_IN_CODE_EDEFAULT;
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
		result.append(" (flaggedUsedInCode: ");
		result.append(flaggedUsedInCode);
		result.append(')');
		return result.toString();
	}

} //ImportSpecifierImpl
