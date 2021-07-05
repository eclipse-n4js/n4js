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

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.function.Predicate;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;
import org.eclipse.n4js.transpiler.im.ImFactory;
import org.eclipse.n4js.transpiler.im.ImPackage;
import org.eclipse.n4js.transpiler.im.ManyReferencingElement_IM;
import org.eclipse.n4js.transpiler.im.PlainReference;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Many Referencing Element IM</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.ManyReferencingElement_IMImpl#getRewiredReferences <em>Rewired References</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ManyReferencingElement_IMImpl extends MinimalEObjectImpl.Container implements ManyReferencingElement_IM {
	/**
	 * The cached value of the '{@link #getRewiredReferences() <em>Rewired References</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRewiredReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<PlainReference> rewiredReferences;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ManyReferencingElement_IMImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImPackage.Literals.MANY_REFERENCING_ELEMENT_IM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PlainReference> getRewiredReferences() {
		if (rewiredReferences == null) {
			rewiredReferences = new EObjectContainmentEList<PlainReference>(PlainReference.class, this, ImPackage.MANY_REFERENCING_ELEMENT_IM__REWIRED_REFERENCES);
		}
		return rewiredReferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SymbolTableEntry> getRewiredTargets() {
		final Function1<PlainReference, SymbolTableEntry> _function = new Function1<PlainReference, SymbolTableEntry>() {
			public SymbolTableEntry apply(final PlainReference it) {
				return it.getRewiredTarget();
			}
		};
		return ECollections.<SymbolTableEntry>newBasicEList(XcoreEListExtensions.<PlainReference, SymbolTableEntry>map(this.getRewiredReferences(), _function));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void addRewiredTarget(final SymbolTableEntry ste) {
		EList<PlainReference> _rewiredReferences = this.getRewiredReferences();
		for (final PlainReference ref : _rewiredReferences) {
			SymbolTableEntry _rewiredTarget = ref.getRewiredTarget();
			boolean _tripleEquals = (_rewiredTarget == ste);
			if (_tripleEquals) {
				return;
			}
		}
		EList<PlainReference> _rewiredReferences_1 = this.getRewiredReferences();
		PlainReference _createPlainReference = ImFactory.eINSTANCE.createPlainReference();
		final Procedure1<PlainReference> _function = new Procedure1<PlainReference>() {
			public void apply(final PlainReference it) {
				it.setRewiredTarget(ste);
			}
		};
		PlainReference _doubleArrow = ObjectExtensions.<PlainReference>operator_doubleArrow(_createPlainReference, _function);
		_rewiredReferences_1.add(_doubleArrow);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void removeRewiredTarget(final SymbolTableEntry ste) {
		final Predicate<PlainReference> _function = new Predicate<PlainReference>() {
			public boolean test(final PlainReference it) {
				SymbolTableEntry _rewiredTarget = it.getRewiredTarget();
				return (_rewiredTarget == ste);
			}
		};
		this.getRewiredReferences().removeIf(_function);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImPackage.MANY_REFERENCING_ELEMENT_IM__REWIRED_REFERENCES:
				return ((InternalEList<?>)getRewiredReferences()).basicRemove(otherEnd, msgs);
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
			case ImPackage.MANY_REFERENCING_ELEMENT_IM__REWIRED_REFERENCES:
				return getRewiredReferences();
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
			case ImPackage.MANY_REFERENCING_ELEMENT_IM__REWIRED_REFERENCES:
				getRewiredReferences().clear();
				getRewiredReferences().addAll((Collection<? extends PlainReference>)newValue);
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
			case ImPackage.MANY_REFERENCING_ELEMENT_IM__REWIRED_REFERENCES:
				getRewiredReferences().clear();
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
			case ImPackage.MANY_REFERENCING_ELEMENT_IM__REWIRED_REFERENCES:
				return rewiredReferences != null && !rewiredReferences.isEmpty();
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
			case ImPackage.MANY_REFERENCING_ELEMENT_IM___GET_REWIRED_TARGETS:
				return getRewiredTargets();
			case ImPackage.MANY_REFERENCING_ELEMENT_IM___ADD_REWIRED_TARGET__SYMBOLTABLEENTRY:
				addRewiredTarget((SymbolTableEntry)arguments.get(0));
				return null;
			case ImPackage.MANY_REFERENCING_ELEMENT_IM___REMOVE_REWIRED_TARGET__SYMBOLTABLEENTRY:
				removeRewiredTarget((SymbolTableEntry)arguments.get(0));
				return null;
		}
		return super.eInvoke(operationID, arguments);
	}

} //ManyReferencingElement_IMImpl
