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

import static com.google.common.collect.Lists.newArrayList;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.projectModel.IN4JSCoreNEW;
import org.eclipse.xpect.xtext.lib.setup.FileSetupContext;
import org.eclipse.xpect.xtext.lib.setup.emf.ResourceFactory;

/**
 * Reads out Xpect ResourceSet configuration to retrieve EMF resources from there.
 */
public class ReadOutResourceSetConfiguration extends ReadOutConfiguration {
	private org.eclipse.xpect.xtext.lib.setup.emf.ResourceSet configuredResourceSet;

	ReadOutResourceSetConfiguration(FileSetupContext ctx, IN4JSCoreNEW resourceSet) {
		super(ctx, resourceSet);
	}

	/**
	 * @param xpectResourceSet
	 *            the Xpect configuration item to be read out
	 */
	public void add(org.eclipse.xpect.xtext.lib.setup.emf.ResourceSet xpectResourceSet) {
		this.configuredResourceSet = xpectResourceSet;
	}

	/**
	 * @return the resources retrieved from the Xpect resource set configuration
	 */
	@Override
	public List<Resource> getResources() {
		final List<Resource> configuredResources = newArrayList();
		if (configuredResourceSet != null) {
			for (ResourceFactory factory : configuredResourceSet.getFactories()) {
				if (factory instanceof org.eclipse.xpect.xtext.lib.setup.emf.Resource) {
					org.eclipse.xpect.xtext.lib.setup.emf.Resource res = (org.eclipse.xpect.xtext.lib.setup.emf.Resource) factory;
					try {
						if (fileSetupCtx != null) {
							Resource createdRes = res.create(fileSetupCtx, resourceSet);
							configuredResources.add(createdRes);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return configuredResources;
	}
}
