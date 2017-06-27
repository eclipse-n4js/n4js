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
package org.eclipse.n4js.ui.building.instructions;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.ui.generator.IDerivedResourceMarkers;

@SuppressWarnings("restriction")
abstract class AbstractBuildParticipantInstruction extends AdapterImpl implements IBuildParticipantInstruction {

	private boolean rebuild = false;
	protected IDerivedResourceMarkers derivedResourceMarkers;
	protected IProject project;
	protected Map<String, OutputConfiguration> outputConfigurations;

	public AbstractBuildParticipantInstruction(IProject project, Map<String, OutputConfiguration> outputConfigurations,
			IDerivedResourceMarkers derivedResourceMarkers) {
		this.project = project;
		this.outputConfigurations = outputConfigurations;
		this.derivedResourceMarkers = derivedResourceMarkers;
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return IBuildParticipantInstruction.class.equals(type);
	}

	@Override
	public boolean isRebuild() {
		return rebuild;
	}

	protected void needRebuild() {
		rebuild = true;
	}
}
