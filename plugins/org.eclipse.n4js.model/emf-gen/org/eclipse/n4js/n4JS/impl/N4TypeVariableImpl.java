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
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.TypeReferenceNode;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.TypeVariable;

import org.eclipse.n4js.ts.types.impl.IdentifiableElementImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>N4 Type Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4TypeVariableImpl#getDefinedTypeVariable <em>Defined Type Variable</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4TypeVariableImpl#isDeclaredCovariant <em>Declared Covariant</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4TypeVariableImpl#isDeclaredContravariant <em>Declared Contravariant</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4TypeVariableImpl#getDeclaredUpperBoundNode <em>Declared Upper Bound Node</em>}</li>
 * </ul>
 *
 * @generated
 */
public class N4TypeVariableImpl extends IdentifiableElementImpl implements N4TypeVariable {
	/**
	 * The cached value of the '{@link #getDefinedTypeVariable() <em>Defined Type Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinedTypeVariable()
	 * @generated
	 * @ordered
	 */
	protected TypeVariable definedTypeVariable;

	/**
	 * The default value of the '{@link #isDeclaredCovariant() <em>Declared Covariant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredCovariant()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_COVARIANT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredCovariant() <em>Declared Covariant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredCovariant()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredCovariant = DECLARED_COVARIANT_EDEFAULT;

	/**
	 * The default value of the '{@link #isDeclaredContravariant() <em>Declared Contravariant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredContravariant()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_CONTRAVARIANT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredContravariant() <em>Declared Contravariant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredContravariant()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredContravariant = DECLARED_CONTRAVARIANT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDeclaredUpperBoundNode() <em>Declared Upper Bound Node</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredUpperBoundNode()
	 * @generated
	 * @ordered
	 */
	protected TypeReferenceNode<TypeRef> declaredUpperBoundNode;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected N4TypeVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.N4_TYPE_VARIABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeVariable getDefinedTypeVariable() {
		if (definedTypeVariable != null && definedTypeVariable.eIsProxy()) {
			InternalEObject oldDefinedTypeVariable = (InternalEObject)definedTypeVariable;
			definedTypeVariable = (TypeVariable)eResolveProxy(oldDefinedTypeVariable);
			if (definedTypeVariable != oldDefinedTypeVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.N4_TYPE_VARIABLE__DEFINED_TYPE_VARIABLE, oldDefinedTypeVariable, definedTypeVariable));
			}
		}
		return definedTypeVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeVariable basicGetDefinedTypeVariable() {
		return definedTypeVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefinedTypeVariable(TypeVariable newDefinedTypeVariable) {
		TypeVariable oldDefinedTypeVariable = definedTypeVariable;
		definedTypeVariable = newDefinedTypeVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_TYPE_VARIABLE__DEFINED_TYPE_VARIABLE, oldDefinedTypeVariable, definedTypeVariable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredCovariant() {
		return declaredCovariant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredCovariant(boolean newDeclaredCovariant) {
		boolean oldDeclaredCovariant = declaredCovariant;
		declaredCovariant = newDeclaredCovariant;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_TYPE_VARIABLE__DECLARED_COVARIANT, oldDeclaredCovariant, declaredCovariant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredContravariant() {
		return declaredContravariant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredContravariant(boolean newDeclaredContravariant) {
		boolean oldDeclaredContravariant = declaredContravariant;
		declaredContravariant = newDeclaredContravariant;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_TYPE_VARIABLE__DECLARED_CONTRAVARIANT, oldDeclaredContravariant, declaredContravariant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeReferenceNode<TypeRef> getDeclaredUpperBoundNode() {
		return declaredUpperBoundNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredUpperBoundNode(TypeReferenceNode<TypeRef> newDeclaredUpperBoundNode, NotificationChain msgs) {
		TypeReferenceNode<TypeRef> oldDeclaredUpperBoundNode = declaredUpperBoundNode;
		declaredUpperBoundNode = newDeclaredUpperBoundNode;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND_NODE, oldDeclaredUpperBoundNode, newDeclaredUpperBoundNode);
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
	public void setDeclaredUpperBoundNode(TypeReferenceNode<TypeRef> newDeclaredUpperBoundNode) {
		if (newDeclaredUpperBoundNode != declaredUpperBoundNode) {
			NotificationChain msgs = null;
			if (declaredUpperBoundNode != null)
				msgs = ((InternalEObject)declaredUpperBoundNode).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND_NODE, null, msgs);
			if (newDeclaredUpperBoundNode != null)
				msgs = ((InternalEObject)newDeclaredUpperBoundNode).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND_NODE, null, msgs);
			msgs = basicSetDeclaredUpperBoundNode(newDeclaredUpperBoundNode, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND_NODE, newDeclaredUpperBoundNode, newDeclaredUpperBoundNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredUpperBound() {
		TypeReferenceNode<TypeRef> _declaredUpperBoundNode = this.getDeclaredUpperBoundNode();
		TypeRef _typeRef = null;
		if (_declaredUpperBoundNode!=null) {
			_typeRef=_declaredUpperBoundNode.getTypeRef();
		}
		return _typeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND_NODE:
				return basicSetDeclaredUpperBoundNode(null, msgs);
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
			case N4JSPackage.N4_TYPE_VARIABLE__DEFINED_TYPE_VARIABLE:
				if (resolve) return getDefinedTypeVariable();
				return basicGetDefinedTypeVariable();
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_COVARIANT:
				return isDeclaredCovariant();
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_CONTRAVARIANT:
				return isDeclaredContravariant();
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND_NODE:
				return getDeclaredUpperBoundNode();
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
			case N4JSPackage.N4_TYPE_VARIABLE__DEFINED_TYPE_VARIABLE:
				setDefinedTypeVariable((TypeVariable)newValue);
				return;
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_COVARIANT:
				setDeclaredCovariant((Boolean)newValue);
				return;
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_CONTRAVARIANT:
				setDeclaredContravariant((Boolean)newValue);
				return;
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND_NODE:
				setDeclaredUpperBoundNode((TypeReferenceNode<TypeRef>)newValue);
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
			case N4JSPackage.N4_TYPE_VARIABLE__DEFINED_TYPE_VARIABLE:
				setDefinedTypeVariable((TypeVariable)null);
				return;
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_COVARIANT:
				setDeclaredCovariant(DECLARED_COVARIANT_EDEFAULT);
				return;
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_CONTRAVARIANT:
				setDeclaredContravariant(DECLARED_CONTRAVARIANT_EDEFAULT);
				return;
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND_NODE:
				setDeclaredUpperBoundNode((TypeReferenceNode<TypeRef>)null);
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
			case N4JSPackage.N4_TYPE_VARIABLE__DEFINED_TYPE_VARIABLE:
				return definedTypeVariable != null;
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_COVARIANT:
				return declaredCovariant != DECLARED_COVARIANT_EDEFAULT;
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_CONTRAVARIANT:
				return declaredContravariant != DECLARED_CONTRAVARIANT_EDEFAULT;
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND_NODE:
				return declaredUpperBoundNode != null;
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
			case N4JSPackage.N4_TYPE_VARIABLE___GET_DECLARED_UPPER_BOUND:
				return getDeclaredUpperBound();
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
		result.append(" (declaredCovariant: ");
		result.append(declaredCovariant);
		result.append(", declaredContravariant: ");
		result.append(declaredContravariant);
		result.append(')');
		return result.toString();
	}

} //N4TypeVariableImpl
