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
import static java.util.Collections.emptyList;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.naming.N4JSQualifiedNameConverter;
import org.eclipse.n4js.ts.scoping.N4TSQualifiedNameProvider;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.resource.impl.AliasedEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.ImportNormalizer;
import org.eclipse.xtext.scoping.impl.ImportScope;
import org.eclipse.xtext.scoping.impl.ImportedNamespaceAwareLocalScopeProvider;

import com.google.common.base.Predicate;

/**
 * Adapts {@link ImportedNamespaceAwareLocalScopeProvider} to filter ArgumentsType & EnumBaseType from globalScobe,
 * since it is a VirtualBaseType. <br />
 */
public class N4JSImportedNamespaceAwareLocalScopeProvider extends ImportedNamespaceAwareLocalScopeProvider {

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
		EObject context = res.getContents().get(0);

		// ||-- changed super-impl here:
		// IDE-662 filtering ArgumentsType & EnumBaseType from globalScobe, since it is a VirtualBaseType.
		Predicate<IEObjectDescription> filter = p -> {
			QualifiedName name = p.getName();
			if (name.getSegmentCount() == 1) {
				String singleSegment = name.getFirstSegment();
				return !("ArgumentsType".equals(singleSegment) || "EnumBaseType".equals(singleSegment));
			}
			return true;
		};
		IScope globalScope = getGlobalScope(res, reference, filter);
		// -- done change --||

		List<ImportNormalizer> normalizers = getImplicitImports(isIgnoreCase(reference));

		// IDE-1735 adding support for static-polyfills:
		TModule module = (TModule) res.getContents().get(1);
		if (module.isStaticPolyfillModule()) { // limit to situations of resources, that contain at least
			// one @StaticPolyfill
			normalizers.add(createImportedNamespaceResolver(
					module.getQualifiedName() + N4JSQualifiedNameConverter.DELIMITER + "*", false));
		}

		if (!normalizers.isEmpty()) {
			globalScope = createImportScope(globalScope, normalizers, null, reference.getEReferenceType(),
					isIgnoreCase(reference));
		}
		IScope resScope = getResourceScope(globalScope, context, reference);
		return resScope;
	}

	@Override
	protected List<ImportNormalizer> getImplicitImports(boolean ignoreCase) {
		// "#global#
		List<ImportNormalizer> result = newArrayList();
		result.add(createImportedNamespaceResolver(
				N4TSQualifiedNameProvider.GLOBAL_NAMESPACE_SEGMENT + N4JSQualifiedNameConverter.DELIMITER + "*",
				ignoreCase));
		return result;
	}

	@Override
	protected ImportScope createImportScope(IScope parent, List<ImportNormalizer> namespaceResolvers,
			ISelectable importFrom, EClass type, boolean ignoreCase) {
		return new NonResolvingImportScope(namespaceResolvers, parent, importFrom, type, ignoreCase);
	}

	static class NonResolvingImportScope extends ImportScope {

		private List<ImportNormalizer> myNormalizers;
		private final EClass myType;

		public NonResolvingImportScope(List<ImportNormalizer> namespaceResolvers, IScope parent, ISelectable importFrom,
				EClass type, boolean ignoreCase) {
			super(namespaceResolvers, parent, importFrom, type, ignoreCase);
			this.myType = type;
		}

		@Override
		protected List<ImportNormalizer> removeDuplicates(List<ImportNormalizer> namespaceResolvers) {
			List<ImportNormalizer> result = super.removeDuplicates(namespaceResolvers);
			myNormalizers = result;
			return result;
		}

		@Override
		protected Iterable<IEObjectDescription> getLocalElementsByName(QualifiedName name) {
			List<IEObjectDescription> result = newArrayList();
			QualifiedName resolvedQualifiedName = null;
			ISelectable importFrom = getImportFrom();
			for (ImportNormalizer normalizer : myNormalizers) {
				final QualifiedName resolvedName = normalizer.resolve(name);
				if (resolvedName != null) {
					Iterable<IEObjectDescription> resolvedElements = importFrom.getExportedObjects(myType, resolvedName,
							isIgnoreCase());
					for (IEObjectDescription resolvedElement : resolvedElements) {
						if (resolvedQualifiedName == null)
							resolvedQualifiedName = resolvedName;
						else if (!resolvedQualifiedName.equals(resolvedName)) {
							// change is here
							if (result.get(0).getEObjectURI().equals(resolvedElement.getEObjectOrProxy())) {
								return emptyList();
							}
							// change is above
						}
						QualifiedName alias = normalizer.deresolve(resolvedElement.getName());
						if (alias == null)
							throw new IllegalStateException("Couldn't deresolve " + resolvedElement.getName()
									+ " with import " + normalizer);
						final AliasedEObjectDescription aliasedEObjectDescription = new AliasedEObjectDescription(alias,
								resolvedElement);
						result.add(aliasedEObjectDescription);
					}
				}
			}
			return result;
		}

	}

}
