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

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;

import com.google.common.base.Optional;

/**
 * The runtime facade for the n4js model containing the core (UI-free) support for n4js projects.
 * <p>
 * The single instance of this interface can be accessed via dependency injection.
 * </p>
 */
public interface IN4JSCore {

	/**
	 * Returns the N4JS project at the given location.
	 * <p>
	 * Note that no check is done at this time on the existence or structure of the location.
	 * </p>
	 *
	 * @param location
	 *            the project location
	 * @return the n4js project corresponding to the given project, null if the given location is null
	 */
	IN4JSProject create(URI location);

	/**
	 * Create a proper typesafe URI for the given location.
	 */
	SafeURI<?> toProjectLocation(URI uri);

	/**
	 * Returns the N4JS project that contains the element at the given location. The returned instance might be created
	 * on the fly to wrap another project instance. Consequently, it should not be used for identity comparisons.
	 *
	 * @param nestedLocation
	 *            the project location
	 * @return the n4js project corresponding to the given project.
	 */
	Optional<? extends IN4JSProject> findProject(URI nestedLocation);

	/**
	 * Returns the N4JS project with the given name.
	 *
	 * @param projectName
	 *            the project name
	 * @return the n4js project
	 */
	Optional<? extends IN4JSProject> findProject(N4JSProjectName projectName);

	/**
	 * Returns list of the N4JS projects that are in current working scope (IWorkspace or registered projects).
	 *
	 * @return List containing n4js projects in scope
	 */
	Iterable<? extends IN4JSProject> findAllProjects();

	/**
	 * @return a map that maps {@link IProject#getName()} to {@link IProject}.
	 */
	Map<N4JSProjectName, IN4JSProject> findAllProjectMappings();

	/**
	 * returns the source container that covers the given location.
	 */
	Optional<? extends IN4JSSourceContainer> findN4JSSourceContainer(URI nestedLocation);

	/**
	 * returns true if for the given URI validation is disabled
	 */
	boolean isNoValidate(URI nestedLocation);

	/**
	 * returns the project relative path to the folder where the generated files should be placed
	 */
	String getOutputPath(URI nestedLocation);

	/**
	 * Creates and returns a new resource set that is properly set up for loading resources in the default workspace
	 * represented by the receiving IN4JSCore, i.e. for loading resources contained in the projects returned by
	 * {@link #findAllProjects()}.
	 * <p>
	 * IMPORTANT:
	 * <ul>
	 * <li>This method should <b>NEVER</b> be used in a context where a resource set is already in place, e.g. during
	 * validations use the editor's resource set, within the incremental builder always use the builder's resource set.
	 * <li>This method should <b>ONLY</b> be used to access the
	 * {@link ResourceDescriptionsProvider#PERSISTED_DESCRIPTIONS persisted state} and <b>NEVER</b> in cases where the
	 * dirty state of the editor(s) or the live scope is to be taken into account (in such cases it is very unlikely
	 * that you have to create a new resource set from scratch, so probably you are in the above case and should try to
	 * obtain an existing resource set).
	 * </ul>
	 *
	 * @param contextProject
	 *            provide an N4JS project that defines the context, for example the containing project of the resource
	 *            you want to load. If this is not available, it is ok to pass in Optional.absent() for now (see
	 *            implementation in {@code N4JSEclipseCore#createResourceSet(Optional)} for details).<br>
	 *            Will be ignored in the headless case.
	 */
	ResourceSet createResourceSet(Optional<IN4JSProject> contextProject);

	/**
	 * Returns the Xtext index for the the given resource set. This resource set should be properly set up for Xtext and
	 * N4JS; usually client code should always prefer obtaining an existing resource set from Xtext and only if this is
	 * not available pass in a resource set returned by method {@link #createResourceSet(Optional)}.
	 * <p>
	 * Use with care. If this method is used with a resource set newly created via method
	 * {@link #createResourceSet(Optional)}, then the returned index represents the persisted information, only. All
	 * dirty editors or the currently running build with incrementally compiled resources are ignored.
	 *
	 * @param resourceSet
	 *            properly configured resource set (load options!)
	 */
	IResourceDescriptions getXtextIndex(ResourceSet resourceSet);

	/**
	 * Deserialize the TModule stored in the user data of the Xtext index.
	 * <p>
	 * If the resource set already contains a resource for the given resource description <em>and</em> that resource is
	 * already loaded (and thus has an AST loaded from source) or even already contains a TModule, then this method
	 * returns the TModule derived from the existing AST or the existing TModule, respectively. If loading from index
	 * fails <em>and</em> <code>allowFullLoad</code> is set to <code>true</code>, then this method loads the resource
	 * from source.
	 * <p>
	 * If loading from index is successfully performed, the resource containing the returned TModule will be in the
	 * state <code>fullyProcessed</code>; in all the other above cases that a non-<code>null</code> value is returned,
	 * the resource will be in state <code>fullyInitialized</code>; if <code>null</code> is returned, the state of the
	 * resource (if it exists) will be unchanged.
	 */
	TModule loadModuleFromIndex(ResourceSet resourceSet, IResourceDescription resourceDescription,
			boolean allowFullLoad);

}
