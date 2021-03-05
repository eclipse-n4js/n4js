/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.helper.server.xt;

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
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyser;
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyserDataRecorder;
import org.eclipse.n4js.flowgraphs.analysers.AllBranchPrintVisitor;
import org.eclipse.n4js.flowgraphs.analysers.AllNodesAndEdgesPrintVisitor;
import org.eclipse.n4js.flowgraphs.analysers.DummyBackwardVisitor;
import org.eclipse.n4js.flowgraphs.analysers.DummyForwardVisitor;
import org.eclipse.n4js.flowgraphs.analysers.InstanceofGuardAnalyser;
import org.eclipse.n4js.flowgraphs.analysis.GraphVisitor;
import org.eclipse.n4js.flowgraphs.analysis.TraverseDirection;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardAssertion;
import org.eclipse.n4js.flowgraphs.dataflow.guards.InstanceofGuard;
import org.eclipse.n4js.flowgraphs.model.ControlFlowEdge;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 */
public class XtMethodsFlowgraphs {

	N4JSFlowAnalyser createFlowAnalyzer(EObject eo) {
		Script script = EcoreUtil2.getContainerOfType(eo, Script.class);
		N4JSFlowAnalyser flowAnalyzer = new N4JSFlowAnalyser();
		flowAnalyzer.createGraphs(script);
		return flowAnalyzer;
	}

	/** Implementation for {@link XtIdeTest#allMergeBranches(XtMethodData)} */
	public List<String> getAllMergeBranches(IEObjectCoveringRegion referenceOffset) {
		N4JSFlowAnalyserDataRecorder.setEnabled(true);
		GraphVisitor dfv = new DummyForwardVisitor();
		GraphVisitor dbv = new DummyBackwardVisitor();
		ControlFlowElement referenceCFE = getCFE(referenceOffset);
		createFlowAnalyzer(referenceCFE).accept(dfv, dbv);
		N4JSFlowAnalyserDataRecorder.setEnabled(false);
		performBranchAnalysis(referenceOffset, null, referenceOffset);
		List<String> edgeStrings = new LinkedList<>();

		int groupIdx = 0;
		List<Pair<Node, List<ControlFlowEdge>>> mergedEdges = N4JSFlowAnalyserDataRecorder.getMergedEdges();

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
		return edgeStrings;
	}

	/** Implementation for {@link XtIdeTest#allBranches(XtMethodData)} */
	public List<String> getAllBranches(IEObjectCoveringRegion offset, String directionName,
			IEObjectCoveringRegion referenceOffset) {

		AllBranchPrintVisitor appw = performBranchAnalysis(offset, directionName, referenceOffset);
		List<String> branchStrings = appw.getBranchStrings();

		return branchStrings;
	}

	/** Implementation for {@link XtIdeTest#allEdges(XtMethodData)} */
	public List<String> getAllEdges(IEObjectCoveringRegion offset) {
		ControlFlowElement cfe = null;
		if (offset != null) {
			cfe = getCFE(offset);
		}
		cfe = FGUtils.getCFContainer(cfe);

		AllNodesAndEdgesPrintVisitor anaepw = new AllNodesAndEdgesPrintVisitor(cfe);
		createFlowAnalyzer(cfe).accept(anaepw);
		List<String> pathStrings = anaepw.getAllEdgeStrings();

		return pathStrings;
	}

	/** Implementation for {@link XtIdeTest#allPaths(XtMethodData)} */
	public List<String> getAllPaths(IEObjectCoveringRegion offset, String directionName,
			IEObjectCoveringRegion referenceOffset) {

		AllBranchPrintVisitor appw = performBranchAnalysis(offset, directionName, referenceOffset);
		List<String> pathStrings = appw.getPathStrings();

		return pathStrings;
	}

	/** Implementation for {@link XtIdeTest#astOrder(XtMethodData)} */
	public List<String> getAstOrder(IEObjectCoveringRegion offset) {
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

		return astElements;
	}

	/** Implementation for {@link XtIdeTest#cfContainer(XtMethodData)} */
	public String getCfContainer(IEObjectCoveringRegion offset) {
		ControlFlowElement cfe = getCFE(offset);
		ControlFlowElement container = createFlowAnalyzer(cfe).getContainer(cfe);
		EObject containerContainer = container.eContainer();

		String ccString = (containerContainer != null) ? FGUtils.getClassName(containerContainer) + "::" : "";
		String containerStr = ccString + FGUtils.getClassName(container);
		return containerStr;
	}

	/** Implementation for {@link XtIdeTest#commonPreds(XtMethodData)} */
	public List<String> getCommonPreds(IEObjectCoveringRegion a, IEObjectCoveringRegion b) {
		ControlFlowElement aCFE = getCFE(a);
		ControlFlowElement bCFE = getCFE(b);

		Set<ControlFlowElement> commonPreds = createFlowAnalyzer(aCFE).getCommonPredecessors(aCFE, bCFE);
		List<String> commonPredStrs = new LinkedList<>();
		for (ControlFlowElement commonPred : commonPreds) {
			String commonPredStr = FGUtils.getSourceText(commonPred);
			commonPredStrs.add(commonPredStr);
		}

		return commonPredStrs;
	}

	/** Implementation for {@link XtIdeTest#instanceofguard(XtMethodData)} */
	public List<String> getInstanceofguard(IEObjectCoveringRegion offset) {
		ControlFlowElement cfe = getCFE(offset);

		InstanceofGuardAnalyser iga = new InstanceofGuardAnalyser();
		N4JSFlowAnalyser flowAnalyzer = createFlowAnalyzer(cfe);
		flowAnalyzer.accept(iga);

		Collection<InstanceofGuard> ioGuards = iga.getDefinitiveGuards(cfe);

		List<String> commonPredStrs = new LinkedList<>();
		for (InstanceofGuard ioGuard : ioGuards) {
			if (ioGuard.asserts == GuardAssertion.AlwaysHolds) {
				String symbolText = FGUtils.getSourceText(ioGuard.symbolCFE);
				String typeText = FGUtils.getSourceText(ioGuard.typeIdentifier);
				commonPredStrs.add(symbolText + "<:" + typeText);
			} else if (ioGuard.asserts == GuardAssertion.NeverHolds) {
				String symbolText = FGUtils.getSourceText(ioGuard.symbolCFE);
				String typeText = FGUtils.getSourceText(ioGuard.typeIdentifier);
				commonPredStrs.add(symbolText + "!<:" + typeText);
			}
		}

		return commonPredStrs;
	}

	/** Implementation for {@link XtIdeTest#path(XtMethodData)} */
	public void getPath(IEObjectCoveringRegion fromOffset, IEObjectCoveringRegion toOffset,
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
			actualPathExists = createFlowAnalyzer(fromCFE).isTransitiveSuccessor(fromCFE, viaCFE, notViaCFE);
			actualPathExists &= createFlowAnalyzer(fromCFE).isTransitiveSuccessor(viaCFE, targetCFE, notViaCFE);
		} else {
			actualPathExists = createFlowAnalyzer(fromCFE).isTransitiveSuccessor(fromCFE, targetCFE, notViaCFE);
		}

		if (expectPathExists && !actualPathExists) {
			fail("Path not found");
		}
		if (!expectPathExists && actualPathExists) {
			fail("A path was found");
		}
	}

	/** Implementation for {@link XtIdeTest#preds(XtMethodData)} */
	public List<String> getPreds(String type, IEObjectCoveringRegion offset) {
		ControlFlowType cfType = getControlFlowType(type);
		ControlFlowElement cfe = getCFE(offset);
		Set<ControlFlowElement> preds = createFlowAnalyzer(cfe).getPredecessorsSkipInternal(cfe);
		filterByControlFlowType(cfe, preds, cfType);

		List<String> predTexts = new LinkedList<>();
		for (ControlFlowElement succ : preds) {
			String predText = FGUtils.getSourceText(succ);
			predTexts.add(predText);
		}

		return predTexts;
	}

	/** Implementation for {@link XtIdeTest#succs(XtMethodData)} */
	public List<String> getSuccs(String type, IEObjectCoveringRegion offset) {
		ControlFlowType cfType = getControlFlowType(type);
		ControlFlowElement cfe = getCFE(offset);
		Set<ControlFlowElement> succs = createFlowAnalyzer(cfe).getSuccessorsSkipInternal(cfe);
		filterByControlFlowType(cfe, succs, cfType);

		List<String> succTexts = new LinkedList<>();
		for (ControlFlowElement succ : succs) {
			String succText = FGUtils.getSourceText(succ);
			succTexts.add(succText);
		}

		return succTexts;
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
			N4JSFlowAnalyser flowAnalyzer = createFlowAnalyzer(start);
			Set<ControlFlowType> currCFTypes = flowAnalyzer.getControlFlowTypeToSuccessors(start, succIt.next());
			if (!currCFTypes.contains(cfType)) {
				succIt.remove();
			}
		}
	}

	private ControlFlowElement getCFEWithReference(EObjectCoveringRegion offset, EObjectCoveringRegion reference) {
		ControlFlowElement cfe = null;
		if (offset != null && reference.getOffset() < offset.getOffset()) {
			cfe = getCFE(offset);
		}
		return cfe;
	}

	private AllBranchPrintVisitor performBranchAnalysis(IEObjectCoveringRegion offset, String directionName,
			IEObjectCoveringRegion referenceOffset) {

		EObjectCoveringRegion offsetImpl = (EObjectCoveringRegion) offset;
		EObjectCoveringRegion referenceOffsetImpl = (EObjectCoveringRegion) referenceOffset;
		ControlFlowElement startCFE = getCFEWithReference(offsetImpl, referenceOffsetImpl);
		ControlFlowElement referenceCFE = getCFE(referenceOffset);
		TraverseDirection direction = getDirection(directionName);

		ControlFlowElement container = FGUtils.getCFContainer(referenceCFE);
		AllBranchPrintVisitor appw = new AllBranchPrintVisitor(container, startCFE, direction);
		createFlowAnalyzer(referenceCFE).accept(appw);
		return appw;
	}

	private TraverseDirection getDirection(String directionName) {
		TraverseDirection direction = TraverseDirection.Forward;
		if (directionName != null && !directionName.isEmpty()) {
			direction = TraverseDirection.valueOf(directionName);
			if (direction == null) {
				fail("Unknown direction");
			}
		}
		return direction;
	}

	private ControlFlowElement getCFE(IEObjectCoveringRegion offset) {
		EObject context = offset.getEObject();
		if (!(context instanceof ControlFlowElement)) {
			fail("Element '" + FGUtils.getSourceText(context) + "' is not a control flow element");
		}
		ControlFlowElement cfe = (ControlFlowElement) context;
		return cfe;
	}

}
