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

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.n4js.n4JS.impl.NamedImportSpecifierImpl;

import org.eclipse.n4js.transpiler.im.ImPackage;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.transpiler.im.VersionedNamedImportSpecifier_IM;

import org.eclipse.n4js.ts.versions.VersionableUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Versioned Named Import Specifier IM</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.VersionedNamedImportSpecifier_IMImpl#getImportedTypeVersions <em>Imported Type Versions</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.VersionedNamedImportSpecifier_IMImpl#isVersionedTypeImport <em>Versioned Type Import</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VersionedNamedImportSpecifier_IMImpl extends NamedImportSpecifierImpl implements VersionedNamedImportSpecifier_IM {
	/**
	 * The cached value of the '{@link #getImportedTypeVersions() <em>Imported Type Versions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportedTypeVersions()
	 * @generated
	 * @ordered
	 */
	protected EList<SymbolTableEntryOriginal> importedTypeVersions;

	/**
	 * The default value of the '{@link #isVersionedTypeImport() <em>Versioned Type Import</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVersionedTypeImport()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VERSIONED_TYPE_IMPORT_EDEFAULT = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VersionedNamedImportSpecifier_IMImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImPackage.Literals.VERSIONED_NAMED_IMPORT_SPECIFIER_IM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SymbolTableEntryOriginal> getImportedTypeVersions() {
		if (importedTypeVersions == null) {
			importedTypeVersions = new EObjectResolvingEList<SymbolTableEntryOriginal>(SymbolTableEntryOriginal.class, this, ImPackage.VERSIONED_NAMED_IMPORT_SPECIFIER_IM__IMPORTED_TYPE_VERSIONS);
		}
		return importedTypeVersions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isVersionedTypeImport() {
		return ((this.getImportedTypeVersions().size() > 0) && ((this.getImportedTypeVersions().size() > 1) || 
			VersionableUtils.isTVersionable(this.getImportedTypeVersions().get(0).getOriginalTarget())));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImPackage.VERSIONED_NAMED_IMPORT_SPECIFIER_IM__IMPORTED_TYPE_VERSIONS:
				return getImportedTypeVersions();
			case ImPackage.VERSIONED_NAMED_IMPORT_SPECIFIER_IM__VERSIONED_TYPE_IMPORT:
				return isVersionedTypeImport();
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
			case ImPackage.VERSIONED_NAMED_IMPORT_SPECIFIER_IM__IMPORTED_TYPE_VERSIONS:
				getImportedTypeVersions().clear();
				getImportedTypeVersions().addAll((Collection<? extends SymbolTableEntryOriginal>)newValue);
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
			case ImPackage.VERSIONED_NAMED_IMPORT_SPECIFIER_IM__IMPORTED_TYPE_VERSIONS:
				getImportedTypeVersions().clear();
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
			case ImPackage.VERSIONED_NAMED_IMPORT_SPECIFIER_IM__IMPORTED_TYPE_VERSIONS:
				return importedTypeVersions != null && !importedTypeVersions.isEmpty();
			case ImPackage.VERSIONED_NAMED_IMPORT_SPECIFIER_IM__VERSIONED_TYPE_IMPORT:
				return isVersionedTypeImport() != VERSIONED_TYPE_IMPORT_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

} //VersionedNamedImportSpecifier_IMImpl
