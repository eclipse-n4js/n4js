/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 */
public class ReflectionUtils {

	/** @return anything you want */
	static public <T> T getFieldValue(Class<?> clazz, Object instance, String fieldName) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			Object value = field.get(instance);

			@SuppressWarnings("unchecked")
			T castedValue = (T) value;
			return castedValue;
		} catch (Exception e) {
			// ignore
			e.printStackTrace();
		}
		return null;
	}

	/** @return anything you want */
	static public <T> T getMethodReturn(Class<?> clazz, String methodName, Object instance) {
		return getMethodReturn(clazz, methodName, instance, new Class<?>[0], new Object[0]);
	}

	/** @return anything you want */
	static public <T> T getMethodReturn(Class<?> clazz, String methodName, Object instance, Class<?>[] paramTypes,
			Object[] paramValues) {

		try {
			Method method = clazz.getDeclaredMethod(methodName, paramTypes);
			method.setAccessible(true);
			Object value = method.invoke(instance, paramValues);

			@SuppressWarnings("unchecked")
			T castedValue = (T) value;
			return castedValue;
		} catch (Exception e) {
			// ignore
			e.printStackTrace();
		}
		return null;
	}
}
