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
package org.eclipse.n4js.projectModel;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.n4mf.BootstrapModule;
import org.eclipse.n4js.n4mf.DeclaredVersion;
import org.eclipse.n4js.n4mf.ModuleFilter;
import org.eclipse.n4js.n4mf.ModuleLoader;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectType;
import org.eclipse.n4js.n4mf.utils.N4MFConstants;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

/**
 * A common interface to projects and their configuration. {@link IN4JSProject Projects} have a set of relevant
 * properties like the configured dependencies and how they resolve to either archives or other projects, or the
 * configured source containers.
 * <p>
 * IN4JSProjects are handle objects that are not necessarily backed by existing structures in the file system. Some
 * methods don't yield interesting information, if the element does not exist. Others work. See the JavaDoc of each
 * method for details.
 * </p>
 * <p>
 * This is modeled similar to {@code org.eclipse.jdt.core.JavaCore} works, e.g. instances of {@link IN4JSProject} are
 * obtained via {@link IN4JSCore#create(URI)}.
 */
public interface IN4JSProject extends IN4JSSourceContainerAware {

	/**
	 * The name of the package.json file.
	 */
	public final static String PACKAGE_JSON = "package.json";

	/**
	 * The name of the manifest file
	 */
	public final static String N4MF_MANIFEST = N4MFConstants.N4MF_MANIFEST;

	/**
	 * Returns the project type of the project or null, if type is not available
	 */
	ProjectType getProjectType();

	/**
	 * Returns whether this project element exists.
	 * <p>
	 * IN4JSProjects are handle objects that may or may not be backed by an actual element. Elements that are backed by
	 * an actual element are said to "exist", and this method returns <code>true</code>.
	 * </p>
	 *
	 * @return <code>true</code> if this element exists, and <code>false</code> if this element does not exist
	 */
	boolean exists();

	/**
	 * The configured libraries on the search path, empty if the project does not exist.
	 *
	 * The project's manifest may declare dependencies that either resolve to archives or other projects.
	 *
	 * @see #getDependencies()
	 */
	ImmutableList<? extends IN4JSArchive> getLibraries();

	/**
	 * The configured projects on the search path (including runtime environment and libraries), empty if the project
	 * does not exist.
	 *
	 * The project's manifest may declare dependencies that either resolve to archives or other projects.
	 *
	 * @see #getLibraries()
	 */
	ImmutableList<? extends IN4JSProject> getDependencies();

	/**
	 * Similar to {@link #getDependencies()} but including implemented APIs. Note: Implemented APIs are not a real
	 * dependency since they are replaced by this implementor at runtime.
	 */
	ImmutableList<? extends IN4JSProject> getDependenciesAndImplementedApis();

	/**
	 * Returns the raw provided runtime libraries data for this project. Empty list returned if project is not
	 * {@link ProjectType#RUNTIME_ENVIRONMENT} or does not provide any libraries.
	 */
	ImmutableList<? extends IN4JSSourceContainerAware> getProvidedRuntimeLibraries();

	/**
	 * Returns projectId of the extended runtime, if any.
	 *
	 * @return optional but not null string
	 */
	Optional<String> getExtendedRuntimeEnvironmentId();

	/**
	 * Returns with the extended runtime environment of the project. If not specified returns with an absent instance.
	 *
	 * @return the extended RE. Could be absent but never {@code null}.
	 */
	Optional<IN4JSSourceContainerAware> getExtendedRuntimeEnvironment();

	/**
	 * The vendor ID. It is not available, if the project does not exist.
	 */
	String getVendorID();

	/**
	 * returns the value of the <code>ModuleLoader</code> property in the manifest.
	 */
	ModuleLoader getModuleLoader();

	/**
	 * The project's location. Also available if the project does not exist. This will return a platform URI when
	 * running within Eclipse, and a file URI in headless mode.
	 */
	@Override
	URI getLocation();

	/**
	 * The project's location in the local file system.
	 */
	Path getLocationPath();

	/**
	 * The declared version of the project. It is not available, if the project does not exist.
	 */
	DeclaredVersion getVersion();

	/**
	 * returns the project relative path to the folder where the generated files should be placed
	 */
	String getOutputPath();

	/**
	 * returns the project relative paths to the folders where the resources should be placed
	 */
	List<String> getResourcePaths();

	/**
	 * returns the no-validate module filter
	 */
	ModuleFilter getModuleValidationFilter();

	/**
	 * returns the no-module-wrapping module filter
	 */
	ModuleFilter getNoModuleWrappingFilter();

	/**
	 * returns the project relative paths to the library folders
	 */
	List<String> getLibraryFolders();

	/**
	 * returns the module specifier of this project's main module or <code>null</code> if not given in manifest.
	 */
	String getMainModule();

	/**
	 * returns the implementation ID of the project if and only if this project is an implementation project
	 */
	Optional<String> getImplementationId();

	/**
	 * returns the projects implemented by the receiving project
	 */
	ImmutableList<? extends IN4JSProject> getImplementedProjects();

	/**
	 * returns list of initialization modules of this project (only Runtime Environment), or empty list
	 */
	List<BootstrapModule> getInitModules();

	/**
	 * returns execution module of this project (only Runtime Environment), or empty optional
	 */
	Optional<BootstrapModule> getExecModule();

	/**
	 * Returns with the URI of the file that contains the project description of this project.
	 *
	 * Optional, could return with an absent instance if the project description file does not exist in the project.
	 * Never {@code null}.
	 *
	 * @return the URI of the project description file. Optional, may be missing but never {@code null}.
	 */
	Optional<URI> getProjectDescriptionLocation();

	/**
	 * Returns with a collection of the tested projects for the current project. May return with an empty collection if:
	 * <p>
	 * <ul>
	 * <li>the current project does not exist,</li>
	 * <li>the current project is NOT a {@link ProjectType#TEST test} project,</li>
	 * <li>the {@link ProjectDescription#getTestedProjects() tested projects} is not specified for the current test
	 * project or</li>
	 * </ul>
	 *
	 * @return a collection of tested projects for the current test project.
	 */
	Collection<IN4JSProject> getTestedProjects();
}
