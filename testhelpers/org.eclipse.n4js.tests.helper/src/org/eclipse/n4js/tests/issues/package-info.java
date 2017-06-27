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
/**
 * The classes in this package form an API to generate expectations for compilation issues which are available as
 * instances of Xtext issues. To create expectations, an instance of
 * {@link org.eclipse.n4js.tests.issues.IssueExpectations} should be created. Then, new instances of
 * {@link org.eclipse.n4js.tests.issues.IssueMatcher} should be created using the
 * {@link org.eclipse.n4js.tests.issues.IssueExpectations#add()} method.
 * <p>
 * Each instance of {@link org.eclipse.n4js.tests.issues.IssueMatcher} represents an expectation for one particular
 * issue. Such an expectation can refer to any property of Xtext issues such as the error code or the error message.
 * These expectations can be configured by using a builder-like API.
 * </p>
 */
package org.eclipse.n4js.tests.issues;
