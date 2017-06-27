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
package org.eclipse.n4js.xpect.config

import java.util.ArrayList
import org.eclipse.xtext.resource.XtextResource
import org.xpect.setup.XpectSetupComponent

/**
 * Compose a simple list of Strings. The order of elements is preserved. For the time being just add them with the
 * following syntax in the body:
 * <pre><code>
 * StringList {
 *   s="++"
 *   s="--"
 *   s="abstract"
 *   s="break"
 * }
 * </code><pre>
 * A StringList can be used inside of {@link VarDef}. See also {@link MemberList} and {@link Config}.
 */
@XpectSetupComponent
class StringList implements ValueList {

	ArrayList<String> strings;

	new() { strings = newArrayList }

	new(String ... x) {
		strings = newArrayList( x );
	}

	def add(String s) { strings += s }

	def setS(String s) { strings += s}

	override evaluate(XtextResource res) {
		return strings
	}

}
