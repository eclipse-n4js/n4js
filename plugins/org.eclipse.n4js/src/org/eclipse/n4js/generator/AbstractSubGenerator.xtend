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
package org.eclipse.n4js.generator

import com.google.inject.Inject
import java.nio.file.Path
import java.nio.file.Paths
import org.eclipse.emf.common.EMFPlugin
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.N4JSLanguageConstants
import org.eclipse.n4js.generator.IGeneratorMarkerSupport.Severity
import org.eclipse.n4js.internal.lsp.N4JSProjectConfigSnapshot
import org.eclipse.n4js.internal.lsp.N4JSWorkspaceConfigSnapshot
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.projectModel.IN4JSCoreNEW
import org.eclipse.n4js.resource.N4JSCache
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.utils.Log
import org.eclipse.n4js.utils.ResourceNameComputer
import org.eclipse.n4js.utils.StaticPolyfillHelper
import org.eclipse.n4js.utils.URIUtils
import org.eclipse.n4js.utils.WildcardPathFilterHelper
import org.eclipse.n4js.validation.helper.FolderContainmentHelper
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.generator.AbstractFileSystemAccess
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGenerator2
import org.eclipse.xtext.generator.IGeneratorContext
import org.eclipse.xtext.service.OperationCanceledManager
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.util.UriExtensions
import org.eclipse.xtext.validation.CheckMode
import org.eclipse.xtext.validation.IResourceValidator
import org.eclipse.xtext.validation.Issue

import static org.eclipse.xtext.diagnostics.Severity.*

/**
 * All sub generators should extend this class. It provides basic blocks of the logic, and
 * shared implementations.
 */
@Log
abstract class AbstractSubGenerator implements ISubGenerator, IGenerator2 {

	@Accessors
	private CompilerDescriptor compilerDescriptor = null
	
	@Inject protected StaticPolyfillHelper staticPolyfillHelper

	@Inject protected IN4JSCoreNEW n4jsCore

	@Inject protected ResourceNameComputer resourceNameComputer

	@Inject protected IResourceValidator resVal

	@Inject protected N4JSCache cache

	@Inject protected IGeneratorMarkerSupport genMarkerSupport

	@Inject protected OperationCanceledManager operationCanceledManager

	@Inject protected GeneratorExceptionHandler exceptionHandler

	@Inject protected N4JSPreferenceAccess preferenceAccess
	
	@Inject private FolderContainmentHelper containmentHelper;
	
	@Inject	private UriExtensions uriExtensions;

	@Inject private WildcardPathFilterHelper wildcardHelper;


	override getCompilerDescriptor() {
		if (compilerDescriptor === null) {
			compilerDescriptor = getDefaultDescriptor
		}
		return compilerDescriptor
	}
	

	override doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		doGenerate(input, fsa);
	}

	override beforeGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		// TODO Auto-generated method stub
		
	}

	override afterGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This override declares an {@link IFileSystemAccess} parameter. At runtime, its actual type depends on whether IDE or headless,
	 * which in turn determines whether the actual argument has a progress monitor or not:
	 * <ul>
	 * <li>
	 * IDE scenario: Actual type is {@code EclipseResourceFileSystemAccess2}. A progress monitor can be obtained via {@code getMonitor()}.
	 * It is checked automatically behind the scenes, for example in {@code generateFile()}.
	 * Upon detecting a pending cancellation request, an {@code OperationCanceledException} is raised.
	 * </li>
	 * <li>
	 * Headless scenario: Actual type is {@code JavaIoFileSystemAccess}. No progress monitor is available.
	 * </li>
	 * </ul>
	 */
	override doGenerate(Resource input, IFileSystemAccess fsa) {
		val ws = n4jsCore.getWorkspaceConfig(input).orNull;
		if (ws === null) {
			return;
		}

		try {
			// remove error-marker
			genMarkerSupport.deleteMarker(input)

			updateOutputPath(ws, fsa, getCompilerID, input);
			internalDoGenerate(ws, input, GeneratorOption.DEFAULT_OPTIONS, fsa);

		} catch (UnresolvedProxyInSubGeneratorException e) {
			genMarkerSupport.createMarker(input, e.message, Severity.ERROR);

		} catch (Exception e) {
			// cancellation is not an error case, so simply propagate as usual
			operationCanceledManager.propagateIfCancelException(e);

			// issue error marker
			val target = (if (input instanceof N4JSResource) input.module?.moduleSpecifier) ?: input.URI?.toString;
			val msgMarker = "Severe error occurred while transpiling module " + target
				+ ". Check error log for details about the failure.";
			genMarkerSupport.createMarker(input, msgMarker, Severity.ERROR);

			// re-throw as GeneratorException to have the frameworks notify the error.
			if (e instanceof GeneratorException) {
				throw e;
			}
			var msg = if (e.message === null) "type=" + e.class.name else "message=" + e.message;
			exceptionHandler.handleError('''Severe error occurred in transpiler=«compilerID» «msg».''',  e);
		}
	}

	override shouldBeCompiled(Resource input, CancelIndicator monitor) {
		val ws = n4jsCore.getWorkspaceConfig(input).orNull;
		if (ws === null) {
			return false;
		}

		val autobuildEnabled = isActive(input)
		val isXPECTMode = N4JSGlobals.XT_FILE_EXTENSION == input.URI.fileExtension.toLowerCase
		val inputUri = input.URI

		val boolean result = (autobuildEnabled
			&& isGenerateProjectType(ws, inputUri)
			&& hasOutput(ws, inputUri)
			&& isOutputNotInSourceContainer(ws, inputUri)
			&& isOutsideOfOutputFolder(ws, inputUri)
			&& isSource(ws, inputUri)
			&& (isNoValidate(ws, inputUri)
				|| isExternal(ws, inputUri)
				// if platform is running (but not in XPECT mode) the generator is called from the builder, hence cannot have any validation errors
				// (note: XPECT swallows errors hence we cannot rely on Eclipse in case of .xt files)
				|| ((EMFPlugin.IS_ECLIPSE_RUNNING && !isXPECTMode) || hasNoErrors(input, monitor))
			))
			&& (!input.isStaticPolyfillingModule) // compile driven by filled type
			&& hasNoPolyfillErrors(input,monitor)
		return result
	}

	private def hasOutput(N4JSWorkspaceConfigSnapshot ws, URI n4jsSourceURI){
		return ws.getOutputPath(n4jsSourceURI) !== null;
	}
	private def isSource(N4JSWorkspaceConfigSnapshot ws, URI n4jsSourceURI) {
		return ws.findSourceFolderContaining(n4jsSourceURI) !== null;
	}

	private def isNoValidate(N4JSWorkspaceConfigSnapshot ws, URI n4jsSourceURI) {
		return ws.isNoValidate(n4jsSourceURI, wildcardHelper);
	}

	private def isExternal(N4JSWorkspaceConfigSnapshot ws, URI n4jsSourceURI) {
		val sourceContainerOpt = ws.findSourceFolderContaining(n4jsSourceURI);
		if (sourceContainerOpt !== null) {
			val sourceContainer = sourceContainerOpt;
			return sourceContainer.external;
		}
		return false;
	}

	/** If the resource has a static polyfill, then ensure it is error-free.
	 * Calls {@link #hasNoErrors()} on the static polyfill resource.
	 */
	private def boolean hasNoPolyfillErrors(Resource input, CancelIndicator monitor) {
		val resSPoly = staticPolyfillHelper.getStaticPolyfillResource(input)
		if (resSPoly === null) {
			return true;
		}
		return hasNoErrors(resSPoly, monitor)
	}

	/**
	 * Does validation report no errors for the given resource?
	 * If errors exists, log them as a side-effect.
	 * If validation was canceled before finishing, don't assume absence of errors.
	 */
	private def boolean hasNoErrors(Resource input, CancelIndicator monitor) {
		val issues = resVal.validate(input, CheckMode.ALL, monitor);
		if (null === issues) {
			// Cancellation occurred likely before all validations completed, thus can't assume absence of errors.
			// Cancellation may result in exit via normal control-flow (this case) or via exceptional control-flow (see exception handler below)
			warnDueToCancelation(input, null)
			return false;
		}
		val Iterable<Issue> errors = issues.filter[severity == ERROR];
		if (errors.isEmpty()) {
			return true
		}
		if (logger.isDebugEnabled) {
			errors.forEach[logger.debug(input.URI + "  " + it.message + "  " + it.severity + " @L_" + it.lineNumber + " ")]
		}
		return false
	}

	private def void warnDueToCancelation(Resource input, Throwable exc) {
		val msg = "User canceled the validation of " + input.URI + ". Will not compile.";
		if (null === exc) {
			logger.warn(msg)
		} else {
			logger.warn(msg,exc)
		}
	}

	/** @return true iff the current project has a project type that is supposed to generate code. */
	private def boolean isGenerateProjectType(N4JSWorkspaceConfigSnapshot ws, URI n4jsSourceURI) {
		val project = ws.findProjectContaining(n4jsSourceURI);
		if (project !== null) {
			val projectType = project.getType();
			if (N4JSGlobals.PROJECT_TYPES_WITHOUT_GENERATION.contains(projectType)) {
				return false;
			}
		}

		return true;
	}

	/** @return true iff the given resource does not lie within the output folder. */
	private def boolean isOutsideOfOutputFolder(N4JSWorkspaceConfigSnapshot ws, URI n4jsSourceURI) {
		return !containmentHelper.isContainedInOutputFolder(ws, n4jsSourceURI);
	}
	
	/** @return true iff the output folder of the given n4js resource is not contained by a source container. */
	private def boolean isOutputNotInSourceContainer(N4JSWorkspaceConfigSnapshot ws, URI n4jsSourceURI) {
		val project = ws.findProjectContaining(n4jsSourceURI);
		if (project !== null) {
			return !containmentHelper.isOutputContainedInSourceContainer(ws, project)
		} else {
			return false;
		} 
	}
	
	/**
	 * Actual generation to be overridden by subclasses.
	 */
	protected def void internalDoGenerate(N4JSWorkspaceConfigSnapshot ws, Resource resource, GeneratorOption[] options, IFileSystemAccess access)

	/**
	 * Returns the name of the target file (without path) to which the source is to be compiled to.
	 * Default implementation returns a configured project Name with version + file name + extension.
	 * E.g., "proj-0.0.1/p/A.js" for a file A in proj.
	 * 
	 * Convenience method, to provide all necessary API for the sub-classes.
	 * Delegates to {@link ResourceNameComputer#getTargetFileName}.
	 */
	protected def String getTargetFileName(Resource n4jsSourceFile, String compiledFileExtension) {
		return resourceNameComputer.generateFileDescriptor(n4jsSourceFile, compiledFileExtension)
	}

	/**
	 * Convenient access to the Script-Element
	 */
	protected def rootElement(Resource resource) {
		resource.contents.filter(Script).head
	}

	/** The file-extension of the compiled result */
	protected def String getCompiledFileExtension(Resource input) {
		preferenceAccess.getPreference(input, getCompilerID(), CompilerProperties.COMPILED_FILE_EXTENSION,
			getDefaultDescriptor())
	}

	/** The file-extension of the source-map to the compiled result */
	protected def String getCompiledFileSourceMapExtension(Resource input) {
		preferenceAccess.getPreference(input, getCompilerID(), CompilerProperties.COMPILED_FILE_SOURCEMAP_EXTENSION,
			getDefaultDescriptor())
	}

	/** Adjust output-path of the generator to match the N4JS projects-settings. */
	private def void updateOutputPath(N4JSWorkspaceConfigSnapshot ws, IFileSystemAccess fsa, String compilerID, Resource input) {
		val outputPath = ws.getOutputPath(input.URI) ?: N4JSLanguageConstants.DEFAULT_PROJECT_OUTPUT;
		if (fsa instanceof AbstractFileSystemAccess) {
			val conf = fsa.outputConfigurations.get(compilerID)
			if (conf !== null) {
				conf.setOutputDirectory(outputPath)
			}
		}
	}

	/** Navigation from the generated output-location to the location of the input-resource  */
	protected def Path calculateNavigationFromOutputToSourcePath(N4JSWorkspaceConfigSnapshot ws, IFileSystemAccess fsa, String compilerID, N4JSResource input) {
		// --- Project locations ---
		val project = ws.findProjectContaining(input.URI);

		// /home/user/workspace/Project/
		val projectPath = project.pathAsFileURI.toFileSystemPath
		// platform:/resource/Project/
		val projectLocURI = project.pathAsFileURI.withTrailingPathDelimiter.toURI

		// --- output locations ---
		// src-gen
		val outputPath = project.outputPath
		// Project/a/b/c
		val outputRelativeLocation = getOutputRelativeLocation(input)

		// --- source locations ---
		// src/a/b/c
		val inputURI = uriExtensions.withEmptyAuthority(input.URI);
		val completetSourceURI = inputURI.trimSegments(1).deresolve(projectLocURI)
		var completetSource = URIUtils.toFile(completetSourceURI).toString();

		// Handling case when source container is the project root itself. (Sources { source { '.' } })
		if (null === completetSource && project.pathAsFileURI.toURI === input.URI.trimSegments(1)) {
			completetSource = projectPath.toFile.absolutePath;
		}

		// /home/user/workspace/Project/src-gen/a/b/c
		val fullOutpath = projectPath.resolve(outputPath).normalize.resolve(outputRelativeLocation).normalize
		// /home/user/workspace/Project/src/a/b/c
		val fullSourcePath = projectPath.resolve(completetSource).normalize

		// ../../../../../../src/a/b/c
		val rel = fullOutpath.relativize(fullSourcePath)

		return rel
	}
	
	/**
	 * Calculates local output path for a given resource.
	 * Depending on the configuration this path can be in various forms, {@code Project-1.0.0/a/b/c/},
	 * {@code Project/a/b/c/} or just {@code a/b/c/}
	 *  
	 */
	private def Path getOutputRelativeLocation(N4JSResource input){
		val uri = uriExtensions.withEmptyAuthority(input.URI);
		// Project/a/b/c/Input.XX
		val localOutputFilePath = Paths.get(resourceNameComputer.generateFileDescriptor(input, uri, ".XX"))

		// if calculated path  has just one element, e.g. "Input.XX"
		// then local path segment is empty
		if(localOutputFilePath.nameCount<2){
			return Paths.get("")
		}

		//otherwise strip resource to get local path, i.e. Project/a/b/c/Input.XX => Project/a/b/c/
		return localOutputFilePath.subpath(0,localOutputFilePath.nameCount - 1)
	}

	/**
	 * Convenience for {@link AbstractSubGenerator#calculateOutputDirectory(String, String)},
	 * uses default compiler ID.
	 *
	 * TODO IDE-1487 currently there is no notion of default compiler. We fake call to the ES5 sub generator.
	 */
	def final static String calculateProjectBasedOutputDirectory(N4JSProjectConfigSnapshot project, boolean includeProjectName) {
		return if (includeProjectName) project.name + "/" + project.outputPath else project.outputPath;
	}

	/** Access to compiler ID */
	def abstract String getCompilerID()

	/** Access to compiler descriptor  */
	def abstract CompilerDescriptor getDefaultDescriptor()

	/** Answers: Is this compiler activated for the input at hand? */
	def boolean isActive(Resource input) {
		Boolean.valueOf(preferenceAccess.getPreference(input, getCompilerID(), CompilerProperties.IS_ACTIVE, getDefaultDescriptor()))
	}

	/** Checking the availability of a static polyfill, which will override the compilation of this module.
	 **/
	def boolean isNotStaticallyPolyfilled(Resource resource) {
		//		val TModule tmodule = (N4JSResource::getModule(resource) ); // for some reason xtend cannot see static getModule ?!
		if (resource instanceof N4JSResource) {
			val TModule tmodule = N4JSResource.getModule(resource)
			// 1. resource must be StaticPolyfillAware and
			// 2. there must exist a polyfilling instance (second instance with same fqn)
			if (tmodule.isStaticPolyfillAware) {
				// search for second instance.
				if (staticPolyfillHelper.hasStaticPolyfill(resource)) {
					return false;
				}
			}
		}
		return true;
	}

	/** 
	 * Checking if this resource represents a static polyfill, which will contribute to a filled resource.
	 **/
	private def boolean isStaticPolyfillingModule(Resource resource) {
		val TModule tmodule = (N4JSResource.getModule(resource));
		if (null !== tmodule) {
			return tmodule.isStaticPolyfillModule;
		}
		return false;
	}

	/**
	 *
	 * @return true if the composite generator is applicable to the given resource and false otherwise.
	 */
	override boolean isApplicableTo(Resource input) {
		return shouldBeCompiled(input, null);
	}

}
