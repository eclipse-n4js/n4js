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
package org.eclipse.n4js.runner.nodejs

import java.util.List

/**
 * Template for generating NodeJS boot file. This file is responsible for bootstrapping
 * the node engine by configuring symlinks to dependencies, adding user provided data
 * to the global scope, and invoking node runtime environment.
 */
class NodeBootScriptTemplate {

	/**
	 * {@code pathNodeModules} is {@code /absolute/path/to/node_modules/}
	 * {@code fileToInvoke} is {@code ./run.js}
	 */
	def static String getRunScriptCore(String pathNodeModules, String fileToInvoke, List<Pair<String, String>> path2names) '''
		const path = require('path')
		const fs = require('fs')
		const os = require("os");
		
		«FOR path2name : path2names SEPARATOR "\n"»
		if(!fs.existsSync('«pathNodeModules»/«path2name.value»'))
			fs.symlinkSync('«path2name.key»', '«pathNodeModules»/«path2name.value»', 'dir');«ENDFOR»
		
		require('./«fileToInvoke»')
	'''
}
