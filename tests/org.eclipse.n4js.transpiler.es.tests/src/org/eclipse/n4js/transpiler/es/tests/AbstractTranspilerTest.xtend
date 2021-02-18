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
package org.eclipse.n4js.transpiler.es.tests

import com.google.common.collect.Lists
import com.google.inject.Inject
import com.google.inject.Provider
import java.util.regex.Pattern
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.common.util.WrappedException
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.n4js.JSActivationUtil
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.N4JSParseHelper
import org.eclipse.n4js.N4JSTestHelper
import org.eclipse.n4js.generator.GeneratorOption
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.ImportSpecifier
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.NamedElement
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.n4JS.TypeReferenceNode
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.naming.N4JSQualifiedNameConverter
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.transpiler.PreparationStep
import org.eclipse.n4js.transpiler.TranspilerState
import org.eclipse.n4js.transpiler.es.EcmaScriptSubGenerator
import org.eclipse.n4js.transpiler.es.EcmaScriptTranspiler
import org.eclipse.n4js.transpiler.es.transform.BlockTransformation
import org.eclipse.n4js.transpiler.im.ReferencingElement_IM
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.transpiler.utils.TranspilerDebugUtils
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.util.ResourceHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.eclipse.xtext.util.CancelIndicator

import static org.junit.Assert.*

/**
 */
abstract class AbstractTranspilerTest {

	protected static final GeneratorOption[] GENERATOR_OPTIONS = #[ GeneratorOption.ES5plus ];

	@Inject protected extension ResourceHelper;
	@Inject protected extension N4JSTestHelper;
	@Inject protected extension N4JSParseHelper;
	@Inject protected extension ValidationTestHelper;

	@Inject private Provider<XtextResourceSet> resourceSetProvider;
	@Inject private PreparationStep preparationStep;
	@Inject private EcmaScriptTranspiler esTranspiler;
	@Inject	private TranspilerDebugUtils transpilerDebugUtils;
	@Inject protected EcmaScriptSubGenerator esSubGen

	new() {
		JSActivationUtil.enableJSSupport();
	}


	/** Find first element of given type in original AST; throw assertion error if not found. */
	def protected <T extends EObject> T findFirstInAST(TranspilerState state, Class<T> typeToSearch) {
		val result = state.resource.script.eAllContents.findFirst[typeToSearch.isAssignableFrom(it.class)] as T;
		if(result===null)
			fail("no node of type '" + typeToSearch.simpleName + "' found in original AST");
		return result;
	}

	/** Find first element of given type in original TModule; throw assertion error if not found. */
	def protected <T extends EObject> T findFirstInModule(TranspilerState state, Class<T> typeToSearch) {
		return findFirstInModule(state.resource.module, typeToSearch);
	}

	/** Find first element of given type in a remote TModule of the given name; throw assertion error if not found. */
	def protected <T extends EObject> T findFirstInRemoteModule(TranspilerState state, String moduleName, Class<T> typeToSearch) {
		val resSet = state.resource.resourceSet;
		val remoteModule = resSet.resources.filter(N4JSResource).filter[it!==state.resource].map[module].filterNull
		.findFirst[
			qualifiedName==moduleName || qualifiedName.endsWith(N4JSQualifiedNameConverter.DELIMITER + moduleName)
		];
		if(remoteModule===null) {
			fail("no remote module found with name '"+moduleName+"'");
		}
		return findFirstInModule(remoteModule, typeToSearch);
	}

	def private <T extends EObject> T findFirstInModule(TModule module, Class<T> typeToSearch) {
		val result = module.eAllContents.findFirst[typeToSearch.isAssignableFrom(it.class)] as T;
		if(result===null)
			fail("no node of type '" + typeToSearch.simpleName + "' found in original TModule");
		return result;
	}

	/** Find first element of given type in intermediate model; throw assertion error if not found. */
	def protected <T extends EObject> T findFirstInIM(TranspilerState state, Class<T> typeToSearch) {
		findFirstInIM(state,typeToSearch, [true])
	}

	/** Find first element of given type in intermediate model; throw assertion error if not found. */
	def protected <T extends EObject> T findFirstInIM(TranspilerState state, Class<T> typeToSearch, (T)=>boolean condition) {
		val result = state.im.eAllContents.findFirst[typeToSearch.isAssignableFrom(it.class) && condition.apply(it as T)] as T;
		if(result===null)
			fail("no node of type '" + typeToSearch.simpleName + "' found in intermediate model");
		return result;
	}

	/** Traverse the content of a container, search first object of a certain type fulfilling the given condition. */
	def protected <T extends EObject> T findFirstOf(EObject container, Class<T> typeToSearch, (T)=>boolean condition) {
		val result = container.eAllContents.findFirst[typeToSearch.isAssignableFrom(it.class) && condition.apply(it as T)] as T;
		if(result===null)
			fail("no node of type '" + typeToSearch.simpleName + "' found in container "+container);
		return result;
	}


	/**
	 * Perform some consistency checks on the transpiler state. For example, this asserts that no node in the
	 * intermediate model has a direct cross-reference to the original AST or an original TModule element.
	 */
	def public void validateState(TranspilerState state) throws AssertionError {
		transpilerDebugUtils.validateState(state, false);
	}


	def protected void assertNoTracing(TranspilerState state, EObject elementInIntermediateModel) {
		assertFalse("element should not have any tracing information",
			state.tracer.containsKey(elementInIntermediateModel));
		// note: cannot use Tracer#getOriginalASTNode() in previous line, because it would throw an exception if
		// elementInIntermediateModel is not contained in the intermediate model, which is often the case here
	}
	def protected void assertTracing(TranspilerState state, EObject elementInIntermediateModel, EObject expectedOriginalASTNode) {
		assertSame("wrong tracing", expectedOriginalASTNode,
			state.tracer.getOriginalASTNode(elementInIntermediateModel));
	}



	def protected void assertNumOfSymbolTableEntries(TranspilerState state, int expected) {
		val st = state.im.symbolTable;
		assertNotNull("symbol table should not be null", st);
		assertEquals("wrong number of symbol table entries", expected, st.entries.size);
	}

	/**
	 * Assert that the symbol table contains exactly one entry for the given name and return this entry.
	 * <p>
	 * NOTE: in general is is legal for the symbol table entry to contain more than one element for a particular name
	 * (e.g. two members of different classifiers may have the same name) and therefore searching symbol table entries
	 * by name is usually a bad idea (except for internal STEs, where only one per name should exist); however, during
	 * testing the assertions of this method are helpful.
	 */
	def protected SymbolTableEntry getSymbolTableEntry(TranspilerState state, String name) {
		// obtain symbol table
		val st = state.im.symbolTable;
		assertNotNull("symbol table should not be null", st);
		// search through the list of symbol table entries
		val allMatches = st.entries.filter[it.name==name].toList;
		if(allMatches.empty) {
			fail("no symbol table entry found for name: " + name);
		} else if(allMatches.size>1) {
			fail("multiple symbol table entries with name \"" + name + "\" found");
		}
		return allMatches.head;
	}

	/** Return an asserter to check the properties of the given symbol table entry. */
	def protected SymbolTableEntryAsserter assertSymbolTableEntry(TranspilerState state, String name) {
		return new SymbolTableEntryAsserter(getSymbolTableEntry(state, name));
	}
	/** Return an asserter to check the properties of the given symbol table entry. */
	def protected SymbolTableEntryAsserter assertSymbolTableEntry(SymbolTableEntry actual) {
		return new SymbolTableEntryAsserter(actual);
	}
	@Data
	protected static class SymbolTableEntryAsserter {
		SymbolTableEntry actual;

		def SymbolTableEntryAsserter name(String expectedName) {
			assertEquals("symbol table entries have different name",
				expectedName, actual.name);
			return this;
		}
		def SymbolTableEntryAsserter elementsOfThisName(NamedElement... expectedElementsOfThisName) {
			assertEquals("symbol table entries have different elementsOfThisName",
				expectedElementsOfThisName.toList, actual.elementsOfThisName);
			return this;
		}
		def SymbolTableEntryAsserter referencingElements(ReferencingElement_IM... expectedReferencingElements) {
			val expectedAndImplicitReferencingElements = Lists.<ReferencingElement_IM>newArrayList();
			for (elem : expectedReferencingElements) {
				expectedAndImplicitReferencingElements.add(elem);
				if (elem.eContainmentFeature === N4JSPackage.Literals.TYPE_REFERENCE_NODE__TYPE_REF_IN_AST) {
					val container = elem.eContainer as TypeReferenceNode<?>;
					val cachedTypeRef = container.cachedProcessedTypeRef;
					if (cachedTypeRef !== null && cachedTypeRef !== elem) {
						// if TypeReferenceNode#typeRefInAST references a SymbolTableEntry, then also
						// TypeReferenceNode#cachedProcessedTypeRef will reference that SymbolTableEntry:
						expectedAndImplicitReferencingElements.add(cachedTypeRef as ReferencingElement_IM);
					}
				}
			}
			assertEquals("symbol table entries have different referencingElements",
				expectedAndImplicitReferencingElements.toList, actual.referencingElements);
			return this;
		}
		def SymbolTableEntryAsserter originalTarget(IdentifiableElement expectedOriginalTarget) {
			if(actual instanceof SymbolTableEntryOriginal) {
				assertEquals("symbol table entries have different original target",
					expectedOriginalTarget, actual.originalTarget);
			} else {
				fail("symbol table entry was expected to be a SymbolTableEntryOriginal, but was: " + actual.eClass.name);
			}
			return this;
		}
		def SymbolTableEntryAsserter importSpecifier(ImportSpecifier expectedImportSpecifier) {
			if(actual instanceof SymbolTableEntryOriginal) {
				assertEquals("symbol table entries have different importSpecifier",
					expectedImportSpecifier, actual.importSpecifier);
			} else {
				fail("symbol table entry was expected to be a SymbolTableEntryOriginal, but was: " + actual.eClass.name);
			}
			return this;
		}
	}

	def protected TranspilerState createTranspilerState(CharSequence code) {
		return createTranspilerState(code, null);
	}
	def protected TranspilerState createTranspilerState(CharSequence code, ResourceSet resourceSet) {
		val script = if(resourceSet!==null) code.createScript(resourceSet) else code.createScript;
		val issues = script.validate;
		assertEquals("code should have no errors or warnings", 0, issues.size);
		val state = preparationStep.prepare(script, GENERATOR_OPTIONS);
		return state;
	}

	def protected Script createScript(CharSequence code) {
		return createScript(code, null);
	}
	def protected Script createScript(CharSequence code, ResourceSet resourceSet) {
		try {
			if(resourceSet!==null) {
				return code.parse(URI.createURI("src/Main.n4js"), resourceSet);
			} else {
				return code.parseN4js;
			}
		} catch(Exception e) { // catching exception here only to get rid of "unhandled exception" warning
			throw new WrappedException("exception while parsing", e);
		}
	}

	def protected ResourceSet createResourceSet() {
		return resourceSetProvider.get();
	}
	def protected ResourceSet addFileN4js(ResourceSet resourceSet, String name, CharSequence code) {
		return addFile(resourceSet, name, JavaScriptVariant.n4js, code);
	}
	def protected ResourceSet addFileN4jsd(ResourceSet resourceSet, String name, CharSequence code) {
		return addFile(resourceSet, name, JavaScriptVariant.external, code);
	}
	def private ResourceSet addFile(ResourceSet resourceSet, String name, JavaScriptVariant variant, CharSequence code) {
		if(variant!==JavaScriptVariant.n4js && variant!==JavaScriptVariant.external) {
			throw new IllegalArgumentException("variant must be n4js or external");
		}
		val fileExt = if(variant===JavaScriptVariant.n4js) {
			N4JSGlobals.N4JS_FILE_EXTENSION
		} else {
			N4JSGlobals.N4JSD_FILE_EXTENSION
		};
		val uriStr = "src" + (if(!name.startsWith("/")) "/" else "") + name + "." + fileExt;
		val uri = URI.createURI(uriStr);
		try {
			code.resource(uri, resourceSet);
		} catch(Exception e) {
			throw new WrappedException("exception while creating an N4JS resource from CharSequence",e);
		}
		return resourceSet;
	}


	def protected TranspilerState prepareAndTransform(N4JSResource resourceToTranspile) {
		val state = esTranspiler.testPrepare(resourceToTranspile, GENERATOR_OPTIONS);
		esTranspiler.testTransform(state);
		return state;
	}

	/** applies all transformation on given prepared {@code state}, returns the state again.  */
	def protected TranspilerState transform(TranspilerState state) {
		esTranspiler.testTransform(state);
		return state
	}

	/** Helper method for transpiler checking */
	def assertCompileResult(Script scriptNode, String expectedTranspilerText ) throws AssertionError {
		assertCompileResult(scriptNode, GENERATOR_OPTIONS, expectedTranspilerText);
	}

	/** Helper method for transpiler checking */
	def assertCompileResult(Script scriptNode, GeneratorOption[] options, String expectedTranspilerText ) throws AssertionError {

		// As long as Pretty print is not here, we get a dump of the structure
		val generatedResult = esSubGen.getCompileResultAsText(scriptNode, options);

		// ignoring pretty printing, we want to compare:
		AbstractTranspilerTest.assertSameExceptWhiteSpace ( expectedTranspilerText, generatedResult );
	}

	/** ensures string-equality when all whitespaces are removed on {@code expected} and {@code actual} parameter*/
	static def assertSameExceptWhiteSpace( String expected, String actual) throws AssertionError {
		assertSameExceptWhiteSpace(null,expected,actual);
	}

	/** ensures string-equality when all whitespaces are removed on {@code expected} and {@code actual} parameter*/
	static def assertSameExceptWhiteSpace(String /*nullable*/ msg,String expected, String actual) throws AssertionError {
		try {
			assertEquals( expected.stripWS, actual.stripWS )
		} catch( AssertionError ae ) {
			println("===== expected : ======")
			println( expected )
			println("=====   actual : ======")
			println( actual )
			println("====  end actual  =====")
			throw ae;
		}
	}


	/** removes all whitespaces */
	static def String stripWS(String withSpace) { withSpace.replaceAll("\\s",""); }


	def static resolveLazyRefs(Script script) {
		EcoreUtil2.resolveLazyCrossReferences( script.eResource, CancelIndicator.NullImpl  )
	}

	/** installs common "src/ExportedStuff.n4js" used in _04 and downward.
	 * Must be called as first resource to load,
	 * returns the commmon ResourceSet to share.
	 */
	def ResourceSet installExportedScript() throws Throwable {
		val exportedScript =
   		'''
   		export public class C1 { public m1(){} }
   		export public class C2 { public m2(){} }
   		'''
   		val exported = exportedScript.resource(URI.createURI("src/ExportedStuff."+N4JSGlobals.N4JS_FILE_EXTENSION));
		val resSet = exported.resourceSet;
		return resSet;
	}


	/** Install the content as 'srcJsScript_01.js' */
	def Resource installJSScript(String jsScript) throws Throwable {
   		val res = jsScript.resource(URI.createURI("src/JsScript_01."+N4JSGlobals.JS_FILE_EXTENSION));
		return res;
	}

	/** Install the content as 'srcJsxScript_01.jsx' */
	def Resource installJSXScript(String jsxScript) throws Throwable {
   		val res = jsxScript.resource(URI.createURI("src/JsxScript_01."+N4JSGlobals.JSX_FILE_EXTENSION));
		return res;
	}


	/** returns the i-th statement from a block without counting a potantial "var $captuer ..." statement at position 0 */
	def Statement statementAt_skipArgsCapture_get(Block block, int i) {
		if( block === null ) return null;
		if( block.statements.isEmpty ) return null;
		var hasCapture = false;
		try {
			hasCapture = ( block.statements.get(0) as VariableStatement).varDecl.get(0).name === BlockTransformation.$CAPTURE_ARGS ;
		} catch (Exception e){}
		val idx = if( hasCapture ) i+1 else i+0;
		if( idx >= block.statements.size ) return null;
		return block.statements.get(idx);
	}


	/** assert to match the pattern in the compiled output. */
	def assertCompileResultMatches(Script scriptNode, Pattern pattern) throws AssertionError {
		val generatedResult = esSubGen.getCompileResultAsText(scriptNode, GENERATOR_OPTIONS);
		val matcher = pattern.matcher(generatedResult);
		if( ! matcher.find ) throw new AssertionError("The generated output doesn't match the pattern "+pattern.toString );
	}

	/** assert to <b>NOT</b> match the pattern in the compiled output. */
	def assertCompileResultDoesNotMatch(Script scriptNode, Pattern pattern) throws AssertionError {
		val generatedResult = esSubGen.getCompileResultAsText(scriptNode, GENERATOR_OPTIONS);
		val matcher = pattern.matcher(generatedResult);
		if( matcher.find ) throw new AssertionError("The generated output unexpectedly matches the pattern "+pattern.toString );
	}
}
