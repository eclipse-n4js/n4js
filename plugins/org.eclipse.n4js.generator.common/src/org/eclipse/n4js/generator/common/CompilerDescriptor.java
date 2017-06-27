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
package org.eclipse.n4js.generator.common;

import java.util.Map;

import org.eclipse.xtext.generator.OutputConfiguration;

import com.google.common.collect.Maps;

import org.eclipse.n4js.utils.ComponentDescriptor;

/**
 * Value object to store the compiler configuration. It also holds the changes to the changes to the before store
 * compilation configuration, so this information can be used to decide whether e.g. a rebuild is required.
 */
public class CompilerDescriptor extends ComponentDescriptor {
	private boolean isActive;
	private String compiledFileExtension;
	private String compiledFileSourceMapExtension;
	private OutputConfiguration outputConfiguration;
	private String objcwrapperPath = "ObjcWrapper";

	/**
	 * @return if the compiler represented by this configuration should be executed during build
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive
	 *            if the compiler represented by this configuration should be executed during build
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the file extension to use for the compiled file (note: this isn't appended to the N4JS one but replaces
	 *         it)
	 */
	public String getCompiledFileExtension() {
		return compiledFileExtension;
	}

	/**
	 * @param compiledFileExtension
	 *            the file extension to use for the compiled file (note: this isn't appended to the N4JS one but
	 *            replaces it)
	 */
	public void setCompiledFileExtension(String compiledFileExtension) {
		this.compiledFileExtension = compiledFileExtension;
	}

	/**
	 * @return the file extension to use for the source-map.
	 */
	public String getCompiledFileSourceMapExtension() {
		return compiledFileSourceMapExtension;
	}

	/**
	 * @param compiledFileSourceMapExtension
	 *            the file extension to use for the source-map
	 */
	public void setCompiledFileSourceMapExtension(String compiledFileSourceMapExtension) {
		this.compiledFileSourceMapExtension = compiledFileSourceMapExtension;
	}

	/**
	 * @return the configuration of the output (e.g. output directory, deletion of derived resources and so on)
	 */
	public OutputConfiguration getOutputConfiguration() {
		return outputConfiguration;
	}

	/**
	 * Sets the absolute path to the ObjcWrapper utility.
	 */
	public void setObjcwrapperPath(String value) {
		objcwrapperPath = value;
	}

	/**
	 * Returns the absolute path to the ObjcWrapper utility.
	 */
	public String getObjcwrapperPath() {
		return objcwrapperPath;
	}

	/**
	 * @param outputConfiguration
	 *            the configuration of the output (e.g. output directory, deletion of derived resources and so on)
	 */
	public void setOutputConfiguration(OutputConfiguration outputConfiguration) {
		this.outputConfiguration = outputConfiguration;
	}

	@Override
	public CompilerDescriptor copy() {
		CompilerDescriptor copy = new CompilerDescriptor();
		copy.setIdentifier(this.getIdentifier());
		copy.setName(this.getName());
		copy.setDescription(this.getDescription());
		copy.setObjcwrapperPath(this.getObjcwrapperPath());
		for (CompilerProperties prop : CompilerProperties.values()) {
			prop.setValueInCompilerDescriptor(copy, this.getIdentifier(),
					prop.getValueInCompilerDescriptor(this, this.getIdentifier()));
		}
		return copy;
	}

	@Override
	public Map<String, String> fillMap(String outputName) {
		Map<String, String> settings = Maps.newHashMap();
		for (CompilerProperties prop : CompilerProperties.values()) {
			settings.put(prop.getKey(outputName),
					String.valueOf(prop.getValueInCompilerDescriptor(this, outputName)));
		}
		return settings;
	}

}
