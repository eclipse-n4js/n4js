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

import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TypableElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>N4 Member Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.N4MemberDeclaration#getOwner <em>Owner</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4MemberDeclaration()
 * @model abstract="true"
 * @generated
 */
public interface N4MemberDeclaration extends AnnotableElement, ModifiableElement, TypeProvidingElement, TypableElement, NamedElement {
	/**
	 * Returns the value of the '<em><b>Owner</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.n4js.n4JS.N4ClassifierDefinition#getOwnedMembersRaw <em>Owned Members Raw</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The classifier owning this member.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Owner</em>' container reference.
	 * @see #setOwner(N4ClassifierDefinition)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4MemberDeclaration_Owner()
	 * @see org.eclipse.n4js.n4JS.N4ClassifierDefinition#getOwnedMembersRaw
	 * @model opposite="ownedMembersRaw" transient="false"
	 * @generated
	 */
	N4ClassifierDefinition getOwner();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.N4MemberDeclaration#getOwner <em>Owner</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' container reference.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(N4ClassifierDefinition value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TMember getDefinedTypeElement();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if the member is declared as static.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.getDeclaredModifiers().contains(&lt;%org.eclipse.n4js.n4JS.N4Modifier%&gt;.STATIC);'"
	 * @generated
	 */
	boolean isDeclaredStatic();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.isDeclaredStatic();'"
	 * @generated
	 */
	boolean isStatic();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if the member is declared as static.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.n4JS.Annotation%&gt;, &lt;%java.lang.Boolean%&gt;&gt; _function = new &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.n4JS.Annotation%&gt;, &lt;%java.lang.Boolean%&gt;&gt;()\n{\n\tpublic &lt;%java.lang.Boolean%&gt; apply(final &lt;%org.eclipse.n4js.n4JS.Annotation%&gt; it)\n\t{\n\t\t&lt;%java.lang.String%&gt; _name = it.getName();\n\t\treturn &lt;%java.lang.Boolean%&gt;.valueOf(&lt;%com.google.common.base.Objects%&gt;.equal(_name, \"Final\"));\n\t}\n};\nreturn &lt;%org.eclipse.xtext.xbase.lib.IterableExtensions%&gt;.&lt;&lt;%org.eclipse.n4js.n4JS.Annotation%&gt;&gt;exists(this.getAnnotations(), _function);'"
	 * @generated
	 */
	boolean isDeclaredFinal();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.isDeclaredFinal();'"
	 * @generated
	 */
	boolean isFinal();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
	 * @generated
	 */
	boolean isConstructor();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
	 * @generated
	 */
	boolean isCallableConstructor();

} // N4MemberDeclaration
