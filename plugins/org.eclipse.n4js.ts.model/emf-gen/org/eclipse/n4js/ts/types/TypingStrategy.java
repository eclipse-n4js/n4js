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
 * A representation of the literals of the enumeration '<em><b>Typing Strategy</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Typing strategy value for definition and use site.
 * By default, nominal typing is used. This can be changed to
 * structural typing on both, definition and use site. On use site
 * it is also possible to set this to structuralFields only, which
 * means that only the public fields are considered when computing
 * the structural subtype relation.
 * <!-- end-model-doc -->
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTypingStrategy()
 * @model
 * @generated
 */
public enum TypingStrategy implements Enumerator {
	/**
	 * The '<em><b>Default</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DEFAULT_VALUE
	 * @generated
	 * @ordered
	 */
	DEFAULT(0, "default", "?~"),

	/**
	 * The '<em><b>Nominal</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NOMINAL_VALUE
	 * @generated
	 * @ordered
	 */
	NOMINAL(1, "nominal", ""),

	/**
	 * The '<em><b>Structural</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STRUCTURAL_VALUE
	 * @generated
	 * @ordered
	 */
	STRUCTURAL(2, "structural", "~"),

	/**
	 * The '<em><b>Structural Fields</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STRUCTURAL_FIELDS_VALUE
	 * @generated
	 * @ordered
	 */
	STRUCTURAL_FIELDS(3, "structuralFields", "~~"),

	/**
	 * The '<em><b>Structural Read Only Fields</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STRUCTURAL_READ_ONLY_FIELDS_VALUE
	 * @generated
	 * @ordered
	 */
	STRUCTURAL_READ_ONLY_FIELDS(4, "structuralReadOnlyFields", "~r~"),

	/**
	 * The '<em><b>Structural Write Only Fields</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STRUCTURAL_WRITE_ONLY_FIELDS_VALUE
	 * @generated
	 * @ordered
	 */
	STRUCTURAL_WRITE_ONLY_FIELDS(5, "structuralWriteOnlyFields", "~w~"),

	/**
	 * The '<em><b>Structural Field Initializer</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STRUCTURAL_FIELD_INITIALIZER_VALUE
	 * @generated
	 * @ordered
	 */
	STRUCTURAL_FIELD_INITIALIZER(6, "structuralFieldInitializer", "~i~"),

	/**
	 * The '<em><b>Empty</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EMPTY_VALUE
	 * @generated
	 * @ordered
	 */
	EMPTY(-1, "empty", "~\u2205~");

	/**
	 * The '<em><b>Default</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Default</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DEFAULT
	 * @model name="default" literal="?~"
	 * @generated
	 * @ordered
	 */
	public static final int DEFAULT_VALUE = 0;

	/**
	 * The '<em><b>Nominal</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Nominal</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NOMINAL
	 * @model name="nominal" literal=""
	 * @generated
	 * @ordered
	 */
	public static final int NOMINAL_VALUE = 1;

	/**
	 * The '<em><b>Structural</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Structural</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STRUCTURAL
	 * @model name="structural" literal="~"
	 * @generated
	 * @ordered
	 */
	public static final int STRUCTURAL_VALUE = 2;

	/**
	 * The '<em><b>Structural Fields</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Structural Fields</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STRUCTURAL_FIELDS
	 * @model name="structuralFields" literal="~~"
	 * @generated
	 * @ordered
	 */
	public static final int STRUCTURAL_FIELDS_VALUE = 3;

	/**
	 * The '<em><b>Structural Read Only Fields</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Structural Read Only Fields</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STRUCTURAL_READ_ONLY_FIELDS
	 * @model name="structuralReadOnlyFields" literal="~r~"
	 * @generated
	 * @ordered
	 */
	public static final int STRUCTURAL_READ_ONLY_FIELDS_VALUE = 4;

	/**
	 * The '<em><b>Structural Write Only Fields</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Structural Write Only Fields</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STRUCTURAL_WRITE_ONLY_FIELDS
	 * @model name="structuralWriteOnlyFields" literal="~w~"
	 * @generated
	 * @ordered
	 */
	public static final int STRUCTURAL_WRITE_ONLY_FIELDS_VALUE = 5;

	/**
	 * The '<em><b>Structural Field Initializer</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Structural Field Initializer</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STRUCTURAL_FIELD_INITIALIZER
	 * @model name="structuralFieldInitializer" literal="~i~"
	 * @generated
	 * @ordered
	 */
	public static final int STRUCTURAL_FIELD_INITIALIZER_VALUE = 6;

	/**
	 * The '<em><b>Empty</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Empty</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EMPTY
	 * @model name="empty" literal="~\u2205~"
	 * @generated
	 * @ordered
	 */
	public static final int EMPTY_VALUE = -1;

	/**
	 * An array of all the '<em><b>Typing Strategy</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final TypingStrategy[] VALUES_ARRAY =
		new TypingStrategy[] {
			DEFAULT,
			NOMINAL,
			STRUCTURAL,
			STRUCTURAL_FIELDS,
			STRUCTURAL_READ_ONLY_FIELDS,
			STRUCTURAL_WRITE_ONLY_FIELDS,
			STRUCTURAL_FIELD_INITIALIZER,
			EMPTY,
		};

	/**
	 * A public read-only list of all the '<em><b>Typing Strategy</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<TypingStrategy> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Typing Strategy</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TypingStrategy get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TypingStrategy result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Typing Strategy</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TypingStrategy getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TypingStrategy result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Typing Strategy</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TypingStrategy get(int value) {
		switch (value) {
			case DEFAULT_VALUE: return DEFAULT;
			case NOMINAL_VALUE: return NOMINAL;
			case STRUCTURAL_VALUE: return STRUCTURAL;
			case STRUCTURAL_FIELDS_VALUE: return STRUCTURAL_FIELDS;
			case STRUCTURAL_READ_ONLY_FIELDS_VALUE: return STRUCTURAL_READ_ONLY_FIELDS;
			case STRUCTURAL_WRITE_ONLY_FIELDS_VALUE: return STRUCTURAL_WRITE_ONLY_FIELDS;
			case STRUCTURAL_FIELD_INITIALIZER_VALUE: return STRUCTURAL_FIELD_INITIALIZER;
			case EMPTY_VALUE: return EMPTY;
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
	private TypingStrategy(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
	
} //TypingStrategy
