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

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.TFunction;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 *  A function definition is either a FunctionDeclaration, a FunctionExpression or a MethodDeclaration.
 * Note that, since an anonymous function expression has no name, the function definition is not a named element.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.FunctionDefinition#getFpars <em>Fpars</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.FunctionDefinition#getDeclaredReturnTypeRef <em>Declared Return Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.FunctionDefinition#getDeclaredReturnTypeRefInAST <em>Declared Return Type Ref In AST</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.FunctionDefinition#isGenerator <em>Generator</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.FunctionDefinition#isDeclaredAsync <em>Declared Async</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getFunctionDefinition()
 * @model abstract="true"
 * @generated
 */
public interface FunctionDefinition extends FunctionOrFieldAccessor, TypeDefiningElement, VersionedElement {
	/**
	 * Returns the value of the '<em><b>Fpars</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.FormalParameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fpars</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getFunctionDefinition_Fpars()
	 * @model containment="true"
	 * @generated
	 */
	EList<FormalParameter> getFpars();

	/**
	 * Returns the value of the '<em><b>Declared Return Type Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Same as {@link #getDeclaredReturnTypeRefInAST()}, but with type aliases being resolved (if any).
	 * The returned type reference may or may not be contained in the AST.
	 * This is set during post-processing by {@code TypeRefProcessor}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Declared Return Type Ref</em>' reference.
	 * @see #setDeclaredReturnTypeRef(TypeRef)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getFunctionDefinition_DeclaredReturnTypeRef()
	 * @model transient="true"
	 * @generated
	 */
	TypeRef getDeclaredReturnTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.FunctionDefinition#getDeclaredReturnTypeRef <em>Declared Return Type Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Return Type Ref</em>' reference.
	 * @see #getDeclaredReturnTypeRef()
	 * @generated
	 */
	void setDeclaredReturnTypeRef(TypeRef value);

	/**
	 * Returns the value of the '<em><b>Declared Return Type Ref In AST</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Developer-provided hint for the return type of this FunctionDefinition.
	 * In case it's not provided, this reference remains null.
	 * In contrast, reference definedType contains a TFunction whose returnTypeRef is always non-null irrespective of whether the hint was provided or not.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Declared Return Type Ref In AST</em>' containment reference.
	 * @see #setDeclaredReturnTypeRefInAST(TypeRef)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getFunctionDefinition_DeclaredReturnTypeRefInAST()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getDeclaredReturnTypeRefInAST();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.FunctionDefinition#getDeclaredReturnTypeRefInAST <em>Declared Return Type Ref In AST</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Return Type Ref In AST</em>' containment reference.
	 * @see #getDeclaredReturnTypeRefInAST()
	 * @generated
	 */
	void setDeclaredReturnTypeRefInAST(TypeRef value);

	/**
	 * Returns the value of the '<em><b>Generator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether the function is a generator
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Generator</em>' attribute.
	 * @see #setGenerator(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getFunctionDefinition_Generator()
	 * @model unique="false"
	 * @generated
	 */
	boolean isGenerator();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.FunctionDefinition#isGenerator <em>Generator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generator</em>' attribute.
	 * @see #isGenerator()
	 * @generated
	 */
	void setGenerator(boolean value);

	/**
	 * Returns the value of the '<em><b>Declared Async</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Whether the function has been defined with the async keyword. In order to query if a function definition is
	 * async, use isAsync as this is maybe derived from other fields.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Declared Async</em>' attribute.
	 * @see #setDeclaredAsync(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getFunctionDefinition_DeclaredAsync()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredAsync();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.FunctionDefinition#isDeclaredAsync <em>Declared Async</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Async</em>' attribute.
	 * @see #isDeclaredAsync()
	 * @generated
	 */
	void setDeclaredAsync(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tells if the return value is optional.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isReturnValueOptional();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Default implementation just returns declaredAsync value, also overrides default implementation in FunctionOrFieldAcccessor
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isAsync();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Convenience method returning the 'definedType' if it is a TFunction, otherwise <code>null</code>.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TFunction getDefinedFunction();

} // FunctionDefinition
