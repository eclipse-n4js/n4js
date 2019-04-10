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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Optional Field Strategy</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Optional field strategy enum defines three strategies for handling optional fields while checking structural subtypes.
 * The optional field strategy is calculated based on the kind of AST node. Each {@code TypeRef} is associated with an optional field strategy.
 * <p>
 * It is important to note that this optional field strategy is introduced to as a workaround to solve the transitivity problem with structural subtyping
 * in the presence of optional fields. As opposed to e.g. typing strategy, the optional field strategy is NOT passed through the type system in anyway.
 * <p>
 * For further details, see IDE-2405, GHOLD-411.
 * <!-- end-model-doc -->
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getOptionalFieldStrategy()
 * @model
 * @generated
 */
public enum OptionalFieldStrategy implements Enumerator {
	/**
	 * The '<em><b>OFF</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * Default: optionality is turned of, e.g. both fields and accessors are mandatory
	 * <!-- end-model-doc -->
	 * @see #OFF_VALUE
	 * @generated
	 * @ordered
	 */
	OFF(0, "OFF", "OFF"),

	/**
	 * The '<em><b>GETTERS OPTIONAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * Only getters are optional
	 * <!-- end-model-doc -->
	 * @see #GETTERS_OPTIONAL_VALUE
	 * @generated
	 * @ordered
	 */
	GETTERS_OPTIONAL(1, "GETTERS_OPTIONAL", "GETTERS_OPTIONAL"),

	/**
	 * The '<em><b>FIELDS AND ACCESSORS OPTIONAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * Both fields and accessors are optional
	 * <!-- end-model-doc -->
	 * @see #FIELDS_AND_ACCESSORS_OPTIONAL_VALUE
	 * @generated
	 * @ordered
	 */
	FIELDS_AND_ACCESSORS_OPTIONAL(2, "FIELDS_AND_ACCESSORS_OPTIONAL", "FIELDS_AND_ACCESSORS_OPTIONAL");

	/**
	 * The '<em><b>OFF</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * Default: optionality is turned of, e.g. both fields and accessors are mandatory
	 * <!-- end-model-doc -->
	 * @see #OFF
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OFF_VALUE = 0;

	/**
	 * The '<em><b>GETTERS OPTIONAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * Only getters are optional
	 * <!-- end-model-doc -->
	 * @see #GETTERS_OPTIONAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int GETTERS_OPTIONAL_VALUE = 1;

	/**
	 * The '<em><b>FIELDS AND ACCESSORS OPTIONAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * Both fields and accessors are optional
	 * <!-- end-model-doc -->
	 * @see #FIELDS_AND_ACCESSORS_OPTIONAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int FIELDS_AND_ACCESSORS_OPTIONAL_VALUE = 2;

	/**
	 * An array of all the '<em><b>Optional Field Strategy</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final OptionalFieldStrategy[] VALUES_ARRAY =
		new OptionalFieldStrategy[] {
			OFF,
			GETTERS_OPTIONAL,
			FIELDS_AND_ACCESSORS_OPTIONAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Optional Field Strategy</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<OptionalFieldStrategy> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Optional Field Strategy</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static OptionalFieldStrategy get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OptionalFieldStrategy result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Optional Field Strategy</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static OptionalFieldStrategy getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OptionalFieldStrategy result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Optional Field Strategy</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static OptionalFieldStrategy get(int value) {
		switch (value) {
			case OFF_VALUE: return OFF;
			case GETTERS_OPTIONAL_VALUE: return GETTERS_OPTIONAL;
			case FIELDS_AND_ACCESSORS_OPTIONAL_VALUE: return FIELDS_AND_ACCESSORS_OPTIONAL;
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
	private OptionalFieldStrategy(int value, String name, String literal) {
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
	
} //OptionalFieldStrategy
