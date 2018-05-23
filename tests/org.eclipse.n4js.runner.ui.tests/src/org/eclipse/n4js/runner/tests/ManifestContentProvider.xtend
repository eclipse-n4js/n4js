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
package org.eclipse.n4js.runner.tests

import com.google.common.base.Optional
import org.eclipse.n4js.n4mf.ProjectType
import org.eclipse.n4js.runner.^extension.RuntimeEnvironment

import static com.google.common.base.CaseFormat.*
import static java.lang.String.valueOf
import static org.eclipse.n4js.n4mf.ProjectType.API

/**
 * Class for providing content for various type of N4JS manifests.
 */
class ManifestContentProvider {

	/**
	 * Creates and returns with the N4JS manifest content based on the given arguments.
	 * @param projectId the projectId of the project.
	 * @param type the type of the N4JS project.
	 * @param extendedRE the optional extended runtime environment.
	 * @param projectDependencies an iterable of direct project dependnencies for the N4JS project.
	 * @param providedRL an iterable of provided runtime libraries.
	 * @param requiredRL an iterable of required runtime libraries.
	 * @return the N4JS manifest content as a string.
	 */
	def String getContent(String projectId, ProjectType type, Optional<RuntimeEnvironment> extendedRE,
		Iterable<String> projectDependencies, Iterable<String> providedRL, Iterable<String> requiredRL,
		Optional<String> implementationId, Iterable<String> implementedProjects
	) '''
		ProjectId: «projectId»
		ProjectType: «IF API == type»«API»«ELSE»«UPPER_UNDERSCORE.to(LOWER_CAMEL, valueOf(type))»«ENDIF»
		ProjectVersion: 0.0.1-SNAPSHOT
		VendorId: org.eclipse.n4js
		VendorName: "Eclipse N4JS Project"
		«'''ProvidedRuntimeLibraries'''.getEnumeration(providedRL)»
		«'''ProjectDependencies'''.getEnumeration(projectDependencies)»
		«'''RequiredRuntimeLibraries'''.getEnumeration(requiredRL)»
		«IF extendedRE.present»ExtendedRuntimeEnvironment : «extendedRE.get.getProjectId»«ENDIF»
		«IF implementationId.isPresent»ImplementationId: «implementationId.get»«ENDIF»
		«'''ImplementedProjects'''.getEnumeration(implementedProjects)»
		Output: "src-gen"
		Sources {
			source {
				"src"
			}
		}
	'''

	private def getEnumeration(CharSequence name, Iterable<String> it) '''
	«IF !empty»
	«name» {
		«FOR i : it SEPARATOR ","»
		«i»
		«ENDFOR»
	}
	«ENDIF»
	'''

}
