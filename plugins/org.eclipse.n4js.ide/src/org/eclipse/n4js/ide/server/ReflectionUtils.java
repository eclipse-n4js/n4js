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
}
