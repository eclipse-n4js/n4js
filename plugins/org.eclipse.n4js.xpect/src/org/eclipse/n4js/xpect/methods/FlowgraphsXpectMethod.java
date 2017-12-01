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

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.ASTIterator;
import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyzer;
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyzerDataRecorder;
import org.eclipse.n4js.flowgraphs.analysers.AllBranchPrintVisitor;
import org.eclipse.n4js.flowgraphs.analysers.AllNodesAndEdgesPrintVisitor;
import org.eclipse.n4js.flowgraphs.analysers.DummyForwardBackwardVisitor;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitor;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitorInternal;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitorInternal.Mode;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter.EObjectCoveringRegion;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter.IEObjectCoveringRegion;
import org.eclipse.n4js.xpect.methods.scoping.IN4JSCommaSeparatedValuesExpectation;
import org.eclipse.n4js.xpect.methods.scoping.N4JSCommaSeparatedValuesExpectation;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.xbase.lib.Pair;
import org.xpect.XpectImport;
import org.xpect.expectation.IStringExpectation;
import org.xpect.expectation.StringExpectation;
import org.xpect.parameter.ParameterParser;
import org.xpect.runner.Xpect;

/**
 */
@XpectImport(N4JSOffsetAdapter.class)
public class FlowgraphsXpectMethod {

	N4JSFlowAnalyzer getFlowAnalyzer(EObject eo) {
		Script script = EcoreUtil2.getContainerOfType(eo, Script.class);
		N4JSFlowAnalyzer flowAnalyzer = new N4JSFlowAnalyzer();
		flowAnalyzer.createGraphs(script);
		return flowAnalyzer;
	}

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

		EObject context = offset.getEObject();
		Iterator<ControlFlowElement> astIter = new ASTIterator(context);

		int idx = 0;
		List<String> astElements = new LinkedList<>();
		while (astIter.hasNext()) {
			ControlFlowElement cfe = astIter.next();
			String elem = idx + ": " + FGUtils.getSourceText(cfe);
			astElements.add(elem);
			idx++;
		}

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

		ControlFlowType cfType = getControlFlowType(type);
		ControlFlowElement cfe = getCFE(offset);
		Set<ControlFlowElement> preds = getFlowAnalyzer(cfe).getPredecessorsSkipInternal(cfe);
		filterByControlFlowType(cfe, preds, cfType);

		List<String> predTexts = new LinkedList<>();
		for (ControlFlowElement succ : preds) {
			String predText = FGUtils.getSourceText(succ);
			predTexts.add(predText);
		}
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

		ControlFlowType cfType = getControlFlowType(type);
		ControlFlowElement cfe = getCFE(offset);
		Set<ControlFlowElement> succs = getFlowAnalyzer(cfe).getSuccessorsSkipInternal(cfe);
		filterByControlFlowType(cfe, succs, cfType);

		List<String> succTexts = new LinkedList<>();
		for (ControlFlowElement succ : succs) {
			String succText = FGUtils.getSourceText(succ);
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

	private void filterByControlFlowType(ControlFlowElement start, Collection<ControlFlowElement> succList,
			ControlFlowType cfType) {

		if (cfType == null)
			return;

		for (Iterator<ControlFlowElement> succIt = succList.iterator(); succIt.hasNext();) {
			N4JSFlowAnalyzer flowAnalyzer = getFlowAnalyzer(start);
			Set<ControlFlowType> currCFTypes = flowAnalyzer.getControlFlowTypeToSuccessors(start, succIt.next());
			if (!currCFTypes.contains(cfType)) {
				succIt.remove();
			}
		}
	}

	/** This xpect method can evaluate if the tested element is a transitive predecessor of the given element. */
	@ParameterParser(syntax = "'from' arg0=OFFSET ('to' arg1=OFFSET)? ('notTo' arg2=OFFSET)? ('via' arg3=OFFSET)? ('notVia' arg4=OFFSET)? ('pleaseNeverUseThisParameterSinceItExistsOnlyToGetAReferenceOffset' arg5=OFFSET)?")
	@Xpect
	public void path(IEObjectCoveringRegion fromOffset, IEObjectCoveringRegion toOffset,
			IEObjectCoveringRegion notToOffset, IEObjectCoveringRegion viaOffset,
			IEObjectCoveringRegion notViaOffset, IEObjectCoveringRegion referenceOffset) {

		EObjectCoveringRegion toOffsetImpl = (EObjectCoveringRegion) toOffset;
		EObjectCoveringRegion notToOffsetImpl = (EObjectCoveringRegion) notToOffset;
		EObjectCoveringRegion viaOffsetImpl = (EObjectCoveringRegion) viaOffset;
		EObjectCoveringRegion notViaOffsetImpl = (EObjectCoveringRegion) notViaOffset;
		EObjectCoveringRegion referenceOffsetImpl = (EObjectCoveringRegion) referenceOffset;

		ControlFlowElement fromCFE = getCFE(fromOffset);
		ControlFlowElement toCFE = getCFEWithReference(toOffsetImpl, referenceOffsetImpl);
		ControlFlowElement notToCFE = getCFEWithReference(notToOffsetImpl, referenceOffsetImpl);
		ControlFlowElement viaCFE = getCFEWithReference(viaOffsetImpl, referenceOffsetImpl);
		ControlFlowElement notViaCFE = getCFEWithReference(notViaOffsetImpl, referenceOffsetImpl);
		ControlFlowElement targetCFE = (toCFE != null) ? toCFE : notToCFE;

		boolean expectPathExists = toCFE != null;

		if (fromCFE == null) {
			fail("Element 'from' could not be found");
		}
		if (targetCFE == null) {
			fail("Element 'to' or 'notTo' could not be found or before 'from'");
		}

		boolean actualPathExists;
		if (viaCFE != null) {
			actualPathExists = getFlowAnalyzer(fromCFE).isTransitiveSuccessor(fromCFE, viaCFE, notViaCFE);
			actualPathExists &= getFlowAnalyzer(fromCFE).isTransitiveSuccessor(viaCFE, targetCFE, notViaCFE);
		} else {
			actualPathExists = getFlowAnalyzer(fromCFE).isTransitiveSuccessor(fromCFE, targetCFE, notViaCFE);
		}

		if (expectPathExists && !actualPathExists) {
			fail("Path not found");
		}
		if (!expectPathExists && actualPathExists) {
			fail("A path was found");
		}
	}

	private ControlFlowElement getCFEWithReference(EObjectCoveringRegion offset, EObjectCoveringRegion reference) {
		ControlFlowElement cfe = null;
		if (reference.getOffset() < offset.getOffset()) {
			cfe = getCFE(offset);
		}
		return cfe;
	}

	/**
	 * This xpect method can evaluate all branches that are merged at the given node name.
	 */
	@ParameterParser(syntax = "('pleaseNeverUseThisParameterSinceItExistsOnlyToGetAReferenceOffset' arg1=OFFSET)?")
	@Xpect
	public void allMergeBranches(@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			IEObjectCoveringRegion referenceOffset) {

		N4JSFlowAnalyzerDataRecorder.setEnabled(true);
		GraphVisitor gv = new DummyForwardBackwardVisitor();
		ControlFlowElement referenceCFE = getCFE(referenceOffset);
		getFlowAnalyzer(referenceCFE).accept(gv);
		N4JSFlowAnalyzerDataRecorder.setEnabled(false);
		performBranchAnalysis(referenceOffset, null, referenceOffset);
		List<String> edgeStrings = new LinkedList<>();

		int groupIdx = 0;
		List<Pair<Node, List<ControlFlowEdge>>> mergedEdges = N4JSFlowAnalyzerDataRecorder.getMergedEdges();

		for (Pair<Node, List<ControlFlowEdge>> pair : mergedEdges) {
			Node startNode = pair.getKey();
			List<ControlFlowEdge> edges = pair.getValue();
			for (ControlFlowEdge edge : edges) {
				String c = edge.start == startNode ? "B" : "F";
				edgeStrings.add(c + groupIdx + ": " + edge.toString());
			}
			groupIdx++;
		}

		Collections.sort(edgeStrings);
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

		AllBranchPrintVisitor appw = performBranchAnalysis(offset, directionName, referenceOffset);
		List<String> branchStrings = appw.getBranchStrings();

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

		AllBranchPrintVisitor appw = performBranchAnalysis(offset, directionName, referenceOffset);
		List<String> pathStrings = appw.getPathStrings();

		expectation.assertEquals(pathStrings);
	}

	private AllBranchPrintVisitor performBranchAnalysis(IEObjectCoveringRegion offset, String directionName,
			IEObjectCoveringRegion referenceOffset) {

		EObjectCoveringRegion offsetImpl = (EObjectCoveringRegion) offset;
		EObjectCoveringRegion referenceOffsetImpl = (EObjectCoveringRegion) referenceOffset;
		ControlFlowElement startCFE = getCFEWithReference(offsetImpl, referenceOffsetImpl);
		ControlFlowElement referenceCFE = getCFE(referenceOffset);
		GraphVisitorInternal.Mode direction = getDirection(directionName);

		ControlFlowElement container = FGUtils.getCFContainer(referenceCFE);
		AllBranchPrintVisitor appw = new AllBranchPrintVisitor(container, startCFE, direction);
		getFlowAnalyzer(referenceCFE).accept(appw);
		return appw;
	}

	private GraphVisitorInternal.Mode getDirection(String directionName) {
		GraphVisitorInternal.Mode direction = Mode.Forward;
		if (directionName != null && !directionName.isEmpty()) {
			direction = GraphVisitorInternal.Mode.valueOf(directionName);
			if (direction == null) {
				fail("Unknown direction");
			}
		}
		return direction;
	}

	/** This xpect method can evaluate all edges of the containing function. */
	@ParameterParser(syntax = "('from' arg1=OFFSET)?")
	@Xpect
	public void allEdges(@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			IEObjectCoveringRegion offset) {

		ControlFlowElement cfe = null;
		if (offset != null) {
			cfe = getCFE(offset);
		}
		cfe = FGUtils.getCFContainer(cfe);

		AllNodesAndEdgesPrintVisitor anaepw = new AllNodesAndEdgesPrintVisitor(cfe);
		getFlowAnalyzer(cfe).accept(anaepw);
		List<String> pathStrings = anaepw.getAllEdgeStrings();

		expectation.assertEquals(pathStrings);
	}

	/** This xpect method can evaluate all common predecessors of two {@link ControlFlowElement}s. */
	@ParameterParser(syntax = "'of' arg1=OFFSET 'and' arg2=OFFSET")
	@Xpect
	public void commonPreds(@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			IEObjectCoveringRegion a, IEObjectCoveringRegion b) {

		ControlFlowElement aCFE = getCFE(a);
		ControlFlowElement bCFE = getCFE(b);

		Set<ControlFlowElement> commonPreds = getFlowAnalyzer(aCFE).getCommonPredecessors(aCFE, bCFE);
		List<String> commonPredStrs = new LinkedList<>();
		for (ControlFlowElement commonPred : commonPreds) {
			String commonPredStr = FGUtils.getSourceText(commonPred);
			commonPredStrs.add(commonPredStr);
		}

		expectation.assertEquals(commonPredStrs);
	}

	private ControlFlowElement getCFE(IEObjectCoveringRegion offset) {
		EObject context = offset.getEObject();
		if (!(context instanceof ControlFlowElement)) {
			fail("Element '" + FGUtils.getSourceText(context) + "' is not a control flow element");
		}
		ControlFlowElement cfe = (ControlFlowElement) context;
		return cfe;
	}

	/** This xpect method can evaluate the control flow container of a given {@link ControlFlowElement}. */
	@ParameterParser(syntax = "('of' arg1=OFFSET)?")
	@Xpect
	public void cfContainer(@StringExpectation IStringExpectation expectation, IEObjectCoveringRegion offset) {
		ControlFlowElement cfe = getCFE(offset);
		ControlFlowElement container = getFlowAnalyzer(cfe).getContainer(cfe);
		EObject containerContainer = container.eContainer();

		String ccString = (containerContainer != null) ? FGUtils.getClassName(containerContainer) + "::" : "";
		String containerStr = ccString + FGUtils.getClassName(container);
		expectation.assertEquals(containerStr);
	}

}
