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
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.RelationalOperator
import org.eclipse.n4js.n4idl.AndSwitchCondition
import org.eclipse.n4js.n4idl.ArrayTypeSwitchCondition
import org.eclipse.n4js.n4idl.ConstantSwitchCondition
import org.eclipse.n4js.n4idl.OrSwitchCondition
import org.eclipse.n4js.n4idl.SwitchCondition
import org.eclipse.n4js.n4idl.TypeSwitchCondition
import org.eclipse.n4js.transpiler.TransformationAssistant

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

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
	
	private dispatch def List<Expression> doTransform(TypeSwitchCondition typeCondition, Expression lhs) {
		val typeSTE = state.steCache.mapOriginal.get(typeCondition.type);
		return #[_RelationalExpr(lhs, RelationalOperator.INSTANCEOF, _IdentRef(typeSTE))];
	}
	
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
						