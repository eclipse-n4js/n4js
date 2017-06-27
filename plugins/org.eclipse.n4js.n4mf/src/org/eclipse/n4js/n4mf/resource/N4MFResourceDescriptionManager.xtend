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
package org.eclipse.n4js.n4mf.resource

import org.eclipse.n4js.n4mf.N4mfPackage
import java.util.Collection
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.IResourceDescription.Delta
import org.eclipse.xtext.resource.IResourceDescriptions
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionManager

import static com.google.common.base.Preconditions.checkArgument
import static org.eclipse.n4js.n4mf.utils.N4MFConstants.N4MF_MANIFEST

import static extension org.eclipse.n4js.n4mf.resource.N4MFResourceDescriptionStrategy.*

/**
 * Resource description manager for N4JS manifests. Considers a {@link Delta change} as relevant to the current
 * candidate if both are pointing to a manifest resource.
 */
class N4MFResourceDescriptionManager extends DefaultResourceDescriptionManager {

	@Override
	override boolean isAffected(Collection<Delta> deltas, IResourceDescription candidate, IResourceDescriptions context) {

		if (candidate.manifest) {
			// Contains only those project IDs that were changed via its N4JS manifest.
			val changedProjectIds = deltas.map[uri].filter[isManifest].map[projectIdFromManifestUri].toSet;

			// Collect all referenced project IDs of the candidate.
			val referencedProjectIds = newLinkedList;
			candidate.getExportedObjectsByType(N4mfPackage.eINSTANCE.projectDescription).forEach[
				referencedProjectIds.addAll(testedProjectIds);
				referencedProjectIds.addAll(implementedProjectIds);
				referencedProjectIds.addAll(projectDependencyIds);
				referencedProjectIds.addAll(providedRuntimeLibraryIds);
				referencedProjectIds.addAll(requiredRuntimeLibraryIds);
				val extRuntimeEnvironmentId = extendedRuntimeEnvironmentId;
				if (!extRuntimeEnvironmentId.nullOrEmpty) {
					referencedProjectIds.add(extRuntimeEnvironmentId);
				}
			];

			// Here we consider only direct project dependencies because this implementation is aligned to the
			// N4JS based resource description manager's #isAffected logic. In the N4JS implementation we consider
			// only direct project dependencies when checking whether a candidate is affected or not.
			//
			// See: N4JSResourceDescriptionManager#basicIsAffected and N4JSResourceDescriptionManager#hasDependencyTo
			for (referencedProjectId : referencedProjectIds) {
				if (changedProjectIds.contains(referencedProjectId)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Returns with the projectId of an N4JS project by appending the second segment from the end of a N4JS manifest URI argument.
	 * This method only works for N4JS manifest URIs and throws {@link IllegalArgumentException} for all other URIs.
	 * Since this method accepts only N4JS manifest URIs it is guaranteed to get the container project name as the second URI
	 * segment from the end. We cannot simply grab and return with the first segment as the project name, because external
	 * projects have a file URI with an absolute path that can be any arbitrary location on the file system.
	 *
	 * The ultimate solution would be to look up the container N4JS project from the nested URI argument and simply get
	 * the project ID of the project but due to plug-in dependency issues N4JS core service is not available from here.
	 *
	 */
	private def getProjectIdFromManifestUri(URI it) {
		checkArgument(isManifest, '''Expected only URI that has «N4MF_MANIFEST» as last segment. Was: «it»''');
		return segment(segmentCount - 2);
	}

	private def isManifest(URI it) {
		null !== it && N4MF_MANIFEST == lastSegment;
	}

	private def isManifest(IResourceDescription it) {
		null !== it && N4MF_MANIFEST.equals(URI.lastSegment);
	}

}
