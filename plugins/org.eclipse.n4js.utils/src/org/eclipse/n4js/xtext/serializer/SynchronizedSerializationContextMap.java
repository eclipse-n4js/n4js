/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.serializer;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.analysis.SerializationContextMap;

/**
 * The base implementation of the SerializationContextMap populates an internal map on demand. When used in a concurrent
 * environment, this is prone to duplicate code execution. Unfortunately, there are side effects caused by the
 * computation logic, that may only happen once. Also maps in general can be corrupted when used in a concurrent way.
 * Thus we need to synchronize the {@link #get(ISerializationContext) access}.
 */
@SuppressWarnings("restriction")
class SynchronizedSerializationContextMap<T> extends SerializationContextMap<T> {

	private static final Field keys;
	static {
		try {
			Field field = SerializationContextMap.class.getDeclaredField("keys");
			field.setAccessible(true);
			keys = field;
		} catch (NoSuchFieldException | SecurityException e) {
			throw new IllegalStateException(e);
		}
	}

	private static Map<ISerializationContext, ?> getKeys(SerializationContextMap<?> map) {
		try {
			@SuppressWarnings("unchecked")
			Map<ISerializationContext, ?> result = (Map<ISerializationContext, ?>) keys.get(map);
			return result;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	private static void setKeys(SerializationContextMap<?> map, Map<ISerializationContext, ?> keys) {
		try {
			SynchronizedSerializationContextMap.keys.set(map, keys);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Create a synchronized copy from hte given serialization context map.
	 */
	public static <T> SynchronizedSerializationContextMap<T> from(SerializationContextMap<T> initial) {
		if (initial instanceof SynchronizedSerializationContextMap<?>) {
			return (SynchronizedSerializationContextMap<T>) initial;
		}
		SynchronizedSerializationContextMap<T> result = new SynchronizedSerializationContextMap<>(initial.values());
		Map<ISerializationContext, ?> original = getKeys(initial);
		setKeys(result, original);
		return result;
	}

	/**
	 * @param values
	 *            the values to init this map with.
	 */
	private SynchronizedSerializationContextMap(List<Entry<T>> values) {
		super(values);
	}

	@Override
	public synchronized T get(ISerializationContext ctx) {
		return super.get(ctx);
	}

	@Override
	public SerializationContextMap<T> sortedCopy() {
		throw new UnsupportedOperationException("Should only be called from the Xtext language generator");
	}

}
