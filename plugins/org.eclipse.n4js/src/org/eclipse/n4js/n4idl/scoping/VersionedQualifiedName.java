/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4idl.scoping;

import java.util.Comparator;

import org.eclipse.xtext.naming.QualifiedName;

/**
 *
 */
public class VersionedQualifiedName extends QualifiedName {

	/**
	 * Creates a new {@link VersionedQualifiedName} with the given version and segments.
	 */
	public static VersionedQualifiedName create(int declaredVersion, String... segments) {
		return new VersionedQualifiedName(declaredVersion, segments);
	}

	private final int declaredVersion;

	private VersionedQualifiedName(int declaredVersion, String... segments) {
		super(segments);

		this.declaredVersion = declaredVersion;
	}

	@Override
	protected int compareTo(QualifiedName qualifiedName, boolean ignoreCase) {
		final int superResult = super.compareTo(qualifiedName, ignoreCase);

		if (qualifiedName instanceof VersionedQualifiedName && superResult == 0) {
			return Comparator.comparingInt(vqn -> ((VersionedQualifiedName) vqn).declaredVersion).compare(this,
					qualifiedName);
		}

		return superResult;
	}

	@Override
	public boolean equals(Object obj) {
		final boolean superResult = super.equals(obj);

		// if already different in usual sense
		if (superResult == false) {
			return false;
		}

		// if comparing to versioned qualified named
		if (obj instanceof VersionedQualifiedName) {
			return ((VersionedQualifiedName) obj).declaredVersion == this.declaredVersion;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		// make sure declaredVersion is considered for hashing
		// return Objects.hash(super.hashCode(), this.declaredVersion);
		return super.hashCode();
	}

	/**
	 * Returns an unversioned copy of this {@link VersionedQualifiedName}.
	 */
	public QualifiedName toUnversionedName() {
		return QualifiedName.create(this.getSegments().toArray(new String[] {}));
	}
}
