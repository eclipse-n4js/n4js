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

import com.google.common.base.Optional
import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.n4js.AbstractN4JSTest
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.scoping.ExportedElementsCollector
import org.eclipse.n4js.ts.types.ElementExportDefinition
import org.eclipse.n4js.ts.types.ExportDefinition
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError
import org.eclipse.xtext.resource.XtextResourceSet
import org.junit.Test

import static org.eclipse.n4js.validation.IssueCodes.UNSUPPORTED

/**
 * Tests certain corner cases of {@link ExportDefinition}s.
 */
class ExportDefinitionTest extends AbstractN4JSTest {

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	private ExportedElementsCollector exportedElementsCollector;

	@Test
	def void testOnlyDirectlyExported() {
		val res =testHelper.parseAndValidateSuccessfullyIgnoring( '''
			export class Cls {}
		''', UNSUPPORTED).eResource as N4JSResource;

		val module = res.module;
		val cls = module.types.get(0);
		val ed = module.exportDefinitions.get(0) as ElementExportDefinition;

		assertTrue(cls.directlyExported);
		assertFalse(cls.indirectlyExported);
		assertSame(cls, ed.exportedElement);
		assertEquals(1, cls.exportingExportDefinitions.size);
		assertSame(ed, cls.exportingExportDefinitions.get(0));

		val elems = exportedElementsCollector.getExportedElements(res.module, res, Optional.absent, true, true).toList;
		assertEquals(1, elems.size);
		assertEquals("Cls", elems.head.name.toString);
		assertSame(cls, elems.head.EObjectOrProxy);
		assertFalse(elems.head instanceof IEObjectDescriptionWithError);
	}

	@Test
	def void testOnlyIndirectlyExported() {
		val res = testHelper.parseAndValidateSuccessfullyIgnoring('''
			public class Cls {}
			export { Cls };
		''', UNSUPPORTED).eResource as N4JSResource;

		val module = res.module;
		val cls = module.types.get(0);
		val ed = module.exportDefinitions.get(0) as ElementExportDefinition;

		assertFalse(cls.directlyExported);
		assertTrue(cls.indirectlyExported);
		assertSame(cls, ed.exportedElement);
		assertEquals(1, cls.exportingExportDefinitions.size);
		assertSame(ed, cls.exportingExportDefinitions.get(0));

		val elems = exportedElementsCollector.getExportedElements(res.module, res, Optional.absent, true, true).toList;
		assertEquals(2, elems.size);
		assertEquals("Cls", elems.head.name.toString);
		assertSame(cls, elems.head.EObjectOrProxy);
		assertFalse(elems.head instanceof IEObjectDescriptionWithError);
		assertEquals("Cls", elems.get(1).name.toString);
		assertSame(cls, elems.get(1).EObjectOrProxy);
		assertTrue(elems.get(1) instanceof IEObjectDescriptionWithError);
		assertEquals(IssueCodes.IMP_NOT_EXPORTED, (elems.get(1) as IEObjectDescriptionWithError).issueCode);
	}

	@Test
	def void testBothDirectlyAndIndirectlyExported() {
		val res = testHelper.parseAndValidateSuccessfullyIgnoring('''
			export class Cls {}
			export { Cls as Cls1 };
		''', UNSUPPORTED).eResource as N4JSResource;

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

		val elems = exportedElementsCollector.getExportedElements(res.module, res, Optional.absent, true, true).toList;
		assertEquals(2, elems.size);
		assertEquals("Cls1", elems.head.name.toString);
		assertSame(cls, elems.head.EObjectOrProxy);
		assertFalse(elems.head instanceof IEObjectDescriptionWithError);
		assertEquals("Cls", elems.get(1).name.toString);
		assertSame(cls, elems.get(1).EObjectOrProxy);
		assertFalse(elems.get(1) instanceof IEObjectDescriptionWithError);
	}

	@Test
	def void testReexport1() {
		val rs = resourceSetProvider.get();

		val scriptOther = testHelper.parseInFile('''
			export class Cls {}
		''', "other.n4js", rs);

		val scriptMain = testHelper.parseInFile('''
			import { Cls } from "other"
			export { Cls };
		''', "main.n4js", rs);

		validationTestHelper.assertNoErrorsExcept(scriptOther, UNSUPPORTED);
		validationTestHelper.assertNoErrorsExcept(scriptMain, UNSUPPORTED);

		val moduleOther = scriptOther.module;
		val moduleMain = scriptMain.module;
		val cls = moduleOther.types.get(0);
		val ed = moduleMain.exportDefinitions.get(0) as ElementExportDefinition;

		assertSame(cls, ed.exportedElement);

		val elems = exportedElementsCollector.getExportedElements(moduleMain, moduleMain.eResource as N4JSResource, Optional.absent, true, true).toList;
		assertEquals(1, elems.size);
		assertEquals("Cls", elems.head.name.toString);
		assertSame(cls, elems.head.EObjectOrProxy);
		assertFalse(elems.head instanceof IEObjectDescriptionWithError);
	}

	@Test
	def void testReexport2() {
		val rs = resourceSetProvider.get();

		val scriptOther = testHelper.parseInFile('''
			export class Cls {}
		''', "other.n4js", rs);

		val scriptMain = testHelper.parseInFile('''
			export { Cls } from "other"
		''', "main.n4js", rs);

		validationTestHelper.assertNoErrorsExcept(scriptOther, UNSUPPORTED);
		validationTestHelper.assertNoErrorsExcept(scriptMain, UNSUPPORTED);

		val moduleOther = scriptOther.module;
		val moduleMain = scriptMain.module;
		val cls = moduleOther.types.get(0);
		val ed = moduleMain.exportDefinitions.get(0) as ElementExportDefinition;

		assertSame(cls, ed.exportedElement);

		val elems = exportedElementsCollector.getExportedElements(moduleMain, moduleMain.eResource as N4JSResource, Optional.absent, true, true).toList;
		assertEquals(1, elems.size);
		assertEquals("Cls", elems.head.name.toString);
		assertSame(cls, elems.head.EObjectOrProxy);
		assertFalse(elems.head instanceof IEObjectDescriptionWithError);
	}

	@Test
	def void testReexport3() {
		val rs = resourceSetProvider.get();

		val scriptOther = testHelper.parseInFile('''
			export class Cls {}
		''', "other.n4js", rs);

		val scriptMain = testHelper.parseInFile('''
			export { Cls as ClsAlias } from "other"
		''', "main.n4js", rs);

		validationTestHelper.assertNoErrorsExcept(scriptOther, UNSUPPORTED);
		validationTestHelper.assertNoErrorsExcept(scriptMain, UNSUPPORTED);

		val moduleOther = scriptOther.module;
		val moduleMain = scriptMain.module;
		val cls = moduleOther.types.get(0);
		val ed = moduleMain.exportDefinitions.get(0) as ElementExportDefinition;

		assertSame(cls, ed.exportedElement);

		val elems = exportedElementsCollector.getExportedElements(moduleMain, moduleMain.eResource as N4JSResource, Optional.absent, true, true).toList;
		assertEquals(1, elems.size);
		assertEquals("ClsAlias", elems.head.name.toString);
		assertSame(cls, elems.head.EObjectOrProxy);
		assertFalse(elems.head instanceof IEObjectDescriptionWithError);
	}

	@Test
	def void testOnlyDirectlyExported_variable() {
		val res = testHelper.parseAndValidateSuccessfullyIgnoring('''
			export var v;
		''', UNSUPPORTED).eResource as N4JSResource;

		val module = res.module;

		assertEquals(1, module.exportedVariables.size);
		assertEquals(0, module.localVariables.size);
		assertEquals(0, module.exposedLocalVariables.size);

		val v = module.exportedVariables.get(0);
		val ed = module.exportDefinitions.get(0) as ElementExportDefinition;

		assertTrue(v.directlyExported);
		assertFalse(v.indirectlyExported);
		assertSame(v, ed.exportedElement);
		assertEquals(1, v.exportingExportDefinitions.size);
		assertSame(ed, v.exportingExportDefinitions.get(0));

		val elems = exportedElementsCollector.getExportedElements(res.module, res, Optional.absent, true, true).toList;
		assertEquals(1, elems.size);
		assertEquals("v", elems.head.name.toString);
		assertSame(v, elems.head.EObjectOrProxy);
		assertFalse(elems.head instanceof IEObjectDescriptionWithError);
	}

	@Test
	def void testOnlyIndirectlyExported_variable() {
		val res = parserHelper.parseN4js('''
			public var v;
			export { v };
		''').eResource as N4JSResource;

		res.installDerivedState(false);

		val module = res.module;
		assertEquals(1, module.localVariables.size);
		assertEquals(0, module.exposedLocalVariables.size);

		res.performPostProcessing();

		assertEquals(0, module.exportedVariables.size);
		assertEquals(0, module.localVariables.size);
		assertEquals(1, module.exposedLocalVariables.size);

		validationTestHelper.assertNoErrorsExcept(res.script, UNSUPPORTED);

		val v = module.exposedLocalVariables.get(0);
		val ed = module.exportDefinitions.get(0) as ElementExportDefinition;

		assertFalse(v.directlyExported);
		assertTrue(v.indirectlyExported);
		assertSame(v, ed.exportedElement);
		assertEquals(1, v.exportingExportDefinitions.size);
		assertSame(ed, v.exportingExportDefinitions.get(0));

		val elems = exportedElementsCollector.getExportedElements(res.module, res, Optional.absent, true, true).toList;
		assertEquals(2, elems.size);
		assertEquals("v", elems.head.name.toString);
		assertSame(v, elems.head.EObjectOrProxy);
		assertFalse(elems.head instanceof IEObjectDescriptionWithError);
		assertEquals("v", elems.get(1).name.toString);
		assertSame(v, elems.get(1).EObjectOrProxy);
		assertTrue(elems.get(1) instanceof IEObjectDescriptionWithError);
		assertEquals(IssueCodes.IMP_NOT_EXPORTED, (elems.get(1) as IEObjectDescriptionWithError).issueCode);
	}
}
