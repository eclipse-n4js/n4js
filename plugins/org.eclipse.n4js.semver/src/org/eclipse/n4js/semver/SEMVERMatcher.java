package org.eclipse.n4js.semver;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.semver.SEMVER.NPMVersion;
import org.eclipse.n4js.semver.SEMVER.SimpleVersion;
import org.eclipse.n4js.semver.SEMVER.URLSemver;
import org.eclipse.n4js.semver.SEMVER.URLVersion;
import org.eclipse.n4js.semver.SEMVER.URLVersionSpecifier;
import org.eclipse.n4js.semver.SEMVER.VersionComparator;
import org.eclipse.n4js.semver.SEMVER.VersionNumber;
import org.eclipse.n4js.semver.SEMVER.VersionPart;
import org.eclipse.n4js.semver.SEMVER.VersionRange;
import org.eclipse.n4js.semver.SEMVER.VersionRangeSet;

/** Utility class to provide methods to check matching of versions. */
public class SEMVERMatcher {

	/** Relation between two {@link VersionNumber}s */
	public enum VersionNumberRelation {
		/** Version A is smaller than version B */
		Smaller,
		/** Version A is greater than version B */
		Greater,
		/** Version A and B are unrelated */
		Equal,
		/** Version A and B are unrelated */
		Unrelated;

		/** @return iff this is either {@link VersionNumberRelation#Smaller} or {@link VersionNumberRelation#Equal} */
		public boolean isSmallerOrEqual() {
			return this == Equal || this == Smaller;
		}

		/** @return iff this is either {@link VersionNumberRelation#Greater} or {@link VersionNumberRelation#Equal} */
		public boolean isGreaterOrEqual() {
			return this == Equal || this == Greater;
		}
	}

	/**
	 * @return true iff the given arguments will yield a useful result when passed to the method
	 *         {@link #matches(VersionNumber, VersionRangeSet)}
	 */
	static public boolean validMatchesArguments(VersionNumber proband, VersionRangeSet constraint) {
		boolean invalidMatchesParams = true;
		invalidMatchesParams |= proband == null;
		invalidMatchesParams |= constraint.getRanges().isEmpty();
		return invalidMatchesParams;
	}

	/**
	 * This method checks {@link NPMVersion}s whether they match or not. In case the given npm constraint is a SEMVER
	 * version (i.e. {@link VersionRangeSet} or other subtypes that contain {@link SimpleVersion}), the given proband is
	 * checked against it and the result of this check is returned. Otherwise {@code true} is returned.
	 *
	 * @param proband
	 *            version that is checked to match the constraint
	 * @param constraint
	 *            npm version that may contain a SEMVER version range set or simple version
	 * @return true iff either the given {@code proband} version matches the given {@code constraint} version or iff the
	 *         {@code constraint} does neither contain a SEMVER version range nor a simple version
	 */
	static public boolean matches(VersionNumber proband, NPMVersion constraint) {
		if (constraint instanceof VersionRangeSet) {
			return matches(proband, (VersionRangeSet) constraint);
		}
		if (constraint instanceof URLVersion) {
			return matches(proband, (URLVersion) constraint);
		}
		return true;
	}

	/**
	 * Compares two SEMVER {@link VersionNumber}s A and B.
	 * <p>
	 * In case the argument {@code a} has a pre-release tag, this method returns true only if the given argument
	 * {@code limit} also has a pre-release tag.
	 * <p>
	 * Note that this function cannot cover cases when one version is checked against multiple other versions. In these
	 * cases the method {@link #matches(VersionNumber, VersionRangeSet)} should be used.
	 *
	 * @return relation between two given {@link VersionNumber}s
	 */
	static public VersionNumberRelation relation(VersionNumber a, VersionNumber limit) {
		return relation(a, limit, false);
	}

	/**
	 * Compares two SEMVER {@link VersionNumber}s A and B.
	 * <ul>
	 * <li/>Returns 0 iff A and B are equal.
	 * <li/>Returns 1 iff A is greater than B.
	 * <li/>Returns -1 iff A is smaller than B.
	 * <li/>Returns -10 otherwise.
	 * </ul>
	 * Note that this function cannot cover cases when one version is checked against multiple other versions. In these
	 * cases the method {@link #matches(VersionNumber, VersionRangeSet)} should be used.
	 */
	static public int compare(VersionNumber a, VersionNumber b) {
		VersionNumberRelation versionRelation = relation(a, b, false);
		switch (versionRelation) {
		case Equal:
			return 0;
		case Greater:
			return 1;
		case Smaller:
			return -1;
		case Unrelated:
			return -10;
		}
		return -10;
	}

	/**
	 * This method checks {@link VersionRangeSet}s whether they match or not. Its semantics is aligned to
	 * <a href="https://semver.npmjs.com/">semver.npmjs.com<a>.
	 *
	 * @param proband
	 *            version that is checked to match the constraint
	 * @param constraint
	 *            version that must be met by the proband
	 * @return true iff the given {@code proband} version matches the given {@code constraint} version
	 */
	static private boolean matches(VersionNumber proband, VersionRangeSet constraint) {
		EList<VersionRange> cRanges = constraint.getRanges();

		for (VersionRange cRange : cRanges) {
			List<SimpleVersion> simpleConstraints = SEMVERConverter.simplify(cRange);
			boolean rangeMatches = matches(proband, simpleConstraints);
			if (rangeMatches) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method checks {@link VersionRangeSet}s whether they match or not. Its semantics is aligned to
	 * <a href="https://semver.npmjs.com/">semver.npmjs.com<a>.
	 *
	 * @param proband
	 *            version that is checked to match the constraint
	 * @param constraint
	 *            version that must be met by the proband
	 * @return true iff the given {@code proband} version matches the given {@code constraint} version
	 */
	static private boolean matches(VersionNumber proband, URLVersion constraint) {
		URLVersionSpecifier versionSpecifier = constraint.getVersionSpecifier();
		if (versionSpecifier != null && versionSpecifier instanceof URLSemver) {
			URLSemver urlSemver = (URLSemver) versionSpecifier;
			SimpleVersion simpleVersion = urlSemver.getSimpleVersion();

			List<SimpleVersion> simpleConstraints = SEMVERConverter.simplify(simpleVersion);
			return matches(proband, simpleConstraints);
		}
		return true;
	}

	static private boolean matches(VersionNumber proband, List<SimpleVersion> simpleConstraints) {
		Collections.sort(simpleConstraints, SEMVERMatcher::compareToClusterPrereleases);

		// check constraints on the given proband
		for (int i = 0; i < simpleConstraints.size(); i++) {
			SimpleVersion simpleConstraint = simpleConstraints.get(i);

			// see definition of allowPreReleaseTag in #matches(VersionNumber, SimpleVersion, boolean)
			boolean allowPreReleaseTag = true;
			allowPreReleaseTag &= proband.hasPreReleaseTag();
			allowPreReleaseTag &= !simpleConstraint.getNumber().hasPreReleaseTag();
			allowPreReleaseTag &= i > 0;

			boolean constraintMatchesProband = matches(proband, simpleConstraint, allowPreReleaseTag);
			if (!constraintMatchesProband) {
				return false;
			}
		}
		return true;
	}

	/** This compareTo method clusters {@link SimpleVersion}s that do have a pre-release tag and those that do not. */
	static private int compareToClusterPrereleases(SimpleVersion sv1, SimpleVersion sv2) {
		boolean sv1HasPreReleaseTag = sv1.getNumber().hasPreReleaseTag();
		boolean sv2HasPreReleaseTag = sv2.getNumber().hasPreReleaseTag();
		if (sv1HasPreReleaseTag == sv2HasPreReleaseTag)
			return 0;
		if (sv1HasPreReleaseTag)
			return -1;
		if (sv2HasPreReleaseTag)
			return 1;
		throw new IllegalStateException("The Impossible State.");
	}

	/**
	 * Checks whether a given proband matches the given constraint.
	 * <p>
	 * <b>Definition of allowPreReleaseTag:</b><br/>
	 * A proband is checked on a constraint with the notion of allowPreReleaseTag iff:
	 * <ul>
	 * <li/>the proband has a pre-release tag,
	 * <li/>the constraint does not have a pre-release tag, and
	 * <li/>the proband was already successfully checked against another constraint that had a pre-release tag.
	 * </ul>
	 */
	static private boolean matches(VersionNumber proband, SimpleVersion constraint, boolean allowPreReleaseTag) {
		VersionNumber constraintVN = constraint.getNumber();
		VersionNumberRelation relation = relation(proband, constraintVN, allowPreReleaseTag);
		if (relation == VersionNumberRelation.Unrelated) {
			return false;
		}

		VersionComparator versionComparator = constraint.getComparators().get(0);
		switch (versionComparator) {
		case GREATER:
			return relation == VersionNumberRelation.Greater;

		case GREATER_EQUALS:
			return relation != VersionNumberRelation.Smaller;

		case SMALLER:
			return relation == VersionNumberRelation.Smaller;

		case SMALLER_EQUALS:
			return relation != VersionNumberRelation.Greater;

		case CARET:
		case TILDE:
		case EQUALS:
		case VERSION:
			throw new IllegalStateException("This comparator should have been replaced in SEMVERConverter#simplify.");
		}

		throw new IllegalStateException("The Impossible State.");
	}

	/**
	 * Cite from <a href="https://semver.org/">Semver.org<a>:
	 * <p>
	 * <i> Precedence MUST be calculated by separating the version into major, minor, patch and pre-release identifiers
	 * in that order (Build metadata does not figure into precedence). Precedence is determined by the first difference
	 * when comparing each of these identifiers from left to right as follows: Major, minor, and patch versions are
	 * always compared numerically.</i>
	 * <p>
	 * When determining if a version {@code vn} lies within a version range, SEMVER takes the pre-release tag of the
	 * {@code limit} as a context into account. Only if the context has a pre-release tag, both of the versions can have
	 * a relation other than {@link VersionNumberRelation#Unrelated}.<br/>
	 * However, SEMVER also specifies a relation between versions where one has a pre-release tag and the other does
	 * not. To enable this relaxed mode, pass {@code true} as a value for the parameter {@code allowPreReleaseTag}.
	 */
	static public VersionNumberRelation relation(VersionNumber vn, VersionNumber limit, boolean allowPreReleaseTag) {
		boolean qHasPR = vn.hasPreReleaseTag();
		boolean lHasPR = limit.hasPreReleaseTag();
		if (!allowPreReleaseTag && qHasPR && !lHasPR) {
			return VersionNumberRelation.Unrelated;
		}
		if (qHasPR && lHasPR) {
			// Two versions that have pre-release tags can match only if their version numbers are equal.
			boolean equalVersionsNumbers = true;
			equalVersionsNumbers &= Math.min(vn.length(), limit.length()) >= 3;
			equalVersionsNumbers &= vn.getMajor().getNumber() == limit.getMajor().getNumber();
			equalVersionsNumbers &= equalVersionsNumbers && vn.getMinor().getNumber() == limit.getMinor().getNumber();
			equalVersionsNumbers &= equalVersionsNumbers && vn.getPatch().getNumber() == limit.getPatch().getNumber();
			if (equalVersionsNumbers) {
				return relationOfQualifiers(vn, limit);
			} else {
				return VersionNumberRelation.Unrelated;
			}
		}

		int idxMax = Math.min(vn.length(), limit.length());
		for (int i = 0; i < idxMax; i++) {
			VersionPart pVn = vn.getPart(i);
			VersionPart pLimit = limit.getPart(i);
			if (pVn.isWildcard()) {
				return VersionNumberRelation.Greater;
			}
			if (pLimit.isWildcard()) {
				return VersionNumberRelation.Smaller;
			}

			int nVn = pVn.getNumber();
			int nLimit = pLimit.getNumber();
			if (nVn > nLimit) {
				return VersionNumberRelation.Greater;
			}
			if (nVn < nLimit) {
				return VersionNumberRelation.Smaller;
			}
		}
		return relationOfQualifiers(vn, limit);
	}

	/**
	 * Cite from <a href="https://semver.org/">Semver.org<a>:
	 * <p>
	 * <i> When major, minor, and patch are equal, a pre-release version has lower precedence than a normal version.
	 * Example: 1.0.0-alpha < 1.0.0. Precedence for two pre-release versions with the same major, minor, and patch
	 * version MUST be determined by comparing each dot separated identifier from left to right until a difference is
	 * found as follows: identifiers consisting of only digits are compared numerically and identifiers with letters or
	 * hyphens are compared lexically in ASCII sort order. Numeric identifiers always have lower precedence than
	 * non-numeric identifiers. A larger set of pre-release fields has a higher precedence than a smaller set, if all of
	 * the preceding identifiers are equal.</i>
	 */
	static private VersionNumberRelation relationOfQualifiers(VersionNumber vn, VersionNumber lmt) {
		List<String> vnPR = vn.getPreReleaseTag();
		List<String> lmtPR = lmt.getPreReleaseTag();

		if (vnPR == null && lmtPR == null) {
			return VersionNumberRelation.Equal;
		}
		if (vnPR != null && lmtPR == null) {
			return VersionNumberRelation.Smaller;
		}
		if (vnPR == null && lmtPR != null) {
			return VersionNumberRelation.Greater;
		}
		if (vnPR != null && lmtPR != null) {
			int idxMax = Math.min(vnPR.size(), lmtPR.size());
			for (int i = 0; i < idxMax; i++) {
				String vnPartStr = vnPR.get(i);
				String lmtPartStr = lmtPR.get(i);
				Integer vnPartInt = parseInt(vnPartStr);
				Integer lmtPartInt = parseInt(lmtPartStr);

				if (vnPartInt == null && lmtPartInt != null) {
					return VersionNumberRelation.Greater;
				}
				if (vnPartInt != null && lmtPartInt == null) {
					return VersionNumberRelation.Smaller;
				}
				if (vnPartInt != null && lmtPartInt != null) {
					if (vnPartInt > lmtPartInt) {
						return VersionNumberRelation.Greater;
					}
					if (vnPartInt < lmtPartInt) {
						return VersionNumberRelation.Smaller;
					}
				}
				if (vnPartInt == null && lmtPartInt == null) {
					if (vnPartStr.compareTo(lmtPartStr) > 0) {
						return VersionNumberRelation.Greater;
					}
					if (vnPartStr.compareTo(lmtPartStr) < 0) {
						return VersionNumberRelation.Smaller;
					}
				}
			}
			if (vnPR.size() > lmtPR.size()) {
				return VersionNumberRelation.Greater;
			}
			if (vnPR.size() < lmtPR.size()) {
				return VersionNumberRelation.Smaller;
			}
			return VersionNumberRelation.Equal;
		}
		return null;
	}

	static private Integer parseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

}
