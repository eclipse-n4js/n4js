/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.workspace;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDependency;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.packagejson.projectDescription.SourceContainerDescription;
import org.eclipse.n4js.packagejson.projectDescription.SourceContainerType;
import org.eclipse.n4js.utils.OSInfo;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.workspace.utils.N4JSProjectName;
import org.eclipse.n4js.workspace.utils.SemanticDependencySupplier;
import org.eclipse.n4js.xtext.workspace.ConfigSnapshotFactory;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.SourceFolderSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceChanges;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.XIProjectConfig;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

/**
 * Like {@link N4JSWorkspaceConfig}, but for individual projects.
 */
@SuppressWarnings("restriction")
public class N4JSProjectConfig implements XIProjectConfig {

	private final ProjectDescriptionLoader projectDescriptionLoader;
	private final SemanticDependencySupplier semanticDependencySupplier;

	private final N4JSWorkspaceConfig workspace;
	private final FileURI path;
	// the following are not immutable, because an existing project might have its properties changed:
	private ProjectDescription projectDescription;
	private Set<? extends IN4JSSourceFolder> sourceFolders;
	private List<ProjectDependency> semanticDependencies;
	private Map<String, String> packageNameToProjectIds;

	/**
	 * Constructor
	 */
	public N4JSProjectConfig(N4JSWorkspaceConfig workspace, FileURI path, ProjectDescription pd,
			ProjectDescriptionLoader projectDescriptionLoader, SemanticDependencySupplier semanticDependencySupplier) {

		this.workspace = Objects.requireNonNull(workspace);
		this.path = Objects.requireNonNull(path);
		this.projectDescriptionLoader = Objects.requireNonNull(projectDescriptionLoader);
		this.semanticDependencySupplier = Objects.requireNonNull(semanticDependencySupplier);

		this.projectDescription = Objects.requireNonNull(pd);
		this.sourceFolders = createSourceFolders(pd);
	}

	/**
	 * Re-reads the project state from disk and notifies the parent workspace (if a change occurred).
	 */
	protected void updateProjectStateFromDisk() {
		ProjectDescription pdOld = projectDescription;
		projectDescription = projectDescriptionLoader
				.loadProjectDescriptionAtLocation(path, pdOld.getRelatedRootLocation());
		if (projectDescription == null) {
			projectDescription = ProjectDescription.builder().build();
		}
		// a project is not allowed to change its name
		String nameOld = pdOld.getPackageName();
		if (!Objects.equals(projectDescription.getPackageName(), nameOld)) {
			// projectDescription = projectDescription.change().setName(nameOld).build();
			System.out.println();
		}
		if (projectDescription.equals(pdOld)) {
			return; // nothing changed
		}
		sourceFolders = createSourceFolders(projectDescription);
		semanticDependencies = null;
		packageNameToProjectIds = null;
		workspace.onProjectChanged(path, pdOld, projectDescription);
	}

	/**
	 * Create source folders from the information in the given project description. Does not update the state of this
	 * project configuration.
	 */
	protected Set<? extends IN4JSSourceFolder> createSourceFolders(ProjectDescription pd) {
		Set<IN4JSSourceFolder> result = new LinkedHashSet<>();
		for (SourceContainerDescription scd : pd.getSourceContainers()) {
			SourceContainerType type = scd.getType();
			for (String relPath : ProjectDescriptionUtils.getPathsNormalized(scd)) {
				result.add(new N4JSSourceFolder(this, type, relPath));
			}
		}
		result.add(new N4JSSourceFolderForPackageJson(this));
		return result;
	}

	@Override
	public IWorkspaceConfig getWorkspaceConfig() {
		return workspace;
	}

	/** Tells whether this project still exists on disk at the time this method is invoked. */
	public boolean exists() {
		return getProjectDescriptionURI().isFile();
	}

	@Override
	public URI getPath() {
		return path.withTrailingPathDelimiter().toURI();
	}

	/** @return the related root, see {@link ProjectDescription#getRelatedRootLocation()} */
	public Path getRelatedRootLocation() {
		return projectDescription.getRelatedRootLocation().toPath();
	}

	/** Returns the {@link #getPath() path} as a {@link FileURI}. */
	public FileURI getPathAsFileURI() {
		return path;
	}

	/** Returns an absolute {@link FileURI} for the given project-relative path. */
	public FileURI getAbsolutePath(String relativePath) {
		if (!Strings.isNullOrEmpty(relativePath) && !relativePath.equals(".")) {
			String linuxPath = OSInfo.isWindows() ? relativePath.replace(File.separatorChar, '/') : relativePath;
			return getPathAsFileURI().appendPath(linuxPath);
		}
		return getPathAsFileURI();
	}

	/** Returns the project description. */
	public ProjectDescription getProjectDescription() {
		return projectDescription;
	}

	/** Returns a {@link FileURI} pointing to the <code>package.json</code> file of this project. */
	public FileURI getProjectDescriptionURI() {
		return getPathAsFileURI().appendSegment(N4JSGlobals.PACKAGE_JSON);
	}

	/** @return the project id */
	@Override
	public String getName() {
		return projectDescription.getId();
	}

	/** @return the project name */
	public String getPackageName() {
		return projectDescription.getPackageName();
	}

	/** Returns this project's name as an {@link N4JSProjectName}. */
	public N4JSProjectName getN4JSProjectName() {
		return new N4JSProjectName(getName());
	}

	/** Tells whether this project is a yarn workspace project. */
	public boolean isWorkspaceProject() {
		return projectDescription.isYarnWorkspaceRoot()
				&& projectDescription.getWorkspaces() != null
				&& !projectDescription.getWorkspaces().isEmpty();
	}

	/** The dependencies of this project as given in the <code>package.json</code> file. */
	@Override
	public Set<String> getDependencies() {
		List<ProjectDependency> deps = projectDescription.getProjectDependencies();
		Set<String> result = new LinkedHashSet<>(deps.size());
		for (ProjectDependency dep : deps) {
			result.add(getProjectIdForPackageName(dep.getPackageName()));
		}
		return result;
	}

	/**
	 * Like {@link #getDependencies()}, but ...
	 * <ol>
	 * <li>unresolved dependencies are removed,
	 * <li>dependencies are sorted (definition projects appear before implementation projects),
	 * <li>implicit dependencies are added (definition projects are added if their defined project is among the
	 * dependencies).
	 * </ol>
	 * The sorting allows the use of definition projects and their implementation counterparts side by side in a
	 * meaningful way. In a nut-shell: implementation projects may contribute modules to the index that are not
	 * available as n4jsd files yet; all other modules should be shadowed by the definition project (i.e. shadowing on
	 * the module-level).
	 */
	public List<ProjectDependency> getSemanticDependencies() {
		if (semanticDependencies == null) {
			init();
		}
		return semanticDependencies;
	}

	/**
	 * Returns the project id for the given package name. This method respects the context of this project, i.e. that in
	 * case there exist multiple projects with the given package name, the project id of that project is returned that a
	 * dependency from this project would bind to.
	 */
	public String getProjectIdForPackageName(String packageName) {
		return getPackageNameForProjectIdMap().getOrDefault(packageName, packageName);
	}

	/**
	 * @return a map from package names to project ids. This map respects the context of this project, see
	 *         {@link #getProjectIdForPackageName(String)}.
	 */
	public Map<String, String> getPackageNameForProjectIdMap() {
		if (packageNameToProjectIds == null) {
			init();
		}
		return packageNameToProjectIds;
	}

	/** Initializes {@link #packageNameToProjectIds} and {@link #semanticDependencies}. */
	protected void init() {
		List<ProjectDependency> deps = projectDescription.getProjectDependencies();
		List<ProjectDependency> semanticDeps = semanticDependencySupplier
				.computeSemanticDependencies(workspace.definitionProjects, deps);

		Path relatedRootLocation = getRelatedRootLocation();

		HashSet<String> allNames = new HashSet<>(workspace.getAllPackageNames());
		semanticDeps.stream().forEach(d -> allNames.add(d.getPackageName()));

		packageNameToProjectIds = Collections.unmodifiableMap(semanticDependencySupplier.computePackageName2ProjectIdMap(
				workspace, projectDescription, relatedRootLocation, allNames));

		List<ProjectDependency> result = new ArrayList<>(semanticDeps.size());
		for (ProjectDependency sdep : semanticDeps) {
			String qualifiedName = getProjectIdForPackageName(sdep.getPackageName());
			ProjectDependency newDep = new ProjectDependency(qualifiedName, sdep.getType(),
					sdep.getVersionRequirementString(), sdep.getVersionRequirement());
			result.add(newDep);
		}
		semanticDependencies = Collections.unmodifiableList(result);
	}

	@Override
	public Set<? extends IN4JSSourceFolder> getSourceFolders() {
		return sourceFolders;
	}

	@Override
	public IN4JSSourceFolder findSourceFolderContaining(URI nestedURI) {
		for (IN4JSSourceFolder candidate : getSourceFolders()) { // includes the package.json source folder
			if (candidate.contains(nestedURI)) {
				return candidate;
			}
		}
		return null;
	}

	/**
	 * Projects are indexed but not transpiled if they are located in node_modules folders.
	 *
	 * @return true iff this project should be indexed only
	 */
	@Override
	public boolean indexOnly() {
		return isInNodeModulesFolder();
	}

	/** @return true iff this project is located inside a node_modules folder */
	public boolean isInNodeModulesFolder() {
		URI projectBase = getPath();
		String lastSegment = projectBase.lastSegment();
		if (lastSegment == null || lastSegment.isBlank()) {
			projectBase = projectBase.trimSegments(1);
		}
		projectBase = projectBase.trimSegments(1); // trim the project name
		lastSegment = projectBase.lastSegment();
		if (lastSegment != null && lastSegment.startsWith("@")) {
			projectBase = projectBase.trimSegments(1);
			lastSegment = projectBase.lastSegment();
		}
		if (lastSegment != null && N4JSGlobals.NODE_MODULES.equals(lastSegment)) {
			// index only true for npm libraries
			return true;
		}
		return false;
	}

	@Override
	public boolean isGeneratorEnabled() {
		ProjectType projectType = projectDescription.getType();
		return !N4JSGlobals.PROJECT_TYPES_WITHOUT_GENERATION.contains(projectType);
	}

	/**
	 * Updates this project configuration's internal state.
	 * <p>
	 * This methods handles changes from
	 * <ul>
	 * <li>existing -> existing (w/o project modifications) and from
	 * <li>existing -> non-existing (project deletion)
	 * </ul>
	 */
	public WorkspaceChanges update(WorkspaceConfigSnapshot oldWorkspaceConfig, URI changedResource,
			ConfigSnapshotFactory configSnapshotFactory) {

		String projectName = getName();
		ProjectConfigSnapshot oldProjectConfig = projectName != null ? oldWorkspaceConfig.findProjectByName(projectName)
				: null;

		if (!exists()) {
			// project was deleted
			return oldProjectConfig != null ? WorkspaceChanges.createProjectRemoved(oldProjectConfig)
					: WorkspaceChanges.NO_CHANGES;
		}

		URI pckjson = getProjectDescriptionURI().toURI();
		if (!pckjson.equals(changedResource)) {
			// different file was saved/modified (not package.json)
			return WorkspaceChanges.NO_CHANGES;
		}

		// package.json was modified

		updateProjectStateFromDisk();
		ProjectConfigSnapshot newProjectConfig = configSnapshotFactory.createProjectConfigSnapshot(this);

		if (oldProjectConfig == null) {
			return WorkspaceChanges.createProjectAdded(newProjectConfig);
		}

		return computeChanges(oldProjectConfig, newProjectConfig);
	}

	/**
	 * Given two project configuration snapshots representing the old and new state of the receiving project, this
	 * method computes the corresponding workspace changes. If no changes occurred, an empty {@link WorkspaceChanges}
	 * instance will be returned.
	 */
	public static WorkspaceChanges computeChanges(ProjectConfigSnapshot oldProjectConfig,
			ProjectConfigSnapshot newProjectConfig) {

		Set<? extends SourceFolderSnapshot> oldSourceFolders = oldProjectConfig.getSourceFolders();
		Set<? extends SourceFolderSnapshot> newSourceFolders = newProjectConfig.getSourceFolders();

		// detect added/removed source folders
		Map<URI, SourceFolderSnapshot> oldSFs = new HashMap<>();
		Map<URI, SourceFolderSnapshot> newSFs = new HashMap<>();
		for (SourceFolderSnapshot sourceFolder : oldSourceFolders) {
			oldSFs.put(sourceFolder.getPath(), sourceFolder);
		}
		for (SourceFolderSnapshot sourceFolder : newSourceFolders) {
			newSFs.put(sourceFolder.getPath(), sourceFolder);
		}
		List<SourceFolderSnapshot> addedSourceFolders = new ArrayList<>();
		List<SourceFolderSnapshot> removedSourceFolders = new ArrayList<>();
		for (URI sfUri : Iterables.concat(oldSFs.keySet(), newSFs.keySet())) {
			boolean isOld = oldSFs.containsKey(sfUri);
			boolean isNew = newSFs.containsKey(sfUri);
			if (isOld && isNew) {
				// unchanged
			} else if (isOld && !isNew) {
				removedSourceFolders.add(oldSFs.get(sfUri));
			} else if (!isOld && isNew) {
				addedSourceFolders.add(newSFs.get(sfUri));
			}
		}

		if (Objects.equals(oldProjectConfig.getName(), newProjectConfig.getName())) {
			// detect changes in project properties
			boolean propertiesChanged = !addedSourceFolders.isEmpty() || !removedSourceFolders.isEmpty()
					|| !Objects.equals(oldProjectConfig, newProjectConfig);

			return new WorkspaceChanges(ImmutableList.of(), ImmutableList.of(), ImmutableList.of(),
					ImmutableList.copyOf(removedSourceFolders),
					ImmutableList.copyOf(addedSourceFolders), ImmutableList.of(), ImmutableList.of(),
					propertiesChanged ? ImmutableList.of(newProjectConfig) : ImmutableList.of());
		} else {

			return new WorkspaceChanges(ImmutableList.of(), ImmutableList.of(), ImmutableList.of(),
					ImmutableList.copyOf(removedSourceFolders),
					ImmutableList.copyOf(addedSourceFolders),
					ImmutableList.of(oldProjectConfig), ImmutableList.of(newProjectConfig),
					ImmutableList.of());
		}
	}
}
