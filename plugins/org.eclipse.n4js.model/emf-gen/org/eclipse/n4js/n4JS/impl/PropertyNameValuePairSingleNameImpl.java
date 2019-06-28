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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

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
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.PropertyNameValuePairSingleNameImpl#getIdentifierRef <em>Identifier Ref</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PropertyNameValuePairSingleNameImpl extends PropertyNameValuePairImpl implements PropertyNameValuePairSingleName {
	/**
	 * The cached value of the '{@link #getIdentifierRef() <em>Identifier Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdentifierRef()
	 * @generated
	 * @ordered
	 */
	protected IdentifierRef identifierRef;

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
		return identifierRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIdentifierRef(IdentifierRef newIdentifierRef, NotificationChain msgs) {
		IdentifierRef oldIdentifierRef = identifierRef;
		identifierRef = newIdentifierRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME__IDENTIFIER_REF, oldIdentifierRef, newIdentifierRef);
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
	public void setIdentifierRef(IdentifierRef newIdentifierRef) {
		if (newIdentifierRef != identifierRef) {
			NotificationChain msgs = null;
			if (identifierRef != null)
				msgs = ((InternalEObject)identifierRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME__IDENTIFIER_REF, null, msgs);
			if (newIdentifierRef != null)
				msgs = ((InternalEObject)newIdentifierRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME__IDENTIFIER_REF, null, msgs);
			msgs = basicSetIdentifierRef(newIdentifierRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME__IDENTIFIER_REF, newIdentifierRef, newIdentifierRef));
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME__IDENTIFIER_REF:
				return basicSetIdentifierRef(null, msgs);
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
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME__IDENTIFIER_REF:
				return getIdentifierRef();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME__IDENTIFIER_REF:
				setIdentifierRef((IdentifierRef)newValue);
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
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME__IDENTIFIER_REF:
				setIdentifierRef((IdentifierRef)null);
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
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME__IDENTIFIER_REF:
				return identifierRef != null;
		}
		return super.eIsSet(featureID);
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
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME___GET_NAME:
				return getName();
		}
		return super.eInvoke(operationID, arguments);
	}

} //PropertyNameValuePairSingleNameImpl
