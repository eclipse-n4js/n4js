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
package org.eclipse.n4js.transpiler.im.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.transpiler.im.ImPackage;
import org.eclipse.n4js.transpiler.im.ParameterizedTypeRef_IM;
import org.eclipse.n4js.transpiler.im.ReferencingElement_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;

import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;

import org.eclipse.n4js.ts.typeRefs.impl.ParameterizedTypeRefImpl;

import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.Type;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parameterized Type Ref IM</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.ParameterizedTypeRef_IMImpl#getRewiredTarget <em>Rewired Target</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParameterizedTypeRef_IMImpl extends ParameterizedTypeRefImpl implements ParameterizedTypeRef_IM {
	/**
	 * The cached value of the '{@link #getRewiredTarget() <em>Rewired Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRewiredTarget()
	 * @generated
	 * @ordered
	 */
	protected SymbolTableEntry rewiredTarget;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParameterizedTypeRef_IMImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImPackage.Literals.PARAMETERIZED_TYPE_REF_IM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SymbolTableEntry getRewiredTarget() {
		if (rewiredTarget != null && rewiredTarget.eIsProxy()) {
			InternalEObject oldRewiredTarget = (InternalEObject)rewiredTarget;
			rewiredTarget = (SymbolTableEntry)eResolveProxy(oldRewiredTarget);
			if (rewiredTarget != oldRewiredTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ImPackage.PARAMETERIZED_TYPE_REF_IM__REWIRED_TARGET, oldRewiredTarget, rewiredTarget));
			}
		}
		return rewiredTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbolTableEntry basicGetRewiredTarget() {
		return rewiredTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRewiredTarget(SymbolTableEntry newRewiredTarget, NotificationChain msgs) {
		SymbolTableEntry oldRewiredTarget = rewiredTarget;
		rewiredTarget = newRewiredTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ImPackage.PARAMETERIZED_TYPE_REF_IM__REWIRED_TARGET, oldRewiredTarget, newRewiredTarget);
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
	public void setRewiredTarget(SymbolTableEntry newRewiredTarget) {
		if (newRewiredTarget != rewiredTarget) {
			NotificationChain msgs = null;
			if (rewiredTarget != null)
				msgs = ((InternalEObject)rewiredTarget).eInverseRemove(this, ImPackage.SYMBOL_TABLE_ENTRY__REFERENCING_ELEMENTS, SymbolTableEntry.class, msgs);
			if (newRewiredTarget != null)
				msgs = ((InternalEObject)newRewiredTarget).eInverseAdd(this, ImPackage.SYMBOL_TABLE_ENTRY__REFERENCING_ELEMENTS, SymbolTableEntry.class, msgs);
			msgs = basicSetRewiredTarget(newRewiredTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.PARAMETERIZED_TYPE_REF_IM__REWIRED_TARGET, newRewiredTarget, newRewiredTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SymbolTableEntry getDeclaredType_IM() {
		return this.getRewiredTarget();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredType_IM(final SymbolTableEntry target) {
		this.setRewiredTarget(target);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Type getDeclaredType() {
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredType(final Type ix) {
		if ((ix != null)) {
			throw new IllegalArgumentException("ParameterizedTypeRef_IM cannot accept types. Use #declaredType_IM.");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IdentifiableElement getOriginalTargetOfRewiredTarget() {
		final SymbolTableEntry declaredTypeSTE = this.getRewiredTarget();
		if ((declaredTypeSTE instanceof SymbolTableEntryOriginal)) {
			return ((SymbolTableEntryOriginal)declaredTypeSTE).getOriginalTarget();
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImPackage.PARAMETERIZED_TYPE_REF_IM__REWIRED_TARGET:
				if (rewiredTarget != null)
					msgs = ((InternalEObject)rewiredTarget).eInverseRemove(this, ImPackage.SYMBOL_TABLE_ENTRY__REFERENCING_ELEMENTS, SymbolTableEntry.class, msgs);
				return basicSetRewiredTarget((SymbolTableEntry)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImPackage.PARAMETERIZED_TYPE_REF_IM__REWIRED_TARGET:
				return basicSetRewiredTarget(null, msgs);
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
			case ImPackage.PARAMETERIZED_TYPE_REF_IM__REWIRED_TARGET:
				if (resolve) return getRewiredTarget();
				return basicGetRewiredTarget();
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
			case ImPackage.PARAMETERIZED_TYPE_REF_IM__REWIRED_TARGET:
				setRewiredTarget((SymbolTableEntry)newValue);
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
			case ImPackage.PARAMETERIZED_TYPE_REF_IM__REWIRED_TARGET:
				setRewiredTarget((SymbolTableEntry)null);
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
			case ImPackage.PARAMETERIZED_TYPE_REF_IM__REWIRED_TARGET:
				return rewiredTarget != null;
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
		if (baseClass == ReferencingElement_IM.class) {
			switch (derivedFeatureID) {
				case ImPackage.PARAMETERIZED_TYPE_REF_IM__REWIRED_TARGET: return ImPackage.REFERENCING_ELEMENT_IM__REWIRED_TARGET;
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
		if (baseClass == ReferencingElement_IM.class) {
			switch (baseFeatureID) {
				case ImPackage.REFERENCING_ELEMENT_IM__REWIRED_TARGET: return ImPackage.PARAMETERIZED_TYPE_REF_IM__REWIRED_TARGET;
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
		if (baseClass == TypeArgument.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_ARGUMENT___GET_DECLARED_TYPE: return ImPackage.PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_TYPE;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == ReferencingElement_IM.class) {
			switch (baseOperationID) {
				case ImPackage.REFERENCING_ELEMENT_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET: return ImPackage.PARAMETERIZED_TYPE_REF_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET;
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
			case ImPackage.PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_TYPE_IM:
				return getDeclaredType_IM();
			case ImPackage.PARAMETERIZED_TYPE_REF_IM___SET_DECLARED_TYPE_IM__SYMBOLTABLEENTRY:
				setDeclaredType_IM((SymbolTableEntry)arguments.get(0));
				return null;
			case ImPackage.PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_TYPE:
				return getDeclaredType();
			case ImPackage.PARAMETERIZED_TYPE_REF_IM___SET_DECLARED_TYPE__TYPE:
				setDeclaredType((Type)arguments.get(0));
				return null;
			case ImPackage.PARAMETERIZED_TYPE_REF_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET:
				return getOriginalTargetOfRewiredTarget();
		}
		return super.eInvoke(operationID, arguments);
	}

} //ParameterizedTypeRef_IMImpl
