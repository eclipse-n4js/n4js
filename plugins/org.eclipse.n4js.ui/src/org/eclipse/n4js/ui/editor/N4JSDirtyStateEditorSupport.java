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
package org.eclipse.n4js.ui.editor;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.resource.UserDataMapper;
import org.eclipse.n4js.scoping.utils.CanLoadFromDescriptionHelper;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Event;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.DirtyStateEditorSupport;
import org.eclipse.xtext.ui.editor.SchedulingRuleFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * Used to use a custom update job that is triggered when a change in the project happened. It has to be customized as
 * otherwise the job family cannot be accessed.
 */
public class N4JSDirtyStateEditorSupport extends DirtyStateEditorSupport {
	private static ISchedulingRule SCHEDULING_RULE = SchedulingRuleFactory.INSTANCE.newSequence();
	/**
	 * The family of the update job that is spawned by the state change event.
	 */
	public static final Object FAMILY_UPDATE_JOB = new Object();
	private N4JSUpdateEditorStateJob updateEditorStateJob;

	private class N4JSUpdateEditorStateJob extends DirtyStateEditorSupport.UpdateEditorStateJob {

		N4JSUpdateEditorStateJob(ISchedulingRule rule) {
			super(rule);
		}

		@Override
		public boolean belongsTo(Object family) {
			return FAMILY_UPDATE_JOB == family;
		}

		@Override
		protected void scheduleFor(IResourceDescription.Event event) {
			super.scheduleFor(event);
		}
	}

	@Inject
	private CanLoadFromDescriptionHelper canLoadFromDescriptionHelper;

	@Inject
	private IResourceDescriptions dirtyState;

	@Override
	protected N4JSUpdateEditorStateJob createUpdateEditorJob() {
		return new N4JSUpdateEditorStateJob(SCHEDULING_RULE);
	}

	@Override
	public void scheduleUpdateEditorJob(final IResourceDescription.Event event) {
		N4JSUpdateEditorStateJob job = updateEditorStateJob;
		if (job == null) {
			job = createUpdateEditorJob();
			updateEditorStateJob = job;
		}
		job.scheduleFor(event);
	}

	@Override
	protected Collection<Resource> collectAffectedResources(XtextResource resource, Event event) {
		List<Resource> result = doCollectAffectedResources(resource, event);
		markResourcesAsLoaded(result);
		return result;
	}

	/**
	 * We need to mark all the resources as loaded. Otherwise a subsequent call to unload would be meaningless and
	 * ignored.
	 */
	private void markResourcesAsLoaded(List<Resource> resources) {
		Iterator<Resource> iter = resources.iterator();
		while (iter.hasNext()) {
			Resource affected = iter.next();
			if (affected instanceof N4JSResource) {
				((N4JSResource) affected).forceSetLoaded();
			}
		}
	}

	private List<Resource> doCollectAffectedResources(XtextResource resource, Event event) {
		Set<URI> deltaURIs = collectDeltaURIs(event);
		List<Resource> result = collectTransitivelyDependentResources(resource, deltaURIs);
		return result;
	}

	private List<Resource> collectTransitivelyDependentResources(XtextResource resource,
			Set<URI> deltaURIs) {
		List<Resource> result = Lists.newArrayList();
		ResourceSet resourceSet = resource.getResourceSet();
		for (Resource candidate : resourceSet.getResources()) {
			if (candidate != resource) {
				URI uri = candidate.getURI();
				if (deltaURIs.contains(uri)) {
					// the candidate is contained in the delta list
					// schedule it for unloading
					result.add(candidate);
				} else if (candidate instanceof N4JSResource) {
					// the candidate does depend on one of the changed resources
					// schedule it for unloading
					if (canLoadFromDescriptionHelper.dependsOnAny(candidate, deltaURIs)) {
						result.add(candidate);
					}
				}
			}
		}
		return result;
	}

	private Set<URI> collectDeltaURIs(Event event) {
		Set<URI> deltaURIs = Sets.newHashSet();
		for (IResourceDescription.Delta delta : event.getDeltas()) {
			deltaURIs.add(delta.getUri());
		}
		return deltaURIs;
	}

	private boolean isSignificantChange(IResourceDescription.Delta delta) {
		if (delta.haveEObjectDescriptionsChanged()) {
			IResourceDescription newDescription = delta.getNew();
			IResourceDescription oldDescription = delta.getOld();
			if ((newDescription != null) != (oldDescription != null)) {
				return true;
			}
			if (newDescription == null || oldDescription == null) {
				throw new IllegalStateException();
			}
			List<IEObjectDescription> newDescriptions = Lists.newArrayList(newDescription.getExportedObjects());
			List<IEObjectDescription> oldDescriptions = Lists.newArrayList(oldDescription.getExportedObjects());
			if (newDescriptions.size() != oldDescriptions.size()) {
				return true;
			}
			URI resourceURI = delta.getUri();
			for (int i = 0; i < newDescriptions.size(); i++) {
				if (!equalDescriptions(newDescriptions.get(i), oldDescriptions.get(i), resourceURI)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean equalDescriptions(IEObjectDescription newDescription,
			IEObjectDescription oldDescription, URI uri) {
		if (!newDescription.getQualifiedName().equals(oldDescription.getQualifiedName())) {
			return false;
		}
		if (!newDescription.getEClass().equals(oldDescription.getEClass())) {
			return false;
		}
		if (!newDescription.getEObjectURI().equals(oldDescription.getEObjectURI())) {
			return false;
		}
		if (TypesPackage.Literals.TMODULE == newDescription.getEClass()) {
			String newModule = newDescription.getUserData(UserDataMapper.USER_DATA_KEY_SERIALIZED_SCRIPT);
			String oldModule = oldDescription.getUserData(UserDataMapper.USER_DATA_KEY_SERIALIZED_SCRIPT);
			if (newModule == null || oldModule == null) {
				return true;
			}
			if (!newModule.equals(oldModule)) {
				TModule newModuleObj = UserDataMapper.getDeserializedModuleFromDescription(newDescription, uri);
				TModule oldModuleObj = UserDataMapper.getDeserializedModuleFromDescription(oldDescription, uri);
				// we deserialize the TModules and ignore the MD5 Hash
				newModuleObj.setAstMD5("");
				oldModuleObj.setAstMD5("");
				if (!EcoreUtilN4.equalsNonResolving(newModuleObj, oldModuleObj)) {
					return false;
				}
			}
		}
		// todo compare user data if module
		return true;
	}

	@Override
	public void modelChanged(XtextResource resource) {
		if (resource == null || !getDirtyResource().isInitialized())
			return;
		resource.getContents(); // trigger init
		super.modelChanged(resource);
	}

	@Override
	public void descriptionsChanged(final IResourceDescription.Event event) {
		if (!getDirtyResource().isInitialized())
			return;
		for (IResourceDescription.Delta delta : event.getDeltas()) {
			if (delta.getOld() == getDirtyResource().getDescription()
					|| delta.getNew() == getDirtyResource().getDescription()) {
				// usually we ignore events from this resource itself, but when it is part
				// of a dependency cycle, the event may affect other resources in the same
				// resource set thus we schedule the event in that case
				if (canLoadFromDescriptionHelper.isPartOfDependencyCycle(delta.getUri(), dirtyState)) {
					scheduleUpdateEditorJob(event);
				}
				return;
			}
		}

		for (IResourceDescription.Delta delta : event.getDeltas()) {
			if (isSignificantChange(delta)) {
				scheduleUpdateEditorJob(event);
				return;
			}
		}
	}
}
