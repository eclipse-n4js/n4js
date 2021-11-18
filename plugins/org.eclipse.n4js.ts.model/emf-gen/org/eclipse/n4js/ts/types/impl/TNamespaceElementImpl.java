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
import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.ts.types.TNamespace;
import org.eclipse.n4js.ts.types.TNamespaceElement;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TNamespace Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class TNamespaceElementImpl extends ProxyResolvingEObjectImpl implements TNamespaceElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TNamespaceElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TNAMESPACE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isHollow() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getContainingNamespaceNamesWithDot() {
		boolean _isEmpty = this.getContainingNamespaceNames().isEmpty();
		if (_isEmpty) {
			return this.getContainingNamespaceNames();
		}
		else {
			String _containingNamespaceNames = this.getContainingNamespaceNames();
			return (_containingNamespaceNames + ".");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getContainingNamespaceNames() {
		String containingTypeNames = "";
		EObject currContainer = this.eContainer();
		while ((currContainer instanceof TNamespace)) {
			{
				boolean _isEmpty = containingTypeNames.isEmpty();
				boolean _not = (!_isEmpty);
				if (_not) {
					containingTypeNames = ("." + containingTypeNames);
				}
				String _name = ((TNamespace)currContainer).getName();
				String _plus = (_name + containingTypeNames);
				containingTypeNames = _plus;
				currContainer = ((TNamespace)currContainer).eContainer();
			}
		}
		return containingTypeNames;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case TypesPackage.TNAMESPACE_ELEMENT___IS_HOLLOW:
				return isHollow();
			case TypesPackage.TNAMESPACE_ELEMENT___GET_CONTAINING_NAMESPACE_NAMES_WITH_DOT:
				return getContainingNamespaceNamesWithDot();
			case TypesPackage.TNAMESPACE_ELEMENT___GET_CONTAINING_NAMESPACE_NAMES:
				return getContainingNamespaceNames();
		}
		return super.eInvoke(operationID, arguments);
	}

} //TNamespaceElementImpl
