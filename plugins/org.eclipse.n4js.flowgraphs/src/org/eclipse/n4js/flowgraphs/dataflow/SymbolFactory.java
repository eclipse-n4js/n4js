/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.dataflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.SuperLiteral;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TypesFactory;

/**
 * Creates {@link Symbol}s depending on the given AST element
 */
public class SymbolFactory {
	static private Symbol undefined;
	static private Map<Symbol, Symbol> symbols = new HashMap<>();

	/** @return a {@link Symbol} based on the given {@link ControlFlowElement} */
	public static Symbol create(ControlFlowElement cfe) {
		if (cfe instanceof Expression) {
			return create((Expression) cfe);
		} else if (cfe instanceof VariableDeclaration) {
			return create((VariableDeclaration) cfe);
		}
		return null;
	}

	/** @return a {@link Symbol} based on the given {@link VariableDeclaration} */
	public static Symbol create(VariableDeclaration vd) {
		Symbol newSymbol = new SymbolOfVariableDeclaration(vd);
		symbols.putIfAbsent(newSymbol, newSymbol);
		Symbol symbol = symbols.get(newSymbol);
		return symbol;
	}

	/** @return a {@link Symbol} based on the given {@link Expression}, or null */
	public static Symbol create(Expression expr) {
		Symbol newSymbol = null;
		if (expr instanceof IdentifierRef) {
			newSymbol = new SymbolOfIdentifierRef((IdentifierRef) expr);
		} else if (expr instanceof ParameterizedPropertyAccessExpression) {
			newSymbol = new SymbolOfParameterizedPropertyAccessExpression((ParameterizedPropertyAccessExpression) expr);
		} else if (expr instanceof IndexedAccessExpression) {
			newSymbol = new SymbolOfIndexedAccessExpression((IndexedAccessExpression) expr);
		} else if (expr instanceof NullLiteral) {
			newSymbol = new SymbolOfNullLiteral((NullLiteral) expr);
		} else if (expr instanceof ThisLiteral) {
			newSymbol = new SymbolOfThisLiteral((ThisLiteral) expr);
		} else if (expr instanceof SuperLiteral) {
			newSymbol = new SymbolOfSuperLiteral((SuperLiteral) expr);
		} else if (expr instanceof NumericLiteral && new Integer(0).equals(((NumericLiteral) expr).getValue())) {
			newSymbol = new SymbolOfZeroLiteral((NumericLiteral) expr);
		}

		Symbol symbol = null;
		if (newSymbol != null) {
			symbols.putIfAbsent(newSymbol, newSymbol);
			symbol = symbols.get(newSymbol);
		}

		return symbol;
	}

	/**
	 * This method creates a {@link Symbol} based on a synthesized {@link ParameterizedPropertyAccessExpression}. It
	 * assumes that the given list is ordered from right to left, starting with the most inner context symbol and ending
	 * with a {@link Symbol} of the last {@link ParameterizedPropertyAccessExpression}.
	 *
	 * @param baseExpression
	 *            {@link Expression} that is the target of the outer most {@link ParameterizedPropertyAccessExpression}
	 * @param wrappers
	 *            list of Symbols that represent {@link ParameterizedPropertyAccessExpression}s
	 * @return a symbol created from the given base expression and list of contexts, or {@code null} iff contexts is
	 *         empty.
	 */
	public static Symbol create(Expression baseExpression, List<Symbol> wrappers) {
		if (wrappers.isEmpty()) {
			return SymbolFactory.create(baseExpression);
		}

		Expression lastTarget = EcoreUtil.copy(baseExpression);
		for (Symbol wrapper : wrappers) {
			ParameterizedPropertyAccessExpression ppae = ((SymbolOfParameterizedPropertyAccessExpression) wrapper).ppae;
			ParameterizedPropertyAccessExpression copy = N4JSFactory.eINSTANCE
					.createParameterizedPropertyAccessExpression();

			copy.setProperty(ppae.getProperty());
			copy.setTarget(lastTarget);
			lastTarget = copy;
		}

		return new SymbolOfParameterizedPropertyAccessExpression((ParameterizedPropertyAccessExpression) lastTarget);
	}

	/** @return true iff the given {@link Expression} */
	public static boolean isUndefined(Expression expr) {
		Symbol undef = SymbolFactory.create(expr);
		return undef != null && undef.isUndefinedLiteral();
	}

	/** @return a {@link Symbol} that represents {@code undefined} */
	public static Symbol getUndefined() {
		if (undefined == null) {
			IdentifiableElement ieUndefined = TypesFactory.eINSTANCE.createIdentifiableElement();
			IdentifierRef irUndefined = N4JSFactory.eINSTANCE.createIdentifierRef();
			irUndefined.setId(ieUndefined);
			ieUndefined.setName("undefined");
			undefined = SymbolFactory.create(irUndefined);
		}
		return undefined;
	}

}
