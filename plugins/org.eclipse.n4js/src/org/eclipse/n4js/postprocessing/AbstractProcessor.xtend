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
package org.eclipse.n4js.postprocessing

import com.google.common.base.Throwables
import com.google.inject.Inject
import java.util.function.BooleanSupplier
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.NamedElement
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TStructMember
import org.eclipse.n4js.ts.types.TypableElement
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.n4js.utils.EcoreUtilN4
import org.eclipse.n4js.utils.UtilN4
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.service.OperationCanceledManager

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*
import static extension org.eclipse.n4js.utils.N4JSLanguageUtils.*

/**
 * Provides some common base functionality used across all processors (e.g. logging). See {@link ASTProcessor} for more
 * details on processors and post-processing of {@link N4JSResource}s.
 */
package abstract class AbstractProcessor {

	val private static Logger LOG = Logger.getLogger(AbstractProcessor);

	val private static DEBUG_LOG = false;
	val private static DEBUG_LOG_RESULT = false;
	val private static DEBUG_RIGID = false; // if true, more consistency checks are performed and exceptions thrown if wrong

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
	def protected void checkCanceled(RuleEnvironment G) {
		operationCanceledManager.checkCanceled(G.cancelIndicator);
	}


	/**
	 * Processors can call this method to directly invoke the 'type' judgment, i.e. invoke method {@code TypeJudgment#apply()}
	 * via facade method {@link N4JSTypeSystem#use_type_judgment_from_PostProcessors(RuleEnvironment, TypableElement)
	 * use_type_judgment_from_PostProcessors()}. Normally, this should only be required by {@link TypeProcessor}, so use
	 * this sparingly (however, sometimes it can be helpful to avoid duplication of logic).
	 */
	def protected TypeRef invokeTypeJudgmentToInferType(RuleEnvironment G, TypableElement elem) {
		if (elem.eIsProxy) {
			return TypeRefsFactory.eINSTANCE.createUnknownTypeRef;
		}
		// special case:
		// TStructMembers are special in that they may be types (in case of TStructMethod) and appear as AST nodes
		// -> if we are dealing with an AST node, make sure to use the definedMember in the TModule
		val definedMember = if (elem instanceof TStructMember) elem.definedMember;
		if (definedMember !== null && elem.isASTNode) {
			return invokeTypeJudgmentToInferType(G, definedMember);
		}
		return ts.use_type_judgment_from_PostProcessors(G, elem);
	}


	/**
	 * Some special handling for async and/or generator functions (including methods): we have to wrap their inner return type
	 * <code>R</code> into a {@code Promise<R,?>}, {@code Generator<R,R,TNext>}, or {@code AsyncGenerator<R,R,TNext>} and use
	 * that as their actual, outer return type. This means for async and/or generator functions, the types builder will create
	 * a <code>TFunction</code> with the inner return type and during post-processing this method will change that return type
	 * to a <code>Promise</code>/<code>Generator</code>/<code>AsyncGenerator</code> (only the return type of the TFunction in
	 * the types model is changed; the declared return type in the AST remains unchanged).
	 * <p>
	 * In addition, a return type of <code>void</code> will be replaced by <code>undefined</code>, i.e. will produce an outer
	 * return type of <code>Promise&lt;undefined,?></code>, <code>Generator&lt;undefined,undefined,TNext></code>, etc. This will
	 * be taken care of by utility methods {@link TypeUtils#createPromiseTypeRef(BuiltInTypeScope,TypeArgument,TypeArgument)}
	 * and {@link TypeUtils#createGeneratorTypeRef(BuiltInTypeScope,FunctionDefinition)}, respectively.
	 * <p>
	 * NOTES:
	 * <ol>
	 * <li>normally, this wrapping could easily be done in the types builder, but because we have to check if the inner
	 * return type is <code>void</code> we need to resolve proxies, which is not allowed in the types builder.
	 * </ol>
	 */
	def protected void handleAsyncOrGeneratorFunctionDefinition(RuleEnvironment G, FunctionDefinition funDef, ASTMetaInfoCache cache) {
		val isAsync = funDef.isAsync;
		val isGenerator = funDef.isGenerator;
		if(isAsync || isGenerator) {
			val tFunction = funDef.definedType;
			if(tFunction instanceof TFunction) {
				val innerReturnTypeRef = tFunction.returnTypeRef;
				if (innerReturnTypeRef !== null && !(innerReturnTypeRef instanceof DeferredTypeRef)) {
					// we took 'innerReturnTypeRef' from the TModule (not the AST), so normally we would not have to
					// invoke #resolveTypeAliases() here; however, since this code is running before TypeAliasProcessor,
					// we still have to invoke #resolveTypeAliases():
					val innerReturnTypeRefResolved = tsh.resolveTypeAliases(G, innerReturnTypeRef);
					val innerReturnTypeRefResolvedUB = ts.upperBoundWithReopenAndResolve(G, innerReturnTypeRefResolved, true);
					val scope = G.builtInTypeScope;
					val needsRewrite =
						(isAsync && !isGenerator && !TypeUtils.isPromise(innerReturnTypeRefResolvedUB, scope)) ||
						(!isAsync && isGenerator && !TypeUtils.isGenerator(innerReturnTypeRefResolvedUB, scope)) ||
						(isAsync && isGenerator && !TypeUtils.isAsyncGenerator(innerReturnTypeRefResolvedUB, scope));
					if (needsRewrite) {
						val outerReturnTypeRef = if (!isGenerator) {
							TypeUtils.createPromiseTypeRef(scope, innerReturnTypeRef, null);
						} else {
							TypeUtils.createGeneratorTypeRef(scope, funDef); // note: this method handles the choice Generator vs. AsyncGenerator
						};
						EcoreUtilN4.doWithDeliver(false, [
							tFunction.returnTypeRef = outerReturnTypeRef;
						], tFunction);
					}
				}
			}
		}
	}

	def protected static String getObjectInfo(EObject obj) {
		if (obj === null) {
			"<null>"
		} else if (obj instanceof IdentifierRef) {
			"IdentifierRef \"" + NodeModelUtils.getTokenText(NodeModelUtils.findActualNodeFor(obj)) + "\""
		} else {
			val name = obj.name;
			if (name !== null) {
				obj.eClass.name + " \"" + name + "\""
			} else {
				obj.eClass.name
			}
		}
	}

	def protected static String getName(EObject obj) {
		switch (obj) {
			NamedElement: obj.name
			IdentifiableElement: obj.name
		}
	}

	def protected static void log(int indentLevel, TypeRef result) {
		if (!isDEBUG_LOG)
			return;
		log(indentLevel, result.typeRefAsString);
	}

	def protected static void log(int indentLevel, EObject astNode, ASTMetaInfoCache cache) {
		if (!isDEBUG_LOG)
			return;
		if (astNode.isTypableNode) {
			val result = cache.getTypeFailSafe(astNode as TypableElement);
			val resultStr = if (result !== null) result.typeRefAsString else "*** MISSING ***";
			log(indentLevel, astNode.objectInfo + " " + resultStr);
		} else {
			log(indentLevel, astNode.objectInfo);
		}
		for (childNode : astNode.eContents) {
			log(indentLevel + 1, childNode, cache);
		}
	}

	def protected static void log(int indentLevel, String msg) {
		if (!isDEBUG_LOG)
			return;
		println(indent(indentLevel) + msg);
	}

	def protected static void logErr(String msg) {
		// always log errors, even if !isDEBUG_LOG()
		System.out.flush();
		System.err.println(msg);
		System.err.flush();
		LOG.error(msg);
	}

	def protected static Throwable logException(String msg, Throwable th) {
		// always log exceptions, even if !isDEBUG_LOG()
		th.printStackTrace // enforce dumping all exceptions to stderr
		// GH-2002: TEMPORARY DEBUG LOGGING
		// Only passing the exception to Logger#error(String,Throwable) does not emit the stack trace of the caught
		// exception in all logger configurations; we therefore include the stack trace in the main message:
		LOG.error(msg + "\n" + Throwables.getStackTraceAsString(th), th);
		return th;
	}

	def protected static void assertTrueIfRigid(ASTMetaInfoCache cache, String message, BooleanSupplier check) {
		if (isDEBUG_RIGID) {
			assertTrueIfRigid(cache, message, check.asBoolean);
		}
	}

	def protected static void assertTrueIfRigid(ASTMetaInfoCache cache, String message, boolean actual) {
		if (isDEBUG_RIGID && !actual) {
			val e = new Error(message);
			if(!cache.hasBrokenAST) {
				// make sure we see this exception on the console, even if it gets caught somewhere
				UtilN4.reportError(e);
			}
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);
		}
	}

	// using a method to read field DEBUG_LOG to get rid of Xtend's "Constant condition is always true|false." warnings
	def protected static boolean isDEBUG_LOG() {
		return DEBUG_LOG;
	}

	def protected static boolean isDEBUG_LOG_RESULT() {
		return DEBUG_LOG_RESULT;
	}

	def protected static boolean isDEBUG_RIGID() {
		return DEBUG_RIGID;
	}

	def protected static String indent(int indentLevel) {
		(0 ..< indentLevel).map["    "].join
	}
}
