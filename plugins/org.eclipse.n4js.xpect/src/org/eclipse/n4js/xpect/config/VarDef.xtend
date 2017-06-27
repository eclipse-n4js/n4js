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

import java.util.List
import org.xpect.setup.XpectSetupComponent

/**
 * Variable definition in XpectSetup section. Variables can be part of a {@link Config} or directly defined. A
 * Variable is given a name on construction: <code>VarDef "my_name" {...}</code> Variables then can be used in tests
 * by referencing the name like {@code <$my_name>} The content of the variable it the evalutation of it's content
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

	public List<ValueList> mlist = newArrayList()

	/**
	 * Constructor requiring a name.
	 *
	 * @param name of Variable
	 */
	public new(String name ) {
		this.name = name
	}

	/** Conent defined by add Method: MemberLists  */
	public def add( MemberList l) { mlist += l }

	public def add( StringList s) { mlist += s }


	public override toString() {
		'''VarDef[«name»"|«FOR m:mlist SEPARATOR ',' »«m»«ENDFOR»]'''
	}

}
