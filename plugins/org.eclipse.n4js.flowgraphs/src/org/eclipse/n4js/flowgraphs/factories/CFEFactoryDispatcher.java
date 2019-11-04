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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.n4JS.AbstractCaseClause;
import org.eclipse.n4js.n4JS.ArrayBindingPattern;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.BindingElement;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.BreakStatement;
import org.eclipse.n4js.n4JS.CoalesceExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.ContinueStatement;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.DebuggerStatement;
import org.eclipse.n4js.n4JS.DoStatement;
import org.eclipse.n4js.n4JS.EmptyStatement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionWithTarget;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.n4js.n4JS.JSXPropertyAttribute;
import org.eclipse.n4js.n4JS.JSXSpreadAttribute;
import org.eclipse.n4js.n4JS.ObjectBindingPattern;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.SwitchStatement;
import org.eclipse.n4js.n4JS.ThrowStatement;
import org.eclipse.n4js.n4JS.TryStatement;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.n4JS.WhileStatement;
import org.eclipse.n4js.n4JS.WithStatement;
import org.eclipse.n4js.n4JS.util.N4JSSwitch;

/**
 * Provides function {@link #build(ReentrantASTIterator, EObject)} to create instances of {@link ComplexNode} for given
 * {@link EObject}s.
 */
final public class CFEFactoryDispatcher {

	/**
	 * Builds a {@link ComplexNode} from a given {@link EObject}, i.e. {@link ControlFlowElement}.
	 */
	static public ComplexNode build(ReentrantASTIterator astIter, EObject cfe) {
		return new InternalFactoryDispatcher(astIter).doSwitch(cfe);
	}

	static private class InternalFactoryDispatcher extends N4JSSwitch<ComplexNode> {
		private final ReentrantASTIterator astIter;

		InternalFactoryDispatcher(ReentrantASTIterator astIter) {
			this.astIter = astIter;
		}

		@Override
		public ComplexNode caseAbstractCaseClause(AbstractCaseClause feature) {
			return AbstractCaseClauseFactory.buildComplexNode(astIter, feature);
		}

		// AnnotationList are ignored

		@Override
		public ComplexNode caseBlock(Block feature) {
			return BlockFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseBreakStatement(BreakStatement feature) {
			return JumpFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseContinueStatement(ContinueStatement feature) {
			return JumpFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseDebuggerStatement(DebuggerStatement feature) {
			return DebuggerStatementFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseDoStatement(DoStatement feature) {
			return DoWhileFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseEmptyStatement(EmptyStatement feature) {
			return EmptyStatementFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseForStatement(ForStatement feature) {
			return ForFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseIfStatement(IfStatement feature) {
			return IfFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseReturnStatement(ReturnStatement feature) {
			return JumpFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseSwitchStatement(SwitchStatement feature) {
			return SwitchFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseThrowStatement(ThrowStatement feature) {
			return JumpFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseTryStatement(TryStatement feature) {
			return TryFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseVariableStatement(VariableStatement feature) {
			return VariableStatementFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseWhileStatement(WhileStatement feature) {
			return WhileFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseWithStatement(WithStatement feature) {
			return WithFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseScript(Script feature) {
			return ScriptFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseFunctionDeclaration(FunctionDeclaration feature) {
			return EmptyStatementFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseConditionalExpression(ConditionalExpression feature) {
			return ConditionalExpressionFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseCoalesceExpression(CoalesceExpression feature) {
			return CoalesceExpressionFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseBinaryLogicalExpression(BinaryLogicalExpression feature) {
			return BinaryLogicalExpressionFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseExpression(Expression feature) {
			return StandardCFEFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseExpressionWithTarget(ExpressionWithTarget feature) {
			return ExpressionWithTargetFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseVariableBinding(VariableBinding feature) {
			return StandardCFEFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseVariableDeclaration(VariableDeclaration feature) {
			return VariableDeclarationFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseBindingElement(BindingElement feature) {
			return StandardCFEFactory.buildComplexNodeHidden(astIter, feature);
		}

		@Override
		public ComplexNode caseArrayBindingPattern(ArrayBindingPattern feature) {
			return StandardCFEFactory.buildComplexNodeHidden(astIter, feature);
		}

		@Override
		public ComplexNode caseObjectBindingPattern(ObjectBindingPattern feature) {
			return StandardCFEFactory.buildComplexNodeHidden(astIter, feature);
		}

		@Override
		public ComplexNode caseJSXSpreadAttribute(JSXSpreadAttribute feature) {
			return StandardCFEFactory.buildComplexNode(astIter, feature);
		}

		@Override
		public ComplexNode caseJSXPropertyAttribute(JSXPropertyAttribute feature) {
			return StandardCFEFactory.buildComplexNode(astIter, feature);
		}
	}

}
