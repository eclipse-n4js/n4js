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

import java.util.List;
import java.util.stream.Collectors;

/** */
public class SomeDataAccess {
	private static final DataCollectors collectors = DataCollectors.getInstance();

	/** */
	public static List<SomeDataPoint> gRawData2(String key) {

		DataCollector dataCollector = collectors.getSimpleCollectors().get(key);

		return dataCollector.getData().stream().sorted(SomeDataAccess::compareDesc)
				.collect(Collectors.toList());
	}

	private static int compareDesc(SomeDataPoint data1, SomeDataPoint data2) {
		return Long.compare(data2.elapsedNanos, data1.elapsedNanos);
	}

}
