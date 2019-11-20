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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.postprocessing.ASTFlowInfo;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.n4js.utils.FindReferenceHelper;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.validators.flowgraphs.DeadCodeValidator;
import org.eclipse.n4js.validation.validators.flowgraphs.FlowValidator;
import org.eclipse.n4js.validation.validators.flowgraphs.MissingReturnOrThrowValidator;
import org.eclipse.n4js.validation.validators.flowgraphs.NullUndefinedValidator;
import org.eclipse.n4js.validation.validators.flowgraphs.UsedBeforeDeclaredValidator;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CancelableDiagnostician;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.inject.Inject;

/**
 * This validator validates all control and data flow related issues.
 */
public class N4JSFlowgraphValidator extends AbstractN4JSDeclarativeValidator {

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
	 * Triggers all flow graph related validations
	 */
	@Check
	public void checkFlowGraphs(Script script) {
		N4JSResource resource = (N4JSResource) script.eResource();
		ASTFlowInfo flowInfo = resource.getASTMetaInfoCache().getFlowInfo();
		if (!flowInfo.canPerformBackwardAnalysis()) {
			return;
		}

		flowInfo.performBackwardAnalysis(this::checkCancelled);

		FlowValidator[] fValidators = {
				new DeadCodeValidator(flowInfo.deadCodeAnalyser, keywordProvider),
				new UsedBeforeDeclaredValidator(flowInfo.usedBeforeDeclaredAnalyser),
				new NullUndefinedValidator(flowInfo.nullDereferenceAnalyser, n4jsCore, findReferenceHelper),
				new MissingReturnOrThrowValidator(flowInfo.missingReturnOrThrowAnalyser)
		};

		String uriString = script.eResource().getURI().toString();

		try (Measurement m1 = N4JSDataCollectors.dcFlowGraphs.getMeasurement("flowGraphs_" + uriString);
				Measurement m2 = N4JSDataCollectors.dcFlowGraphPostprocessing
						.getMeasurement("createGraph_" + uriString);) {

			for (FlowValidator fValidator : fValidators) {
				fValidator.checkResults(this);
			}
		}

		flowInfo.reset(); // release memory
	}

	@Override
	public void addIssue(String message, EObject source, String issueCode) {
		super.addIssue(message, source, issueCode);
	}

	@Override
	public void addIssue(String message, EObject source, int offset, int length, String issueCode) {
		super.addIssue(message, source, offset, length, issueCode);
	}

	@Override
	public void addIssue(String message, EObject source, EStructuralFeature feature, String issueCode,
			String... issueData) {
		super.addIssue(message, source, feature, issueCode, issueData);
	}

}
