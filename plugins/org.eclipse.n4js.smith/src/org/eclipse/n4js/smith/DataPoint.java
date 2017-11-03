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
package org.eclipse.n4js.smith;

import java.util.concurrent.TimeUnit;

/** Single data point collected by the collectors. */
public class DataPoint {

	/** Time duration expressed in {@link TimeUnit#NANOSECONDS} */
	public final Long nanos;
	/** Name for a data point, helps humans distinguish instances. */
	public final String name;

	/** The constructor. No data validation. */
	public DataPoint(final String name, final Long nanos) {
		this.name = name;
		this.nanos = nanos;
	}

}
