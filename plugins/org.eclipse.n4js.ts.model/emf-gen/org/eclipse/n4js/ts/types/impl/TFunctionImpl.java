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
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;

import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.Versionable;

import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TVersionable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TFunction</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TFunctionImpl#getAstElement <em>Ast Element</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TFunctionImpl#getDeclaredVersion <em>Declared Version</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TFunctionImpl#isExternal <em>External</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TFunctionImpl#getFpars <em>Fpars</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TFunctionImpl#isReturnValueMarkedOptional <em>Return Value Marked Optional</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TFunctionImpl#getReturnTypeRef <em>Return Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TFunctionImpl#getTypeVars <em>Type Vars</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TFunctionImpl#getDeclaredThisType <em>Declared This Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TFunctionImpl#isDeclaredAsync <em>Declared Async</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TFunctionImpl#isDeclaredGenerator <em>Declared Generator</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TFunctionImpl#isConstructor <em>Constructor</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TFunctionImpl extends DeclaredTypeWithAccessModifierImpl implements TFunction {
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
	 * The default value of the '{@link #getDeclaredVersion() <em>Declared Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredVersion()
	 * @generated
	 * @ordered
	 */
	protected static final int DECLARED_VERSION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDeclaredVersion() <em>Declared Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredVersion()
	 * @generated
	 * @ordered
	 */
	protected int declaredVersion = DECLARED_VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #isExternal() <em>External</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExternal()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXTERNAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExternal() <em>External</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExternal()
	 * @generated
	 * @ordered
	 */
	protected boolean external = EXTERNAL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFpars() <em>Fpars</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFpars()
	 * @generated
	 * @ordered
	 */
	protected EList<TFormalParameter> fpars;

	/**
	 * The default value of the '{@link #isReturnValueMarkedOptional() <em>Return Value Marked Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReturnValueMarkedOptional()
	 * @generated
	 * @ordered
	 */
	protected static final boolean RETURN_VALUE_MARKED_OPTIONAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReturnValueMarkedOptional() <em>Return Value Marked Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReturnValueMarkedOptional()
	 * @generated
	 * @ordered
	 */
	protected boolean returnValueMarkedOptional = RETURN_VALUE_MARKED_OPTIONAL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getReturnTypeRef() <em>Return Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef returnTypeRef;

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
	 * The cached value of the '{@link #getDeclaredThisType() <em>Declared This Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredThisType()
	 * @generated
	 * @ordered
	 */
	protected TypeRef declaredThisType;

	/**
	 * The default value of the '{@link #isDeclaredAsync() <em>Declared Async</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredAsync()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_ASYNC_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredAsync() <em>Declared Async</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredAsync()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredAsync = DECLARED_ASYNC_EDEFAULT;

	/**
	 * The default value of the '{@link #isDeclaredGenerator() <em>Declared Generator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredGenerator()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_GENERATOR_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredGenerator() <em>Declared Generator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredGenerator()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredGenerator = DECLARED_GENERATOR_EDEFAULT;

	/**
	 * The default value of the '{@link #isConstructor() <em>Constructor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConstructor()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONSTRUCTOR_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isConstructor() <em>Constructor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConstructor()
	 * @generated
	 * @ordered
	 */
	protected boolean constructor = CONSTRUCTOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TFunctionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TFUNCTION;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.TFUNCTION__AST_ELEMENT, oldAstElement, astElement));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TFUNCTION__AST_ELEMENT, oldAstElement, astElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getDeclaredVersion() {
		return declaredVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredVersion(int newDeclaredVersion) {
		int oldDeclaredVersion = declaredVersion;
		declaredVersion = newDeclaredVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TFUNCTION__DECLARED_VERSION, oldDeclaredVersion, declaredVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isExternal() {
		return external;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExternal(boolean newExternal) {
		boolean oldExternal = external;
		external = newExternal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TFUNCTION__EXTERNAL, oldExternal, external));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TFormalParameter> getFpars() {
		if (fpars == null) {
			fpars = new EObjectContainmentEList<TFormalParameter>(TFormalParameter.class, this, TypesPackage.TFUNCTION__FPARS);
		}
		return fpars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isReturnValueMarkedOptional() {
		return returnValueMarkedOptional;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReturnValueMarkedOptional(boolean newReturnValueMarkedOptional) {
		boolean oldReturnValueMarkedOptional = returnValueMarkedOptional;
		returnValueMarkedOptional = newReturnValueMarkedOptional;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TFUNCTION__RETURN_VALUE_MARKED_OPTIONAL, oldReturnValueMarkedOptional, returnValueMarkedOptional));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getReturnTypeRef() {
		return returnTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReturnTypeRef(TypeRef newReturnTypeRef, NotificationChain msgs) {
		TypeRef oldReturnTypeRef = returnTypeRef;
		returnTypeRef = newReturnTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.TFUNCTION__RETURN_TYPE_REF, oldReturnTypeRef, newReturnTypeRef);
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
	public void setReturnTypeRef(TypeRef newReturnTypeRef) {
		if (newReturnTypeRef != returnTypeRef) {
			NotificationChain msgs = null;
			if (returnTypeRef != null)
				msgs = ((InternalEObject)returnTypeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TFUNCTION__RETURN_TYPE_REF, null, msgs);
			if (newReturnTypeRef != null)
				msgs = ((InternalEObject)newReturnTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TFUNCTION__RETURN_TYPE_REF, null, msgs);
			msgs = basicSetReturnTypeRef(newReturnTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TFUNCTION__RETURN_TYPE_REF, newReturnTypeRef, newReturnTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypeVariable> getTypeVars() {
		if (typeVars == null) {
			typeVars = new EObjectContainmentEList<TypeVariable>(TypeVariable.class, this, TypesPackage.TFUNCTION__TYPE_VARS);
		}
		return typeVars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredThisType() {
		return declaredThisType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredThisType(TypeRef newDeclaredThisType, NotificationChain msgs) {
		TypeRef oldDeclaredThisType = declaredThisType;
		declaredThisType = newDeclaredThisType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.TFUNCTION__DECLARED_THIS_TYPE, oldDeclaredThisType, newDeclaredThisType);
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
	public void setDeclaredThisType(TypeRef newDeclaredThisType) {
		if (newDeclaredThisType != declaredThisType) {
			NotificationChain msgs = null;
			if (declaredThisType != null)
				msgs = ((InternalEObject)declaredThisType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TFUNCTION__DECLARED_THIS_TYPE, null, msgs);
			if (newDeclaredThisType != null)
				msgs = ((InternalEObject)newDeclaredThisType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TFUNCTION__DECLARED_THIS_TYPE, null, msgs);
			msgs = basicSetDeclaredThisType(newDeclaredThisType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TFUNCTION__DECLARED_THIS_TYPE, newDeclaredThisType, newDeclaredThisType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredAsync() {
		return declaredAsync;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredAsync(boolean newDeclaredAsync) {
		boolean oldDeclaredAsync = declaredAsync;
		declaredAsync = newDeclaredAsync;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TFUNCTION__DECLARED_ASYNC, oldDeclaredAsync, declaredAsync));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredGenerator() {
		return declaredGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredGenerator(boolean newDeclaredGenerator) {
		boolean oldDeclaredGenerator = declaredGenerator;
		declaredGenerator = newDeclaredGenerator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TFUNCTION__DECLARED_GENERATOR, oldDeclaredGenerator, declaredGenerator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isConstructor() {
		return constructor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConstructor(boolean newConstructor) {
		boolean oldConstructor = constructor;
		constructor = newConstructor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TFUNCTION__CONSTRUCTOR, oldConstructor, constructor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isReturnValueOptional() {
		return (this.isReturnValueMarkedOptional() || ((this.getReturnTypeRef() != null) && this.getReturnTypeRef().isFollowedByQuestionMark()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isCallableConstructor() {
		final EObject parent = this.eContainer();
		boolean _xifexpression = false;
		if ((parent instanceof ContainerType<?>)) {
			TMethod _callableCtor = ((ContainerType<?>)parent).getCallableCtor();
			_xifexpression = (_callableCtor == this);
		}
		else {
			_xifexpression = false;
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TFormalParameter getFparForArgIdx(final int argIndex) {
		final int fparsSize = this.getFpars().size();
		if (((argIndex >= 0) && (argIndex < fparsSize))) {
			return this.getFpars().get(argIndex);
		}
		else {
			if ((((argIndex >= fparsSize) && (fparsSize > 0)) && this.getFpars().get((fparsSize - 1)).isVariadic())) {
				return this.getFpars().get((fparsSize - 1));
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
		strb.append("function ");
		boolean _isDeclaredGenerator = this.isDeclaredGenerator();
		if (_isDeclaredGenerator) {
			strb.append("* ");
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
	@Override
	public boolean isFinal() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getVersion() {
		return this.getDeclaredVersion();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.TFUNCTION__FPARS:
				return ((InternalEList<?>)getFpars()).basicRemove(otherEnd, msgs);
			case TypesPackage.TFUNCTION__RETURN_TYPE_REF:
				return basicSetReturnTypeRef(null, msgs);
			case TypesPackage.TFUNCTION__TYPE_VARS:
				return ((InternalEList<?>)getTypeVars()).basicRemove(otherEnd, msgs);
			case TypesPackage.TFUNCTION__DECLARED_THIS_TYPE:
				return basicSetDeclaredThisType(null, msgs);
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
			case TypesPackage.TFUNCTION__AST_ELEMENT:
				if (resolve) return getAstElement();
				return basicGetAstElement();
			case TypesPackage.TFUNCTION__DECLARED_VERSION:
				return getDeclaredVersion();
			case TypesPackage.TFUNCTION__EXTERNAL:
				return isExternal();
			case TypesPackage.TFUNCTION__FPARS:
				return getFpars();
			case TypesPackage.TFUNCTION__RETURN_VALUE_MARKED_OPTIONAL:
				return isReturnValueMarkedOptional();
			case TypesPackage.TFUNCTION__RETURN_TYPE_REF:
				return getReturnTypeRef();
			case TypesPackage.TFUNCTION__TYPE_VARS:
				return getTypeVars();
			case TypesPackage.TFUNCTION__DECLARED_THIS_TYPE:
				return getDeclaredThisType();
			case TypesPackage.TFUNCTION__DECLARED_ASYNC:
				return isDeclaredAsync();
			case TypesPackage.TFUNCTION__DECLARED_GENERATOR:
				return isDeclaredGenerator();
			case TypesPackage.TFUNCTION__CONSTRUCTOR:
				return isConstructor();
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
			case TypesPackage.TFUNCTION__AST_ELEMENT:
				setAstElement((EObject)newValue);
				return;
			case TypesPackage.TFUNCTION__DECLARED_VERSION:
				setDeclaredVersion((Integer)newValue);
				return;
			case TypesPackage.TFUNCTION__EXTERNAL:
				setExternal((Boolean)newValue);
				return;
			case TypesPackage.TFUNCTION__FPARS:
				getFpars().clear();
				getFpars().addAll((Collection<? extends TFormalParameter>)newValue);
				return;
			case TypesPackage.TFUNCTION__RETURN_VALUE_MARKED_OPTIONAL:
				setReturnValueMarkedOptional((Boolean)newValue);
				return;
			case TypesPackage.TFUNCTION__RETURN_TYPE_REF:
				setReturnTypeRef((TypeRef)newValue);
				return;
			case TypesPackage.TFUNCTION__TYPE_VARS:
				getTypeVars().clear();
				getTypeVars().addAll((Collection<? extends TypeVariable>)newValue);
				return;
			case TypesPackage.TFUNCTION__DECLARED_THIS_TYPE:
				setDeclaredThisType((TypeRef)newValue);
				return;
			case TypesPackage.TFUNCTION__DECLARED_ASYNC:
				setDeclaredAsync((Boolean)newValue);
				return;
			case TypesPackage.TFUNCTION__DECLARED_GENERATOR:
				setDeclaredGenerator((Boolean)newValue);
				return;
			case TypesPackage.TFUNCTION__CONSTRUCTOR:
				setConstructor((Boolean)newValue);
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
			case TypesPackage.TFUNCTION__AST_ELEMENT:
				setAstElement((EObject)null);
				return;
			case TypesPackage.TFUNCTION__DECLARED_VERSION:
				setDeclaredVersion(DECLARED_VERSION_EDEFAULT);
				return;
			case TypesPackage.TFUNCTION__EXTERNAL:
				setExternal(EXTERNAL_EDEFAULT);
				return;
			case TypesPackage.TFUNCTION__FPARS:
				getFpars().clear();
				return;
			case TypesPackage.TFUNCTION__RETURN_VALUE_MARKED_OPTIONAL:
				setReturnValueMarkedOptional(RETURN_VALUE_MARKED_OPTIONAL_EDEFAULT);
				return;
			case TypesPackage.TFUNCTION__RETURN_TYPE_REF:
				setReturnTypeRef((TypeRef)null);
				return;
			case TypesPackage.TFUNCTION__TYPE_VARS:
				getTypeVars().clear();
				return;
			case TypesPackage.TFUNCTION__DECLARED_THIS_TYPE:
				setDeclaredThisType((TypeRef)null);
				return;
			case TypesPackage.TFUNCTION__DECLARED_ASYNC:
				setDeclaredAsync(DECLARED_ASYNC_EDEFAULT);
				return;
			case TypesPackage.TFUNCTION__DECLARED_GENERATOR:
				setDeclaredGenerator(DECLARED_GENERATOR_EDEFAULT);
				return;
			case TypesPackage.TFUNCTION__CONSTRUCTOR:
				setConstructor(CONSTRUCTOR_EDEFAULT);
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
			case TypesPackage.TFUNCTION__AST_ELEMENT:
				return astElement != null;
			case TypesPackage.TFUNCTION__DECLARED_VERSION:
				return declaredVersion != DECLARED_VERSION_EDEFAULT;
			case TypesPackage.TFUNCTION__EXTERNAL:
				return external != EXTERNAL_EDEFAULT;
			case TypesPackage.TFUNCTION__FPARS:
				return fpars != null && !fpars.isEmpty();
			case TypesPackage.TFUNCTION__RETURN_VALUE_MARKED_OPTIONAL:
				return returnValueMarkedOptional != RETURN_VALUE_MARKED_OPTIONAL_EDEFAULT;
			case TypesPackage.TFUNCTION__RETURN_TYPE_REF:
				return returnTypeRef != null;
			case TypesPackage.TFUNCTION__TYPE_VARS:
				return typeVars != null && !typeVars.isEmpty();
			case TypesPackage.TFUNCTION__DECLARED_THIS_TYPE:
				return declaredThisType != null;
			case TypesPackage.TFUNCTION__DECLARED_ASYNC:
				return declaredAsync != DECLARED_ASYNC_EDEFAULT;
			case TypesPackage.TFUNCTION__DECLARED_GENERATOR:
				return declaredGenerator != DECLARED_GENERATOR_EDEFAULT;
			case TypesPackage.TFUNCTION__CONSTRUCTOR:
				return constructor != CONSTRUCTOR_EDEFAULT;
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
		if (baseClass == SyntaxRelatedTElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TFUNCTION__AST_ELEMENT: return TypesPackage.SYNTAX_RELATED_TELEMENT__AST_ELEMENT;
				default: return -1;
			}
		}
		if (baseClass == TVersionable.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TFUNCTION__DECLARED_VERSION: return TypesPackage.TVERSIONABLE__DECLARED_VERSION;
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
		if (baseClass == SyntaxRelatedTElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.SYNTAX_RELATED_TELEMENT__AST_ELEMENT: return TypesPackage.TFUNCTION__AST_ELEMENT;
				default: return -1;
			}
		}
		if (baseClass == TVersionable.class) {
			switch (baseFeatureID) {
				case TypesPackage.TVERSIONABLE__DECLARED_VERSION: return TypesPackage.TFUNCTION__DECLARED_VERSION;
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
		if (baseClass == Versionable.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.VERSIONABLE___GET_VERSION: return TypesPackage.TFUNCTION___GET_VERSION;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == Type.class) {
			switch (baseOperationID) {
				case TypesPackage.TYPE___IS_FINAL: return TypesPackage.TFUNCTION___IS_FINAL;
				case TypesPackage.TYPE___GET_VERSION: return TypesPackage.TFUNCTION___GET_VERSION;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == SyntaxRelatedTElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == TVersionable.class) {
			switch (baseOperationID) {
				case TypesPackage.TVERSIONABLE___GET_VERSION: return TypesPackage.TFUNCTION___GET_VERSION;
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
			case TypesPackage.TFUNCTION___IS_RETURN_VALUE_OPTIONAL:
				return isReturnValueOptional();
			case TypesPackage.TFUNCTION___IS_CALLABLE_CONSTRUCTOR:
				return isCallableConstructor();
			case TypesPackage.TFUNCTION___GET_FPAR_FOR_ARG_IDX__INT:
				return getFparForArgIdx((Integer)arguments.get(0));
			case TypesPackage.TFUNCTION___GET_FUNCTION_AS_STRING:
				return getFunctionAsString();
			case TypesPackage.TFUNCTION___IS_FINAL:
				return isFinal();
			case TypesPackage.TFUNCTION___GET_VERSION:
				return getVersion();
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
		result.append(" (declaredVersion: ");
		result.append(declaredVersion);
		result.append(", external: ");
		result.append(external);
		result.append(", returnValueMarkedOptional: ");
		result.append(returnValueMarkedOptional);
		result.append(", declaredAsync: ");
		result.append(declaredAsync);
		result.append(", declaredGenerator: ");
		result.append(declaredGenerator);
		result.append(", constructor: ");
		result.append(constructor);
		result.append(')');
		return result.toString();
	}

} //TFunctionImpl
