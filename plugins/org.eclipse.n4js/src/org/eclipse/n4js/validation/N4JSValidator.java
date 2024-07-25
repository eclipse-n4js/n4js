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
package org.eclipse.n4js.validation;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
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
import org.eclipse.n4js.validation.validators.N4JSFlowgraphValidator;
import org.eclipse.n4js.validation.validators.N4JSFunctionValidator;
import org.eclipse.n4js.validation.validators.N4JSImportValidator;
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
import org.eclipse.n4js.validation.validators.N4JSTypeAliasValidator;
import org.eclipse.n4js.validation.validators.N4JSTypeValidator;
import org.eclipse.n4js.validation.validators.N4JSVariableValidator;
import org.eclipse.n4js.validation.validators.N4JSXValidator;
import org.eclipse.n4js.validation.validators.RuntimeDependencyValidator;
import org.eclipse.n4js.validation.validators.ThirdPartyValidator;
import org.eclipse.n4js.validation.validators.UnsupportedFeatureValidator;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;
import org.eclipse.xtext.validation.ComposedChecks;
import org.eclipse.xtext.xbase.lib.Exceptions;

import com.google.common.base.Throwables;
import com.google.inject.Inject;

/**
 * Validation rules for N4JS.
 * <p>
 * Note on contained validators: It is required to override
 *
 * <pre>
 * override register(EValidatorRegistrar registrar) {
 * 	// not needed for classes used as ComposedCheck
 * }
 * </pre>
 *
 * since otherwise they will check everything twice!
 *
 * @apiNote http://www.eclipse.org/Xtext/documentation.html#validation
 * @apiNote <a name="N4JSSpec">[N4JSSpec]</a> N4JS Specification / NumberFour AG. Berlin, 2013
 *          <a href="https://github.com/NumberFour/specs/">[GitHub]</a>
 */
@ComposedChecks(validators = {
		// N4JSStrictValidator.class,
		IDEBUGValidator.class,
		N4JSAccessModifierValidator.class,
		N4JSAnnotationValidator.class,
		N4JSClassifierValidator.class,
		N4JSClassValidator.class,
		N4JSDeclaredNameValidator.class,
		N4JSDependencyInjectionValidator.class,
		N4JSDestructureValidator.class,
		N4JSEnumValidator.class,
		N4JSTypeAliasValidator.class,
		N4JSExpressionValidator.class,
		N4JSExternalValidator.class,
		N4JSFlowgraphValidator.class,
		N4JSFunctionValidator.class,
		N4JSImportValidator.class,
		N4JSInjectorCallsitesValidator.class,
		N4JSInterfaceValidator.class,
		N4JSLambdaValidator.class,
		N4JSMemberRedefinitionValidator.class,
		N4JSMemberValidator.class,
		N4JSModuleValidator.class,
		N4JSNameValidator.class,
		N4JSStatementValidator.class,
		N4JSSuperValidator.class,
		N4JSSyntaxValidator.class,
		N4JSTypeValidator.class,
		N4JSVariableValidator.class,
		N4JSXValidator.class,
		ThirdPartyValidator.class,
		UnsupportedFeatureValidator.class,
		RuntimeDependencyValidator.class
})
/** validations are defined in composed validator classes */
@SuppressWarnings("javadoc")
public class N4JSValidator extends AbstractMessageAdjustingN4JSValidator {
	private final static Logger LOGGER = Logger.getLogger(N4JSValidator.class);

	@Inject
	private OperationCanceledManager operationCanceledManager;

	/**
	 * Override to improve error message in case of abnormal termination of validation.
	 */
	@Override
	public MethodWrapperCancelable createMethodWrapper(AbstractDeclarativeValidator instanceToUse, Method method) {
		return new N4JSMethodWrapperCancelable(instanceToUse, method, operationCanceledManager);
	}

	public static class N4JSMethodWrapperCancelable extends MethodWrapperCancelable {
		private final OperationCanceledManager operationCanceledManager;

		public N4JSMethodWrapperCancelable(AbstractDeclarativeValidator instance, Method m,
				OperationCanceledManager operationCanceledManager) {
			super(instance, m);
			this.operationCanceledManager = operationCanceledManager;
		}

		@Override
		public void handleInvocationTargetException(Throwable targetException, State state) {
			// ignore GuardException, check is just not evaluated if guard is false
			// ignore NullPointerException, as not having to check for NPEs all the time is a convenience feature
			super.handleInvocationTargetException(targetException, state);
			if (targetException instanceof NullPointerException) {
				Exceptions.sneakyThrow(targetException);
			}
		}

		// catch exceptions and create better error message as
		// org.eclipse.xtext.validation.CompositeEValidator.validate(EClass, EObject, DiagnosticChain, Map<Object,
		// Object>)
		// note: cannot override validate method directly because it is final
		@Override
		public void invoke(State state) {
			String valMethodName = this.getMethod().getName();
			URI URI = state.currentObject.eResource().getURI();

			operationCanceledManager.checkCanceled(getCancelIndicator(state));

			DataCollector dcCheckMethod = N4JSDataCollectors.createDataCollectorForCheckMethod(valMethodName);
			Measurement mesVM = dcCheckMethod.getMeasurement(valMethodName + "_" + URI.toString());
			try {
				super.invoke(state);
			} catch (Exception e) {
				operationCanceledManager.propagateIfCancelException(e);
				// GH-2002: TEMPORARY DEBUG LOGGING
				// Only passing the exception to Logger#error(String,Throwable) does not emit the stack trace of the
				// caught
				// exception in all logger configurations; we therefore include the stack trace in the main message:
				LOGGER.error("exception while executing EValidator: " + e.getMessage() + "\n"
						+ Throwables.getStackTraceAsString(e), e);
				new RuntimeException(e).printStackTrace();

				state.chain.add(
						new BasicDiagnostic(Diagnostic.ERROR, state.currentObject.toString(), 0, e.getMessage(),
								new Object[] { e, URI }));
			} finally {
				mesVM.close();
			}
		}
	}
}
