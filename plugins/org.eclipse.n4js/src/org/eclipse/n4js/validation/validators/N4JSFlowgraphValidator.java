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
package org.eclipse.n4js.validation.validators;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyser;
import org.eclipse.n4js.flowgraphs.analysers.DeadCodeAnalyser;
import org.eclipse.n4js.flowgraphs.analysers.DeadCodeAnalyser.DeadCodeRegion;
import org.eclipse.n4js.flowgraphs.analysers.NullDereferenceAnalyser;
import org.eclipse.n4js.flowgraphs.analysers.NullDereferenceResult;
import org.eclipse.n4js.flowgraphs.analysers.UsedBeforeDeclaredAnalyser;
import org.eclipse.n4js.flowgraphs.dataflow.Symbol;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.utils.FindReferenceHelper;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CancelableDiagnostician;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.common.base.Strings;
import com.google.inject.Inject;

/**
 * This validator validates all control and data flow related issues.
 */
public class N4JSFlowgraphValidator extends AbstractN4JSDeclarativeValidator {
	static private final DataCollector dcFlowGraphs = DataCollectors.INSTANCE
			.getOrCreateDataCollector("Flow Graphs");
	static private final DataCollector dcPostprocessing = DataCollectors.INSTANCE
			.getOrCreateDataCollector("Postprocessing", "Flow Graphs");

	@Inject
	private OperationCanceledManager operationCanceledManager;

	@Inject
	private FindReferenceHelper findReferenceHelper;

	@Inject
	private IN4JSCore n4jsCore;

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	@Override
	public void register(EValidatorRegistrar registrar) {
		// nop
	}

	private Void checkCancelled() {
		CancelIndicator cancelIndicator = (CancelIndicator) getContext().get(CancelableDiagnostician.CANCEL_INDICATOR);
		operationCanceledManager.checkCanceled(cancelIndicator);
		return null;
	}

	/**
	 * Checks all flow graph related validations
	 */
	@Check
	public void checkFlowGraphs(Script script) {
		// Note: The Flow Graph is NOT stored in the meta info cache. Hence, it is created here at use site.
		// In case the its creation is moved to the N4JSPostProcessor, care about an increase in memory consumption.
		N4JSFlowAnalyser flowAnalyzer = new N4JSFlowAnalyser(this::checkCancelled);

		DeadCodeAnalyser dcv = new DeadCodeAnalyser();
		UsedBeforeDeclaredAnalyser cvgv1 = new UsedBeforeDeclaredAnalyser();
		NullDereferenceAnalyser nda = new NullDereferenceAnalyser();

		flowAnalyzer.createGraphs(script);
		flowAnalyzer.accept(dcv, nda, cvgv1);

		String uriString = script.eResource().getURI().toString();
		Measurement msmnt1 = dcFlowGraphs.getMeasurement("flowGraphs_" + uriString);
		Measurement msmnt2 = dcPostprocessing.getMeasurement("createGraph_" + uriString);
		internalCheckDeadCode(dcv);
		internalCheckUsedBeforeDeclared(cvgv1);
		internalCheckNullDereference(nda);
		msmnt2.end();
		msmnt1.end();
	}

	// Req.107
	private void internalCheckDeadCode(DeadCodeAnalyser dcf) {
		Set<DeadCodeRegion> deadCodeRegions = dcf.getDeadCodeRegions();

		for (DeadCodeRegion deadCodeRegion : deadCodeRegions) {
			String stmtDescription = getStatementDescription(deadCodeRegion);
			String errCode = IssueCodes.FUN_DEAD_CODE;
			String msg = IssueCodes.getMessageForFUN_DEAD_CODE();
			if (stmtDescription != null) {
				msg = IssueCodes.getMessageForFUN_DEAD_CODE_WITH_PREDECESSOR(stmtDescription);
				errCode = IssueCodes.FUN_DEAD_CODE_WITH_PREDECESSOR;
			}
			addIssue(msg, deadCodeRegion.getContainer(), deadCodeRegion.getOffset(), deadCodeRegion.getLength(),
					errCode);
		}
	}

	private void internalCheckUsedBeforeDeclared(UsedBeforeDeclaredAnalyser ubda) {
		List<IdentifierRef> usedBeforeDeclared = ubda.getUsedButNotDeclaredIdentifierRefs();

		for (IdentifierRef idRef : usedBeforeDeclared) {
			String varName = idRef.getId().getName();
			String msg = IssueCodes.getMessageForCFG_USED_BEFORE_DECLARED(varName);
			addIssue(msg, idRef, IssueCodes.CFG_USED_BEFORE_DECLARED);
		}
	}

	private String getStatementDescription(DeadCodeRegion deadCodeRegion) {
		ControlFlowElement reachablePred = deadCodeRegion.getReachablePredecessor();
		if (reachablePred == null)
			return null;

		String keyword = keywordProvider.keyword(reachablePred);
		if (Strings.isNullOrEmpty(keyword)) {
			return reachablePred.eClass().getName();
		}
		return keyword;
	}

	private void internalCheckNullDereference(NullDereferenceAnalyser nda) {
		Iterable<NullDereferenceResult> nullDerefs = nda.getNullDereferences();
		for (NullDereferenceResult ndr : nullDerefs) {
			String varName = ndr.checkedSymbol.getName();

			boolean isLeakingToClosure = false;// isLeakingToClosure(ndr.checkedSymbol);
			boolean isInTestFolder = isInTestFolder(ndr.checkedSymbol.getASTLocation());
			if (isInTestFolder && isLeakingToClosure) {
				continue; // ignore these warnings in test related source
			}

			String isOrMaybe = ndr.must && !isLeakingToClosure ? "is" : "may be";
			String nullOrUndefined = "null or undefined";
			nullOrUndefined = ndr.nullOrUndefinedSymbol.isNullLiteral() ? "null" : nullOrUndefined;
			nullOrUndefined = ndr.nullOrUndefinedSymbol.isUndefinedLiteral() ? "undefined" : nullOrUndefined;
			String reason = getReason(ndr);
			String msg = IssueCodes.getMessageForDFG_NULL_DEREFERENCE(varName, isOrMaybe, nullOrUndefined, reason);
			addIssue(msg, ndr.cfe, IssueCodes.DFG_NULL_DEREFERENCE); // deactivated during tests
		}
	}

	private String getReason(NullDereferenceResult ndr) {
		if (!ndr.checkedSymbol.is(ndr.causingSymbol)) {
			return " due to previous variable " + ndr.causingSymbol.getName();
		}
		return "";
	}

	private boolean isLeakingToClosure(Symbol s) {
		EObject decl = s.getDeclaration();
		Iterator<EObject> refs = findReferenceHelper.findReferences(decl).iterator();
		if (!refs.hasNext()) {
			return false;
		}

		EObject ref = refs.next();
		EObject parentScope = getParentScope(ref);
		while (refs.hasNext()) {
			ref = refs.next();
			if (parentScope != getParentScope(ref)) {
				return true;
			}
		}

		return false;
	}

	private EObject getParentScope(EObject eobj) {
		Iterable<EObject> containers = EcoreUtil2.getAllContainers(eobj);
		for (EObject container : containers) {
			boolean isScopeParent = false;
			isScopeParent |= container instanceof FunctionDeclaration;
			isScopeParent |= container instanceof FunctionExpression;
			if (isScopeParent) {
				return container;
			}
		}
		return null;
	}

	private boolean isInTestFolder(EObject eobj) {
		URI location = eobj.eResource().getURI();
		final IN4JSSourceContainer c = n4jsCore.findN4JSSourceContainer(location).orNull();
		return c != null && c.isTest();
	}

}
