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

import org.eclipse.n4js.utils.IComponentProperties;

/**
 * Compiler properties to be used to generically build up the compiler configuration preference pages.
 */
public enum CompilerProperties implements IComponentProperties<CompilerDescriptor> {

	/** true, if the compiler, represented by this properties, should run */
	IS_ACTIVE("autobuilding", "Compiler is activated", Boolean.class, true, true) {

		@Override
		public void setValueInCompilerDescriptor(CompilerDescriptor compilerDescriptor, String outputName,
				Object value) {
			compilerDescriptor.setActive((Boolean) value);
		}

		@Override
		public Object getValueInCompilerDescriptor(CompilerDescriptor compilerDescriptor, String outputName) {
			return compilerDescriptor.isActive();
		}
	},
	/** the file extension to use for the compiled file */
	COMPILED_FILE_EXTENSION("compiledFileExtension", "Extension to use for compiled file", String.class, true, false) {
		@Override
		public void setValueInCompilerDescriptor(CompilerDescriptor compilerDescriptor, String outputName,
				Object value) {
			compilerDescriptor.setCompiledFileExtension((String) value);
		}

		@Override
		public Object getValueInCompilerDescriptor(CompilerDescriptor compilerDescriptor, String outputName) {
			return compilerDescriptor.getCompiledFileExtension();
		}
	},

	/** the file extension to use for the compiled file */
	COMPILED_FILE_SOURCEMAP_EXTENSION("compiledFileSourceMapExtension",
			"Extension to use for sourcemap of compiled file", String.class, true, false) {
		@Override
		public void setValueInCompilerDescriptor(CompilerDescriptor compilerDescriptor, String outputName,
				Object value) {
			compilerDescriptor.setCompiledFileSourceMapExtension((String) value);
		}

		@Override
		public Object getValueInCompilerDescriptor(CompilerDescriptor compilerDescriptor, String outputName) {
			return compilerDescriptor.getCompiledFileSourceMapExtension();
		}
	},
	/**
	 * the directory, where the files should be compiled to - note that the compiler itself may also produced some path
	 * structure - these paths will be appended to this directory path
	 */
	OUTPUT_DIRECTORY("directory", "Directory", String.class, true, false) {
		@Override
		public void setValueInCompilerDescriptor(CompilerDescriptor compilerDescriptor, String outputName,
				Object value) {
			getOutputConfiguration(compilerDescriptor, outputName).setOutputDirectory((String) value);
		}

		@Override
		public Object getValueInCompilerDescriptor(CompilerDescriptor compilerDescriptor, String outputName) {
			return getOutputConfiguration(compilerDescriptor, outputName).getOutputDirectory();
		}
	},
	/**
	 * whether the output directory should be created automatically. If false compilation will only run, when the
	 * directory has been created manually before.
	 */
	OUTPUT_CREATE_DIRECTORY("createDirectory", "Create directory, if it doesn't exist", Boolean.class, false, false) {
		@Override
		public void setValueInCompilerDescriptor(CompilerDescriptor compilerDescriptor, String outputName,
				Object value) {
			getOutputConfiguration(compilerDescriptor, outputName).setCreateOutputDirectory((Boolean) value);
		}

		@Override
		public Object getValueInCompilerDescriptor(CompilerDescriptor compilerDescriptor, String outputName) {
			return getOutputConfiguration(compilerDescriptor, outputName).isCreateOutputDirectory();
		}
	},
	/**
	 * Whether the compiler is allowed to replace the content of a file with same path (or the user is required to
	 * delete this file manually before to get it recompiled)
	 */
	OUTPUT_OVERRIDE("override", "Overwrite existing files", Boolean.class, false, false) {
		@Override
		public void setValueInCompilerDescriptor(CompilerDescriptor compilerDescriptor, String outputName,
				Object value) {
			getOutputConfiguration(compilerDescriptor, outputName).setOverrideExistingResources((Boolean) value);
		}

		@Override
		public Object getValueInCompilerDescriptor(CompilerDescriptor compilerDescriptor, String outputName) {
			return getOutputConfiguration(compilerDescriptor, outputName).isOverrideExistingResources();
		}
	},
	/**
	 * whether the compiled files show get a flag "derived" - such marked files won't be included into Eclipse file
	 * search and this flag is also be used to delete such files in every clean build (as they are only derived from
	 * other files)
	 */
	OUTPUT_DERIVED("derived", "Mark generated files as derived", Boolean.class, false, false) {
		@Override
		public void setValueInCompilerDescriptor(CompilerDescriptor compilerDescriptor, String outputName,
				Object value) {
			getOutputConfiguration(compilerDescriptor, outputName).setSetDerivedProperty((Boolean) value);
		}

		@Override
		public Object getValueInCompilerDescriptor(CompilerDescriptor compilerDescriptor, String outputName) {
			return getOutputConfiguration(compilerDescriptor, outputName).isSetDerivedProperty();
		}
	},
	/** Whether files marked as derived should be deleted in clean build. */
	OUTPUT_CLEANUP_DERIVED("cleanupDerived", "Delete generated files, when clean build is triggered", Boolean.class,
			false, false) {
		@Override
		public void setValueInCompilerDescriptor(CompilerDescriptor compilerDescriptor, String outputName,
				Object value) {
			getOutputConfiguration(compilerDescriptor, outputName).setCleanUpDerivedResources((Boolean) value);
		}

		@Override
		public Object getValueInCompilerDescriptor(CompilerDescriptor compilerDescriptor, String outputName) {
			return getOutputConfiguration(compilerDescriptor, outputName).isCleanUpDerivedResources();
		}
	},
	/**
	 * Whether the whole output directory contents should (so also all sub directories and files whether marked as
	 * derived or not will be deleted).
	 */
	OUTPUT_CLEAN_DIRECTORY("cleanDirectory", "Clean whole directory, when clean build is triggered", Boolean.class,
			false, false) {
		@Override
		public void setValueInCompilerDescriptor(CompilerDescriptor compilerDescriptor, String outputName,
				Object value) {
			getOutputConfiguration(compilerDescriptor, outputName).setCanClearOutputDirectory((Boolean) value);
		}

		@Override
		public Object getValueInCompilerDescriptor(CompilerDescriptor compilerDescriptor, String outputName) {
			return getOutputConfiguration(compilerDescriptor, outputName).isCanClearOutputDirectory();
		}
	},
	/**
	 * Whether the change history of generated files should be tracked locally so you are able to compare the current
	 * version with earlier generated versions.
	 */
	OUTPUT_KEEP_LOCAL_HISTORY("keepLocalHistory", "Keep local history for generated files", Boolean.class, false,
			false) {
		@Override
		public void setValueInCompilerDescriptor(CompilerDescriptor compilerDescriptor, String outputName,
				Object value) {
			getOutputConfiguration(compilerDescriptor, outputName).setKeepLocalHistory((Boolean) value);
		}

		@Override
		public Object getValueInCompilerDescriptor(CompilerDescriptor compilerDescriptor, String outputName) {
			return getOutputConfiguration(compilerDescriptor, outputName).isKeepLocalHistory();
		}
	};

	/**
	 * first part of the preference key, have to be the same as EclipseOutputConfigurationProvider.OUTPUT_PREFERENCE_TAG
	 * as this class is reused in N4JS UI
	 */
	public static final String OUTPUT_PREFERENCE_TAG = "outlet";

	private final String key;
	private final String label;
	private final Class<?> type;
	private final boolean visibleForClient;
	private final boolean visibleInPreferencePage;

	/**
	 * For a description of the parameters see corresponding getter/setter methods.
	 */
	private CompilerProperties(String key, String label, Class<?> type, boolean visibleForClient,
			boolean visibleInPreferencePage) {
		this.key = key;
		this.label = label;
		this.type = type;
		this.visibleForClient = visibleForClient;
		this.visibleInPreferencePage = visibleInPreferencePage;
	}

	/**
	 * The short key that identifies this property. Note this isn't the key to use to resolve the value in the
	 * preference store as this key is dependent on the actual compiler. That key can be calculated by calling
	 * getKey(String) where String is the output name (= compiler id). This short key will then be the last part of the
	 * compiler dependent key.
	 */
	@Override
	public String getKey() {
		return key;
	}

	/**
	 * The string to label the input field or check box in the compiler configuration preference page
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * Type of the property. Depending on the type, the preference page (see {@code CompilePreferencesDetailsPart}) will
	 * create different field editors. Internally, only Boolean and String are distinguished, all other types are
	 * handled similar to String.
	 */
	@Override
	public Class<?> getType() {
		return type;
	}

	/**
	 * True, if the configuration access interface should provide access to this property. Design rational:
	 * configuration properties like 'Mark generated files as derived' are only sensible to be checked in UI but not
	 * inside the compiler
	 */
	@Override
	public boolean isVisibleForClient() {
		return visibleForClient;
	}

	/**
	 * Returns true if the property can be edited in the preference page. This is used in
	 * {@code CompilePreferencesDetailsPart} in the ui plugin.
	 */
	@Override
	public boolean isVisibleInPreferencePage() {
		return visibleInPreferencePage;
	}

	/**
	 * @param outputName
	 *            the output name (in context of the output configuration) that also identifiers the compiler in our
	 *            case
	 * @return the compiler dependent property key that can be used to resolve the value of this property for the
	 *         compiler identified by the output name in the preference store
	 */
	@Override
	public String getKey(String outputName) {
		return OUTPUT_PREFERENCE_TAG + "." + outputName + "." + key;
	}

	/**
	 * Creates a new output configuration using the output name and sets it to the given compiler descriptor or if there
	 * is already an output configuration contained in the compiler descriptor, this one is returned.
	 *
	 *
	 * @param compilerDescriptor
	 *            the value object (mostly used as transfer object) to hold the configuration of an compiler
	 * @param outputName
	 *            the output name (in context of the output configuration) that also identifiers the compiler in our
	 *            case
	 * @return the existing or newly created output configuration for the given compiler descriptor
	 */
	OutputConfiguration getOutputConfiguration(CompilerDescriptor compilerDescriptor, String outputName) {
		if (compilerDescriptor.getOutputConfiguration() == null) {
			compilerDescriptor.setOutputConfiguration(new OutputConfiguration(outputName));
		}
		return compilerDescriptor.getOutputConfiguration();
	}

	/**
	 * Initializes a compiler descriptor out of the given values in the properties file
	 *
	 * @param properties
	 *            the key value pairs expecting the keys to be compliant to the preference store key
	 * @param outputName
	 *            the compiler id
	 * @return the built up compiler descriptor
	 */
	public static CompilerDescriptor loadFromMap(Map<String, String> properties, String outputName) {
		CompilerDescriptor compilerDescriptor = new CompilerDescriptor();
		compilerDescriptor.setIdentifier(outputName);
		for (CompilerProperties prop : CompilerProperties.values()) {
			prop.setValueInCompilerDescriptor(compilerDescriptor, outputName, properties.get(prop.getKey(outputName)));
		}
		return compilerDescriptor;
	}
}
