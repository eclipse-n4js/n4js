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
package org.eclipse.n4js.n4jsx.generator;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.external.ExternalLibraryUriHelper;
import org.eclipse.n4js.generator.N4JSCompositeGenerator;
import org.eclipse.n4js.generator.common.ISubGenerator;
import org.eclipse.n4js.generator.common.SubGeneratorRegistry;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;

import com.google.inject.Inject;

/**
 * N4JSX composite generator.
 */
public class N4JSXCompositeGenerator extends N4JSCompositeGenerator {

	private static final Logger LOGGER = Logger.getLogger(N4JSXCompositeGenerator.class);

	@Inject
	private ExternalLibraryUriHelper externalLibraryUriHelper;

	@Inject
	private IN4JSCore n4jsCore;
	// Cannot use @Inject because this instance is a global singleton
	// TODO: FIXME after GH-368 is merged.
	private SubGeneratorRegistry subGeneratorRegistry;

	/** Set subgenerator registry. */
	public void setSubGeneratorRegistry(SubGeneratorRegistry subGeneratorRegistry) {
		this.subGeneratorRegistry = subGeneratorRegistry;
	}

	@Override
	public boolean isApplicableTo(Resource input) {
		if (!N4JSGlobals.N4JSX_FILE_EXTENSION.equals(input.getURI().fileExtension())) {
			// Only applicable to n4jsx file extension
			return false;
		}

		// Skip external resource
		if (externalLibraryUriHelper.isExternalLocation(input.getURI())) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.info("Skipped generation for external resource: " + input.getURI());
			}
			return false;
		}
		// This composite generator is applicable to the input resource if the resource is in a N4JS source container
		com.google.common.base.Optional<? extends IN4JSSourceContainer> n4jsContainer = n4jsCore
				.findN4JSSourceContainer(input.getURI());
		return (n4jsContainer.isPresent());
	}

	@Override
	public Collection<ISubGenerator> getSubGenerators() {
		// Ask the global generator registry and filter generators that are applicable to N4JSX language.
		return subGeneratorRegistry.getGenerators(N4JSGlobals.N4JSX_FILE_EXTENSION);
	}
}
