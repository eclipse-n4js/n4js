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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class TypeScopingTest {

	@Inject
	ParseHelper<Script> parseHelper;

	@Inject
	Provider<XtextResourceSet> resourceSetProvider;

	@Test
	public void testDuplicateImport() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		URI uri1 = URI.createURI("c.n4js");
		Script script1 = parseHelper.parse("""
				@Internal public class C {
				}
				""", uri1, rs);
		URI uri2 = URI.createURI("d.n4js");
		Script script2 = parseHelper.parse("""
				import { C as C } from 'c';
				import { C as B } from 'c';
				import { C as A } from 'c';

				@Internal public class D {
					m(b: B): A {
					}
				}
				""", uri2, rs);
		N4ClassDeclaration c = (N4ClassDeclaration) last(script1.getScriptElements());
		N4ClassDeclaration d = (N4ClassDeclaration) last(script2.getScriptElements());
		N4MethodDeclaration m = (N4MethodDeclaration) head(d.getOwnedMembers());
		Type returnType = ((ParameterizedTypeRef) m.getDeclaredReturnTypeRefInAST()).getDeclaredType();
		Type firstParamType = ((ParameterizedTypeRef) head(m.getFpars()).getDeclaredTypeRefInAST()).getDeclaredType();
		Assert.assertSame(returnType, firstParamType);
		Assert.assertSame(c.getDefinedTypeAsClass(), returnType);
	}

	@Test
	public void testTypeVariable() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		URI uri = URI.createURI("c.n4js");
		Script script = parseHelper.parse("""
				@Internal public class C<A> {
					<B> m(b: B): A {
					}
				}
				""", uri, rs);
		N4ClassDeclaration c = (N4ClassDeclaration) last(script.getScriptElements());
		N4MethodDeclaration m = (N4MethodDeclaration) head(c.getOwnedMembers());
		Type returnType = ((ParameterizedTypeRef) m.getDeclaredReturnTypeRefInAST()).getDeclaredType();
		Type firstParamType = ((ParameterizedTypeRef) head(m.getFpars()).getDeclaredTypeRefInAST()).getDeclaredType();
		Assert.assertSame(head(((TMethod) m.getDefinedType()).getTypeVars()), firstParamType);
		Assert.assertSame(head(c.getDefinedTypeAsClass().getTypeVars()), returnType);
	}

	@Test
	public void testFieldType() throws Exception {
		Script script = parseHelper.parse("""
				export project enum StorageType {
					FILESYSTEM, DATABASE, CLOUD
				}
				export public interface Element {
				}
				export public class Storage<E extends Element> {
					private type: StorageType;
				}
				""");
		N4ClassDeclaration storage = (N4ClassDeclaration) ((ExportDeclaration) last(script.getScriptElements()))
				.getExportedElement();
		TClass definedClass = (TClass) storage.getDefinedType();
		TField field = (TField) head(definedClass.getOwnedMembers());
		Type fieldType = ((ParameterizedTypeRef) field.getTypeRef()).getDeclaredType();
		Assert.assertFalse(fieldType.eIsProxy());
	}
}
