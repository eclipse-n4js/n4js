/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.helper.server.xt;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotate a static method in a subclass of {@link XtParentRunner} with this annotation. The return value must be a set
 * of strings of IssueCodes that will be suppressed in all xt tests.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface XtSuppressedIssues {
	// NOOP
}
