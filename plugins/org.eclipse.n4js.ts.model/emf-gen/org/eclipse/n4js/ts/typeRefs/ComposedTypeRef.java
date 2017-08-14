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

import org.eclipse.n4js.ts.types.ComposedMemberCache;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composed Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base class for type references containing an ordered set of other types, that is
 * {@link UnionTypeExpression} and {@link IntersectionTypeExpression}.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ComposedTypeRef#getComposedMemberCache <em>Composed Member Cache</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ComposedTypeRef#getTypeRefs <em>Type Refs</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getComposedTypeRef()
 * @model abstract="true"
 * @generated
 */
public interface ComposedTypeRef extends StaticBaseTypeRef {
	/**
	 * Returns the value of the '<em><b>Composed Member Cache</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This points to a cache of members that can be accessed via property access on the union/intersection
	 * type directly, because they are, for example, common to all types contained in a UnionTypeExpression.
	 * These members are cached for two reasons: (1) performance, (2) TMembers have to be contained in a resource.
	 * The cache will be filled lazily by ComposedMemberScope, so client code should usually not assume this
	 * information to be complete and best not use it at all. Instead, access these members only via the scoping.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Composed Member Cache</em>' reference.
	 * @see #setComposedMemberCache(ComposedMemberCache)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getComposedTypeRef_ComposedMemberCache()
	 * @model transient="true"
	 * @generated
	 */
	ComposedMemberCache getComposedMemberCache();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.ComposedTypeRef#getComposedMemberCache <em>Composed Member Cache</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Composed Member Cache</em>' reference.
	 * @see #getComposedMemberCache()
	 * @generated
	 */
	void setComposedMemberCache(ComposedMemberCache value);

	/**
	 * Returns the value of the '<em><b>Type Refs</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.typeRefs.TypeRef}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Refs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Refs</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getComposedTypeRef_TypeRefs()
	 * @model containment="true"
	 * @generated
	 */
	EList<TypeRef> getTypeRefs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Union type cannot be marked as dynamic
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
	 * @generated
	 */
	boolean isDynamic();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides {@link TypeRef#getTypeRefAsString()}, only returns list of composed members, to be called by subclass.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.typeRefs.TypeRef%>, <%java.lang.String%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.ts.typeRefs.TypeRef%>, <%java.lang.String%>>()\n{\n\tpublic <%java.lang.String%> apply(final <%org.eclipse.n4js.ts.typeRefs.TypeRef%> it)\n\t{\n\t\treturn it.getTypeRefAsString();\n\t}\n};\n<%java.lang.String%> _join = <%org.eclipse.xtext.xbase.lib.IterableExtensions%>.join(<%org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions%>.<<%org.eclipse.n4js.ts.typeRefs.TypeRef%>, <%java.lang.String%>>map(this.getTypeRefs(), _function), \",\");\n<%java.lang.String%> _plus = (\"{\" + _join);\n<%java.lang.String%> _plus_1 = (_plus + \"}\");\n<%java.lang.String%> _modifiersAsString = this.getModifiersAsString();\nreturn (_plus_1 + _modifiersAsString);'"
	 * @generated
	 */
	String getTypeRefAsString();

} // ComposedTypeRef
