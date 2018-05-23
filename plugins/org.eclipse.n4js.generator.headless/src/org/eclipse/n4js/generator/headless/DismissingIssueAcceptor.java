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
package org.eclipse.n4js.generator.headless;

import org.eclipse.xtext.validation.Issue;

/**
 * Dismisses all issues during compilation by simply doing nothing. This class is used as the default issue acceptor if
 * none was provided by the user.
 */
public class DismissingIssueAcceptor implements IssueAcceptor {
	@Override
	public void accept(Issue issue) {
		// do nothing
	}
}
