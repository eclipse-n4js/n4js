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

import org.eclipse.n4js.n4JS.N4IndexSignatureDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.TypeReferenceNode;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>N4 Index Signature Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4IndexSignatureDeclarationImpl#getDeclaredIndexTypeRefNode <em>Declared Index Type Ref Node</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4IndexSignatureDeclarationImpl#getDeclaredValueTypeRefNode <em>Declared Value Type Ref Node</em>}</li>
 * </ul>
 *
 * @generated
 */
public class N4IndexSignatureDeclarationImpl extends AnnotableN4MemberDeclarationImpl implements N4IndexSignatureDeclaration {
	/**
	 * The cached value of the '{@link #getDeclaredIndexTypeRefNode() <em>Declared Index Type Ref Node</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredIndexTypeRefNode()
	 * @generated
	 * @ordered
	 */
	protected TypeReferenceNode<TypeRef> declaredIndexTypeRefNode;

	/**
	 * The cached value of the '{@link #getDeclaredValueTypeRefNode() <em>Declared Value Type Ref Node</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredValueTypeRefNode()
	 * @generated
	 * @ordered
	 */
	protected TypeReferenceNode<TypeRef> declaredValueTypeRefNode;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected N4IndexSignatureDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.N4_INDEX_SIGNATURE_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeReferenceNode<TypeRef> getDeclaredIndexTypeRefNode() {
		return declaredIndexTypeRefNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredIndexTypeRefNode(TypeReferenceNode<TypeRef> newDeclaredIndexTypeRefNode, NotificationChain msgs) {
		TypeReferenceNode<TypeRef> oldDeclaredIndexTypeRefNode = declaredIndexTypeRefNode;
		declaredIndexTypeRefNode = newDeclaredIndexTypeRefNode;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_INDEX_TYPE_REF_NODE, oldDeclaredIndexTypeRefNode, newDeclaredIndexTypeRefNode);
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
	public void setDeclaredIndexTypeRefNode(TypeReferenceNode<TypeRef> newDeclaredIndexTypeRefNode) {
		if (newDeclaredIndexTypeRefNode != declaredIndexTypeRefNode) {
			NotificationChain msgs = null;
			if (declaredIndexTypeRefNode != null)
				msgs = ((InternalEObject)declaredIndexTypeRefNode).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_INDEX_TYPE_REF_NODE, null, msgs);
			if (newDeclaredIndexTypeRefNode != null)
				msgs = ((InternalEObject)newDeclaredIndexTypeRefNode).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_INDEX_TYPE_REF_NODE, null, msgs);
			msgs = basicSetDeclaredIndexTypeRefNode(newDeclaredIndexTypeRefNode, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_INDEX_TYPE_REF_NODE, newDeclaredIndexTypeRefNode, newDeclaredIndexTypeRefNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeReferenceNode<TypeRef> getDeclaredValueTypeRefNode() {
		return declaredValueTypeRefNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredValueTypeRefNode(TypeReferenceNode<TypeRef> newDeclaredValueTypeRefNode, NotificationChain msgs) {
		TypeReferenceNode<TypeRef> oldDeclaredValueTypeRefNode = declaredValueTypeRefNode;
		declaredValueTypeRefNode = newDeclaredValueTypeRefNode;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_VALUE_TYPE_REF_NODE, oldDeclaredValueTypeRefNode, newDeclaredValueTypeRefNode);
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
	public void setDeclaredValueTypeRefNode(TypeReferenceNode<TypeRef> newDeclaredValueTypeRefNode) {
		if (newDeclaredValueTypeRefNode != declaredValueTypeRefNode) {
			NotificationChain msgs = null;
			if (declaredValueTypeRefNode != null)
				msgs = ((InternalEObject)declaredValueTypeRefNode).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_VALUE_TYPE_REF_NODE, null, msgs);
			if (newDeclaredValueTypeRefNode != null)
				msgs = ((InternalEObject)newDeclaredValueTypeRefNode).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_VALUE_TYPE_REF_NODE, null, msgs);
			msgs = basicSetDeclaredValueTypeRefNode(newDeclaredValueTypeRefNode, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_VALUE_TYPE_REF_NODE, newDeclaredValueTypeRefNode, newDeclaredValueTypeRefNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredIndexTypeRef() {
		TypeReferenceNode<TypeRef> _declaredIndexTypeRefNode = this.getDeclaredIndexTypeRefNode();
		TypeRef _typeRef = null;
		if (_declaredIndexTypeRefNode!=null) {
			_typeRef=_declaredIndexTypeRefNode.getTypeRef();
		}
		return _typeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredIndexTypeRefInAST() {
		TypeReferenceNode<TypeRef> _declaredIndexTypeRefNode = this.getDeclaredIndexTypeRefNode();
		TypeRef _typeRefInAST = null;
		if (_declaredIndexTypeRefNode!=null) {
			_typeRefInAST=_declaredIndexTypeRefNode.getTypeRefInAST();
		}
		return _typeRefInAST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredValueTypeRef() {
		TypeReferenceNode<TypeRef> _declaredValueTypeRefNode = this.getDeclaredValueTypeRefNode();
		TypeRef _typeRef = null;
		if (_declaredValueTypeRefNode!=null) {
			_typeRef=_declaredValueTypeRefNode.getTypeRef();
		}
		return _typeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredValueTypeRefInAST() {
		TypeReferenceNode<TypeRef> _declaredValueTypeRefNode = this.getDeclaredValueTypeRefNode();
		TypeRef _typeRefInAST = null;
		if (_declaredValueTypeRefNode!=null) {
			_typeRefInAST=_declaredValueTypeRefNode.getTypeRefInAST();
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
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_INDEX_TYPE_REF_NODE:
				return basicSetDeclaredIndexTypeRefNode(null, msgs);
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_VALUE_TYPE_REF_NODE:
				return basicSetDeclaredValueTypeRefNode(null, msgs);
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
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_INDEX_TYPE_REF_NODE:
				return getDeclaredIndexTypeRefNode();
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_VALUE_TYPE_REF_NODE:
				return getDeclaredValueTypeRefNode();
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
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_INDEX_TYPE_REF_NODE:
				setDeclaredIndexTypeRefNode((TypeReferenceNode<TypeRef>)newValue);
				return;
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_VALUE_TYPE_REF_NODE:
				setDeclaredValueTypeRefNode((TypeReferenceNode<TypeRef>)newValue);
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
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_INDEX_TYPE_REF_NODE:
				setDeclaredIndexTypeRefNode((TypeReferenceNode<TypeRef>)null);
				return;
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_VALUE_TYPE_REF_NODE:
				setDeclaredValueTypeRefNode((TypeReferenceNode<TypeRef>)null);
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
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_INDEX_TYPE_REF_NODE:
				return declaredIndexTypeRefNode != null;
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_VALUE_TYPE_REF_NODE:
				return declaredValueTypeRefNode != null;
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
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION___GET_DECLARED_INDEX_TYPE_REF:
				return getDeclaredIndexTypeRef();
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION___GET_DECLARED_INDEX_TYPE_REF_IN_AST:
				return getDeclaredIndexTypeRefInAST();
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION___GET_DECLARED_VALUE_TYPE_REF:
				return getDeclaredValueTypeRef();
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION___GET_DECLARED_VALUE_TYPE_REF_IN_AST:
				return getDeclaredValueTypeRefInAST();
		}
		return super.eInvoke(operationID, arguments);
	}

} //N4IndexSignatureDeclarationImpl
