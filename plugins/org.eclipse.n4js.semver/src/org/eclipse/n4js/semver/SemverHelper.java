package org.eclipse.n4js.semver;

import java.io.StringReader;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.n4js.semver.Semver.SimpleVersion;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.semver.Semver.VersionRange;
import org.eclipse.n4js.semver.Semver.VersionRangeConstraint;
import org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement;
import org.eclipse.n4js.utils.languages.N4LanguageUtils;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.Issue;

/** Helper class to parse Semver strings and create instances of the Semver language */
public class SemverHelper {
	private SemverResourceValidator validator;
	private IParser semverParser;

	/** @return parser to parse Semver strings */
	public IParser getSemverParser() {
		if (semverParser == null) {
			semverParser = N4LanguageUtils.getServiceForContext(SemverGlobals.FILE_EXTENSION, IParser.class).get();
		}
		return semverParser;
	}

	/** @return validator to validate Semver strings */
	public SemverResourceValidator getSemverValidator() {
		if (validator == null) {
			validator = N4LanguageUtils
					.getServiceForContext(SemverGlobals.FILE_EXTENSION, SemverResourceValidator.class).get();
		}
		return validator;
	}

	/** @return {@link IParseResult} of the given input string */
	public IParseResult getParseResult(String semverString) {
		if (semverString == null) {
			return null;
		}
		IParseResult parseResult = getSemverParser().parse(new StringReader(semverString));
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

	/**
	 * Validates the given {@link IParseResult}
	 *
	 * @param resource
	 *            A {@link Resource} that can be detached from the {@link IParseResult}
	 * @param semverParseResult
	 *            A {@link IParseResult} of a Semver string
	 * @return A list of validation issues
	 */
	public List<Issue> validate(Resource resource, IParseResult semverParseResult) {
		EObject rootASTElement = semverParseResult.getRootASTElement();
		if (rootASTElement == null) {
			return Collections.emptyList();
		}

		SemverResourceValidator validat0r = getSemverValidator();
		List<Issue> issues = validat0r.validate(resource, rootASTElement, CheckMode.ALL, CancelIndicator.NullImpl);
		return issues;
	}

}
