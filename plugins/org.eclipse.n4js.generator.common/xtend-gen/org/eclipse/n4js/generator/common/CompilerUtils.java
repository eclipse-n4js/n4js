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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.generator.common.ExceptionHandler;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.ResourceNameComputer;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;

@Singleton
@SuppressWarnings("all")
public class CompilerUtils {
  @Inject
  private ResourceNameComputer projectUtils;
  
  @Inject
  @Extension
  private ExceptionHandler _exceptionHandler;
  
  /**
   * Convenience method, delegates to {@link #getTargetFileName(URI , String)} with the URI of the given resource.
   */
  public String getTargetFileName(final Resource n4jsSourceFile, final String compiledFileExtension) {
    return this.getTargetFileName(n4jsSourceFile.getURI(), compiledFileExtension);
  }
  
  /**
   * Returns the name of the target file (without path) to which the source is to be compiled to.
   * Default implementation returns a configured project Name with version + file name + extension.
   * E.g., "proj/p/A.js" for a file A in proj and a compiledFileExtension of "js".
   * <p>
   * The compiledFileExtension should not include the separator dot; it may be <code>null</code>
   * and then no extension is appended.
   */
  public String getTargetFileName(final URI n4jsSourceURI, final String compiledFileExtension) {
    String _xifexpression = null;
    if (((compiledFileExtension != null) && (compiledFileExtension.length() > 0))) {
      _xifexpression = ("." + compiledFileExtension);
    } else {
      _xifexpression = "";
    }
    final String extStr = _xifexpression;
    String _xtrycatchfinallyexpression = null;
    try {
      _xtrycatchfinallyexpression = this.projectUtils.generateFileDescriptor(n4jsSourceURI, extStr);
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
   * Returns the name of the target file (without path) to which the source is to be compiled to.
   * Default implementation returns a configured project Name with version + file name + extension.
   * E.g., "proj/p/A.js" for a file A in proj and a compiledFileExtension of "js".
   * <p>
   * The compiledFileExtension should not include the separator dot; it may be <code>null</code>
   * and then no extension is appended.
   */
  public String getTargetFileName(final IN4JSProject project, final URI n4jsSourceURI, final String compiledFileExtension) {
    String _xifexpression = null;
    if (((compiledFileExtension != null) && (compiledFileExtension.length() > 0))) {
      _xifexpression = ("." + compiledFileExtension);
    } else {
      _xifexpression = "";
    }
    final String extStr = _xifexpression;
    String _xtrycatchfinallyexpression = null;
    try {
      _xtrycatchfinallyexpression = this.projectUtils.generateFileDescriptor(project, n4jsSourceURI, extStr);
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
   * Return workspace relative platform url to a given module.
   */
  public String getModuleName(final String sourcePathName, final String fileExt) {
    final URI sourceURI = URI.createPlatformResourceURI(sourcePathName, true);
    final String simpleTargetFileName = this.getTargetFileName(sourceURI, fileExt);
    int _length = simpleTargetFileName.length();
    int _length_1 = fileExt.length();
    int _minus = (_length - _length_1);
    int _minus_1 = (_minus - 1);
    final String targetModuleName = simpleTargetFileName.substring(0, _minus_1);
    return targetModuleName;
  }
  
  /**
   * delegates to {@code N4JSresource::getModule}
   */
  public TModule retrieveModule(final N4JSResource res) {
    return N4JSResource.getModule(res);
  }
}
