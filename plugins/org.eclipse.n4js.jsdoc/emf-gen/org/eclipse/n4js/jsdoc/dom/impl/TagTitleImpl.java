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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.n4js.jsdoc.dom.DomPackage;
import org.eclipse.n4js.jsdoc.dom.Tag;
import org.eclipse.n4js.jsdoc.dom.TagTitle;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tag Title</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.impl.TagTitleImpl#getTag <em>Tag</em>}</li>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.impl.TagTitleImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.impl.TagTitleImpl#getActualTitle <em>Actual Title</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TagTitleImpl extends JSDocNodeImpl implements TagTitle {
	/**
	 * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected static final String TITLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected String title = TITLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getActualTitle() <em>Actual Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActualTitle()
	 * @generated
	 * @ordered
	 */
	protected static final String ACTUAL_TITLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getActualTitle() <em>Actual Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActualTitle()
	 * @generated
	 * @ordered
	 */
	protected String actualTitle = ACTUAL_TITLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TagTitleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomPackage.Literals.TAG_TITLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Tag getTag() {
		if (eContainerFeatureID() != DomPackage.TAG_TITLE__TAG) return null;
		return (Tag)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tag basicGetTag() {
		if (eContainerFeatureID() != DomPackage.TAG_TITLE__TAG) return null;
		return (Tag)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTag(Tag newTag, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newTag, DomPackage.TAG_TITLE__TAG, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTag(Tag newTag) {
		if (newTag != eInternalContainer() || (eContainerFeatureID() != DomPackage.TAG_TITLE__TAG && newTag != null)) {
			if (EcoreUtil.isAncestor(this, newTag))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newTag != null)
				msgs = ((InternalEObject)newTag).eInverseAdd(this, DomPackage.TAG__TITLE, Tag.class, msgs);
			msgs = basicSetTag(newTag, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomPackage.TAG_TITLE__TAG, newTag, newTag));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTitle(String newTitle) {
		String oldTitle = title;
		title = newTitle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomPackage.TAG_TITLE__TITLE, oldTitle, title));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getActualTitle() {
		return actualTitle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setActualTitle(String newActualTitle) {
		String oldActualTitle = actualTitle;
		actualTitle = newActualTitle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomPackage.TAG_TITLE__ACTUAL_TITLE, oldActualTitle, actualTitle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DomPackage.TAG_TITLE__TAG:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetTag((Tag)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DomPackage.TAG_TITLE__TAG:
				return basicSetTag(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case DomPackage.TAG_TITLE__TAG:
				return eInternalContainer().eInverseRemove(this, DomPackage.TAG__TITLE, Tag.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DomPackage.TAG_TITLE__TAG:
				if (resolve) return getTag();
				return basicGetTag();
			case DomPackage.TAG_TITLE__TITLE:
				return getTitle();
			case DomPackage.TAG_TITLE__ACTUAL_TITLE:
				return getActualTitle();
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
			case DomPackage.TAG_TITLE__TAG:
				setTag((Tag)newValue);
				return;
			case DomPackage.TAG_TITLE__TITLE:
				setTitle((String)newValue);
				return;
			case DomPackage.TAG_TITLE__ACTUAL_TITLE:
				setActualTitle((String)newValue);
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
			case DomPackage.TAG_TITLE__TAG:
				setTag((Tag)null);
				return;
			case DomPackage.TAG_TITLE__TITLE:
				setTitle(TITLE_EDEFAULT);
				return;
			case DomPackage.TAG_TITLE__ACTUAL_TITLE:
				setActualTitle(ACTUAL_TITLE_EDEFAULT);
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
			case DomPackage.TAG_TITLE__TAG:
				return basicGetTag() != null;
			case DomPackage.TAG_TITLE__TITLE:
				return TITLE_EDEFAULT == null ? title != null : !TITLE_EDEFAULT.equals(title);
			case DomPackage.TAG_TITLE__ACTUAL_TITLE:
				return ACTUAL_TITLE_EDEFAULT == null ? actualTitle != null : !ACTUAL_TITLE_EDEFAULT.equals(actualTitle);
		}
		return super.eIsSet(featureID);
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
		result.append(" (title: ");
		result.append(title);
		result.append(", actualTitle: ");
		result.append(actualTitle);
		result.append(')');
		return result.toString();
	}

} //TagTitleImpl
