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

import org.eclipse.n4js.N4JSRuntimeModule;
import org.eclipse.n4js.postprocessing.ASTProcessor;
import org.eclipse.n4js.postprocessing.TypeProcessor;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.xsemantics.InternalTypeSystem;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import it.xsemantics.runtime.Result;
import it.xsemantics.runtime.RuleApplicationTrace;
import it.xsemantics.runtime.RuleEnvironment;
import it.xsemantics.runtime.RuleFailedException;

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
	 * <b>!!! This method must never be invoked, except from {@link ASTProcessor} and related classes !!!</b>
	 * <p>
	 * This method may be called to actually use the 'type' judgment in Xsemantics. It is used by {@link ASTProcessor}
	 * while traversing the entire AST (during post-processing) to obtain the type of nodes that have not yet been
	 * processed.
	 */
	public Result<TypeRef> use_type_judgment_from_PostProcessors(RuleEnvironment _environment_,
			RuleApplicationTrace _trace_, TypableElement expression) {
		return super.typeInternal(_environment_, _trace_, expression);
	}
}
