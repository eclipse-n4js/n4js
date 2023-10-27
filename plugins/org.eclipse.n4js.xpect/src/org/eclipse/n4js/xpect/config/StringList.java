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
package org.eclipse.n4js.xpect.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.xpect.setup.XpectSetupComponent;
import org.eclipse.xtext.resource.XtextResource;

/**
 * Compose a simple list of Strings. The order of elements is preserved. For the time being just add them with the
 * following syntax in the body:
 *
 * <pre>
 * <code>
 * StringList {
 *   s="++"
 *   s="--"
 *   s="abstract"
 *   s="break"
 * }
 * </code>
 * </pre>
 *
 * A StringList can be used inside of {@link VarDef}. See also {@link MemberList} and {@link Config}.
 */
@XpectSetupComponent
public class StringList implements ValueList {

	public ArrayList<String> strings;

	/** Constructor */
	public StringList() {
		strings = new ArrayList<>();
	}

	/** Constructor */
	public StringList(String... x) {
		strings = new ArrayList<>();
		strings.addAll(Arrays.asList(x));
	}

	public void add(String s) {
		strings.add(s);
	}

	public void setS(String s) {
		strings.add(s);
	}

	@Override
	public List<String> evaluate(XtextResource res) {
		return strings;
	}

}
