/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.packagejson.projectDescription;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.n4js.semver.Semver.VersionNumber;

import com.google.common.collect.Iterables;

/**
 * Builder for {@link ProjectDescription}s.
 */
@SuppressWarnings("javadoc")
public class ProjectDescriptionBuilder {

	private String name;
	private String vendorId;
	private String vendorName;
	private VersionNumber version;
	private ProjectType type;
	private String mainModule;
	private ProjectReference extendedRuntimeEnvironment;
	private final List<ProjectReference> providedRuntimeLibraries = new ArrayList<>();
	private final List<ProjectReference> requiredRuntimeLibraries = new ArrayList<>();
	private final List<ProjectDependency> dependencies = new ArrayList<>();
	private String implementationId;
	private final List<ProjectReference> implementedProjects = new ArrayList<>();
	private String outputPath;
	private final List<SourceContainerDescription> sourceContainers = new ArrayList<>();
	private final List<ModuleFilter> moduleFilters = new ArrayList<>();
	private final List<ProjectReference> testedProjects = new ArrayList<>();
	private String definesPackage;
	private boolean nestedNodeModulesFolder;
	private boolean n4jsNature;
	private boolean yarnWorkspaceRoot;
	private final List<String> workspaces = new ArrayList<>();

	public ProjectDescriptionBuilder() {
	}

	/** Create the new instance of {@link ProjectDescription}. */
	public ProjectDescription build() {
		return new ProjectDescription(name, vendorId, vendorName, version, type, mainModule,
				extendedRuntimeEnvironment, providedRuntimeLibraries, requiredRuntimeLibraries, dependencies,
				implementationId, implementedProjects, outputPath, sourceContainers, moduleFilters, testedProjects,
				definesPackage, nestedNodeModulesFolder, n4jsNature, yarnWorkspaceRoot, workspaces);
	}

	public String getName() {
		return name;
	}

	public ProjectDescriptionBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public String getVendorId() {
		return vendorId;
	}

	public ProjectDescriptionBuilder setVendorId(String vendorId) {
		this.vendorId = vendorId;
		return this;
	}

	public String getVendorName() {
		return vendorName;
	}

	public ProjectDescriptionBuilder setVendorName(String vendorName) {
		this.vendorName = vendorName;
		return this;
	}

	public VersionNumber getVersion() {
		return version;
	}

	public ProjectDescriptionBuilder setVersion(VersionNumber version) {
		this.version = version;
		return this;
	}

	public ProjectType getType() {
		return type;
	}

	public ProjectDescriptionBuilder setType(ProjectType type) {
		this.type = type;
		return this;
	}

	public String getMainModule() {
		return mainModule;
	}

	public ProjectDescriptionBuilder setMainModule(String mainModule) {
		this.mainModule = mainModule;
		return this;
	}

	public ProjectReference getExtendedRuntimeEnvironment() {
		return extendedRuntimeEnvironment;
	}

	public ProjectDescriptionBuilder setExtendedRuntimeEnvironment(ProjectReference extendedRuntimeEnvironment) {
		this.extendedRuntimeEnvironment = extendedRuntimeEnvironment;
		return this;
	}

	public List<ProjectReference> getProvidedRuntimeLibraries() {
		return providedRuntimeLibraries;
	}

	public ProjectDescriptionBuilder addProvidedRuntimeLibrary(ProjectReference providedRuntimeLibrary) {
		this.providedRuntimeLibraries.add(providedRuntimeLibrary);
		return this;
	}

	public List<ProjectReference> getRequiredRuntimeLibraries() {
		return requiredRuntimeLibraries;
	}

	public ProjectDescriptionBuilder addRequiredRuntimeLibrary(ProjectReference requiredRuntimeLibrary) {
		this.requiredRuntimeLibraries.add(requiredRuntimeLibrary);
		return this;
	}

	public List<ProjectDependency> getDependencies() {
		return dependencies;
	}

	public ProjectDescriptionBuilder addDependency(ProjectDependency dependency) {
		this.dependencies.add(dependency);
		return this;
	}

	public String getImplementationId() {
		return implementationId;
	}

	public ProjectDescriptionBuilder setImplementationId(String implementationId) {
		this.implementationId = implementationId;
		return this;
	}

	public List<ProjectReference> getImplementedProjects() {
		return implementedProjects;
	}

	public ProjectDescriptionBuilder addImplementedProject(ProjectReference implementedProject) {
		this.implementedProjects.add(implementedProject);
		return this;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public ProjectDescriptionBuilder setOutputPath(String outputPath) {
		this.outputPath = outputPath;
		return this;
	}

	public List<SourceContainerDescription> getSourceContainers() {
		return sourceContainers;
	}

	public ProjectDescriptionBuilder addSourceContainer(SourceContainerDescription sourceContainer) {
		this.sourceContainers.add(sourceContainer);
		return this;
	}

	public ProjectDescriptionBuilder addSourceContainers(
			@SuppressWarnings("hiding") Iterable<SourceContainerDescription> sourceContainers) {
		Iterables.addAll(this.sourceContainers, sourceContainers);
		return this;
	}

	public List<ModuleFilter> getModuleFilters() {
		return moduleFilters;
	}

	public ProjectDescriptionBuilder addModuleFilter(ModuleFilter moduleFilter) {
		this.moduleFilters.add(moduleFilter);
		return this;
	}

	public List<ProjectReference> getTestedProjects() {
		return testedProjects;
	}

	public ProjectDescriptionBuilder addTestedProject(ProjectReference testedProject) {
		this.testedProjects.add(testedProject);
		return this;
	}

	public String getDefinesPackage() {
		return definesPackage;
	}

	public ProjectDescriptionBuilder setDefinesPackage(String definesPackage) {
		this.definesPackage = definesPackage;
		return this;
	}

	public boolean hasNestedNodeModulesFolder() {
		return nestedNodeModulesFolder;
	}

	public ProjectDescriptionBuilder setNestedNodeModulesFolder(boolean nestedNodeModulesFolder) {
		this.nestedNodeModulesFolder = nestedNodeModulesFolder;
		return this;
	}

	public boolean hasN4JSNature() {
		return n4jsNature;
	}

	public ProjectDescriptionBuilder setN4JSNature(boolean n4jsNature) {
		this.n4jsNature = n4jsNature;
		return this;
	}

	public boolean isYarnWorkspaceRoot() {
		return yarnWorkspaceRoot;
	}

	public ProjectDescriptionBuilder setYarnWorkspaceRoot(boolean yarnWorkspaceRoot) {
		this.yarnWorkspaceRoot = yarnWorkspaceRoot;
		return this;
	}

	public List<String> getWorkspaces() {
		return workspaces;
	}

	public ProjectDescriptionBuilder addWorkspace(String workspace) {
		this.workspaces.add(workspace);
		return this;
	}
}
