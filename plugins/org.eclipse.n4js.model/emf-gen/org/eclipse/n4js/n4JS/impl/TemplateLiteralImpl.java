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

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.Literal;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.TemplateLiteral;

import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Template Literal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.TemplateLiteralImpl#getSegments <em>Segments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TemplateLiteralImpl extends PrimaryExpressionImpl implements TemplateLiteral {
	/**
	 * The cached value of the '{@link #getSegments() <em>Segments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSegments()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> segments;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TemplateLiteralImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.TEMPLATE_LITERAL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Expression> getSegments() {
		if (segments == null) {
			segments = new EObjectContainmentEList<Expression>(Expression.class, this, N4JSPackage.TEMPLATE_LITERAL__SEGMENTS);
		}
		return segments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getValueAsString() {
		final StringBuilder result = new StringBuilder("`");
		final Function1<Expression, StringBuilder> _function = new Function1<Expression, StringBuilder>() {
			public StringBuilder apply(final Expression expr) {
				StringBuilder _switchResult = null;
				boolean _matched = false;
				if (expr instanceof Literal) {
					_matched=true;
					_switchResult = result.append(TemplateLiteralImpl.this.getValueAsString());
				}
				if (!_matched) {
					_switchResult = result.append("<<").append(TemplateLiteralImpl.this.eClass().getName()).append(">>");
				}
				return _switchResult;
			}
		};
		final Function1<Expression, StringBuilder> appender = _function;
		final Function2<Boolean, Expression, Boolean> _function_1 = new Function2<Boolean, Expression, Boolean>() {
			public Boolean apply(final Boolean isRaw, final Expression expression) {
				if ((!(isRaw).booleanValue())) {
					result.append("${");
					appender.apply(expression);
					result.append("}");
				}
				else {
					appender.apply(expression);
				}
				return Boolean.valueOf((!(isRaw).booleanValue()));
			}
		};
		IterableExtensions.<Expression, Boolean>fold(this.getSegments(), Boolean.valueOf(true), _function_1);
		result.append("`");
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.TEMPLATE_LITERAL__SEGMENTS:
				return ((InternalEList<?>)getSegments()).basicRemove(otherEnd, msgs);
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
			case N4JSPackage.TEMPLATE_LITERAL__SEGMENTS:
				return getSegments();
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
			case N4JSPackage.TEMPLATE_LITERAL__SEGMENTS:
				getSegments().clear();
				getSegments().addAll((Collection<? extends Expression>)newValue);
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
			case N4JSPackage.TEMPLATE_LITERAL__SEGMENTS:
				getSegments().clear();
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
			case N4JSPackage.TEMPLATE_LITERAL__SEGMENTS:
				return segments != null && !segments.isEmpty();
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
			case N4JSPackage.TEMPLATE_LITERAL___GET_VALUE_AS_STRING:
				return getValueAsString();
		}
		return super.eInvoke(operationID, arguments);
	}

} //TemplateLiteralImpl
