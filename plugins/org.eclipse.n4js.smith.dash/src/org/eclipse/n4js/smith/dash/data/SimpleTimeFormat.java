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
package org.eclipse.n4js.smith.dash.data;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.concurrent.TimeUnit;

/**
 * Formatter for time expressed as double with nanoseconds.
 */
public class SimpleTimeFormat extends Format {
	private final static String NO_DECIMAL = "%.0f %s";
	private final static String WITH_DECIMAL = "%.4g %s";

	/** Convert time expressed as long with nanoseconds to human readable string. */
	public static String convert(long duration) {
		return convertWithFormat(duration, WITH_DECIMAL);
	}

	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		if (obj instanceof Double) {
			return toAppendTo.append(convertWithFormat(((Double) obj).longValue(), NO_DECIMAL));
		}
		throw new RuntimeException("Unexpected call.");
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		throw new RuntimeException("Unexpected call.");
	}

	/** Convert with provided format. */
	private static String convertWithFormat(long duration, String format) {

		long nanos = Math.abs(duration);

		TimeUnit unit = chooseUnit(nanos);
		double value = (double) nanos / NANOSECONDS.convert(1, unit);

		String converted = String.format(format, value, abbreviate(unit));
		if (duration < 0)
			return "-" + converted;
		return converted;
	}

	private static TimeUnit chooseUnit(long nanos) {
		if (DAYS.convert(nanos, NANOSECONDS) > 0)
			return DAYS;
		if (HOURS.convert(nanos, NANOSECONDS) > 0)
			return HOURS;
		if (MINUTES.convert(nanos, NANOSECONDS) > 0)
			return MINUTES;
		if (SECONDS.convert(nanos, NANOSECONDS) > 0)
			return SECONDS;
		if (MILLISECONDS.convert(nanos, NANOSECONDS) > 0)
			return MILLISECONDS;
		if (MICROSECONDS.convert(nanos, NANOSECONDS) > 0)
			return MICROSECONDS;
		return NANOSECONDS;
	}

	private static String abbreviate(TimeUnit unit) {
		switch (unit) {
		case NANOSECONDS:
			return "ns";
		case MICROSECONDS:
			return "μs";
		case MILLISECONDS:
			return "ms";
		case SECONDS:
			return "s";
		case MINUTES:
			return "min";
		case HOURS:
			return "h";
		case DAYS:
			return "d";
		default:
			throw new RuntimeException("Unsupported unit " + unit.name());
		}
	}
}
