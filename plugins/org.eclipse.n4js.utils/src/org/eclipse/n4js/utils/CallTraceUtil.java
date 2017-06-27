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
package org.eclipse.n4js.utils;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * This class can be used as from static context (as ordinary utility) or from instance context (as helper). When called
 * inspects caller and provides gathered information.
 *
 * Intended use is to help with debugging.
 */
public final class CallTraceUtil {

	/** Magic number for caller_method + called_public_method + private_delegate_method */
	private static final int CALL_BASE_DEPTH = 3;

	@Inject
	private Injector injector;
	private String injectorData;

	/**
	 * Looks at call stack trace and returns info about the caller, in particular FQN of the class, method name and line
	 * number of the call. Additionally if instance of this class was injected, it will provide information about
	 * creating injector, in particular its hashcode and its bindings.
	 *
	 * @return information about direct caller
	 */
	public String getCallerInfo() {
		return getStaticAndInstanceInfo();

	}

	/**
	 * Same as {@link #getCallerInfo()} but instead returning result it is printed to std out.
	 */
	public void traceCall() {
		System.out.println(getStaticAndInstanceInfo());
	}

	/** Prints stacktrace up to the caller. */
	public void printFullCallTrace() {
		System.out.println(getStaticAndInstanceInfo() + getStackTraceFromOffset(CALL_BASE_DEPTH));
	}

	/**
	 * Same as {@link #getStaticCallerInfo()} but instead returning result it is printed to std out.
	 */
	public static void traceStaticCall() {
		System.out.println(getStaticCallerInfo());

	}

	/**
	 * Looks at call stack trace and returns info about the caller, in particular FQN of the class, method name and line
	 * number of the call. Unlike {@link #getCallerInfo()} this method will not provide instance information, e.g.
	 * injections.
	 *
	 * @return information about direct caller
	 */
	public static String getStaticCallerInfo() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getStackTrace(CALL_BASE_DEPTH));
		return sb.toString();
	}

	private String getStaticAndInstanceInfo() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getStackTrace(CALL_BASE_DEPTH));
		sb.append(", ");
		sb.append(getInjectionInfo());
		return sb.toString();
	}

	private static StringBuilder getStackTraceFromOffset(final int baseOffset) {
		final StringBuilder sb = new StringBuilder();
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		for (int stackOffset = baseOffset; stackOffset < stackTrace.length; stackOffset++) {
			sb.append("\n" + stackTrace[stackOffset]);
		}
		return sb;
	}

	private static StringBuilder getStackTrace(final int callDepth) {
		final StringBuilder sb = new StringBuilder();
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		final int stackOffset = callDepth + 1;// 0-based indexing of the array
		if (stackTrace.length >= callDepth) {
			sb.append(stackTrace[stackOffset].toString());
		} else {
			sb.append("call stack is suspiciously shallow:");
			for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
				sb.append(ste);
			}
		}
		return sb;
	}

	private StringBuilder getInjectionInfo() {
		final StringBuilder sb = new StringBuilder();

		if (injector != null) {
			sb.append("injected with:#").append(injector.hashCode()).append("_").append(getInjectorData());
		} else {
			sb.append("instance not injected");
		}
		return sb;
	}

	private String getInjectorData() {
		if (injectorData == null && injector != null) {
			injectorData = injector.getClass().getTypeName();
			String injectorAllData = injector.toString();
			// if injector contains a lot of bindings using toString
			// will slow down your logging significantly.
			// Surprisingly long lines slow Eclipse console rendering
			// more than lots of short lines
			// injectorData += "\n " + injectorAllData;
			if (injectorAllData.contains("N4JSRuntimeModule")) {
				injectorData += "  [N4JSRuntimeModule]";
			}
			if (injectorAllData.contains("N4JSXRuntimeModule")) {
				injectorData += "  [N4JSXRuntimeModule]";
			}
		}
		return injectorData;
	}
}
