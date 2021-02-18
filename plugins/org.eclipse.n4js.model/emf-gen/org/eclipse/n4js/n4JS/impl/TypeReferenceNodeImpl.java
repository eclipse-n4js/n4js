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

import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.TypeReferenceNode;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Reference Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.TypeReferenceNodeImpl#getCachedProcessedTypeRef <em>Cached Processed Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.TypeReferenceNodeImpl#getTypeRefInAST <em>Type Ref In AST</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeReferenceNodeImpl<T extends TypeRef> extends ProxyResolvingEObjectImpl implements TypeReferenceNode<T> {
	/**
	 * The cached value of the '{@link #getCachedProcessedTypeRef() <em>Cached Processed Type Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCachedProcessedTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef cachedProcessedTypeRef;

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
	protected TypeReferenceNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.TYPE_REFERENCE_NODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getCachedProcessedTypeRef() {
		if (cachedProcessedTypeRef != null && cachedProcessedTypeRef.eIsProxy()) {
			InternalEObject oldCachedProcessedTypeRef = (InternalEObject)cachedProcessedTypeRef;
			cachedProcessedTypeRef = (TypeRef)eResolveProxy(oldCachedProcessedTypeRef);
			if (cachedProcessedTypeRef != oldCachedProcessedTypeRef) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.TYPE_REFERENCE_NODE__CACHED_PROCESSED_TYPE_REF, oldCachedProcessedTypeRef, cachedProcessedTypeRef));
			}
		}
		return cachedProcessedTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeRef basicGetCachedProcessedTypeRef() {
		return cachedProcessedTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCachedProcessedTypeRef(TypeRef newCachedProcessedTypeRef) {
		TypeRef oldCachedProcessedTypeRef = cachedProcessedTypeRef;
		cachedProcessedTypeRef = newCachedProcessedTypeRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.TYPE_REFERENCE_NODE__CACHED_PROCESSED_TYPE_REF, oldCachedProcessedTypeRef, cachedProcessedTypeRef));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.TYPE_REFERENCE_NODE__TYPE_REF_IN_AST, oldTypeRefInAST, newTypeRefInAST);
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
				msgs = ((InternalEObject)typeRefInAST).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.TYPE_REFERENCE_NODE__TYPE_REF_IN_AST, null, msgs);
			if (newTypeRefInAST != null)
				msgs = ((InternalEObject)newTypeRefInAST).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.TYPE_REFERENCE_NODE__TYPE_REF_IN_AST, null, msgs);
			msgs = basicSetTypeRefInAST(newTypeRefInAST, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.TYPE_REFERENCE_NODE__TYPE_REF_IN_AST, newTypeRefInAST, newTypeRefInAST));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getTypeRef() {
		final TypeRef result = this.getCachedProcessedTypeRef();
		if (((result == null) && (this.getTypeRefInAST() != null))) {
			throw new IllegalStateException("attempt to access resolution of a typeRefInAST before it was computed by TypeRefProcessor");
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.TYPE_REFERENCE_NODE__TYPE_REF_IN_AST:
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
			case N4JSPackage.TYPE_REFERENCE_NODE__CACHED_PROCESSED_TYPE_REF:
				if (resolve) return getCachedProcessedTypeRef();
				return basicGetCachedProcessedTypeRef();
			case N4JSPackage.TYPE_REFERENCE_NODE__TYPE_REF_IN_AST:
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
			case N4JSPackage.TYPE_REFERENCE_NODE__CACHED_PROCESSED_TYPE_REF:
				setCachedProcessedTypeRef((TypeRef)newValue);
				return;
			case N4JSPackage.TYPE_REFERENCE_NODE__TYPE_REF_IN_AST:
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
			case N4JSPackage.TYPE_REFERENCE_NODE__CACHED_PROCESSED_TYPE_REF:
				setCachedProcessedTypeRef((TypeRef)null);
				return;
			case N4JSPackage.TYPE_REFERENCE_NODE__TYPE_REF_IN_AST:
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
			case N4JSPackage.TYPE_REFERENCE_NODE__CACHED_PROCESSED_TYPE_REF:
				return cachedProcessedTypeRef != null;
			case N4JSPackage.TYPE_REFERENCE_NODE__TYPE_REF_IN_AST:
				return typeRefInAST != null;
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
			case N4JSPackage.TYPE_REFERENCE_NODE___GET_TYPE_REF:
				return getTypeRef();
		}
		return super.eInvoke(operationID, arguments);
	}

} //TypeReferenceNodeImpl
