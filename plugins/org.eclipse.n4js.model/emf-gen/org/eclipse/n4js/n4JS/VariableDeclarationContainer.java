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
package org.eclipse.n4js.n4JS;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable Declaration Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Base class for elements containing variable declarations.
 * <p>
 * IMPORTANT: the variable declarations need not be <em>directly</em> contained! In order to support destructuring, a
 * {@link VariableBinding} and other destructuring-related nodes might appear in between. To get from a
 * {@link VariableDeclaration} to its <code>VariableDeclarationContainer</code> use method
 * {@link org.eclipse.n4js.n4JS.N4JSASTUtils#getVariableDeclarationContainer(VariableDeclaration)}.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.VariableDeclarationContainer#getVarDeclsOrBindings <em>Var Decls Or Bindings</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.VariableDeclarationContainer#getVarStmtKeyword <em>Var Stmt Keyword</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getVariableDeclarationContainer()
 * @model abstract="true"
 * @generated
 */
public interface VariableDeclarationContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Var Decls Or Bindings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.VariableDeclarationOrBinding}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Var Decls Or Bindings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Var Decls Or Bindings</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getVariableDeclarationContainer_VarDeclsOrBindings()
	 * @model containment="true"
	 * @generated
	 */
	EList<VariableDeclarationOrBinding> getVarDeclsOrBindings();

	/**
	 * Returns the value of the '<em><b>Var Stmt Keyword</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.n4JS.VariableStatementKeyword}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Var Stmt Keyword</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Var Stmt Keyword</em>' attribute.
	 * @see org.eclipse.n4js.n4JS.VariableStatementKeyword
	 * @see #setVarStmtKeyword(VariableStatementKeyword)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getVariableDeclarationContainer_VarStmtKeyword()
	 * @model unique="false"
	 * @generated
	 */
	VariableStatementKeyword getVarStmtKeyword();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.VariableDeclarationContainer#getVarStmtKeyword <em>Var Stmt Keyword</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Var Stmt Keyword</em>' attribute.
	 * @see org.eclipse.n4js.n4JS.VariableStatementKeyword
	 * @see #getVarStmtKeyword()
	 * @generated
	 */
	void setVarStmtKeyword(VariableStatementKeyword value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.n4JS.VariableDeclarationOrBinding%>> _varDeclsOrBindings = this.getVarDeclsOrBindings();\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.n4JS.VariableDeclarationOrBinding%>, <%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.n4JS.VariableDeclaration%>>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.n4JS.VariableDeclarationOrBinding%>, <%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.n4JS.VariableDeclaration%>>>()\n{\n\tpublic <%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.n4JS.VariableDeclaration%>> apply(final <%org.eclipse.n4js.n4JS.VariableDeclarationOrBinding%> it)\n\t{\n\t\treturn it.getVariableDeclarations();\n\t}\n};\n<%org.eclipse.emf.common.util.EList%><<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.n4JS.VariableDeclaration%>>> _map = <%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%>.<<%org.eclipse.n4js.n4JS.VariableDeclarationOrBinding%>, <%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.n4JS.VariableDeclaration%>>>map(_varDeclsOrBindings, _function);\n<%java.lang.Iterable%><<%org.eclipse.n4js.n4JS.VariableDeclaration%>> _flatten = <%com.google.common.collect.Iterables%>.<<%org.eclipse.n4js.n4JS.VariableDeclaration%>>concat(_map);\nreturn <%org.eclipse.emf.common.util.ECollections%>.<<%org.eclipse.n4js.n4JS.VariableDeclaration%>>toEList(_flatten);'"
	 * @generated
	 */
	EList<VariableDeclaration> getVarDecl();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _switchResult = false;\n<%org.eclipse.n4js.n4JS.VariableStatementKeyword%> _varStmtKeyword = this.getVarStmtKeyword();\nif (_varStmtKeyword != null)\n{\n\tswitch (_varStmtKeyword)\n\t{\n\t\tcase LET:\n\t\t\t_switchResult = true;\n\t\t\tbreak;\n\t\tcase CONST:\n\t\t\t_switchResult = true;\n\t\t\tbreak;\n\t\tcase VAR:\n\t\t\t_switchResult = false;\n\t\t\tbreak;\n\t\tdefault:\n\t\t\t<%org.eclipse.n4js.n4JS.VariableStatementKeyword%> _varStmtKeyword_1 = this.getVarStmtKeyword();\n\t\t\t<%java.lang.String%> _plus = (\"unsupported enum literal: \" + _varStmtKeyword_1);\n\t\t\tthrow new <%java.lang.UnsupportedOperationException%>(_plus);\n\t}\n}\nelse\n{\n\t<%org.eclipse.n4js.n4JS.VariableStatementKeyword%> _varStmtKeyword_1 = this.getVarStmtKeyword();\n\t<%java.lang.String%> _plus = (\"unsupported enum literal: \" + _varStmtKeyword_1);\n\tthrow new <%java.lang.UnsupportedOperationException%>(_plus);\n}\nreturn _switchResult;'"
	 * @generated
	 */
	boolean isBlockScoped();

} // VariableDeclarationContainer
