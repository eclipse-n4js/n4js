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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.scoping.accessModifiers.TypeVisibilityChecker;
import org.eclipse.n4js.scoping.accessModifiers.VariableVisibilityChecker;
import org.eclipse.n4js.scoping.accessModifiers.VisibilityAwareIdentifiableScope;
import org.eclipse.n4js.scoping.accessModifiers.VisibilityAwareTypeScope;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.scoping.utils.CanLoadFromDescriptionHelper;
import org.eclipse.n4js.scoping.utils.UserDataAwareScope;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.containers.FilterUriContainer;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.DefaultGlobalScopeProvider;

import com.google.common.base.Predicate;
import com.google.common.base.Throwables;
import com.google.inject.Inject;

/**
 * Global scope which allows access to types stored in user data of {@link IEObjectDescription}s.
 */
public class N4JSGlobalScopeProvider extends DefaultGlobalScopeProvider {

	@Inject
	private TypeVisibilityChecker typeVisibilityChecker;

	@Inject
	private VariableVisibilityChecker varVisibilityChecker;

	@Inject
	private CanLoadFromDescriptionHelper canLoadFromDescriptionHelper;

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
			result = super.getScope(parent, context, ignoreCase, type, filter);
		} catch (IllegalStateException ise) {
			String msg = "ERROR for " + context.getURI() + " ::\n" + Throwables.getStackTraceAsString(ise);
			System.err.println(msg);
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

	/**
	 * Creates a new container scope containing only resource descriptions of the current project. With this container a
	 * {@link UserDataAwareScope} is created.
	 * <p>
	 * This method is called for every container the current project depends on according to the settings in the project
	 * description file. This information has been indirectly retrieved via
	 * {@link ProjectDescription#getProjectDependencies()}.
	 */
	@Override
	protected IScope createContainerScopeWithContext(Resource resource, IScope parent, IContainer container,
			Predicate<IEObjectDescription> filter, EClass type, boolean ignoreCase) {
		if (resource != null) {
			URI uriToFilter = resource.getURI();
			// do filter context-resource from scope except in case of static polyfills.
			if (container.hasResourceDescription(uriToFilter) && !isStaticPolyFiller(resource))
				container = new FilterUriContainer(uriToFilter, container);
			IScope result = UserDataAwareScope.createScope(parent, container, filter, type, ignoreCase,
					resource.getResourceSet(), canLoadFromDescriptionHelper, container);
			return result;
		}
		return IScope.NULLSCOPE;
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

	/**
	 * Local check if the resource at hand is a static polyfill-module (tagged with @@StaticPolyfillModule)
	 *
	 * @param resource
	 *            to check
	 * @return true if static polyfill.
	 */
	private static final boolean isStaticPolyFiller(Resource resource) {
		if (resource instanceof N4JSResource) {
			return ((N4JSResource) resource).getModule().isStaticPolyfillModule();
		}
		return false;
	}
}
