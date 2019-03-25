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

import org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals;

import org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberAnnotationList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Annotable N4 Member Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.AnnotableN4MemberDeclarationImpl#getAnnotationList <em>Annotation List</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AnnotableN4MemberDeclarationImpl extends N4MemberDeclarationImpl implements AnnotableN4MemberDeclaration {
	/**
	 * The cached value of the '{@link #getAnnotationList() <em>Annotation List</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotationList()
	 * @generated
	 * @ordered
	 */
	protected N4MemberAnnotationList annotationList;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AnnotableN4MemberDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.ANNOTABLE_N4_MEMBER_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public N4MemberAnnotationList getAnnotationList() {
		return annotationList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAnnotationList(N4MemberAnnotationList newAnnotationList, NotificationChain msgs) {
		N4MemberAnnotationList oldAnnotationList = annotationList;
		annotationList = newAnnotationList;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.ANNOTABLE_N4_MEMBER_DECLARATION__ANNOTATION_LIST, oldAnnotationList, newAnnotationList);
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
	public void setAnnotationList(N4MemberAnnotationList newAnnotationList) {
		if (newAnnotationList != annotationList) {
			NotificationChain msgs = null;
			if (annotationList != null)
				msgs = ((InternalEObject)annotationList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.ANNOTABLE_N4_MEMBER_DECLARATION__ANNOTATION_LIST, null, msgs);
			if (newAnnotationList != null)
				msgs = ((InternalEObject)newAnnotationList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.ANNOTABLE_N4_MEMBER_DECLARATION__ANNOTATION_LIST, null, msgs);
			msgs = basicSetAnnotationList(newAnnotationList, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.ANNOTABLE_N4_MEMBER_DECLARATION__ANNOTATION_LIST, newAnnotationList, newAnnotationList));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Annotation> getAnnotations() {
		EList<Annotation> _elvis = null;
		N4MemberAnnotationList _annotationList = this.getAnnotationList();
		EList<Annotation> _annotations = null;
		if (_annotationList!=null) {
			_annotations=_annotationList.getAnnotations();
		}
		if (_annotations != null) {
			_elvis = _annotations;
		} else {
			EList<Annotation> _emptyEList = XcoreCollectionLiterals.<Annotation>emptyEList();
			_elvis = _emptyEList;
		}
		return _elvis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.ANNOTABLE_N4_MEMBER_DECLARATION__ANNOTATION_LIST:
				return basicSetAnnotationList(null, msgs);
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
			case N4JSPackage.ANNOTABLE_N4_MEMBER_DECLARATION__ANNOTATION_LIST:
				return getAnnotationList();
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
			case N4JSPackage.ANNOTABLE_N4_MEMBER_DECLARATION__ANNOTATION_LIST:
				setAnnotationList((N4MemberAnnotationList)newValue);
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
			case N4JSPackage.ANNOTABLE_N4_MEMBER_DECLARATION__ANNOTATION_LIST:
				setAnnotationList((N4MemberAnnotationList)null);
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
			case N4JSPackage.ANNOTABLE_N4_MEMBER_DECLARATION__ANNOTATION_LIST:
				return annotationList != null;
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
			case N4JSPackage.ANNOTABLE_N4_MEMBER_DECLARATION___GET_ANNOTATIONS:
				return getAnnotations();
		}
		return super.eInvoke(operationID, arguments);
	}

} //AnnotableN4MemberDeclarationImpl
