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

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.BindingPattern;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.TypeProvidingElement;
import org.eclipse.n4js.n4JS.TypedElement;
import org.eclipse.n4js.n4JS.Variable;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.xtext.EcoreUtil2;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Formal Parameter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FormalParameterImpl#getDeclaredTypeRef <em>Declared Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FormalParameterImpl#getDeclaredTypeRefInAST <em>Declared Type Ref In AST</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FormalParameterImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FormalParameterImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FormalParameterImpl#isVariadic <em>Variadic</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FormalParameterImpl#getDefinedTypeElement <em>Defined Type Element</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FormalParameterImpl#isHasInitializerAssignment <em>Has Initializer Assignment</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FormalParameterImpl#getInitializer <em>Initializer</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FormalParameterImpl#getBindingPattern <em>Binding Pattern</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FormalParameterImpl extends AnnotableElementImpl implements FormalParameter {
	/**
	 * The cached value of the '{@link #getDeclaredTypeRef() <em>Declared Type Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef declaredTypeRef;

	/**
	 * The cached value of the '{@link #getDeclaredTypeRefInAST() <em>Declared Type Ref In AST</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredTypeRefInAST()
	 * @generated
	 * @ordered
	 */
	protected TypeRef declaredTypeRefInAST;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotations()
	 * @generated
	 * @ordered
	 */
	protected EList<Annotation> annotations;

	/**
	 * The default value of the '{@link #isVariadic() <em>Variadic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVariadic()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VARIADIC_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isVariadic() <em>Variadic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVariadic()
	 * @generated
	 * @ordered
	 */
	protected boolean variadic = VARIADIC_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDefinedTypeElement() <em>Defined Type Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinedTypeElement()
	 * @generated
	 * @ordered
	 */
	protected TFormalParameter definedTypeElement;

	/**
	 * The default value of the '{@link #isHasInitializerAssignment() <em>Has Initializer Assignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasInitializerAssignment()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HAS_INITIALIZER_ASSIGNMENT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHasInitializerAssignment() <em>Has Initializer Assignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasInitializerAssignment()
	 * @generated
	 * @ordered
	 */
	protected boolean hasInitializerAssignment = HAS_INITIALIZER_ASSIGNMENT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInitializer() <em>Initializer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitializer()
	 * @generated
	 * @ordered
	 */
	protected Expression initializer;

	/**
	 * The cached value of the '{@link #getBindingPattern() <em>Binding Pattern</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBindingPattern()
	 * @generated
	 * @ordered
	 */
	protected BindingPattern bindingPattern;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FormalParameterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.FORMAL_PARAMETER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredTypeRef() {
		if (declaredTypeRef != null && declaredTypeRef.eIsProxy()) {
			InternalEObject oldDeclaredTypeRef = (InternalEObject)declaredTypeRef;
			declaredTypeRef = (TypeRef)eResolveProxy(oldDeclaredTypeRef);
			if (declaredTypeRef != oldDeclaredTypeRef) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.FORMAL_PARAMETER__DECLARED_TYPE_REF, oldDeclaredTypeRef, declaredTypeRef));
			}
		}
		return declaredTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeRef basicGetDeclaredTypeRef() {
		return declaredTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredTypeRef(TypeRef newDeclaredTypeRef) {
		TypeRef oldDeclaredTypeRef = declaredTypeRef;
		declaredTypeRef = newDeclaredTypeRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FORMAL_PARAMETER__DECLARED_TYPE_REF, oldDeclaredTypeRef, declaredTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredTypeRefInAST() {
		return declaredTypeRefInAST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredTypeRefInAST(TypeRef newDeclaredTypeRefInAST, NotificationChain msgs) {
		TypeRef oldDeclaredTypeRefInAST = declaredTypeRefInAST;
		declaredTypeRefInAST = newDeclaredTypeRefInAST;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.FORMAL_PARAMETER__DECLARED_TYPE_REF_IN_AST, oldDeclaredTypeRefInAST, newDeclaredTypeRefInAST);
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
	public void setDeclaredTypeRefInAST(TypeRef newDeclaredTypeRefInAST) {
		if (newDeclaredTypeRefInAST != declaredTypeRefInAST) {
			NotificationChain msgs = null;
			if (declaredTypeRefInAST != null)
				msgs = ((InternalEObject)declaredTypeRefInAST).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FORMAL_PARAMETER__DECLARED_TYPE_REF_IN_AST, null, msgs);
			if (newDeclaredTypeRefInAST != null)
				msgs = ((InternalEObject)newDeclaredTypeRefInAST).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FORMAL_PARAMETER__DECLARED_TYPE_REF_IN_AST, null, msgs);
			msgs = basicSetDeclaredTypeRefInAST(newDeclaredTypeRefInAST, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FORMAL_PARAMETER__DECLARED_TYPE_REF_IN_AST, newDeclaredTypeRefInAST, newDeclaredTypeRefInAST));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FORMAL_PARAMETER__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Annotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<Annotation>(Annotation.class, this, N4JSPackage.FORMAL_PARAMETER__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isVariadic() {
		return variadic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVariadic(boolean newVariadic) {
		boolean oldVariadic = variadic;
		variadic = newVariadic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FORMAL_PARAMETER__VARIADIC, oldVariadic, variadic));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TFormalParameter getDefinedTypeElement() {
		if (definedTypeElement != null && definedTypeElement.eIsProxy()) {
			InternalEObject oldDefinedTypeElement = (InternalEObject)definedTypeElement;
			definedTypeElement = (TFormalParameter)eResolveProxy(oldDefinedTypeElement);
			if (definedTypeElement != oldDefinedTypeElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.FORMAL_PARAMETER__DEFINED_TYPE_ELEMENT, oldDefinedTypeElement, definedTypeElement));
			}
		}
		return definedTypeElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TFormalParameter basicGetDefinedTypeElement() {
		return definedTypeElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefinedTypeElement(TFormalParameter newDefinedTypeElement) {
		TFormalParameter oldDefinedTypeElement = definedTypeElement;
		definedTypeElement = newDefinedTypeElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FORMAL_PARAMETER__DEFINED_TYPE_ELEMENT, oldDefinedTypeElement, definedTypeElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isHasInitializerAssignment() {
		return hasInitializerAssignment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHasInitializerAssignment(boolean newHasInitializerAssignment) {
		boolean oldHasInitializerAssignment = hasInitializerAssignment;
		hasInitializerAssignment = newHasInitializerAssignment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FORMAL_PARAMETER__HAS_INITIALIZER_ASSIGNMENT, oldHasInitializerAssignment, hasInitializerAssignment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Expression getInitializer() {
		return initializer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInitializer(Expression newInitializer, NotificationChain msgs) {
		Expression oldInitializer = initializer;
		initializer = newInitializer;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.FORMAL_PARAMETER__INITIALIZER, oldInitializer, newInitializer);
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
	public void setInitializer(Expression newInitializer) {
		if (newInitializer != initializer) {
			NotificationChain msgs = null;
			if (initializer != null)
				msgs = ((InternalEObject)initializer).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FORMAL_PARAMETER__INITIALIZER, null, msgs);
			if (newInitializer != null)
				msgs = ((InternalEObject)newInitializer).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FORMAL_PARAMETER__INITIALIZER, null, msgs);
			msgs = basicSetInitializer(newInitializer, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FORMAL_PARAMETER__INITIALIZER, newInitializer, newInitializer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BindingPattern getBindingPattern() {
		return bindingPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBindingPattern(BindingPattern newBindingPattern, NotificationChain msgs) {
		BindingPattern oldBindingPattern = bindingPattern;
		bindingPattern = newBindingPattern;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.FORMAL_PARAMETER__BINDING_PATTERN, oldBindingPattern, newBindingPattern);
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
	public void setBindingPattern(BindingPattern newBindingPattern) {
		if (newBindingPattern != bindingPattern) {
			NotificationChain msgs = null;
			if (bindingPattern != null)
				msgs = ((InternalEObject)bindingPattern).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FORMAL_PARAMETER__BINDING_PATTERN, null, msgs);
			if (newBindingPattern != null)
				msgs = ((InternalEObject)newBindingPattern).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FORMAL_PARAMETER__BINDING_PATTERN, null, msgs);
			msgs = basicSetBindingPattern(newBindingPattern, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FORMAL_PARAMETER__BINDING_PATTERN, newBindingPattern, newBindingPattern));
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
	public TModule getContainingModule() {
		return EcoreUtil2.<TModule>getContainerOfType(this, TModule.class);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.FORMAL_PARAMETER__DECLARED_TYPE_REF_IN_AST:
				return basicSetDeclaredTypeRefInAST(null, msgs);
			case N4JSPackage.FORMAL_PARAMETER__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case N4JSPackage.FORMAL_PARAMETER__INITIALIZER:
				return basicSetInitializer(null, msgs);
			case N4JSPackage.FORMAL_PARAMETER__BINDING_PATTERN:
				return basicSetBindingPattern(null, msgs);
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
			case N4JSPackage.FORMAL_PARAMETER__DECLARED_TYPE_REF:
				if (resolve) return getDeclaredTypeRef();
				return basicGetDeclaredTypeRef();
			case N4JSPackage.FORMAL_PARAMETER__DECLARED_TYPE_REF_IN_AST:
				return getDeclaredTypeRefInAST();
			case N4JSPackage.FORMAL_PARAMETER__NAME:
				return getName();
			case N4JSPackage.FORMAL_PARAMETER__ANNOTATIONS:
				return getAnnotations();
			case N4JSPackage.FORMAL_PARAMETER__VARIADIC:
				return isVariadic();
			case N4JSPackage.FORMAL_PARAMETER__DEFINED_TYPE_ELEMENT:
				if (resolve) return getDefinedTypeElement();
				return basicGetDefinedTypeElement();
			case N4JSPackage.FORMAL_PARAMETER__HAS_INITIALIZER_ASSIGNMENT:
				return isHasInitializerAssignment();
			case N4JSPackage.FORMAL_PARAMETER__INITIALIZER:
				return getInitializer();
			case N4JSPackage.FORMAL_PARAMETER__BINDING_PATTERN:
				return getBindingPattern();
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
			case N4JSPackage.FORMAL_PARAMETER__DECLARED_TYPE_REF:
				setDeclaredTypeRef((TypeRef)newValue);
				return;
			case N4JSPackage.FORMAL_PARAMETER__DECLARED_TYPE_REF_IN_AST:
				setDeclaredTypeRefInAST((TypeRef)newValue);
				return;
			case N4JSPackage.FORMAL_PARAMETER__NAME:
				setName((String)newValue);
				return;
			case N4JSPackage.FORMAL_PARAMETER__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case N4JSPackage.FORMAL_PARAMETER__VARIADIC:
				setVariadic((Boolean)newValue);
				return;
			case N4JSPackage.FORMAL_PARAMETER__DEFINED_TYPE_ELEMENT:
				setDefinedTypeElement((TFormalParameter)newValue);
				return;
			case N4JSPackage.FORMAL_PARAMETER__HAS_INITIALIZER_ASSIGNMENT:
				setHasInitializerAssignment((Boolean)newValue);
				return;
			case N4JSPackage.FORMAL_PARAMETER__INITIALIZER:
				setInitializer((Expression)newValue);
				return;
			case N4JSPackage.FORMAL_PARAMETER__BINDING_PATTERN:
				setBindingPattern((BindingPattern)newValue);
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
			case N4JSPackage.FORMAL_PARAMETER__DECLARED_TYPE_REF:
				setDeclaredTypeRef((TypeRef)null);
				return;
			case N4JSPackage.FORMAL_PARAMETER__DECLARED_TYPE_REF_IN_AST:
				setDeclaredTypeRefInAST((TypeRef)null);
				return;
			case N4JSPackage.FORMAL_PARAMETER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case N4JSPackage.FORMAL_PARAMETER__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case N4JSPackage.FORMAL_PARAMETER__VARIADIC:
				setVariadic(VARIADIC_EDEFAULT);
				return;
			case N4JSPackage.FORMAL_PARAMETER__DEFINED_TYPE_ELEMENT:
				setDefinedTypeElement((TFormalParameter)null);
				return;
			case N4JSPackage.FORMAL_PARAMETER__HAS_INITIALIZER_ASSIGNMENT:
				setHasInitializerAssignment(HAS_INITIALIZER_ASSIGNMENT_EDEFAULT);
				return;
			case N4JSPackage.FORMAL_PARAMETER__INITIALIZER:
				setInitializer((Expression)null);
				return;
			case N4JSPackage.FORMAL_PARAMETER__BINDING_PATTERN:
				setBindingPattern((BindingPattern)null);
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
			case N4JSPackage.FORMAL_PARAMETER__DECLARED_TYPE_REF:
				return declaredTypeRef != null;
			case N4JSPackage.FORMAL_PARAMETER__DECLARED_TYPE_REF_IN_AST:
				return declaredTypeRefInAST != null;
			case N4JSPackage.FORMAL_PARAMETER__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case N4JSPackage.FORMAL_PARAMETER__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case N4JSPackage.FORMAL_PARAMETER__VARIADIC:
				return variadic != VARIADIC_EDEFAULT;
			case N4JSPackage.FORMAL_PARAMETER__DEFINED_TYPE_ELEMENT:
				return definedTypeElement != null;
			case N4JSPackage.FORMAL_PARAMETER__HAS_INITIALIZER_ASSIGNMENT:
				return hasInitializerAssignment != HAS_INITIALIZER_ASSIGNMENT_EDEFAULT;
			case N4JSPackage.FORMAL_PARAMETER__INITIALIZER:
				return initializer != null;
			case N4JSPackage.FORMAL_PARAMETER__BINDING_PATTERN:
				return bindingPattern != null;
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
		if (baseClass == TypeProvidingElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == TypedElement.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.FORMAL_PARAMETER__DECLARED_TYPE_REF: return N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF;
				case N4JSPackage.FORMAL_PARAMETER__DECLARED_TYPE_REF_IN_AST: return N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF_IN_AST;
				default: return -1;
			}
		}
		if (baseClass == TypableElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == IdentifiableElement.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.FORMAL_PARAMETER__NAME: return TypesPackage.IDENTIFIABLE_ELEMENT__NAME;
				default: return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == Variable.class) {
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
		if (baseClass == TypeProvidingElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == TypedElement.class) {
			switch (baseFeatureID) {
				case N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF: return N4JSPackage.FORMAL_PARAMETER__DECLARED_TYPE_REF;
				case N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF_IN_AST: return N4JSPackage.FORMAL_PARAMETER__DECLARED_TYPE_REF_IN_AST;
				default: return -1;
			}
		}
		if (baseClass == TypableElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == IdentifiableElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.IDENTIFIABLE_ELEMENT__NAME: return N4JSPackage.FORMAL_PARAMETER__NAME;
				default: return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == Variable.class) {
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
		if (baseClass == TypeProvidingElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.TYPE_PROVIDING_ELEMENT___GET_DECLARED_TYPE_REF: return N4JSPackage.FORMAL_PARAMETER___GET_DECLARED_TYPE_REF;
				case N4JSPackage.TYPE_PROVIDING_ELEMENT___GET_DECLARED_TYPE_REF_IN_AST: return N4JSPackage.FORMAL_PARAMETER___GET_DECLARED_TYPE_REF_IN_AST;
				default: return -1;
			}
		}
		if (baseClass == TypedElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == TypableElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == IdentifiableElement.class) {
			switch (baseOperationID) {
				case TypesPackage.IDENTIFIABLE_ELEMENT___GET_CONTAINING_MODULE: return N4JSPackage.FORMAL_PARAMETER___GET_CONTAINING_MODULE;
				default: return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.NAMED_ELEMENT___GET_NAME: return N4JSPackage.FORMAL_PARAMETER___GET_NAME;
				default: return -1;
			}
		}
		if (baseClass == Variable.class) {
			switch (baseOperationID) {
				case N4JSPackage.VARIABLE___IS_CONST: return N4JSPackage.FORMAL_PARAMETER___IS_CONST;
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
			case N4JSPackage.FORMAL_PARAMETER___IS_CONST:
				return isConst();
			case N4JSPackage.FORMAL_PARAMETER___GET_CONTAINING_MODULE:
				return getContainingModule();
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
		result.append(" (name: ");
		result.append(name);
		result.append(", variadic: ");
		result.append(variadic);
		result.append(", hasInitializerAssignment: ");
		result.append(hasInitializerAssignment);
		result.append(')');
		return result.toString();
	}

} //FormalParameterImpl
