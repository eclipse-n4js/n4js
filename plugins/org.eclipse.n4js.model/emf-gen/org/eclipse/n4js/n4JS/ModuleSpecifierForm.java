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
 * A representation of the literals of the enumeration '<em><b>Module Specifier Form</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getModuleSpecifierForm()
 * @model
 * @generated
 */
public enum ModuleSpecifierForm implements Enumerator {
	/**
	 * The '<em><b>UNKNOWN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Unknown form. This is the default value and will be changed in {@code ProjectImportEnablingScope}.
	 * <!-- end-model-doc -->
	 * @see #UNKNOWN_VALUE
	 * @generated
	 * @ordered
	 */
	UNKNOWN(0, "UNKNOWN", "UNKNOWN"),

	/**
	 * The '<em><b>PLAIN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A module specifier without leading project name.<p>
	 * Example: <code>import {C} from "path/to/module"</code>.
	 * <!-- end-model-doc -->
	 * @see #PLAIN_VALUE
	 * @generated
	 * @ordered
	 */
	PLAIN(0, "PLAIN", "PLAIN"),

	/**
	 * The '<em><b>COMPLETE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A module specifier including the target project's name.<p>
	 * Example: <code>import {C} from "project-name/path/to/module"</code>.
	 * <!-- end-model-doc -->
	 * @see #COMPLETE_VALUE
	 * @generated
	 * @ordered
	 */
	COMPLETE(0, "COMPLETE", "COMPLETE"),

	/**
	 * The '<em><b>PROJECT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A module specifier consisting only of a project name (a.k.a. "bare import").
	 * The target project defines a main module in its package.json.<p>
	 * Example: <code>import {C} from "project-name"</code>.
	 * <!-- end-model-doc -->
	 * @see #PROJECT_VALUE
	 * @generated
	 * @ordered
	 */
	PROJECT(0, "PROJECT", "PROJECT"),

	/**
	 * The '<em><b>PROJECT NO MAIN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Error case. Same as {@link #PROJECT}, but the target project does not
	 * define a main module in its package.json.
	 * <!-- end-model-doc -->
	 * @see #PROJECT_NO_MAIN_VALUE
	 * @generated
	 * @ordered
	 */
	PROJECT_NO_MAIN(0, "PROJECT_NO_MAIN", "PROJECT_NO_MAIN");

	/**
	 * The '<em><b>UNKNOWN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Unknown form. This is the default value and will be changed in {@code ProjectImportEnablingScope}.
	 * <!-- end-model-doc -->
	 * @see #UNKNOWN
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int UNKNOWN_VALUE = 0;

	/**
	 * The '<em><b>PLAIN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A module specifier without leading project name.<p>
	 * Example: <code>import {C} from "path/to/module"</code>.
	 * <!-- end-model-doc -->
	 * @see #PLAIN
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PLAIN_VALUE = 0;

	/**
	 * The '<em><b>COMPLETE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A module specifier including the target project's name.<p>
	 * Example: <code>import {C} from "project-name/path/to/module"</code>.
	 * <!-- end-model-doc -->
	 * @see #COMPLETE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int COMPLETE_VALUE = 0;

	/**
	 * The '<em><b>PROJECT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A module specifier consisting only of a project name (a.k.a. "bare import").
	 * The target project defines a main module in its package.json.<p>
	 * Example: <code>import {C} from "project-name"</code>.
	 * <!-- end-model-doc -->
	 * @see #PROJECT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PROJECT_VALUE = 0;

	/**
	 * The '<em><b>PROJECT NO MAIN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Error case. Same as {@link #PROJECT}, but the target project does not
	 * define a main module in its package.json.
	 * <!-- end-model-doc -->
	 * @see #PROJECT_NO_MAIN
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PROJECT_NO_MAIN_VALUE = 0;

	/**
	 * An array of all the '<em><b>Module Specifier Form</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ModuleSpecifierForm[] VALUES_ARRAY =
		new ModuleSpecifierForm[] {
			UNKNOWN,
			PLAIN,
			COMPLETE,
			PROJECT,
			PROJECT_NO_MAIN,
		};

	/**
	 * A public read-only list of all the '<em><b>Module Specifier Form</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ModuleSpecifierForm> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Module Specifier Form</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ModuleSpecifierForm get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ModuleSpecifierForm result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Module Specifier Form</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ModuleSpecifierForm getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ModuleSpecifierForm result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Module Specifier Form</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ModuleSpecifierForm get(int value) {
		switch (value) {
			case UNKNOWN_VALUE: return UNKNOWN;
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
	private ModuleSpecifierForm(int value, String name, String literal) {
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
	
} //ModuleSpecifierForm
