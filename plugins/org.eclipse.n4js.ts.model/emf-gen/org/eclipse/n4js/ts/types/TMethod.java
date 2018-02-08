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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TMethod</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Method of a class.
 *  * Attention: Order of super types matters, see https://bugs.eclipse.org/bugs/show_bug.cgi?id=421592
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TMethod#isDeclaredAbstract <em>Declared Abstract</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TMethod#isLacksThisOrSuperUsage <em>Lacks This Or Super Usage</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMethod()
 * @model
 * @generated
 */
public interface TMethod extends TFunction, TMemberWithAccessModifier {
	/**
	 * Returns the value of the '<em><b>Declared Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared Abstract</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Abstract</em>' attribute.
	 * @see #setDeclaredAbstract(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMethod_DeclaredAbstract()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredAbstract();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TMethod#isDeclaredAbstract <em>Declared Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Abstract</em>' attribute.
	 * @see #isDeclaredAbstract()
	 * @generated
	 */
	void setDeclaredAbstract(boolean value);

	/**
	 * Returns the value of the '<em><b>Lacks This Or Super Usage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Knowing whether the body of the linked method contains occurrences of ThisLiteral or SuperLiteral
	 * is useful in cross-resource scenarios (ie, so as to avoid parsing) when checking the (restrictive) conditions
	 * under which a method reference can be assigned to a variable (IDE-1048, see implementation in N4JSExpressionValidator).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Lacks This Or Super Usage</em>' attribute.
	 * @see #setLacksThisOrSuperUsage(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMethod_LacksThisOrSuperUsage()
	 * @model unique="false"
	 * @generated
	 */
	boolean isLacksThisOrSuperUsage();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TMethod#isLacksThisOrSuperUsage <em>Lacks This Or Super Usage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lacks This Or Super Usage</em>' attribute.
	 * @see #isLacksThisOrSuperUsage()
	 * @generated
	 */
	void setLacksThisOrSuperUsage(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if the method is either declared abstract or it is implicitly abstract, i.e. it is declared in a role and has no body.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return (this.isDeclaredAbstract() || ((this.eContainer() instanceof &lt;%org.eclipse.n4js.ts.types.TInterface%&gt;) &amp;&amp; this.isHasNoBody()));'"
	 * @generated
	 */
	boolean isAbstract();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * Always returns METHOD
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return &lt;%org.eclipse.n4js.ts.types.MemberType%&gt;.METHOD;'"
	 * @generated
	 */
	MemberType getMemberType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return (&lt;%com.google.common.base.Objects%&gt;.equal(this.getName(), \"constructor\") &amp;&amp; (!this.isStatic()));'"
	 * @generated
	 */
	boolean isConstructor();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns string representation of this function similar according to the N4JS syntax.
	 * This includes formal parameters and return type (if declared), but excludes annotations.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final &lt;%java.lang.StringBuilder%&gt; strb = new &lt;%java.lang.StringBuilder%&gt;();\nboolean _isGeneric = this.isGeneric();\nif (_isGeneric)\n{\n\tfinal &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.TypeVariable%&gt;, &lt;%java.lang.String%&gt;&gt; _function = new &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.TypeVariable%&gt;, &lt;%java.lang.String%&gt;&gt;()\n\t{\n\t\tpublic &lt;%java.lang.String%&gt; apply(final &lt;%org.eclipse.n4js.ts.types.TypeVariable%&gt; it)\n\t\t{\n\t\t\treturn it.getTypeAsString();\n\t\t}\n\t};\n\tstrb.append(\"&lt;\").append(&lt;%org.eclipse.xtext.xbase.lib.IterableExtensions%&gt;.join(&lt;%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%&gt;.&lt;&lt;%org.eclipse.n4js.ts.types.TypeVariable%&gt;, &lt;%java.lang.String%&gt;&gt;map(this.getTypeVars(), _function), \",\")).append(\"&gt; \");\n}\nboolean _isDeclaredAsync = this.isDeclaredAsync();\nif (_isDeclaredAsync)\n{\n\tstrb.append(\"async \");\n}\nfinal &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.TFormalParameter%&gt;, &lt;%java.lang.String%&gt;&gt; _function_1 = new &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.TFormalParameter%&gt;, &lt;%java.lang.String%&gt;&gt;()\n{\n\tpublic &lt;%java.lang.String%&gt; apply(final &lt;%org.eclipse.n4js.ts.types.TFormalParameter%&gt; it)\n\t{\n\t\treturn it.getFormalParameterAsString();\n\t}\n};\nstrb.append(this.getName()).append(\"(\").append(&lt;%org.eclipse.xtext.xbase.lib.IterableExtensions%&gt;.join(&lt;%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%&gt;.&lt;&lt;%org.eclipse.n4js.ts.types.TFormalParameter%&gt;, &lt;%java.lang.String%&gt;&gt;map(this.getFpars(), _function_1), \", \")).append(\")\");\n&lt;%org.eclipse.n4js.ts.typeRefs.TypeRef%&gt; _returnTypeRef = this.getReturnTypeRef();\nboolean _tripleNotEquals = (_returnTypeRef != null);\nif (_tripleNotEquals)\n{\n\tstrb.append(\": \").append(this.getReturnTypeRef().getTypeRefAsString());\n}\nboolean _isReturnValueOptional = this.isReturnValueOptional();\nif (_isReturnValueOptional)\n{\n\tstrb.append(\"?\");\n}\nreturn strb.toString();'"
	 * @generated
	 */
	String getFunctionAsString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Overrides TMember's method
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.getFunctionAsString();'"
	 * @generated
	 */
	String getMemberAsString();

} // TMethod
