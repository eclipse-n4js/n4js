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
package org.eclipse.n4js.flowgraphs.dataflow.symbols;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IntLiteral;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.NullLiteral;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.SuperLiteral;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.impl.IdentifierRefImpl;
import org.eclipse.n4js.n4JS.impl.NullLiteralImpl;
import org.eclipse.n4js.n4JS.impl.NumericLiteralImpl;
import org.eclipse.n4js.n4JS.impl.SuperLiteralImpl;
import org.eclipse.n4js.n4JS.impl.ThisLiteralImpl;
import org.eclipse.n4js.n4JS.impl.UnaryExpressionImpl;
import org.eclipse.n4js.n4JS.impl.VariableDeclarationImpl;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TypesFactory;

/**
 * Creates {@link Symbol}s depending on the given AST element.
 * <p>
 * <b>Note:</b> Do not resolve proxies during the CFG/DFG analyses. This is done beforehand only (see
 * N4JSPostProcessor#postProcessN4JSResource(...) in step 1)
 */
public class SymbolFactory {
	private Symbol undefined;
	private final Map<Symbol, Symbol> symbols = new HashMap<>();

	static final Map<Class<? extends ControlFlowElement>, Function<ControlFlowElement, Symbol>> symbolCreators;

	static {
		symbolCreators = new HashMap<>();
		symbolCreators.put(VariableDeclarationImpl.class, SymbolFactory::createFromVariableDeclaration);
		symbolCreators.put(IdentifierRefImpl.class, SymbolFactory::createFromIdentifierRef);
		// Deactivated. Not necessary at the moment.
		// symbolCreators.put(ParameterizedPropertyAccessExpressionImpl.class,
		// SymbolFactory::createFromParameterizedPropertyAccessExpression);
		symbolCreators.put(NullLiteralImpl.class, SymbolFactory::createFromNullLiteral);
		symbolCreators.put(UnaryExpressionImpl.class, SymbolFactory::createFromUnaryExpression);
		symbolCreators.put(ThisLiteralImpl.class, SymbolFactory::createFromThisLiteral);
		symbolCreators.put(SuperLiteralImpl.class, SymbolFactory::createFromSuperLiteral);
		symbolCreators.put(NumericLiteralImpl.class, SymbolFactory::createFromNumericLiteral);
	}

	static private Symbol createFromVariableDeclaration(ControlFlowElement cfe) {
		return new SymbolOfVariableDeclaration((VariableDeclaration) cfe);
	}

	static private Symbol createFromIdentifierRef(ControlFlowElement cfe) {
		IdentifierRef idRef = (IdentifierRef) cfe;
		IdentifiableElement id = getId(idRef);
		if (id != null) {
			return new SymbolOfIdentifierRef(id, idRef);
		}
		return null;
	}

	@SuppressWarnings("unused")
	static private Symbol createFromParameterizedPropertyAccessExpression(ControlFlowElement cfe) {
		// Deactivated.
		// Not necessary at the moment. Causes performance issues in
		// n4js-n4/tests/com.enfore.n4js.tests.libraryparsing/src/com/enfore/n4js/tests/libraryparsing/SmokeTestSuite

		// ParameterizedPropertyAccessExpression ppae = (ParameterizedPropertyAccessExpression) cfe;
		// newSymbol = new SymbolOfParameterizedPropertyAccessExpression(this, ppae);
		return null;
	}

	static private Symbol createFromNullLiteral(ControlFlowElement cfe) {
		return new SymbolOfNullLiteral((NullLiteral) cfe);
	}

	static private Symbol createFromUnaryExpression(ControlFlowElement cfe) {
		UnaryExpression ue = (UnaryExpression) cfe;
		if (ue.getOp() == UnaryOperator.VOID) {
			return new SymbolOfUndefined(ue);
		}
		return null;
	}

	static private Symbol createFromThisLiteral(ControlFlowElement cfe) {
		return new SymbolOfThisLiteral((ThisLiteral) cfe);
	}

	static private Symbol createFromSuperLiteral(ControlFlowElement cfe) {
		return new SymbolOfSuperLiteral((SuperLiteral) cfe);
	}

	static private Symbol createFromNumericLiteral(ControlFlowElement cfe) {
		if (new BigDecimal(0).equals(((NumericLiteral) cfe).getValue())) {
			return new SymbolOfZeroLiteral((NumericLiteral) cfe);
		}
		return null;
	}

	/** @return true if the given element can represent a {@link Symbol} */
	static public boolean canCreate(ControlFlowElement cfe) {
		if (cfe == null) {
			return false;
		}
		return symbolCreators.containsKey(cfe.getClass());
	}

	/** @return a {@link Symbol} for the given element or null */
	public Symbol create(ControlFlowElement cfe) {
		if (cfe != null) {
			Function<ControlFlowElement, Symbol> creatorFunction = symbolCreators.get(cfe.getClass());
			if (creatorFunction != null) {
				Symbol newSymbol = creatorFunction.apply(cfe);
				if (newSymbol != null) {
					symbols.putIfAbsent(newSymbol, newSymbol);
					Symbol symbol = symbols.get(newSymbol);
					return symbol;
				}
			}
		}
		return null;
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
	public Symbol create(Expression baseExpression, List<Symbol> wrappers) {
		if (wrappers.isEmpty()) {
			return create(baseExpression);
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

		ParameterizedPropertyAccessExpression ppae = (ParameterizedPropertyAccessExpression) lastTarget;
		return new SymbolOfParameterizedPropertyAccessExpression(this, ppae);
	}

	/** @return {@link IdentifiableElement}. Does not resolve proxies. */
	static public IdentifiableElement getId(IdentifierRef idRef) {
		if (idRef instanceof IdentifierRefImpl) {
			IdentifiableElement id = ((IdentifierRefImpl) idRef).basicGetId();
			if (id != null && !id.eIsProxy()) {
				return id;
			}
		}
		return null;
	}

	/** @return true iff the given {@link Expression} represents 'undefined' */
	static public boolean isUndefined(Expression expr) {
		if (expr instanceof IdentifierRef) {
			IdentifiableElement id = getId((IdentifierRef) expr);
			if (id != null && "undefined".equals(id.getName())) {
				return true;
			}
		}
		return false;
	}

	/** @return true iff the given {@link Expression} represents '0' */
	static public boolean isZero(Expression expr) {
		if (expr instanceof IntLiteral && new BigDecimal(0).equals(((NumericLiteral) expr).getValue())) {
			return true;
		}
		return false;
	}

	/** @return a {@link Symbol} that represents {@code undefined} */
	public Symbol getUndefined() {
		if (undefined == null) {
			IdentifiableElement ieUndefined = TypesFactory.eINSTANCE.createIdentifiableElement();
			IdentifierRef irUndefined = N4JSFactory.eINSTANCE.createIdentifierRef();
			irUndefined.setId(ieUndefined);
			ieUndefined.setName("undefined");
			undefined = create(irUndefined);
		}
		return undefined;
	}

}
