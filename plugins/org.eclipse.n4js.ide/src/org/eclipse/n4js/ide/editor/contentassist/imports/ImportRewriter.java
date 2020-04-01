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
package org.eclipse.n4js.ide.editor.contentassist.imports;

import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.ide.server.imports.ImportOrganizer.ImportRef;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.utils.Lazy;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.util.TextRegion;

import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * Obtain an import rewriter for a resource and add used types optionally along with aliases. It uses the serializer to
 * rewrite the import declarations.
 *
 * Instances of the {@link ImportChanges} are obtained from the {@link #create(String, Resource, NameAndAlias)}.
 *
 * TODO: review if that is stable enough, e.g. with broken import declarations.
 */
public class ImportRewriter {

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	@Inject
	private IValueConverterService valueConverters;

	@Inject
	private N4JSGrammarAccess grammarAccess;

	@Inject
	private ImportRegionHelper importRegionHelper;

	@Inject
	private ImportSpacerUserPreferenceHelper spacerPreference;

	/**
	 * Creates a new import rewriter.
	 */
	public ImportChanges create(String lineDelimiter, Resource resource, NameAndAlias nameAndAlias) {
		ImportChanges result = new ImportChanges(lineDelimiter, resource, nameAndAlias);
		return result;
	}

	/** Constructor */
	public class ImportChanges {
		private final String lineDelimiter;
		private final Script script;
		private final Set<NameAndAlias> requestedImports;
		private final Lazy<String> lazySpacer;

		private ImportChanges(String lineDelimiter, Resource resource, NameAndAlias nameAndAlias) {
			this.lineDelimiter = lineDelimiter;
			this.script = (Script) resource.getContents().get(0);
			this.requestedImports = Sets.newLinkedHashSet();
			requestedImports.add(nameAndAlias);
			this.lazySpacer = new Lazy<>(() -> spacerPreference.getSpacingPreference(resource));
		}

		/**
		 * @param regions
		 *            the accumulator for the changes
		 */
		public void addReplaceRegions(Collection<ReplaceRegion> regions, Collection<ImportRef> importRefs) {
			int insertionOffset = importRegionHelper.findInsertionOffset(script);
			for (NameAndAlias requested : requestedImports) {
				addNewImportDeclaration(requested.getName(), requested.getAlias(), requested.getProjectName(),
						insertionOffset, regions, importRefs);
			}
		}

		private void addNewImportDeclaration(QualifiedName qualifiedName,
				String optionalAlias, String projectName, int insertionOffset,
				Collection<ReplaceRegion> replaceRegions, Collection<ImportRef> importRefs) {

			QualifiedName moduleName = qualifiedName.skipLast(1);

			String spacer = lazySpacer.get();
			String syntacticModuleName = syntacticModuleName(moduleName);

			String importSpec = (insertionOffset != 0 ? lineDelimiter : "") + "import ";

			if (!N4JSLanguageUtils.isDefaultExport(qualifiedName)) { // not an 'default' export
				importSpec = importSpec + "{" + spacer + qualifiedName.getLastSegment();
				if (optionalAlias != null) {
					importSpec = importSpec + " as ";
					importSpec = importSpec + optionalAlias;
				}
				importSpec = importSpec + spacer + "}";
			} else { // import default exported element
				if (optionalAlias == null) {
					importSpec = importSpec + N4JSLanguageUtils.lastSegmentOrDefaultHost(qualifiedName);
				} else {
					importSpec = importSpec + optionalAlias;
				}
			}

			String insertedCode = importSpec + " from " + syntacticModuleName + ";"
					+ (insertionOffset != 0 ? "" : lineDelimiter);
			ITextRegion region = new TextRegion(insertionOffset, 0);
			replaceRegions.add(new XReplaceRegion(region, insertedCode));

			ImportRef importRef = new ImportRef(qualifiedName.getLastSegment(), optionalAlias, projectName, moduleName);
			importRefs.add(importRef);
		}

		/** compute the syntactic string representation of the moduleName */
		private String syntacticModuleName(QualifiedName moduleName) {
			String syntacticModuleName = valueConverters.toString(
					qualifiedNameConverter.toString(moduleName),
					grammarAccess.getModuleSpecifierRule().getName());
			return syntacticModuleName;
		}
	}
}
