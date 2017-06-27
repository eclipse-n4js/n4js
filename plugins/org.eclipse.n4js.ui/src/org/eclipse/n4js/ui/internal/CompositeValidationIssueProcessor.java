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
package org.eclipse.n4js.ui.internal;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.xtext.ui.editor.validation.IValidationIssueProcessor;
import org.eclipse.xtext.validation.Issue;

import com.google.common.collect.Lists;

/**
 * Composite validation issue processor implementation.
 */
public class CompositeValidationIssueProcessor implements IValidationIssueProcessor {

	private final Iterable<IValidationIssueProcessor> processors;

	/**
	 * Creates a new composite issue processor with the given sub processor arguments.
	 *
	 * @param first
	 *            the first sub processor.
	 * @param others
	 *            the other processor;
	 */
	public CompositeValidationIssueProcessor(final IValidationIssueProcessor first,
			final IValidationIssueProcessor... others) {
		processors = Lists.asList(first, others);
	}

	@Override
	public void processIssues(final List<Issue> issues, final IProgressMonitor monitor) {
		for (final IValidationIssueProcessor processor : processors) {
			if (null != processor) {
				processor.processIssues(issues, monitor);
			}
		}
	}

}
