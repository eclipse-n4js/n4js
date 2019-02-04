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

import com.google.common.base.Objects;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.eclipse.n4js.jsdoc.dom.DomPackage;
import org.eclipse.n4js.jsdoc.dom.LineTag;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Doclet</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.impl.DocletImpl#getLineTags <em>Line Tags</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DocletImpl extends CompositeImpl implements Doclet {
	/**
	 * The cached value of the '{@link #getLineTags() <em>Line Tags</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineTags()
	 * @generated
	 * @ordered
	 */
	protected EList<LineTag> lineTags;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocletImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomPackage.Literals.DOCLET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<LineTag> getLineTags() {
		if (lineTags == null) {
			lineTags = new EObjectContainmentWithInverseEList<LineTag>(LineTag.class, this, DomPackage.DOCLET__LINE_TAGS, DomPackage.LINE_TAG__DOCLET);
		}
		return lineTags;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean hasLineTag(final String title) {
		final Function1<LineTag, Boolean> _function = new Function1<LineTag, Boolean>() {
			public Boolean apply(final LineTag it) {
				String _title = it.getTitle().getTitle();
				return Boolean.valueOf(Objects.equal(_title, title));
			}
		};
		LineTag _findFirst = IterableExtensions.<LineTag>findFirst(this.getLineTags(), _function);
		return (_findFirst != null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<LineTag> lineTags(final String title) {
		final Function1<LineTag, Boolean> _function = new Function1<LineTag, Boolean>() {
			public Boolean apply(final LineTag it) {
				String _title = it.getTitle().getTitle();
				return Boolean.valueOf(Objects.equal(_title, title));
			}
		};
		return ECollections.<LineTag>toEList(IterableExtensions.<LineTag>filter(this.getLineTags(), _function));
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
			case DomPackage.DOCLET__LINE_TAGS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getLineTags()).basicAdd(otherEnd, msgs);
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
			case DomPackage.DOCLET__LINE_TAGS:
				return ((InternalEList<?>)getLineTags()).basicRemove(otherEnd, msgs);
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
			case DomPackage.DOCLET__LINE_TAGS:
				return getLineTags();
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
			case DomPackage.DOCLET__LINE_TAGS:
				getLineTags().clear();
				getLineTags().addAll((Collection<? extends LineTag>)newValue);
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
			case DomPackage.DOCLET__LINE_TAGS:
				getLineTags().clear();
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
			case DomPackage.DOCLET__LINE_TAGS:
				return lineTags != null && !lineTags.isEmpty();
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
			case DomPackage.DOCLET___HAS_LINE_TAG__STRING:
				return hasLineTag((String)arguments.get(0));
			case DomPackage.DOCLET___LINE_TAGS__STRING:
				return lineTags((String)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //DocletImpl
