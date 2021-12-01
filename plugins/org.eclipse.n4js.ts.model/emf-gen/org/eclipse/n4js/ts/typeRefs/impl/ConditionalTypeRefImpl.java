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

import org.eclipse.n4js.ts.typeRefs.ConditionalTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Conditional Type Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ConditionalTypeRefImpl#getTypeRef <em>Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ConditionalTypeRefImpl#getUpperBound <em>Upper Bound</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ConditionalTypeRefImpl#getTrueTypeRef <em>True Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ConditionalTypeRefImpl#getFalseTypeRef <em>False Type Ref</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConditionalTypeRefImpl extends TypeRefImpl implements ConditionalTypeRef {
	/**
	 * The cached value of the '{@link #getTypeRef() <em>Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef typeRef;

	/**
	 * The cached value of the '{@link #getUpperBound() <em>Upper Bound</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpperBound()
	 * @generated
	 * @ordered
	 */
	protected TypeRef upperBound;

	/**
	 * The cached value of the '{@link #getTrueTypeRef() <em>True Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrueTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef trueTypeRef;

	/**
	 * The cached value of the '{@link #getFalseTypeRef() <em>False Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFalseTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef falseTypeRef;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConditionalTypeRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.CONDITIONAL_TYPE_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getTypeRef() {
		return typeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTypeRef(TypeRef newTypeRef, NotificationChain msgs) {
		TypeRef oldTypeRef = typeRef;
		typeRef = newTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypeRefsPackage.CONDITIONAL_TYPE_REF__TYPE_REF, oldTypeRef, newTypeRef);
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
	public void setTypeRef(TypeRef newTypeRef) {
		if (newTypeRef != typeRef) {
			NotificationChain msgs = null;
			if (typeRef != null)
				msgs = ((InternalEObject)typeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.CONDITIONAL_TYPE_REF__TYPE_REF, null, msgs);
			if (newTypeRef != null)
				msgs = ((InternalEObject)newTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.CONDITIONAL_TYPE_REF__TYPE_REF, null, msgs);
			msgs = basicSetTypeRef(newTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.CONDITIONAL_TYPE_REF__TYPE_REF, newTypeRef, newTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getUpperBound() {
		return upperBound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUpperBound(TypeRef newUpperBound, NotificationChain msgs) {
		TypeRef oldUpperBound = upperBound;
		upperBound = newUpperBound;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypeRefsPackage.CONDITIONAL_TYPE_REF__UPPER_BOUND, oldUpperBound, newUpperBound);
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
	public void setUpperBound(TypeRef newUpperBound) {
		if (newUpperBound != upperBound) {
			NotificationChain msgs = null;
			if (upperBound != null)
				msgs = ((InternalEObject)upperBound).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.CONDITIONAL_TYPE_REF__UPPER_BOUND, null, msgs);
			if (newUpperBound != null)
				msgs = ((InternalEObject)newUpperBound).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.CONDITIONAL_TYPE_REF__UPPER_BOUND, null, msgs);
			msgs = basicSetUpperBound(newUpperBound, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.CONDITIONAL_TYPE_REF__UPPER_BOUND, newUpperBound, newUpperBound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getTrueTypeRef() {
		return trueTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrueTypeRef(TypeRef newTrueTypeRef, NotificationChain msgs) {
		TypeRef oldTrueTypeRef = trueTypeRef;
		trueTypeRef = newTrueTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypeRefsPackage.CONDITIONAL_TYPE_REF__TRUE_TYPE_REF, oldTrueTypeRef, newTrueTypeRef);
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
	public void setTrueTypeRef(TypeRef newTrueTypeRef) {
		if (newTrueTypeRef != trueTypeRef) {
			NotificationChain msgs = null;
			if (trueTypeRef != null)
				msgs = ((InternalEObject)trueTypeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.CONDITIONAL_TYPE_REF__TRUE_TYPE_REF, null, msgs);
			if (newTrueTypeRef != null)
				msgs = ((InternalEObject)newTrueTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.CONDITIONAL_TYPE_REF__TRUE_TYPE_REF, null, msgs);
			msgs = basicSetTrueTypeRef(newTrueTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.CONDITIONAL_TYPE_REF__TRUE_TYPE_REF, newTrueTypeRef, newTrueTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getFalseTypeRef() {
		return falseTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFalseTypeRef(TypeRef newFalseTypeRef, NotificationChain msgs) {
		TypeRef oldFalseTypeRef = falseTypeRef;
		falseTypeRef = newFalseTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypeRefsPackage.CONDITIONAL_TYPE_REF__FALSE_TYPE_REF, oldFalseTypeRef, newFalseTypeRef);
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
	public void setFalseTypeRef(TypeRef newFalseTypeRef) {
		if (newFalseTypeRef != falseTypeRef) {
			NotificationChain msgs = null;
			if (falseTypeRef != null)
				msgs = ((InternalEObject)falseTypeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.CONDITIONAL_TYPE_REF__FALSE_TYPE_REF, null, msgs);
			if (newFalseTypeRef != null)
				msgs = ((InternalEObject)newFalseTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.CONDITIONAL_TYPE_REF__FALSE_TYPE_REF, null, msgs);
			msgs = basicSetFalseTypeRef(newFalseTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.CONDITIONAL_TYPE_REF__FALSE_TYPE_REF, newFalseTypeRef, newFalseTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String internalGetTypeRefAsString() {
		TypeRef _typeRef = this.getTypeRef();
		String _typeRefAsString = null;
		if (_typeRef!=null) {
			_typeRefAsString=_typeRef.getTypeRefAsString();
		}
		String _plus = (_typeRefAsString + " extends ");
		TypeRef _upperBound = this.getUpperBound();
		String _typeRefAsString_1 = null;
		if (_upperBound!=null) {
			_typeRefAsString_1=_upperBound.getTypeRefAsString();
		}
		String _plus_1 = (_plus + _typeRefAsString_1);
		String _plus_2 = (_plus_1 + " ? ");
		String _typeRefAsString_2 = this.getTrueTypeRef().getTypeRefAsString();
		String _plus_3 = (_plus_2 + _typeRefAsString_2);
		String _plus_4 = (_plus_3 + " : ");
		String _typeRefAsString_3 = this.getFalseTypeRef().getTypeRefAsString();
		return (_plus_4 + _typeRefAsString_3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypeRefsPackage.CONDITIONAL_TYPE_REF__TYPE_REF:
				return basicSetTypeRef(null, msgs);
			case TypeRefsPackage.CONDITIONAL_TYPE_REF__UPPER_BOUND:
				return basicSetUpperBound(null, msgs);
			case TypeRefsPackage.CONDITIONAL_TYPE_REF__TRUE_TYPE_REF:
				return basicSetTrueTypeRef(null, msgs);
			case TypeRefsPackage.CONDITIONAL_TYPE_REF__FALSE_TYPE_REF:
				return basicSetFalseTypeRef(null, msgs);
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
			case TypeRefsPackage.CONDITIONAL_TYPE_REF__TYPE_REF:
				return getTypeRef();
			case TypeRefsPackage.CONDITIONAL_TYPE_REF__UPPER_BOUND:
				return getUpperBound();
			case TypeRefsPackage.CONDITIONAL_TYPE_REF__TRUE_TYPE_REF:
				return getTrueTypeRef();
			case TypeRefsPackage.CONDITIONAL_TYPE_REF__FALSE_TYPE_REF:
				return getFalseTypeRef();
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
			case TypeRefsPackage.CONDITIONAL_TYPE_REF__TYPE_REF:
				setTypeRef((TypeRef)newValue);
				return;
			case TypeRefsPackage.CONDITIONAL_TYPE_REF__UPPER_BOUND:
				setUpperBound((TypeRef)newValue);
				return;
			case TypeRefsPackage.CONDITIONAL_TYPE_REF__TRUE_TYPE_REF:
				setTrueTypeRef((TypeRef)newValue);
				return;
			case TypeRefsPackage.CONDITIONAL_TYPE_REF__FALSE_TYPE_REF:
				setFalseTypeRef((TypeRef)newValue);
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
			case TypeRefsPackage.CONDITIONAL_TYPE_REF__TYPE_REF:
				setTypeRef((TypeRef)null);
				return;
			case TypeRefsPackage.CONDITIONAL_TYPE_REF__UPPER_BOUND:
				setUpperBound((TypeRef)null);
				return;
			case TypeRefsPackage.CONDITIONAL_TYPE_REF__TRUE_TYPE_REF:
				setTrueTypeRef((TypeRef)null);
				return;
			case TypeRefsPackage.CONDITIONAL_TYPE_REF__FALSE_TYPE_REF:
				setFalseTypeRef((TypeRef)null);
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
			case TypeRefsPackage.CONDITIONAL_TYPE_REF__TYPE_REF:
				return typeRef != null;
			case TypeRefsPackage.CONDITIONAL_TYPE_REF__UPPER_BOUND:
				return upperBound != null;
			case TypeRefsPackage.CONDITIONAL_TYPE_REF__TRUE_TYPE_REF:
				return trueTypeRef != null;
			case TypeRefsPackage.CONDITIONAL_TYPE_REF__FALSE_TYPE_REF:
				return falseTypeRef != null;
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
			case TypeRefsPackage.CONDITIONAL_TYPE_REF___INTERNAL_GET_TYPE_REF_AS_STRING:
				return internalGetTypeRefAsString();
		}
		return super.eInvoke(operationID, arguments);
	}

} //ConditionalTypeRefImpl
