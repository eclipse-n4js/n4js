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

import java.util.List;

import org.eclipse.xtext.resource.XtextResource;

interface ValueList {

	/**
	 * Evaluate this value list against a current environment.
	 *
	 * @param res
	 *            Xtext Resource to evaluate against. Can be used for accessing built in types.
	 * @return list of computed strings.
	 */
	List<String> evaluate(XtextResource res);
}
