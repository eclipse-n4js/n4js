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
package org.eclipse.n4js.ui.wizard.project

import org.eclipse.n4js.projectDescription.ProjectType
import org.eclipse.n4js.projectDescription.SourceContainerType
import org.eclipse.n4js.packagejson.PackageJsonBuilder

import static org.eclipse.n4js.packagejson.PackageJsonProperties.VERSION
import org.eclipse.n4js.utils.ProjectDescriptionUtils
import org.eclipse.n4js.projectModel.names.N4JSProjectName

/**
 * Basic Xtend templates for new project wizard.
 */
class N4JSNewProjectFileTemplates {

	/**
	 * Returns the contents of the greeter class module for given projectName
	 *
	 * @param projectName The name of the project
	 * @param safeProjectName The name of the project transformed to be safe to use as class identifier
	 */
	static def getSourceFileWithGreeterClass(String projectName, String safeProjectName) '''
		export class GreeterFor_«safeProjectName» {
		
		    public greet() {
		        console.log("Hello World from '«projectName»'!");
		    }
		
		}
		
		var greeter = new GreeterFor_«safeProjectName»();
		greeter.greet();
		//right click this module and select "Run As -> Launch in Node.js" to see
		//"Hello World from '«projectName»'!"
		
	'''

	/**
	 * Returns the contents of the greeter test class module for given projectName
	 *
	 * @param safeProjectName The name of the project transformed to be safe to use as class identifier
	 */
	static def getSourceFileWithTestGreeter(String safeProjectName) '''
		import { Assert } from "org/eclipse/n4js/mangelhaft/assert/Assert"
		
		export public class Test_«safeProjectName» {
		
			@Test
			testPass() {
				Assert.isTrue(true, "Testing works!");
			}
		
			@Test
			testFail() {
			Assert.fail("Test failed.");
			}
		
		}
		//right click this module and select "Run As -> Test in Node.js" to see the test results
	'''

	/**
	 * Returns the project description file contents for the given project info (package.json).
	 */
	static def getProjectDescriptionContents(N4JSProjectInfo projectInfo) {
		val projectName = 
			ProjectDescriptionUtils.convertEclipseProjectNameToN4JSProjectName(projectInfo.projectName);
		
		// configure basic properties
		val builder = PackageJsonBuilder.newBuilder()
			.withName(projectName)
			.withVersion(VERSION.defaultValue)
			.withType(projectInfo.projectType)
			.withOutput(projectInfo.outputFolder)
			.withVendorId(projectInfo.vendorId);
		
		// configure source container information
		projectInfo.sourceFolders.forEach[sourceFolder | builder.withSourceContainer(SourceContainerType.SOURCE, sourceFolder)];
		projectInfo.externalSourceFolders.forEach[sourceFolder | builder.withSourceContainer(SourceContainerType.EXTERNAL, sourceFolder)];
		projectInfo.testSourceFolders.forEach[sourceFolder | builder.withSourceContainer(SourceContainerType.TEST, sourceFolder)];
		
		// add LIBRARY-specific properties
		if (ProjectType.LIBRARY.equals(projectInfo.projectType)) {
			if (projectInfo.implementationId !== null) {
				builder.withImplementationId(projectInfo.implementationId);
			}
			projectInfo.implementedProjects.forEach[p | builder.withImplementedProject(p)]
		}
		
		// add TEST-specific properties
		if (ProjectType.TEST.equals(projectInfo.projectType)) {
			projectInfo.testedProjects.forEach[p | builder.withTestedProject(p)]
		}
		
		// add dependencies
		projectInfo.projectDependencies.forEach[dep |
			builder.withDependency(new N4JSProjectName(dep));
		]
		projectInfo.projectDevDependencies.forEach[dep |
			builder.withDevDependency(new N4JSProjectName(dep));
		]
		
	
		// finally build textual JSON project description (package.json content)
		return builder.build();
	}
}
