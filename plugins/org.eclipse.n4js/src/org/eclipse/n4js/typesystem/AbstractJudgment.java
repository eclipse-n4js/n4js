/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.typesystem;

import org.eclipse.n4js.n4idl.versioning.N4IDLVersionResolver;
import org.eclipse.n4js.utils.ContainerTypesHelper;

import com.google.inject.Inject;

public abstract class AbstractJudgment {

	@Inject
	protected InternalTypeSystemNEW ts; // FIXME reconsider if we need to use N4JSTypeSystem here!!!!
	@Inject
	protected TypeSystemHelper typeSystemHelper;
	@Inject
	protected ContainerTypesHelper containerTypesHelper;
	@Inject
	protected N4IDLVersionResolver n4idlVersionResolver;

}