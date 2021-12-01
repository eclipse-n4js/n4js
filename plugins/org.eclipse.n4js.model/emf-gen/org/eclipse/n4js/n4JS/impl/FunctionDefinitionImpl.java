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

import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.n4JS.TypePredicateDeclaration;
import org.eclipse.n4js.n4JS.TypeReferenceNode;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.Type;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDefinitionImpl#getDefinedType <em>Defined Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDefinitionImpl#getFpars <em>Fpars</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDefinitionImpl#getDeclaredReturnTypeRefNode <em>Declared Return Type Ref Node</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDefinitionImpl#getDeclaredReturnTypePredicate <em>Declared Return Type Predicate</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDefinitionImpl#isGenerator <em>Generator</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDefinitionImpl#isDeclaredAsync <em>Declared Async</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class FunctionDefinitionImpl extends FunctionOrFieldAccessorImpl implements FunctionDefinition {
	/**
	 * The cached value of the '{@link #getDefinedType() <em>Defined Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinedType()
	 * @generated
	 * @ordered
	 */
	protected Type definedType;

	/**
	 * The cached value of the '{@link #getFpars() <em>Fpars</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFpars()
	 * @generated
	 * @ordered
	 */
	protected EList<FormalParameter> fpars;

	/**
	 * The cached value of the '{@link #getDeclaredReturnTypeRefNode() <em>Declared Return Type Ref Node</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredReturnTypeRefNode()
	 * @generated
	 * @ordered
	 */
	protected TypeReferenceNode<TypeRef> declaredReturnTypeRefNode;

	/**
	 * The cached value of the '{@link #getDeclaredReturnTypePredicate() <em>Declared Return Type Predicate</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredReturnTypePredicate()
	 * @generated
	 * @ordered
	 */
	protected TypePredicateDeclaration declaredReturnTypePredicate;

	/**
	 * The default value of the '{@link #isGenerator() <em>Generator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerator()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATOR_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isGenerator() <em>Generator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerator()
	 * @generated
	 * @ordered
	 */
	protected boolean generator = GENERATOR_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionDefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.FUNCTION_DEFINITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Type getDefinedType() {
		if (definedType != null && definedType.eIsProxy()) {
			InternalEObject oldDefinedType = (InternalEObject)definedType;
			definedType = (Type)eResolveProxy(oldDefinedType);
			if (definedType != oldDefinedType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.FUNCTION_DEFINITION__DEFINED_TYPE, oldDefinedType, definedType));
			}
		}
		return definedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type basicGetDefinedType() {
		return definedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefinedType(Type newDefinedType) {
		Type oldDefinedType = definedType;
		definedType = newDefinedType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DEFINITION__DEFINED_TYPE, oldDefinedType, definedType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<FormalParameter> getFpars() {
		if (fpars == null) {
			fpars = new EObjectContainmentEList<FormalParameter>(FormalParameter.class, this, N4JSPackage.FUNCTION_DEFINITION__FPARS);
		}
		return fpars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeReferenceNode<TypeRef> getDeclaredReturnTypeRefNode() {
		return declaredReturnTypeRefNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredReturnTypeRefNode(TypeReferenceNode<TypeRef> newDeclaredReturnTypeRefNode, NotificationChain msgs) {
		TypeReferenceNode<TypeRef> oldDeclaredReturnTypeRefNode = declaredReturnTypeRefNode;
		declaredReturnTypeRefNode = newDeclaredReturnTypeRefNode;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_NODE, oldDeclaredReturnTypeRefNode, newDeclaredReturnTypeRefNode);
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
	public void setDeclaredReturnTypeRefNode(TypeReferenceNode<TypeRef> newDeclaredReturnTypeRefNode) {
		if (newDeclaredReturnTypeRefNode != declaredReturnTypeRefNode) {
			NotificationChain msgs = null;
			if (declaredReturnTypeRefNode != null)
				msgs = ((InternalEObject)declaredReturnTypeRefNode).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_NODE, null, msgs);
			if (newDeclaredReturnTypeRefNode != null)
				msgs = ((InternalEObject)newDeclaredReturnTypeRefNode).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_NODE, null, msgs);
			msgs = basicSetDeclaredReturnTypeRefNode(newDeclaredReturnTypeRefNode, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_NODE, newDeclaredReturnTypeRefNode, newDeclaredReturnTypeRefNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypePredicateDeclaration getDeclaredReturnTypePredicate() {
		return declaredReturnTypePredicate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredReturnTypePredicate(TypePredicateDeclaration newDeclaredReturnTypePredicate, NotificationChain msgs) {
		TypePredicateDeclaration oldDeclaredReturnTypePredicate = declaredReturnTypePredicate;
		declaredReturnTypePredicate = newDeclaredReturnTypePredicate;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_PREDICATE, oldDeclaredReturnTypePredicate, newDeclaredReturnTypePredicate);
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
	public void setDeclaredReturnTypePredicate(TypePredicateDeclaration newDeclaredReturnTypePredicate) {
		if (newDeclaredReturnTypePredicate != declaredReturnTypePredicate) {
			NotificationChain msgs = null;
			if (declaredReturnTypePredicate != null)
				msgs = ((InternalEObject)declaredReturnTypePredicate).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_PREDICATE, null, msgs);
			if (newDeclaredReturnTypePredicate != null)
				msgs = ((InternalEObject)newDeclaredReturnTypePredicate).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_PREDICATE, null, msgs);
			msgs = basicSetDeclaredReturnTypePredicate(newDeclaredReturnTypePredicate, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_PREDICATE, newDeclaredReturnTypePredicate, newDeclaredReturnTypePredicate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isGenerator() {
		return generator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGenerator(boolean newGenerator) {
		boolean oldGenerator = generator;
		generator = newGenerator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DEFINITION__GENERATOR, oldGenerator, generator));
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
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DEFINITION__DECLARED_ASYNC, oldDeclaredAsync, declaredAsync));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isReturnValueOptional() {
		boolean _or = false;
		if (((this.getDefinedFunction() != null) && this.getDefinedFunction().isReturnValueOptional())) {
			_or = true;
		} else {
			boolean _and = false;
			TypeReferenceNode<TypeRef> _declaredReturnTypeRefNode = this.getDeclaredReturnTypeRefNode();
			TypeRef _typeRefInAST = null;
			if (_declaredReturnTypeRefNode!=null) {
				_typeRefInAST=_declaredReturnTypeRefNode.getTypeRefInAST();
			}
			boolean _tripleNotEquals = (_typeRefInAST != null);
			if (!_tripleNotEquals) {
				_and = false;
			} else {
				boolean _isFollowedByQuestionMark = this.getDeclaredReturnTypeRefNode().getTypeRefInAST().isFollowedByQuestionMark();
				_and = _isFollowedByQuestionMark;
			}
			_or = _and;
		}
		return _or;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredReturnTypeRef() {
		TypeReferenceNode<TypeRef> _declaredReturnTypeRefNode = this.getDeclaredReturnTypeRefNode();
		TypeRef _typeRef = null;
		if (_declaredReturnTypeRefNode!=null) {
			_typeRef=_declaredReturnTypeRefNode.getTypeRef();
		}
		return _typeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredReturnTypeRefInAST() {
		TypeReferenceNode<TypeRef> _declaredReturnTypeRefNode = this.getDeclaredReturnTypeRefNode();
		TypeRef _typeRefInAST = null;
		if (_declaredReturnTypeRefNode!=null) {
			_typeRefInAST=_declaredReturnTypeRefNode.getTypeRefInAST();
		}
		return _typeRefInAST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAsync() {
		return this.isDeclaredAsync();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TFunction getDefinedFunction() {
		final Type defType = this.getDefinedType();
		TFunction _xifexpression = null;
		if ((defType instanceof TFunction)) {
			_xifexpression = ((TFunction)defType);
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
			case N4JSPackage.FUNCTION_DEFINITION__FPARS:
				return ((InternalEList<?>)getFpars()).basicRemove(otherEnd, msgs);
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_NODE:
				return basicSetDeclaredReturnTypeRefNode(null, msgs);
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_PREDICATE:
				return basicSetDeclaredReturnTypePredicate(null, msgs);
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
			case N4JSPackage.FUNCTION_DEFINITION__DEFINED_TYPE:
				if (resolve) return getDefinedType();
				return basicGetDefinedType();
			case N4JSPackage.FUNCTION_DEFINITION__FPARS:
				return getFpars();
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_NODE:
				return getDeclaredReturnTypeRefNode();
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_PREDICATE:
				return getDeclaredReturnTypePredicate();
			case N4JSPackage.FUNCTION_DEFINITION__GENERATOR:
				return isGenerator();
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_ASYNC:
				return isDeclaredAsync();
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
			case N4JSPackage.FUNCTION_DEFINITION__DEFINED_TYPE:
				setDefinedType((Type)newValue);
				return;
			case N4JSPackage.FUNCTION_DEFINITION__FPARS:
				getFpars().clear();
				getFpars().addAll((Collection<? extends FormalParameter>)newValue);
				return;
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_NODE:
				setDeclaredReturnTypeRefNode((TypeReferenceNode<TypeRef>)newValue);
				return;
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_PREDICATE:
				setDeclaredReturnTypePredicate((TypePredicateDeclaration)newValue);
				return;
			case N4JSPackage.FUNCTION_DEFINITION__GENERATOR:
				setGenerator((Boolean)newValue);
				return;
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_ASYNC:
				setDeclaredAsync((Boolean)newValue);
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
			case N4JSPackage.FUNCTION_DEFINITION__DEFINED_TYPE:
				setDefinedType((Type)null);
				return;
			case N4JSPackage.FUNCTION_DEFINITION__FPARS:
				getFpars().clear();
				return;
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_NODE:
				setDeclaredReturnTypeRefNode((TypeReferenceNode<TypeRef>)null);
				return;
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_PREDICATE:
				setDeclaredReturnTypePredicate((TypePredicateDeclaration)null);
				return;
			case N4JSPackage.FUNCTION_DEFINITION__GENERATOR:
				setGenerator(GENERATOR_EDEFAULT);
				return;
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_ASYNC:
				setDeclaredAsync(DECLARED_ASYNC_EDEFAULT);
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
			case N4JSPackage.FUNCTION_DEFINITION__DEFINED_TYPE:
				return definedType != null;
			case N4JSPackage.FUNCTION_DEFINITION__FPARS:
				return fpars != null && !fpars.isEmpty();
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_NODE:
				return declaredReturnTypeRefNode != null;
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_PREDICATE:
				return declaredReturnTypePredicate != null;
			case N4JSPackage.FUNCTION_DEFINITION__GENERATOR:
				return generator != GENERATOR_EDEFAULT;
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_ASYNC:
				return declaredAsync != DECLARED_ASYNC_EDEFAULT;
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
		if (baseClass == TypeDefiningElement.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.FUNCTION_DEFINITION__DEFINED_TYPE: return N4JSPackage.TYPE_DEFINING_ELEMENT__DEFINED_TYPE;
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
		if (baseClass == TypeDefiningElement.class) {
			switch (baseFeatureID) {
				case N4JSPackage.TYPE_DEFINING_ELEMENT__DEFINED_TYPE: return N4JSPackage.FUNCTION_DEFINITION__DEFINED_TYPE;
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
		if (baseClass == FunctionOrFieldAccessor.class) {
			switch (baseOperationID) {
				case N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR___IS_RETURN_VALUE_OPTIONAL: return N4JSPackage.FUNCTION_DEFINITION___IS_RETURN_VALUE_OPTIONAL;
				case N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR___IS_ASYNC: return N4JSPackage.FUNCTION_DEFINITION___IS_ASYNC;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TypeDefiningElement.class) {
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
			case N4JSPackage.FUNCTION_DEFINITION___IS_RETURN_VALUE_OPTIONAL:
				return isReturnValueOptional();
			case N4JSPackage.FUNCTION_DEFINITION___GET_DECLARED_RETURN_TYPE_REF:
				return getDeclaredReturnTypeRef();
			case N4JSPackage.FUNCTION_DEFINITION___GET_DECLARED_RETURN_TYPE_REF_IN_AST:
				return getDeclaredReturnTypeRefInAST();
			case N4JSPackage.FUNCTION_DEFINITION___IS_ASYNC:
				return isAsync();
			case N4JSPackage.FUNCTION_DEFINITION___GET_DEFINED_FUNCTION:
				return getDefinedFunction();
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
		result.append(" (generator: ");
		result.append(generator);
		result.append(", declaredAsync: ");
		result.append(declaredAsync);
		result.append(')');
		return result.toString();
	}

} //FunctionDefinitionImpl
