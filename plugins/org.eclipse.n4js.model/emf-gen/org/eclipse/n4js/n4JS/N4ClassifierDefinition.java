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

import java.lang.Iterable;

import org.eclipse.emf.common.util.EList;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>N4 Classifier Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Abstract base class for n4 classifiers, that is types containing members such as fields or methods.
 * Note that not all types can contain any members, e.g., interfaces must not contain fields.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.N4ClassifierDefinition#getOwnedMembersRaw <em>Owned Members Raw</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4ClassifierDefinition()
 * @model abstract="true"
 * @generated
 */
public interface N4ClassifierDefinition extends N4TypeDefinition {
	/**
	 * Returns the value of the '<em><b>Owned Members Raw</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.N4MemberDeclaration}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.n4js.n4JS.N4MemberDeclaration#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Members directly defined in this classifier, i.e. w/o inherited members.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Owned Members Raw</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4ClassifierDefinition_OwnedMembersRaw()
	 * @see org.eclipse.n4js.n4JS.N4MemberDeclaration#getOwner
	 * @model opposite="owner" containment="true"
	 * @generated
	 */
	EList<N4MemberDeclaration> getOwnedMembersRaw();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns a view on ownedMembersRaw filtering out non-methods.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.n4JS.N4MemberDeclaration%>> _ownedMembersRaw = this.getOwnedMembersRaw();\n<%java.lang.Iterable%><<%org.eclipse.n4js.n4JS.N4MemberDeclaration%>> _filter = <%com.google.common.collect.Iterables%>.<<%org.eclipse.n4js.n4JS.N4MemberDeclaration%>>filter(_ownedMembersRaw, <%org.eclipse.n4js.n4JS.N4MemberDeclaration%>.class);\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.n4JS.N4MemberDeclaration%>, <%java.lang.Boolean%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.n4JS.N4MemberDeclaration%>, <%java.lang.Boolean%>>()\n{\n\tpublic <%java.lang.Boolean%> apply(final <%org.eclipse.n4js.n4JS.N4MemberDeclaration%> it)\n\t{\n\t\tboolean _isCallableConstructor = it.isCallableConstructor();\n\t\treturn <%java.lang.Boolean%>.valueOf((!_isCallableConstructor));\n\t}\n};\nfinal <%java.lang.Iterable%><<%org.eclipse.n4js.n4JS.N4MemberDeclaration%>> methods = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.eclipse.n4js.n4JS.N4MemberDeclaration%>>filter(_filter, _function);\n<%java.util.List%><<%org.eclipse.n4js.n4JS.N4MemberDeclaration%>> _list = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.eclipse.n4js.n4JS.N4MemberDeclaration%>>toList(methods);\nreturn new <%org.eclipse.emf.common.util.BasicEList%><<%org.eclipse.n4js.n4JS.N4MemberDeclaration%>>(_list);'"
	 * @generated
	 */
	EList<N4MemberDeclaration> getOwnedMembers();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns explicitly defined constructor of receiving class or <code>null</code> if none was defined.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.n4JS.N4MemberDeclaration%>> _ownedMembersRaw = this.getOwnedMembersRaw();\n<%java.lang.Iterable%><<%org.eclipse.n4js.n4JS.N4MethodDeclaration%>> _filter = <%com.google.common.collect.Iterables%>.<<%org.eclipse.n4js.n4JS.N4MethodDeclaration%>>filter(_ownedMembersRaw, <%org.eclipse.n4js.n4JS.N4MethodDeclaration%>.class);\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.n4JS.N4MethodDeclaration%>, <%java.lang.Boolean%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.n4JS.N4MethodDeclaration%>, <%java.lang.Boolean%>>()\n{\n\tpublic <%java.lang.Boolean%> apply(final <%org.eclipse.n4js.n4JS.N4MethodDeclaration%> it)\n\t{\n\t\treturn <%java.lang.Boolean%>.valueOf(it.isConstructor());\n\t}\n};\nreturn <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.eclipse.n4js.n4JS.N4MethodDeclaration%>>findFirst(_filter, _function);'"
	 * @generated
	 */
	N4MethodDeclaration getOwnedCtor();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns explicitly defined callable constructor of receiving class or <code>null</code> if none was defined.
	 * This is *not* the actual constructor but instead the function used for direct invocations in call expressions.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.n4JS.N4MemberDeclaration%>> _ownedMembersRaw = this.getOwnedMembersRaw();\n<%java.lang.Iterable%><<%org.eclipse.n4js.n4JS.N4MethodDeclaration%>> _filter = <%com.google.common.collect.Iterables%>.<<%org.eclipse.n4js.n4JS.N4MethodDeclaration%>>filter(_ownedMembersRaw, <%org.eclipse.n4js.n4JS.N4MethodDeclaration%>.class);\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.n4JS.N4MethodDeclaration%>, <%java.lang.Boolean%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.n4JS.N4MethodDeclaration%>, <%java.lang.Boolean%>>()\n{\n\tpublic <%java.lang.Boolean%> apply(final <%org.eclipse.n4js.n4JS.N4MethodDeclaration%> it)\n\t{\n\t\treturn <%java.lang.Boolean%>.valueOf(it.isCallableConstructor());\n\t}\n};\nreturn <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.eclipse.n4js.n4JS.N4MethodDeclaration%>>findFirst(_filter, _function);'"
	 * @generated
	 */
	N4MethodDeclaration getOwnedCallableCtor();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns a view on ownedMembersRaw filtering out non-methods.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.n4JS.N4MemberDeclaration%>> _ownedMembersRaw = this.getOwnedMembersRaw();\n<%java.lang.Iterable%><<%org.eclipse.n4js.n4JS.N4MethodDeclaration%>> _filter = <%com.google.common.collect.Iterables%>.<<%org.eclipse.n4js.n4JS.N4MethodDeclaration%>>filter(_ownedMembersRaw, <%org.eclipse.n4js.n4JS.N4MethodDeclaration%>.class);\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.n4JS.N4MethodDeclaration%>, <%java.lang.Boolean%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.n4JS.N4MethodDeclaration%>, <%java.lang.Boolean%>>()\n{\n\tpublic <%java.lang.Boolean%> apply(final <%org.eclipse.n4js.n4JS.N4MethodDeclaration%> it)\n\t{\n\t\treturn <%java.lang.Boolean%>.valueOf(((!it.isConstructor()) && (!it.isCallableConstructor())));\n\t}\n};\nfinal <%java.lang.Iterable%><<%org.eclipse.n4js.n4JS.N4MethodDeclaration%>> methods = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.eclipse.n4js.n4JS.N4MethodDeclaration%>>filter(_filter, _function);\n<%java.util.List%><<%org.eclipse.n4js.n4JS.N4MethodDeclaration%>> _list = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.eclipse.n4js.n4JS.N4MethodDeclaration%>>toList(methods);\nreturn new <%org.eclipse.emf.common.util.BasicEList%><<%org.eclipse.n4js.n4JS.N4MethodDeclaration%>>(_list);'"
	 * @generated
	 */
	EList<N4MethodDeclaration> getOwnedMethods();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns a view on ownedMembersRaw filtering out non-fields.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.n4JS.N4MemberDeclaration%>> _ownedMembersRaw = this.getOwnedMembersRaw();\nfinal <%java.lang.Iterable%><<%org.eclipse.n4js.n4JS.N4FieldDeclaration%>> fields = <%com.google.common.collect.Iterables%>.<<%org.eclipse.n4js.n4JS.N4FieldDeclaration%>>filter(_ownedMembersRaw, <%org.eclipse.n4js.n4JS.N4FieldDeclaration%>.class);\n<%java.util.List%><<%org.eclipse.n4js.n4JS.N4FieldDeclaration%>> _list = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.eclipse.n4js.n4JS.N4FieldDeclaration%>>toList(fields);\nreturn new <%org.eclipse.emf.common.util.BasicEList%><<%org.eclipse.n4js.n4JS.N4FieldDeclaration%>>(_list);'"
	 * @generated
	 */
	EList<N4FieldDeclaration> getOwnedFields();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns a view on ownedMembersRaw filtering out non-getters.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.n4JS.N4MemberDeclaration%>> _ownedMembersRaw = this.getOwnedMembersRaw();\nfinal <%java.lang.Iterable%><<%org.eclipse.n4js.n4JS.N4GetterDeclaration%>> getters = <%com.google.common.collect.Iterables%>.<<%org.eclipse.n4js.n4JS.N4GetterDeclaration%>>filter(_ownedMembersRaw, <%org.eclipse.n4js.n4JS.N4GetterDeclaration%>.class);\n<%java.util.List%><<%org.eclipse.n4js.n4JS.N4GetterDeclaration%>> _list = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.eclipse.n4js.n4JS.N4GetterDeclaration%>>toList(getters);\nreturn new <%org.eclipse.emf.common.util.BasicEList%><<%org.eclipse.n4js.n4JS.N4GetterDeclaration%>>(_list);'"
	 * @generated
	 */
	EList<N4GetterDeclaration> getOwnedGetters();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns a view on ownedMembersRaw filtering out non-setters.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.n4JS.N4MemberDeclaration%>> _ownedMembersRaw = this.getOwnedMembersRaw();\nfinal <%java.lang.Iterable%><<%org.eclipse.n4js.n4JS.N4SetterDeclaration%>> setters = <%com.google.common.collect.Iterables%>.<<%org.eclipse.n4js.n4JS.N4SetterDeclaration%>>filter(_ownedMembersRaw, <%org.eclipse.n4js.n4JS.N4SetterDeclaration%>.class);\n<%java.util.List%><<%org.eclipse.n4js.n4JS.N4SetterDeclaration%>> _list = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.eclipse.n4js.n4JS.N4SetterDeclaration%>>toList(setters);\nreturn new <%org.eclipse.emf.common.util.BasicEList%><<%org.eclipse.n4js.n4JS.N4SetterDeclaration%>>(_list);'"
	 * @generated
	 */
	EList<N4SetterDeclaration> getOwnedSetters();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Derived, returns extended class (if any) and implemented or extended interfaces.
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefIterable" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return <%java.util.Collections%>.<<%org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef%>>emptyList();'"
	 * @generated
	 */
	Iterable<ParameterizedTypeRef> getSuperClassifierRefs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Derived, returns implemented or extended interfaces.
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefIterable" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return <%java.util.Collections%>.<<%org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef%>>emptyList();'"
	 * @generated
	 */
	Iterable<ParameterizedTypeRef> getImplementedOrExtendedInterfaceRefs();

} // N4ClassifierDefinition
