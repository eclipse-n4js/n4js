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

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.FlowEdge;
import org.eclipse.n4js.flowgraphs.analysis.BranchWalker;
import org.eclipse.n4js.flowgraphs.analysis.BranchWalkerInternal;
import org.eclipse.n4js.flowgraphs.analysis.GraphExplorer;
import org.eclipse.n4js.flowgraphs.analysis.GraphVisitor;
import org.eclipse.n4js.flowgraphs.analysis.TraverseDirection;
import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.GetterDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.SetterDeclaration;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.ThrowStatement;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.xtext.EcoreUtil2;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * [N4JSSpec] 7.1.4 Return Statement
 *
 * Constraint 111
 * <p>
 * Constraint 111.3 Item 2 "all control flows must either end with a return or throw statement"
 */
public class MissingReturnOrThrowAnalyser extends GraphVisitor {
	final private TypeSystemHelper typeSystemHelper;
	final private JavaScriptVariantHelper jsVariantHelper;

	Multimap<FunctionOrFieldAccessor, ControlFlowElement> missingTRAfter = HashMultimap.create();

	/** Constructor */
	public MissingReturnOrThrowAnalyser(TypeSystemHelper typeSystemHelper, JavaScriptVariantHelper jsVariantHelper) {
		super(TraverseDirection.Backward);
		this.typeSystemHelper = typeSystemHelper;
		this.jsVariantHelper = jsVariantHelper;
	}

	@Override
	protected void initializeContainer(ControlFlowElement curContainer) {
		EObject parentContainer = curContainer.eContainer();

		if (parentContainer instanceof FunctionOrFieldAccessor) {
			FunctionOrFieldAccessor fofa = (FunctionOrFieldAccessor) parentContainer;

			if (mrtCheckRequired(fofa)) {
				if (hasStatementsInBody(fofa)) {
					requestActivation(new MissingReturnGraphExplorer(fofa));

				} else {
					missingTRAfter.put(fofa, null);
				}
			}
		}
	}

	class MissingReturnGraphExplorer extends GraphExplorer {
		final FunctionOrFieldAccessor fofa;

		MissingReturnGraphExplorer(FunctionOrFieldAccessor fofa) {
			this.fofa = fofa;
		}

		@Override
		protected BranchWalker joinBranches(List<BranchWalker> branchWalkers) {
			return null;
		}

		@Override
		protected BranchWalkerInternal firstBranchWalker() {
			return new MissingReturnBranchWalker();
		}

		class MissingReturnBranchWalker extends BranchWalker {
			@Override
			protected void visit(ControlFlowElement cfe) {
				deactivate();

				if (isDeadCodeBranch()) {
					return;
				}

				Statement stmt = EcoreUtil2.getContainerOfType(cfe, Statement.class);
				if (stmt instanceof ThrowStatement) {
					return;
				}
				if (stmt instanceof ReturnStatement) {
					return;
				}

				missingTRAfter.put(fofa, stmt);
			}

			@Override
			protected void visit(FlowEdge edge) {
				for (ControlFlowType cfType : edge.cfTypes) {
					boolean isReturnOrThrowEdge = false;
					isReturnOrThrowEdge |= cfType == ControlFlowType.Return;
					isReturnOrThrowEdge |= cfType == ControlFlowType.Throw;
					if (isReturnOrThrowEdge) {
						deactivate();
						return;
					}
				}
			}

			@Override
			protected void exitContainer(ControlFlowElement cfContainer) {
				missingTRAfter.put(fofa, cfContainer);
			}

			@Override
			protected BranchWalker forkPath() {
				if (isDeadCodeBranch()) {
					return null;
				}
				return new MissingReturnBranchWalker();
			}
		}
	}

	private boolean mrtCheckRequired(FunctionOrFieldAccessor fofa) {
		if (!jsVariantHelper.requireCheckFunctionReturn(fofa)) {
			// cf. 13.1
			return false;
		}

		if (fofa instanceof N4MethodDeclaration) {
			if (((N4MethodDeclaration) fofa).isConstructor()) {
				// we have a non-void constructor -> there will already be an error elsewhere -> avoid duplicate errors
				return false;
			}
		}

		if (fofa instanceof FunctionDefinition) {
			if (((FunctionDefinition) fofa).isGenerator()) {
				// Return statements are optional in generator functions
				return false;
			}
		}

		if (fofa instanceof ArrowFunction) {
			if (((ArrowFunction) fofa).isSingleExprImplicitReturn()) {
				// ES6 arrow functions allow single-exprs to lack an explicit return
				return false;
			}
		}

		if (fofa instanceof SetterDeclaration) {
			return false;
		}

		if (fofa instanceof GetterDeclaration) {
			return true;
		}

		if (fofa.isReturnValueOptional()) {
			return false;
		}

		TypeRef returnType = typeSystemHelper.getExpectedTypeOfFunctionOrFieldAccessor(null, fofa);
		if (returnType == null || TypeUtils.isVoid(returnType) || TypeUtils.isUndefined(returnType)) {
			return false;
		}

		return true;
	}

	private boolean hasStatementsInBody(FunctionOrFieldAccessor fofa) {
		Block body = fofa.getBody();
		if (body == null) {
			return false;
		}
		return body.getAllStatements().hasNext();
	}

	/** @return the set of all {@link FunctionOrFieldAccessor} that have a missing return or throw statement(s). */
	public Collection<FunctionOrFieldAccessor> getMRTFunctions() {
		return missingTRAfter.keySet();
	}

	/**
	 * @return a {@link Multimap} of all {@link ControlFlowElement} that miss a succeeding return or throw statement.
	 */
	public Multimap<FunctionOrFieldAccessor, ControlFlowElement> getLastStatementsWithoutSuccReturnOrThrow() {
		return missingTRAfter;
	}
}
