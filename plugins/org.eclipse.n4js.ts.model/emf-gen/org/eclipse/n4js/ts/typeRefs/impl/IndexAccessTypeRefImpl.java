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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.typeRefs.IndexAccessTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Index Access Type Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.IndexAccessTypeRefImpl#getTargetTypeRef <em>Target Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.IndexAccessTypeRefImpl#getIndexTypeRef <em>Index Type Ref</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IndexAccessTypeRefImpl extends TypeRefImpl implements IndexAccessTypeRef {
	/**
	 * The cached value of the '{@link #getTargetTypeRef() <em>Target Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef targetTypeRef;

	/**
	 * The cached value of the '{@link #getIndexTypeRef() <em>Index Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndexTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef indexTypeRef;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IndexAccessTypeRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.INDEX_ACCESS_TYPE_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getTargetTypeRef() {
		return targetTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTargetTypeRef(TypeRef newTargetTypeRef, NotificationChain msgs) {
		TypeRef oldTargetTypeRef = targetTypeRef;
		targetTypeRef = newTargetTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypeRefsPackage.INDEX_ACCESS_TYPE_REF__TARGET_TYPE_REF, oldTargetTypeRef, newTargetTypeRef);
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
	public void setTargetTypeRef(TypeRef newTargetTypeRef) {
		if (newTargetTypeRef != targetTypeRef) {
			NotificationChain msgs = null;
			if (targetTypeRef != null)
				msgs = ((InternalEObject)targetTypeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.INDEX_ACCESS_TYPE_REF__TARGET_TYPE_REF, null, msgs);
			if (newTargetTypeRef != null)
				msgs = ((InternalEObject)newTargetTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.INDEX_ACCESS_TYPE_REF__TARGET_TYPE_REF, null, msgs);
			msgs = basicSetTargetTypeRef(newTargetTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.INDEX_ACCESS_TYPE_REF__TARGET_TYPE_REF, newTargetTypeRef, newTargetTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getIndexTypeRef() {
		return indexTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIndexTypeRef(TypeRef newIndexTypeRef, NotificationChain msgs) {
		TypeRef oldIndexTypeRef = indexTypeRef;
		indexTypeRef = newIndexTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypeRefsPackage.INDEX_ACCESS_TYPE_REF__INDEX_TYPE_REF, oldIndexTypeRef, newIndexTypeRef);
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
	public void setIndexTypeRef(TypeRef newIndexTypeRef) {
		if (newIndexTypeRef != indexTypeRef) {
			NotificationChain msgs = null;
			if (indexTypeRef != null)
				msgs = ((InternalEObject)indexTypeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.INDEX_ACCESS_TYPE_REF__INDEX_TYPE_REF, null, msgs);
			if (newIndexTypeRef != null)
				msgs = ((InternalEObject)newIndexTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.INDEX_ACCESS_TYPE_REF__INDEX_TYPE_REF, null, msgs);
			msgs = basicSetIndexTypeRef(newIndexTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.INDEX_ACCESS_TYPE_REF__INDEX_TYPE_REF, newIndexTypeRef, newIndexTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String internalGetTypeRefAsString() {
		TypeRef _targetTypeRef = this.getTargetTypeRef();
		String _typeRefAsString = null;
		if (_targetTypeRef!=null) {
			_typeRefAsString=_targetTypeRef.getTypeRefAsString();
		}
		String _plus = (_typeRefAsString + "[");
		TypeRef _indexTypeRef = this.getIndexTypeRef();
		String _typeRefAsString_1 = null;
		if (_indexTypeRef!=null) {
			_typeRefAsString_1=_indexTypeRef.getTypeRefAsString();
		}
		String _plus_1 = (_plus + _typeRefAsString_1);
		return (_plus_1 + "]");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypeRefsPackage.INDEX_ACCESS_TYPE_REF__TARGET_TYPE_REF:
				return basicSetTargetTypeRef(null, msgs);
			case TypeRefsPackage.INDEX_ACCESS_TYPE_REF__INDEX_TYPE_REF:
				return basicSetIndexTypeRef(null, msgs);
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
			case TypeRefsPackage.INDEX_ACCESS_TYPE_REF__TARGET_TYPE_REF:
				return getTargetTypeRef();
			case TypeRefsPackage.INDEX_ACCESS_TYPE_REF__INDEX_TYPE_REF:
				return getIndexTypeRef();
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
			case TypeRefsPackage.INDEX_ACCESS_TYPE_REF__TARGET_TYPE_REF:
				setTargetTypeRef((TypeRef)newValue);
				return;
			case TypeRefsPackage.INDEX_ACCESS_TYPE_REF__INDEX_TYPE_REF:
				setIndexTypeRef((TypeRef)newValue);
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
			case TypeRefsPackage.INDEX_ACCESS_TYPE_REF__TARGET_TYPE_REF:
				setTargetTypeRef((TypeRef)null);
				return;
			case TypeRefsPackage.INDEX_ACCESS_TYPE_REF__INDEX_TYPE_REF:
				setIndexTypeRef((TypeRef)null);
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
			case TypeRefsPackage.INDEX_ACCESS_TYPE_REF__TARGET_TYPE_REF:
				return targetTypeRef != null;
			case TypeRefsPackage.INDEX_ACCESS_TYPE_REF__INDEX_TYPE_REF:
				return indexTypeRef != null;
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
			case TypeRefsPackage.INDEX_ACCESS_TYPE_REF___INTERNAL_GET_TYPE_REF_AS_STRING:
				return internalGetTypeRefAsString();
		}
		return super.eInvoke(operationID, arguments);
	}

} //IndexAccessTypeRefImpl
