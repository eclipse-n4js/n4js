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

import org.eclipse.n4js.ts.typeRefs.MappedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mapped Type Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.MappedTypeRefImpl#isIncludeReadonly <em>Include Readonly</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.MappedTypeRefImpl#isExcludeReadonly <em>Exclude Readonly</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.MappedTypeRefImpl#isIncludeOptional <em>Include Optional</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.MappedTypeRefImpl#isExcludeOptional <em>Exclude Optional</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.MappedTypeRefImpl#getPropName <em>Prop Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.MappedTypeRefImpl#getPropNameTypeRef <em>Prop Name Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.MappedTypeRefImpl#getTemplateTypeRef <em>Template Type Ref</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MappedTypeRefImpl extends TypeRefImpl implements MappedTypeRef {
	/**
	 * The default value of the '{@link #isIncludeReadonly() <em>Include Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIncludeReadonly()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INCLUDE_READONLY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIncludeReadonly() <em>Include Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIncludeReadonly()
	 * @generated
	 * @ordered
	 */
	protected boolean includeReadonly = INCLUDE_READONLY_EDEFAULT;

	/**
	 * The default value of the '{@link #isExcludeReadonly() <em>Exclude Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExcludeReadonly()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXCLUDE_READONLY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExcludeReadonly() <em>Exclude Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExcludeReadonly()
	 * @generated
	 * @ordered
	 */
	protected boolean excludeReadonly = EXCLUDE_READONLY_EDEFAULT;

	/**
	 * The default value of the '{@link #isIncludeOptional() <em>Include Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIncludeOptional()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INCLUDE_OPTIONAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIncludeOptional() <em>Include Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIncludeOptional()
	 * @generated
	 * @ordered
	 */
	protected boolean includeOptional = INCLUDE_OPTIONAL_EDEFAULT;

	/**
	 * The default value of the '{@link #isExcludeOptional() <em>Exclude Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExcludeOptional()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXCLUDE_OPTIONAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExcludeOptional() <em>Exclude Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExcludeOptional()
	 * @generated
	 * @ordered
	 */
	protected boolean excludeOptional = EXCLUDE_OPTIONAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getPropName() <em>Prop Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropName()
	 * @generated
	 * @ordered
	 */
	protected static final String PROP_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPropName() <em>Prop Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropName()
	 * @generated
	 * @ordered
	 */
	protected String propName = PROP_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPropNameTypeRef() <em>Prop Name Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropNameTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef propNameTypeRef;

	/**
	 * The cached value of the '{@link #getTemplateTypeRef() <em>Template Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemplateTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef templateTypeRef;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MappedTypeRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.MAPPED_TYPE_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIncludeReadonly() {
		return includeReadonly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIncludeReadonly(boolean newIncludeReadonly) {
		boolean oldIncludeReadonly = includeReadonly;
		includeReadonly = newIncludeReadonly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.MAPPED_TYPE_REF__INCLUDE_READONLY, oldIncludeReadonly, includeReadonly));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isExcludeReadonly() {
		return excludeReadonly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExcludeReadonly(boolean newExcludeReadonly) {
		boolean oldExcludeReadonly = excludeReadonly;
		excludeReadonly = newExcludeReadonly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.MAPPED_TYPE_REF__EXCLUDE_READONLY, oldExcludeReadonly, excludeReadonly));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIncludeOptional() {
		return includeOptional;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIncludeOptional(boolean newIncludeOptional) {
		boolean oldIncludeOptional = includeOptional;
		includeOptional = newIncludeOptional;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.MAPPED_TYPE_REF__INCLUDE_OPTIONAL, oldIncludeOptional, includeOptional));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isExcludeOptional() {
		return excludeOptional;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExcludeOptional(boolean newExcludeOptional) {
		boolean oldExcludeOptional = excludeOptional;
		excludeOptional = newExcludeOptional;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.MAPPED_TYPE_REF__EXCLUDE_OPTIONAL, oldExcludeOptional, excludeOptional));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPropName() {
		return propName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPropName(String newPropName) {
		String oldPropName = propName;
		propName = newPropName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.MAPPED_TYPE_REF__PROP_NAME, oldPropName, propName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getPropNameTypeRef() {
		return propNameTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPropNameTypeRef(TypeRef newPropNameTypeRef, NotificationChain msgs) {
		TypeRef oldPropNameTypeRef = propNameTypeRef;
		propNameTypeRef = newPropNameTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypeRefsPackage.MAPPED_TYPE_REF__PROP_NAME_TYPE_REF, oldPropNameTypeRef, newPropNameTypeRef);
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
	public void setPropNameTypeRef(TypeRef newPropNameTypeRef) {
		if (newPropNameTypeRef != propNameTypeRef) {
			NotificationChain msgs = null;
			if (propNameTypeRef != null)
				msgs = ((InternalEObject)propNameTypeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.MAPPED_TYPE_REF__PROP_NAME_TYPE_REF, null, msgs);
			if (newPropNameTypeRef != null)
				msgs = ((InternalEObject)newPropNameTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.MAPPED_TYPE_REF__PROP_NAME_TYPE_REF, null, msgs);
			msgs = basicSetPropNameTypeRef(newPropNameTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.MAPPED_TYPE_REF__PROP_NAME_TYPE_REF, newPropNameTypeRef, newPropNameTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getTemplateTypeRef() {
		return templateTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTemplateTypeRef(TypeRef newTemplateTypeRef, NotificationChain msgs) {
		TypeRef oldTemplateTypeRef = templateTypeRef;
		templateTypeRef = newTemplateTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypeRefsPackage.MAPPED_TYPE_REF__TEMPLATE_TYPE_REF, oldTemplateTypeRef, newTemplateTypeRef);
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
	public void setTemplateTypeRef(TypeRef newTemplateTypeRef) {
		if (newTemplateTypeRef != templateTypeRef) {
			NotificationChain msgs = null;
			if (templateTypeRef != null)
				msgs = ((InternalEObject)templateTypeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.MAPPED_TYPE_REF__TEMPLATE_TYPE_REF, null, msgs);
			if (newTemplateTypeRef != null)
				msgs = ((InternalEObject)newTemplateTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.MAPPED_TYPE_REF__TEMPLATE_TYPE_REF, null, msgs);
			msgs = basicSetTemplateTypeRef(newTemplateTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.MAPPED_TYPE_REF__TEMPLATE_TYPE_REF, newTemplateTypeRef, newTemplateTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String internalGetTypeRefAsString() {
		String _xifexpression = null;
		boolean _isIncludeReadonly = this.isIncludeReadonly();
		if (_isIncludeReadonly) {
			_xifexpression = "readonly ";
		}
		else {
			String _xifexpression_1 = null;
			boolean _isExcludeReadonly = this.isExcludeReadonly();
			if (_isExcludeReadonly) {
				_xifexpression_1 = "-readonly ";
			}
			else {
				_xifexpression_1 = "";
			}
			_xifexpression = _xifexpression_1;
		}
		String _plus = ("{ " + _xifexpression);
		String _plus_1 = (_plus + "[");
		String _propName = this.getPropName();
		String _plus_2 = (_plus_1 + _propName);
		String _plus_3 = (_plus_2 + " in ");
		TypeRef _propNameTypeRef = this.getPropNameTypeRef();
		String _typeRefAsString = null;
		if (_propNameTypeRef!=null) {
			_typeRefAsString=_propNameTypeRef.getTypeRefAsString();
		}
		String _plus_4 = (_plus_3 + _typeRefAsString);
		String _plus_5 = (_plus_4 + "]");
		String _xifexpression_2 = null;
		boolean _isIncludeOptional = this.isIncludeOptional();
		if (_isIncludeOptional) {
			_xifexpression_2 = "?";
		}
		else {
			String _xifexpression_3 = null;
			boolean _isExcludeOptional = this.isExcludeOptional();
			if (_isExcludeOptional) {
				_xifexpression_3 = "-?";
			}
			else {
				_xifexpression_3 = "";
			}
			_xifexpression_2 = _xifexpression_3;
		}
		String _plus_6 = (_plus_5 + _xifexpression_2);
		String _xifexpression_4 = null;
		TypeRef _templateTypeRef = this.getTemplateTypeRef();
		boolean _tripleNotEquals = (_templateTypeRef != null);
		if (_tripleNotEquals) {
			String _typeRefAsString_1 = this.getTemplateTypeRef().getTypeRefAsString();
			_xifexpression_4 = (": " + _typeRefAsString_1);
		}
		else {
			_xifexpression_4 = "";
		}
		return (_plus_6 + _xifexpression_4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypeRefsPackage.MAPPED_TYPE_REF__PROP_NAME_TYPE_REF:
				return basicSetPropNameTypeRef(null, msgs);
			case TypeRefsPackage.MAPPED_TYPE_REF__TEMPLATE_TYPE_REF:
				return basicSetTemplateTypeRef(null, msgs);
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
			case TypeRefsPackage.MAPPED_TYPE_REF__INCLUDE_READONLY:
				return isIncludeReadonly();
			case TypeRefsPackage.MAPPED_TYPE_REF__EXCLUDE_READONLY:
				return isExcludeReadonly();
			case TypeRefsPackage.MAPPED_TYPE_REF__INCLUDE_OPTIONAL:
				return isIncludeOptional();
			case TypeRefsPackage.MAPPED_TYPE_REF__EXCLUDE_OPTIONAL:
				return isExcludeOptional();
			case TypeRefsPackage.MAPPED_TYPE_REF__PROP_NAME:
				return getPropName();
			case TypeRefsPackage.MAPPED_TYPE_REF__PROP_NAME_TYPE_REF:
				return getPropNameTypeRef();
			case TypeRefsPackage.MAPPED_TYPE_REF__TEMPLATE_TYPE_REF:
				return getTemplateTypeRef();
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
			case TypeRefsPackage.MAPPED_TYPE_REF__INCLUDE_READONLY:
				setIncludeReadonly((Boolean)newValue);
				return;
			case TypeRefsPackage.MAPPED_TYPE_REF__EXCLUDE_READONLY:
				setExcludeReadonly((Boolean)newValue);
				return;
			case TypeRefsPackage.MAPPED_TYPE_REF__INCLUDE_OPTIONAL:
				setIncludeOptional((Boolean)newValue);
				return;
			case TypeRefsPackage.MAPPED_TYPE_REF__EXCLUDE_OPTIONAL:
				setExcludeOptional((Boolean)newValue);
				return;
			case TypeRefsPackage.MAPPED_TYPE_REF__PROP_NAME:
				setPropName((String)newValue);
				return;
			case TypeRefsPackage.MAPPED_TYPE_REF__PROP_NAME_TYPE_REF:
				setPropNameTypeRef((TypeRef)newValue);
				return;
			case TypeRefsPackage.MAPPED_TYPE_REF__TEMPLATE_TYPE_REF:
				setTemplateTypeRef((TypeRef)newValue);
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
			case TypeRefsPackage.MAPPED_TYPE_REF__INCLUDE_READONLY:
				setIncludeReadonly(INCLUDE_READONLY_EDEFAULT);
				return;
			case TypeRefsPackage.MAPPED_TYPE_REF__EXCLUDE_READONLY:
				setExcludeReadonly(EXCLUDE_READONLY_EDEFAULT);
				return;
			case TypeRefsPackage.MAPPED_TYPE_REF__INCLUDE_OPTIONAL:
				setIncludeOptional(INCLUDE_OPTIONAL_EDEFAULT);
				return;
			case TypeRefsPackage.MAPPED_TYPE_REF__EXCLUDE_OPTIONAL:
				setExcludeOptional(EXCLUDE_OPTIONAL_EDEFAULT);
				return;
			case TypeRefsPackage.MAPPED_TYPE_REF__PROP_NAME:
				setPropName(PROP_NAME_EDEFAULT);
				return;
			case TypeRefsPackage.MAPPED_TYPE_REF__PROP_NAME_TYPE_REF:
				setPropNameTypeRef((TypeRef)null);
				return;
			case TypeRefsPackage.MAPPED_TYPE_REF__TEMPLATE_TYPE_REF:
				setTemplateTypeRef((TypeRef)null);
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
			case TypeRefsPackage.MAPPED_TYPE_REF__INCLUDE_READONLY:
				return includeReadonly != INCLUDE_READONLY_EDEFAULT;
			case TypeRefsPackage.MAPPED_TYPE_REF__EXCLUDE_READONLY:
				return excludeReadonly != EXCLUDE_READONLY_EDEFAULT;
			case TypeRefsPackage.MAPPED_TYPE_REF__INCLUDE_OPTIONAL:
				return includeOptional != INCLUDE_OPTIONAL_EDEFAULT;
			case TypeRefsPackage.MAPPED_TYPE_REF__EXCLUDE_OPTIONAL:
				return excludeOptional != EXCLUDE_OPTIONAL_EDEFAULT;
			case TypeRefsPackage.MAPPED_TYPE_REF__PROP_NAME:
				return PROP_NAME_EDEFAULT == null ? propName != null : !PROP_NAME_EDEFAULT.equals(propName);
			case TypeRefsPackage.MAPPED_TYPE_REF__PROP_NAME_TYPE_REF:
				return propNameTypeRef != null;
			case TypeRefsPackage.MAPPED_TYPE_REF__TEMPLATE_TYPE_REF:
				return templateTypeRef != null;
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
			case TypeRefsPackage.MAPPED_TYPE_REF___INTERNAL_GET_TYPE_REF_AS_STRING:
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
		result.append(" (includeReadonly: ");
		result.append(includeReadonly);
		result.append(", excludeReadonly: ");
		result.append(excludeReadonly);
		result.append(", includeOptional: ");
		result.append(includeOptional);
		result.append(", excludeOptional: ");
		result.append(excludeOptional);
		result.append(", propName: ");
		result.append(propName);
		result.append(')');
		return result.toString();
	}

} //MappedTypeRefImpl
