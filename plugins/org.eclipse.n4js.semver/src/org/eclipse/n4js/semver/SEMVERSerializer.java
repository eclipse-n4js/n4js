package org.eclipse.n4js.semver;

import java.io.IOException;
import java.io.Writer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.semver.SEMVER.GitHubVersion;
import org.eclipse.n4js.semver.SEMVER.HyphenVersionRange;
import org.eclipse.n4js.semver.SEMVER.LocalPathVersion;
import org.eclipse.n4js.semver.SEMVER.Qualifier;
import org.eclipse.n4js.semver.SEMVER.QualifierTag;
import org.eclipse.n4js.semver.SEMVER.SimpleVersion;
import org.eclipse.n4js.semver.SEMVER.TagVersion;
import org.eclipse.n4js.semver.SEMVER.URLCommitISH;
import org.eclipse.n4js.semver.SEMVER.URLSemver;
import org.eclipse.n4js.semver.SEMVER.URLVersion;
import org.eclipse.n4js.semver.SEMVER.VersionComparator;
import org.eclipse.n4js.semver.SEMVER.VersionNumber;
import org.eclipse.n4js.semver.SEMVER.VersionPart;
import org.eclipse.n4js.semver.SEMVER.VersionRangeConstraint;
import org.eclipse.n4js.semver.SEMVER.VersionRangeSet;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.util.Strings;

/** Serializes SEMVER elements */
public class SEMVERSerializer implements ISerializer {

	/** Serializes the given {@link EObject} */
	static public String toString(EObject eobj) {
		return new SEMVERSerializer().serialize(eobj);
	}

	/** @return string representation of {@link URLVersion} */
	public String serialize(URLVersion urlv) {
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
	public String serialize(URLSemver urls) {
		if (urls == null)
			return "";

		return serialize(urls.getSimpleVersion());
	}

	/** @return string representation of {@link URLCommitISH} */
	public String serialize(URLCommitISH urlc) {
		if (urlc == null)
			return "";

		return urlc.getCommitISH();
	}

	/** @return string representation of {@link TagVersion} */
	public String serialize(TagVersion tv) {
		if (tv == null)
			return "";

		return tv.getTagName();
	}

	/** @return string representation of {@link GitHubVersion} */
	public String serialize(GitHubVersion ghv) {
		if (ghv == null)
			return "";

		String str = ghv.getGithubUrl();
		if (ghv.getCommitISH() != null)
			str += "#" + ghv.getCommitISH();
		return str;
	}

	/** @return string representation of {@link LocalPathVersion} */
	public String serialize(LocalPathVersion lpv) {
		if (lpv == null)
			return "";

		return "file:" + lpv.getLocalPath();
	}

	/** @return string representation of {@link VersionRangeSet} */
	public String serialize(VersionRangeSet vrs) {
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
	public String serialize(HyphenVersionRange hvr) {
		if (hvr == null)
			return "";

		String str = serialize(hvr.getFrom()) + " - " + serialize(hvr.getTo());
		return str;
	}

	/** @return string representation of {@link VersionRangeConstraint} */
	public String serialize(VersionRangeConstraint vrc) {
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
	public String serialize(SimpleVersion sv) {
		if (sv == null)
			return "";

		String str = "";
		for (VersionComparator vc : sv.getComparators()) {
			str += serialize(vc);
		}
		str += serialize(sv.getNumber());
		return str;
	}

	/** @return string representation of {@link VersionNumber} */
	public String serialize(VersionNumber vn) {
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
	public String serialize(VersionPart vp) {
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
	public String serialize(Qualifier q) {
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
	public String serialize(QualifierTag qt) {
		if (qt == null)
			return "";

		String str = Strings.concat(".", qt.getParts());
		return str;
	}

	/** @return string representation of {@link VersionComparator} */
	public String serialize(VersionComparator vc) {
		switch (vc) {
		case VERSION:
			return "v";
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

	@Override
	public String serialize(EObject obj) {
		if (obj == null)
			return "";

		if (obj instanceof URLVersion) {
			return serialize((URLVersion) obj);
		}
		if (obj instanceof URLSemver) {
			return serialize((URLSemver) obj);
		}
		if (obj instanceof URLCommitISH) {
			return serialize((URLCommitISH) obj);
		}
		if (obj instanceof TagVersion) {
			return serialize((TagVersion) obj);
		}
		if (obj instanceof GitHubVersion) {
			return serialize((GitHubVersion) obj);
		}
		if (obj instanceof LocalPathVersion) {
			return serialize((LocalPathVersion) obj);
		}
		if (obj instanceof VersionRangeSet) {
			return serialize((VersionRangeSet) obj);
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
