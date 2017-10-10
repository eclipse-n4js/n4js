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

import java.util.Collection;
import java.util.List;

/**
 * Container for collecting {@link SomeDataPoint}s based on {@link Measurement}s. Can be used stand alone to gather
 * stand alone data. Can have {@link #getParent()} and / or {@link #getChildren()} which allows to express nested or
 * more elaborated data relations.
 */
public interface DataCollector {
	public DataCollector getParent();

	public Collection<DataCollector> getChildren();

	public Measurement getMeasurement(String name);

	public List<SomeDataPoint> getData();

	DataCollector getChild(String key);

	void addChild(String key, DataCollector child);

	Collection<String> childrenKeys();

	void setPaused(boolean paused);

	public void purgeData();
}
