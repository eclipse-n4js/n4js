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

import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TypingStrategy;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base class for all type references
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.TypeRef#isFollowedByQuestionMark <em>Followed By Question Mark</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.TypeRef#getOriginalAliasTypeRef <em>Original Alias Type Ref</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getTypeRef()
 * @model abstract="true"
 * @generated
 */
public interface TypeRef extends TypeArgument {
	/**
	 * Returns the value of the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * <b>SHOULD NOT BE USED, EXCEPT FOR DECLARATION OF OPTIONAL RETURN VALUES</b>
	 * <p>
	 * Represents the old ? after a type reference for defining optional fields / return values. At the moment, this is
	 * used only for declaring the return value of a function optional. In the future, this might be used for
	 * undefined/null-analysis. For example, <code>string?</code> might be syntactic sugar for
	 * <code>string|undefined</code>.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Followed By Question Mark</em>' attribute.
	 * @see #setFollowedByQuestionMark(boolean)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getTypeRef_FollowedByQuestionMark()
	 * @model unique="false"
	 * @generated
	 */
	boolean isFollowedByQuestionMark();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#isFollowedByQuestionMark <em>Followed By Question Mark</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Followed By Question Mark</em>' attribute.
	 * @see #isFollowedByQuestionMark()
	 * @generated
	 */
	void setFollowedByQuestionMark(boolean value);

	/**
	 * Returns the value of the '<em><b>Original Alias Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If this type reference represents a resolved(!) reference to an alias, this property is
	 * the original, unresolved type reference to the alias, i.e. {@link TypeRef#isAliasUnresolved()}
	 * will always return <code>true</code> for the value of this property. In all other cases,
	 * this property is <code>null</code>.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Original Alias Type Ref</em>' containment reference.
	 * @see #setOriginalAliasTypeRef(ParameterizedTypeRef)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getTypeRef_OriginalAliasTypeRef()
	 * @model containment="true"
	 * @generated
	 */
	ParameterizedTypeRef getOriginalAliasTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#getOriginalAliasTypeRef <em>Original Alias Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Original Alias Type Ref</em>' containment reference.
	 * @see #getOriginalAliasTypeRef()
	 * @generated
	 */
	void setOriginalAliasTypeRef(ParameterizedTypeRef value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns all type modifiers of the receiving type reference as a string, similar to N4JS syntax. This method is
	 * called by subclasses in overridden method. Currently, the <code>+</code> for dynamic is the only existing type
	 * modifier.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getModifiersAsString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides {@link TypeArgument#isTypeRef()}.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isTypeRef();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns true iff this is an <em>unresolved</em> reference to a type alias. For the difference
	 * between resolved and unresolved references to type aliases, see {@link #isAliasResolved()}.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isAliasUnresolved();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns true iff this is a <em>resolved</em> reference to a type alias.
	 * <p>
	 * For comparison:
	 * <ul>
	 * <li>An <em>unresolved</em> reference to a type alias is always a {@link ParameterizedTypeRef} with
	 * a {@link ParameterizedTypeRef#getDeclaredType() declared type} that is an instance of {@link TypeAlias}.
	 * <li>A <em>resolved</em> reference to a type alias can be any kind of type reference (i.e. an instance of
	 * any subclass of {@link TypeRef}), depending on the aliased (or "actual") type of the type alias. It always
	 * contains a copy of the original, unresolved reference it was derived from (returned by {@link #getOriginalAliasTypeRef()}).
	 * </ul>
	 * For example, given the declaration
	 * <pre>
	 * type MyAlias = (string)=>number;
	 * </pre>
	 * in the N4JS source code, an unresolved reference to this type alias would be a {@code ParameterizedTypeRef}
	 * with 'MyAlias' as {@link ParameterizedTypeRef#getDeclaredType() declared type}. A resolved reference to this
	 * type alias would be a {@link FunctionTypeExpression} with a {@code string} parameter and a return type of
	 * {@code number}.
	 * <p>
	 * The reason for having type alias resolution is that we do not yet properly hide all characteristics of
	 * a type reference behind a single, common interface and that our code base is heavily using instanceof checks
	 * to find out the nature of a type reference, which would fail in case of unresolved type references.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isAliasResolved();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns true if the type referenced is either class which is declared final, an enum or a primitive. This is needed in case of type
	 * casts, for example.
	 * <p>
	 * The method is robust, if declared type is null, false is returned.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isFinalByType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tells if this type reference has a declared type which is "array like" as defined by {@link Type#isArrayLike()}.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isArrayLike();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Tells whether the receiving type reference is an {@link UnknownTypeRef}.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isUnknown();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, flag indicating whether user can add properties to the instance not defined in the type.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isDynamic();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, flag indicating whether the type reference is an existential type created on the fly
	 * by the type inferencer from parameterized types with wildcards.
	 * This returns false for all type references except {@link ExistentialTypeRef}
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isExistential();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns true if and only if the referenced type is generic, i.e. declared type variables.
	 * Note that it may be possible that the referenced type is generic, but the reference is not parameterized
	 * (raw reference), but not vice versa.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isGeneric();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns true if and only if the reference has type arguments. Note that it may be possible that
	 * the referenced type is generic, but the reference is not parameterized (raw reference), but not vice versa.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isParameterized();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns true if and only if the referenced type is generic <em>and</em> the reference has
	 * fewer type arguments than the number of type parameters of the referenced type.<p>
	 * Note that N4JS does not allow raw type references as in Java, for example. However, raw type references can
	 * occur due to (1) a broken AST (2) during type inference in InferenceContext (when deriving constraints from
	 * ClassifierTypeRefs) and (3) bugs in the type system and other internal code.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isRaw();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method to avoid type casts, does return null except type ref is a wildcard.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	ParameterizedTypeRef getDeclaredUpperBound();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method to avoid type casts, does return null except type ref is a wildcard.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	ParameterizedTypeRef getDeclaredLowerBound();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method to avoid type casts, does return unmodifiable empty list for all type references except parameterized type refs.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<TypeArgument> getDeclaredTypeArgs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method to avoid type casts, does return unmodifiable empty list for all type references except parameterized type refs.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<TypeArgument> getTypeArgsWithDefaults();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Overrides {@link TypeArgument#getTypeRefAsString(boolean)}.
	 * <!-- end-model-doc -->
	 * @model unique="false" resolveProxiesUnique="false"
	 * @generated
	 */
	String getTypeRefAsString(boolean resolveProxies);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Overrides {@link TypeArgument#getTypeRefAsStringWithAliasExpansion(boolean)}.
	 * <!-- end-model-doc -->
	 * @model unique="false" resolveProxiesUnique="false"
	 * @generated
	 */
	String getTypeRefAsStringWithAliasExpansion(boolean resolveProxies);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Convenience method, returns only true for parameterized type refs if the declared type is any
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isTopType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Convenience method, returns only true for parameterized type refs if the declared type is undefined
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isBottomType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the typing strategy, either the use or def site, usually NOMINAL.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TypingStrategy getTypingStrategy();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns empty list for simply reference by default.
	 * Overridden in {@link ParameterizedTypeRefStructural}
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<TStructMember> getStructuralMembers();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns true if type ref is structural. This is different from {@link #getTypingStrategy}, which
	 * returns true if either use or def site structural typing is true.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isStructuralTyping();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns true if type ref is structural. This is different from {@link #getTypingStrategy}, which
	 * returns true if either use or def site structural typing is true.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isUseSiteStructuralTyping();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns true if type is defined structurally defined. This is different from {@link #getTypingStrategy}, which
	 * returns true if either use or def site structural typing is true.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isDefSiteStructuralTyping();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tells the optional field strategy.
	 * Used to activate the special semantics of optional fields in certain cases.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	OptionalFieldStrategy getASTNodeOptionalFieldStrategy();

} // TypeRef
