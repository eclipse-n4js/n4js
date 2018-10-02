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
package org.eclipse.n4js.ui.navigator.internal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Maps.uniqueIndex;
import static com.google.common.collect.Sets.newHashSet;
import static org.eclipse.emf.common.util.URI.createFileURI;
import static org.eclipse.emf.common.util.URI.createPlatformResourceURI;
import static org.eclipse.n4js.external.libraries.ExternalLibrariesActivator.EXTERNAL_LIBRARIES_SUPPLIER;
import static org.eclipse.n4js.external.libraries.ExternalLibrariesActivator.EXTERNAL_LIBRARY_NAMES;
import static org.eclipse.n4js.external.libraries.ExternalLibrariesActivator.LANG_CATEGORY;
import static org.eclipse.n4js.external.libraries.ExternalLibrariesActivator.MANGELHAFT_CATEGORY;
import static org.eclipse.n4js.external.libraries.ExternalLibrariesActivator.NPM_CATEGORY;
import static org.eclipse.n4js.external.libraries.ExternalLibrariesActivator.RUNTIME_CATEGORY;
import static org.eclipse.n4js.projectDescription.ProjectType.RUNTIME_ENVIRONMENT;
import static org.eclipse.n4js.projectDescription.ProjectType.RUNTIME_LIBRARY;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.ExternalProject;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;
import org.eclipse.n4js.utils.collections.Arrays2;
import org.eclipse.n4js.utils.resources.IExternalResource;
import org.eclipse.swt.graphics.Image;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Helper class for the N4JS based Project Explorer content and label providers.
 */
@Singleton
public class N4JSProjectExplorerHelper {

	@Inject
	private IN4JSCore core;

	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;

	/**
	 * Returns with the corresponding {@link IN4JSProject N4JS project} for the given {@link IProject Eclipse project}
	 * argument. Returns with {@code null} if
	 * <ul>
	 * <li>the {@code project} argument is {@code null} or</li>
	 * <li>no {@link IN4JSProject#exists() existing} {@link IN4JSProject N4JS project} project can be found with the
	 * desired project ID.</li>
	 * </ul>
	 *
	 * @param project
	 *            the searched project.
	 * @return the N4JS project or {@code null} if such project does not exist.
	 */
	public IN4JSProject getProject(IProject project) {

		checkArgument(!(project instanceof ExternalProject), "Expected Eclipse workspace project. Got: " + project);

		if (null == project || !project.exists() || !project.isOpen()) {
			return null;
		}

		final String projectName = Strings.nullToEmpty(project.getName());
		final IN4JSProject n4Project = core.create(createPlatformResourceURI(projectName, true));
		if (null == n4Project || !n4Project.exists() || n4Project.isExternal()) {
			return null;
		}
		return n4Project;
	}

	/**
	 * Returns {@code true} if the folder argument is declared as a source container in the container project. Otherwise
	 * returns with {@code false}.
	 *
	 * @param folder
	 *            the folder to test whether it is a source container, or not. Can be {@code null}. If {@code null},
	 *            this method immediately returns with {@code false}.
	 * @return {@code true} if the folder is a source folder, otherwise returns with {@code false}.
	 */
	public boolean isSourceFolder(IFolder folder) {

		if (null == folder || !folder.exists()) {
			return false;
		}

		IN4JSProject project = getProject(folder.getProject());

		if (null != project) {
			String relativePath = Strings.nullToEmpty(folder.getProjectRelativePath().toOSString());
			return from(project.getSourceContainers()).transform(src -> src.getRelativeLocation())
					.contains(relativePath);
		}

		return false;
	}

	/**
	 * Returns with {@code true} if the folder argument represents a output folder in its container project. Otherwise
	 * returns with {@code false}.
	 *
	 * @param folder
	 *            the folder to test whether it is an output folder or not.
	 * @return {@code true} if the folder is configured as an output folder in the project, otherwise returns with
	 *         {@code false}.
	 */
	public boolean isOutputFolder(IFolder folder) {

		if (null == folder || !folder.exists()) {
			return false;
		}

		IN4JSProject project = getProject(folder.getProject());

		if (null != project) {
			String relativePath = Strings.nullToEmpty(folder.getProjectRelativePath().toOSString());
			return relativePath.equals(project.getOutputPath());
		}

		return false;
	}

	/**
	 * Returns with an array of virtual {@link Node node} instances for the project that should be revealed in the
	 * project explorer.
	 *
	 * @param project
	 *            the workspace project.
	 * @return an array of virtual nodes.
	 */
	public Node[] getVirtualNodesForProject(IProject project) {

		checkArgument(!(project instanceof ExternalProject), "Expected Eclipse workspace project. Got: " + project);

		if (null == project || !project.exists() || !project.isAccessible()) {
			return Node.EMPTY_NODES;
		}

		IN4JSProject n4Project = getProject(project);
		if (null == n4Project || !n4Project.exists() || n4Project.isExternal()) {
			return Node.EMPTY_NODES;
		}

		final Image image = ImageRef.LIB_PATH.asImage().get();
		final NamedNode rootNode = new NamedNode(project, "External Dependencies", image);

		Iterable<IN4JSProject> directDependencies = from(n4Project.getAllDirectDependencies())
				.filter(IN4JSProject.class);

		Iterable<IN4JSProject> runtimeLibraries = getExternalDependenciesOfType(directDependencies, RUNTIME_LIBRARY);
		if (!from(runtimeLibraries).isEmpty()) {

			Map<String, IN4JSProject> builtInRuntimeEnvironments = getBuiltInRuntimeEnvironments();
			Map<String, IN4JSProject> builtInRuntimeLibraries = getBuiltInRuntimeLibraries();

			Collection<IN4JSProject> libs = newHashSet();
			Collection<IN4JSProject> envs = newHashSet();
			for (IN4JSProject p : runtimeLibraries) {
				IN4JSProject dependency = builtInRuntimeLibraries.get(p.getProjectName());
				if (null != dependency) {
					libs.add(dependency);
				}
			}

			if (!libs.isEmpty()) {
				OUTER: for (IN4JSProject p : builtInRuntimeEnvironments.values()) {
					for (IN4JSProject providedLib : from(p.getProvidedRuntimeLibraries()).filter(IN4JSProject.class)) {
						if (libs.contains(providedLib)) {
							envs.add(p);
							String extndedRuntimeEnvName = p.getExtendedRuntimeEnvironmentId().orNull();
							if (null != extndedRuntimeEnvName) {
								final IN4JSProject extension = builtInRuntimeEnvironments.get(extndedRuntimeEnvName);
								if (null != extension) {
									envs.add(extension);
								}
							}
							continue OUTER;
						}
					}
				}
			}

			NamedNode runtimeNode = new NamedNode(rootNode, "N4JS Runtime", image);

			if (!envs.isEmpty()) {
				NamedNode envsNode = new NamedNode(runtimeNode, "Runtime Environments", image);
				envsNode.addChild(from(envs).transform(p -> new BuiltInProjectNode(envsNode, p)));
				runtimeNode.addChild(envsNode);
			}

			if (!libs.isEmpty()) {
				NamedNode libsNode = new NamedNode(runtimeNode, "Runtime Libraries", image);
				libsNode.addChild(from(libs).transform(p -> new BuiltInProjectNode(libsNode, p)));
				runtimeNode.addChild(libsNode);
			}

			if (!Arrays2.isEmpty(runtimeNode.getChildren())) {
				rootNode.addChild(runtimeNode);
			}

		}

		Map<String, IN4JSProject> langProjects = getAvailableLangProjects();
		Map<String, IN4JSProject> mangelhaftProjects = getAvailableMangelhaftProjects();
		Map<String, IN4JSProject> npmProjects = getAvailableNpmProjects();

		Collection<IN4JSProject> requiredLangLibs = newHashSet();
		Collection<IN4JSProject> requiredMangelhaftLibs = newHashSet();
		Collection<IN4JSProject> requiredNpmLibs = newHashSet();

		for (IN4JSProject directDependecy : directDependencies) {
			if (directDependecy.exists() && directDependecy.isExternal()) {
				IN4JSProject externalDepenency = mangelhaftProjects.get(directDependecy.getProjectName());
				if (null != externalDepenency) {
					requiredMangelhaftLibs.add(externalDepenency);
				} else {
					externalDepenency = npmProjects.get(directDependecy.getProjectName());
					if (null != externalDepenency) {
						requiredNpmLibs.add(externalDepenency);
					} else {
						externalDepenency = langProjects.get(directDependecy.getProjectName());
						if (null != externalDepenency) {
							requiredLangLibs.add(externalDepenency);
						}
					}
				}
			}
		}

		if (!requiredLangLibs.isEmpty()) {
			NamedNode langNode = new NamedNode(rootNode, EXTERNAL_LIBRARY_NAMES.get(LANG_CATEGORY), image);
			langNode.addChild(from(requiredLangLibs).transform(p -> new BuiltInProjectNode(langNode, p)));
			rootNode.addChild(langNode);
		}

		if (!requiredMangelhaftLibs.isEmpty()) {
			NamedNode mangNode = new NamedNode(rootNode, EXTERNAL_LIBRARY_NAMES.get(MANGELHAFT_CATEGORY), image);
			mangNode.addChild(from(requiredMangelhaftLibs).transform(p -> new BuiltInProjectNode(mangNode, p)));
			rootNode.addChild(mangNode);
		}

		if (!requiredNpmLibs.isEmpty()) {
			NamedNode npmNode = new NamedNode(rootNode, EXTERNAL_LIBRARY_NAMES.get(NPM_CATEGORY), image);
			npmNode.addChild(from(requiredNpmLibs).transform(p -> new BuiltInProjectNode(npmNode, p)));
			rootNode.addChild(npmNode);
		}
		return Arrays2.isEmpty(rootNode.getChildren()) ? new Node[0] : new Node[] { rootNode };

	}

	private Iterable<IN4JSProject> getExternalDependenciesOfType(Iterable<? extends IN4JSProject> dependencies,
			ProjectType type, ProjectType... others) {

		Collection<ProjectType> allowedTypes = EnumSet.of(type, others);
		return from(dependencies)
				.filter(p -> p.exists() && p.isExternal())
				.filter(p -> allowedTypes.contains(p.getProjectType()))
				.filter(IN4JSProject.class);
	}

	private Map<String, IN4JSProject> getAvailableNpmProjects() {
		return uniqueIndex(from(getBuiltInLibraries(NPM_CATEGORY)), p -> p.getProjectName());
	}

	private Map<String, IN4JSProject> getAvailableLangProjects() {
		return uniqueIndex(from(getBuiltInLibraries(LANG_CATEGORY)), p -> p.getProjectName());
	}

	private Map<String, IN4JSProject> getAvailableMangelhaftProjects() {
		return uniqueIndex(from(getBuiltInLibraries(MANGELHAFT_CATEGORY)), p -> p.getProjectName());
	}

	private Map<String, IN4JSProject> getBuiltInRuntimeEnvironments() {
		return uniqueIndex(from(getBuiltInLibraries(RUNTIME_CATEGORY))
				.filter(p -> RUNTIME_ENVIRONMENT.equals(p.getProjectType())), p -> p.getProjectName());
	}

	private Map<String, IN4JSProject> getBuiltInRuntimeLibraries() {
		return uniqueIndex(from(getBuiltInLibraries(RUNTIME_CATEGORY))
				.filter(p -> RUNTIME_LIBRARY.equals(p.getProjectType())), p -> p.getProjectName());
	}

	private Iterable<IN4JSProject> getBuiltInLibraries(String externalLibraryName) {
		URI location = EXTERNAL_LIBRARIES_SUPPLIER.get().inverse().get(externalLibraryName);

		return location != null ? from(externalLibraryWorkspace.getProjectsIn(location))
				.filter(IExternalResource.class)
				.transform(r -> r.getExternalResource())
				.transform(file -> createFileURI(file.getAbsolutePath()))
				.transform(uri -> core.findProject(uri).orNull())
				.filter(IN4JSProject.class) : Collections.emptyList();
	}

}
