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
package org.eclipse.n4js.xtext.ide.server;

import java.lang.reflect.Type;
import java.util.Map;

import org.eclipse.lsp4j.ExecuteCommandParams;

/**
 * Return the argument types for the available commands.
 */
public interface ExecuteCommandParamsDescriber {

	/**
	 * Command id to argument types. The arguments refer to {@link ExecuteCommandParams#getArguments()}.
	 */
	Map<String, Type[]> argumentTypes();

}
