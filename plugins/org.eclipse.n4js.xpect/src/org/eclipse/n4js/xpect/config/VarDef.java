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
import java.util.List;

import org.eclipse.n4js.utils.Strings;
import org.eclipse.xpect.setup.XpectSetupComponent;

/**
 * Variable definition in XpectSetup section. Variables can be part of a {@link Config} or directly defined. A Variable
 * is given a name on construction: <code>VarDef "my_name" {...}</code> Variables then can be used in tests by
 * referencing the name like {@code <$my_name>} The content of the variable it the evalutation of it's content
 * components evaluation in the order they appear.
 *
 * <p>
 * Possible Content: {@link StringList} or {@link MemberList}
 *
 * <p>
 * Example:
 *
 * <pre>
 * VarDef "keywords" {
 *   StringList { s="++" s="--" }
 *   StringList { s="abstract" s="break" }
 * }
 * </pre>
 *
 * Using {@code <$keywords>} will be evaluated to {@code "++","--","abstract","break"}
 */
@XpectSetupComponent
public class VarDef {

	public String name;

	public List<ValueList> mlist = new ArrayList<>();

	/**
	 * Constructor requiring a name.
	 *
	 * @param name
	 *            of Variable
	 */
	public VarDef(String name) {
		this.name = name;
	}

	/** Content defined by add Method: MemberLists */
	public void add(MemberList l) {
		mlist.add(l);
	}

	public void add(StringList s) {
		mlist.add(s);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("VarDef[");
		sb.append(name);
		sb.append("\"|");
		sb.append(Strings.join(",", mlist));
		return sb.toString();
	}

}
