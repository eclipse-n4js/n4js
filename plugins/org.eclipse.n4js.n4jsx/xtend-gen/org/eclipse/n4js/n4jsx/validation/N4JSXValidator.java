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
package org.eclipse.n4js.n4jsx.validation;

import com.google.inject.Inject;
import java.lang.reflect.Method;
import java.util.List;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.n4js.n4jsx.validation.N4JSXImportValidator;
import org.eclipse.n4js.n4jsx.validation.N4JSXReactBindingValidator;
import org.eclipse.n4js.utils.Log;
import org.eclipse.n4js.validation.AbstractMessageAdjustingN4JSValidator;
import org.eclipse.n4js.validation.validators.IDEBUGValidator;
import org.eclipse.n4js.validation.validators.N4JSAccessModifierValidator;
import org.eclipse.n4js.validation.validators.N4JSAnnotationValidator;
import org.eclipse.n4js.validation.validators.N4JSClassValidator;
import org.eclipse.n4js.validation.validators.N4JSClassifierValidator;
import org.eclipse.n4js.validation.validators.N4JSDeclaredNameValidator;
import org.eclipse.n4js.validation.validators.N4JSDependencyInjectionValidator;
import org.eclipse.n4js.validation.validators.N4JSDestructureValidator;
import org.eclipse.n4js.validation.validators.N4JSEnumValidator;
import org.eclipse.n4js.validation.validators.N4JSExpressionValidator;
import org.eclipse.n4js.validation.validators.N4JSExternalValidator;
import org.eclipse.n4js.validation.validators.N4JSFunctionValidator;
import org.eclipse.n4js.validation.validators.N4JSInjectorCallsitesValidator;
import org.eclipse.n4js.validation.validators.N4JSInterfaceValidator;
import org.eclipse.n4js.validation.validators.N4JSLambdaValidator;
import org.eclipse.n4js.validation.validators.N4JSMemberRedefinitionValidator;
import org.eclipse.n4js.validation.validators.N4JSMemberValidator;
import org.eclipse.n4js.validation.validators.N4JSModuleValidator;
import org.eclipse.n4js.validation.validators.N4JSNameValidator;
import org.eclipse.n4js.validation.validators.N4JSStatementValidator;
import org.eclipse.n4js.validation.validators.N4JSSuperValidator;
import org.eclipse.n4js.validation.validators.N4JSSyntaxValidator;
import org.eclipse.n4js.validation.validators.N4JSTypeValidator;
import org.eclipse.n4js.validation.validators.N4JSVariableValidator;
import org.eclipse.n4js.validation.validators.UnsupportedFeatureValidator;
import org.eclipse.n4js.xsemantics.validation.InternalTypeSystemValidator;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;
import org.eclipse.xtext.validation.ComposedChecks;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * This class contains custom validation rules.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
@ComposedChecks(validators = { IDEBUGValidator.class, N4JSNameValidator.class, N4JSClassifierValidator.class, N4JSMemberRedefinitionValidator.class, N4JSClassValidator.class, N4JSInterfaceValidator.class, N4JSFunctionValidator.class, N4JSXImportValidator.class, N4JSTypeValidator.class, N4JSExpressionValidator.class, N4JSMemberValidator.class, N4JSExternalValidator.class, N4JSAccessModifierValidator.class, N4JSSuperValidator.class, N4JSLambdaValidator.class, N4JSVariableValidator.class, N4JSDeclaredNameValidator.class, N4JSStatementValidator.class, N4JSAnnotationValidator.class, N4JSEnumValidator.class, N4JSSyntaxValidator.class, N4JSDependencyInjectionValidator.class, N4JSInjectorCallsitesValidator.class, N4JSModuleValidator.class, N4JSDestructureValidator.class, UnsupportedFeatureValidator.class, N4JSXReactBindingValidator.class })
@Log
@SuppressWarnings("all")
public class N4JSXValidator extends InternalTypeSystemValidator {
  @Inject
  private OperationCanceledManager operationCanceledManager;
  
  /**
   * Override to improve error message in case of abnormal termination of validation.
   */
  @Override
  public AbstractMessageAdjustingN4JSValidator.MethodWrapperCancelable createMethodWrapper(final AbstractDeclarativeValidator instanceToUse, final Method method) {
    return new AbstractMessageAdjustingN4JSValidator.MethodWrapperCancelable(instanceToUse, method) {
      @Override
      public void handleInvocationTargetException(final Throwable targetException, final AbstractDeclarativeValidator.State state) {
        super.handleInvocationTargetException(targetException, state);
        if ((targetException instanceof NullPointerException)) {
          Exceptions.sneakyThrow(targetException);
        }
      }
      
      @Override
      public void invoke(final AbstractDeclarativeValidator.State state) {
        N4JSXValidator.this.operationCanceledManager.checkCanceled(this.getCancelIndicator(state));
        try {
          super.invoke(state);
        } catch (final Throwable _t) {
          if (_t instanceof Exception) {
            final Exception e = (Exception)_t;
            N4JSXValidator.this.operationCanceledManager.propagateIfCancelException(e);
            N4JSXValidator.logger.error("Error executing EValidator", e);
            String _string = state.currentObject.toString();
            String _message = e.getMessage();
            BasicDiagnostic _basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR, _string, 0, _message, new Object[] { e });
            state.chain.add(_basicDiagnostic);
          } else {
            throw Exceptions.sneakyThrow(_t);
          }
        }
      }
    };
  }
  
  @Override
  protected List<EPackage> getEPackages() {
    final List<EPackage> result = super.getEPackages();
    result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/n4js/n4jsx/N4JSX"));
    return result;
  }
  
  private final static Logger logger = Logger.getLogger(N4JSXValidator.class);
}
