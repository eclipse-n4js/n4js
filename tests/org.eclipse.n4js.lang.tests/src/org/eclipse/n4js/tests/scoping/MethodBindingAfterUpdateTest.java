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
package org.eclipse.n4js.tests.scoping;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class MethodBindingAfterUpdateTest {

	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	ValidationTestHelper valTestHelper;

	@Test
	public void testPlain() throws Exception {
		Script script = parseHelper.parse("""
				class C {
				   getString(): void {
				    	var s: string = ''
				    	s.slice(0, 10)
				   }
				}
				""");
		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void testEditAndResolve() throws Exception {
		Script script = parseHelper.parse("""
				class C {
				   getString(): void {
				    	var s: string = ''
				    	s.slice(0, 10)
				   }
				}
				""");
		XtextResource resource = (XtextResource) script.eResource();
		resource.update(0, 0, " ");
		valTestHelper.assertNoErrors(resource.getContents().get(0));
	}

	@Test
	public void testResolveEditAndResolveAgain() throws Exception {
		Script script = parseHelper.parse("""
				class C {
				   getString(): void {
				    	var s: string = ''
				    	s.slice(0, 10)
				   }
				}
				""");
		valTestHelper.assertNoErrors(script);
		XtextResource resource = (XtextResource) script.eResource();
		resource.update(0, 0, " ");
		valTestHelper.assertNoErrors(resource.getContents().get(0));
	}
}
