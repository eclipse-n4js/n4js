/**
 * Copyright (c) 2017 Marcus Mews.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Marcus Mews - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
@SuppressWarnings("javadoc")
public enum ControlFlowType {
	Return, Throw, Break, Continue, CatchesAll, CatchesRuntimeExceptions, Successor, Loop;

	static public final ControlFlowType[] LoopTypes = { Loop, Continue };
	static public final ControlFlowType[] NonLoopTypes = { Successor, Break, Throw, Return };

	static public List<ControlFlowType> filter(Iterable<ControlFlowType> list, ControlFlowType... onlyThese) {
		List<ControlFlowType> result = new LinkedList<>();
		for (ControlFlowType cft : list) {
			for (ControlFlowType onlyThis : onlyThese) {
				if (onlyThis == cft) {
					result.add(cft);
				}
			}
		}
		return result;
	}

	public boolean isInOrEmpty(ControlFlowType[] flowTypes) {
		if (flowTypes == null || flowTypes.length == 0) {
			return true;
		}
		for (ControlFlowType cft : flowTypes) {
			if (this == cft) {
				return true;
			}
		}
		return false;
	}
}
