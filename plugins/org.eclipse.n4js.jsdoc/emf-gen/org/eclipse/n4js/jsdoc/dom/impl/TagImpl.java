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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.jsdoc.ITagDefinition;
import org.eclipse.n4js.jsdoc.JSDocSerializer;

import org.eclipse.n4js.jsdoc.dom.DomPackage;
import org.eclipse.n4js.jsdoc.dom.Tag;
import org.eclipse.n4js.jsdoc.dom.TagTitle;
import org.eclipse.n4js.jsdoc.dom.TagValue;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tag</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.impl.TagImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.impl.TagImpl#getValues <em>Values</em>}</li>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.impl.TagImpl#getTagDefinition <em>Tag Definition</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class TagImpl extends DocletElementImpl implements Tag {
	/**
	 * The cached value of the '{@link #getTitle() <em>Title</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected TagTitle title;

	/**
	 * The cached value of the '{@link #getValues() <em>Values</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValues()
	 * @generated
	 * @ordered
	 */
	protected EList<TagValue> values;

	/**
	 * The default value of the '{@link #getTagDefinition() <em>Tag Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTagDefinition()
	 * @generated
	 * @ordered
	 */
	protected static final ITagDefinition TAG_DEFINITION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTagDefinition() <em>Tag Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTagDefinition()
	 * @generated
	 * @ordered
	 */
	protected ITagDefinition tagDefinition = TAG_DEFINITION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TagImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomPackage.Literals.TAG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TagTitle getTitle() {
		return title;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTitle(TagTitle newTitle, NotificationChain msgs) {
		TagTitle oldTitle = title;
		title = newTitle;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DomPackage.TAG__TITLE, oldTitle, newTitle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTitle(TagTitle newTitle) {
		if (newTitle != title) {
			NotificationChain msgs = null;
			if (title != null)
				msgs = ((InternalEObject)title).eInverseRemove(this, DomPackage.TAG_TITLE__TAG, TagTitle.class, msgs);
			if (newTitle != null)
				msgs = ((InternalEObject)newTitle).eInverseAdd(this, DomPackage.TAG_TITLE__TAG, TagTitle.class, msgs);
			msgs = basicSetTitle(newTitle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomPackage.TAG__TITLE, newTitle, newTitle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TagValue> getValues() {
		if (values == null) {
			values = new EObjectContainmentEList<TagValue>(TagValue.class, this, DomPackage.TAG__VALUES);
		}
		return values;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ITagDefinition getTagDefinition() {
		return tagDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTagDefinition(ITagDefinition newTagDefinition) {
		ITagDefinition oldTagDefinition = tagDefinition;
		tagDefinition = newTagDefinition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomPackage.TAG__TAG_DEFINITION, oldTagDefinition, tagDefinition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TagValue getValueByKey(final String theKey) {
		EList<TagValue> _values = this.getValues();
		final Function1<TagValue, Boolean> _function = new Function1<TagValue, Boolean>() {
			public Boolean apply(final TagValue it) {
				String _key = it.getKey();
				return Boolean.valueOf(Objects.equal(_key, theKey));
			}
		};
		return IterableExtensions.<TagValue>findFirst(_values, _function);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		return JSDocSerializer.toJSDocString(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DomPackage.TAG__TITLE:
				if (title != null)
					msgs = ((InternalEObject)title).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DomPackage.TAG__TITLE, null, msgs);
				return basicSetTitle((TagTitle)otherEnd, msgs);
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
			case DomPackage.TAG__TITLE:
				return basicSetTitle(null, msgs);
			case DomPackage.TAG__VALUES:
				return ((InternalEList<?>)getValues()).basicRemove(otherEnd, msgs);
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
			case DomPackage.TAG__TITLE:
				return getTitle();
			case DomPackage.TAG__VALUES:
				return getValues();
			case DomPackage.TAG__TAG_DEFINITION:
				return getTagDefinition();
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
			case DomPackage.TAG__TITLE:
				setTitle((TagTitle)newValue);
				return;
			case DomPackage.TAG__VALUES:
				getValues().clear();
				getValues().addAll((Collection<? extends TagValue>)newValue);
				return;
			case DomPackage.TAG__TAG_DEFINITION:
				setTagDefinition((ITagDefinition)newValue);
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
			case DomPackage.TAG__TITLE:
				setTitle((TagTitle)null);
				return;
			case DomPackage.TAG__VALUES:
				getValues().clear();
				return;
			case DomPackage.TAG__TAG_DEFINITION:
				setTagDefinition(TAG_DEFINITION_EDEFAULT);
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
			case DomPackage.TAG__TITLE:
				return title != null;
			case DomPackage.TAG__VALUES:
				return values != null && !values.isEmpty();
			case DomPackage.TAG__TAG_DEFINITION:
				return TAG_DEFINITION_EDEFAULT == null ? tagDefinition != null : !TAG_DEFINITION_EDEFAULT.equals(tagDefinition);
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
			case DomPackage.TAG___GET_VALUE_BY_KEY__STRING:
				return getValueByKey((String)arguments.get(0));
			case DomPackage.TAG___TO_STRING:
				return toString();
		}
		return super.eInvoke(operationID, arguments);
	}

} //TagImpl
