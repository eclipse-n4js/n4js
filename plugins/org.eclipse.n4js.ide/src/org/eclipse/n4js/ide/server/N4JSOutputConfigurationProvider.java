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
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.transpiler.es.EcmaScriptSubGenerator;
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
	private IN4JSCore n4jsCore;

	@Override
	public Set<OutputConfiguration> getOutputConfigurations() {
		Set<OutputConfiguration> outputConfs = new HashSet<>();

		for (IN4JSProject prj : n4jsCore.findAllProjects()) {
			OutputConfiguration outputConfiguration = getOutputConfiguration(prj);
			if (outputConfiguration != null) {
				outputConfs.add(outputConfiguration);
			}
		}

		return outputConfs;
	}

	@Override
	public Set<OutputConfiguration> getOutputConfigurations(ResourceSet context) {
		EList<Resource> resources = context.getResources();
		if (resources.isEmpty()) {
			ProjectDescription description = ProjectDescription.findInEmfObject(context);
			IN4JSProject project = n4jsCore.findProject(new N4JSProjectName(description.getName())).orNull();
			return getOutputConfigurationSet(project);
		}
		return getOutputConfigurations(resources.get(0));
	}

	@Override
	public Set<OutputConfiguration> getOutputConfigurations(Resource context) {
		IN4JSProject project = n4jsCore.findProject(context.getURI()).orNull();
		return getOutputConfigurationSet(project);
	}

	private Set<OutputConfiguration> getOutputConfigurationSet(IN4JSProject project) {
		Set<OutputConfiguration> outputConfs = new HashSet<>();
		OutputConfiguration outputConf = getOutputConfiguration(project);
		if (outputConf != null) {
			outputConfs.add(outputConf);
		}
		return outputConfs;
	}

	private OutputConfiguration getOutputConfiguration(IN4JSProject project) {
		// if (project.)

		String outputPath = null;
		if (project != null) {
			outputPath = project.getOutputPath();
		}

		OutputConfiguration defaultOutputConfig = EcmaScriptSubGenerator.createDefaultOutputConfiguration();
		if (outputPath != null) {
			defaultOutputConfig.setOutputDirectory(outputPath);
		}
		return defaultOutputConfig;
	}
}
