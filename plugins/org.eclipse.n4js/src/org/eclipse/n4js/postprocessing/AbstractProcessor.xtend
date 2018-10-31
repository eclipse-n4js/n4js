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
import org.eclipse.n4js.typesystem.InternalTypeSystemNEW
import org.eclipse.n4js.typesystem.utils.Result
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.utils.EcoreUtilN4
import org.eclipse.n4js.utils.UtilN4
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.service.OperationCanceledManager

import static extension org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.*
import static extension org.eclipse.n4js.utils.N4JSLanguageUtils.*

/**
 * Provides some common base functionality used across all processors (e.g. logging). See {@link ASTProcessor} for more
 * details on processors and post-processing of {@link N4JSResource}s.
 */
package abstract class AbstractProcessor {

	val private static DEBUG_LOG = false;
	val private static DEBUG_LOG_RESULT = false;
	val private static DEBUG_RIGID = false; // if true, more consistency checks are performed and exceptions thrown if wrong

	@Inject
	private InternalTypeSystemNEW ts_internal;
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
	 * Processors can call this method to directly invoke the 'type' judgment of the internal, Xsemantics-generated type
	 * system. Normally, this should only be required by {@link TypeProcessor}, so use this sparingly (however,
	 * sometimes it can be helpful to avoid duplication of logic).
	 */
	def protected Result<TypeRef> askXsemanticsForType(RuleEnvironment G, TypableElement elem) {
		if (elem.eIsProxy) {
			return Result.success(TypeRefsFactory.eINSTANCE.createUnknownTypeRef);
		}
		// special case:
		// TStructMembers are special in that they may be types (in case of TStructMethod) and appear as AST nodes
		// -> if we are dealing with an AST node, make sure to use the definedMember in the TModule
		val definedMember = if (elem instanceof TStructMember) elem.definedMember;
		if (definedMember !== null && elem.isASTNode) {
			return askXsemanticsForType(G, definedMember);
		}
		return ts_internal.use_type_judgment_from_PostProcessors(G, elem);
	}


	/**
	 * Some special handling for async functions (including methods): we have to wrap their inner return type
	 * <code>R</code> into a <code>Promise&lt;R,?></code> and use that as their actual, outer return type. This means
	 * for async functions, the types builder will create a <code>TFunction</code> with the inner return type and during
	 * post-processing this method will change that return type to a <code>Promise</code> (only the return type of the
	 * TFunction in the types model is changed; the declared return type in the AST remains unchanged).
	 * <p>
	 * In addition, a return type of <code>void</code> will be replaced by <code>undefined</code>, i.e. will produce an
	 * outer return type of <code>Promise&lt;undefined,?></code>. This will be taken care of by method
	 * {@link TypeUtils#createPromiseTypeRef(BuiltInTypeScope,TypeArgument,TypeArgument)}.
	 * <p>
	 * NOTES:
	 * <ol>
	 * <li>normally, this wrapping could easily be done in the types builder, but because we have to check if the inner
	 * return type is <code>void</code> we need to resolve proxies, which is not allowed in the types builder.
	 * </ol>
	 */
	def protected void handleAsyncFunctionDefinition(RuleEnvironment G, FunctionDefinition funDef, ASTMetaInfoCache cache) {
		if(funDef.isAsync) {
			val tFunction = funDef.definedType;
			if(tFunction instanceof TFunction) {
				val innerReturnTypeRef = tFunction.returnTypeRef;
				if(innerReturnTypeRef!==null && !(innerReturnTypeRef instanceof DeferredTypeRef)) {
					val scope = G.builtInTypeScope;
				    if (!TypeUtils.isPromise(innerReturnTypeRef, scope)) {
						val outerReturnTypeRef = TypeUtils.createPromiseTypeRef(scope, innerReturnTypeRef, null);
						EcoreUtilN4.doWithDeliver(false, [
							tFunction.returnTypeRef = outerReturnTypeRef;
						], tFunction);
					}
				}
			}
		}
	}


	/**
	 * Some special handling for generator functions (including methods): we have to wrap their inner return type
	 * <code>R</code> into a {@code Generator<R,TReturn,TNext>} and use that as their actual, outer return type. This means
	 * for generator functions, the types builder will create a <code>TFunction</code> with the inner return type and during
	 * post-processing this method will change that return type to a <code>Generator</code> (only the return type of the
	 * TFunction in the types model is changed; the declared return type in the AST remains unchanged).
	 * <p>
	 * In addition, a return type of <code>void</code> will be replaced by <code>undefined</code>, i.e. will produce an
	 * outer return type of {@code Generator<undefined,undefined,TNext>}. This will be taken care of by method
	 * {@link TypeUtils#createGeneratorTypeRef(BuiltInTypeScope,FunctionDefinition)}.
	 * <p>
	 * NOTES:
	 * <ol>
	 * <li>normally, this wrapping could easily be done in the types builder, but because we have to check if the inner
	 * return type is <code>void</code> we need to resolve proxies, which is not allowed in the types builder.
	 * </ol>
	 */
	def protected void handleGeneratorFunctionDefinition(RuleEnvironment G, FunctionDefinition funDef, ASTMetaInfoCache cache) {
		if(funDef.isGenerator) {
			val tFunction = funDef.definedType;
			if(tFunction instanceof TFunction) {
				val innerReturnTypeRef = tFunction.returnTypeRef;
				if (innerReturnTypeRef !== null && !(innerReturnTypeRef instanceof DeferredTypeRef)) {
					val scope = G.builtInTypeScope;
					if (!TypeUtils.isGenerator(innerReturnTypeRef, scope)) {
					    val outerReturnTypeRef = TypeUtils.createGeneratorTypeRef(scope, funDef);
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

	def protected static void log(int indentLevel, Result<TypeRef> result) {
		if (!isDEBUG_LOG)
			return;
		log(indentLevel, result.resultToString);
	}

	def protected static void log(int indentLevel, EObject astNode, ASTMetaInfoCache cache) {
		if (!isDEBUG_LOG)
			return;
		if (astNode.isTypableNode) {
			val result = cache.getTypeFailSafe(astNode as TypableElement);
			val resultStr = if (result !== null) result.resultToString else "*** MISSING ***";
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
	}

	def protected static Throwable logException(Throwable th) {
		// always log exceptions, even if !isDEBUG_LOG()
		th.printStackTrace // enforce dumping all exceptions to stderr
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
			Throwables.propagate(e);
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

	def protected static String resultToString(Result<TypeRef> result) {
		"RESULT: " + if (result !== null && result.failure) {
			"!FAILED! " + result.failureMessage;
		} else {
			result?.value?.typeRefAsString
		}
	}

	def protected static String indent(int indentLevel) {
		(0 ..< indentLevel).map["    "].join
	}
}
