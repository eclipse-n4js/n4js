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
package org.eclipse.n4js.generator.headless;

import java.util.Set;

import org.eclipse.xtext.generator.OutputConfiguration;

import com.google.common.collect.Sets;

/**
 * Delegating OutputConfiguration consuming setters in special circumstances. It doesn't change the value of the
 * delegate.
 */
@SuppressWarnings("javadoc")
public class WrappedOutputConfiguration extends OutputConfiguration {

	private final OutputConfiguration delegate;
	private final String pathprefix;
	private final Set<WrappedSourceMapping> sourceMappings;

	// only if the setOutputSetting() is called on this wrapper the
	// new value will be stored here
	private String redefinedOutputSetting = null;

	public WrappedOutputConfiguration(OutputConfiguration delegate, String pathprefix) {
		super(delegate.getName());
		this.delegate = delegate;
		this.pathprefix = normalize(pathprefix);
		// Wrapp all sourcemappings:
		sourceMappings = Sets.<WrappedSourceMapping> newHashSet();
		for (SourceMapping sm : delegate.getSourceMappings()) {
			sourceMappings.add(new WrappedSourceMapping(sm, this));
		}
	}

	/**
	 * Ensure path ends with in "/" if not empty.
	 *
	 * @param path
	 *            relative path string
	 * @return empty string or a string ending with "/"
	 */
	static String normalize(String path) {
		if (path == null)
			return "";
		if (!path.endsWith("/"))
			return path + "/";
		return path;
	}

	/**
	 * @return
	 * @see org.eclipse.xtext.generator.OutputConfiguration#getOutputDirectory()
	 */
	@Override
	public String getOutputDirectory() {
		String ret = pathprefix;
		if (redefinedOutputSetting != null) {
			ret += redefinedOutputSetting;
		} else {
			ret += delegate.getOutputDirectory();
		}
		return ret;
	}

	/**
	 * @param outputDirectory
	 * @see org.eclipse.xtext.generator.OutputConfiguration#setOutputDirectory(java.lang.String)
	 */
	@Override
	public void setOutputDirectory(String outputDirectory) {
		// delegate.setOutputDirectory(outputDirectory);
		redefinedOutputSetting = outputDirectory;
	}

	// / below just simple delegates //////////////////////////

	/**
	 * @return
	 * @see org.eclipse.xtext.generator.OutputConfiguration#getName()
	 */
	@Override
	public String getName() {
		return delegate.getName();
	}

	/**
	 * @return
	 * @see org.eclipse.xtext.generator.OutputConfiguration#getDescription()
	 */
	@Override
	public String getDescription() {
		return delegate.getDescription();
	}

	/**
	 * @param description
	 * @see org.eclipse.xtext.generator.OutputConfiguration#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String description) {
		// delegate.setDescription(description);
		throw new UnsupportedOperationException("Setting of values is not supported.");
	}

	/**
	 * @return
	 * @see org.eclipse.xtext.generator.OutputConfiguration#isCleanUpDerivedResources()
	 */
	@Override
	public boolean isCleanUpDerivedResources() {
		return delegate.isCleanUpDerivedResources();
	}

	/**
	 * @param cleanUpDerivedResources
	 * @see org.eclipse.xtext.generator.OutputConfiguration#setCleanUpDerivedResources(boolean)
	 */
	@Override
	public void setCleanUpDerivedResources(boolean cleanUpDerivedResources) {
		// delegate.setCleanUpDerivedResources(cleanUpDerivedResources);
		throw new UnsupportedOperationException("Setting of values is not supported.");
	}

	/**
	 * @return
	 * @see org.eclipse.xtext.generator.OutputConfiguration#isOverrideExistingResources()
	 */
	@Override
	public boolean isOverrideExistingResources() {
		return delegate.isOverrideExistingResources();
	}

	/**
	 * @param overrideExistingResources
	 * @see org.eclipse.xtext.generator.OutputConfiguration#setOverrideExistingResources(boolean)
	 */
	@Override
	public void setOverrideExistingResources(boolean overrideExistingResources) {
		// delegate.setOverrideExistingResources(overrideExistingResources);
		throw new UnsupportedOperationException("Setting of values is not supported.");
	}

	/**
	 * @return
	 * @see org.eclipse.xtext.generator.OutputConfiguration#isSetDerivedProperty()
	 */
	@Override
	public boolean isSetDerivedProperty() {
		return delegate.isSetDerivedProperty();
	}

	/**
	 * @param setDerivedProperty
	 * @see org.eclipse.xtext.generator.OutputConfiguration#setSetDerivedProperty(boolean)
	 */
	@Override
	public void setSetDerivedProperty(boolean setDerivedProperty) {
		// delegate.setSetDerivedProperty(setDerivedProperty);
		throw new UnsupportedOperationException("Setting of values is not supported.");
	}

	/**
	 * @return
	 * @see org.eclipse.xtext.generator.OutputConfiguration#isCreateOutputDirectory()
	 */
	@Override
	public boolean isCreateOutputDirectory() {
		return delegate.isCreateOutputDirectory();
	}

	/**
	 * @param createOutputDirectory
	 * @see org.eclipse.xtext.generator.OutputConfiguration#setCreateOutputDirectory(boolean)
	 */
	@Override
	public void setCreateOutputDirectory(boolean createOutputDirectory) {
		// delegate.setCreateOutputDirectory(createOutputDirectory);
		throw new UnsupportedOperationException("Setting of values is not supported.");
	}

	/**
	 * @return
	 * @see org.eclipse.xtext.generator.OutputConfiguration#isCanClearOutputDirectory()
	 */
	@Override
	public boolean isCanClearOutputDirectory() {
		return delegate.isCanClearOutputDirectory();
	}

	/**
	 * @param canClearOutputDirectory
	 * @see org.eclipse.xtext.generator.OutputConfiguration#setCanClearOutputDirectory(boolean)
	 */
	@Override
	public void setCanClearOutputDirectory(boolean canClearOutputDirectory) {
		// delegate.setCanClearOutputDirectory(canClearOutputDirectory);
		throw new UnsupportedOperationException("Setting of values is not supported.");
	}

	/**
	 * @return
	 * @see org.eclipse.xtext.generator.OutputConfiguration#isInstallDslAsPrimarySource()
	 */
	@Override
	public boolean isInstallDslAsPrimarySource() {
		return delegate.isInstallDslAsPrimarySource();
	}

	/**
	 * @param installDslAsPrimarySource
	 * @see org.eclipse.xtext.generator.OutputConfiguration#setInstallDslAsPrimarySource(boolean)
	 */
	@Override
	public void setInstallDslAsPrimarySource(boolean installDslAsPrimarySource) {
		// delegate.setInstallDslAsPrimarySource(installDslAsPrimarySource);
		throw new UnsupportedOperationException("Setting of values is not supported.");
	}

	/**
	 * @return
	 * @see org.eclipse.xtext.generator.OutputConfiguration#isHideSyntheticLocalVariables()
	 */
	@Override
	public boolean isHideSyntheticLocalVariables() {
		return delegate.isHideSyntheticLocalVariables();
	}

	/**
	 * @param hideSyntheticLocalVariables
	 * @see org.eclipse.xtext.generator.OutputConfiguration#setHideSyntheticLocalVariables(boolean)
	 */
	@Override
	public void setHideSyntheticLocalVariables(boolean hideSyntheticLocalVariables) {
		// delegate.setHideSyntheticLocalVariables(hideSyntheticLocalVariables);
		throw new UnsupportedOperationException("Setting of values is not supported.");
	}

	/**
	 * @return
	 * @see org.eclipse.xtext.generator.OutputConfiguration#isKeepLocalHistory()
	 */
	@Override
	public Boolean isKeepLocalHistory() {
		return delegate.isKeepLocalHistory();
	}

	/**
	 * @param keepLocalHistory
	 * @see org.eclipse.xtext.generator.OutputConfiguration#setKeepLocalHistory(java.lang.Boolean)
	 */
	@Override
	public void setKeepLocalHistory(Boolean keepLocalHistory) {
		// delegate.setKeepLocalHistory(keepLocalHistory);
		throw new UnsupportedOperationException("Setting of values is not supported.");
	}

	/**
	 * @return
	 * @see org.eclipse.xtext.generator.OutputConfiguration#isUseOutputPerSourceFolder()
	 */
	@Override
	public boolean isUseOutputPerSourceFolder() {
		return delegate.isUseOutputPerSourceFolder();
	}

	/**
	 * @param useOutputPerSourceFolder
	 * @see org.eclipse.xtext.generator.OutputConfiguration#setUseOutputPerSourceFolder(boolean)
	 */
	@Override
	public void setUseOutputPerSourceFolder(boolean useOutputPerSourceFolder) {
		// delegate.setUseOutputPerSourceFolder(useOutputPerSourceFolder);
		throw new UnsupportedOperationException("Setting of values is not supported.");
	}

	/**
	 * @return
	 * @see org.eclipse.xtext.generator.OutputConfiguration#getSourceMappings()
	 */
	@Override
	public Set<SourceMapping> getSourceMappings() {
		return delegate.getSourceMappings();
	}

	/**
	 * @param sourceFolder
	 * @return
	 * @see org.eclipse.xtext.generator.OutputConfiguration#getOutputDirectory(java.lang.String)
	 */
	@Override
	public String getOutputDirectory(String sourceFolder) {
		return delegate.getOutputDirectory(sourceFolder);
	}

	/**
	 * @return
	 * @see org.eclipse.xtext.generator.OutputConfiguration#getSourceFolders()
	 */
	@Override
	public Set<String> getSourceFolders() {
		return delegate.getSourceFolders();
	}

	/**
	 * @return
	 * @see org.eclipse.xtext.generator.OutputConfiguration#getOutputDirectories()
	 */
	@Override
	public Set<String> getOutputDirectories() {
		return delegate.getOutputDirectories();
	}

	/**
	 * @return
	 * @see org.eclipse.xtext.generator.OutputConfiguration#hashCode()
	 */
	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	/**
	 * @param obj
	 * @return
	 * @see org.eclipse.xtext.generator.OutputConfiguration#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}

	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return delegate.toString();
	}

	public static class WrappedSourceMapping extends SourceMapping {
		final SourceMapping delegate;
		final WrappedOutputConfiguration parent;

		WrappedSourceMapping(SourceMapping delegate, WrappedOutputConfiguration parent) {
			super(delegate.getSourceFolder());
			this.parent = parent;
			this.delegate = delegate;
		}

		/**
		 * @return
		 * @see org.eclipse.xtext.generator.OutputConfiguration.SourceMapping#getOutputDirectory()
		 */
		@Override
		public String getOutputDirectory() {
			String ret = parent.pathprefix;
			delegate.getOutputDirectory();
			return ret;
		}

		// / below just simple delegates //////////////////////////
		/**
		 * @return
		 * @see org.eclipse.xtext.generator.OutputConfiguration.SourceMapping#getSourceFolder()
		 */
		@Override
		public String getSourceFolder() {
			return delegate.getSourceFolder();
		}

		/**
		 * @param outputDirectory
		 * @see org.eclipse.xtext.generator.OutputConfiguration.SourceMapping#setOutputDirectory(java.lang.String)
		 */
		@Override
		public void setOutputDirectory(String outputDirectory) {
			// delegate.setOutputDirectory(outputDirectory);
			throw new UnsupportedOperationException("Setting of values is not supported.");
		}

		/**
		 * @return
		 * @see org.eclipse.xtext.generator.OutputConfiguration.SourceMapping#isIgnore()
		 */
		@Override
		public boolean isIgnore() {
			return delegate.isIgnore();
		}

		/**
		 * @param ignore
		 * @see org.eclipse.xtext.generator.OutputConfiguration.SourceMapping#setIgnore(boolean)
		 */
		@Override
		public void setIgnore(boolean ignore) {
			// delegate.setIgnore(ignore);
			throw new UnsupportedOperationException("Setting of values is not supported.");
		}

		/**
		 * @param obj
		 * @return
		 * @see org.eclipse.xtext.generator.OutputConfiguration.SourceMapping#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			return delegate.equals(obj);
		}

		/**
		 * @return
		 * @see org.eclipse.xtext.generator.OutputConfiguration.SourceMapping#hashCode()
		 */
		@Override
		public int hashCode() {
			return delegate.hashCode();
		}

		/**
		 * @return
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return delegate.toString();
		}

	}

}
