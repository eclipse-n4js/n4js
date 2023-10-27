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
package org.eclipse.n4js.xsemantics;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Collections.emptyList;
import static org.eclipse.n4js.utils.Strings.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.tooling.compare.ProjectCompareHelper;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.typesystem.AbstractScriptAssembler;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Abstract base class for testing typing of function calls. The function under test and its call are created using a
 * builder FunctionCallTestConfig. The following examples shows all possible builder functions:
 *
 * <pre>
 * declare("(S p1, T p2): T")
 * 		.genericWith("S", "T")
 * 		.invokeWith("A", "B")
 * 		.parameterizeWith("A", "A")
 * 		.assignTo("C")
 * 		.inferredTo("A", "A", "A") // last is returned type
 * 		.assertCallTyping()
 * </pre>
 *
 * which will result in the following set up:
 *
 * <pre>
 * function <S,T> func(S p1, T p2): T { return null; }
 * var A _A, B _B, C _c;
 * _C = func(_A, _B);
 * </pre>
 *
 * and assertion that the types of the parameters and the return type are actually resolved to A. If resolvesTo is not
 * called, the type of the signature are used as expected types, "any" is assumed to be the inferred return type if no
 * return type is given.
 *
 * see IDE-346
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
abstract public class AbstractCallExpressionTypesystemTest extends AbstractTypesystemTest {

	@Inject
	public FunctionCallAssembler assembler;

	protected FunctionCallTestConfig declare(String signature) {
		return FunctionCallTestConfig.declare(signature);
	}

	protected void assertCall(FunctionCallTestConfig cfg) {
		assertCall(cfg, 0);
	}

	/**
	 * Creates a function with given formal parameter types, calls it with given argument (types) and checks whether the
	 * expected type in the call equals the expected expected type.
	 */
	protected void assertCall(FunctionCallTestConfig cfg, int expectedIssueCount) {

		RuleEnvironment G = assembler.prepareScriptAndCreateRuleEnvironment(cfg, expectedIssueCount);

		int size = assembler.getScript().getScriptElements().size();
		FunctionDeclaration decl = (FunctionDeclaration) assembler.getScript().getScriptElements().get(size - 2);
		ScriptElement lastSE = assembler.getScript().getScriptElements().get(size - 1);

		ParameterizedCallExpression call;
		if (cfg.isAssignment()) {
			call = (ParameterizedCallExpression) ((AssignmentExpression) ((ExpressionStatement) lastSE).getExpression())
					.getRhs();
		} else if (cfg.isReturnsSometing()) {
			call = (ParameterizedCallExpression) ((VariableStatement) lastSE).getVarDecl().get(0).getExpression();
		} else {
			call = (ParameterizedCallExpression) ((ExpressionStatement) lastSE).getExpression();
		}

		Iterator<String> iter = cfg.expected.iterator();
		Iterator<FormalParameter> iterDeclPars = decl.getFpars().iterator();

		for (int i = 0; i < call.getArguments().size(); i++) {
			Argument arg = call.getArguments().get(i);
			TypeRef declaredTypeRef = (iterDeclPars.hasNext()) ? iterDeclPars.next().getDeclaredTypeRef() : null;
			String expectedType = (iter.hasNext()) ? iter.next()
					: (declaredTypeRef != null) ? declaredTypeRef.getTypeRefAsString() : null;

			if (expectedType != null) {
				TypeRef actualTypeResult = ts.expectedType(G, arg, arg.getExpression());
				assertNotNull("rule expectedTypeIn returned null for argument " + i, actualTypeResult);
				assertEquals(expectedType, actualTypeResult.getTypeRefAsString());
			}
		}

		if (cfg.isAssignment()) {
			TypeRef declaredTypeRef = decl.getDeclaredReturnTypeRefInAST();
			String expectedType = (iter.hasNext()) ? iter.next()
					: (declaredTypeRef != null) ? declaredTypeRef.getTypeRefAsString()
							: "any";

			TypeRef inferedReturnedType = ts.type(G, call);
			assertNotNull(inferedReturnedType);
			assertFalse(inferedReturnedType instanceof UnknownTypeRef);
			assertEquals(expectedType, inferedReturnedType.getTypeRefAsString());
		}
	}

	/**
	 * Function call configuration, create with build pattern. E.g.,
	 *
	 * <pre>
	 * declare("(A a, B b): C").invokeWith("A", "B").assignTo("C").resolvesTo("A", "B", "C");
	 * </pre>
	 *
	 * For generic functions, addional builder methods exists, e.g.,
	 *
	 * <pre>
	 * declare("(A a, B b): C").genericWith("T").invokeWith("A", "B").parameterizedWith("A").assignTo("C").resolvesTo("A",
	 * 		"B", "C");
	 * </pre>
	 *
	 * The default JavaScript variant is set to n4js. If you want to write a test for unrestricted or restricted
	 * JavaScript, you will have to call strict() or unrestricted(). In order to make the n4js mode more explicit, you
	 * could also call n4js();
	 *
	 * The expected type usually needs to be specified only in case of parameterized calls!
	 */
	@SuppressWarnings("hiding")
	static public class FunctionCallTestConfig {

		public String signature;
		public List<String> args = emptyList();
		public String assignLhs;
		public List<String> expected = emptyList();
		public List<String> typeParameters = emptyList();
		public List<String> typeargs = emptyList();
		public JavaScriptVariant javaScriptVariant = JavaScriptVariant.n4js;

		public static FunctionCallTestConfig declare(String signature) {
			FunctionCallTestConfig cfg = new FunctionCallTestConfig();
			cfg.signature = signature;
			return cfg;
		}

		public FunctionCallTestConfig invokeWith(String... args) {
			this.args = Arrays.asList(args);
			return this;
		}

		public FunctionCallTestConfig assignTo(String assignLhs) {
			this.assignLhs = assignLhs;
			return this;
		}

		public FunctionCallTestConfig inferredTo(String... expectedTypesOfFparsAndReturn) {
			expected = Arrays.asList(expectedTypesOfFparsAndReturn);
			return this;
		}

		public FunctionCallTestConfig genericWith(String... typeParameters) {
			this.typeParameters = Arrays.asList(typeParameters);
			return this;
		}

		public FunctionCallTestConfig parameterizedWith(String... typeargs) {
			this.typeargs = Arrays.asList(typeargs);
			return this;
		}

		public void strict() {
			javaScriptVariant = JavaScriptVariant.strict;
		}

		public void unrestricted() {
			javaScriptVariant = JavaScriptVariant.unrestricted;
		}

		public void n4js() {
			javaScriptVariant = JavaScriptVariant.n4js;
		}

		public boolean isAssignment() {
			return !isNullOrEmpty(assignLhs);
		}

		public boolean isGeneric() {
			return !typeParameters.isEmpty();
		}

		public boolean isParameterized() {
			return !typeargs.isEmpty();
		}

		public boolean isReturnsSometing() {
			return !signature.endsWith("void");
		}

	}

	static public class FunctionCallAssembler extends AbstractScriptAssembler {
		private static Logger logger = Logger.getLogger(ProjectCompareHelper.class);

		@Inject
		public FunctionCallAssembler assembler;

		public RuleEnvironment prepareScriptAndCreateRuleEnvironment(FunctionCallTestConfig cfg,
				int expectedIssueCount) {

			Set<String> vars = toSet(cfg.args);
			if (cfg.isAssignment()) {
				vars.add(cfg.assignLhs);
			}

			String completeScriptSrc = getScriptPrefix() + "\n" + assembler.createVariables(vars.toArray(new String[0]))
					+ "\n" + createFunctionDeclaration(cfg) + createFunctionCall(cfg);

			logger.debug(completeScriptSrc);

			Script script = setupScript(completeScriptSrc, cfg.javaScriptVariant, expectedIssueCount);
			RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);
			return G;
		}

		protected String createFunctionCall(FunctionCallTestConfig cfg) {
			String s = ((cfg.isAssignment()) ? createVarNameForTypeRef(cfg.assignLhs) + " = "
					: (!cfg.signature.endsWith("void")) ? "var inferred ="
							: "") //
					+ ((cfg.isParameterized()) ? "<" + join(",", cfg.typeargs) + ">" : "") //
					+ " func(" + join(", ", arg -> assembler.createVarNameForTypeRef(arg), cfg.args) + ");";
			return s;
		}

		protected String createFunctionDeclaration(FunctionCallTestConfig cfg) {
			String s = "function " + //
					((cfg.isGeneric()) ? "<" + join(",", cfg.typeParameters) + ">" : "") + //
					" func" + cfg.signature + " {" + ((cfg.isReturnsSometing()) ? " return null;" : "") + "}\n";

			return s;

		}
	}
}