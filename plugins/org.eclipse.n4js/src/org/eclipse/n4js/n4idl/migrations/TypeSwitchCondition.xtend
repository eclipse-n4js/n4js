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
package org.eclipse.n4js.n4idl.migrations

import com.google.common.collect.Iterables
import com.google.common.hash.HashCode
import java.util.ArrayDeque
import java.util.Iterator
import java.util.List
import java.util.NoSuchElementException
import java.util.Queue
import java.util.stream.Collectors
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.Type

/**
 * A {@link SwitchCondition} represents a runtime condition that matches a given 
 * compile-time {@link TypeRef} at runtime.
 * 
 * See sub-classes for concrete conditions (e.g. type, and, or, etc.).
 */
abstract class SwitchCondition implements Iterable<SwitchCondition> {
	/** 
	 * Returns a new {@link OrSwitchCondition} of the given left-hand side and right-hand side.
	 * 
	 * @param operands The OR operands. At least 2.
	 */
	public static def SwitchCondition or(List<? extends SwitchCondition> operands) {
		if (operands.size < 1) {
			throw new IllegalArgumentException("Cannot create or-switch-condition with less than one operand.")
		} else if (operands.size == 1) {
			return operands.get(0);
		}
		return new OrSwitchCondition(operands.get(0), operands.get(1), operands.drop(2));
	}
	
	/** 
	 * Returns a new {@link AndSwitchCondition} of the given operands.
	 * 
	 * @param operands The AND operands. At least 2.
	 */
	public static def SwitchCondition and(List<? extends SwitchCondition> operands) {
		if (operands.size < 1) {
			throw new IllegalArgumentException("Cannot create and-switch-condition with less than one operand.")
		} else if (operands.size == 1) {
			return operands.get(0);
		}
		return new AndSwitchCondition(operands.get(0), operands.get(1), operands.drop(2));
	}
	
	/** Returns a new {@link TypeSwitchCondition} for the given type. */
	public static def TypeSwitchCondition instanceOf(Type type) {
		return new TypeSwitchCondition(type);
	}
	
	/** Returns a new {@link TypeTypeCondition} for the given type. */
	public static def TypeTypeCondition type(Type type) {
		return new TypeTypeCondition(type);
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
	
	public abstract def Iterable<? extends SwitchCondition> subConditions();
	
	override toString() {
		// use default value identifier "v" for native toString representation
		return this.getConditionAsString("v");
	}
	
	public override Iterator<SwitchCondition> iterator() {
		return new SwitchConditionIterator(this);
	}
}

/**
 * A depth-first iterator for {@link SwitchCondition}s.
 */
class SwitchConditionIterator implements Iterator<SwitchCondition> {
	private Queue<SwitchCondition> queue = new ArrayDeque();
	
	
	new(SwitchCondition root) {
		this.queue.add(root);
	}
	
	override hasNext() {
		return !queue.isEmpty;
	}
	
	override next() {
		val nextElement = this.queue.poll();
		if (null === nextElement) {
			throw new NoSuchElementException();
		}
		// add all sub-conditions
		nextElement.subConditions.forEach[this.queue.add(it)]
		
		return nextElement;
	}
	
}

/** {@link SwitchCondition} which assures that at least one of the {@link #operands} conditions
 * evaluates to true. */
class OrSwitchCondition extends SwitchCondition {
	public List<SwitchCondition> operands;
	
	new (SwitchCondition operand1, SwitchCondition operand2, Iterable<? extends SwitchCondition> remainingOperands) {
		this.operands = Iterables.concat(#[operand1, operand2], remainingOperands).toList
	}
	
	override hashCode() {
		return this.operands.hashCode 
			+ 31 * HashCode.fromString(OrSwitchCondition.simpleName).asInt
	}
	
	override getConditionAsString(String valueIdentifier) {
		return this.operands.stream.map[o | "(" + o.getConditionAsString(valueIdentifier) + ")"].collect(Collectors.joining(" || "));
	}
	
	override subConditions() { return operands; }
	
}

/** {@link SwitchCondition} which assures that all of the {@link #operands} conditions 
 * evaluate to true. */
class AndSwitchCondition extends SwitchCondition {
	public List<SwitchCondition> operands;
	
	new (SwitchCondition operand1, SwitchCondition operand2, Iterable<? extends SwitchCondition> remainingOperands) {
		this.operands = Iterables.concat(#[operand1, operand2], remainingOperands).toList
	}
	
	override hashCode() {
		return this.operands.hashCode 
			+ 31 * HashCode.fromString(AndSwitchCondition.simpleName).asInt
	}
	
	override getConditionAsString(String valueIdentifier) {
		return this.operands.stream.map[o | "(" + o.getConditionAsString(valueIdentifier) + ")"].collect(Collectors.joining(" && "));
	}
	
	override subConditions() { return this.operands }
	
}

/** {@link SwitchCondition} which assures that a given value is an instanceof of {@link #type}. */
class TypeSwitchCondition extends SwitchCondition {
	public val Type type
	
	new(Type type) { this.type = type; }
	
	override hashCode() {
		return HashCode.fromString(EcoreUtil.getURI(this.type).toString).asInt;
	}
	
	override getConditionAsString(String valueIdentifier) {
		return String.format("%s instanceof %s", valueIdentifier, this.type.typeDescription);
	}
	
	override subConditions() { return #[] }
	
}

/** {@link SwitchCondition} which assures that a given value is a reference to a type which is 
 * a subtype of {@link #type} (type reference). */
class TypeTypeCondition extends SwitchCondition {
	public val Type type;
	
	new(Type type) { this.type = type; }
	
	override hashCode() {
		return HashCode.fromString(EcoreUtil.getURI(this.type).toString).asInt;
	}
	
	override getConditionAsString(String valueIdentifier) {
		return String.format("(%s.constructor instanceof type{%s}", valueIdentifier, this.type.typeDescription);
	}
	
	override subConditions() { return #[] }
}


/** {@link SwitchCondition} which assures that a given value is a (non-empty) array
 * whose elements fulfill the given {@link #elementTypeCondition}.
 */
class ArrayTypeSwitchCondition extends SwitchCondition {
	public val SwitchCondition elementTypeCondition
	
	new(SwitchCondition elementTypeCondition) { this.elementTypeCondition = elementTypeCondition }
	
	override hashCode() {
		return this.elementTypeCondition.hashCode
			+ 31 * HashCode.fromString(ArrayTypeSwitchCondition.simpleName).asInt;
	}
	
	override getConditionAsString(String valueIdentifier) {
		return String.format("(%s array with (%s))", 
			valueIdentifier, 
			this.elementTypeCondition.getConditionAsString(valueIdentifier + "[0]"))
	}
	
	override subConditions() { return #[elementTypeCondition] }
	
}

/**
 * A {@link ConstantSwitchCondition} always checks for the same property, 
 * independent from the given {@code valueIdentifier}.
 */
class ConstantSwitchCondition extends SwitchCondition {
	public static ConstantSwitchCondition TRUE = new ConstantSwitchCondition("true");
	public static ConstantSwitchCondition FALSE = new ConstantSwitchCondition("true");
	
	public val String constant;
	
	new(String constant) { this.constant = constant; }
	
	override getConditionAsString(String valueIdentifier) {
		return this.constant;
	}
	
	override subConditions() { return #[] }
}
