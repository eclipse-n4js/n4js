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
package org.eclipse.n4js.semver.Semver;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Version Comparator</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getVersionComparator()
 * @model
 * @generated
 */
public enum VersionComparator implements Enumerator {
	/**
	 * The '<em><b>Equals</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EQUALS_VALUE
	 * @generated
	 * @ordered
	 */
	EQUALS(1, "Equals", "Equals"),

	/**
	 * The '<em><b>Tilde</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TILDE_VALUE
	 * @generated
	 * @ordered
	 */
	TILDE(2, "Tilde", "Tilde"),

	/**
	 * The '<em><b>Caret</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CARET_VALUE
	 * @generated
	 * @ordered
	 */
	CARET(3, "Caret", "Caret"),

	/**
	 * The '<em><b>Smaller</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SMALLER_VALUE
	 * @generated
	 * @ordered
	 */
	SMALLER(4, "Smaller", "Smaller"),

	/**
	 * The '<em><b>Smaller Equals</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SMALLER_EQUALS_VALUE
	 * @generated
	 * @ordered
	 */
	SMALLER_EQUALS(5, "SmallerEquals", "SmallerEquals"),

	/**
	 * The '<em><b>Greater</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GREATER_VALUE
	 * @generated
	 * @ordered
	 */
	GREATER(6, "Greater", "Greater"),

	/**
	 * The '<em><b>Greater Equals</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GREATER_EQUALS_VALUE
	 * @generated
	 * @ordered
	 */
	GREATER_EQUALS(7, "GreaterEquals", "GreaterEquals");

	/**
	 * The '<em><b>Equals</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EQUALS
	 * @model name="Equals"
	 * @generated
	 * @ordered
	 */
	public static final int EQUALS_VALUE = 1;

	/**
	 * The '<em><b>Tilde</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TILDE
	 * @model name="Tilde"
	 * @generated
	 * @ordered
	 */
	public static final int TILDE_VALUE = 2;

	/**
	 * The '<em><b>Caret</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CARET
	 * @model name="Caret"
	 * @generated
	 * @ordered
	 */
	public static final int CARET_VALUE = 3;

	/**
	 * The '<em><b>Smaller</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SMALLER
	 * @model name="Smaller"
	 * @generated
	 * @ordered
	 */
	public static final int SMALLER_VALUE = 4;

	/**
	 * The '<em><b>Smaller Equals</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SMALLER_EQUALS
	 * @model name="SmallerEquals"
	 * @generated
	 * @ordered
	 */
	public static final int SMALLER_EQUALS_VALUE = 5;

	/**
	 * The '<em><b>Greater</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GREATER
	 * @model name="Greater"
	 * @generated
	 * @ordered
	 */
	public static final int GREATER_VALUE = 6;

	/**
	 * The '<em><b>Greater Equals</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GREATER_EQUALS
	 * @model name="GreaterEquals"
	 * @generated
	 * @ordered
	 */
	public static final int GREATER_EQUALS_VALUE = 7;

	/**
	 * An array of all the '<em><b>Version Comparator</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final VersionComparator[] VALUES_ARRAY =
		new VersionComparator[] {
			EQUALS,
			TILDE,
			CARET,
			SMALLER,
			SMALLER_EQUALS,
			GREATER,
			GREATER_EQUALS,
		};

	/**
	 * A public read-only list of all the '<em><b>Version Comparator</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<VersionComparator> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Version Comparator</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static VersionComparator get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			VersionComparator result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Version Comparator</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static VersionComparator getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			VersionComparator result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Version Comparator</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static VersionComparator get(int value) {
		switch (value) {
			case EQUALS_VALUE: return EQUALS;
			case TILDE_VALUE: return TILDE;
			case CARET_VALUE: return CARET;
			case SMALLER_VALUE: return SMALLER;
			case SMALLER_EQUALS_VALUE: return SMALLER_EQUALS;
			case GREATER_VALUE: return GREATER;
			case GREATER_EQUALS_VALUE: return GREATER_EQUALS;
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
	private VersionComparator(int value, String name, String literal) {
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
	
} //VersionComparator
