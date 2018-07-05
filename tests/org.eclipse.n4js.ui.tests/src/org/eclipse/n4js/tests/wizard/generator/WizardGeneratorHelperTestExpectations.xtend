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
package org.eclipse.n4js.tests.wizard.generator

/**
 * Package.json file contents expectation of {@link WizardGeneratorHelperPluginTest}.
 */
class WizardGeneratorHelperTestExpectations {
	public static val String ONE_DEPENDENCY = 
		'''
	{
		"name": "Test",
		"version": "0.0.1",
		"n4js": {
			"projectType": "library",
			"vendorId": "org.eclipse.n4js",
			"vendorName": "Eclipse N4JS Project",
			"sources": {
				"source": [
					"src"
				]
			},
			"output": "src-gen"
		},
		"dependencies": {
			"dep1": "*"
		}
	}'''
	
	public static val String TWO_DEPENDENCIES = 
		'''
	{
		"name": "Test",
		"version": "0.0.1",
		"n4js": {
			"projectType": "library",
			"vendorId": "org.eclipse.n4js",
			"vendorName": "Eclipse N4JS Project",
			"sources": {
				"source": [
					"src"
				]
			},
			"output": "src-gen"
		},
		"dependencies": {
			"dep1": "*",
			"dep2": "*"
		}
	}'''
	
	public static val String ONE_REQUIRED_RUNTIME_LIBRARIES = 
		'''
	{
		"name": "Test",
		"version": "0.0.1",
		"n4js": {
			"projectType": "library",
			"vendorId": "org.eclipse.n4js",
			"vendorName": "Eclipse N4JS Project",
			"sources": {
				"source": [
					"src"
				]
			},
			"output": "src-gen",
			"requiredRuntimeLibraries": [
				"req-lib"
			]
		}
	}'''
	public static val String TWO_REQUIRED_RUNTIME_LIBRARIES = 
		'''
	{
		"name": "Test",
		"version": "0.0.1",
		"n4js": {
			"projectType": "library",
			"vendorId": "org.eclipse.n4js",
			"vendorName": "Eclipse N4JS Project",
			"sources": {
				"source": [
					"src"
				]
			},
			"output": "src-gen",
			"requiredRuntimeLibraries": [
				"req-lib",
				"req-lib-2"
			]
		}
	}'''
}
