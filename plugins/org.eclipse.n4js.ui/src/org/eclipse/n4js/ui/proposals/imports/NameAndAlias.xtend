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
package org.eclipse.n4js.ui.proposals.imports

import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtend.lib.annotations.Data

/**
 */
@Data
package class NameAndAlias {
	QualifiedName name
	String alias
}
