/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */

/**
 * This package contains internal implementations of the services normally provided by the
 * {@link org.eclipse.n4js.internal}. More specifically it provides logic similar to the
 * {@link org.eclipse.n4js.projectModel.IN4JSCore}, {@link org.eclipse.n4js.internal.N4JSModel} and related
 * implementations. Those classes are designed around the concept of the {@code workspace}. That design is based on
 * principles of resource sets, workspaces and singletons that do not hold in all cases.
 *
 * <p>
 * Purpose for this package is to provide runners with access to the shipped code, normally accessible via workspace, in
 * setups where there is no workspace at all (partial workspace with limited functionality). Provided access is designed
 * to follow the same logic as other abstractions over the workspace, but with read only access to the short lived
 * objects and without any persistence layer.
 *
 * <p>
 * For more information see N4JS design documentation, chapters {@code Container Management} and {@code Project Model}.
 */
package org.eclipse.n4js.runner.internal;
