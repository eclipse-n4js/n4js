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
package org.eclipse.n4js.n4idl.migrations;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.n4js.n4idl.migrations.SuperClassifierIterator.SuperClassifierEntry;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TypingStrategy;

/**
 * An iterator which allows to iterate over the super-classifier structure of a given base {@link TClassifier} in a
 * breadth-first fashion.
 *
 * The iterator returns the classifiers as well as the current traversal level (distance from the base classifier) in
 * the class hierarchy.
 */
public class SuperClassifierIterator implements Iterator<SuperClassifierEntry> {
	/** Iterator entry of a {@link SuperClassifierIterator}. */
	public static class SuperClassifierEntry {
		/** The classifier of this entry. */
		public final TClassifier classifier;
		/** The level of this classifier entry (distance from the base classifier). */
		public final int level;

		/** Instantiates a new iterator entry with the given values. */
		public SuperClassifierEntry(int level, TClassifier classifier) {
			this.level = level;
			this.classifier = classifier;
		}
	}

	private final Deque<SuperClassifierEntry> queue = new ArrayDeque<>();

	/** Instantiates a new {@link SuperClassifierIterator} with the given base classifier. */
	public SuperClassifierIterator(TClassifier baseClassifier) {
		this.queue.push(new SuperClassifierEntry(0, baseClassifier));
	}

	@Override
	public boolean hasNext() {
		return !this.queue.isEmpty();
	}

	@Override
	public SuperClassifierEntry next() {
		if (this.queue.isEmpty()) {
			throw new NoSuchElementException();
		}
		// obtain next entry in the queue
		SuperClassifierEntry nextEntry = this.queue.pop();
		// add all super-classifiers that stem from this entry to the queue
		this.queue.addAll(getSuperClassifiers(nextEntry.level, nextEntry.classifier));

		return nextEntry;
	}

	/**
	 * Returns all iterable super-classifiers of the given {@link TClassifier}.
	 *
	 * This excludes types which declare a {@link TypingStrategy} different from {@link TypingStrategy#NOMINAL} on the
	 * definition site.
	 *
	 * @param level
	 *            The previous iteration level
	 * @param classifier
	 *            The classifier of which to obtain all super classifier entries.
	 */
	private Collection<SuperClassifierEntry> getSuperClassifiers(int level, TClassifier classifier) {
		return StreamSupport.stream(classifier.getSuperClassifierRefs().spliterator(), false)
				.filter(c -> c.getTypingStrategy() == TypingStrategy.NOMINAL)
				.map(c -> c.getDeclaredType())
				.filter(c -> c instanceof TClassifier)
				.map(c -> new SuperClassifierEntry(level + 1, (TClassifier) c))
				.collect(Collectors.toList());
	}
}
