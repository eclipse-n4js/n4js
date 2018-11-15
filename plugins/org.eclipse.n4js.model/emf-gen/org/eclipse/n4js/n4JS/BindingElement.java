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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Binding Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.BindingElement#isRest <em>Rest</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.BindingElement#getVarDecl <em>Var Decl</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.BindingElement#getNestedPattern <em>Nested Pattern</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.BindingElement#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getBindingElement()
 * @model
 * @generated
 */
public interface BindingElement extends ControlFlowElement {
	/**
	 * Returns the value of the '<em><b>Rest</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Corresponds to spread in ArrayLiterals (but called 'rest' in ES6 specification within array binding patterns).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Rest</em>' attribute.
	 * @see #setRest(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getBindingElement_Rest()
	 * @model unique="false"
	 * @generated
	 */
	boolean isRest();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.BindingElement#isRest <em>Rest</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rest</em>' attribute.
	 * @see #isRest()
	 * @generated
	 */
	void setRest(boolean value);

	/**
	 * Returns the value of the '<em><b>Var Decl</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Var Decl</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Var Decl</em>' containment reference.
	 * @see #setVarDecl(VariableDeclaration)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getBindingElement_VarDecl()
	 * @model containment="true"
	 * @generated
	 */
	VariableDeclaration getVarDecl();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.BindingElement#getVarDecl <em>Var Decl</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Var Decl</em>' containment reference.
	 * @see #getVarDecl()
	 * @generated
	 */
	void setVarDecl(VariableDeclaration value);

	/**
	 * Returns the value of the '<em><b>Nested Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nested Pattern</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nested Pattern</em>' containment reference.
	 * @see #setNestedPattern(BindingPattern)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getBindingElement_NestedPattern()
	 * @model containment="true"
	 * @generated
	 */
	BindingPattern getNestedPattern();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.BindingElement#getNestedPattern <em>Nested Pattern</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nested Pattern</em>' containment reference.
	 * @see #getNestedPattern()
	 * @generated
	 */
	void setNestedPattern(BindingPattern value);

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getBindingElement_Expression()
	 * @model containment="true"
	 * @generated
	 */
	Expression getExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.BindingElement#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(Expression value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tells if this {@link BindingElement} is an "empty" element, corresponding to an {@link ArrayPadding} element in
	 * an {@link ArrayLiteral}.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isElision();

} // BindingElement
