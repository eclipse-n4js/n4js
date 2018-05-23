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

import static org.eclipse.n4js.jsdoc2spec.adoc.FileSystem.DIR_ADOC_GEN;
import static org.eclipse.n4js.jsdoc2spec.adoc.FileSystem.SEP;

import java.io.File;
import java.util.Map;

import org.eclipse.n4js.jsdoc2spec.KeyUtils;
import org.eclipse.n4js.jsdoc2spec.SpecInfo;

/**
 * A region in a generated AsciiDoc document containing information from JSDoc regarding requirements.
 */
public class SpecRequirementSection extends SpecSection {
	final String specModuleKey;
	final String specKey;

	SpecRequirementSection(SpecInfo specInfo, File rootDir, RepoRelativePathHolder repoPathHolder) {
		super(specInfo, rootDir);
		this.specModuleKey = KeyUtils.getSpecModuleKey(repoPathHolder, specInfo);
		this.specKey = KeyUtils.getSpecKey(repoPathHolder, specInfo);
	}

	@Override
	public String getSpecModuleKey() {
		return specModuleKey;
	}

	@Override
	public String getSpecKey() {
		return specKey;
	}

	@Override
	public File getFile() {
		String fileName = rootDir + SEP + DIR_ADOC_GEN + SEP;
		fileName += "requirements" + File.separator;
		fileName += specKey + ".adoc";
		return new File(fileName);
	}

	/**
	 * Getter for the requirement id.
	 */
	public String getRequirementID() {
		return specInfo.specElementRef.requirementID;
	}

	@Override
	protected String computeADocText(ADocFactory adocFactory, Map<String, SpecSection> specsByKey) {
		CharSequence regionStr = adocFactory.createSpecRegionString(this, specsByKey);
		return regionStr.toString();
	}

}
