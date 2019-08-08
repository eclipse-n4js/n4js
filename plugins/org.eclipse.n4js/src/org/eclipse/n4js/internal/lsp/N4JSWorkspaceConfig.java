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
package org.eclipse.n4js.internal.lsp;

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.lsp.IN4JSProjectConfig;
import org.eclipse.n4js.projectModel.lsp.IN4JSWorkspaceConfig;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;

import com.google.common.collect.FluentIterable;

/**
 * Wrapper around {@link IN4JSCore}.
 */
public class N4JSWorkspaceConfig implements IN4JSWorkspaceConfig {

	private final IN4JSCore delegate;

	/**
	 * Constructor
	 */
	public N4JSWorkspaceConfig(IN4JSCore delegate) {
		this.delegate = delegate;
	}

	@Override
	public IN4JSProjectConfig findProjectByName(String name) {
		return delegate.findProject(new N4JSProjectName(name)).transform(p -> new N4JSProjectConfig(this, p)).orNull();
	}

	@Override
	public IN4JSProjectConfig findProjectContaining(URI member) {
		return delegate.findProject(member).transform(p -> new N4JSProjectConfig(this, p)).orNull();
	}

	@Override
	public Set<? extends IN4JSProjectConfig> getProjects() {
		return FluentIterable.from(delegate.findAllProjects()).transform(p -> new N4JSProjectConfig(this, p)).toSet();
	}

}
