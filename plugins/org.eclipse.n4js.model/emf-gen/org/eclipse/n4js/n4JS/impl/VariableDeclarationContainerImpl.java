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

import com.google.common.collect.Iterables;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;

import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableDeclarationContainer;
import org.eclipse.n4js.n4JS.VariableDeclarationOrBinding;
import org.eclipse.n4js.n4JS.VariableStatementKeyword;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Variable Declaration Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.VariableDeclarationContainerImpl#getVarDeclsOrBindings <em>Var Decls Or Bindings</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.VariableDeclarationContainerImpl#getVarStmtKeyword <em>Var Stmt Keyword</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class VariableDeclarationContainerImpl extends ProxyResolvingEObjectImpl implements VariableDeclarationContainer {
	/**
	 * The cached value of the '{@link #getVarDeclsOrBindings() <em>Var Decls Or Bindings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVarDeclsOrBindings()
	 * @generated
	 * @ordered
	 */
	protected EList<VariableDeclarationOrBinding> varDeclsOrBindings;

	/**
	 * The default value of the '{@link #getVarStmtKeyword() <em>Var Stmt Keyword</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVarStmtKeyword()
	 * @generated
	 * @ordered
	 */
	protected static final VariableStatementKeyword VAR_STMT_KEYWORD_EDEFAULT = VariableStatementKeyword.VAR;

	/**
	 * The cached value of the '{@link #getVarStmtKeyword() <em>Var Stmt Keyword</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVarStmtKeyword()
	 * @generated
	 * @ordered
	 */
	protected VariableStatementKeyword varStmtKeyword = VAR_STMT_KEYWORD_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VariableDeclarationContainerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.VARIABLE_DECLARATION_CONTAINER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VariableDeclarationOrBinding> getVarDeclsOrBindings() {
		if (varDeclsOrBindings == null) {
			varDeclsOrBindings = new EObjectContainmentEList<VariableDeclarationOrBinding>(VariableDeclarationOrBinding.class, this, N4JSPackage.VARIABLE_DECLARATION_CONTAINER__VAR_DECLS_OR_BINDINGS);
		}
		return varDeclsOrBindings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VariableStatementKeyword getVarStmtKeyword() {
		return varStmtKeyword;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVarStmtKeyword(VariableStatementKeyword newVarStmtKeyword) {
		VariableStatementKeyword oldVarStmtKeyword = varStmtKeyword;
		varStmtKeyword = newVarStmtKeyword == null ? VAR_STMT_KEYWORD_EDEFAULT : newVarStmtKeyword;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.VARIABLE_DECLARATION_CONTAINER__VAR_STMT_KEYWORD, oldVarStmtKeyword, varStmtKeyword));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VariableDeclaration> getVarDecl() {
		final Function1<VariableDeclarationOrBinding, EList<VariableDeclaration>> _function = new Function1<VariableDeclarationOrBinding, EList<VariableDeclaration>>() {
			public EList<VariableDeclaration> apply(final VariableDeclarationOrBinding it) {
				return it.getVariableDeclarations();
			}
		};
		return ECollections.<VariableDeclaration>toEList(Iterables.<VariableDeclaration>concat(XcoreEListExtensions.<VariableDeclarationOrBinding, EList<VariableDeclaration>>map(this.getVarDeclsOrBindings(), _function)));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isBlockScoped() {
		boolean _switchResult = false;
		VariableStatementKeyword _varStmtKeyword = this.getVarStmtKeyword();
		if (_varStmtKeyword != null) {
			switch (_varStmtKeyword) {
				case LET:
					_switchResult = true;
					break;
				case CONST:
					_switchResult = true;
					break;
				case VAR:
					_switchResult = false;
					break;
				default:
					VariableStatementKeyword _varStmtKeyword_1 = this.getVarStmtKeyword();
					String _plus = ("unsupported enum literal: " + _varStmtKeyword_1);
					throw new UnsupportedOperationException(_plus);
			}
		}
		else {
			VariableStatementKeyword _varStmtKeyword_1 = this.getVarStmtKeyword();
			String _plus = ("unsupported enum literal: " + _varStmtKeyword_1);
			throw new UnsupportedOperationException(_plus);
		}
		return _switchResult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.VARIABLE_DECLARATION_CONTAINER__VAR_DECLS_OR_BINDINGS:
				return ((InternalEList<?>)getVarDeclsOrBindings()).basicRemove(otherEnd, msgs);
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
			case N4JSPackage.VARIABLE_DECLARATION_CONTAINER__VAR_DECLS_OR_BINDINGS:
				return getVarDeclsOrBindings();
			case N4JSPackage.VARIABLE_DECLARATION_CONTAINER__VAR_STMT_KEYWORD:
				return getVarStmtKeyword();
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
			case N4JSPackage.VARIABLE_DECLARATION_CONTAINER__VAR_DECLS_OR_BINDINGS:
				getVarDeclsOrBindings().clear();
				getVarDeclsOrBindings().addAll((Collection<? extends VariableDeclarationOrBinding>)newValue);
				return;
			case N4JSPackage.VARIABLE_DECLARATION_CONTAINER__VAR_STMT_KEYWORD:
				setVarStmtKeyword((VariableStatementKeyword)newValue);
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
			case N4JSPackage.VARIABLE_DECLARATION_CONTAINER__VAR_DECLS_OR_BINDINGS:
				getVarDeclsOrBindings().clear();
				return;
			case N4JSPackage.VARIABLE_DECLARATION_CONTAINER__VAR_STMT_KEYWORD:
				setVarStmtKeyword(VAR_STMT_KEYWORD_EDEFAULT);
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
			case N4JSPackage.VARIABLE_DECLARATION_CONTAINER__VAR_DECLS_OR_BINDINGS:
				return varDeclsOrBindings != null && !varDeclsOrBindings.isEmpty();
			case N4JSPackage.VARIABLE_DECLARATION_CONTAINER__VAR_STMT_KEYWORD:
				return varStmtKeyword != VAR_STMT_KEYWORD_EDEFAULT;
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
			case N4JSPackage.VARIABLE_DECLARATION_CONTAINER___GET_VAR_DECL:
				return getVarDecl();
			case N4JSPackage.VARIABLE_DECLARATION_CONTAINER___IS_BLOCK_SCOPED:
				return isBlockScoped();
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
		result.append(" (varStmtKeyword: ");
		result.append(varStmtKeyword);
		result.append(')');
		return result.toString();
	}

} //VariableDeclarationContainerImpl
