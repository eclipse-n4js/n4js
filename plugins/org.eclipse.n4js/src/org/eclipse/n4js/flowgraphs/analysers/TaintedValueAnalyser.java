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
package org.eclipse.n4js.flowgraphs.analysers;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.analyses.Assumption;
import org.eclipse.n4js.flowgraphs.model.EffectType;
import org.eclipse.n4js.flowgraphs.model.Symbol;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;

/**
 * This analysis computes all cases where two explicitly declared annotations of type @Tainted and @Untainted conflict
 * with each other.
 */
public class TaintedValueAnalyser {
	final N4JSTypeSystem ts;

	/** Constructor */
	public TaintedValueAnalyser(N4JSTypeSystem ts) {
		this.ts = ts;
	}

	public void visitEffect(EffectType effect, Symbol symbol, ControlFlowElement cfe) {
		if (isUntaintedArgument(symbol, cfe)) {
			IsUntainted isUntainted = new IsUntainted(symbol);
			assume(isUntainted);
		}
		if (isUntaintedAssignee(symbol, cfe)) {
			IsUntainted isUntainted = new IsUntainted(symbol);
			assume(isUntainted);
		}
	}

	protected void assume(Assumption a) {
	}

	boolean isUntaintedArgument(Symbol symbol, ControlFlowElement cfe) {
		if (cfe instanceof ParameterizedCallExpression) {
			ParameterizedCallExpression pce = (ParameterizedCallExpression) cfe;
			for (Argument arg : pce.getArguments()) {
				if (symbol.is(arg.getExpression())) {
					TypeRef typeRef = ts.tau(arg, arg);
					// Check if type of argument is annotated with @Untainted
					if (typeRef instanceof FormalParameter) {
						FormalParameter fp = (FormalParameter) arg;
						for (Annotation ann : fp.getAnnotations()) {
							String name = ann.getName();
							return "Untainted".equals(name);
						}
					}
				}
			}
		}
		return false;
	}

	boolean isUntaintedAssignee(Symbol symbol, ControlFlowElement cfe) {
		if (cfe instanceof AssignmentExpression) {
			AssignmentExpression ae = (AssignmentExpression) cfe;
			Expression lhs = ae.getLhs();
			if (symbol.is(lhs)) {
				return assignedSymbolIsAnnotatedWith(symbol, "Untainted");
			}
		}
		return false;
	}

	private boolean assignedSymbolIsAnnotatedWith(Symbol assignedSymbol, String annotationName) {
		EObject decl = SymbolDeclaration.get(assignedSymbol, ts);
		EList<Annotation> annotations = ECollections.emptyEList();
		if (decl instanceof N4FieldDeclaration) {
			N4FieldDeclaration fieldDecl = (N4FieldDeclaration) decl;
			annotations = fieldDecl.getAnnotations();
		}
		if (decl instanceof VariableDeclaration) {
			VariableDeclaration varDecl = (VariableDeclaration) decl;
			annotations = varDecl.getAnnotations();
		}
		for (Annotation ann : annotations) {
			String name = ann.getName();
			return annotationName.equals(name);
		}
		return false;
	}

	class IsUntainted extends Assumption {
		IsUntainted(Symbol symbol) {
			super(symbol);
		}

		@Override
		public boolean holdsOnDataflow(Symbol lhs, Symbol rhs, ControlFlowElement cfe) {
			return !assignedSymbolIsAnnotatedWith(rhs, "Tainted");
		}
	}
}
