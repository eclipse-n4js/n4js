/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Parts originally copied from .validation.ResourceValidatorImpl
 *	in bundle org.eclipse.xtext
 *	available under the terms of the Eclipse Public License 2.0
 *  Copyright (c) 2008 itemis AG (http://www.itemis.eu) and others.
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.semver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.validation.AbstractInjectableValidator;
import org.eclipse.xtext.validation.CancelableDiagnostician;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.validation.ResourceValidatorImpl;
import org.eclipse.xtext.validation.impl.ConcreteSyntaxEValidator;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

/** Customized validator class to validate Semver objects */
public class SemverResourceValidator extends ResourceValidatorImpl {
	@Inject
	private Diagnostician diagnostician;
	@Inject
	private OperationCanceledManager operationCanceledManager;

	/**
	 * Validates Semver {@link EObject}s
	 *
	 * @param root
	 *            A Semver {@link EObject}, usually {@link NPMVersionRequirement}
	 */
	public List<Issue> validate(EObject root, CheckMode mode, CancelIndicator monitor) {
		List<Issue> result = new ArrayList<>();
		IAcceptor<Issue> acceptor = createAcceptor(result);
		operationCanceledManager.checkCanceled(monitor);
		validate(null, root, mode, monitor, acceptor);

		return result;
	}

	@Override
	@Deprecated
	public List<Issue> validate(Resource resource, CheckMode mode, CancelIndicator indicator) {
		throw new IllegalStateException("Not supported");
	}

	/** Copied from parent class to change language to {@code SemverGlobals.LANGUAGE_NAME} */
	@Override
	protected void validate(Resource resource, EObject eObject, CheckMode mode, CancelIndicator monitor,
			IAcceptor<Issue> acceptor) {
		try {
			Map<Object, Object> options = Maps.newHashMap();
			options.put(CheckMode.KEY, mode);
			options.put(CancelableDiagnostician.CANCEL_INDICATOR, monitor);
			// disable concrete syntax validation, since a semantic model that has been parsed
			// from the concrete syntax always complies with it - otherwise there are parse errors.
			options.put(ConcreteSyntaxEValidator.DISABLE_CONCRETE_SYNTAX_EVALIDATOR, Boolean.TRUE);
			// see EObjectValidator.getRootEValidator(Map<Object, Object>)
			options.put(EValidator.class, diagnostician);
			options.put(AbstractInjectableValidator.CURRENT_LANGUAGE_NAME, SemverGlobals.LANGUAGE_NAME);

			Diagnostic diagnostic = diagnostician.validate(eObject, options);
			if (!diagnostic.getChildren().isEmpty()) {
				for (Diagnostic childDiagnostic : diagnostic.getChildren()) {
					issueFromEValidatorDiagnostic(childDiagnostic, acceptor);
				}
			} else {
				issueFromEValidatorDiagnostic(diagnostic, acceptor);
			}
		} catch (RuntimeException e) {
			operationCanceledManager.propagateAsErrorIfCancelException(e);
		}
	}

}
