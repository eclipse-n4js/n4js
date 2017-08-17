/**
 * Copyright (c) 2017 Marcus Mews.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Marcus Mews - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.factories;

import java.lang.reflect.Method;

/**
 *
 */
abstract public class Dispatcher {

	/**
	 *
	 */
	public static class NoDispatchMethodFoundException extends Exception {
		NoDispatchMethodFoundException(String msg) {
			super(msg);
		}
	}

	/**
	 *
	 */
	public static class InvokationException extends RuntimeException {
		InvokationException(Throwable t) {
			super(t);
		}
	}

	/**
	 * Use with care.
	 *
	 * @throws NoDispatchMethodFoundException
	 *             thrown iff no dispatch method could be found
	 */
	@SuppressWarnings("unchecked")
	protected static <R, T> R dispatch(String name, T cfe) throws NoDispatchMethodFoundException {
		R result = null;
		Class<?> cfeClass = cfe.getClass();
		Class<?> dispatcherClass = null;
		try {
			dispatcherClass = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
		} catch (ClassNotFoundException e1) {
			throw new IllegalStateException("Caller of dispatch method cannot be determined");
		}
		Method[] methods = dispatcherClass.getDeclaredMethods();

		for (Method m : methods) {
			Class<?>[] pTypes = m.getParameterTypes();
			boolean candidate = true;
			candidate &= m.getName().equals(name);
			candidate &= pTypes.length > 0 && pTypes[0].isAssignableFrom(cfeClass);
			if (candidate) {
				try {
					result = (R) m.invoke(null, cfe);
					return result;
				} catch (Exception e) {
					if (e.getCause() instanceof RuntimeException)
						throw (RuntimeException) e.getCause();

					throw new InvokationException(e);
				}
			}
		}

		String methodName = dispatcherClass.getSimpleName() + "." + name + "(" + cfe.getClass().getSimpleName() + ")";
		String msg = "No dispatch found for " + methodName;
		throw new NoDispatchMethodFoundException(msg);
	}

}
