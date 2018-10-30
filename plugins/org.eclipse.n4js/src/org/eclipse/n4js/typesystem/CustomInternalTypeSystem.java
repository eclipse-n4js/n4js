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
package org.eclipse.n4js.typesystem;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.N4JSRuntimeModule;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.postprocessing.ASTProcessor;
import org.eclipse.n4js.postprocessing.TypeProcessor;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.typesystem.AbstractJudgment.JResult;
import org.eclipse.n4js.xsemantics.InternalTypeSystem;
import org.eclipse.xsemantics.runtime.ErrorInformation;
import org.eclipse.xsemantics.runtime.Result;
import org.eclipse.xsemantics.runtime.RuleApplicationTrace;
import org.eclipse.xsemantics.runtime.RuleEnvironment;
import org.eclipse.xsemantics.runtime.RuleFailedException;
import org.eclipse.xtext.service.OperationCanceledManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * This class contains some customizations of the Xsemantics-generated types system {@link InternalTypeSystem}. It is
 * bound to {@code InternalTypeSystem} via {@link N4JSRuntimeModule#bindInternalTypeSystem()}. <b>Most client code
 * should neither use {@code InternalTypeSystem} nor this class directly</b>, but instead use the facade class
 * {@link N4JSTypeSystem}.
 */
@Singleton
public class CustomInternalTypeSystem extends InternalTypeSystem {

	@Inject
	private TypeProcessor typeProcessor;
	@Inject
	private OperationCanceledManager operationCanceledManager;

	@SuppressWarnings("javadoc")
	public static class RuleFailedExceptionWithoutStacktrace extends RuleFailedException {
		public RuleFailedExceptionWithoutStacktrace() {
			super();
		}

		public RuleFailedExceptionWithoutStacktrace(String arg0) {
			super(arg0);
		}

		public RuleFailedExceptionWithoutStacktrace(String failed, String issue2, Throwable t) {
			super(failed, issue2, t);
		}

		public RuleFailedExceptionWithoutStacktrace(String arg0, RuleFailedException previous) {
			super(arg0, previous);
		}

		@SuppressWarnings("sync-override")
		@Override
		public Throwable fillInStackTrace() {
			return this;
		}

		@Override
		public StackTraceElement[] getStackTrace() {
			return new StackTraceElement[0];
		}
	}

	/** Overridden to add null-safety. */
	@Override
	public void checkAssignableTo(Object result, Class<?> destinationClass) {
		if (result == null || !isResultAssignableTo(result, destinationClass)) {
			throw newRuleFailedException(stringRep(result)
					+ " cannot be assigned to " + stringRep(destinationClass));
		}
	}

	/**
	 * Overridden to avoid {@link OperationCanceledException}s being suppressed by Xsemantics.
	 * <p>
	 * Note that this method is also invoked in case of or-blocks. For example, the following Xsemantics source
	 *
	 * <pre>
	 * {
	 * 	doSomethingThatThrowsCancelException();
	 * } or {
	 * 	println("WILL NOT GET HERE!!!")
	 * }
	 * </pre>
	 *
	 * compiles to the following Java code:
	 *
	 * <pre>
	 * RuleFailedException previousFailure = null;
	 * try {
	 * 	doSomethingThatThrowsCancelException();
	 * } catch (Exception e) {
	 * 	previousFailure = extractRuleFailedException(e);
	 * 	InputOutput.<String> println("WILL NOT GET HERE!!!");
	 * }
	 * </pre>
	 *
	 * Without the call to <code>#propagateIfCancelException()</code> in this override, the cancel exception would be
	 * suppressed by Xsemantics and the <code>println()</code> would be executed.
	 */
	@Override
	public RuleFailedException extractRuleFailedException(Exception e) {
		operationCanceledManager.propagateIfCancelException(e);
		return super.extractRuleFailedException(e);
	}

	/** Overridden to avoid {@link OperationCanceledException}s being suppressed by Xsemantics. */
	@Override
	public void throwRuleFailedException(String message, String issue, Throwable t,
			ErrorInformation... errorInformations) {
		operationCanceledManager.propagateIfCancelException(t);
		super.throwRuleFailedException(message, issue, t, errorInformations);
	}

	@Override
	protected RuleFailedException createRuleFailedException(String message, String issue, Throwable t) {
		return new RuleFailedExceptionWithoutStacktrace(message, issue, t);
	}

	/** Overridden to avoid excessive string conversions. */
	@Override
	protected String stringRepForEnv(RuleEnvironment ruleEnvironment) {
		return "[...]";
	}

	/**
	 * <b>All invocations of 'type' judgment in Xsemantics - from outside or from within Xsemantics - are now delegated
	 * to {@link TypeProcessor#getType(RuleEnvironment, RuleApplicationTrace, TypableElement)}.</b>
	 * <p>
	 * {@link TypeProcessor} will simply read the type from the cache (if containing resource is fully processed) or
	 * initiate the post-processing of the entire resource. Actual use of the 'type' judgment will only be done by
	 * {@link TypeProcessor} during post-processing of a resource via method
	 * {@link #use_type_judgment_from_PostProcessors(RuleEnvironment, RuleApplicationTrace, TypableElement)}. Once
	 * post-processing of an {@link N4JSResource} has finished, the 'type' judgment will never be used again for this
	 * resource!
	 */
	@Override
	protected Result<TypeRef> typeInternal(RuleEnvironment _environment_, RuleApplicationTrace _trace_,
			TypableElement expression) {
		return typeProcessor.getType(_environment_, _trace_, expression);
	}

	/**
	 * <b>!!! This method must never be invoked, except from {@code AbstractProcessor#askXsemanticsForType()} !!!</b>
	 * <p>
	 * This method may be called to actually use the 'type' judgment in Xsemantics. It is used by {@link ASTProcessor}
	 * while traversing the entire AST (during post-processing) to obtain the type of nodes that have not yet been
	 * processed.
	 */
	public Result<TypeRef> use_type_judgment_from_PostProcessors(RuleEnvironment _environment_,
			RuleApplicationTrace _trace_, TypableElement expression) {
		// return super.typeInternal(_environment_, _trace_, expression);
		return TEMP_convert(ts_internal_NEW.type_actuallyUseIt(_environment_, expression));
	}

	// *******************************************************************************************

	@Inject
	private InternalTypeSystemNEW ts_internal_NEW;

	@Override
	protected Result<TypeRef> expectedTypeInInternal(RuleEnvironment _environment_, RuleApplicationTrace _trace_,
			EObject container, Expression expression) {
		// return super.expectedTypeInInternal(_environment_, _trace_, container, expression);
		return TEMP_convert(ts_internal_NEW.expectedType(_environment_, container, expression));
	}

	@Override
	protected Result<Boolean> subtypeInternal(RuleEnvironment _environment_, RuleApplicationTrace _trace_,
			TypeArgument left, TypeArgument right) {
		return TEMP_convert(ts_internal_NEW.subtype(_environment_, left, right));
	}

	@Override
	public Boolean subtypeSucceeded(RuleEnvironment _environment_, RuleApplicationTrace _trace_, TypeArgument left,
			TypeArgument right) {
		// return super.subtypeSucceeded(_environment_, _trace_, left, right);
		Result<Boolean> result = subtypeInternal(_environment_, _trace_, left, right);
		return !result.failed() && result.getValue();
	}

	@Override
	protected Result<Boolean> supertypeInternal(RuleEnvironment _environment_, RuleApplicationTrace _trace_,
			TypeArgument left, TypeArgument right) {
		// return super.supertypeInternal(_environment_, _trace_, left, right);
		return TEMP_convert(ts_internal_NEW.supertype(_environment_, left, right));
	}

	@Override
	public Boolean supertypeSucceeded(RuleEnvironment _environment_, RuleApplicationTrace _trace_, TypeArgument left,
			TypeArgument right) {
		// return super.supertypeSucceeded(_environment_, _trace_, left, right);
		Result<Boolean> result = supertypeInternal(_environment_, _trace_, left, right);
		return !result.failed() && result.getValue();
	}

	@Override
	protected Result<Boolean> equaltypeInternal(RuleEnvironment _environment_, RuleApplicationTrace _trace_,
			TypeArgument left, TypeArgument right) {
		// return super.equaltypeInternal(_environment_, _trace_, left, right);
		return TEMP_convert(ts_internal_NEW.equaltype(_environment_, left, right));
	}

	@Override
	public Boolean equaltypeSucceeded(RuleEnvironment _environment_, RuleApplicationTrace _trace_, TypeArgument left,
			TypeArgument right) {
		// return super.equaltypeSucceeded(_environment_, _trace_, left, right);
		Result<Boolean> result = equaltypeInternal(_environment_, _trace_, left, right);
		return !result.failed() && result.getValue();
	}

	@Override
	protected Result<TypeRef> upperBoundInternal(RuleEnvironment _environment_, RuleApplicationTrace _trace_,
			TypeArgument typeArgument) {
		// return super.upperBoundInternal(_environment_, _trace_, typeArgument);
		return new Result<>(ts_internal_NEW.upperBound(_environment_, typeArgument));
	}

	@Override
	protected Result<TypeRef> lowerBoundInternal(RuleEnvironment _environment_, RuleApplicationTrace _trace_,
			TypeArgument typeArgument) {
		// return super.lowerBoundInternal(_environment_, _trace_, typeArgument);
		return new Result<>(ts_internal_NEW.lowerBound(_environment_, typeArgument));
	}

	@Override
	protected Result<TypeArgument> substTypeVariablesInternal(RuleEnvironment _environment_,
			RuleApplicationTrace _trace_, TypeArgument typeArg) {
		// return super.substTypeVariablesInternal(_environment_, _trace_, typeArg);
		return TEMP_convert(ts_internal_NEW.substTypeVariables(_environment_, typeArg));
	}

	@Override
	protected Result<TypeRef> thisTypeRefInternal(RuleEnvironment _environment_, RuleApplicationTrace _trace_,
			EObject location) {
		// return super.thisTypeRefInternal(_environment_, _trace_, location);
		return TEMP_convert(ts_internal_NEW.thisTypeRef(_environment_, location));
	}

	private <T> Result<T> TEMP_convert(JResult<T> result) {
		return result.isSuccess()
				? new Result<>(result.getValue())
				: new Result<>(failureToException(result));
	}

	private RuleFailedException failureToException(JResult<?> failure) {
		if (failure == null) {
			return null;
		}
		RuleFailedException result = new RuleFailedException(
				failure.getFailureMessage(),
				"issue2",
				failureToException(failure.getCause()));
		if (failure.isCustom()) {
			result.addErrorInformation(new ErrorInformation(null, null, TypeSystemErrorExtensions.PRIORITY_ERROR));
		}
		return result;
	}
}
