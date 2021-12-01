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
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4IndexSignatureDeclarationImpl#getKeyName <em>Key Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4IndexSignatureDeclarationImpl#getDeclaredKeyTypeRefNode <em>Declared Key Type Ref Node</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4IndexSignatureDeclarationImpl#getDeclaredValueTypeRefNode <em>Declared Value Type Ref Node</em>}</li>
 * </ul>
 *
 * @generated
 */
public class N4IndexSignatureDeclarationImpl extends AnnotableN4MemberDeclarationImpl implements N4IndexSignatureDeclaration {
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
	 * The cached value of the '{@link #getDeclaredKeyTypeRefNode() <em>Declared Key Type Ref Node</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredKeyTypeRefNode()
	 * @generated
	 * @ordered
	 */
	protected TypeReferenceNode<TypeRef> declaredKeyTypeRefNode;

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
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__KEY_NAME, oldKeyName, keyName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeReferenceNode<TypeRef> getDeclaredKeyTypeRefNode() {
		return declaredKeyTypeRefNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredKeyTypeRefNode(TypeReferenceNode<TypeRef> newDeclaredKeyTypeRefNode, NotificationChain msgs) {
		TypeReferenceNode<TypeRef> oldDeclaredKeyTypeRefNode = declaredKeyTypeRefNode;
		declaredKeyTypeRefNode = newDeclaredKeyTypeRefNode;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_KEY_TYPE_REF_NODE, oldDeclaredKeyTypeRefNode, newDeclaredKeyTypeRefNode);
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
	public void setDeclaredKeyTypeRefNode(TypeReferenceNode<TypeRef> newDeclaredKeyTypeRefNode) {
		if (newDeclaredKeyTypeRefNode != declaredKeyTypeRefNode) {
			NotificationChain msgs = null;
			if (declaredKeyTypeRefNode != null)
				msgs = ((InternalEObject)declaredKeyTypeRefNode).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_KEY_TYPE_REF_NODE, null, msgs);
			if (newDeclaredKeyTypeRefNode != null)
				msgs = ((InternalEObject)newDeclaredKeyTypeRefNode).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_KEY_TYPE_REF_NODE, null, msgs);
			msgs = basicSetDeclaredKeyTypeRefNode(newDeclaredKeyTypeRefNode, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_KEY_TYPE_REF_NODE, newDeclaredKeyTypeRefNode, newDeclaredKeyTypeRefNode));
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
	public TypeRef getDeclaredKeyTypeRef() {
		TypeReferenceNode<TypeRef> _declaredKeyTypeRefNode = this.getDeclaredKeyTypeRefNode();
		TypeRef _typeRef = null;
		if (_declaredKeyTypeRefNode!=null) {
			_typeRef=_declaredKeyTypeRefNode.getTypeRef();
		}
		return _typeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredKeyTypeRefInAST() {
		TypeReferenceNode<TypeRef> _declaredKeyTypeRefNode = this.getDeclaredKeyTypeRefNode();
		TypeRef _typeRefInAST = null;
		if (_declaredKeyTypeRefNode!=null) {
			_typeRefInAST=_declaredKeyTypeRefNode.getTypeRefInAST();
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
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_KEY_TYPE_REF_NODE:
				return basicSetDeclaredKeyTypeRefNode(null, msgs);
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
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__KEY_NAME:
				return getKeyName();
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_KEY_TYPE_REF_NODE:
				return getDeclaredKeyTypeRefNode();
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
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__KEY_NAME:
				setKeyName((String)newValue);
				return;
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_KEY_TYPE_REF_NODE:
				setDeclaredKeyTypeRefNode((TypeReferenceNode<TypeRef>)newValue);
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
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__KEY_NAME:
				setKeyName(KEY_NAME_EDEFAULT);
				return;
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_KEY_TYPE_REF_NODE:
				setDeclaredKeyTypeRefNode((TypeReferenceNode<TypeRef>)null);
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
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__KEY_NAME:
				return KEY_NAME_EDEFAULT == null ? keyName != null : !KEY_NAME_EDEFAULT.equals(keyName);
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION__DECLARED_KEY_TYPE_REF_NODE:
				return declaredKeyTypeRefNode != null;
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
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION___GET_DECLARED_KEY_TYPE_REF:
				return getDeclaredKeyTypeRef();
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION___GET_DECLARED_KEY_TYPE_REF_IN_AST:
				return getDeclaredKeyTypeRefInAST();
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION___GET_DECLARED_VALUE_TYPE_REF:
				return getDeclaredValueTypeRef();
			case N4JSPackage.N4_INDEX_SIGNATURE_DECLARATION___GET_DECLARED_VALUE_TYPE_REF_IN_AST:
				return getDeclaredValueTypeRefInAST();
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
		result.append(" (keyName: ");
		result.append(keyName);
		result.append(')');
		return result.toString();
	}

} //N4IndexSignatureDeclarationImpl
