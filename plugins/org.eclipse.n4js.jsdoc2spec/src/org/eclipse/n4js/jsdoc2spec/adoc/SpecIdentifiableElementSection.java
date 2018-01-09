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
import static org.eclipse.n4js.jsdoc2spec.adoc.FileSystem.DIR_MODULES;
import static org.eclipse.n4js.jsdoc2spec.adoc.FileSystem.SEP;

import java.io.File;
import java.util.Map;

import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.eclipse.n4js.jsdoc2spec.RepoRelativePath;
import org.eclipse.n4js.jsdoc2spec.SpecInfo;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TMember;

/**
 * A region in a AsciiDoc document containing specification information stemming from JSDoc regarding identifiable code
 * elements.
 */
public class SpecIdentifiableElementSection extends SpecSection {
	final IdentifiableElement idElement;
	final String specModuleKey;
	final String specKey;
	final SourceEntry sourceEntry;

	private Doclet doclet;

	SpecIdentifiableElementSection(SpecInfo specInfo, File rootDir, RepoRelativePathHolder repoPathHolder) {
		super(specInfo, rootDir);
		this.idElement = specInfo.specElementRef.identifiableElement;
		this.specModuleKey = KeyUtils.getSpecModuleKey(repoPathHolder, specInfo);
		this.sourceEntry = getSourceEntry(repoPathHolder);
		this.specKey = sourceEntry.toPQN();
	}

	SpecIdentifiableElementSection(SpecInfo specInfo, TMember idElement, File rootDir,
			RepoRelativePathHolder repoPathHolder) {
		super(specInfo, rootDir);
		this.idElement = idElement;
		this.specModuleKey = KeyUtils.getSpecModuleKey(repoPathHolder, specInfo);
		this.sourceEntry = getSourceEntry(repoPathHolder);
		this.specKey = sourceEntry.toPQN();
	}

	/**
	 * This constructor is for X-PECT-tests only.
	 */
	public SpecIdentifiableElementSection(IdentifiableElement element, RepoRelativePathHolder repoPathHolder) {
		super(null, null);
		this.idElement = element;
		this.specModuleKey = element.getName();
		this.specKey = KeyUtils.getSpecKey(repoPathHolder, idElement);
		this.sourceEntry = getSourceEntry(repoPathHolder);
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
		fileName += DIR_MODULES + SEP;
		fileName += sourceEntry.adocPath;
		return new File(fileName);
	}

	/**
	 * Getter for the identifiable element.
	 */
	public IdentifiableElement getIdentifiableElement() {
		return idElement;
	}

	/**
	 * Returns a created {@link SourceEntry} that offers all path information for the element of this code region.
	 */
	public SourceEntry getSourceEntry() {
		return sourceEntry;
	}

	private SourceEntry getSourceEntry(RepoRelativePathHolder repoPathHolder) {
		RepoRelativePath rrp = repoPathHolder.get(idElement);
		SourceEntry se = SourceEntryFactory.create(rrp, idElement);
		return se;
	}

	/**
	 * Getter for a {@link Doclet} instance which is used by the {@link ADocSerializer}.
	 */
	public void setDoclet(Doclet doclet) {
		this.doclet = doclet;
	}

	/**
	 * Setter for a {@link Doclet} instance which is used by the {@link ADocSerializer}.
	 */
	public Doclet getDoclet() {
		return doclet;
	}

	@Override
	protected String computeADocText(ADocFactory adocFactory, Map<String, SpecSection> specsByKey) {
		CharSequence regionStr = adocFactory.createSpecRegionString(this, specsByKey);
		return regionStr.toString();
	}

}
