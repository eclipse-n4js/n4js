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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Type Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.TypeTypeRefImpl#getTypeArg <em>Type Arg</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.TypeTypeRefImpl#isConstructorRef <em>Constructor Ref</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeTypeRefImpl extends BaseTypeRefImpl implements TypeTypeRef {
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
	 * The default value of the '{@link #isConstructorRef() <em>Constructor Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConstructorRef()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONSTRUCTOR_REF_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isConstructorRef() <em>Constructor Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConstructorRef()
	 * @generated
	 * @ordered
	 */
	protected boolean constructorRef = CONSTRUCTOR_REF_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeTypeRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.TYPE_TYPE_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypeRefsPackage.TYPE_TYPE_REF__TYPE_ARG, oldTypeArg, newTypeArg);
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
	public void setTypeArg(TypeArgument newTypeArg) {
		if (newTypeArg != typeArg) {
			NotificationChain msgs = null;
			if (typeArg != null)
				msgs = ((InternalEObject)typeArg).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.TYPE_TYPE_REF__TYPE_ARG, null, msgs);
			if (newTypeArg != null)
				msgs = ((InternalEObject)newTypeArg).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.TYPE_TYPE_REF__TYPE_ARG, null, msgs);
			msgs = basicSetTypeArg(newTypeArg, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.TYPE_TYPE_REF__TYPE_ARG, newTypeArg, newTypeArg));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isConstructorRef() {
		return constructorRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConstructorRef(boolean newConstructorRef) {
		boolean oldConstructorRef = constructorRef;
		constructorRef = newConstructorRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.TYPE_TYPE_REF__CONSTRUCTOR_REF, oldConstructorRef, constructorRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String internalGetTypeRefAsString() {
		String _xifexpression = null;
		boolean _isConstructorRef = this.isConstructorRef();
		if (_isConstructorRef) {
			_xifexpression = "constructor";
		}
		else {
			_xifexpression = "type";
		}
		final String kwd = _xifexpression;
		String _xifexpression_1 = null;
		TypeArgument _typeArg = this.getTypeArg();
		boolean _tripleEquals = (null == _typeArg);
		if (_tripleEquals) {
			_xifexpression_1 = "";
		}
		else {
			_xifexpression_1 = this.getTypeArg().getTypeRefAsString();
		}
		final String refName = _xifexpression_1;
		String _modifiersAsString = this.getModifiersAsString();
		return ((((kwd + "{") + refName) + "}") + _modifiersAsString);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypeRefsPackage.TYPE_TYPE_REF__TYPE_ARG:
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
			case TypeRefsPackage.TYPE_TYPE_REF__TYPE_ARG:
				return getTypeArg();
			case TypeRefsPackage.TYPE_TYPE_REF__CONSTRUCTOR_REF:
				return isConstructorRef();
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
			case TypeRefsPackage.TYPE_TYPE_REF__TYPE_ARG:
				setTypeArg((TypeArgument)newValue);
				return;
			case TypeRefsPackage.TYPE_TYPE_REF__CONSTRUCTOR_REF:
				setConstructorRef((Boolean)newValue);
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
			case TypeRefsPackage.TYPE_TYPE_REF__TYPE_ARG:
				setTypeArg((TypeArgument)null);
				return;
			case TypeRefsPackage.TYPE_TYPE_REF__CONSTRUCTOR_REF:
				setConstructorRef(CONSTRUCTOR_REF_EDEFAULT);
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
			case TypeRefsPackage.TYPE_TYPE_REF__TYPE_ARG:
				return typeArg != null;
			case TypeRefsPackage.TYPE_TYPE_REF__CONSTRUCTOR_REF:
				return constructorRef != CONSTRUCTOR_REF_EDEFAULT;
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
			case TypeRefsPackage.TYPE_TYPE_REF___INTERNAL_GET_TYPE_REF_AS_STRING:
				return internalGetTypeRefAsString();
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
		result.append(" (constructorRef: ");
		result.append(constructorRef);
		result.append(')');
		return result.toString();
	}

} //TypeTypeRefImpl
