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
package org.eclipse.n4js.scoping;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.scoping.accessModifiers.TypeVisibilityChecker;
import org.eclipse.n4js.scoping.accessModifiers.VariableVisibilityChecker;
import org.eclipse.n4js.scoping.accessModifiers.VisibilityAwareIdentifiableScope;
import org.eclipse.n4js.scoping.accessModifiers.VisibilityAwareTypeScope;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.n4js.scoping.utils.CanLoadFromDescriptionHelper;
import org.eclipse.n4js.scoping.utils.UserDataAwareScope;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceDescriptionsProvider;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.DefaultGlobalScopeProvider;

import com.google.common.base.Predicate;
import com.google.inject.Inject;

/**
 * Global scope which allows access to types stored in user data of {@link IEObjectDescription}s.
 */
public class N4JSGlobalScopeProvider extends DefaultGlobalScopeProvider {

	private static final Logger LOGGER = Logger.getLogger(N4JSGlobalScopeProvider.class);

	@Inject
	private TypeVisibilityChecker typeVisibilityChecker;

	@Inject
	private VariableVisibilityChecker varVisibilityChecker;

	@Inject
	private CanLoadFromDescriptionHelper canLoadFromDescriptionHelper;

	@Inject
	private IResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject
	private WorkspaceAccess workspaceAccess;

	/**
	 * If the type is a {@link Type} a new {@link BuiltInTypeScope} is created.
	 */
	@Override
	// visibility increased from 'protected' to 'public' to allow access from ImportedNamesRecordingGlobalScopeAccess
	public IScope getScope(Resource context, boolean ignoreCase, EClass type,
			Predicate<IEObjectDescription> filter) {
		if (isSubtypeOfType(type)) {
			return getScope(getBuiltInTypeScope(context), context, ignoreCase, type, filter);
		}
		return super.getScope(context, ignoreCase, type, filter);
	}

	@Override
	protected IScope getScope(IScope parent, Resource context, boolean ignoreCase, EClass type,
			Predicate<IEObjectDescription> filter) {

		IScope result = null;
		try {
			result = createGlobalScope(parent, context, type, filter);
		} catch (IllegalStateException ise) {
			LOGGER.error("exception while creating global scope for: " + context.getURI(), ise);
			return IScope.NULLSCOPE;
		}
		if (isSubtypeOfType(type)) {
			result = new VisibilityAwareTypeScope(result, typeVisibilityChecker, context);
			return result;
		}
		if (isSubtypeOfIdentifiable(type)) {
			result = new VisibilityAwareIdentifiableScope(result, varVisibilityChecker, typeVisibilityChecker, context);
			return result;
		}
		return result;
	}

	/** Create the actual global scope. */
	protected IScope createGlobalScope(IScope parent, Resource context, EClass type,
			Predicate<IEObjectDescription> filter) {
		if (N4Scheme.isResourceWithN4Scheme(context)) {
			// built-in types do not have access to any other global elements of the workspace
			return parent;
		}
		N4JSProjectConfigSnapshot currProject = workspaceAccess.findProjectContaining(context);
		IResourceDescriptions resDescs = resourceDescriptionsProvider.getResourceDescriptions(context.getResourceSet());
		if (resDescs instanceof ChunkedResourceDescriptions) {
			ChunkedResourceDescriptions chunkedResDescs = (ChunkedResourceDescriptions) resDescs;
			IScope result = new N4JSGlobalScope(parent, currProject, chunkedResDescs, type, filter);
			result = UserDataAwareScope.createScope(result, context.getResourceSet(), resDescs::getResourceDescription,
					canLoadFromDescriptionHelper);
			return result;
		} else {
			if (resDescs != null) {
				LOGGER.error("expected " + IResourceDescriptions.class.getSimpleName() + " of type "
						+ ChunkedResourceDescriptions.class.getSimpleName() + " but got "
						+ resDescs.getClass().getSimpleName() + " for: " + context.getURI());
			} else {
				LOGGER.error("no " + IResourceDescriptions.class.getSimpleName() + " found for: " + context.getURI());
			}
		}
		return parent;
	}

	/**
	 * Returns <code>true</code> if the given {@code type} is a subtype of {@link Type}.
	 */
	protected boolean isSubtypeOfType(EClass type) {
		return type == TypesPackage.Literals.TYPE || type.getEPackage() == TypesPackage.eINSTANCE
				&& TypesPackage.Literals.TYPE.isSuperTypeOf(type);
	}

	/**
	 * Returns <code>true</code> if the given {@code type} is a subtype of {@link IdentifiableElement}.
	 */
	protected boolean isSubtypeOfIdentifiable(EClass type) {
		return type == TypesPackage.Literals.IDENTIFIABLE_ELEMENT || type.getEPackage() == TypesPackage.eINSTANCE
				&& TypesPackage.Literals.IDENTIFIABLE_ELEMENT.isSuperTypeOf(type);
	}

	@Override
	protected IScope createContainerScopeWithContext(Resource resource, IScope parent, IContainer container,
			Predicate<IEObjectDescription> filter, EClass type, boolean ignoreCase) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected IScope createContainerScope(IScope parent, IContainer container, Predicate<IEObjectDescription> filter,
			EClass type, boolean ignoreCase) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Finds the built in type scope for the given resource and its applicable context.
	 *
	 * @param resource
	 *            the resource that is currently linked
	 * @return an instance of the {@link BuiltInTypeScope}
	 */
	protected BuiltInTypeScope getBuiltInTypeScope(Resource resource) {
		ResourceSet resourceSet = resource.getResourceSet();
		return BuiltInTypeScope.get(resourceSet);
	}
}
