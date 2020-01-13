/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.es.n4idl

import org.eclipse.n4js.transpiler.TransformationDependency.RequiresAfter
import org.eclipse.n4js.transpiler.es.transform.ClassDeclarationTransformation
import org.eclipse.n4js.transpiler.es.transform.EnumDeclarationTransformation

/**
 * Only sub-classed to override the super-class's dependency to {@link ClassDeclarationTransformation}
 * and change it to {@link N4IDLClassDeclarationTransformation}.
 */
@RequiresAfter(N4IDLClassDeclarationTransformation)
class N4IDLEnumDeclarationTransformation extends EnumDeclarationTransformation {
	// no changes
}
