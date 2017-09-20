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
package org.eclipse.n4js.smith.dash.data;

/**
 * Interface for measurements that can be linked together. Basic block for lists / graphs of the measurements.
 */
public interface LinkedMeasurement extends Measurement {

	@Override
	public void end();

	/** Links this measurement to other measurement. Unidirectional link. */
	public void linkTo(LinkedMeasurement other);

}
