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
package org.eclipse.n4js.ide.server.concurrent;

import java.util.function.Function;

import org.eclipse.n4js.ide.N4JSIdeDataCollectors;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.xtext.ide.server.QueuedExecutorService;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Singleton;

/**
 * Extends {@link QueuedExecutorService} by performance measurements.
 */
@Singleton
public class N4JSQueuedExecutorService extends QueuedExecutorService {

	@Override
	protected <T> QueuedTask<T> createQueuedTask(Object queueId, String description,
			Function<CancelIndicator, T> task) {

		return super.createQueuedTask(queueId, description, ci -> {
			try (Measurement parent = N4JSIdeDataCollectors.dcN4JSRequest.getMeasurement();
					Measurement measurement = N4JSIdeDataCollectors.request(description).getMeasurement()) {
				return task.apply(ci);
			}
		});
	}
}
