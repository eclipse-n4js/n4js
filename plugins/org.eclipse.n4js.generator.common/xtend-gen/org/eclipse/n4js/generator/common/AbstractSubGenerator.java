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
package org.eclipse.n4js.generator.common;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.generator.common.CompilerDescriptor;
import org.eclipse.n4js.generator.common.CompilerProperties;
import org.eclipse.n4js.generator.common.CompilerUtils;
import org.eclipse.n4js.generator.common.ExceptionHandler;
import org.eclipse.n4js.generator.common.GeneratorException;
import org.eclipse.n4js.generator.common.GeneratorOption;
import org.eclipse.n4js.generator.common.IGeneratorMarkerSupport;
import org.eclipse.n4js.generator.common.ISubGenerator;
import org.eclipse.n4js.generator.common.N4JSPreferenceAccess;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.ResourceNameComputer;
import org.eclipse.n4js.projectModel.StaticPolyfillHelper;
import org.eclipse.n4js.resource.N4JSCache;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.resource.XpectAwareFileExtensionCalculator;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.Log;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.generator.AbstractFileSystemAccess;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@Log
@SuppressWarnings("all")
public abstract class AbstractSubGenerator implements ISubGenerator {
  @Accessors
  private CompilerDescriptor compilerDescriptor = null;
  
  @Inject
  private ResourceNameComputer projectUtils;
  
  @Inject
  private StaticPolyfillHelper staticPolyfillHelper;
  
  @Inject
  private IN4JSCore n4jsCore;
  
  @Inject
  private CompilerUtils compilerUtils;
  
  @Inject
  private IResourceValidator resVal;
  
  @Inject
  private N4JSCache cache;
  
  @Inject
  private IGeneratorMarkerSupport genMarkerSupport;
  
  @Inject
  private OperationCanceledManager operationCanceledManager;
  
  @Inject
  @Extension
  private ExceptionHandler _exceptionHandler;
  
  @Inject
  @Extension
  private N4JSPreferenceAccess _n4JSPreferenceAccess;
  
  @Inject
  @Extension
  private XpectAwareFileExtensionCalculator _xpectAwareFileExtensionCalculator;
  
  @Override
  public CompilerDescriptor getCompilerDescriptor() {
    if ((this.compilerDescriptor == null)) {
      this.compilerDescriptor = this.getDefaultDescriptor();
    }
    return this.compilerDescriptor;
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
  @Override
  public void doGenerate(final Resource input, final IFileSystemAccess fsa) {
    try {
      this.genMarkerSupport.deleteMarker(input);
      this.updateOutputPath(fsa, this.getCompilerID(), input);
      this.internalDoGenerate(input, GeneratorOption.DEFAULT_OPTIONS, fsa);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        this.operationCanceledManager.propagateIfCancelException(e);
        String _elvis = null;
        String _xifexpression = null;
        if ((input instanceof N4JSResource)) {
          TModule _module = ((N4JSResource)input).getModule();
          String _moduleSpecifier = null;
          if (_module!=null) {
            _moduleSpecifier=_module.getModuleSpecifier();
          }
          _xifexpression = _moduleSpecifier;
        }
        if (_xifexpression != null) {
          _elvis = _xifexpression;
        } else {
          URI _uRI = input.getURI();
          String _string = null;
          if (_uRI!=null) {
            _string=_uRI.toString();
          }
          _elvis = _string;
        }
        final String target = _elvis;
        final String msgMarker = (("Severe error occurred while transpiling module " + target) + ". Check error log for details about the failure.");
        this.genMarkerSupport.createMarker(input, msgMarker, IGeneratorMarkerSupport.Severity.ERROR);
        if ((e instanceof GeneratorException)) {
          throw ((GeneratorException)e);
        }
        String _xifexpression_1 = null;
        String _message = e.getMessage();
        boolean _tripleEquals = (_message == null);
        if (_tripleEquals) {
          String _name = e.getClass().getName();
          _xifexpression_1 = ("type=" + _name);
        } else {
          String _message_1 = e.getMessage();
          _xifexpression_1 = ("message=" + _message_1);
        }
        String msg = _xifexpression_1;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("Severe error occurred in transpiler=");
        String _compilerID = this.getCompilerID();
        _builder.append(_compilerID);
        _builder.append(" ");
        _builder.append(msg);
        _builder.append(".");
        this._exceptionHandler.handleError(_builder.toString(), e);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  @Override
  public boolean shouldBeCompiled(final Resource input, final CancelIndicator monitor) {
    final boolean autobuildEnabled = this.isActive(input);
    String _lowerCase = input.getURI().fileExtension().toLowerCase();
    final boolean isXPECTMode = Objects.equal(N4JSGlobals.XT_FILE_EXTENSION, _lowerCase);
    final URI inputUri = input.getURI();
    final String fileExtension = this._xpectAwareFileExtensionCalculator.getXpectAwareFileExtension(inputUri);
    return (((((autobuildEnabled && ((this.hasValidFileExtension(input) || "n4jsx".equals(fileExtension)) || "jsx".equals(fileExtension))) && this.isSource(inputUri)) && 
      ((this.isNoValidate(inputUri) || this.isExternal(inputUri)) || ((EMFPlugin.IS_ECLIPSE_RUNNING && (!isXPECTMode)) || this.hasNoErrors(input, monitor)))) && (!this.isStaticPolyfillingModule(input))) && this.hasNoPolyfillErrors(input, monitor));
  }
  
  private boolean isSource(final URI n4jsSourceURI) {
    return this.n4jsCore.findN4JSSourceContainer(n4jsSourceURI).isPresent();
  }
  
  private boolean isNoValidate(final URI n4jsSourceURI) {
    return this.n4jsCore.isNoValidate(n4jsSourceURI);
  }
  
  private boolean isExternal(final URI n4jsSourceURI) {
    final Optional<? extends IN4JSSourceContainer> sourceContainerOpt = this.n4jsCore.findN4JSSourceContainer(n4jsSourceURI);
    boolean _isPresent = sourceContainerOpt.isPresent();
    if (_isPresent) {
      final IN4JSSourceContainer sourceContainer = sourceContainerOpt.get();
      return sourceContainer.isExternal();
    }
    return false;
  }
  
  /**
   * If the resource has a static polyfill, then ensure it is error-free.
   * Calls {@link #hasNoErrors()} on the static polyfill resource.
   */
  private boolean hasNoPolyfillErrors(final Resource input, final CancelIndicator monitor) {
    final N4JSResource resSPoly = this.staticPolyfillHelper.getStaticPolyfillResource(input);
    if ((resSPoly == null)) {
      return true;
    }
    return this.hasNoErrors(resSPoly, monitor);
  }
  
  /**
   * Does validation report no errors for the given resource?
   * If errors exists, log them as a side-effect.
   * If validation was canceled before finishing, don't assume absence of errors.
   */
  private boolean hasNoErrors(final Resource input, final CancelIndicator monitor) {
    final List<Issue> issues = this.cache.getOrElseUpdateIssues(this.resVal, input, monitor);
    if ((null == issues)) {
      this.warnDueToCancelation(input, null);
      return false;
    }
    final Function1<Issue, Boolean> _function = (Issue it) -> {
      Severity _severity = it.getSeverity();
      return Boolean.valueOf(Objects.equal(_severity, Severity.ERROR));
    };
    final Iterable<Issue> errors = IterableExtensions.<Issue>filter(issues, _function);
    boolean _isEmpty = IterableExtensions.isEmpty(errors);
    if (_isEmpty) {
      return true;
    }
    boolean _isDebugEnabled = AbstractSubGenerator.logger.isDebugEnabled();
    if (_isDebugEnabled) {
      final Consumer<Issue> _function_1 = (Issue it) -> {
        URI _uRI = input.getURI();
        String _plus = (_uRI + "  ");
        String _message = it.getMessage();
        String _plus_1 = (_plus + _message);
        String _plus_2 = (_plus_1 + "  ");
        Severity _severity = it.getSeverity();
        String _plus_3 = (_plus_2 + _severity);
        String _plus_4 = (_plus_3 + " @L_");
        Integer _lineNumber = it.getLineNumber();
        String _plus_5 = (_plus_4 + _lineNumber);
        String _plus_6 = (_plus_5 + " ");
        AbstractSubGenerator.logger.debug(_plus_6);
      };
      errors.forEach(_function_1);
    }
    return false;
  }
  
  private void warnDueToCancelation(final Resource input, final Throwable exc) {
    URI _uRI = input.getURI();
    String _plus = ("User canceled the validation of " + _uRI);
    final String msg = (_plus + ". Will not compile.");
    if ((null == exc)) {
      AbstractSubGenerator.logger.warn(msg);
    } else {
      AbstractSubGenerator.logger.warn(msg, exc);
    }
  }
  
  /**
   * Actual generation to be overridden by subclasses.
   */
  protected abstract void internalDoGenerate(final Resource resource, final GeneratorOption[] options, final IFileSystemAccess access);
  
  /**
   * Returns the name of the target file (without path) to which the source is to be compiled to.
   * Default implementation returns a configured project Name with version + file name + extension.
   * E.g., "proj-0.0.1/p/A.js" for a file A in proj.
   */
  public String getTargetFileName(final Resource n4jsSourceFile, final String compiledFileExtension) {
    URI _xifexpression = null;
    if ((ResourceType.xtHidesOtherExtension(n4jsSourceFile.getURI()) || Objects.equal(N4JSGlobals.XT_FILE_EXTENSION, n4jsSourceFile.getURI().fileExtension().toLowerCase()))) {
      _xifexpression = n4jsSourceFile.getURI().trimFileExtension();
    } else {
      _xifexpression = n4jsSourceFile.getURI();
    }
    URI souceURI = _xifexpression;
    String _xtrycatchfinallyexpression = null;
    try {
      _xtrycatchfinallyexpression = this.projectUtils.generateFileDescriptor(souceURI, ("." + compiledFileExtension));
    } catch (final Throwable _t) {
      if (_t instanceof Throwable) {
        final Throwable t = (Throwable)_t;
        Object _xblockexpression = null;
        {
          this._exceptionHandler.handleError(t.getMessage(), t);
          _xblockexpression = null;
        }
        _xtrycatchfinallyexpression = ((String)_xblockexpression);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    final String targetFilePath = _xtrycatchfinallyexpression;
    return targetFilePath;
  }
  
  /**
   * Checking if a resource will be as source to the generator.
   */
  public boolean hasValidFileExtension(final Resource resource) {
    final ResourceType resourceType = ResourceType.getResourceType(resource);
    return (resourceType.equals(ResourceType.JS) || resourceType.equals(ResourceType.N4JS));
  }
  
  /**
   * Convenient access to the Script-Element
   */
  public Script rootElement(final Resource resource) {
    return IterableExtensions.<Script>head(Iterables.<Script>filter(resource.getContents(), Script.class));
  }
  
  /**
   * The file-extension of the compiled result
   */
  public String getCompiledFileExtension(final Resource input) {
    return this._n4JSPreferenceAccess.getPreference(input, this.getCompilerID(), CompilerProperties.COMPILED_FILE_EXTENSION, 
      this.getDefaultDescriptor());
  }
  
  /**
   * The file-extension of the source-map to the compiled result
   */
  public String getCompiledFileSourceMapExtension(final Resource input) {
    return this._n4JSPreferenceAccess.getPreference(input, this.getCompilerID(), CompilerProperties.COMPILED_FILE_SOURCEMAP_EXTENSION, 
      this.getDefaultDescriptor());
  }
  
  /**
   * Adjust output-path of the generator to match the N4JS projects-settings.
   */
  public void updateOutputPath(final IFileSystemAccess fsa, final String compilerID, final Resource input) {
    final String outputPath = this.n4jsCore.getOutputPath(input.getURI());
    if ((fsa instanceof AbstractFileSystemAccess)) {
      final OutputConfiguration conf = ((AbstractFileSystemAccess)fsa).getOutputConfigurations().get(compilerID);
      if ((conf != null)) {
        conf.setOutputDirectory(AbstractSubGenerator.calculateOutputDirectory(outputPath, compilerID));
      }
    }
  }
  
  /**
   * Navigation from the generated output-location to the location of the input-resource
   */
  public Path calculateNavigationFromOutputToSourcePath(final IFileSystemAccess fsa, final String compilerID, final N4JSResource input) {
    final Optional<? extends IN4JSProject> projectctContainer = this.n4jsCore.findProject(input.getURI());
    final IN4JSProject project = projectctContainer.get();
    final Path projectPath = project.getLocationPath();
    final URI projectLocURI = project.getLocation().appendSegment("");
    final String outputPath = this.n4jsCore.getOutputPath(input.getURI());
    final String outputDirectory = AbstractSubGenerator.calculateOutputDirectory(outputPath, compilerID);
    final Path localOutputFilePath = Paths.get(this.projectUtils.generateFileDescriptor(input.getURI(), ".XX"));
    int _nameCount = localOutputFilePath.getNameCount();
    int _minus = (_nameCount - 1);
    final Path localOutputDir = localOutputFilePath.subpath(0, _minus);
    final URI completetSourceURI = input.getURI().trimSegments(1).deresolve(projectLocURI);
    String completetSource = completetSourceURI.toFileString();
    if (((null == completetSource) && (project.getLocation() == input.getURI().trimSegments(1)))) {
      completetSource = projectPath.toFile().getAbsolutePath();
    }
    final Path fullOutpath = projectPath.resolve(outputDirectory).normalize().resolve(localOutputDir).normalize();
    final Path fullSourcePath = projectPath.resolve(completetSource).normalize();
    final Path rel = fullOutpath.relativize(fullSourcePath);
    return rel;
  }
  
  /**
   * Simply concatenates the outputPath with the compilerID, e.g.
   * for "src-gen" and "es5", this returns "src-gen/es5".
   * 
   * @param outputPath usually src-gen by default
   * @param compilerID ID of the compiler, which is a subfolder in the output path (e.g. "es5")
   */
  public static String calculateOutputDirectory(final String outputPath, final String compilerID) {
    return ((("./" + outputPath) + "/") + compilerID);
  }
  
  /**
   * Access to compiler ID
   */
  public abstract String getCompilerID();
  
  /**
   * Access to compiler descriptor
   */
  protected abstract CompilerDescriptor getDefaultDescriptor();
  
  /**
   * Answers: Is this compiler activated for the input at hand?
   */
  public boolean isActive(final Resource input) {
    return (Boolean.valueOf(this._n4JSPreferenceAccess.getPreference(input, this.getCompilerID(), CompilerProperties.IS_ACTIVE, this.getDefaultDescriptor()))).booleanValue();
  }
  
  /**
   * Checking the availability of a static polyfill, which will override the compilation of this module.
   */
  public boolean isNotStaticallyPolyfilled(final Resource resource) {
    if ((resource instanceof N4JSResource)) {
      final TModule tmodule = this.compilerUtils.retrieveModule(((N4JSResource)resource));
      boolean _isStaticPolyfillAware = tmodule.isStaticPolyfillAware();
      if (_isStaticPolyfillAware) {
        boolean _hasStaticPolyfill = this.staticPolyfillHelper.hasStaticPolyfill(resource);
        if (_hasStaticPolyfill) {
          return false;
        }
      }
    }
    return true;
  }
  
  /**
   * Checking if this resource represents a static polyfill, which will contribute to a filled resource.
   */
  public boolean isStaticPolyfillingModule(final Resource resource) {
    final TModule tmodule = N4JSResource.getModule(resource);
    if ((null != tmodule)) {
      return tmodule.isStaticPolyfillModule();
    }
    return false;
  }
  
  private final static Logger logger = Logger.getLogger(AbstractSubGenerator.class);
  
  public void setCompilerDescriptor(final CompilerDescriptor compilerDescriptor) {
    this.compilerDescriptor = compilerDescriptor;
  }
}
