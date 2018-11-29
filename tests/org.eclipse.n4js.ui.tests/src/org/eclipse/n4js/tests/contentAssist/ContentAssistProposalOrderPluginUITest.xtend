/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.contentAssist

import org.junit.Test

/**
 * Testing the order of proposals presented during content assist.
 */
class ContentAssistProposalOrderPluginUITest extends AbstractN4JSContentAssistWithOrderingPluginUITest {

	private static final String NL = System.lineSeparator;

	private final String code = '''
		class AccessData1 {}
		class AccessData2 {}
		class AccessData3 {}
		
		class AccessInfo1 {}
		class AccessInfo2 {}
		
		let accessInfo1 = 0;
		let accessInfo2 = 0;
		
		let acci = 0;
		let access = 0;
		let accessi = 0;
		let accessI = 0;
		let accessSync = 0;


		let propertyInfoDataElement1 = 0;
		let propertyInfoDataElement2 = 0;
		let propertyInfoDataElement3 = 0;
		
		let propide = 0;
		let propIDE = 0;
	''';

	// use above code in all tests as preamble
	override protected computeActualProposals(CharSequence snippet) {
		super.computeActualProposals(code + NL + NL + snippet);
	}

	@Test
	def void test01() {
		assertProposalOrder("acc", #[
			0 -> 'access',
			0 -> 'accessi',
			0 -> 'accessI',
			0 -> 'accessInfo1',
			0 -> 'accessInfo2',
			0 -> 'accessSync',
			0 -> 'acci',
			1 -> 'AccessData1',
			1 -> 'AccessData2',
			1 -> 'AccessData3',
			1 -> 'AccessInfo1',
			1 -> 'AccessInfo2'
		]);
	}

	@Test
	def void test02() {
		assertProposalOrder("Acc", #[
			0 -> 'AccessData1',
			0 -> 'AccessData2',
			0 -> 'AccessData3',
			0 -> 'AccessInfo1',
			0 -> 'AccessInfo2',
			1 -> 'access',
			1 -> 'accessi',
			1 -> 'accessI',
			1 -> 'accessInfo1',
			1 -> 'accessInfo2',
			1 -> 'accessSync',
			1 -> 'acci'
		]);
	}

	@Test
	def void test03a() {
		assertProposalOrder("accI", #[
			0 -> 'accessI',
			0 -> 'accessInfo1',
			0 -> 'accessInfo2',
			1 -> 'acci'
		]);
	}

	@Test
	def void test03b() {
		assertProposalOrder("propIDE", #[
			0 -> 'propertyInfoDataElement1',
			0 -> 'propertyInfoDataElement2',
			0 -> 'propertyInfoDataElement3',
			0 -> 'propIDE',
			1 -> 'propide'
		]);
	}

	@Test
	def void test04() {
		assertProposalOrder("AccI", #[
			0 -> 'AccessInfo1',
			0 -> 'AccessInfo2',
			1 -> 'acci'
		]);
	}

	@Test
	def void test05() {
		assertProposalOrder("accessi", #[
			0 -> 'accessi',
			1 -> 'accessI',
			1 -> 'accessInfo1',
			1 -> 'AccessInfo1',
			1 -> 'accessInfo2',
			1 -> 'AccessInfo2'
		]);
	}

	@Test
	def void test06() {
		assertProposalOrder("accessI", #[
			0 -> 'accessI',
			0 -> 'accessInfo1',
			0 -> 'accessInfo2',
			1 -> 'accessi',
			1 -> 'AccessInfo1',
			1 -> 'AccessInfo2'
		]);
	}

	@Test
	def void test07() {
		assertProposalOrder("AccessI", #[
			0 -> 'AccessInfo1',
			0 -> 'AccessInfo2',
			1 -> 'accessi',
			1 -> 'accessI',
			1 -> 'accessInfo1',
			1 -> 'accessInfo2'
		]);
	}
}
