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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.TypedElement;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Typed Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.TypedElementImpl#getDeclaredTypeRef <em>Declared Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.TypedElementImpl#getBogusTypeRef <em>Bogus Type Ref</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class TypedElementImpl extends ProxyResolvingEObjectImpl implements TypedElement {
	/**
	 * The cached value of the '{@link #getDeclaredTypeRef() <em>Declared Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef declaredTypeRef;

	/**
	 * The cached value of the '{@link #getBogusTypeRef() <em>Bogus Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBogusTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef bogusTypeRef;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypedElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.TYPED_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredTypeRef() {
		return declaredTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredTypeRef(TypeRef newDeclaredTypeRef, NotificationChain msgs) {
		TypeRef oldDeclaredTypeRef = declaredTypeRef;
		declaredTypeRef = newDeclaredTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF, oldDeclaredTypeRef, newDeclaredTypeRef);
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
	public void setDeclaredTypeRef(TypeRef newDeclaredTypeRef) {
		if (newDeclaredTypeRef != declaredTypeRef) {
			NotificationChain msgs = null;
			if (declaredTypeRef != null)
				msgs = ((InternalEObject)declaredTypeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF, null, msgs);
			if (newDeclaredTypeRef != null)
				msgs = ((InternalEObject)newDeclaredTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF, null, msgs);
			msgs = basicSetDeclaredTypeRef(newDeclaredTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF, newDeclaredTypeRef, newDeclaredTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getBogusTypeRef() {
		return bogusTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBogusTypeRef(TypeRef newBogusTypeRef, NotificationChain msgs) {
		TypeRef oldBogusTypeRef = bogusTypeRef;
		bogusTypeRef = newBogusTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.TYPED_ELEMENT__BOGUS_TYPE_REF, oldBogusTypeRef, newBogusTypeRef);
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
	public void setBogusTypeRef(TypeRef newBogusTypeRef) {
		if (newBogusTypeRef != bogusTypeRef) {
			NotificationChain msgs = null;
			if (bogusTypeRef != null)
				msgs = ((InternalEObject)bogusTypeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.TYPED_ELEMENT__BOGUS_TYPE_REF, null, msgs);
			if (newBogusTypeRef != null)
				msgs = ((InternalEObject)newBogusTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.TYPED_ELEMENT__BOGUS_TYPE_REF, null, msgs);
			msgs = basicSetBogusTypeRef(newBogusTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.TYPED_ELEMENT__BOGUS_TYPE_REF, newBogusTypeRef, newBogusTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF:
				return basicSetDeclaredTypeRef(null, msgs);
			case N4JSPackage.TYPED_ELEMENT__BOGUS_TYPE_REF:
				return basicSetBogusTypeRef(null, msgs);
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
			case N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF:
				return getDeclaredTypeRef();
			case N4JSPackage.TYPED_ELEMENT__BOGUS_TYPE_REF:
				return getBogusTypeRef();
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
			case N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF:
				setDeclaredTypeRef((TypeRef)newValue);
				return;
			case N4JSPackage.TYPED_ELEMENT__BOGUS_TYPE_REF:
				setBogusTypeRef((TypeRef)newValue);
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
			case N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF:
				setDeclaredTypeRef((TypeRef)null);
				return;
			case N4JSPackage.TYPED_ELEMENT__BOGUS_TYPE_REF:
				setBogusTypeRef((TypeRef)null);
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
			case N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF:
				return declaredTypeRef != null;
			case N4JSPackage.TYPED_ELEMENT__BOGUS_TYPE_REF:
				return bogusTypeRef != null;
		}
		return super.eIsSet(featureID);
	}

} //TypedElementImpl
