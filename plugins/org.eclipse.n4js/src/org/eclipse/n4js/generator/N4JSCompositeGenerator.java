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
package org.eclipse.n4js.generator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.external.ExternalLibraryUriHelper;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.resource.XpectAwareFileExtensionCalculator;
import org.eclipse.xtext.generator.IFileSystemAccess;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * This composite generator manages all subgenerators. It delegates the generation logics to its children
 * (subgenerators).
 */
@Singleton
public class N4JSCompositeGenerator implements ICompositeGenerator {

	private static final Logger LOGGER = Logger.getLogger(N4JSCompositeGenerator.class);

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private ExternalLibraryUriHelper externalLibraryUriHelper;

	@Inject
	private SubGeneratorRegistry subGeneratorRegistry;

	@Inject
	private XpectAwareFileExtensionCalculator xpectAwareFileExtensionCalculator;

	@Override
	public void doGenerate(Resource input, IFileSystemAccess fsa) {
		if (isApplicableTo(input)) {
			String fileExtension = xpectAwareFileExtensionCalculator.getXpectAwareFileExtension(input.getURI());
			for (ISubGenerator subgenerator : getSubGenerators(fileExtension)) {
				subgenerator.doGenerate(input, fsa);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.n4js.generator.IComposedGenerator#getCompilerDescriptors()
	 */
	@Override
	public Set<CompilerDescriptor> getCompilerDescriptors() {
		Set<CompilerDescriptor> descriptors = new HashSet<>();
		getSubGenerators().stream().forEachOrdered(subGenerator -> {
			descriptors.add(subGenerator.getCompilerDescriptor());
		});
		return descriptors;
	}

	@Override
	public boolean isApplicableTo(Resource input) {
		// Skip external resource
		if (externalLibraryUriHelper.isExternalLocation(input.getURI())) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.info("Skipped generation for external resource: " + input.getURI());
			}
			return false;
		}
		// This composite generator is applicable to the input resource if the resource is in a N4JS source
		// container
		com.google.common.base.Optional<? extends IN4JSSourceContainer> n4jsContainer = n4jsCore
				.findN4JSSourceContainer(input.getURI());
		return (n4jsContainer.isPresent());
	}

	@Override
	public Collection<ISubGenerator> getSubGenerators() {
		// Ask the global generator registry to retrieve all registered generators
		return subGeneratorRegistry.getGenerators();
	}

	@Override
	public Collection<ISubGenerator> getSubGenerators(String fileExtension) {
		// Ask the global generator registry to retrieve all registered generators
		return subGeneratorRegistry.getGenerators(fileExtension);
	}
}
