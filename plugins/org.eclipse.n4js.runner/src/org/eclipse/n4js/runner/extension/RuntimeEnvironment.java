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
package org.eclipse.n4js.runner.extension;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.n4js.projectModel.names.N4JSProjectName;

/**
 * Runner environments, correspond to runtime environments in N4JS.
 */
public enum RuntimeEnvironment {

	// IMPORTANT:
	// when changing the following enum literals, be sure to update the extension point schema located
	// at /org.eclipse.n4js.runner/schema/org.eclipse.n4js.runner.extension.runnerdescriptor.exsd

	/** base env */
	ES5("n4js-es5"),
	/** V8 env */
	V8("RE_V8"),
	/** Node.js env */
	NODEJS("n4js-node"),
	/** Node.js mangelhaft test env */
	NODEJS_MANGELHAFT("n4js-node-mangelhaft"),
	/** Chrome env */
	CHROME("n4js-chrome"),
	/** IOJS */
	IOJS("RE_IOJS");

	/**
	 * We wrap map in the class to have it initialized before enum instances are initialized.
	 *
	 * @see <a href="http://stackoverflow.com/a/27703839">http://stackoverflow.com/a/27703839</a>
	 * @see <a href="https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom">Initialization on demand
	 *      holder idiom</a>
	 */
	private static class DataMap {
		static Map<N4JSProjectName, RuntimeEnvironment> data = new HashMap<>();
	}

	private final N4JSProjectName projectName;

	private RuntimeEnvironment(String projectName) {
		this.projectName = new N4JSProjectName(projectName);
		DataMap.data.put(this.projectName, this);
	}

	/** @return {@link String} value */
	public N4JSProjectName getProjectName() {
		return this.projectName;
	}

	/** @return instance corresponding to provided representation */
	public static RuntimeEnvironment fromProjectName(N4JSProjectName representation) {
		return DataMap.data.get(representation);
	}
}
