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
package org.eclipse.n4js.xpect.validation.suppression

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import org.eclipse.n4js.validation.helper.N4JSLanguageConstants
import java.util.ArrayList
import java.util.Collection
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.validation.CheckMode
import org.eclipse.xtext.validation.Issue
import org.xpect.XpectImport
import org.xpect.XpectReplace
import org.xpect.setup.ISetupInitializer
import org.xpect.setup.XpectSetupFactory
import org.xpect.state.Creates
import org.xpect.text.IRegion
import org.xpect.xtext.lib.setup.ThisResource
import org.xpect.xtext.lib.tests.ValidationTestModuleSetup.IssuesByLine
import org.xpect.xtext.lib.tests.ValidationTestModuleSetup.IssuesByLineProvider
import org.xpect.xtext.lib.tests.ValidationTestModuleSetup.TestingResourceValidator

/**
 * This setup factory filters issues based on their issue code before
 * passing them to xpect methods.
 *
 * For more detail on which issue codes are suppressed see {@link N4JSLanguageConstants#DEFAULT_SUPPRESSED_ISSUE_CODES_FOR_TESTS}.
 *
 * To integrate this setup with an xpect runner, import this class via @XpectImport.
 *
 * For further configuration you can use {@link IssueConfiguration} in the XPECT_SETUP of specific files.
 */
@XpectSetupFactory
@XpectReplace(IssuesByLineProvider)
@XpectImport( #[SuppressIssuesSetupRoot])
class SuppressIssuesSetup extends IssuesByLineProvider {

	private final Collection<String> suppressedIssueCodes = new ArrayList<String>(
		N4JSLanguageConstants.DEFAULT_SUPPRESSED_ISSUE_CODES_FOR_TESTS
	);
	private Multimap<IRegion, Issue> issuesByLine = null;

	/**
	 * Instantiates a new SuppressingValidatorSetup.
	 *
	 * @param resource
	 * 		The XtextResource. Provided by Xpect.
	 * @param setupInitializer
	 * 		The setup initializer for setup specific settings. Provided by Xpect.
	 * @param suppressedIssueCodes
	 * 		The issue codes which should be suppressed while testing.
	 *
	 */
	new(@ThisResource XtextResource resource,
		ISetupInitializer<SuppressIssuesSetupRoot> setupInitializer) {
		super(resource);

		initialize(setupInitializer);
	}

	private def initialize(ISetupInitializer<SuppressIssuesSetupRoot> setupInitializer) {
		val setup = new SuppressIssuesSetupRoot();
		setupInitializer.initialize(setup);

		// If no specific issue configuration was set
		// just skip the initialization
		if (setup.issueConfiguration === null) {
			return;
		}

		// don't suppress explicitly enabled issue codes
		for (code : setup.issueConfiguration.issueCodes) {
			if (code.enabled) {
				suppressedIssueCodes.remove(code.name);
			} else {
				suppressedIssueCodes.add(code.name);
			}
		}
	}

	/*
	 * Override this method to remove suppressed issues from the
	 * list of issues in the resource.
	 */
	override protected includeIssue(Issue issue) {
		return if (suppressedIssueCodes.contains(issue.code)) {
			false
		} else {
			super.includeIssue(issue);
		}
	}

	/*
	 * Override this method to additionally filter the issue by line mapping.
	 * This is required, since many xpect methods obtain their list of issues by a IssuesByLine parameter.
	 */
	@Creates(IssuesByLine)
	override collectIssuesByLine() {
		// see super implementation
		if (issuesByLine === null) {
				val validator = getResource().getResourceServiceProvider().getResourceValidator() as TestingResourceValidator;
				issuesByLine = validator.validateAndMapByOffset(getResource(), CheckMode.ALL, CancelIndicator.NullImpl);
		}

		// if there are issues to suppress
		if (!suppressedIssueCodes.empty) {
			// create a mutable copy
			val filteredResults = HashMultimap.create(issuesByLine);

			// remove all issues with a suppressed issue code
			issuesByLine.entries
				.filter[entry | !includeIssue(entry.value)]
				.forEach[entry | filteredResults.remove(entry.key, entry.value)];

			issuesByLine = filteredResults;
		}

		return issuesByLine;
	}
}
