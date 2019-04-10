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

import com.google.common.base.Predicate;

import com.google.common.collect.Iterators;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.VariableEnvironmentElement;
import org.eclipse.n4js.n4JS.YieldExpression;

import org.eclipse.n4js.utils.EcoreUtilN4;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IteratorExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Block</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.BlockImpl#getStatements <em>Statements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BlockImpl extends StatementImpl implements Block {
	/**
	 * The cached value of the '{@link #getStatements() <em>Statements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatements()
	 * @generated
	 * @ordered
	 */
	protected EList<Statement> statements;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BlockImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.BLOCK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Statement> getStatements() {
		if (statements == null) {
			statements = new EObjectContainmentEList<Statement>(Statement.class, this, N4JSPackage.BLOCK__STATEMENTS);
		}
		return statements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean appliesOnlyToBlockScopedElements() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterator<Expression> getAllExpressions() {
		final Predicate<EObject> _function = new Predicate<EObject>() {
			public boolean apply(final EObject it) {
				return (!(it instanceof FunctionDefinition));
			}
		};
		return Iterators.<Expression>filter(EcoreUtilN4.getAllContentsFiltered(this, _function), Expression.class);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterator<YieldExpression> getAllYieldExpressions() {
		return Iterators.<YieldExpression>filter(this.getAllExpressions(), YieldExpression.class);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterator<YieldExpression> getAllVoidYieldExpressions() {
		final Function1<YieldExpression, Boolean> _function = new Function1<YieldExpression, Boolean>() {
			public Boolean apply(final YieldExpression it) {
				Expression _expression = it.getExpression();
				return Boolean.valueOf((_expression == null));
			}
		};
		return IteratorExtensions.<YieldExpression>filter(this.getAllYieldExpressions(), _function);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterator<YieldExpression> getAllNonVoidYieldExpressions() {
		final Function1<YieldExpression, Boolean> _function = new Function1<YieldExpression, Boolean>() {
			public Boolean apply(final YieldExpression it) {
				Expression _expression = it.getExpression();
				return Boolean.valueOf((_expression != null));
			}
		};
		return IteratorExtensions.<YieldExpression>filter(this.getAllYieldExpressions(), _function);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean hasNonVoidYield() {
		boolean _isEmpty = IteratorExtensions.isEmpty(this.getAllNonVoidYieldExpressions());
		return (!_isEmpty);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterator<Statement> getAllStatements() {
		final Predicate<EObject> _function = new Predicate<EObject>() {
			public boolean apply(final EObject it) {
				return (!((it instanceof Expression) || (it instanceof FunctionDefinition)));
			}
		};
		return Iterators.<Statement>filter(EcoreUtilN4.getAllContentsFiltered(this, _function), Statement.class);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterator<ReturnStatement> getAllReturnStatements() {
		return Iterators.<ReturnStatement>filter(this.getAllStatements(), ReturnStatement.class);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterator<ReturnStatement> getAllNonVoidReturnStatements() {
		final Function1<ReturnStatement, Boolean> _function = new Function1<ReturnStatement, Boolean>() {
			public Boolean apply(final ReturnStatement it) {
				Expression _expression = it.getExpression();
				return Boolean.valueOf((_expression != null));
			}
		};
		return IteratorExtensions.<ReturnStatement>filter(this.getAllReturnStatements(), _function);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterator<ReturnStatement> getAllVoidReturnStatements() {
		final Function1<ReturnStatement, Boolean> _function = new Function1<ReturnStatement, Boolean>() {
			public Boolean apply(final ReturnStatement it) {
				Expression _expression = it.getExpression();
				return Boolean.valueOf((_expression == null));
			}
		};
		return IteratorExtensions.<ReturnStatement>filter(this.getAllReturnStatements(), _function);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean hasNonVoidReturn() {
		boolean _isEmpty = IteratorExtensions.isEmpty(this.getAllNonVoidReturnStatements());
		return (!_isEmpty);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.BLOCK__STATEMENTS:
				return ((InternalEList<?>)getStatements()).basicRemove(otherEnd, msgs);
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
			case N4JSPackage.BLOCK__STATEMENTS:
				return getStatements();
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
			case N4JSPackage.BLOCK__STATEMENTS:
				getStatements().clear();
				getStatements().addAll((Collection<? extends Statement>)newValue);
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
			case N4JSPackage.BLOCK__STATEMENTS:
				getStatements().clear();
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
			case N4JSPackage.BLOCK__STATEMENTS:
				return statements != null && !statements.isEmpty();
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
		if (baseClass == VariableEnvironmentElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.VARIABLE_ENVIRONMENT_ELEMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS: return N4JSPackage.BLOCK___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;
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
			case N4JSPackage.BLOCK___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS:
				return appliesOnlyToBlockScopedElements();
			case N4JSPackage.BLOCK___GET_ALL_EXPRESSIONS:
				return getAllExpressions();
			case N4JSPackage.BLOCK___GET_ALL_YIELD_EXPRESSIONS:
				return getAllYieldExpressions();
			case N4JSPackage.BLOCK___GET_ALL_VOID_YIELD_EXPRESSIONS:
				return getAllVoidYieldExpressions();
			case N4JSPackage.BLOCK___GET_ALL_NON_VOID_YIELD_EXPRESSIONS:
				return getAllNonVoidYieldExpressions();
			case N4JSPackage.BLOCK___HAS_NON_VOID_YIELD:
				return hasNonVoidYield();
			case N4JSPackage.BLOCK___GET_ALL_STATEMENTS:
				return getAllStatements();
			case N4JSPackage.BLOCK___GET_ALL_RETURN_STATEMENTS:
				return getAllReturnStatements();
			case N4JSPackage.BLOCK___GET_ALL_NON_VOID_RETURN_STATEMENTS:
				return getAllNonVoidReturnStatements();
			case N4JSPackage.BLOCK___GET_ALL_VOID_RETURN_STATEMENTS:
				return getAllVoidReturnStatements();
			case N4JSPackage.BLOCK___HAS_NON_VOID_RETURN:
				return hasNonVoidReturn();
		}
		return super.eInvoke(operationID, arguments);
	}

} //BlockImpl
