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

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.RecursionGuard;

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
 * <li>recursively: roles consumed by super class (incl. indirectly consumed roles) and indirectly extended super class
 * <li>recursively: interfaces implemented by all classes in hierarchy
 * <li>consumed roles in order of declaration
 * <li>recursively: indirectly consumed roles, i.e. roles consumed by roles
 * <li>recursively: interfaces implemented by all roles in hierarchy
 * </ol>
 */
public abstract class AbstractHierachyTraverser<Result> extends TypesSwitch<Boolean> {

	/**
	 * The recursion guard
	 */
	protected final RecursionGuard<Type> guard;

	/**
	 * The type which the traverser has to traverse, that is the bottom type of the hierarchy.
	 */
	protected final Type bottomType;

	/**
	 * Creates a new traverser that is used to safely process a potentially cyclic inheritance tree.
	 */
	public AbstractHierachyTraverser(ContainerType<?> type) {
		this((Type) type);
	}

	/**
	 * Creates a new traverser that is used to safely process a potentially cyclic inheritance tree.
	 */
	public AbstractHierachyTraverser(PrimitiveType type) {
		this((Type) type);
	}

	private AbstractHierachyTraverser(Type type) {
		this.bottomType = type;
		guard = new RecursionGuard<>();
	}

	/**
	 * Return the computed result.
	 */
	public Result getResult() {
		traverse();
		return doGetResult();
	}

	/**
	 * Internal getter for the result.
	 */
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

	private void traverse() {
		Boolean result = doSwitch(bottomType);
		if (Boolean.TRUE.equals(result)) {
			return;
		}
	}

	@Override
	public Boolean casePrimitiveType(PrimitiveType object) {
		if (guard.tryNext(object)) {
			if (process(object)) {
				return true;
			}
			PrimitiveType assignmentCompatible = object.getAssignmentCompatible();
			if (assignmentCompatible != null) {
				ParameterizedTypeRef typeRef = TypeRefsFactory.eINSTANCE.createParameterizedTypeRef();
				typeRef.setDeclaredType(assignmentCompatible);
				Boolean result = doSwitchTypeRef(typeRef);
				if (Boolean.TRUE.equals(result)) {
					return result;
				}
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * Processes declared consumed roles, implemented interfaces and super type. Note that implicit super types are
	 * <b>not</b> not processes, see class description for details.
	 */
	@Override
	public Boolean caseTClass(TClass object) {
		if (guard.tryNext(object)) {
			if (!object.isPolyfill()) {
				if (doSwitchTypeRefs(getPolyfills(object))) {
					return Boolean.TRUE;
				}
			}
			if (process(object)) {
				return Boolean.TRUE;
			}
			if (!object.isPolyfill()) {
				// enqueueInterface(object.getConsumedRoles());
				if (doSwitchSuperTypes(object)) {
					return Boolean.TRUE;
				}
				if (doSwitchImplementedInterfaces(object)) {
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * Process the super type of a class.
	 */
	protected boolean doSwitchSuperTypes(TClass object) {
		return doSwitchTypeRefs(getSuperTypes(object));
	}

	/**
	 * Process the consumed roles of a class.
	 */
	protected boolean doSwitchImplementedInterfaces(TClass object) {
		return doSwitchTypeRefs(object.getImplementedInterfaceRefs());
	}

	@Override
	public Boolean caseTInterface(TInterface object) {
		if (guard.tryNext(object)) {
			if (!object.isPolyfill()) {
				if (doSwitchTypeRefs(getPolyfills(object))) {
					return Boolean.TRUE;
				}
			}
			if (process(object)) {
				return Boolean.TRUE;
			}
			if (!object.isPolyfill()) {
				if (doSwitchSuperInterfaces(object)) {
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * Process the super interfaces of an interface.
	 */
	protected boolean doSwitchSuperInterfaces(TInterface object) {
		return doSwitchTypeRefs(getSuperTypes(object));
	}

	@Override
	public Boolean caseTStructuralType(TStructuralType object) {
		if (guard.tryNext(object)) {
			return process(object);
		}
		return Boolean.FALSE;
	}

	/**
	 * Traverses all types of the list, usually either roles or polyfills.
	 *
	 * @return {@code true} if the processing is finished and no further types should be considered.
	 */
	protected boolean doSwitchTypeRefs(List<ParameterizedTypeRef> typeRefs) {
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
		if (typeRef == null)
			return false;
		Type type = typeRef.getDeclaredType();
		if (type == null || type.eIsProxy())
			return false;

		Boolean result = doSwitch(type);
		return result == null ? false : result;

	}

	/**
	 * Returns collection of all super types except consumed roles, i.e. super class.
	 */
	protected List<ParameterizedTypeRef> getSuperTypes(Type t) {
		if (t instanceof TClass && ((TClass) t).getSuperClassRef() != null)
			return Collections.singletonList(((TClass) t).getSuperClassRef());
		else if (t instanceof TInterface && !((TInterface) t).getSuperInterfaceRefs().isEmpty())
			return ((TInterface) t).getSuperInterfaceRefs();
		return getImplicitSuperTypes(t);
	}

	/**
	 * Override this method to support implicit super types.
	 */
	protected List<ParameterizedTypeRef> getImplicitSuperTypes(@SuppressWarnings("unused") Type t) {
		return Collections.emptyList(); // implicit super types not supported by default
	}

	/**
	 * Override this method to support polyfills.
	 *
	 * @param filledType
	 *            the type for which the polyfills are to be retrieved
	 */
	protected List<ParameterizedTypeRef> getPolyfills(Type filledType) {
		return Collections.emptyList(); // polyfills not supported by default
	}

}
