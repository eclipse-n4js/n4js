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
 * A representation of the literals of the enumeration '<em><b>Module Filter Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * *
 * Categorizes a filter to mark paths/files to be treated in a special way:
 * all files included in NO_VALIDATE won't be validated, for all files
 * included in NO_MODULE_WRAPPING the modules won't be wrapped and
 * IMPL_PROV_BY_RUNTIME specifies the path pattern to look up the
 * implementation provided by the runtime.
 * <!-- end-model-doc -->
 * @see org.eclipse.n4js.n4mf.N4mfPackage#getModuleFilterType()
 * @model
 * @generated
 */
public enum ModuleFilterType implements Enumerator {
	/**
	 * The '<em><b>NO VALIDATE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NO_VALIDATE_VALUE
	 * @generated
	 * @ordered
	 */
	NO_VALIDATE(0, "NO_VALIDATE", "NO_VALIDATE"),

	/**
	 * The '<em><b>NO MODULE WRAPPING</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NO_MODULE_WRAPPING_VALUE
	 * @generated
	 * @ordered
	 */
	NO_MODULE_WRAPPING(1, "NO_MODULE_WRAPPING", "NO_MODULE_WRAPPING");

	/**
	 * The '<em><b>NO VALIDATE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>NO VALIDATE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NO_VALIDATE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int NO_VALIDATE_VALUE = 0;

	/**
	 * The '<em><b>NO MODULE WRAPPING</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>NO MODULE WRAPPING</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NO_MODULE_WRAPPING
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int NO_MODULE_WRAPPING_VALUE = 1;

	/**
	 * An array of all the '<em><b>Module Filter Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ModuleFilterType[] VALUES_ARRAY =
		new ModuleFilterType[] {
			NO_VALIDATE,
			NO_MODULE_WRAPPING,
		};

	/**
	 * A public read-only list of all the '<em><b>Module Filter Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ModuleFilterType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Module Filter Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ModuleFilterType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ModuleFilterType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Module Filter Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ModuleFilterType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ModuleFilterType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Module Filter Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ModuleFilterType get(int value) {
		switch (value) {
			case NO_VALIDATE_VALUE: return NO_VALIDATE;
			case NO_MODULE_WRAPPING_VALUE: return NO_MODULE_WRAPPING;
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
	private ModuleFilterType(int value, String name, String literal) {
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
	
} //ModuleFilterType
