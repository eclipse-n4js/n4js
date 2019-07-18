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
package org.eclipse.n4js.jsdoc2spec.adoc;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.jsdoc2spec.RepoRelativePath;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TModule;

import com.google.inject.Inject;

/**
 * The {@link RepoRelativePathHolder} caches {@link RepoRelativePath}s for subsequent queries. <br/>
 * <br/>
 * Needs to be injected.
 */
public class RepoRelativePathHolder {

	@Inject
	private IN4JSCore n4jscore;

	private final Map<Resource, RepoRelativePath> modulesToRepoCache = new HashMap<>();

	/**
	 * The method returns the RepoRelativePath for a given SyntaxRelatedTElement. It caches the result for subsequent
	 * queries.
	 */
	public RepoRelativePath get(IdentifiableElement idElement) {
		Resource res = evadeStaticPolyfillResource(idElement);

		if (res != null) {
			if (!modulesToRepoCache.containsKey(res)) {
				FileURI fileURI = new FileURI(res.getURI());
				RepoRelativePath rrpRes = RepoRelativePath.compute(fileURI, n4jscore);
				if (rrpRes != null) {
					modulesToRepoCache.put(res, rrpRes);
				}
			}
			RepoRelativePath rrpRes = modulesToRepoCache.get(res);

			if (rrpRes != null && idElement instanceof SyntaxRelatedTElement) {
				return rrpRes.withLine((SyntaxRelatedTElement) idElement);
			}
		}
		return null;
	}

	/**
	 * Static polyfill modules are integrated into their corresponding polyfill aware modules. Whenever a static
	 * polyfill module is found, this method retrieves the corresponding aware module and returns its resource.
	 */
	private Resource evadeStaticPolyfillResource(IdentifiableElement idElement) {
		TModule module = idElement.getContainingModule();
		if (module == null) // happens when executing tests
			return null;

		Resource res = module.eResource();
		return res;
	}

}
