/**
 * Copyright (c) 2015, 2016 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.n4js.ide.xtext.server.build;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.CompilerPhases;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescription.Manager.AllChangeAware;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.impl.AbstractResourceDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.resource.persistence.SerializableEObjectDescriptionProvider;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.inject.Inject;

/**
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.9
 */
@SuppressWarnings("restriction")
public class XIndexer {
	/**
	 * The result of an indexing operation.
	 */
	public static class XIndexResult {
		private final List<IResourceDescription.Delta> resourceDeltas;

		private final ResourceDescriptionsData newIndex;

		/**
		 * Constructor.
		 */
		public XIndexResult(List<IResourceDescription.Delta> resourceDeltas,
				ResourceDescriptionsData newIndex) {
			super();
			this.resourceDeltas = resourceDeltas;
			this.newIndex = newIndex;
		}

		/**
		 * Getter
		 */
		public List<IResourceDescription.Delta> getResourceDeltas() {
			return this.resourceDeltas;
		}

		/**
		 * Getter
		 */
		public ResourceDescriptionsData getNewIndex() {
			return this.newIndex;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + ((this.resourceDeltas == null) ? 0 : this.resourceDeltas.hashCode());
			return prime * result + ((this.newIndex == null) ? 0 : this.newIndex.hashCode());
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			XIndexer.XIndexResult other = (XIndexer.XIndexResult) obj;
			if (this.resourceDeltas == null) {
				if (other.resourceDeltas != null)
					return false;
			} else if (!this.resourceDeltas.equals(other.resourceDeltas))
				return false;
			if (this.newIndex == null) {
				if (other.newIndex != null)
					return false;
			} else if (!this.newIndex.equals(other.newIndex))
				return false;
			return true;
		}

		@Override
		public String toString() {
			ToStringBuilder b = new ToStringBuilder(this);
			b.add("resourceDeltas", this.resourceDeltas);
			b.add("newIndex", this.newIndex);
			return b.toString();
		}

	}

	/**
	 * A resource description that has no references to the origin resource anymore. Imported names and reference
	 * descriptions are not available.
	 */
	protected static class XResolvedResourceDescription extends AbstractResourceDescription {

		private static final Logger LOG = Logger.getLogger(XResolvedResourceDescription.class);

		private URI URI;

		private ImmutableList<IEObjectDescription> exported;

		/**
		 * Constructor
		 */
		public XResolvedResourceDescription(IResourceDescription original) {
			this.URI = original.getURI();
			this.exported = ImmutableList.copyOf(IterableExtensions.map(original.getExportedObjects(), (from) -> {
				if (from instanceof SerializableEObjectDescriptionProvider) {
					return ((SerializableEObjectDescriptionProvider) from).toSerializableEObjectDescription();
				}
				if (from.getEObjectOrProxy().eIsProxy()) {
					return from;
				}
				InternalEObject result = ((InternalEObject) EcoreUtil.create(from.getEClass()));
				result.eSetProxyURI(from.getEObjectURI());
				Map<String, String> userData = null;
				String[] userDataKeys = from.getUserDataKeys();
				for (String key : userDataKeys) {
					if (userData == null) {
						userData = Maps.newHashMapWithExpectedSize(userDataKeys.length);
					}
					userData.put(key, from.getUserData(key));
				}
				return EObjectDescription.create(from.getName(), result, userData);
			}));
		}

		@Override
		protected List<IEObjectDescription> computeExportedObjects() {
			return this.exported;
		}

		@Override
		public Iterable<QualifiedName> getImportedNames() {
			IllegalStateException exception = new IllegalStateException("getImportedNames" + getURI());
			LOG.error(exception, exception);
			return CollectionLiterals.<QualifiedName> emptyList();
		}

		@Override
		public Iterable<IReferenceDescription> getReferenceDescriptions() {
			IllegalStateException exception = new IllegalStateException("getReferenceDescriptions" + getURI());
			LOG.error(exception, exception);
			return CollectionLiterals.<IReferenceDescription> emptyList();
		}

		@Override
		public URI getURI() {
			return this.URI;
		}
	}

	@Inject
	private CompilerPhases compilerPhases;

	@Inject
	@Extension
	private OperationCanceledManager operationCanceledManager;

	/**
	 * Compute deltas for the build's initial resource deletions and changes as recorded in the given build request, and
	 * register them in the request's index.
	 */
	public XIndexer.XIndexResult computeAndIndexDeletedAndChanged(XBuildRequest request, XBuildContext context) {
		ResourceDescriptionsData previousIndex = context.getOldState().getResourceDescriptions();
		ResourceDescriptionsData newIndex = request.getState().getResourceDescriptions();
		List<IResourceDescription.Delta> deltas = new ArrayList<>();
		deltas.addAll(getDeltasForDeletedResources(request, previousIndex, context));
		deltas.addAll(getDeltasForChangedResources(request.getDirtyFiles(), previousIndex, context));
		for (IResourceDescription.Delta delta : deltas) {
			newIndex.register(delta);
		}
		return new XIndexer.XIndexResult(deltas, newIndex);
	}

	/**
	 * Compute deltas for resources affected by the given <code>newDeltas</code> and register them in the given
	 * <code>newIndex</code>.
	 *
	 * @param index
	 *            the current index; will be changed by this method.
	 * @param remainingURIs
	 *            set of URIs that were not processed yet.
	 * @param newDeltas
	 *            deltas representing the resources processed during the most recent build iteration.
	 * @param allDeltas
	 *            deltas representing all resources processed so far, including {@link XBuildRequest#getExternalDeltas()
	 *            external deltas}.
	 * @param context
	 *            the build context.
	 * @return list of deltas representing the affected resources.
	 */
	public List<Delta> computeAndIndexAffected(ResourceDescriptionsData index, Set<URI> remainingURIs,
			Collection<Delta> newDeltas, Collection<Delta> allDeltas, XBuildContext context) {

		ResourceDescriptionsData originalIndex = context.getOldState().getResourceDescriptions();
		List<URI> affectedURIs = new ArrayList<>();
		for (URI uri : remainingURIs) {
			IResourceServiceProvider resourceServiceProvider = context.getResourceServiceProvider(uri);
			IResourceDescription.Manager manager = resourceServiceProvider.getResourceDescriptionManager();
			IResourceDescription resourceDescription = originalIndex.getResourceDescription(uri);
			if (isAffected(resourceDescription, manager, newDeltas, allDeltas, index)) {
				affectedURIs.add(uri);
			}
		}

		List<Delta> affectedDeltas = getDeltasForChangedResources(affectedURIs, originalIndex, context);
		for (IResourceDescription.Delta delta : affectedDeltas) {
			index.register(delta);
		}
		return affectedDeltas;
	}

	/**
	 * Process the deleted resources.
	 */
	protected List<IResourceDescription.Delta> getDeltasForDeletedResources(XBuildRequest request,
			ResourceDescriptionsData oldIndex, XBuildContext context) {

		List<IResourceDescription.Delta> deltas = new ArrayList<>();
		for (URI deleted : request.getDeletedFiles()) {
			IResourceServiceProvider resourceServiceProvider = context.getResourceServiceProvider(deleted);
			if (resourceServiceProvider != null) {
				this.operationCanceledManager.checkCanceled(context.getCancelIndicator());
				IResourceDescription oldDescription = oldIndex != null ? oldIndex.getResourceDescription(deleted)
						: null;
				if (oldDescription != null) {
					DefaultResourceDescriptionDelta delta = new DefaultResourceDescriptionDelta(oldDescription, null);
					deltas.add(delta);
				}
			}
		}
		return deltas;
	}

	/**
	 * Process the changed resources.
	 */
	protected List<IResourceDescription.Delta> getDeltasForChangedResources(Iterable<URI> changedURIs,
			ResourceDescriptionsData oldIndex, XBuildContext context) {
		try {
			this.compilerPhases.setIndexing(context.getResourceSet(), true);
			List<IResourceDescription.Delta> result = new ArrayList<>();
			List<Delta> deltas = context.executeClustered(changedURIs, it -> addToIndex(it, true, oldIndex, context));
			for (IResourceDescription.Delta delta : deltas) {
				if (delta != null) {
					result.add(delta);
				}
			}
			return result;
		} finally {
			this.compilerPhases.setIndexing(context.getResourceSet(), false);
		}
	}

	/**
	 * Index the given resource.
	 *
	 * @param isPreIndexing
	 *            can be evaluated to produce different index entries depending on the phase
	 */
	protected IResourceDescription.Delta addToIndex(Resource resource, boolean isPreIndexing,
			ResourceDescriptionsData oldIndex, XBuildContext context) {
		this.operationCanceledManager.checkCanceled(context.getCancelIndicator());
		if (context.getResourceSet() != resource.getResourceSet()) {
			// we are seeing an out-of-sequence resource - don't index it
			return null;
		}
		URI uri = resource.getURI();
		IResourceServiceProvider serviceProvider = context.getResourceServiceProvider(uri);
		IResourceDescription.Manager manager = serviceProvider.getResourceDescriptionManager();
		IResourceDescription newDescription = manager.getResourceDescription(resource);
		IResourceDescription toBeAdded = new XIndexer.XResolvedResourceDescription(newDescription);
		IResourceDescription.Delta delta = manager
				.createDelta(oldIndex != null ? oldIndex.getResourceDescription(uri) : null, toBeAdded);
		return delta;
	}

	/**
	 * Return true, if the given resource must be processed due to the given changes.
	 */
	protected boolean isAffected(IResourceDescription affectionCandidate,
			IResourceDescription.Manager manager, Collection<IResourceDescription.Delta> newDeltas,
			Collection<IResourceDescription.Delta> allDeltas, IResourceDescriptions resourceDescriptions) {

		if (manager instanceof IResourceDescription.Manager.AllChangeAware) {
			AllChangeAware allChangeAwareManager = (IResourceDescription.Manager.AllChangeAware) manager;
			return allChangeAwareManager.isAffectedByAny(allDeltas, affectionCandidate, resourceDescriptions);
		} else {
			if (newDeltas.isEmpty()) {
				return false;
			} else {
				return manager.isAffected(newDeltas, affectionCandidate, resourceDescriptions);
			}
		}
	}
}
