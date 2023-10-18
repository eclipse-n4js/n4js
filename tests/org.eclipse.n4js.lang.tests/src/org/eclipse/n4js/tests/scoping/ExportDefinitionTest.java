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
package org.eclipse.n4js.tests.scoping;

import static org.eclipse.n4js.validation.IssueCodes.IMP_NOT_EXPORTED;
import static org.eclipse.n4js.validation.IssueCodes.UNSUPPORTED;

import java.util.List;

import org.eclipse.n4js.AbstractN4JSTest;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.scoping.ExportedElementsCollector;
import org.eclipse.n4js.ts.types.ElementExportDefinition;
import org.eclipse.n4js.ts.types.ExportDefinition;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Tests certain corner cases of {@link ExportDefinition}s.
 */
public class ExportDefinitionTest extends AbstractN4JSTest {

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	private ExportedElementsCollector exportedElementsCollector;

	@Test
	public void testOnlyDirectlyExported() throws Exception {
		N4JSResource res = (N4JSResource) testHelper.parseAndValidateSuccessfullyIgnoring("""
				export class Cls {}
				""", UNSUPPORTED).eResource();

		TModule module = res.getModule();
		Type cls = module.getTypes().get(0);
		ElementExportDefinition ed = (ElementExportDefinition) module.getExportDefinitions().get(0);

		assertTrue(cls.isDirectlyExported());
		assertFalse(cls.isIndirectlyExported());
		assertSame(cls, ed.getExportedElement());
		assertEquals(1, cls.getExportingExportDefinitions().size());
		assertSame(ed, cls.getExportingExportDefinitions().get(0));

		List<IEObjectDescription> elems = IterableExtensions.toList(
				exportedElementsCollector.getExportedElements(res.getModule(), res, Optional.absent(), true, true));
		assertEquals(1, elems.size());
		assertEquals("Cls", elems.get(0).getName().toString());
		assertSame(cls, elems.get(0).getEObjectOrProxy());
		assertFalse(elems.get(0) instanceof IEObjectDescriptionWithError);
	}

	@Test
	public void testOnlyIndirectlyExported() throws Exception {
		N4JSResource res = (N4JSResource) testHelper.parseAndValidateSuccessfullyIgnoring("""
				public class Cls {}
				export { Cls };
				""", UNSUPPORTED).eResource();

		TModule module = res.getModule();
		Type cls = module.getTypes().get(0);
		ElementExportDefinition ed = (ElementExportDefinition) module.getExportDefinitions().get(0);

		assertFalse(cls.isDirectlyExported());
		assertTrue(cls.isIndirectlyExported());
		assertSame(cls, ed.getExportedElement());
		assertEquals(1, cls.getExportingExportDefinitions().size());
		assertSame(ed, cls.getExportingExportDefinitions().get(0));

		List<IEObjectDescription> elems = IterableExtensions.toList(
				exportedElementsCollector.getExportedElements(res.getModule(), res, Optional.absent(), true, true));
		assertEquals(2, elems.size());
		assertEquals("Cls", elems.get(0).getName().toString());
		assertSame(cls, elems.get(0).getEObjectOrProxy());
		assertFalse(elems.get(0) instanceof IEObjectDescriptionWithError);
		assertEquals("Cls", elems.get(1).getName().toString());
		assertSame(cls, elems.get(1).getEObjectOrProxy());
		assertTrue(elems.get(1) instanceof IEObjectDescriptionWithError);
		assertEquals(IMP_NOT_EXPORTED, ((IEObjectDescriptionWithError) elems.get(1)).getIssueCode());
	}

	@Test
	public void testBothDirectlyAndIndirectlyExported() throws Exception {
		N4JSResource res = (N4JSResource) testHelper.parseAndValidateSuccessfullyIgnoring("""
				export class Cls {}
				export { Cls as Cls1 };
				""", UNSUPPORTED).eResource();

		TModule module = res.getModule();
		Type cls = module.getTypes().get(0);
		ElementExportDefinition ed1 = (ElementExportDefinition) module.getExportDefinitions().get(0);
		ElementExportDefinition ed2 = (ElementExportDefinition) module.getExportDefinitions().get(1);

		assertTrue(cls.isDirectlyExported());
		assertTrue(cls.isIndirectlyExported());
		assertSame(cls, ed1.getExportedElement());
		assertSame(cls, ed2.getExportedElement());
		assertEquals(2, cls.getExportingExportDefinitions().size());
		assertSame(ed1, cls.getExportingExportDefinitions().get(0));
		assertSame(ed2, cls.getExportingExportDefinitions().get(1));

		List<IEObjectDescription> elems = IterableExtensions.toList(
				exportedElementsCollector.getExportedElements(res.getModule(), res, Optional.absent(), true, true));
		assertEquals(2, elems.size());
		assertEquals("Cls1", elems.get(0).getName().toString());
		assertSame(cls, elems.get(0).getEObjectOrProxy());
		assertFalse(elems.get(0) instanceof IEObjectDescriptionWithError);
		assertEquals("Cls", elems.get(1).getName().toString());
		assertSame(cls, elems.get(1).getEObjectOrProxy());
		assertFalse(elems.get(1) instanceof IEObjectDescriptionWithError);
	}

	@Test
	public void testReexport1() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();

		Script scriptOther = testHelper.parseInFile("""
				export class Cls {}
				""", "other.n4js", rs);

		Script scriptMain = testHelper.parseInFile("""
				import { Cls } from "other";
				export { Cls };
				""", "main.n4js", rs);

		validationTestHelper.assertNoErrorsExcept(scriptOther, UNSUPPORTED);
		validationTestHelper.assertNoErrorsExcept(scriptMain, UNSUPPORTED);

		TModule moduleOther = scriptOther.getModule();
		TModule moduleMain = scriptMain.getModule();
		Type cls = moduleOther.getTypes().get(0);
		ElementExportDefinition ed = (ElementExportDefinition) moduleMain.getExportDefinitions().get(0);

		assertSame(cls, ed.getExportedElement());

		List<IEObjectDescription> elems = IterableExtensions.toList(exportedElementsCollector
				.getExportedElements(moduleMain, (N4JSResource) moduleMain.eResource(), Optional.absent(), true, true));
		assertEquals(1, elems.size());
		assertEquals("Cls", elems.get(0).getName().toString());
		assertSame(cls, elems.get(0).getEObjectOrProxy());
		assertFalse(elems.get(0) instanceof IEObjectDescriptionWithError);
	}

	@Test
	public void testReexport2() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();

		Script scriptOther = testHelper.parseInFile("""
				export class Cls {}
				""", "other.n4js", rs);

		Script scriptMain = testHelper.parseInFile("""
				export { Cls } from "other"
				""", "main.n4js", rs);

		validationTestHelper.assertNoErrorsExcept(scriptOther, UNSUPPORTED);
		validationTestHelper.assertNoErrorsExcept(scriptMain, UNSUPPORTED);

		TModule moduleOther = scriptOther.getModule();
		TModule moduleMain = scriptMain.getModule();
		Type cls = moduleOther.getTypes().get(0);
		ElementExportDefinition ed = (ElementExportDefinition) moduleMain.getExportDefinitions().get(0);

		assertSame(cls, ed.getExportedElement());

		List<IEObjectDescription> elems = IterableExtensions.toList(exportedElementsCollector
				.getExportedElements(moduleMain, (N4JSResource) moduleMain.eResource(), Optional.absent(), true, true));
		assertEquals(1, elems.size());
		assertEquals("Cls", elems.get(0).getName().toString());
		assertSame(cls, elems.get(0).getEObjectOrProxy());
		assertFalse(elems.get(0) instanceof IEObjectDescriptionWithError);
	}

	@Test
	public void testReexport3() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();

		Script scriptOther = testHelper.parseInFile("""
				export class Cls {}
				""", "other.n4js", rs);

		Script scriptMain = testHelper.parseInFile("""
				export { Cls as ClsAlias } from "other"
				""", "main.n4js", rs);

		validationTestHelper.assertNoErrorsExcept(scriptOther, UNSUPPORTED);
		validationTestHelper.assertNoErrorsExcept(scriptMain, UNSUPPORTED);

		TModule moduleOther = scriptOther.getModule();
		TModule moduleMain = scriptMain.getModule();
		Type cls = moduleOther.getTypes().get(0);
		ElementExportDefinition ed = (ElementExportDefinition) moduleMain.getExportDefinitions().get(0);

		assertSame(cls, ed.getExportedElement());

		List<IEObjectDescription> elems = IterableExtensions.toList(exportedElementsCollector
				.getExportedElements(moduleMain, (N4JSResource) moduleMain.eResource(), Optional.absent(), true, true));
		assertEquals(1, elems.size());
		assertEquals("ClsAlias", elems.get(0).getName().toString());
		assertSame(cls, elems.get(0).getEObjectOrProxy());
		assertFalse(elems.get(0) instanceof IEObjectDescriptionWithError);
	}

	@Test
	public void testOnlyDirectlyExported_variable() throws Exception {
		N4JSResource res = (N4JSResource) testHelper.parseAndValidateSuccessfullyIgnoring("""
				export var v;
				""", UNSUPPORTED).eResource();

		TModule module = res.getModule();

		assertEquals(1, module.getExportedVariables().size());
		assertEquals(0, module.getLocalVariables().size());
		assertEquals(0, module.getExposedLocalVariables().size());

		TVariable v = module.getExportedVariables().get(0);
		ElementExportDefinition ed = (ElementExportDefinition) module.getExportDefinitions().get(0);

		assertTrue(v.isDirectlyExported());
		assertFalse(v.isIndirectlyExported());
		assertSame(v, ed.getExportedElement());
		assertEquals(1, v.getExportingExportDefinitions().size());
		assertSame(ed, v.getExportingExportDefinitions().get(0));

		List<IEObjectDescription> elems = IterableExtensions.toList(
				exportedElementsCollector.getExportedElements(res.getModule(), res, Optional.absent(), true, true));
		assertEquals(1, elems.size());
		assertEquals("v", elems.get(0).getName().toString());
		assertSame(v, elems.get(0).getEObjectOrProxy());
		assertFalse(elems.get(0) instanceof IEObjectDescriptionWithError);
	}

	@Test
	public void testOnlyIndirectlyExported_variable() throws Exception {
		N4JSResource res = (N4JSResource) parserHelper.parseN4js("""
				public var v;
				export { v };
				""").eResource();

		res.installDerivedState(false);

		TModule module = res.getModule();
		assertEquals(1, module.getLocalVariables().size());
		assertEquals(0, module.getExposedLocalVariables().size());

		res.performPostProcessing();

		assertEquals(0, module.getExportedVariables().size());
		assertEquals(0, module.getLocalVariables().size());
		assertEquals(1, module.getExposedLocalVariables().size());

		validationTestHelper.assertNoErrorsExcept(res.getScript(), UNSUPPORTED);

		TVariable v = module.getExposedLocalVariables().get(0);
		ElementExportDefinition ed = (ElementExportDefinition) module.getExportDefinitions().get(0);

		assertFalse(v.isDirectlyExported());
		assertTrue(v.isIndirectlyExported());
		assertSame(v, ed.getExportedElement());
		assertEquals(1, v.getExportingExportDefinitions().size());
		assertSame(ed, v.getExportingExportDefinitions().get(0));

		List<IEObjectDescription> elems = IterableExtensions.toList(
				exportedElementsCollector.getExportedElements(res.getModule(), res, Optional.absent(), true, true));
		assertEquals(2, elems.size());
		assertEquals("v", elems.get(0).getName().toString());
		assertSame(v, elems.get(0).getEObjectOrProxy());
		assertFalse(elems.get(0) instanceof IEObjectDescriptionWithError);
		assertEquals("v", elems.get(1).getName().toString());
		assertSame(v, elems.get(1).getEObjectOrProxy());
		assertTrue(elems.get(1) instanceof IEObjectDescriptionWithError);
		assertEquals(IMP_NOT_EXPORTED, ((IEObjectDescriptionWithError) elems.get(1)).getIssueCode());
	}
}
