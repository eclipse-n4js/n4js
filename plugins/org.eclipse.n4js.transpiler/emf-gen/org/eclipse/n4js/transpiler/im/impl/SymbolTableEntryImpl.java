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
package org.eclipse.n4js.transpiler.im.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.n4JS.NamedElement;

import org.eclipse.n4js.transpiler.im.ImPackage;
import org.eclipse.n4js.transpiler.im.ReferencingElement_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Symbol Table Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryImpl#getElementsOfThisName <em>Elements Of This Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryImpl#getReferencingElements <em>Referencing Elements</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class SymbolTableEntryImpl extends MinimalEObjectImpl.Container implements SymbolTableEntry {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getElementsOfThisName() <em>Elements Of This Name</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementsOfThisName()
	 * @generated
	 * @ordered
	 */
	protected EList<NamedElement> elementsOfThisName;

	/**
	 * The cached value of the '{@link #getReferencingElements() <em>Referencing Elements</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferencingElements()
	 * @generated
	 * @ordered
	 */
	protected EList<ReferencingElement_IM> referencingElements;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SymbolTableEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImPackage.Literals.SYMBOL_TABLE_ENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.SYMBOL_TABLE_ENTRY__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NamedElement> getElementsOfThisName() {
		if (elementsOfThisName == null) {
			elementsOfThisName = new EObjectResolvingEList<NamedElement>(NamedElement.class, this, ImPackage.SYMBOL_TABLE_ENTRY__ELEMENTS_OF_THIS_NAME);
		}
		return elementsOfThisName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ReferencingElement_IM> getReferencingElements() {
		if (referencingElements == null) {
			referencingElements = new EObjectWithInverseResolvingEList<ReferencingElement_IM>(ReferencingElement_IM.class, this, ImPackage.SYMBOL_TABLE_ENTRY__REFERENCING_ELEMENTS, ImPackage.REFERENCING_ELEMENT_IM__REWIRED_TARGET);
		}
		return referencingElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImPackage.SYMBOL_TABLE_ENTRY__REFERENCING_ELEMENTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getReferencingElements()).basicAdd(otherEnd, msgs);
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
			case ImPackage.SYMBOL_TABLE_ENTRY__REFERENCING_ELEMENTS:
				return ((InternalEList<?>)getReferencingElements()).basicRemove(otherEnd, msgs);
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
			case ImPackage.SYMBOL_TABLE_ENTRY__NAME:
				return getName();
			case ImPackage.SYMBOL_TABLE_ENTRY__ELEMENTS_OF_THIS_NAME:
				return getElementsOfThisName();
			case ImPackage.SYMBOL_TABLE_ENTRY__REFERENCING_ELEMENTS:
				return getReferencingElements();
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
			case ImPackage.SYMBOL_TABLE_ENTRY__NAME:
				setName((String)newValue);
				return;
			case ImPackage.SYMBOL_TABLE_ENTRY__ELEMENTS_OF_THIS_NAME:
				getElementsOfThisName().clear();
				getElementsOfThisName().addAll((Collection<? extends NamedElement>)newValue);
				return;
			case ImPackage.SYMBOL_TABLE_ENTRY__REFERENCING_ELEMENTS:
				getReferencingElements().clear();
				getReferencingElements().addAll((Collection<? extends ReferencingElement_IM>)newValue);
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
			case ImPackage.SYMBOL_TABLE_ENTRY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ImPackage.SYMBOL_TABLE_ENTRY__ELEMENTS_OF_THIS_NAME:
				getElementsOfThisName().clear();
				return;
			case ImPackage.SYMBOL_TABLE_ENTRY__REFERENCING_ELEMENTS:
				getReferencingElements().clear();
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
			case ImPackage.SYMBOL_TABLE_ENTRY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ImPackage.SYMBOL_TABLE_ENTRY__ELEMENTS_OF_THIS_NAME:
				return elementsOfThisName != null && !elementsOfThisName.isEmpty();
			case ImPackage.SYMBOL_TABLE_ENTRY__REFERENCING_ELEMENTS:
				return referencingElements != null && !referencingElements.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //SymbolTableEntryImpl
