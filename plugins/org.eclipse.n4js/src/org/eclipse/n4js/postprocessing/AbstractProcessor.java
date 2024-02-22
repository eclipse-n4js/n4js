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
package org.eclipse.n4js.postprocessing;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getBuiltInTypeScope;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getCancelIndicator;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.isASTNode;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.isTypableNode;

import java.util.function.BooleanSupplier;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.base.Throwables;
import com.google.inject.Inject;

/**
 * Provides some common base functionality used across all processors (e.g. logging). See {@link ASTProcessor} for more
 * details on processors and post-processing of {@link N4JSResource}s.
 */
abstract class AbstractProcessor {

	private static Logger LOG = Logger.getLogger(AbstractProcessor.class);

	private static boolean DEBUG_LOG = false;
	private static boolean DEBUG_LOG_RESULT = false;
	private static boolean DEBUG_RIGID = false; // if true, more consistency checks are performed and exceptions thrown
												// if wrong

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;
	@Inject
	private OperationCanceledManager operationCanceledManager;

	/**
	 * Convenience method. Same as {@link OperationCanceledManager#checkCanceled(CancelIndicator)}, using the cancel
	 * indicator of the given rule environment.
	 */
	protected void checkCanceled(RuleEnvironment G) {
		operationCanceledManager.checkCanceled(getCancelIndicator(G));
	}

	/**
	 * Processors can call this method to directly invoke the 'type' judgment, i.e. invoke method
	 * {@code TypeJudgment#apply()} via facade method
	 * {@link N4JSTypeSystem#use_type_judgment_from_PostProcessors(RuleEnvironment, TypableElement)
	 * use_type_judgment_from_PostProcessors()}. Normally, this should only be required by {@link TypeProcessor}, so use
	 * this sparingly (however, sometimes it can be helpful to avoid duplication of logic).
	 */
	protected TypeRef invokeTypeJudgmentToInferType(RuleEnvironment G, TypableElement elem) {
		if (elem.eIsProxy()) {
			return TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
		}
		// special case:
		// TStructMembers are special in that they may be types (in case of TStructMethod) and appear as AST nodes
		// -> if we are dealing with an AST node, make sure to use the definedMember in the TModule
		TStructMember definedMember = (elem instanceof TStructMember)
				? ((TStructMember) elem).getDefinedMember()
				: null;
		if (definedMember != null && isASTNode(elem)) {
			return invokeTypeJudgmentToInferType(G, definedMember);
		}
		return ts.use_type_judgment_from_PostProcessors(G, elem);
	}

	/**
	 * Some special handling for async and/or generator functions (including methods): we have to wrap their inner
	 * return type <code>R</code> into a {@code Promise<R,?>}, {@code Generator<R,R,TNext>}, or
	 * {@code AsyncGenerator<R,R,TNext>} and use that as their actual, outer return type. This means for async and/or
	 * generator functions, the types builder will create a <code>TFunction</code> with the inner return type and during
	 * post-processing this method will change that return type to a
	 * <code>Promise</code>/<code>Generator</code>/<code>AsyncGenerator</code> (only the return type of the TFunction in
	 * the types model is changed; the declared return type in the AST remains unchanged).
	 * <p>
	 * In addition, a return type of <code>void</code> will be replaced by <code>undefined</code>, i.e. will produce an
	 * outer return type of <code>Promise&lt;undefined,?></code>, <code>Generator&lt;undefined,undefined,TNext></code>,
	 * etc. This will be taken care of by utility methods
	 * {@link TypeUtils#createPromiseTypeRef(BuiltInTypeScope,TypeArgument,TypeArgument)} and
	 * {@link TypeUtils#createGeneratorTypeRef(BuiltInTypeScope,FunctionDefinition)}, respectively.
	 * <p>
	 * NOTES:
	 * <ol>
	 * <li>normally, this wrapping could easily be done in the types builder, but because we have to check if the inner
	 * return type is <code>void</code> we need to resolve proxies, which is not allowed in the types builder.
	 * </ol>
	 */
	protected void handleAsyncOrGeneratorFunctionDefinition(RuleEnvironment G, FunctionDefinition funDef) {
		boolean isAsync = funDef.isAsync();
		boolean isGenerator = funDef.isGenerator();
		if (isAsync || isGenerator) {
			Type tFunction = funDef.getDefinedType();
			if (tFunction instanceof TFunction) {
				TFunction tFun = (TFunction) tFunction;
				TypeRef innerReturnTypeRef = tFun.getReturnTypeRef();
				if (innerReturnTypeRef != null && !(innerReturnTypeRef instanceof DeferredTypeRef)) {
					// we took 'innerReturnTypeRef' from the TModule (not the AST), so normally we would not have to
					// invoke #resolveTypeAliases() here; however, since this code is running before TypeAliasProcessor,
					// we still have to invoke #resolveTypeAliases():
					TypeRef innerReturnTypeRefResolved = tsh.resolveTypeAliases(G, innerReturnTypeRef);
					TypeRef innerReturnTypeRefResolvedUB = ts.upperBoundWithReopenAndResolveTypeVars(G,
							innerReturnTypeRefResolved);
					BuiltInTypeScope scope = getBuiltInTypeScope(G);
					boolean needsRewrite = !N4JSLanguageUtils.hasExpectedSpecialReturnType(innerReturnTypeRefResolvedUB,
							funDef, scope);
					if (needsRewrite) {
						TypeRef outerReturnTypeRef = (!isGenerator)
								? TypeUtils.createPromiseTypeRef(scope, innerReturnTypeRef, null)
								:
								// note: this method handles the choice Generator vs. AsyncGenerator
								TypeUtils.createGeneratorTypeRef(scope, funDef);
						EcoreUtilN4.doWithDeliver(false, () -> tFun.setReturnTypeRef(outerReturnTypeRef), tFun);
					}
				}
			}
		}
	}

	protected static String getObjectInfo(EObject obj) {
		if (obj == null) {
			return "<null>";
		} else if (obj instanceof IdentifierRef) {
			ICompositeNode node = NodeModelUtils.findActualNodeFor(obj);
			if (node == null) {
				return "<null>";
			} else {
				return "IdentifierRef \"" + NodeModelUtils.getTokenText(node) + "\"";
			}
		} else {
			String name = getName(obj);
			if (name != null) {
				return obj.eClass().getName() + " \"" + name + "\"";
			} else {
				return obj.eClass().getName();
			}
		}
	}

	protected static String getName(EObject obj) {
		if (obj instanceof NamedElement) {
			return ((NamedElement) obj).getName();
		}
		if (obj instanceof IdentifiableElement) {
			return ((IdentifiableElement) obj).getName();
		}
		return null;
	}

	protected static void log(int indentLevel, TypeRef result) {
		if (!isDEBUG_LOG()) {
			return;
		}
		log(indentLevel, result.getTypeRefAsString());
	}

	protected static void log(int indentLevel, EObject astNode, ASTMetaInfoCache cache) {
		if (!isDEBUG_LOG())
			return;
		if (isTypableNode(astNode)) {
			TypeRef result = cache.getTypeFailSafe((TypableElement) astNode);
			String resultStr = (result != null) ? result.getTypeRefAsString() : "*** MISSING ***";
			log(indentLevel, getObjectInfo(astNode) + " " + resultStr);
		} else {
			log(indentLevel, getObjectInfo(astNode));
		}
		for (EObject childNode : astNode.eContents()) {
			log(indentLevel + 1, childNode, cache);
		}
	}

	protected static void log(int indentLevel, String msg) {
		if (!isDEBUG_LOG()) {
			return;
		}
		System.out.println(indent(indentLevel) + msg);
	}

	protected static void logErr(String msg) {
		// always log errors, even if !isDEBUG_LOG()
		System.out.flush();
		System.err.println(msg);
		System.err.flush();
		LOG.error(msg);
	}

	protected static Throwable logException(String msg, Throwable th) {
		// always log exceptions, even if !isDEBUG_LOG()
		th.printStackTrace(); // enforce dumping all exceptions to stderr
		// GH-2002: TEMPORARY DEBUG LOGGING
		// Only passing the exception to Logger#error(String,Throwable) does not emit the stack trace of the caught
		// exception in all logger configurations; we therefore include the stack trace in the main message:
		LOG.error(msg + "\n" + Throwables.getStackTraceAsString(th), th);
		return th;
	}

	protected static void assertTrueIfRigid(ASTMetaInfoCache cache, String message, BooleanSupplier check) {
		if (isDEBUG_RIGID()) {
			assertTrueIfRigid(cache, message, check.getAsBoolean());
		}
	}

	protected static void assertTrueIfRigid(ASTMetaInfoCache cache, String message, boolean actual) {
		if (isDEBUG_RIGID() && !actual) {
			Error e = new Error(message);
			if (!cache.hasBrokenAST()) {
				// make sure we see this exception on the console, even if it gets caught somewhere
				UtilN4.reportError(e);
			}
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);
		}
	}

	// using a method to read field DEBUG_LOG to get rid of Xtend's "Constant condition is always true|false." warnings
	protected static boolean isDEBUG_LOG() {
		return DEBUG_LOG;
	}

	protected static boolean isDEBUG_LOG_RESULT() {
		return DEBUG_LOG_RESULT;
	}

	protected static boolean isDEBUG_RIGID() {
		return DEBUG_RIGID;
	}

	protected static String indent(int indentLevel) {
		String res = "";
		for (int i = 0; i < indentLevel; i++) {
			res += "    ";
		}
		return res;

	}
}
