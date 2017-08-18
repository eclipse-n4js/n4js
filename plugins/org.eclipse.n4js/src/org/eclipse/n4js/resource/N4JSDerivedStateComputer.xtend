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
package org.eclipse.n4js.resource;

import com.google.inject.Inject
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.typesbuilder.N4JSTypesBuilder
import org.eclipse.xtext.resource.DerivedStateAwareResource
import org.eclipse.xtext.resource.IDerivedStateComputer

/**
 * Derives the types model from the AST and stores it at the second index of the resource.
 * See {@link N4JSTypesBuilder}.
 */
public class N4JSDerivedStateComputer implements IDerivedStateComputer {

	@Inject extension N4JSUnloader
	@Inject private N4JSTypesBuilder typesBuilder;

	/**
	 * Creates an {@link TModule} on the second slot of the resource. when the resource contents is not empty.
	 */
	override installDerivedState(DerivedStateAwareResource resource, boolean preLinkingPhase) {
		val contents = resource.contents;
		if (contents.nullOrEmpty) {
			throw new IllegalStateException
		} else if (contents.size == 1) {
			typesBuilder.createTModuleFromSource(resource, preLinkingPhase);
		} else if (contents.size == 2) {
			/* TODO adjust comments 
			 * 
			 * When loading an AST from a TModule (usually retrieved from the index) by calling
			 * SyntaxRelatedTElement.astElement, this would cause a new TModule to be created again from the AST. That would
			 * lead to an inconsistent state: Two different TModules which both link to the same AST, but the original TModule
			 * (from the index) would not contained in any resource anymore (because it was removed from the resource and
			 * replaced with the newly loaded one). Since the original one is detached from a resource, it cannot resolve
			 * proxies anymore. This would lead to a lot of weird scenarios which are extremely hard to debug because the both
			 * TModule instances look similar.
			 * 
			 * This method adds a new rewire step after the AST has been loaded and a new TModule has been created. It first
			 * compares the original TModule with the new one. If both were derived from the same AST (using a hashCode to
			 * compare that), all links from the AST pointing to the new TModule are rewired to point to the old TModule (and
			 * vice versa all astElements are set to the now loaded AST).
			 * 
			 * If either the modules were derived from different ASTs or the rewiring fails for other reasons (information will
			 * be logged), an {@link IllegalStateException} is thrown. In that case, the AST won't be linked to the old TModule
			 * (and vice versa) at all.
			 */
			typesBuilder.linkTModuleToSource(resource, preLinkingPhase);
		} else {
			throw new IllegalStateException;
		}
	}

	/**
	 * Calls {@link N4JSUnloader#unloadRoot(EObject} for the second slot root.
	 * Then all contents of the resource are cleared.
	 */
	override void discardDerivedState(DerivedStateAwareResource resource) {
		val contents = resource.contents;
		// resource.getContents().get(1-n)clear
		if (contents.empty) {
			return;
		}

		// other resources may hold references to the derived state thus we
		// have to unload (proxify) it explicitly before it is removed from the resource
		val tail = contents.subList(1, contents.size)
		tail.forEach[unloadRoot]
		tail.clear
	}
}
