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
package org.eclipse.n4js.workspace;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.scoping.utils.UserDataAwareScope;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;
import org.eclipse.n4js.workspace.utils.N4JSPackageName;
import org.eclipse.n4js.xtext.workspace.ProjectSet;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigAdapter;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceDescriptionsProvider;
import org.eclipse.xtext.resource.ISynchronizable;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Helper class for accessing the current workspace configuration of a context defined by an EMF {@link ResourceSet} (or
 * an element contained in a resource set).
 */
@Singleton
public class WorkspaceAccess {

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	private IResourceDescriptionsProvider resourceDescriptionsProvider;

	/**
	 * Returns the current workspace configuration of the context defined by the given EMF {@link ResourceSet} or
	 * element contained in a resource set. Returns {@link N4JSWorkspaceConfigSnapshot#EMPTY} in case no workspace
	 * configuration can be found. Never returns <code>null</code>.
	 */
	public N4JSWorkspaceConfigSnapshot getWorkspaceConfig(Notifier context) {
		ResourceSet resourceSet = EcoreUtil2.getResourceSet(context);
		WorkspaceConfigSnapshot config = resourceSet != null
				? WorkspaceConfigAdapter.getWorkspaceConfig(resourceSet)
				: null;
		return config != null ? (N4JSWorkspaceConfigSnapshot) config : N4JSWorkspaceConfigSnapshot.EMPTY;
	}

	/** Convenience for {@link N4JSWorkspaceConfigSnapshot#getProjects()}. */
	public ImmutableSet<N4JSProjectConfigSnapshot> findAllProjects(Notifier context) {
		return getWorkspaceConfig(context).getProjects();
	}

	/** Convenience for {@link N4JSWorkspaceConfigSnapshot#findProjectByPath(URI)}. */
	public N4JSProjectConfigSnapshot findProjectByPath(Notifier context, URI path) {
		return getWorkspaceConfig(context).findProjectByPath(path);
	}

	/** Convenience for {@link N4JSWorkspaceConfigSnapshot#findProjectByID(String)}. */
	public N4JSProjectConfigSnapshot findProjectByName(Notifier context, N4JSPackageName packageName) {
		return packageName != null ? getWorkspaceConfig(context).findProjectByPackageName(packageName) : null;
	}

	/** Convenience for {@link N4JSWorkspaceConfigSnapshot#findProjectByID(String)}. */
	public N4JSProjectConfigSnapshot findProjectByName(Notifier context, String projectID) {
		return getWorkspaceConfig(context).findProjectByID(projectID);
	}

	/**
	 * Convenience for {@link N4JSWorkspaceConfigSnapshot#findProjectByNestedLocation(URI)}.
	 * <p>
	 * Note the difference to {@link #findProjectContaining(Notifier, URI)}; for details see
	 * {@link ProjectSet#findProjectByNestedLocation(URI) HERE}.
	 */
	public N4JSProjectConfigSnapshot findProjectByNestedLocation(Notifier context, URI nestedLocation) {
		return getWorkspaceConfig(context).findProjectByNestedLocation(nestedLocation);
	}

	/**
	 * Same as {@link #findProjectContaining(Notifier, URI)}, using the resource containing the given eObject both as
	 * context and for the URI.
	 */
	public N4JSProjectConfigSnapshot findProjectContaining(EObject eObject) {
		Resource resource = eObject != null ? eObject.eResource() : null;
		return resource != null ? findProjectContaining(resource, resource.getURI()) : null;
	}

	/**
	 * Same as {@link #findProjectContaining(Notifier, URI)}, using the given resource both as context and for the URI.
	 */
	public N4JSProjectConfigSnapshot findProjectContaining(Resource resource) {
		return resource != null ? findProjectContaining(resource, resource.getURI()) : null;
	}

	/**
	 * Convenience for {@link N4JSWorkspaceConfigSnapshot#findProjectContaining(URI)}.
	 * <p>
	 * Note the difference to {@link #findProjectByNestedLocation(Notifier, URI)}; for details see
	 * {@link ProjectSet#findProjectByNestedLocation(URI) HERE}.
	 */
	public N4JSProjectConfigSnapshot findProjectContaining(Notifier context, URI nestedLocation) {
		return getWorkspaceConfig(context).findProjectContaining(nestedLocation);
	}

	/** Convenience for {@link N4JSWorkspaceConfigSnapshot#findSourceFolderContaining(URI)}. */
	public N4JSSourceFolderSnapshot findSourceFolderContaining(Notifier context, URI nestedLocation) {
		return getWorkspaceConfig(context).findSourceFolderContaining(nestedLocation);
	}

	/** Convenience for {@link N4JSWorkspaceConfigSnapshot#findProjectAndSourceFolderContaining(URI)}. */
	public Pair<N4JSProjectConfigSnapshot, N4JSSourceFolderSnapshot> findProjectAndSourceFolderContaining(
			Notifier context, URI nestedLocation) {
		return getWorkspaceConfig(context).findProjectAndSourceFolderContaining(nestedLocation);
	}

	/** Convenience for {@link N4JSWorkspaceConfigSnapshot#isNoValidate(URI)}. */
	public boolean isNoValidate(Notifier context, URI nestedLocation) {
		return getWorkspaceConfig(context).isNoValidate(nestedLocation);
	}

	// ######################################################################################################
	// The following methods are included here for historic reasons:

	/**
	 * Creates a new resource set and configures it with the "current" state of the "default workspace". It is undefined
	 * which workspace will be used (in case several exist) and which exact point in time its state will represent.
	 * <b>Therefore, this method should only be used when setting up an independent environment from scratch (e.g. some
	 * external tooling).</b>
	 * <p>
	 * Because no workspace context is defined on the level of bundle <code>org.eclispe.n4js</code> this method will use
	 * an {@link N4JSWorkspaceConfigSnapshot#EMPTY EMPTY} workspace configuration, by default. In the IDE context, the
	 * current workspace configuration of the LSP builder's {@code ConcurrentIndex} will be used (configured in override
	 * of this method in subclass {@code IdeWorkspaceAccess}). In tests, a mock workspace configuration will be used
	 * (configured indirectly via {@code MockResourceSetProvider}).
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
	 */
	public XtextResourceSet createResourceSet() {
		XtextResourceSet result = resourceSetProvider.get();
		// use EMPTY iff the resource set provider did not install a workspace config (e.g. MockResourceSetProvider):
		if (WorkspaceConfigAdapter.getWorkspaceConfig(result) == null) {
			WorkspaceConfigAdapter.installWorkspaceConfig(result, N4JSWorkspaceConfigSnapshot.EMPTY);
		}
		return result;
	}

	/**
	 * Returns the Xtext index for the the given context object. Convenience method for
	 * {@link IResourceDescriptionsProvider#getResourceDescriptions(ResourceSet)}.
	 */
	public Optional<IResourceDescriptions> getXtextIndex(Notifier context) {
		ResourceSet resourceSet = EcoreUtil2.getResourceSet(context);
		if (resourceSet == null) {
			return Optional.absent();
		}
		IResourceDescriptions result = resourceDescriptionsProvider.getResourceDescriptions(resourceSet);
		return Optional.fromNullable(result);
	}

	/**
	 * Convenience method. When looking up several objects, prefer method
	 * {@link #loadEObjectFromIndex(IResourceDescriptions, ResourceSet, URI, boolean)} (for performance reasons).
	 */
	public EObject loadEObjectFromIndex(ResourceSet resourceSet, URI eObjectURI, boolean allowFullLoad) {
		Optional<IResourceDescriptions> index = getXtextIndex(resourceSet);
		if (!index.isPresent()) {
			return null;
		}
		return loadEObjectFromIndex(index.get(), resourceSet, eObjectURI, allowFullLoad);
	}

	/**
	 * Loads a TModule element from the user data of the Xtext index. Returns <code>null</code> in case of error. If the
	 * given <code>eObjectURI</code> does not point to a TModule element, this method will avoid a "load from source"
	 * and instead return <code>null</code>.
	 * <p>
	 * Normally the "load from index" behavior should happen automatically (via {@link UserDataAwareScope} and/or
	 * {@link ProxyResolvingEObjectImpl} with {@link N4JSResource#doResolveProxy(InternalEObject, EObject)}), so this
	 * method should only be used in rare special cases if there is a good reason for the automatic "load from index"
	 * behavior being unavailable.
	 *
	 * @param index
	 *            the Xtext index to use.
	 * @param resourceSet
	 *            the resource set to used as context and as container for the newly created resource containing the
	 *            target object. This resource set may be changed!
	 * @param eObjectURI
	 *            the URI of the target object to load.
	 * @param allowFullLoad
	 *            see
	 * @return the object loaded or <code>null</code>.
	 */
	public EObject loadEObjectFromIndex(IResourceDescriptions index, ResourceSet resourceSet, URI eObjectURI,
			boolean allowFullLoad) {
		URI targetResourceURI = eObjectURI.trimFragment();
		ResourceType targetResourceType = ResourceType.getResourceType(targetResourceURI);
		String targetFragment = eObjectURI.fragment();
		if (!targetResourceType.isN4JS()
				|| targetFragment == null
				|| !(targetFragment.equals("/1") || targetFragment.startsWith("/1/"))) {
			// eObjectURI does not point to an EObject in the TModule of an N4JS resource
			return null;
		}
		IResourceDescription targetResourceDesc = index.getResourceDescription(targetResourceURI);
		TModule targetModule = targetResourceDesc != null
				? loadModuleFromIndex(resourceSet, targetResourceDesc, allowFullLoad)
				: null;
		Resource targetResource = targetModule != null ? targetModule.eResource() : null;
		return targetResource != null ? targetResource.getEObject(targetFragment) : null;
	}

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
	public TModule loadModuleFromIndex(final ResourceSet resourceSet, IResourceDescription resourceDescription,
			boolean allowFullLoad) {

		final URI resourceURI = resourceDescription.getURI();
		Resource resource = resourceSet.getResource(resourceURI, false);
		TModule result = loadModuleFromResource(resource);
		if (result != null) {
			return result;
		}
		if (resource == null) {
			if (resourceSet instanceof ISynchronizable<?>) {
				synchronized (((ISynchronizable<?>) resourceSet).getLock()) {
					resource = resourceSet.getResource(resourceURI, false);
					result = loadModuleFromResource(resource);
					if (result != null) {
						return result;
					}
					if (resource == null) {
						resource = resourceSet.createResource(resourceURI);
					}
				}
			} else {
				resource = resourceSet.createResource(resourceURI);
			}
		}
		if (resource instanceof N4JSResource) {
			if (resource.getContents().isEmpty()) {
				final N4JSResource casted = (N4JSResource) resource;
				try {
					if (casted.loadFromDescription(resourceDescription)) {
						casted.performPostProcessing();
						return casted.getModule();
					} else if (allowFullLoad) {
						casted.unload();
						casted.load(resourceSet.getLoadOptions());
						casted.installDerivedState(false);
						return casted.getModule();
					}
				} catch (final Exception e) {
					casted.unload();
					return null;
				}
			}
		}
		return null;
	}

	private TModule loadModuleFromResource(Resource resource) {
		if (resource instanceof N4JSResource) {
			final N4JSResource resourceCasted = (N4JSResource) resource;
			final Script existingScript = resourceCasted.getScript();
			final TModule existingModule = resourceCasted.getModule();
			if (existingModule != null) {
				// resource exists already and it already has a TModule
				// -> simply return that
				return existingModule;
			} else if (existingScript != null && !existingScript.eIsProxy()) {
				// resource exists already and it already has its AST loaded (though no TModule yet)
				// -> we have to create the TModule from that AST instead of loading it from index
				resourceCasted.installDerivedState(false); // trigger installation of derived state (i.e. types builder)
				return resourceCasted.getModule();
			}
		}
		return null;
	}
}
