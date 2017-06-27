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
package org.eclipse.n4js.generator;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import org.eclipse.n4js.generator.common.ISubGenerator;

/**
 */
public enum SubGeneratorType implements ISubGeneratorType,CompilerConstants {
	// @formatter:off
	@SuppressWarnings("javadoc")
	ECMA_SCRIPT("org.eclipse.n4js.transpiler.es", ECMA_SCRIPT_SUB_GENERATOR);
//	@SuppressWarnings("javadoc")
//	SAMPLE("org.eclipse.n4js.transpiler.sample", SAMPLE_SUB_GENERATOR);
	// @formatter:on

	private static Logger LOG = Logger.getLogger(SubGeneratorType.class);

	private final String bundleId;
	private final String generatorClassName;

	private SubGeneratorType(String bundleId, String generatorClassName) {
		this.bundleId = bundleId;
		this.generatorClassName = generatorClassName;
	}

	/**
	 * @return the bundleId
	 */
	@Override
	public String getBundleId() {
		return bundleId;
	}

	/**
	 * @return the generatorClass
	 */
	@Override
	public String getGeneratorClassName() {
		return generatorClassName;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<? extends ISubGenerator> getSubGeneratorClass() {
		Class<?> clazz = null;
		try {
			Bundle b = org.osgi.framework.FrameworkUtil.getBundle(SubGeneratorType.class);
			if (b != null) {
				if (b.getBundleContext() == null) {
					b.start();
				}
				BundleContext ctx = b.getBundleContext();
				if (ctx != null) {
					Bundle subGeneratorBundle = null;
					if (Platform.isRunning()) {
						subGeneratorBundle = org.eclipse.core.runtime.Platform.getBundle(getBundleId());
					} else {
						subGeneratorBundle = ctx.getBundle(getBundleId());
					}
					if (subGeneratorBundle != null) {
						clazz = subGeneratorBundle.loadClass(getGeneratorClassName());
					}
				}
			} else {
				clazz = Class.forName(getGeneratorClassName());
			}
			if (clazz == null) {
				LOG.warn(getGeneratorClassName() + " cannot be found.");
			} else {
				if (ISubGenerator.class.isAssignableFrom(clazz)) {
					return (Class<? extends ISubGenerator>) clazz;
				} else {
					LOG.warn("Found " + getGeneratorClassName() + " doesn't implement ISubGenerator.");
				}
			}
		} catch (ClassNotFoundException e) {
			LOG.warn(getGeneratorClassName() + " cannot be found.");
		} catch (BundleException e) {
			LOG.error(e);
		}
		return null;
	}

}
