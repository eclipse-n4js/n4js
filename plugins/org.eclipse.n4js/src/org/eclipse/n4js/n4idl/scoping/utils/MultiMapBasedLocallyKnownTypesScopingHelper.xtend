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
package org.eclipse.n4js.n4idl.scoping.utils

import org.eclipse.n4js.scoping.utils.LocallyKnownTypesScopingHelper
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.impl.MultimapBasedScope

/**
 * Adapts {@link LocallyKnownTypesScopingHelper} by creating an instance of {@link MultimapBasedScope} as the scope for
 * locally known types, for example those defined within the same module.
 */
class MultiMapBasedLocallyKnownTypesScopingHelper extends LocallyKnownTypesScopingHelper {

	override protected buildMapBasedScope(IScope importScope, TModule localModule) {
		return MultimapBasedScope.createScope(importScope, localModule.topLevelTypes.map [ topLevelType |
			EObjectDescription.create(topLevelType.name, topLevelType)
		], false)
	}

}
