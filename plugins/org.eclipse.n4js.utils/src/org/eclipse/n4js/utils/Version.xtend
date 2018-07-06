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
package org.eclipse.n4js.utils

import com.google.common.base.Preconditions
import com.google.common.base.Strings
import com.google.common.primitives.Ints
import java.util.ArrayList
import java.util.Collections
import org.apache.log4j.Logger
import org.eclipse.xtend.lib.annotations.AccessorType
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.EqualsHashCode

import static extension org.eclipse.xtend.lib.annotations.AccessorType.*

/**
 * Representation of a version. Contains major, minor and micro version number.
 */
@Accessors(AccessorType.PUBLIC_GETTER)
@EqualsHashCode
class Version implements Comparable<Version> {

	val static LOGGER = Logger.getLogger(Version);

	/** Singleton for representing a missing version. */
	public val static MISSING = new Version(0, 0, 0, null);

	val int major;
	val int minor;
	val int micro;
	val String qualifier;

	/**
	 * Returns with {@code true} if the version argument is neither {@code null}, nor the {@link #MISSING} one.
	 * Otherwise returns with {@code false}.
	 */
	static def boolean isValid(Version version) {
		return null !== version && MISSING !== version;
	}

	static def Version createFromString(String toRead) {

		if (toRead.nullOrEmpty) {
			return MISSING;
		}

		// Strip all leading non-numeric characters.
		var finished = false;
		var firstDigitIndex = 0;
		while (!finished && firstDigitIndex < toRead.length) {
			if (!Character.isDigit(toRead.charAt(firstDigitIndex))) {
				firstDigitIndex++;
			} else {
				finished = true;
			}
		}

		val ArrayList<String> values = new ArrayList(toRead.substring(firstDigitIndex).split('\\.', 4));
		if (values.length < 3) {
			return MISSING;
		}

		val tailValues = values.get(2).split('-', 2);
		if (tailValues.size == 2) {
			values.set(2, tailValues.get(0))
			values.add(tailValues.get(1))
		} else if (tailValues.size > 2) {
			LOGGER.warn('''Error while parsing version qualifier form string '«toRead»'. Ignoring qualifier.''');
		}

		val int[] numbers = newIntArrayOfSize(3);
		try {
			for (var i = 0; i < 3; i++) {
				numbers.set(i, Integer.parseInt(values.get(i)));
			}
		} catch (NumberFormatException e) {
			LOGGER.error('''Error while parsing version form string '«toRead»'. Falling back to «MISSING».''', e);
			return MISSING;
		}

		return new Version(numbers.get(0), numbers.get(1), numbers.get(2),
			if (4 === values.length) values.get(3) else null);
	}

	static def Version findClosestMatching(Iterable<Version> versions, Version toFind) {

		if (versions.nullOrEmpty) {
			return MISSING;
		}

		if (null === toFind || MISSING == toFind) {
			return MISSING;
		}

		val sortedVersions = versions.toList.sort;
		val index = Collections.binarySearch(sortedVersions, toFind);
		if (0 <= index) {
			return sortedVersions.get(index);
		}

		var current = MISSING;
		for (v : sortedVersions) {
			if (0 <= toFind.compareTo(v)) {
				current = v;
			}
		}

		return current;
	}

	static def int getVersionNumber(String versionString, int place) {
		if (versionString !== null) {
			val split = versionString.split(".");
			if (split.length > place && split.get(place).length() > 0) {
				val versionNumber = Integer.parseInt(split.get(place));
				return versionNumber
			}
		}
		return 0;
	}

	static def String getVersionQualifier(String versionString) {
		if (versionString !== null) {
			val split = versionString.split("-");
			if (split.length > 1) {
				return split.get(1)
			}
		}
		return "";
	}

	new(String versionString) {
		this(
			getVersionNumber(versionString, 0),
			getVersionNumber(versionString, 1),
			getVersionNumber(versionString, 2),
			getVersionQualifier(versionString)
		);
	}

	new(int major, int minor, int micro) {
		this(major, minor, micro, null);
	}

	new(int major, int minor, int micro, String qualifier) {
		Preconditions.checkArgument(major >= 0, 'major < 0');
		Preconditions.checkArgument(minor >= 0, 'minor < 0');
		Preconditions.checkArgument(micro >= 0, 'micro < 0');
		this.major = major;
		this.minor = minor;
		this.micro = micro;
		this.qualifier = Strings.nullToEmpty(qualifier);
	}

	override compareTo(Version o) {
		if (!o.valid) {
			return -1;
		}

		var result = Ints.compare(major, o.major);
		if (0 !== result) {
			return result;
		}

		result = Ints.compare(minor, o.minor);
		if (0 !== result) {
			return result;
		}

		result = Ints.compare(micro, o.micro);
		if (0 !== result) {
			return result;
		}

		result = nullSafeCompare(qualifier, o.qualifier);
		if (0 !== result) {
			return result;
		}

		return 0;
	}

	override toString() {
		if (MISSING == this) {
			return 'MISSING';
		}
		return '''«major».«minor».«micro»«IF !qualifier.nullOrEmpty»-«qualifier»«ENDIF»''';
	}

	def private int nullSafeCompare(String l, String r) {
		val lHasQualifier = !Strings.isNullOrEmpty(l);
		val rHasQualifier = !Strings.isNullOrEmpty(r);
		return if (lHasQualifier && !rHasQualifier) {
			-1
		} else if (!lHasQualifier && rHasQualifier) {
			1
		} else if (lHasQualifier && rHasQualifier) {
			l.compareTo(r) // TODO double-check if Java string compare complies to SemVer precedence, cf. https://semver.org/#spec-item-11
		} else {
			0
		};		
	}
}
