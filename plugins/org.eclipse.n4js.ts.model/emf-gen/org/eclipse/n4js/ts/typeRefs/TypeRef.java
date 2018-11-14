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
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getTypeRef()
 * @model abstract="true"
 * @generated
 */
public interface TypeRef extends TypeArgument, Versionable {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns all type modifiers of the receiving type reference as a string, similar to N4JS syntax. This method is
	 * called by subclasses in overridden method. Currently, the <code>+</code> for dynamic is the only existing type
	 * modifier.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return \"\";'"
	 * @generated
	 */
	String getModifiersAsString();

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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final &lt;%org.eclipse.n4js.ts.types.Type%&gt; dtype = this.getDeclaredType();\nreturn ((dtype != null) &amp;&amp; dtype.isFinal());'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final &lt;%org.eclipse.n4js.ts.types.Type%&gt; dtype = this.getDeclaredType();\nreturn ((dtype != null) &amp;&amp; dtype.isArrayLike());'"
	 * @generated
	 */
	boolean isArrayLike();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, flag indicating whether user can add properties to the instance not defined in the type.
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
	 * Convenience method, flag indicating whether the type reference is an existential type created on the fly
	 * by the type inferencer from parameterized types with wildcards.
	 * This returns false for all type references except {@link ExistentialTypeRef}
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return null;'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return null;'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return &lt;%org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals%&gt;.&lt;&lt;%org.eclipse.n4js.ts.typeRefs.TypeArgument%&gt;&gt;emptyEList();'"
	 * @generated
	 */
	EList<TypeArgument> getTypeArgs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns the type expression, usually the type name, as a string. The
	 * returned string representation usually reflect the N4JS syntax. Basically used for testing.
	 * As the returned string is used for comparison in tests, this method should not be changed.
	 * This method actually overrides {@link TypeArgument#getTypeRefAsString()}.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return null;'"
	 * @generated
	 */
	String getTypeRefAsString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.getTypeRefAsString();'"
	 * @generated
	 */
	String toString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Convenience method, returns only true for parameterized type refs if the declared type is any
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.types.Type%&gt; _declaredType = this.getDeclaredType();\nreturn (_declaredType instanceof &lt;%org.eclipse.n4js.ts.types.AnyType%&gt;);'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.types.Type%&gt; _declaredType = this.getDeclaredType();\nreturn (_declaredType instanceof &lt;%org.eclipse.n4js.ts.types.UndefinedType%&gt;);'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return &lt;%org.eclipse.n4js.ts.types.TypingStrategy%&gt;.NOMINAL;'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return &lt;%org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals%&gt;.&lt;&lt;%org.eclipse.n4js.ts.types.TStructMember%&gt;&gt;emptyEList();'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return &lt;%org.eclipse.n4js.ts.typeRefs.OptionalFieldStrategy%&gt;.OFF;'"
	 * @generated
	 */
	OptionalFieldStrategy getASTNodeOptionalFieldStrategy();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Override Versioned#getVersion()
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='int _xifexpression = (int) 0;\n&lt;%org.eclipse.n4js.ts.types.Type%&gt; _declaredType = this.getDeclaredType();\nboolean _tripleNotEquals = (_declaredType != null);\nif (_tripleNotEquals)\n{\n\t_xifexpression = this.getDeclaredType().getVersion();\n}\nelse\n{\n\t_xifexpression = 0;\n}\nreturn _xifexpression;'"
	 * @generated
	 */
	int getVersion();

} // TypeRef
