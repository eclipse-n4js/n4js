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
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.generator.IGeneratorMarkerSupport.Severity
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.projectModel.ProjectUtils
import org.eclipse.n4js.resource.N4JSCache
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.utils.CompilerHelper
import org.eclipse.n4js.utils.Log
import org.eclipse.n4js.utils.ResourceType
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.generator.AbstractFileSystemAccess
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.service.OperationCanceledManager
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.validation.IResourceValidator
import org.eclipse.xtext.validation.Issue

import static org.eclipse.xtext.diagnostics.Severity.*

/**
 */
@Log
abstract class AbstractSubGenerator implements ISubGenerator {

	@Accessors
	private CompilerDescriptor compilerDescriptor = null

	@Inject
	ProjectUtils projectUtils

	@Inject
	IN4JSCore core

	@Inject
	CompilerHelper compilerUtils

	@Inject
	IResourceValidator resVal

	@Inject
	N4JSCache cache

	@Inject
	IGeneratorMarkerSupport genMarkerSupport

	@Inject
	OperationCanceledManager operationCanceledManager;

	@Inject
	extension ExceptionHandler

	@Inject
	extension N4JSPreferenceAccess

	override getCompilerDescriptor() {
		if (compilerDescriptor === null) {
			compilerDescriptor = getDefaultDescriptor
		}
		return compilerDescriptor
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
		try {

			// remove error-marker
			genMarkerSupport.deleteMarker(input)

			updateOutputPath(fsa, getCompilerID, input);
			internalDoGenerate(input, GeneratorOption.DEFAULT_OPTIONS, fsa);
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
			handleError('''Severe error occurred in transpiler=«compilerID» «msg».''',  e);
		}
	}

	override shouldBeCompiled(Resource input, CancelIndicator monitor) {
		val autobuildEnabled = isActive(input)
		val isXPECTMode = N4JSGlobals.XT_FILE_EXTENSION == input.URI.fileExtension.toLowerCase

		return (autobuildEnabled && projectUtils.isSource(input.URI)
			&& (projectUtils.isNoValidate(input.URI)
				|| projectUtils.isExternal(input.URI)
				// if platform is running (but not in XPECT mode) the generator is called from the builder, hence cannot have any validation errors
				// (note: XPECT swallows errors hence we cannot rely on Eclipse in case of .xt files)
				|| ((EMFPlugin.IS_ECLIPSE_RUNNING && !isXPECTMode) || hasNoErrors(input, monitor))
			))
			&& (!input.isStaticPolyfillingModule) // compile driven by filled type
			&& hasNoPolyfillErrors(input,monitor)
	}

	/** If the resource has a static polyfill, then ensure it is error-free.
	 * Calls {@link #hasNoErrors()} on the static polyfill resource.
	 */
	private def boolean hasNoPolyfillErrors(Resource input, CancelIndicator monitor) {
		val resSPoly = projectUtils.getStaticPolyfillResource(input)
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
		val issues = cache.getOrElseUpdateIssues(resVal, input, monitor)
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

	/**
	 * Actual generation to be overridden by subclasses.
	 */
	def protected void internalDoGenerate(Resource resource, GeneratorOption[] options, IFileSystemAccess access)

	/**
	 * Returns the name of the target file (without path) to which the source is to be compiled to.
	 * Default implementation returns a configured project Name with version + file name + extension.
	 * E.g., "proj-0.0.1/p/A.js" for a file A in proj.
	 */
	def String getTargetFileName(Resource n4jsSourceFile, String compiledFileExtension) {
		/*
		 * handle double extension for xpect, e.g. foo.n4js.xt
		 *
		 * assuming hasValidFileExtension was called, so care only about double extension
		 */
		var souceURI =
			//TODO: need a uniform way to handle multiple languages here
			if (ResourceType.xtHidesOtherExtension(n4jsSourceFile.URI) || (N4JSGlobals.XT_FILE_EXTENSION  == n4jsSourceFile.URI.fileExtension.toLowerCase)) {
				n4jsSourceFile.URI.trimFileExtension
			} else {
				n4jsSourceFile.URI
			}

		val String targetFilePath = try {
			projectUtils.generateFileDescriptor(souceURI, "." + compiledFileExtension)
		} catch (Throwable t) {

			//TODO a bit generic error handling
			handleError(t.message, t)
			null
		}

		return targetFilePath;
	}

	/**
	 * Convenient access to the Script-Element
	 */
	def rootElement(Resource resource) {
		resource.contents.filter(Script).head
	}

	/** The file-extension of the compiled result */
	def String getCompiledFileExtension(Resource input) {
		getPreference(input, getCompilerID(), CompilerProperties.COMPILED_FILE_EXTENSION,
			getDefaultDescriptor())
	}

	/** The file-extension of the source-map to the compiled result */
	def String getCompiledFileSourceMapExtension(Resource input) {
		getPreference(input, getCompilerID(), CompilerProperties.COMPILED_FILE_SOURCEMAP_EXTENSION,
			getDefaultDescriptor())
	}

	/** Adjust output-path of the generator to match the N4JS projects-settings. */
	def void updateOutputPath(IFileSystemAccess fsa, String compilerID, Resource input) {
		val outputPath = projectUtils.getOutputPath(input.URI)
		if (fsa instanceof AbstractFileSystemAccess) {
			val conf = fsa.outputConfigurations.get(compilerID)
			if (conf !== null) {
				conf.setOutputDirectory(calculateOutputDirectory(outputPath, compilerID))
			}
		}
	}

	/** Navigation from the generated output-location to the location of the input-resource  */
	def Path calculateNavigationFromOutputToSourcePath(IFileSystemAccess fsa, String compilerID, N4JSResource input) {

		// --- Project locations ---
		val projectctContainer = core.findProject(input.URI)
		val project = projectctContainer.get;

		// /home/user/workspace/Project/
		val projectPath = project.locationPath
		// platform:/resource/Project/
		val projectLocURI = project.location.appendSegment("")

		// --- output locations ---
		// src-gen
		val outputPath = projectUtils.getOutputPath(input.URI)
		// src-gen/es5
		val outputDirectory = calculateOutputDirectory(outputPath, compilerID)

		// Project/a/b/c/Input.XX
		val localOutputFilePath = Paths.get(projectUtils.generateFileDescriptor(input.URI, ".XX"))
		// Project/a/b/c
		val localOutputDir = localOutputFilePath.subpath(0,localOutputFilePath.nameCount - 1)

		// --- source locations ---
		// src/a/b/c
		val completetSourceURI = input.URI.trimSegments(1).deresolve(projectLocURI)
		var completetSource = completetSourceURI.toFileString

		// Handling case when source container is the project root itself. (Sources { source { '.' } })
		if (null === completetSource && project.location === input.URI.trimSegments(1)) {
			completetSource = projectPath.toFile.absolutePath;
		}

		// /home/user/workspace/Project/src-gen/es5/Project-0.0.1/a/b/c
		val fullOutpath = projectPath.resolve(outputDirectory).normalize.resolve(localOutputDir).normalize
		// /home/user/workspace/Project/src/a/b/c
		val fullSourcePath = projectPath.resolve(completetSource).normalize

		// ../../../../../../src/a/b/c
		val rel = fullOutpath.relativize(fullSourcePath)

		return rel
	}

	/**
	 * Simply concatenates the outputPath with the compilerID, e.g.
	 * for "src-gen" and "es5", this returns "src-gen/es5".
	 *
	 * @param outputPath usually src-gen by default
	 * @param compilerID ID of the compiler, which is a subfolder in the output path (e.g. "es5")
	 */
	def static String calculateOutputDirectory(String outputPath, String compilerID) {
		"./" + outputPath + "/" + compilerID
	}

	/** Access to compiler ID */
	def abstract String getCompilerID()

    /** Access to compiler descriptor  */
	def abstract protected CompilerDescriptor getDefaultDescriptor()

	/** Answers: Is this compiler activated for the input at hand? */
	def boolean isActive(Resource input) {
		Boolean.valueOf(getPreference(input, getCompilerID(), CompilerProperties.IS_ACTIVE, getDefaultDescriptor()))
	}

	/** Checking the availability of a static polyfill, which will override the compilation of this module.
	 **/
	def boolean isNotStaticallyPolyfilled(Resource resource) {
		//		val TModule tmodule = (N4JSResource::getModule(resource) ); // for some reason xtend cannot see static getModule ?!
		if (resource instanceof N4JSResource) {
			val TModule tmodule = compilerUtils.retrieveModule(resource)
			// 1. resource must be StaticPolyfillAware and
			// 2. there must exist a polyfilling instance (second instance with same fqn)
			if (tmodule.isStaticPolyfillAware) {
				// search for second instance.
				if (projectUtils.hasStaticPolyfill(resource)) {
					return false;
				}
			}
		}
		return true;
	}

	/** Checking if this resource represents a static polyfill, which will contribute to a filled resource.
	 **/
	def boolean isStaticPolyfillingModule(Resource resource)
	{
		val TModule tmodule = (N4JSResource::getModule(resource));
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
