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
package org.eclipse.n4js.flowgraphs.analyses;

import java.util.Collection;
import java.util.LinkedList;

/**
 *
 */
abstract public class DataFlowVisitorHost extends GraphVisitorInternal {
	final Collection<DataFlowVisitor> dfVisitors;
	final Collection<Assumption> simpleAssumptions = new LinkedList<>();
	final Collection<AssumptionWithContext> contextAssumptions = new LinkedList<>();

	DataFlowVisitorHost(Collection<DataFlowVisitor> dfVisitors) {
		this.dfVisitors = dfVisitors;

	}
}
