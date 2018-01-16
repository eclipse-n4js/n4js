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
 * Template for generating N4JS ELF code for node, see N4JSDesign document, chap. 17 : Execution, section 17.2 N4JS Execution And
 * Linking File. 
 * 
 * This file is responsible for bootstrapping
 * the node engine by configuring symbolic links to dependencies, adding user provided data
 * to the global scope, initializing init modules and invoking node runtime environment.
 * 
 * @param list
 *            of runtime modules to be bootstrapped
 * @param entryPoint
 *            of the code to be executed
 * @param executionData
 *            that is expected by execution module
 * @return elf data in format for used JS engine
 */
class NodeBootScriptTemplate {

	/**
	 * {@code pathNodeModules} is {@code /absolute/path/to/node_modules/}
	 * {@code fileToInvoke} is {@code ./run.js}
	 */
	def static String getRunScriptCore(String pathNodeModules, String executionData, List<String> initModules,
		String fileToInvoke, List<Pair<String, String>> path2names) '''
		«IF !path2names.isNullOrEmpty»
			//link dependencies
			const path = require('path')
			const fs = require('fs')
			const os = require("os");
			«FOR path2name : path2names SEPARATOR "\n"»
				if(!fs.existsSync('«pathNodeModules»/«path2name.value»'))
					fs.symlinkSync('«path2name.key»', '«pathNodeModules»/«path2name.value»', 'dir');
			«ENDFOR»
		«ELSE»
			//no dependencies to link
		«ENDIF»
		
		«IF !executionData.isNullOrEmpty»
			/*
			 * In this form execution module needs to read prop '$executionData' from global scope (also would be good idea
			 * to remove it). It would be possible that execution module exports function that takes this data as parameter
			 * but then we need to change order of things in ELF file, that is execution module has to be loaded, its export
			 * function assigned to variable and called with this data below.
			 *
			 * keep it in sync
			 */
			global.$executionData = «executionData»;
		«ELSE»
			//no execution data provided
		«ENDIF»
		
		«IF !initModules.isNullOrEmpty»g
				// invoke init modules
				«FOR initModule : initModules SEPARATOR "\n"»
					require('«initModule»')
				«ENDFOR»
				//no init modules to invoke
		«ENDIF»
		
		require('«fileToInvoke»')
	'''
}
