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
import static com.google.common.base.Strings.nullToEmpty;
import static org.eclipse.emf.common.util.URI.createPlatformResourceURI;
import static org.eclipse.n4js.projectDescription.ProjectType.API;
import static org.eclipse.xtext.util.Strings.toFirstUpper;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.n4js.external.ExternalIndexSynchronizer;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.ExternalProject;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.external.ShadowingInfoHelper;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.semver.model.SemverSerializer;
import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;
import org.eclipse.n4js.utils.collections.Arrays2;
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

	@Inject
	private ExternalLibraryPreferenceStore prefStore;

	@Inject
	private ExternalIndexSynchronizer indexSynchronizer;

	@Inject
	private ShadowingInfoHelper shadowingInfoHelper;

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
		IN4JSProject project = getProject(folder.getProject());

		if (project != null) {
			String relativePath = Strings.nullToEmpty(folder.getProjectRelativePath().toOSString());
			for (IN4JSSourceContainer srcContainer : project.getSourceContainers()) {
				String relSrcCont = Strings.nullToEmpty(srcContainer.getRelativeLocation());
				if (relativePath.equals(relSrcCont) || relSrcCont.startsWith(relativePath + File.separator)) {
					return true;
				}
			}
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
		IN4JSProject project = getProject(folder.getProject());

		if (project != null) {
			String relativePath = Strings.nullToEmpty(folder.getProjectRelativePath().toOSString());
			String outputPath = Strings.nullToEmpty(project.getOutputPath());
			return relativePath.equals(outputPath) || outputPath.startsWith(relativePath + File.separator);
		}
		return false;
	}

	/**
	 * Returns with {@code true} if the folder argument represents a node_modules folder in its container project.
	 * Otherwise returns with {@code false}.
	 *
	 * @param folder
	 *            the folder to test whether it is an output folder or not.
	 * @return {@code true} if the folder is detected as an node_modules folder in the project, otherwise returns with
	 *         {@code false}.
	 */
	public N4JSExternalProject getNodeModulesNpmProjectOrNull(IFolder folder) {
		return externalLibraryWorkspace.getProject(new FileURI(folder.getLocation().toFile()));
	}

	/**
	 * Returns with {@code true} if the folder argument represents a node_modules folder in its container project.
	 * Otherwise returns with {@code false}.
	 *
	 * @param folder
	 *            the folder to test whether it is an output folder or not.
	 * @return {@code true} if the folder is detected as an node_modules folder in the project, otherwise returns with
	 *         {@code false}.
	 */
	public boolean isNodeModulesNpmProject(IFolder folder) {
		IContainer parentContainer = folder.getParent();
		if (parentContainer instanceof IFolder) {
			return isNodeModulesFolder(parentContainer);
		}
		return false;
	}

	/**
	 * Returns with {@code true} if the folder argument represents a node_modules folder in its container project.
	 * Otherwise returns with {@code false}.
	 *
	 * @param container
	 *            the folder to test whether it is an output folder or not.
	 * @return {@code true} if the folder is detected as an node_modules folder in the project, otherwise returns with
	 *         {@code false}.
	 */
	public boolean isNodeModulesFolder(IContainer container) {
		if ("node_modules".equals(container.getName()) && container instanceof IFolder) {
			IPath path = container.getLocation();
			FileURI locURI = new FileURI(path.toFile());
			if (prefStore.getNodeModulesLocations().contains(locURI)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return a styled string for a given external project. Respects name, type, version, and information about
	 *         shadowing and whether it is available in the xtext index
	 */
	public StyledString getStyledTextForExternalProject(final IN4JSProject project,
			N4JSProjectName overrideProjectName) {
		N4JSProjectName name = (overrideProjectName == null) ? project.getProjectName() : overrideProjectName;
		ProjectType type = project.getProjectType();
		// for better visual representation MyProject @1.2.3 -> MyProject v1.2.3
		String version = SemverSerializer.serialize(project.getVersion()).replaceFirst("@", "v");
		String typeLabel = getProjectTypeLabel(type);
		boolean inIndex = project.isExternal()
				&& indexSynchronizer.isInIndex((FileURI) project.getProjectDescriptionLocation());
		String rootLocationName = getRootLocationName(project);

		Styler stylerName = inIndex ? null : StyledString.QUALIFIER_STYLER;
		Styler stylerType = inIndex ? StyledString.DECORATIONS_STYLER : StyledString.QUALIFIER_STYLER;
		StyledString string = new StyledString(name + " " + version, stylerName);
		string.append(typeLabel, stylerType);
		if (rootLocationName != null) {
			string.append(rootLocationName, StyledString.COUNTER_STYLER);
		}
		return string;
	}

	private String getRootLocationName(final IN4JSProject project) {
		String rootLocationName = null;
		List<IN4JSProject> shadowingProjects = shadowingInfoHelper.findShadowingProjects(project);
		if (!shadowingProjects.isEmpty()) {
			IN4JSProject shadowedProject = shadowingProjects.get(0);
			if (shadowedProject.isExternal()) {
				FileURI location = (FileURI) shadowedProject.getLocation();
				FileURI rootLocation = externalLibraryWorkspace.getRootLocationForResource(location);
				if (rootLocation != null) {
					Path rootPath = rootLocation.toFileSystemPath();
					Path subpath = rootPath.subpath(rootPath.getNameCount() - 2, rootPath.getNameCount());
					rootLocationName = subpath.toString();
				} else {
					rootLocationName = "unknown";
				}
			} else {
				rootLocationName = "workspace";
			}
			rootLocationName = " [shadowed by " + rootLocationName + "]";
		}
		return rootLocationName;
	}

	private String getProjectTypeLabel(final ProjectType projectType) {
		final String label;
		if (API.equals(projectType)) {
			label = API.getName();
		} else {
			label = toFirstUpper(nullToEmpty(projectType.getName()).replaceAll("_", " ").toLowerCase());
		}
		return " [" + label + "]";
	}

	/**
	 * Returns with an array of virtual {@link Node node} instances for the project that should be revealed in the
	 * project explorer.
	 *
	 * <p>
	 * <b>IMPLEMENTATION NOTE</b>
	 * </p>
	 *
	 * This was used to show shipped-code projects, if and only if they are referenced among the direct dependencies of
	 * the given project, under an additional, virtual node "External Dependencies" in the project explorer. Since
	 * shipped code was removed from the IDE, this method will now always return an empty array. However, this method
	 * was not removed because a similar feature might be required for something else in the future.
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

		// note: at this point, children of rootNode were created for various categories of shipped-code projects

		return Arrays2.isEmpty(rootNode.getChildren()) ? Node.EMPTY_NODES : new Node[] { rootNode };
	}
}
