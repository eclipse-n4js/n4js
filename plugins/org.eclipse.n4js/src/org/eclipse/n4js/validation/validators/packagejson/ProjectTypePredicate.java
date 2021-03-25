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
package org.eclipse.n4js.validation.validators.packagejson;

import static com.google.common.base.Predicates.in;
import static com.google.common.collect.Lists.asList;

import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

/**
 * Convenient predicate for {@link ProjectType project type}s. Provides {@code true} result for the evaluation if the
 * tested project type input equals any of the given project types.
 */
public class ProjectTypePredicate implements Predicate<ProjectType> {

	private final Predicate<ProjectType> delegate;

	/**
	 * Creates and returns with a predicate that provides {@code true} evaluation result if the currently tested project
	 * type matches any of the given arguments. Otherwise the created predicates provides {@code false} result.
	 *
	 * @param type
	 *            the project type to match.
	 * @param others
	 *            the other projects.
	 * @return the new predicate instance for project types.
	 */
	public static ProjectTypePredicate anyOf(final ProjectType type, final ProjectType... others) {
		return new ProjectTypePredicate(type, others);
	}

	/**
	 * Sugar for negating the predicate with type-safety.
	 *
	 * @param predicate
	 *            the predicate to negate.
	 * @return the negated predicate.
	 */
	public static ProjectTypePredicate not(ProjectTypePredicate predicate) {
		return new ProjectTypePredicate(Predicates.not(predicate));
	}

	/**
	 * Sugar for creating a new predicate that is the logical disjunction of the given predicates.
	 *
	 * @param first
	 *            the first predicate.
	 * @param second
	 *            the second predicate.
	 * @param others
	 *            the rest of the predicates. Optional, can be omitted, but when given must not contain {@code null}.
	 * @return a logical disjunction of the predicates.
	 */
	public static ProjectTypePredicate or(ProjectTypePredicate first, ProjectTypePredicate second,
			ProjectTypePredicate... others) {

		return new ProjectTypePredicate(Predicates.or(Lists.asList(first, second, others)));
	}

	/**
	 * Sugar for delegating into
	 * {@link ProjectTypePredicate#or(ProjectTypePredicate, ProjectTypePredicate, ProjectTypePredicate...)} with
	 * {@code this} and the {@code other} argument.
	 *
	 * @param other
	 *            the other predicate.
	 * @return the logical disjunction of {@code this} and the {@code other} one.
	 */
	public ProjectTypePredicate or(final ProjectTypePredicate other) {
		return ProjectTypePredicate.or(this, other);
	}

	/**
	 * Transforms the current predicate into predicate for {@link ProjectDescription project description} instances. The
	 * returning predicate is {@code null} safe.
	 *
	 * @return a new predicate instance for project descriptions.
	 */
	public Predicate<ProjectDescription> forProjectDescriptions() {
		return description -> {
			if (null == description || null == description.getType()) {
				return false;
			}
			return delegate.apply(description.getType());
		};
	}

	/**
	 * Creates a predicate for the given {@link ProjectType project type}s.
	 *
	 * @param type
	 *            the project type to match.
	 * @param others
	 *            other types. If any.
	 */
	private ProjectTypePredicate(final ProjectType type, final ProjectType... others) {
		this(in(asList(type, others)));
	}

	/**
	 * Creates a new predicate with the delegate predicate.
	 *
	 * @param delegate
	 *            the delegate predicate.
	 */
	private ProjectTypePredicate(final Predicate<ProjectType> delegate) {
		this.delegate = delegate;
	}

	@Override
	public boolean apply(final ProjectType input) {
		return delegate.apply(input);
	}

}
