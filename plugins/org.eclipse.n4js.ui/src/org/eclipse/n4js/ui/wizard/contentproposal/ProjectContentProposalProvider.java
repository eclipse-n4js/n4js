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
package org.eclipse.n4js.ui.wizard.contentproposal;

import java.util.stream.StreamSupport;

import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.n4js.projectModel.IN4JSCore;

import com.google.inject.Inject;

/**
 * A content proposal provider for workspace projects
 */
public class ProjectContentProposalProvider extends SimpleContentProposalProvider {

	/** Creates a new ProjectcontentProposalProvider */
	@Inject
	public ProjectContentProposalProvider(IN4JSCore n4jsCore) {
		super(StreamSupport.stream(n4jsCore.findAllProjects().spliterator(), false)
				.filter(p -> !p.isExternal())
				.filter(p -> p.exists())
				.map(p -> p.getProjectName().getRawName())
				.toArray(String[]::new));
		this.setFiltering(true);
	}
}
