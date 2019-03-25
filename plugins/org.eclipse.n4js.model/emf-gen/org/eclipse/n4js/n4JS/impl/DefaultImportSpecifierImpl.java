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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.n4js.n4JS.DefaultImportSpecifier;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Default Import Specifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class DefaultImportSpecifierImpl extends NamedImportSpecifierImpl implements DefaultImportSpecifier {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DefaultImportSpecifierImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.DEFAULT_IMPORT_SPECIFIER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAlias() {
		return this.getImportedElementAsText();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDefaultImport() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == NamedImportSpecifier.class) {
			switch (baseOperationID) {
				case N4JSPackage.NAMED_IMPORT_SPECIFIER___IS_DEFAULT_IMPORT: return N4JSPackage.DEFAULT_IMPORT_SPECIFIER___IS_DEFAULT_IMPORT;
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
			case N4JSPackage.DEFAULT_IMPORT_SPECIFIER___GET_ALIAS:
				return getAlias();
			case N4JSPackage.DEFAULT_IMPORT_SPECIFIER___IS_DEFAULT_IMPORT:
				return isDefaultImport();
		}
		return super.eInvoke(operationID, arguments);
	}

} //DefaultImportSpecifierImpl
