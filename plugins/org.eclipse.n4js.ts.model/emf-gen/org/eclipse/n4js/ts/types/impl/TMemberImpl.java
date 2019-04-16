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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.MemberType;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TAnnotableElement;
import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TMember</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMemberImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMemberImpl#getAstElement <em>Ast Element</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMemberImpl#isDeclaredFinal <em>Declared Final</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMemberImpl#isDeclaredStatic <em>Declared Static</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMemberImpl#isDeclaredOverride <em>Declared Override</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMemberImpl#isHasComputedName <em>Has Computed Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMemberImpl#getConstituentMembers <em>Constituent Members</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TMemberImpl#isComposed <em>Composed</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class TMemberImpl extends IdentifiableElementImpl implements TMember {
	/**
	 * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotations()
	 * @generated
	 * @ordered
	 */
	protected EList<TAnnotation> annotations;

	/**
	 * The cached value of the '{@link #getAstElement() <em>Ast Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAstElement()
	 * @generated
	 * @ordered
	 */
	protected EObject astElement;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TMemberImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TMEMBER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TAnnotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<TAnnotation>(TAnnotation.class, this, TypesPackage.TMEMBER__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject getAstElement() {
		if (astElement != null && astElement.eIsProxy()) {
			InternalEObject oldAstElement = (InternalEObject)astElement;
			astElement = eResolveProxy(oldAstElement);
			if (astElement != oldAstElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.TMEMBER__AST_ELEMENT, oldAstElement, astElement));
			}
		}
		return astElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetAstElement() {
		return astElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAstElement(EObject newAstElement) {
		EObject oldAstElement = astElement;
		astElement = newAstElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMEMBER__AST_ELEMENT, oldAstElement, astElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredFinal() {
		return declaredFinal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredFinal(boolean newDeclaredFinal) {
		boolean oldDeclaredFinal = declaredFinal;
		declaredFinal = newDeclaredFinal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMEMBER__DECLARED_FINAL, oldDeclaredFinal, declaredFinal));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredStatic() {
		return declaredStatic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredStatic(boolean newDeclaredStatic) {
		boolean oldDeclaredStatic = declaredStatic;
		declaredStatic = newDeclaredStatic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMEMBER__DECLARED_STATIC, oldDeclaredStatic, declaredStatic));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredOverride() {
		return declaredOverride;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredOverride(boolean newDeclaredOverride) {
		boolean oldDeclaredOverride = declaredOverride;
		declaredOverride = newDeclaredOverride;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMEMBER__DECLARED_OVERRIDE, oldDeclaredOverride, declaredOverride));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isHasComputedName() {
		return hasComputedName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHasComputedName(boolean newHasComputedName) {
		boolean oldHasComputedName = hasComputedName;
		hasComputedName = newHasComputedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMEMBER__HAS_COMPUTED_NAME, oldHasComputedName, hasComputedName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TMember> getConstituentMembers() {
		if (constituentMembers == null) {
			constituentMembers = new EObjectResolvingEList<TMember>(TMember.class, this, TypesPackage.TMEMBER__CONSTITUENT_MEMBERS);
		}
		return constituentMembers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isComposed() {
		return composed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setComposed(boolean newComposed) {
		boolean oldComposed = composed;
		composed = newComposed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMEMBER__COMPOSED, oldComposed, composed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
	@Override
	public MemberAccessModifier getMemberAccessModifier() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MemberType getMemberType() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isField() {
		MemberType _memberType = this.getMemberType();
		return Objects.equal(_memberType, MemberType.FIELD);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isGetter() {
		MemberType _memberType = this.getMemberType();
		return Objects.equal(_memberType, MemberType.GETTER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetter() {
		MemberType _memberType = this.getMemberType();
		return Objects.equal(_memberType, MemberType.SETTER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAccessor() {
		return (Objects.equal(this.getMemberType(), MemberType.SETTER) || Objects.equal(this.getMemberType(), MemberType.GETTER));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isMethod() {
		MemberType _memberType = this.getMemberType();
		return Objects.equal(_memberType, MemberType.METHOD);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isConstructor() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isOptional() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAbstract() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isReadable() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isWriteable() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getMemberAsString() {
		return this.getName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFinal() {
		return this.isDeclaredFinal();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isStatic() {
		return this.isDeclaredStatic();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isConst() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.TMEMBER__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
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
			case TypesPackage.TMEMBER__ANNOTATIONS:
				return getAnnotations();
			case TypesPackage.TMEMBER__AST_ELEMENT:
				if (resolve) return getAstElement();
				return basicGetAstElement();
			case TypesPackage.TMEMBER__DECLARED_FINAL:
				return isDeclaredFinal();
			case TypesPackage.TMEMBER__DECLARED_STATIC:
				return isDeclaredStatic();
			case TypesPackage.TMEMBER__DECLARED_OVERRIDE:
				return isDeclaredOverride();
			case TypesPackage.TMEMBER__HAS_COMPUTED_NAME:
				return isHasComputedName();
			case TypesPackage.TMEMBER__CONSTITUENT_MEMBERS:
				return getConstituentMembers();
			case TypesPackage.TMEMBER__COMPOSED:
				return isComposed();
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
			case TypesPackage.TMEMBER__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends TAnnotation>)newValue);
				return;
			case TypesPackage.TMEMBER__AST_ELEMENT:
				setAstElement((EObject)newValue);
				return;
			case TypesPackage.TMEMBER__DECLARED_FINAL:
				setDeclaredFinal((Boolean)newValue);
				return;
			case TypesPackage.TMEMBER__DECLARED_STATIC:
				setDeclaredStatic((Boolean)newValue);
				return;
			case TypesPackage.TMEMBER__DECLARED_OVERRIDE:
				setDeclaredOverride((Boolean)newValue);
				return;
			case TypesPackage.TMEMBER__HAS_COMPUTED_NAME:
				setHasComputedName((Boolean)newValue);
				return;
			case TypesPackage.TMEMBER__CONSTITUENT_MEMBERS:
				getConstituentMembers().clear();
				getConstituentMembers().addAll((Collection<? extends TMember>)newValue);
				return;
			case TypesPackage.TMEMBER__COMPOSED:
				setComposed((Boolean)newValue);
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
			case TypesPackage.TMEMBER__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case TypesPackage.TMEMBER__AST_ELEMENT:
				setAstElement((EObject)null);
				return;
			case TypesPackage.TMEMBER__DECLARED_FINAL:
				setDeclaredFinal(DECLARED_FINAL_EDEFAULT);
				return;
			case TypesPackage.TMEMBER__DECLARED_STATIC:
				setDeclaredStatic(DECLARED_STATIC_EDEFAULT);
				return;
			case TypesPackage.TMEMBER__DECLARED_OVERRIDE:
				setDeclaredOverride(DECLARED_OVERRIDE_EDEFAULT);
				return;
			case TypesPackage.TMEMBER__HAS_COMPUTED_NAME:
				setHasComputedName(HAS_COMPUTED_NAME_EDEFAULT);
				return;
			case TypesPackage.TMEMBER__CONSTITUENT_MEMBERS:
				getConstituentMembers().clear();
				return;
			case TypesPackage.TMEMBER__COMPOSED:
				setComposed(COMPOSED_EDEFAULT);
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
			case TypesPackage.TMEMBER__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case TypesPackage.TMEMBER__AST_ELEMENT:
				return astElement != null;
			case TypesPackage.TMEMBER__DECLARED_FINAL:
				return declaredFinal != DECLARED_FINAL_EDEFAULT;
			case TypesPackage.TMEMBER__DECLARED_STATIC:
				return declaredStatic != DECLARED_STATIC_EDEFAULT;
			case TypesPackage.TMEMBER__DECLARED_OVERRIDE:
				return declaredOverride != DECLARED_OVERRIDE_EDEFAULT;
			case TypesPackage.TMEMBER__HAS_COMPUTED_NAME:
				return hasComputedName != HAS_COMPUTED_NAME_EDEFAULT;
			case TypesPackage.TMEMBER__CONSTITUENT_MEMBERS:
				return constituentMembers != null && !constituentMembers.isEmpty();
			case TypesPackage.TMEMBER__COMPOSED:
				return composed != COMPOSED_EDEFAULT;
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
		if (baseClass == TAnnotableElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TMEMBER__ANNOTATIONS: return TypesPackage.TANNOTABLE_ELEMENT__ANNOTATIONS;
				default: return -1;
			}
		}
		if (baseClass == SyntaxRelatedTElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TMEMBER__AST_ELEMENT: return TypesPackage.SYNTAX_RELATED_TELEMENT__AST_ELEMENT;
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
		if (baseClass == TAnnotableElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.TANNOTABLE_ELEMENT__ANNOTATIONS: return TypesPackage.TMEMBER__ANNOTATIONS;
				default: return -1;
			}
		}
		if (baseClass == SyntaxRelatedTElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.SYNTAX_RELATED_TELEMENT__AST_ELEMENT: return TypesPackage.TMEMBER__AST_ELEMENT;
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
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case TypesPackage.TMEMBER___GET_CONTAINING_TYPE:
				return getContainingType();
			case TypesPackage.TMEMBER___GET_MEMBER_ACCESS_MODIFIER:
				return getMemberAccessModifier();
			case TypesPackage.TMEMBER___GET_MEMBER_TYPE:
				return getMemberType();
			case TypesPackage.TMEMBER___IS_FIELD:
				return isField();
			case TypesPackage.TMEMBER___IS_GETTER:
				return isGetter();
			case TypesPackage.TMEMBER___IS_SETTER:
				return isSetter();
			case TypesPackage.TMEMBER___IS_ACCESSOR:
				return isAccessor();
			case TypesPackage.TMEMBER___IS_METHOD:
				return isMethod();
			case TypesPackage.TMEMBER___IS_CONSTRUCTOR:
				return isConstructor();
			case TypesPackage.TMEMBER___IS_OPTIONAL:
				return isOptional();
			case TypesPackage.TMEMBER___IS_ABSTRACT:
				return isAbstract();
			case TypesPackage.TMEMBER___IS_READABLE:
				return isReadable();
			case TypesPackage.TMEMBER___IS_WRITEABLE:
				return isWriteable();
			case TypesPackage.TMEMBER___GET_MEMBER_AS_STRING:
				return getMemberAsString();
			case TypesPackage.TMEMBER___IS_FINAL:
				return isFinal();
			case TypesPackage.TMEMBER___IS_STATIC:
				return isStatic();
			case TypesPackage.TMEMBER___IS_CONST:
				return isConst();
			case TypesPackage.TMEMBER___IS_POLYFILLED:
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
		result.append(')');
		return result.toString();
	}

} //TMemberImpl
