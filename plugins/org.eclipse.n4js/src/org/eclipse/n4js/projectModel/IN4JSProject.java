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

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.internal.N4JSProject;
import org.eclipse.n4js.projectDescription.ModuleFilter;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

/**
 * A common interface to projects and their configuration. {@link IN4JSProject Projects} have a set of relevant
 * properties like the configured dependencies and how they resolve to other projects, or the configured source
 * containers.
 * <p>
 * IN4JSProjects are handle objects that are not necessarily backed by existing structures in the file system. Some
 * methods don't yield interesting information, if the element does not exist. Others work. See the JavaDoc of each
 * method for details.
 * </p>
 * <p>
 * This is modeled similar to {@code org.eclipse.jdt.core.JavaCore} works, e.g. instances of {@link IN4JSProject} are
 * obtained via {@link IN4JSCore#create(URI)}.
 */
public interface IN4JSProject extends IProjectConfigEx {

	/**
	 * The name of the package.json file.
	 */
	public final static String PACKAGE_JSON = N4JSGlobals.PACKAGE_JSON;

	/**
	 * Returns the receiving project's <em>N4JS project name</em>, as defined at
	 * {@link ProjectDescriptionUtils#isProjectNameWithScope(String) here}.
	 * <p>
	 * For scoped projects this will include the scope name and be of the form {@code @myScope/myProject}. Always equal
	 * to value of the top-level "name" property in the {@code package.json} (enforced by a validation).
	 * <p>
	 * NOTE: in the UI case, this is distinct from the <em>Eclipse project name</em> as returned by
	 * {@link IProject#getName()}.
	 * <p>
	 * For more details, see {@link ProjectDescriptionUtils#isProjectNameWithScope(String)}.
	 */
	String getProjectName();

	/**
	 * The project's location. Also available if the project does not exist. This will return a platform URI when
	 * running within Eclipse, and a file URI in headless mode.
	 */
	URI getLocation();

	/**
	 * The source containers of this container structure, possibly empty.
	 */
	ImmutableList<? extends IN4JSSourceContainer> getSourceContainers();

	/**
	 * All direct dependencies for this structure.
	 */
	ImmutableList<? extends IN4JSProject> getAllDirectDependencies();

	/**
	 * @return true if this container structure is external to the workspace.
	 */
	boolean isExternal();

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
	 * The configured projects on the search path (including runtime environment and libraries), empty if the project
	 * does not exist.
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
	ImmutableList<? extends IN4JSProject> getProvidedRuntimeLibraries();

	/**
	 * Returns projectName of the extended runtime, if any.
	 *
	 * @return optional but not null string
	 */
	Optional<String> getExtendedRuntimeEnvironmentId();

	/**
	 * Returns with the extended runtime environment of the project. If not specified returns with an absent instance.
	 *
	 * @return the extended RE. Could be absent but never {@code null}.
	 */
	Optional<IN4JSProject> getExtendedRuntimeEnvironment();

	/**
	 * The vendor ID. It is not available, if the project does not exist.
	 */
	String getVendorID();

	/**
	 * The project's location in the local file system.
	 */
	Path getLocationPath();

	/**
	 * The declared version of the project. It is not available, if the project does not exist.
	 */
	VersionNumber getVersion();

	/**
	 * returns the project relative path to the folder where the generated files should be placed
	 */
	String getOutputPath();

	/**
	 * returns the no-validate module filter
	 */
	ModuleFilter getModuleValidationFilter();

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

	/**
	 * Returns {@code true} if this {@link N4JSProject} was explicitly configured to be of the N4JS nature.
	 *
	 * @See {@link ProjectDescription#isHasN4JSNature()}
	 */
	boolean hasN4JSNature();

	/**
	 * Returns the name of the package this {@code project} provides type definitions for.
	 *
	 * {@code null} if this project does not specify the property (i.e. not a type definitions project (cf.
	 * {@link ProjectType#DEFINITION})).
	 */
	public String getDefinesPackageName();
}
