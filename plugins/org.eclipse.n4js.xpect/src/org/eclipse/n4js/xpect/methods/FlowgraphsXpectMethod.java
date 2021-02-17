/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xpect.methods;

import java.util.List;

import org.eclipse.n4js.ide.tests.helper.server.xt.IEObjectCoveringRegion;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtMethodsFlowgraphs;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter;
import org.eclipse.n4js.xpect.methods.scoping.IN4JSCommaSeparatedValuesExpectation;
import org.eclipse.n4js.xpect.methods.scoping.N4JSCommaSeparatedValuesExpectation;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.expectation.IStringExpectation;
import org.eclipse.xpect.expectation.StringExpectation;
import org.eclipse.xpect.parameter.ParameterParser;
import org.eclipse.xpect.runner.Xpect;

import com.google.inject.Inject;

/**
 */
@XpectImport(N4JSOffsetAdapter.class)
public class FlowgraphsXpectMethod {
	@Inject
	private XtMethodsFlowgraphs mh;

	/**
	 * This xpect method can evaluate the direct predecessors of a code element. The predecessors can be limited when
	 * specifying the edge type.
	 * <p>
	 * <b>Attention:</b> The type parameter <i>does not</i> work on self loops!
	 */
	@ParameterParser(syntax = "('of' arg2=OFFSET)?")
	@Xpect
	public void astOrder(@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			IEObjectCoveringRegion offset) {

		List<String> astElements = mh.getAstOrder(offset);
		expectation.assertEquals(astElements);
	}

	/**
	 * This xpect method can evaluate the direct predecessors of a code element. The predecessors can be limited when
	 * specifying the edge type.
	 * <p>
	 * <b>Attention:</b> The type parameter <i>does not</i> work on self loops!
	 */
	@ParameterParser(syntax = "('type' arg1=STRING)? ('at' arg2=OFFSET)?")
	@Xpect
	public void preds(@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			String type, IEObjectCoveringRegion offset) {

		List<String> predTexts = mh.getPreds(type, offset);
		expectation.assertEquals(predTexts);
	}

	/**
	 * This xpect method can evaluate the direct successors of a code element. The successors can be limited when
	 * specifying the edge type.
	 * <p>
	 * <b>Attention:</b> The type parameter <i>does not</i> work on self loops!
	 */
	@ParameterParser(syntax = "('type' arg1=STRING)? ('at' arg2=OFFSET)?")
	@Xpect
	public void succs(@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			String type, IEObjectCoveringRegion offset) {

		List<String> succTexts = mh.getSuccs(type, offset);
		expectation.assertEquals(succTexts);
	}

	/** This xpect method can evaluate if the tested element is a transitive predecessor of the given element. */
	@ParameterParser(syntax = "'from' arg0=OFFSET ('to' arg1=OFFSET)? ('notTo' arg2=OFFSET)? ('via' arg3=OFFSET)? ('notVia' arg4=OFFSET)? ('pleaseNeverUseThisParameterSinceItExistsOnlyToGetAReferenceOffset' arg5=OFFSET)?")
	@Xpect
	public void path(IEObjectCoveringRegion fromOffset, IEObjectCoveringRegion toOffset,
			IEObjectCoveringRegion notToOffset, IEObjectCoveringRegion viaOffset,
			IEObjectCoveringRegion notViaOffset, IEObjectCoveringRegion referenceOffset) {

		mh.getPath(fromOffset, toOffset, notToOffset, viaOffset, notViaOffset, referenceOffset);
	}

	/**
	 * This xpect method can evaluate all branches that are merged at the given node name.
	 */
	@ParameterParser(syntax = "('pleaseNeverUseThisParameterSinceItExistsOnlyToGetAReferenceOffset' arg1=OFFSET)?")
	@Xpect
	public void allMergeBranches(@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			IEObjectCoveringRegion referenceOffset) {

		List<String> edgeStrings = mh.getAllMergeBranches(referenceOffset);
		expectation.assertEquals(edgeStrings);
	}

	/**
	 * This xpect method can evaluate all branches from a given start code element. If no start code element is
	 * specified, the first code element of the containing function.
	 */
	@ParameterParser(syntax = "('from' arg1=OFFSET)? ('direction' arg2=STRING)? ('pleaseNeverUseThisParameterSinceItExistsOnlyToGetAReferenceOffset' arg3=OFFSET)?")
	@Xpect
	public void allBranches(@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			IEObjectCoveringRegion offset, String directionName, IEObjectCoveringRegion referenceOffset) {

		List<String> branchStrings = mh.getAllBranches(offset, directionName, referenceOffset);
		expectation.assertEquals(branchStrings);
	}

	/**
	 * This xpect method can evaluate all paths from a given start code element. If no start code element is specified,
	 * the first code element of the containing function.
	 */
	@ParameterParser(syntax = "('from' arg1=OFFSET)? ('direction' arg2=STRING)? ('pleaseNeverUseThisParameterSinceItExistsOnlyToGetAReferenceOffset' arg3=OFFSET)?")
	@Xpect
	public void allPaths(@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			IEObjectCoveringRegion offset, String directionName, IEObjectCoveringRegion referenceOffset) {

		List<String> pathStrings = mh.getAllPaths(offset, directionName, referenceOffset);
		expectation.assertEquals(pathStrings);
	}

	/** This xpect method can evaluate all edges of the containing function. */
	@ParameterParser(syntax = "('from' arg1=OFFSET)?")
	@Xpect
	public void allEdges(@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			IEObjectCoveringRegion offset) {

		List<String> pathStrings = mh.getAllEdges(offset);
		expectation.assertEquals(pathStrings);
	}

	/** This xpect method can evaluate all common predecessors of two {@link ControlFlowElement}s. */
	@ParameterParser(syntax = "'of' arg1=OFFSET 'and' arg2=OFFSET")
	@Xpect
	public void commonPreds(@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			IEObjectCoveringRegion a, IEObjectCoveringRegion b) {

		List<String> commonPredStrs = mh.getCommonPreds(a, b);
		expectation.assertEquals(commonPredStrs);
	}

	/** This xpect method can evaluate the control flow container of a given {@link ControlFlowElement}. */
	@ParameterParser(syntax = "('of' arg1=OFFSET)?")
	@Xpect
	public void cfContainer(@StringExpectation IStringExpectation expectation, IEObjectCoveringRegion offset) {
		String containerStr = mh.getCfContainer(offset);
		expectation.assertEquals(containerStr);
	}

	/** This xpect method can evaluate the control flow container of a given {@link ControlFlowElement}. */
	@ParameterParser(syntax = "('of' arg1=OFFSET)?")
	@Xpect
	public void instanceofguard(@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			IEObjectCoveringRegion offset) {

		List<String> commonPredStrs = mh.getInstanceofguard(offset);
		expectation.assertEquals(commonPredStrs);
	}

}
