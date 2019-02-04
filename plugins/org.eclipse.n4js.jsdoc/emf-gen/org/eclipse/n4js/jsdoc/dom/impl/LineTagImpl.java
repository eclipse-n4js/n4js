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

import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.eclipse.n4js.jsdoc.dom.DomPackage;
import org.eclipse.n4js.jsdoc.dom.LineTag;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Line Tag</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.impl.LineTagImpl#getDoclet <em>Doclet</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LineTagImpl extends TagImpl implements LineTag {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LineTagImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomPackage.Literals.LINE_TAG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Doclet getDoclet() {
		if (eContainerFeatureID() != DomPackage.LINE_TAG__DOCLET) return null;
		return (Doclet)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Doclet basicGetDoclet() {
		if (eContainerFeatureID() != DomPackage.LINE_TAG__DOCLET) return null;
		return (Doclet)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDoclet(Doclet newDoclet, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newDoclet, DomPackage.LINE_TAG__DOCLET, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDoclet(Doclet newDoclet) {
		if (newDoclet != eInternalContainer() || (eContainerFeatureID() != DomPackage.LINE_TAG__DOCLET && newDoclet != null)) {
			if (EcoreUtil.isAncestor(this, newDoclet))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newDoclet != null)
				msgs = ((InternalEObject)newDoclet).eInverseAdd(this, DomPackage.DOCLET__LINE_TAGS, Doclet.class, msgs);
			msgs = basicSetDoclet(newDoclet, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomPackage.LINE_TAG__DOCLET, newDoclet, newDoclet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DomPackage.LINE_TAG__DOCLET:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetDoclet((Doclet)otherEnd, msgs);
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
			case DomPackage.LINE_TAG__DOCLET:
				return basicSetDoclet(null, msgs);
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
			case DomPackage.LINE_TAG__DOCLET:
				return eInternalContainer().eInverseRemove(this, DomPackage.DOCLET__LINE_TAGS, Doclet.class, msgs);
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
			case DomPackage.LINE_TAG__DOCLET:
				if (resolve) return getDoclet();
				return basicGetDoclet();
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
			case DomPackage.LINE_TAG__DOCLET:
				setDoclet((Doclet)newValue);
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
			case DomPackage.LINE_TAG__DOCLET:
				setDoclet((Doclet)null);
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
			case DomPackage.LINE_TAG__DOCLET:
				return basicGetDoclet() != null;
		}
		return super.eIsSet(featureID);
	}

} //LineTagImpl
