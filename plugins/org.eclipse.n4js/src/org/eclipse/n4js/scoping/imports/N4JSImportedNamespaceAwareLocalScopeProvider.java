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
package org.eclipse.n4js.scoping.imports;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.naming.N4JSQualifiedNameConverter;
import org.eclipse.n4js.naming.N4JSQualifiedNameProvider;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.DeclMergingHelper;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.ImportNormalizer;
import org.eclipse.xtext.scoping.impl.ImportScope;
import org.eclipse.xtext.scoping.impl.ImportedNamespaceAwareLocalScopeProvider;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Adapts {@link ImportedNamespaceAwareLocalScopeProvider}.
 */
public class N4JSImportedNamespaceAwareLocalScopeProvider extends ImportedNamespaceAwareLocalScopeProvider {

	@Inject
	private DeclMergingHelper declMergingHelper;

	/**
	 * NOTE: for N4JS, 'context' is only used to retrieve the containing resource, and 'reference' will be one of:
	 * <ul>
	 * <li>IdentifierRef#id
	 * <li>ImportDeclaration#module
	 * <li>ParameterizedTypeRef#declaredType
	 * </ul>
	 */
	@Override
	public IScope getScope(EObject context, EReference reference) {
		return super.getScope(context, reference);
	}

	@Override
	protected List<ImportNormalizer> getImportedNamespaceResolvers(EObject context, boolean ignoreCase) {
		return Collections.emptyList();
	}

	@Override
	protected IScope getLocalElementsScope(IScope parent, final EObject context, final EReference reference) {
		// nothing to do
		return parent;
	}

	@Override
	protected IScope getResourceScope(Resource res, EReference reference) {
		if (res == null) {
			return IScope.NULLSCOPE;
		}
		EObject context = res.getContents().get(0);
		IScope globalScope = getGlobalScope(res, reference);
		List<ImportNormalizer> normalizers = getImplicitImports(isIgnoreCase(reference));

		// ||-- changed super-impl here:
		// IDE-1735 adding support for static-polyfills:
		TModule module = (TModule) res.getContents().get(1);
		if (module.isStaticPolyfillModule()) { // limit to situations of resources, that contain at least
			// one @StaticPolyfill
			normalizers.add(createImportedNamespaceResolver(
					module.getQualifiedName() + N4JSQualifiedNameConverter.DELIMITER
							+ N4JSQualifiedNameProvider.MODULE_CONTENT_SEGMENT + N4JSQualifiedNameConverter.DELIMITER
							+ "*",
					false));
		}
		// -- done change --||

		if (!normalizers.isEmpty()) {
			globalScope = createImportScope(globalScope, normalizers, null, reference.getEReferenceType(),
					isIgnoreCase(reference), res.getResourceSet());
		}

		IScope resScope = getResourceScope(globalScope, context, reference);
		return resScope;
	}

	@Override
	protected List<ImportNormalizer> getImplicitImports(boolean ignoreCase) {
		// "#global#
		List<ImportNormalizer> result = newArrayList();
		result.add(createImportedNamespaceResolver(
				N4JSQualifiedNameProvider.GLOBAL_NAMESPACE_SEGMENT + N4JSQualifiedNameConverter.DELIMITER
						+ N4JSQualifiedNameProvider.MODULE_CONTENT_SEGMENT + N4JSQualifiedNameConverter.DELIMITER
						+ "*",
				ignoreCase));
		return result;
	}

	@Override
	protected ImportScope createImportScope(IScope parent, List<ImportNormalizer> namespaceResolvers,
			ISelectable importFrom, EClass type, boolean ignoreCase) {
		return new NonResolvingImportScope(namespaceResolvers, parent, importFrom, type, ignoreCase, declMergingHelper,
				Optional.absent());
	}

	protected ImportScope createImportScope(IScope parent, List<ImportNormalizer> namespaceResolvers,
			ISelectable importFrom, EClass type, boolean ignoreCase, ResourceSet contextResourceSet) {
		BuiltInTypeScope builtInTypeScope = contextResourceSet != null ? BuiltInTypeScope.get(contextResourceSet)
				: null;
		return new NonResolvingImportScope(namespaceResolvers, parent, importFrom, type, ignoreCase, declMergingHelper,
				Optional.fromNullable(builtInTypeScope));
	}

	@Override
	protected IScope getResourceScope(IScope parent, EObject context, EReference reference) {
		return parent; // not required in N4JS
	}
}
