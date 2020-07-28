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
import org.eclipse.n4js.ide.xtext.server.ResourceTaskManager;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest.AfterValidateListener;
import org.eclipse.n4js.xtext.server.LSPIssue;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Implementation of an AfterValidateListener that will forward issues to a a delegate if the originating resource is
 * not currently open in an editor.
 */
@Singleton
@SuppressWarnings("deprecation")
public class WorkspaceValidateListener implements AfterValidateListener {

	private final IssueAcceptor acceptor;

	/**
	 * Constructor.
	 */
	@Inject
	public WorkspaceValidateListener(PublishingIssueAcceptor publisher,
			ResourceTaskManager resourceTaskManager) {
		this.acceptor = new FilteringIssueAcceptor(publisher, uri -> !resourceTaskManager.isOpen(uri));
	}

	@Override
	public void afterValidate(URI source, List<? extends Issue> issues) {
		acceptor.accept(source, LSPIssue.cast(issues));
	}

}
