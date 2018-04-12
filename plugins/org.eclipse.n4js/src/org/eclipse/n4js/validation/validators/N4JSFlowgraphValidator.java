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
import org.eclipse.n4js.flowgraphs.FlowAnalyser;
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyser;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.smith.ClosableMeasurement;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;
import org.eclipse.n4js.typesystem.TypeSystemHelper;
import org.eclipse.n4js.utils.FindReferenceHelper;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
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
	static private final DataCollector dcFlowGraphs = DataCollectors.INSTANCE
			.getOrCreateDataCollector("Flow Graphs");
	static private final DataCollector dcPostprocessing = DataCollectors.INSTANCE
			.getOrCreateDataCollector("PostProcessing", "Flow Graphs");

	@Inject
	private OperationCanceledManager operationCanceledManager;

	@Inject
	private FindReferenceHelper findReferenceHelper;

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private TypeSystemHelper typeSystemHelper;

	@Inject
	private JavaScriptVariantHelper jsVariantHelper;

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
		// Note: The Flow Graph is NOT stored in the meta info cache. Hence, it is created here at use site.
		// In case the its creation is moved to the N4JSPostProcessor, care about an increase in memory consumption.
		N4JSFlowAnalyser flowAnalyzer = new N4JSFlowAnalyser(this::checkCancelled);

		FlowValidator[] fValidators = {
				new DeadCodeValidator(keywordProvider),
				new UsedBeforeDeclaredValidator(),
				new NullUndefinedValidator(n4jsCore, findReferenceHelper),
				new MissingReturnOrThrowValidator(typeSystemHelper, jsVariantHelper)
		};

		FlowAnalyser[] fAnalysers = new FlowAnalyser[fValidators.length];
		for (int i = 0; i < fValidators.length; i++) {
			fAnalysers[i] = fValidators[i].getFlowAnalyser();
		}

		flowAnalyzer.createGraphs(script);
		flowAnalyzer.accept(fAnalysers);

		String uriString = script.eResource().getURI().toString();

		try (ClosableMeasurement m1 = dcFlowGraphs.getClosableMeasurement("flowGraphs_" + uriString);
				ClosableMeasurement m2 = dcPostprocessing.getClosableMeasurement("createGraph_" + uriString);) {

			for (FlowValidator fValidator : fValidators) {
				fValidator.checkResults(this);
			}
		}
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
