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

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.MemberType;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMemberWithAccessModifier;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.n4js.ts.types.util.AccessModifiers;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TMethod</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMethodImpl#isDeclaredFinal <em>Declared Final</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMethodImpl#isDeclaredStatic <em>Declared Static</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMethodImpl#isDeclaredOverride <em>Declared Override</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMethodImpl#isHasComputedName <em>Has Computed Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMethodImpl#getConstituentMembers <em>Constituent Members</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMethodImpl#isComposed <em>Composed</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMethodImpl#isHasNoBody <em>Has No Body</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMethodImpl#getDeclaredMemberAccessModifier <em>Declared Member Access Modifier</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMethodImpl#isDeclaredAbstract <em>Declared Abstract</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMethodImpl#isLacksThisOrSuperUsage <em>Lacks This Or Super Usage</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TMethodImpl extends TFunctionImpl implements TMethod {
	/**
	 * The default value of the '{@link #isDeclaredFinal() <em>Declared Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredFinal()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_FINAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredFinal() <em>Declared Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredFinal()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredFinal = DECLARED_FINAL_EDEFAULT;

	/**
	 * The default value of the '{@link #isDeclaredStatic() <em>Declared Static</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredStatic()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_STATIC_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredStatic() <em>Declared Static</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredStatic()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredStatic = DECLARED_STATIC_EDEFAULT;

	/**
	 * The default value of the '{@link #isDeclaredOverride() <em>Declared Override</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredOverride()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_OVERRIDE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredOverride() <em>Declared Override</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredOverride()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredOverride = DECLARED_OVERRIDE_EDEFAULT;

	/**
	 * The default value of the '{@link #isHasComputedName() <em>Has Computed Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasComputedName()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HAS_COMPUTED_NAME_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHasComputedName() <em>Has Computed Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasComputedName()
	 * @generated
	 * @ordered
	 */
	protected boolean hasComputedName = HAS_COMPUTED_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getConstituentMembers() <em>Constituent Members</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstituentMembers()
	 * @generated
	 * @ordered
	 */
	protected EList<TMember> constituentMembers;

	/**
	 * The default value of the '{@link #isComposed() <em>Composed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isComposed()
	 * @generated
	 * @ordered
	 */
	protected static final boolean COMPOSED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isComposed() <em>Composed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isComposed()
	 * @generated
	 * @ordered
	 */
	protected boolean composed = COMPOSED_EDEFAULT;

	/**
	 * The default value of the '{@link #isHasNoBody() <em>Has No Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasNoBody()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HAS_NO_BODY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHasNoBody() <em>Has No Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasNoBody()
	 * @generated
	 * @ordered
	 */
	protected boolean hasNoBody = HAS_NO_BODY_EDEFAULT;

	/**
	 * The default value of the '{@link #getDeclaredMemberAccessModifier() <em>Declared Member Access Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredMemberAccessModifier()
	 * @generated
	 * @ordered
	 */
	protected static final MemberAccessModifier DECLARED_MEMBER_ACCESS_MODIFIER_EDEFAULT = MemberAccessModifier.UNDEFINED;

	/**
	 * The cached value of the '{@link #getDeclaredMemberAccessModifier() <em>Declared Member Access Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredMemberAccessModifier()
	 * @generated
	 * @ordered
	 */
	protected MemberAccessModifier declaredMemberAccessModifier = DECLARED_MEMBER_ACCESS_MODIFIER_EDEFAULT;

	/**
	 * The default value of the '{@link #isDeclaredAbstract() <em>Declared Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredAbstract()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_ABSTRACT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredAbstract() <em>Declared Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredAbstract()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredAbstract = DECLARED_ABSTRACT_EDEFAULT;

	/**
	 * The default value of the '{@link #isLacksThisOrSuperUsage() <em>Lacks This Or Super Usage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLacksThisOrSuperUsage()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LACKS_THIS_OR_SUPER_USAGE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLacksThisOrSuperUsage() <em>Lacks This Or Super Usage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLacksThisOrSuperUsage()
	 * @generated
	 * @ordered
	 */
	protected boolean lacksThisOrSuperUsage = LACKS_THIS_OR_SUPER_USAGE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TMethodImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TMETHOD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDeclaredFinal() {
		return declaredFinal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeclaredFinal(boolean newDeclaredFinal) {
		boolean oldDeclaredFinal = declaredFinal;
		declaredFinal = newDeclaredFinal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMETHOD__DECLARED_FINAL, oldDeclaredFinal, declaredFinal));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDeclaredStatic() {
		return declaredStatic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeclaredStatic(boolean newDeclaredStatic) {
		boolean oldDeclaredStatic = declaredStatic;
		declaredStatic = newDeclaredStatic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMETHOD__DECLARED_STATIC, oldDeclaredStatic, declaredStatic));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDeclaredOverride() {
		return declaredOverride;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeclaredOverride(boolean newDeclaredOverride) {
		boolean oldDeclaredOverride = declaredOverride;
		declaredOverride = newDeclaredOverride;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMETHOD__DECLARED_OVERRIDE, oldDeclaredOverride, declaredOverride));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHasComputedName() {
		return hasComputedName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasComputedName(boolean newHasComputedName) {
		boolean oldHasComputedName = hasComputedName;
		hasComputedName = newHasComputedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMETHOD__HAS_COMPUTED_NAME, oldHasComputedName, hasComputedName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TMember> getConstituentMembers() {
		if (constituentMembers == null) {
			constituentMembers = new EObjectResolvingEList<TMember>(TMember.class, this, TypesPackage.TMETHOD__CONSTITUENT_MEMBERS);
		}
		return constituentMembers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isComposed() {
		return composed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComposed(boolean newComposed) {
		boolean oldComposed = composed;
		composed = newComposed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMETHOD__COMPOSED, oldComposed, composed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHasNoBody() {
		return hasNoBody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasNoBody(boolean newHasNoBody) {
		boolean oldHasNoBody = hasNoBody;
		hasNoBody = newHasNoBody;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMETHOD__HAS_NO_BODY, oldHasNoBody, hasNoBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MemberAccessModifier getDeclaredMemberAccessModifier() {
		return declaredMemberAccessModifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeclaredMemberAccessModifier(MemberAccessModifier newDeclaredMemberAccessModifier) {
		MemberAccessModifier oldDeclaredMemberAccessModifier = declaredMemberAccessModifier;
		declaredMemberAccessModifier = newDeclaredMemberAccessModifier == null ? DECLARED_MEMBER_ACCESS_MODIFIER_EDEFAULT : newDeclaredMemberAccessModifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMETHOD__DECLARED_MEMBER_ACCESS_MODIFIER, oldDeclaredMemberAccessModifier, declaredMemberAccessModifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDeclaredAbstract() {
		return declaredAbstract;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeclaredAbstract(boolean newDeclaredAbstract) {
		boolean oldDeclaredAbstract = declaredAbstract;
		declaredAbstract = newDeclaredAbstract;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMETHOD__DECLARED_ABSTRACT, oldDeclaredAbstract, declaredAbstract));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isLacksThisOrSuperUsage() {
		return lacksThisOrSuperUsage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLacksThisOrSuperUsage(boolean newLacksThisOrSuperUsage) {
		boolean oldLacksThisOrSuperUsage = lacksThisOrSuperUsage;
		lacksThisOrSuperUsage = newLacksThisOrSuperUsage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMETHOD__LACKS_THIS_OR_SUPER_USAGE, oldLacksThisOrSuperUsage, lacksThisOrSuperUsage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAbstract() {
		return (this.isDeclaredAbstract() || ((this.eContainer() instanceof TInterface) && this.isHasNoBody()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MemberType getMemberType() {
		return MemberType.METHOD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isConstructor() {
		return (Objects.equal(this.getName(), "constructor") && (!this.isStatic()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFunctionAsString() {
		final StringBuilder strb = new StringBuilder();
		boolean _isGeneric = this.isGeneric();
		if (_isGeneric) {
			final Function1<TypeVariable, String> _function = new Function1<TypeVariable, String>() {
				public String apply(final TypeVariable it) {
					return it.getTypeAsString();
				}
			};
			strb.append("<").append(IterableExtensions.join(XcoreEListExtensions.<TypeVariable, String>map(this.getTypeVars(), _function), ",")).append("> ");
		}
		boolean _isDeclaredAsync = this.isDeclaredAsync();
		if (_isDeclaredAsync) {
			strb.append("async ");
		}
		final Function1<TFormalParameter, String> _function_1 = new Function1<TFormalParameter, String>() {
			public String apply(final TFormalParameter it) {
				return it.getFormalParameterAsString();
			}
		};
		strb.append(this.getName()).append("(").append(IterableExtensions.join(XcoreEListExtensions.<TFormalParameter, String>map(this.getFpars(), _function_1), ", ")).append(")");
		TypeRef _returnTypeRef = this.getReturnTypeRef();
		boolean _tripleNotEquals = (_returnTypeRef != null);
		if (_tripleNotEquals) {
			strb.append(": ").append(this.getReturnTypeRef().getTypeRefAsString());
		}
		boolean _isReturnValueOptional = this.isReturnValueOptional();
		if (_isReturnValueOptional) {
			strb.append("?");
		}
		return strb.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMemberAsString() {
		return this.getFunctionAsString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MemberAccessModifier getMemberAccessModifier() {
		MemberAccessModifier _declaredMemberAccessModifier = this.getDeclaredMemberAccessModifier();
		boolean _tripleEquals = (_declaredMemberAccessModifier == MemberAccessModifier.UNDEFINED);
		if (_tripleEquals) {
			final EObject parent = this.eContainer();
			if ((parent instanceof TInterface)) {
				final MemberAccessModifier modifierDerivedFromType = AccessModifiers.toMemberModifier(((Type) parent).getTypeAccessModifier());
				if ((modifierDerivedFromType != MemberAccessModifier.PRIVATE)) {
					return modifierDerivedFromType;
				}
			}
			return MemberAccessModifier.PROJECT;
		}
		return this.getDeclaredMemberAccessModifier();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContainerType<?> getContainingType() {
		final EObject myContainer = this.eContainer();
		ContainerType<?> _xifexpression = null;
		if ((myContainer instanceof ContainerType<?>)) {
			_xifexpression = ((ContainerType<?>)myContainer);
		}
		else {
			_xifexpression = null;
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isField() {
		MemberType _memberType = this.getMemberType();
		return Objects.equal(_memberType, MemberType.FIELD);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGetter() {
		MemberType _memberType = this.getMemberType();
		return Objects.equal(_memberType, MemberType.GETTER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetter() {
		MemberType _memberType = this.getMemberType();
		return Objects.equal(_memberType, MemberType.SETTER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAccessor() {
		return (Objects.equal(this.getMemberType(), MemberType.SETTER) || Objects.equal(this.getMemberType(), MemberType.GETTER));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMethod() {
		MemberType _memberType = this.getMemberType();
		return Objects.equal(_memberType, MemberType.METHOD);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isOptional() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isReadable() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isWriteable() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isFinal() {
		return this.isDeclaredFinal();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isStatic() {
		return this.isDeclaredStatic();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isConst() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPolyfilled() {
		final ContainerType<?> containingType = this.getContainingType();
		if ((containingType == null)) {
			return false;
		}
		return (containingType.isPolyfill() || containingType.isStaticPolyfill());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.TMETHOD__DECLARED_FINAL:
				return isDeclaredFinal();
			case TypesPackage.TMETHOD__DECLARED_STATIC:
				return isDeclaredStatic();
			case TypesPackage.TMETHOD__DECLARED_OVERRIDE:
				return isDeclaredOverride();
			case TypesPackage.TMETHOD__HAS_COMPUTED_NAME:
				return isHasComputedName();
			case TypesPackage.TMETHOD__CONSTITUENT_MEMBERS:
				return getConstituentMembers();
			case TypesPackage.TMETHOD__COMPOSED:
				return isComposed();
			case TypesPackage.TMETHOD__HAS_NO_BODY:
				return isHasNoBody();
			case TypesPackage.TMETHOD__DECLARED_MEMBER_ACCESS_MODIFIER:
				return getDeclaredMemberAccessModifier();
			case TypesPackage.TMETHOD__DECLARED_ABSTRACT:
				return isDeclaredAbstract();
			case TypesPackage.TMETHOD__LACKS_THIS_OR_SUPER_USAGE:
				return isLacksThisOrSuperUsage();
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
			case TypesPackage.TMETHOD__DECLARED_FINAL:
				setDeclaredFinal((Boolean)newValue);
				return;
			case TypesPackage.TMETHOD__DECLARED_STATIC:
				setDeclaredStatic((Boolean)newValue);
				return;
			case TypesPackage.TMETHOD__DECLARED_OVERRIDE:
				setDeclaredOverride((Boolean)newValue);
				return;
			case TypesPackage.TMETHOD__HAS_COMPUTED_NAME:
				setHasComputedName((Boolean)newValue);
				return;
			case TypesPackage.TMETHOD__CONSTITUENT_MEMBERS:
				getConstituentMembers().clear();
				getConstituentMembers().addAll((Collection<? extends TMember>)newValue);
				return;
			case TypesPackage.TMETHOD__COMPOSED:
				setComposed((Boolean)newValue);
				return;
			case TypesPackage.TMETHOD__HAS_NO_BODY:
				setHasNoBody((Boolean)newValue);
				return;
			case TypesPackage.TMETHOD__DECLARED_MEMBER_ACCESS_MODIFIER:
				setDeclaredMemberAccessModifier((MemberAccessModifier)newValue);
				return;
			case TypesPackage.TMETHOD__DECLARED_ABSTRACT:
				setDeclaredAbstract((Boolean)newValue);
				return;
			case TypesPackage.TMETHOD__LACKS_THIS_OR_SUPER_USAGE:
				setLacksThisOrSuperUsage((Boolean)newValue);
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
			case TypesPackage.TMETHOD__DECLARED_FINAL:
				setDeclaredFinal(DECLARED_FINAL_EDEFAULT);
				return;
			case TypesPackage.TMETHOD__DECLARED_STATIC:
				setDeclaredStatic(DECLARED_STATIC_EDEFAULT);
				return;
			case TypesPackage.TMETHOD__DECLARED_OVERRIDE:
				setDeclaredOverride(DECLARED_OVERRIDE_EDEFAULT);
				return;
			case TypesPackage.TMETHOD__HAS_COMPUTED_NAME:
				setHasComputedName(HAS_COMPUTED_NAME_EDEFAULT);
				return;
			case TypesPackage.TMETHOD__CONSTITUENT_MEMBERS:
				getConstituentMembers().clear();
				return;
			case TypesPackage.TMETHOD__COMPOSED:
				setComposed(COMPOSED_EDEFAULT);
				return;
			case TypesPackage.TMETHOD__HAS_NO_BODY:
				setHasNoBody(HAS_NO_BODY_EDEFAULT);
				return;
			case TypesPackage.TMETHOD__DECLARED_MEMBER_ACCESS_MODIFIER:
				setDeclaredMemberAccessModifier(DECLARED_MEMBER_ACCESS_MODIFIER_EDEFAULT);
				return;
			case TypesPackage.TMETHOD__DECLARED_ABSTRACT:
				setDeclaredAbstract(DECLARED_ABSTRACT_EDEFAULT);
				return;
			case TypesPackage.TMETHOD__LACKS_THIS_OR_SUPER_USAGE:
				setLacksThisOrSuperUsage(LACKS_THIS_OR_SUPER_USAGE_EDEFAULT);
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
			case TypesPackage.TMETHOD__DECLARED_FINAL:
				return declaredFinal != DECLARED_FINAL_EDEFAULT;
			case TypesPackage.TMETHOD__DECLARED_STATIC:
				return declaredStatic != DECLARED_STATIC_EDEFAULT;
			case TypesPackage.TMETHOD__DECLARED_OVERRIDE:
				return declaredOverride != DECLARED_OVERRIDE_EDEFAULT;
			case TypesPackage.TMETHOD__HAS_COMPUTED_NAME:
				return hasComputedName != HAS_COMPUTED_NAME_EDEFAULT;
			case TypesPackage.TMETHOD__CONSTITUENT_MEMBERS:
				return constituentMembers != null && !constituentMembers.isEmpty();
			case TypesPackage.TMETHOD__COMPOSED:
				return composed != COMPOSED_EDEFAULT;
			case TypesPackage.TMETHOD__HAS_NO_BODY:
				return hasNoBody != HAS_NO_BODY_EDEFAULT;
			case TypesPackage.TMETHOD__DECLARED_MEMBER_ACCESS_MODIFIER:
				return declaredMemberAccessModifier != DECLARED_MEMBER_ACCESS_MODIFIER_EDEFAULT;
			case TypesPackage.TMETHOD__DECLARED_ABSTRACT:
				return declaredAbstract != DECLARED_ABSTRACT_EDEFAULT;
			case TypesPackage.TMETHOD__LACKS_THIS_OR_SUPER_USAGE:
				return lacksThisOrSuperUsage != LACKS_THIS_OR_SUPER_USAGE_EDEFAULT;
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
		if (baseClass == TMember.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TMETHOD__DECLARED_FINAL: return TypesPackage.TMEMBER__DECLARED_FINAL;
				case TypesPackage.TMETHOD__DECLARED_STATIC: return TypesPackage.TMEMBER__DECLARED_STATIC;
				case TypesPackage.TMETHOD__DECLARED_OVERRIDE: return TypesPackage.TMEMBER__DECLARED_OVERRIDE;
				case TypesPackage.TMETHOD__HAS_COMPUTED_NAME: return TypesPackage.TMEMBER__HAS_COMPUTED_NAME;
				case TypesPackage.TMETHOD__CONSTITUENT_MEMBERS: return TypesPackage.TMEMBER__CONSTITUENT_MEMBERS;
				case TypesPackage.TMETHOD__COMPOSED: return TypesPackage.TMEMBER__COMPOSED;
				default: return -1;
			}
		}
		if (baseClass == TMemberWithAccessModifier.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TMETHOD__HAS_NO_BODY: return TypesPackage.TMEMBER_WITH_ACCESS_MODIFIER__HAS_NO_BODY;
				case TypesPackage.TMETHOD__DECLARED_MEMBER_ACCESS_MODIFIER: return TypesPackage.TMEMBER_WITH_ACCESS_MODIFIER__DECLARED_MEMBER_ACCESS_MODIFIER;
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
		if (baseClass == TMember.class) {
			switch (baseFeatureID) {
				case TypesPackage.TMEMBER__DECLARED_FINAL: return TypesPackage.TMETHOD__DECLARED_FINAL;
				case TypesPackage.TMEMBER__DECLARED_STATIC: return TypesPackage.TMETHOD__DECLARED_STATIC;
				case TypesPackage.TMEMBER__DECLARED_OVERRIDE: return TypesPackage.TMETHOD__DECLARED_OVERRIDE;
				case TypesPackage.TMEMBER__HAS_COMPUTED_NAME: return TypesPackage.TMETHOD__HAS_COMPUTED_NAME;
				case TypesPackage.TMEMBER__CONSTITUENT_MEMBERS: return TypesPackage.TMETHOD__CONSTITUENT_MEMBERS;
				case TypesPackage.TMEMBER__COMPOSED: return TypesPackage.TMETHOD__COMPOSED;
				default: return -1;
			}
		}
		if (baseClass == TMemberWithAccessModifier.class) {
			switch (baseFeatureID) {
				case TypesPackage.TMEMBER_WITH_ACCESS_MODIFIER__HAS_NO_BODY: return TypesPackage.TMETHOD__HAS_NO_BODY;
				case TypesPackage.TMEMBER_WITH_ACCESS_MODIFIER__DECLARED_MEMBER_ACCESS_MODIFIER: return TypesPackage.TMETHOD__DECLARED_MEMBER_ACCESS_MODIFIER;
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
		if (baseClass == Type.class) {
			switch (baseOperationID) {
				case TypesPackage.TYPE___IS_FINAL: return TypesPackage.TMETHOD___IS_FINAL;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TFunction.class) {
			switch (baseOperationID) {
				case TypesPackage.TFUNCTION___GET_FUNCTION_AS_STRING: return TypesPackage.TMETHOD___GET_FUNCTION_AS_STRING;
				case TypesPackage.TFUNCTION___IS_FINAL: return TypesPackage.TMETHOD___IS_FINAL;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TMember.class) {
			switch (baseOperationID) {
				case TypesPackage.TMEMBER___GET_CONTAINING_TYPE: return TypesPackage.TMETHOD___GET_CONTAINING_TYPE;
				case TypesPackage.TMEMBER___GET_MEMBER_ACCESS_MODIFIER: return TypesPackage.TMETHOD___GET_MEMBER_ACCESS_MODIFIER;
				case TypesPackage.TMEMBER___GET_MEMBER_TYPE: return TypesPackage.TMETHOD___GET_MEMBER_TYPE;
				case TypesPackage.TMEMBER___IS_FIELD: return TypesPackage.TMETHOD___IS_FIELD;
				case TypesPackage.TMEMBER___IS_GETTER: return TypesPackage.TMETHOD___IS_GETTER;
				case TypesPackage.TMEMBER___IS_SETTER: return TypesPackage.TMETHOD___IS_SETTER;
				case TypesPackage.TMEMBER___IS_ACCESSOR: return TypesPackage.TMETHOD___IS_ACCESSOR;
				case TypesPackage.TMEMBER___IS_METHOD: return TypesPackage.TMETHOD___IS_METHOD;
				case TypesPackage.TMEMBER___IS_CONSTRUCTOR: return TypesPackage.TMETHOD___IS_CONSTRUCTOR;
				case TypesPackage.TMEMBER___IS_OPTIONAL: return TypesPackage.TMETHOD___IS_OPTIONAL;
				case TypesPackage.TMEMBER___IS_ABSTRACT: return TypesPackage.TMETHOD___IS_ABSTRACT;
				case TypesPackage.TMEMBER___IS_READABLE: return TypesPackage.TMETHOD___IS_READABLE;
				case TypesPackage.TMEMBER___IS_WRITEABLE: return TypesPackage.TMETHOD___IS_WRITEABLE;
				case TypesPackage.TMEMBER___GET_MEMBER_AS_STRING: return TypesPackage.TMETHOD___GET_MEMBER_AS_STRING;
				case TypesPackage.TMEMBER___IS_FINAL: return TypesPackage.TMETHOD___IS_FINAL;
				case TypesPackage.TMEMBER___IS_STATIC: return TypesPackage.TMETHOD___IS_STATIC;
				case TypesPackage.TMEMBER___IS_CONST: return TypesPackage.TMETHOD___IS_CONST;
				case TypesPackage.TMEMBER___IS_POLYFILLED: return TypesPackage.TMETHOD___IS_POLYFILLED;
				default: return -1;
			}
		}
		if (baseClass == TMemberWithAccessModifier.class) {
			switch (baseOperationID) {
				case TypesPackage.TMEMBER_WITH_ACCESS_MODIFIER___GET_MEMBER_ACCESS_MODIFIER: return TypesPackage.TMETHOD___GET_MEMBER_ACCESS_MODIFIER;
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
			case TypesPackage.TMETHOD___IS_ABSTRACT:
				return isAbstract();
			case TypesPackage.TMETHOD___GET_MEMBER_TYPE:
				return getMemberType();
			case TypesPackage.TMETHOD___IS_CONSTRUCTOR:
				return isConstructor();
			case TypesPackage.TMETHOD___GET_FUNCTION_AS_STRING:
				return getFunctionAsString();
			case TypesPackage.TMETHOD___GET_MEMBER_AS_STRING:
				return getMemberAsString();
			case TypesPackage.TMETHOD___GET_MEMBER_ACCESS_MODIFIER:
				return getMemberAccessModifier();
			case TypesPackage.TMETHOD___GET_CONTAINING_TYPE:
				return getContainingType();
			case TypesPackage.TMETHOD___IS_FIELD:
				return isField();
			case TypesPackage.TMETHOD___IS_GETTER:
				return isGetter();
			case TypesPackage.TMETHOD___IS_SETTER:
				return isSetter();
			case TypesPackage.TMETHOD___IS_ACCESSOR:
				return isAccessor();
			case TypesPackage.TMETHOD___IS_METHOD:
				return isMethod();
			case TypesPackage.TMETHOD___IS_OPTIONAL:
				return isOptional();
			case TypesPackage.TMETHOD___IS_READABLE:
				return isReadable();
			case TypesPackage.TMETHOD___IS_WRITEABLE:
				return isWriteable();
			case TypesPackage.TMETHOD___IS_FINAL:
				return isFinal();
			case TypesPackage.TMETHOD___IS_STATIC:
				return isStatic();
			case TypesPackage.TMETHOD___IS_CONST:
				return isConst();
			case TypesPackage.TMETHOD___IS_POLYFILLED:
				return isPolyfilled();
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
		result.append(" (declaredFinal: ");
		result.append(declaredFinal);
		result.append(", declaredStatic: ");
		result.append(declaredStatic);
		result.append(", declaredOverride: ");
		result.append(declaredOverride);
		result.append(", hasComputedName: ");
		result.append(hasComputedName);
		result.append(", composed: ");
		result.append(composed);
		result.append(", hasNoBody: ");
		result.append(hasNoBody);
		result.append(", declaredMemberAccessModifier: ");
		result.append(declaredMemberAccessModifier);
		result.append(", declaredAbstract: ");
		result.append(declaredAbstract);
		result.append(", lacksThisOrSuperUsage: ");
		result.append(lacksThisOrSuperUsage);
		result.append(')');
		return result.toString();
	}

} //TMethodImpl
