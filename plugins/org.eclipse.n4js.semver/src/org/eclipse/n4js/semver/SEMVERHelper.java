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

	/** @return {@link NPMVersion} of the given input string */
	public NPMVersion parse(String semverString) {
		IParseResult parseResult = getParseResult(semverString);
		if (parseResult.getRootASTElement() instanceof NPMVersion) {
			NPMVersion npmVersion = (NPMVersion) parseResult.getRootASTElement();
			return npmVersion;
		}
		return null;
	}

	/** @return {@link VersionRangeSet} of the given input string */
	public VersionRangeSet parseVersionRangeSet(String semverString) {
		IParseResult parseResult = getParseResult(semverString);
		if (parseResult.getRootASTElement() instanceof VersionRangeSet) {
			VersionRangeSet vrs = (VersionRangeSet) parseResult.getRootASTElement();
			return vrs;
		}
		return null;
	}

	/** @return {@link VersionNumber} of the given {@link IParseResult} */
	public VersionNumber parseVersionNumber(IParseResult semverParseResult) {
		if (semverParseResult == null) {
			return null;
		}
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
