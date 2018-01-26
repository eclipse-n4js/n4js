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
package org.eclipse.n4js.flowgraphs.dataflow;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.factories.ASTUtils;
import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.DestructNode;
import org.eclipse.n4js.n4JS.DestructureUtils;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.VariableDeclaration;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 *
 */
public class AssignmentRelationFactory {
	private final SymbolFactory symbolFactory;

	AssignmentRelationFactory(SymbolFactory symbolFactory) {
		this.symbolFactory = symbolFactory;
	}

	Multimap<Symbol, Object> findAssignments(ControlFlowElement cfe) {
		Multimap<Symbol, Object> assgns = HashMultimap.create();

		if (DestructureUtils.isTop(cfe)) {
			findInAllDestructNodes(assgns, cfe);

		} else if (DestructureUtils.isInDestructuringPattern(cfe)) {
			findInCorrespondingDestructNodes(assgns, cfe);

		} else if (cfe instanceof AssignmentExpression) {
			findInAssignmentExpression(assgns, (AssignmentExpression) cfe);

		} else if (cfe instanceof VariableDeclaration) {
			findInVariableDeclaration(assgns, (VariableDeclaration) cfe);

		} else if (cfe instanceof IdentifierRef) {
			EObject parent = cfe.eContainer();
			if (parent instanceof ForStatement) {
				ForStatement fs = (ForStatement) parent;
				if (fs.getInitExpr() == cfe && fs.isForOf()) {
					findInForStatementInOf(assgns, cfe, fs);
				}
			}
		}

		return assgns;
	}

	private void findInAssignmentExpression(Multimap<Symbol, Object> assgns, AssignmentExpression ae) {
		Expression lhs = ae.getLhs();
		Expression rhs = ae.getRhs();
		handleSubexpressions(assgns, lhs, rhs);
	}

	private void findInVariableDeclaration(Multimap<Symbol, Object> assgns, VariableDeclaration vd) {
		EObject parent = vd.eContainer();

		if (parent instanceof ForStatement && ((ForStatement) parent).isForOf()) {
			findInForStatementInOf(assgns, vd, (ForStatement) parent);
			return;
		}

		Expression rhs = vd.getExpression();
		if (rhs == null) {
			Symbol undefinedSymbol = symbolFactory.getUndefined();
			createRelation(assgns, vd, undefinedSymbol, null);
		} else {
			handleSubexpressions(assgns, vd, rhs);
		}
	}

	private void findInForStatementInOf(Multimap<Symbol, Object> assgns, ControlFlowElement cfe, ForStatement fs) {
		Expression rhs = fs.getExpression();
		if (rhs instanceof ArrayLiteral) {
			ArrayLiteral al = (ArrayLiteral) rhs;
			for (ArrayElement arElem : al.getElements()) {
				handleSubexpressions(assgns, cfe, arElem.getExpression());
			}
		}
	}

	private void handleSubexpressions(Multimap<Symbol, Object> assgns, ControlFlowElement lhs, Expression rhs) {
		rhs = ASTUtils.unwrapParentheses(rhs);

		if (rhs instanceof AssignmentExpression) {
			AssignmentExpression ae = (AssignmentExpression) rhs;
			Expression innerRhs = ae.getRhs();
			// The inner assignment is handled already.
			// Expression innerLhs = ae.getLhs();
			// handleSubexpressions(assgns, innerLhs, innerRhs, false);
			handleSubexpressions(assgns, lhs, innerRhs);

		} else if (rhs instanceof ConditionalExpression) {
			ConditionalExpression ce = (ConditionalExpression) rhs;
			Expression trueExpr = ce.getTrueExpression();
			Expression falseExpr = ce.getFalseExpression();
			handleSubexpressions(assgns, lhs, trueExpr);
			handleSubexpressions(assgns, lhs, falseExpr);

		} else {
			createRelation(assgns, lhs, rhs);
		}
	}

	private void findInAllDestructNodes(Multimap<Symbol, Object> assgns, ControlFlowElement cfe) {
		EObject top = DestructureUtils.getTop(cfe);
		DestructNode dNode = DestructNode.unify(top);
		if (dNode == null) {
			return;
		}

		if (top instanceof ForStatement) {
			ForStatement fs = (ForStatement) top;
			Expression fsExpr = fs.getExpression();
			if (fsExpr instanceof ArrayLiteral) {
				ArrayLiteral al = (ArrayLiteral) fsExpr;
				EObject rootOfDestrNode = DestructureUtils.getRoot(cfe);
				for (ArrayElement arrElem : al.getElements()) {
					dNode = DestructNode.unify(rootOfDestrNode, arrElem.getExpression());
					findInDestructNodes(assgns, dNode);
				}
			}

		} else {
			findInDestructNodes(assgns, dNode);
		}
	}

	private void findInCorrespondingDestructNodes(Multimap<Symbol, Object> assgns, ControlFlowElement cfe) {
		DestructNode dNode = DestructureUtils.getCorrespondingDestructNode(cfe);
		if (dNode == null) {
			return;
		}

		EObject parentOfDestrNode = DestructureUtils.getTop(cfe);
		if (parentOfDestrNode instanceof ForStatement) {
			ForStatement fs = (ForStatement) parentOfDestrNode;
			Expression fsExpr = fs.getExpression();
			if (fsExpr instanceof ArrayLiteral) {
				ArrayLiteral al = (ArrayLiteral) fsExpr;
				EObject rootOfDestrNode = DestructureUtils.getRoot(cfe);
				for (ArrayElement arrElem : al.getElements()) {
					dNode = DestructNode.unify(rootOfDestrNode, arrElem.getExpression());
					findInDestructNodes(assgns, dNode);
				}
			}

		} else {
			findInDestructNodes(assgns, dNode);
		}
	}

	private void findInDestructNodes(Multimap<Symbol, Object> assgns, DestructNode dNode) {
		for (Iterator<DestructNode> dnIter = dNode.stream().iterator(); dnIter.hasNext();) {
			DestructNode dnChild = dnIter.next();
			ControlFlowElement lhs = dnChild.getVarRef() != null ? dnChild.getVarRef() : dnChild.getVarDecl();
			EObject rhs = DestructureUtilsForSymbols.getValueFromDestructuring(symbolFactory, dnChild);
			if (rhs == null) {
				Symbol undefinedSymbol = symbolFactory.getUndefined();
				createRelation(assgns, lhs, undefinedSymbol, null);
			} else {
				createRelation(assgns, lhs, (Expression) rhs);
			}
		}
	}

	private void createRelation(Multimap<Symbol, Object> assgns, ControlFlowElement lhs, Expression rhs) {
		Symbol rSymbol = symbolFactory.create(rhs);
		createRelation(assgns, lhs, rSymbol, rhs);
	}

	private void createRelation(Multimap<Symbol, Object> assgns, ControlFlowElement lhs, Symbol rSymbol,
			Expression rhs) {

		Symbol lSymbol = symbolFactory.create(lhs);
		if (lSymbol != null) {
			if (rSymbol != null) {
				assgns.put(lSymbol, rSymbol);
			} else if (rhs != null) {
				assgns.put(lSymbol, rhs);
			}
		}
	}

}
