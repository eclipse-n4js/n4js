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
import org.eclipse.n4js.n4JS.N4TypeVariable;

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
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4TypeVariableImpl#getDeclaredUpperBound <em>Declared Upper Bound</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4TypeVariableImpl#getDeclaredUpperBoundInAST <em>Declared Upper Bound In AST</em>}</li>
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
	 * The cached value of the '{@link #getDeclaredUpperBound() <em>Declared Upper Bound</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredUpperBound()
	 * @generated
	 * @ordered
	 */
	protected TypeRef declaredUpperBound;

	/**
	 * The cached value of the '{@link #getDeclaredUpperBoundInAST() <em>Declared Upper Bound In AST</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredUpperBoundInAST()
	 * @generated
	 * @ordered
	 */
	protected TypeRef declaredUpperBoundInAST;

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
	public TypeRef getDeclaredUpperBound() {
		if (declaredUpperBound != null && declaredUpperBound.eIsProxy()) {
			InternalEObject oldDeclaredUpperBound = (InternalEObject)declaredUpperBound;
			declaredUpperBound = (TypeRef)eResolveProxy(oldDeclaredUpperBound);
			if (declaredUpperBound != oldDeclaredUpperBound) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND, oldDeclaredUpperBound, declaredUpperBound));
			}
		}
		return declaredUpperBound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeRef basicGetDeclaredUpperBound() {
		return declaredUpperBound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredUpperBound(TypeRef newDeclaredUpperBound) {
		TypeRef oldDeclaredUpperBound = declaredUpperBound;
		declaredUpperBound = newDeclaredUpperBound;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND, oldDeclaredUpperBound, declaredUpperBound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredUpperBoundInAST() {
		return declaredUpperBoundInAST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredUpperBoundInAST(TypeRef newDeclaredUpperBoundInAST, NotificationChain msgs) {
		TypeRef oldDeclaredUpperBoundInAST = declaredUpperBoundInAST;
		declaredUpperBoundInAST = newDeclaredUpperBoundInAST;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND_IN_AST, oldDeclaredUpperBoundInAST, newDeclaredUpperBoundInAST);
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
	public void setDeclaredUpperBoundInAST(TypeRef newDeclaredUpperBoundInAST) {
		if (newDeclaredUpperBoundInAST != declaredUpperBoundInAST) {
			NotificationChain msgs = null;
			if (declaredUpperBoundInAST != null)
				msgs = ((InternalEObject)declaredUpperBoundInAST).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND_IN_AST, null, msgs);
			if (newDeclaredUpperBoundInAST != null)
				msgs = ((InternalEObject)newDeclaredUpperBoundInAST).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND_IN_AST, null, msgs);
			msgs = basicSetDeclaredUpperBoundInAST(newDeclaredUpperBoundInAST, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND_IN_AST, newDeclaredUpperBoundInAST, newDeclaredUpperBoundInAST));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND_IN_AST:
				return basicSetDeclaredUpperBoundInAST(null, msgs);
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
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND:
				if (resolve) return getDeclaredUpperBound();
				return basicGetDeclaredUpperBound();
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND_IN_AST:
				return getDeclaredUpperBoundInAST();
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
			case N4JSPackage.N4_TYPE_VARIABLE__DEFINED_TYPE_VARIABLE:
				setDefinedTypeVariable((TypeVariable)newValue);
				return;
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_COVARIANT:
				setDeclaredCovariant((Boolean)newValue);
				return;
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_CONTRAVARIANT:
				setDeclaredContravariant((Boolean)newValue);
				return;
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND:
				setDeclaredUpperBound((TypeRef)newValue);
				return;
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND_IN_AST:
				setDeclaredUpperBoundInAST((TypeRef)newValue);
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
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND:
				setDeclaredUpperBound((TypeRef)null);
				return;
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND_IN_AST:
				setDeclaredUpperBoundInAST((TypeRef)null);
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
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND:
				return declaredUpperBound != null;
			case N4JSPackage.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND_IN_AST:
				return declaredUpperBoundInAST != null;
		}
		return super.eIsSet(featureID);
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
