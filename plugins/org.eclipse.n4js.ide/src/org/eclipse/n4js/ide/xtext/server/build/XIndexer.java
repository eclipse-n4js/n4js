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
import java.util.HashSet;
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
import org.eclipse.xtext.xbase.lib.ListExtensions;
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
	 * Compute an updated index.
	 */
	public XIndexer.XIndexResult computeAndIndexAffected(XBuildRequest request,
			@Extension XBuildContext context) {
		ResourceDescriptionsData previousIndex = context.getOldState().getResourceDescriptions();
		ResourceDescriptionsData newIndex = request.getState().getResourceDescriptions();
		List<IResourceDescription.Delta> deltas = new ArrayList<>();
		deltas.addAll(getDeltasForDeletedResources(request, previousIndex, context));
		deltas.addAll(getDeltasForChangedResources(request.getDirtyFiles(), previousIndex, context));
		for (IResourceDescription.Delta delta : deltas) {
			newIndex.register(delta);
		}
		HashSet<IResourceDescription.Delta> allDeltas = new HashSet<>(deltas);
		allDeltas.addAll(request.getExternalDeltas());
		Set<URI> remainingURIs = IterableExtensions.toSet(
				IterableExtensions.map(previousIndex.getAllResourceDescriptions(), IResourceDescription::getURI));
		remainingURIs.removeAll(ListExtensions.map(deltas, Delta::getUri));
		List<URI> allAffected = IterableExtensions.toList(IterableExtensions.filter(remainingURIs, it -> {
			IResourceDescription.Manager manager = context.getResourceServiceProvider(it)
					.getResourceDescriptionManager();
			IResourceDescription resourceDescription = previousIndex.getResourceDescription(it);
			return isAffected(resourceDescription, manager, allDeltas, allDeltas, newIndex);
		}));
		deltas.addAll(this.getDeltasForChangedResources(allAffected, previousIndex, context));
		return new XIndexer.XIndexResult(deltas, newIndex);
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
				if ((oldDescription != null)) {
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
	protected List<IResourceDescription.Delta> getDeltasForChangedResources(Iterable<URI> affectedUris,
			ResourceDescriptionsData oldIndex, XBuildContext context) {
		try {
			this.compilerPhases.setIndexing(context.getResourceSet(), true);
			return IterableExtensions
					.toList(context.executeClustered(affectedUris, it -> addToIndex(it, true, oldIndex, context)));
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
		if ((manager instanceof IResourceDescription.Manager.AllChangeAware)) {
			return ((IResourceDescription.Manager.AllChangeAware) manager).isAffectedByAny(allDeltas,
					affectionCandidate, resourceDescriptions);
		} else {
			if (newDeltas.isEmpty()) {
				return false;
			} else {
				return manager.isAffected(newDeltas, affectionCandidate, resourceDescriptions);
			}
		}
	}
}
