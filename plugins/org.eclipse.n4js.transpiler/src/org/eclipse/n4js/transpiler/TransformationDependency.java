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
package org.eclipse.n4js.transpiler;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.n4js.generator.common.GeneratorOption;
import org.eclipse.n4js.utils.collections.Arrays2;

import com.google.common.base.Joiner;

/**
 * Contains some annotation classes for declaring dependencies between {@link Transformation}s.
 */
public abstract class TransformationDependency {

	/**
	 * Declares a transformation to be optional and activate if and only if at least one of the given
	 * {@link GeneratorOption generator options} was specified when transpilation was started.
	 * <p>
	 * Notes:
	 * <ol>
	 * <li>removing inactive optional transformations from a set of transformations is implemented in
	 * {@link TransformationDependency#filterByTranspilerOptions(Transformation[], GeneratorOption[])
	 * #filterByTranspilerOptions()},
	 * <li>validation of all other transformation dependencies happens <em>after</em> removal of inactive optional
	 * transformations, see step 1 in {@link AbstractTranspiler#transform(TranspilerState)},
	 * <li>at time of writing, generator options are not made available for the end user and can only be set internally
	 * for testing purposes. The main transpilation in production will always run with the options defined in
	 * {@link GeneratorOption#DEFAULT_OPTIONS}.
	 * </ol>
	 */
	@Target({ TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Optional {
		/**
		 * The {@link GeneratorOption generator options} of which at least one is required for this transformation to
		 * become active during transpilation.
		 */
		public GeneratorOption[] value();
	}

	/**
	 * Declares that a {@link Transformation} {@code T} requires one or more other transformations {@code T1...Tn} to be
	 * executed, either before or after {@code T}.
	 */
	@Target({ TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Requires {
		/** Lists the required other transformations. */
		public Class<? extends Transformation>[] value();
	}

	/**
	 * Declares that a {@link Transformation} {@code T} requires one or more other transformations {@code T1...Tn} to be
	 * executed <b>before</b> {@code T}. This requirement is not met by executing {@code T1...Tn} after {@code T}.
	 */
	@Target({ TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface RequiresBefore {
		/** Lists the required other transformations. */
		public Class<? extends Transformation>[] value();
	}

	/**
	 * Declares that a {@link Transformation} {@code T} requires one or more other transformations {@code T1...Tn} to be
	 * executed <b>after</b> {@code T}. This requirement is not met by executing {@code T1...Tn} before {@code T}.
	 */
	@Target({ TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface RequiresAfter {
		/** Lists the required other transformations. */
		public Class<? extends Transformation>[] value();
	}

	/**
	 * Declares that a {@link Transformation} {@code T} and one or more other transformations {@code T1...Tn} are
	 * mutually exclusive, i.e.
	 *
	 * <pre>
	 * ! (T && (T1 || T2 || ... Tn))
	 * </pre>
	 */
	@Target({ TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Excludes {
		/** Lists the excluded other transformations. */
		public Class<? extends Transformation>[] value();
	}

	/**
	 * Declares that a {@link Transformation} {@code T} must not be executed <b>after</b> one or more other
	 * transformations {@code T1...Tn}.
	 */
	@Target({ TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface ExcludesBefore {
		/** Lists the excluded other transformations. */
		public Class<? extends Transformation>[] value();
	}

	/**
	 * Declares that a {@link Transformation} {@code T} must not be executed <b>before</b> one or more other
	 * transformations {@code T1...Tn}.
	 */
	@Target({ TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface ExcludesAfter {
		/** Lists the excluded other transformations. */
		public Class<? extends Transformation>[] value();
	}

	/**
	 * Tells if the given transformation is active, based on the given {@link GeneratorOption generator options}.
	 */
	public static final boolean isActiveIn(Transformation transformation, GeneratorOption[] activeOptions) {
		final Optional ann = transformation.getClass().getAnnotation(Optional.class);
		if (ann != null) {
			// at least one of the options given in the annotation must be active, based on the given 'activeOptions'
			final GeneratorOption[] annOptions = ann.value();
			for (GeneratorOption annOption : annOptions) {
				if (GeneratorOption.isActiveIn(annOption, activeOptions)) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	/**
	 * Returns a new array of transformations with all given transformations except those that are {@link Optional} and
	 * are {@link #isActiveIn(Transformation, GeneratorOption[]) inactive}, based on the given {@link GeneratorOption
	 * generator options}.
	 */
	public static final Transformation[] filterByTranspilerOptions(Transformation[] transformations,
			GeneratorOption[] options) {
		return Stream.of(transformations).filter(t -> isActiveIn(t, options)).toArray(len -> new Transformation[len]);
	}

	/**
	 * Checks whether the given dependency is fulfilled for the given sequence of transformations running beforehand and
	 * afterwards.
	 *
	 * @return <code>null</code> on success or an error message if dependency is not fulfilled.
	 */
	public static final String check(Class<? extends Transformation> curr, Annotation dep,
			Class<? extends Transformation>[] before, Class<? extends Transformation>[] after) {
		final List<Class<? extends Transformation>> wrongTrafos = new ArrayList<>();
		for (Class<? extends Transformation> reqOrExclTrafo : getValue(dep)) {
			if (dep.annotationType() == Requires.class) {
				if (!(contains(before, reqOrExclTrafo) || contains(after, reqOrExclTrafo))) {
					wrongTrafos.add(reqOrExclTrafo);
				}
			} else if (dep.annotationType() == RequiresBefore.class) {
				if (!contains(before, reqOrExclTrafo)) {
					wrongTrafos.add(reqOrExclTrafo);
				}
			} else if (dep.annotationType() == RequiresAfter.class) {
				if (!contains(after, reqOrExclTrafo)) {
					wrongTrafos.add(reqOrExclTrafo);
				}
			} else if (dep.annotationType() == Excludes.class) {
				if (contains(before, reqOrExclTrafo) || contains(after, reqOrExclTrafo)) {
					wrongTrafos.add(reqOrExclTrafo);
				}
			} else if (dep.annotationType() == ExcludesBefore.class) {
				if (contains(before, reqOrExclTrafo)) {
					wrongTrafos.add(reqOrExclTrafo);
				}
			} else if (dep.annotationType() == ExcludesAfter.class) {
				if (contains(after, reqOrExclTrafo)) {
					wrongTrafos.add(reqOrExclTrafo);
				}
			}
		}
		if (!wrongTrafos.isEmpty()) {
			final String dependencyString = dep.annotationType().getSimpleName();
			final Iterator<String> wrongTrafoNames = wrongTrafos.stream().map(t -> t.getSimpleName()).iterator();
			return curr.getSimpleName() + " @" + dependencyString + ": " + Joiner.on(", ").join(wrongTrafoNames);
		}
		return null;
	}

	/**
	 * Checks all dependencies of the transformations in the given sequence of transformations.
	 *
	 * @return an empty list on success or one or more error messages if one or more dependencies are unfulfilled.
	 */
	public static final List<String> check(Class<? extends Transformation>[] transformations) {
		final List<String> errMsgs = new ArrayList<>();
		for (int idx = 0; idx < transformations.length; idx++) {
			final Class<? extends Transformation> currT = transformations[idx];
			final Class<? extends Transformation>[] before = Arrays.copyOf(transformations, idx);
			final Class<? extends Transformation>[] after = Arrays.copyOfRange(transformations, idx + 1,
					transformations.length);
			for (Annotation ann : currT.getAnnotations()) {
				final String errMsg = check(currT, ann, before, after);
				if (errMsg != null)
					errMsgs.add(errMsg);
			}
		}
		return errMsgs;
	}

	/**
	 * Asserts that all dependencies of the transformations in the given sequence of transformations are fulfilled or
	 * throws an {@link AssertionError} otherwise.
	 */
	public static final void assertDependencies(Transformation[] transformations) {
		@SuppressWarnings("unchecked")
		final Class<? extends Transformation>[] sequence = Arrays2.transform(transformations, t -> t.getClass())
				.toArray(new Class[0]);
		final List<String> errMsgs = check(sequence);
		if (!errMsgs.isEmpty()) {
			throw new AssertionError("one or more dependencies between transformations are violated:\n" +
					Joiner.on("\n").join(errMsgs));
		}
	}

	@SuppressWarnings("unchecked")
	private static final Class<? extends Transformation>[] getValue(Annotation ann) {
		if (ann instanceof Requires)
			return ((Requires) ann).value();
		if (ann instanceof RequiresBefore)
			return ((RequiresBefore) ann).value();
		if (ann instanceof RequiresAfter)
			return ((RequiresAfter) ann).value();
		if (ann instanceof Excludes)
			return ((Excludes) ann).value();
		if (ann instanceof ExcludesBefore)
			return ((ExcludesBefore) ann).value();
		if (ann instanceof ExcludesAfter)
			return ((ExcludesAfter) ann).value();
		if (ann instanceof Optional)
			return new Class[0];
		throw new IllegalArgumentException("unknown transformation dependency annotation: " + ann);
	}

	private static final <T> boolean contains(T[] array, T value) {
		return org.eclipse.xtext.util.Arrays.contains(array, value);
	}

	private TransformationDependency() {
		// disallow creation of instances
	}
}
