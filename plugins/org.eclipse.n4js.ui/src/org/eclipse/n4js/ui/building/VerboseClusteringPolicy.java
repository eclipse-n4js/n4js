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
package org.eclipse.n4js.ui.building;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.clustering.DynamicResourceClusteringPolicy;

/**
 * Customized clustering policy that will at least process 100 resources per cluster. It also logs eagerly when the
 * cluster size is capped.
 */
@SuppressWarnings("restriction")
public class VerboseClusteringPolicy extends DynamicResourceClusteringPolicy {

	private static final Logger LOGGER = Logger.getLogger(VerboseClusteringPolicy.class);

	@Override
	public boolean continueProcessing(ResourceSet resourceSet, URI next, int alreadyProcessed) {
		if (alreadyProcessed < 100) {
			return true;
		}
		return super.continueProcessing(resourceSet, next, alreadyProcessed);
	}

	@Override
	protected void logClusterCapped(ResourceSet resourceSet, int alreadyProcessed, long freeMemory,
			long totalMemory) {
		LOGGER.info(
				"Cluster capped at " + alreadyProcessed + '/' + resourceSet.getResources().size()
						+ " processed/loaded resources; " + (freeMemory >> 20) + "/" + (totalMemory >> 20)
						+ " free/total memory");
	}
}