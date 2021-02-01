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

import org.eclipse.n4js.n4JS.GetterDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
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
 *   <li>{@link org.eclipse.n4js.n4JS.impl.GetterDeclarationImpl#getDeclaredTypeRef <em>Declared Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.GetterDeclarationImpl#getDeclaredTypeRefInAST <em>Declared Type Ref In AST</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.GetterDeclarationImpl#getDefinedGetter <em>Defined Getter</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class GetterDeclarationImpl extends FieldAccessorImpl implements GetterDeclaration {
	/**
	 * The cached value of the '{@link #getDeclaredTypeRef() <em>Declared Type Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef declaredTypeRef;

	/**
	 * The cached value of the '{@link #getDeclaredTypeRefInAST() <em>Declared Type Ref In AST</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredTypeRefInAST()
	 * @generated
	 * @ordered
	 */
	protected TypeRef declaredTypeRefInAST;

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
	public TypeRef getDeclaredTypeRef() {
		if (declaredTypeRef != null && declaredTypeRef.eIsProxy()) {
			InternalEObject oldDeclaredTypeRef = (InternalEObject)declaredTypeRef;
			declaredTypeRef = (TypeRef)eResolveProxy(oldDeclaredTypeRef);
			if (declaredTypeRef != oldDeclaredTypeRef) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF, oldDeclaredTypeRef, declaredTypeRef));
			}
		}
		return declaredTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeRef basicGetDeclaredTypeRef() {
		return declaredTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredTypeRef(TypeRef newDeclaredTypeRef) {
		TypeRef oldDeclaredTypeRef = declaredTypeRef;
		declaredTypeRef = newDeclaredTypeRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF, oldDeclaredTypeRef, declaredTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredTypeRefInAST() {
		return declaredTypeRefInAST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredTypeRefInAST(TypeRef newDeclaredTypeRefInAST, NotificationChain msgs) {
		TypeRef oldDeclaredTypeRefInAST = declaredTypeRefInAST;
		declaredTypeRefInAST = newDeclaredTypeRefInAST;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_IN_AST, oldDeclaredTypeRefInAST, newDeclaredTypeRefInAST);
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
	public void setDeclaredTypeRefInAST(TypeRef newDeclaredTypeRefInAST) {
		if (newDeclaredTypeRefInAST != declaredTypeRefInAST) {
			NotificationChain msgs = null;
			if (declaredTypeRefInAST != null)
				msgs = ((InternalEObject)declaredTypeRefInAST).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_IN_AST, null, msgs);
			if (newDeclaredTypeRefInAST != null)
				msgs = ((InternalEObject)newDeclaredTypeRefInAST).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_IN_AST, null, msgs);
			msgs = basicSetDeclaredTypeRefInAST(newDeclaredTypeRefInAST, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_IN_AST, newDeclaredTypeRefInAST, newDeclaredTypeRefInAST));
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_IN_AST:
				return basicSetDeclaredTypeRefInAST(null, msgs);
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
			case N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF:
				if (resolve) return getDeclaredTypeRef();
				return basicGetDeclaredTypeRef();
			case N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_IN_AST:
				return getDeclaredTypeRefInAST();
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
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF:
				setDeclaredTypeRef((TypeRef)newValue);
				return;
			case N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_IN_AST:
				setDeclaredTypeRefInAST((TypeRef)newValue);
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
			case N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF:
				setDeclaredTypeRef((TypeRef)null);
				return;
			case N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_IN_AST:
				setDeclaredTypeRefInAST((TypeRef)null);
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
			case N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF:
				return declaredTypeRef != null;
			case N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_IN_AST:
				return declaredTypeRefInAST != null;
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
				case N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF: return N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF;
				case N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_IN_AST: return N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF_IN_AST;
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
				case N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF: return N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF;
				case N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF_IN_AST: return N4JSPackage.GETTER_DECLARATION__DECLARED_TYPE_REF_IN_AST;
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
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case N4JSPackage.GETTER_DECLARATION___GET_DEFINED_ACCESSOR:
				return getDefinedAccessor();
		}
		return super.eInvoke(operationID, arguments);
	}

} //GetterDeclarationImpl
