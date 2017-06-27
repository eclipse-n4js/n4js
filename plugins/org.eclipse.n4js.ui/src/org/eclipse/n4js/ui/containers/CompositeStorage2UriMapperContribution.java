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
package org.eclipse.n4js.ui.containers;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.ui.resource.IStorage2UriMapperContribution;
import org.eclipse.xtext.util.Pair;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * Composite {@link IStorage storage} to {@link URI URI} mapper contribution.
 *
 * This implementation does nothing but delegates to all instances that are created from the contributing classes its
 * all injected fields that are assignable to {@link IStorage2UriMapperContribution} class.
 */
@Singleton
public class CompositeStorage2UriMapperContribution implements IStorage2UriMapperContribution {

	private static final Iterable<Class<? extends IStorage2UriMapperContribution>> CONTRIBUTING_CLASSES = ImmutableSet
			.<Class<? extends IStorage2UriMapperContribution>> builder()
			.add(NfarStorageMapper.class)
			.add(N4JSExternalLibraryStorage2UriMapperContribution.class)
			.build();

	@Inject
	private Injector injector;

	private final Collection<IStorage2UriMapperContribution> contributions = newArrayList();

	@Override
	public void initializeCache() {

		for (final Class<? extends IStorage2UriMapperContribution> clazz : CONTRIBUTING_CLASSES) {
			final IStorage2UriMapperContribution contribution = injector.getInstance(clazz);
			if (null != contribution) {
				contributions.add(contribution);
			}
		}

		for (final IStorage2UriMapperContribution contribution : contributions) {
			contribution.initializeCache();
		}
	}

	@Override
	public boolean isRejected(final IFolder folder) {
		return Iterables.all(contributions, c -> c.isRejected(folder));
	}

	@Override
	public Iterable<Pair<IStorage, IProject>> getStorages(final URI uri) {
		return from(contributions).transformAndConcat(c -> c.getStorages(uri));
	}

	@Override
	public URI getUri(final IStorage storage) {
		for (final IStorage2UriMapperContribution contribution : contributions) {
			final URI uri = contribution.getUri(storage);
			if (null != uri) {
				return uri;
			}
		}
		return null;
	}

}
