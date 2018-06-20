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
package org.eclipse.n4js.utils

import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtend.lib.annotations.EqualsHashCode
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.junit.Test

import static org.junit.Assert.*

/**
 * Test for {@link DiffBuilder}.
 */
class DiffBuilderTest {

	@Test
	def void testAddExistingExpectZeroAdded() {
		val builder = new NodeDiffBuilder(newInput(#[1, 2, 3]))
			.add(newNode(1));

		assertTrue('''Expected empty added items. Was «builder.addedItems»''', builder.addedItems.empty);
	}

	@Test
	def void testAddNewThenRemoveExpectZeroAdded() {
		val builder = new NodeDiffBuilder(newInput(#[1, 2, 3]))
			.add(newNode(36))
			.delete(newNode(36))

		assertTrue('''Expected empty added items. Was «builder.addedItems»''', builder.addedItems.empty);
	}

	@Test
	def void testAddNewThenRemoveExpectZeroRemoved() {
		val builder = new NodeDiffBuilder(newInput(#[1, 2, 3]))
			.add(newNode(36))
			.delete(newNode(36))

		assertTrue('''Expected empty deleted items. Was «builder.deletedItems»''', builder.deletedItems.empty);
	}

	@Test
	def void testEditExistingThenRemoveExpectZeroEdited() {
		val builder = new NodeDiffBuilder(newInput(#[1, 2, 3]))
			.edit(newNode(2), newNode(36))
			.delete(newNode(36))

		assertTrue('''Expected empty edited items. Was «builder.editedItems»''', builder.editedItems.empty);
	}

	@Test
	def void testEditExistingThenRemoveExpectOriginalIsDeleted() {
		val builder = new NodeDiffBuilder(newInput(#[1, 2, 3]))
			.edit(newNode(2), newNode(36))
			.delete(newNode(36))

		assertTrue('''Expected «newNode(2)» among deleted items. Was «builder.deletedItems»''', builder.deletedItems.contains(newNode(2)));
	}

	@Test
	def void testEditExistingThenRemoveThenReAddExpectNoChanges() {
		val builder = new NodeDiffBuilder(newInput(#[1, 2, 3]))
			.edit(newNode(2), newNode(36))
			.delete(newNode(36))
			.add(newNode(2))

		assertTrue('''Expected empty added items. Was «builder.addedItems»''', builder.addedItems.empty);
		assertTrue('''Expected empty edited items. Was «builder.editedItems»''', builder.editedItems.empty);
		assertTrue('''Expected empty deleted items. Was «builder.deletedItems»''', builder.deletedItems.empty);
	}

	private def newInput(Integer[] ids) {
		newInput(ids, ids);
	}

	private def newInput(Integer[] ids, Integer[] allIds) {
		new Input(ids.map[newNode], allIds.map[newNode]);
	}

	private def newNode(Integer id) {
		newNode(id, '''«id»''');
	}

	private def newNode(Integer id, String label) {
		new Node(id, label);
	}

	@FinalFieldsConstructor
	@Accessors
	@EqualsHashCode
	static class Node {
		var String label;
		val Integer id;

		new(Integer id, String label) {
			this(id);
			this.label = label;
		}

		override toString() {
			'''ID: «id» | Label: «label»'''
		}

	}

	@Data
	static class Input {
		val Node[] oldItems;
		val Node[] oldAllItems;
	}

	@Accessors
	static class NodeDiffBuilder extends DiffBuilder<Input, Node> {

		new(Input f) {
			super(f)
		}

		override protected getOldItemsFunction() {
			return [oldItems];
		}

		override protected getAllOldItemsFunction() {
			return [oldAllItems];
		}

	}

}
