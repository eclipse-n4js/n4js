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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.impl.N4MemberDeclarationImpl;

import org.eclipse.n4js.transpiler.im.DelegatingMember;
import org.eclipse.n4js.transpiler.im.ImPackage;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Delegating Member</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.DelegatingMemberImpl#getDelegationBaseType <em>Delegation Base Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.DelegatingMemberImpl#getDelegationSuperClassSteps <em>Delegation Super Class Steps</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.DelegatingMemberImpl#getDelegationTarget <em>Delegation Target</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.DelegatingMemberImpl#isDelegationTargetIsAbstract <em>Delegation Target Is Abstract</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class DelegatingMemberImpl extends N4MemberDeclarationImpl implements DelegatingMember {
	/**
	 * The cached value of the '{@link #getDelegationBaseType() <em>Delegation Base Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDelegationBaseType()
	 * @generated
	 * @ordered
	 */
	protected SymbolTableEntryOriginal delegationBaseType;

	/**
	 * The default value of the '{@link #getDelegationSuperClassSteps() <em>Delegation Super Class Steps</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDelegationSuperClassSteps()
	 * @generated
	 * @ordered
	 */
	protected static final int DELEGATION_SUPER_CLASS_STEPS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDelegationSuperClassSteps() <em>Delegation Super Class Steps</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDelegationSuperClassSteps()
	 * @generated
	 * @ordered
	 */
	protected int delegationSuperClassSteps = DELEGATION_SUPER_CLASS_STEPS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDelegationTarget() <em>Delegation Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDelegationTarget()
	 * @generated
	 * @ordered
	 */
	protected SymbolTableEntryOriginal delegationTarget;

	/**
	 * The default value of the '{@link #isDelegationTargetIsAbstract() <em>Delegation Target Is Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDelegationTargetIsAbstract()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DELEGATION_TARGET_IS_ABSTRACT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDelegationTargetIsAbstract() <em>Delegation Target Is Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDelegationTargetIsAbstract()
	 * @generated
	 * @ordered
	 */
	protected boolean delegationTargetIsAbstract = DELEGATION_TARGET_IS_ABSTRACT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DelegatingMemberImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImPackage.Literals.DELEGATING_MEMBER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SymbolTableEntryOriginal getDelegationBaseType() {
		if (delegationBaseType != null && delegationBaseType.eIsProxy()) {
			InternalEObject oldDelegationBaseType = (InternalEObject)delegationBaseType;
			delegationBaseType = (SymbolTableEntryOriginal)eResolveProxy(oldDelegationBaseType);
			if (delegationBaseType != oldDelegationBaseType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ImPackage.DELEGATING_MEMBER__DELEGATION_BASE_TYPE, oldDelegationBaseType, delegationBaseType));
			}
		}
		return delegationBaseType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbolTableEntryOriginal basicGetDelegationBaseType() {
		return delegationBaseType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDelegationBaseType(SymbolTableEntryOriginal newDelegationBaseType) {
		SymbolTableEntryOriginal oldDelegationBaseType = delegationBaseType;
		delegationBaseType = newDelegationBaseType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.DELEGATING_MEMBER__DELEGATION_BASE_TYPE, oldDelegationBaseType, delegationBaseType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getDelegationSuperClassSteps() {
		return delegationSuperClassSteps;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDelegationSuperClassSteps(int newDelegationSuperClassSteps) {
		int oldDelegationSuperClassSteps = delegationSuperClassSteps;
		delegationSuperClassSteps = newDelegationSuperClassSteps;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.DELEGATING_MEMBER__DELEGATION_SUPER_CLASS_STEPS, oldDelegationSuperClassSteps, delegationSuperClassSteps));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SymbolTableEntryOriginal getDelegationTarget() {
		if (delegationTarget != null && delegationTarget.eIsProxy()) {
			InternalEObject oldDelegationTarget = (InternalEObject)delegationTarget;
			delegationTarget = (SymbolTableEntryOriginal)eResolveProxy(oldDelegationTarget);
			if (delegationTarget != oldDelegationTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ImPackage.DELEGATING_MEMBER__DELEGATION_TARGET, oldDelegationTarget, delegationTarget));
			}
		}
		return delegationTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbolTableEntryOriginal basicGetDelegationTarget() {
		return delegationTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDelegationTarget(SymbolTableEntryOriginal newDelegationTarget) {
		SymbolTableEntryOriginal oldDelegationTarget = delegationTarget;
		delegationTarget = newDelegationTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.DELEGATING_MEMBER__DELEGATION_TARGET, oldDelegationTarget, delegationTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDelegationTargetIsAbstract() {
		return delegationTargetIsAbstract;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDelegationTargetIsAbstract(boolean newDelegationTargetIsAbstract) {
		boolean oldDelegationTargetIsAbstract = delegationTargetIsAbstract;
		delegationTargetIsAbstract = newDelegationTargetIsAbstract;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.DELEGATING_MEMBER__DELEGATION_TARGET_IS_ABSTRACT, oldDelegationTargetIsAbstract, delegationTargetIsAbstract));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImPackage.DELEGATING_MEMBER__DELEGATION_BASE_TYPE:
				if (resolve) return getDelegationBaseType();
				return basicGetDelegationBaseType();
			case ImPackage.DELEGATING_MEMBER__DELEGATION_SUPER_CLASS_STEPS:
				return getDelegationSuperClassSteps();
			case ImPackage.DELEGATING_MEMBER__DELEGATION_TARGET:
				if (resolve) return getDelegationTarget();
				return basicGetDelegationTarget();
			case ImPackage.DELEGATING_MEMBER__DELEGATION_TARGET_IS_ABSTRACT:
				return isDelegationTargetIsAbstract();
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
			case ImPackage.DELEGATING_MEMBER__DELEGATION_BASE_TYPE:
				setDelegationBaseType((SymbolTableEntryOriginal)newValue);
				return;
			case ImPackage.DELEGATING_MEMBER__DELEGATION_SUPER_CLASS_STEPS:
				setDelegationSuperClassSteps((Integer)newValue);
				return;
			case ImPackage.DELEGATING_MEMBER__DELEGATION_TARGET:
				setDelegationTarget((SymbolTableEntryOriginal)newValue);
				return;
			case ImPackage.DELEGATING_MEMBER__DELEGATION_TARGET_IS_ABSTRACT:
				setDelegationTargetIsAbstract((Boolean)newValue);
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
			case ImPackage.DELEGATING_MEMBER__DELEGATION_BASE_TYPE:
				setDelegationBaseType((SymbolTableEntryOriginal)null);
				return;
			case ImPackage.DELEGATING_MEMBER__DELEGATION_SUPER_CLASS_STEPS:
				setDelegationSuperClassSteps(DELEGATION_SUPER_CLASS_STEPS_EDEFAULT);
				return;
			case ImPackage.DELEGATING_MEMBER__DELEGATION_TARGET:
				setDelegationTarget((SymbolTableEntryOriginal)null);
				return;
			case ImPackage.DELEGATING_MEMBER__DELEGATION_TARGET_IS_ABSTRACT:
				setDelegationTargetIsAbstract(DELEGATION_TARGET_IS_ABSTRACT_EDEFAULT);
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
			case ImPackage.DELEGATING_MEMBER__DELEGATION_BASE_TYPE:
				return delegationBaseType != null;
			case ImPackage.DELEGATING_MEMBER__DELEGATION_SUPER_CLASS_STEPS:
				return delegationSuperClassSteps != DELEGATION_SUPER_CLASS_STEPS_EDEFAULT;
			case ImPackage.DELEGATING_MEMBER__DELEGATION_TARGET:
				return delegationTarget != null;
			case ImPackage.DELEGATING_MEMBER__DELEGATION_TARGET_IS_ABSTRACT:
				return delegationTargetIsAbstract != DELEGATION_TARGET_IS_ABSTRACT_EDEFAULT;
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
		result.append(" (delegationSuperClassSteps: ");
		result.append(delegationSuperClassSteps);
		result.append(", delegationTargetIsAbstract: ");
		result.append(delegationTargetIsAbstract);
		result.append(')');
		return result.toString();
	}

} //DelegatingMemberImpl
