/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.server.issues;

import java.util.List;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.xtext.server.LSPIssue;

/**
 * An issue acceptor that will only forward issues for matching URIs to its delegate.
 */
@SuppressWarnings("deprecation")
public class FilteringIssueAcceptor implements IssueAcceptor {

	private final IssueAcceptor delegate;
	private final Predicate<? super URI> filter;

	FilteringIssueAcceptor(IssueAcceptor delegate, Predicate<? super URI> filter) {
		this.delegate = delegate;
		this.filter = filter;
	}

	@Override
	public void accept(URI uri, List<? extends LSPIssue> issues) {
		if (filter.test(uri)) {
			delegate.accept(uri, issues);
		}
	}

}
