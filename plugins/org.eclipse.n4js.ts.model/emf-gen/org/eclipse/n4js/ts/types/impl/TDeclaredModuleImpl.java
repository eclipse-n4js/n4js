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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.n4js.ts.types.AbstractModule;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.TDeclaredModule;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TDeclared Module</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class TDeclaredModuleImpl extends AbstractModuleImpl implements TDeclaredModule {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TDeclaredModuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TDECLARED_MODULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isStaticPolyfillModule() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isStaticPolyfillAware() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TDeclaredModule getContainingModule() {
		return this;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == AbstractNamespace.class) {
			switch (baseOperationID) {
				case TypesPackage.ABSTRACT_NAMESPACE___GET_CONTAINING_MODULE: return TypesPackage.TDECLARED_MODULE___GET_CONTAINING_MODULE;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == AbstractModule.class) {
			switch (baseOperationID) {
				case TypesPackage.ABSTRACT_MODULE___IS_STATIC_POLYFILL_MODULE: return TypesPackage.TDECLARED_MODULE___IS_STATIC_POLYFILL_MODULE;
				case TypesPackage.ABSTRACT_MODULE___IS_STATIC_POLYFILL_AWARE: return TypesPackage.TDECLARED_MODULE___IS_STATIC_POLYFILL_AWARE;
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
			case TypesPackage.TDECLARED_MODULE___IS_STATIC_POLYFILL_MODULE:
				return isStaticPolyfillModule();
			case TypesPackage.TDECLARED_MODULE___IS_STATIC_POLYFILL_AWARE:
				return isStaticPolyfillAware();
			case TypesPackage.TDECLARED_MODULE___GET_CONTAINING_MODULE:
				return getContainingModule();
		}
		return super.eInvoke(operationID, arguments);
	}

} //TDeclaredModuleImpl
