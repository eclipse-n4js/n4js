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
import org.eclipse.n4js.smith.ClosableMeasurement;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;
import org.eclipse.n4js.ts.validation.TypesValidator;
import org.eclipse.n4js.utils.Log;
import org.eclipse.n4js.validation.validators.IDEBUGValidator;
import org.eclipse.n4js.validation.validators.N4IDLValidator;
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
import org.eclipse.n4js.validation.validators.N4JSTypeValidator;
import org.eclipse.n4js.validation.validators.N4JSVariableValidator;
import org.eclipse.n4js.validation.validators.N4JSXValidator;
import org.eclipse.n4js.validation.validators.ThirdPartyValidator;
import org.eclipse.n4js.validation.validators.UnsupportedFeatureValidator;
import org.eclipse.n4js.xsemantics.validation.InternalTypeSystemValidator;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;
import org.eclipse.xtext.validation.ComposedChecks;
import org.eclipse.xtext.xbase.lib.Exceptions;

import com.google.inject.Inject;

/**
 * Validation rules for N4JS.
 *
 * Validation of type expression is defined in {@link TypesValidator}. However, some context sensitive validations such
 * as type ref of formal parameters or return types have to be repeated here, as the rules are overwritten.
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
 * @see <a href="http://www.eclipse.org/Xtext/documentation.html#validation">[GitHub]</a>
 * @see <a href="N4JSSpec">[N4JS Specification / NumberFour AG. Berlin, 2013]</a>
 *      <a href="https://github.com/NumberFour/specs/">[GitHub]</a>
 * @see TypesValidator
 */
@ComposedChecks(validators = {
		// N4JSStrictValidator,
		IDEBUGValidator.class,
		N4JSAccessModifierValidator.class,
		N4JSAnnotationValidator.class,
		N4JSClassifierValidator.class,
		N4JSClassValidator.class,
		N4JSDeclaredNameValidator.class,
		N4JSDependencyInjectionValidator.class,
		N4JSDestructureValidator.class,
		N4JSEnumValidator.class,
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
		N4IDLValidator.class
})
@Log
/** validations are defined in composed validator classes */
public class N4JSValidator extends InternalTypeSystemValidator {
	static private final Logger logger = Logger.getLogger(N4JSValidator.class);

	@Inject
	private OperationCanceledManager operationCanceledManager;

	/**
	 * Override to improve error message in case of abnormal termination of validation.
	 */
	@Override
	protected MethodWrapperCancelable createMethodWrapper(AbstractDeclarativeValidator instanceToUse, Method method) {
		// when running tests (e.g. org.eclipse.n4js.tests.scoping.ErrorTest)
		// the following data collectors need to be created here
		DataCollectors.INSTANCE.getOrCreateDataCollector("Build");
		DataCollectors.INSTANCE.getOrCreateDataCollector("Validations", "Build");

		return new N4JSMethodWrapperCancelable(instanceToUse, method, operationCanceledManager);
	}

	static class N4JSMethodWrapperCancelable extends MethodWrapperCancelable {
		private final OperationCanceledManager operationCanceledManager;

		protected N4JSMethodWrapperCancelable(AbstractDeclarativeValidator instance, Method m,
				OperationCanceledManager operationCanceledManager) {

			super(instance, m);
			this.operationCanceledManager = operationCanceledManager;
		}

		@Override
		protected void handleInvocationTargetException(Throwable targetException, State state) {
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
			operationCanceledManager.checkCanceled(getCancelIndicator(state));

			String valMethodName = this.getMethod().getName();
			DataCollector dcValidator = DataCollectors.INSTANCE.getOrCreateDataCollector(valMethodName, "Build",
					"Validations");
			URI uri = state.currentObject.eResource().getURI();
			try (ClosableMeasurement mes = dcValidator.getClosableMeasurement(valMethodName + "_" + uri.toString());) {

				super.invoke(state);
			} catch (Exception e) {
				operationCanceledManager.propagateIfCancelException(e);
				logger.error("Error executing EValidator", e);

				String source = state.currentObject.toString();
				String msg = e.getMessage();
				BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR, source, 0, msg, new Object[] { e });
				state.chain.add(diagnostic);
			}
		}
	}
}
