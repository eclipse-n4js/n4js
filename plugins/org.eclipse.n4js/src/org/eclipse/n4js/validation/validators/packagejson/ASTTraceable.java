/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.validation.validators.packagejson;

import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;

/**
 * An object wrapper around an arbitrary object that allows to trace objects that were the result of a transformation
 * based on AST elements, back to their original AST elements.
 */
public class ASTTraceable<ElementT> {
	/**
	 * Returns a transformer function that applies {@code transformer} and transforms its result to a
	 * {@link ASTTraceable} element.
	 *
	 * The resulting traceable allows access to the transformation result via {@link #element}, and access to the AST
	 * element via {@link #astElement}.
	 */
	public static <FromT extends EObject, ElementT> Function<FromT, ASTTraceable<ElementT>> map(
			Function<FromT, ElementT> transformer) {
		return e -> ASTTraceable.of(e, transformer.apply(e));
	}

	/**
	 * Creates a new {@link ASTTraceable} object wrapper for the given {@link EObject} and higher-level {@code element}.
	 */
	public static <ElementT> ASTTraceable<ElementT> of(EObject e, ElementT element) {
		return new ASTTraceable<>(e, element);
	}

	/** The corresponding AST element. */
	final EObject astElement;
	/** The wrapped value. */
	final ElementT element;

	/* Instantiates a new traceable element wrapper. */
	private ASTTraceable(EObject astElement, ElementT element) {
		this.astElement = astElement;
		this.element = element;
	}

	@Override
	public int hashCode() {
		return this.astElement.hashCode() ^ this.element.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ASTTraceable<?>)) {
			return false;
		}
		return element.equals(((ASTTraceable<?>) object).element) &&
				astElement.equals(((ASTTraceable<?>) object).astElement);
	}

	@Override
	public String toString() {
		return ASTTraceable.class.getSimpleName() + "(" + this.element.toString() + ", astElement="
				+ this.astElement.toString() + ")";
	}
}