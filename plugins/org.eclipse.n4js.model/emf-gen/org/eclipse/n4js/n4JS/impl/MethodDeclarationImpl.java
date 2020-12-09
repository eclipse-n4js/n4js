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

import com.google.common.collect.Iterators;

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

import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.MethodDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.SuperLiteral;
import org.eclipse.n4js.n4JS.TypeProvidingElement;
import org.eclipse.n4js.n4JS.TypedElement;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;

import org.eclipse.n4js.utils.EcoreUtilN4;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IteratorExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Method Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.MethodDeclarationImpl#getTypeVars <em>Type Vars</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.MethodDeclarationImpl#getDeclaredTypeRef <em>Declared Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.MethodDeclarationImpl#getBogusTypeRef <em>Bogus Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.MethodDeclarationImpl#getDeclaredName <em>Declared Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class MethodDeclarationImpl extends FunctionDefinitionImpl implements MethodDeclaration {
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
	 * The cached value of the '{@link #getDeclaredTypeRef() <em>Declared Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef declaredTypeRef;

	/**
	 * The cached value of the '{@link #getBogusTypeRef() <em>Bogus Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBogusTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef bogusTypeRef;

	/**
	 * The cached value of the '{@link #getDeclaredName() <em>Declared Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredName()
	 * @generated
	 * @ordered
	 */
	protected LiteralOrComputedPropertyName declaredName;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MethodDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.METHOD_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypeVariable> getTypeVars() {
		if (typeVars == null) {
			typeVars = new EObjectContainmentEList<TypeVariable>(TypeVariable.class, this, N4JSPackage.METHOD_DECLARATION__TYPE_VARS);
		}
		return typeVars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredTypeRef() {
		return declaredTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredTypeRef(TypeRef newDeclaredTypeRef, NotificationChain msgs) {
		TypeRef oldDeclaredTypeRef = declaredTypeRef;
		declaredTypeRef = newDeclaredTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.METHOD_DECLARATION__DECLARED_TYPE_REF, oldDeclaredTypeRef, newDeclaredTypeRef);
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
	public void setDeclaredTypeRef(TypeRef newDeclaredTypeRef) {
		if (newDeclaredTypeRef != declaredTypeRef) {
			NotificationChain msgs = null;
			if (declaredTypeRef != null)
				msgs = ((InternalEObject)declaredTypeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.METHOD_DECLARATION__DECLARED_TYPE_REF, null, msgs);
			if (newDeclaredTypeRef != null)
				msgs = ((InternalEObject)newDeclaredTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.METHOD_DECLARATION__DECLARED_TYPE_REF, null, msgs);
			msgs = basicSetDeclaredTypeRef(newDeclaredTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.METHOD_DECLARATION__DECLARED_TYPE_REF, newDeclaredTypeRef, newDeclaredTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getBogusTypeRef() {
		return bogusTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBogusTypeRef(TypeRef newBogusTypeRef, NotificationChain msgs) {
		TypeRef oldBogusTypeRef = bogusTypeRef;
		bogusTypeRef = newBogusTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.METHOD_DECLARATION__BOGUS_TYPE_REF, oldBogusTypeRef, newBogusTypeRef);
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
	public void setBogusTypeRef(TypeRef newBogusTypeRef) {
		if (newBogusTypeRef != bogusTypeRef) {
			NotificationChain msgs = null;
			if (bogusTypeRef != null)
				msgs = ((InternalEObject)bogusTypeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.METHOD_DECLARATION__BOGUS_TYPE_REF, null, msgs);
			if (newBogusTypeRef != null)
				msgs = ((InternalEObject)newBogusTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.METHOD_DECLARATION__BOGUS_TYPE_REF, null, msgs);
			msgs = basicSetBogusTypeRef(newBogusTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.METHOD_DECLARATION__BOGUS_TYPE_REF, newBogusTypeRef, newBogusTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LiteralOrComputedPropertyName getDeclaredName() {
		return declaredName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredName(LiteralOrComputedPropertyName newDeclaredName, NotificationChain msgs) {
		LiteralOrComputedPropertyName oldDeclaredName = declaredName;
		declaredName = newDeclaredName;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.METHOD_DECLARATION__DECLARED_NAME, oldDeclaredName, newDeclaredName);
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
	public void setDeclaredName(LiteralOrComputedPropertyName newDeclaredName) {
		if (newDeclaredName != declaredName) {
			NotificationChain msgs = null;
			if (declaredName != null)
				msgs = ((InternalEObject)declaredName).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.METHOD_DECLARATION__DECLARED_NAME, null, msgs);
			if (newDeclaredName != null)
				msgs = ((InternalEObject)newDeclaredName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.METHOD_DECLARATION__DECLARED_NAME, null, msgs);
			msgs = basicSetDeclaredName(newDeclaredName, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.METHOD_DECLARATION__DECLARED_NAME, newDeclaredName, newDeclaredName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean existsExplicitSuperCall() {
		final Function1<ExpressionStatement, Expression> _function = new Function1<ExpressionStatement, Expression>() {
			public Expression apply(final ExpressionStatement it) {
				return it.getExpression();
			}
		};
		final Function1<ParameterizedCallExpression, Boolean> _function_1 = new Function1<ParameterizedCallExpression, Boolean>() {
			public Boolean apply(final ParameterizedCallExpression it) {
				Expression _target = it.getTarget();
				return Boolean.valueOf((_target instanceof SuperLiteral));
			}
		};
		final boolean existsSuperCall = IteratorExtensions.<ParameterizedCallExpression>exists(Iterators.<ParameterizedCallExpression>filter(IteratorExtensions.<ExpressionStatement, Expression>map(Iterators.<ExpressionStatement>filter(EcoreUtilN4.<Statement>getAllDirectlyFoundContentsOfType(this.getBody(), Statement.class), ExpressionStatement.class), _function), ParameterizedCallExpression.class), _function_1);
		return existsSuperCall;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TMember getDefinedTypeElement() {
		TMember _xifexpression = null;
		Type _definedType = this.getDefinedType();
		boolean _tripleEquals = (_definedType == null);
		if (_tripleEquals) {
			_xifexpression = null;
		}
		else {
			TMember _xifexpression_1 = null;
			Type _definedType_1 = this.getDefinedType();
			if ((_definedType_1 instanceof TMember)) {
				Type _definedType_2 = this.getDefinedType();
				_xifexpression_1 = ((TMember) _definedType_2);
			}
			else {
				throw new IllegalArgumentException(
					"");
			}
			_xifexpression = _xifexpression_1;
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isStatic() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		LiteralOrComputedPropertyName _declaredName = this.getDeclaredName();
		String _name = null;
		if (_declaredName!=null) {
			_name=_declaredName.getName();
		}
		return _name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean hasComputedPropertyName() {
		final LiteralOrComputedPropertyName declName = this.getDeclaredName();
		return ((declName != null) && declName.hasComputedPropertyName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isValidName() {
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.METHOD_DECLARATION__TYPE_VARS:
				return ((InternalEList<?>)getTypeVars()).basicRemove(otherEnd, msgs);
			case N4JSPackage.METHOD_DECLARATION__DECLARED_TYPE_REF:
				return basicSetDeclaredTypeRef(null, msgs);
			case N4JSPackage.METHOD_DECLARATION__BOGUS_TYPE_REF:
				return basicSetBogusTypeRef(null, msgs);
			case N4JSPackage.METHOD_DECLARATION__DECLARED_NAME:
				return basicSetDeclaredName(null, msgs);
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
			case N4JSPackage.METHOD_DECLARATION__TYPE_VARS:
				return getTypeVars();
			case N4JSPackage.METHOD_DECLARATION__DECLARED_TYPE_REF:
				return getDeclaredTypeRef();
			case N4JSPackage.METHOD_DECLARATION__BOGUS_TYPE_REF:
				return getBogusTypeRef();
			case N4JSPackage.METHOD_DECLARATION__DECLARED_NAME:
				return getDeclaredName();
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
			case N4JSPackage.METHOD_DECLARATION__TYPE_VARS:
				getTypeVars().clear();
				getTypeVars().addAll((Collection<? extends TypeVariable>)newValue);
				return;
			case N4JSPackage.METHOD_DECLARATION__DECLARED_TYPE_REF:
				setDeclaredTypeRef((TypeRef)newValue);
				return;
			case N4JSPackage.METHOD_DECLARATION__BOGUS_TYPE_REF:
				setBogusTypeRef((TypeRef)newValue);
				return;
			case N4JSPackage.METHOD_DECLARATION__DECLARED_NAME:
				setDeclaredName((LiteralOrComputedPropertyName)newValue);
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
			case N4JSPackage.METHOD_DECLARATION__TYPE_VARS:
				getTypeVars().clear();
				return;
			case N4JSPackage.METHOD_DECLARATION__DECLARED_TYPE_REF:
				setDeclaredTypeRef((TypeRef)null);
				return;
			case N4JSPackage.METHOD_DECLARATION__BOGUS_TYPE_REF:
				setBogusTypeRef((TypeRef)null);
				return;
			case N4JSPackage.METHOD_DECLARATION__DECLARED_NAME:
				setDeclaredName((LiteralOrComputedPropertyName)null);
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
			case N4JSPackage.METHOD_DECLARATION__TYPE_VARS:
				return typeVars != null && !typeVars.isEmpty();
			case N4JSPackage.METHOD_DECLARATION__DECLARED_TYPE_REF:
				return declaredTypeRef != null;
			case N4JSPackage.METHOD_DECLARATION__BOGUS_TYPE_REF:
				return bogusTypeRef != null;
			case N4JSPackage.METHOD_DECLARATION__DECLARED_NAME:
				return declaredName != null;
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
		if (baseClass == GenericDeclaration.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.METHOD_DECLARATION__TYPE_VARS: return N4JSPackage.GENERIC_DECLARATION__TYPE_VARS;
				default: return -1;
			}
		}
		if (baseClass == TypeProvidingElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == TypedElement.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.METHOD_DECLARATION__DECLARED_TYPE_REF: return N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF;
				case N4JSPackage.METHOD_DECLARATION__BOGUS_TYPE_REF: return N4JSPackage.TYPED_ELEMENT__BOGUS_TYPE_REF;
				default: return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == PropertyNameOwner.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.METHOD_DECLARATION__DECLARED_NAME: return N4JSPackage.PROPERTY_NAME_OWNER__DECLARED_NAME;
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
		if (baseClass == GenericDeclaration.class) {
			switch (baseFeatureID) {
				case N4JSPackage.GENERIC_DECLARATION__TYPE_VARS: return N4JSPackage.METHOD_DECLARATION__TYPE_VARS;
				default: return -1;
			}
		}
		if (baseClass == TypeProvidingElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == TypedElement.class) {
			switch (baseFeatureID) {
				case N4JSPackage.TYPED_ELEMENT__DECLARED_TYPE_REF: return N4JSPackage.METHOD_DECLARATION__DECLARED_TYPE_REF;
				case N4JSPackage.TYPED_ELEMENT__BOGUS_TYPE_REF: return N4JSPackage.METHOD_DECLARATION__BOGUS_TYPE_REF;
				default: return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == PropertyNameOwner.class) {
			switch (baseFeatureID) {
				case N4JSPackage.PROPERTY_NAME_OWNER__DECLARED_NAME: return N4JSPackage.METHOD_DECLARATION__DECLARED_NAME;
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
				case N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR___GET_NAME: return N4JSPackage.METHOD_DECLARATION___GET_NAME;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == GenericDeclaration.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == TypeProvidingElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.TYPE_PROVIDING_ELEMENT___GET_DECLARED_TYPE_REF: return N4JSPackage.METHOD_DECLARATION___GET_DECLARED_TYPE_REF;
				default: return -1;
			}
		}
		if (baseClass == TypedElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.NAMED_ELEMENT___GET_NAME: return N4JSPackage.METHOD_DECLARATION___GET_NAME;
				default: return -1;
			}
		}
		if (baseClass == PropertyNameOwner.class) {
			switch (baseOperationID) {
				case N4JSPackage.PROPERTY_NAME_OWNER___GET_NAME: return N4JSPackage.METHOD_DECLARATION___GET_NAME;
				case N4JSPackage.PROPERTY_NAME_OWNER___HAS_COMPUTED_PROPERTY_NAME: return N4JSPackage.METHOD_DECLARATION___HAS_COMPUTED_PROPERTY_NAME;
				case N4JSPackage.PROPERTY_NAME_OWNER___IS_VALID_NAME: return N4JSPackage.METHOD_DECLARATION___IS_VALID_NAME;
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
			case N4JSPackage.METHOD_DECLARATION___EXISTS_EXPLICIT_SUPER_CALL:
				return existsExplicitSuperCall();
			case N4JSPackage.METHOD_DECLARATION___GET_DEFINED_TYPE_ELEMENT:
				return getDefinedTypeElement();
			case N4JSPackage.METHOD_DECLARATION___IS_STATIC:
				return isStatic();
			case N4JSPackage.METHOD_DECLARATION___GET_NAME:
				return getName();
			case N4JSPackage.METHOD_DECLARATION___HAS_COMPUTED_PROPERTY_NAME:
				return hasComputedPropertyName();
			case N4JSPackage.METHOD_DECLARATION___IS_VALID_NAME:
				return isValidName();
		}
		return super.eInvoke(operationID, arguments);
	}

} //MethodDeclarationImpl
