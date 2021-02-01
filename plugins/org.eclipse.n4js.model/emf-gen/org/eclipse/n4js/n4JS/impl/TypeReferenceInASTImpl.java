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
import org.eclipse.n4js.n4JS.TypeReferenceInAST;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Reference In AST</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.TypeReferenceInASTImpl#getTypeRef <em>Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.TypeReferenceInASTImpl#getTypeRefInAST <em>Type Ref In AST</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeReferenceInASTImpl<T extends TypeRef> extends ProxyResolvingEObjectImpl implements TypeReferenceInAST<T> {
	/**
	 * The cached value of the '{@link #getTypeRef() <em>Type Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeRef()
	 * @generated
	 * @ordered
	 */
	protected T typeRef;

	/**
	 * The cached value of the '{@link #getTypeRefInAST() <em>Type Ref In AST</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeRefInAST()
	 * @generated
	 * @ordered
	 */
	protected T typeRefInAST;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeReferenceInASTImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.TYPE_REFERENCE_IN_AST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getTypeRef() {
		if (typeRef != null && typeRef.eIsProxy()) {
			InternalEObject oldTypeRef = (InternalEObject)typeRef;
			typeRef = (T)eResolveProxy(oldTypeRef);
			if (typeRef != oldTypeRef) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.TYPE_REFERENCE_IN_AST__TYPE_REF, oldTypeRef, typeRef));
			}
		}
		return typeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public T basicGetTypeRef() {
		return typeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTypeRef(T newTypeRef) {
		T oldTypeRef = typeRef;
		typeRef = newTypeRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.TYPE_REFERENCE_IN_AST__TYPE_REF, oldTypeRef, typeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public T getTypeRefInAST() {
		return typeRefInAST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTypeRefInAST(T newTypeRefInAST, NotificationChain msgs) {
		T oldTypeRefInAST = typeRefInAST;
		typeRefInAST = newTypeRefInAST;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.TYPE_REFERENCE_IN_AST__TYPE_REF_IN_AST, oldTypeRefInAST, newTypeRefInAST);
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
	public void setTypeRefInAST(T newTypeRefInAST) {
		if (newTypeRefInAST != typeRefInAST) {
			NotificationChain msgs = null;
			if (typeRefInAST != null)
				msgs = ((InternalEObject)typeRefInAST).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.TYPE_REFERENCE_IN_AST__TYPE_REF_IN_AST, null, msgs);
			if (newTypeRefInAST != null)
				msgs = ((InternalEObject)newTypeRefInAST).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.TYPE_REFERENCE_IN_AST__TYPE_REF_IN_AST, null, msgs);
			msgs = basicSetTypeRefInAST(newTypeRefInAST, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.TYPE_REFERENCE_IN_AST__TYPE_REF_IN_AST, newTypeRefInAST, newTypeRefInAST));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.TYPE_REFERENCE_IN_AST__TYPE_REF_IN_AST:
				return basicSetTypeRefInAST(null, msgs);
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
			case N4JSPackage.TYPE_REFERENCE_IN_AST__TYPE_REF:
				if (resolve) return getTypeRef();
				return basicGetTypeRef();
			case N4JSPackage.TYPE_REFERENCE_IN_AST__TYPE_REF_IN_AST:
				return getTypeRefInAST();
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
			case N4JSPackage.TYPE_REFERENCE_IN_AST__TYPE_REF:
				setTypeRef((T)newValue);
				return;
			case N4JSPackage.TYPE_REFERENCE_IN_AST__TYPE_REF_IN_AST:
				setTypeRefInAST((T)newValue);
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
			case N4JSPackage.TYPE_REFERENCE_IN_AST__TYPE_REF:
				setTypeRef((T)null);
				return;
			case N4JSPackage.TYPE_REFERENCE_IN_AST__TYPE_REF_IN_AST:
				setTypeRefInAST((T)null);
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
			case N4JSPackage.TYPE_REFERENCE_IN_AST__TYPE_REF:
				return typeRef != null;
			case N4JSPackage.TYPE_REFERENCE_IN_AST__TYPE_REF_IN_AST:
				return typeRefInAST != null;
		}
		return super.eIsSet(featureID);
	}

} //TypeReferenceInASTImpl
