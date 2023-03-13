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
package org.eclipse.n4js.ts.types.impl;

import com.google.common.base.Objects;

import com.google.common.collect.Iterables;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;

import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TNamespaceElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TInterface</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TInterfaceImpl#getSubClassRefs <em>Sub Class Refs</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TInterfaceImpl#getSubInterfaceRefs <em>Sub Interface Refs</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TInterfaceImpl#getSuperInterfaceRefs <em>Super Interface Refs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TInterfaceImpl extends TN4ClassifierImpl implements TInterface {
	/**
	 * The cached value of the '{@link #getSubClassRefs() <em>Sub Class Refs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubClassRefs()
	 * @generated
	 * @ordered
	 */
	protected EList<ParameterizedTypeRef> subClassRefs;

	/**
	 * The cached value of the '{@link #getSubInterfaceRefs() <em>Sub Interface Refs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubInterfaceRefs()
	 * @generated
	 * @ordered
	 */
	protected EList<ParameterizedTypeRef> subInterfaceRefs;

	/**
	 * The cached value of the '{@link #getSuperInterfaceRefs() <em>Super Interface Refs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperInterfaceRefs()
	 * @generated
	 * @ordered
	 */
	protected EList<ParameterizedTypeRef> superInterfaceRefs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TInterfaceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TINTERFACE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ParameterizedTypeRef> getSubClassRefs() {
		if (subClassRefs == null) {
			subClassRefs = new EObjectContainmentEList<ParameterizedTypeRef>(ParameterizedTypeRef.class, this, TypesPackage.TINTERFACE__SUB_CLASS_REFS);
		}
		return subClassRefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ParameterizedTypeRef> getSubInterfaceRefs() {
		if (subInterfaceRefs == null) {
			subInterfaceRefs = new EObjectContainmentEList<ParameterizedTypeRef>(ParameterizedTypeRef.class, this, TypesPackage.TINTERFACE__SUB_INTERFACE_REFS);
		}
		return subInterfaceRefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ParameterizedTypeRef> getSuperInterfaceRefs() {
		if (superInterfaceRefs == null) {
			superInterfaceRefs = new EObjectContainmentEList<ParameterizedTypeRef>(ParameterizedTypeRef.class, this, TypesPackage.TINTERFACE__SUPER_INTERFACE_REFS);
		}
		return superInterfaceRefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAbstract() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterable<ParameterizedTypeRef> getSuperClassifierRefs() {
		return this.getSuperInterfaceRefs();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterable<ParameterizedTypeRef> getSubClassifierRefs() {
		EList<ParameterizedTypeRef> _subClassRefs = this.getSubClassRefs();
		EList<ParameterizedTypeRef> _subInterfaceRefs = this.getSubInterfaceRefs();
		return Iterables.<ParameterizedTypeRef>concat(_subClassRefs, _subInterfaceRefs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterable<ParameterizedTypeRef> getImplementedOrExtendedInterfaceRefs() {
		return this.getSuperInterfaceRefs();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isHollow() {
		return (this.isExternal() && (!IterableExtensions.<TAnnotation>exists(this.getAnnotations(), new Function1<TAnnotation, Boolean>() {
			public Boolean apply(final TAnnotation it) {
				String _name = it.getName();
				return Boolean.valueOf(Objects.equal(_name, "N4JS"));
			}
		})));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.TINTERFACE__SUB_CLASS_REFS:
				return ((InternalEList<?>)getSubClassRefs()).basicRemove(otherEnd, msgs);
			case TypesPackage.TINTERFACE__SUB_INTERFACE_REFS:
				return ((InternalEList<?>)getSubInterfaceRefs()).basicRemove(otherEnd, msgs);
			case TypesPackage.TINTERFACE__SUPER_INTERFACE_REFS:
				return ((InternalEList<?>)getSuperInterfaceRefs()).basicRemove(otherEnd, msgs);
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
			case TypesPackage.TINTERFACE__SUB_CLASS_REFS:
				return getSubClassRefs();
			case TypesPackage.TINTERFACE__SUB_INTERFACE_REFS:
				return getSubInterfaceRefs();
			case TypesPackage.TINTERFACE__SUPER_INTERFACE_REFS:
				return getSuperInterfaceRefs();
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
			case TypesPackage.TINTERFACE__SUB_CLASS_REFS:
				getSubClassRefs().clear();
				getSubClassRefs().addAll((Collection<? extends ParameterizedTypeRef>)newValue);
				return;
			case TypesPackage.TINTERFACE__SUB_INTERFACE_REFS:
				getSubInterfaceRefs().clear();
				getSubInterfaceRefs().addAll((Collection<? extends ParameterizedTypeRef>)newValue);
				return;
			case TypesPackage.TINTERFACE__SUPER_INTERFACE_REFS:
				getSuperInterfaceRefs().clear();
				getSuperInterfaceRefs().addAll((Collection<? extends ParameterizedTypeRef>)newValue);
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
			case TypesPackage.TINTERFACE__SUB_CLASS_REFS:
				getSubClassRefs().clear();
				return;
			case TypesPackage.TINTERFACE__SUB_INTERFACE_REFS:
				getSubInterfaceRefs().clear();
				return;
			case TypesPackage.TINTERFACE__SUPER_INTERFACE_REFS:
				getSuperInterfaceRefs().clear();
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
			case TypesPackage.TINTERFACE__SUB_CLASS_REFS:
				return subClassRefs != null && !subClassRefs.isEmpty();
			case TypesPackage.TINTERFACE__SUB_INTERFACE_REFS:
				return subInterfaceRefs != null && !subInterfaceRefs.isEmpty();
			case TypesPackage.TINTERFACE__SUPER_INTERFACE_REFS:
				return superInterfaceRefs != null && !superInterfaceRefs.isEmpty();
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
		if (baseClass == TNamespaceElement.class) {
			switch (baseOperationID) {
				case TypesPackage.TNAMESPACE_ELEMENT___IS_HOLLOW: return TypesPackage.TINTERFACE___IS_HOLLOW;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == Type.class) {
			switch (baseOperationID) {
				case TypesPackage.TYPE___IS_HOLLOW: return TypesPackage.TINTERFACE___IS_HOLLOW;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TClassifier.class) {
			switch (baseOperationID) {
				case TypesPackage.TCLASSIFIER___IS_ABSTRACT: return TypesPackage.TINTERFACE___IS_ABSTRACT;
				case TypesPackage.TCLASSIFIER___GET_SUB_CLASSIFIER_REFS: return TypesPackage.TINTERFACE___GET_SUB_CLASSIFIER_REFS;
				case TypesPackage.TCLASSIFIER___GET_SUPER_CLASSIFIER_REFS: return TypesPackage.TINTERFACE___GET_SUPER_CLASSIFIER_REFS;
				case TypesPackage.TCLASSIFIER___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS: return TypesPackage.TINTERFACE___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
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
			case TypesPackage.TINTERFACE___IS_ABSTRACT:
				return isAbstract();
			case TypesPackage.TINTERFACE___GET_SUPER_CLASSIFIER_REFS:
				return getSuperClassifierRefs();
			case TypesPackage.TINTERFACE___GET_SUB_CLASSIFIER_REFS:
				return getSubClassifierRefs();
			case TypesPackage.TINTERFACE___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS:
				return getImplementedOrExtendedInterfaceRefs();
			case TypesPackage.TINTERFACE___IS_HOLLOW:
				return isHollow();
		}
		return super.eInvoke(operationID, arguments);
	}

} //TInterfaceImpl
