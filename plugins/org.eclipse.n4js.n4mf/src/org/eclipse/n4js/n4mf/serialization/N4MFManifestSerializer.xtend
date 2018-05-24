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
package org.eclipse.n4js.n4mf.serialization

import org.eclipse.n4js.n4mf.BootstrapModule
import org.eclipse.n4js.n4mf.DeclaredVersion
import org.eclipse.n4js.n4mf.ModuleFilter
import org.eclipse.n4js.n4mf.ModuleFilterSpecifier
import org.eclipse.n4js.n4mf.ModuleFilterType
import org.eclipse.n4js.n4mf.ModuleLoader
import org.eclipse.n4js.n4mf.ProjectDependency
import org.eclipse.n4js.n4mf.ProjectDependencyScope
import org.eclipse.n4js.n4mf.ProjectDescription
import org.eclipse.n4js.n4mf.ProjectReference
import org.eclipse.n4js.n4mf.ProjectType
import org.eclipse.n4js.n4mf.SourceContainerDescription
import org.eclipse.n4js.n4mf.SourceContainerType
import org.eclipse.n4js.n4mf.VersionConstraint

/**
 * A string serializer for N4MF {@link ProjectDescription}s.
 */
public class N4MFManifestSerializer {
	/**
	 * Creates and returns the textual manifest representation of the given {@link ProjectDescription}.
	 */
	public def String serialize(ProjectDescription d) {
		return '''
			ProjectId: «d.projectId»
			ProjectType: «serializeProjectType(d.projectType)»
			ProjectVersion: «serializeDeclaredVersion(d.projectVersion)»
			VendorId: «d.vendorId»
			«IF d.vendorName !== null»VendorName: «d.vendorName» «ENDIF»
			«IF d.mainModule !== null»MainModule: «d.mainModule» «ENDIF»
			
			«IF d.extendedRuntimeEnvironment !== null
				»ExtendedRuntimeEnvironment: «serializeProjectReference(d.extendedRuntimeEnvironment)» «ENDIF»
			
			«IF d.providedRuntimeLibraries !== null && !d.providedRuntimeLibraries.empty
				»ProvidedRuntimeLibraries «d.providedRuntimeLibraries
					.map[ref | serializeProjectReference(ref)].toCommaList»«ENDIF»
			
			«IF d.requiredRuntimeLibraries !== null && !d.requiredRuntimeLibraries.empty
				»RequiredRuntimeLibraries «d.requiredRuntimeLibraries
					.map[ref | serializeProjectReference(ref)].toCommaList»«ENDIF»
			
			«IF d.projectDependencies !== null && !d.projectDependencies.empty
				»ProjectDependencies «d.projectDependencies
					.map[dep | serializeProjectDependency(dep)].toCommaList»«ENDIF»
			
			«IF d.implementationId !== null»ImplementationId: «d.implementationId» «ENDIF»
			
			
			«IF d.implementedProjects !== null && !d.implementedProjects.empty
				»ImplementedProjects «d.implementedProjects
					.map[ref | serializeProjectReference(ref)].toCommaList»«ENDIF»
			
			«IF d.initModules !== null && !d.initModules.empty
				»InitModules «d.initModules
					.map[m | serializeBootstrapModule(m)].toCommaList»«ENDIF»
			
			«IF d.execModule !== null»ExecModule: «serializeBootstrapModule(d.execModule)» «ENDIF»
			
			«IF d.outputPathRaw !== null»Output: «d.outputPathRaw» «ENDIF»
			
			«IF d.libraryPathsRaw !== null && !d.libraryPathsRaw.empty
				»Libraries «d.libraryPathsRaw
							.toCommaList»«ENDIF»
			
			«IF d.resourcePathsRaw !== null && !d.resourcePathsRaw.empty
				»Resources «d.resourcePathsRaw
					.toCommaList»«ENDIF»
			
			«IF d.libraryPathsRaw !== null && !d.sourceContainers.empty
				»Sources «d.sourceContainers
							.map[sc | serializeSourceContainerDescription(sc)]
							.toCommaList»«ENDIF»

			«IF d.moduleFilters !== null && !d.moduleFilters.empty
				»ModuleFilters «d.moduleFilters
					.map[f | serializeModuleFilter(f)]
					.toCommaList»«ENDIF»
			
			«IF d.testedProjects !== null && !d.testedProjects.empty
				»TestedProjects «d.testedProjects
					.map[ref | serializeProjectDependency(ref)].toCommaList»«ENDIF»
			
			«IF d.moduleLoader !== null»ModuleLoader: «serializeModuleLoader(d.moduleLoader)» «ENDIF»
		'''
	}

	/** 
	 * Returns a comma-separated list of the given String elements (each element appears on a new line).
	 * 
	 * The list is furthermore framed with curly braces.
	 */
	private def String toCommaList(Iterable<String> elements) {
		return '''{
			«elements.join(",\n")»
		}
		'''
	}

	/**
	 * ProjectIdWithOptionalVendor
	 * 	(versionConstraint=VersionConstraint)?
	 * 	(declaredScope=ProjectDependencyScope)?
	 */
	/** Returns the syntactic representation of the given {@link ProjectDependency} */
	private def String serializeProjectDependency(ProjectDependency dependency) {
		return '''«serializeProjectReference(dependency)»«IF (dependency.versionConstraint !== null)
		» «serializeVersionConstraint(dependency.versionConstraint)»«ENDIF»«IF dependency.declaredScope !== null
		» «serializeProjectDependencyScope(dependency.scope)»«ENDIF»'''
	}

	/*
	 * (
	 * 	(exclLowerBound?='(' | '[')  lowerVersion=DeclaredVersion
	 * 			((',' upperVersion=DeclaredVersion (exclUpperBound?=')' | ']') )? | ')')
	 * 	) | lowerVersion=DeclaredVersion
	 */
	private def String serializeVersionConstraint(VersionConstraint versionConstraint) {
		if (versionConstraint.exclLowerBound || versionConstraint.exclUpperBound) {
			var constraintString = "";
			constraintString += if (versionConstraint.exclLowerBound) "(" else "[";
			constraintString += serializeDeclaredVersion(versionConstraint.lowerVersion);
			if (versionConstraint.upperVersion !== null)
				constraintString += "," + serializeDeclaredVersion(versionConstraint.upperVersion)
			constraintString += if (versionConstraint.exclUpperBound) ")" else "]";
			return constraintString;
		} else {
			return serializeDeclaredVersion(versionConstraint.lowerVersion);
		}
	}

	/** Returns the syntactic representation of the given {@link ProjectReference} */
	private def String serializeProjectReference(ProjectReference reference) {
		return '''«reference.declaredVendorId»:«reference.projectId»''';
	}

	/** Returns the syntactic representation of the given {@link DeclaredVersion}. */
	private def String serializeDeclaredVersion(DeclaredVersion version) {
		var versionString = "";
		versionString += version.major;
		versionString += "." + version.minor ?: "";
		versionString += "." + version.micro ?: "";
		versionString += "-" + version.qualifier;
		return versionString;
	}

	/** Returns the syntactic representation of the given {@link BootstrapModule} */
	private def String serializeBootstrapModule(BootstrapModule module) {
		// moduleSpecifierWithWildcard=STRING ('in' sourcePath=STRING)?
		return '''«module.moduleSpecifierWithWildcard»«IF module.sourcePath !== null
		» in «module.moduleSpecifierWithWildcard»«ENDIF»''';
	}
	
	private def String serializeModuleFilter(ModuleFilter filter) {
		return '''«serializeModuleFilterType(filter.moduleFilterType)»
			«filter.moduleSpecifiers
				.map[s | serializeModuleFilterSpecifier(s)]
				.toCommaList»
		}'''
	}
	
	/** Returns the syntactic representation of the given {@link SourceContainerDescription} */
	private def String serializeSourceContainerDescription(SourceContainerDescription description) {
		return '''«serializeSourceContainerType(description.sourceContainerType)»
			«description.pathsRaw.toCommaList»
		}'''
	}

	/** Returns the syntactic representation of the given {@link ModuleFilterSpecifier} */
	private def String serializeModuleFilterSpecifier(ModuleFilterSpecifier specifier) {
		// moduleSpecifierWithWildcard=STRING ('in' sourcePath=STRING)?
		return '''«specifier.moduleSpecifierWithWildcard»«IF specifier.sourcePath !== null
		» in «specifier.moduleSpecifierWithWildcard»«ENDIF»''';
	}
	
	/** Returns the syntactic representation of the given {@link SourceContainerType} */
	private def String serializeSourceContainerType(SourceContainerType type) {
		return switch (type) {
			case EXTERNAL: "external"
			case SOURCE: "source"
			case TEST: "test"
		}
	}
	
	/** Returns the syntactic representation of the given {@link ModuleFilterType} */
	private def String serializeModuleFilterType(ModuleFilterType type) {
		return switch(type) {
			case NO_VALIDATE: "noValidate",
			case NO_MODULE_WRAPPING: "noModuleWrap"
		};
	}

	/** Returns the syntactic representation of the given {@link ProjectType} */
	private def String serializeProjectType(ProjectType type) {
		return switch (type) {
			case APPLICATION: "application"
			case PROCESSOR: "processor"
			case LIBRARY: "library"
			case API: "API"
			case RUNTIME_ENVIRONMENT: "runtimeEnvironment"
			case RUNTIME_LIBRARY: "runtimeLibrary"
			case TEST: "test"
			case VALIDATION: "validation"
		}
	}

	/** Returns the syntactic representation of the given {@link ProjectDependencyScope} */
	private def String serializeProjectDependencyScope(ProjectDependencyScope scope) {
		return if (scope == ProjectDependencyScope.COMPILE) "compile" else "test"
	}

	/** Returns the syntactic representation of the given {@link ModuleLoader} */
	private def String serializeModuleLoader(ModuleLoader loader) {
		return switch (loader) {
			case N4JS: "n4js"
			case COMMONJS: "commonjs"
			case NODE_BUILTIN: "node_builtin"
		};
	}
}