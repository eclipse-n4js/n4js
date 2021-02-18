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
 * the test flow is common in most cases: create all resources where they are consistent, then change one resource in a
 * way that another resource should be get error markers - verify this, then revise the breaking change and verify that
 * marker(s) are gone
 * <p>
 * in the resource only test one have to wait for the build job to end before verifying the new state (the build job is
 * triggered automatically after a resource have been saved)
 * <p>
 * in the dirty state test one have to wait for the firing of the event that triggers the dirty state job (for this in
 * the test and own listener is added to the dirty state manager to block until such an event is fired) and then for the
 * ending of this job, before verifying the new state
 */
package org.eclipse.n4js.tests.dirtystate;
