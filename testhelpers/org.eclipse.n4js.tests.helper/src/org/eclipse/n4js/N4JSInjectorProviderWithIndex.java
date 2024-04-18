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
package org.eclipse.n4js;

import org.eclipse.xtext.resource.IResourceDescriptions;

/**
 * An injector provider which binds {@link EagerResourceSetBasedResourceDescriptions}.
 */
public class N4JSInjectorProviderWithIndex extends N4JSInjectorProvider {
	/** */
	public N4JSInjectorProviderWithIndex() {
		super(new EagerResourceSetModule());
	}

	public static class EagerResourceSetModule extends BaseTestModule {
		public Class<? extends IResourceDescriptions> bindResourceDescriptions() {
			return EagerResourceSetBasedResourceDescriptions.class;
		}
	}
}
