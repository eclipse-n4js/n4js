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
package org.eclipse.n4js.packagejson

import java.util.Collection
import java.util.Map
import java.util.SortedMap
import java.util.TreeMap
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.n4js.json.model.utils.JSONModelUtils
import org.eclipse.n4js.projectDescription.ProjectType
import org.eclipse.n4js.projectDescription.SourceContainerType
import org.eclipse.n4js.utils.ProjectDescriptionLoader

import static com.google.common.base.Optional.fromNullable
import static com.google.common.base.Preconditions.checkNotNull
import org.eclipse.n4js.projectModel.names.N4JSProjectName

/**
 * Convenient builder for creating the N4JS package.json compliant {@link JSONDocument} model 
 * instances or file content.#
 * 
 * Provides support for most supported N4JS-specific package.json properties. 
 * See {@link ProjectDescriptionLoader} for details on all supported properties. 
 */
public class PackageJsonBuilder {

	/**
	 * Creates a new builder instance with a default project type of {@link ProjectType#VALIDATION}.
	 */
	public static def PackageJsonBuilder newBuilder() {
		return new PackageJsonBuilder(ProjectType.VALIDATION); // use project type 'validation'
	}

	/**
	 * Creates a new builder instance without a default project type. Use this for creating a
	 * plain package.json without an "n4js" section.
	 */
	public static def PackageJsonBuilder newPlainBuilder() {
		return new PackageJsonBuilder(null);
	}

	private String projectName;
	private ProjectType type;
	private String version;
	private Boolean _private;

	private SortedMap<String, String> dependencies;
	private SortedMap<String, String> devDependencies;

	private String vendorId;
	private String vendorName;

	private String output;

	private Collection<String> providedRLs;
	private Collection<String> requiredRLs;
	private Collection<String> testedProjects;
	private String extendedRE;
	private Collection<String> implementedProjects;
	private String implementationId;

	private Map<SourceContainerType, String> sourceContainers;

	private Collection<String> workspaces;

	private new(ProjectType defaultProjectType) {
		type = defaultProjectType;
		providedRLs = newArrayList
		requiredRLs = newArrayList
		dependencies = new TreeMap();
		devDependencies = new TreeMap();
		implementedProjects = newArrayList
		testedProjects = newArrayList;
		sourceContainers = newHashMap;
		workspaces = newArrayList;
	}

	/**
	 * Builds the N4JS package.json file contents for a project with the given name.
	 *
	 * @return the N4JS package.json file contents as a string.
	 */
	def String build() {
		val document = this.buildModel();
		return JSONModelUtils.serializeJSON(document);
	}

	/**
	 * Builds the N4JS package.json {@code JSONDocument} model representation.
	 * 
	 * @return the N4JS package.json {@code JSONDocument} representation.
	 */
	def JSONDocument buildModel() {
		return PackageJsonContentProvider.getModel(
			fromNullable(projectName),
			fromNullable(version),
			fromNullable(_private),
			workspaces,
			fromNullable(type),
			fromNullable(vendorId),
			fromNullable(vendorName),
			fromNullable(output),
			fromNullable(extendedRE),
			dependencies,
			devDependencies,
			providedRLs,
			requiredRLs,
			fromNullable(implementationId),
			implementedProjects,
			testedProjects,
			sourceContainers);
	}
	
	/**
	 * Sets the project name and returns with the builder.
	 * 
	 * @param type The N4JS project name.
	 * @return the builder.
	 */
	def PackageJsonBuilder withName(String name) {
		this.projectName = checkNotNull(name)
		return this;
	}
	
	/**
	 * Sets the project version and returns with the builder.
	 * 
	 * @param version The project version.
	 * @return the builder.
	 */
	def PackageJsonBuilder withVersion(String version) {
		this.version = checkNotNull(version)
		return this;
	}

	/**
	 * Adds top-level property "private" to the package.json, with the given value.
	 */
	def PackageJsonBuilder withPrivate(boolean _private) {
		this._private = _private;
		return this;
	}

	/**
	 * Sets the project type and returns with the builder.
	 * 
	 * @param type the N4JS project type.
	 * @return the builder.
	 */
	def PackageJsonBuilder withType(ProjectType type) {
		this.type = checkNotNull(type)
		return this;
	}

	/**
	 * Sets the vendorId and returns with the builder.
	 * 
	 * @param type The project's vendor ID.
	 * @return the builder. 
	 */
	def PackageJsonBuilder withVendorId(String vendorId) {
		this.vendorId = vendorId;
		return this;
	}

	/**
	 * Sets the vendor name and returns with the builder.
	 * 
	 * @param type The project's vendor name.
	 * @return the builder. 
	 */
	def PackageJsonBuilder withVendorName(String vendorName) {
		this.vendorName = vendorName;
		return this;
	}

	/**
	 * Sets the output folder and returns with the builder.
	 * 
	 * @param type The project's vendor name.
	 * @return the builder. 
	 */
	def PackageJsonBuilder withOutput(String output) {
		this.output = output;
		return this;
	}

	/**
	 * Adds a source container with the given folder specifier and type.
	 * 
	 * @param type The source container type.
	 * @param path The source container path.  
	 */
	def PackageJsonBuilder withSourceContainer(SourceContainerType type, String path) {
		this.sourceContainers.put(checkNotNull(type), checkNotNull(path));
		return this;
	}

	/**
	 * Sets the extended runtime environment on the builder.
	 * @param extendedRE the extended runtime environment. Optional. Can be {@code null}.
	 * @return the builder.
	 */
	def PackageJsonBuilder withExtendedRE(String extendedRE) {
		this.extendedRE = extendedRE
		return this;
	}

	/**
	 * Adds a new provided runtime library to the current builder.
	 * @param providedRL the provided runtime library to add to the current builder. Cannot be {@code null}.
	 * @return the builder.
	 */
	def PackageJsonBuilder withProvidedRL(String providedRL) {
		providedRLs.add(checkNotNull(providedRL))
		return this;
	}

	/**
	 * Adds a new required runtime library to the current builder.
	 * 
	 * This method will add the required runtime library both to the "requiredRuntimeLibraries" section,
	 * as well as the "dependencies" section.
	 * 
	 * @param requiredRL the required runtime library to add to the current builder. Cannot be {@code null}.
	 * @return the builder.
	 */
	def PackageJsonBuilder withRequiredRL(String requiredRL) {
		requiredRLs.add(checkNotNull(requiredRL))
		// also add to dependencies
		dependencies.put(requiredRL, "*")
		return this;
	}
	
	/**
	 * Adds a new required runtime library with the given version constraint.
	 * 
	 * This method will add the required runtime library both to the "requiredRuntimeLibraries" section,
	 * as well as the "dependencies" section.
	 * 
	 * @param requiredRL The project id of the required runtime library.
	 * @param versionConstraint The version constraint to be used in the dependency section.
	 */
	def PackageJsonBuilder withRequiredRL(String requiredRL, String versionConstraint) {
		requiredRLs.add(checkNotNull(requiredRL))
		// also add to dependencies
		dependencies.put(requiredRL, versionConstraint)
		return this;
	}

	/**
	 * Adds a new project dependency to the current builder.
	 * @param projectDependency the project dependency to add to the current builder. Cannot be {@code null}.
	 * @return the builder.
	 */
	def PackageJsonBuilder withDependency(N4JSProjectName projectDependency) {
		dependencies.put(checkNotNull(projectDependency).rawName, "*")
		return this;
	}
	
	/**
	 * Adds a new project dependency to the current builder.
	 * 
	 * @param projectDependency The project dependency to add to the current builder. Cannot be {@code null}.
	 * @param versionConstraint The version constraint of the added project dependency.
	 * 
	 * @return the builder.
	 */
	def PackageJsonBuilder withDependency(N4JSProjectName projectDependency, String versionConstraint) {
		dependencies.put(checkNotNull(projectDependency).rawName, checkNotNull(versionConstraint))
		return this;
	}

	/**
	 * Adds a new project devDependency to the current builder.
	 * @param projectDevDependency the project devDependency to add to the current builder. Cannot be {@code null}.
	 * @return the builder.
	 */
	def PackageJsonBuilder withDevDependency(N4JSProjectName projectDevDependency) {
		devDependencies.put(checkNotNull(projectDevDependency).rawName, "*")
		return this;
	}
	
	/**
	 * Adds a new project devDependency to the current builder.
	 * 
	 * @param projectDevDependency The project devDependency to add to the current builder. Cannot be {@code null}.
	 * @param versionConstraint The version constraint of the added project devDependency.
	 * 
	 * @return the builder.
	 */
	def PackageJsonBuilder withDevDependency(String projectDevDependency, String versionConstraint) {
		devDependencies.put(checkNotNull(projectDevDependency), checkNotNull(versionConstraint))
		return this;
	}

	/**
	 * Adds the given project id to the list of tested projects.
	 * 
	 * This method also adds the given tested project as project dependency.
	 */
	def PackageJsonBuilder withTestedProject(String testedProject) {
		dependencies.put(checkNotNull(testedProject), "*");
		testedProjects.add(checkNotNull(testedProject));
		return this;
	}
	
	/**
	 * Adds the given project id to the list of tested projects and to the list
	 * of dependencies with the given version constraint.
	 * 
	 * This method also adds the given tested project as project dependency.
	 */
	def PackageJsonBuilder withTestedProject(String testedProject, String version) {
		dependencies.put(checkNotNull(testedProject), "*");
		testedProjects.add(checkNotNull(testedProject));
		return this;
	}

	/** Sets the implementation id to the current builder.
	 * @param implementationId id for the implementations to choose from.
	 * @return the builder.
	 */
	def PackageJsonBuilder withImplementationId(String implementationId) {
		this.implementationId = checkNotNull(implementationId)
		return this;
	}

	/** Adds a project to the list of implemented Projects.
	 * NOTE: also call {@link withImplementationId()}
	 * @param project id of the implemented API
	 * @return the builder.
	 */
	def PackageJsonBuilder withImplementedProject(String implementationAPI) {
		implementedProjects.add(checkNotNull(implementationAPI))
		return this;
	}

	/** Adds top-level property "workspaces" to the package.json, using an array with the given strings as value. */
	def PackageJsonBuilder withWorkspaces(String... workspaces) {
		this.workspaces.addAll(workspaces);
		return this;
	}

	override toString() {
		return '!!! This is just a preview of the N4JS package.json file !!!\n' + this.build();
	}

}
