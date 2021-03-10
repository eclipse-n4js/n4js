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

import com.google.common.base.Objects;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Collection;
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
import org.eclipse.n4js.transpiler.im.ImPackage;
import org.eclipse.n4js.transpiler.im.VersionedParameterizedTypeRefStructural_IM;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.TypeVariableMapping;
import org.eclipse.n4js.ts.typeRefs.Versionable;
import org.eclipse.n4js.ts.typeRefs.VersionedParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.VersionedParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.VersionedReference;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Versioned Parameterized Type Ref Structural IM</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.VersionedParameterizedTypeRefStructural_IMImpl#getRequestedVersion <em>Requested Version</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.VersionedParameterizedTypeRefStructural_IMImpl#getAstStructuralMembers <em>Ast Structural Members</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.VersionedParameterizedTypeRefStructural_IMImpl#getStructuralType <em>Structural Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.VersionedParameterizedTypeRefStructural_IMImpl#getGenStructuralMembers <em>Gen Structural Members</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.VersionedParameterizedTypeRefStructural_IMImpl#getPostponedSubstitutions <em>Postponed Substitutions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VersionedParameterizedTypeRefStructural_IMImpl extends ParameterizedTypeRef_IMImpl implements VersionedParameterizedTypeRefStructural_IM {
	/**
	 * The default value of the '{@link #getRequestedVersion() <em>Requested Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequestedVersion()
	 * @generated
	 * @ordered
	 */
	protected static final BigDecimal REQUESTED_VERSION_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getRequestedVersion() <em>Requested Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequestedVersion()
	 * @generated
	 * @ordered
	 */
	protected BigDecimal requestedVersion = REQUESTED_VERSION_EDEFAULT;
	/**
	 * The cached value of the '{@link #getAstStructuralMembers() <em>Ast Structural Members</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAstStructuralMembers()
	 * @generated
	 * @ordered
	 */
	protected EList<TStructMember> astStructuralMembers;
	/**
	 * The cached value of the '{@link #getStructuralType() <em>Structural Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStructuralType()
	 * @generated
	 * @ordered
	 */
	protected TStructuralType structuralType;
	/**
	 * The cached value of the '{@link #getGenStructuralMembers() <em>Gen Structural Members</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenStructuralMembers()
	 * @generated
	 * @ordered
	 */
	protected EList<TStructMember> genStructuralMembers;
	/**
	 * The cached value of the '{@link #getPostponedSubstitutions() <em>Postponed Substitutions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPostponedSubstitutions()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeVariableMapping> postponedSubstitutions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VersionedParameterizedTypeRefStructural_IMImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImPackage.Literals.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BigDecimal getRequestedVersion() {
		return requestedVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRequestedVersion(BigDecimal newRequestedVersion) {
		BigDecimal oldRequestedVersion = requestedVersion;
		requestedVersion = newRequestedVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__REQUESTED_VERSION, oldRequestedVersion, requestedVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TStructMember> getAstStructuralMembers() {
		if (astStructuralMembers == null) {
			astStructuralMembers = new EObjectContainmentEList<TStructMember>(TStructMember.class, this, ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_STRUCTURAL_MEMBERS);
		}
		return astStructuralMembers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TStructuralType getStructuralType() {
		if (structuralType != null && structuralType.eIsProxy()) {
			InternalEObject oldStructuralType = (InternalEObject)structuralType;
			structuralType = (TStructuralType)eResolveProxy(oldStructuralType);
			if (structuralType != oldStructuralType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__STRUCTURAL_TYPE, oldStructuralType, structuralType));
			}
		}
		return structuralType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TStructuralType basicGetStructuralType() {
		return structuralType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStructuralType(TStructuralType newStructuralType) {
		TStructuralType oldStructuralType = structuralType;
		structuralType = newStructuralType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__STRUCTURAL_TYPE, oldStructuralType, structuralType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TStructMember> getGenStructuralMembers() {
		if (genStructuralMembers == null) {
			genStructuralMembers = new EObjectContainmentEList<TStructMember>(TStructMember.class, this, ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__GEN_STRUCTURAL_MEMBERS);
		}
		return genStructuralMembers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypeVariableMapping> getPostponedSubstitutions() {
		if (postponedSubstitutions == null) {
			postponedSubstitutions = new EObjectContainmentEList<TypeVariableMapping>(TypeVariableMapping.class, this, ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__POSTPONED_SUBSTITUTIONS);
		}
		return postponedSubstitutions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypingStrategy getTypingStrategy() {
		TypingStrategy _definedTypingStrategy = this.getDefinedTypingStrategy();
		boolean _tripleEquals = (_definedTypingStrategy == TypingStrategy.DEFAULT);
		if (_tripleEquals) {
			return TypingStrategy.STRUCTURAL;
		}
		else {
			return this.getDefinedTypingStrategy();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTypingStrategy(final TypingStrategy typingStrategy) {
		boolean _equals = Objects.equal(typingStrategy, TypingStrategy.NOMINAL);
		if (_equals) {
			throw new IllegalArgumentException("cannot set structural type reference to nominal");
		}
		this.setDefinedTypingStrategy(typingStrategy);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TStructMember> getStructuralMembers() {
		EList<TStructMember> _xifexpression = null;
		TStructuralType _structuralType = this.getStructuralType();
		boolean _tripleNotEquals = (_structuralType != null);
		if (_tripleNotEquals) {
			_xifexpression = this.getStructuralType().getOwnedMembers();
		}
		else {
			EList<TStructMember> _xifexpression_1 = null;
			boolean _isEmpty = this.getAstStructuralMembers().isEmpty();
			boolean _not = (!_isEmpty);
			if (_not) {
				_xifexpression_1 = this.getAstStructuralMembers();
			}
			else {
				_xifexpression_1 = this.getGenStructuralMembers();
			}
			_xifexpression = _xifexpression_1;
		}
		return ECollections.<TStructMember>unmodifiableEList(_xifexpression);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String internalGetTypeRefAsString() {
		TypingStrategy _typingStrategy = this.getTypingStrategy();
		Type _declaredType = this.getDeclaredType();
		String _rawTypeAsString = null;
		if (_declaredType!=null) {
			_rawTypeAsString=_declaredType.getRawTypeAsString();
		}
		String _plus = (_typingStrategy + _rawTypeAsString);
		String _xifexpression = null;
		boolean _isEmpty = this.getTypeArgs().isEmpty();
		if (_isEmpty) {
			_xifexpression = "";
		}
		else {
			final Function1<TypeArgument, String> _function = new Function1<TypeArgument, String>() {
				public String apply(final TypeArgument it) {
					return it.getTypeRefAsString();
				}
			};
			String _join = IterableExtensions.join(XcoreEListExtensions.<TypeArgument, String>map(this.getTypeArgs(), _function), ",");
			String _plus_1 = ("<" + _join);
			_xifexpression = (_plus_1 + ">");
		}
		String _plus_2 = (_plus + _xifexpression);
		String _xifexpression_1 = null;
		boolean _isEmpty_1 = this.getStructuralMembers().isEmpty();
		if (_isEmpty_1) {
			_xifexpression_1 = "";
		}
		else {
			final Function1<TStructMember, String> _function_1 = new Function1<TStructMember, String>() {
				public String apply(final TStructMember it) {
					return it.getMemberAsString();
				}
			};
			String _join_1 = IterableExtensions.join(XcoreEListExtensions.<TStructMember, String>map(this.getStructuralMembers(), _function_1), "; ");
			String _plus_3 = (" with { " + _join_1);
			String _plus_4 = (_plus_3 + " }");
			String _xifexpression_2 = null;
			boolean _isEmpty_2 = this.getPostponedSubstitutions().isEmpty();
			if (_isEmpty_2) {
				_xifexpression_2 = "";
			}
			else {
				final Function1<TypeVariableMapping, String> _function_2 = new Function1<TypeVariableMapping, String>() {
					public String apply(final TypeVariableMapping it) {
						String _typeAsString = it.getTypeVar().getTypeAsString();
						String _plus = (_typeAsString + "->");
						String _typeRefAsString = it.getTypeArg().getTypeRefAsString();
						return (_plus + _typeRefAsString);
					}
				};
				String _join_2 = IterableExtensions.join(XcoreEListExtensions.<TypeVariableMapping, String>map(this.getPostponedSubstitutions(), _function_2), ", ");
				String _plus_5 = (" [[" + _join_2);
				_xifexpression_2 = (_plus_5 + "]]");
			}
			_xifexpression_1 = (_plus_4 + _xifexpression_2);
		}
		return (_plus_2 + _xifexpression_1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean hasPostponedSubstitutionFor(final TypeVariable typeVar) {
		final Function1<TypeVariableMapping, Boolean> _function = new Function1<TypeVariableMapping, Boolean>() {
			public Boolean apply(final TypeVariableMapping m) {
				TypeVariable _typeVar = null;
				if (m!=null) {
					_typeVar=m.getTypeVar();
				}
				return Boolean.valueOf((_typeVar == typeVar));
			}
		};
		return IterableExtensions.<TypeVariableMapping>exists(this.getPostponedSubstitutions(), _function);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getVersion() {
		int _xifexpression = (int) 0;
		boolean _hasRequestedVersion = this.hasRequestedVersion();
		if (_hasRequestedVersion) {
			_xifexpression = this.getRequestedVersion().intValue();
		}
		else {
			_xifexpression = super.getVersion();
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean hasRequestedVersion() {
		BigDecimal _requestedVersion = this.getRequestedVersion();
		return (_requestedVersion != null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getRequestedVersionOrZero() {
		int _xifexpression = (int) 0;
		boolean _hasRequestedVersion = this.hasRequestedVersion();
		if (_hasRequestedVersion) {
			_xifexpression = this.getRequestedVersion().intValue();
		}
		else {
			_xifexpression = 0;
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_STRUCTURAL_MEMBERS:
				return ((InternalEList<?>)getAstStructuralMembers()).basicRemove(otherEnd, msgs);
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__GEN_STRUCTURAL_MEMBERS:
				return ((InternalEList<?>)getGenStructuralMembers()).basicRemove(otherEnd, msgs);
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__POSTPONED_SUBSTITUTIONS:
				return ((InternalEList<?>)getPostponedSubstitutions()).basicRemove(otherEnd, msgs);
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
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__REQUESTED_VERSION:
				return getRequestedVersion();
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_STRUCTURAL_MEMBERS:
				return getAstStructuralMembers();
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__STRUCTURAL_TYPE:
				if (resolve) return getStructuralType();
				return basicGetStructuralType();
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__GEN_STRUCTURAL_MEMBERS:
				return getGenStructuralMembers();
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__POSTPONED_SUBSTITUTIONS:
				return getPostponedSubstitutions();
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
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__REQUESTED_VERSION:
				setRequestedVersion((BigDecimal)newValue);
				return;
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_STRUCTURAL_MEMBERS:
				getAstStructuralMembers().clear();
				getAstStructuralMembers().addAll((Collection<? extends TStructMember>)newValue);
				return;
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__STRUCTURAL_TYPE:
				setStructuralType((TStructuralType)newValue);
				return;
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__GEN_STRUCTURAL_MEMBERS:
				getGenStructuralMembers().clear();
				getGenStructuralMembers().addAll((Collection<? extends TStructMember>)newValue);
				return;
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__POSTPONED_SUBSTITUTIONS:
				getPostponedSubstitutions().clear();
				getPostponedSubstitutions().addAll((Collection<? extends TypeVariableMapping>)newValue);
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
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__REQUESTED_VERSION:
				setRequestedVersion(REQUESTED_VERSION_EDEFAULT);
				return;
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_STRUCTURAL_MEMBERS:
				getAstStructuralMembers().clear();
				return;
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__STRUCTURAL_TYPE:
				setStructuralType((TStructuralType)null);
				return;
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__GEN_STRUCTURAL_MEMBERS:
				getGenStructuralMembers().clear();
				return;
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__POSTPONED_SUBSTITUTIONS:
				getPostponedSubstitutions().clear();
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
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__REQUESTED_VERSION:
				return REQUESTED_VERSION_EDEFAULT == null ? requestedVersion != null : !REQUESTED_VERSION_EDEFAULT.equals(requestedVersion);
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_STRUCTURAL_MEMBERS:
				return astStructuralMembers != null && !astStructuralMembers.isEmpty();
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__STRUCTURAL_TYPE:
				return structuralType != null;
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__GEN_STRUCTURAL_MEMBERS:
				return genStructuralMembers != null && !genStructuralMembers.isEmpty();
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__POSTPONED_SUBSTITUTIONS:
				return postponedSubstitutions != null && !postponedSubstitutions.isEmpty();
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
		if (baseClass == VersionedReference.class) {
			switch (derivedFeatureID) {
				case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__REQUESTED_VERSION: return TypeRefsPackage.VERSIONED_REFERENCE__REQUESTED_VERSION;
				default: return -1;
			}
		}
		if (baseClass == VersionedParameterizedTypeRef.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == StructuralTypeRef.class) {
			switch (derivedFeatureID) {
				case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_STRUCTURAL_MEMBERS: return TypeRefsPackage.STRUCTURAL_TYPE_REF__AST_STRUCTURAL_MEMBERS;
				case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__STRUCTURAL_TYPE: return TypeRefsPackage.STRUCTURAL_TYPE_REF__STRUCTURAL_TYPE;
				case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__GEN_STRUCTURAL_MEMBERS: return TypeRefsPackage.STRUCTURAL_TYPE_REF__GEN_STRUCTURAL_MEMBERS;
				case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__POSTPONED_SUBSTITUTIONS: return TypeRefsPackage.STRUCTURAL_TYPE_REF__POSTPONED_SUBSTITUTIONS;
				default: return -1;
			}
		}
		if (baseClass == ParameterizedTypeRefStructural.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == VersionedParameterizedTypeRefStructural.class) {
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
		if (baseClass == VersionedReference.class) {
			switch (baseFeatureID) {
				case TypeRefsPackage.VERSIONED_REFERENCE__REQUESTED_VERSION: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__REQUESTED_VERSION;
				default: return -1;
			}
		}
		if (baseClass == VersionedParameterizedTypeRef.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == StructuralTypeRef.class) {
			switch (baseFeatureID) {
				case TypeRefsPackage.STRUCTURAL_TYPE_REF__AST_STRUCTURAL_MEMBERS: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF__STRUCTURAL_TYPE: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__STRUCTURAL_TYPE;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF__GEN_STRUCTURAL_MEMBERS: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__GEN_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF__POSTPONED_SUBSTITUTIONS: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__POSTPONED_SUBSTITUTIONS;
				default: return -1;
			}
		}
		if (baseClass == ParameterizedTypeRefStructural.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == VersionedParameterizedTypeRefStructural.class) {
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
		if (baseClass == TypeArgument.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_ARGUMENT___INTERNAL_GET_TYPE_REF_AS_STRING: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___INTERNAL_GET_TYPE_REF_AS_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == Versionable.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.VERSIONABLE___GET_VERSION: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_VERSION;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_REF___GET_TYPING_STRATEGY: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPING_STRATEGY;
				case TypeRefsPackage.TYPE_REF___GET_STRUCTURAL_MEMBERS: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.TYPE_REF___GET_VERSION: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_VERSION;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == ParameterizedTypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.PARAMETERIZED_TYPE_REF___GET_TYPING_STRATEGY: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPING_STRATEGY;
				case TypeRefsPackage.PARAMETERIZED_TYPE_REF___INTERNAL_GET_TYPE_REF_AS_STRING: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___INTERNAL_GET_TYPE_REF_AS_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == VersionedReference.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.VERSIONED_REFERENCE___HAS_REQUESTED_VERSION: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___HAS_REQUESTED_VERSION;
				case TypeRefsPackage.VERSIONED_REFERENCE___GET_REQUESTED_VERSION_OR_ZERO: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_REQUESTED_VERSION_OR_ZERO;
				default: return -1;
			}
		}
		if (baseClass == VersionedParameterizedTypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.VERSIONED_PARAMETERIZED_TYPE_REF___GET_VERSION: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_VERSION;
				default: return -1;
			}
		}
		if (baseClass == StructuralTypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.STRUCTURAL_TYPE_REF___GET_TYPING_STRATEGY: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPING_STRATEGY;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF___SET_TYPING_STRATEGY__TYPINGSTRATEGY: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___SET_TYPING_STRATEGY__TYPINGSTRATEGY;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF___GET_STRUCTURAL_MEMBERS: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF___HAS_POSTPONED_SUBSTITUTION_FOR__TYPEVARIABLE: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___HAS_POSTPONED_SUBSTITUTION_FOR__TYPEVARIABLE;
				default: return -1;
			}
		}
		if (baseClass == ParameterizedTypeRefStructural.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_TYPING_STRATEGY: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPING_STRATEGY;
				case TypeRefsPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL___SET_TYPING_STRATEGY__TYPINGSTRATEGY: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___SET_TYPING_STRATEGY__TYPINGSTRATEGY;
				case TypeRefsPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_STRUCTURAL_MEMBERS: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL___INTERNAL_GET_TYPE_REF_AS_STRING: return ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___INTERNAL_GET_TYPE_REF_AS_STRING;
				default: return -1;
			}
		}
		if (baseClass == VersionedParameterizedTypeRefStructural.class) {
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
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPING_STRATEGY:
				return getTypingStrategy();
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___SET_TYPING_STRATEGY__TYPINGSTRATEGY:
				setTypingStrategy((TypingStrategy)arguments.get(0));
				return null;
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_STRUCTURAL_MEMBERS:
				return getStructuralMembers();
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___INTERNAL_GET_TYPE_REF_AS_STRING:
				return internalGetTypeRefAsString();
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___HAS_POSTPONED_SUBSTITUTION_FOR__TYPEVARIABLE:
				return hasPostponedSubstitutionFor((TypeVariable)arguments.get(0));
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_VERSION:
				return getVersion();
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___HAS_REQUESTED_VERSION:
				return hasRequestedVersion();
			case ImPackage.VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_REQUESTED_VERSION_OR_ZERO:
				return getRequestedVersionOrZero();
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
		result.append(" (requestedVersion: ");
		result.append(requestedVersion);
		result.append(')');
		return result.toString();
	}

} //VersionedParameterizedTypeRefStructural_IMImpl
