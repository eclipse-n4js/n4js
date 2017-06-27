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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IFileSystemAccess;

import com.google.inject.Inject;
import com.google.inject.Injector;

import org.eclipse.n4js.external.ExternalLibraryUriHelper;
import org.eclipse.n4js.generator.common.CompilerDescriptor;
import org.eclipse.n4js.generator.common.IComposedGenerator;
import org.eclipse.n4js.generator.common.ISubGenerator;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;

/**
 */
public class CompositeGenerator implements IComposedGenerator {

	private static final Logger LOGGER = Logger.getLogger(CompositeGenerator.class);

	@Inject
	private Injector injector;

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private ExternalLibraryUriHelper externalLibraryUriHelper;

	@Override
	public void doGenerate(Resource input, IFileSystemAccess fsa) {
		// useMultiBinding(input, fsa);

		if (externalLibraryUriHelper.isExternalLocation(input.getURI())) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.info("Skipped generation for external resource: " + input.getURI());
			}
			return;
		}

		com.google.common.base.Optional<? extends IN4JSSourceContainer> n4jsContainer = n4jsCore
				.findN4JSSourceContainer(input.getURI());
		if (n4jsContainer.isPresent()) {
			useTranspilerType(input, fsa);
		}
	}

	/**
	 * fails with Caused by: java.lang.ClassNotFoundException: com.google.inject.internal.util.$ImmutableList cannot be
	 * found by org.eclipse.n4js.generator_0.0.1.qualifier
	 */
	// private void useMultiBinding(Resource input, IFileSystemAccess fsa) {
	// if (childInjector == null) {
	// childInjector = injector.createChildInjector(new CompositeGeneratorModule());
	// }
	// Set<ISubGenerator> subGenerators = childInjector.getInstance(Key.get(new TypeLiteral<Set<ISubGenerator>>() {
	// // nothing to do
	// }));
	// for (ISubGenerator subGenerator : subGenerators) {
	// subGenerator.doGenerate(input, fsa);
	// }
	// }

	/**
	 * For each available sub generator, let it do its work after having its dependencies injected.
	 */
	private void useTranspilerType(Resource input, IFileSystemAccess fsa) {
		subGeneratorInstances().forEachOrdered(subGenerator -> {
			injector.injectMembers(subGenerator);
			subGenerator.doGenerate(input, fsa);
		});
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.n4js.generator.IComposedGenerator#getCompilerDescriptors()
	 */
	@Override
	public Set<CompilerDescriptor> getCompilerDescriptors() {
		Set<CompilerDescriptor> descriptors = new HashSet<>();
		subGeneratorInstances().forEachOrdered(subGenerator -> {
			descriptors.add(subGenerator.getCompilerDescriptor());
		});
		return descriptors;
	}

	/**
	 * @return the classes of the available {@link SubGeneratorType sub generators}
	 */
	@SuppressWarnings("unchecked")
	private Stream<Class<ISubGenerator>> subGeneratorClasses() {
		Stream<?> result = Arrays.stream(SubGeneratorType.values())
				.map(subGenerator -> subGenerator.getSubGeneratorClass())
				.filter(Objects::nonNull);
		return (Stream<Class<ISubGenerator>>) result;

	}

	/**
	 * Attempts to instantiate an {@link ISubGenerator} from their class; returning it in a single-element stream if
	 * successful; returning an empty stream otherwise.
	 */
	private Stream<ISubGenerator> subGeneratorInstance(Class<ISubGenerator> clazz) {
		try {
			return Stream.of(clazz.newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return Stream.empty();
	}

	/**
	 * @return the available (non-null) {@link SubGeneratorType sub generators}
	 */
	private Stream<ISubGenerator> subGeneratorInstances() {
		return subGeneratorClasses().flatMap(clazz -> subGeneratorInstance(clazz));
	}
}
