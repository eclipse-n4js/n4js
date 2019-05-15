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
package org.eclipse.n4js.external.libraries;

import com.google.common.base.Preconditions;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * Utilities for dealing with the folder containing the external libraries.
 */
@SuppressWarnings("all")
public class ExternalLibraryFolderUtils {
  /**
   * The name of NPM's package json file.
   */
  public static final String PACKAGE_JSON = "package.json";
  
  /**
   * Unique name of the root npm folder for N4JS.
   */
  public static final String NPM_ROOT = ".n4npm";
  
  /**
   * Returns the initial contents of the "package.json" file located in the root of the external libraries
   * folder, next to the "node_modules" folder. Required by npm.
   * <p>
   * This is just a minimal stub. We never add/remove dependencies to this file ourselves; instead, npm will
   * update this file over time when we invoke "npm install", etc.
   */
  public static String createTargetPlatformPackageJson() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"name\": \"targetplatform\"");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
  
  /**
   * Creates a fresh target platform definition file ({@code package.json} file) in the given
   * target platform location.
   */
  public static File createTargetPlatformDefinitionFile(final File targetPlatformLocationFile) {
    final File targetPlatformFile = new File(targetPlatformLocationFile, ExternalLibraryFolderUtils.PACKAGE_JSON);
    boolean _exists = targetPlatformFile.exists();
    boolean _not = (!_exists);
    if (_not) {
      try {
        Preconditions.checkState(targetPlatformFile.createNewFile(), "Error while creating default target platform file.");
        FileWriter fw = null;
        try {
          FileWriter _fileWriter = new FileWriter(targetPlatformFile);
          fw = _fileWriter;
          fw.write(ExternalLibraryFolderUtils.createTargetPlatformPackageJson());
          fw.flush();
        } finally {
          fw.close();
        }
      } catch (final Throwable _t) {
        if (_t instanceof IOException) {
          final IOException e = (IOException)_t;
          final String message = "Error while initializing default target platform file.";
          throw new RuntimeException(message, e);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    }
    return targetPlatformFile;
  }
}
