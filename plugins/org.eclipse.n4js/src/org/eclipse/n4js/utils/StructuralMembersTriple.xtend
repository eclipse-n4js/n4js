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
package org.eclipse.n4js.utils

import org.eclipse.n4js.ts.types.FieldAccessor
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.xtend.lib.annotations.Data

/**
 * Triple of structural members. Encapsulates a left and a right {@link TMember member} and additionally
 * a {@link FieldAccessor field accessor} for the right hand side. All members could be {@code null}.
 * Its the client's responsibility to check whether the members can be referenced or not.
 */
@Data
class StructuralMembersTriple {

	val TMember left;
	val TMember right;
	val FieldAccessor leftOtherAccessor;

}
