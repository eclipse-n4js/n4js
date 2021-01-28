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
package org.eclipse.n4js.xsemantics

import com.google.inject.Inject
import java.util.List
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef
import org.eclipse.n4js.typesystem.AbstractScriptAssembler
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.n4js.utils.Log
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 * Abstract base class for testing typing of function calls. The function under test and its call are created using
 * a builder FunctionCallTestConfig. The following examples shows all possible builder functions:
 * <pre>
 * declare("(S p1, T p2): T")
 * .genericWith("S", "T")
 * .invokeWith("A", "B")
 * .parameterizeWith("A", "A")
 * .assignTo("C")
 * .inferredTo("A","A","A") // last is returned type
 * .assertCallTyping()
 * </pre>
 * which will result in the following set up:
 * <pre>
 * function <S,T> func(S p1, T p2): T { return null; }
 * var A _A, B _B, C _c;
 * _C = func(_A, _B);
 * </pre>
 * and assertion that the types of the parameters and the return type are actually resolved to A.
 * If resolvesTo is not called, the type of the signature are used as expected types, "any" is assumed to be the
 * inferred return type if no return type is given.
 *
 * @see IDE-346
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
abstract class AbstractCallExpressionTypesystemTest extends AbstractTypesystemTest {

	@Inject
	public extension FunctionCallAssembler assembler;

	def FunctionCallTestConfig declare(String signature) {
		return FunctionCallTestConfig.declare(signature);
	}

	def void assertCall(FunctionCallTestConfig cfg) {
		assertCall(cfg, 0)
	}

	/*
	 * Creates a function with given formal paramter types, calls it with given argument (types) and checks whether
	 * the expected type in the call equals the expected expected type.
	 *
	 * @param typeArgParExps
	 * 	Triples of <ul><li>[0] argument type expression<li>[1]parameter type expression<li>[2] expected expected type (optional)</ul>
	 *  First triple defines the return type triple.
	 */
	def void assertCall(FunctionCallTestConfig cfg, int expectedIssueCount) {

		val G = assembler.prepareScriptAndCreateRuleEnvironment(cfg, expectedIssueCount)

		val decl = getScript().scriptElements.reverseView.tail.head as FunctionDeclaration;

		val call = (
			if (cfg.assignment) {
			((getScript().scriptElements.last as ExpressionStatement).expression as AssignmentExpression).rhs;
		} else if (cfg.returnsSometing) {
			(getScript().scriptElements.last as VariableStatement).varDecl.head.expression;
		} else {
			(getScript().scriptElements.last as ExpressionStatement).expression;
		} )
		as ParameterizedCallExpression

		val iter = cfg.expected.iterator;
		val iterDeclPars = decl.fpars.iterator;
		call.arguments.forEach[ arg, i |
			val declaredTypeRef = if (iterDeclPars.hasNext()) {
					iterDeclPars.next.declaredTypeRef
				} else
					null;
			val expectedType = if (iter.hasNext)
					iter.next()
				else if (declaredTypeRef !== null)
					declaredTypeRef.typeRefAsString
				else
					null;
			if (expectedType !== null) {
				val actualTypeResult = ts.expectedType(G, arg, arg.expression);
				assertNotNull("rule expectedTypeIn returned null for argument "+i, actualTypeResult);
				assertEquals(expectedType, actualTypeResult.typeRefAsString)
			}
		]

		if (cfg.assignment) {
			val declaredTypeRef = decl.declaredReturnTypeRef
			val expectedType = if (iter.hasNext)
					iter.next()
				else if (declaredTypeRef !== null)
					declaredTypeRef.typeRefAsString
				else
					"any";

			val inferedReturnedType = ts.type(G, call);
			assertNotNull(inferedReturnedType);
			assertFalse(inferedReturnedType instanceof UnknownTypeRef);
			assertEquals(expectedType, inferedReturnedType.typeRefAsString)

		}
	}
}

/*
 * Function call configuration, create with build pattern.
 * E.g.,
 * <pre>
 * declare("(A a, B b): C").invokeWith("A", "B").assignTo("C").resolvesTo("A","B", "C");
 * </pre>
 * For generic functions, addional builder methods exists, e.g.,
 * <pre>
 * declare("(A a, B b): C").genericWith("T").invokeWith("A", "B").parameterizedWith("A").assignTo("C").resolvesTo("A","B", "C");
 * </pre>
 *
 * The default JavaScript variant is set to n4js. If you want to write a test for unrestricted or restricted JavaScript, you will have
 * to call strict() or unrestricted(). In order to make the n4js mode more explicit, you could also call n4js();
 *
 * The expected type  usually needs to be specified only in case of parameterized calls!
 */
class FunctionCallTestConfig {

	public String signature;
	public List<String> args = emptyList;
	public String assignLhs;
	public List<String> expected = emptyList;
	public List<String> typeParameters = emptyList;
	public List<String> typeargs = emptyList;
	public JavaScriptVariant javaScriptVariant = JavaScriptVariant.n4js

	public static def declare(String signature) {
		val cfg = new FunctionCallTestConfig();
		cfg.signature = signature;
		return cfg;
	}

	public def invokeWith(String... args) {
		this.args = args;
		return this;
	}

	public def assignTo(String assignLhs) {
		this.assignLhs = assignLhs;
		return this;
	}

	public def inferredTo(String... expectedTypesOfFparsAndReturn) {
		expected = expectedTypesOfFparsAndReturn;
		return this;
	}

	public def genericWith(String... typeParameters) {
		this.typeParameters = typeParameters;
		return this;
	}

	public def parameterizedWith(String... typeargs) {
		this.typeargs = typeargs;
		return this;
	}

	public def strict() {
		javaScriptVariant = JavaScriptVariant.strict;
	}

	public def unrestricted() {
		javaScriptVariant = JavaScriptVariant.unrestricted;
	}

	public def n4js() {
		javaScriptVariant = JavaScriptVariant.n4js;
	}

	public def boolean isAssignment() {
		return ! assignLhs.nullOrEmpty;
	}

	public def boolean isGeneric() {
		return ! typeParameters.nullOrEmpty
	}

	public def boolean isParameterized() {
		return ! typeargs.nullOrEmpty
	}

	public def boolean isReturnsSometing() {
		return ! signature.endsWith("void");
	}

}

@Log
class FunctionCallAssembler extends AbstractScriptAssembler {

	def RuleEnvironment prepareScriptAndCreateRuleEnvironment(FunctionCallTestConfig cfg, int expectedIssueCount) {

		val vars = cfg.args.toSet;
		if (cfg.assignment) {
			vars.add(cfg.assignLhs)
		}

		val completeScriptSrc = getScriptPrefix() + "\n" + createVariables(vars) + "\n" +
			createFunctionDeclaration(cfg) + createFunctionCall(cfg);

		logger.debug(completeScriptSrc)

		val script = setupScript(completeScriptSrc, cfg.javaScriptVariant, expectedIssueCount);
		val G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		return G;
	}

	protected def createFunctionCall(FunctionCallTestConfig cfg) {
		val s = (if (cfg.assignment)
			createVarNameForTypeRef(cfg.assignLhs) + " = "
		else if (! cfg.signature.endsWith("void")) {
			"var inferred ="
		} else
			""
		) //
		+ (if(cfg.parameterized) "<" + cfg.typeargs.join(",") + ">" else "") //
		+ " func(" + cfg.args.map[createVarNameForTypeRef()].join(", ") + ");";
		return s;
	}

	protected def createFunctionDeclaration(FunctionCallTestConfig cfg) {
		val s = "function " + //
		(if(cfg.generic) "<" + cfg.typeParameters.join(",") + ">" else "") + //
		" func" + cfg.signature + " {" + (if(cfg.returnsSometing) " return null;" else "") + "}\n";

		return s;

	}
}
