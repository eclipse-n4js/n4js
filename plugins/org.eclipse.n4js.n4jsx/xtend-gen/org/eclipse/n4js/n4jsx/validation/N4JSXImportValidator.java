/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4jsx.validation;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import org.apache.log4j.Logger;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4jsx.helpers.ReactHelper;
import org.eclipse.n4js.n4jsx.validation.IssueCodes;
import org.eclipse.n4js.organize.imports.ImportSpecifiersUtil;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.Log;
import org.eclipse.n4js.validation.validators.N4JSImportValidator;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

/**
 * This validator validates react import statements.
 */
@Log
@SuppressWarnings("all")
public class N4JSXImportValidator extends N4JSImportValidator {
  @Inject
  private ReactHelper reactHelper;
  
  /**
   * NEEEDED
   * 
   * when removed check methods will be called twice once by N4JSValidator, and once by
   * AbstractDeclarativeN4JSValidator
   */
  @Override
  public void register(final EValidatorRegistrar registrar) {
  }
  
  /**
   * Make sure the namespace to react module is React.
   */
  @Check
  public void checkReactImport(final NamespaceImportSpecifier importSpecifier) {
    final TModule module = ImportSpecifiersUtil.importedModule(importSpecifier);
    boolean _isReactModule = this.reactHelper.isReactModule(module);
    if (_isReactModule) {
      String _alias = importSpecifier.getAlias();
      boolean _notEquals = (!Objects.equal(_alias, ReactHelper.REACT_NAMESPACE));
      if (_notEquals) {
        this.addIssue(
          IssueCodes.getMessageForREACT_NAMESPACE_NOT_ALLOWED(), importSpecifier, IssueCodes.REACT_NAMESPACE_NOT_ALLOWED);
      }
    }
  }
  
  private final static Logger logger = Logger.getLogger(N4JSXImportValidator.class);
}
