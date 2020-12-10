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
package org.eclipse.n4js.validation

import com.google.common.base.Throwables
import com.google.inject.Inject
import java.lang.reflect.Method
import org.eclipse.emf.common.util.BasicDiagnostic
import org.eclipse.emf.common.util.Diagnostic
import org.eclipse.n4js.smith.N4JSDataCollectors
import org.eclipse.n4js.ts.validation.TypesValidator
import org.eclipse.n4js.utils.Log
import org.eclipse.n4js.validation.validators.IDEBUGValidator
import org.eclipse.n4js.validation.validators.N4IDLMigrationValidator
import org.eclipse.n4js.validation.validators.N4IDLValidator
import org.eclipse.n4js.validation.validators.N4JSAccessModifierValidator
import org.eclipse.n4js.validation.validators.N4JSAnnotationValidator
import org.eclipse.n4js.validation.validators.N4JSClassValidator
import org.eclipse.n4js.validation.validators.N4JSClassifierValidator
import org.eclipse.n4js.validation.validators.N4JSDeclaredNameValidator
import org.eclipse.n4js.validation.validators.N4JSDependencyInjectionValidator
import org.eclipse.n4js.validation.validators.N4JSDestructureValidator
import org.eclipse.n4js.validation.validators.N4JSEnumValidator
import org.eclipse.n4js.validation.validators.N4JSExpressionValidator
import org.eclipse.n4js.validation.validators.N4JSExternalValidator
import org.eclipse.n4js.validation.validators.N4JSFlowgraphValidator
import org.eclipse.n4js.validation.validators.N4JSFunctionValidator
import org.eclipse.n4js.validation.validators.N4JSImportValidator
import org.eclipse.n4js.validation.validators.N4JSInjectorCallsitesValidator
import org.eclipse.n4js.validation.validators.N4JSInterfaceValidator
import org.eclipse.n4js.validation.validators.N4JSLambdaValidator
import org.eclipse.n4js.validation.validators.N4JSMemberRedefinitionValidator
import org.eclipse.n4js.validation.validators.N4JSMemberValidator
import org.eclipse.n4js.validation.validators.N4JSModuleValidator
import org.eclipse.n4js.validation.validators.N4JSNameValidator
import org.eclipse.n4js.validation.validators.N4JSStatementValidator
import org.eclipse.n4js.validation.validators.N4JSSuperValidator
import org.eclipse.n4js.validation.validators.N4JSSyntaxValidator
import org.eclipse.n4js.validation.validators.N4JSTypeValidator
import org.eclipse.n4js.validation.validators.N4JSVariableValidator
import org.eclipse.n4js.validation.validators.N4JSXValidator
import org.eclipse.n4js.validation.validators.RuntimeDependencyValidator
import org.eclipse.n4js.validation.validators.ThirdPartyValidator
import org.eclipse.n4js.validation.validators.UnsupportedFeatureValidator
import org.eclipse.xtext.service.OperationCanceledManager
import org.eclipse.xtext.validation.AbstractDeclarativeValidator
import org.eclipse.xtext.validation.ComposedChecks

/**
 * Validation rules for N4JS.
 * 
 * Validation of type expression is defined in
 * {@link TypesValidator}. However, some context
 * sensitive validations such as type ref of formal parameters or return types
 * have to be repeated here, as the rules are overwritten.
 * <p>
 * Note on contained validators:
 * It is required to override
 * <pre>
 * override register(EValidatorRegistrar registrar) {
 * 	// not needed for classes used as ComposedCheck
 * }
 * </pre>
 * since otherwise they will check everything twice!
 * 
 * @see http://www.eclipse.org/Xtext/documentation.html#validation
 * @see <a name="N4JSSpec">[N4JSSpec]</a> N4JS Specification / NumberFour AG. Berlin, 2013 <a href="https://github.com/NumberFour/specs/">[GitHub]</a>
 * @see TypesValidator
 */
@ComposedChecks(validators=#[
	// N4JSStrictValidator,
	IDEBUGValidator,
	N4JSAccessModifierValidator,
	N4JSAnnotationValidator,
	N4JSClassifierValidator,
	N4JSClassValidator,
	N4JSDeclaredNameValidator,
	N4JSDependencyInjectionValidator,
	N4JSDestructureValidator,
	N4JSEnumValidator,
	N4JSExpressionValidator,
	N4JSExternalValidator,
	N4JSFlowgraphValidator,
	N4JSFunctionValidator,
	N4JSImportValidator,
	N4JSInjectorCallsitesValidator,
	N4JSInterfaceValidator,
	N4JSLambdaValidator,
	N4JSMemberRedefinitionValidator,
	N4JSMemberValidator,
	N4JSModuleValidator,
	N4JSNameValidator,
	N4JSStatementValidator,
	N4JSSuperValidator,
	N4JSSyntaxValidator,
	N4JSTypeValidator,
	N4JSVariableValidator,
	N4JSXValidator,
	ThirdPartyValidator,
	UnsupportedFeatureValidator,
	N4IDLValidator,
	N4IDLMigrationValidator,
	RuntimeDependencyValidator
])
@Log
/** validations are defined in composed validator classes */
class N4JSValidator extends AbstractMessageAdjustingN4JSValidator {
	@Inject
	private OperationCanceledManager operationCanceledManager;

	/**
	 * Override to improve error message in case of abnormal termination of validation.
	 */
	override MethodWrapperCancelable createMethodWrapper(AbstractDeclarativeValidator instanceToUse, Method method) {
		return new N4JSMethodWrapperCancelable(instanceToUse, method, operationCanceledManager);
	}

	public static class N4JSMethodWrapperCancelable extends MethodWrapperCancelable {
		private OperationCanceledManager operationCanceledManager;

		new(AbstractDeclarativeValidator instance, Method m, OperationCanceledManager operationCanceledManager) {
			super(instance, m)
			this.operationCanceledManager = operationCanceledManager;
		}

		override handleInvocationTargetException(Throwable targetException, State state) {
			// ignore GuardException, check is just not evaluated if guard is false
			// ignore NullPointerException, as not having to check for NPEs all the time is a convenience feature
			super.handleInvocationTargetException(targetException, state);
			if (targetException instanceof NullPointerException) {
				Exceptions.sneakyThrow(targetException)
			}
		}

		// catch exceptions and create better error message as org.eclipse.xtext.validation.CompositeEValidator.validate(EClass, EObject, DiagnosticChain, Map<Object, Object>)
		// note: cannot override validate method directly because it is final
		override void invoke(State state) {
			val valMethodName = this.method.name;
			val URI = state.currentObject.eResource.URI;

			operationCanceledManager.checkCanceled(getCancelIndicator(state));

			val dcCheckMethod = N4JSDataCollectors.createDataCollectorForCheckMethod(valMethodName);
			val mesVM = dcCheckMethod.getMeasurement(valMethodName + "_" + URI.toString);
			try {
				super.invoke(state);
			} catch (Exception e) {
				operationCanceledManager.propagateIfCancelException(e);
				// GH-2002: TEMPORARY DEBUG LOGGING
				// Only passing the exception to Logger#error(String,Throwable) does not emit the stack trace of the caught
				// exception in all logger configurations; we therefore include the stack trace in the main message:
				logger.error("exception while executing EValidator: " + e.message + "\n" + Throwables.getStackTraceAsString(e), e);
				new RuntimeException(e).printStackTrace();

				state.chain.add(
					new BasicDiagnostic(Diagnostic.ERROR, state.currentObject.toString(), 0, e.message, #[e, URI]));
			} finally {
				mesVM.close();
			}
		}
	}
}
