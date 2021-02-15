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

import org.eclipse.n4js.n4JS.FieldAccessor;
import org.eclipse.n4js.n4JS.GetterDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.TypeProvidingElement;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.n4JS.TypedElement;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.TGetter;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Getter Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.GetterDeclarationImpl#getDeclaredTypeRefNode <em>Declared Type Ref Node</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.GetterDeclarationImpl#getDefinedGetter <em>Defined Getter</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class GetterDeclarationImpl extends FieldAccessorImpl implements GetterDeclaration {
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
	 * The cached value of the '{@link #getDefinedGetter() <em>Defined Getter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinedGetter()
	 * @generated
	 * @ordered
	 */
	protected TGetter definedGetter;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GetterDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.GETTER_DECLARATION;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_NODE, oldDeclaredTypeRefNode, newDeclaredTypeRefNode);
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
				msgs = ((InternalEObject)declaredTypeRefNode).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_NODE, null, msgs);
			if (newDeclaredTypeRefNode != null)
				msgs = ((InternalEObject)newDeclaredTypeRefNode).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_NODE, null, msgs);
			msgs = basicSetDeclaredTypeRefNode(newDeclaredTypeRefNode, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_NODE, newDeclaredTypeRefNode, newDeclaredTypeRefNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGetter getDefinedGetter() {
		if (definedGetter != null && definedGetter.eIsProxy()) {
			InternalEObject oldDefinedGetter = (InternalEObject)definedGetter;
			definedGetter = (TGetter)eResolveProxy(oldDefinedGetter);
			if (definedGetter != oldDefinedGetter) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.GETTER_DECLARATION__DEFINED_GETTER, oldDefinedGetter, definedGetter));
			}
		}
		return definedGetter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGetter basicGetDefinedGetter() {
		return definedGetter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefinedGetter(TGetter newDefinedGetter) {
		TGetter oldDefinedGetter = definedGetter;
		definedGetter = newDefinedGetter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.GETTER_DECLARATION__DEFINED_GETTER, oldDefinedGetter, definedGetter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGetter getDefinedAccessor() {
		return this.getDefinedGetter();
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
			case N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_NODE:
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
			case N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_NODE:
				return getDeclaredTypeRefNode();
			case N4JSPackage.GETTER_DECLARATION__DEFINED_GETTER:
				if (resolve) return getDefinedGetter();
				return basicGetDefinedGetter();
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
			case N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_NODE:
				setDeclaredTypeRefNode((TypeReferenceNode<TypeRef>)newValue);
				return;
			case N4JSPackage.GETTER_DECLARATION__DEFINED_GETTER:
				setDefinedGetter((TGetter)newValue);
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
			case N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_NODE:
				setDeclaredTypeRefNode((TypeReferenceNode<TypeRef>)null);
				return;
			case N4JSPackage.GETTER_DECLARATION__DEFINED_GETTER:
				setDefinedGetter((TGetter)null);
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
			case N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_NODE:
				return declaredTypeRefNode != null;
			case N4JSPackage.GETTER_DECLARATION__DEFINED_GETTER:
				return definedGetter != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == TypedElement.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_NODE: return N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF_NODE;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == TypedElement.class) {
			switch (baseFeatureID) {
				case N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF_NODE: return N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_NODE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == TypeProvidingElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.TYPE_PROVIDING_ELEMENT___GET_DECLARED_TYPE_REF: return N4JSPackage.GETTER_DECLARATION___GET_DECLARED_TYPE_REF;
				case N4JSPackage.TYPE_PROVIDING_ELEMENT___GET_DECLARED_TYPE_REF_IN_AST: return N4JSPackage.GETTER_DECLARATION___GET_DECLARED_TYPE_REF_IN_AST;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == FieldAccessor.class) {
			switch (baseOperationID) {
				case N4JSPackage.FIELD_ACCESSOR___GET_DECLARED_TYPE_REF: return N4JSPackage.GETTER_DECLARATION___GET_DECLARED_TYPE_REF;
				case N4JSPackage.FIELD_ACCESSOR___GET_DECLARED_TYPE_REF_IN_AST: return N4JSPackage.GETTER_DECLARATION___GET_DECLARED_TYPE_REF_IN_AST;
				case N4JSPackage.FIELD_ACCESSOR___GET_DEFINED_ACCESSOR: return N4JSPackage.GETTER_DECLARATION___GET_DEFINED_ACCESSOR;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TypedElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.TYPED_ELEMENT___GET_DECLARED_TYPE_REF: return N4JSPackage.GETTER_DECLARATION___GET_DECLARED_TYPE_REF;
				case N4JSPackage.TYPED_ELEMENT___GET_DECLARED_TYPE_REF_IN_AST: return N4JSPackage.GETTER_DECLARATION___GET_DECLARED_TYPE_REF_IN_AST;
				default: return -1;
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
			case N4JSPackage.GETTER_DECLARATION___GET_DEFINED_ACCESSOR:
				return getDefinedAccessor();
			case N4JSPackage.GETTER_DECLARATION___GET_DECLARED_TYPE_REF:
				return getDeclaredTypeRef();
			case N4JSPackage.GETTER_DECLARATION___GET_DECLARED_TYPE_REF_IN_AST:
				return getDeclaredTypeRefInAST();
		}
		return super.eInvoke(operationID, arguments);
	}

} //GetterDeclarationImpl
