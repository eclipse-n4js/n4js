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
package org.eclipse.n4js

import com.google.inject.Inject
import org.eclipse.n4js.naming.N4JSQualifiedNameConverter
import org.eclipse.n4js.packagejson.projectDescription.ProjectReference
import org.eclipse.n4js.tooling.compare.ProjectCompareHelper
import org.eclipse.n4js.tooling.compare.ProjectCompareResult.Status
import org.eclipse.n4js.tooling.compare.ProjectComparison
import org.eclipse.n4js.tooling.compare.ProjectComparisonEntry
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.Type

import static org.junit.Assert.*

/**
 * Helper methods for testing API / implementation compare functionality.
 */
class ApiImplCompareTestHelper {

	@Inject
	private ProjectCompareHelper projectCompareHelper;

	/**
	 * Find entry for type with given name and check number, order and status/description of
	 * its child entries (corresponding to the members of the type).
	 *
	 * @param expectedChildrenNameStatusDescription
	 *             the expected name, compare status, and compare description of each child in the following format:
	 *             <pre>
	 *             expected name of child  ->  expected comparison status  ->  expected comparison description
	 *             </pre>
	 */
	public def void assertCorrectChildEntries(
		ProjectComparison comparison,
		String fqnOfModule, String nameOfType,
		Pair<Pair<String,Status>,String>... expectedChildrenNameStatusDescription
	) {
		val fqnOfType = fqnOfModule + N4JSQualifiedNameConverter.DELIMITER + nameOfType;
		val entryForType = comparison.findEntryForType(fqnOfType);
		assertNotNull("cannot find entry for type "+fqnOfType, entryForType);
		val childEntriesOfType = entryForType.children;
		val expectedChildCount = expectedChildrenNameStatusDescription.size;
		val expectedChildNames = expectedChildrenNameStatusDescription.map[key.key].toList
		assertEquals(nameOfType, entryForType.elementNameForEntry);
		assertEquals(
			"expected exactly "+expectedChildCount+" child entries for "+nameOfType,
			expectedChildCount,
			childEntriesOfType.size);
		assertEquals(
			"child entries of "+nameOfType+" have wrong name(s) or incorrect order",
			expectedChildNames,
			childEntriesOfType.map[elementNameForEntry].toList)
		for(var idx=0;idx<childEntriesOfType.size;idx++) {
			val currChildEntry = childEntriesOfType.get(idx);
			val expectedStatus = expectedChildrenNameStatusDescription.get(idx).key.value;
			val expectedDescription = expectedChildrenNameStatusDescription.get(idx).value;
			currChildEntry.assertDiff(expectedStatus, expectedDescription);
		}
	}

	public def assertCorrectTypeEntry(
		ProjectComparison comparision,
		String fqnOfModule,
		Pair<Pair<String,Status>,String>... expectedTypeStatusDescriptions
	) {
		for (var idx=0;idx<expectedTypeStatusDescriptions.size;idx++) {
			val elementName = expectedTypeStatusDescriptions.get(idx).key.key
			val elementStatus = expectedTypeStatusDescriptions.get(idx).key.value;
			val elementDescription = expectedTypeStatusDescriptions.get(idx).value;

			val fqnOfType = fqnOfModule + N4JSQualifiedNameConverter.DELIMITER + elementName;
			val entryForType = comparision.findEntryForType(fqnOfType);

			entryForType.assertDiff(elementStatus, elementDescription);
		}
	}

	private def void assertDiff(ProjectComparisonEntry entry, Status status, String description) {
		assertNotNull(entry);
		assertSame("wrong status", status, entry.statusForFirstImplementation);
		assertEquals("wrong description", description, entry.descriptionForFirstImplementation);
	}

	/**
	 * Search the given comparison for an entry with an API- or implementation-side element
	 * with the given fully qualified name.
	 */
	public def ProjectComparisonEntry findEntryForType(ProjectComparison comparison, String fqn) {
		comparison.allEntries.filter[entry|entry.elementFqnForEntry==fqn].findFirst.orElse(null)
	}

	public def String getElementNameForEntry(ProjectComparisonEntry entry) {
		entry.allElements.filter(IdentifiableElement).map[name].head
	}
	public def String getElementFqnForEntry(ProjectComparisonEntry entry) {
		entry.allElements.filter(Type).map[containingModule?.qualifiedName + N4JSQualifiedNameConverter.DELIMITER + name].head
	}
	private def Status getStatusForFirstImplementation(ProjectComparisonEntry entry) {
		// note 0 in next line: always returns status of comparing API with implementation at index 0
		projectCompareHelper.compareApiImpl(entry,0).status
	}
	private def String getDescriptionForFirstImplementation(ProjectComparisonEntry entry) {
		// note 0 in next line: always returns description of comparing API with implementation at index 0
		projectCompareHelper.compareApiImpl(entry,0).description
	}

	public def ProjectReference createProjectReference(String projectNameOfTargetProject) {
		return new ProjectReference(projectNameOfTargetProject);
	}
}
