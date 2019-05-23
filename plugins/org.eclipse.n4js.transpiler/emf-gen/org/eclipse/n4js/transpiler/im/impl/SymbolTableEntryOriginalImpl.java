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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.transpiler.im.ImPackage;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TExportableElement;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Symbol Table Entry Original</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryOriginalImpl#getOriginalTarget <em>Original Target</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryOriginalImpl#getImportSpecifier <em>Import Specifier</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SymbolTableEntryOriginalImpl extends SymbolTableEntryImpl implements SymbolTableEntryOriginal {
	/**
	 * The cached value of the '{@link #getOriginalTarget() <em>Original Target</em>}' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getOriginalTarget()
	 * @generated
	 * @ordered
	 */
	protected IdentifiableElement originalTarget;

	/**
	 * The cached value of the '{@link #getImportSpecifier() <em>Import Specifier</em>}' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getImportSpecifier()
	 * @generated
	 * @ordered
	 */
	protected ImportSpecifier importSpecifier;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected SymbolTableEntryOriginalImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImPackage.Literals.SYMBOL_TABLE_ENTRY_ORIGINAL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IdentifiableElement getOriginalTarget() {
		if (originalTarget != null && originalTarget.eIsProxy()) {
			InternalEObject oldOriginalTarget = (InternalEObject)originalTarget;
			originalTarget = (IdentifiableElement)eResolveProxy(oldOriginalTarget);
			if (originalTarget != oldOriginalTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ImPackage.SYMBOL_TABLE_ENTRY_ORIGINAL__ORIGINAL_TARGET, oldOriginalTarget, originalTarget));
			}
		}
		return originalTarget;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public IdentifiableElement basicGetOriginalTarget() {
		return originalTarget;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOriginalTarget(IdentifiableElement newOriginalTarget) {
		IdentifiableElement oldOriginalTarget = originalTarget;
		originalTarget = newOriginalTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.SYMBOL_TABLE_ENTRY_ORIGINAL__ORIGINAL_TARGET, oldOriginalTarget, originalTarget));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ImportSpecifier getImportSpecifier() {
		if (importSpecifier != null && importSpecifier.eIsProxy()) {
			InternalEObject oldImportSpecifier = (InternalEObject)importSpecifier;
			importSpecifier = (ImportSpecifier)eResolveProxy(oldImportSpecifier);
			if (importSpecifier != oldImportSpecifier) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ImPackage.SYMBOL_TABLE_ENTRY_ORIGINAL__IMPORT_SPECIFIER, oldImportSpecifier, importSpecifier));
			}
		}
		return importSpecifier;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ImportSpecifier basicGetImportSpecifier() {
		return importSpecifier;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setImportSpecifier(ImportSpecifier newImportSpecifier) {
		ImportSpecifier oldImportSpecifier = importSpecifier;
		importSpecifier = newImportSpecifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.SYMBOL_TABLE_ENTRY_ORIGINAL__IMPORT_SPECIFIER, oldImportSpecifier, importSpecifier));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getExportedName() {
		final IdentifiableElement trgt = this.getOriginalTarget();
		if ((trgt instanceof TExportableElement)) {
			return ((TExportableElement)trgt).getExportedName();
		}
		return this.getOriginalTarget().getName();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImPackage.SYMBOL_TABLE_ENTRY_ORIGINAL__ORIGINAL_TARGET:
				if (resolve) return getOriginalTarget();
				return basicGetOriginalTarget();
			case ImPackage.SYMBOL_TABLE_ENTRY_ORIGINAL__IMPORT_SPECIFIER:
				if (resolve) return getImportSpecifier();
				return basicGetImportSpecifier();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ImPackage.SYMBOL_TABLE_ENTRY_ORIGINAL__ORIGINAL_TARGET:
				setOriginalTarget((IdentifiableElement)newValue);
				return;
			case ImPackage.SYMBOL_TABLE_ENTRY_ORIGINAL__IMPORT_SPECIFIER:
				setImportSpecifier((ImportSpecifier)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ImPackage.SYMBOL_TABLE_ENTRY_ORIGINAL__ORIGINAL_TARGET:
				setOriginalTarget((IdentifiableElement)null);
				return;
			case ImPackage.SYMBOL_TABLE_ENTRY_ORIGINAL__IMPORT_SPECIFIER:
				setImportSpecifier((ImportSpecifier)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ImPackage.SYMBOL_TABLE_ENTRY_ORIGINAL__ORIGINAL_TARGET:
				return originalTarget != null;
			case ImPackage.SYMBOL_TABLE_ENTRY_ORIGINAL__IMPORT_SPECIFIER:
				return importSpecifier != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case ImPackage.SYMBOL_TABLE_ENTRY_ORIGINAL___GET_EXPORTED_NAME:
				return getExportedName();
		}
		return super.eInvoke(operationID, arguments);
	}

} // SymbolTableEntryOriginalImpl
