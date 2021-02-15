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

import com.google.common.base.Objects;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.TypeProvidingElement;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.n4JS.TypedElement;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.TStructField;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property Name Value Pair</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.PropertyNameValuePairImpl#getDeclaredTypeRefNode <em>Declared Type Ref Node</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.PropertyNameValuePairImpl#getDefinedField <em>Defined Field</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.PropertyNameValuePairImpl#isDeclaredOptional <em>Declared Optional</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.PropertyNameValuePairImpl#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PropertyNameValuePairImpl extends AnnotablePropertyAssignmentImpl implements PropertyNameValuePair {
	/**
	 * The cached value of the '{@link #getDeclaredTypeRefNode() <em>Declared Type Ref Node</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredTypeRefNode()
	 * @generated
	 * @ordered
	 */
	protected TypeReferenceNode<TypeRef> declaredTypeRefNode;

	/**
	 * The cached value of the '{@link #getDefinedField() <em>Defined Field</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinedField()
	 * @generated
	 * @ordered
	 */
	protected TStructField definedField;

	/**
	 * The default value of the '{@link #isDeclaredOptional() <em>Declared Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredOptional()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_OPTIONAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredOptional() <em>Declared Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredOptional()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredOptional = DECLARED_OPTIONAL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected Expression expression;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyNameValuePairImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.PROPERTY_NAME_VALUE_PAIR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeReferenceNode<TypeRef> getDeclaredTypeRefNode() {
		return declaredTypeRefNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredTypeRefNode(TypeReferenceNode<TypeRef> newDeclaredTypeRefNode, NotificationChain msgs) {
		TypeReferenceNode<TypeRef> oldDeclaredTypeRefNode = declaredTypeRefNode;
		declaredTypeRefNode = newDeclaredTypeRefNode;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DECLARED_TYPE_REF_NODE, oldDeclaredTypeRefNode, newDeclaredTypeRefNode);
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
	public void setDeclaredTypeRefNode(TypeReferenceNode<TypeRef> newDeclaredTypeRefNode) {
		if (newDeclaredTypeRefNode != declaredTypeRefNode) {
			NotificationChain msgs = null;
			if (declaredTypeRefNode != null)
				msgs = ((InternalEObject)declaredTypeRefNode).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DECLARED_TYPE_REF_NODE, null, msgs);
			if (newDeclaredTypeRefNode != null)
				msgs = ((InternalEObject)newDeclaredTypeRefNode).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DECLARED_TYPE_REF_NODE, null, msgs);
			msgs = basicSetDeclaredTypeRefNode(newDeclaredTypeRefNode, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DECLARED_TYPE_REF_NODE, newDeclaredTypeRefNode, newDeclaredTypeRefNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TStructField getDefinedField() {
		if (definedField != null && definedField.eIsProxy()) {
			InternalEObject oldDefinedField = (InternalEObject)definedField;
			definedField = (TStructField)eResolveProxy(oldDefinedField);
			if (definedField != oldDefinedField) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DEFINED_FIELD, oldDefinedField, definedField));
			}
		}
		return definedField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TStructField basicGetDefinedField() {
		return definedField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefinedField(TStructField newDefinedField) {
		TStructField oldDefinedField = definedField;
		definedField = newDefinedField;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DEFINED_FIELD, oldDefinedField, definedField));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredOptional() {
		return declaredOptional;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredOptional(boolean newDeclaredOptional) {
		boolean oldDeclaredOptional = declaredOptional;
		declaredOptional = newDeclaredOptional;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DECLARED_OPTIONAL, oldDeclaredOptional, declaredOptional));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Expression getExpression() {
		return expression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExpression(Expression newExpression, NotificationChain msgs) {
		Expression oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.PROPERTY_NAME_VALUE_PAIR__EXPRESSION, oldExpression, newExpression);
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
	public void setExpression(Expression newExpression) {
		if (newExpression != expression) {
			NotificationChain msgs = null;
			if (expression != null)
				msgs = ((InternalEObject)expression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.PROPERTY_NAME_VALUE_PAIR__EXPRESSION, null, msgs);
			if (newExpression != null)
				msgs = ((InternalEObject)newExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.PROPERTY_NAME_VALUE_PAIR__EXPRESSION, null, msgs);
			msgs = basicSetExpression(newExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.PROPERTY_NAME_VALUE_PAIR__EXPRESSION, newExpression, newExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TStructField getDefinedMember() {
		return this.getDefinedField();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isValidName() {
		String _name = this.getName();
		boolean _equals = Objects.equal("prototype", _name);
		if (_equals) {
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredTypeRef() {
		TypeReferenceNode<TypeRef> _declaredTypeRefNode = this.getDeclaredTypeRefNode();
		TypeRef _typeRef = null;
		if (_declaredTypeRefNode!=null) {
			_typeRef=_declaredTypeRefNode.getTypeRef();
		}
		return _typeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredTypeRefInAST() {
		TypeReferenceNode<TypeRef> _declaredTypeRefNode = this.getDeclaredTypeRefNode();
		TypeRef _typeRefInAST = null;
		if (_declaredTypeRefNode!=null) {
			_typeRefInAST=_declaredTypeRefNode.getTypeRefInAST();
		}
		return _typeRefInAST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DECLARED_TYPE_REF_NODE:
				return basicSetDeclaredTypeRefNode(null, msgs);
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR__EXPRESSION:
				return basicSetExpression(null, msgs);
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
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DECLARED_TYPE_REF_NODE:
				return getDeclaredTypeRefNode();
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DEFINED_FIELD:
				if (resolve) return getDefinedField();
				return basicGetDefinedField();
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DECLARED_OPTIONAL:
				return isDeclaredOptional();
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR__EXPRESSION:
				return getExpression();
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
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DECLARED_TYPE_REF_NODE:
				setDeclaredTypeRefNode((TypeReferenceNode<TypeRef>)newValue);
				return;
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DEFINED_FIELD:
				setDefinedField((TStructField)newValue);
				return;
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DECLARED_OPTIONAL:
				setDeclaredOptional((Boolean)newValue);
				return;
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR__EXPRESSION:
				setExpression((Expression)newValue);
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
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DECLARED_TYPE_REF_NODE:
				setDeclaredTypeRefNode((TypeReferenceNode<TypeRef>)null);
				return;
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DEFINED_FIELD:
				setDefinedField((TStructField)null);
				return;
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DECLARED_OPTIONAL:
				setDeclaredOptional(DECLARED_OPTIONAL_EDEFAULT);
				return;
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR__EXPRESSION:
				setExpression((Expression)null);
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
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DECLARED_TYPE_REF_NODE:
				return declaredTypeRefNode != null;
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DEFINED_FIELD:
				return definedField != null;
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DECLARED_OPTIONAL:
				return declaredOptional != DECLARED_OPTIONAL_EDEFAULT;
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR__EXPRESSION:
				return expression != null;
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
				case N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DECLARED_TYPE_REF_NODE: return N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF_NODE;
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
				case N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF_NODE: return N4JSPackage.PROPERTY_NAME_VALUE_PAIR__DECLARED_TYPE_REF_NODE;
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
		if (baseClass == PropertyNameOwner.class) {
			switch (baseOperationID) {
				case N4JSPackage.PROPERTY_NAME_OWNER___IS_VALID_NAME: return N4JSPackage.PROPERTY_NAME_VALUE_PAIR___IS_VALID_NAME;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == PropertyAssignment.class) {
			switch (baseOperationID) {
				case N4JSPackage.PROPERTY_ASSIGNMENT___GET_DEFINED_MEMBER: return N4JSPackage.PROPERTY_NAME_VALUE_PAIR___GET_DEFINED_MEMBER;
				case N4JSPackage.PROPERTY_ASSIGNMENT___IS_VALID_NAME: return N4JSPackage.PROPERTY_NAME_VALUE_PAIR___IS_VALID_NAME;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TypeProvidingElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.TYPE_PROVIDING_ELEMENT___GET_DECLARED_TYPE_REF: return N4JSPackage.PROPERTY_NAME_VALUE_PAIR___GET_DECLARED_TYPE_REF;
				case N4JSPackage.TYPE_PROVIDING_ELEMENT___GET_DECLARED_TYPE_REF_IN_AST: return N4JSPackage.PROPERTY_NAME_VALUE_PAIR___GET_DECLARED_TYPE_REF_IN_AST;
				default: return -1;
			}
		}
		if (baseClass == TypedElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.TYPED_ELEMENT___GET_DECLARED_TYPE_REF: return N4JSPackage.PROPERTY_NAME_VALUE_PAIR___GET_DECLARED_TYPE_REF;
				case N4JSPackage.TYPED_ELEMENT___GET_DECLARED_TYPE_REF_IN_AST: return N4JSPackage.PROPERTY_NAME_VALUE_PAIR___GET_DECLARED_TYPE_REF_IN_AST;
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
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR___GET_DEFINED_MEMBER:
				return getDefinedMember();
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR___IS_VALID_NAME:
				return isValidName();
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR___GET_DECLARED_TYPE_REF:
				return getDeclaredTypeRef();
			case N4JSPackage.PROPERTY_NAME_VALUE_PAIR___GET_DECLARED_TYPE_REF_IN_AST:
				return getDeclaredTypeRefInAST();
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
		result.append(" (declaredOptional: ");
		result.append(declaredOptional);
		result.append(')');
		return result.toString();
	}

} //PropertyNameValuePairImpl
