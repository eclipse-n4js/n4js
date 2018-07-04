package org.eclipse.n4js.semver;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.semver.SEMVER.Qualifier;
import org.eclipse.n4js.semver.SEMVER.SimpleVersion;
import org.eclipse.n4js.semver.SEMVER.VersionComparator;
import org.eclipse.n4js.semver.SEMVER.VersionNumber;
import org.eclipse.n4js.semver.SEMVER.VersionPart;
import org.eclipse.n4js.semver.SEMVER.VersionRange;
import org.eclipse.n4js.semver.SEMVER.VersionRangeSet;

/** Utility class to provide methods to check matching of versions. */
public class SEMVERMatcher {

	/**
	 * This method checks {@link VersionRangeSet}s whether they match or not.
	 *
	 * @param proband
	 *            version that is checked to match the constraint
	 * @param constraint
	 *            version that must be met by the proband
	 * @return true iff the given {@code proband} version matches the given {@code constraint} version
	 */
	static public boolean matches(VersionNumber proband, VersionRangeSet constraint) {
		EList<VersionRange> cRanges = constraint.getRanges();

		for (VersionRange cRange : cRanges) {
			boolean rangeMatches = matches(proband, cRange);
			if (rangeMatches) {
				return true;
			}
		}
		return false;
	}

	static private boolean matches(VersionNumber proband, VersionRange constraint) {
		List<SimpleVersion> vrConstraints = SEMVERConverter.simplify(constraint);
		for (SimpleVersion sv : vrConstraints) {
			if (!matches(proband, sv)) {
				return false;
			}
		}
		return true;
	}

	static private boolean matches(VersionNumber proband, SimpleVersion constraint) {
		VersionNumber constraintVN = constraint.getNumber();
		VersionNumberRelation relation = relation(proband, constraintVN);
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

		return false;
	}

	private enum VersionNumberRelation {
		Smaller, Greater, Equal, Unrelated
	}

	/**
	 * Cite from <a href="https://semver.org/">Semver.org<a>
	 * <p>
	 * Precedence MUST be calculated by separating the version into major, minor, patch and pre-release identifiers in
	 * that order (Build metadata does not figure into precedence). Precedence is determined by the first difference
	 * when comparing each of these identifiers from left to right as follows: Major, minor, and patch versions are
	 * always compared numerically.
	 */
	static private VersionNumberRelation relation(VersionNumber vn, VersionNumber limit) {
		List<String> qPR = getPreReleaseParts(vn.getQualifier());
		List<String> lPR = getPreReleaseParts(limit.getQualifier());
		if (qPR != null && lPR != null) {
			// Two versions that have pre-release tags can match only if their version numbers are equal.
			boolean equalVersionsNumbers = true;
			equalVersionsNumbers &= Math.min(vn.length(), limit.length()) >= 3;
			equalVersionsNumbers &= vn.getMajor().getNumber() == limit.getMajor().getNumber();
			equalVersionsNumbers &= equalVersionsNumbers && vn.getMinor().getNumber() == limit.getMinor().getNumber();
			equalVersionsNumbers &= equalVersionsNumbers && vn.getPatch().getNumber() == limit.getPatch().getNumber();
			if (equalVersionsNumbers) {
				return relation(vn.getQualifier(), limit.getQualifier());
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
		return relation(vn.getQualifier(), limit.getQualifier());
	}

	/**
	 * Cite from <a href="https://semver.org/">Semver.org<a>
	 * <p>
	 * When major, minor, and patch are equal, a pre-release version has lower precedence than a normal version.
	 * Example: 1.0.0-alpha < 1.0.0. Precedence for two pre-release versions with the same major, minor, and patch
	 * version MUST be determined by comparing each dot separated identifier from left to right until a difference is
	 * found as follows: identifiers consisting of only digits are compared numerically and identifiers with letters or
	 * hyphens are compared lexically in ASCII sort order. Numeric identifiers always have lower precedence than
	 * non-numeric identifiers. A larger set of pre-release fields has a higher precedence than a smaller set, if all of
	 * the preceding identifiers are equal.
	 */
	static private VersionNumberRelation relation(Qualifier qlf, Qualifier lmt) {
		List<String> qPR = getPreReleaseParts(qlf);
		List<String> lPR = getPreReleaseParts(lmt);

		if (qPR == null && lPR == null) {
			return VersionNumberRelation.Equal;
		}
		if (qPR != null && lPR == null) {
			return VersionNumberRelation.Smaller;
		}
		if (qPR == null && lPR != null) {
			return VersionNumberRelation.Greater;
		}
		if (qPR != null && lPR != null) {
			int idxMax = Math.min(qPR.size(), lPR.size());
			for (int i = 0; i < idxMax; i++) {
				String qPartStr = qPR.get(i);
				String lPartStr = lPR.get(i);
				Integer qPartInt = parseInt(qPartStr);
				Integer lPartInt = parseInt(lPartStr);

				if (qPartInt == null && lPartInt != null) {
					return VersionNumberRelation.Greater;
				}
				if (qPartInt != null && lPartInt == null) {
					return VersionNumberRelation.Smaller;
				}
				if (qPartInt != null && lPartInt != null) {
					if (qPartInt > lPartInt) {
						return VersionNumberRelation.Greater;
					}
					if (qPartInt < lPartInt) {
						return VersionNumberRelation.Smaller;
					}
				}
				if (qPartInt == null && lPartInt == null) {
					if (qPartStr.compareTo(lPartStr) > 0) {
						return VersionNumberRelation.Greater;
					}
					if (qPartStr.compareTo(lPartStr) < 0) {
						return VersionNumberRelation.Smaller;
					}
				}
			}
			if (qPR.size() > lPR.size()) {
				return VersionNumberRelation.Greater;
			}
			if (qPR.size() < lPR.size()) {
				return VersionNumberRelation.Smaller;
			}
			return VersionNumberRelation.Greater;
		}
		return null;
	}

	static private List<String> getPreReleaseParts(Qualifier qlf) {
		return (qlf != null && qlf.getPreRelease() != null) ? qlf.getPreRelease().getParts() : null;
	}

	static private Integer parseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

}
