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
package org.eclipse.n4js.utils.ui.quickfix;

import static com.google.common.collect.FluentIterable.from;

import java.util.List;

import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolution;
import org.eclipse.xtext.validation.Issue;

import com.google.common.collect.Iterables;

/**
 * Quickfix provider implementation that delegates into any registered {@code quickfixProviderSupplier} extension point
 * contributions if cannot find proper resolution in the current implementation.
 *
 * This is a kind of workaround solution to provide a proper quick fix resolution between Xtext languages.
 */
public abstract class DelegatingQuickfixProvider extends DefaultQuickfixProvider {

	@Override
	public boolean hasResolutionFor(final String issueCode) {
		if (super.hasResolutionFor(issueCode)) {
			return true;
		}
		return Iterables.any(getDelegates(), p -> p.hasResolutionFor(issueCode));
	}

	@Override
	public List<IssueResolution> getResolutions(final Issue issue) {
		final List<IssueResolution> resolutions = super.getResolutions(issue);
		resolutions.addAll(from(getDelegates()).transformAndConcat(p -> p.getResolutions(issue)).toList());
		return resolutions;
	}

	/**
	 * @return the delegates to be used to compute the quickfixes
	 */
	protected abstract Iterable<? extends DefaultQuickfixProvider> getDelegates();

}
