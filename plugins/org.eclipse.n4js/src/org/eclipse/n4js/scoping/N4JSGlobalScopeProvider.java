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
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.scoping.accessModifiers.TypeVisibilityChecker;
import org.eclipse.n4js.scoping.accessModifiers.VariableVisibilityChecker;
import org.eclipse.n4js.scoping.accessModifiers.VisibilityAwareIdentifiableScope;
import org.eclipse.n4js.scoping.accessModifiers.VisibilityAwareTypeScope;
import org.eclipse.n4js.scoping.utils.CanLoadFromDescriptionHelper;
import org.eclipse.n4js.scoping.utils.UserDataAwareScope;
import org.eclipse.n4js.ts.scoping.builtin.DefaultN4GlobalScopeProvider;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.containers.FilterUriContainer;
import org.eclipse.xtext.scoping.IScope;

import com.google.common.base.Predicate;
import com.google.common.base.Throwables;
import com.google.inject.Inject;

/**
 * Global scope which allows access to types stored in user data of {@link IEObjectDescription}s.
 */
public class N4JSGlobalScopeProvider extends DefaultN4GlobalScopeProvider {

	@Inject
	private TypeVisibilityChecker typeVisibilityChecker;

	@Inject
	private VariableVisibilityChecker varVisibilityChecker;

	@Inject
	private CanLoadFromDescriptionHelper canLoadFromDescriptionHelper;

	@Override
	protected IScope createContainerScope(IScope parent, IContainer container, Predicate<IEObjectDescription> filter,
			EClass type, boolean ignoreCase) {
		throw new UnsupportedOperationException();
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

	/**
	 * Local ckeck if the resource at hand is a static polyfill-module (tagged with @@StaticPolyfillModule)
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
