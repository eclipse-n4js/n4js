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
import org.eclipse.n4js.n4JS.TypeReferenceNode;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.TFunction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDefinitionImpl#getFpars <em>Fpars</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDefinitionImpl#getDeclaredReturnTypeRefNode <em>Declared Return Type Ref Node</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDefinitionImpl#isGenerator <em>Generator</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDefinitionImpl#isDeclaredAsync <em>Declared Async</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FunctionDefinitionImpl#getDefinedFunction <em>Defined Function</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class FunctionDefinitionImpl extends FunctionOrFieldAccessorImpl implements FunctionDefinition {
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
	 * The cached value of the '{@link #getDefinedFunction() <em>Defined Function</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinedFunction()
	 * @generated
	 * @ordered
	 */
	protected TFunction definedFunction;

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
	public TFunction getDefinedFunction() {
		if (definedFunction != null && definedFunction.eIsProxy()) {
			InternalEObject oldDefinedFunction = (InternalEObject)definedFunction;
			definedFunction = (TFunction)eResolveProxy(oldDefinedFunction);
			if (definedFunction != oldDefinedFunction) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.FUNCTION_DEFINITION__DEFINED_FUNCTION, oldDefinedFunction, definedFunction));
			}
		}
		return definedFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TFunction basicGetDefinedFunction() {
		return definedFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefinedFunction(TFunction newDefinedFunction) {
		TFunction oldDefinedFunction = definedFunction;
		definedFunction = newDefinedFunction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FUNCTION_DEFINITION__DEFINED_FUNCTION, oldDefinedFunction, definedFunction));
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.FUNCTION_DEFINITION__FPARS:
				return ((InternalEList<?>)getFpars()).basicRemove(otherEnd, msgs);
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_NODE:
				return basicSetDeclaredReturnTypeRefNode(null, msgs);
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
			case N4JSPackage.FUNCTION_DEFINITION__FPARS:
				return getFpars();
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_NODE:
				return getDeclaredReturnTypeRefNode();
			case N4JSPackage.FUNCTION_DEFINITION__GENERATOR:
				return isGenerator();
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_ASYNC:
				return isDeclaredAsync();
			case N4JSPackage.FUNCTION_DEFINITION__DEFINED_FUNCTION:
				if (resolve) return getDefinedFunction();
				return basicGetDefinedFunction();
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
			case N4JSPackage.FUNCTION_DEFINITION__FPARS:
				getFpars().clear();
				getFpars().addAll((Collection<? extends FormalParameter>)newValue);
				return;
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_NODE:
				setDeclaredReturnTypeRefNode((TypeReferenceNode<TypeRef>)newValue);
				return;
			case N4JSPackage.FUNCTION_DEFINITION__GENERATOR:
				setGenerator((Boolean)newValue);
				return;
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_ASYNC:
				setDeclaredAsync((Boolean)newValue);
				return;
			case N4JSPackage.FUNCTION_DEFINITION__DEFINED_FUNCTION:
				setDefinedFunction((TFunction)newValue);
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
			case N4JSPackage.FUNCTION_DEFINITION__FPARS:
				getFpars().clear();
				return;
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_NODE:
				setDeclaredReturnTypeRefNode((TypeReferenceNode<TypeRef>)null);
				return;
			case N4JSPackage.FUNCTION_DEFINITION__GENERATOR:
				setGenerator(GENERATOR_EDEFAULT);
				return;
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_ASYNC:
				setDeclaredAsync(DECLARED_ASYNC_EDEFAULT);
				return;
			case N4JSPackage.FUNCTION_DEFINITION__DEFINED_FUNCTION:
				setDefinedFunction((TFunction)null);
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
			case N4JSPackage.FUNCTION_DEFINITION__FPARS:
				return fpars != null && !fpars.isEmpty();
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_NODE:
				return declaredReturnTypeRefNode != null;
			case N4JSPackage.FUNCTION_DEFINITION__GENERATOR:
				return generator != GENERATOR_EDEFAULT;
			case N4JSPackage.FUNCTION_DEFINITION__DECLARED_ASYNC:
				return declaredAsync != DECLARED_ASYNC_EDEFAULT;
			case N4JSPackage.FUNCTION_DEFINITION__DEFINED_FUNCTION:
				return definedFunction != null;
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
		if (baseClass == FunctionOrFieldAccessor.class) {
			switch (baseOperationID) {
				case N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR___IS_RETURN_VALUE_OPTIONAL: return N4JSPackage.FUNCTION_DEFINITION___IS_RETURN_VALUE_OPTIONAL;
				case N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR___IS_ASYNC: return N4JSPackage.FUNCTION_DEFINITION___IS_ASYNC;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
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
