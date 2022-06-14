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
package org.eclipse.n4js.ts.types.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.Type;

/**
 * Implements the common map operation on the direct and indirect super types of a class, interface, etc.
 */
public class SuperTypesMapper<T> extends AbstractHierachyTraverser<List<T>> {

	/** The map function. May return <code>null</code> to denote that the given super type should be ignored. */
	protected final Function<Type, T> mapper;
	/** If <code>true</code>, mapping will stop after receiving the first non-<code>null</code> map result. */
	protected final boolean stopAtFirstNonNullResult;
	/** The results received so far. */
	protected final List<T> results = new ArrayList<>();

	/** Tells whether the given predicate returns <code>true</code> for at least one of the super types of 'type'. */
	public static boolean exists(ContainerType<?> type, Predicate<Type> predicate) {
		return findFirst(type, predicate) != null;
	}

	/** Returns the first super type of the given type for which the given predicate returns <code>true</code>. */
	public static Type findFirst(ContainerType<?> type, Predicate<Type> predicate) {
		List<Type> results = map(type, t -> predicate.test(t) ? t : null, true);
		return !results.isEmpty() ? results.get(0) : null;
	}

	/**
	 * Returns the first non-<code>null</code> value returned by the given map function when invoked with the super
	 * types of the given type.
	 */
	public static <T> T findFirst(ContainerType<?> type, Function<Type, T> mapper) {
		List<T> results = map(type, mapper, true);
		return !results.isEmpty() ? results.get(0) : null;
	}

	/** Like {@link #map(ContainerType, Function, boolean)}, always retrieving all results. */
	public static <T> List<T> map(ContainerType<?> type, Function<Type, T> mapper) {
		return map(type, mapper, false);
	}

	/**
	 * Implements the common map operation on the direct and indirect super types of a class, interface, etc., filtering
	 * out <code>null</code>-results and optionally stopping after receiving the first non-<code>null</code> result.
	 */
	public static <T> List<T> map(ContainerType<?> type, Function<Type, T> mapper, boolean stopAtFirstNonNullResult) {
		return new SuperTypesMapper<>(type, mapper, stopAtFirstNonNullResult).getResult();
	}

	/**
	 * @param type
	 *            the initial type that should be processed.
	 */
	public SuperTypesMapper(ContainerType<?> type, Function<Type, T> mapper, boolean stopAtFirstNonNullResult) {
		super(type);
		this.mapper = mapper;
		this.stopAtFirstNonNullResult = stopAtFirstNonNullResult;
	}

	/** See {@link #SuperTypesMapper(ContainerType, Function, boolean)}. */
	public SuperTypesMapper(PrimitiveType type, Function<Type, T> mapper, boolean stopAtFirstNonNullResult) {
		super(type);
		this.mapper = mapper;
		this.stopAtFirstNonNullResult = stopAtFirstNonNullResult;
	}

	@Override
	protected List<T> doGetResult() {
		return results;
	}

	@Override
	protected boolean process(ContainerType<?> currType) {
		return doProcess(currType);
	}

	@Override
	protected boolean process(PrimitiveType currType) {
		return doProcess(currType);
	}

	/** The actual processing. */
	protected boolean doProcess(Type currType) {
		T currResult = mapper.apply(currType);
		if (currResult != null) {
			results.add(currResult);
			if (stopAtFirstNonNullResult) {
				return true;
			}
		}
		return false;
	}
}
