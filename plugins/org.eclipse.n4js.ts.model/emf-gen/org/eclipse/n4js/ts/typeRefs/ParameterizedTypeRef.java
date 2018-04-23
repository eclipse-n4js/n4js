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

import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
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
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getTypeArgs <em>Type Args</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#isArrayTypeLiteral <em>Array Type Literal</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getAstNamespace <em>Ast Namespace</em>}</li>
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
	 * <p>
	 * If the meaning of the '<em>Declared Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
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
	 * Returns the value of the '<em><b>Type Args</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.typeRefs.TypeArgument}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Args</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Args</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getParameterizedTypeRef_TypeArgs()
	 * @model containment="true"
	 * @generated
	 */
	EList<TypeArgument> getTypeArgs();

	/**
	 * Returns the value of the '<em><b>Array Type Literal</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Array Type Literal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Array Type Literal</em>' attribute.
	 * @see #setArrayTypeLiteral(boolean)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getParameterizedTypeRef_ArrayTypeLiteral()
	 * @model default="false" unique="false"
	 * @generated
	 */
	boolean isArrayTypeLiteral();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#isArrayTypeLiteral <em>Array Type Literal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Array Type Literal</em>' attribute.
	 * @see #isArrayTypeLiteral()
	 * @generated
	 */
	void setArrayTypeLiteral(boolean value);

	/**
	 * Returns the value of the '<em><b>Ast Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If and only if this {@code ParameterizedTypeRef} is used as an AST node and the declared type is, in the source
	 * code, referred to via the namespace of a namespace import, then this is non-null and points to that namespace.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Ast Namespace</em>' reference.
	 * @see #setAstNamespace(ModuleNamespaceVirtualType)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getParameterizedTypeRef_AstNamespace()
	 * @model transient="true"
	 * @generated
	 */
	ModuleNamespaceVirtualType getAstNamespace();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getAstNamespace <em>Ast Namespace</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ast Namespace</em>' reference.
	 * @see #getAstNamespace()
	 * @generated
	 */
	void setAstNamespace(ModuleNamespaceVirtualType value);

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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.n4js.ts.types.TypingStrategy%> _definedTypingStrategy = this.getDefinedTypingStrategy();\nboolean _tripleEquals = (_definedTypingStrategy == <%org.eclipse.n4js.ts.types.TypingStrategy%>.DEFAULT);\nif (_tripleEquals)\n{\n\tboolean _isDefSiteStructuralTyping = this.isDefSiteStructuralTyping();\n\tif (_isDefSiteStructuralTyping)\n\t{\n\t\treturn <%org.eclipse.n4js.ts.types.TypingStrategy%>.STRUCTURAL;\n\t}\n\telse\n\t{\n\t\treturn <%org.eclipse.n4js.ts.types.TypingStrategy%>.NOMINAL;\n\t}\n}\nreturn this.getDefinedTypingStrategy();'"
	 * @generated
	 */
	TypingStrategy getTypingStrategy();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  @see TypeArgument#containsWildcard()
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((this.getTypeArgs().isEmpty() && (!this.getDeclaredType().isGeneric())) || <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.eclipse.n4js.ts.typeRefs.TypeArgument%>>exists(this.getTypeArgs(), new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.typeRefs.TypeArgument%>, <%java.lang.Boolean%>>()\n{\n\tpublic <%java.lang.Boolean%> apply(final <%org.eclipse.n4js.ts.typeRefs.TypeArgument%> it)\n\t{\n\t\treturn <%java.lang.Boolean%>.valueOf(it.containsWildcards());\n\t}\n}));'"
	 * @generated
	 */
	boolean containsWildcards();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides {@link TypeRef#getTypeRefAsString()}
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.n4js.ts.types.Type%> _declaredType = this.getDeclaredType();\n<%java.lang.String%> _rawTypeAsString = null;\nif (_declaredType!=null)\n{\n\t_rawTypeAsString=_declaredType.getRawTypeAsString();\n}\n<%java.lang.String%> _xifexpression = null;\nint _version = this.getVersion();\nboolean _greaterThan = (_version > 0);\nif (_greaterThan)\n{\n\tint _version_1 = this.getVersion();\n\t_xifexpression = (\"#\" + <%java.lang.Integer%>.valueOf(_version_1));\n}\nelse\n{\n\t<%java.lang.String%> _xifexpression_1 = null;\n\tboolean _isEmpty = this.getTypeArgs().isEmpty();\n\tif (_isEmpty)\n\t{\n\t\t_xifexpression_1 = \"\";\n\t}\n\telse\n\t{\n\t\tfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.typeRefs.TypeArgument%>, <%java.lang.String%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.typeRefs.TypeArgument%>, <%java.lang.String%>>()\n\t\t{\n\t\t\tpublic <%java.lang.String%> apply(final <%org.eclipse.n4js.ts.typeRefs.TypeArgument%> it)\n\t\t\t{\n\t\t\t\treturn it.getTypeRefAsString();\n\t\t\t}\n\t\t};\n\t\t<%java.lang.String%> _join = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.join(<%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%>.<<%org.eclipse.n4js.ts.typeRefs.TypeArgument%>, <%java.lang.String%>>map(this.getTypeArgs(), _function), \",\");\n\t\t<%java.lang.String%> _plus = (\"<\" + _join);\n\t\t_xifexpression_1 = (_plus + \">\");\n\t}\n\t<%java.lang.String%> _plus_1 = (\"\" + _xifexpression_1);\n\t<%java.lang.String%> _modifiersAsString = this.getModifiersAsString();\n\t_xifexpression = (_plus_1 + _modifiersAsString);\n}\nreturn (_rawTypeAsString + _xifexpression);'"
	 * @generated
	 */
	String getTypeRefAsString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns true if type reference contains type arguments.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='boolean _isEmpty = this.getTypeArgs().isEmpty();\nreturn (!_isEmpty);'"
	 * @generated
	 */
	boolean isParameterized();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Delegates to {@link Type.isGeneric()}
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((this.getDeclaredType() != null) && this.getDeclaredType().isGeneric());'"
	 * @generated
	 */
	boolean isGeneric();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides {@link TypeRef#isRaw()}.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return (this.isGeneric() && (this.getTypeArgs().size() < this.getDeclaredType().getTypeVars().size()));'"
	 * @generated
	 */
	boolean isRaw();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * @see TypeArgument#containsUnboundTypeVariables()
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return (((this.getDeclaredType() instanceof <%org.eclipse.n4js.ts.types.TypeVariable%>) || ((!this.isParameterized()) && this.getDeclaredType().isGeneric())) || <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.eclipse.n4js.ts.typeRefs.TypeArgument%>>exists(this.getTypeArgs(), new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.typeRefs.TypeArgument%>, <%java.lang.Boolean%>>()\n{\n\tpublic <%java.lang.Boolean%> apply(final <%org.eclipse.n4js.ts.typeRefs.TypeArgument%> it)\n\t{\n\t\treturn <%java.lang.Boolean%>.valueOf(it.containsUnboundTypeVariables());\n\t}\n}));'"
	 * @generated
	 */
	boolean containsUnboundTypeVariables();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns true if typingStrategy is neither NOMINAL nor DEFAULT, and if is is not
	 * definition site.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((this.getDefinedTypingStrategy() != <%org.eclipse.n4js.ts.types.TypingStrategy%>.NOMINAL) && \n\t(this.getDefinedTypingStrategy() != <%org.eclipse.n4js.ts.types.TypingStrategy%>.DEFAULT));'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.n4js.ts.types.Type%> _declaredType = this.getDeclaredType();\nif ((_declaredType instanceof <%org.eclipse.n4js.ts.types.TN4Classifier%>))\n{\n\t<%org.eclipse.n4js.ts.types.Type%> _declaredType_1 = this.getDeclaredType();\n\t<%org.eclipse.n4js.ts.types.TypingStrategy%> _typingStrategy = ((<%org.eclipse.n4js.ts.types.TN4Classifier%>) _declaredType_1).getTypingStrategy();\n\treturn (_typingStrategy == <%org.eclipse.n4js.ts.types.TypingStrategy%>.STRUCTURAL);\n}\n<%org.eclipse.n4js.ts.types.Type%> _declaredType_2 = this.getDeclaredType();\nif ((_declaredType_2 instanceof <%org.eclipse.n4js.ts.types.TStructuralType%>))\n{\n\treturn true;\n}\nreturn false;'"
	 * @generated
	 */
	boolean isDefSiteStructuralTyping();

} // ParameterizedTypeRef
