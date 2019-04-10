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
 * A representation of the literals of the enumeration '<em><b>Unary Operator</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getUnaryOperator()
 * @model
 * @generated
 */
public enum UnaryOperator implements Enumerator {
	/**
	 * The '<em><b>Delete</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DELETE_VALUE
	 * @generated
	 * @ordered
	 */
	DELETE(0, "delete", "delete"),

	/**
	 * The '<em><b>Void</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VOID_VALUE
	 * @generated
	 * @ordered
	 */
	VOID(1, "void", "void"),

	/**
	 * The '<em><b>Typeof</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TYPEOF_VALUE
	 * @generated
	 * @ordered
	 */
	TYPEOF(2, "typeof", "typeof"),

	/**
	 * The '<em><b>Inc</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INC_VALUE
	 * @generated
	 * @ordered
	 */
	INC(3, "inc", "++"),

	/**
	 * The '<em><b>Dec</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DEC_VALUE
	 * @generated
	 * @ordered
	 */
	DEC(4, "dec", "--"),

	/**
	 * The '<em><b>Pos</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #POS_VALUE
	 * @generated
	 * @ordered
	 */
	POS(5, "pos", "+"),

	/**
	 * The '<em><b>Neg</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NEG_VALUE
	 * @generated
	 * @ordered
	 */
	NEG(6, "neg", "-"),

	/**
	 * The '<em><b>Inv</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INV_VALUE
	 * @generated
	 * @ordered
	 */
	INV(7, "inv", "~"),

	/**
	 * The '<em><b>Not</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NOT_VALUE
	 * @generated
	 * @ordered
	 */
	NOT(8, "not", "!");

	/**
	 * The '<em><b>Delete</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DELETE
	 * @model name="delete"
	 * @generated
	 * @ordered
	 */
	public static final int DELETE_VALUE = 0;

	/**
	 * The '<em><b>Void</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VOID
	 * @model name="void"
	 * @generated
	 * @ordered
	 */
	public static final int VOID_VALUE = 1;

	/**
	 * The '<em><b>Typeof</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TYPEOF
	 * @model name="typeof"
	 * @generated
	 * @ordered
	 */
	public static final int TYPEOF_VALUE = 2;

	/**
	 * The '<em><b>Inc</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INC
	 * @model name="inc" literal="++"
	 * @generated
	 * @ordered
	 */
	public static final int INC_VALUE = 3;

	/**
	 * The '<em><b>Dec</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DEC
	 * @model name="dec" literal="--"
	 * @generated
	 * @ordered
	 */
	public static final int DEC_VALUE = 4;

	/**
	 * The '<em><b>Pos</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #POS
	 * @model name="pos" literal="+"
	 * @generated
	 * @ordered
	 */
	public static final int POS_VALUE = 5;

	/**
	 * The '<em><b>Neg</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NEG
	 * @model name="neg" literal="-"
	 * @generated
	 * @ordered
	 */
	public static final int NEG_VALUE = 6;

	/**
	 * The '<em><b>Inv</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INV
	 * @model name="inv" literal="~"
	 * @generated
	 * @ordered
	 */
	public static final int INV_VALUE = 7;

	/**
	 * The '<em><b>Not</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NOT
	 * @model name="not" literal="!"
	 * @generated
	 * @ordered
	 */
	public static final int NOT_VALUE = 8;

	/**
	 * An array of all the '<em><b>Unary Operator</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final UnaryOperator[] VALUES_ARRAY =
		new UnaryOperator[] {
			DELETE,
			VOID,
			TYPEOF,
			INC,
			DEC,
			POS,
			NEG,
			INV,
			NOT,
		};

	/**
	 * A public read-only list of all the '<em><b>Unary Operator</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<UnaryOperator> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Unary Operator</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UnaryOperator get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UnaryOperator result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Unary Operator</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UnaryOperator getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UnaryOperator result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Unary Operator</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UnaryOperator get(int value) {
		switch (value) {
			case DELETE_VALUE: return DELETE;
			case VOID_VALUE: return VOID;
			case TYPEOF_VALUE: return TYPEOF;
			case INC_VALUE: return INC;
			case DEC_VALUE: return DEC;
			case POS_VALUE: return POS;
			case NEG_VALUE: return NEG;
			case INV_VALUE: return INV;
			case NOT_VALUE: return NOT;
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
	private UnaryOperator(int value, String name, String literal) {
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
	
} //UnaryOperator
