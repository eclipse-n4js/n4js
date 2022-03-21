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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TFormal Parameter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TFormalParameterImpl#isVariadic <em>Variadic</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TFormalParameterImpl#getAstInitializer <em>Ast Initializer</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TFormalParameterImpl#isHasInitializerAssignment <em>Has Initializer Assignment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TFormalParameterImpl extends TAbstractVariableImpl implements TFormalParameter {
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
	 * The default value of the '{@link #getAstInitializer() <em>Ast Initializer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAstInitializer()
	 * @generated
	 * @ordered
	 */
	protected static final String AST_INITIALIZER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAstInitializer() <em>Ast Initializer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAstInitializer()
	 * @generated
	 * @ordered
	 */
	protected String astInitializer = AST_INITIALIZER_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TFormalParameterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TFORMAL_PARAMETER;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TFORMAL_PARAMETER__VARIADIC, oldVariadic, variadic));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getAstInitializer() {
		return astInitializer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAstInitializer(String newAstInitializer) {
		String oldAstInitializer = astInitializer;
		astInitializer = newAstInitializer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TFORMAL_PARAMETER__AST_INITIALIZER, oldAstInitializer, astInitializer));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TFORMAL_PARAMETER__HAS_INITIALIZER_ASSIGNMENT, oldHasInitializerAssignment, hasInitializerAssignment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean hasASTInitializer() {
		String _astInitializer = this.getAstInitializer();
		return (_astInitializer != null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isOptional() {
		EList<TFormalParameter> _switchResult = null;
		EObject _eContainer = this.eContainer();
		boolean _matched = false;
		if (_eContainer instanceof TFunction) {
			_matched=true;
			EObject _eContainer_1 = this.eContainer();
			_switchResult = ((TFunction) _eContainer_1).getFpars();
		}
		if (!_matched) {
			if (_eContainer instanceof FunctionTypeExprOrRef) {
				_matched=true;
				EObject _eContainer_1 = this.eContainer();
				_switchResult = ((FunctionTypeExprOrRef) _eContainer_1).getFpars();
			}
		}
		if (!_matched) {
			return false;
		}
		final EList<TFormalParameter> fpars = _switchResult;
		for (int i = fpars.indexOf(this); (i >= 0); i--) {
			{
				final TFormalParameter fpar = fpars.get(i);
				if ((fpar.isVariadic() || fpar.isHasInitializerAssignment())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isVariadicOrOptional() {
		return (this.isVariadic() || this.isOptional());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getFormalParameterAsTypesString() {
		final StringBuilder strb = new StringBuilder();
		boolean _isVariadic = this.isVariadic();
		if (_isVariadic) {
			strb.append("...");
		}
		TypeRef _typeRef = this.getTypeRef();
		boolean _tripleNotEquals = (_typeRef != null);
		if (_tripleNotEquals) {
			strb.append(this.getTypeRef().getTypeRefAsString());
		}
		else {
			strb.append("null");
		}
		boolean _isHasInitializerAssignment = this.isHasInitializerAssignment();
		if (_isHasInitializerAssignment) {
			strb.append("=\u2026");
		}
		return strb.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getFormalParameterAsString() {
		final StringBuilder strb = new StringBuilder();
		boolean _isVariadic = this.isVariadic();
		if (_isVariadic) {
			strb.append("...");
		}
		strb.append(this.getName());
		TypeRef _typeRef = this.getTypeRef();
		boolean _tripleNotEquals = (_typeRef != null);
		if (_tripleNotEquals) {
			strb.append(": ").append(this.getTypeRef().getTypeRefAsString());
		}
		boolean _isHasInitializerAssignment = this.isHasInitializerAssignment();
		if (_isHasInitializerAssignment) {
			strb.append("=\u2026");
		}
		return strb.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.TFORMAL_PARAMETER__VARIADIC:
				return isVariadic();
			case TypesPackage.TFORMAL_PARAMETER__AST_INITIALIZER:
				return getAstInitializer();
			case TypesPackage.TFORMAL_PARAMETER__HAS_INITIALIZER_ASSIGNMENT:
				return isHasInitializerAssignment();
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
			case TypesPackage.TFORMAL_PARAMETER__VARIADIC:
				setVariadic((Boolean)newValue);
				return;
			case TypesPackage.TFORMAL_PARAMETER__AST_INITIALIZER:
				setAstInitializer((String)newValue);
				return;
			case TypesPackage.TFORMAL_PARAMETER__HAS_INITIALIZER_ASSIGNMENT:
				setHasInitializerAssignment((Boolean)newValue);
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
			case TypesPackage.TFORMAL_PARAMETER__VARIADIC:
				setVariadic(VARIADIC_EDEFAULT);
				return;
			case TypesPackage.TFORMAL_PARAMETER__AST_INITIALIZER:
				setAstInitializer(AST_INITIALIZER_EDEFAULT);
				return;
			case TypesPackage.TFORMAL_PARAMETER__HAS_INITIALIZER_ASSIGNMENT:
				setHasInitializerAssignment(HAS_INITIALIZER_ASSIGNMENT_EDEFAULT);
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
			case TypesPackage.TFORMAL_PARAMETER__VARIADIC:
				return variadic != VARIADIC_EDEFAULT;
			case TypesPackage.TFORMAL_PARAMETER__AST_INITIALIZER:
				return AST_INITIALIZER_EDEFAULT == null ? astInitializer != null : !AST_INITIALIZER_EDEFAULT.equals(astInitializer);
			case TypesPackage.TFORMAL_PARAMETER__HAS_INITIALIZER_ASSIGNMENT:
				return hasInitializerAssignment != HAS_INITIALIZER_ASSIGNMENT_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case TypesPackage.TFORMAL_PARAMETER___HAS_AST_INITIALIZER:
				return hasASTInitializer();
			case TypesPackage.TFORMAL_PARAMETER___IS_OPTIONAL:
				return isOptional();
			case TypesPackage.TFORMAL_PARAMETER___IS_VARIADIC_OR_OPTIONAL:
				return isVariadicOrOptional();
			case TypesPackage.TFORMAL_PARAMETER___GET_FORMAL_PARAMETER_AS_TYPES_STRING:
				return getFormalParameterAsTypesString();
			case TypesPackage.TFORMAL_PARAMETER___GET_FORMAL_PARAMETER_AS_STRING:
				return getFormalParameterAsString();
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
		result.append(" (variadic: ");
		result.append(variadic);
		result.append(", astInitializer: ");
		result.append(astInitializer);
		result.append(", hasInitializerAssignment: ");
		result.append(hasInitializerAssignment);
		result.append(')');
		return result.toString();
	}

} //TFormalParameterImpl
