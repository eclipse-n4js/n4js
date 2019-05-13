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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.analysis.GraphVisitorGuideInternal;
import org.eclipse.n4js.n4JS.CaseClause;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.JSXElement;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.WhileStatement;
import org.eclipse.n4js.n4JS.util.N4JSSwitch;

/**
 * This class returns the child elements of each {@link ControlFlowElement} in order of the AST traversal. This order is
 * important for the {@link GraphVisitorGuideInternal} which walks through the AST in forward/backward direction.
 * Unfortunately, the correct order cannot be retrieved from the method {@link EObject#eContents()}, since it relies on
 * the xcore model. This class adjusts this order when necessary.
 * <p/>
 * Wrong AST order can result in a failing asserting with the message
 * {@link ReentrantASTIterator#ASSERTION_MSG_AST_ORDER}.
 */
final public class OrderedEContentProvider {

	/** @return all child elements of the given element in AST order */
	static public List<EObject> eContents(EObject eObj) {
		if (eObj == null) {
			return Collections.emptyList();
		}
		return new InternalFactoryDispatcher().doSwitch(eObj);
	}

	static private class InternalFactoryDispatcher extends N4JSSwitch<List<EObject>> {

		@Override
		public List<EObject> defaultCase(EObject feature) {
			return feature.eContents();
		}

		@Override
		public List<EObject> caseForStatement(ForStatement feature) {
			List<EObject> orderedEContents = new LinkedList<>();
			orderedEContents.addAll(feature.getVarDeclsOrBindings());
			if (feature.getInitExpr() != null)
				orderedEContents.add(feature.getInitExpr());
			if (feature.getExpression() != null)
				orderedEContents.add(feature.getExpression());
			orderedEContents.add(feature.getStatement());
			if (feature.getUpdateExpr() != null)
				orderedEContents.add(feature.getUpdateExpr());
			return orderedEContents;
		}

		@Override
		public List<EObject> caseWhileStatement(WhileStatement feature) {
			List<EObject> orderedEContents = new LinkedList<>();
			orderedEContents.add(feature.getExpression());
			orderedEContents.add(feature.getStatement());
			return orderedEContents;
		}

		@Override
		public List<EObject> caseCaseClause(CaseClause feature) {
			List<EObject> orderedEContents = new LinkedList<>();
			orderedEContents.add(feature.getExpression());
			orderedEContents.addAll(feature.getStatements());
			return orderedEContents;
		}

		@Override
		public List<EObject> caseVariableBinding(VariableBinding feature) {
			List<EObject> orderedEContents = new LinkedList<>();
			if (feature.getExpression() != null)
				orderedEContents.add(feature.getExpression());
			if (feature.getPattern() != null)
				orderedEContents.add(feature.getPattern());
			return orderedEContents;
		}

		@Override
		public List<EObject> caseJSXElement(JSXElement feature) {
			List<EObject> orderedEContents = new LinkedList<>();
			if (feature.getJsxElementName() != null)
				orderedEContents.add(feature.getJsxElementName());

			orderedEContents.addAll(feature.getJsxAttributes());
			orderedEContents.addAll(feature.getJsxChildren());

			if (feature.getJsxClosingName() != null)
				orderedEContents.add(feature.getJsxClosingName());
			return orderedEContents;
		}
	}
}
