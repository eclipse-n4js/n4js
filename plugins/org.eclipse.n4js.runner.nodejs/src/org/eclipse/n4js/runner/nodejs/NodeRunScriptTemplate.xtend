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

/**
 * 
 */
class NodeRunScriptTemplate {

	/**
	 * {@code pathNodeModules} is {@code /absolute/path/to/node_modules/}
	 * {@code fileToInvoke} is {@code ./run.js}
	 */
	def static String getRunScriptCore(String pathNodeModules, String fileToInvoke, String[] pathsToLink) '''
		const path = require('path')
		const fs = require('fs')
		const os = require("os");
		
		const execNodeModules = '«pathNodeModules»'
		const coreProjectPaths =  [ «FOR path : pathsToLink SEPARATOR ", " »'«path»'«ENDFOR»]
		
		function symlinkPaths(base, projectPaths){
		    if(!fs.existsSync(base)) throw new Error("cannot locate " + base)
		    projectPaths.forEach((projectPath)=>{
		        let parsedPath = path.parse(projectPath)
		        const linkPath = path.join(base, parsedPath.name);
		        if(!fs.existsSync(linkPath))
		            fs.symlinkSync(projectPath, linkPath, 'dir')
		    })
		}
		
		symlinkPaths(execNodeModules, coreProjectPaths);
		
		require('./«fileToInvoke»')
	'''
}
