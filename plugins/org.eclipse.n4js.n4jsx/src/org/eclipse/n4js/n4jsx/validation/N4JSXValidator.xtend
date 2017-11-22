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
package org.eclipse.n4js.n4jsx.validation

import com.google.inject.Inject
import java.lang.reflect.Method
import java.util.List
import org.eclipse.emf.common.util.BasicDiagnostic
import org.eclipse.emf.common.util.Diagnostic
import org.eclipse.emf.ecore.EPackage
import org.eclipse.n4js.utils.Log
import org.eclipse.n4js.validation.AbstractMessageAdjustingN4JSValidator.MethodWrapperCancelable
import org.eclipse.n4js.validation.validators.IDEBUGValidator
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
import org.eclipse.n4js.validation.validators.N4JSFunctionValidator
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
import org.eclipse.n4js.validation.validators.UnsupportedFeatureValidator
import org.eclipse.n4js.xsemantics.validation.InternalTypeSystemValidator
import org.eclipse.xtext.service.OperationCanceledManager
import org.eclipse.xtext.validation.AbstractDeclarativeValidator
import org.eclipse.xtext.validation.AbstractDeclarativeValidator.State
import org.eclipse.xtext.validation.ComposedChecks

//import org.eclipse.xtext.validation.Check
/**
 * This class contains custom validation rules.
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
@ComposedChecks(validators=#[
	IDEBUGValidator,
	// N4JSStrictValidator,
	N4JSNameValidator,
	N4JSClassifierValidator,
	N4JSMemberRedefinitionValidator,
	N4JSClassValidator,
	N4JSInterfaceValidator,
	N4JSFunctionValidator,
	N4JSXImportValidator,
	N4JSTypeValidator,
	N4JSExpressionValidator,
	N4JSMemberValidator,
	N4JSExternalValidator,
	N4JSAccessModifierValidator,
	N4JSSuperValidator,
	N4JSLambdaValidator,
	N4JSVariableValidator,
	N4JSDeclaredNameValidator,
	N4JSStatementValidator,
	N4JSAnnotationValidator,
	N4JSEnumValidator,
	N4JSSyntaxValidator,
	N4JSDependencyInjectionValidator,
	N4JSInjectorCallsitesValidator,
	N4JSModuleValidator,
	N4JSDestructureValidator,
	UnsupportedFeatureValidator,
	N4JSXReactBindingValidator
])
@Log
class N4JSXValidator extends InternalTypeSystemValidator {

	@Inject
	private OperationCanceledManager operationCanceledManager;

	// validations are defined in composed validator classes
	/**
	 * Override to improve error message in case of abnormal termination of validation.
	 */
	override MethodWrapperCancelable createMethodWrapper(AbstractDeclarativeValidator instanceToUse, Method method) {
		return new MethodWrapperCancelable(instanceToUse, method) {

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
				operationCanceledManager.checkCanceled(getCancelIndicator(state));
				try {
					super.invoke(state);
				} catch (Exception e) {
					operationCanceledManager.propagateIfCancelException(e);
					logger.error("Error executing EValidator", e);
					state.chain.add(
						new BasicDiagnostic(Diagnostic.ERROR, state.currentObject.toString(), 0, e.message, #[e]));
				}
			}
		};
	}

	override protected List<EPackage> getEPackages() {
		val List<EPackage> result = super.EPackages
		result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/n4js/n4jsx/N4JSX"));
		return result;
	}
}
