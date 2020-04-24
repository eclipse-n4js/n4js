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
package org.eclipse.n4js.ide;

import static org.eclipse.n4js.smith.DataCollectors.INSTANCE;

import org.eclipse.n4js.smith.DataCollector;

/**
 * Data collectors for the N4JS language server.
 */
public class N4JSIdeDataCollectors {

	/**
	 * Base collector for all requests
	 */
	public static final DataCollector dcN4JSRequest = INSTANCE.getOrCreateSerialDataCollector("LSP Request");

	/**
	 * Generic facility to obtain a data collector for a given request category.
	 */
	public static DataCollector request(String category) {
		return create(dcN4JSRequest, category);
	}

	/**
	 * Generic facility to obtain a data collector for a given request category.
	 */
	public static DataCollector request(String category, String sub) {
		return create(request(category), sub);
	}

	private static DataCollector create(DataCollector parent, String key) {
		return INSTANCE.getOrCreateDataCollector(key, parent);
	}
}
