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
package org.eclipse.n4js.smith;

import com.google.common.base.Preconditions;

/**
 * A data collector that will discard its children on reset or purge requests.
 */
class DataCollectorWithTransientChildren extends TimedDataCollector {

	/**
	 * Constructor
	 */
	public DataCollectorWithTransientChildren(String id, DataCollector parent) {
		super(id, Preconditions.checkNotNull(parent));
	}

	@Override
	public void purgeData() {
		clearChildren();
		super.purgeData();
	}

	@Override
	public void resetData() {
		clearChildren();
		super.resetData();
	}

}
