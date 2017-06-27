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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((((this.eContainer() instanceof <%org.eclipse.n4js.n4JS.N4InterfaceDeclaration%>) && (this.getBody() == null)) && \n\t(!<%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.eclipse.n4js.n4JS.Annotation%>>exists(this.getAnnotations(), new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.n4JS.Annotation%>, <%java.lang.Boolean%>>()\n\t{\n\t\tpublic <%java.lang.Boolean%> apply(final <%org.eclipse.n4js.n4JS.Annotation%> it)\n\t\t{\n\t\t\t<%java.lang.String%> _name = it.getName();\n\t\t\treturn <%java.lang.Boolean%>.valueOf(<%com.google.common.base.Objects%>.equal(_name, \"ProvidesDefaultImplementation\"));\n\t\t}\n\t}))) || this.getDeclaredModifiers().contains(<%org.eclipse.n4js.n4JS.N4Modifier%>.ABSTRACT));'"
	 * @generated
	 */
	boolean isAbstract();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return (<%com.google.common.base.Objects%>.equal(this.getName(), \"constructor\") && (!this.isStatic()));'"
	 * @generated
	 */
	boolean isConstructor();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName%> _declaredName = this.getDeclaredName();\nreturn (_declaredName == null);'"
	 * @generated
	 */
	boolean isCallableConstructor();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.n4JS.N4Modifier%>> _declaredModifiers = this.getDeclaredModifiers();\nreturn _declaredModifiers.contains(<%org.eclipse.n4js.n4JS.N4Modifier%>.STATIC);'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%java.lang.String%> _name = this.getName();\nboolean _equals = <%com.google.common.base.Objects%>.equal(\"prototype\", _name);\nif (_equals)\n{\n\treturn false;\n}\nboolean _and = false;\nif (!(<%com.google.common.base.Objects%>.equal(\"constructor\", this.getName()) && this.isGenerator()))\n{\n\t_and = false;\n} else\n{\n\t<%org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName%> _declaredName = this.getDeclaredName();\n\t<%org.eclipse.n4js.n4JS.PropertyNameKind%> _kind = null;\n\tif (_declaredName!=null)\n\t{\n\t\t_kind=_declaredName.getKind();\n\t}\n\tboolean _tripleNotEquals = (_kind != <%org.eclipse.n4js.n4JS.PropertyNameKind%>.COMPUTED);\n\t_and = _tripleNotEquals;\n}\nif (_and)\n{\n\treturn false;\n}\nreturn true;'"
	 * @generated
	 */
	boolean isValidName();

} // N4MethodDeclaration
