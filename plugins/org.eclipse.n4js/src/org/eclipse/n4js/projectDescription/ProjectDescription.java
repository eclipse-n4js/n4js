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
package org.eclipse.n4js.projectDescription;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.semver.model.SemverSerializer;
import org.eclipse.n4js.utils.ImmutableDataClass;

import com.google.common.collect.ImmutableList;

/**
 * Basic information about a project, as read from the {@code package.json} file in the project's root folder.
 */
@SuppressWarnings("javadoc")
public class ProjectDescription extends ImmutableDataClass {

	private final String projectName;
	private final String vendorId;
	private final String vendorName;
	private final VersionNumber projectVersion;
	private final String internalProjectVersionStr; // for hash code computation and equality checks
	private final ProjectType projectType;
	private final String mainModule;
	private final ProjectReference extendedRuntimeEnvironment;
	private final ImmutableList<ProjectReference> providedRuntimeLibraries;
	private final ImmutableList<ProjectReference> requiredRuntimeLibraries;
	private final ImmutableList<ProjectDependency> projectDependencies;
	private final String implementationId;
	private final ImmutableList<ProjectReference> implementedProjects;
	private final String outputPath;
	private final ImmutableList<SourceContainerDescription> sourceContainers;
	private final ImmutableList<ModuleFilter> moduleFilters;
	private final ImmutableList<ProjectReference> testedProjects;
	private final String definesPackage;
	private final boolean nestedNodeModulesFolder;
	private final boolean n4jsNature;
	private final boolean yarnWorkspaceRoot;
	private final ImmutableList<String> workspaces;

	/** Better use a {@link ProjectDescriptionBuilder builder}. */
	public ProjectDescription(String projectName, String vendorId, String vendorName, VersionNumber projectVersion,
			ProjectType projectType, String mainModule, ProjectReference extendedRuntimeEnvironment,
			Iterable<ProjectReference> providedRuntimeLibraries, Iterable<ProjectReference> requiredRuntimeLibraries,
			Iterable<ProjectDependency> projectDependencies, String implementationId,
			Iterable<ProjectReference> implementedProjects, String outputPath,
			Iterable<SourceContainerDescription> sourceContainers, Iterable<ModuleFilter> moduleFilters,
			Iterable<ProjectReference> testedProjects, String definesPackage, boolean nestedNodeModulesFolder,
			boolean n4jsNature, boolean yarnWorkspaceRoot, Iterable<String> workspaces) {
		this.projectName = projectName;
		this.vendorId = vendorId;
		this.vendorName = vendorName;
		this.projectVersion = projectVersion != null ? EcoreUtil.copy(projectVersion) : null;
		this.internalProjectVersionStr = projectVersion != null ? SemverSerializer.serialize(projectVersion) : null;
		this.projectType = projectType;
		this.mainModule = mainModule;
		this.extendedRuntimeEnvironment = extendedRuntimeEnvironment;
		this.providedRuntimeLibraries = ImmutableList.copyOf(providedRuntimeLibraries);
		this.requiredRuntimeLibraries = ImmutableList.copyOf(requiredRuntimeLibraries);
		this.projectDependencies = ImmutableList.copyOf(projectDependencies);
		this.implementationId = implementationId;
		this.implementedProjects = ImmutableList.copyOf(implementedProjects);
		this.outputPath = outputPath;
		this.sourceContainers = ImmutableList.copyOf(sourceContainers);
		this.moduleFilters = ImmutableList.copyOf(moduleFilters);
		this.testedProjects = ImmutableList.copyOf(testedProjects);
		this.definesPackage = definesPackage;
		this.nestedNodeModulesFolder = nestedNodeModulesFolder;
		this.n4jsNature = n4jsNature;
		this.yarnWorkspaceRoot = yarnWorkspaceRoot;
		this.workspaces = ImmutableList.copyOf(workspaces);
	}

	public ProjectDescription(ProjectDescription template) {
		this.projectName = template.projectName;
		this.vendorId = template.vendorId;
		this.vendorName = template.vendorName;
		this.projectVersion = template.projectVersion != null ? EcoreUtil.copy(template.projectVersion) : null;
		this.internalProjectVersionStr = projectVersion != null ? SemverSerializer.serialize(projectVersion) : null;
		this.projectType = template.projectType;
		this.mainModule = template.mainModule;
		this.extendedRuntimeEnvironment = template.extendedRuntimeEnvironment;
		this.providedRuntimeLibraries = template.providedRuntimeLibraries;
		this.requiredRuntimeLibraries = template.requiredRuntimeLibraries;
		this.projectDependencies = template.projectDependencies;
		this.implementationId = template.implementationId;
		this.implementedProjects = template.implementedProjects;
		this.outputPath = template.outputPath;
		this.sourceContainers = template.sourceContainers;
		this.moduleFilters = template.moduleFilters;
		this.testedProjects = template.testedProjects;
		this.definesPackage = template.definesPackage;
		this.nestedNodeModulesFolder = template.nestedNodeModulesFolder;
		this.n4jsNature = template.n4jsNature;
		this.yarnWorkspaceRoot = template.yarnWorkspaceRoot;
		this.workspaces = template.workspaces;
	}

	/** Builds a new {@link ProjectDescription project description}. */
	public static ProjectDescriptionBuilder builder() {
		return new ProjectDescriptionBuilder();
	}

	public ProjectDescriptionBuilder change() {
		ProjectDescriptionBuilder builder = new ProjectDescriptionBuilder();
		builder.setProjectName(projectName);
		builder.setVendorId(vendorId);
		builder.setVendorName(vendorName);
		builder.setProjectVersion(projectVersion != null ? EcoreUtil.copy(projectVersion) : null);
		builder.setProjectType(projectType);
		builder.setMainModule(mainModule);
		builder.setExtendedRuntimeEnvironment(extendedRuntimeEnvironment);
		builder.getProvidedRuntimeLibraries().addAll(providedRuntimeLibraries);
		builder.getRequiredRuntimeLibraries().addAll(requiredRuntimeLibraries);
		builder.getProjectDependencies().addAll(projectDependencies);
		builder.setImplementationId(implementationId);
		builder.getImplementedProjects().addAll(implementedProjects);
		builder.setOutputPath(outputPath);
		builder.getSourceContainers().addAll(sourceContainers);
		builder.getModuleFilters().addAll(moduleFilters);
		builder.getTestedProjects().addAll(testedProjects);
		builder.setDefinesPackage(definesPackage);
		builder.setNestedNodeModulesFolder(nestedNodeModulesFolder);
		builder.setN4JSNature(n4jsNature);
		builder.setYarnWorkspaceRoot(yarnWorkspaceRoot);
		builder.getWorkspaces().addAll(workspaces);
		return builder;
	}

	/** The project name, possibly including a scope prefix (e.g. {@code "@someScope/myProject"}). */
	public String getProjectName() {
		return projectName;
	}

	public String getVendorId() {
		return vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public VersionNumber getProjectVersion() {
		return projectVersion;
	}

	public ProjectType getProjectType() {
		return projectType;
	}

	public String getMainModule() {
		return mainModule;
	}

	public ProjectReference getExtendedRuntimeEnvironment() {
		return extendedRuntimeEnvironment;
	}

	public List<ProjectReference> getProvidedRuntimeLibraries() {
		return providedRuntimeLibraries;
	}

	public List<ProjectReference> getRequiredRuntimeLibraries() {
		return requiredRuntimeLibraries;
	}

	public List<ProjectDependency> getProjectDependencies() {
		return projectDependencies;
	}

	public String getImplementationId() {
		return implementationId;
	}

	public List<ProjectReference> getImplementedProjects() {
		return implementedProjects;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public List<SourceContainerDescription> getSourceContainers() {
		return sourceContainers;
	}

	public List<ModuleFilter> getModuleFilters() {
		return moduleFilters;
	}

	public List<ProjectReference> getTestedProjects() {
		return testedProjects;
	}

	/**
	 * Returns the name of the package the project provides type definitions for. {@code null} if this project does not
	 * specify the property (i.e. not a type definitions project (cf. {@link ProjectType#DEFINITION}).
	 */
	public String getDefinesPackage() {
		return definesPackage;
	}

	/**
	 * Tells if the project represented by this project description has a nested "node_modules" folder, i.e. a folder
	 * named "node_modules" located right next to the package.json file.
	 */
	public boolean hasNestedNodeModulesFolder() {
		return nestedNodeModulesFolder;
	}

	/**
	 * Indicates whether the underlying project description explicitly configured the project to be an N4JS project
	 * (e.g. includes n4js section).
	 */
	public boolean hasN4JSNature() {
		return n4jsNature;
	}

	/**
	 * Tells whether the project represented by this project description is the root of a yarn workspace. This flag will
	 * be {@code true} iff the package.json contains yarn's top-level property "workspaces", no matter the value (i.e.
	 * will be {@code true} even if the value is the empty array).
	 */
	public boolean isYarnWorkspaceRoot() {
		return yarnWorkspaceRoot;
	}

	/**
	 * Value of top-level property "workspaces" in package.json, used by yarn to denote the contained projects.
	 */
	public List<String> getWorkspaces() {
		return workspaces;
	}

	@Override
	protected int computeHashCode() {
		return Objects.hash(
				projectName,
				vendorId,
				vendorName,
				// projectVersion is covered by internalProjectVersionStr
				internalProjectVersionStr,
				projectType,
				mainModule,
				extendedRuntimeEnvironment,
				providedRuntimeLibraries,
				requiredRuntimeLibraries,
				projectDependencies,
				implementationId,
				implementedProjects,
				outputPath,
				sourceContainers,
				moduleFilters,
				testedProjects,
				definesPackage,
				nestedNodeModulesFolder,
				n4jsNature,
				yarnWorkspaceRoot,
				workspaces);
	}

	@Override
	protected boolean computeEquals(Object obj) {
		ProjectDescription other = (ProjectDescription) obj;
		return Objects.equals(projectName, other.projectName)
				&& Objects.equals(vendorId, other.vendorId)
				&& Objects.equals(vendorName, other.vendorName)
				// projectVersion is covered by internalProjectVersionStr
				&& Objects.equals(internalProjectVersionStr, other.internalProjectVersionStr)
				&& projectType == other.projectType
				&& Objects.equals(mainModule, other.mainModule)
				&& Objects.equals(extendedRuntimeEnvironment, other.extendedRuntimeEnvironment)
				&& Objects.equals(providedRuntimeLibraries, other.providedRuntimeLibraries)
				&& Objects.equals(requiredRuntimeLibraries, other.requiredRuntimeLibraries)
				&& Objects.equals(projectDependencies, other.projectDependencies)
				&& Objects.equals(implementationId, other.implementationId)
				&& Objects.equals(implementedProjects, other.implementedProjects)
				&& Objects.equals(outputPath, other.outputPath)
				&& Objects.equals(sourceContainers, other.sourceContainers)
				&& Objects.equals(moduleFilters, other.moduleFilters)
				&& Objects.equals(testedProjects, other.testedProjects)
				&& Objects.equals(definesPackage, other.definesPackage)
				&& nestedNodeModulesFolder == other.nestedNodeModulesFolder
				&& n4jsNature == other.n4jsNature
				&& yarnWorkspaceRoot == other.yarnWorkspaceRoot
				&& Objects.equals(workspaces, other.workspaces);
	}
}
