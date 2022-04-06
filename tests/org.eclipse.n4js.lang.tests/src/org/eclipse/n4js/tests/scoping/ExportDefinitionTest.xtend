/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.scoping

import org.eclipse.n4js.AbstractN4JSTest
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.ts.types.ElementExportDefinition
import org.eclipse.n4js.ts.types.ExportDefinition
import org.junit.Test

import static org.eclipse.n4js.validation.IssueCodes.UNSUPPORTED

/**
 * Tests certain corner cases of {@link ExportDefinition}s.
 */
class ExportDefinitionTest extends AbstractN4JSTest {

	@Test
	def void testOnlyDirectlyExported() {
		val res = '''
			export class Cls {}
		'''.parseAndValidateSuccessfullyIgnoring(UNSUPPORTED).eResource as N4JSResource;
		res.performPostProcessing;

		val module = res.module;
		val cls = module.types.get(0);
		val ed = module.exportDefinitions.get(0) as ElementExportDefinition;

		assertTrue(cls.directlyExported);
		assertFalse(cls.indirectlyExported);
		assertSame(cls, ed.exportedElement);
		assertEquals(1, cls.exportingExportDefinitions.size);
		assertSame(ed, cls.exportingExportDefinitions.get(0));
	}	

	@Test
	def void testOnlyIndirectlyExported() {
		val res = '''
			public class Cls {}
			export { Cls };
		'''.parseAndValidateSuccessfullyIgnoring(UNSUPPORTED).eResource as N4JSResource;
		res.performPostProcessing;

		val module = res.module;
		val cls = module.types.get(0);
		val ed = module.exportDefinitions.get(0) as ElementExportDefinition;

		assertFalse(cls.directlyExported);
		assertTrue(cls.indirectlyExported);
		assertSame(cls, ed.exportedElement);
		assertEquals(1, cls.exportingExportDefinitions.size);
		assertSame(ed, cls.exportingExportDefinitions.get(0));
	}	

	@Test
	def void testBothDirectlyAndIndirectlyExported() {
		val res = '''
			export class Cls {}
			export { Cls as Cls1 };
		'''.parseAndValidateSuccessfullyIgnoring(UNSUPPORTED).eResource as N4JSResource;
		res.performPostProcessing;

		val module = res.module;
		val cls = module.types.get(0);
		val ed1 = module.exportDefinitions.get(0) as ElementExportDefinition;
		val ed2 = module.exportDefinitions.get(1) as ElementExportDefinition;

		assertTrue(cls.directlyExported);
		assertTrue(cls.indirectlyExported);
		assertSame(cls, ed1.exportedElement);
		assertSame(cls, ed2.exportedElement);
		assertEquals(2, cls.exportingExportDefinitions.size);
		assertSame(ed1, cls.exportingExportDefinitions.get(0));
		assertSame(ed2, cls.exportingExportDefinitions.get(1));
	}	
}
