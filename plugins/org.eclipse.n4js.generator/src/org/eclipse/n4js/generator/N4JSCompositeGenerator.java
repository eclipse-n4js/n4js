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
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.external.ExternalLibraryUriHelper;
import org.eclipse.n4js.generator.common.CompilerDescriptor;
import org.eclipse.n4js.generator.common.ICompositeGenerator;
import org.eclipse.n4js.generator.common.ISubGenerator;
import org.eclipse.n4js.generator.common.SubGeneratorRegistry;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.xtext.generator.IFileSystemAccess;

import com.google.inject.Inject;

/**
 * A composite generator is responsible for a language. A composite generator delegates the generation logics to its
 * children (subgenerators).
 */
public class N4JSCompositeGenerator implements ICompositeGenerator {

	private static final Logger LOGGER = Logger.getLogger(N4JSCompositeGenerator.class);

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private ExternalLibraryUriHelper externalLibraryUriHelper;

	@Inject
	private SubGeneratorRegistry subGeneratorRegistry;

	@Override
	public void doGenerate(Resource input, IFileSystemAccess fsa) {
		if (isApplicableTo(input)) {
			// Delegate to subgenerators only when the composite generator is applicable to the resource
			for (ISubGenerator subgenerator : getSubGenerators()) {
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
		if (N4JSGlobals.N4JS_FILE_EXTENSION.equals(input.getURI().fileExtension())
				|| N4JSGlobals.JS_FILE_EXTENSION.equals(input.getURI().fileExtension())) {
			// Only applicable to n4js or js file extension
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
		return false;
	}

	@Override
	public Collection<ISubGenerator> getSubGenerators() {
		// Ask the global generator registry and filter generators that are applicable to N4JS language.
		return subGeneratorRegistry.getGenerators(N4JSGlobals.N4JS_FILE_EXTENSION);
	}
}
