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
 * A representation of the model object '<em><b>N4 Method Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4MethodDeclaration()
 * @model
 * @generated
 */
public interface N4MethodDeclaration extends AnnotableN4MemberDeclaration, MethodDeclaration {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((((this.eContainer() instanceof &lt;%org.eclipse.n4js.n4JS.N4InterfaceDeclaration%&gt;) &amp;&amp; (this.getBody() == null)) &amp;&amp; \n\t(!&lt;%org.eclipse.xtext.xbase.lib.IterableExtensions%&gt;.&lt;&lt;%org.eclipse.n4js.n4JS.Annotation%&gt;&gt;exists(this.getAnnotations(), new &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.n4JS.Annotation%&gt;, &lt;%java.lang.Boolean%&gt;&gt;()\n\t{\n\t\tpublic &lt;%java.lang.Boolean%&gt; apply(final &lt;%org.eclipse.n4js.n4JS.Annotation%&gt; it)\n\t\t{\n\t\t\t&lt;%java.lang.String%&gt; _name = it.getName();\n\t\t\treturn &lt;%java.lang.Boolean%&gt;.valueOf(&lt;%com.google.common.base.Objects%&gt;.equal(_name, \"ProvidesDefaultImplementation\"));\n\t\t}\n\t}))) || this.getDeclaredModifiers().contains(&lt;%org.eclipse.n4js.n4JS.N4Modifier%&gt;.ABSTRACT));'"
	 * @generated
	 */
	boolean isAbstract();

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
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName%&gt; _declaredName = this.getDeclaredName();\nreturn (_declaredName == null);'"
	 * @generated
	 */
	boolean isCallableConstructor();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.getDeclaredModifiers().contains(&lt;%org.eclipse.n4js.n4JS.N4Modifier%&gt;.STATIC);'"
	 * @generated
	 */
	boolean isStatic();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Methods in classes may not be called 'prototype'.
	 * Generators may not be called 'constructor' either (except for computed names).
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%java.lang.String%&gt; _name = this.getName();\nboolean _equals = &lt;%com.google.common.base.Objects%&gt;.equal(\"prototype\", _name);\nif (_equals)\n{\n\treturn false;\n}\nboolean _and = false;\nif (!(&lt;%com.google.common.base.Objects%&gt;.equal(\"constructor\", this.getName()) &amp;&amp; this.isGenerator()))\n{\n\t_and = false;\n} else\n{\n\t&lt;%org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName%&gt; _declaredName = this.getDeclaredName();\n\t&lt;%org.eclipse.n4js.n4JS.PropertyNameKind%&gt; _kind = null;\n\tif (_declaredName!=null)\n\t{\n\t\t_kind=_declaredName.getKind();\n\t}\n\tboolean _tripleNotEquals = (_kind != &lt;%org.eclipse.n4js.n4JS.PropertyNameKind%&gt;.COMPUTED);\n\t_and = _tripleNotEquals;\n}\nif (_and)\n{\n\treturn false;\n}\nreturn true;'"
	 * @generated
	 */
	boolean isValidName();

} // N4MethodDeclaration
