package org.eclipse.n4js.semver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.semver.SemverMatcher.VersionNumberRelation;
import org.eclipse.n4js.semver.Semver.Qualifier;
import org.eclipse.n4js.semver.Semver.QualifierTag;
import org.eclipse.n4js.semver.Semver.SemverFactory;
import org.eclipse.n4js.semver.Semver.SimpleVersion;
import org.eclipse.n4js.semver.Semver.VersionComparator;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.semver.Semver.VersionPart;
import org.eclipse.n4js.semver.Semver.VersionRangeConstraint;
import org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement;

import com.google.common.base.Strings;

/** Utilities to create {@link VersionNumber}s */
public class SemverUtils {

	/** Utility class to simplify handling when dealing with versions, especially inside {@link SemverMatcher} */
	static class VersionNumberDescriptor {
		final Integer major;
		final Integer minor;
		final Integer patch;
		final boolean majorIsWildcard;
		final boolean minorIsWildcard;
		final boolean patchIsWildcard;
		final String[] preReleaseParts;
		final String[] buildMetadataParts;

		VersionNumberDescriptor(Integer major, Integer minor, Integer patch) {
			this(major, minor, patch, null, null);
		}

		VersionNumberDescriptor(Integer major, Integer minor, Integer patch,
				String[] preReleaseParts, String[] buildMetadataParts) {

			this.major = major;
			this.minor = minor;
			this.patch = patch;
			this.majorIsWildcard = false;
			this.minorIsWildcard = false;
			this.patchIsWildcard = false;
			this.preReleaseParts = preReleaseParts;
			this.buildMetadataParts = buildMetadataParts;
		}

		VersionNumberDescriptor(VersionPart major, VersionPart minor, VersionPart patch,
				String[] preReleaseParts, String[] buildMetadataParts) {

			this.major = (major != null && !major.isWildcard()) ? major.getNumber() : null;
			this.minor = (minor != null && !minor.isWildcard()) ? minor.getNumber() : null;
			this.patch = (patch != null && !patch.isWildcard()) ? patch.getNumber() : null;
			this.majorIsWildcard = (major == null) ? false : major.isWildcard();
			this.minorIsWildcard = (minor == null) ? false : minor.isWildcard();
			this.patchIsWildcard = (patch == null) ? false : patch.isWildcard();
			this.preReleaseParts = hasWildcard() ? null : preReleaseParts;
			this.buildMetadataParts = hasWildcard() ? null : buildMetadataParts;
		}

		boolean hasWildcard() {
			return majorIsWildcard || minorIsWildcard || patchIsWildcard;
		}

		VersionNumberDescriptor getTildeUpperBound() {
			if (minor != null) {
				return new VersionNumberDescriptor(major, minor + 1, null, null, null);
			}
			return new VersionNumberDescriptor(major + 1, null, null, null, null);
		}

		VersionNumberDescriptor getCaretUpperBound() {
			if (major != 0 || minorIsWildcard || minor == null) {
				return new VersionNumberDescriptor(major + 1, null, null, null, null);
			}
			if (minor != 0 || patchIsWildcard || patch == null) {
				return new VersionNumberDescriptor(major, minor + 1, null, null, null);
			}
			return new VersionNumberDescriptor(major, minor, patch + 1, null, null);
		}
	}

	static VersionNumberDescriptor getVersionNumberDescriptor(VersionNumber vn) {
		String[] preReleaseParts = null;
		String[] buildMetadataParts = null;

		Qualifier svQualifier = vn.getQualifier();
		if (svQualifier != null && svQualifier.getPreRelease() != null) {
			EList<String> prParts = svQualifier.getPreRelease().getParts();
			preReleaseParts = prParts.toArray(new String[prParts.size()]);
		}
		if (svQualifier != null && svQualifier.getBuildMetadata() != null) {
			EList<String> bmParts = svQualifier.getBuildMetadata().getParts();
			buildMetadataParts = bmParts.toArray(new String[bmParts.size()]);
		}

		VersionPart major = vn.getMajor();
		VersionPart minor = vn.getMinor();
		VersionPart patch = vn.getPatch();
		return new VersionNumberDescriptor(major, minor, patch, preReleaseParts, buildMetadataParts);
	}

	static VersionNumber createVersionNumber(VersionNumberDescriptor descriptor) {
		VersionNumber versNumber = SemverFactory.eINSTANCE.createVersionNumber();
		VersionPart fromMajor = SemverFactory.eINSTANCE.createVersionPart();
		fromMajor.setNumberRaw(descriptor.major);
		versNumber.setMajor(fromMajor);
		if (descriptor.minor != null) {
			VersionPart fromMinor = SemverFactory.eINSTANCE.createVersionPart();
			fromMinor.setNumberRaw(descriptor.minor);
			versNumber.setMinor(fromMinor);
		}
		if (descriptor.patch != null) {
			VersionPart fromPatch = SemverFactory.eINSTANCE.createVersionPart();
			fromPatch.setNumberRaw(descriptor.patch);
			versNumber.setPatch(fromPatch);
		}
		if (descriptor.preReleaseParts != null || descriptor.buildMetadataParts != null) {
			Qualifier qualifier = SemverFactory.eINSTANCE.createQualifier();
			versNumber.setQualifier(qualifier);
			if (descriptor.preReleaseParts != null) {
				QualifierTag prQualifierTag = SemverFactory.eINSTANCE.createQualifierTag();
				prQualifierTag.getParts().addAll(Arrays.asList(descriptor.preReleaseParts));
				qualifier.setPreRelease(prQualifierTag);
			}
			if (descriptor.buildMetadataParts != null) {
				QualifierTag bmQualifierTag = SemverFactory.eINSTANCE.createQualifierTag();
				bmQualifierTag.getParts().addAll(Arrays.asList(descriptor.buildMetadataParts));
				qualifier.setBuildMetadata(bmQualifierTag);
			}
		}
		return versNumber;
	}

	/** Creates a new instance of {@link VersionNumber} with the given properties. */
	public static VersionNumber createVersionNumber(Integer major, Integer minor, Integer patch,
			String preRelease, String buildMetadata) {

		String[] preReleaseParts = Strings.isNullOrEmpty(preRelease) ? null : preRelease.split("\\.");
		String[] buildMetadataParts = Strings.isNullOrEmpty(buildMetadata) ? null : buildMetadata.split("\\.");

		return createVersionNumber(major, minor, patch, preReleaseParts, buildMetadataParts);
	}

	/** Creates a new instance of {@link VersionNumber} with the given properties. */
	public static VersionNumber createVersionNumber(Integer major, Integer minor, Integer patch,
			String[] preReleaseParts, String[] buildMetadataParts) {

		VersionNumberDescriptor vnd = new VersionNumberDescriptor(major, minor, patch,
				preReleaseParts, buildMetadataParts);

		return createVersionNumber(vnd);
	}

	/** Creates a new instance of {@link VersionNumber} with the given properties. */
	public static VersionNumber createVersionNumber(Integer major, Integer minor, Integer patch) {
		VersionNumberDescriptor vnd = new VersionNumberDescriptor(major, minor, patch);
		return createVersionNumber(vnd);
	}

	/** Creates a new instance of {@link VersionRangeSetRequirement} with the given properties. */
	public static VersionRangeSetRequirement createVersionRangeSet(VersionComparator comparator,
			Integer major, Integer minor, Integer patch) {

		VersionNumberDescriptor vnd = new VersionNumberDescriptor(major, minor, patch);
		VersionNumber versionNumber = createVersionNumber(vnd);

		return createVersionRangeSet(comparator, versionNumber);
	}

	/** Creates a new instance of {@link VersionRangeSetRequirement} with the given properties. */
	public static VersionRangeSetRequirement createVersionRangeSet(VersionComparator comparator,
			VersionNumber version) {
		VersionNumber versionNumber = copyVersionNumber(version);

		SimpleVersion simpleVersion = SemverFactory.eINSTANCE.createSimpleVersion();
		simpleVersion.setNumber(versionNumber);
		simpleVersion.getComparators().add(comparator);

		VersionRangeConstraint versionRangeConstraint = SemverFactory.eINSTANCE.createVersionRangeConstraint();
		versionRangeConstraint.getVersionConstraints().add(simpleVersion);

		VersionRangeSetRequirement versionRangeSet = SemverFactory.eINSTANCE.createVersionRangeSetRequirement();
		versionRangeSet.getRanges().add(versionRangeConstraint);

		return versionRangeSet;
	}

	/** Copies the given {@link VersionNumber} */
	public static VersionNumber copyVersionNumber(VersionNumber version) {
		VersionNumberDescriptor vnd = getVersionNumberDescriptor(version);
		VersionNumber versionNumber = createVersionNumber(vnd);
		return versionNumber;
	}

	/** Finds the version in the collection 'versions' that is closest to the given 'toFind' */
	public static VersionNumber findClosestMatching(Collection<VersionNumber> versions, VersionNumber toFind) {
		if (versions == null || versions.isEmpty()) {
			return null;
		}

		if (null == toFind) {
			return null;
		}

		List<VersionNumber> sortedVersions = new ArrayList<>(versions);
		Collections.sort(sortedVersions, SemverMatcher::compareLoose);
		int index = Collections.binarySearch(sortedVersions, toFind, SemverMatcher::compareLoose);
		if (index > 0) {
			return sortedVersions.get(index);
		}

		VersionNumber current = null;
		for (VersionNumber v : sortedVersions) {
			VersionNumberRelation relation = SemverMatcher.relation(toFind, v);
			if (relation.isGreaterOrEqual()) {
				current = v;
			}
		}

		return current;
	}
}
