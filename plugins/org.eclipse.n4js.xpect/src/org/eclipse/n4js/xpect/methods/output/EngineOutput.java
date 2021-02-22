/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xpect.methods.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DataObject, used as container for standard output and error output captured during
 * {@link XpectN4JSES5TranspilerHelper} run.
 *
 * Once initiated values cannot be modified.
 */
public class EngineOutput {
	private final List<String> std;
	private final List<String> err;

	/**
	 * Creates instance with empty outputs
	 */
	public EngineOutput() {
		std = new ArrayList<>();
		err = new ArrayList<>();
	}

	/**
	 * Creates instance which outputs are initiated with provided initial values
	 *
	 * @param oStd
	 *            initial value of the stdout.
	 * @param oErr
	 *            initial value of the stderr.
	 */
	public EngineOutput(List<String> oStd, List<String> oErr) {
		std = Collections.unmodifiableList(oStd);
		err = Collections.unmodifiableList(oErr);
	}

	/**
	 *
	 * @return Returns an unmodifiable view of the engine standard output
	 */
	public List<String> getStdOut() {
		return std;
	}

	/**
	 *
	 * @return Returns an unmodifiable view of the engine error output
	 */
	public List<String> getErrOut() {
		return err;
	}
}
