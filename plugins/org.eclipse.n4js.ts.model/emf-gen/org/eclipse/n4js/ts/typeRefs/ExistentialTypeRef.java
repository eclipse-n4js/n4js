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

import java.util.UUID;

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
 * S. Wehr and P. Thiemann: Subtyping Existential Types. 2008, <a href="http://www.stefanwehr.de/publications/Wehr_Subtyping_existential_types.pdf">PDF</a><br/>
 * N. Cameron, S. Drossopoulou, and E. Ernst: A Model for Java with Wildcards. Springer, 2008,<a href="https://doi.org/10.1007/978-3-540-70592-5_2">DOI</a><br/>
 * M. Torgersen, E. Ernst, and C. P. Hansen: Wild FJ. 2005
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#isReopened <em>Reopened</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#getWildcard <em>Wildcard</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getExistentialTypeRef()
 * @model
 * @generated
 */
public interface ExistentialTypeRef extends TypeRef {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Globally unique identifier of this capture. Required due to the copying semantics of
	 * {@code TypeRef}s: unlike types, type references may be copied at will and therefore a
	 * plain POJO identity comparison of two ExistentialTypeRefs cannot be used to check if
	 * two {@code ExistentialTypeRef}s refer to the same capture.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(UUID)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getExistentialTypeRef_Id()
	 * @model unique="false" dataType="org.eclipse.n4js.ts.typeRefs.UUID"
	 * @generated
	 */
	UUID getId();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(UUID value);

	/**
	 * Returns the value of the '<em><b>Reopened</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If true, this existential type roughly behaves like the wildcard it was created from.
	 * For detailed semantics see {@code SubtypeJudgment#applyExistentialTypeRef_Left|Right()}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Reopened</em>' attribute.
	 * @see #setReopened(boolean)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getExistentialTypeRef_Reopened()
	 * @model unique="false"
	 * @generated
	 */
	boolean isReopened();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#isReopened <em>Reopened</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reopened</em>' attribute.
	 * @see #isReopened()
	 * @generated
	 */
	void setReopened(boolean value);

	/**
	 * Returns the value of the '<em><b>Wildcard</b></em>' reference.
	 * <!-- begin-user-doc -->
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
	 * Overrides {@link TypeRef#internalGetTypeRefAsString()}
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String internalGetTypeRefAsString();

} // ExistentialTypeRef
