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

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.workspace.locations.FileURI;

import com.google.common.collect.Iterables;

/**
 * Builder for {@link ProjectDescription}s.
 */
@SuppressWarnings("javadoc")
public class ProjectDescriptionBuilder {

	private FileURI location;
	private FileURI relatedRootLocation;
	private String id;

	private String packageName;
	private String vendorId;
	private String vendorName;
	private VersionNumber version;
	private ProjectType projectType;
	private String main;
	private String types;
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
	private boolean esm;
	private boolean moduleProperty;
	private boolean n4jsNature;
	private boolean yarnWorkspaceRoot;
	private Boolean isGeneratorEnabledSourceMaps;
	private Boolean isGeneratorEnabledDts;
	private final Map<String, String> generatorRewriteModuleSpecifiers = new HashMap<>();
	private Boolean isGeneratorEnabledRewriteCjsImports;
	private final List<String> workspaces = new ArrayList<>();

	private final List<String> tsFiles = new ArrayList<>();
	private final List<String> tsInclude = new ArrayList<>();
	private final List<String> tsExclude = new ArrayList<>();

	public ProjectDescriptionBuilder() {
	}

	/** Create the new instance of {@link ProjectDescription}. */
	public ProjectDescription build() {
		id = id == null ? computeProjectID() : id;
		isGeneratorEnabledSourceMaps = isGeneratorEnabledSourceMaps == null ? false : isGeneratorEnabledSourceMaps;
		isGeneratorEnabledDts = isGeneratorEnabledDts == null ? false : isGeneratorEnabledDts;
		isGeneratorEnabledRewriteCjsImports = isGeneratorEnabledRewriteCjsImports == null ? false
				: isGeneratorEnabledRewriteCjsImports;

		return new ProjectDescription(location, relatedRootLocation, id,
				packageName, vendorId, vendorName, version, projectType, main, types, mainModule,
				extendedRuntimeEnvironment,
				providedRuntimeLibraries, requiredRuntimeLibraries, dependencies, implementationId, implementedProjects,
				outputPath, sourceContainers, moduleFilters, testedProjects, definesPackage,
				nestedNodeModulesFolder, esm, moduleProperty, n4jsNature, yarnWorkspaceRoot,
				isGeneratorEnabledSourceMaps, isGeneratorEnabledDts, generatorRewriteModuleSpecifiers,
				isGeneratorEnabledRewriteCjsImports, workspaces, tsFiles, tsInclude, tsExclude);
	}

	public String computeProjectID() {
		if (relatedRootLocation == null) {
			relatedRootLocation = location;
		}

		if (location != null) {
			if (relatedRootLocation != null && relatedRootLocation.getParent() != null) {
				Path parent = relatedRootLocation.getParent().toPath();
				if (parent.getFileName() != null
						&& parent.getFileName().toString().startsWith("@")
						&& parent.getParent() != null) {

					parent = parent.getParent();
				}
				Path relativeLocation = parent.relativize(location.toPath());
				return relativeLocation.toString();
			}
		}
		return packageName;
	}

	public FileURI getLocation() {
		return location;
	}

	public ProjectDescriptionBuilder setLocation(FileURI location) {
		this.location = location;
		return this;
	}

	public ProjectDescriptionBuilder setLocation(URI location) {
		this.location = location == null ? null : new FileURI(location);
		return this;
	}

	public FileURI getRelatedRootLocation() {
		return relatedRootLocation;
	}

	public ProjectDescriptionBuilder setRelatedRootLocation(FileURI relatedRootlocation) {
		this.relatedRootLocation = relatedRootlocation;
		return this;
	}

	public ProjectDescriptionBuilder setRelatedRootLocation(URI relatedRootlocation) {
		this.relatedRootLocation = relatedRootlocation == null ? null : new FileURI(relatedRootlocation);
		return this;
	}

	public String getId() {
		return id;
	}

	public ProjectDescriptionBuilder setId(String id) {
		this.id = id;
		return this;
	}

	public String getPackageName() {
		return packageName;
	}

	public ProjectDescriptionBuilder setPackageName(String packageName) {
		this.packageName = packageName;
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

	public ProjectType getProjectType() {
		return projectType;
	}

	public ProjectDescriptionBuilder setProjectType(ProjectType type) {
		this.projectType = type;
		return this;
	}

	public String getMainModule() {
		return mainModule;
	}

	public ProjectDescriptionBuilder setMainModule(String mainModule) {
		this.mainModule = mainModule;
		return this;
	}

	public String getMain() {
		return main;
	}

	public ProjectDescriptionBuilder setMain(String main) {
		this.main = main;
		return this;
	}

	public String getTypes() {
		return types;
	}

	public ProjectDescriptionBuilder setTypes(String typesModule) {
		this.types = typesModule;
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

	public boolean isESM() {
		return esm;
	}

	public ProjectDescriptionBuilder setESM(boolean esm) {
		this.esm = esm;
		return this;
	}

	public boolean hasModuleProperty() {
		return moduleProperty;
	}

	public ProjectDescriptionBuilder setModuleProperty(boolean moduleProperty) {
		this.moduleProperty = moduleProperty;
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

	public Boolean isGeneratorEnabledSourceMaps() {
		return isGeneratorEnabledSourceMaps;
	}

	public ProjectDescriptionBuilder setGeneratorEnabledSourceMaps(boolean isGeneratorEnabledSourceMaps) {
		this.isGeneratorEnabledSourceMaps = isGeneratorEnabledSourceMaps;
		return this;
	}

	public Boolean isGeneratorEnabledDts() {
		return isGeneratorEnabledDts;
	}

	public ProjectDescriptionBuilder setGeneratorEnabledDts(boolean isGeneratorEnabledDts) {
		this.isGeneratorEnabledDts = isGeneratorEnabledDts;
		return this;
	}

	public Map<String, String> getGeneratorRewriteModuleSpecifiers() {
		return this.generatorRewriteModuleSpecifiers;
	}

	public Boolean isGeneratorEnabledRewriteCjsImports() {
		return isGeneratorEnabledRewriteCjsImports;
	}

	public ProjectDescriptionBuilder setGeneratorEnabledRewriteCjsImports(boolean isGeneratorEnabledRewriteCjsImports) {
		this.isGeneratorEnabledRewriteCjsImports = isGeneratorEnabledRewriteCjsImports;
		return this;
	}

	public List<String> getWorkspaces() {
		return workspaces;
	}

	public ProjectDescriptionBuilder addWorkspace(String workspace) {
		this.workspaces.add(workspace);
		return this;
	}

	public List<String> getTsFiles() {
		return tsFiles;
	}

	public ProjectDescriptionBuilder addTsFile(String tsFileElem) {
		this.tsFiles.add(tsFileElem);
		return this;
	}

	public List<String> getTsInclude() {
		return tsInclude;
	}

	public ProjectDescriptionBuilder addTsInclude(String tsIncludeElem) {
		this.tsInclude.add(tsIncludeElem);
		return this;
	}

	public List<String> getTsExclude() {
		return tsExclude;
	}

	public ProjectDescriptionBuilder addTsExclude(String tsExcludeElem) {
		this.tsExclude.add(tsExcludeElem);
		return this;
	}
}
