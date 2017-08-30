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

import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyses;
import org.eclipse.n4js.flowgraphs.analyses.AllNodesAndEdgesPrintWalker;
import org.eclipse.n4js.flowgraphs.analyses.AllPathPrintWalker;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter.IEObjectCoveringRegion;
import org.eclipse.n4js.xpect.methods.scoping.IN4JSCommaSeparatedValuesExpectation;
import org.eclipse.n4js.xpect.methods.scoping.N4JSCommaSeparatedValuesExpectation;
import org.xpect.XpectImport;
import org.xpect.parameter.ParameterParser;
import org.xpect.runner.Xpect;

import com.google.inject.Inject;

/**
 */
@XpectImport(N4JSOffsetAdapter.class)
public class FlowgraphsXpectMethod {

	@Inject
	N4JSFlowAnalyses flowAnalyses;

	/**
	 * This xpect method can evaluate the direct successors of a code element. The successors can be limited when
	 * specifying the edge type.
	 */
	@ParameterParser(syntax = "('type' arg1=STRING)? ('at' arg2=OFFSET)?")
	@Xpect
	public void succs(@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			String type, IEObjectCoveringRegion offset) {

		ControlFlowType cfType = getControlFlowType(type);
		ControlFlowElement cfe = getControlFlowElement(offset);
		List<ControlFlowElement> succs = flowAnalyses.getSuccessors(cfe);
		filterByControlFlowType(cfe, succs, cfType);

		List<String> succTexts = new LinkedList<>();
		for (ControlFlowElement succ : succs) {
			String succText = FGUtils.getTextLabel(succ);
			succTexts.add(succText);
		}
		expectation.assertEquals(succTexts);
	}

	private ControlFlowType getControlFlowType(String type) {
		ControlFlowType cfType = null;
		if (type != null && !type.isEmpty()) {
			cfType = ControlFlowType.valueOf(type);
			if (cfType == null) {
				fail("Type '" + type + "' is not a control flow type");
				return null;
			}
		}
		return cfType;
	}

	private void filterByControlFlowType(ControlFlowElement start, List<ControlFlowElement> succList,
			ControlFlowType cfType) {

		if (cfType == null)
			return;

		for (Iterator<ControlFlowElement> succIt = succList.iterator(); succIt.hasNext();) {
			Set<ControlFlowType> currCFTypes = flowAnalyses.getControlFlowTypeToSuccessors(start, succIt.next());
			if (!currCFTypes.contains(cfType)) {
				succIt.remove();
			}
		}
	}

	/**
	 * This xpect method can evaluate all paths from a given start code element. If no start code element is specified,
	 * the first code element of the containing function.
	 */
	@ParameterParser(syntax = "('from' arg1=OFFSET)?")
	@Xpect
	public void allPaths(@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			IEObjectCoveringRegion offset) {

		ControlFlowElement cfe = null;
		if (offset != null) {
			cfe = getControlFlowElement(offset);
		}
		AllPathPrintWalker appw = new AllPathPrintWalker(cfe);
		flowAnalyses.performAnalyzes(appw);
		List<String> pathStrings = appw.getPathStrings();

		expectation.assertEquals(pathStrings);
	}

	/**
	 * This xpect method can evaluate all edges of the containing function.
	 */
	@ParameterParser(syntax = "('from' arg1=OFFSET)?")
	@Xpect
	public void allEdges(@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			IEObjectCoveringRegion offset) {

		ControlFlowElement cfe = null;
		if (offset != null) {
			cfe = getControlFlowElement(offset);
		}
		cfe = FGUtils.getCFContainer(cfe);

		AllNodesAndEdgesPrintWalker anaepw = new AllNodesAndEdgesPrintWalker(cfe);
		flowAnalyses.performAnalyzes(anaepw);
		List<String> pathStrings = anaepw.getAllEdgeStrings();

		expectation.assertEquals(pathStrings);
	}

	private ControlFlowElement getControlFlowElement(IEObjectCoveringRegion offset) {
		EObject context = offset.getEObject();
		if (!(context instanceof ControlFlowElement)) {
			fail("Element '" + FGUtils.getTextLabel(context) + "' is not a control flow element");
		}
		ControlFlowElement cfe = (ControlFlowElement) context;
		return cfe;
	}

}
