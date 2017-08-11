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
					e.printStackTrace();
				}
			}
		}
		throw new NoDispatchMethodFoundException("No dispatch found for class " + cfe.getClass().getSimpleName());
	}

}
