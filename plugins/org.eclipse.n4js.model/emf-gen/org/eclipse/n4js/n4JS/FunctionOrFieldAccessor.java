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

import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.TypableElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function Or Field Accessor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base class for functions or getter/setter.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#getBody <em>Body</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#getImplicitArgumentsVariable <em>Implicit Arguments Variable</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getFunctionOrFieldAccessor()
 * @model abstract="true"
 * @generated
 */
public interface FunctionOrFieldAccessor extends AnnotableElement, VariableEnvironmentElement, ThisArgProvider, TypableElement {
	/**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' containment reference.
	 * @see #setBody(Block)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getFunctionOrFieldAccessor_Body()
	 * @model containment="true"
	 * @generated
	 */
	Block getBody();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#getBody <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(Block value);

	/**
	 * Returns the value of the '<em><b>Implicit Arguments Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implicit Arguments Variable</em>' reference.
	 * @see #setImplicitArgumentsVariable(TVariable)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getFunctionOrFieldAccessor_ImplicitArgumentsVariable()
	 * @model transient="true"
	 * @generated
	 */
	TVariable getImplicitArgumentsVariable();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#getImplicitArgumentsVariable <em>Implicit Arguments Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implicit Arguments Variable</em>' reference.
	 * @see #getImplicitArgumentsVariable()
	 * @generated
	 */
	void setImplicitArgumentsVariable(TVariable value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isReturnValueOptional();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Default implementation, always returns false (since accessors cannot be async), overridden in FunctionDefinition
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isAsync();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	IdentifiableElement getDefinedFunctionOrAccessor();

} // FunctionOrFieldAccessor
