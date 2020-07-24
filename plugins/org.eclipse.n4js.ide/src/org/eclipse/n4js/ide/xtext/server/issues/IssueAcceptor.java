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

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest.AfterValidateListener;
import org.eclipse.n4js.xtext.server.LSPIssue;

/**
 * Minimal protocol to communicate issues to interested parties.
 *
 * Differs from the {@link AfterValidateListener} such that it only allows {@link LSPIssue}. May become obsolete with
 * more recent Xtext versions.
 */
@SuppressWarnings("deprecation")
public interface IssueAcceptor {
	/**
	 * Call to announce that the list of issues relates to the resource with the given URI. Neither the list nor the uri
	 * may be null.
	 *
	 * Announce an empty list of issues if there are no issues.
	 */
	void accept(URI uri, List<? extends LSPIssue> issues);
}
