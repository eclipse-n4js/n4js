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
package org.eclipse.n4js.projectDescription;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Project Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectType()
 * @model
 * @generated
 */
public enum ProjectType implements Enumerator {
	/**
	 * The '<em><b>PLAINJS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PLAINJS_VALUE
	 * @generated
	 * @ordered
	 */
	PLAINJS(0, "PLAINJS", "PLAINJS"),

	/**
	 * The '<em><b>VALIDATION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VALIDATION_VALUE
	 * @generated
	 * @ordered
	 */
	VALIDATION(1, "VALIDATION", "VALIDATION"),

	/**
	 * The '<em><b>DEFINITION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DEFINITION_VALUE
	 * @generated
	 * @ordered
	 */
	DEFINITION(2, "DEFINITION", "DEFINITION"),

	/**
	 * The '<em><b>APPLICATION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #APPLICATION_VALUE
	 * @generated
	 * @ordered
	 */
	APPLICATION(3, "APPLICATION", "APPLICATION"),

	/**
	 * The '<em><b>PROCESSOR</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROCESSOR_VALUE
	 * @generated
	 * @ordered
	 */
	PROCESSOR(4, "PROCESSOR", "PROCESSOR"),

	/**
	 * The '<em><b>LIBRARY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LIBRARY_VALUE
	 * @generated
	 * @ordered
	 */
	LIBRARY(5, "LIBRARY", "LIBRARY"),

	/**
	 * The '<em><b>API</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #API_VALUE
	 * @generated
	 * @ordered
	 */
	API(6, "API", "API"),

	/**
	 * The '<em><b>RUNTIME ENVIRONMENT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RUNTIME_ENVIRONMENT_VALUE
	 * @generated
	 * @ordered
	 */
	RUNTIME_ENVIRONMENT(7, "RUNTIME_ENVIRONMENT", "RUNTIME_ENVIRONMENT"),

	/**
	 * The '<em><b>RUNTIME LIBRARY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RUNTIME_LIBRARY_VALUE
	 * @generated
	 * @ordered
	 */
	RUNTIME_LIBRARY(8, "RUNTIME_LIBRARY", "RUNTIME_LIBRARY"),

	/**
	 * The '<em><b>TEST</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TEST_VALUE
	 * @generated
	 * @ordered
	 */
	TEST(9, "TEST", "TEST");

	/**
	 * The '<em><b>PLAINJS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PLAINJS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PLAINJS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PLAINJS_VALUE = 0;

	/**
	 * The '<em><b>VALIDATION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>VALIDATION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #VALIDATION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int VALIDATION_VALUE = 1;

	/**
	 * The '<em><b>DEFINITION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DEFINITION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DEFINITION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DEFINITION_VALUE = 2;

	/**
	 * The '<em><b>APPLICATION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>APPLICATION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #APPLICATION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int APPLICATION_VALUE = 3;

	/**
	 * The '<em><b>PROCESSOR</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PROCESSOR</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROCESSOR
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PROCESSOR_VALUE = 4;

	/**
	 * The '<em><b>LIBRARY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>LIBRARY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LIBRARY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int LIBRARY_VALUE = 5;

	/**
	 * The '<em><b>API</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>API</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #API
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int API_VALUE = 6;

	/**
	 * The '<em><b>RUNTIME ENVIRONMENT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RUNTIME ENVIRONMENT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RUNTIME_ENVIRONMENT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int RUNTIME_ENVIRONMENT_VALUE = 7;

	/**
	 * The '<em><b>RUNTIME LIBRARY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RUNTIME LIBRARY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RUNTIME_LIBRARY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int RUNTIME_LIBRARY_VALUE = 8;

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
	public static final int TEST_VALUE = 9;

	/**
	 * An array of all the '<em><b>Project Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ProjectType[] VALUES_ARRAY =
		new ProjectType[] {
			PLAINJS,
			VALIDATION,
			DEFINITION,
			APPLICATION,
			PROCESSOR,
			LIBRARY,
			API,
			RUNTIME_ENVIRONMENT,
			RUNTIME_LIBRARY,
			TEST,
		};

	/**
	 * A public read-only list of all the '<em><b>Project Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ProjectType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Project Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ProjectType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ProjectType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Project Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ProjectType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ProjectType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Project Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ProjectType get(int value) {
		switch (value) {
			case PLAINJS_VALUE: return PLAINJS;
			case VALIDATION_VALUE: return VALIDATION;
			case DEFINITION_VALUE: return DEFINITION;
			case APPLICATION_VALUE: return APPLICATION;
			case PROCESSOR_VALUE: return PROCESSOR;
			case LIBRARY_VALUE: return LIBRARY;
			case API_VALUE: return API;
			case RUNTIME_ENVIRONMENT_VALUE: return RUNTIME_ENVIRONMENT;
			case RUNTIME_LIBRARY_VALUE: return RUNTIME_LIBRARY;
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
	private ProjectType(int value, String name, String literal) {
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
	
} //ProjectType
