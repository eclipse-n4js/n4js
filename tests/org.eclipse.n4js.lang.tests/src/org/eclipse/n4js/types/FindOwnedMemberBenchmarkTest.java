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
package org.eclipse.n4js.types;

import java.util.Collection;
import java.util.List;

import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.google.common.collect.Lists;

/**
 */
@RunWith(Parameterized.class)
public class FindOwnedMemberBenchmarkTest {

	private final int members;
	private ContainerType<?> type;
	private List<String> names;

	private static final int runs = 1000000;
	private final int mode;

	
	@Parameters(name = "{0}-{1}")
	public static Collection<Object[]> data() {
		List<Object[]> data = Lists.newArrayList();
		for (int i = 0; i < 15; i++) {
			data.add(new Object[] { i, 0 });
			// data.add(new Object[] {i, 1});
			// data.add(new Object[] {i, 2});
		}
		return data;
	}

	
	public FindOwnedMemberBenchmarkTest(int members, int mode) {
		this.members = members;
		this.mode = mode;
	}

	
	@Before
	public void setup() {
		@SuppressWarnings("hiding")
		TClass type = TypesFactory.eINSTANCE.createTClass();
		@SuppressWarnings("hiding")
		List<String> names = Lists.newArrayList();
		for (int i = 0; i < members; i++) {
			TMember member = TypesFactory.eINSTANCE.createTField(); // Member();
			member.setName("member" + i);
			names.add(member.getName());
			type.getOwnedMembers().add(member);
		}
		this.type = type;
		this.names = names;
	}

	
	@Test
	public void test() {
		switch (mode) {
		case 0:
			testImplementation();
			return;
		// case 1:
		// testImplementationWithOccasionalClear2();
		// return;
		// case 2:
		// testImplementationWithClear2();
		// return;
		}
	}

	
	public void testImplementation() {
		for (int i = 0; i < runs; i++) {
			Assert.assertNull(type.findOwnedMember("DoesNotExist"));
			for (int j = 0; j < members; j++) {
				Assert.assertNotNull(type.findOwnedMember(names.get(j)));
			}
		}
	}

	
	public void testImplementationWithClear() {
		for (int i = 0; i < runs; i++) {
			Assert.assertNull(type.findOwnedMember("DoesNotExist"));
			for (int j = 0; j < members; j++) {
				Assert.assertNotNull(type.findOwnedMember(names.get(j)));
			}
			type.setOwnedMembersByNameAndAccess(null);
		}
	}

	
	public void testImplementationWithOccasionalClear() {
		for (int i = 0; i < runs; i++) {
			Assert.assertNull(type.findOwnedMember("DoesNotExist"));
			for (int j = 0; j < members; j++) {
				Assert.assertNotNull(type.findOwnedMember(names.get(j)));
				if (j % 5 == 0)
					type.setOwnedMembersByNameAndAccess(null);
			}
			type.setOwnedMembersByNameAndAccess(null);
		}
	}

	
	public void testImplementationWithEagerClear() {
		for (int i = 0; i < runs; i++) {
			Assert.assertNull(type.findOwnedMember("DoesNotExist"));
			type.setOwnedMembersByNameAndAccess(null);
			for (int j = 0; j < members; j++) {
				Assert.assertNotNull(type.findOwnedMember(names.get(j)));
				type.setOwnedMembersByNameAndAccess(null);
			}
		}
	}

	
	public void testImplementation2() {
		for (int i = 0; i < runs; i++) {
			for (int j = 0; j < members; j++) {
				Assert.assertNotNull(type.findOwnedMember(names.get(j)));
			}
			Assert.assertNull(type.findOwnedMember("DoesNotExist"));
		}
	}

	
	public void testImplementationWithClear2() {
		for (int i = 0; i < runs; i++) {
			for (int j = 0; j < members; j++) {
				Assert.assertNotNull(type.findOwnedMember(names.get(j)));
			}
			Assert.assertNull(type.findOwnedMember("DoesNotExist"));
			type.setOwnedMembersByNameAndAccess(null);
		}
	}

	
	public void testImplementationWithOccasionalClear2() {
		for (int i = 0; i < runs; i++) {
			for (int j = 0; j < members; j++) {
				Assert.assertNotNull(type.findOwnedMember(names.get(j)));
				if (j % 5 == 0)
					type.setOwnedMembersByNameAndAccess(null);
			}
			Assert.assertNull(type.findOwnedMember("DoesNotExist"));
			type.setOwnedMembersByNameAndAccess(null);
		}
	}

	
	public void testImplementationWithEagerClear2() {
		for (int i = 0; i < runs; i++) {
			for (int j = 0; j < members; j++) {
				Assert.assertNotNull(type.findOwnedMember(names.get(j)));
				type.setOwnedMembersByNameAndAccess(null);
			}
			Assert.assertNull(type.findOwnedMember("DoesNotExist"));
			type.setOwnedMembersByNameAndAccess(null);
		}
	}

}
