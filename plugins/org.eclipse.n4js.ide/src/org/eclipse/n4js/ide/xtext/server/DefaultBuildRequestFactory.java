/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.server;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 */
@Singleton
public class DefaultBuildRequestFactory implements IBuildRequestFactory {

	@Inject
	private IssueAcceptor issueAcceptor;

	@Override
	public XBuildRequest getBuildRequest(Set<URI> changedFiles, Set<URI> deletedFiles, List<Delta> externalDeltas) {
		XBuildRequest result = new XBuildRequest();
		result.setDirtyFiles(changedFiles);
		result.setDeletedFiles(deletedFiles);
		result.setExternalDeltas(externalDeltas);
		result.setAfterValidateListener((URI uri, Collection<Issue> issues) -> {
			issueAcceptor.publishDiagnostics(uri, issues);
		});
		return result;
	}

}
