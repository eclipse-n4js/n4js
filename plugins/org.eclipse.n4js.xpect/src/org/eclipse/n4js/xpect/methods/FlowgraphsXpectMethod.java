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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyzer;
import org.eclipse.n4js.flowgraphs.analysers.AllNodesAndEdgesPrintVisitor;
import org.eclipse.n4js.flowgraphs.analysers.AllPathPrintVisitor;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitorInternal;
import org.eclipse.n4js.flowgraphs.analyses.GraphVisitorInternal.Mode;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter.EObjectCoveringRegion;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter.IEObjectCoveringRegion;
import org.eclipse.n4js.xpect.methods.scoping.IN4JSCommaSeparatedValuesExpectation;
import org.eclipse.n4js.xpect.methods.scoping.N4JSCommaSeparatedValuesExpectation;
import org.eclipse.xtext.EcoreUtil2;
import org.xpect.XpectImport;
import org.xpect.expectation.IStringExpectation;
import org.xpect.expectation.StringExpectation;
import org.xpect.parameter.ParameterParser;
import org.xpect.runner.Xpect;

/**
 */
@XpectImport(N4JSOffsetAdapter.class)
public class FlowgraphsXpectMethod {

	// @Inject
	// private ASTMetaInfoCacheHelper astMetaInfoCacheHelper;

	N4JSFlowAnalyzer getFlowAnalyzer(EObject eo) {
		Script script = EcoreUtil2.getContainerOfType(eo, Script.class);
		N4JSFlowAnalyzer flowAnalyzer = new N4JSFlowAnalyzer();
		flowAnalyzer.createGraphs(script);
		// ASTMetaInfoCache cache = astMetaInfoCacheHelper.getOrCreate((N4JSResource) eo.eResource());
		// flowAnalyzer = cache.getFlowAnalyses();
		return flowAnalyzer;
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
		ControlFlowElement cfe = getControlFlowElement(offset);
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
		ControlFlowElement cfe = getControlFlowElement(offset);
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
	@ParameterParser(syntax = "'from' arg0=OFFSET ('to' arg1=OFFSET)? ('notTo' arg2=OFFSET)? ('pleaseNeverUseThisParameterSinceItExistsOnlyToGetAReferenceOffset' arg3=OFFSET)?")
	@Xpect
	public void path(IEObjectCoveringRegion fromOffset, IEObjectCoveringRegion toOffset,
			IEObjectCoveringRegion notToOffset, IEObjectCoveringRegion referenceOffset) {

		EObjectCoveringRegion toOffsetImpl = (EObjectCoveringRegion) toOffset;
		EObjectCoveringRegion notToOffsetImpl = (EObjectCoveringRegion) notToOffset;
		EObjectCoveringRegion referenceOffsetImpl = (EObjectCoveringRegion) referenceOffset;

		ControlFlowElement fromCFE = getControlFlowElement(fromOffset);
		ControlFlowElement toCFE = null;
		if (referenceOffsetImpl.getOffset() < toOffsetImpl.getOffset()) {
			toCFE = getControlFlowElement(toOffset);
		}
		ControlFlowElement notToCFE = null;
		if (referenceOffsetImpl.getOffset() < notToOffsetImpl.getOffset()) {
			notToCFE = getControlFlowElement(notToOffsetImpl);
		}

		if (fromCFE == null) {
			fail("Element 'from' could not be found");
		}
		if (toCFE == null && notToCFE == null) {
			fail("Element 'to' or 'notTo' could not be found or before 'from'");
		}

		if (toCFE != null) {
			boolean isTransitiveSuccs = getFlowAnalyzer(fromCFE).isTransitiveSuccessor(fromCFE, toCFE);
			if (!isTransitiveSuccs) {
				fail("Elements are no transitive successors");
			}
		}
		if (notToCFE != null) {
			boolean isTransitiveSuccs = getFlowAnalyzer(fromCFE).isTransitiveSuccessor(fromCFE, notToCFE);
			if (isTransitiveSuccs) {
				fail("Elements are transitive successors");
			}
		}
	}

	/**
	 * This xpect method can evaluate all paths from a given start code element. If no start code element is specified,
	 * the first code element of the containing function.
	 */
	@ParameterParser(syntax = "('from' arg1=OFFSET)? ('direction' arg2=STRING)? ('pleaseNeverUseThisParameterSinceItExistsOnlyToGetAReferenceOffset' arg3=OFFSET)?")
	@Xpect
	public void allPaths(@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			IEObjectCoveringRegion offset, String directionName, IEObjectCoveringRegion referenceOffset) {

		EObjectCoveringRegion offsetImpl = (EObjectCoveringRegion) offset;
		EObjectCoveringRegion referenceOffsetImpl = (EObjectCoveringRegion) referenceOffset;
		ControlFlowElement startCFE = null;
		if (referenceOffsetImpl.getOffset() < offsetImpl.getOffset()) {
			startCFE = getControlFlowElement(offsetImpl);
		}
		ControlFlowElement referenceCFE = getControlFlowElement(referenceOffset);
		GraphVisitorInternal.Mode direction = getDirection(directionName);

		ControlFlowElement container = FGUtils.getCFContainer(referenceCFE);
		AllPathPrintVisitor appw = new AllPathPrintVisitor(container, startCFE, direction);
		getFlowAnalyzer(referenceCFE).accept(appw);
		List<String> pathStrings = appw.getPathStrings();

		expectation.assertEquals(pathStrings);
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
			cfe = getControlFlowElement(offset);
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

		ControlFlowElement aCFE = getControlFlowElement(a);
		ControlFlowElement bCFE = getControlFlowElement(b);

		Set<ControlFlowElement> commonPreds = getFlowAnalyzer(aCFE).getCommonPredecessors(aCFE, bCFE);
		List<String> commonPredStrs = new LinkedList<>();
		for (ControlFlowElement commonPred : commonPreds) {
			String commonPredStr = FGUtils.getSourceText(commonPred);
			commonPredStrs.add(commonPredStr);
		}

		expectation.assertEquals(commonPredStrs);
	}

	private ControlFlowElement getControlFlowElement(IEObjectCoveringRegion offset) {
		EObject context = offset.getEObject();
		if (!(context instanceof ControlFlowElement)) {
			fail("Element '" + FGUtils.getSourceText(context) + "' is not a control flow element");
		}
		ControlFlowElement cfe = (ControlFlowElement) context;
		return cfe;
	}

	/** This xpect method can evaluate all common predecessors of two {@link ControlFlowElement}s. */
	@ParameterParser(syntax = "('pleaseNeverUseThisParameterSinceItExistsOnlyToGetAReferenceOffset' arg1=OFFSET)?")
	@Xpect
	public void allIslandElems(@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			IEObjectCoveringRegion referenceOffset) {

		ControlFlowElement referenceCFE = getControlFlowElement(referenceOffset);
		ControlFlowElement container = getFlowAnalyzer(referenceCFE).getContainer(referenceCFE);
		AllNodesAndEdgesPrintVisitor anaepw = new AllNodesAndEdgesPrintVisitor(container);
		getFlowAnalyzer(referenceCFE).accept(anaepw);
		List<String> nodeStrings = anaepw.getAllIslandsNodeStrings();
		expectation.assertEquals(nodeStrings);
	}

	/** This xpect method can evaluate the control flow container of a given {@link ControlFlowElement}. */
	@ParameterParser(syntax = "('of' arg1=OFFSET)?")
	@Xpect
	public void cfContainer(@StringExpectation IStringExpectation expectation, IEObjectCoveringRegion offset) {
		ControlFlowElement cfe = getControlFlowElement(offset);
		ControlFlowElement container = getFlowAnalyzer(cfe).getContainer(cfe);
		EObject containerContainer = container.eContainer();

		String ccString = (containerContainer != null) ? FGUtils.getClassName(containerContainer) + "::" : "";
		String containerStr = ccString + FGUtils.getClassName(container);
		expectation.assertEquals(containerStr);
	}

	/** This xpect method can evaluate the control flow container of a given {@link ControlFlowElement}. */
	@ParameterParser(syntax = "('of' arg1=OFFSET)?")
	@Xpect
	public void allCatchBlocks(@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			IEObjectCoveringRegion offset) {

		ControlFlowElement cfe = getControlFlowElement(offset);
		ControlFlowElement container = getFlowAnalyzer(cfe).getContainer(cfe);
		List<Block> catchBlocks = getFlowAnalyzer(cfe).getCatchBlocksOfContainer(container);

		List<String> catchBlockStrs = new LinkedList<>();
		for (Block catchBlock : catchBlocks) {
			String catchBlockStr = FGUtils.getSourceText(catchBlock);
			catchBlockStrs.add(catchBlockStr);
		}
		expectation.assertEquals(catchBlockStrs);
	}
}
