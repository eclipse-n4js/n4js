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

import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableDeclarationOrBinding;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Variable Declaration Or Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class VariableDeclarationOrBindingImpl extends ProxyResolvingEObjectImpl implements VariableDeclarationOrBinding {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VariableDeclarationOrBindingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.VARIABLE_DECLARATION_OR_BINDING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<VariableDeclaration> getVariableDeclarations() {
		EList<VariableDeclaration> _switchResult = null;
		boolean _matched = false;
		if (this instanceof VariableDeclaration) {
			_matched=true;
			_switchResult = ECollections.<VariableDeclaration>toEList(java.util.Collections.<VariableDeclaration>unmodifiableList(org.eclipse.xtext.xbase.lib.CollectionLiterals.<VariableDeclaration>newArrayList(((VariableDeclaration) this))));
		}
		if (!_matched) {
			if (this instanceof VariableBinding) {
				_matched=true;
				_switchResult = ECollections.<VariableDeclaration>toEList(Iterators.<VariableDeclaration>filter(this.eAllContents(), VariableDeclaration.class));
			}
		}
		if (!_matched) {
			_switchResult = ECollections.<VariableDeclaration>toEList(java.util.Collections.<VariableDeclaration>unmodifiableList(org.eclipse.xtext.xbase.lib.CollectionLiterals.<VariableDeclaration>newArrayList()));
		}
		return _switchResult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getExpression() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case N4JSPackage.VARIABLE_DECLARATION_OR_BINDING___GET_VARIABLE_DECLARATIONS:
				return getVariableDeclarations();
			case N4JSPackage.VARIABLE_DECLARATION_OR_BINDING___GET_EXPRESSION:
				return getExpression();
		}
		return super.eInvoke(operationID, arguments);
	}

} //VariableDeclarationOrBindingImpl
