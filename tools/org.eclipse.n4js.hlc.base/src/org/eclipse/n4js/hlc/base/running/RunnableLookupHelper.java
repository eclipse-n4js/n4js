/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.hlc.base.running;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import org.eclipse.n4js.generator.headless.logging.IHeadlessLogger;
import org.eclipse.n4js.runner.extension.IRunnableDescriptor;

import com.google.inject.Inject;

/**
 * Lookup helper for subtypes of the {@link IRunnableDescriptor}.
 */
@Singleton
public class RunnableLookupHelper {
	@Inject
	private IHeadlessLogger logger;

	/**
	 * Lookup that will use provided map with runnables to find one matching provided ID.
	 * <p>
	 * First tries to get runnable from map treating provided id as the key. It is assumed that
	 * {@code map.key == map.get(key).getId}). Lookup will return element from the map regardless if the assumption is
	 * true.
	 * <p>
	 * If method above fails, lookup will iterate over keys in the map and compare them with the provided id. It will
	 * return <b>all</b> elements for which {@link IRunnableDescriptor#getId()} will return id that matches or ends with
	 * {@code .runnableID} (case insensitive comparison).
	 *
	 * @param runnableID
	 *            id used to the runnable
	 * @param descriptors
	 *            mapping between {@code IDs} and {@link IRunnableDescriptor}
	 * @return list of matched descriptors.
	 */
	public <T extends IRunnableDescriptor> List<T> findRunnableById(
			String runnableID,
			Map<String, T> descriptors) {

		// early return
		if (runnableID == null || runnableID.trim().isEmpty()) {
			logger.warn("Expecting non null, non empty, non whitespace runnable ID. Was <" + runnableID + ">.");
			return Collections.emptyList();
		}

		// exact match
		final T rd = descriptors.get(runnableID);
		if (rd != null) {
			if (runnableID != rd.getId())
				logger.warn("Inconsistent mapping of the runnable. ID used as key in the map <" + runnableID
						+ ">, id in the runnable <" + rd.getId() + ">");
			return Collections.singletonList(rd);
		}

		// fuzzy match
		logger.info("Could not find runnable by ID: " + runnableID + ", switching to fuzzy search.");
		final String lowerRunner = runnableID.toLowerCase();
		final List<T> matchingRDs = descriptors.values().stream()
				.filter(descriptor -> {
					final String lowerID = descriptor.getId().toLowerCase();
					return lowerID.equals(runnableID) || lowerID.endsWith("." + lowerRunner);
				})
				.collect(Collectors.toList());
		return matchingRDs;
	}

}
