/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.jsdoc2spec.adoc;

import java.io.File;
import java.util.Map;
import java.util.SortedSet;

import org.eclipse.n4js.jsdoc2spec.SpecFile;
import org.eclipse.n4js.jsdoc2spec.SpecInfo;
import org.eclipse.n4js.jsdoc2spec.SpecTestInfo;
import org.eclipse.n4js.ts.types.TMember;

/**
 * {@link SpecSection}s contain information which later is combined into {@link SpecFile}s. While
 * {@link SpecModuleFile}s can contain more than one SpecSection, other SpecFiles only contain one SpecSection.
 */
abstract public class SpecSection implements Comparable<SpecSection> {
	final SpecInfo specInfo;
	final File rootDir;
	private String adocText;
	private int changeLineCount;
	private final SortedSet<SpecTestInfo> testsForType;
	private final Map<TMember, SortedSet<SpecTestInfo>> testsForInheritedMembers;
	private SortedSet<SpecTestInfo> testsForMember;

	/**
	 * Getter for the spec file key.
	 */
	abstract public String getSpecModuleKey();

	/**
	 * Getter for the spec key.
	 */
	abstract public String getSpecKey();

	/**
	 * Returns file of this change entry with an absolute path.
	 */
	abstract public File getFile();

	/**
	 * Returns the AsciiDoc for this <code>SpecRegionEntry</code>.
	 */
	abstract protected String computeADocText(ADocFactory adocFactory, Map<String, SpecSection> specsByKey);

	SpecSection(SpecInfo specInfo, File rootDir) {
		this.specInfo = specInfo;
		this.rootDir = rootDir;
		if (specInfo != null) {
			this.testsForType = specInfo.getTestsForType();
			this.testsForInheritedMembers = specInfo.getTestsForInheritedMembers();
		} else {
			this.testsForType = null;
			this.testsForInheritedMembers = null;
		}
	}

	/**
	 * Getter for specInfo.
	 */
	public SpecInfo getSpecInfo() {
		return specInfo;
	}

	/**
	 * Generate ADoc text for later use when creating SpecFiles.
	 */
	public String generateADocText(ADocFactory adocFactory, Map<String, SpecSection> specsByKey) {
		if (adocText == null) {
			adocText = computeADocText(adocFactory, specsByKey) + "\n";
			changeLineCount = StringCountUtils.countNewLines(adocText);
		}
		return adocText;
	}

	/**
	 * Getter for ADoc text content.
	 */
	public String getGeneratedADocText() {
		return adocText;
	}

	/**
	 * Getter for the line count of the ADoc text content.
	 */
	public int getGeneratedLineCount() {
		return changeLineCount;
	}

	/**
	 * Setter for ADoc text content.
	 */
	public void setADocText(String adocText) {
		this.adocText = adocText;
	}

	/**
	 * Getter for type tests.
	 */
	public SortedSet<SpecTestInfo> getTestInfosForType() {
		return testsForType;
	}

	/**
	 * Getter for inherited member tests.
	 */
	public Map<TMember, SortedSet<SpecTestInfo>> getTestInfosForInheritedMember() {
		return testsForInheritedMembers;
	}

	/**
	 * Setter for member tests
	 */
	public void setTestInfosForMember(SortedSet<SpecTestInfo> testsForMember) {
		this.testsForMember = testsForMember;
	}

	/**
	 * Getter for member tests
	 */
	public SortedSet<SpecTestInfo> getTestInfosForMember() {
		return testsForMember;
	}

	@Override
	public int hashCode() {
		return getSpecKey().hashCode();
	}

	@Override
	public int compareTo(SpecSection o) {
		return getSpecKey().compareTo(o.getSpecKey());
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SpecSection))
			return false;

		return this.getSpecKey().equals(((SpecSection) obj).getSpecKey());
	}

	@Override
	public String toString() {
		return getSpecKey();
	}
}
