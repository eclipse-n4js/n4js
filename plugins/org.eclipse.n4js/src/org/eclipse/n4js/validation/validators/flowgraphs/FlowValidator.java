/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.validation.validators.flowgraphs;

import org.eclipse.n4js.flowgraphs.FlowAnalyser;
import org.eclipse.n4js.validation.validators.N4JSFlowgraphValidator;

/**
 * Abstraction of all flow graph related analyses and validations.
 */
public interface FlowValidator {

	/** @return the {@link FlowAnalyser} that gets triggered during the CF/DF analysis. */
	FlowAnalyser createFlowAnalyser();

	/**
	 * After the CF/DF analysis was performed, the collected data is evaluated and issue markers are created in this
	 * method.
	 *
	 * @param adv
	 *            a reference to the {@link N4JSFlowgraphValidator} to add issues
	 */
	void checkResults(N4JSFlowgraphValidator adv);

}
