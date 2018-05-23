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
package org.eclipse.n4js.runner.tests

import com.google.inject.Inject
import org.eclipse.n4js.n4mf.ProjectType
import org.eclipse.n4js.runner.^extension.RuntimeEnvironment
import java.util.Collection

import static com.google.common.base.Optional.fromNullable
import static com.google.common.base.Preconditions.checkNotNull
import static org.eclipse.n4js.n4mf.ProjectType.LIBRARY

/**
 * Convenient builder for creating N4JS manifest file content.
 */
class ManifestBuilder {

	@Inject extension ManifestContentProvider

	ProjectType type
	Collection<String> providedRLs
	Collection<String> requiredRLs
	Collection<String> projectDependencies
	RuntimeEnvironment extendedRE
	Collection<String> implementedProjects
	String implementationId

	new() {
		type = LIBRARY
		providedRLs = newArrayList
		requiredRLs = newArrayList
		projectDependencies = newArrayList
		implementedProjects = newArrayList
	}

	/**
	 * Builds the N4JS manifest with the given project name.
	 * @param projectId the name of the project. Cannot be {@code null}.
	 * @return the N4JS manifest content as a string.
	 */
	def build(String projectId) {
		checkNotNull(projectId).getContent(type, fromNullable(extendedRE), projectDependencies, providedRLs, requiredRLs, fromNullable(implementationId), implementedProjects)
	}

	/**
	 * Sets the project type and return with the builder.
	 * @param type the N4JS project type.
	 * @return the builder.
	 */
	def withType(ProjectType type) {
		this.type = checkNotNull(type)
		this
	}

	/**
	 * Sets the extended runtime environment on the builder.
	 * @param extendedRE the extended runtime environment. Optional. Can be {@code null}.
	 * @return the builder.
	 */
	def withExtendedRE(RuntimeEnvironment extendedRE) {
		this.extendedRE = extendedRE
		this
	}

	/**
	 * Adds a new provided runtime library to the current builder.
	 * @param providedRL the provided runtime library to add to the current builder. Cannot be {@code null}.
	 * @return the builder.
	 */
	def withProvidedRL(String providedRL) {
		providedRLs.add(checkNotNull(providedRL))
		this
	}

	/**
	 * Adds a new required runtime library to the current builder.
	 * @param requiredRL the required runtime library to add to the current builder. Cannot be {@code null}.
	 * @return the builder.
	 */
	def withRequiredRL(String providedRL) {
		requiredRLs.add(checkNotNull(providedRL))
		this
	}

	/**
	 * Adds a new project dependency to the current builder.
	 * @param projectDependency the project dependency to add to the current builder. Cannot be {@code null}.
	 * @return the builder.
	 */
	def withProjectDependency(String projectDependency) {
		projectDependencies.add(checkNotNull(projectDependency))
		this
	}

	/** Sets the implementation id to the current builder.
	 * @param implementationId id for the implementations to choose from.
	 * @return the builder.
	 */
	def withImplementationId(String implementationId) {
		this.implementationId = checkNotNull(implementationId)
		this
	}

	/** Adds a project to the list of implemented Projects.
	 * NOTE: also call {@link withImplementationId()}
	 * @param project id of the implemented API
	 * @return the builder.
	 */
	def withImplementedAPI(String implementationAPI) {
		implementedProjects.add(checkNotNull(implementationAPI))
		this
	}



	override toString() {
		build('!!! This is just a preview of the N4JS manifest file !!!')
	}

}
