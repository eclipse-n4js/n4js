/**
 * Copyright (c) 2017 Marcus Mews.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Marcus Mews - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.factories;

import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.n4JS.AbstractCaseClause;
import org.eclipse.n4js.n4JS.AnnotationList;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.BreakStatement;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.ContinueStatement;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.DoStatement;
import org.eclipse.n4js.n4JS.EmptyStatement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.SwitchStatement;
import org.eclipse.n4js.n4JS.ThrowStatement;
import org.eclipse.n4js.n4JS.TryStatement;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.n4JS.WhileStatement;
import org.eclipse.n4js.n4JS.util.N4JSSwitch;

/**
 * Provides function {@link #build(ControlFlowElement)} to create instances of {@link ComplexNode} for given
 * {@link ControlFlowElement}s.
 */
final public class CFEFactory {

	/**
	 * Builds a {@link ComplexNode} from a given {@link ControlFlowElement}
	 */
	static public ComplexNode build(ControlFlowElement cfe) {
		return new InternalFactoryDispatcher().doSwitch(cfe);
	}

	static private class InternalFactoryDispatcher extends N4JSSwitch<ComplexNode> {
		@Override
		public ComplexNode caseAbstractCaseClause(AbstractCaseClause feature) {
			return AbstractCaseClauseFactory.buildComplexNode(feature);
		}

		@Override
		public ComplexNode caseAnnotationList(AnnotationList feature) {
			return EmptyStatementFactory.buildComplexNode(feature);
		}

		@Override
		public ComplexNode caseBlock(Block feature) {
			return BlockFactory.buildComplexNode(feature);
		}

		@Override
		public ComplexNode caseBreakStatement(BreakStatement feature) {
			return JumpFactory.buildComplexNode(feature);
		}

		@Override
		public ComplexNode caseContinueStatement(ContinueStatement feature) {
			return JumpFactory.buildComplexNode(feature);
		}

		@Override
		public ComplexNode caseDoStatement(DoStatement feature) {
			return DoWhileFactory.buildComplexNode(feature);
		}

		@Override
		public ComplexNode caseEmptyStatement(EmptyStatement feature) {
			return EmptyStatementFactory.buildComplexNode(feature);
		}

		@Override
		public ComplexNode caseForStatement(ForStatement feature) {
			return ForFactory.buildComplexNode(feature);
		}

		@Override
		public ComplexNode caseIfStatement(IfStatement feature) {
			return IfFactory.buildComplexNode(feature);
		}

		@Override
		public ComplexNode caseReturnStatement(ReturnStatement feature) {
			return JumpFactory.buildComplexNode(feature);
		}

		@Override
		public ComplexNode caseSwitchStatement(SwitchStatement feature) {
			return SwitchFactory.buildComplexNode(feature);
		}

		@Override
		public ComplexNode caseThrowStatement(ThrowStatement feature) {
			return JumpFactory.buildComplexNode(feature);
		}

		@Override
		public ComplexNode caseTryStatement(TryStatement feature) {
			return TryFactory.buildComplexNode(feature);
		}

		@Override
		public ComplexNode caseVariableStatement(VariableStatement feature) {
			return DeclarationStatementFactory.buildComplexNode(feature);
		}

		@Override
		public ComplexNode caseWhileStatement(WhileStatement feature) {
			return WhileFactory.buildComplexNode(feature);
		}

		@Override
		public ComplexNode caseConditionalExpression(ConditionalExpression feature) {
			return ConditionalExpressionFactory.buildComplexNode(feature);
		}

		@Override
		public ComplexNode caseExpression(Expression feature) {
			return ExpressionFactory.buildComplexNode(feature);
		}

		@Override
		public ComplexNode caseVariableDeclaration(VariableDeclaration feature) {
			return VariableDeclaratorFactory.buildComplexNode(feature);
		}
	}

}
