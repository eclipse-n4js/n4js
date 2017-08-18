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

/**
 *
 */
final public class FactoryDispatcher extends Dispatcher {

	/**
	 *
	 */
	static public ComplexNode build(ControlFlowElement cfe) {
		try {
			return dispatch("_build", cfe);
		} catch (NoDispatchMethodFoundException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	static ComplexNode _build(AbstractCaseClause feature) {
		return AbstractCaseClauseFactory.buildComplexNode(feature);
	}

	static ComplexNode _build(AnnotationList feature) {
		return EmptyStatementFactory.buildComplexNode(feature);
	}

	static ComplexNode _build(Block feature) {
		return BlockFactory.buildComplexNode(feature);
	}

	static ComplexNode _build(BreakStatement feature) {
		return JumpFactory.buildComplexNode(feature);
	}

	static ComplexNode _build(ContinueStatement feature) {
		return JumpFactory.buildComplexNode(feature);
	}

	static ComplexNode _build(DoStatement feature) {
		return DoWhileFactory.buildComplexNode(feature);
	}

	static ComplexNode _build(EmptyStatement feature) {
		return EmptyStatementFactory.buildComplexNode(feature);
	}

	static ComplexNode _build(ForStatement feature) {
		return ForFactory.buildComplexNode(feature);
	}

	static ComplexNode _build(IfStatement feature) {
		return IfFactory.buildComplexNode(feature);
	}

	static ComplexNode _build(ReturnStatement feature) {
		return JumpFactory.buildComplexNode(feature);
	}

	static ComplexNode _build(SwitchStatement feature) {
		return SwitchFactory.buildComplexNode(feature);
	}

	static ComplexNode _build(ThrowStatement feature) {
		return JumpFactory.buildComplexNode(feature);
	}

	static ComplexNode _build(TryStatement feature) {
		return TryFactory.buildComplexNode(feature);
	}

	static ComplexNode _build(VariableStatement feature) {
		return DeclarationStatementFactory.buildComplexNode(feature);
	}

	static ComplexNode _build(WhileStatement feature) {
		return WhileFactory.buildComplexNode(feature);
	}

	static ComplexNode _build(Expression feature) {
		return ExpressionFactory.buildComplexNode(feature);
	}

	static ComplexNode _build(VariableDeclaration feature) {
		return VariableDeclaratorFactory.buildComplexNode(feature);
	}

}
