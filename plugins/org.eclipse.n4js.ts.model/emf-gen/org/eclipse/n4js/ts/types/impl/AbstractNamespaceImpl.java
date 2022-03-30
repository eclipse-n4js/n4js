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

import com.google.common.collect.Iterables;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;

import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TNamespace;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.xtext.EcoreUtil2;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Namespace</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.AbstractNamespaceImpl#getTypes <em>Types</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.AbstractNamespaceImpl#getExportedVariables <em>Exported Variables</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.AbstractNamespaceImpl#getLocalVariables <em>Local Variables</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.AbstractNamespaceImpl#getExposedLocalVariables <em>Exposed Local Variables</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.AbstractNamespaceImpl#getNamespaces <em>Namespaces</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbstractNamespaceImpl extends TExportingElementImpl implements AbstractNamespace {
	/**
	 * The cached value of the '{@link #getTypes() <em>Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<Type> types;

	/**
	 * The cached value of the '{@link #getExportedVariables() <em>Exported Variables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportedVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<TVariable> exportedVariables;

	/**
	 * The cached value of the '{@link #getLocalVariables() <em>Local Variables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<TVariable> localVariables;

	/**
	 * The cached value of the '{@link #getExposedLocalVariables() <em>Exposed Local Variables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExposedLocalVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<TVariable> exposedLocalVariables;

	/**
	 * The cached value of the '{@link #getNamespaces() <em>Namespaces</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespaces()
	 * @generated
	 * @ordered
	 */
	protected EList<TNamespace> namespaces;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractNamespaceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.ABSTRACT_NAMESPACE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Type> getTypes() {
		if (types == null) {
			types = new EObjectContainmentEList<Type>(Type.class, this, TypesPackage.ABSTRACT_NAMESPACE__TYPES);
		}
		return types;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TVariable> getExportedVariables() {
		if (exportedVariables == null) {
			exportedVariables = new EObjectContainmentEList<TVariable>(TVariable.class, this, TypesPackage.ABSTRACT_NAMESPACE__EXPORTED_VARIABLES);
		}
		return exportedVariables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TVariable> getLocalVariables() {
		if (localVariables == null) {
			localVariables = new EObjectContainmentEList<TVariable>(TVariable.class, this, TypesPackage.ABSTRACT_NAMESPACE__LOCAL_VARIABLES);
		}
		return localVariables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TVariable> getExposedLocalVariables() {
		if (exposedLocalVariables == null) {
			exposedLocalVariables = new EObjectContainmentEList<TVariable>(TVariable.class, this, TypesPackage.ABSTRACT_NAMESPACE__EXPOSED_LOCAL_VARIABLES);
		}
		return exposedLocalVariables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TNamespace> getNamespaces() {
		if (namespaces == null) {
			namespaces = new EObjectContainmentEList<TNamespace>(TNamespace.class, this, TypesPackage.ABSTRACT_NAMESPACE__NAMESPACES);
		}
		return namespaces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterable<? extends TExportableElement> getExportableElements() {
		return Iterables.<TExportableElement>concat(this.getTypes(), this.getExportedVariables(), this.getLocalVariables(), this.getExposedLocalVariables(), this.getNamespaces());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterable<? extends AbstractNamespace> getAllNamespaces() {
		boolean _isEmpty = this.getNamespaces().isEmpty();
		if (_isEmpty) {
			return java.util.Collections.<AbstractNamespace>unmodifiableList(org.eclipse.xtext.xbase.lib.CollectionLiterals.<AbstractNamespace>newArrayList(this));
		}
		final Function1<TNamespace, Iterable<? extends AbstractNamespace>> _function = new Function1<TNamespace, Iterable<? extends AbstractNamespace>>() {
			public Iterable<? extends AbstractNamespace> apply(final TNamespace it) {
				return it.getAllNamespaces();
			}
		};
		Iterable<AbstractNamespace> _flatten = Iterables.<AbstractNamespace>concat(XcoreEListExtensions.<TNamespace, Iterable<? extends AbstractNamespace>>map(this.getNamespaces(), _function));
		return Iterables.<AbstractNamespace>concat(java.util.Collections.<AbstractNamespace>unmodifiableList(org.eclipse.xtext.xbase.lib.CollectionLiterals.<AbstractNamespace>newArrayList(this)), _flatten);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TModule getContainingModule() {
		return EcoreUtil2.<TModule>getContainerOfType(this, TModule.class);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void clearTransientElements() {
		this.getLocalVariables().clear();
		EList<TNamespace> _namespaces = this.getNamespaces();
		for (final TNamespace child : _namespaces) {
			child.clearTransientElements();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.ABSTRACT_NAMESPACE__TYPES:
				return ((InternalEList<?>)getTypes()).basicRemove(otherEnd, msgs);
			case TypesPackage.ABSTRACT_NAMESPACE__EXPORTED_VARIABLES:
				return ((InternalEList<?>)getExportedVariables()).basicRemove(otherEnd, msgs);
			case TypesPackage.ABSTRACT_NAMESPACE__LOCAL_VARIABLES:
				return ((InternalEList<?>)getLocalVariables()).basicRemove(otherEnd, msgs);
			case TypesPackage.ABSTRACT_NAMESPACE__EXPOSED_LOCAL_VARIABLES:
				return ((InternalEList<?>)getExposedLocalVariables()).basicRemove(otherEnd, msgs);
			case TypesPackage.ABSTRACT_NAMESPACE__NAMESPACES:
				return ((InternalEList<?>)getNamespaces()).basicRemove(otherEnd, msgs);
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
			case TypesPackage.ABSTRACT_NAMESPACE__TYPES:
				return getTypes();
			case TypesPackage.ABSTRACT_NAMESPACE__EXPORTED_VARIABLES:
				return getExportedVariables();
			case TypesPackage.ABSTRACT_NAMESPACE__LOCAL_VARIABLES:
				return getLocalVariables();
			case TypesPackage.ABSTRACT_NAMESPACE__EXPOSED_LOCAL_VARIABLES:
				return getExposedLocalVariables();
			case TypesPackage.ABSTRACT_NAMESPACE__NAMESPACES:
				return getNamespaces();
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
			case TypesPackage.ABSTRACT_NAMESPACE__TYPES:
				getTypes().clear();
				getTypes().addAll((Collection<? extends Type>)newValue);
				return;
			case TypesPackage.ABSTRACT_NAMESPACE__EXPORTED_VARIABLES:
				getExportedVariables().clear();
				getExportedVariables().addAll((Collection<? extends TVariable>)newValue);
				return;
			case TypesPackage.ABSTRACT_NAMESPACE__LOCAL_VARIABLES:
				getLocalVariables().clear();
				getLocalVariables().addAll((Collection<? extends TVariable>)newValue);
				return;
			case TypesPackage.ABSTRACT_NAMESPACE__EXPOSED_LOCAL_VARIABLES:
				getExposedLocalVariables().clear();
				getExposedLocalVariables().addAll((Collection<? extends TVariable>)newValue);
				return;
			case TypesPackage.ABSTRACT_NAMESPACE__NAMESPACES:
				getNamespaces().clear();
				getNamespaces().addAll((Collection<? extends TNamespace>)newValue);
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
			case TypesPackage.ABSTRACT_NAMESPACE__TYPES:
				getTypes().clear();
				return;
			case TypesPackage.ABSTRACT_NAMESPACE__EXPORTED_VARIABLES:
				getExportedVariables().clear();
				return;
			case TypesPackage.ABSTRACT_NAMESPACE__LOCAL_VARIABLES:
				getLocalVariables().clear();
				return;
			case TypesPackage.ABSTRACT_NAMESPACE__EXPOSED_LOCAL_VARIABLES:
				getExposedLocalVariables().clear();
				return;
			case TypesPackage.ABSTRACT_NAMESPACE__NAMESPACES:
				getNamespaces().clear();
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
			case TypesPackage.ABSTRACT_NAMESPACE__TYPES:
				return types != null && !types.isEmpty();
			case TypesPackage.ABSTRACT_NAMESPACE__EXPORTED_VARIABLES:
				return exportedVariables != null && !exportedVariables.isEmpty();
			case TypesPackage.ABSTRACT_NAMESPACE__LOCAL_VARIABLES:
				return localVariables != null && !localVariables.isEmpty();
			case TypesPackage.ABSTRACT_NAMESPACE__EXPOSED_LOCAL_VARIABLES:
				return exposedLocalVariables != null && !exposedLocalVariables.isEmpty();
			case TypesPackage.ABSTRACT_NAMESPACE__NAMESPACES:
				return namespaces != null && !namespaces.isEmpty();
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
			case TypesPackage.ABSTRACT_NAMESPACE___GET_EXPORTABLE_ELEMENTS:
				return getExportableElements();
			case TypesPackage.ABSTRACT_NAMESPACE___GET_ALL_NAMESPACES:
				return getAllNamespaces();
			case TypesPackage.ABSTRACT_NAMESPACE___GET_CONTAINING_MODULE:
				return getContainingModule();
			case TypesPackage.ABSTRACT_NAMESPACE___CLEAR_TRANSIENT_ELEMENTS:
				clearTransientElements();
				return null;
		}
		return super.eInvoke(operationID, arguments);
	}

} //AbstractNamespaceImpl
