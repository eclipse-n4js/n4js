/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.filechecker;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.function.Function;

import com.google.common.collect.Multimap;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;

/**
 * Utilities for processing reports
 */
public class ReportUtils {

	/** Filters the given reports and returns those that are ignored */
	static public Collection<Report> filterIgnoredReports(Collection<Report> reports) {
		return filterReports(reports, Report::isIgnored);
	}

	/** Filters the given reports and returns those that are ignored */
	static public Collection<Report> filterNotIgnoredReports(Collection<Report> reports) {
		return filterReports(reports, Report::isNotIgnored);
	}

	/** Filters the given reports and returns those that are valid */
	static public Collection<Report> filterValidReports(Collection<Report> reports) {
		return filterReports(reports, Report::isValid);
	}

	/** Filters the given reports and returns those that have problems */
	static public Collection<Report> filterInvalidReports(Collection<Report> reports) {
		return filterReports(reports, Report::isInvalid);
	}

	/** Filters the given reports and returns those that are erroneous */
	static public Collection<Report> filterErroneousReports(Collection<Report> reports) {
		return filterReports(reports, Report::isErroneous);
	}

	/** Filters the given reports and returns those that do not have a copyright header */
	static public Collection<Report> filterReportsNoCRH(Collection<Report> reports) {
		return filterReports(reports, Report::isMissingCopyrightHeader);
	}

	/** Filters the given reports and returns those that are located in test projects */
	static public Collection<Report> filterReportsInTests(Collection<Report> reports) {
		return filterReports(reports, Report::isInTestProject);
	}

	/** Filters the given reports and returns those that are located in the N4JS repository */
	static public Collection<Report> filterReportsInN4JS(Collection<Report> reports) {
		return filterReports(reports, Report::isInN4JSRepo);
	}

	/** Filters the given reports and returns those that are located in the N4JS repository */
	static public Collection<Report> filterReportsInN4JSN4(Collection<Report> reports) {
		return filterReports(reports, Report::isInN4JSN4Repo);
	}

	/** Filters the given reports and returns those that are located in test projects */
	static public Collection<Report> filterReportsNotInTests(Collection<Report> reports) {
		return filterReports(reports, Report::isNotInTestProject);
	}

	/** Filter is supposed to return true iff the element should be contained in the list. */
	static public Collection<Report> filterReports(Collection<Report> reports, Function<Report, Boolean> filter) {
		Collection<Report> filteredReports = new LinkedList<>();
		for (Report report : reports) {
			if (filter.apply(report)) {
				filteredReports.add(report);
			}
		}
		return filteredReports;
	}

	/** Creates a histogram of the given collections */
	static public <E extends Comparable<E>, T extends Comparable<T>> Multimap<T, E> getHistogram(Collection<E> elems,
			Function<E, T> pivot) {

		final SortedSetMultimap<T, E> histogram = TreeMultimap.create();
		for (E elem : elems) {
			T t = pivot.apply(elem);
			histogram.put(t, elem);
		}
		return histogram;
	}

	/** Sorts a {@link Multimap} based on the length of each value list */
	static public <E extends Comparable<E>, T extends Comparable<T>> TreeMap<T, Collection<E>> sortByListSize(
			Multimap<T, E> multimap) {

		Comparator<T> comparator = new Comparator<>() {
			@Override
			public int compare(T t1, T t2) {
				int s1 = multimap.get(t1).size();
				int s2 = multimap.get(t2).size();
				if (s2 != s1)
					return s2 - s1;
				return t1.compareTo(t2);
			}
		};
		TreeMap<T, Collection<E>> sortedMap = new TreeMap<>(comparator);
		for (T key : multimap.keySet()) {
			Collection<E> elems = multimap.get(key);
			sortedMap.put(key, elems);
		}

		return sortedMap;
	}
}
