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
package org.eclipse.n4js.transpiler.im.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.transpiler.im.ImPackage;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryInternal;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Symbol Table Entry Internal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryInternalImpl#getImportSpecifier <em>Import Specifier</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SymbolTableEntryInternalImpl extends SymbolTableEntryImpl implements SymbolTableEntryInternal {
	/**
	 * The cached value of the '{@link #getImportSpecifier() <em>Import Specifier</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportSpecifier()
	 * @generated
	 * @ordered
	 */
	protected NamespaceImportSpecifier importSpecifier;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SymbolTableEntryInternalImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImPackage.Literals.SYMBOL_TABLE_ENTRY_INTERNAL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamespaceImportSpecifier getImportSpecifier() {
		if (importSpecifier != null && importSpecifier.eIsProxy()) {
			InternalEObject oldImportSpecifier = (InternalEObject)importSpecifier;
			importSpecifier = (NamespaceImportSpecifier)eResolveProxy(oldImportSpecifier);
			if (importSpecifier != oldImportSpecifier) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ImPackage.SYMBOL_TABLE_ENTRY_INTERNAL__IMPORT_SPECIFIER, oldImportSpecifier, importSpecifier));
			}
		}
		return importSpecifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamespaceImportSpecifier basicGetImportSpecifier() {
		return importSpecifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportSpecifier(NamespaceImportSpecifier newImportSpecifier) {
		NamespaceImportSpecifier oldImportSpecifier = importSpecifier;
		importSpecifier = newImportSpecifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.SYMBOL_TABLE_ENTRY_INTERNAL__IMPORT_SPECIFIER, oldImportSpecifier, importSpecifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImPackage.SYMBOL_TABLE_ENTRY_INTERNAL__IMPORT_SPECIFIER:
				if (resolve) return getImportSpecifier();
				return basicGetImportSpecifier();
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
			case ImPackage.SYMBOL_TABLE_ENTRY_INTERNAL__IMPORT_SPECIFIER:
				setImportSpecifier((NamespaceImportSpecifier)newValue);
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
			case ImPackage.SYMBOL_TABLE_ENTRY_INTERNAL__IMPORT_SPECIFIER:
				setImportSpecifier((NamespaceImportSpecifier)null);
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
			case ImPackage.SYMBOL_TABLE_ENTRY_INTERNAL__IMPORT_SPECIFIER:
				return importSpecifier != null;
		}
		return super.eIsSet(featureID);
	}

} //SymbolTableEntryInternalImpl
