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
package org.eclipse.n4js.ts.typeRefs.impl;

import java.lang.reflect.InvocationTargetException;

import java.util.UUID;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.Wildcard;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Existential Type Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ExistentialTypeRefImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ExistentialTypeRefImpl#isReopened <em>Reopened</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ExistentialTypeRefImpl#getWildcard <em>Wildcard</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExistentialTypeRefImpl extends TypeRefImpl implements ExistentialTypeRef {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final UUID ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected UUID id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #isReopened() <em>Reopened</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReopened()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REOPENED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReopened() <em>Reopened</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReopened()
	 * @generated
	 * @ordered
	 */
	protected boolean reopened = REOPENED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getWildcard() <em>Wildcard</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWildcard()
	 * @generated
	 * @ordered
	 */
	protected Wildcard wildcard;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExistentialTypeRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.EXISTENTIAL_TYPE_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UUID getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(UUID newId) {
		UUID oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.EXISTENTIAL_TYPE_REF__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isReopened() {
		return reopened;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReopened(boolean newReopened) {
		boolean oldReopened = reopened;
		reopened = newReopened;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.EXISTENTIAL_TYPE_REF__REOPENED, oldReopened, reopened));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Wildcard getWildcard() {
		if (wildcard != null && wildcard.eIsProxy()) {
			InternalEObject oldWildcard = (InternalEObject)wildcard;
			wildcard = (Wildcard)eResolveProxy(oldWildcard);
			if (wildcard != oldWildcard) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypeRefsPackage.EXISTENTIAL_TYPE_REF__WILDCARD, oldWildcard, wildcard));
			}
		}
		return wildcard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Wildcard basicGetWildcard() {
		return wildcard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWildcard(Wildcard newWildcard) {
		Wildcard oldWildcard = wildcard;
		wildcard = newWildcard;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.EXISTENTIAL_TYPE_REF__WILDCARD, oldWildcard, wildcard));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isExistential() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isGeneric() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isParameterized() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String internalGetTypeRefAsString() {
		Wildcard _wildcard = this.getWildcard();
		String _typeRefAsString = null;
		if (_wildcard!=null) {
			_typeRefAsString=_wildcard.getTypeRefAsString();
		}
		return _typeRefAsString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypeRefsPackage.EXISTENTIAL_TYPE_REF__ID:
				return getId();
			case TypeRefsPackage.EXISTENTIAL_TYPE_REF__REOPENED:
				return isReopened();
			case TypeRefsPackage.EXISTENTIAL_TYPE_REF__WILDCARD:
				if (resolve) return getWildcard();
				return basicGetWildcard();
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
			case TypeRefsPackage.EXISTENTIAL_TYPE_REF__ID:
				setId((UUID)newValue);
				return;
			case TypeRefsPackage.EXISTENTIAL_TYPE_REF__REOPENED:
				setReopened((Boolean)newValue);
				return;
			case TypeRefsPackage.EXISTENTIAL_TYPE_REF__WILDCARD:
				setWildcard((Wildcard)newValue);
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
			case TypeRefsPackage.EXISTENTIAL_TYPE_REF__ID:
				setId(ID_EDEFAULT);
				return;
			case TypeRefsPackage.EXISTENTIAL_TYPE_REF__REOPENED:
				setReopened(REOPENED_EDEFAULT);
				return;
			case TypeRefsPackage.EXISTENTIAL_TYPE_REF__WILDCARD:
				setWildcard((Wildcard)null);
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
			case TypeRefsPackage.EXISTENTIAL_TYPE_REF__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case TypeRefsPackage.EXISTENTIAL_TYPE_REF__REOPENED:
				return reopened != REOPENED_EDEFAULT;
			case TypeRefsPackage.EXISTENTIAL_TYPE_REF__WILDCARD:
				return wildcard != null;
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
		if (baseClass == TypeArgument.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_ARGUMENT___INTERNAL_GET_TYPE_REF_AS_STRING: return TypeRefsPackage.EXISTENTIAL_TYPE_REF___INTERNAL_GET_TYPE_REF_AS_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_REF___IS_EXISTENTIAL: return TypeRefsPackage.EXISTENTIAL_TYPE_REF___IS_EXISTENTIAL;
				case TypeRefsPackage.TYPE_REF___IS_GENERIC: return TypeRefsPackage.EXISTENTIAL_TYPE_REF___IS_GENERIC;
				case TypeRefsPackage.TYPE_REF___IS_PARAMETERIZED: return TypeRefsPackage.EXISTENTIAL_TYPE_REF___IS_PARAMETERIZED;
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
			case TypeRefsPackage.EXISTENTIAL_TYPE_REF___IS_EXISTENTIAL:
				return isExistential();
			case TypeRefsPackage.EXISTENTIAL_TYPE_REF___IS_GENERIC:
				return isGeneric();
			case TypeRefsPackage.EXISTENTIAL_TYPE_REF___IS_PARAMETERIZED:
				return isParameterized();
			case TypeRefsPackage.EXISTENTIAL_TYPE_REF___INTERNAL_GET_TYPE_REF_AS_STRING:
				return internalGetTypeRefAsString();
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(" (id: ");
		result.append(id);
		result.append(", reopened: ");
		result.append(reopened);
		result.append(')');
		return result.toString();
	}

} //ExistentialTypeRefImpl
