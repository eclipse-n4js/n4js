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

import com.google.common.collect.Iterators;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.n4js.n4JS.BindingPattern;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.VariableDeclaration;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Binding Pattern</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class BindingPatternImpl extends ProxyResolvingEObjectImpl implements BindingPattern {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BindingPatternImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.BINDING_PATTERN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VariableDeclaration> getAllVariableDeclarations() {
		return ECollections.<VariableDeclaration>toEList(Iterators.<VariableDeclaration>filter(this.eAllContents(), VariableDeclaration.class));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case N4JSPackage.BINDING_PATTERN___GET_ALL_VARIABLE_DECLARATIONS:
				return getAllVariableDeclarations();
		}
		return super.eInvoke(operationID, arguments);
	}

} //BindingPatternImpl
