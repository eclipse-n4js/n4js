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
package org.eclipse.n4js.tester.tests;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.inject.util.Modules.override;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * JUnit class runner to take care of Guice injection and module without Xtext dependencies.
 */
public class JUnitGuiceClassRunner extends BlockJUnit4ClassRunner {

	private final Injector injector;

	/**
	 * Creates a new class runner instance
	 *
	 * @param clazz
	 *            the test class that has to be run
	 * @throws InitializationError
	 *             if the runner cannot be initialized.
	 */
	public JUnitGuiceClassRunner(final Class<?> clazz) throws InitializationError {
		super(clazz);
		this.injector = createInjector(clazz);
	}

	private Injector createInjector(final Class<?> clazz) throws InitializationError {
		final InjectedModules annotation = clazz.getAnnotation(InjectedModules.class);
		if (null == annotation) {
			throw new InitializationError("Missing @" + InjectedModules.class.getSimpleName()
					+ " annotation for test class " + clazz.getName() + ".");

		}
		final Class<? extends Module>[] baseModules = annotation.baseModules();
		if (null == baseModules) {
			throw new InitializationError("Missing values for the @" + InjectedModules.class.getSimpleName()
					+ " annotation for the test class " + clazz.getName() + ".");
		}

		final Class<? extends Module>[] overrideModules = annotation.overrides();
		if (null == overrideModules) {
			throw new InitializationError("Missing values for the @" + InjectedModules.class.getSimpleName()
					+ " annotation for the test class " + clazz.getName() + ".");
		}

		final Iterable<? extends Module> bases = toModules(baseModules);
		final Iterable<? extends Module> overrides = toModules(overrideModules);

		boolean hasWithParentInjector = false;
		Injector parentInjector = null;
		for (final Method method : clazz.getMethods()) {
			if (method.isAnnotationPresent(WithParentInjector.class)) {

				if (!Injector.class.isAssignableFrom(method.getReturnType())) {
					throw new InitializationError("Methods annotated with @" + WithParentInjector.class.getSimpleName()
							+ " should have a " + Injector.class.getName() + "return type.");
				}

				if (!Modifier.isStatic(method.getModifiers())) {
					throw new InitializationError("Methods annotated with @" + WithParentInjector.class.getSimpleName()
							+ " should have a static modifier.");
				}

				if (0 < method.getParameterCount()) {
					throw new InitializationError("Methods annotated with @" + WithParentInjector.class.getSimpleName()
							+ " should have zero formal parameters.");
				}

				if (hasWithParentInjector) {
					throw new InitializationError("Only one method can be annotated with @"
							+ WithParentInjector.class.getSimpleName() + ".");
				}

				hasWithParentInjector = true;
				try {
					parentInjector = (Injector) method.invoke(null);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new InitializationError(e);
				}
			}

		}

		if (hasWithParentInjector && null == parentInjector) {
			throw new InitializationError("Failed to instantiate parent injector.");
		}

		final Module module = override(bases).with(overrides);
		return null == parentInjector ? Guice.createInjector(module) : parentInjector.createChildInjector(module);

	}

	private Iterable<? extends Module> toModules(final Class<? extends Module>[] baseModules) {
		return transform(newArrayList(baseModules), moduleClass -> {
			try {
				return moduleClass.getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(new InitializationError(e));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		});
	}

	@Override
	protected Object createTest() throws Exception {
		final Object test = super.createTest();
		injector.injectMembers(test);
		return test;
	}

}
