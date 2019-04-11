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
package org.eclipse.n4js.ide.sever;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.build.BuildRequest;
import org.eclipse.xtext.build.BuildRequest.IPostValidationCallback;
import org.eclipse.xtext.ide.server.ProjectManager;
import org.eclipse.xtext.resource.IExternalContentSupport.IExternalContentProvider;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

import com.google.inject.Provider;

/**
 *
 */
public class N4JSProjectManager extends ProjectManager {

	Procedure2<? super URI, ? super Iterable<Issue>> issueAcceptor;

	@Override
	public void initialize(ProjectDescription description, IProjectConfig projectConfig,
			Procedure2<? super URI, ? super Iterable<Issue>> acceptor,
			IExternalContentProvider openedDocumentsContentProvider,
			Provider<Map<String, ResourceDescriptionsData>> indexProvider, CancelIndicator cancelIndicator) {

		super.initialize(description, projectConfig, acceptor, openedDocumentsContentProvider, indexProvider,
				cancelIndicator);
		issueAcceptor = acceptor;
	}

	@Override
	protected BuildRequest newBuildRequest(List<URI> changedFiles, List<URI> deletedFiles,
			List<IResourceDescription.Delta> externalDeltas, CancelIndicator cancelIndicator) {

		BuildRequest br = super.newBuildRequest(changedFiles, deletedFiles, externalDeltas, cancelIndicator);

		IPostValidationCallback newIssueAcceptor = new IPostValidationCallback() {
			@Override
			public boolean afterValidate(URI validated, Iterable<Issue> issues) {
				issueAcceptor.apply(validated, issues);
				if (issues.iterator().hasNext()) {
					URI fileURI = URI.createFileURI(new File(".").getAbsolutePath().toString());
					issueAcceptor.apply(fileURI, issues);
				}
				return false;
			}
		};

		br.setAfterValidate(newIssueAcceptor);

		return br;
	}

}
