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
package org.eclipse.n4js.ts.types.util;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.utils.RecursionGuard;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.IResourceScopeCache;

import com.google.common.collect.Iterables;

/**
 * Abstract helper that allows to traverse a hierarchy of Types that may contain erroneous cycles. Processes declared
 * super types. Note that by default implicit super types are <b>not</b> processed for two reasons:
 * <ol>
 * <li>different languages may use different implicit super types, these implicit super types are usually used for
 * reflective features
 * <li>the N4JS built-in types are not available in the types bundle (due to reason 1)
 * </ol>
 * But subclasses may override method {@link #getImplicitSuperTypes(Type)} to obtain support for implicit super types.
 * <p>
 * Types are processed in the following order (slightly reflecting the order in which members are added to a class):
 * <ol>
 * <li>polyfills (but without visiting super types of polyfills themselves!)
 * <li>the type itself
 * <li>extended super class
 * <li>recursively: roles consumed by super class and indirectly extended super class
 * <li>recursively: interfaces implemented by all classes in hierarchy
 * </ol>
 */
public abstract class AbstractTypeHierachyTraverser<Result> {

	/** The type which the traverser has to traverse, that is the bottom type of the hierarchy. */
	protected final Type bottomType;

	/** The recursion guard */
	protected final RecursionGuard<ParameterizedTypeRef> guard = new RecursionGuard<>();

	/** Top element of stack represents the {@link ParameterizedTypeRef} that points to the current type. */
	protected final Stack<ParameterizedTypeRef> currentTypeRefs = new Stack<>();

	/**
	 * If <code>true</code>, the current type will be processed but its polyfills/merged types and super types won't be
	 * traversed. More precisely, the case methods will invoke the abstract process methods for the current type, but
	 * won't recursively invoke {@link #doSwitch(ParameterizedTypeRef)} for its polyfills/merged types and super types.
	 */
	protected boolean suppressPolyfillOrMergedTypes;

	/** If <code>true</code>, the current type is a direct polyfill or merged type. */
	protected boolean isDirectPolyfillOrMergedType;

	/** Creates a new traverser that is used to safely process a potentially cyclic inheritance tree. */
	public AbstractTypeHierachyTraverser(ContainerType<?> type) {
		this((Type) type);
	}

	/** Creates a new traverser that is used to safely process a potentially cyclic inheritance tree. */
	public AbstractTypeHierachyTraverser(PrimitiveType type) {
		this((Type) type);
	}

	private AbstractTypeHierachyTraverser(Type type) {
		this(type, TypeUtils.createTypeRef(type));
	}

	/** Creates a new traverser that is used to safely process a potentially cyclic inheritance tree. */
	public AbstractTypeHierachyTraverser(ParameterizedTypeRef typeRef) {
		this(typeRef.getDeclaredType(), typeRef);
	}

	private AbstractTypeHierachyTraverser(Type type, ParameterizedTypeRef typeRef) {
		this.bottomType = type;
		this.currentTypeRefs.push(typeRef);
	}

	/** Return the computed result. */
	final public Result getResult() {
		try (Measurement m = getMeasurement()) {
			Result result = null;
			Resource resource = bottomType.eResource();
			Object key = getCacheKey();

			if (key != null && resource instanceof XtextResource) {
				IResourceScopeCache cache = ((XtextResource) resource).getCache();
				if (cache != null) {
					result = cache.get(key, resource, this::internalGetResult);
				}
			}
			if (result == null) {
				result = internalGetResult();
			}

			return result;
		}
	}

	/** Implement to enable performance measurements. */
	abstract protected Measurement getMeasurement();

	/** Override to enable caching. */
	protected Object getCacheKey() {
		return null;
	}

	/** Computes the result. */
	protected Result internalGetResult() {
		doSwitchTypeRef(getCurrentTypeRef());
		return doGetResult();
	}

	/** Internal getter for the result. */
	protected abstract Result doGetResult();

	/**
	 * Process the given container type. The traversal itself is handled by this class.
	 *
	 * @param type
	 *            the processed type.
	 * @return {@code true} if the processing is finished and no further types should be considered.
	 */
	protected abstract boolean process(ContainerType<?> type);

	/**
	 * Process the given primitive type. The traversal itself is handled by this class.
	 *
	 * @param type
	 *            the processed type.
	 * @return {@code true} if the processing is finished and no further types should be considered.
	 */
	protected abstract boolean process(PrimitiveType type);

	/**
	 * Processes primitive types.
	 *
	 * @param typeRef
	 *            for the given primitive type
	 */
	protected boolean visitPrimitiveType(ParameterizedTypeRef typeRef, PrimitiveType primType) {
		if (process(primType)) {
			return true;
		}
		PrimitiveType assignmentCompatible = primType.getAssignmentCompatible();
		if (assignmentCompatible != null) {
			ParameterizedTypeRef typeRefAC = TypeRefsFactory.eINSTANCE.createParameterizedTypeRef();
			typeRefAC.setDeclaredType(assignmentCompatible);
			Boolean result = doSwitchTypeRef(typeRefAC);
			if (result) {
				return result;
			}
		}
		return false;
	}

	/**
	 * Processes classes: polyfills, the class itself, its super types, its merged types, its super interfaces.
	 *
	 * @param typeRef
	 *            for the given class
	 */
	protected boolean visitTClass(ParameterizedTypeRef typeRef, TClass clazz) {
		if (!clazz.isPolyfill()) {
			Iterable<ParameterizedTypeRef> polyfills = getPolyfillTypeRefs(clazz);
			try {
				isDirectPolyfillOrMergedType = true;
				if (doSwitchTypeRefs(polyfills)) {
					return true;
				}
			} finally {
				isDirectPolyfillOrMergedType = false;
			}
		}
		if (process(clazz)) {
			return true;
		}
		if (doSwitchSuperTypes(clazz)) {
			return true;
		}
		if (!suppressPolyfillOrMergedTypes) {
			Iterable<ParameterizedTypeRef> mergedTypes = getMergedTypeRefs(clazz);
			try {
				suppressPolyfillOrMergedTypes = true;
				isDirectPolyfillOrMergedType = true;
				if (doSwitchTypeRefs(mergedTypes)) {
					return true;
				}
			} finally {
				suppressPolyfillOrMergedTypes = false;
				isDirectPolyfillOrMergedType = false;
			}
		}
		if (doSwitchImplementedInterfaces(clazz)) {
			return true;
		}
		return false;
	}

	/**
	 * Processes interfaces: polyfills and merged types, the interface itself, its super interfaces.
	 *
	 * @param typeRef
	 *            for the given interface
	 */
	protected boolean visitTInterface(ParameterizedTypeRef typeRef, TInterface interf) {
		if (!suppressPolyfillOrMergedTypes && !interf.isPolyfill()) {
			Iterable<ParameterizedTypeRef> polyfillsOrMerged = getPolyfillsOrMergedTypeRefs(interf);
			try {
				suppressPolyfillOrMergedTypes = true;
				isDirectPolyfillOrMergedType = true;
				// handle classes first
				for (ParameterizedTypeRef ptr : polyfillsOrMerged) {
					if (ptr.getDeclaredType() instanceof TClass) {
						if (doSwitch(ptr)) {
							return true;
						}
					}
				}
				for (ParameterizedTypeRef ptr : polyfillsOrMerged) {
					if (!(ptr.getDeclaredType() instanceof TClass)) {
						if (doSwitch(ptr)) {
							return true;
						}
					}
				}
			} finally {
				suppressPolyfillOrMergedTypes = false;
				isDirectPolyfillOrMergedType = false;
			}
		}
		if (process(interf)) {
			return true;
		}
		if (doSwitchSuperInterfaces(interf)) {
			return true;
		}
		return false;
	}

	/**
	 * Processes structural types.
	 *
	 * @param typeRef
	 *            for the given structural type
	 */
	protected boolean visitTStructuralType(ParameterizedTypeRef typeRef, TStructuralType structType) {
		return process(structType);
	}

	/** Returns true iff the current type ref is the bottom type or a polyfill / merged type of the bottom type. */
	protected boolean isCurrentBottomOrPolyfillOrMergedType() {
		if (getCurrentTypeRef() == null) {
			return false;
		}
		Type currType = getCurrentTypeRef().getDeclaredType();
		if (bottomType == currType) {
			return true;
		}
		if (!isDirectPolyfillOrMergedType) {
			return false;
		}
		if (currentTypeRefs.size() < 2) {
			return false;
		}
		ParameterizedTypeRef beforeElem = currentTypeRefs.get(currentTypeRefs.size() - 2);
		return bottomType == beforeElem.getDeclaredType();
	}

	/**
	 * Process the super type of a class.
	 */
	protected boolean doSwitchSuperTypes(TClass object) {
		boolean tmp = isDirectPolyfillOrMergedType;
		try {
			isDirectPolyfillOrMergedType = false;
			return doSwitchTypeRefs(getSuperTypes(object));
		} finally {
			isDirectPolyfillOrMergedType = tmp;
		}
	}

	/**
	 * Process the consumed roles of a class.
	 */
	protected boolean doSwitchImplementedInterfaces(TClass object) {
		boolean tmp = isDirectPolyfillOrMergedType;
		try {
			isDirectPolyfillOrMergedType = false;
			return doSwitchTypeRefs(object.getImplementedInterfaceRefs());
		} finally {
			isDirectPolyfillOrMergedType = tmp;
		}
	}

	/**
	 * Process the super interfaces of an interface.
	 */
	protected boolean doSwitchSuperInterfaces(TInterface object) {
		boolean tmp = isDirectPolyfillOrMergedType;
		try {
			isDirectPolyfillOrMergedType = false;
			return doSwitchTypeRefs(getSuperTypes(object));
		} finally {
			isDirectPolyfillOrMergedType = tmp;
		}
	}

	/**
	 * Traverses all types of the list, usually either roles or polyfills.
	 *
	 * @return {@code true} if the processing is finished and no further types should be considered.
	 */
	protected boolean doSwitchTypeRefs(Iterable<ParameterizedTypeRef> typeRefs) {
		if (typeRefs == null) {
			return false;
		}
		for (ParameterizedTypeRef typeRef : typeRefs) {
			if (doSwitchTypeRef(typeRef)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Traverses the declared type, used for super type references.
	 *
	 * @return {@code true} if the processing is finished and no further types should be considered.
	 */
	protected boolean doSwitchTypeRef(ParameterizedTypeRef typeRef) {
		if (typeRef == null) {
			return false;
		}
		Type type = typeRef.getDeclaredType();
		if (type == null || type.eIsProxy()) {
			return false;
		}
		return guardedSwitch(typeRef);
	}

	/** Wrapper method of {@link #doSwitch(ParameterizedTypeRef)} mainly to apply guarded behavior. */
	protected boolean guardedSwitch(ParameterizedTypeRef typeRef) {
		if (typeRef.getDeclaredType() == bottomType && currentTypeRefs.size() > 1) {
			return false;
		}
		if (guard.tryNext(typeRef)) {
			try {
				return doSwitch(typeRef);
			} finally {
				if (releaseGuard()) {
					guard.done(typeRef);
				}
			}
		} else {
			// guard failed
			return false;
		}
	}

	/**
	 * Guards make sure a type is not visited twice. However releasing enables multiple visits. Drawback of not
	 * releasing arises with same type but different type arguments.
	 *
	 * Default: never visit types twice; drawback: same type with different type argument
	 */
	protected boolean releaseGuard() {
		return false;
	}

	/** Delegate depending on the type of the given ref. */
	protected boolean doSwitch(ParameterizedTypeRef typeRef) {
		try {
			Type type = typeRef.getDeclaredType();
			currentTypeRefs.push(typeRef);
			switch (type.eClass().getClassifierID()) {
			case TypesPackage.TCLASS:
				return visitTClass(typeRef, (TClass) type);
			case TypesPackage.TINTERFACE:
				return visitTInterface(typeRef, (TInterface) type);
			case TypesPackage.TSTRUCTURAL_TYPE:
				return visitTStructuralType(typeRef, (TStructuralType) type);
			case TypesPackage.PRIMITIVE_TYPE:
				return visitPrimitiveType(typeRef, (PrimitiveType) type);
			}
			return false;
		} finally {
			currentTypeRefs.pop();
		}
	}

	/** Returns the type ref currently being visited */
	protected ParameterizedTypeRef getCurrentTypeRef() {
		if (currentTypeRefs.empty()) {
			return null;
		}
		return currentTypeRefs.peek();
	}

	/**
	 * Returns collection of all super types except consumed roles, i.e. super class.
	 */
	protected List<ParameterizedTypeRef> getSuperTypes(Type t) {
		if (t instanceof TClass && ((TClass) t).getSuperClassRef() != null) {
			return Collections.singletonList(((TClass) t).getSuperClassRef());

		} else if (t instanceof TInterface) {
			TInterface ti = (TInterface) t;
			if (!ti.getSuperInterfaceRefs().isEmpty()) {
				return ti.getSuperInterfaceRefs();
			}
		}
		return getImplicitSuperTypes(t);
	}

	/**
	 * Override this method to support implicit super types.
	 */
	protected List<ParameterizedTypeRef> getImplicitSuperTypes(@SuppressWarnings("unused") Type t) {
		return Collections.emptyList(); // implicit super types not supported by default
	}

	/**
	 * Override this method to support polyfills and declaration merging.
	 *
	 * @param filledType
	 *            the type for which the polyfills / merged types are to be retrieved
	 */
	protected Iterable<ParameterizedTypeRef> getPolyfillsOrMergedTypeRefs(Type filledType) {
		Iterable<ParameterizedTypeRef> polyfillsOrMergedTypes = Iterables.concat(
				getPolyfillTypeRefs(filledType),
				getMergedTypeRefs(filledType));

		return polyfillsOrMergedTypes;
	}

	/**
	 * Override this method to support polyfills
	 *
	 * @param filledType
	 *            the type for which the polyfills / merged types are to be retrieved
	 */
	protected List<ParameterizedTypeRef> getPolyfillTypeRefs(Type filledType) {
		return Collections.emptyList(); // polyfills merging not supported by default
	}

	/**
	 * Override this method to support declaration merging.
	 *
	 * @param filledType
	 *            the type for which the polyfills / merged types are to be retrieved
	 */
	protected List<ParameterizedTypeRef> getMergedTypeRefs(Type filledType) {
		return Collections.emptyList(); // declaration merging not supported by default
	}
}
