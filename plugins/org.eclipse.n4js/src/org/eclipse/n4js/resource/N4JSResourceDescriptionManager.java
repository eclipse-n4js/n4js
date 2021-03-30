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
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.fileextensions.FileExtensionTypeHelper;
import org.eclipse.n4js.packagejson.projectDescription.ProjectReference;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ts.utils.TypeHelper;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.xtext.resource.IWorkspaceAwareResourceDescriptionManager;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
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
public class N4JSResourceDescriptionManager extends DerivedStateAwareResourceDescriptionManager
		implements IWorkspaceAwareResourceDescriptionManager, N4Scheme {

	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;

	@Inject
	private TypeHelper typeHelper;

	@Inject
	private N4JSCrossReferenceComputer crossReferenceComputer;

	@Inject
	private FileExtensionTypeHelper fileExtensionTypeHelper;

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

	@Override
	public boolean isAffected(Delta delta, IResourceDescription candidate) {
		throw new UnsupportedOperationException(
				"a workspace configuration must be provided for isAffected computation");
	}

	@Override
	public boolean isAffected(Collection<IResourceDescription.Delta> deltas, IResourceDescription candidate,
			IResourceDescriptions context) {
		throw new UnsupportedOperationException(
				"a workspace configuration must be provided for isAffected computation");
	}

	/**
	 * {@inheritDoc}
	 *
	 * This marks {@code n4js} as affected if the manifest of the project changes. In turn, they will be revalidated and
	 * taken into consideration for the code generation step.
	 */
	@Override
	public boolean isAffected(Collection<IResourceDescription.Delta> deltas, IResourceDescription candidate,
			IResourceDescriptions context, WorkspaceConfigSnapshot workspaceConfig) {

		if (workspaceConfig == null) {
			isAffected(deltas, candidate, context); // will throw exception
		}

		N4JSWorkspaceConfigSnapshot workspaceConfigCasted = (N4JSWorkspaceConfigSnapshot) workspaceConfig;
		URI candidateURI = candidate.getURI();

		// Opaque modules cannot contain any references to one of the deltas.
		// Thus, they will never be affected by any change.
		if (N4JSLanguageUtils.isOpaqueModule(workspaceConfigCasted, candidateURI)) {
			return false;
		}

		boolean result = basicIsAffected(deltas, candidate, workspaceConfigCasted);
		if (!result) {
			for (IResourceDescription.Delta delta : deltas) {
				URI uri = delta.getUri();
				// if uri looks like a N4JS project description file (i.e. package.json)
				if (N4JSGlobals.PACKAGE_JSON.equalsIgnoreCase(uri.lastSegment())) {
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
	private boolean basicIsAffected(Collection<Delta> deltas, final IResourceDescription candidate,
			N4JSWorkspaceConfigSnapshot workspaceConfig) {

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
					if (hasDependencyTo(candidate, delta, workspaceConfig)) { // isAffected does not compare project
																				// names
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
	private boolean hasDependencyTo(IResourceDescription candidate, IResourceDescription.Delta delta,
			N4JSWorkspaceConfigSnapshot workspaceConfig) {

		return hasDependencyTo(candidate.getURI(), delta.getUri(), workspaceConfig);
	}

	/**
	 * Returns true iff the project containing the 'fromUri' has a direct dependency to the project containing the
	 * 'toUri'.
	 */
	private boolean hasDependencyTo(URI fromUri, URI toUri, N4JSWorkspaceConfigSnapshot workspaceConfig) {
		final N4JSProjectConfigSnapshot fromProject = workspaceConfig.findProjectContaining(fromUri);
		final N4JSProjectConfigSnapshot toProject = workspaceConfig.findProjectContaining(toUri);

		if (null != fromProject && null != toProject) { // Consider libraries. TODO: implement it at #findProject(URI)

			if (Objects.equals(fromProject, toProject)) {
				return true;
			}

			String toProjectName = toProject.getName();
			Iterable<ProjectReference> fromProjectDependencies = getDependenciesForIsAffected(fromProject);
			for (ProjectReference fromProjectDependencyName : fromProjectDependencies) {
				if (Objects.equals(fromProjectDependencyName.getProjectName(), toProjectName)) {
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
	 * Normally this method should return {@link N4JSProjectConfigSnapshot#getDependenciesAndImplementedApis()}, but
	 * subclasses may choose to filter out certain dependencies. In effect, filtering out certain dependencies will mean
	 * that incremental builds won't propagate along those dependencies.
	 * <p>
	 * NOTE: only required for external library workspace in Eclipse.
	 */
	protected Iterable<ProjectReference> getDependenciesForIsAffected(N4JSProjectConfigSnapshot fromProject) {
		return fromProject.getDependenciesAndImplementedApis();
	}

	private boolean hasDirectLoadtimeDependencyTo(IResourceDescription from, String toURIString) {
		if (from == null) {
			return false;
		}
		Optional<List<String>> fromDeps = UserDataMapper.readDependenciesLoadtimeForInheritanceFromDescription(from);
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
