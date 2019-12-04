/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.resource

import com.google.common.collect.Lists
import java.util.Collection
import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.n4js.AbstractN4JSTest
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.xtend.lib.annotations.Data
import org.junit.Test

/**
 * Test the various load states an N4JSResource can be in.
 */
class N4JSResourceLoadStatesTest extends AbstractN4JSTest {

	static val SAMPLE_FILE = URI.createURI("src/org/eclipse/n4js/tests/resource/N4JSResourceLoadStatesTest.n4js");

	private static enum State {
		CREATED, CREATED_PRIME, LOADED, PRE_LINKED, FULLY_INITIALIZED, FULLY_PROCESSED,
		LOADED_FROM_DESC, LOADED_FROM_DESC_PRIME,
		FULLY_INITIALIZED_RECONCILED, FULLY_PROCESSED_RECONCILED
	}
	private static enum ParseResult {
		NULL, AVAILABLE
	}
	private static enum AST {
		NULL, PROXY, LAZY, AVAILABLE
	}
	private static enum Module {
		NULL, STUBS, DEFERRED, AVAILABLE
	}
	private static enum ASTMetaCache {
		NULL, AVAILABLE
	}

	@Data
	static class StateInfo {
		State state;
		ParseResult parseResult;
		AST ast;
		Module module;
		ASTMetaCache astMetaCache;
		Boolean loaded;
		Boolean fullyInitialized;
		Boolean fullyProcessed;
		Boolean reconciled;
	}

	static val Boolean INDETERMINATE = null;

	static val loadStateInfos = #[
		new StateInfo(State.CREATED,                      ParseResult.NULL,      AST.NULL,      Module.NULL,      ASTMetaCache.NULL,      false,          false, false, false ),
		new StateInfo(State.CREATED_PRIME,                ParseResult.NULL,      AST.NULL,      Module.NULL,      ASTMetaCache.NULL,      false,          true,  false, false ),
		new StateInfo(State.LOADED,                       ParseResult.AVAILABLE, AST.LAZY,      Module.NULL,      ASTMetaCache.NULL,      true,           false, false, false ),
		new StateInfo(State.PRE_LINKED,                   ParseResult.AVAILABLE, AST.LAZY,      Module.STUBS,     ASTMetaCache.NULL,      true,           true,  false, false ),
		new StateInfo(State.FULLY_INITIALIZED,            ParseResult.AVAILABLE, AST.LAZY,      Module.DEFERRED,  ASTMetaCache.NULL,      true,           true,  false, false ),
		new StateInfo(State.FULLY_PROCESSED,              ParseResult.AVAILABLE, AST.AVAILABLE, Module.AVAILABLE, ASTMetaCache.AVAILABLE, true,           true,  true,  false ),
		new StateInfo(State.LOADED_FROM_DESC,             ParseResult.NULL,      AST.PROXY,     Module.AVAILABLE, ASTMetaCache.NULL,      INDETERMINATE,  true,  true,  false ),
		new StateInfo(State.LOADED_FROM_DESC_PRIME,       ParseResult.NULL,      AST.PROXY,     Module.DEFERRED,  ASTMetaCache.NULL,      INDETERMINATE,  true,  true,  false ),
		new StateInfo(State.FULLY_INITIALIZED_RECONCILED, ParseResult.AVAILABLE, AST.LAZY,      Module.AVAILABLE, ASTMetaCache.NULL,      INDETERMINATE,  true,  false, true  ),
		new StateInfo(State.FULLY_PROCESSED_RECONCILED,   ParseResult.AVAILABLE, AST.AVAILABLE, Module.AVAILABLE, ASTMetaCache.AVAILABLE, INDETERMINATE,  true,  true,  true  )
	];



	// ###############################################################################################################
	// TEST CASES

	@Test
	def void testStateCreated1() throws Exception {
		val res = createFromFile(SAMPLE_FILE);
		res.assertState(State.CREATED);
	}

	@Test
	def void testStateCreated2() throws Exception {
		val res = createFromFile(SAMPLE_FILE);
		res.load(emptyMap);
		res.unload();
		res.assertState(State.CREATED);
	}

	@Test
	def void testStateCreated3() throws Exception {
		val res = createFromFile(SAMPLE_FILE);
		res.load(emptyMap);
		res.unloadAST();
		res.assertState(State.CREATED);
	}

	@Test
	def void testStateCreated4() throws Exception {
		val res = createFromFile(SAMPLE_FILE);
		res.load(emptyMap);
		res.contents; // trigger installation of derived state (i.e. types builder)
		// NOTE: for some reason, Xtext does not clear flag 'fullyInitialized' upon unload; that is done lazily when
		// #load() is invoked later. Hence, we have to issue #discardDerivedState() explicitly to get back to state
		// CREATED instead of CREATED_PRIME.
		res.discardDerivedState();
		res.unload();
		res.assertState(State.CREATED);
	}

	@Test
	def void testStateCreatedPrime() throws Exception {
		val res = createFromFile(SAMPLE_FILE);
		res.load(emptyMap);
		res.contents; // trigger installation of derived state (i.e. types builder)
		res.unload();
		res.assertState(State.CREATED_PRIME);
	}

	@Test
	def void testStateLoaded1() throws Exception {
		val res = createFromFile(SAMPLE_FILE);
		res.load(emptyMap);
		res.assertState(State.LOADED);
	}

	@Test
	def void testStateLoaded2() throws Exception {
		val res = createFromFile(SAMPLE_FILE);
		res.load(emptyMap);
		res.unload();
		res.assertState(State.CREATED);
		res.load(emptyMap);
		res.assertState(State.LOADED);
	}

	@Test
	def void testPreLinked() throws Exception {
		val res = createFromFile(SAMPLE_FILE);
		res.load(emptyMap);
		res.installDerivedState(true);
		res.assertState(State.PRE_LINKED);
	}

	@Test
	def void testStateFullyInitialized1() throws Exception {
		val res = createFromFile(SAMPLE_FILE);
		res.load(emptyMap);
		res.contents; // trigger installation of derived state (i.e. types builder)
		res.assertState(State.FULLY_INITIALIZED);
	}

	@Test
	def void testStateFullyInitialized2() throws Exception {
		val res = createFromFile(SAMPLE_FILE);
		res.load(emptyMap);
		res.contents; // trigger installation of derived state (i.e. types builder)
		res.discardDerivedState();
		res.unload();
		res.assertState(State.CREATED);
		res.load(emptyMap);
		res.assertState(State.LOADED);
		res.contents; // trigger installation of derived state (i.e. types builder)
		res.assertState(State.FULLY_INITIALIZED);
	}

	/** Make sure that we can reach state 'fullyInitialized' after having been in state 'pre-linked'. */
	@Test
	def void testStateFullyInitialized3() throws Exception {
		val res = createFromFile(SAMPLE_FILE);
		res.load(emptyMap);
		res.installDerivedState(true);
		res.assertState(State.PRE_LINKED);
		res.discardDerivedState();
		res.installDerivedState(false);
		res.assertState(State.FULLY_INITIALIZED);
	}

	@Test
	def void testStateFullyProcessed1() throws Exception {
		val res = createFromFile(SAMPLE_FILE);
		res.load(emptyMap);
		res.contents; // trigger installation of derived state (i.e. types builder)
		res.performPostProcessing();
		res.assertState(State.FULLY_PROCESSED);
	}

	@Test
	def void testStateFullyProcessed2() throws Exception {
		val res = createFromFile(SAMPLE_FILE);
		res.load(emptyMap);
		// do *not* trigger installation of derived state explicitly, in this test case
		res.performPostProcessing();
		res.assertState(State.FULLY_PROCESSED);
	}

	@Test
	def void testStateFullyProcessed_triggeredOnlyThroughProxyResolution() throws Exception {
		val res = createFromFile(SAMPLE_FILE);
		res.load(emptyMap);
		// do *not* trigger installation of derived state explicitly, in this test case
		// do *not* trigger post-processing explicitly, in this test case
		val idRef = res.script.eAllContents.filter(IdentifierRef).head;
		assertNotNull(idRef);
		idRef.id; // trigger installation of derived state + post-processing by resolving a proxy *without* type inference
		res.assertState(State.FULLY_PROCESSED);
	}

	@Test
	def void testStateLoadedFromDescription1() throws Exception {
		val res = loadFromDescription(SAMPLE_FILE);
		res.assertState(State.LOADED_FROM_DESC);
	}

	@Test
	def void testStateLoadedFromDescription2() throws Exception {
		val res = createFromFile(SAMPLE_FILE);
		res.load(emptyMap);
		res.contents; // trigger installation of derived state (i.e. types builder)
		// do *not* perform post-processing
		res.unloadAST();
		res.assertState(State.LOADED_FROM_DESC_PRIME); // note: when not doing post-processing before, DeferredTypeRefs leak into state LOADED_FROM_DESC
	}

	@Test
	def void testStateLoadedFromDescription3() throws Exception {
		val res = createFromFile(SAMPLE_FILE);
		res.load(emptyMap);
		res.performPostProcessing();
		res.unloadAST();
		res.assertState(State.LOADED_FROM_DESC);
	}

	@Test
	def void testStateLoadedFromDescription4() throws Exception {
		val res = loadFromDescription(SAMPLE_FILE);
		res.unloadAST();
		res.unloadAST();
		res.assertState(State.LOADED_FROM_DESC);
	}

	@Test
	def void testStateReconciled1() throws Exception {
		val res = loadFromDescription(SAMPLE_FILE);
		res.contents.get(0); // trigger demand-loading of AST (with reconciliation)
		res.assertState(State.FULLY_INITIALIZED_RECONCILED);
		res.performPostProcessing(); // trigger post-processing
		res.assertState(State.FULLY_PROCESSED_RECONCILED);
	}

	/** Same as previous method, but use different ways of triggering demand-loading of AST and post-processing. */
	@Test
	def void testStateReconciled2() throws Exception {
		val res = loadFromDescription(SAMPLE_FILE);
		val tClass = res.module.eAllContents.filter(TClass).head;
		assertNotNull(tClass);
		tClass.astElement; // trigger demand-loading of AST (with reconciliation)
		res.assertState(State.FULLY_INITIALIZED_RECONCILED);
		val propAccess = res.script.eAllContents.filter(ParameterizedPropertyAccessExpression).head;
		assertNotNull(propAccess);
		propAccess.property; // trigger post-processing
		res.assertState(State.FULLY_PROCESSED_RECONCILED);
	}



	// ###############################################################################################################
	// ASSERTION OF STATES

	def private void assertState(N4JSResource res, State state) {
		val info = loadStateInfos.findFirst[it.state===state];
		assertNotNull(info);
		switch(info.parseResult) {
			case NULL: {
				assertNull("in state " + state + " parse result should be null", res.basicGetParseResult);
			}
			case AVAILABLE: {
				assertNotNull("in state " + state + " parse result should be available", res.basicGetParseResult);
			}
			default: {
				throw new IllegalStateException("unknown literal: " + info.parseResult);
			}
		}
		switch(info.ast) {
			case NULL: {
				assertNull("in state " + state + " the script should be null", res.script);
			}
			case PROXY: {
				assertTrue("in state " + state + " the script should be a proxy", res.script.eIsProxy);
			}
			case LAZY: {
				assertFalse("in state " + state + " the script should *not* be a proxy", res.script.eIsProxy);
				assertTrue("in state " + state + " the script should contain lazy linking proxies", res.script.hasLazyLinkingProxy);
			}
			case AVAILABLE: {
				assertFalse("in state " + state + " the script should *not* be a proxy", res.script.eIsProxy);
				assertFalse("in state " + state + " the script should *not* contain lazy linking proxies", res.script.hasLazyLinkingProxy);
			}
			default: {
				throw new IllegalStateException("unknown literal: " + info.ast);
			}
		}
		switch(info.module) {
			case NULL: {
				assertNull("in state " + state + " the module should be null", res.module);
			}
			case STUBS: {
				assertNotNull("in state " + state + " the module should *not* be null", res.module);
				assertFalse("in state " + state + " the module should *not* be a proxy", res.module.eIsProxy);
				assertTrue("in state " + state + " the module should contain stubs", res.module.containsStub);
			}
			case DEFERRED: {
				assertNotNull("in state " + state + " the module should *not* be null", res.module);
				assertFalse("in state " + state + " the module should *not* be a proxy", res.module.eIsProxy);
				assertFalse("in state " + state + " the module should *not* contain stubs", res.module.containsStub);
				assertTrue("in state " + state + " the module should contain DeferredTypeRefs", res.module.containsDeferredTypeRef);
			}
			case AVAILABLE: {
				assertNotNull("in state " + state + " the module should *not* be null", res.module);
				assertFalse("in state " + state + " the module should *not* be a proxy", res.module.eIsProxy);
				assertFalse("in state " + state + " the module should *not* contain stubs", res.module.containsStub);
				assertFalse("in state " + state + " the module should *not* contain DeferredTypeRefs", res.module.containsDeferredTypeRef);
			}
			default: {
				throw new IllegalStateException("unknown literal: " + info.module);
			}
		}
		switch (info.astMetaCache) {
			case NULL: {
				assertNull("in state " + state + " the ASTMetaInfoCache should be null", res.getASTMetaInfoCache)
			}
			case AVAILABLE: {
				assertNotNull("in state " + state + " the ASTMetaInfoCache should *not* be null", res.getASTMetaInfoCacheVerifyContext)
			}
		}

		if(info.loaded!==INDETERMINATE) {
			assertEquals("in state " + state + " flag 'loaded' should be " + info.loaded,
				info.loaded, res.loaded);
		}
		if(info.fullyInitialized!==INDETERMINATE) {
			assertEquals("in state " + state + " flag 'fullyInitialized' should be " + info.fullyInitialized,
				info.fullyInitialized, res.fullyInitialized);
		}
		if(info.fullyProcessed!==INDETERMINATE) {
			assertEquals("in state " + state + " flag 'fullyProcessed' should be " + info.fullyProcessed,
				info.fullyProcessed, res.fullyProcessed);
		}
		if(info.reconciled!==INDETERMINATE) {
			assertEquals("in state " + state + " flag 'reconciled' should be " + info.reconciled,
				info.reconciled, res.reconciled);
		}
	}
	
	def private boolean containsDeferredTypeRef(EObject root) {
		return !root.eAllContents.filter(DeferredTypeRef).empty;
	}

	def private boolean hasLazyLinkingProxy(EObject root) {
		val res = root.eResource as N4JSResource;
		return root.eAllContents.exists[eobj|
			eobj.eClass.EReferences.filter[!containment && !transient && !volatile].exists[eref|
				hasLazyLinkingProxy(res, eobj, eref);
			]
		];
	}

	def private boolean hasLazyLinkingProxy(N4JSResource res, EObject eobj, EReference eref) {
		if(eref.isMany) {
			val value = eobj.eGet(eref, false) as Collection<?>;
			return value.filter(EObject).exists[isLazyLinkingProxy(res, it)];
		} else {
			val value = eobj.eGet(eref, false) as EObject;
			return value!==null && isLazyLinkingProxy(res,value);
		}
	}

	def private boolean isLazyLinkingProxy(N4JSResource res, EObject proxy) {
		return proxy.eIsProxy
			&& res.getEncoder().isCrossLinkFragment(res, (proxy as InternalEObject).eProxyURI.fragment());
	}

	def private boolean containsStub(TModule root) {
		return root.eAllContents.filter(TMethod).exists[isStub];
	}

	/**
	 * Tells if the given {@link TMethod} is a stub as created by the types build during pre-linking phase, i.e.
	 * when the types builder is invoked with flag <code>preLinkingPhase</code> set to true. If this method returns
	 * <code>false</code>, the given {@code TMethod} is guaranteed to have been created by the types builder in the
	 * ordinary, main phase (i.e. <code>preLinkingPhase</code> set to false).
	 * <p>
	 * As can be seen in {@code AbstractFunctionDefinitionTypesBuilder#setReturnType(TFunction, FunctionDefinition,
	 * BuiltInTypeScope, boolean)}, return types of methods are created if and only if the types builder is running
	 * in the main phase, not in pre-linking phase.
	 */
	def private boolean isStub(TMethod method) {
		return method.returnTypeRef === null;
	}



	// ###############################################################################################################
	// GENERATE ASCIIDOC TABLE

	def static void main(String[] args) {
		val rows = Lists.newArrayList;
		rows.add(new StringBuilder());
		loadStateInfos.forEach[rows.add(new StringBuilder())];

		rows.makeEqualLengthAndAppend("| ");
		rows.forEach[row, i | row.append(if(i===0) "State" else loadStateInfos.get(i-1).state.label)];
		rows.makeEqualLengthAndAppend(" | ");
		rows.forEach[row, i | row.append(if(i===0) "Parse Result" else loadStateInfos.get(i-1).parseResult.label)];
		rows.makeEqualLengthAndAppend(" | ");
		rows.forEach[row, i | row.append(if(i===0) "AST" else loadStateInfos.get(i-1).ast.label)];
		rows.makeEqualLengthAndAppend(" | ");
		rows.forEach[row, i | row.append(if(i===0) "TModule" else loadStateInfos.get(i-1).module.label)];
		rows.makeEqualLengthAndAppend(" | ");
		rows.forEach[row, i | row.append(if(i===0) "ASTMetaInfoCache" else loadStateInfos.get(i-1).astMetaCache.label)];
		rows.makeEqualLengthAndAppend(" | ");
		rows.forEach[row, i | row.append(if(i===0) "loaded" else loadStateInfos.get(i-1).loaded.label)];
		rows.makeEqualLengthAndAppend(" | ");
		rows.forEach[row, i | row.append(if(i===0) "fullyInitialized" else loadStateInfos.get(i-1).fullyInitialized.label)];
		rows.makeEqualLengthAndAppend(" | ");
		rows.forEach[row, i | row.append(if(i===0) "fullyProcessed" else loadStateInfos.get(i-1).fullyProcessed.label)];
		rows.makeEqualLengthAndAppend(" | ");
		rows.forEach[row, i | row.append(if(i===0) "reconciled" else loadStateInfos.get(i-1).reconciled.label)];

		println('[cols="1h,1,1,1,1,1,1,1,1",options="header"]');
		println('|===');
		println(rows.join('\n'));
		println('|===');
	}

	def static String getLabel(Object obj) {
		return LABELS.get(obj) ?: obj?.toString;
	}

	def static void makeEqualLengthAndAppend(List<StringBuilder> strings, String suffix) {
		val len = strings.map[length].fold(0, [r,t|Math.max(r,t)]);
		strings.forEach[sb|
			while(sb.length()<len) {
				sb.append(' ');
			}
			if(suffix!==null) {
				sb.append(suffix);
			}
		];
	}

	static val LABELS = <Object,String>newLinkedHashMap(
		State.CREATED -> "Created",
		State.CREATED_PRIME -> "Created'",
		State.LOADED -> "Loaded",
		State.PRE_LINKED -> "Pre-linked",
		State.FULLY_INITIALIZED -> "Fully Initialized",
		State.FULLY_PROCESSED -> "Fully Processed",
		State.LOADED_FROM_DESC -> "Loaded from Description",
		State.LOADED_FROM_DESC_PRIME -> "Loaded from Description'",
		State.FULLY_INITIALIZED_RECONCILED -> "Fully Initialized ®",
		State.FULLY_PROCESSED_RECONCILED -> "Fully Processed ®",
		ParseResult.NULL -> "`null`",
		ParseResult.AVAILABLE -> "available",
		AST.NULL -> "`null`",
		AST.PROXY -> "proxy",
		AST.LAZY -> "with lazy linking proxies",
		AST.AVAILABLE -> "available",
		Module.NULL -> "`null`",
		Module.STUBS -> "with stubs",
		Module.DEFERRED -> "with DeferredTypeRefs",
		Module.AVAILABLE -> "available",
		ASTMetaCache.NULL -> "`null`",
		ASTMetaCache.AVAILABLE -> "available",
		INDETERMINATE -> "indeterminate"
	);
}
