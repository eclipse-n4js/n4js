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
package org.eclipse.n4js.xpect.validation.suppression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.XpectReplace;
import org.eclipse.xpect.setup.ISetupInitializer;
import org.eclipse.xpect.setup.XpectSetupFactory;
import org.eclipse.xpect.state.Creates;
import org.eclipse.xpect.text.IRegion;
import org.eclipse.xpect.xtext.lib.setup.ThisResource;
import org.eclipse.xpect.xtext.lib.tests.ValidationTestModuleSetup.IssuesByLine;
import org.eclipse.xpect.xtext.lib.tests.ValidationTestModuleSetup.IssuesByLineProvider;
import org.eclipse.xpect.xtext.lib.tests.ValidationTestModuleSetup.TestingResourceValidator;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.Issue;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * This setup factory filters issues based on their issue code before passing them to xpect methods.
 *
 * For more detail on which issue codes are suppressed see
 * {@link N4JSLanguageConstants#DEFAULT_SUPPRESSED_ISSUE_CODES_FOR_TESTS}.
 *
 * To integrate this setup with an xpect runner, import this class via @XpectImport.
 *
 * For further configuration you can use {@link IssueConfiguration} in the XPECTSETUP of specific files.
 *
 * <p>
 * When sub-classing this setup class, the two following points must be fulfilled:
 *
 * <ul>
 * <li>The subclass must be decorated with the same set of <code>@Xpect*</code> annotations as this class.</li>
 * <li>The subclass must implement a constructor with the same <code>@ThisResource</code> annotation on the
 * <code>resource</code> parameters as in this class.</li>
 * </ul>
 * </p>
 */
@XpectSetupFactory
@XpectReplace(IssuesByLineProvider.class)
@XpectImport({ SuppressIssuesSetupRoot.class })
public abstract class AbstractSuppressIssuesSetup extends IssuesByLineProvider {

	private final Collection<String> suppressedIssueCodes = new ArrayList<>(
			this.getDefaultSuppressedIssueCodes());
	private Multimap<IRegion, Issue> issuesByLine = null;

	/**
	 * Instantiates a new SuppressingValidatorSetup.
	 *
	 * @param resource
	 *            The XtextResource. Provided by Xpect.
	 * @param setupInitializer
	 *            The setup initializer for setup specific settings. Provided by Xpect.
	 */
	protected AbstractSuppressIssuesSetup(@ThisResource XtextResource resource,
			ISetupInitializer<SuppressIssuesSetupRoot> setupInitializer) {

		super(resource);
		initialize(setupInitializer);
	}

	private void initialize(ISetupInitializer<SuppressIssuesSetupRoot> setupInitializer) {
		SuppressIssuesSetupRoot setup = new SuppressIssuesSetupRoot();
		setupInitializer.initialize(setup);

		// If no specific issue configuration was set
		// just skip the initialization
		if (setup.getIssueConfiguration() == null) {
			return;
		}

		// don't suppress explicitly enabled issue codes
		for (IssueCode code : setup.getIssueConfiguration().getIssueCodes()) {
			if (code.isEnabled()) {
				suppressedIssueCodes.remove(code.getName());
			} else {
				suppressedIssueCodes.add(code.getName());
			}
		}
	}

	/**
	 * Returns the list of issue codes that are suppressed by default when using this suppress issues setup.
	 */
	abstract protected Collection<String> getDefaultSuppressedIssueCodes();

	/**
	 * Override this method to remove suppressed issues from the list of issues in the resource.
	 */
	@Override
	protected boolean includeIssue(Issue issue) {
		return suppressedIssueCodes.contains(issue.getCode())
				? false
				: super.includeIssue(issue);
	}

	/**
	 * Override this method to additionally filter the issue by line mapping. This is required, since many xpect methods
	 * obtain their list of issues by an IssuesByLine parameter.
	 */
	@Override
	@Creates(IssuesByLine.class)
	public Multimap<IRegion, Issue> collectIssuesByLine() {
		// see super implementation
		if (issuesByLine == null) {
			TestingResourceValidator validator = (TestingResourceValidator) getResource().getResourceServiceProvider()
					.getResourceValidator();
			issuesByLine = validator.validateDelegateAndMapByOffset(getResource(), CheckMode.ALL,
					CancelIndicator.NullImpl, null);
		}

		// if there are issues to suppress
		if (!suppressedIssueCodes.isEmpty()) {
			// create a mutable copy
			HashMultimap<IRegion, Issue> filteredResults = HashMultimap.create(issuesByLine);

			// remove all issues with a suppressed issue code
			for (Map.Entry<IRegion, Issue> entry : issuesByLine.entries()) {
				if (!includeIssue(entry.getValue())) {
					filteredResults.remove(entry.getKey(), entry.getValue());
				}
			}

			issuesByLine = filteredResults;
		}

		return issuesByLine;
	}
}
