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
package org.eclipse.n4js.jsdoc.dom.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.jsdoc.dom.DomPackage;
import org.eclipse.n4js.jsdoc.dom.FullMemberReference;
import org.eclipse.n4js.jsdoc.dom.FullTypeReference;
import org.eclipse.n4js.jsdoc.dom.JSDocNode;
import org.eclipse.n4js.jsdoc.dom.SimpleTypeReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Full Member Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.impl.FullMemberReferenceImpl#getMemberName <em>Member Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.impl.FullMemberReferenceImpl#isStaticMember <em>Static Member</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FullMemberReferenceImpl extends FullTypeReferenceImpl implements FullMemberReference {
	/**
	 * The default value of the '{@link #getMemberName() <em>Member Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemberName()
	 * @generated
	 * @ordered
	 */
	protected static final String MEMBER_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMemberName() <em>Member Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemberName()
	 * @generated
	 * @ordered
	 */
	protected String memberName = MEMBER_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isStaticMember() <em>Static Member</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStaticMember()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STATIC_MEMBER_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isStaticMember() <em>Static Member</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStaticMember()
	 * @generated
	 * @ordered
	 */
	protected boolean staticMember = STATIC_MEMBER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FullMemberReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomPackage.Literals.FULL_MEMBER_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getMemberName() {
		return memberName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMemberName(String newMemberName) {
		String oldMemberName = memberName;
		memberName = newMemberName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomPackage.FULL_MEMBER_REFERENCE__MEMBER_NAME, oldMemberName, memberName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isStaticMember() {
		return staticMember;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStaticMember(boolean newStaticMember) {
		boolean oldStaticMember = staticMember;
		staticMember = newStaticMember;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomPackage.FULL_MEMBER_REFERENCE__STATIC_MEMBER, oldStaticMember, staticMember));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean memberNameSet() {
		return ((this.getMemberName() != null) && (!this.getMemberName().isEmpty()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		String _string = super.toString();
		StringBuilder strb = new StringBuilder(_string);
		boolean _memberNameSet = this.memberNameSet();
		if (_memberNameSet) {
			int _length = strb.length();
			boolean _greaterThan = (_length > 0);
			if (_greaterThan) {
				boolean _isStaticMember = this.isStaticMember();
				if (_isStaticMember) {
					strb.append("#");
				}
				else {
					strb.append(".");
				}
			}
			strb.append(this.getMemberName());
		}
		return strb.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DomPackage.FULL_MEMBER_REFERENCE__MEMBER_NAME:
				return getMemberName();
			case DomPackage.FULL_MEMBER_REFERENCE__STATIC_MEMBER:
				return isStaticMember();
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
			case DomPackage.FULL_MEMBER_REFERENCE__MEMBER_NAME:
				setMemberName((String)newValue);
				return;
			case DomPackage.FULL_MEMBER_REFERENCE__STATIC_MEMBER:
				setStaticMember((Boolean)newValue);
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
			case DomPackage.FULL_MEMBER_REFERENCE__MEMBER_NAME:
				setMemberName(MEMBER_NAME_EDEFAULT);
				return;
			case DomPackage.FULL_MEMBER_REFERENCE__STATIC_MEMBER:
				setStaticMember(STATIC_MEMBER_EDEFAULT);
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
			case DomPackage.FULL_MEMBER_REFERENCE__MEMBER_NAME:
				return MEMBER_NAME_EDEFAULT == null ? memberName != null : !MEMBER_NAME_EDEFAULT.equals(memberName);
			case DomPackage.FULL_MEMBER_REFERENCE__STATIC_MEMBER:
				return staticMember != STATIC_MEMBER_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == JSDocNode.class) {
			switch (baseOperationID) {
				case DomPackage.JS_DOC_NODE___TO_STRING: return DomPackage.FULL_MEMBER_REFERENCE___TO_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == SimpleTypeReference.class) {
			switch (baseOperationID) {
				case DomPackage.SIMPLE_TYPE_REFERENCE___TO_STRING: return DomPackage.FULL_MEMBER_REFERENCE___TO_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == FullTypeReference.class) {
			switch (baseOperationID) {
				case DomPackage.FULL_TYPE_REFERENCE___TO_STRING: return DomPackage.FULL_MEMBER_REFERENCE___TO_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
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
			case DomPackage.FULL_MEMBER_REFERENCE___MEMBER_NAME_SET:
				return memberNameSet();
			case DomPackage.FULL_MEMBER_REFERENCE___TO_STRING:
				return toString();
		}
		return super.eInvoke(operationID, arguments);
	}

} //FullMemberReferenceImpl
