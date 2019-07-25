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
import org.eclipse.n4js.transpiler.es.EcmaScriptSubGenerator;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.generator.OutputConfigurationProvider;

import com.google.inject.Inject;

/**
 *
 */
public class N4JSOutputConfigurationProvider extends OutputConfigurationProvider {

	@Inject
	private IN4JSCore n4jsCore;

	@Override
	public Set<OutputConfiguration> getOutputConfigurations(ResourceSet context) {
		EList<Resource> resources = context.getResources();
		return getOutputConfigurations(resources.get(0));
	}

	@Override
	public Set<OutputConfiguration> getOutputConfigurations(Resource context) {
		String outputPath = null;
		IN4JSProject project = n4jsCore.findProject(context.getURI()).orNull();
		if (project != null) {
			outputPath = project.getOutputPath();
		}

		OutputConfiguration defaultOutputConfig = EcmaScriptSubGenerator.createDefaultOutputConfiguration();
		if (outputPath != null) {
			defaultOutputConfig.setOutputDirectory(outputPath);
		}
		Set<OutputConfiguration> outputConfs = new HashSet<>();
		outputConfs.add(defaultOutputConfig);
		return outputConfs;
	}
}
