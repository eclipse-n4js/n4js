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
package org.eclipse.n4js.scoping.accessModifiers;

import static java.util.Collections.emptyList;

import java.util.Collection;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.packagejson.projectDescription.ProjectReference;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypeAccessModifier;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.util.Strings;

import com.google.inject.Inject;

/**
 * Abstract visibility checker that implements the logic how a type access modifier is evaluated in a given context.
 */
public abstract class AbstractTypeVisibilityChecker<T extends IdentifiableElement>
		implements AbstractVisibilityChecker<T> {

	/** The N4JS core. This service is used for resolving projects by its contained modules. */
	@Inject
	protected WorkspaceAccess workspaceAccess;

	@Override
	public TypeVisibility isVisible(
			Resource contextResource, IEObjectDescription e) {
		TypeAccessModifier typeAccessModifier = N4JSResourceDescriptionStrategy.getTypeAccessModifier(e);
		return isVisible(contextResource, typeAccessModifier, e);
	}

	/**
	 * Returns the TypeVisibility of the <i>element</i> in the given <i>context</i>(that is the given resource) if it
	 * had the give access modifier. That is, the actual access modifier of the element is not considered here, usually
	 * this is done in the caller via {@code getTypeAccessModifier}. However, there is no common interface for
	 * retrieving that information.
	 */
	protected TypeVisibility isVisible(final Resource contextResource, final TypeAccessModifier accessModifier,
			final T element) {

		int startIndex = accessModifier.getValue();
		boolean visibility = false;
		String firstVisible = TypeAccessModifier.PUBLIC.getName().toUpperCase();

		for (int i = startIndex; i < TypeAccessModifier.values().length; i++) {

			boolean visibilityForModifier = false;
			TypeAccessModifier modifier = TypeAccessModifier.get(i);

			switch (modifier) {
			case PRIVATE: {
				visibilityForModifier = isPrivateVisible(contextResource, element);
				break;
			}
			case PROJECT: {
				visibilityForModifier = isProjectVisible(contextResource, element);
				break;
			}
			case PUBLIC_INTERNAL: {
				visibilityForModifier = isPublicInternalVisible(contextResource, element);
				break;
			}
			case PUBLIC:
				visibilityForModifier = true;
				break;
			default:
				visibilityForModifier = false;
				break;
			}
			// First modifier = element modifier
			if (i - startIndex < 1) {
				visibility = visibilityForModifier;
			}
			// First visible modifier = suggested element modifier
			if (visibilityForModifier) {
				firstVisible = modifier.getName().toUpperCase();
				break;
			}
		}
		return new TypeVisibility(visibility, firstVisible);
	}

	/**
	 * Returns the TypeVisibility of the <i>element</i> in the given <i>context</i>(that is the given resource) if it
	 * had the give access modifier. That is, the actual access modifier of the element is not considered here, usually
	 * this is done in the caller via {@code getTypeAccessModifier}. However, there is no common interface for
	 * retrieving that information. Implementors should avoid calling {@link IEObjectDescription#getEObjectOrProxy()}.
	 */
	protected TypeVisibility isVisible(final Resource contextResource, final TypeAccessModifier accessModifier,
			final IEObjectDescription element) {

		int startIndex = accessModifier.getValue();
		boolean visibility = false;
		String firstVisible = "PUBLIC";

		for (int i = startIndex; i < TypeAccessModifier.values().length; i++) {

			boolean visibilityForModifier = false;
			TypeAccessModifier modifier = TypeAccessModifier.get(i);

			switch (modifier) {
			case PRIVATE: {
				visibilityForModifier = isPrivateVisible(contextResource, element);
				break;
			}
			case PROJECT: {
				visibilityForModifier = isProjectVisible(contextResource, element);
				break;
			}
			case PUBLIC_INTERNAL: {
				visibilityForModifier = isPublicInternalVisible(contextResource, element);
				break;
			}
			case PUBLIC:
				visibilityForModifier = true;
				break;
			default:
				visibilityForModifier = false;
				break;
			}
			// First modifier = element modifier
			if (i - startIndex < 1) {
				visibility = visibilityForModifier;
			}
			// First visible modifier = suggested element modifier
			if (visibilityForModifier) {
				firstVisible = modifier.getName().toUpperCase();
				break;
			}
		}
		return new TypeVisibility(visibility, firstVisible);
	}

	private boolean isPublicInternalVisible(Resource contextResource, final T element) {
		if (contextResource != null) {
			final TModule contextModule = N4JSResource.getModule(contextResource);
			final TModule elementModule = N4JSResource.getModule(element.eResource());
			return elementModule == null || Strings.equal(contextModule.getVendorID(), elementModule.getVendorID());
		}
		return false;
	}

	private boolean isPublicInternalVisible(Resource contextResource, final IEObjectDescription element) {
		if (contextResource != null) {
			final TModule contextModule = N4JSResource.getModule(contextResource);
			if (contextModule != null) {
				N4JSProjectConfigSnapshot project = this.workspaceAccess.findProjectByNestedLocation(contextResource,
						element.getEObjectURI());
				if (project == null) {
					return true;
				}
				return Strings.equal(contextModule.getVendorID(), project.getVendorId());
			}
		}
		return false;
	}

	private boolean isPrivateVisible(Resource contextResource, final T element) {
		return element.eResource() == contextResource;
		// TModule typeModule = EcoreUtil2.getContainerOfType(element, TModule.class);
		// return typeModule == null || typeModule == contextModule;
	}

	private boolean isPrivateVisible(Resource contextResource, final IEObjectDescription element) {
		return contextResource == null || element.getEObjectURI().trimFragment().equals(contextResource.getURI());
	}

	private boolean isProjectVisible(Resource contextResource, final T element) {
		if (contextResource != null) {
			final TModule contextModule = N4JSResource.getModule(contextResource);
			if (contextModule != null) {
				final TModule elementModule = N4JSResource.getModule(element.eResource());
				return elementModule == null
						|| elementModule == contextModule
						|| ( //
						Strings.equal(contextModule.getProjectID(), elementModule.getProjectID())
								&& Strings.equal(contextModule.getVendorID(), elementModule.getVendorID()) //
						)
						|| isTestedProjectOf(contextModule, elementModule);
			}
		}
		return false;
	}

	private boolean isProjectVisible(Resource contextResource, final IEObjectDescription element) {
		if (contextResource != null) {
			final TModule contextModule = N4JSResource.getModule(contextResource);
			if (contextModule == null) {
				return false;
			}
			N4JSProjectConfigSnapshot project = this.workspaceAccess.findProjectByNestedLocation(contextResource,
					element.getEObjectURI());
			if (project == null) {
				return true;
			}
			return Strings.equal(contextModule.getProjectID(), project.getName())
					&& Strings.equal(contextModule.getVendorID(), project.getVendorId())
					|| isTestedProjectOf(contextModule, project);
		}
		return false;
	}

	/**
	 * Returns with {@code true} if the context module argument belongs to a {@link ProjectType#TEST test} project and
	 * any of its tested projects contains the element module argument.
	 *
	 * @param contextModule
	 *            the content module.
	 * @param elementModule
	 *            the element module.
	 * @return {@code true} if the element module's container project is the tested project of the context module.
	 *         Otherwise returns with {@code false}.
	 */
	public boolean isTestedProjectOf(final TModule contextModule, final TModule elementModule) {
		if (null == elementModule || null == contextModule || null == elementModule.eResource()
				|| null == contextModule.eResource()) {
			return false;
		}

		for (final ProjectReference testedProject : getTestedProjects(contextModule.eResource())) {
			final Resource eResource = elementModule.eResource();
			if (null != eResource) {
				final N4JSProjectConfigSnapshot elementProject = workspaceAccess.findProjectContaining(eResource);
				if (null != elementProject) {
					String projectId = elementProject.getProjectIdForPackageName(testedProject.getPackageName());
					if (elementProject.getName().equals(projectId)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Returns with {@code true} if the context module argument belongs to a {@link ProjectType#TEST test} project and
	 * any of its tested projects contains the element module argument.
	 *
	 * @param contextModule
	 *            the content module.
	 * @param elementProject
	 *            the element's projects.
	 * @return {@code true} if the element module's container project is the tested project of the context module.
	 *         Otherwise returns with {@code false}.
	 */
	public boolean isTestedProjectOf(final TModule contextModule, final N4JSProjectConfigSnapshot elementProject) {
		for (final ProjectReference testedProject : getTestedProjects(contextModule.eResource())) {
			if (elementProject.getName().equals(testedProject.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns with a collection of projects that are tested by the container project for the resource given with the
	 * unique resource URI.
	 *
	 * @param contextResource
	 *            the context resource to retrieve its container project's host.
	 * @return a collection of tested projects. May be empty but never {@code null}.
	 */
	public Collection<? extends ProjectReference> getTestedProjects(Resource contextResource) {
		if (null == contextResource) {
			return emptyList();
		}

		final N4JSProjectConfigSnapshot contextProject = workspaceAccess.findProjectContaining(contextResource);
		if (null == contextProject) {
			return emptyList();
		}

		return contextProject.getProjectDescription().getTestedProjects();
	}

	/**
	 * Encapsulates visibility information as well as the minimally needed access modifier to make the type visible.
	 */
	public static class TypeVisibility {
		/**
		 * Instatiates TypeVisbility with given visibility and empty suggestion
		 *
		 * @param visibility
		 *            true on visible
		 */
		public TypeVisibility(boolean visibility) {
			this(visibility, null);
		}

		/**
		 * Instantiates TypeVisibility with given visibility and suggestion
		 *
		 * @param visibility
		 *            true on visible
		 * @param suggestion
		 *            access modifier as String
		 */

		public TypeVisibility(boolean visibility, String suggestion) {
			this.visibility = visibility;
			this.accessModifierSuggestion = suggestion;
		}

		/**
		 * Visibility: true if visible
		 */
		final public boolean visibility;
		/**
		 * Access modifier needed to make the element visible.
		 */
		final public String accessModifierSuggestion;
	}

}
