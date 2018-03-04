/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4idl

import com.google.common.collect.Iterables
import com.google.common.hash.HashCode
import java.util.List
import java.util.stream.Collectors
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.ts.types.Type

/**
 * Type-switch conditions.
 * 
 * See sub-classes for concrete conditions (e.g. type, and, or...).
 */
abstract class SwitchCondition {
	/** 
	 * Returns a new {@link OrSwitchCondition} of the given left-hand side and right-hand side.
	 * 
	 * @param operands The OR operands. At least 2.
	 */
	public static def OrSwitchCondition or(List<SwitchCondition> operands) {
		if (operands.size < 2) {
			throw new IllegalArgumentException("Cannot create or-switch-condition with less than 2 operands.")
		}
		return new OrSwitchCondition(operands.get(0), operands.get(1), operands.drop(2));
	}
	
	/** 
	 * Returns a new {@link AndSwitchCondition} of the given operands.
	 * 
	 * @param operands The AND operands. At least 2.
	 */
	public static def AndSwitchCondition and(List<SwitchCondition> operands) {
		if (operands.size < 2) {
			throw new IllegalArgumentException("Cannot create and-switch-condition with less than 2 operands.")
		}
		return new AndSwitchCondition(operands.get(0), operands.get(1), operands.drop(2));
	}
	
	/** Returns a new {@link TypeSwitchCondition} for the given type */
	public static def TypeSwitchCondition instanceOf(Type type) {
		return new TypeSwitchCondition(type);
	}
	
	/** Returns a new {@link ArrayTypeSwitchCondition} for the given element type. */
	public static def ArrayTypeSwitchCondition arrayOf(SwitchCondition elementTypeCondition) {
		return new ArrayTypeSwitchCondition(elementTypeCondition);
	}
	
	/** Returns a constant switch condition, which will always evaluate to {@code true}. */
	public static def ConstantSwitchCondition trueCondition() {
		return ConstantSwitchCondition.TRUE;
	}
	
	/** Returns a constant switch condition, which will always evaluate to {@code false}. */
	public static def ConstantSwitchCondition falseCondition() {
		return ConstantSwitchCondition.FALSE;
	}
	
	protected static def String getTypeDescription(Type type) {
		return type.name + (if (type.version != 0) { "#" + type.version } else { "" });
	}
	
	/** 
	 * Returns a string-representation of this condition, testing the given value identifier.
	 * 
	 * Example: {@code getConditionAsString("v")} return {@code "v instanceof A#1" }
	 */
	public abstract def String getConditionAsString(String valueIdentifier);
	
	override toString() {
		// use default value identifier "v" for native toString representation
		return this.getConditionAsString("v");
	}
	
}

class OrSwitchCondition extends SwitchCondition {
	private List<SwitchCondition> operands;
	
	new (SwitchCondition operand1, SwitchCondition operand2, Iterable<SwitchCondition> remainingOperands) {
		this.operands = Iterables.concat(#[operand1, operand2], remainingOperands).toList
	}
	
	override hashCode() {
		return this.operands.hashCode 
			+ 31 * HashCode.fromString(OrSwitchCondition.simpleName).asInt
	}
	
	override getConditionAsString(String valueIdentifier) {
		return this.operands.stream.map[o | "(" + o.getConditionAsString(valueIdentifier) + ")"].collect(Collectors.joining(" || "));
	}
}

class AndSwitchCondition extends SwitchCondition {
	private List<SwitchCondition> operands;
	
	new (SwitchCondition operand1, SwitchCondition operand2, Iterable<SwitchCondition> remainingOperands) {
		this.operands = Iterables.concat(#[operand1, operand2], remainingOperands).toList
	}
	
	override hashCode() {
		return this.operands.hashCode 
			+ 31 * HashCode.fromString(AndSwitchCondition.simpleName).asInt
	}
	
	override getConditionAsString(String valueIdentifier) {
		return this.operands.stream.map[o | "(" + o.getConditionAsString(valueIdentifier) + ")"].collect(Collectors.joining(" && "));
	}
}

class TypeSwitchCondition extends SwitchCondition {
	private val Type type
	
	new(Type type) { this.type = type; }
	
	override hashCode() {
		return HashCode.fromString(EcoreUtil.getURI(this.type).toString).asInt;
	}
	
	override getConditionAsString(String valueIdentifier) {
		return String.format("%s instanceof %s", valueIdentifier, this.type.typeDescription);
	}
}

class ArrayTypeSwitchCondition extends SwitchCondition {
	private val SwitchCondition elementTypeCondition
	
	new(SwitchCondition elementTypeCondition) { this.elementTypeCondition = elementTypeCondition }
	
	override hashCode() {
		return this.elementTypeCondition.hashCode
			+ 31 * HashCode.fromString(ArrayTypeSwitchCondition.simpleName).asInt;
	}
	
	override getConditionAsString(String valueIdentifier) {
		return String.format("(%s instanceof Array && v.length > 0 && (%s))", 
			valueIdentifier, 
			this.elementTypeCondition.getConditionAsString(valueIdentifier + "[0]"))
	}
	
}

/**
 * A {@link ConstantSwitchCondition} always checks for the same property, 
 * independant from the given {@code valueIdentifier}.
 */
class ConstantSwitchCondition extends SwitchCondition {
	public static ConstantSwitchCondition TRUE = new ConstantSwitchCondition("true");
	public static ConstantSwitchCondition FALSE = new ConstantSwitchCondition("true");
	
	private String constant;
	
	new(String constant) { this.constant = constant; }
	
	override getConditionAsString(String valueIdentifier) {
		return this.constant;
	}
}