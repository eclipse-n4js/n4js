/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ts.scoping.builtin;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

public abstract class ReentrantEnumerableScope extends EnumerableScope {

	// FIXME are these guards even required after setting 'elements' early???
	protected static final Set<String> guards = new HashSet<>();

	private static class ReentrantExecutionEnvironmentDescriptor extends ExecutionEnvironmentDescriptor {

		private final String guardKey;
		private final ExecutionEnvironmentDescriptor delegate;

		public ReentrantExecutionEnvironmentDescriptor(String guardKey, ExecutionEnvironmentDescriptor delegate) {
			super(null);
			this.guardKey = guardKey;
			this.delegate = delegate;
		}

		@Override
		protected void processResources(String[] fileNames, Consumer<? super Resource> consumer) {
			synchronized (guards) {
				if (guards.contains(guardKey)) {
					return;
				}
				guards.add(guardKey);
			}
			try {
				delegate.processResources(fileNames, consumer);
			} finally {
				synchronized (guards) {
					guards.remove(guardKey);
				}
			}
		}
	}

	protected ReentrantEnumerableScope(Class<? extends ReentrantEnumerableScope> subtype,
			ExecutionEnvironmentDescriptor descriptor) {
		super(new ReentrantExecutionEnvironmentDescriptor(subtype.getName(), descriptor));
		if (subtype != this.getClass()) {
			throw new IllegalArgumentException("incorrect subclass provided!!!");
		}
	}

	@Override
	protected void buildMap(Resource resource, Map<QualifiedName, IEObjectDescription> result) {
		try {
			Field field = EnumerableScope.class.getDeclaredField("elements");
			field.setAccessible(true);
			field.set(this, result);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new WrappedException(e);
		}
		doBuildMap(resource, result);
	}

	protected abstract void doBuildMap(Resource resource, Map<QualifiedName, IEObjectDescription> result);
}
