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
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyses;
import org.eclipse.n4js.flowgraphs.analysers.AllNodesAndEdgesPrintWalker;
import org.eclipse.n4js.flowgraphs.analysers.AllPathPrintWalker;
import org.eclipse.n4js.flowgraphs.analyses.GraphWalkerInternal;
import org.eclipse.n4js.flowgraphs.analyses.GraphWalkerInternal.Direction;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.postprocessing.ASTMetaInfoCache;
import org.eclipse.n4js.postprocessing.ASTMetaInfoCacheHelper;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter.EObjectCoveringRegion;
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
	private ASTMetaInfoCacheHelper astMetaInfoCacheHelper;

	N4JSFlowAnalyses getFlowAnalyses(EObject eo) {
		N4JSFlowAnalyses flowAnalyses = null;
		// Script script = EcoreUtil2.getContainerOfType(eo, Script.class);
		// flowAnalyses = new N4JSFlowAnalyses();
		// flowAnalyses.perform(script);

		ASTMetaInfoCache cache = astMetaInfoCacheHelper.getOrCreate((N4JSResource) eo.eResource());
		flowAnalyses = cache.getFlowAnalyses();
		return flowAnalyses;
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
		Set<ControlFlowElement> succs = getFlowAnalyses(cfe).getSuccessors(cfe);
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

	private void filterByControlFlowType(ControlFlowElement start, Collection<ControlFlowElement> succList,
			ControlFlowType cfType) {

		if (cfType == null)
			return;

		for (Iterator<ControlFlowElement> succIt = succList.iterator(); succIt.hasNext();) {
			Set<ControlFlowType> currCFTypes = getFlowAnalyses(start).getControlFlowTypeToSuccessors(start,
					succIt.next());
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
			boolean isTransitiveSuccs = getFlowAnalyses(fromCFE).isTransitiveSuccessor(fromCFE, toCFE);
			if (!isTransitiveSuccs) {
				fail("Elements are no transitive successors");
			}
		}
		if (notToCFE != null) {
			boolean isTransitiveSuccs = getFlowAnalyses(fromCFE).isTransitiveSuccessor(fromCFE, notToCFE);
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
		GraphWalkerInternal.Direction direction = getDirection(directionName);

		ControlFlowElement container = FGUtils.getCFContainer(referenceCFE);
		AllPathPrintWalker appw = new AllPathPrintWalker(container, startCFE, direction);
		getFlowAnalyses(referenceCFE).performAnalyzes(appw);
		List<String> pathStrings = appw.getPathStrings();

		expectation.assertEquals(pathStrings);
	}

	private GraphWalkerInternal.Direction getDirection(String directionName) {
		GraphWalkerInternal.Direction direction = Direction.Forward;
		if (directionName != null && !directionName.isEmpty()) {
			direction = GraphWalkerInternal.Direction.valueOf(directionName);
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

		AllNodesAndEdgesPrintWalker anaepw = new AllNodesAndEdgesPrintWalker(cfe);
		getFlowAnalyses(cfe).performAnalyzes(anaepw);
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

		Set<ControlFlowElement> commonPreds = getFlowAnalyses(aCFE).getCommonPredecessors(aCFE, bCFE);
		List<String> commonPredStrs = new LinkedList<>();
		for (ControlFlowElement commonPred : commonPreds) {
			String commonPredStr = FGUtils.getTextLabel(commonPred);
			commonPredStrs.add(commonPredStr);
		}

		expectation.assertEquals(commonPredStrs);
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
