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
import org.eclipse.n4js.n4JS.TypePredicateDeclaration;
import org.eclipse.n4js.n4JS.TypeReferenceNode;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.IdentifiableElement;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Predicate Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.TypePredicateDeclarationImpl#isReferringToThis <em>Referring To This</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.TypePredicateDeclarationImpl#getFpar <em>Fpar</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.TypePredicateDeclarationImpl#getTypeRefNode <em>Type Ref Node</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypePredicateDeclarationImpl extends ProxyResolvingEObjectImpl implements TypePredicateDeclaration {
	/**
	 * The default value of the '{@link #isReferringToThis() <em>Referring To This</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReferringToThis()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REFERRING_TO_THIS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReferringToThis() <em>Referring To This</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReferringToThis()
	 * @generated
	 * @ordered
	 */
	protected boolean referringToThis = REFERRING_TO_THIS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFpar() <em>Fpar</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFpar()
	 * @generated
	 * @ordered
	 */
	protected IdentifiableElement fpar;

	/**
	 * The cached value of the '{@link #getTypeRefNode() <em>Type Ref Node</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeRefNode()
	 * @generated
	 * @ordered
	 */
	protected TypeReferenceNode<TypeRef> typeRefNode;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypePredicateDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.TYPE_PREDICATE_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isReferringToThis() {
		return referringToThis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReferringToThis(boolean newReferringToThis) {
		boolean oldReferringToThis = referringToThis;
		referringToThis = newReferringToThis;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.TYPE_PREDICATE_DECLARATION__REFERRING_TO_THIS, oldReferringToThis, referringToThis));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IdentifiableElement getFpar() {
		if (fpar != null && fpar.eIsProxy()) {
			InternalEObject oldFpar = (InternalEObject)fpar;
			fpar = (IdentifiableElement)eResolveProxy(oldFpar);
			if (fpar != oldFpar) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.TYPE_PREDICATE_DECLARATION__FPAR, oldFpar, fpar));
			}
		}
		return fpar;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IdentifiableElement basicGetFpar() {
		return fpar;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFpar(IdentifiableElement newFpar) {
		IdentifiableElement oldFpar = fpar;
		fpar = newFpar;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.TYPE_PREDICATE_DECLARATION__FPAR, oldFpar, fpar));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeReferenceNode<TypeRef> getTypeRefNode() {
		return typeRefNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTypeRefNode(TypeReferenceNode<TypeRef> newTypeRefNode, NotificationChain msgs) {
		TypeReferenceNode<TypeRef> oldTypeRefNode = typeRefNode;
		typeRefNode = newTypeRefNode;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.TYPE_PREDICATE_DECLARATION__TYPE_REF_NODE, oldTypeRefNode, newTypeRefNode);
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
	public void setTypeRefNode(TypeReferenceNode<TypeRef> newTypeRefNode) {
		if (newTypeRefNode != typeRefNode) {
			NotificationChain msgs = null;
			if (typeRefNode != null)
				msgs = ((InternalEObject)typeRefNode).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.TYPE_PREDICATE_DECLARATION__TYPE_REF_NODE, null, msgs);
			if (newTypeRefNode != null)
				msgs = ((InternalEObject)newTypeRefNode).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.TYPE_PREDICATE_DECLARATION__TYPE_REF_NODE, null, msgs);
			msgs = basicSetTypeRefNode(newTypeRefNode, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.TYPE_PREDICATE_DECLARATION__TYPE_REF_NODE, newTypeRefNode, newTypeRefNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getTypeRef() {
		TypeReferenceNode<TypeRef> _typeRefNode = this.getTypeRefNode();
		TypeRef _typeRef = null;
		if (_typeRefNode!=null) {
			_typeRef=_typeRefNode.getTypeRef();
		}
		return _typeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getTypeRefInAST() {
		TypeReferenceNode<TypeRef> _typeRefNode = this.getTypeRefNode();
		TypeRef _typeRefInAST = null;
		if (_typeRefNode!=null) {
			_typeRefInAST=_typeRefNode.getTypeRefInAST();
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
			case N4JSPackage.TYPE_PREDICATE_DECLARATION__TYPE_REF_NODE:
				return basicSetTypeRefNode(null, msgs);
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
			case N4JSPackage.TYPE_PREDICATE_DECLARATION__REFERRING_TO_THIS:
				return isReferringToThis();
			case N4JSPackage.TYPE_PREDICATE_DECLARATION__FPAR:
				if (resolve) return getFpar();
				return basicGetFpar();
			case N4JSPackage.TYPE_PREDICATE_DECLARATION__TYPE_REF_NODE:
				return getTypeRefNode();
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
			case N4JSPackage.TYPE_PREDICATE_DECLARATION__REFERRING_TO_THIS:
				setReferringToThis((Boolean)newValue);
				return;
			case N4JSPackage.TYPE_PREDICATE_DECLARATION__FPAR:
				setFpar((IdentifiableElement)newValue);
				return;
			case N4JSPackage.TYPE_PREDICATE_DECLARATION__TYPE_REF_NODE:
				setTypeRefNode((TypeReferenceNode<TypeRef>)newValue);
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
			case N4JSPackage.TYPE_PREDICATE_DECLARATION__REFERRING_TO_THIS:
				setReferringToThis(REFERRING_TO_THIS_EDEFAULT);
				return;
			case N4JSPackage.TYPE_PREDICATE_DECLARATION__FPAR:
				setFpar((IdentifiableElement)null);
				return;
			case N4JSPackage.TYPE_PREDICATE_DECLARATION__TYPE_REF_NODE:
				setTypeRefNode((TypeReferenceNode<TypeRef>)null);
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
			case N4JSPackage.TYPE_PREDICATE_DECLARATION__REFERRING_TO_THIS:
				return referringToThis != REFERRING_TO_THIS_EDEFAULT;
			case N4JSPackage.TYPE_PREDICATE_DECLARATION__FPAR:
				return fpar != null;
			case N4JSPackage.TYPE_PREDICATE_DECLARATION__TYPE_REF_NODE:
				return typeRefNode != null;
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
			case N4JSPackage.TYPE_PREDICATE_DECLARATION___GET_TYPE_REF:
				return getTypeRef();
			case N4JSPackage.TYPE_PREDICATE_DECLARATION___GET_TYPE_REF_IN_AST:
				return getTypeRefInAST();
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
		result.append(" (referringToThis: ");
		result.append(referringToThis);
		result.append(')');
		return result.toString();
	}

} //TypePredicateDeclarationImpl
