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
import org.eclipse.n4js.n4mf.serializer.N4MFSemanticSequencer

/**
 * A string serializer for N4MF {@link ProjectDescription}s.
 * 
 * This serializer is not meant as a full replacement for {@link N4MFSemanticSequencer} but
 * rather as a simple alternative for serializing {@link ProjectDescription} model elements.
 */
public class N4MFManifestSerializer {
	/**
	 * Creates and returns the textual manifest representation of the given {@link ProjectDescription}.
	 * 
	 * For {@link DeclaredVersion}s, trailing zeroes are omitted. Furthermore, the serialization will not
	 * explicitly configure default values for {@link ProjectDependencyScope} or {@link ModuleLoader}.
	 */
	public def String serialize(ProjectDescription d) {
		return '''
			«IF d.projectId !== null»ProjectId: «d.projectId»«ENDIF»
			«IF d.projectType !== null»ProjectType: «serializeProjectType(d.projectType)»«ENDIF»
			«IF d.projectVersion !== null»ProjectVersion: «serializeDeclaredVersion(d.projectVersion)»«ENDIF»
			«IF d.vendorId !== null»VendorId: «d.vendorId»«ENDIF»
			«IF d.vendorName !== null»VendorName: «d.vendorName.asStringLiteral»«ENDIF»
			«IF d.mainModule !== null»MainModule: «d.mainModule.asStringLiteral»«ENDIF»
			«IF d.extendedRuntimeEnvironment !== null
				»ExtendedRuntimeEnvironment: «serializeProjectReference(d.extendedRuntimeEnvironment)»«ENDIF»
			«IF d.providedRuntimeLibraries !== null && !d.providedRuntimeLibraries.empty
				»ProvidedRuntimeLibraries «d.providedRuntimeLibraries
					.map[ref | serializeProjectReference(ref)].toCommaList»«ENDIF»
			«IF d.requiredRuntimeLibraries !== null && !d.requiredRuntimeLibraries.empty
				»RequiredRuntimeLibraries «d.requiredRuntimeLibraries
					.map[ref | serializeProjectReference(ref)].toCommaList»«ENDIF»
			«IF d.projectDependencies !== null && !d.projectDependencies.empty
				»ProjectDependencies «d.projectDependencies
					.map[dep | serializeProjectDependency(dep)].toCommaList»«ENDIF»
			«IF d.implementationId !== null»ImplementationId: «d.implementationId»«ENDIF»
			«IF d.implementedProjects !== null && !d.implementedProjects.empty
				»ImplementedProjects «d.implementedProjects
					.map[ref | serializeProjectReference(ref)].toCommaList»«ENDIF»
			«IF d.initModules !== null && !d.initModules.empty
				»InitModules «d.initModules
					.map[m | serializeBootstrapModule(m)].toCommaList»«ENDIF»
			«IF d.execModule !== null»ExecModule: «serializeBootstrapModule(d.execModule)»«ENDIF»
			«IF d.outputPathRaw !== null»Output: «d.outputPathRaw.asStringLiteral»«ENDIF»
			«IF d.libraryPathsRaw !== null && !d.libraryPathsRaw.empty
				»Libraries «d.libraryPathsRaw
							.map[p | p.asStringLiteral]
							.toCommaList»«ENDIF»
			«IF d.resourcePathsRaw !== null && !d.resourcePathsRaw.empty
				»Resources «d.resourcePathsRaw
							.map[p | p.asStringLiteral]
							.toCommaList»«ENDIF»
			«IF d.libraryPathsRaw !== null && !d.sourceContainers.empty
			»Sources {
				«d.sourceContainers
					.map[sc | serializeSourceContainerDescription(sc)]
					.join("\n")»
			}
			«ENDIF»
			«IF d.moduleFilters !== null && !d.moduleFilters.empty
			»ModuleFilters {
				«d.moduleFilters
					.map[f | serializeModuleFilter(f)]
					.join("\n")»
			}
			«ENDIF»
			«IF d.testedProjects !== null && !d.testedProjects.empty
				»TestedProjects «d.testedProjects
					.map[ref | serializeProjectDependency(ref)].toCommaList»«ENDIF»
			«IF d.moduleLoader !== null && d.moduleLoader != ModuleLoader.N4JS
				»ModuleLoader: «serializeModuleLoader(d.moduleLoader)»«ENDIF»
		'''
	}

	/**
	 * ProjectIdWithOptionalVendor
	 * 	(versionConstraint=VersionConstraint)?
	 * 	(declaredScope=ProjectDependencyScope)?
	 */
	/** Returns the syntactic representation of the given {@link ProjectDependency} */
	private def String serializeProjectDependency(ProjectDependency dependency) {
		val projectDependency = new StringBuilder();
		
		// add project reference (vendorID ':')? projectID
		projectDependency.append(serializeProjectReference(dependency));
		
		// add versionConstraint if available
		if (dependency.versionConstraint !== null) {
			projectDependency.append(" " + serializeVersionConstraint(dependency.versionConstraint));
		}
		// add declaredScope if available and not default value COMPILE
		if (dependency.declaredScope !== null && dependency.scope != ProjectDependencyScope.COMPILE) {
			projectDependency.append(" test");
		}
		
		return projectDependency.toString;
	}

	/*
	 * (
	 * 	(exclLowerBound?='(' | '[')  lowerVersion=DeclaredVersion
	 * 			((',' upperVersion=DeclaredVersion (exclUpperBound?=')' | ']') )? | ')')
	 * 	) | lowerVersion=DeclaredVersion
	 */
	private def String serializeVersionConstraint(VersionConstraint versionConstraint) {
		if (versionConstraint.exclLowerBound || 
			versionConstraint.exclUpperBound || 
			versionConstraint.upperVersion !== null
		) {
			var constraintString = "";
			constraintString += if (versionConstraint.exclLowerBound) "(" else "[";
			constraintString += serializeDeclaredVersion(versionConstraint.lowerVersion);
			if (versionConstraint.upperVersion !== null)
				constraintString += ", " + serializeDeclaredVersion(versionConstraint.upperVersion)
			constraintString += if (versionConstraint.exclUpperBound) ")" else "]";
			return constraintString;
		} else {
			return serializeDeclaredVersion(versionConstraint.lowerVersion);
		}
	}

	/** Returns the syntactic representation of the given {@link ProjectReference} */
	private def String serializeProjectReference(ProjectReference reference) {
		return '''«IF(reference.declaredVendorId !== null)»«
			reference.declaredVendorId»:«
		ENDIF»«
			reference.projectId»''';
	}

	/** Returns the syntactic representation of the given {@link DeclaredVersion}. */
	private def String serializeDeclaredVersion(DeclaredVersion version) {
		var versionString = "";
		versionString += version.major;
		if (version.minor != 0 || version.micro != 0) {
			versionString += "." + version.minor;
		}
		if (version.micro != 0) {
			versionString += "." + version.micro;
		}
		if (version.qualifier !== null) {
			versionString += "-" + version.qualifier;
		}
		return versionString;
	}

	/** Returns the syntactic representation of the given {@link BootstrapModule} */
	private def String serializeBootstrapModule(BootstrapModule module) {
		// moduleSpecifierWithWildcard=STRING ('in' sourcePath=STRING)?
		return '''«module.moduleSpecifierWithWildcard.asStringLiteral»«IF module.sourcePath !== null
		» in «module.sourcePath.asStringLiteral»«ENDIF»''';
	}
	
	private def String serializeModuleFilter(ModuleFilter filter) {
		return '''«serializeModuleFilterType(filter.moduleFilterType)
			» «filter.moduleSpecifiers
				.map[s | serializeModuleFilterSpecifier(s)]
				.toCommaList»'''
	}
	
	/** Returns the syntactic representation of the given {@link SourceContainerDescription} */
	private def String serializeSourceContainerDescription(SourceContainerDescription description) {
		return '''
		«serializeSourceContainerType(description.sourceContainerType)» «
		description.pathsRaw.map[p | p.asStringLiteral].toCommaList»'''
	}

	/** Returns the syntactic representation of the given {@link ModuleFilterSpecifier} */
	private def String serializeModuleFilterSpecifier(ModuleFilterSpecifier specifier) {
		// moduleSpecifierWithWildcard=STRING ('in' sourcePath=STRING)?
		return '''«specifier.moduleSpecifierWithWildcard.asStringLiteral»«IF specifier.sourcePath !== null
		» in «specifier.sourcePath.asStringLiteral»«ENDIF»''';
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

	/** Returns the syntactic representation of the given {@link ModuleLoader} */
	private def String serializeModuleLoader(ModuleLoader loader) {
		return switch (loader) {
			case N4JS: "n4js"
			case COMMONJS: "commonjs"
			case NODE_BUILTIN: "node_builtin"
		};
	}
	
	/** 
	 * Returns a comma-separated list of the given String elements (each element appears on a new line).
	 * 
	 * The list is furthermore framed with curly braces.
	 */
	private def String toCommaList(Iterable<String> elements) {
		return '''
		{
			«elements.join(",\n")»
		}'''
	}
	
	/** Returns a string literal (with quotes) with the given value. */
	private def String asStringLiteral(String value) {
		return '''"«value»"''';
	}
}
