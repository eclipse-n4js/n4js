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
package org.eclipse.n4js.analysis;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.diagnostics.ExceptionDiagnostic;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;

import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Used in smoke tests to check that no exception have been thrown. Issued errors and warning are ignored.
 */
public class ExceptionAnalyser extends PositiveAnalyser {
	@Inject
	private N4JSTypeSystem typeSystem;

	@Inject
	private IResourceValidator validator;

	/***/
	public ExceptionAnalyser() {
		super(Logger.getLogger(ExceptionAnalyser.class), null);
	}

	@Override
	protected List<Diagnostic> getScriptErrors(Script script) {
		EcoreUtil.resolveAll(script.eResource());
		List<Diagnostic> diagnostics = super.getScriptErrors(script);
		Iterator<TypableElement> typableASTNodes = Iterators.filter(EcoreUtil2.eAll(script), TypableElement.class);
		List<Diagnostic> result = Lists.<Diagnostic> newArrayList(Iterables.filter(diagnostics,
				ExceptionDiagnostic.class));
		while (typableASTNodes.hasNext()) {
			TypableElement typableASTNode = typableASTNodes.next();
			RuleEnvironment ruleEnvironment = RuleEnvironmentExtensions.newRuleEnvironment(typableASTNode);
			try {
				typeSystem.type(ruleEnvironment, typableASTNode);
			} catch (Throwable cause) {
				if (cause instanceof Exception) {
					result.add(new ExceptionDiagnostic((Exception) cause));
				} else {
					throw new RuntimeException(cause);
				}
			}
		}
		validator.validate(script.eResource(), CheckMode.ALL, CancelIndicator.NullImpl);
		return result;
	}
}
