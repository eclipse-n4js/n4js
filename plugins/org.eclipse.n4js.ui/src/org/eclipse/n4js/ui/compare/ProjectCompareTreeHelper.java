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
package org.eclipse.n4js.ui.compare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.swt.graphics.Image;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.eclipse.n4js.compare.ProjectCompareHelper;
import org.eclipse.n4js.compare.ProjectCompareResult;
import org.eclipse.n4js.compare.ProjectComparison;
import org.eclipse.n4js.compare.ProjectComparisonEntry;
import org.eclipse.n4js.jsdoc.N4JSDocHelper;
import org.eclipse.n4js.jsdoc.N4JSDocletParser;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.ui.labeling.N4JSLabelProvider;

/**
 */
public class ProjectCompareTreeHelper { // note: public only for testing purposes

	@Inject
	private IN4JSCore n4jsCore;
	@Inject
	private N4JSLabelProvider n4jsLabelProvider;
	@Inject
	private N4JSDocHelper n4jsDocHelper;
	@Inject
	private ProjectCompareHelper projectCompareHelper;

	/**
	 * Convenience method. Simply forwards to {@link N4JSLabelProvider#getImage(Object)}.
	 */
	public Image getImage(Object element) {
		return n4jsLabelProvider.getImage(element);
	}

	/**
	 * Convenience method. Simply forwards to {@link ProjectCompareHelper#createComparison(boolean, List)}.
	 */
	public ProjectComparison createComparison(boolean fullCompare, List<String> addErrorMessagesHere) {
		return projectCompareHelper.createComparison(fullCompare, addErrorMessagesHere);
	}

	/**
	 * Convenience method. Simply forwards to {@link ProjectCompareHelper#compareApiImpl(ProjectComparisonEntry, int)}.
	 */
	public ProjectCompareResult compareApiImpl(ProjectComparisonEntry entry, int implIdx) {
		return projectCompareHelper.compareApiImpl(entry, implIdx);
	}

	/**
	 * Read documentation from the special <code>&#64;api</code> tag in the JSdoc of all types and members in the given
	 * comparison. Documentation from the API source code will always be included; documentation from the implementation
	 * source code will only be included for the given implementation indices. Strings will be stored in the given map
	 * on a per-entry basis.
	 */
	public Map<ProjectComparisonEntry, String> readDocumentation(ProjectComparison comparison, int[] implIndices) {
		final Map<ProjectComparisonEntry, String> result = new HashMap<>();
		final ResourceSet resourceSetForDocRetrieval = n4jsCore.createResourceSet(Optional.absent());
		comparison.getAllEntries().forEach(
				currE -> {
					final String doc = readDocumentation(resourceSetForDocRetrieval, currE, implIndices);
					if (doc != null) {
						result.put(currE, doc);
					}
				});
		return result;
	}

	/**
	 * Read documentation from the special <code>&#64;api</code> tag in the JSdoc of all types and members related to
	 * the given entry. Documentation of the API element and of the implementation elements for all given implementation
	 * IDs will be merged into a single string (separated by '|').
	 *
	 * @return string containing the documentation of the API element and of the implementation elements for all given
	 *         implementation IDs or <code>null</code> if none of the elements has documentation provided.
	 */
	private String readDocumentation(ResourceSet resourceSetForDocRetrieval, ProjectComparisonEntry entry,
			int[] implIndices) {
		if (entry.isElementEntry()) {
			// collect API element and the requested implementation elements
			final List<EObject> elemsToConsider = new ArrayList<>();
			elemsToConsider.add(entry.getElementAPI());
			for (int currImplIdx : implIndices) {
				elemsToConsider.add(entry.getElementImpl(currImplIdx));
			}
			// read documentation from those elements
			final StringBuilder sb = new StringBuilder();
			for (EObject currElem : elemsToConsider) {
				if (currElem != null && !currElem.eIsProxy()) {
					final String docStr = n4jsDocHelper.getDocSafely(resourceSetForDocRetrieval, currElem);
					if (docStr != null) {
						final Doclet dl = n4jsDocHelper.getDoclet(docStr);
						if (dl != null) {
							final String docApiTag = N4JSDocletParser.TAG_API_NOTE.getValue(dl, null);
							if (docApiTag != null && docApiTag.trim().length() > 0) {
								// disallow NL within tag text
								final String sanitized = docApiTag.replaceAll("\\s*\n\\s*", " ").trim();
								if (sb.length() > 0)
									sb.append(" | "); // separate API and implementations
								sb.append(sanitized);
							}
						}
					}
				}
			}
			final String doc = sb.toString().trim();
			if (doc.length() > 0) {
				return doc;
			}
		}
		return null;
	}
}
