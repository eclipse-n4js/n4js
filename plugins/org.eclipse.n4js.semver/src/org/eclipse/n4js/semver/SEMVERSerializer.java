package org.eclipse.n4js.semver;

import java.io.IOException;
import java.io.Writer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.semver.SEMVER.HyphenVersionRange;
import org.eclipse.n4js.semver.SEMVER.Qualifier;
import org.eclipse.n4js.semver.SEMVER.QualifierTag;
import org.eclipse.n4js.semver.SEMVER.SimpleVersion;
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

	/** @return string representation of {@link VersionRangeSet} */
	public String serialize(VersionRangeSet vrs) {
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
		String str = serialize(hvr.getFrom()) + " - " + serialize(hvr.getTo());
		return str;
	}

	/** @return string representation of {@link VersionRangeConstraint} */
	public String serialize(VersionRangeConstraint vrc) {
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
		String str = "";
		for (VersionComparator vc : sv.getComparators()) {
			str += serialize(vc);
		}
		str += serialize(sv.getNumber());
		return str;
	}

	/** @return string representation of {@link VersionNumber} */
	public String serialize(VersionNumber vn) {
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
		String str = "";
		if (q.getPreRelease() != null)
			str += "-" + serialize(q.getPreRelease());
		if (q.getBuildMetadata() != null)
			str += "+" + serialize(q.getBuildMetadata());
		return str;
	}

	/** @return string representation of {@link QualifierTag} */
	public String serialize(QualifierTag qt) {
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
		throw new UnsupportedOperationException();
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
