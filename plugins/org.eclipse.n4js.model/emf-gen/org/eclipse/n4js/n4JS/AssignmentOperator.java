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
 * A representation of the literals of the enumeration '<em><b>Assignment Operator</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getAssignmentOperator()
 * @model
 * @generated
 */
public enum AssignmentOperator implements Enumerator {
	/**
	 * The '<em><b>Assign</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ASSIGN_VALUE
	 * @generated
	 * @ordered
	 */
	ASSIGN(0, "assign", "="),

	/**
	 * The '<em><b>Mul Assign</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MUL_ASSIGN_VALUE
	 * @generated
	 * @ordered
	 */
	MUL_ASSIGN(1, "mulAssign", "*="),

	/**
	 * The '<em><b>Div Assign</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DIV_ASSIGN_VALUE
	 * @generated
	 * @ordered
	 */
	DIV_ASSIGN(2, "divAssign", "/="),

	/**
	 * The '<em><b>Mod Assign</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MOD_ASSIGN_VALUE
	 * @generated
	 * @ordered
	 */
	MOD_ASSIGN(3, "modAssign", "%="),

	/**
	 * The '<em><b>Add Assign</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ADD_ASSIGN_VALUE
	 * @generated
	 * @ordered
	 */
	ADD_ASSIGN(4, "addAssign", "+="),

	/**
	 * The '<em><b>Sub Assign</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SUB_ASSIGN_VALUE
	 * @generated
	 * @ordered
	 */
	SUB_ASSIGN(5, "subAssign", "-="),

	/**
	 * The '<em><b>Shl Assign</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SHL_ASSIGN_VALUE
	 * @generated
	 * @ordered
	 */
	SHL_ASSIGN(6, "shlAssign", "<<="),

	/**
	 * The '<em><b>Shr Assign</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SHR_ASSIGN_VALUE
	 * @generated
	 * @ordered
	 */
	SHR_ASSIGN(7, "shrAssign", ">>="),

	/**
	 * The '<em><b>Ushr Assign</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #USHR_ASSIGN_VALUE
	 * @generated
	 * @ordered
	 */
	USHR_ASSIGN(8, "ushrAssign", ">>>="),

	/**
	 * The '<em><b>And Assign</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #AND_ASSIGN_VALUE
	 * @generated
	 * @ordered
	 */
	AND_ASSIGN(9, "andAssign", "&="),

	/**
	 * The '<em><b>Xor Assign</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #XOR_ASSIGN_VALUE
	 * @generated
	 * @ordered
	 */
	XOR_ASSIGN(10, "xorAssign", "^="),

	/**
	 * The '<em><b>Or Assign</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OR_ASSIGN_VALUE
	 * @generated
	 * @ordered
	 */
	OR_ASSIGN(11, "orAssign", "|=");

	/**
	 * The '<em><b>Assign</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ASSIGN
	 * @model name="assign" literal="="
	 * @generated
	 * @ordered
	 */
	public static final int ASSIGN_VALUE = 0;

	/**
	 * The '<em><b>Mul Assign</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MUL_ASSIGN
	 * @model name="mulAssign" literal="*="
	 * @generated
	 * @ordered
	 */
	public static final int MUL_ASSIGN_VALUE = 1;

	/**
	 * The '<em><b>Div Assign</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DIV_ASSIGN
	 * @model name="divAssign" literal="/="
	 * @generated
	 * @ordered
	 */
	public static final int DIV_ASSIGN_VALUE = 2;

	/**
	 * The '<em><b>Mod Assign</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MOD_ASSIGN
	 * @model name="modAssign" literal="%="
	 * @generated
	 * @ordered
	 */
	public static final int MOD_ASSIGN_VALUE = 3;

	/**
	 * The '<em><b>Add Assign</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ADD_ASSIGN
	 * @model name="addAssign" literal="+="
	 * @generated
	 * @ordered
	 */
	public static final int ADD_ASSIGN_VALUE = 4;

	/**
	 * The '<em><b>Sub Assign</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SUB_ASSIGN
	 * @model name="subAssign" literal="-="
	 * @generated
	 * @ordered
	 */
	public static final int SUB_ASSIGN_VALUE = 5;

	/**
	 * The '<em><b>Shl Assign</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SHL_ASSIGN
	 * @model name="shlAssign" literal="&lt;&lt;="
	 * @generated
	 * @ordered
	 */
	public static final int SHL_ASSIGN_VALUE = 6;

	/**
	 * The '<em><b>Shr Assign</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SHR_ASSIGN
	 * @model name="shrAssign" literal="&gt;&gt;="
	 * @generated
	 * @ordered
	 */
	public static final int SHR_ASSIGN_VALUE = 7;

	/**
	 * The '<em><b>Ushr Assign</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #USHR_ASSIGN
	 * @model name="ushrAssign" literal="&gt;&gt;&gt;="
	 * @generated
	 * @ordered
	 */
	public static final int USHR_ASSIGN_VALUE = 8;

	/**
	 * The '<em><b>And Assign</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #AND_ASSIGN
	 * @model name="andAssign" literal="&amp;="
	 * @generated
	 * @ordered
	 */
	public static final int AND_ASSIGN_VALUE = 9;

	/**
	 * The '<em><b>Xor Assign</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #XOR_ASSIGN
	 * @model name="xorAssign" literal="^="
	 * @generated
	 * @ordered
	 */
	public static final int XOR_ASSIGN_VALUE = 10;

	/**
	 * The '<em><b>Or Assign</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OR_ASSIGN
	 * @model name="orAssign" literal="|="
	 * @generated
	 * @ordered
	 */
	public static final int OR_ASSIGN_VALUE = 11;

	/**
	 * An array of all the '<em><b>Assignment Operator</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final AssignmentOperator[] VALUES_ARRAY =
		new AssignmentOperator[] {
			ASSIGN,
			MUL_ASSIGN,
			DIV_ASSIGN,
			MOD_ASSIGN,
			ADD_ASSIGN,
			SUB_ASSIGN,
			SHL_ASSIGN,
			SHR_ASSIGN,
			USHR_ASSIGN,
			AND_ASSIGN,
			XOR_ASSIGN,
			OR_ASSIGN,
		};

	/**
	 * A public read-only list of all the '<em><b>Assignment Operator</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<AssignmentOperator> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Assignment Operator</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AssignmentOperator get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			AssignmentOperator result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Assignment Operator</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AssignmentOperator getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			AssignmentOperator result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Assignment Operator</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AssignmentOperator get(int value) {
		switch (value) {
			case ASSIGN_VALUE: return ASSIGN;
			case MUL_ASSIGN_VALUE: return MUL_ASSIGN;
			case DIV_ASSIGN_VALUE: return DIV_ASSIGN;
			case MOD_ASSIGN_VALUE: return MOD_ASSIGN;
			case ADD_ASSIGN_VALUE: return ADD_ASSIGN;
			case SUB_ASSIGN_VALUE: return SUB_ASSIGN;
			case SHL_ASSIGN_VALUE: return SHL_ASSIGN;
			case SHR_ASSIGN_VALUE: return SHR_ASSIGN;
			case USHR_ASSIGN_VALUE: return USHR_ASSIGN;
			case AND_ASSIGN_VALUE: return AND_ASSIGN;
			case XOR_ASSIGN_VALUE: return XOR_ASSIGN;
			case OR_ASSIGN_VALUE: return OR_ASSIGN;
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
	private AssignmentOperator(int value, String name, String literal) {
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
	
} //AssignmentOperator
