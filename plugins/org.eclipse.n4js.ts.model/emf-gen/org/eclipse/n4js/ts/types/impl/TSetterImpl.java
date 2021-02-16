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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.MemberType;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TSetter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TSetterImpl#getFpar <em>Fpar</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TSetterImpl extends FieldAccessorImpl implements TSetter {
	/**
	 * The cached value of the '{@link #getFpar() <em>Fpar</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFpar()
	 * @generated
	 * @ordered
	 */
	protected TFormalParameter fpar;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TSetterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TSETTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TFormalParameter getFpar() {
		return fpar;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFpar(TFormalParameter newFpar, NotificationChain msgs) {
		TFormalParameter oldFpar = fpar;
		fpar = newFpar;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.TSETTER__FPAR, oldFpar, newFpar);
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
	public void setFpar(TFormalParameter newFpar) {
		if (newFpar != fpar) {
			NotificationChain msgs = null;
			if (fpar != null)
				msgs = ((InternalEObject)fpar).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TSETTER__FPAR, null, msgs);
			if (newFpar != null)
				msgs = ((InternalEObject)newFpar).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TSETTER__FPAR, null, msgs);
			msgs = basicSetFpar(newFpar, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TSETTER__FPAR, newFpar, newFpar));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getTypeRef() {
		TFormalParameter _fpar = this.getFpar();
		TypeRef _typeRef = null;
		if (_fpar!=null) {
			_typeRef=_fpar.getTypeRef();
		}
		return _typeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isReadable() {
		return false;
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
	public MemberType getMemberType() {
		return MemberType.SETTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getMemberAsString() {
		String _name = this.getName();
		String _plus = ("set " + _name);
		String _xifexpression = null;
		boolean _isOptional = this.isOptional();
		if (_isOptional) {
			_xifexpression = "?";
		}
		else {
			_xifexpression = "";
		}
		String _plus_1 = (_plus + _xifexpression);
		String _plus_2 = (_plus_1 + "(");
		TFormalParameter _fpar = this.getFpar();
		String _formalParameterAsString = null;
		if (_fpar!=null) {
			_formalParameterAsString=_fpar.getFormalParameterAsString();
		}
		String _plus_3 = (_plus_2 + _formalParameterAsString);
		return (_plus_3 + ")");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.TSETTER__FPAR:
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
			case TypesPackage.TSETTER__FPAR:
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
			case TypesPackage.TSETTER__FPAR:
				setFpar((TFormalParameter)newValue);
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
			case TypesPackage.TSETTER__FPAR:
				setFpar((TFormalParameter)null);
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
			case TypesPackage.TSETTER__FPAR:
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
		if (baseClass == TMember.class) {
			switch (baseOperationID) {
				case TypesPackage.TMEMBER___GET_MEMBER_TYPE: return TypesPackage.TSETTER___GET_MEMBER_TYPE;
				case TypesPackage.TMEMBER___IS_READABLE: return TypesPackage.TSETTER___IS_READABLE;
				case TypesPackage.TMEMBER___IS_WRITEABLE: return TypesPackage.TSETTER___IS_WRITEABLE;
				case TypesPackage.TMEMBER___GET_MEMBER_AS_STRING: return TypesPackage.TSETTER___GET_MEMBER_AS_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == FieldAccessor.class) {
			switch (baseOperationID) {
				case TypesPackage.FIELD_ACCESSOR___GET_TYPE_REF: return TypesPackage.TSETTER___GET_TYPE_REF;
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
			case TypesPackage.TSETTER___GET_TYPE_REF:
				return getTypeRef();
			case TypesPackage.TSETTER___IS_READABLE:
				return isReadable();
			case TypesPackage.TSETTER___IS_WRITEABLE:
				return isWriteable();
			case TypesPackage.TSETTER___GET_MEMBER_TYPE:
				return getMemberType();
			case TypesPackage.TSETTER___GET_MEMBER_AS_STRING:
				return getMemberAsString();
		}
		return super.eInvoke(operationID, arguments);
	}

} //TSetterImpl
