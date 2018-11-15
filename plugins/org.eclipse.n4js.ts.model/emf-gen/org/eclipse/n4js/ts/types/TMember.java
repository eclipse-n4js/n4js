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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TMember</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TMember#isDeclaredFinal <em>Declared Final</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TMember#isDeclaredStatic <em>Declared Static</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TMember#isDeclaredOverride <em>Declared Override</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TMember#isHasComputedName <em>Has Computed Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TMember#getConstituentMembers <em>Constituent Members</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TMember#isComposed <em>Composed</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMember()
 * @model abstract="true"
 * @generated
 */
public interface TMember extends IdentifiableElement, TAnnotableElement, SyntaxRelatedTElement {
	/**
	 * Returns the value of the '<em><b>Declared Final</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared Final</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Final</em>' attribute.
	 * @see #setDeclaredFinal(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMember_DeclaredFinal()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredFinal();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TMember#isDeclaredFinal <em>Declared Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Final</em>' attribute.
	 * @see #isDeclaredFinal()
	 * @generated
	 */
	void setDeclaredFinal(boolean value);

	/**
	 * Returns the value of the '<em><b>Declared Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared Static</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Static</em>' attribute.
	 * @see #setDeclaredStatic(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMember_DeclaredStatic()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredStatic();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TMember#isDeclaredStatic <em>Declared Static</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Static</em>' attribute.
	 * @see #isDeclaredStatic()
	 * @generated
	 */
	void setDeclaredStatic(boolean value);

	/**
	 * Returns the value of the '<em><b>Declared Override</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared Override</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Override</em>' attribute.
	 * @see #setDeclaredOverride(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMember_DeclaredOverride()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredOverride();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TMember#isDeclaredOverride <em>Declared Override</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Override</em>' attribute.
	 * @see #isDeclaredOverride()
	 * @generated
	 */
	void setDeclaredOverride(boolean value);

	/**
	 * Returns the value of the '<em><b>Has Computed Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Has Computed Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Has Computed Name</em>' attribute.
	 * @see #setHasComputedName(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMember_HasComputedName()
	 * @model unique="false"
	 * @generated
	 */
	boolean isHasComputedName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TMember#isHasComputedName <em>Has Computed Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has Computed Name</em>' attribute.
	 * @see #isHasComputedName()
	 * @generated
	 */
	void setHasComputedName(boolean value);

	/**
	 * Returns the value of the '<em><b>Constituent Members</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TMember}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * The constituent members if this member is a composed member.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Constituent Members</em>' reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMember_ConstituentMembers()
	 * @model transient="true"
	 * @generated
	 */
	EList<TMember> getConstituentMembers();

	/**
	 * Returns the value of the '<em><b>Composed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * Tells if this member is a composed member created by {@code ComposedMemberFactory}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Composed</em>' attribute.
	 * @see #setComposed(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMember_Composed()
	 * @model unique="false"
	 * @generated
	 */
	boolean isComposed();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TMember#isComposed <em>Composed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Composed</em>' attribute.
	 * @see #isComposed()
	 * @generated
	 */
	void setComposed(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns the container casted to a container type or 'null'
	 * if the container is not of type ContainerType.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	ContainerType<?> getContainingType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns either the declared access modifier (if possible) or
	 * derives the access modifier from the type access modifier of the containing type.
	 * This method needs to be implemented by subclasses, in particular by {@link TMemberWithAccessModifier}
	 * Also see [N4JSSpec] Constraints 2 (Default Member Access Modifiers)
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	MemberAccessModifier getMemberAccessModifier();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the member type enum, which is useful for validation etc.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	MemberType getMemberType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns true if member type is MemberType.FIELD
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isField();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns true if member type is MemberType.GETTER
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isGetter();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns true if member type is MemberType.SETTER
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isSetter();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns true if member type is MemberType.SETTER or MemberType.GETTER
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isAccessor();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns true if member type is MemberType.METHOD
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isMethod();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isConstructor();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tells whether the entire member is optional. Applies only to data fields and field accessors (not methods).
	 * Don't confuse this with optional return types of methods, see {@link TFunction#isReturnValueOptional()}.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isOptional();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Default implementation always returns false, which is actually only true for fields.
	 * Note however that "concrete" fields defined in interfaces may be overridden.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isAbstract();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isReadable();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isWriteable();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getMemberAsString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns value of declaredFinal field.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isFinal();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns value of declaredStatic field.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isStatic();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if this member is a const field.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isConst();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method returns true, if containing type is either a dynamic or static polyfill and therefore
	 * this member has been poly-filled.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isPolyfilled();

} // TMember
