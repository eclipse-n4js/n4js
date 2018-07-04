package org.eclipse.n4js.semver;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.semver.SEMVER.HyphenVersionRange;
import org.eclipse.n4js.semver.SEMVER.Qualifier;
import org.eclipse.n4js.semver.SEMVER.QualifierTag;
import org.eclipse.n4js.semver.SEMVER.SEMVERFactory;
import org.eclipse.n4js.semver.SEMVER.SimpleVersion;
import org.eclipse.n4js.semver.SEMVER.VersionComparator;
import org.eclipse.n4js.semver.SEMVER.VersionNumber;
import org.eclipse.n4js.semver.SEMVER.VersionPart;
import org.eclipse.n4js.semver.SEMVER.VersionRange;
import org.eclipse.n4js.semver.SEMVER.VersionRangeConstraint;

import com.google.common.collect.Lists;

/**
 * Internal class to convert {@link HyphenVersionRange}s and some kinds of {@link SimpleVersion}s to a
 * {@link VersionRangeConstraint} that always consists of two {@link SimpleVersion}s: One has a
 * {@link VersionComparator#GREATER} or {@link VersionComparator#GREATER_EQUALS}, and the other has a
 * {@link VersionComparator#SMALLER} or {@link VersionComparator#SMALLER_EQUALS}.
 */
class SEMVERConverter {

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

		VersionNumberDescriptor vnDescriptor = getVersionNumberDescriptor(vn);
		return packRange(vnDescriptor, vnDescriptor);
	}

	private static List<SimpleVersion> simplifyCaret(SimpleVersion sv) {
		VersionNumber caretVN = sv.getNumber();
		VersionNumberDescriptor vnd = getVersionNumberDescriptor(caretVN);
		VersionNumberDescriptor vndCaretUpper = vnd.getCaretUpperBound();
		return packRange(vnd, VersionComparator.GREATER_EQUALS, vndCaretUpper, VersionComparator.SMALLER);
	}

	private static List<SimpleVersion> simplifyTilde(SimpleVersion sv) {
		VersionNumber caretVN = sv.getNumber();
		VersionNumberDescriptor vnd = getVersionNumberDescriptor(caretVN);
		VersionNumberDescriptor vndTildeUpper = vnd.getTildeUpperBound();
		return packRange(vnd, VersionComparator.GREATER_EQUALS, vndTildeUpper, VersionComparator.SMALLER);
	}

	private static List<SimpleVersion> simplifyHyphenRange(HyphenVersionRange hvr) {
		VersionNumberDescriptor vndFrom = getVersionNumberDescriptor(hvr.getFrom());
		VersionNumberDescriptor vndTo = getVersionNumberDescriptor(hvr.getTo());
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

		VersionNumber vnFrom = createVersionNumber(vndFrom);
		VersionNumber vnTo = createVersionNumber(vndTo);
		SimpleVersion svFrom = SEMVERFactory.eINSTANCE.createSimpleVersion();
		SimpleVersion svTo = SEMVERFactory.eINSTANCE.createSimpleVersion();
		svFrom.getComparators().add(vcFrom);
		svTo.getComparators().add(vcTo);
		svFrom.setNumber(vnFrom);
		svTo.setNumber(vnTo);

		return Lists.newArrayList(svFrom, svTo);
	}

	private static List<SimpleVersion> copyAndPack(SimpleVersion sv) {
		VersionNumberDescriptor verDescr = getVersionNumberDescriptor(sv.getNumber());
		EList<VersionComparator> vComps = sv.getComparators();
		return pack(verDescr, vComps.toArray(new VersionComparator[vComps.size()]));
	}

	private static List<SimpleVersion> pack(VersionNumberDescriptor verDescr, VersionComparator... vc) {
		VersionNumber vnCopy = createVersionNumber(verDescr);
		SimpleVersion svCopy = SEMVERFactory.eINSTANCE.createSimpleVersion();
		svCopy.getComparators().addAll(Arrays.asList(vc));
		svCopy.setNumber(vnCopy);

		return Lists.newArrayList(svCopy);
	}

	private static class VersionNumberDescriptor {
		final Integer major;
		final Integer minor;
		final Integer patch;
		final String[] preReleaseParts;
		final String[] buildMetadataParts;

		VersionNumberDescriptor(Integer major, Integer minor, Integer patch,
				String[] preReleaseParts, String[] buildMetadataParts) {

			this.major = major;
			this.minor = minor;
			this.patch = patch;
			this.preReleaseParts = preReleaseParts;
			this.buildMetadataParts = buildMetadataParts;
		}

		VersionNumberDescriptor getTildeUpperBound() {
			if (minor != null) {
				return new VersionNumberDescriptor(major, minor + 1, null, null, null);
			}
			return new VersionNumberDescriptor(major + 1, null, null, null, null);
		}

		VersionNumberDescriptor getCaretUpperBound() {
			if (major != 0 || minor == null) {
				return new VersionNumberDescriptor(major + 1, null, null, null, null);
			}
			if ((minor != null && minor != 0) || patch == null) {
				return new VersionNumberDescriptor(major, minor + 1, null, null, null);
			}
			return new VersionNumberDescriptor(major, minor, patch + 1, null, null);
		}
	}

	private static VersionNumberDescriptor getVersionNumberDescriptor(VersionNumber vn) {
		Integer major = null;
		Integer minor = null;
		Integer patch = null;
		String[] preReleaseParts = null;
		String[] buildMetadataParts = null;

		major = vn.getMajor().getNumber();
		if (vn.getMinor() != null) {
			minor = vn.getMinor().getNumber();
		}
		if (vn.getPatch() != null) {
			patch = vn.getPatch().getNumber();
		}
		Qualifier svQualifier = vn.getQualifier();
		if (svQualifier != null && svQualifier.getPreRelease() != null) {
			EList<String> prParts = svQualifier.getPreRelease().getParts();
			preReleaseParts = prParts.toArray(new String[prParts.size()]);
		}
		if (svQualifier != null && svQualifier.getBuildMetadata() != null) {
			EList<String> bmParts = svQualifier.getBuildMetadata().getParts();
			buildMetadataParts = bmParts.toArray(new String[bmParts.size()]);
		}

		return new VersionNumberDescriptor(major, minor, patch, preReleaseParts, buildMetadataParts);
	}

	private static VersionNumber createVersionNumber(VersionNumberDescriptor descriptor) {
		VersionNumber versNumber = SEMVERFactory.eINSTANCE.createVersionNumber();
		VersionPart fromMajor = SEMVERFactory.eINSTANCE.createVersionPart();
		fromMajor.setNumber(descriptor.major);
		versNumber.setMajor(fromMajor);
		if (descriptor.minor != null) {
			VersionPart fromMinor = SEMVERFactory.eINSTANCE.createVersionPart();
			fromMinor.setNumber(descriptor.minor);
			versNumber.setMinor(fromMinor);
		}
		if (descriptor.patch != null) {
			VersionPart fromPatch = SEMVERFactory.eINSTANCE.createVersionPart();
			fromPatch.setNumber(descriptor.patch);
			versNumber.setPatch(fromPatch);
		}
		if (descriptor.preReleaseParts != null || descriptor.buildMetadataParts != null) {
			Qualifier qualifier = SEMVERFactory.eINSTANCE.createQualifier();
			versNumber.setQualifier(qualifier);
			if (descriptor.preReleaseParts != null) {
				QualifierTag prQualifierTag = SEMVERFactory.eINSTANCE.createQualifierTag();
				prQualifierTag.getParts().addAll(Arrays.asList(descriptor.preReleaseParts));
				qualifier.setPreRelease(prQualifierTag);
			}
			if (descriptor.buildMetadataParts != null) {
				QualifierTag bmQualifierTag = SEMVERFactory.eINSTANCE.createQualifierTag();
				bmQualifierTag.getParts().addAll(Arrays.asList(descriptor.buildMetadataParts));
				qualifier.setBuildMetadata(bmQualifierTag);
			}
		}
		return versNumber;
	}
}
