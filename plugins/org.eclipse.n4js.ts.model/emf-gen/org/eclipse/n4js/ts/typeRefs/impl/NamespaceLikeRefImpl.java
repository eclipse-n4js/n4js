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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.typeRefs.NamespaceLikeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;

import org.eclipse.n4js.ts.types.Type;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Namespace Like Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.NamespaceLikeRefImpl#getDeclaredTypeAsText <em>Declared Type As Text</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.NamespaceLikeRefImpl#getDeclaredType <em>Declared Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NamespaceLikeRefImpl extends ProxyResolvingEObjectImpl implements NamespaceLikeRef {
	/**
	 * The default value of the '{@link #getDeclaredTypeAsText() <em>Declared Type As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredTypeAsText()
	 * @generated
	 * @ordered
	 */
	protected static final String DECLARED_TYPE_AS_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDeclaredTypeAsText() <em>Declared Type As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredTypeAsText()
	 * @generated
	 * @ordered
	 */
	protected String declaredTypeAsText = DECLARED_TYPE_AS_TEXT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDeclaredType() <em>Declared Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredType()
	 * @generated
	 * @ordered
	 */
	protected Type declaredType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NamespaceLikeRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.NAMESPACE_LIKE_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDeclaredTypeAsText() {
		return declaredTypeAsText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredTypeAsText(String newDeclaredTypeAsText) {
		String oldDeclaredTypeAsText = declaredTypeAsText;
		declaredTypeAsText = newDeclaredTypeAsText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.NAMESPACE_LIKE_REF__DECLARED_TYPE_AS_TEXT, oldDeclaredTypeAsText, declaredTypeAsText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Type getDeclaredType() {
		if (declaredType != null && declaredType.eIsProxy()) {
			InternalEObject oldDeclaredType = (InternalEObject)declaredType;
			declaredType = (Type)eResolveProxy(oldDeclaredType);
			if (declaredType != oldDeclaredType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypeRefsPackage.NAMESPACE_LIKE_REF__DECLARED_TYPE, oldDeclaredType, declaredType));
			}
		}
		return declaredType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type basicGetDeclaredType() {
		return declaredType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredType(Type newDeclaredType) {
		Type oldDeclaredType = declaredType;
		declaredType = newDeclaredType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.NAMESPACE_LIKE_REF__DECLARED_TYPE, oldDeclaredType, declaredType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NamespaceLikeRef getPreviousSibling() {
		EObject _eContainer = this.eContainer();
		boolean _not = (!(_eContainer instanceof ParameterizedTypeRef));
		if (_not) {
			return null;
		}
		EObject _eContainer_1 = this.eContainer();
		return ((ParameterizedTypeRef) _eContainer_1).getPreviousSibling(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypeRefsPackage.NAMESPACE_LIKE_REF__DECLARED_TYPE_AS_TEXT:
				return getDeclaredTypeAsText();
			case TypeRefsPackage.NAMESPACE_LIKE_REF__DECLARED_TYPE:
				if (resolve) return getDeclaredType();
				return basicGetDeclaredType();
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
			case TypeRefsPackage.NAMESPACE_LIKE_REF__DECLARED_TYPE_AS_TEXT:
				setDeclaredTypeAsText((String)newValue);
				return;
			case TypeRefsPackage.NAMESPACE_LIKE_REF__DECLARED_TYPE:
				setDeclaredType((Type)newValue);
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
			case TypeRefsPackage.NAMESPACE_LIKE_REF__DECLARED_TYPE_AS_TEXT:
				setDeclaredTypeAsText(DECLARED_TYPE_AS_TEXT_EDEFAULT);
				return;
			case TypeRefsPackage.NAMESPACE_LIKE_REF__DECLARED_TYPE:
				setDeclaredType((Type)null);
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
			case TypeRefsPackage.NAMESPACE_LIKE_REF__DECLARED_TYPE_AS_TEXT:
				return DECLARED_TYPE_AS_TEXT_EDEFAULT == null ? declaredTypeAsText != null : !DECLARED_TYPE_AS_TEXT_EDEFAULT.equals(declaredTypeAsText);
			case TypeRefsPackage.NAMESPACE_LIKE_REF__DECLARED_TYPE:
				return declaredType != null;
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
			case TypeRefsPackage.NAMESPACE_LIKE_REF___GET_PREVIOUS_SIBLING:
				return getPreviousSibling();
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
		result.append(" (declaredTypeAsText: ");
		result.append(declaredTypeAsText);
		result.append(')');
		return result.toString();
	}

} //NamespaceLikeRefImpl
