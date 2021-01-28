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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.n4JS.TypeProvidingElement;
import org.eclipse.n4js.n4JS.TypedElement;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAlias;
import org.eclipse.n4js.ts.types.TypeVariable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>N4 Type Alias Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4TypeAliasDeclarationImpl#getTypeVars <em>Type Vars</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4TypeAliasDeclarationImpl#getDeclaredTypeRef <em>Declared Type Ref</em>}</li>
 * </ul>
 *
 * @generated
 */
public class N4TypeAliasDeclarationImpl extends N4TypeDeclarationImpl implements N4TypeAliasDeclaration {
	/**
	 * The cached value of the '{@link #getTypeVars() <em>Type Vars</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeVars()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeVariable> typeVars;

	/**
	 * The cached value of the '{@link #getDeclaredTypeRef() <em>Declared Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef declaredTypeRef;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected N4TypeAliasDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.N4_TYPE_ALIAS_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypeVariable> getTypeVars() {
		if (typeVars == null) {
			typeVars = new EObjectContainmentEList<TypeVariable>(TypeVariable.class, this, N4JSPackage.N4_TYPE_ALIAS_DECLARATION__TYPE_VARS);
		}
		return typeVars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredTypeRef() {
		return declaredTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredTypeRef(TypeRef newDeclaredTypeRef, NotificationChain msgs) {
		TypeRef oldDeclaredTypeRef = declaredTypeRef;
		declaredTypeRef = newDeclaredTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_TYPE_ALIAS_DECLARATION__DECLARED_TYPE_REF, oldDeclaredTypeRef, newDeclaredTypeRef);
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
	public void setDeclaredTypeRef(TypeRef newDeclaredTypeRef) {
		if (newDeclaredTypeRef != declaredTypeRef) {
			NotificationChain msgs = null;
			if (declaredTypeRef != null)
				msgs = ((InternalEObject)declaredTypeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_TYPE_ALIAS_DECLARATION__DECLARED_TYPE_REF, null, msgs);
			if (newDeclaredTypeRef != null)
				msgs = ((InternalEObject)newDeclaredTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_TYPE_ALIAS_DECLARATION__DECLARED_TYPE_REF, null, msgs);
			msgs = basicSetDeclaredTypeRef(newDeclaredTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_TYPE_ALIAS_DECLARATION__DECLARED_TYPE_REF, newDeclaredTypeRef, newDeclaredTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeAlias getDefinedTypeAsTypeAlias() {
		Type _definedType = this.getDefinedType();
		return ((TypeAlias) _definedType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.N4_TYPE_ALIAS_DECLARATION__TYPE_VARS:
				return ((InternalEList<?>)getTypeVars()).basicRemove(otherEnd, msgs);
			case N4JSPackage.N4_TYPE_ALIAS_DECLARATION__DECLARED_TYPE_REF:
				return basicSetDeclaredTypeRef(null, msgs);
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
			case N4JSPackage.N4_TYPE_ALIAS_DECLARATION__TYPE_VARS:
				return getTypeVars();
			case N4JSPackage.N4_TYPE_ALIAS_DECLARATION__DECLARED_TYPE_REF:
				return getDeclaredTypeRef();
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
			case N4JSPackage.N4_TYPE_ALIAS_DECLARATION__TYPE_VARS:
				getTypeVars().clear();
				getTypeVars().addAll((Collection<? extends TypeVariable>)newValue);
				return;
			case N4JSPackage.N4_TYPE_ALIAS_DECLARATION__DECLARED_TYPE_REF:
				setDeclaredTypeRef((TypeRef)newValue);
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
			case N4JSPackage.N4_TYPE_ALIAS_DECLARATION__TYPE_VARS:
				getTypeVars().clear();
				return;
			case N4JSPackage.N4_TYPE_ALIAS_DECLARATION__DECLARED_TYPE_REF:
				setDeclaredTypeRef((TypeRef)null);
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
			case N4JSPackage.N4_TYPE_ALIAS_DECLARATION__TYPE_VARS:
				return typeVars != null && !typeVars.isEmpty();
			case N4JSPackage.N4_TYPE_ALIAS_DECLARATION__DECLARED_TYPE_REF:
				return declaredTypeRef != null;
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
		if (baseClass == GenericDeclaration.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.N4_TYPE_ALIAS_DECLARATION__TYPE_VARS: return N4JSPackage.GENERIC_DECLARATION__TYPE_VARS;
				default: return -1;
			}
		}
		if (baseClass == TypeProvidingElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == TypedElement.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.N4_TYPE_ALIAS_DECLARATION__DECLARED_TYPE_REF: return N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF;
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
		if (baseClass == GenericDeclaration.class) {
			switch (baseFeatureID) {
				case N4JSPackage.GENERIC_DECLARATION__TYPE_VARS: return N4JSPackage.N4_TYPE_ALIAS_DECLARATION__TYPE_VARS;
				default: return -1;
			}
		}
		if (baseClass == TypeProvidingElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == TypedElement.class) {
			switch (baseFeatureID) {
				case N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF: return N4JSPackage.N4_TYPE_ALIAS_DECLARATION__DECLARED_TYPE_REF;
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
			case N4JSPackage.N4_TYPE_ALIAS_DECLARATION___GET_DEFINED_TYPE_AS_TYPE_ALIAS:
				return getDefinedTypeAsTypeAlias();
		}
		return super.eInvoke(operationID, arguments);
	}

} //N4TypeAliasDeclarationImpl
