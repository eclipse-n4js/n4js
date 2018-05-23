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
 * A representation of the model object '<em><b>N4 Field Accessor</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4FieldAccessor()
 * @model abstract="true"
 * @generated
 */
public interface N4FieldAccessor extends AnnotableN4MemberDeclaration, FieldAccessor {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((((this.eContainer() instanceof &lt;%org.eclipse.n4js.n4JS.N4InterfaceDeclaration%&gt;) &amp;&amp; (this.getBody() == null)) &amp;&amp; \n\t(!&lt;%org.eclipse.xtext.xbase.lib.IterableExtensions%&gt;.&lt;&lt;%org.eclipse.n4js.n4JS.Annotation%&gt;&gt;exists(this.getAnnotations(), new &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.n4JS.Annotation%&gt;, &lt;%java.lang.Boolean%&gt;&gt;()\n\t{\n\t\tpublic &lt;%java.lang.Boolean%&gt; apply(final &lt;%org.eclipse.n4js.n4JS.Annotation%&gt; it)\n\t\t{\n\t\t\t&lt;%java.lang.String%&gt; _name = it.getName();\n\t\t\treturn &lt;%java.lang.Boolean%&gt;.valueOf(&lt;%com.google.common.base.Objects%&gt;.equal(_name, \"ProvidesDefaultImplementation\"));\n\t\t}\n\t}))) || \n\tthis.getDeclaredModifiers().contains(&lt;%org.eclipse.n4js.n4JS.N4Modifier%&gt;.ABSTRACT));'"
	 * @generated
	 */
	boolean isAbstract();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Field accessors in classes may not be called 'prototype' or 'constructor' (except for computed names).
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%java.lang.String%&gt; _name = this.getName();\nboolean _equals = &lt;%com.google.common.base.Objects%&gt;.equal(\"prototype\", _name);\nif (_equals)\n{\n\treturn false;\n}\nboolean _and = false;\n&lt;%java.lang.String%&gt; _name_1 = this.getName();\nboolean _equals_1 = &lt;%com.google.common.base.Objects%&gt;.equal(\"constructor\", _name_1);\nif (!_equals_1)\n{\n\t_and = false;\n} else\n{\n\t&lt;%org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName%&gt; _declaredName = this.getDeclaredName();\n\t&lt;%org.eclipse.n4js.n4JS.PropertyNameKind%&gt; _kind = null;\n\tif (_declaredName!=null)\n\t{\n\t\t_kind=_declaredName.getKind();\n\t}\n\tboolean _tripleNotEquals = (_kind != &lt;%org.eclipse.n4js.n4JS.PropertyNameKind%&gt;.COMPUTED);\n\t_and = _tripleNotEquals;\n}\nif (_and)\n{\n\treturn false;\n}\nreturn true;'"
	 * @generated
	 */
	boolean isValidName();

} // N4FieldAccessor
