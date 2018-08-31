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

import java.util.Map;
import java.util.function.Function;

import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.Maps;

/**
 * A map that wraps its values on {@link #put(Object, Object)}.
 */
class ValueWrappingMap<K, V> extends ForwardingMap<K, V> {

	private final Function<? super V, ? extends V> wrapper;
	private final Map<K, V> delegate;

	public ValueWrappingMap(Function<? super V, ? extends V> wrapper) {
		this(Maps.newHashMap(), wrapper);
	}

	public ValueWrappingMap(Map<K, V> delegate, Function<? super V, ? extends V> wrapper) {
		this.wrapper = Preconditions.checkNotNull(wrapper);
		this.delegate = Preconditions.checkNotNull(delegate);
	}

	@Override
	protected Map<K, V> delegate() {
		return delegate;
	}

	@Override
	public V put(K key, V value) {
		return super.put(key, wrapper.apply(value));
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		super.putAll(Maps.transformValues(map, (v) -> wrapper.apply(v)));
	}

}
