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
package org.eclipse.n4js.external;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Sets.newHashSet;

import java.io.File;
import java.util.Collection;
import java.util.Set;

import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.n4js.internal.locations.FileURI;
import org.eclipse.n4js.projectModel.IN4JSProject;

import com.google.common.collect.ImmutableList;

/**
 * Representation of an external N4JS project.
 */
public class N4JSExternalProject extends ExternalProject {
	/** Nature ID of N4JS external projects */
	public static final String NATURE_ID = "org.eclipse.xtext.ui.shared.xtextNature";

	/** Builder ID of N4JS external projects */
	public static final String BUILDER_ID = "org.eclipse.xtext.ui.shared.xtextBuilder";

	private final IN4JSProject externalPackage;
	private final Collection<IBuildConfiguration> referencedBuildConfigs;

	/** Constructor */
	public N4JSExternalProject(final File file, final IN4JSProject externalPackage) {
		super(file, NATURE_ID, BUILDER_ID);
		this.externalPackage = externalPackage;
		referencedBuildConfigs = newHashSet();
	}

	@Override
	public IBuildConfiguration[] internalGetReferencedBuildConfigs(String configName, boolean includeMissing) {
		final IBuildConfiguration[] filteredConfigs = from(referencedBuildConfigs)
				.filter(config -> includeMissing ? true : config.getProject().exists())
				.toArray(IBuildConfiguration.class);
		return filteredConfigs;
	}

	/**
	 * Attach the argument as a referenced build configuration to the project.
	 *
	 * @param config
	 *            the configuration to attach.
	 * @return {@link Set#add(Object)}.
	 */
	boolean add(final IBuildConfiguration config) {
		return referencedBuildConfigs.add(config);
	}

	/** @return the underlying instance of {@link IN4JSProject} */
	public IN4JSProject getIProject() {
		return externalPackage;
	}

	public FileURI getSafeLocation() {
		return (FileURI) externalPackage.getSafeLocation();
	}

	/**
	 * Returns with all direct dependency project IDs of the project extracted from the wrapped {@link IN4JSProject
	 * external package}.
	 *
	 * @return an iterable of direct dependency project IDs.
	 */
	public ImmutableList<? extends IN4JSProject> getAllDirectDependencies() {
		return getIProject().getAllDirectDependencies();
	}

	/**
	 * Returns with all direct dependency project IDs of the project extracted from the wrapped {@link IN4JSProject
	 * external package}.
	 *
	 * @return an iterable of direct dependency project IDs.
	 */
	public Iterable<String> getAllDirectDependencyIds() {
		return from(getAllDirectDependencies())
				.transform(p -> p.getProjectName())
				.toSet();
	}

}
