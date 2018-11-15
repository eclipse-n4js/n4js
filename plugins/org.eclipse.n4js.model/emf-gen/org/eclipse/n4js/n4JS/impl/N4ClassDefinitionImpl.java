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

import com.google.common.collect.Iterables;

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

import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.N4ClassDefinition;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.ThisTarget;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;

import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.Type;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>N4 Class Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4ClassDefinitionImpl#getSuperClassRef <em>Super Class Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4ClassDefinitionImpl#getSuperClassExpression <em>Super Class Expression</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4ClassDefinitionImpl#getImplementedInterfaceRefs <em>Implemented Interface Refs</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class N4ClassDefinitionImpl extends N4ClassifierDefinitionImpl implements N4ClassDefinition {
	/**
	 * The cached value of the '{@link #getSuperClassRef() <em>Super Class Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperClassRef()
	 * @generated
	 * @ordered
	 */
	protected ParameterizedTypeRef superClassRef;

	/**
	 * The cached value of the '{@link #getSuperClassExpression() <em>Super Class Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperClassExpression()
	 * @generated
	 * @ordered
	 */
	protected Expression superClassExpression;

	/**
	 * The cached value of the '{@link #getImplementedInterfaceRefs() <em>Implemented Interface Refs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementedInterfaceRefs()
	 * @generated
	 * @ordered
	 */
	protected EList<ParameterizedTypeRef> implementedInterfaceRefs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected N4ClassDefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.N4_CLASS_DEFINITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterizedTypeRef getSuperClassRef() {
		return superClassRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSuperClassRef(ParameterizedTypeRef newSuperClassRef, NotificationChain msgs) {
		ParameterizedTypeRef oldSuperClassRef = superClassRef;
		superClassRef = newSuperClassRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_CLASS_DEFINITION__SUPER_CLASS_REF, oldSuperClassRef, newSuperClassRef);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuperClassRef(ParameterizedTypeRef newSuperClassRef) {
		if (newSuperClassRef != superClassRef) {
			NotificationChain msgs = null;
			if (superClassRef != null)
				msgs = ((InternalEObject)superClassRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_CLASS_DEFINITION__SUPER_CLASS_REF, null, msgs);
			if (newSuperClassRef != null)
				msgs = ((InternalEObject)newSuperClassRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_CLASS_DEFINITION__SUPER_CLASS_REF, null, msgs);
			msgs = basicSetSuperClassRef(newSuperClassRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_CLASS_DEFINITION__SUPER_CLASS_REF, newSuperClassRef, newSuperClassRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getSuperClassExpression() {
		return superClassExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSuperClassExpression(Expression newSuperClassExpression, NotificationChain msgs) {
		Expression oldSuperClassExpression = superClassExpression;
		superClassExpression = newSuperClassExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_CLASS_DEFINITION__SUPER_CLASS_EXPRESSION, oldSuperClassExpression, newSuperClassExpression);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuperClassExpression(Expression newSuperClassExpression) {
		if (newSuperClassExpression != superClassExpression) {
			NotificationChain msgs = null;
			if (superClassExpression != null)
				msgs = ((InternalEObject)superClassExpression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_CLASS_DEFINITION__SUPER_CLASS_EXPRESSION, null, msgs);
			if (newSuperClassExpression != null)
				msgs = ((InternalEObject)newSuperClassExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.N4_CLASS_DEFINITION__SUPER_CLASS_EXPRESSION, null, msgs);
			msgs = basicSetSuperClassExpression(newSuperClassExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_CLASS_DEFINITION__SUPER_CLASS_EXPRESSION, newSuperClassExpression, newSuperClassExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ParameterizedTypeRef> getImplementedInterfaceRefs() {
		if (implementedInterfaceRefs == null) {
			implementedInterfaceRefs = new EObjectContainmentEList<ParameterizedTypeRef>(ParameterizedTypeRef.class, this, N4JSPackage.N4_CLASS_DEFINITION__IMPLEMENTED_INTERFACE_REFS);
		}
		return implementedInterfaceRefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TClass getDefinedTypeAsClass() {
		Type _definedType = this.getDefinedType();
		return ((TClass) _definedType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Iterable<ParameterizedTypeRef> getSuperClassifierRefs() {
		ParameterizedTypeRef _superClassRef = this.getSuperClassRef();
		EList<ParameterizedTypeRef> _implementedInterfaceRefs = this.getImplementedInterfaceRefs();
		return Iterables.<ParameterizedTypeRef>concat(java.util.Collections.<ParameterizedTypeRef>unmodifiableList(org.eclipse.xtext.xbase.lib.CollectionLiterals.<ParameterizedTypeRef>newArrayList(_superClassRef)), _implementedInterfaceRefs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Iterable<ParameterizedTypeRef> getImplementedOrExtendedInterfaceRefs() {
		return this.getImplementedInterfaceRefs();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.N4_CLASS_DEFINITION__SUPER_CLASS_REF:
				return basicSetSuperClassRef(null, msgs);
			case N4JSPackage.N4_CLASS_DEFINITION__SUPER_CLASS_EXPRESSION:
				return basicSetSuperClassExpression(null, msgs);
			case N4JSPackage.N4_CLASS_DEFINITION__IMPLEMENTED_INTERFACE_REFS:
				return ((InternalEList<?>)getImplementedInterfaceRefs()).basicRemove(otherEnd, msgs);
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
			case N4JSPackage.N4_CLASS_DEFINITION__SUPER_CLASS_REF:
				return getSuperClassRef();
			case N4JSPackage.N4_CLASS_DEFINITION__SUPER_CLASS_EXPRESSION:
				return getSuperClassExpression();
			case N4JSPackage.N4_CLASS_DEFINITION__IMPLEMENTED_INTERFACE_REFS:
				return getImplementedInterfaceRefs();
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
			case N4JSPackage.N4_CLASS_DEFINITION__SUPER_CLASS_REF:
				setSuperClassRef((ParameterizedTypeRef)newValue);
				return;
			case N4JSPackage.N4_CLASS_DEFINITION__SUPER_CLASS_EXPRESSION:
				setSuperClassExpression((Expression)newValue);
				return;
			case N4JSPackage.N4_CLASS_DEFINITION__IMPLEMENTED_INTERFACE_REFS:
				getImplementedInterfaceRefs().clear();
				getImplementedInterfaceRefs().addAll((Collection<? extends ParameterizedTypeRef>)newValue);
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
			case N4JSPackage.N4_CLASS_DEFINITION__SUPER_CLASS_REF:
				setSuperClassRef((ParameterizedTypeRef)null);
				return;
			case N4JSPackage.N4_CLASS_DEFINITION__SUPER_CLASS_EXPRESSION:
				setSuperClassExpression((Expression)null);
				return;
			case N4JSPackage.N4_CLASS_DEFINITION__IMPLEMENTED_INTERFACE_REFS:
				getImplementedInterfaceRefs().clear();
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
			case N4JSPackage.N4_CLASS_DEFINITION__SUPER_CLASS_REF:
				return superClassRef != null;
			case N4JSPackage.N4_CLASS_DEFINITION__SUPER_CLASS_EXPRESSION:
				return superClassExpression != null;
			case N4JSPackage.N4_CLASS_DEFINITION__IMPLEMENTED_INTERFACE_REFS:
				return implementedInterfaceRefs != null && !implementedInterfaceRefs.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == N4ClassifierDefinition.class) {
			switch (baseOperationID) {
				case N4JSPackage.N4_CLASSIFIER_DEFINITION___GET_SUPER_CLASSIFIER_REFS: return N4JSPackage.N4_CLASS_DEFINITION___GET_SUPER_CLASSIFIER_REFS;
				case N4JSPackage.N4_CLASSIFIER_DEFINITION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS: return N4JSPackage.N4_CLASS_DEFINITION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == ThisTarget.class) {
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
			case N4JSPackage.N4_CLASS_DEFINITION___GET_DEFINED_TYPE_AS_CLASS:
				return getDefinedTypeAsClass();
			case N4JSPackage.N4_CLASS_DEFINITION___GET_SUPER_CLASSIFIER_REFS:
				return getSuperClassifierRefs();
			case N4JSPackage.N4_CLASS_DEFINITION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS:
				return getImplementedOrExtendedInterfaceRefs();
		}
		return super.eInvoke(operationID, arguments);
	}

} //N4ClassDefinitionImpl
