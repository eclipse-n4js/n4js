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
package org.eclipse.n4js.flowgraphs.factories;

import static com.google.common.base.Preconditions.checkState;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.flowgraphs.dataflow.EffectInfo;
import org.eclipse.n4js.flowgraphs.dataflow.EffectType;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.Symbol;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.SymbolFactory;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.RepresentingNode;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.DestructNode;
import org.eclipse.n4js.n4JS.DestructureUtils;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.PostfixExpression;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.util.N4JSSwitch;

class CFEEffectInfos {

	static void set(SymbolFactory symbolFactory, Map<ControlFlowElement, ComplexNode> cnMap, ComplexNode cNode,
			ControlFlowElement cfe) {

		new InternalEffectInfos(symbolFactory, cnMap, cNode).doSwitch(cfe);
	}

	static private class InternalEffectInfos extends N4JSSwitch<Void> {
		final private SymbolFactory symbolFactory;
		final Map<ControlFlowElement, ComplexNode> cnMap;
		final ComplexNode cNode;

		InternalEffectInfos(SymbolFactory symbolFactory, Map<ControlFlowElement, ComplexNode> cnMap,
				ComplexNode cNode) {

			this.symbolFactory = symbolFactory;
			this.cnMap = cnMap;
			this.cNode = cNode;
		}

		@Override
		public Void caseVariableDeclaration(VariableDeclaration feature) {
			Node entryNode = cNode.getEntry();
			Node exitNode = cNode.getExit();

			addEffect(EffectType.Declaration, feature, entryNode);
			addEffect(EffectType.Write, feature, exitNode);

			return null;
		}

		@Override
		public Void caseAssignmentExpression(AssignmentExpression feature) {
			Expression lhs = feature.getLhs();
			if (lhs == null) {
				return null;
			}

			List<Expression> idRefs = new LinkedList<>();
			if (DestructureUtils.isTopOfDestructuringAssignment(feature)) {
				idRefs.addAll(DestructNode.getAllDeclaredIdRefs(feature));
			} else {
				idRefs.add(lhs);
			}

			Node exitNode = cNode.getExit();
			for (Expression assignedVar : idRefs) {
				clearEffectsOfExitNode(assignedVar);
				addEffect(EffectType.Write, assignedVar, exitNode);
			}
			return null;
		}

		@Override
		public Void caseForStatement(ForStatement feature) {
			if (feature.isForOf()) {
				Expression initExpr = feature.getInitExpr();
				if (initExpr instanceof IdentifierRef) {
					clearEffectsOfExitNode(initExpr);
					ComplexNode cn = cnMap.get(initExpr);
					Node exitNode = cn.getExit();
					addEffect(EffectType.Write, initExpr, exitNode);
				}
			}

			return null;
		}

		@Override
		public Void casePostfixExpression(PostfixExpression feature) {
			if (feature.getExpression() == null) {
				return null;
			}

			clearEffectsOfExitNode(feature.getExpression());
			Node exitNode = cNode.getExit();
			Node expressionNode = cnMap.get(feature.getExpression()).getExit();

			addEffect(EffectType.Read, feature.getExpression(), expressionNode);
			addEffect(EffectType.Write, feature.getExpression(), exitNode);

			return null;
		}

		@Override
		public Void caseUnaryExpression(UnaryExpression feature) {
			if (feature.getExpression() == null) {
				return null;
			}

			boolean addEffects = false;
			addEffects |= feature.getOp() == UnaryOperator.INC;
			addEffects |= feature.getOp() == UnaryOperator.DEC;
			if (!addEffects) {
				return null;
			}

			clearEffectsOfExitNode(feature.getExpression());
			Node exitNode = cNode.getExit();
			Node expressionNode = cnMap.get(feature.getExpression()).getExit();

			addEffect(EffectType.Write, feature.getExpression(), expressionNode);
			addEffect(EffectType.Read, feature.getExpression(), exitNode);

			return null;
		}

		@Override
		public Void caseParameterizedCallExpression(ParameterizedCallExpression feature) {
			Expression targetExpr = feature.getTarget();
			if (targetExpr == null) {
				return null;
			}

			Node exitNode = cNode.getExit();
			addEffect(EffectType.MethodCall, targetExpr, exitNode);

			return null;
		}

		@Override
		public Void caseParameterizedPropertyAccessExpression(ParameterizedPropertyAccessExpression feature) {
			setRead(feature);
			return null;
		}

		@Override
		public Void caseIdentifierRef(IdentifierRef feature) {
			setRead(feature);
			return null;
		}

		@Override
		public Void caseIndexedAccessExpression(IndexedAccessExpression feature) {
			setRead(feature);
			return null;
		}

		private void setRead(Expression feature) {
			Node exitNode = cNode.getExit();
			addEffect(EffectType.Read, feature, exitNode);
		}

		private void clearEffectsOfExitNode(Expression feature) {
			ComplexNode cn = cnMap.get(feature);
			if (cn != null) {
				Node exitNode = cn.getExit();
				exitNode.effectInfos.clear();
			}
		}

		private void addEffect(EffectType effectType, ControlFlowElement expr, Node node) {
			boolean supportsEffectInfo = node instanceof HelperNode || node instanceof RepresentingNode;
			checkState(supportsEffectInfo, "Effect info can be attached to Helper-/Representing- nodes only");

			Symbol symbol = symbolFactory.create(expr);
			if (symbol != null) {
				EffectInfo eiDecl = new EffectInfo(effectType, expr, symbol);
				node.addEffectInfo(eiDecl);

				if (effectType == EffectType.Write) {
					symbol.addDefinitionCFE(expr);
				}
			}

		}
	}

}
