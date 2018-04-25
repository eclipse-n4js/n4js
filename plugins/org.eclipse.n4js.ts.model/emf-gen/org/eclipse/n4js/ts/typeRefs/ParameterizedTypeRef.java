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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt; _definedTypingStrategy = this.getDefinedTypingStrategy();\nboolean _tripleEquals = (_definedTypingStrategy == &lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt;.DEFAULT);\nif (_tripleEquals)\n{\n\tboolean _isDefSiteStructuralTyping = this.isDefSiteStructuralTyping();\n\tif (_isDefSiteStructuralTyping)\n\t{\n\t\treturn &lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt;.STRUCTURAL;\n\t}\n\telse\n\t{\n\t\treturn &lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt;.NOMINAL;\n\t}\n}\nreturn this.getDefinedTypingStrategy();'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((this.getTypeArgs().isEmpty() &amp;&amp; (!this.getDeclaredType().isGeneric())) || &lt;%org.eclipse.xtext.xbase.lib.IterableExtensions%&gt;.&lt;&lt;%org.eclipse.n4js.ts.typeRefs.TypeArgument%&gt;&gt;exists(this.getTypeArgs(), new &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.typeRefs.TypeArgument%&gt;, &lt;%java.lang.Boolean%&gt;&gt;()\n{\n\tpublic &lt;%java.lang.Boolean%&gt; apply(final &lt;%org.eclipse.n4js.ts.typeRefs.TypeArgument%&gt; it)\n\t{\n\t\treturn &lt;%java.lang.Boolean%&gt;.valueOf(it.containsWildcards());\n\t}\n}));'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.types.Type%&gt; _declaredType = this.getDeclaredType();\n&lt;%java.lang.String%&gt; _rawTypeAsString = null;\nif (_declaredType!=null)\n{\n\t_rawTypeAsString=_declaredType.getRawTypeAsString();\n}\n&lt;%java.lang.String%&gt; _xifexpression = null;\nint _version = this.getVersion();\nboolean _greaterThan = (_version &gt; 0);\nif (_greaterThan)\n{\n\tint _version_1 = this.getVersion();\n\t_xifexpression = (\"#\" + &lt;%java.lang.Integer%&gt;.valueOf(_version_1));\n}\nelse\n{\n\t&lt;%java.lang.String%&gt; _xifexpression_1 = null;\n\tboolean _isEmpty = this.getTypeArgs().isEmpty();\n\tif (_isEmpty)\n\t{\n\t\t_xifexpression_1 = \"\";\n\t}\n\telse\n\t{\n\t\tfinal &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.typeRefs.TypeArgument%&gt;, &lt;%java.lang.String%&gt;&gt; _function = new &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.typeRefs.TypeArgument%&gt;, &lt;%java.lang.String%&gt;&gt;()\n\t\t{\n\t\t\tpublic &lt;%java.lang.String%&gt; apply(final &lt;%org.eclipse.n4js.ts.typeRefs.TypeArgument%&gt; it)\n\t\t\t{\n\t\t\t\treturn it.getTypeRefAsString();\n\t\t\t}\n\t\t};\n\t\t&lt;%java.lang.String%&gt; _join = &lt;%org.eclipse.xtext.xbase.lib.IterableExtensions%&gt;.join(&lt;%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%&gt;.&lt;&lt;%org.eclipse.n4js.ts.typeRefs.TypeArgument%&gt;, &lt;%java.lang.String%&gt;&gt;map(this.getTypeArgs(), _function), \",\");\n\t\t&lt;%java.lang.String%&gt; _plus = (\"&lt;\" + _join);\n\t\t_xifexpression_1 = (_plus + \"&gt;\");\n\t}\n\t&lt;%java.lang.String%&gt; _plus_1 = (\"\" + _xifexpression_1);\n\t&lt;%java.lang.String%&gt; _modifiersAsString = this.getModifiersAsString();\n\t_xifexpression = (_plus_1 + _modifiersAsString);\n}\nreturn (_rawTypeAsString + _xifexpression);'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((this.getDeclaredType() != null) &amp;&amp; this.getDeclaredType().isGeneric());'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return (this.isGeneric() &amp;&amp; (this.getTypeArgs().size() &lt; this.getDeclaredType().getTypeVars().size()));'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return (((this.getDeclaredType() instanceof &lt;%org.eclipse.n4js.ts.types.TypeVariable%&gt;) || ((!this.isParameterized()) &amp;&amp; this.getDeclaredType().isGeneric())) || &lt;%org.eclipse.xtext.xbase.lib.IterableExtensions%&gt;.&lt;&lt;%org.eclipse.n4js.ts.typeRefs.TypeArgument%&gt;&gt;exists(this.getTypeArgs(), new &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.typeRefs.TypeArgument%&gt;, &lt;%java.lang.Boolean%&gt;&gt;()\n{\n\tpublic &lt;%java.lang.Boolean%&gt; apply(final &lt;%org.eclipse.n4js.ts.typeRefs.TypeArgument%&gt; it)\n\t{\n\t\treturn &lt;%java.lang.Boolean%&gt;.valueOf(it.containsUnboundTypeVariables());\n\t}\n}));'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((this.getDefinedTypingStrategy() != &lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt;.NOMINAL) &amp;&amp; \n\t(this.getDefinedTypingStrategy() != &lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt;.DEFAULT));'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.types.Type%&gt; _declaredType = this.getDeclaredType();\nif ((_declaredType instanceof &lt;%org.eclipse.n4js.ts.types.TN4Classifier%&gt;))\n{\n\t&lt;%org.eclipse.n4js.ts.types.Type%&gt; _declaredType_1 = this.getDeclaredType();\n\t&lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt; _typingStrategy = ((&lt;%org.eclipse.n4js.ts.types.TN4Classifier%&gt;) _declaredType_1).getTypingStrategy();\n\treturn (_typingStrategy == &lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt;.STRUCTURAL);\n}\n&lt;%org.eclipse.n4js.ts.types.Type%&gt; _declaredType_2 = this.getDeclaredType();\nif ((_declaredType_2 instanceof &lt;%org.eclipse.n4js.ts.types.TStructuralType%&gt;))\n{\n\treturn true;\n}\nreturn false;'"
	 * @generated
	 */
	boolean isDefSiteStructuralTyping();

} // ParameterizedTypeRef
