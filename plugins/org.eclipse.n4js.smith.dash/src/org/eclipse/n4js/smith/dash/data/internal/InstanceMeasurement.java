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
package org.eclipse.n4js.smith.dash.data.internal;

import java.util.function.Consumer;

import org.eclipse.n4js.smith.dash.data.LinkedMeasurement;

/**
 *
 */
public class InstanceMeasurement implements LinkedMeasurement {
	public final String name;
	public final long ID;
	Consumer<InstanceMeasurement> stopHandler;

	InstanceMeasurement(final String name, final long id, Consumer<InstanceMeasurement> stopHandler) {
		this.name = name;
		this.ID = id;
		this.stopHandler = stopHandler;
	}

	@Override
	public void end() {
		stopHandler.accept(this);
		stopHandler = InstanceMeasurement::noop;
	}

	@SuppressWarnings("unused")
	static void noop(InstanceMeasurement measurement) {
		// NOOP
	}

	@Override
	public void linkTo(LinkedMeasurement other) {
		// TODO Auto-generated method stub

	}

}
