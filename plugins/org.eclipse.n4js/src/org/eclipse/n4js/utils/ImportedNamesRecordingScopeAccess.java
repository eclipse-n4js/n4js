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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.scoping.N4JSGlobalScopeProvider;
import org.eclipse.xtext.linking.impl.ImportedNamesAdapter;
import org.eclipse.xtext.scoping.IGlobalScopeProvider;
import org.eclipse.xtext.scoping.IScope;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * The scope obtained through this class will record all queried members into the imported-names list of the context
 * resource passed in: {@link #getRecordingPolyfillScope(Resource, EReference)}.
 *
 * By using this scope additional elements, e.g. polyfills can be recorded as interest for the resource.
 */
public class ImportedNamesRecordingScopeAccess {

	@Inject
	private Provider<ImportedNamesAdapter> importedNamesAdapterProvider;

	@Inject
	private IGlobalScopeProvider globalScopeProvider;

	/**
	 * Obtain a global scope to lookup polyfills. Any request by name on the returned scope will record the name in the
	 * list of imported names of the given context resource.
	 */
	public IScope getRecordingPolyfillScope(Resource context, EReference eReference) {
		return getRecordingPolyfillScope(context, eReference.getEReferenceType());
	}

	public IScope getRecordingPolyfillScope(Resource context, EClass elementType) {
		ImportedNamesAdapter importedNamesAdapter = getImportedNamesAdapter(context);
		IScope scope = ((N4JSGlobalScopeProvider) globalScopeProvider).getScope(context, false, elementType, null);
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
