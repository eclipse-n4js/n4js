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
package org.eclipse.n4js.tooling.findReferences;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.findReferences.TargetURICollector;
import org.eclipse.xtext.findReferences.TargetURIs;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.impl.DefaultReferenceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;

import org.eclipse.n4js.ts.findReferences.SimpleResourceAccess;

/**
 * A reference finder that does not need any user interaction. This may be used during the build.
 */
@SuppressWarnings("restriction")
public class HeadlessReferenceFinder {

	@Inject
	private IReferenceFinder referenceFinder;

	@Inject
	private Provider<TargetURIs> targetURIsProvider;

	@Inject
	private TargetURICollector uriCollector;

	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	/**
	 * Find all references to the given target with its resource set as working environment. Equivalent to
	 * {@code findReferencesTo(target, null)}.
	 *
	 * @param target
	 *            the object to look for.
	 * @return the list of reference descriptions.
	 */
	public List<IReferenceDescription> findReferencesTo(EObject target) {
		return findReferencesTo(target, null);
	}

	/**
	 * Find all references to the given target with its resource set as working environment.
	 *
	 * @param target
	 *            the object to look for.
	 * @param monitor
	 *            the progress monitor.
	 * @return the list of reference descriptions.
	 */
	public List<IReferenceDescription> findReferencesTo(EObject target, IProgressMonitor monitor) {
		final TargetURIs targetURIs = getTargetURIs(target);
		final ResourceSet resourceSet = target.eResource().getResourceSet();
		final List<IReferenceDescription> result = Lists.newArrayList();
		IReferenceFinder.IResourceAccess resourceAccess = new SimpleResourceAccess(resourceSet);
		IReferenceFinder.Acceptor acceptor = new IReferenceFinder.Acceptor() {

			@Override
			public void accept(IReferenceDescription description) {
				result.add(description);
			}

			@Override
			public void accept(EObject source, URI sourceURI, EReference eReference, int index, EObject targetOrProxy,
					URI targetURI) {
				accept(new DefaultReferenceDescription(sourceURI, targetURI, eReference, index, null));
			}
		};
		referenceFinder.findAllReferences(targetURIs, resourceAccess,
				resourceDescriptionsProvider.getResourceDescriptions(resourceSet),
				acceptor, monitor);
		return result;
	}

	private TargetURIs getTargetURIs(EObject primaryTarget) {
		TargetURIs result = targetURIsProvider.get();
		uriCollector.add(primaryTarget, result);
		return result;
	}
}
