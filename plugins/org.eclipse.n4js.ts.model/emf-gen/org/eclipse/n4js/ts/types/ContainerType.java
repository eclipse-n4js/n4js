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

import java.util.Map;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Container Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base class for types containing members, such as TClassifier and PrimitiveTypes.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.ContainerType#getOwnedMembersByNameAndAccess <em>Owned Members By Name And Access</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.ContainerType#getOwnedMembers <em>Owned Members</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.ContainerType#getCallableCtor <em>Callable Ctor</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.ContainerType#getTypeVars <em>Type Vars</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getContainerType()
 * @model abstract="true"
 * @generated
 */
public interface ContainerType<MT extends TMember> extends Type {
	/**
	 * Returns the value of the '<em><b>Owned Members By Name And Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Members By Name And Access</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owned Members By Name And Access</em>' attribute.
	 * @see #setOwnedMembersByNameAndAccess(Map)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getContainerType_OwnedMembersByNameAndAccess()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	Map<NameAndAccess, ? extends TMember> getOwnedMembersByNameAndAccess();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.ContainerType#getOwnedMembersByNameAndAccess <em>Owned Members By Name And Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owned Members By Name And Access</em>' attribute.
	 * @see #getOwnedMembersByNameAndAccess()
	 * @generated
	 */
	void setOwnedMembersByNameAndAccess(Map<NameAndAccess, ? extends TMember> value);

	/**
	 * Returns the value of the '<em><b>Owned Members</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Members</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owned Members</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getContainerType_OwnedMembers()
	 * @model containment="true"
	 * @generated
	 */
	EList<MT> getOwnedMembers();

	/**
	 * Returns the value of the '<em><b>Callable Ctor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Callable Ctor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Callable Ctor</em>' containment reference.
	 * @see #setCallableCtor(TMethod)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getContainerType_CallableCtor()
	 * @model containment="true"
	 * @generated
	 */
	TMethod getCallableCtor();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.ContainerType#getCallableCtor <em>Callable Ctor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Callable Ctor</em>' containment reference.
	 * @see #getCallableCtor()
	 * @generated
	 */
	void setCallableCtor(TMethod value);

	/**
	 * Returns the value of the '<em><b>Type Vars</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TypeVariable}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * type parameters of generic types
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type Vars</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getContainerType_TypeVars()
	 * @model containment="true"
	 * @generated
	 */
	EList<TypeVariable> getTypeVars();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method returning the owned constructor or <code>null</code> if not available.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.TMethod%&gt;, &lt;%java.lang.Boolean%&gt;&gt; _function = new &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.TMethod%&gt;, &lt;%java.lang.Boolean%&gt;&gt;()\n{\n\tpublic &lt;%java.lang.Boolean%&gt; apply(final &lt;%org.eclipse.n4js.ts.types.TMethod%&gt; it)\n\t{\n\t\treturn &lt;%java.lang.Boolean%&gt;.valueOf(it.isConstructor());\n\t}\n};\nreturn &lt;%org.eclipse.xtext.xbase.lib.IterableExtensions%&gt;.&lt;&lt;%org.eclipse.n4js.ts.types.TMethod%&gt;&gt;findFirst(&lt;%com.google.common.collect.Iterables%&gt;.&lt;&lt;%org.eclipse.n4js.ts.types.TMethod%&gt;&gt;filter(this.getOwnedMembers(), &lt;%org.eclipse.n4js.ts.types.TMethod%&gt;.class), _function);'"
	 * @generated
	 */
	TMethod getOwnedCtor();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, similar to {code findMember(name, false)}.
	 * That is, this method returns fields, methods, and getters rather then setters.
	 * <!-- end-model-doc -->
	 * @model unique="false" nameUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.findOwnedMember(name, false, false);'"
	 * @generated
	 */
	TMember findOwnedMember(String name);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns members with given name, writeable and static access.
	 * <!-- end-model-doc -->
	 * @model unique="false" nameUnique="false" writeAccessUnique="false" staticAccessUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final &lt;%org.eclipse.n4js.ts.types.NameAndAccess%&gt; nameAndAccess = new &lt;%org.eclipse.n4js.ts.types.NameAndAccess%&gt;(name, writeAccess, staticAccess);\nreturn this.getOrCreateOwnedMembersByNameAndAccess().get(nameAndAccess);'"
	 * @generated
	 */
	TMember findOwnedMember(String name, boolean writeAccess, boolean staticAccess);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Creates member collection mapped by static and writable access, fields are listed twice (since they are read- and
	 * writeable).
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%java.util.Map%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.NameAndAccess%&gt;, ? extends &lt;%org.eclipse.n4js.ts.types.TMember%&gt;&gt; _ownedMembersByNameAndAccess = this.getOwnedMembersByNameAndAccess();\nboolean _tripleEquals = (_ownedMembersByNameAndAccess == null);\nif (_tripleEquals)\n{\n\t&lt;%java.util.Map%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.NameAndAccess%&gt;, ? extends &lt;%org.eclipse.n4js.ts.types.TMember%&gt;&gt; _switchResult = null;\n\tint _size = this.getOwnedMembers().size();\n\tswitch (_size)\n\t{\n\t\tcase 0:\n\t\t\t_switchResult = &lt;%java.util.Collections%&gt;.&lt;&lt;%org.eclipse.n4js.ts.types.NameAndAccess%&gt;, &lt;%org.eclipse.n4js.ts.types.TMember%&gt;&gt;emptyMap();\n\t\t\tbreak;\n\t\tcase 1:\n\t\t\t&lt;%java.util.Map%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.NameAndAccess%&gt;, ? extends &lt;%org.eclipse.n4js.ts.types.TMember%&gt;&gt; _xblockexpression = null;\n\t\t\t{\n\t\t\t\tfinal MT singleMember = this.getOwnedMembers().get(0);\n\t\t\t\tfinal &lt;%org.eclipse.n4js.ts.types.NameAndAccess%&gt;[] nameAndAccess = &lt;%org.eclipse.n4js.ts.types.NameAndAccess%&gt;.of(singleMember);\n\t\t\t\t&lt;%java.util.Map%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.NameAndAccess%&gt;, ? extends &lt;%org.eclipse.n4js.ts.types.TMember%&gt;&gt; _xifexpression = null;\n\t\t\t\tint _length = nameAndAccess.length;\n\t\t\t\tboolean _greaterThan = (_length &gt; 1);\n\t\t\t\tif (_greaterThan)\n\t\t\t\t{\n\t\t\t\t\t&lt;%java.util.Map%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.NameAndAccess%&gt;, &lt;%org.eclipse.n4js.ts.types.TMember%&gt;&gt; _xblockexpression_1 = null;\n\t\t\t\t\t{\n\t\t\t\t\t\tfinal &lt;%java.util.HashMap%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.NameAndAccess%&gt;, &lt;%org.eclipse.n4js.ts.types.TMember%&gt;&gt; map = new &lt;%java.util.HashMap%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.NameAndAccess%&gt;, &lt;%org.eclipse.n4js.ts.types.TMember%&gt;&gt;();\n\t\t\t\t\t\tmap.put(nameAndAccess[0], singleMember);\n\t\t\t\t\t\tmap.put(nameAndAccess[1], singleMember);\n\t\t\t\t\t\t_xblockexpression_1 = &lt;%java.util.Collections%&gt;.&lt;&lt;%org.eclipse.n4js.ts.types.NameAndAccess%&gt;, &lt;%org.eclipse.n4js.ts.types.TMember%&gt;&gt;unmodifiableMap(map);\n\t\t\t\t\t}\n\t\t\t\t\t_xifexpression = _xblockexpression_1;\n\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n\t\t\t\t\t_xifexpression = &lt;%java.util.Collections%&gt;.&lt;&lt;%org.eclipse.n4js.ts.types.NameAndAccess%&gt;, MT&gt;singletonMap(nameAndAccess[0], singleMember);\n\t\t\t\t}\n\t\t\t\t_xblockexpression = _xifexpression;\n\t\t\t}\n\t\t\t_switchResult = _xblockexpression;\n\t\t\tbreak;\n\t\tdefault:\n\t\t\t&lt;%org.eclipse.emf.common.util.EList%&gt;&lt;MT&gt; _ownedMembers = this.getOwnedMembers();\n\t\t\t&lt;%org.eclipse.n4js.ts.types.internal.MemberByNameAndAccessMap%&gt; _memberByNameAndAccessMap = new &lt;%org.eclipse.n4js.ts.types.internal.MemberByNameAndAccessMap%&gt;(_ownedMembers);\n\t\t\t_switchResult = &lt;%java.util.Collections%&gt;.&lt;&lt;%org.eclipse.n4js.ts.types.NameAndAccess%&gt;, &lt;%org.eclipse.n4js.ts.types.TMember%&gt;&gt;unmodifiableMap(_memberByNameAndAccessMap);\n\t\t\tbreak;\n\t}\n\tfinal &lt;%java.util.Map%&gt;&lt;&lt;%org.eclipse.n4js.ts.types.NameAndAccess%&gt;, ? extends &lt;%org.eclipse.n4js.ts.types.TMember%&gt;&gt; newRegistry = _switchResult;\n\tfinal &lt;%org.eclipse.xtext.xbase.lib.Procedures.Procedure0%&gt; _function = new &lt;%org.eclipse.xtext.xbase.lib.Procedures.Procedure0%&gt;()\n\t{\n\t\tpublic void apply()\n\t\t{\n\t\t\t&lt;%this%&gt;.setOwnedMembersByNameAndAccess(newRegistry);\n\t\t}\n\t};\n\t&lt;%org.eclipse.n4js.utils.EcoreUtilN4%&gt;.doWithDeliver(false, _function, this);\n}\nreturn this.getOwnedMembersByNameAndAccess();'"
	 * @generated
	 */
	Map<NameAndAccess, ? extends TMember> getOrCreateOwnedMembersByNameAndAccess();

} // ContainerType
