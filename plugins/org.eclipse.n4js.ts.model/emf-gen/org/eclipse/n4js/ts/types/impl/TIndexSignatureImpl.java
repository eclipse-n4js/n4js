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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.TIndexSignature;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TIndex Signature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TIndexSignatureImpl#isReadonly <em>Readonly</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TIndexSignatureImpl#getKeyName <em>Key Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TIndexSignatureImpl#getKeyTypeRef <em>Key Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TIndexSignatureImpl#getValueTypeRef <em>Value Type Ref</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TIndexSignatureImpl extends TMemberImpl implements TIndexSignature {
	/**
	 * The default value of the '{@link #isReadonly() <em>Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadonly()
	 * @generated
	 * @ordered
	 */
	protected static final boolean READONLY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReadonly() <em>Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadonly()
	 * @generated
	 * @ordered
	 */
	protected boolean readonly = READONLY_EDEFAULT;

	/**
	 * The default value of the '{@link #getKeyName() <em>Key Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeyName()
	 * @generated
	 * @ordered
	 */
	protected static final String KEY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getKeyName() <em>Key Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeyName()
	 * @generated
	 * @ordered
	 */
	protected String keyName = KEY_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getKeyTypeRef() <em>Key Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeyTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef keyTypeRef;

	/**
	 * The cached value of the '{@link #getValueTypeRef() <em>Value Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef valueTypeRef;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TIndexSignatureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TINDEX_SIGNATURE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isReadonly() {
		return readonly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReadonly(boolean newReadonly) {
		boolean oldReadonly = readonly;
		readonly = newReadonly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TINDEX_SIGNATURE__READONLY, oldReadonly, readonly));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getKeyName() {
		return keyName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setKeyName(String newKeyName) {
		String oldKeyName = keyName;
		keyName = newKeyName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TINDEX_SIGNATURE__KEY_NAME, oldKeyName, keyName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getKeyTypeRef() {
		return keyTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKeyTypeRef(TypeRef newKeyTypeRef, NotificationChain msgs) {
		TypeRef oldKeyTypeRef = keyTypeRef;
		keyTypeRef = newKeyTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.TINDEX_SIGNATURE__KEY_TYPE_REF, oldKeyTypeRef, newKeyTypeRef);
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
	public void setKeyTypeRef(TypeRef newKeyTypeRef) {
		if (newKeyTypeRef != keyTypeRef) {
			NotificationChain msgs = null;
			if (keyTypeRef != null)
				msgs = ((InternalEObject)keyTypeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TINDEX_SIGNATURE__KEY_TYPE_REF, null, msgs);
			if (newKeyTypeRef != null)
				msgs = ((InternalEObject)newKeyTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TINDEX_SIGNATURE__KEY_TYPE_REF, null, msgs);
			msgs = basicSetKeyTypeRef(newKeyTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TINDEX_SIGNATURE__KEY_TYPE_REF, newKeyTypeRef, newKeyTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getValueTypeRef() {
		return valueTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValueTypeRef(TypeRef newValueTypeRef, NotificationChain msgs) {
		TypeRef oldValueTypeRef = valueTypeRef;
		valueTypeRef = newValueTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.TINDEX_SIGNATURE__VALUE_TYPE_REF, oldValueTypeRef, newValueTypeRef);
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
	public void setValueTypeRef(TypeRef newValueTypeRef) {
		if (newValueTypeRef != valueTypeRef) {
			NotificationChain msgs = null;
			if (valueTypeRef != null)
				msgs = ((InternalEObject)valueTypeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TINDEX_SIGNATURE__VALUE_TYPE_REF, null, msgs);
			if (newValueTypeRef != null)
				msgs = ((InternalEObject)newValueTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TINDEX_SIGNATURE__VALUE_TYPE_REF, null, msgs);
			msgs = basicSetValueTypeRef(newValueTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TINDEX_SIGNATURE__VALUE_TYPE_REF, newValueTypeRef, newValueTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.TINDEX_SIGNATURE__KEY_TYPE_REF:
				return basicSetKeyTypeRef(null, msgs);
			case TypesPackage.TINDEX_SIGNATURE__VALUE_TYPE_REF:
				return basicSetValueTypeRef(null, msgs);
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
			case TypesPackage.TINDEX_SIGNATURE__READONLY:
				return isReadonly();
			case TypesPackage.TINDEX_SIGNATURE__KEY_NAME:
				return getKeyName();
			case TypesPackage.TINDEX_SIGNATURE__KEY_TYPE_REF:
				return getKeyTypeRef();
			case TypesPackage.TINDEX_SIGNATURE__VALUE_TYPE_REF:
				return getValueTypeRef();
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
			case TypesPackage.TINDEX_SIGNATURE__READONLY:
				setReadonly((Boolean)newValue);
				return;
			case TypesPackage.TINDEX_SIGNATURE__KEY_NAME:
				setKeyName((String)newValue);
				return;
			case TypesPackage.TINDEX_SIGNATURE__KEY_TYPE_REF:
				setKeyTypeRef((TypeRef)newValue);
				return;
			case TypesPackage.TINDEX_SIGNATURE__VALUE_TYPE_REF:
				setValueTypeRef((TypeRef)newValue);
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
			case TypesPackage.TINDEX_SIGNATURE__READONLY:
				setReadonly(READONLY_EDEFAULT);
				return;
			case TypesPackage.TINDEX_SIGNATURE__KEY_NAME:
				setKeyName(KEY_NAME_EDEFAULT);
				return;
			case TypesPackage.TINDEX_SIGNATURE__KEY_TYPE_REF:
				setKeyTypeRef((TypeRef)null);
				return;
			case TypesPackage.TINDEX_SIGNATURE__VALUE_TYPE_REF:
				setValueTypeRef((TypeRef)null);
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
			case TypesPackage.TINDEX_SIGNATURE__READONLY:
				return readonly != READONLY_EDEFAULT;
			case TypesPackage.TINDEX_SIGNATURE__KEY_NAME:
				return KEY_NAME_EDEFAULT == null ? keyName != null : !KEY_NAME_EDEFAULT.equals(keyName);
			case TypesPackage.TINDEX_SIGNATURE__KEY_TYPE_REF:
				return keyTypeRef != null;
			case TypesPackage.TINDEX_SIGNATURE__VALUE_TYPE_REF:
				return valueTypeRef != null;
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
		result.append(" (readonly: ");
		result.append(readonly);
		result.append(", keyName: ");
		result.append(keyName);
		result.append(')');
		return result.toString();
	}

} //TIndexSignatureImpl
