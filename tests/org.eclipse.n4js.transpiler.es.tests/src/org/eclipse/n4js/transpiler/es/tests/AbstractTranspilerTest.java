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
package org.eclipse.n4js.transpiler.es.tests;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.findFirst;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.JSActivationUtil;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.N4JSParseHelper;
import org.eclipse.n4js.N4JSTestHelper;
import org.eclipse.n4js.generator.GeneratorOption;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.naming.N4JSQualifiedNameConverter;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.tests.helper.mock.MockWorkspaceSupplier;
import org.eclipse.n4js.transpiler.PreparationStep;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.es.EcmaScriptSubGenerator;
import org.eclipse.n4js.transpiler.es.EcmaScriptTranspiler;
import org.eclipse.n4js.transpiler.es.transform.BlockTransformation;
import org.eclipse.n4js.transpiler.im.ReferencingElement_IM;
import org.eclipse.n4js.transpiler.im.SymbolTable;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.transpiler.utils.TranspilerDebugUtils;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtend.lib.annotations.Data;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.util.ResourceHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;
import com.google.inject.Provider;

public abstract class AbstractTranspilerTest {

	protected static final GeneratorOption[] GENERATOR_OPTIONS = { GeneratorOption.ES5plus };

	@Inject
	protected ResourceHelper resHelper;
	@Inject
	protected N4JSTestHelper testHelper;
	@Inject
	protected N4JSParseHelper parseHelper;
	@Inject
	protected ValidationTestHelper valTestHelepr;
	@Inject
	private MockWorkspaceSupplier mockWorkspace;

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;
	@Inject
	private PreparationStep preparationStep;
	@Inject
	private EcmaScriptTranspiler esTranspiler;
	@Inject
	private TranspilerDebugUtils transpilerDebugUtils;
	@Inject
	protected EcmaScriptSubGenerator esSubGen;

	public AbstractTranspilerTest() {
		JSActivationUtil.enableJSSupport();
	}

	/** Find first element of given type in original AST; throw assertion error if not found. */
	protected <T extends EObject> T findFirstInAST(TranspilerState state, Class<T> typeToSearch) {
		T result = findFirstOf(state.resource.getScript().eAllContents(), typeToSearch, null);
		if (result == null) {
			fail("no node of type '" + typeToSearch.getSimpleName() + "' found in original AST");
		}
		return result;
	}

	/** Find first element of given type in original TModule; throw assertion error if not found. */
	protected <T extends EObject> T findFirstInModule(TranspilerState state, Class<T> typeToSearch) {
		return findFirstInModule(state.resource.getModule(), typeToSearch);
	}

	/** Find first element of given type in a remote TModule of the given name; throw assertion error if not found. */
	protected <T extends EObject> T findFirstInRemoteModule(TranspilerState state, String moduleName,
			Class<T> typeToSearch) {
		ResourceSet resSet = state.resource.getResourceSet();
		for (Resource res : resSet.getResources()) {
			if (res instanceof N4JSResource && res != state.resource) {
				TModule module = ((N4JSResource) res).getModule();
				if (module != null) {
					String qName = module.getQualifiedName();
					if (Objects.equals(qName, moduleName)
							|| qName.endsWith(N4JSQualifiedNameConverter.DELIMITER + moduleName)) {

						return findFirstInModule(module, typeToSearch);
					}
				}
			}
		}

		fail("no remote module found with name '" + moduleName + "'");
		return null; // unreachable code
	}

	private <T extends EObject> T findFirstInModule(TModule module, Class<T> typeToSearch) {
		T result = findFirstOf(module.eAllContents(), typeToSearch, null);
		if (result == null) {
			fail("no node of type '" + typeToSearch.getSimpleName() + "' found in original TModule");
		}
		return result;
	}

	/** Find first element of given type in intermediate model; throw assertion error if not found. */
	protected <T extends EObject> T findFirstInIM(TranspilerState state, Class<T> typeToSearch) {
		return findFirstInIM(state, typeToSearch, x -> true);
	}

	/** Find first element of given type in intermediate model; throw assertion error if not found. */
	protected <T extends EObject> T findFirstInIM(TranspilerState state, Class<T> typeToSearch,
			Function<T, Boolean> condition) {

		T result = findFirstOf(state.im.eAllContents(), typeToSearch, condition);
		if (result == null) {
			fail("no node of type '" + typeToSearch.getSimpleName() + "' found in intermediate model");
		}
		return result;
	}

	/** Traverse the content of a container, search first object of a certain type fulfilling the given condition. */
	protected <T extends EObject> T findFirstOf(EObject container, Class<T> typeToSearch,
			Function<T, Boolean> condition) {

		T result = findFirstOf(container.eAllContents(), typeToSearch, condition);
		if (result == null) {
			fail("no node of type '" + typeToSearch.getSimpleName() + "' found in container " + container);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected <T extends EObject> T findFirstOf(Iterator<EObject> iter, Class<T> typeToSearch,
			Function<T, Boolean> condition) {

		return (T) findFirst(iter, elem -> typeToSearch.isAssignableFrom(elem.getClass())
				&& (condition == null ? true : condition.apply((T) elem)));
	}

	/**
	 * Perform some consistency checks on the transpiler state. For example, this asserts that no node in the
	 * intermediate model has a direct cross-reference to the original AST or an original TModule element.
	 */
	public void validateState(TranspilerState state) throws AssertionError {
		transpilerDebugUtils.validateState(state, false);
	}

	protected void assertNoTracing(TranspilerState state, EObject elementInIntermediateModel) {
		assertFalse("element should not have any tracing information",
				state.tracer.containsKey(elementInIntermediateModel));
		// note: cannot use Tracer#getOriginalASTNode() in previous line, because it would throw an exception if
		// elementInIntermediateModel is not contained in the intermediate model, which is often the case here
	}

	protected void assertTracing(TranspilerState state, EObject elementInIntermediateModel,
			EObject expectedOriginalASTNode) {
		assertSame("wrong tracing", expectedOriginalASTNode,
				state.tracer.getOriginalASTNode(elementInIntermediateModel));
	}

	protected void assertNumOfSymbolTableEntries(TranspilerState state, int expected) {
		SymbolTable st = state.im.getSymbolTable();
		assertNotNull("symbol table should not be null", st);
		assertEquals("wrong number of symbol table entries", expected, st.getEntries().size());
	}

	/**
	 * Assert that the symbol table contains exactly one entry for the given name and return this entry.
	 * <p>
	 * NOTE: in general is is legal for the symbol table entry to contain more than one element for a particular name
	 * (e.g. two members of different classifiers may have the same name) and therefore searching symbol table entries
	 * by name is usually a bad idea (except for internal STEs, where only one per name should exist); however, during
	 * testing the assertions of this method are helpful.
	 */
	protected SymbolTableEntry getSymbolTableEntry(TranspilerState state, String name) {
		// obtain symbol table
		SymbolTable st = state.im.getSymbolTable();
		assertNotNull("symbol table should not be null", st);
		// search through the list of symbol table entries
		List<SymbolTableEntry> allMatches = toList(filter(st.getEntries(), e -> Objects.equals(e.getName(), name)));
		if (allMatches.isEmpty()) {
			fail("no symbol table entry found for name: " + name);
		} else if (allMatches.size() > 1) {
			fail("multiple symbol table entries with name \"" + name + "\" found");
		}
		return allMatches.get(0);
	}

	/** Return an asserter to check the properties of the given symbol table entry. */
	protected SymbolTableEntryAsserter assertSymbolTableEntry(TranspilerState state, String name) {
		return new SymbolTableEntryAsserter(getSymbolTableEntry(state, name));
	}

	/** Return an asserter to check the properties of the given symbol table entry. */
	protected SymbolTableEntryAsserter assertSymbolTableEntry(SymbolTableEntry actual) {
		return new SymbolTableEntryAsserter(actual);
	}

	@Data
	protected static class SymbolTableEntryAsserter {
		SymbolTableEntry actual;

		public SymbolTableEntryAsserter(SymbolTableEntry actual) {
			this.actual = actual;
		}

		public SymbolTableEntryAsserter name(String expectedName) {
			assertEquals("symbol table entries have different name",
					expectedName, actual.getName());
			return this;
		}

		public SymbolTableEntryAsserter elementsOfThisName(NamedElement... expectedElementsOfThisName) {
			assertEquals("symbol table entries have different elementsOfThisName",
					Arrays.asList(expectedElementsOfThisName), actual.getElementsOfThisName());
			return this;
		}

		public SymbolTableEntryAsserter referencingElements(ReferencingElement_IM... expectedReferencingElements) {
			assertEquals("symbol table entries have different referencingElements",
					Arrays.asList(expectedReferencingElements), actual.getReferencingElements());
			return this;
		}

		public SymbolTableEntryAsserter originalTarget(IdentifiableElement expectedOriginalTarget) {
			if (actual instanceof SymbolTableEntryOriginal) {
				assertEquals("symbol table entries have different original target",
						expectedOriginalTarget, ((SymbolTableEntryOriginal) actual).getOriginalTarget());
			} else {
				fail("symbol table entry was expected to be a SymbolTableEntryOriginal, but was: "
						+ actual.eClass().getName());
			}
			return this;
		}

		public SymbolTableEntryAsserter importSpecifier(ImportSpecifier expectedImportSpecifier) {
			if (actual instanceof SymbolTableEntryOriginal) {
				assertEquals("symbol table entries have different importSpecifier",
						expectedImportSpecifier, ((SymbolTableEntryOriginal) actual).getImportSpecifier());
			} else {
				fail("symbol table entry was expected to be a SymbolTableEntryOriginal, but was: "
						+ actual.eClass().getName());
			}
			return this;
		}
	}

	protected TranspilerState createTranspilerState(CharSequence code) {
		return createTranspilerState(code, null);
	}

	protected TranspilerState createTranspilerState(CharSequence code, ResourceSet resourceSet) {
		Script script = resourceSet != null ? createScript(code, resourceSet) : createScript(code);
		List<Issue> issues = valTestHelepr.validate(script);
		assertEquals("code should have no errors or warnings", 0, issues.size());
		TranspilerState state = preparationStep.prepare(script, GENERATOR_OPTIONS);
		return state;
	}

	protected Script createScript(CharSequence code) {
		return createScript(code, null);
	}

	protected Script createScript(CharSequence code, ResourceSet resourceSet) {
		try {
			if (resourceSet != null) {
				return parseHelper.parse(code, toTestProjectURI("Main.n4js"), resourceSet);
			} else {
				return parseHelper.parseN4js(code);
			}
		} catch (Exception e) { // catching exception here only to get rid of "unhandled exception" warning
			throw new WrappedException("exception while parsing", e);
		}
	}

	protected ResourceSet createResourceSet() {
		return resourceSetProvider.get();
	}

	protected ResourceSet addFileN4js(ResourceSet resourceSet, String name, CharSequence code) {
		return addFile(resourceSet, name, JavaScriptVariant.n4js, code);
	}

	protected ResourceSet addFileN4jsd(ResourceSet resourceSet, String name, CharSequence code) {
		return addFile(resourceSet, name, JavaScriptVariant.external, code);
	}

	private ResourceSet addFile(ResourceSet resourceSet, String name, JavaScriptVariant variant, CharSequence code) {
		if (variant != JavaScriptVariant.n4js && variant != JavaScriptVariant.external) {
			throw new IllegalArgumentException("variant must be n4js or external");
		}
		String fileExt = variant == JavaScriptVariant.n4js ? N4JSGlobals.N4JS_FILE_EXTENSION
				: N4JSGlobals.N4JSD_FILE_EXTENSION;
		String uriStr = (!name.startsWith("/") ? "/" : "") + name + "." + fileExt;
		URI uri = toTestProjectURI(uriStr);
		try {
			resHelper.resource(code, uri, resourceSet);
		} catch (Exception e) {
			throw new WrappedException("exception while creating an N4JS resource from CharSequence", e);
		}
		return resourceSet;
	}

	protected TranspilerState prepareAndTransform(N4JSResource resourceToTranspile) {
		TranspilerState state = esTranspiler.testPrepare(resourceToTranspile,
				GENERATOR_OPTIONS);
		esTranspiler.testTransform(state);
		return state;
	}

	/** applies all transformation on given prepared {@code state}, returns the state again. */
	protected TranspilerState transform(TranspilerState state) {
		esTranspiler.testTransform(state);
		return state;
	}

	/** Helper method for transpiler checking */
	public void assertCompileResult(Script scriptNode, String expectedTranspilerText) throws AssertionError {
		assertCompileResult(scriptNode, GENERATOR_OPTIONS, expectedTranspilerText);
	}

	/** Helper method for transpiler checking */
	public void assertCompileResult(Script scriptNode, GeneratorOption[] options, String expectedTranspilerText)
			throws AssertionError {

		// As long as Pretty print is not here, we get a dump of the structure
		String generatedResult = esSubGen.getCompileResultAsText(scriptNode, options);

		// ignoring pretty printing, we want to compare:
		AbstractTranspilerTest.assertSameExceptWhiteSpace(expectedTranspilerText, generatedResult);
	}

	/** ensures string-equality when all whitespaces are removed on {@code expected} and {@code actual} parameter */
	static public void assertSameExceptWhiteSpace(String expected, String actual) throws AssertionError {
		try {
			assertEquals(stripWS(expected), stripWS(actual));
		} catch (AssertionError ae) {
			System.out.println("==== expected : ====");
			System.out.println(expected);
			System.out.println("====   actual : ====");
			System.out.println(actual);
			System.out.println("===  end actual  ====");
			throw ae;
		}
	}

	/** removes all whitespaces */
	static public String stripWS(String withSpace) {
		return withSpace.replaceAll("\\s", "");
	}

	public static void resolveLazyRefs(Script script) {
		EcoreUtil2.resolveLazyCrossReferences(script.eResource(), CancelIndicator.NullImpl);
	}

	/**
	 * installs common "src/ExportedStuff.n4js" used in _04 and downward. Must be called as first resource to load,
	 * returns the commmon ResourceSet to share.
	 */
	public ResourceSet installExportedScript() throws Throwable {
		String exportedScript = """
				export public class C1 { public m1(){} }
				export public class C2 { public m2(){} }
				""";
		Resource exported = resHelper.resource(exportedScript,
				toTestProjectURI("ExportedStuff." + N4JSGlobals.N4JS_FILE_EXTENSION));
		ResourceSet resSet = exported.getResourceSet();
		return resSet;
	}

	/** Install the content as 'srcJsScript_01.js' */
	public Resource installJSScript(String jsScript) throws Throwable {
		Resource res = resHelper.resource(jsScript, toTestProjectURI("JsScript_01." + N4JSGlobals.JS_FILE_EXTENSION));
		return res;
	}

	/** Install the content as 'srcJsxScript_01.jsx' */
	public Resource installJSXScript(String jsxScript) throws Throwable {
		Resource res = resHelper.resource(jsxScript,
				toTestProjectURI("JsxScript_01." + N4JSGlobals.JSX_FILE_EXTENSION));
		return res;
	}

	/**
	 * returns the i-th statement from a block without counting a potential "var $captuer ..." statement at position 0
	 */
	public Statement statementAt_skipArgsCapture_get(Block block, int i) {
		if (block == null)
			return null;
		if (block.getStatements().isEmpty()) {
			return null;
		}
		boolean hasCapture = false;
		try {
			hasCapture = BlockTransformation.$CAPTURE_ARGS
					.equals(((VariableStatement) block.getStatements().get(0)).getVarDecl().get(0).getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		int idx = hasCapture ? i + 1 : i + 0;
		if (idx >= block.getStatements().size())
			return null;
		return block.getStatements().get(idx);
	}

	/** assert to match the pattern in the compiled output. */
	public void assertCompileResultMatches(Script scriptNode, Pattern pattern) throws AssertionError {
		String generatedResult = esSubGen.getCompileResultAsText(scriptNode, GENERATOR_OPTIONS);
		Matcher matcher = pattern.matcher(generatedResult);
		if (!matcher.find())
			throw new AssertionError("The generated output doesn't match the pattern " + pattern.toString());
	}

	/** assert to <b>NOT</b> match the pattern in the compiled output. */
	public void assertCompileResultDoesNotMatch(Script scriptNode, Pattern pattern) throws AssertionError {
		String generatedResult = esSubGen.getCompileResultAsText(scriptNode, GENERATOR_OPTIONS);
		Matcher matcher = pattern.matcher(generatedResult);
		if (matcher.find())
			throw new AssertionError("The generated output unexpectedly matches the pattern " + pattern.toString());
	}

	public URI toTestProjectURI(String projectRelativePath) {
		return mockWorkspace.toTestProjectURI(projectRelativePath);
	}
}
