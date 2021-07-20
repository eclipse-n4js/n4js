/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.dts.transform

import org.eclipse.n4js.transpiler.es.transform.ModuleSpecifierTransformation
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot

/**
 *
 */
class ModuleSpecifierTransformationDts extends ModuleSpecifierTransformation {

	override protected String createAbsoluteModuleSpecifier(N4JSProjectConfigSnapshot targetProject, TModule targetModule) {
		// FIXME GH-2150 check if the following can/should be moved to the super class (and also used by EcmaScriptTranspiler)
		if (N4JSLanguageUtils.isMainModule(targetProject, targetModule)) {
			// 'targetModule' is the main module of 'targetProject', so we can use a project import:
			return getActualProjectName(targetProject).toString();
		}
		return super.createAbsoluteModuleSpecifier(targetProject, targetModule);
	}
}
