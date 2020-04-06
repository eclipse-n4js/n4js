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
package org.eclipse.n4js.transpiler.es.n4idl.assistants

import java.util.List
import org.eclipse.n4js.n4JS.EqualityOperator
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.RelationalOperator
import org.eclipse.n4js.n4JS.UnaryOperator
import org.eclipse.n4js.n4idl.migrations.AndSwitchCondition
import org.eclipse.n4js.n4idl.migrations.ArrayTypeSwitchCondition
import org.eclipse.n4js.n4idl.migrations.ConstantSwitchCondition
import org.eclipse.n4js.n4idl.migrations.OrSwitchCondition
import org.eclipse.n4js.n4idl.migrations.SwitchCondition
import org.eclipse.n4js.n4idl.migrations.TypeSwitchCondition
import org.eclipse.n4js.n4idl.migrations.TypeTypeCondition
import org.eclipse.n4js.transpiler.TransformationAssistant
import org.eclipse.n4js.ts.types.PrimitiveType
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.Type

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*
import org.eclipse.n4js.N4JSLanguageConstants

/**
 * A sub-transpiler which transforms {@link SwitchCondition}s to IM elements 
 * and thus JavaScript conditions/expressions.
 * 
 * Use the method {@link #transform} to recursively trigger the transformation of a 
 * given {@link SwitchCondition}. 
 */
class TypeSwitchTranspiler extends TransformationAssistant {

	/**
	 * Transforms the given {@link SwitchCondition} to a corresponding IM model {@link Expression}.
	 * 
	 * @param lhs The operand of the switch condition. If used more than once, this element will be copied. 
	 */
	public def List<Expression> transform(SwitchCondition condition, Expression lhs) {
		return doTransform(condition, lhs.copy()).map[e | _Parenthesis(e)];
	}
	
	private dispatch def List<Expression> doTransform(OrSwitchCondition or, Expression lhs) {
		return or.operands.map[op | return transform(op, lhs)].flatten.toList
	}
	
	private dispatch def List<Expression> doTransform(AndSwitchCondition and, Expression lhs) {
		return and.operands
			.map[op | return transform(op, lhs)]
			.map[subExpressions | _AND(subExpressions)];
	}
	
	private dispatch def List<Expression> doTransform(ArrayTypeSwitchCondition arrayCondition, Expression lhs) {
		// Array.isArray(lhs)
		val arrayIsArray = _PropertyAccessExpr(_IdentRef(getSymbolTableEntryInternal("Array", true)), getSymbolTableEntryInternal("isArray", true));
		val isArrayExpression = _CallExpr(arrayIsArray, lhs.copy()); 
		// lhs.length > 0
		val notEmptyExpression = _RelationalExpr(_PropertyAccessExpr(lhs.copy(), getSymbolTableEntryInternal("length", true)), RelationalOperator.GT, _NumericLiteral(0));
		// elementTypeExpression (e.g. lhs[0] instanceof T)  
		val elementTypeExpression = transform(arrayCondition.elementTypeCondition, _IndexAccessExpr(lhs.copy(), _NumericLiteral(0)));
		
		return #[_AND(#[isArrayExpression, notEmptyExpression] + elementTypeExpression)]
	}
	
	private dispatch def List<Expression> doTransform(TypeTypeCondition typeTypeCondition, Expression lhs) {
		// simply check whether the value has the "n4type" property
		#[_PropertyAccessExpr(lhs, getSymbolTableEntryInternal(N4JSLanguageConstants.N4TYPE_NAME, true))];
	}
	
	private dispatch def List<Expression> doTransform(TypeSwitchCondition typeCondition, Expression lhs) {
		#[runtimeTypeCheck(typeCondition.type, lhs)];
	}
	
	/** Creates a runtime type-check expression for the given type and lhs. */
	private dispatch def Expression runtimeTypeCheck(TClassifier type, Expression lhs) {
		val typeSTE = state.steCache.mapOriginal.get(type);
		return _RelationalExpr(lhs, RelationalOperator.INSTANCEOF, _IdentRef(typeSTE));
	}
	
	/** @see {@link #runtimeTypeCheck(TClassifier, Expression) } */
	private dispatch def Expression runtimeTypeCheck(PrimitiveType type, Expression lhs) {
		switch (type.name) {
			case "any":
				_TRUE
			case "int":
				_TypeOfCheck(lhs, "number")
			case "number":
				_TypeOfCheck(lhs, "number")
			case "string":
				_TypeOfCheck(lhs, "string")
			case "boolean":
				_TypeOfCheck(lhs, "boolean")
			default:
				throw new IllegalStateException("Unhandled primitive type in TypeSwitchTranspiler: " + type)
		}
	}
	
	private dispatch def Expression runtimeTypeCheck(Type type, Expression lhs) {
		throw new IllegalStateException("Cannot produce runtime type-check for type " + type);
	}

	/** 
	 * Creates a new typeof check using the given lhs and typeofResult:
	 * {@code typeof <lhs> === "<typeofResult>"}.
	 * 
	 * @param lhs The left-hand side to check the type of
	 * @param typeofResult The desired result of the typeof operator
	 */
	private def Expression _TypeOfCheck(Expression lhs, String typeofResult) {
		_EqualityExpr(_UnaryExpr(UnaryOperator.TYPEOF, lhs), EqualityOperator.SAME, _StringLiteral(typeofResult))  
	}
	
	/** @see {@link #runtimeTypeCheck(TClassifier, Expression) } */
	
	private dispatch def List<Expression> doTransform(ConstantSwitchCondition constantCondition, Expression lhs) {
		if (constantCondition.constant.equals("true")) {
			return #[_TRUE];
		} else if (constantCondition.constant.equals("true")) {
			return #[_FALSE];
		} else {
			throw new IllegalStateException("Unhandled ConstantSwitchCondition with constant '" + constantCondition.constant + "'");
		}
	}
	
	private dispatch def List<Expression> doTransform(SwitchCondition unhandledCondition, Expression lhs) {
		throw new IllegalStateException("Encountered unhandled switch-condition of type " + unhandledCondition.class.simpleName + " in transpiler: " + unhandledCondition.toString);
	}
}
