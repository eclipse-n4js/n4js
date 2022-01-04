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
package org.eclipse.n4js.dts;

import org.eclipse.n4js.dts.TypeScriptParser.ProgramContext;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.Script;

/**
 *
 */
@SuppressWarnings("javadoc")
public class DtsParserListener extends TypeScriptParserBaseListener {
	private Script script = null;

	public Script getScript() {
		return script;
	}

	@Override
	public void enterProgram(ProgramContext ctx) {
		script = N4JSFactory.eINSTANCE.createScript();
	}

}
