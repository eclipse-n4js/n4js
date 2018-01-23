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
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.DestructNode;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableDeclarationOrBinding;
import org.eclipse.n4js.n4JS.VariableStatement;

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

		if (cfe instanceof AssignmentExpression) {
			findInAssignmentExpressions(ars, (AssignmentExpression) cfe);

		} else if (cfe instanceof VariableDeclaration) {
			findInVariableDeclarations(ars, (VariableDeclaration) cfe);
		}
		return ars;
	}

	private void findInAssignmentExpressions(List<AssignmentRelation> ars, AssignmentExpression ae) {
		if (N4JSASTUtils.isDestructuringAssignment(ae)) {
			DestructNode dNode = DestructNode.unify(ae);
			handleDestrucNodes(ars, dNode);

		} else {
			Expression lhs = ae.getLhs();
			Expression rhs = ae.getRhs();
			handleSubexpressions(ars, lhs, rhs);
		}
	}

	private void findInVariableDeclarations(List<AssignmentRelation> ars, VariableDeclaration vd) {
		EObject parent = vd.eContainer();

		if (N4JSASTUtils.isInDestructuringPattern(vd)) {
			DestructNode dNode = N4JSASTUtils.getCorrespondingDestructNode(vd);
			if (dNode != null) {
				handleDestrucNodes(ars, dNode);
			}

		} else if (parent instanceof VariableStatement) {
			VariableStatement vs = (VariableStatement) parent;
			for (VariableDeclarationOrBinding varDeclOrBind : vs.getVarDeclsOrBindings()) {
				if (varDeclOrBind instanceof VariableDeclaration) {
					VariableDeclaration varDecl = (VariableDeclaration) varDeclOrBind;
					Expression rhs = varDecl.getExpression();
					if (rhs == null) {
						Symbol undefinedSymbol = symbolFactory.getUndefined();
						createRelation(ars, varDecl, undefinedSymbol, null);
					} else {
						handleSubexpressions(ars, varDecl, rhs);
					}
				}
			}
		}
	}

	private void handleSubexpressions(List<AssignmentRelation> ars, ControlFlowElement lhs, Expression rhs) {
		rhs = ASTUtils.unwrapParentheses(rhs);

		if (rhs instanceof AssignmentExpression) {
			AssignmentExpression ae = (AssignmentExpression) rhs;
			Expression innerRhs = ae.getRhs();
			Expression innerLhs = ae.getLhs();
			handleSubexpressions(ars, innerLhs, innerRhs);
			handleSubexpressions(ars, lhs, innerRhs);

		} else if (rhs instanceof ConditionalExpression) {
			ConditionalExpression ce = (ConditionalExpression) rhs;
			Expression trueExpr = ce.getTrueExpression();
			Expression falseExpr = ce.getFalseExpression();
			handleSubexpressions(ars, lhs, trueExpr);
			handleSubexpressions(ars, lhs, falseExpr);

		} else {
			createRelation(ars, lhs, rhs);
		}
	}

	private void handleDestrucNodes(List<AssignmentRelation> ars, DestructNode dNode) {
		for (Iterator<DestructNode> dnIter = dNode.stream().iterator(); dnIter.hasNext();) {
			DestructNode dnChild = dnIter.next();
			ControlFlowElement lhs = dnChild.getVarRef() != null ? dnChild.getVarRef() : dnChild.getVarDecl();
			EObject rhs = DestructureUtils.getValueFromDestructuring(symbolFactory, dnChild);
			if (rhs instanceof Expression) {
				createRelation(ars, lhs, (Expression) rhs);
			}
		}
	}

	private void createRelation(List<AssignmentRelation> ars, ControlFlowElement lhs, Expression rhs) {
		Symbol rSymbol = symbolFactory.create(rhs);
		createRelation(ars, lhs, rSymbol, rhs);
	}

	private void createRelation(List<AssignmentRelation> ars, ControlFlowElement lhs, Symbol rSymbol, Expression rhs) {
		Symbol lSymbol = symbolFactory.create(lhs);

		if (lSymbol != null) {
			ars.add(new AssignmentRelation(lSymbol, rSymbol, rhs));
		}
	}

}
