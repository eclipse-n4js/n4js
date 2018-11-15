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
package org.eclipse.n4js.json.JSON.impl;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;

import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.JSON.NameValuePair;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.json.JSON.impl.JSONObjectImpl#getNameValuePairs <em>Name Value Pairs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JSONObjectImpl extends JSONValueImpl implements JSONObject {
	/**
	 * The cached value of the '{@link #getNameValuePairs() <em>Name Value Pairs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNameValuePairs()
	 * @generated
	 * @ordered
	 */
	protected EList<NameValuePair> nameValuePairs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JSONObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JSONPackage.Literals.JSON_OBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NameValuePair> getNameValuePairs() {
		if (nameValuePairs == null) {
			nameValuePairs = new EObjectContainmentEList<NameValuePair>(NameValuePair.class, this, JSONPackage.JSON_OBJECT__NAME_VALUE_PAIRS);
		}
		return nameValuePairs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		final Function1<NameValuePair, String> _function = new Function1<NameValuePair, String>() {
			public String apply(final NameValuePair e) {
				return e.toString();
			}
		};
		String _join = IterableExtensions.join(XcoreEListExtensions.<NameValuePair, String>map(this.getNameValuePairs(), _function), ",\n");
		String _plus = ("{\n" + _join);
		return (_plus + "\n}");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JSONPackage.JSON_OBJECT__NAME_VALUE_PAIRS:
				return ((InternalEList<?>)getNameValuePairs()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JSONPackage.JSON_OBJECT__NAME_VALUE_PAIRS:
				return getNameValuePairs();
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
			case JSONPackage.JSON_OBJECT__NAME_VALUE_PAIRS:
				getNameValuePairs().clear();
				getNameValuePairs().addAll((Collection<? extends NameValuePair>)newValue);
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
			case JSONPackage.JSON_OBJECT__NAME_VALUE_PAIRS:
				getNameValuePairs().clear();
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
			case JSONPackage.JSON_OBJECT__NAME_VALUE_PAIRS:
				return nameValuePairs != null && !nameValuePairs.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case JSONPackage.JSON_OBJECT___TO_STRING:
				return toString();
		}
		return super.eInvoke(operationID, arguments);
	}

} //JSONObjectImpl
