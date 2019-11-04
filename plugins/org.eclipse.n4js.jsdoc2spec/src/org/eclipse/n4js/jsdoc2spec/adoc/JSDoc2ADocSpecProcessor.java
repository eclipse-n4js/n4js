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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.n4js.jsdoc2spec.JSDoc2SpecProcessor;
import org.eclipse.n4js.jsdoc2spec.KeyUtils;
import org.eclipse.n4js.jsdoc2spec.SpecElementRef;
import org.eclipse.n4js.jsdoc2spec.SpecFile;
import org.eclipse.n4js.jsdoc2spec.SpecInfo;
import org.eclipse.n4js.jsdoc2spec.SubMonitorMsg;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TN4Classifier;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.util.MemberList;
import org.eclipse.n4js.utils.ContainerTypesHelper.MemberCollector;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.inject.Inject;

/**
 * Main controller creating AsciiDoc files from N4JS-projects.
 *
 * <p>
 * Needs to be injected.
 *
 * <p>
 * Jira: IDE-2336
 */
public class JSDoc2ADocSpecProcessor extends JSDoc2SpecProcessor {
	private File rootDir;

	@Inject
	private ADocFactory adocFactory;

	@Inject
	private RepoRelativePathHolder repoPathHolder;

	/**
	 * This method is not needed in the processor for AsciiDoc
	 */
	@Override
	public void setRootDir(File newRootDir) {
		this.rootDir = newRootDir;
	}

	/**
	 * Computes new AsciiDoc files and compares them to old AsciiDoc files.
	 *
	 * @throws IOException
	 *             thrown when problems occur regarding existing adoc files
	 * @throws InterruptedException
	 *             thrown when user cancels operation
	 *
	 * @returns map with changed files as keys and new content.
	 */
	@Override
	public Collection<SpecFile> computeUpdates(Collection<SpecInfo> specInfos, SubMonitorMsg monitor)
			throws IOException, InterruptedException {

		Collection<SpecFile> newSpecSet = createNewEntries(specInfos, monitor);
		List<SpecFile> indexChangeFiles = IndicesCreator.createIndexFiles(rootDir, newSpecSet);
		newSpecSet.addAll(indexChangeFiles);
		Collection<SpecFile> changeSpecSet = filterChangedFiles(newSpecSet);

		return changeSpecSet;
	}

	/**
	 * This method iterates through the entries of {@code typesByName} and creates {@link SpecFile}s from them. The
	 * entries of {@code typeByName} contain e.g. requirements, or N4JS types like classes.
	 */
	private Collection<SpecFile> createNewEntries(Collection<SpecInfo> specInfos, SubMonitorMsg monitor)
			throws InterruptedException {

		TreeMap<String, SpecFile> specChangeSet = new TreeMap<>();
		Map<String, SpecSection> specsByKey = new HashMap<>();

		SubMonitorMsg sub = monitor.convert(specInfos.size());
		for (SpecInfo specInfo : specInfos) {
			SpecElementRef specElementRef = specInfo.specElementRef;

			String specKey = KeyUtils.getSpecKey(repoPathHolder, specInfo);
			if (specsByKey.containsKey(specKey))
				throw new RuntimeException("Unexpected!");

			// create spec regions for every requirement and source element
			SpecSection specSection = null;
			if (specElementRef.requirementID != null) {
				specSection = new SpecRequirementSection(specInfo, rootDir, repoPathHolder);
				specSection.generateADocText(adocFactory, specsByKey);
				specsByKey.put(specSection.getSpecKey(), specSection);

				SpecRequirementFile srf = new SpecRequirementFile((SpecRequirementSection) specSection);
				specChangeSet.put(specSection.getSpecModuleKey(), srf);
			}
			if (specElementRef.identifiableElement != null) {
				specSection = new SpecIdentifiableElementSection(specInfo, rootDir, repoPathHolder);
				SpecIdentifiableElementSection sies = (SpecIdentifiableElementSection) specSection;
				insertIntoSpecModuleFile(specChangeSet, specsByKey, sies);
				addMembers(sies.specInfo, specsByKey, specChangeSet);
			}

			sub.worked(1);
			checkUserCanceled(sub);
		}

		Set<SpecFile> changeEntries = new HashSet<>();
		changeEntries.addAll(specChangeSet.values());
		return changeEntries;
	}

	/**
	 * This method iterates through all members of a N4JS type and creates corresponding {@link SpecFile}s from them.
	 * During this process, member tests are linked if available.
	 */
	private void addMembers(SpecInfo specInfo, Map<String, SpecSection> specsByKey,
			TreeMap<String, SpecFile> specChangeSet) {

		IdentifiableElement element = specInfo.specElementRef.identifiableElement;
		if (element instanceof TN4Classifier) {

			addMembers(specInfo, (TN4Classifier) element, specsByKey, specChangeSet);

			Type pfaElement = specInfo.specElementRef.polyfillAware;
			if (pfaElement instanceof TN4Classifier) {
				addMembers(specInfo, (TN4Classifier) pfaElement, specsByKey, specChangeSet);
			}
		}
	}

	/**
	 * This method iterates through all members of a N4JS type and creates corresponding {@link SpecFile}s from them.
	 * During this process, member tests are linked if available.
	 */
	private void addMembers(SpecInfo specInfo, TN4Classifier classifier, Map<String, SpecSection> specsByKey,
			TreeMap<String, SpecFile> specChangeSet) {

		MemberCollector memberCollector = containerTypesHelper.fromContext(classifier);
		MemberList<TMember> memberList = memberCollector.allMembers(classifier, false, false, false);
		Stream<TMember> nonPrivMembs = memberList.stream().filter(isNonPrivate());

		// add spec region for new members
		for (TMember member : (Iterable<TMember>) nonPrivMembs::iterator) {

			SpecSection specSection = new SpecIdentifiableElementSection(specInfo, member, rootDir, repoPathHolder);
			if (!specsByKey.containsKey(specSection.getSpecKey())) {
				specSection.setTestInfosForMember(specInfo.getTestsForMember(member));
				insertIntoSpecModuleFile(specChangeSet, specsByKey, (SpecIdentifiableElementSection) specSection);
			}
		}
	}

	private void insertIntoSpecModuleFile(TreeMap<String, SpecFile> specFiles,
			Map<String, SpecSection> specsByKey, SpecIdentifiableElementSection specSection) {

		String specKey = specSection.getSpecKey();
		specsByKey.put(specKey, specSection);
		String moduleKey = specSection.getSpecModuleKey();
		if (!specFiles.containsKey(moduleKey)) {
			SpecModuleFile scf = new SpecModuleFile(specSection.getFile());
			specFiles.put(moduleKey, scf);
		}
		SpecFile scf = specFiles.get(moduleKey);
		specSection.generateADocText(adocFactory, specsByKey);
		scf.add(specSection);
	}

	private Predicate<? super TMember> isNonPrivate() {
		return m -> m.getMemberAccessModifier() != MemberAccessModifier.PRIVATE;
	}

	/**
	 * The wizard will only show files whose content changed. Also, only these files will be written to the file system.
	 * The difference is computed by comparing both of the file's contents as strings.
	 */
	private Collection<SpecFile> filterChangedFiles(Collection<SpecFile> newSpecSet) throws IOException {
		List<SpecFile> changedFiles = new ArrayList<>();

		for (SpecFile scf : newSpecSet) {
			boolean fileExists = scf.getFile().exists();
			boolean fileChanged = !fileExists;

			if (fileExists) {
				String oldContent = Files.asCharSource(scf.getFile(), Charsets.UTF_8).read().replace("\r\n", "\n");
				String newContent = scf.getNewContent();
				if (!oldContent.equals(newContent))
					fileChanged = true;
			}

			if (fileChanged)
				changedFiles.add(scf);
		}

		return changedFiles;
	}

	private void checkUserCanceled(IProgressMonitor monitor) throws InterruptedException {
		if (monitor.isCanceled() || Thread.interrupted())
			throw new InterruptedException("User canceled Operation.");
	}

}
