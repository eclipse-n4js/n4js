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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.CaseClause;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.WhileStatement;
import org.eclipse.n4js.n4JS.util.N4JSSwitch;
import org.eclipse.n4js.n4jsx.n4JSX.util.N4JSXSwitch;

/**
 *
 */
final public class OrderedEContentProvider {

	/**
	 *
	 */
	static public List<EObject> eContents(EObject cfe) {
		List<EObject> list = new InternalFactoryDispatcherX().doSwitch(cfe);
		if (list != null) {
			return list;
		}
		return new InternalFactoryDispatcher().doSwitch(cfe);
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

	}

	static private class InternalFactoryDispatcherX extends N4JSXSwitch<List<EObject>> {
		//
	}
}
