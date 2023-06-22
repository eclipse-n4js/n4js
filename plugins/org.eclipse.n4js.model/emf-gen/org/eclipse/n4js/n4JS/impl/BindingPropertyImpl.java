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

import org.eclipse.n4js.n4JS.BindingElement;
import org.eclipse.n4js.n4JS.BindingProperty;
import org.eclipse.n4js.n4JS.MemberAccess;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.n4JS.VariableDeclaration;

import org.eclipse.n4js.ts.types.ComposedMemberCache;
import org.eclipse.n4js.ts.types.IdentifiableElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Binding Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.BindingPropertyImpl#getComposedMemberCache <em>Composed Member Cache</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.BindingPropertyImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.BindingPropertyImpl#getPropertyAsText <em>Property As Text</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.BindingPropertyImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BindingPropertyImpl extends PropertyNameOwnerImpl implements BindingProperty {
	/**
	 * The cached value of the '{@link #getComposedMemberCache() <em>Composed Member Cache</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComposedMemberCache()
	 * @generated
	 * @ordered
	 */
	protected ComposedMemberCache composedMemberCache;

	/**
	 * The cached value of the '{@link #getProperty() <em>Property</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
	protected IdentifiableElement property;

	/**
	 * The default value of the '{@link #getPropertyAsText() <em>Property As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyAsText()
	 * @generated
	 * @ordered
	 */
	protected static final String PROPERTY_AS_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPropertyAsText() <em>Property As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyAsText()
	 * @generated
	 * @ordered
	 */
	protected String propertyAsText = PROPERTY_AS_TEXT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected BindingElement value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BindingPropertyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.BINDING_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ComposedMemberCache getComposedMemberCache() {
		if (composedMemberCache != null && composedMemberCache.eIsProxy()) {
			InternalEObject oldComposedMemberCache = (InternalEObject)composedMemberCache;
			composedMemberCache = (ComposedMemberCache)eResolveProxy(oldComposedMemberCache);
			if (composedMemberCache != oldComposedMemberCache) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.BINDING_PROPERTY__COMPOSED_MEMBER_CACHE, oldComposedMemberCache, composedMemberCache));
			}
		}
		return composedMemberCache;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposedMemberCache basicGetComposedMemberCache() {
		return composedMemberCache;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setComposedMemberCache(ComposedMemberCache newComposedMemberCache) {
		ComposedMemberCache oldComposedMemberCache = composedMemberCache;
		composedMemberCache = newComposedMemberCache;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.BINDING_PROPERTY__COMPOSED_MEMBER_CACHE, oldComposedMemberCache, composedMemberCache));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IdentifiableElement getProperty() {
		if (property != null && property.eIsProxy()) {
			InternalEObject oldProperty = (InternalEObject)property;
			property = (IdentifiableElement)eResolveProxy(oldProperty);
			if (property != oldProperty) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.BINDING_PROPERTY__PROPERTY, oldProperty, property));
			}
		}
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IdentifiableElement basicGetProperty() {
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProperty(IdentifiableElement newProperty) {
		IdentifiableElement oldProperty = property;
		property = newProperty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.BINDING_PROPERTY__PROPERTY, oldProperty, property));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPropertyAsText() {
		return propertyAsText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPropertyAsText(String newPropertyAsText) {
		String oldPropertyAsText = propertyAsText;
		propertyAsText = newPropertyAsText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.BINDING_PROPERTY__PROPERTY_AS_TEXT, oldPropertyAsText, propertyAsText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BindingElement getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValue(BindingElement newValue, NotificationChain msgs) {
		BindingElement oldValue = value;
		value = newValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.BINDING_PROPERTY__VALUE, oldValue, newValue);
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
	public void setValue(BindingElement newValue) {
		if (newValue != value) {
			NotificationChain msgs = null;
			if (value != null)
				msgs = ((InternalEObject)value).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.BINDING_PROPERTY__VALUE, null, msgs);
			if (newValue != null)
				msgs = ((InternalEObject)newValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.BINDING_PROPERTY__VALUE, null, msgs);
			msgs = basicSetValue(newValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.BINDING_PROPERTY__VALUE, newValue, newValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		String _propertyAsText = this.getPropertyAsText();
		boolean _tripleNotEquals = (_propertyAsText != null);
		if (_tripleNotEquals) {
			return this.getPropertyAsText();
		}
		String _name = super.getName();
		boolean _tripleNotEquals_1 = (_name != null);
		if (_tripleNotEquals_1) {
			return super.getName();
		}
		BindingElement _value = this.getValue();
		VariableDeclaration _varDecl = null;
		if (_value!=null) {
			_varDecl=_value.getVarDecl();
		}
		String _name_1 = null;
		if (_varDecl!=null) {
			_name_1=_varDecl.getName();
		}
		return _name_1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isValidName() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSingleNameBinding() {
		return ((this.getPropertyAsText() == null) && (this.getDeclaredName() == null));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.BINDING_PROPERTY__VALUE:
				return basicSetValue(null, msgs);
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
			case N4JSPackage.BINDING_PROPERTY__COMPOSED_MEMBER_CACHE:
				if (resolve) return getComposedMemberCache();
				return basicGetComposedMemberCache();
			case N4JSPackage.BINDING_PROPERTY__PROPERTY:
				if (resolve) return getProperty();
				return basicGetProperty();
			case N4JSPackage.BINDING_PROPERTY__PROPERTY_AS_TEXT:
				return getPropertyAsText();
			case N4JSPackage.BINDING_PROPERTY__VALUE:
				return getValue();
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
			case N4JSPackage.BINDING_PROPERTY__COMPOSED_MEMBER_CACHE:
				setComposedMemberCache((ComposedMemberCache)newValue);
				return;
			case N4JSPackage.BINDING_PROPERTY__PROPERTY:
				setProperty((IdentifiableElement)newValue);
				return;
			case N4JSPackage.BINDING_PROPERTY__PROPERTY_AS_TEXT:
				setPropertyAsText((String)newValue);
				return;
			case N4JSPackage.BINDING_PROPERTY__VALUE:
				setValue((BindingElement)newValue);
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
			case N4JSPackage.BINDING_PROPERTY__COMPOSED_MEMBER_CACHE:
				setComposedMemberCache((ComposedMemberCache)null);
				return;
			case N4JSPackage.BINDING_PROPERTY__PROPERTY:
				setProperty((IdentifiableElement)null);
				return;
			case N4JSPackage.BINDING_PROPERTY__PROPERTY_AS_TEXT:
				setPropertyAsText(PROPERTY_AS_TEXT_EDEFAULT);
				return;
			case N4JSPackage.BINDING_PROPERTY__VALUE:
				setValue((BindingElement)null);
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
			case N4JSPackage.BINDING_PROPERTY__COMPOSED_MEMBER_CACHE:
				return composedMemberCache != null;
			case N4JSPackage.BINDING_PROPERTY__PROPERTY:
				return property != null;
			case N4JSPackage.BINDING_PROPERTY__PROPERTY_AS_TEXT:
				return PROPERTY_AS_TEXT_EDEFAULT == null ? propertyAsText != null : !PROPERTY_AS_TEXT_EDEFAULT.equals(propertyAsText);
			case N4JSPackage.BINDING_PROPERTY__VALUE:
				return value != null;
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
		if (baseClass == MemberAccess.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.BINDING_PROPERTY__COMPOSED_MEMBER_CACHE: return N4JSPackage.MEMBER_ACCESS__COMPOSED_MEMBER_CACHE;
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
		if (baseClass == MemberAccess.class) {
			switch (baseFeatureID) {
				case N4JSPackage.MEMBER_ACCESS__COMPOSED_MEMBER_CACHE: return N4JSPackage.BINDING_PROPERTY__COMPOSED_MEMBER_CACHE;
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
		if (baseClass == NamedElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.NAMED_ELEMENT___GET_NAME: return N4JSPackage.BINDING_PROPERTY___GET_NAME;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == PropertyNameOwner.class) {
			switch (baseOperationID) {
				case N4JSPackage.PROPERTY_NAME_OWNER___GET_NAME: return N4JSPackage.BINDING_PROPERTY___GET_NAME;
				case N4JSPackage.PROPERTY_NAME_OWNER___IS_VALID_NAME: return N4JSPackage.BINDING_PROPERTY___IS_VALID_NAME;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == MemberAccess.class) {
			switch (baseOperationID) {
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
			case N4JSPackage.BINDING_PROPERTY___GET_NAME:
				return getName();
			case N4JSPackage.BINDING_PROPERTY___IS_VALID_NAME:
				return isValidName();
			case N4JSPackage.BINDING_PROPERTY___IS_SINGLE_NAME_BINDING:
				return isSingleNameBinding();
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
		result.append(" (propertyAsText: ");
		result.append(propertyAsText);
		result.append(')');
		return result.toString();
	}

} //BindingPropertyImpl
