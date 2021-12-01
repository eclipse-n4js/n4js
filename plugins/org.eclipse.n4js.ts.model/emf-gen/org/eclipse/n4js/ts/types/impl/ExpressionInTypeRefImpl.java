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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

import org.eclipse.n4js.ts.typeRefs.LiteralTypeRef;

import org.eclipse.n4js.ts.types.ExpressionInTypeRef;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Expression In Type Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.ExpressionInTypeRefImpl#getNameTypeRef <em>Name Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.ExpressionInTypeRefImpl#getIdentifierNames <em>Identifier Names</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExpressionInTypeRefImpl extends ProxyResolvingEObjectImpl implements ExpressionInTypeRef {
	/**
	 * The cached value of the '{@link #getNameTypeRef() <em>Name Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNameTypeRef()
	 * @generated
	 * @ordered
	 */
	protected LiteralTypeRef nameTypeRef;

	/**
	 * The cached value of the '{@link #getIdentifierNames() <em>Identifier Names</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdentifierNames()
	 * @generated
	 * @ordered
	 */
	protected EList<String> identifierNames;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExpressionInTypeRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.EXPRESSION_IN_TYPE_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LiteralTypeRef getNameTypeRef() {
		return nameTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNameTypeRef(LiteralTypeRef newNameTypeRef, NotificationChain msgs) {
		LiteralTypeRef oldNameTypeRef = nameTypeRef;
		nameTypeRef = newNameTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.EXPRESSION_IN_TYPE_REF__NAME_TYPE_REF, oldNameTypeRef, newNameTypeRef);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNameTypeRef(LiteralTypeRef newNameTypeRef) {
		if (newNameTypeRef != nameTypeRef) {
			NotificationChain msgs = null;
			if (nameTypeRef != null)
				msgs = ((InternalEObject)nameTypeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.EXPRESSION_IN_TYPE_REF__NAME_TYPE_REF, null, msgs);
			if (newNameTypeRef != null)
				msgs = ((InternalEObject)newNameTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.EXPRESSION_IN_TYPE_REF__NAME_TYPE_REF, null, msgs);
			msgs = basicSetNameTypeRef(newNameTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.EXPRESSION_IN_TYPE_REF__NAME_TYPE_REF, newNameTypeRef, newNameTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getIdentifierNames() {
		if (identifierNames == null) {
			identifierNames = new EDataTypeEList<String>(String.class, this, TypesPackage.EXPRESSION_IN_TYPE_REF__IDENTIFIER_NAMES);
		}
		return identifierNames;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.EXPRESSION_IN_TYPE_REF__NAME_TYPE_REF:
				return basicSetNameTypeRef(null, msgs);
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
			case TypesPackage.EXPRESSION_IN_TYPE_REF__NAME_TYPE_REF:
				return getNameTypeRef();
			case TypesPackage.EXPRESSION_IN_TYPE_REF__IDENTIFIER_NAMES:
				return getIdentifierNames();
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
			case TypesPackage.EXPRESSION_IN_TYPE_REF__NAME_TYPE_REF:
				setNameTypeRef((LiteralTypeRef)newValue);
				return;
			case TypesPackage.EXPRESSION_IN_TYPE_REF__IDENTIFIER_NAMES:
				getIdentifierNames().clear();
				getIdentifierNames().addAll((Collection<? extends String>)newValue);
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
			case TypesPackage.EXPRESSION_IN_TYPE_REF__NAME_TYPE_REF:
				setNameTypeRef((LiteralTypeRef)null);
				return;
			case TypesPackage.EXPRESSION_IN_TYPE_REF__IDENTIFIER_NAMES:
				getIdentifierNames().clear();
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
			case TypesPackage.EXPRESSION_IN_TYPE_REF__NAME_TYPE_REF:
				return nameTypeRef != null;
			case TypesPackage.EXPRESSION_IN_TYPE_REF__IDENTIFIER_NAMES:
				return identifierNames != null && !identifierNames.isEmpty();
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
		result.append(" (identifierNames: ");
		result.append(identifierNames);
		result.append(')');
		return result.toString();
	}

} //ExpressionInTypeRefImpl
