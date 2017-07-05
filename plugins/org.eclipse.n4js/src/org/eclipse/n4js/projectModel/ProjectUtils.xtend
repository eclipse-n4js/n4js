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
package org.eclipse.n4js.projectModel

import com.google.common.base.Optional
import com.google.inject.Inject
import com.google.inject.Singleton
import java.io.File
import java.nio.file.Path
import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.n4js.internal.N4JSModel
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4mf.BootstrapModule
import org.eclipse.n4js.n4mf.DeclaredVersion
import org.eclipse.n4js.n4mf.ProjectDescription
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy
import org.eclipse.n4js.ts.scoping.N4TSQualifiedNameProvider
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider

import static extension org.eclipse.n4js.utils.N4JSLanguageUtils.*

/**
 * Provides utilities for generating resource (file or module) descriptors that are based on containing project and its properties.
 */
@Singleton
public class ProjectUtils {
	@Inject
	IN4JSCore n4jsCore;
	
	@Inject
	N4JSModel model;

	@Inject
	ResourceDescriptionsProvider indexAccess;

	@Inject
	IQualifiedNameConverter qualifiedNameConverter;

	/**
	 * Based on provided file resource URI and extension (must include dot!) will generate descriptor in form of Project-0.0.1
	 * Convenience method. Delegates to {@link ProjectUtils#formatDescriptor}
	 * For delegation project is calculated from provided URI.
	 * 
	 * @n4jsSourceURI URI from file resource
	 * @fileExtension String with containing project info (a.k.a. origin)
	 */
	def generateProjectDescriptor(URI n4jsSourceURI) {
		formatDescriptor(n4jsSourceURI.resolveProject, "", "-", ".", "", false);
	}

	/**
	 * Based on provided file resource URI and extension (must include dot!) will generate descriptor in form of Project/file/path/File.js
	 * Convenience method. Delegates to {@link ProjectUtils#formatDescriptor}
	 * For delegation both project and unitPath are calculated from provided URI.
	 * 
	 * @n4jsSourceURI URI from file resource
	 * @fileExtension String containing desired extensions, should include dot
	 */
	def generateFileDescriptor(URI n4jsSourceURI, String fileExtension) {
		formatDescriptor(n4jsSourceURI.resolveProject, n4jsSourceURI.resolvePackageAndFileName, "-", ".", "/", false) +
			fileExtension;
	}

	/**
	 * Based on provided file resource URI and extension (must include dot!) will generate descriptor in form of Project/file/path/File.js
	 * Convenience method. Delegates to {@link ProjectUtils#formatDescriptor}
	 * For delegation both project and unitPath are calculated from provided URI.
	 * 
	 * @n4jsSourceURI URI from file resource
	 * @fileExtension String containing desired extensions, should include dot
	 */
	def generateFileDescriptor(IN4JSProject project, URI n4jsSourceURI, String fileExtension) {
		formatDescriptor(project, n4jsSourceURI.resolvePackageAndFileName(project), "-", ".", "/", false) +
			fileExtension;
	}

	/**
	 * Formats descriptor in form of
	 * <pre>
	 * projectId
	 *  + sep1 + Project.declaredVersion.getMajor
	 *  + sep2 + Project.declaredVersion.getMinor
	 *  + sep2 + Project.declaredVersion.getMinor
	 *  + sep3 + unitPath
	 * </pre>
	 * 
	 * @param project  used to resolve declared version and project name.
	 * @param unitPath  a path like string, can be derived from file Path, module specifier, or constructed manually.
	 * @param includeProjectVersion  tells if project version should be included or not. If false, then sep1 and sep2
	 *                               will be ignored.
	 */
	def public final static String formatDescriptor(IN4JSProject project, String unitPath, String sep1, String sep2,
		String sep3, boolean includeProjectVersion) {
		return if (includeProjectVersion) {
			project.projectId + sep1 + projectVersionToStringWithoutQualifier(project.version, sep2) + sep3 + unitPath;
		} else {
			project.projectId + sep3 + unitPath;
		};
	}

	def public final static formatDescriptor(ProjectDescription project, String unitPath, String sep1, String sep2,
		String sep3) {
		return project.projectId + sep1 + projectVersionToStringWithoutQualifier(project.projectVersion, sep2) + sep3 +
			unitPath;
	}

	def public final formatDescriptorAsIdentifier(IN4JSProject project, String unitPath, String sep1, String sep2,
		String sep3, boolean includeProjectVersion) {
		return if (includeProjectVersion) {
			project.projectId.getValidJavascriptIdentifierName + sep1 +
				projectVersionToStringWithoutQualifier(project.version, sep2) + sep3 + unitPath.getValidUnitPath;
		} else {
			project.projectId.getValidJavascriptIdentifierName + sep3 + unitPath.getValidUnitPath;
		}
	}

	def public final formatDescriptorAsModuleParameterName(ProjectDescription project, String unitPath, String sep1,
		String sep2, String sep3) {
		return project.projectId.getValidJavascriptIdentifierName + sep1 +
			projectVersionToStringWithoutQualifier(project.projectVersion, sep2) + sep3 + unitPath.getValidUnitPath;
	}

	public def String getValidUnitPath(String unitPath) {
		return unitPath.split('/').map[getValidJavascriptIdentifierName].join('/');
	}

	public def String getValidJavascriptIdentifierName(String moduleSpecifier) {
		if (moduleSpecifier === null || moduleSpecifier.length === 0) {
			return moduleSpecifier;
		}
		val sb = new StringBuilder();
		for (var int i = 0; i < moduleSpecifier.length; i++) {
			val ch = moduleSpecifier.charAt(i);
			val isValid = if (i === 0) Character.isJavaIdentifierStart(ch) else Character.isJavaIdentifierPart(ch);
			if (isValid) {
				sb.append(ch);
			} else {
				sb.append(toUnicode(ch));
			}
		}
		return sb.toString();
	}

	def private toUnicode(char charAtPos) {
		return "_u" + Integer.toHexString(charAtPos.bitwiseOr(0x10000)).substring(1);
	}

	/**
	 * Resolves package and filename from provided URI. File extension is trimmed
	 */
	def private resolvePackageAndFileName(URI uri) {
		val project = n4jsCore.findProject(uri).orNull;
		return resolvePackageAndFileName(uri, project);
	}

	/**
	 * Resolves package and filename from provided URI. File extension is trimmed
	 */
	def private resolvePackageAndFileName(URI uri, IN4JSProject project) {
		val msg = '''Cannot locate source container for module «uri.lastSegment».''';
		if (null === project || !project.exists) {
			throw new RuntimeException('''«msg» Provided project was null.''');
		}
		if (!project.
			exists) {
			throw new RuntimeException('''«msg» Does project '«project.projectId»' exists and opened in the workspace?''');
		}

		val optionalSourceContainer = model.findN4JSExternalSourceContainer(project, uri);
		if (!optionalSourceContainer.present) {
			throw new RuntimeException('''«msg»''');
		}

		return uri.deresolve(optionalSourceContainer.get.location.appendSegment('')).trimFileExtension.toString;
	}

	/**
	 * Resolves project from provided URI. File extension is trimmed
	 */
	def IN4JSProject resolveProject(URI n4jsSourceURI) {
		val optionalProject = n4jsCore.findProject(n4jsSourceURI);
		if (!optionalProject.
			present) {
			throw new RuntimeException('''Cannot handle resource without containing project. Resource URI was: «n4jsSourceURI».''');
		}
		return optionalProject.get;
	}

	/**
	 * Transforms the version into a string used for variable, parameter, and file names.
	 */
	def private static projectVersionToStringWithoutQualifier(DeclaredVersion declaredVersion, String separatorChar) {
		return declaredVersion.getMajor + separatorChar + declaredVersion.getMinor + separatorChar +
			declaredVersion.getMicro;
	}

	/**
	 * returns the project relative path to the folder where the generated files should be placed
	 */
	def getOutputPath(URI uri) {
		return n4jsCore.getOutputPath(uri);
	}

	/**
	 * returns the absolute path to the folder where transpiled target files should be placed.
	 */
	def Path getOutputPathComplete(IN4JSProject project, String compilerId) {
		val outputFolderPathRelative = project.outputPath + File.separator + compilerId + File.separator +
			project.projectId;
		return project.locationPath.resolve(outputFolderPathRelative);
	}

	/**
	 * returns true if for the given URI module wrapping is enabled
	 */
	def isModuleWrappingEnabled(URI n4jsSourceURI) {
		return !n4jsCore.isNoModuleWrapping(n4jsSourceURI);
	}

	def isSource(URI n4jsSourceURI) {
		return n4jsCore.findN4JSSourceContainer(n4jsSourceURI).present;
	}

	/**
	 * returns true if for the given URI module validation is disabled
	 */
	def isNoValidate(URI n4jsSourceURI) {
		return n4jsCore.isNoValidate(n4jsSourceURI);
	}

	/**
	 * returns true if the given URI points to an external implementation
	 */
	def isExternal(URI n4jsSourceURI) {
		val sourceContainerOpt = n4jsCore.findN4JSSourceContainer(n4jsSourceURI);
		if (sourceContainerOpt.present) {
			val sourceContainer = sourceContainerOpt.get;
			return sourceContainer.external;
		}
		return false;
	}

	/**
	 * Same as {@link IN4JSProject#getInitModules()}, but returns the initialization modules as URIs.
	 */
	def List<URI> getInitModulesAsURIs(IN4JSProject project) {
		return project.initModules.map [BootstrapModule bm|
			findArtifact(project, bm.moduleSpecifierWithWildcard, Optional.of(".js"))
		].filterNull.toList;
	}

	/**
	 * Same as {@link IN4JSProject#getExecModule()}, but returns the execution module as URI.
	 */
	def Optional<URI> getExecModuleAsURI(IN4JSProject project) {
		var Optional<BootstrapModule> oExecModule = project.execModule;
		if (oExecModule.present) {
			return Optional.of(findArtifact(project, oExecModule.get.moduleSpecifierWithWildcard, Optional.of(".js")));
		}
		return Optional.absent;
	}

	/**
	 * Same as {@link #findArtifact(IN4JSProject, QualifiedName, String)}, but the qualified name
	 * can be provided as a String.
	 */
	def URI findArtifact(IN4JSProject project, String fqn, Optional<String> fileExtension) {
		return findArtifact(project, qualifiedNameConverter.toQualifiedName(fqn), fileExtension);
	}

	/**
	 * Convenience method for {@link IN4JSSourceContainer#findArtifact(QualifiedName, Optional)},
	 * searching all source containers of the given project.
	 */
	def URI findArtifact(IN4JSProject project, QualifiedName fqn, Optional<String> fileExtension) {
		for (IN4JSSourceContainer srcConti : project.sourceContainers) {
			val uri = srcConti.findArtifact(fqn, fileExtension);
			if (uri !== null)
				return uri;
		}
		return null;
	}

	/** Checking if the given n4jsSourceUri is part of a project implementing a API definition */
	def boolean isApiImplementation(URI n4jsSourceUri) {
		val project = n4jsCore.findProject(n4jsSourceUri);
		if (project.present) {
			return project.get.implementationId.isPresent;
		}
		return false;
	}

	/** For a given N4JSResource annotated with {@code @@StaticPolyfillAware} lookup the filling Module.
	 * returns {@code true} if the filling Module exists in the project.
	 */
	def boolean hasStaticPolyfill(Resource res) {
		// ensure right resource
		if (res instanceof N4JSResource) {
			if (res.script.isContainedInStaticPolyfillAware) {

				// TODO GHOLD-196 resolve inconsistency in logic between this method and #findStaticPolyfiller(Resource)
				// (i.e. if possible, delete strategy #1 and only use strategy #2; but make sure this isn't a
				// performance issue, esp. with respect to the call "srcConti.findArtifact(fqn, fileExtension)" in
				// #findStaticPolyfiller(Resource))
				var boolean strategyIndex = true;

				if (strategyIndex) {
					// 1. query index
					val QualifiedName qnFilled = qualifiedNameConverter.toQualifiedName(res.module.qualifiedName);
					val index = indexAccess.getResourceDescriptions(res.resourceSet);
					val optQnFilling = N4TSQualifiedNameProvider.toStaticPolyfillFQN(qnFilled);
					if (optQnFilling.isPresent) {
						val qnFilling = optQnFilling.get;

						val modules = index.getExportedObjectsByType(TypesPackage.Literals.TMODULE);
						for (module : modules) {
							if (module.qualifiedName == qnFilling) {
								return true;
							}
						}
					}
				} else {
					// 2. query all source-containers for file with same QN
					var fillingURI = findStaticPolyfiller(res);
					if (null !== fillingURI) return true;
				}
			}
		}
		return false;
	}

	/** Find the corresponding static-polyfill to this {@code @@PolyfillAware} resource in the same project.
	 * returns null if not found or this resource has no {@code @@PolyfillAware} annotation.
	 */
	def URI findStaticPolyfiller(Resource res) {
		// ensure right resource
		if (res instanceof N4JSResource) {
			if (! res.script.isContainedInStaticPolyfillAware) return null;

			val QualifiedName qnFilled = qualifiedNameConverter.toQualifiedName(res.module.qualifiedName);
			val project = resolveProject(res.URI);
			val fqn = qnFilled;
			val fileExtension = Optional.of(res.URI.fileExtension); // see Req.155#4: "Both extensions are equal."
			val filledSrcContainer = n4jsCore.findN4JSSourceContainer(res.URI).get;
			for (IN4JSSourceContainer srcConti : project.sourceContainers) {
				if (filledSrcContainer != srcConti) {
					val uri = srcConti.findArtifact(fqn, fileExtension);
					if (uri !== null) {
						return uri;
					}
				}
			}
		}
		return null;
	}

	/** Helper to load a StaticPolyfill resource into the same ResourceSet. Takes an {@code @@StaticPolyfillAware} {@code N4JSResource}.
	 * Returns the filling resource if any or {@code null}
	 */
	def N4JSResource getStaticPolyfillResource(Resource res) {
		val uri = res?.findStaticPolyfiller;
		if (null !== uri) {
			return res.resourceSet.getResource(uri, true) as N4JSResource;
		}
		return null;
	}

	/**
	 * Currently the StaticPolyfill Annotation is only allowed on N4ClassDeclarations. If type is something else {@code null} will be returned.
	 * N4TypeDefinition <: (N4Enum, N4Classifier <: ( N4Interface, N4Classdefintion <: (N4Classdeclaration,N4classExpression)))
	 * 
	 * The containing resource will be loaded on demand in the same ResourceSet, in which the passed in type is kept.
	 */
	def N4ClassDeclaration getStaticPolyfill(Type type) {
		return getStaticPolyfill(type.eResource.staticPolyfillResource, type);
	}

	/**
	 * Same as {@link #getStaticPolyfill(N4TypeDefinition)}, but the resource of the filler can be provided in case
	 * it is already known (to avoid unnecessary search).
	 */
	def N4ClassDeclaration getStaticPolyfill(N4JSResource fillingResource, Type type) {
		if (type instanceof TClass) {
			val scriptFiller = fillingResource?.scriptResolved;
			if (null !== scriptFiller) {
				val staticPolyfiller = EcoreUtil2.getAllContentsOfType(scriptFiller, N4ClassDeclaration).filter [
					it.definedTypeAsClass.isDeclaredStaticPolyfill // is a static-polyfill
					&& it.superClassRef.declaredType == type // extends this class explicitly
				].head;

				return staticPolyfiller;
			}
		}
		return null;
	}

	/**
	 * Helper method that checks if given {@link IEObjectDescription description} describes {@link TModule} containing given {@link EObject}.
	 * 
	 * Returns <code>true</code> only if provided {@link IEObjectDescription description} has the same {@link QualifiedName} as module of the {@link EObject}.
	 * Additionally if {@link IEObjectDescription description} describes {@link TModule#isMainModule() main module} then it is checked if both are contained in the same {@link IN4JSProject}.
	 * 
	 * @returns true if {@link IEObjectDescription} describes module of {@link EObject}
	 */
	def public boolean isDescriptionOfModuleWith(IEObjectDescription eoDescription, EObject eObject) {
		// check if module names are the same
		val eobjectModuleName = EcoreUtil2.getContainerOfType(eObject, Script).module.qualifiedName;
		if (!eobjectModuleName.equals(qualifiedNameConverter.toString(eoDescription.qualifiedName))) {
			return false;
		}

		// check if  not a main module, assume true
		val mainModuelUserData = eoDescription.getUserData(N4JSResourceDescriptionStrategy.MAIN_MODULE_KEY);
		if (mainModuelUserData === null || !Boolean.getBoolean(mainModuelUserData)) {
			return true;
		}

		// for main modules we check containing project
		val targetProject = n4jsCore.findProject(eoDescription.EObjectURI).orNull;
		val currentProject = n4jsCore.findProject(eObject.eResource.URI).orNull;

		return targetProject == currentProject;
	}
}
