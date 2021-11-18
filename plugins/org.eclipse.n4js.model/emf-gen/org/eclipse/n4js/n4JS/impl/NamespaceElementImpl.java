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
import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.NamespaceElement;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Namespace Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class NamespaceElementImpl extends ProxyResolvingEObjectImpl implements NamespaceElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NamespaceElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.NAMESPACE_ELEMENT;
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
		while ((currContainer instanceof N4NamespaceDeclaration)) {
			{
				boolean _isEmpty = containingTypeNames.isEmpty();
				boolean _not = (!_isEmpty);
				if (_not) {
					containingTypeNames = ("." + containingTypeNames);
				}
				String _name = ((N4NamespaceDeclaration)currContainer).getName();
				String _plus = (_name + containingTypeNames);
				containingTypeNames = _plus;
				currContainer = ((N4NamespaceDeclaration)currContainer).eContainer();
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
			case N4JSPackage.NAMESPACE_ELEMENT___IS_HOLLOW:
				return isHollow();
			case N4JSPackage.NAMESPACE_ELEMENT___GET_CONTAINING_NAMESPACE_NAMES_WITH_DOT:
				return getContainingNamespaceNamesWithDot();
			case N4JSPackage.NAMESPACE_ELEMENT___GET_CONTAINING_NAMESPACE_NAMES:
				return getContainingNamespaceNames();
		}
		return super.eInvoke(operationID, arguments);
	}

} //NamespaceElementImpl
