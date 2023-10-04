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
package org.eclipse.n4js;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.List;

import org.eclipse.n4js.naming.N4JSQualifiedNameConverter;
import org.eclipse.n4js.packagejson.projectDescription.ProjectReference;
import org.eclipse.n4js.tooling.compare.ProjectCompareHelper;
import org.eclipse.n4js.tooling.compare.ProjectCompareResult.Status;
import org.eclipse.n4js.tooling.compare.ProjectComparison;
import org.eclipse.n4js.tooling.compare.ProjectComparisonEntry;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.inject.Inject;

/**
 * Helper methods for testing API / implementation compare functionality.
 */
public class ApiImplCompareTestHelper {

	@Inject
	private ProjectCompareHelper projectCompareHelper;

	/**
	 * Find entry for type with given name and check number, order and status/description of its child entries
	 * (corresponding to the members of the type).
	 *
	 * @param expectedChildrenNameStatusDescription
	 *            the expected name, compare status, and compare description of each child in the following format:
	 *
	 *            <pre>
	 * expected name of child -> expected comparison status -> expected comparison description
	 *            </pre>
	 */
	@SuppressWarnings("unchecked")
	public void assertCorrectChildEntries(
			ProjectComparison comparison,
			String fqnOfModule, String nameOfType,
			Pair<Pair<String, Status>, String>... expectedChildrenNameStatusDescription) {

		String fqnOfType = fqnOfModule + N4JSQualifiedNameConverter.DELIMITER + nameOfType;
		ProjectComparisonEntry entryForType = findEntryForType(comparison, fqnOfType);
		assertNotNull("cannot find entry for type " + fqnOfType, entryForType);
		ProjectComparisonEntry[] childEntriesOfType = entryForType.getChildren();
		int expectedChildCount = expectedChildrenNameStatusDescription.length;
		List<String> expectedChildNames = toList(
				map(Arrays.asList(expectedChildrenNameStatusDescription), key -> key.getKey().getKey()));
		List<String> list = toList(map(Arrays.asList(childEntriesOfType), entry -> getElementNameForEntry(entry)));

		assertEquals(nameOfType, getElementNameForEntry(entryForType));
		assertEquals(
				"expected exactly " + expectedChildCount + " child entries for " + nameOfType,
				expectedChildCount,
				childEntriesOfType.length);
		assertEquals(
				"child entries of " + nameOfType + " have wrong name(s) or incorrect order",
				expectedChildNames,
				list);

		for (var idx = 0; idx < childEntriesOfType.length; idx++) {
			ProjectComparisonEntry currChildEntry = childEntriesOfType[idx];
			Status expectedStatus = expectedChildrenNameStatusDescription[idx].getKey().getValue();
			String expectedDescription = expectedChildrenNameStatusDescription[idx].getValue();
			assertDiff(currChildEntry, expectedStatus, expectedDescription);
		}
	}

	/***/
	@SuppressWarnings("unchecked")
	public void assertCorrectTypeEntry(
			ProjectComparison comparision,
			String fqnOfModule,
			Pair<Pair<String, Status>, String>... expectedTypeStatusDescriptions) {

		for (var idx = 0; idx < expectedTypeStatusDescriptions.length; idx++) {
			String elementName = expectedTypeStatusDescriptions[idx].getKey().getKey();
			Status elementStatus = expectedTypeStatusDescriptions[idx].getKey().getValue();
			String elementDescription = expectedTypeStatusDescriptions[idx].getValue();

			String fqnOfType = fqnOfModule + N4JSQualifiedNameConverter.DELIMITER + elementName;
			ProjectComparisonEntry entryForType = findEntryForType(comparision, fqnOfType);

			assertDiff(entryForType, elementStatus, elementDescription);
		}
	}

	private void assertDiff(ProjectComparisonEntry entry, Status status, String description) {
		assertNotNull(entry);
		assertSame("wrong status", status, getStatusForFirstImplementation(entry));
		assertEquals("wrong description", description, getDescriptionForFirstImplementation(entry));
	}

	/**
	 * Search the given comparison for an entry with an API- or implementation-side element with the given fully
	 * qualified name.
	 */
	public ProjectComparisonEntry findEntryForType(ProjectComparison comparison, String fqn) {
		List<ProjectComparisonEntry> entries = comparison.getAllEntries()
				.filter(entry -> getElementFqnForEntry(entry) == fqn).toList();
		if (entries.isEmpty()) {
			return null;
		}
		return entries.get(0);
	}

	/***/
	public String getElementNameForEntry(ProjectComparisonEntry entry) {
		Iterable<IdentifiableElement> idElems = filter(Arrays.asList(entry.getAllElements()),
				IdentifiableElement.class);
		return map(idElems, idElem -> idElem.getName()).iterator().next();
	}

	/***/
	public String getElementFqnForEntry(ProjectComparisonEntry entry) {
		Iterable<Type> types = filter(Arrays.asList(entry.getAllElements()), Type.class);
		Iterable<String> names = map(types,
				type -> (type.getContainingModule() == null ? "" : type.getContainingModule().getQualifiedName())
						+ N4JSQualifiedNameConverter.DELIMITER + type.getName());

		return names.iterator().next();
	}

	private Status getStatusForFirstImplementation(ProjectComparisonEntry entry) {
		// note 0 in next line: always returns status of comparing API with implementation at index 0
		return projectCompareHelper.compareApiImpl(entry, 0).status;
	}

	private String getDescriptionForFirstImplementation(ProjectComparisonEntry entry) {
		// note 0 in next line: always returns description of comparing API with implementation at index 0
		return projectCompareHelper.compareApiImpl(entry, 0).description;
	}

	/***/
	public ProjectReference createProjectReference(String projectNameOfTargetProject) {
		return new ProjectReference(projectNameOfTargetProject);
	}
}
