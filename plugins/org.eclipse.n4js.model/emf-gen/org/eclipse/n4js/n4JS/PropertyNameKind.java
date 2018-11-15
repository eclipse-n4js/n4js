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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Property Name Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getPropertyNameKind()
 * @model
 * @generated
 */
public enum PropertyNameKind implements Enumerator {
	/**
	 * The '<em><b>Identifier</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The name is assigned from a object literal property with
	 * an identifier as the concrete syntax for the name.
	 * <!-- end-model-doc -->
	 * @see #IDENTIFIER_VALUE
	 * @generated
	 * @ordered
	 */
	IDENTIFIER(0, "identifier", "identifier"),

	/**
	 * The '<em><b>String</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The name is assigned from a object literal property with
	 * a string literal as the concrete syntax for the name. This variant is discouraged.
	 * <!-- end-model-doc -->
	 * @see #STRING_VALUE
	 * @generated
	 * @ordered
	 */
	STRING(0, "string", "string"),

	/**
	 * The '<em><b>Number</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The name is assigned from a object literal property with
	 * a number as the concrete syntax for the name. This variant is discouraged.
	 * <!-- end-model-doc -->
	 * @see #NUMBER_VALUE
	 * @generated
	 * @ordered
	 */
	NUMBER(0, "number", "number"),

	/**
	 * The '<em><b>Computed</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The name is assigned from a object literal property with
	 * a computed property name expression as the concrete syntax for the name. This variant is discouraged.
	 * <!-- end-model-doc -->
	 * @see #COMPUTED_VALUE
	 * @generated
	 * @ordered
	 */
	COMPUTED(0, "computed", "computed");

	/**
	 * The '<em><b>Identifier</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The name is assigned from a object literal property with
	 * an identifier as the concrete syntax for the name.
	 * <!-- end-model-doc -->
	 * @see #IDENTIFIER
	 * @model name="identifier"
	 * @generated
	 * @ordered
	 */
	public static final int IDENTIFIER_VALUE = 0;

	/**
	 * The '<em><b>String</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The name is assigned from a object literal property with
	 * a string literal as the concrete syntax for the name. This variant is discouraged.
	 * <!-- end-model-doc -->
	 * @see #STRING
	 * @model name="string"
	 * @generated
	 * @ordered
	 */
	public static final int STRING_VALUE = 0;

	/**
	 * The '<em><b>Number</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The name is assigned from a object literal property with
	 * a number as the concrete syntax for the name. This variant is discouraged.
	 * <!-- end-model-doc -->
	 * @see #NUMBER
	 * @model name="number"
	 * @generated
	 * @ordered
	 */
	public static final int NUMBER_VALUE = 0;

	/**
	 * The '<em><b>Computed</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The name is assigned from a object literal property with
	 * a computed property name expression as the concrete syntax for the name. This variant is discouraged.
	 * <!-- end-model-doc -->
	 * @see #COMPUTED
	 * @model name="computed"
	 * @generated
	 * @ordered
	 */
	public static final int COMPUTED_VALUE = 0;

	/**
	 * An array of all the '<em><b>Property Name Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final PropertyNameKind[] VALUES_ARRAY =
		new PropertyNameKind[] {
			IDENTIFIER,
			STRING,
			NUMBER,
			COMPUTED,
		};

	/**
	 * A public read-only list of all the '<em><b>Property Name Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<PropertyNameKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Property Name Kind</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static PropertyNameKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PropertyNameKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Property Name Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static PropertyNameKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PropertyNameKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Property Name Kind</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static PropertyNameKind get(int value) {
		switch (value) {
			case IDENTIFIER_VALUE: return IDENTIFIER;
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
	private PropertyNameKind(int value, String name, String literal) {
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
	
} //PropertyNameKind
