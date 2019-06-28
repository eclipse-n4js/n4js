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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.JSXPropertyAttribute;
import org.eclipse.n4js.n4JS.MemberAccess;
import org.eclipse.n4js.n4JS.N4JSPackage;

import org.eclipse.n4js.ts.types.ComposedMemberCache;
import org.eclipse.n4js.ts.types.IdentifiableElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JSX Property Attribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.JSXPropertyAttributeImpl#getComposedMemberCache <em>Composed Member Cache</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.JSXPropertyAttributeImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.JSXPropertyAttributeImpl#getPropertyAsText <em>Property As Text</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.JSXPropertyAttributeImpl#getJsxAttributeValue <em>Jsx Attribute Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JSXPropertyAttributeImpl extends JSXAttributeImpl implements JSXPropertyAttribute {
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
	 * The cached value of the '{@link #getJsxAttributeValue() <em>Jsx Attribute Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJsxAttributeValue()
	 * @generated
	 * @ordered
	 */
	protected Expression jsxAttributeValue;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JSXPropertyAttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.JSX_PROPERTY_ATTRIBUTE;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.JSX_PROPERTY_ATTRIBUTE__COMPOSED_MEMBER_CACHE, oldComposedMemberCache, composedMemberCache));
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
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.JSX_PROPERTY_ATTRIBUTE__COMPOSED_MEMBER_CACHE, oldComposedMemberCache, composedMemberCache));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.JSX_PROPERTY_ATTRIBUTE__PROPERTY, oldProperty, property));
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
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.JSX_PROPERTY_ATTRIBUTE__PROPERTY, oldProperty, property));
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
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.JSX_PROPERTY_ATTRIBUTE__PROPERTY_AS_TEXT, oldPropertyAsText, propertyAsText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Expression getJsxAttributeValue() {
		return jsxAttributeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJsxAttributeValue(Expression newJsxAttributeValue, NotificationChain msgs) {
		Expression oldJsxAttributeValue = jsxAttributeValue;
		jsxAttributeValue = newJsxAttributeValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.JSX_PROPERTY_ATTRIBUTE__JSX_ATTRIBUTE_VALUE, oldJsxAttributeValue, newJsxAttributeValue);
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
	public void setJsxAttributeValue(Expression newJsxAttributeValue) {
		if (newJsxAttributeValue != jsxAttributeValue) {
			NotificationChain msgs = null;
			if (jsxAttributeValue != null)
				msgs = ((InternalEObject)jsxAttributeValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.JSX_PROPERTY_ATTRIBUTE__JSX_ATTRIBUTE_VALUE, null, msgs);
			if (newJsxAttributeValue != null)
				msgs = ((InternalEObject)newJsxAttributeValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.JSX_PROPERTY_ATTRIBUTE__JSX_ATTRIBUTE_VALUE, null, msgs);
			msgs = basicSetJsxAttributeValue(newJsxAttributeValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.JSX_PROPERTY_ATTRIBUTE__JSX_ATTRIBUTE_VALUE, newJsxAttributeValue, newJsxAttributeValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.JSX_PROPERTY_ATTRIBUTE__JSX_ATTRIBUTE_VALUE:
				return basicSetJsxAttributeValue(null, msgs);
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
			case N4JSPackage.JSX_PROPERTY_ATTRIBUTE__COMPOSED_MEMBER_CACHE:
				if (resolve) return getComposedMemberCache();
				return basicGetComposedMemberCache();
			case N4JSPackage.JSX_PROPERTY_ATTRIBUTE__PROPERTY:
				if (resolve) return getProperty();
				return basicGetProperty();
			case N4JSPackage.JSX_PROPERTY_ATTRIBUTE__PROPERTY_AS_TEXT:
				return getPropertyAsText();
			case N4JSPackage.JSX_PROPERTY_ATTRIBUTE__JSX_ATTRIBUTE_VALUE:
				return getJsxAttributeValue();
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
			case N4JSPackage.JSX_PROPERTY_ATTRIBUTE__COMPOSED_MEMBER_CACHE:
				setComposedMemberCache((ComposedMemberCache)newValue);
				return;
			case N4JSPackage.JSX_PROPERTY_ATTRIBUTE__PROPERTY:
				setProperty((IdentifiableElement)newValue);
				return;
			case N4JSPackage.JSX_PROPERTY_ATTRIBUTE__PROPERTY_AS_TEXT:
				setPropertyAsText((String)newValue);
				return;
			case N4JSPackage.JSX_PROPERTY_ATTRIBUTE__JSX_ATTRIBUTE_VALUE:
				setJsxAttributeValue((Expression)newValue);
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
			case N4JSPackage.JSX_PROPERTY_ATTRIBUTE__COMPOSED_MEMBER_CACHE:
				setComposedMemberCache((ComposedMemberCache)null);
				return;
			case N4JSPackage.JSX_PROPERTY_ATTRIBUTE__PROPERTY:
				setProperty((IdentifiableElement)null);
				return;
			case N4JSPackage.JSX_PROPERTY_ATTRIBUTE__PROPERTY_AS_TEXT:
				setPropertyAsText(PROPERTY_AS_TEXT_EDEFAULT);
				return;
			case N4JSPackage.JSX_PROPERTY_ATTRIBUTE__JSX_ATTRIBUTE_VALUE:
				setJsxAttributeValue((Expression)null);
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
			case N4JSPackage.JSX_PROPERTY_ATTRIBUTE__COMPOSED_MEMBER_CACHE:
				return composedMemberCache != null;
			case N4JSPackage.JSX_PROPERTY_ATTRIBUTE__PROPERTY:
				return property != null;
			case N4JSPackage.JSX_PROPERTY_ATTRIBUTE__PROPERTY_AS_TEXT:
				return PROPERTY_AS_TEXT_EDEFAULT == null ? propertyAsText != null : !PROPERTY_AS_TEXT_EDEFAULT.equals(propertyAsText);
			case N4JSPackage.JSX_PROPERTY_ATTRIBUTE__JSX_ATTRIBUTE_VALUE:
				return jsxAttributeValue != null;
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
				case N4JSPackage.JSX_PROPERTY_ATTRIBUTE__COMPOSED_MEMBER_CACHE: return N4JSPackage.MEMBER_ACCESS__COMPOSED_MEMBER_CACHE;
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
				case N4JSPackage.MEMBER_ACCESS__COMPOSED_MEMBER_CACHE: return N4JSPackage.JSX_PROPERTY_ATTRIBUTE__COMPOSED_MEMBER_CACHE;
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
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (propertyAsText: ");
		result.append(propertyAsText);
		result.append(')');
		return result.toString();
	}

} //JSXPropertyAttributeImpl
