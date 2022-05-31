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
package org.eclipse.n4js.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.scoping.N4JSGlobalScopeProvider;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.linking.impl.ImportedNamesAdapter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IGlobalScopeProvider;
import org.eclipse.xtext.scoping.IScope;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * The scope obtained through this class will record all queried members into the imported-names list of the context
 * resource passed in: {@link #getRecordingGlobalScope(Resource, EReference)}.
 *
 * By using this scope additional elements, e.g. polyfills or elements merged via declaration merging, can be recorded
 * as interest for the resource.
 */
public class ImportedNamesRecordingGlobalScopeAccess {

	@Inject
	private Provider<ImportedNamesAdapter> importedNamesAdapterProvider;

	@Inject
	private IGlobalScopeProvider globalScopeProvider;

	/**
	 * Like {@link #getElementsFromGlobalScope(Resource, EClass, QualifiedName)}, but for {@link Type}s.
	 */
	public List<Type> getTypesFromGlobalScope(Resource context, QualifiedName fqn) {
		List<EObject> result = getElementsFromGlobalScope(context, TypesPackage.Literals.TYPE, fqn);
		@SuppressWarnings("unchecked")
		List<Type> resultCasted = (List<Type>) ((List<?>) result);
		return resultCasted;

	}

	/**
	 * Like {@link #getElementsFromGlobalScope(Resource, EClass, QualifiedName)}, but for {@link AbstractNamespace}s.
	 */
	public List<AbstractNamespace> getNamespacesFromGlobalScope(Resource context, QualifiedName fqn) {
		List<EObject> result = getElementsFromGlobalScope(context, TypesPackage.Literals.ABSTRACT_NAMESPACE, fqn);
		@SuppressWarnings("unchecked")
		List<AbstractNamespace> resultCasted = (List<AbstractNamespace>) ((List<?>) result);
		return resultCasted;

	}

	/**
	 * Like {@link #getRecordingGlobalScope(Resource, EClass)}, but immediately query the global scope for elements of
	 * the given qualified name.
	 */
	// moved here from ContainerTypesHelper.MemberCollector#getPolyfillTypesFromScope(QualifiedName)
	public List<EObject> getElementsFromGlobalScope(Resource context, EClass elementType, QualifiedName fqn) {

		IScope contextScope = getRecordingGlobalScope(context, elementType);
		List<EObject> result = new ArrayList<>();

		// contextScope.getElements(fqn) returns all polyfills, since shadowing is handled differently
		// for them!
		for (IEObjectDescription descr : contextScope.getElements(fqn)) {
			EObject polyfillType = descr.getEObjectOrProxy();
			if (polyfillType.eIsProxy()) {
				// TODO review: this seems odd... is this a test setup problem (since we do not use the
				// index
				// there and load the resource separately)?
				polyfillType = EcoreUtil.resolve(polyfillType, context);
				if (polyfillType.eIsProxy()) {
					throw new IllegalStateException("unexpected proxy");
				}
			}
			result.add(polyfillType);
		}
		// }

		return result;
	}

	/**
	 * Obtain a global scope for global element lookup. Any request by name on the returned scope will record the name
	 * in the list of imported names of the given context resource.
	 */
	public IScope getRecordingGlobalScope(Resource context, EReference eReference) {
		return getRecordingGlobalScope(context, eReference.getEReferenceType());
	}

	/**
	 * Same as {@link #getRecordingGlobalScope(Resource, EReference)}, but accepts an expected element type instead of
	 * an {@link EReference}.
	 */
	public IScope getRecordingGlobalScope(Resource context, EClass expectedElementType) {
		ImportedNamesAdapter importedNamesAdapter = getImportedNamesAdapter(context);
		IScope scope = ((N4JSGlobalScopeProvider) globalScopeProvider).getScope(context, false, expectedElementType,
				null);
		return importedNamesAdapter.wrap(scope);
	}

	/**
	 * Obtain the previously registered adapter for the imported names or register a new one.
	 *
	 * @param context
	 *            the resource that is supposed to hold the adapter
	 */
	private ImportedNamesAdapter getImportedNamesAdapter(Resource context) {
		ImportedNamesAdapter adapter = ImportedNamesAdapter.find(context);
		if (adapter != null)
			return adapter;
		ImportedNamesAdapter importedNamesAdapter = importedNamesAdapterProvider.get();
		context.eAdapters().add(importedNamesAdapter);
		return importedNamesAdapter;
	}

}
