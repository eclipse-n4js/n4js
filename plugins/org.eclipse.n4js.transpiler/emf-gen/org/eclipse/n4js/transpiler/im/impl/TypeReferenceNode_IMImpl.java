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
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.n4JS.impl.TypeReferenceNodeImpl;

import org.eclipse.n4js.transpiler.im.ImFactory;
import org.eclipse.n4js.transpiler.im.ImPackage;
import org.eclipse.n4js.transpiler.im.ManyReferencingElement_IM;
import org.eclipse.n4js.transpiler.im.PlainReference;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.TypeReferenceNode_IM;

import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Reference Node IM</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.TypeReferenceNode_IMImpl#getRewiredReferences <em>Rewired References</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.TypeReferenceNode_IMImpl#getCodeToEmit <em>Code To Emit</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeReferenceNode_IMImpl<T extends TypeRef> extends TypeReferenceNodeImpl<T> implements TypeReferenceNode_IM<T> {
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
	 * The default value of the '{@link #getCodeToEmit() <em>Code To Emit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCodeToEmit()
	 * @generated
	 * @ordered
	 */
	protected static final String CODE_TO_EMIT_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getCodeToEmit() <em>Code To Emit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCodeToEmit()
	 * @generated
	 * @ordered
	 */
	protected String codeToEmit = CODE_TO_EMIT_EDEFAULT;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeReferenceNode_IMImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImPackage.Literals.TYPE_REFERENCE_NODE_IM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PlainReference> getRewiredReferences() {
		if (rewiredReferences == null) {
			rewiredReferences = new EObjectContainmentEList<PlainReference>(PlainReference.class, this, ImPackage.TYPE_REFERENCE_NODE_IM__REWIRED_REFERENCES);
		}
		return rewiredReferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCodeToEmit() {
		return codeToEmit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCodeToEmit(String newCodeToEmit) {
		String oldCodeToEmit = codeToEmit;
		codeToEmit = newCodeToEmit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.TYPE_REFERENCE_NODE_IM__CODE_TO_EMIT, oldCodeToEmit, codeToEmit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getTypeRef() {
		throw new IllegalStateException("attempt to access resolution of a typeRefInAST from transpiler; use InformationRegistry#getOriginalProcessedTypeRef() instead");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getCachedProcessedTypeRef() {
		throw new IllegalStateException("attempt to access resolution of a typeRefInAST from transpiler; use InformationRegistry#getOriginalProcessedTypeRef() instead");
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
			case ImPackage.TYPE_REFERENCE_NODE_IM__REWIRED_REFERENCES:
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
			case ImPackage.TYPE_REFERENCE_NODE_IM__REWIRED_REFERENCES:
				return getRewiredReferences();
			case ImPackage.TYPE_REFERENCE_NODE_IM__CODE_TO_EMIT:
				return getCodeToEmit();
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
			case ImPackage.TYPE_REFERENCE_NODE_IM__REWIRED_REFERENCES:
				getRewiredReferences().clear();
				getRewiredReferences().addAll((Collection<? extends PlainReference>)newValue);
				return;
			case ImPackage.TYPE_REFERENCE_NODE_IM__CODE_TO_EMIT:
				setCodeToEmit((String)newValue);
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
			case ImPackage.TYPE_REFERENCE_NODE_IM__REWIRED_REFERENCES:
				getRewiredReferences().clear();
				return;
			case ImPackage.TYPE_REFERENCE_NODE_IM__CODE_TO_EMIT:
				setCodeToEmit(CODE_TO_EMIT_EDEFAULT);
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
			case ImPackage.TYPE_REFERENCE_NODE_IM__REWIRED_REFERENCES:
				return rewiredReferences != null && !rewiredReferences.isEmpty();
			case ImPackage.TYPE_REFERENCE_NODE_IM__CODE_TO_EMIT:
				return CODE_TO_EMIT_EDEFAULT == null ? codeToEmit != null : !CODE_TO_EMIT_EDEFAULT.equals(codeToEmit);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == ManyReferencingElement_IM.class) {
			switch (derivedFeatureID) {
				case ImPackage.TYPE_REFERENCE_NODE_IM__REWIRED_REFERENCES: return ImPackage.MANY_REFERENCING_ELEMENT_IM__REWIRED_REFERENCES;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == ManyReferencingElement_IM.class) {
			switch (baseFeatureID) {
				case ImPackage.MANY_REFERENCING_ELEMENT_IM__REWIRED_REFERENCES: return ImPackage.TYPE_REFERENCE_NODE_IM__REWIRED_REFERENCES;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == TypeReferenceNode.class) {
			switch (baseOperationID) {
				case N4JSPackage.TYPE_REFERENCE_NODE___GET_TYPE_REF: return ImPackage.TYPE_REFERENCE_NODE_IM___GET_TYPE_REF;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == ManyReferencingElement_IM.class) {
			switch (baseOperationID) {
				case ImPackage.MANY_REFERENCING_ELEMENT_IM___GET_REWIRED_TARGETS: return ImPackage.TYPE_REFERENCE_NODE_IM___GET_REWIRED_TARGETS;
				case ImPackage.MANY_REFERENCING_ELEMENT_IM___ADD_REWIRED_TARGET__SYMBOLTABLEENTRY: return ImPackage.TYPE_REFERENCE_NODE_IM___ADD_REWIRED_TARGET__SYMBOLTABLEENTRY;
				case ImPackage.MANY_REFERENCING_ELEMENT_IM___REMOVE_REWIRED_TARGET__SYMBOLTABLEENTRY: return ImPackage.TYPE_REFERENCE_NODE_IM___REMOVE_REWIRED_TARGET__SYMBOLTABLEENTRY;
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
			case ImPackage.TYPE_REFERENCE_NODE_IM___GET_TYPE_REF:
				return getTypeRef();
			case ImPackage.TYPE_REFERENCE_NODE_IM___GET_CACHED_PROCESSED_TYPE_REF:
				return getCachedProcessedTypeRef();
			case ImPackage.TYPE_REFERENCE_NODE_IM___GET_REWIRED_TARGETS:
				return getRewiredTargets();
			case ImPackage.TYPE_REFERENCE_NODE_IM___ADD_REWIRED_TARGET__SYMBOLTABLEENTRY:
				addRewiredTarget((SymbolTableEntry)arguments.get(0));
				return null;
			case ImPackage.TYPE_REFERENCE_NODE_IM___REMOVE_REWIRED_TARGET__SYMBOLTABLEENTRY:
				removeRewiredTarget((SymbolTableEntry)arguments.get(0));
				return null;
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
		result.append(" (codeToEmit: ");
		result.append(codeToEmit);
		result.append(')');
		return result.toString();
	}

} //TypeReferenceNode_IMImpl
