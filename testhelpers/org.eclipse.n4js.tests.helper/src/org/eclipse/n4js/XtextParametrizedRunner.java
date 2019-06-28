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
package org.eclipse.n4js;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.internal.InjectorProviders;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import com.google.inject.Injector;
import com.google.inject.Provider;

/**
 *
 * we need to use {@link org.eclipse.xtext.testing.XtextRunner} to get dependency injection working we need to use
 * extend {@link org.junit.runners.Suite} the same way {@link org.junit.runners.Parameterized} does to get parameters
 * working following is naive merge between two.
 *
 * marking as final, if you need to extend it, you probably should just implement it from scratch.
 */
@SuppressWarnings("restriction")
public final class XtextParametrizedRunner extends Suite {
	/**
	 * Annotation for a method which provides parameters to be injected into the test class constructor by
	 * <code>Parameterized</code>
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface Parameters {
		/**
		 * <p>
		 * Optional pattern to derive the test's name from the parameters. Use numbers in braces to refer to the
		 * parameters or the additional data as follows:
		 * </p>
		 *
		 * <pre>
		 * {index} - the current parameter index
		 * {0} - the first parameter value
		 * {1} - the second parameter value
		 * etc...
		 * </pre>
		 * <p>
		 * Default value is "{index}" for compatibility with previous JUnit versions.
		 * </p>
		 *
		 * @return {@link MessageFormat} pattern string, except the index placeholder.
		 * @see MessageFormat
		 */
		String name() default "{index}";
	}

	/**
	 * Annotation for a method which provides an instance of Provider that should be used to compute the parameters.
	 * <code>Parameterized</code>
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface ParametersProvider {
		/**
		 * <p>
		 * Optional pattern to derive the test's name from the parameters provider. Use numbers in braces to refer to
		 * the parameters or the additional data as follows:
		 * </p>
		 *
		 * <pre>
		 * {index} - the current parameter index
		 * {0} - the first parameter value
		 * {1} - the second parameter value
		 * etc...
		 * </pre>
		 * <p>
		 * Default value is "{index}" for compatibility with previous JUnit versions.
		 * </p>
		 *
		 * @return {@link MessageFormat} pattern string, except the index placeholder.
		 * @see MessageFormat
		 */
		String name() default "{index}";
	}

	/**
	 * Annotation for fields of the test class which will be initialized by the method annotated by
	 * <code>Parameters</code><br/>
	 * By using directly this annotation, the test class constructor isn't needed.<br/>
	 * Index range must start at 0. Default value is 0.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface Parameter {
		/**
		 * Method that returns the index of the parameter in the array returned by the method annotated by
		 * <code>Parameters</code>.<br/>
		 * Index range must start at 0. Default value is 0.
		 *
		 * @return the index of the parameter.
		 */
		int value() default 0;
	}

	private final class ParametrizedXtextTestClassRunner extends XtextRunner {
		private final Object[] fParameters; // org.junit.runners.Parameterized$TestClassRunnerForParameters

		private final String fName; // org.junit.runners.Parameterized$TestClassRunnerForParameters

		ParametrizedXtextTestClassRunner(Class<?> type, Object[] parameters, String name) throws InitializationError {
			super(type);
			fParameters = parameters;
			fName = name;
		}

		/**
		 * Implements behavior from: org.junit.runners.Parameterized$TestClassRunnerForParameters
		 * org.eclipse.xtext.testing.XtextRunner
		 */
		@Override
		public Object createTest() throws Exception {
			Object object;
			// Functionality of
			// org.junit.runners.Parameterized$TestClassRunnerForParameters
			if (fieldsAreAnnotated()) {
				object = createTestUsingFieldInjection();
			} else {
				object = createTestUsingConstructorInjection();
			}

			// Functionality of org.eclipse.xtext.testing.XtextRunner
			IInjectorProvider injectorProvider = getOrCreateInjectorProvider();
			if (injectorProvider != null) {
				Injector injector = injectorProvider.getInjector();
				if (injector != null)
					injector.injectMembers(object);
			}
			return object;
		}

		/**
		 * Implements behavior from: org.eclipse.xtext.testing.XtextRunner
		 */
		private Object createTestUsingConstructorInjection() throws Exception {
			return getTestClass().getOnlyConstructor().newInstance(fParameters);
		}

		/**
		 * Implements behavior from: org.junit.runners.Parameterized$TestClassRunnerForParameters
		 */
		private Object createTestUsingFieldInjection() throws Exception {
			List<FrameworkField> annotatedFieldsByParameter = getAnnotatedFieldsByParameter();
			if (annotatedFieldsByParameter.size() != fParameters.length) {
				throw new Exception("Wrong number of parameters and @Parameter fields."
						+ " @Parameter fields counted: " + annotatedFieldsByParameter.size()
						+ ", available parameters: " + fParameters.length + ".");
			}
			Object testClassInstance = getTestClass().getJavaClass().getConstructor().newInstance();
			for (FrameworkField each : annotatedFieldsByParameter) {
				Field field = each.getField();
				Parameter annotation = field.getAnnotation(Parameter.class);
				int index = annotation.value();
				try {
					field.set(testClassInstance, fParameters[index]);
				} catch (IllegalArgumentException iare) {
					throw new Exception(getTestClass().getName() + ": Trying to set " + field.getName()
							+ " with the value " + fParameters[index] + " that is not the right type ("
							+ fParameters[index].getClass().getSimpleName() + " instead of "
							+ field.getType().getSimpleName() + ").", iare);
				}
			}
			return testClassInstance;
		}

		/**
		 * Implements behavior from: org.junit.runners.Parameterized$TestClassRunnerForParameters
		 */
		@Override
		protected String getName() {
			return fName;
		}

		/**
		 * Implements behavior from: org.junit.runners.Parameterized$TestClassRunnerForParameters
		 */
		@Override
		protected String testName(FrameworkMethod method) {
			return method.getName() + getName();
		}

		/**
		 * Implements behavior from: org.junit.runners.Parameterized$TestClassRunnerForParameters
		 */
		@Override
		protected void validateConstructor(List<Throwable> errors) {
			validateOnlyOneConstructor(errors);
			if (fieldsAreAnnotated()) {
				validateZeroArgConstructor(errors);
			}
		}

		/**
		 * Implements behavior from: org.junit.runners.Parameterized$TestClassRunnerForParameters
		 */
		@Override
		protected void validateFields(List<Throwable> errors) {
			super.validateFields(errors);
			if (fieldsAreAnnotated()) {
				List<FrameworkField> annotatedFieldsByParameter = getAnnotatedFieldsByParameter();
				int[] usedIndices = new int[annotatedFieldsByParameter.size()];
				for (FrameworkField each : annotatedFieldsByParameter) {
					int index = each.getField().getAnnotation(Parameter.class).value();
					if (index < 0 || index > annotatedFieldsByParameter.size() - 1) {
						errors.add(new Exception("Invalid @Parameter value: " + index + ". @Parameter fields counted: "
								+ annotatedFieldsByParameter.size() + ". Please use an index between 0 and "
								+ (annotatedFieldsByParameter.size() - 1) + "."));
					} else {
						usedIndices[index]++;
					}
				}
				for (int index = 0; index < usedIndices.length; index++) {
					int numberOfUse = usedIndices[index];
					if (numberOfUse == 0) {
						errors.add(new Exception("@Parameter(" + index + ") is never used."));
					} else if (numberOfUse > 1) {
						errors.add(new Exception("@Parameter(" + index + ") is used more than once (" + numberOfUse
								+ ")."));
					}
				}
			}
		}

		/**
		 * Implements behavior from: org.junit.runners.Parameterized$TestClassRunnerForParameters
		 */
		@Override
		protected Statement classBlock(RunNotifier notifier) {
			return childrenInvoker(notifier);
		}

		/**
		 * Implements behavior from: org.junit.runners.Parameterized$TestClassRunnerForParameters
		 */
		@Override
		protected Annotation[] getRunnerAnnotations() {
			return new Annotation[0];
		}

	}

	private static final List<Runner> NO_RUNNERS = Collections.<Runner> emptyList();

	private final ArrayList<Runner> runners = new ArrayList<>(); // org.junit.runners.Parameterized

	/**
	 * Implements behavior from: org.junit.runners.Parameterized
	 */
	public XtextParametrizedRunner(Class<?> klass) throws Throwable {
		super(klass, NO_RUNNERS);
		FrameworkMethod parametersMethod = getParametersMethod();
		if (parametersMethod != null) {
			Parameters parameters = parametersMethod.getAnnotation(Parameters.class);
			createRunnersForParameters(allParameters(), parameters.name());
		} else {
			FrameworkMethod parametersProviderMethod = getParametersProviderMethod();
			ParametersProvider parameters = parametersProviderMethod.getAnnotation(ParametersProvider.class);
			createRunnersForParameters(allParameters(), parameters.name());
		}
	}

	/**
	 * Implements behavior from: org.junit.runners.Parameterized$TestClassRunnerForParameters
	 */
	@Override
	protected List<Runner> getChildren() {
		return runners;
	}

	/**
	 * Implements behavior from: org.junit.runners.Parameterized
	 */
	@SuppressWarnings("unchecked")
	private Iterable<Object> allParameters() throws Throwable {
		FrameworkMethod parametersMethod = getParametersMethod();
		if (parametersMethod != null) {
			Object parameters = parametersMethod.invokeExplosively(null);
			if (parameters instanceof Iterable) {
				return (Iterable<Object>) parameters;
			} else {
				throw parametersMethodReturnedWrongType(parametersMethod);
			}
		}
		FrameworkMethod parametersProviderMethod = getParametersProviderMethod();
		if (parametersProviderMethod != null) {
			Object provider = parametersProviderMethod.invokeExplosively(null);
			if (provider instanceof Provider<?>) {
				IInjectorProvider injectorProvider = InjectorProviders.getOrCreateInjectorProvider(getTestClass());
				if (injectorProvider != null) {
					Injector injector = injectorProvider.getInjector();
					if (injector != null)
						injector.injectMembers(provider);
				}
				Object parameters = ((Provider<?>) provider).get();
				if (parameters instanceof Iterable) {
					return (Iterable<Object>) parameters;
				}
			}
			throw parametersProviderMethodReturnedWrongType(parametersProviderMethod);
		}
		throw new Exception("No public static parameters method on class " + getTestClass().getName());
	}

	/**
	 * Implements behavior from: org.junit.runners.Parameterized
	 */
	private FrameworkMethod getParametersMethod() throws Exception {
		return getAnnotatedPublicStaticMethod(Parameters.class);
	}

	/**
	 * Implements support for ParametersProvider
	 */
	private FrameworkMethod getParametersProviderMethod() throws Exception {
		return getAnnotatedPublicStaticMethod(ParametersProvider.class);
	}

	private FrameworkMethod getAnnotatedPublicStaticMethod(Class<? extends Annotation> anno) {
		List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(anno);
		for (FrameworkMethod each : methods) {
			if (each.isStatic() && each.isPublic()) {
				return each;
			}
		}
		return null;
	}

	/**
	 * Implements behavior from: org.junit.runners.Parameterized
	 */
	private void createRunnersForParameters(Iterable<Object> allParameters, String namePattern)
			throws InitializationError, Exception {
		try {
			int i = 0;
			for (Object parametersOfSingleTest : allParameters) {
				if (parametersOfSingleTest instanceof Object[]) {
					Object[] parameters = (Object[]) parametersOfSingleTest;
					String name = nameFor(namePattern, i, parameters);
					ParametrizedXtextTestClassRunner runner = new ParametrizedXtextTestClassRunner(getTestClass()
							.getJavaClass(), parameters, name);
					runners.add(runner);
				} else {
					String name = parametersOfSingleTest.toString();
					ParametrizedXtextTestClassRunner runner = new ParametrizedXtextTestClassRunner(getTestClass()
							.getJavaClass(), new Object[] { parametersOfSingleTest }, name);
					runners.add(runner);
				}
				++i;
			}
		} catch (ClassCastException e) {
			throw unexpectedArrayElement();
		}
	}

	/**
	 * Implements behavior from: org.junit.runners.Parameterized
	 */
	private String nameFor(String namePattern, int index, Object[] parameters) {
		String finalPattern = namePattern.replaceAll("\\{index\\}", Integer.toString(index));
		String name = MessageFormat.format(finalPattern, parameters);
		return "[" + name + "]";
	}

	/**
	 * Implements behavior from: org.junit.runners.Parameterized
	 */
	private Exception parametersMethodReturnedWrongType(FrameworkMethod parametersMethod) throws Exception {
		String className = getTestClass().getName();
		String message = MessageFormat.format("{0}.{1}() must return an Iterable of arrays.", className,
				parametersMethod.getName());
		return new Exception(message);
	}

	private Exception parametersProviderMethodReturnedWrongType(FrameworkMethod parametersMethod) throws Exception {
		String className = getTestClass().getName();
		String message = MessageFormat.format("{0}.{1}() must return an Provider for an Iterable of arrays.",
				className,
				parametersMethod.getName());
		return new Exception(message);
	}

	private Exception unexpectedArrayElement() throws Exception {
		FrameworkMethod method = getParametersMethod();
		if (method != null) {
			return parametersMethodReturnedWrongType(method);
		}
		return parametersProviderMethodReturnedWrongType(getParametersProviderMethod());
	}

	/**
	 * Implements behavior from: org.junit.runners.Parameterized
	 */
	List<FrameworkField> getAnnotatedFieldsByParameter() {
		return getTestClass().getAnnotatedFields(Parameter.class);
	}

	/**
	 * Implements behavior from: org.junit.runners.Parameterized
	 */
	boolean fieldsAreAnnotated() {
		return !getAnnotatedFieldsByParameter().isEmpty();
	}
}
