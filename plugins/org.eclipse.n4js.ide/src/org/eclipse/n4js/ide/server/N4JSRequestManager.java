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
package org.eclipse.n4js.ide.server;

import java.util.concurrent.CompletableFuture;

import org.eclipse.n4js.ide.N4JSIdeDataCollectors;
import org.eclipse.n4js.ide.xtext.server.concurrent.XRequestManager;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;

import com.google.inject.Singleton;

/**
 * A request manager that will add measurements around submitted requests.
 */
@Singleton
public class N4JSRequestManager extends XRequestManager {

	@Override
	public synchronized <V> CompletableFuture<V> runRead(String description,
			Function1<? super CancelIndicator, ? extends V> cancellable) {
		return super.runRead(description, (ci) -> {
			try (Measurement parent = N4JSIdeDataCollectors.dcN4JSRequest.getMeasurement()) {
				try (Measurement measurement = N4JSIdeDataCollectors.request(description).getMeasurement()) {
					return cancellable.apply(ci);
				}
			}
		});
	}

	@Override
	public synchronized <U, V> CompletableFuture<V> runWrite(String description, Function0<? extends U> nonCancellable,
			Function2<? super CancelIndicator, ? super U, ? extends V> cancellable) {
		return super.runWrite(description, () -> {
			try (Measurement parent = N4JSIdeDataCollectors.dcN4JSRequest.getMeasurement();
					Measurement measurement = N4JSIdeDataCollectors.request(description).getMeasurement();
					Measurement sub = N4JSIdeDataCollectors.request(description, "nonCancellable").getMeasurement()) {
				return nonCancellable.apply();
			}
		}, (ci, val) -> {
			try (Measurement parent = N4JSIdeDataCollectors.dcN4JSRequest.getMeasurement();
					Measurement measurement = N4JSIdeDataCollectors.request(description).getMeasurement();
					Measurement sub = N4JSIdeDataCollectors.request(description, "cancellable").getMeasurement()) {
				return cancellable.apply(ci, val);
			}
		});
	}

}
