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
package org.eclipse.n4js.transpiler.es.assistants

import com.google.inject.Inject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.transpiler.TransformationAssistant
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider

/**
 * 
 */
class ModuleSpecifierAssistant extends TransformationAssistant {

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	// FIXME improve implementation & reconsider performance!!!
	def String getActualFileExtensionForN4jsdFile(Resource targetResource, TModule targetModule) {
		val targetQN = qualifiedNameConverter.toQualifiedName(targetModule.getQualifiedName());
		val index = resourceDescriptionsProvider.getResourceDescriptions(targetResource);
		val matchingTModules = index.getExportedObjects(TypesPackage.Literals.TMODULE, targetQN, false);
		var boolean gotCJS = false;
		var boolean gotMJS = false;
		for (desc : matchingTModules) {
			val ext = desc.EObjectURI.fileExtension;
			if (ext == N4JSGlobals.CJS_FILE_EXTENSION) {
				gotCJS = true;
			} else if (ext == N4JSGlobals.MJS_FILE_EXTENSION) {
				gotMJS = true;
			}
		}
		if (gotMJS) {
			return N4JSGlobals.MJS_FILE_EXTENSION;
		} else if (gotCJS) {
			return N4JSGlobals.CJS_FILE_EXTENSION;
		}
		return N4JSGlobals.JS_FILE_EXTENSION;
	}
}
