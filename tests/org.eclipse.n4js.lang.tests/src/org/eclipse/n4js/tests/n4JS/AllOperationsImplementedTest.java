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
package org.eclipse.n4js.tests.n4JS;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.flatten;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Creates instances of all concrete EClasses in the given EPackage and invokes all declared EOperations on them. Should
 * never see an UnsupportedOperationException.
 */
@RunWith(Parameterized.class)
public class AllOperationsImplementedTest {

	/**
	 * Returns test data.
	 */
	@Parameters(name = "{0}")
	public static Collection<Object[]> data() throws Exception {
		List<EClassifier> classifiers = new ArrayList<>();
		classifiers.addAll(TypeRefsPackage.eINSTANCE.getEClassifiers());
		classifiers.addAll(TypesPackage.eINSTANCE.getEClassifiers());
		classifiers.addAll(N4JSPackage.eINSTANCE.getEClassifiers());

		Iterable<EClass> allClasses = filter(classifiers, EClass.class);
		Iterable<EClass> allConcreteClasses = filter(allClasses, ec -> !ec.isAbstract() && !ec.isInterface());

		return toList(flatten(map(allConcreteClasses, c -> {
			Set<Method> allMethods = toSet(map(c.getEAllOperations(),
					op -> {
						Iterable<Class<?>> paramClasses = map(op.getEParameters(),
								it -> it.getEType().getInstanceClass());
						try {
							return c.getInstanceClass().getMethod(op.getName(),
									IterableExtensions.toList(paramClasses).toArray(new Class[0]));

						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}
					}));

			return map(allMethods, m -> new Object[] { c.getName() + "." + m.getName(), c, m });
		})));
	}

	protected final String name;
	private final EClass clazz;
	private final Method method;

	@FinalFieldsConstructor
	public AllOperationsImplementedTest(final String name, final EClass clazz, final Method method) {
		this.name = name;
		this.clazz = clazz;
		this.method = method;
	}

	@Test
	public void invoke() throws Throwable {
		EObject instance = EcoreUtil.create(clazz);
		Object[] array = toList(map(Arrays.asList(method.getParameters()), p -> toDefaultValue(p))).toArray();
		try {
			method.invoke(instance, array);
		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();
			if (cause instanceof NullPointerException
					|| cause instanceof IllegalArgumentException
					|| cause instanceof IllegalStateException) {
				// ignore
			} else if (cause instanceof UnsupportedOperationException) {
				// duplication on purpose to make it more obvious what we try to figure out here
				throw cause;
			} else {
				throw cause;
			}
		}
	}

	private Object toDefaultValue(Parameter param) {
		if (param.getType() == byte.class) {
			return (byte) 0;
		}
		if (param.getType() == short.class) {
			return (short) 0;
		}
		if (param.getType() == char.class) {
			return (char) 0;
		}
		if (param.getType() == int.class) {
			return (int) 0;
		}
		if (param.getType() == long.class) {
			return (long) 0;
		}
		if (param.getType() == float.class) {
			return (float) 0;
		}
		if (param.getType() == double.class) {
			return (double) 0;
		}
		if (param.getType() == boolean.class) {
			return (boolean) false;
		}
		return null;
	}

	// @Test
	// public void invoke() {
	// try {
	// final EObject instance = EcoreUtil.create(this.clazz);
	// final Function1<Parameter, Object> _function = (Parameter it) -> {
	// return this.toDefaultValue(it);
	// };
	// final Object[] array = ((Object[]) Conversions.unwrapArray(
	// ListExtensions.<Parameter, Object> map(
	// ((List<Parameter>) Conversions.doWrapArray(this.method.getParameters())), _function),
	// Object.class));
	// try {
	// this.method.invoke(instance, array);
	// } catch (final Throwable _t) {
	// if (_t instanceof InvocationTargetException) {
	// final InvocationTargetException e = (InvocationTargetException) _t;
	// Throwable cause = e.getCause();
	// if (cause instanceof NullPointerException
	// || cause instanceof IllegalArgumentException
	// || cause instanceof IllegalStateException) {
	// // ignore
	// }
	// if (!_matched) {
	// if (_cause instanceof UnsupportedOperationException) {
	// _matched = true;
	// throw e.getCause();
	// }
	// }
	// if (!_matched) {
	// throw e.getCause();
	// }
	// } else {
	// throw Exceptions.sneakyThrow(_t);
	// }
	// }
	// } catch (Throwable _e) {
	// throw Exceptions.sneakyThrow(_e);
	// }
	// }

}
