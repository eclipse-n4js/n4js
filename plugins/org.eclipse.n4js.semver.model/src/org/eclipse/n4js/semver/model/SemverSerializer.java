package org.eclipse.n4js.semver.model;

import java.io.IOException;
import java.io.Writer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.semver.Semver.GitHubVersionRequirement;
import org.eclipse.n4js.semver.Semver.HyphenVersionRange;
import org.eclipse.n4js.semver.Semver.LocalPathVersionRequirement;
import org.eclipse.n4js.semver.Semver.Qualifier;
import org.eclipse.n4js.semver.Semver.QualifierTag;
import org.eclipse.n4js.semver.Semver.SemverToStringable;
import org.eclipse.n4js.semver.Semver.SimpleVersion;
import org.eclipse.n4js.semver.Semver.TagVersionRequirement;
import org.eclipse.n4js.semver.Semver.URLCommitISH;
import org.eclipse.n4js.semver.Semver.URLSemver;
import org.eclipse.n4js.semver.Semver.URLVersionRequirement;
import org.eclipse.n4js.semver.Semver.URLVersionSpecifier;
import org.eclipse.n4js.semver.Semver.VersionComparator;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.semver.Semver.VersionPart;
import org.eclipse.n4js.semver.Semver.VersionRange;
import org.eclipse.n4js.semver.Semver.VersionRangeConstraint;
import org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.util.Strings;

import com.google.inject.Singleton;

/** Serializes Semver elements */
@Singleton
public class SemverSerializer implements ISerializer {

	/** @return string representation of {@link URLVersionRequirement} */
	static public String serialize(URLVersionRequirement urlv) {
		if (urlv == null)
			return "";

		String str = "";
		if (urlv.getProtocol() != null)
			str += urlv.getProtocol() + "://";
		str += urlv.getUrl();
		if (urlv.getVersionSpecifier() != null)
			str += "#" + serialize(urlv.getVersionSpecifier());
		return str;
	}

	/** @return string representation of {@link URLSemver} */
	static public String serialize(URLVersionSpecifier urlvs) {
		if (urlvs instanceof URLCommitISH)
			return serialize((URLCommitISH) urlvs);
		if (urlvs instanceof URLSemver)
			return serialize((URLSemver) urlvs);

		return "";
	}

	/** @return string representation of {@link URLSemver} */
	static public String serialize(URLSemver urls) {
		if (urls == null)
			return "";

		String str = "";
		if (urls.isWithSemverTag())
			str += "semver:";
		str += serialize(urls.getSimpleVersion());
		return str;
	}

	/** @return string representation of {@link URLCommitISH} */
	static public String serialize(URLCommitISH urlc) {
		if (urlc == null)
			return "";

		return urlc.getCommitISH();
	}

	/** @return string representation of {@link TagVersionRequirement} */
	static public String serialize(TagVersionRequirement tv) {
		if (tv == null)
			return "";

		return tv.getTagName();
	}

	/** @return string representation of {@link GitHubVersionRequirement} */
	static public String serialize(GitHubVersionRequirement ghv) {
		if (ghv == null)
			return "";

		String str = ghv.getGithubUrl();
		if (ghv.getCommitISH() != null)
			str += "#" + ghv.getCommitISH();
		return str;
	}

	/** @return string representation of {@link LocalPathVersionRequirement} */
	static public String serialize(LocalPathVersionRequirement lpv) {
		if (lpv == null)
			return "";

		return "file:" + lpv.getLocalPath();
	}

	/** @return string representation of {@link VersionRangeSetRequirement} */
	static public String serialize(VersionRangeSetRequirement vrs) {
		if (vrs == null)
			return "";

		String str = "";
		for (int i = 0; i < vrs.getRanges().size(); i++) {
			if (i > 0) {
				str += " || ";
			}
			str += serialize(vrs.getRanges().get(i));
		}
		return str;
	}

	/** @return string representation of {@link HyphenVersionRange} */
	static public String serialize(VersionRange vr) {
		if (vr instanceof HyphenVersionRange)
			return serialize((HyphenVersionRange) vr);
		if (vr instanceof VersionRangeConstraint)
			return serialize((VersionRangeConstraint) vr);

		return "";
	}

	/** @return string representation of {@link HyphenVersionRange} */
	static public String serialize(HyphenVersionRange hvr) {
		if (hvr == null)
			return "";

		String str = serialize(hvr.getFrom()) + " - " + serialize(hvr.getTo());
		return str;
	}

	/** @return string representation of {@link VersionRangeConstraint} */
	static public String serialize(VersionRangeConstraint vrc) {
		if (vrc == null)
			return "";

		String str = "";
		for (int i = 0; i < vrc.getVersionConstraints().size(); i++) {
			if (i > 0) {
				str += " ";
			}
			str += serialize(vrc.getVersionConstraints().get(i));
		}
		return str;
	}

	/** @return string representation of {@link SimpleVersion} */
	static public String serialize(SimpleVersion sv) {
		if (sv == null)
			return "";

		String str = "";
		for (VersionComparator vc : sv.getComparators()) {
			str += serialize(vc);
		}
		if (sv.isWithLetterV()) {
			str += "v";
		}
		str += serialize(sv.getNumber());
		return str;
	}

	/** @return string representation of {@link VersionNumber} */
	static public String serialize(VersionNumber vn) {
		if (vn == null)
			return "";

		String str = serialize(vn.getMajor());
		if (vn.getMinor() != null)
			str += "." + serialize(vn.getMinor());
		if (vn.getPatch() != null)
			str += "." + serialize(vn.getPatch());
		for (VersionPart extend : vn.getExtended()) {
			str += "." + serialize(extend);
		}
		if (vn.getQualifier() != null)
			str += serialize(vn.getQualifier());
		return str;
	}

	/** @return string representation of {@link VersionPart} */
	static public String serialize(VersionPart vp) {
		if (vp == null)
			return "";

		String str = null;
		if (vp.isWildcard()) {
			str = "*";
		} else {
			str = String.valueOf(vp.getNumber());
		}
		return str;
	}

	/** @return string representation of {@link Qualifier} */
	static public String serialize(Qualifier q) {
		if (q == null)
			return "";

		String str = "";
		if (q.getPreRelease() != null)
			str += "-" + serialize(q.getPreRelease());
		if (q.getBuildMetadata() != null)
			str += "+" + serialize(q.getBuildMetadata());
		return str;
	}

	/** @return string representation of {@link QualifierTag} */
	static public String serialize(QualifierTag qt) {
		if (qt == null)
			return "";

		String str = Strings.concat(".", qt.getParts());
		return str;
	}

	/** @return string representation of {@link VersionComparator} */
	static public String serialize(VersionComparator vc) {
		switch (vc) {
		case TILDE:
			return "~";
		case CARET:
			return "^";
		case EQUALS:
			return "=";
		case GREATER:
			return ">";
		case GREATER_EQUALS:
			return ">=";
		case SMALLER:
			return "<";
		case SMALLER_EQUALS:
			return "<=";
		}
		throw new UnsupportedOperationException();
	}

	/** @return string representation of {@link SemverToStringable} */
	static public String serialize(SemverToStringable obj) {
		if (obj == null)
			return "";

		if (obj instanceof URLVersionRequirement) {
			return serialize((URLVersionRequirement) obj);
		}
		if (obj instanceof URLSemver) {
			return serialize((URLSemver) obj);
		}
		if (obj instanceof URLCommitISH) {
			return serialize((URLCommitISH) obj);
		}
		if (obj instanceof TagVersionRequirement) {
			return serialize((TagVersionRequirement) obj);
		}
		if (obj instanceof GitHubVersionRequirement) {
			return serialize((GitHubVersionRequirement) obj);
		}
		if (obj instanceof LocalPathVersionRequirement) {
			return serialize((LocalPathVersionRequirement) obj);
		}
		if (obj instanceof VersionRangeSetRequirement) {
			return serialize((VersionRangeSetRequirement) obj);
		}
		if (obj instanceof HyphenVersionRange) {
			return serialize((HyphenVersionRange) obj);
		}
		if (obj instanceof VersionRangeConstraint) {
			return serialize((VersionRangeConstraint) obj);
		}
		if (obj instanceof SimpleVersion) {
			return serialize((SimpleVersion) obj);
		}
		if (obj instanceof VersionNumber) {
			return serialize((VersionNumber) obj);
		}
		if (obj instanceof VersionPart) {
			return serialize((VersionPart) obj);
		}
		if (obj instanceof Qualifier) {
			return serialize((Qualifier) obj);
		}
		if (obj instanceof QualifierTag) {
			return serialize((QualifierTag) obj);
		}
		throw new UnsupportedOperationException("No serialize method found for type " + obj.getClass().getSimpleName());
	}

	@Override
	public String serialize(EObject obj) {
		if (obj instanceof SemverToStringable) {
			return serialize((SemverToStringable) obj);
		}
		return null;
	}

	@Override
	public String serialize(EObject obj, SaveOptions options) {
		return this.serialize(obj);
	}

	@Override
	public void serialize(EObject obj, Writer writer, SaveOptions options) throws IOException {
		String serializedString = this.serialize(obj);
		writer.write(serializedString);
	}

	@Override
	public ReplaceRegion serializeReplacement(EObject obj, SaveOptions options) {
		throw new UnsupportedOperationException();
	}

}
