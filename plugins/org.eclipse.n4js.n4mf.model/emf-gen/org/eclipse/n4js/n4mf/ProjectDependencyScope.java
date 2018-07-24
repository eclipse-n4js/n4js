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
package org.eclipse.n4js.n4mf;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Project Dependency Scope</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * *
 * A version bound describes a side of an version range.
 * A version bound can be inclusive or exclusive,
 * so e.g. in (1.0.0, 2.0.0] 1.0.0 is excluded,
 * but 2.0.0 is included, so all versions x with
 * 1.0.0 < x <= 2.0.0 are allowed. If only a lowerVersion
 * is assigned this means [version, infinity).
 * <!-- end-model-doc -->
 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDependencyScope()
 * @model
 * @generated
 */
public enum ProjectDependencyScope implements Enumerator {
	/**
	 * The '<em><b>COMPILE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #COMPILE_VALUE
	 * @generated
	 * @ordered
	 */
	COMPILE(0, "COMPILE", "COMPILE"),

	/**
	 * The '<em><b>TEST</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TEST_VALUE
	 * @generated
	 * @ordered
	 */
	TEST(1, "TEST", "TEST");

	/**
	 * The '<em><b>COMPILE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>COMPILE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #COMPILE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int COMPILE_VALUE = 0;

	/**
	 * The '<em><b>TEST</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>TEST</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TEST
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int TEST_VALUE = 1;

	/**
	 * An array of all the '<em><b>Project Dependency Scope</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ProjectDependencyScope[] VALUES_ARRAY =
		new ProjectDependencyScope[] {
			COMPILE,
			TEST,
		};

	/**
	 * A public read-only list of all the '<em><b>Project Dependency Scope</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ProjectDependencyScope> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Project Dependency Scope</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ProjectDependencyScope get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ProjectDependencyScope result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Project Dependency Scope</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ProjectDependencyScope getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ProjectDependencyScope result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Project Dependency Scope</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ProjectDependencyScope get(int value) {
		switch (value) {
			case COMPILE_VALUE: return COMPILE;
			case TEST_VALUE: return TEST;
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
	private ProjectDependencyScope(int value, String name, String literal) {
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
	
} //ProjectDependencyScope
