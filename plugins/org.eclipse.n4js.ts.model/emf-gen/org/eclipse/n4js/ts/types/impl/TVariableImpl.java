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
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.AccessibleTypeElement;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TAnnotableElement;
import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.ts.types.TConstableElement;
import org.eclipse.n4js.ts.types.TTypedElement;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.TypeAccessModifier;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TVariable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TVariableImpl#isConst <em>Const</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TVariableImpl#getCompileTimeValue <em>Compile Time Value</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TVariableImpl#getAstElement <em>Ast Element</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TVariableImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TVariableImpl#getDeclaredTypeAccessModifier <em>Declared Type Access Modifier</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TVariableImpl#isDeclaredProvidedByRuntime <em>Declared Provided By Runtime</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TVariableImpl#getTypeRef <em>Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TVariableImpl#isExternal <em>External</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TVariableImpl#isObjectLiteral <em>Object Literal</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TVariableImpl#isNewExpression <em>New Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TVariableImpl extends TExportableElementImpl implements TVariable {
	/**
	 * The default value of the '{@link #isConst() <em>Const</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConst()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONST_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isConst() <em>Const</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConst()
	 * @generated
	 * @ordered
	 */
	protected boolean const_ = CONST_EDEFAULT;

	/**
	 * The default value of the '{@link #getCompileTimeValue() <em>Compile Time Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompileTimeValue()
	 * @generated
	 * @ordered
	 */
	protected static final String COMPILE_TIME_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCompileTimeValue() <em>Compile Time Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompileTimeValue()
	 * @generated
	 * @ordered
	 */
	protected String compileTimeValue = COMPILE_TIME_VALUE_EDEFAULT;

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
	 * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotations()
	 * @generated
	 * @ordered
	 */
	protected EList<TAnnotation> annotations;

	/**
	 * The default value of the '{@link #getDeclaredTypeAccessModifier() <em>Declared Type Access Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredTypeAccessModifier()
	 * @generated
	 * @ordered
	 */
	protected static final TypeAccessModifier DECLARED_TYPE_ACCESS_MODIFIER_EDEFAULT = TypeAccessModifier.UNDEFINED;

	/**
	 * The cached value of the '{@link #getDeclaredTypeAccessModifier() <em>Declared Type Access Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredTypeAccessModifier()
	 * @generated
	 * @ordered
	 */
	protected TypeAccessModifier declaredTypeAccessModifier = DECLARED_TYPE_ACCESS_MODIFIER_EDEFAULT;

	/**
	 * The default value of the '{@link #isDeclaredProvidedByRuntime() <em>Declared Provided By Runtime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredProvidedByRuntime()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_PROVIDED_BY_RUNTIME_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredProvidedByRuntime() <em>Declared Provided By Runtime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredProvidedByRuntime()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredProvidedByRuntime = DECLARED_PROVIDED_BY_RUNTIME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTypeRef() <em>Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef typeRef;

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
	 * The default value of the '{@link #isObjectLiteral() <em>Object Literal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isObjectLiteral()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OBJECT_LITERAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isObjectLiteral() <em>Object Literal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isObjectLiteral()
	 * @generated
	 * @ordered
	 */
	protected boolean objectLiteral = OBJECT_LITERAL_EDEFAULT;

	/**
	 * The default value of the '{@link #isNewExpression() <em>New Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNewExpression()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NEW_EXPRESSION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNewExpression() <em>New Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNewExpression()
	 * @generated
	 * @ordered
	 */
	protected boolean newExpression = NEW_EXPRESSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TVARIABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isConst() {
		return const_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConst(boolean newConst) {
		boolean oldConst = const_;
		const_ = newConst;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TVARIABLE__CONST, oldConst, const_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCompileTimeValue() {
		return compileTimeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCompileTimeValue(String newCompileTimeValue) {
		String oldCompileTimeValue = compileTimeValue;
		compileTimeValue = newCompileTimeValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TVARIABLE__COMPILE_TIME_VALUE, oldCompileTimeValue, compileTimeValue));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.TVARIABLE__AST_ELEMENT, oldAstElement, astElement));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TVARIABLE__AST_ELEMENT, oldAstElement, astElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TAnnotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<TAnnotation>(TAnnotation.class, this, TypesPackage.TVARIABLE__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeAccessModifier getDeclaredTypeAccessModifier() {
		return declaredTypeAccessModifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredTypeAccessModifier(TypeAccessModifier newDeclaredTypeAccessModifier) {
		TypeAccessModifier oldDeclaredTypeAccessModifier = declaredTypeAccessModifier;
		declaredTypeAccessModifier = newDeclaredTypeAccessModifier == null ? DECLARED_TYPE_ACCESS_MODIFIER_EDEFAULT : newDeclaredTypeAccessModifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TVARIABLE__DECLARED_TYPE_ACCESS_MODIFIER, oldDeclaredTypeAccessModifier, declaredTypeAccessModifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredProvidedByRuntime() {
		return declaredProvidedByRuntime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredProvidedByRuntime(boolean newDeclaredProvidedByRuntime) {
		boolean oldDeclaredProvidedByRuntime = declaredProvidedByRuntime;
		declaredProvidedByRuntime = newDeclaredProvidedByRuntime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TVARIABLE__DECLARED_PROVIDED_BY_RUNTIME, oldDeclaredProvidedByRuntime, declaredProvidedByRuntime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getTypeRef() {
		return typeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTypeRef(TypeRef newTypeRef, NotificationChain msgs) {
		TypeRef oldTypeRef = typeRef;
		typeRef = newTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.TVARIABLE__TYPE_REF, oldTypeRef, newTypeRef);
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
	public void setTypeRef(TypeRef newTypeRef) {
		if (newTypeRef != typeRef) {
			NotificationChain msgs = null;
			if (typeRef != null)
				msgs = ((InternalEObject)typeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TVARIABLE__TYPE_REF, null, msgs);
			if (newTypeRef != null)
				msgs = ((InternalEObject)newTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TVARIABLE__TYPE_REF, null, msgs);
			msgs = basicSetTypeRef(newTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TVARIABLE__TYPE_REF, newTypeRef, newTypeRef));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TVARIABLE__EXTERNAL, oldExternal, external));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isObjectLiteral() {
		return objectLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setObjectLiteral(boolean newObjectLiteral) {
		boolean oldObjectLiteral = objectLiteral;
		objectLiteral = newObjectLiteral;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TVARIABLE__OBJECT_LITERAL, oldObjectLiteral, objectLiteral));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isNewExpression() {
		return newExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNewExpression(boolean newNewExpression) {
		boolean oldNewExpression = newExpression;
		newExpression = newNewExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TVARIABLE__NEW_EXPRESSION, oldNewExpression, newExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getVariableAsString() {
		final StringBuilder strb = new StringBuilder();
		boolean _isConst = this.isConst();
		if (_isConst) {
			strb.append("const ");
		}
		else {
			strb.append("var ");
		}
		strb.append(this.getName());
		TypeRef _typeRef = this.getTypeRef();
		boolean _tripleNotEquals = (_typeRef != null);
		if (_tripleNotEquals) {
			strb.append(": ").append(this.getTypeRef().getTypeRefAsString());
		}
		return strb.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isProvidedByRuntime() {
		return this.isDeclaredProvidedByRuntime();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeAccessModifier getTypeAccessModifier() {
		TypeAccessModifier _declaredTypeAccessModifier = this.getDeclaredTypeAccessModifier();
		boolean _equals = Objects.equal(_declaredTypeAccessModifier, TypeAccessModifier.UNDEFINED);
		if (_equals) {
			boolean _isExported = this.isExported();
			if (_isExported) {
				return TypeAccessModifier.PROJECT;
			}
			else {
				return TypeAccessModifier.PRIVATE;
			}
		}
		return this.getDeclaredTypeAccessModifier();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.TVARIABLE__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case TypesPackage.TVARIABLE__TYPE_REF:
				return basicSetTypeRef(null, msgs);
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
			case TypesPackage.TVARIABLE__CONST:
				return isConst();
			case TypesPackage.TVARIABLE__COMPILE_TIME_VALUE:
				return getCompileTimeValue();
			case TypesPackage.TVARIABLE__AST_ELEMENT:
				if (resolve) return getAstElement();
				return basicGetAstElement();
			case TypesPackage.TVARIABLE__ANNOTATIONS:
				return getAnnotations();
			case TypesPackage.TVARIABLE__DECLARED_TYPE_ACCESS_MODIFIER:
				return getDeclaredTypeAccessModifier();
			case TypesPackage.TVARIABLE__DECLARED_PROVIDED_BY_RUNTIME:
				return isDeclaredProvidedByRuntime();
			case TypesPackage.TVARIABLE__TYPE_REF:
				return getTypeRef();
			case TypesPackage.TVARIABLE__EXTERNAL:
				return isExternal();
			case TypesPackage.TVARIABLE__OBJECT_LITERAL:
				return isObjectLiteral();
			case TypesPackage.TVARIABLE__NEW_EXPRESSION:
				return isNewExpression();
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
			case TypesPackage.TVARIABLE__CONST:
				setConst((Boolean)newValue);
				return;
			case TypesPackage.TVARIABLE__COMPILE_TIME_VALUE:
				setCompileTimeValue((String)newValue);
				return;
			case TypesPackage.TVARIABLE__AST_ELEMENT:
				setAstElement((EObject)newValue);
				return;
			case TypesPackage.TVARIABLE__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends TAnnotation>)newValue);
				return;
			case TypesPackage.TVARIABLE__DECLARED_TYPE_ACCESS_MODIFIER:
				setDeclaredTypeAccessModifier((TypeAccessModifier)newValue);
				return;
			case TypesPackage.TVARIABLE__DECLARED_PROVIDED_BY_RUNTIME:
				setDeclaredProvidedByRuntime((Boolean)newValue);
				return;
			case TypesPackage.TVARIABLE__TYPE_REF:
				setTypeRef((TypeRef)newValue);
				return;
			case TypesPackage.TVARIABLE__EXTERNAL:
				setExternal((Boolean)newValue);
				return;
			case TypesPackage.TVARIABLE__OBJECT_LITERAL:
				setObjectLiteral((Boolean)newValue);
				return;
			case TypesPackage.TVARIABLE__NEW_EXPRESSION:
				setNewExpression((Boolean)newValue);
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
			case TypesPackage.TVARIABLE__CONST:
				setConst(CONST_EDEFAULT);
				return;
			case TypesPackage.TVARIABLE__COMPILE_TIME_VALUE:
				setCompileTimeValue(COMPILE_TIME_VALUE_EDEFAULT);
				return;
			case TypesPackage.TVARIABLE__AST_ELEMENT:
				setAstElement((EObject)null);
				return;
			case TypesPackage.TVARIABLE__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case TypesPackage.TVARIABLE__DECLARED_TYPE_ACCESS_MODIFIER:
				setDeclaredTypeAccessModifier(DECLARED_TYPE_ACCESS_MODIFIER_EDEFAULT);
				return;
			case TypesPackage.TVARIABLE__DECLARED_PROVIDED_BY_RUNTIME:
				setDeclaredProvidedByRuntime(DECLARED_PROVIDED_BY_RUNTIME_EDEFAULT);
				return;
			case TypesPackage.TVARIABLE__TYPE_REF:
				setTypeRef((TypeRef)null);
				return;
			case TypesPackage.TVARIABLE__EXTERNAL:
				setExternal(EXTERNAL_EDEFAULT);
				return;
			case TypesPackage.TVARIABLE__OBJECT_LITERAL:
				setObjectLiteral(OBJECT_LITERAL_EDEFAULT);
				return;
			case TypesPackage.TVARIABLE__NEW_EXPRESSION:
				setNewExpression(NEW_EXPRESSION_EDEFAULT);
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
			case TypesPackage.TVARIABLE__CONST:
				return const_ != CONST_EDEFAULT;
			case TypesPackage.TVARIABLE__COMPILE_TIME_VALUE:
				return COMPILE_TIME_VALUE_EDEFAULT == null ? compileTimeValue != null : !COMPILE_TIME_VALUE_EDEFAULT.equals(compileTimeValue);
			case TypesPackage.TVARIABLE__AST_ELEMENT:
				return astElement != null;
			case TypesPackage.TVARIABLE__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case TypesPackage.TVARIABLE__DECLARED_TYPE_ACCESS_MODIFIER:
				return declaredTypeAccessModifier != DECLARED_TYPE_ACCESS_MODIFIER_EDEFAULT;
			case TypesPackage.TVARIABLE__DECLARED_PROVIDED_BY_RUNTIME:
				return declaredProvidedByRuntime != DECLARED_PROVIDED_BY_RUNTIME_EDEFAULT;
			case TypesPackage.TVARIABLE__TYPE_REF:
				return typeRef != null;
			case TypesPackage.TVARIABLE__EXTERNAL:
				return external != EXTERNAL_EDEFAULT;
			case TypesPackage.TVARIABLE__OBJECT_LITERAL:
				return objectLiteral != OBJECT_LITERAL_EDEFAULT;
			case TypesPackage.TVARIABLE__NEW_EXPRESSION:
				return newExpression != NEW_EXPRESSION_EDEFAULT;
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
		if (baseClass == TConstableElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TVARIABLE__CONST: return TypesPackage.TCONSTABLE_ELEMENT__CONST;
				case TypesPackage.TVARIABLE__COMPILE_TIME_VALUE: return TypesPackage.TCONSTABLE_ELEMENT__COMPILE_TIME_VALUE;
				default: return -1;
			}
		}
		if (baseClass == SyntaxRelatedTElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TVARIABLE__AST_ELEMENT: return TypesPackage.SYNTAX_RELATED_TELEMENT__AST_ELEMENT;
				default: return -1;
			}
		}
		if (baseClass == TAnnotableElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TVARIABLE__ANNOTATIONS: return TypesPackage.TANNOTABLE_ELEMENT__ANNOTATIONS;
				default: return -1;
			}
		}
		if (baseClass == AccessibleTypeElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TVARIABLE__DECLARED_TYPE_ACCESS_MODIFIER: return TypesPackage.ACCESSIBLE_TYPE_ELEMENT__DECLARED_TYPE_ACCESS_MODIFIER;
				case TypesPackage.TVARIABLE__DECLARED_PROVIDED_BY_RUNTIME: return TypesPackage.ACCESSIBLE_TYPE_ELEMENT__DECLARED_PROVIDED_BY_RUNTIME;
				default: return -1;
			}
		}
		if (baseClass == TTypedElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TVARIABLE__TYPE_REF: return TypesPackage.TTYPED_ELEMENT__TYPE_REF;
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
		if (baseClass == TConstableElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.TCONSTABLE_ELEMENT__CONST: return TypesPackage.TVARIABLE__CONST;
				case TypesPackage.TCONSTABLE_ELEMENT__COMPILE_TIME_VALUE: return TypesPackage.TVARIABLE__COMPILE_TIME_VALUE;
				default: return -1;
			}
		}
		if (baseClass == SyntaxRelatedTElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.SYNTAX_RELATED_TELEMENT__AST_ELEMENT: return TypesPackage.TVARIABLE__AST_ELEMENT;
				default: return -1;
			}
		}
		if (baseClass == TAnnotableElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.TANNOTABLE_ELEMENT__ANNOTATIONS: return TypesPackage.TVARIABLE__ANNOTATIONS;
				default: return -1;
			}
		}
		if (baseClass == AccessibleTypeElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT__DECLARED_TYPE_ACCESS_MODIFIER: return TypesPackage.TVARIABLE__DECLARED_TYPE_ACCESS_MODIFIER;
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT__DECLARED_PROVIDED_BY_RUNTIME: return TypesPackage.TVARIABLE__DECLARED_PROVIDED_BY_RUNTIME;
				default: return -1;
			}
		}
		if (baseClass == TTypedElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.TTYPED_ELEMENT__TYPE_REF: return TypesPackage.TVARIABLE__TYPE_REF;
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
		if (baseClass == TConstableElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == SyntaxRelatedTElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == TAnnotableElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == AccessibleTypeElement.class) {
			switch (baseOperationID) {
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___IS_PROVIDED_BY_RUNTIME: return TypesPackage.TVARIABLE___IS_PROVIDED_BY_RUNTIME;
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___GET_TYPE_ACCESS_MODIFIER: return TypesPackage.TVARIABLE___GET_TYPE_ACCESS_MODIFIER;
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___IS_EXPORTED: return TypesPackage.TVARIABLE___IS_EXPORTED;
				default: return -1;
			}
		}
		if (baseClass == TTypedElement.class) {
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
			case TypesPackage.TVARIABLE___GET_VARIABLE_AS_STRING:
				return getVariableAsString();
			case TypesPackage.TVARIABLE___IS_PROVIDED_BY_RUNTIME:
				return isProvidedByRuntime();
			case TypesPackage.TVARIABLE___GET_TYPE_ACCESS_MODIFIER:
				return getTypeAccessModifier();
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
		result.append(" (const: ");
		result.append(const_);
		result.append(", compileTimeValue: ");
		result.append(compileTimeValue);
		result.append(", declaredTypeAccessModifier: ");
		result.append(declaredTypeAccessModifier);
		result.append(", declaredProvidedByRuntime: ");
		result.append(declaredProvidedByRuntime);
		result.append(", external: ");
		result.append(external);
		result.append(", objectLiteral: ");
		result.append(objectLiteral);
		result.append(", newExpression: ");
		result.append(newExpression);
		result.append(')');
		return result.toString();
	}

} //TVariableImpl
