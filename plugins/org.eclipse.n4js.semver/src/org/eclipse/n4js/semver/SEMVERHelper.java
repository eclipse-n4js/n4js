package org.eclipse.n4js.semver;

import java.io.StringReader;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.semver.SEMVER.NPMVersion;
import org.eclipse.n4js.semver.SEMVER.SimpleVersion;
import org.eclipse.n4js.semver.SEMVER.VersionNumber;
import org.eclipse.n4js.semver.SEMVER.VersionRange;
import org.eclipse.n4js.semver.SEMVER.VersionRangeConstraint;
import org.eclipse.n4js.semver.SEMVER.VersionRangeSet;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.resource.IResourceServiceProvider;

import com.google.inject.Inject;

/** Helper class to parse SEMVER strings and create instances of the SEMVER language */
public class SEMVERHelper {

	private final IResourceServiceProvider.Registry serviceProviders;

	private IParser regexParser;

	/**
	 * Creates a new literal converter that obtains an {@link IParser} for regular expressions from the given registry.
	 */
	@Inject
	public SEMVERHelper(IResourceServiceProvider.Registry serviceProviders) {
		this.serviceProviders = serviceProviders;
	}

	/** @return parser to parse SEMVER strings */
	public IParser getSEMVERParser() {
		if (regexParser == null) {
			// no need for sync since we can also use a new regexParser if concurrent access happens by accident
			IResourceServiceProvider serviceProvider = serviceProviders.getResourceServiceProvider(URI
					.createURI("a.regex"));
			regexParser = serviceProvider.get(IParser.class);
		}
		return regexParser;
	}

	/** @return {@link IParseResult} of the given input string */
	public IParseResult getParseResult(String semverString) {
		IParseResult parseResult = getSEMVERParser().parse(new StringReader(semverString));
		return parseResult;
	}

	/** @return {@link NPMVersion} of the given input string */
	public NPMVersion parse(String semverString) {
		IParseResult parseResult = getParseResult(semverString);
		NPMVersion npmVersion = (NPMVersion) parseResult.getRootASTElement();
		return npmVersion;
	}

	/** @return {@link VersionRangeSet} of the given input string */
	public VersionRangeSet parseVersionRangeSet(String semverString) {
		IParseResult parseResult = getParseResult(semverString);
		VersionRangeSet vrs = (VersionRangeSet) parseResult.getRootASTElement();
		return vrs;
	}

	/** @return {@link VersionNumber} of the given {@link IParseResult} */
	public VersionNumber parseVersionNumber(IParseResult semverParseResult) {
		VersionRangeSet vrs = (VersionRangeSet) semverParseResult.getRootASTElement();
		if (vrs.getRanges().isEmpty()) {
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
