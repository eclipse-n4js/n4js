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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.n4JS.JSXAttribute;
import org.eclipse.n4js.n4JS.JSXChild;
import org.eclipse.n4js.n4JS.JSXElement;
import org.eclipse.n4js.n4JS.JSXElementName;
import org.eclipse.n4js.n4JS.N4JSPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JSX Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.JSXElementImpl#getJsxElementName <em>Jsx Element Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.JSXElementImpl#getJsxAttributes <em>Jsx Attributes</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.JSXElementImpl#getJsxChildren <em>Jsx Children</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.JSXElementImpl#getJsxClosingName <em>Jsx Closing Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JSXElementImpl extends ExpressionImpl implements JSXElement {
	/**
	 * The cached value of the '{@link #getJsxElementName() <em>Jsx Element Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJsxElementName()
	 * @generated
	 * @ordered
	 */
	protected JSXElementName jsxElementName;

	/**
	 * The cached value of the '{@link #getJsxAttributes() <em>Jsx Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJsxAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<JSXAttribute> jsxAttributes;

	/**
	 * The cached value of the '{@link #getJsxChildren() <em>Jsx Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJsxChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<JSXChild> jsxChildren;

	/**
	 * The cached value of the '{@link #getJsxClosingName() <em>Jsx Closing Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJsxClosingName()
	 * @generated
	 * @ordered
	 */
	protected JSXElementName jsxClosingName;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JSXElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.JSX_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public JSXElementName getJsxElementName() {
		return jsxElementName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJsxElementName(JSXElementName newJsxElementName, NotificationChain msgs) {
		JSXElementName oldJsxElementName = jsxElementName;
		jsxElementName = newJsxElementName;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.JSX_ELEMENT__JSX_ELEMENT_NAME, oldJsxElementName, newJsxElementName);
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
	public void setJsxElementName(JSXElementName newJsxElementName) {
		if (newJsxElementName != jsxElementName) {
			NotificationChain msgs = null;
			if (jsxElementName != null)
				msgs = ((InternalEObject)jsxElementName).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.JSX_ELEMENT__JSX_ELEMENT_NAME, null, msgs);
			if (newJsxElementName != null)
				msgs = ((InternalEObject)newJsxElementName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.JSX_ELEMENT__JSX_ELEMENT_NAME, null, msgs);
			msgs = basicSetJsxElementName(newJsxElementName, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.JSX_ELEMENT__JSX_ELEMENT_NAME, newJsxElementName, newJsxElementName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<JSXAttribute> getJsxAttributes() {
		if (jsxAttributes == null) {
			jsxAttributes = new EObjectContainmentEList<JSXAttribute>(JSXAttribute.class, this, N4JSPackage.JSX_ELEMENT__JSX_ATTRIBUTES);
		}
		return jsxAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<JSXChild> getJsxChildren() {
		if (jsxChildren == null) {
			jsxChildren = new EObjectContainmentEList<JSXChild>(JSXChild.class, this, N4JSPackage.JSX_ELEMENT__JSX_CHILDREN);
		}
		return jsxChildren;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public JSXElementName getJsxClosingName() {
		return jsxClosingName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJsxClosingName(JSXElementName newJsxClosingName, NotificationChain msgs) {
		JSXElementName oldJsxClosingName = jsxClosingName;
		jsxClosingName = newJsxClosingName;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.JSX_ELEMENT__JSX_CLOSING_NAME, oldJsxClosingName, newJsxClosingName);
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
	public void setJsxClosingName(JSXElementName newJsxClosingName) {
		if (newJsxClosingName != jsxClosingName) {
			NotificationChain msgs = null;
			if (jsxClosingName != null)
				msgs = ((InternalEObject)jsxClosingName).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.JSX_ELEMENT__JSX_CLOSING_NAME, null, msgs);
			if (newJsxClosingName != null)
				msgs = ((InternalEObject)newJsxClosingName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.JSX_ELEMENT__JSX_CLOSING_NAME, null, msgs);
			msgs = basicSetJsxClosingName(newJsxClosingName, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.JSX_ELEMENT__JSX_CLOSING_NAME, newJsxClosingName, newJsxClosingName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.JSX_ELEMENT__JSX_ELEMENT_NAME:
				return basicSetJsxElementName(null, msgs);
			case N4JSPackage.JSX_ELEMENT__JSX_ATTRIBUTES:
				return ((InternalEList<?>)getJsxAttributes()).basicRemove(otherEnd, msgs);
			case N4JSPackage.JSX_ELEMENT__JSX_CHILDREN:
				return ((InternalEList<?>)getJsxChildren()).basicRemove(otherEnd, msgs);
			case N4JSPackage.JSX_ELEMENT__JSX_CLOSING_NAME:
				return basicSetJsxClosingName(null, msgs);
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
			case N4JSPackage.JSX_ELEMENT__JSX_ELEMENT_NAME:
				return getJsxElementName();
			case N4JSPackage.JSX_ELEMENT__JSX_ATTRIBUTES:
				return getJsxAttributes();
			case N4JSPackage.JSX_ELEMENT__JSX_CHILDREN:
				return getJsxChildren();
			case N4JSPackage.JSX_ELEMENT__JSX_CLOSING_NAME:
				return getJsxClosingName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case N4JSPackage.JSX_ELEMENT__JSX_ELEMENT_NAME:
				setJsxElementName((JSXElementName)newValue);
				return;
			case N4JSPackage.JSX_ELEMENT__JSX_ATTRIBUTES:
				getJsxAttributes().clear();
				getJsxAttributes().addAll((Collection<? extends JSXAttribute>)newValue);
				return;
			case N4JSPackage.JSX_ELEMENT__JSX_CHILDREN:
				getJsxChildren().clear();
				getJsxChildren().addAll((Collection<? extends JSXChild>)newValue);
				return;
			case N4JSPackage.JSX_ELEMENT__JSX_CLOSING_NAME:
				setJsxClosingName((JSXElementName)newValue);
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
			case N4JSPackage.JSX_ELEMENT__JSX_ELEMENT_NAME:
				setJsxElementName((JSXElementName)null);
				return;
			case N4JSPackage.JSX_ELEMENT__JSX_ATTRIBUTES:
				getJsxAttributes().clear();
				return;
			case N4JSPackage.JSX_ELEMENT__JSX_CHILDREN:
				getJsxChildren().clear();
				return;
			case N4JSPackage.JSX_ELEMENT__JSX_CLOSING_NAME:
				setJsxClosingName((JSXElementName)null);
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
			case N4JSPackage.JSX_ELEMENT__JSX_ELEMENT_NAME:
				return jsxElementName != null;
			case N4JSPackage.JSX_ELEMENT__JSX_ATTRIBUTES:
				return jsxAttributes != null && !jsxAttributes.isEmpty();
			case N4JSPackage.JSX_ELEMENT__JSX_CHILDREN:
				return jsxChildren != null && !jsxChildren.isEmpty();
			case N4JSPackage.JSX_ELEMENT__JSX_CLOSING_NAME:
				return jsxClosingName != null;
		}
		return super.eIsSet(featureID);
	}

} //JSXElementImpl
