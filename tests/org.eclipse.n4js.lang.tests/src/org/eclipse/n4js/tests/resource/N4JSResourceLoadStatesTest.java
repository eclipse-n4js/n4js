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
package org.eclipse.n4js.tests.resource;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.forEach;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.AbstractN4JSTest;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtend.lib.annotations.Data;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;
import org.junit.Test;

/**
 * Test the various load states an N4JSResource can be in.
 */
public class N4JSResourceLoadStatesTest extends AbstractN4JSTest {

	static URI SAMPLE_FILE = URI.createURI("src/org/eclipse/n4js/tests/resource/N4JSResourceLoadStatesTest.n4js");

	private static enum State {
		CREATED, CREATED_PRIME, LOADED, PRE_LINKED, FULLY_INITIALIZED, FULLY_PROCESSED, LOADED_FROM_DESC, LOADED_FROM_DESC_PRIME, FULLY_INITIALIZED_RECONCILED, FULLY_PROCESSED_RECONCILED
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
	public static class StateInfo {
		private final N4JSResourceLoadStatesTest.State state;

		private final N4JSResourceLoadStatesTest.ParseResult parseResult;

		private final N4JSResourceLoadStatesTest.AST ast;

		private final N4JSResourceLoadStatesTest.Module module;

		private final N4JSResourceLoadStatesTest.ASTMetaCache astMetaCache;

		private final Boolean loaded;

		private final Boolean fullyInitialized;

		private final Boolean fullyProcessed;

		private final Boolean reconciled;

		public StateInfo(final N4JSResourceLoadStatesTest.State state,
				final N4JSResourceLoadStatesTest.ParseResult parseResult, final N4JSResourceLoadStatesTest.AST ast,
				final N4JSResourceLoadStatesTest.Module module,
				final N4JSResourceLoadStatesTest.ASTMetaCache astMetaCache, final Boolean loaded,
				final Boolean fullyInitialized, final Boolean fullyProcessed, final Boolean reconciled) {
			super();
			this.state = state;
			this.parseResult = parseResult;
			this.ast = ast;
			this.module = module;
			this.astMetaCache = astMetaCache;
			this.loaded = loaded;
			this.fullyInitialized = fullyInitialized;
			this.fullyProcessed = fullyProcessed;
			this.reconciled = reconciled;
		}

		@Override

		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((this.state == null) ? 0 : this.state.hashCode());
			result = prime * result + ((this.parseResult == null) ? 0 : this.parseResult.hashCode());
			result = prime * result + ((this.ast == null) ? 0 : this.ast.hashCode());
			result = prime * result + ((this.module == null) ? 0 : this.module.hashCode());
			result = prime * result + ((this.astMetaCache == null) ? 0 : this.astMetaCache.hashCode());
			result = prime * result + ((this.loaded == null) ? 0 : this.loaded.hashCode());
			result = prime * result + ((this.fullyInitialized == null) ? 0 : this.fullyInitialized.hashCode());
			result = prime * result + ((this.fullyProcessed == null) ? 0 : this.fullyProcessed.hashCode());
			return prime * result + ((this.reconciled == null) ? 0 : this.reconciled.hashCode());
		}

		@Override

		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			N4JSResourceLoadStatesTest.StateInfo other = (N4JSResourceLoadStatesTest.StateInfo) obj;
			if (this.state == null) {
				if (other.state != null)
					return false;
			} else if (!this.state.equals(other.state))
				return false;
			if (this.parseResult == null) {
				if (other.parseResult != null)
					return false;
			} else if (!this.parseResult.equals(other.parseResult))
				return false;
			if (this.ast == null) {
				if (other.ast != null)
					return false;
			} else if (!this.ast.equals(other.ast))
				return false;
			if (this.module == null) {
				if (other.module != null)
					return false;
			} else if (!this.getModule().equals(other.module))
				return false;
			if (this.astMetaCache == null) {
				if (other.astMetaCache != null)
					return false;
			} else if (!this.astMetaCache.equals(other.astMetaCache))
				return false;
			if (this.loaded == null) {
				if (other.loaded != null)
					return false;
			} else if (!this.loaded.equals(other.loaded))
				return false;
			if (this.fullyInitialized == null) {
				if (other.fullyInitialized != null)
					return false;
			} else if (!this.fullyInitialized.equals(other.fullyInitialized))
				return false;
			if (this.fullyProcessed == null) {
				if (other.fullyProcessed != null)
					return false;
			} else if (!this.fullyProcessed.equals(other.fullyProcessed))
				return false;
			if (this.reconciled == null) {
				if (other.reconciled != null)
					return false;
			} else if (!this.reconciled.equals(other.reconciled))
				return false;
			return true;
		}

		@Override

		public String toString() {
			ToStringBuilder b = new ToStringBuilder(this);
			b.add("state", this.state);
			b.add("parseResult", this.parseResult);
			b.add("ast", this.ast);
			b.add("module", this.module);
			b.add("astMetaCache", this.astMetaCache);
			b.add("loaded", this.loaded);
			b.add("fullyInitialized", this.fullyInitialized);
			b.add("fullyProcessed", this.fullyProcessed);
			b.add("reconciled", this.reconciled);
			return b.toString();
		}

		public N4JSResourceLoadStatesTest.State getState() {
			return this.state;
		}

		public N4JSResourceLoadStatesTest.ParseResult getParseResult() {
			return this.parseResult;
		}

		public N4JSResourceLoadStatesTest.AST getAst() {
			return this.ast;
		}

		public N4JSResourceLoadStatesTest.Module getModule() {
			return this.module;
		}

		public N4JSResourceLoadStatesTest.ASTMetaCache getAstMetaCache() {
			return this.astMetaCache;
		}

		public Boolean getLoaded() {
			return this.loaded;
		}

		public Boolean getFullyInitialized() {
			return this.fullyInitialized;
		}

		public Boolean getFullyProcessed() {
			return this.fullyProcessed;
		}

		public Boolean getReconciled() {
			return this.reconciled;
		}
	}

	static Boolean INDETERMINATE = null;

	// @formatter:off
	static List<StateInfo>  loadStateInfos = List.of(
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
	);
	// @formatter:on

	// ###############################################################################################################
	// TEST CASES

	@Test
	public void testStateCreated1() throws Exception {
		N4JSResource res = createFromFile(SAMPLE_FILE);
		assertState(res, State.CREATED);
	}

	@Test
	public void testStateCreated2() throws Exception {
		N4JSResource res = createFromFile(SAMPLE_FILE);
		res.load(Collections.emptyMap());
		res.unload();
		assertState(res, State.CREATED);
	}

	@Test
	public void testStateCreated3() throws Exception {
		N4JSResource res = createFromFile(SAMPLE_FILE);
		res.load(Collections.emptyMap());
		res.unloadAST();
		assertState(res, State.CREATED);
	}

	@Test
	public void testStateCreated4() throws Exception {
		N4JSResource res = createFromFile(SAMPLE_FILE);
		res.load(Collections.emptyMap());
		res.getContents(); // trigger installation of derived state (i.e. types builder)
		// NOTE: for some reason, Xtext does not clear flag 'fullyInitialized' upon unload; that is done lazily when
		// #load() is invoked later. Hence, we have to issue #discardDerivedState() explicitly to get back to state
		// CREATED instead of CREATED_PRIME.
		res.discardDerivedState();
		res.unload();
		assertState(res, State.CREATED);
	}

	@Test
	public void testStateCreatedPrime() throws Exception {
		N4JSResource res = createFromFile(SAMPLE_FILE);
		res.load(Collections.emptyMap());
		res.getContents(); // trigger installation of derived state (i.e. types builder)
		res.unload();
		assertState(res, State.CREATED_PRIME);
	}

	@Test
	public void testStateLoaded1() throws Exception {
		N4JSResource res = createFromFile(SAMPLE_FILE);
		res.load(Collections.emptyMap());
		assertState(res, State.LOADED);
	}

	@Test
	public void testStateLoaded2() throws Exception {
		N4JSResource res = createFromFile(SAMPLE_FILE);
		res.load(Collections.emptyMap());
		res.unload();
		assertState(res, State.CREATED);
		res.load(Collections.emptyMap());
		assertState(res, State.LOADED);
	}

	@Test
	public void testPreLinked() throws Exception {
		N4JSResource res = createFromFile(SAMPLE_FILE);
		res.load(Collections.emptyMap());
		res.installDerivedState(true);
		assertState(res, State.PRE_LINKED);
	}

	@Test
	public void testStateFullyInitialized1() throws Exception {
		N4JSResource res = createFromFile(SAMPLE_FILE);
		res.load(Collections.emptyMap());
		res.getContents(); // trigger installation of derived state (i.e. types builder)
		assertState(res, State.FULLY_INITIALIZED);
	}

	@Test
	public void testStateFullyInitialized2() throws Exception {
		N4JSResource res = createFromFile(SAMPLE_FILE);
		res.load(Collections.emptyMap());
		res.getContents(); // trigger installation of derived state (i.e. types builder)
		res.discardDerivedState();
		res.unload();
		assertState(res, State.CREATED);
		res.load(Collections.emptyMap());
		assertState(res, State.LOADED);
		res.getContents(); // trigger installation of derived state (i.e. types builder)
		assertState(res, State.FULLY_INITIALIZED);
	}

	/** Make sure that we can reach state 'fullyInitialized' after having been in state 'pre-linked'. */
	@Test
	public void testStateFullyInitialized3() throws Exception {
		N4JSResource res = createFromFile(SAMPLE_FILE);
		res.load(Collections.emptyMap());
		res.installDerivedState(true);
		assertState(res, State.PRE_LINKED);
		res.discardDerivedState();
		res.installDerivedState(false);
		assertState(res, State.FULLY_INITIALIZED);
	}

	@Test
	public void testStateFullyProcessed1() throws Exception {
		N4JSResource res = createFromFile(SAMPLE_FILE);
		res.load(Collections.emptyMap());
		res.getContents(); // trigger installation of derived state (i.e. types builder)
		res.performPostProcessing();
		assertState(res, State.FULLY_PROCESSED);
	}

	@Test
	public void testStateFullyProcessed2() throws Exception {
		N4JSResource res = createFromFile(SAMPLE_FILE);
		res.load(Collections.emptyMap());
		// do *not* trigger installation of derived state explicitly, in this test case
		res.performPostProcessing();
		assertState(res, State.FULLY_PROCESSED);
	}

	@Test
	public void testStateFullyProcessed_triggeredOnlyThroughProxyResolution() throws Exception {
		N4JSResource res = createFromFile(SAMPLE_FILE);
		res.load(Collections.emptyMap());
		// do *not* trigger installation of derived state explicitly, in this test case
		// do *not* trigger post-processing explicitly, in this test case
		IdentifierRef idRef = head(filter(res.getScript().eAllContents(), IdentifierRef.class));
		assertNotNull(idRef);
		idRef.getId(); // trigger installation of derived state + post-processing by resolving a proxy *without* type
						// inference
		assertState(res, State.FULLY_PROCESSED);
	}

	@Test
	public void testStateLoadedFromDescription1() throws Exception {
		N4JSResource res = loadFromDescription(SAMPLE_FILE);
		assertState(res, State.LOADED_FROM_DESC);
	}

	@Test
	public void testStateLoadedFromDescription2() throws Exception {
		N4JSResource res = createFromFile(SAMPLE_FILE);
		res.load(Collections.emptyMap());
		res.getContents(); // trigger installation of derived state (i.e. types builder)
		// do *not* perform post-processing
		res.unloadAST();
		assertState(res, State.LOADED_FROM_DESC_PRIME); // note: when not doing post-processing before, DeferredTypeRefs
														// leak into state LOADED_FROM_DESC
	}

	@Test
	public void testStateLoadedFromDescription3() throws Exception {
		N4JSResource res = createFromFile(SAMPLE_FILE);
		res.load(Collections.emptyMap());
		res.performPostProcessing();
		res.unloadAST();
		assertState(res, State.LOADED_FROM_DESC);
	}

	@Test
	public void testStateLoadedFromDescription4() throws Exception {
		N4JSResource res = loadFromDescription(SAMPLE_FILE);
		res.unloadAST();
		res.unloadAST();
		assertState(res, State.LOADED_FROM_DESC);
	}

	@Test
	public void testStateReconciled1() throws Exception {
		N4JSResource res = loadFromDescription(SAMPLE_FILE);
		res.getContents().get(0); // trigger demand-loading of AST (with reconciliation)
		assertState(res, State.FULLY_INITIALIZED_RECONCILED);
		res.performPostProcessing(); // trigger post-processing
		assertState(res, State.FULLY_PROCESSED_RECONCILED);
	}

	/** Same as previous method, but use different ways of triggering demand-loading of AST and post-processing. */
	@Test
	public void testStateReconciled2() throws Exception {
		N4JSResource res = loadFromDescription(SAMPLE_FILE);
		TClass tClass = head(filter(res.getModule().eAllContents(), TClass.class));
		assertNotNull(tClass);
		tClass.getAstElement(); // trigger demand-loading of AST (with reconciliation)
		assertState(res, State.FULLY_INITIALIZED_RECONCILED);
		ParameterizedPropertyAccessExpression propAccess = head(
				filter(res.getScript().eAllContents(), ParameterizedPropertyAccessExpression.class));
		assertNotNull(propAccess);
		propAccess.getProperty(); // trigger post-processing
		assertState(res, State.FULLY_PROCESSED_RECONCILED);
	}

	// ###############################################################################################################
	// ASSERTION OF STATES

	private void assertState(N4JSResource res, State state) {
		StateInfo info = IterableExtensions.findFirst(loadStateInfos, si -> Objects.equals(si.state, state));
		assertNotNull(info);
		switch (info.getParseResult()) {
		case NULL: {
			assertNull("in state " + state + " parse result should be null", res.basicGetParseResult());
			break;
		}
		case AVAILABLE: {
			assertNotNull("in state " + state + " parse result should be available", res.basicGetParseResult());
			break;
		}
		default: {
			throw new IllegalStateException("unknown literal: " + info.getParseResult());
		}
		}
		switch (info.ast) {
		case NULL: {
			assertNull("in state " + state + " the script should be null", res.getScript());
			break;
		}
		case PROXY: {
			assertTrue("in state " + state + " the script should be a proxy", res.getScript().eIsProxy());
			break;
		}
		case LAZY: {
			assertFalse("in state " + state + " the script should *not* be a proxy", res.getScript().eIsProxy());
			assertTrue("in state " + state + " the script should contain lazy linking proxies",
					hasLazyLinkingProxy(res.getScript()));
			break;
		}
		case AVAILABLE: {
			assertFalse("in state " + state + " the script should *not* be a proxy", res.getScript().eIsProxy());
			assertFalse("in state " + state + " the script should *not* contain lazy linking proxies",
					hasLazyLinkingProxy(res.getScript()));
			break;
		}
		default: {
			throw new IllegalStateException("unknown literal: " + info.ast);
		}
		}
		switch (info.getModule()) {
		case NULL: {
			assertNull("in state " + state + " the module should be null", res.getModule());
			break;
		}
		case STUBS: {
			assertNotNull("in state " + state + " the module should *not* be null", res.getModule());
			assertFalse("in state " + state + " the module should *not* be a proxy", res.getModule().eIsProxy());
			assertTrue("in state " + state + " the module should contain stubs", containsStub(res.getModule()));
			break;
		}
		case DEFERRED: {
			assertNotNull("in state " + state + " the module should *not* be null", res.getModule());
			assertFalse("in state " + state + " the module should *not* be a proxy", res.getModule().eIsProxy());
			assertFalse("in state " + state + " the module should *not* contain stubs", containsStub(res.getModule()));
			assertTrue("in state " + state + " the module should contain DeferredTypeRefs",
					containsDeferredTypeRef(res.getModule()));
			break;
		}
		case AVAILABLE: {
			assertNotNull("in state " + state + " the module should *not* be null", res.getModule());
			assertFalse("in state " + state + " the module should *not* be a proxy", res.getModule().eIsProxy());
			assertFalse("in state " + state + " the module should *not* contain stubs", containsStub(res.getModule()));
			assertFalse("in state " + state + " the module should *not* contain DeferredTypeRefs",
					containsDeferredTypeRef(res.getModule()));
			break;
		}
		default: {
			throw new IllegalStateException("unknown literal: " + info.getModule());
		}
		}
		switch (info.astMetaCache) {
		case NULL: {
			assertNull("in state " + state + " the ASTMetaInfoCache should be null", res.getASTMetaInfoCache());
			break;
		}
		case AVAILABLE: {
			assertNotNull("in state " + state + " the ASTMetaInfoCache should *not* be null",
					res.getASTMetaInfoCacheVerifyContext());
			break;
		}
		}

		if (info.loaded != INDETERMINATE) {
			assertEquals("in state " + state + " flag 'loaded' should be " + info.loaded,
					info.loaded, res.isLoaded());
		}
		if (info.fullyInitialized != INDETERMINATE) {
			assertEquals("in state " + state + " flag 'fullyInitialized' should be " + info.fullyInitialized,
					info.fullyInitialized, res.isFullyInitialized());
		}
		if (info.fullyProcessed != INDETERMINATE) {
			assertEquals("in state " + state + " flag 'fullyProcessed' should be " + info.fullyProcessed,
					info.fullyProcessed, res.isFullyProcessed());
		}
		if (info.reconciled != INDETERMINATE) {
			assertEquals("in state " + state + " flag 'reconciled' should be " + info.reconciled,
					info.reconciled, res.isReconciled());
		}
	}

	private boolean containsDeferredTypeRef(EObject root) {
		return filter(root.eAllContents(), DeferredTypeRef.class).hasNext();
	}

	private boolean hasLazyLinkingProxy(final EObject root) {
		// val res = root.eResource as N4JSResource;
		// return root.eAllContents.exists[eobj|
		// eobj.eClass.EReferences.filter[!containment && !transient && !volatile].exists[eref|
		// hasLazyLinkingProxy(res, eobj, eref);
		// ]
		// ];
		Resource _eResource = root.eResource();
		final N4JSResource res = ((N4JSResource) _eResource);
		final Function1<EObject, Boolean> _function = (EObject eobj) -> {
			final Function1<EReference, Boolean> _function_1 = (EReference it) -> {
				return Boolean.valueOf((((!it.isContainment()) && (!it.isTransient())) && (!it.isVolatile())));
			};
			final Function1<EReference, Boolean> _function_2 = (EReference eref) -> {
				return Boolean.valueOf(this.hasLazyLinkingProxy(res, eobj, eref));
			};
			return Boolean.valueOf(IterableExtensions.<EReference> exists(
					IterableExtensions.<EReference> filter(eobj.eClass().getEReferences(), _function_1), _function_2));
		};
		return IteratorExtensions.<EObject> exists(root.eAllContents(), _function);
	}

	private boolean hasLazyLinkingProxy(N4JSResource res, EObject eobj, EReference eref) {
		if (eref.isMany()) {
			Collection<?> value = (Collection<?>) eobj.eGet(eref, false);
			return exists(IterableExtensions.filter(value, EObject.class), it -> isLazyLinkingProxy(res, it));
		} else {
			EObject value = (EObject) eobj.eGet(eref, false);
			return value != null && isLazyLinkingProxy(res, value);
		}
	}

	private boolean isLazyLinkingProxy(N4JSResource res, EObject proxy) {
		return proxy.eIsProxy()
				&& res.getEncoder().isCrossLinkFragment(res, ((InternalEObject) proxy).eProxyURI().fragment());
	}

	private boolean containsStub(TModule root) {
		return IteratorExtensions.exists(filter(root.eAllContents(), TMethod.class), m -> isStub(m));
	}

	/**
	 * Tells if the given {@link TMethod} is a stub as created by the types build during pre-linking phase, i.e. when
	 * the types builder is invoked with flag <code>preLinkingPhase</code> set to true. If this method returns
	 * <code>false</code>, the given {@code TMethod} is guaranteed to have been created by the types builder in the
	 * ordinary, main phase (i.e. <code>preLinkingPhase</code> set to false).
	 * <p>
	 * As can be seen in {@code AbstractFunctionDefinitionTypesBuilder#setReturnType(TFunction, FunctionDefinition,
	 * BuiltInTypeScope, boolean)}, return types of methods are created if and only if the types builder is running in
	 * the main phase, not in pre-linking phase.
	 */
	private boolean isStub(TMethod method) {
		return method.getReturnTypeRef() == null;
	}

	// ###############################################################################################################
	// GENERATE ASCIIDOC TABLE

	public static void main(String[] args) {
		List<StringBuilder> rows = new ArrayList<>();
		rows.add(new StringBuilder());
		for (@SuppressWarnings("unused")
		StateInfo is : loadStateInfos) {
			rows.add(new StringBuilder());
		}

		makeEqualLengthAndAppend(rows, "| ");
		forEach(rows, (row, i) -> row.append((i == 0 ? "State" : getLabel(loadStateInfos.get(i - 1).state))));
		makeEqualLengthAndAppend(rows, "| ");
		forEach(rows, (row, i) -> row
				.append((i == 0 ? "Parse Result" : getLabel(loadStateInfos.get(i - 1).getParseResult()))));
		makeEqualLengthAndAppend(rows, "| ");
		forEach(rows, (row, i) -> row.append((i == 0 ? "AST" : getLabel(loadStateInfos.get(i - 1).ast))));
		makeEqualLengthAndAppend(rows, "| ");
		forEach(rows, (row, i) -> row.append((i == 0 ? "TModule" : getLabel(loadStateInfos.get(i - 1).module))));
		makeEqualLengthAndAppend(rows, "| ");
		forEach(rows, (row, i) -> row
				.append((i == 0 ? "ASTMetaInfoCache" : getLabel(loadStateInfos.get(i - 1).astMetaCache))));
		makeEqualLengthAndAppend(rows, "| ");
		forEach(rows, (row, i) -> row.append((i == 0 ? "loaded" : getLabel(loadStateInfos.get(i - 1).loaded))));
		makeEqualLengthAndAppend(rows, "| ");
		forEach(rows, (row, i) -> row
				.append((i == 0 ? "fullyInitialized" : getLabel(loadStateInfos.get(i - 1).fullyInitialized))));
		makeEqualLengthAndAppend(rows, "| ");
		forEach(rows, (row, i) -> row
				.append((i == 0 ? "fullyProcessed" : getLabel(loadStateInfos.get(i - 1).fullyProcessed))));
		makeEqualLengthAndAppend(rows, "| ");
		forEach(rows, (row, i) -> row.append((i == 0 ? "reconciled" : getLabel(loadStateInfos.get(i - 1).reconciled))));

		System.out.println("[cols=\"1h,1,1,1,1,1,1,1,1\",options=\"header\"]");
		System.out.println("|==");
		System.out.println(Strings.join("\n", rows));
		System.out.println("|==");
	}

	public static String getLabel(Object obj) {
		if (LABELS.get(obj) != null) {
			return LABELS.get(obj);
		}
		if (obj != null) {
			return obj.toString();
		}
		return null;
	}

	public static void makeEqualLengthAndAppend(List<StringBuilder> strings, String suffix) {
		// val len = strings.map[length].fold(0, [r,t|Math.max(r,t)]);
		final Function1<StringBuilder, Integer> _function = (StringBuilder it) -> {
			return Integer.valueOf(it.length());
		};
		final Function2<Integer, Integer, Integer> _function_1 = (Integer r, Integer t) -> {
			return Integer.valueOf(Math.max((r).intValue(), (t).intValue()));
		};
		final Integer len = IterableExtensions.<Integer, Integer> fold(
				ListExtensions.<StringBuilder, Integer> map(strings, _function), Integer.valueOf(0), _function_1);

		for (StringBuilder sb : strings) {
			while (sb.length() < len) {
				sb.append(" ");
			}
			if (suffix != null) {
				sb.append(suffix);
			}
		}
	}

	static Map<Object, String> LABELS = new HashMap<>() {
		{
			put(State.CREATED, "Created");
			put(State.CREATED_PRIME, "Created'");
			put(State.LOADED, "Loaded");
			put(State.PRE_LINKED, "Pre-linked");
			put(State.FULLY_INITIALIZED, "Fully Initialized");
			put(State.FULLY_PROCESSED, "Fully Processed");
			put(State.LOADED_FROM_DESC, "Loaded from Description");
			put(State.LOADED_FROM_DESC_PRIME, "Loaded from Description'");
			put(State.FULLY_INITIALIZED_RECONCILED, "Fully Initialized ®");
			put(State.FULLY_PROCESSED_RECONCILED, "Fully Processed ®");
			put(ParseResult.NULL, "`null`");
			put(ParseResult.AVAILABLE, "available");
			put(AST.NULL, "`null`");
			put(AST.PROXY, "proxy");
			put(AST.LAZY, "with lazy linking proxies");
			put(AST.AVAILABLE, "available");
			put(Module.NULL, "`null`");
			put(Module.STUBS, "with stubs");
			put(Module.DEFERRED, "with DeferredTypeRefs");
			put(Module.AVAILABLE, "available");
			put(ASTMetaCache.NULL, "`null`");
			put(ASTMetaCache.AVAILABLE, "available");
			put(INDETERMINATE, "indeterminate");
		}
	};

}
