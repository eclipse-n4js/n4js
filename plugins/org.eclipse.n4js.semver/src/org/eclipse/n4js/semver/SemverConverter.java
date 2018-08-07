package org.eclipse.n4js.semver;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.semver.SemverUtils.VersionNumberDescriptor;
import org.eclipse.n4js.semver.Semver.HyphenVersionRange;
import org.eclipse.n4js.semver.Semver.SemverFactory;
import org.eclipse.n4js.semver.Semver.SimpleVersion;
import org.eclipse.n4js.semver.Semver.VersionComparator;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.semver.Semver.VersionRange;
import org.eclipse.n4js.semver.Semver.VersionRangeConstraint;

import com.google.common.collect.Lists;

/**
 * Internal class to convert {@link HyphenVersionRange}s and some kinds of {@link SimpleVersion}s to a
 * {@link VersionRangeConstraint} that always consists of two {@link SimpleVersion}s: One has a
 * {@link VersionComparator#GREATER} or {@link VersionComparator#GREATER_EQUALS}, and the other has a
 * {@link VersionComparator#SMALLER} or {@link VersionComparator#SMALLER_EQUALS}.
 */
class SemverConverter {

	static List<SimpleVersion> simplify(VersionRange vr) {
		List<SimpleVersion> constraints = new LinkedList<>();
		if (vr instanceof HyphenVersionRange) {
			HyphenVersionRange hvr = (HyphenVersionRange) vr;
			constraints.addAll(simplify(hvr));
		}
		if (vr instanceof VersionRangeConstraint) {
			VersionRangeConstraint vrc = (VersionRangeConstraint) vr;
			for (SimpleVersion sv : vrc.getVersionConstraints()) {
				constraints.addAll(simplify(sv));
			}
		}
		return constraints;
	}

	/**
	 * Converts versions without {@link VersionComparator} or with one of the following comparators {@code v, =, ~, ^}
	 * to versions that have one the following {@link VersionComparator}s: {@code <, <=, >, >=}.
	 */
	static List<SimpleVersion> simplify(SimpleVersion sv) {
		if (sv.isSpecific()) {
			return simplifySpecific(sv);
		}
		if (sv.isCaret()) {
			return simplifyCaret(sv);
		}
		if (sv.isTilde()) {
			return simplifyTilde(sv);
		}
		return copyAndPack(sv);
	}

	static List<SimpleVersion> simplify(HyphenVersionRange hvr) {
		return simplifyHyphenRange(hvr);
	}

	private static List<SimpleVersion> simplifySpecific(SimpleVersion sv) {
		VersionNumber vn = sv.getNumber();
		if (vn.getMajor().isWildcard()) {
			VersionNumberDescriptor vnDescr = new VersionNumberDescriptor(0, 0, 0, null, null);
			return pack(vnDescr, VersionComparator.GREATER_EQUALS);
		}
		int major = vn.getMajor().getNumber();
		if (vn.getMinor() == null || vn.getMinor().isWildcard()) {
			VersionNumberDescriptor fromVND = new VersionNumberDescriptor(major, 0, 0, null, null);
			VersionNumberDescriptor toVND = new VersionNumberDescriptor(major + 1, 0, 0, null, null);
			return packRange(fromVND, VersionComparator.GREATER_EQUALS, toVND, VersionComparator.SMALLER);
		}
		int minor = vn.getMinor().getNumber();
		if (vn.getPatch() == null || vn.getPatch().isWildcard()) {
			VersionNumberDescriptor fromVND = new VersionNumberDescriptor(major, minor, 0, null, null);
			VersionNumberDescriptor toVND = new VersionNumberDescriptor(major, minor + 1, 0, null, null);
			return packRange(fromVND, VersionComparator.GREATER_EQUALS, toVND, VersionComparator.SMALLER);
		}

		VersionNumberDescriptor vnDescriptor = SemverUtils.getVersionNumberDescriptor(vn);
		return packRange(vnDescriptor, vnDescriptor);
	}

	private static List<SimpleVersion> simplifyCaret(SimpleVersion sv) {
		VersionNumber caretVN = sv.getNumber();
		VersionNumberDescriptor vnd = SemverUtils.getVersionNumberDescriptor(caretVN);
		VersionNumberDescriptor vndCaretUpper = vnd.getCaretUpperBound();
		return packRange(vnd, VersionComparator.GREATER_EQUALS, vndCaretUpper, VersionComparator.SMALLER);
	}

	private static List<SimpleVersion> simplifyTilde(SimpleVersion sv) {
		VersionNumber caretVN = sv.getNumber();
		VersionNumberDescriptor vnd = SemverUtils.getVersionNumberDescriptor(caretVN);
		VersionNumberDescriptor vndTildeUpper = vnd.getTildeUpperBound();
		return packRange(vnd, VersionComparator.GREATER_EQUALS, vndTildeUpper, VersionComparator.SMALLER);
	}

	private static List<SimpleVersion> simplifyHyphenRange(HyphenVersionRange hvr) {
		VersionNumberDescriptor vndFrom = SemverUtils.getVersionNumberDescriptor(hvr.getFrom());
		VersionNumberDescriptor vndTo = SemverUtils.getVersionNumberDescriptor(hvr.getTo());
		VersionComparator toComparator = VersionComparator.SMALLER_EQUALS;
		if (vndTo.minor == null || vndTo.patch == null) {
			toComparator = VersionComparator.SMALLER;
		}
		return packRange(vndFrom, VersionComparator.GREATER_EQUALS, vndTo, toComparator);
	}

	private static List<SimpleVersion> packRange(VersionNumberDescriptor vndFrom, VersionNumberDescriptor vndTo) {
		return packRange(vndFrom, VersionComparator.GREATER_EQUALS, vndTo, VersionComparator.SMALLER_EQUALS);
	}

	private static List<SimpleVersion> packRange(VersionNumberDescriptor vndFrom, VersionComparator vcFrom,
			VersionNumberDescriptor vndTo, VersionComparator vcTo) {

		VersionNumber vnFrom = SemverUtils.createVersionNumber(vndFrom);
		VersionNumber vnTo = SemverUtils.createVersionNumber(vndTo);
		SimpleVersion svFrom = SemverFactory.eINSTANCE.createSimpleVersion();
		SimpleVersion svTo = SemverFactory.eINSTANCE.createSimpleVersion();
		svFrom.getComparators().add(vcFrom);
		svTo.getComparators().add(vcTo);
		svFrom.setNumber(vnFrom);
		svTo.setNumber(vnTo);

		return Lists.newArrayList(svFrom, svTo);
	}

	private static List<SimpleVersion> copyAndPack(SimpleVersion sv) {
		VersionNumberDescriptor verDescr = SemverUtils.getVersionNumberDescriptor(sv.getNumber());
		EList<VersionComparator> vComps = sv.getComparators();
		return pack(verDescr, vComps.toArray(new VersionComparator[vComps.size()]));
	}

	private static List<SimpleVersion> pack(VersionNumberDescriptor verDescr, VersionComparator... vc) {
		VersionNumber vnCopy = SemverUtils.createVersionNumber(verDescr);
		SimpleVersion svCopy = SemverFactory.eINSTANCE.createSimpleVersion();
		svCopy.getComparators().addAll(Arrays.asList(vc));
		svCopy.setNumber(vnCopy);

		return Lists.newArrayList(svCopy);
	}

}
