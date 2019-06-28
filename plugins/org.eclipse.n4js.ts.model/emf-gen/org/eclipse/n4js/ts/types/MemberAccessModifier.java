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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Member Access Modifier</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Member access modifier as described in the N4JSSpec. Note that some languages may represent the
 * this modifier differently, e.g., in N4JS, the publicApi type is expressed via the public modifier
 * and the Api annotation.
 * For helper (relations etc.) see AccessModifiers.
 * @see org.eclipse.n4js.ts.model.TMember.getMemberAccessModifier
 * <!-- end-model-doc -->
 * @see org.eclipse.n4js.ts.types.TypesPackage#getMemberAccessModifier()
 * @model
 * @generated
 */
public enum MemberAccessModifier implements Enumerator {
	/**
	 * The '<em><b>Undefined</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNDEFINED_VALUE
	 * @generated
	 * @ordered
	 */
	UNDEFINED(0, "undefined", "undefined"),

	/**
	 * The '<em><b>Private</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PRIVATE_VALUE
	 * @generated
	 * @ordered
	 */
	PRIVATE(1, "private", "private"),

	/**
	 * The '<em><b>Project</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROJECT_VALUE
	 * @generated
	 * @ordered
	 */
	PROJECT(2, "project", "project"),

	/**
	 * The '<em><b>Protected Internal</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROTECTED_INTERNAL_VALUE
	 * @generated
	 * @ordered
	 */
	PROTECTED_INTERNAL(3, "protectedInternal", "protectedInternal"),

	/**
	 * The '<em><b>Protected</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROTECTED_VALUE
	 * @generated
	 * @ordered
	 */
	PROTECTED(4, "protected", "protected"),

	/**
	 * The '<em><b>Public Internal</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PUBLIC_INTERNAL_VALUE
	 * @generated
	 * @ordered
	 */
	PUBLIC_INTERNAL(5, "publicInternal", "publicInternal"),

	/**
	 * The '<em><b>Public</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PUBLIC_VALUE
	 * @generated
	 * @ordered
	 */
	PUBLIC(6, "public", "public");

	/**
	 * The '<em><b>Undefined</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNDEFINED
	 * @model name="undefined"
	 * @generated
	 * @ordered
	 */
	public static final int UNDEFINED_VALUE = 0;

	/**
	 * The '<em><b>Private</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PRIVATE
	 * @model name="private"
	 * @generated
	 * @ordered
	 */
	public static final int PRIVATE_VALUE = 1;

	/**
	 * The '<em><b>Project</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROJECT
	 * @model name="project"
	 * @generated
	 * @ordered
	 */
	public static final int PROJECT_VALUE = 2;

	/**
	 * The '<em><b>Protected Internal</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROTECTED_INTERNAL
	 * @model name="protectedInternal"
	 * @generated
	 * @ordered
	 */
	public static final int PROTECTED_INTERNAL_VALUE = 3;

	/**
	 * The '<em><b>Protected</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROTECTED
	 * @model name="protected"
	 * @generated
	 * @ordered
	 */
	public static final int PROTECTED_VALUE = 4;

	/**
	 * The '<em><b>Public Internal</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PUBLIC_INTERNAL
	 * @model name="publicInternal"
	 * @generated
	 * @ordered
	 */
	public static final int PUBLIC_INTERNAL_VALUE = 5;

	/**
	 * The '<em><b>Public</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PUBLIC
	 * @model name="public"
	 * @generated
	 * @ordered
	 */
	public static final int PUBLIC_VALUE = 6;

	/**
	 * An array of all the '<em><b>Member Access Modifier</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final MemberAccessModifier[] VALUES_ARRAY =
		new MemberAccessModifier[] {
			UNDEFINED,
			PRIVATE,
			PROJECT,
			PROTECTED_INTERNAL,
			PROTECTED,
			PUBLIC_INTERNAL,
			PUBLIC,
		};

	/**
	 * A public read-only list of all the '<em><b>Member Access Modifier</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<MemberAccessModifier> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Member Access Modifier</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static MemberAccessModifier get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			MemberAccessModifier result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Member Access Modifier</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static MemberAccessModifier getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			MemberAccessModifier result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Member Access Modifier</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static MemberAccessModifier get(int value) {
		switch (value) {
			case UNDEFINED_VALUE: return UNDEFINED;
			case PRIVATE_VALUE: return PRIVATE;
			case PROJECT_VALUE: return PROJECT;
			case PROTECTED_INTERNAL_VALUE: return PROTECTED_INTERNAL;
			case PROTECTED_VALUE: return PROTECTED;
			case PUBLIC_INTERNAL_VALUE: return PUBLIC_INTERNAL;
			case PUBLIC_VALUE: return PUBLIC;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private MemberAccessModifier(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //MemberAccessModifier
