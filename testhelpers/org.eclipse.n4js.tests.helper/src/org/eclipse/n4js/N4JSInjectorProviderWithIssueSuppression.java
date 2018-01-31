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
package org.eclipse.n4js;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.validation.IDiagnosticConverter;
import org.eclipse.xtext.validation.Issue;

/**
 * An injector provider which binds a custom {@link IDiagnosticConverter} which filters the diagnosed issues.
 *
 * This can be used to suppress certain issues based on their issue codes. See
 * {@code N4JSLanguageConstants.DEFAULT_SUPPRESSED_ISSUE_CODES_FOR_TESTING} for configuration.
 *
 */
public class N4JSInjectorProviderWithIssueSuppression extends N4JSInjectorProvider {
	/** **/
	public N4JSInjectorProviderWithIssueSuppression() {
		super(new IssueSuppressionModule());
	}

	/** */
	public static class IssueSuppressionModule extends N4JSStandaloneTestsModule {

		/** */
		@Override
		public Class<? extends IDiagnosticConverter> bindDiagnosticConverter() {
			return FilteringDiagnosticConverter.class;
		}
	}

	/**
	 * A diagnostic converter which filters incoming diagnostics before passing them to the super implementations.
	 */
	public static class FilteringDiagnosticConverter extends ExceptionAwareDiagnosticConverter {
		@Override
		public void convertValidatorDiagnostic(Diagnostic diagnostic, IAcceptor<Issue> acceptor) {
			super.convertValidatorDiagnostic(diagnostic, new IAcceptor<Issue>() {
				@Override
				public void accept(Issue t) {
					if (!N4JSLanguageConstants.DEFAULT_SUPPRESSED_ISSUE_CODES_FOR_TESTS.contains(t.getCode())) {
						acceptor.accept(t);
					}
				}
			});
		}
	}
}
