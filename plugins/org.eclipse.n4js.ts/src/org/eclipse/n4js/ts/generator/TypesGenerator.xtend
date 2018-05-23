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
package org.eclipse.n4js.ts.generator

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IGenerator
import org.eclipse.xtext.generator.IFileSystemAccess

/**
 * Generates code from your model files on save.
 *
 * see http://www.eclipse.org/Xtext/documentation.html#TutorialCodeGeneration
 */
class TypesGenerator implements IGenerator {

	override void doGenerate(Resource resource, IFileSystemAccess fsa) {
//		fsa.generateFile('greetings.txt', 'People to greet: ' +
//			resource.allContents
//				.filter(typeof(Greeting))
//				.map[name]
//				.join(', '))
	}
}
