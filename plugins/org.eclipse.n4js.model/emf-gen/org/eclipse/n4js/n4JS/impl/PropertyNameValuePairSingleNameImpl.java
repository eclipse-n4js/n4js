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

import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.n4JS.PropertyNameValuePairSingleName;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property Name Value Pair Single Name</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class PropertyNameValuePairSingleNameImpl extends PropertyNameValuePairImpl implements PropertyNameValuePairSingleName {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyNameValuePairSingleNameImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IdentifierRef getIdentifierRef() {
		final Expression expr = this.getExpression();
		Expression _xifexpression = null;
		if ((expr instanceof AssignmentExpression)) {
			_xifexpression = ((AssignmentExpression)expr).getLhs();
		}
		else {
			_xifexpression = expr;
		}
		final Expression candidate = _xifexpression;
		IdentifierRef _xifexpression_1 = null;
		if ((candidate instanceof IdentifierRef)) {
			_xifexpression_1 = ((IdentifierRef)candidate);
		}
		return _xifexpression_1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		String _elvis = null;
		String _name = super.getName();
		if (_name != null) {
			_elvis = _name;
		} else {
			IdentifierRef _identifierRef = this.getIdentifierRef();
			String _idAsText = null;
			if (_identifierRef!=null) {
				_idAsText=_identifierRef.getIdAsText();
			}
			_elvis = _idAsText;
		}
		return _elvis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == NamedElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.NAMED_ELEMENT___GET_NAME: return N4JSPackage.PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME___GET_NAME;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == PropertyNameOwner.class) {
			switch (baseOperationID) {
				case N4JSPackage.PROPERTY_NAME_OWNER___GET_NAME: return N4JSPackage.PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME___GET_NAME;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == PropertyAssignment.class) {
			switch (baseOperationID) {
				case N4JSPackage.PROPERTY_ASSIGNMENT___GET_NAME: return N4JSPackage.PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME___GET_NAME;
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
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME___GET_IDENTIFIER_REF:
				return getIdentifierRef();
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME___GET_NAME:
				return getName();
		}
		return super.eInvoke(operationID, arguments);
	}

} //PropertyNameValuePairSingleNameImpl
