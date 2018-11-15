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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.N4JSPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Array Literal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ArrayLiteralImpl#getElements <em>Elements</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ArrayLiteralImpl#isTrailingComma <em>Trailing Comma</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ArrayLiteralImpl extends PrimaryExpressionImpl implements ArrayLiteral {
	/**
	 * The cached value of the '{@link #getElements() <em>Elements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElements()
	 * @generated
	 * @ordered
	 */
	protected EList<ArrayElement> elements;

	/**
	 * The default value of the '{@link #isTrailingComma() <em>Trailing Comma</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTrailingComma()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TRAILING_COMMA_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isTrailingComma() <em>Trailing Comma</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTrailingComma()
	 * @generated
	 * @ordered
	 */
	protected boolean trailingComma = TRAILING_COMMA_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArrayLiteralImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.ARRAY_LITERAL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ArrayElement> getElements() {
		if (elements == null) {
			elements = new EObjectContainmentEList<ArrayElement>(ArrayElement.class, this, N4JSPackage.ARRAY_LITERAL__ELEMENTS);
		}
		return elements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTrailingComma() {
		return trailingComma;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrailingComma(boolean newTrailingComma) {
		boolean oldTrailingComma = trailingComma;
		trailingComma = newTrailingComma;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.ARRAY_LITERAL__TRAILING_COMMA, oldTrailingComma, trailingComma));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.ARRAY_LITERAL__ELEMENTS:
				return ((InternalEList<?>)getElements()).basicRemove(otherEnd, msgs);
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
			case N4JSPackage.ARRAY_LITERAL__ELEMENTS:
				return getElements();
			case N4JSPackage.ARRAY_LITERAL__TRAILING_COMMA:
				return isTrailingComma();
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
			case N4JSPackage.ARRAY_LITERAL__ELEMENTS:
				getElements().clear();
				getElements().addAll((Collection<? extends ArrayElement>)newValue);
				return;
			case N4JSPackage.ARRAY_LITERAL__TRAILING_COMMA:
				setTrailingComma((Boolean)newValue);
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
			case N4JSPackage.ARRAY_LITERAL__ELEMENTS:
				getElements().clear();
				return;
			case N4JSPackage.ARRAY_LITERAL__TRAILING_COMMA:
				setTrailingComma(TRAILING_COMMA_EDEFAULT);
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
			case N4JSPackage.ARRAY_LITERAL__ELEMENTS:
				return elements != null && !elements.isEmpty();
			case N4JSPackage.ARRAY_LITERAL__TRAILING_COMMA:
				return trailingComma != TRAILING_COMMA_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (trailingComma: ");
		result.append(trailingComma);
		result.append(')');
		return result.toString();
	}

} //ArrayLiteralImpl
