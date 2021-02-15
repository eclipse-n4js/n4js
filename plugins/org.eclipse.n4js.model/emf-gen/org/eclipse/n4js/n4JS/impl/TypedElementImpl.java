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
 *   <li>{@link org.eclipse.n4js.n4JS.impl.TypedElementImpl#getDeclaredTypeRefNode <em>Declared Type Ref Node</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class TypedElementImpl extends ProxyResolvingEObjectImpl implements TypedElement {
	/**
	 * The cached value of the '{@link #getDeclaredTypeRefNode() <em>Declared Type Ref Node</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredTypeRefNode()
	 * @generated
	 * @ordered
	 */
	protected TypeReferenceNode<TypeRef> declaredTypeRefNode;

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
	public TypeReferenceNode<TypeRef> getDeclaredTypeRefNode() {
		return declaredTypeRefNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredTypeRefNode(TypeReferenceNode<TypeRef> newDeclaredTypeRefNode, NotificationChain msgs) {
		TypeReferenceNode<TypeRef> oldDeclaredTypeRefNode = declaredTypeRefNode;
		declaredTypeRefNode = newDeclaredTypeRefNode;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF_NODE, oldDeclaredTypeRefNode, newDeclaredTypeRefNode);
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
	public void setDeclaredTypeRefNode(TypeReferenceNode<TypeRef> newDeclaredTypeRefNode) {
		if (newDeclaredTypeRefNode != declaredTypeRefNode) {
			NotificationChain msgs = null;
			if (declaredTypeRefNode != null)
				msgs = ((InternalEObject)declaredTypeRefNode).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF_NODE, null, msgs);
			if (newDeclaredTypeRefNode != null)
				msgs = ((InternalEObject)newDeclaredTypeRefNode).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF_NODE, null, msgs);
			msgs = basicSetDeclaredTypeRefNode(newDeclaredTypeRefNode, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF_NODE, newDeclaredTypeRefNode, newDeclaredTypeRefNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredTypeRef() {
		TypeReferenceNode<TypeRef> _declaredTypeRefNode = this.getDeclaredTypeRefNode();
		TypeRef _typeRef = null;
		if (_declaredTypeRefNode!=null) {
			_typeRef=_declaredTypeRefNode.getTypeRef();
		}
		return _typeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredTypeRefInAST() {
		TypeReferenceNode<TypeRef> _declaredTypeRefNode = this.getDeclaredTypeRefNode();
		TypeRef _typeRefInAST = null;
		if (_declaredTypeRefNode!=null) {
			_typeRefInAST=_declaredTypeRefNode.getTypeRefInAST();
		}
		return _typeRefInAST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF_NODE:
				return basicSetDeclaredTypeRefNode(null, msgs);
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
			case N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF_NODE:
				return getDeclaredTypeRefNode();
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
			case N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF_NODE:
				setDeclaredTypeRefNode((TypeReferenceNode<TypeRef>)newValue);
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
			case N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF_NODE:
				setDeclaredTypeRefNode((TypeReferenceNode<TypeRef>)null);
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
			case N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF_NODE:
				return declaredTypeRefNode != null;
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
			case N4JSPackage.TYPED_ELEMENT___GET_DECLARED_TYPE_REF:
				return getDeclaredTypeRef();
			case N4JSPackage.TYPED_ELEMENT___GET_DECLARED_TYPE_REF_IN_AST:
				return getDeclaredTypeRefInAST();
		}
		return super.eInvoke(operationID, arguments);
	}

} //TypedElementImpl
