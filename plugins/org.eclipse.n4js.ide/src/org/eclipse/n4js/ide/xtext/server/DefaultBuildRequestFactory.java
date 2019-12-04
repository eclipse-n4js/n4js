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
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest.AfterDeleteListener;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest.AfterGenerateListener;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest.AfterValidateListener;
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

	@Inject(optional = true) // DefaultAfterValidateListener will be overwritten if defined in a module
	private AfterValidateListener afterValidateListener = new DefaultAfterValidateListener();
	@Inject(optional = true)
	private AfterGenerateListener afterGenerateListener;
	@Inject(optional = true)
	private AfterDeleteListener afterDeleteListener;

	class DefaultAfterValidateListener implements AfterValidateListener {
		@Override
		public void afterValidate(URI source, Collection<Issue> issues) {
			issueAcceptor.publishDiagnostics(source, issues);
		}
	}

	/** Create the build request. */
	public XBuildRequest getBuildRequest() {
		XBuildRequest result = new XBuildRequest();
		result.setAfterDeleteListener(afterDeleteListener);
		result.setAfterValidateListener(afterValidateListener);
		result.setAfterGenerateListener(afterGenerateListener);
		return result;
	}

	@Override
	public XBuildRequest getBuildRequest(Set<URI> changedFiles, Set<URI> deletedFiles, List<Delta> externalDeltas) {
		XBuildRequest result = getBuildRequest();
		result.setDirtyFiles(changedFiles);
		result.setDeletedFiles(deletedFiles);
		result.setExternalDeltas(externalDeltas);
		return result;
	}

	/** @return {@link AfterValidateListener} */
	public AfterValidateListener getAfterValidateListener() {
		return afterValidateListener;
	}

	/** Set {@link #afterValidateListener} */
	public void setAfterValidateListener(AfterValidateListener afterValidateListener) {
		this.afterValidateListener = afterValidateListener;
	}

	/** @return {@link AfterGenerateListener} */
	public AfterGenerateListener getAfterGenerateListener() {
		return afterGenerateListener;
	}

	/** Set {@link #afterGenerateListener} */
	public void setAfterGenerateListener(AfterGenerateListener afterGenerateListener) {
		this.afterGenerateListener = afterGenerateListener;
	}

	/** @return {@link AfterDeleteListener} */
	public AfterDeleteListener getAfterDeleteListener() {
		return afterDeleteListener;
	}

	/** Set {@link #afterDeleteListener} */
	public void setAfterDeleteListener(AfterDeleteListener afterDeleteListener) {
		this.afterDeleteListener = afterDeleteListener;
	}

}
