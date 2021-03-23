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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectDescription.ProjectDependency;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectDescription.SourceContainerDescription;
import org.eclipse.n4js.projectDescription.SourceContainerType;
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

/**
 * Like {@link N4JSWorkspaceConfig}, but for individual projects.
 */
@SuppressWarnings("restriction")
public class N4JSProjectConfig implements XIProjectConfig {

	private final ProjectDescriptionLoader projectDescriptionLoader;

	private final N4JSWorkspaceConfig workspace;
	private final FileURI path;
	// the following are not immutable, because an existing project might have its properties changed:
	private ProjectDescription projectDescription;
	private Set<? extends IN4JSSourceFolder> sourceFolders;

	/**
	 * Constructor
	 */
	public N4JSProjectConfig(N4JSWorkspaceConfig workspace, FileURI path, ProjectDescription pd,
			ProjectDescriptionLoader projectDescriptionLoader) {
		this.workspace = Objects.requireNonNull(workspace);
		this.path = Objects.requireNonNull(path);
		this.projectDescriptionLoader = Objects.requireNonNull(projectDescriptionLoader);

		this.projectDescription = Objects.requireNonNull(pd);
		this.sourceFolders = createSourceFolders(pd);
	}

	/**
	 * Re-reads the project state from disk and notifies the parent workspace (if a change occurred).
	 */
	protected void updateProjectStateFromDisk() {
		ProjectDescription pdOld = projectDescription;
		projectDescription = projectDescriptionLoader.loadProjectDescriptionAtLocation(path);
		if (projectDescription == null) {
			projectDescription = ProjectDescription.builder().build();
		}
		// a project is not allowed to change its name
		String nameOld = pdOld.getProjectName();
		if (!Objects.equals(projectDescription.getProjectName(), nameOld)) {
			projectDescription = projectDescription.change().setProjectName(nameOld).build();
		}
		if (projectDescription.equals(pdOld)) {
			return; // nothing changed
		}
		sourceFolders = createSourceFolders(projectDescription);
		workspace.onProjectChanged(path, pdOld, projectDescription);
	}

	/**
	 * Create source folders from the information in the given project description. Does not update the state of this
	 * project configuration.
	 */
	protected Set<? extends IN4JSSourceFolder> createSourceFolders(ProjectDescription pd) {
		Set<IN4JSSourceFolder> result = new LinkedHashSet<>();
		for (SourceContainerDescription scd : pd.getSourceContainers()) {
			SourceContainerType type = scd.getSourceContainerType();
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

	/** Tells whether this project exists. */
	public boolean exists() {
		return getProjectDescriptionURI().isFile();
	}

	@Override
	public URI getPath() {
		return path.withTrailingPathDelimiter().toURI();
	}

	/** Returns the {@link #getPath() path} as a {@link FileURI}. */
	public FileURI getPathAsFileURI() {
		return path;
	}

	/** Returns the project description. */
	public ProjectDescription getProjectDescription() {
		return projectDescription;
	}

	/** Returns a {@link FileURI} pointing to the <code>package.json</code> file of this project. */
	public FileURI getProjectDescriptionURI() {
		return getPathAsFileURI().appendSegment(N4JSGlobals.PACKAGE_JSON);
	}

	@Override
	public String getName() {
		return projectDescription.getProjectName();
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
			result.add(dep.getProjectName());
		}
		return result;
	}

	/**
	 * Like {@link #getDependencies()}, but the dependencies returned here are
	 * <ol>
	 * <li>unresolved dependencies are removed,
	 * <li>sorted (definition projects before their implementation projects),
	 * <li>implicit dependencies are added (dependency to definition project added).
	 * </ol>
	 * The sorting allows the use definition projects and their implementation counterparts side by side in a meaningful
	 * way. In a nutshell: Implementation projects may contribute modules to the index that are not available as n4jsd
	 * files yet. All other modules should be shadowed by the definition project.
	 */
	public List<ProjectDependency> computeSemanticDependencies() {
		List<ProjectDependency> deps = projectDescription.getProjectDependencies();
		return ImmutableList.copyOf(
				SemanticDependencySupplier.computeSemanticDependencies(workspace.definitionProjects, deps));
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
	 * Projects are indexed but not transpiled if they have '.' as the output path or if they are located in
	 * node_modules folders.
	 *
	 * @return true iff this project should be indexed only
	 */
	@Override
	public boolean indexOnly() {
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
		ProjectType projectType = projectDescription.getProjectType();
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

		// detect changes in project properties
		boolean propertiesChanged = !addedSourceFolders.isEmpty() || !removedSourceFolders.isEmpty()
				|| !Objects.equals(oldProjectConfig, newProjectConfig);

		return new WorkspaceChanges(ImmutableList.of(), ImmutableList.of(), ImmutableList.of(),
				ImmutableList.copyOf(removedSourceFolders),
				ImmutableList.copyOf(addedSourceFolders), ImmutableList.of(), ImmutableList.of(),
				propertiesChanged ? ImmutableList.of(newProjectConfig) : ImmutableList.of());
	}
}
