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

import org.eclipse.n4js.n4JS.FieldAccessor;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.SetterDeclaration;
import org.eclipse.n4js.n4JS.TypeProvidingElement;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.TSetter;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Setter Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.SetterDeclarationImpl#getDefinedSetter <em>Defined Setter</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.SetterDeclarationImpl#getFpar <em>Fpar</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class SetterDeclarationImpl extends FieldAccessorImpl implements SetterDeclaration {
	/**
	 * The cached value of the '{@link #getDefinedSetter() <em>Defined Setter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinedSetter()
	 * @generated
	 * @ordered
	 */
	protected TSetter definedSetter;

	/**
	 * The cached value of the '{@link #getFpar() <em>Fpar</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFpar()
	 * @generated
	 * @ordered
	 */
	protected FormalParameter fpar;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SetterDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.SETTER_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TSetter getDefinedSetter() {
		if (definedSetter != null && definedSetter.eIsProxy()) {
			InternalEObject oldDefinedSetter = (InternalEObject)definedSetter;
			definedSetter = (TSetter)eResolveProxy(oldDefinedSetter);
			if (definedSetter != oldDefinedSetter) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.SETTER_DECLARATION__DEFINED_SETTER, oldDefinedSetter, definedSetter));
			}
		}
		return definedSetter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TSetter basicGetDefinedSetter() {
		return definedSetter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefinedSetter(TSetter newDefinedSetter) {
		TSetter oldDefinedSetter = definedSetter;
		definedSetter = newDefinedSetter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.SETTER_DECLARATION__DEFINED_SETTER, oldDefinedSetter, definedSetter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FormalParameter getFpar() {
		return fpar;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFpar(FormalParameter newFpar, NotificationChain msgs) {
		FormalParameter oldFpar = fpar;
		fpar = newFpar;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.SETTER_DECLARATION__FPAR, oldFpar, newFpar);
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
	public void setFpar(FormalParameter newFpar) {
		if (newFpar != fpar) {
			NotificationChain msgs = null;
			if (fpar != null)
				msgs = ((InternalEObject)fpar).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.SETTER_DECLARATION__FPAR, null, msgs);
			if (newFpar != null)
				msgs = ((InternalEObject)newFpar).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.SETTER_DECLARATION__FPAR, null, msgs);
			msgs = basicSetFpar(newFpar, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.SETTER_DECLARATION__FPAR, newFpar, newFpar));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TSetter getDefinedAccessor() {
		return this.getDefinedSetter();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredTypeRef() {
		FormalParameter _fpar = this.getFpar();
		TypeRef _declaredTypeRef = null;
		if (_fpar!=null) {
			_declaredTypeRef=_fpar.getDeclaredTypeRef();
		}
		return _declaredTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.SETTER_DECLARATION__FPAR:
				return basicSetFpar(null, msgs);
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
			case N4JSPackage.SETTER_DECLARATION__DEFINED_SETTER:
				if (resolve) return getDefinedSetter();
				return basicGetDefinedSetter();
			case N4JSPackage.SETTER_DECLARATION__FPAR:
				return getFpar();
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
			case N4JSPackage.SETTER_DECLARATION__DEFINED_SETTER:
				setDefinedSetter((TSetter)newValue);
				return;
			case N4JSPackage.SETTER_DECLARATION__FPAR:
				setFpar((FormalParameter)newValue);
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
			case N4JSPackage.SETTER_DECLARATION__DEFINED_SETTER:
				setDefinedSetter((TSetter)null);
				return;
			case N4JSPackage.SETTER_DECLARATION__FPAR:
				setFpar((FormalParameter)null);
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
			case N4JSPackage.SETTER_DECLARATION__DEFINED_SETTER:
				return definedSetter != null;
			case N4JSPackage.SETTER_DECLARATION__FPAR:
				return fpar != null;
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
		if (baseClass == TypeProvidingElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.TYPE_PROVIDING_ELEMENT___GET_DECLARED_TYPE_REF: return N4JSPackage.SETTER_DECLARATION___GET_DECLARED_TYPE_REF;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == FieldAccessor.class) {
			switch (baseOperationID) {
				case N4JSPackage.FIELD_ACCESSOR___GET_DECLARED_TYPE_REF: return N4JSPackage.SETTER_DECLARATION___GET_DECLARED_TYPE_REF;
				case N4JSPackage.FIELD_ACCESSOR___GET_DEFINED_ACCESSOR: return N4JSPackage.SETTER_DECLARATION___GET_DEFINED_ACCESSOR;
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
			case N4JSPackage.SETTER_DECLARATION___GET_DEFINED_ACCESSOR:
				return getDefinedAccessor();
			case N4JSPackage.SETTER_DECLARATION___GET_DECLARED_TYPE_REF:
				return getDeclaredTypeRef();
		}
		return super.eInvoke(operationID, arguments);
	}

} //SetterDeclarationImpl
