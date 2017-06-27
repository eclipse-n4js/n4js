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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.TypeVariableMapping;

import org.eclipse.n4js.ts.types.TypeVariable;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Variable Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.TypeVariableMappingImpl#getTypeVar <em>Type Var</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.TypeVariableMappingImpl#getTypeArg <em>Type Arg</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeVariableMappingImpl extends ProxyResolvingEObjectImpl implements TypeVariableMapping {
	/**
	 * The cached value of the '{@link #getTypeVar() <em>Type Var</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeVar()
	 * @generated
	 * @ordered
	 */
	protected TypeVariable typeVar;

	/**
	 * The cached value of the '{@link #getTypeArg() <em>Type Arg</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeArg()
	 * @generated
	 * @ordered
	 */
	protected TypeArgument typeArg;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeVariableMappingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.TYPE_VARIABLE_MAPPING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeVariable getTypeVar() {
		if (typeVar != null && typeVar.eIsProxy()) {
			InternalEObject oldTypeVar = (InternalEObject)typeVar;
			typeVar = (TypeVariable)eResolveProxy(oldTypeVar);
			if (typeVar != oldTypeVar) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypeRefsPackage.TYPE_VARIABLE_MAPPING__TYPE_VAR, oldTypeVar, typeVar));
			}
		}
		return typeVar;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeVariable basicGetTypeVar() {
		return typeVar;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypeVar(TypeVariable newTypeVar) {
		TypeVariable oldTypeVar = typeVar;
		typeVar = newTypeVar;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.TYPE_VARIABLE_MAPPING__TYPE_VAR, oldTypeVar, typeVar));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeArgument getTypeArg() {
		return typeArg;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTypeArg(TypeArgument newTypeArg, NotificationChain msgs) {
		TypeArgument oldTypeArg = typeArg;
		typeArg = newTypeArg;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypeRefsPackage.TYPE_VARIABLE_MAPPING__TYPE_ARG, oldTypeArg, newTypeArg);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypeArg(TypeArgument newTypeArg) {
		if (newTypeArg != typeArg) {
			NotificationChain msgs = null;
			if (typeArg != null)
				msgs = ((InternalEObject)typeArg).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.TYPE_VARIABLE_MAPPING__TYPE_ARG, null, msgs);
			if (newTypeArg != null)
				msgs = ((InternalEObject)newTypeArg).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.TYPE_VARIABLE_MAPPING__TYPE_ARG, null, msgs);
			msgs = basicSetTypeArg(newTypeArg, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.TYPE_VARIABLE_MAPPING__TYPE_ARG, newTypeArg, newTypeArg));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypeRefsPackage.TYPE_VARIABLE_MAPPING__TYPE_ARG:
				return basicSetTypeArg(null, msgs);
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
			case TypeRefsPackage.TYPE_VARIABLE_MAPPING__TYPE_VAR:
				if (resolve) return getTypeVar();
				return basicGetTypeVar();
			case TypeRefsPackage.TYPE_VARIABLE_MAPPING__TYPE_ARG:
				return getTypeArg();
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
			case TypeRefsPackage.TYPE_VARIABLE_MAPPING__TYPE_VAR:
				setTypeVar((TypeVariable)newValue);
				return;
			case TypeRefsPackage.TYPE_VARIABLE_MAPPING__TYPE_ARG:
				setTypeArg((TypeArgument)newValue);
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
			case TypeRefsPackage.TYPE_VARIABLE_MAPPING__TYPE_VAR:
				setTypeVar((TypeVariable)null);
				return;
			case TypeRefsPackage.TYPE_VARIABLE_MAPPING__TYPE_ARG:
				setTypeArg((TypeArgument)null);
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
			case TypeRefsPackage.TYPE_VARIABLE_MAPPING__TYPE_VAR:
				return typeVar != null;
			case TypeRefsPackage.TYPE_VARIABLE_MAPPING__TYPE_ARG:
				return typeArg != null;
		}
		return super.eIsSet(featureID);
	}

} //TypeVariableMappingImpl
