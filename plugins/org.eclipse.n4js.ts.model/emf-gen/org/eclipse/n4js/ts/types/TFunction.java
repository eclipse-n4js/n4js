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
package org.eclipse.n4js.ts.types;

import org.eclipse.emf.common.util.EList;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TFunction</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Represents a function declaration. In JavaScript, functions are first-class objects, hence
 * a function declaration also is a type declaration.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TFunction#isExternal <em>External</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TFunction#getFpars <em>Fpars</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TFunction#isReturnValueMarkedOptional <em>Return Value Marked Optional</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TFunction#getReturnTypeRef <em>Return Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TFunction#getTypeVars <em>Type Vars</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TFunction#getDeclaredThisType <em>Declared This Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TFunction#isDeclaredAsync <em>Declared Async</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TFunction#isDeclaredGenerator <em>Declared Generator</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TFunction#isConstructor <em>Constructor</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTFunction()
 * @model
 * @generated
 */
public interface TFunction extends DeclaredTypeWithAccessModifier, SyntaxRelatedTElement, TVersionable {
	/**
	 * Returns the value of the '<em><b>External</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>External</em>' attribute.
	 * @see #setExternal(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTFunction_External()
	 * @model unique="false"
	 * @generated
	 */
	boolean isExternal();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TFunction#isExternal <em>External</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>External</em>' attribute.
	 * @see #isExternal()
	 * @generated
	 */
	void setExternal(boolean value);

	/**
	 * Returns the value of the '<em><b>Fpars</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TFormalParameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Formal parameters
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Fpars</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTFunction_Fpars()
	 * @model containment="true"
	 * @generated
	 */
	EList<TFormalParameter> getFpars();

	/**
	 * Returns the value of the '<em><b>Return Value Marked Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Explicitly marks the return value of this TFunction as optional. This is only used for TFunctions that are
	 * created programmatically. Those that appear in the AST (only possible in Types.xtext language) will instead have
	 * a 'returnTypeRef' with 'followedByQuestionMark' set to <code>true</code>.
	 * This will probably become obsolete once we implement undefined/null analysis.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Return Value Marked Optional</em>' attribute.
	 * @see #setReturnValueMarkedOptional(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTFunction_ReturnValueMarkedOptional()
	 * @model unique="false"
	 * @generated
	 */
	boolean isReturnValueMarkedOptional();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TFunction#isReturnValueMarkedOptional <em>Return Value Marked Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Return Value Marked Optional</em>' attribute.
	 * @see #isReturnValueMarkedOptional()
	 * @generated
	 */
	void setReturnValueMarkedOptional(boolean value);

	/**
	 * Returns the value of the '<em><b>Return Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Return type
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Return Type Ref</em>' containment reference.
	 * @see #setReturnTypeRef(TypeRef)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTFunction_ReturnTypeRef()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getReturnTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TFunction#getReturnTypeRef <em>Return Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Return Type Ref</em>' containment reference.
	 * @see #getReturnTypeRef()
	 * @generated
	 */
	void setReturnTypeRef(TypeRef value);

	/**
	 * Returns the value of the '<em><b>Type Vars</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TypeVariable}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Type parameters of a generic function or method. Do not confuse this with the formal parameters.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type Vars</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTFunction_TypeVars()
	 * @model containment="true"
	 * @generated
	 */
	EList<TypeVariable> getTypeVars();

	/**
	 * Returns the value of the '<em><b>Declared This Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * optional thisType declaration (@This)
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Declared This Type</em>' containment reference.
	 * @see #setDeclaredThisType(TypeRef)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTFunction_DeclaredThisType()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getDeclaredThisType();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TFunction#getDeclaredThisType <em>Declared This Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared This Type</em>' containment reference.
	 * @see #getDeclaredThisType()
	 * @generated
	 */
	void setDeclaredThisType(TypeRef value);

	/**
	 * Returns the value of the '<em><b>Declared Async</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * optional async modifier
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Declared Async</em>' attribute.
	 * @see #setDeclaredAsync(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTFunction_DeclaredAsync()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredAsync();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TFunction#isDeclaredAsync <em>Declared Async</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Async</em>' attribute.
	 * @see #isDeclaredAsync()
	 * @generated
	 */
	void setDeclaredAsync(boolean value);

	/**
	 * Returns the value of the '<em><b>Declared Generator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * optional generator modifier
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Declared Generator</em>' attribute.
	 * @see #setDeclaredGenerator(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTFunction_DeclaredGenerator()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredGenerator();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TFunction#isDeclaredGenerator <em>Declared Generator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Generator</em>' attribute.
	 * @see #isDeclaredGenerator()
	 * @generated
	 */
	void setDeclaredGenerator(boolean value);

	/**
	 * Returns the value of the '<em><b>Constructor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Whether this function is intentionally to be used as a constructor
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Constructor</em>' attribute.
	 * @see #setConstructor(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTFunction_Constructor()
	 * @model unique="false"
	 * @generated
	 */
	boolean isConstructor();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TFunction#isConstructor <em>Constructor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constructor</em>' attribute.
	 * @see #isConstructor()
	 * @generated
	 */
	void setConstructor(boolean value);

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
	 * Tells whether this function is a method that represents a callable constructor.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isCallableConstructor();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the formal parameter corresponding to the argument at index 'argIndex' in a function call
	 * or 'null' if 'argIndex' is invalid. This method takes into account optional and variadic parameters.
	 * <!-- end-model-doc -->
	 * @model unique="false" argIndexUnique="false"
	 * @generated
	 */
	TFormalParameter getFparForArgIdx(int argIndex);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns string representation of this function similar according to the N4JS syntax.
	 * This includes formal parameters and return type (if declared), but excludes annotations.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getFunctionAsString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * There may be sub-types of a function type unless explicitly stated differently
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isFinal();

} // TFunction
