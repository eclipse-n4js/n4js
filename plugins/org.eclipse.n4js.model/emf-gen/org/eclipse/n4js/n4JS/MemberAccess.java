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

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.ts.types.ComposedMemberCache;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Member Access</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An AST node accessing the member of a ContainerType via member scoping. In the default case, member scoping will
 * require an object of this type as 'context', cf. {@code MemberScopingHelper#createMemberScope()}.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.MemberAccess#getComposedMemberCache <em>Composed Member Cache</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getMemberAccess()
 * @model abstract="true"
 * @generated
 */
public interface MemberAccess extends EObject {
	/**
	 * Returns the value of the '<em><b>Composed Member Cache</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This points to a cache of members that can be accessed via member access on union/intersection
	 * types directly, because they are, for example, common to all types contained in a UnionTypeExpression.
	 * These members are cached for two reasons: (1) performance, (2) TMembers have to be contained in a resource.
	 * The cache will be filled lazily by ComposedMemberScope, so client code should usually not assume this
	 * information to be complete and best not use it at all. Instead, access these members only via the scoping.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Composed Member Cache</em>' reference.
	 * @see #setComposedMemberCache(ComposedMemberCache)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getMemberAccess_ComposedMemberCache()
	 * @model transient="true"
	 * @generated
	 */
	ComposedMemberCache getComposedMemberCache();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.MemberAccess#getComposedMemberCache <em>Composed Member Cache</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Composed Member Cache</em>' reference.
	 * @see #getComposedMemberCache()
	 * @generated
	 */
	void setComposedMemberCache(ComposedMemberCache value);

} // MemberAccess
