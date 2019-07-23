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
package org.eclipse.n4js.resource;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.fileextensions.FileExtensionTypeHelper;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ts.utils.TypeHelper;
import org.eclipse.n4js.utils.N4JSLanguageHelper;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.DerivedStateAwareResourceDescriptionManager;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.EObjectDescriptionLookUp;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Only differences to super class method are that a {@link N4JSResourceDescription} is created as well the call to
 * getCache() instead of directly accessing the property cache. The cast to {@link N4JSResourceDescriptionStrategy} is
 * only a double check that the correct resource description strategy is bound in the runtime module.
 *
 * Furthermore, this manager implementation configures an N4JS specific
 * {@link org.eclipse.xtext.resource.IResourceDescription.Delta} implementation to customize the builder behavior.
 */
@Singleton
public class N4JSResourceDescriptionManager extends DerivedStateAwareResourceDescriptionManager implements N4Scheme {

	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;

	@Inject
	private TypeHelper typeHelper;

	@Inject
	private N4JSCrossReferenceComputer crossReferenceComputer;

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private FileExtensionTypeHelper fileExtensionTypeHelper;

	@Inject
	private N4JSLanguageHelper langHelper;

	@Override
	protected IResourceDescription createResourceDescription(final Resource resource,
			IDefaultResourceDescriptionStrategy strategy) {
		return new N4JSResourceDescription(crossReferenceComputer, typeHelper,
				qualifiedNameProvider, resource,
				(N4JSResourceDescriptionStrategy) strategy,
				getCache()) {
			@Override
			protected EObjectDescriptionLookUp getLookUp() {
				if (lookup == null)
					lookup = new EObjectDescriptionLookUp(computeExportedObjects());
				return lookup;
			}
		};
	}

	@Override
	public Delta createDelta(IResourceDescription oldDescription, IResourceDescription newDescription) {
		return new N4JSResourceDescriptionDelta(oldDescription, newDescription);
	}

	/**
	 * {@inheritDoc}
	 *
	 * This marks {@code n4js} as affected if the manifest of the project changes. In turn, they will be revalidated and
	 * taken into consideration for the code generation step.
	 */
	@Override
	public boolean isAffected(Collection<IResourceDescription.Delta> deltas, IResourceDescription candidate,
			IResourceDescriptions context) {
		URI candidateURI = candidate.getURI();

		// Opaque modules cannot contain any references to one of the deltas.
		// Thus, they will never be affected by any change.
		if (langHelper.isOpaqueModule(candidateURI)) {
			return false;
		}

		boolean result = basicIsAffected(deltas, candidate);
		if (!result) {
			for (IResourceDescription.Delta delta : deltas) {
				URI uri = delta.getUri();
				// if uri looks like a N4JS project description file (i.e. package.json)
				if (IN4JSProject.PACKAGE_JSON.equalsIgnoreCase(uri.lastSegment())) {
					URI prefixURI = uri.trimSegments(1).appendSegment("");
					if (candidateURI.replacePrefix(prefixURI, prefixURI) != null) {
						return true;
					}
				}
			}
		}
		return result;
	}

	/**
	 * Computes if a candidate is affected by any change, aka delta. It is affected, if
	 */
	private boolean basicIsAffected(Collection<Delta> deltas, final IResourceDescription candidate) {
		// The super implementation DefaultResourceDescriptionManager#isAffected is based on a tradeoff / some
		// assumptions which do not hold for n4js wrt to manifest changes

		// computed the first time we need it, do not compute eagerly
		Collection<QualifiedName> namesImportedByCandidate = null;

		for (IResourceDescription.Delta delta : deltas) {
			if (delta.haveEObjectDescriptionsChanged()
					&& fileExtensionTypeHelper.isTypable(delta.getUri().fileExtension())) {

				if (null == namesImportedByCandidate) {
					// note: this does not only contain the explicitly imported names, but indirectly
					// imported names as well!
					namesImportedByCandidate = getImportedNames(candidate);
				}

				if (isAffected(namesImportedByCandidate, delta.getNew()) // we may added a new exported name!
						|| isAffected(namesImportedByCandidate, delta.getOld())) { // we may removed an exported name
					if (hasDependencyTo(candidate, delta)) { // isAffected does not compare project names
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Returns true iff project containing the 'candidate' has a direct dependency to the project containing the
	 * 'delta'.
	 */
	private boolean hasDependencyTo(IResourceDescription candidate, IResourceDescription.Delta delta) {
		return hasDependencyTo(candidate.getURI(), delta.getUri());
	}

	/**
	 * Returns true iff the project containing the 'fromUri' has a direct dependency to the project containing the
	 * 'toUri'.
	 */
	private boolean hasDependencyTo(URI fromUri, URI toUri) {
		final IN4JSProject fromProject = n4jsCore.findProject(fromUri).orNull();
		final IN4JSProject toProject = n4jsCore.findProject(toUri).orNull();

		if (null != fromProject && null != toProject) { // Consider libraries. TODO: implement it at #findProject(URI)

			if (Objects.equals(fromProject, toProject)) {
				return true;
			}

			for (IN4JSProject fromProjectDependency : fromProject.getDependenciesAndImplementedApis()) {

				// Never mark a resource as effected when trying to resolve its dependency from an external to a
				// workspace one and/or vice versa.
				if (fromProjectDependency.isExternal() == fromProject.isExternal()
						&& Objects.equals(fromProjectDependency, toProject)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected void addExportedNames(Set<QualifiedName> names, IResourceDescription resourceDescriptor) {
		if (resourceDescriptor == null)
			return;
		Iterable<IEObjectDescription> iterable = resourceDescriptor.getExportedObjects();
		for (IEObjectDescription ieObjectDescription : iterable) {
			names.add(ieObjectDescription.getName());
		}
	}

	/**
	 * Overrides super implementation to replace case insensitive comparison logic by case sensitive comparison of
	 * names.
	 * <p>
	 * It returns true, if there is a dependency (i.e. name imported by a candidate) to any name exported by the
	 * description from a delta. That is, it computes if a candidate (with given importedNames) is affected by a change
	 * represented by the description from the delta.
	 */
	@Override
	protected boolean isAffected(Collection<QualifiedName> namesImportedByCandidate,
			IResourceDescription descriptionFromDelta) {
		if (descriptionFromDelta != null) {
			for (IEObjectDescription desc : descriptionFromDelta.getExportedObjects())
				if (namesImportedByCandidate.contains(desc.getName()))
					return true;
		}
		return false;
	}
}
