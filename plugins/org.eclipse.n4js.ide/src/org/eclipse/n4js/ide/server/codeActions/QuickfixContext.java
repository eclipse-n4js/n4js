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
package org.eclipse.n4js.ide.server.codeActions;

import org.eclipse.xtext.ide.server.codeActions.ICodeActionService2.Options;

@SuppressWarnings({ "restriction", "javadoc" })
public class QuickfixContext {
	public final String code;
	public final Options options;

	public QuickfixContext(String code, Options options) {
		this.code = code;
		this.options = options;
	}
}