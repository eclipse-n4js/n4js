/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.postprocessing;

import java.util.concurrent.Callable;

import org.eclipse.n4js.flowgraphs.FlowAnalyser;
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyser;
import org.eclipse.n4js.flowgraphs.analysers.DeadCodeAnalyser;
import org.eclipse.n4js.flowgraphs.analysers.InstanceofGuardAnalyser;
import org.eclipse.n4js.flowgraphs.analysers.MissingReturnOrThrowAnalyser;
import org.eclipse.n4js.flowgraphs.analysers.NullDereferenceAnalyser;
import org.eclipse.n4js.flowgraphs.analysers.UsedBeforeDeclaredAnalyser;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;

/**
 *
 */
public class ASTFlowInfo {
	/** Provides results of dead code analysis */
	public final DeadCodeAnalyser deadCodeAnalyser;
	/** Provides results of type guards analysis */
	public final InstanceofGuardAnalyser instanceofGuardAnalyser;
	/** Provides results of used before declared analysis */
	public final UsedBeforeDeclaredAnalyser usedBeforeDeclaredAnalyser;
	/** Provides results of null dereference analysis */
	public final NullDereferenceAnalyser nullDereferenceAnalyser;
	/** Provides results of missing return or throw analysis */
	public final MissingReturnOrThrowAnalyser missingReturnOrThrowAnalyser;
	/** Array of all analyses */
	public final FlowAnalyser[] allAnalysers;

	private final N4JSFlowAnalyser flowAnalyzer = new N4JSFlowAnalyser();

	/** Constructor */
	public ASTFlowInfo(TypeSystemHelper typeSystemHelper, JavaScriptVariantHelper jsVariantHelper) {
		this.deadCodeAnalyser = new DeadCodeAnalyser();
		this.instanceofGuardAnalyser = new InstanceofGuardAnalyser();
		this.usedBeforeDeclaredAnalyser = new UsedBeforeDeclaredAnalyser();
		this.nullDereferenceAnalyser = new NullDereferenceAnalyser();
		this.missingReturnOrThrowAnalyser = new MissingReturnOrThrowAnalyser(typeSystemHelper, jsVariantHelper);
		this.allAnalysers = new FlowAnalyser[] { deadCodeAnalyser, instanceofGuardAnalyser, usedBeforeDeclaredAnalyser,
				nullDereferenceAnalyser, missingReturnOrThrowAnalyser };
	}

	/** Creates the control flow graph. Call first and only once. */
	public void createGraphs(Script script, Callable<?> cancelledChecker) {
		flowAnalyzer.createGraphs(script, cancelledChecker);
	}

	/** Performs a forward control flow analysis. Call second and only once. */
	public void performForwardAnalysis(Callable<?> cancelledChecker) {
		flowAnalyzer.acceptForwardAnalysers(cancelledChecker, allAnalysers);
	}

	/** Performs a backward control flow analysis. Call forth. */
	public void performBackwardAnalysis(Callable<?> cancelledChecker) {
		flowAnalyzer.acceptBackwardAnalysers(cancelledChecker, allAnalysers);
	}

}
