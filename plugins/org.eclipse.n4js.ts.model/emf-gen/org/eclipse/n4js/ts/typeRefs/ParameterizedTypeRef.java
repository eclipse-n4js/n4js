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
package org.eclipse.n4js.ts.typeRefs;

import org.eclipse.emf.common.util.EList;

import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypingStrategy;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameterized Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Type reference to declared types which may be parameterized. This is
 * probably the most often used type reference. This base version always
 * uses nominal typing on use site, there is a sub class
 * {@link ParameterizedTypeRefStructural} which support structural typing
 * on use site. However, the latter is not allowed everywhere.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getDeclaredType <em>Declared Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getDeclaredTypeAsText <em>Declared Type As Text</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getDeclaredTypeArgs <em>Declared Type Args</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#isArrayTypeExpression <em>Array Type Expression</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#isArrayNTypeExpression <em>Array NType Expression</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getAstDeclaredTypeQualifier <em>Ast Declared Type Qualifier</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getASTNodeOptionalFieldStrategy <em>AST Node Optional Field Strategy</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getDefinedTypingStrategy <em>Defined Typing Strategy</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getParameterizedTypeRef()
 * @model
 * @generated
 */
public interface ParameterizedTypeRef extends BaseTypeRef {
	/**
	 * Returns the value of the '<em><b>Declared Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Type</em>' reference.
	 * @see #setDeclaredType(Type)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getParameterizedTypeRef_DeclaredType()
	 * @model
	 * @generated
	 */
	Type getDeclaredType();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getDeclaredType <em>Declared Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Type</em>' reference.
	 * @see #getDeclaredType()
	 * @generated
	 */
	void setDeclaredType(Type value);

	/**
	 * Returns the value of the '<em><b>Declared Type As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Type As Text</em>' attribute.
	 * @see #setDeclaredTypeAsText(String)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getParameterizedTypeRef_DeclaredTypeAsText()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	String getDeclaredTypeAsText();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getDeclaredTypeAsText <em>Declared Type As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Type As Text</em>' attribute.
	 * @see #getDeclaredTypeAsText()
	 * @generated
	 */
	void setDeclaredTypeAsText(String value);

	/**
	 * Returns the value of the '<em><b>Declared Type Args</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.typeRefs.TypeArgument}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The type arguments provided with this type reference, <b>NOT</b> including defaults for optional type parameters.
	 * Use {@link ParameterizedTypeRef#getTypeArgsWithDefaults() #getTypeArgsWithDefaults()} to also obtain those defaults.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Declared Type Args</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getParameterizedTypeRef_DeclaredTypeArgs()
	 * @model containment="true"
	 * @generated
	 */
	EList<TypeArgument> getDeclaredTypeArgs();

	/**
	 * Returns the value of the '<em><b>Array Type Expression</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  An array type in the AST, i.e. something like {@code let x: string[];}. Don't confuse with {@link ParameterizedTypeRef#isArrayNTypeExpression() arrayNTypeExpression}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Array Type Expression</em>' attribute.
	 * @see #setArrayTypeExpression(boolean)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getParameterizedTypeRef_ArrayTypeExpression()
	 * @model default="false" unique="false"
	 * @generated
	 */
	boolean isArrayTypeExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#isArrayTypeExpression <em>Array Type Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Array Type Expression</em>' attribute.
	 * @see #isArrayTypeExpression()
	 * @generated
	 */
	void setArrayTypeExpression(boolean value);

	/**
	 * Returns the value of the '<em><b>Array NType Expression</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  An ArrayN type in the AST, i.e. something like {@code let x: [string,number];}. Don't confuse with {@link ParameterizedTypeRef#isArrayTypeExpression() arrayTypeExpression}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Array NType Expression</em>' attribute.
	 * @see #setArrayNTypeExpression(boolean)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getParameterizedTypeRef_ArrayNTypeExpression()
	 * @model default="false" unique="false"
	 * @generated
	 */
	boolean isArrayNTypeExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#isArrayNTypeExpression <em>Array NType Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Array NType Expression</em>' attribute.
	 * @see #isArrayNTypeExpression()
	 * @generated
	 */
	void setArrayNTypeExpression(boolean value);

	/**
	 * Returns the value of the '<em><b>Ast Declared Type Qualifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If and only if this {@code ParameterizedTypeRef} is used as an AST node <em>and</em> ...
	 * <ol>
	 * <li>the declared type is, in the source code, referred to via the namespace of a namespace import, then this is non-null
	 * and points to that namespace.
	 * <li>the declared type is an enum literal, then this is non-null and points to the TEnum containing the literal.
	 * </ol>
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Ast Declared Type Qualifier</em>' reference.
	 * @see #setAstDeclaredTypeQualifier(Type)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getParameterizedTypeRef_AstDeclaredTypeQualifier()
	 * @model transient="true"
	 * @generated
	 */
	Type getAstDeclaredTypeQualifier();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getAstDeclaredTypeQualifier <em>Ast Declared Type Qualifier</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ast Declared Type Qualifier</em>' reference.
	 * @see #getAstDeclaredTypeQualifier()
	 * @generated
	 */
	void setAstDeclaredTypeQualifier(Type value);

	/**
	 * Returns the value of the '<em><b>AST Node Optional Field Strategy</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.ts.typeRefs.OptionalFieldStrategy}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  @see TypeRef#getASTOptionalFieldStrategy()
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>AST Node Optional Field Strategy</em>' attribute.
	 * @see org.eclipse.n4js.ts.typeRefs.OptionalFieldStrategy
	 * @see #setASTNodeOptionalFieldStrategy(OptionalFieldStrategy)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getParameterizedTypeRef_ASTNodeOptionalFieldStrategy()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	OptionalFieldStrategy getASTNodeOptionalFieldStrategy();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getASTNodeOptionalFieldStrategy <em>AST Node Optional Field Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>AST Node Optional Field Strategy</em>' attribute.
	 * @see org.eclipse.n4js.ts.typeRefs.OptionalFieldStrategy
	 * @see #getASTNodeOptionalFieldStrategy()
	 * @generated
	 */
	void setASTNodeOptionalFieldStrategy(OptionalFieldStrategy value);

	/**
	 * Returns the value of the '<em><b>Defined Typing Strategy</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.ts.types.TypingStrategy}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The defined (declared or inferred) typing strategy on use site, nominal typing by default. Could be changed to structural or structural
	 * field (via tilde or tilde-tilde operators).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Defined Typing Strategy</em>' attribute.
	 * @see org.eclipse.n4js.ts.types.TypingStrategy
	 * @see #setDefinedTypingStrategy(TypingStrategy)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getParameterizedTypeRef_DefinedTypingStrategy()
	 * @model unique="false"
	 * @generated
	 */
	TypingStrategy getDefinedTypingStrategy();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getDefinedTypingStrategy <em>Defined Typing Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Defined Typing Strategy</em>' attribute.
	 * @see org.eclipse.n4js.ts.types.TypingStrategy
	 * @see #getDefinedTypingStrategy()
	 * @generated
	 */
	void setDefinedTypingStrategy(TypingStrategy value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the actual typing strategy, that is either the defined typing strategy, or the typing strategy of the declared type.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TypingStrategy getTypingStrategy();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns all type arguments: those given in this type reference as well as
	 * default arguments of optional type parameters for which the argument is omitted.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<TypeArgument> getTypeArgsWithDefaults();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides {@link TypeRef#internalGetTypeRefAsString()}
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String internalGetTypeRefAsString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns true if type reference contains type arguments.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isParameterized();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Delegates to {@link Type#isGeneric()}
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isGeneric();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns true iff the given type reference is "raw", i.e. if it points to a generic type and has fewer type
	 * arguments than the generic type has mandatory(!) type parameters.
	 * 	 * Overrides {@link TypeRef#isRaw()}.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isRaw();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns true if typingStrategy is neither NOMINAL nor DEFAULT, and if is is not
	 * definition site.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isUseSiteStructuralTyping();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns true if typingStrategy of the declared type is STRUCTURAL or in case of Object literals.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isDefSiteStructuralTyping();

} // ParameterizedTypeRef
