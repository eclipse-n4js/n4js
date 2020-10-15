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
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.fileextensions.FileExtensionTypeHelper;
import org.eclipse.n4js.internal.N4JSProject;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ts.utils.TypeHelper;
import org.eclipse.n4js.utils.N4JSLanguageHelper;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.xtext.server.util.RemovedProjectResourceDescriptionDelta;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.DerivedStateAwareResourceDescriptionManager;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.EObjectDescriptionLookUp;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
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
		Optional<? extends IN4JSProject> candidateProject = n4jsCore.findProject(candidateURI);

		// Opaque modules cannot contain any references to one of the deltas.
		// Thus, they will never be affected by any change.
		if (langHelper.isOpaqueModule(candidateURI)) {
			return false;
		}

		boolean result = basicIsAffected(deltas, candidateProject, candidate);
		if (!result) {
			if (hasPackageJsonOfCandidateProjectChanged(deltas, candidateProject)) {
				return true;
			}
		}
		return result;
	}

	private boolean hasPackageJsonOfCandidateProjectChanged(Collection<Delta> deltas,
			Optional<? extends IN4JSProject> candidateProject) {

		if (!candidateProject.isPresent()) {
			return false;
		}
		SafeURI<?> location = candidateProject.get().getLocation();
		URI candidateProjectURI = location != null ? location.toURI() : null;
		if (candidateProjectURI != null) {
			URI candidateProjectPackageJsonURI = URIUtils.trimTrailingPathSeparator(candidateProjectURI)
					.appendSegment(N4JSGlobals.PACKAGE_JSON);
			Optional<Delta> packageJsonOfCandidateProject = Iterables.tryFind(deltas,
					d -> candidateProjectPackageJsonURI.equals(d.getUri()));
			if (packageJsonOfCandidateProject.isPresent()) {
				// note: normally we would also require #haveEObjectDescriptionsChanged() to return true for
				// 'packageJsonOfCandidateProject', but because not the entire information of a package.json file is
				// stored in its resource description, we simply always return 'true' here:

				// FIXME the following change might break Eclipse!!!
				// return true;
				return packageJsonOfCandidateProject.get().haveEObjectDescriptionsChanged();
			}
		}
		return false;
	}

	/**
	 * Computes if a candidate is affected by any change, aka delta. It is affected, if
	 */
	private boolean basicIsAffected(Collection<Delta> deltas, Optional<? extends IN4JSProject> candidateProject,
			final IResourceDescription candidate) {
		// The super implementation DefaultResourceDescriptionManager#isAffected is based on a tradeoff / some
		// assumptions which do not hold for n4js wrt to manifest changes

		String candidateURIString = candidate.getURI().toString();

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

				IResourceDescription oldDesc = delta.getOld();
				IResourceDescription newDesc = delta.getNew();
				if (isAffected(namesImportedByCandidate, newDesc) // we may added a new exported name!
						|| isAffected(namesImportedByCandidate, oldDesc)) { // we may removed an exported name
					if (hasDependencyTo(candidateProject, delta)) { // isAffected does not compare project names
						return true;
					}
				}

				boolean loadtimeDependencyOld = hasDirectLoadtimeDependencyTo(oldDesc, candidateURIString);
				boolean loadtimeDependencyNew = hasDirectLoadtimeDependencyTo(newDesc, candidateURIString);
				if (loadtimeDependencyOld != loadtimeDependencyNew) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Returns true iff project containing the 'candidate' has a direct dependency to the project containing the
	 * 'delta'.
	 */
	private boolean hasDependencyTo(Optional<? extends IN4JSProject> candidateProject,
			IResourceDescription.Delta delta) {
		if (delta instanceof RemovedProjectResourceDescriptionDelta) {
			return hasDependencyTo(candidateProject,
					((RemovedProjectResourceDescriptionDelta) delta).getRemovedProjectName());
		}
		return hasDependencyTo(candidateProject, delta.getUri());
	}

	private boolean hasDependencyTo(Optional<? extends IN4JSProject> fromProjectOpt, String toProjectName) {
		final IN4JSProject fromProject = fromProjectOpt.orNull();
		if (fromProject instanceof N4JSProject) {

			if (Objects.equals(fromProject.getProjectName().getRawName(), toProjectName)) {
				return true;
			}

			List<String> fromProjectDependencies = ((N4JSProject) fromProject)
					.getDependenciesAndImplementedApisUnresolved();
			for (String fromProjectDependencyName : fromProjectDependencies) {
				if (Objects.equals(fromProjectDependencyName, toProjectName)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns true iff the project containing the 'fromUri' has a direct dependency to the project containing the
	 * 'toUri'.
	 */
	private boolean hasDependencyTo(Optional<? extends IN4JSProject> fromProjectOpt, URI toUri) {
		final IN4JSProject fromProject = fromProjectOpt.orNull();
		final IN4JSProject toProject = n4jsCore.findProject(toUri).orNull();

		if (null != fromProject && null != toProject) { // Consider libraries. TODO: implement it at #findProject(URI)

			if (Objects.equals(fromProject, toProject)) {
				return true;
			}

			Iterable<? extends IN4JSProject> fromProjectDependencies = getDependenciesForIsAffected(fromProject);
			for (IN4JSProject fromProjectDependency : fromProjectDependencies) {
				if (Objects.equals(fromProjectDependency, toProject)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns project dependencies of the given project that should be considered when computing the
	 * {@link #isAffected(Collection, IResourceDescription, IResourceDescriptions) isAffected()} relation.
	 * <p>
	 * Normally this method should return {@link IN4JSProject#getDependenciesAndImplementedApis()}, but subclasses may
	 * choose to filter out certain dependencies. In effect, filtering out certain dependencies will mean that
	 * incremental builds won't propagate along those dependencies.
	 * <p>
	 * NOTE: only required for external library workspace in Eclipse.
	 */
	protected Iterable<? extends IN4JSProject> getDependenciesForIsAffected(IN4JSProject fromProject) {
		return fromProject.getDependenciesAndImplementedApis();
	}

	private boolean hasDirectLoadtimeDependencyTo(IResourceDescription from, String toURIString) {
		if (from == null) {
			return false;
		}
		java.util.Optional<List<String>> fromDeps = UserDataMapper
				.readDependenciesLoadtimeForInheritanceFromDescription(from);
		return fromDeps.isPresent() && fromDeps.get().contains(toURIString);
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
