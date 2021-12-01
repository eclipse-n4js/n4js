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

import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.NamespaceElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Namespace Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class NamespaceElementImpl extends ScriptElementImpl implements NamespaceElement {
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
	public N4NamespaceDeclaration getNamespace() {
		EObject parent = this.eContainer();
		if ((parent instanceof ExportDeclaration)) {
			parent = ((ExportDeclaration)parent).eContainer();
		}
		if ((parent instanceof N4NamespaceDeclaration)) {
			return ((N4NamespaceDeclaration)parent);
		}
		return null;
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
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case N4JSPackage.NAMESPACE_ELEMENT___GET_NAMESPACE:
				return getNamespace();
			case N4JSPackage.NAMESPACE_ELEMENT___IS_HOLLOW:
				return isHollow();
		}
		return super.eInvoke(operationID, arguments);
	}

} //NamespaceElementImpl
