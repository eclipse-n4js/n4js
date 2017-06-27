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
package org.eclipse.n4js.jsdoc2spec.ui.adoc;

/**
 * Data holder for the configuration done by the user on the {@link SpecConfigOutputPage}.
 */
class ConfigOutput {
	final boolean writeAdocFiles;

	ConfigOutput(boolean writeAdocFiles) {

		this.writeAdocFiles = writeAdocFiles;
	}

	boolean isGeneratingSomething() {
		boolean isGenSomething = false;
		isGenSomething |= writeAdocFiles;
		return isGenSomething;
	}
}
