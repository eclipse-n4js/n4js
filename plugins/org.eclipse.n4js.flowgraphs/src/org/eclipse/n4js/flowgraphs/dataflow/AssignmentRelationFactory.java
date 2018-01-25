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
import java.util.LinkedList;
import java.util.List;

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

/**
 *
 */
public class AssignmentRelationFactory {
	private final SymbolFactory symbolFactory;

	AssignmentRelationFactory(SymbolFactory symbolFactory) {
		this.symbolFactory = symbolFactory;
	}

	List<AssignmentRelation> findAssignments(ControlFlowElement cfe) {
		List<AssignmentRelation> ars = new LinkedList<>();

		if (DestructureUtils.isTop(cfe)) {
			findInAllDestructNodes(ars, cfe);

		} else if (DestructureUtils.isInDestructuringPattern(cfe)) {
			findInCorrespondingDestructNodes(ars, cfe);

		} else if (cfe instanceof AssignmentExpression) {
			findInAssignmentExpression(ars, (AssignmentExpression) cfe);

		} else if (cfe instanceof VariableDeclaration) {
			findInVariableDeclaration(ars, (VariableDeclaration) cfe);

		} else if (cfe instanceof IdentifierRef) {
			EObject parent = cfe.eContainer();
			if (parent instanceof ForStatement) {
				ForStatement fs = (ForStatement) parent;
				if (fs.getInitExpr() == cfe && fs.isForOf()) {
					findInForStatementInOf(ars, cfe, fs);
				}
			}
		}

		return ars;
	}

	private void findInAssignmentExpression(List<AssignmentRelation> ars, AssignmentExpression ae) {
		Expression lhs = ae.getLhs();
		Expression rhs = ae.getRhs();
		handleSubexpressions(ars, lhs, rhs, false);
	}

	private void findInVariableDeclaration(List<AssignmentRelation> ars, VariableDeclaration vd) {
		EObject parent = vd.eContainer();

		if (parent instanceof ForStatement && ((ForStatement) parent).isForOf()) {
			findInForStatementInOf(ars, vd, (ForStatement) parent);
			return;
		}

		Expression rhs = vd.getExpression();
		if (rhs == null) {
			Symbol undefinedSymbol = symbolFactory.getUndefined();
			createRelation(ars, vd, undefinedSymbol, null, false);
		} else {
			handleSubexpressions(ars, vd, rhs, false);
		}
	}

	private void findInForStatementInOf(List<AssignmentRelation> ars, ControlFlowElement cfe, ForStatement fs) {
		Expression rhs = fs.getExpression();
		if (rhs instanceof ArrayLiteral) {
			ArrayLiteral al = (ArrayLiteral) rhs;
			for (ArrayElement arElem : al.getElements()) {
				handleSubexpressions(ars, cfe, arElem.getExpression(), true);
			}
		}
	}

	private void handleSubexpressions(List<AssignmentRelation> ars, ControlFlowElement lhs, Expression rhs,
			boolean mayHappen) {

		rhs = ASTUtils.unwrapParentheses(rhs);

		if (rhs instanceof AssignmentExpression) {
			AssignmentExpression ae = (AssignmentExpression) rhs;
			Expression innerRhs = ae.getRhs();
			Expression innerLhs = ae.getLhs();
			handleSubexpressions(ars, innerLhs, innerRhs, false);
			handleSubexpressions(ars, lhs, innerRhs, mayHappen);

		} else if (rhs instanceof ConditionalExpression) {
			ConditionalExpression ce = (ConditionalExpression) rhs;
			Expression trueExpr = ce.getTrueExpression();
			Expression falseExpr = ce.getFalseExpression();
			handleSubexpressions(ars, lhs, trueExpr, true);
			handleSubexpressions(ars, lhs, falseExpr, true);

		} else {
			createRelation(ars, lhs, rhs, mayHappen);
		}
	}

	private void findInAllDestructNodes(List<AssignmentRelation> ars, ControlFlowElement cfe) {
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
					findInDestructNodes(ars, dNode, true);
				}
			}

		} else {
			findInDestructNodes(ars, dNode, false);
		}
	}

	private void findInCorrespondingDestructNodes(List<AssignmentRelation> ars, ControlFlowElement cfe) {
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
					findInDestructNodes(ars, dNode, true);
				}
			}

		} else {
			findInDestructNodes(ars, dNode, false);
		}
	}

	private void findInDestructNodes(List<AssignmentRelation> ars, DestructNode dNode, boolean mayHappen) {
		for (Iterator<DestructNode> dnIter = dNode.stream().iterator(); dnIter.hasNext();) {
			DestructNode dnChild = dnIter.next();
			ControlFlowElement lhs = dnChild.getVarRef() != null ? dnChild.getVarRef() : dnChild.getVarDecl();
			EObject rhs = DestructureUtilsForSymbols.getValueFromDestructuring(symbolFactory, dnChild);
			if (rhs == null) {
				Symbol undefinedSymbol = symbolFactory.getUndefined();
				createRelation(ars, lhs, undefinedSymbol, null, mayHappen);
			} else {
				createRelation(ars, lhs, (Expression) rhs, mayHappen);
			}
		}
	}

	private void createRelation(List<AssignmentRelation> ars, ControlFlowElement lhs, Expression rhs,
			boolean mayHappen) {

		Symbol rSymbol = symbolFactory.create(rhs);
		createRelation(ars, lhs, rSymbol, rhs, mayHappen);
	}

	private void createRelation(List<AssignmentRelation> ars, ControlFlowElement lhs, Symbol rSymbol, Expression rhs,
			boolean mayHappen) {

		Symbol lSymbol = symbolFactory.create(lhs);
		if (lSymbol != null) {
			ars.add(new AssignmentRelation(lSymbol, rSymbol, rhs, mayHappen));
		}
	}

}
