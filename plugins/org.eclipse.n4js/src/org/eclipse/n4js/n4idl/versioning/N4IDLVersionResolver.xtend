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
package org.eclipse.n4js.n4idl.versioning

import com.google.inject.Inject
import java.math.BigDecimal
import java.util.List
import java.util.ListIterator
import org.eclipse.n4js.n4JS.VersionedElement
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.typeRefs.VersionedFunctionTypeRef
import org.eclipse.n4js.ts.typeRefs.VersionedParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.VersionedParameterizedTypeRefStructural
import org.eclipse.n4js.ts.typeRefs.VersionedReference
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TFormalParameter
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.utils.TypeUtils

/**
 * Resolver for the actual version of a type referenced by a given type reference.
 *
 * Generally, when resolving an actual version of a versioned type, this implementation will delegate to
 * {@link VersionHelper#findClassifierWithVersion(TClassifier, int)}.
 *
 * This resolver does not resolve any non-versionable/non-versioned elements and
 * thus has no effect, when applied to any non-N4IDL-specific AST elements.
 */
public class N4IDLVersionResolver {
	@Inject
	private VersionHelper versionHelper;

	/**
	 * Returns a type reference referencing the requested version of the type referenced by the given type reference.
	 *
	 * @param typeRef
	 *            the type reference to resolve
	 * @param versionedReference
	 *            the versioned reference to obtain the context version from
	 * @return a reference to the resolved version of the type
	 */
	def <T extends TypeArgument, S> T resolveVersion(T typeRef, S versionedReference) {
		if (versionedReference instanceof VersionedReference) {
			return resolveVersion(typeRef, versionedReference as VersionedReference);
		} else {
			return typeRef;
		}
	}

	private def <T extends TypeArgument> T resolveVersion(T typeRef, VersionedReference versionedReference) {
		if (versionedReference.hasRequestedVersion()) {
			return resolveVersion(typeRef, versionedReference.getRequestedVersionOrZero());
		} else {
			return typeRef;
		}
	}

	/**
	 * Returns a type reference referencing the requested version of the type referenced by the given type reference.
	 * May return the given type argument or a different instance that is assignable to <code>T</code>. Type arguments
	 * are also allowed to allow easier use of this method.
	 *
	 * @param typeRef
	 *            the type reference to resolve
	 * @param contextVersion
	 *            the context version to use when determining the actual version of the type referenced by the actual
	 *            parameter
	 * @return a reference to the resolved version of the type
	 */
	def <T extends TypeArgument> T resolveVersion(T typeArg, int contextVersion) {
		if (contextVersion === 0)
			return typeArg;

		// FIXME in the following cases, don't create unnecessary copies, i.e. don't create copy if recursive calls will return 'typeRef' unchanged!
		switch (typeArg) {
			VersionedReference case typeArg.hasRequestedVersion:	typeArg
			VersionedElement case typeArg.hasDeclaredVersion:		typeArg
			ComposedTypeRef: 										resolveVersionOfComposedTypeRef(typeArg, contextVersion) as T
			TypeTypeRef: 											resolveVersionOfTypeTypeRef(typeArg, contextVersion) as T
			FunctionTypeRef: 										resolveVersionOfFunctionTypeRef(typeArg, contextVersion) as T
			FunctionTypeExpression: 								resolveVersionOfFunctionTypeExpression(typeArg, contextVersion) as T
			ParameterizedTypeRef:									resolveVersionOfParameterizedTypeRef(typeArg, contextVersion) as T
			default: 												typeArg
		}
	}

	private def ComposedTypeRef resolveVersionOfComposedTypeRef(ComposedTypeRef typeRef, int contextVersion) {
		val composedTypeRefWithVersion = TypeUtils.copy(typeRef);
		resolveTypeArguments(composedTypeRefWithVersion.typeArgs, contextVersion);
		return composedTypeRefWithVersion;
	}

	private def TypeTypeRef resolveVersionOfTypeTypeRef(TypeTypeRef typeRef, int contextVersion) {
		val typeTypeRefWithVersion = TypeUtils.copy(typeRef);
		typeTypeRefWithVersion.typeArg = resolveVersion(typeTypeRefWithVersion.typeArg, contextVersion);
		return typeTypeRefWithVersion;
	}

	private def FunctionTypeRef resolveVersionOfFunctionTypeRef(FunctionTypeRef typeRef, int contextVersion) {
		val TFunction function = typeRef.declaredType as TFunction;
		if (function instanceof TMethod) {
			val ContainerType<?> container = function.containingType;
			if (container instanceof TClassifier) {
				val functionTypeRefWithVersion = copyParameterizedTypeRef(typeRef, contextVersion ) as VersionedFunctionTypeRef;
				functionTypeRefWithVersion.declaredType = versionHelper.findMemberWithVersion(function, contextVersion);
				return functionTypeRefWithVersion;
			}
		}
	}

	private def FunctionTypeExpression resolveVersionOfFunctionTypeExpression(FunctionTypeExpression typeRef, int contextVersion) {
		val typeRefWithVersion = TypeUtils.copy(typeRef);
		typeRefWithVersion.returnTypeRef = resolveVersion(typeRefWithVersion.returnTypeRef, contextVersion);
		typeRefWithVersion.declaredThisType = resolveVersion(typeRefWithVersion.declaredThisType, contextVersion);
		resolveFormalParameters(typeRefWithVersion.fpars, contextVersion);
		resolveTypeArguments(typeRefWithVersion.unboundTypeVarsUpperBounds, contextVersion);
		return typeRefWithVersion;
	}

	private def ParameterizedTypeRef resolveVersionOfParameterizedTypeRef(ParameterizedTypeRef typeRef, int contextVersion) {
		val propTypeRefWithVersion = copyParameterizedTypeRef(typeRef, contextVersion);

		val Type declaredType = typeRef.declaredType;
		if (declaredType instanceof TClassifier) {
			propTypeRefWithVersion.declaredType = versionHelper.findTypeWithVersion(declaredType,
				contextVersion);
		}

		resolveTypeArguments(propTypeRefWithVersion.typeArgs, contextVersion);

		return propTypeRefWithVersion;
	}

	private def VersionedParameterizedTypeRef copyParameterizedTypeRef(ParameterizedTypeRef typeRef, int requestedVersion) {
		val VersionedParameterizedTypeRef result = switch (typeRef) {
			FunctionTypeRef: 					TypeUtils.copy(typeRef, TypeRefsPackage.eINSTANCE.versionedFunctionTypeRef) as VersionedFunctionTypeRef
			ParameterizedTypeRefStructural: 	TypeUtils.copy(typeRef, TypeRefsPackage.eINSTANCE.versionedParameterizedTypeRefStructural) as VersionedParameterizedTypeRefStructural
			default: 								TypeUtils.copy(typeRef, TypeRefsPackage.eINSTANCE.versionedParameterizedTypeRef) as VersionedParameterizedTypeRef
		}

		result.requestedVersion = new BigDecimal(requestedVersion);
		return result;
	}


	private def <T extends TypeArgument> void resolveTypeArguments(List<T> typeArgs, int contextVersion) {
		val ListIterator<T> i = typeArgs.listIterator();
		while (i.hasNext()) {
			val TypeArgument current = i.next()
			if (current !== null) // FunctionTypeExpression#unboundTypeVarsUpperBounds can contain null elements
				i.set(resolveVersion(current, contextVersion) as T)
		}
	}

	private def void resolveFormalParameters(List<TFormalParameter> parameters, int contextVersion) {
		val ListIterator<TFormalParameter> i = parameters.listIterator();
		while (i.hasNext()) {
			val TFormalParameter par = i.next();
			par.typeRef = resolveVersion(par.typeRef, contextVersion);
		}
	}
}
