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

import org.apache.log4j.Logger;
import org.eclipse.n4js.generator.common.GeneratorException;
import org.eclipse.n4js.utils.Log;
import org.eclipse.xtext.xbase.lib.InputOutput;

@Log
@SuppressWarnings("all")
public class ExceptionHandler {
  public void handleError(final String message, final Throwable cause) {
    InputOutput.<String>println(message);
    cause.printStackTrace();
    throw new GeneratorException(message, cause);
  }
  
  private final static Logger logger = Logger.getLogger(ExceptionHandler.class);
}
