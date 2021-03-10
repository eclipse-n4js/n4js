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
package org.eclipse.n4js.ts.typeRefs.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals;

import org.eclipse.n4js.ts.typeRefs.OptionalFieldStrategy;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.Versionable;

import org.eclipse.n4js.ts.types.AnyType;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.ts.types.UndefinedType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.TypeRefImpl#isFollowedByQuestionMark <em>Followed By Question Mark</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.TypeRefImpl#getOriginalAliasTypeRef <em>Original Alias Type Ref</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class TypeRefImpl extends TypeArgumentImpl implements TypeRef {
	/**
	 * The default value of the '{@link #isFollowedByQuestionMark() <em>Followed By Question Mark</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFollowedByQuestionMark()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FOLLOWED_BY_QUESTION_MARK_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFollowedByQuestionMark() <em>Followed By Question Mark</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFollowedByQuestionMark()
	 * @generated
	 * @ordered
	 */
	protected boolean followedByQuestionMark = FOLLOWED_BY_QUESTION_MARK_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOriginalAliasTypeRef() <em>Original Alias Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalAliasTypeRef()
	 * @generated
	 * @ordered
	 */
	protected ParameterizedTypeRef originalAliasTypeRef;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.TYPE_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFollowedByQuestionMark() {
		return followedByQuestionMark;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFollowedByQuestionMark(boolean newFollowedByQuestionMark) {
		boolean oldFollowedByQuestionMark = followedByQuestionMark;
		followedByQuestionMark = newFollowedByQuestionMark;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.TYPE_REF__FOLLOWED_BY_QUESTION_MARK, oldFollowedByQuestionMark, followedByQuestionMark));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ParameterizedTypeRef getOriginalAliasTypeRef() {
		return originalAliasTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOriginalAliasTypeRef(ParameterizedTypeRef newOriginalAliasTypeRef, NotificationChain msgs) {
		ParameterizedTypeRef oldOriginalAliasTypeRef = originalAliasTypeRef;
		originalAliasTypeRef = newOriginalAliasTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypeRefsPackage.TYPE_REF__ORIGINAL_ALIAS_TYPE_REF, oldOriginalAliasTypeRef, newOriginalAliasTypeRef);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOriginalAliasTypeRef(ParameterizedTypeRef newOriginalAliasTypeRef) {
		if (newOriginalAliasTypeRef != originalAliasTypeRef) {
			NotificationChain msgs = null;
			if (originalAliasTypeRef != null)
				msgs = ((InternalEObject)originalAliasTypeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.TYPE_REF__ORIGINAL_ALIAS_TYPE_REF, null, msgs);
			if (newOriginalAliasTypeRef != null)
				msgs = ((InternalEObject)newOriginalAliasTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.TYPE_REF__ORIGINAL_ALIAS_TYPE_REF, null, msgs);
			msgs = basicSetOriginalAliasTypeRef(newOriginalAliasTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.TYPE_REF__ORIGINAL_ALIAS_TYPE_REF, newOriginalAliasTypeRef, newOriginalAliasTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getModifiersAsString() {
		return "";
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isTypeRef() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAliasUnresolved() {
		final Type declType = this.getDeclaredType();
		return ((declType != null) && declType.isAlias());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAliasResolved() {
		ParameterizedTypeRef _originalAliasTypeRef = this.getOriginalAliasTypeRef();
		return (_originalAliasTypeRef != null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFinalByType() {
		final Type dtype = this.getDeclaredType();
		return ((dtype != null) && dtype.isFinal());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isArrayLike() {
		final Type dtype = this.getDeclaredType();
		return ((dtype != null) && dtype.isArrayLike());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isUnknown() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDynamic() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isExistential() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isGeneric() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isParameterized() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isRaw() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ParameterizedTypeRef getDeclaredUpperBound() {
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ParameterizedTypeRef getDeclaredLowerBound() {
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypeArgument> getTypeArgs() {
		return XcoreCollectionLiterals.<TypeArgument>emptyEList();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTypeRefAsString() {
		final ParameterizedTypeRef oatr = this.getOriginalAliasTypeRef();
		if ((oatr != null)) {
			return oatr.getTypeRefAsString();
		}
		return this.internalGetTypeRefAsString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTypeRefAsStringWithAliasResolution() {
		final String result = this.getTypeRefAsString();
		boolean _isAliasResolved = this.isAliasResolved();
		if (_isAliasResolved) {
			String _internalGetTypeRefAsString = this.internalGetTypeRefAsString();
			return ((result + " <=> ") + _internalGetTypeRefAsString);
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return this.getTypeRefAsStringWithAliasResolution();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isTopType() {
		Type _declaredType = this.getDeclaredType();
		return (_declaredType instanceof AnyType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isBottomType() {
		Type _declaredType = this.getDeclaredType();
		return (_declaredType instanceof UndefinedType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypingStrategy getTypingStrategy() {
		return TypingStrategy.NOMINAL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TStructMember> getStructuralMembers() {
		return XcoreCollectionLiterals.<TStructMember>emptyEList();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isUseSiteStructuralTyping() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDefSiteStructuralTyping() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OptionalFieldStrategy getASTNodeOptionalFieldStrategy() {
		return OptionalFieldStrategy.OFF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getVersion() {
		int _xifexpression = (int) 0;
		Type _declaredType = this.getDeclaredType();
		boolean _tripleNotEquals = (_declaredType != null);
		if (_tripleNotEquals) {
			_xifexpression = this.getDeclaredType().getVersion();
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
			case TypeRefsPackage.TYPE_REF__ORIGINAL_ALIAS_TYPE_REF:
				return basicSetOriginalAliasTypeRef(null, msgs);
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
			case TypeRefsPackage.TYPE_REF__FOLLOWED_BY_QUESTION_MARK:
				return isFollowedByQuestionMark();
			case TypeRefsPackage.TYPE_REF__ORIGINAL_ALIAS_TYPE_REF:
				return getOriginalAliasTypeRef();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TypeRefsPackage.TYPE_REF__FOLLOWED_BY_QUESTION_MARK:
				setFollowedByQuestionMark((Boolean)newValue);
				return;
			case TypeRefsPackage.TYPE_REF__ORIGINAL_ALIAS_TYPE_REF:
				setOriginalAliasTypeRef((ParameterizedTypeRef)newValue);
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
			case TypeRefsPackage.TYPE_REF__FOLLOWED_BY_QUESTION_MARK:
				setFollowedByQuestionMark(FOLLOWED_BY_QUESTION_MARK_EDEFAULT);
				return;
			case TypeRefsPackage.TYPE_REF__ORIGINAL_ALIAS_TYPE_REF:
				setOriginalAliasTypeRef((ParameterizedTypeRef)null);
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
			case TypeRefsPackage.TYPE_REF__FOLLOWED_BY_QUESTION_MARK:
				return followedByQuestionMark != FOLLOWED_BY_QUESTION_MARK_EDEFAULT;
			case TypeRefsPackage.TYPE_REF__ORIGINAL_ALIAS_TYPE_REF:
				return originalAliasTypeRef != null;
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
		if (baseClass == TypeArgument.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_ARGUMENT___IS_TYPE_REF: return TypeRefsPackage.TYPE_REF___IS_TYPE_REF;
				case TypeRefsPackage.TYPE_ARGUMENT___GET_TYPE_REF_AS_STRING: return TypeRefsPackage.TYPE_REF___GET_TYPE_REF_AS_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == Versionable.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.VERSIONABLE___GET_VERSION: return TypeRefsPackage.TYPE_REF___GET_VERSION;
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
			case TypeRefsPackage.TYPE_REF___GET_MODIFIERS_AS_STRING:
				return getModifiersAsString();
			case TypeRefsPackage.TYPE_REF___IS_TYPE_REF:
				return isTypeRef();
			case TypeRefsPackage.TYPE_REF___IS_ALIAS_UNRESOLVED:
				return isAliasUnresolved();
			case TypeRefsPackage.TYPE_REF___IS_ALIAS_RESOLVED:
				return isAliasResolved();
			case TypeRefsPackage.TYPE_REF___IS_FINAL_BY_TYPE:
				return isFinalByType();
			case TypeRefsPackage.TYPE_REF___IS_ARRAY_LIKE:
				return isArrayLike();
			case TypeRefsPackage.TYPE_REF___IS_UNKNOWN:
				return isUnknown();
			case TypeRefsPackage.TYPE_REF___IS_DYNAMIC:
				return isDynamic();
			case TypeRefsPackage.TYPE_REF___IS_EXISTENTIAL:
				return isExistential();
			case TypeRefsPackage.TYPE_REF___IS_GENERIC:
				return isGeneric();
			case TypeRefsPackage.TYPE_REF___IS_PARAMETERIZED:
				return isParameterized();
			case TypeRefsPackage.TYPE_REF___IS_RAW:
				return isRaw();
			case TypeRefsPackage.TYPE_REF___GET_DECLARED_UPPER_BOUND:
				return getDeclaredUpperBound();
			case TypeRefsPackage.TYPE_REF___GET_DECLARED_LOWER_BOUND:
				return getDeclaredLowerBound();
			case TypeRefsPackage.TYPE_REF___GET_TYPE_ARGS:
				return getTypeArgs();
			case TypeRefsPackage.TYPE_REF___GET_TYPE_REF_AS_STRING:
				return getTypeRefAsString();
			case TypeRefsPackage.TYPE_REF___GET_TYPE_REF_AS_STRING_WITH_ALIAS_RESOLUTION:
				return getTypeRefAsStringWithAliasResolution();
			case TypeRefsPackage.TYPE_REF___TO_STRING:
				return toString();
			case TypeRefsPackage.TYPE_REF___IS_TOP_TYPE:
				return isTopType();
			case TypeRefsPackage.TYPE_REF___IS_BOTTOM_TYPE:
				return isBottomType();
			case TypeRefsPackage.TYPE_REF___GET_TYPING_STRATEGY:
				return getTypingStrategy();
			case TypeRefsPackage.TYPE_REF___GET_STRUCTURAL_MEMBERS:
				return getStructuralMembers();
			case TypeRefsPackage.TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING:
				return isUseSiteStructuralTyping();
			case TypeRefsPackage.TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING:
				return isDefSiteStructuralTyping();
			case TypeRefsPackage.TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY:
				return getASTNodeOptionalFieldStrategy();
			case TypeRefsPackage.TYPE_REF___GET_VERSION:
				return getVersion();
		}
		return super.eInvoke(operationID, arguments);
	}

} //TypeRefImpl
