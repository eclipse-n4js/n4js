package org.eclipse.n4js.semver;

import java.io.StringReader;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.semver.SEMVER.NPMVersionRequirement;
import org.eclipse.n4js.semver.SEMVER.SimpleVersion;
import org.eclipse.n4js.semver.SEMVER.VersionNumber;
import org.eclipse.n4js.semver.SEMVER.VersionRange;
import org.eclipse.n4js.semver.SEMVER.VersionRangeConstraint;
import org.eclipse.n4js.semver.SEMVER.VersionRangeSetRequirement;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.resource.IResourceServiceProvider;

import com.google.inject.Inject;

/** Helper class to parse SEMVER strings and create instances of the SEMVER language */
public class SEMVERHelper {

	private final IResourceServiceProvider.Registry serviceProviders;

	private IParser semverParser;

	/**
	 * Creates a new literal converter that obtains an {@link IParser} for regular expressions from the given registry.
	 */
	@Inject
	public SEMVERHelper(IResourceServiceProvider.Registry serviceProviders) {
		this.serviceProviders = serviceProviders;
	}

	/** @return parser to parse SEMVER strings */
	public IParser getSEMVERParser() {
		if (semverParser == null) {
			// no need for sync since we can also use a new semverParser if concurrent access happens by accident
			IResourceServiceProvider serviceProvider = serviceProviders.getResourceServiceProvider(URI
					.createURI("a." + SEMVERGlobals.FILE_EXTENSION));
			semverParser = serviceProvider.get(IParser.class);
		}
		return semverParser;
	}

	/** @return {@link IParseResult} of the given input string */
	public IParseResult getParseResult(String semverString) {
		if (semverString == null) {
			return null;
		}
		IParseResult parseResult = getSEMVERParser().parse(new StringReader(semverString));
		return parseResult;
	}

	/** @return {@link NPMVersionRequirement} of the given input string */
	public NPMVersionRequirement parse(String semverString) {
		IParseResult parseResult = getParseResult(semverString);
		return parse(parseResult);
	}

	/** @return {@link NPMVersionRequirement} of the given parse result */
	public NPMVersionRequirement parse(IParseResult parseResult) {
		if (parseResult != null && parseResult.getRootASTElement() instanceof NPMVersionRequirement) {
			NPMVersionRequirement npmVersion = (NPMVersionRequirement) parseResult.getRootASTElement();
			return npmVersion;
		}
		return null;
	}

	/** @return {@link VersionRangeSetRequirement} of the given input string */
	public VersionRangeSetRequirement parseVersionRangeSet(String semverString) {
		NPMVersionRequirement npmVersion = parse(semverString);
		if (npmVersion instanceof VersionRangeSetRequirement) {
			VersionRangeSetRequirement vrs = (VersionRangeSetRequirement) npmVersion;
			return vrs;
		}
		return null;
	}

	/** @return {@link VersionRangeSetRequirement} of the given {@link IParseResult} */
	public VersionRangeSetRequirement parseVersionRangeSet(IParseResult semverParseResult) {
		NPMVersionRequirement npmVersion = parse(semverParseResult);
		if (npmVersion instanceof VersionRangeSetRequirement) {
			VersionRangeSetRequirement vrs = (VersionRangeSetRequirement) npmVersion;
			return vrs;
		}
		return null;
	}

	/** @return {@link VersionNumber} of the given {@link IParseResult} */
	public VersionNumber parseVersionNumber(IParseResult semverParseResult) {
		VersionRangeSetRequirement vrs = parseVersionRangeSet(semverParseResult);
		if (vrs == null) {
			return null;
		}
		VersionRange firstVersionRange = vrs.getRanges().get(0);
		if (!(firstVersionRange instanceof VersionRangeConstraint)) {
			return null;
		}
		VersionRangeConstraint vrc = (VersionRangeConstraint) firstVersionRange;
		if (vrc.getVersionConstraints().isEmpty()) {
			return null;
		}
		SimpleVersion firstSimpleVersion = vrc.getVersionConstraints().get(0);
		return firstSimpleVersion.getNumber();
	}

	/** @return {@link VersionNumber} of the given input string */
	public VersionNumber parseVersionNumber(String semverString) {
		IParseResult semverParseResult = getParseResult(semverString);
		return parseVersionNumber(semverParseResult);
	}

}
