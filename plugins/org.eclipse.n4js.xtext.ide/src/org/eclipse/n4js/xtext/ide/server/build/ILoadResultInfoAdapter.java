/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.ide.server.build;

import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

/**
 *
 */
public interface ILoadResultInfoAdapter extends Adapter {

	public static ILoadResultInfoAdapter get(Resource resource) {
		for (Adapter adapter : resource.eAdapters()) {
			if (adapter instanceof ILoadResultInfoAdapter) {
				return (ILoadResultInfoAdapter) adapter;
			}
		}
		return null;
	}

	public static ILoadResultInfoAdapter remove(Resource resource) {
		ILoadResultInfoAdapter adapter = get(resource);
		if (adapter != null) {
			resource.eAdapters().remove(adapter);
			return adapter;
		}
		return adapter;
	}

	/** */
	public List<URI> getNewUris();
}
