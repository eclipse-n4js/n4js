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
package org.eclipse.n4js.ts.types.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMemberWithAccessModifier;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.n4js.ts.types.util.AccessModifiers;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TMember With Access Modifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMemberWithAccessModifierImpl#isHasNoBody <em>Has No Body</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMemberWithAccessModifierImpl#getDeclaredMemberAccessModifier <em>Declared Member Access Modifier</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class TMemberWithAccessModifierImpl extends TMemberImpl implements TMemberWithAccessModifier {
	/**
	 * The default value of the '{@link #isHasNoBody() <em>Has No Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasNoBody()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HAS_NO_BODY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHasNoBody() <em>Has No Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasNoBody()
	 * @generated
	 * @ordered
	 */
	protected boolean hasNoBody = HAS_NO_BODY_EDEFAULT;

	/**
	 * The default value of the '{@link #getDeclaredMemberAccessModifier() <em>Declared Member Access Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredMemberAccessModifier()
	 * @generated
	 * @ordered
	 */
	protected static final MemberAccessModifier DECLARED_MEMBER_ACCESS_MODIFIER_EDEFAULT = MemberAccessModifier.UNDEFINED;

	/**
	 * The cached value of the '{@link #getDeclaredMemberAccessModifier() <em>Declared Member Access Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredMemberAccessModifier()
	 * @generated
	 * @ordered
	 */
	protected MemberAccessModifier declaredMemberAccessModifier = DECLARED_MEMBER_ACCESS_MODIFIER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TMemberWithAccessModifierImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TMEMBER_WITH_ACCESS_MODIFIER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHasNoBody() {
		return hasNoBody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasNoBody(boolean newHasNoBody) {
		boolean oldHasNoBody = hasNoBody;
		hasNoBody = newHasNoBody;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMEMBER_WITH_ACCESS_MODIFIER__HAS_NO_BODY, oldHasNoBody, hasNoBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MemberAccessModifier getDeclaredMemberAccessModifier() {
		return declaredMemberAccessModifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeclaredMemberAccessModifier(MemberAccessModifier newDeclaredMemberAccessModifier) {
		MemberAccessModifier oldDeclaredMemberAccessModifier = declaredMemberAccessModifier;
		declaredMemberAccessModifier = newDeclaredMemberAccessModifier == null ? DECLARED_MEMBER_ACCESS_MODIFIER_EDEFAULT : newDeclaredMemberAccessModifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMEMBER_WITH_ACCESS_MODIFIER__DECLARED_MEMBER_ACCESS_MODIFIER, oldDeclaredMemberAccessModifier, declaredMemberAccessModifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MemberAccessModifier getMemberAccessModifier() {
		MemberAccessModifier _declaredMemberAccessModifier = this.getDeclaredMemberAccessModifier();
		boolean _tripleEquals = (_declaredMemberAccessModifier == MemberAccessModifier.UNDEFINED);
		if (_tripleEquals) {
			final EObject parent = this.eContainer();
			if ((parent instanceof TInterface)) {
				final MemberAccessModifier modifierDerivedFromType = AccessModifiers.toMemberModifier(((Type) parent).getTypeAccessModifier());
				if ((modifierDerivedFromType != MemberAccessModifier.PRIVATE)) {
					return modifierDerivedFromType;
				}
			}
			return MemberAccessModifier.PROJECT;
		}
		return this.getDeclaredMemberAccessModifier();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.TMEMBER_WITH_ACCESS_MODIFIER__HAS_NO_BODY:
				return isHasNoBody();
			case TypesPackage.TMEMBER_WITH_ACCESS_MODIFIER__DECLARED_MEMBER_ACCESS_MODIFIER:
				return getDeclaredMemberAccessModifier();
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
			case TypesPackage.TMEMBER_WITH_ACCESS_MODIFIER__HAS_NO_BODY:
				setHasNoBody((Boolean)newValue);
				return;
			case TypesPackage.TMEMBER_WITH_ACCESS_MODIFIER__DECLARED_MEMBER_ACCESS_MODIFIER:
				setDeclaredMemberAccessModifier((MemberAccessModifier)newValue);
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
			case TypesPackage.TMEMBER_WITH_ACCESS_MODIFIER__HAS_NO_BODY:
				setHasNoBody(HAS_NO_BODY_EDEFAULT);
				return;
			case TypesPackage.TMEMBER_WITH_ACCESS_MODIFIER__DECLARED_MEMBER_ACCESS_MODIFIER:
				setDeclaredMemberAccessModifier(DECLARED_MEMBER_ACCESS_MODIFIER_EDEFAULT);
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
			case TypesPackage.TMEMBER_WITH_ACCESS_MODIFIER__HAS_NO_BODY:
				return hasNoBody != HAS_NO_BODY_EDEFAULT;
			case TypesPackage.TMEMBER_WITH_ACCESS_MODIFIER__DECLARED_MEMBER_ACCESS_MODIFIER:
				return declaredMemberAccessModifier != DECLARED_MEMBER_ACCESS_MODIFIER_EDEFAULT;
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
			case TypesPackage.TMEMBER_WITH_ACCESS_MODIFIER___GET_MEMBER_ACCESS_MODIFIER:
				return getMemberAccessModifier();
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
		result.append(" (hasNoBody: ");
		result.append(hasNoBody);
		result.append(", declaredMemberAccessModifier: ");
		result.append(declaredMemberAccessModifier);
		result.append(')');
		return result.toString();
	}

} //TMemberWithAccessModifierImpl
