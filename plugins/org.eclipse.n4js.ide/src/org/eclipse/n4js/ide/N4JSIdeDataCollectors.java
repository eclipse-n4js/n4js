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

import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;

/**
 * Data collectors for the N4JS language server.
 */
public class N4JSIdeDataCollectors {

	/**
	 * Base collector for all requests
	 */
	public static final DataCollector dcN4JSRequest = create("LSP Request");

	/**
	 * Generic facility to obtain a data collector for a given request category.
	 */
	public static DataCollector request(String category) {
		return create(category, dcN4JSRequest);
	}

	/**
	 * Generic facility to obtain a data collector for a given request category.
	 */
	public static DataCollector request(String category, String sub) {
		return create(sub, create(category, dcN4JSRequest));
	}

	private static DataCollector create(String key) {
		return DataCollectors.INSTANCE.getOrCreateDataCollector(key);
	}

	private static DataCollector create(String key, DataCollector parent) {
		return DataCollectors.INSTANCE.getOrCreateDataCollector(key, parent);
	}
}
