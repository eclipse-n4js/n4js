/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4idl;

import org.eclipse.n4js.N4JSRuntimeModule;
import org.eclipse.n4js.n4idl.scoping.N4IDLAwareTopLevelElementsCollector;
import org.eclipse.n4js.n4idl.tester.N4IDLAwareTestDiscoveryHelper;
import org.eclipse.n4js.scoping.TopLevelElementsCollector;
import org.eclipse.n4js.tester.TestDiscoveryHelper;
import org.eclipse.xtext.service.AbstractGenericModule;

/**
 * Additional bindings for the {@link N4JSRuntimeModule} if the N4IDL bundles are running as well.
 */
public class N4IDLModuleExtension extends AbstractGenericModule {
	// /** In case of transpiling N4IDL */
	// public Class<? extends ImportRewriteAssistant> bindImportRewriteAssistant() {
	// return VersionAwareImportRewriteAssistant.class;
	// }

	/** */
	public Class<? extends TopLevelElementsCollector> bindScopeProvider() {
		return N4IDLAwareTopLevelElementsCollector.class;
	}

	/**
	 * Binds an N4IDL-aware {@link TestDiscoveryHelper}.
	 */
	public Class<? extends TestDiscoveryHelper> bindTestDiscoveryHelper() {
		return N4IDLAwareTestDiscoveryHelper.class;
	}
}
