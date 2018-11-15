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

import org.eclipse.n4js.ts.types.TypeVariable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Existential Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Existential type references are parameterized type references inferred from wildcards and
 * bound to type variables during substitution.
 * <p>
 * E.g., the type {@code G<? extends A>} implicitly defines an
 * existential type <i>E</i>, which is a subtype of A, but for all other
 * subtypes <i>S</i> of A, neither <i>E&lt;:S</i> nor <i>S&lt;:E</i> is true.
 * Same is true for wildcards without bounds.
 * <p>
 * Example: Assuming that both B and C extends A, and the following variable declarations:
 * <pre>
 * var G&lt;? extends A> g1 = new G&lt;B>();
 * var G&lt;? extends A> g2 = new G&lt;C>();
 * var G&lt;?> g = new G&lt;A>
 * </pre>
 * With the typical getter/setter methods in G (T get(), set(T)), the following
 * calls must not be allowed:
 * <pre>
 * g1.set(g2.get()); // cannot convert C to B
 * g2.set(g.get());  // cannot convert A to C
 * </pre>
 * Note that the explanation in the example requires knowledge of the actual type
 * which is usually not available at compile time. (That is, in the example, the
 * declared type of g1, g2, and g3 could have been specified without wildcards).
 * <p>
 * For details, see<br/>
 * S. Wehr and P. Thiemann: Subtyping Existential Types. 2008, <a href="http://www.informatik.uni-freiburg.de/~wehr/publications/Wehr_Subtyping_existential_types.pdf">PDF</a><br/>
 * N. Cameron, S. Drossopoulou, and E. Ernst: A Model for Java with Wildcards. Springer, 2008,<a href="http://dx.doi.org/10.1007/978-3-540-70592-5_2">DOI</a><br/>
 * M. Torgersen, E. Ernst, and C. P. Hansen: Wild FJ. 2005
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#getWildcard <em>Wildcard</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#getBoundTypeVariable <em>Bound Type Variable</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getExistentialTypeRef()
 * @model
 * @generated
 */
public interface ExistentialTypeRef extends TypeRef {
	/**
	 * Returns the value of the '<em><b>Wildcard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Wildcard</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Wildcard</em>' reference.
	 * @see #setWildcard(Wildcard)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getExistentialTypeRef_Wildcard()
	 * @model
	 * @generated
	 */
	Wildcard getWildcard();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#getWildcard <em>Wildcard</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Wildcard</em>' reference.
	 * @see #getWildcard()
	 * @generated
	 */
	void setWildcard(Wildcard value);

	/**
	 * Returns the value of the '<em><b>Bound Type Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bound Type Variable</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bound Type Variable</em>' reference.
	 * @see #setBoundTypeVariable(TypeVariable)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getExistentialTypeRef_BoundTypeVariable()
	 * @model
	 * @generated
	 */
	TypeVariable getBoundTypeVariable();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#getBoundTypeVariable <em>Bound Type Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bound Type Variable</em>' reference.
	 * @see #getBoundTypeVariable()
	 * @generated
	 */
	void setBoundTypeVariable(TypeVariable value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Always returns true for existential types.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isExistential();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Always returns true for existential types.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isGeneric();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Always returns true for existential types.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isParameterized();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides {@link TypeRef#getTypeRefAsString()}
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getTypeRefAsString();

} // ExistentialTypeRef
