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
package org.eclipse.n4js.parser;

import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.parsetree.reconstr.impl.DefaultHiddenTokenHelper;

/**
 */
public class N4JSHiddenTokenHelper extends DefaultHiddenTokenHelper {

	@Override
	public boolean isWhitespace(AbstractRule rule) {
		return rule != null && ("WS".equals(rule.getName()) || "EOL".equals(rule.getName()));
	}
}
