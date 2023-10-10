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
package org.eclipse.n4js.utils;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;
import org.junit.Test;

/**
 * Test for {@link DiffBuilder}.
 */
public class DiffBuilderTest2 {

	@Test
	public void testAddExistingExpectZeroAdded() {
		DiffBuilder<Input, Node> builder = new NodeDiffBuilder(newInput(List.of(1, 2, 3)))
				.add(newNode(1));

		assertTrue("Expected empty added items. Was «builder.addedItems»", builder.addedItems.isEmpty());
	}

	@Test
	public void testAddNewThenRemoveExpectZeroAdded() {
		DiffBuilder<Input, Node> builder = new NodeDiffBuilder(newInput(List.of(1, 2, 3)))
				.add(newNode(36))
				.delete(newNode(36));

		assertTrue("Expected empty added items. Was «builder.addedItems»", builder.addedItems.isEmpty());
	}

	@Test
	public void testAddNewThenRemoveExpectZeroRemoved() {
		DiffBuilder<Input, Node> builder = new NodeDiffBuilder(newInput(List.of(1, 2, 3)))
				.add(newNode(36))
				.delete(newNode(36));

		assertTrue("Expected empty deleted items. Was «builder.deletedItems»", builder.deletedItems.isEmpty());
	}

	@Test
	public void testEditExistingThenRemoveExpectZeroEdited() {
		DiffBuilder<Input, Node> builder = new NodeDiffBuilder(newInput(List.of(1, 2, 3)))
				.edit(newNode(2), newNode(36))
				.delete(newNode(36));

		assertTrue("Expected empty edited items. Was «builder.editedItems»", builder.editedItems.isEmpty());
	}

	@Test
	public void testEditExistingThenRemoveExpectOriginalIsDeleted() {
		DiffBuilder<Input, Node> builder = new NodeDiffBuilder(newInput(List.of(1, 2, 3)))
				.edit(newNode(2), newNode(36))
				.delete(newNode(36));

		assertTrue("Expected «newNode(2)» among deleted items. Was «builder.deletedItems»",
				builder.deletedItems.contains(newNode(2)));
	}

	@Test
	public void testEditExistingThenRemoveThenReAddExpectNoChanges() {
		DiffBuilder<Input, Node> builder = new NodeDiffBuilder(newInput(List.of(1, 2, 3)))
				.edit(newNode(2), newNode(36))
				.delete(newNode(36))
				.add(newNode(2));

		assertTrue("Expected empty added items. Was «builder.addedItems»", builder.addedItems.isEmpty());
		assertTrue("Expected empty edited items. Was «builder.editedItems»", builder.editedItems.isEmpty());
		assertTrue("Expected empty deleted items. Was «builder.deletedItems»", builder.deletedItems.isEmpty());
	}

	private Input newInput(List<Integer> ids) {
		return newInput(ids, ids);
	}

	private Input newInput(List<Integer> ids, List<Integer> allIds) {

		return new Input(map(ids, id -> newNode(id)), map(allIds, id -> newNode(id)));
	}

	private Node newNode(Integer id) {
		return newNode(id, "«id»");
	}

	private Node newNode(Integer id, String label) {
		return new Node(id, label);
	}

	static class Node {
		final Integer id;
		String label;

		Node(Integer id, String label) {
			this.id = id;
			this.label = label;
		}

		@Override
		public String toString() {
			return "ID: " + id + " | Label: " + label;
		}

		public Node(final Integer id) {
			super();
			this.id = id;
		}

		@Pure
		public String getLabel() {
			return this.label;
		}

		public void setLabel(final String label) {
			this.label = label;
		}

		@Pure
		public Integer getId() {
			return this.id;
		}

		@Override
		@Pure
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (this.label == null) {
				if (other.label != null)
					return false;
			} else if (!this.label.equals(other.label))
				return false;
			if (this.id == null) {
				if (other.id != null)
					return false;
			} else if (!this.id.equals(other.id))
				return false;
			return true;
		}

		@Override
		@Pure
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((this.label == null) ? 0 : this.label.hashCode());
			return prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		}
	}

	static class Input {
		Node[] oldItems;
		Node[] oldAllItems;

		public Input(Iterable<Node> oldItems, Iterable<Node> oldAllItems) {
			this.oldItems = IterableExtensions.toList(oldItems).toArray(new Node[0]);
			this.oldAllItems = IterableExtensions.toList(oldAllItems).toArray(new Node[0]);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((this.oldItems == null) ? 0 : Arrays.deepHashCode(this.oldItems));
			return prime * result + ((this.oldAllItems == null) ? 0 : Arrays.deepHashCode(this.oldAllItems));
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Input other = (Input) obj;
			if (this.oldItems == null) {
				if (other.oldItems != null)
					return false;
			} else if (!Arrays.deepEquals(this.oldItems, other.oldItems))
				return false;
			if (this.oldAllItems == null) {
				if (other.oldAllItems != null)
					return false;
			} else if (!Arrays.deepEquals(this.oldAllItems, other.oldAllItems))
				return false;
			return true;
		}

		@Override
		public String toString() {
			ToStringBuilder b = new ToStringBuilder(this);
			b.add("oldItems", this.oldItems);
			b.add("oldAllItems", this.oldAllItems);
			return b.toString();
		}

		public Node[] getOldItems() {
			return this.oldItems;
		}

		public Node[] getOldAllItems() {
			return this.oldAllItems;
		}
	}

	@Accessors
	static class NodeDiffBuilder extends DiffBuilder<Input, Node> {

		NodeDiffBuilder(Input f) {
			super(f);
		}

		@Override
		protected Function<Input, Node[]> getOldItemsFunction() {
			return input -> input.oldItems;
		}

		@Override
		protected Function<Input, Node[]> getAllOldItemsFunction() {
			return input -> input.oldAllItems;
		}

	}

}
