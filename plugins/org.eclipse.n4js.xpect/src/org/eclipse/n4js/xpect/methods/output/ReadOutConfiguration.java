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
package org.eclipse.n4js.xpect.methods.output;

import static com.google.common.base.Optional.absent;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.xpect.common.DuplicateResourceAwareFileSetupContext;
import org.eclipse.xpect.xtext.lib.setup.FileSetupContext;
import org.eclipse.xtext.resource.IResourceDescriptions;

/**
 * Java value object to passed to ISetupInitializer to let extract the configuration via reflection. Xpect look for
 * add-methods and checks its input parameter type whether there is a configuration of same type and then passes this
 * object to this add-method.
 */
public abstract class ReadOutConfiguration {

	/** Resource set initialized from {@link IN4JSCore} and wrapped into a delegate. */
	protected final ResourceSet resourceSet;
	/** The Xtext index for the resource set. */
	protected final IResourceDescriptions index;
	/** Context of the current running Xpect test. */
	protected final FileSetupContext fileSetupCtx;

	ReadOutConfiguration(final FileSetupContext ctx, final IN4JSCore core) {
		this.resourceSet = core.createResourceSet(absent());
		index = core.getXtextIndex(this.resourceSet);
		this.fileSetupCtx = new DuplicateResourceAwareFileSetupContext(ctx);
	}

	/**
	 * @return the resources retrieved from the Xpect resource set configuration
	 */
	public abstract List<Resource> getResources();
}
