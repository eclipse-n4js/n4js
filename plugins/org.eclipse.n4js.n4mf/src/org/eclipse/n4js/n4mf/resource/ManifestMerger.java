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
package org.eclipse.n4js.n4mf.resource;

import static com.google.common.collect.FluentIterable.from;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectReference;
import org.eclipse.n4js.utils.emf.EObjectFeatureMerger;

/**
 * Singleton service class for merging the content of N4JS manifests.
 */
@Singleton
public class ManifestMerger extends EObjectFeatureMerger {

	private static final Logger LOGGER = Logger.getLogger(ManifestMerger.class);

	// TODO consider splitting this up to headless and IDE cases.
	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	/**
	 * Merges the content of two {@link ProjectDescription project description} instances that are representing the
	 * actual N4JS manifests.
	 *
	 * @param fromLocation
	 *            the source location. These attributes and references will be merged to the other one given with the
	 *            {@code toLocation}.
	 * @param toLocation
	 *            the target location. The project description that has to be updated with the content of the
	 *            {@code fromLocation}.
	 * @return the merged project description that has been detached from its resource.
	 */
	public ProjectDescription mergeContent(final URI fromLocation, final URI toLocation) {

		final XtextResourceSet fromResourceSet = getResourceSet(fromLocation);
		final XtextResourceSet toResourceSet = getResourceSet(toLocation);
		if (null == fromResourceSet || null == toResourceSet) {
			return null;
		}

		try {

			final Resource from = fromResourceSet.getResource(fromLocation, true);
			final Resource to = toResourceSet.getResource(toLocation, true);
			return mergeContent(from, to);

		} catch (final Exception e) {
			LOGGER.error("Error while trying to merge N4JS manifest content. Source URI: " + fromLocation
					+ ". Target URI: " + toLocation + ".", e);
		}
		return null;
	}

	/**
	 * Merges the content of two {@link ProjectDescription project description} instances that are representing the
	 * actual N4JS manifests.
	 *
	 * @param from
	 *            the source resource. These attributes and references will be merged to the other one given with the
	 *            {@code to} argument.
	 * @param to
	 *            the target resource. The project description that has to be updated with the content of the
	 *            {@code from} argument.
	 * @return the merged project description that has been detached from its resource.
	 */
	public ProjectDescription mergeContent(final Resource from, final Resource to) {

		try {

			final EObject fromContent = from.getContents().get(0);
			final EObject toContent = to.getContents().get(0);

			if (fromContent instanceof ProjectDescription && toContent instanceof ProjectDescription) {
				return (ProjectDescription) merge(fromContent, toContent);
			}

			return null;

		} finally {
			from.getContents().clear();
			to.getContents().clear();
		}

	}

	/**
	 * Returns with the resource set for loading the Xtext resources.
	 *
	 * @param location
	 *            the location of the resource which resource set should be returned.
	 *
	 * @return the resource set to get the resources.
	 */
	protected XtextResourceSet getResourceSet(final URI location) {
		return resourceSetProvider.get();
	}

	@Override
	protected boolean contains(final Collection<? extends Object> toManyValue, final Object fromElement) {
		if (fromElement instanceof ProjectReference) {
			final String id = ((ProjectReference) fromElement).getProject().getProjectId();
			return from(toManyValue).filter(ProjectReference.class)
					.transform(ref -> ref.getProject().getProjectId())
					.contains(id);
		}
		return super.contains(toManyValue, fromElement);
	}

}
