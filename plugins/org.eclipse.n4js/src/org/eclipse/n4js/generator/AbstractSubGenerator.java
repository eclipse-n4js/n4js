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
package org.eclipse.n4js.generator;

import static org.eclipse.xtext.diagnostics.Severity.ERROR;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.generator.IGeneratorMarkerSupport.Severity;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.resource.N4JSCache;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.FolderContainmentHelper;
import org.eclipse.n4js.utils.ResourceNameComputer;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.n4js.utils.StaticPolyfillHelper;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.N4JSSourceFolderSnapshot;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.xtext.generator.AbstractFileSystemAccess;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.UriExtensions;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;

/**
 * All sub generators should extend this class. It provides basic blocks of the logic, and shared implementations.
 */
abstract public class AbstractSubGenerator implements ISubGenerator, IGenerator2 {
	private final static Logger LOGGER = Logger.getLogger(AbstractSubGenerator.class);

	private CompilerDescriptor compilerDescriptor = null;

	/***/
	@Inject
	protected StaticPolyfillHelper staticPolyfillHelper;

	/***/
	@Inject
	protected WorkspaceAccess workspaceAccess;

	/***/
	@Inject
	protected ResourceNameComputer resourceNameComputer;

	/***/
	@Inject
	protected IResourceValidator resVal;

	/***/
	@Inject
	protected N4JSCache cache;

	/***/
	@Inject
	protected IGeneratorMarkerSupport genMarkerSupport;

	/***/
	@Inject
	protected OperationCanceledManager operationCanceledManager;

	/***/
	@Inject
	protected GeneratorExceptionHandler exceptionHandler;

	/***/
	@Inject
	protected N4JSPreferenceAccess preferenceAccess;

	@Inject
	private FolderContainmentHelper containmentHelper;

	@Inject
	private UriExtensions uriExtensions;

	@Override
	public CompilerDescriptor getCompilerDescriptor() {
		if (compilerDescriptor == null) {
			compilerDescriptor = getDefaultDescriptor();
		}
		return compilerDescriptor;
	}

	@Override
	public void setCompilerDescriptor(CompilerDescriptor compilerDescriptor) {
		this.compilerDescriptor = compilerDescriptor;
	}

	@Override
	public void doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		doGenerate(input, fsa);
	}

	@Override
	public void beforeGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		// TODO Auto-generated method stub

	}

	/**
	 * This override declares an {@link IFileSystemAccess} parameter. At runtime, its actual type depends on whether IDE
	 * or headless, which in turn determines whether the actual argument has a progress monitor or not:
	 * <ul>
	 * <li>IDE scenario: Actual type is {@code EclipseResourceFileSystemAccess2}. A progress monitor can be obtained via
	 * {@code getMonitor()}. It is checked automatically behind the scenes, for example in {@code generateFile()}. Upon
	 * detecting a pending cancellation request, an {@code OperationCanceledException} is raised.</li>
	 * <li>Headless scenario: Actual type is {@code JavaIoFileSystemAccess}. No progress monitor is available.</li>
	 * </ul>
	 */
	@Override
	public void doGenerate(Resource input, IFileSystemAccess fsa) {
		if (justCopy(input)) {
			copyWithoutTranspilation(input, fsa);
			return;
		}

		N4JSWorkspaceConfigSnapshot ws = workspaceAccess.getWorkspaceConfig(input);

		try {
			// remove error-marker
			genMarkerSupport.deleteMarker(input);

			updateOutputPath(ws, fsa, getCompilerID(), input);
			internalDoGenerate(ws, input, GeneratorOption.DEFAULT_OPTIONS, fsa);

		} catch (UnresolvedProxyInSubGeneratorException e) {
			genMarkerSupport.createMarker(input, e.getMessage(), Severity.ERROR);

		} catch (Exception e) {
			// cancellation is not an error case, so simply propagate as usual
			operationCanceledManager.propagateIfCancelException(e);

			// issue error marker
			String target = input.getURI() == null ? "unknown" : input.getURI().toString();
			if (input instanceof N4JSResource && ((N4JSResource) input).getModule() != null) {
				target = ((N4JSResource) input).getModule().getModuleSpecifier();
			}
			String msgMarker = "Severe error occurred while transpiling module " + target
					+ ". Check error log for details about the failure.";
			genMarkerSupport.createMarker(input, msgMarker, Severity.ERROR);

			// re-throw as GeneratorException to have the frameworks notify the error.
			if (e instanceof GeneratorException) {
				throw e;
			}
			String msg = (e.getMessage() == null) ? "type=" + e.getClass().getName() : "message=" + e.getMessage();
			exceptionHandler.handleError("Severe error occurred in transpiler=" + getCompilerID() + " " + msg + ".", e);
		}
	}

	@Override
	public boolean shouldBeCompiled(Resource input, CancelIndicator monitor) {
		N4JSWorkspaceConfigSnapshot ws = workspaceAccess.getWorkspaceConfig(input);

		boolean autobuildEnabled = isActive(input);
		boolean isXPECTMode = N4JSGlobals.XT_FILE_EXTENSION
				.equals(URIUtils.fileExtension(input.getURI()).toLowerCase());
		URI inputUri = input.getURI();

		boolean result = (autobuildEnabled
				&& isGenerateProjectType(ws, inputUri)
				&& hasOutput(ws, inputUri)
				&& isOutputNotInSourceContainer(ws, inputUri)
				&& isOutsideOfOutputFolder(ws, inputUri)
				&& isSource(ws, inputUri)
				&& (isNoValidate(ws, inputUri)
						|| isExternal(ws, inputUri)
						// if platform is running (but not in XPECT mode) the generator is called from the builder,
						// hence cannot have any validation errors
						// (note: XPECT swallows errors hence we cannot rely on Eclipse in case of .xt files)
						|| ((EMFPlugin.IS_ECLIPSE_RUNNING && !isXPECTMode) || hasNoErrors(input, monitor))))
				&& (!isStaticPolyfillingModule(input)) // compile driven by filled type
				&& hasNoPolyfillErrors(input, monitor);
		return result;
	}

	private boolean hasOutput(N4JSWorkspaceConfigSnapshot ws, URI n4jsSourceURI) {
		return getOutputPath(ws, n4jsSourceURI) != null;
	}

	private boolean isSource(N4JSWorkspaceConfigSnapshot ws, URI n4jsSourceURI) {
		return ws.findSourceFolderContaining(n4jsSourceURI) != null;
	}

	private boolean isNoValidate(N4JSWorkspaceConfigSnapshot ws, URI n4jsSourceURI) {
		return ws.isNoValidate(n4jsSourceURI);
	}

	private boolean isExternal(N4JSWorkspaceConfigSnapshot ws, URI n4jsSourceURI) {
		N4JSSourceFolderSnapshot sourceContainerOpt = ws.findSourceFolderContaining(n4jsSourceURI);
		if (sourceContainerOpt != null) {
			N4JSSourceFolderSnapshot sourceContainer = sourceContainerOpt;
			return sourceContainer.isExternal();
		}
		return false;
	}

	/**
	 * If the resource has a static polyfill, then ensure it is error-free. Calls
	 * {@link #hasNoErrors(Resource, CancelIndicator)} on the static polyfill resource.
	 */
	private boolean hasNoPolyfillErrors(Resource input, CancelIndicator monitor) {
		N4JSResource resSPoly = staticPolyfillHelper.getStaticPolyfillResource(input);
		if (resSPoly == null) {
			return true;
		}
		// re-validation is necessary since the changes of the current resource (i.e. filled resource)
		// can affect the filling resource in a way that validation errors will be removed or created.
		cache.recreateIssues(resVal, resSPoly, CheckMode.ALL, monitor);
		return hasNoErrors(resSPoly, monitor);
	}

	/**
	 * Does validation report no errors for the given resource? If errors exists, log them as a side-effect. If
	 * validation was canceled before finishing, don't assume absence of errors.
	 */
	private boolean hasNoErrors(Resource input, CancelIndicator monitor) {
		List<Issue> issues = cache.getOrUpdateIssues(resVal, input, CheckMode.ALL, monitor);
		if (issues == null || input instanceof N4JSResource && !((N4JSResource) input).isFullyProcessed()) {
			// Cancellation occurred likely before all validations completed, thus can't assume absence of errors.
			// Cancellation may result in exit via normal control-flow (this case) or via exceptional control-flow (see
			// exception handler below)
			warnDueToCancelation(input, null);
			return false;
		}

		List<Issue> errors = toList(filter(issues, i -> i.getSeverity() == ERROR));
		if (errors.isEmpty()) {
			return true;
		}
		if (LOGGER.isDebugEnabled()) {
			for (Issue it : errors) {
				LOGGER.debug(input.getURI() + "  " + it.getMessage() + "  " + it.getSeverity() + " @L_"
						+ it.getLineNumber() + " ");
			}
		}
		return false;
	}

	private void warnDueToCancelation(Resource input, Throwable exc) {
		String msg = "User canceled the validation of " + input.getURI() + ". Will not compile.";
		if (null == exc) {
			LOGGER.warn(msg);
		} else {
			LOGGER.warn(msg, exc);
		}
	}

	/** @return true iff the current project has a project type that is supposed to generate code. */
	private boolean isGenerateProjectType(N4JSWorkspaceConfigSnapshot ws, URI n4jsSourceURI) {
		N4JSProjectConfigSnapshot project = ws.findProjectContaining(n4jsSourceURI);
		if (project != null) {
			ProjectType projectType = project.getType();
			if (N4JSGlobals.PROJECT_TYPES_WITHOUT_GENERATION.contains(projectType)) {
				return false;
			}
		}

		return true;
	}

	/** @return true iff the given resource does not lie within the output folder. */
	private boolean isOutsideOfOutputFolder(N4JSWorkspaceConfigSnapshot ws, URI n4jsSourceURI) {
		return !containmentHelper.isContainedInOutputFolder(ws, n4jsSourceURI);
	}

	/** @return true iff the output folder of the given n4js resource is not contained by a source container. */
	private boolean isOutputNotInSourceContainer(N4JSWorkspaceConfigSnapshot ws, URI n4jsSourceURI) {
		N4JSProjectConfigSnapshot project = ws.findProjectContaining(n4jsSourceURI);
		if (project != null) {
			return !containmentHelper.isOutputContainedInSourceContainer(ws, project);
		} else {
			return false;
		}
	}

	/**
	 * Actual generation to be overridden by subclasses.
	 */
	abstract protected void internalDoGenerate(N4JSWorkspaceConfigSnapshot ws, Resource resource,
			GeneratorOption[] options, IFileSystemAccess access);

	/**
	 * Returns the name of the target file (without path) to which the source is to be compiled to. Default
	 * implementation returns a configured project Name with version + file name + extension. E.g., "proj-0.0.1/p/A.js"
	 * for a file A in proj.
	 *
	 * Convenience method, to provide all necessary API for the sub-classes. Delegates to
	 * {@link ResourceNameComputer#generateFileDescriptor(Resource, String)}.
	 */
	protected String getTargetFileName(Resource n4jsSourceFile, String compiledFileExtension) {
		return resourceNameComputer.generateFileDescriptor(n4jsSourceFile, compiledFileExtension);
	}

	/**
	 * Convenient access to the Script-Element
	 */
	protected Script rootElement(Resource resource) {
		return head(filter(resource.getContents(), Script.class));
	}

	/** The file-extension of the compiled result */
	protected String getCompiledFileExtension(Resource input) {
		return preferenceAccess.getPreference(input, getCompilerID(), CompilerProperties.COMPILED_FILE_EXTENSION,
				getDefaultDescriptor());
	}

	/** The file-extension of the source-map to the compiled result */
	protected String getCompiledFileSourceMapExtension(Resource input) {
		return preferenceAccess.getPreference(input, getCompilerID(),
				CompilerProperties.COMPILED_FILE_SOURCEMAP_EXTENSION,
				getDefaultDescriptor());
	}

	/** Adjust output-path of the generator to match the N4JS projects-settings. */
	private void updateOutputPath(N4JSWorkspaceConfigSnapshot ws, IFileSystemAccess fsa, String compilerID,
			Resource input) {
		String outputPath = getOutputPath(ws, input.getURI());
		if (outputPath == null) {
			outputPath = N4JSLanguageConstants.DEFAULT_PROJECT_OUTPUT;
		}
		if (fsa instanceof AbstractFileSystemAccess) {
			OutputConfiguration conf = ((AbstractFileSystemAccess) fsa).getOutputConfigurations().get(compilerID);
			if (conf != null) {
				conf.setOutputDirectory(outputPath);
			}
		}
	}

	private static String getOutputPath(N4JSWorkspaceConfigSnapshot ws, URI nestedLocation) {
		if (ws.findProjectContaining(nestedLocation) == null) {
			return null;
		}
		if (ws.findProjectContaining(nestedLocation).getProjectDescription() == null) {
			return null;
		}
		return ws.findProjectContaining(nestedLocation).getProjectDescription().getOutputPath();
	}

	/** Navigation from the generated output-location to the location of the input-resource */
	@SuppressWarnings("unused")
	protected Path calculateNavigationFromOutputToSourcePath(N4JSWorkspaceConfigSnapshot ws, IFileSystemAccess fsa,
			String compilerID, N4JSResource input) {
		// --- Project locations ---
		N4JSProjectConfigSnapshot project = ws.findProjectContaining(input.getURI());

		// /home/user/workspace/Project/
		Path projectPath = project.getPathAsFileURI().toFileSystemPath();
		// platform:/resource/Project/
		URI projectLocURI = project.getPathAsFileURI().withTrailingPathDelimiter().toURI();

		// --- output locations ---
		// src-gen
		String outputPath = project.getOutputPath();
		// Project/a/b/c
		Path outputRelativeLocation = getOutputRelativeLocation(input);

		// --- source locations ---
		// src/a/b/c
		URI inputURI = uriExtensions.withEmptyAuthority(input.getURI());
		URI completetSourceURI = inputURI.trimSegments(1).deresolve(projectLocURI);
		String completetSource = URIUtils.toFile(completetSourceURI).toString();

		// Handling case when source container is the project root itself. (Sources { source { '.' } })
		if (null == completetSource && project.getPathAsFileURI().toURI() == input.getURI().trimSegments(1)) {
			completetSource = projectPath.toFile().getAbsolutePath();
		}

		// /home/user/workspace/Project/src-gen/a/b/c
		Path fullOutpath = projectPath.resolve(outputPath).normalize().resolve(outputRelativeLocation).normalize();
		// /home/user/workspace/Project/src/a/b/c
		Path fullSourcePath = projectPath.resolve(completetSource).normalize();

		// ../../../../../../src/a/b/c
		Path rel = fullOutpath.relativize(fullSourcePath);

		return rel;
	}

	/**
	 * Calculates local output path for a given resource. Depending on the configuration this path can be in various
	 * forms, {@code Project-1.0.0/a/b/c/}, {@code Project/a/b/c/} or just {@code a/b/c/}
	 *
	 */
	private Path getOutputRelativeLocation(N4JSResource input) {
		URI uri = uriExtensions.withEmptyAuthority(input.getURI());
		// Project/a/b/c/Input.XX
		Path localOutputFilePath = Paths.get(resourceNameComputer.generateFileDescriptor(input, uri, ".XX"));

		// if calculated path has just one element, e.g. "Input.XX"
		// then local path segment is empty
		if (localOutputFilePath.getNameCount() < 2) {
			return Paths.get("");
		}

		// otherwise strip resource to get local path, i.e. Project/a/b/c/Input.XX => Project/a/b/c/
		return localOutputFilePath.subpath(0, localOutputFilePath.getNameCount() - 1);
	}

	/**
	 * TODO IDE-1487 currently there is no notion of default compiler. We fake call to the ES5 sub generator.
	 */
	final static String calculateProjectBasedOutputDirectory(N4JSProjectConfigSnapshot project,
			boolean includeProjectName) {
		return (includeProjectName) ? project.getPackageName() + "/" + project.getOutputPath()
				: project.getOutputPath();
	}

	/** Access to compiler ID */
	abstract public String getCompilerID();

	/** Access to compiler descriptor */
	abstract public CompilerDescriptor getDefaultDescriptor();

	/** Answers: Is this compiler activated for the input at hand? */
	public boolean isActive(Resource input) {
		return Boolean.valueOf(preferenceAccess.getPreference(input, getCompilerID(), CompilerProperties.IS_ACTIVE,
				getDefaultDescriptor()));
	}

	/**
	 * Checking the availability of a static polyfill, which will override the compilation of this module.
	 **/
	public boolean isNotStaticallyPolyfilled(Resource resource) {
		// val TModule tmodule = (N4JSResource::getModule(resource) ); // for some reason xtend cannot see static
		// getModule ?!
		if (resource instanceof N4JSResource) {
			TModule tmodule = N4JSResource.getModule(resource);
			// 1. resource must be StaticPolyfillAware and
			// 2. there must exist a polyfilling instance (second instance with same fqn)
			if (tmodule.isStaticPolyfillAware()) {
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
	private boolean isStaticPolyfillingModule(Resource resource) {
		TModule tmodule = (N4JSResource.getModule(resource));
		if (null != tmodule) {
			return tmodule.isStaticPolyfillModule();
		}
		return false;
	}

	/**
	 *
	 * @return true if the composite generator is applicable to the given resource and false otherwise.
	 */
	@Override
	public boolean isApplicableTo(Resource input) {
		return shouldBeCompiled(input, null);
	}

	/**
	 * Depending on the file-extension, determines if the given resource requires actual transpilation as opposed to
	 * simply copying the source file to the output folder.
	 *
	 * @param eResource
	 *            N4JS resource to check.
	 * @return true if the code requires transpilation.
	 */
	protected boolean justCopy(Resource eResource) {
		ResourceType resourceType = ResourceType.getResourceType(eResource);
		return !(resourceType.equals(ResourceType.N4JS) || resourceType.equals(ResourceType.N4JSX)
				|| resourceType.equals(ResourceType.N4JSD));
	}

	/**
	 * Take the content of resource and copy it over to the output folder without any transformation.
	 *
	 * @param resource
	 *            JS-code snippet which will be treated as text.
	 * @param fsa
	 *            file system access
	 */
	protected void copyWithoutTranspilation(Resource resource, IFileSystemAccess fsa) {
		StringWriter outCode = new StringWriter();
		// get script
		EObject script = resource.getContents().get(0);

		// obtain text
		String scriptAsText = NodeModelUtils.getNode(script).getRootNode().getText();

		// write
		String decorated = scriptAsText.toString();
		outCode.write(decorated);
		String filename = resourceNameComputer.generateFileDescriptor(resource,
				URIUtils.fileExtension(resource.getURI()));
		fsa.generateFile(filename, getCompilerID(), outCode.toString());
	}
}
