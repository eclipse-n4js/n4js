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

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class SeriesContainer {
	private final List<Series> series = new LinkedList<>();

	public SeriesContainer(final List<Series> series) {
		this.series.addAll(series);
	}

	public SeriesContainer(Series... series) {
		for (Series s : series) {
			this.series.add(s);
		}
	}

	public List<Series> getSeries() {
		return new LinkedList<>(series);
	}
}
