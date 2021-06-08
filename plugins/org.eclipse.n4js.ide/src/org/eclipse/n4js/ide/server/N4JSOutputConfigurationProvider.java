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
package org.eclipse.n4js.ide.server;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.transpiler.dts.DtsSubGenerator;
import org.eclipse.n4js.transpiler.es.EcmaScriptSubGenerator;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.generator.OutputConfigurationProvider;
import org.eclipse.xtext.resource.impl.ProjectDescription;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 */
@Singleton
public class N4JSOutputConfigurationProvider extends OutputConfigurationProvider {

	@Inject
	private WorkspaceAccess workspaceAccess;

	@Override
	public Set<OutputConfiguration> getOutputConfigurations() {
		return getOutputConfigurationSet(null); // returns a default configuration
	}

	@Override
	public Set<OutputConfiguration> getOutputConfigurations(ResourceSet context) {
		EList<Resource> resources = context.getResources();
		if (resources.isEmpty()) {
			ProjectDescription description = ProjectDescription.findInEmfObject(context);
			N4JSProjectConfigSnapshot project = workspaceAccess.findProjectByName(context, description.getName());
			return getOutputConfigurationSet(project); // returns a default configuration if 'project' is still 'null'
		}
		return getOutputConfigurations(resources.get(0));
	}

	@Override
	public Set<OutputConfiguration> getOutputConfigurations(Resource context) {
		N4JSProjectConfigSnapshot project = workspaceAccess.findProjectContaining(context);
		return getOutputConfigurationSet(project);
	}

	private Set<OutputConfiguration> getOutputConfigurationSet(N4JSProjectConfigSnapshot project) {
		Set<OutputConfiguration> outputConfs = new HashSet<>();

		OutputConfiguration outputConf = getESOutputConfigurations(project);
		if (outputConf != null) {
			outputConfs.add(outputConf);
		}
		outputConf = getDTSOutputConfigurations(project);
		if (outputConf != null) {
			outputConfs.add(outputConf);
		}
		return outputConfs;
	}

	private OutputConfiguration getESOutputConfigurations(N4JSProjectConfigSnapshot project) {
		if (project != null
				&& N4JSGlobals.PROJECT_TYPES_WITHOUT_GENERATION.contains(project.getType())) {
			return null;
		}

		String outputPath = null;
		if (project != null) {
			outputPath = project.getOutputPath();
		}

		OutputConfiguration defaultESOutputConfig = EcmaScriptSubGenerator.createDefaultOutputConfiguration();
		if (outputPath != null) {
			defaultESOutputConfig.setOutputDirectory(outputPath);
		}
		return defaultESOutputConfig;
	}

	private OutputConfiguration getDTSOutputConfigurations(N4JSProjectConfigSnapshot project) {
		if (project != null
				&& N4JSGlobals.PROJECT_TYPES_WITHOUT_GENERATION.contains(project.getType())) {
			return null;
		}

		String outputPath = null;
		if (project != null) {
			outputPath = project.getOutputPath();
		}

		OutputConfiguration defaultESOutputConfig = DtsSubGenerator.createDefaultOutputConfiguration();
		if (outputPath != null) {
			defaultESOutputConfig.setOutputDirectory(outputPath);
		}
		return defaultESOutputConfig;
	}
}
