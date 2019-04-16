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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.n4js.jsdoc.dom.DocletElement;
import org.eclipse.n4js.jsdoc.dom.DomPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Doclet Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.impl.DocletElementImpl#getBegin <em>Begin</em>}</li>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.impl.DocletElementImpl#getEnd <em>End</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class DocletElementImpl extends MinimalEObjectImpl.Container implements DocletElement {
	/**
	 * The default value of the '{@link #getBegin() <em>Begin</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBegin()
	 * @generated
	 * @ordered
	 */
	protected static final int BEGIN_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getBegin() <em>Begin</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBegin()
	 * @generated
	 * @ordered
	 */
	protected int begin = BEGIN_EDEFAULT;

	/**
	 * The default value of the '{@link #getEnd() <em>End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnd()
	 * @generated
	 * @ordered
	 */
	protected static final int END_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getEnd() <em>End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnd()
	 * @generated
	 * @ordered
	 */
	protected int end = END_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocletElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomPackage.Literals.DOCLET_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getBegin() {
		return begin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBegin(int newBegin) {
		int oldBegin = begin;
		begin = newBegin;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomPackage.DOCLET_ELEMENT__BEGIN, oldBegin, begin));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getEnd() {
		return end;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEnd(int newEnd) {
		int oldEnd = end;
		end = newEnd;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomPackage.DOCLET_ELEMENT__END, oldEnd, end));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRange(final int begin, final int end) {
		this.setBegin(begin);
		this.setEnd(end);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean covers(final int offset) {
		return ((this.getBegin() <= offset) && (this.getEnd() > offset));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DomPackage.DOCLET_ELEMENT__BEGIN:
				return getBegin();
			case DomPackage.DOCLET_ELEMENT__END:
				return getEnd();
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
			case DomPackage.DOCLET_ELEMENT__BEGIN:
				setBegin((Integer)newValue);
				return;
			case DomPackage.DOCLET_ELEMENT__END:
				setEnd((Integer)newValue);
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
			case DomPackage.DOCLET_ELEMENT__BEGIN:
				setBegin(BEGIN_EDEFAULT);
				return;
			case DomPackage.DOCLET_ELEMENT__END:
				setEnd(END_EDEFAULT);
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
			case DomPackage.DOCLET_ELEMENT__BEGIN:
				return begin != BEGIN_EDEFAULT;
			case DomPackage.DOCLET_ELEMENT__END:
				return end != END_EDEFAULT;
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
			case DomPackage.DOCLET_ELEMENT___SET_RANGE__INT_INT:
				setRange((Integer)arguments.get(0), (Integer)arguments.get(1));
				return null;
			case DomPackage.DOCLET_ELEMENT___COVERS__INT:
				return covers((Integer)arguments.get(0));
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
		result.append(" (begin: ");
		result.append(begin);
		result.append(", end: ");
		result.append(end);
		result.append(')');
		return result.toString();
	}

} //DocletElementImpl
