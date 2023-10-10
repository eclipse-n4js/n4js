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
package org.eclipse.n4js.tests.annotations;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;

import java.util.List;
import java.util.Objects;

import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class AnnotationsTest {

	@Inject
	ParseHelper<Script> ph;

	public Script compile(String code) {
		ph.fileExtension = "n4js";
		Script script;
		try {
			script = ph.parse(code);
			return script;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public N4ClassDeclaration findClassDeclaration(String code, String name) {
		return filter(
				filter(compile(code).eAllContents(), N4ClassDeclaration.class),
				cd -> Objects.equals(name, cd.getName())).next();
	}

	public N4MemberDeclaration findMemberDeclaration(String code, String name) {
		return filter(
				filter(compile(code).eAllContents(), N4MemberDeclaration.class),
				md -> Objects.equals(name, md.getName())).next();
	}

	@Test
	public void testBindAnnotationOnExportDeclaration() {
		String code = """

				export public interface ITires {}
				export public class Fuel {}

				export public class BiasPly implements ITires {}
				export public class Gasoline extends Fuel {}

				@Binder
				@Bind(ITires, BiasPly)
				@Bind(Fuel, Gasoline)
				export public class OldificationBinder {}
				""";

		N4ClassDeclaration classDecl = findClassDeclaration(code, "OldificationBinder");

		List<Annotation> bindings = toList(AnnotationDefinition.BIND.getAllAnnotations(classDecl));
		Assert.assertEquals("incorrect AllAnnotations", 2, bindings.size());

		List<Annotation> ownedBindings = toList(AnnotationDefinition.BIND.getAllOwnedAnnotations(classDecl));
		Assert.assertEquals("incorrect AllOwnedAnnotations", 2, ownedBindings.size());
	}

	@Test
	public void testBindAnnotationOnExportedElement() {
		String code = """

				export public interface ITires {}
				export public class Fuel {}

				export public class BiasPly implements ITires {}
				export public class Gasoline extends Fuel {}

				@Binder
				export @Bind(ITires, BiasPly) @Bind(Fuel, Gasoline) public class OldificationBinder {}
				""";

		N4ClassDeclaration classDecl = findClassDeclaration(code, "OldificationBinder");

		List<Annotation> bindings = toList(AnnotationDefinition.BIND.getAllAnnotations(classDecl));
		Assert.assertEquals("incorrect AllAnnotations", 2, bindings.size());

		List<Annotation> ownedBindings = toList(AnnotationDefinition.BIND.getAllOwnedAnnotations(classDecl));
		Assert.assertEquals("incorrect AllOwnedAnnotations", 2, ownedBindings.size());
	}

	@Test
	public void testTransitiveRepeatableAllPlaces() {
		String code = """

				@IDEBUG(1, "1")
				@IDEBUG(2, "2")
				export @IDEBUG(3, "3") @IDEBUG(4, "4") public class C {
				    @IDEBUG(5, "5")
				    @IDEBUG(6, "6")
				    m(){}
				}
				""";

		N4MemberDeclaration memberDecl = findMemberDeclaration(code, "m");

		List<Annotation> bindings = toList(AnnotationDefinition.IDEBUG.getAllAnnotations(memberDecl));
		Assert.assertEquals("incorrect AllAnnotations", 6, bindings.size());

		List<Annotation> ownedBindings = toList(AnnotationDefinition.IDEBUG.getAllOwnedAnnotations(memberDecl));
		Assert.assertEquals("incorrect AllOwnedAnnotations", 2, ownedBindings.size());
	}

	@Test
	public void testTransitiveRepeatableExportDeclaration() {
		String code = """

				@Internal
				export public class C {}
				""";

		N4ClassDeclaration classDecl = findClassDeclaration(code, "C");

		List<Annotation> bindings = toList(AnnotationDefinition.INTERNAL.getAllAnnotations(classDecl));
		Assert.assertEquals("incorrect AllAnnotations", 1, bindings.size());

		List<Annotation> ownedBindings = toList(AnnotationDefinition.INTERNAL.getAllAnnotations(classDecl));
		Assert.assertEquals("incorrect AllOwnedAnnotations", 1, ownedBindings.size());
	}

	@Test
	public void testTransitiveRepeatableExportedElement() {
		String code = """

				export @Internal public class C {}
				""";

		N4ClassDeclaration classDecl = findClassDeclaration(code, "C");

		List<Annotation> bindings = toList(AnnotationDefinition.INTERNAL.getAllAnnotations(classDecl));
		Assert.assertEquals("incorrect AllAnnotations", 1, bindings.size());

		List<Annotation> ownedBindings = toList(AnnotationDefinition.INTERNAL.getAllAnnotations(classDecl));
		Assert.assertEquals("incorrect AllOwnedAnnotations", 1, ownedBindings.size());
	}

	@Test
	public void testNonTransitiveNonRepeatableAllPLaces() {
		String code = """

				@Internal
				export @Internal public class C {}
				""";

		N4ClassDeclaration classDecl = findClassDeclaration(code, "C");

		List<Annotation> bindings = toList(AnnotationDefinition.INTERNAL.getAllAnnotations(classDecl));
		Assert.assertEquals("incorrect AllAnnotations", 1, bindings.size());

		List<Annotation> ownedBindings = toList(AnnotationDefinition.INTERNAL.getAllAnnotations(classDecl));
		Assert.assertEquals("incorrect AllOwnedAnnotations", 1, ownedBindings.size());
	}

	@Test
	public void testNonTransitiveNonRepeatableNotOnMember() {
		String code = """

				@Internal
				export @Internal public class C {
					m(){}
				}
				""";

		N4MemberDeclaration memberDecl = findMemberDeclaration(code, "m");

		List<Annotation> bindings = toList(AnnotationDefinition.IDEBUG.getAllAnnotations(memberDecl));
		Assert.assertEquals("incorrect AllAnnotations", 0, bindings.size());

		List<Annotation> ownedBindings = toList(AnnotationDefinition.IDEBUG.getAllOwnedAnnotations(memberDecl));
		Assert.assertEquals("incorrect AllOwnedAnnotations", 0, ownedBindings.size());
	}

	@Test
	public void testNonTransitiveNonRepeatableAllPlaces() {
		String code = """

				@Internal
				export @Internal public class C {
					@Internal
					m(){}
				}
				""";

		N4MemberDeclaration memberDecl = findMemberDeclaration(code, "m");

		List<Annotation> bindings = toList(AnnotationDefinition.INTERNAL.getAllAnnotations(memberDecl));
		Assert.assertEquals("incorrect AllAnnotations", 1, bindings.size());

		List<Annotation> ownedBindings = toList(AnnotationDefinition.INTERNAL.getAllOwnedAnnotations(memberDecl));
		Assert.assertEquals("incorrect AllOwnedAnnotations", 1, ownedBindings.size());
	}

}
