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

import com.google.common.base.Objects;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.types.ExpressionInTypeRef;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMemberWithAccessModifier;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructMethod;
import org.eclipse.n4js.ts.types.TStructSetter;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TStruct Setter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TStructSetterImpl#getDefinedMember <em>Defined Member</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TStructSetterImpl#getDtsComputedNameExpression <em>Dts Computed Name Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TStructSetterImpl extends TSetterImpl implements TStructSetter {
	/**
	 * The cached value of the '{@link #getDefinedMember() <em>Defined Member</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinedMember()
	 * @generated
	 * @ordered
	 */
	protected TStructMember definedMember;

	/**
	 * The cached value of the '{@link #getDtsComputedNameExpression() <em>Dts Computed Name Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDtsComputedNameExpression()
	 * @generated
	 * @ordered
	 */
	protected ExpressionInTypeRef dtsComputedNameExpression;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TStructSetterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TSTRUCT_SETTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TStructMember getDefinedMember() {
		if (definedMember != null && definedMember.eIsProxy()) {
			InternalEObject oldDefinedMember = (InternalEObject)definedMember;
			definedMember = (TStructMember)eResolveProxy(oldDefinedMember);
			if (definedMember != oldDefinedMember) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.TSTRUCT_SETTER__DEFINED_MEMBER, oldDefinedMember, definedMember));
			}
		}
		return definedMember;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TStructMember basicGetDefinedMember() {
		return definedMember;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefinedMember(TStructMember newDefinedMember) {
		TStructMember oldDefinedMember = definedMember;
		definedMember = newDefinedMember;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TSTRUCT_SETTER__DEFINED_MEMBER, oldDefinedMember, definedMember));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExpressionInTypeRef getDtsComputedNameExpression() {
		return dtsComputedNameExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDtsComputedNameExpression(ExpressionInTypeRef newDtsComputedNameExpression, NotificationChain msgs) {
		ExpressionInTypeRef oldDtsComputedNameExpression = dtsComputedNameExpression;
		dtsComputedNameExpression = newDtsComputedNameExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.TSTRUCT_SETTER__DTS_COMPUTED_NAME_EXPRESSION, oldDtsComputedNameExpression, newDtsComputedNameExpression);
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
	public void setDtsComputedNameExpression(ExpressionInTypeRef newDtsComputedNameExpression) {
		if (newDtsComputedNameExpression != dtsComputedNameExpression) {
			NotificationChain msgs = null;
			if (dtsComputedNameExpression != null)
				msgs = ((InternalEObject)dtsComputedNameExpression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TSTRUCT_SETTER__DTS_COMPUTED_NAME_EXPRESSION, null, msgs);
			if (newDtsComputedNameExpression != null)
				msgs = ((InternalEObject)newDtsComputedNameExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TSTRUCT_SETTER__DTS_COMPUTED_NAME_EXPRESSION, null, msgs);
			msgs = basicSetDtsComputedNameExpression(newDtsComputedNameExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TSTRUCT_SETTER__DTS_COMPUTED_NAME_EXPRESSION, newDtsComputedNameExpression, newDtsComputedNameExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isWriteable() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MemberAccessModifier getDefaultMemberAccessModifier() {
		return MemberAccessModifier.PUBLIC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isStatic() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MemberAccessModifier getMemberAccessModifier() {
		return MemberAccessModifier.PUBLIC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isASTCallSignature() {
		return ((this instanceof TStructMethod) && (this.getName() == null));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isASTConstructSignature() {
		return ((this instanceof TStructMethod) && Objects.equal(this.getName(), "new"));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.TSTRUCT_SETTER__DTS_COMPUTED_NAME_EXPRESSION:
				return basicSetDtsComputedNameExpression(null, msgs);
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
			case TypesPackage.TSTRUCT_SETTER__DEFINED_MEMBER:
				if (resolve) return getDefinedMember();
				return basicGetDefinedMember();
			case TypesPackage.TSTRUCT_SETTER__DTS_COMPUTED_NAME_EXPRESSION:
				return getDtsComputedNameExpression();
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
			case TypesPackage.TSTRUCT_SETTER__DEFINED_MEMBER:
				setDefinedMember((TStructMember)newValue);
				return;
			case TypesPackage.TSTRUCT_SETTER__DTS_COMPUTED_NAME_EXPRESSION:
				setDtsComputedNameExpression((ExpressionInTypeRef)newValue);
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
			case TypesPackage.TSTRUCT_SETTER__DEFINED_MEMBER:
				setDefinedMember((TStructMember)null);
				return;
			case TypesPackage.TSTRUCT_SETTER__DTS_COMPUTED_NAME_EXPRESSION:
				setDtsComputedNameExpression((ExpressionInTypeRef)null);
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
			case TypesPackage.TSTRUCT_SETTER__DEFINED_MEMBER:
				return definedMember != null;
			case TypesPackage.TSTRUCT_SETTER__DTS_COMPUTED_NAME_EXPRESSION:
				return dtsComputedNameExpression != null;
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
		if (baseClass == TStructMember.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TSTRUCT_SETTER__DEFINED_MEMBER: return TypesPackage.TSTRUCT_MEMBER__DEFINED_MEMBER;
				case TypesPackage.TSTRUCT_SETTER__DTS_COMPUTED_NAME_EXPRESSION: return TypesPackage.TSTRUCT_MEMBER__DTS_COMPUTED_NAME_EXPRESSION;
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
		if (baseClass == TStructMember.class) {
			switch (baseFeatureID) {
				case TypesPackage.TSTRUCT_MEMBER__DEFINED_MEMBER: return TypesPackage.TSTRUCT_SETTER__DEFINED_MEMBER;
				case TypesPackage.TSTRUCT_MEMBER__DTS_COMPUTED_NAME_EXPRESSION: return TypesPackage.TSTRUCT_SETTER__DTS_COMPUTED_NAME_EXPRESSION;
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
		if (baseClass == TMember.class) {
			switch (baseOperationID) {
				case TypesPackage.TMEMBER___GET_MEMBER_ACCESS_MODIFIER: return TypesPackage.TSTRUCT_SETTER___GET_MEMBER_ACCESS_MODIFIER;
				case TypesPackage.TMEMBER___IS_WRITEABLE: return TypesPackage.TSTRUCT_SETTER___IS_WRITEABLE;
				case TypesPackage.TMEMBER___IS_STATIC: return TypesPackage.TSTRUCT_SETTER___IS_STATIC;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TMemberWithAccessModifier.class) {
			switch (baseOperationID) {
				case TypesPackage.TMEMBER_WITH_ACCESS_MODIFIER___GET_MEMBER_ACCESS_MODIFIER: return TypesPackage.TSTRUCT_SETTER___GET_MEMBER_ACCESS_MODIFIER;
				case TypesPackage.TMEMBER_WITH_ACCESS_MODIFIER___GET_DEFAULT_MEMBER_ACCESS_MODIFIER: return TypesPackage.TSTRUCT_SETTER___GET_DEFAULT_MEMBER_ACCESS_MODIFIER;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TSetter.class) {
			switch (baseOperationID) {
				case TypesPackage.TSETTER___IS_WRITEABLE: return TypesPackage.TSTRUCT_SETTER___IS_WRITEABLE;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TStructMember.class) {
			switch (baseOperationID) {
				case TypesPackage.TSTRUCT_MEMBER___GET_DEFAULT_MEMBER_ACCESS_MODIFIER: return TypesPackage.TSTRUCT_SETTER___GET_DEFAULT_MEMBER_ACCESS_MODIFIER;
				case TypesPackage.TSTRUCT_MEMBER___IS_STATIC: return TypesPackage.TSTRUCT_SETTER___IS_STATIC;
				case TypesPackage.TSTRUCT_MEMBER___GET_MEMBER_ACCESS_MODIFIER: return TypesPackage.TSTRUCT_SETTER___GET_MEMBER_ACCESS_MODIFIER;
				case TypesPackage.TSTRUCT_MEMBER___IS_AST_CALL_SIGNATURE: return TypesPackage.TSTRUCT_SETTER___IS_AST_CALL_SIGNATURE;
				case TypesPackage.TSTRUCT_MEMBER___IS_AST_CONSTRUCT_SIGNATURE: return TypesPackage.TSTRUCT_SETTER___IS_AST_CONSTRUCT_SIGNATURE;
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
			case TypesPackage.TSTRUCT_SETTER___IS_WRITEABLE:
				return isWriteable();
			case TypesPackage.TSTRUCT_SETTER___GET_DEFAULT_MEMBER_ACCESS_MODIFIER:
				return getDefaultMemberAccessModifier();
			case TypesPackage.TSTRUCT_SETTER___IS_STATIC:
				return isStatic();
			case TypesPackage.TSTRUCT_SETTER___GET_MEMBER_ACCESS_MODIFIER:
				return getMemberAccessModifier();
			case TypesPackage.TSTRUCT_SETTER___IS_AST_CALL_SIGNATURE:
				return isASTCallSignature();
			case TypesPackage.TSTRUCT_SETTER___IS_AST_CONSTRUCT_SIGNATURE:
				return isASTConstructSignature();
		}
		return super.eInvoke(operationID, arguments);
	}

} //TStructSetterImpl
