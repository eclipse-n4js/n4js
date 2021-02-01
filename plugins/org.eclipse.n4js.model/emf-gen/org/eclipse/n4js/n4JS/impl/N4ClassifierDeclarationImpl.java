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
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.ThisTarget;
import org.eclipse.n4js.n4JS.TypeReferenceInAST;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;

import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypingStrategy;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>N4 Classifier Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4ClassifierDeclarationImpl#getOwnedMembersRaw <em>Owned Members Raw</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4ClassifierDeclarationImpl#getTypeVars <em>Type Vars</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4ClassifierDeclarationImpl#getTypingStrategy <em>Typing Strategy</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class N4ClassifierDeclarationImpl extends N4TypeDeclarationImpl implements N4ClassifierDeclaration {
	/**
	 * The cached value of the '{@link #getOwnedMembersRaw() <em>Owned Members Raw</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedMembersRaw()
	 * @generated
	 * @ordered
	 */
	protected EList<N4MemberDeclaration> ownedMembersRaw;

	/**
	 * The cached value of the '{@link #getTypeVars() <em>Type Vars</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeVars()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeVariable> typeVars;

	/**
	 * The default value of the '{@link #getTypingStrategy() <em>Typing Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypingStrategy()
	 * @generated
	 * @ordered
	 */
	protected static final TypingStrategy TYPING_STRATEGY_EDEFAULT = TypingStrategy.DEFAULT;

	/**
	 * The cached value of the '{@link #getTypingStrategy() <em>Typing Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypingStrategy()
	 * @generated
	 * @ordered
	 */
	protected TypingStrategy typingStrategy = TYPING_STRATEGY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected N4ClassifierDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.N4_CLASSIFIER_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<N4MemberDeclaration> getOwnedMembersRaw() {
		if (ownedMembersRaw == null) {
			ownedMembersRaw = new EObjectContainmentWithInverseEList<N4MemberDeclaration>(N4MemberDeclaration.class, this, N4JSPackage.N4_CLASSIFIER_DECLARATION__OWNED_MEMBERS_RAW, N4JSPackage.N4_MEMBER_DECLARATION__OWNER);
		}
		return ownedMembersRaw;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypeVariable> getTypeVars() {
		if (typeVars == null) {
			typeVars = new EObjectContainmentEList<TypeVariable>(TypeVariable.class, this, N4JSPackage.N4_CLASSIFIER_DECLARATION__TYPE_VARS);
		}
		return typeVars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypingStrategy getTypingStrategy() {
		return typingStrategy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTypingStrategy(TypingStrategy newTypingStrategy) {
		TypingStrategy oldTypingStrategy = typingStrategy;
		typingStrategy = newTypingStrategy == null ? TYPING_STRATEGY_EDEFAULT : newTypingStrategy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_CLASSIFIER_DECLARATION__TYPING_STRATEGY, oldTypingStrategy, typingStrategy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<N4MemberDeclaration> getOwnedMembers() {
		final Function1<N4MemberDeclaration, Boolean> _function = new Function1<N4MemberDeclaration, Boolean>() {
			public Boolean apply(final N4MemberDeclaration it) {
				boolean _isCallableConstructor = it.isCallableConstructor();
				return Boolean.valueOf((!_isCallableConstructor));
			}
		};
		final Iterable<N4MemberDeclaration> methods = IterableExtensions.<N4MemberDeclaration>filter(Iterables.<N4MemberDeclaration>filter(this.getOwnedMembersRaw(), N4MemberDeclaration.class), _function);
		List<N4MemberDeclaration> _list = IterableExtensions.<N4MemberDeclaration>toList(methods);
		return new BasicEList<N4MemberDeclaration>(_list);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public N4MethodDeclaration getOwnedCtor() {
		final Function1<N4MethodDeclaration, Boolean> _function = new Function1<N4MethodDeclaration, Boolean>() {
			public Boolean apply(final N4MethodDeclaration it) {
				return Boolean.valueOf(it.isConstructor());
			}
		};
		return IterableExtensions.<N4MethodDeclaration>findFirst(Iterables.<N4MethodDeclaration>filter(this.getOwnedMembersRaw(), N4MethodDeclaration.class), _function);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public N4MethodDeclaration getOwnedCallableCtor() {
		final Function1<N4MethodDeclaration, Boolean> _function = new Function1<N4MethodDeclaration, Boolean>() {
			public Boolean apply(final N4MethodDeclaration it) {
				return Boolean.valueOf(it.isCallableConstructor());
			}
		};
		return IterableExtensions.<N4MethodDeclaration>findFirst(Iterables.<N4MethodDeclaration>filter(this.getOwnedMembersRaw(), N4MethodDeclaration.class), _function);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<N4MethodDeclaration> getOwnedMethods() {
		final Function1<N4MethodDeclaration, Boolean> _function = new Function1<N4MethodDeclaration, Boolean>() {
			public Boolean apply(final N4MethodDeclaration it) {
				return Boolean.valueOf(((!it.isConstructor()) && (!it.isCallableConstructor())));
			}
		};
		final Iterable<N4MethodDeclaration> methods = IterableExtensions.<N4MethodDeclaration>filter(Iterables.<N4MethodDeclaration>filter(this.getOwnedMembersRaw(), N4MethodDeclaration.class), _function);
		List<N4MethodDeclaration> _list = IterableExtensions.<N4MethodDeclaration>toList(methods);
		return new BasicEList<N4MethodDeclaration>(_list);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<N4FieldDeclaration> getOwnedFields() {
		final Iterable<N4FieldDeclaration> fields = Iterables.<N4FieldDeclaration>filter(this.getOwnedMembersRaw(), N4FieldDeclaration.class);
		List<N4FieldDeclaration> _list = IterableExtensions.<N4FieldDeclaration>toList(fields);
		return new BasicEList<N4FieldDeclaration>(_list);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<N4GetterDeclaration> getOwnedGetters() {
		final Iterable<N4GetterDeclaration> getters = Iterables.<N4GetterDeclaration>filter(this.getOwnedMembersRaw(), N4GetterDeclaration.class);
		List<N4GetterDeclaration> _list = IterableExtensions.<N4GetterDeclaration>toList(getters);
		return new BasicEList<N4GetterDeclaration>(_list);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<N4SetterDeclaration> getOwnedSetters() {
		final Iterable<N4SetterDeclaration> setters = Iterables.<N4SetterDeclaration>filter(this.getOwnedMembersRaw(), N4SetterDeclaration.class);
		List<N4SetterDeclaration> _list = IterableExtensions.<N4SetterDeclaration>toList(setters);
		return new BasicEList<N4SetterDeclaration>(_list);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterable<TypeReferenceInAST<ParameterizedTypeRef>> getSuperClassifierRefs() {
		return Collections.<TypeReferenceInAST<ParameterizedTypeRef>>emptyList();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterable<TypeReferenceInAST<ParameterizedTypeRef>> getImplementedOrExtendedInterfaceRefs() {
		return Collections.<TypeReferenceInAST<ParameterizedTypeRef>>emptyList();
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
			case N4JSPackage.N4_CLASSIFIER_DECLARATION__OWNED_MEMBERS_RAW:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOwnedMembersRaw()).basicAdd(otherEnd, msgs);
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
			case N4JSPackage.N4_CLASSIFIER_DECLARATION__OWNED_MEMBERS_RAW:
				return ((InternalEList<?>)getOwnedMembersRaw()).basicRemove(otherEnd, msgs);
			case N4JSPackage.N4_CLASSIFIER_DECLARATION__TYPE_VARS:
				return ((InternalEList<?>)getTypeVars()).basicRemove(otherEnd, msgs);
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
			case N4JSPackage.N4_CLASSIFIER_DECLARATION__OWNED_MEMBERS_RAW:
				return getOwnedMembersRaw();
			case N4JSPackage.N4_CLASSIFIER_DECLARATION__TYPE_VARS:
				return getTypeVars();
			case N4JSPackage.N4_CLASSIFIER_DECLARATION__TYPING_STRATEGY:
				return getTypingStrategy();
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
			case N4JSPackage.N4_CLASSIFIER_DECLARATION__OWNED_MEMBERS_RAW:
				getOwnedMembersRaw().clear();
				getOwnedMembersRaw().addAll((Collection<? extends N4MemberDeclaration>)newValue);
				return;
			case N4JSPackage.N4_CLASSIFIER_DECLARATION__TYPE_VARS:
				getTypeVars().clear();
				getTypeVars().addAll((Collection<? extends TypeVariable>)newValue);
				return;
			case N4JSPackage.N4_CLASSIFIER_DECLARATION__TYPING_STRATEGY:
				setTypingStrategy((TypingStrategy)newValue);
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
			case N4JSPackage.N4_CLASSIFIER_DECLARATION__OWNED_MEMBERS_RAW:
				getOwnedMembersRaw().clear();
				return;
			case N4JSPackage.N4_CLASSIFIER_DECLARATION__TYPE_VARS:
				getTypeVars().clear();
				return;
			case N4JSPackage.N4_CLASSIFIER_DECLARATION__TYPING_STRATEGY:
				setTypingStrategy(TYPING_STRATEGY_EDEFAULT);
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
			case N4JSPackage.N4_CLASSIFIER_DECLARATION__OWNED_MEMBERS_RAW:
				return ownedMembersRaw != null && !ownedMembersRaw.isEmpty();
			case N4JSPackage.N4_CLASSIFIER_DECLARATION__TYPE_VARS:
				return typeVars != null && !typeVars.isEmpty();
			case N4JSPackage.N4_CLASSIFIER_DECLARATION__TYPING_STRATEGY:
				return typingStrategy != TYPING_STRATEGY_EDEFAULT;
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
		if (baseClass == N4ClassifierDefinition.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.N4_CLASSIFIER_DECLARATION__OWNED_MEMBERS_RAW: return N4JSPackage.N4_CLASSIFIER_DEFINITION__OWNED_MEMBERS_RAW;
				default: return -1;
			}
		}
		if (baseClass == GenericDeclaration.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.N4_CLASSIFIER_DECLARATION__TYPE_VARS: return N4JSPackage.GENERIC_DECLARATION__TYPE_VARS;
				default: return -1;
			}
		}
		if (baseClass == ThisTarget.class) {
			switch (derivedFeatureID) {
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
		if (baseClass == N4ClassifierDefinition.class) {
			switch (baseFeatureID) {
				case N4JSPackage.N4_CLASSIFIER_DEFINITION__OWNED_MEMBERS_RAW: return N4JSPackage.N4_CLASSIFIER_DECLARATION__OWNED_MEMBERS_RAW;
				default: return -1;
			}
		}
		if (baseClass == GenericDeclaration.class) {
			switch (baseFeatureID) {
				case N4JSPackage.GENERIC_DECLARATION__TYPE_VARS: return N4JSPackage.N4_CLASSIFIER_DECLARATION__TYPE_VARS;
				default: return -1;
			}
		}
		if (baseClass == ThisTarget.class) {
			switch (baseFeatureID) {
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
		if (baseClass == N4ClassifierDefinition.class) {
			switch (baseOperationID) {
				case N4JSPackage.N4_CLASSIFIER_DEFINITION___GET_OWNED_MEMBERS: return N4JSPackage.N4_CLASSIFIER_DECLARATION___GET_OWNED_MEMBERS;
				case N4JSPackage.N4_CLASSIFIER_DEFINITION___GET_OWNED_CTOR: return N4JSPackage.N4_CLASSIFIER_DECLARATION___GET_OWNED_CTOR;
				case N4JSPackage.N4_CLASSIFIER_DEFINITION___GET_OWNED_CALLABLE_CTOR: return N4JSPackage.N4_CLASSIFIER_DECLARATION___GET_OWNED_CALLABLE_CTOR;
				case N4JSPackage.N4_CLASSIFIER_DEFINITION___GET_OWNED_METHODS: return N4JSPackage.N4_CLASSIFIER_DECLARATION___GET_OWNED_METHODS;
				case N4JSPackage.N4_CLASSIFIER_DEFINITION___GET_OWNED_FIELDS: return N4JSPackage.N4_CLASSIFIER_DECLARATION___GET_OWNED_FIELDS;
				case N4JSPackage.N4_CLASSIFIER_DEFINITION___GET_OWNED_GETTERS: return N4JSPackage.N4_CLASSIFIER_DECLARATION___GET_OWNED_GETTERS;
				case N4JSPackage.N4_CLASSIFIER_DEFINITION___GET_OWNED_SETTERS: return N4JSPackage.N4_CLASSIFIER_DECLARATION___GET_OWNED_SETTERS;
				case N4JSPackage.N4_CLASSIFIER_DEFINITION___GET_SUPER_CLASSIFIER_REFS: return N4JSPackage.N4_CLASSIFIER_DECLARATION___GET_SUPER_CLASSIFIER_REFS;
				case N4JSPackage.N4_CLASSIFIER_DEFINITION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS: return N4JSPackage.N4_CLASSIFIER_DECLARATION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS;
				default: return -1;
			}
		}
		if (baseClass == GenericDeclaration.class) {
			switch (baseOperationID) {
				default: return -1;
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
			case N4JSPackage.N4_CLASSIFIER_DECLARATION___GET_OWNED_MEMBERS:
				return getOwnedMembers();
			case N4JSPackage.N4_CLASSIFIER_DECLARATION___GET_OWNED_CTOR:
				return getOwnedCtor();
			case N4JSPackage.N4_CLASSIFIER_DECLARATION___GET_OWNED_CALLABLE_CTOR:
				return getOwnedCallableCtor();
			case N4JSPackage.N4_CLASSIFIER_DECLARATION___GET_OWNED_METHODS:
				return getOwnedMethods();
			case N4JSPackage.N4_CLASSIFIER_DECLARATION___GET_OWNED_FIELDS:
				return getOwnedFields();
			case N4JSPackage.N4_CLASSIFIER_DECLARATION___GET_OWNED_GETTERS:
				return getOwnedGetters();
			case N4JSPackage.N4_CLASSIFIER_DECLARATION___GET_OWNED_SETTERS:
				return getOwnedSetters();
			case N4JSPackage.N4_CLASSIFIER_DECLARATION___GET_SUPER_CLASSIFIER_REFS:
				return getSuperClassifierRefs();
			case N4JSPackage.N4_CLASSIFIER_DECLARATION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS:
				return getImplementedOrExtendedInterfaceRefs();
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
		result.append(" (typingStrategy: ");
		result.append(typingStrategy);
		result.append(')');
		return result.toString();
	}

} //N4ClassifierDeclarationImpl
